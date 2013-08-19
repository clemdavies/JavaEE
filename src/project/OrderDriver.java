
package project;


/**
 * Contains main class that runs ProcessOrder.
 * @see Order
 * @see OrderDriver
 * @see MyValidationException
 * @author pig
 */
public class OrderDriver {

    /**
     * Sets the security policy then instantiates and runs ProcessOrder object.
     * @param args expects one param pointing to property file.
     */
    public static void main(String[] args) {

        try{
            if (args[0].isEmpty()) {
                throw new MyValidationException("main",
                        "No argument supplied for property file location",
                        MyValidationException.INPUT_FAIL, "args[0]", args[0]);
            }

            
            System.getProperties().setProperty(
                    "java.security.policy",
                    "/home/pig/Dropbox/semester5/java/project/config/project/mySecurity.policy");
            System.setSecurityManager(new SecurityManager());


            ProcessOrder  process  = new ProcessOrder(args[0]);
            process.run();
        }catch(MyValidationException e){
            e.printStackTrace();
        }
    }
}