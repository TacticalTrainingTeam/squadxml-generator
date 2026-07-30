package de.tacticalteam.squadxml_generator.domain.service;

import org.springframework.stereotype.Service;

import de.tacticalteam.squadxml_generator.domain.model.Squad;
import de.tacticalteam.squadxml_generator.domain.port.in.SquadInPort;
import de.tacticalteam.squadxml_generator.domain.port.out.MemberOutPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SquadService implements SquadInPort {

    private final MemberOutPort memberOutPort;

    @Override
    public Squad getTttSquad() {
        return Squad.ttt()
            .withMembers(memberOutPort.fetchMembers());
    }

    @Override
    public Squad getTttSubduedSquad() {
        return Squad.tttSubdued()
            .withMembers(memberOutPort.fetchMembers());
    }
}