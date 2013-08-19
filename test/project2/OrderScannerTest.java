
package project2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import junit.framework.TestCase;
import project.Order;
import project.OrderScanner;

/**
 *
 * @author pig
 */
public class OrderScannerTest extends TestCase {

    private String testPropertyPath = "/project/projectTest.properties";

    public OrderScannerTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Order.setLastOrderNumber(0);
    }

    /**
     * Test of setProgramIsRunning and isProgramIsRunning method, of class OrderScanner.
     */
    public void testSetAndIsProgramIsRunning() throws Exception {
        setUp();
        OrderScanner instance = new OrderScanner();
        instance.setProgramIsRunning(true);
        boolean resultProgramIsRunning = true;
        assertEquals(resultProgramIsRunning, instance.isProgramIsRunning());
    }

    /**
     * Test of setStartingSize and getStartingSize method, of class OrderScanner.
     */
    public void testSetAndGetStartingSize() throws Exception{
        setUp();
        OrderScanner instance = new OrderScanner();
        instance.setStartingSize((long) 500);
        assertEquals((long) 500, instance.getStartingSize());
    }

    /**
     * Test of setOutputFile and getOutputFile method, of class OrderScanner.
     */
    public void testSetAndGetOutputFile() throws Exception{
        setUp();
        OrderScanner instance = new OrderScanner();
        instance.setOutputFile(new File("test"));
        assertEquals(new File("test"), instance.getOutputFile());
    }

    /**
     * Test of setOutputPath and getOutputPath method, of class OrderScanner.
     */
    public void testSetAndGetOutputPath() throws Exception{
        setUp();
        OrderScanner instance = new OrderScanner();
        instance.setOutputPath("/path/to/file.txt");
        assertEquals("/path/to/file.txt", instance.getOutputPath());
    }

    /**
     * Test of setOutputPath method, of class OrderScanner.
     */
    public void testSetOutputPath() throws Exception{
        setUp();
        OrderScanner instance = new OrderScanner();

        System.setProperty("xml.enabled", "true");
        String path = System.getProperty("output.dir") + System.getProperty("xml.order.output.file");
        instance.setOutputPath();
        assertEquals(path, instance.getOutputPath());


        System.setProperty("xml.enabled", "false");
        path = System.getProperty("output.dir") + System.getProperty("order.output.file");
        instance.setOutputPath();
        assertEquals(path, instance.getOutputPath());
    }


    /**
     * Test of changeInFileSize method, of class OrderScanner.
     */
    public void testChangeInFileSize() throws Exception {
        setUp();

        OrderScanner instance = new OrderScanner();
        //load test properties
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream(testPropertyPath));
        Properties systemProperties = System.getProperties();
        Set<String> propertySet = properties.stringPropertyNames();
        for (String name : propertySet) {
            systemProperties.setProperty(name,properties.getProperty(name));
        }
        // clear output file
        try {
            PrintWriter  pr  = new PrintWriter(new BufferedWriter(
                    new FileWriter(
                    System.getProperty("output.dir") +
                    System.getProperty("order.output.file"),false )));
            pr.print("");
            pr.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        instance.setOutputFile(new File(System.getProperty("output.dir") +
                    System.getProperty("order.output.file")));

        // test no change.
        assertEquals(false, instance.changeInFileSize());

        // append output file
        try {
            PrintWriter  pr  = new PrintWriter(new BufferedWriter(
                    new FileWriter(
                    System.getProperty("output.dir") +
                    System.getProperty("order.output.file"),false )));
            pr.print("test");
            pr.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        //test a change
        assertEquals(true, instance.changeInFileSize());
    }
    /**
     * Test of logChange method, of class OrderScanner.
     */
    public void testLogChange() throws Exception{
        setUp();

        //load test properties
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream(testPropertyPath));
        Properties systemProperties = System.getProperties();
        Set<String> propertySet = properties.stringPropertyNames();
        for (String name : propertySet) {
            systemProperties.setProperty(name,properties.getProperty(name));
        }

        // clear log file
        try {
            PrintWriter  pr  = new PrintWriter(new BufferedWriter(
                    new FileWriter(
                    System.getProperty("output.dir") +
                    System.getProperty("log.output.file"),false )));
            pr.print("");
            pr.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // create instance
        OrderScanner instance = new OrderScanner();
        instance.logChange();
        String resultLine = "Change to order results : " + new Date();

        // loop over actual lines that should match expected lines
        try{
            BufferedReader  in  = new BufferedReader(new FileReader(
                    properties.getProperty("output.dir") +
                    properties.getProperty("log.output.file")));

            String line = in.readLine();
            assertEquals(resultLine, line);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Test of toString method, of class OrderScanner.
     */
    public void testToString() throws Exception {
        setUp();

        System.setProperty("xml.enabled","true");
        OrderScanner instance = new OrderScanner();

        String resultString = "OrderScanner";
        resultString += System.getProperty("line.separator");
        resultString += "outputPath : ";
        resultString += instance.getOutputPath();
        resultString += System.getProperty("line.separator");
        resultString += "programIsRunning : ";
        resultString += true;
        resultString += System.getProperty("line.separator");

        assertEquals(resultString, instance.toString());


        System.setProperty("xml.enabled","false");
        instance = new OrderScanner();
        resultString = "OrderScanner";
        resultString += System.getProperty("line.separator");
        resultString += "outputPath : ";
        resultString += instance.getOutputPath();
        resultString += System.getProperty("line.separator");
        resultString += "programIsRunning : ";
        resultString += true;
        resultString += System.getProperty("line.separator");

        assertEquals(resultString, instance.toString());

    }
}
