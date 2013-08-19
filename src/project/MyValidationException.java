
package project;

import java.util.Arrays;

/**
 * Specialized exception class for project.
 * @see Order
 * @see OrderDriver
 * @see MyValidationException
 * @author pig
 */
public class MyValidationException extends Exception{

    /**
     * Constant that determines output message to say Input error.
     */
    public static int INPUT_FAIL = 0;
    /**
     * Constant that determines output message to say Output error.
     */
    public static int OUTPUT_FAIL = 1;
    /**
     * Holds the passed variable name.
     */
    public String variableName;
    /**
     * Holds the passed variable value.
     */
    public String variableValue;
    /**
     * Holds the passed method name.
     */
    public String methodName;
    /**
     * Holds a constant value of OUTPUT_FAIL or INPUT_FAIL.
     */
    private int failType;

    /**
     * Constructor with String.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValueString
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, String inVariableValueString ) {
        super(strInMessage);
        this.methodName = inMethodName;
        this.failType = inputOutputFail;
        this.variableName = inVariableName;
        this.variableValue = inVariableValueString;
    }
    /**
     * Constructor with String array.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValueString
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, String[] inVariableValueString ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,Arrays.toString(inVariableValueString));
    }


    /**
     * Constructor with char array.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, char[] inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,Arrays.toString(inVariableValue));
    }
    /**
     * Constructor with char.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, char inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,String.valueOf(inVariableValue));
    }


    /**
     * Constructor with int array.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, int[] inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,Arrays.toString(inVariableValue));
    }
    /**
     * Constructor with int.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, int inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,String.valueOf(inVariableValue));
    }


    /**
     * Constructor with short array.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, short[] inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,Arrays.toString(inVariableValue));
    }
    /**
     * Constructor with short.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, short inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,String.valueOf(inVariableValue));
    }


    /**
     * Constructor with long array.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, long[] inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,Arrays.toString(inVariableValue));
    }
    /**
     * Constructor with long.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, long inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,String.valueOf(inVariableValue));
    }


    /**
     * Constructor with float array.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, float[] inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,Arrays.toString(inVariableValue));
    }
    /**
     * Constructor with float.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, float inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,String.valueOf(inVariableValue));
    }


    /**
     * Constructor with double array.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, double[] inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,Arrays.toString(inVariableValue));
    }
    /**
     * Constructor with double.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, double inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,String.valueOf(inVariableValue));
    }

    /**
     * Constructor with boolean array.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, boolean[] inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,Arrays.toString(inVariableValue));
    }
    /**
     * Constructor with boolean.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, boolean inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,String.valueOf(inVariableValue));
    }


    /**
     * Constructor with byte array.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, byte[] inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,Arrays.toString(inVariableValue));
    }
    /**
     * Constructor with byte.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, byte inVariableValue ) {
        this(inMethodName,strInMessage,inputOutputFail,inVariableName,String.valueOf(inVariableValue));
    }



    /**
     * Constructor with Object array.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, Object[] inVariableValue ) {
        super(strInMessage);
        this.methodName = inMethodName;
        this.failType = inputOutputFail;
        this.variableName = inVariableName;
        String inVariableValueString = null;

        if (inVariableValue != null) {
            inVariableValueString = Arrays.toString(inVariableValue);
        }

        this.variableValue = inVariableValueString;
    }
    /**
     * Constructor with Object.
     * @param inMethodName
     * @param strInMessage
     * @param inputOutputFail
     * @param inVariableName
     * @param inVariableValue
     */
    public MyValidationException(String inMethodName,String strInMessage,
            int inputOutputFail, String inVariableName, Object inVariableValue ) {
        super(strInMessage);
        this.methodName = inMethodName;
        this.failType = inputOutputFail;
        this.variableName = inVariableName;
        String inVariableValueString = null;

        if (inVariableValue != null) {
            inVariableValueString = inVariableValue.toString();
        }

        this.variableValue = inVariableValueString;
    }

    /**
     * @return failType.
     */
    public int getFailType() {
        return this.failType;
    }

    /**
     * @return variableName.
     */
    public String getVariableName() {
        return this.variableName;
    }

    /**
     * @return variableValue.
     */
    public String getVariableValue() {
        return this.variableValue;
    }

    /**
     *
     * @return methodName.
     */
    public String getMethodName(){
        return this.methodName;
    }
    /**
     * Formats all information collected within the object.
     * @return String representation of this object.
     */
    public String toString()
    {
        String stringOut;
        stringOut = System.getProperty("line.separator");
        stringOut += this.getMessage();
        stringOut += System.getProperty("line.separator");
        stringOut += "Method: ";
        stringOut += this.methodName;
        stringOut += System.getProperty("line.separator");

        if (this.failType == OUTPUT_FAIL) {
            stringOut += "Failure in:  OUTPUT ";
        } else {
            stringOut += "Failure in:  INPUT ";
        }

        stringOut += System.getProperty("line.separator");
        stringOut += "Variable name:  ";
        stringOut += this.variableName;
        stringOut += System.getProperty("line.separator");
        stringOut += "Variable value:  ";
        stringOut += this.variableValue;
        stringOut += System.getProperty("line.separator");
        return stringOut;
    }


}
