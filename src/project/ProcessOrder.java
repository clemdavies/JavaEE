
package project;

import java.io.*;
import java.util.*;

/**
 * Processes a order input file.
 * Reads input file. Creates Order objects as.
 * @see Order
 * @see OrderDriver
 * @see MyValidationException
 * @author pig
 */
public class ProcessOrder {

    private List orderInfo;
    private Map orders;
    private OrderScanner orderScanner;

    /**
     * Empty Constructor.
     */
    public ProcessOrder(){
        this.orderInfo = new ArrayList();
        this.orders = new Hashtable();
    }

    /**
     * Constructor that loads the passed property file.
     * @param propertyFilePath Path to .property file for program.
     * @throws MyValidationException
     */
    public ProcessOrder(String propertyFilePath) throws MyValidationException{
        this();
        loadProperties(propertyFilePath);
        this.orderScanner = new OrderScanner();
    }

    /**
     * Sets the instance variable orderInfo.
     * @param orderInfo New orderIfno.
     */
    public void setOrderInfo(List orderInfo){
        this.orderInfo = orderInfo;
    }
    /**
     * @return Instance variable orderInfo.
     */
    public List getOrderInfo(){
        return orderInfo;
    }
    /**
     * Sets the instance variable orders.
     * @param orders New orders.
     */
    public void setOrders(Map orders){
        this.orders = orders;
    }
    /**
     * @return Instance variable orders.
     */
    public Map getOrders(){
        return orders;
    }
    /**
     * Sets the instance variable orderScanner.
     * @param orderScanner New orderScanner.
     */
    public void setOrderScanner(OrderScanner orderScanner){
        this.orderScanner = orderScanner;
    }
    /**
     * @return Instance variable orderScanner.
     */
    public OrderScanner getOrderScanner(){
        return orderScanner;
    }

    /**
     * Loads each property file at given path into system properties.
     * @param propertyFilePath path to .property file.
     * @throws MyValidationException
     */
    public void loadProperties(String propertyFilePath) throws MyValidationException{

        if (propertyFilePath.length() < 1 || propertyFilePath.length() > 1000) {
            throw new MyValidationException("loadProperties",
                            "propertyFilePath is invalid",
                            MyValidationException.INPUT_FAIL,
                            "propertyFilePath", propertyFilePath);
        }


        try {
            Properties properties = new Properties();
            properties.load(
                    this.getClass().getResourceAsStream(propertyFilePath));

            Properties systemProperties = System.getProperties();

            Set<String> propertySet = properties.stringPropertyNames();

            for (String name : propertySet) {
                systemProperties.setProperty(name,properties.getProperty(name));
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Using BufferedReader, reads an input file and adds each line to
     * instance variable orderInfo.
     */
    public void readOrder() {

        try {
            BufferedReader  in  = new BufferedReader(new FileReader(
                    System.getProperties().getProperty("input.dir") +
                    System.getProperties().getProperty("order.input.file")));
            while (in.ready()) {
                String lineContent = in.readLine().trim();
                // check maxlength allowed
                if (lineContent.length() > 0) {
                    orderInfo.add(lineContent);
                }
            }
            in.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    /**
     * Reads handling charges from given input file, setting order instance
     * variables hChargeQuantity and hChargeAmount.
     * @throws MyValidationException
     */
    public void readCharges() throws MyValidationException{

        // set constants and test input
        int CHARGES_ROW_COUNT;
        int CHARGES_COLUMN_COUNT;
        try{

            CHARGES_ROW_COUNT = Integer.parseInt(System.getProperty("charges.rows"));
            CHARGES_COLUMN_COUNT = Integer.parseInt(System.getProperty("charges.columns"));
        }catch(NumberFormatException nfe){
            throw new MyValidationException("readCharges",
                            "charges.rows or charges.columns is not set to integer value in properties file",
                            MyValidationException.INPUT_FAIL,
                            "{charges.rows, charges.columns}",
                            new String[]{System.getProperty("charges.rows"),
                                         System.getProperty("charges.columns")});
        }


        int[]     itemRange   = new int[CHARGES_ROW_COUNT];
        double[]  itemCharge  = new double[CHARGES_ROW_COUNT];
        String[]  sarray      = new String[CHARGES_COLUMN_COUNT];
        int       counter     = 0;
        try {
            BufferedReader  br  = new BufferedReader(new FileReader(
                    System.getProperties().getProperty("input.dir") +
                    System.getProperties().getProperty("charges.input.file")));


            while (br.ready()) {
                if (counter >= CHARGES_ROW_COUNT) {
                    throw new MyValidationException("readCharges",
                            "Too many rows of charges.",
                            MyValidationException.INPUT_FAIL,
                            "counter", counter);
                }

                String  line  = br.readLine();
                if (line.split(" ").length == CHARGES_COLUMN_COUNT) {

                    sarray = line.split(" ");

                } else {
                    throw new MyValidationException("readCharges",
                            "Charges file coloumns formatted incorrectly.",
                            MyValidationException.INPUT_FAIL,
                            "s.split(\" \")", line.split(" "));
                }
                if (Integer.parseInt(sarray[0]) > 0
                        && Double.parseDouble(sarray[1]) > 0) {

                    itemRange[counter] = Integer.parseInt(sarray[0]);
                    itemCharge[counter] = Double.parseDouble(sarray[1]);

                }else{
                    throw new MyValidationException("readCharges",
                            "Invalid value types within charges file.",
                            MyValidationException.INPUT_FAIL,
                            "{sarray[0],sarray[1]}", sarray);
                }

                counter++;
            }


        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NumberFormatException nfe) {
            throw new MyValidationException("readCharges",
                    "Invalid value types within charges file.",
                    MyValidationException.INPUT_FAIL,
                    "{sarray[0],sarray[1]}", sarray);
        }

        Order.setHChargeQuantity(itemRange);
        Order.setHChargeAmount(itemCharge);
    }


    /**
     * Using information stored in orderInfo, creates a new order object
     * and populates it.
     * @throws MyValidationException
     */
    public void createOrder() throws MyValidationException {

        for (int i = 0; i < orderInfo.size(); i++) {
            Order     order       = new Order();
            String[]  splitOrder  = ((String) orderInfo.get(i)).split("\\s");

            if (splitOrder.length != 5) {
                throw new MyValidationException("createOrder",
                        "Order input has invalid number of columns.",
                        MyValidationException.INPUT_FAIL,
                        "splitOrder.length", splitOrder.length);
            }

            order.setCustomerName(splitOrder[0]);
            order.setCustomerNumber(Integer.parseInt(splitOrder[1]));
            order.setQuantity(Integer.parseInt(splitOrder[2]));
            order.setUnitPrice(Double.parseDouble(splitOrder[3]));
            order.setItem(splitOrder[4]);
            orders.put((int) order.getOrderNumber(), order);
        }
    }


    /**
     * Iterates over each Order object in orders map and
     * outputs each object to given file.
     */
    public void writeSequential() {
        Iterator  i  = orders.entrySet().iterator();
        try {
            PrintWriter  pr  = new PrintWriter(new BufferedWriter(
                    new FileWriter(
                    System.getProperties().getProperty("output.dir") +
                    System.getProperties().getProperty("order.output.file"),true )));
            while (i.hasNext()) {
                Map.Entry me    = (Map.Entry) i.next();
                Order     order = (Order) me.getValue();
                pr.print(order);
            }
            pr.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    /**
     * Creates new Thread for each Order and initiates writing to output file.
     * @throws MyValidationException
     */
    public void writeConcurrent() throws MyValidationException{

        Iterator  iterate  = getOrders().entrySet().iterator();
        Thread[] threads = new Thread[getOrders().size()];

        for (int index = 0; iterate.hasNext(); index++) {
            Map.Entry me    = (Map.Entry) iterate.next();
            Order     order = (Order) me.getValue();
            ConcurrentWriter writer = new ConcurrentWriter(order);
            threads[index] = new Thread(writer,"Order output thread : " + index);
            threads[index].start();
        }

        // issue command to wait for threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ignore) {
            }
        }
    }


    /**
     * Ensures the values determining processing type in properties file is valid.
     * @throws MyValidationException
     */
    public void validateProcessingProperties() throws MyValidationException{

        //test

        if( Boolean.getBoolean("xml.enabled") ){
            // xml is enabled therefore the rest doesn't matter
            return;
        }


        if (Boolean.getBoolean("sequential.processing") &&
            Boolean.getBoolean("concurrent.processing")) {
            /* Both values are true */

            throw new MyValidationException("validateProcessingProperties",
                        "Both sequentail and concurrent properties are set to true",
                        MyValidationException.INPUT_FAIL,
                        "{sequential.processing, concurrent.processing}",
                        new String[]{
                            System.getProperty("sequential.processing"),
                            System.getProperty("concurrent.processing")} );

        }else if(!(Boolean.getBoolean("sequential.processing")) &&
                 !(Boolean.getBoolean("concurrent.processing"))){
            /* both are false or invalid booleans */

            throw new MyValidationException("validateProcessingProperties",
                        "Both sequentail and concurrent properties are false or invalid booleans",
                        MyValidationException.INPUT_FAIL,
                        "{sequential.processing, concurrent.processing}",
                        new String[]{
                            System.getProperty("sequential.processing"),
                            System.getProperty("concurrent.processing")} );
        }
        /* else one is a valid boolean set to true */



    }


    /**
     * Starts a thread running an instance of OrderScanner.
     */
    public void startScanner(){
        Thread scannerThread = new Thread(getOrderScanner(), "Detect changes to order results.");
        scannerThread.start();
    }

    /**
     * Calls allStop method on orderScanner object.
     */
    public void stopScanner(){
        getOrderScanner().allStop();
    }


    /**
     * Reads orders in through xml document then sets to instance.
     */
    public void xmlReadOrder(){
        SAXReader reader = new SAXReader();
        reader.load();
        setOrders(reader.getOrders());
    }

    /**
     * Runs load method on DOMWriter with current orders object, which outputs
     * an XML document representing all orders in memory.
     * @throws MyValidationException
     */
    public void xmlWriteOrder() throws MyValidationException{
        DOMWriter domWriter = new DOMWriter(getOrders());
        domWriter.load();
    }


    /**
     * Start of program, determines running conditions from properties and
     * runs appropriate methods for reading orders in then writing orders out.
     * Supports flat-file and XML input/output.
     * Supports concurrent and sequential flat-file input/output.
     * @throws MyValidationException
     */
    public void run() throws MyValidationException{

        validateProcessingProperties();

        startScanner();

        readCharges();

        if (Boolean.getBoolean("xml.enabled")) {
            xmlReadOrder();
            xmlWriteOrder();
        }else{
            readOrder();
            createOrder();
            if(Boolean.getBoolean("sequential.processing")){
                writeSequential();
            }else if(Boolean.getBoolean("concurrent.processing")){
                writeConcurrent();
            }
        }

        stopScanner();
    }

    /**
     * Display this object.
     * @return string String representing this object.
     */
    public String toString(){
        String objectString = "";
        try{

            objectString += "ProcessOrder";
            objectString += System.getProperty("line.separator");
            objectString += "orderInfo : ";
            objectString += getOrderInfo();
            objectString += System.getProperty("line.separator");
            objectString += "orders : ";
            objectString += getOrders();
            objectString += System.getProperty("line.separator");
            objectString += "orderScanner : ";
            objectString += getOrderScanner();
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