package com.ttk.common.webservices;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ttk.action.enrollment.MemberUploadAction;
import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.common.EmailScheduler;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;



public class MemberXMLMailSend implements Job
{
		private static Logger log = Logger.getLogger( EmailScheduler.class );
		private static final String strEmailScheduler="EmailScheduler";
		CommunicationManager communicationManager = null;
		@Override
		public void execute(JobExecutionContext arg0) {
			try
			{
				
				if(new Boolean(TTKPropertiesReader.getPropertyValue("TAKFUL.EMAIL.RUN.MODE"))){ 
					System.out.println(" Mail Triger Schedular Started");
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
		       if(memberxml!=null) memberUploadAction.memberRegisterXMLSendingEmail(cal,memberxml,new Long("1"),authorityInsCode[0],authorityInsCode[1]);
		       
				}
				}
		        System.out.println(" Mail Triger Schedular Ended");
				}//end if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.WS.GN.NT.RUN.MODE"))){ 
			}catch(Exception e){
				//Thread.sleep(10000);
				TTKCommon.logStackTrace(e);
			}//end of catch(Exception e)
		}
		
		/**
		 * Returns the PreAuthSupportManager session object for invoking methods on it.
		 * @return PreAuthSupportManager session object which can be used for method invokation
		 * @exception throws TTKException
		 */
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
