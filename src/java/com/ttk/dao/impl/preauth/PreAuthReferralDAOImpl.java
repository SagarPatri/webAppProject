/**
 *  @ (#) PreAuthReferralDAOImpl.java April 10, 2006
 *   Project 	   : TTK HealthCare Services
 * File          : PreAuthReferralDAOImpl.java
 * Author      : Kishor Kumar S H
 * Company     : RCS
 * Date Created: 08 Dec 2016
 *
 * @author 		 : Kishor Kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :    :
 */

package com.ttk.dao.impl.preauth;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
//added as per KOC 1142 and 1140 Change Request and 1165
import com.ttk.dao.ResourceManager;
import com.ttk.dto.preauth.PreAuthReferralVO;

public class PreAuthReferralDAOImpl implements BaseDAO, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(PreAuthReferralDAOImpl.class );
	private static final String strGetProvDetails = "{CALL PREAUTH_REFERRAL_LETTER_PKG_GET_PROVIDER_DETAILS (?,?)}"; 
	private static final String strGetMemData	  = "{CALL PREAUTH_REFERRAL_LETTER_PKG_GET_MEMBER_DETAILS (?,?)}"; 
	private static final String strSaveReferralDetails	= "{CALL PREAUTH_REFERRAL_LETTER_PKG_SAVE_REFERRAL_LETTER (?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetReferralDetails 	= "{CALL PREAUTH_REFERRAL_LETTER_PKG_GET_REFERRAL_LETTERS (?,?)}";
	private static final String strReferralList 	= "{CALL PREAUTH_REFERRAL_LETTER_PKG_SELECT_REFERRAL_LETTER (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSendMail			= "{CALL PREAUTH_REFERRAL_LETTER_PKG_REFERAL_LETTER_MAIL_HOSP (?,?,?)}";
	private static final String strGetMailSentStatus = "SELECT COALESCE(TRL.LETTER_SENT_YN,'N')  LETTER_SENT_YN,REFERRAL_DATE FROM APP.TPA_REFERRAL_LETTER  TRL WHERE TRL.REFERRAL_SEQ_ID=?";

	/*
	 * Search Referrals 
	 */
	 public ArrayList<PreAuthReferralVO> getReferralList(ArrayList<?> alSearchObjects) throws TTKException
	    {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PreAuthReferralVO referralVO=null;
		ArrayList<PreAuthReferralVO> alResultList = new ArrayList<PreAuthReferralVO>();
		try{
			
			conn=ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strReferralList);
			cStmtObject.setString(1,(String)alSearchObjects.get(0));//referralID
			cStmtObject.setString(2,(String)alSearchObjects.get(1));//vidalMemberID
			cStmtObject.setString(3,(String)alSearchObjects.get(2));//globalNetID
			cStmtObject.setString(4,(String)alSearchObjects.get(3));//patOrMemName
			cStmtObject.setString(5,(String)alSearchObjects.get(4));//memCompName
			cStmtObject.setString(6,(String)alSearchObjects.get(5));//sPolicyNumber
			cStmtObject.setString(7,(String)alSearchObjects.get(6));//vidalBranch
			cStmtObject.setString(8,(String)alSearchObjects.get(7));//status
			cStmtObject.setString(9,(String)alSearchObjects.get(8));//providerName
			cStmtObject.setString(10,(String)alSearchObjects.get(9));//payerName
			
			if(!"".equals(TTKCommon.checkNull(alSearchObjects.get(10)))){
			cStmtObject.setString(11,(String)alSearchObjects.get(10));//ltrFromDt
			   }
			else{
					cStmtObject.setString(11,null);
				}//end of else				
			if(!"".equals(TTKCommon.checkNull(alSearchObjects.get(11)))){
				 cStmtObject.setString(12,(String)alSearchObjects.get(11));//ltrFromDt
			   }
			else{
					cStmtObject.setString(12,null);
				}//end of else	
			cStmtObject.setString(13,(String)alSearchObjects.get(12));//sort var
			cStmtObject.setString(14,(String)alSearchObjects.get(13));//sort order
			cStmtObject.setString(15,(String)alSearchObjects.get(14));//
			cStmtObject.setString(16,(String)alSearchObjects.get(15));//end number
			cStmtObject.registerOutParameter(17,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(17);
			conn.commit();
			if(rs != null){
				while(rs.next()){
					referralVO = new PreAuthReferralVO();
					if(rs.getString("REFERRAL_SEQ_ID") != null){
						referralVO.setReferralSeqId(new Long(rs.getLong("REFERRAL_SEQ_ID")));
					}//end of if(rs.getString("REFERRAL_SEQ_ID") != null)
					referralVO.setReferralId(TTKCommon.checkNull(rs.getString("REFERRAL_ID")));
					referralVO.setProviderId(TTKCommon.checkNull(rs.getString("PROVIDER_ID")));
					referralVO.setProviderName(TTKCommon.checkNull(rs.getString("PROVIDER_NAME")));
					referralVO.setAddress(TTKCommon.checkNull(rs.getString("PROVIDER_ADDRESS")));
					referralVO.setMemberId(TTKCommon.checkNull(rs.getString("MEMBER_ID")));
					referralVO.setMemOrPatName(TTKCommon.checkNull(rs.getString("PATORMEMNAME")));
					referralVO.setPatCompName(TTKCommon.checkNull(rs.getString("PATCOMPNAME")));
					referralVO.setSpeciality(TTKCommon.checkNull(rs.getString("SPECIALITY")));
					referralVO.setOtherMessages(TTKCommon.checkNull(rs.getString("OTHER_MESSAGES")));
					referralVO.setReferredDate(TTKCommon.checkNull(rs.getString("REFERRED_DATE")));
					referralVO.setStatus(TTKCommon.checkNull(rs.getString("STATUS")));
					referralVO.setUser(TTKCommon.checkNull(rs.getString("EDIT_BY")));
					
					alResultList.add(referralVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getHospClaimsList",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement inOnlineAccessDAOImpl getHospClaimsList",sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{

						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getHospClaimsList",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return alResultList;
	}//end of getReferralList(ArrayList alSearchObjects)
	 
	 
	public String[] getProviderDetails(String linceseNo) throws TTKException{
		Connection conn = null;
		ResultSet rs = null;
		String provData[]	=	new String[5];
		CallableStatement cStmtObject=null;
		try{
	        conn = ResourceManager.getConnection();
	        cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetProvDetails);
	        cStmtObject.setString(1,linceseNo);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			conn.commit();
			if(rs!=null){
			if(rs.next()){
				provData	=	new String[5];
				provData[0]	=	TTKCommon.checkNull(rs.getString("PROVIDER_NAME"));
				provData[1]	=	TTKCommon.checkNull(rs.getString("PROVIDER_ADDRESS"));
				provData[2]	=	TTKCommon.checkNull(rs.getString("PROVIDER_CONTNCTNO"));
				provData[3]	=	TTKCommon.checkNull(rs.getString("PROVIDER_MAIL_ID"));
				provData[4]	=	TTKCommon.checkNull(rs.getString("PROVIDER_ID"));
				}			
			}
			return provData;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "preauth");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "preauth");
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
					log.error("Error while closing the Resultset in PreAuthDAOImpl getPreAuthDetail()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthDAOImpl getPreAuthDetail()",sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthDAOImpl getPreAuthDetail()",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getObservDetails(long lngPreAuthSeqID,long lngUserSeqID,String strSelectionType)
	
	
	/*
	 * Get MemberName on Member ID
	 */
	public String[] getMemberDetails(String memberId) throws TTKException{
		Connection conn = null;
		ResultSet ors = null;
		String[] memName	=	null;
		CallableStatement cStmtObject=null;
		try{
	        conn = ResourceManager.getConnection();
	        cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetMemData);
	        cStmtObject.setString(1,memberId);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.execute();
			ors = (java.sql.ResultSet)cStmtObject.getObject(2);
			conn.commit();
			if(ors!=null){
			if(ors.next()){
				memName	=	new String[2];
				memName[0]	=	ors.getString("PATIENT_NAME");
				memName[1]	=	ors.getString("GROUP_NAME");
				}			
			}
			return memName;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "preauth");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "preauth");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (ors != null) ors.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthDAOImpl getPreAuthDetail()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthDAOImpl getPreAuthDetail()",sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthDAOImpl getPreAuthDetail()",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				ors = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of String getMemberDetails(String memberId)
	
	
	
	public Long saveReferralDetails(PreAuthReferralVO saveReferralVO)throws TTKException{
		
		ResultSet rs = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
	    
		long lngRefSeqId	=	0;
		try
		{
	        conn = ResourceManager.getConnection();
	        cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveReferralDetails);
	        if(saveReferralVO.getReferralSeqId()!=null)
	        	cStmtObject.setLong(1,saveReferralVO.getReferralSeqId());
	        else
	        	cStmtObject.setLong(1,0);
	        cStmtObject.registerOutParameter(2,Types.VARCHAR);
	        cStmtObject.setString(3,saveReferralVO.getProviderId());
	        cStmtObject.setString(4,saveReferralVO.getCasereferBy());	
	        cStmtObject.setString(5,saveReferralVO.getMemberId());	
	        cStmtObject.setLong(6,Long.parseLong(saveReferralVO.getSpeciality()));	
	        cStmtObject.setString(7,saveReferralVO.getContentOfLetter());		
	        cStmtObject.setString(8,saveReferralVO.getOtherMessages());	
	        cStmtObject.setLong(9,saveReferralVO.getAddedBy());			
	        cStmtObject.setString(10,saveReferralVO.getEmailId());											
	        cStmtObject.registerOutParameter(11,Types.INTEGER);	//rowsprocessed		
	        cStmtObject.registerOutParameter(1,Types.BIGINT);
	        cStmtObject.execute();
	        lngRefSeqId = cStmtObject.getLong(1);//Seq Id
	        conn.commit();
			return lngRefSeqId;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "preauth");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "preauth");
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
					log.error("Error while closing the Resultset in PreAuthDAOImpl saveReferralDetails()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthDAOImpl saveReferralDetails()",sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthDAOImpl saveReferralDetails()",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of PreAuthReferralVO saveReferralDetails(PreAuthReferralVO saveReferralVO)
	
	
	
	public PreAuthReferralVO getReferralDetails(Long lRefSeqId)throws TTKException {
		
		ResultSet rs 						=	null;
		Connection conn 					= 	null;
		PreAuthReferralVO referralVO		=	null;
    	CallableStatement cStmtObject		=	null;
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetReferralDetails);
			cStmtObject.setLong(1,lRefSeqId);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			conn.commit();
			if(rs != null){
				referralVO = new PreAuthReferralVO();
				if(rs.next()){
					referralVO.setProviderName(TTKCommon.checkNull(rs.getString("PROVIDER_NAME")));
					referralVO.setProviderId(TTKCommon.checkNull(rs.getString("PROVIDER_ID")));
					referralVO.setAddress(TTKCommon.checkNull(rs.getString("PROVIDER_ADDRESS")));
					referralVO.setContactNo(TTKCommon.checkNull(rs.getString("PROVIDER_CONTNCTNO")));
					referralVO.setEmailId(TTKCommon.checkNull(rs.getString("PROVIDER_MAIL_ID")));
					referralVO.setCasereferBy(TTKCommon.checkNull(rs.getString("PROFESSIONAL_ID")));
					referralVO.setSpeciality(TTKCommon.checkNull(rs.getString("SPECIALIST_NAME")));
					referralVO.setReferralId(TTKCommon.checkNull(rs.getString("REFERRAL_ID")));
					referralVO.setPatCompName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					referralVO.setReferredDate(TTKCommon.checkNull(rs.getString("REFERRAL_DATE")));
					referralVO.setUser(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					referralVO.setMemberId(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					referralVO.setMemOrPatName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
					referralVO.setOtherMessages(TTKCommon.checkNull(rs.getString("OTH_MSG")));
					referralVO.setContentOfLetter(TTKCommon.checkNull(rs.getString("CONTENT_OF_LETTER")));
					referralVO.setReferralSeqId(TTKCommon.getLong(rs.getString("REFERRAL_SEQ_ID")));
					referralVO.setLetterYN(TTKCommon.checkNull(rs.getString("LETTER_SENT_YN")));
					referralVO.setLetterGenYN(TTKCommon.checkNull(rs.getString("LETTER_GENERATED_YN")));
				}
			}
			return referralVO;
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
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
						throw new TTKException(sqlExp, "hospital");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
							throw new TTKException(sqlExp, "hospital");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "hospital");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of PreAuthReferralVO getReferralDetails(Long lRefSeqId)
	
public Long mailSend(Long lRefSeqId,Long userSeqId)throws TTKException{
		
		ResultSet rs = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
	    
		long lngRefSeqId	=	0;
		try
		{
	        conn = ResourceManager.getConnection();
	        cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSendMail);
	        cStmtObject.setLong(1,lRefSeqId);
	        cStmtObject.setLong(2,userSeqId);	
	        cStmtObject.registerOutParameter(3,Types.BIGINT);	//Message sequence id
	        cStmtObject.execute();
	        lngRefSeqId = cStmtObject.getLong(3);//Seq Id
	        conn.commit();
			return lngRefSeqId;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "preauth");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "preauth");
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
					log.error("Error while closing the Resultset in PreAuthDAOImpl saveReferralDetails()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthDAOImpl saveReferralDetails()",sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthDAOImpl saveReferralDetails()",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of Long mailSend(Long lRefSeqId,Long userSeqId)



public String[] getmailSentStatus(Long lRefSeqId)throws TTKException {
	Connection conn = null;
	PreparedStatement pStmt = null;
	ResultSet ors = null;
	String[] ltrSntOrNt	=	null;
	try{
		conn = ResourceManager.getConnection();
		pStmt = conn.prepareStatement(strGetMailSentStatus);
		pStmt.setLong(1,lRefSeqId);
		ors = (java.sql.ResultSet)pStmt.executeQuery();
	if(ors!=null){
		ltrSntOrNt	=	new String[2];
		if(ors.next()){
			ltrSntOrNt[0]	=	ors.getString("LETTER_SENT_YN");
			ltrSntOrNt[1]	=	ors.getString("REFERRAL_DATE");
			}			
		}
	
		return ltrSntOrNt;
	}//end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "preauth");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "preauth");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
			{
				if (ors != null) ors.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in PreAuthDAOImpl getPreAuthDetail()",sqlExp);
				throw new TTKException(sqlExp, "preauth");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{

				try
				{
					if (pStmt != null) pStmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in PreAuthDAOImpl getPreAuthDetail()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in PreAuthDAOImpl getPreAuthDetail()",sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "preauth");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			ors = null;
			pStmt = null;
			conn = null;
		}//end of finally
	}//end of finally
}//end of String getmailSentStatus(Long lRefSeqId)
}//end of PreAuthReferralDAOImpl