
package project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used for implementing within a thread to write a single order
 * to the order output file.
 * @see Order
 * @see Runnable
 * @see MyValidationException
 * @author pig
 */
public class ConcurrentWriter implements Runnable {

    private Order order;

    /**
     * Empty constructor.
     */
    public ConcurrentWriter() {
    }

    /**
     * Constructor with Order object parameter.
     * @param order New order
     * @throws MyValidationException
     */
    public ConcurrentWriter(Order order) throws MyValidationException {
        if (!( Order.class.isInstance(order) )) {
            throw new MyValidationException("ConcurrentWriter",
                            "Order object passed is not of Order class",
                            MyValidationException.INPUT_FAIL,"order", order);
        }
        this.order = order;
    }

    /**
     * Calls write().
     */
    public void run() {
        write();
    }


    /**
     * Writes this objects Order object to the output file.
     */
    public void write(){

        String path = System.getProperties().getProperty("output.dir") +
                        System.getProperties().getProperty("order.output.file");
        PrintWriter pr = null;
        try {
              pr = new PrintWriter(new BufferedWriter(
                        new FileWriter( path ,true )));
              pr.print(order);
              pr.close();
        } catch (IOException ex) {
            Logger.getLogger(ConcurrentWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Display this object.
     * @return String representing this object.
     */
    public String toString(){
        String objectString = "";
        try{

            objectString += "ConcurrentWriter";
            objectString += System.getProperty("line.separator");
            objectString += "Order : ";
            objectString += getOrder();
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

}
