
package com.ttk.dao.impl.common;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class TestClient{
	private static Logger log = Logger.getLogger(TestClient.class );
	final String ICD = ":Get_ICD:";
	final String resultICD = ":ICD_res:";
	final int OPSIZE = 17;
	int totSize = 0;
	Socket s = null;
	String diseaseCode = null;
	int diseaseLen = 0;
	String SIZEOPCODE = null;
	String result = "";
	String ip = "";
	ArrayList<Object> compData;
	String [][] data;
	
	
	public TestClient(){
		
//		log.info("ip = ");
		if ( ip.equals("") )
			this.ip = getIP();
		//this.ip = "172.16.1.71";
//		log.info("ip = " + ip);
	}
	
	public String[][] getICD(String disease){
		diseaseCode = disease.toUpperCase();
		diseaseLen = diseaseCode.length();
		String tempd = diseaseLen + "";
		
		totSize = OPSIZE + diseaseLen + tempd.length();
		totSizeOPCODE();
		
		try{
			s = new Socket(ip, 12352);
		}
		catch(UnknownHostException uhe){
			log.info("Unknown Host :" + ip);
			s = null;
		}
		catch(IOException ioe){
			log.info("Cant connect to server " + ip + " .Make sure it is running.");
			s = null;
		}
		
		if(s == null)
		{
			System.exit(-1);
		}//end of if(s == null)
		
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		byte[] readBuf = new byte[8000];
		byte[] icdBuf = new byte[8000];
		byte[] closeBuf = new byte[8000];
		int counter = 0;
		
		try{
			in =  new BufferedInputStream(s.getInputStream());
			out = new BufferedOutputStream(s.getOutputStream());
			
			//            out.println("Hello");
			//            out.flush();
			
			//            try{
			//            	Thread.sleep(5000);
			//            }
			//            catch(Exception e){
			//            }
			
			int cnt = 0;
			String svrstr = "";
			
			try{
				cnt = in.read(readBuf,0,8000);
//				log.info("cnt == " + cnt);		    	
			}//try
			catch(java.io.InterruptedIOException e){
			}//catch
			
			if (cnt > 0){
				svrstr = "";
				for(int t = 0;t < cnt;t++){
					svrstr = svrstr + (char)readBuf[(int)t];
				}
			}
			
//			log.info("svrstr = " + svrstr);
			String icd = totSize + SIZEOPCODE + ICD + diseaseLen + ":" + diseaseCode;	//size:data
//			log.info("ICD = " + icd);
			int icdcnt = icd.length();
			icdBuf = icd.getBytes();
//			log.info(icdcnt);
			out.write(icdBuf,0,icdcnt);
			out.flush();
			
			boolean runFlag = true;
			svrstr = "";
			while (runFlag){
				try{
					cnt = in.read(readBuf,0,8000);
				}//try
				catch(java.io.InterruptedIOException e){
					e.printStackTrace();
				}//catch
//				log.info("count = " + cnt);
				if (cnt > 0){
					for(int t = 0;t < cnt;t++){
						svrstr = svrstr + (char)readBuf[(int)t];
					}
				}
//				log.info("svrstr = " + svrstr);
				if ( svrstr.indexOf(resultICD) != -1 )
				{
					result += svrstr.substring(svrstr.indexOf(resultICD) + resultICD.length(),svrstr.length() );
				}//end of if ( svrstr.indexOf(resultICD) != -1 )
				if ( svrstr.contains(":Done") )
				{
					runFlag = false;
				}//end of if ( svrstr.contains(":Done") )
				
				try{
					counter++;
					Thread.sleep(500);
				}
				catch(Exception e){}
				
				if ( counter > 20 ){
					runFlag = false;
				}
//				log.info("svrstr = " + svrstr);
//				log.info("result = " + result);
				
			}//while
			
			
//			log.info(svrstr.indexOf(resultICD));
			
			String close = "14     :CLOSE:";	//size:data
			closeBuf = close.getBytes();
			icdcnt = close.length();
			out.write(closeBuf,0,icdcnt);
			out.flush();
			
			try{
				Thread.sleep(300);
			}
			catch(Exception e){}
			
			if ( result == null || result.trim().equals("") )
			{
				compData = null;
			}//end of if ( result == null || result.trim().equals("") )
			else{
//				log.info("Result = " + result);
				parseFile(result);
			}
			out.close();
			in.close();
			s.close();
			result = ""; 
		}
		catch(IOException ioe){
			log.info("Exception during communication. Server probably closed connection.");
		}
//		finally{
//		}*/
//		log.info("compData = " + compData);
		
		if ( compData != null ){
			
			data = new String[compData.size()/4][4];
			
			for ( int i=1,k=0,z=0;i<=compData.size();i++,z++ ){
				if ( z == 4 ){
					z=0;
					k++;
				}
				data[k][z] = compData.get(i - 1).toString();
				//			log.info("data[" + k + "][" + z + "] = " +  compData.get(i - 1).toString());
			}
		}
		return data;
	}
	
	int sendData(byte[] writebuf,int len){
		int DSi,DSj;
		BufferedOutputStream DSoutput;
		String DSsendstr = "";
		byte[] DSwritebuf = null;
		
		try
		{
			len = len + 7;
			DSwritebuf = new byte[len];
			
			DSsendstr = DSsendstr + len;
			DSj = DSsendstr.length();
			for(DSi = 0;DSi <DSj;DSi++)
			{
				char DSch = DSsendstr.charAt(DSi);
				DSwritebuf[DSi] = (byte)DSch;
			}//for
			for(DSi=DSj;DSi<7;DSi++)
			{
				DSwritebuf[DSi] = 32;
			}//for
			for(DSj = 0;DSj < len - 7;DSj++)
			{
				DSwritebuf[DSi] = writebuf[DSj];
				DSi++;
			}//for
			DSoutput = new BufferedOutputStream(s.getOutputStream());
//			System.out.p[rintln();
			DSoutput.write(DSwritebuf,0,len);
			DSoutput.flush();
			DSoutput = null;
		}//try
		catch(IOException e)
		{
//			write_log("An Exception Error Was Caught When Trying To Send Data Over The Socket");
			e.printStackTrace();
			DSoutput = null;
//			return TestClient.SOCKET_ERROR;
			
		}//catch
		return 1;
	}
	
	public void totSizeOPCODE(){
		String tot = totSize + "";
		int len = tot.length();
		
		if ( len == 1 ){
			SIZEOPCODE = "      ";
		}
		else if ( len == 2 ){
			SIZEOPCODE = "     ";
		}
		else if ( len == 3 ){
			SIZEOPCODE = "    ";
		}
		else if ( len == 4 ){
			SIZEOPCODE = "   ";
		}
		else if ( len == 5 ){
			SIZEOPCODE = "  ";
		}
		else if ( len == 6 ){
			SIZEOPCODE = " ";
		}
		else if ( len == 7 ){
			SIZEOPCODE = "";
		}
		
		
	}//totSizeOPCODE
	
	private ArrayList parseFile(String data){
//		log.info("DATA \n" + data);
//		int size = Integer.parseInt(data.substring( 0,data.indexOf(":") ) );
//		data = data.substring(data.indexOf(":") + 1,data.length());
		compData = new ArrayList<Object>();
//		int row = 0;
		
//		if ( row == 0 )
//		row = size;
		
//		log.info("Result == " + data);
		
		int idx = 0;
		int prvIdx = -1;
		int byteRead = 0;
		String strdata = null;
		
		int c = 0;
		
		try{
			while ( true ){
				
				if ( prvIdx == -1 && idx != 0){
					break;
				}
				
				idx = data.indexOf(":",prvIdx+1);
				byteRead = Integer.parseInt(data.substring(prvIdx+1,idx).trim());
				strdata = data.substring(idx+1,idx+byteRead+1);
				if ( !strdata.startsWith("ICD_done") )
				{
					compData.add(strdata);
				}//end of if ( !strdata.startsWith("ICD_done") )
				
				
				idx += byteRead;
				prvIdx = data.indexOf(":",idx+1);
				c++;
			}//while
			log.info(compData);			
		}
		catch(java.lang.NumberFormatException nfe){
			nfe.printStackTrace();
			return null;
		}
		return compData;
	}//parseFile
	
//	public static void main(String[] args)throws Exception
//	{
//		TestClient tc = new TestClient();
//		tc.getICD("KIDNEY");
//		tc.getICD("LIVER");
//		tc.getICD("CATARACT");
//		tc.getICD("SENILE CATARACT");
//		tc.getICD("HEART");
//		tc.getICD("EYE");
//		if(args.length == 0){
//		log.info("Usage : TestClient <serverName>");
//		return;
//		}
		
//		for ( int i=0;i<90;i++ )
//		{
//		Thread.sleep(100);
//		openThread("kidney",i);
//		
//		}
		
//	}//main
	
//	private static void openThread(String desc,final int nu)
//	{
//		Thread th = new Thread()
//		{
//			public void run()
//			{
//				long stime = System.currentTimeMillis();
//				TestClient tc = new TestClient();
//				String[][] abc = tc.getICD("heart");
////				try	{sleep(5000); }	catch(Exception e){ }
//				log.info("completed thread " + nu + " in " + ((System.currentTimeMillis() - stime)/1000) + " seconds");
//			}
//		};
//		
//		th.start();
//	}//openThread
	
	public String getIP(){
		String line = "";
		try{
			File fr = new File("..");
			String curPath = fr.getCanonicalPath();
			curPath+=File.separator;
//			log.info("curPath = " + curPath);
			java.io.RandomAccessFile read = new RandomAccessFile(new java.io.File("c:/" + "ip.txt"),"r");
			
			while ( (line=read.readLine())!=null ){
				line = line.trim();
				
				if( line.equals("") )
				{
					continue;
				}//end of if( line.equals("") )
				else
				{
					return line;
				}//end of else
			}
		}//try
		catch(Exception e){
			e.printStackTrace();
		}//catch
		return line;
	}//getIP
}