/**
 * @ (#) TTKCommon.java 12th Jul 2005
 * Project      :
 * File         : TTKCommon.java
 * Author       :
 * Company      :
 * Date Created : 12th Jul 2005
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common;

import java.io.BufferedReader;
import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
//import java.io.InputStreamReader;
import java.io.PrintWriter;
//import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.net.InetAddress;
import java.text.DateFormat;
import java.text.DecimalFormat;
//import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
//import java.util.Locale;
import java.util.Map;
import java.util.Properties;
//import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
//import java.util.jar.JarEntry;
//import java.util.jar.JarInputStream;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
//import org.dom4j.Node;
//import org.dom4j.XPath;
import org.dom4j.io.DOMReader;

import com.ttk.action.table.TableData;
import com.ttk.action.tree.TreeData;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.common.security.SecurityProfile;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import com.ttk.dto.usermanagement.UserSession;
/**
 * This class has the common methods usefull across the project
 *
 */
public class TTKCommon
{
    private static Logger log = Logger.getLogger( TTKCommon.class );
//    private static String strVerbose = null;
//    private static boolean Verbose=false;
//    private static File flLogFile=null;
//    private static FileOutputStream fos=null;
    private static Map<Object, Object> reportsMap = null;
//    private static File flDBLogFile=null;
//    private static FileOutputStream fosDB=null;

    /*****************************************************
     Method : getHtmlString(String AsciiString)
     Added by: Anilkumar
     Date: 19th November 2002

     Input: String containing Ascii characters
     Output: String which can be displayed on HTML

     ******************************************************/
    public static String getHtmlString(String AsciiString)
    {
        AsciiString = checkNull(AsciiString);
        try{

            if(AsciiString.length() != 0)
            {
                char[] ac = AsciiString.toCharArray();
                int iValue;
                StringBuffer sb = new StringBuffer();

                for( int ndx = 0; ndx < ac.length; ndx++ )
                {
                    iValue = ac[ndx];
                    if( (iValue < 48 && iValue > 0 )|| (iValue > 57 &&  iValue < 65) || (iValue > 126 &&  iValue < 260) )
                    {
                        sb.append("&#"+ Integer.toString( iValue )+";");
                    }//end of if( (iValue < 48 && iValue > 0 )|| (iValue > 57 &&  iValue < 65) || (iValue > 126 &&  iValue < 260) )
                    else
                    {
                        sb.append(ac[ndx]);
                    }//end of else
                }//end of for( int ndx = 0; ndx < ac.length; ndx++ )
                return sb.toString();
            }//end of if(AsciiString.length() != 0)
            else
            {
                return AsciiString;
            }//end of else
        }//end of try
        catch(Exception exp)
        {
            log.error("Inside getHtmlString.....Throwing IO Exception...");
        }
        return AsciiString;
    }//end of getHtmlString(String AsciiString)

    /*public static void writeDBLog(String strConnection)
    {
        try
        {
            if(flDBLogFile==null)
            {
                flDBLogFile = new File("ttkdblog.log");
                fosDB=  new FileOutputStream(flDBLogFile);
            }//end of if(flDBLogFile==null)
            if(Verbose)
            {
                strConnection+="\n";

                fosDB.write(strConnection.getBytes());
                //fosDB.write("\n".getBytes());
                fosDB.flush();
            }//end of if(Verbose)
        }//end of try
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
*/
    /**
     * This method converts the Date from mm/dd/yyyy format to dd/mmm/yyyy
     * as it is required for Sql insert
     *
     * @param strDate String in mm/dd/yyyy format
     * @return String in dd/mmm/yyyy format
     */
    public static String getOracleDate(String strDate)
    {
        if(strDate != null && strDate.trim().length()>0)
        {
            String[] strMonthsName = {"","JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
            String strSQLDate = strDate.substring(3, 5)+"-"+strMonthsName[Integer.parseInt(strDate.substring(0, 2))]+"-"+strDate.substring(6);
            return strSQLDate;
        }//end of if(strDate != null && strDate.trim().length()>0)
        return strDate;
    }//getOracleDate(String strDate)

    /**
     * This method returns the Date Object by appending the Date & Time & MeriDian
     * @param strDate The String Containing the date value In Format DD//MM/YY
     * @param strTime The String Containing the Time value In Format HH:mm
     * @param strMeridian The String Containing The Meridian Value AM/PM
     * @return The Date In the Format DD/MM/YY HH:mm AM
     */
    public static Date getOracleDateWithTime(String strDate,String strTime,String strMeridian)
    {
        DateFormat formatter = null;
        String strCompleteDate = null;
        Date date = null;
        try{
            if(strDate != null && strDate.trim().length()>0)
            {
                strCompleteDate = strDate ;
                formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                if(strTime != null && strTime.trim().length()>0)
                {
                    strCompleteDate = strDate+" "+strTime;
                }//end of if(strTime != null && strTime.trim().length()>0)
                else
                {
                    formatter = new SimpleDateFormat("dd/MM/yyyy");
                    date = (Date)formatter.parse(strCompleteDate);
                    return date;
                }//end of else
                if (strMeridian != null && strMeridian.trim().length()>0)
                {
                    strCompleteDate =  strCompleteDate+" "+strMeridian;
                }//end of if (strMeridian != null && strMeridian.trim().length()>0)
                date = (Date)formatter.parse(strCompleteDate);
                return date;
            }//end of if(strDate != null && strDate.trim().length()>0)
        }//end of try
        catch (ParseException parserException)
        {
            TTKCommon.logStackTrace(parserException);
        }//end of Catach
        return date;
    }//getOracleDateWithTime(String strDate,String strTime,String strMeridian)

    /**
     * This method creates the initial context , which is required to look up
     * remote objects.
     *
     * @return Context Initial context object
     * @throws Namingexception if the unable to look up Context Factory
     */
    public static Context getInitialContext()
    throws NamingException
    {
        Properties p = new Properties();
        try
        {
//            String strLocalHostIPAddress = InetAddress.getLocalHost().getHostAddress();
//            String strAdminIPAddress = TTKPropertiesReader.getPropertyValue("WLAdminIPAddress");
//            String strAdminPort = TTKPropertiesReader.getPropertyValue("WLAdminPort");
            String url = "";
//          if(strLocalHostIPAddress.equalsIgnoreCase(strAdminIPAddress))
//          {
//          url = "t3://"+strAdminIPAddress+":"+strAdminPort;
//          }//END if request from admin
//          else
//          {
            url = "t3://"+TTKPropertiesReader.getPropertyValue("WLIPAddress")+":"+TTKPropertiesReader.getPropertyValue("WLPort");
//          }
            log.debug("URL is  :    "+url);
            p.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            p.put(Context.PROVIDER_URL, url);
        }
        catch(Exception exp)
        {
            TTKCommon.logStackTrace(exp);
        }
        return new InitialContext(p);
    }//End of  getInitialContext()

    /**
     * This method creates the initial context , which is required to look up
     * remote objects.
     *
     * @return Context Initial context object
     * @throws Namingexception if the unable to look up Context Factory
     */
    public static Context getJMSInitialContext()
    throws NamingException
    {
        Properties prop = new Properties();
        try
        {
            prop.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
            prop.put(Context.URL_PKG_PREFIXES,"org.jnp.interfaces");
            prop.put(Context.PROVIDER_URL,"jnp://localhost:1099/");
        }
        catch(Exception exp)
        {
            TTKCommon.logStackTrace(exp);
        }
        return new InitialContext(prop);
    }//End of  getJMSInitialContext()

    /**
     * This method creates the initial context , which is required to look up
     * remote objects.
     *
     * @return Context Initial context object
     * @throws Namingexception if the unable to look up Context Factory
     */
    /*public static Context getAdminInitialContext()
    throws NamingException
    {
        Properties p = new Properties();
        String strIpAddress = TTKPropertiesReader.getPropertyValue("WLAdminIPAddress");
        if(strIpAddress==null)
        {
            strIpAddress = TTKPropertiesReader.getPropertyValue("WLIPAddress");
        }//end of if(strIpAddress==null)
        String strProxyPort = TTKPropertiesReader.getPropertyValue("WLAdminPort");
        if(strProxyPort==null)
        {
            strProxyPort = TTKPropertiesReader.getPropertyValue("WLPort");
        }//end of if(strProxyPort==null)
        String url = "t3://"+strIpAddress+":"+strProxyPort;
        p.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        p.put(Context.PROVIDER_URL, url);
        return new InitialContext(p);
    }//End of  getInitialContext()
*/

    /**
     * This method returns TableData object retreived from session.
     * If null is retruned from session, creates a new one
     *
     * @param HttpServletRequest request
     * @return TableData the TableData object
     */
    public static TableData getTableData(HttpServletRequest request)
    {
        TableData tableConfig = null;
        if((request.getSession()).getAttribute("tableData") == null)
        {
            tableConfig = new TableData();
        }//end of if((request.getSession()).getAttribute("tableData") == null)
        else
        {
            tableConfig = (TableData)(request.getSession()).getAttribute("tableData");
        }//end of else
        return tableConfig;
    }//End of getTableConfig(HttpServletRequest request)
    
    
    /**
     * This method returns TreeData object retreived from session.
     * If null is retruned from session, creates a new one
     *
     * @param HttpServletRequest request
     * @return TreeData the TreeData object
     */
    public static TreeData getTreeData(HttpServletRequest request)
    {
        TreeData treeConfig = null;
        if((request.getSession()).getAttribute("treeData") == null)
        {
            treeConfig = new TreeData();
        }//end of if((request.getSession()).getAttribute("treeData") == null)
        else
        {
            treeConfig = (TreeData)(request.getSession()).getAttribute("treeData");
        }//end of else

        return treeConfig;
    }//End of getTreeConfig(HttpServletRequest request)

    /**
     * This Method processes the string array passed and
     * builds a comma separated string of the strings in given string array.
     *
     * @param String[] String array to be processed
     * @return strRetrunString String the string in javascript array form
     */
    /*public static String getJSArray(String[] strArray)
    {
        String strRetrunString = "";
        String suffix = "";
        for(int i = 0;i<strArray.length; i++)
        {
            String strTemp = (strArray[i] == null)?"":strArray[i];
            //Avoid ',' to the last element
            if(i<strArray.length-1)
            {
                suffix = ",";
            }//end of if(i<strArray.length-1)
            else
            {
                suffix = "";
            }//end of else
            strRetrunString += "\""+replaceDoubleQotsWithSlash(strTemp)+"\""+suffix;
        }//End of for(int i = 0;i<strArray.length; i++)
        return strRetrunString;
    }//end of getJSArray()
*/
    /**
     * This Method processes the comma seperated string passed and
     * builds a String array
     *
     * @param String comma seperated string to be processed
     * @return strRetrunStringArr String[]
     */
    /*public static String[] getStringArray(String strCommaSepString)
    {
        ArrayList<Object> alRetrunAL = new ArrayList<Object>();
        StringTokenizer st = new StringTokenizer(strCommaSepString,",");
        while (st.hasMoreTokens())
        {
            alRetrunAL.add(st.nextToken());
        }//End of while (st.hasMoreTokens())
        //Convert the array list to string array
        String[] strRetrunStringArr= {};
        strRetrunStringArr =(String[])alRetrunAL.toArray(strRetrunStringArr);
        return strRetrunStringArr;
    }//end of getJSArray()
*/

    /**
     * This method returns the stored procedure name , from the given
     * String identifier. The method does the reflection on the
     * SPManager and then returns the stored procedure name.
     *
     * @param strSPIdentifier String, identifier of the stored procedure
     * @return String, Stored procedure name after look up on SPManager
     * @exception com.ttk.ui.error.TTKException
     */
    /*public static String getStoredProcedure(String strSPIdentifier)throws TTKException
    {
        String strStoredProcedure = "";
//        TTKException expTTK = new TTKException();
        try
        {
            Class c1 = Class.forName("com.ttk.data.dbmanager.SPManager");
            Field f1 = c1.getField(strSPIdentifier);
            strStoredProcedure = f1.get("").toString();
        }//try
        catch(ClassNotFoundException expClassNotFound)
        {
            log.error("Exception in Building Error Object");
            TTKCommon.logStackTrace(expClassNotFound);
            //expTTK.setDescription("ClassNotFoundException");
        } //catch(ClassNotFoundException expClassNotFound)
        catch(NoSuchFieldException expNoSuchFieldException)
        {
            log.error("Exception in Building Error Object");
            TTKCommon.logStackTrace(expNoSuchFieldException);
            //expTTK.setDescription("NoSuchFieldException");
        } //catch(NoSuchFieldException expNoSuchFieldException)
        catch(IllegalAccessException expIllegalAccessException)
        {
            log.error("Exception in Building Error Object");
            TTKCommon.logStackTrace(expIllegalAccessException);
            //expTTK.setDescription("IllegalAccessException");
        } //catch(IllegalAccessException expIllegalAccessException)
        return strStoredProcedure;
    }//getStoredProcedure(String strSPIdentifier)
*/
    /**
     * This method replaces the double quotes with &quots; for display method
     *
     * @param sValue String
     * @return String

     Method : replaceDoubleQots(String sValue)
     Modified by: Anilkumar
     Date: 19th November 2002

     Input: String containing Ascii characters
     Output: String which can be displayed on HTML

     */
    /*public static String replaceDoubleQots(String sValue)
    {
        return getHtmlString(sValue); //added by Anil

        if(sValue==null)
         return "";
         else
         {
         sValue = sValue.trim();
         String sTemp = "";
         int i=0;
         while(sValue.length() > 0 && i !=-1)
         {
         i =  sValue.indexOf('"');
         if(i!=-1)
         {
         sTemp+= sValue.substring(0,i)+"&quot;";
         sValue = sValue.substring(i+1,sValue.length());
         }
         else
         {
         sTemp+= sValue;
         }

         }
         return sTemp;

         }

    }//end of replaceDoubleQots(String sValue)
*/
    /**
     * This method replaces the double quotes with '\"' for display method
     *
     * @param sValue String
     * @return String
     */
    /*public static String replaceDoubleQotsWithSlash(String sValue)
    {
        if(sValue==null)
        {
            return "";
        }//end of if(sValue==null)
        else
        {
            sValue = sValue.trim();
            String sTemp = "";
            int i=0;
            while(sValue.length() > 0 && i !=-1)
            {
                i =  sValue.indexOf('"');
                if(i!=-1)
                {
                    sTemp+= sValue.substring(0,i)+"\\\"";
                    sValue = sValue.substring(i+1,sValue.length());
                }//end of if(i!=-1)
                else
                {
                    sTemp+= sValue;
                }//end of else
            }//end of
            return sTemp;
        }//end of else

    }//end of replaceDoubleQotsWithSlash(String sValue)
*/
    /**
     * This method replaces the single quotes with '' for display method
     *
     * @param sValue String
     * @return String
     */
    public static String replaceSingleQots(String sValue)
    {
        if(sValue==null)
        {
            return "";
        }//end of if(sValue==null)
        else
        {
            sValue = sValue.trim();
            String sTemp = "";
            int i=0;
            while(sValue.length() > 0 && i !=-1)
            {
                i =  sValue.indexOf('\'');
                if(i!=-1)
                {
                    sTemp+= sValue.substring(0,i)+"''";
                    sValue = sValue.substring(i+1,sValue.length());
                }//if(i!=-1)
                else
                {
                    sTemp+= sValue;
                }//end of else
            }//end of while(sValue.length() > 0 && i !=-1)
            return sTemp;
        }//end of else
    }//end of replaceDoubleQotsWithSlash(String sValue)

    /**
     * This method checks it the string contains null
     *
     * @param strIn String
     * @return String
     */
    public static String checkNull(String strIn){
        return strIn == null?"":strIn;
    }//checkNull(String strIn)
    /**
     * This method convert sql Date as String with given format 
     * @param dateformat valid string format
     * @param date sql date
     * @return String
     */
public static String convertDateAsString(String dateformat,java.sql.Date date){
	if(date!=null&&dateformat!=null) return new SimpleDateFormat(dateformat).format(new Date(date.getTime()));
	else return "";
}
    /**
     * This method checks it the Object contains null
     *
     * @param oIn String
     * @return Object
     */
    public static Object checkNull(Object oIn){
    	 return oIn == null?"":oIn;
    }//checkNull(Object oIn)
    /**
     * This method checks it the Object contains null
     *
     * @param oIn String
     * @return Object
     */
   
    

    /**
     * This method returns the instance of SecurityProfile object from the session
     *
     * @param   request HttpServletRequest
     * @return  SecurityProfile object contains security inforamtion
     */
    //public static SecurityProfile getSecurityProfile(HttpServletRequest request)
    //{
    /*
     if((request.getSession()).getAttribute("securityProfileVO") == null)
     return null;
     else
     return ((SecurityProfile)(request.getSession()).getAttribute("securityProfileVO"));
     */
    //return null;
    //}//end of getSecurityProfile(HTTPServletRequest request)

    /**
     * This method returns the instance of UserSession object from the session
     *
     * @param   request HttpServletRequest
     * @return  UserSession object information of the person who loged in
     */
    public static UserSession getUserSession(HttpServletRequest request)
    {
        if((request.getSession()).getAttribute("userSession") == null)
        {
            return null;
        }//end of if((request.getSession()).getAttribute("userSession") == null)
        else
        {
            return ((UserSession)(request.getSession()).getAttribute("userSession"));
        }//end of else
    }//end of getSecurityProfile(HTTPServletRequest request)

    /**
     * This method returns the servers system date in 'MM/DD/YYYY' format.
     *
     * @return  String System date
     */
    public static String getServerDate()
    {
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdt;
        sdt = new java.text.SimpleDateFormat("dd_MM_yyyy");
        String strDate = sdt.format(dt);
        return strDate;
    }//end of getServerDate()
    
    /**
     * This method returns the servers system date in 'MM/DD/YYYY' format.
     *
     * @return  String System date
     */
    public static String getServerDateNewFormat()
    {
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdt;
        sdt = new java.text.SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdt.format(dt);
        return strDate;
    }//end of getServerDate()

    /**
     * This method returns the date as String
     * with system date added/subtracted with days in 'DD/MM/YYYY' format.
     * @parameters days integer(positive if to be added / negative if to be subtract
     * @return  String System date
     */
    public static String getAddedDate(int days)
    {
        Calendar calObj = Calendar.getInstance();
        calObj.add(Calendar.DATE,days);
        java.text.SimpleDateFormat sdt;
        sdt = new java.text.SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdt.format(calObj.getTime());
        return strDate;
    }//end of getAddedDate(int days)

    /**
     * This method returns the servers system date.
     *
     * @return  Date System date
     */
    public static Date getDate()  {
        return new java.util.Date();
    }//end of getServerDate()

    /**
     * Returns the required number of Filler String
     * to be used as filler
     *
     * @param strFiller String the String to be filled with
     * @param iLength int Length of Filler
     * @return String Filler of required length
     */
/*    public static String getFiller( String strFiller, int iLength )
    {
        String strRetFiller = "";
        int i = 0;
        while( i < iLength )
        {
            strRetFiller += strFiller;
            i++;
        }//end of while( i < iLength )
        return strRetFiller;
    }//end of getFiller( String strFiller, int iLength )
*/
    /**
     * Returns the formatted string with required number of spaces post fixed
     *
     * @param strData String Data to be formatted
     * @param iLength int Required length of the String
     * @return String Formatted String
     */
    /*public static String getFormattedString( String strData, int iLength )
    {
        if( strData == null )
        {
            return getFiller( " ", iLength );
        }//end of if( strData == null )
        if( strData.length() > iLength )
        {
            return strData.substring( 0, iLength );
        }//end of if( strData.length() > iLength )
        else if ( strData.length() < iLength )
        {
            return strData + getFiller( " ", iLength - strData.length() );
        }//end of else if ( strData.length() < iLength )

        return strData;
    }//end of getFormattedString( String strData, int iLength )
*/
    /**
     * Returns the formatted string with required number of spaces post fixed
     *
     * @param strData String Data to be formatted
     * @param strFormatString
     * @param iLength int Required length of the String
     * @return String Formatted String
     */
    /*public static String getFormattedString( String strData, String strFormatString, int iLength )
    {
        if( strData == null )
        {
            return getFiller( strFormatString, iLength );
        }//end of if( strData == null )
        else if( strData.length() > iLength )
        {
            return strData.substring( 0, iLength );
        }//end of else if( strData.length() > iLength )
        else if ( strData.length() < iLength )
        {
            if( strFormatString.equals("0") )
            {
                return getFiller( strFormatString, iLength - strData.length() ) + strData ;
            }//end of if( strFormatString.equals("0") )
            else
            {
                return strData + getFiller( strFormatString, iLength - strData.length() );
            }//end of else
        }//end of else if ( strData.length() < iLength )
        return strData;
    }//end of getFormattedString( String strData, String strFormatString, int iLength )
*/
    /**
     * This method returns true if the second date(passed in 'MM/DD/YYYY' format)
     * is greater than first date (passed in 'MM/DD/YYYY' format)
     * @param strFirstDate String in 'MM/DD/YYYY' format
     * @param strSecondDate in 'MM/DD/YYYY' format
     *
     * @return  boolean true if second date is greater than first date
     */
    /*public static boolean compareDates(String strFirstDate, String strSecondDate)
    {
        boolean bCompare = false;
        try
        {
            java.util.Date dtFirstDate = null;
            java.util.Date dtSecondDate = null;
            java.text.SimpleDateFormat sdtFormat;
            sdtFormat = new java.text.SimpleDateFormat("MM/dd/yyyy");
            dtFirstDate = sdtFormat.parse(strFirstDate);
            dtSecondDate = sdtFormat.parse(strSecondDate);
            int iCompare = dtFirstDate.compareTo(dtSecondDate);
            if(iCompare >=0)
            {
                bCompare = false;
            }//end of if(iCompare >=0)
            else if(iCompare < 0)
            {
                bCompare = true;
            }//end of else if(iCompare < 0)
        }//end of try
        catch(java.text.ParseException pexp)
        {
            TTKCommon.logStackTrace(pexp);
        }//end of catch(java.text.ParseException pexp)
        return bCompare;
    }//end of getServerDate()
*/


    /**
     * This method logs the exception in ttkstackTrace.log
     * @param exp Exception
     *
     */
    public static synchronized  boolean logStackTrace(Exception exp)
    {
        try
        {
            exp.printStackTrace();
            PrintWriter pw;
            pw = new PrintWriter( new FileWriter("ttkstackTrace.log", true) );
            exp.printStackTrace( pw );
            pw.write("******************************************************************<BR>");
            pw.flush();
            pw.close();
        }//end of try
        catch(Exception ex)
        {
           ex.printStackTrace();
        }//end of catch(Exception ex)
        return true;

    }//end of logStackTrace()

    /**
     * This method returns the BufferedReader object of ttkstackTrace.log
     * @param strOperation String
     *
     */
    /*public static BufferedReader readStackTraceLog(String strOperation)
    {
        BufferedReader in = null;
        try
        {
            if(strOperation.equalsIgnoreCase("Clear"))
            {
                PrintWriter pw;
                pw = new PrintWriter( new FileWriter("ttkstackTrace.log"));
                Date dt = new Date();
                pw.write(" <p><font color=\"#FF0000\"><b>The Trace Log was last cleared at " + dt +" </b></font></p>");
                pw.flush();
                pw.close();
            }//end of if(strOperation.equalsIgnoreCase("Clear"))
            in = new BufferedReader(new FileReader("ttkstackTrace.log"));
        }//end of try
        catch(Exception ex)
        {
            TTKCommon.logStackTrace(ex);
        }//end of catch(Exception ex)
        return in;
    }//end of readStackTraceLog()
*/

    /**
     * This method returns the BufferedReader object of ttklog.log
     * @param strOperation String
     *
     */
    public static BufferedReader readTTKLog(String strOperation)
    {
        BufferedReader in = null;
        try
        {
            if(strOperation.equalsIgnoreCase("Clear"))
            {
                PrintWriter pw;
                pw = new PrintWriter( new FileWriter("ttklog.log"));
                Date dt = new Date();
                pw.write(" <p><font color=\"#FF0000\"><b>The Vidal Health Log was last cleared at " + dt +" </b></font></p>");
                pw.flush();
                pw.close();
            }//end of if(strOperation.equalsIgnoreCase("Clear"))
            in = new BufferedReader(new FileReader("ttklog.log"));
        }//end of try
        catch(Exception ex)
        {
            TTKCommon.logStackTrace(ex);
        }//end of catch(Exception ex)
        return in;
    }//end of readTTKLog()

    /**
     * This method returns the BufferedReader object of eru.properties
     * or eruQuery.xml or struts-config.xml based on the operation
     * @param strOperation String
     *
     */
    /*public static BufferedReader readTTKProperties(String strOperation)
    {
        BufferedReader in = null;
        try
        {
            if(strOperation.equalsIgnoreCase("properties"))
            {
                in = new BufferedReader(new FileReader("ttk.properties"));
            }//end of if(strOperation.equalsIgnoreCase("properties"))
            else if(strOperation.equalsIgnoreCase("strutsconfig"))
            {
                JarInputStream jarInStream = new JarInputStream(new FileInputStream("ttkweb.war"));
                JarEntry entry = null;
                while((entry = jarInStream.getNextJarEntry()) != null)
                {
                    if("WEB-INF/struts-config.xml".equals(entry.getName()))
                        break;
                }//end of while((entry = jarInStream.getNextJarEntry()) != null)
                if(entry != null)
                {
                    in = new BufferedReader(new InputStreamReader(jarInStream));
                    //jarInStream.close();
                }//end of if(entry != null)
            }//end of else if(strOperation.equalsIgnoreCase("strutsconfig"))
            else if(strOperation.equalsIgnoreCase("queryobject"))
            {
                in = new BufferedReader(new FileReader("ttkQuery.xml"));
            }//end of else if(strOperation.equalsIgnoreCase("queryobject"))
        }//end of try
        catch(Exception ex)
        {
            TTKCommon.logStackTrace(ex);
        }//end of catch(Exception ex)
        return in;
    }//end of readTTKProperties()
*/
    /**
     * This method returns the BufferedReader object of eru.properties
     * or eruQuery.xml or struts-config.xml based on the operation
     * @param strOperation String
     *
     */
    /*public static JarInputStream readJarFile(String strJarFileName)
    {
        JarInputStream in = null;
        try
        {
            in = new JarInputStream(new FileInputStream(strJarFileName));
        }//end of try
        catch(Exception ex)
        {
            TTKCommon.logStackTrace(ex);
        }//end of catch(Exception ex)
        return in;
    }//end of readJarFile()
*/
    public static String replaceInString(String str, String pattern, String replace) {
        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();

        while ((e = str.indexOf(pattern, s)) >= 0) {
            str.substring(s, e);
            result.append(str.substring(s, e));
            result.append(replace);
            s = e+pattern.length();
        }//end of while ((e = str.indexOf(pattern, s)) >= 0)
        result.append(str.substring(s));
        return result.toString();
    }//end of replaceInString(String str, String pattern, String replace)


    /*public static boolean validateDate( String mDate )
    {
        mDate = TTKCommon.getFormattedDate(mDate);

        String mMonth = "";
        String mDay = "";
        String mYear = "";

        int mnthInt = 0;
        int dayInt = 0;
        int yearInt = 0;

        try
        {
            int firstDgt = Integer.parseInt( mDate.substring( 0, 1 ) );
            log.debug("firstDgt :"+firstDgt);
        }//end of try
        catch( Exception e )
        {
            return false;
        }//end of catch( Exception e )

        StringTokenizer mST = new StringTokenizer( mDate, "/" );

        if ( mST.hasMoreTokens() )
        {
            mMonth = mST.nextToken();
        }//end of if ( mST.hasMoreTokens() )
        else
        {
            return false;
        }//end of else

        if ( mST.hasMoreTokens() )
        {
            mDay = mST.nextToken();
        }//end of if ( mST.hasMoreTokens() )
        else
        {
            return false;
        }//end of else

        if ( mST.hasMoreTokens() )
        {
            mYear = mST.nextToken();
        }//end of if ( mST.hasMoreTokens() )
        else
        {
            return false;
        }//end of else

        if ( mMonth.length() != 2 )
        {
            return false;
        }//end of if ( mMonth.length() != 2 )
        else
        {
            try
            {
                mnthInt = Integer.parseInt( mMonth );
            }//end of try
            catch ( Exception e )
            {
                return false;
            }//end of catch ( Exception e )
            if ( mnthInt < 1 || mnthInt > 12 )
            {
                return false;
            }//end of if ( mnthInt < 1 || mnthInt > 12 )
        }//end of else

        if ( mDay.length() != 2 )
        {
            return false;
        }//end of if ( mDay.length() != 2 )
        else
        {
            try
            {
                dayInt = Integer.parseInt( mDay );
            }//end of try
            catch ( Exception e )
            {
                return false;
            }//end of catch ( Exception e )
        }//end of else
        if ( dayInt > 31 || dayInt < 1 )
        {
            return false;
        }//end of if ( dayInt > 31 || dayInt < 1 )

        if ( mYear.length() != 4 )
        {
            return false;
        }//end of if ( mYear.length() != 4 )
        else
        {
            try
            {
                yearInt = Integer.parseInt( mYear );
            }//end of try
            catch ( Exception e )
            {
                return false;
            }//end of catch ( Exception e )
        }//end of else
        if ( mnthInt == 1 || mnthInt == 3 || mnthInt == 5 || mnthInt == 7 || mnthInt == 8 || mnthInt == 10 || mnthInt == 12 )
        {
            if ( dayInt > 31 || dayInt < 1 )
                return false;
        }//end of if ( mnthInt == 1 || mnthInt == 3 || mnthInt == 5 || mnthInt == 7 || mnthInt == 8 || mnthInt == 10 || mnthInt == 12 )
        if ( mnthInt == 4 || mnthInt == 6 || mnthInt == 9 || mnthInt == 11 )
        {
            if ( dayInt > 30 && dayInt < 1 )
            {
                return false;
            }//end of if ( dayInt > 30 && dayInt < 1 )
        }//end of if ( mnthInt == 4 || mnthInt == 6 || mnthInt == 9 || mnthInt == 11 )
        if ( mnthInt == 2 )
        {
            if ( (yearInt%1000 != 0 && yearInt%4 == 0 ) || ( yearInt%1000 == 0 && yearInt%400 == 0 ) )
            {
                if ( dayInt > 29 || dayInt < 1 )
                {
                    return false;
                }//end of if ( dayInt > 29 || dayInt < 1 )
            }//end of if ( (yearInt%1000 != 0 && yearInt%4 == 0 ) || ( yearInt%1000 == 0 && yearInt%400 == 0 ) )
            else
            {
                if ( dayInt > 28 || dayInt < 1 )
                {
                    return false;
                }//end of if ( dayInt > 28 || dayInt < 1 )
            }//end of else
        }//end of if ( mnthInt == 2 )
        return true;
    }//end of validateDate( String mDate )
 */
    public static String getStartDate(String strYear, String strQtr)
    {
        String strStartDate="";
        int iYear = Integer.parseInt(strYear);
        if(strQtr.equals("1"))
        {
            strStartDate=getOracleDate("07/01/"+(iYear-1));
        }//end of if(strQtr.equals("1"))
        else if(strQtr.equals("2"))
        {
            strStartDate=getOracleDate("10/01/"+(iYear-1));
        }//end of else if(strQtr.equals("2"))
        else if(strQtr.equals("3"))
        {
            strStartDate=getOracleDate("01/01/"+iYear);
        }//end of else if(strQtr.equals("3"))
        else if(strQtr.equals("4"))
        {
            strStartDate=getOracleDate("04/01/"+iYear);
        }//end of else if(strQtr.equals("4"))
        return strStartDate;
    }//end of getStartDate(String strYear, String strQtr)

    public static String getEndDate(String strYear, String strQtr)
    {
        String strEndDate="";
        int iYear = Integer.parseInt(strYear);
        if(strQtr.equals("1"))
        {
            strEndDate=getOracleDate("09/30/"+(iYear-1));
        }//end of if(strQtr.equals("1"))
        else if(strQtr.equals("2"))
        {
            strEndDate=getOracleDate("12/31/"+(iYear-1));
        }//end of if(strQtr.equals("2"))
        else if(strQtr.equals("3"))
        {
            strEndDate=getOracleDate("03/31/"+iYear);
        }//end of if(strQtr.equals("3"))
        else if(strQtr.equals("4"))
        {
            strEndDate=getOracleDate("06/30/"+iYear);
        }//end of if(strQtr.equals("4"))
        return strEndDate;
    }//end of getEndDate(String strYear, String strQtr)

    public static Map getReportPermissionNames()
    {
        if(reportsMap==null)
        {
            prepareReportPermissionNames();
        }//end of if(reportsMap==null)
        return  reportsMap;
    }
    public static Map getReportPermissionNames(String strKey)
    {
        if(reportsMap==null)
        {
            prepareReportPermissionNames();
        }//end of if(reportsMap==null)
        if(reportsMap.get(strKey) !=null)
        {
            return (Map)reportsMap.get(strKey);
        }//end of if(reportsMap.get(strKey) !=null)
        return new TreeMap();
    }//end of getReportPermissionNames(String strKey)


    public static void prepareReportPermissionNames()
    {
        if(reportsMap==null)
        {
            reportsMap = new TreeMap<Object,Object>();
        }//end of if(reportsMap==null)

        Map<Object, Object> caseReportMap = new TreeMap<Object, Object>();
        caseReportMap.put("Asset Report","AssetReport");
        caseReportMap.put("Case List","CaseList");
        caseReportMap.put("Case Specialist Aging Report","CaseSpecialistAgingReport");
        caseReportMap.put("Case Aging Report","CaseAgingReport");
        caseReportMap.put("Collection Report","CollectionReport");
        caseReportMap.put("Statistical Reports","StatisticalReports");
        caseReportMap.put("Average Collection Time Report","AverageCollectionTimeReport");
        caseReportMap.put("SNT Reports","SNTReports");
        caseReportMap.put("Inter Office Memo Report","InterOfficeMemoReport");
        caseReportMap.put("Waiver Report","WaiverReport");
        reportsMap.put("CHCF.ERU.ERUReports.ERUReports",caseReportMap);

        Map<Object, Object> noncaseReportMap = new TreeMap<Object, Object>();
        noncaseReportMap.put("Claim Summary Report","ClaimSummaryReport");
        noncaseReportMap.put("Lien Case Summary Report","LienCaseSummaryReport");
        noncaseReportMap.put("Medical Billing History Report","MedicalBillingHistoryReport");
        noncaseReportMap.put("HIC Report","HICReport");
        noncaseReportMap.put("Case History for a Period","CaseHistoryforaPeriod");
        reportsMap.put("CHCF.ERU.CaseLedger.Reports",noncaseReportMap);

        Map<Object, Object> caseLetterMap = new TreeMap<Object, Object>();
        caseLetterMap.put("Letter to the Assessor","LettertotheAssessor");
        caseLetterMap.put("Probate Letter","ProbateLetter");
        caseLetterMap.put("Notice of Claim","NoticeofClaim");
        caseLetterMap.put("Release of All Demands and assent to account","ReleaseofAllDemandsandassenttoaccount");
        caseLetterMap.put("Escrow Release","EscrowRelease");
        caseLetterMap.put("First Demand Letter","FirstDemandLetter");
        caseLetterMap.put("Demand with medical billing history","Demandwithmedicalbillinghistory");
        caseLetterMap.put("Unclaimed Demand","UnclaimedDemand");
        caseLetterMap.put("Thirty-Day Notification","Thirty-DayNotification");
        caseLetterMap.put("General Assent Letter","GeneralAssentLetter");
        caseLetterMap.put("Second Demand Letter","SecondDemandLetter");
        caseLetterMap.put("Inter Office Memo","InterOfficeMemo");
        caseLetterMap.put("Referral to Public Administrator","ReferraltoPublicAdministrator");
        caseLetterMap.put("Lien Release","LienRelease");
        caseLetterMap.put("Information Letter","InformationLetter");
        caseLetterMap.put("Case Status Letter","CaseStatusLetter");
        caseLetterMap.put("Probate Court Visit Checklist","ProbateCourtVisitChecklist");
        caseLetterMap.put("Acknowledgement for the receipt of check - Lien","Acknowledgementforthereceiptofcheck-Lien");
        caseLetterMap.put("Amended NOC","AmendedNOC");
        caseLetterMap.put("Medical Billing History","MedicalBillingHistory");
        caseLetterMap.put("Lien Release Memo to DMA","LienReleaseMemotoDMA");
        caseLetterMap.put("Letter returning original copies","Letterreturningoriginalcopies");
        caseLetterMap.put("Refund Memo","RefundMemo");
        caseLetterMap.put("Letter Requesting Additional Information","LetterRequestingAdditionalInformation");
        caseLetterMap.put("Acknowledgement for the receipt of check - Claim","Acknowledgementforthereceiptofcheck-Claim");
        caseLetterMap.put("Registry of Deeds Request","RegistryofDeedsRequest");
        reportsMap.put("CHCF.ERU.CaseLedger.Letters",caseLetterMap);

    }
    public static String getFormattedDate( String strDate )
    {
        StringTokenizer strDateTknzr = new StringTokenizer( strDate, "/" );
        String strMonth = "";
        String strDay = "";
        String strYear = "";
        if( strDateTknzr.hasMoreTokens() )
        {
            strMonth = strDateTknzr.nextToken();
            if( strMonth.trim() == ""  )
            {
                return "";
            }//end of if( strMonth.trim() == ""  )c
            else if( strMonth.trim().length() == 1 )
            {
                strMonth = "0"+strMonth;
            }//end of else if( strMonth.trim().length() == 1 )
            else if( strMonth.trim().length() > 2)
            {
                return "";
            }//end of else if( strMonth.trim().length() > 2)
        }//end of if( strDateTknzr.hasMoreTokens() )
        else
        {
            return "";
        }//end of else
        if( strDateTknzr.hasMoreTokens() )
        {
            strDay = strDateTknzr.nextToken();
            if( strDay.trim() == ""  )
            {
                return "";
            }//end of if( strDay.trim() == ""  )
            else if( strDay.trim().length() == 1 )
            {
                strDay = "0"+strDay;
            }//end of else if( strDay.trim().length() == 1 )
            else if( strDay.trim().length() > 2)
            {
                return "";
            }//end of else if( strDay.trim().length() > 2)
        }//end of if( strDateTknzr.hasMoreTokens() )
        else
        {
            return "";
        }//end of else
        if( strDateTknzr.hasMoreTokens() )
        {
            strYear = strDateTknzr.nextToken();
        }//end of if( strDateTknzr.hasMoreTokens() )
        else
        {
            return "";
        }//end of else

        if( strYear.trim().equals("") )
        {
            return "";
        }//end of if( strYear.trim().equals("") )

        return strMonth+"/"+strDay+"/"+strYear;
    }//end of getFormattedDate( String strDate )

    /**
     * This method compares the old list of records with the new list of records
     * and returns an ArrayList consisting of Records Insertable and  Deletable.
     * The returned values will be supplies to PL/SQL batch Procedure to insert/delete.
     *
     * @param aLOld ArrayList,aLNew ArrayList
     * @return ArrayList
     */
    /*public static ArrayList getInsertDeleteLists(ArrayList aLOld,ArrayList aLNew)
    {
        // List to collect Insert Values
        String strInsert = "", strDelete = "";
        ArrayList alInsertAndDelete = new ArrayList();
        int iOldSize = 0, iNewSize = 0;
        iOldSize = aLOld.size();
        iNewSize = aLNew.size();
        if(aLOld == null || iOldSize == 0)
        {
            for(int i=0;i<iNewSize;i++)
            {
                if(i == 0 )
                    strInsert=strInsert + (String)aLNew.get(i);
                else
                    strInsert= strInsert + ","+ (String)aLNew.get(i) ;
            }
        } // end of if(aLOld == null || aLOld.size() == 0)
        if(aLNew == null || iNewSize == 0)
        {
            for(int i=0;i<iOldSize;i++)
            {
                if(i == 0 )
                    strDelete=strDelete + (String)aLOld.get(i);
                else
                    strDelete= strDelete + "," + (String)aLOld.get(i) ;
            }
        } // end of if if(aLNew == null || aLNew.size() == 0)
        if(aLOld != null && iOldSize != 0 && aLNew != null && iNewSize != 0)
        {
            int j=0;
            boolean bInsertflag = false, bDeleteflag = false;
            // block to identify the records to be inserted
            for(int i=0;i<iNewSize;i++)
            {
                for(j=0;j<iOldSize;j++)
                {
                    if( ((String)aLNew.get(i)).equals((String)aLOld.get(j)) )
                    {
                        bInsertflag = true;
                        break;
                    } // end of if( ((String)aLNew.get(i)).equals((String)aLOld.get(j)) )
                }   // end for inner
                if(bInsertflag == false)
                {
                    if(i == 0 )
                        strInsert=strInsert + (String)aLNew.get(i);
                    else
                        strInsert=strInsert + "," + (String)aLNew.get(i)  ;
                }   // end of if (bInsertflag == false)
                else
                {
                    bInsertflag = false;
                }   // end of else
            } // end of for(int i=0;i<aLNew.size();i++)
            // block to identify the records to be deleted
            for(int i=0;i<iOldSize;i++)
            {
                for(j=0;j<iNewSize;j++)
                {
                    if( ((String)aLOld.get(i)).equals((String)aLNew.get(j)) )
                    {
                        bDeleteflag = true;
                        break;
                    } // end of if( ((String)aLOld.get(i)).equals((String)aLNew.get(j)) )
                }   // end for inner
                if(bDeleteflag == false)
                {
                    if(i == 0 )
                        strDelete=strDelete + (String)aLOld.get(i);
                    else
                        strDelete= strDelete + "," +  (String)aLOld.get(i) ;
                }   // end of if(bDeleteflag == false)
                else
                {
                    //  log.info("Dont delete .........."+ strOld[i]);
                    bDeleteflag = false;
                }   // end of else
            } // end of outer for(int i=0;i<aLOld.size();i++)
            // Printing Inserting Items
        } // end of if(aLOld != null && aLOld.size() != 0 && aLNew != null && aLNew.size() != 0)
        if(! strInsert.equals("") && strInsert.startsWith(","))  strInsert= strInsert.substring(1,strInsert.length());
        if(! strDelete.equals("") && strInsert.startsWith(","))  strDelete= strDelete.substring(1,strDelete.length());
        alInsertAndDelete.add(strInsert);
        alInsertAndDelete.add(strDelete);
        return alInsertAndDelete;
    } // end of getInsertDeleteLists(ArrayList aLOld,ArrayList aLNew)
 */
    /**
     * This method trims the input string to the required  size and returns the same.
     *
     * @param strToBeTrimmed String,iTrimSize) int
     * @return String
     */
    /*public static String getTrimmedTo(String strToBeTrimmed,int iTrimSize)
    {
        if(strToBeTrimmed != null)
        {
            if(strToBeTrimmed.length() >= iTrimSize)
                strToBeTrimmed = strToBeTrimmed.substring(0,iTrimSize);
        } // end of if(strToBeTrimmed != null)
        else
        {
            strToBeTrimmed = "";
        } // end else
        return strToBeTrimmed;
    } // end of method getTrimmedTo
 */
    /**
     * This method converts the input string to the required  US Format Number.
     *
     * @param strRawNumber String( The String which contains numeric),strNumberType String("L" - If Long,"D" - If Double)
     * eg., strRawNumber("1234567","L") , strRawNumber("123456343347.34","D")
     * @return String
     *
     */
    /*public static String convertNumberToUSFormat(String strRawNumber,String strNumberType)
    {
        String strFormatted = "";
        try
        {
            if(strRawNumber != null && !strRawNumber.equals("") && strNumberType != null)
            {
                if(strNumberType.equals("D")) // checking if double
                {
                    strFormatted = NumberFormat.getInstance(Locale.US).format(Double.parseDouble(strRawNumber.trim()));
                } // end of if(strNumberType.equals("D"))
                else if(strNumberType.equals("L"))
                {
                    strFormatted = NumberFormat.getInstance(Locale.US).format(Long.parseLong(strRawNumber.trim()));
                } // end of else  if(strNumberType.equals("L"))
            } // end of if(strRawNumber != null && !strRawNumber.equals("") && strNumberType != null)
        } // end of try
        catch(Exception e)
        {
            TTKCommon.logStackTrace(e);
        } // end of catch
        return strFormatted;
    } // end of method getUSFormattedNumber
*/
    /**
     * This method returns TableData object retreived from session.
     * If null is retruned from session, creates a new one
     *
     * @param HttpServletRequest request
     * @return TableData the TableData object
     */
    /*public static String getLinkId(HttpServletRequest request, String strDestSubLinkName)
    {
        return null;
        com.ttk.security.administration.userhelper.SecurityProfile securityProfile = (com.ttk.security.administration.userhelper.SecurityProfile)request.getSession().getAttribute("securityProfileVO");
         Map mapProfile = securityProfile.getProfile();
         log.info("\n\n@@@@@@@mapProfile:");
         log.info("strDestSubLinkName:"+strDestSubLinkName);
         log.info("@@@@@@@iMainCaseLedgerNo in CaseHistoryAction:"+mapProfile.get("iMainCaseLedgerNo"));
         log.info("@@@@@@@iMainCaseLedgerNo in CaseHistoryAction:"+mapProfile.get("iSubCaseLedgerNo"));

         Integer integer = ((Integer)mapProfile.get("iMainCaseLedgerNo"));
         int iMainCaseLedgerNo = integer.intValue();

         String strMainCaseLedgerNo =  "0" + iMainCaseLedgerNo;

         String strDestSubLink = "";
         if(strDestSubLinkName.equals("CaseInView"))
         {
         strDestSubLink = "iSubCaseLedgerNo";
         }
         else if(strDestSubLinkName.equals("CreateCase"))
         {
         strDestSubLink = "iCreateCaseNo";
         }
         else if(strDestSubLinkName.equals("NonMember"))
         {
         strDestSubLink = "iNonMemberNo";
         }
         else if(strDestSubLinkName.equals("SearchCases"))
         {
         strDestSubLink = "iSearchCasesNo";
         }
         else if(strDestSubLinkName.equals("Cases"))
         {
         strDestSubLink = "iCasesNo";
         }
         else if(strDestSubLinkName.equals("ComputeClaim"))
         {
         strDestSubLink = "iComputClaimNo";
         }

         log.info("strDestSubLink:"+strDestSubLink);

         integer = ((Integer)mapProfile.get(strDestSubLink));

         iMainCaseLedgerNo = integer.intValue();
         String strSubCaseLedgerNo = "";
         strSubCaseLedgerNo = "0" + iMainCaseLedgerNo;

         String strLink =  strMainCaseLedgerNo + strSubCaseLedgerNo+"00";
         log.info("strLink:"+strLink);
         return strLink;


    }//End of getTableConfig(HttpServletRequest request)
*/
    /**
     * This method trims the values in the map object to the required  size and returns the modified map.
     *
     * @param mpOrg Map,iSize int
     * @return Map
     */
    /*public static Map getSortedTrimmedMap(Map mpOrg,int iSize)
    {
        //if the passed argument is null create a blank map
        mpOrg = (mpOrg == null)?new TreeMap():mpOrg;
        Map mpTrim = new TreeMap(mpOrg);
        Set stOrg = mpOrg.keySet();
        //Object objOrg[] = stOrg.toArray();
        Object[] objOrg = (Object []) stOrg.toArray();
        for(int j=0;j<objOrg.length;j++)
        {
            String strMapValueOrg = TTKCommon.checkNull((String)mpTrim.get(objOrg[j]));
            String strMapValue = strMapValueOrg;
            if(iSize < strMapValue.length())
                strMapValue = strMapValueOrg.substring(0,iSize);
            mpTrim.put(objOrg[j], strMapValue);
        }//end of for(int j=0;j<objOrg.length;j++)

        return mpTrim;
    }//end of getSortedTrimmedMap(Map mpOrg,int iSize)
*/
    /**
     * This method trims the values in the map object to the required  size and returns the modified map.
     *
     * @param mpOrg Map,iSize int
     * @return Map
     */
    /*public static Map getTrimmedMap(Map mpOrg,int iSize)
    {
        //if the passed argument is null create a blank map
        mpOrg = (mpOrg == null)?new TreeMap():mpOrg;
        Map mpTrim = new TreeMap();
        Set stOrg = mpOrg.keySet();
        Object objOrg[] = stOrg.toArray();
        for(int j=0;j<objOrg.length;j++)
        {
            String strMapValueOrg = TTKCommon.checkNull((String)mpOrg.get(objOrg[j]));
            String strMapValue = strMapValueOrg;
            if(iSize < strMapValue.length())
                strMapValue = strMapValueOrg.substring(0,iSize);
            mpTrim.put(objOrg[j], strMapValue);
        }//end of for(int j=0;j<objOrg.length;j++)

        return mpTrim;
    }//end of getTrimmedMap(Map mpOrg,int iSize)
*/
    /**
     * this method takes java.util.Date and returns the date in dd/mm//yyyy format
     * @param date java.util.Date
     * @return String in the format dd/mm//yyyy
     */
    public synchronized static String getFormattedDate(Date date)
    {
        if(date!=null)
        {
            SimpleDateFormat sdFormat=new SimpleDateFormat("dd/MM/yyyy");
            return sdFormat.format(date.getTime());
        }//end of if(date!=null)
        return "";

    }//end of getFormattedDate(Date date)

    /**
     * this method takes java.util.Date and returns the date in dd/MM/yyyy h:mm a format
     * @param date java.util.Date
     * @return String in format dd/MM/yyyy h:mm a
     */
    public synchronized static String getFormattedDateHour(Date date)
    {
        if(date!=null)
        {
            SimpleDateFormat sdFormat=new SimpleDateFormat("dd/MM/yyyy h:mm a");
            return sdFormat.format(date.getTime());
        }//end of if(date!=null)
        return "";
    }//end of getFormattedDateHour(Date date)

    /**
     * this method takes java.util.Date and returns the date in dd/MM/yyyy h:mm a format
     * @param date java.util.Date
     * @return String in format dd/MM/yyyy h:mm a
     */
    public synchronized static String getFormattedDateTime(Date date)
    {
        if(date!=null)
        {
            SimpleDateFormat sdFormat=new SimpleDateFormat("ddMMyyyy-hhmm");
            return sdFormat.format(date.getTime());
        }//end of if(date!=null)
        return "";
    }//end of getFormattedDateTime(Date date)

    /**
    * this method takes java.util.Date and returns the date in ddMMyyyy-kkmmss format.
    * @param date java.util.Date
    * @return String in format ddMMyyyy-kkmmss
    */
   public synchronized static String getFormattedDateTimeSec(Date date)
   {
       if(date!=null)
       {
           SimpleDateFormat sdFormat=new SimpleDateFormat("ddMMyyyy-kkmmss");
           return sdFormat.format(date.getTime());
       }//end of if(date!=null)
       return "";
   }//end of getFormattedDateTimeSec(Date date)
   
   // Change added for BOA CR KOC1220
   /**
    * this method takes java.util.Date and returns the date in ddMMyyyykkmmss format.
    * @param date java.util.Date
    * @return String in format ddMMyyyykkmmss
    */
   public synchronized static String getFormattedDateTimeSecNoHyphen(Date date)
   {
       if(date!=null)
       {
           SimpleDateFormat sdFormat=new SimpleDateFormat("ddMMyyyykkmmss");
           return sdFormat.format(date.getTime());
       }//end of if(date!=null)
       return "";
   }//end of getFormattedDateTimeSecNoHyphen(Date date)
   

    /**
     * this method takes date in dd/mm//yyyy and returns java.util.Date
     * month takes the values 1-12
     * @param strDate in the format dd/mm//yyyy
     * @return date java.util.Date
     */
    public synchronized static java.util.Date getUtilDate(String strDate)throws TTKException
    {
        /*java.util.Date date=null;

         if(!strDate.equals(""))
         {
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         try
         {
         date=sdf.parse(strDate);
         }
         catch (ParseException exp)
         {
         throw new TTKException(exp,"error.date.format");
         }
         }
         return date;
         */
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        if (strDate!=null && !strDate.equals(""))
        {
//            java.sql.Timestamp timeStamp = null;
            StringTokenizer strTokenDate = new StringTokenizer(strDate, "/");
            int intYear = 0;
            int intHH = 0;
            int intMM = 0;
            int intDay = Integer.parseInt(strTokenDate.nextToken());
            int intMonth = Integer.parseInt(strTokenDate.nextToken());
            String strYear = strTokenDate.nextToken(); // for tokenizing yyyy hh:mm
            //log.info("Day =="+intDay+"Month=="+intMonth+"Year=="+strYear);
            boolean bolFormat1 = strDate.matches("\\d{2}/\\d{2}/\\d{4}"); // check the dd/mm/yyyy format
            boolean bolFormat2 = strDate.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}"); // check the dd/mm/yyyy HH:MM format

            if (!(bolFormat1 | bolFormat2)) // NOTE : Returns true if bolFormat1 and bolFormat2 are both true; always evaluates bolFormat1 and bolFormat2
            {
                ParseException exp = new ParseException("Invalid Date Format",0);
                throw new TTKException(exp, "error.date.format");
            }// end of if(!(bolFormat1 | bolFormat2))

            StringTokenizer strTokenYear = new StringTokenizer(strYear);
            if (strTokenYear != null && strTokenYear.countTokens() > 1)
            {
                intYear = Integer.parseInt(strTokenYear.nextToken());
                String strTime = strTokenYear.nextToken();
                StringTokenizer strTokenTime = new StringTokenizer(strTime, ":");
                if (strTokenTime != null && strTokenTime.countTokens() > 1)
                {
                    intHH = Integer.parseInt(strTokenTime.nextToken());
                    intMM = Integer.parseInt(strTokenTime.nextToken());
                }//end of if (strTokenTime != null && strTokenTime.countTokens() > 1)
            }//end of if (strTokenYear != null && strTokenYear.countTokens() > 1)
            else
            {
                intYear = Integer.parseInt(strYear);
            }// end if(strTokenYear.countTokens()>1)
            calendar.clear();
            calendar.set(intYear, intMonth-1, intDay, intHH, intMM,0); // calendar will take month from 0-11 in script we get 1-12

//            timeStamp = new java.sql.Timestamp(calendar.getTimeInMillis());

            return calendar.getTime();
        }
        else
        {
            return null;
        }

    }//end of getUtilDate(String strDate)

    /**
     * this method takes date in dd/mm//yyyy and returns java.sql.Timestamp
     * @param strDate in the format dd/mm//yyyy
     * @return date java.sql.Timestamp
     */
    /*public synchronized static java.sql.Timestamp getTimeStamp(String strDate)throws TTKException
    {
        java.util.Date date=null;
        java.sql.Timestamp timeStamp= null;
        if(!strDate.equals(""))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try
            {
                date=sdf.parse(strDate);
                timeStamp=new java.sql.Timestamp(date.getTime());
            }
            catch (ParseException exp)
            {
                throw new TTKException(exp,"error.date.format");
            }
        }
        return timeStamp;
    }//end of getUtilDate(String strDate)
*/
    /**
     * this method takes date in dd/MM/yyyy h:mm a and returns java.util.Date
     * @param strDate in the format dd/MM/yyyy h:mm a
     * @return date java.util.Date
     */
    public synchronized static java.util.Date getUtilDateHour(String strDate)throws TTKException
    {
        java.util.Date date=null;
        if(!strDate.equals(""))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy h:mm a");
            try
            {
                date=sdf.parse(strDate);
            }//end of try
            catch (ParseException exp)
            {
                throw new TTKException(exp,"error.date.format");
            }//end of catch (ParseException exp)
        }//end of if(!strDate.equals(""))
        return date;
    }//end of getUtilDateHour(String strDate)


    /**
     * this method takes String values and return Long value
     * @param strLong
     * @return Long
     */

    public static Long getLong(String strLong)throws TTKException
    {
        Long value=null;

        if(strLong!=null && !strLong.equals(""))
        {
            value=Long.parseLong(strLong);
        }//end of if(strLong!=null && !strLong.equals(""))
        return value;
    }//end of getLong(String strLong)
	
	//KOC 1270 for hospital cash benefit
    public static String getString(String strString)throws TTKException
    {
        String value=null;

        if(strString!=null && !strString.equals(""))
        {
            value=String.valueOf(strString);
        }//end of if(strLong!=null && !strLong.equals(""))
        return value;
    }//end of getLong(String strLong)
    //KOC 1270 for hospital cash benefit
    /**
     * this method takes String values and return int value
     * @param strInt
     * @return int
     */

    public static int getInt(String strInt)throws TTKException
    {
        int value=0;

        if(strInt!=null && !strInt.equals(""))
        {
            value=Integer.parseInt(strInt);
        }//end of if(strInt!=null && !strInt.equals(""))
        return value;
    }//end of getInt(String strInt)

    /**
     * this method takes String values and return Double value
     * @param strDouble
     * @return Double
     */
    public static Double getDouble(String strDouble)throws TTKException
    {
        Double value=null;
        if(strDouble!=null && !strDouble.equals(""))
        {
            value=Double.parseDouble(strDouble);
        }//end of if(strDouble!=null && !strDouble.equals(""))
        return value;
    }//end of getDouble(String strDouble)

    /**
     * this method takes String values and return BigDecimal value
     * @param strBigDecimal
     * @return BigDecimal
     */
    public static BigDecimal getBigDecimal(String strBigDecimal)throws TTKException
    {
        BigDecimal bdValue = null;
        if(!TTKCommon.checkNull(strBigDecimal).equals(""))
        {
            bdValue = new BigDecimal(strBigDecimal);
        }//end of if(!TTKCommon.checkNull(strBigDecimal).equals(""))
        return bdValue;
    }//end of getDouble(String strDouble)


    /**
     * this method checks for the which item should be selected in combo box
     * @param strId1 String
     * @param strId2 String
     * @return String
     */

    public static String isSelected(String strId1 ,String strId2)
    {
        if(TTKCommon.checkNull(strId1).equals(TTKCommon.checkNull(strId2)))
        {
            return "SELECTED";
        }//end of if(TTKCommon.checkNull(strId1).equals(TTKCommon.checkNull(strId2)))
        else
        {
            return "";
        }//end of else
    }//end of isSelected(String strId1 ,String strId2)
    /**
     * This method will return the string of pipe concatenated String for the given String Array
     * @param strAssociateProcedureList String Array which has to be concatenated
     * @return the pipe concatenated string
     */
    /*public static String getPipeConcatenatedString(String [] strAssociateProcedureList)
    {
        StringBuffer strBuffConcate = new StringBuffer();
        for(int i=0;i<strAssociateProcedureList.length;i++)
        {
            if(strAssociateProcedureList[i] != null && strAssociateProcedureList[i].trim().length()>0)
            {
                strBuffConcate.append("|").append(strAssociateProcedureList[i]);
            }//end of if(strAssociateProcedureList[i] != null && strAssociateProcedureList[i].trim().length()>0)
        }//end of For
        strBuffConcate.append("|");
        return strBuffConcate.toString();

    }//end of getPipeConcatenatedString(String [] strAssociateProcedureList)
*/
    /**
     * This method returns current webboard id
     *
     * @param HttpServletRequest request
     * @return strCacheId String the webboard id
     */
    public static Long getWebBoardId(HttpServletRequest request)
    {
        //set the web board id if any
        TTKCommon.setWebBoardId(request);
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        String strCacheId = "";
        strCacheId = toolbar.getWebBoard().getCurrentId();
        //set the default id if the current id is empty
        if(strCacheId.equals(""))
        {
            if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
                strCacheId = ((CacheObject)toolbar.getWebBoard().getWebboardList().get(0)).getCacheId();
        }//end of if(strCacheId.equals(""))
        if(!strCacheId.equals(""))
        {
        	
            return new Long(strCacheId);
        }//end of if(!strCacheId.equals(""))
        else
        {
            return null;
        }//end of else
    }//end of getWebBoardId(HttpServletRequest request)

    /**
     * This method returns current webboard desc
     *
     * @param HttpServletRequest request
     * @return strCacheId String the webboard desc
     */
    public static String getWebBoardDesc(HttpServletRequest request)
    {
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        String strCacheDesc = "";
        String strCacheId = toolbar.getWebBoard().getCurrentId();
        //set the default id if the current id is empty
        if(!strCacheId.equals("") && toolbar.getWebBoard().getWebboardList() != null)
        {
            CacheObject cacheObject = null;
            for(int i=0; i < toolbar.getWebBoard().getWebboardList().size(); i++)
            {
                cacheObject =  ((CacheObject)toolbar.getWebBoard().getWebboardList().get(i));
                if(strCacheId.equals(""+cacheObject.getCacheId()))
                {
                    strCacheDesc = cacheObject.getCacheDesc();
                    break;
                }//end of if

            }//end of for
        }//end of if(!strCacheId.equals("") && toolbar.getWebBoard().getWebboardList() != null)
        return strCacheDesc;
    }//end of getWebBoardDesc(HttpServletRequest request)

    /**
     * This method sets the webboard id
     *
     * @param HttpServletRequest request
     * @return strCacheId String the webboard id
     */
    private static void setWebBoardId(HttpServletRequest request)
    {
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        String strCacheId = TTKCommon.checkNull(request.getParameter("webBoard"));
        //set the web board id if the parameter is found
        if(TTKCommon.checkNull((String)request.getAttribute("webboardinvoked")).equals(""))
        {
            if(!strCacheId.equals(""))
            {
                //if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
                //strCacheId = ((CacheObject)toolbar.getWebBoard().getWebboardList().get(0)).getCacheId();
                toolbar.getWebBoard().setCurrentId(strCacheId);
            }//end of if(strCacheId.equals(""))
        }
    }//end of setWebBoardId(HttpServletRequest request)

    /**
     * This method modifies the webboard description if it changed
     *
     * @param HttpServletRequest request
     */
    public static void modifyWebBoardId(HttpServletRequest request)
    {
        String strCacheId = TTKCommon.checkNull((String)request.getAttribute("cacheId"));
        String strCacheDesc = TTKCommon.checkNull((String)request.getAttribute("cacheDesc"));
        //restrict the length of the description for 40 characters
        if(strCacheDesc.length() > 40)
        {
            strCacheDesc = strCacheDesc.substring(0, 41);
        }//end of if(strCacheDesc.length() > 40)

        if(!TTKCommon.checkNull(strCacheId).equals(""))
        {
            Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
            ArrayList alCacheObjects = toolbar.getWebBoard().getWebboardList();
            CacheObject cacheObject = null;
            if(alCacheObjects != null && alCacheObjects.size() > 0)
            {
                for(int i=0; i < alCacheObjects.size(); i++)
                {
                    cacheObject = (CacheObject)alCacheObjects.get(i);
                    if(cacheObject.getCacheId().equals(strCacheId))
                    {
                        cacheObject.setCacheDesc(strCacheDesc);
                        return;
                    }//end of if
                }//end of for
            }//end of if(alCacheObjects != null && alCacheObjects.size() > 0)
        }//end of if
    }//end of modifyWebBoardId(HttpServletRequest request)

    /**
     * This method deletes the webboard id
     *
     * @param HttpServletRequest request
     */
    public static void deleteWebBoardId(HttpServletRequest request)
    {

        String strCacheId = TTKCommon.checkNull((String)request.getAttribute("cacheId"));
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        ArrayList alCacheObjects = null;
        if(!TTKCommon.checkNull(strCacheId).equals(""))
        {
            StringTokenizer stCacheId = new StringTokenizer(strCacheId, "|");
            alCacheObjects = toolbar.getWebBoard().getWebboardList();
            CacheObject cacheObject = null;
            String strToken = "";
            //check for the deleted items from the screen and delete the same from the web board items
            while(stCacheId.hasMoreTokens())
            {
                strToken = stCacheId.nextToken();
                if(alCacheObjects != null && alCacheObjects.size() > 0)
                {
                    for(int i=(alCacheObjects.size()-1); i >= 0; i--)
                    {
                        cacheObject = (CacheObject)alCacheObjects.get(i);
                        if(cacheObject.getCacheId().equals(strToken))
                        {
                            alCacheObjects.remove(i);
                        }//end of if
                    }//end of for
                }//end of if(alCacheObjects != null && alCacheObjects.size() > 0)
            }//end of while(stCacheId.hasMoreTokens())

            //re-set the web board id
            alCacheObjects = toolbar.getWebBoard().getWebboardList();
            if(alCacheObjects != null && alCacheObjects.size() > 0)
            {
                toolbar.getWebBoard().setCurrentId(((CacheObject)alCacheObjects.get(0)).getCacheId());
            }//end of if(alCacheObjects != null && alCacheObjects.size() > 0)
            else
            {
                toolbar.getWebBoard().setCurrentId("");
            }//end of else

        }//end of if

    }//end of deleteWebBoardId(HttpServletRequest request)

    /**
     * This method returns the logged in user sequence id
     *
     * @param HttpServletRequest request
     * @return Long the user sequence id
     */
    public static Long getUserSeqId(HttpServletRequest request)
    {
        return ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getUSER_SEQ_ID();
    }//end of getWebBoardId(HttpServletRequest request)

	//KOC 1270 for hospital cash benefit
    public static String getCashBenefitSeqID(HttpServletRequest request)
    {
        return ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getCashBenefitSeqID();
    }//end of getWebBoardId(HttpServletRequest request)
    
    public static String getCashBenefitID(HttpServletRequest request)
    {
        return ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getCashBenefitID();
    }//end of getWebBoardId(HttpServletRequest request)
    //KOC 1270 for hospital cash benefit
    /**
     * This method returns the logged in user associated group list
     *
     * @param HttpServletRequest request
     * @return ArrayList the user associated group list
     */
    //KOC-1273 FOR PRAVEEN CRITICAL BENEFIT
    public static String getCriticalBenefitID(HttpServletRequest request)
    {
        return ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getCriticalBenefitID();
    }//end of getWebBoardId(HttpServletRequest request)
  //KOC FOR PRAVEEN CRITICAL BENEFIT
    public static ArrayList getUserGroupList(HttpServletRequest request)
    {
        return ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getGroupList();
    }//end of getWebBoardId(HttpServletRequest request)

    /**
     * This method returns the logged in user sequence id
     *
     * @param HttpServletRequest request
     * @return Long the user sequence id
     */
    public static String getUserID(HttpServletRequest request)
    {
        return ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getUSER_ID();
    }//end of getUserID(HttpServletRequest request)

    /**
     * This method checks whether user as the access to the given path.
     * @param HttpServletRequest request
     * @param String strPermission path to be checked
     * @return boolean true/false
     */
    public static boolean isAuthorized(HttpServletRequest request, String strPermission) throws TTKException
    {
        StringBuffer sbfPermisson=new StringBuffer();
        sbfPermisson.append(((UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile().getActiveLink()).append(".");
        sbfPermisson.append(((UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile().getActiveSubLink()).append(".");
        sbfPermisson.append(((UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile().getActiveTab()).append(".").append(strPermission);
      
        return ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile().isAuthorized(sbfPermisson.toString());
    }//end of isAuthorized(HttpServletRequest request)

    
    /**
     * This method checks whether user as the access to the given path.
     * @param HttpServletRequest request
     * @param String strPermission path to be checked
     * @return boolean true/false
     */
    public static boolean isAuthorizedLST(HttpServletRequest request, String strLST) throws TTKException
    {
      
        return ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile().isAuthorized(strLST);
    }//end of isAuthorized(HttpServletRequest request)

    
    /**
     * This method checks for the data in GridTable and returns true/false
     *
     * @param HttpServletRequest request
     * @param String strIdentifier TableData to be checked
     * @return boolean true/false
     */
    public static boolean isDataFound(HttpServletRequest request, String strIdentifier) throws TTKException
    {
        boolean bFlag=false;
        if((request.getSession().getAttribute(strIdentifier))!=null)
        {
            TableData tableData=(TableData)request.getSession().getAttribute(strIdentifier);
            
            ArrayList alData=(ArrayList)tableData.getData();
            if(alData!=null && alData.size()!=0)
                bFlag=true;
            
           
        }//end of if((request.getSession().getAttribute(strIdentifier))!=null)
        return bFlag;
        
    }//end of isDataFound(HttpServletRequest request, String strIdentifier)

    /**
     * This method checks for the data in Tree and returns true/false
     *
     * @param HttpServletRequest request
     * @param String strIdentifier TreeData to be checked
     * @return boolean true/false
     */
    public static boolean isTreeDataFound(HttpServletRequest request, String strIdentifier) throws TTKException
    {
        boolean bFlag=false;
        if((request.getSession().getAttribute(strIdentifier))!=null)
        {
            TreeData treeData=(TreeData)request.getSession().getAttribute(strIdentifier);
            ArrayList alData=(ArrayList)treeData.getRootData();
            if(alData!=null && alData.size()!=0)
            {
                bFlag=true;
            }//end of if(alData!=null && alData.size()!=0)
        }//end of if((request.getSession().getAttribute(strIdentifier))!=null)
        return bFlag;
    }//end of isTreeDataFound(HttpServletRequest request, String strIdentifier)

    /**
     * This method will return the active link from the security profile in session
     *
     * @param HttpServletRequest request
     * @return String the active sub link
     * @throws TTKException
     */
    public static String getActiveLink(HttpServletRequest request) throws TTKException
    {
        String strLink = "";
        strLink=((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile().getActiveLink();
        return strLink;
    }//end of getActiveLink(HttpServletRequest request)
    /**
     * This method will set the set left link,sub link and tab from the security profile in session
     *
     * @param HttpServletRequest request
     * @return String the active left link, String the active sub link String the active tab
     * @throws TTKException
     */
    public static void setActiveLinks(String leftLink,String subLink,String tab,HttpServletRequest request) throws TTKException
    {
    	leftLink=TTKCommon.checkNull(leftLink);
    	subLink=TTKCommon.checkNull(subLink);
    	tab=TTKCommon.checkNull(tab);
    	if(leftLink.length()>1&&subLink.length()>1&&tab.length()>1){
    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		if(userSecurityProfile!=null){
    			 SecurityProfile  securityProfile=userSecurityProfile.getSecurityProfile();
    			 if(securityProfile.isAuthorized(leftLink+"."+subLink+"."+tab))securityProfile.setLinks(leftLink, subLink, tab);
    		}
    	}
    }//end of setActiveLinks(String leftLink,String subLink,String tab,HttpServletRequest request)

    /**
     * This method will return the active sub link from the security profile in session
     *
     * @param HttpServletRequest request
     * @return String the active sub link
     */
    public static String getActiveSubLink(HttpServletRequest request) throws TTKException
    {
        String strSubLink = "";
        strSubLink=((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile().getActiveSubLink();
        return strSubLink;
    }//end of getActiveSubLink(HttpServletRequest request)

    /**
     * This method will return the active tab link from the security profile in session
     *
     * @param HttpServletRequest request
     * @return String the active tab link
     */
    public static String getActiveTab(HttpServletRequest request) throws TTKException
    {
        String strTabLink = "";
        strTabLink=((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile().getActiveTab();
        return strTabLink;
    }//end of getActiveTab(HttpServletRequest request)
    /**
     * This method returns the counts of deleted records
     * @param strDeleteList String object which contains the comma separated sequence id's to be deleted
     * @return int the number of records to be deleted
     */
    public static int deleteStringLength(String strDeleteList,String strPattern)
    {
        StringTokenizer stCounts = new StringTokenizer(strDeleteList, strPattern);
        if(stCounts != null)
        {
            return stCounts.countTokens();
        }//end of if(stCounts != null)
        else
        {
            return 0;
        }//end of else
    }//end of deleteStringLength(String strDeleteList,String strPattern)

    /**
     * This method returns the counts of deleted records
     * @param strDeleteList String object which contains the comma separated sequence id's to be deleted
     * @return int the number of records to be deleted
     */
    public static int deleteStringLength(String strDeleteList)
    {
        StringTokenizer stCounts = new StringTokenizer(strDeleteList, ",");
        if(stCounts != null)
        {
            return stCounts.countTokens();
        }//end of deleteStringLength
        else
        {
            return 0;
        }//end of else
    }//end of deleteStringLength(String strDeleteList)

    /**
     * this method makes the check box to be checked/unchked
     * @param strId1 String
     * @param strId2 String
     * @return String
     */
    public static String isChecked(String strId1)
    {
        if(TTKCommon.checkNull(strId1).equals("Y"))
        {
            return "checked";
        }//end of if(TTKCommon.checkNull(strId1).equals("Y"))
        else
        {
            return "";
        }//end of else
    }//end of  isChecked(String strId1)
	
	 // Change added for KOC1227C
    
    /**
     * this method makes the check box to be checked/unchked
     * @param strId1 String
     * @return String
     */
    public static String isCheckedLS(String strId1)
    {
        if(TTKCommon.checkNull(strId1).equals("LS"))
        {
            return "checked";
        }//end of if(TTKCommon.checkNull(strId1).equals("LS"))
        else
        {
            return "";
        }//end of else
    }//end of  isCheckedLS(String strId1)
    
    /**
     * this method makes the check box to be checked/unchked
     * @param strId1 String
     * @return String
     */
    public static String isCheckedINSL(String strId1)
    {
        if(TTKCommon.checkNull(strId1).equals("INSL"))
        {
            return "checked";
        }//end of if(TTKCommon.checkNull(strId1).equals("INSL"))
        else
        {
            return "";
        }//end of else
    }//end of  isCheckedINSL(String strId1)

    /**
     * This menthod for document viewer information
     * @param request HttpServletRequest object which contains hospital information.
     * @param hospitalDetailVO HospitalDetailVO object which contains hospital information.
     * @exception throws TTKException
     */

    public static void documentViewer(HttpServletRequest request) throws TTKException

    {
//        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        String strSubLink = TTKCommon.getActiveLink(request);
        ArrayList<String> alDocumentList = new ArrayList<String>();
        alDocumentList.add("leftlink="+TTKCommon.getActiveLink(request));

        if(strSubLink.equalsIgnoreCase("empanelment")){
            alDocumentList.add("hosp_seq_id="+TTKCommon.getWebBoardId(request));
        }//end of if(strSubLink.equalsIgnoreCase("empanelment"))

        else{
            alDocumentList.add("policy_number="+WebBoardHelper.getPolicyNumber(request));
            alDocumentList.add("dms_reference_number="+request.getParameter("DMSRefID"));
        }//end of else(strSubLink.equalsIgnoreCase("enrollment"))

        if(request.getSession().getAttribute("toolbar")!=null){
            ((Toolbar)request.getSession().getAttribute("toolbar")).setDocViewParams(alDocumentList);
        }//end of if(request.getSession().getAttribute("toolbar")!=null)

    }//end of documentViewer(HttpServletRequest request)

    /**
     * This menthod for compare two value to load style class
     * @param String strCompare which holds the first value.
     * @param String strCompareWith which holds the second value.
     * @param String strIfEquals which class to return if comparision is true.
     * @param String strIfNotEquals which class to return if comparision fails.
     * @exception throws TTKException
     */

    public static String compareValues(String strCompare,String strCompareWith,String strIfEquals,
            String strIfNotEquals) throws TTKException
    {
        if(strCompare!=null&&strCompareWith!=null&&!strCompare.equals("")&&!strCompareWith.equals(""))
        {
            if(strCompare.equals(strCompareWith))
            {
                return strIfEquals;
            }//end of if(strCompare.equals(strCompareWith))
            else
            {
                return strIfNotEquals;
            }//end of else
        }//end of if(strCompare!=null&&strCompareWith!=null&&!strCompare.equals("")&&!strCompareWith.equals(""))
        return strIfEquals;
    }//end of compareValues(String strCompare,String strCompareWith,String strIfEquals, String strIfNotEquals)

    /**
     * This menthod for returns the cache description.
     * @param String strCasheId which holds cacheId value.
     * @param String strCache which holds the cache value.
     * @param String which returns cache description.
     * @exception throws TTKException
     */

    public static String getCacheDescription(String strCasheId,String strCache) throws TTKException
    {
        if(strCasheId==null||strCache==null)
        {
            return null;
        }//end of if(strCasheId==null||strCache==null)

        CacheObject cacheObject=null;
        ArrayList alCache=(ArrayList)Cache.getCacheObject(strCache);

        if(alCache!=null&&alCache.size()>0)
        {
            for(int iCache=0;iCache<alCache.size();iCache++)
            {
                cacheObject = (CacheObject)alCache.get(iCache);
                if(strCasheId.equals(cacheObject.getCacheId()))
                {
                    return  cacheObject.getCacheDesc();
                }//end of if(strCasheId.equals(cacheObject.getCacheId()))
            }//end of for(int iCache=0;iCache<alCache.size();iCache++)
        }//end of if(alCache!=null&&alCache.size()>0)
        return "";
    }//end of getCacheDescription(String strCasheId,String strCache) throws TTKException

    /**
     * This method will take xml file name as input,Read it and builds
     * the DOm4j document object and returns it to called method
     * @param strXmlFileName String name of the XML file
     * @return doc Dom4j Document object
     * @throws TTKException if any Error occures
     */
    public static Document getDocument(String strXmlFileName)throws TTKException
    {
        Document doc=null;
        try{
            //TODO now XML files Read from Jboss/bin later it will be read from Properties file
            File inputFile = new File(strXmlFileName);
            //create a document builder object
            DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = fact.newDocumentBuilder();
            //create a dom reader object
            DOMReader domReader = new DOMReader();
            //covert the w3c document object obtained from document builder to dom4j document
            doc = domReader.read(db.parse(inputFile));//read method take w3c document and returns dom4j document
            log.info("Document Read Sucessfully..."+strXmlFileName);
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException( exp,"");
        }//end of catch(Exception exp)
        return doc;
    }
    /**
     * This method is used to get the Element from ruleconfig file
     *
     * @param strOperator String operator name.
     * @return Element
     */
    public static Element getRuleConfig(String strOperator) throws TTKException
    {
        Document configDocument = getDocument("ruleconfig.xml");
        Element eleOperator=(Element)configDocument.selectSingleNode("/configuration/operator[@type='"+strOperator+"']/display");
        return eleOperator;
    }//end of getRuleConfig(String strOperator)

    /**
     * This method is used to build the Element to be displayed from the XML
     * @param eleDisplay Element to be displayed
     * @param strFieldName String field name of the Element
     * @param strFieldValue String current value of the element
     * @param strPermission String to disable element or to makeReadOnly
     * @param strMandatoryID String to give unique id for element if it is a mandatory field
     * @return String
     */
    public static String buildDisplayElement(Element eleDisplay,String strFieldName,String strFieldValue,String strPermission,String strMandatoryID)
    {
        StringBuffer sbfDisplayElement=new StringBuffer();

        if(eleDisplay!=null)
        {
            if(eleDisplay.valueOf("@control").equalsIgnoreCase("input"))
            {
                sbfDisplayElement.append("<input type=\"").append(eleDisplay.valueOf("@type")).append("\" ");
                sbfDisplayElement.append(" name=\"").append(strFieldName).append("\" ");
                if(eleDisplay.valueOf("@type").equalsIgnoreCase("text"))
                {
                	String strMaxLength = TTKCommon.checkNull(eleDisplay.valueOf("@maxlength"));
                    if(eleDisplay.valueOf("@class").equals(""))
                    {
                        sbfDisplayElement.append(" class=\"textBox textBoxTiny\" ");
                    }//end of if(eleDisplay.valueOf("@class").equals(""))
                    else
                    {
                        sbfDisplayElement.append(" class=\"").append(eleDisplay.valueOf("@class")).append("\" ");
                    }//end of else
                    sbfDisplayElement.append(" value=\"").append(strFieldValue.equals("null")? "":strFieldValue).append("\" ");
                    if(strMaxLength.equals(""))
                    {
                    	sbfDisplayElement.append(" maxlength=\"250\" ");
                    }//end of if(strMaxLength.equals(""))
                    else
                    {
                    	sbfDisplayElement.append(" maxlength=\""+strMaxLength+"\" ");
                    	
                    }//end of else
                    
                    if(strMandatoryID!=null && !strMandatoryID.equals(""))
                    {
                        sbfDisplayElement.append(" id=\""+strMandatoryID+"\" ");
                    }//end of if(strMandatoryID!=null && !strMandatoryID.equals(""))
                    else
                    {
                        sbfDisplayElement.append(" id=\""+eleDisplay.valueOf("@id")+"\" ");
                    }//end of else

                }//end of if(eleDisplay.valueOf("@type").equalsIgnoreCase("text"))
                else if(eleDisplay.valueOf("@type").equalsIgnoreCase("checkbox"))
                {
                    sbfDisplayElement.append(" value=\"").append(eleDisplay.valueOf("@value")).append("\" ");
                    sbfDisplayElement.append(" id=\""+eleDisplay.valueOf("@id")+"\" ");
                    if(eleDisplay.valueOf("@value").equalsIgnoreCase(strFieldValue))
                    {
                        sbfDisplayElement.append(" checked ");
                    }//end of if(eleDisplay.valueOf("@value").equals(strFieldValue))
                }//end of else if(eleDisplay.valueOf("@type").equalsIgnoreCase("checkbox"))
                else if(eleDisplay.valueOf("@type").equalsIgnoreCase("option"))
                {
                    sbfDisplayElement.append(" value=\"").append(eleDisplay.valueOf("@value")).append("\" ");
                    sbfDisplayElement.append(" id=\""+eleDisplay.valueOf("@id")+"\" ");
                    if(eleDisplay.valueOf("@value").equals(strFieldValue))
                    {
                        sbfDisplayElement.append(" selected ");
                    }//end of if(eleDisplay.valueOf("@value").equals(strFieldValue))
                }//end of else if(eleDisplay.valueOf("@type").equalsIgnoreCase("option"))
                sbfDisplayElement.append(eleDisplay.valueOf("@jscall"));
                if(strPermission.equals("") && eleDisplay.valueOf("@disabled").equals("true"))
                {
                    sbfDisplayElement.append(" ").append("readOnly");
                }//end of if(strPermission.equals("") && eleDisplay.valueOf("@disabled").equals("true"))
                else
                {
                    sbfDisplayElement.append(" ").append(strPermission);
                }//end of else
                if(eleDisplay.valueOf("@type").equalsIgnoreCase("text"))
                {
                	sbfDisplayElement.append(" onkeypress = \"javascript:ConvertToUpperCase()\" onblur = \"javascript:toUpperCase(this.value,this.id,this.name)\" >");
                }//end of if(eleDisplay.valueOf("@type").equalsIgnoreCase("text"))
                else {
                	sbfDisplayElement.append(" >");
                }//end of else
            }//end of if(eleDisplay.valueOf("@control").equalsIgnoreCase("input"))

            else if(eleDisplay.valueOf("@control").equalsIgnoreCase("date"))
            {
                sbfDisplayElement.append("<input type=\"text\" class=\"textBox textDate\" ");
                sbfDisplayElement.append(" name=\"").append(strFieldName).append("\" ");
                sbfDisplayElement.append(" value=\"").append(strFieldValue).append("\" ");
                if(strMandatoryID!=null && !strMandatoryID.equals(""))
                {
                    sbfDisplayElement.append(" id=\""+strMandatoryID+"\" ");
                }//end of if(strMandatoryID!=null && !strMandatoryID.equals(""))
                else
                {
                    sbfDisplayElement.append(" id=\""+eleDisplay.valueOf("@id")+"\" ");
                }//end of else
                sbfDisplayElement.append(strPermission);
                sbfDisplayElement.append(" maxlength=\"10\" >");
                if(strPermission.equals(""))
                {
                    sbfDisplayElement.append("<a name=\"CalendarObject").append(strFieldName).append("\" ").append("id=\"CalendarObject").append(strFieldName).append("\" ");
                    sbfDisplayElement.append(" href=\"#\" onClick=\"javascript:show_calendar('CalendarObject").append(strFieldName).append("','forms[1].").append(strFieldName).append("',document.forms[1].").append(strFieldName).append(".value,'',event,148,178);return false;\"");
                    sbfDisplayElement.append(" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"/ttk/images/CalendarIcon.gif\" alt=\"Calendar\"");
                    sbfDisplayElement.append(" name=\"img").append(strFieldName).append("\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a> ");
                }//end of if(strPermission.equals(""))
            }//end of else if(eleDisplay.valueOf("@control").equalsIgnoreCase("date"))

            else if(eleDisplay.valueOf("@control").equalsIgnoreCase("textArea"))
            {
                sbfDisplayElement.append("<textarea name=\"").append(strFieldName).append("\" ");
                if(eleDisplay.valueOf("@class").equals(""))
                {
                    sbfDisplayElement.append(" rows=\"4\" cols=\"70\" ");
                }//end of if(eleDisplay.valueOf("@class").equals(""))
                else
                {
                sbfDisplayElement.append("class=\"textBox textAreaLarge10Lines\" ");
                }

                if(strMandatoryID!=null && !strMandatoryID.equals(""))
                {
                    sbfDisplayElement.append(" id=\""+strMandatoryID+"\" ");
                }//end of if(strMandatoryID!=null && !strMandatoryID.equals(""))
                else
                {
                    sbfDisplayElement.append(" id=\""+eleDisplay.valueOf("@id")+"\" ");
                }//end of else
                sbfDisplayElement.append(eleDisplay.valueOf("@jscall"));
                if(strPermission.equals("") && eleDisplay.valueOf("@disabled").equals("true"))
                {
                    sbfDisplayElement.append(" ").append("readOnly");
                }//end of if(strPermission.equals("") && eleDisplay.valueOf("@disabled").equals("true"))
                else
                {
                    sbfDisplayElement.append(" ").append(strPermission);
                }//end of else
                sbfDisplayElement.append(" >");
                if(strFieldName.equalsIgnoreCase("Others.1"))
                {
                	 sbfDisplayElement.append(strFieldValue.replaceAll("</br>"," "));
                }//end of if(strFieldName.equalsIgnoreCase("Others.1"))
                else
                {
                	// Unni Added for fixing Bug ID 42647
                	if(strFieldValue.indexOf("\r") != -1 || strFieldValue == "\n" || strFieldValue.indexOf("\r\n") != -1){
                 	    sbfDisplayElement.append(strFieldValue.replaceAll("~", "\n"));
                     }//end of if(strFieldValue.indexOf("\r") != -1 || strFieldValue == "\n" || strFieldValue.indexOf("\r\n") != -1){
                     else if(strFieldValue.indexOf("~") != -1)
                     {
                         sbfDisplayElement.append(strFieldValue.replaceAll("~", "\n"));
                     }//end of else if(strFieldValue.indexOf("~") != -1)
                     else
                     {
                    	 sbfDisplayElement.append(strFieldValue);
                     }//end of else
                //sbfDisplayElement.append(strFieldValue);
                // End of Adding
                }//end of else
                sbfDisplayElement.append("</textarea>");
            }//end of else if(eleDisplay.valueOf("@control").equalsIgnoreCase("textArea"))

            else if(eleDisplay.valueOf("@control").equalsIgnoreCase("select"))
            {
                sbfDisplayElement.append("<select name=\"").append(strFieldName).append("\" ");
                if(eleDisplay.valueOf("@source").equalsIgnoreCase("OnlinAccessManager"))
                {
                	if(eleDisplay.valueOf("@style").equalsIgnoreCase("marginTop"))
                	{
                		sbfDisplayElement.append("class=\"selectBoxWeblogin selectBoxLargestWeblogin\" style=\"margin-top:5px;\" ");
                	}//end of if(eleDisplay.valueOf("@style").equalsIgnoreCase("marginTop"))
                	else if(eleDisplay.valueOf("@style").equalsIgnoreCase("marginBottom"))
                	{
                		sbfDisplayElement.append("class=\"selectBoxWeblogin selectBoxLargestWeblogin\" style=\"margin-bottom:5px;\" ");
                	}//end of else
                }//end of if(eleDisplay.valueOf("@source").equalsIgnoreCase("OnlinAccessManager"))
                else
                {
                	sbfDisplayElement.append("class=\"selectBox\" ");
                }//end of else
                sbfDisplayElement.append(eleDisplay.valueOf("@jscall"));
                sbfDisplayElement.append(" ").append(eleDisplay.valueOf("@type"));
                if(strMandatoryID!=null && !strMandatoryID.equals(""))
                {
                    sbfDisplayElement.append(" id=\""+strMandatoryID+"\" ");
                }//end of if(strMandatoryID!=null && !strMandatoryID.equals(""))
                else
                {
                    sbfDisplayElement.append(" id=\""+eleDisplay.valueOf("@id")+"\" ");
                }//end of else

                if(strPermission.equals("") && eleDisplay.valueOf("@disabled").equals("true"))
                {
                    sbfDisplayElement.append(" ").append("Disabled");
                }//end of if(strPermission.equals("") && eleDisplay.valueOf("@disabled").equals("true"))
                else
                {
                    sbfDisplayElement.append(" ").append(strPermission);
                }//end of else
                sbfDisplayElement.append(" >");
                sbfDisplayElement.append(buildOptions(eleDisplay,strFieldValue));
                sbfDisplayElement.append("</select>");
            }//end of else if(eleDisplay.valueOf("@control").equalsIgnoreCase("select"))

            else if(eleDisplay.valueOf("@control").equalsIgnoreCase("image"))  //to display the image
            {
                sbfDisplayElement.append("<a href=\"#\" ");
                sbfDisplayElement.append(eleDisplay.valueOf("@jscall")).append(" >");
                if(eleDisplay.valueOf("@imagepath").equals(""))
                {
                	sbfDisplayElement.append("<img src=\"/ttk/images/EditIcon.gif\" width=\"16\" height=\"16\"");
                }//end of if(eleDisplay.valueOf("@imagepath").equals(""))
                else
                {
                	sbfDisplayElement.append("<img src=\"/ttk/images/"+eleDisplay.valueOf("@imagepath")+"\" width=\"16\" height=\"16\"");
                }//end of else
                sbfDisplayElement.append(" alt=\""+eleDisplay.valueOf("@alt")+"\" border=\"0\" align=\"absmiddle\"></a>");
            }//eleDisplay.valueOf("@control").equalsIgnoreCase("image")

            else if(eleDisplay.valueOf("@control").equalsIgnoreCase("radiogroup"))  //to display the radio buttons
            {
                try
                {
                    String[] strOptText=eleDisplay.valueOf("@optText").split(",");
                    String[] strOptValue=eleDisplay.valueOf("@optVal").split(",");
                    if(strFieldValue.trim().equals("")||strFieldValue.equals("~"))
                    {
                        strFieldValue=eleDisplay.valueOf("@default");
                    }//end of if(strFieldValue.trim().equals("")||strFieldValue.equals("~"))
                    if(strOptText.length==strOptValue.length)
                    {
                        for(int iOptTextCnt=0;iOptTextCnt<strOptText.length;iOptTextCnt++)
                        {
                            sbfDisplayElement.append("<input type=\"radio\" ");
                            sbfDisplayElement.append(" name=\"").append(strFieldName).append("\" ");
                            sbfDisplayElement.append("id=\"").append(strFieldName).append("rad").append(iOptTextCnt).append("\"");
                            sbfDisplayElement.append(" value=\"").append(strOptValue[iOptTextCnt]).append("\" ");

                            if(eleDisplay.valueOf("@class").equals(""))
                            {
                                sbfDisplayElement.append(" class=\"\" ");
                            }//end of if(eleDisplay.valueOf("@class").equals(""))
                            else
                            {
                                sbfDisplayElement.append(" class=\"").append(eleDisplay.valueOf("@class")).append("\" ");
                            }//end of else
                            if(strOptValue[iOptTextCnt].equals(strFieldValue))
                            {
                                sbfDisplayElement.append(" checked ");
                            }//end of if(eleDisplay.valueOf("@value").equals(strFieldValue))
                            sbfDisplayElement.append(" ").append(strPermission).append(" ");
                            sbfDisplayElement.append(eleDisplay.valueOf("@jscall")).append(" /> ");
                            sbfDisplayElement.append("<label for=\"").append(strFieldName).append("rad").
                                                                    append(iOptTextCnt).append("\">");
                            sbfDisplayElement.append(strOptText[iOptTextCnt]);
                            sbfDisplayElement.append("</label>");
                            if(iOptTextCnt!=strOptText.length-1)
                            {
                                sbfDisplayElement.append(" &nbsp;&nbsp; ");
                            }//end of if(iOptTextCnt!=strOptText.length-1)
                        }//end of for(int iOptTextCnt=0;iOptTextCnt<strOptText.length;iOptTextCnt++)
                    }//end of if(strOptText.length==strOptValue.length)
                }//end of try
                catch(ArrayIndexOutOfBoundsException e)
                {
                    log.error("ArrayIndexOutOfBoundsException .....method.........."+eleDisplay.valueOf("@id"));
                }//end of catch(ArrayIndexOutOfBoundsException e)
            }//end of else if(eleDisplay.valueOf("@control").equalsIgnoreCase("radiogroup"))
        }//end of if(eleDisplay!=null)
        else
        {
            log.error("Element to Displayed is null");
        }//end of else
        return sbfDisplayElement.toString();
    }//end of buildDisplayElement(Element eleDisplay,String strFieldName,String strFieldValue)
// Shortfall CR KOC1179
    /**
     * This method is used to build the Element to be displayed from the XML
     * @param eleDisplay Element to be displayed
     * @param strFieldName String field name of the Element
     * @param strFieldValue String current value of the element
     * @param strPermission String to disable element or to makeReadOnly
     * @param strMandatoryID String to give unique id for element if it is a mandatory field
     * @return String
     */
    public static String buildDisplayElementClaims(Element eleDisplay,String strFieldName,String strFieldValue,String strFieldValue1,String strFieldValue2,String strPermission,String strMandatoryID)
    {
        StringBuffer sbfDisplayElement=new StringBuffer();
        if(eleDisplay!=null)
        {
            if(eleDisplay.valueOf("@control").equalsIgnoreCase("input"))
            {
            	  sbfDisplayElement.append("<input type=\"").append(eleDisplay.valueOf("@type")).append("\" ");
                  sbfDisplayElement.append(" name=\"").append(strFieldName).append("\" ");
                 
                    if(eleDisplay.valueOf("@type").equalsIgnoreCase("checkbox"))
                    {
                        sbfDisplayElement.append(" value=\"").append(eleDisplay.valueOf("@value")).append("\" ");
                        sbfDisplayElement.append(" id=\""+eleDisplay.valueOf("@id")+"\" ");
                       
                        if(eleDisplay.valueOf("@id").contains("Check"))
                        {
                        	if("RECEIVED".equalsIgnoreCase(strFieldValue2))
                            {
                                sbfDisplayElement.append(" checked ");
                            }//end of if(eleDisplay.valueOf("@value").equals(strFieldValue))
                        	
                        	if("YES".equalsIgnoreCase(strFieldValue1))
                        	{
                        		sbfDisplayElement.append(" enabled=\"true\"");
                        	}
                        	else 
                        	{
                        		sbfDisplayElement.append(" disabled=\"true\"");
                        	}
                        	
                        }
                        else if(!eleDisplay.valueOf("@id").contains("Check"))
                        {
                        	 if(eleDisplay.valueOf("@value").equalsIgnoreCase(strFieldValue))
                             {
                                 sbfDisplayElement.append(" checked ");
                             }//end of if(eleDisplay.valueOf("@value").equals(strFieldValue))
                        }
                    }//end of else if(eleDisplay.valueOf("@type").equalsIgnoreCase("checkbox"))
                    sbfDisplayElement.append(eleDisplay.valueOf("@jscall"));
                    if(strPermission.equals("") && eleDisplay.valueOf("@disabled").equals("true"))
                    {
                        sbfDisplayElement.append(" ").append("readOnly");
                    }//end of if(strPermission.equals("") && eleDisplay.valueOf("@disabled").equals("true"))
                    else
                    {
                        sbfDisplayElement.append(" ").append(strPermission);
                    }//end of else
                    if(eleDisplay.valueOf("@type").equalsIgnoreCase("text"))
                    {
                    	sbfDisplayElement.append(" onkeypress = \"javascript:ConvertToUpperCase()\" onblur = \"javascript:toUpperCase(this.value,this.id,this.name)\" >");
                    }//end of if(eleDisplay.valueOf("@type").equalsIgnoreCase("text"))
                    else {
                    	sbfDisplayElement.append(" >");
                    }//end of else
                }//end of if(eleDisplay.valueOf("@control").equalsIgnoreCase("input"))
            }//end of if(eleDisplay!=null)
			 else if(eleDisplay.valueOf("@control").equalsIgnoreCase("textArea"))
            {
                sbfDisplayElement.append("<textarea name=\"").append(strFieldName).append("\" ");
                sbfDisplayElement.append("class=\"textBox textAreaLarge10Lines\" ");

                if(strMandatoryID!=null && !strMandatoryID.equals(""))
                {
                    sbfDisplayElement.append(" id=\""+strMandatoryID+"\" ");
                }//end of if(strMandatoryID!=null && !strMandatoryID.equals(""))
                else
                {
                    sbfDisplayElement.append(" id=\""+eleDisplay.valueOf("@id")+"\" ");
                }//end of else
                sbfDisplayElement.append(eleDisplay.valueOf("@jscall"));
                if(strPermission.equals("") && eleDisplay.valueOf("@disabled").equals("true"))
                {
                    sbfDisplayElement.append(" ").append("readOnly");
                }//end of if(strPermission.equals("") && eleDisplay.valueOf("@disabled").equals("true"))
                else
                {
                    sbfDisplayElement.append(" ").append(strPermission);
                }//end of else
                sbfDisplayElement.append(" >");
                if(strFieldName.equalsIgnoreCase("Others.1"))
                {
                	 sbfDisplayElement.append(strFieldValue.replaceAll("</br>"," "));
                }//end of if(strFieldName.equalsIgnoreCase("Others.1"))
                else
                {
                	// Unni Added for fixing Bug ID 42647
                	if(strFieldValue.indexOf("\r") != -1 || strFieldValue == "\n" || strFieldValue.indexOf("\r\n") != -1){
                 	    sbfDisplayElement.append(strFieldValue.replaceAll("~", "\n"));
                     }//end of if(strFieldValue.indexOf("\r") != -1 || strFieldValue == "\n" || strFieldValue.indexOf("\r\n") != -1){
                     else if(strFieldValue.indexOf("~") != -1)
                     {
                         sbfDisplayElement.append(strFieldValue.replaceAll("~", "\n"));
                     }//end of else if(strFieldValue.indexOf("~") != -1)
                     else
                     {
                    	 sbfDisplayElement.append(strFieldValue);
                     }//end of else
                //sbfDisplayElement.append(strFieldValue);
                // End of Adding
                }//end of else
                sbfDisplayElement.append("</textarea>");
            }//end of else if(eleDisplay.valueOf("@control").equalsIgnoreCase("textArea"))
            else
            {
                log.error("Element to Displayed is null");
            }//end of else
            return sbfDisplayElement.toString();
        }//end of buildDisplayElementClaims(Element eleDisplay,String strFieldName,String strFieldValue)
    // End Shortfall CR KOC1179
    /**
     * This method is used to build the options for the select box
     * @param eleDisplay Element, node to be displayed
     * @param strFieldValue String, Current value of the element
     * @return sbfBuildOption String
     */
    private static String buildOptions(Element eleDisplay,String strFieldValue)
    {
        StringBuffer sbfBuildOption=new StringBuffer();
        String strOptValues[] =eleDisplay.valueOf("@optVal").split(",");
        String strOptTexts[] =eleDisplay.valueOf("@optText").split(",");
        if(strOptValues!=null && strOptTexts!=null && strOptValues.length==strOptTexts.length)
        {
            for(int ioptValCnt=0;ioptValCnt<strOptValues.length;ioptValCnt++)
            {
                sbfBuildOption.append("<option value=\"").append(strOptValues[ioptValCnt]).append("\"");
                if(strOptValues[ioptValCnt].equals(strFieldValue))
                {
                    sbfBuildOption.append(" selected ");
                }
                sbfBuildOption.append(">");
                sbfBuildOption.append(strOptTexts[ioptValCnt]).append("</option>");
            }//end of for(int ioptValCnt=0;ioptValCnt<strOptValues.length;ioptValCnt++)
        }//end of if(strOptValues!=null && strOptTexts!=null && strOptValues.length==strOptTexts.length)
        return sbfBuildOption.toString();
    }//end of buildoptions(Element eleDisplay,String strFieldValue)

    /**
     * @Description Sorting an string array and returns the sorted array
     * @author      Unni V Mana
     * @param       String array
     * @param       int lower bound
     * @param       int upper bound
     * @return      sorted string array
     */
    public static String [] sortArray(String a[],int lo0, int hi0)
    {
        int lo = 0;
        int hi = a.length-1;
        String mid;
        if (hi0 > lo0) {
            mid = a[(lo0 + hi0) / 2];
            while (lo <= hi) {
                while ((lo < hi0) && (a[lo].compareTo(mid) < 0))
                {
                    ++lo;
                }//end of while ((lo < hi0) && (a[lo].compareTo(mid) < 0))
                while ((hi > lo0) && (a[hi].compareTo(mid) > 0))
                {
                    --hi;
                }//end of while ((hi > lo0) && (a[hi].compareTo(mid) > 0))
                if (lo <= hi) {
                    String t = a[hi];
                    a[hi] = a[lo];
                    a[lo] = t;
                    ++lo;
                    --hi;
                }//end of if (lo <= hi)
            }//end of  while (lo <= hi)
            if (lo0 < hi)
            {
                sortArray(a, lo0, hi);
            }//end of if (lo0 < hi)
            if (lo < hi0)
            {
                sortArray(a, lo, hi0);
            }//end of if (lo < hi0)
        }//end of  if (hi0 > lo0)
        return a;
    }//end of sortArray(String a[],int lo0, int hi0)

  //Changes on jan 2012 KOC1099
    /**
     * This method is used to build the TextArea
     * @param eleDisplay Element, node to be displayed
     * @param strFieldValue String, Current value of the element
     * @return sbfDisplayElement String
     */

    public static String buildDisplayRemarks(Element eleDisplay,String strFieldName,String strFieldValue,String strPermission,String strMandatoryID)
    {
        StringBuffer sbfDisplayElement=new StringBuffer();
       // String r="remarks";
        if(eleDisplay!=null)
        {
              if(eleDisplay.valueOf("@control").equalsIgnoreCase("textArea"))
            {
               // sbfDisplayElement.append("<textarea name=\"").append(strFieldName).append("\" ");
                    sbfDisplayElement.append("<textarea name=\"").append(strFieldName).append("\" ");
                sbfDisplayElement.append("class=\"textBox textAreaLong\" ");

                if(strMandatoryID!=null && !strMandatoryID.equals(""))
                {
                    sbfDisplayElement.append(" id=\""+strMandatoryID+"\" ");
                }//end of if(strMandatoryID!=null && !strMandatoryID.equals(""))
                else
                {
                    sbfDisplayElement.append(" id=\""+eleDisplay.valueOf("@id")+"\" ");
                }//end of else
                sbfDisplayElement.append(eleDisplay.valueOf("@jscall"));
                                           
                if(strPermission.equals("") || eleDisplay.valueOf("@disabled").equals("true"))
                {
                  
                    sbfDisplayElement.append(" ").append("readonly=\"true\"");
                }//end of if(strPermission.equals("") && eleDisplay.valueOf("@disabled").equals("true"))
              /*  else
                {
                    sbfDisplayElement.append(" ");
                    sbfDisplayElement.append("disabled=\""+strPermission+"\" ");
                    //sbfDisplayElement.append("readonly=\"readonly\" ");
                }//end of else
                
*/             
               // sbfDisplayElement.append(" ").append("maxlength=\"250\"");
                sbfDisplayElement.append(" >");
                if(strFieldName.equalsIgnoreCase("Others.1"))
                {
                   sbfDisplayElement.append(strFieldValue.replaceAll("</br>"," "));
                }//end of if(strFieldName.equalsIgnoreCase("Others.1"))
                else
                {
                  // Unni Added for fixing Bug ID 42647
                  if(strFieldValue.indexOf("\r") != -1 || strFieldValue == "\n" || strFieldValue.indexOf("\r\n") != -1){
                     sbfDisplayElement.append(strFieldValue.replaceAll("~", "\n"));
                     }//end of if(strFieldValue.indexOf("\r") != -1 || strFieldValue == "\n" || strFieldValue.indexOf("\r\n") != -1){
                     else if(strFieldValue.indexOf("~") != -1)
                     {
                         sbfDisplayElement.append(strFieldValue.replaceAll("~", "\n"));
                     }//end of else if(strFieldValue.indexOf("~") != -1)
                     else
                     {
                         sbfDisplayElement.append(strFieldValue);
//                         
                     }//end of else
                //sbfDisplayElement.append(strFieldValue);
                // End of Adding
                }//end of else
                sbfDisplayElement.append("</textarea>");
            }//end of else if(eleDisplay.valueOf("@control").equalsIgnoreCase("textArea"))
        }
        else
        {
            //log.error("Element to Displayed is null");
        }//end of else
        return sbfDisplayElement.toString();
        }
    //Changes on jan 2012 KOC1099
    /**
     * This method get the all states based on passing value
     * @param countryCode String which contains country code
     * @return HashMap<String,String> contains country codes and country names as key and value pair
     */
    public static HashMap<String,String> getStates(String countryCode){
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		HashMap<String,String> hmStates=new  HashMap<String,String>();
		try{
			if(countryCode!=null&&countryCode.length()>0){
		countryCode=(countryCode==null)?"0":countryCode.trim();
	    String query="select state_type_id,state_name from APP.TPA_STATE_CODE where country_id="+countryCode+" ORDER BY state_name";
	   
		   
	    con=ResourceManager.getConnection();
		statement=con.prepareStatement(query);
		  resultSet=statement.executeQuery();
		  
	       while(resultSet.next()){
	    	   hmStates.put(resultSet.getString(1), resultSet.getString(2));
	       }
		   }//if(countryCode!=null&&countryCode.length()>0){
		}catch(Exception exception){
			log.error(exception.getMessage(), exception);
			}
		finally{
			try{
	   			 closeConnectionResource(resultSet,statement,con,"TTKCommon","getStates()");
	   		 }catch(Exception ex){
	   		 }finally{
	   			resultSet=null;
	   			statement=null;
	   			con=null; 
	   		 }			
		 }
	  
        return hmStates;
	}
    
    /**
     * This method get the all areas based on passing value
     * @param stateCode String which contains state code
     * @return HashMap<String,String> contains area codes and area names as key and value pair
     */
    public static HashMap<String,String> getAreas(String stateCode){
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		HashMap<String,String> hmAreas=new  HashMap<String,String>();
		
		stateCode=(stateCode==null)?"0":stateCode.trim();
		String query="SELECT CITY_TYPE_ID,CITY_DESCRIPTION FROM APP.TPA_CITY_CODE WHERE STATE_TYPE_ID='"+stateCode+"' ORDER BY CITY_DESCRIPTION";
	   try{
	    con=ResourceManager.getConnection();
		statement=con.prepareStatement(query);
		  resultSet=statement.executeQuery();
		  
	       while(resultSet.next()){
	    	   hmAreas.put(resultSet.getString(1), resultSet.getString(2));
	       }
		}catch(Exception exception){
			log.error(exception.getMessage(), exception);
			}
		finally{
			try{
   			 closeConnectionResource(resultSet,statement,con,"TTKCommon","getAreas()");
   		 }catch(Exception ex){
   		 }finally{
   			resultSet=null;
   			 statement=null;
   			con=null; 
   		 }		
		 }
	  
        return hmAreas;
	}
    /**
     * This method close the all passed connection resources
     * @param rs ResultSet object
     * @param stm Statement object  
     * @param conn Connection object 
     * @param className String which contain current class name to  display in log file 
     * @param methodName String which contain current method name to  display in log file
     * @throws Exception while closing passed resources throws the exception  
     */
     
    public static void closeConnectionResource(ResultSet rs,Statement stm,Connection conn,String className,String methodName)throws Exception{
   	 try{
   		 if(rs!=null)rs.close();
   	   }catch(Exception ex){
   		 log.error("Error occurred in TTKCommon.closeConnectionResource()  while closing the ResultSet obj at "+className+" of "+methodName,ex);
   		 ex.printStackTrace();
   		 throw ex;
   	 }finally{
   		 try{
   			 if(stm!=null)stm.close();
   		    }catch(Exception ex){
   		   log.error("Error occurred in TTKCommon.closeConnectionResource() while closing the Statement obj at "+className+" of "+methodName,ex);
       		 ex.printStackTrace();
       		 throw ex;
   		    }finally{
   		    	try{
   			    if(conn!=null)conn.close();
   		    	 }catch(Exception ex){
   		    		 log.error("Error occurred in TTKCommon.closeConnectionResource() while closing the Connection obj at "+className+" of "+methodName,ex);
   	        		 ex.printStackTrace();
   	        		 throw ex;
   		    	}
   		 }
   	 }
    }
    
    public static String getNavigation(HttpServletRequest request)
    {
    	UserSecurityProfile userSecurityObj=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    	return (userSecurityObj.getSecurityProfile().getActiveLink()+"->"+userSecurityObj.getSecurityProfile().getActiveSubLink()+"->"+userSecurityObj.getSecurityProfile().getActiveTab());
    }
    
    public static String getConvertDateToString(Date date)
    {
    	String stringDate="";
    	SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
    	stringDate=dateFormat.format(date);
    	return stringDate;
    }
   
    /**
     * This method checks whether user as the access to the given path.
     * @param HttpServletRequest request
     * @param String strPermission path to be checked
     * @return boolean true/false
     */
    public static String isAuthorizedYN(HttpServletRequest request, String strPermission) throws TTKException
    {
    	 SecurityProfile securityProfile=((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile();
    	
    	StringBuffer sbfPermisson=new StringBuffer();
        sbfPermisson.append(securityProfile.getActiveLink()).append(".");
        sbfPermisson.append(securityProfile.getActiveSubLink()).append(".");
        sbfPermisson.append(securityProfile.getActiveTab()).append(".").append(strPermission);
        return securityProfile.isAuthorized(sbfPermisson.toString())?"Y":"N";
    }//end of isAuthorized(HttpServletRequest request)
public static java.sql.Date convertStringSqlDate(String strDate)throws Exception{
    	
    	if(strDate!=null&&strDate.trim().length()>1){
    		return new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(strDate).getTime());
    	}
    	
    	else return null;
    }
public static String splitAndAddBR(String strTarget,int position)throws Exception{

	StringBuilder brString=new StringBuilder();
		
		if(strTarget!=null){
			int strLng=strTarget.length();
			if(strLng>position){
				int i=0;
				while(true){
					
					brString.append(strTarget.substring(i*position,(i+1)*position));
					brString.append("<br>");
					strLng=strLng-position;
					if(strLng<=position){
						brString.append(strTarget.substring((i+1)*position,((i+1)*position)+strLng));
						break;
					}
					i++;
				}
				
				
			}else brString.append(strTarget);
		}
return 	brString.toString();
}
  
    
    public static ArrayList<String[]> extractExcelData(HSSFSheet sheet,int columns) throws Exception{
		   
		   ArrayList<String[]> excelData = new ArrayList<>();
		        Iterator<?> rows     = sheet.rowIterator ();
		        DecimalFormat  formatter = new DecimalFormat("0.000");
		        if(!rows.hasNext ()){
		        	TTKException expTTK = new TTKException();
					expTTK.setMessage("error.xls.file.norows");
					throw expTTK;
		        }
		        rows.next();
		        	String[]rowData=null;
		        
		        while (rows.hasNext()) {
		        	
		        	HSSFRow row =  (HSSFRow) rows.next(); 
		        	
		        	rowData=new String[columns];

		            String temp	=	"";
		            for(short i=0;i<columns;i++)//No Of Excel Cells
		            {
		            	HSSFCell cell	=	row.getCell(i);
		            	
		            	if(cell ==null)temp="";
		            	else 
		            	switch (cell.getCellType ())
		                {
		                    case HSSFCell.CELL_TYPE_NUMERIC :  {
		                        // Date CELL TYPE
		                       
		                        if(HSSFDateUtil.isCellDateFormatted((HSSFCell) cell)){
		 	                    	temp	=	new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue());
		 	                    }else{
		                        	String strValue=formatter.format(cell.getNumericCellValue ());
		                        	if(i==7){
			                    		Date javaDate= HSSFDateUtil.getJavaDate(Double.parseDouble(strValue));
			                    		temp	=	new SimpleDateFormat("dd/MM/yyyy").format(javaDate);
			                    	}else{
		                        	String values[]=strValue.split("[.]");
		                        	if(new Integer(values[1])>0) temp=strValue; 
		                        	else temp=values[0];
		                        }
		                        }
		                        break;
		                    }
		                    case HSSFCell.CELL_TYPE_STRING :
		                    {
		                    	temp=TTKCommon.checkNull(cell.getStringCellValue()).replaceAll("'", "");
		                        break;
		                    }
		                    case HSSFCell.CELL_TYPE_BLANK :
		                    {	
		                    	
		                    	temp="";
		                    	break;
		                    }
		                    default:
		                    {
		                    	temp="";
		                        break;
		                    }
		                } // end switch
		            	
		            	
		            	rowData[i]=temp.trim().replaceAll("[^\\x00-\\x7F]","");
		            	temp	=	"";
		            }//for
		            excelData.add(rowData);
			        
		        } //end while
		       
		        
		        return excelData;
	   }    
    
}//end of class TTKCommon
