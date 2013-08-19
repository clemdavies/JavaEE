
package project1;

import junit.framework.TestCase;
import project.MyValidationException;
import project.Order;

/**
 * Test class for testing Order Class.
 * @author pig
 */
public class OrderTest extends TestCase {

    /**
     *
     * @param testName
     */
    public OrderTest(String testName) {
        super(testName);
    }

    /**
     * Resets the static variable of Order.lastOrderNumber to 0;
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception{
        super.setUp();
        Order.setLastOrderNumber(0);
    }

    /**
     * Test of setLastOrderNumber and getLastOrderNumber method, of class Order.
     * @throws Exception
     */
    public void testSetAndGetLastOrderNumber() throws Exception {
        setUp();
        Order.setLastOrderNumber(3);
        assertEquals(3, Order.getLastOrderNumber());
        Order.setLastOrderNumber(1);
        assertEquals(1, Order.getLastOrderNumber());
        Order.setLastOrderNumber(0);
        assertEquals(0, Order.getLastOrderNumber());
    }

    /**
     * Test of Order constructor that iterates the lastOrderNumber static
     * variable on execution, of class Order.
     * @throws Exception
     */
    public void testOrderNumberIteration() throws Exception {
        setUp();
        Order instance = new Order();
        assertEquals(1, instance.getOrderNumber());
        instance = new Order();
        assertEquals(2, instance.getOrderNumber());
        instance = new Order();
        instance = new Order();
        assertEquals(4, instance.getOrderNumber());
    }

    /**
     * Test of setOrderNumber and getOrderNumber method, of class Order.
     * @throws Exception
     */
    public void testSetAndGetOrderNumber() throws Exception {
        setUp();
        int orderNumber = 3;
        Order instance = new Order();
        instance.setOrderNumber(orderNumber);
        assertEquals(orderNumber, instance.getOrderNumber());
    }

    /**
     * Test of setCustomerName method, of class Order.
     * @throws Exception
     */
    public void testSetAndGetCustomerName() throws Exception {
        setUp();
        String name = "Clem";
        Order instance = new Order();
        instance.setCustomerName(name);
        assertEquals(name, instance.getCustomerName());
    }

    /**
     * Test of setCustomerNumber method, of class Order.
     * @throws Exception
     */
    public void testSetAndGetCustomerNumber() throws Exception {
        setUp();
        int number = 12;
        Order instance = new Order();
        instance.setCustomerNumber(number);
        assertEquals(number, instance.getCustomerNumber());
    }

    /**
     * Test of setExtendedPrice method, of class Order.
     * Test of getExtendedPrice method, of class Order.
     * Test of calExtendedPrice method, of class Order.
     * Due to dependence within Order class for setExtendedPrice,
     * getExtendedPrice and calExtendedPrice methods.
     * All tests can be accomplished in one method.
     * @throws Exception
     */
    public void testSetGetCalExtendedPrice() throws Exception {
        setUp();
        Order instance = new Order();
        instance.setQuantity(0);
        instance.setUnitPrice(0.00);

        instance.calExtendedPrice();
        assertEquals(0.00, instance.getExtendedPrice());

        instance = new Order();
        instance.setQuantity(3);
        instance.setUnitPrice(2.00);
        instance.calExtendedPrice();
        assertEquals(6.00, instance.getExtendedPrice());

        instance = new Order();
        instance.setQuantity(7);
        instance.setUnitPrice(3.00);
        instance.calExtendedPrice();
        assertEquals(21.00, instance.getExtendedPrice());
    }

    /**
     * Test of setItem method, of class Order.
     * @throws Exception
     */
    public void testSetAndGetItem() throws Exception {
        setUp();
        String item = "Widget";
        Order instance = new Order();
        instance.setItem(item);
        assertEquals(item, instance.getItem());
    }

    /**
     * Test of setQuantity method, of class Order.
     * @throws Exception
     */
    public void testSetAndGetQuantity() throws Exception {
        setUp();
        int quant = 3;
        Order instance = new Order();
        instance.setQuantity(quant);
        assertEquals(quant, instance.getQuantity());
    }

    /**
     * Test of setUnitPrice method, of class Order.
     * @throws Exception
     */
    public void testSetAndGetUnitPrice() throws Exception {
        setUp();
        double uPrice = 20.0;
        Order instance = new Order();
        instance.setUnitPrice(uPrice);
        assertEquals(uPrice, instance.getUnitPrice());
    }

    /**
     * Test of setHandlingCharge method, of class Order.
     * Test of getHandlingCharge method, of class Order.
     * Test of calHandlingCharge method, of class Order.
     * Due to dependence within Order class for setHandlingCharge,
     * getHandlingCharge and calHandlingCharge methods.
     * All tests can be accomplished in one method.
     * @throws MyValidationException
     * @throws Exception
     */
    public void testSetGetCalHandlingCharge() throws MyValidationException,
            Exception {
        setUp();
        Order instance = new Order();
        Order.setHChargeQuantity(new int[]{1});
        Order.setHChargeAmount(new double[]{2.00});
        instance.setQuantity(1);
        assertEquals(2.00, instance.getHandlingCharge());

        instance = new Order();
        Order.setHChargeQuantity(new int[]{1,3});
        Order.setHChargeAmount(new double[]{2.00,3.00});
        instance.setQuantity(3);
        assertEquals(3.00, instance.getHandlingCharge());

        instance = new Order();
        Order.setHChargeQuantity(new int[]{1,3});
        Order.setHChargeAmount(new double[]{2.00,3.00});
        instance.setQuantity(0);
        assertEquals(2.00, instance.getHandlingCharge());

        instance = new Order();
        Order.setHChargeQuantity(new int[]{1,3});
        Order.setHChargeAmount(new double[]{2.00,3.00});
        instance.setQuantity(2);
        assertEquals(2.00, instance.getHandlingCharge());
    }

    /**
     * Test of setHChargeQuantity and getHChargeQuantity methods, of class Order.
     * @throws Exception
     */
    public void testSetAndGetHChargeQuantity() throws Exception {
        setUp();
        int[] hChargeQuant = new int[]{1,2};
        Order.setHChargeQuantity(hChargeQuant);
        assertEquals(hChargeQuant, Order.getHChargeQuantity());
    }

    /**
     * Test of setHChargeAmount and getHChargeAmount methods, of class Order.
     * @throws Exception
     */
    public void testSetAndGetHChargeAmount() throws Exception {
        setUp();
        double[] hChargeAmount = new double[]{1.00,2.00,4.00};
        Order.setHChargeAmount(hChargeAmount);
        assertEquals(hChargeAmount, Order.getHChargeAmount());
    }


    /**
     * Test of getTotal method, of class Order.
     * @throws Exception
     */
    public void testGetTotal() throws Exception {
        setUp();
        Order instance = new Order();

        //total = (quantity * unitPrice) + handlingCharge
        instance.setQuantity(1);
        instance.setUnitPrice(1.00);
        int[] hChargeQuantity = {1,2,3};
        Order.setHChargeQuantity(hChargeQuantity);
        double[] hChargeAmount = {1.00,1.00,1.00};
        Order.setHChargeAmount(hChargeAmount);
        // 1 * 1 + 1 = 1
        assertEquals(2.00, instance.getTotal());

        instance.setQuantity(2);
        instance.setUnitPrice(4.00);
        int[] hChargeQuantity2 = {1,2,3};
        Order.setHChargeQuantity(hChargeQuantity2);
        double[] hChargeAmount2 = {1.00,2.00,3.00};
        Order.setHChargeAmount(hChargeAmount2);
        // 2 * 4 + 2 = 10
        assertEquals(10.00, instance.getTotal());
    }

    /**
     * Test of toString method, of class Order.
     * @throws Exception
     */
    public void testToString() throws Exception {
        setUp();
        Order instance = new Order();
        instance.setCustomerName("Clem");
        instance.setItem("Widget");
        instance.setQuantity(3);
        instance.setUnitPrice(5.00);
        instance.setExtendedPrice(7.00);
        Order.setHChargeQuantity(new int[]{1,3});
        Order.setHChargeAmount(new double[]{2.00,3.00});
        // handlingCharge = 3.00

        String expectedString = "\nCustomer: Clem";
        expectedString += "\nItem Ordered: Widget";
        expectedString += "\nUnit Price: $" + 5.00;
        expectedString += "\nTotal: $" + 15.00;
        expectedString += "\nPlus a $"+ 3.00;
        expectedString += " processing charge\nGrand Total: $" + 18.00 + "\n";

        assertEquals(expectedString.length(), instance.toString().length());
        assertEquals(expectedString, instance.toString());
    }
}
