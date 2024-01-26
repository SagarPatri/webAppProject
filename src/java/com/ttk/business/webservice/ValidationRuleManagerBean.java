/**
 * @ (#) RuleManagerBean.java Apr 11, 2006
 * Project 	     : TTK HealthCare Services
 * File          : RuleManagerBean.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 11, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import com.ttk.business.preauth.ruleengine.CompileErros;
import com.ttk.business.preauth.ruleengine.RuleEngine;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.preauth.PreAuthDAOFactory;
import com.ttk.dao.impl.preauth.RuleDAOImpl;
import com.ttk.dto.webservice.ErrorLogVO;
@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class ValidationRuleManagerBean implements ValidationRuleManager{

    private PreAuthDAOFactory preAuthDAOFactory = null;
    private RuleDAOImpl ruleDAO = null;

    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getRuleDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if (preAuthDAOFactory == null)
                preAuthDAOFactory = new PreAuthDAOFactory();
            if(strIdentifier!=null)
            {
                return preAuthDAOFactory.getDAO(strIdentifier);
            }
            else
                return null;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error."+strIdentifier+".dao");
        }//end of catch(Exception exp)
    }//End of getRuleDAO(String strIdentifier)

    /**
     * This method saves the Rule Error information
     * @param ArrayList having errorLogVO
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveRuleErrors(ArrayList alErrors) throws TTKException
    {
        int iResult = 0;
        ruleDAO = (RuleDAOImpl)this.getRuleDAO("policyrules");
        iResult = ruleDAO.saveErrorLog(alErrors);
        return iResult;
    }//end of saveRuleErros(ArrayList alErrors)

    /**
     * This method clears the Rule Error information
     * @param String POLICY_NUMBER or TPA_ENROLLMENT_NUMBER or TPA_ENROLLMENT_ID or BATCH_NUMBER
     * @param Stirng flag 'P' for Policy or 'E' for Enrollment or 'M' for Member or 'B' for Batch
     * @exception throws TTKException
     */
    public void clearRuleErrors(String strSeqID,String strFlag) throws TTKException
    {
        ruleDAO = (RuleDAOImpl)this.getRuleDAO("policyrules");
        ruleDAO.clearRuleErrors(strSeqID,strFlag);
    }//end of clearRuleErros(String strSeqID,String strFlag)

    /**
     * This method returns the validation error.
     * @param ArrayList search parameter
     * @return ArrayList containing ErrorLogVO
     * @exception throws TTKException
     */
    public ArrayList selectRuleErrors(ArrayList alSearchParam) throws TTKException
    {
        ArrayList alError = null;
        ruleDAO = (RuleDAOImpl)this.getRuleDAO("policyrules");
        alError = ruleDAO.selectRuleErrors(alSearchParam);
        return alError;
    }//end of selectRuleErros(ArrayList alSearchParam)
    /**
     * This method is used to execute the policy level validation rules
     * @return ArrayList having error if any
     * @param Document containing the rule
     */
    public ArrayList executePolicyValidation(Document ruleDoc) throws TTKException{
        ArrayList alErrors = null;
        RuleEngine ruleEngine = new RuleEngine();
        if(ruleDoc==null)
            return null;
        ErrorLogVO errorLogVO = getPolicyDetail(ruleDoc,"");
        try
        {
            Document policyRuleDoc = DocumentHelper.parseText("<clauses>"+ruleDoc.selectSingleNode("//policy/clause").asXML()+"</clauses>");
            Document resultDoc = ruleEngine.applyClause((policyRuleDoc));
            alErrors = CompileErros.compile(resultDoc,errorLogVO);
        }//end of try
        catch (DocumentException e)
        {
            e.printStackTrace();
        }//end of catch (DocumentException e)
        return alErrors;
    }//end of executePolicyValidation(Document ruleDoc)

    /**
     * This method is used to execute the enrollment level validation rules
     * @return ArrayList having error if any
     * @param Document containing the rule
     */
    public ArrayList executeEnrollmentValidation(Document ruleDoc) throws TTKException{
        ArrayList<Object> alErrors = null;
        ArrayList<Object> alError = null;
        Document resultDoc = null;
        RuleEngine ruleEngine = new RuleEngine();
        if(ruleDoc==null)
            return null;
        ErrorLogVO errorLogVO = getPolicyDetail(ruleDoc,"E");
        try
        {
            Element eleClause = (Element)ruleDoc.selectSingleNode("//policy/enrollment/validation/clause");
            //If there is no clause to execute return
            if(eleClause!=null && eleClause.asXML()!=null)
            {
                Document enrollmentRuleDoc = DocumentHelper.parseText("<clauses>"+eleClause.asXML()+"</clauses>");
                resultDoc = ruleEngine.applyClause((enrollmentRuleDoc));
                alErrors = CompileErros.compile(resultDoc,errorLogVO);
            }//end of if(eleClause!=null ||eleClause.asXML()!=null)
            //Run the rule for all the member in enrollment
            List listMember = ruleDoc.selectNodes("//member");
            for(int iMember=0;iMember<listMember.size();iMember++)
            {
                resultDoc = null;
                alError = new ArrayList<Object>();
                Element member = ((Element)listMember.get(iMember));
                //fill member detail
                errorLogVO.setEnrollmentID(TTKCommon.checkNull(member.valueOf("@tpaenrollmentid")));
                errorLogVO.setMemberRelation(TTKCommon.checkNull(member.valueOf("@relationship")));
                Document memberRuleDoc = DocumentHelper.parseText("<clauses>"+ member.selectSingleNode(".//clause").asXML()+"</clauses>");
                Document resultDoc1 = ruleEngine.applyClause((memberRuleDoc));
                alError = CompileErros.compile(resultDoc1,errorLogVO);
                //Append the memeber error to Enrollment error
                if(alErrors==null)
                {
                    alErrors = alError;
                }//end of if(alErrors==null)
                else
                {
                    alErrors.addAll(alError);
                }//end of else
            }//end of for(int iMember=0;iMember<listMember.size();iMember++)
        }//end of try
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        return alErrors;
    }

    /**
     * This method is used to execute the enrollment level validation rules
     * @param String strFlag identifier containing P -> POLICY, -> E enrollment, M -> member
     * @param Long lngSeqID containing Policy Seq_id or Policy_group_seq_id OR Member seq_id
     * @param String strStatus containing U -> unckecked, P -> passed F -> failed
     */
    public void updateValidationStatus(String strFlag,Long lngSeqID,String strStatus) throws TTKException
    {
        ruleDAO = (RuleDAOImpl)this.getRuleDAO("policyrules");
        ruleDAO.updateValidationStatus(strFlag,lngSeqID,strStatus);
    }//end of updateValidationStatus(String strFlag,Long lngSeqID,String strStatus)

    /**
     * This method is used get the policy and enrollment detail from Rule XML
     * @param Document policy rule document
     * @param String identifier for policy and enrollment
     * @return ErrorLogVo containing the policy and enrollment detail
     */
    private ErrorLogVO getPolicyDetail(Document ruleDoc,String strIdentifer)throws TTKException
    {
        ErrorLogVO errorLogVO = new ErrorLogVO();
        Node policy = ruleDoc.selectSingleNode("//policy");
        Node enrollment = ruleDoc.selectSingleNode("//policy/enrollment");
        if(policy!=null)
        {
            errorLogVO.setOfficeSeqID(TTKCommon.getLong(policy.valueOf("@tpaofficeseqid")));
            errorLogVO.setInsSeqID(TTKCommon.getLong(policy.valueOf("@insseqid")));
            errorLogVO.setAbbrevationCode(TTKCommon.checkNull(policy.valueOf("@abbrevationcode")));
            errorLogVO.setInsCompCode(TTKCommon.checkNull(policy.valueOf("@inscompcodenumber")));
            errorLogVO.setBatchNbr(TTKCommon.checkNull(policy.valueOf("@batchnumber")));
            errorLogVO.setPolicyNbr(TTKCommon.checkNull(policy.valueOf("@policynumber")));
            errorLogVO.setEndorsementNbr(TTKCommon.checkNull(policy.valueOf("@custendorementnumber")));
        }//end of if(policy!=null)
        //Populate the enrollment error on demand
        if(enrollment!=null && strIdentifer.equals("E"))
        {
            errorLogVO.setEmployeeNbr(TTKCommon.checkNull(enrollment.valueOf("@employeeno")));
            errorLogVO.setEnrollmentNbr(TTKCommon.checkNull(enrollment.valueOf("@tpaenrollmentnumber")));
            errorLogVO.setPolicyHolder(TTKCommon.checkNull(enrollment.valueOf("@policyholder")));

        }//end of if(enrollment!=null)
        return errorLogVO;
    }
    public static void main(String ar[]) throws TTKException, DocumentException
    {
        Document ruleDoc = TTKCommon.getDocument("testvalid.xml");
        ValidationRuleManagerBean vb = new ValidationRuleManagerBean();
        ArrayList alErrors = vb.executePolicyValidation(ruleDoc);
//        String strSaveErrorLog = "{CALL POLICY_DATA_FEED_PKG.SAVE_ERROR_LOG(";
        for(int iError=0;iError<alErrors.size();iError++){
            StringBuffer sbfSQL = new StringBuffer();
            ErrorLogVO errorLogVO  = (ErrorLogVO)alErrors.get(iError);
            sbfSQL = sbfSQL.append(errorLogVO.getErrorLogSeqID()).append(",")
            .append(errorLogVO.getOfficeSeqID()).append(",")
            .append(errorLogVO.getInsSeqID()).append(",'")
            .append(errorLogVO.getAbbrevationCode()).append("','")
            .append(errorLogVO.getInsCompCode()).append("','")
            .append(errorLogVO.getBatchNbr()).append("','")
            .append(errorLogVO.getPolicyNbr()).append("','")
            .append(errorLogVO.getEndorsementNbr()).append("','")
            .append(errorLogVO.getEmployeeNbr()).append("','")
            .append(errorLogVO.getPolicyHolder()).append("','")
            .append(errorLogVO.getEnrollmentID()).append("','")
            .append(errorLogVO.getErrorNbr()).append("','")
            .append(errorLogVO.getErrorMessage()).append("','")
            .append(errorLogVO.getErrorType()).append("')}");
        }//end of for
    }
}//end of RuleManagerBean