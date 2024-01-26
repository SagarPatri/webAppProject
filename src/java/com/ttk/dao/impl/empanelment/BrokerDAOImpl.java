/**
 * @ (#) BrokerDAOImpl Oct 21, 2005
 * Project      : TTK HealthCare Services
 * File         : InsuranceCompanyDAOImpl.java
 * Author       : Ravindra
 * Company      : Span Systems Corporation
 * Date Created : Oct 21, 2005
 *
 * @author       :  Ravindra

 * Modified by   :  RamaKrishna K M
 * Modified date :  Dec 13, 2005
 * Reason        :  Modification in log statements
 */

package com.ttk.dao.impl.empanelment;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.BrokerDetailVO;
import com.ttk.dto.empanelment.BrokerVO;
import com.ttk.dto.empanelment.ContactVO;










//AK
import oracle.jdbc.driver.OracleTypes;
import sun.security.util.BigInt;

public class BrokerDAOImpl  implements BaseDAO, Serializable{

	private static Logger log = Logger.getLogger(BrokerDAOImpl.class);
	
	private static final int  INS_SEQ_ID = 1 ;
	private static final int  INS_ADDR_SEQ_ID = 2 ;
	private static final int  TPA_OFFICE_SEQ_ID = 3 ;
	private static final int  INS_SECTOR_GENERAL_TYPE_ID = 4;
	private static final int  INS_OFFICE_GENERAL_TYPE_ID = 5;
	private static final int  INS_COMP_NAME = 6 ;
	private static final int  ABBREVATION_CODE   = 7;
	private static final int  INS_COMP_CODE_NUMBER  = 8 ;
	private static final int  EMPANELED_DATE  = 9 ;
	private static final int  DISB_TYPE_ID  = 10 ;
	private static final int  FREQUENCY_TYPE_ID  =11  ;
	private static final int  FLOAT_REPLENISHMENT_PERIOD =12;
	private static final int  ACTIVE_YN  = 13;
	private static final int  INS_PARENT_SEQ_ID  = 14;
	private static final int  ADDRESS_1  = 15 ;
	private static final int  ADDRESS_2  = 16 ;
	private static final int  ADDRESS_3 = 17;
	private static final int  CITY_TYPE_ID  = 18 ;
	private static final int  STATE_TYPE_ID   = 19 ;
	private static final int  PIN_CODE  = 20 ;
	private static final int  COUNTRY_ID   = 21 ;
	private static final int  LANDMARKS  = 22 ;
	private static final int  INS_OFF_SEQ_ID = 23;
	private static final int  VALID_FROM = 24;
	private static final int  VALID_TO = 25;
	private static final int  USER_SEQ_ID = 26;
	
	// Shortfall CR KOC1179
	private static final int  INS_COMPANY_EMAILID  = 27 ;
	private static final int  Licence_No  = 28 ;
	private static final int  Reg_date  = 29 ;
	private static final int  LIC_EXP_DT  = 30 ; //kocBroker
	
	//intx
	private static final int  ISD_CODE = 31;
	private static final int  STD_CODE = 32;
	private static final int  PHONE1 = 33;
	private static final int  PHONE2 = 34;
	//AK
	private static final int  BROKER_AUTHORITY = 35;
		
	private static final int  ROW_PROCESSED = 36;

//	private static final String strBrokerList="SELECT * FROM (SELECT INS_SEQ_ID, INS_COMP_NAME, EMPANELED_DATE, ACTIVE_YN, INS_COMP_CODE_NUMBER,BROKER_AUTHORITY, DENSE_RANK() OVER (ORDER BY #) Q FROM APP.TPA_BRO_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO'" ;
	private static final String strGetBrokerDetails = "SELECT * FROM (SELECT A.INS_SEQ_ID, A.INS_COMP_NAME, A.EMPANELED_DATE, A.ACTIVE_YN, A.INS_COMP_CODE_NUMBER,A.BROKER_AUTHORITY,B.OFFICE_NAME,  DENSE_RANK() OVER (ORDER BY #) Q FROM APP.TPA_BRO_INFO A LEFT OUTER JOIN  APP.TPA_OFFICE_INFO B ON (B.TPA_OFFICE_SEQ_ID = A.TPA_OFFICE_SEQ_ID)WHERE A.INS_OFFICE_GENERAL_TYPE_ID = 'IHO'";
     //AK
  //   private static final String strGetBrokerDetailsList= "SELECT * FROM (SELECT A.*,DENSE_RANK() OVER(ORDER BY #,ROWNUM) Q FROM (SELECT A.INS_SEQ_ID, A.CONTACT_SEQ_ID, A.CONTACT_NAME, B.DESIGNATION_DESCRIPTION , A.PROVIDE_ACCESS_USER_YN, A.ACTIVE_YN , A.PRIMARY_EMAIL_ID, A.OFF_PHONE_NO_1, C.USER_ID, D.ROLE_SEQ_ID, G.ROLE_NAME FROM TPA_USER_CONTACTS A LEFT OUTER JOIN TPA_DESIGNATION_CODE B ON (A.DESIGNATION_TYPE_ID = B.DESIGNATION_TYPE_ID)  LEFT OUTER JOIN TPA_LOGIN_INFO C  ON (A.CONTACT_SEQ_ID = C.CONTACT_SEQ_ID)LEFT OUTER JOIN TPA_USER_ROLES D ON (A.CONTACT_SEQ_ID = D.CONTACT_SEQ_ID)  LEFT OUTER  JOIN TPA_ROLES_CODE G ON (G.ROLE_SEQ_ID = D.ROLE_SEQ_ID) WHERE PROVIDE_ACCESS_USER_YN='Y' and INS_SEQ_ID= ? ";
  private static final String strGetBrokerDetailsList= "SELECT * FROM (SELECT A.*,DENSE_RANK() OVER(ORDER BY #) Q FROM (SELECT * FROM (SELECT A.INS_SEQ_ID, case  when A.CONTACT_SEQ_ID  in (select CONTACT_SEQ_ID from app.TPA_USER_CONTACTS  where INS_SEQ_ID= ? and PROVIDE_ACCESS_USER_YN='Y' and CONTACT_SEQ_ID in (select CONTACT_SEQ_ID  from app.TPA_BRO_USER_CONTACTS  where INS_SEQ_ID= ? and Policy_Seq_Id= !)) then 'Associated' else 'Disassociated' end USER_ASSO_DISASSO,A.CONTACT_SEQ_ID, A.CONTACT_NAME,B.DESIGNATION_TYPE_ID,B.DESIGNATION_DESCRIPTION , A.PROVIDE_ACCESS_USER_YN, CASE A.ACTIVE_YN WHEN 'Y' THEN 'Active' else 'Inactive' end ACTIVE_YN , A.PRIMARY_EMAIL_ID, A.OFF_PHONE_NO_1, C.USER_ID, D.ROLE_SEQ_ID, G.ROLE_NAME FROM app.TPA_USER_CONTACTS A LEFT OUTER JOIN app.TPA_DESIGNATION_CODE B ON (A.DESIGNATION_TYPE_ID = B.DESIGNATION_TYPE_ID)  LEFT OUTER JOIN app.TPA_LOGIN_INFO C  ON (A.CONTACT_SEQ_ID = C.CONTACT_SEQ_ID)LEFT OUTER JOIN app.TPA_USER_ROLES D ON (A.CONTACT_SEQ_ID = D.CONTACT_SEQ_ID)  LEFT OUTER  JOIN app.TPA_ROLES_CODE G ON (G.ROLE_SEQ_ID = D.ROLE_SEQ_ID))B WHERE PROVIDE_ACCESS_USER_YN='Y' and INS_SEQ_ID= ?";
     //private static final String strGetBrokerDetailsList= "SELECT * FROM (SELECT A.*,DENSE_RANK() OVER(ORDER BY #,ROWNUM) Q FROM (SELECT A.INS_SEQ_ID, A.CONTACT_SEQ_ID, A.CONTACT_NAME, B.DESIGNATION_DESCRIPTION , A.PROVIDE_ACCESS_USER_YN, A.ACTIVE_YN , A.PRIMARY_EMAIL_ID, A.OFF_PHONE_NO_1, C.USER_ID, D.ROLE_SEQ_ID, G.ROLE_NAME ,TBI.INS_COMP_CODE_NUMBER AS INS_COMP_CODE_NUMBER FROM TPA_USER_CONTACTS A LEFT OUTER JOIN TPA_DESIGNATION_CODE B ON (A.DESIGNATION_TYPE_ID = B.DESIGNATION_TYPE_ID) LEFT OUTER JOIN TPA_BRO_INFO TBI on( A.INS_SEQ_ID=TBI.INS_SEQ_ID)  LEFT OUTER JOIN TPA_LOGIN_INFO C  ON (A.CONTACT_SEQ_ID = C.CONTACT_SEQ_ID)LEFT OUTER JOIN TPA_USER_ROLES D ON (A.CONTACT_SEQ_ID = D.CONTACT_SEQ_ID)  LEFT OUTER  JOIN TPA_ROLES_CODE G ON (G.ROLE_SEQ_ID = D.ROLE_SEQ_ID) WHERE A.INS_SEQ_ID = ?";
     //Broker TableInfo AK 14/02/2018 
     private static final String strBrokerAssocGrp=" {CALL POLICY_ENROLLMENT_PKG_select_policy_assoc(?,?,?)}";
     private static final String strBrokerDeatils = " {CALL POLICY_ENROLLMENT_PKG_SAVE_POLICY_BROKER_ASSOC(?,?,?,?,?,?)}";
     private static final String strDeleteFloatAssocGrp = " {CALL POLICY_ENROLLMENT_PKG_DELETE_POLICY_BROKER_ASSOC (?,?,?,?,?)}";
     private static final String strsaveBrokerContactDetails = "{CALL POLICY_ENROLLMENT_PKG_SAVE_POLICY_BROKER_USER_ASSOC(?,?,?,?,?,?,?)}";
                                                   
     
     
 	//private static final String strInsureList="SELECT * FROM (SELECT INS_SEQ_ID, INS_COMP_NAME, EMPANELED_DATE, ACTIVE_YN, DENSE_RANK() OVER (ORDER BY #, ROWNUM) Q FROM TPA_INS_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO' " ;
	//private static final String strInsuranceList="SELECT * FROM (SELECT INS_SEQ_ID, INS_COMP_NAME, EMPANELED_DATE, ACTIVE_YN, DENSE_RANK() OVER (ORDER BY #, ROWNUM) Q FROM TPA_INS_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO' " ;
	//kocBroker
	private static final String strInsurancelDetail="SELECT INS_OFFICE_GENERAL_TYPE_ID,TPA_BRO_INFO.INS_PARENT_SEQ_ID,TPA_BRO_INFO.TPA_OFFICE_SEQ_ID,A.INS_COMP_NAME,A.BROKER_AUTHORITY,ACTIVE_YN,INS_COMP_CODE_NUMBER,A.ABBREVATION_CODE,A.INS_SECTOR_GENERAL_TYPE_ID,EMPANELED_DATE,DISB_TYPE_ID,FLOAT_REPLENISHMENT_PERIOD ,FREQUENCY_TYPE_ID,ADDR_SEQ_ID,ADDRESS_1,ADDRESS_2,ADDRESS_3,STATE_TYPE_ID,CITY_TYPE_ID, PIN_CODE,EMAIL_ID, COUNTRY_ID,A.DESCRIPTION,Licence_No,REG_DATE,LIC_EXP_DT,ISD_CODE,STD_CODE,OFFICE_PHONE1,OFFICE_PHONE2 FROM APP.TPA_BRO_INFO, APP.TPA_BRO_ADDRESS,(SELECT INS_SEQ_ID,INS_COMP_NAME,BROKER_AUTHORITY, ABBREVATION_CODE, INS_SECTOR_GENERAL_TYPE_ID,DESCRIPTION FROM APP.TPA_BRO_INFO,APP.TPA_GENERAL_CODE  WHERE INS_PARENT_SEQ_ID IS NULL AND APP.TPA_BRO_INFO.INS_SECTOR_GENERAL_TYPE_ID = GENERAL_TYPE_ID AND INS_SEQ_ID = ?)A WHERE APP.TPA_BRO_INFO.INS_SEQ_ID = APP.TPA_BRO_ADDRESS.INS_SEQ_ID AND APP.TPA_BRO_INFO.INS_SEQ_ID = ?";
	//private static final String strAddUpdateInsuranceInfo="{CALL BROCOMP_EMPANEL_PKG.PR_BRO_INFO_SAVE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//KOC1179  //kocBroker Added one column for License Exp date- Added 4 columns for IntX for phone Nos
	private static final String strAddUpdateInsuranceInfo="{CALL BROCOMP_EMPANEL_PKG_PR_BRO_INFO_SAVE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//KOC1179  //kocBroker Added one column for License Exp date- Added 4 columns for IntX for phone Nos
	private static final String strDeleteInsuranceInfo="{CALL BROCOMP_EMPANEL_PKG_PR_INS_INFO_DELETE(?,?)}";
	//private static final String strGetCompanyDetails = "SELECT * FROM (SELECT A.*,DENSE_RANK() OVER (ORDER BY INS_COMP_NAME,ROWNUM) Q FROM(SELECT INS_SEQ_ID, TPA_OFFICE_SEQ_ID,B.DESCRIPTION, INS_PARENT_SEQ_ID,A.INS_SECTOR_GENERAL_TYPE_ID,A.ABBREVATION_CODE, A.INS_COMP_NAME,CASE WHEN A.INS_OFFICE_GENERAL_TYPE_ID != 'IHO' THEN A.INS_COMP_NAME ||'-'|| A.INS_COMP_CODE_NUMBER ELSE A.INS_COMP_NAME END COMPANY,A.INS_COMP_CODE_NUMBER, INS_OFFICE_GENERAL_TYPE_ID FROM (SELECT  INS_SEQ_ID, TPA_OFFICE_SEQ_ID, INS_PARENT_SEQ_ID,INS_SECTOR_GENERAL_TYPE_ID,INS_COMP_CODE_NUMBER,ABBREVATION_CODE, INS_OFFICE_GENERAL_TYPE_ID ,INS_COMP_NAME FROM TPA_INS_INFO START WITH INS_SEQ_ID = ? ";
	// Shortfall CR KOC1179 added EMAIL_ID
	private static final String strGetCompanyDetails = "SELECT * FROM (SELECT F.*,DENSE_RANK() OVER (ORDER BY F.INS_COMP_NAME) Q FROM( SELECT D.INS_SEQ_ID, D.TPA_OFFICE_SEQ_ID, E.DESCRIPTION, INS_PARENT_SEQ_ID, D.INS_SECTOR_GENERAL_TYPE_ID,D.ABBREVATION_CODE, D.INS_COMP_NAME, CASE WHEN D.INS_OFFICE_GENERAL_TYPE_ID != 'IHO' THEN D.INS_COMP_NAME ||'-'|| D.INS_COMP_CODE_NUMBER ELSE D.INS_COMP_NAME END COMPANY, D.INS_COMP_CODE_NUMBER, D.INS_OFFICE_GENERAL_TYPE_ID, D.BROKER_AUTHORITY, D.EMAIL_ID FROM (WITH RECURSIVE A AS (SELECT  t1.INS_SEQ_ID, t1.TPA_OFFICE_SEQ_ID, t1.INS_PARENT_SEQ_ID,t1.INS_SECTOR_GENERAL_TYPE_ID,t1.INS_COMP_CODE_NUMBER,t1.ABBREVATION_CODE, t1.INS_OFFICE_GENERAL_TYPE_ID ,t1.INS_COMP_NAME,t1.EMAIL_ID,t1.BROKER_AUTHORITY FROM app.TPA_BRO_INFO t1 where t1.INS_SEQ_ID = ?),B as (SELECT  t2.INS_SEQ_ID, t2.TPA_OFFICE_SEQ_ID, t2.INS_PARENT_SEQ_ID,t2.INS_SECTOR_GENERAL_TYPE_ID,t2.INS_COMP_CODE_NUMBER,t2.ABBREVATION_CODE,t2.INS_OFFICE_GENERAL_TYPE_ID ,t2.INS_COMP_NAME,t2.EMAIL_ID,t2.BROKER_AUTHORITY FROM app.TPA_BRO_INFO t2 join A on a.INS_SEQ_ID=t2.INS_PARENT_SEQ_ID),C AS (SELECT  t3.INS_SEQ_ID, t3.TPA_OFFICE_SEQ_ID, t3.INS_PARENT_SEQ_ID,t3.INS_SECTOR_GENERAL_TYPE_ID,t3.INS_COMP_CODE_NUMBER,t3.ABBREVATION_CODE,t3.INS_OFFICE_GENERAL_TYPE_ID ,t3.INS_COMP_NAME,t3.EMAIL_ID ,t3.BROKER_AUTHORITY FROM app.TPA_BRO_INFO t3 join B on B.INS_SEQ_ID=t3.INS_PARENT_SEQ_ID)select * from a union all select * from B UNION ALL select * from C)D";// Shortfall CR KOC1179 getting EMAIL_ID
	private static final String strDisAsscociateBrokseUser = " {CALL POLICY_ENROLLMENT_PKG_del_pol_bro_usr_assoc(?,?,?,?,?,?,?)}";
	
	private static final String strAssoDissoCount="SELECT COUNT(*) COUNTALL FROM (select case  when CONTACT_SEQ_ID  in (select CONTACT_SEQ_ID from  app.TPA_USER_CONTACTS  where INS_SEQ_ID=? and PROVIDE_ACCESS_USER_YN='Y' and CONTACT_SEQ_ID in (select CONTACT_SEQ_ID  from app.TPA_BRO_USER_CONTACTS  where INS_SEQ_ID=? and Policy_Seq_Id=?)) then 'ASSOCIATED' else 'UNASSOCIATED' end status from app.TPA_USER_CONTACTS) A where a.status='ASSOCIATED'";
	
	private static final String strgetAssociatedBrokerDetailsList = "SELECT  A.CONTACT_SEQ_ID,E.INS_SEQ_ID,A.CONTACT_NAME, B.DESIGNATION_DESCRIPTION , A.PROVIDE_ACCESS_USER_YN, A.ACTIVE_YN , A.PRIMARY_EMAIL_ID, A.OFF_PHONE_NO_1, C.USER_ID, D.ROLE_SEQ_ID, G.ROLE_NAME, 'ASSOCIATED' USER_ASSO_DISASSO FROM app.TPA_BRO_USER_CONTACTS E JOIN app.TPA_USER_CONTACTS A ON (E.CONTACT_SEQ_ID=A.CONTACT_SEQ_ID) LEFT OUTER JOIN app.TPA_DESIGNATION_CODE B ON (A.DESIGNATION_TYPE_ID = B.DESIGNATION_TYPE_ID)  LEFT OUTER JOIN app.TPA_LOGIN_INFO C  ON (A.CONTACT_SEQ_ID = C.CONTACT_SEQ_ID)LEFT OUTER JOIN app.TPA_USER_ROLES D ON (A.CONTACT_SEQ_ID = D.CONTACT_SEQ_ID)  LEFT OUTER  JOIN app.TPA_ROLES_CODE G ON (G.ROLE_SEQ_ID = D.ROLE_SEQ_ID) WHERE  E.INS_SEQ_ID=? ::numeric and E.POLICY_SEQ_ID=?";
	private static final String strgetAssociatedBrokerCount = "SELECT   COUNT(*)  BRO_CNT  FROM APP.TPA_BRO_POLICY  WHERE POLICY_SEQ_ID= ?";
	/**
     * This method returns the ArrayList, which contains the list of InsuranceCompany which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of InsuranceCompany object's which contains the InsuranceCompany details
     * @exception throws TTKException
     */
    public ArrayList getBrokerCompanyList(ArrayList alSearchObjects) throws TTKException
	{ 
    	 String strEmpaneledDate   = TTKCommon.checkNull(((SearchCriteria)alSearchObjects.get(0)).getValue());
        StringBuffer sbfDynamicQuery = new StringBuffer();
        String strStaticQuery = "SELECT * FROM (SELECT to_date(to_char(EMPANELED_DATE,'DD/MM/YYYY'),'dd/mm/yyyy'),to_date(to_char(TO_DATE('"+strEmpaneledDate+"','DD/MM/YYYY'),'DD/MM/YYYY'),'dd/mm/yyyy') ,INS_SEQ_ID, INS_COMP_NAME, EMPANELED_DATE, ACTIVE_YN, INS_COMP_CODE_NUMBER,BROKER_AUTHORITY,DENSE_RANK() OVER (ORDER BY #) Q FROM APP.TPA_BRO_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO'";
        Collection<Object> resultList = new ArrayList<Object>();
        BrokerVO  brokerVO = null;
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        if(alSearchObjects != null && alSearchObjects.size() > 0)
        {
          //  String strEmpaneledDate   = TTKCommon.checkNull(((SearchCriteria)alSearchObjects.get(0)).getValue());
            if (!strEmpaneledDate.equals(""))
            {
               // sbfDynamicQuery.append(" AND to_char(EMPANELED_DATE,'DD/MM/YYYY')>= to_char(TO_DATE('"+strEmpaneledDate+"','DD/MM/YYYY'),'DD/MM/YYYY')");
            	sbfDynamicQuery.append(" AND to_date(to_char(EMPANELED_DATE,'DD/MM/YYYY'),'dd/mm/yyyy')>= to_date(to_char(TO_DATE('"+strEmpaneledDate+"' ,'DD/MM/YYYY'),'DD/MM/YYYY'),'dd/mm/yyyy')");
            }//end of if (!strEmpaneledDate.equals(""))
            for(int i=1; i < alSearchObjects.size()-4; i++)
            {
                if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
                {
                    sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
                }//end of if(!((SearchCriteria)alSearchCriteria.get(i)).getValue().equals(""))
                
            }//end of for()
        }//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
        strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
        sbfDynamicQuery = sbfDynamicQuery .append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
      
        try{
            conn = ResourceManager.getConnection();
        //   System.out.println("Search procedure query broker ..."+strStaticQuery);
            pStmt = conn.prepareStatement(strStaticQuery);
            rs = pStmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                	brokerVO = new BrokerVO();


                    if(rs.getString("INS_SEQ_ID") != null){
                    	brokerVO.setInsuranceSeqID(new Long(rs.getLong("INS_SEQ_ID")));
                    }//end of if(rs.getString("INS_SEQ_ID") != null)
                    brokerVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
                    
                    if(rs.getDate("EMPANELED_DATE") != null){
                    	brokerVO.setEmpanelmentDate(rs.getDate("EMPANELED_DATE"));
                    }//end of if(rs.getDate("EMPANELED_DATE") != null)

                    brokerVO.setCompanyStatus(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
                    brokerVO.setActiveInactive(TTKCommon.checkNull(rs.getString("ACTIVE_YN")).equalsIgnoreCase("Y")?"Active":"Inactive");
                    brokerVO.setCompanyCodeNbr(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
                    //AK
                    brokerVO.setRegAuthority(TTKCommon.checkNull(rs.getString("BROKER_AUTHORITY")));
                    resultList.add(brokerVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)resultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "broker");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "broker");
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
					log.error("Error while closing the Resultset in BrokerDAOImpl getInsuranceComapanyList()",sqlExp);
					throw new TTKException(sqlExp, "broker");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BrokerDAOImpl getInsuranceComapanyList()",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BrokerDAOImpl getInsuranceComapanyList()",sqlExp);
							throw new TTKException(sqlExp, "broker");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "broker");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getInsuranceComapanyList(ArrayList alSearchObjects)
    
    
    

	/**
	 * This method returns the AraayList of InsuranceVO which contains the details of the HeadOffice and regional office and branch offices and regionael offices
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return AraayList of InsuranceVO which contains the details of the HeadOffice and regional office and branch offices and regionael offices
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getCompanyDetails(ArrayList alSearchObjects) throws TTKException
	{
		String strStaticQuery = strGetCompanyDetails;
		//log.info("strStaticQuery::::::::"+strStaticQuery);
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strParentOfficeSeqID = (String) alSearchObjects.get(0);//INS_SEQ_ID OF THE PARENT OFFICE
		String strOfficeType = (String) alSearchObjects.get(1); //'IHO'  FOR HEAD OFFICE 'IRO'  FOR REGIONAL OFFICES  'IDO'  FOR DIVISIONAL OFFICES  'IBO'  FOR BRANCH OFFICES.
		String strStartRow = (String) alSearchObjects.get(2); //START ROW IN THE CASES PAGINATION NOT REQUIRED   --   REPLACE THE VALUES OF Q WITH Q ITSELF
		String strEndRow = (String) alSearchObjects.get(3); //START ROW IN THE CASES PAGINATION NOT REQUIRED   --   REPLACE THE VALUES OF Q WITH Q ITSELF
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?", strParentOfficeSeqID);
		sbfDynamicQuery.append(" JOIN app.TPA_GENERAL_CODE E ON (D.INS_SECTOR_GENERAL_TYPE_ID = E.GENERAL_TYPE_ID)WHERE D.INS_OFFICE_GENERAL_TYPE_ID =")
		.append("'"+strOfficeType+"'").append(")F)G WHERE Q >= ").append(strStartRow).append("  AND Q <=").append(strEndRow);
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();	
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		BrokerVO brokerVO = null;
		ArrayList<Object> alBrokerList = null;
		try
		{
			conn = ResourceManager.getConnection();
			//System.out.println("Broker edit..."+strStaticQuery);
			pStmt = conn.prepareStatement(strStaticQuery);
			rs = pStmt.executeQuery();
			if(rs!=null){
				alBrokerList = new ArrayList<Object>();
				while(rs.next()){
					brokerVO = new BrokerVO();
					brokerVO.setInsuranceSeqID(rs.getString("INS_SEQ_ID")!=null ? new Long(rs.getLong("INS_SEQ_ID")):null);
					brokerVO.setTTKBranchCode(rs.getString("TPA_OFFICE_SEQ_ID")!=null ? new Long(rs.getString("TPA_OFFICE_SEQ_ID")):null);
					brokerVO.setSectorTypeDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					brokerVO.setParentSeqID(rs.getString("INS_PARENT_SEQ_ID")!=null ? new Long(rs.getString("INS_PARENT_SEQ_ID")):null);
					brokerVO.setSectorTypeCode(TTKCommon.checkNull(rs.getString("INS_SECTOR_GENERAL_TYPE_ID")));
		//			System.out.println("Sector Type Code..."+rs.getString("INS_SECTOR_GENERAL_TYPE_ID"));
					brokerVO.setCompanyAbbreviation(TTKCommon.checkNull(rs.getString("ABBREVATION_CODE")));
					brokerVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
				//	System.out.println("INS_COMP_NAME...."+rs.getString("INS_COMP_NAME"));
					brokerVO.setsCompanyCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
					brokerVO.setOfficeType(TTKCommon.checkNull(rs.getString("INS_OFFICE_GENERAL_TYPE_ID")));
					brokerVO.setBranchName(TTKCommon.checkNull(rs.getString("COMPANY")));
					brokerVO.setRegAuthority(TTKCommon.checkNull(rs.getString("BROKER_AUTHORITY")));
					alBrokerList.add(brokerVO);
				}//end of while(rs.next())
			}// End of  if(rs!=null)
			return alBrokerList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "broker");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "broker");
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
					log.error("Error while closing the Resultset in BrokerDAOImpl getCompanyDetails()",sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BrokerDAOImpl getCompanyDetails()",sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BrokerDAOImpl getCompanyDetails()",sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "broker");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//End of getCompanyDetails(ArrayList alSearchObjects)

	/**
	 * This method returns the InsuranceDetailVO, which contains all the insurancecompany details
	 * @param lInsuranceParentId is Insurance Company Parent Sequence ID
	 * @param lInsuranceCompanyID is  insurance company ID whih is of long type
	 * @return InsuranceDetailVO object which contains all the insuranceComapany details
	 * @exception throws TTKException
	 */
	public BrokerDetailVO getBrokerCompanyDetail(Long lInsuranceParentId,Long lInsuranceCompanyID) throws TTKException
	{
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		BrokerDetailVO  brokerDetailVO =new BrokerDetailVO();
		AddressVO addressVO =new AddressVO();
		try
		{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strInsurancelDetail);
			pStmt.setLong(1,lInsuranceParentId);  //head office ins_seq_id
			pStmt.setLong(2,lInsuranceCompanyID); //ins_seq_id of the office to be edited
			rs = pStmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					brokerDetailVO.setInsuranceSeqID(lInsuranceCompanyID);
					brokerDetailVO.setOfficeType(TTKCommon.checkNull(rs.getString("INS_OFFICE_GENERAL_TYPE_ID")));
					
					if (rs.getString("INS_PARENT_SEQ_ID")!=null)
					{
						brokerDetailVO.setParentInsSeqID(new Long(rs.getLong("INS_PARENT_SEQ_ID")));
					}//end of if (rs.getString("INS_PARENT_SEQ_ID")!=null)
					if (rs.getString("TPA_OFFICE_SEQ_ID")!= null)
					{
						brokerDetailVO.setTTKBranchCode(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
					}//end of if (rs.getString("TPA_OFFICE_SEQ_ID")!= null)
					brokerDetailVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					brokerDetailVO.setCompanyStatus(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
					brokerDetailVO.setCompanyCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
					brokerDetailVO.setCompanyAbbreviation(TTKCommon.checkNull(rs.getString("ABBREVATION_CODE")));
					brokerDetailVO.setRegAuthority(TTKCommon.checkNull(rs.getString("BROKER_AUTHORITY")));
					// Shortfall CR KOC1179
					brokerDetailVO.setCompanyEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					// End Shortfall CR KOC1179
					
					//bROKER 
					brokerDetailVO.setLicenseNo(TTKCommon.checkNull(rs.getString("Licence_No")));
					if(rs.getDate("REG_DATE")!=null)
					{
						brokerDetailVO.setRegDate(new java.util.Date(rs.getTimestamp("REG_DATE").getTime()));
					}//end of if(rs.getDate("REG_DATE")!=null)
					//kocBroker
					if(rs.getDate("LIC_EXP_DT")!=null)
					{
						brokerDetailVO.setLicenseExpDate(new java.util.Date(rs.getTimestamp("LIC_EXP_DT").getTime()));
					}//end of if(rs.getDate("REG_DATE")!=null)
					
					brokerDetailVO.setSectorTypeCode(TTKCommon.checkNull(rs.getString("INS_SECTOR_GENERAL_TYPE_ID")));
					brokerDetailVO.setSectorTypeDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					if(rs.getDate("EMPANELED_DATE")!=null)
					{
						brokerDetailVO.setEmpanelmentDate(new java.util.Date(rs.getTimestamp("EMPANELED_DATE").getTime()));
					}//end of if(rs.getDate("EMPANELED_DATE")!=null)
					brokerDetailVO.setFundDisbursalCode(TTKCommon.checkNull(rs.getString("DISB_TYPE_ID")));
					if (rs.getString("FLOAT_REPLENISHMENT_PERIOD")!=null)
					{
						brokerDetailVO.setReplenishmentPeriod(new Long(rs.getLong("FLOAT_REPLENISHMENT_PERIOD")));
					}//end of if (rs.getString("FLOAT_REPLENISHMENT_PERIOD")!=null)
					brokerDetailVO.setFrequencyCode(TTKCommon.checkNull(rs.getString("FREQUENCY_TYPE_ID")));
					if(rs.getString("ADDR_SEQ_ID") != null)
					{
						addressVO.setAddrSeqId(new Long(rs.getLong("ADDR_SEQ_ID")));
					}//end of if(rs.getString("ADDR_SEQ_ID") != null)
					addressVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
					addressVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
					addressVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
					addressVO.setStateCode(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));
					addressVO.setCityCode(TTKCommon.checkNull(rs.getString("CITY_TYPE_ID")));
					addressVO.setPinCode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
					addressVO.setCountryCode(TTKCommon.checkNull(rs.getString("COUNTRY_ID")));
					addressVO.setIsdCode(TTKCommon.getInt(TTKCommon.checkNull(rs.getString("ISD_CODE"))));
					addressVO.setStdCode(TTKCommon.getInt(TTKCommon.checkNull(rs.getString("STD_CODE"))));
					addressVO.setPhoneNo1(TTKCommon.checkNull(rs.getString("OFFICE_PHONE1")));
					addressVO.setPhoneNo2(TTKCommon.checkNull(rs.getString("OFFICE_PHONE2")));
					
					
					brokerDetailVO.setAddress(addressVO);
				}//end of while(rs.next())
			}// End of  if(rs!=null)
			return brokerDetailVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "insurance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "insurance");
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
					log.error("Error while closing the Resultset in BrokerDAOImpl getInsuranceCompanyDetail()",sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BrokerDAOImpl getInsuranceCompanyDetail()",sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BrokerDAOImpl getInsuranceCompanyDetail()",sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "broker");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getInsuranceCompanyDetail(Long lInsuranceCompanyID)

	/**
	 * This method adds or updates the insurance details
	 * The method also calls other methods on DAO to insert/update the insurance details to the database
	 * @param insuranceCompanyVO InsuranceDetailVO object which contains the hospital details to be added/updated
	 * @return long value, Insurance Seq Id
	 * @exception throws TTKException
	 */
	public long addUpdateInsuranceCompany(BrokerDetailVO brokerDetailVO) throws TTKException
	{
		long lResult =0;
		AddressVO  addressVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			addressVO = brokerDetailVO.getAddress();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddUpdateInsuranceInfo);
			
			if(brokerDetailVO.getInsuranceSeqID() == null)
			{
				cStmtObject.setLong(INS_SEQ_ID,0);//INS_SEQ_ID
			}//end of if(insuranceDetailVO.getInsuranceSeqID() == null)
			else
			{
				cStmtObject.setLong(INS_SEQ_ID,brokerDetailVO.getInsuranceSeqID());//INS_SEQ_ID
			}//end of else
			if(addressVO.getAddrSeqId()!=null)
			{
				cStmtObject.setLong(INS_ADDR_SEQ_ID,addressVO.getAddrSeqId());//INS_ADDR_SEQ_ID
			}//end of if(addressVO.getAddrSeqId()!=null)
			else
			{
				cStmtObject.setLong(INS_ADDR_SEQ_ID,0);
			}//end of else
			if (brokerDetailVO.getTTKBranchCode()!= null)
			{
				cStmtObject.setLong(TPA_OFFICE_SEQ_ID,brokerDetailVO.getTTKBranchCode());//TPA_OFFICE_SEQ_ID
			}//end of if (insuranceDetailVO.getTTKBranchCode()!= null)
			else
			{
				cStmtObject.setLong(TPA_OFFICE_SEQ_ID,0);//TPA_OFFICE_SEQ_ID
			}//end of else
			cStmtObject.setString(INS_SECTOR_GENERAL_TYPE_ID,TTKCommon.checkNull(brokerDetailVO.getSectorTypeCode()));//INS_SECTOR_GENERAL_TYPE_ID
			cStmtObject.setString(INS_OFFICE_GENERAL_TYPE_ID,TTKCommon.checkNull(brokerDetailVO.getOfficeType()));//INS_OFFICE_GENERAL_TYPE_ID
		//	System.out.println("INS_COMP_NAME.."+brokerDetailVO.getCompanyName());
			cStmtObject.setString(INS_COMP_NAME,TTKCommon.checkNull(brokerDetailVO.getCompanyName()));//INS_COMP_NAME
			/* if(!"".equals(TTKCommon.checkNull(brokerDetailVO.getCompanyName()))){
				 cStmtObject.setString(INS_COMP_NAME,TTKCommon.checkNull(brokerDetailVO.getCompanyName()));//INS_COMP_NAME
	            }
			 else{
				 cStmtObject.setString(6,null);
			 }*/
				
			cStmtObject.setString(ABBREVATION_CODE,TTKCommon.checkNull(brokerDetailVO.getCompanyAbbreviation()));//ABBREVATION_CODE
		//	System.out.println("ABBREVATION_CODE..."+brokerDetailVO.getCompanyAbbreviation());
			cStmtObject.setString(INS_COMP_CODE_NUMBER,TTKCommon.checkNull(brokerDetailVO.getCompanyCode()));//INS_COMP_CODE_NUMBER
			// Shortfall CR KOC1179
			cStmtObject.setString(INS_COMPANY_EMAILID,TTKCommon.checkNull(brokerDetailVO.getCompanyEmailID()));//INS_COMPANY_EMAILID
			// End Shortfall CR KOC1179
			// Broker License No and Reg Date	
			 cStmtObject.setString(Licence_No,TTKCommon.checkNull(brokerDetailVO.getLicenseNo()));//INS_COMPANY_EMAILID
			if(brokerDetailVO.getEmpanelmentDate()!=null && brokerDetailVO.getRegDate()!=null&& !brokerDetailVO.getRegDate().equals(""))
			{
				cStmtObject.setTimestamp(Reg_date,(new Timestamp(brokerDetailVO.getRegDate().getTime())));//registration_DATE
			}//end of if(insuranceDetailVO.getEmpanelmentDate()!=null && !registration_DATE().equals(""))
			else
			{
				cStmtObject.setTimestamp(Reg_date,null);//registration_DATE
			}//end of else
			//kocBroker
			if(brokerDetailVO.getLicenseExpDate()!=null && !brokerDetailVO.getLicenseExpDate().equals(""))
			{
				cStmtObject.setTimestamp(LIC_EXP_DT,(new Timestamp(brokerDetailVO.getLicenseExpDate().getTime())));//License Expiry Date
			}//end of if(insuranceDetailVO.getLicenseExpDate()!=null && !License Expiry Date().equals(""))
			else
			{
				cStmtObject.setTimestamp(LIC_EXP_DT,null);//License Expiry Date
			}//end of else
			
			
			if(brokerDetailVO.getEmpanelmentDate()!=null && !brokerDetailVO.getEmpanelmentDate().equals(""))
			{
				cStmtObject.setTimestamp(EMPANELED_DATE,(new Timestamp(brokerDetailVO.getEmpanelmentDate().getTime())));//EMPANELED_DATE
			}//end of if(insuranceDetailVO.getEmpanelmentDate()!=null && !insuranceDetailVO.getEmpanelmentDate().equals(""))
			else
			{
				cStmtObject.setTimestamp(EMPANELED_DATE,null);//EMPANELED_DATE
			}//end of else
			cStmtObject.setString(FREQUENCY_TYPE_ID,TTKCommon.checkNull(brokerDetailVO.getFrequencyCode()));//FREQ_TYPE_ID
		//	cStmtObject.setString(DISB_TYPE_ID,TTKCommon.checkNull(brokerDetailVO.getFundDisbursalCode()));//DISB_TYPE_ID
			if (brokerDetailVO.getReplenishmentPeriod()== null)
			{
				cStmtObject.setString(DISB_TYPE_ID,null);//DISB_TYPE_ID
			}//end of if (insuranceDetailVO.getReplenishmentPeriod()== null)
			else
			{
				cStmtObject.setString(DISB_TYPE_ID,brokerDetailVO.getReplenishmentPeriod().toString());
			}//end of else
			if (brokerDetailVO.getReplenishmentPeriod()== null)
			{
				cStmtObject.setLong(FLOAT_REPLENISHMENT_PERIOD,0);//FLOAT_REPLENISHMENT_PERIOD
			}//end of if (insuranceDetailVO.getReplenishmentPeriod()== null)
			else
			{
				cStmtObject.setLong(FLOAT_REPLENISHMENT_PERIOD,brokerDetailVO.getReplenishmentPeriod());
			}//end of else
			cStmtObject.setString(ACTIVE_YN,TTKCommon.checkNull(brokerDetailVO.getCompanyStatus()));//ACTIVE_YN
			if(brokerDetailVO.getParentInsSeqID()!=null)
			{
				cStmtObject.setLong(INS_PARENT_SEQ_ID,(brokerDetailVO.getParentInsSeqID()));//INS_PARENT_SEQ_ID
			}//end of if(insuranceDetailVO.getParentInsSeqID()!=null)
			else
			{
				cStmtObject.setLong(INS_PARENT_SEQ_ID,0);//INS_PARENT_SEQ_ID
			}//end of else
	//		System.out.println("INS_PARENT_SEQ_ID..."+brokerDetailVO.getParentInsSeqID());
			cStmtObject.setString(ADDRESS_1,TTKCommon.checkNull(addressVO.getAddress1()));//ADDRESS_1
			cStmtObject.setString(ADDRESS_2,TTKCommon.checkNull(addressVO.getAddress2()));//ADDRESS_2
			cStmtObject.setString(ADDRESS_3,TTKCommon.checkNull(addressVO.getAddress3()));//ADDRESS_3
			cStmtObject.setString(CITY_TYPE_ID,TTKCommon.checkNull(addressVO.getCityCode()));//CITY_TYPE_ID
			cStmtObject.setString(STATE_TYPE_ID,TTKCommon.checkNull(addressVO.getStateCode()));//STATE_TYPE_ID
			cStmtObject.setLong(PIN_CODE,(Long.parseLong(TTKCommon.checkNull(addressVO.getPinCode()))));//PIN_CODE
			cStmtObject.setLong(COUNTRY_ID,(Long.parseLong(TTKCommon.checkNull(addressVO.getCountryCode()))));//COUNTRY_ID
			cStmtObject.setString(LANDMARKS,null);//LANDMARKS
			if (brokerDetailVO.getOfficeSequenceID()!=null)
			{
				cStmtObject.setLong(INS_OFF_SEQ_ID,brokerDetailVO.getOfficeSequenceID());//INS_OFF_SEQ_ID
			}//end of if (insuranceDetailVO.getOfficeSequenceID()!=null)
			else
			{
				cStmtObject.setLong(INS_OFF_SEQ_ID,0);
			}//end of else
			if(brokerDetailVO.getStartDate()!=null && !brokerDetailVO.getStartDate().equals(""))
			{
				cStmtObject.setTimestamp(VALID_FROM,new Timestamp(brokerDetailVO.getStartDate().getTime()));//VALID_FROM
			}//end of if(insuranceDetailVO.getStartDate()!=null && !insuranceDetailVO.getStartDate().equals(""))
			else
			{
				cStmtObject.setTimestamp(VALID_FROM,null);
			}//end of else
			if(brokerDetailVO.getEndDate()!=null && !brokerDetailVO.getEndDate().equals(""))
			{
				cStmtObject.setTimestamp(VALID_TO,new Timestamp(brokerDetailVO.getEndDate().getTime()));//VALID_TO
			}//end of if(insuranceDetailVO.getEndDate()!=null && !insuranceDetailVO.getEndDate().equals(""))
			else
			{
				cStmtObject.setTimestamp(VALID_TO,null);
			}//end of else
			cStmtObject.setLong(USER_SEQ_ID,brokerDetailVO.getUpdatedBy()); //USER_SEQ_ID
			cStmtObject.setInt(ISD_CODE,addressVO.getIsdCode());
			cStmtObject.setInt(STD_CODE,addressVO.getStdCode());
			cStmtObject.setLong(PHONE1,Long.parseLong(addressVO.getPhoneNo1()));
			//	cStmtObject.setLong(PHONE2,Long.parseLong(addressVO.getPhoneNo2()));
			 if(!"".equals(TTKCommon.checkNull(addressVO.getPhoneNo2()))){
				 cStmtObject.setLong(PHONE2,Long.parseLong(addressVO.getPhoneNo2()));
	            }
			 else{
					cStmtObject.setLong(34,0);
				}//end of else
			cStmtObject.setString(BROKER_AUTHORITY,TTKCommon.checkNull(brokerDetailVO.getRegAuthority()));//INS_OFFICE_GENERAL_TYPE_ID
			cStmtObject.registerOutParameter(INS_SEQ_ID,Types.BIGINT);//INS_SEQ_ID	
			cStmtObject.registerOutParameter(INS_ADDR_SEQ_ID,Types.BIGINT);
			cStmtObject.registerOutParameter(INS_COMP_NAME, Types.VARCHAR);
			cStmtObject.registerOutParameter(INS_OFF_SEQ_ID, Types.BIGINT);
			cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			conn.commit();
			lResult = cStmtObject.getLong(INS_SEQ_ID);
		}//end of try
		
		
		catch (SQLException sqlExp){
			throw new TTKException(sqlExp, "broker");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "broker");
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
        			log.error("Error while closing the Statement in BrokerDAOImpl addUpdateInsuranceCompany()",sqlExp);
        			throw new TTKException(sqlExp, "broker");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BrokerDAOImpl addUpdateInsuranceCompany()",sqlExp);
        				throw new TTKException(sqlExp, "broker");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "broker");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return  lResult;
	}//  end of addUpdateInsuranceCompany(InsuranceDetailVO insuranceDetailVO) throws TTKException

	/**
	 * This method delete's the insurance records from the database.
	 * @param alInsuranceCompanyList ArrayList object which contains the insurance sequence id's to be deleted
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public  int deleteInsuranceCompany(String strInsSeqID) throws TTKException
	{
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteInsuranceInfo);
			cStmtObject.setString(1, strInsSeqID);//string of sequence id's which are separated with | as separator (Note: String should also end with | at the end)
			cStmtObject.registerOutParameter(2, Types.INTEGER);//out parameter which gives the number of records deleted
			cStmtObject.execute();
			conn.commit();
			iResult = cStmtObject.getInt(2);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "broker");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "broker");
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
        			log.error("Error while closing the Statement in BrokerDAOImpl deleteBrokerCompany()",sqlExp);
        			throw new TTKException(sqlExp, "broker");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BrokerDAOImpl deleteBrokerCompany()",sqlExp);
        				throw new TTKException(sqlExp, "broker");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "broker");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteInsuranceCompany(String strInsSeqID)
//AK 2 March 2018
public ArrayList getBrokerDetails(ArrayList alSearchObjects) throws TTKException{
	StringBuffer sbfDynamicQuery = new StringBuffer();
    String strStaticQuery = strGetBrokerDetails;
    Collection resultList = new ArrayList();
    BrokerVO  brokerVO = null;
    Connection conn = null;
    PreparedStatement pStmt = null;
    ResultSet rs = null;
    if(alSearchObjects != null && alSearchObjects.size() > 0)
    {
        for(int i=0; i < alSearchObjects.size()-4; i++)
        {
            if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
            {
                sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
            }//end of if(!((SearchCriteria)alSearchCriteria.get(i)).getValue().equals(""))
            
        }//end of for()
    }//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
    
    strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
    sbfDynamicQuery = sbfDynamicQuery .append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
    strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
   
    try{
        conn = ResourceManager.getConnection();
     //   System.out.println("Broker details query.."+strStaticQuery);
        pStmt = conn.prepareStatement(strStaticQuery);
    //    System.out.println("Broker details query.."+strStaticQuery);
        rs = pStmt.executeQuery();
        if(rs != null){
            while(rs.next()){
            	
            	brokerVO = new BrokerVO();
                if(rs.getString("INS_SEQ_ID") != null){
                	brokerVO.setInsuranceSeqID(new Long(rs.getLong("INS_SEQ_ID")));
                }//end of if(rs.getString("INS_SEQ_ID") != null)
                brokerVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
				brokerVO.setCompanyCodeNbr(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
                brokerVO.setRegAuthority(TTKCommon.checkNull(rs.getString("BROKER_AUTHORITY")));
                brokerVO.setOfficeInfo(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
                resultList.add(brokerVO);
            }//end of while(rs.next())
        }//end of if(rs != null)
        return (ArrayList)resultList;
    }//end of try
    catch (SQLException sqlExp)
    {
        throw new TTKException(sqlExp, "broker");
    }//end of catch (SQLException sqlExp)
    catch (Exception exp)
    {
        throw new TTKException(exp, "broker");
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
				log.error("Error while closing the Resultset in BrokerDAOImpl getInsuranceComapanyList()",sqlExp);
				throw new TTKException(sqlExp, "broker");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmt != null)	pStmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in BrokerDAOImpl getInsuranceComapanyList()",sqlExp);
					throw new TTKException(sqlExp, "broker");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in BrokerDAOImpl getInsuranceComapanyList()",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "broker");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs = null;
			pStmt = null;
			conn = null;
		}//end of finally
	}//end of finally
			
}//end of 	public ArrayList<Object> getBrokerDetails() throws TTKException

 public ArrayList getBrokerDetailsList(ArrayList alSearchObjects,Long policySeqID) throws TTKException{
	 StringBuffer sbfDynamicQuery = new StringBuffer();
	String strStaticQuery = strGetBrokerDetailsList;
	ContactVO contactVO = null;
	Collection<Object> resultList = new ArrayList<Object>();
	
	if(alSearchObjects != null && alSearchObjects.size() > 0)
	{
		String strInsuranceSeqId = ((SearchCriteria)alSearchObjects.get(0)).getValue();
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?", strInsuranceSeqId);
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"!", policySeqID.toString());
		for(int i=1; i < alSearchObjects.size()-4; i++)
		{
		String r2	= ((SearchCriteria)alSearchObjects.get(2)).getValue();
		
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
	sbfDynamicQuery = sbfDynamicQuery .append( " )A)AB WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
	strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
	
	Connection conn = null;
	PreparedStatement pStmt = null;
	ResultSet rs = null;
	try
	{
		
		conn = ResourceManager.getConnection();
		pStmt = conn.prepareStatement(strStaticQuery);
	//	System.out.println("Broker search..."+strStaticQuery);
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
				contactVO.setUserRoleBRO(TTKCommon.checkNull(rs.getString("ROLE_NAME")));
				contactVO.setUserID(TTKCommon.checkNull(rs.getString("USER_ID")));
				contactVO.setPrimaryEmailID(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));
				contactVO.setOfficePhoneNO(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
				contactVO.setStatus(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
				contactVO.setBrokerseqid(TTKCommon.checkNull(rs.getString("INS_SEQ_ID")));
				contactVO.setUserAssoDisAsso(TTKCommon.checkNull(rs.getString("USER_ASSO_DISASSO")));
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
        throw new TTKException(sqlExp, "broker");
    }//end of catch (SQLException sqlExp)
    catch (Exception exp)
    {
        throw new TTKException(exp, "broker");
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
				log.error("Error while closing the Resultset in BrokerDAOImpl getInsuranceComapanyList()",sqlExp);
				throw new TTKException(sqlExp, "broker");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmt != null)	pStmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in BrokerDAOImpl getInsuranceComapanyList()",sqlExp);
					throw new TTKException(sqlExp, "broker");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in BrokerDAOImpl getInsuranceComapanyList()",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "broker");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs = null;
			pStmt = null;
			conn = null;
		}//end of finally
	}//end of finally
 }//end of 	public ArrayList<Object> getBrokerDetails() throws TTKException
 public int saveAssociatedBrokersUser(ArrayList alSaveList,Long endorsementSeqId) throws TTKException
	{
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		
		try{
			if(alSaveList != null && alSaveList.size() > 0){
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strsaveBrokerContactDetails);
			//	System.out.println("alSaveList..."+alSaveList);
				cStmtObject.setString(1,(String)alSaveList.get(0));
				cStmtObject.setLong(2,(Long)alSaveList.get(1));
				cStmtObject.setLong(3,Long.parseLong(alSaveList.get(2).toString()));
				cStmtObject.setLong(4,(Long)alSaveList.get(3));
				cStmtObject.setString(5,(String)alSaveList.get(4));
				cStmtObject.setLong(6,endorsementSeqId != null ? endorsementSeqId : 0);
				cStmtObject.registerOutParameter(7,Types.SMALLINT);
				cStmtObject.execute();
				conn.commit();
				iResult = cStmtObject.getByte(7);
			}//end of if(alDeleteList != null && alDeleteList.size() > 0)

			
			
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "broker");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "broker");
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
        			log.error("Error while closing the Statement in BrokerDAOImpl saveBrokerCompany()",sqlExp);
        			throw new TTKException(sqlExp, "broker");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BrokerDAOImpl saveBrokerCompany()",sqlExp);
        				throw new TTKException(sqlExp, "broker");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "broker");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteInsuranceCompany(String strInsSeqID)
	public ArrayList<Object> getBrokerDetailsAssocGrpList(ArrayList alSearchObjects) throws TTKException {
		
		Collection<Object> alResultList = new ArrayList<Object>();
		BrokerVO brokerVO =null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		
		String value="";
		value = (alSearchObjects.get(1).toString().equals("ENM") || alSearchObjects.get(1).toString().equals("END")) ? alSearchObjects.get(1).toString() : null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBrokerAssocGrp);
			cStmtObject.setLong(1,(long)alSearchObjects.get(0));
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.setString(3, value);
			cStmtObject.execute();
			conn.commit();
			rs =(ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					brokerVO = new BrokerVO();

					    if(rs.getString("ins_seq_id") != null){
					    	brokerVO.setBrokerseqid(TTKCommon.checkNull(rs.getString("ins_seq_id")));
					    }
					    brokerVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
						brokerVO.setCompanyCodeNbr(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
		                brokerVO.setRegAuthority(TTKCommon.checkNull(rs.getString("BROKER_AUTHORITY")));
		                brokerVO.setOfficeInfo(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
		                brokerVO.setUserAssociaedStatus(TTKCommon.checkNull(rs.getString("User_Associated")));
		                brokerVO.setDeleteImageName("DeleteIcon");
		                brokerVO.setDeleteImageTitle("DeleteIcon");
		              
					alResultList.add(brokerVO);
				}//end of while(rs.next())
			}
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
	    {
	        throw new TTKException(sqlExp, "broker");
	    }//end of catch (SQLException sqlExp)
	    catch (Exception exp)
	    {
	        throw new TTKException(exp, "broker");
	    }//end of catch (Exception exp)
		finally
		{
			 //Nested Try Catch to ensure resource closure 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in BrokerDAOImpl getInsuranceComapanyList()",sqlExp);
					throw new TTKException(sqlExp, "broker");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Resultset in BrokerDAOImpl getInsuranceComapanyList()",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Resultset in BrokerDAOImpl getInsuranceComapanyList()",sqlExp);
							throw new TTKException(sqlExp, "broker");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "broker");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getFloatAssocGrpList(Long lngFloatSeqID)
	
//END TableAK	
	
	public  int saveBrokerDeatils(ArrayList alSearchObjects) throws TTKException
	{
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBrokerDeatils);
			cStmtObject.setLong(1,(long)alSearchObjects.get(0));
			cStmtObject.setString(2,(String)alSearchObjects.get(1));
			cStmtObject.setLong(3,(long)alSearchObjects.get(2));
			cStmtObject.setString(4,(String)alSearchObjects.get(3));
			cStmtObject.setLong(5,(long) (alSearchObjects.get(4) != null ? (long)alSearchObjects.get(4):0));
			cStmtObject.registerOutParameter(6,OracleTypes.INTEGER);
			cStmtObject.execute();
			conn.commit();
			iResult =(int)cStmtObject.getObject(6);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "broker");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "broker");
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
        			log.error("Error while closing the Statement in BrokerDAOImpl deleteBrokerCompany()",sqlExp);
        			throw new TTKException(sqlExp, "broker");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BrokerDAOImpl deleteBrokerCompany()",sqlExp);
        				throw new TTKException(sqlExp, "broker");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "broker");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}
	public int deleteFloatAssocGrp(String Brokerseqid,Long policySeqID,Long endorsementSeqId,String SwitchType) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		ResultSet rs = null;
		BrokerVO brokerVO =null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
		//	System.out.println("before endorsementSeqId.."+endorsementSeqId);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteFloatAssocGrp);
			cStmtObject.setInt(1,Integer.parseInt(Brokerseqid));
			cStmtObject.setInt(2,policySeqID.intValue());
		//	System.out.println("endorsementSeqId.."+endorsementSeqId);
			
			if(endorsementSeqId != null){
				cStmtObject.setInt(3,endorsementSeqId.intValue());
			}
			else{
			    cStmtObject.setInt(3, 0);	
			}
		//	cStmtObject.setInt(3,endorsementSeqId.intValue() != 0 ? endorsementSeqId.intValue() : 0);
			cStmtObject.setString(4,SwitchType);
		//	System.out.println("SwitchType.."+SwitchType);
			cStmtObject.registerOutParameter(5,Types.INTEGER);
		
			cStmtObject.execute();
			iResult = cStmtObject.getInt(5);
			conn.commit();
				
		}// end of try
		
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "broker");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "broker");
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
        			log.error("Error while closing the Statement in BrokerDAOImpl deleteBrokerCompany()",sqlExp);
        			throw new TTKException(sqlExp, "broker");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BrokerDAOImpl deleteBrokerCompany()",sqlExp);
        				throw new TTKException(sqlExp, "broker");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "broker");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteFloatAssocGrp(Long lngFloatGrpAssocSeqID) 

	 public int disAssociateBrokersUser(ArrayList alSaveList,Long endorsementSeqId) throws TTKException
		{
			int iResult = 0;
			Connection conn = null;
			CallableStatement cStmtObject=null;
			
			try{
				if(alSaveList != null && alSaveList.size() > 0){
					conn = ResourceManager.getConnection();
				//	System.out.println("Array list of broker..."+alSaveList);
					cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDisAsscociateBrokseUser);
					cStmtObject.setString(1,(String)alSaveList.get(0));
				//	System.out.println(alSaveList.get(0));
					cStmtObject.setLong(2,(Long)alSaveList.get(1));
				//	System.out.println(alSaveList.get(1));
					cStmtObject.setLong(3,Long.parseLong(alSaveList.get(2).toString()));
				//	System.out.println(alSaveList.get(2));
					cStmtObject.setLong(4,(Long)alSaveList.get(3));
				//	System.out.println(alSaveList.get(3));
					cStmtObject.setString(5,(String)alSaveList.get(4));
				//	System.out.println(alSaveList.get(4));
					cStmtObject.setLong(6,endorsementSeqId != null ? endorsementSeqId : 0);
				//	System.out.println(alSaveList.get(5));
					cStmtObject.registerOutParameter(7,Types.SMALLINT);
				//	System.out.println("Array list of broker after call..."+alSaveList);
					cStmtObject.execute();
					iResult = cStmtObject.getByte(7);
					conn.commit();
				}//end of if(alDeleteList != null && alDeleteList.size() > 0)

				
				
			}// end of try
			catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "broker");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "broker");
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
	        			log.error("Error while closing the Statement in BrokerDAOImpl saveBrokerCompany()",sqlExp);
	        			throw new TTKException(sqlExp, "broker");
	        		}//end of catch (SQLException sqlExp)
	        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	        		{
	        			try
	        			{
	        				if(conn != null) conn.close();
	        			}//end of try
	        			catch (SQLException sqlExp)
	        			{
	        				log.error("Error while closing the Connection in BrokerDAOImpl saveBrokerCompany()",sqlExp);
	        				throw new TTKException(sqlExp, "broker");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "broker");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects 
	        	{
	        		cStmtObject = null;
	        		conn = null;
	        	}//end of finally
			}//end of finally
			return iResult;
		}//
	
	
	 public int getAssoDissoCount(Long policySeqID,Long brokerseqid) throws TTKException
		{
			int count = 0;
			Connection conn = null;
		    PreparedStatement pStmt = null;
			ResultSet rs = null;
			try{
					conn = ResourceManager.getConnection();
					pStmt = conn.prepareStatement(strAssoDissoCount);
					pStmt.setLong(1,brokerseqid != null ? brokerseqid : 0);
					pStmt.setLong(2,brokerseqid != null ? brokerseqid : 0);
					pStmt.setLong(3,policySeqID != null ? policySeqID : 0);
					rs = pStmt.executeQuery();
					
					while(rs.next())
					{
					    count = rs.getInt("COUNTALL");
					}
			}
			catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "broker");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "broker");
			}//end of catch (Exception exp)
			finally
			{
	        	/* Nested Try Catch to ensure resource closure */ 
	        	try // First try closing the Statement
	        	{
	        		try
	        		{
	        			if (pStmt != null) pStmt.close();
	        		}//end of try
	        		catch (SQLException sqlExp)
	        		{
	        			log.error("Error while closing the Statement in BrokerDAOImpl getAssoDissoCount()",sqlExp);
	        			throw new TTKException(sqlExp, "broker");
	        		}//end of catch (SQLException sqlExp)
	        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	        		{
	        			try
	        			{
	        				if(conn != null) conn.close();
	        			}//end of try
	        			catch (SQLException sqlExp)
	        			{
	        				log.error("Error while closing the Connection in BrokerDAOImpl getAssoDissoCount()",sqlExp);
	        				throw new TTKException(sqlExp, "broker");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "broker");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects 
	        	{
	        		pStmt = null;
	        		conn = null;
	        	}//end of finally
			}//end of finally
			return count;
		}//
	
	 public ArrayList getAssociatedBrokerDetailsList(ArrayList alSearchObjects,Long policySeqID) throws TTKException{
			Connection conn = null;
			PreparedStatement pStmt = null;
			ResultSet rs = null;
			ContactVO contactVO = null;
			Collection<Object> resultList = new ArrayList<Object>();
			String InsuranceSeqId="";
			if(alSearchObjects != null && alSearchObjects.size() > 0)
					
			InsuranceSeqId = ((SearchCriteria)alSearchObjects.get(0)).getValue();
			
			try
			{
				conn = ResourceManager.getConnection();
				
				pStmt = conn.prepareStatement(strgetAssociatedBrokerDetailsList);
				pStmt.setString(1,InsuranceSeqId);
				pStmt.setLong(2,policySeqID != null ? policySeqID : 0);
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
						contactVO.setUserRoleBRO(TTKCommon.checkNull(rs.getString("ROLE_NAME")));
						contactVO.setUserID(TTKCommon.checkNull(rs.getString("USER_ID")));
						contactVO.setPrimaryEmailID(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));
						contactVO.setOfficePhoneNO(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
						contactVO.setStatus(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
						contactVO.setBrokerseqid(TTKCommon.checkNull(rs.getString("INS_SEQ_ID")));
						contactVO.setUserAssoDisAsso(TTKCommon.checkNull(rs.getString("USER_ASSO_DISASSO")));
						/*if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
						{
							contactVO.setImageName("UserIcon");
							contactVO.setImageTitle("User");
						}//end of if (rs.getString("PROVIDE_ACCESS_USER_YN")!=null && rs.getString("PROVIDE_ACCESS_USER_YN").equals("Y"))
*/						resultList.add(contactVO);
					}//end of while(rs.next())
				}//end of if(rs != null)
				return (ArrayList)resultList;
			}//end of try
			catch (SQLException sqlExp)
		    {
		        throw new TTKException(sqlExp, "broker");
		    }//end of catch (SQLException sqlExp)
		    catch (Exception exp)
		    {
		        throw new TTKException(exp, "broker");
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
						log.error("Error while closing the Resultset in BrokerDAOImpl getBrokerDetailsList()",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (pStmt != null)	pStmt.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in BrokerDAOImpl getBrokerDetailsList()",sqlExp);
							throw new TTKException(sqlExp, "broker");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in BrokerDAOImpl getBrokerDetailsList()",sqlExp);
								throw new TTKException(sqlExp, "broker");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "broker");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{
					rs = null;
					pStmt = null;
					conn = null;
				}//end of finally
			}//end of finally
		 }//end of 	public ArrayList<Object> getBrokerDetails() throws TTKException 
	 
	 public String getBrokerAssocCount(Long policySeqID) throws TTKException{

			Connection conn = null;
			PreparedStatement pStmt = null;
			ResultSet rs = null;
			String strData="0";
			try
			{
				conn = ResourceManager.getConnection();
				
				pStmt = conn.prepareStatement(strgetAssociatedBrokerCount);
				pStmt.setLong(1,policySeqID);
				rs = pStmt.executeQuery();
				
				if(rs != null){
				
					while (rs.next()) {
                        strData=rs.getInt("BRO_CNT")+"";
					}//end of while(rs.next())
				}//end of if(rs != null)
				return strData;
			}//end of try
			catch (SQLException sqlExp)
		    {
		        throw new TTKException(sqlExp, "broker");
		    }//end of catch (SQLException sqlExp)
		    catch (Exception exp)
		    {
		        throw new TTKException(exp, "broker");
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
						log.error("Error while closing the Resultset in BrokerDAOImpl getBrokerAssocCount()",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (pStmt != null)	pStmt.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in BrokerDAOImpl getBrokerAssocCount()",sqlExp);
							throw new TTKException(sqlExp, "broker");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in BrokerDAOImpl getBrokerAssocCount()",sqlExp);
								throw new TTKException(sqlExp, "broker");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "broker");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{
					rs = null;
					pStmt = null;
					conn = null;
				}//end of finally
			}//end of finally
		 
	 }
	 
}// end of BrokerDAOImpl
