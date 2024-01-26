package com.ttk.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ttk.action.enrollment.MemberUploadAction;
import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.common.exception.TTKException;
import com.ttk.common.messageservices.EmailHelper;
import com.ttk.dto.common.CommunicationOptionVO;

public class ShafafiyaMemberXML implements Job{

	private static Logger log = Logger.getLogger( ShafafiyaMemberXML.class );
	private static final String strEmailScheduler="ShafafiyaMemberXML";
	CommunicationManager communicationManager = null;
	
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		log.info("inside ShafafiyaMemberXML execute");
		  
		  try {
				
				if(new Boolean(TTKPropertiesReader.getPropertyValue("HAAD.EMAIL.RUN.MODE"))){ 
					System.out.println("***********HAAD Mail Triger Schedular Started***********");
				MemberUploadAction memberUploadAction=new MemberUploadAction();
				MemberManager memberManager=this.getMemberManager();
				
				ArrayList<String> authorityInsCompCodeList=memberManager.getAuthorityInsCompCodeList();// getting authority and insCode for configured mail ID's
				ArrayList<String> alData=null;
				
				if(authorityInsCompCodeList!=null&&authorityInsCompCodeList.size()>0)
				{
				for(int i=0;i<authorityInsCompCodeList.size();i++)
				{
					String[] authorityInsCode=authorityInsCompCodeList.get(i).split("\\|");
					alData=new ArrayList<String>();
					alData.add(authorityInsCode[1]);
					alData.add(null);
					alData.add(null);
					alData.add(authorityInsCode[0]);
		        Reader memberxml=memberManager.getMemberXMlDataForMailAttacment(alData);
		       
		        
		        final Calendar cal = Calendar.getInstance();
			    cal.add(Calendar.DATE, -1);
			    System.out.println("memberxml----"+memberxml);
		        if(memberxml!=null) memberUploadAction.memberRegisterXMLSendingEmail(cal,memberxml,new Long("1"),authorityInsCode[0],authorityInsCode[1]);
		       
				}
				}
		        System.out.println("***********HAAD Mail Triger Schedular Ended***********");
				}//end if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.WS.GN.NT.RUN.MODE"))){ 
			}
		  catch (TTKException e) {
				e.printStackTrace();
			}
		  catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	

	
	private MemberManager getMemberManager() throws TTKException
    {
        MemberManager memberManager = null;
        try
        {
            if(memberManager == null)
            {
                InitialContext ctx = new InitialContext();
                memberManager = (MemberManager) ctx.lookup("java:global/TTKServices/business.ejb3/MemberManagerBean!com.ttk.business.enrollment.MemberManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "memberdetail");
        }//end of catch
        return memberManager;
    }//end getMemberManager()
	

}
