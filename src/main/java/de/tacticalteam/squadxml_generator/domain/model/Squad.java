package de.tacticalteam.squadxml_generator.domain.model;

import java.util.List;

import lombok.With;

@With
public record Squad(String nick, String name, String email, String web, String picture, String title, List<Member> members) {

    private static final String NICK = "TTT";
    private static final String NAME = "Tactical Training Team";
    private static final String EMAIL = "kontakt@tacticalteam.de";
    private static final String WEB = "tacticalteam.de";
    private static final String PICTURE = "logo.paa";
    private static final String PICTURE_SUBDUED = "logo_subdued.paa";
    private static final String TITLE = "Tactical Training Team";


    public static Squad ttt() {
        return new Squad(NICK, NAME, EMAIL, WEB, PICTURE, TITLE, List.of());
    }

    public static Squad tttSubdued() {
        return new Squad(NICK, NAME, EMAIL, WEB, PICTURE_SUBDUED, TITLE, List.of());
    }
}