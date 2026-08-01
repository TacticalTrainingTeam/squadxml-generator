package de.tacticalteam.squadxml_generator.adapter.out.member;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import de.tacticalteam.squadxml_generator.adapter.out.authentik.model.PartialGroupDto;
import de.tacticalteam.squadxml_generator.adapter.out.authentik.model.UserDto;
import de.tacticalteam.squadxml_generator.domain.model.Member;

@Mapper
abstract class AuthentikAdapterMapper {

    private static final UUID OFFIZIER_ID = UUID.fromString("15596018-e696-4b0a-9940-d2126ddaf100");
    private static final UUID UNTEROFFIZIER_ID = UUID.fromString("c57dac86-5452-4464-91f3-462a3cde9437");
    private static final UUID VETERAN_ID = UUID.fromString("27d9591f-e008-42d0-b571-f9e5b1febea0");
    private static final UUID SOLDAT_ID = UUID.fromString("0f65053d-0389-4e73-a91c-bbf6083412e2");

    static final List<UUID> ALLOWED_RANKS = List.of(OFFIZIER_ID, UNTEROFFIZIER_ID, VETERAN_ID, SOLDAT_ID);

    // TODO Arma 3 name
    @Mapping(target = "remark", source = "userDto.groupsObj")
    abstract Member toDomain(String steamId, UserDto userDto);

    String remark(List<PartialGroupDto> partialGroupDtos) {
        var result = "N/A";

        if (partialGroupDtos == null || partialGroupDtos.isEmpty()) {
            return result;
        }

        // We use the first match we find because users should not have multiple ranks anyway
        for (var partialGroupDto : partialGroupDtos) {
            if (ALLOWED_RANKS.contains(partialGroupDto.getPk())) {
                result = partialGroupDto.getName();
                break;
            }
        }

        return result;
    }
}
