/**
 * @ (#) AdditionalSumInsuredAction.java
 * Project 		: TTK HealthCare Services
 * File 		: AdditionalSumInsuredAction.java
 * Author 		: Balaji C R B
 * Company 		: Span Systems Corporation
 * Date Created : Jan 17,2008
 *
 * @author 		: Balaji C R B
 * Modified by 	:
 * Modified date:
 * Reason 		:
 */

package com.ttk.action.onlineforms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

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
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.enrollment.OnlineAccessPolicyVO;
import com.ttk.dto.onlineforms.DependentDetailVO;
import com.ttk.dto.onlineforms.SumInsuredVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;


/**
 * This class is used to display Additional Sum Insured Details
 * to select for a plan.
 */
public class AdditionalSumInsuredAction extends TTKAction
{
	private static Logger log = Logger.getLogger( AdditionalSumInsuredAction.class );
	private static final String strAdditionalSumInsuredDetails = "suminsureddetails";
	private static final String strAddSumInsDetailSelect = "suminsureddetailselect";
	private static final String strCloseDetails="closesuminsureddetails";

	//Exception Message Identifier
	private static final String strFailure="failure";
	private static final String strSavePlan="saveplan";
	private static final String strClearPlan="clearplan";
	private static final String strClose="close";
	/**
	 * This method is used to for bringing out Additional Sum Insured Details
	 * grid
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 */
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.info("Inside doDefault AdditionalSumInsuredAction ");
			TableData  tableData =TTKCommon.getTableData(request);

			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			OnlineAccessPolicyVO onlinePolicyVO = null;
			SumInsuredVO sumInsuredVO = null;
			ArrayList alSumInsList = null;
			DependentDetailVO dependentDetailVO =null;
			OnlineAccessManager onlineAccessManagerObject =this.getOnlineAccessManagerObject();
			StringBuffer sbfCaption = new StringBuffer();
			sbfCaption.append("Additional Sum Insured Details - [");
			DynaActionForm frmSumInsuredDetails = (DynaActionForm)form;
			DynaActionForm frmMemberDetails = (DynaActionForm) request.getSession().getAttribute("frmMemberDetails");
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			Long lngPolicySeqID = null;
			Long lngPolicyGroupSeqID = null;
			Long lngMemberSeqID = null;
			String strCancelYN="";
			String strExpiredYN="";
			String strMemberAge = null;
			String strEnrollmentNo=null;//Added for KOC-1216
			lngPolicySeqID = TTKCommon.getLong((String)frmMemberDetails.get("policySeqID"));

			//Added for KOC-1216
			strEnrollmentNo = (String)frmMemberDetails.get("enrollmentNbr");
			
			request.getSession().setAttribute("EnrollmentNo", frmMemberDetails.get("enrollmentNbr"));
			//setAttribute("EnrollmentNo",frmMemberDetails.get("enrollmentNbr"));
			//End of KOC-1216



			lngPolicyGroupSeqID = TTKCommon.getLong((String)frmMemberDetails.get("policyGroupSeqID"));
			//if rownumber is found populate the form object
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				dependentDetailVO=(DependentDetailVO)((TableData)request.getSession().getAttribute("tableData")).
				getData().get(Integer.parseInt(request.getParameter("rownum")));
				lngMemberSeqID = (Long)dependentDetailVO.getMemberSeqID();
				strCancelYN= dependentDetailVO.getCancelYN();
				strExpiredYN= dependentDetailVO.getExpiredYN();
				strMemberAge = dependentDetailVO.getAge().toString();

				frmSumInsuredDetails.set("sMemberName",dependentDetailVO.getName());
				frmSumInsuredDetails.set("sMemberType",dependentDetailVO.getMemberType());
				frmSumInsuredDetails.set("sRelationship",dependentDetailVO.getRelationDesc());
				frmSumInsuredDetails.set("sAge",dependentDetailVO.getAge().toString());
				frmSumInsuredDetails.set("sGender",dependentDetailVO.getGenderDescription());
				frmSumInsuredDetails.set("sDOB",TTKCommon.getFormattedDate(dependentDetailVO.getDateOfBirth()));
				frmSumInsuredDetails.set("sTotSumInsured",dependentDetailVO.getTotalSumInsured().toString());
				frmSumInsuredDetails.set("showMemberDetails","Y");
				/*if("0".equals(dependentDetailVO.getTotalSumInsured().toString())){
					frmSumInsuredDetails.set("sPlanPremium",null);
				}*/
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			else
			{
				strCancelYN=frmMemberDetails.getString("cancelYN");
				strExpiredYN= frmMemberDetails.getString("expiredYN");
				frmSumInsuredDetails.set("showMemberDetails","N");
			}//end of else
			ArrayList<Object> alParams = new ArrayList<Object>();
			alParams.add(lngPolicySeqID);
			alParams.add(lngPolicyGroupSeqID);
			alParams.add(lngMemberSeqID);
			alParams.add(strMemberAge);

			//build the caption according to the User Type
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				log.debug("Login Type :"+userSecurityProfile.getLoginType());
				sbfCaption.append( onlinePolicyVO.getPolicyNbr() +"]");
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E"))
			{
				log.debug("Login Type :"+userSecurityProfile.getLoginType());
				sbfCaption.append( userSecurityProfile.getPolicyNo() +"]");
			}//end of else if(userSecurityProfile.getLoginType().equals("E"))

			if(dependentDetailVO!=null){
				sbfCaption.append("["+dependentDetailVO.getEnrollmentID()+"][" + dependentDetailVO.getName()+"]");
				frmSumInsuredDetails.set("lnMemSeqID",(Long)dependentDetailVO.getMemberSeqID() );
				frmSumInsuredDetails.set("dtOFInception", dependentDetailVO.getInceptionDate() );
			}//end of if(dependentDetailVO!=null)
			else{
				sbfCaption.append("["+frmMemberDetails.get("enrollmentNbr")+"]");
				sbfCaption.append("["+frmMemberDetails.get("insuredName")+"]");
				//Date of Inception should be null in case clicking Family Sum Insured icon
				frmSumInsuredDetails.set("dtOFInception", null );
			}//end of else
			frmSumInsuredDetails.set("caption",sbfCaption.toString());
			if(!strSortID.equals(""))
			{
				tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableData.modifySearchData("sort");//modify the search data
			}// end of if(!strSortID.equals(""))
			else
			{
				tableData.createTableInfo("AdditionalSumInsuredDetailsTable",new ArrayList());
				tableData.setSearchData(alParams);
				tableData.modifySearchData("search");
			}// end of else
			alSumInsList = onlineAccessManagerObject.getSumInsuredPlan(tableData.getSearchData());
			tableData.setData(alSumInsList);
			//to traverse through the array list to find out previously selected records
			int inAlLength = alSumInsList.size();
			String[] strChkArray = new String[inAlLength];
			String[] strChkArray1 = new String[inAlLength];
			String strtemp = "";
			String strtemp1= "";
			for(int i=0;i<inAlLength;i++)
			{
				sumInsuredVO = (SumInsuredVO)alSumInsList.get(i);
				String strSelectedYN = sumInsuredVO.getSelectedYN();
				strChkArray1[i] = sumInsuredVO.getSelectedYN();

				strtemp=strtemp+(strChkArray1[i]);
				strtemp1=strtemp1.concat(strChkArray1[i]);
				 if(strSelectedYN.equals("Y"))
				 {
					log.debug("before setting planAmt" +sumInsuredVO.getPlanPremium());
					frmSumInsuredDetails.set("sPlanAmt",sumInsuredVO.getPlanAmount());
					frmSumInsuredDetails.set("sPlanPremium",sumInsuredVO.getPlanPremium());
					// Change added for KOC1227C
					frmSumInsuredDetails.set("sPremiumDeductOption",sumInsuredVO.getPremiumDeductionOption());
					log.debug("after setting planAmt");
					strChkArray[i] = i+"";
				 }//end of if(strSelectedYN.equals("Y"))
				 else
				 {
					strChkArray[i] = -1+"";
				 }//end of else

				 if(strtemp.equals("NNN"))
				  {
						
						frmSumInsuredDetails.set("sPlanPremium",null);
						frmSumInsuredDetails.set("sPlanAmt",null);
				  }//end of if(sbf.equals("NNN"))
			  }//end of for(int i=0;i<alSumInsList.size();i++)

			if(inAlLength==0)
			{
				frmSumInsuredDetails.set("sPlanPremium",null);
			}
			tableData.setSelectedCheckBoxInfo(1,strChkArray);
			frmSumInsuredDetails.set("allowCancYN",strCancelYN);
			frmSumInsuredDetails.set("allowExpiredYN",strExpiredYN);
			request.getSession().setAttribute("tableData",tableData);
			request.getSession().removeAttribute("dependentDetailVO");
			request.getSession().setAttribute("frmMemberDetails",frmMemberDetails);
			request.getSession().setAttribute("frmSumInsuredDetails",frmSumInsuredDetails);
			return (mapping.findForward(strAdditionalSumInsuredDetails));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strAdditionalSumInsuredDetails));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to list out the sum insured plans for selection
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doListPlan(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.info("Inside doListPlan AdditionalSumInsuredAction ");
			TableData  tableData =TTKCommon.getTableData(request);
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			OnlineAccessPolicyVO onlinePolicyVO = null;
			SumInsuredVO sumInsuredVO = null;
			ArrayList alSumInsList = null;
			ArrayList alRelationShip = null;
			// prorata cr KOC1184
			Long lngPolicyGroupSeqID = null;
			DependentDetailVO dependentDetailVO = (DependentDetailVO)request.getAttribute("dependentDetailVO");
			OnlineAccessManager onlineAccessManagerObject =this.getOnlineAccessManagerObject();
			StringBuffer sbfCaption = new StringBuffer();
			sbfCaption.append("Additional Sum Insured Details - [");
			DynaActionForm frmSumInsuredDetails = (DynaActionForm)form;
			DynaActionForm frmMemberDetails = (DynaActionForm) request.getSession().getAttribute("frmMemberDetails");
			// prorata CR KOC1184
			lngPolicyGroupSeqID = TTKCommon.getLong((String)frmMemberDetails.get("policyGroupSeqID"));
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
			}//end of if(userSecurityProfile.getLoginType().equals("H"))

			Long lngPolicySeqID = null;
			String strProdPolicySeqID= TTKCommon.checkNull(frmMemberDetails.getString("prodPlanSeqID"));
			String strCancelYN="";
			String strExpiredYN="";
			String strMemberAge = dependentDetailVO.getAge().toString();
			log.debug("genderYN is " + dependentDetailVO.getGenderYN());
			lngPolicySeqID = TTKCommon.getLong((String)frmMemberDetails.get("policySeqID"));
			{
				strCancelYN=frmMemberDetails.getString("cancelYN");
				strExpiredYN= frmMemberDetails.getString("expiredYN");
			}
			log.debug("strCancelYN :"+strCancelYN);
			ArrayList<Object> alParams = new ArrayList<Object>();
			alParams.add(lngPolicySeqID);
			alParams.add(strMemberAge);
			alParams.add(null); //prod_plan_seq_id
			// pro rata cr KOC1184
			alParams.add(lngPolicyGroupSeqID);
			//build the caption according to the User Type
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				log.debug("Login Type :"+userSecurityProfile.getLoginType());
				sbfCaption.append( onlinePolicyVO.getPolicyNbr() +"]");
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E"))
			{
				log.debug("Login Type :"+userSecurityProfile.getLoginType());
				sbfCaption.append( userSecurityProfile.getPolicyNo() +"]");
			}//end of else if(userSecurityProfile.getLoginType().equals("E"))

			{
				sbfCaption.append("["+frmMemberDetails.get("enrollmentNbr")+"]");
				sbfCaption.append("["+frmMemberDetails.get("insuredName")+"]");
			}//end of else
			frmSumInsuredDetails.set("caption",sbfCaption.toString());

				tableData.createTableInfo("AdditionalSumInsuredDetailsTable",new ArrayList());
				tableData.setSearchData(alParams);
				tableData.modifySearchData("search");

			alSumInsList = onlineAccessManagerObject.getSumInsuredPlanInfo(tableData.getSearchData());
			tableData.setData(alSumInsList);
			//to traverse through the array list to find out previously selected records
			int inAlLength = alSumInsList.size();
			String[] strChkArray = new String[inAlLength];
			log.debug("strProdPolicySeqID is "+strProdPolicySeqID);

			for(int i=0;i<inAlLength;i++)
			{
				sumInsuredVO = (SumInsuredVO)alSumInsList.get(i);
				log.debug(sumInsuredVO.getProdPlanSeqId());
					if(strProdPolicySeqID.equals(sumInsuredVO.getProdPlanSeqId())){
						strChkArray[i] = i+"";
					}//end of if(strSelectedYN.equals("Y"))
					else {
						strChkArray[i] = -1+"";
					}//end of else
			}//end of for(int i=0;i<alSumInsList.size();i++)

			//setting form values for Member Details Information in Sum Insured Plan list
			frmSumInsuredDetails.set("sMemberName",dependentDetailVO.getName());
			ArrayList alMemberType=null;
			alMemberType=Cache.getCacheObject("floaterNonFloater");
			for(int i=0;i<alMemberType.size();i++){
				CacheObject tmpCacheObject = (CacheObject)alMemberType.get(i);
				String strCacheID = tmpCacheObject.getCacheId();
				String strMemberTypeID = dependentDetailVO.getMemberTypeID();
				if(strCacheID.equals(strMemberTypeID)){
					frmSumInsuredDetails.set("sMemberType",tmpCacheObject.getCacheDesc());
					break;
				}//end of if(strCacheID.equals(strRelationTypeID))
			}//end of for(int i=0;i<alMemberType.size();i++)

			frmSumInsuredDetails.set("sAge",dependentDetailVO.getAge().toString());
			if("MAL".equals(dependentDetailVO.getGenderTypeID())){
				frmSumInsuredDetails.set("sGender","MALE");
			}//end of if("MAL".equals(dependentDetailVO.getGenderTypeID()))
			else if("FEM".equals(dependentDetailVO.getGenderTypeID())){
				frmSumInsuredDetails.set("sGender","FEMALE");
			}//end of else if("FEM".equals(dependentDetailVO.getGenderTypeID()))
			else if("EUN".equals(dependentDetailVO.getGenderTypeID())){
				frmSumInsuredDetails.set("sGender","EUNUCH");
			}//end of else if("EUN".equals(dependentDetailVO.getGenderTypeID()))
			else {
				frmSumInsuredDetails.set("sGender","");
			}//end of else

			frmSumInsuredDetails.set("sDOB",TTKCommon.getFormattedDate(dependentDetailVO.getDateOfBirth()));
			frmSumInsuredDetails.set("sTotSumInsured",dependentDetailVO.getTotalSumInsured().toString());
			if("0".equals(dependentDetailVO.getTotalSumInsured().toString())){
				frmSumInsuredDetails.set("sPlanPremium",null);
			}

			alRelationShip = (ArrayList)frmMemberDetails.get("alRelationShip");
			int iCheck=-1;
			for(int i=0;i<alRelationShip.size();i++){
				CacheObject tmpCacheObject = (CacheObject)alRelationShip.get(i);
				String strCacheID = tmpCacheObject.getCacheId();
				String strRelationTypeID = dependentDetailVO.getRelationTypeID();
				if(strCacheID.equals(strRelationTypeID)){
					frmSumInsuredDetails.set("sRelationship",tmpCacheObject.getCacheDesc());
					iCheck=0;
					break;
				}//end of if(strCacheID.equals(strRelationTypeID))
			}//end of for(int i=0;i<alRelationShip.size();i++)
			if(iCheck==-1){
				frmSumInsuredDetails.set("sRelationship","");
			}//end of if(iCheck==-1)
			tableData.setSelectedCheckBoxInfo(1,strChkArray);
			frmSumInsuredDetails.set("allowCancYN",strCancelYN);
			frmSumInsuredDetails.set("allowExpiredYN",strExpiredYN);
			request.getSession().setAttribute("tableData",tableData);
			request.getSession().setAttribute("dependentDetailVO",dependentDetailVO);
			request.getSession().setAttribute("frmSumInsuredDetails",frmSumInsuredDetails);
			return mapping.findForward(strAddSumInsDetailSelect);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strAdditionalSumInsuredDetails));
		}//end of catch(Exception exp)
	}//end of doListPlan(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to save the selected plan
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
			HttpServletResponse response) throws Exception {
		try{
			setOnlineLinks(request);
			log.debug("Inside doSave AdditionalSumInsuredAction ");
			String[] strChk = request.getParameterValues("chkopt");
			TableData tableData = (TableData)request.getSession().getAttribute("tableData");
			SumInsuredVO sumInsuredVO = null;
			DynaActionForm frmMemberDetails = (DynaActionForm) request.getSession().getAttribute("frmMemberDetails");
			DynaActionForm frmSumInsuredDetails =(DynaActionForm)request.getSession().getAttribute("frmSumInsuredDetails");
			String strCancelYN=(String)frmSumInsuredDetails.get("allowCancYN");
			String strExpiredYN=(String)frmSumInsuredDetails.get("allowExpiredYN");
			log.debug("strCancYN in doSave :"+strCancelYN);
			log.debug("strExpiredYN in doSave :"+strExpiredYN);
			OnlineAccessManager onlineAccessManagerObject =this.getOnlineAccessManagerObject();
			long iResult = 0;
			if(strChk!=null&&strChk.length!=0)
			{
				String[] strCheckedArr = null;
				//strChk has selected check box values as String array
				for(int i=0; i<strChk.length;i++)
				{
					sumInsuredVO = (SumInsuredVO) tableData.getData().get(Integer.parseInt(strChk[i]));
					frmSumInsuredDetails.set("sSelPlanAmt",sumInsuredVO.getPlanAmount());
					frmSumInsuredDetails.set("sSelPlanPremium",sumInsuredVO.getPlanPremium());
					//block start to change the array strCheckedArr based on newly selected record(s)
					//based upon strChk which has newly selected chcekbox values
					strCheckedArr = tableData.getAllSelectedCheckBoxInfo();
					for(int j=0;j<strCheckedArr.length;j++){
						if(Integer.parseInt(strChk[i])==j){
							strCheckedArr[j] = j+"";
						}//end of if(Integer.parseInt(strChk[i])==j)
						else {
							strCheckedArr[j] = -1+"";
						}//end of else
					}//end of for(int j=0;j<strCheckedArr.length;j++)
				}//end of for(int i=0; i<strChk.length;i++)
				tableData.setSelectedCheckBoxInfo(1,strCheckedArr);
			}//end of if(strChk!=null&&strChk.length!=0)

			sumInsuredVO.setPolicyGroupSeqID(TTKCommon.getLong((String)frmMemberDetails.get("policyGroupSeqID")));//new Long("805101"));
			sumInsuredVO.setPolicySeqID(TTKCommon.getLong((String)frmMemberDetails.get("policySeqID")));//new Long("320001"));
			sumInsuredVO.setMemberSeqID((Long)frmSumInsuredDetails.get("lnMemSeqID"));//new Long("2001371"));

			log.info("Policy Group Seq ID is " + sumInsuredVO.getPolicyGroupSeqID());
			log.info("Policy Seq ID is " + sumInsuredVO.getPolicySeqID());
			log.info("Member seq Id is " + sumInsuredVO.getMemberSeqID());
			log.info("Plan Amount is " + sumInsuredVO.getPlanAmount());
			log.info("Prd Plan Seq ID is " + sumInsuredVO.getProdPlanSeqId());
			log.info("Plan Premium is " + sumInsuredVO.getPlanPremium());

			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			if(userSecurityProfile.getLoginType().equals("H")){
				sumInsuredVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E")){
				//sumInsuredVO.setUpdatedBy(TTKCommon.getLong(userSecurityProfile.getUSER_ID()));
				sumInsuredVO.setUpdatedBy(new Long(1));
			}//end of else
			sumInsuredVO.setInceptionDate((Date)frmSumInsuredDetails.get("dtOFInception"));

			// Change made for KOC1227C providing 2 checkboxes
			String predeductopt=request.getParameter("predeductopt");
			sumInsuredVO.setPremiumDeductionOption(predeductopt);
			frmSumInsuredDetails.set("sPremiumDeductOption",sumInsuredVO.getPremiumDeductionOption());

			if(sumInsuredVO != null){
				log.info("sumInsuredVO is not null");
				iResult = onlineAccessManagerObject.saveMemInsured(sumInsuredVO);
				log.info("iResult is  " + iResult);
			}//end of if(sumInsuredVO != null)
			log.info("iResult is :" + iResult);
			// Added Rule related validations as per Sreeraj instructions on 27/02/2008
			if(iResult <0){
				log.info("Errors are there..........");
				RuleManager ruleManager=this.getRuleManagerObject();
				//RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();
				ArrayList alValidationError=ruleManager.getValidationErrorList(new Long(iResult));

				//prepare Error messages
				//ArrayList alErrorMessage=ruleXMLHelper.prepareErrorMessage(alValidationError,request);
				request.setAttribute("BUSINESS_ERRORS",alValidationError);
				return this.getForward(strAdditionalSumInsuredDetails, mapping, request);
			}//end of addition
			if(iResult != 0)
			{
				request.setAttribute("updated","message.savedSuccessfully");
				BigDecimal bdTotSumInsured =new BigDecimal(frmSumInsuredDetails.getString("sTotSumInsured"));
				BigDecimal bdPlanAmt 	   =new BigDecimal(frmSumInsuredDetails.getString("sPlanAmt"));
				BigDecimal bdSelPlanAmt	   =new BigDecimal(frmSumInsuredDetails.getString("sSelPlanAmt"));
				log.info("bdTotSumInsured issssssssssssssssssssssssssss "+ bdTotSumInsured);
				log.info("bdPlanAmt" + bdPlanAmt);
				log.info("bdSelPlanAmt is " + bdSelPlanAmt);
				bdTotSumInsured = bdTotSumInsured.subtract(bdPlanAmt);
				bdTotSumInsured = bdTotSumInsured.add(bdSelPlanAmt);
				bdPlanAmt=bdSelPlanAmt;
				log.info("bdTotSumInsured  "+ bdTotSumInsured);
				log.info("bdPlanAmt" + bdPlanAmt);
				log.info("bdSelPlanAmt is " + bdSelPlanAmt);
				frmSumInsuredDetails.set("sTotSumInsured",bdTotSumInsured.toString());
				frmSumInsuredDetails.set("sPlanAmt",bdPlanAmt.toString());
				frmSumInsuredDetails.set("sPlanPremium",frmSumInsuredDetails.getString("sSelPlanPremium"));
			}//end of if(iResult == 0)
			frmSumInsuredDetails.set("allowCancYN",strCancelYN);
			frmSumInsuredDetails.set("allowExpiredYN",strExpiredYN);
			request.getSession().setAttribute("frmSumInsuredDetails",frmSumInsuredDetails);
			request.getSession().setAttribute("tableData",tableData);
			return (mapping.findForward(strAdditionalSumInsuredDetails));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strSavePlan));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is called when a plan is selected either from grid click flow or add flow
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectPlan(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
			setOnlineLinks(request);
			log.debug("Inside doSelectPlan AdditionalSumInsuredAction ");
			String[] strChk = request.getParameterValues("chkopt");
			TableData tableData = (TableData)request.getSession().getAttribute("tableData");
			SumInsuredVO sumInsuredVO = null;
			DynaActionForm frmSumInsuredDetails =(DynaActionForm)request.getSession().getAttribute("frmSumInsuredDetails");
			if(strChk!=null&&strChk.length!=0)
			{
				String[] strCheckedArr = null;
				//strChk has selected check box values as String array
				for(int i=0; i<strChk.length;i++)
				{
					sumInsuredVO = (SumInsuredVO) tableData.getData().get(Integer.parseInt(strChk[i]));
					frmSumInsuredDetails.set("sSelPlanAmt",sumInsuredVO.getPlanAmount());
					frmSumInsuredDetails.set("sPlanPremium",sumInsuredVO.getPlanPremium());
					//block start to change the array strCheckedArr based on newly selected record(s)
					//based upon strChk which has newly selected chcekbox values
					strCheckedArr = tableData.getAllSelectedCheckBoxInfo();
					for(int j=0;j<strCheckedArr.length;j++){
						if(Integer.parseInt(strChk[i])==j){
							strCheckedArr[j] = j+"";
						}//end of if(Integer.parseInt(strChk[i])==j)
						else {
							strCheckedArr[j] = -1+"";
						}//end of else
					}//end of for(int j=0;j<strCheckedArr.length;j++)
				}//end of for(int i=0; i<strChk.length;i++)
				tableData.setSelectedCheckBoxInfo(1,strCheckedArr);
				BigDecimal bdTotSumInsured = null;
				if(!(frmSumInsuredDetails.getString("sTotSumInsured")==null || frmSumInsuredDetails.getString("sTotSumInsured").equals(""))){
					bdTotSumInsured =new BigDecimal(frmSumInsuredDetails.getString("sTotSumInsured"));
				}
				else {
					bdTotSumInsured=new BigDecimal("0");
				}//end of else
				BigDecimal bdPlanAmt = null;
				BigDecimal bdSelPlanAmt=null;
				if(bdTotSumInsured.intValue()==0)
				{
					bdPlanAmt=new BigDecimal("0");
				}//end of if(bdTotSumInsured.intValue()==0)
				else{
					//log.info("inside else");
					//log.info("planamt in the form is " + frmSumInsuredDetails.getString("sPlanAmt"));
					if(frmSumInsuredDetails.get("sPlanAmt")!=null){
						bdPlanAmt=TTKCommon.getBigDecimal(frmSumInsuredDetails.getString("sPlanAmt"));
					}//end of if(frmSumInsuredDetails.get("sPlanAmt")!=null)
				}//end of else
				bdSelPlanAmt =new BigDecimal(frmSumInsuredDetails.getString("sSelPlanAmt"));
				log.info("bdTotSumInsured is "+ bdTotSumInsured);
				log.info("bdPlanAmt" + bdPlanAmt);
				log.info("bdSelPlanAmt is " + bdSelPlanAmt);
				if(bdPlanAmt!=null){
					bdTotSumInsured = bdTotSumInsured.subtract(bdPlanAmt);
				}//end of if(bdPlanAmt!=null)
				bdTotSumInsured = bdTotSumInsured.add(bdSelPlanAmt);

				bdPlanAmt=bdSelPlanAmt;
				log.info("bdTotSumInsured is "+ bdTotSumInsured);
				log.info("bdPlanAmt" + bdPlanAmt);
				log.info("bdSelPlanAmt is " + bdSelPlanAmt);
				frmSumInsuredDetails.set("sTotSumInsured",bdTotSumInsured.toString());
				frmSumInsuredDetails.set("sPlanAmt",bdPlanAmt.toString());
			}//end of if(strChk!=null&&strChk.length!=0)
			request.getSession().setAttribute("frmSumInsuredDetails",frmSumInsuredDetails);
			request.getSession().setAttribute("tableData",tableData);
			if("grid".equals(request.getParameter("flow"))){
				return (mapping.findForward(strAdditionalSumInsuredDetails));
			}//end of if("grid".equals(request.getParameter("flow")))
			else if("add".equals(request.getParameter("flow"))){
				return (mapping.findForward(strAddSumInsDetailSelect));
			}//end of else if("add".equals(request.getParameter("flow")))
			return null;
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strSavePlan));
		}//end of catch(Exception exp)
	}//end of doSelectPlan(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


	public ActionForward doSelect(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
			
			setOnlineLinks(request);
			log.info("Inside doSelect AdditionalSumInsuredAction ");
			String[] strChk = request.getParameterValues("chkopt");
			TableData tableData = (TableData)request.getSession().getAttribute("tableData");
			SumInsuredVO sumInsuredVO = null;
			DynaActionForm frmMemberDetails = (DynaActionForm) request.getSession().getAttribute("frmMemberDetails");
			if(strChk!=null&&strChk.length!=0)
			{
				String[] strCheckedArr = null;
				//strChk has selected check box values as String array
				for(int i=0; i<strChk.length;i++)
				{
					sumInsuredVO = (SumInsuredVO) tableData.getData().get(Integer.parseInt(strChk[i]));
					//block start to change the array strCheckedArr based on newly selected record(s)
					//based upon strChk which has newly selected chcekbox values
					strCheckedArr = tableData.getAllSelectedCheckBoxInfo();
					for(int j=0;j<strCheckedArr.length;j++){
						if(Integer.parseInt(strChk[i])==j){
							strCheckedArr[j] = j+"";
						}//end of if(Integer.parseInt(strChk[i])==j)
						else {
							strCheckedArr[j] = -1+"";
						}//end of else
					}//end of for(int j=0;j<strCheckedArr.length;j++)
				}//end of for(int i=0; i<strChk.length;i++)
				tableData.setSelectedCheckBoxInfo(1,strCheckedArr);
			}//end of if(strChk!=null&&strChk.length!=0)
			log.debug("Plan Amount is " + sumInsuredVO.getPlanAmount());
			BigDecimal bdTotSumInsured = null;
			BigDecimal bdPlanAmount=null;
			BigDecimal bdPlanPremium=null;
			Long lngProdPlanSeqID = null;

			if(frmMemberDetails.getString("totalSumInsured")!=""){
				bdTotSumInsured = new BigDecimal(frmMemberDetails.getString("totalSumInsured"));
				log.info(bdTotSumInsured);
			}
			else {
				bdTotSumInsured = TTKCommon.getBigDecimal("0");
				bdPlanAmount=TTKCommon.getBigDecimal("0");
			}
			if(frmMemberDetails.getString("planAmt")!=""){
				bdPlanAmount = new BigDecimal(frmMemberDetails.getString("planAmt"));
				frmMemberDetails.set("totalSumInsured",bdTotSumInsured.subtract(bdPlanAmount).toString());
				log.info(frmMemberDetails.getString("totalSumInsured"));
			}
			if(sumInsuredVO.getPlanAmount()!=null){
				frmMemberDetails.set("planAmt",sumInsuredVO.getPlanAmount());
				log.info(frmMemberDetails.getString("planAmt"));
			}
			if(frmMemberDetails.getString("totalSumInsured")!=""){
				bdTotSumInsured = new BigDecimal(frmMemberDetails.getString("totalSumInsured"));
				log.info(bdTotSumInsured);
			}
			if(sumInsuredVO.getPlanAmount()!=null){
				bdPlanAmount = new BigDecimal(sumInsuredVO.getPlanAmount());
				frmMemberDetails.set("planAmt",sumInsuredVO.getPlanAmount());
				log.info(bdPlanAmount);
			}
			if(bdTotSumInsured!=null && bdPlanAmount!=null){
				bdTotSumInsured = bdTotSumInsured.add(bdPlanAmount);
				log.info(bdTotSumInsured);
			}
			if(sumInsuredVO.getProdPlanSeqId()!=null){
				log.info("prodplanseq id is " + sumInsuredVO.getProdPlanSeqId());
				frmMemberDetails.set("prodPlanSeqID",sumInsuredVO.getProdPlanSeqId());
				lngProdPlanSeqID = new Long(sumInsuredVO.getProdPlanSeqId());
			}//end of if(sumInsuredVO.getProdPlanSeqId()!=null)

			// Prorata CR KOC1184
		/*	if(sumInsuredVO.getPlanPremium()!=null){
				frmMemberDetails.set("addPremium",sumInsuredVO.getPlanPremium());
				bdPlanPremium=new BigDecimal(sumInsuredVO.getPlanPremium());
			}//end of if(sumInsuredVO.getPlanPremium()!=null)*/

			// Prorata CR KOC1184
			if(sumInsuredVO.getProRata()!=null){
				log.info("ProRata is " + sumInsuredVO.getProRata());
				frmMemberDetails.set("addPremium",sumInsuredVO.getProRata());
				bdPlanPremium=new BigDecimal(sumInsuredVO.getProRata());
			}//end of if(sumInsuredVO.getProRata()!=null)

			frmMemberDetails.set("totalSumInsured",bdTotSumInsured.toString());
			DependentDetailVO dependentDetailVO  = (DependentDetailVO) request.getSession().getAttribute("dependentDetailVO");
			dependentDetailVO.setTotalSumInsured(bdTotSumInsured);
			dependentDetailVO.setPlanAmt(bdPlanAmount);
			dependentDetailVO.setAddPremium(bdPlanPremium);
			dependentDetailVO.setProdPlanSeqID(lngProdPlanSeqID);
			if(dependentDetailVO.getGenderYN()!=null){
				frmMemberDetails.set("genderYN",dependentDetailVO.getGenderYN());
			}
			//Change added for KOC1227C
			String predeductopt=request.getParameter("predeductopt");
			dependentDetailVO.setPremiumDeductionOption(predeductopt);
			frmMemberDetails.set("premiumDeductOption",predeductopt);

			request.getSession().setAttribute("dependentDetailVO",dependentDetailVO);
			request.getSession().setAttribute("frmMemberDetails",frmMemberDetails);
			return (mapping.findForward(strCloseDetails));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strSavePlan));
		}//end of catch(Exception exp)
	}//end of doSelect(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


	/**
	 * This method is used to clear the additional sum insured plan as saved.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doClearAdditionalSum(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.info("Inside doDeleteAdditionalSum AdditionalSumInsuredAction ");
			UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			String[] strChk = request.getParameterValues("chkopt");
			DynaActionForm frmMemberDetails = (DynaActionForm) request.getSession().getAttribute("frmMemberDetails");
			DynaActionForm frmSumInsuredDetails = (DynaActionForm)form;
			String strCaption = (String)frmSumInsuredDetails.get("caption");
			String strCancYN=(String)frmSumInsuredDetails.get("allowCancYN");
			String strExpiredYN=(String)frmSumInsuredDetails.get("allowExpiredYN");
			log.info("strCancYN in doClear :"+strCancYN);
			log.info("strExpiredYN in doClear :"+strExpiredYN);
			OnlineAccessManager onlineAccessManagerObject =this.getOnlineAccessManagerObject();
			TableData tableData = (TableData)request.getSession().getAttribute("tableData");
			SumInsuredVO sumInsuredVO = null;
			int iResult = 0;
			int selectedRow = -1;
			String[] strCheckedArr = null;
			Long lngUserSeqID = null;
			if(userSecurityProfile.getLoginType().equals("H")){
				lngUserSeqID = userSecurityProfile.getUSER_SEQ_ID();
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E")){
				lngUserSeqID = new Long(1);
			}//end of if(userSecurityProfile.getLoginType().equals("E"))

			if(strChk!=null&&strChk.length!=0)
			{
				//strChk has selected check box values as String array
				for(int i=0; i<strChk.length;i++)
				{
					//block start to change the array strCheckedArr based on newly selected record(s)
					//based upon strChk which has newly selected chcekbox values
					strCheckedArr = tableData.getAllSelectedCheckBoxInfo();
					for(int j=0 ;j<strCheckedArr.length;j++){
						if(Integer.parseInt(strChk[i])==j){
							strCheckedArr[j] = j+"";
							selectedRow = j;
							sumInsuredVO = (SumInsuredVO) tableData.getData().get(Integer.parseInt(strChk[i]));
						}//end of if(Integer.parseInt(strChk[i])==j)
						else {
							strCheckedArr[j] = -1+"";
						}//end of else
					}//end of for(int j=0;j<strCheckedArr.length;j++)
				}//end of for(int i=0; i<strChk.length;i++)

				tableData.setSelectedCheckBoxInfo(1,strCheckedArr);
			}//end of if(strChk!=null&&strChk.length!=0)

			if(sumInsuredVO != null){
				log.info("sumInsuredVO is not null");
				sumInsuredVO.setPolicyGroupSeqID(TTKCommon.getLong((String)frmMemberDetails.get("policyGroupSeqID")));//new Long("805101"));
				sumInsuredVO.setPolicySeqID(TTKCommon.getLong((String)frmMemberDetails.get("policySeqID")));//new Long("320001"));
				sumInsuredVO.setMemberSeqID((Long)frmSumInsuredDetails.get("lnMemSeqID"));//new Long("2001371"));
				sumInsuredVO.setUpdatedBy(lngUserSeqID);
				log.info("memberseqid in clearSumInsuredPlan method is " +(Long)frmSumInsuredDetails.get("lnMemSeqID") );
				iResult = onlineAccessManagerObject.clearAdditionalSumInsured(sumInsuredVO);
			}//end of if(sumInsuredVO != null)
			log.debug("iResult is :"  + iResult);
			if(iResult != 0)
			{
				strCheckedArr[selectedRow] = -1+"";
				tableData.setSelectedCheckBoxInfo(1,strCheckedArr);
				//frmSumInsuredDetails.initialize(mapping);
				//this has to be changed properly
				request.setAttribute("updated","message.planClearedSuccessfully");
				//if the plan is cleared successfully planAmt should be cleared from total sum insured
				BigDecimal bdTotSumInsured =new BigDecimal(frmSumInsuredDetails.getString("sTotSumInsured"));
				BigDecimal bdPlanAmt 	   =new BigDecimal(frmSumInsuredDetails.getString("sPlanAmt"));
				//BigDecimal bdSelPlanAmt	   =new BigDecimal(frmSumInsuredDetails.getString("sSelPlanAmt"));
				bdTotSumInsured = (bdTotSumInsured.subtract(bdPlanAmt));
				frmSumInsuredDetails.set("sTotSumInsured",bdTotSumInsured.toString());
				frmSumInsuredDetails.set("sPlanAmt","0");
				frmSumInsuredDetails.set("sPlanPremium","0");
			}//end of if(iResult != 0)
			request.getSession().setAttribute("tableData",tableData);
			frmSumInsuredDetails.set("allowCancYN",strCancYN);
			frmSumInsuredDetails.set("allowExpiredYN",strExpiredYN);
			frmSumInsuredDetails.set("caption",strCaption);
			request.getSession().setAttribute("frmSumInsuredDetails",frmSumInsuredDetails);
			return (mapping.findForward(strAdditionalSumInsuredDetails));
		}
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strClearPlan));
		}//end of catch(Exception exp)

	}//end of doClearAdditionalSum(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)


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
			setOnlineLinks(request);
			log.info("Inside AdditionalSumInsuredAction doClose");
			/*DependentDetailVO dependentDetailVO = (DependentDetailVO)request.getAttribute("dependentDetailVO");
			request.setAttribute("dependentDetailVO",dependentDetailVO);*/
			return mapping.findForward(strCloseDetails);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request,mapping,new TTKException(exp,strClose));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the onlineAccessManager session object for invoking methods on it.
	 * @return onlineAccessManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private OnlineAccessManager getOnlineAccessManagerObject() throws TTKException
	{
		OnlineAccessManager onlineAccessManager = null;
		try
		{
			if(onlineAccessManager == null)
			{
				InitialContext ctx = new InitialContext();
				onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
				log.debug("Inside getOnlineAccessManagerObject: onlineAccessManager: " + onlineAccessManager);
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strFailure);
		}//end of catch
		return onlineAccessManager;
	}//end of getOnlineAccessManagerObject()

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

}//end of class AdditionalSumInsuredAction


