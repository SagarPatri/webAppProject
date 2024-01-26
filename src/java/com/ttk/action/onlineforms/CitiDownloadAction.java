package com.ttk.action.onlineforms;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DownloadAction;

import com.ttk.action.tree.TreeData;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberAddressVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.enrollment.MemberVO;

import formdef.plugin.util.FormUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

public class CitiDownloadAction extends DownloadAction {

    protected StreamInfo getStreamInfo(ActionMapping mapping, 
                                       ActionForm form,
                                       HttpServletRequest request, 
                                       HttpServletResponse response)
            throws Exception {
        
        // Download a "jpeg" file - gets the file name from the
        // Action Mapping's parameter
        String contentType         = "application/vnd.ms-excel";
        String path =mapping.getParameter();
        	//"//10.1.1.23//$E:\\Books\\Book1.XLS";
        //String path ="//10.1.1.23//Book1.XLS";
//10.1.9.100//rcs//daily-activity//Book1.XLS" ;           = 
        	//mapping.getParameter();
        
        ServletContext application = servlet.getServletContext();
        
        
        return new ResourceStreamInfo(contentType, application, path);
        
    }

}

/*public class CitiDownloadAction extends DownloadAction{

	
	protected StreamInfo getStreamInfo(ActionMapping arg0, ActionForm arg1,
			HttpServletRequest arg2, HttpServletResponse arg3) throws Exception {
		
		String contenttye="application/vnd.ms-excel";
		File file=new File("\\ttk\\citiexcel\\KOC1131 Testing.xlsx");
		return new FileStreamInfo(contenttye,file);
	}

	
}*/