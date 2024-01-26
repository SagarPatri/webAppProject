package com.ttk.action.administration;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.RelationshipInfoVO;

public class RelationshipInfoAction extends TTKAction{
private static Logger log = Logger.getLogger( RelationshipInfoAction.class );
	
	//Action mapping forwards.
	private static final String strRelationshipInfo="relationinfo";
	private static final String strRelationClose="relationclose";
	
	//Exception Message Identifier
	private static final String strProduct="product";
	
	/**
	 * This method is used to display the relationships
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doRelationshipList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doRelationshipList method of RelationshipInfoAction");
			setLinks(request);
			DynaActionForm frmRelInformation =(DynaActionForm)form;
			ArrayList<Object> alRelationShipList = new ArrayList<Object>();
			StringBuffer sbfCaption=new StringBuffer();
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			Long lngProdPolicySeqId=TTKCommon.getWebBoardId(request);
			alRelationShipList = productPolicyObject.getRelationshipList(lngProdPolicySeqId);
			if(alRelationShipList!=null && alRelationShipList.size()>0)
			{
				frmRelInformation.set("relationList",(RelationshipInfoVO[])alRelationShipList.toArray(new RelationshipInfoVO[0]));
			}//end of if
			sbfCaption=sbfCaption.append(" - [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
            frmRelInformation.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmRelInformation",frmRelInformation);
			return this.getForward(strRelationshipInfo, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
	
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
    		log.debug("Inside doSave method of RelationshipInfoAction");
    		ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
    		DynaActionForm frmRelInformation =(DynaActionForm)form;
    		StringBuffer sbfCaption= new StringBuffer();
    		ArrayList<Object> alRelationShipList = new ArrayList<Object>();
    		ArrayList<Object> alRelation = new ArrayList<Object>();
    		String[] strRelshipTypeID=request.getParameterValues("relshipTypeID");
    		    		
    		String[] strSelectedYN=request.getParameterValues("selectedYN");
    		    		
            String[] strWindowPeriodResYN=request.getParameterValues("windowPeriodResYN");
                        
            String[] strRelshipWindowPeriod=request.getParameterValues("relshipWindowPeriod");
                        
            String[] strFromDateGenTypeID=request.getParameterValues("fromDateGenTypeID");
                        
            RelationshipInfoVO[] objRelationshipInfoArr= (RelationshipInfoVO[]) frmRelInformation.get("relationList");

            for (int i = 0; i < objRelationshipInfoArr.length; i++) 
            {

				objRelationshipInfoArr[i].setRelshipTypeID(strRelshipTypeID[i]);
				objRelationshipInfoArr[i].setSelectedYN(strSelectedYN[i]);
				objRelationshipInfoArr[i].setWindowPeriodResYN(strWindowPeriodResYN[i]);
				if (strRelshipWindowPeriod[i] != "") 
				{
					objRelationshipInfoArr[i].setRelshipWindowPeriod(new Integer(strRelshipWindowPeriod[i]));
				}// end of if(strRelshipWindowPeriod[i]!="" )

				objRelationshipInfoArr[i].setFromDateGenTypeID(strFromDateGenTypeID[i]);
            }// end of for(int i=0;i<objRelationshipInfoArr.length;i++)

			frmRelInformation.set("relationList", objRelationshipInfoArr);
			request.getSession().setAttribute("frmRelInformation",frmRelInformation);

            if(strRelshipTypeID!=null && strRelshipTypeID.length>0)
            {
            		StringBuffer sbfLimit=new StringBuffer();
                	for(int iCount=0;iCount<strRelshipTypeID.length;iCount++)
                    {
                		if(strSelectedYN[iCount].equals("Y"))
                		{
                		sbfLimit.append("|"+TTKCommon.checkNull(strRelshipTypeID[iCount])+"|"+TTKCommon.checkNull(strWindowPeriodResYN[iCount])+"|"
                    			+TTKCommon.checkNull(strRelshipWindowPeriod[iCount])+"|"+TTKCommon.checkNull(strFromDateGenTypeID[iCount]));
                		}
                	}//end of for loop
                	sbfLimit.append("|");
                	
                	alRelationShipList.add(sbfLimit.toString());
                	alRelationShipList.add(TTKCommon.getWebBoardId(request));
                	alRelationShipList.add(null);
                	alRelationShipList.add(TTKCommon.getUserSeqId(request));
            }//end of if
            if(alRelationShipList!=null && alRelationShipList.size()>0)
            {
            	int iCount=productPolicyObject.saveSelectedRelshipInfo(alRelationShipList);
                
                if(iCount>0)    //if record is updated then requery
                {
                	frmRelInformation.initialize(mapping);
                	alRelation = productPolicyObject.getRelationshipList(TTKCommon.getWebBoardId(request));
        			if(alRelation!=null && alRelation.size()>0)
        			{
        				frmRelInformation.set("relationList",(RelationshipInfoVO[])alRelation.toArray(new RelationshipInfoVO[0]));
        			}//end of if
                	request.setAttribute("updated","message.savedSuccessfully");
                }//end of if(iCount>0)
            }//end of if(alDebitDeposit!=null && alDebitDeposit.size()>0)

        	sbfCaption=sbfCaption.append(" - [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
            frmRelInformation.set("caption",sbfCaption.toString());
            return this.getForward(strRelationshipInfo, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strProduct));
    	}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to reload the screen when the reset button is pressed.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							 HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside doReset method of RelationshipInfoAction");
    		DynaActionForm frmRelInformation =(DynaActionForm)form;
			ArrayList<Object> alRelation = new ArrayList<Object>();
			StringBuffer sbfCaption= new StringBuffer();
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			alRelation = productPolicyObject.getRelationshipList(TTKCommon.getWebBoardId(request));
			if(alRelation!=null && alRelation.size()>0)
			{
				frmRelInformation.set("relationList",(RelationshipInfoVO[])alRelation.toArray(new RelationshipInfoVO[0]));
			}//end of if
			sbfCaption=sbfCaption.append(" - [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			frmRelInformation.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmRelInformation",frmRelInformation);
			return this.getForward(strRelationshipInfo, mapping, request);
    		//return this.getForward(strRenewalConfig, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strProduct));
    	}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
	 * This method is used to navigate to previous screen when closed button is clicked.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												   HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doClose method of RenewalDaysConfAction");
			setLinks(request);
			return mapping.findForward(strRelationClose);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return ProductPolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ProductPolicyManager getProductPolicyManagerObject() throws TTKException
	{
		ProductPolicyManager productPolicyManager = null;
		try
		{
			if(productPolicyManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyManager = (ProductPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProductPolicyManagerBean!com.ttk.business.administration.ProductPolicyManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch
		return productPolicyManager;
	}//end getProductManagerObject()

}//end of RelationshipInfoAction
