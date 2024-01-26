
//Created as per KOC 1216 B Change request
package com.ttk.action.maintenance;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.validator.DynaValidatorForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.action.tree.TreeData;
import com.ttk.business.accountinfo.AccountInfoManager;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.enrollment.PolicyVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;



public class EnrollBufferSearchAction extends TTKAction {
	//Modes
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	private static final String strEnrollBuffersearch ="enrollmentbuffersearch";
	private static final String strEnrollBufferDetails ="enrollmentbufferdetails";
	  private static final String strGroupList="grouplist";
	private static Logger log = Logger.getLogger(EnrollBufferSearchAction.class );
	
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
										HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside EnrollBufferSearchAction doDefault");
			setLinks(request);
			DynaActionForm frmEnrollBufferSearch=(DynaActionForm)form;
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			
			TableData tableData =null;
		
			if((request.getSession()).getAttribute("enrolltableData") != null)
				tableData= (TableData)(request.getSession()).getAttribute("enrolltableData");
			//tableData==tableHospitalAdvice
			else
				tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("BufferEnrollmentListTable",new ArrayList());
			
			request.getSession().setAttribute("enrolltableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			frmEnrollBufferSearch.set("sPolicyType", "COR");
			return this.getForward(strEnrollBuffersearch, mapping, request);

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strEnrollBuffersearch));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
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
									HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside EnrollBufferSearchAction doSearch");
			setLinks(request);
			String strComma="";
			  //get the session bean from the bean pool for each excecuting thread
            AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();
			DynaActionForm frmEnrollBufferSearch=(DynaActionForm)form;
			//StringBuffer strHospitalAdvice = new StringBuffer();
			ArrayList alEnrollDetails = null;
			TableData tableData=null;
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if((request.getSession()).getAttribute("enrolltableData") != null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("enrolltableData");
			}//end of if((request.getSession()).getAttribute("tableData") != null)
			else
			{
				tableData = new TableData();
			}//end of else
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(strPageID.equals(""))
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strEnrollBuffersearch));					
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("BufferEnrollmentListTable",null);
				//setting a visibility of columns has based on print status
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			alEnrollDetails= accountInfoManager.getBufferEnrollmentlist(tableData.getSearchData());
			
			tableData.setData(alEnrollDetails, "search");
			request.getSession().setAttribute("enrolltableData",tableData);
			//finally return to the grid screen
			return this.getForward(strEnrollBuffersearch,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strEnrollBuffersearch));
		}//end of catch(Exception exp)
	}
	
	/**
	 * This method is used to get the previous set of records with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													  HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doBackward method of EnrollBufferSearchAction");
			setLinks(request);
			//get the session bean from the bean pool for each excecuting threadl
		
           		 AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();
		           TableData tableData =null;
    			if((request.getSession()).getAttribute("enrolltableData") != null)
				tableData= (TableData)(request.getSession()).getAttribute("enrolltableData");
			//tableData==tableHospitalAdvice
			else
				tableData = new TableData();
			//modify the search data
			tableData.modifySearchData(strBackward);
			//ArrayList alPolicyList = productpolicyObject.getPolicyList(tableData.getSearchData());
			ArrayList alEnrollDetails= accountInfoManager.getBufferEnrollmentlist(tableData.getSearchData());

			tableData.setData(alEnrollDetails, strBackward);
			//set the table data object to session
			request.getSession().setAttribute("enrolltableData",tableData);

			return (mapping.findForward(strEnrollBuffersearch));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strEnrollBuffersearch));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * This method is used to get the next set of records with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													 HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doForward method of EnrollBufferSearchAction");
			setLinks(request);
			//get the session bean from the bean pool for each excecuting threadl
		
          		  AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();

         		   TableData tableData =null;
    		
			if((request.getSession()).getAttribute("enrolltableData") != null)
				tableData= (TableData)(request.getSession()).getAttribute("enrolltableData");
			//tableData==tableHospitalAdvice
			else
				tableData = new TableData();
			//modify the search data
			tableData.modifySearchData(strForward);
			//ArrayList alPolicyList = productpolicyObject.getPolicyList(tableData.getSearchData());
			ArrayList alEnrollDetails= accountInfoManager.getBufferEnrollmentlist(tableData.getSearchData());

			//set the table data object to session
			tableData.setData(alEnrollDetails, strForward);
			//finally return to the grid screen
			request.getSession().setAttribute("enrolltableData",tableData);
			return (mapping.findForward(strEnrollBuffersearch));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strEnrollBuffersearch));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
    public ActionForward doChangePolicyType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    															HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside EnrollBufferSearchAction doChangePolicyType");
    		String strPolicyType="";
    		DynaActionForm frmEnrollBufferSearch= (DynaActionForm) form;
    		/*String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession()
                    .getAttribute("UserSecurityProfile")).getBranchID());     */      
    		strPolicyType = (String)frmEnrollBufferSearch.get("sPolicyType");
    		((DynaActionForm)form).initialize(mapping);		//reset the form data
    		//TableData tableData =TTKCommon.getTableData(request);
    		TableData tableData =null;
    		
			if((request.getSession()).getAttribute("enrolltableData") != null)
				tableData= (TableData)(request.getSession()).getAttribute("enrolltableData");
			//tableData==tableHospitalAdvice
			else
				tableData = new TableData();
            //create new table data object
            tableData = new TableData();
            //create the required grid table
            tableData.createTableInfo("BufferEnrollmentListTable",new ArrayList());
            request.getSession().setAttribute("enrolltableData",tableData);
           // frmEnrollBufferSearch("sTTKBranch",strDefaultBranchID);
            frmEnrollBufferSearch.set("sPolicyType",strPolicyType);  
    		return this.getForward(strEnrollBuffersearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strEnrollBuffersearch));
    	}//end of catch(Exception exp)
    }//end of doChangePolicyType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    	//HttpServletResponse response)

	/**
     * This method is used to
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doClearCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside EnrollBufferSearchAction doClearCorporate");
    		DynaActionForm frmEnrollBufferSearch=(DynaActionForm)form;
    		frmEnrollBufferSearch.set("sGroupId","");
    		frmEnrollBufferSearch.set("sGroupName","");
    		request.getSession().setAttribute("frmEnrollBufferSearch",frmEnrollBufferSearch);
    		return this.getForward(strEnrollBuffersearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strEnrollBuffersearch));
    	}//end of catch(Exception exp)
    }//end of doClearCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
      //HttpServletResponse response)
	
	 

    /**
     * This method is called on click of Select Group icon
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSelectGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside EnrollBufferSearchAction doSelectCorporate");
    		DynaActionForm frmEnrollBufferSearch=(DynaActionForm)form;
    		frmEnrollBufferSearch.set("frmChanged","changed");
    		request.getSession().setAttribute("frmEnrollBufferSearch",frmEnrollBufferSearch);
    		return mapping.findForward(strGroupList);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request,mapping,new TTKException(exp,strEnrollBuffersearch));
    	}//end of catch(Exception exp)
    }//end of doSelectGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     //HttpServletResponse response)
	
    
    /**
     * Returns the AccountInfoManager session object for invoking methods on it.
     * @return accountInfoManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private AccountInfoManager getAccountInfoManagerObject() throws TTKException
    {
        AccountInfoManager accountInfoManager = null;
        try
        {
            if(accountInfoManager == null)
            {
                InitialContext ctx = new InitialContext();
                accountInfoManager = (AccountInfoManager) ctx.lookup("java:global/TTKServices/business.ejb3/AccountInfoManagerBean!com.ttk.business.accountinfo.AccountInfoManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "product");
        }//end of catch
        return accountInfoManager;
    }//end of getAccountInfoManagerObject()
    
    
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
	/*public ActionForward doViewBufferDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			TableData tableData =null;
			
			setLinks(request);
			if((request.getSession()).getAttribute("tableData") != null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableData");
			}//end of if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			else
			{
				tableData = new TableData();
			}//end of else
			
			DynaActionForm frmEnrollBufferSearch=(DynaActionForm)form;
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
			//	HospitalAdviceVO hospitalAdviceVO=(HospitalAdviceVO)tableData.getRowInfo(Integer.parseInt(
																			//(String)request.getParameter("rownum")));
				//this.addToWebBoard(hospitalAdviceVO, request);
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			
			return mapping.findForward(strEnrollBufferDetails);
			
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"HospitalAdviceSearch"));
		}//end of catch(Exception exp)
	}//end of doViewPreauth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  //HttpServletResponse response)
*/

	
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmCustomerBankDetailsSearch,HttpServletRequest request)throws TTKException
	{
		
		  //  ArrayList<Object> alSearchObjects = new ArrayList<Object>();
	        ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();
		       
          alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCustomerBankDetailsSearch.get("sPolicyNumber")));//sPolicyNumber
          alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCustomerBankDetailsSearch.get("sEnrollNumber"))); //sEnrollNumber
          alSearchBoxParams.add((String)frmCustomerBankDetailsSearch.get("sEmployeeName"));//sEmpName
          alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCustomerBankDetailsSearch.get("sEnrollmentId"))); //sEnrollmentId
          alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCustomerBankDetailsSearch.get("sMemberName")));//sMemberName
          alSearchBoxParams.add((String)frmCustomerBankDetailsSearch.get("sGroupId"));//sGroupId
         

		// TODO Auto-generated method stub
          //String[] strSearchObjects = alSearchBoxParams.toArray(new String[0]);
        //  alSearchObjects.add(strSearchObjects);
         // return alSearchObjects;
          return alSearchBoxParams;
	}




    
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
