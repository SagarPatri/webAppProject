package com.ttk.common.webservices;

import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ttk.action.enrollment.MemberUploadAction;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
public class UploadNewMemberAction implements Job {
	private static Logger logger=Logger.getLogger(UploadNewMemberAction.class);
	@Override
	public void execute(JobExecutionContext arg0)  
	{
		try {
			if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.WS.MU.RUN.MODE"))){ 
			 MemberUploadAction memberUploadAction=new MemberUploadAction();
			 MemberManager memberManager=this.getMemberManager();
			 ArrayList<String> inCmpnyCnt=memberManager.getInsurenceCompanyCount();
			 if(inCmpnyCnt!=null)
			 {
			 for(int i=0;i<inCmpnyCnt.size();i++)
			 {
				 ArrayList<String> alData=new ArrayList<String>();
				 alData.add(inCmpnyCnt.get(i));
		       List<Object[]> memberList=memberManager.getNewMemberUploadXML(alData);
		       for (Object[] objects : memberList) {
		    	   try {
		    		   String type=(String) objects[0];
		    		   Reader memberxml=(Reader) objects[1];
		    		   String xmlSeqId=(String) objects[2];
//			        	 Reader memberxml=new FileReader("C:\\Users\\siva.lingam\\Desktop\\memberregistertestnew.xml");
			        	SimpleDateFormat sdate=new SimpleDateFormat("ddMMyy");
				        final Calendar cal = Calendar.getInstance();
				        cal.add(Calendar.DATE, -1);
			        	if(memberxml!=null){
			        		int count=("NEW".equalsIgnoreCase(type)?(memberUploadAction.newUploadActiveMemberToDHPO(memberxml,"schedular",sdate.format(cal.getTime()),inCmpnyCnt.get(i),xmlSeqId)):(memberUploadAction.updateNewMemberToDHPO(memberxml,"schedular",sdate.format(cal.getTime()),inCmpnyCnt.get(i),xmlSeqId)));
			        		logger.info("count::"+count);
			        	} 
			        		
					} catch (IOException e) {
						e.printStackTrace();
					}
		       }     
		    } 
			 }
		}//if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.WS.MU.RUN.MODE"))){ 
		}
		catch (TTKException e) 
		   {
			TTKCommon.logStackTrace(e);
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
