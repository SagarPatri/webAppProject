/**
 * @ (#) TTKValidatorUtil.java Feb 20th, 2006
 * Project       : TTK HealthCare Services
 * File          : TTKValidatorUtil.java
 * Author        : Krishna K H
 * Company       : Span Systems Corporation
 * Date Created  : Feb 20th, 2006
 *
 * @author       :  Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.validator;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorUtil;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;

public class TTKValidatorUtil
{
    private static Logger log = Logger.getLogger(TTKValidatorUtil.class );

    /**
     * Compare the two form "date field" with the second form "date field" given in "secondProperty" using "operator"
     *
     * @param bean The bean validation is being performed on.
     * @param va The ValidatorAction that is currently being performed.
     * @param field The Field object associated with the current field being validated.
     * @param errors The ActionMessages object to add errors to if any validation errors occur.
     * @param request Current request object.
     * @return true if meets stated requirements, false otherwise.
     * @throws TTKException
     */

    public static boolean dateCompare(Object bean,ValidatorAction va,Field field, ActionMessages errors,
            Validator validator,HttpServletRequest request) throws TTKException
    {
        String strValue1 = ValidatorUtil.getValueAsString(bean, field.getProperty());   // get the value of the current field
        String strProperty2 = field.getVarValue("secondProperty");                      // get the Form element name for second date field
        String strOperator = field.getVarValue("operator");                             // get the operator for date compare
        String strValue2 ="";
        if(strProperty2.equals("withServerDate"))      //if the field name is "withServerDate"  get the system date else get the filed from form
        {
            java.util.Date dtcurrentTime = new java.util.Date();
            strValue2=TTKCommon.getFormattedDate(dtcurrentTime);
        }//end of if(strProperty2.equals("currentDate"))
        else
        {
            strValue2 = ValidatorUtil.getValueAsString(bean, strProperty2);          // get the value of the second date field
        }//end of else
        log.debug(" in side ttkValidator ==== Operator == "+strOperator);
        //if any one of the date field is null the validation will not happen
        if (!GenericValidator.isBlankOrNull(strValue1) && !GenericValidator.isBlankOrNull(strValue2))
        {
            java.util.Date dtDate1 = TTKCommon.getUtilDate(strValue1);
            java.util.Date dtDate2 = TTKCommon.getUtilDate(strValue2);
            boolean bResult = false;
            try
            {
                if(strOperator.equalsIgnoreCase("lessThan"))
                {
                    bResult = dtDate1.compareTo(dtDate2)< 0?true:false;//(dtDate1.getTime() < dtDate2.getTime());
                }//end of if(strOperator.equalsIgnoreCase("notEqual"))
                else if(strOperator.equalsIgnoreCase("lessThanEqual"))
                {
                    bResult = dtDate1.compareTo(dtDate2)<=0?true:false;//(dtDate1.getTime() <= dtDate2.getTime());
                }//end of else if(strOperator.equalsIgnoreCase("lessThanEqual"))
                else if(strOperator.equalsIgnoreCase("greaterThan"))
                {
                    bResult = dtDate1.compareTo(dtDate2)>0?true:false;//(dtDate1.getTime() > dtDate2.getTime());
                }//end of else if(strOperator.equalsIgnoreCase("greaterThan"))
                else if(strOperator.equalsIgnoreCase("greaterThanEqual"))
                {
                    bResult = dtDate1.compareTo(dtDate2)>=0?true:false;//(dtDate1.getTime() >= dtDate2.getTime());
                }//end of else if(strOperator.equalsIgnoreCase("greaterThanEqual"))
                else if(strOperator.equalsIgnoreCase("Equal"))
                {
                    bResult = dtDate1.compareTo(dtDate2)==0?true:false;//(dtDate1.getTime() == dtDate2.getTime());
                }//end of else if(strOperator.equalsIgnoreCase("Equal"))
                else if(strOperator.equalsIgnoreCase("notEqual"))
                {
                    bResult = dtDate1.compareTo(dtDate2)!=0?true:false;//(dtDate1.getTime()!= dtDate2.getTime());
                }//end of else if(strOperator.equalsIgnoreCase("notEqual"))
                //if the result is false then display the error message
                if(bResult)
                {
                    errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                    return false;
                }//end of if(bResult)
            }//end of try
            catch (Exception e)
            {
                errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                return false;
            }//end of catch
        }//end of if (!GenericValidator.isBlankOrNull(value) && !GenericValidator.isBlankOrNull(value2))
        return true;
    }//end of validateTwoFields(Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request)
    /**
     * Compare the two form "date field" with the second form "date field" given in "secondProperty" using "operator"
     *
     * @param bean The bean validation is being performed on.
     * @param va The ValidatorAction that is currently being performed.
     * @param field The Field object associated with the current field being validated.
     * @param errors The ActionMessages object to add errors to if any validation errors occur.
     * @param request Current request object.
     * @return true if meets stated requirements, false otherwise.
     * @throws TTKException
     */

    public static boolean dependDateCompare(Object bean,ValidatorAction va,Field field, ActionMessages errors,
            Validator validator,HttpServletRequest request) throws TTKException
    {
        String strValue1 = ValidatorUtil.getValueAsString(bean, field.getProperty());   // get the value of the current field
        String strProperty2 = field.getVarValue("secondProperty");                      // get the Form element name for second date field
        String strOperator = field.getVarValue("operator");
        String strOperator3 = field.getVarValue("thirdProperty");// get the operator for date compare
        String strEqualValue = field.getVarValue("equalValue");
        String strValue2 ="";
        if(strProperty2.equals("withServerDate"))      //if the field name is "withServerDate"  get the system date else get the filed from form
        {
            java.util.Date dtcurrentTime = new java.util.Date();
            strValue2=TTKCommon.getFormattedDate(dtcurrentTime);
        }//end of if(strProperty2.equals("currentDate"))
        else
        {
            strValue2 = ValidatorUtil.getValueAsString(bean, strProperty2);          // get the value of the second date field
        }//end of else
        log.debug(" in side ttkValidator ==== Operator == "+strOperator);
        //if any one of the date field is null the validation will not happen
        if (!GenericValidator.isBlankOrNull(strValue1) && !GenericValidator.isBlankOrNull(strValue2))
        {
            java.util.Date dtDate1 = TTKCommon.getUtilDate(strValue1);
            java.util.Date dtDate2 = TTKCommon.getUtilDate(strValue2);
            boolean bResult = false;
            try
            {
                if(strOperator.equalsIgnoreCase("lessThan"))
                {
                    bResult = dtDate1.compareTo(dtDate2)< 0?true:false;//(dtDate1.getTime() < dtDate2.getTime());
                }//end of if(strOperator.equalsIgnoreCase("notEqual"))
                else if(strOperator.equalsIgnoreCase("lessThanEqual"))
                {
                    bResult = dtDate1.compareTo(dtDate2)<=0?true:false;//(dtDate1.getTime() <= dtDate2.getTime());
                }//end of else if(strOperator.equalsIgnoreCase("lessThanEqual"))
                else if(strOperator.equalsIgnoreCase("greaterThan"))
                {
                    bResult = dtDate1.compareTo(dtDate2)>0?true:false;//(dtDate1.getTime() > dtDate2.getTime());
                }//end of else if(strOperator.equalsIgnoreCase("greaterThan"))
                else if(strOperator.equalsIgnoreCase("greaterThanEqual"))
                {
                    bResult = dtDate1.compareTo(dtDate2)>=0?true:false;//(dtDate1.getTime() >= dtDate2.getTime());
                }//end of else if(strOperator.equalsIgnoreCase("greaterThanEqual"))
                else if(strOperator.equalsIgnoreCase("Equal"))
                {
                    bResult = dtDate1.compareTo(dtDate2)==0?true:false;//(dtDate1.getTime() == dtDate2.getTime());
                }//end of else if(strOperator.equalsIgnoreCase("Equal"))
                else if(strOperator.equalsIgnoreCase("notEqual"))
                {
                    bResult = dtDate1.compareTo(dtDate2)!=0?true:false;//(dtDate1.getTime()!= dtDate2.getTime());
                }//end of else if(strOperator.equalsIgnoreCase("notEqual"))
                //if the result is false then display the error message
                if(bResult)
                {
                    errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                    return false;
                }//end of if(bResult)
            }//end of try
            catch (Exception e)
            {
                errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                return false;
            }//end of catch
        }//end of if (!GenericValidator.isBlankOrNull(value) && !GenericValidator.isBlankOrNull(value2))
        return true;
    }//end of validateTwoFields(Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request)

    /**
     * This method is used to compare two times. Times will be compared if dates are equal.
     *
     * @param bean The bean validation is being performed on.
     * @param va The ValidatorAction that is currently being performed.
     * @param field The Field object associated with the current field being validated.
     * @param errors The ActionMessages object to add errors to if any validation errors occur.
     * @param request Current request object.
     * @return true if meets stated requirements, false otherwise.
     * @throws TTKException
     */

    public static boolean timeCompare(Object bean,ValidatorAction va,Field field,ActionMessages errors,
            Validator validator, HttpServletRequest request) throws TTKException
    {
        String strFirstTimeVal = ValidatorUtil.getValueAsString(bean, field.getProperty());
        boolean blnResult=true;
        String strFirstDateProperty=field.getVarValue("firstDate");
        String strFirstDay=field.getVarValue("firstDay");
        String strSecondDateProperty=field.getVarValue("secondDate");
        String strSecondTimeProperty=field.getVarValue("secondTime");
        String strSecondDay=field.getVarValue("secondDay");

        String strCheckTimeFormat="^(([0-9]|[0][0-9]|[0-1][0-2])\\:([0-9]|[0-5][0-9]))$";
        String strOperator = field.getVarValue("operator");     // get the operator for date compare

        String strFirstDtVal="";
        String strSecondDtVal ="";
        String strSecondTimeVal="";
        String strSecondDayVal="";

        strFirstDtVal = ValidatorUtil.getValueAsString(bean, strFirstDateProperty);
        String strFirstDayVal=ValidatorUtil.getValueAsString(bean,strFirstDay);

        strSecondDayVal=ValidatorUtil.getValueAsString(bean,strSecondDay);

        //if the field name is "withServerDate"  get the system date else get the filed from form
        if(strSecondDateProperty.equals("withServerDate"))
        {
            java.util.Date dtcurrentTime = new java.util.Date();
            strSecondDtVal=TTKCommon.getFormattedDate(dtcurrentTime);

        }//end of if(strProperty2.equals("currentDate"))
        else
        {
            strSecondDtVal = ValidatorUtil.getValueAsString(bean, strSecondDateProperty);
            strSecondTimeVal = ValidatorUtil.getValueAsString(bean, strSecondTimeProperty);
            strSecondDayVal=ValidatorUtil.getValueAsString(bean,strSecondDay);
        }//end of else

        if(GenericValidator.isBlankOrNull(strFirstDtVal) || GenericValidator.isBlankOrNull(strSecondDtVal))
        {
            return true; //one of the date is empty, need not compare time
        }//end of if (!GenericValidator.isBlankOrNull(strFirstDtVal) && !GenericValidator.isBlankOrNull(strSecondDtVal))

        if(!GenericValidator.isDate(strFirstDtVal,"dd/MM/yyyy",true) ||
                !GenericValidator.isDate(strSecondDtVal,"dd/MM/yyyy",true))
        {
            return true; //one of the date is not valied, need not compare time
        }//end of if

        if(!strFirstDtVal.equals(strSecondDtVal))
        {
            return true; //dates are not equal need not compare time
        }//end of if(!strFirstDtVal.equals(strSecondDtVal))

        if(GenericValidator.isBlankOrNull(strFirstTimeVal) || GenericValidator.isBlankOrNull(strSecondTimeVal))
        {
            return true; //one of the time field is empty need not compare time
        }//end of if (!GenericValidator.isBlankOrNull(strFirstDtVal) && !GenericValidator.isBlankOrNull(strSecondDtVal

        if(!(strFirstTimeVal.matches(strCheckTimeFormat) && strSecondTimeVal.matches(strCheckTimeFormat)))
        {
            return true; //one of the time to be compared is not in time format, need not compare time
        }//end of if(!(strFirstTimeVal.matches(strCheckTimeFormat) && strSecondTimeVal.matches(strCheckTimeFormat)))

        try
        {
            String [] strSplitFirstTime=strFirstTimeVal.split(":");
            String [] strSplitSecondTime=strSecondTimeVal.split(":");

            long lngFirstTime=getTime(Integer.parseInt(strSplitFirstTime[0]),
                    Integer.parseInt(strSplitFirstTime[1]),0,strFirstDayVal);

            long lngSecondTime=getTime(Integer.parseInt(strSplitSecondTime[0]),
                    Integer.parseInt(strSplitSecondTime[1]),0,strSecondDayVal);

            if(strOperator.equalsIgnoreCase("lessThan"))
            {
                blnResult = (lngFirstTime<lngSecondTime);
            }//end of if(strOperator.equalsIgnoreCase("notEqual"))
            else if(strOperator.equalsIgnoreCase("lessThanEqual"))
            {
                blnResult =(lngFirstTime<=lngSecondTime);
            }//end of else if(strOperator.equalsIgnoreCase("lessThanEqual"))
            else if(strOperator.equalsIgnoreCase("greaterThan"))
            {
                blnResult =(lngFirstTime>lngSecondTime);
            }//end of else if(strOperator.equalsIgnoreCase("greaterThan"))
            else if(strOperator.equalsIgnoreCase("greaterThanEqual"))
            {
                blnResult =(lngFirstTime>=lngSecondTime);
            }//end of else if(strOperator.equalsIgnoreCase("greaterThanEqual"))
            else if(strOperator.equalsIgnoreCase("Equal"))
            {
                blnResult =(lngFirstTime==lngSecondTime);
            }//end of else if(strOperator.equalsIgnoreCase("Equal"))
            else if(strOperator.equalsIgnoreCase("notEqual"))
            {
                blnResult =(lngFirstTime!=lngSecondTime);
            }//end of else if(strOperator.equalsIgnoreCase("notEqual"))

            if(!blnResult)
            {
                errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                return false;
            }//end of if(bResult)
        }//end of try
        catch(Exception exp)
        {
            errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
            return false;
        }//end of catch(Exception exp)
        return true;
    }//end of timeCompare(Object bean,ValidatorAction va,Field field,ActionMessages errors,Validator validator, HttpServletRequest request)

    /**
     * This method will return the given time in seconds
     * @param hours Hour value
     * @param minuts Minute value
     * @param seconds second value
     * @param strDay String AM/PM
     * @return time in seconds
     */
    private static long getTime(int hours,int minuts,int seconds,String strDay)
    {
        long lngSeconds=0;
        if(strDay.equals("PM"))
        {
            if(hours==12)
            {
                lngSeconds=(hours*60*60)+(minuts*60)+seconds;
            }//end of if(hours==12)
            else
            {
                lngSeconds=((hours+12)*60*60)+(minuts*60)+seconds;
            }//end of else

        }//end of if(strDay.equals("PM"))
        else if(strDay.equals("AM"))
        {
            if(hours==12)
            {
                lngSeconds=(minuts*60)+seconds;
            }//end of if(hours==12)
            else
            {
                lngSeconds=(hours*60*60)+(minuts*60)+seconds;
            }//end of else
        }//end of else
        return lngSeconds;
    }//end of getTime(int hours,int minuts,int seconds,String strDay)


    /**
     *  Custom validator for the Domiciliary Treatment Limits screen
     *
     * @param bean The bean validation is being performed on.
     * @param va The ValidatorAction that is currently being performed.
     * @param field The Field object associated with the current field being validated.
     * @param errors The ActionMessages object to add errors to if any validation errors occur.
     * @param request Current request object.
     * @return true if meets stated requirements, false otherwise.
     */

    public static boolean limitInfo (Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request)
    {
        log.debug("          - - - inside limitInfo server side validator ");
//        String strValue = ValidatorUtil.getValueAsString(bean, field.getProperty());   // get the value of the current field
        String strProperty2 = field.getVarValue("subType");                      // get the Form element name for second date field
        String strSubType = ValidatorUtil.getValueAsString(bean, strProperty2);
        String strNumericFormat = field.getVarValue("numberFormat");                      // get the Number format
        ActionMessages actionMessages = new ActionMessages();
        String strCheckZero="^[0]{1,}(\\.[0]{1,})?$";
        ActionMessage actionMessage=null;
        boolean bErrorFound=false;

        String strDomiciliaryTypeID[]=request.getParameterValues("domiciliaryTypeID");
        String strDomiciliaryLimit[]=request.getParameterValues("domiciliaryLimit");
        String strDomHospAmt[]=request.getParameterValues("domHospAmt");
        String strTotalFlyLimit=request.getParameter("totalFlyLimit");
        int iTotalMembers=0;
//        int iPFLCount=0; // counter for Floater type
//        int iPNFCount=0; // counter for Non-Floater type

        if(strDomiciliaryTypeID!=null && strDomiciliaryTypeID.length >0)
        {
            iTotalMembers=strDomiciliaryTypeID.length;
            try
            {
//				for(int i=0;i<iTotalMembers;i++)
//				{
//				if(strDomiciliaryTypeID[i].equals("PFL"))
//				iPFLCount++;
//				else if(strDomiciliaryTypeID[i].equals("PNF"))
//				iPNFCount++;
//				}
                for(int i=0;i<iTotalMembers;i++)
                {
                    
                	if(!strDomHospAmt[i].matches(strNumericFormat))
                    {
                        actionMessages = new ActionMessages();
                        actionMessage = new ActionMessage("error.HospNumberFormat");
                        actionMessages.add("global.error",actionMessage);
                        errors.add(actionMessages);
                        bErrorFound=true;
                        break;
                        //bErrorFound=true;
                    }//end of else if(!strDomHospAmt[i].matches(strNumericFormat))
                	
                	if(strDomiciliaryTypeID[i].equals("PNF"))
                    {
                        if(strDomiciliaryLimit[i]==null || strDomiciliaryLimit[i].equals("")||strDomiciliaryLimit[i].matches(strCheckZero))
                        {
                            actionMessages = new ActionMessages();
                            actionMessage = new ActionMessage("error.LimitRequired");
                            actionMessages.add("global.error",actionMessage);
                            errors.add(actionMessages);
                            bErrorFound=true;break;
                            //  bErrorFound=true;
                        }//end of if(strTotalPremium[i]!=null || !strTotalPremium[i].equals("")||!strTotalPremium[i].equals("0"))
                        else if(!strDomiciliaryLimit[i].matches(strNumericFormat))
                        {
                            actionMessages = new ActionMessages();
                            actionMessage = new ActionMessage("error.LimitNumberFormat");
                            actionMessages.add("global.error",actionMessage);
                            errors.add(actionMessages);
                            bErrorFound=true;
                            break;
                            //bErrorFound=true;
                        }//end of else if(!strDomiciliaryLimit[i].matches(strNumericFormat))
                        /*else if(!strDomHospAmt[i].matches(strNumericFormat))
                        {
                            actionMessages = new ActionMessages();
                            actionMessage = new ActionMessage("error.HospNumberFormat");
                            actionMessages.add("global.error",actionMessage);
                            errors.add(actionMessages);
                            bErrorFound=true;
                            break;
                            //bErrorFound=true;
                        }//end of else if(!strDomHospAmt[i].matches(strNumericFormat))
*/                    }//end of if(strDomiciliaryTypeID[i].equals("PNF"))
                }//end of ffor(int i=0;i<iTotalMembers;i++)

                if(strSubType.equals("PNF"))
                {
                    if(strTotalFlyLimit==null || strTotalFlyLimit.equals("")||strTotalFlyLimit.matches(strCheckZero))
                    {
                        actionMessages = new ActionMessages();
                        actionMessage = new ActionMessage("error.TotalFlyLimitRequired");
                        actionMessages.add("global.error",actionMessage);
                        errors.add(actionMessages);
                        bErrorFound=true;
                    }//end of if(strTotalFlyLimit==null || strTotalFlyLimit.equals("")||strTotalFlyLimit.matches(strCheckZero))
                    else if(!strTotalFlyLimit.matches(strNumericFormat))
                    {
                        actionMessages = new ActionMessages();
                        actionMessage = new ActionMessage("error.TotalFlyLimitFormat");
                        actionMessages.add("global.error",actionMessage);
                        errors.add(actionMessages);
                        bErrorFound=true;
                    }//end of else if(!strTotalFlyLimit.matches(strNumericFormat))
                }//end of if(strSubType.equals("PNF"))

                if(strSubType.equals("PFL"))
                {
                    if(strTotalFlyLimit==null || strTotalFlyLimit.equals("")||strTotalFlyLimit.matches(strCheckZero))
                    {
                        actionMessages = new ActionMessages();
                        actionMessage = new ActionMessage("error.TotalFlyLimitRequired");
                        actionMessages.add("global.error",actionMessage);
                        errors.add(actionMessages);
                        bErrorFound=true;
                    }//end of if(strTotalFlyPremium==null || strTotalFlyPremium.equals("")||strTotalFlyPremium.equals("0"))
                    else if(!strTotalFlyLimit.matches(strNumericFormat))
                    {
                        actionMessages = new ActionMessages();
                        actionMessage = new ActionMessage("error.TotalFlyLimitFormat");
                        actionMessages.add("global.error",actionMessage);
                        errors.add(actionMessages);
                        bErrorFound=true;
                    }//end of else if(!strTotalFlyPremium.matches(strNumericFormat))
                }//end of if(strSubType.equals("PFL"))
            }//end of try
            catch (Exception e)
            {
                errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                return false;
            }//end of catch
        }//end of if(strMemberPolicyTypeID!=null && strMemberPolicyTypeID.length >0)
        else {
        	iTotalMembers=strDomHospAmt.length;
        	try
            {
				for(int i=0;i<iTotalMembers;i++)
                {
                    
                	if(!strDomHospAmt[i].matches(strNumericFormat))
                    {
                        actionMessages = new ActionMessages();
                        actionMessage = new ActionMessage("error.HospNumberFormat");
                        actionMessages.add("global.error",actionMessage);
                        errors.add(actionMessages);
                        bErrorFound=true;
                        break;
                    }//end of else if(!strDomHospAmt[i].matches(strNumericFormat))
                }//end of for(int i=0;i<iTotalMembers;i++)
			}//end of try
            catch (Exception e)
            {
                errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                return false;
            }//end of catch
        }//end of else
        return true;
    }//end of limitInfo

    /**
     *  Custom validator for the Premium Information screen
     *
     * @param bean The bean validation is being performed on.
     * @param va The ValidatorAction that is currently being performed.
     * @param field The Field object associated with the current field being validated.
     * @param errors The ActionMessages object to add errors to if any validation errors occur.
     * @param request Current request object.
     * @return true if meets stated requirements, false otherwise.
     */

    public static boolean premiumInfo (Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request)
    {
//        String strValue = ValidatorUtil.getValueAsString(bean, field.getProperty());   // get the value of the current field
        String strProperty2 = field.getVarValue("subType");                      // get the Form element name for second date field
        String strSubType = ValidatorUtil.getValueAsString(bean, strProperty2);
        String strNumericFormat = field.getVarValue("numberFormat");  
        // get the Number format
        ActionMessages actionMessages = new ActionMessages();
        String strCheckZero="^[0]{1,}(\\.[0]{1,})?$";
        ActionMessage actionMessage=null;
        boolean bErrorFound=false;
        int iPFLCount=0; // counter for Floater type
        int iPNFCount=0; // counter for Non-Floater type
        int iPFNCount=0; // counter for Floater + Non-Floater type
        int iPFRCount=0; // counter for Floater with restriction type
        int iTotalMembers=0;
        int iTotalCancelledMembers=0;   //counter for cancelled members
        //get the parameters required for validation
        String strClearPremiumInfoYN=request.getParameter("clearPremiumInfoYN");
        String strActiveLink=request.getParameter("activeLink");
        log.info("Active Link in Validator Util: "+strActiveLink);
        String strFloaterSumInsured=request.getParameter("floaterSumInsured");
        String strFloaterPremium=request.getParameter("floaterPremium");
        String strTotalFlySumInsured=request.getParameter("totalFlySumInsured");
        String strTotalFlyPremium=request.getParameter("totalFlyPremium");
        String strMemberPolicyTypeID[]=request.getParameterValues("memberPolicyTypeID");
        String strCancelYN[]=request.getParameterValues("cancelYN");
        String strSumInsured[]=request.getParameterValues("totalSumInsured");
       // String strTotalPremium[]=request.getParameterValues("premiumPaid"); comented for pr rata premium cr

        if(strMemberPolicyTypeID!=null && strMemberPolicyTypeID.length >0)
        {
            iTotalMembers=strMemberPolicyTypeID.length;
            try
            {
                boolean blnSumInsuredfound=false;
                for(int i=0;i<iTotalMembers;i++)    //get the count of each member policy type and check if sum insured is present or not
                {
                    if(strCancelYN[i].equals("Y"))
                    {
                        iTotalCancelledMembers++;   //increment counter if member is cancelled
                    }//end of if(strCancelYN[i].equals("Y"))

                    //if member is not cancelled get the count of each policy sub type
                    //check for the presence of sum insured
                    if(strMemberPolicyTypeID[i].equals("PFL"))
                    {
                        if(!(strCancelYN[i].equals("Y")))
                        {
                            iPFLCount++;
                        }//end of if(!(strCancelYN[i].equals("Y")))
                        if(strSumInsured[i]!=null && !(strSumInsured[i].matches(strCheckZero)||strSumInsured[i].equals("")))
                        {
                            blnSumInsuredfound=true;
                        }//end of if(strSumInsured[i]!=null && !(strSumInsured[i].matches(strCheckZero)||strSumInsured[i].equals("")))
                    }//end of if(strMemberPolicyTypeID[i].equals("PFL"))
                    else if(strMemberPolicyTypeID[i].equals("PNF"))
                    {
                        if(!(strCancelYN[i].equals("Y")))
                        {
                            iPNFCount++;
                        }//end of if(!(strCancelYN[i].equals("Y")))
                        if(strSumInsured[i]!=null && !(strSumInsured[i].matches(strCheckZero)||strSumInsured[i].equals("")))
                        {
                            blnSumInsuredfound=true;
                        }//end of if(strSumInsured[i]!=null && !(strSumInsured[i].matches(strCheckZero)||strSumInsured[i].equals("")))
                    }//end of else if(strMemberPolicyTypeID[i].equals("PNF"))
                    else if(strMemberPolicyTypeID[i].equals("PFN"))
                    {
                        if(!(strCancelYN[i].equals("Y")))
                        {
                            iPFNCount++;
                        }//end of if(!(strCancelYN[i].equals("Y")))
                        if(strSumInsured[i]!=null && !(strSumInsured[i].matches(strCheckZero)||strSumInsured[i].equals("")))
                        {
                            blnSumInsuredfound=true;
                        }//end of if(strSumInsured[i]!=null && !(strSumInsured[i].matches(strCheckZero)||strSumInsured[i].equals("")))
                    }//end of else if(strMemberPolicyTypeID[i].equals("PFN"))
                    else if(strMemberPolicyTypeID[i].equals("PFR"))
                    {
                        if(!(strCancelYN[i].equals("Y")))
                        {
                            iPFRCount++;
                        }//end of if(!(strCancelYN[i].equals("Y")))
                        if(strSumInsured[i]!=null && !(strSumInsured[i].matches(strCheckZero)||strSumInsured[i].equals("")))
                        {
                            blnSumInsuredfound=true;
                        }//end of if(strSumInsured[i]!=null && !(strSumInsured[i].matches(strCheckZero)||strSumInsured[i].equals("")))
                    }//end of else if(strMemberPolicyTypeID[i].equals("PFR"))
                }//end of for(int i=0;i<iTotalMembers;i++)

                //check if all the members in policy/enrollment are cancelled and register appropriate message
                if(iTotalMembers==iTotalCancelledMembers)
                {
                    actionMessages = new ActionMessages();
                    actionMessage = new ActionMessage("error.enrollment.premiuminfo.allmemberscancelled");
                    actionMessages.add("global.error",actionMessage);
                    errors.add(actionMessages);
                    bErrorFound=true;
                }//end of if(iTotalMembers==iTotalCancelledMembers)

                //if ClearPremiumInfoYN is selected and sum insured is present register the error message.
                else if(strClearPremiumInfoYN.equals("Y") && blnSumInsuredfound==true)
                {
                    actionMessages = new ActionMessages();
                    actionMessage = new ActionMessage("error.enrollment.premiuminfo.insuredamountnotdeleted");
                    actionMessages.add("global.error",actionMessage);
                    errors.add(actionMessages);
                    bErrorFound=true;
                }//end of if(strClearPremiumInfoYN.equals("Y") && blnSumInsuredfound==true)
                else if(strClearPremiumInfoYN.equals("Y") && (strSubType.equals("PFL")||strSubType.equals("PFN")||strSubType.equals("PFR")))
                {
                    if(strFloaterSumInsured!=null && !(strFloaterSumInsured.matches(strCheckZero)||strFloaterSumInsured.equals("")))
                    {
                        actionMessages = new ActionMessages();
                        actionMessage = new ActionMessage("error.enrollment.premiuminfo.insuredamountnotdeleted");
                        actionMessages.add("global.error",actionMessage);
                        errors.add(actionMessages);
                        bErrorFound=true;
                    }//end of if(strFloaterSumInsured!=null && !(strFloaterSumInsured.matches(strCheckZero)||strFloaterSumInsured.equals("")))
                }//end of else if(strClearPremiumInfoYN.equals("Y") && (strSubType.equals("PFL")||strSubType.equals("PFN")||strSubType.equals("PFR")))

                //if ClearPremiumInfoYN is not selected then validate for the sum insured and premiumAmount.
                if(!strClearPremiumInfoYN.equals("Y"))
                {
                    for(int i=0;i<iTotalMembers;i++)
                    {
                        if(!strCancelYN[i].equals("Y"))     //if member is not cancelled then do validations for him
                        {
                            if(strMemberPolicyTypeID[i].equals("PFL"))  // for member policy subtype floater
                            {
                                if(strSumInsured[i]!=null && !(strSumInsured[i].matches(strCheckZero)||strSumInsured[i].equals("")))
                                {
                                   /* actionMessages = new ActionMessages();
                                    actionMessage = new ActionMessage("error.enrollment.premiuminfo.suminsurednotrequired");
                                    actionMessages.add("global.error",actionMessage);
                                    errors.add(actionMessages);
                                    bErrorFound=true;break;*/
                                }//end of if(strSumInsured[i]!=null || !strSumInsured[i].equals("")||!strSumInsured[i].equals("0"))
                               /* if(strTotalPremium[i]!=null && !(strTotalPremium[i].matches(strCheckZero)||strTotalPremium[i].equals("")))
                                {
                                    actionMessages = new ActionMessages();
                                    actionMessage = new ActionMessage("error.enrollment.premiuminfo.premiumpaidnotrequired");
                                    actionMessages.add("global.error",actionMessage);
                                    errors.add(actionMessages);
                                    bErrorFound=true;break;
                                } comented for pro tata premium CR    *///end of if(strTotalPremium[i]!=null || !strTotalPremium[i].equals("")||!strTotalPremium[i].equals("0"))

                            }//end of if(strSubType.equals("PFL"))
                            else    //for member policy subtype non-floater,floater+non-floater,floater+floaterWithRestriction
                            {
                                if((strSumInsured[i]==null || strSumInsured[i].equals("")||strSumInsured[i].matches(strCheckZero)))
                                {
                                    actionMessages = new ActionMessages();
                                    actionMessage = new ActionMessage("error.enrollment.premiuminfo.suminsuredrequired");
                                    actionMessages.add("global.error",actionMessage);
                                    errors.add(actionMessages);
                                    bErrorFound=true;break;
                                }//end of if(strSumInsured[i]==null || strSumInsured[i].equals("")||strSumInsured[i].equals("0"))
                                else if(!strSumInsured[i].matches(strNumericFormat))
                                {
                                    actionMessages = new ActionMessages();
                                    actionMessage = new ActionMessage("error.enrollment.premiuminfo.suminsurednumberformat");
                                    actionMessages.add("global.error",actionMessage);
                                    errors.add(actionMessages);
                                    bErrorFound=true;break;
                                }//end of else if(!strSumInsured[i].matches(strNumericFormat))
                              /*  if(!strTotalPremium[i].matches(strNumericFormat) && !(strTotalPremium[i].equals("0")))
                                {
                                	actionMessages = new ActionMessages();
                                    actionMessage = new ActionMessage("error.enrollment.premiuminfo.premiumpaidnumberformat");
                                    actionMessages.add("global.error",actionMessage);
                                    errors.add(actionMessages);
                                    bErrorFound=true;break;
                                } comented for pro rata premium cr  *///end of else if(!strTotalPremium[i].matches(strNumericFormat))
                            }//end of else
                        }//end of if(!strCancelYN[i].equals("Y"))
                    }//end of for(int i=0;i<strMemberPolicyTypeID.length;i++)

                    if(strSubType.equals("PFL")||strSubType.equals("PFN")||strSubType.equals("PFR"))
                    {
                        if(iPFLCount > 0 && (strFloaterSumInsured==null || strFloaterSumInsured.equals("")||strFloaterSumInsured.matches(strCheckZero)))
                        {
                            actionMessages = new ActionMessages();
                            actionMessage = new ActionMessage("error.enrollment.premiuminfo.floatersuminsuredrequired");
                            actionMessages.add("global.error",actionMessage);
                            errors.add(actionMessages);
                            bErrorFound=true;
                        }//end of if(strFloaterSumInsured==null || strFloaterSumInsured.equals("")||strFloaterSumInsured.equals("0"))
                        else if(iPFLCount == 0 && (strFloaterSumInsured!=null && !(strFloaterSumInsured.matches(strCheckZero)||strFloaterSumInsured.equals(""))))
                        {
                            actionMessages = new ActionMessages();
                            actionMessage = new ActionMessage("error.enrollment.premiuminfo.floatersuminsurednotrequired");
                            actionMessages.add("global.error",actionMessage);
                            errors.add(actionMessages);
                            bErrorFound=true;
                        }//end ofb else if(iPFLCount == 0 && (strFloaterSumInsured!=null && !(strFloaterSumInsured.matches(strCheckZero)||strFloaterSumInsured.equals(""))))
                        else if(!(strFloaterSumInsured.matches(strCheckZero)|| strFloaterSumInsured.matches(strNumericFormat)))
                        {
                            actionMessages = new ActionMessages();
                            actionMessage = new ActionMessage("error.enrollment.premiuminfo.floatersuminsurednumberformat");
                            actionMessages.add("global.error",actionMessage);
                            errors.add(actionMessages);
                            bErrorFound=true;
                        }//end of else if(!(strFloaterSumInsured.matches(strCheckZero)|| strFloaterSumInsured.matches(strNumericFormat)))

                        if(iPFLCount > 0 && (strFloaterPremium==null || strFloaterPremium.equals("")||strFloaterPremium.matches(strCheckZero)))
                        {
                        	if(!strActiveLink.equals("Corporate Policy")){
                        		actionMessages = new ActionMessages();
                                actionMessage = new ActionMessage("error.enrollment.premiuminfo.floaterpremiumrequired");
                                actionMessages.add("global.error",actionMessage);
                                errors.add(actionMessages);
                                bErrorFound=true;
                        	}//end of if(!strActiveLink.equals("Corporate Policy"))
                        }//end of if(strFloaterPremium==null || strFloaterPremium.equals("")||strFloaterPremium.equals("0"))
                        else if(iPFLCount == 0 && (strFloaterPremium!=null && !(strFloaterPremium.matches(strCheckZero)||strFloaterPremium.equals(""))))
                        {
                            actionMessages = new ActionMessages();
                            actionMessage = new ActionMessage("error.enrollment.premiuminfo.floaterpremiumnotrequired");
                            actionMessages.add("global.error",actionMessage);
                            errors.add(actionMessages);
                            bErrorFound=true;
                        }//end of else if(iPFLCount == 0 && (strFloaterPremium!=null && !(strFloaterPremium.matches(strCheckZero)||strFloaterPremium.equals(""))))
                        else if(!(strFloaterPremium.matches(strCheckZero)||strFloaterPremium.matches(strNumericFormat)))
                        {
                            actionMessages = new ActionMessages();
                            actionMessage = new ActionMessage("error.enrollment.premiuminfo.floaterpremiumnumberformat");
                            actionMessages.add("global.error",actionMessage);
                            errors.add(actionMessages);
                            bErrorFound=true;
                        }//end of else if(!strFloaterPremium.matches(strNumericFormat))
                    }//end of else if(!(strFloaterPremium.matches(strCheckZero)||strFloaterPremium.matches(strNumericFormat)))

                    if(strSubType.equals("PNF")||strSubType.equals("PFN")||strSubType.equals("PFR"))
                    {
                        if(strTotalFlySumInsured==null || strTotalFlySumInsured.equals("")||strTotalFlySumInsured.matches(strCheckZero))
                        {
                            actionMessages = new ActionMessages();
                            actionMessage = new ActionMessage("error.enrollment.premiuminfo.totalflysuminsuredrequired");
                            actionMessages.add("global.error",actionMessage);
                            errors.add(actionMessages);
                            bErrorFound=true;
                        }//end of if(strTotalFlySumInsured==null || strTotalFlySumInsured.equals("")||strTotalFlySumInsured.equals("0"))
                        else if(!strTotalFlySumInsured.matches(strNumericFormat))
                        {
                            actionMessages = new ActionMessages();
                            actionMessage = new ActionMessage("error.enrollment.premiuminfo.totalflysuminsurednumberformat");
                            actionMessages.add("global.error",actionMessage);
                            errors.add(actionMessages);
                            bErrorFound=true;
                        }//end of else if(!strTotalFlySumInsured.matches(strNumericFormat))

                        if(strTotalFlyPremium==null || strTotalFlyPremium.equals("")||strTotalFlyPremium.matches(strCheckZero))
                        {
                        	if(!strActiveLink.equals("Corporate Policy"))
                        	{
	                            actionMessages = new ActionMessages();
	                            actionMessage = new ActionMessage("error.enrollment.premiuminfo.totalflypremiumrequired");
	                            actionMessages.add("global.error",actionMessage);
	                            errors.add(actionMessages);
	                            bErrorFound=true;
                        	}//end of if(!strActiveLink.equals("Corporate Policy"))
                        }//end of if(strTotalFlyPremium==null || strTotalFlyPremium.equals("")||strTotalFlyPremium.equals("0"))
                        else if(!strTotalFlyPremium.matches(strNumericFormat))
                        {
                            actionMessages = new ActionMessages();
                            actionMessage = new ActionMessage("error.enrollment.premiuminfo.totalflypremiumnumberformat");
                            actionMessages.add("global.error",actionMessage);
                            errors.add(actionMessages);
                            bErrorFound=true;
                        }//end of else if(!strTotalFlyPremium.matches(strNumericFormat))
                    }//end of if(strSubType.equals("PNF")||strSubType.equals("PFN")||strSubType.equals("PFR"))
                }//end of if(!strClearPremiumInfoYN.equals("Y"))
                if(bErrorFound)     //if any error found return false
                    return false;
            }//end of try
            catch (Exception e)
            {

                errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                return false;
            }//end of catch
        }//end of if(strMemberPolicyTypeID!=null && strMemberPolicyTypeID.length >0)
        return true;
    }//end of validateTwoFields(Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request)


    /**
     *  Custom validator for the Ailment Details screen
     *
     * @param bean The bean validation is being performed on.
     * @param va The ValidatorAction that is currently being performed.
     * @param field The Field object associated with the current field being validated.
     * @param errors The ActionMessages object to add errors to if any validation errors occur.
     * @param request Current request object.
     * @return true if meets stated requirements, false otherwise.
     */

    public static boolean expValidation(Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request)
    {
        String strField = field.getProperty();   // get the current field
        String strTestFormat = field.getVarValue("test");                      // get the Expression format
        String[] strFieldVal = request.getParameterValues(strField);
        //get the parameters required for validation
        if(strFieldVal!=null)
        {
            for(int i=0;i<strFieldVal.length;i++)
            {
                if(!strFieldVal[i].matches(strTestFormat))
                {
                    errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                    return false;
                }//end of if
            }// end of for
        }//end of if(strFieldVal!=null)
        return true;
    }//end of expValidation(Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request)

    /**
     * This is custom validator for comparing the Amounts, if valid amounts entered
     * for diffent conditions. throws error message if condition is not satisified.
     * @param bean The bean validation is being performed on.
     * @param va The ValidatorAction that is currently being performed.
     * @param field The Field object associated with the current field being validated.
     * @param errors The ActionMessages object to add errors to if any validation errors occur.
     * @param request Current request object.
     * @return true if meets stated requirements, false otherwise.
     * @throws TTKException if any Exception occures
     */
    public static boolean amountCompare(Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request) throws TTKException
    {
        String strProperty2 = field.getVarValue("secondProperty"); // get the Form element name for second Amount field
        String strOperator = field.getVarValue("operator");     //conditional operator to compare
        String strValue1=ValidatorUtils.getValueAsString(bean, field.getProperty());
        String strValue2=ValidatorUtils.getValueAsString(bean,strProperty2);
        //if any one of the field is null then validation will not happen
        if (!GenericValidator.isBlankOrNull(strValue1) && !GenericValidator.isBlankOrNull(strValue2))
        {
            try
            {
                boolean bErrorFound = false;
                BigDecimal bdValue1=TTKCommon.getBigDecimal(strValue1);
                BigDecimal bdValue2=TTKCommon.getBigDecimal(strValue2);
                log.debug("bdValue2  "+bdValue2);
                //if the zero is allowed in second property don't validate
                if(field.getVar("allowSecondPropertyZero")!=null && field.getVarValue("allowSecondPropertyZero").equals("true") && bdValue2.compareTo(TTKCommon.getBigDecimal("0"))==0)
                {
                    bErrorFound = false;
                }//end of if(field.getVar("allowSecondPropertyZero")!=null && field.getVarValue("allowSecondPropertyZero").equals("true") && bdValue2.compareTo(TTKCommon.getBigDecimal("0"))==0)
                else if(bdValue2.compareTo(TTKCommon.getBigDecimal("0"))>0)
                {
                    if(strOperator.equalsIgnoreCase("lessThan"))
                    {
                        bErrorFound = bdValue1.compareTo(bdValue2)< 0?false:true;
                    }//end of if(strOperator.equalsIgnoreCase("lessThan"))
                    else if(strOperator.equalsIgnoreCase("lessThanEqual"))
                    {
                        bErrorFound = bdValue1.compareTo(bdValue2)<=0?false:true;
                    }//end of else if(strOperator.equalsIgnoreCase("lessThanEqual"))
                    else if(strOperator.equalsIgnoreCase("greaterThan"))
                    {
                        bErrorFound = bdValue1.compareTo(bdValue2)>0?false:true;
                    }//end of else if(strOperator.equalsIgnoreCase("greaterThan"))
                    else if(strOperator.equalsIgnoreCase("greaterThanEqual"))
                    {
                        bErrorFound = bdValue1.compareTo(bdValue2)>=0?false:true;
                    }//end of else if(strOperator.equalsIgnoreCase("greaterThanEqual"))
                    else if(strOperator.equalsIgnoreCase("notEqual"))
                    {
                        bErrorFound = bdValue1.compareTo(bdValue2)!=0?true:false;//(dtDate1.getTime()!= dtDate2.getTime());
                    }//end of else if(strOperator.equalsIgnoreCase("notEqual"))
                }//end of else if(bdValue2.compareTo(TTKCommon.getBigDecimal("0"))>0)
                log.debug(" in side if === "+bErrorFound);

                if(bErrorFound) //if error found display the error message
                {
                    errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                    return false;
                }//end of if(bErrorFound)
            }//end of try
            catch (Exception e)
            {

                if(e instanceof NumberFormatException)
                {
                    //need not compare the values as one of the value is not number.
                    return true;
                }//end of if(e instanceof NumberFormatException)
                errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                return false;
            }//end of catch
        }//end of if (!GenericValidator.isBlankOrNull(strValue1) && !GenericValidator.isBlankOrNull(strValue2))
        return true;
    }//end of amountCompare(Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request)
    
    /**
     * This is custom validator for comparing the Amounts, if valid amounts entered
     * for diffent conditions. throws error message if condition is not satisified.
     * @param bean The bean validation is being performed on.
     * @param va The ValidatorAction that is currently being performed.
     * @param field The Field object associated with the current field being validated.
     * @param errors The ActionMessages object to add errors to if any validation errors occur.
     * @param request Current request object.
     * @return true if meets stated requirements, false otherwise.
     * @throws TTKException if any Exception occures
     */
    public static boolean bufferAmountCompare(Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request) throws TTKException
    {
        log.debug("inside bufferAmountCompare method");
    	String strProperty2 = field.getVarValue("availBufferAmt"); // get the Form element name for second Amount field
        String strOperator = field.getVarValue("operatorForBufferAmountCompare");     //conditional operator to compare
        String strValue1=ValidatorUtils.getValueAsString(bean, field.getProperty());
        String strValue2=ValidatorUtils.getValueAsString(bean,strProperty2);
        //if any one of the field is null then validation will not happen
        if (!GenericValidator.isBlankOrNull(strValue1) && !GenericValidator.isBlankOrNull(strValue2))
        {
            try
            {
                boolean bErrorFound = false;
                BigDecimal bdValue1=TTKCommon.getBigDecimal(strValue1);
                BigDecimal bdValue2=TTKCommon.getBigDecimal(strValue2);
                log.debug("bdValue2  "+bdValue2);
                //if the zero is allowed in second property don't validate
                if(field.getVar("allowSecondPropertyZero")!=null && field.getVarValue("allowSecondPropertyZero").equals("true") && bdValue2.compareTo(TTKCommon.getBigDecimal("0"))==0)
                {
                    bErrorFound = false;
                }//end of if(field.getVar("allowSecondPropertyZero")!=null && field.getVarValue("allowSecondPropertyZero").equals("true") && bdValue2.compareTo(TTKCommon.getBigDecimal("0"))==0)
                else if(bdValue2.compareTo(TTKCommon.getBigDecimal("0"))>0)
                {
                    if(strOperator.equalsIgnoreCase("lessThan"))
                    {
                        bErrorFound = bdValue1.compareTo(bdValue2)< 0?false:true;
                    }//end of if(strOperator.equalsIgnoreCase("lessThan"))
                    else if(strOperator.equalsIgnoreCase("lessThanEqual"))
                    {
                        bErrorFound = bdValue1.compareTo(bdValue2)<=0?false:true;
                    }//end of else if(strOperator.equalsIgnoreCase("lessThanEqual"))
                    else if(strOperator.equalsIgnoreCase("greaterThan"))
                    {
                        bErrorFound = bdValue1.compareTo(bdValue2)>0?false:true;
                    }//end of else if(strOperator.equalsIgnoreCase("greaterThan"))
                    else if(strOperator.equalsIgnoreCase("greaterThanEqual"))
                    {
                        bErrorFound = bdValue1.compareTo(bdValue2)>=0?false:true;
                    }//end of else if(strOperator.equalsIgnoreCase("greaterThanEqual"))
                    else if(strOperator.equalsIgnoreCase("notEqual"))
                    {
                        bErrorFound = bdValue1.compareTo(bdValue2)!=0?true:false;//(dtDate1.getTime()!= dtDate2.getTime());
                    }//end of else if(strOperator.equalsIgnoreCase("notEqual"))
                }//end of else if(bdValue2.compareTo(TTKCommon.getBigDecimal("0"))>0)
                else if(bdValue2.compareTo(TTKCommon.getBigDecimal("0"))<0){
                	log.debug("Inside bufferAmountCompare");
                	bErrorFound = true;
                }//end of else if(bdValue2.compareTo(TTKCommon.getBigDecimal("0"))<0)
                log.debug(" in side if === "+bErrorFound);

                if(bErrorFound) //if error found display the error message
                {
                    errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                    return false;
                }//end of if(bErrorFound)
            }//end of try
            catch (Exception e)
            {

                if(e instanceof NumberFormatException)
                {
                    //need not compare the values as one of the value is not number.
                    return true;
                }//end of if(e instanceof NumberFormatException)
                errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                return false;
            }//end of catch
        }//end of if (!GenericValidator.isBlankOrNull(strValue1) && !GenericValidator.isBlankOrNull(strValue2))
        return true;
    }//end of bufferAmountCompare(Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request)

    /**
    * This is custom validator for comparing the Amounts in the request scope of tariff screen in pre-auth, if valid amounts entered
    * for diffent conditions. throws error message if condition is not satisified.
    * @param bean The bean validation is being performed on.
    * @param va The ValidatorAction that is currently being performed.
    * @param field The Field object associated with the current field being validated.
    * @param errors The ActionMessages object to add errors to if any validation errors occur.
    * @param request Current request object.
    * @return true if meets stated requirements, false otherwise.
    * @throws TTKException if any Exception occures
    */
   public static boolean tariffAmountCompare(Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request) throws TTKException
   {
       String strField1 = field.getProperty();
       String[] strFieldVal1 = request.getParameterValues(strField1);
       String strProperty2 = field.getVarValue("secondProperty");
       String[] strFieldVal2 = request.getParameterValues(strProperty2);

       String strOperator = field.getVarValue("operator");     //conditional operator to compare

       if(strFieldVal1!=null)
       {
           for(int i=0;i<strFieldVal1.length;i++)
           {
               //it will not validate if the field is balnk or null
               if (!GenericValidator.isBlankOrNull(strFieldVal1[i]) && !GenericValidator.isBlankOrNull(strFieldVal2[i]))
               {
                   try
                   {
                       boolean bErrorFound = false;
                       BigDecimal bdValue1=TTKCommon.getBigDecimal(strFieldVal1[i]);
                       BigDecimal bdValue2=TTKCommon.getBigDecimal(strFieldVal2[i]);

                       if(bdValue1.compareTo(TTKCommon.getBigDecimal("0"))>0 && bdValue2.compareTo(TTKCommon.getBigDecimal("0"))>0)
                       {
                           if(strOperator.equalsIgnoreCase("lessThan"))
                           {
                               bErrorFound = bdValue1.compareTo(bdValue2)< 0?false:true;
                           }//end of if(strOperator.equalsIgnoreCase("lessThan"))
                           else if(strOperator.equalsIgnoreCase("lessThanEqual"))
                           {
                               bErrorFound = bdValue1.compareTo(bdValue2)<=0?false:true;
                           }//end of else if(strOperator.equalsIgnoreCase("lessThanEqual"))
                           else if(strOperator.equalsIgnoreCase("greaterThan"))
                           {
                               bErrorFound = bdValue1.compareTo(bdValue2)>0?false:true;
                           }//end of else if(strOperator.equalsIgnoreCase("greaterThan"))
                           else if(strOperator.equalsIgnoreCase("greaterThanEqual"))
                           {
                               bErrorFound = bdValue1.compareTo(bdValue2)>=0?false:true;
                           }//end of else if(strOperator.equalsIgnoreCase("greaterThanEqual"))
                           else if(strOperator.equalsIgnoreCase("notEqual"))
                           {
                               bErrorFound = bdValue1.compareTo(bdValue2)!=0?true:false;//(dtDate1.getTime()!= dtDate2.getTime());
                           }//end of else if(strOperator.equalsIgnoreCase("notEqual"))
                       }//end of if(bdValue1.compareTo(TTKCommon.getBigDecimal("0"))>0 && bdValue2.compareTo(TTKCommon.getBigDecimal("0"))>0)
                       if(bErrorFound) //if error found display the error message
                       {
                           errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                           return false;
                       }//end of if(bErrorFound)
                   }//end of try
                   catch (Exception e)
                   {

                       if(e instanceof NumberFormatException)
                       {
                           //need not compare the values as one of the value is not number.
                           return true;
                       }//end of if(e instanceof NumberFormatException)
                       errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                       return false;
                   }//end of catch
               }//end of if (!GenericValidator.isBlankOrNull(strValue1) && !GenericValidator.isBlankOrNull(strValue2))
           }
       }//if(strFieldVal1!=null)
       return true;
   }//end of tariffAmountCompare(Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request)

   /**
    * This is custom validator for comparing the Number, if valid Number entered
    * for diffent conditions. throws error message if condition is not satisified.
    * @param bean The bean validation is being performed on.
    * @param va The ValidatorAction that is currently being performed.
    * @param field The Field object associated with the current field being validated.
    * @param errors The ActionMessages object to add errors to if any validation errors occur.
    * @param request Current request object.
    * @return true if meets stated requirements, false otherwise.
    * @throws TTKException if any Exception occures
    */
   public static boolean configParamCompare(Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request) throws TTKException
   {
       String strProperty2 = field.getVarValue("secondProperty"); // get the Form element name for second Amount field
       String strOperator = field.getVarValue("operator");     //conditional operator to compare
       String strValue1=ValidatorUtils.getValueAsString(bean, field.getProperty());
       String strValue2=ValidatorUtils.getValueAsString(bean,strProperty2);
       //if any one of the field is null then validation will not happen
       if (!GenericValidator.isBlankOrNull(strValue1) && !GenericValidator.isBlankOrNull(strValue2))
       {
           try
           {
               boolean bErrorFound = false;
               Integer itValue1=TTKCommon.getInt(strValue1);
               Integer itValue2=TTKCommon.getInt(strValue2);
               //if the zero is allowed in second property don't validate
               if(field.getVar("allowSecondPropertyZero")!=null && field.getVarValue("allowSecondPropertyZero").equals("true") && itValue2.compareTo(0)==0)//TTKCommon.getInt("0") compareTo(TTKCommon.getBigDecimal("0"))==0
               {
                   bErrorFound = false;
               }//end of if(field.getVar("allowSecondPropertyZero")!=null && field.getVarValue("allowSecondPropertyZero").equals("true") && bdValue2.compareTo(TTKCommon.getBigDecimal("0"))==0)
               else if(itValue2.compareTo(0)>0)
               {
            	   if(strOperator.equalsIgnoreCase("lessThan"))
                   {
                       bErrorFound = itValue1.compareTo(itValue2)< 0?false:true;
                   }//end of if(strOperator.equalsIgnoreCase("lessThan"))
               }//end of else if(itValue2.compareTo(0)>0)
               if(bErrorFound) //if error found display the error message
               {
                   errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
                   return false;
               }//end of if(bErrorFound)
           }//end of try
           catch (Exception e)
           {
               if(e instanceof NumberFormatException)
               {
                   //need not compare the values as one of the value is not number.
                   return true;
               }//end of if(e instanceof NumberFormatException)
               errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
               return false;
           }//end of catch
       }//end of if (!GenericValidator.isBlankOrNull(strValue1) && !GenericValidator.isBlankOrNull(strValue2))
       return true;
   }//end of configParamCompare(Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request)
   
    /**
     * @Description This method checks the following for product/policy rule date entered
     * a) Checks for empty date or not
     * b) Checks for valid date or not
     * @param bean
     * @param va
     * @param field
     * @param errors
     * @param validator
     * @param request
     * @return html string
     */
    /*public  String validatePolicyRuleDate(Object bean,ValidatorAction va,Field field, ActionMessages errors,Validator validator, HttpServletRequest request)
    {
        String strHtml	 			  = "";
        String strValue1			  = ValidatorUtils.getValueAsString(bean, field.getProperty());
        String strScreenType 		  = field.getVarValue("ProductBaseRule");
        ActionMessages actionMessages = null;
        ActionMessage actionMessage   = null;
        boolean bResult=true;
           boolean bValidation=true;

        if(strValue1 !=null && strValue1.length()<=0)
        {
        errors.add(field.getKey(),Resources.getActionMessage(validator,request, va, field));
        bValidation=false;
        }
        else
        {

            //////checking for valid Rule date ///////
            // \d{2}\/\d{2}\/\d{4}
            if  (!strValue1.matches("\\d{2}/\\d{2}/\\d{4}") || strValue1.equalsIgnoreCase("00/00/0000"))
            {
                actionMessages = new ActionMessages();
                actionMessage = new ActionMessage("invalid.RuleValidity.Date");
                actionMessages.add("global.error",actionMessage);
                errors.add(actionMessages);
                bValidation=false;
            }
            //////  end of date check /////////////

            try{
            java.util.Date dtFromDate = TTKCommon.getUtilDate(request.getParameter("hiddenFromDate"));
            java.util.Date dtDate2    = TTKCommon.getUtilDate(strValue1);
            if(dtFromDate.compareTo(dtDate2)==0)
               bResult=true;
            else
            bResult 				  = dtFromDate.compareTo(dtDate2)<0?true:false;

            if(!bResult)
            {
                    actionMessages = new ActionMessages();
                    actionMessage = new ActionMessage("invalid.RuleValidity.Date");
                    actionMessages.add("global.error",actionMessage);
                    errors.add(actionMessages);
                    bValidation=false;
            }

            }catch(Exception e)
            {

            }
          }
        // now constructing xml dom and and displays it
        if(!bValidation)
        {
            Document loadedBaseRule          = null;
            if(strScreenType !=null && strScreenType.equalsIgnoreCase("ProductBaseRule"))
            {
                loadedBaseRule = RuleDocument.loadXmlDom("BaseRule.xml");
                request.setAttribute("ProductBaseRuleXml",loadedBaseRule);
                request.setAttribute("ProductBaseRule","ProductBaseRule");
            }
            else
            {
                loadedBaseRule = RuleDocument.loadXmlDom("BaseRule.xml");
                request.setAttribute("PolicyBaseRuleXml",loadedBaseRule);
                request.setAttribute("ProductBaseRule","PolicyBaseRule");

            }
            Document doc 			 	  = RuleDocument.createXMLDocument(request);
            Object [] objClauseArray 	  = RuleDocument.getCoverageName(request);
            String [] strClauseArray 	  = new String[(objClauseArray==null)?0:objClauseArray.length];
            int intClauseArraySize 		  = (strClauseArray==null)?0:strClauseArray.length;
            for(int i=0; i<intClauseArraySize;i++)
            {
                    strClauseArray[i] = (String)objClauseArray[i];
            }
            strHtml						  = RuleDocument.getXMLAttrToHtml(strClauseArray,doc,"",null,request);

            if(strScreenType !=null && strScreenType.equalsIgnoreCase("ProductBaseRule"))
                request.getSession().setAttribute("html1",strHtml);
            else
                request.getSession().setAttribute("policyhtml",strHtml);

        }//end if
        return strHtml;
    }*/
}//end of TTKValidatorUtil