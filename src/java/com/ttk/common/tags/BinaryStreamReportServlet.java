/**
 * @ (#) BinaryStreamReportServlet.java July 6, 2006
 * Project       : TTK HealthCare Services
 * File          : BinaryStreamReportServlet.java
 * Author        : Balaji C R B
 * Company       : Span Systems Corporation
 * Date Created  : July 6, 2006
 *
 * @author       : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.common.tags;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class BinaryStreamReportServlet extends HttpServlet
{
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger( BinaryStreamReportServlet.class );
	
	public void service(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException
	{
		try{
			log.debug("inside BinaryStreamReportServlet service method");
			String strReportType = req.getParameter("reportType");
			if("PDF".equals(strReportType))
			{
				//log.info("report type is pdf");
				res.setContentType("application/pdf");
			}//end of if("PDF".equals(strReportType))
			else if("EXCEL".equals(strReportType))
			{
				//log.info("report type is xls");
				res.setContentType("application/vnd.ms-excel");
			}//end of else if("EXCEL".equals(strReportType))
			else if("TXT".equals(strReportType))
			{
				//log.info("report type is txt");
				res.setContentType("text/plain");
				res.addHeader("Content-Disposition","attachment; filename=claims.txt");
			}//end of else if("TXT".equals(strReportType)		
			ByteArrayOutputStream baos = null;
			baos = (ByteArrayOutputStream)req.getAttribute("boas");
			OutputStream sos = res.getOutputStream();
			baos.writeTo(sos);	
		}//end of try
		catch(IOException ioExp)
		{
			ioExp.printStackTrace();
		}//end of catch(IOException ioExp)
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch(Exception e)		
	}//end of service(HttpServletRequest req, HttpServletResponse res)
}//end of BinaryStreamReportServlet