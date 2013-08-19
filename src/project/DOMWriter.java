
package project;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Writes an xml representation of the order results.
 * Loads xml order results to memory and appends new results.
 *
 * @see MyValidationException
 * @author pig
 */
public class DOMWriter {

    private Document document;
    private Map orders;

    /**
     * Empty constructor.
     */
    public DOMWriter(){
    }

    /**
     * Constructor.
     * @param orders Hashtable of Order objects.
     * @throws MyValidationException
     */
    public DOMWriter(Map orders) throws MyValidationException {
        if (!( Map.class.isInstance(orders) )) {
            throw new MyValidationException("DOMWriter",
                            "Orders object passed is not of Map class",
                            MyValidationException.INPUT_FAIL,"orders", orders);
        }
        this.orders = orders;
    }

    /**
     * Loads XML output file, appends new orders and writes changes out.
     */
    public void load() {

        setDocument(null);

        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            setDocument(builder.parse( new File(System.getProperty("output.dir") + System.getProperty("xml.order.output.file")) ));

            populateFromMemory();
            outputXMLDocument();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * Creates DOM representation of order objects inside orders instance.
     *
     * @throws MyValidationException
     */
    public void populateFromMemory() throws MyValidationException {

        NodeList nodeList;
        Element orderElement, itemElement;
        Node rootNode, nameNode, quantityNode, priceNode, totalNode;

        nodeList = getDocument().getElementsByTagName("report");
        rootNode = nodeList.item(0);


        Iterator  iterate  = getOrders().entrySet().iterator();
        for (int index = 0; iterate.hasNext(); index++) {
            Map.Entry me    = (Map.Entry) iterate.next();
            Order     order = (Order) me.getValue();

            orderElement = getDocument().createElement("order");
            rootNode.appendChild (orderElement);

            nameNode = document.createElement("name");
            nameNode.setTextContent(order.getCustomerName());
            orderElement.appendChild(nameNode);

            itemElement = document.createElement("item");
            orderElement.appendChild(itemElement);

            quantityNode = document.createElement("quantity");
            quantityNode.setTextContent( String.valueOf(order.getQuantity()) );
            itemElement.appendChild(quantityNode);

            priceNode = document.createElement("price");
            priceNode.setTextContent( String.valueOf(order.getUnitPrice()) );
            itemElement.appendChild(priceNode);

            totalNode = document.createElement("total");
            totalNode.setTextContent(String.valueOf(order.getTotal()));

            orderElement.appendChild(totalNode);
        }
    }

    /**
     * Writes XML document.
     *
     * @throws Exception
     */
    public void outputXMLDocument() throws Exception
    {
        TransformerFactory oTransformerFactory =  TransformerFactory.newInstance();
        Transformer oTransformer = oTransformerFactory.newTransformer();

        DOMSource oDOMSource = new DOMSource(getDocument());
        StreamResult oStreamResult = new StreamResult(new File(
                System.getProperty("output.dir") +
                System.getProperty("xml.order.output.file") ));
        oTransformer.transform(oDOMSource, oStreamResult);
    }

    /**
     * Formats the data within this object for viewing as a single statement.
     * @return formatted string for displaying.
     */
    public String toString() {

        String objectString = "";
        try{

            objectString += "DOMWriter";
            objectString += System.getProperty("line.separator");
            objectString += "document : ";
            objectString += getDocument();
            objectString += System.getProperty("line.separator");
            objectString += "orders : ";
            objectString += getOrders();
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

    /**
     * @return the document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(Document document) {
        this.document = document;
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

}
