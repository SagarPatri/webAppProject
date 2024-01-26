/**
 * @ (#) PolicyDetailsAction.java Feb 3, 2006
 * Project       : TTK HealthCare Services
 * File          : PolicyDetailsAction.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 3, 2006
 *
 * @author       : Srikanth H M
 * Modified by   : Arun K N
 * Modified date : May 8, 2007
 * Reason        : Conversion to dispatch action
 */

package com.ttk.action.enrollment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.icu.text.SimpleDateFormat;
import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.business.administration.RuleManager;
import com.ttk.business.empanelment.BrokerManager;
import com.ttk.business.enrollment.PolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.EventVO;
import com.ttk.dto.administration.MOUDocumentVO;
import com.ttk.dto.administration.ProductVO;
import com.ttk.dto.administration.WorkflowVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.enrollment.MemberAddressVO;
import com.ttk.dto.enrollment.PolicyDetailVO;
import com.ttk.dto.enrollment.PolicyVO;
import com.ttk.dto.maintenance.EnrollBufferVO;
import com.ttk.dto.preauth.ProviderDetailsVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is reusable one, used to add/edit individual policy,individual policy as group,
 * corporate policies and non corporate policies in enrollment and endorsement flow.
 * This class is also used to list corporate /NonCorporate Group for the policy,and promoting the policy to the next level.
 */

public class PolicyDetailsAction extends TTKAction {
    private static Logger log = Logger.getLogger( PolicyDetailsAction.class );
    //	declrations of modes
    private static final String strPolicyDetails="PolicyDetails";

    //declare forward paths
    private static final String strIndPolicyDetails="indpolicydetails";
    private static final String strGrpPolicyDetails="grppolicydetails";
    private static final String strCorpPolicyDetails="corppolicydetails";
    private static final String strNonCorpPolicyDetails="noncorppolicydetails";
    private static final String strAddCoInsComp = "addCoInsComp";

    //  declaration of other constants used in this class
    private static final String strIndPolicyType="IP";
    private static final String strGrpPolicyType="IG";
    private static final String strCorpPolicyType="CP";
    private static final String strNonCorpPolicyType="NC";
    private static final String strIndPolicy="Individual Policy";
    private static final String strIndGrpPolicy="Ind. Policy as Group";
    private static final String strCorporatePolicy="Corporate Policy";
    private static final String strNonCorporatePolicy="Non-Corporate Policy";

    private static final String strEnrollment="ENM";
    private static final String strEndorsement="END";
    private static final String strcoInsCompDetails="coInsCompDetails";
    //private static final String strOfficeList="ChangeOfficeList";


    /**
     * This method is used to view the policy details of selected policy
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewPolicyDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
            log.debug("Inside doViewPolicyDetails of PolicyDetailsAction");
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            DynaActionForm frmPolicyDetails= (DynaActionForm)form;
            StringBuffer sbfCaption=buildCaption(request,strActiveSubLink,strSwitchType);
            String strForwardPath=getForwardPath(strActiveSubLink);

            ArrayList<Object> alPolicy = new ArrayList<Object>();
            PolicyDetailVO policyDetailVO=null;
            
            //get the session bean from the bean pool for each excecuting thread
            PolicyManager policyObject=this.getPolicyManagerObject();

            if(WebBoardHelper.checkWebBoardId(request)==null)
            {
                TTKException expTTK = new TTKException();
                if(strSwitchType.equals(strEnrollment))//when no policy/endorsement is selected in web-board.
                {
                    expTTK.setMessage("error.enrollment.required");
                }//end of if(strSwitchType.equals(strEnrollment))
                else
                {
                	expTTK.setMessage("error.endorsement.required");
                }//end of else
                throw expTTK;
            }//end of if(WebBoardHelper.checkWebBoardId(request)==null)

            if(strSwitchType.equals(strEndorsement))
            {
                if(WebBoardHelper.getPolicySeqId(request)==null)
                {
                	TTKException expTTK = new TTKException();
                    expTTK.setMessage("error.enrollment.policyrequired");
                    throw expTTK;
                }//end of if(WebBoardHelper.getPolicySeqId(request)==null)
                if(!WebBoardHelper.getPolicyYN(request).equals("Y"))
                {
                    TTKException expTTK = new TTKException();

                    //when no policy is attached to this endorsement.
                    if(WebBoardHelper.getPolicyYN(request).equals("N"))
                    {
                    	expTTK.setMessage("error.endorsement.policydetails.nopolicy");
                    }//end of if(WebBoardHelper.getPolicyYN(request).equals("N"))

                    //when policy is in other work flow.
                    if(WebBoardHelper.getPolicyYN(request).equals("O"))
                    {
                        expTTK.setMessage("error.endorsement.policydetails.anotherworflow" );
                    }//end of if(WebBoardHelper.getPolicyYN(request).equals("O"))
                    throw expTTK;
                }//end of if(!WebBoardHelper.getPolicyYN(request).equals("Y"))
    			
            }//end of if(strSwitchType.equals("END"))

            if(strSwitchType.equals(strEnrollment))
            {
                alPolicy.add(WebBoardHelper.getPolicySeqId(request));
                
            }//end of if(strSwitchType.equals("ENM"))
            else
            {
                alPolicy.add(WebBoardHelper.getEndorsementSeqId(request));
                request.getSession().setAttribute("endorsementSeqId", WebBoardHelper.getEndorsementSeqId(request));
            }//end of else if(strSwitchType.equals("ENM"))
            alPolicy.add(strSwitchType);//Enrollment or Endorsement
            alPolicy.add(getPolicyType(strActiveSubLink));
             request.getSession().setAttribute("strSwitchType", strSwitchType);
            policyDetailVO= policyObject.getPolicy(alPolicy);
        	/*int countFlag = 0;
            if("END".equals(strSwitchType)){
            	
            	   String updateFlag="";
                   String endFlag = request.getParameter("endorsmentFlag");
                   if(endFlag==null||endFlag==""){
                   	updateFlag="POL";
                   }
               	Long policySeqID =WebBoardHelper.getPolicySeqId(request);
               	   DynaActionForm frmEndorsementDetails = (DynaActionForm)
       						request.getSession().getAttribute("frmEndorsementDetails");
       	            String effectiveDate =TTKCommon.checkNull(frmEndorsementDetails.getString("effectiveDate")) ;
       	            
       	              policyDetailVO.setEndorsmentFlag(updateFlag);
       	        	  policyDetailVO.setPolicySeqID(policySeqID);
       	        	  policyDetailVO.setEndorsmentEeffectiveDate(effectiveDate);
               	
               	 countFlag = policyObject.getPreauthClaimsCount(policyDetailVO);
            	 policyDetailVO.setPreauthClaimCount(countFlag);
            }*/
            
         
            
            frmPolicyDetails.initialize(mapping);
            
            
            frmPolicyDetails= (DynaActionForm)FormUtils.setFormValues("frmPolicyDetails",policyDetailVO,this,mapping, request);
            frmPolicyDetails.set("frmPolicyAddress",FormUtils.setFormValues("frmPolicyAddress",policyDetailVO.getAddress(),this,mapping,request));
            sbfCaption.append(" - Edit");
            frmPolicyDetails.set("caption",sbfCaption.toString());
            frmPolicyDetails.set("display",null); //when web board is empty we are setting the attribute to display error message.
            frmPolicyDetails.set("policyStatusDesc",TTKCommon.getCacheDescription(policyDetailVO.getPolicyStatusID(),"policyStatus"));
            //load the products associated to the insurance company
            frmPolicyDetails.set("alProductCode",policyDetailVO.getProductList());
            frmPolicyDetails.set("eventCompleteYN",policyDetailVO.getEventCompleteYN());
            //keep the frmPolicyDetails in session scope
            //AK
            ArrayList arAssociatedCorp = new ArrayList();
            BrokerManager brokerObject = (BrokerManager) this.getBrokerManagerObject();	
            //TableData tableDataBro=request.getSession().getAttribute("tableDataBro") != null ?(TableData)request.getSession().getAttribute("tableDataBro"): new TableData();
            TableData tableDataBro=null;
            if(request.getSession().getAttribute("tableDataBro") != null)
            	tableDataBro = (TableData)request.getSession().getAttribute("tableDataBro");
            else
            	tableDataBro = new TableData();
			tableDataBro.createTableInfo("BrokerDetailsListTable",new ArrayList<>());
			tableDataBro.setSearchData(alPolicy);
			tableDataBro.modifySearchData("search");
			arAssociatedCorp =  brokerObject.getBrokerDetailsAssocGrpList(tableDataBro.getSearchData());
			request.getSession().setAttribute("brokerCount", arAssociatedCorp.size());
			//System.out.println(request.getSession().getAttribute("brokerCount")+":  arAssociatedCorp : "+arAssociatedCorp.size());
			tableDataBro.setData(arAssociatedCorp, "search");	
			//System.out.println("============tableData======================="+tableDataBro.getData().size());
			request.getSession().setAttribute("tableDataBro", tableDataBro);
            //END AK
            request.getSession().setAttribute("frmPolicyDetails",frmPolicyDetails);
            
            /*Required data for softcopy upload.*/
            request.getSession().setAttribute("policy_num",policyDetailVO.getPolicySeqID());
            request.getSession().setAttribute("productTyp_num",policyDetailVO.getProductSeqID());
            request.getSession().setAttribute("insComp_num",policyDetailVO.getInsuranceSeqID());
            request.getSession().setAttribute("grpId_num", policyDetailVO.getGroupRegnSeqID());
            request.getSession().setAttribute("capitation_type", policyDetailVO.getCapitationflag());
            request.getSession().setAttribute("policy_category", policyDetailVO.getPolicyCategory());
            request.getSession().setAttribute("policyStatusSeqID", policyDetailVO.getPolicyStatusID());
            request.getSession().setAttribute("freshOrRenewalPolicyStatusFlag", policyDetailVO.getTPAStatusTypeID());
            request.getSession().setAttribute("portedYN", policyDetailVO.getPortedYN());
            request.getSession().setAttribute("productAuthority", policyDetailVO.getProductAuthority());
            request.getSession().setAttribute("officeCode", policyDetailVO.getOfficeCode());
            
            this.documentViewer(request,policyDetailVO);
            return this.getForward(strForwardPath, mapping, request);
        }// end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, strPolicyDetails));
        }//end of catch(Exception exp)
    }//end of doViewPolicyDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to get the details of the selected record from web-board.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
    	log.debug("Inside doChangeWebBoard of PolicyDetailsAction");
        //action is similar to View the Policy details of the new Web board item
        return doViewPolicyDetails(mapping,form,request,response);
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
        	log.debug("Inside doReset of PolicyDetailsAction");
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);

            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            DynaActionForm frmPolicyDetails= (DynaActionForm)form;
            StringBuffer sbfCaption=buildCaption(request,strActiveSubLink,strSwitchType);
            String strForwardPath=getForwardPath(strActiveSubLink);

            ArrayList<Object> alPolicy = new ArrayList<Object>();
            PolicyDetailVO policyDetailVO=null;
            MemberAddressVO memberAddressVO=null;

            //get the session bean from the bean pool for each excecuting thread
            PolicyManager policyObject=this.getPolicyManagerObject();

            if(frmPolicyDetails.get("policySeqID")!=null && !frmPolicyDetails.get("policySeqID").equals(""))
            {
                if(strSwitchType.equals(strEnrollment))
                {
                    alPolicy.add(WebBoardHelper.getPolicySeqId(request));
                }//end of if(strSwitchType.equals("ENM"))
                else
                {
                    alPolicy.add(WebBoardHelper.getEndorsementSeqId(request));
                }//end of else if(strSwitchType.equals("ENM"))
                alPolicy.add(strSwitchType);//Enrollment or Endorsement
                alPolicy.add(getPolicyType(strActiveSubLink));

                policyDetailVO= policyObject.getPolicy(alPolicy);
                sbfCaption.append(" - Edit");
            }//end of if(frmPolicyDetails.get("policySeqID")!=null && !frmPolicyDetails.get("policySeqID").equals(""))
            else
            {
                policyDetailVO=new PolicyDetailVO();
                memberAddressVO=new MemberAddressVO();
                policyDetailVO.setAddress(memberAddressVO);
                sbfCaption.append(" - Add");
            }//end of else

            frmPolicyDetails= (DynaActionForm)FormUtils.setFormValues("frmPolicyDetails",
            		policyDetailVO,this, mapping, request);

            frmPolicyDetails.set("frmPolicyAddress",FormUtils.setFormValues("frmPolicyAddress",
            		policyDetailVO.getAddress(),this,mapping,request));
            frmPolicyDetails.set("display",null);
            frmPolicyDetails.set("caption",sbfCaption.toString());
            frmPolicyDetails.set("policyStatusDesc",TTKCommon.getCacheDescription(policyDetailVO.
            		getPolicyStatusID(),"policyStatus"));

            //load the products associated to the insurance company
            frmPolicyDetails.set("alProductCode",policyDetailVO.getProductList());
            request.getSession().setAttribute("frmPolicyDetails",frmPolicyDetails);
            this.documentViewer(request,policyDetailVO);
            return this.getForward(strForwardPath, mapping, request);
        }// end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, strPolicyDetails));
        }//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
        	log.debug("Inside doSave of PolicyDetailsAction");
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);

            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            DynaActionForm frmPolicyDetails= (DynaActionForm)form;
            FormFile formFile=(FormFile)frmPolicyDetails.get("policyFileCopy");
            StringBuffer sbfCaption=buildCaption(request,strActiveSubLink,strSwitchType);
            String strForwardPath=getForwardPath(strActiveSubLink);

            if(formFile!=null&&formFile.getFileSize()>1){
            	String[] arr=formFile.getFileName().split("[.]");
            	 String fileType=arr[arr.length-1];
            	if(!"pdf".equalsIgnoreCase(fileType)){            		
                		request.setAttribute("fileError","Upload File Type Should Be PDF");
                		return this.getForward(strForwardPath, mapping, request);
            	}
            }
            
            ArrayList<Object> alPolicy = new ArrayList<Object>();
            PolicyDetailVO policyDetailVO=null;
            MemberAddressVO memberAddressVO=null;
            //RuleXMLHelper ruleXmlHelper=new RuleXMLHelper();
            //get the session bean from the bean pool for each excecuting thread
            PolicyManager policyObject=this.getPolicyManagerObject();

            //boolean blnUpdate = false;
            int iRuleDefined = 0;
            //get the policy detail values from the form
            if(request.getParameter("proposalFormYN")==null)// if proposalFormYN checkbox in not selected
                frmPolicyDetails.set("proposalFormYN","N");
            if(request.getParameter("condonedYN")==null)// if proposalFormYN checkbox in not selected
                frmPolicyDetails.set("condonedYN","N");
                     
            policyDetailVO = (PolicyDetailVO)FormUtils.getFormValues(frmPolicyDetails, this, mapping, request);
            // get the frmAddress that's nested in the submitted form
            ActionForm frmPolicyAddress = (ActionForm) frmPolicyDetails.get("frmPolicyAddress");
            memberAddressVO=(MemberAddressVO)FormUtils.getFormValues(frmPolicyAddress,"frmPolicyAddress",
                    this, mapping, request);
            policyDetailVO.setAddress(memberAddressVO);
            policyDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id

            if(strSwitchType.equals(strEndorsement))//Pass EndorsementSeqId in Endorsement Flow
                policyDetailVO.setEndorsementSeqID(WebBoardHelper.getEndorsementSeqId(request));
            byte[] data=null;           
            if(formFile!=null&&formFile.getFileSize()>1){
            	
            	data=formFile.getFileData();
            	String fileLocation=TTKPropertiesReader.getPropertyValue("path.policyfile");
            	File file=new File(fileLocation);
            	if(!file.exists())file.mkdir();
            	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy_MM_dd_HH_mm_SSS");
            	String arr[]=formFile.getFileName().split(".pdf");
            	String fileName=arr[0]+"_"+dateFormat.format(new Date())+".pdf";
            	FileOutputStream fileOutputStream=new FileOutputStream(fileLocation+fileName);
            	fileOutputStream.write(data);
            	fileOutputStream.close();
            	policyDetailVO.setPolicyFileName(fileName);            	
            }
            //calling savePolicy  through the DAO
            
          String strPYN=request.getParameter("portedYN");
          if("Y".equals(strPYN))policyDetailVO.setPortedYN("Y");
          else policyDetailVO.setPortedYN("N");
          	long iUpdate2=0;
            long iUpdate=policyObject.savePolicy(policyDetailVO,strSwitchType,getPolicyType(strActiveSubLink),data);
            System.out.println("scheme | iUpdate(1) = "+iUpdate);
            if(iUpdate >0)
            {
            	 iUpdate2=policyObject.savePolicy2(policyDetailVO,iUpdate);
            }
            // set the appropriate message
            System.out.println("scheme save | iUpdate(2) = "+iUpdate2);
            // Added for integrating Rule related validation as per Sreeraj advise : 27/02/2008
            if(iUpdate <0 || iUpdate2<0){
            	log.info("Errors are there..........");
				RuleManager ruleManager=this.getRuleManagerObject();
				//RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();
				ArrayList alValidationError=ruleManager.getValidationErrorList(iUpdate);
				
				//prepare Error messages
				//ArrayList alErrorMessage=ruleXMLHelper.prepareErrorMessage(alValidationError,request);
				request.setAttribute("BUSINESS_ERRORS",alValidationError);
				return this.getForward(strForwardPath, mapping, request);
            }//end of if(iUpdate <0)
            // End of Addition
            if(iUpdate > 0 && iUpdate2 > 0 )
            {
                if(policyDetailVO.getPolicySeqID()!=null)
                {
                	//blnUpdate=true;
                	/*iRuleDefined = policyObject.checkRuleDefined(policyDetailVO.getPolicySeqID());
                	if(iRuleDefined ==0){
                		request.setAttribute("updated","message.savedSuccessfullyButRuleNotDefined");
                	}//end of if(iRuleDefined ==0)
                	else{
                		request.setAttribute("updated","message.savedSuccessfully");
                	}*/ //end of else
                	request.setAttribute("updated","message.savedSuccessfully");
                    if(strSwitchType.equals("END"))
                    {
                        request.setAttribute("cacheId", String.valueOf(policyDetailVO.getEndorsementSeqID()));
                    }//end of if(strSwitchType.equals("END"))
                    else
                    {
                        request.setAttribute("cacheId", String.valueOf(policyDetailVO.getPolicySeqID()));
                    }//end of else
                    request.setAttribute("cacheDesc", policyDetailVO.getPolicyNbr());
                    //finally modify the web board details, if the Policy Number is changed
                    WebBoardHelper.modifyWebBoardId(request);

                }//end of if(policyDetailVO.getPolicySeqID()!=null)
                else //end of if(policyDetailVO.getPolicySeqID()!=null)
                {
                	/*iRuleDefined = policyObject.checkRuleDefined(iUpdate);
                	if(iRuleDefined ==0){
                		request.setAttribute("updated","message.addedSuccessfullyButRuleNotDefined");
                	}//end of if(iRuleDefined ==0)
                	else{
                		request.setAttribute("updated","message.addedSuccessfully");
                	}//end of else
               */  
                	request.setAttribute("updated","message.addedSuccessfully");
                	policyDetailVO.setPolicySeqID(iUpdate);
                    this.addToWebBoard((PolicyVO)policyDetailVO, request,strSwitchType);
                }//end of else  if(policyDetailVO.getPolicySeqID()!=null)
                if(strSwitchType.equals("ENM"))
                {
                    alPolicy.add(WebBoardHelper.getPolicySeqId(request));
                }//end of if(strSwitchType.equals("ENM"))
                else
                {
                    alPolicy.add(WebBoardHelper.getEndorsementSeqId(request));
                }//end of else if(strSwitchType.equals("ENM"))
                alPolicy.add(strSwitchType);//Enrollment or Endorsement
                alPolicy.add(getPolicyType(strActiveSubLink));
                //make requery
                policyDetailVO= policyObject.getPolicy(alPolicy);
                frmPolicyDetails= (DynaActionForm)FormUtils.setFormValues("frmPolicyDetails", policyDetailVO,
                        this,mapping, request);
                frmPolicyDetails.set("frmPolicyAddress",FormUtils.setFormValues("frmPolicyAddress",
                        policyDetailVO.getAddress(),this,mapping,request));
                frmPolicyDetails.set("display",null);//when web board is empty we are setting the attribute to dispaly error message.
                sbfCaption.append(" - Edit");
                frmPolicyDetails.set("policyStatusDesc",TTKCommon.getCacheDescription(policyDetailVO.
                		getPolicyStatusID(),"policyStatus"));
                frmPolicyDetails.set("caption",sbfCaption.toString());
                //load the products associated to the insurance company
               
                frmPolicyDetails.set("alProductCode",policyDetailVO.getProductList());
                frmPolicyDetails.set("eventSeqID", policyDetailVO.getEventSeqID().toString());
                frmPolicyDetails.set("portedYN", policyDetailVO.getPortedYN());
                request.getSession().setAttribute("portedYN",policyDetailVO.getPortedYN());
                request.getSession().setAttribute("frmPolicyDetails",frmPolicyDetails);
                request.getSession().setAttribute("productAuthority", policyDetailVO.getProductAuthority());
                request.getSession().setAttribute("officeCode", policyDetailVO.getOfficeCode());
                this.documentViewer(request,policyDetailVO);
	
//                Document policyRule = policyObject.validateEnrollment("P",policyDetailVO.getPolicySeqID());
//                if(policyRule!=null)
//                {
//                    //Merge the policy rule with Base rule to add display nodes
//                    Document baseRuleDoc=TTKCommon.getDocument("MasterBaseRules.xml");
//                    policyRule=ruleXmlHelper.mergeDisplayNodes(policyRule,baseRuleDoc);
//
//                    ValidationRuleManager validationRuleManager = this.getValidationRuleManagerObject();
//                    ArrayList alErrors = validationRuleManager.executePolicyValidation(policyRule);
//                    //clear the previous error stored in database
//                    validationRuleManager.clearRuleErrors(policyDetailVO.getPolicyNbr(),"P");
//                    if(alErrors!=null && alErrors.size()>0)
//                    {
//                        //Save the error
//                        validationRuleManager.saveRuleErrors(alErrors);
//                        //Update status as Rule failed
//                        // Commented Code for validateEnrollment ad per Arun's comment on 29/02/2008
//                        //validationRuleManager.updateValidationStatus("P",policyDetailVO.getPolicySeqID(),"F");
//                        // End of Commenting
//                        if(blnUpdate)
//                            request.setAttribute("updated","message.savedSuccessfullyWithValidationError");
//                        else
//                            request.setAttribute("updated","message.addedSuccessfullyWithValidationError");
//                    }//end of if(alErrors!=null && alErrors.size()>0)
//                }//end of if(policyRule!=null)
//                else
//                {
                    /*if(blnUpdate)
                        request.setAttribute("updated","message.savedSuccessfullyButRuleNotDefined");
                    else
                        request.setAttribute("updated","message.addedSuccessfullyButRuleNotDefined");*/
//                }//end of else

            }//end of if(iUpdate > 0)
            request.getSession().setAttribute("policyStatusSeqID", policyDetailVO.getPolicyStatusID());
            request.getSession().setAttribute("freshOrRenewalPolicyStatusFlag", policyDetailVO.getTPAStatusTypeID());
            return this.getForward(strForwardPath, mapping, request);
        }// end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, strPolicyDetails));
        }//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to do Review/Promote the Policy to next level
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doReview(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside doReview of PolicyDetailsAction");
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);

            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            DynaActionForm frmPolicyDetails= (DynaActionForm)form;
            StringBuffer sbfCaption=buildCaption(request,strActiveSubLink,strSwitchType);
            String strForwardPath=getForwardPath(strActiveSubLink);

            ArrayList<Object> alPolicy = new ArrayList<Object>();
            PolicyDetailVO policyDetailVO=null;

            //get the session bean from the bean pool for each excecuting thread
            PolicyManager policyObject=this.getPolicyManagerObject();


            PolicyDetailVO policyDetailVO1 = null;
            policyDetailVO = (PolicyDetailVO)FormUtils.getFormValues(frmPolicyDetails, this, mapping, request);
            policyDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id

            if(strSwitchType.equals(strEndorsement))
            {
                policyDetailVO.setEndorsementSeqID(WebBoardHelper.getEndorsementSeqId(request));
            }//end of if(strSwitchType.equals("END"))
            policyDetailVO=policyObject.saveReview(policyDetailVO,strSwitchType,getPolicyType(strActiveSubLink));

            frmPolicyDetails.set("eventSeqID",policyDetailVO.getEventSeqID().toString());
            frmPolicyDetails.set("eventName",policyDetailVO.getEventName());
            frmPolicyDetails.set("reviewCount",policyDetailVO.getReviewCount().toString());
            frmPolicyDetails.set("requiredReviewCnt",policyDetailVO.getRequiredReviewCnt().toString());
            frmPolicyDetails.set("review",policyDetailVO.getReview());

            if(strSwitchType.equals(strEnrollment))
            {
                alPolicy.add(WebBoardHelper.getPolicySeqId(request));
            }//end of if(strSwitchType.equals("ENM"))
            else
            {
                alPolicy.add(WebBoardHelper.getEndorsementSeqId(request));
            }//end of else if(strSwitchType.equals("ENM"))
            alPolicy.add(strSwitchType);//Enrollment or Endorsement
            alPolicy.add(getPolicyType(strActiveSubLink));

            policyDetailVO1= policyObject.getPolicy(alPolicy);
            this.addToWebBoard((PolicyVO)policyDetailVO1, request,strSwitchType);

            //check whether user is having the permession for next Event.
            boolean blnReviewPermession=checkReviewPermession(policyDetailVO.getEventSeqID(),strSwitchType,request);
            if(!blnReviewPermession)
            {
                //delete the Policies from the web board if any
                if(strSwitchType.equals(strEnrollment))
                {
                    request.setAttribute("cacheId","|"+WebBoardHelper.getPolicySeqId(request)+"|");
                }//end of if(strSwitchType.equals("ENM"))
                else if(strSwitchType.equals(strEndorsement))
                {
                    request.setAttribute("cacheId","|"+WebBoardHelper.getEndorsementSeqId(request)+"|");
                }//end of else if(strSwitchType.equals("END"))
                WebBoardHelper.deleteWebBoardId(request);
                request.setAttribute("webboardinvoked","true");

                //After deleting if web board is null display the error message.
                if(WebBoardHelper.checkWebBoardId(request)==null)
                {
                    TTKException expTTK = new TTKException();
                    frmPolicyDetails.set("display","display");//this attribute is used in JSP to show the error message.
                    if(strSwitchType.equals("ENM"))
                    {
                        expTTK.setMessage("error.enrollment.required");
                    }//end of if(strSwitchType.equals("ENM"))
                    else
                    {
                        expTTK.setMessage("error.endorsement.required");
                    }//end of else
                    throw expTTK;
                }//end of if(WebBoardHelper.checkWebBoardId(request)==null)
                else
                {
                    if(strSwitchType.equals(strEnrollment))
                    {
                        alPolicy.add(WebBoardHelper.getPolicySeqId(request));
                    }//end of if(strSwitchType.equals("ENM"))
                    else
                    {
                        alPolicy.add(WebBoardHelper.getEndorsementSeqId(request));
                    }//end of else if(strSwitchType.equals("ENM"))
                    alPolicy.add(strSwitchType);
                    alPolicy.add(getPolicyType(strActiveSubLink));

                    policyDetailVO= policyObject.getPolicy(alPolicy);
                    frmPolicyDetails.initialize(mapping);
                    frmPolicyDetails= (DynaActionForm)FormUtils.setFormValues("frmPolicyDetails", policyDetailVO,
                            this,mapping,request);
                    frmPolicyDetails.set("frmPolicyAddress",FormUtils.setFormValues("frmPolicyAddress",
                            policyDetailVO.getAddress(),this,mapping,request));
                    frmPolicyDetails.set("caption",sbfCaption.toString());
                    frmPolicyDetails.set("display",null);//when web board is empty we are setting the attribute to dispaly error message.
                    frmPolicyDetails.set("policyStatusDesc",TTKCommon.getCacheDescription(policyDetailVO.
                    		getPolicyStatusID(),"policyStatus"));
                    //keep the frmPolicyDetails in session scope
                    request.getSession().setAttribute("frmPolicyDetails",frmPolicyDetails);
                    //load the products associated to the insurance company
                    frmPolicyDetails.set("alProductCode",policyDetailVO.getProductList());
                    this.documentViewer(request,policyDetailVO);
                }//end of else
            }//end of if(!blnReviewPermession)
            return this.getForward(strForwardPath, mapping, request);
        }// end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, strPolicyDetails));
        }//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
        try
        {
        	log.debug("Inside doClose of PolicyDetailsAction");
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            String strForwardPath=getForwardPath(strActiveSubLink);

            return this.getForward(strForwardPath, mapping, request);
        }// end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "endorsementdetails"));
        }//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


    /**
     * This method is will update the select Corporate /NonCorporate Group for the policy
     * moves it the next screen.
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
        try
        {
        	DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            return mapping.findForward("corporatelist");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "endorsementdetails"));
        }//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is will forward to Sum Insured Information Screen
     * moves it the next screen.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws TTKException if any error occurs
     */
    public ActionForward doShowSIInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws TTKException
        {
    	  try
    	  {
    		  setLinks(request);
    		  String strForward ="";
    		  String strActiveSubLink=TTKCommon.getActiveSubLink(request);
    		  DynaActionForm frmPolicyDetails=(DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");
    		  PolicyManager policyObject=this.getPolicyManagerObject();
    		  PolicyDetailVO policyDetailVO=null;
    		  Long lngPolicySeqID= Long.parseLong(frmPolicyDetails.getString("policySeqID"));
    		  policyDetailVO= policyObject.getPolicySIInfo(lngPolicySeqID);
    		  if(policyDetailVO.getAddedSIAmt()!= null)
    		  {
    		    frmPolicyDetails.set("addedSIAmt", TTKCommon.checkNull(policyDetailVO.getAddedSIAmt().toString()));
    		  }//end of if(policyDetailVO.getAddedSIAmt()!= null)
    		  if(policyDetailVO.getNoofFamiliesAdded()!= null)
    		  {
    		    frmPolicyDetails.set("noofFamiliesAdded", TTKCommon.checkNull(policyDetailVO.getNoofFamiliesAdded().toString()));
    		  }//end of if(policyDetailVO.getNoofFamiliesAdded()!= null)
    		  if(policyDetailVO.getAddedSIWording()!= null)
    		  {
    		    frmPolicyDetails.set("addedSIWording",policyDetailVO.getAddedSIWording());
    		  }//end of if(policyDetailVO.getAddedSIWording()!= null)
    		  if(policyDetailVO.getActiveMembers()!= null)
    		  {
    		    frmPolicyDetails.set("activeMembers", TTKCommon.checkNull(policyDetailVO.getActiveMembers().toString()));
    		  }//end of if(policyDetailVO.getActiveMembers()!= null)
    		  if(policyDetailVO.getActiveMembers()!= null)
    		  {
    		    frmPolicyDetails.set("cancelMembers", TTKCommon.checkNull(policyDetailVO.getCancelMembers().toString()));
    		  }//end of if(policyDetailVO.getActiveMembers()!= null)
  			 if(strActiveSubLink.equals(strCorporatePolicy))
  			 {
  				strForward="showsiinfocorp";
  			 }//end of if(strActiveSubLink.equals(strCorporatePolicy))
  			 else if(strActiveSubLink.equals(strNonCorporatePolicy))
  			 {
  				strForward="showsiinfononcorp";
  			 }//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
    		  return this.getForward(strForward, mapping, request);
    	  }//end of try
    	  catch(TTKException expTTK)
          {
              return this.processExceptions(request, mapping, expTTK);
          }//end of catch(TTKException expTTK)
          catch(Exception exp)
          {
              return this.processExceptions(request, mapping, new TTKException(exp, "endorsementdetails"));
          }//end of catch(Exception exp)
        }//end of doShowSIInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
          // HttpServletResponse response)
    
    /**
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSIClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForward ="";
			if(strActiveSubLink.equals(strCorporatePolicy))
			{
				strForward="corpclose";
			}//end of if(strActiveSubLink.equals(strCorporatePolicy))
			else if(strActiveSubLink.equals(strNonCorporatePolicy))
			{
				strForward="noncorpclose";
			}//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
			 return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"endorsementdetails"));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
 	
	public ActionForward doClosePolicyDocs(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside doClosePolicyDocs of PolicyDetailsAction");
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            return this.getForward("corppolicydetails", mapping, request);
        }// end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "endorsementdetails"));
        }//end of catch(Exception exp)
    }//end of doClosePolicyDocs(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward policyModifyHistory(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside policyModifyHistory of PolicyDetailsAction");
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            
            DynaActionForm frmPolicyDetails=(DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");
            PolicyManager policyObject=this.getPolicyManagerObject();
            ArrayList<String[]>	policyHistoryDtls=	 policyObject.getPolicyModifyHistory(frmPolicyDetails.getString("policySeqID"));
 		      request.setAttribute("policyHistoryDtls", policyHistoryDtls);
            return mapping.findForward("policyHistoryDetails");
        }// end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "endorsementdetails"));
        }//end of catch(Exception exp)
    }//end of policyModifyHistory(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
    /**
     * Returns the PolicyManager session object for invoking methods on it.
     * @return policyManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private PolicyManager getPolicyManagerObject() throws TTKException
    {
        PolicyManager policyManager = null;
        try
        {
            if(policyManager == null)
            {
                InitialContext ctx = new InitialContext();
                policyManager = (PolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/PolicyManagerBean!com.ttk.business.enrollment.PolicyManager");
            }//end if(policyManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "policydetail");
        }//end of catch
        return policyManager;
    }//end getHospitalManagerObject()

    /**
     * Methods checks whether user is having permession for the next Event.
     * @param lngEventSeqId Long Event_Seq_Id.
     * @param strSwitchType String SwitchType.
     * @param request HttpServletRequest object.
     * @return blnPermession boolean.
     */
    private boolean checkReviewPermession(Long lngEventSeqId,String strSwitchType,HttpServletRequest request)
    {
        boolean blnPermession=false;
        WorkflowVO workFlowVO=null;
        ArrayList alEventId=null;
        //get the HashMap from UserSecurityProfile
        HashMap hmWorkFlow=((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).
        					getWorkFlowMap();

        if(strSwitchType.equals("ENM"))
        {
            workFlowVO=(WorkflowVO)hmWorkFlow.get(new Long(1));
        }//end of  if(strSwitchType.equals("ENM"))
        else if(strSwitchType.equals("END"))
        {
            workFlowVO=(WorkflowVO)hmWorkFlow.get(new Long(2));
        }//end of else if(strSwitchType.equals("END"))
        //get the arrayList which is having event information of the particular user.
        if(workFlowVO!=null)
        {
            alEventId=workFlowVO.getEventVO();
        }//end of if(workFlowVO!=null)
        //compare the current policy EventSeqId with the User permession.
        if(alEventId!=null)
        {
            for(int i=0;i<alEventId.size();i++)
            {
                if(lngEventSeqId==((EventVO)alEventId.get(i)).getEventSeqID())
                {
                    blnPermession=true;
                    break;
                }//end of if(lngEventSeqId==((EventVO)alEventId.get(i)).getEventSeqID())
            }//end of for(int i=0;i<alEventId.size();i++)
        }//end of if(alEventId!=null)
        return blnPermession;
    }
    /**
     * Adds the selected item to the web board and makes it as the selected item in the web board
     * @param  policyVO InsuranceVO object which contains the information of the Insurance Office
     * @param request HttpServletRequest
     * @param strSwitchType String  identifier of enrollment or endorsement flow
     * @throws TTKException if any runtime exception occures
     */
    private void addToWebBoard(PolicyVO policyVO, HttpServletRequest request,String strSwitchType)
    	throws TTKException
    {
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        ArrayList<Object> alCacheObject = new ArrayList<Object>();
        CacheObject cacheObject = new CacheObject();
        cacheObject.setCacheId(this.prepareWebBoardId(policyVO,strSwitchType)); //set the cacheID
        if(strSwitchType.equals(strEnrollment))
        {
            cacheObject.setCacheDesc(policyVO.getPolicyNbr());
        }//end of if(strSwitchType.equals(strEnrollment))
        else if(strSwitchType.equals(strEndorsement))
        {
            cacheObject.setCacheDesc(policyVO.getEndorsementNbr());
        }//end of else if(strSwitchType.equals(strEndorsement))

        alCacheObject.add(cacheObject);
        //if the object(s) are added to the web board, set the current web board id
        toolbar.getWebBoard().addToWebBoardList(alCacheObject);
        toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
        //webboardinvoked attribute will be set as true in request scope
        //to avoid the replacement of web board id with old value if it is called twice in same request scope
        request.setAttribute("webboardinvoked", "true");
    }//end of addToWebBoard(PolicyVO policyVO, HttpServletRequest request,String strSwitchType)

    /**
     * This method prepares the Weboard id for the selected Policy
     * @param policyVO  PolicyVO for which webboard id to be prepared
     * @param strSwitchType identifier for enrollment or endorsement flow
     * @return Web board id for the passedVO
     */
    private String prepareWebBoardId(PolicyVO policyVO,String strSwitchType)throws TTKException
    {
        StringBuffer sbfCacheId=new StringBuffer();

        //set Policy Seq Id,policy No and endorsement Seq Ids will be concatanated
        //and set as the valueof Cache id
        if(strSwitchType.equals(strEnrollment))
        {
        	//if policy seq id is not there then empty String with space is used as identifier
            sbfCacheId.append(policyVO.getPolicySeqID()!=null? String.valueOf(policyVO.getPolicySeqID()):" ");
            //if Endorsement Seq id is not there then empty String with space is used as identifier
            sbfCacheId.append("~#~").append(policyVO.getEndorsementSeqID()!=null? String.valueOf(policyVO.
            		getEndorsementSeqID()):" ");
        }//end of if(strSwitchType.equals(strEnrollment))
        else
        {
        	//if Endorsement Seq id is not there then empty String with space is used as identifier
            sbfCacheId.append(policyVO.getEndorsementSeqID()!=null? String.valueOf(policyVO.getEndorsementSeqID()):" ");
            //if policy seq id is not there then empty String with space is used as identifier
            sbfCacheId.append("~#~").append(policyVO.getPolicySeqID()!=null? String.valueOf(policyVO.
            		getPolicySeqID()):" ");
        }//end of else

        sbfCacheId.append("~#~").append(TTKCommon.checkNull(String.valueOf(policyVO.getPolicyNbr())));
        //identifier to check for valid Policy
        sbfCacheId.append("~#~").append(TTKCommon.checkNull(policyVO.getPolicyYN()).equals("")?"N":policyVO.
        		getPolicyYN());
        //if Group Id is not there then empty String with space is used as identifier
        sbfCacheId.append("~#~").append(TTKCommon.checkNull(policyVO.getGroupID()).equals("")? " ":policyVO.
        		getGroupID());
        //if Group Name is not there then empty String with space is used as identifier
        sbfCacheId.append("~#~").append(TTKCommon.checkNull(policyVO.getGroupName()).equals("")? " ":policyVO.
        		getGroupName());
        //if Completed_yn is not there then empty String with space is used as identifier
        sbfCacheId.append("~#~").append(TTKCommon.checkNull(policyVO.getCompletedYN()).equals("")? " ":policyVO.
        		getCompletedYN());
        return sbfCacheId.toString();
    }//end of prepareWebBoardId(PolicyVO policyVO,String strSwitchType)



    /**
     * This menthod for document viewer information
     * @param request HttpServletRequest object which contains hospital information.
     * @param policyDetailVO PolicyDetailVO object which contains policy information.
     * @exception throws TTKException
     */
    private void documentViewer(HttpServletRequest request, PolicyDetailVO policyDetailVO) throws TTKException
    {
        ArrayList<String> alDocviewParams = new ArrayList<String>();
        alDocviewParams.add("leftlink="+TTKCommon.getActiveLink(request));

        alDocviewParams.add("policy_number="+WebBoardHelper.getPolicyNumber(request));
        alDocviewParams.add("dms_reference_number="+policyDetailVO.getDMSRefID());
        if(request.getSession().getAttribute("toolbar")!=null)
            ((Toolbar)request.getSession().getAttribute("toolbar")).setDocViewParams(alDocviewParams);
    }//end of documentViewer(HttpServletRequest request, PolicyDetailVO policyDetailVO)


    /**
     * Returns the PolicyManager session object for invoking methods on it.
     * @return policyManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    /*private ValidationRuleManager getValidationRuleManagerObject() throws TTKException
    {
        ValidationRuleManager validationRuleManager = null;
        try
        {
            if(validationRuleManager == null)
            {
                InitialContext ctx = new InitialContext();
                validationRuleManager = (ValidationRuleManager) ctx.lookup("java:global/TTKServices/business.ejb3/ValidationRuleManagerBean!com.ttk.business.webservice.ValidationRuleManager");
            }//end if(policyManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "policydetail");
        }//end of catch
        return validationRuleManager;
    }//end getHospitalManagerObject()
*/

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
            strForwardPath=strIndPolicyDetails;
        }//end of if(strActiveSubLink.equals(strIndPolicy))
        else if (strActiveSubLink.equals(strIndGrpPolicy))
        {
            strForwardPath=strGrpPolicyDetails;
        }//end of else if (strActiveSubLink.equals(strIndGrpPolicy))
        else if (strActiveSubLink.equals(strCorporatePolicy))
        {
            strForwardPath=strCorpPolicyDetails;
        }//end of else if (strActiveSubLink.equals(strCorporatePolicy))
        else if (strActiveSubLink.equals(strNonCorporatePolicy))
        {
            strForwardPath=strNonCorpPolicyDetails;
        }//end of else if (strActiveSubLink.equals(strNonCorporatePolicy))
        return strForwardPath;
    }//end of getForwardPath(String strActiveSubLink)


    /**
     * This method is prepares the Caption based on the flow and retunrs it
     *
     ** @param request HttpServletRequest current request
     * @param strSwitchType String Identfier for Enrollment/Endorsement flow
     * @param strActiveSubLink String current sublink
     * @return StringBuffer prepared Caption
     */
    private StringBuffer buildCaption(HttpServletRequest request,String strActiveSubLink,String strSwitchType)
    	throws TTKException
    {
        StringBuffer strCaption=new StringBuffer();
        if(strActiveSubLink.equals(strIndPolicy))//for Individual Policy
        {
            strCaption.append("Individual Policy");
        }//end of if(strActiveSubLink.equals(strIndPolicy))
        else if(strActiveSubLink.equals(strIndGrpPolicy))//for Individual Policy
        {
            strCaption.append("Individual Policy as Group");
        }//end of else if(strActiveSubLink.equals(strIndGrpPolicy))
        else if(strActiveSubLink.equals(strCorporatePolicy))//for Individual Policy
        {
            strCaption.append("Corporate Policy");
        }//end of else if(strActiveSubLink.equals(strCorporatePolicy))
        else if(strActiveSubLink.equals(strNonCorporatePolicy))//for Individual Policy
        {
            strCaption.append("Non-Corporate Policy");
        }//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
        if(strSwitchType.equals(strEndorsement))
        {
            strCaption.append(" - [ ");
            strCaption.append(WebBoardHelper.getPolicyNumber(request));
            strCaption.append(" ] ");
        }//end of if(strSwitchType.equals("END"))

        return strCaption;
    }//end of

    /**
     * This method returns the identifier of the policy type based on the flow
     *
     * @param strActiveSubLink current sublink
     * @return strPolicyType String Policy type Identfier
     */
    private String getPolicyType(String strActiveSubLink)
    {
        String strPolicyType=null;

        if(strActiveSubLink.equals(strIndPolicy))
        {
            strPolicyType=strIndPolicyType;
        }//end of if(strActiveSubLink.equals(strIndPolicy))
        else if (strActiveSubLink.equals(strIndGrpPolicy))
        {
            strPolicyType=strGrpPolicyType;
        }//end of else if (strActiveSubLink.equals(strIndGrpPolicy))
        else if (strActiveSubLink.equals(strCorporatePolicy))
        {
            strPolicyType=strCorpPolicyType;
        }//end of else if (strActiveSubLink.equals(strCorporatePolicy))
        else if (strActiveSubLink.equals(strNonCorporatePolicy))
        {
            strPolicyType=strNonCorpPolicyType;
        }//end of else if (strActiveSubLink.equals(strNonCorporatePolicy))
        return strPolicyType;
    }//end of getPolicyType(String strActiveSubLink)

    // Added the following piece of code for integrating Rule related validation 
    // as per Sreeraj's instruction
	/**
     * Returns the RuleManager session object for invoking methods on it.
     * @return RuleManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private BrokerManager getBrokerManagerObject() throws TTKException {
		BrokerManager bromanager = null;
		try {
			if (bromanager == null) {
				InitialContext ctx = new InitialContext();
				bromanager = (BrokerManager) ctx
						.lookup("java:global/TTKServices/business.ejb3/BrokerManagerBean!com.ttk.business.empanelment.BrokerManager");
			}// end if
		}// end of try
		catch (Exception exp) {
			// throw new TTKException(exp, "strBroError");
			throw new TTKException(exp, "endorsementdetails");

		}// end of catch
		return bromanager;
	}
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
    
    
    
	public ActionForward doUploadPolicyDocs(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		try{
			//test nag
			setLinks(request);
			request.getSession().removeAttribute("successMsgs");
			MOUDocumentVO mouDocumentVO	=	new MOUDocumentVO();
			DynaActionForm frmPolicyUploads=(DynaActionForm)form;
			mouDocumentVO=(MOUDocumentVO)FormUtils.getFormValues(frmPolicyUploads,this, mapping, request);
			String policy_seq_id	=	(String)request.getSession().getAttribute("upload_policy_seq_id");
			/*  Commented by Rishi Sharma
			 * com.ttk.action.table.TableData tablePolicyDocs = null;
			if(request.getSession().getAttribute("tablePolicyDocs")!=null) 
			{
				tablePolicyDocs=(com.ttk.action.table.TableData)(request.getSession()).getAttribute("tablePolicyDocs");
			}//end of if(request.getSession().getAttribute("tablePolicyDocs")!=null)
			else
			{
				tablePolicyDocs=new com.ttk.action.table.TableData();
			}//end of else
*/			
			ArrayList<MOUDocumentVO>alPolicyDocs = null;
			if(request.getSession().getAttribute("alPolicyDocs")!=null) 
			{
				alPolicyDocs=(ArrayList)(request.getSession()).getAttribute("alPolicyDocs");
			}//end of if(request.getSession().getAttribute("tablePolicyDocs")!=null)
			else
			{
				alPolicyDocs=new ArrayList<MOUDocumentVO>();
			}//end of else
			
			mouDocumentVO=(MOUDocumentVO)FormUtils.getFormValues(frmPolicyUploads,this, mapping, request);
			mouDocumentVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			//ConfigurationManager servConfigurationManager=this.getConfManager();
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			
			//..............File Upload from Local System.................
			//UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			mouDocumentVO =(MOUDocumentVO)FormUtils.getFormValues(frmPolicyUploads, "frmPolicyUploads",this, mapping, request);
			//Get the FormFile object from ActionForm.
			StringBuffer strCaption=new StringBuffer();
			ArrayList alFileAUploadList = new ArrayList();
			Long userSeqId	=	(Long) TTKCommon.getUserSeqId(request);
			//S T A R T S FETCH FILE FROM LOCAL SYSTEM
			/*Commented by Rishi Sharma
			FormFile formFile = (FormFile)frmPolicyUploads.get("file");
			String fileDesc	=	(String)frmPolicyUploads.get("description");
            */
			FormFile formFile = (FormFile)frmPolicyUploads.get("file");
			String filetype	=	(String)frmPolicyUploads.get("fileType");
			String filedescription=	(String)frmPolicyUploads.get("filedescription");
			if(filedescription.length()>250)
			{
				 ActionMessages actionMessages = new ActionMessages();
	             ActionMessage actionMessage = new ActionMessage("error.fileDescription");
	             actionMessages.add("global.error",actionMessage);
	             saveErrors(request,actionMessages);
			}
			else
			{
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
	        InputStream inputStream = formFile.getInputStream(); 
			int formFileSize	=	formFile.getFileSize();
		    if(formFileSize==0)
		    {
		    	//throw new TTKException("error.formfilesize");
		   	 ActionMessages actionMessages = new ActionMessages();
             ActionMessage actionMessage = new ActionMessage("error.formfilesize");
             actionMessages.add("global.error",actionMessage);
             saveErrors(request,actionMessages);
		    }
		    else
		    {
            //COPYNG FILE TO SERVER FOR BACKUP
            FileOutputStream outputStream = null;
            String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("PolicyDocumentStorage"));
            File folder = new File(path);
            if(!folder.exists()){
                  folder.mkdirs();
            }
            //String finalPath=(path+new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+"_"+formFile);
            String timeStamps	=	new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date());
            String fileExc[]=formFile.getFileName().split("[.]");
            String oFileName=formFile.getFileName().split("."+fileExc[fileExc.length-1])[0];
            String filetypesName = fileExc[1];
            if("mp4".equals(filetypesName) || "exe".equals(filetypesName) || "mp3".equals(filetypesName) || "wmv".equals(filetypesName))
            	{
            	//throw new TTKException("error.filetype.mp3.mp4.exe");
            	 ActionMessages actionMessages = new ActionMessages();
                 ActionMessage actionMessage = new ActionMessage("error.filetype.mp3.mp4.exe");
                 actionMessages.add("global.error",actionMessage);
                 saveErrors(request,actionMessages);
            	
            	}
            else
            {
            String fileName2=oFileName+"-"+timeStamps+"."+fileExc[fileExc.length-1];
            String origFileName	=	formFile.toString().substring(0, formFile.toString().lastIndexOf("."))+
            		timeStamps+formFile.toString().substring(formFile.toString().lastIndexOf("."));
            String finalPath=(path+formFile.toString().substring(0, formFile.toString().lastIndexOf("."))+
            		timeStamps+formFile.toString().substring(formFile.toString().lastIndexOf(".")));
            
            if(finalPath.length()>200)
            {
            	 ActionMessages actionMessages = new ActionMessages();
                 ActionMessage actionMessage = new ActionMessage("error.fileNamePath.PolicyDOCUploads");
                 actionMessages.add("global.error",actionMessage);
                 saveErrors(request,actionMessages);
            }
            else
            {
            	
            outputStream = new FileOutputStream(new File(path+fileName2));
/*           Commented By Rishi Sharma
			alFileAUploadList.add(TTKCommon.getUserSeqId(request));//0
			alFileAUploadList.add(finalPath);//1
			alFileAUploadList.add(fileDesc);//2
			alFileAUploadList.add(policy_seq_id);//3
			*/
            alFileAUploadList.add(TTKCommon.getUserSeqId(request));//0
			alFileAUploadList.add(finalPath);//1
			alFileAUploadList.add(filetype);//2
			alFileAUploadList.add(filedescription);//3
			alFileAUploadList.add(policy_seq_id);//4
			alFileAUploadList.add(strSwitchType);//5
		   outputStream.write(formFile.getFileData());//Uploaded file backUp
            // E N D S FETCH FILE FROM LOCAL SYSTEM
          //get the session bean from the bean pool for each excecuting thread
            PolicyManager policyObject=this.getPolicyManagerObject();
           int iSuccess	=	 policyObject.savePolicyDocsUploads(alFileAUploadList,userSeqId,policy_seq_id,fileName2,inputStream,formFileSize);
           if(iSuccess>0)
			{    //Commented By Rishi Sharma
				/*ArrayList alPolicyDocs = new ArrayList();*/
				alPolicyDocs = policyObject.getPolicyDocsUploads(policy_seq_id);
				//Commented By Rishi Sharma
				/*tablePolicyDocs.setData(alPolicyDocs,"search");
				request.getSession().setAttribute("tablePolicyDocs",tablePolicyDocs);*/
				request.getSession().setAttribute("alPolicyDocs", alPolicyDocs);
				frmPolicyUploads.set("fileType","");
				frmPolicyUploads.set("filedescription","");
				request.getSession().setAttribute("frmPolicyUploads",frmPolicyUploads);
				request.getSession().setAttribute("successMsgs", "Document Upload Successfully.");
				
			}
			else{
				request.getSession().setAttribute("notify", "Document not Uploaded Properly! Please again upload the Document.");
			//frmPolicyUploads.set("updated",updated);
			request.getSession().setAttribute("frmPolicyUploads", frmPolicyUploads);
			}
			//frmPolicyUploads.set("caption",strCaption);
			//..............File Upload from Local System Ends...........
			
			if(inputStream!=null)
				inputStream.close();
			if(outputStream!=null)
				outputStream.close();
            }
            }
		    }
			}
			return mapping.findForward("policyDocsUploads");
		}//end of try
		catch(TTKException expTTK)
	     {
	            return this.processExceptions(request, mapping, expTTK);
	     }//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,"endorsementdetails"));
		}//end of catch(Exception exp)
	}//end of doUploadPolicyDocs(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				//HttpServletResponse response)
	
	
	public ActionForward doPolicyFileDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
			{
		try{
			
			setLinks(request);
			ArrayList<MOUDocumentVO>alPolicyDocs = new ArrayList<MOUDocumentVO>();
			String policy_seq_id	=	(String)request.getSession().getAttribute("upload_policy_seq_id");
    		String moudocseqid = request.getParameter("moudocseqid");
			String deleteRemarks = request.getParameter("deleteRemarks");
			if(deleteRemarks.length() > 250)
			{
				 ActionMessages actionMessages = new ActionMessages();
	             ActionMessage actionMessage = new ActionMessage("error.deleteRemarks");
	             actionMessages.add("global.error",actionMessage);
	             saveErrors(request,actionMessages);
			}
			else
			{
    		Long userSeqId	=	(Long) TTKCommon.getUserSeqId(request);
    		DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String mode=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            PolicyManager policyObject=this.getPolicyManagerObject();
            int result = policyObject.deleteUploadedFile(policy_seq_id,moudocseqid,deleteRemarks,userSeqId,mode);
            if(result>0)
            {
            	request.getSession().setAttribute("successMsgs", "Document deleted successfully.");
            	alPolicyDocs = policyObject.getPolicyDocsUploads(policy_seq_id);
            	request.getSession().setAttribute("alPolicyDocs", alPolicyDocs);
            }
            else
            {
            	request.getSession().setAttribute("notify", "Document deleted problem.");
            }
			}
			return mapping.findForward("policyDocsUploads");
    		}//end of try
		    catch(TTKException expTTK)
	        {
	            return this.processExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
    		catch(Exception exp)
    		{
    			return this.processExceptions(request,mapping,new TTKException(exp,"endorsementdetails"));
    		}//end of catch(Exception exp)
    	}//end of doPolicyFileDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    				//HttpServletResponse response)
	
	
	public ActionForward doPolicyUploads(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.info("Inside the doPolicyUploads method of PolicyDetailsAction");

		try
		{
			request.getSession().removeAttribute("alPolicyDocs");
			
			request.getSession().removeAttribute("frmPolicyUploads");
			
			request.getSession().removeAttribute("successMsgs");
			
			request.getSession().removeAttribute("notify");
			
			String policy_seq_id	=	(String)request.getParameter("policy_seq_id");
			
			String eventSeqId		=	request.getParameter("eventSeqId");
			
			String switchtype		=	request.getParameter("switType");
			
			DynaActionForm frmPolicyDetails = (DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");
			
			request.getSession().setAttribute("upload_policy_seq_id", policy_seq_id);
			setLinks(request);
			
			String strTable = "";
			
			DynaActionForm frmPolicyUploads= (DynaActionForm) form;
			
			frmPolicyUploads.set("fileType", "");
			
			frmPolicyUploads.set("filedescription", "");
			
			ArrayList alPolicyDocs = new ArrayList();
			//Commented By Rishi Sharma
			//com.ttk.action.table.TableData tablePolicyDocs =TTKCommon.getTableData(request);
			DynaActionForm frmtemp	=	(DynaActionForm) request.getSession().getAttribute("frmPolicyDetails");

			frmPolicyUploads.set("caption",frmtemp.get("policyNbr"));
			//frmPolicyUploads.set("switchtype",switchtype);
			//get the table data from session if exists	
			//Commented By Rishi Sharma
			/*if(tablePolicyDocs==null){
			//create new table data object
			tablePolicyDocs = new com.ttk.action.table.TableData();
		}//end of if(tableData==null) 	
			 */		//create the required grid table
			//Commented By Rishi Sharma
			//	strTable = "MouUploadFilesTable";		
			//	tablePolicyDocs.createTableInfo(strTable,null);
			PolicyManager policyObject=this.getPolicyManagerObject();
			
			alPolicyDocs = policyObject.getPolicyDocsUploads(policy_seq_id);
			//Commented By Rishi Sharma
			//	tablePolicyDocs.setData(alPolicyDocs,"search");
			//	request.getSession().setAttribute("tablePolicyDocs",tablePolicyDocs);
			
				 request.setAttribute("lngEventSeqID",eventSeqId);
				
				 request.setAttribute("switchtype",switchtype);
			
				
				
				
				
			if(!(alPolicyDocs.isEmpty()))
			{	
				MOUDocumentVO moudocObj = (MOUDocumentVO)alPolicyDocs.get(0);
				
			//	int eventseqid = moudocObj.getEventSeqID();
				request.getSession().setAttribute("alPolicyDocs", alPolicyDocs);
				
				request.getSession().setAttribute("frmPolicyUploads",frmPolicyUploads);
				
				request.getSession().setAttribute("policyCompletedYN", request.getSession().getAttribute("policyCompletedYN"));
				
				return mapping.findForward("policyDocsUploads");//this.getForward("policyDocsUploads", mapping, request);
			
			}	
			else
			{
               return mapping.findForward("policyDocsUploads");//this.getForward("policyDocsUploads", mapping, request);
			}
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"endorsementdetails"));
		}//end of catch(Exception exp)
	}//end of doPolicyUploads(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward checkIssueDate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{

		try
		{
			
			String policyStartDate	=request.getParameter("startDate");
			String policyEndDate	=request.getParameter("endDate");
			String policyIssueDate	=request.getParameter("issueDate");
			SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
			

			long from = dateFormat.parse(policyStartDate).getTime();  
			long to   = dateFormat.parse(policyEndDate).getTime();  
			long check = dateFormat.parse(policyIssueDate).getTime();  
			
			if(check < from || check > to){
				response.getWriter().write("NV");				
			}
				
				return null;
		}//end of try
		catch(ParseException exp)
		{
			 response.getWriter().write("DFNV");
		}
		catch(Exception exp)
		{
			 response.getWriter().write("ER");
		}//end of catch(Exception exp)
		return null;
	}//end of doPolicyUploads(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward doSelectCompanyDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
			{
		try{
			log.info("Inside the doSelectCompanyDetails method of PolicyDetailsAction");
			setLinks(request);
			 TableData tableData =TTKCommon.getTableData(request);
	            //create new table data object
	            tableData = new TableData();
	            //create the required grid table
	            tableData.createTableInfo("coInsCompDetailsTable",new ArrayList());
	            request.getSession().setAttribute("tableData",tableData);
			return mapping.findForward(strcoInsCompDetails);
    		}//end of try
		    catch(TTKException expTTK)
	        {
	            return this.processExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
    		catch(Exception exp)
    		{
    			return this.processExceptions(request,mapping,new TTKException(exp,"endorsementdetails"));
    		}//end of catch(Exception exp)
    	}//doSelectCompanyDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    				//HttpServletResponse response)
	public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
			{
		try{
			setLinks(request);
			DynaActionForm frmCoInsCompDetails = (DynaActionForm)form;
			PolicyManager policyObject=this.getPolicyManagerObject();
			DynaActionForm frmPolicyDetails= (DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");
			PolicyDetailVO policyDetailVO = (PolicyDetailVO)FormUtils.getFormValues(frmPolicyDetails, this, mapping, request);
			/*policyDetailVO.setCoInsuranceV("Vikram");*/
			
			Long policySqId = policyDetailVO.getPolicySeqID();
			TableData tableData = new TableData();
			tableData .createTableInfo("coInsCompDetailsTable",new ArrayList());
			tableData.setSearchData(this.populateSearchCriteria(frmCoInsCompDetails));
			tableData.modifySearchData("search");
			String compNameString = "|";
			String compSeqString = "|";
			ArrayList<PolicyDetailVO> alCoInsList = new ArrayList();
			alCoInsList = policyObject.getcoInsCompDetailsList(tableData.getSearchData(),policySqId);
			for(PolicyDetailVO obj:alCoInsList){
				compNameString = compNameString+obj.getCompName()+"|";
				compSeqString = compSeqString+obj.getCoInsSeqId()+"|";
			}

			/*frmCoInsCompDetails= (DynaActionForm)FormUtils.setFormValues("frmPolicyDetails", policyDetailVO,
                    this,mapping, request);*/
			
			request.getSession().setAttribute("alCoInsList",alCoInsList);/*
			request.getSession().setAttribute("compNameString",compNameString);
			request.getSession().setAttribute("compSeqString",compSeqString);*/
			tableData.setData(alCoInsList, "search");
			//set the table data object to session
			frmCoInsCompDetails.set("searchflag", "Y");
			request.getSession().setAttribute("seqId",policySqId);
			request.getSession().setAttribute("frmCoInsCompDetails",frmCoInsCompDetails);

			request.getSession().setAttribute("frmPolicyDetails",frmPolicyDetails);
			request.getSession().setAttribute("tableData",tableData);
			request.getSession().setAttribute("coInsCompDetailsTable",tableData);
			//finally return to the grid screen
			return mapping.findForward(strcoInsCompDetails);
    		}//end of try
		    catch(TTKException expTTK)
	        {
	            return this.processExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
    		catch(Exception exp)
    		{
    			return this.processExceptions(request,mapping,new TTKException(exp,"endorsementdetails"));
    		}//end of catch(Exception exp)
    	}//doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmCoInsCompDetails) {
		ArrayList<Object> alSearchObjects = new ArrayList<Object>();
		alSearchObjects.add(frmCoInsCompDetails.get("compName"));
		alSearchObjects.add(frmCoInsCompDetails.get("compID"));
		return alSearchObjects;
	}
	
	public ActionForward doViewInsComp(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
			{
		try{
			log.info("Inside the doViewInsComp method of PolicyDetailsAction");
			setLinks(request);
			HttpSession session=request.getSession();
			TableData coInsCompDetailsTable = (TableData)session.getAttribute("coInsCompDetailsTable");
			DynaActionForm frmPolicyDetails= (DynaActionForm) request.getSession().getAttribute("frmPolicyDetails");
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals(""))){
				PolicyDetailVO policyDetailVO=(PolicyDetailVO)coInsCompDetailsTable.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
				frmPolicyDetails.set("coInsComDetails", policyDetailVO.getCompName());
				request.getSession().setAttribute("frmPolicyDetails",frmPolicyDetails);
			}
			
			return mapping.findForward(strCorpPolicyDetails);
    		}//end of try
		    catch(TTKException expTTK)
	        {
	            return this.processExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
    		catch(Exception exp)
    		{
    			return this.processExceptions(request,mapping,new TTKException(exp,"endorsementdetails"));
    		}//end of catch(Exception exp)
    	}//doViewInsComp(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    				//HttpServletResponse response)
	
	public ActionForward toAddCoInsComp(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
			{
		try{
			log.info("Inside the toAddCoInsComp method of PolicyDetailsAction");
			setLinks(request);
			DynaActionForm frmCoInsCompDetails= (DynaActionForm) form;
			frmCoInsCompDetails.initialize(mapping);
			return mapping.findForward(strAddCoInsComp);
    		}//end of try
		    catch(TTKException expTTK)
	        {
	            return this.processExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
    		catch(Exception exp)
    		{
    			return this.processExceptions(request,mapping,new TTKException(exp,"endorsementdetails"));
    		}//end of catch(Exception exp)
    	}//doViewInsComp(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    				//HttpServletResponse response)
	
	public ActionForward doCloseCoInsComp(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
			{
		try{
			log.info("Inside the doCloseCoInsComp method of PolicyDetailsAction");
			setLinks(request);
			DynaActionForm frmCoInsCompDetails= (DynaActionForm) form;
			String compNameString="|";
			String compSeqString="|";
			
			DynaActionForm frmPolicyDetails= (DynaActionForm) request.getSession().getAttribute("frmPolicyDetails");
			ArrayList<PolicyDetailVO> alCoInsList = (ArrayList<PolicyDetailVO>) request.getSession().getAttribute("alCoInsList");
			for(PolicyDetailVO obj:alCoInsList){
				
				compNameString = compNameString+obj.getCompName()+"|";
				compSeqString = compSeqString+obj.getCoInsSeqId()+"|";
			}
			frmPolicyDetails.set("compNameString", compNameString);
			frmPolicyDetails.set("compSeqString", compSeqString);
			request.getSession().setAttribute("frmPolicyDetails",frmPolicyDetails);
			frmCoInsCompDetails.initialize(mapping);
			return mapping.findForward(strCorpPolicyDetails);
    		}//end of try
		    catch(TTKException expTTK)
	        {
	            return this.processExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
    		catch(Exception exp)
    		{
    			return this.processExceptions(request,mapping,new TTKException(exp,"endorsementdetails"));
    		}//end of catch(Exception exp)
    	}//doViewInsComp(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    				//HttpServletResponse response)
	public ActionForward doCloseAddCoInsComp(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
			{
		try{
				log.info("Inside the doSelectCompanyDetails method of PolicyDetailsAction");
				setLinks(request);
				TableData tableData =TTKCommon.getTableData(request);
				DynaActionForm frmCoInsCompDetails= (DynaActionForm) form;
				frmCoInsCompDetails.initialize(mapping);
				PolicyManager policyObject=this.getPolicyManagerObject();
				Long policySqId = (Long) request.getSession().getAttribute("seqId");
				tableData.setSearchData(this.populateSearchCriteria(frmCoInsCompDetails));
				tableData.modifySearchData("search");
				ArrayList alCoInsList = new ArrayList();
				alCoInsList = policyObject.getcoInsCompDetailsList(tableData.getSearchData(),policySqId);
				tableData.setData(alCoInsList, "search");
				//set the table data object to session
				request.getSession().setAttribute("alCoInsList",alCoInsList);
				frmCoInsCompDetails.set("searchflag", "Y");
				request.getSession().setAttribute("coInsCompDetailsTable",tableData);
		        request.getSession().setAttribute("tableData",tableData);
		        
				return mapping.findForward(strcoInsCompDetails);
    		}//end of try
		    catch(TTKException expTTK)
	        {
	            return this.processExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
    		catch(Exception exp)
    		{
    			return this.processExceptions(request,mapping,new TTKException(exp,"endorsementdetails"));
    		}//end of catch(Exception exp)
    	}//doViewInsComp(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    				//HttpServletResponse response)
	private String[] populateDeleteId(HttpServletRequest request, TableData tableData)
	{
		String[] strChk = request.getParameterValues("chkopt");
		String[] strDeleteIds = {"",""};
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
						strDeleteIds[0] = String.valueOf(((PolicyDetailVO)tableData.getData().
										get(Integer.parseInt(strChk[i]))).getCoInsSeqId());
						strDeleteIds[1] = String.valueOf(((PolicyDetailVO)tableData.getData().
								 get(Integer.parseInt(strChk[i]))).getCoInsPercenSeqId());
					}//end of if(i==0)
					else
					{
						strDeleteIds[0] = strDeleteIds[0] +"|"+ ((PolicyDetailVO)tableData.getData().
											   get(Integer.parseInt(strChk[i]))).getCoInsSeqId();
						strDeleteIds[1] = strDeleteIds[1] +"|"+ ((PolicyDetailVO)tableData.getData().
								   get(Integer.parseInt(strChk[i]))).getCoInsPercenSeqId();
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
		}//end of if(strChk!=null&&strChk.length!=0)
		return strDeleteIds;
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)

	
	public ActionForward doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
				try{
					log.debug("Inside the doDeleteList method of ProductSearchAction");
					setLinks(request);
					PolicyManager policyObject=this.getPolicyManagerObject();
					DynaActionForm frmCoInsCompDetails= (DynaActionForm) form;
		            DynaActionForm frmPolicyDetails= (DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");
					TableData tableData = TTKCommon.getTableData(request);
					PolicyDetailVO policyDetailVO = (PolicyDetailVO)FormUtils.getFormValues(frmPolicyDetails, this, mapping, request);
					Long policySqId = policyDetailVO.getPolicySeqID();
					String[] strDeleteIds ={"",""};
					ArrayList<Object> alDeleteProducts = new ArrayList<Object>();
					strDeleteIds = populateDeleteId(request, (TableData)request.getSession().getAttribute("tableData"));
					alDeleteProducts.add("|"+strDeleteIds[0]+"|");
					alDeleteProducts.add("|"+strDeleteIds[1]+"|");
					alDeleteProducts.add(TTKCommon.getUserSeqId(request));
					int iCount = policyObject.deleteCoInsComp(alDeleteProducts,policySqId);
					if(iCount>0)
					{
					request.setAttribute("cacheId",strDeleteIds[1]);
					TTKCommon.deleteWebBoardId(request);
					}
					ArrayList alCoInsList = null;
					tableData.setSearchData(this.populateSearchCriteria(frmCoInsCompDetails));
					
					if(iCount == tableData.getData().size())
					{
						if(tableData.getData().size()==1){
							alCoInsList = policyObject.getcoInsCompDetailsList(tableData.getSearchData(),policySqId);
						}
						else{
							tableData.modifySearchData("Delete");
							int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
													getSearchData().size()-2));
							if(iStartRowCount > 0)
								{
									alCoInsList = policyObject.getcoInsCompDetailsList(tableData.getSearchData(),policySqId);
								}
					}
					}
					else
					{
						alCoInsList = policyObject.getcoInsCompDetailsList(tableData.getSearchData(),policySqId);
					}//end of else
					request.getSession().setAttribute("alCoInsList",alCoInsList);
					tableData.setData(alCoInsList, "Delete");
					request.getSession().setAttribute("tableData",tableData);
					return mapping.findForward(strcoInsCompDetails);
				}//end of try
				catch(TTKException expTTK)
				{
					return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request, mapping, new TTKException(exp,"endorsementdetails"));
				}//end of catch(Exception exp)
				}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				//HttpServletResponse response)
	
	public ActionForward saveCoInsComp(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
			{
		try{
				log.info("Inside the saveCoInsComp method of PolicyDetailsAction");
				setLinks(request);
				DynaActionForm frmCoInsCompDetails= (DynaActionForm) form;
				PolicyDetailVO policyDetailVO= (PolicyDetailVO)FormUtils.getFormValues(frmCoInsCompDetails,this,mapping,request);
				Long policySqId = (Long) request.getSession().getAttribute("seqId");
				String compName = frmCoInsCompDetails.getString("compName");
				PolicyManager policyObject=this.getPolicyManagerObject();
				String message = policyObject.addCoInsComp(compName,policyDetailVO,policySqId);
				 if (!message.equals("")){
    				 request.setAttribute("updated","Co Insurance company added Successfully" );
    			 }
    			 else{
    				 request.setAttribute("fileError","Co Insurance company cannot be added" );
    			 }
				frmCoInsCompDetails.initialize(mapping);
				return mapping.findForward(strAddCoInsComp);
    		}//end of try
		    catch(TTKException expTTK)
	        {
	            return this.processExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
    		catch(Exception exp)
    		{
    			return this.processExceptions(request,mapping,new TTKException(exp,"endorsementdetails"));
    		}//end of catch(Exception exp)
    	}//doViewInsComp(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    				//HttpServletResponse response)
	
	public ActionForward toGetCompanyCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
			{
		try{
				log.info("Inside the toGetCompanyCode method of PolicyDetailsAction");
				setLinks(request);
				DynaActionForm frmCoInsCompDetails= (DynaActionForm) form;
				String compSeqId = frmCoInsCompDetails.getString("compName");
				PolicyManager policyObject=this.getPolicyManagerObject();
				String compId = policyObject.getCompanyCode(compSeqId);
				frmCoInsCompDetails.set("compID", compId);
				request.getSession().setAttribute("frmCoInsCompDetails", frmCoInsCompDetails);
				return mapping.findForward(strAddCoInsComp);
    		}//end of try
		    catch(TTKException expTTK)
	        {
	            return this.processExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
    		catch(Exception exp)
    		{
    			return this.processExceptions(request,mapping,new TTKException(exp,"endorsementdetails"));
    		}//end of catch(Exception exp)
    	}//doViewInsComp(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    				//HttpServletResponse response)
	public ActionForward doChangeCoIns(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			DynaActionForm frmCoInsCompDetails= (DynaActionForm)form;
			String coInsV = frmCoInsCompDetails.getString("coInsuranceV");
			DynaActionForm frmPolicyDetails= (DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");
			/*PolicyDetailVO policyDetailVO= (PolicyDetailVO)FormUtils.getFormValues(frmPolicyDetails,this,mapping,request);
			frmPolicyDetails= (DynaActionForm)FormUtils.setFormValues("frmPolicyDetails",policyDetailVO,this,mapping, request);*/

			frmPolicyDetails.set("coInsuranceV", coInsV);
			request.getSession().setAttribute("frmPolicyDetails",frmPolicyDetails);
			return this.getForward(strCorpPolicyDetails, mapping, request);
		}
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strCorpPolicyDetails));
		}
	} 
}//end of PolicyDetailsAction
