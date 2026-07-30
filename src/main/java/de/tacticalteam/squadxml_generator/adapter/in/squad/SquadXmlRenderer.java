package de.tacticalteam.squadxml_generator.adapter.in.squad;

import java.io.StringWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.springframework.stereotype.Component;

import de.tacticalteam.squadxml_generator.domain.model.Member;
import de.tacticalteam.squadxml_generator.domain.model.Squad;

@Component
public class SquadXmlRenderer {

    private static final String NOT_APPLICABLE = "N/A";
    private static final String TTT_TAG = "[TTT]";

    public String render(Squad squad) {
        StringWriter output = new StringWriter();
        try {
            XMLStreamWriter writer = XMLOutputFactory.newFactory()
                .createXMLStreamWriter(output);
            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeDTD("<!DOCTYPE squad SYSTEM \"squad.dtd\">");
            writer.writeStartElement("squad");
            writer.writeAttribute("nick", squad.nick());
            writeElement(writer, "name", squad.name());
            writeElement(writer, "email", squad.email());
            writeElement(writer, "web", squad.web());
            writeElement(writer, "picture", squad.picture());
            writeElement(writer, "title", squad.title());
            for (Member member : squad.members()) {
                writer.writeStartElement("member");
                writer.writeAttribute("id", member.steamId());
                writer.writeAttribute("nick", member.name());
                writeElement(writer, "name", member.name());
                writeElement(writer, "email", NOT_APPLICABLE);
                writeElement(writer, "icq", NOT_APPLICABLE);
                writeElement(writer, "remark", member.remark());
                writer.writeEndElement();
            }
            for (Member member : squad.members()) {
                writer.writeStartElement("member");
                writer.writeAttribute("id", member.steamId());
                writer.writeAttribute("nick", TTT_TAG + " " + member.name());
                writeElement(writer, "name", TTT_TAG + " " + member.name());
                writeElement(writer, "email", NOT_APPLICABLE);
                writeElement(writer, "icq", NOT_APPLICABLE);
                writeElement(writer, "remark", member.remark());
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.close();
            return output.toString();
        } catch (XMLStreamException exception) {
            throw new IllegalStateException("Unable to generate squad XML", exception);
        }
    }

    private void writeElement(XMLStreamWriter writer, String name, String value) throws XMLStreamException {
        writer.writeStartElement(name);
        writer.writeCharacters(value);
        writer.writeEndElement();
    }
}