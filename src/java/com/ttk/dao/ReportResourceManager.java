/**
  * @ (#) ReportResourceManager.java May 29, 2007
  * Project      : TTK HealthCare Services
  * File         : ReportResourceManager.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : 
  *
  * @author       :  Ajay Kumar
  * Modified by   :
  * Modified date :
  * Reason        :
  */

package com.ttk.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;

/**
 * Class to get connection
 */
public class ReportResourceManager {
	
	static String jndiPath = null;

    /**
     * Constructor for ResourceManager
     */
    public ReportResourceManager() {
        super();
    }//end of ReportResourceManager()

    public static void init(String path) {
        jndiPath = path;
    }//end of init(String path)

    /**
     * get a connection from the pool
     * @return Connection
     * @throws SQLException
     * @throws Exception
     */
    public synchronized static Connection getReportConnection() throws TTKException {

    	Context context = null;
    	DataSource ds = null;
    	Connection con = null;
    	
    	try {
    		context = (Context) new InitialContext();
    		//ds = (DataSource) context.lookup(TTKPropertiesReader.getPropertyValue("ReportDataSourceName"));
			ds = (DataSource) context.lookup(TTKPropertiesReader.getPropertyValue("PGDataSourceName"));			
            con = ds.getConnection();
            con.setAutoCommit(false);
    	} catch (NamingException ne) {
    		ne.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}//end of (Exception e)
    	return con;
    }//end of getReportConnection()
    /**
     * get a connection from the pool
     * @return Connection
     * @throws SQLException
     * @throws Exception
     */
    public synchronized static Connection getImageConnection() throws TTKException {

    	Context context = null;
    	DataSource ds = null;
    	Connection con = null;
    	
    	try {
    		context = (Context) new InitialContext();
    		ds = (DataSource) context.lookup(TTKPropertiesReader.getPropertyValue("ImageDataSourceName"));
    		con = ds.getConnection();
    	} catch (NamingException ne) {
    		ne.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}//end of (Exception e)
    	return con;
    }//end of getImageConnection()
    /**
     * closes Connection
     * @param conn Connection
     */
    public synchronized static void close(Connection conn) throws TTKException {
        try {
            if (conn != null)
            {
                conn.close();
            }//end of if (conn != null)
        } catch (SQLException sql) {
            sql.printStackTrace();
            throw new TTKException();
        }//end of catch (SQLException sql)
    }//end of close(Connection conn)
    /**
     * sets auto commit for connection to true or false
     * @param conn Connection
     * @param bFlag boolean
     * @throws TTKException
     */
    public synchronized static void setAutoCommit(Connection conn, boolean bFlag) throws TTKException {
        try {
            if (conn != null)
            {
                conn.setAutoCommit(bFlag);
            }//end of if (conn != null)
        } catch (SQLException sql) {
            sql.printStackTrace();
        }//end of catch (SQLException sql)
    }//end of setAutoCommit(Connection conn, boolean bFlag)

    /**
     * does a commit
     * @param conn Connection
     * @throws TTKException
     */
    public synchronized static void Commit(Connection conn) throws TTKException {
        try {
            if (conn != null)
            {
                conn.commit();
            }//end of if (conn != null)
        } catch (SQLException sql) {
			sql.printStackTrace();
        }//end of catch (SQLException sql)
    }//end of Commit(Connection conn)

    /**
     * does a rollback
     * @param conn Connection
     * @throws TTKException
     */
    public synchronized static void RollBack(Connection conn) throws TTKException {
        try {
            if (conn != null)
            {
                conn.rollback();
            }//end of if (conn != null)
        } catch (SQLException sql) {
			sql.printStackTrace();
        }//end of catch (SQLException sql)
    }//end of RollBack(Connection conn)
}//end of ReportResourceManager
