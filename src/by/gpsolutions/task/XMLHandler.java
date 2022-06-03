package by.gpsolutions.task;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.gpsolutions.task.ConstantsXML.*;

public class XMLHandler extends DefaultHandler {

    private static final List<Hotel> hotels = new ArrayList<>();

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        AdvancedXMLHandler handler = new AdvancedXMLHandler();
        parser.parse(new File(HOTELS), handler);

        ServiceUtil.putToXml(ServiceUtil.sortHotels(hotels));
    }

    private static class AdvancedXMLHandler extends DefaultHandler {
        private String lastElementName;
        private Hotel hotel = new Hotel();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes){
            lastElementName = qName;
            if (qName.equals(HOTEL)) {
                hotel.setPrice(attributes.getValue(PRICE));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            String information = new String(ch, start, length);
            information = information.replace("\n", "").trim();
            if (!information.isEmpty()) {
                if (lastElementName.equals(NAME))
                    hotel.setName(information);
                if (lastElementName.equals(ADDRESS_LINE))
                    hotel.setAddressLine(information);
                if (lastElementName.equals(CITY))
                    hotel.setCity(information);
                if (lastElementName.equals(COUNTRY))
                    hotel.setCountry(information);
                if (lastElementName.equals(STATE))
                    hotel.setState(information);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName){
            if (hotel.getState() != null && !hotel.getState().isEmpty()) {
                hotels.add(hotel);
                hotel = new Hotel();
            }
        }
    }
}