/**
 * @ (#) MOUAction.java Dec 1, 2005
 * Project      : 
 * File         : MOUAction.java
 * Author       : Nagaraj D V
 * Company      : 
 * Date Created : Dec 1, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.empanelment;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ttk.action.TTKAction;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.MOUDocument;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * This class is used for viewing and editing the MOUs of the hospitals.
 */

public class MOUAction  extends TTKAction{

    private static Logger log = Logger.getLogger(MOUAction.class );
    
    //forward links
	private static final String strArticleDetails="articledetails";
	
	//Exception Message Identifier
    private static final String strHospMOUSearch="hospitalmousearch";
    
    /**
     * This method is used to navigate to detail screen to edit selected record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside doEdit method of MOUAction");
    		if(TTKCommon.getWebBoardId(request)==null)
            {
                TTKException expTTK = new TTKException();
                expTTK.setMessage("error.hospital.required");
                throw expTTK;
            }//end of if
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		request.setAttribute("MOUDocument", MOUDocument.getModifiedDocument(hospitalObject.
    				getMOUDocument(TTKCommon.getWebBoardId(request))));
            TTKCommon.documentViewer(request);
            return this.getForward(strArticleDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHospMOUSearch));
		}//end of catch(Exception exp)
    }//end of doEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to get the details of the selected record from web-board.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		request.setAttribute("MOUDocument", MOUDocument.getModifiedDocument(hospitalObject.
    				getMOUDocument(TTKCommon.getWebBoardId(request))));
            TTKCommon.documentViewer(request);
            return this.getForward(strArticleDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHospMOUSearch));
		}//end of catch(Exception exp)
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to add/update the record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
            Document modifiedDoc = MOUDocument.getBaseDocument();//TO BE MADE AS NULL AFTER PROCEDURE TESTING
            if(request.getParameterValues("nonapplicable") != null)
            {
                //get the description, applicable and non-applicable values from screen
                String[] strDesc = request.getParameterValues("Description");
                String[] strNonApplicable = request.getParameterValues("nonapplicable");
                String[] strApplicable = request.getParameterValues("applicable");
                ArrayList<String> alNonApplicable = new ArrayList<String>();
                ArrayList<String> alApplicable = new ArrayList<String>();
                Element element = null;
                //load the applicable and non-applicable clause numbers
                for(int i=0; i < strNonApplicable.length; i++)
                {
                    if(!strNonApplicable[i].equals("")){
                    	alNonApplicable.add(strNonApplicable[i]);
                    }//end of if(!strNonApplicable[i].equals(""))
                        
                    if(!strApplicable[i].equals("") && !alNonApplicable.contains(strApplicable[i])){
                    	alApplicable.add(strApplicable[i]);
                    }//end of if(!strApplicable[i].equals("") && !alNonApplicable.contains(strApplicable[i]))
                }//end of for
                //check if any non-applicable clauses are to be added
                if(alNonApplicable != null && alNonApplicable.size() > 0)
                for(int i=0; i < alNonApplicable.size(); i++)
                {
                    if(modifiedDoc == null){
                    	modifiedDoc = MOUDocument.getBaseDocument();
                    }//end of if(modifiedDoc == null)
                    modifiedDoc.getRootElement().add(MOUDocument.getModifiedElement((String)alNonApplicable.get(i), ""));
                }//end of for
                
                //check if any data has been modified
                if(strDesc != null && alApplicable != null && alApplicable.size() > 0)
                for(int i=0; i < alApplicable.size(); i++)
                {
                    element = MOUDocument.getModifiedElement((String)alApplicable.get(i), strDesc[i]);
                    if(element != null)
                    {
                        if(modifiedDoc == null){
                        	modifiedDoc = MOUDocument.getBaseDocument();
                        }//end of if(modifiedDoc == null)
                        modifiedDoc.getRootElement().add(element);
                    }//end of if(element != null)
                }//end of for()
                int iResult = hospitalObject.addUpdateDocument(modifiedDoc, TTKCommon.getWebBoardId(request),
                		TTKCommon.getUserSeqId(request));
                //finally merge the template copy of MOU with the modified document.
                if(iResult > 0){
                	request.setAttribute("updated","message.saved");
                }//end of if(iResult > 0)
                modifiedDoc = MOUDocument.getModifiedDocument(modifiedDoc);
            }//end of if(request.getParameterValues("Description") != null)
            request.setAttribute("MOUDocument", modifiedDoc);
            return this.getForward(strArticleDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHospMOUSearch));
		}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * Returns the HospitalManager session object for invoking methods on it.
     * @return HospitalManager session object which can be used for method invokation 
     * @exception throws TTKException  
     */
    private HospitalManager getHospitalManagerObject() throws TTKException
    {
        HospitalManager hospManager = null;
        try 
        {
            if(hospManager == null)
            {
                InitialContext ctx = new InitialContext();
                hospManager = (HospitalManager) ctx.lookup("java:global/TTKServices/business.ejb3/HospitalManagerBean!com.ttk.business.empanelment.HospitalManager");
            }//end of if(hospManager == null)
        }//end of try 
        catch(Exception exp) 
        {
            throw new TTKException(exp, strHospMOUSearch);
        }//end of catch 
        return hospManager;
    }//end getHospitalManagerObject()
}//end of class MOUAction
