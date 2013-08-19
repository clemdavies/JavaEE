
package project;

import java.io.CharArrayWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.ParserAdapter;

/**
 * Reads xml file of orders, populating a map of orders.
 *
 * @author pig
 */
public class SAXReader extends DefaultHandler{

    private CharArrayWriter contents;
    private Map orders;
    private Order order;


    /**
     * Empty constructor.
     */
    public SAXReader(){
    }

    /**
     * Reads xml document into stream, initiates processing of xml document into memory.
     */
    public void load(){

        try {
            InputStream inputStream = null;

            setContents( new CharArrayWriter() );
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            ParserAdapter parserAdapter = new ParserAdapter( saxParser.getParser() );
            parserAdapter.setContentHandler(this);

            //derive a stream from the file and parse
            setOrders( new Hashtable() );
            inputStream = new FileInputStream(System.getProperty("input.dir") + System.getProperty("xml.order.input.file"));
            parserAdapter.parse( new InputSource( inputStream ) );
        } catch(Exception ex) {
            ex.printStackTrace ();
        }

    }


    /**
     * Called for each start element encountered, populates Order objects in memory
     * based on XML content within.
     *
     * @param namespaceURI The Namespace URI, or the empty string if the element has no Namespace URI or if Namespace processing is not being performed.
     * @param localName    The name of the element.
     * @param qName        The qualified name (with prefix), or the empty string if qualified names are not available.
     * @param attr         A map-like structure that contains all element attributes.
     * @throws SAXException
     */
    public void startElement(String namespaceURI, String localName, String qName, Attributes attr) throws SAXException
    {
        getContents().reset();
        String hold;
        if(localName.equalsIgnoreCase("ORDER")){
            setOrder(new Order());

            hold = attr.getValue("custid");
            if(hold != null && hold.trim ().length () > 0){
                order.setCustomerNumber(Integer.parseInt(hold));
            }
        }else if(localName.equalsIgnoreCase("ITEM")){
            hold = attr.getValue("name");
            if(hold != null && hold.trim ().length () > 0){
                order.setItem(hold);
            }
        }
    }

    /**
     * Called for each end element encountered, populates Order objects in memory
     * based on XML content within.
     *
     * @param namespaceURI
     * @param localName The local name (without prefix), or the empty string if Namespace processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty string if qualified names are not available.
     * @throws SAXException
     */
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException
    {
        String hold;
        if (localName.equalsIgnoreCase("ORDER")) {
            addOrder(getOrder());
            setOrder(null);
        } else if (localName.equalsIgnoreCase("NAME")) {
            hold = contents.toString();
            order.setCustomerName(hold);
        } else if (localName.equalsIgnoreCase("QUANTITY")) {
            hold = contents.toString();
            order.setQuantity(Integer.parseInt(hold));
        } else if (localName.equalsIgnoreCase("PRICE")) {
            hold = contents.toString();
            order.setUnitPrice(Double.parseDouble(hold));
        }
    }

    /**
     * Override methods of the DefaultHandler class to gain notification of SAX Events.
     * This method is used to retrieve the characters in the XML stream
     *
     * @param ch     The characters.
     * @param start  The start position in the character array.
     * @param length The number of characters to use from the character array.
     * @throws SAXException
     */
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        this.contents.write(ch, start, length);
    }


    /**
     * @return the contents
     */
    public CharArrayWriter getContents() {
        return contents;
    }

    /**
     * @param contents the contents to set
     */
    public void setContents(CharArrayWriter contents) {
        this.contents = contents;
    }

    /**
     * @return the orders
     */
    public Map getOrders() {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(Map orders) {
        this.orders = orders;
    }

    /**
     * @return the order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * @param order the order to add to Hashtable
     */
    public void addOrder(Order order){
        orders.put(order.getOrderNumber(), order);
    }

    /**
     * Formats the data within this object for viewing as a single statement.
     * @return formatted string for displaying.
     */
    public String toString(){

        String objectString = "";
        try{

            objectString += "SAXReader";
            objectString += System.getProperty("line.separator");
            objectString += "contents : ";
            objectString += getContents();
            objectString += System.getProperty("line.separator");
            objectString += "orders : ";
            objectString += getOrders();
            objectString += System.getProperty("line.separator");
            objectString += "order : ";
            objectString += getOrder();
            objectString += System.getProperty("line.separator");

            if (objectString.length() > 100000) {
                    objectString = "";
                    throw new MyValidationException("toString",
                            "Output string too long.",
                            MyValidationException.OUTPUT_FAIL,
                            "objectString", objectString);
            }
        } catch(MyValidationException e) {
            e.printStackTrace();
        }
        return objectString;
    }
}
