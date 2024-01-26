/**
 * @ (#) InsCorpDAOImpl.java Jul 19, 2007
 * Project 	     : TTK HealthCare Services
 * File          : InsCorpDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 19, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.onlineforms.insuranceLogin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.onlineforms.insuranceLogin.AuthDetailsVO;
import com.ttk.dto.onlineforms.insuranceLogin.AuthorizationVO;
import com.ttk.dto.onlineforms.insuranceLogin.CallCenterDetailsVO;
import com.ttk.dto.onlineforms.insuranceLogin.ClaimDetailsVO;
import com.ttk.dto.onlineforms.insuranceLogin.ClaimsVO;
import com.ttk.dto.onlineforms.insuranceLogin.DashBoardVO;
import com.ttk.dto.onlineforms.insuranceLogin.DependentVO;
import com.ttk.dto.onlineforms.insuranceLogin.EnrollMemberVO;
import com.ttk.dto.onlineforms.insuranceLogin.InsCorporateVO;
import com.ttk.dto.onlineforms.insuranceLogin.InsGlobalViewVO;
import com.ttk.dto.onlineforms.insuranceLogin.NetworkDetailsVO;
//import com.ttk.dto.preauth.PreAuthVO;
//import com.ttk.dto.security.GroupVO;
import com.ttk.dto.onlineforms.insuranceLogin.PolicyDetailVO;
import com.ttk.dto.onlineforms.insuranceLogin.ProductTableVO;
import com.ttk.dto.onlineforms.insuranceLogin.RetailVO;
import com.ttk.dto.usermanagement.PersonalInfoVO;
import com.ttk.dto.usermanagement.UserListVO;
public class InsCorpDAOImpl implements BaseDAO, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID 				= 1L;
	private static Logger log = Logger.getLogger(InsCorpDAOImpl.class);
	private static final String strEnrollCorporateData 		 = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_CORPORATE_LIST(?,?,?,?,?,?,?,?,?)}";
	private static final String strCorpFocusMemberDetails	 = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_MEMBER_DETAILS(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strCorpFocusAuthOrClmDetails = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_PRE_CLM_LIST(?,?,?,?,?,?,?)}";
	private static final String strInsDashBoard				 = "{CALL ONLINE_EXTERNAL_INFO_PKG.INS_DASHBOARD(?,?)}";
	private static final String strClmDetails				 = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_CLAIM_DETAILS(?,?)}";
	private static final String strAuthDetails				 = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_PREAUTH_DETAILS(?,?)}";
	private static final String strEnrollMemberData 		 = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_MEMBER_LIST(?,?,?,?,?,?,?,?,?,?)}";

	
	//global view
	private static final String strGlobalViewDetails 		 = "{CALL ONLINE_EXTERNAL_INFO_PKG.INS_GLOBAL_COR_SUMMARY(?,?,?,?,?,?,?,?)}";
	
	//Retail 
	
	private static final String strRetailSearchList 		 = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_RETAIL_LIST(?,?,?,?,?,?,?,?,?)}";
	private static final String strRetailMemberList 		 = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_RET_MEMBER_LIST(?,?,?,?,?,?,?,?,?)}";
	
	
	//Product and Policies
	private static final String strProductSearchList 		 = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_PROD_LIST(?,?,?,?,?,?,?,?,?)}";
	private static final String strPolicyDetail		 		 = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_POLICY_LIST (?,?,?)}";
//	private static final String strPolicyDetail		 		 = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_PROD_POL_BENEFIT(?,?,?)}";
	private static final String strLogDetail		 		 = "SELECT R.* FROM( SELECT UC.CONTACT_NAME,HI.USER_ID,HI.LOGIN_DATE FROM APP.TPA_USER_LOGIN_HISTORY HI JOIN APP.TPA_LOGIN_INFO LI ON (HI.USER_ID = LI.USER_ID) JOIN APP.TPA_USER_CONTACTS UC ON (LI.CONTACT_SEQ_ID = UC.CONTACT_SEQ_ID) WHERE HI.USER_ID = ? GROUP  BY UC.CONTACT_NAME,HI.USER_ID,HI.LOGIN_DATE ORDER BY HI.LOGIN_DATE DESC) R WHERE ROWNUM < 6";
    private static final String strGetEndorsements			 = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_COR_END_DETAILS(?,?,?)}";
    private static final String strPolicyBenefitsDetail		= "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_PROD_POL_BENEFIT (?,?,?,?,?)}";
    private static final String strGetCorporateList			= "SELECT DISTINCT R.GROUP_REG_SEQ_ID,R.GROUP_NAME FROM APP.TPA_INS_INFO II LEFT OUTER JOIN APP.TPA_ENR_POLICY TEP ON (II.INS_SEQ_ID = TEP.INS_SEQ_ID) LEFT OUTER JOIN APP.TPA_GROUP_REGISTRATION R ON (TEP.GROUP_REG_SEQ_ID = R.GROUP_REG_SEQ_ID) WHERE tep.enrol_type_id = 'COR' AND II.INS_COMP_CODE_NUMBER = ?";
    private static final String strGetPolicyList			= "SELECT TEP.POLICY_SEQ_ID,TEP.POLICY_NUMBER,TO_CHAR(TEP.EFFECTIVE_FROM_DATE,'YYYY')||'-'||TO_CHAR(TEP.EFFECTIVE_TO_DATE,'YYYY') AS POLICY_PERIOD FROM APP.TPA_ENR_POLICY TEP LEFT OUTER JOIN APP.TPA_GROUP_REGISTRATION R ON (R.GROUP_REG_SEQ_ID = TEP.GROUP_REG_SEQ_ID) WHERE TEP.GROUP_REG_SEQ_ID = ?";
    private static final String strGetPolicyPeriod			= "SELECT TEP.EFFECTIVE_FROM_DATE||'#'||TEP.EFFECTIVE_TO_DATE AS POLICY_DATE,TO_CHAR(TEP.EFFECTIVE_FROM_DATE,'YYYY')||'-'||TO_CHAR(TEP.EFFECTIVE_TO_DATE,'YYYY') AS POLICY_PERIOD FROM APP.TPA_ENR_POLICY TEP WHERE TEP.POLICY_SEQ_ID = ? ";
    private static final String strGetINDPolicyList			= "SELECT TEP.POLICY_NUMBER, TEP.POLICY_SEQ_ID,TEP.GROUP_REG_SEQ_ID FROM APP.TPA_INS_INFO II LEFT OUTER JOIN APP.TPA_ENR_POLICY TEP ON(II.INS_SEQ_ID = TEP.INS_SEQ_ID) WHERE TEP.ENROL_TYPE_ID = 'IND' AND II.INS_COMP_CODE_NUMBER =?";
    private static final String strClaimTat					= "{CALL ONLINE_EXTERNAL_INFO_PKG.CLAIM_TAT_RPT1 (?,?,?,?,?,?,?,?)}";
    private static final String strPreAuthTat				= "{CALL ONLINE_EXTERNAL_INFO_PKG.PREAUTH_TAT_RPT (?,?,?,?,?,?,?,?)}";
    private static final String strTATForCards				= "{CALL ONLINE_EXTERNAL_INFO_PKG.TAT_CARDS_RPT (?,?)}";
    
    /**
     * This method returns the Arraylist of PolicyVO's  which contains the details of enrollment policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PolicyVO object which contains the details of enrollment policy details
     * @exception throws TTKException
     */
    public ArrayList getEnrollMemberData(ArrayList alSearchCriteria,String strVar) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        OracleCallableStatement oCstmt = null;
        EnrollMemberVO enrollMemberVO= null;
        ResultSet rs = null;
        try{
        	if("COR".equals(strVar)){
            conn = ResourceManager.getConnection();
            oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strEnrollCorporateData);
            oCstmt.setString(1,(String)alSearchCriteria.get(0));
            oCstmt.setString(2,(String)alSearchCriteria.get(1));
            oCstmt.setString(3,(String)alSearchCriteria.get(2));
            oCstmt.setString(4,(String)alSearchCriteria.get(3));

            oCstmt.setString(5,(String)alSearchCriteria.get(4));
            oCstmt.setString(6,(String)alSearchCriteria.get(5));
            oCstmt.setString(7,(String)alSearchCriteria.get(6));
            oCstmt.setString(8,(String)alSearchCriteria.get(7));
            oCstmt.registerOutParameter(9,OracleTypes.CURSOR);
            oCstmt.execute();
            rs = (java.sql.ResultSet)oCstmt.getObject(9);
            if(rs != null){
                while (rs.next()) {
                	enrollMemberVO = new EnrollMemberVO();
                	
                	enrollMemberVO.setCorpName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
                	enrollMemberVO.setPolicyNumb(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                	if(rs.getTimestamp("EFFECTIVE_TO_DATE") != null)
                		enrollMemberVO.setEffectiveToDate(new java.util.Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
                	enrollMemberVO.setGroupId(TTKCommon.checkNull(rs.getString("GROUP_ID")));
                	
                    alResultList.add(enrollMemberVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            
        	}else{
        		oCstmt 	=	null;
        		conn	=	null;
        		rs		=	null;
        		conn = ResourceManager.getConnection();
                oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strEnrollMemberData);
                oCstmt.setString(1,(String)alSearchCriteria.get(0));
                oCstmt.setString(2,(String)alSearchCriteria.get(1));
                oCstmt.setString(3,(String)alSearchCriteria.get(2));
                oCstmt.setString(4,(String)alSearchCriteria.get(3));
                oCstmt.setString(5,(String)alSearchCriteria.get(4));

                oCstmt.setString(6,(String)alSearchCriteria.get(5));
                oCstmt.setString(7,(String)alSearchCriteria.get(6));
                oCstmt.setString(8,(String)alSearchCriteria.get(7));
                oCstmt.setString(9,(String)alSearchCriteria.get(8));
                oCstmt.registerOutParameter(10,OracleTypes.CURSOR);
                oCstmt.execute();
                rs = (java.sql.ResultSet)oCstmt.getObject(10);
                if(rs != null){
                    while (rs.next()) {
                    	enrollMemberVO = new EnrollMemberVO();
                    	enrollMemberVO.setEnrollmentId(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
                    	enrollMemberVO.setMemberName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
                    	enrollMemberVO.setEmployeeName(TTKCommon.checkNull(rs.getString("EMP_NAME")));
                    	enrollMemberVO.setEmployeeNo(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
                        alResultList.add(enrollMemberVO);
                    }//end of while(rs.next())
                }//end of if(rs != null)
        	}
            return (ArrayList)alResultList;
        }//end of try
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
					log.error("Error while closing the Resultset in InsCorpDAOImpl getOnlinePolicyList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsCorpDAOImpl getOnlinePolicyList()",sqlExp);
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
							log.error("Error while closing the Connection in InsCorpDAOImpl getOnlinePolicyList()",sqlExp);
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
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getEnrollMemberData(ArrayList alSearchCriteria)
    
    
    
    
    
    
    public InsCorporateVO getCorpFocusMemberDetails(String enrollmentId,ArrayList alSortVars,String strVar) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        OracleCallableStatement oCstmt = null;
        InsCorporateVO insCorporateVO	= new InsCorporateVO();
        PersonalInfoVO personalInfoVO	=	null;
        PolicyDetailVO	policyDetailVO	=	null;
        ClaimsVO claimsVO				=	null;
        ArrayList<DependentVO> alDependants		=	null;
        DependentVO dependentVO			=	null;
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strCorpFocusMemberDetails);
            oCstmt.setString(1,enrollmentId);
            oCstmt.registerOutParameter(2,OracleTypes.CURSOR);
            oCstmt.registerOutParameter(3,OracleTypes.CURSOR);
            oCstmt.registerOutParameter(4,OracleTypes.CURSOR);
            oCstmt.registerOutParameter(5,OracleTypes.CURSOR);
            oCstmt.registerOutParameter(6,OracleTypes.CURSOR);//dependents details
            oCstmt.setString(7,strVar);//Corporate Or Retail
            oCstmt.setString(8,(String)alSortVars.get(0));
            oCstmt.setString(9,(String)alSortVars.get(1));
            oCstmt.setString(10,(String)alSortVars.get(2));
            oCstmt.setString(11,(String)alSortVars.get(3));
            
            oCstmt.execute();
            rs = (java.sql.ResultSet)oCstmt.getObject(2);
            
            //Personnel Details
            if(rs != null){
            	personalInfoVO	=	new PersonalInfoVO();
                if (rs.next()) {
                	personalInfoVO.setName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
                	personalInfoVO.setDob(TTKCommon.checkNull(rs.getString("MEM_DOB")));
                	personalInfoVO.setAge(TTKCommon.checkNull(rs.getString("MEM_AGE")));
                	personalInfoVO.setMaritalStatus(TTKCommon.checkNull(rs.getString("MARITAL_STATUS_ID")));
                	personalInfoVO.setGender(TTKCommon.checkNull(rs.getString("GENDER_GENERAL_TYPE_ID")));
                	
                }//end of while(rs.next())
                insCorporateVO.setPersonalInfoVO(personalInfoVO);
            }//end of if(rs != null)
            
            if(rs!=null)
            	rs.close();
            
          //Dependant Details
            rs = (java.sql.ResultSet)oCstmt.getObject(6);
            if(rs != null){
            	alDependants	=	new ArrayList<>();
                while (rs.next()) {
                	dependentVO	=	new DependentVO();
                	dependentVO.setName(TTKCommon.checkNull(rs.getString("DEPENDENT_NAME")));
                	dependentVO.setAge(TTKCommon.getInt(rs.getString("DEPENDENT_AGE")));
                	dependentVO.setGender(TTKCommon.checkNull(rs.getString("DEPENDENT_GENDER")));
                	dependentVO.setRelation(TTKCommon.checkNull(rs.getString("RELSHIP_DESCRIPTION")));
                	alDependants.add(dependentVO);
                }//end of while(rs.next())
                insCorporateVO.setDependendents(alDependants);
            }//end of if(rs != null)
            
            
            
            if(rs!=null)
            	rs.close();
            //Policy Details 
            rs = (java.sql.ResultSet)oCstmt.getObject(3);
            if(rs != null){
            	policyDetailVO=	new PolicyDetailVO();
                if (rs.next()) {
                	
                	if(rs.getTimestamp("EFFECTIVE_FROM_DATE") != null)
                    {
                		policyDetailVO.setDateOfInception(new java.util.Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
                    }//
                	
                	if(rs.getTimestamp("EFFECTIVE_TO_DATE") != null)
                    {
                		policyDetailVO.setExpiryDate(new java.util.Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
                    }//
                	if(rs.getTimestamp("DATE_OF_JOINING") != null)
                    {
                		policyDetailVO.setJoiningDate(new java.util.Date(rs.getTimestamp("DATE_OF_JOINING").getTime()));
                    }//
                	if(rs.getTimestamp("DATE_OF_RESIGNATION") != null)
                    {
                		policyDetailVO.setExitDate(new java.util.Date(rs.getTimestamp("DATE_OF_RESIGNATION").getTime()));
                    }//
                	

                	if(rs.getString("TOTAL_SUM_INSURED") != null)
                    {
                		policyDetailVO.setAggSumInsured(new BigDecimal(rs.getString("TOTAL_SUM_INSURED")));
                    }//
                	if(rs.getString("BALANCE_SUM_INSURED") != null)
                    {
                		policyDetailVO.setBalSumInsured(new BigDecimal(rs.getString("BALANCE_SUM_INSURED")));
                    }//
                	policyDetailVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
                	policyDetailVO.setNetwork(TTKCommon.checkNull(rs.getString("PRODUCT_CAT_TYPE_ID")));
                	policyDetailVO.setProductName(TTKCommon.checkNull(rs.getString("PRODUCT_NAME")));
                	policyDetailVO.setSpecialExclusions(TTKCommon.checkNull(rs.getString("SPECIAL_EXCLUSION")));
                	policyDetailVO.setWaitingPeriod(TTKCommon.checkNull(rs.getString("WAITING_PERIOD")));
                	policyDetailVO.setPolicyNo(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                	policyDetailVO.setPolicySeqId(TTKCommon.checkNull(rs.getString("POLICY_SEQ_ID")));
                	
                	//Benefit Tree
                	policyDetailVO.setIpLimit(rs.getString("IP_LIMIT"));
                	policyDetailVO.setOpLimit(rs.getString("OP_LIMIT"));
                	policyDetailVO.setChronicLimit(rs.getString("CHRONIC_LIMIT"));
                	policyDetailVO.setMaternityNormal(rs.getString("NORMAL_DEL_LIMIT"));
                	policyDetailVO.setMaternityCSection(rs.getString("C_SECTION_LIMIT"));
                	policyDetailVO.setMaternity(rs.getString("MATERNITY_LIMIT"));
                	policyDetailVO.setDentalLimit(rs.getString("DENTAL_LIMIT"));
                	policyDetailVO.setOpticalLimit(rs.getString("OPTICAL_LIMIT"));
                	policyDetailVO.setAlternativeMedicineLimit(rs.getString("ALTERNATIVE_MEDICINE_LIMIT"));
                	policyDetailVO.setPhysiotherapyLimit(rs.getString("PHYSIOTHERAPY_LIMIT"));
                	policyDetailVO.setPharmacy(rs.getString("PHARMACY"));
                	policyDetailVO.setMriandCTscan(rs.getString("MRI_CT_SCAN"));
                	
                }//end of if(rs.next())
                insCorporateVO.setPolicyDetailVO(policyDetailVO);
                
            }//end of if(rs != null)
            
            
            //Claim Details
            if(rs!=null)
            	rs.close();
            rs = (java.sql.ResultSet)oCstmt.getObject(4);
            ArrayList<ClaimsVO> alClaimDetails	=	null;
            if(rs != null){
            	alClaimDetails	=	new ArrayList<ClaimsVO>();
                while (rs.next()) {
                	claimsVO	=	new ClaimsVO();
                	claimsVO.setClaimNbr(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
                	if(rs.getTimestamp("DATE_OF_HOSPITALIZATION") != null)
                    {
                		claimsVO.setDateFrom(rs.getDate("DATE_OF_HOSPITALIZATION"));
                    }if(rs.getTimestamp("DATE_OF_DISCHARGE") != null)
                    {
                		claimsVO.setDateTo(rs.getDate("DATE_OF_DISCHARGE"));
                    }
                    if(rs.getString("TOT_DISC_GROSS_AMOUNT") != null)
                    {
                    	claimsVO.setClaimedAmt(new BigDecimal(rs.getString("TOT_DISC_GROSS_AMOUNT")));
                    }if(rs.getString("TOT_APPROVED_AMOUNT") != null)
                    {
                    	claimsVO.setSettledAmt(new BigDecimal(rs.getString("TOT_APPROVED_AMOUNT")));
                    }
                	claimsVO.setStatus(TTKCommon.checkNull(rs.getString("CLM_STATUS_TYPE_ID")));
                	alClaimDetails.add(claimsVO);
                }//end of while(rs.next())
                insCorporateVO.setClaimDetails(alClaimDetails);
            }//end of if(rs != null)
            
            return insCorporateVO;
        }//end of try
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
					log.error("Error while closing the Resultset in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
							log.error("Error while closing the Connection in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getCorpFocusMemberDetails(String enrollmentId)

    
    
    
    
    
    public RetailVO getRetailFocusMemberDetails(String enrollmentId,ArrayList alSortVars,String strVar) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        OracleCallableStatement oCstmt = null;
        RetailVO retailVO	= new RetailVO();
        PersonalInfoVO personalInfoVO	=	null;
        PolicyDetailVO	policyDetailVO	=	null;
        ClaimsVO claimsVO				=	null;
        DependentVO dependentVO			=	null;
        ArrayList<DependentVO> alDependants	=	null;	
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strCorpFocusMemberDetails);
            oCstmt.setString(1,enrollmentId);
            oCstmt.registerOutParameter(2,OracleTypes.CURSOR);//presonnel details
            oCstmt.registerOutParameter(3,OracleTypes.CURSOR);//policy details
            oCstmt.registerOutParameter(4,OracleTypes.CURSOR);//claim details
            oCstmt.registerOutParameter(5,OracleTypes.CURSOR);
            oCstmt.registerOutParameter(6,OracleTypes.CURSOR);//dependents details
            oCstmt.setString(7,strVar);
            oCstmt.setString(8,(String)alSortVars.get(0));
            oCstmt.setString(9,(String)alSortVars.get(1));
            oCstmt.setString(10,(String)alSortVars.get(2));
            oCstmt.setString(11,(String)alSortVars.get(3));
            
            oCstmt.execute();
            rs = (java.sql.ResultSet)oCstmt.getObject(2);
            
            //Personnel Details
            if(rs != null){
            	personalInfoVO	=	new PersonalInfoVO();
                if (rs.next()) {
                	personalInfoVO.setName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
                	personalInfoVO.setDob(TTKCommon.checkNull(rs.getString("MEM_DOB")));
                	personalInfoVO.setAge(TTKCommon.checkNull(rs.getString("MEM_AGE")));
                	personalInfoVO.setMaritalStatus(TTKCommon.checkNull(rs.getString("MARITAL_STATUS_ID")));
                	personalInfoVO.setGender(TTKCommon.checkNull(rs.getString("GENDER_GENERAL_TYPE_ID")));
                	
                }//end of while(rs.next())
                retailVO.setPersonalInfoVO(personalInfoVO);
            }//end of if(rs != null)
            

            if(rs!=null)
            	rs.close();
          //Dependant Details
            rs = (java.sql.ResultSet)oCstmt.getObject(6);
            if(rs != null){
            	alDependants	=	new ArrayList<>();
                while (rs.next()) {
                	dependentVO	=	new DependentVO();
                	dependentVO.setName(TTKCommon.checkNull(rs.getString("DEPENDENT_NAME")));
                	dependentVO.setAge(TTKCommon.getInt(rs.getString("DEPENDENT_AGE")));
                	dependentVO.setGender(TTKCommon.checkNull(rs.getString("DEPENDENT_GENDER")));
                	dependentVO.setRelation(TTKCommon.checkNull(rs.getString("RELSHIP_DESCRIPTION")));
                	alDependants.add(dependentVO);
                }//end of while(rs.next())
                retailVO.setDependendents(alDependants);
            }//end of if(rs != null)
            
            
            if(rs!=null)
            	rs.close();
            
            //Policy Details 
            rs = (java.sql.ResultSet)oCstmt.getObject(3);
            if(rs != null){
            	policyDetailVO=	new PolicyDetailVO();
                if (rs.next()) {
                	
                	if(rs.getTimestamp("EFFECTIVE_FROM_DATE") != null)
                    {
                		policyDetailVO.setDateOfInception(new java.util.Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
                    }//
                	
                	if(rs.getTimestamp("EFFECTIVE_TO_DATE") != null)
                    {
                		policyDetailVO.setExpiryDate(new java.util.Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
                    }//
                	if(rs.getTimestamp("DATE_OF_JOINING") != null)
                    {
                		policyDetailVO.setJoiningDate(new java.util.Date(rs.getTimestamp("DATE_OF_JOINING").getTime()));
                    }//
                	if(rs.getTimestamp("DATE_OF_RESIGNATION") != null)
                    {
                		policyDetailVO.setExitDate(new java.util.Date(rs.getTimestamp("DATE_OF_RESIGNATION").getTime()));
                    }//
                	

                	if(rs.getString("TOTAL_SUM_INSURED") != null)
                    {
                		policyDetailVO.setAggSumInsured(new BigDecimal(rs.getString("TOTAL_SUM_INSURED")));
                    }//
                	if(rs.getString("BALANCE_SUM_INSURED") != null)
                    {
                		policyDetailVO.setBalSumInsured(new BigDecimal(rs.getString("BALANCE_SUM_INSURED")));
                    }//
                	policyDetailVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
                	policyDetailVO.setNetwork(TTKCommon.checkNull(rs.getString("PRODUCT_CAT_TYPE_ID")));
                	policyDetailVO.setProductName(TTKCommon.checkNull(rs.getString("PRODUCT_NAME")));
                	policyDetailVO.setSpecialExclusions(TTKCommon.checkNull(rs.getString("SPECIAL_EXCLUSION")));
                	policyDetailVO.setWaitingPeriod(TTKCommon.checkNull(rs.getString("WAITING_PERIOD")));
                	policyDetailVO.setPolicyNo(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                	policyDetailVO.setPolicySeqId(TTKCommon.checkNull(rs.getString("POLICY_SEQ_ID")));
                	
                	//Benefit Tree
                	policyDetailVO.setIpLimit(rs.getString("IP_LIMIT"));
                	policyDetailVO.setOpLimit(rs.getString("OP_LIMIT"));
                	policyDetailVO.setDentalLimit(rs.getString("DENTAL_LIMIT"));
                	policyDetailVO.setChronicLimit(rs.getString("CHRONIC_LIMIT"));
                	policyDetailVO.setMaternityNormal(rs.getString("NORMAL_DEL_LIMIT"));
                	policyDetailVO.setMaternityCSection(rs.getString("C_SECTION_LIMIT"));
                	policyDetailVO.setMaternity(rs.getString("MATERNITY_LIMIT"));
                	
                	policyDetailVO.setOpticalLimit(rs.getString("OPTICAL_LIMIT"));
                	policyDetailVO.setAlternativeMedicineLimit(rs.getString("ALTERNATIVE_MEDICINE_LIMIT"));
                	policyDetailVO.setPhysiotherapyLimit(rs.getString("PHYSIOTHERAPY_LIMIT"));
                	policyDetailVO.setPharmacy(rs.getString("PHARMACY"));
                	policyDetailVO.setMriandCTscan(rs.getString("MRI_CT_SCAN"));
                	
                }//end of if(rs.next())
                retailVO.setPolicyDetailVO(policyDetailVO);
                
            }//end of if(rs != null)
            
            
            //Claim Details
            if(rs!=null)
            	rs.close();
            rs = (java.sql.ResultSet)oCstmt.getObject(4);
            ArrayList<ClaimsVO> alClaimDetails	=	null;
            if(rs != null){
            	alClaimDetails	=	new ArrayList<ClaimsVO>();
                while (rs.next()) {
                	claimsVO	=	new ClaimsVO();
                	claimsVO.setClaimNbr(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
                	if(rs.getTimestamp("DATE_OF_HOSPITALIZATION") != null)
                    {
                		claimsVO.setDateFrom(new java.util.Date(rs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime()));
                    }if(rs.getTimestamp("DATE_OF_DISCHARGE") != null)
                    {
                		claimsVO.setDateTo(new java.util.Date(rs.getTimestamp("DATE_OF_DISCHARGE").getTime()));
                    }
                    if(rs.getString("TOT_DISC_GROSS_AMOUNT") != null)
                    {
                    	claimsVO.setClaimedAmt(new BigDecimal(rs.getString("TOT_DISC_GROSS_AMOUNT")));
                    }if(rs.getString("TOT_APPROVED_AMOUNT") != null)
                    {
                    	claimsVO.setSettledAmt(new BigDecimal(rs.getString("TOT_APPROVED_AMOUNT")));
                    }
                	claimsVO.setStatus(TTKCommon.checkNull(rs.getString("CLM_STATUS_TYPE_ID")));
                	alClaimDetails.add(claimsVO);
                }//end of while(rs.next())
                retailVO.setClaimDetails(alClaimDetails);
            }//end of if(rs != null)
            
            return retailVO;
        }//end of try
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
					log.error("Error while closing the Resultset in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
							log.error("Error while closing the Connection in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getRetailFocusMemberDetails(String enrollmentId)
    


    
    public ArrayList<AuthorizationVO> getCorpFocusAuthorizationDetails(String enrollmentId,ArrayList alSortVars) throws TTKException {
        Connection conn = null;
        OracleCallableStatement oCstmt = null;
        AuthorizationVO authorizationVO	=	null;
        
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strCorpFocusAuthOrClmDetails);
            oCstmt.setString(1,enrollmentId);
            oCstmt.setString(2,"PAT");

            oCstmt.setString(3,(String)alSortVars.get(0));
            oCstmt.setString(4,(String)alSortVars.get(1));
            oCstmt.setString(5,(String)alSortVars.get(2));
            oCstmt.setString(6,(String)alSortVars.get(3));
            oCstmt.registerOutParameter(7,OracleTypes.CURSOR);
            oCstmt.execute();
           
            //Authorization Details
            rs = (java.sql.ResultSet)oCstmt.getObject(7);
            ArrayList<AuthorizationVO> alAuthDetails	=	new ArrayList<AuthorizationVO>();
            if(rs != null){
                while (rs.next()) {
                	authorizationVO	=	new AuthorizationVO();
                	authorizationVO.setName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
                	authorizationVO.setRelation(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
                	authorizationVO.setAuthNo(TTKCommon.checkNull(rs.getString("AUTH_NUMBER")));
                	
                	if(rs.getTimestamp("HOSPITALIZATION_DATE") != null)
                    {
                		authorizationVO.setDateFrom(rs.getDate("HOSPITALIZATION_DATE"));
                    }if(rs.getTimestamp("DISCHARGE_DATE") != null)
                    {
                    	authorizationVO.setDateTo(rs.getDate("DISCHARGE_DATE"));
                    }
                    
                    if(rs.getString("TOT_DISC_GROSS_AMOUNT") != null)
                    {
                    	authorizationVO.setClaimedAmt(new BigDecimal(rs.getString("TOT_DISC_GROSS_AMOUNT")));
                    }if(rs.getString("TOT_APPROVED_AMOUNT") != null)
                    {
                    	authorizationVO.setSettledAmt(new BigDecimal(rs.getString("TOT_APPROVED_AMOUNT")));
                    }
                    authorizationVO.setStatus(TTKCommon.checkNull(rs.getString("PAT_STATUS_TYPE_ID")));
                    alAuthDetails.add(authorizationVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            
            return alAuthDetails;
        }//end of try
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
					log.error("Error while closing the Resultset in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
							log.error("Error while closing the Connection in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getCorpFocusAuthorizationDetails(String enrollmentId)
    
    
    
    public ArrayList<ClaimsVO> getCorpFocusClaimsDetails(String enrollmentId,ArrayList alSortVars) throws TTKException {
        Connection conn = null;
        OracleCallableStatement oCstmt = null;
        ClaimsVO claimsVO	=	null;
        
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strCorpFocusAuthOrClmDetails);
            oCstmt.setString(1,enrollmentId);
            oCstmt.setString(2,"CLM");

            oCstmt.setString(3,(String)alSortVars.get(0));
            oCstmt.setString(4,(String)alSortVars.get(1));
            oCstmt.setString(5,(String)alSortVars.get(2));
            oCstmt.setString(6,(String)alSortVars.get(3));
            oCstmt.registerOutParameter(7,OracleTypes.CURSOR);
            oCstmt.execute();
           
            //Authorization Details
            rs = (java.sql.ResultSet)oCstmt.getObject(7);
            ArrayList<ClaimsVO> alClaimDetails	=	new ArrayList<ClaimsVO>();
            int i=0;
            if(rs != null){
                while (rs.next()) {
                	claimsVO	=	new ClaimsVO();
                	i=i+1;
                	claimsVO.setClaimNbr(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
                	if(rs.getTimestamp("DATE_OF_HOSPITALIZATION") != null)
                    {
                		claimsVO.setDateFrom(rs.getDate("DATE_OF_HOSPITALIZATION"));
                    }if(rs.getTimestamp("DATE_OF_DISCHARGE") != null)
                    {
                		claimsVO.setDateTo(rs.getDate("DATE_OF_DISCHARGE"));
                    }
                    if(rs.getString("TOT_DISC_GROSS_AMOUNT") != null)
                    {
                    	claimsVO.setClaimedAmt(new BigDecimal(rs.getString("TOT_DISC_GROSS_AMOUNT")));
                    }if(rs.getString("TOT_APPROVED_AMOUNT") != null)
                    {
                    	claimsVO.setSettledAmt(new BigDecimal(rs.getString("TOT_APPROVED_AMOUNT")));
                    }
                	claimsVO.setStatus(TTKCommon.checkNull(rs.getString("CLM_STATUS_TYPE_ID")));
                	alClaimDetails.add(claimsVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alClaimDetails;
        }//end of try
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
					log.error("Error while closing the Resultset in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
							log.error("Error while closing the Connection in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getCorpFocusClaimsDetails(String enrollmentId)
    

    
    
    
    public DashBoardVO getDashBoardDetails(String insCompCode) throws TTKException {
        Connection conn = null;
        OracleCallableStatement oCstmt = null;
        ClaimsVO claimsVO	=	null;
        
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strInsDashBoard);
            oCstmt.setString(1,insCompCode);
            oCstmt.registerOutParameter(2,OracleTypes.CURSOR);
            oCstmt.execute();
           
            //Authorization Details
            rs = (java.sql.ResultSet)oCstmt.getObject(2);
            DashBoardVO dashBoardVO	=	null;
            if(rs != null){
                if (rs.next()) {
                	
                	dashBoardVO	=	new DashBoardVO();
                	dashBoardVO.setClaimsReceived(rs.getInt("CLAIMS_RECEIVED"));
                	dashBoardVO.setClaimsPaid(rs.getInt("CLAIMS_PAID"));
                	dashBoardVO.setAmountClaimed(rs.getInt("AMOUNT_CLAIMED"));
                	dashBoardVO.setAmountPaid(rs.getInt("AMOUNT_PAID"));
                	dashBoardVO.setCashlessReceived(rs.getInt("PREAUTH_RECEIVED"));
                	dashBoardVO.setCashlessApproved(rs.getInt("PREAUTH_APPROVED"));
                	dashBoardVO.setTtlNoOfProviders(rs.getInt("TOTAL_NO_OF_PROVIDER"));
                	/*String temp	=	"";
                	if(rs.getString("TOP_PROV_NAME")!=null)
                		temp	=	rs.getString("TOP_PROV_NAME");
                	temp		=	temp.replace("#13;", ";&#13;");*/
                	dashBoardVO.setTopProvNames(rs.getString("TOP_PROV_NAME"));
                	dashBoardVO.setLivesCoverdPI(rs.getInt("LIVES_COVERE_PI"));
                	dashBoardVO.setLivesCoverdNonPI(rs.getInt("LIVES_COVERED_NON_PI"));
                	dashBoardVO.setPremUnderPI(rs.getInt("PREMIUM_UNDERWRITTEN_PI"));
                	dashBoardVO.setPremUnderNonPI(rs.getInt("PREMIUM_UNDERWRITTEN_NON_PI"));
                	dashBoardVO.setClaimsPortfolioRatio(rs.getString("CLAIM_RATIO"));
                	dashBoardVO.setCardsTAT(rs.getString("CARD_TIME"));
                	dashBoardVO.setCashlessTAT(rs.getString("CASHLESS_TIME"));
                	dashBoardVO.setClaimsTAT(rs.getString("CLAIMS_TIME"));
                	
                }//end of if(rs.next())
            }//end of if(rs != null)
            
            return dashBoardVO;
        }//end of try
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
					log.error("Error while closing the Resultset in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
							log.error("Error while closing the Connection in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getDashBoardDetails(String insCompCode)
    
    
    
    public ClaimDetailsVO getClaimDetails(String clmNumber) throws TTKException {
        Connection conn = null;
        OracleCallableStatement oCstmt = null;
        ClaimDetailsVO claimDetailsVO	=	null;
        
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strClmDetails);
            oCstmt.setString(1,clmNumber);
            oCstmt.registerOutParameter(2,OracleTypes.CURSOR);
            oCstmt.execute();
           
            //Claim Details
            rs = (java.sql.ResultSet)oCstmt.getObject(2);
            if(rs != null){
            	claimDetailsVO	=	new ClaimDetailsVO();
                if (rs.next()) {
                	claimDetailsVO.setClaimNumber(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
                	if(rs.getTimestamp("CLM_RECEIVED_DATE") != null)
                    {
                		claimDetailsVO.setClmReceivedDate(new java.util.Date(rs.getTimestamp("CLM_RECEIVED_DATE").getTime()));
                    }
                	if(rs.getTimestamp("START_DATE") != null)
                    {
                		claimDetailsVO.setEncounterDateFrom(new java.util.Date(rs.getTimestamp("START_DATE").getTime()));
                    }if(rs.getTimestamp("ENCOUNTER_DATE_TO") != null)
                    {
                		claimDetailsVO.setEncounterDateTo(new java.util.Date(rs.getTimestamp("ENCOUNTER_DATE_TO").getTime()));
                    }
                	claimDetailsVO.setHospName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
                	claimDetailsVO.setEncounterProviderInvoiceNo(TTKCommon.checkNull(rs.getString("ENCOUNTER_PROVIDER_INVOICE_NO")));
                	claimDetailsVO.setClaimPaymentId(TTKCommon.checkNull(rs.getString("CLAIM_PAYMENT_ID")));
                	if(rs.getTimestamp("CLAIM_PAYMENT_DATE") != null)
                    {
                		claimDetailsVO.setClaimPaymentDate(new java.util.Date(rs.getTimestamp("CLAIM_PAYMENT_DATE").getTime()));
                    }
                	claimDetailsVO.setDiagnosysCode(TTKCommon.checkNull(rs.getString("DIAGNOSYS_CODE")));
                	claimDetailsVO.setShortDesc(TTKCommon.checkNull(rs.getString("SHORT_DESC")));
                	if(rs.getString("TOT_DISC_GROSS_AMOUNT") != null)
                    	claimDetailsVO.setTotDiscGrossAmount(new BigDecimal(rs.getString("TOT_DISC_GROSS_AMOUNT")));
                	
                	if(rs.getString("DEDUCTIBLE_AMOUNT") != null)
                		claimDetailsVO.setDeductibleAmount(new BigDecimal(rs.getString("DEDUCTIBLE_AMOUNT")));
                	if(rs.getString("COPAY_AMOUNT") != null)
                		claimDetailsVO.setCopayAmount(new BigDecimal(rs.getString("COPAY_AMOUNT")));
                	if(rs.getString("BENEFIT_AMOUNT") != null)
                		claimDetailsVO.setBenefitAmount(new BigDecimal(rs.getString("BENEFIT_AMOUNT")));

                	claimDetailsVO.setClaimRecipient(TTKCommon.checkNull(rs.getString("CLAIM_RECIPIENT")));
                	claimDetailsVO.setDescription(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                	claimDetailsVO.setAuthNumber(TTKCommon.checkNull(rs.getString("AUTH_NUMBER")));
                	claimDetailsVO.setClmStatusTypeId(TTKCommon.checkNull(rs.getString("CLM_STATUS_TYPE_ID")));
                	claimDetailsVO.setClaimStatusDescription(TTKCommon.checkNull(rs.getString("CLAIM_STATUS_DESCRIPTION")));
                	claimDetailsVO.setPayeeName(TTKCommon.checkNull(rs.getString("PAYEE_NAME")));
                	claimDetailsVO.setChequeEftNumber(TTKCommon.checkNull(rs.getString("CHEQUE_EFT_NUMBER")));
                	if(rs.getTimestamp("CHEQUE_EFT_DATE") != null)
                    {
                		claimDetailsVO.setChequeEftDate(new java.util.Date(rs.getTimestamp("CHEQUE_EFT_DATE").getTime()));
                    }
                	if(rs.getTimestamp("CHEQUE_DISPATCH_DATE") != null)
                    {
                		claimDetailsVO.setChequeDispatchDate(new java.util.Date(rs.getTimestamp("CHEQUE_DISPATCH_DATE").getTime()));
                    }
                	claimDetailsVO.setNameOfCourier(TTKCommon.checkNull(rs.getString("NAME_OF_COURIER")));
                	claimDetailsVO.setCourierConsignment(TTKCommon.checkNull(rs.getString("CONSIGNMENT_NO")));
                }//end of if(rs.next())
            }//end of if(rs != null)
            
            return claimDetailsVO;
        }//end of try
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
					log.error("Error while closing the Resultset in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
							log.error("Error while closing the Connection in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getClaimDetails(String clmNumber)
    
    
    
    /**
     * Auth Details - on click on the Auth numbers link
     */
    
    public AuthDetailsVO getAuthDetails(String authNumber) throws TTKException {
        Connection conn = null;
        OracleCallableStatement oCstmt = null;
        AuthDetailsVO alAuthDetailsVO	=	null;
        
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strAuthDetails);
            oCstmt.setString(1,authNumber);
            oCstmt.registerOutParameter(2,OracleTypes.CURSOR);
            oCstmt.execute();
           
            //Claim Details
            rs = (java.sql.ResultSet)oCstmt.getObject(2);
            if(rs != null){
            	alAuthDetailsVO	=	new AuthDetailsVO();
                if (rs.next()) {
                	alAuthDetailsVO.setAuthNumber(TTKCommon.checkNull(rs.getString("AUTH_NUMBER")));
                	alAuthDetailsVO.setHospName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
                	if(rs.getTimestamp("ENCOUNTER_DATE_FROM") != null)
                    {
                		alAuthDetailsVO.setEncounterDateFrom(new java.util.Date(rs.getTimestamp("ENCOUNTER_DATE_FROM").getTime()));
                    }
                	if(rs.getTimestamp("ENCOUNTER_DATE_TO") != null)
                    {
                		alAuthDetailsVO.setEncounterDateTo(new java.util.Date(rs.getTimestamp("ENCOUNTER_DATE_TO").getTime()));
                    }
                	if(rs.getString("START_DATE") != null)
                		alAuthDetailsVO.setStartDate(new java.util.Date(rs.getTimestamp("START_DATE").getTime()));
                	
                	alAuthDetailsVO.setDiagnosysCode(TTKCommon.checkNull(rs.getString("DIAGNOSYS_CODE")));
                	alAuthDetailsVO.setShortDesc(TTKCommon.checkNull(rs.getString("SHORT_DESC")));
                	
                	
                	if(rs.getString("TOT_APPROVED_AMOUNT") != null)
                		alAuthDetailsVO.setTotApprovedAmount(new BigDecimal(rs.getString("TOT_APPROVED_AMOUNT")));
                	if(rs.getString("DEDUCTIBLE_AMOUNT") != null)
                		alAuthDetailsVO.setDeductibleAmount(new BigDecimal(rs.getString("DEDUCTIBLE_AMOUNT")));
                	if(rs.getString("COPAY_AMOUNT") != null)
                		alAuthDetailsVO.setCopayAmount(new BigDecimal(rs.getString("COPAY_AMOUNT")));
                	if(rs.getString("BENEFIT_AMOUNT") != null)
                		alAuthDetailsVO.setBenefitAmount(new BigDecimal(rs.getString("BENEFIT_AMOUNT")));

                	alAuthDetailsVO.setActivityBenifit(TTKCommon.checkNull(rs.getString("ACTIVITY_BENEFIT")));
                	alAuthDetailsVO.setEncounterType(TTKCommon.checkNull(rs.getString("ENCOUNTER_TYPE")));
                	alAuthDetailsVO.setActivityStatus(TTKCommon.checkNull(rs.getString("ACTIVITY_STATUS")));
                	alAuthDetailsVO.setActivityStatusDescription(TTKCommon.checkNull(rs.getString("ACTIVITY_STATUS_DESCRIPTION")));
                	alAuthDetailsVO.setClaimReceived(TTKCommon.checkNull(rs.getString("CLAIM_RECEIVED")));
                }//end of if(rs.next())
            }//end of if(rs != null)
            
            return alAuthDetailsVO;
        }//end of try
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
					log.error("Error while closing the Resultset in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
							log.error("Error while closing the Connection in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getAuthDetails(String authNumber)
    
    
    
    /**
     * Auth Details - on click on the Auth numbers link
     */
    
    public InsGlobalViewVO getCorpGlobalDetails(String insCompCode,String strVar,String CorpOrProdSeqId) throws TTKException {
        Connection conn = null;
        OracleCallableStatement oCstmt = null;
        InsGlobalViewVO insGlobalViewVO	=	null;
        
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strGlobalViewDetails);
            oCstmt.setString(1,insCompCode);
            oCstmt.registerOutParameter(2,OracleTypes.CURSOR);
            oCstmt.registerOutParameter(3,OracleTypes.CURSOR);
            oCstmt.registerOutParameter(4,OracleTypes.CURSOR);
            oCstmt.registerOutParameter(5,OracleTypes.CURSOR);
            oCstmt.registerOutParameter(6,OracleTypes.CURSOR);
            oCstmt.setString(7,strVar);
            oCstmt.setString(8,CorpOrProdSeqId);
            oCstmt.execute();
           
            //PreAuth Details

    		insGlobalViewVO	=	new InsGlobalViewVO();
            rs = (java.sql.ResultSet)oCstmt.getObject(2);
            if(rs != null){
            	if(rs.next()){
            		AuthorizationVO authorizationVO	=	new AuthorizationVO();
            		//as On Date Values
            		authorizationVO.setPreAuthReceivedAsOnDate(rs.getInt("NO_OF_PREAUTH_RECEIVED"));
            		if(rs.getString("REQUESTED_AMOUNT")!=null)
            			authorizationVO.setAmtPreAuthReceivedAsOnDate(new BigDecimal(rs.getString("REQUESTED_AMOUNT")));
            		else
            			authorizationVO.setAmtPreAuthReceivedAsOnDate(TTKCommon.getBigDecimal(rs.getString("REQUESTED_AMOUNT")));
            		authorizationVO.setPreAuthApprovedAsOnDate(rs.getInt("NO_OF_PREAUTH_APPROVED"));
            		authorizationVO.setAmtPreAuthApprovedAsOnDate(TTKCommon.getBigDecimal(rs.getString("APPROVED_AMOUNT")));
            		authorizationVO.setPreAuthDeniedAsOnDate(rs.getInt("NO_OF_PREAUTH_DENIED"));
            		authorizationVO.setAmtPreAuthDeniedAsOnDate(TTKCommon.getBigDecimal(rs.getString("DENIED_AMOUNT")));
            		authorizationVO.setPreAuthShortfallAsOnDate(rs.getInt("NO_OF_PREAUTH_SHORTFALL"));
            		authorizationVO.setAmtPreAuthShortfallAsOnDate(TTKCommon.getBigDecimal(rs.getString("SHORTFALL_AMOUNT")));
            		
            		//current month values
            		authorizationVO.setPreAuthReceivedCurMonth(rs.getInt("NO_OF_PRE_CUR_MON_RECEIVED"));
            		authorizationVO.setAmtPreAuthReceivedCurMonth(TTKCommon.getBigDecimal(rs.getString("CUR_MON_REQUESTED_AMOUNT")));
            		authorizationVO.setPreAuthApprovedCurMonth(rs.getInt("NO_OF_PRE_CUR_MON_APPROVED"));
            		authorizationVO.setAmtPreAuthApprovedCurMonth(TTKCommon.getBigDecimal(rs.getString("CUR_MON_APPROVED_AMOUNT")));
            		authorizationVO.setPreAuthDeniedCurMonth(rs.getInt("NO_OF_PRE_CUR_MON_DENIED"));
            		authorizationVO.setAmtPreAuthDeniedCurMonth(TTKCommon.getBigDecimal(rs.getString("CUR_MON_DENIED_AMOUNT")));
            		authorizationVO.setPreAuthShortfallCurMonth(rs.getInt("NO_OF_PRE_CUR_MON_SHORTFALL"));
            		authorizationVO.setAmtPreAuthShortfallCurMonth(TTKCommon.getBigDecimal(rs.getString("CUR_MON_SHORTFALL_AMOUNT")));
            		
            		insGlobalViewVO.setAuthorizationVO(authorizationVO);
            	}
            	
            }//end of if(rs != null)
            
            if(rs!=null)
            	rs.close();
            //Emrollment Data
            if("COR".equals(strVar)){
            	rs = (java.sql.ResultSet)oCstmt.getObject(3);
            if(rs != null){
            	if(rs.next()){
            		EnrollMemberVO enrollMemberVO	=	new EnrollMemberVO();
            		enrollMemberVO.setNumberOfEmployees(TTKCommon.getInt(rs.getString("NUMBER_OF_EMPLOYEES")));
            		enrollMemberVO.setNumberOfDependents(TTKCommon.getInt(rs.getString("NUMBER_OF_DEPENDENTS")));
            		enrollMemberVO.setNoOfEmpCurMonAdded(TTKCommon.getInt(rs.getString("NO_OF_EMP_CUR_MON_ADDED")));
            		enrollMemberVO.setNoOfDependantsCurMonAdded(TTKCommon.getInt(rs.getString("NO_OF_DEPENDANTS_CUR_MON_ADDED")));
            		enrollMemberVO.setNoOfEmpCurMonDeleted(TTKCommon.getInt(rs.getString("NO_OF_EMP_CUR_MON_DELETED")));
            		enrollMemberVO.setNoOfDepndCurMonDeleted(TTKCommon.getInt(rs.getString("NO_OF_DEPND_CUR_MON_DELETED")));
            		enrollMemberVO.setNoOfEmployeesAdded(TTKCommon.getInt(rs.getString("NO_OF_EMPLOYEES_ADDED")));
            		enrollMemberVO.setNoOfDependantsAdded(TTKCommon.getInt(rs.getString("NO_OF_DEPENDANTS_ADDED")));
            		enrollMemberVO.setNoOfEmployeesDeleted(TTKCommon.getInt(rs.getString("NO_OF_EMPLOYEES_DELETED")));
            		enrollMemberVO.setNoOfDependantsDeleted(TTKCommon.getInt(rs.getString("NO_OF_DEPENDANTS_DELETED")));
            		enrollMemberVO.setTtlPremiumAtPolicy(TTKCommon.getBigDecimal(rs.getString("GROSS_PREMIUM")));
            		enrollMemberVO.setTtlEarnedPremium(TTKCommon.getBigDecimal(rs.getString("EARNED_PREMIUM")));
            		insGlobalViewVO.setEnrollMemberVO(enrollMemberVO);
            	}
            	
            }//end of if(rs != null)
            } else if("RET".equals(strVar)){
            	rs = (java.sql.ResultSet)oCstmt.getObject(3);
            	if(rs != null){
                	if(rs.next()){
                		EnrollMemberVO enrollMemberVO	=	new EnrollMemberVO();
                		enrollMemberVO.setNumberOfPolicies(TTKCommon.getInt(rs.getString("NUMBER_OF_POLICIES")));
                		enrollMemberVO.setNumberOfLives(TTKCommon.getInt(rs.getString("NUMBER_OF_LIVES")));
                		enrollMemberVO.setTotalGrossPremium(TTKCommon.getBigDecimal(rs.getString("TOTAL_GROSS_PREMIUM")));
                		enrollMemberVO.setTotalEarnedPremium(TTKCommon.getBigDecimal(rs.getString("TOTAL_EARNED_PREMIUM")));
                		insGlobalViewVO.setEnrollMemberVO(enrollMemberVO);
                	}
                	
                }//end of if(rs != null)
            }
             
            
            if(rs!=null)
            	rs.close();
            //Claims Data
            rs = (java.sql.ResultSet)oCstmt.getObject(4);
            if(rs != null){
            	if(rs.next()){
            		ClaimsVO claimsVO	=	new ClaimsVO();
            		claimsVO.setNoOfClaimsReported(TTKCommon.getInt(rs.getString("NO_OF_CLAIMS_REPORTED")));
            		claimsVO.setReportedAmount(TTKCommon.getBigDecimal(rs.getString("REPORTED_AMOUNT")));
            		claimsVO.setNoOfClaimsPaid(TTKCommon.getInt(rs.getString("NO_OF_CLAIMS_PAID")));
            		claimsVO.setPaidAmount(TTKCommon.getBigDecimal(rs.getString("PAID_AMOUNT")));
            		claimsVO.setNoOfClaimsOutstanding(TTKCommon.getInt(rs.getString("NO_OF_CLAIMS_OUTSTANDING")));
            		claimsVO.setOutstandingAmount(TTKCommon.getBigDecimal(rs.getString("OUTSTANDING_AMOUNT")));
            		claimsVO.setNoOfClaimsApproved(TTKCommon.getInt(rs.getString("NO_OF_CLAIMS_APPROVED")));
            		claimsVO.setApprovedAmount(TTKCommon.getBigDecimal(rs.getString("APPROVED_AMOUNT")));
            		claimsVO.setNoOfClaimsDenied(TTKCommon.getInt(rs.getString("NO_OF_CLAIMS_DENIED")));
            		claimsVO.setDeniedAmount(TTKCommon.getBigDecimal(rs.getString("DENIED_AMOUNT")));
            		claimsVO.setNoOfClaimsShortfall(TTKCommon.getInt(rs.getString("NO_OF_CLAIMS_SHORTFALL")));
            		claimsVO.setShortfallAmount(TTKCommon.getBigDecimal(rs.getString("SHORTFALL_AMOUNT")));
            		claimsVO.setNoOfClmCurMonRptd(TTKCommon.getInt(rs.getString("NO_OF_CLM_CUR_MON_RPTD")));
            		claimsVO.setCurMonReportedAmount(TTKCommon.getBigDecimal(rs.getString("CUR_MON_REPORTED_AMOUNT")));
            		claimsVO.setNoOfClmCurMonPaid(TTKCommon.getInt(rs.getString("NO_OF_CLM_CUR_MON_PAID")));
            		claimsVO.setCurMonPaidAmount(TTKCommon.getBigDecimal(rs.getString("CUR_MON_PAID_AMOUNT")));
            		claimsVO.setNoOfClmCurMonOutstanding(TTKCommon.getInt(rs.getString("NO_OF_CLM_CUR_MON_OUTSTANDING")));
            		claimsVO.setCurMonOutstandingAmount(TTKCommon.getBigDecimal(rs.getString("CUR_MON_OUTSTANDING_AMOUNT")));
            		claimsVO.setNoOfClmCurMonApproved(TTKCommon.getInt(rs.getString("NO_OF_CLM_CUR_MON_APPROVED")));
            		claimsVO.setCurMonApprovedAmount(TTKCommon.getBigDecimal(rs.getString("CUR_MON_APPROVED_AMOUNT")));
            		claimsVO.setNoOfClmCurMonDenied(TTKCommon.getInt(rs.getString("NO_OF_CLM_CUR_MON_DENIED")));
            		claimsVO.setCurMonDeniedAmount(TTKCommon.getBigDecimal(rs.getString("CUR_MON_DENIED_AMOUNT")));
            		claimsVO.setNoOfClmCurMonShortfall(TTKCommon.getInt(rs.getString("NO_OF_CLM_CUR_MON_SHORTFALL")));
            		claimsVO.setCurMonShortfallAmount(TTKCommon.getBigDecimal(rs.getString("CUR_MON_SHORTFALL_AMOUNT")));
            		
            		insGlobalViewVO.setClaimsVO(claimsVO);
            	}
            	
            }//end of if(rs != null)
            
            if("COR".equals(strVar)){
            if(rs!=null)
            	rs.close();
            //Call Center Data
            rs = (java.sql.ResultSet)oCstmt.getObject(5);
            if(rs != null){
            	if(rs.next()){
            		CallCenterDetailsVO callCenterDetailsVO	=	new CallCenterDetailsVO();
            		
            		callCenterDetailsVO.setNoOfGenCallCurMon(TTKCommon.getInt(rs.getString("NO_OF_GEN_CALL_CUR_MON")));
            		callCenterDetailsVO.setNoOfEnrCallCurMon(TTKCommon.getInt(rs.getString("NO_OF_ENR_CALL_CUR_MON")));
            		callCenterDetailsVO.setNoOfClmCallCurMon(TTKCommon.getInt(rs.getString("NO_OF_CLM_CALL_CUR_MON")));
            		callCenterDetailsVO.setNoOfPreCallCurMon(TTKCommon.getInt(rs.getString("NO_OF_PRE_CALL_CUR_MON")));
            		callCenterDetailsVO.setNoOfGenCallUptodate(TTKCommon.getInt(rs.getString("NO_OF_GEN_CALL_UPTODATE")));
            		callCenterDetailsVO.setNoOfEnrCallUptodate(TTKCommon.getInt(rs.getString("NO_OF_ENR_CALL_UPTODATE")));
            		callCenterDetailsVO.setNoOfClmCallUptodate(TTKCommon.getInt(rs.getString("NO_OF_CLM_CALL_UPTODATE")));
            		callCenterDetailsVO.setNoOfPreCallUptodate(TTKCommon.getInt(rs.getString("NO_OF_PRE_CALL_UPTODATE")));
            		callCenterDetailsVO.setNoOfCallsCurMon(TTKCommon.getInt(rs.getString("NO_OF_CALLS_CUR_MON")));
            		callCenterDetailsVO.setNoOfCallsUptodate(TTKCommon.getInt(rs.getString("NO_OF_CALLS_UPTODATE")));
            		
            		insGlobalViewVO.setCallCenterDetailsVO(callCenterDetailsVO);
            	}
            	
            }//end of if(rs != null)
            }
            
            
            if(rs!=null)
            	rs.close();
            //Network Data
            rs = (java.sql.ResultSet)oCstmt.getObject(6);
            if(rs != null){
            	if(rs.next()){
            		NetworkDetailsVO networkDetailsVO	=	new NetworkDetailsVO();
            		
            		networkDetailsVO.setNoOfCnNetwork(TTKCommon.getInt(rs.getString("NO_OF_CN_NETWORK")));
            		networkDetailsVO.setNoOfGnNetwork(TTKCommon.getInt(rs.getString("NO_OF_GN_NETWORK")));
            		networkDetailsVO.setNoOfSnNetwork(TTKCommon.getInt(rs.getString("NO_OF_SN_NETWORK")));
            		networkDetailsVO.setNoOfBnNetwork(TTKCommon.getInt(rs.getString("NO_OF_BN_NETWORK")));
            		networkDetailsVO.setNoOfWnNetwork(TTKCommon.getInt(rs.getString("NO_OF_WN_NETWORK")));
            		networkDetailsVO.setTotalNoOfProviders(TTKCommon.getInt(rs.getString("TOTAL_NO_OF_PROVIDERS")));
            		networkDetailsVO.setNumberOfHospitals(TTKCommon.getInt(rs.getString("NUMBER_OF_HOSPITALS")));
            		networkDetailsVO.setNumberOfMedicalcenter(TTKCommon.getInt(rs.getString("NUMBER_OF_MEDICALCENTER")));
            		networkDetailsVO.setNumberOfClinics(TTKCommon.getInt(rs.getString("NUMBER_OF_CLINICS")));
            		networkDetailsVO.setNumberOfPharmacies(TTKCommon.getInt(rs.getString("NUMBER_OF_PHARMACIES")));
            		networkDetailsVO.setNumberOfDiagCenters(TTKCommon.getInt(rs.getString("NUMBER_OF_DIAG_CENTERS")));
            		networkDetailsVO.setTotalNoOfProvidersUsed(TTKCommon.getInt(rs.getString("TOTAL_NO_OF_PROVIDERS_USED")));
            		
            		insGlobalViewVO.setNetworkDetailsVO(networkDetailsVO);
            	}
            	
            }//end of if(rs != null)
            
            
            
            return insGlobalViewVO;
        }//end of try
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
					log.error("Error while closing the Resultset in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
							log.error("Error while closing the Connection in InsCorpDAOImpl getCorpFocusMemberDetails()",sqlExp);
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
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of InsGlobalViewVO getCorpGlobalDetails(String insCompCode)

    
   
    //RETAIL S T A R T S here
    
    
    /**
     * SEARCH RETAIL LIST
     * 
     * @param alSearchCriteria
     * @return
     * @throws TTKException
     */
    public ArrayList<RetailVO> getRetailList(ArrayList alSearchCriteria) throws TTKException {
    	ArrayList<RetailVO> alResultList = new ArrayList<RetailVO>();
        Connection conn = null;
        OracleCallableStatement oCstmt = null;
        RetailVO retailVO= null;
        ResultSet rs = null;
        try{
        	
        		oCstmt 	=	null;
        		conn	=	null;
        		rs		=	null;
        		conn = ResourceManager.getConnection();
                oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strRetailSearchList);
                oCstmt.setString(1,(String)alSearchCriteria.get(0));
                oCstmt.setString(2,(String)alSearchCriteria.get(1));
                oCstmt.setString(3,(String)alSearchCriteria.get(2));
                oCstmt.setString(4,(String)alSearchCriteria.get(3));

                oCstmt.setString(5,(String)alSearchCriteria.get(4));
                oCstmt.setString(6,(String)alSearchCriteria.get(5));
                oCstmt.setString(7,(String)alSearchCriteria.get(6));
                oCstmt.setString(8,(String)alSearchCriteria.get(7));
                oCstmt.registerOutParameter(9,OracleTypes.CURSOR);
                oCstmt.execute();
                rs = (java.sql.ResultSet)oCstmt.getObject(9);
                if(rs != null){
                    while (rs.next()) {
                    	retailVO = new RetailVO();
                    	retailVO.setProductId(TTKCommon.getInt(rs.getString("PRODUCT_SEQ_ID")));
                    	retailVO.setProductName(TTKCommon.checkNull(rs.getString("PRODUCT_NAME")));
                    	if(rs.getTimestamp("VALID_FROM") != null)
                    		retailVO.setDateAsString(TTKCommon.getFormattedDate(rs.getTimestamp("VALID_FROM")));
                    	
                    	if(rs.getTimestamp("VALID_TO") != null)
                    		retailVO.setDateTo(new java.util.Date(rs.getTimestamp("VALID_TO").getTime()));
                        alResultList.add(retailVO);
                    }//end of while(rs.next())
                }//end of if(rs != null)
            return (ArrayList<RetailVO>)alResultList;
        }//end of try
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
					log.error("Error while closing the Resultset in InsCorpDAOImpl getRetailList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsCorpDAOImpl getRetailList()",sqlExp);
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
							log.error("Error while closing the Connection in InsCorpDAOImpl getRetailList()",sqlExp);
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
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getRetailList(alSearchCriteria)
    
    
    
    
    
    /**
     * SEARCH RETAIL LIST
     * 
     * @param alSearchCriteria
     * @return
     * @throws TTKException
     */
    public ArrayList<EnrollMemberVO> getRetailMemberList(ArrayList alSearchCriteria) throws TTKException {
    	ArrayList<EnrollMemberVO> alResultList = new ArrayList<EnrollMemberVO>();
        Connection conn = null;
        OracleCallableStatement oCstmt = null;
        EnrollMemberVO enrollMemberVO= null;
        ResultSet rs = null;
        try{
        	
        		oCstmt 	=	null;
        		conn	=	null;
        		rs		=	null;
        		conn = ResourceManager.getConnection();
                oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strRetailMemberList);
                oCstmt.setString(1,(String)alSearchCriteria.get(0));
                oCstmt.setString(2,(String)alSearchCriteria.get(1));
                oCstmt.setString(3,(String)alSearchCriteria.get(2));
                oCstmt.setString(4,(String)alSearchCriteria.get(3));

                oCstmt.setString(5,(String)alSearchCriteria.get(4));
                oCstmt.setString(6,(String)alSearchCriteria.get(5));
                oCstmt.setString(7,(String)alSearchCriteria.get(6));
                oCstmt.setString(8,(String)alSearchCriteria.get(7));
                oCstmt.registerOutParameter(9,OracleTypes.CURSOR);
                oCstmt.execute();
                rs = (java.sql.ResultSet)oCstmt.getObject(9);
                if(rs != null){
                    while (rs.next()) {
                    	enrollMemberVO = new EnrollMemberVO();
                    	enrollMemberVO.setEnrollmentId(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
                    	enrollMemberVO.setMemberName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
                    	enrollMemberVO.setEmployeeName(TTKCommon.checkNull(rs.getString("EMP_NAME")));
                    	enrollMemberVO.setEmployeeNo(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
                        alResultList.add(enrollMemberVO);
                    }//end of while(rs.next())
                }//end of if(rs != null)
            return (ArrayList<EnrollMemberVO>)alResultList;
        }//end of try
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
					log.error("Error while closing the Resultset in InsCorpDAOImpl getRetailList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsCorpDAOImpl getRetailList()",sqlExp);
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
							log.error("Error while closing the Connection in InsCorpDAOImpl getRetailList()",sqlExp);
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
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getRetailMemberList(alSearchCriteria)
    
    
    
    
    
    
    /**
     * SEARCH PRODUCT LIST
     * 
     * @param alSearchCriteria
     * @return
     * @throws TTKException
     */
    public ArrayList<ProductTableVO> getProductsList(ArrayList<String> alSearchCriteria) throws TTKException {
    	ArrayList<ProductTableVO> alResultList = new ArrayList<ProductTableVO>();
        Connection conn 		= null;
        ResultSet rs 			= null;
        OracleCallableStatement oCstmt = null;
        ProductTableVO productTableVO	=	null;
    try{
    	
    		conn	=	null;
    		rs		=	null;
    		conn 	= ResourceManager.getConnection();
            oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strProductSearchList);

            oCstmt.setString(1,(String)alSearchCriteria.get(0));
            oCstmt.setString(2,(String)alSearchCriteria.get(1));
            oCstmt.setString(3,(String)alSearchCriteria.get(2));
    		oCstmt.setString(4,(String)alSearchCriteria.get(3));

    		oCstmt.setString(5,(String)alSearchCriteria.get(4));
    		oCstmt.setString(6,(String)alSearchCriteria.get(5));
    		oCstmt.setString(7,(String)alSearchCriteria.get(6));
    		oCstmt.setString(8,(String)alSearchCriteria.get(7));
            oCstmt.registerOutParameter(9,OracleTypes.CURSOR);
    		oCstmt.execute();
            rs = (java.sql.ResultSet)oCstmt.getObject(9);
            
            if(rs != null){
                while (rs.next()) {
                	
                	productTableVO = new ProductTableVO();
                	productTableVO.setProductSeqId(TTKCommon.getInt(rs.getString("PRODUCT_SEQ_ID")));
                	productTableVO.setProductName(TTKCommon.checkNull(rs.getString("PRODUCT_NAME")));
                	productTableVO.setDescription(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                	productTableVO.setInsProductCode(TTKCommon.checkNull(rs.getString("INS_PRODUCT_CODE")));
                	productTableVO.setProductCatTypeId(TTKCommon.checkNull(rs.getString("PRODUCT_CAT_TYPE_ID")));
                	productTableVO.setAuthorityType(TTKCommon.checkNull(rs.getString("AUTHORITY_TYPE")));
                    alResultList.add(productTableVO);

                }
            }
        return (ArrayList<ProductTableVO>)alResultList;
        
    }//end of try
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
					log.error("Error while closing the Resultset in InsCorpDAOImpl getRetailList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsCorpDAOImpl getRetailList()",sqlExp);
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
							log.error("Error while closing the Connection in InsCorpDAOImpl getRetailList()",sqlExp);
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
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getProductsList(alSearchCriteria)
    
    
    
    
    
    
    
    /**
     * SEARCH PRODUCT LIST
     * 
     * @param alSearchCriteria
     * @return
     * @throws TTKException
     */
    public ArrayList<PolicyDetailVO> getPolicyDetails(int productSeqId,String insCompCodeWebLogin) throws TTKException {
    	ArrayList<PolicyDetailVO> alResultList = new ArrayList<PolicyDetailVO>();
        Connection conn 		= null;
        ResultSet rs 			= null;
        PolicyDetailVO policyDetailVO	=	null;
        OracleCallableStatement oCstmt = null;
    try{
    	conn	=	null;
		rs		=	null;
		conn 	= ResourceManager.getConnection();
        oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strPolicyDetail);
        oCstmt.setLong(1,productSeqId);
        oCstmt.setString(2,insCompCodeWebLogin);
        oCstmt.registerOutParameter(3,OracleTypes.CURSOR);
		oCstmt.execute();
        rs = (java.sql.ResultSet)oCstmt.getObject(3);
         if(rs != null){
             while (rs.next()) {
            	
            	 policyDetailVO = new PolicyDetailVO();
            	 
            	 policyDetailVO.setPolicyNo(TTKCommon.getString(rs.getString("POLICY_NUMBER")));
            	 if(rs.getTimestamp("EFFECTIVE_FROM_DATE") != null)
            		 policyDetailVO.setJoiningDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
            	 if(rs.getTimestamp("EFFECTIVE_TO_DATE") != null)
            		 policyDetailVO.setExpiryDate(new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
            	 policyDetailVO.setPolicySeqId(TTKCommon.getString(rs.getString("POLICY_SEQ_ID")));
            	 policyDetailVO.setEnrolTypeId(TTKCommon.getString(rs.getString("ENROL_TYPE_ID")));
            	 
                alResultList.add(policyDetailVO);

            }
        }
        return (ArrayList<PolicyDetailVO>)alResultList;
        
    }//end of try
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
					log.error("Error while closing the Resultset in InsCorpDAOImpl getRetailList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsCorpDAOImpl getRetailList()",sqlExp);
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
							log.error("Error while closing the Connection in InsCorpDAOImpl getRetailList()",sqlExp);
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
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPolicyDetails(int productSeqId,String insCompCodeWebLogin)
    
    
    

    
    /**
    * Get The BEnefits of Each Policy for the Product
     * 
     * @param alSearchCriteria
     * @return
     * @throws TTKException
     */
    public String[] getPolicyBenefitDetails(int productSeqId,String policySeqId,String enrolTypeId,String insCompCodeWebLogin) throws TTKException {
        Connection conn 		= null;
        ResultSet rs 			= null;
        OracleCallableStatement oCstmt = null;
    try{
    	conn	=	null;
		rs		=	null;
		conn 	= ResourceManager.getConnection();
        oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strPolicyBenefitsDetail);
        oCstmt.setInt(1,productSeqId);
        oCstmt.setString(2,policySeqId);
        oCstmt.setString(3, enrolTypeId);
        oCstmt.setString(4,insCompCodeWebLogin);
        oCstmt.registerOutParameter(5,OracleTypes.CURSOR);
		oCstmt.execute();
        rs = (java.sql.ResultSet)oCstmt.getObject(5);
        String[]	strBenefitTypes = new String[8];
         if(rs != null){
             while (rs.next()) {
            	
            	 strBenefitTypes[0]	=	(TTKCommon.checkNull(rs.getString("ASSISTANCE_COVER_LIMIT")));
            	 strBenefitTypes[1]	=	(TTKCommon.checkNull(rs.getString("CHRONIC_LIMIT")));
            	 strBenefitTypes[2]	=	(TTKCommon.checkNull(rs.getString("DENTAL_LIMIT")));
            	 strBenefitTypes[3]	=	(TTKCommon.checkNull(rs.getString("MATERNITY_LIMIT")));
            	 strBenefitTypes[4]	=	(TTKCommon.checkNull(rs.getString("NORMAL_DELIVERY_LIMIT")));
            	 strBenefitTypes[5]	=	(TTKCommon.checkNull(rs.getString("CESERIAN_DELIVERY_LIMIT")));
            	 strBenefitTypes[6]	=	(TTKCommon.checkNull(rs.getString("OPTICAL_LIMIT")));
            	 strBenefitTypes[7]	=	(TTKCommon.checkNull(rs.getString("POLICY_NO")));
            }
        }
        return strBenefitTypes;
        
    }//end of try
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
					log.error("Error while closing the Resultset in InsCorpDAOImpl getRetailList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsCorpDAOImpl getRetailList()",sqlExp);
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
							log.error("Error while closing the Connection in InsCorpDAOImpl getRetailList()",sqlExp);
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
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPolicyBenefitDetails(Long productSeqId,String policySeqId,String insCompCodeWebLogin)
    
    
    
    /*
     * Get Log Details
     */
    public ArrayList getLogData(String strUserId) throws TTKException {
    	ArrayList alResultList = new ArrayList();
        Connection conn 		= null;
        ResultSet rs 			= null;
        PreparedStatement pStmt = null;
        UserListVO userListVO	=	null;
    try{
    	
    		conn	=	null;
    		rs		=	null;
    		conn 	= ResourceManager.getConnection();
    		 pStmt = conn.prepareStatement(strLogDetail);
             pStmt.setString(1,strUserId);
             rs = pStmt.executeQuery();
             if(rs != null){
                 while (rs.next()) {
                	
                	 userListVO = new UserListVO();
                	 
                	 userListVO.setUserID(TTKCommon.checkNull(rs.getString("USER_ID")));
                	 userListVO.setUserName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
                	 if(rs.getTimestamp("LOGIN_DATE") != null)
                		 userListVO.setLoginDate(new Date(rs.getTimestamp("LOGIN_DATE").getTime()));
                	 alResultList.add(userListVO);
                }
            }
        return (ArrayList<String>)alResultList;
        
    }//end of try
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
					log.error("Error while closing the Resultset in InsCorpDAOImpl getRetailList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsCorpDAOImpl getRetailList()",sqlExp);
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
							log.error("Error while closing the Connection in InsCorpDAOImpl getRetailList()",sqlExp);
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
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getLogData(String strUserId)
    
    
    /*
     * Get Endorsements
     */
    
    public ArrayList getEndorsements(String insCompCodeWebLogin,String enrollmentId) throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt1 = null;
    	ArrayList alEndorsist	=	null;
    	ResultSet rs1 = null;
    	PolicyDetailVO policyDetailVO	=	null;
    	OracleCallableStatement oCstmt	=	null;

    	try{
	    	conn = ResourceManager.getConnection();
	        oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strGetEndorsements);
	    	oCstmt.setString(1, insCompCodeWebLogin);
	    	oCstmt.setString(2, enrollmentId);
	    	oCstmt.registerOutParameter(3,OracleTypes.CURSOR);
	        oCstmt.execute();
	        rs1 = (java.sql.ResultSet)oCstmt.getObject(3);
			if(rs1 != null){
				alEndorsist	=	new ArrayList();
				while(rs1.next()){
					
					policyDetailVO = new PolicyDetailVO();
					policyDetailVO.setPolicyNo(TTKCommon.checkNull(rs1.getString("POLICY_NUMBER")));
					policyDetailVO.setInsured(TTKCommon.checkNull(rs1.getString("INSURED_NAME")));
					policyDetailVO.setDependent(TTKCommon.checkNull(rs1.getString("MEM_NAME")));
	                if(rs1.getString("DATE_OF_INCEPTION") != null){
	                	policyDetailVO.setDateOfInception(rs1.getDate("DATE_OF_INCEPTION"));
					}//end of if(rs.getString("ADDED_DATE") != null)
	                if(rs1.getString("DATE_OF_EXIT") != null){
	                	policyDetailVO.setExpiryDate(rs1.getDate("DATE_OF_EXIT"));
					}//end of if(rs.getString("ADDED_DATE") != null)
	                policyDetailVO.setStatus(TTKCommon.checkNull(rs1.getString("ACTION")));
	                alEndorsist.add(policyDetailVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
	    	return alEndorsist;
	    
    		
           
        }//end of try
    	catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "hospital");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "hospital");
        }//end of catch (Exception exp)
    	finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs1 != null) rs1.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in group_name  getPolicySIInfo()",sqlExp);
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
						log.error("Error while closing the Statement in group_name  getPolicySIInfo()",sqlExp);
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
							log.error("Error while closing the Connection in group_name  getPolicySIInfo()",sqlExp);
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
				rs1 = null;
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//getEndorsements
    
    
    
    
    
    /*
	 * Getting the Corporate List for Insurance login reports
	 */
    
    @SuppressWarnings("null")
	public ArrayList<CacheObject> getCorporatesList(String insCompCodeWebLogin) throws TTKException {
    	Connection conn = null;
    	ArrayList<CacheObject> alCorpList	=	null;
    	ResultSet rs = null;
    	CacheObject cacheObject	=	null;
    	OracleCallableStatement oCstmt	=	null;

    	try{
	    	conn = ResourceManager.getConnection();
			 PreparedStatement pStmt = null;
			 pStmt = conn.prepareStatement(strGetCorporateList);
             pStmt.setString(1,insCompCodeWebLogin);
             rs = pStmt.executeQuery();
             if(rs != null){
            	 alCorpList	=	new ArrayList<>();
                 while (rs.next()) {
                	cacheObject = new CacheObject();
 					cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("GROUP_REG_SEQ_ID")));
 					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
 	                alCorpList.add(cacheObject);
                }
            }
             
	    	return alCorpList;
	    
    		
           
        }//end of try
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
					log.error("Error while closing the Resultset in group_name  getCorporatesList()",sqlExp);
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
						log.error("Error while closing the Statement in group_name  getCorporatesList()",sqlExp);
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
							log.error("Error while closing the Connection in group_name  getCorporatesList()",sqlExp);
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
    }//ArrayList<CacheObject> getCorporatesList(String insCompCodeWebLogin
    
    
    /*
	 * Getting the Policies List based on the corporate Selectes for Insurance login reports
   	 */
       
       @SuppressWarnings("null")
   	public ArrayList<CacheObject> getPolicyList(String insCompCodeWebLogin,String strCorpSeqId) throws TTKException {
       	Connection conn = null;
       	ArrayList<CacheObject> alPolicyList	=	null;
       	ResultSet rs = null;
       	CacheObject cacheObject	=	null;
       	OracleCallableStatement oCstmt	=	null;

       	try{
   	    	conn = ResourceManager.getConnection();
   			 PreparedStatement pStmt = null;
   			 pStmt = conn.prepareStatement(strGetPolicyList);
               // pStmt.setString(1,insCompCodeWebLogin);;
                pStmt.setString(1,strCorpSeqId);
                rs = pStmt.executeQuery();
                if(rs != null){
                	alPolicyList	=	new ArrayList<>();
                    while (rs.next()) {
                   	cacheObject = new CacheObject();
    					cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("POLICY_SEQ_ID")));
    					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
    					alPolicyList.add(cacheObject);
                   }
               }
                
   	    	return alPolicyList;
              
           }//end of try
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
   					log.error("Error while closing the Resultset in group_name  getCorporatesList()",sqlExp);
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
   						log.error("Error while closing the Statement in group_name  getCorporatesList()",sqlExp);
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
   							log.error("Error while closing the Connection in group_name  getCorporatesList()",sqlExp);
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
       }//ArrayList<CacheObject> getPolicyList(String insCompCodeWebLogin,String strCorpSeqId
       
       
       

       /*
	 * Getting the Policy Period based on the policy for Insurance login reports
      	 */
          
          @SuppressWarnings("null")
      	public ArrayList<CacheObject> getPolicyPeriod(String strPolicySeqId) throws TTKException {
          	Connection conn = null;
          	ArrayList<CacheObject> alPolicyList	=	null;
          	ResultSet rs = null;
          	CacheObject cacheObject	=	null;
          	OracleCallableStatement oCstmt	=	null;

          	try{
      	    	conn = ResourceManager.getConnection();
      			 PreparedStatement pStmt = null;
      			 pStmt = conn.prepareStatement(strGetPolicyPeriod);
                  // pStmt.setString(1,insCompCodeWebLogin);;
                   pStmt.setString(1,strPolicySeqId);
                   rs = pStmt.executeQuery();
                   if(rs != null){
                   	alPolicyList	=	new ArrayList<>();
                       while (rs.next()) {
                      	cacheObject = new CacheObject();
       					cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("POLICY_DATE")));
       					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("POLICY_PERIOD")));
       					alPolicyList.add(cacheObject);
                      }
                  }
                   
      	    	return alPolicyList;
                 
              }//end of try
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
      					log.error("Error while closing the Resultset in group_name  getCorporatesList()",sqlExp);
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
      						log.error("Error while closing the Statement in group_name  getCorporatesList()",sqlExp);
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
      							log.error("Error while closing the Connection in group_name  getCorporatesList()",sqlExp);
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
          }//ArrayList<CacheObject> getPolicyPeriod(String strPolicySeqId)
          
      	public ArrayList<CacheObject> getInduPolicyList(String insCompCodeWebLogin) throws TTKException {
          	Connection conn = null;
          	ArrayList<CacheObject> alPolicyList	=	null;
          	ResultSet rs = null;
          	CacheObject cacheObject	=	null;
          	OracleCallableStatement oCstmt	=	null;

          	try{
      	    	conn = ResourceManager.getConnection();
      			 PreparedStatement pStmt = null;
      			 pStmt = conn.prepareStatement(strGetINDPolicyList);
                  // pStmt.setString(1,insCompCodeWebLogin);;
                   pStmt.setString(1,insCompCodeWebLogin);
                   rs = pStmt.executeQuery();
                   if(rs != null){
                   	alPolicyList	=	new ArrayList<>();
                       while (rs.next()) {
                      	cacheObject = new CacheObject();
       					cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("POLICY_SEQ_ID")));
       					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
       					alPolicyList.add(cacheObject);
                      }
                  }
                   
      	    	return alPolicyList;
                 
              }//end of try
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
      					log.error("Error while closing the Resultset in group_name  getCorporatesList()",sqlExp);
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
      						log.error("Error while closing the Statement in group_name  getCorporatesList()",sqlExp);
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
      							log.error("Error while closing the Connection in group_name  getCorporatesList()",sqlExp);
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
          }//ArrayList<CacheObject> getInduPolicyList(String strPolicySeqId)
      	
      	
      	
      	public ArrayList<String[]> getClaimTatData(ArrayList alInputParams) throws TTKException {
          	Connection conn = null;
          	ArrayList<String[]> alPolicyList	=	null;
          	ResultSet rs = null;
          	CallableStatement cStmtObject=null;
          	String[] clmData	=	null;
          	try{
          		conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimTat);
                cStmtObject.setString(1,(String)alInputParams.get(0));
                cStmtObject.setString(2,((String)alInputParams.get(1)));
                cStmtObject.setString(3,(String)alInputParams.get(2));
                //cStmtObject.setString(3,"TPA033");
                cStmtObject.setString(4,((String)alInputParams.get(3)));
                cStmtObject.setString(5,(String)alInputParams.get(4));
                cStmtObject.setString(6,((String)alInputParams.get(5)));
                cStmtObject.setString(7,((String)alInputParams.get(6)));
                cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
                cStmtObject.execute();
                rs = (java.sql.ResultSet) cStmtObject.getObject(8);
                   if(rs != null){
                   	alPolicyList	=	new ArrayList<>();
                       while (rs.next()) {
                    	clmData	=	new String[10];
                    	clmData[0]	=	(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));
                    	clmData[1]	=	rs.getString("COMPLETED_DATE_M");
                    	clmData[2]	=	rs.getString("COMPLETED_DATE_D");
                    	clmData[3]	=	rs.getString("COMPLETED_DATE_Y");
                    	clmData[4]	=	rs.getString("CLM_RECEIVED_DATE_M");
                    	clmData[5]	=	rs.getString("CLM_RECEIVED_DATE_D");
                    	clmData[6]	=	rs.getString("CLM_RECEIVED_DATE_Y");
       					alPolicyList.add(clmData);
                      }
                  }
      	    	return alPolicyList;
                 
              }//end of try
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
      					log.error("Error while closing the Resultset in group_name  getCorporatesList()",sqlExp);
      					throw new TTKException(sqlExp, "enrollment");
      				}//end of catch (SQLException sqlExp)
      				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
      				{
      					try
      					{
      						if (cStmtObject != null) cStmtObject.close();
      					}//end of try
      					catch (SQLException sqlExp)
      					{
      						log.error("Error while closing the Statement in group_name  getCorporatesList()",sqlExp);
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
      							log.error("Error while closing the Connection in group_name  getCorporatesList()",sqlExp);
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
      				cStmtObject = null;
      				conn = null;
      			}//end of finally
      		}//end of finally
          }//ArrayList<CacheObject> getClaimTatData(ArrayList alInputParams)
      	
      	
      	
      	public ArrayList<String[]> getPreAuthTatData(ArrayList alInputParams) throws TTKException {
          	Connection conn = null;
          	ArrayList<String[]> alPolicyList	=	null;
          	ResultSet rs = null;
          	CallableStatement cStmtObject=null;
          	String[] clmData	=	null;
          	try{
          		conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPreAuthTat);
                cStmtObject.setString(1,(String)alInputParams.get(0));
                cStmtObject.setString(2,((String)alInputParams.get(1)));
                cStmtObject.setString(3,(String)alInputParams.get(2));
                //cStmtObject.setString(3,"TPA033");
                cStmtObject.setString(4,((String)alInputParams.get(3)));
                cStmtObject.setString(5,(String)alInputParams.get(4));
                cStmtObject.setString(6,((String)alInputParams.get(5)));
                cStmtObject.setString(7,((String)alInputParams.get(6)));
                cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
                cStmtObject.execute();
                rs = (java.sql.ResultSet) cStmtObject.getObject(8);
                   if(rs != null){
                   	alPolicyList	=	new ArrayList<>();
                       while (rs.next()) {
                    	clmData	=	new String[15];
                    	clmData[0]	=	(TTKCommon.checkNull(rs.getString("BENEFIT_TYPE")));
                    	clmData[1]	=	rs.getString("COMPLETED_DATE_M");
                    	clmData[2]	=	rs.getString("COMPLETED_DATE_D");
                    	clmData[3]	=	rs.getString("COMPLETED_DATE_Y");
                    	clmData[4]	=	rs.getString("COMPLETED_DATE_H");
                    	clmData[5]	=	rs.getString("COMPLETED_DATE_MI");
                    	clmData[6]	=	rs.getString("CLM_RECEIVED_DATE_M");
                    	clmData[7]	=	rs.getString("CLM_RECEIVED_DATE_D");
                    	clmData[8]	=	rs.getString("CLM_RECEIVED_DATE_Y");
                    	clmData[9]	=	rs.getString("CLM_RECEIVED_DATE_H");
                    	clmData[10]	=	rs.getString("CLM_RECEIVED_DATE_MI");
       					alPolicyList.add(clmData);
                      }
                  }
      	    	return alPolicyList;
                 
              }//end of try
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
      					log.error("Error while closing the Resultset in group_name  getCorporatesList()",sqlExp);
      					throw new TTKException(sqlExp, "enrollment");
      				}//end of catch (SQLException sqlExp)
      				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
      				{
      					try
      					{
      						if (cStmtObject != null) cStmtObject.close();
      					}//end of try
      					catch (SQLException sqlExp)
      					{
      						log.error("Error while closing the Statement in group_name  getCorporatesList()",sqlExp);
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
      							log.error("Error while closing the Connection in group_name  getCorporatesList()",sqlExp);
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
      				cStmtObject = null;
      				conn = null;
      			}//end of finally
      		}//end of finally
          }//ArrayList<CacheObject> getPreAuthTatData(ArrayList alInputParams)
      	
      	
      	
      	public ArrayList<String> getTATForCards(String insCompCodeWebLogin) throws TTKException {
          	Connection conn = null;
          	ArrayList<String> alTatForCards	=	null;
          	ResultSet rs = null;
          	CallableStatement cStmtObject=null;
          	try{
          		conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strTATForCards);
                cStmtObject.setString(1,insCompCodeWebLogin);
                cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
                cStmtObject.execute();
                rs = (java.sql.ResultSet) cStmtObject.getObject(2);
                   if(rs != null){
                	   alTatForCards	=	new ArrayList<>();
                       while (rs.next()) {
                    	alTatForCards.add(TTKCommon.checkNull(rs.getString("0-5")));
                    	alTatForCards.add(rs.getString("6-10"));
                    	alTatForCards.add(rs.getString("Above 10"));
                      }
                  }
      	    	return alTatForCards;
                 
              }//end of try
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
      					log.error("Error while closing the Resultset in group_name  getCorporatesList()",sqlExp);
      					throw new TTKException(sqlExp, "enrollment");
      				}//end of catch (SQLException sqlExp)
      				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
      				{
      					try
      					{
      						if (cStmtObject != null) cStmtObject.close();
      					}//end of try
      					catch (SQLException sqlExp)
      					{
      						log.error("Error while closing the Statement in group_name  getCorporatesList()",sqlExp);
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
      							log.error("Error while closing the Connection in group_name  getCorporatesList()",sqlExp);
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
      				cStmtObject = null;
      				conn = null;
      			}//end of finally
      		}//end of finally
          }//ArrayList<CacheObject> getTATForCards()
      	
      	
}//end of InsCorpDAOImpl
