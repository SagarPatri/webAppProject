/**
 * @ (#) PremiumInfoAction.java Feb 9, 2006
 * Project      : TTK HealthCare Services
 * File         : PremiumInfoAction.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Feb 9, 2006
 *
 * @author       : Arun K N
 * Modified by   : Arun K N
 * Modified date : May 11, 2007
 * Reason        : Conversion to Dispatch Action
 */

package com.ttk.action.enrollment;

import java.math.BigDecimal;
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
import com.ttk.action.tree.TreeData;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.PremiumInfoVO;

/**
 * This class is used to calculate the Sum Insured and PremiuimInfo of the Policy,
 * for each member and store it in database.It also provides facility to edit the Insured
 * Amount and Premium Information for all the Policy Types in both Enrollment and
 * Endorsement flow.
 *
 */
public class PremiumInfoAction extends TTKAction{

    private static Logger log = Logger.getLogger(PremiumInfoAction.class);

    //declarations of modes
    //private static final String strBonusClose="BonusClose";

    private static final String strIndPolicy="Individual Policy";
    private static final String strIndGrpPolicy="Ind. Policy as Group";
    private static final String strCorporatePolicy="Corporate Policy";
    private static final String strNonCorporatePolicy="Non-Corporate Policy";

    //declare forward paths
    private static final String strIndPremiumInfo="indpremiuminfo";
    private static final String strGrpPremiumInfo="grppremiuminfo";
    private static final String strCorpPremiumInfo="corppremiuminfo";
    private static final String strNonCorpPremiumInfo="noncorppremiuminfo";
    private static final String strBonusInfo="bonusinfo";


    /**
     * This method is used to View the Premium Info of the Family/Group
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewPremiumInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the doViewPremiumInfo of PremiumInfoAction");
        	log.info("Inside the doViewPremiumInfo of PremiumInfoAction");
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
            MemberManager memberManager=this.getMemberManager();
            DynaActionForm frmPremiumInfo=(DynaActionForm)form;
           
            //declaration of the local variables
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            Long lngPolicyGrpSeqID=null;
            log.debug("Active Sub link is : "+strActiveSubLink);
            //get the Policy group Seqid from the treedata if coming here by selecting the object from tree
            if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
            {
                frmPremiumInfo.initialize(mapping); //initialize the form bean
                MemberVO memberVO=(MemberVO)treeData.getSelectedObject((String)request.
                				  getParameter("selectedroot"),null);
                lngPolicyGrpSeqID=memberVO.getPolicyGroupSeqID();
                frmPremiumInfo.set("policyGroupSeqID",lngPolicyGrpSeqID); //load the policy group seq id to form bean
            }//end of if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
            else//select the policy group Seq Id from the form bean
            {
                lngPolicyGrpSeqID=(Long)frmPremiumInfo.get("policyGroupSeqID");
            }//end of else

            //load the caption to be displayed
            frmPremiumInfo.set("caption","["+WebBoardHelper.getPolicyNumber(request)+"]");

            frmPremiumInfo.initialize(mapping);     //intialise the form bean to get new Data from database
            frmPremiumInfo.set("policyGroupSeqID",lngPolicyGrpSeqID); //load the policy group seq id to form bean
            frmPremiumInfo.set("caption","["+WebBoardHelper.getPolicyNumber(request)+"]"); //load the caption to be displayed

            ArrayList<Object> alSearchCriteria =new ArrayList<Object>();
            alSearchCriteria.add(lngPolicyGrpSeqID);    //policy_Group_seq_id
            alSearchCriteria.add(strSwitchType);    // identifier of Enrollment or Endorsement flow
            alSearchCriteria.add(getSubLinkCode(strActiveSubLink));    //identifier of current sublink

            //call business layer to get the premium info of family/Group
            ArrayList<Object> alPremiumInfo=memberManager.getPremiumList(alSearchCriteria);
            MemberVO memberVO1=(MemberVO) request.getSession().getAttribute("memberVO");
           
			
            if(alPremiumInfo!= null && alPremiumInfo.size()>0)
            {
                PremiumInfoVO premiumInfoVO=(PremiumInfoVO)alPremiumInfo.get(0);
                frmPremiumInfo.set("premiumInfo",(PremiumInfoVO[])alPremiumInfo.toArray(new PremiumInfoVO[0]));
                frmPremiumInfo.set("policySubTypeId",premiumInfoVO.getPolicySubTypeID());
                frmPremiumInfo.set("policySubTypeDesc",premiumInfoVO.getPolicySubTypeDesc());
                frmPremiumInfo.set("floaterSumInsured",premiumInfoVO.getFloaterSumInsured()!=null?
                        premiumInfoVO.getFloaterSumInsured().toString() :"" );
                frmPremiumInfo.set("floaterPremium",(String)TTKCommon.checkNull(premiumInfoVO.getFloatPremium()));
                frmPremiumInfo.set("selectregion",(String)TTKCommon.checkNull(premiumInfoVO. getSelectregion())); //Added as per KOC 1284 Change Request 
                frmPremiumInfo.set("selectregionYN",(String)TTKCommon.checkNull(premiumInfoVO.getSelectregionYN()));      //Added as per KOC 1284 Change Request 
                PremiumInfoVO[] p=(PremiumInfoVO[]) frmPremiumInfo.get("premiumInfo");
                premiumInfoVO=p[0];
                //if policySubType is Floater,clear the Total Family SumInsured and family premium
                if(!premiumInfoVO.getPolicySubTypeID().equals("PFL"))
                {
                    frmPremiumInfo.set("totalFlySumInsured",premiumInfoVO.getTotalFlySumInsured()!=null?
                            premiumInfoVO.getTotalFlySumInsured().toString() :"" );
                    frmPremiumInfo.set("totalFlyPremium",(String)TTKCommon.checkNull(premiumInfoVO.
                    		getTotalFamilyPremium()));
                }//end of if(!premiumInfoVO.getPolicySubTypeID().equals("PFL"))
                else
                {
                    frmPremiumInfo.set("totalFlySumInsured",null);
                    frmPremiumInfo.set("totalFlyPremium",null);
                }//end of else
                frmPremiumInfo.set("policySubTypeDesc",premiumInfoVO.getPolicySubTypeDesc());
                frmPremiumInfo.set("productSeqID",premiumInfoVO.getProductSeqID());
                frmPremiumInfo.set("activeLink",strActiveSubLink);
                if(memberVO1!=null)
    			{
                	
                	frmPremiumInfo.set("capitationYN",memberVO1.getCapitaionFlag().trim());
    			}
    			else
    			{
    				
    				frmPremiumInfo.set("capitationYN","1");
    			}
                if(premiumInfoVO.getMemberSeqID() != null)
                {	
                	frmPremiumInfo.set("selectedmemberSeqID",premiumInfoVO.getMemberSeqID().toString());
                	System.out.println("doViewPremiumInfo| selectedmemberSeqID ->"+premiumInfoVO.getMemberSeqID().toString());
                } 
                else
                	frmPremiumInfo.set("selectedmemberSeqID","");	
            }//end of if(alPremiumInfo!= null && alPremiumInfo.size()>0)
           log.info("strActiveSubLink==========="+strActiveSubLink);
            return this.getForward(getForwardPath(strActiveSubLink),mapping,request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"premiuminfo"));
        }//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to get reset the form
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
    	log.debug("Inside the doReset of PremiumInfoAction");
        return doViewPremiumInfo(mapping,form,request,response);
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


    /**
     * This method is used to temporarily store all the premium information before doing serverside validation
     * submitting it save the Information.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSubmitPremiumInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the doSubmitPremiumInfo of PremiumInfoAction");
        	log.info("Inside the doSubmitPremiumInfo of PremiumInfoAction");
        	//get the DynaActionForm instance
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");  
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);

            DynaActionForm frmPremiumInfo=(DynaActionForm)form;     //get the instance of formbean
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            //declaration of the local variables
            Long lngPolicyGrpSeqID=null;
            lngPolicyGrpSeqID=(Long)frmPremiumInfo.get("policyGroupSeqID");
            ArrayList<Object> alPremiumInfo=new ArrayList<Object>();
            PremiumInfoVO premiumInfoVO=null;
            // get data of all the members from request parameter
            String[] strMemberSeqIds=request.getParameterValues("memberSeqID");
            String[] strMemberName=request.getParameterValues("name");
            String[] strMemberPolicyTypeID=request.getParameterValues("memberPolicyTypeID");
            String[] strTotalSumInsured=request.getParameterValues("totalSumInsured");
           /* String[] strTotalPremium=request.getParameterValues("premiumPaid");
            String[] strCalcPremium=request.getParameterValues("calcPremium");*/
            String[] strGrossPremium=request.getParameterValues("grossPremium");
            String[] strproRataPremium=request.getParameterValues("proRataPremium");
            String[] strnetPremium=request.getParameterValues("netPremium");
            String[] strCancelYN=request.getParameterValues("cancelYN");
            //loop througth the no of members and prepare the ArrayList of premiuminfoVO's
            if(strMemberSeqIds!=null && strMemberSeqIds.length>0)
            {
                for(int iCount=0;iCount<strMemberSeqIds.length;iCount++)
                {
                    premiumInfoVO=new PremiumInfoVO();
                    premiumInfoVO.setMemberSeqID(TTKCommon.checkNull(strMemberSeqIds[iCount]).equals("")?
                            null:new Long(strMemberSeqIds[iCount]));
                    premiumInfoVO.setCancelYN(TTKCommon.checkNull(strCancelYN[iCount]));
                    premiumInfoVO.setName(TTKCommon.checkNull(strMemberName[iCount]));
                    premiumInfoVO.setMemberPolicyTypeID(TTKCommon.checkNull(strMemberPolicyTypeID[iCount]));
                    premiumInfoVO.setTotalSumInsured(TTKCommon.getBigDecimal(strTotalSumInsured[iCount]));
                  /*  premiumInfoVO.setPremiumPaid(TTKCommon.checkNull(strTotalPremium[iCount]));
                    premiumInfoVO.setCalcPremium(TTKCommon.getBigDecimal(strCalcPremium[iCount]));*/
                    premiumInfoVO.setGrossPremium(TTKCommon.getBigDecimal(strGrossPremium[iCount]));
                    premiumInfoVO.setProRataPremium(TTKCommon.getBigDecimal(strproRataPremium[iCount]));
                    premiumInfoVO.setNetPremium(TTKCommon.getBigDecimal(strnetPremium[iCount]));
                    if(iCount==0)   //in the first VO of ArrayList save floater,familySum Insured,floaterpremium and familyPremium
                    {
                        premiumInfoVO.setPolicyGroupSeqID(lngPolicyGrpSeqID);
                        premiumInfoVO.setFloaterSumInsured(TTKCommon.getBigDecimal((String)frmPremiumInfo.
                        		get("floaterSumInsured")));
                        premiumInfoVO.setFloatPremium(TTKCommon.checkNull((String)frmPremiumInfo.
                        		get("floaterPremium")));
                        premiumInfoVO.setTotalFlySumInsured(TTKCommon.getBigDecimal((String)frmPremiumInfo.
                        		get("totalFlySumInsured")));
                        premiumInfoVO.setTotalFamilyPremium(TTKCommon.checkNull((String)frmPremiumInfo.
                        		get("totalFlyPremium")));
                    }//end of if(iCount==0)
                
					premiumInfoVO.setSelectregion(TTKCommon.checkNull((String)frmPremiumInfo.get("selectregion")));  //added as per kOC 1284 Change Request 
                    premiumInfoVO.setSelectregionYN(TTKCommon.checkNull((String)frmPremiumInfo.get("selectregionYN")));  //added as per kOC 1284 Change Request 
					alPremiumInfo.add(premiumInfoVO);
                }//end of for(int iCount=0;iCount<strMemberSeqIds.length;iCount++)
            }//end of else if(strMode.equals(strSubmit))
            frmPremiumInfo.set("premiumInfo",(PremiumInfoVO[])alPremiumInfo.toArray(new PremiumInfoVO[0]));
            frmPremiumInfo.set("activeLink",strActiveSubLink);
            return mapping.findForward("submitform");
        }//end of try
        catch(TTKException expTTK)
        {
        	expTTK.printStackTrace();
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
        	exp.printStackTrace();
            return this.processExceptions(request, mapping, new TTKException(exp,"premiuminfo"));
        }//end of catch(Exception exp)
    }//end of doSubmitPremiumInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



    /**
     * This method is used to Save the Premium Info of the Family/Group members.
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
        try
        {
        	log.debug("Inside the doSave of PremiumInfoAction");
        	log.info("Inside the doSave of PremiumInfoAction");
        	//get the DynaActionForm instance
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");  
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);

            DynaActionForm frmPremiumInfo=(DynaActionForm)form;     //get the instance of formbean
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            //declaration of the local variables
            Long lngPolicyGrpSeqID=null;
            lngPolicyGrpSeqID=(Long)frmPremiumInfo.get("policyGroupSeqID");
            MemberManager memberManager=this.getMemberManager();
            ArrayList<Object> alPremiumInfo=new ArrayList<Object>();
            PremiumInfoVO premiumInfoVO=null;
            String strClearPremiumInfoYN=TTKCommon.checkNull((String)frmPremiumInfo.get("clearPremiumInfoYN"));
            //if strClearPremiumInfoYN field is checked then delete the premium info
            if(strClearPremiumInfoYN.equals("Y"))
            {
                premiumInfoVO=new PremiumInfoVO();
                premiumInfoVO.setPolicyGroupSeqID(lngPolicyGrpSeqID);
                if(strSwitchType.equals("ENM")){
                    premiumInfoVO.setSeqID(WebBoardHelper.getPolicySeqId(request));
                }
                else if(strSwitchType.equals("END")){
                    premiumInfoVO.setSeqID(WebBoardHelper.getEndorsementSeqId(request));
                }
                premiumInfoVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
                alPremiumInfo.add(premiumInfoVO);
            }//end of if(strClearPremiumInfoYN.equals("Y"))
            else        //prepare the premiuminfoVO to save the premium Information
            {
                String[] strMemberSeqIds=request.getParameterValues("memberSeqID");
                String[] strMemberName=request.getParameterValues("name");
                String[] strMemberPolicyTypeID=request.getParameterValues("memberPolicyTypeID");
                String[] strTotalSumInsured=request.getParameterValues("totalSumInsured");
               /* String[] strTotalPremium=request.getParameterValues("premiumPaid");*/
                String[] strCancelYN=request.getParameterValues("cancelYN");
                String[] strGrosspremium=request.getParameterValues("grossPremium");
                String[] strProRataPremium=request.getParameterValues("proRataPremium");
                String[] strNetPremium=request.getParameterValues("netPremium");
                
                if(strMemberSeqIds!=null && strMemberSeqIds.length>0)
                {
                    //loop througth the no of members and prepare the ArrayList of premiuminfoVO's
                    for(int iCount=0;iCount<strMemberSeqIds.length;iCount++)
                    {
                        premiumInfoVO=new PremiumInfoVO();
                        premiumInfoVO.setMemberSeqID(TTKCommon.checkNull(strMemberSeqIds[iCount]).equals("")?
                                null:new Long(strMemberSeqIds[iCount]));
                        premiumInfoVO.setCancelYN(TTKCommon.checkNull(strCancelYN[iCount]));
                        premiumInfoVO.setMemberPolicyTypeID(TTKCommon.checkNull(strMemberPolicyTypeID[iCount]));
                        premiumInfoVO.setTotalSumInsured(TTKCommon.getBigDecimal(strTotalSumInsured[iCount]));
                        premiumInfoVO.setName(TTKCommon.checkNull(strMemberName[iCount]));
                      /*  premiumInfoVO.setPremiumPaid(TTKCommon.checkNull(strTotalPremium[iCount]));
                        premiumInfoVO.setTotalPremium(TTKCommon.getBigDecimal(strTotalPremium[iCount]));*/
                        premiumInfoVO.setGrossPremium(TTKCommon.getBigDecimal(strGrosspremium[iCount]));
                        premiumInfoVO.setProRataPremium(TTKCommon.getBigDecimal(strProRataPremium[iCount]));
                        premiumInfoVO.setNetPremium(TTKCommon.getBigDecimal(strNetPremium[iCount]));
                        premiumInfoVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
                        if(iCount==0)   //send the family suminsured,floater sum insured and respective premiums in first element as per DAO convention
                        {
                            premiumInfoVO.setPolicyGroupSeqID(lngPolicyGrpSeqID);
                            premiumInfoVO.setFloaterSumInsured(TTKCommon.getBigDecimal((String)frmPremiumInfo.
                            		get("floaterSumInsured")));
                            premiumInfoVO.setFloaterPremium(TTKCommon.getBigDecimal((String)frmPremiumInfo.
                            		get("floaterPremium")));
                            premiumInfoVO.setTotalFlySumInsured(TTKCommon.getBigDecimal((String)frmPremiumInfo.
                            		get("totalFlySumInsured")));
                            premiumInfoVO.setTotalFlyPremium(TTKCommon.getBigDecimal((String)frmPremiumInfo.
                            		get("totalFlyPremium")));
                          
                          premiumInfoVO.setSelectregion(TTKCommon.checkNull((String)frmPremiumInfo.get("selectregion")));//added as per KOC 1284 Change Request 
						  premiumInfoVO.setSelectregionYN(TTKCommon.checkNull((String)frmPremiumInfo.get("selectregionYN")));//added as per KOC 1284 Change Request 
                        }//end of if(iCount==0)
                        if(strSwitchType.equals("ENM"))
                        {
                            premiumInfoVO.setSeqID(WebBoardHelper.getPolicySeqId(request));
                        }//end of if(strSwitchType.equals("ENM"))
                        else if(strSwitchType.equals("END"))
                        {
                            premiumInfoVO.setSeqID(WebBoardHelper.getEndorsementSeqId(request));
                        }//end of else if(strSwitchType.equals("END"))
                        alPremiumInfo.add(premiumInfoVO);
                    }//end of for(int iCount=0;iCount<strMemberSeqIds.length;iCount++)
                }//end of if(strMemberSeqIds!=null && strMemberSeqIds.length>0)
            }//end of else

            if(alPremiumInfo!=null && alPremiumInfo.size()>0)
            {
                frmPremiumInfo.set("premiumInfo",(PremiumInfoVO[])alPremiumInfo.toArray(new PremiumInfoVO[0]));
                //call the Dao to update the premium Info
               
                int iCount=memberManager.savePremium(alPremiumInfo,strSwitchType,getSubLinkCode(strActiveSubLink),
                        strClearPremiumInfoYN);

                if(iCount>0)    //if record is updated then requery
                {
                    frmPremiumInfo.initialize(mapping);     //intialise the form bean to get new Data from database
                    frmPremiumInfo.set("policyGroupSeqID",lngPolicyGrpSeqID); //load the policy group seq id to form bean
                    frmPremiumInfo.set("caption","["+WebBoardHelper.getPolicyNumber(request)+"]"); //load the caption to be displayed

                    ArrayList<Object> alSearchCriteria =new ArrayList<Object>();
                    premiumInfoVO=null;
                    alSearchCriteria.add(lngPolicyGrpSeqID);    //policy_Group_seq_id
                    alSearchCriteria.add(strSwitchType);    // identifier of Enrollment or Endorsement flow
                    alSearchCriteria.add(getSubLinkCode(strActiveSubLink));    //identifier of current sublink
                    alPremiumInfo=memberManager.getPremiumList(alSearchCriteria);
                    if(alPremiumInfo!= null && alPremiumInfo.size()>0)
                    {
                        premiumInfoVO=(PremiumInfoVO)alPremiumInfo.get(0);
                        frmPremiumInfo.set("premiumInfo",(PremiumInfoVO[])alPremiumInfo.toArray(new PremiumInfoVO[0]));
                        frmPremiumInfo.set("policySubTypeId",premiumInfoVO.getPolicySubTypeID());
                        frmPremiumInfo.set("policySubTypeDesc",premiumInfoVO.getPolicySubTypeDesc());
                        frmPremiumInfo.set("floaterSumInsured",premiumInfoVO.getFloaterSumInsured()!=null?
                                premiumInfoVO.getFloaterSumInsured().toString() :"" );
                        frmPremiumInfo.set("floaterPremium",(String)TTKCommon.checkNull(premiumInfoVO.
                        		getFloatPremium()));
                        //Added as per KOC 1284 Change Request 
                        frmPremiumInfo.set("selectregion",(String)TTKCommon.checkNull(premiumInfoVO.getSelectregion()));
                        //Added as per KOC 1284 Change Request 
                       //if policySubType is Floater,clear the Total Family SumInsured and family premium
                        if(!premiumInfoVO.getPolicySubTypeID().equals("PFL"))
                        {
                            frmPremiumInfo.set("totalFlySumInsured",premiumInfoVO.getTotalFlySumInsured()!=null?
                                    premiumInfoVO.getTotalFlySumInsured().toString() :"" );
                            frmPremiumInfo.set("totalFlyPremium",(String)TTKCommon.checkNull(premiumInfoVO.
                            		getTotalFamilyPremium()));
                        }//end of if(!premiumInfoVO.getPolicySubTypeID().equals("PFL"))
                        else
                        {
                            frmPremiumInfo.set("totalFlySumInsured",null);
                            frmPremiumInfo.set("totalFlyPremium",null);
                        }//end of else
                        frmPremiumInfo.set("policySubTypeDesc",premiumInfoVO.getPolicySubTypeDesc());
                        frmPremiumInfo.set("productSeqID",premiumInfoVO.getProductSeqID());
                        //display the message based on premium amount saved or deletion of the premium amount
                        if(!strClearPremiumInfoYN.equals("Y"))
                        {
                            request.setAttribute("updated","message.savedSuccessfully");
                        }//end of if(!strClearPremiumInfoYN.equals("Y"))
                        else
                        {
                            request.setAttribute("updated","message.premiumclearedsuccessfully");
                        }//end of else
                    }//end of if(alPremiumInfo!= null && alPremiumInfo.size()>0)
                }//end of if(iCount>0)
            }//end of if(alPremiumInfo!=null && alPremiumInfo.size()>0)
            MemberVO memberVO1=(MemberVO) request.getSession().getAttribute("memberVO");
            if(memberVO1!=null)
			{
            	frmPremiumInfo.set("capitationYN",memberVO1.getCapitaionFlag().trim());
			}
			else
			{
				
				frmPremiumInfo.set("capitationYN","1");
			}
            frmPremiumInfo.set("activeLink",strActiveSubLink);
            return this.getForward(getForwardPath(strActiveSubLink),mapping,request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"premiuminfo"));
        }//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


    /**
     * This method is used to View the Bonus details of member/Family
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewBonusInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the doViewBonusInfo of PremiumInfoAction");
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            DynaActionForm frmPremiumInfo=(DynaActionForm)form;     //get the instance of formbean

            //declaration of the local variables
            Long lngPolicyGrpSeqID=null;
            lngPolicyGrpSeqID=(Long)frmPremiumInfo.get("policyGroupSeqID");
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            ArrayList<Object> alPremiumInfo=new ArrayList<Object>();
            PremiumInfoVO premiumInfoVO=null;
            String[] strMemberName=request.getParameterValues("name");
            String[] strMemberSeqIds=request.getParameterValues("memberSeqID");
            String[] strMemberPolicyTypeID=request.getParameterValues("memberPolicyTypeID");
            String[] strTotalSumInsured=request.getParameterValues("totalSumInsured");
          /*  String[] strTotalPremium=request.getParameterValues("premiumPaid");
            String[] strCalcPremium=request.getParameterValues("calcPremium");*/
            String[] strnetPremium=request.getParameterValues("netPremium");
            
            String[] strProRataPremium=request.getParameterValues("proRataPremium");
            
            
            String[] strCancelYN=request.getParameterValues("cancelYN");

            
            
            //Temporarily store all the information in premium screen before going to bonus list screen
            //for calculating sum insured for member or for family, and store it in form bean again.
            if(strMemberSeqIds!=null && strMemberSeqIds.length>0)
            {
                //loop througth the no of members and prepare the ArrayList of premiuminfoVO's
                for(int iCount=0;iCount<strMemberSeqIds.length;iCount++)
                {
                	
                	 premiumInfoVO=new PremiumInfoVO();
                     premiumInfoVO.setMemberSeqID(TTKCommon.checkNull(strMemberSeqIds[iCount]).equals("")?
                             null:new Long(strMemberSeqIds[iCount]));
                     premiumInfoVO.setCancelYN(TTKCommon.checkNull(strCancelYN[iCount]));
                     premiumInfoVO.setName(TTKCommon.checkNull(strMemberName[iCount]));
                     premiumInfoVO.setMemberPolicyTypeID(TTKCommon.checkNull(strMemberPolicyTypeID[iCount]));
                     premiumInfoVO.setTotalSumInsured(TTKCommon.getBigDecimal(strTotalSumInsured[iCount]));
                   /*  premiumInfoVO.setPremiumPaid(TTKCommon.checkNull(strTotalPremium[iCount]));
                     premiumInfoVO.setCalcPremium(TTKCommon.getBigDecimal(strCalcPremium[iCount]));*/
                     premiumInfoVO.setNetPremium(TTKCommon.getBigDecimal(strnetPremium[iCount]));
                     premiumInfoVO.setProRataPremium(TTKCommon.getBigDecimal(strProRataPremium[iCount]));
                	
                	
                  /*  premiumInfoVO=new PremiumInfoVO();
                    premiumInfoVO.setMemberSeqID(TTKCommon.checkNull(strMemberSeqIds[iCount]).equals("")?
                            null:new Long(strMemberSeqIds[iCount]));
                    premiumInfoVO.setCancelYN(TTKCommon.checkNull(strCancelYN[iCount]));
                    premiumInfoVO.setName(TTKCommon.checkNull(strMemberName[iCount]));
                    premiumInfoVO.setMemberPolicyTypeID(TTKCommon.checkNull(strMemberPolicyTypeID[iCount]));
                    
                    
                    System.out.println("strTotalSumInsured::::"+strTotalSumInsured[iCount]);
                    System.out.println("strTotalSumInsured::::"+strProRataPremium[iCount]);
                    
                    premiumInfoVO.setTotalSumInsured(TTKCommon.getBigDecimal(strTotalSumInsured[iCount]));
                    premiumInfoVO.setPremiumPaid(TTKCommon.checkNull(strTotalPremium[iCount]));
                    premiumInfoVO.setCalcPremium(TTKCommon.getBigDecimal(strCalcPremium[iCount]));
                    premiumInfoVO.setNetPremium(TTKCommon.getBigDecimal(strnetPremium[iCount]));
                    premiumInfoVO.setProRataPremium(TTKCommon.getBigDecimal(strProRataPremium[iCount]));
                    
                    
                    */
                    
                    if(iCount==0)   //in the first VO of ArrayList setPolicyGroupSeqId,floater and familySum Insured,floaterpremium and familyPremium
                    {
                        premiumInfoVO.setPolicyGroupSeqID(lngPolicyGrpSeqID);
                        premiumInfoVO.setFloaterSumInsured(TTKCommon.getBigDecimal((String)frmPremiumInfo.
                        		get("floaterSumInsured")));
                        premiumInfoVO.setTotalFlySumInsured(TTKCommon.getBigDecimal((String)frmPremiumInfo.
                        		get("totalFlySumInsured")));
                        premiumInfoVO.setFloatPremium((String)frmPremiumInfo.get("floaterPremium"));
                        premiumInfoVO.setTotalFamilyPremium((String)frmPremiumInfo.get("totalFlyPremium"));
                    }//end of if(iCount==0)
                    alPremiumInfo.add(premiumInfoVO);
                }//end of for(int iCount=0;iCount<strMemberSeqIds.length;iCount++)
            }//end of if(strMemberSeqIds!=null && strMemberSeqIds.length>0)
            frmPremiumInfo.set("premiumInfo",(PremiumInfoVO[])alPremiumInfo.toArray(new PremiumInfoVO[0]));

            if(!TTKCommon.checkNull(request.getParameter("selectedmemberSeqID")).equals(""))
            {
                frmPremiumInfo.set("selMemberSeqId",Long.parseLong(request.getParameter("selectedmemberSeqID")));
            }//end of if(!TTKCommon.checkNull(request.getParameter("selectedmemberSeqID")).equals(""))
            else
            {
                frmPremiumInfo.set("selMemberSeqId",null);
            }//end of else

            if(!TTKCommon.checkNull(request.getParameter("selectedmemberName")).equals(""))
            {
                frmPremiumInfo.set("selMemberName",(String)request.getParameter("selectedmemberName"));
            }//end of if(!TTKCommon.checkNull(request.getParameter("selectedmemberName")).equals(""))
            else
            {
                frmPremiumInfo.set("selMemberName",null);
            }//end of else
            frmPremiumInfo.set("activeLink",strActiveSubLink);
            return mapping.findForward(strBonusInfo);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"premiuminfo"));
        }//end of catch(Exception exp)
    }//end of doSubmitPremiumInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


    /**
     * This method is used to navigate back to Premium Info screen from the bonus screen
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
        try
        {
        	log.debug("Inside the doClose of PremiumInfoAction");
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            //DynaActionForm frmPremiumInfo=(DynaActionForm)form;
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            //frmPremiumInfo.set("frmChanged","changed");
            return this.getForward(getForwardPath(strActiveSubLink),mapping,request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"premiuminfo"));
        }//end of catch(Exception exp)
    }//end of doSubmitPremiumInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * Returns the MemberManager session object for invoking methods on it.
     * @return MemberManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private MemberManager getMemberManager() throws TTKException
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
            throw new TTKException(exp, "member");
        }//end of catch
        return memberManager;
    }//end getMemberManager()

    /**
     * This method returns the forward path for next view based on the Flow
     *
     * @param strActiveSubLink String current sublink
     * @return strForwardPath String forward path for the next view
     */
    private String getForwardPath(String strActiveSubLink)
    {
        String strForwardPath=null;

        if(strActiveSubLink.equals(strIndPolicy))
        {
            strForwardPath=strIndPremiumInfo;
        }//end of if(strActiveSubLink.equals(strIndividualPolicy))
        else if(strActiveSubLink.equals(strIndGrpPolicy))
        {
            strForwardPath=strGrpPremiumInfo;
        }//end of if(strActiveSubLink.equals(strIndPolicyasGroup))
        else if(strActiveSubLink.equals(strCorporatePolicy))
        {
            strForwardPath=strCorpPremiumInfo;
        } //end of if(strActiveSubLink.equals(strCorporatePolicy))
        else if(strActiveSubLink.equals(strNonCorporatePolicy))
        {
            strForwardPath=strNonCorpPremiumInfo;
        }//end of if(strActiveSubLink.equals(strNonCorporatePolicy))
        return strForwardPath;
    }//end of getForwardPath(String strActiveSubLink)

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
}//end of PremiumInfoAction.java
