package com.ttk.action.enrollment;

import com.ttk.action.TTKAction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.tree.TreeData;
import com.ttk.business.administration.RuleManager;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.business.enrollment.PolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.enrollment.MemberAddressVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.PolicyDetailVO;

import formdef.plugin.util.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ttk.action.table.TableData;

public class OccupationCodeListAction extends TTKAction{
	private static final String strOccuCodeList="occupationCodeList";
	private static final String StrCorporate="corporate";
	private static final String strForward="Forward";
	private static final String strBackward="Backward";

	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside OccupationCodeListAction doDefault");
			DynaActionForm frmoccupCodeList=(DynaActionForm)form;
			TableData tableData =TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("OccupationcodeListTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			return this.getForward(strOccuCodeList, mapping, request);
		}
		catch(TTKException expTTK)
	      {
	          return this.processExceptions(request, mapping, expTTK);
	       }//end of catch(TTKException expTTK)
	       catch(Exception exp)
	       {
	         return this.processExceptions(request, mapping, new TTKException(exp,strOccuCodeList));
	        }//end of catch(Exception exp)
	      }
	
	public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			try{
				log.debug("Inside OccupationCodeListAction doSearch");
				setLinks(request);
				TableData tableData=null;
				MemberManager occupationcodeObject=this.getoccupationcodeObject();
				//clear the dynaform if visting from left links for the first time
				//else get the dynaform data from session
				if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
				{
				((DynaActionForm)form).initialize(mapping);//reset the form data
				}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
				if((request.getSession()).getAttribute("tableData") == null)
				{
				tableData = new TableData();
				}//end of if((request.getSession()).getAttribute("tableData") == null)
				else
				{
				tableData = (TableData)(request.getSession()).getAttribute("tableData");
				}//end of else
				
				String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
				String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
				
				//if the page number or sort id is clicked
				if(!strPageID.equals("") || !strSortID.equals(""))
				{
				if(!strPageID.equals(""))
				{
				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return (mapping.findForward(strOccuCodeList));
				}///end of if(!strPageID.equals(""))
				else
				{
				tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableData.modifySearchData("sort");//modify the search data
				}//end of else
				}//end of if(!strPageID.equals("") || !strSortID.equals(""))
				else
				{
				//create the required grid table
				tableData.createTableInfo("OccupationcodeListTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
				}//end of else
				//Populating Data Which matches the search criteria
				ArrayList alUserList=occupationcodeObject.selectOccupationCode(tableData.getSearchData());
				tableData.setData(alUserList,"search");
				//set the table data object to session
				request.getSession().setAttribute("tableData",tableData);
				//finally return to the grid screen
				return this.getForward(strOccuCodeList, mapping, request);
				}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strOccuCodeList));
			}//end of catch(Exception exp)
	}
	
	private MemberManager getoccupationcodeObject() throws TTKException
    {
    	MemberManager memOccupCode = null;
    	try
        {
            if(memOccupCode == null)
            {
                InitialContext ctx = new InitialContext();
                memOccupCode = (MemberManager) ctx.lookup("java:global/TTKServices/business.ejb3/MemberManagerBean!com.ttk.business.enrollment.MemberManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strOccuCodeList);
        }//end of catch
        return memOccupCode;
    }
	
	public ActionForward doSelectOccupationCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			try{
				setLinks(request);
				log.debug("Inside OccupationCodeAction doSelectOccupationCode ");
				TableData tableData = null;
				if((request.getSession()).getAttribute("tableData") != null){
				tableData = (TableData)(request.getSession()).getAttribute("tableData");
				}
				else{
				tableData = new TableData();
				}//end of else
				MemberDetailVO memberDetaliVO = null;
				DynaActionForm frmPolicyDetails=(DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");
				if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				{
				memberDetaliVO = (MemberDetailVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				frmPolicyDetails.set("occupationCode",memberDetaliVO.getOccupationCode());
				frmPolicyDetails.set("occuCodeDesc",memberDetaliVO.getOccuCodeDesc());
				frmPolicyDetails.set("frmChanged","changed");
				}
				request.getSession().removeAttribute("tableData");
				request.getSession().removeAttribute("frmoccupCodeList");
				return this.getForward(StrCorporate, mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp, StrCorporate));
			}//end of catch(Exception exp)
	}
	
	public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			try{
				log.debug("Inside OccupationCodeListAction doBackward");
				setLinks(request);
	            //get the session bean from the bean pool for each excecuting thread
				MemberManager occupationcodeObject=this.getoccupationcodeObject();
	            TableData tableData = TTKCommon.getTableData(request);
	            tableData.modifySearchData(strBackward);
	            ArrayList alPolicyList = occupationcodeObject.selectOccupationCode(tableData.getSearchData());
	            tableData.setData(alPolicyList, strBackward);
	            request.getSession().setAttribute("tableData",tableData);
	            return this.getForward(strOccuCodeList, mapping, request);
				}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strOccuCodeList));
			}//end of catch(Exception exp)
		}
	
	public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			try{
				log.debug("Inside OccupationCodeListAction doForward");
	            setLinks(request);
	            MemberManager occupationcodeObject=this.getoccupationcodeObject();
	            TableData tableData = TTKCommon.getTableData(request);
	            tableData.modifySearchData(strForward);
	            ArrayList alPolicyList = occupationcodeObject.selectOccupationCode(tableData.getSearchData());
	            tableData.setData(alPolicyList, strForward);
	            request.getSession().setAttribute("tableData",tableData);
	            return this.getForward(strOccuCodeList, mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strOccuCodeList));
			}//end of catch(Exception exp)
	}
	
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			try{
				log.debug("Inside AccountsManagerAction doClose");
				setLinks(request);
				request.getSession().removeAttribute("tableData");
				return mapping.findForward("occupationcodelistclose");
				}//end of try
				catch(TTKException expTTK)
				{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strOccuCodeList));
			}//end of catch(Exception exp)
	}
	
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmoccupCodeList,HttpServletRequest request)
	{
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmoccupCodeList.getString("sOccupationCode")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmoccupCodeList.getString("sOccupationCodeDesc")));
		return alSearchParams;
	}
	
}
