/**
* @ (#) OnlineMemberDetailsAction.java Jan 14th 2008
* Project 		: TTK HealthCare Services
* File 			: OnlineMemberDetailsAction.java
* Author 		: Krupa J
* Company 		: Span Systems Corporation
* Date Created 	: Jan 14th 2008
*
* @author 		: Krupa J
* Modified by 	:
* Modified date :
* Reason 		:
*/

package com.ttk.action.onlineforms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.log4j.Logger;
import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.action.tree.TreeData;
import com.ttk.business.administration.RuleManager;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.OnlineAccessPolicyVO;
import com.ttk.dto.onlineforms.DependentDetailVO;
import com.ttk.dto.onlineforms.MemberPermVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import formdef.plugin.util.FormUtils;

/**
 * This class is used to display,add and edit a member in the Weblogin flow
 *
 */

public class OnlineMemberDetailsAction extends TTKAction
{
		private static Logger log = Logger.getLogger( OnlineMemberDetailsAction.class );
		private static final String strMemberDetails="onlinememberdetails";
		private static final String strCloseMember="onlinemember";
		private static final String strListSumInsPlan="listsuminsuredplan";
		//Exception Message Identifier
	    private static final String strMember="groupdetail";
	    private static final String strFailure="failure";

		/**
		 * This method is used to initialize the search grid.
		 * Finally it forwards to the appropriate view based on the specified forward mappings
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
				setOnlineLinks(request);
				log.info("do OnlineMemberDetailsAction doDefault");
				DynaActionForm frmMemberDetails=(DynaActionForm)form;
				OnlineAccessManager onlineAccessManager=this.getOnlineAccessManagerObject();
				TableData  tableData =TTKCommon.getTableData(request);
				TreeData treeData =(TreeData)request.getSession().getAttribute("treeData");
				UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
				OnlineAccessPolicyVO onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
				//MemberManager memberManager=this.getMemberManager();
				DependentDetailVO dependentDetailVO =new DependentDetailVO();
				StringBuffer sbfCaption=new StringBuffer();
				String strAllowAddYN="";
				String strInsuredName="";
				String strEnrollmentNbr="";
				String strPolicyStatusTypeID = "";
				BigDecimal bgDefaultSumInsured=null;
				//get the Policy details from the treedata if coming here by selecting the object from tree
				if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
				{
					frmMemberDetails.initialize(mapping); //initialize the form bean
					request.getSession().removeAttribute("frmMemberDetails");
					request.getSession().removeAttribute("dependentDetailVO");
					// Fix mismatch Additional Sum Insured issue
					request.getSession().removeAttribute("frmSumInsuredDetails");
					MemberVO memberVO=(MemberVO)treeData.getSelectedObject((String)request.
							getParameter("selectedroot"),null);
					dependentDetailVO.setPolicyGroupSeqID(memberVO.getPolicyGroupSeqID());
					dependentDetailVO.setPolicySeqID(memberVO.getPolicySeqID());
					dependentDetailVO.setAllowAddSumYN(memberVO.getAllowAddSumYN());
					dependentDetailVO.setAbbrCode(memberVO.getAbbrCode());
					dependentDetailVO.setMemberTypeID(memberVO.getMemberTypeID());
					dependentDetailVO.setCancelYN(memberVO.getCancleYN());
					dependentDetailVO.setExpiredYN(memberVO.getExpiredYN());
					if(userSecurityProfile.getLoginType().equals("H"))
					{
						strAllowAddYN=memberVO.getDependentAddYN();
						strPolicyStatusTypeID=memberVO.getPolicyStatusTypeID();
					}//end of if(userSecurityProfile.getLoginType().equals("H"))
					else
					{
						strAllowAddYN=memberVO.getEmpAddYN();
						strPolicyStatusTypeID=memberVO.getPolicyStatusTypeID();
					}//end of else
					strInsuredName=memberVO.getInsuredName();
					strEnrollmentNbr=memberVO.getEnrollmentNbr();
					bgDefaultSumInsured=memberVO.getPolicySumInsured();
				}//end of if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))

				else//this block is executed on click of Close in Add. Sum Insured screen
				{
					dependentDetailVO.setPolicyGroupSeqID(TTKCommon.getLong((String)frmMemberDetails.get("policyGroupSeqID")));
					dependentDetailVO.setPolicySeqID(TTKCommon.getLong((String)frmMemberDetails.get("policySeqID")));
					dependentDetailVO.setAllowAddSumYN((String)frmMemberDetails.get("allowAddSumYN"));
					dependentDetailVO.setAbbrCode((String)frmMemberDetails.get("abbrCode"));
					dependentDetailVO.setMemberTypeID((String)frmMemberDetails.get("memberTypeID"));
					dependentDetailVO.setCancelYN((String)frmMemberDetails.get("cancelYN"));
					dependentDetailVO.setExpiredYN((String)frmMemberDetails.get("expiredYN"));
					//while coming back after selecting the plan
					//dependentDetailVO is taken from the request which has values entered
					//in the Member Details Information fieldset in member details screen
					strAllowAddYN=(String)frmMemberDetails.get("allowAddYN");
					strPolicyStatusTypeID=(String)frmMemberDetails.get("policyStatusTypeID");
					strInsuredName=(String)frmMemberDetails.get("insuredName");
					strEnrollmentNbr=(String)frmMemberDetails.get("enrollmentNbr");
					bgDefaultSumInsured=TTKCommon.getBigDecimal(frmMemberDetails.getString("defaultSumInsured"));
				}//end of else

				//get the Relationship list based on the Ins. Company
				//ArrayList alRelationShip = memberManager.getRelationshipCode(dependentDetailVO.getAbbrCode());
				ArrayList alRelationShip = onlineAccessManager.getRelationshipCode(dependentDetailVO.getPolicySeqID(),dependentDetailVO.getAbbrCode());
				dependentDetailVO.setRelationTypeID(dependentDetailVO.getRelationTypeID()+"#"+dependentDetailVO.getGenderYN());
				String strRelTypeID = "NSF#"+dependentDetailVO.getGenderYN();
				/*log.info("Cancel YN :"+dependentDetailVO.getCancelYN());
				log.info("Policy SeqID :"+dependentDetailVO.getPolicySeqID().toString());
				log.info("Policy Group SeqID :"+dependentDetailVO.getPolicyGroupSeqID().toString());*/
				frmMemberDetails =(DynaActionForm)FormUtils.setFormValues("frmMemberDetails",dependentDetailVO,
						this,mapping,request);
				//added for Oracle Declaration
				frmMemberDetails.set("groupId",userSecurityProfile.getGroupID());
				request.getSession().setAttribute("groupId",userSecurityProfile.getGroupID());//Koc Netsol
				//Ended		
				//Ended		
				tableData.createTableInfo("OnlineMemberTable",null);
				tableData.setSearchData(this.populateSearchCriteria(frmMemberDetails,request,userSecurityProfile.getLoginType()));
				tableData.modifySearchData("search");

				//call business layer to get the dependant list of family/Group
				ArrayList alMemberDetails=onlineAccessManager.dependentList(tableData.getSearchData());

				if(!frmMemberDetails.getString("relationTypeID").equalsIgnoreCase(strRelTypeID))
				{
					frmMemberDetails.set("name","");
				}//end of if(!frmMemberDetails.getString("relationTypeID").equalsIgnoreCase(strRelTypeID))

				MemberPermVO memberPermVO = new MemberPermVO();
				//set the field display properties
				ArrayList<Object> alFieldInfo=onlineAccessManager.getFieldInfo(dependentDetailVO.getPolicySeqID(),dependentDetailVO.getPolicyGroupSeqID());
				if(alFieldInfo!= null && alFieldInfo.size()>0)
	            {
					frmMemberDetails.set("alFieldInfo",(MemberPermVO[])alFieldInfo.toArray(new MemberPermVO[0]));
					frmMemberDetails.set("display","display");
	            }//end of if(alFieldInfo!= null && alFieldInfo.size()>0)
				else//if field status is not configured
				{
					frmMemberDetails.set("display","");
					request.setAttribute("configurationinfo","error.weblogin.memconfig");
				}//end of else

				for(int i= 0; i<alFieldInfo.size(); i++)
				{
					memberPermVO=(MemberPermVO)alFieldInfo.get(i);
					setFieldDisplay(memberPermVO,frmMemberDetails);
					setTableDisplay(memberPermVO,tableData);
				}//end of for(int i= 0; i<alFieldInfo.size(); i++)

				//set the flag to display the family sum insured icon and floater sum insured value
				if(alMemberDetails.size()>0)
				{
					dependentDetailVO=(DependentDetailVO)alMemberDetails.get(0);
					frmMemberDetails.set("familySumIconYN",dependentDetailVO.getFamilySumIconYN());
					//log.info("FloaterSumInsured value :"+ dependentDetailVO.getFloaterSumInsured());
					frmMemberDetails.set("floaterSumInsured",dependentDetailVO.getFloaterSumInsured()!=null?
							dependentDetailVO.getFloaterSumInsured().toString():"");
					//log.info("FloaterSumStatus"+dependentDetailVO.getFloaterSumStatus());
					frmMemberDetails.set("floaterSumStatus",dependentDetailVO.getFloaterSumStatus());
				}//end of if(alMemberDetails.size()>0)

				//if the member type is floater set that value to form
				for(int j=0;j<alMemberDetails.size();j++)
				{
					dependentDetailVO=(DependentDetailVO)alMemberDetails.get(j);

					//hide the sum insured colunm if the policy subtype is Floater
					if(dependentDetailVO.getMemberTypeID().equals("PFL"))
					{
						((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(false);
					}//end of if(dependentDetailVO.getAllowAddSumYN().equals("N"))
					// Change added for KOC1227H
					if(dependentDetailVO.getMemberTypeID().equals("PNF"))
					{
						frmMemberDetails.set("noteChange",dependentDetailVO.getMemberTypeID());

					}//end of if(dependentDetailVO.getMemberTypeID().equals("PNF"))
					/*if(dependentDetailVO.getAllowAddYN().equals("N"))
					{
						log.info("Inside if getAllowAddYN");
						((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(false);
					}//end of if(dependentDetailVO.getAllowAddYN().equals("N"))*/

					//Start Modifications as per KOC 1160 and 1159 Change request
					if(dependentDetailVO.getAllowDeleteYN().equalsIgnoreCase("Y") ){
						((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
					}//end of if(dependentDetailVO.getAllowAddYN().equals("Y"))
					//End Modifications as per KOC 1160 and 1159 Change request

/*
					//hide the delete icon column based on the allowAddYN flag
					if(dependentDetailVO.getAllowAddYN().equals("Y")){
						((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
					}//end of if(dependentDetailVO.getAllowAddYN().equals("Y"))*/
					/*if(dependentDetailVO.getAllowAddYN().equals("N"))
					{
						log.info("Inside if getAllowAddYN");
						((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(false);
					}//end of if(dependentDetailVO.getAllowAddYN().equals("N"))
					else
					{
						log.info("Inside if else");
						((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
					}//end of else if
*/				}//end of for(int j=0;j<alMemberDetails.size();j++)
				tableData.setData(alMemberDetails);

				//build the caption according to the User Type
				if(userSecurityProfile.getLoginType().equals("H"))
				{
					//log.info("Login Type :"+userSecurityProfile.getLoginType());
					sbfCaption=sbfCaption.append("[").append(onlinePolicyVO.getPolicyNbr()).append("] [")
					.append(strEnrollmentNbr).append("] [").append(strInsuredName).append("]");
				}//end of if(userSecurityProfile.getLoginType().equals("H"))
				else if(userSecurityProfile.getLoginType().equals("E"))
				{
					//log.info("Login Type :"+userSecurityProfile.getLoginType());
					sbfCaption=sbfCaption.append("[").append(userSecurityProfile.getPolicyNo()).append("] [").
					append(strEnrollmentNbr).append("] [").append(strInsuredName).append("]");
				}//end of else if(userSecurityProfile.getLoginType().equals("E"))

				frmMemberDetails.set("totalSumInsured",bgDefaultSumInsured!=null?
						bgDefaultSumInsured.toString() :"");
		        frmMemberDetails.set("defaultSumInsured",bgDefaultSumInsured!=null?
						bgDefaultSumInsured.toString() :"");
				frmMemberDetails.set("allowAddYN",strAllowAddYN);
				frmMemberDetails.set("policyStatusTypeID",strPolicyStatusTypeID);
				frmMemberDetails.set("caption",sbfCaption.toString());
				frmMemberDetails.set("insuredName",strInsuredName);
				frmMemberDetails.set("caption",sbfCaption.toString());
				frmMemberDetails.set("alRelationShip",alRelationShip);
				frmMemberDetails.set("enrollmentNbr",strEnrollmentNbr);
				frmMemberDetails.set("loginDate",(String)userSecurityProfile.getLoginDate());
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				//Added for IBM Age CR
				if(dependentDetailVO.getDateOfMarriage()!=null)
				{
				   String FormattedMarriageDate = df.format(dependentDetailVO.getDateOfMarriage());
				   request.getSession().setAttribute("EmpDateOfMarriage",FormattedMarriageDate);
				}
				//Added for KOC-1216
				
				if(dependentDetailVO.getPolicy_Stop_YN()=="")
				{
					frmMemberDetails.set("policyOPT","Y");//To disable family sumInsured Icon on OPTOUT --added by Praveen
				}else
				{
					frmMemberDetails.set("policyOPT",dependentDetailVO.getPolicy_Stop_YN());//To disable family sumInsured Icon on OPTOUT --added by Praveen
				}
				//End
				if(dependentDetailVO.getLoginWindowPeriodAlert()!=null && dependentDetailVO.getLoginWindowPeriodAlert()!="")
				{
					frmMemberDetails.set("loginWindowPeriodAlert",dependentDetailVO.getLoginWindowPeriodAlert());
					request.setAttribute("alert", dependentDetailVO.getLoginWindowPeriodAlert());
				}//end of if(dependentDetailVO.getLoginWindowPeriodAlert()!=null && dependentDetailVO.getLoginWindowPeriodAlert()!="")
				request.getSession().setAttribute("tableData",tableData);
				if(request.getSession().getAttribute("dependentDetailVO")!=null){
					DependentDetailVO tmpDependentDetailVO = (DependentDetailVO)request.getSession().getAttribute("dependentDetailVO");
					DynaActionForm tmpFrmMemberDetails = (DynaActionForm)request.getSession().getAttribute("frmMemberDetails");
					frmMemberDetails.set("prevname",tmpFrmMemberDetails.getString("prevname"));
					frmMemberDetails.set("prevvalue",tmpFrmMemberDetails.getString("prevvalue"));
					if(tmpDependentDetailVO.getName()!=null){
						frmMemberDetails.set("name",tmpDependentDetailVO.getName());
					}
					if(tmpDependentDetailVO.getMemberTypeID()!=null){
						frmMemberDetails.set("memberTypeID",tmpDependentDetailVO.getMemberTypeID());
					}
					if(tmpDependentDetailVO.getRelationTypeID()!=null){
						if(tmpDependentDetailVO.getRelationTypeID().contains("NSF")){
							frmMemberDetails.set("relationID","NSF");
							//frmMemberDetails.set("genderStatus","SHOW");
						}
						frmMemberDetails.set("relationTypeID",tmpDependentDetailVO.getRelationTypeID());
					}
					if(tmpDependentDetailVO.getGenderTypeID()!=null){
						frmMemberDetails.set("genderTypeID",tmpDependentDetailVO.getGenderTypeID());
					}
					if(tmpDependentDetailVO.getTotalSumInsured()!=null){
						frmMemberDetails.set("totalSumInsured",tmpDependentDetailVO.getTotalSumInsured().toString());
					}
					if(tmpDependentDetailVO.getDateOfBirth()!=null){
						frmMemberDetails.set("dateOfBirth",TTKCommon.getFormattedDate(tmpDependentDetailVO.getDateOfBirth()));
					}
					if(tmpDependentDetailVO.getAge()!=null){
						frmMemberDetails.set("age",tmpDependentDetailVO.getAge().toString());
					}
					if(tmpDependentDetailVO.getPlanAmt()!=null){
						frmMemberDetails.set("planAmt",tmpDependentDetailVO.getPlanAmt().toString());
					}
					if(tmpDependentDetailVO.getProdPlanSeqID()!=null){
						frmMemberDetails.set("prodPlanSeqID",tmpDependentDetailVO.getProdPlanSeqID().toString());
					}
					if(tmpDependentDetailVO.getAddPremium()!=null){
						frmMemberDetails.set("addPremium",tmpDependentDetailVO.getAddPremium().toString());
					}
					if(tmpDependentDetailVO.getGenderYN()!=null){
						frmMemberDetails.set("genderYN",tmpDependentDetailVO.getGenderYN());
					}
					// Change added for KOC1227C
					frmMemberDetails.set("premiumDeductOption",tmpFrmMemberDetails.get("premiumDeductOption"));
				}//end of if(request.getAttribute("dependentDetailVO")!=null)
				request.getSession().setAttribute("frmMemberDetails",frmMemberDetails);
				return this.getForward(strMemberDetails, mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processOnlineExceptions(request,mapping,expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processOnlineExceptions(request,mapping,new TTKException(exp,strMember));
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
	    public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    										HttpServletResponse response) throws Exception{
	    	try{
	    		setOnlineLinks(request);
	    		log.info("do OnlineMemberDetailsAction doView");
	    		DependentDetailVO dependentDetailVO =null;
	    		DynaActionForm frmMemberDetails=(DynaActionForm)form;
	    		TableData  tableData =TTKCommon.getTableData(request);
	    		OnlineAccessManager onlineAccessManager=this.getOnlineAccessManagerObject();
	    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	    		StringBuffer sbfCaption=new StringBuffer();
	    		String strRelationID =(String)frmMemberDetails.get("relationID");
	    		String strGenderID = (String)frmMemberDetails.get("genderYN");

	    		//get the Relationship list based on the Ins. Company
	    		ArrayList alRelationShip = (ArrayList)frmMemberDetails.get("alRelationShip");
	    		OnlineAccessPolicyVO onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
	    		DependentDetailVO dependentDetailVO1 = new DependentDetailVO();
				dependentDetailVO1=(DependentDetailVO)FormUtils.getFormValues(frmMemberDetails,this, mapping, request);
	    		String strInsuredName=(String)frmMemberDetails.get("insuredName");
	    		BigDecimal bgDefaultSumInsured=TTKCommon.getBigDecimal((String)frmMemberDetails.get("defaultSumInsured"));
	    		MemberPermVO[] alFieldInfo =(MemberPermVO[])frmMemberDetails.get("alFieldInfo");

	    		//if rownumber is found populate the form object
	    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	    		{
	    			dependentDetailVO=(DependentDetailVO)((TableData)request.getSession().getAttribute("tableData")).
	    			getData().get(Integer.parseInt(request.getParameter("rownum")));
	    			dependentDetailVO=onlineAccessManager.selectDependent(dependentDetailVO.getMemberSeqID(),userSecurityProfile.getLoginType());
	    			strRelationID=dependentDetailVO.getRelationTypeID();
	    			strGenderID=dependentDetailVO.getGenderYN();
	    			dependentDetailVO.setRelationTypeID(dependentDetailVO.getRelationTypeID()+"#"+dependentDetailVO.getGenderYN());
	    			//hide the sum insured column if the policy sub-type is Floater
	    			if(dependentDetailVO.getMemberTypeID().equals("PFL"))
	    			{
	    				((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(false);
	    			}//end of if(dependentDetailVO.getMemberTypeID().equals("PFL"))
	    			else
	    			{
	    				((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(true);
	    			}//end of else

	    			if(dependentDetailVO.getAllowAddYN().equals("Y"))
	    			{
	    				((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
	    			}//end of if(dependentDetailVO.getAllowAddYN().equals("Y"))

	    			//hide the delete icon column based on the allowAddYN flag
	    			/*if(dependentDetailVO.getAllowAddYN().equals("N"))
	    			{
	    				((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(false);
	    			}//end of if(dependentDetailVO.getAllowAddYN().equals("N"))
	    			else
	    			{
	    				((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
	    			}//end of else if
*/	    			frmMemberDetails = (DynaActionForm)FormUtils.setFormValues("frmMemberDetails",dependentDetailVO,
	    					this,mapping,request);
	    		}// end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	    		else
	    		{
	    			frmMemberDetails.initialize(mapping);
	    		}// end of else

	    		//set the display properties of the fields
	    		//log.info("Arraylist length :"+alFieldInfo.length);
	    		if(alFieldInfo.length>0)
	    		{
		    		for(int i= 0; i<alFieldInfo.length; i++)
		    		{
		    			MemberPermVO memberPermVO = new MemberPermVO();
		    			memberPermVO=(MemberPermVO)alFieldInfo[i];
		    			setFieldDisplay(memberPermVO,frmMemberDetails);
		    			setTableDisplay(memberPermVO,tableData);
		    		}//end of for(int i= 0; i<alFieldInfo.size(); i++)
		    		frmMemberDetails.set("display","display");
	    		}//end of if(alFieldInfo.length>0)
	    		else
				{
					frmMemberDetails.set("display","");
					request.setAttribute("configurationinfo","error.weblogin.memconfig");
				}//end of else

	    		if(frmMemberDetails.get("ageStatus").equals("HIDE"))
	    		{
	    			frmMemberDetails.set("age",null);
	    		}//end of if(frmMemberDetails.get("ageStatus").equals("HIDE"))
	    		if(frmMemberDetails.get("dobStatus").equals("HIDE"))
	    		{
	    			frmMemberDetails.set("dateOfBirth",null);
	    		}//end of if(frmMemberDetails.get("dobStatus").equals("HIDE"))
	    		//build the caption based on the type of the Logged in User
	    		if(userSecurityProfile.getLoginType().equals("H"))
	    		{
	    			sbfCaption=sbfCaption.append("[").append(onlinePolicyVO.getPolicyNbr()).append("] [")
	    			.append(dependentDetailVO1.getEnrollmentNbr()).append("] [").append(strInsuredName).append("]");
	    		}//end of if(userSecurityProfile.getLoginType().equals("H"))
	    		else if(userSecurityProfile.getLoginType().equals("E"))
	    		{
	    			sbfCaption=sbfCaption.append("[").append(userSecurityProfile.getPolicyNo()).append("] [")
	    			.append(dependentDetailVO1.getEnrollmentNbr()).append("] [").append(strInsuredName).append("]");
	    		}//end of else if(userSecurityProfile.getLoginType().equals("E"))
	    		if(dependentDetailVO1.getLoginWindowPeriodAlert()!=null && dependentDetailVO1.getLoginWindowPeriodAlert()!="")
				{
					frmMemberDetails.set("loginWindowPeriodAlert",dependentDetailVO1.getLoginWindowPeriodAlert());
					request.setAttribute("alert", dependentDetailVO1.getLoginWindowPeriodAlert());
				}//end of if(dependentDetailVO.getLoginWindowPeriodAlert()!=null && dependentDetailVO.getLoginWindowPeriodAlert()!="")
	    		frmMemberDetails.set("policySeqID",dependentDetailVO1.getPolicySeqID().toString());
	    		frmMemberDetails.set("policyGroupSeqID",dependentDetailVO1.getPolicyGroupSeqID().toString());
	    		frmMemberDetails.set("insuredName",strInsuredName);
	    		frmMemberDetails.set("familySumIconYN",dependentDetailVO1.getFamilySumIconYN());
	    		frmMemberDetails.set("caption",sbfCaption.toString());
	    		frmMemberDetails.set("loginDate",(String)userSecurityProfile.getLoginDate());
	    		frmMemberDetails.set("relationID",strRelationID);
	    		frmMemberDetails.set("abbrCode",dependentDetailVO1.getAbbrCode());
	    		frmMemberDetails.set("alRelationShip",alRelationShip);
	    		frmMemberDetails.set("floaterSumInsured",dependentDetailVO1.getFloaterSumInsured()!=null?
	    				dependentDetailVO1.getFloaterSumInsured().toString() :"");
	    		frmMemberDetails.set("floaterSumStatus",dependentDetailVO1.getFloaterSumStatus());
	    		frmMemberDetails.set("enrollmentNbr",dependentDetailVO1.getEnrollmentNbr());
	    		if(!strGenderID.equals("OTH"))
	    			frmMemberDetails.set("genderTypeID",strGenderID);
	    		frmMemberDetails.set("genderYN",strGenderID);
	    		frmMemberDetails.set("defaultSumInsured",bgDefaultSumInsured!=null?
	    				bgDefaultSumInsured.toString():"");
	    		frmMemberDetails.set("allowAddYN",dependentDetailVO1.getAllowAddYN());
	    		frmMemberDetails.set("cancelYN",dependentDetailVO1.getCancelYN());
	    		frmMemberDetails.set("expiredYN",dependentDetailVO1.getExpiredYN());
	    		frmMemberDetails.set("alFieldInfo",(MemberPermVO[])alFieldInfo);
	    		frmMemberDetails.set("sumInsIconEditYN","N");
	    		frmMemberDetails.set("policyOPT",dependentDetailVO.getPolicy_Stop_YN());//To disable family sumInsured Icon on OPTOUT --//KOC-1216
	    		frmMemberDetails.set("ageDeclaration",dependentDetailVO.getDeclaration());//declaration checkbox IBM
	    		request.getSession().setAttribute("frmMemberDetails",frmMemberDetails);
	    		return (mapping.findForward(strMemberDetails));
	    	}//end of try
	    	catch(TTKException expTTK)
	    	{
	    		return this.processOnlineExceptions(request,mapping,expTTK);
	    	}//end of catch(TTKException expTTK)
	    	catch(Exception exp)
	    	{
	    		return this.processOnlineExceptions(request,mapping,new TTKException(exp,strMember));
	    	}//end of catch(Exception exp)
	    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse

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
				setOnlineLinks(request);
				log.info("do OnlineMemberDetailsAction doSave");
				OnlineAccessManager onlineAccessManager=this.getOnlineAccessManagerObject();
				long iUpdate=0;
				DynaActionForm frmMemberDetails=(DynaActionForm)form;
				UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
				String strLoginType=userSecurityProfile.getLoginType();
				StringBuffer sbfCaption=new StringBuffer();
				DependentDetailVO dependentDetailVO1 = new DependentDetailVO();
				dependentDetailVO1=(DependentDetailVO)FormUtils.getFormValues(frmMemberDetails,this, mapping, request);
				String strFamilyIconYN=(String)frmMemberDetails.get("familySumIconYN");

				BigDecimal bgDefaultSumInsured=TTKCommon.getBigDecimal((String)frmMemberDetails.get("defaultSumInsured"));

				//MemberPermVO[] alFieldInfo =(MemberPermVO[])frmMemberDetails.get("alFieldInfo");

				String strInsuredName=(String)frmMemberDetails.get("insuredName");
	    		ArrayList alRelationShip = (ArrayList)frmMemberDetails.get("alRelationShip");
				TableData  tableData =TTKCommon.getTableData(request);
				OnlineAccessPolicyVO onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
				DependentDetailVO dependentDetailVO = new DependentDetailVO();
				dependentDetailVO=(DependentDetailVO)FormUtils.getFormValues(frmMemberDetails,this, mapping, request);
				/*log.info("PolicyGroupSeqID from object :"+dependentDetailVO1.getPolicyGroupSeqID());
				log.info("PolicyGroupSeqID() from object :"+dependentDetailVO1.getPolicyGroupSeqID());*/
				String strRelationTypeID = dependentDetailVO.getRelationTypeID();
				strRelationTypeID=strRelationTypeID.substring(0,strRelationTypeID.indexOf("#"));
				dependentDetailVO.setRelationTypeID(strRelationTypeID);
				if(strLoginType.equals("H"))
				{
					//log.info("User Seq ID: " +TTKCommon.getUserSeqId(request));
					sbfCaption=sbfCaption.append("[").append(onlinePolicyVO.getPolicyNbr()).append("] [").
					append(dependentDetailVO1.getEnrollmentNbr()).append("] [").append(strInsuredName).append("]");
					dependentDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
				}//end of if(strLoginType.equals("H"))
				if(strLoginType.equals("E"))
				{
					//log.info("User ID: " +TTKCommon.getLong(userSecurityProfile.getUSER_ID()));
					sbfCaption=sbfCaption.append("[").append(userSecurityProfile.getPolicyNo()).append("] [").
					append(dependentDetailVO1.getEnrollmentNbr()).append("] [").append(strInsuredName).append("]");
					//dependentDetailVO.setUpdatedBy(TTKCommon.getLong(userSecurityProfile.getUSER_ID()));
					dependentDetailVO.setUpdatedBy(new Long(1));
				}//end of if(strLoginType.equals("E"))

				//if Preauth/Claim exists modification is not allowed
				if(dependentDetailVO.getPreClaimExistYN().equals("Y"))
				{
					ActionMessages actionMessages = new ActionMessages();
					ActionMessage actionMessage=new ActionMessage("error.weblogin.preclaimexist");
					actionMessages.add("global.error",actionMessage);
					saveErrors(request,actionMessages);
					return (mapping.findForward(strMemberDetails));
				}//end of if(dependentDetailVO.getPreClaimExistYN().equals("Y"))

				if(dependentDetailVO.getProdPlanSeqID()==null && dependentDetailVO.getAddPremium().intValue()==0 && dependentDetailVO.getTotalSumInsured().intValue()==0){
					dependentDetailVO.setTotalSumInsured(null);
				}
				// Change added for KOC1227C
				dependentDetailVO.setPremiumDeductionOption((String)frmMemberDetails.get("premiumDeductOption"));
				//added for IBM
				String Declaration = request.getParameter("confirm");
				dependentDetailVO.setDeclaration(Declaration);
				String strgroupid=(String)request.getSession().getAttribute("groupId");//Koc Netsol
				
				
				//Added for IBM.....KOC-1216
				if(dependentDetailVO1.getEnrollmentNbr().contains("I310-01")||dependentDetailVO1.getEnrollmentNbr().contains("I310-001"))
				{

					String Relation = dependentDetailVO1.getRelationTypeID();
					
					String Gender = dependentDetailVO1.getGenderTypeID();
					int age = dependentDetailVO1.getAge();

					if((Gender.equalsIgnoreCase("MAL"))&&(!(Relation.contains("YSP#OTH")))&&(!(Relation.contains("NSF#OTH")))&&(age>21))
					{
						request.setAttribute("CheckMaleValidation", "message.CheckMaleValidation");
					}
					else if((Gender.equalsIgnoreCase("FEM"))&&(!(Relation.contains("YSP#OTH")))&&(!(Relation.contains("NSF#OTH")))&&(age>24))
					{
						request.setAttribute("CheckFemaleValidation", "message.CheckFemaleValidation");
					}
					else
					{
						iUpdate=onlineAccessManager.saveDependent(dependentDetailVO);
					}

				}
				//Koc Netsol
				else if(strgroupid.equalsIgnoreCase("N0327"))
				{

					String Relation = dependentDetailVO1.getRelationTypeID();
					
					String Gender = dependentDetailVO1.getGenderTypeID();
					int age = dependentDetailVO1.getAge();
				
					if((Gender.equalsIgnoreCase("MAL"))&&(((Relation.contains("NC")))||((Relation.contains("ND")))||((Relation.contains("NS"))))&&(age>21))
					{
						request.setAttribute("CheckMaleValidation", "message.CheckMaleValidation");
					}
					else if((Gender.equalsIgnoreCase("FEM"))&&(((Relation.contains("NC")))||((Relation.contains("ND")))||((Relation.contains("NS"))))&&(age>24))
					{
						request.setAttribute("CheckFemaleValidation", "message.CheckFemaleValidation");
					}
					else
					{
						iUpdate=onlineAccessManager.saveDependent(dependentDetailVO);
					}

				}
				//Koc Netsol
				//Ended
				else
				{
						iUpdate=onlineAccessManager.saveDependent(dependentDetailVO);
				}

				//iUpdate=onlineAccessManager.saveDependent(dependentDetailVO);
                                  //Changes as per KOC 1159 1160
					if((iUpdate<=0)){
					// start Changes as per KOC 1159 and 1160
					frmMemberDetails.set("combinationMembersLimit",dependentDetailVO.getCombinationMembersLimit());
					
					//	
					// end Changes as per KOC 1159 and 1160
					}
						//Changes as per KOC 1159 1160

				//Added Rule related validations as per Sreeraj instructions on 27/02/2008
				if(iUpdate<0){
					//log.info("Errors are there..........");
					RuleManager ruleManager=this.getRuleManagerObject();
					//RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();
					ArrayList alValidationError=ruleManager.getValidationErrorList(new Long(iUpdate));

					//prepare Error messages
					//ArrayList alErrorMessage=ruleXMLHelper.prepareErrorMessage(alValidationError,request);
					request.setAttribute("BUSINESS_ERRORS",alValidationError);
					return this.getForward(strMemberDetails, mapping, request);
				}//end of Addition
				if(iUpdate>0)
				{
					frmMemberDetails.initialize(mapping);
					frmMemberDetails.set("memberTypeID",dependentDetailVO1.getMemberTypeID());
					frmMemberDetails.set("totalSumInsured",bgDefaultSumInsured!=null?
		    				bgDefaultSumInsured.toString():"");
					if(dependentDetailVO.getMemberSeqID()!=null)
					{
						//set the appropriate message
						request.setAttribute("updated","message.savedSuccessfully");
					}//end of if(chequeVO.getSeqID()!=null)
					else
					{
						//set the appropriate message
						request.setAttribute("updated","message.addedSuccessfully");
					}//end else

					ArrayList alDependantList=onlineAccessManager.dependentList(tableData.getSearchData());

					if(alDependantList.size()>0)
					{
						dependentDetailVO=(DependentDetailVO)alDependantList.get(0);
						strFamilyIconYN =dependentDetailVO.getFamilySumIconYN();
						frmMemberDetails.set("floaterSumInsured",dependentDetailVO.getFloaterSumInsured()!=null?
								dependentDetailVO.getFloaterSumInsured().toString():"");
						frmMemberDetails.set("floaterSumStatus",dependentDetailVO.getFloaterSumStatus());
						//log.info("floaterSumStatus value after save :" +dependentDetailVO.getFloaterSumStatus());
					}
					for(int j=0;j<alDependantList.size();j++)
					{
						dependentDetailVO=(DependentDetailVO)alDependantList.get(j);
						//hide the sum insured column if the policy sub-type is Floater
						if(dependentDetailVO.getMemberTypeID().equals("PFL"))
						{
							((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(false);
						}//end of if(dependentDetailVO.getMemberTypeID().equals("PFL"))
						else
						{
							((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(true);
						}//end of if(dependentDetailVO.getAllowAddSumYN().equals("N"))

						if(dependentDetailVO.getAllowAddYN().equals("Y"))
						{
							((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
						}//end of if(dependentDetailVO.getAllowAddYN().equals("Y"))

						//hide the delete icon column based on the allowAddYN flag
						/*if(dependentDetailVO.getAllowAddYN().equals("N"))
						{
							((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(false);
						}//end of if(dependentDetailVO.getAllowAddYN().equals("N"))
						else
						{
							((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
						}//end of else if
*/					}//end of for(int j=0;j<alMemberDetails.size();j++)
					tableData.setData(alDependantList);
					request.getSession().setAttribute("tableData",tableData);
				}//end of if(iUpdate>0)

				//set the field display properties
				//ArrayList alFieldInfo=onlineAccessManager.getFieldInfo(lngPolicySeqID);
				//MemberPermVO memberPermVO = new MemberPermVO();
				//log.info("Arraylist length :"+alFieldInfo.length);
				MemberPermVO memberPermVO = new MemberPermVO();
				ArrayList<Object> alFieldInfo=onlineAccessManager.getFieldInfo(dependentDetailVO1.getPolicySeqID(),dependentDetailVO1.getPolicyGroupSeqID());
				if(alFieldInfo!= null && alFieldInfo.size()>0)
	            {
					frmMemberDetails.set("alFieldInfo",(MemberPermVO[])alFieldInfo.toArray(new MemberPermVO[0]));
					frmMemberDetails.set("display","display");
	            }//end of if(alFieldInfo!= null && alFieldInfo.size()>0)
				else//if field status is not configured
				{
					frmMemberDetails.set("display","");
					request.setAttribute("configurationinfo","error.weblogin.memconfig");
				}//end of else
				for(int i= 0; i<alFieldInfo.size(); i++)
				{
					memberPermVO=(MemberPermVO)alFieldInfo.get(i);
					setFieldDisplay(memberPermVO,frmMemberDetails);
					setTableDisplay(memberPermVO,tableData);
				}//end of for(int i= 0; i<alFieldInfo.size(); i++)

				/*if(alFieldInfo.length>0)
	    		{
		    		for(int i= 0; i<alFieldInfo.length; i++)
		    		{
		    			MemberPermVO memberPermVO = new MemberPermVO();
		    			memberPermVO=(MemberPermVO)alFieldInfo[i];
		    			setFieldDisplay(memberPermVO,frmMemberDetails);
		    			setTableDisplay(memberPermVO,tableData);
		    		}//end of for(int i= 0; i<alFieldInfo.size(); i++)
		    		frmMemberDetails.set("display","display");
	    		}//end of if(alFieldInfo.length>0)
	    		else//show a message if field configuration is not done
				{
					frmMemberDetails.set("display","");
					request.setAttribute("configurationinfo","error.weblogin.memconfig");
				}//end of else
*/
				if(dependentDetailVO.getLoginWindowPeriodAlert()!=null && dependentDetailVO.getLoginWindowPeriodAlert()!="")
				{
					frmMemberDetails.set("loginWindowPeriodAlert",dependentDetailVO.getLoginWindowPeriodAlert());
					request.setAttribute("alert", dependentDetailVO.getLoginWindowPeriodAlert());
				}//end of if(dependentDetailVO.getLoginWindowPeriodAlert()!=null && dependentDetailVO.getLoginWindowPeriodAlert()!="")
				frmMemberDetails.set("policySeqID",dependentDetailVO1.getPolicySeqID().toString());
				frmMemberDetails.set("policyGroupSeqID",dependentDetailVO1.getPolicyGroupSeqID().toString());
				frmMemberDetails.set("allowAddYN",dependentDetailVO1.getAllowAddYN());
				frmMemberDetails.set("insuredName",strInsuredName);
				frmMemberDetails.set("caption",sbfCaption.toString());
				frmMemberDetails.set("allowAddSumYN",dependentDetailVO1.getAllowAddSumYN());
				frmMemberDetails.set("familySumIconYN",strFamilyIconYN);
				frmMemberDetails.set("loginDate",(String)userSecurityProfile.getLoginDate());
				frmMemberDetails.set("alRelationShip",alRelationShip);
				frmMemberDetails.set("abbrCode",dependentDetailVO1.getAbbrCode());
				frmMemberDetails.set("enrollmentNbr",dependentDetailVO1.getEnrollmentNbr());
				frmMemberDetails.set("cancelYN",dependentDetailVO1.getCancelYN());
				frmMemberDetails.set("expiredYN",dependentDetailVO1.getExpiredYN());
				/*frmMemberDetails.set("floaterSumInsured",dependentDetailVO1.getFloaterSumInsured()!=null?
	    				dependentDetailVO1.getFloaterSumInsured().toString() :"");*/
	    		//frmMemberDetails.set("floaterSumStatus",dependentDetailVO1.getFloaterSumStatus());
	    		frmMemberDetails.set("defaultSumInsured",bgDefaultSumInsured!=null?
	    				bgDefaultSumInsured.toString():"");
				//frmMemberDetails.set("alFieldInfo",(MemberPermVO[])alFieldInfo);
				//Added for KOC-1216
				if(dependentDetailVO.getPolicy_Stop_YN()=="")
				{
					frmMemberDetails.set("policyOPT","Y");//To disable family sumInsured Icon on OPTOUT --added by Praveen
				}else
				{
					frmMemberDetails.set("policyOPT",dependentDetailVO.getPolicy_Stop_YN());//To disable family sumInsured Icon on OPTOUT --added by Praveen
				}
				//End
				request.getSession().setAttribute("frmMemberDetails",frmMemberDetails);
				return (mapping.findForward(strMemberDetails));
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processOnlineExceptions(request,mapping,expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processOnlineExceptions(request,mapping,new TTKException(exp,strMember));
			}//end of catch(Exception exp)
		}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
				log.info("do OnlineMemberDetailsAction doListPlan");
				DynaActionForm frmMemberDetails=(DynaActionForm)form;
				DependentDetailVO dependentDetailVO = new DependentDetailVO();
				dependentDetailVO=(DependentDetailVO)FormUtils.getFormValues(frmMemberDetails,this, mapping, request);
				request.setAttribute("dependentDetailVO",dependentDetailVO);
				return (mapping.findForward(strListSumInsPlan));
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processOnlineExceptions(request,mapping,expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processOnlineExceptions(request,mapping,new TTKException(exp,strMember));
			}//end of catch(Exception exp)
		}//end of doListPlan(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	    		return mapping.findForward(strCloseMember);
	    	}//end of try
	    	catch(TTKException expTTK)
	    	{
	    		return this.processOnlineExceptions(request,mapping,expTTK);
	    	}//end of catch(ETTKException expTTK)
	    	catch(Exception exp)
	    	{
	    		return this.processOnlineExceptions(request,mapping,new TTKException(exp,strMember));
	    	}//end of catch(Exception exp)
	    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	    /**
		 * This method is used to load the gender based on the relationship.
		 * Finally it forwards to the appropriate view based on the specified forward mappings
		 *
		 * @param mapping The ActionMapping used to select this instance
		 * @param form The optional ActionForm bean for this request (if any)
		 * @param request The HTTP request we are processing
		 * @param response The HTTP response we are creating
		 * @return ActionForward Where the control will be forwarded, after this request is processed
		 * @throws Exception if any error occurs
		 */
	    public ActionForward doChangeRelationship(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			try {
				setOnlineLinks(request);
				log.info("do OnlineMemberDetailsAction doChangeRelationship");
				DynaActionForm frmMemberDetails = (DynaActionForm) form;
				String strHiddenName = (String) frmMemberDetails.get("insuredName");
				String strRelationTypeID = frmMemberDetails
						.getString("relationTypeID");
				String strGenderID = strRelationTypeID.substring(strRelationTypeID
						.indexOf("#") + 1, strRelationTypeID.length());
				if (strRelationTypeID != null && strRelationTypeID.length() > 0)
				{
							strRelationTypeID = strRelationTypeID.substring(0,
							strRelationTypeID.indexOf("#"));
				}//end of if (strRelationTypeID != null && strRelationTypeID.length() > 0)
				if (frmMemberDetails.getString("relationTypeID").equalsIgnoreCase("NSF#OTH"))
				{
					frmMemberDetails.set("name", strHiddenName);
				}// end of if (frmMemberDetails.getString("relationTypeID").equalsIgnoreCase("NSF#OTH"))
				if (!strGenderID.equals("OTH"))
				{
					frmMemberDetails.set("genderTypeID", strGenderID);
				}//end of if (!strGenderID.equals("OTH"))
				else
				{
					frmMemberDetails.set("genderTypeID", "");
				}// end of else

				frmMemberDetails.set("relationID", strRelationTypeID);
				frmMemberDetails.set("genderYN", strGenderID);
				frmMemberDetails.set("insuredName", strHiddenName);
				//initializing prevvalue value so that for next change it can be used
				//to get previously selected relationship value
				if(frmMemberDetails.get("prevvalue").equals("") || frmMemberDetails.get("prevvalue")==null  ){
					//log.info("check5");
					frmMemberDetails.set("prevvalue",strRelationTypeID);
				}//end of if(frmMemberDetails.get("prevvalue").equals("") || frmMemberDetails.get("prevvalue")==null)
				else {
					//if previously selected value is Self for relationship
					//then on change of that, Member Name which not representing Self and that
					//entered before selecting Self has to be populated as default value in Member Name
					if(frmMemberDetails.get("prevvalue").equals("NSF")){
						frmMemberDetails.set("name", frmMemberDetails.getString("prevname"));
					}//end of if(frmMemberDetails.get("prevvalue").equals("NSF"))
				}//end of else
				//if relationship is not self then prevname is loaded so that it can be populated
				//to Member Name text box
				if (!frmMemberDetails.getString("relationTypeID").equalsIgnoreCase("NSF#OTH")) {
					frmMemberDetails.set("prevname",frmMemberDetails.getString("name"));
				}//end of if (frmMemberDetails.getString("relationTypeID").equalsIgnoreCase("NSF#OTH")) {
				frmMemberDetails.set("prevvalue",strRelationTypeID);
				frmMemberDetails.set("frmChanged","changed");
				frmMemberDetails.set("prevvalue",strRelationTypeID);
				//IBM Isuue	
				log.info("log memberSeqID---memberSeqID---->>"+frmMemberDetails.getString("memberSeqID"));
				log.info("log before---getstring totalSumInsured---->>"+frmMemberDetails.getString("totalSumInsured"));
				String strMemberSeqID = frmMemberDetails.getString("memberSeqID");
				if((strMemberSeqID=="") || (strMemberSeqID==null))
				{	
				BigDecimal bdTotSumInsured = null;
			    if(frmMemberDetails.getString("totalSumInsured")!=""){
				bdTotSumInsured = TTKCommon.getBigDecimal("0");
				log.info("frmMemberDetails===============>>>IBM"+bdTotSumInsured);
				}
				frmMemberDetails.set("totalSumInsured",bdTotSumInsured.toString());
				}
				//End IBM Isuue	
				request.getSession().setAttribute("frmMemberDetails",frmMemberDetails);
				return (mapping.findForward(strMemberDetails));
			
			}// end of try
			catch (TTKException expTTK)
			{
				return this.processOnlineExceptions(request, mapping, expTTK);
			}// end of catch(TTKException expTTK)
			catch (Exception exp)
			{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp, strMember));
			}// end of catch(Exception exp)
		}// end of doChangeRelationship(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response)

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
		/*public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
									  HttpServletResponse response) throws Exception{
			try{
				setOnlineLinks(request);
				log.info("do OnlineMemberDetailsAction doDelete");
				DynaActionForm frmMemberDetails=(DynaActionForm)form;
				OnlineAccessManager onlineAccessManager=this.getOnlineAccessManagerObject();
				DependentDetailVO dependentDetailVO =null;
				UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
				OnlineAccessPolicyVO onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
				String strLoginType=userSecurityProfile.getLoginType();
				DependentDetailVO dependentDetailVO1 = new DependentDetailVO();
				dependentDetailVO1=(DependentDetailVO)FormUtils.getFormValues(frmMemberDetails,this, mapping, request);
				String strInsuredName=(String)frmMemberDetails.get("insuredName");
				BigDecimal bgDefaultSumInsured=TTKCommon.getBigDecimal((String)frmMemberDetails.get("defaultSumInsured"));
				MemberPermVO[] alFieldInfo =(MemberPermVO[])frmMemberDetails.get("alFieldInfo");
				ArrayList alRelationShip = (ArrayList)frmMemberDetails.get("alRelationShip");
				String strFamilyIconYN="";
				StringBuffer sbfCaption=new StringBuffer();
				int iUpdate=0;
				String strFlag="MEM";
				Long lngMemberSeqID=null;
				TableData  tableData =TTKCommon.getTableData(request);

				if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				{
					dependentDetailVO=(DependentDetailVO)((TableData)request.getSession().getAttribute("tableData")).
					getData().get(Integer.parseInt(request.getParameter("rownum")));

					if(dependentDetailVO.getAllowDeleteYN().equals("N"))
					{
						ActionMessages actionMessages = new ActionMessages();
						ActionMessage actionMessage=new ActionMessage("onlineforms.delete.self");
						actionMessages.add("global.error",actionMessage);
						saveErrors(request,actionMessages);
						return (mapping.findForward(strMemberDetails));
					}//end of if(dependentDetailVO.getAllowDeleteYN().equals("N"))
					lngMemberSeqID = dependentDetailVO.getMemberSeqID();
				}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))

				if(strLoginType.equals("H"))
				{
					sbfCaption=sbfCaption.append("[").append(onlinePolicyVO.getPolicyNbr()).append("] [").
					append(dependentDetailVO1.getEnrollmentNbr()).append("] [").append(strInsuredName).append("]");
					dependentDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
				}//end of if(strLoginType.equals("H"))
				if(strLoginType.equals("E"))
				{
					sbfCaption=sbfCaption.append("[").append(userSecurityProfile.getPolicyNo()).append("] [").
					append(dependentDetailVO1.getEnrollmentNbr()).append("] [").append(strInsuredName).append("]");
					//dependentDetailVO.setUpdatedBy(TTKCommon.getLong(userSecurityProfile.getUSER_ID()));
					dependentDetailVO.setUpdatedBy(new Long(1));
				}//end of if(strLoginType.equals("E"))
				ArrayList<Object> alParams = new ArrayList<Object>();
				alParams.add(strFlag);
				alParams.add(lngMemberSeqID);
				alParams.add(dependentDetailVO1.getPolicySeqID());
				alParams.add(dependentDetailVO.getUpdatedBy());

				iUpdate=onlineAccessManager.deleteGeneral(alParams);
				//log.info("iUpdate value is :"+iUpdate);
				frmMemberDetails.initialize(mapping);
				frmMemberDetails.set("memberTypeID",dependentDetailVO1.getMemberTypeID());
				frmMemberDetails.set("totalSumInsured",bgDefaultSumInsured!=null?
	    				bgDefaultSumInsured.toString():"");
				ArrayList alDependantList=onlineAccessManager.dependentList(tableData.getSearchData());
				tableData.setData(alDependantList);
				if(alDependantList.size()>0)
		        {
			        dependentDetailVO=(DependentDetailVO)alDependantList.get(0);
			        strFamilyIconYN =dependentDetailVO.getFamilySumIconYN();
			        frmMemberDetails.set("floaterSumInsured",dependentDetailVO.getFloaterSumInsured()!=null?
							dependentDetailVO.getFloaterSumInsured().toString():"");
					frmMemberDetails.set("floaterSumStatus",dependentDetailVO.getFloaterSumStatus());
		        }
				for(int j=0;j<alDependantList.size();j++)
		        {
		        	dependentDetailVO=(DependentDetailVO)alDependantList.get(j);
		        	//hide the sum insured colunm if the policy subtype is Floater
		        	if(dependentDetailVO.getMemberTypeID().equals("PFL"))
		        	{
		        		((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(false);
		        	}//end of if(dependentDetailVO.getMemberTypeID().equals("PFL"))
		        	else
		        	{
		        		((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(true);
		        	}//end of if(dependentDetailVO.getAllowAddSumYN().equals("N"))

		        	if(dependentDetailVO.getAllowAddYN().equals("Y"))
		        	{
		        		((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
		        	}//end of if(dependentDetailVO.getAllowAddYN().equals("Y"))

		        	//hide the delete icon column based on the allowAddYN flag
		        	/*if(dependentDetailVO.getAllowAddYN().equals("N"))
		        	{
		        		((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(false);
		        	}//end of if(dependentDetailVO.getAllowAddYN().equals("N"))
		        	else
		        	{
		        		((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
		        	}//end of else if
*/		      //  }//end of for(int j=0;j<alMemberDetails.size();j++)
				//if(dependentDetailVO.getLoginWindowPeriodAlert()!=null && dependentDetailVO.getLoginWindowPeriodAlert()!="")
				//{
					//frmMemberDetails.set("loginWindowPeriodAlert",dependentDetailVO.getLoginWindowPeriodAlert());
					//request.setAttribute("alert", dependentDetailVO.getLoginWindowPeriodAlert());
			//	}//end of if(dependentDetailVO.getLoginWindowPeriodAlert()!=null && dependentDetailVO.getLoginWindowPeriodAlert()!="")
				//set the field display properties
				//ArrayList alFieldInfo=onlineAccessManager.getFieldInfo(lngPolicySeqID);
		        //MemberPermVO memberPermVO = new MemberPermVO();

		        /*if(alFieldInfo.length>0)
	    		{
		    		for(int i= 0; i<alFieldInfo.length; i++)
		    		{
		    			//log.info("i value is :" +i);

		    			MemberPermVO memberPermVO = new MemberPermVO();
		    			memberPermVO=(MemberPermVO)alFieldInfo[i];
		    			setFieldDisplay(memberPermVO,frmMemberDetails);
		    			setTableDisplay(memberPermVO,tableData);
		    		}//end of for(int i= 0; i<alFieldInfo.size(); i++)
		    		frmMemberDetails.set("display","display");
	    		}//end of if(alFieldInfo.length>0)
	    		else
				{
					frmMemberDetails.set("display","");
					request.setAttribute("configurationinfo","error.weblogin.memconfig");
				}//end of else
	    		frmMemberDetails.set("policySeqID",dependentDetailVO1.getPolicySeqID().toString());
		        frmMemberDetails.set("policyGroupSeqID",dependentDetailVO1.getPolicyGroupSeqID().toString());
		        frmMemberDetails.set("allowAddYN",dependentDetailVO1.getAllowAddYN());
		        frmMemberDetails.set("insuredName",strInsuredName);
		        frmMemberDetails.set("caption",sbfCaption.toString());
		        frmMemberDetails.set("allowAddSumYN",dependentDetailVO1.getAllowAddSumYN());
		        frmMemberDetails.set("familySumIconYN",strFamilyIconYN);
		        frmMemberDetails.set("loginDate",(String)userSecurityProfile.getLoginDate());
		        frmMemberDetails.set("alRelationShip",alRelationShip);
		        frmMemberDetails.set("abbrCode",dependentDetailVO1.getAbbrCode());
		        frmMemberDetails.set("enrollmentNbr",dependentDetailVO1.getEnrollmentNbr());
		        /*frmMemberDetails.set("floaterSumInsured",dependentDetailVO1.getFloaterSumInsured()!=null?
	    				dependentDetailVO1.getFloaterSumInsured().toString() :"");*/
	    		//frmMemberDetails.set("floaterSumStatus",dependentDetailVO1.getFloaterSumStatus());
	    		/*frmMemberDetails.set("defaultSumInsured",bgDefaultSumInsured!=null?
	    				bgDefaultSumInsured.toString():"");
		        frmMemberDetails.set("alFieldInfo",(MemberPermVO[])alFieldInfo);
		        frmMemberDetails.set("cancelYN",dependentDetailVO1.getCancelYN());
		        frmMemberDetails.set("expiredYN",dependentDetailVO1.getExpiredYN());
		        frmMemberDetails.set("policyOPT",dependentDetailVO.getPolicy_Stop_YN());//To disable family sumInsured Icon on OPTOUT --KOC-1216
		        request.getSession().setAttribute("tableData",tableData);
		        request.getSession().setAttribute("frmMemberDetails",frmMemberDetails);
				return (mapping.findForward(strMemberDetails));

			}//end of try
			catch(TTKException expTTK)
			{
				return this.processOnlineExceptions(request,mapping,expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processOnlineExceptions(request,mapping,new TTKException(exp,strMember));
			}//end of catch(Exception exp)
		}*/
		
		public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
									  HttpServletResponse response) throws Exception{
			try{
				setOnlineLinks(request);
				log.info("do OnlineMemberDetailsAction doDelete");
				DynaActionForm frmMemberDetails=(DynaActionForm)form;
				OnlineAccessManager onlineAccessManager=this.getOnlineAccessManagerObject();
				DependentDetailVO dependentDetailVO =null;
				UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
				OnlineAccessPolicyVO onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
				String strLoginType=userSecurityProfile.getLoginType();
				DependentDetailVO dependentDetailVO1 = new DependentDetailVO();
				dependentDetailVO1=(DependentDetailVO)FormUtils.getFormValues(frmMemberDetails,this, mapping, request);
				String strInsuredName=(String)frmMemberDetails.get("insuredName");
				BigDecimal bgDefaultSumInsured=TTKCommon.getBigDecimal((String)frmMemberDetails.get("defaultSumInsured"));
				MemberPermVO[] alFieldInfo =(MemberPermVO[])frmMemberDetails.get("alFieldInfo");
				ArrayList alRelationShip = (ArrayList)frmMemberDetails.get("alRelationShip");
				String strFamilyIconYN="";
				StringBuffer sbfCaption=new StringBuffer();
				int iUpdate=0;
				String strFlag="MEM";
				Long lngMemberSeqID=null;
				TableData  tableData =TTKCommon.getTableData(request);

				if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				{
					dependentDetailVO=(DependentDetailVO)((TableData)request.getSession().getAttribute("tableData")).
					getData().get(Integer.parseInt(request.getParameter("rownum")));

					if(dependentDetailVO.getAllowDeleteYN().equals("N"))
					{
						ActionMessages actionMessages = new ActionMessages();
						ActionMessage actionMessage=new ActionMessage("onlineforms.delete.self");
						actionMessages.add("global.error",actionMessage);
						saveErrors(request,actionMessages);
						return (mapping.findForward(strMemberDetails));
					}//end of if(dependentDetailVO.getAllowDeleteYN().equals("N"))
					lngMemberSeqID = dependentDetailVO.getMemberSeqID();
				}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))

				if(strLoginType.equals("H"))
				{
					sbfCaption=sbfCaption.append("[").append(onlinePolicyVO.getPolicyNbr()).append("] [").
					append(dependentDetailVO1.getEnrollmentNbr()).append("] [").append(strInsuredName).append("]");
					dependentDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
					ArrayList<Object> alParams = new ArrayList<Object>();
						alParams.add(strFlag);
						alParams.add(lngMemberSeqID);
						alParams.add(dependentDetailVO1.getPolicySeqID());
						alParams.add(dependentDetailVO.getUpdatedBy());
							
						
						iUpdate=onlineAccessManager.deleteGeneral(alParams);
						
						
						//log.info("iUpdate value is :"+iUpdate);
						frmMemberDetails.initialize(mapping);
						frmMemberDetails.set("memberTypeID",dependentDetailVO1.getMemberTypeID());
						frmMemberDetails.set("totalSumInsured",bgDefaultSumInsured!=null?
			    				bgDefaultSumInsured.toString():"");
						ArrayList alDependantList=onlineAccessManager.dependentList(tableData.getSearchData());
						tableData.setData(alDependantList);
						if(alDependantList.size()>0)
				        {
					        dependentDetailVO=(DependentDetailVO)alDependantList.get(0);
					        strFamilyIconYN =dependentDetailVO.getFamilySumIconYN();
					        frmMemberDetails.set("floaterSumInsured",dependentDetailVO.getFloaterSumInsured()!=null?
									dependentDetailVO.getFloaterSumInsured().toString():"");
							frmMemberDetails.set("floaterSumStatus",dependentDetailVO.getFloaterSumStatus());
				        }
						for(int j=0;j<alDependantList.size();j++)
				        {
				        	dependentDetailVO=(DependentDetailVO)alDependantList.get(j);
				        	//hide the sum insured colunm if the policy subtype is Floater
				        	if(dependentDetailVO.getMemberTypeID().equals("PFL"))
				        	{
				        		((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(false);
				        	}//end of if(dependentDetailVO.getMemberTypeID().equals("PFL"))
				        	else
				        	{
				        		((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(true);
				        	}//end of if(dependentDetailVO.getAllowAddSumYN().equals("N"))

				        	if(dependentDetailVO.getAllowAddYN().equals("Y"))
				        	{
				        		((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
				        	}//end of if(dependentDetailVO.getAllowAddYN().equals("Y"))

				        	//hide the delete icon column based on the allowAddYN flag
				        	/*if(dependentDetailVO.getAllowAddYN().equals("N"))
				        	{
				        		((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(false);
				        	}//end of if(dependentDetailVO.getAllowAddYN().equals("N"))
				        	else
				        	{
				        		((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
				        	}//end of else if
		*/		        }//end of for(int j=0;j<alMemberDetails.size();j++)
						if(dependentDetailVO.getLoginWindowPeriodAlert()!=null && dependentDetailVO.getLoginWindowPeriodAlert()!="")
						{
							frmMemberDetails.set("loginWindowPeriodAlert",dependentDetailVO.getLoginWindowPeriodAlert());
							request.setAttribute("alert", dependentDetailVO.getLoginWindowPeriodAlert());
						}//end of if(dependentDetailVO.getLoginWindowPeriodAlert()!=null && dependentDetailVO.getLoginWindowPeriodAlert()!="")
						//set the field display properties
						//ArrayList alFieldInfo=onlineAccessManager.getFieldInfo(lngPolicySeqID);
				        //MemberPermVO memberPermVO = new MemberPermVO();

				        if(alFieldInfo.length>0)
			    		{
				    		for(int i= 0; i<alFieldInfo.length; i++)
				    		{
				    			//log.info("i value is :" +i);

				    			MemberPermVO memberPermVO = new MemberPermVO();
				    			memberPermVO=(MemberPermVO)alFieldInfo[i];
				    			setFieldDisplay(memberPermVO,frmMemberDetails);
				    			setTableDisplay(memberPermVO,tableData);
				    		}//end of for(int i= 0; i<alFieldInfo.size(); i++)
				    		frmMemberDetails.set("display","display");
			    		}//end of if(alFieldInfo.length>0)
			    		else
						{
							frmMemberDetails.set("display","");
							request.setAttribute("configurationinfo","error.weblogin.memconfig");
						}//end of else
			    		frmMemberDetails.set("policySeqID",dependentDetailVO1.getPolicySeqID().toString());
				        frmMemberDetails.set("policyGroupSeqID",dependentDetailVO1.getPolicyGroupSeqID().toString());
				        frmMemberDetails.set("allowAddYN",dependentDetailVO1.getAllowAddYN());
				        frmMemberDetails.set("insuredName",strInsuredName);
				        frmMemberDetails.set("caption",sbfCaption.toString());
				        frmMemberDetails.set("allowAddSumYN",dependentDetailVO1.getAllowAddSumYN());
				        frmMemberDetails.set("familySumIconYN",strFamilyIconYN);
				        frmMemberDetails.set("loginDate",(String)userSecurityProfile.getLoginDate());
				        frmMemberDetails.set("alRelationShip",alRelationShip);
				        frmMemberDetails.set("abbrCode",dependentDetailVO1.getAbbrCode());
				        frmMemberDetails.set("enrollmentNbr",dependentDetailVO1.getEnrollmentNbr());
				        /*frmMemberDetails.set("floaterSumInsured",dependentDetailVO1.getFloaterSumInsured()!=null?
			    				dependentDetailVO1.getFloaterSumInsured().toString() :"");*/
			    		//frmMemberDetails.set("floaterSumStatus",dependentDetailVO1.getFloaterSumStatus());
			    		frmMemberDetails.set("defaultSumInsured",bgDefaultSumInsured!=null?
			    				bgDefaultSumInsured.toString():"");
				        frmMemberDetails.set("alFieldInfo",(MemberPermVO[])alFieldInfo);
				        frmMemberDetails.set("cancelYN",dependentDetailVO1.getCancelYN());
				        frmMemberDetails.set("expiredYN",dependentDetailVO1.getExpiredYN());
				        frmMemberDetails.set("policyOPT",dependentDetailVO.getPolicy_Stop_YN());//To disable family sumInsured Icon on OPTOUT --KOC-1216
				        request.getSession().setAttribute("tableData",tableData);
				        request.getSession().setAttribute("frmMemberDetails",frmMemberDetails);

				}//end of if(strLoginType.equals("H"))
				if(strLoginType.equals("E"))
				{
					sbfCaption=sbfCaption.append("[").append(userSecurityProfile.getPolicyNo()).append("] [").
					append(dependentDetailVO1.getEnrollmentNbr()).append("] [").append(strInsuredName).append("]");
					//dependentDetailVO.setUpdatedBy(TTKCommon.getLong(userSecurityProfile.getUSER_ID()));
					dependentDetailVO.setUpdatedBy(new Long(1));
					//Added for Oracle alert Message on Deletion of Records
					
					
					
					request.setAttribute("RelationShipTypeID", dependentDetailVO.getRelationTypeID());
				    
					request.setAttribute("rownum",request.getParameter("rownum"));	
					
					if(dependentDetailVO1.getEnrollmentNbr().contains("O0099"))
					{
						if(dependentDetailVO.getRelationTypeID().contains("NFR")||(dependentDetailVO.getRelationTypeID().contains("YFL"))||(dependentDetailVO.getRelationTypeID().contains("YMO"))||(dependentDetailVO.getRelationTypeID().contains("YML")))
						{
							request.setAttribute("alert","message.oracle");														
						}
						else
						{
							request.setAttribute("alert","message.others");
						}
					}
					else
					{
						
						ArrayList<Object> alParams = new ArrayList<Object>();
						alParams.add(strFlag);
						alParams.add(lngMemberSeqID);
						alParams.add(dependentDetailVO1.getPolicySeqID());
						alParams.add(dependentDetailVO.getUpdatedBy());
							
						
						iUpdate=onlineAccessManager.deleteGeneral(alParams);
						
						
						//log.info("iUpdate value is :"+iUpdate);
						frmMemberDetails.initialize(mapping);
						frmMemberDetails.set("memberTypeID",dependentDetailVO1.getMemberTypeID());
						frmMemberDetails.set("totalSumInsured",bgDefaultSumInsured!=null?
			    				bgDefaultSumInsured.toString():"");
						ArrayList alDependantList=onlineAccessManager.dependentList(tableData.getSearchData());
						tableData.setData(alDependantList);
						if(alDependantList.size()>0)
				        {
					        dependentDetailVO=(DependentDetailVO)alDependantList.get(0);
					        strFamilyIconYN =dependentDetailVO.getFamilySumIconYN();
					        frmMemberDetails.set("floaterSumInsured",dependentDetailVO.getFloaterSumInsured()!=null?
									dependentDetailVO.getFloaterSumInsured().toString():"");
							frmMemberDetails.set("floaterSumStatus",dependentDetailVO.getFloaterSumStatus());
				        }
						for(int j=0;j<alDependantList.size();j++)
				        {
				        	dependentDetailVO=(DependentDetailVO)alDependantList.get(j);
				        	//hide the sum insured colunm if the policy subtype is Floater
				        	if(dependentDetailVO.getMemberTypeID().equals("PFL"))
				        	{
				        		((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(false);
				        	}//end of if(dependentDetailVO.getMemberTypeID().equals("PFL"))
				        	else
				        	{
				        		((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(true);
				        	}//end of if(dependentDetailVO.getAllowAddSumYN().equals("N"))

				        	if(dependentDetailVO.getAllowAddYN().equals("Y"))
				        	{
				        		((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
				        	}//end of if(dependentDetailVO.getAllowAddYN().equals("Y"))

				        	//hide the delete icon column based on the allowAddYN flag
				        	/*if(dependentDetailVO.getAllowAddYN().equals("N"))
				        	{
				        		((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(false);
				        	}//end of if(dependentDetailVO.getAllowAddYN().equals("N"))
				        	else
				        	{
				        		((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
				        	}//end of else if
		*/		        }//end of for(int j=0;j<alMemberDetails.size();j++)
						if(dependentDetailVO.getLoginWindowPeriodAlert()!=null && dependentDetailVO.getLoginWindowPeriodAlert()!="")
						{
							frmMemberDetails.set("loginWindowPeriodAlert",dependentDetailVO.getLoginWindowPeriodAlert());
							request.setAttribute("alert", dependentDetailVO.getLoginWindowPeriodAlert());
						}//end of if(dependentDetailVO.getLoginWindowPeriodAlert()!=null && dependentDetailVO.getLoginWindowPeriodAlert()!="")
						//set the field display properties
						//ArrayList alFieldInfo=onlineAccessManager.getFieldInfo(lngPolicySeqID);
				        //MemberPermVO memberPermVO = new MemberPermVO();

				        if(alFieldInfo.length>0)
			    		{
				    		for(int i= 0; i<alFieldInfo.length; i++)
				    		{
				    			//log.info("i value is :" +i);

				    			MemberPermVO memberPermVO = new MemberPermVO();
				    			memberPermVO=(MemberPermVO)alFieldInfo[i];
				    			setFieldDisplay(memberPermVO,frmMemberDetails);
				    			setTableDisplay(memberPermVO,tableData);
				    		}//end of for(int i= 0; i<alFieldInfo.size(); i++)
				    		frmMemberDetails.set("display","display");
			    		}//end of if(alFieldInfo.length>0)
			    		else
						{
							frmMemberDetails.set("display","");
							request.setAttribute("configurationinfo","error.weblogin.memconfig");
						}//end of else
			    		frmMemberDetails.set("policySeqID",dependentDetailVO1.getPolicySeqID().toString());
				        frmMemberDetails.set("policyGroupSeqID",dependentDetailVO1.getPolicyGroupSeqID().toString());
				        frmMemberDetails.set("allowAddYN",dependentDetailVO1.getAllowAddYN());
				        frmMemberDetails.set("insuredName",strInsuredName);
				        frmMemberDetails.set("caption",sbfCaption.toString());
				        frmMemberDetails.set("allowAddSumYN",dependentDetailVO1.getAllowAddSumYN());
				        frmMemberDetails.set("familySumIconYN",strFamilyIconYN);
				        frmMemberDetails.set("loginDate",(String)userSecurityProfile.getLoginDate());
				        frmMemberDetails.set("alRelationShip",alRelationShip);
				        frmMemberDetails.set("abbrCode",dependentDetailVO1.getAbbrCode());
				        frmMemberDetails.set("enrollmentNbr",dependentDetailVO1.getEnrollmentNbr());
				        /*frmMemberDetails.set("floaterSumInsured",dependentDetailVO1.getFloaterSumInsured()!=null?
			    				dependentDetailVO1.getFloaterSumInsured().toString() :"");*/
			    		//frmMemberDetails.set("floaterSumStatus",dependentDetailVO1.getFloaterSumStatus());
			    		frmMemberDetails.set("defaultSumInsured",bgDefaultSumInsured!=null?
			    				bgDefaultSumInsured.toString():"");
				        frmMemberDetails.set("alFieldInfo",(MemberPermVO[])alFieldInfo);
				        frmMemberDetails.set("cancelYN",dependentDetailVO1.getCancelYN());
				        frmMemberDetails.set("expiredYN",dependentDetailVO1.getExpiredYN());
				        frmMemberDetails.set("policyOPT",dependentDetailVO.getPolicy_Stop_YN());//To disable family sumInsured Icon on OPTOUT --KOC-1216
				        request.getSession().setAttribute("tableData",tableData);
				        request.getSession().setAttribute("frmMemberDetails",frmMemberDetails);

					}//Modification ended for Oracle alert Message
				}//end of if(strLoginType.equals("E"))
				return (mapping.findForward(strMemberDetails));

			}//end of try
			catch(TTKException expTTK)
			{
				return this.processOnlineExceptions(request,mapping,expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processOnlineExceptions(request,mapping,new TTKException(exp,strMember));
			}//end of catch(Exception exp)
		}

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
		//Added for Oracle alert Message on Deletion of Records
		public ActionForward doconfirmDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
									  HttpServletResponse response) throws Exception{
			try{
				setOnlineLinks(request);
				log.info("do OnlineMemberDetailsAction doconfirmDelete");
				DynaActionForm frmMemberDetails=(DynaActionForm)form;
				OnlineAccessManager onlineAccessManager=this.getOnlineAccessManagerObject();
				DependentDetailVO dependentDetailVO =null;
				UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
				OnlineAccessPolicyVO onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
				String strLoginType=userSecurityProfile.getLoginType();
				DependentDetailVO dependentDetailVO1 = new DependentDetailVO();
				dependentDetailVO1=(DependentDetailVO)FormUtils.getFormValues(frmMemberDetails,this, mapping, request);
				String strInsuredName=(String)frmMemberDetails.get("insuredName");
				BigDecimal bgDefaultSumInsured=TTKCommon.getBigDecimal((String)frmMemberDetails.get("defaultSumInsured"));
				MemberPermVO[] alFieldInfo =(MemberPermVO[])frmMemberDetails.get("alFieldInfo");
				ArrayList alRelationShip = (ArrayList)frmMemberDetails.get("alRelationShip");
				String strFamilyIconYN="";
				StringBuffer sbfCaption=new StringBuffer();
				int iUpdate=0;
				String strFlag="MEM";
				Long lngMemberSeqID=null;
				TableData  tableData =TTKCommon.getTableData(request);

				if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				{
					dependentDetailVO=(DependentDetailVO)((TableData)request.getSession().getAttribute("tableData")).
					getData().get(Integer.parseInt(request.getParameter("rownum")));

					if(dependentDetailVO.getAllowDeleteYN().equals("N"))
					{
						ActionMessages actionMessages = new ActionMessages();
						ActionMessage actionMessage=new ActionMessage("onlineforms.delete.self");
						actionMessages.add("global.error",actionMessage);
						saveErrors(request,actionMessages);
						return (mapping.findForward(strMemberDetails));
					}//end of if(dependentDetailVO.getAllowDeleteYN().equals("N"))
					lngMemberSeqID = dependentDetailVO.getMemberSeqID();
				}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))

				if(strLoginType.equals("H"))
				{
					sbfCaption=sbfCaption.append("[").append(onlinePolicyVO.getPolicyNbr()).append("] [").
					append(dependentDetailVO1.getEnrollmentNbr()).append("] [").append(strInsuredName).append("]");
					dependentDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
				}//end of if(strLoginType.equals("H"))
				if(strLoginType.equals("E"))
				{
					sbfCaption=sbfCaption.append("[").append(userSecurityProfile.getPolicyNo()).append("] [").
					append(dependentDetailVO1.getEnrollmentNbr()).append("] [").append(strInsuredName).append("]");
					//dependentDetailVO.setUpdatedBy(TTKCommon.getLong(userSecurityProfile.getUSER_ID()));
					dependentDetailVO.setUpdatedBy(new Long(1));
							
				}//end of if(strLoginType.equals("E"))
				
				ArrayList<Object> alParams = new ArrayList<Object>();
				alParams.add(strFlag);
				alParams.add(lngMemberSeqID);
				alParams.add(dependentDetailVO1.getPolicySeqID());
				alParams.add(dependentDetailVO.getUpdatedBy());
					
				
				iUpdate=onlineAccessManager.deleteGeneral(alParams);
				
				
				//log.info("iUpdate value is :"+iUpdate);
				frmMemberDetails.initialize(mapping);
				frmMemberDetails.set("memberTypeID",dependentDetailVO1.getMemberTypeID());
				frmMemberDetails.set("totalSumInsured",bgDefaultSumInsured!=null?
	    				bgDefaultSumInsured.toString():"");
				ArrayList alDependantList=onlineAccessManager.dependentList(tableData.getSearchData());
				tableData.setData(alDependantList);
				if(alDependantList.size()>0)
		        {
			        dependentDetailVO=(DependentDetailVO)alDependantList.get(0);
			        strFamilyIconYN =dependentDetailVO.getFamilySumIconYN();
			        frmMemberDetails.set("floaterSumInsured",dependentDetailVO.getFloaterSumInsured()!=null?
							dependentDetailVO.getFloaterSumInsured().toString():"");
					frmMemberDetails.set("floaterSumStatus",dependentDetailVO.getFloaterSumStatus());
		        }
				for(int j=0;j<alDependantList.size();j++)
		        {
		        	dependentDetailVO=(DependentDetailVO)alDependantList.get(j);
		        	//hide the sum insured colunm if the policy subtype is Floater
		        	if(dependentDetailVO.getMemberTypeID().equals("PFL"))
		        	{
		        		((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(false);
		        	}//end of if(dependentDetailVO.getMemberTypeID().equals("PFL"))
		        	else
		        	{
		        		((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(true);
		        	}//end of if(dependentDetailVO.getAllowAddSumYN().equals("N"))

		        	if(dependentDetailVO.getAllowAddYN().equals("Y"))
		        	{
		        		((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
		        	}//end of if(dependentDetailVO.getAllowAddYN().equals("Y"))

		        	//hide the delete icon column based on the allowAddYN flag
		        	/*if(dependentDetailVO.getAllowAddYN().equals("N"))
		        	{
		        		((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(false);
		        	}//end of if(dependentDetailVO.getAllowAddYN().equals("N"))
		        	else
		        	{
		        		((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
		        	}//end of else if
*/		        }//end of for(int j=0;j<alMemberDetails.size();j++)
				if(dependentDetailVO.getLoginWindowPeriodAlert()!=null && dependentDetailVO.getLoginWindowPeriodAlert()!="")
				{
					frmMemberDetails.set("loginWindowPeriodAlert",dependentDetailVO.getLoginWindowPeriodAlert());
					request.setAttribute("alert", dependentDetailVO.getLoginWindowPeriodAlert());
				}//end of if(dependentDetailVO.getLoginWindowPeriodAlert()!=null && dependentDetailVO.getLoginWindowPeriodAlert()!="")
				//set the field display properties
				//ArrayList alFieldInfo=onlineAccessManager.getFieldInfo(lngPolicySeqID);
		        //MemberPermVO memberPermVO = new MemberPermVO();

		        if(alFieldInfo.length>0)
	    		{
		    		for(int i= 0; i<alFieldInfo.length; i++)
		    		{
		    			//log.info("i value is :" +i);

		    			MemberPermVO memberPermVO = new MemberPermVO();
		    			memberPermVO=(MemberPermVO)alFieldInfo[i];
		    			setFieldDisplay(memberPermVO,frmMemberDetails);
		    			setTableDisplay(memberPermVO,tableData);
		    		}//end of for(int i= 0; i<alFieldInfo.size(); i++)
		    		frmMemberDetails.set("display","display");
	    		}//end of if(alFieldInfo.length>0)
	    		else
				{
					frmMemberDetails.set("display","");
					request.setAttribute("configurationinfo","error.weblogin.memconfig");
				}//end of else
	    		frmMemberDetails.set("policySeqID",dependentDetailVO1.getPolicySeqID().toString());
		        frmMemberDetails.set("policyGroupSeqID",dependentDetailVO1.getPolicyGroupSeqID().toString());
		        frmMemberDetails.set("allowAddYN",dependentDetailVO1.getAllowAddYN());
		        frmMemberDetails.set("insuredName",strInsuredName);
		        frmMemberDetails.set("caption",sbfCaption.toString());
		        frmMemberDetails.set("allowAddSumYN",dependentDetailVO1.getAllowAddSumYN());
		        frmMemberDetails.set("familySumIconYN",strFamilyIconYN);
		        frmMemberDetails.set("loginDate",(String)userSecurityProfile.getLoginDate());
		        frmMemberDetails.set("alRelationShip",alRelationShip);
		        frmMemberDetails.set("abbrCode",dependentDetailVO1.getAbbrCode());
		        frmMemberDetails.set("enrollmentNbr",dependentDetailVO1.getEnrollmentNbr());
		        /*frmMemberDetails.set("floaterSumInsured",dependentDetailVO1.getFloaterSumInsured()!=null?
	    				dependentDetailVO1.getFloaterSumInsured().toString() :"");*/
	    		//frmMemberDetails.set("floaterSumStatus",dependentDetailVO1.getFloaterSumStatus());
	    		frmMemberDetails.set("defaultSumInsured",bgDefaultSumInsured!=null?
	    				bgDefaultSumInsured.toString():"");
		        frmMemberDetails.set("alFieldInfo",(MemberPermVO[])alFieldInfo);
		        frmMemberDetails.set("cancelYN",dependentDetailVO1.getCancelYN());
		        frmMemberDetails.set("expiredYN",dependentDetailVO1.getExpiredYN());
		        frmMemberDetails.set("policyOPT",dependentDetailVO.getPolicy_Stop_YN());//To disable family sumInsured Icon on OPTOUT --KOC-1216
		        request.getSession().setAttribute("tableData",tableData);
		        request.getSession().setAttribute("frmMemberDetails",frmMemberDetails);
		        
				return (mapping.findForward(strMemberDetails));

			}//end of try
			catch(TTKException expTTK)
			{
				return this.processOnlineExceptions(request,mapping,expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processOnlineExceptions(request,mapping,new TTKException(exp,strMember));
			}//end of catch(Exception exp)
		}//end of doconfirmDelete Added for Oracle alert Message


		
		
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
	    		setOnlineLinks(request);
	    		log.info("do OnlineMemberDetailsAction doReset");
	    		DependentDetailVO dependentDetailVO =null;
	    		DynaActionForm frmMemberDetails=(DynaActionForm)form;
	    		TableData  tableData =TTKCommon.getTableData(request);
	    		OnlineAccessManager onlineAccessManager=this.getOnlineAccessManagerObject();
	    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	    		StringBuffer sbfCaption=new StringBuffer();
	    		String strRelationID =(String)frmMemberDetails.get("relationID");
	    		String strGenderID = (String)frmMemberDetails.get("genderYN");
	    		//get the Relationship list based on the Ins. Company
	    		ArrayList alRelationShip = (ArrayList)frmMemberDetails.get("alRelationShip");
	    		OnlineAccessPolicyVO onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
	    		DependentDetailVO dependentDetailVO1 = new DependentDetailVO();
				dependentDetailVO1=(DependentDetailVO)FormUtils.getFormValues(frmMemberDetails,this, mapping, request);
	    		String strInsuredName=(String)frmMemberDetails.get("insuredName");
	    		MemberPermVO[] alFieldInfo =(MemberPermVO[])frmMemberDetails.get("alFieldInfo");
	    		String strFamilyIconYN=(String)frmMemberDetails.get("familySumIconYN");
	    		BigDecimal bgDefaultSumInsured=TTKCommon.getBigDecimal((String)frmMemberDetails.get("defaultSumInsured"));

	    		//if rownumber is found populate the form object
	    		if(frmMemberDetails.get("memberSeqID")!=null && !frmMemberDetails.get("memberSeqID").equals(""))
	    		{
	    			dependentDetailVO=onlineAccessManager.selectDependent(TTKCommon.getLong((String)frmMemberDetails.get("memberSeqID")),userSecurityProfile.getLoginType());
	    			strRelationID=dependentDetailVO.getRelationTypeID();
	    			strGenderID=dependentDetailVO.getGenderYN();
	    			dependentDetailVO.setRelationTypeID(dependentDetailVO.getRelationTypeID()+"#"+dependentDetailVO.getGenderYN());
	    			//hide the sum insured column if the policy sub-type is Floater
	    			if(dependentDetailVO.getMemberTypeID().equals("PFL"))
	    			{
	    				((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(false);
	    			}//end of if(dependentDetailVO.getMemberTypeID().equals("PFL"))
	    			else
	    			{
	    				((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(true);
	    			}//end of else

	    			if(dependentDetailVO.getAllowAddYN().equals("Y"))
	    			{
	    				((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
	    			}//end of if(dependentDetailVO.getAllowAddYN().equals("Y"))

	    			//hide the delete icon column based on the allowAddYN flag
	    			/*if(dependentDetailVO.getAllowAddYN().equals("N"))
	    			{
	    				((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(false);
	    			}//end of if(dependentDetailVO.getAllowAddYN().equals("N"))
	    			else
	    			{
	    				((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
	    			}//end of else if
*/	    			frmMemberDetails = (DynaActionForm)FormUtils.setFormValues("frmMemberDetails",dependentDetailVO,
	    					this,mapping,request);
		    		frmMemberDetails.set("sumInsIconEditYN","N");
	    		}// end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	    		else
	    		{
	    			frmMemberDetails.initialize(mapping);
	    			frmMemberDetails.set("memberTypeID",dependentDetailVO1.getMemberTypeID());
	    			frmMemberDetails.set("totalSumInsured",bgDefaultSumInsured!=null?
		    				bgDefaultSumInsured.toString():"");
	    		}// end of else

	    		//set the display properties of the fields
	    		//log.info("Arraylist length :"+alFieldInfo.length);
	    		if(alFieldInfo.length>0)
	    		{
		    		for(int i= 0; i<alFieldInfo.length; i++)
		    		{
		    			//log.info("i value is :" +i);
		    			MemberPermVO memberPermVO = new MemberPermVO();
		    			memberPermVO=(MemberPermVO)alFieldInfo[i];
		    			setFieldDisplay(memberPermVO,frmMemberDetails);
		    			setTableDisplay(memberPermVO,tableData);
		    		}//end of for(int i= 0; i<alFieldInfo.size(); i++)
		    		frmMemberDetails.set("display","display");
	    		}//end of if(alFieldInfo.length>0)
	    		else
				{
					frmMemberDetails.set("display","");
					request.setAttribute("configurationinfo","error.weblogin.memconfig");
				}//end of else
	    		if(frmMemberDetails.get("ageStatus").equals("HIDE"))
	    		{
	    			frmMemberDetails.set("age",null);
	    		}
	    		if(frmMemberDetails.get("dobStatus").equals("HIDE"))
	    		{
	    			frmMemberDetails.set("dateOfBirth",null);
	    		}
	    		//build the caption based on the type of the Logged in User
	    		if(userSecurityProfile.getLoginType().equals("H"))
	    		{
	    			sbfCaption=sbfCaption.append("[").append(onlinePolicyVO.getPolicyNbr()).append("] [")
	    			.append(dependentDetailVO1.getEnrollmentNbr()).append("] [").append(strInsuredName).append("]");
	    		}//end of if(userSecurityProfile.getLoginType().equals("H"))
	    		else if(userSecurityProfile.getLoginType().equals("E"))
	    		{
	    			sbfCaption=sbfCaption.append("[").append(userSecurityProfile.getPolicyNo()).append("] [")
	    			.append(dependentDetailVO1.getEnrollmentNbr()).append("] [").append(strInsuredName).append("]");
	    		}//end of else if(userSecurityProfile.getLoginType().equals("E"))
	    		if(dependentDetailVO1.getLoginWindowPeriodAlert()!=null && dependentDetailVO1.getLoginWindowPeriodAlert()!="")
				{
					frmMemberDetails.set("loginWindowPeriodAlert",dependentDetailVO1.getLoginWindowPeriodAlert());
					request.setAttribute("alert", dependentDetailVO1.getLoginWindowPeriodAlert());
				}//end of if(dependentDetailVO.getLoginWindowPeriodAlert()!=null && dependentDetailVO.getLoginWindowPeriodAlert()!="")
	    		frmMemberDetails.set("policySeqID",dependentDetailVO1.getPolicySeqID().toString());
	    		frmMemberDetails.set("policyGroupSeqID",dependentDetailVO1.getPolicyGroupSeqID().toString());
	    		frmMemberDetails.set("insuredName",strInsuredName);
	    		frmMemberDetails.set("familySumIconYN",strFamilyIconYN);
	    		frmMemberDetails.set("caption",sbfCaption.toString());
	    		frmMemberDetails.set("loginDate",(String)userSecurityProfile.getLoginDate());
	    		frmMemberDetails.set("relationID",strRelationID);
	    		frmMemberDetails.set("abbrCode",dependentDetailVO1.getAbbrCode());
	    		frmMemberDetails.set("alRelationShip",alRelationShip);
	    		frmMemberDetails.set("enrollmentNbr",dependentDetailVO1.getEnrollmentNbr());
	    		if(!strGenderID.equals("OTH"))
	    		{
	    			frmMemberDetails.set("genderTypeID",strGenderID);
	    		}//end of if(!strGenderID.equals("OTH"))
	    		frmMemberDetails.set("genderYN",strGenderID);
	    		frmMemberDetails.set("allowAddYN",dependentDetailVO1.getAllowAddYN());
	    		frmMemberDetails.set("cancelYN",dependentDetailVO1.getCancelYN());
	    		frmMemberDetails.set("expiredYN",dependentDetailVO1.getExpiredYN());
	    		frmMemberDetails.set("floaterSumInsured",dependentDetailVO1.getFloaterSumInsured()!=null?
	    				dependentDetailVO1.getFloaterSumInsured().toString() :"");
	    		frmMemberDetails.set("floaterSumStatus",dependentDetailVO1.getFloaterSumStatus());
	    		frmMemberDetails.set("defaultSumInsured",bgDefaultSumInsured!=null?
	    				bgDefaultSumInsured.toString():"");
	    		frmMemberDetails.set("alFieldInfo",(MemberPermVO[])alFieldInfo);
	    		request.getSession().setAttribute("frmMemberDetails",frmMemberDetails);
	    		return this.getForward(strMemberDetails, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
	    	{
	    		return this.processOnlineExceptions(request,mapping,expTTK);
	    	}//end of catch(TTKException expTTK)
	    	catch(Exception exp)
	    	{
	    		return this.processOnlineExceptions(request,mapping,new TTKException(exp,strMember));
	    	}//end of catch(Exception exp)
	    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	    		}//end if
	    	}//end of try
	    	catch(Exception exp)
	    	{
	    		throw new TTKException(exp, strFailure);
	    	}//end of catch
	    	return onlineAccessManager;
	    }//end of getOnlineAccessManagerObject()


/*		*//**
		 * Returns the MemberManager session object for invoking methods on it.
		 * @return MemberManager session object which can be used for method invokation
		 * @exception throws TTKException
		 *//*
		private MemberManager getMemberManager() throws TTKException
		{
			MemberManager memberManager = null;
			try
			{
				if(memberManager == null)
				{
					InitialContext ctx = new InitialContext();
					memberManager = (MemberManager) ctx.lookup("java:global/TTKServices/business.ejb3/MemberManagerBean!com.ttk.business.enrollment.MemberManager");
				}//end if(memberManager == null)
			}//end of try
			catch(Exception exp)
			{
				throw new TTKException(exp, "memberdetail");
			}//end of catch
			return memberManager;
		}//end getMemberManager()
*/
		//Added the following piece of code for integrating Rule related validation
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
	            }//end if(ruleManager == null)
	        }//end of try
	        catch(Exception exp)
	        {
	            throw new TTKException(exp, "memberdetail");
	        }//end of catch
	        return ruleManager;
	    }//end of getRuleManagerObject()

	    /**
	     * this method will add search criteria fields and values to the arraylist and will return it
	     * @param frmTransSearch formbean which contains the search fields
	     * @param request HttpServletRequest
	     * @param strLoginType string which contains the LoginType of the user
	     * @throws TTKException
	     */

	    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmMemberDetails,HttpServletRequest request,String strLoginType)
	    throws TTKException
	    {
	    	//build the column names along with their values to be searched
	        ArrayList<Object> alSearchParams = new ArrayList<Object>();
	        alSearchParams.add(TTKCommon.getLong((String)frmMemberDetails.get("policySeqID")));
	        alSearchParams.add(TTKCommon.getLong((String)frmMemberDetails.get("policyGroupSeqID")));
	        alSearchParams.add(strLoginType);
	        return alSearchParams;
	    }//end of populateSearchCriteria(DynaActionForm frmTransSearch,HttpServletRequest request)

	    /**
	     * This method sets the columns to be displayed in the grid
	     * @param memberPermVO object which contains the field information
	     * @param tableData object which contains the table information
	     */

	    private void setTableDisplay(MemberPermVO memberPermVO,TableData tableData)
	    {

	    	 if(memberPermVO.getFieldStatus()!=null && memberPermVO.getFieldStatus().equals("HIDE"))
	    	{
	    		//log.info("FieldName in setTableDisplay is :"+memberPermVO.getFieldName());
	    		if(memberPermVO.getFieldName().equals("MEM_NAME"))
		        {
		        	((Column)((ArrayList)tableData.getTitle()).get(0)).setVisibility(false);
		        }//end of if(memberPermVO.getFieldName().equals("MEM_NAME"))
	    		else if(memberPermVO.getFieldName().equals("RELSHIP_TYPE_ID"))
		        {
		        	((Column)((ArrayList)tableData.getTitle()).get(3)).setVisibility(false);
		        }//end of else if(memberPermVO.getFieldName().equals("RELSHIP_TYPE_ID"))
	    		else if(memberPermVO.getFieldName().equals("MEM_AGE"))
		        {
		        	((Column)((ArrayList)tableData.getTitle()).get(4)).setVisibility(false);
		        }//end of else if(memberPermVO.getFieldName().equals("MEM_AGE"))
	    		else if(memberPermVO.getFieldName().equals("GENDER_GENERAL_TYPE_ID"))
		    	{
		    		((Column)((ArrayList)tableData.getTitle()).get(5)).setVisibility(false);
		    	}//end of else if(memberPermVO.getFieldName().equals("GENDER_GENERAL_TYPE_ID"))
	    		else if(memberPermVO.getFieldName().equals("MEM_DOB"))
		        {
		        	((Column)((ArrayList)tableData.getTitle()).get(6)).setVisibility(false);
		        }//end of else if(memberPermVO.getFieldName().equals("MEM_DOB"))
	    		else if(memberPermVO.getFieldName().equals("TPA_ENROLLMENT_ID"))
		        {
		        	((Column)((ArrayList)tableData.getTitle()).get(1)).setVisibility(false);
		        }//end of else if(memberPermVO.getFieldName().equals("TPA_ENROLLMENT_ID"))
	    		else if(memberPermVO.getFieldName().equals("MEM_GENERAL_TYPE_ID"))
	    		{
	    			((Column)((ArrayList)tableData.getTitle()).get(2)).setVisibility(false);
	    		}//end of else if(memberPermVO.getFieldName().equals("MEM_GENERAL_TYPE_ID"))
	    		else if(memberPermVO.getFieldName().equals("MEM_TOT_SUM_INSURED"))
	    		{
	    			((Column)((ArrayList)tableData.getTitle()).get(7)).setVisibility(false);
	    		}//end of else if(memberPermVO.getFieldName().equals("MEM_TOT_SUM_INSURED"))
	    	}//end of if(memberPermVO.getFieldStatus()!=null && memberPermVO.getFieldStatus().equals("HIDE"))

	    	 else if(memberPermVO.getFieldStatus()==null ||memberPermVO.getFieldStatus()=="")
	    	 {
	    		 ((Column)((ArrayList)tableData.getTitle()).get(0)).setVisibility(false);
	    		 ((Column)((ArrayList)tableData.getTitle()).get(1)).setVisibility(false);
	    		 ((Column)((ArrayList)tableData.getTitle()).get(2)).setVisibility(false);
	    		 ((Column)((ArrayList)tableData.getTitle()).get(3)).setVisibility(false);
	    		 ((Column)((ArrayList)tableData.getTitle()).get(4)).setVisibility(false);
	    		 ((Column)((ArrayList)tableData.getTitle()).get(5)).setVisibility(false);
	    		 ((Column)((ArrayList)tableData.getTitle()).get(6)).setVisibility(false);
	    		 ((Column)((ArrayList)tableData.getTitle()).get(7)).setVisibility(false);
	    		 ((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(false);
	    		 ((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(false);
	    	}//end of else if(memberPermVO.getFieldStatus()==null ||memberPermVO.getFieldStatus()=="")

	    }//end of setTableDisplay(MemberPermVO memberPermVO,TableData tableData)

	    /**
	     * this method sets the field to be displayed and the status of the fields
	     * @param memberPermVO object which contains the field information
	     * @param frmMemberDetails formbean object to which the values have to be set
	     */

	    private void setFieldDisplay(MemberPermVO memberPermVO,DynaActionForm frmMemberDetails)
	    {
	    	if(memberPermVO.getFieldName().equals("MEM_NAME"))
	    	{
	    		frmMemberDetails.set("nameMandatoryYN",memberPermVO.getMandatoryYN());
	    		frmMemberDetails.set("nameStatus",memberPermVO.getFieldStatus());
	    	}//end of if(memberPermVO.getFieldName().equals("MEM_NAME"))
	    	else if(memberPermVO.getFieldName().equals("MEM_DOB"))
	    	{
	    		frmMemberDetails.set("dobMandatoryYN",memberPermVO.getMandatoryYN());
	    		frmMemberDetails.set("dobStatus",memberPermVO.getFieldStatus());
	    	}//end of else if(memberPermVO.getFieldName().equals("MEM_DOB"))
	    	else if(memberPermVO.getFieldName().equals("GENDER_GENERAL_TYPE_ID"))
	    	{
	    		frmMemberDetails.set("genderMandatoryYN",memberPermVO.getMandatoryYN());
	    		frmMemberDetails.set("genderStatus",memberPermVO.getFieldStatus());
	    	}//end of else if(memberPermVO.getFieldName().equals("GENDER_GENERAL_TYPE_ID"))
	    	else if(memberPermVO.getFieldName().equals("MEM_AGE"))
	    	{
	    		frmMemberDetails.set("ageMandatoryYN",memberPermVO.getMandatoryYN());
	    		frmMemberDetails.set("ageStatus",memberPermVO.getFieldStatus());
	    	}//end of else if(memberPermVO.getFieldName().equals("MEM_AGE"))
	    	else if(memberPermVO.getFieldName().equals("RELSHIP_TYPE_ID"))
	    	{
	    		frmMemberDetails.set("relationMandatoryYN",memberPermVO.getMandatoryYN());
	    		frmMemberDetails.set("relationStatus",memberPermVO.getFieldStatus());
	    	}//end of else if(memberPermVO.getFieldName().equals("RELSHIP_TYPE_ID"))
	    	else if(memberPermVO.getFieldName().equals("MEM_TOT_SUM_INSURED"))
	    	{
	    		frmMemberDetails.set("sumInsuredMandatoryYN",memberPermVO.getMandatoryYN());
	    		frmMemberDetails.set("sumInsuredStatus",memberPermVO.getFieldStatus());
	    	}//end of else if(memberPermVO.getFieldName().equals("MEM_TOT_SUM_INSURED"))
	    	else if(memberPermVO.getFieldName().equals("MEM_GENERAL_TYPE_ID"))
	    	{
	    		frmMemberDetails.set("memTypeMandatoryYN",memberPermVO.getMandatoryYN());
	    		frmMemberDetails.set("memTypeStatus",memberPermVO.getFieldStatus());
	    	}//end of else if(memberPermVO.getFieldName().equals("MEM_GENERAL_TYPE_ID"))
	    	else if(memberPermVO.getAddSumIconYN().equals("Y"))
	    	{
	    		frmMemberDetails.set("addSumIconYN",memberPermVO.getAddSumIconYN());
	    	}//end of else if(memberPermVO.getAddSumIconYN().equals(""))
	    }//end of setFieldDisplay(MemberPermVO memberPermVO,DynaActionForm frmMemberDetails)

}//end of OnlineMemberDetailsAction class
