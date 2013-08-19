
package project1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import project.MyValidationException;
import project.Order;
import project.OrderScanner;
import project.ProcessOrder;

/**
 * Test class for testing ProcessOrder class.
 * @author pig
 */
public class ProcessOrderTest extends TestCase {

    private String testPropertyPath = "/project/projectTest.properties";

    /**
     *
     * @param testName
     */
    public ProcessOrderTest(String testName) {
        super(testName);
    }

    /**
     * Resets the static variable of Order lastOrderNumber to 0;
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception{
        super.setUp();
        Order.setLastOrderNumber(0);
    }

    /**
     * Test of setOrderInfo and getOrderInfo method, of class ProcessOrder.
     */
    public void testSetAndGetOrderInfo() throws Exception {
        setUp();
        List orderInfo = new ArrayList();
        ProcessOrder instance = new ProcessOrder();
        instance.setOrderInfo(orderInfo);
        assertEquals(orderInfo,instance.getOrderInfo());
    }


    /**
     * Test of setOrders and getOrders method, of class ProcessOrder.
     */
    public void testSetAndGetOrders() throws Exception {
        setUp();
        Map orders = new Hashtable();
        ProcessOrder instance = new ProcessOrder();
        instance.setOrders(orders);
        assertEquals(orders,instance.getOrders());
    }

    /**
     * Test of loadProperties method, of class ProcessOrder.
     * @throws Exception
     * @throws IOException
     */
    public void testLoadProperties() throws Exception, IOException{
        setUp();
        Properties sysProp = System.getProperties();
        ProcessOrder instance = new ProcessOrder();
        instance.loadProperties(testPropertyPath);

        assertEquals(sysProp.getProperty("input.dir"),
                "/home/pig/Dropbox/semester5/java/project/input/");

        assertEquals(sysProp.getProperty("output.dir"),
                "/home/pig/Dropbox/semester5/java/project/output/");

        assertEquals(sysProp.getProperty("order.input.file"),
                "orderinTest.txt");

        assertEquals(sysProp.getProperty("charges.input.file"),
                "chargesTest.dat");

        assertEquals(sysProp.getProperty("order.output.file"),
                "orderResultTest.txt");

        assertEquals(sysProp.getProperty("log.output.file"),
                "orderLogTest.txt");

        assertEquals(sysProp.getProperty("charges.columns"),
                "2");

        assertEquals(sysProp.getProperty("charges.rows"),
                "4");
    }

    /**
     * Test of readOrder method, of class ProcessOrder.
     */
    public void testReadOrder() throws Exception {
        setUp();
        ProcessOrder instance = new ProcessOrder(testPropertyPath);
        instance.readOrder();

        ArrayList list = new ArrayList();
        list.add("Jones 1111 5 12.00 Widget");
        list.add("Williams 1112 7 10.00 Thing");

        assertEquals(list.size(),instance.getOrderInfo().size());
        assertEquals(list,instance.getOrderInfo());

    }

    /**
     * Test of readCharges method, of class ProcessOrder.
     * @throws Exception
     */
    public void testReadCharges() throws Exception {
        setUp();
        ProcessOrder instance = new ProcessOrder(testPropertyPath);

        // sets values to testOrder.hCharge* as defined in properties file.
        instance.readCharges();

        double[] hChargeAmount = new double[]{4.0,5.0,6.0,7.0};
        int[] hChargeQuantity = new int[]{1,4,7,10};

        assertEquals(Arrays.toString(hChargeAmount),
                     Arrays.toString(Order.getHChargeAmount()));

        assertEquals(Arrays.toString(hChargeQuantity),
                     Arrays.toString(Order.getHChargeQuantity()));
    }

    /**
     * Test of createOrder method, of class ProcessOrder.
     * @throws Exception
     */
    public void testCreateOrder() throws Exception {
        setUp();
        ProcessOrder instance = new ProcessOrder(testPropertyPath);
        instance.readOrder();
        instance.createOrder();

        Map orders = instance.getOrders();

        Set<Integer> keySet = orders.keySet();

        int key1 = 1;
        int key2 = 2;

        assertEquals(2, keySet.size());
        assertTrue(keySet.contains(key1));
        assertTrue(keySet.contains(key2));

        Order order1 = (Order)orders.get(key1);
        assertEquals("Jones",order1.getCustomerName());
        assertEquals(1111,order1.getCustomerNumber());
        assertEquals(5,order1.getQuantity());
        assertEquals(12.00,order1.getUnitPrice());
        assertEquals("Widget",order1.getItem());

        Order order2 = (Order)orders.get(key2);
        assertEquals("Williams",order2.getCustomerName());
        assertEquals(1112,order2.getCustomerNumber());
        assertEquals(7,order2.getQuantity());
        assertEquals(10.00,order2.getUnitPrice());
        assertEquals("Thing",order2.getItem());
    }

    /**
     * Test of writeSequential method, of class ProcessOrder.
     * @throws Exception
     */
    public void testWriteSequential() throws Exception {
        setUp();
        ProcessOrder instance = new ProcessOrder(testPropertyPath);

        // clear output file
        try {
            PrintWriter  pr  = new PrintWriter(new BufferedWriter(
                    new FileWriter(
                    System.getProperties().getProperty("output.dir") +
                    System.getProperties().getProperty("order.output.file"),false )));
            pr.print("");
            pr.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }


        instance.readOrder();
        instance.createOrder();
        instance.writeSequential();

        String[] resultLines = new String[15];
        resultLines[0] = "";
        resultLines[1] = "Customer: Williams";
        resultLines[2] = "Item Ordered: Thing";
        resultLines[3] = "Unit Price: $10.0";
        resultLines[4] = "Total: $70.0";
        resultLines[5] = "Plus a $6.0 processing charge";
        resultLines[6] = "Grand Total: $76.0";
        resultLines[7] = "";
        resultLines[8] = "Customer: Jones";
        resultLines[9] = "Item Ordered: Widget";
        resultLines[10] = "Unit Price: $12.0";
        resultLines[11] = "Total: $60.0";
        resultLines[12] = "Plus a $5.0 processing charge";
        resultLines[13] = "Grand Total: $65.0";
        resultLines[14] = "";



        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream(testPropertyPath));

        try{
            BufferedReader  in  = new BufferedReader(new FileReader(
                    properties.getProperty("output.dir") +
                    properties.getProperty("order.output.file")));
            String actualFull = "";
            String testFull = "";
            String line = "";
            for (int i = 0; i < 15 && in.ready(); i++) {
                line = in.readLine();
                actualFull += line;
                testFull += resultLines[i];
                assertEquals(resultLines[i], line);
            }
            assertEquals(testFull, actualFull);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

    /**
     * Test of toString method, of class ProcessOrder.
     * @throws Exception
     */
    public void testToString() throws Exception{
        setUp();
        ProcessOrder instance = new ProcessOrder(testPropertyPath);

        String resultString = "ProcessOrder";
        resultString += System.getProperty("line.separator");
        resultString += "orderInfo : ";
        resultString += new ArrayList();
        resultString += System.getProperty("line.separator");
        resultString += "orders : ";
        resultString += new Hashtable();
        resultString += System.getProperty("line.separator");
        resultString += "orderScanner : ";
        resultString += new OrderScanner();
        resultString += System.getProperty("line.separator");

        assertEquals(resultString, instance.toString());

        List testOrderInfo = new ArrayList();
        testOrderInfo.add(5);
        testOrderInfo.add(7);

        Map testOrders = new Hashtable();
        testOrders.put("one", 1);
        testOrders.put("three", 3);

        instance.setOrderInfo(testOrderInfo);
        instance.setOrders(testOrders);

        resultString = "ProcessOrder";
        resultString += System.getProperty("line.separator");
        resultString += "orderInfo : ";
        resultString += testOrderInfo;
        resultString += System.getProperty("line.separator");
        resultString += "orders : ";
        resultString += testOrders;
        resultString += System.getProperty("line.separator");
        resultString += "orderScanner : ";
        resultString += new OrderScanner();
        resultString += System.getProperty("line.separator");

        assertEquals(resultString, instance.toString());
    }

    /**
     * Test of setAndGetOrderScanner method, of class ProcessOrder.
     * @throws Exception
     */
    public void testSetAndGetOrderScanner() throws Exception{
        setUp();
        ProcessOrder instance = new ProcessOrder(testPropertyPath);
        OrderScanner orderScanner = new OrderScanner();
        instance.setOrderScanner(orderScanner);
        assertEquals(orderScanner,instance.getOrderScanner());
    }

    /**
     * Test of writeConcurrent method, of class ProcessOrder.
     * @throws Exception
     */
    public void testWriteConcurrent() throws Exception{
        setUp();
        ProcessOrder instance = new ProcessOrder(testPropertyPath);

        // clear output file
        try {
            PrintWriter  pr  = new PrintWriter(new BufferedWriter(
                    new FileWriter(
                    System.getProperties().getProperty("output.dir") +
                    System.getProperties().getProperty("order.output.file"),false )));
            pr.print("");
            pr.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        instance.readOrder();
        instance.createOrder();
        instance.writeConcurrent();



        String[] resultLines1 = new String[15];

        resultLines1[0] = "";
        resultLines1[1] = "Customer: Williams";
        resultLines1[2] = "Item Ordered: Thing";
        resultLines1[3] = "Unit Price: $10.0";
        resultLines1[4] = "Total: $70.0";
        resultLines1[5] = "Plus a $6.0 processing charge";
        resultLines1[6] = "Grand Total: $76.0";
        resultLines1[7] = "";
        resultLines1[8] = "Customer: Jones";
        resultLines1[9] = "Item Ordered: Widget";
        resultLines1[10] = "Unit Price: $12.0";
        resultLines1[11] = "Total: $60.0";
        resultLines1[12] = "Plus a $5.0 processing charge";
        resultLines1[13] = "Grand Total: $65.0";
        resultLines1[14] = "";

        String[] resultLines2 = new String[15];
        resultLines2[0] = "";
        resultLines2[1] = "Customer: Jones";
        resultLines2[2] = "Item Ordered: Widget";
        resultLines2[3] = "Unit Price: $12.0";
        resultLines2[4] = "Total: $60.0";
        resultLines2[5] = "Plus a $5.0 processing charge";
        resultLines2[6] = "Grand Total: $65.0";
        resultLines2[7] = "";
        resultLines2[8] = "Customer: Williams";
        resultLines2[9] = "Item Ordered: Thing";
        resultLines2[10] = "Unit Price: $10.0";
        resultLines2[11] = "Total: $70.0";
        resultLines2[12] = "Plus a $6.0 processing charge";
        resultLines2[13] = "Grand Total: $76.0";
        resultLines2[14] = "";



        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream(testPropertyPath));

        try{
            BufferedReader  in  = new BufferedReader(new FileReader(
                    properties.getProperty("output.dir") +
                    properties.getProperty("order.output.file")));
            String actualFull = "";
            String testFull = "";
            String line = "";
            for (int i = 0; i < 15 && in.ready(); i++) {
                line = in.readLine();
                actualFull += line;

                if (resultLines1[i].equals(line)) {
                    testFull += resultLines1[i];
                }else if (resultLines2[i].equals(line)) {
                    testFull += resultLines2[i];
                }else{
                    fail(resultLines1[i] + " and " + resultLines2[i] + " do not equal " + line );
                }

            }
            assertEquals(testFull, actualFull);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }


    }

    /**
     * Test of validateProcessingProperties method, of class ProcessOrder.
     * @throws Exception
     */
    public void testValidateProcessingProperties() throws Exception{
        setUp();
        ProcessOrder instance = new ProcessOrder(testPropertyPath);


        // set xml.enabled to false

        System.setProperty("xml.enabled","false");

        System.setProperty("sequential.processing","true");
        System.setProperty("concurrent.processing","true");
        try{
            instance.validateProcessingProperties();
            fail("Expected an exception for method validateProcessingProperties() with both values set to true.");
        }catch(MyValidationException e){
            //expected
        }

        System.setProperty("sequential.processing","false");
        System.setProperty("concurrent.processing","false");
        try{
            instance.validateProcessingProperties();
            fail("Expected an exception for method validateProcessingProperties() with both values set to false.");
        }catch(MyValidationException e){
            //expected
        }

        System.setProperty("sequential.processing","aa");
        System.setProperty("concurrent.processing","aa");
        try{
            instance.validateProcessingProperties();
            fail("Expected an exception for method validateProcessingProperties() with both values set to aa.");
        }catch(MyValidationException e){
            //expected
        }

        System.setProperty("sequential.processing","true");
        System.setProperty("concurrent.processing","false");
        try{
            instance.validateProcessingProperties();
        }catch(MyValidationException e){
            fail("Caught an exception when shouldn't for method"+
                    " validateProcessingProperties() with sequential set to true.");
        }

        System.setProperty("sequential.processing","false");
        System.setProperty("concurrent.processing","true");
        try{
            instance.validateProcessingProperties();
        }catch(MyValidationException e){
            fail("Caught an exception when shouldn't for method"+
                    " validateProcessingProperties() with concurrent set to true.");
        }


        // set xml.enabled to true

        System.setProperty("xml.enabled","true");

        System.setProperty("sequential.processing","true");
        System.setProperty("concurrent.processing","true");
        try{
            instance.validateProcessingProperties();
        }catch(MyValidationException e){
            fail("Didn't expect an exception for method validateProcessingProperties() with both values set to true.");
        }

        System.setProperty("sequential.processing","false");
        System.setProperty("concurrent.processing","false");
        try{
            instance.validateProcessingProperties();
        }catch(MyValidationException e){
            fail("Didn't expect an exception for method validateProcessingProperties() with both values set to false.");
        }

        System.setProperty("sequential.processing","aa");
        System.setProperty("concurrent.processing","aa");
        try{
            instance.validateProcessingProperties();
        }catch(MyValidationException e){
            fail("Didn't expect an exception for method validateProcessingProperties() with both values set to aa.");
        }

        // valid settings
        System.setProperty("sequential.processing","true");
        System.setProperty("concurrent.processing","false");
        try{
            instance.validateProcessingProperties();
        }catch(MyValidationException e){
            fail("Caught an exception when shouldn't for method"+
                    " validateProcessingProperties() with sequential set to true.");
        }

        System.setProperty("sequential.processing","false");
        System.setProperty("concurrent.processing","true");
        try{
            instance.validateProcessingProperties();
        }catch(MyValidationException e){
            fail("Caught an exception when shouldn't for method"+
                    " validateProcessingProperties() with concurrent set to true.");
        }

    }

    /**
     * Test of startScanner method, of class ProcessOrder.
     * @throws Exception
     */
    public void testStartAndStopScanner() throws Exception{

        setUp();
        ProcessOrder instance = new ProcessOrder(testPropertyPath);

        instance.startScanner();

        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();

        Set<Thread> mapThreads = allStackTraces.keySet();

        for (Thread thread : mapThreads) {
            if(thread.getName().equals("Detect changes to order results.")){
                assertEquals(thread.isAlive(), true);
                instance.stopScanner();
                thread.join();
                assertEquals(thread.isAlive(), false);
                break;
            }
        }

    }

    /**
     * Test of xmlReadOrder method, of class ProcessOrder.
     */
    public void testXmlReadOrder() throws Exception {
        setUp();
        ProcessOrder instance = new ProcessOrder(testPropertyPath);


        Map orders = new Hashtable();
        Order order = new Order();
        order.setCustomerNumber(1111);
        order.setCustomerName("Jones");
        order.setItem("Widget");
        order.setQuantity(7);
        order.setUnitPrice(12.99);
        orders.put(1,order);

        order = new Order();
        order.setCustomerNumber(1212);
        order.setCustomerName("Smith");
        order.setItem("Thingamajig");
        order.setQuantity(12);
        order.setUnitPrice(15.35);
        orders.put(2,order);


        instance.xmlReadOrder();



        Iterator  expectedIterate  = orders.entrySet().iterator();
        Iterator  instanceIterate  = instance.getOrders().entrySet().iterator();
        for (int index = 0; expectedIterate.hasNext(); index++) {
            Map.Entry expectedME = (Map.Entry) expectedIterate.next();
            Order expectedOrder = (Order) expectedME.getValue();

            Map.Entry instanceME = (Map.Entry) instanceIterate.next();
            Order instanceOrder = (Order) instanceME.getValue();

            assertEquals(expectedOrder.toString(), instanceOrder.toString());
        }




    }


    /**
     * Test of xmlReadCharges method, of class ProcessOrder.
     */
    /*
    public void testXmlReadCharges() throws Exception {
        setUp();
        ProcessOrder instance = new ProcessOrder(testPropertyPath);

        instance.xmlReadOrder();
        instance.xmlReadCharges();




        double[] hChargeAmount = new double[]{4.0,5.0,6.0,7.0};
        int[] hChargeQuantity = new int[]{1,4,7,10};


        Iterator  instanceIterate  = instance.getOrders().entrySet().iterator();
        for (int index = 0; instanceIterate.hasNext(); index++) {

            Map.Entry instanceME = (Map.Entry) instanceIterate.next();
            Order instanceOrder = (Order) instanceME.getValue();

            assertEquals(Arrays.toString(hChargeAmount),
                         Arrays.toString(instanceOrder.getHChargeAmount()));

            assertEquals(Arrays.toString(hChargeQuantity),
                         Arrays.toString(instanceOrder.getHChargeQuantity()));

        }




    }
    */

    /**
     * Test of xmlWriteOrder method, of class ProcessOrder.
     * Is only method calls, test for presence  of method only.
     */
    public void testXmlWriteOrder() throws Exception {
        setUp();
        ProcessOrder instance = new ProcessOrder(testPropertyPath);

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

        instance.readCharges();
        instance.xmlReadOrder();
        instance.xmlWriteOrder();



        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(
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


}
