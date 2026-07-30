package de.tacticalteam.squadxml_generator.adapter.out.member;

import static de.tacticalteam.squadxml_generator.adapter.out.member.AuthentikAdapterMapper.ALLOWED_RANKS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import de.tacticalteam.squadxml_generator.adapter.out.authentik.api.CoreApi;
import de.tacticalteam.squadxml_generator.adapter.out.authentik.api.SourcesApi;
import de.tacticalteam.squadxml_generator.adapter.out.authentik.model.UserDto;
import de.tacticalteam.squadxml_generator.adapter.out.authentik.model.UserSourceConnectionDto;
import de.tacticalteam.squadxml_generator.domain.model.Member;
import de.tacticalteam.squadxml_generator.domain.port.out.MemberOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthentikAdapter implements MemberOutPort {

    private static final String SOURCE_SLUG = "steam";
    private static final Pattern STEAM_OPEN_ID = Pattern.compile("^https://steamcommunity\\.com/openid/id/([0-9]+)$");

    private final SourcesApi sourcesApi;
    private final CoreApi coreApi;
    private final AuthentikAdapterMapper mapper;

    @Override
    public List<Member> fetchMembers() {
        var userConnections = new ArrayList<UserSourceConnectionDto>();

        var page = BigDecimal.ONE;
        do {
            log.debug("Fetching page {} of UserSourceConnections", page);
            var userConnectionsPage = sourcesApi.sourcesUserConnectionsAllList(null, page.intValue(), null, null, SOURCE_SLUG, null);
            log.debug("Fetched page {} of UserSourceConnections, UserSourceConnections object: {}", page, userConnectionsPage);
            page = userConnectionsPage.getPagination()
                .getNext();
            userConnections.addAll(userConnectionsPage.getResults());
        } while (!page.equals(BigDecimal.ZERO));

        var steamIdAndUserDto = new HashMap<String, UserDto>();
        for (var userConnection : userConnections) {
            var steamOpenId = userConnection.getIdentifier();
            var matcher = STEAM_OPEN_ID.matcher(steamOpenId);
            if (matcher.matches()) {
                var steamId = matcher.group(1);
                var userId = userConnection.getUser();
                log.debug("Fetching user with id {}", userId);
                var userDto = coreApi.coreUsersRetrieve(userId);
                log.debug("Fetched user with id {}, user object: {}", userId, userDto);
                if (userDto.getGroups() != null && userDto.getGroups()
                    .stream()
                    .anyMatch(ALLOWED_RANKS::contains)) {
                    steamIdAndUserDto.put(steamId, userDto);
                }
            }
        }

        var result = steamIdAndUserDto.entrySet()
            .stream()
            .map(entry -> mapper.toDomain(entry.getKey(), entry.getValue()))
            .toList();
        return result;
    }
}
