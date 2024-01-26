package com.ttk.dao.impl.onlineforms.providerLogin; 
/**
 * @ (#) OnlinePbmProviderDAOImpl.java April 02, 2017
 * Project 	     : Dubai ProjectX
 * File          : OnlinePbmProviderDAOImpl.java
 * Author        : Nagababu Kamadi
 * Company       : RCS Technologies
 * Date Created  : April 02, 2017
 *
 * @author       :  Nagababu Kamadi
 * Modified by   :
 * Modified date :
 * Reason        :
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.xdb.XMLType;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.dom4j.io.DOMReader;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.onlineforms.providerLogin.PbmPreAuthVO;
import com.ttk.dto.preauth.ActivityDetailsVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
//import com.ttk.dto.preauth.PreAuthVO;
//import com.ttk.dto.security.GroupVO;
public class OnlinePbmProviderDAOImpl implements BaseDAO, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID 				= 1L;
	private static Logger log = Logger.getLogger(OnlinePbmProviderDAOImpl.class);
	
	private static String strPreAuthGeneralDetails="{CALL HOSP_PBM_PKG.SAVE_PBM_PAT(?,?,?,?,?,?,?,?,?,?)}";
	private static String strSelectAllPreAuthDetails="{CALL HOSP_PBM_PKG.SELECT_PAT_AUTH_DETAILS(?,?,?,?)}";
	private static String strAddIcdDetails="{CALL HOSP_PBM_PKG.SAVE_DIAGNOSYS_DETAILS(?,?,?,?,?)}";
	private static String strAddDrugDetails="{CALL HOSP_PBM_PKG.SAVE_ACTIVITY_DETAILS(?,?,?,?,?,?,?,?,?)}";
	private static String strRequestAuthorization="{CALL HOSP_PBM_PKG.GENERATE_AUTHORIZATION(?,?)}";
	private static String strWebserviceCode="{CALL  HOSP_PBM_PKG.PMB_WEBSRVC_PROC(?,?,?,?,?)}";

	
	
	private static String strDeleteDiagnosisDetails="{CALL HOSP_PBM_PKG.DELETE_DIAGNOSYS_DETAILS(?,?,?)}";
	private static String strDeleteDrugDetails="{CALL HOSP_PBM_PKG.DELETE_ACTIVITY_DETAILS(?,?,?)}";
	private static String strValidateMember="{CALL HOSP_PBM_PKG.VALIDATE_ENROLLMENT_ID(?,?,?,?)}";
	private static String strPreAuthList="{CALL HOSP_PBM_PKG.SELECT_PREAUTH_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static String strGetTariffDetails="{CALL HOSP_PBM_PKG.SELECT_ACTIVITY_CODE(?,?,?,?,?,?)}";
	private static String strSubmitClaim="{CALL hosp_pbm_pkg.save_pbm_clm(?,?,?,?)}";
	private static String strCheckElegibility="{CALL hosp_pbm_pkg.pbm_clm_status(?,?)}";
	private static String strPreAuthStatus="select PAT_STATUS_TYPE_ID from APP.PAT_AUTHORIZATION_DETAILS where PAT_AUTH_SEQ_ID= ?";
	private static String strPBMUploadFile="{CALL hosp_pbm_pkg.save_pbm_clm_file(?,?,?,?,?)}";
	private static String strclaimsList="{CALL hosp_pbm_pkg.select_claim_list(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static String strclaimDetails="{CALL hosp_pbm_pkg.SELECT_CLAIM_DETAILS(?,?)}";
	
	
	
	
	
	
	
	public Long addPreAuthGeneralDetails(PbmPreAuthVO preAuthVO) throws TTKException{
        Connection conn = null;
        CallableStatement cllStm=null;
        
        try{
            conn = ResourceManager.getConnection();
            cllStm	=	conn.prepareCall(strPreAuthGeneralDetails);
            
            if(preAuthVO.getPreAuthSeqID()==null){
            	//cllStm.setString(1,null);     
            	cllStm.registerOutParameter(1,Types.BIGINT);
            }else{
            	cllStm.setLong(1,preAuthVO.getPreAuthSeqID());
            }
            cllStm.setString(2,preAuthVO.getDateOfTreatment());
            cllStm.setString(3,preAuthVO.getEnrolmentID());
           /* System.out.println("qatarID:::::"+preAuthVO.getQatarID());*/
            cllStm.setString(4,preAuthVO.getQatarID());            
            cllStm.setString(5,preAuthVO.getPreAuthNO());
            cllStm.setLong(6,preAuthVO.getHospitalSeqID());           
            cllStm.setString(7,preAuthVO.getClinicianID());
            cllStm.setString(8,preAuthVO.getClinicianName());
            cllStm.setString(9,preAuthVO.getEventRefNo());
            cllStm.setLong(10,preAuthVO.getAddedBy());
            
            cllStm.execute();
            
             Long preAuthSeqID;
             if(preAuthVO.getPreAuthSeqID()==null)preAuthSeqID=cllStm.getLong(1);
             else preAuthSeqID=preAuthVO.getPreAuthSeqID();
             
            return preAuthSeqID;
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
        	closeResource("addPreAuthGeneralDetails()",null,cllStm,conn);
		}
			
    }//end of addPreAuthGeneralDetails(String enrollmentId)
	
	public PbmPreAuthVO  validateMemberID(PbmPreAuthVO preAuthVO) throws TTKException{
        Connection conn = null;
        CallableStatement cllStm=null;
        ResultSet         rs=null;
        
        
        try{
        	
            conn = ResourceManager.getConnection();
            cllStm	=	conn.prepareCall(strValidateMember);
            cllStm.setString(1,TTKCommon.checkNull(preAuthVO.getEnrolmentID()).trim());
            cllStm.setString(2,preAuthVO.getDateOfTreatment());
            cllStm.setString(3,preAuthVO.getEventRefNo());
            cllStm.registerOutParameter(4,OracleTypes.CURSOR);
            
            
           
             cllStm.execute();
            
             
             rs=(ResultSet)cllStm.getObject(4);
             
             if(rs!=null){
            	 
            	 if(rs.next()){
            		 preAuthVO.setMemSeqID(rs.getLong("MEMBER_SEQ_ID"));
            		 preAuthVO.setDobDate(rs.getString("MEM_DOB"));
            		 preAuthVO.setEnrolmentID(rs.getString("TPA_ENROLLMENT_ID"));
            		 preAuthVO.setQatarID(rs.getString("EMIRATE_ID"));
            		 preAuthVO.setInsCompanyID(rs.getString("ins_cmp_code"));
            		 preAuthVO.setMemGender(rs.getString("gender_general_type_id"));
            	
            	 }
             }
             
             
            return preAuthVO;
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
        	closeResource("validateMemberID()",null,cllStm,conn);
		}
			
    }//end of validateMemberID(String enrollmentId)
	
	
	
	
	public String  checkClaimElegibility(Long preAuthSeqID) throws TTKException{
        Connection conn = null;
        CallableStatement cllStm=null;
       
        
        
        try{
        	
            conn = ResourceManager.getConnection();
            
            cllStm	=	conn.prepareCall(strCheckElegibility);
            cllStm.setLong(1,preAuthSeqID);
            
            cllStm.registerOutParameter(2,OracleTypes.CHAR);
                                  
             cllStm.execute();
             String rowprocc=cllStm.getString(2);
                                      
            return rowprocc;
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
        	closeResource("chechClaimElegibility()",null,cllStm,conn);
		}
			
    }//end of validateMemberID(String enrollmentId)
	
	
	
	
	
	

	
	public PbmPreAuthVO getAllPreAuthDetails(Long preAuthSeqID) throws TTKException{
        Connection conn = null;
        CallableStatement cllStm=null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        PbmPreAuthVO pbmPreAuthVO=new PbmPreAuthVO();
        DiagnosisDetailsVO diagnosisDetailsVO=null;
        ActivityDetailsVO activityDetailsVO=null;
        ArrayList<DiagnosisDetailsVO> allIcds=new ArrayList<>();
        ArrayList<ActivityDetailsVO> allAct=new ArrayList<>();
        ResultSet rsGeneral=null;
        BigDecimal totalRequestedAmt=new BigDecimal(0);
        BigDecimal totalApprovedAmt=new BigDecimal(0);
        BigDecimal totalRequestedAmtforApp=new BigDecimal(0);
        /*String dateFormat="DD/MM/YYYY";*/
        try{
            conn = ResourceManager.getConnection();
            cllStm	=	conn.prepareCall(strSelectAllPreAuthDetails);
            
            	cllStm.setLong(1,preAuthSeqID);
            	
            	cllStm.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            	cllStm.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
            	cllStm.registerOutParameter(4, oracle.jdbc.OracleTypes.CURSOR);
             cllStm.execute();
             rsGeneral=(ResultSet)cllStm.getObject(2);
             ResultSet rsIcd=(ResultSet)cllStm.getObject(3);
             ResultSet rsDrug=(ResultSet)cllStm.getObject(4);
             
             if(rsGeneral!=null){
            	 if(rsGeneral.next()){
            		 pbmPreAuthVO.setPreAuthSeqID(preAuthSeqID);
            		 pbmPreAuthVO.setPreAuthNO(rsGeneral.getString("PRE_AUTH_NUMBER"));
            		 pbmPreAuthVO.setEnrolmentID(rsGeneral.getString("TPA_ENROLLMENT_ID"));
            		 pbmPreAuthVO.setQatarID(rsGeneral.getString("QUATAR_ID"));
            		 pbmPreAuthVO.setDobDate(rsGeneral.getString("MEM_DOB"));
            		 pbmPreAuthVO.setInsCompanyID(rsGeneral.getString("PAYER_ID"));
            		 pbmPreAuthVO.setClinicianID(rsGeneral.getString("CLINICIAN_ID"));
            		 pbmPreAuthVO.setClinicianName(rsGeneral.getString("CLINICIAN_NAME"));
            		 pbmPreAuthVO.setDateOfTreatment(rsGeneral.getString("HOSPITALIZATION_DATE"));
            		 pbmPreAuthVO.setTransactionDate(rsGeneral.getString("TRANSACTION_DATE"));
            		 pbmPreAuthVO.setInsuranceCompanyName(rsGeneral.getString("INS_COMP_NAME"));
            		 pbmPreAuthVO.setAuthorizationNO(rsGeneral.getString("AUTH_NUMBER"));
            		 pbmPreAuthVO.setErxRef(rsGeneral.getString("ERX_REF"));
            		 pbmPreAuthVO.setCommonts(rsGeneral.getString("COMMENTS"));
            		 pbmPreAuthVO.setPreAuthStatus(rsGeneral.getString("PAT_STATUS_TYPE_ID"));
            		 pbmPreAuthVO.setEventRefNo(rsGeneral.getString("event_no"));
            	 }
             }
            	 if(rsIcd!=null){
                	 while(rsIcd.next()){
                		 diagnosisDetailsVO=new DiagnosisDetailsVO();
                		 diagnosisDetailsVO.setDiagSeqId(rsIcd.getLong("DIAG_SEQ_ID"));
                		 diagnosisDetailsVO.setAilmentDescription(rsIcd.getString("ICD_DESCRIPTION"));
                		 diagnosisDetailsVO.setIcdCode(rsIcd.getString("DIAGNOSYS_CODE"));
                		 diagnosisDetailsVO.setPrimaryAilment(rsIcd.getString("PRIMARY_AILMENT_YN"));
                		 diagnosisDetailsVO.setIcdCodeSeqId(rsIcd.getLong("ICD_CODE_SEQ_ID"));
                		 allIcds.add(diagnosisDetailsVO);
                	 }
                	 
             }
            	 if(rsDrug!=null){
            		 int sNO=1;
                	 while(rsDrug.next()){
                		 activityDetailsVO=new ActivityDetailsVO();
                		 activityDetailsVO.setSerialNo(sNO);
                		
                		 activityDetailsVO.setActivityCodeDesc(rsDrug.getString("ACTIVITY_DESCRIPTION"));
                		 
                		 activityDetailsVO.setUnitType(rsDrug.getString("UNIT_TYPE")); 
                		
                		 activityDetailsVO.setGrossAmount(rsDrug.getBigDecimal("GROSS_AMOUNT"));
                		 activityDetailsVO.setDiscount(rsDrug.getBigDecimal("DISCOUNT_AMOUNT"));
                		 activityDetailsVO.setNetAmount(rsDrug.getBigDecimal("NET_AMOUNT"));
                		 activityDetailsVO.setQuantity(rsDrug.getFloat("QUANTITY"));
                		 activityDetailsVO.setQuantityInt(rsDrug.getInt("QUANTITY"));
                		 activityDetailsVO.setApprovedQuantity(rsDrug.getFloat("APPROVED_QUANTITY"));
                		 activityDetailsVO.setApprovedAmount(rsDrug.getBigDecimal("APPROVED_AMT"));
                		 if(rsDrug.getBigDecimal("APPROVED_AMT")!=null){
                			 totalApprovedAmt=totalApprovedAmt.add(rsDrug.getBigDecimal("APPROVED_AMT"));
                    		 }
                		 activityDetailsVO.setActivityDtlSeqId(rsDrug.getLong("ACTIVITY_DTL_SEQ_ID"));
                		 activityDetailsVO.setActivityStatus(rsDrug.getString("ACTIVITY_STATUS"));
                		                 		                		 
                		 activityDetailsVO.setProviderRequestedAmt(rsDrug.getBigDecimal("REQ_AMOUNT"));
                		
                		 if(rsDrug.getBigDecimal("REQ_AMOUNT")!=null){
                		 totalRequestedAmt=totalRequestedAmt.add(rsDrug.getBigDecimal("REQ_AMOUNT"));
                		 }
                		 if(rsDrug.getBigDecimal("REQ_AMOUNT")!=null&& "Approved".equals(rsDrug.getString("ACTIVITY_STATUS")))
                		 {  
                			 totalRequestedAmtforApp=totalRequestedAmtforApp.add(rsDrug.getBigDecimal("REQ_AMOUNT"));
                			 
                		 }
                		 
                		
                		 
                			 
                			 
                		 activityDetailsVO.setPatientShare(rsDrug.getBigDecimal("PATIENT_SHARE_AMOUNT"));
                		 activityDetailsVO.setDuration(rsDrug.getInt("DURATION_DAYS"));
                		 activityDetailsVO.setDenialDescription(rsDrug.getString("DENIAL_CODE"));
                		 activityDetailsVO.setDenialRemarks(rsDrug.getString("REMARKS"));
                		 activityDetailsVO.setDateOfApproval(rsDrug.getString("DATE_APPROVAL"));
                		/* activityDetailsVO.setMiRef(rsDrug.getString("MI_REF"));*/
                		 activityDetailsVO.setErxRef(rsDrug.getString("ERX_REF"));
                		 activityDetailsVO.setErxInstruction(rsDrug.getString("ERX_INSTRUCTIONS"));
                		 
                		 allAct.add(activityDetailsVO);
                		 sNO++;
                	 }
             }
            	 pbmPreAuthVO.setIcdDetails(allIcds);
            	 pbmPreAuthVO.setDrugDetails(allAct);
            	 pbmPreAuthVO.setTotalReqAmt(totalRequestedAmt);
            	 pbmPreAuthVO.setTotalApprAmt(totalApprovedAmt);
            	 pbmPreAuthVO.setTotalReqAmtforApp(totalRequestedAmtforApp);
            	
            	 
            	 if(rsDrug!=null)rsDrug.close();
            	 if(rsIcd!=null)rsIcd.close();
            	
            	 pStmt = conn.prepareStatement(strPreAuthStatus);
                 pStmt.setLong(1,preAuthSeqID);
                 
                 rs = pStmt.executeQuery();
                 if(rs != null){
                     while(rs.next()){
                     	
                     	pbmPreAuthVO.setFinalStatus(rs.getString("PAT_STATUS_TYPE_ID"));
                     }
                 }
                 
                 
            return pbmPreAuthVO;
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
        	closeResource("getAllPreAuthDetails()",rsGeneral,cllStm,conn);
        	closeResource("getAllPreAuthDetails()",rs,pStmt,conn);
		}
			
    }//end of getAllPreAuthDetails(String enrollmentId)
	
	public Integer addIcdDetails(PbmPreAuthVO preAuthVO) throws TTKException{
        Connection conn = null;
        CallableStatement cllStm=null;
        
        try{
            conn = ResourceManager.getConnection();
            cllStm	=	conn.prepareCall(strAddIcdDetails);
            
            if(preAuthVO.getIcdDtlSeqID()==null){
            	cllStm.setString(1,null);
            }else{
            	cllStm.setLong(1,preAuthVO.getIcdDtlSeqID());
            }
            cllStm.setLong(2,preAuthVO.getPreAuthSeqID());
            cllStm.setLong(3,preAuthVO.getIcdCodeSeqID());
            cllStm.setString(4,preAuthVO.getIcdCode());
            cllStm.setLong(5,preAuthVO.getAddedBy());
            cllStm.registerOutParameter(6,OracleTypes.INTEGER);         
            cllStm.execute();
             Integer rowprocc=cllStm.getInt(6);
            return rowprocc;
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
        	closeResource("addIcdDetails()",null,cllStm,conn);
		}
			
    }//ublic Long addIcdDetails(PbmPreAuthVO preAuthVO) throws TTKException{
	
	
	public Integer addDrugDetails(PbmPreAuthVO preAuthVO) throws TTKException{
        Connection conn = null;
        CallableStatement cllStm=null;
        
        try{
            conn = ResourceManager.getConnection();
            cllStm	=	conn.prepareCall(strAddDrugDetails);
            
            if(preAuthVO.getDrugDtlSeqID()==null){
            	cllStm.setString(1,null);
            }else{
            	cllStm.setLong(1,preAuthVO.getDrugDtlSeqID());
            }
            cllStm.setLong(2,preAuthVO.getPreAuthSeqID());
            cllStm.setLong(3,preAuthVO.getDrugCodeSeqID());
            cllStm.setString(4,preAuthVO.getUnitType());
            cllStm.setString(5,preAuthVO.getQuantity());
            cllStm.setString(6,preAuthVO.getDays());
            cllStm.setString(7,"Y");
            cllStm.setLong(8,preAuthVO.getAddedBy());
            cllStm.setString(9,"Y");
            cllStm.registerOutParameter(10,OracleTypes.INTEGER);         
            cllStm.execute();
             Integer rowprocc=cllStm.getInt(10);
            return rowprocc;
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
        	closeResource("addDrugDetails()",null,cllStm,conn);
		}
			
    }//ublic Integer addDrugDetails(PbmPreAuthVO preAuthVO) throws TTKException{
	

	public Long requstAuthorization(PbmPreAuthVO preAuthVO) throws TTKException{
        Connection conn = null;
    //    Connection conn2 = null;
        CallableStatement generalCllStm=null;        
        CallableStatement icdCllStm=null;
        CallableStatement drugCllStm=null;
        CallableStatement reqAuthCllStm=null;
        CallableStatement webserviceData=null;
        
        try{
            conn = ResourceManager.getConnection();
            conn.setAutoCommit(false);
          //  conn2 = ResourceManager.getConnection();
          //  conn2.setAutoCommit(false);
            
            //PreAuth general details saving            
            generalCllStm	=	conn.prepareCall(strPreAuthGeneralDetails);
            generalCllStm.registerOutParameter(1,Types.BIGINT);          
            generalCllStm.setString(2,preAuthVO.getDateOfTreatment());
            generalCllStm.setString(3,preAuthVO.getEnrolmentID());
            generalCllStm.setString(4,preAuthVO.getQatarID());            
            generalCllStm.setString(5,preAuthVO.getPreAuthNO());
            
            
            generalCllStm.setLong(6,preAuthVO.getHospitalSeqID());           
            generalCllStm.setString(7,preAuthVO.getClinicianID());
            generalCllStm.setString(8,preAuthVO.getClinicianName());
            generalCllStm.setString(9,preAuthVO.getEventRefNo());
            generalCllStm.setLong(10,preAuthVO.getAddedBy());            
            generalCllStm.execute();
            
             Long preAuthSeqID=generalCllStm.getLong(1);
            
             preAuthVO.setPreAuthSeqID(preAuthSeqID);
             
             //Adding icd details with respect to preauthseqid
             
             ArrayList<DiagnosisDetailsVO> icdDetails=preAuthVO.getIcdDetails();             
             
             icdCllStm	=	conn.prepareCall(strAddIcdDetails);
             for(DiagnosisDetailsVO diagnosisDetailsVO:icdDetails){
            
             icdCllStm.setString(1,null);            
             icdCllStm.setLong(2,preAuthSeqID);
             icdCllStm.setLong(3,diagnosisDetailsVO.getIcdCodeSeqId());
             icdCllStm.setString(4,diagnosisDetailsVO.getIcdCode());
             icdCllStm.setLong(5,preAuthVO.getAddedBy());                      
             icdCllStm.addBatch();
             }
             icdCllStm.executeBatch();
             
             //Adding drug details with respect to preauthseqid
             
             ArrayList<ActivityDetailsVO> drugdetails=preAuthVO.getDrugDetails();
             
             drugCllStm	=	conn.prepareCall(strAddDrugDetails);
             
            for(ActivityDetailsVO activityDetailsVO:drugdetails){
            	
             drugCllStm.setString(1,null);             
             drugCllStm.setLong(2,preAuthSeqID);
             drugCllStm.setLong(3,activityDetailsVO.getActivityCodeSeqId());
             drugCllStm.setString(4,activityDetailsVO.getUnitType());
             drugCllStm.setFloat(5,activityDetailsVO.getQuantity());
             drugCllStm.setInt(6,activityDetailsVO.getMedicationDays());
             drugCllStm.setString(7,"Y");
             drugCllStm.setLong(8,preAuthVO.getAddedBy());
             drugCllStm.setString(9,"Y");            
             drugCllStm.addBatch();             
            }
            drugCllStm.executeBatch();
            
            reqAuthCllStm	=	conn.prepareCall(strRequestAuthorization);
           
            reqAuthCllStm.setLong(1,preAuthVO.getPreAuthSeqID());
            reqAuthCllStm.setLong(2,preAuthVO.getAddedBy());
            reqAuthCllStm.execute();
            
            conn.commit();
            
         /* webserviceData = conn2.prepareCall(strWebserviceCode);
            //  XMLType  xmlInputType = XMLType.createXML(conn,preAuthVO.getInputPBMXML());
              XMLType  xmlInputType =   XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), preAuthVO.getInputPBMXML());
              XMLType  xmlResponseType =   XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), preAuthVO.getResponsePBMXML());

           //   XMLType  xmlResponseType = XMLType.createXML(conn,preAuthVO.getInputPBMXML());

            
           webserviceData.setObject(1, xmlInputType);
            webserviceData.setObject(2, xmlResponseType);
            webserviceData.setString(3, "PAT");
            webserviceData.setLong(4,preAuthSeqID);
            webserviceData.setLong(5,preAuthVO.getAddedBy());            
            webserviceData.execute();
            conn2.commit();*/
            
            
            return preAuthSeqID;
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
        	closeResource("requstAuthorization()",null,reqAuthCllStm,null);
        	closeResource("requstAuthorization()",null,drugCllStm,null);
        	closeResource("requstAuthorization()",null,icdCllStm,null);
        	closeResource("requstAuthorization()",null,generalCllStm,conn);  
        	closeResource("requstAuthorization()",null,webserviceData,null);        	
        	
        	
		}
			
    }//ublic Long requstAuthorization(PbmPreAuthVO preAuthVO) throws TTKException{
	
	public Integer deleteDiagnosisDetails(String strIcdDtlSeqID,String preAuthSeqID) throws TTKException{
        Connection conn = null;
        CallableStatement cllStm=null;
        try{
            conn = ResourceManager.getConnection();
            cllStm	=	conn.prepareCall(strDeleteDiagnosisDetails);
            
            	cllStm.setString(1,preAuthSeqID);
               cllStm.setString(2,strIcdDtlSeqID);
            cllStm.registerOutParameter(3,OracleTypes.INTEGER);         
            cllStm.execute();
             Integer rowprocc=cllStm.getInt(3);
            return rowprocc;
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
        	closeResource("deleteDiagnosisDetails()",null,cllStm,conn);
		}
			
    }//ublic Integer deleteDiagnosisDetails(PbmPreAuthVO preAuthVO) throws TTKException{
	
	public Integer deleteDrugDetails(String strDrugDtlSeqID,String preAuthSeqID) throws TTKException{
        Connection conn = null;
        CallableStatement cllStm=null;
        try{
            conn = ResourceManager.getConnection();
            cllStm	=	conn.prepareCall(strDeleteDrugDetails);
            
            	cllStm.setString(1,preAuthSeqID);
            cllStm.setString(2,strDrugDtlSeqID);
            cllStm.registerOutParameter(3,OracleTypes.INTEGER);         
            cllStm.execute();
             Integer rowprocc=cllStm.getInt(3);
            return rowprocc;
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
        	closeResource("deleteDrugDetails()",null,cllStm,conn);
		}
			
    }//ublic Integer deleteDrugDetails(PbmPreAuthVO preAuthVO) throws TTKException{
	
	
	
	public ArrayList<PbmPreAuthVO> getPbmPreAuthList(ArrayList<Object> alData,Long userSeqID) throws TTKException{
/*System.out.println("alData="+alData);
System.out.println("userId="+userSeqID);*/
        Connection conn = null;
        CallableStatement cllStm=null;
        PbmPreAuthVO pbmPreAuthVO=null;
        ArrayList<PbmPreAuthVO> allPreAuths=new ArrayList<>();
        String dateFormat="dd/MM/yyyy";
        String strSuppressLink[]={"1"};
        ResultSet rs=null;
       
        try{
            conn = ResourceManager.getConnection();
            cllStm	=	conn.prepareCall(strPreAuthList);// alData=[hjbhjhb, 02/05/2017, 03/05/2017, hjhh, hjhj, hbjbhj, bhjbhj, INP, AUTH_NUMBER, ASC, 1, 101]
          /*System.out.println("alData="+alData);*/
            cllStm.setString(1,(String)alData.get(0));
            cllStm.setString(2,(String)alData.get(1));
            cllStm.setString(3,(String)alData.get(2));
            cllStm.setString(4,(String)alData.get(3));
            cllStm.setString(5,(String)alData.get(4));
            cllStm.setString(6,(String)alData.get(5));
            cllStm.setString(7,(String)alData.get(6));
            cllStm.setString(8,(String)alData.get(7));
            cllStm.setString(9,(String)alData.get(8));
            cllStm.setString(10,(String)alData.get(9));
            cllStm.setString(11,(String)alData.get(10));
            cllStm.setString(12,(String)alData.get(11));
            cllStm.setString(13,(String)alData.get(12));
            cllStm.setString(14,(String)alData.get(13));            
            cllStm.setLong(15,userSeqID);
            cllStm.registerOutParameter(16, oracle.jdbc.OracleTypes.CURSOR);
            	
             cllStm.execute();
             rs=(ResultSet)cllStm.getObject(16);
             
             if(rs!=null){
            	 while(rs.next()){
            		 /*System.out.println("fasdfsdfsd");*/
            		 /*System.out.println("PRE_AUTH_NUMBER="+rs.getString("PRE_AUTH_NUMBER"));*/
            		 pbmPreAuthVO=new PbmPreAuthVO();
            		 pbmPreAuthVO.setPreAuthSeqID(rs.getLong("PRE_AUTH_SEQ_ID"));
            		 pbmPreAuthVO.setPreAuthNO(rs.getString("PRE_AUTH_NUMBER"));
            		 pbmPreAuthVO.setEnrolmentID(rs.getString("TPA_ENROLLMENT_ID"));
            		 if(rs.getString("QUATAR_ID")==null){
            			 pbmPreAuthVO.setQatarID(""); 
            		 }else{
            		 pbmPreAuthVO.setQatarID(rs.getString("QUATAR_ID"));     
            		 }
            		 pbmPreAuthVO.setClinicianName(rs.getString("CLINICIAN_NAME"));
            		 if(rs.getString("HOSPITALIZATION_DATE")!=null){
            		 pbmPreAuthVO.setDateOfTreatment(rs.getString("HOSPITALIZATION_DATE").split(" ")[0]);
            		 }
            		 //pbmPreAuthVO.setDateOfTreatment(TTKCommon.convertDateAsString(dateFormat, rs.getDate("HOSPITALIZATION_DATE")));
            		 pbmPreAuthVO.setAuthorizationNO(rs.getString("AUTH_NUMBER"));
            		 pbmPreAuthVO.setMemberName(rs.getString("MEM_NAME"));
            		 
            		 pbmPreAuthVO.setEventRefNo(rs.getString("EVENT_NO"));
            		
            		 pbmPreAuthVO.setStatus(rs.getString("STATUS"));
            		 
            		 if("In Progress".equals(rs.getString("STATUS"))){
	            			
	            			pbmPreAuthVO.setSuppressLink(strSuppressLink);
	            		 }
            		 allPreAuths.add(pbmPreAuthVO);
            		 
            	 }
             }
            	
            	 
            return allPreAuths;
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
        	closeResource("getPbmPreAuthList()",rs,cllStm,conn);
		}
			
    
	}//public ArrayList<PbmPreAuthVO> getPbmPreAuthList(ArrayList<Object> alData) throws TTKException{
	
	//getPbmClaimList
	
	
	public ArrayList getPbmClaimList(ArrayList alSearchCriteria,Long hospSeqID) throws TTKException{
		
		        Connection conn = null;
		        CallableStatement cllStm=null;
		        PbmPreAuthVO pbmPreAuthVO=null;
		        String strSuppressLink[]={"13"};
		        ResultSet rs=null;
		        
		        try{
		            conn = ResourceManager.getConnection();
		            cllStm	=	conn.prepareCall(strclaimsList);// alSearchCriteria=[hjbhjhb, 02/05/2017, 03/05/2017, hjhh, hjhj, hbjbhj, bhjbhj, INP, AUTH_NUMBER, ASC, 1, 101]
		         
		          cllStm.setString(1,(String) alSearchCriteria.get(0));//1 trtmtFromDate
		          cllStm.setString(2,(String) alSearchCriteria.get(1));//2 trtmtToDate
		          cllStm.setString(3,(String) alSearchCriteria.get(2));//3 clmFromDate
		          cllStm.setString(4,(String) alSearchCriteria.get(3));//4 clmToDate
		          cllStm.setString(5,(String) alSearchCriteria.get(4));//5  patientName
		          cllStm.setString(6,(String) alSearchCriteria.get(5));//6 authNo
		          cllStm.setString(7,(String) alSearchCriteria.get(6));//7 invoiceNumber
		          cllStm.setString(8,(String) alSearchCriteria.get(7));//8 alKootId
		          cllStm.setString(9,(String) alSearchCriteria.get(8));//9 claimNumber
		          cllStm.setString(10,(String) alSearchCriteria.get(9));//10 clmPayStatus
		          cllStm.setString(11,(String) alSearchCriteria.get(10));//11 status
		          cllStm.setString(12,(String) alSearchCriteria.get(11));//12  eventRefNo
		          cllStm.setString(13,(String) alSearchCriteria.get(12));//13 srt Var
		          cllStm.setString(14,(String) alSearchCriteria.get(13));//14 srt order
		          cllStm.setString(15,(String) alSearchCriteria.get(14));//15 start
		          cllStm.setString(16,(String) alSearchCriteria.get(15));//16  end
		          
		          cllStm.setLong(17,(Long) hospSeqID);//18 hospSeqID
		         
		          cllStm.registerOutParameter(18,OracleTypes.CURSOR);//20
		          cllStm.execute();
		           
		            //Clasims sEARCH lIST
		            rs = (java.sql.ResultSet)cllStm.getObject(18);
		            ArrayList<PbmPreAuthVO> alPreAuthSearchVOs	=	new ArrayList<PbmPreAuthVO>();
		            if(rs != null){
		                while (rs.next()) {
		                	pbmPreAuthVO	=	new PbmPreAuthVO();
		                			                			                	
		                	pbmPreAuthVO.setClaimNo(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
		                	pbmPreAuthVO.setClmSeqId((Long)TTKCommon.checkNull(rs.getLong("claim_seq_id")));
		                	pbmPreAuthVO.setEnrolmentID(TTKCommon.checkNull(rs.getString("tpa_enrollment_id")));
		                	pbmPreAuthVO.setMemberName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
		                			                	
		                	pbmPreAuthVO.setInvoiceNo(TTKCommon.checkNull(rs.getString("INVOICE_NUMBER")));
		                	pbmPreAuthVO.setStatus(TTKCommon.checkNull(rs.getString("status")));
		                	pbmPreAuthVO.setDateOfTreatment(TTKCommon.checkNull(rs.getString("PRESCRIPTION_DATE")));
		                	
		                	
		                	pbmPreAuthVO.setAuthorizationNO(TTKCommon.checkNull(rs.getString("Auth_number")));
		                	
		                	
		                	
		                	//pbmPreAuthVO.setDispenseStatus(TTKCommon.checkNull(rs.getString("Dispensed_status")));
		                	
		                	
		                	pbmPreAuthVO.setTotalApprAmt(rs.getBigDecimal("APPROVED_AMOUNT"));
		                			                	
		                	pbmPreAuthVO.setClaimSubmittedDate(TTKCommon.checkNull(rs.getString("DISPENSED_DATE")));
		                	pbmPreAuthVO.setClmPayStatus(TTKCommon.checkNull(rs.getString("payment_status")));
		                			                	
		                 	 pbmPreAuthVO.setSettlementNO(rs.getString("SETTLEMENT_NUMBER"));
		            		 
		            		 
		            		 if("Approved".equals(rs.getString("status"))){
			            			
			            			pbmPreAuthVO.setClaimStatus("APR");
			            		 }
		            		 else if("Rejected".equals(rs.getString("status"))){
		            			 pbmPreAuthVO.setClaimStatus("REJ");
		            		 }
		            		 
		            		 if("In Progress".equals(rs.getString("status"))){
		            			
		            			pbmPreAuthVO.setSuppressLink(strSuppressLink);
		            	      }
		                	alPreAuthSearchVOs.add(pbmPreAuthVO);
		                	 
		                }//end of while(rs.next())
		            }//end of if(rs != null)
		            
		            return alPreAuthSearchVOs;
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
		        	closeResource("getPbmClaimList()",rs,cllStm,conn);
				}
					
		    
			}//public ArrayList<PbmPreAuthVO> getPbmPreAuthList(ArrayList<Object> alData) throws TTKException{
			
	
	
	
	
	

	//getPBMSubmitClaim
	
	public PbmPreAuthVO getPBMSubmitClaim(ArrayList<Object> claimData) throws TTKException{
		
		        Connection conn = null;
		        CallableStatement cllStm=null;
		       
		      
		       
		        ResultSet rs=null;
		        PbmPreAuthVO pbmPreAuthVO=new PbmPreAuthVO();
		        
		        try{
		            conn = ResourceManager.getConnection();
		            cllStm	=	conn.prepareCall(strSubmitClaim);
		         
		            cllStm.setLong(1,(Long)claimData.get(0));
		            cllStm.setString(2,(String)claimData.get(1));
		            cllStm.setString(3,(String)claimData.get(2));
		            cllStm.registerOutParameter(4,oracle.jdbc.OracleTypes.CURSOR );
		            	
		             cllStm.execute();
		             rs=(ResultSet)cllStm.getObject(4);
		             
		             if(rs!=null){
		            	 while(rs.next()){
		            		 pbmPreAuthVO.setInvoiceNo(rs.getString("INVOICE_NUMBER"));
		            		 pbmPreAuthVO.setBatchNo(rs.getString("BATCH_NO"));
		            		 pbmPreAuthVO.setClmSeqId(rs.getLong("claim_seq_id"));
		            		 pbmPreAuthVO.setClmBatchSeqId(rs.getLong("CLM_BATCH_SEQ_ID"));
		            		 pbmPreAuthVO.setClaimType(rs.getString("CLAIM_TYPE"));
		            		 pbmPreAuthVO.setClaimNo(rs.getString("CLAIM_NUMBER"));
		            		 pbmPreAuthVO.setClaimSubmittedDate(rs.getString("CLM_RECEIVED_DATE"));
		            		 pbmPreAuthVO.setClaimedAmount(rs.getString("TOT_ALLOWED_AMOUNT"));
		            		 pbmPreAuthVO.setClaimStatus(rs.getString("CLM_STATUS_TYPE_ID"));
		        		     pbmPreAuthVO.setClinicianID(rs.getString("CLINICIAN_ID"));
		            		 pbmPreAuthVO.setClinicianName(rs.getString("CLINICIAN_NAME"));
		            		 pbmPreAuthVO.setPolicyNo(rs.getString("policy_number"));
		            		 pbmPreAuthVO.setMemberName(rs.getString("mem_name"));
		            	 }
		             }
		            	
		            	 
		            return pbmPreAuthVO;
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
		        	closeResource("getPBMSubmitClaim()",rs,cllStm,conn);
				}
					
		    
			}//public ArrayList<PbmPreAuthVO> getPbmPreAuthList(ArrayList<Object> alData) throws TTKException{
	
	
	//claim details
	
	 public PbmPreAuthVO getClaimDetails(Long claimSeqNO) throws TTKException
	 {
			
	        Connection conn = null;
	        CallableStatement cllStm=null;
	       
	      
	       
	        ResultSet rs=null;
	        PbmPreAuthVO pbmPreAuthVO=new PbmPreAuthVO();
	        
	        try{
	            conn = ResourceManager.getConnection();
	            cllStm	=	conn.prepareCall(strclaimDetails);
	         
	            cllStm.setLong(1,(Long)claimSeqNO);
	            
	            cllStm.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR );
	            	
	             cllStm.execute();
	             rs=(ResultSet)cllStm.getObject(2);
	             
	             if(rs!=null){
	            	 while(rs.next()){
	            		 pbmPreAuthVO.setInvoiceNo(rs.getString("INVOICE_NUMBER"));
	            		 pbmPreAuthVO.setBatchNo(rs.getString("BATCH_NO"));
	            		 pbmPreAuthVO.setClmSeqId(rs.getLong("claim_seq_id"));
	            		 pbmPreAuthVO.setClmBatchSeqId(rs.getLong("CLM_BATCH_SEQ_ID"));
	            		 pbmPreAuthVO.setClaimType(rs.getString("CLAIM_TYPE"));   
	            		 pbmPreAuthVO.setClaimNo(rs.getString("CLAIM_NUMBER"));
	            		 pbmPreAuthVO.setClaimSubmittedDate(rs.getString("CLM_RECEIVED_DATE"));
	            		 pbmPreAuthVO.setClaimedAmount(rs.getString("TOT_ALLOWED_AMOUNT"));
	            		 pbmPreAuthVO.setClaimStatus(rs.getString("CLM_STATUS_TYPE_ID"));
	        		     pbmPreAuthVO.setClinicianID(rs.getString("CLINICIAN_ID"));
	            		 pbmPreAuthVO.setClinicianName(rs.getString("CLINICIAN_NAME"));
	            		 pbmPreAuthVO.setPolicyNo(rs.getString("policy_number"));
	            		 pbmPreAuthVO.setMemberName(rs.getString("mem_name"));
	            		 
	            		 pbmPreAuthVO.setSettlementNO(rs.getString("SETTLEMENT_NUMBER"));
	            	 }
	             }
	            	
	            	 
	            return pbmPreAuthVO;
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
	        	closeResource("getClaimDetails()",rs,cllStm,conn);
			}
				
	    
		}//public ArrayList<PbmPreAuthVO> getPbmPreAuthList(ArrayList<Object> alData) throws TTKException{


	
	
	
	
	
	
	
	
	
	
	//SavePBMUploadFile
	
	 public long SavePBMUploadFile(String preAuthSeqID,String generateType,FormFile formFile) throws TTKException
	 {
			
	        Connection conn = null;
	        CallableStatement cllStm=null;     
	       
        try{ 
	        	byte[] iStream	=	formFile.getFileData();
	            conn = ResourceManager.getConnection();
	            cllStm	=	conn.prepareCall(strPBMUploadFile);
	         
	            cllStm.setString(1,(String)preAuthSeqID);
	            cllStm.setString(2,(String)formFile.getFileName());
	            cllStm.setString(3,(String)generateType);
	            cllStm.setBytes(4,iStream);
	            cllStm.registerOutParameter(5,oracle.jdbc.OracleTypes.INTEGER);
	            	
	             cllStm.execute();
	         long count= cllStm.getInt(5);
	             
	             
	            	 
	            return count;
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
	        	closeResource("SavePBMUploadFile()",null,cllStm,conn);
			}
				
	    
		}//public ArrayList<PbmPreAuthVO> getPbmPreAuthList(ArrayList<Object> alData) throws TTKException{



	
	

private void closeResource(String methodName,ResultSet  rs,Statement stm,Connection con){
try{
	try{
		if(rs!=null)rs.close();	
	    if(stm!=null)stm.close();
	   }catch(Exception e){		  
		   log.error("Error while closing the Connection Resource in OnlinePbmProviderDAOImpl "+methodName,e);
		   }
	if(con!=null)con.close();
}catch(Exception e){
	rs=null;
	stm=null;
	con=null;
	 log.error("Error while closing the Connection Resource in OnlinePbmProviderDAOImpl "+methodName,e);
}
}//private void closeResource(ResultSet  rs,Statement stm,Connection con){

public ActivityDetailsVO getTariffDetails(PbmPreAuthVO pbmPreAuthVO)throws TTKException {
	
	
	Connection conn = null;
    CallableStatement cllStm=null;
    ResultSet rsDrug=null;    
    try{
    	ActivityDetailsVO activityDetailsVO=null;
        conn = ResourceManager.getConnection();
        cllStm	=	conn.prepareCall(strGetTariffDetails);
        cllStm.setLong(1,pbmPreAuthVO.getHospitalSeqID());
        cllStm.setString(2,pbmPreAuthVO.getEnrolmentID());
        cllStm.setLong(3,pbmPreAuthVO.getDrugCodeSeqID());
        cllStm.setString(4,pbmPreAuthVO.getDateOfTreatment());
        cllStm.setString(5,pbmPreAuthVO.getUnitType());
        
        cllStm.registerOutParameter(6,OracleTypes.CURSOR);                
        cllStm.execute();
        rsDrug=(ResultSet)cllStm.getObject(6);
         
         if(rsDrug!=null){
        	 if(rsDrug.next()){
        		 activityDetailsVO=new ActivityDetailsVO();
        		 //activityDetailsVO.setActivityCodeDesc(rsDrug.getString("ACTIVITY_DESCRIPTION"));        		 
        		 activityDetailsVO.setGrossAmount(rsDrug.getBigDecimal("GROSS_AMOUNT"));
        		 activityDetailsVO.setDiscount(rsDrug.getBigDecimal("UNIT_DISCOUNT"));     
        		 activityDetailsVO.setActivityCode(rsDrug.getString("ACTIVITY_CODE"));     
        		 activityDetailsVO.setStartDate(rsDrug.getString("START_DATE"));     
        		 activityDetailsVO.setActivityServiceType(rsDrug.getString("ACTIVITY_TYPE_SEQ_ID"));     
        
        	 }
     }
        return activityDetailsVO;
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
    	closeResource("getTariffDetails()",rsDrug,cllStm,conn);
	}
}

public long requstAuthWebservice(Long preAuthSeqID, PbmPreAuthVO pbmPreAuthVO) throws TTKException{
	Connection conn = null;
    CallableStatement webserviceData=null;
    ResultSet rsDrug=null;    
    try{
        conn = ResourceManager.getConnection();
        webserviceData = conn.prepareCall(strWebserviceCode);
        System.out.println("xmlInputType:before:::"+pbmPreAuthVO.getInputPBMXML());
        System.out.println("xmlResponseType::before::"+pbmPreAuthVO.getResponsePBMXML());
        
          XMLType  xmlInputType =   XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), pbmPreAuthVO.getInputPBMXML());
          XMLType  xmlResponseType =   XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), pbmPreAuthVO.getResponsePBMXML());
          System.out.println("xmlInputType::::"+xmlInputType);
          System.out.println("xmlResponseType::::"+xmlResponseType);

          
        webserviceData.setObject(1, xmlInputType);
        webserviceData.setObject(2, xmlResponseType);
        webserviceData.setString(3, "PAT");
        webserviceData.setLong(4,preAuthSeqID);
        webserviceData.setLong(5,pbmPreAuthVO.getAddedBy());            
        webserviceData.execute();
        long flag =1l;
        return flag;
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
    	closeResource("requstAuthWebservice()",rsDrug,webserviceData,conn);
	}
}
}//end of OnlinePbmProviderDAOImpl
