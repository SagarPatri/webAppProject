/**
 * @ (#) EmailScheduler.java Nov 20, 2006
 * Project      : TTK HealthCare Services
 * File         : EmailScheduler.java
 * Author       : Balakrishna E
 * Company      : Span Systems Corporation
 * Date Created : Nov 20, 2006
 *
 * @author       :  Balakrishna E
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common;

import javax.sql.*;
import javax.mail.internet.*;
import javax.mail.*;

import java.util.*;
import javax.*;
import javax.naming.*;
import java.text.*;
import javax.mail.search.FlagTerm;
import javax.activation.DataHandler;
//import com.oreilly.servlet.MultipartRequest;
import com.sun.mail.imap.IMAPBodyPart;
import com.sun.mail.util.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.apache.log4j.Logger;
import java.io.File;
import java.util.regex.*;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.mail.Session;
import org.apache.log4j.Logger;
import org.omg.CORBA.Request;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import com.ttk.business.claims.ClaimIntimationSmsManager;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.claims.ClaimIntimationSmsVO;
import com.ttk.common.ClaimIntimationSchedular;
import com.ttk.common.TTKPropertiesReader;

public class ClaimIntimationSchedular implements Job {
	private static Logger log = Logger.getLogger( ClaimIntimationSchedular.class );
	private static final String strClaimIntimationSchedular="claimIntimationSchedular";
	ClaimIntimationSmsManager claimIntimationSmsManager = null;
	ClaimIntimationSmsVO claimIntimationSmsVO=null;
	ArrayList ClaimIntimationSms=new ArrayList();
	public void execute(JobExecutionContext arg0) {
		try
		{   
			ArrayList intimationDetails = this.readClaimIntimationSmsMail(); 
			claimIntimationSmsManager = this.getClaimIntimationSmsManagerObject();
			log.info("Inside run method in ClaimIntimationSchedular");
			claimIntimationSmsManager.saveClaimIntimationSmsDetail(intimationDetails);
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
	private ClaimIntimationSmsManager getClaimIntimationSmsManagerObject() throws TTKException
	{
		ClaimIntimationSmsManager claimIntimationSmsManager = null;
		try
		{
			if(claimIntimationSmsManager == null)
			{
				InitialContext ctx = new InitialContext();
				claimIntimationSmsManager = (ClaimIntimationSmsManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimIntimationSmsManagerBean!com.ttk.business.claims.ClaimIntimationSmsManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp,strClaimIntimationSchedular );
		}//end of catch
		return claimIntimationSmsManager;
	}//end getCommunicationManagerObject()
	
	private ArrayList readClaimIntimationSmsMail()
	{
		ClaimIntimationSmsVO claimIntimationSmsVO=null;
		
		 //URL of Oracle database server
		//DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver()); 
       String url = "jdbc:oracle:thin:@172.16.1.144:1521:TESTTIPS";
		//String url = "jdbc:oracle:thin:@10.1.0.112:1521:devdb";
		    //properties for creating connection to Oracle database
		    Properties props = new Properties();
		    props.setProperty("user", "appln");
		    props.setProperty("password", "ARTYPLKJ789");
		 
		    //creating connection to Oracle database using JDBC
		   
		    
		    	Session session1 = null;
	           //String host = "imap.gmail.com";
	            //String host ="mail.smtp.host";
		    	String strHost = TTKPropertiesReader.getPropertyValue("MAILHOST");
				String strUsername = TTKPropertiesReader.getPropertyValue("MAILUSERNAME");
				String strPassword = TTKPropertiesReader.getPropertyValue("MAILPASSWORD");
				String strMaps = TTKPropertiesReader.getPropertyValue("MAILMAPS");
				String strMainfolder = TTKPropertiesReader.getPropertyValue("MAILFOLDER");
				String strBackupfolder = TTKPropertiesReader.getPropertyValue("MAILBACKUPFOLDER");
	            //String host="10.1.0.3";	    
	            //String username = "ttktips@gmail.com";
	            //String passwoed = "ttk@2101";
	           // String username = "sms.incoming";
	            
	            /*String username = "mailtesting0071@gmail.com";
	            String passwoed = "1@#$%^&*(";*/
	            Properties properties = System.getProperties();
	            //properties.setProperty("mail.store.protocol", "imaps");
	            session1 = Session.getDefaultInstance(properties);
	            String body="",value="";
	             try
	            {
	            	
	            	/*Connection conn = null;
	         		CallableStatement cStmtObject=null;
	         		ResultSet rs1 = null;
	     		    conn = DriverManager.getConnection(url,props);*/
	     		 	 
	            //Store store = session1.getStore("pop3");
	     		Store store = session1.getStore(strMaps);
	            store.connect(strHost, strUsername, strPassword);
	            Folder folder = store.getFolder(strMainfolder);
	            Folder folder1 = store.getFolder(strBackupfolder);
	            HashMap hm= new  HashMap();
	            if (!folder.exists())
	            {
	            System.exit(0);
	            }
	           folder.open(Folder.READ_WRITE);
	           folder1.open(Folder.READ_WRITE);
	           int totalMsg = folder.getMessageCount();
	           
	           int unread = folder.getUnreadMessageCount();
	           
	           int newmsg = folder.getNewMessageCount();
	           
	            Message[] msg = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN),false));
	            folder.copyMessages(msg,folder1);
	            folder.setFlags(msg, new Flags(Flags.Flag.DELETED), true);
	            int cnt=-1;
	            boolean ismail=false;
	            for (int i = 0; i < msg.length; i++)
	            {
	            	claimIntimationSmsVO=new ClaimIntimationSmsVO();
	            cnt++;
	            String read="read";
	            //char ampersand='@';
	            String dot=".";
	            String from = InternetAddress.toString(msg[i].getFrom());
	            //int result=from.indexOf(ampersand);
	            if (from != null)
	            {
					
	            hm.put("From"+cnt+"",from);
	            }
	            int result=from.indexOf(dot);
	            value = from.substring(0, result);
	            claimIntimationSmsVO.setFromMobile(value);
	            
	            /*if	(result== -1)
	            {
	            	 	
	            }
	            else
	            {
	            	
	            }*/
	            /*String[] FieldData = from.split("\\.");
	            
	            for(int j=0;j<FieldData.length;j++)
	            {
	            	
	            	
	            }
	            
	            */
	           
	            String subject = msg[i].getSubject();
	            if (subject != null) {
	            hm.put("Subject"+cnt+"", subject);
	            }
	            java.util.Date sent = msg[i].getSentDate();
	            if (sent != null)
	            {
	            String sent1=sent.toString();
	            hm.put("Sent"+cnt+"", sent1);
	            }
	            String s1="";
	            try
	            {
	            Object content = msg[i].getContent();
	            if (content instanceof String)
	            {      
	            	body = (String)content;
	            claimIntimationSmsVO.setFromContent(body);
	            if(!read.equals("rd")){
	              hm.put("msg"+cnt+"",body);
	              read="rd";
	              }
	            }
	            else //if (content instanceof Multipart)
	            {
	            int f=0;
	            BodyPart bodyPart=null,bp3=null,bpart=null;
	            Multipart multipart = (Multipart)content;
	            String body_content="",body_content11="";
	            String filepath="";
	            for (int x = 0; x < multipart.getCount(); x++)
	            {
	             bodyPart = multipart.getBodyPart(x);
	             if(x==0){
	                         bp3=multipart.getBodyPart(0);
	                         }
	            String disposition = bodyPart.getDisposition();
	            Object o2 = bp3.getContent();
	            if(x==0){
	                  //out.println("OO@@ 22 : "+o2);
	            if (o2 instanceof String) {
	                  String new1=(String)o2;
	                  if(!read.equals("rd")){
	                  hm.put("msg"+cnt+"",new1);
	                  read="rd";
	                  }
	            }
	                  else
	                  {
	               Multipart mp1=(Multipart)o2;
	                  for(int u=0;u<mp1.getCount();u++)
	                  {
	                        bpart=mp1.getBodyPart(u);
	                        Object o3=bpart.getContent();
	                        if(o3 instanceof String){
	                        String new2=(String)o3;
	                        if(!read.equals("rd")){
	                        hm.put("msg"+cnt+"",new2);
	                        read="rd";
	                  }
	                  }
	                  }
	            }
	 
	            }
	            if (disposition != null && (disposition.equalsIgnoreCase(BodyPart.ATTACHMENT))){
	            f++;
	            MimeBodyPart mbp = (MimeBodyPart)bodyPart;
	            DataHandler handler = bodyPart.getDataHandler();
	            String file1=(String)handler.getName();
	            file1=file1.trim();
	            //String uploadDir            =   getServletConfig().getServletContext().getRealPath("/")+"upload_logcall\\";
	            //MultipartRequest multi      =     new MultipartRequest(request, uploadDir, 10*1024*1024);
	            //out.println("path"+ uploadDir);
	            //mbp.saveFile(mbp.getFileName());
	            String uploadFile1=" ";
	            File dir1 = new File(".");
	            String path1=(String)dir1.getCanonicalPath();
	            path1=path1.trim();
	            uploadFile1=path1+"\\"+file1;
	            File cv = new File(uploadFile1);
	             File cv1 = new File("D://");
	             cv.renameTo(new File(cv1, cv.getName()));
	            // filepath=uploadDir+""+file1;
	            hm.put("filepath"+cnt+"",filepath);
	            }
	            else
	            {
	            //hm.put("msg"+cnt+"",bodyPart.getContent());
	            //out.println(bodyPart.getContent());
	            }
	            }
	            }// end of else body instance of
	            }// try ends...
	            catch(Exception e11)
	            {
	                  e11.printStackTrace();
	            }
	            //msg[i].setFlag(Flags.Flag.DELETED, true);
	            ClaimIntimationSms.add(claimIntimationSmsVO);
	            }// end of for loop msg[].length....
	            for(int k=0;k<msg.length;k++)
	            {
	            String insert_msg=(String)hm.get("msg"+k);
	            String insert_sub=(String)hm.get("Subject"+k);
	            String fromid=(String)hm.get("From"+k);
	            String logdate=(String)hm.get("Sent"+k);
	            String filepath22="";
	            filepath22=(String)hm.get("filepath"+k);
	            //out.println("ss1");
	            if(insert_msg!=null){
	            insert_msg=insert_msg.replaceAll("'"," ");
	            }
	            if(fromid!=null)
	            {
	                  fromid=fromid.replaceAll("'"," ");
	            }
	            if(insert_sub!=null){
	            insert_sub=insert_sub.replaceAll("'"," ");
	            }
	            if(logdate!=null){
	                  logdate=logdate.replaceAll("'"," ");
	            }
	           // 
	            String sql = "";
	            //stmt.executeUpdate("insert into mail_details(sender,sent_date,Mail_sub,mail_body,mail_status,Attach_path) values('"+fromid+"' , '"+logdate+"' , '"+insert_sub+"' , '"+insert_msg+"' , 'UR','"+filepath22+"')");
	            
	    		/*try{
	    			String strProc = "{CALL App.data_transfer.p_sms_parse(?,?)}";
	    			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strProc);
	    			cStmtObject.setString(1,body);
	    			cStmtObject.setString(2,value);
	    			cStmtObject.execute();
	    		}
	    		catch(SQLException e)
	    		{
	    			
	    		}
	            }*/
	            folder.close(true);
	            store.close();
	            
	      }
	      
	   }
	             catch(Exception ex)
	             {
	            	 ex.printStackTrace();
	             }
	             return ClaimIntimationSms;
	}
	
}//end of EmailScheduler
