/**
 * @ (#) Condition.java Jul 09, 2006
 * Project      : TTK HealthCare Services
 * File         : Condition.java
 * Author       : Unni V M
 * Company      : Span Systems Corporation
 * Date Created : Jul 09, 2006
 *
 * @author       : Unni V M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.preauth.ruleengine;

import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Node;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;

/**
 * This Class evaluates the condtion node based on the Operator type and the Operator.
 * Updates the result attribute of the condition node based on the result of operation
 * performed. returns the updated condtion node
 *
 */
public class Condition {
    private static Logger log = Logger.getLogger( Condition.class );

    //declartion of Operator Types as constants used in the Rule
    private static final String strNUMERIC_OPERATOR="numeric";
    private static final String strDATE_OPERATOR="date";
    private static final String strTEXT_OPERATOR="text";
 //added as per regionBased 1284
    private static final String strNOTTEXT_OPERATOR="notText";//1284
    //declarations of operators as Constants used in the Rule
    private static final String strEQUAL="EQ";
    private static final String strNOT_EQUAL="NE";
    private static final String strIN="IN";
    private static final String strNOT_IN="NOTIN";
    private static final String strLESS_THAN="LT";
    private static final String strLESS_THAN_OR_EQUAL="LE";
    private static final String strGREATER_THAN="GT";
    private static final String strGREATER_THAN_OR_EQUAL="GE";

    Node conditionNode = null;

    /**
     * Construtctor for initializing the condtion node
     * @param conditionNode
     */
    public Condition(Node conditionNode)
    {
        this.conditionNode = conditionNode;
    }//end of Condition(Node conditionNode)

    /**
     * This method evaluates the condtion node based on the Operator type and the Operator.
     * Updates the result attribute of the condition node based on the result of operation
     * performed. returns the updated condtion node
     *
     * @return conditionNode updated Condition node
     */
    public Node evaluate()
    {
        if(conditionNode !=null)
        {
            String strValue =conditionNode.valueOf("@value");
            String strOpValue =conditionNode.valueOf("@op");
            String strFieldData =conditionNode.valueOf("@fieldData");

            String strOperator=conditionNode.valueOf("@opType");

            if(strValue.equals("")||strValue.equals("~")||strValue.equals("null"))
            {
                ((Element)(conditionNode)).addAttribute("result","Rule undefined");
                return conditionNode;
            }//end of if(strValue.equals("")||strValue.equals("~")||strValue.equals("null"))

            if(strFieldData.equals("")||strFieldData.equals("~")||strFieldData.equals("null"))
            {
                ((Element)(conditionNode)).addAttribute("result","Data unavailable");
                return conditionNode;
            }//end of if(strFieldData.equals("")||strFieldData.equals("~")||strFieldData.equals("null"))

            try
            {
                boolean blnResult = false;
                if(strOperator.equals(strNUMERIC_OPERATOR))
                {
                    blnResult=doNumericOperation(strFieldData,strValue,strOpValue);
                }//end of if(strOperator.equals(strNUMERIC_OPERATOR))

                else if(strOperator.equals(strDATE_OPERATOR))
                {
                    blnResult=doDateOpertaion(strFieldData,strValue,strOpValue);
                }//end of else if(strOperator.equals(strDATE_OPERATOR))

                else if(strOperator.equals(strTEXT_OPERATOR))
                {
                    blnResult=doTextOperation(strFieldData,strValue,strOpValue);
                }//end of else if(strOperator.equals(strTEXT_OPERATOR))
              else if(strOperator.equals(strNOTTEXT_OPERATOR))//1284 change
                {
                    blnResult=true;
                }//end of else if(strOperator.equals(strTEXT_OPERATOR))
             
                if(blnResult)
                    ((Element)(conditionNode)).addAttribute("result","pass");
                else
                    ((Element)(conditionNode)).addAttribute("result","fail");

            }//try
            catch(NumberFormatException nfe)
            {
                log.error("One of the field is not numeric evaluating condition :"+conditionNode.valueOf("@id"));
                ((Element)(conditionNode)).addAttribute("result","One of the field is not numeric");
            }//end of catch(NumberFormatException nfe)
            catch(TTKException ttkException)
            {
                log.error("Error in Data while evaluating condition :"+conditionNode.valueOf("@id"));
                ((Element)(conditionNode)).addAttribute("result","Error in Data");
            }//end of catch(TTKException ttkException)
        }//end of if(conditionNode !=null)
        return conditionNode;
    }//end of evaluate()


    /**
     * This method will perform the Numerical comparisions on the Operands
     * based on the Operator and returns the Result
     *
     * @param strFieldData String First Operand
     * @param strValue  String Second Operand
     * @param strOpValue String Operator
     * @return blnResult boolean result of the operation performed
     */
    private boolean doNumericOperation(String strFieldData,String strValue
            ,String strOpValue) throws NumberFormatException
    {
        boolean blnResult=false;
        if(strOpValue.equals(strLESS_THAN_OR_EQUAL))
        {
            blnResult=Double.parseDouble(strFieldData) <= Double.parseDouble(strValue);
        }//end of if(strOpValue.equals(strLESS_THAN_OR_EQUAL))

        else if(strOpValue.equals(strEQUAL))
        {
            blnResult=Double.parseDouble(strFieldData) == Double.parseDouble(strValue);
        }//end of else if(strOpValue.equals(strEQUAL))

        else if(strOpValue.equals(strLESS_THAN))
        {
            blnResult=Double.parseDouble(strFieldData) < Double.parseDouble(strValue);
        }//end of else if(strOpValue.equals(strLESS_THAN))

        else if(strOpValue.equals(strGREATER_THAN_OR_EQUAL))
        {
            blnResult=Double.parseDouble(strFieldData) >= Double.parseDouble(strValue);
        }//end of else if(strOpValue.equals(strLESS_THAN))

        //added for koc 1278
        else if(strOpValue.equals(strGREATER_THAN))
        {
        	String[] FieldData = strFieldData.split(" ");
        	String[] Value = strValue.split(" ");
        	strFieldData=FieldData[0];
        	strValue=Value[0];
        	blnResult=Double.parseDouble(strFieldData) > Double.parseDouble(strValue);
        }//end of else if(strOpValue.equals(strGREATER_THAN))
        //added for koc 1278

        else if(strOpValue.equals(strNOT_EQUAL))
        {
            blnResult=Double.parseDouble(strFieldData) != Double.parseDouble(strValue);
        }//end of else if(strOpValue.equals(strNOT_EQUAL))
        return blnResult;
    }//end of doNumericOperation(String strFieldData,String strValue,String strOpValue)

    /**
     * This method will perform the date comparisions on the Operands
     * based on the Operator and returns the Result
     *
     * @param strFieldData String First Operand
     * @param strValue  String Second Operand
     * @param strOpValue String Operator
     * @return blnResult boolean result of the operation performed
     */
    private boolean doDateOpertaion(String strFieldData,String strValue,
            String strOpValue)throws TTKException
    {
        boolean blnResult=false;

        java.util.Date dtDate1 = TTKCommon.getUtilDate(strFieldData);
        java.util.Date dtDate2 = TTKCommon.getUtilDate(strValue);

        if(strOpValue.equals(strLESS_THAN))
        {
            blnResult = dtDate1.compareTo(dtDate2)< 0?true:false;
        }//end of if(strOpValue.equals(strLESS_THAN))

        else if(strOpValue.equals(strLESS_THAN_OR_EQUAL))
        {
            blnResult = dtDate1.compareTo(dtDate2)<=0?true:false;
        }//end of else if(strOpValue.equals(strLESS_THAN_OR_EQUAL))

        else if(strOpValue.equals(strGREATER_THAN))
        {
            blnResult = dtDate1.compareTo(dtDate2)>0?true:false;
        }//end of else  if(strOpValue.equals(strGREATER_THAN))

        else if(strOpValue.equals(strGREATER_THAN_OR_EQUAL))
        {
            blnResult = dtDate1.compareTo(dtDate2)>=0?true:false;
        }//end of else if(strOpValue.equals(strGREATER_THAN_OR_EQUAL))

        else if(strOpValue.equals(strEQUAL))
        {
            blnResult = dtDate1.compareTo(dtDate2)==0?true:false;
        }//end of else if(strOpValue.equals(strEQUAL))

        else if(strOpValue.equals(strNOT_EQUAL))
        {
            blnResult = dtDate1.compareTo(dtDate2)!=0?true:false;
        }//end of else if(strOpValue.equals(strNOT_EQUAL))
        return blnResult;
    }//end of doDateOpertaion(String strFieldData,String strValue,String strOpValue)

    /**
     * This method will perform the text/String operations on the Operands
     * based on the Operator and returns the Result
     *
     * @param strFieldData String First Operand
     * @param strValue  String Second Operand
     * @param strOpValue String Operator
     * @return blnResult boolean result of the operation performed
     */
    private boolean doTextOperation(String strFieldData,String strValue,String strOpValue)
    {
        boolean blnResult=false;
        if(strOpValue.equals(strEQUAL))
        {
            blnResult=strValue.equalsIgnoreCase(strFieldData);
        }//end of if(strOpValue.equals(strEQUAL))

        else if(strOpValue.equals(strNOT_EQUAL))
        {
            blnResult=!strValue.equalsIgnoreCase(strFieldData);
        }//end of else if(strOpValue.equals(strNOT_EQUAL))

        else if(strOpValue.equals(strIN))
        {
            StringTokenizer stFieldValues=new StringTokenizer(strFieldData,"|");
            while(stFieldValues.hasMoreTokens())
            {
                if(strValue.indexOf(stFieldValues.nextToken())!=-1)
                {
                    blnResult=true;
                    break;
                }//end of if(strValue.indexOf(stFieldValues.nextToken())!=-1)
            }//end of while(stFieldValues.hasMoreTokens())
        }//end of else if(strOpValue.equals(strIN))

        else if(strOpValue.equals(strNOT_IN))
        {
            blnResult=true;
            StringTokenizer stFieldValues=new StringTokenizer(strFieldData,"|");
            while(stFieldValues.hasMoreTokens())
            {
                if(strValue.indexOf(stFieldValues.nextToken())!=-1)
                {
                    blnResult=false;
                    break;
                }//end of if(strValue.indexOf(stFieldValues.nextToken())!=-1)
            }//end of while(stFieldValues.hasMoreTokens())
        }//end of else if(strOpValue.equals(strNOT_IN))
        return blnResult;
    }//end of doTextOperation(String strFieldData,String strValue,String strOpValue)
}//end of Condition.java