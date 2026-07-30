package de.tacticalteam.squadxml_generator.domain.port.out;

import de.tacticalteam.squadxml_generator.domain.model.Member;

import java.util.List;

public interface MemberOutPort {

    List<Member> fetchMembers();
}