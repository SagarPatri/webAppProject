
/**
  * @ (#) ReportCacheDAOImpl.java May 29, 2007
  * Project      : TTK HealthCare Services
  * File         : ReportCacheDAOImpl.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : 
  *
  * @author       :  Ajay Kumar
  * Modified by   :
  * Modified date :
  * Reason        :
  */

package com.ttk.dao.impl.common.misreports;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ReportResourceManager;
import com.ttk.dto.BaseVO;
import com.ttk.dto.common.CacheObject;

public class ReportCacheDAOImpl implements BaseDAO, Serializable {
	
	private static Logger log = Logger.getLogger( ReportCacheDAOImpl.class );
	
	//private static final String strTTK_TPA_OFFICE_INFO = "SELECT TPA_OFFICE_SEQ_ID, OFFICE_NAME FROM TPA_OFFICE_INFO ORDER BY OFFICE_NAME";
	private static final String strTTK_TPA_OFFICE_INFO = "SELECT TPA_OFFICE_SEQ_ID, OFFICE_NAME FROM TPA_OFFICE_INFO O WHERE O.TPA_OFFICE_GENERAL_TYPE_ID='TBO' ORDER BY OFFICE_NAME";
    private static final String strTPA_INS_INFO_ABB = "SELECT ABBREVATION_CODE,INS_COMP_NAME FROM APP.TPA_INS_INFO ORDER BY INS_COMP_NAME";
    private static final String strTPA_INS_INFO_DoBo = "SELECT INS_SEQ_ID,INS_COMP_CODE_NUMBER FROM APP.TPA_INS_INFO ORDER BY INS_COMP_CODE_NUMBER";
    //private static final String strTTK_HOSPITAL_NAME_INFO = "SELECT HOSP_SEQ_ID, HOSP_NAME FROM tpa_hosp_info ORDER BY HOSP_NAME";
    //private static final String strTTK_HOSPITAL_NAME_INFO = "SELECT HOSP_SEQ_ID,h.HOSP_NAME||'-'||o.office_name ||'-'|| h.empanel_number as Name FROM tpa_hosp_info h,tpa_office_info o where h.tpa_office_seq_id=o.tpa_office_seq_id";
    private static final String strTTK_HOSPITAL_NAME_INFO = "SELECT h.HOSP_SEQ_ID,h.HOSP_NAME||'-'||a.CITY ||'-'|| h.empanel_number FROM tpa_hosp_info h,tpa_hosp_address a where h.hosp_seq_id=a.HOSP_SEQ_ID ORDER BY HOSP_NAME";
    private static final String strTPA_INS_INFO = "SELECT INS_SEQ_ID,INS_COMP_NAME FROM APP.TPA_INS_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO' ORDER BY INS_COMP_NAME";
    private static final String strTPA_OFFICE_INFO = "SELECT TPA_OFFICE_SEQ_ID, OFFICE_NAME FROM TPA_OFFICE_INFO ORDER BY OFFICE_NAME";
    private static final String strTPA_ENROLMENT_TYPE_CODE= "SELECT ENROL_TYPE_ID,ENROL_DESCRIPTION FROM TPA_ENROLMENT_TYPE_CODE ORDER BY SORT_NO";
    private static final String strLIABILITY_STATUS = "SELECT GENERAL_TYPE_ID,DESCRIPTION FROM TPA_GENERAL_CODE WHERE HEADER_TYPE = 'LIABILITY_STATUS' ORDER BY SORT_NO";
    private static final String strInward_Register_INFO = "SELECT G.GENERAL_TYPE_ID,upper(G.DESCRIPTION) AS INWARD_REGISTER FROM TPA_GENERAL_CODE G WHERE G.GENERAL_TYPE_ID IN('DTA','DTS','DTC')";
    private static final String strMIS_REPORTS_CODING = "SELECT GENERAL_TYPE_ID,DESCRIPTION FROM TPA_GENERAL_CODE WHERE HEADER_TYPE = 'CODING_MIS_REPORT' ORDER BY SORT_NO";
    private static final String strInvestigat_Agency_Details = "SELECT INVEST_AGNCY_SEQ_ID,AGENCY_NAME FROM INVESTIGATION_AGENCY_DETAILS ORDER BY UPPER(AGENCY_NAME)";
    private static final String strPeriodDetails="SELECT GENERAL_TYPE_ID,DESCRIPTION FROM TPA_GENERAL_CODE WHERE HEADER_TYPE = 'SINCE_WHEN' AND GENERAL_TYPE_ID NOT IN('S45') ORDER BY SORT_NO";
    
    private static final String strPREAUTH_STATUS = "SELECT GENERAL_TYPE_ID,DESCRIPTION FROM TPA_GENERAL_CODE WHERE HEADER_TYPE = 'PREAUTH_STATUS'";
  
    private static final String strInsCmpnyList ="SELECT INS_SEQ_ID, INS_COMP_NAME as INS_COMP_NAME FROM APP.TPA_INS_INFO ORDER BY INS_COMP_NAME";
    
    
    
   
    
    
    /**
     * This method populates the appropriate query to be loaded based on the identifier    
     * @param strIdentifier String object which identifies the query to be executed
     * @return Collection of value objects
     * @exception throws TTKException
     */
    public Collection loadObjects(String strIdentifier) throws TTKException 
    {
        String strQuery = "";
        
        if(strIdentifier.equals("ttkOfficeInfo"))
            strQuery = strTTK_TPA_OFFICE_INFO;
       
        else if (strIdentifier.equals("insCompany"))
            strQuery = strTPA_INS_INFO_ABB;
        else if (strIdentifier.equals("insDoBo"))
            strQuery = strTPA_INS_INFO_DoBo;
        else if (strIdentifier.equals("hospitalNameInfo"))
            strQuery = strTTK_HOSPITAL_NAME_INFO;
        else if (strIdentifier.equals("insHO"))
            strQuery = strTPA_INS_INFO;
        else if (strIdentifier.equals("officeInfo"))
            strQuery = strTPA_OFFICE_INFO;
        else if (strIdentifier.equals("enrollTypeCode"))
        	strQuery = strTPA_ENROLMENT_TYPE_CODE;
        else if(strIdentifier.equals("liabilityStatus"))
            strQuery = strLIABILITY_STATUS;
        else if (strIdentifier.equals("inwardRegisterInfo"))
            strQuery = strInward_Register_INFO;
        else if (strIdentifier.equals("misReportsCoding"))
            strQuery = strMIS_REPORTS_CODING;
        else if (strIdentifier.equals("investAgencyDetails"))
        	
            strQuery = strInvestigat_Agency_Details;
        else if(strIdentifier.equals("period"))
        
        	strQuery = strPeriodDetails;
        
        else if(strIdentifier.equals("preauthStatus"))
        {
            strQuery = strPREAUTH_STATUS;
           
        }
        else if (strIdentifier.equals("inscmpnyNameList"))
            strQuery = strInsCmpnyList;
        
        
        
        
        return findAll(strQuery);
    }//end of loadObjects(String strIdentifier)
    
    /**
     * This method returns the collection of value objects  
     * @param strQuery String which contains the SQL statement to be executed
     * @exception throws TTKException
     */
    public Collection findAll(String strQuery) throws TTKException {
     
        Connection conn = null; 
        PreparedStatement stmt = null;
        ResultSet rs = null;
         
        try 
        { 
        	
            conn = ReportResourceManager.getReportConnection();
            stmt = conn.prepareStatement(strQuery);
            rs = stmt.executeQuery();
            return fetchMultiResults(rs);
        }//end of try 
        catch (SQLException sqlExp) 
        {
            throw new TTKException(sqlExp, "cache");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp) 
        {
            throw new TTKException(exp, "error.cache");
        }//end of catch (Exception exp) 
        finally 
        { 
             try 
             {
                if (rs != null)
                    rs.close(); 
                if (stmt != null) 
                    stmt.close(); 
                 if(conn != null) 
                    conn.close(); 
             }//end of try
             catch (SQLException sqlExp) 
             { 
                throw new TTKException(sqlExp, "cache");
             }//end of catch (SQLException sqlExp)
             rs = null;
             stmt = null;
             conn = null;
        }//end of finally
        
    }//end of findAll(String strQuery)
    
    /**
     * This method populates the basebean with the information contained in the resultset object    
     * @param rs ResultSet object which contains the information from the database
     * @return Collection of value objects
     * @exception throws TTKException
     */
    protected Collection fetchMultiResults(ResultSet rs) throws SQLException {
        Collection<Object> resultList = new ArrayList<Object>();
        BaseVO bvo = null;
        if(rs != null){
            while (rs.next()) {
                bvo = new CacheObject();
                populateBean(bvo, rs);
                resultList.add(bvo);
            }//end of while
        }//end of if(rs != null)
        return resultList;
    }//end of fetchMultiResults(ResultSet rs)
    
    /**
     * This method populates the basebean with the information contained in the resultset object    
     * @param baseVO BaseVO object which has to be populated
     * @param rs ResultSet object which has to be populated
     * @exception throws TTKException
     */
    protected void populateBean(BaseVO BaseVO, ResultSet rs) throws SQLException {
        CacheObject voBean = (CacheObject) BaseVO;
        voBean.setCacheId(TTKCommon.checkNull(rs.getString(1)));
        if(TTKCommon.checkNull(rs.getString(2)).length() > 50)
        	voBean.setCacheDesc(TTKCommon.checkNull(rs.getString(2)).substring(0,50));
        else
        	voBean.setCacheDesc(TTKCommon.checkNull(rs.getString(2)));
        
    }//end of populateBean method

}//end of ReportCacheDAOImpl
