
package project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used for implementing within a thread to detect changes to the size
 * of the order output file.
 * @see Runnable
 * @see MyValidationException
 * @see Order
 * @author pig
 */
public class OrderScanner implements Runnable{

    private boolean programIsRunning;
    private long startingSize;
    private File outputFile;
    private String outputPath;

    /**
     * Empty constructor sets programmingIsRunning to true.
     */
    public OrderScanner(){
        this.programIsRunning = true;
        setOutputPath();
    }

    /**
     * @return the programIsRunning
     */
    public boolean isProgramIsRunning() {
        return programIsRunning;
    }

    /**
     * @param programIsRunning the programIsRunning to set
     */
    public void setProgramIsRunning(boolean programIsRunning) {
        this.programIsRunning = programIsRunning;
    }


    /**
     * @return the startingSize
     */
    public long getStartingSize() {
        return startingSize ;
    }

    /**
     * @param startingSize  the startingSize to set
     */
    public void setStartingSize(long startingSize ) {
        this.startingSize  = startingSize ;
    }

    /**
     * @return the outputFile
     */
    public File getOutputFile() {
        return outputFile;
    }

    /**
     * @param outputFile the outputFile to set
     */
    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }


    /**
     * Calls orderScan().
     */
    public void run(){
        orderScan();
    }


    /**
     * Monitors the output file for a change in file size.
     * Stops when allStop() is called from outside, or change is found.
     */
    public void orderScan(){
        setOutputFile(new File(getOutputPath()));
        setStartingSize(getOutputFile().length());
        while (isProgramIsRunning()){
            if (changeInFileSize()) {
                logChange();
                allStop();
            }
        }
    }

    /**
     * Checks current file size against starting file size.
     * @return if file size has changed.
     */
    public boolean changeInFileSize(){
        long currentSize = getOutputFile().length();
        if (currentSize > getStartingSize()) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * Outputs a single line to log file with current date and time.
     */
    public void logChange(){

        String path = System.getProperties().getProperty("output.dir") +
                        System.getProperties().getProperty("log.output.file");
        PrintWriter pr = null;
        try {
              pr = new PrintWriter(new BufferedWriter(
                        new FileWriter( path ,true )));
              pr.println("Change to order results : " + new Date());
              pr.close();
        } catch (IOException ex) {
            Logger.getLogger(ConcurrentWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Sets programingIsRunning to false, halting execution of orderScan().
     */
    public synchronized void allStop()
    {
        setProgramIsRunning(false);
    }

    /**
     * Display this object.
     * @return String representing this object.
     */
    public String toString(){
        String objectString = "";
        try{

            objectString += "OrderScanner";
            objectString += System.getProperty("line.separator");
            objectString += "outputPath : ";
            objectString += getOutputPath();
            objectString += System.getProperty("line.separator");
            objectString += "programIsRunning : ";
            objectString += isProgramIsRunning();
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

    /**
     * @return the outputPath
     */
    public String getOutputPath() {
        return outputPath;
    }

    /**
     * @param outputPath the outputPath to set
     */
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * sets the output path to monitor for changes based on whether xml enabled.
     */
    public void setOutputPath() {
        String path = System.getProperty("output.dir");
        if (Boolean.getBoolean("xml.enabled")) {
            path += System.getProperty("xml.order.output.file");
        }else{
            path += System.getProperty("order.output.file");
        }
        setOutputPath(path);
    }

}
