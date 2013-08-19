
package project3;

import java.io.CharArrayWriter;
import java.util.Hashtable;
import java.util.Map;
import junit.framework.TestCase;
import org.xml.sax.helpers.AttributesImpl;
import project.Order;
import project.SAXReader;

/**
 *
 * @author pig
 */
public class SAXReaderTest extends TestCase {

    private String testPropertyPath = "/project/projectTest.properties";

    public SAXReaderTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Order.setLastOrderNumber(0);
    }


    /**
     * Test of setContents and getContents methods, of class SAXReader.
     */
    public void testSetAndGetContents() throws Exception{
        setUp();
        SAXReader instance = new SAXReader();

        CharArrayWriter contents = new CharArrayWriter();
        instance.setContents(contents);
        assertEquals(contents, instance.getContents());
    }

    /**
     * Test of setOrders and getOrders methods, of class SAXReader.
     */
    public void testSetAndGetOrders() throws Exception{
        setUp();
        SAXReader instance = new SAXReader();

        Map orders = new Hashtable();
        instance.setOrders(orders);
        assertEquals(orders, instance.getOrders());
    }

    /**
     * Test of setOrder and getOrder methods, of class SAXReader.
     */
    public void testSetAndGetOrder() throws Exception{
        setUp();
        SAXReader instance = new SAXReader();
        Order order = new Order();
        instance.setOrder(order);
        assertEquals(order, instance.getOrder());
    }

    /**
     * Test of addOrder method, of class SAXReader.
     */
    public void testAddOrder() throws Exception{
        setUp();
        SAXReader instance = new SAXReader();

        Order order = new Order();
        int orderNum = order.getOrderNumber();

        instance.setOrders(new Hashtable());
        instance.addOrder(order);
        Order instanceOrder = (Order) instance.getOrders().get(orderNum);

        assertEquals(order, instanceOrder);

    }


    /**
     * Test of startElement method, of class SAXReader.
     */
    public void testStartElement() throws Exception{
        setUp();
        SAXReader instance = new SAXReader();

        instance.setContents( new CharArrayWriter() );
        instance.startElement("namespace", "localname", "qname", new AttributesImpl());
        assertEquals(0, instance.getContents().size());

        Order.setLastOrderNumber(0);
        instance.startElement("namespace", "ORDER", "qname", new AttributesImpl());
        Order.setLastOrderNumber(0);
        Order order = new Order();
        assertEquals(order.toString(), instance.getOrder().toString());

        Order.setLastOrderNumber(0);
        instance.startElement("namespace", "ITEM", "qname", new AttributesImpl());
        Order.setLastOrderNumber(0);
        order = new Order();
        assertEquals(order.toString(), instance.getOrder().toString());

        AttributesImpl attr = new AttributesImpl();
        attr.addAttribute("uri", "localName", "custid", "type", "1000");
        instance.startElement("namespace", "ORDER", "qname", attr);
        assertEquals(1000, instance.getOrder().getCustomerNumber());

        attr = new AttributesImpl();
        attr.addAttribute("uri", "localName", "name", "type", "Widgey didge");
        instance.startElement("namespace", "ITEM", "qname", attr);
        assertEquals("Widgey didge", instance.getOrder().getItem());
    }

    /**
     * Test of endElement method, of class SAXReader.
     */
    public void testEndElement() throws Exception{
        setUp();
        SAXReader instance = new SAXReader();

        instance.setOrder(new Order());
        instance.setOrders(new Hashtable());
        instance.endElement("namespaceURI", "ORDER", "qname");
        assertEquals(1, instance.getOrders().size());
        assertEquals(null, instance.getOrder());

        instance.setOrder(new Order());
        instance.setContents(new CharArrayWriter());
        instance.getContents().append("Joe Someone");
        instance.endElement("namespaceURI", "NAME", "qname");
        assertEquals("Joe Someone", instance.getOrder().getCustomerName());

        instance.setContents(new CharArrayWriter());
        instance.getContents().append("566");
        instance.endElement("namespaceURI", "QUANTITY", "qname");
        assertEquals(566, instance.getOrder().getQuantity());

        instance.setContents(new CharArrayWriter());
        instance.getContents().append("11.123");
        instance.endElement("namespaceURI", "PRICE", "qname");
        assertEquals(11.123, instance.getOrder().getUnitPrice());
    }

    /**
     * Test of characters method, of class SAXReader.
     */
    public void testCharacters() throws Exception{
        setUp();
        SAXReader instance = new SAXReader();

        instance.setContents(new CharArrayWriter());

        char[] ch = "test".toCharArray();
        instance.characters(ch, 0, ch.length);
        assertEquals(ch.length, instance.getContents().size());

        char[] ch2 = "append".toCharArray();
        instance.characters(ch2, 0, ch2.length);
        assertEquals(ch.length + ch2.length, instance.getContents().size());

        instance.getContents().reset();

        char[] ch3 = "".toCharArray();
        instance.characters(ch3, 0, ch3.length);
        assertEquals(0, instance.getContents().size());

    }

    /**
     * Test of toString method, of class SAXReader.
     */
    public void testToString() throws Exception{
        setUp();
        SAXReader instance = new SAXReader();
        String expected = "SAXReader";
        expected += System.getProperty("line.separator");
        expected += "contents : ";
        expected += null;
        expected += System.getProperty("line.separator");
        expected += "orders : ";
        expected += null;
        expected += System.getProperty("line.separator");
        expected += "order : ";
        expected += null;
        expected += System.getProperty("line.separator");

        assertEquals(expected, instance.toString());


        CharArrayWriter charArrayWriter = new CharArrayWriter();
        Map orders = new Hashtable();
        Order order = new Order();

        expected = "SAXReader";
        expected += System.getProperty("line.separator");
        expected += "contents : ";
        expected += charArrayWriter;
        expected += System.getProperty("line.separator");
        expected += "orders : ";
        expected += orders;
        expected += System.getProperty("line.separator");
        expected += "order : ";
        expected += order;
        expected += System.getProperty("line.separator");

        instance.setContents(charArrayWriter);
        instance.setOrders(orders);
        instance.setOrder(order);

        assertEquals(expected, instance.toString());
    }

}
