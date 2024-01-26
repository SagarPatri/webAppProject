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

package com.ttk.action.empanelment;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ttk.dao.ResourceManager;

/**
 * This class is reusable for adding enrollment information in corporate and non corporate policies in enrollment flow.
 * This class also provides option for deleting the selected enrollment.
 */

public class AsynchronusAction extends DispatchAction {
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
      if(rs.next())writer.println(rs.getInt(1));
      else writer.println("");
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

public ActionForward getIsdForMember(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		  	PrintWriter	writer= response.getWriter();
			Connection con=null;
			PreparedStatement statement=null;
			ResultSet rs=null;
			String vidalId	=	request.getParameter("id");
			try{
				String query;
				query="SELECT CC.ISD_CODE,PM.TPA_ENROLLMENT_ID,TTK_UTIL_PKG.fn_decrypt(ME.MOBILE_NO) AS MOBILE_NO "
						+ "FROM APP.TPA_ENR_MEM_ADDRESS ME JOIN APP.TPA_ENR_POLICY_MEMBER PM ON "
						+ "(ME.ENR_ADDRESS_SEQ_ID=PM.ENR_ADDRESS_SEQ_ID) JOIN APP.TPA_COUNTRY_CODE CC ON "
						+ "(CC.COUNTRY_ID=ME.COUNTRY_ID) WHERE PM.TPA_ENROLLMENT_ID='"+vidalId+"' ";
				
				con=ResourceManager.getConnection();
				statement=con.prepareStatement(query); 
				rs=statement.executeQuery();
				if(rs.next())
					writer.println(rs.getInt(1)+"@"+rs.getLong(3)+"@");
				else
					writer.println("ISD"+"@"+"");
				writer.flush();
			}
			catch(Exception e){e.printStackTrace();}
			
			finally{
				if(writer!=null)writer.close();
				if(rs!=null)rs.close();
				if(statement!=null)statement.close();
				if(con!=null)con.close();		
				
			}
    return null;
}

//Common methd for multiple purposes

public ActionForward getCommonMethod(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		  	PrintWriter	writer= response.getWriter();
			Connection con=null;
			PreparedStatement statement=null;
			ResultSet rs=null;
			String id	=	request.getParameter("id");
			String getType		=	request.getParameter("getType");
			String regAuthority		=	request.getParameter("regAuthority");
			String id1	=	"";
			id1	=	request.getParameter("id");
			try{
				String query	=	null;
				//if(true)//"BroCode".equals(getType)
				if("BroCode".equals(getType))//"BroCode".equals(getType)
				{
					//id1	=	request.getParameter("id");
					query="SELECT M.BRK_ID,M.COMPANY_NAME  FROM APP.DHA_INS_BROKER_MASTER M "
							+"WHERE M.AUTHORITY='"+regAuthority+"'  and M.COMPANY_NAME  like '%"+id1+"'";
				}
				if("InsCode".equals(getType))
				{
					query="SELECT PM.PAYER_AUTHORITY,PM.PAYER_ID,PM.PAYER_NAME FROM APP.DHA_PAYERS_MASTER PM "
							+"WHERE PM.PAYER_NAME='"+id+"' ";
				}
				else if("StdCode".equals(getType))
				{
					query="SELECT CC.ISD_CODE,PM.TPA_ENROLLMENT_ID,TTK_UTIL_PKG.fn_decrypt(ME.MOBILE_NO) AS MOBILE_NO "
						+ "FROM APP.TPA_ENR_MEM_ADDRESS ME JOIN APP.TPA_ENR_POLICY_MEMBER PM ON "
						+ "(ME.ENR_ADDRESS_SEQ_ID=PM.ENR_ADDRESS_SEQ_ID) JOIN APP.TPA_COUNTRY_CODE CC ON "
						+ "(CC.COUNTRY_ID=ME.COUNTRY_ID) WHERE PM.TPA_ENROLLMENT_ID='"+id+"' ";
				}
				else if("clinicianId".equals(getType))
				{
					id1	=	request.getParameter("id");
					id1	=	id1.substring(0,id1.indexOf('@'));
					
					query	=	" SELECT A.CONTACT_NAME, A.Professional_Id, A.SPECIALTY FROM (SELECT S.CONTACT_NAME, S.Professional_Id, M.SPECIALTY"+
								" FROM APP.tpa_hosp_professionals S LEFT OUTER JOIN APP.DHA_CLNSN_SPECIALTIES_MASTER M "+
								" ON (S.SPECIALITY_ID = M.SPECIALTY_ID) WHERE S.HOSP_SEQ_ID = '"+request.getParameter("hospSeqId")+"') A WHERE A.CONTACT_NAME = '"+id1+"'";
				}
				else if("ProfessionalsName".equals(getType))
				{
					id1	=	request.getParameter("id");
					String professionalsId	=	request.getParameter("professionalsId");
					query="select m.clinitian_id as profession_id,m.professional_name,m.clini_standard as standard, "+
					"m.start_date,m.end_date from app.dha_clinicians_list_master m WHERE professional_name='"+id1+"' and m.clinitian_id='"+professionalsId+"' ";
				}
				else if("addConsumablesForPreAuth".equals(getType))
				{
					id1	=	request.getParameter("id");
					query="select length(ad.activity_code),ad.activity_description from app.tpa_activity_details ad where ad.activity_type_id=4 and length(ad.activity_code)=5";
				}
				else if("PharmacyDaetails".equals(getType))
				{
					query="SELECT S.ACTIVITY_CODE,S.ACTIVITY_DESCRIPTION FROM APP.TPA_ACTIVITY_MASTER_DETAILS S WHERE S.ACTIVITY_TYPE_SEQ_ID=5";
				}
				else if("accountName".equals(getType))
				{
					id1	=	request.getParameter("HospitalSeqId");
					String id2	=	request.getParameter("id");
					query="select hi.hosp_seq_id,hi.trade_licence_name from app.tpa_hosp_info hi where hi.hosp_seq_id = "+id1+" and "
							+"hi.trade_licence_name='"+id2+"' ";
				}
				else if("bankName".equals(getType))
				{
					String id2	=	request.getParameter("id");
					query="SELECT TBM.BANK_NAME FROM FIN_APP.TPA_BANK_MASTER TBM WHERE TBM.BANK_NAME='"+id2+"' ";
				}
				else if("clinicianName".equals(getType))
				{
					id1	=	request.getParameter("id");
					query="SELECT HP.PROFESSIONAL_ID, HP.CONTACT_NAME AS CLINICIAN_NAME, HP.SPECIALITY_ID  AS CLINICIAN_SPECIALITY, HP.CONSULT_GEN_TYPE AS CONSULTATION FROM TPA_HOSP_PROFESSIONALS HP WHERE HP.PROFESSIONAL_ID ='"+id1+"' ";
				}
				else if("icdCode".equals(getType))
				{
					query="SELECT ICD_CODE,SHORT_DESC FROM APP.TPA_ICD10_MASTER_DETAILS WHERE ICD_CODE ='"+id1+"' ";
				}
				else if("activityCode".equals(getType))
				{
					query="SELECT ACT.ACTIVITY_CODE,ACT.SHORT_DESCRIPTION FROM APP.TPA_ACTIVITY_MASTER_DETAILS ACT WHERE ACT.ACTIVITY_TYPE_SEQ_ID !=5";
				}
				
				con=ResourceManager.getConnection();
				statement=con.prepareStatement(query); 
				rs=statement.executeQuery();
				if("StdCode".equals(getType))
				{
					if(rs.next())
						writer.print(rs.getInt(2)+"@"+rs.getLong(2)+"@");
					else
						writer.print("ISD"+"@"+"");
				}
				else if("InsCode".equals(getType))
				{
					if(rs.next())
						writer.print(rs.getString(2)+"@"+rs.getString(2)+"@");
					else
						writer.print("");
				}
				else if("BroCode".equals(getType))
				{
					if(rs.next())
						writer.print(rs.getString(1)+"@"+rs.getString(1)+"@");
					else
						writer.print("");
				}
				
				
				else if("clinicianId".equals(getType))
				{
					if(rs.next())
						writer.print(rs.getString(2)+"@"+rs.getString(3)+"@");
					else
						writer.print("");
				}
				else if("ProfessionalsName".equals(getType))
				{
					if(rs.next())
						writer.print(rs.getString(1)+"@"+rs.getString(3)+"@");
					else
						writer.print("");
				}
				else if("addConsumablesForPreAuth".equals(getType))
				{
					if(rs.next())
					{
						writer.println(rs.getString(2));
						while(rs.next())
							writer.println(rs.getString(2));
					}
					else
						writer.println("");
				}else if("PharmacyDaetails".equals(getType))
				{
					if(rs.next()){
						writer.println(rs.getString(2));
						while(rs.next())
						writer.println(rs.getString(2));
					}
					else
						writer.println("");
				}
				else if("accountName".equals(getType))
				{
					if(rs==null||!rs.next()){
						writer.print("invalid");
					}
					else
						writer.print("valid");
				}
				
				else if("bankName".equals(getType))
				{
					if(rs==null||!rs.next()){
						writer.print("invalid");
					}
					else
						writer.print("valid");
				}
				else 
				{
					if(rs.next()){
					if("clinicianName".equals(getType))
						writer.print(rs.getString(1)+"@"+rs.getString(3)+"@"+rs.getString(4)+"@");
					else if("icdCode".equals(getType))
						writer.print(rs.getString(1)+"@"+rs.getString(2)+"@");
					}else if("activityCode".equals(getType))
						writer.print(rs.getString(1)+"@");
				}
				
				writer.flush();
			}
			catch(Exception e){e.printStackTrace();}
			
			finally{
				if(writer!=null)writer.close();
				if(rs!=null)rs.close();
				if(statement!=null)statement.close();
				if(con!=null)con.close();			
			}
    return null;
}

/**
 * common auto complete Method
 * 
 */
public ActionForward getAutoCompleteMethod(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		  	PrintWriter	writer= response.getWriter();
			Connection con=null;
			PreparedStatement statement=null;
			ResultSet rs=null;
			String id			=	request.getParameter("id");
			String getType		=	request.getParameter("getType");
			try{
				String query	=	"";
				if("professionsName".equals(getType))
				{
					query="select m.clinitian_id as profession_id,m.professional_name,m.clini_standard as standard, "+
							"m.start_date,m.end_date from app.dha_clinicians_list_master m order by m.clini_standard";
				}else if("hospNameForEmpanelment".equals(getType))
				{
					String regType	=	request.getParameter("regType");
	//				query="select p.provider_id as provider_id,(p.PROVIDER_name||'['||provider_id||']') as provider_name,p.HEALTH_AUTHORITY"
		//					+" as emirate from app.dha_providers_master p where p.health_authority='"+regType+"' AND P.PROVIDER_ID NOT IN (select thi.hosp_licenc_numb from app.tpa_hosp_info thi)";
					
					query="select p.provider_id as provider_id,(p.PROVIDER_name || '[' || provider_id || ']') as provider_name,p.HEALTH_AUTHORITY as emirate from app.dha_providers_master p "+
							"where p.health_authority ='"+regType+"' AND P.PROVIDER_ID NOT IN (select p.provider_id from app.tpa_hosp_info thi join app.dha_providers_master p on (thi.hosp_licenc_numb=p.provider_id))";

					
				}else if("cliniciansForOnlinePreAuth".equals(getType))
				{
					query="SELECT P.PROFESSIONAL_ID ,P.CONTACT_NAME ||'::'||P.PROFESSIONAL_ID AS CONTACT_NAME FROM APP.TPA_HOSP_PROFESSIONALS P ";
				}
				else if("icdCodesForOnlinePreAuth".equals(getType))
				{
					query="SELECT ICD_CODE,SHORT_DESC FROM APP.TPA_ICD10_MASTER_DETAILS";
				}
				else if("icdDescForOnlinePreAuth".equals(getType))
				{
					query="SELECT ICD_CODE,SHORT_DESC FROM APP.TPA_ICD10_MASTER_DETAILS";
				}
				else if("EmpanelledHospList".equals(getType))
				{
					query="SELECT THI.HOSP_LICENC_NUMB AS PROVIDER_ID,(THI.HOSP_NAME || '[' || HOSP_LICENC_NUMB || ']') AS PROVIDER_NAME FROM APP.TPA_HOSP_INFO THI,APP.TPA_HOSP_EMPANEL_STATUS TES WHERE THI.HOSP_SEQ_ID=TES.HOSP_SEQ_ID AND EMPANEL_STATUS_TYPE_ID='EMP'";
				}
				else if("CaseRefferBy".equals(getType))
				{
					query="SELECT  (PD.CONTACT_NAME || '[' || PROFESSIONAL_ID || ']') CONTACT_NAME FROM APP.TPA_HOSP_PROFESSIONALS PD";
				}
				
				con=ResourceManager.getConnection();
				statement=con.prepareStatement(query); 
				rs=statement.executeQuery();
				if("professionsName".equals(getType))
				{
					if(rs!=null)
					{
						while(rs.next())
							writer.println(rs.getString(2));
					}else{
						writer.println("");
					}
				}else if("hospNameForEmpanelment".equals(getType))
				{
					if(rs!=null)
					{
						while(rs.next())
							writer.println(rs.getString("provider_name"));
					}else{
						writer.println("");
					}
				}
				
				/*else if("cliniciansForOnlinePreAuth".equals(getType))
				{*/
					if(rs!=null)
					{
						while(rs.next())
							if("cliniciansForOnlinePreAuth".equals(getType))
								writer.println(rs.getString("CONTACT_NAME"));
							else if("icdCodesForOnlinePreAuth".equals(getType))
								writer.println(rs.getString("ICD_CODE"));
							else if("icdDescForOnlinePreAuth".equals(getType))
								writer.println(rs.getString("SHORT_DESC"));
							else if("EmpanelledHospList".equals(getType))
								writer.println(rs.getString("PROVIDER_NAME"));
							else if("CaseRefferBy".equals(getType))
								writer.println(rs.getString("CONTACT_NAME"));
					}else{
						writer.println("");
					}
				//}
				
				writer.flush();
			}
			catch(Exception e){e.printStackTrace();}
			
			finally{
				if(writer!=null)writer.close();
				if(rs!=null)rs.close();
				if(statement!=null)statement.close();
				if(con!=null)con.close();
			}
    return null;
}



public ActionForward getBrokerNames(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	PrintWriter	writer= response.getWriter();
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;
    String brokerAuthority=(String)request.getParameter("brokerAuthority");
    brokerAuthority=(brokerAuthority==null)?"0":brokerAuthority.trim();
    String options="";
    String query = "SELECT BRK_ID,COMPANY_NAME  FROM APP.DHA_INS_BROKER_MASTER WHERE AUTHORITY=? ORDER BY COMPANY_NAME";
	try {
		con=ResourceManager.getConnection();
		statement=con.prepareStatement(query);
		statement.setString(1, brokerAuthority);
		  resultSet=statement.executeQuery();
		    HashMap<String,String> insuranceCompanyName=new  HashMap<String,String>();
	       while(resultSet.next()){
	    	   String id=resultSet.getString(1);
	    	   String brokerCompanyName=resultSet.getString(2);
	    	   options+=id+"@"+brokerCompanyName+"&";  
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














}