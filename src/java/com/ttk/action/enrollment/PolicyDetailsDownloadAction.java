package com.ttk.action.enrollment;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

import com.ttk.common.TTKPropertiesReader;

public class PolicyDetailsDownloadAction extends DownloadAction {

	@Override
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm arg1,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
		String fileName=request.getParameter("fileName");
		fileName=(fileName==null)?"SomeProblemOccuredPleaseContactAdminstrator.pdf":fileName;
		 response.setHeader("Content-Disposition","attachment;filename="+fileName);
		String fileLocation=TTKPropertiesReader.getPropertyValue("path.policyfile");
		File file=new File(fileLocation+fileName);
		file=file.exists()?file:File.createTempFile("SomeProblemOccuredPleaseContactAdminstrator",".pdf");
		StreamInfo streamInfo= new FileStreamInfo("application/pdf", file);
		return streamInfo;
		}catch(Exception exp){	        
	           exp.printStackTrace();
	           return  new FileStreamInfo("application/pdf",File.createTempFile("SomeProblemOccuredPleaseContactAdminstrator",".pdf"));
	        }
	}
}
