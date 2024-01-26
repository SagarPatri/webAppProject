/**
 * @ (#) ValidatorResourceHelper.java Jun 9, 2006
 * Project      : TTK HealthCare Services
 * File         : ValidatorResourceHelper.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jun 9, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.webservices.helper;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResourcesInitializer;
import org.apache.commons.validator.ValidatorResult;
import org.apache.commons.validator.ValidatorResults;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;

/**
 *
 *
 */
public class ValidatorResourceHelper {
    private static ValidatorResources validatorResources=null;
    private static ResourceBundle resourceBundle=null;
    private static Logger log = Logger.getLogger( ValidatorResourceHelper.class );
    /**
     * This method will create ValidatorResources if not exists and returns it
     * to the called method to validate the request.
     * @return ValidatorResources returns ValidatorResources Object
     * @throws TTKException if any Runtime Exception occures while getting ValidatorResource
     */
    private static ValidatorResources getValidatorResource()throws TTKException
    {
        if(validatorResources==null)
        {
            //call prepareValidatorResource method to get new resource
            validatorResources= prepareValidatorResource();
        }//end of if(validatorResources==null)
        return validatorResources;
    }//end of getValidatorResource()

    public static ResourceBundle getResourceBundle()
    {
        if(resourceBundle==null)    //load the ResourceBundle
        {
            resourceBundle=ResourceBundle.getBundle("ApplicationResources");
        }//end of if(resourceBundle==null)
        return resourceBundle;
    }//end of getResourceBundle()

    /**
     * This method will prepare new ValidatorResource object, intialize the ResourceBundle
     * and returns it to the called method
     * @return validatorResources ValidatorResources Object
     * @throws TTKException if any runtime Exception occures
     */
    private static ValidatorResources prepareValidatorResource() throws TTKException
    {
        ValidatorResources validatorResources = new ValidatorResources();

        //Load the ResourceBundle
        resourceBundle=ResourceBundle.getBundle("ApplicationResources");
        try
        {
            //TODO now validator Rules File is Read from Jboss/bin later it will be read from properties file
            InputStream validatorRules = new BufferedInputStream(new FileInputStream("C:/jboss-4.0.2/bin/validator-rules.xml"));
            ValidatorResourcesInitializer.initialize(validatorResources, validatorRules);

            //get the Validation.xml which contains the form bean definitions which are to validated
            InputStream validationXmlStream=getValidationXML();
            ValidatorResourcesInitializer.initialize(validatorResources, validationXmlStream);  //initialize the validation.xml
        }//end of try
        catch (IOException ie)
        {
           throw new TTKException(ie, "product");
        }//end of catch (IOException ie)
        return validatorResources;
    }//end of prepareValidatorResource()

    /**
     * This method will combine the validation.xml and form-defs.xml into one Document and writes that into an Stream and
     * returns the Stream which contains the validation definitions to be checked before saving to data to database.
     *
     * @return validationXmlStream InputStream
     * @throws TTKException
     */
    private static InputStream getValidationXML()throws TTKException
    {
        InputStream validationXmlStream=null;
        Document validationDoc=TTKCommon.getDocument("validation.xml"); //get the dom4j object of validation.xml
        Document formDefDoc=TTKCommon.getDocument("form-defs.xml");     //get the dom4j object of form-defs.xml
        Element formsetElement=null;
        Element element=null;
        ArrayList alFormNodes=null;

        if(formDefDoc!=null && validationDoc!=null)
        {
            // get all the form nodes from formdef.xml
            alFormNodes=(ArrayList)formDefDoc.selectNodes("//formset/form");

            //navigate to formset node in validation.xml
            formsetElement=(Element)validationDoc.selectSingleNode("//formset");
            if(formsetElement!=null && alFormNodes!=null);
            {
                //if formnodes are present attach each of them to fromset node of validation.xml
                Iterator itFormElement=alFormNodes.iterator();
                while(itFormElement.hasNext())
                {
                    element=(Element)itFormElement.next();
                    element.setParent(null);    //detach the form node from current parent
                    formsetElement.add(element);
                }//end of while(itFormElement.hasNext())

                //Read the validation.xml document to InputStream and return it
                validationXmlStream = new BufferedInputStream( new StringBufferInputStream(validationDoc.asXML()));
            }//end of if(formsetElement!=null && alFormNodes!=null);
        }//end of if(formDefDoc!=null && validationDoc!=null)
        return validationXmlStream;
    }//end of getValidationXML()

    /**
     * This method take value object and form bean name as the inputs and validates it using the
     * Validator Resource and returns error messages if any to the called method.
     * @param bean value Object which is to be validated
     * @param strForm Name of the Formbean against which Value Object has to be Validated.
     * @return strValidateMessage String which conatins the error messages.
     * @throws TTKException if any Runtime Exception occures
     */
    public static String validateForm(Object bean,String strForm)throws TTKException
    {
        String strValidateMessage="";
        ValidatorResults results=null;
        try
        {
            ValidatorResources resource=getValidatorResource(); //get the validator resource
            Validator validator = new Validator(resource, strForm);
            //Tell the validator which bean to validate against.
            validator.addResource(Validator.BEAN_KEY, bean);

            validator.setOnlyReturnErrors(true);//capture only validatorResults having errors.
            results = validator.validate(); //validate the value object
            if(results!=null && !results.isEmpty())
            {
                //capture the Errormessages for current validations performed
                strValidateMessage=getValidatorResults(strForm,results);
            }//end of if(results!=null && !results.isEmpty())
        }//end of try
        catch (ValidatorException ve)
        {
            throw new TTKException();
        }//end of catch (ValidatorException ve)
        return strValidateMessage;
    }//end of validateForm(Object bean,String strForm)

    /**
     * This method will take forname and Validator Results as the input.
     * prepares the Error message from it and sends it back to called method.
     * @param strForm String Name of the Formbean
     * @param results ValidatorResults which contians results of the validated Form
     * @return
     */
    private static String getValidatorResults(String strForm,ValidatorResults results)throws TTKException
    {
        StringBuffer sbfValidatorResults=new StringBuffer();
        ValidatorResources resources=getValidatorResource(); //get the refernce of the ValidatorResources

        //get the Form from the Resource for which error messages has to be collected
        Form form = resources.getForm(Locale.getDefault(),strForm);

        //get the list of Property Names which are having errors
        Iterator itPropertyNames = results.getPropertyNames().iterator();
        while (itPropertyNames.hasNext())
        {
            String strPropertyName = (String)itPropertyNames.next();
            // Get the Field associated with that property in the Form
            Field field = form.getField(strPropertyName);

            //Get the result of validating the property.
            ValidatorResult result = results.getValidatorResult(strPropertyName);

            // Get all the actions run against the property, and iterate over their names.
            Map actionMap = result.getActionMap();
            Iterator itKeys = actionMap.keySet().iterator();

            while (itKeys.hasNext())
            {
                String strDependName = (String)itKeys.next();

                // Get the Action for that name.
                if (!result.isValid(strDependName))
                {
                    String strErrorMessage="";
                    ValidatorAction action = resources.getValidatorAction(strDependName);

                    if(field.getMessage(strDependName)!=null)
                    {
                        if(field.getMessage(strDependName).isResource() && !field.getMsg(strDependName).equals("errors.date"))
                        {
                            strErrorMessage=resourceBundle.getString(field.getMsg(strDependName));
                        }//end of if(field.getMessage(strDependName).isResource() && !field.getMsg(strDependName).equals("errors.date"))
                        else
                        {
                            strErrorMessage=field.getMsg(strDependName);
                        }//end of else
                    }//end of if(field.getMessage(strActName)!=null)
                    else if(!action.getMsg().equals("errors.date"))
                    {
                        strErrorMessage=resourceBundle.getString(action.getMsg());
                    }//end of else if(!action.getMsg().equals("errors.date"))

                    //get the arguments of the current field for current dependency
                    Arg strArgs[]=field.getArgs(strDependName);
                    String[] strArguments=new String[strArgs.length];
                    for(int i=0;i<strArgs.length;i++)
                    {
                        if(strArgs[i]!=null)
                        {
                            if(strArgs[i].isResource())
                            {
                                strArguments[i]=resourceBundle.getString(strArgs[i].getKey());
                            }//end of if(strArgs[i].isResource())
                            else
                            {
                                strArguments[i]=strArgs[i].getKey();
                            }//end of else
                        }//end of if(strArgs[i]!=null)
                    }//end of for(int i=0;i<strArgs.length;i++)
                    if(!strErrorMessage.equals(""))
                    {
                        sbfValidatorResults.append(MessageFormat.format(strErrorMessage, strArguments)).append("\n");
                    }//end of if(!strErrorMessage.equals(""))
                }//end of if (!result.isValid(strActName))
            }//end of while (keys.hasNext())
        }//end of while (itPropertyNames.hasNext())
        return sbfValidatorResults.toString();
    }//end of getValidatorResults(String strForm,ValidatorResults results)
    
}//end of ValidatorResourceHelper.java
