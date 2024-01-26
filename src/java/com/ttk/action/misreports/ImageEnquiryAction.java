/** @ (#) ImageEnquiryAction.java May 28, 2007
 * Project     : TTK Healthcare Services
 * File        : ImageEnquiryAction.java
 * Author      : Ajay Kumar
 * Company     : WebEdge Technologies Pvt.Ltd.
 * Date Created: May 28, 2007
 *
 * @author 		: Ajay Kumar
 * Modified by  : Balakrishna Erram
 * Modified date: April 15, 2009
 * Company      : Span Infotech Pvt.Ltd.
 * Reason       : Code Review
 */

package com.ttk.action.misreports;

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
import com.ttk.business.misreports.ImageEnquiryManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.SearchCriteria;

public class ImageEnquiryAction extends TTKAction {
	 
	  private static final Logger log = Logger.getLogger(ImageEnquiryAction.class);
	
	  //declare forward paths
	  private static final String strImageEnquiryLlist="imageenquirylist";
	    
	 /**
	  * This method is used to initialize the search grid.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			                         HttpServletResponse response)throws TTKException
	    {
	    	try
	    	{
	    		log.debug("Inside the Default method of ImageEnquiryAction");
	    		setLinks(request);
	          
	            if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
	            {
					((DynaActionForm)form).initialize(mapping);//reset the form data
	            }//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
				return this.getForward(strImageEnquiryLlist,mapping,request);	
	    	}//end of try
	    	catch(TTKException expTTK)
	    	{
	    		return this.processExceptions(request, mapping, expTTK);	
	    	}//end of catch(TTKException expTTK)
	    	catch (Exception exp) {
				return this.processExceptions(request, mapping, new TTKException(exp,"imageList"));
			}//end of catch (Exception exp)
	 }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	                     //HttpServletResponse response)throws Exception
	 /**
	  * This method is used to search the data with the given search criteria.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            				   HttpServletResponse response) throws TTKException{
	        try{
	        	log.debug("Inside the doSearch method of ImageEnquiryAction");
	        	setLinks(request);
	        	//get the session bean from the bean pool for each excecuting thread
	        	ImageEnquiryManager imageEnquiryObject=this.getImageEnquiryManagerObject();
	        	
	        	if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
	        		((DynaActionForm)form).initialize(mapping);//reset the form data
	        	}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
	        	
	        	DynaActionForm frmImageEnquiry=(DynaActionForm)form;     //get the instance of formbean
	        	ArrayList<Object> alImageEnquiryList= imageEnquiryObject.getImageEnquiryList(
	        			                                  this.populateSearchCriteria(frmImageEnquiry,request));
	        	frmImageEnquiry.set("alImageEnquiryList",alImageEnquiryList);
	        	request.getSession().setAttribute("alImageEnquiryList",alImageEnquiryList);
	        	//finally return to the grid screen
	        	return this.getForward(strImageEnquiryLlist, mapping, request);
	        }//end of try
	        catch(TTKException expTTK)
	        {
	            return this.processExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
	        catch(Exception exp)
	        {
	            return this.processExceptions(request, mapping, new TTKException(exp,"imageList"));
	        }//end of catch(Exception exp)
	    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	                       //HttpServletResponse response)
	    
	    /**
		 * Returns the ImageEnquiryManager session object for invoking methods on it.
		 * @return ImageEnquiryManager session object which can be used for method invokation 
		 * @exception throws TTKException  
		 */
		private ImageEnquiryManager getImageEnquiryManagerObject() throws TTKException
		{
			ImageEnquiryManager imageEnquiryManager = null;
			try 
			{
				if(imageEnquiryManager == null)
				{
					InitialContext ctx = new InitialContext();
					imageEnquiryManager = (ImageEnquiryManager) ctx.lookup("java:global/TTKServices/business.ejb3/ImageEnquiryManagerBean!com.ttk.business.misreports.ImageEnquiryManager");
				}//end if
			}//end of try 
			catch(Exception exp) 
			{
				throw new TTKException(exp, "ImageEnquiry");
			}//end of catch
			return imageEnquiryManager;
		}//end getProductManagerObject()
		
		/**
		 * this method will add search criteria fields and values to the arraylist and will return it
		 * @param frmProductList formbean which contains the search fields
		 * @param request HttpServletRequest
		 * @return ArrayList contains search parameters
		 */
		private ArrayList<Object> populateSearchCriteria(DynaActionForm frmImageEnquiry,HttpServletRequest request)
		{
			//build the column names along with their values to be searched
			ArrayList<Object> alSearchParams = new ArrayList<Object>();
			alSearchParams.add(new SearchCriteria("POLICY_NUMBER",TTKCommon.replaceSingleQots((String)frmImageEnquiry.
																							 get("policyNbr"))));
			alSearchParams.add(new SearchCriteria("TPA_ENROLLMENT_NUMBER",TTKCommon.replaceSingleQots((String)
					                              frmImageEnquiry.get("enrollmentNbr"))));
			alSearchParams.add(new SearchCriteria("TPA_ENROLLMENT_ID",TTKCommon.replaceSingleQots((String)
					                              frmImageEnquiry.get("enrollmentID"))));
			alSearchParams.add(new SearchCriteria("MEM_NAME",TTKCommon.replaceSingleQots((String)frmImageEnquiry.
					                              get("memberName"))));
			alSearchParams.add(new SearchCriteria("GROUP_NAME",TTKCommon.replaceSingleQots((String)frmImageEnquiry.
					                              get("corporateName"))));
			alSearchParams.add(new SearchCriteria("GROUP_ID",TTKCommon.replaceSingleQots((String)frmImageEnquiry.
					                              get("groupId"))));
			alSearchParams.add(new SearchCriteria("pol.ENROL_TYPE_ID",TTKCommon.replaceSingleQots((String)frmImageEnquiry.
                                                  get("policyType"))));
			return alSearchParams;
		}//end of populateSearchCriteria(DynaActionForm frmImageEnquiry,HttpServletRequest request)
}//end of ImageEnquiryAction
