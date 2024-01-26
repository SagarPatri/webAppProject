/**
 * @ (#) ReportHelper.java July 8, 2006
 * Project       : TTK HealthCare Services
 * File          : ReportHelper.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : July 8, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.security;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import net.sf.jasperreports.engine.base.JRBaseStaticText;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;


/**
 *  This class builds the method which is help full for report generation.
 */

public class ReportHelper implements Serializable
{
	private static Logger log = Logger.getLogger( ReportHelper.class );
	/**
     * This method builds the Heading Title.
     * @param alHeader ArrayList which contains Header Name.
     * @return StringBuffer object which contains header separated by Tab.
     */
    public static StringBuffer getHeader(ArrayList alHeader) throws TTKException
    {
    	log.info("## INSIDE THE getHeader ##");
    	StringBuffer sbfBuild=new StringBuffer();
    	JRBaseStaticText jbst=null;
    	for(int i=0;i<alHeader.size();i++)
      	{
      		if(alHeader.get(i) instanceof JRBaseStaticText)
      		{
      			jbst=(JRBaseStaticText)alHeader.get(i);
      			sbfBuild.append(jbst.getText()).append("\t");
      		}//end of if(al.get(i) instanceof JRBaseStaticText)
      	}//end of for(int i=0;i<al.size();i++)
      	sbfBuild.append("\n");
      	return sbfBuild;
    }//end of getHeader(ArrayList alHeader)

    /**
     * This method builds the Content.
     * @param alHeader ArrayList which contains Header Name.
     * @return StringBuffer object which contains header separated by Tab.
     */
    public static StringBuffer getContents(ResultSet rs) throws TTKException, SQLException
    {
    	log.info("## INSIDE THE getContents ##");
    	StringBuffer sbfContents=new StringBuffer();
    	if(rs!=null)
      	{
      		while(rs.next())
      		{
      			for(int j=1;j<=rs.getMetaData().getColumnCount();j++)
      			{
      				sbfContents.append(TTKCommon.checkNull(rs.getString(j))).append("\t");
      			}//end of for(int j=1;j<=rs.getMetaData().getColumnCount();j++)
      			sbfContents.append("\n");
      		}//end of while(rs.next())
      	}//end of if(rs!=null)
    	return sbfContents;
    }//end of getContents(ResultSet rs)
}//end of ReportHelper
