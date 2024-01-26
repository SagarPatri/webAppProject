/**
 * @ (#) AccountInfoDAOImpl.java Jul 26, 2007
 * Project 	     : TTK HealthCare Services
 * File          : AccountInfoDAOImpl.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 26, 2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

package com.ttk.dao.impl.accountinfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.xdb.XMLType;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.DOMReader;
import org.dom4j.io.SAXReader;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.accountinfo.EndorsementVO;
import com.ttk.dto.accountinfo.PolicyAccountInfoDetailVO;
import com.ttk.dto.accountinfo.PolicyAccountInfoVO;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.enrollment.MemberAddressVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.PolicyVO;
import com.ttk.dto.maintenance.EnrollBufferVO;
import com.ttk.dto.preauth.BufferVO;
import com.ttk.dto.preauth.CitibankHistoryVO;
import com.ttk.dto.preauth.ClaimantHistoryVO;
import com.ttk.dto.preauth.EndorsementHistoryVO;
import com.ttk.dto.preauth.InvestigationHistoryVO;
import com.ttk.dto.preauth.PolicyHistoryVO;
import com.ttk.dto.preauth.PreAuthHistoryVO;


public class AccountInfoDAOImpl implements BaseDAO, Serializable{

	private static Logger log = Logger.getLogger(AccountInfoDAOImpl.class);

	private static final String strPolicyList = "{CALL ACCOUNT_INFO_PKG_SELECT_MEMBER_LIST(?,?,?,?,?,?,?)}";//KOC Cigna_insurance_resriction one parameter
	private static final String strSelectPolicy = "{CALL ACCOUNT_INFO_PKG_select_policy(?,?,?,?)}";
	private static final String strEnrollmentList ="{CALL ACCOUNT_INFO_PKG_SELECT_ENROLLMENT_LIST(?,?)}";
	private static final String strGetEnrollment = "{CALL ACCOUNT_INFO_PKG_SELECT_ENROLLMENT(?,?)}";
	private static final String strMemberList = "{CALL ACCOUNT_INFO_PKG_SELECT_MEMBER_LIST(?,?)}";
	private static final String strHistoryList = "{CALL ACCOUNT_INFO_PKG_SELECT_HISTORY_LIST(?,?,?,?,?,?,?,?,?)}";
	private static final String strPreAuthorizationHistoryList = "{CALL AUTHORIZATION_PKG_SELECT_PAT_CLM_HISTORY(?,?,?,?)}";
	private static final String strPreAuthHistoryList = "{CALL AUTHORIZATION_PKG_CREATE_PREAUTH_XML(?,?)}";
	private static final String strClaimHistoryList = "{CALL CLAIM_PKG_CREATE_CLAIM_XML(?,?)}";
	/*private static final String strPreAuthHistoryList = "{CALL ACCOUNT_INFO_PKG_CREATE_PREAUTH_XML(?,?,?)}";*/
	private static final String strPolicyHistoryList = "{CALL ACCOUNT_INFO_PKG_CREATE_POLICY_XML(?,?)}";
	/*private static final String strClaimHistoryList = "{CALL ACCOUNT_INFO_PKG_CREATE_CLAIM_XML(?,?,?)}";*/
	private static final String strSelectEndorsementList = "{CALL ACCOUNT_INFO_PKG_SELECT_ENDORSEMENT_LIST(?,?)}";
	private static final String strCitiEnrolHistoryList = "{CALL ACCOUNT_INFO_PKG_CREATE_CITI_ENROL_HIST_XML(?,?)}";
	private static final String strCitiClmHistoryList = "{CALL ACCOUNT_INFO_PKG_CREATE_CITI_CLM_HIST_XML(?,?)}";
//Added as per KOC 1216B IBM ChangeRequest 
	//account_info_pkg.select_member_buffer_list
	//private static final String strEnrollMemberList  = "{CALL TTKAPP.ACCOUNT_INFO_PKG.SELECT_MEMBER_BUFFER_LIST(?,?,?,?,?,?,?,?,?,?,?)}";	
	private static final String strEnrollMemberList  = "{CALL ACCOUNT_INFO_PKG.SELECT_MEMBER_BUFFER_LIST(?,?,?,?,?,?,?,?,?,?,?)}";	

	//select_buff_mem_list
	//private static final String strBuffMemberList  = "{CALL TTKAPP.ACCOUNT_INFO_PKG.SELECT_BUFF_MEM_LIST(?,?,?,?,?,?,?,?,?)}";
	private static final String strBuffMemberList  = "{CALL ACCOUNT_INFO_PKG.SELECT_BUFF_MEM_LIST(?,?,?,?,?,?,?,?,?)}";

	//account_info_pkg.select_add_buffer
	//private static final String strMemberAddBuffer  = "{CALL TTKAPP.ACCOUNT_INFO_PKG.SELECT_ADD_BUFFER(?,?,?,?,?,?,?)}";
	private static final String strMemberAddBuffer  = "{CALL ACCOUNT_INFO_PKG.SELECT_ADD_BUFFER(?,?,?,?,?,?,?,?,?)}";//added 2 param for hyundai buffer

	//save_mem_buffer_details
	//private static final String strSaveMemberBuffer = "{CALL TTKAPP.ACCOUNT_INFO_PKG.SAVE_MEM_BUFFER_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveMemberBuffer = "{CALL ACCOUNT_INFO_PKG.SAVE_MEM_BUFFER_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//added 3 param buffer for hyundai buffer

	//account_info_pkg.select_add_buffer
	//private static final String strMemberAddBuffer  = "{CALL TTKAPP.ACCOUNT_INFO_PKG.SELECT_ADD_BUFFER(?,?,?,?,?,?,?)}";
	//save_mem_buffer_details
	//private static final String strSaveMemberBuffer = "{CALL TTKAPP.ACCOUNT_INFO_PKG.SAVE_MEM_BUFFER_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    /**
	 * This method returns the Arraylist of PolicyVO's  which contains the details of enrollment policy details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PolicyVO object which contains the details of enrollment policy details
	 * @exception throws TTKException
	 */
	public ArrayList getPolicyList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement oCstmt = null;
		PolicyAccountInfoVO policyAccountInfoVO = null;
		ResultSet rs = null;
		String strSuppressLink[]={"2"}; //2Prev. Policy No. 
        try{
			conn = ResourceManager.getConnection();
			//conn = ResourceManager.getTestConnection();
			oCstmt=(CallableStatement) conn.prepareCall(strPolicyList);
			String[] strArr= (String[]) alSearchCriteria.get(0);
            Array sqlArray=conn.createArrayOf("text",strArr);
            oCstmt.setArray(1,sqlArray );
		//	oCstmt.setPlsqlIndexTable(1, alSearchCriteria.get(0), 13,13,Types.VARCHAR,250);
			oCstmt.setString(2,(String)alSearchCriteria.get(2));
			oCstmt.setString(3,(String)alSearchCriteria.get(3));
			oCstmt.setLong(4 ,(Long.parseLong(alSearchCriteria.get(4).toString())));
			oCstmt.setLong(5 ,(Long.parseLong(alSearchCriteria.get(5).toString())));
			oCstmt.setLong(6,(Long.parseLong(alSearchCriteria.get(1).toString())));//KOC Cigna_insurance_resriction
			oCstmt.registerOutParameter(7,Types.OTHER);
			oCstmt.execute();
			rs = (java.sql.ResultSet)oCstmt.getObject(7);
			if(rs != null){
				while (rs.next()) {
					policyAccountInfoVO = new PolicyAccountInfoVO();

					if(rs.getString("POLICY_SEQ_ID") != null){
						policyAccountInfoVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)

                    if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
                        policyAccountInfoVO.setPolicyGroupSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
                    }//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)

					policyAccountInfoVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					if(rs.getString("PREV_POLICY_SEQ_ID") != null){
						policyAccountInfoVO.setPrevPolicySeqID(new Long(rs.getLong("PREV_POLICY_SEQ_ID")));
					}//end of if(rs.getString("PREV_POLICY_SEQ_ID") != null)
					if(rs.getString("PREV_POLICY_GROUP_SEQ_ID") != null){
						policyAccountInfoVO.setPrevPolicyGroupSeqID(new Long(rs.getLong("PREV_POLICY_GROUP_SEQ_ID")));
					}//end of if(rs.getString("PREV_POLICY_GROUP_SEQ_ID") != null)
					policyAccountInfoVO.setPrevPolicyNbr(TTKCommon.checkNull(rs.getString("PREV_POLICY_NUMBER")));
					policyAccountInfoVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					policyAccountInfoVO.setMemberName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
					policyAccountInfoVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					policyAccountInfoVO.setPolicyTypeDesc(TTKCommon.checkNull(rs.getString("POLICY_TYPE")));
					policyAccountInfoVO.setPolicyTypeID(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));
					policyAccountInfoVO.setPreauthExist(TTKCommon.checkNull(rs.getString("PREAUTH_MADE")));
					policyAccountInfoVO.setClaimExist(TTKCommon.checkNull(rs.getString("CLAIM_MADE")));
					
					policyAccountInfoVO.setsEmiratesID(TTKCommon.checkNull(rs.getString("emirate_id")));
					policyAccountInfoVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					
					if(rs.getString("EFFECTIVE_FROM_DATE") != null){
						policyAccountInfoVO.setStartDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
					}//end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)

					if(rs.getString("EFFECTIVE_TO_DATE") != null){
						policyAccountInfoVO.setEndDate(new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
					}//end of if(rs.getString("EFFECTIVE_TO_DATE") != null)
					
					if(rs.getString("PREV_POLICY_SEQ_ID")==null||rs.getString("PREV_POLICY_GROUP_SEQ_ID")==null)
					{
						policyAccountInfoVO.setSuppressLink(strSuppressLink);
					}//end of if(rs.getString("PREV_POLICY_SEQ_ID")==null||rs.getString("PREV_POLICY_GROUP_SEQ_ID")==null)

					if(rs.getString("INS_STATUS_GENERAL_TYPE_ID") != null){

						if(rs.getString("INS_STATUS_GENERAL_TYPE_ID").equals("POC")) {
							policyAccountInfoVO.setImageName("CancelledIcon");
							policyAccountInfoVO.setImageTitle("Cancelled Policy");
						}//end of if(rs.getString("TPA_STATUS_GENERAL_TYPE_ID") .equals("TPC"))

						if(rs.getString("INS_STATUS_GENERAL_TYPE_ID").equals("FTS")){
							policyAccountInfoVO.setImageName("FreshIcon");
							policyAccountInfoVO.setImageTitle("Fresh Policy");
						}//end of if(rs.getString("TPA_STATUS_GENERAL_TYPE_ID") .equals("TPF"))

						if(rs.getString("INS_STATUS_GENERAL_TYPE_ID").equals("RTS")) {
							policyAccountInfoVO.setImageName("RenewIcon");
							policyAccountInfoVO.setImageTitle("Renewal Policy");
						}//end of if(rs.getString("TPA_STATUS_GENERAL_TYPE_ID") .equals("TPR"))
					}//end of if(rs.getString("TPA_STATUS_GENERAL_TYPE_ID") != null)
                    alResultList.add(policyAccountInfoVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in AccountInfoDAOImpl getPolicyList()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in AccountInfoDAOImpl getPolicyList()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in AccountInfoDAOImpl getPolicyList()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPolicyList(ArrayList alSearchCriteria)

    /**
     * This method returns the MemberDetailVO, which contains the Enrollment details which are populated from the database
     * @param alEnrollment ArrayList which contains seq id for Enrollment or Endorsement flow to get the Enrollment information
     * @return MemberDetailVO object's which contains the details of the Enrollment
     * @exception throws TTKException
     */
    public MemberDetailVO getEnrollment(ArrayList alEnrollment) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        MemberDetailVO memberDetailVO = null;
        MemberAddressVO memberAddressVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetEnrollment);
            cStmtObject.setLong(1,(Long)alEnrollment.get(0));
            cStmtObject.registerOutParameter(2,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if(rs != null){
            	while(rs.next()){
            		memberDetailVO = new MemberDetailVO();
            		memberAddressVO = new MemberAddressVO();

            		if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
            			memberDetailVO.setPolicyGroupSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
            		}//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)

            		if(rs.getString("POLICY_SEQ_ID") != null){
            			memberDetailVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
            		}//end of if(rs.getString("POLICY_SEQ_ID") != null)

            		if(rs.getString("ENR_ADDRESS_SEQ_ID") != null){
            			memberAddressVO.setAddrSeqId(new Long(rs.getLong("ENR_ADDRESS_SEQ_ID")));
            		}//end of if(rs.getString("ENR_ADDRESS_SEQ_ID") != null)

            		memberDetailVO.setOrderNbr(TTKCommon.checkNull(rs.getString("ORDER_NUMBER")));
            		memberDetailVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
            		memberDetailVO.setName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
            		memberDetailVO.setDepartment(TTKCommon.checkNull(rs.getString("DEPARTMENT")));
            		memberDetailVO.setDesignation(TTKCommon.checkNull(rs.getString("DESIGNATION")));

            		if(rs.getString("DATE_OF_JOINING") != null){
            			memberDetailVO.setStartDate(new Date(rs.getTimestamp("DATE_OF_JOINING").getTime()));
            		}//end of if(rs.getString("DATE_OF_JOINING") != null)

            		if(rs.getString("DATE_OF_RESIGNATION") != null){
            			memberDetailVO.setEndDate(new Date(rs.getTimestamp("DATE_OF_RESIGNATION").getTime()));
            		}//end of if(rs.getString("DATE_OF_RESIGNATION") != null)

            		memberAddressVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
            		memberAddressVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
            		memberAddressVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
            		memberAddressVO.setCityCode(TTKCommon.checkNull(rs.getString("CITY_TYPE_ID")));
            		memberAddressVO.setStateCode(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));
            		memberAddressVO.setCountryCode(TTKCommon.checkNull(rs.getString("COUNTRY_ID")));
            		memberAddressVO.setPinCode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
            		memberAddressVO.setHomePhoneNbr(TTKCommon.checkNull(rs.getString("RES_PHONE_NO")));
            		memberAddressVO.setPhoneNbr1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
            		memberAddressVO.setPhoneNbr2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
            		memberAddressVO.setMobileNbr(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
            		memberAddressVO.setFaxNbr(TTKCommon.checkNull(rs.getString("FAX_NO")));
            		memberAddressVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
            		memberDetailVO.setMemberAddressVO(memberAddressVO);
            		memberDetailVO.setBeneficiaryname(TTKCommon.checkNull(rs.getString("BENEFICIARY_NAME")));
            		memberDetailVO.setRelationTypeID(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
            		memberDetailVO.setCustomerNbr(TTKCommon.checkNull(rs.getString("CUSTOMER_NO")));
            		memberDetailVO.setProposalFormYN(TTKCommon.checkNull(rs.getString("PROPOSAL_FORM_YN")));
            		if(rs.getString("DECLARATION_DATE") != null){
            			memberDetailVO.setDeclarationDate(new Date(rs.getTimestamp("DECLARATION_DATE").getTime()));
            		}//end of if(rs.getString("DATE_OF_JOINING") != null)
            		if(rs.getString("BANK_SEQ_ID") != null){
            			memberDetailVO.setBankSeqID(new Long(rs.getLong("BANK_SEQ_ID")));
            		}//end of if(rs.getString("BANK_SEQ_ID") != null)

            		memberDetailVO.setCertificateNbr(TTKCommon.checkNull(rs.getString("CERTIFICATE_NO")));
            		memberDetailVO.setCreditCardNbr(TTKCommon.checkNull(rs.getString("CREDITCARD_NO")));
            		memberDetailVO.setBankAccNbr(TTKCommon.checkNull(rs.getString("BANK_ACCOUNT_NO")));
            		memberDetailVO.setBankName(TTKCommon.checkNull(rs.getString("BANK_NAME")));
            		memberDetailVO.setBranch(TTKCommon.checkNull(rs.getString("BANK_BRANCH")));
            		memberDetailVO.setBankPhone(TTKCommon.checkNull(rs.getString("BANK_PHONE_NO")));
            		memberDetailVO.setMICRCode(TTKCommon.checkNull(rs.getString("BANK_MICR")));
            		if(rs.getString("GROUP_REG_SEQ_ID") != null){
            			memberDetailVO.setGroupRegnSeqID(new Long(rs.getLong("GROUP_REG_SEQ_ID")));
            		}//end of if(rs.getString("GROUP_REG_SEQ_ID") != null)
            		memberDetailVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));

            		memberDetailVO.setStopPatClmYN(TTKCommon.checkNull(rs.getString("STOP_PAT_CLM_PROCESS_YN")));
            		if(rs.getString("RECIEVED_AFTER") != null){
            			memberDetailVO.setDeclarationDate(new Date(rs.getTimestamp("RECIEVED_AFTER").getTime()));
            		}//end of if(rs.getString("RECIEVED_AFTER") != null)
            	}//end of while(rs.next())
            }//end of if(rs != null)
            return memberDetailVO;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
        }//end of catch (Exception exp)
        finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in AccountInfoDAOImpl getEnrollment()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in AccountInfoDAOImpl getEnrollment()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in AccountInfoDAOImpl getEnrollment()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getEnrollment(ArrayList alEnrollment)

	/**
     * This method returns the MemberDetailVO which contains the Member information
     * @param alMember ArrayList which contains seq id for Enrollment or Endorsement flow to get the Member information
     * @return MemberDetailVO the contains the Member information
     * @exception throws TTKException
     */
    public PolicyAccountInfoDetailVO getPolicy(ArrayList alMember) throws TTKException
    {
    	PolicyAccountInfoDetailVO policyAccountInfoDetailVO = null;
    	AddressVO familyAddressVO = null;
        AddressVO insuranceAddressVO = null;
        AddressVO groupAddressVO = null;


        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectPolicy);
            cStmtObject.setLong(1,(Long)alMember.get(0));
            if(alMember.get(1) != null){
            	cStmtObject.setLong(2,(Long)alMember.get(1));
            }//end of if(alMember.get(1) != null)
            else{
            	cStmtObject.setLong(2,0);
            }//end of else
            cStmtObject.setString(3,(String)alMember.get(2));
            cStmtObject.registerOutParameter(4,Types.OTHER);//ROW_PROCESSED
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);

            if (rs!=null)
            {
                while(rs.next())
                 {
                	policyAccountInfoDetailVO = new PolicyAccountInfoDetailVO();
                	familyAddressVO = new AddressVO();
                	insuranceAddressVO = new AddressVO();
                	groupAddressVO = new AddressVO();

                    if(rs.getString("POLICY_SEQ_ID")!=null){
                    	policyAccountInfoDetailVO.setPolicySeqID(new Long(rs.getInt("POLICY_SEQ_ID")));
                    }//end of if(rs.getString("policy_seq_id")!=null)
                    policyAccountInfoDetailVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                    policyAccountInfoDetailVO.setProductName(TTKCommon.checkNull(rs.getString("PRODUCT_NAME")));
                    if(rs.getString("INSURED_NAME")!=null){
                    	policyAccountInfoDetailVO.setMemberName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
                    }
                    if(rs.getString("ENROL_TYPE_ID").equalsIgnoreCase("COR")||rs.getString("ENROL_TYPE_ID").equalsIgnoreCase("NCR"))
                    {
                    	policyAccountInfoDetailVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
                    	policyAccountInfoDetailVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
                    }else//only for the IND and ING
                    {
                    	policyAccountInfoDetailVO.setHomePhoneNbr(TTKCommon.checkNull(rs.getString("RES_PHONE_NO")));
                    	policyAccountInfoDetailVO.setMobileNbr(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
                    }
                	//policyAccountInfoDetailVO.setCompanyCode(TTKCommon.checkNull(rs.getString("ins_comp_code_number")));
                    policyAccountInfoDetailVO.setEndorsementCnt(rs.getString("ENDORSEMENT_COUNT")!=null ? new Integer(rs.getInt("ENDORSEMENT_COUNT")):null);
                	policyAccountInfoDetailVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
                	policyAccountInfoDetailVO.setPolicyTypeDesc(TTKCommon.checkNull(rs.getString("POLICY_TYPE")));
                	policyAccountInfoDetailVO.setPolicySubType(TTKCommon.checkNull(rs.getString("POLICY_SUB_TYPE")));
                	policyAccountInfoDetailVO.setStartDate(rs.getString("EFFECTIVE_FROM_DATE")!=null ? new Date(rs.getTimestamp("effective_from_date").getTime()):null);
                	policyAccountInfoDetailVO.setEndDate(rs.getString("EFFECTIVE_TO_DATE")!=null ? new Date(rs.getTimestamp("effective_to_date").getTime()):null);
                	policyAccountInfoDetailVO.setTotalSumInsured(rs.getString("TOTAL_SUM_INSURED")!=null ? new BigDecimal(rs.getString("total_sum_insured")):null);
                	policyAccountInfoDetailVO.setTPAStatusType(TTKCommon.checkNull(rs.getString("TERM_STATUS")));
                	policyAccountInfoDetailVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
                	policyAccountInfoDetailVO.setZoneCode(TTKCommon.checkNull(rs.getString("ZONE_CODE")));
                	policyAccountInfoDetailVO.setPolicyTypeID(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));
                	familyAddressVO.setAddress1(TTKCommon.checkNull(rs.getString("FAMILY_ADDRESS_1")));
                	familyAddressVO.setAddress2(TTKCommon.checkNull(rs.getString("FAMILY_ADDRESS_2")));
                	familyAddressVO.setAddress3(TTKCommon.checkNull(rs.getString("FAMILY_ADDRESS_3")));
                	familyAddressVO.setStateName(TTKCommon.checkNull(rs.getString("FAMILY_STATE")));
                	familyAddressVO.setCityDesc(TTKCommon.checkNull(rs.getString("FAMILY_CITY")));
                	familyAddressVO.setPinCode(TTKCommon.checkNull(rs.getString("FAMILY_PINCODE")));
                	policyAccountInfoDetailVO.setProposalDate(rs.getString("PROPOSAL_DATE")!=null ? new Date(rs.getTimestamp("PROPOSAL_DATE").getTime()):null);
                	policyAccountInfoDetailVO.setFamilyAddressObj(familyAddressVO);


                	insuranceAddressVO.setAddress1(TTKCommon.checkNull(rs.getString("INS_ADDRESS_1")));
                	insuranceAddressVO.setAddress2(TTKCommon.checkNull(rs.getString("INS_ADDRESS_2")));
                	insuranceAddressVO.setAddress3(TTKCommon.checkNull(rs.getString("ins_address_3")));
                	insuranceAddressVO.setStateName(TTKCommon.checkNull(rs.getString("ins_state_name")));
                	insuranceAddressVO.setCityDesc(TTKCommon.checkNull(rs.getString("ins_city")));
                	insuranceAddressVO.setPinCode(TTKCommon.checkNull(rs.getString("ins_pin_code")));
                	policyAccountInfoDetailVO.setInsuranceAddressObj(insuranceAddressVO);


                	groupAddressVO.setAddress1(TTKCommon.checkNull(rs.getString("group_address_1")));
                	groupAddressVO.setAddress2(TTKCommon.checkNull(rs.getString("group_address_2")));
                	groupAddressVO.setAddress3(TTKCommon.checkNull(rs.getString("group_address_3")));
                	groupAddressVO.setStateName(TTKCommon.checkNull(rs.getString("group_state_name")));
                	groupAddressVO.setCityDesc(TTKCommon.checkNull(rs.getString("group_city")));
                	groupAddressVO.setPinCode(TTKCommon.checkNull(rs.getString("group_pin_code")));
                	policyAccountInfoDetailVO.setGroupAddressObj(groupAddressVO);

                    }
            }// End of if (rs!=null)
          }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
        }//end of catch (Exception exp)
        finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in AccountInfoDAOImpl getMember()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in AccountInfoDAOImpl getMember()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in AccountInfoDAOImpl getMember()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return policyAccountInfoDetailVO;
    }//end of getMember(ArrayList alMember)

    /**
     * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of MemberVO'S object's which contains the details of the Member
     * @exception throws TTKException
     */
    public ArrayList getMemberList(ArrayList alSearchCriteria) throws TTKException
    {
        Collection<Object> alBatchPolicyList = new ArrayList<Object>();
        MemberVO memberVO = new MemberVO();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try {
            conn = ResourceManager.getConnection();
        	//conn = ResourceManager.getTestConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strEnrollmentList);
            if(alSearchCriteria.get(0)!=null){
                cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
            }//end of if(alSearchCriteria.get(0)!=null)
            else{
                cStmtObject.setLong(1,0);
            }//end of else

            cStmtObject.registerOutParameter(2,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if (rs!=null)
            {
                while (rs.next())
                {
                    memberVO = new MemberVO();
                    memberVO.setPolicyGroupSeqID(rs.getString("POLICY_GROUP_SEQ_ID")!=null ? new Long(rs.getInt("POLICY_GROUP_SEQ_ID")):null);
                    memberVO.setName(TTKCommon.checkNull(rs.getString("ENROLLMENT")));
                    memberVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
                    memberVO.setPolicySeqID(rs.getString("POLICY_SEQ_ID")!=null ? new Long(rs.getInt("POLICY_SEQ_ID")):null);
                    alBatchPolicyList.add(memberVO);
                }// End of while (rs.next())
            }// End of if (rs!=null)
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
        }//end of catch (Exception exp)
        finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in AccountInfoDAOImpl getMemberList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in AccountInfoDAOImpl getMemberList()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in AccountInfoDAOImpl getMemberList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return (ArrayList) alBatchPolicyList;
    }//end of getMemberList(ArrayList alSearchCriteria)

    /**
     * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
     * @param alMember ArrayList which contains seq id for Enrollment or Endorsement flow to get the Policy Details
     * @return ArrayList of MemberVO'S object's which contains the details of the Member
     * @exception throws TTKException
     */
    public ArrayList getDependentList(ArrayList alMember) throws TTKException
    {
    	Collection<Object> alBatchPolicyList = new ArrayList<Object>();
        MemberVO memberVO = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try {
            conn = ResourceManager.getConnection();
        	//conn = ResourceManager.getTestConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strMemberList);
            cStmtObject.setLong(1,(Long)alMember.get(0));
            cStmtObject.registerOutParameter(2,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if (rs!=null)
            {
                while (rs.next())
                {
                    memberVO = new MemberVO();
                    memberVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
                    memberVO.setMemberSeqID(rs.getString("MEMBER_SEQ_ID")!=null ? new Long(rs.getLong("MEMBER_SEQ_ID")):null);
                    memberVO.setName(TTKCommon.checkNull(rs.getString("MEMBER")));
                    alBatchPolicyList.add(memberVO);
                }// End of while (rs.next())
            }// End of if (rs!=null)
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
        }//end of catch (Exception exp)
        finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in AccountInfoDAOImpl getDependentList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in AccountInfoDAOImpl getDependentList()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in AccountInfoDAOImpl getDependentList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
       return (ArrayList) alBatchPolicyList;
    }//end of getDependentList(ArrayList alMember)

    /**
     * This method returns the Arraylist of PreAuthHistoryVO/PolicyHistoryVO's  which contains History Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthHistoryVO/PolicyHistoryVO object which contains History Details
     * @exception throws TTKException
     */
    public ArrayList getHistoryList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PreAuthHistoryVO preAuthHistoryVO = null;
		PolicyHistoryVO policyHistoryVO = null;
        BufferVO bufferVO = null;
        EndorsementHistoryVO endorsementHistoryVO = null;
        ClaimantHistoryVO claimantHistoryVO = null;
        CitibankHistoryVO citibankHistoryVO = null;
		InvestigationHistoryVO investigationHistoryVO = null; //koc11 koc 11
		String strHistoryType = "";
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strHistoryList);
			strHistoryType = alSearchCriteria.get(0).toString();
			cStmtObject.setString(1,strHistoryType);//HPR-> Pre-Authorization , POL->Policy , HCL-> Claims
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setLong(7,(Long.parseLong(alSearchCriteria.get(6).toString())));
			cStmtObject.setLong(8,(Long.parseLong(alSearchCriteria.get(7).toString())));
			cStmtObject.registerOutParameter(9,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(9);
			if(rs != null){
				while(rs.next()){
					if(strHistoryType.equalsIgnoreCase("HPR")){
						preAuthHistoryVO = new PreAuthHistoryVO();

						if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null){
							preAuthHistoryVO.setEnrollDtlSeqID(new Long(rs.getLong("PAT_ENROLL_DETAIL_SEQ_ID")));
						}//end of if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null)

						if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
							preAuthHistoryVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
						}//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

						preAuthHistoryVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("PRE_AUTH_NUMBER")));
						preAuthHistoryVO.setAuthorizationNO(TTKCommon.checkNull(rs.getString("AUTH_NUMBER")));
						preAuthHistoryVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
						preAuthHistoryVO.setApprovedAmount(TTKCommon.checkNull(rs.getString("TOTAL_APP_AMOUNT")));

						/*if(rs.getString("PAT_RECEIVED_DATE") != null){
							preAuthHistoryVO.setReceivedDate(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime()));
						}//end of if(rs.getString("PAT_RECEIVED_DATE") != null)
*/
						if(rs.getString("LIKELY_DATE_OF_HOSPITALIZATION") != null){
							preAuthHistoryVO.setClaimAdmnDate(new Date(rs.getTimestamp("LIKELY_DATE_OF_HOSPITALIZATION").getTime()));
                        }//end of if(rs.getString("LIKELY_DATE_OF_HOSPITALIZATION") != null)
						
						preAuthHistoryVO.setStatusDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
						preAuthHistoryVO.setEnhancedYN(TTKCommon.checkNull(rs.getString("PAT_ENHANCED_YN")));

						if(TTKCommon.checkNull(rs.getString("PAT_ENHANCED_YN")).equals("Y")){
							preAuthHistoryVO.setPreAuthImageName("OriginalIcon");
							preAuthHistoryVO.setPreAuthImageTitle("Enhance Pre-Auth");
						}//end of if(TTKCommon.checkNull(rs.getString("PAT_ENHANCED_YN")).equals("Yes"))
						alResultList.add(preAuthHistoryVO);
					}//end of if(strHistoryType.equalsIgnoreCase("HPR"))

					if(strHistoryType.equalsIgnoreCase("POL")){
						policyHistoryVO = new PolicyHistoryVO();

                        if(rs.getString("POLICY_SEQ_ID") != null){
                            policyHistoryVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
                        }//end of if(rs.getString("POLICY_SEQ_ID") != null)

						policyHistoryVO.setPolicyNo(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));

						if(rs.getString("EFFECTIVE_FROM_DATE") != null){
							policyHistoryVO.setStartDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
						}//end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)

						if(rs.getString("EFFECTIVE_TO_DATE") != null){
							policyHistoryVO.setEndDate(new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
						}//end of if(rs.getString("EFFECTIVE_TO_DATE") != null)

						if(rs.getString("MEMBER_SEQ_ID") != null){
							policyHistoryVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
						}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

						alResultList.add(policyHistoryVO);
					}//end of if(strHistoryType.equalsIgnoreCase("POL"))
                    if(strHistoryType.equalsIgnoreCase("BST")){ //Buffor History
                        bufferVO = new BufferVO();
                        bufferVO.setBufferNbr(TTKCommon.checkNull(rs.getString("BUFFER_REQ_NO")));
                        bufferVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
                        bufferVO.setClaimantName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
                        if(rs.getString("BUFFER_REQ_DATE") != null){
                            bufferVO.setRequestedDate(new Date(rs.getTimestamp("BUFFER_REQ_DATE").getTime()));
                        }//end of  if(rs.getString("buffer_req_date")
                        bufferVO.setRequestedAmt(TTKCommon.getBigDecimal(rs.getString("BUFFER_REQ_AMOUNT")));
                        bufferVO.setRemarks(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                        if(rs.getString("BUFFER_APPROVED_DATE") != null){
                            bufferVO.setReceivedDate(new Date(rs.getTimestamp("BUFFER_APPROVED_DATE").getTime()));
                        }//end of  if(rs.getString("buffer_req_date")
                        bufferVO.setApprovedAmt(TTKCommon.getBigDecimal(rs.getString("BUFFER_APP_AMOUNT")));
                        alResultList.add(bufferVO);
                    }//end of if(strHistoryType.equalsIgnoreCase("BUF"))
                    if(strHistoryType.equalsIgnoreCase("ENS")){  //Endorsement History
                        endorsementHistoryVO = new EndorsementHistoryVO();
                        endorsementHistoryVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("ENROLLMENT_NUMBER")));
                        endorsementHistoryVO.setMemberName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
                        endorsementHistoryVO.setEndorsementNbr(TTKCommon.checkNull(rs.getString("CUSTOMER_ENDORSEMENT_NUMBER")));
                        if(rs.getString("ENDORSEMENT_DATE") != null){
                            endorsementHistoryVO.setEffectiveDate(new Date(rs.getTimestamp("ENDORSEMENT_DATE").getTime()));
                        }//end of if(rs.getString("ENDORSEMENT_DATE") != null)

                        alResultList.add(endorsementHistoryVO);
                    }//end of if(strHistoryType.equalsIgnoreCase("POL"))
                    if(strHistoryType.equalsIgnoreCase("HCL")){ //Claim History
                        claimantHistoryVO = new ClaimantHistoryVO();

                        claimantHistoryVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
                        claimantHistoryVO.setClaimNo(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
                        claimantHistoryVO.setClaimantName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));

                        if(rs.getString("RCVD_DATE") != null){
                            claimantHistoryVO.setReceivedDate(new Date(rs.getTimestamp("RCVD_DATE").getTime()));
                        }//end of if(rs.getString("RCVD_DATE") != null)

                        if(rs.getString("REQUESTED_AMOUNT") != null){
                        claimantHistoryVO.setRequestedAmount(new BigDecimal(rs.getString("REQUESTED_AMOUNT")));
                        }//end of if(rs.getString("REQUESTED_AMOUNT") != null)

                        claimantHistoryVO.setRemarks(TTKCommon.checkNull(rs.getString("DESCRIPTION")));

                        if(rs.getString("TOTAL_APP_AMOUNT") != null){
                        claimantHistoryVO.setApprovedAmount(new BigDecimal(rs.getString("TOTAL_APP_AMOUNT")));
                        }//end of if(rs.getString("TOTAL_APP_AMOUNT") != null)
                        else{
                        	claimantHistoryVO.setApprovedAmount(new BigDecimal("0.00"));
                        }//end of else
                        
                        if(rs.getString("DATE_OF_ADMISSION") != null){
                            claimantHistoryVO.setClaimAdmnDate(new Date(rs.getTimestamp("DATE_OF_ADMISSION").getTime()));
                        }//end of if(rs.getString("DATE_OF_ADMISSION") != null)
                        
                        if(rs.getString("DATE_OF_DISCHARGE") != null){
                            claimantHistoryVO.setClmDischargeDate(new Date(rs.getTimestamp("DATE_OF_DISCHARGE").getTime()));
                        }//end of if(rs.getString("DATE_OF_DISCHARGE") != null)
                        
                        claimantHistoryVO.setPolicyNo(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                        alResultList.add(claimantHistoryVO);
                    }//end of if(strHistoryType.equalsIgnoreCase("HCL"))
                    if(strHistoryType.equalsIgnoreCase("CEH")){
                    	citibankHistoryVO = new CitibankHistoryVO();

                        if(rs.getString("CITIBANK_ENR_SEQ_ID") != null){
                        	citibankHistoryVO.setCitiEnrolSeqID(new Long(rs.getLong("CITIBANK_ENR_SEQ_ID")));
                        }//end of if(rs.getString("CITIBANK_ENR_SEQ_ID") != null)
                        
                        citibankHistoryVO.setCustCode(TTKCommon.checkNull(rs.getString("CUSTCODE")));
                        citibankHistoryVO.setOrderNbr(TTKCommon.checkNull(rs.getString("ORD_NUM")));

						alResultList.add(citibankHistoryVO);
					}//end of if(strHistoryType.equalsIgnoreCase("CEH"))
                    if(strHistoryType.equalsIgnoreCase("CCH")){
                    	citibankHistoryVO = new CitibankHistoryVO();

                        if(rs.getString("CITIBANK_CLM_SEQ_ID") != null){
                        	citibankHistoryVO.setCitiClmSeqID(new Long(rs.getLong("CITIBANK_CLM_SEQ_ID")));
                        }//end of if(rs.getString("CITIBANK_CLM_SEQ_ID") != null)
                        
                        citibankHistoryVO.setCustCode(TTKCommon.checkNull(rs.getString("CUSTCODE")));
                        citibankHistoryVO.setClaimNbr(TTKCommon.checkNull(rs.getString("CLAIMNO")));
                        citibankHistoryVO.setClaimYear(TTKCommon.checkNull(rs.getString("CLAIMYEAR")));
						alResultList.add(citibankHistoryVO);
					}//end of if(strHistoryType.equalsIgnoreCase("CCH"))
					if(strHistoryType.equalsIgnoreCase("HIN")){
                    	investigationHistoryVO = new InvestigationHistoryVO();
                    	investigationHistoryVO.setInvestigationNo(TTKCommon.checkNull(rs.getString("INVESTIGATION_ID")));
                         if(rs.getString("INVESTIGATED_DATE") != null){    
                    		 investigationHistoryVO.setHistoryInvestDate(TTKCommon.checkNull(rs.getString("INVESTIGATED_DATE")));
                    		//investigationHistoryVO.setHistoryInvestDate(new Date(rs.getTimestamp("INVESTIGATED_DATE").getTime()));
						}//end of if(rs.getString("INVESTIGATED_DATE") != null)
                    	investigationHistoryVO.setInvestigatedBy((TTKCommon.checkNull(rs.getString("INVESTIGATED_BY"))));
                                                 
                    		
                    		 
                    	investigationHistoryVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                        if(rs.getString("CLM_APR_AMT") != null)
                    	{
                                                        
                    		 investigationHistoryVO.setHistoryClaimAmt(TTKCommon.checkNull(rs.getString("CLM_APR_AMT")));
                    	//investigationHistoryVO.setHistoryClaimAmt(new BigDecimal(rs.getString("CLM_APR_AMT")));
                    	}
                        investigationHistoryVO.setInvestSeqID(new Long(rs.getString("INVEST_SEQ_ID")));
                    	if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
                    		investigationHistoryVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
                        }//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)
                    	if(rs.getString("CLAIM_SEQ_ID") != null){
                    		investigationHistoryVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
                        }//end of if(rs.getString("CLAIM_SEQ_ID") != null)
                    	alResultList.add(investigationHistoryVO);
                    }//end of if(strHistoryType.equalsIgnoreCase("HIN"))
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "history");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "history");
		}//end of catch (Exception exp)
		finally
		{
			// Nested Try Catch to ensure resource closure 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in AccountInfoDAOImpl getHistoryList()",sqlExp);
					throw new TTKException(sqlExp, "history");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in AccountInfoDAOImpl getHistoryList()",sqlExp);
						throw new TTKException(sqlExp, "history");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in AccountInfoDAOImpl getHistoryList()",sqlExp);
							throw new TTKException(sqlExp, "history");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "history");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getHistoryList(ArrayList alSearchCriteria)
    
    /*public ArrayList<String[]> getHistoryList(Long memberSeqID,
			String authType, String preauthorclaimNumber) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
	    ResultSet ars = null;
		ArrayList<String[]> authList = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strPreAuthorizationHistoryList);
			cStmtObject.setLong(1, memberSeqID);
			cStmtObject.setString(2, authType);
			if(preauthorclaimNumber!= null){
				cStmtObject.setString(3, preauthorclaimNumber);
			}else{
				cStmtObject.setString(3, null);	
			}
			cStmtObject.registerOutParameter(4, Types.OTHER);
			cStmtObject.execute();
			ars = (java.sql.ResultSet) cStmtObject.getObject(4);

			if (ars != null) {
				authList = new ArrayList<String[]>();
				while (ars.next()) {
					String authSeqId = "";
					String preAuthNo = "";
					String authNo = "";
					if ("PAT".equals(authType)) {
						authSeqId = ars.getLong("PAT_AUTH_SEQ_ID") == 0 ? ""
								: ars.getLong("PAT_AUTH_SEQ_ID") + "";
						preAuthNo = ars.getString("PRE_AUTH_NUMBER") == null ? ""
								: ars.getString("PRE_AUTH_NUMBER");
						authNo = ars.getString("AUTH_NUMBER") == null ? ""
								: ars.getString("AUTH_NUMBER");
					} else if ("CLM".equals(authType)) {
						authSeqId = ars.getLong("CLAIM_SEQ_ID") == 0 ? "" : ars
								.getLong("CLAIM_SEQ_ID") + "";
						preAuthNo = ars.getString("CLAIM_NUMBER") == null ? ""
								: ars.getString("CLAIM_NUMBER");
						authNo = ars.getString("settlement_number") == null ? ""
								: ars.getString("settlement_number");
					}
					
					String hospitalName=ars.getString("HOSP_NAME")==null?"":ars.getString("HOSP_NAME");
					String approvedAmt=ars.getBigDecimal("TOT_APPROVED_AMOUNT")==null?"":ars.getBigDecimal("TOT_APPROVED_AMOUNT").toString();
					String admissionDate=ars.getDate("ADMISSION_DATE")==null?"":ars.getDate("ADMISSION_DATE").toString();
					String status=ars.getString("PAT_STATUS")==null?"":ars.getString("PAT_STATUS");
					
					
					String approvedDate=ars.getString("approved_date")==null?"":ars.getString("approved_date");
					String paymentDate=ars.getString("payment_date")==null?"":ars.getString("payment_date");
					String chonologicalNo=ars.getString("chronic_number")==null?"":ars.getString("chronic_number");
					 
					String referenceNo=ars.getString("REFERENCE_NUM")==null?"":ars.getString("REFERENCE_NUM");

					authList.add(new String[]{authSeqId,preAuthNo,authNo,hospitalName,approvedAmt,admissionDate,status,approvedDate,paymentDate,chonologicalNo,referenceNo});			
				
				}
			}
			return authList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			 Nested Try Catch to ensure resource closure 
			try // First try closing the result set
			{
				try {
					if (ars != null)
						ars.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in PreAuthDAOImpl getPreauthHistoryList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{

					try {
						if (cStmtObject != null)
							cStmtObject.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in PreAuthDAOImpl getPreauthHistoryList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null)
								conn.close();
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in PreAuthDAOImpl getPreauthHistoryList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				ars = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getPreauthHistoryList(Long memberSeqID)
*/
    /**
     * This method returns the XML Document of PreAuthHistory/PolicyHistory/ClaimsHistory which contains History Details
     * @param strHistoryType, lngSeqId, lngEnrollDtlSeqId which web board values
     * @return Document of PreAuthHistory/PolicyHistory/ClaimsHistory XML object
     * @exception throws TTKException
     */
    /*public Document getHistory(String strHistoryType, Long lngSeqId, Long lngEnrollDtlSeqId)throws TTKException
    {
        Connection conn = null;
        CallableStatement cStmtObject = null;
        Document doc = null;
        SQLXML xml = null;

        try
        {
            conn = ResourceManager.getConnection();
            if(strHistoryType.equals("HPR"))
            {
            	cStmtObject = (CallableStatement)conn.prepareCall(strPreAuthHistoryList);
                cStmtObject.setLong(1,lngSeqId);//ENH-> enhancement, HPR-> Pre-Authorization , POL->Policy , HCL-> Claims
    			cStmtObject.setLong(2,lngEnrollDtlSeqId);// Mandatory ENH-> PAT_ENROLL_DETAIL_SEQ_ID, HPR-> MEMBER_SEQ_ID , POL->POLICY_SEQ_ID , HCL-> CLAIM_SEQ_ID
                cStmtObject.registerOutParameter(3,Types.SQLXML);
                cStmtObject.execute();
                xml = cStmtObject.getSQLXML(3);
            }//end of if(strHistoryType.equals("HPR"))
            else if(strHistoryType.equals("POL"))
            {
            	cStmtObject = (CallableStatement)conn.prepareCall(strPolicyHistoryList);
                cStmtObject.setLong(1,lngSeqId);//ENH-> enhancement, HPR-> Pre-Authorization , POL->Policy , HCL-> Claims
                cStmtObject.registerOutParameter(2,Types.SQLXML);
                cStmtObject.execute();
                xml = cStmtObject.getSQLXML(2);
            }//end of if(strHistoryType.equals("POL"))
            else if(strHistoryType.equals("HCL"))
            {
            	cStmtObject = (CallableStatement)conn.prepareCall(strClaimHistoryList);
                cStmtObject.setLong(1,lngSeqId);//ENH-> enhancement, HPR-> Pre-Authorization , POL->Policy , HCL-> Claims

                if(lngEnrollDtlSeqId != null){
                	 cStmtObject.setLong(2,lngEnrollDtlSeqId);// Mandatory ENH-> PAT_ENROLL_DETAIL_SEQ_ID, HPR-> MEMBER_SEQ_ID , POL->POLICY_SEQ_ID , HCL-> CLAIM_SEQ_ID
                    }//end of if(rs.getString("TOTAL_APP_AMOUNT") != null)
                    else{
                    	cStmtObject.setLong(2,0);
                    }

                cStmtObject.registerOutParameter(3,Types.SQLXML);
                cStmtObject.execute();
                xml = cStmtObject.getSQLXML(3);
            }//end of if(strHistoryType.equals("HCL"))
            else if(strHistoryType.equals("CEH")){
            	cStmtObject = (CallableStatement)conn.prepareCall(strCitiEnrolHistoryList);
            	cStmtObject.setLong(1,lngSeqId);
                cStmtObject.registerOutParameter(2,Types.SQLXML);
               cStmtObject.execute();
               xml = cStmtObject.getSQLXML(2);
            }//end of else if(strHistoryType.equals("CEH"))
            else if(strHistoryType.equals("CCH")){
            	cStmtObject = (CallableStatement)conn.prepareCall(strCitiClmHistoryList);
            	cStmtObject.setLong(1,lngSeqId);
                cStmtObject.registerOutParameter(2,Types.SQLXML);
               cStmtObject.execute();
               xml = cStmtObject.getSQLXML(2);
            }//end of else if(strHistoryType.equals("CCH"))

            String XMLPRINT=xml.getString();
            SAXReader saxReader = new SAXReader();
            doc = xml != null ? saxReader.read(xml.getCharacterStream()):null;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "history");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "history");
        }//end of catch (Exception exp)
        finally
		{
        	 Nested Try Catch to ensure resource closure 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in AccountInfoDAOImpl getHistory()",sqlExp);
        			throw new TTKException(sqlExp, "history");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in AccountInfoDAOImpl getHistory()",sqlExp);
        				throw new TTKException(sqlExp, "history");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "history");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return doc;
    }//end of getHistory(String strHistoryType, long lngSeqId, long lngEnrollDtlSeqId)
*/
    public Document getPreAuthHistory(Long lngSeqId) throws TTKException {
        Connection conn = null;
        CallableStatement cStmtObject = null;
        Document doc = null;
    		try {
    			conn = ResourceManager.getConnection();

    			cStmtObject = conn.prepareCall(strPreAuthHistoryList);
    			cStmtObject.setLong(1, lngSeqId);
    			cStmtObject.registerOutParameter(2, Types.SQLXML);
                cStmtObject.execute();
    			SAXReader saxReader = new SAXReader();
    			doc = saxReader.read(cStmtObject.getSQLXML(2).getCharacterStream());

    		}// end of try
    		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "history");
    		}// end of catch (SQLException sqlExp)
    		catch (Exception exp) {
            throw new TTKException(exp, "history");
    		}// end of catch (Exception exp)
    		finally {
    			/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
    				try {
    					if (cStmtObject != null)
    						cStmtObject.close();
    				}// end of try
    				catch (SQLException sqlExp) {
    					log.error(
    							"Error while closing the Statement in PreAuthDAOImpl getPreAuthHistory()",
    							sqlExp);
        			throw new TTKException(sqlExp, "history");
    				}// end of catch (SQLException sqlExp)
    				finally // Even if statement is not closed, control reaches
    						// here. Try closing the connection now.
        		{
    					try {
    						if (conn != null)
    							conn.close();
    					}// end of try
    					catch (SQLException sqlExp) {
    						log.error(
    								"Error while closing the Connection in PreAuthDAOImpl getPreAuthHistory()",
    								sqlExp);
        				throw new TTKException(sqlExp, "history");
    					}// end of catch (SQLException sqlExp)
    				}// end of finally Connection Close
    			}// end of try
    			catch (TTKException exp) {
        		throw new TTKException(exp, "history");
    			}// end of catch (TTKException exp)
    			finally // Control will reach here in anycase set null to the
    					// objects
        	{
        		cStmtObject = null;
        		conn = null;
    			}// end of finally
    		}// end of finally
        return doc;
    	}// end of getPreAuthHistory(String strHistoryType, long lngSeqId, long
    		// lngEnrollDtlSeqId)

    	public Document getClaimHistory(Long lngSeqId) throws TTKException {
        Connection conn = null;
        CallableStatement cStmtObject = null;
        Document doc = null;
    		try {
    			conn = ResourceManager.getConnection();

    			cStmtObject = conn.prepareCall(strClaimHistoryList);
    			cStmtObject.setLong(1, lngSeqId);
    			cStmtObject.registerOutParameter(2, Types.SQLXML);
                cStmtObject.execute();
    			SAXReader saxReader = new SAXReader();
    			doc = saxReader.read(cStmtObject.getSQLXML(2).getCharacterStream());

    		}// end of try
    		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "history");
    		}// end of catch (SQLException sqlExp)
    		catch (Exception exp) {
            throw new TTKException(exp, "history");
    		}// end of catch (Exception exp)
    		finally {
    			/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
    				try {
    					if (cStmtObject != null)
    						cStmtObject.close();
    				}// end of try
    				catch (SQLException sqlExp) {
    					log.error(
    							"Error while closing the Statement in PreAuthDAOImpl getClaimHistory()",
    							sqlExp);
        			throw new TTKException(sqlExp, "history");
    				}// end of catch (SQLException sqlExp)
    				finally // Even if statement is not closed, control reaches
    						// here. Try closing the connection now.
        		{
    					try {
    						if (conn != null)
    							conn.close();
    					}// end of try
    					catch (SQLException sqlExp) {
    						log.error(
    								"Error while closing the Connection in PreAuthDAOImpl getClaimHistory()",
    								sqlExp);
        				throw new TTKException(sqlExp, "history");
    					}// end of catch (SQLException sqlExp)
    				}// end of finally Connection Close
    			}// end of try
    			catch (TTKException exp) {
        		throw new TTKException(exp, "history");
    			}// end of catch (TTKException exp)
    			finally // Control will reach here in anycase set null to the
    					// objects
        	{
        		cStmtObject = null;
        		conn = null;
    			}// end of finally
    		}// end of finally
        return doc;
    	}// end of getClaimHistory(String strHistoryType, long lngSeqId, long
    		// lngEnrollDtlSeqId)
    
    /**
     * This method returns the Arraylist of EndorsementVO  which contains endorsement detail
     * @param lngSeqId  which web board values
     * @return ArrayList of EndorsementVO object which contains endorsement detail
     * @exception throws TTKException
     */
    public ArrayList getEndorsementList(long lngSeqId) throws TTKException
    {
        Collection<Object> alEndorsementList = new ArrayList<Object>();
        EndorsementVO endorsementVO = new EndorsementVO();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try {
            conn = ResourceManager.getConnection();
        	//conn = ResourceManager.getTestConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectEndorsementList);
            cStmtObject.setLong(1,lngSeqId);
            cStmtObject.registerOutParameter(2,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if (rs!=null)
            {
                while (rs.next())
                {
                	endorsementVO = new EndorsementVO();
                	endorsementVO.setEndorsementSeqID(rs.getString("ENDORSEMENT_SEQ_ID")!=null ? new Long(rs.getInt("ENDORSEMENT_SEQ_ID")):null);
                	endorsementVO.setEndorsementNbr(TTKCommon.checkNull(rs.getString("endorsement_number")));
                	endorsementVO.setCustEndorsementNbr(TTKCommon.checkNull(rs.getString("cust_endorsement_number")));
                	endorsementVO.setEndorsementType(TTKCommon.checkNull(rs.getString("endorse_general_type_id")));
                	if(rs.getTimestamp("received_date") != null)
                	{
                		endorsementVO.setRecdDate(new java.util.Date(rs.getTimestamp("received_date").getTime()));
                	}//end of if(rs.getTimestamp("received_date") != null)
                	if(rs.getTimestamp("effective_date") != null)
                	{
                    	endorsementVO.setEffectiveDate(new java.util.Date(rs.getTimestamp("effective_date").getTime()));
                	}//end of if(rs.getTimestamp("effective_date") != null)
                    endorsementVO.setAddMemberCnt(rs.getString("add_no_of_members")!=null ? new Integer(rs.getInt("add_no_of_members")):null);
                    endorsementVO.setUpdateMemberCnt(rs.getString("mod_no_of_members")!=null ? new Integer(rs.getInt("mod_no_of_members")):null);
                    endorsementVO.setDeletedMemberCnt(rs.getString("del_no_of_members")!=null ? new Integer(rs.getInt("del_no_of_members")):null);
                    endorsementVO.setEndorsementCompletedYN(TTKCommon.checkNull(rs.getString("completed_yn")));
                    alEndorsementList.add(endorsementVO);

                }// End of while (rs.next())
            }// End of if (rs!=null)
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
        }//end of catch (Exception exp)
        finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in AccountInfoDAOImpl getMemberList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in AccountInfoDAOImpl getMemberList()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in AccountInfoDAOImpl getMemberList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return (ArrayList) alEndorsementList;
    }//end of getMemberList(ArrayList alSearchCriteria)

	/*public static void main(String args[]) throws TTKException
	{
		AccountInfoDAOImpl accountInfoDAOImpl = new AccountInfoDAOImpl();
		 ArrayList<Object> alPolicy = new ArrayList<Object>();
		 String[] strSearchObjects = {"","","BA/AUG-13TH/COR-01","","","","","","","","","","",""};
		 alPolicy.add(strSearchObjects);
		 alPolicy.add("policy_number");
		 alPolicy.add("ASC");
		 alPolicy.add("1");
		 alPolicy.add("20");
		 accountInfoDAOImpl.getPolicyList(alPolicy);


		/*ArrayList<Object> alMemberList = new ArrayList<Object>();
		alMemberList.add(new Long(817973));//


		accountInfoDAOImpl.getMemberList(alMemberList);

		/*ArrayList<Object> alDependentList = new ArrayList<Object>();
		alDependentList.add(new Long(808642));

		accountInfoDAOImpl.getDependentList(alDependentList);

	}*/
  
   /** added as per KOC 1216B IBM CR
	 * This method returns the Arraylist of BufferEnrollVo's  which contains BufferConfig  details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of BufferEnrollVo's object which contains BufferConfig details
	 * @exception throws TTKException
	 */
	    public ArrayList getBufferEnrollmentlist(ArrayList alSearchCriteria) throws TTKException {
	    	Collection<Object> alResultList = new ArrayList<Object>();
	    	Connection conn = null;
	    	CallableStatement cStmtObject=null;
			ResultSet rs = null;
			
			try{
				conn = ResourceManager.getConnection();
				
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strEnrollMemberList);
				//cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strPolicyList);
				//cStmtObject.setPlsqlIndexTable(1, alSearchCriteria.get(0), 6,6,OracleTypes.VARCHAR,250);

				cStmtObject.setString(1,(String)alSearchCriteria.get(0));//sPolicyNumber
				cStmtObject.setString(2,(String)alSearchCriteria.get(1));//sEnrollNumber
				cStmtObject.setString(3,(String)alSearchCriteria.get(2));//sEmpName
				cStmtObject.setString(4,(String)alSearchCriteria.get(3));//sEnrollmentId
				cStmtObject.setString(5,(String)alSearchCriteria.get(4));//sMemberName
				cStmtObject.setString(6,(String)alSearchCriteria.get(5));//sGroupId
				cStmtObject.setString(7,(String)alSearchCriteria.get(6));//Sort variable
				cStmtObject.setString(8,(String)alSearchCriteria.get(7));//Sort Order
				cStmtObject.setString(9,(String)alSearchCriteria.get(8));//start num
				cStmtObject.setString(10,(String)alSearchCriteria.get(9));//end num


				cStmtObject.registerOutParameter(11,OracleTypes.CURSOR);//out parameter
				cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(11);
				
				if(rs != null){
					while(rs.next()){
						PolicyVO policyVO=new PolicyVO();
						if(rs.getString("POLICY_SEQ_ID") != null){
							policyVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
						}//end of if(rs.getString("POLICY_SEQ_ID") != null)
					

	                    if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
	                    	policyVO.setPolicyGroupSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
	                    }//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)
						

                        policyVO.setGrpID(TTKCommon.checkNull(rs.getString("GROUP_REG_SEQ_ID")));
				

                        policyVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
						if(rs.getString("MEMBER_SEQ_ID") != null){
							policyVO.setMemberSeqID((new Long(rs.getLong("MEMBER_SEQ_ID"))));
						}//end of if(rs.getString("MEMBER_SEQ_ID") != null)
						
						/*if(rs.getString("PREV_POLICY_GROUP_SEQ_ID") != null){
							policyVO.setPrevPolicyGroupSeqID(new Long(rs.getLong("PREV_POLICY_GROUP__ID")));
						}//end of if(rs.getString("PREV_POLICY_GROUP_SEQ_ID") != null)
*/						
						policyVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
						policyVO.setEnrollmentType(TTKCommon.checkNull(rs.getString("ENROL_TYPE")));

						policyVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
                        policyVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));

						policyVO.setMemberName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
						policyVO.setInsuredName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
						policyVO.setMemberBuffer(TTKCommon.checkNull(rs.getString("MEMBER_BUFFER")));
						policyVO.setTotalMemberBuffer(TTKCommon.checkNull(rs.getString("TOTAL_MEMBER_BUFFER")));

						alResultList.add(policyVO);
					}//end of while(rs.next())
				}//end of if(rs != null)			
				return (ArrayList)alResultList;
			}//end of try
			catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "member");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (Exception exp)
			finally
			{
				/* Nested Try Catch to ensure resource closure */ 
				try // First try closing the result set
				{
					try
					{
						if (rs != null) rs.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Resultset in AccountInfoDAOImpl getBufferEnrollmentlist()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{

						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Resultset in AccountInfoDAOImpl getBufferEnrollmentlist()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Resultset in AccountInfoDAOImpl getBufferEnrollmentlist()",sqlExp);
								throw new TTKException(sqlExp, "member");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "member");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{
					rs = null;
					cStmtObject = null;
					conn = null;
				}//end of finally
			}//end of finally
	    }//end of getBufferEnrollmentlist(ArrayList alSearchCriteria)

		 public ArrayList getBufferConfiguredList(ArrayList alSearchCriteria) throws TTKException {
			 Collection<Object> alResultList = new ArrayList<Object>();
		    	Connection conn = null;
		    	CallableStatement cStmtObject=null;
				ResultSet rs = null;
				
				try{
					conn = ResourceManager.getConnection();
					
					cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBuffMemberList);
					

					cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));//policyseqid
					cStmtObject.setLong(2,(Long)alSearchCriteria.get(1));//policygroupseqid
					cStmtObject.setLong(3,(Long)alSearchCriteria.get(2));//memberseqid
					cStmtObject.setString(4,(String)alSearchCriteria.get(3));//mem_buff_seq_Id

					cStmtObject.setString(5,(String)alSearchCriteria.get(4));//Sort variable
					cStmtObject.setString(6,(String)alSearchCriteria.get(5));//Sort Order
					cStmtObject.setString(7,(String)alSearchCriteria.get(6));//start num
					cStmtObject.setString(8,(String)alSearchCriteria.get(7));//end num
					cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);//out parameter
					cStmtObject.execute();
					rs = (java.sql.ResultSet)cStmtObject.getObject(9);
					
					if(rs != null){
						while(rs.next()){
							EnrollBufferVO enrollBufferVO=new EnrollBufferVO();
							/*
							 *  POLICY_SEQ_ID, POLICY_GROUP_SEQ_ID,MEMBER_SEQ_ID,MEM_BUFF_SEQID,REFERENCE_NO,BUFFER_ADDED_DATE,BUFFER_MODE
						        BUFFER_AMT,APPROVED_BY,REMARKS,ADDED_DATE,TPA_ENROLLMENT_ID,USER_NAME 
						        AVA_COR_BUFFER,AVA_FAM_BUFFER,MEMBER_BUFFER_ALLOC,EDIT_YN,HR_APPR_AMOUNT
							 */
							if(rs.getString("POLICY_SEQ_ID") != null){
								enrollBufferVO.setPolicySeqId(new Long(rs.getLong("POLICY_SEQ_ID")));
							}//end of if(rs.getString("POLICY_SEQ_ID") != null)

		                    if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
		                    	enrollBufferVO.setPolicyGroupSeqId(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
		                    }//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)

	                       // policyVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
							if(rs.getString("MEMBER_SEQ_ID") != null){
								enrollBufferVO.setMemberSeqId((new Long(rs.getLong("MEMBER_SEQ_ID"))));
							}//end of if(rs.getString("MEMBER_SEQ_ID") != null)
		                    
							if(rs.getString("MEM_BUFF_SEQID") != null){
								enrollBufferVO.setMemberBuffSeqId((new Long(rs.getLong("MEM_BUFF_SEQID"))));
							}//end of if(rs.getString("MEM_BUFF_SEQID") != null)
							enrollBufferVO.setRefNbr(TTKCommon.checkNull(rs.getString("REFERENCE_NO")));
							
							if(rs.getString("BUFFER_ADDED_DATE") != null){
								enrollBufferVO.setBufferDate(new Date(rs.getTimestamp("BUFFER_ADDED_DATE").getTime()));
							}//end of if(rs.getString("BUFFER_ADDED_DATE") != null)
							
							if(rs.getString("BUFFER_ADDED_DATE") != null){
								enrollBufferVO.setBufferDate1(new Date(rs.getTimestamp("BUFFER_ADDED_DATE").getTime()));
							}//end of if(rs.getString("BUFFER_ADDED_DATE") != null)
							
							
							
							enrollBufferVO.setModeDesc(TTKCommon.checkNull(rs.getString("BUFFER_MODE_DESC")));//modified for hyundai buffer
							enrollBufferVO.setModeTypeId(TTKCommon.checkNull(rs.getString("BUFFER_MODE_DESC")));

							
													
							if(rs.getString("BUFFER_AMT") != null){
								enrollBufferVO.setBufferAmt(new BigDecimal(rs.getString("BUFFER_AMT")));
							}//end of if(rs.getString("MAX_ALLOWED_AMOUNT") != null)
							else{
								enrollBufferVO.setBufferAmt(new BigDecimal("0.00"));
							}//end of else
							
							enrollBufferVO.setUserId(TTKCommon.checkNull(rs.getString("USER_ID")));

							enrollBufferVO.setEnrollmentId(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
	                       
                           // AVA_COR_BUFFER,AVA_FAM_BUFFER,MEMBER_BUFFER_ALLOC
							
							if(rs.getString("AVA_COR_BUFFER") != null){
								enrollBufferVO.setAvCorpBuffer(new BigDecimal(rs.getString("AVA_COR_BUFFER")));
							}//end of if(rs.getString("AVA_COR_BUFFER") != null)
							else{
								enrollBufferVO.setAvCorpBuffer(new BigDecimal("0.00"));
							}//end of else
							
							if(rs.getString("AVA_FAM_BUFFER") != null){
								enrollBufferVO.setAvFamilyBuffer(new BigDecimal(rs.getString("AVA_FAM_BUFFER")));
							}//end of if(rs.getString("AVA_FAM_BUFFER") != null)
							else{
								enrollBufferVO.setAvFamilyBuffer(new BigDecimal("0.00"));
							}//end of else
							
							if(rs.getString("AVA_MEM_BUFFER") != null){
								enrollBufferVO.setAvMemberBuffer(new BigDecimal(rs.getString("AVA_MEM_BUFFER")));
							}//end of if(rs.getString("AVA_MEM_BUFFER") != null)
							else{
								enrollBufferVO.setAvMemberBuffer(new BigDecimal("0.00"));
							}//end of else
							
							
							if(rs.getString("MEMBER_BUFFER_ALLOC") != null){
								enrollBufferVO.setMemberBufferAlloc(new BigDecimal(rs.getString("MEMBER_BUFFER_ALLOC")));
							}//end of if(rs.getString("MEMBER_BUFFER_ALLOC") != null)
							else{
								enrollBufferVO.setMemberBufferAlloc(new BigDecimal("0.00"));
							}//end of else
							
							if(rs.getString("ADDED_DATE") != null){
								enrollBufferVO.setAddedDate(new Date(rs.getTimestamp("ADDED_DATE").getTime()));
							}//end of if(rs.getString("BUFFER_ADDED_DATE") != null)
							
						/*	if(rs.getString("ADDED_DATE") != null){
								enrollBufferVO.setAddedDate(new Date(rs.getTimestamp("ADDED_DATE").getTime()));
							}//end of if(rs.getString("ADDED_DATE") != null)
*/							
							
							
							enrollBufferVO.setApprovedBy(TTKCommon.checkNull(rs.getString("APPROVED_BY")));
							enrollBufferVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
							
							if(rs.getString("HR_APPR_BUFFER") != null){
								enrollBufferVO.setHrInsurerBuffAmount(new BigDecimal(rs.getString("HR_APPR_BUFFER")));
							}//end of if(rs.getString("HR_APPR_AMOUNT") != null)
							else{
								enrollBufferVO.setHrInsurerBuffAmount(new BigDecimal("0.00"));
							}//end of else
							
							enrollBufferVO.setEditYN(TTKCommon.checkNull(rs.getString("EDIT_YN")));
							enrollBufferVO.setClaimType(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));//added for hyundai buffer
							enrollBufferVO.setBufferType(TTKCommon.checkNull(rs.getString("BUFFER_TYPE")));//added for hyundai buffer
							enrollBufferVO.setClaimTypeDesc(TTKCommon.checkNull(rs.getString("CLAIM_TYPE_DESC")));//added for hyundai buffer
							enrollBufferVO.setBufferTypeDesc(TTKCommon.checkNull(rs.getString("BUFFER_TYPE_DESC")));//added for hyundai buffer
					
							alResultList.add(enrollBufferVO);
						}//end of while(rs.next())
					}//end of if(rs != null)			
					return (ArrayList)alResultList;
				}//end of try
				catch (SQLException sqlExp)
				{
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				catch (Exception exp)
				{
					throw new TTKException(exp, "member");
				}//end of catch (Exception exp)
				finally
				{
					/* Nested Try Catch to ensure resource closure */ 
					try // First try closing the result set
					{
						try
						{
							if (rs != null) rs.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Resultset in AccountInfoDAOImpl getBufferConfiguredList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
						finally // Even if result set is not closed, control reaches here. Try closing the statement now.
						{

							try
							{
								if (cStmtObject != null) cStmtObject.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Resultset in AccountInfoDAOImpl getBufferConfiguredList()",sqlExp);
								throw new TTKException(sqlExp, "member");
							}//end of catch (SQLException sqlExp)
							finally // Even if statement is not closed, control reaches here. Try closing the connection now.
							{
								try
								{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Resultset in AccountInfoDAOImpl getBufferConfiguredList()",sqlExp);
									throw new TTKException(sqlExp, "member");
								}//end of catch (SQLException sqlExp)
							}//end of finally Connection Close
						}//end of finally Statement Close
					}//end of try
					catch (TTKException exp)
					{
						throw new TTKException(exp, "member");
					}//end of catch (TTKException exp)
					finally // Control will reach here in anycase set null to the objects 
					{
						rs = null;
						cStmtObject = null;
						conn = null;
					}//end of finally
				}//end of finally
		    }//end of getBufferConfiguredList(ArrayList alSearchCriteria)


		public Object[] Addbuffer(ArrayList alAddBufferParam) throws TTKException {
				
			Object[] objArrayResult = new Object[7];
	    	String strEnrollmentID = "";
	    	String strMemberBuffer = "0";
	    	String strAvaCorBuffer = "0";
	    	String strAvaFamBuffer = "0";
			    	Connection conn = null;
			    	CallableStatement cStmtObject=null;
					ResultSet rs = null;
					
					try{
						conn = ResourceManager.getConnection();
						
						cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strMemberAddBuffer);
						

						cStmtObject.setLong(1,(Long)alAddBufferParam.get(0));//policyseqid
						cStmtObject.setLong(2,(Long)alAddBufferParam.get(1));//policygroupseqid
						cStmtObject.setLong(3,(Long)alAddBufferParam.get(2));//memberseqid
						cStmtObject.setString(4,(String)alAddBufferParam.get(3));//strClaimType
						cStmtObject.setString(5,(String)alAddBufferParam.get(4));//strBufferType
					
						cStmtObject.registerOutParameter(6,OracleTypes.VARCHAR);//out parameter
						cStmtObject.registerOutParameter(7,OracleTypes.VARCHAR);//out parameter
						cStmtObject.registerOutParameter(8,OracleTypes.VARCHAR);//out parameter
						cStmtObject.registerOutParameter(9,OracleTypes.VARCHAR);//out parameter
						cStmtObject.execute();
						strEnrollmentID =((String)cStmtObject.getObject(6)).equalsIgnoreCase("") ? "0":(String)cStmtObject.getObject(6);
						strAvaCorBuffer =((String)cStmtObject.getObject(7)).equalsIgnoreCase("") ? "0":(String)cStmtObject.getObject(7);
						if(cStmtObject.getObject(8)!=null)
						{
						strAvaFamBuffer =((String)cStmtObject.getObject(8)).equalsIgnoreCase("") ? "0":(String)cStmtObject.getObject(8);
						}
						if(cStmtObject.getObject(9)!=null)
						{
						strMemberBuffer =((String)cStmtObject.getObject(9)).equalsIgnoreCase("") ? "0":(String)cStmtObject.getObject(9);
						}	
	            		objArrayResult[0] = strEnrollmentID;
	            		objArrayResult[1] = strAvaCorBuffer;//corporate buffer
	            		objArrayResult[2] = strAvaFamBuffer;//family buffer
	        			objArrayResult[3] = strMemberBuffer;//member_buffer_allocated
	            		objArrayResult[4] = (String)alAddBufferParam.get(3);//family buffer
	            		objArrayResult[5] = (String)alAddBufferParam.get(4);//family buffer
	        			
	        			   			
						return objArrayResult;
					}//end of try
					catch (SQLException sqlExp)
					{
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					catch (Exception exp)
					{
						throw new TTKException(exp, "member");
					}//end of catch (Exception exp)
					finally
					{
						/* Nested Try Catch to ensure resource closure */ 
						try // First try closing the result set
						{
							try
							{
								if (rs != null) rs.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Resultset in AccountInfoDAOImpl Addbuffer()",sqlExp);
								throw new TTKException(sqlExp, "member");
							}//end of catch (SQLException sqlExp)
							finally // Even if result set is not closed, control reaches here. Try closing the statement now.
							{

								try
								{
									if (cStmtObject != null) cStmtObject.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Resultset in AccountInfoDAOImpl Addbuffer()",sqlExp);
									throw new TTKException(sqlExp, "member");
								}//end of catch (SQLException sqlExp)
								finally // Even if statement is not closed, control reaches here. Try closing the connection now.
								{
									try
									{
										if(conn != null) conn.close();
									}//end of try
									catch (SQLException sqlExp)
									{
										log.error("Error while closing the Resultset in AccountInfoDAOImpl Addbuffer()",sqlExp);
										throw new TTKException(sqlExp, "member");
									}//end of catch (SQLException sqlExp)
								}//end of finally Connection Close
							}//end of finally Statement Close
						}//end of try
						catch (TTKException exp)
						{
							throw new TTKException(exp, "member");
						}//end of catch (TTKException exp)
						finally // Control will reach here in anycase set null to the objects 
						{
							rs = null;
							cStmtObject = null;
							conn = null;
						}//end of finally
					}//end of finally
			    }//end of Addbuffer(ArrayList alSearchCriteria)

		public Long saveEnrbuffer(EnrollBufferVO enrollBufferVO) throws TTKException {
			 Collection<Object> alResultList = new ArrayList<Object>();
		    	Connection conn = null;
		    	CallableStatement cStmtObject=null;
			//	ResultSet rs = null;
				Long lngBufferSeqID=null;
				
							
				try{
		    		conn = ResourceManager.getConnection();
		            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveMemberBuffer);
		            
		           if(enrollBufferVO.getMemberBuffSeqId() != null){
		            	cStmtObject.setLong(1,enrollBufferVO.getMemberBuffSeqId());
		            }//end of if(enrollBufferVO.getMemberBuffSeqId() != null)
		            else{
		            	cStmtObject.setLong(1,0);
		            }//end of else
		            
		            if(enrollBufferVO.getPolicySeqId() != null){
		            	cStmtObject.setLong(2,enrollBufferVO.getPolicySeqId());
		            }//end of if(enrollBufferVO.getPolicySeqId() != null)
		            else{
		            	cStmtObject.setString(2,null);
		            }//end of else
		            
		            if(enrollBufferVO.getPolicyGroupSeqId() != null){
		            	cStmtObject.setLong(3,enrollBufferVO.getPolicyGroupSeqId());
		            }//end of if(enrollBufferVO.getPolicySeqId() != null)
		            else{
		            	cStmtObject.setString(3,null);
		            }//end of else
		            
		            if(enrollBufferVO.getMemberSeqId() != null){
		            	cStmtObject.setLong(4,enrollBufferVO.getMemberSeqId());
		            }//end of if(enrollBufferVO.getPolicySeqId() != null)
		            else{
		            	cStmtObject.setString(4,null);
		            }//end of else
		            
		            cStmtObject.setString(5,enrollBufferVO.getRefNbr());
		            		            
		            if(enrollBufferVO.getBufferDate() != null){
		            	cStmtObject.setTimestamp(6,new Timestamp(enrollBufferVO.getBufferDate().getTime()));
		            }//end of if(bufferDetailVO.getBufferDate() != null)
		            else{
		            	cStmtObject.setTimestamp(6,null);
		            }//end of else
		            
		            cStmtObject.setString(7,enrollBufferVO.getModeTypeId());
		            
					 if(enrollBufferVO.getHrInsurerBuffAmount() != null){
						 if(enrollBufferVO.getHrInsurerBuffAmount().compareTo(TTKCommon.getBigDecimal("0"))==0)
						 {
							 cStmtObject.setString(8,null);
						 }
						 else	
						 {	 
			            	cStmtObject.setBigDecimal(8,enrollBufferVO.getHrInsurerBuffAmount());
						 }
			            }//end of if(bufferDetailVO.getBufferAmt() != null)
			            else{
			            	cStmtObject.setString(8,null);
			            }//end of else
					
					
					
					
		            if(enrollBufferVO.getBufferAmt() != null){
		            	cStmtObject.setBigDecimal(9,enrollBufferVO.getBufferAmt());
		            }//end of if(bufferDetailVO.getBufferAmt() != null)
		            else{
		            	cStmtObject.setString(9,null);
		            }//end of else
		            
		            cStmtObject.setString(10,enrollBufferVO.getApprovedBy());

				
		            cStmtObject.setString(11,enrollBufferVO.getRemarks());

		            cStmtObject.setLong(12,enrollBufferVO.getUpdatedBy());


		            if(enrollBufferVO.getAvCorpBuffer() != null){
		            	cStmtObject.setBigDecimal(13,enrollBufferVO.getAvCorpBuffer());
		            }//end of if(bufferDetailVO.getAvCorpBuffer() != null)
		            else{
		            	cStmtObject.setString(13,null);
		            }//end of else
		  

		            if(enrollBufferVO.getAvFamilyBuffer() != null){
		            	cStmtObject.setBigDecimal(14,enrollBufferVO.getAvFamilyBuffer());
		            }//end of if(bufferDetailVO.getAvFamilyBuffer() != null)
		            else{
		            	cStmtObject.setString(14,null);
		            }//end of else
		            

		            if(enrollBufferVO.getMemberBufferAlloc() != null){
		            	cStmtObject.setBigDecimal(15,enrollBufferVO.getAvMemberBuffer());
		            }//end of if(bufferDetailVO.getMemberBufferAlloc != null)
		            else{
		            	cStmtObject.setString(15,null);
		            }//end of else
		            cStmtObject.setString(16,enrollBufferVO.getClaimType());
		            log.info("ClaimType:::"+enrollBufferVO.getClaimType());
		            if(enrollBufferVO.getClaimType().equals("NRML"))
		            {
		            cStmtObject.setString(17,enrollBufferVO.getBufferType());
		            }else if(enrollBufferVO.getClaimType().equals("CRTL"))
		            {
		            cStmtObject.setString(17,enrollBufferVO.getBufferType1());
		            }
		            cStmtObject.registerOutParameter(18,Types.VARCHAR);
		            cStmtObject.registerOutParameter(19,Types.INTEGER);
		            cStmtObject.registerOutParameter(1,Types.BIGINT);
		            cStmtObject.execute(); 
		            lngBufferSeqID = cStmtObject.getLong(1);
				}catch (SQLException sqlExp) 
		        {
		            throw new TTKException(sqlExp, "member");
		        }//end of catch (SQLException sqlExp)
		        catch (Exception exp) 
		        {
		            throw new TTKException(exp, "member");
		        }//end of catch (Exception exp) 
		        finally
				{
		        	/* Nested Try Catch to ensure resource closure */ 
		        	try // First try closing the Statement
		        	{
		        		try
		        		{
		        			if (cStmtObject != null) cStmtObject.close();
		        		}//end of try
		        		catch (SQLException sqlExp)
		        		{
		        			log.error("Error while closing the Statement in AccountInfoDAOImpl saveEnrBuffer()",sqlExp);
		        			throw new TTKException(sqlExp, "member");
		        		}//end of catch (SQLException sqlExp)
		        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
		        		{
		        			try
		        			{
		        				if(conn != null) conn.close();
		        			}//end of try
		        			catch (SQLException sqlExp)
		        			{
		        				log.error("Error while closing the Connection inAccountInfoDAOImpl saveEnrBuffer",sqlExp);
		        				throw new TTKException(sqlExp, "member");
		        			}//end of catch (SQLException sqlExp)
		        		}//end of finally Connection Close
		        	}//end of try
		        	catch (TTKException exp)
		        	{
		        		throw new TTKException(exp, "member");
		        	}//end of catch (TTKException exp)
		        	finally // Control will reach here in anycase set null to the objects 
		        	{
		        		cStmtObject = null;
		        		conn = null;
		        	}//end of finally
				}//end of finally
				
			return lngBufferSeqID;
		}// end saveEnrbuffer

		public ArrayList getEnrBufferDetail(ArrayList alSearchCriteria)throws TTKException {
			 Collection<Object> alResultList = new ArrayList<Object>();
		    	Connection conn = null;
		    	CallableStatement cStmtObject=null;
				ResultSet rs = null;
				//EnrollBufferVO enrollBufferVO=null;
			
				try{
					conn = ResourceManager.getConnection();
				
					cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBuffMemberList);
								
					cStmtObject.setString(1,(String)null);//policyseqid
					cStmtObject.setString(2,(String)null);//policygroupseqid
					cStmtObject.setString(3,(String)null);//memberseqid
					cStmtObject.setLong(4,(Long)alSearchCriteria.get(0));//mem_buff_seq_Id
					cStmtObject.setString(5,"");//Sort variable
					cStmtObject.setString(6,"");//Sort Order
					cStmtObject.setString(7,"");//start num
					cStmtObject.setString(8,"");//end num
					cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);//out parameter
					cStmtObject.execute();
					rs = (java.sql.ResultSet)cStmtObject.getObject(9);
					
					if(rs != null){
						while(rs.next()){
							EnrollBufferVO enrollBufferVO=new EnrollBufferVO();
							/*
							 *  POLICY_SEQ_ID, POLICY_GROUP_SEQ_ID,MEMBER_SEQ_ID,MEM_BUFF_SEQID,REFERENCE_NO,BUFFER_ADDED_DATE,BUFFER_MODE
						        BUFFER_AMT,APPROVED_BY,REMARKS,ADDED_DATE,TPA_ENROLLMENT_ID,USER_NAME 
						        AVA_COR_BUFFER,AVA_FAM_BUFFER,MEMBER_BUFFER_ALLOC,EDIT_YN,HR_APPR_AMOUNT
							 */
							if(rs.getString("POLICY_SEQ_ID") != null){
								enrollBufferVO.setPolicySeqId(new Long(rs.getLong("POLICY_SEQ_ID")));
							}//end of if(rs.getString("POLICY_SEQ_ID") != null)

		                    if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
		                    	enrollBufferVO.setPolicyGroupSeqId(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
		                    }//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)

	                       // policyVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
							if(rs.getString("MEMBER_SEQ_ID") != null){
								enrollBufferVO.setMemberSeqId((new Long(rs.getLong("MEMBER_SEQ_ID"))));
							}//end of if(rs.getString("MEMBER_SEQ_ID") != null)
		                    
							if(rs.getString("MEM_BUFF_SEQID") != null){
								enrollBufferVO.setMemberBuffSeqId((new Long(rs.getLong("MEM_BUFF_SEQID"))));
							}//end of if(rs.getString("MEM_BUFF_SEQID") != null)
							enrollBufferVO.setRefNbr(TTKCommon.checkNull(rs.getString("REFERENCE_NO")));
							
							if(rs.getString("BUFFER_ADDED_DATE") != null){
								enrollBufferVO.setBufferDate(new Date(rs.getTimestamp("BUFFER_ADDED_DATE").getTime()));
							}//end of if(rs.getString("BUFFER_ADDED_DATE") != null)
							
							enrollBufferVO.setModeDesc(TTKCommon.checkNull(rs.getString("BUFFER_MODE")));
							enrollBufferVO.setModeTypeId(TTKCommon.checkNull(rs.getString("BUFFER_MODE")));

																			
							if(rs.getString("BUFFER_AMT") != null){
								enrollBufferVO.setBufferAmt(new BigDecimal(rs.getString("BUFFER_AMT")));
							}//end of if(rs.getString("MAX_ALLOWED_AMOUNT") != null)
							else{
								enrollBufferVO.setBufferAmt(new BigDecimal("0.00"));
							}//end of else
							
							enrollBufferVO.setUserId(TTKCommon.checkNull(rs.getString("USER_ID")));

							enrollBufferVO.setEnrollmentId(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
	                       
                           // AVA_COR_BUFFER,AVA_FAM_BUFFER,MEMBER_BUFFER_ALLOC,AVA_MEM_BUFFER
							
							if(rs.getString("AVA_COR_BUFFER") != null){
								enrollBufferVO.setAvCorpBuffer(new BigDecimal(rs.getString("AVA_COR_BUFFER")));
							}//end of if(rs.getString("AVA_COR_BUFFER") != null)
							else{
								enrollBufferVO.setAvCorpBuffer(new BigDecimal("0.00"));
							}//end of else
							
							if(rs.getString("AVA_FAM_BUFFER") != null){
								enrollBufferVO.setAvFamilyBuffer(new BigDecimal(rs.getString("AVA_FAM_BUFFER")));
							}//end of if(rs.getString("AVA_FAM_BUFFER") != null)
							else{
								enrollBufferVO.setAvFamilyBuffer(new BigDecimal("0.00"));
							}//end of else
							
							if(rs.getString("MEMBER_BUFFER_ALLOC") != null){
								enrollBufferVO.setMemberBufferAlloc(new BigDecimal(rs.getString("MEMBER_BUFFER_ALLOC")));
							}//end of if(rs.getString("MEMBER_BUFFER_ALLOC") != null)
							else{
								enrollBufferVO.setMemberBufferAlloc(new BigDecimal("0.00"));
							}//end of else
							
							if(rs.getString("AVA_MEM_BUFFER") != null){
								enrollBufferVO.setAvMemberBuffer(new BigDecimal(rs.getString("AVA_MEM_BUFFER")));
							}//end of if(rs.getString("MEMBER_BUFFER_ALLOC") != null)
							else{
								enrollBufferVO.setAvMemberBuffer(new BigDecimal("0.00"));
							}//end of else
							
							
							
							if(rs.getString("ADDED_DATE") != null){
								enrollBufferVO.setAddedDate(new Date(rs.getTimestamp("ADDED_DATE").getTime()));
							}//end of if(rs.getString("BUFFER_ADDED_DATE") != null)
							
							enrollBufferVO.setApprovedBy(TTKCommon.checkNull(rs.getString("APPROVED_BY")));
							enrollBufferVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
							
							if(rs.getString("HR_APPR_BUFFER") != null){
								enrollBufferVO.setHrInsurerBuffAmount(new BigDecimal(rs.getString("HR_APPR_BUFFER")));
							}//end of if(rs.getString("HR_APPR_AMOUNT") != null)
							else{
								enrollBufferVO.setHrInsurerBuffAmount(new BigDecimal("0.00"));
							}//end of else
							
                               //added for hyundai buffer
							if(rs.getString("MEM_MED_BUFFER_ALLOC") != null){
								enrollBufferVO.setMemberMedBufferAlloc(new BigDecimal(rs.getString("MEM_MED_BUFFER_ALLOC")));
							}//end of if(rs.getString("MEMBER_BUFFER_ALLOC") != null)
							else{
								enrollBufferVO.setMemberMedBufferAlloc(new BigDecimal("0.00"));
							}//end of else
							
							if(rs.getString("MEM_CRIT_BUFF_ALLOC") != null){
								enrollBufferVO.setMemberCritBufferAlloc(new BigDecimal(rs.getString("MEM_CRIT_BUFF_ALLOC")));
							}//end of if(rs.getString("MEMBER_BUFFER_ALLOC") != null)
							else{
								enrollBufferVO.setMemberCritBufferAlloc(new BigDecimal("0.00"));
							}//end of else
							
							if(rs.getString("MEM_CRIT_CORP_BUFF_ALLOC") != null){
								enrollBufferVO.setMemberCritCorpBufferAlloc(new BigDecimal(rs.getString("MEM_CRIT_CORP_BUFF_ALLOC")));
							}//end of if(rs.getString("MEMBER_BUFFER_ALLOC") != null)
							else{
								enrollBufferVO.setMemberCritCorpBufferAlloc(new BigDecimal("0.00"));
							}//end of else
							
							if(rs.getString("MEM_CRIT_MED_BUFF_ALLOC") != null){
								enrollBufferVO.setMemberCritMedBufferAlloc(new BigDecimal(rs.getString("MEM_CRIT_MED_BUFF_ALLOC")));
							}//end of if(rs.getString("MEMBER_BUFFER_ALLOC") != null)
							else{
								enrollBufferVO.setMemberCritMedBufferAlloc(new BigDecimal("0.00"));
							}//end of else
							
							
							
							
							//end added for hyundai buffer
							
							enrollBufferVO.setEditYN(TTKCommon.checkNull(rs.getString("EDIT_YN")));
							enrollBufferVO.setClaimType(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));//added for hyundai buffer
							if(rs.getString("CLAIM_TYPE").equals("NRML"))
							{
							enrollBufferVO.setBufferType(TTKCommon.checkNull(rs.getString("BUFFER_TYPE")));//added for hyundai buffer
							}
							else if(rs.getString("CLAIM_TYPE").equals("CRTL"))
							{
							enrollBufferVO.setBufferType1(TTKCommon.checkNull(rs.getString("BUFFER_TYPE")));//added for hyundai buffer
							}
							alResultList.add(enrollBufferVO);
						}//end of while(rs.next())
					}//end of if(rs != null)			
					return (ArrayList)alResultList;
				}//end of try
				catch (SQLException sqlExp)
				{
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				catch (Exception exp)
				{
					throw new TTKException(exp, "member");
				}//end of catch (Exception exp)
				finally
				{
					/* Nested Try Catch to ensure resource closure */ 
					try // First try closing the result set
					{
						try
						{
							if (rs != null) rs.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Resultset in AccountInfoDAOImpl getEnrBufferDetail()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
						finally // Even if result set is not closed, control reaches here. Try closing the statement now.
						{

							try
							{
								if (cStmtObject != null) cStmtObject.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Resultset in AccountInfoDAOImpl getEnrBufferDetail()",sqlExp);
								throw new TTKException(sqlExp, "member");
							}//end of catch (SQLException sqlExp)
							finally // Even if statement is not closed, control reaches here. Try closing the connection now.
							{
								try
								{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Resultset in AccountInfoDAOImpl getEnrBufferDetail()",sqlExp);
									throw new TTKException(sqlExp, "member");
								}//end of catch (SQLException sqlExp)
							}//end of finally Connection Close
						}//end of finally Statement Close
					}//end of try
					catch (TTKException exp)
					{
						throw new TTKException(exp, "member");
					}//end of catch (TTKException exp)
					finally // Control will reach here in anycase set null to the objects 
					{
						rs = null;
						cStmtObject = null;
						conn = null;
					}//end of finally
				}//end of finally
		    }//end of getEnrBufferDetail(ArrayList alSearchCriteria)

    //added as per KOC 1216B IBM CR
		
		public ArrayList<String[]> getPreauthHistoryList(Long memberSeqID,
				String authType, String preauthorclaimNumber) throws TTKException {
			Connection conn = null;
			CallableStatement cStmtObject = null;
		    ResultSet ars = null;
			ArrayList<String[]> authList = null;
			try {
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strPreAuthorizationHistoryList);
				cStmtObject.setLong(1, memberSeqID);
				cStmtObject.setString(2, authType);
				if(preauthorclaimNumber!= null){
					cStmtObject.setString(3, preauthorclaimNumber);
				}else{
					cStmtObject.setString(3, null);	
				}
				cStmtObject.registerOutParameter(4, Types.OTHER);
				cStmtObject.execute();
				ars = (java.sql.ResultSet) cStmtObject.getObject(4);

				if (ars != null) {
					authList = new ArrayList<String[]>();
					while (ars.next()) {
						String authSeqId = "";
						String preAuthNo = "";
						String authNo = "";
						if ("PAT".equals(authType)) {
							authSeqId = ars.getLong("PAT_AUTH_SEQ_ID") == 0 ? ""
									: ars.getLong("PAT_AUTH_SEQ_ID") + "";
							preAuthNo = ars.getString("PRE_AUTH_NUMBER") == null ? ""
									: ars.getString("PRE_AUTH_NUMBER");
							authNo = ars.getString("AUTH_NUMBER") == null ? ""
									: ars.getString("AUTH_NUMBER");
						} else if ("CLM".equals(authType)) {
							authSeqId = ars.getLong("CLAIM_SEQ_ID") == 0 ? "" : ars
									.getLong("CLAIM_SEQ_ID") + "";
							preAuthNo = ars.getString("CLAIM_NUMBER") == null ? ""
									: ars.getString("CLAIM_NUMBER");
							authNo = ars.getString("settlement_number") == null ? ""
									: ars.getString("settlement_number");
						}
						
						String hospitalName=ars.getString("HOSP_NAME")==null?"":ars.getString("HOSP_NAME");
						String approvedAmt=ars.getBigDecimal("TOT_APPROVED_AMOUNT")==null?"":ars.getBigDecimal("TOT_APPROVED_AMOUNT").toString();
						String admissionDate=ars.getDate("ADMISSION_DATE")==null?"":ars.getDate("ADMISSION_DATE").toString();
						String status=ars.getString("PAT_STATUS")==null?"":ars.getString("PAT_STATUS");
						
						
						String approvedDate=ars.getString("approved_date")==null?"":ars.getString("approved_date");
						String paymentDate=ars.getString("payment_date")==null?"":ars.getString("payment_date");
						String chonologicalNo=ars.getString("chronic_number")==null?"":ars.getString("chronic_number");
						 
						String referenceNo=ars.getString("REFERENCE_NUM")==null?"":ars.getString("REFERENCE_NUM");

						authList.add(new String[]{authSeqId,preAuthNo,authNo,hospitalName,approvedAmt,admissionDate,status,approvedDate,paymentDate,chonologicalNo,referenceNo});			
					
					}
				}
				return authList;
			}// end of try
			catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "preauth");
			}// end of catch (SQLException sqlExp)
			catch (Exception exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (Exception exp)
			finally {
				/* Nested Try Catch to ensure resource closure */
				try // First try closing the result set
				{
					try {
						if (ars != null)
							ars.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Resultset in PreAuthDAOImpl getPreauthHistoryList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches
							// here. Try closing the statement now.
					{

						try {
							if (cStmtObject != null)
								cStmtObject.close();
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Statement in PreAuthDAOImpl getPreauthHistoryList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches
								// here. Try closing the connection now.
						{
							try {
								if (conn != null)
									conn.close();
							}// end of try
							catch (SQLException sqlExp) {
								log.error(
										"Error while closing the Connection in PreAuthDAOImpl getPreauthHistoryList()",
										sqlExp);
								throw new TTKException(sqlExp, "preauth");
							}// end of catch (SQLException sqlExp)
						}// end of finally Connection Close
					}// end of finally Statement Close
				}// end of try
				catch (TTKException exp) {
					throw new TTKException(exp, "preauth");
				}// end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the
						// objects
				{
					ars = null;
					cStmtObject = null;
					conn = null;
				}// end of finally
			}// end of finally
		}// end of getPreauthHistoryList(Long memberSeqID)
	

}//end of AccountInfoDAOImpl
