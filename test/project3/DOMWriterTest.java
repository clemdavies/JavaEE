/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import project.DOMWriter;
import project.Order;

/**
 *
 * @author pig
 */
public class DOMWriterTest extends TestCase {

    private String testPropertyPath = "/project/projectTest.properties";

    public DOMWriterTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Order.setLastOrderNumber(0);
    }


    /**
     * Test of setDocument and getDocument methods, of class DOMWriter.
     */
    public void testSetAndGetDocument() throws Exception{
        setUp();
        DOMWriter instance = new DOMWriter();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        instance.setDocument(document);
        assertEquals(document, instance.getDocument());
    }

    /**
     * Test of setOrders and getOrders methods, of class DOMWriter.
     */
    public void testSetAndGetOrders() throws Exception{
        setUp();
        DOMWriter instance = new DOMWriter();

        Map orders = new Hashtable();
        instance.setOrders(orders);

        assertEquals(orders, instance.getOrders());
    }

    /**
     * Test of load populateFromMemory, of class DOMWriter.
     */
    public void testPopulateFromMemory() throws Exception{
        setUp();
        DOMWriter instance = new DOMWriter();

        int[] hChargeQuant = new int[]{1,4,7,10};
        double[] hChargeAmount = new double[]{4.00,5.00,6.00,7.00};

        Map orders = new Hashtable();
        Order order = new Order();
        order.setCustomerNumber(1111);
        order.setCustomerName("Jones");
        order.setItem("Widget");
        order.setQuantity(7);
        order.setUnitPrice(12.99);
        Order.setHChargeQuantity(hChargeQuant);
        Order.setHChargeAmount(hChargeAmount);
        orders.put(1,order);

        order = new Order();
        order.setCustomerNumber(1212);
        order.setCustomerName("Smith");
        order.setItem("Thingamajig");
        order.setQuantity(12);
        order.setUnitPrice(15.35);
        Order.setHChargeQuantity(hChargeQuant);
        Order.setHChargeAmount(hChargeAmount);
        orders.put(2,order);

        // set orders instance
        instance.setOrders(orders);


        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream(testPropertyPath));

        // clear output file
        try {
            PrintWriter  pr  = new PrintWriter(new BufferedWriter(
                    new FileWriter(
                    properties.getProperty("output.dir") +
                    properties.getProperty("xml.order.output.file"),false )));
            pr.print("<report></report>");
            pr.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        //load document instance
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        instance.setDocument(builder.parse(
                new File(properties.getProperty("output.dir") +
                         properties.getProperty("xml.order.output.file")) ));

        //run method
        instance.populateFromMemory();


        NodeList nodeList;
        Node node;
        String hold;

        String[] resultLines = new String[2];
        resultLines[0] = "Smith1215.35191.2";
        resultLines[1] = "Jones712.9996.93";

        nodeList = instance.getDocument().getElementsByTagName ("order");

        for(int a = 0; a < nodeList.getLength (); a++)
        {
            node = nodeList.item (a);
            hold = node.getTextContent();
            assertEquals(resultLines[a],hold);
        }




    }

    /**
     * Test of outputXMLDocument method, of class DOMWriter.
     */
    public void testOutputXMLDocument() throws Exception{
        setUp();
        DOMWriter instance = new DOMWriter();

        // clear output file
        try {
            PrintWriter  pr  = new PrintWriter(new BufferedWriter(
                    new FileWriter(
                    System.getProperty("output.dir") +
                    System.getProperty("xml.order.output.file"),false )));
            pr.print("<report></report>");
            pr.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }


        int[] hChargeQuant = new int[]{1,4,7,10};
        double[] hChargeAmount = new double[]{4.00,5.00,6.00,7.00};

        Map orders = new Hashtable();
        Order order = new Order();
        order.setCustomerNumber(1111);
        order.setCustomerName("Jones");
        order.setItem("Widget");
        order.setQuantity(7);
        order.setUnitPrice(12.99);
        Order.setHChargeQuantity(hChargeQuant);
        Order.setHChargeAmount(hChargeAmount);
        orders.put(1,order);

        order = new Order();
        order.setCustomerNumber(1212);
        order.setCustomerName("Smith");
        order.setItem("Thingamajig");
        order.setQuantity(12);
        order.setUnitPrice(15.35);
        Order.setHChargeQuantity(hChargeQuant);
        Order.setHChargeAmount(hChargeAmount);
        orders.put(2,order);

        // set orders instance
        instance.setOrders(orders);

        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream(testPropertyPath));
        //load document instance
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        instance.setDocument(builder.parse(
                new File(properties.getProperty("output.dir") +
                         properties.getProperty("xml.order.output.file")) ));

        instance.populateFromMemory();
        instance.outputXMLDocument();


        // load document in to memory
        DocumentBuilderFactory a_factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder a_builder = a_factory.newDocumentBuilder();
        Document document = a_builder.parse(
                new File(System.getProperty("output.dir") +
                         System.getProperty("xml.order.output.file")) );

        NodeList nodeList;
        Node node;
        String hold;

        String[] resultLines = new String[2];
        resultLines[0] = "Smith1215.35191.2";
        resultLines[1] = "Jones712.9996.93";

        nodeList = document.getElementsByTagName ("order");
        for(int a = 0; a < nodeList.getLength (); a++)
        {
            node = nodeList.item (a);
            hold = node.getTextContent ();
            assertEquals(resultLines[a],hold);
        }
    }


    /**
     * Test of toString method, of class DOMWriter.
     */
    public void testToString() throws Exception{
        setUp();
        DOMWriter instance = new DOMWriter();

        String expected = "DOMWriter";
        expected += System.getProperty("line.separator");
        expected += "document : ";
        expected += null;
        expected += System.getProperty("line.separator");
        expected += "orders : ";
        expected += null;
        expected += System.getProperty("line.separator");

        assertEquals(expected, instance.toString());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        expected = "DOMWriter";
        expected += System.getProperty("line.separator");
        expected += "document : ";
        expected += document;
        expected += System.getProperty("line.separator");
        expected += "orders : ";
        expected += new Hashtable();
        expected += System.getProperty("line.separator");

        instance = new DOMWriter();
        instance.setDocument(document);
        instance.setOrders(new Hashtable());

        assertEquals(expected, instance.toString());
    }

}
