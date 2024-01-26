package com.ttk.common;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.xml.ws.Holder;

import oracle.xdb.XMLType;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ttk.business.webservice.haad.Webservices;
import com.ttk.business.webservice.haad.WebservicesSoap;

public class HaadAlreadyDownloadedFile  implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Connection gnConn=null;
		PreparedStatement statement1=null;
		ResultSet rs=null;
		PreparedStatement statement5=null;
		ResultSet rs5 =null;
		PreparedStatement statement2=null;
		PreparedStatement statement=null;
		PreparedStatement statement4=null;
		System.out.println("Haad Redownlaod started....");
		//for(int i=4;i<=4;i++)
		//{
			//System.out.println("Day------------------"+i);
			try
			{
				Class.forName("oracle.jdbc.OracleDriver");
				 gnConn=DriverManager.getConnection("jdbc:oracle:thin:@172.16.1.82:1521:globalnet","intx","AQUuPWhqS*");
				System.out.println("gnConn="+gnConn);
				int max_download_seq_id=0;
				String max_download_seq_id_query="select nvl(max(DOWNLOAD_SEQ_ID)+1,1) from SURYA.not_uploded_file";
				 statement1= gnConn.prepareStatement(max_download_seq_id_query);
				 rs = statement1.executeQuery(max_download_seq_id_query );
				while (rs.next()) 
				{
					max_download_seq_id= rs.getInt(1);
				}
				
				String yes_date="",cur_mon="";
				String cur_date_qry="select to_char(sysdate-1,'dd/mm/yyyy'),to_char(sysdate-1,'MonYY') from dual";
				 statement5= gnConn.prepareStatement(cur_date_qry);
				rs5 = statement5.executeQuery(cur_date_qry );
				if (rs5.next()) 
				{
					yes_date= rs5.getString(1);
					cur_mon= rs5.getString(2);
				}
				cur_mon="CLM-"+cur_mon;
	
				String receiver_id_query="select to_char(d.down_load_file.extract('/Claim.Submission/Header/ReceiverID/text()').getCLOBVAL()) X FROM surya.not_uploded_file d where d.DOWNLOAD_SEQ_ID= ?";
				statement2= gnConn.prepareStatement(receiver_id_query);
				
				String query="INSERT INTO SURYA.not_uploded_file"
						+ "(DOWNLOAD_SEQ_ID,FILE_ID,FILE_NAME,DOWN_LOAD_DATE,FILE_TYPE,DOWN_LOAD_STATUS,RESULT_TYPE,ADDED_DATE,DOWN_LOAD_FILE,BIFURCATION_YN) "
						+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
				statement= gnConn.prepareStatement(query);
				
				String exist_id_query="select * from surya.un_zip_trans_downlaod  where file_id=?";
				statement4= gnConn.prepareStatement(exist_id_query);
	
				SAXReader saxReader	=	new SAXReader();
				int direction =2;//2 for not download
				String callerLicense=null;
				String ePartner=null;
				int transactionID=2;//2 for claims 
				int transactionStatus=2;
				String transactionFileName=null;

				String transactionFromDate=yes_date+" 00:00:00";//dd/MM/yyyy hh:mm:ss
				System.out.println("transactionFromDate--------------"+transactionFromDate);
				String transactionToDate=yes_date+" 23:59:59";//dd/MM/yyyy hh:mm:ss
				System.out.println("transactionToDate--------------"+transactionToDate);

				int minRecordCount =-1;
				int maxRecordCount =-1;
				Holder<Integer> searchTransactionsResult=new Holder<>();
				Holder<Integer> downloadTransactionFileResult	=	new Holder<Integer>();
				
				Holder<String> errorMessage=new Holder<>();
				Holder<String> xmlTransaction=new Holder<>();
	
	
				String userID="C016globalnet";
				String password="Vidal!@#123";
				//String password="Vidal@1234";

	
				Webservices webservices=new Webservices();
				WebservicesSoap webservicesSoap=webservices.getWebservicesSoap();
				
				
				webservicesSoap.searchTransactions(userID, password, direction, callerLicense, ePartner, transactionID, transactionStatus, transactionFileName, transactionFromDate, transactionToDate, minRecordCount, maxRecordCount, searchTransactionsResult, xmlTransaction, errorMessage);
				String strXmlTransaction=xmlTransaction.value;
						
				if(strXmlTransaction!=null&&strXmlTransaction.length()>1)
				{
					StringReader strReader1=new StringReader(strXmlTransaction);
					Document document1=	saxReader.read(strReader1);
	
					List<Node> nodeList=document1.selectNodes("Files/File");
					if(nodeList!=null&&nodeList.size()>0)
					{
						int noOfFiles=nodeList.size();
						int noOfDownload=0;
						
						for(Node node:nodeList)
						{
							if("True".equalsIgnoreCase(node.valueOf("@IsDownloaded")))
							{
								try
								{
									Holder<byte[]> fileContent=new Holder<byte[]>();
									Holder<String> fileName				=	new Holder<String>(node.valueOf("@FileName"));
									Holder<String> errorMessage2				=	new Holder<String>();
									Holder<String> errorMessage3				=	new Holder<String>();
									Holder<Integer> setTransactionDownloadedResult				=	new Holder<Integer>();
							
									String fileID=node.valueOf("@FileID");
																			
									statement4.setString(1,fileID);
								    ResultSet rs4 = statement4.executeQuery();
								    if (!rs4.next()) 
									{
										webservicesSoap.downloadTransactionFile(userID,password,fileID, downloadTransactionFileResult,fileName, fileContent, errorMessage2);
										if(downloadTransactionFileResult!=null&&downloadTransactionFileResult.value!=null)
										{							
											if(fileContent!=null&&fileContent.value!=null)
											{
												String strFileContent=new String(fileContent.value);
						
												if(strFileContent.contains("ClaimSubmission.xsd")||strFileContent.contains("Claim.Submission"))
												{
													System.out.println(fileID);
																		    
													XMLType poXML = null;
													strFileContent=strFileContent.replaceAll("[^\\x20-\\x7e\n]", "");
													poXML = XMLType.createXML(gnConn, strFileContent);
									
									
												    statement.setInt(1,max_download_seq_id);
												    statement.setString(2,fileID);
												    statement.setObject(3,fileName.value);
												    statement.setObject(4,getCurrentJavaSqlTimestamp());
												    statement.setObject(5,cur_mon);
												    statement.setObject(6,"YES");
												    statement.setObject(7,"0");
												    statement.setObject(8,getCurrentJavaSqlTimestamp());
												    statement.setObject(9,poXML);
												    statement.setObject(10,"N");
												    statement.execute();
								    
								    				String receiver_id="";
								    				statement2.setInt(1,max_download_seq_id);
								    				ResultSet rs1 = statement2.executeQuery();
								    				if (rs1.next()) 
													{
								    					receiver_id= rs1.getString(1);
													}
												    PreparedStatement statement3= gnConn.prepareStatement("update SURYA.not_uploded_file d set d.RECIEVER_ID=? where d.DOWNLOAD_SEQ_ID=? ");
												    statement3.setString(1,receiver_id);
												    statement3.setInt(2,max_download_seq_id);
												    statement3.execute();
												    max_download_seq_id++;
												    statement3.close();
												    rs1.close();
												}
												
												
											}
										}
									}
								    rs4.close();
								}
								catch(Exception e)
								{
									System.out.println(e);
								}
							}
						}
					}
				}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}finally{
				try {
					
					if(statement4!=null)statement4.close();
					if(statement!=null)statement.close();
					if(statement2!=null)statement2.close();
					if(rs5!=null)rs5.close();
					if(statement5!=null)statement5.close();
					if(rs!=null)rs.close();
					if(statement1!=null)statement1.close();
					if(gnConn!=null)gnConn.close();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		//}
		System.out.println("Haad Redownlaod End....");
		
	}
	public static java.sql.Timestamp getCurrentJavaSqlTimestamp() 
	{
	    java.util.Date date = new java.util.Date();
	    return new java.sql.Timestamp(date.getTime());
	 }
}
