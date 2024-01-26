/**
 * @ (#) CourierSearchAction.java May 26th 2006
 * Project 		: TTK HealthCare Services
 * File 		: CourierSearchAction.java
 * Author 		: Krupa J
 * Company 		: Span Systems Corporation
 * Date Created : May 26th 2006
 *
 * @author 		: Krupa J
 * Modified by 	:
 * Modified date:
 * Reason 		:
 */

package com.ttk.action.support;

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
import com.ttk.action.table.TableData;
import com.ttk.business.enrollment.CourierManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.CourierVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This class is used for searching of courier.
 */

public class CourierSearchAction extends TTKAction
{
	private static Logger log = Logger.getLogger(CourierSearchAction.class );

	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	private static final String strDeleteList="DeleteList";
	
	//forwards
	private static final String strCourierList="courierlist";
	private static final String strCouriers="COU";
	
	private static final String strCourierRptDetails="courierreportlist";


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
			log.debug("Inside CourierSearchAction doDefault");
			setLinks(request);
			DynaActionForm frmCourier=(DynaActionForm)form;
			TableData  tableData =TTKCommon.getTableData(request);
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
											request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("CourierSearchTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			request.getSession().setAttribute("searchparam", null);
			frmCourier.set("sOfficeInfo",strDefaultBranchID);
			return this.getForward(strCourierList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"couriersearch"));
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
			log.debug("Inside CourierSearchAction doSearch");
			setLinks(request);
			TableData  tableData =TTKCommon.getTableData(request);
			CourierManager courierManagerObject=this.getCourierManagerObject();
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
					return (mapping.findForward(strCourierList));
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("CourierSearchTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alCourier= courierManagerObject.getCourierList(tableData.getSearchData());
			tableData.setData(alCourier, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strCourierList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"couriersearch"));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside CourierSearchAction doForward");
			setLinks(request);
			TableData  tableData =TTKCommon.getTableData(request);
			CourierManager courierManagerObject=this.getCourierManagerObject();
			tableData.modifySearchData(strForward);//modify the search data
			ArrayList alCourier = courierManagerObject.getCourierList(tableData.getSearchData());
			tableData.setData(alCourier, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strCourierList, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"couriersearch"));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside CourierSearchAction doBackward");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			CourierManager courierManagerObject=this.getCourierManagerObject();
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alCourier = courierManagerObject.getCourierList(tableData.getSearchData());
			tableData.setData(alCourier, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strCourierList, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"couriersearch"));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to get the delete records from the grid screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
											HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CourierSearchAction doDeleteList");
			setLinks(request);
			StringBuffer sbfDeleteId = new StringBuffer("|");
			TableData tableData = TTKCommon.getTableData(request);
			int iCount=0;
			int iStartRowCount=0;
			CourierManager courierManagerObject=this.getCourierManagerObject();
			//populate the delete string which contains the sequence id's to be deleted
			sbfDeleteId.append(populateDeleteId(request,(TableData)request.getSession().getAttribute("tableData")));

			ArrayList <Object>alDeleteMode=new ArrayList<Object>();
			alDeleteMode.add(strCouriers);
			alDeleteMode.add(sbfDeleteId.toString());
			alDeleteMode.add(null);//PAT_ENROLL_DETAIL_SEQ_ID is not required.
			alDeleteMode.add(null);//PAT_GENERAL_DETAIL_SEQ_ID is not required
			alDeleteMode.add(TTKCommon.getUserSeqId(request));//User id
			//delete the selected courier based on the flow
			iCount = courierManagerObject.deleteCourierDetail(alDeleteMode);

			//refresh the grid with search data in session
			ArrayList alCourierList = null;
			//fetch the data from previous set of rowcounts, if all the records are deleted for the current
			//set of search criteria
			if(iCount == tableData.getData().size())
			{
			tableData.modifySearchData(strDeleteList);//modify the search data
		    iStartRowCount= Integer.parseInt((String)tableData.getSearchData().get(tableData.getSearchData().size()-2));
			if(iStartRowCount > 0)
			{
				alCourierList= courierManagerObject.getCourierList(tableData.getSearchData());
			}//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				alCourierList= courierManagerObject.getCourierList(tableData.getSearchData());
			}//end of else
			tableData.setData(alCourierList, strDeleteList);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strCourierList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"couriersearch"));
		}//end of catch(Exception exp)
   }//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the CourierManager session object for invoking methods on it.
	 * @return CourierManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private CourierManager getCourierManagerObject() throws TTKException
	{
		CourierManager courierManager = null;
		try
		{
			if(courierManager == null)
			{
				InitialContext ctx = new InitialContext();
				courierManager = (CourierManager) ctx.lookup("java:global/TTKServices/business.ejb3/CourierManagerBean!com.ttk.business.enrollment.CourierManager");
			}//end of if(courierManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "support");
		}//end of catch
		return courierManager;
	}//end getCourierManagerObject()


	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmCourier DynaActionForm
	 * @return ArrayList contains search parameters
	 */

	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmCourier,HttpServletRequest request)throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add((String)frmCourier.get("sCourierType"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCourier.get("sDocketNbr")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCourier.get("sCourierName")));
		alSearchParams.add(TTKCommon.getLong((String)frmCourier.get("sOfficeInfo")));
		alSearchParams.add(TTKCommon.replaceSingleQots(((String)frmCourier.get("sCourierNbr"))));
		alSearchParams.add(((String)frmCourier.get("sStartDare")));
		alSearchParams.add(((String)frmCourier.get("sEndDate")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmCourier,HttpServletRequest request)

	/**
	 * This method returns a string which contains the comma separated sequence id's to be deleted,
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param TableData object which contains the value objects
	 * @return String which contains the comma separated sequence id's to be deleted
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableData)
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			//loop through to populate delete sequence id's and get the value from session for the matching
			//check box value
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append(String.valueOf(((CourierVO)tableData.getData().
											get(Integer.parseInt(strChk[i]))).getCourierSeqID()));
					}// end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((CourierVO)tableData.getData().
								get(Integer.parseInt(strChk[i]))).getCourierSeqID()));
					}// end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)
	
	
	//added for 2koc
	
	public ActionForward doViewCourierList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the Default method of CourierSearchAction");
			setLinks(request);
			DynaActionForm frmCourierReportList= (DynaActionForm) form;
			//log.debug("form is " + form);
			frmCourierReportList.initialize(mapping);
			return mapping.findForward(strCourierRptDetails);
			//return this.getForward(strCourierRptDetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCourierRptDetails));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	//end added for 2koc
	
}//end of CourierSearchAction