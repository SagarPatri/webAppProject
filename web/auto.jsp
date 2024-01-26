<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>



<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="com.ttk.dao.ResourceManager" %>
 
   <%
   
   Connection conn = null;
   Connection con =conn = ResourceManager.getConnection(); 
   Statement st=con.createStatement();
   ResultSet rs =	null;
   try{
	   String mode			=	request.getParameter("mode");
	   String hospSeqId		=	request.getParameter("hospSeqId");
	   String strIdentifier	=	request.getParameter("strIdentifier")==null?"":request.getParameter("strIdentifier");
	  // out.print("mode::"+mode);
     String s[]=null; 	 
    /*  Class.forName("oracle.jdbc.driver.OracleDriver"); */
     
     List li	=	null;
     
     if(mode.equals("providerName")){
    	 String regType	=	request.getParameter("regType");
    	/* String Qry	=	"select p.provider_id as provider_id,p.PROVIDER_name as provider_name,(p.PROVIDER_name || '[' || provider_id || ']') as provider_namewithId,p.HEALTH_AUTHORITY"
       		 +" as emirate from app.dha_providers_master p where p.health_authority ='"+regType+"' AND" 
       		 +" P.PROVIDER_ID NOT IN (select p.provider_id from tpa_hosp_info thi join app.dha_providers_master p "
       		 +" on (thi.hosp_licenc_numb=p.provider_id))"; */
    	String Qry	= "select b.bank_seq_id, b.bank_name as bank_name from fin_app.tpa_bank_master b";
     rs = st.executeQuery(Qry);
     
     
       li = new ArrayList();
 		if("LicenceNo".equals(strIdentifier))
 		{
 			while(rs.next())
 	       {
 	           li.add(rs.getString("provider_id"));
 	       }
 			
 		}else
 		{
	       while(rs.next())
	       {
	           li.add(rs.getString("provider_namewithId"));
	       }
 		}
     }else if(mode.equals("profession")){
    	 rs = st.executeQuery("select m.clinitian_id as profession_id,m.professional_name,m.clini_standard as standard,"
    			 +" m.start_date,m.end_date from app.dha_clinicians_list_master m order by m.clini_standard");
         li = new ArrayList();
   
         while(rs.next())
         {
             li.add(rs.getString("profession_id"));
         }
     }
     
     
     else if(mode.equals("bankNameStad")){
    	 rs = st.executeQuery("select b.bank_seq_id, b.bank_name as bank_name from fin_app.tpa_bank_master b");				// autopopulate bank names
         li = new ArrayList();
   
         while(rs.next())
         {
             li.add(rs.getString("bank_name"));
         }
     }
     
     
     else if(mode.equals("prerequisite")){
    	 rs = st.executeQuery("select p.provider_id as provider_id,p.PROVIDER_name as provider_name,p.HEALTH_AUTHORITY as "+
     			" emirate from app.dha_providers_master p");
         li = new ArrayList();
   
         while(rs.next())
         {
             li.add(rs.getString("provider_name"));
         }
     }else if(mode.equals("productName")){//Administration -> Products -> General
    	 rs = st.executeQuery("select PACKAGE_ID, PACKAGENAME from app.DHA_BENFIT_PKG_MASTER");
         li = new ArrayList();
   
         while(rs.next())
         {
             li.add(rs.getString("PACKAGENAME"));
         }
     }
     else if(mode.equals("authStandard")){
    	 rs = st.executeQuery("SELECT PM.PAYER_AUTHORITY,PM.PAYER_ID,PM.PAYER_NAME FROM APP.DHA_PAYERS_MASTER PM where not exists(select * from APP.tpa_ins_info i where pm.payer_id = i.ins_comp_code_number)");
         li = new ArrayList();
   
         while(rs.next())
         {
             li.add(rs.getString("PAYER_NAME"));
         }
     }
     else if(mode.equals("brokerName")){
    	 rs = st.executeQuery("SELECT M.BRK_ID,M.COMPANY_NAME FROM APP.DHA_INS_BROKER_MASTER M ");
         li = new ArrayList();
   
         while(rs.next())
         {
             li.add(rs.getString("COMPANY_NAME"));
         }
     }
     else if(mode.equals("preAuthProfessions")){
    	 rs = st.executeQuery("SELECT (S.CONTACT_NAME||'@'||S.Professional_Id) AS CLINICIAN_NAME "+
    	           " FROM APP.tpa_hosp_professionals S where s.hosp_seq_id='"+hospSeqId+"'");
         li = new ArrayList();
   
         while(rs.next())
         {
             li.add(rs.getString("CLINICIAN_NAME"));
         }
     } else if(mode.equals("PayerName")){//INWARD ENTRY -> ENROLLMENT -> BATCH DETAILS
    	/*  rs = st.executeQuery("SELECT I.INS_COMP_NAME as INS_COMP_NAME,I.INS_SEQ_ID as INS_SEQ_ID,I.INS_COMP_CODE_NUMBER FROM APP.TPA_INS_INFO I  ORDER BY I.INS_SEQ_ID"); */
    	//rs = st.executeQuery("SELECT I.INS_COMP_NAME||'@'||I.INS_COMP_CODE_NUMBER as INS_COMP_NAME,I.INS_SEQ_ID as INS_SEQ_ID,I.INS_COMP_CODE_NUMBER FROM APP.TPA_INS_INFO I  ORDER BY I.INS_SEQ_ID");
        rs = st.executeQuery("SELECT I.INS_COMP_NAME||'@'||I.INS_COMP_CODE_NUMBER as INS_COMP_NAME,I.INS_SEQ_ID as INS_SEQ_ID,I.INS_COMP_CODE_NUMBER FROM APP.TPA_INS_INFO I WHERE I.ACTIVE_YN='Y' ORDER BY I.INS_SEQ_ID");	
    	li = new ArrayList();
   
         while(rs.next())
         {
             li.add(rs.getString("INS_COMP_NAME"));
         }
     }
     else if(mode.equals("professionsName")){
    	 rs = st.executeQuery("select m.clinitian_id as profession_id,(m.professional_name || '[' || clinitian_id || ']') as professional_name,m.clini_standard as standard, "+
					"m.start_date,m.end_date from app.dha_clinicians_list_master m order by m.clini_standard");
         li = new ArrayList();
   
         while(rs.next())
         {
             li.add(rs.getString("professional_name"));
         }
     }else if(mode.equals("bankName")){//finance level Guarantee details
    	 rs = st.executeQuery("SELECT TBM.BANK_NAME FROM FIN_APP.TPA_BANK_MASTER TBM");//WHERE TBM.OFFICE_TYPE='IHO'
         li = new ArrayList();
   
         while(rs.next())
         {
             li.add(rs.getString("BANK_NAME"));
         }
     }else if(mode.equals("brokerCode")){//Enrollment -COrp Scheme- Scheme details
    	 rs = st.executeQuery("SELECT INS_COMP_CODE_NUMBER FROM APP.TPA_BRO_INFO ");//
         li = new ArrayList();
   
         while(rs.next())
         {
             li.add(rs.getString("INS_COMP_CODE_NUMBER"));
         }
     }else if(mode.equals("reInsurer")){//Re-Insurer Name in Erollment -> Corporate SChemes
    	 rs = st.executeQuery("SELECT GROUP_NAME||'=>'||GROUP_ID AS GROUP_NAME FROM APP.TPA_REINSURER_REGISTRATION ");//
         li = new ArrayList();
   
         while(rs.next())
         {
             li.add(rs.getString("GROUP_NAME"));
         }
     }
     
     
       String[] str = new String[li.size()];
       Iterator it = li.iterator();
 
       int i = 0;
       while(it.hasNext())
       {
           String p = (String)it.next();
           str[i] = p;
           i++;
       }
       String query = (String)request.getParameter("q");       
       int cnt=1;
       for(int j=0;j<str.length;j++)
       {
           if(str[j].toUpperCase().startsWith(query.toUpperCase()))
           {
              out.print(str[j]+"\n");
              cnt++;
            }
       }
    

 
}
catch(Exception e){
e.printStackTrace();
}
   finally{
	 try{
		 rs.close(); 
	 }catch(Exception e){e.printStackTrace();}
	 
try{
	st.close(); 
	 }catch(Exception e){e.printStackTrace();}
try{
	con.close();
}catch(Exception e){e.printStackTrace();}
	   
		
	}
%>