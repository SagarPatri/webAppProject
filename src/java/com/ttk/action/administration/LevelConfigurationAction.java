
/**
 * @ (#) LevelConfigurationAction.java June 20th, 2014
 * Project      : TTK HealthCare Services
 * File         : LevelConfigurationAction.java
 * Author       : Rekha Boga
 * Company      : RCS Technologies
 * Date Created : June 20th, 2014
 *
 * @author       :  Rekha Boga
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.administration;

import java.util.ArrayList;
import java.util.Arrays;

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
import com.ttk.dto.administration.LevelConfigurationVO;
import com.ttk.dto.administration.PolicyClauseVO;
import com.ttk.dto.enrollment.MemberAddressVO;

import formdef.plugin.util.FormUtils;

public class LevelConfigurationAction extends TTKAction 
{

	private static Logger log = Logger.getLogger(LevelConfigurationAction.class);
	//for forwarding
	
	private static final String strDeleteCoverage="deletecoverage";
	private static final String strPolicylevelConfiguration="levelConfiguration";
	private static final String strDelPolicyCoverage="delLevelConfiguration";
	//private static final String strProductClose="productclose";
	private static final String strPolicyClose="policyclose";

	//  Exception Message bufferType
	private static final String strCoverageExp="coverage";

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
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.debug("Inside LevelConfigurationAction doDefault");
			TableData  tableDataClause =null;
			//get the table data from session if exists
			if((request.getSession()).getAttribute("tableDataClause") != null)
			{
				tableDataClause = (TableData)(request.getSession()).getAttribute("tableDataClause");
			}//end of if((request.getSession()).getAttribute("tableDataClause") != null)
			else
			{
				tableDataClause = new TableData();
			}//end of else
			DynaActionForm frmCoverage = (DynaActionForm)form;
			DynaActionForm frmProductList = null;
			
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			ArrayList alClauseList=null;
			RuleManager ruleManagerObject=this.getRuleManagerObject();
			String strForward="";
			//String BufferType=request.getParameter("bufferType");
			//log.info("BufferType:"+BufferType);
		
			DynaActionForm frmLevelConfiguration =(DynaActionForm)form;
			//strForward=strPolicylevelConfiguration;
			/* if(strActiveSubLink.equals("Policies"))
			{
				 frmLevelConfiguration = (DynaActionForm)request.getSession().getAttribute("frmLevelConfiguration");
				strForward=strPolicylevelConfiguration;
			}//end of else if(strActiveSubLink.equals("Policies"))
*/			//RuleVO ruleVO=(RuleVO)request.getSession().getAttribute("Current_Rule");
		/*	if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
*/			tableDataClause.createTableInfo("LevelConfigurationTable",null);
			tableDataClause.setSearchData(this.populateSearchCriteria(request));
			tableDataClause.modifySearchData("search");
			 if(frmLevelConfiguration != null)
			{
				alClauseList=ruleManagerObject.getPolicyLevelConfiguration(TTKCommon.getWebBoardId(request),TTKCommon.getUserSeqId(request));
			}//end of else if(frmPoliciesSearch != null)
			tableDataClause.setData(alClauseList);
			frmLevelConfiguration.set("caption",buildCaption(request));
			//frmLevelConfiguration.set("bufferType",BufferType);
			request.getSession().setAttribute("tableDataClause",tableDataClause);
			request.getSession().setAttribute("frmLevelConfiguration",frmLevelConfiguration);
			return mapping.findForward("levelConfiguration");
			//return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strCoverageExp));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doViewLevelConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);

			log.info("Inside LevelConfigurationAction doViewLevelConfiguration");
			LevelConfigurationVO levelConfigurationVO =null;
			DynaActionForm frmLevelConfiguration=(DynaActionForm)form;
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForward="";
			 if(strActiveSubLink.equals("Policies"))
			{
				strForward=strPolicylevelConfiguration;
			}//end of else if(strActiveSubLink.equals("Policies"))

			//if rownumber is found populate the form object
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				levelConfigurationVO=(LevelConfigurationVO)((TableData)request.getSession().getAttribute("tableDataClause")).
				getData().get(Integer.parseInt(request.getParameter("rownum")));
				frmLevelConfiguration = (DynaActionForm)FormUtils.setFormValues("frmLevelConfiguration",levelConfigurationVO,this,mapping,request);
			}// end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			else
			{
				frmLevelConfiguration.initialize(mapping);
			}// end of else
			frmLevelConfiguration.set("caption",buildCaption(request));
			request.setAttribute("frmLevelConfiguration",frmLevelConfiguration);
			return mapping.findForward(strPolicylevelConfiguration);
			//return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strCoverageExp));
		}//end of catch(Exception exp)
	}//end of doViewCoverage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse

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
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.debug("Inside LevelConfigurationAction doSave");
			LevelConfigurationVO levelConfigurationVO =null;
			DynaActionForm frmLevelConfiguration=(DynaActionForm)form;
		
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForward="";
			// if(strActiveSubLink.equals("Policies"))
			//{
			//	 frmLevelConfiguration = (DynaActionForm)request.getSession().getAttribute("frmLevelConfiguration");
				strForward=strPolicylevelConfiguration;
			//}//end of else if(strActiveSubLink.equals("Policies"))
			 RuleManager ruleManagerObject=this.getRuleManagerObject();
			//RuleVO ruleVO=(RuleVO)request.getSession().getAttribute("Current_Rule");
			TableData  tableDataClause =null;
			//get the table data from session if exists
			if((request.getSession()).getAttribute("tableDataClause") != null)
			{
				tableDataClause = (TableData)(request.getSession()).getAttribute("tableDataClause");
			}//end of if((request.getSession()).getAttribute("tableDataClause") != null)
			else
			{
				tableDataClause = new TableData();
			}//end of else
			ArrayList alClauseList=null;
			//ActionForm levelConfigForm=(ActionForm)frmLevelConfiguration.get("levelConfigurationVO");
			
		//	log.info("bufferType"+request.getParameter("bufferType"));
			
			//levelConfigurationVO=(LevelConfigurationVO)FormUtils.getFormValues(levelConfigForm,"frmLevelConfiguration",this, mapping, request);
			levelConfigurationVO=(LevelConfigurationVO)FormUtils.getFormValues(frmLevelConfiguration,this, mapping, request);
		
			levelConfigurationVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
			
			levelConfigurationVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			
			ArrayList allOutParam=ruleManagerObject.savePolicyLevelConfiguration(levelConfigurationVO);
			int iResult=((Integer)allOutParam.get(0)).intValue();
			
			String adminExistAlert=(String) allOutParam.get(1);
			log.info("adminExistAlert::"+adminExistAlert);
			int intUpdate= (Integer) allOutParam.get(2);
			
			if(intUpdate>0)
			{
				frmLevelConfiguration.initialize(mapping);
				if(iResult>0)
				{
				
				if(levelConfigurationVO.getBufferSeqId()!=null)
				{
					//set the appropriate message
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if(chequeVO.getSeqID()!=null)
				else
				{
					//set the appropriate message
					request.setAttribute("updated","message.addedSuccessfully");
				}//end else
				 if(frmLevelConfiguration != null)
				{
					alClauseList=ruleManagerObject.getPolicyLevelConfiguration(TTKCommon.getWebBoardId(request),TTKCommon.getUserSeqId(request));
				
				}//end of else if(frmPoliciesSearch != null)
				tableDataClause.setData(alClauseList);
				request.getSession().setAttribute("frmLevelConfiguration",frmLevelConfiguration);
				request.getSession().setAttribute("tableDataClause",tableDataClause);
		
			    }// end ofif(iResult>0)
			}// end of if(iUpdate>0)
			frmLevelConfiguration.set("caption",buildCaption(request));
			frmLevelConfiguration.set("adminExistAlert",adminExistAlert);
			return mapping.findForward(strPolicylevelConfiguration);
		//	return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strCoverageExp));
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
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.debug("Inside LevelConfigurationAction doReset");
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForward="";
			 if(strActiveSubLink.equals("Policies"))
			{
				strForward=strPolicylevelConfiguration;
			}//end of else if(strActiveSubLink.equals("Policies"))

			DynaActionForm frmLevelConfiguration=(DynaActionForm)form;
			frmLevelConfiguration.initialize(mapping);
			frmLevelConfiguration.set("caption",buildCaption(request));
			return mapping.findForward(strPolicylevelConfiguration);
			//return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strCoverageExp));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is called from the struts framework.
	 * This method is used to delete a record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			//setLinks(request);
			
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			DynaActionForm frmLevelConfiguration=(DynaActionForm)form;
			
			String BufferType=request.getParameter("bufferType");
			String strForward="";
			strForward=strDelPolicyCoverage;
			// if(strActiveSubLink.equals("Policies"))
		//	{
			//	 frmLevelConfiguration = (DynaActionForm)request.getSession().getAttribute("frmLevelConfiguration");
				
			//}//end of else if(strActiveSubLink.equals("Policies"))

			StringBuffer sbfDeleteId = new StringBuffer();
			ArrayList <Object>alClauseDelete=new ArrayList<Object>();

			RuleManager ruleManagerObject=this.getRuleManagerObject();
			
			//RuleVO ruleVO=(RuleVO)request.getSession().getAttribute("Current_Rule");
			TableData  tableDataClause =null;
			//get the table data from session if exists
			if((request.getSession()).getAttribute("tableDataClause") != null)
			{
				tableDataClause = (TableData)(request.getSession()).getAttribute("tableDataClause");
			}//end of if((request.getSession()).getAttribute("tableDataClause") != null)
			else
			{
				tableDataClause = new TableData();
			}//end of else
			ArrayList alClauseList=null;
			sbfDeleteId.append(populateDeleteId(request,(TableData)request.getSession().getAttribute("tableDataClause")));
		
			
			alClauseDelete.add(String.valueOf(sbfDeleteId));
			alClauseDelete.add(TTKCommon.getWebBoardId(request));
			alClauseDelete.add(BufferType);
			alClauseDelete.add(TTKCommon.getUserSeqId(request));
			
			int iCount=ruleManagerObject.deletePolicyLevelConfiguration(alClauseDelete);
			
			//refresh the data in order to get the new records if any.
			 if(frmLevelConfiguration != null)
			{
				 alClauseList=ruleManagerObject.getPolicyLevelConfiguration(TTKCommon.getWebBoardId(request),TTKCommon.getUserSeqId(request));
			
			}//end of else if(frmPoliciesSearch != null)
			tableDataClause.setData(alClauseList);
		
			frmLevelConfiguration.set("caption",buildCaption(request));
		    request.getSession().setAttribute("tableDataClause",tableDataClause);
			frmLevelConfiguration.initialize(mapping);
		//	return mapping.findForward("delLevelConfiguration");
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strCoverageExp));
		}//end of catch(Exception exp)
	}//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.debug("Inside ClauseAction doClose");
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForward="";
			/*if(strActiveSubLink.equals("Products"))
			{
				strForward=strProductClose;
			}//end of if(strActiveSubLink.equals("Products"))
    			else*/ if(strActiveSubLink.equals("Policies"))
			{
				strForward=strPolicyClose;
			}//end of else if(strActiveSubLink.equals("Policies"))
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strCoverageExp));
		}//end of catch(Exception exp)
	}// end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param Long lngBankAccntSeqId
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		long lngProdPolicyRuleSeqID=new Long(20);
		alSearchParams.add(lngProdPolicyRuleSeqID);
		return alSearchParams;
	}//end of populateSearchCriteria(HttpServletRequest request)

	/**
	 * This method is to build the Caption based 
	 * @param request current HttpRequest
	 * @return String caption built
	 * @throws TTKException
	 */
	private String buildCaption(HttpServletRequest request)throws Exception
	{
		String strCaption=null;
		StringBuffer sbfCaption= new StringBuffer();
		String strActiveSubLink=TTKCommon.getActiveSubLink(request);

		if(strActiveSubLink.equals("Products"))
		{
			DynaActionForm frmProductList = (DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			sbfCaption.append("List of Levels - [ ").append(frmProductList.get("companyName")).append(" ] [ ").append(frmProductList.get("productName")).append(" ]");
		}//end of if(strActiveSubLink.equals("Products"))
		else if(strActiveSubLink.equals("Policies"))
		{
			DynaActionForm frmPolicies = (DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
			sbfCaption.append("List of Levels - [ ").append(frmPolicies.get("companyName")).append(" ] [ ").append(frmPolicies.get("policyNbr")).append(" ]");
		}//end of else if(strActiveSubLink.equals("Policies"))
		strCaption=String.valueOf(sbfCaption);
		return strCaption;
	}//end of buildCaption(HttpServletRequest request)

	/**
	 * Returns a string which contains the | separated sequence id's to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the | separated sequence id's to be delete
	 * @throws TTKException If any run time Excepton occures
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableDataClause)throws TTKException
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append("|").append(String.valueOf(((LevelConfigurationVO)tableDataClause.getData().get(Integer.parseInt(strChk[i]))).getBufferSeqId()));//setBankAccSeqId
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((LevelConfigurationVO)tableDataClause.getData().get(Integer.parseInt(strChk[i]))).getBufferSeqId().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId=sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return String.valueOf(sbfDeleteId);
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)

	/**
	 * Returns the RuleManager session object for invoking methods on it.
	 * @return RuleManager session object which can be used for method invokation
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
				log.info("Inside RuleManager: BufferManagerBean: " + ruleManager);
			}//end if(ruleManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "ruledata");
		}//end of catch
		return ruleManager;
	}//end of getRuleManagerObject()
}// end of CoverageAction
