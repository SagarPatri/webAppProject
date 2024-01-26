/** @ (#) ImageEnquiryDAOImpl.java May 28, 2007
 * Project     : TTK Healthcare Services
 * File        : ImageEnquiryDAOImpl.java
 * Author      : Ajay Kumar
 * Company     : WebEdge Technologies Pvt.Ltd.
 * Date Created: May 28, 2007
 *
 * @author 		 : Ajay Kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

package com.ttk.dao.impl.misreports;

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
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.misreports.ImageEnquiryVO;

public class ImageEnquiryDAOImpl implements BaseDAO,Serializable {
	
	private static Logger log = Logger.getLogger(ImageEnquiryDAOImpl.class);
	
	//private static final String strImageEnquiryList="SELECT * FROM(SELECT pol.policy_number,pg.tpa_enrollment_number,mem.tpa_enrollment_id,pg.employee_no,grp.group_name,mem.mem_name,grp.group_id,img.image FROM  tpa_enr_policy pol,tpa_enrolment_type_code etc,tpa_enr_policy_group pg,tpa_enr_policy_member mem,tpa_group_registration grp,image_data img WHERE (etc.enrol_type_id=pol.enrol_type_id)AND (pol.policy_seq_id=pg.policy_seq_id)AND (mem.policy_group_seq_id=pg.policy_group_seq_id)AND ( pol.group_reg_seq_id=grp.group_reg_seq_id (+))AND (img.enrolment_id=mem.tpa_enrollment_id)";
	//changed as per 11G
	private static final String strImageEnquiryList="SELECT * FROM(SELECT pol.policy_number,pg.tpa_enrollment_number,mem.tpa_enrollment_id,pg.employee_no,grp.group_name,mem.mem_name,grp.group_id,img.image FROM  tpa_enr_policy pol,tpa_enrolment_type_code etc,tpa_enr_policy_group pg,tpa_enr_policy_member mem,tpa_group_registration grp,image_data img WHERE (etc.enrol_type_id=pol.enrol_type_id)AND (pol.policy_seq_id=pg.policy_seq_id)AND (mem.policy_group_seq_id=pg.policy_group_seq_id)AND ( pol.group_reg_seq_id=grp.group_reg_seq_id (+))AND (mem.tpa_enrollment_id=img.enrolment_id(+))";
	//private static final String strImageEnquiryList="SELECT * FROM(SELECT pol.policy_number,pg.tpa_enrollment_number,mem.tpa_enrollment_id,pg.employee_no,grp.group_name,mem.mem_name,img.image FROM  tpa_enr_policy pol,tpa_enr_policy_group pg,tpa_enr_policy_member mem,tpa_group_registration grp,image_data img WHERE (pol.policy_seq_id=pg.policy_seq_id)AND (mem.policy_group_seq_id=pg.policy_group_seq_id)AND ( pol.group_reg_seq_id=grp.group_reg_seq_id)AND (img.enrolment_id=mem.tpa_enrollment_id)AND MEM.MEMBER_SEQ_ID IN (SELECT MAX(MEM.MEMBER_SEQ_ID) FROM TPA_ENR_POLICY_MEMBER MEM";
	
	/**
     * This method returns the ArrayList, which contains the ImageEnquiryVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of ImageEnquiryVO'S object's which contains the details of the Image Enquiry
     * @exception throws TTKException
     */
	public ArrayList getImageEnquiryList(ArrayList alSearchObjects) throws TTKException{
		log.debug("Inside the getImageEnquiryList method of ImageEnquiryDAOImpl");
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery = strImageEnquiryList;
		
		ImageEnquiryVO imageEnquiryVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		if(alSearchObjects != null && alSearchObjects.size() > 0)
		{
			for(int i=0; i < alSearchObjects.size(); i++)
			{
				if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
				{
					if (((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
					{
						sbfDynamicQuery.append(" AND "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
					}//END OF IF
					else
					{	
						sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('%"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
					}//END OF ELSE
				}//end of if(!((SearchCriteria)alSearchCriteria.get(i)).getValue().equals(""))
			}//end of for()
		}//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
		sbfDynamicQuery = sbfDynamicQuery .append( "AND mem.date_of_exit>sysdate ORDER BY mem.tpa_enrollment_id)");
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
		
		try{
			conn = ReportResourceManager.getImageConnection();
			pStmt = conn.prepareStatement(strStaticQuery);
			rs = pStmt.executeQuery();
			
			if(rs != null){
				while (rs.next()) {
					imageEnquiryVO = new ImageEnquiryVO();
					imageEnquiryVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("policy_number")));
					imageEnquiryVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("tpa_enrollment_number")));
					imageEnquiryVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("tpa_enrollment_id")));
					imageEnquiryVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("employee_no")));
					imageEnquiryVO.setCorporateName(TTKCommon.checkNull(rs.getString("group_name")));
					imageEnquiryVO.setMemberName(TTKCommon.checkNull(rs.getString("mem_name")));
					imageEnquiryVO.setGroupId(TTKCommon.checkNull(rs.getString("GROUP_ID")));
					//imageEnquiryVO.setChoice_image(rs.getBlob("image"));
					if(rs.getBlob("image") !=null){
						imageEnquiryVO.setChoice_image(rs.getBlob("image"));
						}//END OF if(rs.getBlob("image") !=null)
					resultList.add(imageEnquiryVO);
				}//end of while
			}//end of if(rs != null)
			return (ArrayList)resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "imageList");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "imageList");
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
					log.error("Error while closing the Resultset in ImageEnquiryDAOImpl getImageEnquiryList()",sqlExp);
					throw new TTKException(sqlExp, "imageList");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ImageEnquiryDAOImpl getImageEnquiryList()",sqlExp);
						throw new TTKException(sqlExp, "imageList");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ImageEnquiryDAOImpl getImageEnquiryList()",sqlExp);
							throw new TTKException(sqlExp, "imageList");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "imageList");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getImageEnquiryList(ArrayList alSearchObjects)
}//end of ImageEnquiryDAOImpl implements Serializable, BaseDAO
