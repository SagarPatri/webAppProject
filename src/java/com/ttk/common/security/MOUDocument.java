/**
 * @ (#) MOUDocument.java Dec 1, 2005
 * Project      :
 * File         : MOUDocument.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Dec 1, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.security;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;

import com.ttk.common.exception.TTKException;

/**
 *  This class reads the MOU document which is in the XML file and returns the document.
 *  It also has method tp merge the hospital specific modified clauses (XML document) with the original copy of
 *  MOU and returns the merged document.
 */
public class MOUDocument implements Serializable{

    private static Document doc = null;
    private static Document baseDoc = null;

    /**
     * This method builds the XML file for the MOU articles and clauses
     * This XML will act as an original copy of data for all the articles and its associated clauses
     *
     * @return Document an XML document object which contains the MOU articles and clauses
     */
    public static Document getMOUDocument() throws TTKException
    {
        try{
            if(doc == null)
            {
                //read the MOU.xml file from the JBOSS/bin folder
                File input = new File("MOU.xml");
                //create a document builder object
                DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = fact.newDocumentBuilder();
                //create a dom reader object
                DOMReader domReader = new DOMReader();
                //covert the w3c document object obtained from document builder to dom4j document
                doc = domReader.read(db.parse(input));//read method take w3c document and returns dom4j document
            }//end of if(doc == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error.moudocument");
        }//end of catch(Exception exp)

        return (Document)doc.clone();//return the copy of the document

    }//end of getMOUDocument()

    /**
     * This method builds the base XML file for storing the modified and non-applicable MOU clauses
     *
     * @return Document a base XML document object for storing the modified and non-applicable MOU clauses
     */
    public static Document getBaseDocument() throws TTKException
    {

        try{
            if(baseDoc == null)
            {
                //read the MOU.xml file from the JBOSS/bin folder
                File input = new File("BaseMOU.xml");
                //create a document builder object
                DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = fact.newDocumentBuilder();
                //create a dom reader object
                DOMReader domReader = new DOMReader();
                //covert the w3c document object obtained from document builder to dom4j document
                baseDoc = domReader.read(db.parse(input));//read method take w3c document and returns dom4j document
            }//end of if(baseDoc == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error.basemoudocument");
        }//end of catch(Exception exp)

        return (Document)baseDoc.clone();//return the copy of the document

    }//end of getBaseDocument()

    /**
     * This method returns the modified element after comparing with the original copy,
     * if the description is modified or if the clause has been made as non applicable
     *
     * @param strClauseNumber String the clause number
     * @param strModifiedDesc String the clause description
     * @return Element the modified element, if the description/applicable condition is changed
     */
    public static Element getModifiedElement(String strClauseNumber, String strModifiedDesc) throws TTKException
    {
        Element element = null;
        //get the original copy of MOU document
        if(doc == null)
            doc = MOUDocument.getMOUDocument();

        //get the appropriate element for the specified clause number
        List  clauses = (List)doc.selectNodes("/MOU/Article/Clause[@number='"+strClauseNumber+"']");

        if(clauses != null && clauses.size() > 0)
        {
            //create a copy of the original element
            element = ((Element)clauses.get(0)).createCopy();
            //set the applicable flag as 'N' if desc is empty
            if(strModifiedDesc.equals(""))
            {
                element.setAttributeValue("applicable", "N");
                //set the modified description
                element.setText(strModifiedDesc);
            }//end of if(strModifiedDesc.equals(""))
            //check if the description is modified or not
            else if(!strModifiedDesc.equals("") && !element.getText().trim().equals(strModifiedDesc.trim()))
            {
                element.setAttributeValue("applicable", "Y");
                element.setText(strModifiedDesc);
            }//end of else
            else//if the element is unchanged return null
                element = null;
        }//end of if
        return element;
    }//end of getModifiedElement(String strClauseNumber, String strModifiedDesc)

    /**
     * This method returns the modified element after comparing with the original copy,
     * if the description is modified or if the clause has been made as non applicable
     *
     * @param strClauseNumber String the clause number
     * @param strModifiedDesc String the clause description
     * @return Element the modified element, if the description/applicable condition is changed
     */
    public static Document getModifiedDocument(Document modifiedDoc) throws TTKException
    {
        if(doc == null)
            doc = MOUDocument.getMOUDocument();

        Document originalDoc = (Document)doc.clone();
        if(modifiedDoc != null)
        {
            List  modifiedClauses = (List)modifiedDoc.selectNodes("/MOU/Clause");
            List  originalClauses = null;
            Element modifiedElement = null;
            Element originalElement = null;
            
            if(modifiedClauses != null && modifiedClauses.size() > 0 )
            for(int i=0; i < modifiedClauses.size(); i++)
            {
                modifiedElement = ((Element)modifiedClauses.get(i));

                originalClauses = (List)originalDoc.selectNodes("/MOU/Article/Clause[@number='"+modifiedElement.attribute("number").getText()+"']");

                if(originalClauses != null && originalClauses.size() > 0)
                {
                    originalElement = (Element)originalClauses.get(0);
                    originalElement.attribute("applicable").setText(modifiedElement.attribute("applicable").getText());
                    //replace the content here...if modifed or applicable
                    if(((String)modifiedElement.attribute("applicable").getText()).equals("Y"))
                        originalElement.setText(modifiedElement.getText());
                }//end of if
            }//end of for

        }//end of if(modifiedDoc != null)

        return originalDoc;
    }//end of getModifiedDocument(Document modifiedDoc)

}//end of class MOUDocument
