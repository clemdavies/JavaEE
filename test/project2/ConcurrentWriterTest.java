
package project2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Set;
import junit.framework.TestCase;
import project.ConcurrentWriter;
import project.Order;

/**
 *
 * @author pig
 */
public class ConcurrentWriterTest extends TestCase {

    private String testPropertyPath = "/project/projectTest.properties";

    public ConcurrentWriterTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Order.setLastOrderNumber(0);
    }

    /**
     * Test of write method, of class ConcurrentWriter.
     */
    public void testWrite() throws Exception {
        setUp();

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


        // create test order
        Order order = new Order();
        order.setCustomerName("Clem");
        order.setItem("Widget");
        order.setQuantity(3);
        order.setUnitPrice(5.00);
        order.setExtendedPrice(7.00);
        Order.setHChargeQuantity(new int[]{1,3});
        Order.setHChargeAmount(new double[]{2.00,3.00});

        //create instance
        ConcurrentWriter instance = new ConcurrentWriter(order);
        instance.write();


        // expected output array.
        String[] resultLines = new String[15];
        resultLines[0] = "";
        resultLines[1] = "Customer: Clem";
        resultLines[2] = "Item Ordered: Widget";
        resultLines[3] = "Unit Price: $5.0";
        resultLines[4] = "Total: $15.0";
        resultLines[5] = "Plus a $3.0 processing charge";
        resultLines[6] = "Grand Total: $18.0";
        resultLines[7] = "";

        // loop over actual lines that should match expected lines
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
     * Test of getOrder method, of class ConcurrentWriter.
     */
    public void testSetAndGetOrder() throws Exception {
        setUp();
        ConcurrentWriter instance = new ConcurrentWriter();

        // create test order
        Order order = new Order();
        order.setCustomerName("Clem");
        order.setItem("Widget");
        order.setQuantity(3);
        order.setUnitPrice(5.00);
        order.setExtendedPrice(7.00);
        Order.setHChargeQuantity(new int[]{1,3});
        Order.setHChargeAmount(new double[]{2.00,3.00});

        instance.setOrder(order);
        assertEquals(order, instance.getOrder());
    }

    /**
     * Test of toString method, of class ConcurrentWriter.
     */
    public void testToString() throws Exception{
        setUp();

        ConcurrentWriter instance = new ConcurrentWriter();
        String resultString = "";
        resultString += "ConcurrentWriter";
        resultString += System.getProperty("line.separator");
        resultString += "Order : ";
        resultString += null;
        resultString += System.getProperty("line.separator");

        // test without setting order instance.
        assertEquals(resultString, instance.toString());

        // create test order
        Order order = new Order();
        order.setCustomerName("Clem");
        order.setItem("Widget");
        order.setQuantity(3);
        order.setUnitPrice(5.00);
        order.setExtendedPrice(7.00);
        Order.setHChargeQuantity(new int[]{1,3});
        Order.setHChargeAmount(new double[]{2.00,3.00});
        instance.setOrder(order);

        resultString = "ConcurrentWriter";
        resultString += System.getProperty("line.separator");
        resultString += "Order : ";
        resultString += order;
        resultString += System.getProperty("line.separator");

        // test with setting order instance.
        assertEquals(resultString, instance.toString());


    }
}
