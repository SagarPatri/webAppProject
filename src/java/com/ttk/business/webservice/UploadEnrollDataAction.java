package com.ttk.business.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.xml.bind.DatatypeConverter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ProcedureDetailVO;
import com.ttk.dto.webservice.CmaResponce;
import com.ttk.dto.webservice.DhaminiMemberUploadVO;
import com.ttk.dto.webservice.DhaminiUploadVo;
import com.ttk.dto.webservice.Errors;


public class UploadEnrollDataAction implements Job{

	@Override
	public void execute(JobExecutionContext arg0)  
	{
		 MaintenanceManager maintenanceManager = null;
		 try {
			 	if(new Boolean(TTKPropertiesReader.getPropertyValue("OMAN.WS.MU.RUN.MODE"))){ 
				System.out.println("************************** Oman member upload schedular Started (vings) *******************************");
			
				maintenanceManager = this.getMaintenanceManagerObject();
				ProcedureDetailVO memberDataVO=maintenanceManager.getOMANMemberUploadData();
		        try {
				        String result ="";
				        String jsonInput="";
				        String errorMsg="";
				        String transaction_Id ="";
		        		String SuccessFailRes = "";
		        		String Ben_Ins_ID ="";
		        		String flag ="";
		        		int res=0;
				        CmaResponce cmaResponce = null;
				        DhaminiUploadVo DhaminiUploadVo = null;
				        
						jsonInput=memberDataVO.getOmaJsonInput();
						
						ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
						DhaminiUploadVo = mapper.readValue(jsonInput, DhaminiUploadVo.class);
						System.out.println("DB-Input to Json -> :"+DhaminiUploadVo);
						
						if(DhaminiUploadVo != null)
						{
			        		System.out.println("Transaction_ID -> "+DhaminiUploadVo.getTransaction_ID());
			        		System.out.println("Sender_ID -> "+DhaminiUploadVo.getSender_ID());
			        		System.out.println("Data ->"+DhaminiUploadVo.getData());
			        		
			        		ArrayList<DhaminiMemberUploadVO> data=DhaminiUploadVo.getData();
			        		if(data != null && data.size()>0)
			        		{
				        		DhaminiMemberUploadVO dhaminiMemberUploadVO=data.get(0);
				        		Ben_Ins_ID =dhaminiMemberUploadVO.getBeneficiary_Insurance_ID();
				        		flag =dhaminiMemberUploadVO.getFlag();
				        		System.out.println("List Data : Beneficiary_Insurance_ID ="+Ben_Ins_ID);
				        		System.out.println("List Data : Flag ="+flag);
				        		
				        		HttpClient httpClient = HttpClientBuilder.create().build();
								HttpPost httpPost = new HttpPost(TTKPropertiesReader.getPropertyValue("omanMemberUploadSchedular"));
								httpPost.setHeader("Content-type", "application/json");
								StringEntity stringEntity = new StringEntity(jsonInput);
								httpPost.getRequestLine();
								httpPost.setEntity(stringEntity);
								System.out.println("------- before calling spring app ---------");		
								HttpResponse response1 = httpClient.execute(httpPost);
								System.out.println("------- after called spring app ---------");	
								HttpEntity entity = response1.getEntity();
								result = EntityUtils.toString(entity);
								System.out.println("Member uploaded result from CMA -> "+result.toString());
								
							//	if(!result.contains("Invalid payload recieved"))
							//	{
									cmaResponce = mapper.readValue(result, CmaResponce.class);
									System.out.println("CmaResponce class data -> :"+cmaResponce);
						        	
						        	if(cmaResponce != null)
						        	{
						        		transaction_Id = cmaResponce.getTransaction_ID();
						        		SuccessFailRes = cmaResponce.getSuccess();
						        		List<Errors> error = cmaResponce.getErrors();
						        		if(error != null && error.size()>0)
						        		{
							        		System.out.println("Compleate error list :"+error.toString());
							        		Errors cmaError = error.get(0);
							        		errorMsg = cmaError.getIndex()+" | "+cmaError.getError();
							        		System.out.println("only Index ->"+cmaError.getIndex());
							        		System.out.println("only Error ->"+cmaError.getError());
							        		System.out.println("Index + Error ->"+errorMsg);
						        		} // if(error != null && error.size()>0)
						        		res = maintenanceManager.cmaPayLoadBackUp(jsonInput,result,transaction_Id,SuccessFailRes,errorMsg,Ben_Ins_ID,flag);	
						        	}// if(cmaResponce != null)
							//	} // if(!result.contains("Invalid payload recieved")){
				        		
			        		} // if(data != null && data.size()>0)
						}// if(DhaminiUploadVo != null)
			        	
		        } // end of inside try
		        catch ( Exception e) {
					e.printStackTrace();
				}
		        System.out.println("************************** Oman member upload schedular ended (vings) *******************************");
			 }//if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.WS.MU.RUN.MODE"))){ 
		} // end of main try
		catch (TTKException e){
			TTKCommon.logStackTrace(e);
		}
	} // end of execute(JobExecutionContext arg0)
	
 private String  omanMemberRestService(ProcedureDetailVO memVO) throws Exception {
		
	 String result = "";
	 String strSuccessMsg="";
	 MaintenanceManager maintenanceManager = null;

	 String authHeader = "Basic "+DatatypeConverter.printBase64Binary("vidal_health:$r@MSJ7us=".getBytes("UTF-8"));
     HttpPost post = new HttpPost("http://91.132.64.27:8333/updateDhamaniRegistry/");
     post.addHeader("Authorization",authHeader);
     post.setEntity(new StringEntity(memVO.getOmaJsonInput()));
     try (CloseableHttpClient httpClient = HttpClients.createDefault();
          CloseableHttpResponse response = httpClient.execute(post)) {
        
            int status=0;
			result = EntityUtils.toString(response.getEntity());
		    System.out.println("result=====>"+result);
		   maintenanceManager = this.getMaintenanceManagerObject();
	       status = maintenanceManager.saveOmanMemberUploadLogFile(result);
	         if(status>0){
	        	 System.out.println("Response saved successfully");
	        	 strSuccessMsg="true";
	         }
     }
     return strSuccessMsg;
}
	
	private MaintenanceManager getMaintenanceManagerObject() throws TTKException
	{
		MaintenanceManager maintenanceManager = null;
		try
		{
			if(maintenanceManager == null)
			{
				InitialContext ctx = new InitialContext();
				maintenanceManager = (MaintenanceManager) ctx.lookup("java:global/TTKServices/business.ejb3/MaintenanceManagerBean!com.ttk.business.maintenance.MaintenanceManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "OmanNewTransaction");
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()	

}
