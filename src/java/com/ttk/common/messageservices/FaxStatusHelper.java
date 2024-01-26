/**
 * @ (#) FaxStatusHelper.java 28th Jan 2008
 * Project      : TTK HealthCare Services
 * File         : FaxStatusHelper.java
 * Author       : Balakrishna.E
 * Company      : Span Systems Corporation
 * Date Created : 28th Jan 2008
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.common.messageservices;

import gnu.hylafax.HylaFAXClient;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.common.CommunicationDAOImpl;
import com.ttk.dto.common.CommunicationOptionVO;

public class FaxStatusHelper {
	
	private static Logger log = Logger.getLogger( FaxStatusHelper.class ); // Getting Logger for this Class file
	
	/**
	 * This method the fax status like delivered or not.
	 * @exception throws TTKException
	 */
	public void faxStatus() throws Exception
	{
		HylaFAXClient hylaFaxClient= new HylaFAXClient();
		String strFaxHost = TTKPropertiesReader.getPropertyValue("FAXHOST");
		String strFaxUser = TTKPropertiesReader.getPropertyValue("FAXUSER");
		String strFaxPassword = TTKPropertiesReader.getPropertyValue("FAXPASSWORD");
		int iPort = Integer.parseInt(TTKPropertiesReader.getPropertyValue("HYLAFAXPORT"));
		String str[];
		CommunicationOptionVO communicationOptionVO = null;
		try
		{
			hylaFaxClient.open(strFaxHost,iPort);
			if(hylaFaxClient.user(strFaxUser))
			{
				hylaFaxClient.pass(strFaxPassword);
			}//end of if
			ArrayList alFaxStatus = null;
			ArrayList<String> alFaxStatusInfo = null;
			alFaxStatus = new ArrayList();
			CommunicationDAOImpl communicationDAOImpl = new CommunicationDAOImpl();
			alFaxStatus = communicationDAOImpl.getProcGetPendingFax();
			communicationOptionVO = new CommunicationOptionVO();
			Vector lst = null;
			String strGeneralTypeID = "";
//			String strMailStatus ="";
//			String strMessageJobID="";
			for(int l=0; l<alFaxStatus.size(); l++)
			{
				communicationOptionVO = (CommunicationOptionVO)alFaxStatus.get(l);
				String strMsgJobID = communicationOptionVO.getMsgJobID();
				String strRcptSeqID = communicationOptionVO.getDestMsgRcptSeqId();
				
				str =hylaFaxClient.getList("doneq").lastElement().toString().split(" ");
				int intValue = Integer.parseInt(str[0]);
				int intJobID = Integer.parseInt(strMsgJobID);
				log.info("done queue Job ID is :"+intValue);
				log.info("Resultset Job ID is  :"+intJobID);
				lst = hylaFaxClient.getList("doneq");
				String[][] data = new String[lst.size()][];
				for (int i = 0; i < lst.size(); i++)
				{
					data[i] = ((String)lst.get(i)).split("\\|");
					for (int j =0 ;j<data[i].length;j++){
						// log.info("data["+i+"]["+j+"]"+ (data[i][j]));
						String strRemarks = "";
						str= ((String)data[i][j]).split(" ");
						if(strMsgJobID.equals(str[0]))
						{
							if(str[2].equals("D"))
							{
								strGeneralTypeID = "MDL";
								strRemarks ="Fax delivered successfully";
							}//end of if(str[2].equals("D"))
							else if(str[2].equals("F"))
							{
								strRemarks = "";
								strGeneralTypeID = "MFL";
								for(int icount=14; icount<str.length; icount++)
								{
									if(!str[icount].equals("") && icount > 14 )
									{
										strRemarks = strRemarks+str[icount]+" ";
									}//if(!str[icount].equals("") && icount > 14 )
								}//end of for(int icount=14; icount<str.length; icount++)
							}//end of else if(str[2].equals("F"))
							else
							{
								log.info("record doesn't have the status");
							}//end of else	
							alFaxStatusInfo = new ArrayList<String>();
							alFaxStatusInfo.add(strRcptSeqID);
							alFaxStatusInfo.add("");
							alFaxStatusInfo.add("");
							alFaxStatusInfo.add(strRemarks);
							alFaxStatusInfo.add(strGeneralTypeID);
//							communicationDAOImpl.messageStatusChangePr(strRcptSeqID, strMailStatus, strMessageJobID, strRemarks, strGeneralTypeID);
							communicationDAOImpl.messageStatusChangePr(alFaxStatusInfo);
							//  FaxMessageStatus
						}//end of if(strMsgJobID.equals(str[0]))													
					}//end of for (int j =0 ;j<data[i].length;j++)
				}//end of for (int i = 0; i < lst.size(); i++) 	
			}//end of for(int l=0; l<alFaxStatus.size(); l++)
		}//end of try
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TTKException(ex, "error.hylafaxserver");
		}//end of catch (Exception ex)
		finally
		{
			if (hylaFaxClient == null) {
				return;
			}//end of if (hylaFaxClient == null)
			try {
				if(hylaFaxClient !=null)
				{
					hylaFaxClient.quit();
				}//end of if(hylaFaxClient !=null)
			}//end of try
			catch (Exception ex) {
				throw new TTKException(ex, "error.hylafaxserver");
			}//end of catch (Exception ex)	
			finally
			{
				hylaFaxClient = null;				
			}//end of finally
		}//end of finally
	}//end of sendFax(CommunicationOptionVO cOption)
	
}//end of FaxStatusHelper()
