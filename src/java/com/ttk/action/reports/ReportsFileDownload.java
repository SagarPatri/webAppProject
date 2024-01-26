/**
 * @ (#) FileDownload.java Nov2, 2012
 * Project      : TTK HealthCare Services
 * File         : FileDownload.java
 * Author       : PRAVEEN
 * Company      : RCS
 * Date Created : October 17, 2012
 *
 * @author       : PRAVEEN
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.reports;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import com.ttk.common.TTKPropertiesReader;

import java.io.FileOutputStream;
/*This Method is used to download the IBMReports into Excel Format
 * this method supports Excel download of more than lakh records.
 */

public class ReportsFileDownload extends DownloadAction{
	 protected StreamInfo getStreamInfo(ActionMapping mapping,  ActionForm form,   HttpServletRequest request,  HttpServletResponse response) throws Exception {
		 
		 	String File = (String)request.getAttribute("File");
		 	String fileDest = (String)request.getAttribute("FileDest");
		 	response.setHeader("Content-disposition",   "attachment; filename=" + File);
			String contentType = "application/vnd.ms-excel";
			File file = new File(fileDest);
			return new FileStreamInfo(contentType, file);



	  }
	}
