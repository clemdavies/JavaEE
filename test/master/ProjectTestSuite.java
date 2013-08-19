
package master;

import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import project1.*;
import project2.*;
import project3.*;


/**
 * TestSuite for project1.
 * @author pig
 */
public class ProjectTestSuite extends TestCase {

    /**
     *
     * @param testName
     */
    public ProjectTestSuite(String testName) {
        super(testName);
    }

    /**
     * Creates a single TestSuite comprised of other TestSuites to be executed.
     * @return TestSuite instance.
     */
    public static Test suite() {
        TestSuite projectSuite = new TestSuite("ProjectTestSuite");
        projectSuite.addTest(createPropertiesSuite());
        projectSuite.addTest(createOrderSuite());
        projectSuite.addTest(createProcessOrderSuite());
        projectSuite.addTest(createConcurrentWriterSuite());
        projectSuite.addTest(createOrderScannerSuite());
        projectSuite.addTest(createDOMWriterSuite());
        projectSuite.addTest(createSAXReaderSuite());
        return projectSuite;
    }

    /**
     * Creates the TestSuite for executing each method in PropertiesTest.
     * @return TestSuite instance of PropertiesTest.
     */
    private static TestSuite createPropertiesSuite(){
        TestSuite propertiesSuite = new TestSuite("PropertiesTestSuite");

        propertiesSuite.addTest(new PropertiesTest("testProperties"));

        return propertiesSuite;
    }

    /**
     * Creates the TestSuite for executing each method in OrderTest.
     * @return TestSuite instance of OrderTest.
     */
    private static TestSuite createOrderSuite(){
        TestSuite orderSuite = new TestSuite("OrderTestSuite");

        orderSuite.addTest(new OrderTest("testSetAndGetLastOrderNumber"));
        orderSuite.addTest(new OrderTest("testOrderNumberIteration"));
        orderSuite.addTest(new OrderTest("testSetAndGetOrderNumber"));
        orderSuite.addTest(new OrderTest("testSetAndGetCustomerName"));
        orderSuite.addTest(new OrderTest("testSetAndGetCustomerNumber"));
        orderSuite.addTest(new OrderTest("testSetGetCalExtendedPrice"));
        orderSuite.addTest(new OrderTest("testSetAndGetItem"));
        orderSuite.addTest(new OrderTest("testSetAndGetQuantity"));
        orderSuite.addTest(new OrderTest("testSetAndGetUnitPrice"));
        orderSuite.addTest(new OrderTest("testSetGetCalHandlingCharge"));
        orderSuite.addTest(new OrderTest("testSetAndGetHChargeQuantity"));
        orderSuite.addTest(new OrderTest("testSetAndGetHChargeAmount"));
        orderSuite.addTest(new OrderTest("testToString"));

        orderSuite.addTest(new OrderTest("testGetTotal"));

        return orderSuite;
    }

    /**
     * Creates the TestSuite for executing each method in ProcessOrderTest.
     * @return TestSuite instance of ProcessOrderTest.
     */
    private static TestSuite createProcessOrderSuite(){
        TestSuite processOrderSuite = new TestSuite("ProcessOrderTestSuite");

        processOrderSuite.addTest(new ProcessOrderTest("testSetAndGetOrderInfo"));
        processOrderSuite.addTest(new ProcessOrderTest("testSetAndGetOrders"));
        processOrderSuite.addTest(new ProcessOrderTest("testLoadProperties"));
        processOrderSuite.addTest(new ProcessOrderTest("testReadOrder"));
        processOrderSuite.addTest(new ProcessOrderTest("testReadCharges"));
        processOrderSuite.addTest(new ProcessOrderTest("testCreateOrder"));
        processOrderSuite.addTest(new ProcessOrderTest("testWriteSequential"));
        processOrderSuite.addTest(new ProcessOrderTest("testToString"));

        processOrderSuite.addTest(new ProcessOrderTest("testSetAndGetOrderScanner"));
        processOrderSuite.addTest(new ProcessOrderTest("testWriteConcurrent"));
        processOrderSuite.addTest(new ProcessOrderTest("testValidateProcessingProperties"));
        processOrderSuite.addTest(new ProcessOrderTest("testStartAndStopScanner"));

        processOrderSuite.addTest(new ProcessOrderTest("testXmlReadOrder"));
        processOrderSuite.addTest(new ProcessOrderTest("testXmlWriteOrder"));

        return processOrderSuite;
    }


    /**
     * Creates the TestSuite for executing each method in ProcessOrderTest.
     * @return TestSuite instance of ProcessOrderTest.
     */
    private static TestSuite createOrderScannerSuite(){
        TestSuite orderScannerSuite = new TestSuite("OrderScannerSuite");

        orderScannerSuite.addTest(new OrderScannerTest("testSetAndIsProgramIsRunning"));
        orderScannerSuite.addTest(new OrderScannerTest("testSetAndGetStartingSize"));
        orderScannerSuite.addTest(new OrderScannerTest("testSetAndGetOutputFile"));
        orderScannerSuite.addTest(new OrderScannerTest("testChangeInFileSize"));
        orderScannerSuite.addTest(new OrderScannerTest("testLogChange"));
        orderScannerSuite.addTest(new OrderScannerTest("testToString"));

        orderScannerSuite.addTest(new OrderScannerTest("testSetAndGetOutputPath"));
        orderScannerSuite.addTest(new OrderScannerTest("testSetOutputPath"));

        return orderScannerSuite;
    }


    /**
     * Creates the TestSuite for executing each method in ProcessOrderTest.
     * @return TestSuite instance of ProcessOrderTest.
     */
    private static TestSuite createConcurrentWriterSuite(){
        TestSuite concurrentWriterSuite = new TestSuite("ConcurrentWriterSuite");

        concurrentWriterSuite.addTest(new ConcurrentWriterTest("testWrite"));
        concurrentWriterSuite.addTest(new ConcurrentWriterTest("testSetAndGetOrder"));
        concurrentWriterSuite.addTest(new ConcurrentWriterTest("testToString"));

        return concurrentWriterSuite;
    }


    /**
     * Creates the TestSuite for executing each method in ProcessOrderTest.
     * @return TestSuite instance of ProcessOrderTest.
     */
    private static TestSuite createSAXReaderSuite(){
        TestSuite SAXReaderSuite = new TestSuite("SAXReaderSuite");

        SAXReaderSuite.addTest(new SAXReaderTest("testSetAndGetContents"));
        SAXReaderSuite.addTest(new SAXReaderTest("testSetAndGetOrders"));
        SAXReaderSuite.addTest(new SAXReaderTest("testSetAndGetOrder"));
        SAXReaderSuite.addTest(new SAXReaderTest("testAddOrder"));
        SAXReaderSuite.addTest(new SAXReaderTest("testStartElement"));
        SAXReaderSuite.addTest(new SAXReaderTest("testEndElement"));
        SAXReaderSuite.addTest(new SAXReaderTest("testCharacters"));
        SAXReaderSuite.addTest(new SAXReaderTest("testToString"));

        return SAXReaderSuite;
    }


    /**
     * Creates the TestSuite for executing each method in ProcessOrderTest.
     * @return TestSuite instance of ProcessOrderTest.
     */
    private static TestSuite createDOMWriterSuite(){
        TestSuite DOMWriterSuite = new TestSuite("DOMWriterSuite");

        DOMWriterSuite.addTest(new DOMWriterTest("testSetAndGetDocument"));
        DOMWriterSuite.addTest(new DOMWriterTest("testSetAndGetOrders"));
        DOMWriterSuite.addTest(new DOMWriterTest("testPopulateFromMemory"));
        DOMWriterSuite.addTest(new DOMWriterTest("testOutputXMLDocument"));
        DOMWriterSuite.addTest(new DOMWriterTest("testToString"));
        
        return DOMWriterSuite;
    }


}
