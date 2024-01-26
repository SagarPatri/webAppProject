/**
 * @ (#) ResourceManager.java Jul 29, 2005
 * Project      :
 * File         : ResourceManager.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Jul 29, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
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
public class ResourceManager {

    static String jndiPath = null;

    /**
     * Constructor for ResourceManager
     */
    public ResourceManager() {
        super();
    }

    public static void init(String path) {
        jndiPath = path;
    }
    /**
     * get a connection from the pool
     * @return Connection
     * @throws SQLException
     * @throws Exception
     */
    public synchronized static Connection getConnection() throws TTKException {

        Context context = null;
        DataSource ds = null;
        Connection con = null;

        try {
            context = (Context) new InitialContext();
            //ds = (DataSource) context.lookup(jndiPath);
			ds = (DataSource) context.lookup(TTKPropertiesReader.getPropertyValue("PGDataSourceName"));			
            con = ds.getConnection();
            con.setAutoCommit(false);
        } catch (NamingException ne) {
			ne.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
        }//end of (Exception e)
        return con;
    }//end of getConnection()
    /**
     * get a connection from the pool
     * @return Connection
     * @throws SQLException
     * @throws Exception
     */
    
    
    public synchronized static Connection getConnectionBackup() throws TTKException {

        Context context = null;
        DataSource ds = null;
        Connection con = null;

        try {
            context = (Context) new InitialContext();
            //ds = (DataSource) context.lookup(jndiPath);
			ds = (DataSource) context.lookup(TTKPropertiesReader.getPropertyValue("PGDataSourceName"));
            con = ds.getConnection();
        } catch (NamingException ne) {
			ne.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
        }//end of (Exception e)
        return con;
    }//end of getConnection()
    
    /**
     * get a connection from the pool
     * @return Connection
     * @throws SQLException
     * @throws Exception
     */
    public synchronized static Connection getPGConnection() throws TTKException {

        Context context = null;
        DataSource ds = null;
        Connection con = null;

        try {
            context = (Context) new InitialContext();
            //ds = (DataSource) context.lookup(jndiPath);
			ds = (DataSource) context.lookup(TTKPropertiesReader.getPropertyValue("PGDataSourceName"));			
            con = ds.getConnection();
            con.setAutoCommit(false);
        } catch (NamingException ne) {
			ne.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
        }//end of (Exception e)
        return con;
    }//end of getPGConnection()
    
    /** This connection is used in Report Server for generating Reports connected to Report Database
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
            //ds = (DataSource) context.lookup(jndiPath);
			ds = (DataSource) context.lookup(TTKPropertiesReader.getPropertyValue("UATReportDataSourceName"));
            con = ds.getConnection();
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
    public synchronized static Connection getWebserviceConnection() throws TTKException {

        Context context = null;
        DataSource ds = null;
        Connection con = null;

        try {
            context = (Context) new InitialContext();
            ds = (DataSource) context.lookup(TTKPropertiesReader.getPropertyValue("DataSourceNameWebsevices"));
            con = ds.getConnection();
        } catch (NamingException ne) {
			ne.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
        }//end of (Exception e)
        return con;
    }//end of getWebserviceConnection()


    //Added as per KOC BAJAJ ALLIANZ WebService Intergration
    /**
     * get a connection from the pool
     * @return Connection
     * @throws SQLException
     * @throws Exception
     */
    public synchronized static Connection getMISConnection() throws TTKException {

        Context context = null;
        DataSource ds = null;
        Connection con = null;

        try {
            context = (Context) new InitialContext();
            ds = (DataSource) context.lookup(TTKPropertiesReader.getPropertyValue("DataSourceNameMIS"));
            con = ds.getConnection();
        } catch (NamingException ne) {
			ne.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
        }//end of (Exception e)
        return con;
    }//end of getMISConnection()
    
        
    /**
     * get a connection from the pool
     * @return Connection
     * @throws SQLException
     * @throws Exception
     */
    public synchronized static Connection getEcardConnection() throws TTKException {

        Context context = null;
        DataSource ds = null;
        Connection con = null;
        try {
            context = (Context) new InitialContext();
            ds = (DataSource) context.lookup(TTKPropertiesReader.getPropertyValue("DataSourceNameEcard"));
            con = ds.getConnection();
        } catch (NamingException ne) {
			ne.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
        }//end of (Exception e)
        return con;
    }//end of getEcardConnection()

    /**
     * get a connection from the URL
     * @return Connection
     * @throws SQLException
     * @throws Exception
     */
    public synchronized static Connection getTestConnection() throws TTKException {

        Connection conn = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //conn=  DriverManager.getConnection("jdbc:oracle:thin:@172.16.1.145:1521:TTKPROD","appln","appln");
            //conn=  DriverManager.getConnection("jdbc:oracle:thin:@172.16.0.115:1521:TTKPROD","appln","appln");
            conn=  DriverManager.getConnection("jdbc:oracle:thin:@10.10.206.5:1521:TTKPROD","appln","appln");
        }  catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
        }//end of catch(SQLException sqlExp)
        catch (ClassNotFoundException e) {
          e.printStackTrace();
        }//end of catch (ClassNotFoundException e)
        return conn;
    }//end of getTestConnection()

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

}//end of ResourceManager