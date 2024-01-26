/**
 * @ (#) BonusAction.java Feb 10, 2006
 * Project       : TTK HealthCare Services
 * File          : BonusAction.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 10, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.enrollment;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
import com.ttk.action.tree.TreeData;
import com.ttk.business.administration.RuleManager;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.PremiumInfoVO;
import com.ttk.dto.enrollment.SumInsuredVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for listing the bonu related information.
 * This class also provides option for add/update, delete the bonus information.
 */

public class BonusAction extends TTKAction
{
	private static Logger log = Logger.getLogger(BonusAction.class);
	//	declrations of modes
	private static final String strBonuListMode="BonusList";
	//declare forward paths
	private static final String strIndBonusList="indbonuslist";
	private static final String strIndGrpBonusList="indgrpbonuslist";
	private static final String strCorpBonusList="corpbonuslist";
	private static final String strNonCorpBonusList="noncorpbonuslist";
	private static final String strIndAddBonus="indaddbonus";
	private static final  String strIndGrpAddBonus="indgrpaddbonus";
	private static final String strCorpAddBonus="corpaddbonus";
	private static final String strNonCorpAddBonus="noncorpaddbonus";
	private static final String strBonusListClose="bonuslistclose";
	//	declaration of other constants used in this class
	private static final String strIndPolicy="Individual Policy";
	private static final String strIndGrpPolicy="Ind. Policy as Group";
	private static final String strCorporatePolicy="Corporate Policy";
	private static final String strNonCorporatePolicy="Non-Corporate Policy";

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
			log.debug("Inside BonusAction doSearch");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);

			TableData bonusListData = new TableData();   //create new table data object
			bonusListData.createTableInfo("BonusTable",new ArrayList());
			//get the session from the bean pool for each exceuting thread
			MemberManager memberManagerObject=this.getMemberManagerObject();
			ArrayList alBonusList=null;
			ArrayList<Object> alSearchCriteria=new ArrayList<Object>();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			DynaActionForm frmPremiumInfo=(DynaActionForm)request.getSession().getAttribute("frmPremiumInfo");

            DynaActionForm frmBonus=(DynaActionForm)form;
			Long lngSelMemberSeqId=(Long)frmPremiumInfo.get("selMemberSeqId");
			Long lngPolicyGroupSeqID=(Long)frmPremiumInfo.get("policyGroupSeqID");
			String strSelMemName=(String)frmPremiumInfo.get("selMemberName");
			String strPolicyType=getPolicyType(strActiveSubLink);
			String strBonusList=getBonusListForwardPath(strActiveSubLink);
			StringBuffer sbfCaption=new StringBuffer();
			//	Building the caption
			sbfCaption.append(" [ ");
			sbfCaption.append(WebBoardHelper.getPolicyNumber(request));
			sbfCaption.append(" ] ");
			if(lngSelMemberSeqId!=null)
			{
				if(strSelMemName!=null && !"".equals(strSelMemName)){
				sbfCaption.append(" [ ");
				sbfCaption.append(strSelMemName);
				sbfCaption.append(" ] ");}
			}//end of if(lngSelMemberSeqId!=null)

			alSearchCriteria.add(lngSelMemberSeqId);
			alSearchCriteria.add(lngPolicyGroupSeqID);
			alSearchCriteria.add(strSwitchType);
			alSearchCriteria.add(strPolicyType);

			//call the DAO to get the records
			alBonusList = memberManagerObject.getBonusList(alSearchCriteria);
			if(alBonusList!=null&&alBonusList.size()>0)
			{
				frmBonus.set("cumulativeBonus",((SumInsuredVO)alBonusList.get(0)).getCumulativeBonusAmt().toString());
			}//end of if(alBonusList!=null&&alBonusList.size()>0)
			else
			{
				frmBonus.set("cumulativeBonus","");
			}//end of else if(alBonusList!=null&&alBonusList.size()>0)
			frmBonus.set("caption",sbfCaption.toString());
			bonusListData.setData(alBonusList,"Search");
			request.getSession().setAttribute("bonusListData",bonusListData);
			return this.getForward(strBonusList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBonuListMode));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 *
	 * This method is used to navigate to detail screen to add a record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
									HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside BonusAction doAdd");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);

			SumInsuredVO sumInsuredVO = new SumInsuredVO();
			//TableData bonusListData = null;
			ArrayList alPlanList=null;
			Double sumInsured = null;		

			//get the session from the bean pool for each exceuting thread
			MemberManager memberManagerObject=this.getMemberManagerObject();
			StringBuffer sbfBonusCaption=new StringBuffer();
			DynaActionForm frmPremiumInfo=(DynaActionForm)request.getSession().getAttribute("frmPremiumInfo");
			DynaActionForm frmBonusList=(DynaActionForm)request.getSession().getAttribute("frmSumInsured");
			Long lngProductSeqID=(Long)frmPremiumInfo.get("productSeqID");
			Long lngSelMemberSeqId=(Long)frmPremiumInfo.get("selMemberSeqId");
			Long lngPolicyGroupSeqID=(Long)frmPremiumInfo.get("policyGroupSeqID");
			Long lngPolicySeqID=(Long)WebBoardHelper.getPolicySeqId(request);
			
			ArrayList<Object> alPlanListObj = new ArrayList<Object>();
			alPlanListObj.add(lngProductSeqID);
			alPlanListObj.add(lngPolicySeqID);
			alPlanListObj.add(lngPolicyGroupSeqID);
			alPlanListObj.add(lngSelMemberSeqId);

			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			MemberVO memberVO=null;
			//Check the active sub link
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strAddBonus=getAddBonusForwardPath(strActiveSubLink);

			alPlanList=memberManagerObject.getPlanList(alPlanListObj);
			ArrayList al=memberManagerObject.getSumInsured(lngPolicySeqID);
			if(al.size()>0)
			{
				sumInsured =(Double)al.get(0);
			}
			sumInsuredVO.setMemberSeqID(lngSelMemberSeqId);
			sumInsuredVO.setPolicyGroupSeqID(lngPolicyGroupSeqID);
			sumInsuredVO.setType("0");//Set the Bonus drop down value to None vlaue.
			sbfBonusCaption.append(" Add");
			// This block is to get the policy start date from the tree and to display in the Effective Date field
			//on click of Add button
			if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
			{
				memberVO=(MemberVO)treeData.getSelectedObject((String)request.getParameter("selectedroot"),null);
			}//end of if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
			else
			{
				memberVO=(MemberVO)treeData.getSelectedObject("0",null);
				if(strSwitchType.equals("ENM"))
				{
					sumInsuredVO.setEffectiveDate(memberVO.getPolicyStartDate());
				}//end of if(strSwitchType.equals("ENM"))
				else if(strSwitchType.equals("END"))
				{
					sumInsuredVO.setEffectiveDate(memberVO.getEffectiveDate());
				}//end of else if(strSwitchType.equals("END"))
			}//end of else if(treeData!=null && !(TTKCommon.checkNull(request.
				//getParameter("selectedroot")).equals("")))

			sbfBonusCaption.append(frmBonusList.get("caption").toString());
			if(sumInsured != null)
				sumInsuredVO.setSumInsured(sumInsured.toString());
			else
				sumInsuredVO.setSumInsured("0.00");
			DynaActionForm frmAddBonus = (DynaActionForm)FormUtils.setFormValues("frmAddBonus", sumInsuredVO,
											this, mapping, request);
			//for displaying the label when it is amt or %
			if(((String)frmAddBonus.get("type")).equals("1"))
			{
				frmAddBonus.set("hiddenvalue","%");
				//to set the previously setted percentage amount
				frmAddBonus.set("hidbonamt",frmAddBonus.get("bonus").toString());
			}//end of if(((String)frmAddBonus.get("type")).equals("1"))
			else if(((String)frmAddBonus.get("type")).equals("2"))
			{
				frmAddBonus.set("hiddenvalue","Rs.");
				//to set the previously setted bonus amount
				frmAddBonus.set("hidbonamt",frmAddBonus.get("bonusAmt").toString());
			}//end of if(((String)frmAddBonus.get("type")).equals("1"))
			request.getSession().setAttribute("alPlanList",alPlanList);
			frmAddBonus.set("caption",sbfBonusCaption.toString());
			//keep frmAddBonus in the request scope
			request.setAttribute("frmAddBonus",frmAddBonus);
			return this.getForward(strAddBonus, mapping, request);

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBonuListMode));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doViewBonus(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside BonusAction doViewBonus");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);

			SumInsuredVO sumInsuredVO = new SumInsuredVO();
			TableData bonusListData = null;
			//	get the table data from session if exists
			if((request.getSession()).getAttribute("bonusListData") != null)
			{
				bonusListData = (TableData)(request.getSession()).getAttribute("bonusListData");
			}//end of if((request.getSession()).getAttribute("bonusListData") != null)
			else
			{
				bonusListData = new TableData();
			}//end of else
			ArrayList alPlanList=null;
			DynaActionForm frmBonus=(DynaActionForm)form;
			
			//adding currency and currencyList
			HashMap<String,String>  currencyList=new HashMap<String,String>();
			Connection con=null;
			PreparedStatement statement=null;
			ResultSet resultSet=null;
			try{
				con=ResourceManager.getConnection();
				statement=con.prepareStatement("select CURRENCY_ID AS ID,CURRENCY_ID from Tpa_Currency_CODE ORDER BY CURRENCY_SEQ_ID");
				resultSet=statement.executeQuery();
				while(resultSet.next())currencyList.put(resultSet.getString(1),resultSet.getString(2));
				//frmBonus.set("currencyList",currencyList);
				request.getSession().setAttribute("currencyList",currencyList);
			}catch(Exception e){e.printStackTrace();}
			finally{
				try{
				if(resultSet!=null)resultSet.close();
				if(statement!=null)statement.close();
				if(con!=null)con.close();
				}catch(Exception e){}
			}
			//get the session from the bean pool for each exceuting thread
			MemberManager memberManagerObject=this.getMemberManagerObject();
			StringBuffer sbfBonusCaption=new StringBuffer();
			DynaActionForm frmPremiumInfo=(DynaActionForm)request.getSession().getAttribute("frmPremiumInfo");
			DynaActionForm frmBonusList=(DynaActionForm)request.getSession().getAttribute("frmSumInsured");
			Long lngProductSeqID=(Long)frmPremiumInfo.get("productSeqID");
			Long lngSelMemberSeqId=(Long)frmPremiumInfo.get("selMemberSeqId");
			Long lngPolicyGroupSeqID=(Long)frmPremiumInfo.get("policyGroupSeqID");
			Long lngPolicySeqID=(Long)WebBoardHelper.getPolicySeqId(request);
						
			ArrayList<Object> alPlanListObj = new ArrayList<Object>();
			alPlanListObj.add(lngProductSeqID);
			alPlanListObj.add(lngPolicySeqID);
			alPlanListObj.add(lngPolicyGroupSeqID);
			alPlanListObj.add(lngSelMemberSeqId);
			
			/*Long lngSelMemberSeqId=(Long)frmPremiumInfo.get("selMemberSeqId");
			Long lngPolicyGroupSeqID=(Long)frmPremiumInfo.get("policyGroupSeqID");
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			MemberVO memberVO=null;*/
			//Check the active sub link
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strAddBonus=getAddBonusForwardPath(strActiveSubLink);
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				sumInsuredVO = (SumInsuredVO)bonusListData.getRowInfo(Integer.parseInt((String)(
																	frmBonus).get("rownum")));
				sbfBonusCaption.append(" Edit");
				sumInsuredVO=memberManagerObject.getBonus(sumInsuredVO.getMemInsuredSeqID(),alPlanListObj);
				alPlanList=sumInsuredVO.getPlanList();
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))

			sbfBonusCaption.append(frmBonusList.get("caption").toString());
			DynaActionForm frmAddBonus = (DynaActionForm)FormUtils.setFormValues("frmAddBonus", sumInsuredVO,
																		this, mapping, request);
			//for displaying the label when it is amt or %
			if(((String)frmAddBonus.get("type")).equals("1"))
			{
				frmAddBonus.set("hiddenvalue","%");
				//to set the previously setted percentage amount
				frmAddBonus.set("hidbonamt",frmAddBonus.get("bonus").toString());
			}//end of if(((String)frmAddBonus.get("type")).equals("1"))
			else if(((String)frmAddBonus.get("type")).equals("2"))
			{
				frmAddBonus.set("hiddenvalue","Rs.");
				//to set the previously setted bonus amount
				frmAddBonus.set("hidbonamt",frmAddBonus.get("bonusAmt").toString());
			}//end of if(((String)frmAddBonus.get("type")).equals("1"))
			if(frmAddBonus.get("bonus")== null && frmAddBonus.get("bonusAmt")== "")
			{
				frmAddBonus.set("type","0");
			}//end of if(frmAddBonus.get("bonus")== null && frmAddBonus.get("bonusAmt")== "")
			request.getSession().setAttribute("alPlanList",alPlanList);
			frmAddBonus.set("caption",sbfBonusCaption.toString());
			//keep frmAddBonus in the request scope
			request.setAttribute("frmAddBonus",frmAddBonus);
			return this.getForward(strAddBonus, mapping, request);

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBonuListMode));
		}//end of catch(Exception exp)
	}//end of doViewBonus(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
								HttpServletResponse response) throws Exception
	{
		try{
			log.debug("Inside BonusAction doSave");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);

			DynaActionForm frmBonus=(DynaActionForm)form;

			SumInsuredVO sumInsuredVO = new SumInsuredVO();
			MemberVO memberVO=new MemberVO();
			StringBuffer sbfBonusCaption=new StringBuffer();
			DynaActionForm frmAddBonus=null;
			Long lngSelMemberSeqId=null;
			//get the session from the bean pool for each exceuting thread
			MemberManager memberManagerObject=this.getMemberManagerObject();
			DynaActionForm frmBonusList=(DynaActionForm)request.getSession().getAttribute("frmSumInsured");
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;

            //Check the active sub link
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			DynaActionForm frmPremiumInfo=(DynaActionForm)request.getSession().getAttribute("frmPremiumInfo");
			String selMemberSeqId = frmPremiumInfo.getString("selectedmemberSeqID");
			System.out.println("before : Bonus | doSave|selMemberSeqId ->"+selMemberSeqId);
			if(!selMemberSeqId.equals("") && selMemberSeqId != null)
				 lngSelMemberSeqId= Long.parseLong(selMemberSeqId);
			else
				lngSelMemberSeqId=(long) 0;
			System.out.println("after : lngSelMemberSeqId ->"+lngSelMemberSeqId);
			Long lngPolicyGroupSeqID=(Long)frmPremiumInfo.get("policyGroupSeqID");
			Long lngProductSeqID=(Long)frmPremiumInfo.get("productSeqID");
			
			Long lngPolicySeqID=(Long)WebBoardHelper.getPolicySeqId(request);
			
			ArrayList<Object> alPlanListObj = new ArrayList<Object>();
			alPlanListObj.add(lngProductSeqID);
			alPlanListObj.add(lngPolicySeqID);
			alPlanListObj.add(lngPolicyGroupSeqID);
			alPlanListObj.add(lngSelMemberSeqId);
			
			sumInsuredVO = (SumInsuredVO)FormUtils.getFormValues(frmBonus, this, mapping, request);			
			sumInsuredVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id
			//in enrollment module send policySeqID and in endorsementflow send endorsementSeqId
			if(strSwitchType.equals("ENM"))
				sumInsuredVO.setSeqID(WebBoardHelper.getPolicySeqId(request));
			else if(strSwitchType.equals("END"))
				sumInsuredVO.setSeqID(WebBoardHelper.getEndorsementSeqId(request));
			String strPolicyType=getPolicyType(strActiveSubLink);
			String currency=frmBonus.getString("currency");
			long lngCount=memberManagerObject.saveBonus(sumInsuredVO,strSwitchType,strPolicyType,currency);
			String strAddBonus=getAddBonusForwardPath(strActiveSubLink);
			// Added Rule related validations as per Sreeraj instructions on 27/02/2008
			if(lngCount <0){
				log.info("Errors are there..........");
				RuleManager ruleManager=this.getRuleManagerObject();
				//RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();
				ArrayList alValidationError=ruleManager.getValidationErrorList(new Long(lngCount));
				
				//prepare Error messages
				//ArrayList alErrorMessage=ruleXMLHelper.prepareErrorMessage(alValidationError,request);
				request.setAttribute("BUSINESS_ERRORS",alValidationError);
				return this.getForward(strAddBonus, mapping, request);
			}
			if(lngCount>0)
			{
				if(!frmBonus.get("memInsuredSeqID").equals(""))
				{
					request.setAttribute("updated","message.savedSuccessfully");
					sumInsuredVO=memberManagerObject.getBonus(TTKCommon.getLong((String)
																frmBonus.get("memInsuredSeqID")),alPlanListObj);
					sbfBonusCaption.append(" Edit");
				}//end of if(!frmBonus.get("memInsuredSeqID").equals(""))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
					sumInsuredVO = new SumInsuredVO();
					sumInsuredVO.setMemberSeqID(lngSelMemberSeqId);
					sumInsuredVO.setPolicyGroupSeqID(lngPolicyGroupSeqID);
					frmBonus.initialize(mapping);
					sbfBonusCaption.append(" Add");
					sumInsuredVO.setType("0");//Set the Bonus drop down value to None vlaue.
					//This block is to get the policy start date from the tree and to display in the
						//Effective Date field on click of Add button
					if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
					{
					  memberVO=(MemberVO)treeData.getSelectedObject((String)request.
							  					 getParameter("selectedroot"),null);
					}//end of if(treeData!=null && !(TTKCommon.checkNull(request.
						//getParameter("selectedroot")).equals("")))
					else
					{
						memberVO=(MemberVO)treeData.getSelectedObject("0",null);
						if(strSwitchType.equals("ENM"))
						{
							sumInsuredVO.setEffectiveDate(memberVO.getPolicyStartDate());
						}//end of if(strSwitchType.equals("ENM"))
						else if(strSwitchType.equals("END"))
						{
							sumInsuredVO.setEffectiveDate(memberVO.getEffectiveDate());
						}//end of else if(strSwitchType.equals("END"))
					}//end else 
				}//end of else if(!frmBonus.get("memInsuredSeqID").equals(""))
				frmAddBonus = (DynaActionForm)FormUtils.setFormValues("frmAddBonus", sumInsuredVO,
									this, mapping, request);
				if(((String)frmAddBonus.get("type")).equals("1"))
				{
					frmAddBonus.set("hiddenvalue","%");
					//to set the previously setted percentage amount
					frmAddBonus.set("hidbonamt",frmAddBonus.get("bonus").toString());
				}//end of if(((String)frmAddBonus.get("type")).equals("1"))
				else if(((String)frmAddBonus.get("type")).equals("2"))
				{
					frmAddBonus.set("hiddenvalue","Rs.");
					//to set the previously setted bonus amount
					frmAddBonus.set("hidbonamt",frmAddBonus.get("bonusAmt").toString());
				}//end of else if(((String)frmAddBonus.get("type")).equals("2"))
				sbfBonusCaption.append(frmBonusList.get("caption").toString());
				frmAddBonus.set("caption",sbfBonusCaption.toString());
				request.setAttribute("frmAddBonus",frmAddBonus);
			}//end of if(iCount>0)
			return this.getForward(strAddBonus, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBonuListMode));
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
			log.debug("Inside BonusAction doReset");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);

			SumInsuredVO sumInsuredVO = new SumInsuredVO();
			DynaActionForm frmAddBonus=null;
			DynaActionForm frmBonus=(DynaActionForm)form;
			//get the session from the bean pool for each exceuting thread
			MemberManager memberManagerObject=this.getMemberManagerObject();
			StringBuffer sbfBonusCaption=new StringBuffer();
			DynaActionForm frmPremiumInfo=(DynaActionForm)request.getSession().getAttribute("frmPremiumInfo");
			DynaActionForm frmBonusList=(DynaActionForm)request.getSession().getAttribute("frmSumInsured");
			Long lngProductSeqID=(Long)frmPremiumInfo.get("productSeqID");
			Long lngSelMemberSeqId=(Long)frmPremiumInfo.get("selMemberSeqId");
			Long lngPolicyGroupSeqID=(Long)frmPremiumInfo.get("policyGroupSeqID");
			Long lngPolicySeqID=(Long)WebBoardHelper.getPolicySeqId(request);
			
			ArrayList<Object> alPlanListObj = new ArrayList<Object>();
			alPlanListObj.add(lngProductSeqID);
			alPlanListObj.add(lngPolicySeqID);
			alPlanListObj.add(lngPolicyGroupSeqID);
			alPlanListObj.add(lngSelMemberSeqId);
			
//			ArrayList alPlanList=null;
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			MemberVO memberVO=new MemberVO();
			//Check the active sub link
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strAddBonus=getAddBonusForwardPath(strActiveSubLink);
			if(!frmBonus.get("memInsuredSeqID").equals(""))
			{
				sumInsuredVO=memberManagerObject.getBonus(TTKCommon.getLong((String)frmBonus.get("memInsuredSeqID")),
						alPlanListObj);
				sbfBonusCaption.append(" Edit");
				frmAddBonus = (DynaActionForm)FormUtils.setFormValues("frmAddBonus", sumInsuredVO,
														this, mapping, request);
				//for displaying the label whethe it is amt or %
				if(((String)frmAddBonus.get("type")).equals("1"))
				{
					frmAddBonus.set("hiddenvalue","%");
					//to set the previously setted percentage amount
					frmAddBonus.set("hidbonamt",frmAddBonus.get("bonus").toString());
				}//end of if(((String)frmAddBonus.get("type")).equals("1"))
				else if(((String)frmAddBonus.get("type")).equals("2"))
				{
					frmAddBonus.set("hiddenvalue","Rs.");
					//to set the previously setted bonus amount
					frmAddBonus.set("hidbonamt",frmAddBonus.get("bonusAmt").toString());
				}//end of else if(((String)frmAddBonus.get("type")).equals("2"))
			}//end of if(!frmBonus.get("memInsuredSeqID").equals(""))
			else
			{
//				alPlanList=memberManagerObject.getPlanList(lngProductSeqID);
				sumInsuredVO.setMemberSeqID(lngSelMemberSeqId);
				sumInsuredVO.setPolicyGroupSeqID(lngPolicyGroupSeqID);
				sbfBonusCaption.append(" Add");
				sumInsuredVO.setType("0");
				//This block is to get the policy start date from the tree and to display in the
					//Effective Date field on click of Add button
				if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
				{
					memberVO=(MemberVO)treeData.getSelectedObject((String)request.getParameter("selectedroot"),null);
				}//end of if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
				else
				{
					memberVO=(MemberVO)treeData.getSelectedObject("0",null);
					if(strSwitchType.equals("ENM"))
					{
						sumInsuredVO.setEffectiveDate(memberVO.getPolicyStartDate());
					}//end of if(strSwitchType.equals("ENM"))
					else if(strSwitchType.equals("END"))
					{
						sumInsuredVO.setEffectiveDate(memberVO.getEffectiveDate());
					}//end of else if(strSwitchType.equals("END"))
				}//end of else 
				frmAddBonus = (DynaActionForm)FormUtils.setFormValues("frmAddBonus", sumInsuredVO,
															this, mapping, request);
			}//end of else if(!frmBonus.get("memInsuredSeqID").equals(""))
			if(frmAddBonus.get("bonus")== null && frmAddBonus.get("bonusAmt")== "")
			{
				frmAddBonus.set("type","0");
			}//end of if(frmAddBonus.get("bonus")== null && frmAddBonus.get("bonusAmt")== "")
			sbfBonusCaption.append(frmBonusList.get("caption").toString());
			frmAddBonus.set("caption",sbfBonusCaption.toString());
			request.setAttribute("frmAddBonus",frmAddBonus);
			return this.getForward(strAddBonus, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBonuListMode));
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
			log.debug("Inside BonusAction doClose");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);

			DynaActionForm frmPremiumInfo=(DynaActionForm)request.getSession().getAttribute("frmPremiumInfo");
			DynaActionForm frmBonus=(DynaActionForm)form;
			Long lngSelMemberSeqId=(Long)frmPremiumInfo.get("selMemberSeqId");
			TableData bonusListData = null;
			//Calculate the Sum insured of that member or family and update the amount in the session
			BigDecimal bdTotalSumInsured=new BigDecimal(0);
			BigDecimal bdTotalFlySumInsured=new BigDecimal(0);
			BigDecimal bdTotalFlyPremium=new BigDecimal(0);
			BigDecimal bdTotalPremium=new BigDecimal(0);
			SumInsuredVO sumInsuredVO = null;
			PremiumInfoVO [] premiumInfoVOs=null;
			//get the table data from session if exists
			if((request.getSession()).getAttribute("bonusListData") != null)
				bonusListData = (TableData)(request.getSession()).getAttribute("bonusListData");
			else
				bonusListData = new TableData();
			ArrayList alBonusList=(ArrayList)bonusListData.getData();
			String strPolicySubType=(String)frmPremiumInfo.getString("policySubTypeId");
			if(alBonusList!=null && alBonusList.size()>0)   //get the total suminsured for a member or family
			{
				Iterator itSumInsuredVO=alBonusList.iterator();
				while(itSumInsuredVO.hasNext())
				{
					sumInsuredVO=(SumInsuredVO)itSumInsuredVO.next();
					if(sumInsuredVO.getMemSumInsured()!=null )
					{
						bdTotalSumInsured=bdTotalSumInsured.add(sumInsuredVO.getMemSumInsured());
					}//end of if(sumInsuredVO.getMemSumInsured()!=null )
				}//end of while(itSumInsuredVO.hasNext())
			}//end of if(alBonusList!=null && alBonusList.size()>0)
			premiumInfoVOs=(PremiumInfoVO[])frmPremiumInfo.get("premiumInfo");
			Long lngPolicyGrpSeqID=(Long)frmPremiumInfo.get("policyGroupSeqID");
			if(premiumInfoVOs!=null && premiumInfoVOs.length>0)
			{
				//if member seqid is null then calculate the floater Sum insured for family
				if(lngSelMemberSeqId==null)
				{
					premiumInfoVOs[0].setFloaterSumInsured(bdTotalSumInsured);
					for(int i=0;i<premiumInfoVOs.length;i++)    //to calculate the total Family Sum Insured
					{
						//add member's Sum Insured Amount to Total family Sum Insured Amount
						if(premiumInfoVOs[i].getTotalSumInsured()!=null)
						{
							bdTotalFlySumInsured=bdTotalFlySumInsured.add(premiumInfoVOs[i].getTotalSumInsured());							
						}//end of if(premiumInfoVOs[i].getTotalSumInsured()!=null)
					}//end of  for(int i=0;i<premiumInfoVOs.length;i++)
					frmPremiumInfo.set("floaterSumInsured",bdTotalSumInsured.floatValue()>0?
											String.valueOf(bdTotalSumInsured):"");
				}//end of if(lngSelMemberSeqId==null)
				else
				{
					for(int i=0;i<premiumInfoVOs.length;i++)
					{
						//when member SeqID matches update the calculated Amount to corresponding member
						if(premiumInfoVOs[i].getMemberSeqID().longValue() ==lngSelMemberSeqId.longValue())
						{
							premiumInfoVOs[i].setTotalSumInsured(bdTotalSumInsured);
							premiumInfoVOs[i].setTotalPremium(bdTotalPremium);
						}//end of if(premiumInfoVOs[i].getMemberSeqID().longValue() ==lngSelMemberSeqId.longValue())

						//add member's Sum Insured Amount to Total family Sum Insured Amount
						if(premiumInfoVOs[i].getTotalSumInsured()!=null && !(premiumInfoVOs[i].getCancelYN().equals("Y")))
						{
							bdTotalFlySumInsured=bdTotalFlySumInsured.add(premiumInfoVOs[i].getTotalSumInsured());
						}//end of if(premiumInfoVOs[i].getTotalSumInsured()!=null)						
					}//end of for(int i=0;i<premiumInfoVOs.length;i++)
				}//end of else

				//update the Total family SumInsured based on the policy subtype
				if(strPolicySubType.equals("PNF"))  //for non-floater policy type
				{
					premiumInfoVOs[0].setTotalFlySumInsured(bdTotalFlySumInsured);
					premiumInfoVOs[0].setPremiumPaid((String)bdTotalFlyPremium.toString());
					log.info("inside non floater codition :"+bdTotalFlyPremium);
					frmPremiumInfo.set("totalFlySumInsured",bdTotalFlySumInsured.floatValue()>0?
											String.valueOf(bdTotalFlySumInsured):"");
					frmPremiumInfo.set("totalFlyPremium",bdTotalFlyPremium.floatValue()>0?
							String.valueOf(bdTotalFlyPremium):"");

				}//end of if(strPolicySubType.equals("PNF"))
				//for floater+restriction and floater+nonfloater policy type
				else if(strPolicySubType.equals("PFN") || strPolicySubType.equals("PFR"))
				{
					//add the floater SumInsured to family Sum Insured if exists
					if(premiumInfoVOs[0].getFloaterSumInsured()!=null)
					{
						bdTotalFlySumInsured=bdTotalFlySumInsured.add(premiumInfoVOs[0].getFloaterSumInsured());					
					}//end of if(premiumInfoVOs[0].getFloaterSumInsured()!=null)
					
					if(premiumInfoVOs[0].getFloaterPremium()!=null)
					{
						bdTotalFlyPremium=bdTotalFlyPremium.add(premiumInfoVOs[0].getFloaterPremium());
					}//end of if(premiumInfoVOs[0].getFloaterPremium()!=null)
					
					premiumInfoVOs[0].setTotalFlySumInsured(bdTotalFlySumInsured);
					frmPremiumInfo.set("totalFlySumInsured",bdTotalFlySumInsured.floatValue()>0?
									String.valueOf(bdTotalFlySumInsured):"");					
//					frmPremiumInfo.set("floaterPremium",(String)TTKCommon.checkNull(premiumInfoVOs[0].
//                    		getTotalFamilyPremium()));					
					frmPremiumInfo.set("totalFlyPremium",bdTotalFlyPremium.floatValue()>0?
							String.valueOf(bdTotalFlyPremium):"");
                    
				}//end of else if(bdTotalFlySumInsured.floatValue()>0 &&(strPolicySubType.equals("PFN")
					//|| strPolicySubType.equals("PFR")))
			}//end of if(premiumInfoVOs!=null && premiumInfoVOs.length>0)
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			ArrayList<Object> alSearchCriteria =new ArrayList<Object>();
            alSearchCriteria.add(lngPolicyGrpSeqID);    //policy_Group_seq_id
            alSearchCriteria.add(strSwitchType);    // identifier of Enrollment or Endorsement flow
            alSearchCriteria.add(getSubLinkCode(strActiveSubLink));    //identifier of current sublink
            MemberManager memberManager = this.getMemberManagerObject();
			ArrayList<Object> alPremiumInfo=memberManager.getPremiumList(alSearchCriteria);
            if(alPremiumInfo!= null && alPremiumInfo.size()>0)
            {
            	 PremiumInfoVO premiumInfoVO=(PremiumInfoVO)alPremiumInfo.get(0);
            	 frmPremiumInfo.set("premiumInfo",(PremiumInfoVO[])alPremiumInfo.toArray(new PremiumInfoVO[0]));
            	frmPremiumInfo.set("floaterPremium",(String)TTKCommon.checkNull(premiumInfoVO.getTotalFamilyPremium()));
            	frmPremiumInfo.set("premiumPaid",bdTotalFlyPremium.floatValue()>0?
						String.valueOf(bdTotalFlyPremium):"");
            	
                //if policySubType is Floater,clear the Total Family SumInsured and family premium
                if(!premiumInfoVO.getPolicySubTypeID().equals("PFL"))
                {
                    frmPremiumInfo.set("totalFlyPremium",(String)TTKCommon.checkNull(premiumInfoVO.
                    		getTotalFamilyPremium()));
                    log.info(" premiumInfoVO.getTotalFamilyPremium() :"+premiumInfoVO.getTotalFamilyPremium());
                }//end of if(!premiumInfoVO.getPolicySubTypeID().equals("PFL"))
            }
			
			//update the premium Info Form Bean and clear the unwanted variables
			//frmPremiumInfo.set("premiumInfo",(PremiumInfoVO[])premiumInfoVOs);
            
			frmPremiumInfo.set("selMemberSeqId",null);
			frmPremiumInfo.set("selMemberName",null);
			//clear the bonusList FormBean
			frmBonus.initialize(mapping);
			return mapping.findForward(strBonusListClose);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBonuListMode));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
			log.debug("Inside BonusAction doDeleteList");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);

			StringBuffer sbfCaption=new StringBuffer();
			ArrayList alBonusList=null;
			TableData bonusListData = null;

             //get the table data from session if exists
            if((request.getSession()).getAttribute("bonusListData") != null)
            {
                bonusListData = (TableData)(request.getSession()).getAttribute("bonusListData");
            }//end of if((request.getSession()).getAttribute("bonusListData") != null)
            else
            {
                bonusListData = new TableData();
            }//end of else

			//get the session from the bean pool for each exceuting thread
			MemberManager memberManagerObject=this.getMemberManagerObject();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strPolicyType=getPolicyType(strActiveSubLink);
			String strBonusList=getBonusListForwardPath(strActiveSubLink);
			StringBuffer sbfMemberSeqId=new StringBuffer();
			sbfMemberSeqId.append(populatePremium(request,(TableData)request.getSession().
															getAttribute("bonusListData")));
			DynaActionForm frmPremiumInfo=(DynaActionForm)request.getSession().getAttribute("frmPremiumInfo");
			DynaActionForm frmBonus=(DynaActionForm)form;
			Long lngSelMemberSeqId=(Long)frmPremiumInfo.get("selMemberSeqId");
			Long lngPolicyGroupSeqID=(Long)frmPremiumInfo.get("policyGroupSeqID");
			ArrayList <Object>alDeleteMode=new ArrayList<Object>();
			alDeleteMode.add("BONUS");
			alDeleteMode.add(sbfMemberSeqId.toString());
			if(strSwitchType.equals("ENM"))
			{
				alDeleteMode.add(WebBoardHelper.getPolicySeqId(request));
			}//end of if(strSwitchType.equals("ENM"))
			else
			{
				alDeleteMode.add(WebBoardHelper.getEndorsementSeqId(request));
			}//end of else if(strSwitchType.equals("ENM"))
			alDeleteMode.add(strSwitchType);
			alDeleteMode.add(strPolicyType);
			alDeleteMode.add(TTKCommon.getUserSeqId(request));
			//call deletePremium method
			memberManagerObject.deletePremium(alDeleteMode);
			ArrayList<Object> alSearchCriteria=new ArrayList<Object>();
			alSearchCriteria.add(lngSelMemberSeqId);
			alSearchCriteria.add(lngPolicyGroupSeqID);
			alSearchCriteria.add(strSwitchType);
			alSearchCriteria.add(strPolicyType);
			//call the DAO to get the records
			alBonusList = memberManagerObject.getBonusList(alSearchCriteria);
			if(alBonusList!=null&&alBonusList.size()>0)
			{
				frmBonus.set("cumulativeBonus",((SumInsuredVO)alBonusList.get(0)).getCumulativeBonusAmt().toString());
			}//end of if(alBonusList!=null&&alBonusList.size()>0)
			else
			{
				frmBonus.set("cumulativeBonus","");
			}//end of else if(alBonusList!=null&&alBonusList.size()>0)
			frmBonus.set("caption",sbfCaption.toString());
			bonusListData.setData(alBonusList,"Search");
			request.getSession().setAttribute("bonusListData",bonusListData);
			return this.getForward(strBonusList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"role"));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)


	/**
	 * Returns a string which contains the | separated MemInsuredSeqID(s) to be Deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the | separated MemberSeqId(s) to be Renewed
	 */

	private String populatePremium(HttpServletRequest request, TableData bonusListData)
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfMemberSeqId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfMemberSeqId.append("|").append(String.valueOf(((SumInsuredVO)bonusListData.getData().
										get(Integer.parseInt(strChk[i]))).getMemInsuredSeqID().intValue()));

					}//end of if(i == 0)
					else
					{
						sbfMemberSeqId = sbfMemberSeqId.append("|").append(String.valueOf(((SumInsuredVO)bonusListData.
										getData().get(Integer.parseInt(strChk[i]))).getMemInsuredSeqID().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfMemberSeqId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return sbfMemberSeqId.toString();
	}//end of populatePremium(HttpServletRequest request, TableData bonusListData)

	/**
	 * This method returns the bonus list forward path for next view based on the Flow
	 *
	 * @param strActiveSubLink String current sublink
	 * @return strForwardPath String forward path for the next view
	 */
	private String getBonusListForwardPath(String strActiveSubLink)
	{
		String strForwardPath=null;
		//checking for the sub links
		if(strActiveSubLink.equals(strIndPolicy))
		{
			strForwardPath=strIndBonusList;
		}//end of if(strActiveSubLink.equals(strIndPolicy))
		else if(strActiveSubLink.equals(strIndGrpPolicy))
		{
			strForwardPath=strIndGrpBonusList;
		}//end of if(strActiveSubLink.equals(strIndPolicy))
		else if(strActiveSubLink.equals(strCorporatePolicy))
		{
			strForwardPath=strCorpBonusList;
		}//end of if(strActiveSubLink.equals(strIndPolicy))
		else if(strActiveSubLink.equals(strNonCorporatePolicy))
		{
			strForwardPath=strNonCorpBonusList;
		}//end of if(strActiveSubLink.equals(strIndPolicy))
		return strForwardPath;
	}//end of getBonusListForwardPath(String strActiveSubLink)

	/**
	 * This method returns the add bonus forward path for next view based on the Flow
	 *
	 * @param strActiveSubLink String current sublink
	 * @return strForwardPath String forward path for the next view
	 */
	private String getAddBonusForwardPath(String strActiveSubLink)
	{
		String strForwardPath=null;
		//checking for the sub links
		if(strActiveSubLink.equals(strIndPolicy))
		{
			strForwardPath=strIndAddBonus;
		}//end of if(strActiveSubLink.equals(strIndPolicy))
		else if(strActiveSubLink.equals(strIndGrpPolicy))
		{
			strForwardPath=strIndGrpAddBonus;
		}//end of if(strActiveSubLink.equals(strIndPolicy))
		else if(strActiveSubLink.equals(strCorporatePolicy))
		{
			strForwardPath=strCorpAddBonus;
		}//end of if(strActiveSubLink.equals(strIndPolicy))
		else if(strActiveSubLink.equals(strNonCorporatePolicy))
		{
			strForwardPath=strNonCorpAddBonus;
		}//end of if(strActiveSubLink.equals(strIndPolicy))
		return strForwardPath;
	}//end of getAddBonusForwardPath(String strActiveSubLink)

	/**
	 * This method returns the policy type based on the Flow
	 *
	 * @param strActiveSubLink String current sublink
	 * @return strForwardPath String forward path for the next view
	 */
	private String getPolicyType(String strActiveSubLink)
	{
		String strForwardPath=null;
		//checking for the sub links
		if(strActiveSubLink.equals(strIndPolicy))
		{
			strForwardPath="IP";
		}//end of if(strActiveSubLink.equals(strIndPolicy))
		else if(strActiveSubLink.equals(strIndGrpPolicy))
		{
			strForwardPath="IG";
		}//end of if(strActiveSubLink.equals(strIndPolicy))
		else if(strActiveSubLink.equals(strCorporatePolicy))
		{
			strForwardPath="CP";
		}//end of if(strActiveSubLink.equals(strIndPolicy))
		else if(strActiveSubLink.equals(strNonCorporatePolicy))
		{
			strForwardPath="NC";
		}//end of if(strActiveSubLink.equals(strIndPolicy))
		return strForwardPath;
	}//end of getPolicyTypeForwardPath(String strActiveSubLink)


	/**
	 * Returns the MemberManager session object for invoking methods on it.
	 * @return MemberManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private MemberManager getMemberManagerObject() throws TTKException
	{
		MemberManager memberManager = null;
		try
		{
			if(memberManager == null)
			{
				InitialContext ctx = new InitialContext();
				memberManager = (MemberManager) ctx.lookup("java:global/TTKServices/business.ejb3/MemberManagerBean!com.ttk.business.enrollment.MemberManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strBonuListMode);
		}//end of catch
		return memberManager;
	}//end getMemberManager()
	
	 /**
     * This method returns the SubLink Code based on the Flow
     *
     * @param strActiveSubLink String current sublink
     * @return strForwardPath String forward path for the next view
     */
    private String getSubLinkCode(String strActiveSubLink)
    {
        String strSubLinkCode=null;
        if(strActiveSubLink.equals(strIndPolicy))
        {
            strSubLinkCode="IP";
        }//end of if(strActiveSubLink.equals(strIndividualPolicy))
        else if(strActiveSubLink.equals(strIndGrpPolicy))
        {
            strSubLinkCode="IG";
        }//end of if(strActiveSubLink.equals(strIndPolicyasGroup))
        else if(strActiveSubLink.equals(strCorporatePolicy))
        {
            strSubLinkCode="CP";
        } //end of if(strActiveSubLink.equals(strCorporatePolicy))
        else if(strActiveSubLink.equals(strNonCorporatePolicy))
        {
            strSubLinkCode="NC";
        }//end of if(strActiveSubLink.equals(strNonCorporatePolicy))
        return strSubLinkCode;
    }//end of getSubLinkCode(String strActiveSubLink)
	
	 // Added the following piece of code for integrating Rule related validation 
    // as per Sreeraj's instruction
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
                log.debug("Inside RuleManager: RuleManager: " + ruleManager);
            }//end if(ruleManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "memberdetail");
        }//end of catch
        return ruleManager;
    }//end of getRuleManagerObject()
}//end of BonusAction
