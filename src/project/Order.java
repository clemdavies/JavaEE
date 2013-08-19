
package project;

/**
 * Holds all information regarding to a single order.
 *
 * @see Order
 * @see OrderDriver
 * @see MyValidationException
 * @author pig
 */
public class Order {

    static private int lastOrderNumber;

    private int orderNumber;
    private String customerName;
    private int customerNumber;
    private double extendedPrice;
    private String item;
    private int quantity;
    private double unitPrice;
    private double handlingCharge;

    private static int[] hChargeQuantity;
    private static double[] hChargeAmount;

    /**
     * Constructor that iterates static variable lastOrderNumber by 1.
     */
    public Order(){
        this.orderNumber = Order.lastOrderNumber + 1;
        Order.lastOrderNumber = orderNumber;
    }

    /**
     * Sets static variable lastOrderNumber.
     * @param lastOrderNumber new lastOrderNumber.
     */
    static public void setLastOrderNumber(int lastOrderNumber){
        Order.lastOrderNumber = lastOrderNumber;
    }

    /**
     * @return static variable lastOrderNumber.
     */
    static public int getLastOrderNumber(){
        return Order.lastOrderNumber;
    }

    /**
     * Sets instance variable orderNumber.
     * @param orderNumber new orderNumber.
     */
    public void setOrderNumber(int orderNumber){
        this.orderNumber = orderNumber;
    }

    /**
     * @return instance variable orderNumber.
     */
    public int getOrderNumber(){
        return this.orderNumber;
    }

    /**
     * Sets instance variable customerName.
     * @param customerName new customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    /**
     * @return Instance variable customerName.
     */
    public String getCustomerName() {
        return this.customerName;
    }


    /**
     * Sets instance variable customerNumber.
     * @param customerNumber new customerNumber.
     */
    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }


    /**
     * @return Instance variable customerNumber.
     */
    public int getCustomerNumber() {
        return this.customerNumber;
    }


    /**
     * Sets instance variable extendedPrice.
     * @param extendedPrice new extendedPrice.
     */
    public void setExtendedPrice(double extendedPrice) {
        this.extendedPrice = extendedPrice;
    }


    /**
     * @return Instance variable extendedPrice.
     */
    public double getExtendedPrice() {
        calExtendedPrice();
        return this.extendedPrice;
    }


    /**
     * Sets instance variable item.
     * @param item new item.
     */
    public void setItem(String item) {
        this.item = item;
    }


    /**
     * @return Instance variable item.
     */
    public String getItem() {
        return this.item;
    }


    /**
     * Sets instance variable quantity.
     * @param quantity new quantity.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    /**
     * @return Instance variable quantity.
     */
    public int getQuantity() {
        return this.quantity;
    }


    /**
     * Sets instance variable unitPrice.
     * @param unitPrice new unitPrice.
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }


    /**
     * @return Instance variable unitPrice.
     */
    public double getUnitPrice() {
        return this.unitPrice;
    }


    /**
     * Sets instance variable handlingCharge.
     * @param handlingCharge new handlingCharge.
     */
    public void setHandlingCharge(double handlingCharge) {
        this.handlingCharge = handlingCharge;
    }


    /**
     * Calls calHandlingCharge before returning handlingCharge.
     * @return Instance variable handlingCharge.
     * @throws MyValidationException
     */
    public double getHandlingCharge() throws MyValidationException{
        calHandlingCharge();
        return this.handlingCharge;
    }


    /**
     * Sets static variable hChargeQuantity.
     * @param hChargeQuantity new hChargeQuantity.
     */
    public static void setHChargeQuantity(int[] hChargeQuantity) {
        Order.hChargeQuantity = hChargeQuantity;
    }


    /**
     * @return Static variable hChargeQuantity.
     */
    public static int[] getHChargeQuantity() {
        return Order.hChargeQuantity;
    }


    /**
     * Sets static variable hChargeAmount.
     * @param hChargeAmount
     */
    public static void setHChargeAmount(double[] hChargeAmount) {
       Order.hChargeAmount = hChargeAmount;
    }


    /**
     * @return Static variable hChargeAmount.
     */
    public static double[] getHChargeAmount() {
        return Order.hChargeAmount;
    }


    /**
     * Calculates then sets the extended price of the order.
     * That is multiplying quantity by unitPrice.
     */
    public void calExtendedPrice() {
        setExtendedPrice(this.quantity * this.unitPrice);
    }


    /**
     * Calculates then sets the handling charge of the order.
     * That is finding from the list of charges already set, where the
     * order's quantity is equal to or greater than a certain charge.
     * Will at least apply the lowest handling charge possible.
     * @throws MyValidationException
     */
    public void calHandlingCharge() throws MyValidationException{
        double  charge  = 0;

        if (this.hChargeQuantity == null || this.hChargeAmount == null) {
            setHandlingCharge(charge);
            return;
        }


        for (int i = 0; i < this.hChargeQuantity.length; i++) {
            if (this.quantity < this.hChargeQuantity[i]) {
                if (this.quantity>0 && this.hChargeAmount[i-1] > 0) {
                    charge = this.hChargeAmount[i - 1];
                } else {
                    charge = this.hChargeAmount[0];
                }
                break;
            }
            if (this.hChargeAmount[i] > 0) {
                charge = this.hChargeAmount[i];
            }
        }

        if ( charge <= 0 ) {
            throw new MyValidationException("calHandlingCharge",
                    "Handling charge is invalid",
                    MyValidationException.OUTPUT_FAIL,
                    "charge", charge);
        }
        setHandlingCharge(charge);
    }


    /**
     * Calculates total by adding extendedPrice to handlingCharge.
     * @return calculation of total cost.
     * @throws MyValidationException
     */
    public double getTotal() throws MyValidationException{
        //total = (quantity * unitPrice) + handlingCharge
        double total = getExtendedPrice() + getHandlingCharge();
        return total;
    }

    /**
     * Formats the data within this object for viewing as a single statement.
     * @return formatted string for displaying.
     */
    public String toString(){
        // didn't check output of this method

        String displayString = "";
        try{
            displayString += "\nCustomer: " + getCustomerName();
            displayString += "\nItem Ordered: " + getItem();
            displayString += "\nUnit Price: $" + getUnitPrice();
            displayString += "\nTotal: $" + getExtendedPrice();
            displayString += "\nPlus a $" + getHandlingCharge();
            displayString += " processing charge\nGrand Total: $";
            displayString += getTotal();
            displayString += "\n";

            if (displayString.length() > 100000) {
                displayString = "";
                throw new MyValidationException("toString",
                        "Output string too long.",
                        MyValidationException.OUTPUT_FAIL,
                        "displayString", displayString);
            }
        } catch(MyValidationException e) {
            e.printStackTrace();
        }
        return displayString;
    }

 }