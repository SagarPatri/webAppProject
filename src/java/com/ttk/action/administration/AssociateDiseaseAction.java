/**
 * @ (#)AssociateDiseaseAction.java 23rd Sep 2010
 * Project      : Vidal Health TPA Services
 * File         : AssociateDiseaseAction.java
 * Author       : Manikanta Kumar G G
 * Company      : Span Systems Corporation
 * Date Created : 23rd Sep 2010
 *
 * @author       : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 */
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
import com.ttk.action.table.TableData;
import com.ttk.business.administration.RuleManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ClauseDiseaseVO;
import com.ttk.dto.administration.PolicyClauseVO;


public class AssociateDiseaseAction extends TTKAction 

{
	private static Logger log = Logger.getLogger(AssociateDiseaseAction.class);
	
	private static final String strClauseExp="clauses";
	private static final String strProductClose="productclose";
	private static final String strPolicyClose="policyclose";
	private static final String strTableData="tableData";
	private static final String strDiseaseList ="DiseaseList";
	
	/**
	 * This method is used to initialize the search grid.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws TTKException if any error occurs
	 */
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws TTKException
	{
		try
		{
			setLinks(request);
			log.debug("Inside AssociateDiseaseAction doAssocDefault");
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForward="";
			String strTable = "";
			DynaActionForm frmClauseDisease = (DynaActionForm)form;
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
            	((DynaActionForm)form).initialize(mapping);//reset the form data
            }//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			TableData tableData =TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			strTable = "DiseaseListTable";
			tableData.createTableInfo(strTable,new ArrayList<Object>());
			
			if(strActiveSubLink.equals("Products"))
			{
				strForward="productdisease";
			}//end of if(strActiveSubLink.equals("Products"))
			else if(strActiveSubLink.equals("Policies"))
			{
				strForward="policydisease";
			}//end of else if(strActiveSubLink.equals("Policies"))
			frmClauseDisease.set("caption",buildCaption(request));
			request.getSession().setAttribute(strTableData,tableData);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strClauseExp));
		}//end of catch(Exception exp)
		
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to go to previous screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws TTKException if any error occurs
	 */
	public ActionForward doClose(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws TTKException
	{
		try
		{
			setLinks(request);
			log.debug("Inside AssociateDiseaseAction doClose");
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForward="";
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			if(strActiveSubLink.equals("Products"))
			{
				strForward=strProductClose;
			}//end of if(strActiveSubLink.equals("Products"))
			else if(strActiveSubLink.equals("Policies"))
			{
				strForward=strPolicyClose;
			}//end of else if(strActiveSubLink.equals("Policies"))
			request.getSession().removeAttribute("frmClauseDisease");
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strClauseExp));
		}//end of catch(Exception exp)
	}// end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to search the data with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws TTKException if any error occurs
	 */
	public ActionForward doSearch(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws TTKException
	{
		try
		{
			log.debug("Inside the doSearch method of AssociateDisease Action");
			setLinks(request);
			String strForward = "";
			String strTable = "";
			strForward = strDiseaseList;
			strTable = "DiseaseListTable";
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			//get the session bean from the bean pool for each excecuting thread
			RuleManager ruleManagerObject=this.getRuleManagerObject();
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			tableData.createTableInfo(strTable,null);
			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form, request));
			tableData.modifySearchData("search");
			ArrayList<ClauseDiseaseVO> alDiseaseList = ruleManagerObject.getDiseaseList(tableData.getSearchData());
			tableData.setData(alDiseaseList, "search");
			//set the table data object to session
			request.getSession().setAttribute(strTableData,tableData);
			//finally return to the grid screen
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClauseExp));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	/**
     * This method is used to Associate or Exclude the selected Diseases
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws TTKException if any error occurs
     */
	public ActionForward doAssociateExclude(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws TTKException
	{
		try
		{
			log.debug("Inside doAssociateExclude of AssociatedDiseaseAction");
			setLinks(request);
			ArrayList<Object> alDiseaseList = new ArrayList<Object>();
			String strClaimSeqIds = "";
			RuleManager ruleManagerObject=this.getRuleManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			PolicyClauseVO policyClauseVO = (PolicyClauseVO)request.getSession().getAttribute("policyclauseVO");
			strClaimSeqIds = this.getConcatenatedSeqID(request, (TableData)request.getSession().getAttribute("tableData"));
			alDiseaseList.add(new Long(policyClauseVO.getClauseSeqID()));
			alDiseaseList.add(strClaimSeqIds);
			if(request.getParameter("associatedList").equals("DBA")){
				alDiseaseList.add("DBU");
			}//end of if(request.getParameter("associatedList").equals("DBA")){
			else 
			{
				alDiseaseList.add("DBA");
			}//end of else    		
			alDiseaseList.add(TTKCommon.getUserSeqId(request));//user seq id
			int intResult = ruleManagerObject.associateDiseases(alDiseaseList);//to associate/Exclude
			//refresh the grid 
			ArrayList<ClauseDiseaseVO> alDiseases = ruleManagerObject.getDiseaseList(tableData.getSearchData());
			if(alDiseases.size() == 0 || intResult == tableData.getData().size())
			{
				tableData.modifySearchData("Delete");
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
						getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alDiseases = ruleManagerObject.getDiseaseList(tableData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if(alContact.size() == 0 || iCount == tableData.getData().size())
			tableData.setData(alDiseases, "Delete");
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strDiseaseList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strClauseExp));
		}//end of catch(Exception exp)
	}//end of doAssociateExclude()
	
	/**
	 * This method is to build the Caption 
	 * @param request current HttpRequest
	 * @param form 
	 * @return String caption built
	 * @throws TTKException
	 */
	private String buildCaption(HttpServletRequest request)throws Exception
	{
		String strCaption=null;
		StringBuffer sbfCaption= new StringBuffer();
		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
		PolicyClauseVO policyClauseVO = (PolicyClauseVO)((TableData)request.getSession().getAttribute("tableDataClause")).
			getData().get(Integer.parseInt(request.getParameter("rownum")));
			String strClausenumber = policyClauseVO.getClauseNbr();
			request.getSession().setAttribute("policyclauseVO",policyClauseVO);
		if(strActiveSubLink.equals("Products"))
		{
			DynaActionForm frmProductList = (DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			sbfCaption.append("List of Diseases - [ ").append(frmProductList.get("companyName")).append(" ] [ ").append(frmProductList.get("productName")).append(" ] [Clause Number-").append(strClausenumber).append(" ]");
		}//end of if(strActiveSubLink.equals("Products"))
		else if(strActiveSubLink.equals("Policies"))
		{
			DynaActionForm frmPolicies = (DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
			sbfCaption.append("List of Diseases - [ ").append(frmPolicies.get("companyName")).append(" ] [ ").append(frmPolicies.get("policyNbr")).append(" ] [Clause Number-").append(strClausenumber).append(" ]");
		}//end of else if(strActiveSubLink.equals("Policies"))
		strCaption=String.valueOf(sbfCaption);
		return strCaption;
	}//end of buildCaption(HttpServletRequest request)
	
	/**Returns the String of concatenated string of contact_seq_id delimited by '|'.
	 * @param HttpServletRequest
	 * @param TableData
	 * @return String
	 */
	private String getConcatenatedSeqID(HttpServletRequest request,TableData tableData) {
		StringBuffer sbfConcatenatedSeqId=new StringBuffer("|");
		String strChOpt[]=request.getParameterValues("chkopt");
		if((strChOpt!=null)&& strChOpt.length!=0)
		{
			for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
			{
				if(strChOpt[iCounter]!=null)
				{
					if(iCounter==0)
					{
						sbfConcatenatedSeqId.append(String.valueOf(((ClauseDiseaseVO)tableData.getRowInfo(
																Integer.parseInt(strChOpt[iCounter]))).getInsSpecificSeqID()));
					}//end of if(iCounter==0)
					else
					{
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((ClauseDiseaseVO)tableData.getRowInfo(Integer.
																		parseInt(strChOpt[iCounter]))).getInsSpecificSeqID()));
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		sbfConcatenatedSeqId.append("|");
		return sbfConcatenatedSeqId.toString();
	} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)
	
	/**
	 * this method will add search criteria fields and values to the ArrayList and will return it
	 * @param frmClauseDisease form bean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmClauseDisease,HttpServletRequest request )
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		PolicyClauseVO policyClauseVO = (PolicyClauseVO)request.getSession().getAttribute("policyclauseVO");
		alSearchParams.add(new Long(policyClauseVO.getClauseSeqID()));
		alSearchParams.add(new Long(policyClauseVO.getProdPolicySeqID()));
		alSearchParams.add("IDC");
		alSearchParams.add((String)request.getParameter("associatedList"));
		alSearchParams.add((String)frmClauseDisease.getString("diseaseDesc"));
		return alSearchParams;
	}//end of populateSearchCriteria(HttpServletRequest request)
	
	/**
	 * Returns the RuleManager session object for invoking methods on it.
	 * @return RuleManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private RuleManager getRuleManagerObject() throws TTKException
	{
		RuleManager ruleManager = null;
		try
		{
			if(ruleManager == null)
			{
				InitialContext ctx = new InitialContext();
				ruleManager = (RuleManager) ctx.lookup("java:global/TTKServices/business.ejb3/RuleManagerBean!com.ttk.business.administration.RuleManager");
			}//end if(ruleManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "ruledata");
		}//end of catch
		return ruleManager;
	}//end of getRuleManagerObject()
}// end of AssociateDiseaseAction
