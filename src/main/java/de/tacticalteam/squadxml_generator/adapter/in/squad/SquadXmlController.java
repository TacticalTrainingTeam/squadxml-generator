package de.tacticalteam.squadxml_generator.adapter.in.squad;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.tacticalteam.squadxml_generator.domain.model.Squad;
import de.tacticalteam.squadxml_generator.domain.port.in.SquadInPort;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SquadXmlController {

    private final SquadInPort squadInPort;

    @GetMapping(value = "/squadxml/squad.xml", produces = MediaType.APPLICATION_XML_VALUE)
    ResponseEntity<String> squadXml() {
        return xmlResponse(squadInPort.getTttSquad());
    }

    @GetMapping(value = "/squadxml_tvt/squad.xml", produces = MediaType.APPLICATION_XML_VALUE)
    ResponseEntity<String> tvtSquadXml() {
        return xmlResponse(squadInPort.getTttSubduedSquad());
    }

    private ResponseEntity<String> xmlResponse(Squad squad) {
        var squadXmlRenderer = new SquadXmlRenderer();
        var xml = squadXmlRenderer.render(squad);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_XML)
            .body(xml);
    }
}