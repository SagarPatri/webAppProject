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

import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OracleTypes;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ttk.common.TTKCommon;
import com.ttk.dao.ResourceManager;

/**
 * This class is reusable for adding enrollment information in corporate and non corporate policies in enrollment flow.
 * This class also provides option for deleting the selected enrollment.
 */

public class AsnchronusAction extends DispatchAction {
	public ActionForward getEmirateIdDescription(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	PrintWriter	writer= response.getWriter();
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet rs=null;
		try{
		con=ResourceManager.getConnection();
		statement=con.prepareStatement("select b.detail_number,b.detail_desc from tpa_mouse_over_details b  where detail_number= ?");		
	    String eId=(String)request.getParameter("eId");
	         eId=(eId==null)?null:eId.trim();	    
	    
	      statement.setString(1,eId);
	      rs=statement.executeQuery();
	      if(rs.next())writer.println(rs.getString(2));
	      else writer.println("NON");
	      writer.flush();	    
		}catch(Exception e){e.printStackTrace();}
		finally{
			if(writer!=null)writer.close();
			if(rs!=null)rs.close();
			if(statement!=null)statement.close();
			if(con!=null)con.close();
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
						
	    String sorc=(String)request.getParameter("sorc");
	    String query=null;
	    String options="";
	    if("states".equals(sorc)){
	    	 String countryId=(String)request.getParameter("countryId");
	    	query="select state_type_id,state_name from TPA_STATE_CODE where country_id="+countryId+" ORDER BY state_name";
	    }
	    else if("cities".equals(sorc)){
	    	String stateId=(String)request.getParameter("stateId");
	    	query="SELECT CITY_TYPE_ID,CITY_DESCRIPTION FROM TPA_CITY_CODE WHERE STATE_TYPE_ID='"+stateId+"' ORDER BY CITY_DESCRIPTION";
	    }else options="";
	    
	   if(query!=null){		   
	   try{
	    con=ResourceManager.getConnection();
		statement=con.prepareStatement(query);
		  resultSet=statement.executeQuery();
		  
	       while(resultSet.next())options+=resultSet.getString(1)+"@"+resultSet.getString(2)+"&";
	           
		}catch(Exception e){e.printStackTrace();}
		finally{
			if(resultSet!=null)resultSet.close();
			if(statement!=null)statement.close();
			if(con!=null)con.close();		
		 }
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
		if("ISD".equals(iors)) query="select ISD_CODE from TPA_COUNTRY_CODE where country_id="+request.getParameter("countryId");
		else query="select STD_CODE from TPA_STATE_CODE where STATE_TYPE_ID='"+request.getParameter("stateId")+"'";
	con=ResourceManager.getConnection();
	statement=con.prepareStatement(query);		
      rs=statement.executeQuery();
      if(rs.next())writer.print(rs.getInt(1));      
      else writer.print("");
      writer.flush();	    
	}catch(Exception e){e.printStackTrace();}
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
	}catch(Exception e){e.printStackTrace();}
	finally{
		if(writer!=null)writer.close();
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();
		if(con!=null)con.close();
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
    	 query="Select STATE_TYPE_ID,STATE_NAME from TPA_STATE_CODE where STATE_TYPE_ID='"+stateCode+"'";
     }
     else{
    	 String cityCode=request.getParameter("cityCode");
    	 query="select CITY_TYPE_ID,CITY_DESCRIPTION from TPA_CITY_CODE where CITY_TYPE_ID='"+cityCode+"'"; 
     }
       
	statement=con.prepareStatement(query);
	resultSet=statement.executeQuery();
	  
       while(resultSet.next()) writer.println(resultSet.getString(1)+"@"+resultSet.getString(2));       
       writer.flush();
	}catch(Exception e){e.printStackTrace();}
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
	}catch(Exception e){e.printStackTrace();}
	finally{
		if(writer!=null)writer.close();
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();
		if(con!=null)con.close();
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
	     statement=con.prepareCall("{call authorization_pkg.select_member(?,?)}");    
	     statement.setString(1,memberId);
	     statement.registerOutParameter(2,OracleTypes.CURSOR);
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
			
		writer.write("SUCC@"+memberSeqId+"@"+memberName+"@"+memberAge+"@"+emirateId+"@"+payerId+"@"+insSeqId+"@"+insCompName+"@"+policySeqId);
		writer.flush();		 
	}		
	}catch(Exception e){
		writer.write("EO@");//Exception Occured
		e.printStackTrace();}
	finally{
		if(writer!=null)writer.close();
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();
		if(con!=null)con.close();
	 }  
    return null;
}


}