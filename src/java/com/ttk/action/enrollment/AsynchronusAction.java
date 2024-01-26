/**
 * @ (#) EnrollmentAction.java Feb 10, 2006
 * Project 		: TTK HealthCare Services
 * File 		: EnrollmentAction.java
 * Author 		: Pradeep R
 * Company 		: Span Systems Corporation
 * Date Created : Feb 10, 2006
 *
 * @author 		: Pradeep R
 * Modified by 	: Raghavendra T M
 * Modified date: July 30, 2007
 * Reason 		: doViewAccInfoEnrollment method added for accountinfo 
 */

package com.ttk.action.enrollment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.ResourceManager;

/**
 * This class is reusable for adding enrollment information in corporate and non corporate policies in enrollment flow.
 * This class also provides option for deleting the selected enrollment.
 */

public class AsynchronusAction extends DispatchAction {
	private static Logger log = Logger.getLogger( AsynchronusAction.class );
	public ActionForward getEmirateIdDescription(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	PrintWriter	writer= response.getWriter();
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet rs=null;
		try{ 
		con=ResourceManager.getConnection();
		statement=con.prepareStatement("select b.detail_number,b.detail_desc from app.tpa_mouse_over_details b  where detail_number= ?");		
	    String eId=(String)request.getParameter("eId");
	         eId=(eId==null)?null:eId.trim();	    
	    
	      statement.setString(1,eId);
	      rs=statement.executeQuery();
	      if(rs.next())writer.println(rs.getString(2));
	      else writer.println("NON");
	      writer.flush();	    
		}catch(Exception exception){
			log.error(exception.getMessage(), exception);			
			}
		finally{
			if(rs!=null)rs.close();
			if(statement!=null)statement.close();
			if(con!=null)con.close();
		}		
        return null;
    }
	public ActionForward getProviderInvoiceNO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	PrintWriter	writer= response.getWriter();
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet rs=null;
		try{
			String claimSeqID=(String)request.getParameter("claimSeqID");
			claimSeqID=(claimSeqID==null)?null:claimSeqID.trim();
		con=ResourceManager.getConnection();
		statement=con.prepareStatement("SELECT INVOICE_NUMBER FROM APP.CLM_AUTHORIZATION_DETAILS WHERE CLAIM_SEQ_ID="+claimSeqID);		
	      rs=statement.executeQuery();
	      if(rs.next()){
	    	  writer.print(rs.getString(1));
	      }
	      else writer.print("");
	      
	      writer.flush();	    
		}catch(Exception exception){
			log.error(exception.getMessage(), exception);			
			}
		finally{
			if(rs!=null)rs.close();
			if(statement!=null)statement.close();
			if(con!=null)con.close();
		}		
        return null;
    }
	
	public ActionForward getProviderNames(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	PrintWriter	writer= response.getWriter();
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet rs=null;
		try{
		con=ResourceManager.getConnection();
		statement=con.prepareStatement("SELECT HOSP_NAME||' ('||HOSP_LICENC_NUMB||')' FROM TPA_HOSP_INFO I JOIN TPA_HOSP_EMPANEL_STATUS S ON (I.HOSP_SEQ_ID=S.HOSP_SEQ_ID) WHERE S.EMPANEL_STATUS_TYPE_ID='EMP'");		
	      rs=statement.executeQuery();
	      while(rs.next())
	      writer.println(rs.getString(1));	      
	     //String data[]={"AAAA","BBBB","CCCC","DDDD"};
	     //for(String dt:data)writer.println(dt);
	      writer.flush();	    
		}catch(Exception exception){
			log.error(exception.getMessage(), exception);			
		}
		finally{
			if(rs!=null)rs.close();
			if(statement!=null)statement.close();			
			if(con!=null)con.close();			
			if(writer!=null)writer.close();
			if(statement!=null)statement.close();
			if(con!=null)con.close();
		}		
        return null;
    }
	public ActionForward validateMember(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	PrintWriter	writer= response.getWriter();
		Connection con=null;
		CallableStatement statement=null;
		ResultSet resultSet=null;
		String status;
		try{
	       String memberId=request.getParameter("memberId");
	       memberId=(memberId==null)?"":memberId.trim();
	     con=ResourceManager.getConnection();     
	     statement=con.prepareCall("{CALL AUTHORIZATION_PKG.SELECT_MEMBER(?,?)}");    
	     statement.setString(1,memberId);
	     statement.registerOutParameter(2,OracleTypes.CURSOR);
		statement.execute();
		
		 resultSet=(ResultSet)statement.getObject(2);	
		if(resultSet==null||!resultSet.next()||resultSet.getLong("MEMBER_SEQ_ID")==0)status="MINE";		 
		else status="SUCCE";
	     
	      writer.println(status);  
	      writer.flush();	    
		}catch(Exception exception){
			log.error(exception.getMessage(), exception);
			 status="EO";			
			}finally{
				if(resultSet!=null)resultSet.close();
				if(statement!=null)statement.close();				
			if(con!=null)con.close();			
			if(writer!=null)writer.close();
		}		
        return null;
    }
	
	public ActionForward getClinicianNames(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	PrintWriter	writer= response.getWriter();
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet rs=null;
		try{
		con=ResourceManager.getConnection();
		statement=con.prepareStatement("SELECT CONTACT_NAME ||' ('||PROFESSIONAL_ID||')' AS CONTACT_NAME FROM TPA_HOSP_PROFESSIONALS");		
	 
	      rs=statement.executeQuery();
	      while(rs.next())  writer.println(rs.getString(1));	    	  
	     
	      writer.flush();	    
		}catch(Exception exception){
			log.error(exception.getMessage(), exception);
			}
		finally{
			if(rs!=null)rs.close();
			if(statement!=null)statement.close();			
			if(con!=null)con.close();			
			if(writer!=null)writer.close();
		}		
        return null;
    }
	
	
	
	public ActionForward getStates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	PrintWriter	writer= response.getWriter();
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
						
	    String countryId=(String)request.getParameter("countryId");
	    countryId=(countryId==null)?"0":countryId.trim();
	    //System.out.println("countryid..."+countryId);
	    String options="";
	    String query="select state_type_id,state_name from app.TPA_STATE_CODE where country_id="+countryId+" ORDER BY state_name";
	   try{ 	
	    con=ResourceManager.getConnection();
	    //System.out.println("query................"+query);
		statement=con.prepareStatement(query);
		  resultSet=statement.executeQuery();
		    HashMap<String,String> providerStates=new  HashMap<String,String>();
	       while(resultSet.next()){
	    	   String id=resultSet.getString(1);
	    	   String desc=resultSet.getString(2);
	    	   providerStates.put(id, desc);
	    	   options+=id+"@"+desc+"&";
	       }
	       request.getSession().setAttribute("providerStates", providerStates);
		}catch(Exception exception){
			log.error(exception.getMessage(), exception);
			}
		finally{
			if(resultSet!=null)resultSet.close();
			if(statement!=null)statement.close();					
			if(con!=null)con.close();				
		 }
	  
	   
      writer.print(options);
      writer.flush();
      if(writer!=null)writer.close();
        return null;
	}
	public ActionForward getAreas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	PrintWriter	writer= response.getWriter();
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
						
	    String stateId=(String)request.getParameter("stateId");
	    
	    stateId=(stateId==null)?"0":stateId.trim();
	    String options="";
	    //System.out.println("state id..."+stateId);
    	String query="SELECT CITY_TYPE_ID,CITY_DESCRIPTION FROM APP.TPA_CITY_CODE WHERE STATE_TYPE_ID='"+stateId+"' ORDER BY CITY_DESCRIPTION";
	   try{ 
	    con=ResourceManager.getConnection();
		statement=con.prepareStatement(query);
		//System.out.println("City code query..."+query);
		  resultSet=statement.executeQuery();
		    HashMap<String,String> providerAreas=new  HashMap<String,String>();
	       while(resultSet.next()){
	    	   String id=resultSet.getString(1);
	    	   String desc=resultSet.getString(2);
	    	  
	    	   providerAreas.put(id, desc);
	    	   options+=id+"@"+desc+"&";
	       }
	       request.getSession().setAttribute("providerAreas", providerAreas);
		}catch(Exception exception){
			log.error(exception.getMessage(), exception);
			}
		finally{
			if(resultSet!=null)resultSet.close();
			if(statement!=null)statement.close();			
			if(con!=null)con.close();
		 }
	  
      writer.print(options);
      writer.flush();
      if(writer!=null)writer.close();
        return null;
	}

public ActionForward getIsdOrStd(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
  PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet rs=null;
	try{ 
		String iors=request.getParameter("iors");
		String query;
		if("ISD".equals(iors)) query="select ISD_CODE from APP.TPA_COUNTRY_CODE where country_id="+request.getParameter("countryId");
		else query="select STD_CODE from APP.TPA_STATE_CODE where STATE_TYPE_ID='"+request.getParameter("stateId")+"'";
	con=ResourceManager.getConnection();
	statement=con.prepareStatement(query);		
      rs=statement.executeQuery();
      if(rs.next())writer.print(rs.getInt(1));      
      else writer.print("");
      writer.flush();	    
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		}
	finally{
		if(writer!=null)writer.close();
		if(rs!=null)rs.close();
		if(statement!=null)statement.close();			
		if(con!=null)con.close();
	}		
    return null;
}

public ActionForward getIcdDescription(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;					
   
   try{
    con=ResourceManager.getConnection();
     String query;
     String id=request.getParameter("id");
     if("desc".equals(id))query="select icd_desc from test_icd";
     else{
    	 String icd_desc=request.getParameter("icd_desc");     
        query="select icd_id from test_icd where icd_desc='"+icd_desc+"'";
     }
       
	statement=con.prepareStatement(query);
	resultSet=statement.executeQuery();
	  
       while(resultSet.next()) writer.println(resultSet.getString(1));
       writer.flush();
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		}
	finally{
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();			
		if(con!=null)con.close();
		if(writer!=null)writer.close();
	 }  
  
    return null;
}
public ActionForward getStateNames(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;					
   
   try{
    con=ResourceManager.getConnection();
     String query;
     String id=request.getParameter("id");
     if("stateId".equals(id)){
    	 String stateCode=request.getParameter("stateCode");
    	 query="Select STATE_TYPE_ID,STATE_NAME from APP.TPA_STATE_CODE where STATE_TYPE_ID='"+stateCode+"'";
     }
     else{
    	 String cityCode=request.getParameter("cityCode");
    	 query="select CITY_TYPE_ID,CITY_DESCRIPTION from APP.TPA_CITY_CODE where CITY_TYPE_ID='"+cityCode+"'"; 
     }
       
	statement=con.prepareStatement(query);
	resultSet=statement.executeQuery();
	  
       while(resultSet.next()) writer.println(resultSet.getString(1)+"@"+resultSet.getString(2));       
       writer.flush();
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		}
	finally{
		if(writer!=null)writer.close();
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();
		if(con!=null)con.close();
	 }  
  
    return null;
}
public ActionForward getProductNetworkType(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;					
   
   try{
    con=ResourceManager.getConnection();     
     String productSeqID=request.getParameter("productSeqID"); 	 
     String query="select gc.description from app.tpa_ins_product ip join app.tpa_general_code gc on (ip.product_cat_type_id=gc.general_type_id) where ip.product_seq_id="+productSeqID+"";
    
	statement=con.prepareStatement(query);
	resultSet=statement.executeQuery();
	  
       while(resultSet.next()) writer.println(resultSet.getString(1));       
       writer.flush();
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		}
	finally{
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();			
		if(con!=null)con.close();
		if(writer!=null)writer.close();
	 }  
  
    return null;
}

public ActionForward getMemberDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	PrintWriter	writer= response.getWriter();
	Connection con=null;
	CallableStatement statement=null;
	ResultSet resultSet=null;
   try{	 
	   con=ResourceManager.getConnection();     
	     String memberId=request.getParameter("memberId");
	     memberId=(memberId==null)?"":memberId.trim();     
	     statement=con.prepareCall("{CALL AUTHORIZATION_PKG_SELECT_MEMBER(?,?)}");    
	     statement.setString(1,memberId);
	     statement.registerOutParameter(2,Types.OTHER);
		statement.execute();
	   resultSet=(ResultSet)statement.getObject(2);	
		if(resultSet==null||!resultSet.next()||resultSet.getLong("MEMBER_SEQ_ID")==0){		
			writer.write("MNE@");//Member Not Exist		 
		}else{
			String memberSeqId=(resultSet.getLong("MEMBER_SEQ_ID")==0)?"":resultSet.getLong("MEMBER_SEQ_ID")+"";
			String memberName=(resultSet.getString("MEM_NAME")==null)?"":resultSet.getString("MEM_NAME");
			String memberAge=(resultSet.getString("MEM_AGE")==null)?"":resultSet.getString("MEM_AGE");
			String emirateId=(resultSet.getString("EMIRATE_ID")==null)?"":resultSet.getString("EMIRATE_ID");
			String payerId=(resultSet.getString("PAYER_ID")==null)?"":resultSet.getString("PAYER_ID");
			String insSeqId=(resultSet.getLong("INS_SEQ_ID")==0)?"":resultSet.getLong("INS_SEQ_ID")+"";
			String insCompName=(resultSet.getString("INS_COMP_NAME")==null)?"":resultSet.getString("INS_COMP_NAME");
			String policySeqId=(resultSet.getLong("POLICY_SEQ_ID")==0)?"":resultSet.getLong("POLICY_SEQ_ID")+"";
			String patientGender=(resultSet.getString("GENDER")==null)?"":resultSet.getString("GENDER");
			String policyNumber=(resultSet.getString("POLICY_NUMBER")==null)?"":resultSet.getString("POLICY_NUMBER");
			String corporateName=(resultSet.getString("CORPORATE_NAME")==null)?"":resultSet.getString("CORPORATE_NAME");
			String policyStartDate=TTKCommon.convertDateAsString("dd/MM/yyyy",resultSet.getDate("POLICY_START_DATE"));
			String policyEndtDate=TTKCommon.convertDateAsString("dd/MM/yyyy",resultSet.getDate("POLICY_END_DATE"));
			String nationality=(resultSet.getString("NATIONALITY")==null)?"":resultSet.getString("NATIONALITY");
			String sumInsured=(resultSet.getString("SUM_INSURED")==null)?"":resultSet.getString("SUM_INSURED");
			String availableSumInsured=(resultSet.getString("AVA_SUM_INSURED")==null)?"":resultSet.getString("AVA_SUM_INSURED");
			String vipyn=(resultSet.getString("VIP_YN")==null)?"":resultSet.getString("VIP_YN");
            String incepDate=(resultSet.getString("CLM_MEM_INSP_DATE")==null)?"":resultSet.getString("CLM_MEM_INSP_DATE");	
            String policyCategory= (resultSet.getString("CLASSIFICATION")==null)?"":resultSet.getString("CLASSIFICATION");
			writer.write("SUCC@"+memberSeqId+"@"+memberName+"@"+memberAge+"@"+emirateId+"@"+payerId+"@"+insSeqId+"@"+insCompName+"@"+policySeqId+"@"+patientGender+"@"+policyNumber+"@"+corporateName+"@"+policyStartDate+"@"+policyEndtDate+"@"+nationality+"@"+sumInsured+"@"+availableSumInsured+"@"+vipyn+"@"+incepDate+"@"+policyCategory);
		writer.flush();		
		con.commit();
	}		
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		writer.write("EO@");//Exception Occured
        }
	finally{
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();			
		if(con!=null)con.close();
		if(writer!=null)writer.close();
	 }  
    return null;
}
public ActionForward getProviderDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;
   try{	 
	   con=ResourceManager.getConnection();     
	     String providerId=request.getParameter("providerId");
	     providerId=(providerId==null)?"":providerId.trim();     
	     statement=con.prepareStatement("SELECT I.HOSP_SEQ_ID,I.HOSP_LICENC_NUMB,I.HOSP_NAME FROM APP.TPA_HOSP_INFO I JOIN APP.TPA_HOSP_EMPANEL_STATUS ES ON (I.HOSP_SEQ_ID=ES.HOSP_SEQ_ID AND ES.EMPANEL_STATUS_TYPE_ID='EMP') WHERE I.HOSP_LICENC_NUMB='"+providerId+"'");    
	   resultSet=statement.executeQuery();	
		if(resultSet==null||!resultSet.next()){		
			writer.write("PNE@");//Provider Not Exist		 
		}else{
			String providerSeqId=(resultSet.getLong(1)==0)?"":resultSet.getLong(1)+"";	
			String providerNum=(resultSet.getString(2)==null)?"":resultSet.getString(2);
			String providerName=(resultSet.getString(3)==null)?"":resultSet.getString(3);
			
		writer.write("SUCC@"+providerSeqId+"@"+providerNum+"@"+providerName);
		writer.flush();		 
	}		
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		writer.write("EO@");//Exception Occured
		}
	finally{
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();			
		if(con!=null)con.close();
	 }  
    return null;
}
public ActionForward getProviderDetails2(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;
   try{	 
	   con=ResourceManager.getConnection();     
	     String providerName=request.getParameter("providerName");
	     providerName=(providerName==null)?"":providerName.trim();     
	     statement=con.prepareStatement("SELECT HOSP_NAME||' ('||HOSP_LICENC_NUMB||')',HOSP_SEQ_ID,HOSP_LICENC_NUMB FROM TPA_HOSP_INFO WHERE HOSP_LICENC_NUMB = REGEXP_SUBSTR('"+providerName+"', '[^/()]+', 1, 2)");    
		
	   resultSet=statement.executeQuery();	
		if(resultSet==null||!resultSet.next()){		
			writer.write("PNE@");//Provider Not Exist		 
		}else{
			String providerName2=(resultSet.getString(1)==null)?"":resultSet.getString(1);
			String providerSeqId=(resultSet.getLong(2)==0)?"":resultSet.getLong(2)+"";			
			String providerNum=(resultSet.getString(3)==null)?"":resultSet.getString(3);
		writer.write("SUCC@"+providerSeqId+"@"+providerName2+"@"+providerNum);
		writer.flush();		 
	}		
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		writer.write("EO@");//Exception Occured
		}
	finally{
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();			
		if(con!=null)con.close();
	 }  
    return null;
}
public ActionForward getClinicianDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;
   try{	 
	   con=ResourceManager.getConnection();     
	     String clinicianId=request.getParameter("clinicianId");
	     clinicianId=(clinicianId==null)?"":clinicianId.trim();     
	     statement=con.prepareStatement("SELECT P.CONTACT_SEQ_ID,P.PROFESSIONAL_ID,P.CONTACT_NAME FROM APP.TPA_HOSP_PROFESSIONALS P WHERE P.PROFESSIONAL_ID='"+clinicianId+"'");    
		
	   resultSet=statement.executeQuery();	
		if(resultSet==null||!resultSet.next()){		
			writer.write("CNE@");//Clinician Not Exist		 
		}else{
			String clinicianName=(resultSet.getString(3)==null)?"":resultSet.getString(3);
		writer.write("SUCC@"+clinicianName);
		writer.flush();		 
	}		
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		writer.write("EO@");//Exception Occured
		}
	finally{
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();			
		if(con!=null)con.close();
	 }  
    return null;
}
public ActionForward getClinicianDetails2(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;
   try{	 
	   con=ResourceManager.getConnection();     
	     String clinicianName=request.getParameter("clinicianName");
	     clinicianName=(clinicianName==null)?"":clinicianName.trim();     
	     statement=con.prepareStatement("SELECT CONTACT_NAME ||' ('||PROFESSIONAL_ID||')' AS CONTACT_NAME,THP.PROFESSIONAL_ID FROM TPA_HOSP_PROFESSIONALS THP WHERE THP.PROFESSIONAL_ID=REGEXP_SUBSTR('"+clinicianName+"', '[^/()]+', 1, 2)");    
		
	   resultSet=statement.executeQuery();	
		if(resultSet==null||!resultSet.next()){		
			writer.write("CNE@");//Clinician Not Exist		 
		}else{
			String clinicianName2=(resultSet.getString(1)==null)?"":resultSet.getString(1);
			String clinicianId=(resultSet.getString(2)==null)?"":resultSet.getString(2);
		writer.write("SUCC@"+clinicianName2+"@"+clinicianId);
		writer.flush();		 
	}		
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		writer.write("EO@");//Exception Occured
		}
	finally{
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();			
		if(con!=null)con.close();
	 }  
    return null;
}
public ActionForward getIcdCodeDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	PrintWriter	writer= response.getWriter();
	Connection con=null;
	CallableStatement statement=null;
	ResultSet resultSet=null;
   try{	 
	   con=ResourceManager.getConnection();     
	     String icdCode=request.getParameter("icdCode");
	     String seqId=request.getParameter("seqID");
	     String authType=request.getParameter("authType");
	     icdCode=(icdCode==null)?"":icdCode.trim();
	     seqId=(seqId==null)?"0":seqId.trim();
	     authType=(authType==null)?"":authType.trim();
	     statement=con.prepareCall("{CALL AUTHORIZATION_PKG_SELECT_ICD(?,?,?,?)}");    
	     statement.setString(1,icdCode);
	     statement.setLong(2,new Long(seqId));
	     statement.setString(3,authType);
	     statement.registerOutParameter(4,Types.OTHER);
		statement.execute();
	   resultSet=(ResultSet)statement.getObject(4);	
		if(resultSet==null||!resultSet.next()||resultSet.getLong("ICD_CODE_SEQ_ID")==0){		
			writer.write("INE@");//ICD Code Not Exist		 
		}else{
			String icdCodeSeqId=(resultSet.getLong("ICD_CODE_SEQ_ID")==0)?"":resultSet.getLong("ICD_CODE_SEQ_ID")+"";
			String icdDescription=(resultSet.getString("ICD_DESCRIPTION")==null)?"":resultSet.getString("ICD_DESCRIPTION");
			String primary=(resultSet.getString("PRIMARY")==null)?"":resultSet.getString("PRIMARY");
			String chronicYN=(resultSet.getString("CHRONIC_YN")==null)?"":resultSet.getString("CHRONIC_YN");
			
		writer.write("SUCC@"+icdCodeSeqId+"@"+icdDescription+"@"+primary+"@"+chronicYN);
		writer.flush();		 
	}		
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		writer.write("EO@");//Exception Occured
		writer.flush();
		}
	finally{
		if(writer!=null)writer.close();
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();			
		if(con!=null)con.close();
	 }  
    return null;
}
public ActionForward getActivityCodeDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	PrintWriter	writer= response.getWriter();
	Connection con=null;
	CallableStatement statement=null;
	ResultSet resultSet=null;
	ResultSet tarrifrs=null;
   try{	 
	   con=ResourceManager.getConnection();     
	     String preAuthSeqID=request.getParameter("preAuthSeqID");
	     String activityCode=request.getParameter("activityCode");
	     String activityStartDate=request.getParameter("activityStartDate");
	     activityCode=(activityCode==null)?"":activityCode.trim();
	     preAuthSeqID=(preAuthSeqID==null)?"":preAuthSeqID.trim();
	     activityStartDate=(activityStartDate==null)?"":activityStartDate.trim();
	     statement=con.prepareCall("{CALL AUTHORIZATION_PKG.SELECT_ACTIVITY_CODE(?,?,?,?,?)}");    
	     statement.setString(1,preAuthSeqID);
	     statement.setString(2,activityCode);	    
	     statement.setDate(3,new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(activityStartDate).getTime()));
	     statement.registerOutParameter(4,OracleTypes.CURSOR);
	     statement.registerOutParameter(5,OracleTypes.CURSOR);
		statement.execute();
	   resultSet=(ResultSet)statement.getObject(4);	
		if(resultSet==null||!resultSet.next()||resultSet.getLong("ACTIVITY_SEQ_ID")==0){		
			writer.write("AVCNE@");//Activity Code Not Exist		 
		}else{
			String activityCodeSeqId=(resultSet.getLong("ACTIVITY_SEQ_ID")==0)?"":resultSet.getLong("ACTIVITY_SEQ_ID")+"";
			String activityCodeDescription=(resultSet.getString("ACTIVITY_DESCRIPTION")==null)?"":resultSet.getString("ACTIVITY_DESCRIPTION");
			String activityTypeId=(resultSet.getString("ACTIVITY_TYPE_ID")==null)?"":resultSet.getString("ACTIVITY_TYPE_ID");
			tarrifrs=(ResultSet)statement.getObject(5);	
			String grossAmt="";
			String discountAmt="";
			String discountGrossAmt="";
			String bindleId="";
			String packageId="";
			String internalCode="";
			if(tarrifrs!=null&&tarrifrs.next()){
				grossAmt=tarrifrs.getBigDecimal("GROSS_AMOUNT")==null?"":tarrifrs.getBigDecimal("GROSS_AMOUNT")+"";
				discountAmt=tarrifrs.getBigDecimal("DISC_AMOUNT")==null?"":tarrifrs.getBigDecimal("DISC_AMOUNT")+"";
				discountGrossAmt=tarrifrs.getBigDecimal("DISC_GROSS_AMOUNT")==null?"":tarrifrs.getBigDecimal("DISC_GROSS_AMOUNT")+"";
				bindleId=tarrifrs.getString("BUNDLE_ID")==null?"":tarrifrs.getString("BUNDLE_ID");
				packageId=tarrifrs.getString("PACKAGE_ID")==null?"":tarrifrs.getString("PACKAGE_ID");
				internalCode=tarrifrs.getString("INTERNAL_CODE")==null?"":tarrifrs.getString("INTERNAL_CODE");
			}
		writer.write("SUCC@"+activityCodeSeqId+"@"+activityCodeDescription+"@"+activityTypeId+"@"+grossAmt+"@"+discountAmt+"@"+discountGrossAmt+"@"+bindleId+"@"+packageId+"@"+internalCode);
		writer.flush();		 
	}		
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		writer.write("EO@");//Exception Occurred
		writer.flush();
		}
	finally{
		if(resultSet!=null)resultSet.close();
		if(tarrifrs!=null)tarrifrs.close();
		if(statement!=null)statement.close();			
		if(con!=null)con.close();
		
		if(writer!=null)writer.close();
	 }  
    return null;
}

public ActionForward getDiagnosisDesc(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	log.debug("Inside AsynchronusAction getDiagnosisDesc ");
	PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;
   try{	 
	   con=ResourceManager.getConnection();     
	     statement=con.prepareStatement("SELECT TRIM(LONG_DESC)||'('||TRIM(ICD_CODE)||')' AS ICD_DESCRIPTION FROM TPA_ICD10_MASTER_DETAILS");
	     resultSet=(ResultSet)statement.executeQuery();
	     
	     while(resultSet.next()){
	    	 writer.println(resultSet.getString(1));      
	     }
	     writer.flush();	
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		writer.flush();
		}
	finally{
		if(writer!=null)writer.close();
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();
		if(con!=null)con.close();
	 }  
    return null;
}


public ActionForward  getObservTypeDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement1=null,statement2=null;
	ResultSet resultSet1=null,resultSet2=null;
   try{	 
	   con=ResourceManager.getConnection();     
	     String observType=request.getParameter("observType");
	     observType=(observType==null)?"":observType.trim();
	     statement1=con.prepareStatement("SELECT S.OBSERVATION_CODE_ID,S.OBSERVATION_CODE FROM APP.TPA_OBSERVATION_VALUE_CODES S where s.observation_type_id='"+observType+"'");
	     resultSet1=(ResultSet)statement1.executeQuery();
	     String result="";
	     while(resultSet1.next()){
	    	 String codeId=(resultSet1.getString(1)==null)?"":resultSet1.getString(1);
	    	 String codeDesc=(resultSet1.getString(2)==null)?"":resultSet1.getString(2);	    	 
	    	 result+=codeId+"@"+codeDesc+"#";       
	     }
	     result+="|";
	     statement2=con.prepareStatement("select C.OBS_VALUE_TYPE_CODE_ID,C.VALUE_TYPE from app.TPA_OBSER_VALUE_TYPE_CODES C WHERE C.OBSERVATION_TYPE_ID='"+observType+"'");
	     resultSet2=(ResultSet)statement2.executeQuery();
	   
	     while(resultSet2.next()){	    	
	    	 String valueTypeId=(resultSet2.getString(1)==null)?"":resultSet2.getString(1);
	    	 String valueTypeDesc=(resultSet2.getString(2)==null)?"":resultSet2.getString(2);
	    	 result+=valueTypeId+"@"+valueTypeDesc+"#";      
	     }
	     writer.println(result);
	     writer.flush();	
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		writer.flush();
		}
	finally{
		if(writer!=null)writer.close();
		if(resultSet2!=null)resultSet2.close();
		if(resultSet1!=null)resultSet1.close();
		if(statement2!=null)statement2.close();
		if(statement1!=null)statement1.close();
		if(con!=null)con.close();
	 }  
    return null;
}

public ActionForward getIcdCodes(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	log.debug("Inside AsynchronusAction getIcdCodes ");
	PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;
   try{	 
	   con=ResourceManager.getConnection();     
	     statement=con.prepareStatement("SELECT ICD_CODE FROM TPA_ICD10_MASTER_DETAILS");
	     resultSet=(ResultSet)statement.executeQuery();
	     
	     while(resultSet.next()){
	    	 writer.println(resultSet.getString(1));      
	     }
	     writer.flush();	
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		writer.flush();
		}
	finally{
		if(writer!=null)writer.close();
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();
		if(con!=null)con.close();
	 }  
    return null;
}
public ActionForward getUnitTypePrice(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	log.debug("Inside AsynchronusAction getUnitTypePrice ");
	PrintWriter	writer= response.getWriter();
	Connection con=null;
	CallableStatement statement=null;
   try{	   
	    String activityCode=request.getParameter("activityCode");
	    activityCode=activityCode==null?"":activityCode.trim();
	    String activityStartDate=request.getParameter("activityStartDate");
	    activityStartDate=activityStartDate==null?"":activityStartDate.trim();
	    String unitType=request.getParameter("unitType");
	    String providerSeqID=request.getParameter("providerSeqID");
	    String type=request.getParameter("type");
	    String seqID=request.getParameter("seqID");
	    unitType=unitType==null?"":unitType.trim();
	    con=ResourceManager.getConnection();     
		     statement=con.prepareCall("{CALL AUTHORIZATION_PKG_SELECT_DDC_PRICE(?,?,?,?,?,?,?,?)}");    
		     statement.setString(1,activityCode);
		     statement.setDate(2,new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(activityStartDate).getTime()));
		     statement.setString(3,unitType);
		     statement.setLong(4,Long.parseLong(providerSeqID));
		     statement.setString(5,type);
		     statement.setLong(6,Long.parseLong(seqID));
		     statement.registerOutParameter(7,Types.VARCHAR);
		     statement.registerOutParameter(8,Types.VARCHAR);
			statement.execute();
			con.commit();
		   String price=statement.getString(7);
		   String discount=statement.getString(8);
		   price=(price==null||"".equals(price))?"0":price;
		   discount=(discount==null||"".equals(discount))?"0":discount;
	     writer.print(price+"@"+discount);      
	     writer.flush();	
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		 writer.print("0@0");  
		writer.flush();
		}
	finally{
		if(writer!=null)writer.close();
		if(statement!=null)statement.close();
		if(con!=null)con.close();
	 }  
    return null;
}
public ActionForward getCurencyType(ActionMapping mapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException
{
	String countryID=request.getParameter("countryId");
	PrintWriter pw=response.getWriter();
	Connection connection=null;
	ResultSet resultSet=null;
	PreparedStatement preStatement=null;
	try {
		connection=ResourceManager.getConnection();
		preStatement=connection.prepareStatement("select jk.currency_id from app.tpa_country_code hj join app.tpa_currency_code jk on (hj.country_id=jk.country_id) where hj.country_id=?");
		preStatement.setInt(1,Integer.parseInt(countryID));
		resultSet=preStatement.executeQuery();
		if(resultSet.next())
		{
			pw.print(resultSet.getString("currency_id"));
			
		}
		pw.flush();
	} 
	catch (TTKException | SQLException e)
	{
		e.printStackTrace();
	} 
	finally
	{
		
		if(pw!=null)pw.close();
		if(resultSet!=null)resultSet.close();
		if(preStatement!=null)preStatement.close();
		if(connection!=null)connection.close();
	}
	return null;
	
}

public ActionForward getInsuranceCompany(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;
					
    String providerAuthority=(String)request.getParameter("providerAuthority");
    String forwardValue=TTKCommon.checkNull((String)request.getParameter("forwardValue"));
    
    providerAuthority=(providerAuthority==null)?"0":providerAuthority.trim();
    String options="";
    String query = " SELECT INS_COMP_CODE_NUMBER,INS_COMP_NAME,INS_SEQ_ID FROM app.TPA_INS_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO' and PAYER_AUTHORITY = ?  ORDER BY INS_COMP_NAME";
   try{ 
    con=ResourceManager.getConnection();
	statement=con.prepareStatement(query);
	statement.setString(1, providerAuthority);
	  resultSet=statement.executeQuery();
	    HashMap<String,String> insuranceCompanyName=new  HashMap<String,String>();
       while(resultSet.next()){
    	   String id=resultSet.getString(3);
    	   
    	   String insCompanyName=resultSet.getString(2);
    	   
    	   if("mailTrigger".equals(forwardValue)){
    		   
    		   options+=resultSet.getString(1)+"@"+insCompanyName+"&";  
    	   }
    	   else{
    		   options+=resultSet.getString(3)+"@"+insCompanyName+"&";  
    	   }
    	   
    	   
    	      
    	  // insuranceCompanyName.put(id, insCompanyName);
    	  }
      
      // request.getSession().setAttribute("alInsuranceCompany", insuranceCompanyName);
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		}
	finally{
		if(resultSet!=null)resultSet.close();	
		if(statement!=null)statement.close();
		if(con!=null)con.close();
	 }

  writer.print(options);
  writer.flush();
  if(writer!=null)writer.close();
    return null;
}

//bikki  
public ActionForward getInsuranceCompanyName(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;
					
    
	String providerAuthority=(String)request.getParameter("providerAuthority");
	
	 providerAuthority=(providerAuthority==null)?"0":providerAuthority.trim();
    String options="";
   // String query = "select i.ins_seq_id,i.ins_comp_name from tpa_ins_info i where i.payer_authority=?";
    
    String query = "SELECT distinct T.INS_COMPANY_NAME,T.INS_COMPANY_NAME FROM  APP.PRIC_TAB_PAST T WHERE T.AUTHOR_TYPE = ? UNION ALL select 'OTHERS' AS INS_COMPANY_NAME,'OTHERS' AS  INS_COMPANY_NAME";
 
    try {
		con=ResourceManager.getConnection();
		statement=con.prepareStatement(query);
		statement.setString(1, providerAuthority);
		  resultSet=statement.executeQuery();
	//	  fetchMultiResults(resultSet); // added govind
		    HashMap<String,String> insuranceCompanyName=new  HashMap<String,String>();
	       while(resultSet.next()){
	    	   String id=resultSet.getString(1);
	    	   String insCompanyName=resultSet.getString(2);
	    	   options+=id+"@"+insCompanyName+"&";  
	    	  }
	} catch(Exception exception){
		log.error(exception.getMessage(), exception);
		}
	finally{
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();
		if(con!=null)con.close();			
	 }

  writer.print(options);
  writer.flush();
  if(writer!=null)writer.close();
    return null;

}
   
//end bikki

// govind

public ActionForward getPreviousProd(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;
					
    String previousPolicyNo=(String)request.getParameter("previousPolicyNo");
    String flag=(String)request.getParameter("flag");
    previousPolicyNo=(previousPolicyNo==null)?"0":previousPolicyNo.trim();
    String options="";
    String query ="";
    if(flag.equalsIgnoreCase("PL") ){
    	
    	query ="SELECT distinct PRODUCT_NAME,PRODUCT_NAME FROM APP.PRIC_TAB_PAST WHERE ','||POLICY_NUMBER||',' like '%,'||'"+previousPolicyNo+"'||',%'";
       log.info("getPreviousProd query:"+query);
    }else if(flag.equalsIgnoreCase("DHA") || flag.equalsIgnoreCase("SME")  ){
    	query = "SELECT distinct PRODUCT_NAME,PRODUCT_NAME FROM APP.PRIC_TAB_PAST WHERE corp_id in ('NA') AND PRODUCT_Name like '%"+flag+"%'";
    }
  
  
   try{
    con=ResourceManager.getConnection();
	statement=con.prepareStatement(query);
/*	if(flag.equalsIgnoreCase("PL")){
		statement.setString(1, previousPolicyNo);
	}else{
		
	}*/
	
	  resultSet=statement.executeQuery();
	    HashMap<String,String> insuranceCompanyName=new  HashMap<String,String>();
       while(resultSet.next()){
    	   String id=resultSet.getString(1);
    	   
    	   String previousProdName=resultSet.getString(2);
    	   /*options+=resultSet.getString(1)+"@"+previousProdName+"&";*/    
    	   options+=id+"@"+previousProdName+"&";
    	   
    	  // insuranceCompanyName.put(id, insCompanyName);
    	  }
      
      // request.getSession().setAttribute("alInsuranceCompany", insuranceCompanyName);
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		}
	finally{
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();
		if(con!=null)con.close();			
	 }

  writer.print(options);
  writer.flush();
  if(writer!=null)writer.close();
    return null;
}

public ActionForward getOutPatientAreaCov(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
    PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;
					
    String networkList=(String)request.getParameter("networkList");
    networkList=(networkList==null)?"0":networkList.trim();
    String options="";
    String	 query = "SELECT a.fact_id,a.descriptn  from app.pri_factors_cal A where a.bid='6' and a.rule_name='Outpatient' and a.hide_val like '%"+networkList+"%' order by a.sort_number";

    log.info("query:"+query);
   try{
    con=ResourceManager.getConnection();
	statement=con.prepareStatement(query);

	  resultSet=statement.executeQuery();
       while(resultSet.next()){
    	   String id=resultSet.getString(1);
    	   
    	   String previousProdName=resultSet.getString(2);
    	   options+=resultSet.getString(1)+"@"+previousProdName+"#";    
    	  
    	  }
      	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		}
	finally{
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();
		if(con!=null)con.close();			
	 }

  writer.print(options);
  writer.flush();
  if(writer!=null)writer.close();
    return null;
}



public StringBuilder getInsCodeWithAuthority(String InsName,String authority)throws TTKException{
    final   String strGetInsCode="SELECT  I.INS_COMP_NAME as INS_COMP_NAME,I.INS_COMP_CODE_NUMBER as INS_COMP_CODE_NUMBER,INS_SEQ_ID FROM APP.TPA_INS_INFO I  WHERE INS_COMP_NAME LIKE ? and I.INS_OFFICE_GENERAL_TYPE_ID='IHO' and I.PAYER_AUTHORITY=?";//for intX

	Connection conn = null;
	PreparedStatement pStmt = null;
	ResultSet rs = null;
	StringBuilder allresult=new StringBuilder();
	//ArrayList<String> alLicense	=	null;
	try
	{
		conn = ResourceManager.getConnection();
		pStmt = conn.prepareStatement(strGetInsCode);
		pStmt.setString(1,InsName+"%");  //InsName
		pStmt.setString(2,authority);  //authority
		rs = pStmt.executeQuery();
		if(rs!=null){
			while(rs.next()){
				allresult.append(rs.getString("INS_SEQ_ID")+"|"+rs.getString("INS_COMP_NAME")+"|"+rs.getString("INS_COMP_CODE_NUMBER")+"#");
			}
            
            }
		return allresult;
	}//end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "enrollment");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "enrollment");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in PolicyDAOImpl getPolicySIInfo()",sqlExp);
				throw new TTKException(sqlExp, "enrollment");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmt != null) pStmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in PolicyDAOImpl getPolicySIInfo()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in PolicyDAOImpl getPolicySIInfo()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "enrollment");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs = null;
			pStmt = null;
			conn = null;
		}//end of finally
	}//end of finally
}//end of getLicenceNumbers(ArrayList alProfessionals)




public ActionForward getInsCodeWithAuthority(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	PrintWriter out = null;
	try
    {
    	
    	log.debug("Inside the getInsCodeWithAuthority method of HospitalSearchAction");
        		StringBuilder alResult;
        		String InsName	=	request.getParameter("InsName");
        		String authority=request.getParameter("authority");
        		alResult= this.getInsCodeWithAuthority(InsName,authority);
        		out = response.getWriter();  
    	        out.write(alResult.toString());		
    	        out.flush();
    	        out.close();
        		
        	}//end of try
        	catch(TTKException expTTK)
    		{
        		log.error(expTTK.getMessage(), expTTK);
    		}//end of catch(TTKException expTTK)
    		catch(Exception exp)
    		{
    			log.error(exp.getMessage(), exp);
    		}//end of catch(Exception exp)
          finally{
        	  if(out!=null)out.close();
          }
    
      return null;
	
        }//end of getInsCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
public ActionForward getInsuranceCompanyNames(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;
					
    String providerAuthority=(String)request.getParameter("providerAuthority");
    providerAuthority=(providerAuthority==null||"".equals(providerAuthority))?"DHA":providerAuthority.trim();
    StringBuilder builder=new StringBuilder();
    String query = "SELECT INS_SEQ_ID,INS_COMP_NAME FROM TPA_INS_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO' and PAYER_AUTHORITY = ?  ORDER BY INS_COMP_NAME";
   try{
    con=ResourceManager.getConnection();
	statement=con.prepareStatement(query);
	statement.setString(1, providerAuthority);
	  resultSet=statement.executeQuery();
	  //  HashMap<String,String> insuranceCompanyName=new  HashMap<String,String>();
       while(resultSet.next()){
    	   String id=resultSet.getString(1);    	   
    	   String insCompanyName=resultSet.getString(2);    	   
    	   builder.append(id).append("@").append(insCompanyName).append("&");
    	 //  insuranceCompanyName.put(id, insCompanyName);
    	  }
     // System.out.println(providerAuthority+"insuranceCompanyName="+insuranceCompanyName);
     //  request.getSession().setAttribute("insuranceCompanyNames", insuranceCompanyName);
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		}
	finally{
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();
		if(con!=null)con.close();
		
					
	 }

  writer.print(builder.toString());
  writer.flush();
  if(writer!=null)writer.close();
    return null;
}
}