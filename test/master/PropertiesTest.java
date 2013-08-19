
package master;

import java.io.IOException;
import java.util.Properties;
import junit.framework.TestCase;

/**
 *  Test to ensure properties file contains at a minimum the appropriate keys
 *  and no empty values.
 * @author pig
 */
public class PropertiesTest extends TestCase {

    /**
     *
     * @param testName
     */
    public PropertiesTest(String testName) {
        super(testName);
    }

    /**
     * Tests to ensure each key exists in project.properties.
     * Tests to ensure no value is empty.
     */
    public void testProperties(){

        String path = "/project/project.properties";

        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(path));

            String[] propertyName = new String[]{
                "input.dir",
                "order.input.file",
                "charges.input.file",
                "output.dir",
                "order.output.file",
                "charges.rows",
                "charges.columns",
                "sequential.processing",
                "concurrent.processing",
                "log.output.file",
                "xml.order.input.file",
                "xml.order.output.file",
                "xml.enabled"};

            for(int i = 0 ; i < propertyName.length ; i++) {
                if (properties.getProperty(propertyName[i]).isEmpty()) {
                    fail("Property name holds invalid value.");
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


}
