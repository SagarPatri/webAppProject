/**
 * @ (#) OfficeDAOImpl.java Nov 05, 2005
 * Project      : TTK HealthCare Services
 * File         : OfficeDAOImpl
 * Author       : Ramakrishna K M
 * Company      : Span Systems Corporation
 * Date Created : Nov 05, 2005
 *
 * @author       :  Ramakrishna K M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dao.impl.administration;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.OfficeVO;
import com.ttk.dto.common.SearchCriteria;

public class OfficeDAOImpl implements BaseDAO,Serializable {
	
	private static Logger log = Logger.getLogger(OfficeDAOImpl.class);
    
    private static final String strOfficeCodeList = "SELECT * FROM (SELECT A.INS_SEQ_ID, A.INS_COMP_CODE_NUMBER,A.INS_OFFICE_GENERAL_TYPE_ID , B.DESCRIPTION, DENSE_RANK() OVER ( ORDER BY #) Q FROM app.TPA_INS_INFO A JOIN app.TPA_GENERAL_CODE B ON ( A.INS_OFFICE_GENERAL_TYPE_ID = B.GENERAL_TYPE_ID ) ";//and prod_policy_seq_id=? and INS_COMP_CODE_NUMBER LIKE ? (ProductPolicy sequence id and Concatenation of Abbrevation code and inscompcodenumber is required as a join mandatorily)  
    
      private static final String strOfficeCodeListp="{CALL PROD_ADMIN_SELECT_COMPANY_CODE(?,?,?,?,?,?,?)}";
    
     
     
    /**
     * This method returns the ArrayList, which contains the OfficeVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of OfficeVO'S object's which contains the Office Code details
     * @exception throws TTKException
     */
      public ArrayList getOfficeCodeList(ArrayList alSearchObjects) throws TTKException {

      	Connection conn = null;
          //PreparedStatement pStmt = null;
          CallableStatement cStmt=null;  
      	  ResultSet rs = null;
          //String strStaticQuery = strOfficeCodeList;
          OfficeVO officeVO = null;
          Collection<Object> resultList = new ArrayList<Object>();
          try{
              conn = ResourceManager.getConnection();
              cStmt =conn.prepareCall(strOfficeCodeListp);
              cStmt.setLong(1,Long.parseLong((String)alSearchObjects.get(0)));
              cStmt.setString(2,(String)alSearchObjects.get(1) );
              cStmt.setString(3,(String)alSearchObjects.get(2) );
              cStmt.setString(4,(String)alSearchObjects.get(3) );
              cStmt.setString(5,(String)alSearchObjects.get(4) );
              cStmt.setString(6,(String)alSearchObjects.get(5) );
              cStmt.registerOutParameter(7,Types.OTHER);
              cStmt.execute();
              rs = (ResultSet)cStmt.getObject(7);
              if(rs != null){
                  while (rs.next()) {
                      officeVO = new OfficeVO();
                      
                      if(rs.getString("INS_SEQ_ID") != null){
                      	officeVO.setInsSeqId(new Long(rs.getLong("INS_SEQ_ID")));
                      }//end of if(rs.getString("INS_SEQ_ID") != null)
                          
                      officeVO.setOfficeCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
                      officeVO.setOfficeType(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                      resultList.add(officeVO);
                  }//end of while
              }//end of if(rs != null)
              return (ArrayList)resultList;
          }//end of try
          catch (SQLException sqlExp) 
          {
              throw new TTKException(sqlExp, "officecode");
          }//end of catch (SQLException sqlExp)
          catch (Exception exp) 
          {
              throw new TTKException(exp, "officecode");
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
  					log.error("Error while closing the Resultset in OfficeDAOImpl getOfficeCodeList()",sqlExp);
  					throw new TTKException(sqlExp, "officecode");
  				}//end of catch (SQLException sqlExp)
  				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
  				{
  					try
  					{
  						if (cStmt != null)	cStmt.close();
  					}//end of try
  					catch (SQLException sqlExp)
  					{
  						log.error("Error while closing the Statement in OfficeDAOImpl getOfficeCodeList()",sqlExp);
  						throw new TTKException(sqlExp, "officecode");
  					}//end of catch (SQLException sqlExp)
  					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
  					{
  						try
  						{
  							if(conn != null) conn.close();
  						}//end of try
  						catch (SQLException sqlExp)
  						{
  							log.error("Error while closing the Connection in OfficeDAOImpl getOfficeCodeList()",sqlExp);
  							throw new TTKException(sqlExp, "officecode");
  						}//end of catch (SQLException sqlExp)
  					}//end of finally Connection Close
  				}//end of finally Statement Close
  			}//end of try
  			catch (TTKException exp)
  			{
  				throw new TTKException(exp, "officecode");
  			}//end of catch (TTKException exp)
  			finally // Control will reach here in anycase set null to the objects 
  			{
  				rs = null;
  				cStmt = null;
  				conn = null;
  			}//end of finally
  		}//end of finally
      
      }
    public ArrayList getOfficeCodeListBackup(ArrayList alSearchObjects) throws TTKException {
    	Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        StringBuffer sbfDynamicQuery = new StringBuffer();
        String strStaticQuery = strOfficeCodeList;
        String strInsSeqId = "";
        String strOfficeCode = "";
        OfficeVO officeVO = null;
        Collection<Object> resultList = new ArrayList<Object>();
        if(alSearchObjects != null && alSearchObjects.size() > 0)
        {
            strInsSeqId = ((SearchCriteria)alSearchObjects.get(0)).getValue();
            strOfficeCode = ((SearchCriteria)alSearchObjects.get(1)).getValue();
            log.debug("strOfficeCode value is "+strOfficeCode);
            strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?",strInsSeqId);
            for(int i=1; i < alSearchObjects.size()-4; i++)
            {
                if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals("")){
                    sbfDynamicQuery.append(" WHERE UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%') ").append(" START WITH INS_SEQ_ID = "+strInsSeqId+" CONNECT BY PRIOR INS_SEQ_ID=INS_PARENT_SEQ_ID");
                }//end of if
                else{
                	sbfDynamicQuery.append(" START WITH INS_SEQ_ID = "+strInsSeqId+" CONNECT BY PRIOR INS_SEQ_ID=INS_PARENT_SEQ_ID");
                }//end of else
            }//end of for()
        }//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
        strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
        sbfDynamicQuery = sbfDynamicQuery .append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));  
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
        
       // System.out.println("strStaticQuery="+strStaticQuery);
        try{
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strStaticQuery);
            rs = pStmt.executeQuery();
            if(rs != null){
                while (rs.next()) {
                    officeVO = new OfficeVO();
                    
                    if(rs.getString("INS_SEQ_ID") != null){
                    	officeVO.setInsSeqId(new Long(rs.getLong("INS_SEQ_ID")));
                    }//end of if(rs.getString("INS_SEQ_ID") != null)
                        
                    officeVO.setOfficeCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
                    officeVO.setOfficeType(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                    resultList.add(officeVO);
                }//end of while
            }//end of if(rs != null)
            return (ArrayList)resultList;
        }//end of try
        catch (SQLException sqlExp) 
        {
            throw new TTKException(sqlExp, "officecode");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp) 
        {
            throw new TTKException(exp, "officecode");
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
					log.error("Error while closing the Resultset in OfficeDAOImpl getOfficeCodeList()",sqlExp);
					throw new TTKException(sqlExp, "officecode");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OfficeDAOImpl getOfficeCodeList()",sqlExp);
						throw new TTKException(sqlExp, "officecode");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OfficeDAOImpl getOfficeCodeList()",sqlExp);
							throw new TTKException(sqlExp, "officecode");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "officecode");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getOfficeCodeList(ArrayList alSearchObjects)
}//end of OfficeDAOImpl
