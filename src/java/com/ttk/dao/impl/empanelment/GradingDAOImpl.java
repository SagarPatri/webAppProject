/**
 * @ (#) GradingDAOImpl.java Oct 11, 2005
 * Project      : TTK HealthCare Services
 * File         : GradingDAOImpl
 * Author       : Ramakrishna K M
 * Company      : Span Systems Corporation
 * Date Created : Oct 11, 2005
 *
 * @author       :  Ramakrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.empanelment;

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

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.empanelment.GradingServicesVO;
import com.ttk.dto.empanelment.GradingVO;
import com.ttk.dto.empanelment.InfraStructureVO;
import com.ttk.dto.onlineforms.OnlineFacilityVO;

public class GradingDAOImpl implements BaseDAO, Serializable {
	
	private static Logger log = Logger.getLogger(GradingDAOImpl.class);

    private static final String strAddUpdateGradingInfo = "{call HOSPITAL_EMPANEL_PKG_PR_GRADING_OVERRIDING_SAVE(?,?,?,?,?,?,?)}";
    private static final String strGradingInfo = "SELECT A.hosp_seq_id,B.description,A.grade_type_id,A.remarks,A.SYSTEM_GRADED_ID,A.APPROVED_GRADE_TYPE_ID,C.DESCRIPTION AS system_grade_desc,D.DESCRIPTION AS approved_grade_desc FROM app.tpa_hosp_grading A LEFT OUTER JOIN app.tpa_hosp_grade_code B ON(A.Grade_Type_Id=B.GRADE_TYPE_ID) LEFT OUTER JOIN app.tpa_hosp_grade_code C ON (A.System_Graded_Id=C.GRADE_TYPE_ID) LEFT OUTER JOIN app.tpa_hosp_grade_code D ON (A.Approved_Grade_Type_Id=D.GRADE_TYPE_ID) WHERE ( A.hosp_seq_id = ? )";
    private static final String strGenerateGradeInfo = "{call HOSPITAL_EMPANEL_PKG_HOSP_GRADE_CALCULATION(?,?)}";
    private static final String strGradingGeneralInfo = "SELECT HOSP_SEQ_ID,LOCATION_TYPE_ID,CATEGORY_TYPE_ID FROM TPA_HOSP_GRADING WHERE HOSP_SEQ_ID = ?";
    private static final String strAddUpdateGeneralInfo = "{call HOSPITAL_EMPANEL_PKG.PR_GRADING_GENERAL_SAVE(?,?,?,?,?)}";
   // private static final String strInfraStructureInfo = "SELECT THI.INFRASTR_SEQ_ID,THI.HOSP_SEQ_ID,THI.WHOLE_PREMISES_YN,THI.OTHER_OCCUPANTS,THI.FLOOR_DETAILS,THI.BUILT_UP_AREA,THI.OPEN_AREA,THI.COST_OF_AREA, THG.LOCATION_TYPE_ID AS LOCATION, THG.CATEGORY_TYPE_ID AS CATEGORY,THI.INFRA_REMARKS FROM TPA_HOSP_INFO I LEFT OUTER JOIN TPA_HOSP_INFRASTR_INFO THI ON (I.HOSP_SEQ_ID = THI.HOSP_SEQ_ID) LEFT OUTER JOIN TPA_HOSP_GRADING THG ON (THG.HOSP_SEQ_ID = I.HOSP_SEQ_ID) JOIN TPA_HOSP_LOCATION_CODE LC ON (LC.LOCATION_TYPE_ID = THG.LOCATION_TYPE_ID) JOIN TPA_HOSP_CATEGORY_CODE CC ON (CC.CATEGORY_TYPE_ID = THG.CATEGORY_TYPE_ID) WHERE I.HOSP_SEQ_ID = ?";
    private static final String strInfraStructureInfo = "SELECT THI.INFRASTR_SEQ_ID,THI.HOSP_SEQ_ID,THI.WHOLE_PREMISES_YN,THI.OTHER_OCCUPANTS,THI.FLOOR_DETAILS,THI.BUILT_UP_AREA,THI.OPEN_AREA,THI.COST_OF_AREA, THG.LOCATION_TYPE_ID AS LOCATION,THG.CATEGORY_TYPE_ID AS CATEGORY,THI.INFRA_REMARKS FROM app.TPA_HOSP_INFO I LEFT OUTER JOIN app.TPA_HOSP_INFRASTR_INFO THI ON (I.HOSP_SEQ_ID = THI.HOSP_SEQ_ID)LEFT OUTER JOIN app.TPA_HOSP_GRADING THG ON (THG.HOSP_SEQ_ID = I.HOSP_SEQ_ID)JOIN app.TPA_HOSP_LOCATION_CODE LC ON (LC.LOCATION_TYPE_ID = THG.LOCATION_TYPE_ID)JOIN app.TPA_HOSP_CATEGORY_CODE CC ON (CC.CATEGORY_TYPE_ID = THG.CATEGORY_TYPE_ID)WHERE I.HOSP_SEQ_ID = ?";
    private static final String strAddUpdateInfraStructureInfo = "{call HOSPITAL_EMPANEL_PKG_PR_GRADING_INFRASTR_SAVE(?,?,?,?,?,?,?,?,?,?,?,?,?)}";//added 2 columns from general for projectX
    private static final String strAddUpdateServicesInfo = "{CALL HOSPITAL_EMPANEL_PKG_PR_GRADING_MEDICAL_SAVE(?,?,?,?,?,?)}";
   // private static final String strAddUpdateServicesInfo = "call HOSPITAL_EMPANEL_PKG_PR_GRADING_MEDICAL_SAVE(";
    private static final String strServicesInfo = "SELECT MEDICAL_DETAIL.medical_seq_id,MEDICAL_CODE.medical_type_id,MEDICAL_CODE.description,MEDICAL_CODE.medical_description,CASE WHEN MEDICAL_CODE.header_description = ? THEN COALESCE( MEDICAL_DETAIL.value_1, '') ELSE COALESCE( MEDICAL_DETAIL.value_1, 'N') END Value_1,CASE WHEN MEDICAL_CODE.header_description = ? THEN COALESCE( MEDICAL_DETAIL.value_2, '') ELSE COALESCE( MEDICAL_DETAIL.value_1, 'N') END Value_2 FROM ( SELECT NULL medical_seq_id,tpa_hosp_medical_code.medical_type_id,tpa_hosp_facility_code.description,tpa_hosp_medical_code.medical_description,tpa_hosp_facility_code.header_description,tpa_hosp_facility_code.sort_no sort_no1,tpa_hosp_medical_code.sort_no sort_no2,'N' value_1,'N' value_2 FROM APP.tpa_hosp_facility_code,APP.tpa_hosp_medical_code WHERE ( tpa_hosp_medical_code.facility_type_id = tpa_hosp_facility_code.facility_type_id ) and ( tpa_hosp_facility_code.header_description = ? )) MEDICAL_CODE LEFT OUTER JOIN ( SELECT tpa_hosp_medical_details.medical_seq_id,tpa_hosp_medical_code.medical_type_id,tpa_hosp_facility_code.description,tpa_hosp_medical_code.medical_description,tpa_hosp_facility_code.header_description,tpa_hosp_facility_code.sort_no sort_no1,tpa_hosp_medical_code.sort_no sort_no2,tpa_hosp_medical_details.value_1,tpa_hosp_medical_details.value_2 FROM APP.tpa_hosp_facility_code,APP.tpa_hosp_medical_code,APP.tpa_hosp_medical_details WHERE ( tpa_hosp_medical_code.facility_type_id = tpa_hosp_facility_code.facility_type_id ) and ( tpa_hosp_medical_details.medical_type_id = tpa_hosp_medical_code.medical_type_id) and ( tpa_hosp_facility_code.header_description = ? ) AND ( tpa_hosp_medical_details.hosp_seq_id = ?)) MEDICAL_DETAIL  ON (MEDICAL_CODE.medical_type_id = MEDICAL_DETAIL.medical_type_id) ORDER BY MEDICAL_CODE.sort_no1 ASC,MEDICAL_CODE.sort_no2 ASC";

  //  private static final String strServicesInfo = "SELECT MEDICAL_DETAIL.medical_seq_id,MEDICAL_CODE.medical_type_id,MEDICAL_CODE.description,MEDICAL_CODE.medical_description,CASE WHEN MEDICAL_CODE.header_description = ? THEN NVL( MEDICAL_DETAIL.value_1, '') ELSE NVL( MEDICAL_DETAIL.value_1, 'N') END Value_1,CASE WHEN MEDICAL_CODE.header_description = ? THEN NVL( MEDICAL_DETAIL.value_2, '') ELSE NVL( MEDICAL_DETAIL.value_1, 'N') END Value_2 FROM ( SELECT TO_NUMBER('') medical_seq_id,tpa_hosp_medical_code.medical_type_id,tpa_hosp_facility_code.description,tpa_hosp_medical_code.medical_description,tpa_hosp_facility_code.header_description,tpa_hosp_facility_code.sort_no sort_no1,tpa_hosp_medical_code.sort_no sort_no2,'N' value_1,'N' value_2 FROM tpa_hosp_facility_code,tpa_hosp_medical_code WHERE ( tpa_hosp_medical_code.facility_type_id = tpa_hosp_facility_code.facility_type_id ) and ( tpa_hosp_facility_code.header_description = ? )) MEDICAL_CODE,( SELECT tpa_hosp_medical_details.medical_seq_id,tpa_hosp_medical_code.medical_type_id,tpa_hosp_facility_code.description,tpa_hosp_medical_code.medical_description,tpa_hosp_facility_code.header_description,tpa_hosp_facility_code.sort_no sort_no1,tpa_hosp_medical_code.sort_no sort_no2,tpa_hosp_medical_details.value_1,tpa_hosp_medical_details.value_2 FROM tpa_hosp_facility_code,tpa_hosp_medical_code,tpa_hosp_medical_details WHERE ( tpa_hosp_medical_code.facility_type_id = tpa_hosp_facility_code.facility_type_id ) and ( tpa_hosp_medical_details.medical_type_id = tpa_hosp_medical_code.medical_type_id) and ( tpa_hosp_facility_code.header_description = ? ) AND ( tpa_hosp_medical_details.hosp_seq_id = ?)) MEDICAL_DETAIL WHERE MEDICAL_CODE.medical_type_id = MEDICAL_DETAIL.medical_type_id (+) ORDER BY MEDICAL_CODE.sort_no1 ASC,MEDICAL_CODE.sort_no2 ASC ";
    //intx
    private static final String strProvServicesInfo	=	"SELECT MEDICAL_DETAIL.medical_seq_id,MEDICAL_CODE.medical_type_id,MEDICAL_CODE.description,MEDICAL_CODE.medical_description,CASE WHEN MEDICAL_CODE.header_description = ? THEN NVL(MEDICAL_DETAIL.value_1, '') ELSE NVL(MEDICAL_DETAIL.value_1, 'N') END Value_1, CASE WHEN MEDICAL_CODE.header_description = ? THEN NVL(MEDICAL_DETAIL.value_2, '') ELSE NVL(MEDICAL_DETAIL.value_1, 'N') END Value_2 FROM (SELECT TO_NUMBER('') medical_seq_id, tpa_hosp_medical_code.medical_type_id, tpa_hosp_facility_code.description,tpa_hosp_medical_code.medical_description,tpa_hosp_facility_code.header_description,tpa_hosp_facility_code.sort_no sort_no1,tpa_hosp_medical_code.sort_no sort_no2,'N' value_1,'N' value_2 FROM tpa_hosp_facility_code, tpa_hosp_medical_code WHERE (tpa_hosp_medical_code.facility_type_id = tpa_hosp_facility_code.facility_type_id) and (tpa_hosp_facility_code.header_description = ?)) MEDICAL_CODE,(SELECT tpa_hosp_medical_details.medical_seq_id, tpa_hosp_medical_code.medical_type_id, tpa_hosp_facility_code.description, tpa_hosp_medical_code.medical_description,tpa_hosp_facility_code.header_description, tpa_hosp_facility_code.sort_no sort_no1, tpa_hosp_medical_code.sort_no sort_no2,tpa_hosp_medical_details.value_1, tpa_hosp_medical_details.value_2 FROM tpa_hosp_facility_code, tpa_hosp_medical_code, tpa_hosp_medical_details,tpa_hosp_info WHERE (tpa_hosp_medical_code.facility_type_id = tpa_hosp_facility_code.facility_type_id) and (tpa_hosp_medical_details.medical_type_id = tpa_hosp_medical_code.medical_type_id) and (tpa_hosp_facility_code.header_description = ?) and (tpa_hosp_medical_details.hosp_seq_id=tpa_hosp_info.hosp_seq_id) AND (tpa_hosp_info.hosp_licenc_numb = ?)) MEDICAL_DETAIL WHERE MEDICAL_CODE.medical_type_id = MEDICAL_DETAIL.medical_type_id(+) ORDER BY MEDICAL_CODE.sort_no1 ASC, MEDICAL_CODE.sort_no2 ASC";
    private static final String strProvInfraStructureInfo = "SELECT THI.INFRASTR_SEQ_ID,THI.HOSP_SEQ_ID,THI.WHOLE_PREMISES_YN,THI.OTHER_OCCUPANTS,THI.FLOOR_DETAILS,THI.BUILT_UP_AREA,THI.OPEN_AREA,THI.COST_OF_AREA, THG.LOCATION_TYPE_ID AS LOCATION, THG.CATEGORY_TYPE_ID AS CATEGORY,THI.INFRA_REMARKS FROM TPA_HOSP_INFO I LEFT OUTER JOIN TPA_HOSP_INFRASTR_INFO THI ON (I.HOSP_SEQ_ID = THI.HOSP_SEQ_ID) LEFT OUTER JOIN TPA_HOSP_GRADING THG ON (THG.HOSP_SEQ_ID = I.HOSP_SEQ_ID) JOIN TPA_HOSP_LOCATION_CODE LC ON (LC.LOCATION_TYPE_ID = THG.LOCATION_TYPE_ID) JOIN TPA_HOSP_CATEGORY_CODE CC ON (CC.CATEGORY_TYPE_ID = THG.CATEGORY_TYPE_ID) WHERE I.HOSP_LICENC_NUMB = ?";
    private static final String strSaveOnlinefacility	=	"{call hospital_empanel_pkg_add_provider_facilities(?,?,?,?,?)}";

    /**
     * This method adds or updates the grading general details
     * The method also calls other methods on DAO to insert/update the grading general details to the database
     * @param gradingVO GradingVO object which contains the grading general details to be added/updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateGradingGeneral(GradingVO gradingVO) throws TTKException{
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddUpdateGeneralInfo);
            if(gradingVO.getHospSeqId() == null)
            {
                cStmtObject.setLong(1,0);//HOSP_SEQ_ID
            }//end of if(gradingVO.getHospSeqId() == null)
            else
            {
                cStmtObject.setLong(1,gradingVO.getHospSeqId());//HOSP_SEQ_ID
            }//end of else
            cStmtObject.setString(2,gradingVO.getLocation());//LOCATION_TYPE_ID
            cStmtObject.setString(3,gradingVO.getCategory());//CATEGORY_TYPE_ID
            cStmtObject.setLong(4, gradingVO.getUpdatedBy());//USER_SEQ_ID
            cStmtObject.registerOutParameter(5,Types.INTEGER);//ROW_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(5);
        }//end of try
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "grading");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "grading");
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
        			log.error("Error while closing the Statement in GradingDAOImpl addUpdateGradingGeneral()",sqlExp);
        			throw new TTKException(sqlExp, "grading");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in GradingDAOImpl addUpdateGradingGeneral()",sqlExp);
        				throw new TTKException(sqlExp, "grading");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "grading");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of addUpdateGradingGeneral(GradingVO gradingVO)

    /**
     * This method returns the GradingVO, which contains all the grading general details
     * @param lHospSeqId long object which contains the Hospital Seq Id
     * @return GradingVO object which contains all the grading general details
     * @exception throws TTKException
     */
    public GradingVO getGradingGeneralInfo(long lHospSeqId) throws TTKException{
    	Connection conn = null;
    	PreparedStatement pStmt = null;
        ResultSet rs = null;
        GradingVO gradingVO = new GradingVO();
        try{
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strGradingGeneralInfo);
            pStmt.setLong(1,lHospSeqId);
            rs = pStmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    if(rs.getString("HOSP_SEQ_ID") != null)
                    {
                        gradingVO.setHospSeqId(new Long(rs.getLong("HOSP_SEQ_ID")));
                    }//end of if(rs.getString("HOSP_SEQ_ID") != null)
                    gradingVO.setLocation(TTKCommon.checkNull(rs.getString("LOCATION_TYPE_ID")));
                    gradingVO.setCategory(TTKCommon.checkNull(rs.getString("CATEGORY_TYPE_ID")));
                }//end of while(rs.next())
            }//end of if(rs != null)
            return gradingVO;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "grading");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "grading");
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
					log.error("Error while closing the Resultset in GradingDAOImpl getGradingGeneralInfo()",sqlExp);
					throw new TTKException(sqlExp, "grading");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in GradingDAOImpl getGradingGeneralInfo()",sqlExp);
						throw new TTKException(sqlExp, "grading");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in GradingDAOImpl getGradingGeneralInfo()",sqlExp);
							throw new TTKException(sqlExp, "grading");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "grading");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getGradingGeneralInfo(long lHospSeqId)

    /**
     * This method adds or updates the grading infrastructure details
     * The method also calls other methods on DAO to insert/update the grading infrastructure details to the database
     * @param infraStructureVO InfraStructureVO object which contains the grading infrastructure details to be added/updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateInfraStructureDetails(InfraStructureVO infraStructureVO) throws TTKException{
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddUpdateInfraStructureInfo);

            if(infraStructureVO.getInfrastrSeqId() == null)
            {
                cStmtObject.setLong(1,0);//INFRASTR_SEQ_ID
            }//end of if(infraStructureVO.getInfrastrSeqId() == null)
            else
            {
                cStmtObject.setLong(1,infraStructureVO.getInfrastrSeqId().longValue());//INFRASTR_SEQ_ID
            }//end of else
            if(infraStructureVO.getHospSeqId() == null)
            {
                cStmtObject.setLong(2,0);//HOSP_SEQ_ID
            }//end of if(infraStructureVO.getHospSeqId() == null)
            else
            {
                cStmtObject.setLong(2,infraStructureVO.getHospSeqId().longValue());//HOSP_SEQ_ID
            }//end of else
            cStmtObject.setString(3,infraStructureVO.getWholePremisesYN());//WHOLE_PREMISES_YN
            cStmtObject.setString(4,infraStructureVO.getOtherOccupants());//OTHER_OCCUPANTS
            cStmtObject.setString(5,infraStructureVO.getFloorDetails());//FLOOR_DETAILS
            if(infraStructureVO.getBuiltUpArea()!= null)
            {
               // cStmtObject.setBigDecimal(6, infraStructureVO.getBuiltUpArea());//BUILT_UP_AREA
                cStmtObject.setDouble(6,infraStructureVO.getBuiltUpArea().doubleValue());
            }//end of if(infraStructureVO.getBuiltUpArea()!= null)
            else
            {
            	 cStmtObject.setDouble(6,0);//BUILT_UP_AREA
            }//end of else
            if(infraStructureVO.getOpenArea()!= null)
            {
               // cStmtObject.setBigDecimal(7, infraStructureVO.getOpenArea());//OPEN_AREA
                cStmtObject.setDouble(7, infraStructureVO.getOpenArea().doubleValue());//OPEN_AREA
            }//end of if(infraStructureVO.getOpenArea()!= null)
            else
            {
                cStmtObject.setDouble(7,0);//OPEN_AREA
            }//end of else
            if(infraStructureVO.getCostOfArea()!= null)
            {
                //cStmtObject.setBigDecimal(8, infraStructureVO.getCostOfArea());//COST_OF_AREA
                cStmtObject.setDouble(8, infraStructureVO.getCostOfArea().doubleValue());//COST_OF_AREA
            }//end of if(infraStructureVO.getCostOfArea()!= null)
            else
            {
                cStmtObject.setDouble(8,0);//COST_OF_AREA
            }//end of else
            cStmtObject.setString(9,infraStructureVO.getRemarks());//Remarks
            cStmtObject.setLong(10, infraStructureVO.getUpdatedBy().longValue());//USER_SEQ_ID
            
            //Added for projectX
            cStmtObject.setString(11, infraStructureVO.getLocation());//LOCATION_ID
            cStmtObject.setString(12, infraStructureVO.getCategory());//CATEGORY_ID
            
            cStmtObject.registerOutParameter(13,Types.INTEGER);//ROW_PROCESSED
            cStmtObject.execute();
           
            iResult = cStmtObject.getInt(13);//ROW_PROCESSED
            conn.commit();
        }//end of try
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "grading");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "grading");
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
        			log.error("Error while closing the Statement in GradingDAOImpl addUpdateInfrastructureDetails()",sqlExp);
        			throw new TTKException(sqlExp, "grading");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in GradingDAOImpl addUpdateInfrastructureDetails()",sqlExp);
        				throw new TTKException(sqlExp, "grading");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "grading");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }// end of addUpdateInfrastructureDetails(InfrastructureVO infraStructureVO)

    /**
     * This method returns the InfraStructureVO, which contains all the grading infrastructure details
     * @param lHospSeqId long object which contains the Hospital Seq Id
     * @return InfraStructureVO object which contains all the grading infrastructure details
     * @exception throws TTKException
     */
    public InfraStructureVO getInfraStructureInfo(long lHospSeqId) throws TTKException{
    	Connection conn = null;
    	PreparedStatement pStmt = null;
        ResultSet rs = null;
        InfraStructureVO infraStructureVO = null;
        try{
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strInfraStructureInfo);
            pStmt.setLong(1,lHospSeqId);
            rs = pStmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    infraStructureVO = new InfraStructureVO();
                    if(rs.getString("INFRASTR_SEQ_ID") != null)
                    {
                        infraStructureVO.setInfrastrSeqId(new Long(rs.getLong("INFRASTR_SEQ_ID")));
                    }//end of if(rs.getString("INFRASTR_SEQ_ID") != null)
                    if(rs.getString("HOSP_SEQ_ID") != null)
                    {
                        infraStructureVO.setHospSeqId(new Long(rs.getLong("HOSP_SEQ_ID")));
                    }//end of if(rs.getString("HOSP_SEQ_ID") != null)
                    infraStructureVO.setWholePremisesYN(TTKCommon.checkNull(rs.getString("WHOLE_PREMISES_YN")));
                    infraStructureVO.setOtherOccupants(TTKCommon.checkNull(rs.getString("OTHER_OCCUPANTS")));
                    infraStructureVO.setFloorDetails(TTKCommon.checkNull(rs.getString("FLOOR_DETAILS")));
                    if(rs.getString("BUILT_UP_AREA") != null)
                    {
                        infraStructureVO.setBuiltUpArea(new BigDecimal(rs.getString("BUILT_UP_AREA")));
                    }//end of if(rs.getString("BUILT_UP_AREA") != null)
                    if(rs.getString("OPEN_AREA") != null)
                    {
                        infraStructureVO.setOpenArea(new BigDecimal(rs.getString("OPEN_AREA")));
                    }//end of if(rs.getString("OPEN_AREA") != null)
                    if(rs.getString("COST_OF_AREA") != null)
                    {
                        infraStructureVO.setCostOfArea(new BigDecimal(rs.getString("COST_OF_AREA")));
                    }//end of if(rs.getString("COST_OF_AREA") != null)
                    infraStructureVO.setRemarks(TTKCommon.checkNull(rs.getString("INFRA_REMARKS")));//INFRA_REMARKS
                    infraStructureVO.setLocation(TTKCommon.checkNull(rs.getString("LOCATION")));
                    infraStructureVO.setCategory(TTKCommon.checkNull(rs.getString("CATEGORY")));
                }//end of while(rs.next())
            }//end of if(rs != null)
            return infraStructureVO;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "grading");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "grading");
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
					log.error("Error while closing the Resultset in GradingDAOImpl getInfraStructureInfo()",sqlExp);
					throw new TTKException(sqlExp, "grading");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in GradingDAOImpl getInfraStructureInfo()",sqlExp);
						throw new TTKException(sqlExp, "grading");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in GradingDAOImpl getInfraStructureInfo()",sqlExp);
							throw new TTKException(sqlExp, "grading");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "grading");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getInfraStructureInfo(long lHospSeqId)
    
    /**
     * This method returns the InfraStructureVO, which contains all the grading infrastructure details
     * @param lHospSeqId long object which contains the Hospital Seq Id
     * @return InfraStructureVO object which contains all the grading infrastructure details
     * @exception throws TTKException
     */
    public InfraStructureVO getProvInfraStructureInfo(String strUserId) throws TTKException{
    	Connection conn = null;
    	PreparedStatement pStmt = null;
        ResultSet rs = null;
        InfraStructureVO infraStructureVO = null;
        try{
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strProvInfraStructureInfo);
            pStmt.setString(1,strUserId);
            rs = pStmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    infraStructureVO = new InfraStructureVO();
                    if(rs.getString("INFRASTR_SEQ_ID") != null)
                    {
                        infraStructureVO.setInfrastrSeqId(new Long(rs.getLong("INFRASTR_SEQ_ID")));
                    }//end of if(rs.getString("INFRASTR_SEQ_ID") != null)
                    if(rs.getString("HOSP_SEQ_ID") != null)
                    {
                        infraStructureVO.setHospSeqId(new Long(rs.getLong("HOSP_SEQ_ID")));
                    }//end of if(rs.getString("HOSP_SEQ_ID") != null)
                    infraStructureVO.setWholePremisesYN(TTKCommon.checkNull(rs.getString("WHOLE_PREMISES_YN")));
                    infraStructureVO.setOtherOccupants(TTKCommon.checkNull(rs.getString("OTHER_OCCUPANTS")));
                    infraStructureVO.setFloorDetails(TTKCommon.checkNull(rs.getString("FLOOR_DETAILS")));
                    if(rs.getString("BUILT_UP_AREA") != null)
                    {
                        infraStructureVO.setBuiltUpArea(new BigDecimal(rs.getString("BUILT_UP_AREA")));
                    }//end of if(rs.getString("BUILT_UP_AREA") != null)
                    if(rs.getString("OPEN_AREA") != null)
                    {
                        infraStructureVO.setOpenArea(new BigDecimal(rs.getString("OPEN_AREA")));
                    }//end of if(rs.getString("OPEN_AREA") != null)
                    if(rs.getString("COST_OF_AREA") != null)
                    {
                        infraStructureVO.setCostOfArea(new BigDecimal(rs.getString("COST_OF_AREA")));
                    }//end of if(rs.getString("COST_OF_AREA") != null)
                    infraStructureVO.setRemarks(TTKCommon.checkNull(rs.getString("INFRA_REMARKS")));//INFRA_REMARKS
                    infraStructureVO.setLocation(TTKCommon.checkNull(rs.getString("LOCATION")));
                    infraStructureVO.setCategory(TTKCommon.checkNull(rs.getString("CATEGORY")));
                }//end of while(rs.next())
            }//end of if(rs != null)
            return infraStructureVO;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "grading");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "grading");
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
					log.error("Error while closing the Resultset in GradingDAOImpl getProvInfraStructureInfo()",sqlExp);
					throw new TTKException(sqlExp, "grading");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in GradingDAOImpl getProvInfraStructureInfo()",sqlExp);
						throw new TTKException(sqlExp, "grading");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in GradingDAOImpl getProvInfraStructureInfo()",sqlExp);
							throw new TTKException(sqlExp, "grading");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "grading");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getProvInfraStructureInfo(long lHospSeqId)

    /**
     * This method adds or updates the grading details
     * The method also calls other methods on DAO to insert/update the grading details to the database
     * @param gradingVO GradingVO object which contains the grading details to be added/updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateGrading(GradingVO gradingVO) throws TTKException{
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddUpdateGradingInfo);

            if(gradingVO.getHospSeqId() != null)
            {
                cStmtObject.setLong(1,gradingVO.getHospSeqId().longValue());//HOSP_SEQ_ID
            }//end of if(gradingVO.getHospSeqId() != null)
            else
            {
                cStmtObject.setLong(1,0);//HOSP_SEQ_ID
            }//end of else
            cStmtObject.setString(2,TTKCommon.checkNull(gradingVO.getGradeTypeId()));//GRADE_TYPE_ID
            if(gradingVO.getApprovedBy() != null)
            {
                cStmtObject.setLong(3,gradingVO.getApprovedBy());//APPROVED_BY
            }//end of if(gradingVO.getApprovedBy() != null)
            else
            {
                cStmtObject.setLong(3,0);
            }//end of else
            cStmtObject.setString(4, TTKCommon.checkNull(gradingVO.getApprovedGrade()));//APPROVED_GRADE_TYPE_ID
            cStmtObject.setString(5,TTKCommon.checkNull(gradingVO.getRemarks()));//REMARKS
            if(gradingVO.getUpdatedBy() != null)
            {
                cStmtObject.setLong(6, gradingVO.getUpdatedBy().longValue());//USER_SEQ_ID
            }//end of if(gradingVO.getUpdatedBy() != null)
            else
            {
                cStmtObject.setLong(6, 0);//USER_SEQ_ID
            }//end of else
            cStmtObject.registerOutParameter(7,Types.INTEGER);//ROW_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(7);
            conn.commit();
            
        }//end of try
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "grading");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "grading");
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
        			log.error("Error while closing the Statement in GradingDAOImpl addUpdateGrading()",sqlExp);
        			throw new TTKException(sqlExp, "grading");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in GradingDAOImpl addUpdateGrading()",sqlExp);
        				throw new TTKException(sqlExp, "grading");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "grading");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of addUpdateGrading(GradingVO gradingVO)

    /**
     * This method returns the GradingVO, which contains all the details about the grading details
     * @param lHospSeqId long object which contains the Hospital Seq Id
     * @return GradingVO object which contains all the details about the grading infrastructure details
     * @exception throws TTKException
     */
    public GradingVO getGradingInfo(long lHospSeqId) throws TTKException{
    	Connection conn = null;
    	PreparedStatement pStmt = null;
        ResultSet rs = null;
        GradingVO gradingVO = new GradingVO();
        try{
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strGradingInfo);
            pStmt.setLong(1,lHospSeqId);
            rs = pStmt.executeQuery();
            conn.commit();
            if(rs != null){
                while(rs.next()){
                    if(rs.getString("HOSP_SEQ_ID") != null)
                    {
                        gradingVO.setHospSeqId(new Long(rs.getLong("HOSP_SEQ_ID")));
                    }//end of if(rs.getString("HOSP_SEQ_ID") != null)
                    gradingVO.setGrading(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                    gradingVO.setGradeTypeId(TTKCommon.checkNull(rs.getString("GRADE_TYPE_ID")));
                    gradingVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
                    gradingVO.setSystemGradedId(TTKCommon.checkNull(rs.getString("SYSTEM_GRADED_ID")));
                    gradingVO.setApprovedGrade(TTKCommon.checkNull(rs.getString("APPROVED_GRADE_TYPE_ID")));
                    gradingVO.setSystemGradedDesc(TTKCommon.checkNull(rs.getString("SYSTEM_GRADE_DESC")));
                    gradingVO.setApprovedGradeDesc(TTKCommon.checkNull(rs.getString("APPROVED_GRADE_DESC")));
                }//end of while(rs.next())
            }//end of if(rs != null)
            return gradingVO;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "grading");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "grading");
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
					log.error("Error while closing the Resultset in GradingDAOImpl getGradingInfo()",sqlExp);
					throw new TTKException(sqlExp, "grading");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in GradingDAOImpl getGradingInfo()",sqlExp);
						throw new TTKException(sqlExp, "grading");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in GradingDAOImpl getGradingInfo()",sqlExp);
							throw new TTKException(sqlExp, "grading");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "grading");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getGradingInfo(long lHospSeqId)

    /**
     * This method adds or updates the grading services details
     * The method also calls other methods on DAO to insert/update the grading services details to the database
     * @param gradingServicesVO GradingServicesVO object which contains the grading services details to be added/updated
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int addUpdateServices(GradingServicesVO gradingServicesVO) throws TTKException{
        int iResult = 1;
        Connection conn = null;
        CallableStatement callStmt = null;
        try{
            conn = ResourceManager.getConnection();
            callStmt = conn.prepareCall(strAddUpdateServicesInfo);
            if(gradingServicesVO.getMedicalSeqIdList()!= null){

                Long[] medicalSeqIdList=gradingServicesVO.getMedicalSeqIdList();
                Long hospSeqId=gradingServicesVO.getHospSeqId();
                String[] medicalTypeIdList=gradingServicesVO.getMedicalTypeIdList();
                String[] answer1List=gradingServicesVO.getAnswer1List();
                String[] answer2List=gradingServicesVO.getAnswer2List();
                Long updatedBy=gradingServicesVO.getUpdatedBy();

                for(int i=0;i<medicalSeqIdList.length;i++){

                   if(medicalSeqIdList[i]==null)callStmt.setLong(1, 0);
                    else callStmt.setLong(1, medicalSeqIdList[i]);

                    if(hospSeqId==null)callStmt.setLong(2, 0);
                    else callStmt.setLong(2, hospSeqId);
              if(!"".equals(TTKCommon.checkNull(medicalTypeIdList[i])))callStmt.setString(3, medicalTypeIdList[i]);
                    else callStmt.setString(3, null);
             if(!"".equals(TTKCommon.checkNull(answer1List[i])))callStmt.setString(4, answer1List[i]);
                    else callStmt.setString(4, null);
             if(answer2List!=null){
             if(!"".equals(TTKCommon.checkNull(answer2List[i])))callStmt.setString(5, answer2List[i]);
             else callStmt.setString(5, null);
             }else callStmt.setString(5, null);
                    callStmt.setLong(6, updatedBy);
                    callStmt.addBatch();
                }//end of for
            }//end of if(cardRuleVO.getCardRuleTypeIdList()!= null)
            callStmt.executeBatch();
            conn.commit();
        }//end of try
        catch (SQLException sqlExp)
        {

                throw new TTKException(sqlExp, "cardrules");

        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {

                throw new TTKException(exp, "cardrules");

        }//end of catch (Exception exp)
        finally
                                {
                /* Nested Try Catch to ensure resource closure */
                try // First try closing the Statement
                {
                                try
                                {
                                                if (callStmt != null) callStmt.close();
                                }//end of try
                                catch (SQLException sqlExp)
                                {
                                                log.error("Error while closing the Statement in GradingDAOImpl addUpdateServices()",sqlExp);
                                                throw new TTKException(sqlExp, "grading");
                                }//end of catch (SQLException sqlExp)
                                finally // Even if statement is not closed, control reaches here. Try closing the connection now.
                                {
                                                try
                                                {
if(conn != null) conn.close();
                                                }//end of try
                                                catch (SQLException sqlExp)
                                                {
log.error("Error while closing the Connection in GradingDAOImpl addUpdateServices()",sqlExp);
throw new TTKException(sqlExp, "grading");
                                                }//end of catch (SQLException sqlExp)
                                }//end of finally Connection Close
                }//end of try
                catch (TTKException exp)
                {
                                throw new TTKException(exp, "grading");
                }//end of catch (TTKException exp)
                finally // Control will reach here in anycase set null to the objects
                {
                    callStmt = null;
                    conn = null;
                }//end of finally
                                }//end of finally
        return iResult;
    }//end of updateCardRule(CardRuleVO cardRuleVO)

    
    
    public int addUpdateServices2(GradingServicesVO gradingServicesVO) throws TTKException{
        int iResult = 0;
        Connection conn = null;
        Statement stmt = null;
        String strSQL ="";
        CallableStatement callStmt = null;
        try{
            conn = ResourceManager.getConnection();
            conn.setAutoCommit(false);
            stmt = (java.sql.Statement)conn.createStatement();
            if(gradingServicesVO.getMedicalSeqIdList() != null) {
                for(int i=0;i<gradingServicesVO.getMedicalTypeIdList().length;i++){
                	
                	
                    strSQL ="";
                    strSQL = strSQL+"'"+gradingServicesVO.getMedicalSeqIdList() [i]+"',"; //MEDICAL_SEQ_ID
                    strSQL = strSQL+"'"+gradingServicesVO.getHospSeqId()+"',";//HOSP_SEQ_ID
                    strSQL = strSQL+"'"+gradingServicesVO.getMedicalTypeIdList()[i]+"',"; //MEDICAL_TYPE_ID
                    if(gradingServicesVO.getAnswer1List()[i] .equals(""))
                    {
                        strSQL = strSQL+""+null+",";//VALUE1
                    }//end of if(gradingServicesVO.getAnswer1List()[i] .equals(""))
                    else
                    {
                        strSQL = strSQL+"'"+gradingServicesVO.getAnswer1List()[i]+"',"; //VALUE1
                    }//end of else
                    if(gradingServicesVO.getAnswer2List() != null)
                    {
                        strSQL = strSQL+"'"+gradingServicesVO.getAnswer2List()[i]+"',"; //VALUE2
                    }//end of if(gradingServicesVO.getAnswer2List() != null)
                    else
                    {
                        strSQL = strSQL+""+null+",";//VALUE2
                    }//end of else
                   // strSQL = strSQL+"'"+gradingServicesVO.getUpdatedBy()+"')}"; //USER_SEQ_ID
                    strSQL = strSQL+"'"+gradingServicesVO.getUpdatedBy()+"')"; //USER_SEQ_ID
                   // System.out.println("grading save..."+strAddUpdateServicesInfo+strSQL);
                    stmt.addBatch(strAddUpdateServicesInfo+strSQL);
                    conn.commit();
                }//end of for
            }//end of if(gradingServicesVO.getMedicalTypeIdList() != null)
            stmt.executeBatch();
        }//end of try
        catch (SQLException sqlExp)
        {
            try {
                conn.rollback();
                throw new TTKException(sqlExp, "grading");
            } //end of try
            catch (SQLException sExp) {
                throw new TTKException(sExp, "grading");
            }//end of catch (SQLException sExp)
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            try {
                conn.rollback();
                throw new TTKException(exp, "grading");
            } //end of try
            catch (SQLException sqlExp) {
                throw new TTKException(sqlExp, "grading");
            }//end of catch (SQLException sExp)
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (stmt != null) stmt.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in GradingDAOImpl addUpdateServices()",sqlExp);
        			throw new TTKException(sqlExp, "grading");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in GradingDAOImpl addUpdateServices()",sqlExp);
        				throw new TTKException(sqlExp, "grading");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "grading");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		stmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        iResult = 1;
        return iResult;
    }//end of addUpdateServices(GradingServicesVO gradingServicesVO)
    
    /**
     * This method adds or updates the grading services details
     * The method also calls other methods on DAO to insert/update the grading services details to the database
     * @param gradingServicesVO GradingServicesVO object which contains the grading services details to be added/updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateServices(OnlineFacilityVO onlineFacilityVO) throws TTKException{
        int iResult = 0;
        Connection conn = null;
        Statement stmt = null;
        CallableStatement cStmtObject=null;
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveOnlinefacility);
            
            cStmtObject.setString( 1, onlineFacilityVO.getFacilityHeader());
			
			cStmtObject.setString( 2, onlineFacilityVO.getMedicalAbbr());
			cStmtObject.setString( 3, onlineFacilityVO.getMedicalDesc());
			cStmtObject.setLong( 4, onlineFacilityVO.getAddedBy());
			cStmtObject.registerOutParameter(5,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(5);//ROW_PROCESSED
			conn.commit();
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "services");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "services");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (stmt != null) stmt.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in GradingDAOImpl addUpdateServices()",sqlExp);
        			throw new TTKException(sqlExp, "grading");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in GradingDAOImpl addUpdateServices()",sqlExp);
        				throw new TTKException(sqlExp, "grading");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "grading");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		stmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of addUpdateServices(OnlineFacilityVO onlineFacilityVO)

    /**
     * This method returns the ArrayList, which contains all the grading services details
     * @param strServiceId String which contains the Service Id
     * @param lHospSeqId long value which contains the Hospital Seq Id
     * @return ArrayList object which contains all the grading services details
     * @exception throws TTKException
     */
    public ArrayList getServices(String strServiceId,long lHospSeqId) throws TTKException{
    	Connection conn = null;
    	PreparedStatement pStmt = null;
        ResultSet rs = null;
        GradingServicesVO gradingServicesVO = null;
        ArrayList<Object> alServicesList = null;
        try{
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strServicesInfo);
            pStmt.setString(1,strServiceId);
            pStmt.setString(2,strServiceId);
            pStmt.setString(3,strServiceId);
            pStmt.setString(4,strServiceId);
            pStmt.setLong(5,lHospSeqId);
            rs = pStmt.executeQuery();
            alServicesList= new ArrayList<Object>();
            if(rs != null){
                while(rs.next()){
                    gradingServicesVO = new GradingServicesVO();
                    if(rs.getString("MEDICAL_SEQ_ID") != null)
                    {
                        gradingServicesVO.setMedicalSeqId(new Long(rs.getLong("MEDICAL_SEQ_ID")));
                    }//end of if(rs.getString("MEDICAL_SEQ_ID") != null)
                    gradingServicesVO.setMedicalTypeId(TTKCommon.checkNull(rs.getString("MEDICAL_TYPE_ID")));
                    gradingServicesVO.setGroupName(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                    gradingServicesVO.setQuestionDesc(TTKCommon.checkNull(rs.getString("MEDICAL_DESCRIPTION")));
                    gradingServicesVO.setAnswer1(TTKCommon.checkNull(rs.getString("VALUE_1")));
                    if((TTKCommon.checkNull(rs.getString("VALUE_2")) != null) && (!TTKCommon.checkNull(rs.getString("VALUE_2")).equals("")))
                    {
                        gradingServicesVO.setAnswer2(TTKCommon.checkNull(rs.getString("VALUE_2")));
                    }//end of if((TTKCommon.checkNull(rs.getString("VALUE_2")) != null) && (!TTKCommon.checkNull(rs.getString("VALUE_2")).equals("")))
                    alServicesList.add(gradingServicesVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alServicesList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "grading");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "grading");
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
					log.error("Error while closing the Resultset in GradingDAOImpl getServices()",sqlExp);
					throw new TTKException(sqlExp, "grading");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in GradingDAOImpl getServices()",sqlExp);
						throw new TTKException(sqlExp, "grading");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in GradingDAOImpl getServices()",sqlExp);
							throw new TTKException(sqlExp, "grading");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "grading");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
   }//end of getServices(String strServiceId,long lHospSeqId)



    /**
     * This method returns the ArrayList, which contains all the grading services details
     * @param strServiceId String which contains the Service Id
     * @param lHospSeqId long value which contains the Hospital Seq Id
     * @return ArrayList object which contains all the grading services details
     * @exception throws TTKException
     */
    public ArrayList getProvServices(String strServiceId,String UserId) throws TTKException{
    	Connection conn = null;
    	PreparedStatement pStmt = null;
        ResultSet rs = null;
        GradingServicesVO gradingServicesVO = null;
        ArrayList<Object> alServicesList = null;
        try{
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strProvServicesInfo);
            pStmt.setString(1,strServiceId);
            pStmt.setString(2,strServiceId);
            pStmt.setString(3,strServiceId);
            pStmt.setString(4,strServiceId);
            pStmt.setString(5,UserId);
            rs = pStmt.executeQuery();
            alServicesList= new ArrayList<Object>();
            if(rs != null){
                while(rs.next()){
                    gradingServicesVO = new GradingServicesVO();
                    if(rs.getString("MEDICAL_SEQ_ID") != null)
                    {
                        gradingServicesVO.setMedicalSeqId(new Long(rs.getLong("MEDICAL_SEQ_ID")));
                    }//end of if(rs.getString("MEDICAL_SEQ_ID") != null)
                    gradingServicesVO.setMedicalTypeId(TTKCommon.checkNull(rs.getString("MEDICAL_TYPE_ID")));
                    gradingServicesVO.setGroupName(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                    gradingServicesVO.setQuestionDesc(TTKCommon.checkNull(rs.getString("MEDICAL_DESCRIPTION")));
                    gradingServicesVO.setAnswer1(TTKCommon.checkNull(rs.getString("VALUE_1")));
                    if((TTKCommon.checkNull(rs.getString("VALUE_2")) != null) && (!TTKCommon.checkNull(rs.getString("VALUE_2")).equals("")))
                    {
                        gradingServicesVO.setAnswer2(TTKCommon.checkNull(rs.getString("VALUE_2")));
                    }//end of if((TTKCommon.checkNull(rs.getString("VALUE_2")) != null) && (!TTKCommon.checkNull(rs.getString("VALUE_2")).equals("")))
                    alServicesList.add(gradingServicesVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alServicesList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "grading");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "grading");
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
					log.error("Error while closing the Resultset in GradingDAOImpl getServices()",sqlExp);
					throw new TTKException(sqlExp, "grading");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in GradingDAOImpl getServices()",sqlExp);
						throw new TTKException(sqlExp, "grading");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in GradingDAOImpl getServices()",sqlExp);
							throw new TTKException(sqlExp, "grading");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "grading");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
   }//end of getProvServices(String strServiceId,long lHospSeqId)

    /**
     * This method adds or updates the grading overriding details
     * The method also calls other methods on DAO to insert/update the grading overriding details to the database
     * @param long lHospSeqId which contains the Hospital Sequence Id
     * @return grade type id  String, contains generated Grade Type Id
     * @exception throws TTKException
     */
    public String generateGrade(long lHospSeqId) throws TTKException{
        String strGradeTypeid = "";
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGenerateGradeInfo);
            cStmtObject.setLong(1,lHospSeqId);//HOSP_SEQ_ID
            cStmtObject.registerOutParameter(2,Types.VARCHAR);//ROW_PROCESSED
            cStmtObject.execute();
            conn.commit();
            strGradeTypeid = cStmtObject.getString(2);
        }//end of try
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "grading");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "grading");
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
        			log.error("Error while closing the Statement in GradingDAOImpl generateGrade()",sqlExp);
        			throw new TTKException(sqlExp, "grading");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in GradingDAOImpl generateGrade()",sqlExp);
        				throw new TTKException(sqlExp, "grading");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "grading");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return strGradeTypeid;
    }//end of generateGrade(long lHospSeqId)
}//end of GradingDAOImpl
