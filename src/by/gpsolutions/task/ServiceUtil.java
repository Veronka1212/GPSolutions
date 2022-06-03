package by.gpsolutions.task;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

import static by.gpsolutions.task.ConstantsXML.*;

public class ServiceUtil {

    public static List<Hotel> sortHotels(List<Hotel> hotels) {

        return hotels.stream()
                .filter(
                        o -> o.getName().contains(HILTON
                        )
                                &&
                                (
                                        o.getState().equalsIgnoreCase(NEW_YORK) || o.getState().equalsIgnoreCase(NY)
                                )
                )
                .toList();
    }

    public static void putToXml(List<Hotel> hotels) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document results = builder.newDocument();
        Element lists = results.createElement(LISTS);
        results.appendChild(lists);
        Element names = results.createElement(NAMES);
        lists.appendChild(names);
        Element prices = results.createElement(PRICES);
        lists.appendChild(prices);
        Element addresses = results.createElement(ADDRESSES);
        lists.appendChild(addresses);

        for (Hotel hotel : hotels) {
            Element name = results.createElement(NAME);
            names.appendChild(name);
            name.appendChild(results.createTextNode(hotel.getName()));
            Element price = results.createElement(PRICE);
            prices.appendChild(price);
            price.appendChild(results.createTextNode(hotel.getPrice()));
            Element address = results.createElement(ADDRESS);
            addresses.appendChild(address);
            address.appendChild(results.createTextNode(hotel.getAddressLine()));
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(results);
        StreamResult file = new StreamResult(new File(FILE_WITH_RESULS));
        transformer.transform(source, file);
    }
}
