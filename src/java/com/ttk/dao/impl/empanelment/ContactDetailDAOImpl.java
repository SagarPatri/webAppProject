/**
 * @ (#) ContactsDetailDaoImpl.java Sep 27, 2005
 * Project      :
 * File         : ContactsDetailDaoImpl.java
 * Author       : Suresh.M
 * Company      :
 * Date Created : Sep 27, 2005
 *
 * @author       :  Suresh.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.empanelment;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.ReportVO;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.ContactVO;
import com.ttk.dto.empanelment.HospitalDetailVO;

public class ContactDetailDAOImpl implements BaseDAO,Serializable{
	
	private static Logger log = Logger.getLogger(ContactDetailDAOImpl.class);

	// Hospital Search Query
//	private static final String strHospitalContactList = "SELECT * FROM (SELECT A.*,DENSE_RANK() OVER(ORDER BY #) Q FROM (SELECT A.CONTACT_SEQ_ID,A.CONTACT_NAME,B.CONTACT_DESCRIPTION,A.PROVIDE_ACCESS_USER_YN,A.ACTIVE_YN  FROM APP.TPA_USER_CONTACTS A LEFT OUTER JOIN APP.TPA_HOSP_CONTACT_CODE B ON (A.CONTACT_TYPE_ID = B.CONTACT_TYPE_ID ) WHERE HOSP_SEQ_ID = ?";
	private static final String strHospitalContactList = "SELECT * FROM (SELECT A.*,DENSE_RANK() OVER(ORDER BY #) Q FROM (SELECT A.CONTACT_SEQ_ID,A.CONTACT_NAME,B.CONTACT_DESCRIPTION,A.PROVIDE_ACCESS_USER_YN,A.ACTIVE_YN  FROM APP.TPA_USER_CONTACTS A LEFT OUTER JOIN APP.TPA_HOSP_CONTACT_CODE B ON (A.CONTACT_TYPE_ID = B.CONTACT_TYPE_ID ) WHERE HOSP_SEQ_ID = ?";
	// Insurance Search Query

//	private static final String strHospitalContactList1 = "SELECT A.CONTACT_SEQ_ID,a.designation_type_id,c.designation_description as designation,A.CONTACT_NAME,coalesce(a.primary_email_id,a.secondary_email_id) as email_id,a.mobile_no,B.CONTACT_DESCRIPTION as department,a.contact_type_id,A.PROVIDE_ACCESS_USER_YN,A.ACTIVE_YN FROM  app.TPA_USER_CONTACTS A LEFT OUTER JOIN app.TPA_HOSP_CONTACT_CODE B ON (A.CONTACT_TYPE_ID = B.CONTACT_TYPE_ID) LEFT OUTER JOIN app.tpa_designation_code c on (c.designation_type_id = a.designation_type_id) WHERE A.HOSP_PROF_ID is null and HOSP_SEQ_ID = ?";
	private static final String strHospitalContactList1 = "SELECT A.CONTACT_SEQ_ID,a.designation_type_id,c.designation_description as designation,A.CONTACT_NAME,coalesce(a.primary_email_id,a.secondary_email_id) as email_id,a.mobile_no,B.CONTACT_DESCRIPTION as department,a.contact_type_id,A.PROVIDE_ACCESS_USER_YN,A.ACTIVE_YN FROM  app.TPA_USER_CONTACTS A LEFT OUTER JOIN app.TPA_HOSP_CONTACT_CODE B ON (A.CONTACT_TYPE_ID = B.CONTACT_TYPE_ID) LEFT OUTER JOIN app.tpa_designation_code c on (c.designation_type_id = a.designation_type_id) WHERE A.HOSP_PROF_ID is null and HOSP_SEQ_ID = ?";
	private static final String strLicenseDetailList = "SELECT A.CONTACT_SEQ_ID,A.PROFESSIONAL_ID ,A.CONTACT_NAME AS DR_NAME,A.VALID_FROM_DATE AS START_DATE,A.VALID_TO_DATE AS END_DATE,A.PROF_AUTHORITY,NULL  AS DEPARTMENT,NULL  AS CONTACT_TYPE_ID,A.ACTIVE_YN FROM app.TPA_HOSP_PROFESSIONALS A WHERE A.HOSP_SEQ_ID=?";
	
	
	private static final String strInsuranceCompContactList="SELECT * FROM (SELECT B.*,DENSE_RANK() OVER(ORDER BY B.PRIMARY_EMAIL_ID ASC) Q FROM  (SELECT A.PRIMARY_EMAIL_ID,A.CONTACT_SEQ_ID,A.CONTACT_NAME,B.DESIGNATION_DESCRIPTION ,A.PROVIDE_ACCESS_USER_YN, A.ACTIVE_YN  FROM APP.TPA_USER_CONTACTS A LEFT OUTER JOIN APP.TPA_DESIGNATION_CODE B ON (A.DESIGNATION_TYPE_ID  = B.DESIGNATION_TYPE_ID  )WHERE INS_SEQ_ID = ?";
	//added for broker login
	
	//private static final String strBrokerCompContactList="SELECT * FROM (SELECT A.*,DENSE_RANK() OVER(ORDER BY #,ROWNUM) Q FROM (SELECT A.CONTACT_SEQ_ID,A.CONTACT_NAME,B.DESIGNATION_DESCRIPTION ,A.PROVIDE_ACCESS_USER_YN,A.ACTIVE_YN ,A.PRIMARY_EMAIL_ID,A.OFF_PHONE_NO_1,C.USER_ID,D.ROLE_SEQ_ID,G.ROLE_NAME  FROM TPA_USER_CONTACTS A LEFT OUTER JOIN TPA_DESIGNATION_CODE B ON (A.DESIGNATION_TYPE_ID  = B.DESIGNATION_TYPE_ID  ) LEFT OUTER JOIN TPA_LOGIN_INFO C ON (A.CONTACT_SEQ_ID = C.CONTACT_SEQ_ID) LEFT OUTER JOIN TPA_USER_ROLES D ON (A.CONTACT_SEQ_ID = D.CONTACT_SEQ_ID) LEFT OUTER JOIN TPA_ROLES_CODE G ON (G.ROLE_SEQ_ID = D.ROLE_SEQ_ID) WHERE INS_SEQ_ID = ?";
	 //AK
	private static final String strBrokerCompContactList= "SELECT * FROM (SELECT A.*,DENSE_RANK() OVER(ORDER BY #) Q FROM(SELECT A.INS_SEQ_ID,A.CONTACT_SEQ_ID,A.CONTACT_NAME,B.DESIGNATION_DESCRIPTION ,A.PROVIDE_ACCESS_USER_YN,A.ACTIVE_YN  ,A.PRIMARY_EMAIL_ID,A.OFF_PHONE_NO_1,C.USER_ID,D.ROLE_SEQ_ID,G.ROLE_NAME  FROM APP.TPA_USER_CONTACTS A LEFT OUTER JOIN APP.TPA_DESIGNATION_CODE B ON (A.DESIGNATION_TYPE_ID = B.DESIGNATION_TYPE_ID)  LEFT OUTER JOIN APP.TPA_LOGIN_INFO C  ON (A.CONTACT_SEQ_ID = C.CONTACT_SEQ_ID) LEFT OUTER JOIN APP.TPA_USER_ROLES D ON (A.CONTACT_SEQ_ID = D.CONTACT_SEQ_ID) LEFT OUTER  JOIN APP.TPA_ROLES_CODE G ON (G.ROLE_SEQ_ID = D.ROLE_SEQ_ID)  WHERE INS_SEQ_ID = ?";
	//Group Registration Search Query
	//Changed as per 11g 
	//	private static final String strGroupRegContactList = "SELECT * FROM(SELECT A.*, DENSE_RANK() OVER(ORDER BY #,ROWNUM) Q FROM (SELECT A.CONTACT_SEQ_ID ,A.CONTACT_NAME, B.DESIGNATION_DESCRIPTION,A.PROVIDE_ACCESS_USER_YN,A.ACTIVE_YN FROM TPA_USER_CONTACTS A JOIN TPA_DESIGNATION_CODE B ON (A.DESIGNATION_TYPE_ID = B.DESIGNATION_TYPE_ID) JOIN TPA_GROUP_REGISTRATION  C ON (A.GROUP_REG_SEQ_ID = C.GROUP_REG_SEQ_ID) WHERE GROUP_REG_SEQ_ID = ?";

	private static final String strGroupRegContactList = "SELECT * FROM (SELECT B.*,DENSE_RANK() OVER(ORDER BY B.PRIMARY_EMAIL_ID ASC) Q FROM  (SELECT A.PRIMARY_EMAIL_ID,A.CONTACT_SEQ_ID,A.CONTACT_NAME,B.DESIGNATION_DESCRIPTION ,A.PROVIDE_ACCESS_USER_YN, A.ACTIVE_YN  FROM APP.TPA_USER_CONTACTS A JOIN APP.TPA_DESIGNATION_CODE B ON (A.DESIGNATION_TYPE_ID = B.DESIGNATION_TYPE_ID) JOIN APP.TPA_GROUP_REGISTRATION  C ON (A.GROUP_REG_SEQ_ID = C.GROUP_REG_SEQ_ID) WHERE A.GROUP_REG_SEQ_ID = ?";
	
	private static final String strContactInactivate = "{CALL CONTACT_PKG_INACTIVATE_CONTACTS(?,?,?)}";

	private static final String strFinanceContactList = "SELECT * FROM (SELECT B.*,DENSE_RANK() OVER(ORDER BY #) Q FROM (SELECT A.BANK_SEQ_ID,A.DESIGNATION_TYPE_ID,A.CONTACT_SEQ_ID,A.CONTACT_NAME,B.DESIGNATION_DESCRIPTION,A.ACTIVE_YN FROM app.TPA_USER_CONTACTS A LEFT OUTER JOIN app.TPA_DESIGNATION_CODE B ON (A.DESIGNATION_TYPE_ID = B.DESIGNATION_TYPE_ID) WHERE A.BANK_SEQ_ID = ?)B) A WHERE BANK_SEQ_ID = ?";

	private static final String strHospitalList = "{call inscomp_empanel_pkg_Select_ins_hosp_list(?,?,?,?,?,?,?,?,?,?,?,?)}";//Insurance_corporate_wise_hosp_network
    
    private static final String strAssociateHospitalInfo = "{CALL inscomp_empanel_pkg_ins_hosp_associate_save(?,?,?,?,?)}";//Insurance_corporate_wise_hosp_network
   
    
    private static final String strAssociateHospitalGroupInfo = "{CALL CONTACT_PKG_corp_hosp_associate_save(?,?,?,?,?)}";//Insurance_corporate_wise_hosp_network
    
    private static final String strAssociateHospitalExcInsInfo = "{CALL inscomp_empanel_pkg_ins_hosp_associate_delete(?,?,?)}";//Insurance_corporate_wise_hosp_network
   
    private static final String strAssociateHospitalExcGroupInfo = "{CALL CONTACT_PKG_corp_hosp_associate_delete(?,?,?)}";//Insurance_corporate_wise_hosp_network
   
	private static String strGetPrfessionalDeatils	=	"select m.clinitian_id as profession_id,m.professional_name,m.clini_standard as standard, m.start_date,m.end_date from app.dha_clinicians_list_master m WHERE clinitian_id=?";//
	
	private static final String strPartnerContactList = "SELECT TAB.* FROM (SELECT D.*,DENSE_RANK() OVER(ORDER BY # ) Q FROM (SELECT A.CONTACT_SEQ_ID,A.CONTACT_NAME,B.CONTACT_DESCRIPTION,A.PROVIDE_ACCESS_USER_YN,  A.ACTIVE_YN ,A.ptnr_seq_id FROM APP.TPA_USER_CONTACTS A LEFT OUTER JOIN APP.TPA_HOSP_CONTACT_CODE B ON (A.CONTACT_TYPE_ID = B.CONTACT_TYPE_ID )) D) TAB where TAB.ptnr_seq_id =  ?";
	private static final String strPartnerContactList1 = "SELECT A.CONTACT_SEQ_ID,a.DESIGNATION as designation,A.CONTACT_NAME,COALESCE(a.primary_email_id,a.secondary_email_id) as email_id,a.mobile_no,B.CONTACT_DESCRIPTION as department,a.contact_type_id,A.PROVIDE_ACCESS_USER_YN,A.ACTIVE_YN FROM APP.TPA_USER_CONTACTS A LEFT OUTER JOIN APP.TPA_HOSP_CONTACT_CODE B ON (A.CONTACT_TYPE_ID = B.CONTACT_TYPE_ID) WHERE A.PTNR_USER_TYPE ='PAT'  AND  PTNR_SEQ_ID = ? order by CONTACT_NAME asc";  

	private static final String strPartnerContactList2 = "SELECT A.CONTACT_SEQ_ID,a.DESIGNATION as designation,A.CONTACT_NAME,COALESCE(a.primary_email_id,a.secondary_email_id) as email_id,a.mobile_no,B.CONTACT_DESCRIPTION as department,a.contact_type_id,A.PROVIDE_ACCESS_USER_YN,A.ACTIVE_YN FROM APP.TPA_USER_CONTACTS A LEFT OUTER JOIN APP.TPA_HOSP_CONTACT_CODE B ON (A.CONTACT_TYPE_ID = B.CONTACT_TYPE_ID) WHERE A.PTNR_USER_TYPE ='CLM'  AND  PTNR_SEQ_ID = ?";  
	
	
	private static final String strPartnerContactList3 = "SELECT A.CONTACT_SEQ_ID,a.DESIGNATION as designation,A.CONTACT_NAME,COALESCE(a.primary_email_id,a.secondary_email_id) as email_id,a.mobile_no,B.CONTACT_DESCRIPTION as department,a.contact_type_id,A.PROVIDE_ACCESS_USER_YN,A.ACTIVE_YN FROM APP.TPA_USER_CONTACTS A LEFT OUTER JOIN APP.TPA_HOSP_CONTACT_CODE B ON (A.CONTACT_TYPE_ID = B.CONTACT_TYPE_ID) WHERE A.PTNR_USER_TYPE ='ACC'  AND  PTNR_SEQ_ID = ?";  

	private static final String strProviderProfessionalContact = "SELECT I.HOSP_LICENC_NUMB AS PROVIDER_ID,I.HOSP_NAME AS PROVIDER_NAME,P.PROFESSIONAL_ID AS PROFFESSIONAL_ID,P.CONTACT_NAME AS CLINICIAN_NAME,GC.DESCRIPTION AS CONSULTATION_TYPE,M.SPECIALTY AS SPECIALITY,P.ACTIVE_YN AS STATUS,to_char(P.VALID_FROM_DATE,'DD/MM/YYYY') AS START_DATE,case when P.GENDER_GENERAL_TYPE='MAL' then  'MALE' when P.GENDER_GENERAL_TYPE='FEM' then  'FEMALE' end  AS GENDER,NC.DESCRIPTION AS NATIONALITY FROM app.TPA_HOSP_INFO I JOIN app.TPA_HOSP_PROFESSIONALS P ON (I.HOSP_SEQ_ID = P.HOSP_SEQ_ID)JOIN app.TPA_GENERAL_CODE GC  ON (GC.GENERAL_TYPE_ID = P.CONSULT_GEN_TYPE) JOIN app.DHA_CLNSN_SPECIALTIES_MASTER M ON (M.SPECIALTY_ID = P.SPECIALITY_ID)LEFT JOIN app.TPA_NATIONALITIES_CODE NC ON (NC.NATIONALITY_ID = P.NATIONALITY_ID) WHERE I.HOSP_SEQ_ID=?";

	//private static final String strProviderGeneralContact = "SELECT UC.CONTACT_NAME as Name, DC.DESIGNATION_DESCRIPTION as Designation, UC.PRIMARY_EMAIL_ID as Primary_Email, UC.SECONDARY_EMAIL_ID as Alternate_Email, UC.OFF_PHONE_NO_1 as Phone,  UC.PROVIDE_ACCESS_USER_YN as Provider_User_Access,  RC.ROLE_NAME as User_Role, L.USER_ID as User_ID, CC.CONTACT_DESCRIPTION as Contact_Type   FROM TPA_HOSP_INFO I   JOIN TPA_USER_CONTACTS UC  ON (I.HOSP_SEQ_ID = UC.HOSP_SEQ_ID) JOIN TPA_DESIGNATION_CODE DC  ON (DC.DESIGNATION_TYPE_ID = UC.DESIGNATION_TYPE_ID)   LEFT JOIN TPA_USER_ROLES R     ON (R.CONTACT_SEQ_ID = UC.CONTACT_SEQ_ID)   LEFT JOIN TPA_ROLES_CODE RC     ON (RC.ROLE_SEQ_ID = R.ROLE_SEQ_ID)  LEFT JOIN TPA_LOGIN_INFO L ON (L.CONTACT_SEQ_ID = UC.CONTACT_SEQ_ID) LEFT JOIN TPA_HOSP_CONTACT_CODE CC ON (CC.CONTACT_TYPE_ID = UC.CONTACT_TYPE_ID) WHERE I.HOSP_SEQ_ID = ?";
	
	private static final String strProviderGeneralContact = "SELECT UC.CONTACT_NAME as Name, DC.DESIGNATION_DESCRIPTION as Designation, UC.PRIMARY_EMAIL_ID as Primary_Email, UC.SECONDARY_EMAIL_ID as Alternate_Email, UC.OFF_PHONE_NO_1 as Phone,  UC.PROVIDE_ACCESS_USER_YN as Provider_User_Access,  RC.ROLE_NAME as User_Role, L.USER_ID as User_ID, CC.CONTACT_DESCRIPTION as Contact_Type   FROM app.TPA_HOSP_INFO I   JOIN app.TPA_USER_CONTACTS UC  ON (I.HOSP_SEQ_ID = UC.HOSP_SEQ_ID) JOIN app.TPA_DESIGNATION_CODE DC  ON (DC.DESIGNATION_TYPE_ID = UC.DESIGNATION_TYPE_ID)   LEFT JOIN app.TPA_USER_ROLES R     ON (R.CONTACT_SEQ_ID = UC.CONTACT_SEQ_ID)   LEFT JOIN app.TPA_ROLES_CODE RC     ON (RC.ROLE_SEQ_ID = R.ROLE_SEQ_ID)  LEFT JOIN app.TPA_LOGIN_INFO L ON (L.CONTACT_SEQ_ID = UC.CONTACT_SEQ_ID) LEFT JOIN app.TPA_HOSP_CONTACT_CODE CC ON (CC.CONTACT_TYPE_ID = UC.CONTACT_TYPE_ID) WHERE I.HOSP_SEQ_ID = ?";
	/**
	 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ContactVO object's which contains the contact details
	 * @exception throws TTKException
	 */
	public ArrayList getContactList(ArrayList alSearchObjects)throws TTKException{
		String strIdentifier = (String)alSearchObjects.get(0);
		if ((strIdentifier).equalsIgnoreCase("Hospital") || (strIdentifier).equalsIgnoreCase("PROFESSIONAL_ID"))
		{
			return getHospitalContactList(alSearchObjects);
		}// End of if ((strIdentifier).equalsIgnoreCase("HOSPITAL"))
		else if ((strIdentifier).equalsIgnoreCase("Partner"))
		{
			return getPartnerContactList(alSearchObjects);
		}// End of else if ((strIdentifier).equalsIgnoreCase("PARTNER"))
		else if ((strIdentifier).equalsIgnoreCase("Insurance"))
		{
			return getInsuranceContactList(alSearchObjects);
		}// End of else if ((strIdentifier).equalsIgnoreCase("INSURANCE"))
		//added for kocb broker login
		else if ((strIdentifier).equalsIgnoreCase("Broker"))
		{
			return  getBrokerContactList(alSearchObjects);
		}// End of else if ((strIdentifier).equalsIgnoreCase("INSURANCE"))
		else if ((strIdentifier).equalsIgnoreCase("Group Registration"))
		{
			return getGroupRegContactList(alSearchObjects);
		}//end of else If ((strIdentifier).equalsIgnoreCase("GROUPREGISTRATION"))
		else if ((strIdentifier).equalsIgnoreCase("Banks"))
		{
			return getFinanceContactList(alSearchObjects);
		}//end of else If ((strIdentifier).equalsIgnoreCase("Finance"))
		return null;
	}//end of getContactList(ArrayList alSearchObjects)

	
	/**
	 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ContactVO object's which contains the contact details
	 * @exception throws TTKException
	 */
	
	
	
	
	public ArrayList getHospitalList(ArrayList alSearchObjects) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
		HospitalDetailVO hospitalDetailVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strHospitalList);
			cStmtObject.setString(1,(String)alSearchObjects.get(0));
			cStmtObject.setString(2,(String)alSearchObjects.get(1));
			cStmtObject.setString(3,(String)alSearchObjects.get(2));
			cStmtObject.setString(4,(String)alSearchObjects.get(3));
			cStmtObject.setString(5,(String)alSearchObjects.get(4));
			cStmtObject.setString(6,(String)alSearchObjects.get(5));
			cStmtObject.setLong(7,(Long)alSearchObjects.get(6));
			cStmtObject.setString(8,(String)alSearchObjects.get(7));
			cStmtObject.setString(9,(String)alSearchObjects.get(8));
			cStmtObject.setString(10,(String)alSearchObjects.get(9));
			cStmtObject.setString(11,(String)alSearchObjects.get(10));						
			cStmtObject.registerOutParameter(12,Types.OTHER);
	//		System.out.println("Search Query..."+strHospitalList);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(12);
			if(rs != null){
				while(rs.next()){
					hospitalDetailVO = new HospitalDetailVO();
					
					if(rs.getString("HOSP_SEQ_ID") != null){
						hospitalDetailVO.setHospSeqId(new Long(rs.getLong("HOSP_SEQ_ID")));
					}
				
					hospitalDetailVO.setHospName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					hospitalDetailVO.setEmpanelmentNO(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));

			
					hospitalDetailVO.setOfficeInfo(TTKCommon.checkNull(rs.getString("STATE_NAME")));
					hospitalDetailVO.setCityDesc(TTKCommon.checkNull(rs.getString("CITY_DESCRIPTION")));


					alResultList.add(hospitalDetailVO);
					conn.commit();
				}//end of while(rs.next())
			}//end of if(rs != null)
			conn.commit();
			return (ArrayList)alResultList;
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
					log.error("Error while closing the Resultset in ContactDetailDAOImpl getHospitalList()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ContactDetailDAOImpl getHospitalList()",sqlExp);
						throw new TTKException(sqlExp, "contacts");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ContactDetailDAOImpl getHospitalList()",sqlExp);
							throw new TTKException(sqlExp, "contacts");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "contacts");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPreAuthHospitalList(ArrayList alSearchCriteria)

	 public int associateHospitalList(ArrayList alAssoicateHospList,Long lngInsuranceSeqID,String strAssociateCode) throws TTKException {
	        int iResult = 0;
	        Connection conn = null;
	        CallableStatement cStmtObject=null;
	        try{
	            if(alAssoicateHospList != null && alAssoicateHospList.size() > 0) {
	                conn = ResourceManager.getConnection();
	                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssociateHospitalInfo);
	                cStmtObject.setString(1, (String)alAssoicateHospList.get(0));//string of hospital sequence id's which are separated with | as separator (Note: String should also end with | at the end)
	                cStmtObject.setLong(2,lngInsuranceSeqID);//PROD_POLICY_SEQ_ID
	                cStmtObject.setString(3,strAssociateCode);//STATUS_GENERAL_TYPE_ID
	                cStmtObject.setLong(4, (Long)alAssoicateHospList.get(1));//user sequence id
	                cStmtObject.registerOutParameter(5, Types.INTEGER);//out parameter which gives the number of records deleted
	                cStmtObject.execute();
	                iResult = cStmtObject.getInt(5);
	                conn.commit();

	            }//end of if(alAssocExcludedList != null && alAssocExcludedList.size() > 0)
	        }//end of try
	        catch (SQLException sqlExp)
	        {
	              throw new TTKException(sqlExp, "contacts");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	              throw new TTKException(exp, "contacts");
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
	        			log.error("Error while closing the Statement in ContactDetailDAOImpl associateHospitalList()",sqlExp);
	        			throw new TTKException(sqlExp, "contacts");
	        		}//end of catch (SQLException sqlExp)
	        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	        		{
	        			try
	        			{
	        				if(conn != null) conn.close();
	        			}//end of try
	        			catch (SQLException sqlExp)
	        			{
	        				log.error("Error while closing the Connection in ContactDetailDAOImpl associateHospitalList()",sqlExp);
	        				throw new TTKException(sqlExp, "contacts");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "contacts");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects 
	        	{
	        		cStmtObject = null;
	        		conn = null;
	        	}//end of finally
			}//end of finally
	        return iResult;
	    }//end of associateExcludeHospital(Long lProdPolicySeqId,String strStatusGeneralTypeId,ArrayList alAssocExcludedList)
	 
	 
	 public int associateHospitalGroupList(ArrayList alAssoicateHospList,Long lngGroupRegSeqId,String strAssociateCode) throws TTKException {
	        int iResult = 0;
	        Connection conn = null;
	        CallableStatement cStmtObject=null;
	        try{
	            if(alAssoicateHospList != null && alAssoicateHospList.size() > 0) {
	                conn = ResourceManager.getConnection();
	                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssociateHospitalGroupInfo);
	                cStmtObject.setString(1, (String)alAssoicateHospList.get(0));//string of hospital sequence id's which are separated with | as separator (Note: String should also end with | at the end)
	                cStmtObject.setLong(2,lngGroupRegSeqId);//PROD_POLICY_SEQ_ID
	                cStmtObject.setString(3,strAssociateCode);//STATUS_GENERAL_TYPE_ID
	                cStmtObject.setLong(4, (Long)alAssoicateHospList.get(1));//user sequence id
	                cStmtObject.registerOutParameter(5, Types.INTEGER);//out parameter which gives the number of records deleted
	                cStmtObject.execute();
	                iResult = cStmtObject.getInt(5);
	                conn.commit();

	            }//end of if(alAssocExcludedList != null && alAssocExcludedList.size() > 0)
	        }//end of try
	        catch (SQLException sqlExp)
	        {
	              throw new TTKException(sqlExp, "contacts");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	              throw new TTKException(exp, "contacts");
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
	        			log.error("Error while closing the Statement in ContactDetailDAOImpl associateHospitalList()",sqlExp);
	        			throw new TTKException(sqlExp, "contacts");
	        		}//end of catch (SQLException sqlExp)
	        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	        		{
	        			try
	        			{
	        				if(conn != null) conn.close();
	        			}//end of try
	        			catch (SQLException sqlExp)
	        			{
	        				log.error("Error while closing the Connection in ContactDetailDAOImpl associateHospitalList()",sqlExp);
	        				throw new TTKException(sqlExp, "contacts");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "contacts");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects 
	        	{
	        		cStmtObject = null;
	        		conn = null;
	        	}//end of finally
			}//end of finally
	        return iResult;
	    }//end of associateExcludeHospital(Long lProdPolicySeqId,String strStatusGeneralTypeId,ArrayList alAssocExcludedList)
	 
	 public int associateHospitalExcInsList(ArrayList alAssoicateHospList) throws TTKException {
	        int iResult = 0;
	        Connection conn = null;
	        CallableStatement cStmtObject=null;
	        try{
	            if(alAssoicateHospList != null && alAssoicateHospList.size() > 0) {
	                conn = ResourceManager.getConnection();

	                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssociateHospitalExcInsInfo);
	                cStmtObject.setString(1, (String)alAssoicateHospList.get(0));//string of hospital sequence id's which are separated with | as separator (Note: String should also end with | at the end)
	                cStmtObject.setLong(2, (Long)alAssoicateHospList.get(1));//user sequence id
	                cStmtObject.registerOutParameter(3, Types.INTEGER);//out parameter which gives the number of records deleted
	                cStmtObject.execute();
	                iResult = cStmtObject.getInt(3);
	                conn.commit();

	            }//end of if(alAssocExcludedList != null && alAssocExcludedList.size() > 0)
	        }//end of try
	        catch (SQLException sqlExp)
	        {
	              throw new TTKException(sqlExp, "contacts");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	              throw new TTKException(exp, "contacts");
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
	        			log.error("Error while closing the Statement in ContactDetailDAOImpl associateHospitalList()",sqlExp);
	        			throw new TTKException(sqlExp, "contacts");
	        		}//end of catch (SQLException sqlExp)
	        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	        		{
	        			try
	        			{
	        				if(conn != null) conn.close();
	        			}//end of try
	        			catch (SQLException sqlExp)
	        			{
	        				log.error("Error while closing the Connection in ContactDetailDAOImpl associateHospitalList()",sqlExp);
	        				throw new TTKException(sqlExp, "contacts");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "contacts");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects 
	        	{
	        		cStmtObject = null;
	        		conn = null;
	        	}//end of finally
			}//end of finally
	        return iResult;
	    }//end of associateExcludeHospital(Long lProdPolicySeqId,String strStatusGeneralTypeId,ArrayList alAssocExcludedList)
	 
	 
	 public int associateHospitalExcGroupList(ArrayList alAssoicateHospList) throws TTKException {
	        int iResult = 0;
	        Connection conn = null;
	        CallableStatement cStmtObject=null;
	        try{
	            if(alAssoicateHospList != null && alAssoicateHospList.size() > 0) {
	                conn = ResourceManager.getConnection();

	                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssociateHospitalExcGroupInfo);
	                cStmtObject.setString(1, (String)alAssoicateHospList.get(0));//string of hospital sequence id's which are separated with | as separator (Note: String should also end with | at the end)
	                cStmtObject.setLong(2, (Long)alAssoicateHospList.get(1));//user sequence id
	                cStmtObject.registerOutParameter(3, Types.INTEGER);//out parameter which gives the number of records deleted
	                cStmtObject.execute();
	                iResult = cStmtObject.getInt(3);
	                conn.commit();

	            }//end of if(alAssocExcludedList != null && alAssocExcludedList.size() > 0)
	        }//end of try
	        catch (SQLException sqlExp)
	        {
	              throw new TTKException(sqlExp, "contacts");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	              throw new TTKException(exp, "contacts");
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
	        			log.error("Error while closing the Statement in ContactDetailDAOImpl associateHospitalList()",sqlExp);
	        			throw new TTKException(sqlExp, "contacts");
	        		}//end of catch (SQLException sqlExp)
	        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	        		{
	        			try
	        			{
	        				if(conn != null) conn.close();
	        			}//end of try
	        			catch (SQLException sqlExp)
	        			{
	        				log.error("Error while closing the Connection in ContactDetailDAOImpl associateHospitalList()",sqlExp);
	        				throw new TTKException(sqlExp, "contacts");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "contacts");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects 
	        	{
	        		cStmtObject = null;
	        		conn = null;
	        	}//end of finally
			}//end of finally
	        return iResult;
	    }//end of associateExcludeHospital(Long lProdPolicySeqId,String strStatusGeneralTypeId,ArrayList alAssocExcludedList)
	
	 
	 
	 
	/**
	 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ContactVO object's which contains the Hospital contact details
	 * @exception throws TTKException
	 */
	public ArrayList getHospitalContactList(ArrayList alSearchObjects)throws TTKException{
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery = strHospitalContactList;
		String strStaticQuery1 = strHospitalContactList1;
		ContactVO contactVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		String strHospitalSeqId = ((SearchCriteria)alSearchObjects.get(1)).getValue();
		strStaticQuery1 = TTKCommon.replaceInString(strStaticQuery1,"?", strHospitalSeqId);
		/*strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?", strHospitalSeqId);
		if(alSearchObjects != null && alSearchObjects.size() > 0)
		{
			for(int i=2; i < alSearchObjects.size()-4; i++)
			{
				if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
				{
					if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
					{
						sbfDynamicQuery.append(" AND "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
					}//end of if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
					else
					{
						sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
					}//end of else
				}// End of if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
			}//end of for(int i=2; i < alSearchObjects.size(); i++)
		}//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
		sbfDynamicQuery = sbfDynamicQuery .append( " )A) WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
		*/
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
	//		System.out.println("Hospital Query..."+ strStaticQuery1);
			System.out.println("strStaticQuery1.."+strStaticQuery1);
			pStmt = conn.prepareStatement(strStaticQuery1);
			rs = pStmt.executeQuery();
			if(rs != null){
				while (rs.next()) {
					contactVO = new ContactVO();
					if (rs.getString("CONTACT_SEQ_ID") != null)
					{
						contactVO.setContactSeqId(new Long(rs.getLong("CONTACT_SEQ_ID")));
					}//end of if (rs.getString("CONTACT_SEQ_ID") != null)
					contactVO.setContactName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					contactVO.setContactDesc(TTKCommon.checkNull(rs.getString("department")));
					contactVO.setDesignation(TTKCommon.checkNull(rs.getString("DESIGNATION")));
					contactVO.setEmail(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					contactVO.setPhone(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
					contactVO.setActiveYn(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
					contactVO.setUserAccessYn(TTKCommon.checkNull(rs.getString("PROVIDE_ACCESS_USER_YN")));
					if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					{
						contactVO.setImageName("UserIcon");
						contactVO.setImageTitle("User");
					}//end of if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					resultList.add(contactVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "contacts");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "contacts");
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
					log.error("Error while closing the Resultset in ContactDetailDAOImpl getHospitalContactList()",sqlExp);
					throw new TTKException(sqlExp, "contacts");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ContactDetailDAOImpl getHospitalContactList()",sqlExp);
						throw new TTKException(sqlExp, "contacts");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ContactDetailDAOImpl getHospitalContactList()",sqlExp);
							throw new TTKException(sqlExp, "contacts");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "contacts");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getHospitalContactList(ArrayList alSearchObjects)
	
	
	/**
	 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ContactVO object's which contains the Hospital contact details
	 * @exception throws TTKException
	 */
	public ArrayList getPartnerContactList(ArrayList alSearchObjects)throws TTKException{
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery = strPartnerContactList;
		String strStaticQuery1 = strPartnerContactList1;
		ContactVO contactVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		String strPartnerSeqId = ((SearchCriteria)alSearchObjects.get(1)).getValue();
		strStaticQuery1 = TTKCommon.replaceInString(strStaticQuery1,"?", strPartnerSeqId);
		/*strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?", strHospitalSeqId);
		if(alSearchObjects != null && alSearchObjects.size() > 0)
		{
			for(int i=2; i < alSearchObjects.size()-4; i++)
			{
				if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
				{
					if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
					{
						sbfDynamicQuery.append(" AND "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
					}//end of if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
					else
					{
						sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
					}//end of else
				}// End of if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
			}//end of for(int i=2; i < alSearchObjects.size(); i++)
		}//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)*/
	/*	strStaticQuery1 = TTKCommon.replaceInString(strStaticQuery1,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
		sbfDynamicQuery = sbfDynamicQuery .append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
		strStaticQuery1= strStaticQuery1 + sbfDynamicQuery.toString();*/
		
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
		//	System.out.println("Contact search..."+strStaticQuery1);
			pStmt = conn.prepareStatement(strStaticQuery1);
			rs = pStmt.executeQuery();
			if(rs != null){
				while (rs.next()) {
					contactVO = new ContactVO();
					if (rs.getString("CONTACT_SEQ_ID") != null)
					{
						contactVO.setContactSeqId(new Long(rs.getLong("CONTACT_SEQ_ID")));
					}//end of if (rs.getString("CONTACT_SEQ_ID") != null)
				
					contactVO.setContactName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					
					contactVO.setContactDesc(TTKCommon.checkNull(rs.getString("department")));
					contactVO.setDesignation(TTKCommon.checkNull(rs.getString("DESIGNATION")));
					contactVO.setEmail(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					contactVO.setPhone(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
					contactVO.setActiveYn(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
					contactVO.setUserAccessYn(TTKCommon.checkNull(rs.getString("PROVIDE_ACCESS_USER_YN")));
					if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					{
						contactVO.setImageName("UserIcon");
						contactVO.setImageTitle("User");
					}//end of if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					resultList.add(contactVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "contacts");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "contacts");
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
					log.error("Error while closing the Resultset in ContactDetailDAOImpl getPartnerContactList()",sqlExp);
					throw new TTKException(sqlExp, "contacts");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ContactDetailDAOImpl getPartnerContactList()",sqlExp);
						throw new TTKException(sqlExp, "contacts");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ContactDetailDAOImpl getPartnerContactList()",sqlExp);
							throw new TTKException(sqlExp, "contacts");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "contacts");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPartnerContactList(ArrayList alSearchObjects)
	
	
	/**
	 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ContactVO object's which contains the Hospital contact details
	 * @exception throws TTKException
	 */
	public ArrayList getContactListIntx(Long lngHospitalSeqId)throws TTKException{
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery1 = strHospitalContactList1;
		ContactVO contactVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		String strHospitalSeqId = ""+lngHospitalSeqId;
	//	System.out.println("Hospital Sequence id    "+lngHospitalSeqId);
		strStaticQuery1 = TTKCommon.replaceInString(strStaticQuery1,"?", strHospitalSeqId);
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
		//	System.out.println("Contact details query.."+strStaticQuery1);
			pStmt = conn.prepareStatement(strStaticQuery1);
			rs = pStmt.executeQuery();
			if(rs != null){
				while (rs.next()) {
					contactVO = new ContactVO();
					if (rs.getString("CONTACT_SEQ_ID") != null)
					{
						contactVO.setContactSeqId(rs.getLong("CONTACT_SEQ_ID"));
					}//end of if (rs.getString("CONTACT_SEQ_ID") != null)
					contactVO.setContactName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					contactVO.setContactDesc(TTKCommon.checkNull(rs.getString("department")));
					contactVO.setDesignation(TTKCommon.checkNull(rs.getString("DESIGNATION")));
					contactVO.setEmail(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					contactVO.setPhone(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
					contactVO.setActiveYn(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
					contactVO.setUserAccessYn(TTKCommon.checkNull(rs.getString("PROVIDE_ACCESS_USER_YN")));
					if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					{
						contactVO.setImageName("UserIcon");
						contactVO.setImageTitle("User");
					}//end of if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					resultList.add(contactVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			conn.commit();
			return (ArrayList)resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "contacts");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "contacts");
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
					log.error("Error while closing the Resultset in ContactDetailDAOImpl getHospitalContactList()",sqlExp);
					throw new TTKException(sqlExp, "contacts");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ContactDetailDAOImpl getHospitalContactList()",sqlExp);
						throw new TTKException(sqlExp, "contacts");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ContactDetailDAOImpl getHospitalContactList()",sqlExp);
							throw new TTKException(sqlExp, "contacts");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "contacts");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getHospitalContactList(ArrayList alSearchObjects)
	
	
	/**
	 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ContactVO object's which contains the Hospital contact details
	 * @exception throws TTKException
	 */
	public ArrayList getLicenseList(Long lngHospitalSeqId)throws TTKException{
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery = strLicenseDetailList;
		ContactVO contactVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		String strHospitalSeqId = ""+lngHospitalSeqId;
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?", strHospitalSeqId);
		
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strStaticQuery);
			rs = pStmt.executeQuery();
			if(rs != null){
				while (rs.next()) {
					contactVO = new ContactVO();
					if (rs.getString("CONTACT_SEQ_ID") != null)
					{
						contactVO.setContactSeqId(new Long(rs.getLong("CONTACT_SEQ_ID")));
					}//end of if (rs.getString("CONTACT_SEQ_ID") != null)
					contactVO.setDoctorName(TTKCommon.checkNull(rs.getString("DR_NAME")));
					contactVO.setLicenseNumb(TTKCommon.checkNull(rs.getString("professional_id")));
					contactVO.setAuthType(TTKCommon.checkNull(rs.getString("prof_authority")));
					
					if(rs.getTimestamp("start_date") != null)
						contactVO.setEffectiveFrom(new Date(rs.getTimestamp("start_date").getTime()));
					
					if(rs.getTimestamp("end_date") != null)
						contactVO.setEffectiveTo(new Date(rs.getTimestamp("end_date").getTime()));
					
					resultList.add(contactVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "contacts");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "contacts");
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
					log.error("Error while closing the Resultset in ContactDetailDAOImpl getHospitalContactList()",sqlExp);
					throw new TTKException(sqlExp, "contacts");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ContactDetailDAOImpl getHospitalContactList()",sqlExp);
						throw new TTKException(sqlExp, "contacts");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ContactDetailDAOImpl getHospitalContactList()",sqlExp);
							throw new TTKException(sqlExp, "contacts");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "contacts");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getLicenseList(ArrayList alSearchObjects)

	/**
	 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ContactVO object's which contains the Group Registration contact details
	 * @exception throws TTKException
	 */
	public ArrayList getInsuranceContactList(ArrayList alSearchObjects)throws TTKException{
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery = strInsuranceCompContactList;
		ContactVO contactVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		String strInsuranceSeqId = ((SearchCriteria)alSearchObjects.get(1)).getValue();
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?", strInsuranceSeqId);

		
		
		if(alSearchObjects != null && alSearchObjects.size() > 0)
		{
			for(int i=2; i < alSearchObjects.size()-4; i++)
			{
				if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
				{
					if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
					{
						sbfDynamicQuery.append(" AND "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
					}//end of if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
					else
					{
						sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
					}//end of else
				}// End of if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
			}//end of for(int i=2; i < alSearchObjects.size(); i++)
		}//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
		sbfDynamicQuery = sbfDynamicQuery .append( " )B) AB WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
	//	System.out.println("Contact Query.."+strStaticQuery);
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strStaticQuery);
			rs = pStmt.executeQuery();
			if(rs != null){
				while (rs.next()) {
					contactVO = new ContactVO();
					if (rs.getString("CONTACT_SEQ_ID") != null)
					{
						contactVO.setContactSeqId(new Long(rs.getLong("CONTACT_SEQ_ID")));
					}//end of if (rs.getString("CONTACT_SEQ_ID") != null)
					contactVO.setContactName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					contactVO.setContactDesc(TTKCommon.checkNull(rs.getString("DESIGNATION_DESCRIPTION")));
					contactVO.setActiveYn(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
					if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					{
						contactVO.setImageName("UserIcon");
						contactVO.setImageTitle("User");
					}//end of if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					resultList.add(contactVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "contacts");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "contacts");
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
					log.error("Error while closing the Resultset in ContactDetailDAOImpl getInsuranceContactList()",sqlExp);
					throw new TTKException(sqlExp, "contacts");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ContactDetailDAOImpl getInsuranceContactList()",sqlExp);
						throw new TTKException(sqlExp, "contacts");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ContactDetailDAOImpl getInsuranceContactList()",sqlExp);
							throw new TTKException(sqlExp, "contacts");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "contacts");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getInsuranceContactList(ArrayList alSearchObjects)

	/**
	 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ContactVO object's which contains the Insurance contact details
	 * @exception throws TTKException
	 */
	public ArrayList getGroupRegContactList(ArrayList alSearchObjects)throws TTKException{
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery = strGroupRegContactList;
		ContactVO contactVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		String strGroupRegSeqId = ((SearchCriteria)alSearchObjects.get(1)).getValue();
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?", strGroupRegSeqId);
		if(alSearchObjects != null && alSearchObjects.size() > 0)
		{
			for(int i=2; i < alSearchObjects.size()-4; i++)
			{
				if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
				{
					if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
					{
						sbfDynamicQuery.append(" AND "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
					}//end of if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
					else
					{
						sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
					}//end of else
				}// End of if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
			}//end of for(int i=2; i < alSearchObjects.size(); i++)
		}//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
		sbfDynamicQuery = sbfDynamicQuery .append( " )B)AB WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
					

		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
			log.info("................"+strStaticQuery);
			pStmt = conn.prepareStatement(strStaticQuery);
			rs = pStmt.executeQuery();
			if(rs != null){
				while (rs.next()) {
					contactVO = new ContactVO();
					if (rs.getString("CONTACT_SEQ_ID") != null)
					{
						contactVO.setContactSeqId(new Long(rs.getLong("CONTACT_SEQ_ID")));
					}//end of if (rs.getString("CONTACT_SEQ_ID") != null)
					contactVO.setContactName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					contactVO.setContactDesc(TTKCommon.checkNull(rs.getString("DESIGNATION_DESCRIPTION")));
					contactVO.setActiveYn(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
					if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					{
						contactVO.setImageName("UserIcon");
						contactVO.setImageTitle("User");
					}//end of if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					resultList.add(contactVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "contacts");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "contacts");
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
					log.error("Error while closing the Resultset in ContactDetailDAOImpl getGroupRegContactList()",sqlExp);
					throw new TTKException(sqlExp, "contacts");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ContactDetailDAOImpl getGroupRegContactList()",sqlExp);
						throw new TTKException(sqlExp, "contacts");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ContactDetailDAOImpl getGroupRegContactList()",sqlExp);
							throw new TTKException(sqlExp, "contacts");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "contacts");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getGroupRegContactList(ArrayList alSearchObjects)

	/**
	 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ContactVO object's which contains the Group Registration contact details
	 * @exception throws TTKException
	 */
	public ArrayList getFinanceContactList(ArrayList alSearchObjects)throws TTKException{
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery = strFinanceContactList;
		ContactVO contactVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		String strBankSeqId = ((SearchCriteria)alSearchObjects.get(1)).getValue();

		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?", strBankSeqId);
		if(alSearchObjects != null && alSearchObjects.size() > 0)
		{
			for(int i=2; i < alSearchObjects.size()-4; i++)
			{
				if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
				{
					if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
					{	
						sbfDynamicQuery.append(" AND "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
					}//end of if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
					else
					{
						sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
					}//end of else
				}// End of if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
			}//end of for(int i=2; i < alSearchObjects.size(); i++)
		}//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
		sbfDynamicQuery = sbfDynamicQuery .append( " AND Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
		//System.out.println("StaticQuery-----"+strStaticQuery);
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();

			pStmt = conn.prepareStatement(strStaticQuery);
			rs = pStmt.executeQuery();
			if(rs != null){
				while (rs.next()) {
					contactVO = new ContactVO();

					if (rs.getString("CONTACT_SEQ_ID") != null){
						contactVO.setContactSeqId(new Long(rs.getLong("CONTACT_SEQ_ID")));
					}//end of if (rs.getString("CONTACT_SEQ_ID") != null)

					contactVO.setContactName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					contactVO.setContactDesc(TTKCommon.checkNull(rs.getString("DESIGNATION_DESCRIPTION")));
					contactVO.setActiveYn(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
					resultList.add(contactVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "contacts");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "contacts");
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
					log.error("Error while closing the Resultset in ContactDetailDAOImpl getFinanceContactList()",sqlExp);
					throw new TTKException(sqlExp, "contacts");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ContactDetailDAOImpl getFinanceContactList()",sqlExp);
						throw new TTKException(sqlExp, "contacts");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ContactDetailDAOImpl getFinanceContactList()",sqlExp);
							throw new TTKException(sqlExp, "contacts");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "contacts");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getFinanceContactList(ArrayList alSearchObjects)

	/**
	 * This method Activate/InActivate's the contacts records from the database.
	 * @param alContactList ArrayList object which contains the contacts sequence id's to be deleted
	 * @param strIdentifier which identifies the request sender
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int activateInActivateContact(ArrayList alContactList) throws TTKException {
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try
		{
			if(alContactList != null && alContactList.size() > 0)
			{
				conn = ResourceManager.getConnection();
				cStmtObject = conn.prepareCall(strContactInactivate);
				cStmtObject.setString(1, (String)alContactList.get(0));//Concatenated Strings |contact_seq_id|
				cStmtObject.setString(2, (String)alContactList.get(1)); // Activate/InActivate
				cStmtObject.registerOutParameter(3, Types.INTEGER);//out parameter which gives the number of records deleted
				cStmtObject.execute();
				iResult = cStmtObject.getInt(3);
				conn.commit();
			}//end of if(alContactList != null && alContactList.size() > 0)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "contacts");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "contacts");
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
        			log.error("Error while closing the Statement in ContactDetailDAOImpl activateInActivateContact()",sqlExp);
        			throw new TTKException(sqlExp, "contacts");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ContactDetailDAOImpl activateInActivateContact()",sqlExp);
        				throw new TTKException(sqlExp, "contacts");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "contacts");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}// End of activateInActivateContact(ArrayList alContactList,String strIdentifier)
	//added for kocb broker login
	
	public ArrayList getBrokerContactList(ArrayList alSearchObjects)throws TTKException{
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery = strBrokerCompContactList;
		ContactVO contactVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		String strInsuranceSeqId = ((SearchCriteria)alSearchObjects.get(1)).getValue();
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?", strInsuranceSeqId);
		if(alSearchObjects != null && alSearchObjects.size() > 0)
		{
			for(int i=2; i < alSearchObjects.size()-4; i++)
			{
				String strOptr=((SearchCriteria)alSearchObjects.get(i)).getOperator();
				
				if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
				{
					
					if("equals".equalsIgnoreCase(strOptr))
					{
						sbfDynamicQuery.append(" AND "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
					}//end of if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))					
					else
					{
						sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
					}//end of else
				}// End of if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
			}//end of for(int i=2; i < alSearchObjects.size(); i++)
		}//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
		sbfDynamicQuery = sbfDynamicQuery .append( " )A)B WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
	//		System.out.println("Contact details Search.."+strStaticQuery);
			pStmt = conn.prepareStatement(strStaticQuery);
			rs = pStmt.executeQuery();
			if(rs != null){
				while (rs.next()) {
					contactVO = new ContactVO();
					if (rs.getString("CONTACT_SEQ_ID") != null)
					{
						contactVO.setContactSeqId(new Long(rs.getLong("CONTACT_SEQ_ID")));
					}//end of if (rs.getString("CONTACT_SEQ_ID") != null)
					contactVO.setContactName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					contactVO.setContactDesc(TTKCommon.checkNull(rs.getString("DESIGNATION_DESCRIPTION")));
					contactVO.setActiveYn(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
					//AK
					contactVO.setUserRoleBRO(TTKCommon.checkNull(rs.getString("ROLE_NAME")));
					contactVO.setUserID(TTKCommon.checkNull(rs.getString("USER_ID")));
					contactVO.setPrimaryEmailID(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));
					contactVO.setOfficePhoneNO(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
					contactVO.setStatus(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
					if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					{
						contactVO.setImageName("UserIcon");
						contactVO.setImageTitle("User");
					}//end of if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					resultList.add(contactVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "contacts");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "contacts");
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
					log.error("Error while closing the Resultset in ContactDetailDAOImpl getInsuranceContactList()",sqlExp);
					throw new TTKException(sqlExp, "contacts");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ContactDetailDAOImpl getInsuranceContactList()",sqlExp);
						throw new TTKException(sqlExp, "contacts");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ContactDetailDAOImpl getInsuranceContactList()",sqlExp);
							throw new TTKException(sqlExp, "contacts");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "contacts");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getInsuranceContactList(ArrayList alSearchObjects)
	
	/**
     * 
     */
	@SuppressWarnings("null")
	public ArrayList getProfessionalDetails(String profId)throws TTKException{
		
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		ArrayList<String> alProfession	=	null;
		try
		{
			
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strGetPrfessionalDeatils);
			log.info("Query-----"+strGetPrfessionalDeatils);
			log.info("ProfID-------"+profId);
			pStmt.setString(1,profId);  //profId
			rs = pStmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					alProfession	=	new ArrayList<String>();
					log.info("Profession id----"+rs.getString("profession_id"));
					alProfession.add(rs.getString("profession_id"));
					log.info("professional_name----"+rs.getString("professional_name"));
					alProfession.add(rs.getString("professional_name"));
					log.info("standard----"+rs.getString("standard"));
					alProfession.add(rs.getString("standard"));
				}
	            
	            }
			return alProfession;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "contacts");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "contacts");
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
					log.error("Error while closing the Resultset in ContactDetailDAOImpl getProfessionalDetails()",sqlExp);
					throw new TTKException(sqlExp, "contacts");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ContactDetailDAOImpl getProfessionalDetails()",sqlExp);
						throw new TTKException(sqlExp, "contacts");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ContactDetailDAOImpl getProfessionalDetails()",sqlExp);
							throw new TTKException(sqlExp, "contacts");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "contacts");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getProfessionalDetails(ArrayList alProfessionals)
	
	
	/**
	 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ContactVO object's which contains the Hospital contact details
	 * @exception throws TTKException
	 */
	public ArrayList getContactListIntx1(Long lngPartnerSeqId)throws TTKException{
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery1 = strPartnerContactList1;
		ContactVO contactVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		String strPartnerSeqId = ""+lngPartnerSeqId;
		strStaticQuery1 = TTKCommon.replaceInString(strStaticQuery1,"?", strPartnerSeqId);
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strStaticQuery1);
			rs = pStmt.executeQuery();
			if(rs != null){
				while (rs.next()) {
					contactVO = new ContactVO();
					if (rs.getString("CONTACT_SEQ_ID") != null)
					{
						contactVO.setContactSeqId(new Long(rs.getLong("CONTACT_SEQ_ID")));
					}//end of if (rs.getString("CONTACT_SEQ_ID") != null)
					contactVO.setContactName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					contactVO.setContactDesc(TTKCommon.checkNull(rs.getString("department")));
					contactVO.setDesignation(TTKCommon.checkNull(rs.getString("DESIGNATION")));
					contactVO.setEmail(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					contactVO.setPhone(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
					contactVO.setActiveYn(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
					contactVO.setUserAccessYn(TTKCommon.checkNull(rs.getString("PROVIDE_ACCESS_USER_YN")));
					if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					{
						contactVO.setImageName("UserIcon");
						contactVO.setImageTitle("User");
					}//end of if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					resultList.add(contactVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "contacts");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "contacts");
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
					log.error("Error while closing the Resultset in ContactDetailDAOImpl getPartnerContactList()",sqlExp);
					throw new TTKException(sqlExp, "contacts");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ContactDetailDAOImpl getPartnerContactList()",sqlExp);
						throw new TTKException(sqlExp, "contacts");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ContactDetailDAOImpl getPartnerContactList()",sqlExp);
							throw new TTKException(sqlExp, "contacts");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "contacts");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPartnerContactList(ArrayList alSearchObjects)
	
	
	/**
	 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ContactVO object's which contains the Hospital contact details
	 * @exception throws TTKException
	 */
	public ArrayList getContactListIntx2(Long lngPartnerSeqId)throws TTKException{
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery1 = strPartnerContactList2;
		ContactVO contactVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		String strPartnerSeqId = ""+lngPartnerSeqId;
		strStaticQuery1 = TTKCommon.replaceInString(strStaticQuery1,"?", strPartnerSeqId);
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strStaticQuery1);
			rs = pStmt.executeQuery();
			if(rs != null){
				while (rs.next()) {
					contactVO = new ContactVO();
					if (rs.getString("CONTACT_SEQ_ID") != null)
					{
						contactVO.setContactSeqId(new Long(rs.getLong("CONTACT_SEQ_ID")));
					}//end of if (rs.getString("CONTACT_SEQ_ID") != null)
					contactVO.setContactName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					contactVO.setContactDesc(TTKCommon.checkNull(rs.getString("department")));
					contactVO.setDesignation(TTKCommon.checkNull(rs.getString("DESIGNATION")));
					contactVO.setEmail(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					contactVO.setPhone(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
					contactVO.setActiveYn(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
					contactVO.setUserAccessYn(TTKCommon.checkNull(rs.getString("PROVIDE_ACCESS_USER_YN")));
					if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					{
						contactVO.setImageName("UserIcon");
						contactVO.setImageTitle("User");
					}//end of if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					resultList.add(contactVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "contacts");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "contacts");
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
					log.error("Error while closing the Resultset in ContactDetailDAOImpl getPartnerContactList()",sqlExp);
					throw new TTKException(sqlExp, "contacts");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ContactDetailDAOImpl getPartnerContactList()",sqlExp);
						throw new TTKException(sqlExp, "contacts");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ContactDetailDAOImpl getPartnerContactList()",sqlExp);
							throw new TTKException(sqlExp, "contacts");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "contacts");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPartnerContactList(ArrayList alSearchObjects)
	
	/**
	 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ContactVO object's which contains the Hospital contact details
	 * @exception throws TTKException
	 */
	public ArrayList getContactListIntx3(Long lngPartnerSeqId)throws TTKException{
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery1 = strPartnerContactList3;
		ContactVO contactVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		String strPartnerSeqId = ""+lngPartnerSeqId;
		strStaticQuery1 = TTKCommon.replaceInString(strStaticQuery1,"?", strPartnerSeqId);
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strStaticQuery1);
			rs = pStmt.executeQuery();
			if(rs != null){
				while (rs.next()) {
					contactVO = new ContactVO();
					if (rs.getString("CONTACT_SEQ_ID") != null)
					{
						contactVO.setContactSeqId(new Long(rs.getLong("CONTACT_SEQ_ID")));
					}//end of if (rs.getString("CONTACT_SEQ_ID") != null)
					contactVO.setContactName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					contactVO.setContactDesc(TTKCommon.checkNull(rs.getString("department")));
					contactVO.setDesignation(TTKCommon.checkNull(rs.getString("DESIGNATION")));
					contactVO.setEmail(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					contactVO.setPhone(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
					contactVO.setActiveYn(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
					contactVO.setUserAccessYn(TTKCommon.checkNull(rs.getString("PROVIDE_ACCESS_USER_YN")));
					if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					{
						contactVO.setImageName("UserIcon");
						contactVO.setImageTitle("User");
					}//end of if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
					resultList.add(contactVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "contacts");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "contacts");
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
					log.error("Error while closing the Resultset in ContactDetailDAOImpl getPartnerContactList()",sqlExp);
					throw new TTKException(sqlExp, "contacts");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ContactDetailDAOImpl getPartnerContactList()",sqlExp);
						throw new TTKException(sqlExp, "contacts");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ContactDetailDAOImpl getPartnerContactList()",sqlExp);
							throw new TTKException(sqlExp, "contacts");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "contacts");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPartnerContactList(ArrayList alSearchObjects)
	
	public ArrayList getProviderProfessionalContact(Long hospitalSeqId,String downloadtype) throws TTKException
	{
		
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
	    ArrayList<ReportVO> alRresultList	=	null;
	    ReportVO reportVO = null;
		 try{
		       	conn = ResourceManager.getConnection();
		        if("professionalcontact".equals(downloadtype))
		         	pStmt = conn.prepareStatement(strProviderProfessionalContact);
	            else if("gencontact".equals(downloadtype))
	            	pStmt = (java.sql.CallableStatement)conn.prepareCall(strProviderGeneralContact);
	            	
		        	pStmt.setLong(1,hospitalSeqId);
		        	rs= pStmt.executeQuery();
		        	if(rs != null){
		               	alRresultList	=	new ArrayList<ReportVO>();
		            	//GEETING META DATA FOR COLUMN HEADS
		            	 ArrayList<String> alResult	=	null;
		                 ResultSetMetaData metaData	=	rs.getMetaData();
		                 int colmCount				=	metaData.getColumnCount();
		                 if(colmCount>0)
		                 	alResult = new ArrayList<String>();
		                 for(int k=0;k<colmCount;k++)
		                 {
		                	alResult.add(metaData.getColumnName(k+1));
		                 }
		                 reportVO	=	new ReportVO();
		                 reportVO.setAlColumns(alResult);
		                 alRresultList.add(reportVO);

		               //THIS BLOCK IS DATA FROM QUERY
		            	while (rs.next()) {
		            		alResult = new ArrayList<String>();
		                	reportVO = new ReportVO();

		                	for(int l=1;l<=colmCount;l++)
		                		alResult.add(rs.getString(l)==null?"":rs.getString(l));

		                	reportVO.setAlColumns(alResult);
		                    alRresultList.add(reportVO);
		                }//end of while(rs.next())
		            }//end of if(rs != null)
		            return alRresultList;
		        }
		 
		 catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "contacts");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "contacts");
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
						log.error("Error while closing the Resultset in ContactDetailDAOImpl getProviderProfessionalContact()",sqlExp);
						throw new TTKException(sqlExp, "contacts");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (pStmt != null)	pStmt.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in ContactDetailDAOImpl getProviderProfessionalContact()",sqlExp);
							throw new TTKException(sqlExp, "contacts");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in ContactDetailDAOImpl getProviderProfessionalContact()",sqlExp);
								throw new TTKException(sqlExp, "contacts");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "contacts");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{
					rs = null;
					pStmt = null;
					conn = null;
				}//end of finally
			}//end of finally
		       
	}
	
}//end of ContactDetailDAOImpl
