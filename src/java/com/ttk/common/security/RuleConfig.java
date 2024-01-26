/**
 * @ (#) RuleConfig.java Jul 26, 2006
 * Project      : TTK HealthCare Services
 * File         : RuleConfig.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jul 26, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.security;

import org.dom4j.Document;
import org.dom4j.Element;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;

/**
 *
 *
 */
public class RuleConfig {
    private static Document ruleConfigDoc=null;

    public static Element getOperatorElement(String strOperator)throws TTKException
    {
        if(ruleConfigDoc==null)
            ruleConfigDoc=TTKCommon.getDocument("ruleconfig.xml");
        Element eleOperator=(Element)ruleConfigDoc.selectSingleNode("/configuration/operator[@type='"+strOperator+"']/display");
        return eleOperator;
    }//end of getOperatorElement(String strOperator)
    public static Element getLookupElement(String strLookupName)throws TTKException
    {
        if(ruleConfigDoc==null)
            ruleConfigDoc=TTKCommon.getDocument("ruleconfig.xml");
        Element eleLookup=(Element)ruleConfigDoc.selectSingleNode("/configuration/lookup[@name='"+strLookupName+"']/display");
        return eleLookup;
    }//end of getOperatorElement(String strOperator)
    public static String getOperatorText(String strOperator,String strOptVal)throws TTKException
    {
        if(!strOperator.equals(""))
        {
            Element eleOperator=getOperatorElement(strOperator);
            if(eleOperator!=null)
            {
                String [] strOpValues = eleOperator.valueOf("@optVal").split(",");
                String [] strAltText = eleOperator.valueOf("@altText").split(",");
                for(int iCnt=0;iCnt<strOpValues.length;iCnt++)
                {
                    if(strOptVal.equals(strOpValues[iCnt]))
                        return strAltText[iCnt];
                }//end of for(int iCnt=0;iCnt<strOpValues.length;iCnt++)
            }//end of if(eleOperator!=null)
        }//end of if(!strOperator.equals(""))
        return "";
    }
    public static String getLookupText(String strLookupName,String strOptVal)throws TTKException
    {
        if(!strLookupName.equals(""))
        {
            Element eleLookup=getLookupElement(strLookupName);
            if(eleLookup!=null)
            {
                String [] strOptValues = eleLookup.valueOf("@optVal").split(",");
                String [] strAltText = eleLookup.valueOf("@altText").split(",");
                for(int iCnt=0;iCnt<strOptValues.length;iCnt++)
                {
                    if(strOptVal.equals(strOptValues[iCnt]))
                        return strAltText[iCnt];
                }//end of for(int iCnt=0;iCnt<strOpValues.length;iCnt++)
            }//end of if(eleLookup!=null)
        }//end of if(!strOperator.equals(""))
        return "";
    }

}
