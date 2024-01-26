package com.ttk.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLXML;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.InvestigationRuleVO;

public class RuleXmlUtility {

	public static HashMap<String,SQLXML> getConsltFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
		HashMap<String,SQLXML> hmSQLXmlTypes=new HashMap<>();
		
		Document staticDoc = DocumentHelper.createDocument();
	     Element rootFields = staticDoc.addElement("Consultation");
	     Element fields = rootFields.addElement("Fields");
	     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
	     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
	     fields.addAttribute("geo-location", investigationRuleVO.getGeoLocationID());
	     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
	     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());
	     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
	     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
	     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
	     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
	     fields.addAttribute("clinician-cons-yn", investigationRuleVO.getClncConsYN());
	     fields.addAttribute("follow-period", investigationRuleVO.getFollowUpPeriod());
	     fields.addAttribute("follow-period-type", investigationRuleVO.getFollowUpPeriodType());
	     
	     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
	     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
	     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
	     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
	     
	     //SQLXML commRuleFieldsXML = conn.createSQLXML();.setString( staticDoc.asXML());
	     SQLXML commRuleFieldsXML=conn.createSQLXML();
	     commRuleFieldsXML.setString(staticDoc.asXML());
	     hmSQLXmlTypes.put("StaticXml",commRuleFieldsXML );
	     
	     //creating provider type xml
	     Document proTypeDoc = DocumentHelper.createDocument();
	     Element rootProTypes = proTypeDoc.addElement("Provider-Types");
	     String strProTypes=investigationRuleVO.getProviderTypes();
	     if(strProTypes!=null){
	    	 String[]arrProTypes=strProTypes.split("[|]");
	    	 for(String proType:arrProTypes){
	    		 rootProTypes.addElement("type").setText(proType);
	    	 }
	     }
	     //SQLXML providerTypesXML = conn.createSQLXML();.setString( proTypeDoc.asXML());
	     SQLXML providerTypesXML=conn.createSQLXML();
	     providerTypesXML.setString(proTypeDoc.asXML());
	     hmSQLXmlTypes.put("ProviderTypes",providerTypesXML );
	     
	     //creating country xml
	     Document countryDoc = DocumentHelper.createDocument();
	     Element rootContries = countryDoc.addElement("Countries");
	     String strContries=investigationRuleVO.getCountryIDs();
	     if(strContries!=null){
	    	 String[]arrContries=strContries.split("[|]");
	    	 for(String country:arrContries){
	    		 rootContries.addElement("gen-id").setText(country);
	    	 }
	     }
	     //SQLXML countriesXML = conn.createSQLXML();.setString( countryDoc.asXML());
	     SQLXML countriesXML=conn.createSQLXML();
	     countriesXML.setString(countryDoc.asXML());
	     hmSQLXmlTypes.put("Countries",countriesXML );
		
	     //creating emirate xml
	     Document emirateDoc = DocumentHelper.createDocument();
	     Element rootEmirate = emirateDoc.addElement("Emirates");
	     String strEmirates=investigationRuleVO.getEmiratesIDs();
	     if(strEmirates!=null){
	    	 String[]arrEmirates=strEmirates.split("[|]");
	    	 for(String emirate:arrEmirates){
	    		 rootEmirate.addElement("gen-id").setText(emirate);
	    	 }
	     }
	     //SQLXML emirateXML = conn.createSQLXML();.setString( emirateDoc.asXML());
	     SQLXML emirateXML=conn.createSQLXML();
	     emirateXML.setString(emirateDoc.asXML());
	     hmSQLXmlTypes.put("Emirates",emirateXML );
	     
	   //creating encounter types xml
	     Document encountersDoc = DocumentHelper.createDocument();
	     Element rootEncounters = encountersDoc.addElement("Encounters");
	     String strEncounter=investigationRuleVO.getEncounterTypes();
	     if(strEncounter!=null){
	    	 String[]arrEncounters=strEncounter.split("[|]");
	    	 for(String encounetr:arrEncounters){
	    		 rootEncounters.addElement("type").setText(encounetr);
	    	 }
	     }
	     //SQLXML encountersXML = conn.createSQLXML();.setString( encountersDoc.asXML());
	     SQLXML encountersXML=conn.createSQLXML();
	     encountersXML.setString(encountersDoc.asXML());
	     hmSQLXmlTypes.put("Encounters",encountersXML );
	     
	   //creating clinician copay details  xml
	     Document clinCopayDoc = DocumentHelper.createDocument();
	     Element rootClin = clinCopayDoc.addElement("Clinicians");
	     String strClinCopayDetails=investigationRuleVO.getClinicianCopayDetails();
	     if(strClinCopayDetails!=null&&strClinCopayDetails.length()>1){
	    	 
	    	 String[]arrrClinCopayDetails=strClinCopayDetails.split("[|]");
	    if(arrrClinCopayDetails!=null&&arrrClinCopayDetails.length>0){
	    	 for(String copayDetails:arrrClinCopayDetails){
	    		 Element rootCoapyDtl = rootClin.addElement("Copay-Details");
	    		 
	    		 String arrGenIds[]=copayDetails.split("[@]");
	    		 if(arrGenIds!=null&&arrGenIds.length>0){
	    		 String arrCpDetails[]=arrGenIds[1].split("[_]");
	    		 if(arrCpDetails!=null&&arrCpDetails.length>0){
	    		 rootCoapyDtl.addAttribute("type", arrGenIds[0]);
	    		 rootCoapyDtl.addAttribute("copay", arrCpDetails[0]);
	    		 rootCoapyDtl.addAttribute("deduct", arrCpDetails[1]);
	    		 rootCoapyDtl.addAttribute("flag", arrCpDetails[2]);
	    		 }
	    		 }
	    	 }
	    }// if(arrrClinCopayDetails!null&&arrrClinCopayDetails.length>0){
	     }
	     //SQLXML copayDetailsXML = conn.createSQLXML();.setString( clinCopayDoc.asXML());
	     SQLXML copayDetailsXML=conn.createSQLXML();
	     copayDetailsXML.setString(clinCopayDoc.asXML());
	     hmSQLXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
	     
return hmSQLXmlTypes;
	}

	public static void getConsConfdtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
	
	
	investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	
	
	SQLXML sdXmlType =(SQLXML) rs.getObject("STATIC_COND");
	
	 if(sdXmlType!=null){
       // SAXReader saxReader = new SAXReader();
         SAXReader saxreader=new SAXReader();
		 Document  staticdoc =  saxreader.read(sdXmlType.getCharacterStream());
      
       Node fieldNode=staticdoc.selectSingleNode("/Consultation/Fields");
       if(fieldNode!=null){
       investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
       investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
       investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));
       investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
       investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
       investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
       investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
       investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
       investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
       investRuleVO.setClncConsYN(fieldNode.valueOf("@clinician-cons-yn"));
       investRuleVO.setFollowUpPeriod(fieldNode.valueOf("@follow-period"));
       investRuleVO.setFollowUpPeriodType(fieldNode.valueOf("@follow-period-type"));
       investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
       investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
       investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
       investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
       }//if(fieldNode!=null){
       }//if(sdXmlType!=null){
   	
	 SQLXML encXmlType =rs.getSQLXML("ENCOUNTER_TYPE");
   	if(encXmlType!=null){
           //SAXReader saxReader = new SAXReader();
   		  SAXReader saxreader=new SAXReader(); 
   		  Document  encdoc = saxreader.read(encXmlType.getCharacterStream());
          List<Node> listType=encdoc.selectNodes("/Encounters/type");
          
          if(listType!=null){
        	  StringBuilder stringBuilder=new StringBuilder();
        	  for(Node typeNode:listType){
        		  stringBuilder.append(typeNode.getText());
        		  stringBuilder.append("|");
        	  }
        	  
        	  String strEncounters=stringBuilder.toString();
        	  if(strEncounters.length()>0)investRuleVO.setEncounterTypes(strEncounters.substring(0, strEncounters.length()-1));
          }
   	}//if(encXmlType!=null){
   	
   	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
   	if(prfXmlType!=null){
           //SAXReader saxReader = new SAXReader();
          SAXReader saxreader=new SAXReader();
   		  Document  doc = saxreader.read(prfXmlType.getCharacterStream());
          List<Node> listType=doc.selectNodes("/Provider-Types/type");
          
          if(listType!=null){
        	  StringBuilder stringBuilder=new StringBuilder();
        	  for(Node typeNode:listType){
        		  stringBuilder.append(typeNode.getText());
        		  stringBuilder.append("|");
        	  }
        	  
        	  String strPRF=stringBuilder.toString();
        	  if(strPRF.length()>0)investRuleVO.setProviderTypes(strPRF.substring(0, strPRF.length()-1));
          }
   	}//if(prfXmlType!=null){
   	
   	SQLXML countryXmlType =rs.getSQLXML("COUNTRIES_ID");
   	if(countryXmlType!=null){
           //SAXReader saxReader = new SAXReader();
          SAXReader saxreader=new SAXReader();
   		  Document  doc = saxreader.read(countryXmlType.getCharacterStream());
          List<Node> listType=doc.selectNodes("/Countries/gen-id");
          
          if(listType!=null){
        	  StringBuilder stringBuilder=new StringBuilder();
        	  for(Node typeNode:listType){
        		  stringBuilder.append(typeNode.getText());
        		  stringBuilder.append("|");
        	  }
        	  
        	  String strCon=stringBuilder.toString();
        	  if(strCon.length()>0)investRuleVO.setCountryIDs(strCon.substring(0, strCon.length()-1));
          }
   	}//if(countryXmlType!=null){
   	

   	SQLXML emrtXmlType =rs.getSQLXML("EMIRATES_ID");
   	if(emrtXmlType!=null){
           SAXReader saxreader = new SAXReader();
          Document  doc = saxreader.read(emrtXmlType.getCharacterStream());
          List<Node> listType=doc.selectNodes("/Emirates/gen-id");
          
          if(listType!=null){
        	  StringBuilder stringBuilder=new StringBuilder();
        	  for(Node typeNode:listType){
        		  stringBuilder.append(typeNode.getText());
        		  stringBuilder.append("|");
        	  }
        	  
        	  String strEmrt=stringBuilder.toString();
        	  if(strEmrt.length()>0)investRuleVO.setEmiratesIDs(strEmrt.substring(0, strEmrt.length()-1));
          }
   	}//if(countryXmlType!=null){
   	//GPH@10_100_MAX|DNPH@20_200_MIN
	SQLXML clconsXmlType =rs.getSQLXML("CLINICIAN_CONSULTATION");
   	if(clconsXmlType!=null){
           SAXReader saxReader = new SAXReader();
          Document  doc = saxReader.read(clconsXmlType.getCharacterStream());
          List<Node> listType=doc.selectNodes("/Clinicians/Copay-Details");
          
          if(listType!=null){
        	  StringBuilder stringBuilder=new StringBuilder();
        	  for(Node typeNode:listType){
        		  stringBuilder.append(TTKCommon.checkNull(typeNode.valueOf("@type")));
        		  stringBuilder.append("@");
        		  stringBuilder.append(TTKCommon.checkNull(typeNode.valueOf("@copay")));
        		  stringBuilder.append("_");
        		  stringBuilder.append(TTKCommon.checkNull(typeNode.valueOf("@deduct")));
        		  stringBuilder.append("_");
        		  stringBuilder.append(TTKCommon.checkNull(typeNode.valueOf("@flag")));        		 
        		  stringBuilder.append("|");
        	  }
        	  
        	  String strClCons=stringBuilder.toString();
        	  if(strClCons.length()>0)investRuleVO.setClinicianCopayDetails(strClCons.substring(0, strClCons.length()-1));
          }
   	}//if(countryXmlType!=null){
   	
	}
	

	public static void getInvestConfDtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
		
		
		investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	
		
		SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");
		
		 if(sdXmlType!=null){
	        SAXReader saxaReader = new SAXReader();
	       Document  staticdoc =  saxaReader.read(sdXmlType.getCharacterStream());
	      
	       Node fieldNode=staticdoc.selectSingleNode("/Investigations/Fields");
	       if(fieldNode!=null){
	       investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
	       investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
	       investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));
	       investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
	       investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
	       investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
	       investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
	       investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
	       investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
	       investRuleVO.setClncConsYN(fieldNode.valueOf("@clinician-cons-yn"));
	       investRuleVO.setFollowUpPeriod(fieldNode.valueOf("@follow-period"));
	       investRuleVO.setFollowUpPeriodType(fieldNode.valueOf("@follow-period-type"));
	       investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
	       investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
	       investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
	       investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
	       investRuleVO.setInvestTypeName(fieldNode.valueOf("@master-code"));
	       investRuleVO.setNoOfSessAllowPerPolicy(fieldNode.valueOf("@noof-sess-per-policy"));
	       }//if(fieldNode!=null){
	       }//if(sdXmlType!=null){
	   	
		
	   	
	   	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
	   	if(prfXmlType!=null){
	           SAXReader saxReader = new SAXReader();
	          Document  doc = saxReader.read(prfXmlType.getCharacterStream());
	          List<Node> listType=doc.selectNodes("/Provider-Types/type");
	          
	          if(listType!=null){
	        	  StringBuilder stringBuilder=new StringBuilder();
	        	  for(Node typeNode:listType){
	        		  stringBuilder.append(typeNode.getText());
	        		  stringBuilder.append("|");
	        	  }
	        	  
	        	  String strPRF=stringBuilder.toString();
	        	  if(strPRF.length()>0)investRuleVO.setProviderTypes(strPRF.substring(0, strPRF.length()-1));
	          }
	   	}//if(prfXmlType!=null){
	   	
		}
	
public static void getPhysioConfDtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
		
		investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	
		
		SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");
		
		 if(sdXmlType!=null){
	        SAXReader saxReader = new SAXReader();
	       Document  staticdoc =  saxReader.read(sdXmlType.getCharacterStream());
	      
	       Node fieldNode=staticdoc.selectSingleNode("/Physiotherapy/Fields");
	       if(fieldNode!=null){
	       investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
	       investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
	       investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
	       investRuleVO.setNoOfSessAllowPerPolicy(fieldNode.valueOf("@noof-sess-per-policy"));
	       investRuleVO.setLimitPerSession(fieldNode.valueOf("@per-sess-limit"));
	       investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
	       investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
	       investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
	       investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
	       investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
	       investRuleVO.setCopayDedPerSessionLimit(fieldNode.valueOf("@per-sess-cd-limit"));
	       investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
	       investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
	       }//if(fieldNode!=null){
	       }//if(sdXmlType!=null){
	   	
		
	   	
	   	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
	   	if(prfXmlType!=null){
	           SAXReader saxReader = new SAXReader();
	          Document  doc = saxReader.read(prfXmlType.getCharacterStream());
	          List<Node> listType=doc.selectNodes("/Provider-Types/type");
	          
	          if(listType!=null){
	        	  StringBuilder stringBuilder=new StringBuilder();
	        	  for(Node typeNode:listType){
	        		  stringBuilder.append(typeNode.getText());
	        		  stringBuilder.append("|");
	        	  }
	        	  
	        	  String strPRF=stringBuilder.toString();
	        	  if(strPRF.length()>0)investRuleVO.setProviderTypes(strPRF.substring(0, strPRF.length()-1));
	          }
	   	}//if(prfXmlType!=null){
	   	
		}
	

	public static HashMap<String,SQLXML> getInvestFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
		HashMap<String,SQLXML> hmSQLXmlTypes=new HashMap<>();
		
		Document staticDoc = DocumentHelper.createDocument();
	     Element rootFields = staticDoc.addElement("Investigations");
	     Element fields = rootFields.addElement("Fields");
	     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
	     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
	     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
	     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());
	     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
	     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
	     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
	     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
	     fields.addAttribute("noof-sess-per-policy", investigationRuleVO.getNoOfSessAllowPerPolicy());
	     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
	     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
	     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
	     fields.addAttribute("invest-type-label", investigationRuleVO.getInvestTypeLabel());
	     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
	     
	       //SQLXML commRuleFieldsXML =  conn.createSQLXML();.setString( staticDoc.asXML());
	       SQLXML commRuleFieldsXML=conn.createSQLXML();
	       commRuleFieldsXML.setString(staticDoc.asXML());
	       hmSQLXmlTypes.put("StaticXml",commRuleFieldsXML );
	     //creating provider type xml
	      Document proTypeDoc = DocumentHelper.createDocument();
	      Element rootProTypes = proTypeDoc.addElement("Provider-Types");
	      String strProTypes=investigationRuleVO.getProviderTypes();
	      if(strProTypes!=null){
	    	 String[]arrProTypes=strProTypes.split("[|]");
	    	 for(String proType:arrProTypes){
	    		 rootProTypes.addElement("type").setText(proType);
	    	 }
	     }
	     //SQLXML providerTypesXML = conn.createSQLXML();.setString( proTypeDoc.asXML());
	      SQLXML providerTypesXML=conn.createSQLXML();
	      providerTypesXML.setString(proTypeDoc.asXML());
	      hmSQLXmlTypes.put("ProviderTypes",providerTypesXML );
	     
	     
	     //SQLXML countriesXML =  conn.createSQLXML();.setString( "<empty/>");
	      SQLXML countriesXML=conn.createSQLXML();
	      countriesXML.setString("<empty/>");
	      hmSQLXmlTypes.put("Countries",countriesXML );
		
	   
	     //SQLXML emirateXML =  conn.createSQLXML();.setString( "<empty/>");
	      SQLXML emirateXML=conn.createSQLXML();
	      emirateXML.setString("<empty/>");
	      hmSQLXmlTypes.put("Emirates",emirateXML );
	     
	 
	     //SQLXML encountersXML = conn.createSQLXML();.setString( "<empty/>");
	      SQLXML encountersXML=conn.createSQLXML();
	      encountersXML.setString("<empty/>");
	      hmSQLXmlTypes.put("Encounters",encountersXML );
	     
	   
	     //SQLXML copayDetailsXML =  conn.createSQLXML();.setString( "<empty/>");
	      SQLXML copayDetailsXML=conn.createSQLXML();
	      copayDetailsXML.setString("<empty/>");
	      hmSQLXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
	     
return hmSQLXmlTypes;
	}
	public static HashMap<String,SQLXML> getPhysioFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
		HashMap<String,SQLXML> hmSQLXmlTypes=new HashMap<>();
		
		Document staticDoc = DocumentHelper.createDocument();
	     Element rootFields = staticDoc.addElement("Physiotherapy");
	     Element fields = rootFields.addElement("Fields");
	     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
	     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
	     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
	     fields.addAttribute("noof-sess-per-policy", investigationRuleVO.getNoOfSessAllowPerPolicy());
	     fields.addAttribute("per-sess-limit", investigationRuleVO.getLimitPerSession());
	     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
	     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
	     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
	     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
	     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
	     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
	    
	     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
	     fields.addAttribute("per-sess-cd-limit", investigationRuleVO.getCopayDedPerSessionLimit());
	     
	     //SQLXML commRuleFieldsXML = conn.createSQLXML();.setString( staticDoc.asXML());
	     SQLXML commRuleFieldsXML=conn.createSQLXML();
	     commRuleFieldsXML.setString(staticDoc.asXML());
	     hmSQLXmlTypes.put("StaticXml",commRuleFieldsXML );
	     
	     //creating provider type xml
	     Document proTypeDoc = DocumentHelper.createDocument();
	     Element rootProTypes = proTypeDoc.addElement("Provider-Types");
	     String strProTypes=investigationRuleVO.getProviderTypes();
	     if(strProTypes!=null){
	    	 String[]arrProTypes=strProTypes.split("[|]");
	    	 for(String proType:arrProTypes){
	    		 rootProTypes.addElement("type").setText(proType);
	    	 }
	     }
	     //SQLXML providerTypesXML = conn.createSQLXML();.setString( proTypeDoc.asXML());
	     SQLXML providerTypesXML=conn.createSQLXML();
	     providerTypesXML.setString(proTypeDoc.asXML());
	     hmSQLXmlTypes.put("ProviderTypes",providerTypesXML );
	     
	     
	     //SQLXML countriesXML = conn.createSQLXML();.setString( "<empty/>");
	     SQLXML countriesXML=conn.createSQLXML();
	     countriesXML.setString("<empty/>");
	     hmSQLXmlTypes.put("Countries",countriesXML );
		
	   
	     //SQLXML emirateXML = conn.createSQLXML();.setString( "<empty/>");
	     SQLXML emirateXML=conn.createSQLXML();
	     emirateXML.setString("<empty/>");
	     hmSQLXmlTypes.put("Emirates",emirateXML );
	     
	 
	     //SQLXML encountersXML = conn.createSQLXML();.setString( "<empty/>");
	     SQLXML encountersXML=conn.createSQLXML();
	     encountersXML.setString("<empty/>");
	     hmSQLXmlTypes.put("Encounters",encountersXML );
	     
	   
	     //SQLXML copayDetailsXML = conn.createSQLXML();.setString( "<empty/>");
	     SQLXML copayDetailsXML=conn.createSQLXML();
	     copayDetailsXML.setString("<empty/>");
	     hmSQLXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
	     
return hmSQLXmlTypes;
	}
	
public static void getPharmaConfDtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
		
		investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	
		
		SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");
		
		 if(sdXmlType!=null){
	       SAXReader saxReader = new SAXReader();
	       Document  staticdoc =  saxReader.read(sdXmlType.getCharacterStream());
	      
	       Node fieldNode=staticdoc.selectSingleNode("/Pharmaceutical/Fields");
	       if(fieldNode!=null){
	       investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
	       investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
	       investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
	       investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
	       investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
	       investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
	       investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
	       investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
	       investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
	       investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
	       investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
	       investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
	       
	       }//if(fieldNode!=null){
	       }//if(sdXmlType!=null){
	   	
		
	   	
	   	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
	   	if(prfXmlType!=null){
	           SAXReader saxReader = new SAXReader();
	          Document  doc = saxReader.read(prfXmlType.getCharacterStream());
	          List<Node> listType=doc.selectNodes("/Provider-Types/type");
	          
	          if(listType!=null){
	        	  StringBuilder stringBuilder=new StringBuilder();
	        	  for(Node typeNode:listType){
	        		  stringBuilder.append(typeNode.getText());
	        		  stringBuilder.append("|");
	        	  }
	        	  
	        	  String strPRF=stringBuilder.toString();
	        	  if(strPRF.length()>0)investRuleVO.setProviderTypes(strPRF.substring(0, strPRF.length()-1));
	          }
	   	}//if(prfXmlType!=null){
	   	
		}


	

public static HashMap<String,SQLXML> getPharmaFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
	HashMap<String,SQLXML> hmSQLXmlTypes=new HashMap<>();
	
	Document staticDoc = DocumentHelper.createDocument();
     Element rootFields = staticDoc.addElement("Pharmaceutical");
     Element fields = rootFields.addElement("Fields");
     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());     
     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
    
     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
     
     //SQLXML commRuleFieldsXML = conn.createSQLXML();.setString( staticDoc.asXML());
     SQLXML commRuleFieldsXML=conn.createSQLXML();
     commRuleFieldsXML.setString(staticDoc.asXML());
     hmSQLXmlTypes.put("StaticXml",commRuleFieldsXML );
     
     //creating provider type xml
     Document proTypeDoc = DocumentHelper.createDocument();
     Element rootProTypes = proTypeDoc.addElement("Provider-Types");
     String strProTypes=investigationRuleVO.getProviderTypes();
     if(strProTypes!=null){
    	 String[]arrProTypes=strProTypes.split("[|]");
    	 for(String proType:arrProTypes){
    		 rootProTypes.addElement("type").setText(proType);
    	 }
     }
     //SQLXML providerTypesXML = conn.createSQLXML();.setString( proTypeDoc.asXML());
     SQLXML providerTypesXML=conn.createSQLXML();
     providerTypesXML.setString(proTypeDoc.asXML());
     hmSQLXmlTypes.put("ProviderTypes",providerTypesXML );
     
     
     //SQLXML countriesXML = conn.createSQLXML();.setString( "<empty/>");
     SQLXML countriesXML=conn.createSQLXML();
     countriesXML.setString("<empty/>");
     hmSQLXmlTypes.put("Countries",countriesXML );
	
   
     //SQLXML emirateXML = conn.createSQLXML();.setString( "<empty/>");
     SQLXML emirateXML=conn.createSQLXML();
     emirateXML.setString("<empty/>");
     hmSQLXmlTypes.put("Emirates",emirateXML );
     
 
     //SQLXML encountersXML = conn.createSQLXML();.setString( "<empty/>");
     SQLXML encountersXML=conn.createSQLXML();
     encountersXML.setString("<empty/>");
     hmSQLXmlTypes.put("Encounters",encountersXML );
     
   
     //SQLXML copayDetailsXML = conn.createSQLXML();.setString( "<empty/>");
     SQLXML copayDetailsXML=conn.createSQLXML();
     copayDetailsXML.setString("<empty/>");
     hmSQLXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
     
return hmSQLXmlTypes;
}

public  static Document  getConfRuleConfigDetails(HttpServletRequest request,Long benefitRuleSeqID)throws Exception{
    
	  HttpSession session=request.getSession();
	  LinkedHashMap<String, Node> ruleBenefitNodeList=(LinkedHashMap<String, Node>)session.getServletContext().getAttribute("RuleBenefitNodeList");
	  
	  String[]sRadioNames=request.getParameterValues("sRadioNames");
	  String[]sRadioValues=request.getParameterValues("sRadioValues");
	 String strBenefitID=request.getParameter("benefitID");
	 String strBenefitName="";
	 Document document = DocumentHelper.createDocument();
   Element root = document.addElement("benefit");
  //System.out.println("benefitPayType="+request.getParameter("benefitPayType"));
  // root.addAttribute("condType",request.getParameter("benefitPayType") );
	  
   if(ruleBenefitNodeList!=null){
	  if(sRadioNames!=null){
		  HashSet<String> crmCheckCodeList=new HashSet<>();
		  
		  Node benefitNode=ruleBenefitNodeList.get(strBenefitID);
		  strBenefitName=benefitNode.valueOf("@name");
		// Node  confNode=(Node)benefitNode.clone();
	
		  
	  for(int i=0;i<sRadioNames.length;i++){
	 
		 Node sbNode= benefitNode.selectSingleNode("./sub-benefit[@id='"+sRadioNames[i]+"']");
		// Element conTypeEle=(Element)sbNode.selectSingleNode("./condition-type");
		String strFieldValue=sRadioValues[i];
		
		String strSbID=sbNode.valueOf("@id");
		
		 
		 Element subElm=root.addElement("sub-benefit").addAttribute("id", strSbID);
		 
		subElm.addElement("condition-type").addAttribute("default",strFieldValue);
		// conTypeEle.addAttribute("default", sRadioValues[i]);
		 
		 
		// if("3".equals( sRadioValues[i])){
			 
			 List<Node> conNodes=(List<Node>)sbNode.selectNodes("./condition");
			 
			 for(Node conNode:conNodes){
				//<condition act-mcode="" def-params="&apos;INC&apos;" fun-name="check_global_rule" icd-mcode="" id="C1">
				String fun_name=conNode.valueOf("@fun-name");
				String strMcYN=conNode.valueOf("@mcYN");
				String strConID=conNode.valueOf("@id");
				if(!("Y".equals(strMcYN)||"N".equals(strMcYN)))throw new TTKException("error.dublicate.crm.code","master code should contains (mcYN=Y/N) please check BenefitRules.xml file  benefit gid="+strBenefitID+", sub-benefit id="+strSbID+", condition id="+strConID+", please change and re-configure rule");
				if(fun_name!=null&&!"".equals(fun_name)){
				
				Element condElm=subElm.addElement("condition").addAttribute("id", conNode.valueOf("@id"));
				
				 condElm.addAttribute("def-params", conNode.valueOf("@def-params"));
				 condElm.addAttribute("fun-name", fun_name);
				 String strActMcode=conNode.valueOf("@act-mcode");
				 String strIcdMcode=conNode.valueOf("@icd-mcode");
				 if("Y".equals(strMcYN)){
				
					 if((strActMcode==null||strActMcode.trim().length()<1)&&(strIcdMcode==null||strIcdMcode.trim().length()<1))throw new TTKException("error.dublicate.crm.code","If master code is available(mcYN=Y) then either act-mcode or icd-mcode attribute are should not be empty please check BenefitRules.xml file   benefit gid="+strBenefitID+", sub-benefit id="+strSbID+", condition id="+strConID+", please change and re-configure rule");
						
					 
				 condElm.addAttribute("act-mcode", strActMcode);
				 condElm.addAttribute("icd-mcode", strIcdMcode);
				 condElm.addAttribute("mcYN", strMcYN);
				 }else {
					 String strCrmCode=conNode.valueOf("@crm-code");
						
						if(strCrmCode==null||strCrmCode.trim().length()<1)throw new TTKException("error.dublicate.crm.code","If master code is not available(mcYN=N) then crm-code attribute is should not be empty please check BenefitRules.xml file  benefit gid="+strBenefitID+", sub-benefit id="+strSbID+", condition id="+strConID+", please change and re-configure rule");
							
						
						if(!crmCheckCodeList.add(strCrmCode)) throw new TTKException("error.dublicate.crm.code",strCrmCode+" This crm-code repeated  more than one time withing benefit in BenefitRules.xml benefit gid="+strBenefitID+", sub-benefit id="+strSbID+", condition id="+strConID+", please change and re-configure rule");
					 
					 condElm.addAttribute("mcYN", strMcYN);
					 condElm.addAttribute("crm-code", strCrmCode);
				 }
				
				 
				
				 
				 List<Node> fieldNodes=(List<Node>)conNode.selectNodes("./field");
				 
				 HashSet<String> fieldNameHS=new HashSet<>();
				 
				 for(Node fieldNode:fieldNodes){
					 
					 Element fieldElm=condElm.addElement("field");
					String strFieldName= fieldNode.valueOf("@name");
					
					if( !fieldNameHS.add(strFieldName))throw new TTKException("error.dublicate.crm.code","In field element name should not duplicate please check BenefitRules.xml file  benefit gid="+strBenefitID+", sub-benefit id="+strSbID+", condition id="+strConID+",Field name="+strFieldName+" please change and re-configure rule");
					 
					 fieldElm.addAttribute("name", strFieldName);
					 
					String strDefault=fieldNode.valueOf("@default");
					// String strName=fieldNode.valueOf("@name");	
					if("3".equals(strFieldValue)){
					 String fieldValue=request.getParameter(strSbID+strConID+strFieldName);
					 if(fieldValue!=null&&fieldValue.length()>0)strDefault=fieldValue;
					}
					fieldElm.addAttribute("ctrType", fieldNode.valueOf("@ctrType"));
					fieldElm.addAttribute("default", strDefault);
				 }//if(fun_name!=null&&!"".equals(fun_name)){
				 }//for(Node fieldNode:fieldNodes){
				 //((Element)conNode).normalize();
			 }// for(Node conNode:conNodes){
			 
		// }//if("3".equals( sRadioValues[i])){
		
	  }//for(int i=0;i<sRadioNames.length;i++){
	
	
	  if(benefitRuleSeqID==null) request.setAttribute("displayMsg",strBenefitName+" Configuration Details Added Successfully!");
	  else request.setAttribute("displayMsg",strBenefitName+" Configuration Details Updated Successfully!");
	  
	 // System.out.println("confNode::"+confNode.asXML());
	  }//if(sRadioNames!=null){
	  }// if(ruleBenefitNodeList!=null){
	 return document; 
  }

public static HashMap<String,SQLXML> getCompanFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
	HashMap<String,SQLXML> hmSQLXmlTypes=new HashMap<>();
	
	Document staticDoc = DocumentHelper.createDocument();
     Element rootFields = staticDoc.addElement("Companion");
     Element fields = rootFields.addElement("Fields");     
     fields.addAttribute("geo-location", investigationRuleVO.getGeoLocationID());
     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());
     fields.addAttribute("age-range-from", investigationRuleVO.getAgeRangeFrom());
     fields.addAttribute("age-range-to", investigationRuleVO.getAgeRangeTo());
     fields.addAttribute("noof-days-per-claim", investigationRuleVO.getNoOfdaysAllowPerCalim());
     fields.addAttribute("max-limit-per-day", investigationRuleVO.getMaxLimitAllowPerDay());
     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
     fields.addAttribute("follow-period-type", investigationRuleVO.getFollowUpPeriodType());     
     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
     
    // SQLXML commRuleFieldsXML = conn.createSQLXML();.setString( staticDoc.asXML());
     SQLXML commRuleFieldsXML=conn.createSQLXML();
     commRuleFieldsXML.setString(staticDoc.asXML());
     hmSQLXmlTypes.put("StaticXml",commRuleFieldsXML );
     
     //creating provider type xml
     Document proTypeDoc = DocumentHelper.createDocument();
     Element rootProTypes = proTypeDoc.addElement("Provider-Types");
     String strProTypes=investigationRuleVO.getProviderTypes();
     if(strProTypes!=null){
    	 String[]arrProTypes=strProTypes.split("[|]");
    	 for(String proType:arrProTypes){
    		 rootProTypes.addElement("type").setText(proType);
    	 }
     }
     //SQLXML providerTypesXML = conn.createSQLXML();.setString( proTypeDoc.asXML());
     SQLXML providerTypesXML=conn.createSQLXML();
     providerTypesXML.setString(proTypeDoc.asXML());
     hmSQLXmlTypes.put("ProviderTypes",providerTypesXML );
     
     
     //creating country xml
     Document countryDoc = DocumentHelper.createDocument();
     Element rootContries = countryDoc.addElement("Countries");
     String strContries=investigationRuleVO.getCountryIDs();
     if(strContries!=null){
    	 String[]arrContries=strContries.split("[|]");
    	 for(String country:arrContries){
    		 rootContries.addElement("gen-id").setText(country);
    	 }
     }
     //SQLXML countriesXML = conn.createSQLXML();.setString( countryDoc.asXML());
     SQLXML countriesXML=conn.createSQLXML();
     countriesXML.setString(countryDoc.asXML());
     hmSQLXmlTypes.put("Countries",countriesXML );
	
     //creating emirate xml
     Document emirateDoc = DocumentHelper.createDocument();
     Element rootEmirate = emirateDoc.addElement("Emirates");
     String strEmirates=investigationRuleVO.getEmiratesIDs();
     if(strEmirates!=null){
    	 String[]arrEmirates=strEmirates.split("[|]");
    	 for(String emirate:arrEmirates){
    		 rootEmirate.addElement("gen-id").setText(emirate);
    	 }
     }
     
     //SQLXML emirateXML = conn.createSQLXML();.setString( emirateDoc.asXML());
     SQLXML emirateXML=conn.createSQLXML();
     emirateXML.setString(emirateDoc.asXML());
     hmSQLXmlTypes.put("Emirates",emirateXML );
     
     Document hospTypeDoc = DocumentHelper.createDocument();
     Element rootHospTypes = hospTypeDoc.addElement("Hosp-Types");
     String strHospTypes=investigationRuleVO.getHospType();
     if(strHospTypes!=null){
    	 String[]arrHospTypes=strHospTypes.split("[|]");
    	 for(String hospType:arrHospTypes){
    		 rootHospTypes.addElement("type").setText(hospType);
    	 }
     }
     //SQLXML hospTypesXML = conn.createSQLXML();.setString( hospTypeDoc.asXML());
     SQLXML hospTypesXML=conn.createSQLXML();
     hospTypesXML.setString(hospTypeDoc.asXML());
     hmSQLXmlTypes.put("HospTypes",hospTypesXML );
     
     
     //SQLXML encountersXML = conn.createSQLXML();.setString( "<empty/>");
     SQLXML encountersXML=conn.createSQLXML();
     encountersXML.setString("<empty/>");
     hmSQLXmlTypes.put("Encounters",encountersXML );
     
   
     //SQLXML copayDetailsXML = conn.createSQLXML();.setString( "<empty/>");
     SQLXML copayDetailsXML=conn.createSQLXML();
     copayDetailsXML.setString("<empty/>");
     hmSQLXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
     
return hmSQLXmlTypes;
}

public static void getCompanConfdtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
	
	investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	
	
	SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");
	
	 if(sdXmlType!=null){
       SAXReader saxReader = new SAXReader();
       Document  staticdoc =  saxReader.read(sdXmlType.getCharacterStream());
      
       Node fieldNode=staticdoc.selectSingleNode("/Companion/Fields");
       if(fieldNode!=null){
       investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
       investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
       investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));
       investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
       investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
       investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
       investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
       investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
       investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
      
       investRuleVO.setAgeRangeFrom(fieldNode.valueOf("@age-range-from"));
       investRuleVO.setAgeRangeTo(fieldNode.valueOf("@age-range-to"));
       investRuleVO.setFollowUpPeriodType(fieldNode.valueOf("@follow-period-type"));
       investRuleVO.setNoOfdaysAllowPerCalim(fieldNode.valueOf("@noof-days-per-claim"));
       investRuleVO.setMaxLimitAllowPerDay(fieldNode.valueOf("@max-limit-per-day"));
      
       investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
       investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
       investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
       investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
       }//if(fieldNode!=null){
       }//if(sdXmlType!=null){
   	
	 SQLXML hospXmlType =rs.getSQLXML("PROVIDER_TYPE");
   	if(hospXmlType!=null){
           SAXReader saxReader = new SAXReader();
          Document  hospdoc = saxReader.read(hospXmlType.getCharacterStream());
          List<Node> listType=hospdoc.selectNodes("/Hosp-Types/type");
          
          if(listType!=null){
        	  StringBuilder stringBuilder=new StringBuilder();
        	  for(Node typeNode:listType){
        		  stringBuilder.append(typeNode.getText());
        		  stringBuilder.append("|");
        	  }
        	  
        	  String strHospTypes=stringBuilder.toString();
        	  if(strHospTypes.length()>0)investRuleVO.setHospType(strHospTypes.substring(0, strHospTypes.length()-1));
          }
   	}//if(encXmlType!=null){
   	
   	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
   	if(prfXmlType!=null){
          SAXReader saxReader = new SAXReader();
          Document  doc = saxReader.read(prfXmlType.getCharacterStream());
          List<Node> listType=doc.selectNodes("/Provider-Types/type");
          
          if(listType!=null){
        	  StringBuilder stringBuilder=new StringBuilder();
        	  for(Node typeNode:listType){
        		  stringBuilder.append(typeNode.getText());
        		  stringBuilder.append("|");
        	  }
        	  
        	  String strPRF=stringBuilder.toString();
        	  if(strPRF.length()>0)investRuleVO.setProviderTypes(strPRF.substring(0, strPRF.length()-1));
          }
   	}//if(prfXmlType!=null){
   	
   	SQLXML countryXmlType =rs.getSQLXML("COUNTRIES_ID");
   	if(countryXmlType!=null){
           SAXReader saxReader = new SAXReader();
          Document  doc = saxReader.read(countryXmlType.getCharacterStream());
          List<Node> listType=doc.selectNodes("/Countries/gen-id");
          
          if(listType!=null){
        	  StringBuilder stringBuilder=new StringBuilder();
        	  for(Node typeNode:listType){
        		  stringBuilder.append(typeNode.getText());
        		  stringBuilder.append("|");
        	  }
        	  
        	  String strCon=stringBuilder.toString();
        	  if(strCon.length()>0)investRuleVO.setCountryIDs(strCon.substring(0, strCon.length()-1));
          }
   	}//if(countryXmlType!=null){
   	

   	SQLXML emrtXmlType =rs.getSQLXML("EMIRATES_ID");
   	if(emrtXmlType!=null){
           SAXReader saxReader = new SAXReader();
          Document  doc = saxReader.read(emrtXmlType.getCharacterStream());
          List<Node> listType=doc.selectNodes("/Emirates/gen-id");
          
          if(listType!=null){
        	  StringBuilder stringBuilder=new StringBuilder();
        	  for(Node typeNode:listType){
        		  stringBuilder.append(typeNode.getText());
        		  stringBuilder.append("|");
        	  }
        	  
        	  String strEmrt=stringBuilder.toString();
        	  if(strEmrt.length()>0)investRuleVO.setEmiratesIDs(strEmrt.substring(0, strEmrt.length()-1));
          }
   	}//if(countryXmlType!=null){
   
   	
	}


public static HashMap<String,SQLXML> getAmbulanceFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
	HashMap<String,SQLXML> hmSQLXmlTypes=new HashMap<>();
	
	Document staticDoc = DocumentHelper.createDocument();
     Element rootFields = staticDoc.addElement("Ambulance");
     Element fields = rootFields.addElement("Fields");     
     fields.addAttribute("geo-location", investigationRuleVO.getGeoLocationID());
     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());     
     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());     
     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
     
     //SQLXML commRuleFieldsXML = conn.createSQLXML();.setString( staticDoc.asXML());
     SQLXML commRuleFieldsXML=conn.createSQLXML();
     commRuleFieldsXML.setString(staticDoc.asXML());
     hmSQLXmlTypes.put("StaticXml",commRuleFieldsXML );
     
     //creating provider type xml
     Document proTypeDoc = DocumentHelper.createDocument();
     Element rootProTypes = proTypeDoc.addElement("Provider-Types");
     String strProTypes=investigationRuleVO.getProviderTypes();
     if(strProTypes!=null){
    	 String[]arrProTypes=strProTypes.split("[|]");
    	 for(String proType:arrProTypes){
    		 rootProTypes.addElement("type").setText(proType);
    	 }
     }
     //SQLXML providerTypesXML = conn.createSQLXML();.setString( proTypeDoc.asXML());
     SQLXML providerTypesXML=conn.createSQLXML();
     providerTypesXML.setString(proTypeDoc.asXML());
     hmSQLXmlTypes.put("ProviderTypes",providerTypesXML );
     
     
     //creating country xml
     Document countryDoc = DocumentHelper.createDocument();
     Element rootContries = countryDoc.addElement("Countries");
     String strContries=investigationRuleVO.getCountryIDs();
     if(strContries!=null){
    	 String[]arrContries=strContries.split("[|]");
    	 for(String country:arrContries){
    		 rootContries.addElement("gen-id").setText(country);
    	 }
     }
     //SQLXML countriesXML = conn.createSQLXML();.setString( countryDoc.asXML());
     SQLXML countriesXML=conn.createSQLXML();
     countriesXML.setString(countryDoc.asXML());
     hmSQLXmlTypes.put("Countries",countriesXML );
	
     //creating emirate xml
     Document emirateDoc = DocumentHelper.createDocument();
     Element rootEmirate = emirateDoc.addElement("Emirates");
     String strEmirates=investigationRuleVO.getEmiratesIDs();
     if(strEmirates!=null){
    	 String[]arrEmirates=strEmirates.split("[|]");
    	 for(String emirate:arrEmirates){
    		 rootEmirate.addElement("gen-id").setText(emirate);
    	 }
     }
     
     //SQLXML emirateXML = conn.createSQLXML();.setString( emirateDoc.asXML());
     SQLXML emirateXML=conn.createSQLXML();
     emirateXML.setString(emirateDoc.asXML());
     hmSQLXmlTypes.put("Emirates",emirateXML );
     
     Document hospTypeDoc = DocumentHelper.createDocument();
     Element rootHospTypes = hospTypeDoc.addElement("Hosp-Types");
     String strHospTypes=investigationRuleVO.getHospType();
     if(strHospTypes!=null){
    	 String[]arrHospTypes=strHospTypes.split("[|]");
    	 for(String hospType:arrHospTypes){
    		 rootHospTypes.addElement("type").setText(hospType);
    	 }
     }
     //SQLXML hospTypesXML = conn.createSQLXML();.setString( hospTypeDoc.asXML());
     SQLXML hospTypesXML=conn.createSQLXML();
     hospTypesXML.setString(hospTypeDoc.asXML());
     hmSQLXmlTypes.put("HospTypes",hospTypesXML );
     
     //creating encounter types xml
     Document encountersDoc = DocumentHelper.createDocument();
     Element rootEncounters = encountersDoc.addElement("Encounters");
     String strEncounter=investigationRuleVO.getEncounterTypes();
     if(strEncounter!=null){
    	 String[]arrEncounters=strEncounter.split("[|]");
    	 for(String encounetr:arrEncounters){
    		 rootEncounters.addElement("type").setText(encounetr);
    	 }
     }
     //SQLXML encountersXML = conn.createSQLXML();.setString( encountersDoc.asXML());
     SQLXML encountersXML=conn.createSQLXML();
     encountersXML.setString(encountersDoc.asXML());
     hmSQLXmlTypes.put("Encounters",encountersXML );
     
     
   
     //SQLXML copayDetailsXML = conn.createSQLXML();.setString( "<empty/>");
     SQLXML copayDetailsXML=conn.createSQLXML();
     copayDetailsXML.setString("<empty/>");
     hmSQLXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
     
return hmSQLXmlTypes;
}


public static void getAmbulanceConfdtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
	
	investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	
	
	SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");
	
	 if(sdXmlType!=null){
        SAXReader saxReader = new SAXReader();
       Document  staticdoc =  saxReader.read(sdXmlType.getCharacterStream());
      
       Node fieldNode=staticdoc.selectSingleNode("/Ambulance/Fields");
       if(fieldNode!=null){
       investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
       investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
       investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));
       investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
       investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
       investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
       investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
       investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
       investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));

       investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
       investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
       investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
       investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
       }//if(fieldNode!=null){
       }//if(sdXmlType!=null){
   	
	 SQLXML hospXmlType =rs.getSQLXML("PROVIDER_TYPE");
   	if(hospXmlType!=null){
           SAXReader saxReader = new SAXReader();
          Document  hospdoc = saxReader.read(hospXmlType.getCharacterStream());
          List<Node> listType=hospdoc.selectNodes("/Hosp-Types/type");
          
          if(listType!=null){
        	  StringBuilder stringBuilder=new StringBuilder();
        	  for(Node typeNode:listType){
        		  stringBuilder.append(typeNode.getText());
        		  stringBuilder.append("|");
        	  }
        	  
        	  String strHospTypes=stringBuilder.toString();
        	  if(strHospTypes.length()>0)investRuleVO.setHospType(strHospTypes.substring(0, strHospTypes.length()-1));
          }
   	}//if(encXmlType!=null){
   	
   	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
   	if(prfXmlType!=null){
           SAXReader saxReader = new SAXReader();
          Document  doc = saxReader.read(prfXmlType.getCharacterStream());
          List<Node> listType=doc.selectNodes("/Provider-Types/type");
          
          if(listType!=null){
        	  StringBuilder stringBuilder=new StringBuilder();
        	  for(Node typeNode:listType){
        		  stringBuilder.append(typeNode.getText());
        		  stringBuilder.append("|");
        	  }
        	  
        	  String strPRF=stringBuilder.toString();
        	  if(strPRF.length()>0)investRuleVO.setProviderTypes(strPRF.substring(0, strPRF.length()-1));
          }
   	}//if(prfXmlType!=null){
   	
   	SQLXML countryXmlType =rs.getSQLXML("COUNTRIES_ID");
   	if(countryXmlType!=null){
          SAXReader saxReader = new SAXReader();
          Document  doc = saxReader.read(countryXmlType.getCharacterStream());
          List<Node> listType=doc.selectNodes("/Countries/gen-id");
          
          if(listType!=null){
        	  StringBuilder stringBuilder=new StringBuilder();
        	  for(Node typeNode:listType){
        		  stringBuilder.append(typeNode.getText());
        		  stringBuilder.append("|");
        	  }
        	  
        	  String strCon=stringBuilder.toString();
        	  if(strCon.length()>0)investRuleVO.setCountryIDs(strCon.substring(0, strCon.length()-1));
          }
   	}//if(countryXmlType!=null){
   	

   	SQLXML emrtXmlType =rs.getSQLXML("EMIRATES_ID");
   	if(emrtXmlType!=null){
           SAXReader saxReader = new SAXReader();
          Document  doc = saxReader.read(emrtXmlType.getCharacterStream());
          List<Node> listType=doc.selectNodes("/Emirates/gen-id");
          
          if(listType!=null){
        	  StringBuilder stringBuilder=new StringBuilder();
        	  for(Node typeNode:listType){
        		  stringBuilder.append(typeNode.getText());
        		  stringBuilder.append("|");
        	  }
        	  
        	  String strEmrt=stringBuilder.toString();
        	  if(strEmrt.length()>0)investRuleVO.setEmiratesIDs(strEmrt.substring(0, strEmrt.length()-1));
          }
   	}//if(countryXmlType!=null){
   
    SQLXML encXmlType =rs.getSQLXML("ENCOUNTER_TYPE");
   	if(encXmlType!=null){
          SAXReader saxReader = new SAXReader();
          Document  encdoc = saxReader.read(encXmlType.getCharacterStream());
          List<Node> listType=encdoc.selectNodes("/Encounters/type");
          
          if(listType!=null){
        	  StringBuilder stringBuilder=new StringBuilder();
        	  for(Node typeNode:listType){
        		  stringBuilder.append(typeNode.getText());
        		  stringBuilder.append("|");
        	  }
        	  
        	  String strEncounters=stringBuilder.toString();
        	  if(strEncounters.length()>0)investRuleVO.setEncounterTypes(strEncounters.substring(0, strEncounters.length()-1));
          }
   	}//	if(encXmlType!=null){
	
}
public static void getConditionFunctions(String benefitID,Document document,Element dynBenefitType,Element staticBenefitType)throws Exception{
    
	List<Node> sbNodes=document.selectNodes("//sub-benefit");
		
	for(Node sbNode:sbNodes){		
		 
		String strcnType=sbNode.selectSingleNode("./condition-type").valueOf("@default");
		
		List<Node> conNodes=sbNode.selectNodes(".//condition");
		String strSBid=sbNode.valueOf("@id");
		for(Node conNode:conNodes){
			
			String strMcYN=conNode.valueOf("@mcYN");			
			StringBuilder funSB=new StringBuilder();
			funSB.append(conNode.valueOf("@fun-name"));
		
			List<Node> fieldNodes=conNode.selectNodes(".//field");
			
			funSB.append("(");
			String strDefParam=conNode.valueOf("@def-params");
			if(strDefParam!=null&&strDefParam.length()>1){
			funSB.append(strDefParam);
			funSB.append(",");
			}
			
			for(Node fieldNode:fieldNodes){
			
				String strCtrType=fieldNode.valueOf("@ctrType");
				if(strCtrType!=null&&strCtrType.length()>1&&!"img".equals(strCtrType)){
				  funSB.append("'");
					funSB.append(TTKCommon.checkNull(fieldNode.valueOf("@default")));
					funSB.append("'");
					funSB.append(",");						
				}
				}//for(Node fieldNode:fieldNodes){
		
				if(funSB.length()>1)funSB.deleteCharAt(funSB.length()-1);
				funSB.append(")");
			
			
			if("Y".equals(strMcYN)) {
				Element dynConElem = dynBenefitType.addElement("condition");	
				String strActMcode=conNode.valueOf("@act-mcode");
				String strIcdMcode=conNode.valueOf("@icd-mcode");
				
				dynConElem.addAttribute("conType",strcnType);			
				dynConElem.addAttribute("dynFun",funSB.toString());	
				dynConElem.addAttribute("id",strSBid+"-"+conNode.valueOf("@id"));	
				
				 dynConElem.addAttribute("act-mcode",strActMcode);	
				 dynConElem.addAttribute("icd-mcode",strIcdMcode);
			}else{
				Element statConElem = staticBenefitType.addElement("condition");				
				statConElem.addAttribute("conType",strcnType);	
				String strCrmCode=conNode.valueOf("@crm-code");
				statConElem.addAttribute("crm-code",strCrmCode);
				statConElem.addAttribute("dynFun",funSB.toString());
				statConElem.addAttribute("id",strSBid+"-"+conNode.valueOf("@id"));
			}
			
			
			}//for(Node conNode:conNodes){
        }//for(Node sbNode:sbNodes){

}
//development by govind for Special Benefits

public static HashMap<String,SQLXML> getVaccinFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
	HashMap<String,SQLXML> hmXmlTypes=new HashMap<>();
	
	Document staticDoc = DocumentHelper.createDocument();
     Element rootFields = staticDoc.addElement("Vaccinations");
     Element fields = rootFields.addElement("Fields");
     fields.addAttribute("claim-type", investigationRuleVO.getClaimType());
     fields.addAttribute("network-type", investigationRuleVO.getNetworkYN());
     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
     fields.addAttribute("geo-location", investigationRuleVO.getGeoLocationID());
     fields.addAttribute("provider-type", investigationRuleVO.getProviderTypes());
     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());
     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
     fields.addAttribute("min-age", investigationRuleVO.getMinAge());
     fields.addAttribute("max-age", investigationRuleVO.getMaxAge());
     fields.addAttribute("allowed-age-type", investigationRuleVO.getAllowedAgeType());
     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
     
     SQLXML commRuleFieldsXML = conn.createSQLXML();
     commRuleFieldsXML.setString( staticDoc.asXML());
     hmXmlTypes.put("StaticXml",commRuleFieldsXML );
     
     //creating ProviderFaclityTypes type xml
     Document proTypeDoc = DocumentHelper.createDocument();
     Element rootProFaclityTypes = proTypeDoc.addElement("Provider-Faclity-Types");
     String strProTypes=investigationRuleVO.getProviderTypesID();
     if(strProTypes!=null){
    	 String[]arrProFaclityTypes=strProTypes.split("[|]");
    	 for(String proFaclityType:arrProFaclityTypes){
    		 rootProFaclityTypes.addElement("type").setText(proFaclityType);
    	 }
     }
     SQLXML providerTypesXML = conn.createSQLXML();
     providerTypesXML.setString( proTypeDoc.asXML());
     hmXmlTypes.put("ProviderFaclityTypes",providerTypesXML );
 
	   //creating encounter types xml
     Document encountersDoc = DocumentHelper.createDocument();
     Element rootEncounters = encountersDoc.addElement("Encounters");
     String strEncounter=investigationRuleVO.getEncounterTypes();
     if(strEncounter!=null){
    	 String[]arrEncounters=strEncounter.split("[|]");
    	 for(String encounetr:arrEncounters){
    		 rootEncounters.addElement("type").setText(encounetr);
    	 }
     }
     SQLXML encountersXML = conn.createSQLXML();
     encountersXML.setString( encountersDoc.asXML());
     hmXmlTypes.put("Encounters",encountersXML );
     
     
     
     //creating country xml
     Document countryDoc = DocumentHelper.createDocument();
     Element rootContries = countryDoc.addElement("Countries");
     String strContries=investigationRuleVO.getCountryIDs();
     if(strContries!=null){
    	 String[]arrContries=strContries.split("[|]");
    	 for(String country:arrContries){
    		 rootContries.addElement("gen-id").setText(country);
    	 }
     }
     SQLXML countriesXML = conn.createSQLXML();
     countriesXML.setString( countryDoc.asXML());
     hmXmlTypes.put("Countries",countriesXML );
	
     //creating emirate xml
     Document emirateDoc = DocumentHelper.createDocument();
     Element rootEmirate = emirateDoc.addElement("Emirates");
     String strEmirates=investigationRuleVO.getEmiratesIDs();
     if(strEmirates!=null){
    	 String[]arrEmirates=strEmirates.split("[|]");
    	 for(String emirate:arrEmirates){
    		 rootEmirate.addElement("gen-id").setText(emirate);
    	 }
     }
     SQLXML emirateXML = conn.createSQLXML();
     emirateXML.setString( emirateDoc.asXML());
     hmXmlTypes.put("Emirates",emirateXML );
     
	   //creating clinician copay details  xml
     Document clinCopayDoc = DocumentHelper.createDocument();
     Element rootClin = clinCopayDoc.addElement("Clinicians");
     String strClinCopayDetails=investigationRuleVO.getClinicianCopayDetails();
     if(strClinCopayDetails!=null&&strClinCopayDetails.length()>1){
    	 
    	 String[]arrrClinCopayDetails=strClinCopayDetails.split("[|]");
    if(arrrClinCopayDetails!=null&&arrrClinCopayDetails.length>0){
    	 for(String copayDetails:arrrClinCopayDetails){
    		 Element rootCoapyDtl = rootClin.addElement("Copay-Details");
    		 
    		 String arrGenIds[]=copayDetails.split("[@]");
    		 if(arrGenIds!=null&&arrGenIds.length>0){
    		 String arrCpDetails[]=arrGenIds[1].split("[_]");
    		 if(arrCpDetails!=null&&arrCpDetails.length>0){
    		 rootCoapyDtl.addAttribute("type", arrGenIds[0]);
    		 rootCoapyDtl.addAttribute("copay", arrCpDetails[0]);
    		 rootCoapyDtl.addAttribute("deduct", arrCpDetails[1]);
    		 rootCoapyDtl.addAttribute("flag", arrCpDetails[2]);
    		 }
    		 }
    	 }
    }// if(arrrClinCopayDetails!null&&arrrClinCopayDetails.length>0){
     }
     SQLXML copayDetailsXML = conn.createSQLXML();
     copayDetailsXML.setString( clinCopayDoc.asXML());
     hmXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
     
     
return hmXmlTypes;
}


public static void getVaccinConfDtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
	
	
investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	

SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");

 if(sdXmlType!=null){
    SAXReader saxReader = new SAXReader();
   Document  staticdoc = saxReader.read(sdXmlType.getCharacterStream());
   
   
  
   Node fieldNode=staticdoc.selectSingleNode("/Vaccinations/Fields");
   if(fieldNode!=null){
   investRuleVO.setClaimType(fieldNode.valueOf("@claim-type"));  
   investRuleVO.setNetworkYN(fieldNode.valueOf("@network-type"));
   investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
   investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
   investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));  
   investRuleVO.setProviderTypes(fieldNode.valueOf("@provider-type"));
   investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
   investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
   investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
   investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
   investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
   investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
   investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
   investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
   investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
   investRuleVO.setMinAge(fieldNode.valueOf("@min-age"));
   investRuleVO.setMaxAge(fieldNode.valueOf("@max-age"));
   investRuleVO.setAllowedAgeType(fieldNode.valueOf("@allowed-age-type"));
   investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
   }//if(fieldNode!=null){
   }//if(sdXmlType!=null){
	
 SQLXML encXmlType =rs.getSQLXML("ENCOUNTER_TYPE");
	if(encXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  encdoc = saxReader.read(encXmlType.getCharacterStream());
      List<Node> listType=encdoc.selectNodes("/Encounters/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEncounters=stringBuilder.toString();
    	  if(strEncounters.length()>0)investRuleVO.setEncounterTypes(strEncounters.substring(0, strEncounters.length()-1));
      }
	}//if(encXmlType!=null){
	
	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
	if(prfXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(prfXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Provider-Faclity-Types/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strPRF=stringBuilder.toString();
    	  if(strPRF.length()>0)investRuleVO.setProviderTypesID(strPRF.substring(0, strPRF.length()-1));
      }
	}//if(prfXmlType!=null){
	
	SQLXML countryXmlType =rs.getSQLXML("COUNTRIES_ID");
	if(countryXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(countryXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Countries/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strCon=stringBuilder.toString();
    	  if(strCon.length()>0)investRuleVO.setCountryIDs(strCon.substring(0, strCon.length()-1));
      }
	}//if(countryXmlType!=null){
	

	SQLXML emrtXmlType =rs.getSQLXML("EMIRATES_ID");
	if(emrtXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(emrtXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Emirates/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEmrt=stringBuilder.toString();
    	  if(strEmrt.length()>0)investRuleVO.setEmiratesIDs(strEmrt.substring(0, strEmrt.length()-1));
      }
	}//if(countryXmlType!=null){

	
}

public static void getAltTmtConfdtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
	
	
investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	

SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");

 if(sdXmlType!=null){
    SAXReader saxReader = new SAXReader();
   Document  staticdoc = saxReader.read(sdXmlType.getCharacterStream());
  
   Node fieldNode=staticdoc.selectSingleNode("/Alternate-Treatments/Fields");
   if(fieldNode!=null){
   investRuleVO.setClaimType(fieldNode.valueOf("@claim-type"));  
   investRuleVO.setNetworkYN(fieldNode.valueOf("@network-type"));
   investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
   investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
   investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));  
   investRuleVO.setProviderTypes(fieldNode.valueOf("@provider-type"));
   investRuleVO.setAltTmtTypes(fieldNode.valueOf("@alternate-treatment-types"));
   investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@limit-per-policy"));
   investRuleVO.setNoOfSessAllowPerPolicy(fieldNode.valueOf("@no-of-sessions-allowed"));
   investRuleVO.setLimitPerSession(fieldNode.valueOf("@limit-per-session"));
   investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
   investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
   investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
   investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
   investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
   investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
   investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
 
   investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
   }//if(fieldNode!=null){
   }//if(sdXmlType!=null){
	
 SQLXML encXmlType =rs.getSQLXML("ENCOUNTER_TYPE");
	if(encXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  encdoc = saxReader.read(encXmlType.getCharacterStream());
      List<Node> listType=encdoc.selectNodes("/Encounters/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEncounters=stringBuilder.toString();
    	  if(strEncounters.length()>0)investRuleVO.setEncounterTypes(strEncounters.substring(0, strEncounters.length()-1));
      }
	}//if(encXmlType!=null){
	
	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
	if(prfXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(prfXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Provider-Faclity-Types/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strPRF=stringBuilder.toString();
    	  if(strPRF.length()>0)investRuleVO.setProviderTypesID(strPRF.substring(0, strPRF.length()-1));
      }
	}//if(prfXmlType!=null){
	
	SQLXML countryXmlType =rs.getSQLXML("COUNTRIES_ID");
	if(countryXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(countryXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Countries/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strCon=stringBuilder.toString();
    	  if(strCon.length()>0)investRuleVO.setCountryIDs(strCon.substring(0, strCon.length()-1));
      }
	}//if(countryXmlType!=null){
	

	SQLXML emrtXmlType =rs.getSQLXML("EMIRATES_ID");
	if(emrtXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(emrtXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Emirates/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEmrt=stringBuilder.toString();
    	  if(strEmrt.length()>0)investRuleVO.setEmiratesIDs(strEmrt.substring(0, strEmrt.length()-1));
      }
	}//if(countryXmlType!=null){

	
}


public static HashMap<String,SQLXML> getAltTmtFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
	HashMap<String,SQLXML> hmXmlTypes=new HashMap<>();
	
	Document staticDoc = DocumentHelper.createDocument();
     Element rootFields = staticDoc.addElement("Alternate-Treatments");
     Element fields = rootFields.addElement("Fields");
     fields.addAttribute("claim-type", investigationRuleVO.getClaimType());
     fields.addAttribute("network-type", investigationRuleVO.getNetworkYN());
     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
     fields.addAttribute("geo-location", investigationRuleVO.getGeoLocationID());
     fields.addAttribute("provider-type", investigationRuleVO.getProviderTypes());
     fields.addAttribute("alternate-treatment-types", investigationRuleVO.getAltTmtTypes());
     fields.addAttribute("limit-per-policy", investigationRuleVO.getPerPolicyLimit());
     fields.addAttribute("no-of-sessions-allowed", investigationRuleVO.getNoOfSessAllowPerPolicy());
     fields.addAttribute("limit-per-session", investigationRuleVO.getLimitPerSession());
     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
   
     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
     
     SQLXML commRuleFieldsXML = conn.createSQLXML();
     commRuleFieldsXML.setString( staticDoc.asXML());
     hmXmlTypes.put("StaticXml",commRuleFieldsXML );
     
     //creating ProviderFaclityTypes type xml
     Document proTypeDoc = DocumentHelper.createDocument();
     Element rootProFaclityTypes = proTypeDoc.addElement("Provider-Faclity-Types");
     String strProTypes=investigationRuleVO.getProviderTypesID();
     if(strProTypes!=null){
    	 String[]arrProFaclityTypes=strProTypes.split("[|]");
    	 for(String proFaclityType:arrProFaclityTypes){
    		 rootProFaclityTypes.addElement("type").setText(proFaclityType);
    	 }
     }
     SQLXML providerTypesXML = conn.createSQLXML();
     providerTypesXML.setString( proTypeDoc.asXML());
     hmXmlTypes.put("ProviderFaclityTypes",providerTypesXML );
 
	   //creating encounter types xml
     Document encountersDoc = DocumentHelper.createDocument();
     Element rootEncounters = encountersDoc.addElement("Encounters");
     String strEncounter=investigationRuleVO.getEncounterTypes();
     if(strEncounter!=null){
    	 String[]arrEncounters=strEncounter.split("[|]");
    	 for(String encounetr:arrEncounters){
    		 rootEncounters.addElement("type").setText(encounetr);
    	 }
     }
     SQLXML encountersXML = conn.createSQLXML();
     encountersXML.setString( encountersDoc.asXML());
     hmXmlTypes.put("Encounters",encountersXML );
     
     
     
     //creating country xml
     Document countryDoc = DocumentHelper.createDocument();
     Element rootContries = countryDoc.addElement("Countries");
     String strContries=investigationRuleVO.getCountryIDs();
     if(strContries!=null){
    	 String[]arrContries=strContries.split("[|]");
    	 for(String country:arrContries){
    		 rootContries.addElement("gen-id").setText(country);
    	 }
     }
     SQLXML countriesXML = conn.createSQLXML();
     countriesXML.setString( countryDoc.asXML());
     hmXmlTypes.put("Countries",countriesXML );
	
     //creating emirate xml
     Document emirateDoc = DocumentHelper.createDocument();
     Element rootEmirate = emirateDoc.addElement("Emirates");
     String strEmirates=investigationRuleVO.getEmiratesIDs();
     if(strEmirates!=null){
    	 String[]arrEmirates=strEmirates.split("[|]");
    	 for(String emirate:arrEmirates){
    		 rootEmirate.addElement("gen-id").setText(emirate);
    	 }
     }
     SQLXML emirateXML = conn.createSQLXML();
     emirateXML.setString( emirateDoc.asXML());
     hmXmlTypes.put("Emirates",emirateXML );
     
	   //creating clinician copay details  xml
     Document clinCopayDoc = DocumentHelper.createDocument();
     Element rootClin = clinCopayDoc.addElement("Clinicians");
     String strClinCopayDetails=investigationRuleVO.getClinicianCopayDetails();
     if(strClinCopayDetails!=null&&strClinCopayDetails.length()>1){
    	 
    	 String[]arrrClinCopayDetails=strClinCopayDetails.split("[|]");
    if(arrrClinCopayDetails!=null&&arrrClinCopayDetails.length>0){
    	 for(String copayDetails:arrrClinCopayDetails){
    		 Element rootCoapyDtl = rootClin.addElement("Copay-Details");
    		 
    		 String arrGenIds[]=copayDetails.split("[@]");
    		 if(arrGenIds!=null&&arrGenIds.length>0){
    		 String arrCpDetails[]=arrGenIds[1].split("[_]");
    		 if(arrCpDetails!=null&&arrCpDetails.length>0){
    		 rootCoapyDtl.addAttribute("type", arrGenIds[0]);
    		 rootCoapyDtl.addAttribute("copay", arrCpDetails[0]);
    		 rootCoapyDtl.addAttribute("deduct", arrCpDetails[1]);
    		 rootCoapyDtl.addAttribute("flag", arrCpDetails[2]);
    		 }
    		 }
    	 }
    }// if(arrrClinCopayDetails!null&&arrrClinCopayDetails.length>0){
     }
     SQLXML copayDetailsXML = conn.createSQLXML();
     copayDetailsXML.setString( clinCopayDoc.asXML());
     hmXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
      
return hmXmlTypes;
}


public static HashMap<String,SQLXML> getDEOTFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
	HashMap<String,SQLXML> hmXmlTypes=new HashMap<>();
	
	Document staticDoc = DocumentHelper.createDocument();
     Element rootFields = staticDoc.addElement("Dnr-Exp-Or-Tp");
     Element fields = rootFields.addElement("Fields");
     fields.addAttribute("claim-type", investigationRuleVO.getClaimType());
     fields.addAttribute("network-type", investigationRuleVO.getNetworkYN());
     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
     fields.addAttribute("geo-location", investigationRuleVO.getGeoLocationID());
     fields.addAttribute("provider-type", investigationRuleVO.getProviderTypes());
     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());
     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
   
     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
     
     SQLXML commRuleFieldsXML = conn.createSQLXML();
     commRuleFieldsXML.setString( staticDoc.asXML());
     hmXmlTypes.put("StaticXml",commRuleFieldsXML );
     
     //creating ProviderFaclityTypes type xml
     Document proTypeDoc = DocumentHelper.createDocument();
     Element rootProFaclityTypes = proTypeDoc.addElement("Provider-Faclity-Types");
     String strProTypes=investigationRuleVO.getProviderTypesID();
     if(strProTypes!=null){
    	 String[]arrProFaclityTypes=strProTypes.split("[|]");
    	 for(String proFaclityType:arrProFaclityTypes){
    		 rootProFaclityTypes.addElement("type").setText(proFaclityType);
    	 }
     }
     SQLXML providerTypesXML = conn.createSQLXML();
     providerTypesXML.setString( proTypeDoc.asXML());
     hmXmlTypes.put("ProviderFaclityTypes",providerTypesXML );
 
	   //creating encounter types xml
     Document encountersDoc = DocumentHelper.createDocument();
     Element rootEncounters = encountersDoc.addElement("Encounters");
     String strEncounter=investigationRuleVO.getEncounterTypes();
     if(strEncounter!=null){
    	 String[]arrEncounters=strEncounter.split("[|]");
    	 for(String encounetr:arrEncounters){
    		 rootEncounters.addElement("type").setText(encounetr);
    	 }
     }
     SQLXML encountersXML = conn.createSQLXML();
     encountersXML.setString( encountersDoc.asXML());
     hmXmlTypes.put("Encounters",encountersXML );
     
     
     
     //creating country xml
     Document countryDoc = DocumentHelper.createDocument();
     Element rootContries = countryDoc.addElement("Countries");
     String strContries=investigationRuleVO.getCountryIDs();
     if(strContries!=null){
    	 String[]arrContries=strContries.split("[|]");
    	 for(String country:arrContries){
    		 rootContries.addElement("gen-id").setText(country);
    	 }
     }
     SQLXML countriesXML = conn.createSQLXML();
     countriesXML.setString( countryDoc.asXML());
     hmXmlTypes.put("Countries",countriesXML );
	
     //creating emirate xml
     Document emirateDoc = DocumentHelper.createDocument();
     Element rootEmirate = emirateDoc.addElement("Emirates");
     String strEmirates=investigationRuleVO.getEmiratesIDs();
     if(strEmirates!=null){
    	 String[]arrEmirates=strEmirates.split("[|]");
    	 for(String emirate:arrEmirates){
    		 rootEmirate.addElement("gen-id").setText(emirate);
    	 }
     }
     SQLXML emirateXML = conn.createSQLXML();
     emirateXML.setString( emirateDoc.asXML());
     hmXmlTypes.put("Emirates",emirateXML );
     
	   //creating clinician copay details  xml
     Document clinCopayDoc = DocumentHelper.createDocument();
     Element rootClin = clinCopayDoc.addElement("Clinicians");
     String strClinCopayDetails=investigationRuleVO.getClinicianCopayDetails();
     if(strClinCopayDetails!=null&&strClinCopayDetails.length()>1){
    	 
    	 String[]arrrClinCopayDetails=strClinCopayDetails.split("[|]");
    if(arrrClinCopayDetails!=null&&arrrClinCopayDetails.length>0){
    	 for(String copayDetails:arrrClinCopayDetails){
    		 Element rootCoapyDtl = rootClin.addElement("Copay-Details");
    		 
    		 String arrGenIds[]=copayDetails.split("[@]");
    		 if(arrGenIds!=null&&arrGenIds.length>0){
    		 String arrCpDetails[]=arrGenIds[1].split("[_]");
    		 if(arrCpDetails!=null&&arrCpDetails.length>0){
    		 rootCoapyDtl.addAttribute("type", arrGenIds[0]);
    		 rootCoapyDtl.addAttribute("copay", arrCpDetails[0]);
    		 rootCoapyDtl.addAttribute("deduct", arrCpDetails[1]);
    		 rootCoapyDtl.addAttribute("flag", arrCpDetails[2]);
    		 }
    		 }
    	 }
    }// if(arrrClinCopayDetails!null&&arrrClinCopayDetails.length>0){
     }
     SQLXML copayDetailsXML = conn.createSQLXML();
     copayDetailsXML.setString( clinCopayDoc.asXML());
     hmXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
      
return hmXmlTypes;
}
public static void getDEOTConfdtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
	
	
investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	

SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");

 if(sdXmlType!=null){
    SAXReader saxReader = new SAXReader();
   Document  staticdoc = saxReader.read(sdXmlType.getCharacterStream());
  
   Node fieldNode=staticdoc.selectSingleNode("/Dnr-Exp-Or-Tp/Fields");
   if(fieldNode!=null){
   investRuleVO.setClaimType(fieldNode.valueOf("@claim-type"));  
   investRuleVO.setNetworkYN(fieldNode.valueOf("@network-type"));
   investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
   investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
   investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));  
   investRuleVO.setProviderTypes(fieldNode.valueOf("@provider-type"));
   investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
   investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
   investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
   investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
   investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
   investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
   investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
   investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
   investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
 
   investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
   }//if(fieldNode!=null){
   }//if(sdXmlType!=null){
	
 SQLXML encXmlType =rs.getSQLXML("ENCOUNTER_TYPE");
	if(encXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  encdoc = saxReader.read(encXmlType.getCharacterStream());
      List<Node> listType=encdoc.selectNodes("/Encounters/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEncounters=stringBuilder.toString();
    	  if(strEncounters.length()>0)investRuleVO.setEncounterTypes(strEncounters.substring(0, strEncounters.length()-1));
      }
	}//if(encXmlType!=null){
	
	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
	if(prfXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(prfXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Provider-Faclity-Types/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strPRF=stringBuilder.toString();
    	  if(strPRF.length()>0)investRuleVO.setProviderTypesID(strPRF.substring(0, strPRF.length()-1));
      }
	}//if(prfXmlType!=null){
	
	SQLXML countryXmlType =rs.getSQLXML("COUNTRIES_ID");
	if(countryXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(countryXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Countries/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strCon=stringBuilder.toString();
    	  if(strCon.length()>0)investRuleVO.setCountryIDs(strCon.substring(0, strCon.length()-1));
      }
	}//if(countryXmlType!=null){
	

	SQLXML emrtXmlType =rs.getSQLXML("EMIRATES_ID");
	if(emrtXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(emrtXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Emirates/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEmrt=stringBuilder.toString();
    	  if(strEmrt.length()>0)investRuleVO.setEmiratesIDs(strEmrt.substring(0, strEmrt.length()-1));
      }
	}//if(countryXmlType!=null){

	
}

public static HashMap<String,SQLXML> getSpecialBenefitsFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
	
	String SpcbType=investigationRuleVO.getInvsType();
	  Element rootFields = null;
	HashMap<String,SQLXML> hmXmlTypes=new HashMap<>();
	
	Document staticDoc = DocumentHelper.createDocument();
	
	if(SpcbType.equals("ATMT")){
		
		rootFields = staticDoc.addElement("Alternate-Treatments");
		
	}
	else if(SpcbType.equals("DEOT")){
		
		rootFields = staticDoc.addElement("Dnr-Exp-Or-Tp");
		
	}
    else if(SpcbType.equals("EYCR")){
    	
    	rootFields = staticDoc.addElement("Eye-Care");
		
	}
else if(SpcbType.equals("GNGY")){
	
	rootFields = staticDoc.addElement("Gynaecology");
		
	}
else if(SpcbType.equals("MISG")){
	
	rootFields = staticDoc.addElement("Minor-Surgeries");
	
}
else if(SpcbType.equals("NASL")){
	
	rootFields = staticDoc.addElement("Nasal-Correction-Septoplasty");
	
}
else if(SpcbType.equals("ONGY")){
	
	rootFields = staticDoc.addElement("Oncology");
	
}
else if(SpcbType.equals("OTTR")){
	
	rootFields = staticDoc.addElement("Or-Tp-Rcpt");
	
}
else if(SpcbType.equals("PSYC")){
	
	rootFields = staticDoc.addElement("Psychiatric");
	
}

else if(SpcbType.equals("RNDL")){
	
	rootFields = staticDoc.addElement("Renal-Dialysis");
	
}
else if(SpcbType.equals("VCIN")){
	
	rootFields = staticDoc.addElement("Vaccinations");
	
}

	
    // rootFields = staticDoc.addElement("Eye-Care");
     Element fields = rootFields.addElement("Fields");
   //  fields.addAttribute("claim-type", investigationRuleVO.getClaimType());
  //   fields.addAttribute("network-type", investigationRuleVO.getNetworkYN());
     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
     fields.addAttribute("geo-location", investigationRuleVO.getGeoLocationID());
   //  fields.addAttribute("provider-type", investigationRuleVO.getProviderTypes());
    // fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());
     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
   
     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
     
	if(SpcbType.equals("ATMT")){
		
		     fields.addAttribute("alternate-treatment-types", investigationRuleVO.getAltTmtTypes());
		     fields.addAttribute("limit-per-policy", investigationRuleVO.getPerPolicyLimit());
		     fields.addAttribute("no-of-sessions-allowed", investigationRuleVO.getNoOfSessAllowPerPolicy());
		     fields.addAttribute("limit-per-session", investigationRuleVO.getLimitPerSession());
		
	}
	else{
		
		  fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
		
	}
	
	if(SpcbType.equals("VCIN")){
		
		 fields.addAttribute("min-age", investigationRuleVO.getMinAge());
	     fields.addAttribute("max-age", investigationRuleVO.getMaxAge());
	     fields.addAttribute("allowed-age-type", investigationRuleVO.getAllowedAgeType());
	
}
     
     // new change start
     
     //creating claim type type xml
     Document claimTypeDoc = DocumentHelper.createDocument();
     Element rootClaimFaclityTypes = claimTypeDoc.addElement("claim-type");
     String strClaimTypes=investigationRuleVO.getClaimType();
     if(strClaimTypes!=null){
    	 String[]arrClaimFaclityTypes=strClaimTypes.split("[|]");
    	 for(String claimFaclityType:arrClaimFaclityTypes){
    		 rootClaimFaclityTypes.addElement("type").setText(claimFaclityType);
    	 }
     }
     SQLXML claimTypesXML = conn.createSQLXML();
     claimTypesXML.setString( claimTypeDoc.asXML());
     hmXmlTypes.put("claimType",claimTypesXML );
     
     //creating network type xml
     Document nwkTypeDoc = DocumentHelper.createDocument();
     Element rootNwkFaclityTypes = nwkTypeDoc.addElement("network-type");
     String strNwkTypes=investigationRuleVO.getNetworkYN();
     if(strNwkTypes!=null){
    	 String[]arrNwkFaclityTypes=strNwkTypes.split("[|]");
    	 for(String nwkFaclityType:arrNwkFaclityTypes){
    		 rootNwkFaclityTypes.addElement("type").setText(nwkFaclityType);
    	 }
     }
     SQLXML networkTypesXML = conn.createSQLXML();
     networkTypesXML.setString( nwkTypeDoc.asXML());
     hmXmlTypes.put("networkType",networkTypesXML );
     
     //creating provider type xml
     Document hospTypeDoc = DocumentHelper.createDocument();
     Element rootHospTypes = hospTypeDoc.addElement("Hosp-Types");
     String strHospTypes=investigationRuleVO.getProviderTypes();
     if(strHospTypes!=null){
    	 String[]arrHospTypes=strHospTypes.split("[|]");
    	 for(String hospType:arrHospTypes){
    		 rootHospTypes.addElement("type").setText(hospType);
    	 }
     }
     SQLXML hospTypesXML = conn.createSQLXML();
     hospTypesXML.setString( hospTypeDoc.asXML());
     hmXmlTypes.put("HospTypes",hospTypesXML );
     // new change end
     
     SQLXML commRuleFieldsXML = conn.createSQLXML();
     commRuleFieldsXML.setString( staticDoc.asXML());
     hmXmlTypes.put("StaticXml",commRuleFieldsXML );
     
     //creating ProviderFaclityTypes type xml
     Document proTypeDoc = DocumentHelper.createDocument();
     Element rootProFaclityTypes = proTypeDoc.addElement("Provider-Faclity-Types");
     String strProTypes=investigationRuleVO.getProviderTypesID();
     if(strProTypes!=null){
    	 String[]arrProFaclityTypes=strProTypes.split("[|]");
    	 for(String proFaclityType:arrProFaclityTypes){
    		 rootProFaclityTypes.addElement("type").setText(proFaclityType);
    	 }
     }
     SQLXML providerTypesXML = conn.createSQLXML();
     providerTypesXML.setString( proTypeDoc.asXML());
     hmXmlTypes.put("ProviderFaclityTypes",providerTypesXML );
 
	   //creating encounter types xml
     Document encountersDoc = DocumentHelper.createDocument();
     Element rootEncounters = encountersDoc.addElement("Encounters");
     String strEncounter=investigationRuleVO.getEncounterTypes();
     if(strEncounter!=null){
    	 String[]arrEncounters=strEncounter.split("[|]");
    	 for(String encounetr:arrEncounters){
    		 rootEncounters.addElement("type").setText(encounetr);
    	 }
     }
     SQLXML encountersXML = conn.createSQLXML();
     encountersXML.setString( encountersDoc.asXML());
     hmXmlTypes.put("Encounters",encountersXML );
     
     
     
     //creating country xml
     Document countryDoc = DocumentHelper.createDocument();
     Element rootContries = countryDoc.addElement("Countries");
     String strContries=investigationRuleVO.getCountryIDs();
     if(strContries!=null){
    	 String[]arrContries=strContries.split("[|]");
    	 for(String country:arrContries){
    		 rootContries.addElement("gen-id").setText(country);
    	 }
     }
     SQLXML countriesXML = conn.createSQLXML();
     countriesXML.setString( countryDoc.asXML());
     hmXmlTypes.put("Countries",countriesXML );
	
     //creating emirate xml
     Document emirateDoc = DocumentHelper.createDocument();
     Element rootEmirate = emirateDoc.addElement("Emirates");
     String strEmirates=investigationRuleVO.getEmiratesIDs();
     if(strEmirates!=null){
    	 String[]arrEmirates=strEmirates.split("[|]");
    	 for(String emirate:arrEmirates){
    		 rootEmirate.addElement("gen-id").setText(emirate);
    	 }
     }
     SQLXML emirateXML = conn.createSQLXML();
     emirateXML.setString( emirateDoc.asXML());
     hmXmlTypes.put("Emirates",emirateXML );
     
	  
     SQLXML copayDetailsXML = conn.createSQLXML();
     copayDetailsXML.setString( "<Empty/>");
     hmXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
      
return hmXmlTypes;
}

public static void getSpecialBenefitsConfdtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
	
	String SpcbType=investRuleVO.getInvsType();	
	Node fieldNode= null;
investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	

SQLXML sdsqlxml =(SQLXML) rs.getObject("STATIC_COND");

 if(sdsqlxml!=null){
    SAXReader saxreader = new SAXReader();
   Document  staticdoc =  saxreader.read(sdsqlxml.getCharacterStream());
   
	if(SpcbType.equals("ATMT")){
		
		fieldNode=staticdoc.selectSingleNode("/Alternate-Treatments/Fields");
		
	}
	else if(SpcbType.equals("DEOT")){
	
		 fieldNode=staticdoc.selectSingleNode("/Dnr-Exp-Or-Tp/Fields");
		
	}
    else if(SpcbType.equals("EYCR")){
    	
    	fieldNode=staticdoc.selectSingleNode("/Eye-Care/Fields");
		
	}
else if(SpcbType.equals("GNGY")){
	
	fieldNode=staticdoc.selectSingleNode("/Gynaecology/Fields");
		
	}
else if(SpcbType.equals("MISG")){
	
	fieldNode=staticdoc.selectSingleNode("/Minor-Surgeries/Fields");
	
}
else if(SpcbType.equals("NASL")){
	
	fieldNode=staticdoc.selectSingleNode("/Nasal-Correction-Septoplasty/Fields");
	
}
else if(SpcbType.equals("ONGY")){
	
	fieldNode=staticdoc.selectSingleNode("/Oncology/Fields");
	
}
else if(SpcbType.equals("OTTR")){
	
	fieldNode=staticdoc.selectSingleNode("/Or-Tp-Rcpt/Fields");
	
}
else if(SpcbType.equals("PSYC")){
	
	fieldNode=staticdoc.selectSingleNode("/Psychiatric/Fields");
	
}

else if(SpcbType.equals("RNDL")){
	
	fieldNode=staticdoc.selectSingleNode("/Renal-Dialysis/Fields");
	
}

else if(SpcbType.equals("VCIN")){
	
	fieldNode=staticdoc.selectSingleNode("/Vaccinations/Fields");
	
}
   
   
  
  
   if(fieldNode!=null){
 //  investRuleVO.setClaimType(fieldNode.valueOf("@claim-type"));  
 //  investRuleVO.setNetworkYN(fieldNode.valueOf("@network-type"));
   investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
   investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
   investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));  
 //  investRuleVO.setProviderTypes(fieldNode.valueOf("@provider-type"));
   
   investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
   investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
   investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
   investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
   investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
   investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
   investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
   investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
 
   investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
   
	if(SpcbType.equals("ATMT")){
		
		investRuleVO.setAltTmtTypes(rs.getString("ACT_MASTER_CODE"));
		   investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@limit-per-policy"));
		   investRuleVO.setNoOfSessAllowPerPolicy(fieldNode.valueOf("@no-of-sessions-allowed"));
		   investRuleVO.setLimitPerSession(fieldNode.valueOf("@limit-per-session"));
	
}
else{
	
	investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
	
}
	
	if(SpcbType.equals("VCIN")){
		
		 investRuleVO.setMinAge(fieldNode.valueOf("@min-age"));
		   investRuleVO.setMaxAge(fieldNode.valueOf("@max-age"));
	
}
   
   
   }//if(fieldNode!=null){
   }//if(sdXmlType!=null){
 
 
 // new 
 
 SQLXML clmsqlxml =rs.getSQLXML("CLAIM_TYPE");
	if(clmsqlxml!=null){
       SAXReader saxReader = new SAXReader();
      Document  encdoc = saxReader.read(clmsqlxml.getCharacterStream());
      List<Node> listType=encdoc.selectNodes("/claim-type/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEncounters=stringBuilder.toString();
    	  if(strEncounters.length()>0)investRuleVO.setClaimType(strEncounters.substring(0, strEncounters.length()-1));
      }
    
	}//if(encXmlType!=null){
	
	 SQLXML nwksqlxml =rs.getSQLXML("NETWORK_TYPE");
		if(nwksqlxml!=null){
	       SAXReader saxReader = new SAXReader();
	      Document  encdoc = saxReader.read(nwksqlxml.getCharacterStream());
	      List<Node> listType=encdoc.selectNodes("/network-type/type");
	      
	      if(listType!=null){
	    	  StringBuilder stringBuilder=new StringBuilder();
	    	  for(Node typeNode:listType){
	    		  stringBuilder.append(typeNode.getText());
	    		  stringBuilder.append("|");
	    	  }
	    	  
	    	  String strEncounters=stringBuilder.toString();
	    	  if(strEncounters.length()>0)investRuleVO.setNetworkYN(strEncounters.substring(0, strEncounters.length()-1));
	      }
		}//if(encXmlType!=null){
		
		 SQLXML pvdsqlxml =rs.getSQLXML("PROVIDER_TYPE");
			if(pvdsqlxml!=null){
		      SAXReader saxReader = new SAXReader();
		      Document  encdoc = saxReader.read(pvdsqlxml.getCharacterStream());
		      List<Node> listType=encdoc.selectNodes("/Hosp-Types/type");
		      
		      if(listType!=null){
		    	  StringBuilder stringBuilder=new StringBuilder();
		    	  for(Node typeNode:listType){
		    		  stringBuilder.append(typeNode.getText());
		    		  stringBuilder.append("|");
		    	  }
		    	  
		    	  String strEncounters=stringBuilder.toString();
		    	  if(strEncounters.length()>0)investRuleVO.setProviderTypes(strEncounters.substring(0, strEncounters.length()-1));
		      }
			}//if(encXmlType!=null){
 
 
 // new end
 
 
	
 SQLXML encsqlxml =rs.getSQLXML("ENCOUNTER_TYPE");
	if(encsqlxml!=null){
       SAXReader saxReader = new SAXReader();
      Document  encdoc = saxReader.read(encsqlxml.getCharacterStream());
      List<Node> listType=encdoc.selectNodes("/Encounters/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEncounters=stringBuilder.toString();
    	  if(strEncounters.length()>0)investRuleVO.setEncounterTypes(strEncounters.substring(0, strEncounters.length()-1));
      }
	}//if(encXmlType!=null){
	
	SQLXML prfsqlxml =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
	if(prfsqlxml!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(prfsqlxml.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Provider-Faclity-Types/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strPRF=stringBuilder.toString();
    	  if(strPRF.length()>0)investRuleVO.setProviderTypesID(strPRF.substring(0, strPRF.length()-1));
      }
	}//if(prfXmlType!=null){
	
	SQLXML countrysqlxml =rs.getSQLXML("COUNTRIES_ID");
	if(countrysqlxml!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(countrysqlxml.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Countries/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strCon=stringBuilder.toString();
    	  if(strCon.length()>0)investRuleVO.setCountryIDs(strCon.substring(0, strCon.length()-1));
      }
	}//if(countryXmlType!=null){
	

	SQLXML emrtsqlxml =rs.getSQLXML("EMIRATES_ID");
	if(emrtsqlxml!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(emrtsqlxml.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Emirates/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEmrt=stringBuilder.toString();
    	  if(strEmrt.length()>0)investRuleVO.setEmiratesIDs(strEmrt.substring(0, strEmrt.length()-1));
      }
	}//if(countryXmlType!=null){

	
}



public static HashMap<String,SQLXML> getEyeCareFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
	HashMap<String,SQLXML> hmXmlTypes=new HashMap<>();
	
	Document staticDoc = DocumentHelper.createDocument();
     Element rootFields = staticDoc.addElement("Eye-Care");
     Element fields = rootFields.addElement("Fields");
   //  fields.addAttribute("claim-type", investigationRuleVO.getClaimType());
  //   fields.addAttribute("network-type", investigationRuleVO.getNetworkYN());
     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
     fields.addAttribute("geo-location", investigationRuleVO.getGeoLocationID());
   //  fields.addAttribute("provider-type", investigationRuleVO.getProviderTypes());
     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());
     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
   
     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
     
     // new change start
     
     //creating claim type type xml
     Document claimTypeDoc = DocumentHelper.createDocument();
     Element rootClaimFaclityTypes = claimTypeDoc.addElement("claim-type");
     String strClaimTypes=investigationRuleVO.getClaimType();
     if(strClaimTypes!=null){
    	 String[]arrClaimFaclityTypes=strClaimTypes.split("[|]");
    	 for(String claimFaclityType:arrClaimFaclityTypes){
    		 rootClaimFaclityTypes.addElement("type").setText(claimFaclityType);
    	 }
     }
     SQLXML claimTypesXML = conn.createSQLXML();
     claimTypesXML.setString( claimTypeDoc.asXML());
     hmXmlTypes.put("claimType",claimTypesXML );
     
     //creating network type xml
     Document nwkTypeDoc = DocumentHelper.createDocument();
     Element rootNwkFaclityTypes = nwkTypeDoc.addElement("network-type");
     String strNwkTypes=investigationRuleVO.getNetworkYN();
     if(strNwkTypes!=null){
    	 String[]arrNwkFaclityTypes=strNwkTypes.split("[|]");
    	 for(String nwkFaclityType:arrNwkFaclityTypes){
    		 rootNwkFaclityTypes.addElement("type").setText(nwkFaclityType);
    	 }
     }
     SQLXML networkTypesXML = conn.createSQLXML();
     networkTypesXML.setString( nwkTypeDoc.asXML());
     hmXmlTypes.put("networkType",networkTypesXML );
     
     //creating provider type xml
     Document hospTypeDoc = DocumentHelper.createDocument();
     Element rootHospTypes = hospTypeDoc.addElement("Hosp-Types");
     String strHospTypes=investigationRuleVO.getProviderTypes();
     if(strHospTypes!=null){
    	 String[]arrHospTypes=strHospTypes.split("[|]");
    	 for(String hospType:arrHospTypes){
    		 rootHospTypes.addElement("type").setText(hospType);
    	 }
     }
     SQLXML hospTypesXML = conn.createSQLXML();
     hospTypesXML.setString( hospTypeDoc.asXML());
     hmXmlTypes.put("HospTypes",hospTypesXML );
     // new change end
     
     SQLXML commRuleFieldsXML = conn.createSQLXML();
     commRuleFieldsXML.setString( staticDoc.asXML());
     hmXmlTypes.put("StaticXml",commRuleFieldsXML );
     
     //creating ProviderFaclityTypes type xml
     Document proTypeDoc = DocumentHelper.createDocument();
     Element rootProFaclityTypes = proTypeDoc.addElement("Provider-Faclity-Types");
     String strProTypes=investigationRuleVO.getProviderTypesID();
     if(strProTypes!=null){
    	 String[]arrProFaclityTypes=strProTypes.split("[|]");
    	 for(String proFaclityType:arrProFaclityTypes){
    		 rootProFaclityTypes.addElement("type").setText(proFaclityType);
    	 }
     }
     SQLXML providerTypesXML = conn.createSQLXML();
     providerTypesXML.setString( proTypeDoc.asXML());
     hmXmlTypes.put("ProviderFaclityTypes",providerTypesXML );
 
	   //creating encounter types xml
     Document encountersDoc = DocumentHelper.createDocument();
     Element rootEncounters = encountersDoc.addElement("Encounters");
     String strEncounter=investigationRuleVO.getEncounterTypes();
     if(strEncounter!=null){
    	 String[]arrEncounters=strEncounter.split("[|]");
    	 for(String encounetr:arrEncounters){
    		 rootEncounters.addElement("type").setText(encounetr);
    	 }
     }
     SQLXML encountersXML = conn.createSQLXML();
     encountersXML.setString( encountersDoc.asXML());
     hmXmlTypes.put("Encounters",encountersXML );
     
     
     
     //creating country xml
     Document countryDoc = DocumentHelper.createDocument();
     Element rootContries = countryDoc.addElement("Countries");
     String strContries=investigationRuleVO.getCountryIDs();
     if(strContries!=null){
    	 String[]arrContries=strContries.split("[|]");
    	 for(String country:arrContries){
    		 rootContries.addElement("gen-id").setText(country);
    	 }
     }
     SQLXML countriesXML = conn.createSQLXML();
     countriesXML.setString( countryDoc.asXML());
     hmXmlTypes.put("Countries",countriesXML );
	
     //creating emirate xml
     Document emirateDoc = DocumentHelper.createDocument();
     Element rootEmirate = emirateDoc.addElement("Emirates");
     String strEmirates=investigationRuleVO.getEmiratesIDs();
     if(strEmirates!=null){
    	 String[]arrEmirates=strEmirates.split("[|]");
    	 for(String emirate:arrEmirates){
    		 rootEmirate.addElement("gen-id").setText(emirate);
    	 }
     }
     SQLXML emirateXML = conn.createSQLXML();
     emirateXML.setString( emirateDoc.asXML());
     hmXmlTypes.put("Emirates",emirateXML );
     
	  
     SQLXML copayDetailsXML = conn.createSQLXML();
     copayDetailsXML.setString( "<Empty/>");
     hmXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
      
return hmXmlTypes;
}
public static void getEyeCareConfdtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
	
	
investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	

SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");

 if(sdXmlType!=null){
    SAXReader saxReader = new SAXReader();
   Document  staticdoc = saxReader.read(sdXmlType.getCharacterStream());
  
   Node fieldNode=staticdoc.selectSingleNode("/Eye-Care/Fields");
   if(fieldNode!=null){
   investRuleVO.setClaimType(fieldNode.valueOf("@claim-type"));  
   investRuleVO.setNetworkYN(fieldNode.valueOf("@network-type"));
   investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
   investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
   investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));  
   investRuleVO.setProviderTypes(fieldNode.valueOf("@provider-type"));
   investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
   investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
   investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
   investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
   investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
   investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
   investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
   investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
   investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
 
   investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
   }//if(fieldNode!=null){
   }//if(sdXmlType!=null){
	
 SQLXML encXmlType =rs.getSQLXML("ENCOUNTER_TYPE");
	if(encXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  encdoc = saxReader.read(encXmlType.getCharacterStream());
      List<Node> listType=encdoc.selectNodes("/Encounters/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEncounters=stringBuilder.toString();
    	  if(strEncounters.length()>0)investRuleVO.setEncounterTypes(strEncounters.substring(0, strEncounters.length()-1));
      }
	}//if(encXmlType!=null){
	
	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
	if(prfXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(prfXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Provider-Faclity-Types/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strPRF=stringBuilder.toString();
    	  if(strPRF.length()>0)investRuleVO.setProviderTypesID(strPRF.substring(0, strPRF.length()-1));
      }
	}//if(prfXmlType!=null){
	
	SQLXML countryXmlType =rs.getSQLXML("COUNTRIES_ID");
	if(countryXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(countryXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Countries/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strCon=stringBuilder.toString();
    	  if(strCon.length()>0)investRuleVO.setCountryIDs(strCon.substring(0, strCon.length()-1));
      }
	}//if(countryXmlType!=null){
	

	SQLXML emrtXmlType =rs.getSQLXML("EMIRATES_ID");
	if(emrtXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(emrtXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Emirates/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEmrt=stringBuilder.toString();
    	  if(strEmrt.length()>0)investRuleVO.setEmiratesIDs(strEmrt.substring(0, strEmrt.length()-1));
      }
	}//if(countryXmlType!=null){

	
}


public static HashMap<String,SQLXML> getGyncolgyFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
	HashMap<String,SQLXML> hmXmlTypes=new HashMap<>();
	
	Document staticDoc = DocumentHelper.createDocument();
     Element rootFields = staticDoc.addElement("Gynaecology");
     Element fields = rootFields.addElement("Fields");
     fields.addAttribute("claim-type", investigationRuleVO.getClaimType());
     fields.addAttribute("network-type", investigationRuleVO.getNetworkYN());
     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
     fields.addAttribute("geo-location", investigationRuleVO.getGeoLocationID());
     fields.addAttribute("provider-type", investigationRuleVO.getProviderTypes());
     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());
     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
   
     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
     
     SQLXML commRuleFieldsXML = conn.createSQLXML();
     commRuleFieldsXML.setString( staticDoc.asXML());
     hmXmlTypes.put("StaticXml",commRuleFieldsXML );
     
     //creating ProviderFaclityTypes type xml
     Document proTypeDoc = DocumentHelper.createDocument();
     Element rootProFaclityTypes = proTypeDoc.addElement("Provider-Faclity-Types");
     String strProTypes=investigationRuleVO.getProviderTypesID();
     if(strProTypes!=null){
    	 String[]arrProFaclityTypes=strProTypes.split("[|]");
    	 for(String proFaclityType:arrProFaclityTypes){
    		 rootProFaclityTypes.addElement("type").setText(proFaclityType);
    	 }
     }
     SQLXML providerTypesXML = conn.createSQLXML();
     providerTypesXML.setString( proTypeDoc.asXML());
     hmXmlTypes.put("ProviderFaclityTypes",providerTypesXML );
 
	   //creating encounter types xml
     Document encountersDoc = DocumentHelper.createDocument();
     Element rootEncounters = encountersDoc.addElement("Encounters");
     String strEncounter=investigationRuleVO.getEncounterTypes();
     if(strEncounter!=null){
    	 String[]arrEncounters=strEncounter.split("[|]");
    	 for(String encounetr:arrEncounters){
    		 rootEncounters.addElement("type").setText(encounetr);
    	 }
     }
     SQLXML encountersXML = conn.createSQLXML();
     encountersXML.setString( encountersDoc.asXML());
     hmXmlTypes.put("Encounters",encountersXML );
     
     
     
     //creating country xml
     Document countryDoc = DocumentHelper.createDocument();
     Element rootContries = countryDoc.addElement("Countries");
     String strContries=investigationRuleVO.getCountryIDs();
     if(strContries!=null){
    	 String[]arrContries=strContries.split("[|]");
    	 for(String country:arrContries){
    		 rootContries.addElement("gen-id").setText(country);
    	 }
     }
     SQLXML countriesXML = conn.createSQLXML();
     countriesXML.setString( countryDoc.asXML());
     hmXmlTypes.put("Countries",countriesXML );
	
     //creating emirate xml
     Document emirateDoc = DocumentHelper.createDocument();
     Element rootEmirate = emirateDoc.addElement("Emirates");
     String strEmirates=investigationRuleVO.getEmiratesIDs();
     if(strEmirates!=null){
    	 String[]arrEmirates=strEmirates.split("[|]");
    	 for(String emirate:arrEmirates){
    		 rootEmirate.addElement("gen-id").setText(emirate);
    	 }
     }
     SQLXML emirateXML = conn.createSQLXML();
     emirateXML.setString( emirateDoc.asXML());
     hmXmlTypes.put("Emirates",emirateXML );
     
	   //creating clinician copay details  xml
     Document clinCopayDoc = DocumentHelper.createDocument();
     Element rootClin = clinCopayDoc.addElement("Clinicians");
     String strClinCopayDetails=investigationRuleVO.getClinicianCopayDetails();
     if(strClinCopayDetails!=null&&strClinCopayDetails.length()>1){
    	 
    	 String[]arrrClinCopayDetails=strClinCopayDetails.split("[|]");
    if(arrrClinCopayDetails!=null&&arrrClinCopayDetails.length>0){
    	 for(String copayDetails:arrrClinCopayDetails){
    		 Element rootCoapyDtl = rootClin.addElement("Copay-Details");
    		 
    		 String arrGenIds[]=copayDetails.split("[@]");
    		 if(arrGenIds!=null&&arrGenIds.length>0){
    		 String arrCpDetails[]=arrGenIds[1].split("[_]");
    		 if(arrCpDetails!=null&&arrCpDetails.length>0){
    		 rootCoapyDtl.addAttribute("type", arrGenIds[0]);
    		 rootCoapyDtl.addAttribute("copay", arrCpDetails[0]);
    		 rootCoapyDtl.addAttribute("deduct", arrCpDetails[1]);
    		 rootCoapyDtl.addAttribute("flag", arrCpDetails[2]);
    		 }
    		 }
    	 }
    }// if(arrrClinCopayDetails!null&&arrrClinCopayDetails.length>0){
     }
     SQLXML copayDetailsXML = conn.createSQLXML();
     copayDetailsXML.setString( clinCopayDoc.asXML());
     hmXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
      
return hmXmlTypes;
}
public static void getGyncolgyConfdtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
	
	
investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	

SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");

 if(sdXmlType!=null){
    SAXReader saxReader = new SAXReader();
   Document  staticdoc = saxReader.read(sdXmlType.getCharacterStream());
  
   Node fieldNode=staticdoc.selectSingleNode("/Gynaecology/Fields");
   if(fieldNode!=null){
   investRuleVO.setClaimType(fieldNode.valueOf("@claim-type"));  
   investRuleVO.setNetworkYN(fieldNode.valueOf("@network-type"));
   investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
   investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
   investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));  
   investRuleVO.setProviderTypes(fieldNode.valueOf("@provider-type"));
   investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
   investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
   investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
   investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
   investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
   investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
   investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
   investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
   investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
 
   investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
   }//if(fieldNode!=null){
   }//if(sdXmlType!=null){
	
 SQLXML encXmlType =rs.getSQLXML("ENCOUNTER_TYPE");
	if(encXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  encdoc = saxReader.read(encXmlType.getCharacterStream());
      List<Node> listType=encdoc.selectNodes("/Encounters/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEncounters=stringBuilder.toString();
    	  if(strEncounters.length()>0)investRuleVO.setEncounterTypes(strEncounters.substring(0, strEncounters.length()-1));
      }
	}//if(encXmlType!=null){
	
	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
	if(prfXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(prfXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Provider-Faclity-Types/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strPRF=stringBuilder.toString();
    	  if(strPRF.length()>0)investRuleVO.setProviderTypesID(strPRF.substring(0, strPRF.length()-1));
      }
	}//if(prfXmlType!=null){
	
	SQLXML countryXmlType =rs.getSQLXML("COUNTRIES_ID");
	if(countryXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(countryXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Countries/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strCon=stringBuilder.toString();
    	  if(strCon.length()>0)investRuleVO.setCountryIDs(strCon.substring(0, strCon.length()-1));
      }
	}//if(countryXmlType!=null){
	

	SQLXML emrtXmlType =rs.getSQLXML("EMIRATES_ID");
	if(emrtXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(emrtXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Emirates/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEmrt=stringBuilder.toString();
    	  if(strEmrt.length()>0)investRuleVO.setEmiratesIDs(strEmrt.substring(0, strEmrt.length()-1));
      }
	}//if(countryXmlType!=null){

	
}



public static HashMap<String,SQLXML> getMinrSrgryFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
	HashMap<String,SQLXML> hmXmlTypes=new HashMap<>();
	
	Document staticDoc = DocumentHelper.createDocument();
     Element rootFields = staticDoc.addElement("Minor-Surgeries");
     Element fields = rootFields.addElement("Fields");
     fields.addAttribute("claim-type", investigationRuleVO.getClaimType());
     fields.addAttribute("network-type", investigationRuleVO.getNetworkYN());
     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
     fields.addAttribute("geo-location", investigationRuleVO.getGeoLocationID());
     fields.addAttribute("provider-type", investigationRuleVO.getProviderTypes());
     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());
     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
   
     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
     
     SQLXML commRuleFieldsXML = conn.createSQLXML();
     commRuleFieldsXML.setString( staticDoc.asXML());
     hmXmlTypes.put("StaticXml",commRuleFieldsXML );
     
     //creating ProviderFaclityTypes type xml
     Document proTypeDoc = DocumentHelper.createDocument();
     Element rootProFaclityTypes = proTypeDoc.addElement("Provider-Faclity-Types");
     String strProTypes=investigationRuleVO.getProviderTypesID();
     if(strProTypes!=null){
    	 String[]arrProFaclityTypes=strProTypes.split("[|]");
    	 for(String proFaclityType:arrProFaclityTypes){
    		 rootProFaclityTypes.addElement("type").setText(proFaclityType);
    	 }
     }
     SQLXML providerTypesXML = conn.createSQLXML();
     providerTypesXML.setString( proTypeDoc.asXML());
     hmXmlTypes.put("ProviderFaclityTypes",providerTypesXML );
 
	   //creating encounter types xml
     Document encountersDoc = DocumentHelper.createDocument();
     Element rootEncounters = encountersDoc.addElement("Encounters");
     String strEncounter=investigationRuleVO.getEncounterTypes();
     if(strEncounter!=null){
    	 String[]arrEncounters=strEncounter.split("[|]");
    	 for(String encounetr:arrEncounters){
    		 rootEncounters.addElement("type").setText(encounetr);
    	 }
     }
     SQLXML encountersXML = conn.createSQLXML();
     encountersXML.setString( encountersDoc.asXML());
     hmXmlTypes.put("Encounters",encountersXML );
     
     
     
     //creating country xml
     Document countryDoc = DocumentHelper.createDocument();
     Element rootContries = countryDoc.addElement("Countries");
     String strContries=investigationRuleVO.getCountryIDs();
     if(strContries!=null){
    	 String[]arrContries=strContries.split("[|]");
    	 for(String country:arrContries){
    		 rootContries.addElement("gen-id").setText(country);
    	 }
     }
     SQLXML countriesXML = conn.createSQLXML();
     countriesXML.setString( countryDoc.asXML());
     hmXmlTypes.put("Countries",countriesXML );
	
     //creating emirate xml
     Document emirateDoc = DocumentHelper.createDocument();
     Element rootEmirate = emirateDoc.addElement("Emirates");
     String strEmirates=investigationRuleVO.getEmiratesIDs();
     if(strEmirates!=null){
    	 String[]arrEmirates=strEmirates.split("[|]");
    	 for(String emirate:arrEmirates){
    		 rootEmirate.addElement("gen-id").setText(emirate);
    	 }
     }
     SQLXML emirateXML = conn.createSQLXML();
     emirateXML.setString( emirateDoc.asXML());
     hmXmlTypes.put("Emirates",emirateXML );
     
	   //creating clinician copay details  xml
     Document clinCopayDoc = DocumentHelper.createDocument();
     Element rootClin = clinCopayDoc.addElement("Clinicians");
     String strClinCopayDetails=investigationRuleVO.getClinicianCopayDetails();
     if(strClinCopayDetails!=null&&strClinCopayDetails.length()>1){
    	 
    	 String[]arrrClinCopayDetails=strClinCopayDetails.split("[|]");
    if(arrrClinCopayDetails!=null&&arrrClinCopayDetails.length>0){
    	 for(String copayDetails:arrrClinCopayDetails){
    		 Element rootCoapyDtl = rootClin.addElement("Copay-Details");
    		 
    		 String arrGenIds[]=copayDetails.split("[@]");
    		 if(arrGenIds!=null&&arrGenIds.length>0){
    		 String arrCpDetails[]=arrGenIds[1].split("[_]");
    		 if(arrCpDetails!=null&&arrCpDetails.length>0){
    		 rootCoapyDtl.addAttribute("type", arrGenIds[0]);
    		 rootCoapyDtl.addAttribute("copay", arrCpDetails[0]);
    		 rootCoapyDtl.addAttribute("deduct", arrCpDetails[1]);
    		 rootCoapyDtl.addAttribute("flag", arrCpDetails[2]);
    		 }
    		 }
    	 }
    }// if(arrrClinCopayDetails!null&&arrrClinCopayDetails.length>0){
     }
     SQLXML copayDetailsXML = conn.createSQLXML();
     copayDetailsXML.setString( clinCopayDoc.asXML());
     hmXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
      
return hmXmlTypes;
}
public static void getMinrSrgryConfdtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
	
	
investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	

SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");

 if(sdXmlType!=null){
    SAXReader saxReader = new SAXReader();
   Document  staticdoc = saxReader.read(sdXmlType.getCharacterStream());
  
   Node fieldNode=staticdoc.selectSingleNode("/Minor-Surgeries/Fields");
   if(fieldNode!=null){
   investRuleVO.setClaimType(fieldNode.valueOf("@claim-type"));  
   investRuleVO.setNetworkYN(fieldNode.valueOf("@network-type"));
   investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
   investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
   investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));  
   investRuleVO.setProviderTypes(fieldNode.valueOf("@provider-type"));
   investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
   investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
   investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
   investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
   investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
   investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
   investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
   investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
   investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
 
   investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
   }//if(fieldNode!=null){
   }//if(sdXmlType!=null){
	
 SQLXML encXmlType =rs.getSQLXML("ENCOUNTER_TYPE");
	if(encXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  encdoc = saxReader.read(encXmlType.getCharacterStream());
      List<Node> listType=encdoc.selectNodes("/Encounters/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEncounters=stringBuilder.toString();
    	  if(strEncounters.length()>0)investRuleVO.setEncounterTypes(strEncounters.substring(0, strEncounters.length()-1));
      }
	}//if(encXmlType!=null){
	
	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
	if(prfXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(prfXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Provider-Faclity-Types/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strPRF=stringBuilder.toString();
    	  if(strPRF.length()>0)investRuleVO.setProviderTypesID(strPRF.substring(0, strPRF.length()-1));
      }
	}//if(prfXmlType!=null){
	
	SQLXML countryXmlType =rs.getSQLXML("COUNTRIES_ID");
	if(countryXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(countryXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Countries/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strCon=stringBuilder.toString();
    	  if(strCon.length()>0)investRuleVO.setCountryIDs(strCon.substring(0, strCon.length()-1));
      }
	}//if(countryXmlType!=null){
	

	SQLXML emrtXmlType =rs.getSQLXML("EMIRATES_ID");
	if(emrtXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(emrtXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Emirates/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEmrt=stringBuilder.toString();
    	  if(strEmrt.length()>0)investRuleVO.setEmiratesIDs(strEmrt.substring(0, strEmrt.length()-1));
      }
	}//if(countryXmlType!=null){

	
}



public static HashMap<String,SQLXML> getNaslCrctionFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
	HashMap<String,SQLXML> hmXmlTypes=new HashMap<>();
	
	Document staticDoc = DocumentHelper.createDocument();
     Element rootFields = staticDoc.addElement("Nasal-Correction-Septoplasty");
     Element fields = rootFields.addElement("Fields");
     fields.addAttribute("claim-type", investigationRuleVO.getClaimType());
     fields.addAttribute("network-type", investigationRuleVO.getNetworkYN());
     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
     fields.addAttribute("geo-location", investigationRuleVO.getGeoLocationID());
     fields.addAttribute("provider-type", investigationRuleVO.getProviderTypes());
     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());
     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
   
     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
     
     SQLXML commRuleFieldsXML = conn.createSQLXML();
     commRuleFieldsXML.setString( staticDoc.asXML());
     hmXmlTypes.put("StaticXml",commRuleFieldsXML );
     
     //creating ProviderFaclityTypes type xml
     Document proTypeDoc = DocumentHelper.createDocument();
     Element rootProFaclityTypes = proTypeDoc.addElement("Provider-Faclity-Types");
     String strProTypes=investigationRuleVO.getProviderTypesID();
     if(strProTypes!=null){
    	 String[]arrProFaclityTypes=strProTypes.split("[|]");
    	 for(String proFaclityType:arrProFaclityTypes){
    		 rootProFaclityTypes.addElement("type").setText(proFaclityType);
    	 }
     }
     SQLXML providerTypesXML = conn.createSQLXML();
     providerTypesXML.setString( proTypeDoc.asXML());
     hmXmlTypes.put("ProviderFaclityTypes",providerTypesXML );
 
	   //creating encounter types xml
     Document encountersDoc = DocumentHelper.createDocument();
     Element rootEncounters = encountersDoc.addElement("Encounters");
     String strEncounter=investigationRuleVO.getEncounterTypes();
     if(strEncounter!=null){
    	 String[]arrEncounters=strEncounter.split("[|]");
    	 for(String encounetr:arrEncounters){
    		 rootEncounters.addElement("type").setText(encounetr);
    	 }
     }
     SQLXML encountersXML = conn.createSQLXML();
     encountersXML.setString( encountersDoc.asXML());
     hmXmlTypes.put("Encounters",encountersXML );
     
     
     
     //creating country xml
     Document countryDoc = DocumentHelper.createDocument();
     Element rootContries = countryDoc.addElement("Countries");
     String strContries=investigationRuleVO.getCountryIDs();
     if(strContries!=null){
    	 String[]arrContries=strContries.split("[|]");
    	 for(String country:arrContries){
    		 rootContries.addElement("gen-id").setText(country);
    	 }
     }
     SQLXML countriesXML = conn.createSQLXML();
     countriesXML.setString( countryDoc.asXML());
     hmXmlTypes.put("Countries",countriesXML );
	
     //creating emirate xml
     Document emirateDoc = DocumentHelper.createDocument();
     Element rootEmirate = emirateDoc.addElement("Emirates");
     String strEmirates=investigationRuleVO.getEmiratesIDs();
     if(strEmirates!=null){
    	 String[]arrEmirates=strEmirates.split("[|]");
    	 for(String emirate:arrEmirates){
    		 rootEmirate.addElement("gen-id").setText(emirate);
    	 }
     }
     SQLXML emirateXML = conn.createSQLXML();
     emirateXML.setString( emirateDoc.asXML());
     hmXmlTypes.put("Emirates",emirateXML );
     
	   //creating clinician copay details  xml
     Document clinCopayDoc = DocumentHelper.createDocument();
     Element rootClin = clinCopayDoc.addElement("Clinicians");
     String strClinCopayDetails=investigationRuleVO.getClinicianCopayDetails();
     if(strClinCopayDetails!=null&&strClinCopayDetails.length()>1){
    	 
    	 String[]arrrClinCopayDetails=strClinCopayDetails.split("[|]");
    if(arrrClinCopayDetails!=null&&arrrClinCopayDetails.length>0){
    	 for(String copayDetails:arrrClinCopayDetails){
    		 Element rootCoapyDtl = rootClin.addElement("Copay-Details");
    		 
    		 String arrGenIds[]=copayDetails.split("[@]");
    		 if(arrGenIds!=null&&arrGenIds.length>0){
    		 String arrCpDetails[]=arrGenIds[1].split("[_]");
    		 if(arrCpDetails!=null&&arrCpDetails.length>0){
    		 rootCoapyDtl.addAttribute("type", arrGenIds[0]);
    		 rootCoapyDtl.addAttribute("copay", arrCpDetails[0]);
    		 rootCoapyDtl.addAttribute("deduct", arrCpDetails[1]);
    		 rootCoapyDtl.addAttribute("flag", arrCpDetails[2]);
    		 }
    		 }
    	 }
    }// if(arrrClinCopayDetails!null&&arrrClinCopayDetails.length>0){
     }
     SQLXML copayDetailsXML = conn.createSQLXML();
     copayDetailsXML.setString( clinCopayDoc.asXML());
     hmXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
      
return hmXmlTypes;
}
public static void getNaslCrctionConfdtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
	
	
investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	

SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");

 if(sdXmlType!=null){
    SAXReader saxReader = new SAXReader();
   Document  staticdoc = saxReader.read(sdXmlType.getCharacterStream());
  
   Node fieldNode=staticdoc.selectSingleNode("/Nasal-Correction-Septoplasty/Fields");
   if(fieldNode!=null){
   investRuleVO.setClaimType(fieldNode.valueOf("@claim-type"));  
   investRuleVO.setNetworkYN(fieldNode.valueOf("@network-type"));
   investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
   investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
   investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));  
   investRuleVO.setProviderTypes(fieldNode.valueOf("@provider-type"));
   investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
   investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
   investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
   investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
   investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
   investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
   investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
   investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
   investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
 
   investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
   }//if(fieldNode!=null){
   }//if(sdXmlType!=null){
	
 SQLXML encXmlType =rs.getSQLXML("ENCOUNTER_TYPE");
	if(encXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  encdoc = saxReader.read(encXmlType.getCharacterStream());
      List<Node> listType=encdoc.selectNodes("/Encounters/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEncounters=stringBuilder.toString();
    	  if(strEncounters.length()>0)investRuleVO.setEncounterTypes(strEncounters.substring(0, strEncounters.length()-1));
      }
	}//if(encXmlType!=null){
	
	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
	if(prfXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(prfXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Provider-Faclity-Types/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strPRF=stringBuilder.toString();
    	  if(strPRF.length()>0)investRuleVO.setProviderTypesID(strPRF.substring(0, strPRF.length()-1));
      }
	}//if(prfXmlType!=null){
	
	SQLXML countryXmlType =rs.getSQLXML("COUNTRIES_ID");
	if(countryXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(countryXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Countries/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strCon=stringBuilder.toString();
    	  if(strCon.length()>0)investRuleVO.setCountryIDs(strCon.substring(0, strCon.length()-1));
      }
	}//if(countryXmlType!=null){
	

	SQLXML emrtXmlType =rs.getSQLXML("EMIRATES_ID");
	if(emrtXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(emrtXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Emirates/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEmrt=stringBuilder.toString();
    	  if(strEmrt.length()>0)investRuleVO.setEmiratesIDs(strEmrt.substring(0, strEmrt.length()-1));
      }
	}//if(countryXmlType!=null){

	
}



public static HashMap<String,SQLXML> getOnclgyFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
	HashMap<String,SQLXML> hmXmlTypes=new HashMap<>();
	
	Document staticDoc = DocumentHelper.createDocument();
     Element rootFields = staticDoc.addElement("Oncology");
     Element fields = rootFields.addElement("Fields");
     fields.addAttribute("claim-type", investigationRuleVO.getClaimType());
     fields.addAttribute("network-type", investigationRuleVO.getNetworkYN());
     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
     fields.addAttribute("geo-location", investigationRuleVO.getGeoLocationID());
     fields.addAttribute("provider-type", investigationRuleVO.getProviderTypes());
     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());
     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
   
     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
     
     SQLXML commRuleFieldsXML = conn.createSQLXML();
     commRuleFieldsXML.setString( staticDoc.asXML());
     hmXmlTypes.put("StaticXml",commRuleFieldsXML );
     
     //creating ProviderFaclityTypes type xml
     Document proTypeDoc = DocumentHelper.createDocument();
     Element rootProFaclityTypes = proTypeDoc.addElement("Provider-Faclity-Types");
     String strProTypes=investigationRuleVO.getProviderTypesID();
     if(strProTypes!=null){
    	 String[]arrProFaclityTypes=strProTypes.split("[|]");
    	 for(String proFaclityType:arrProFaclityTypes){
    		 rootProFaclityTypes.addElement("type").setText(proFaclityType);
    	 }
     }
     SQLXML providerTypesXML = conn.createSQLXML();
     providerTypesXML.setString( proTypeDoc.asXML());
     hmXmlTypes.put("ProviderFaclityTypes",providerTypesXML );
 
	   //creating encounter types xml
     Document encountersDoc = DocumentHelper.createDocument();
     Element rootEncounters = encountersDoc.addElement("Encounters");
     String strEncounter=investigationRuleVO.getEncounterTypes();
     if(strEncounter!=null){
    	 String[]arrEncounters=strEncounter.split("[|]");
    	 for(String encounetr:arrEncounters){
    		 rootEncounters.addElement("type").setText(encounetr);
    	 }
     }
     SQLXML encountersXML = conn.createSQLXML();
     encountersXML.setString( encountersDoc.asXML());
     hmXmlTypes.put("Encounters",encountersXML );
     
     
     
     //creating country xml
     Document countryDoc = DocumentHelper.createDocument();
     Element rootContries = countryDoc.addElement("Countries");
     String strContries=investigationRuleVO.getCountryIDs();
     if(strContries!=null){
    	 String[]arrContries=strContries.split("[|]");
    	 for(String country:arrContries){
    		 rootContries.addElement("gen-id").setText(country);
    	 }
     }
     SQLXML countriesXML = conn.createSQLXML();
     countriesXML.setString( countryDoc.asXML());
     hmXmlTypes.put("Countries",countriesXML );
	
     //creating emirate xml
     Document emirateDoc = DocumentHelper.createDocument();
     Element rootEmirate = emirateDoc.addElement("Emirates");
     String strEmirates=investigationRuleVO.getEmiratesIDs();
     if(strEmirates!=null){
    	 String[]arrEmirates=strEmirates.split("[|]");
    	 for(String emirate:arrEmirates){
    		 rootEmirate.addElement("gen-id").setText(emirate);
    	 }
     }
     SQLXML emirateXML = conn.createSQLXML();
     emirateXML.setString( emirateDoc.asXML());
     hmXmlTypes.put("Emirates",emirateXML );
     
	   //creating clinician copay details  xml
     Document clinCopayDoc = DocumentHelper.createDocument();
     Element rootClin = clinCopayDoc.addElement("Clinicians");
     String strClinCopayDetails=investigationRuleVO.getClinicianCopayDetails();
     if(strClinCopayDetails!=null&&strClinCopayDetails.length()>1){
    	 
    	 String[]arrrClinCopayDetails=strClinCopayDetails.split("[|]");
    if(arrrClinCopayDetails!=null&&arrrClinCopayDetails.length>0){
    	 for(String copayDetails:arrrClinCopayDetails){
    		 Element rootCoapyDtl = rootClin.addElement("Copay-Details");
    		 
    		 String arrGenIds[]=copayDetails.split("[@]");
    		 if(arrGenIds!=null&&arrGenIds.length>0){
    		 String arrCpDetails[]=arrGenIds[1].split("[_]");
    		 if(arrCpDetails!=null&&arrCpDetails.length>0){
    		 rootCoapyDtl.addAttribute("type", arrGenIds[0]);
    		 rootCoapyDtl.addAttribute("copay", arrCpDetails[0]);
    		 rootCoapyDtl.addAttribute("deduct", arrCpDetails[1]);
    		 rootCoapyDtl.addAttribute("flag", arrCpDetails[2]);
    		 }
    		 }
    	 }
    }// if(arrrClinCopayDetails!null&&arrrClinCopayDetails.length>0){
     }
     SQLXML copayDetailsXML = conn.createSQLXML();
     copayDetailsXML.setString( clinCopayDoc.asXML());
     hmXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
      
return hmXmlTypes;
}
public static void getOnclgyConfdtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
	
	
investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	

SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");

 if(sdXmlType!=null){
    SAXReader saxReader = new SAXReader();
   Document  staticdoc = saxReader.read(sdXmlType.getCharacterStream());
  
   Node fieldNode=staticdoc.selectSingleNode("/Oncology/Fields");
   if(fieldNode!=null){
   investRuleVO.setClaimType(fieldNode.valueOf("@claim-type"));  
   investRuleVO.setNetworkYN(fieldNode.valueOf("@network-type"));
   investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
   investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
   investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));  
   investRuleVO.setProviderTypes(fieldNode.valueOf("@provider-type"));
   investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
   investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
   investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
   investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
   investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
   investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
   investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
   investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
   investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
 
   investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
   }//if(fieldNode!=null){
   }//if(sdXmlType!=null){
	
 SQLXML encXmlType =rs.getSQLXML("ENCOUNTER_TYPE");
	if(encXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  encdoc = saxReader.read(encXmlType.getCharacterStream());
      List<Node> listType=encdoc.selectNodes("/Encounters/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEncounters=stringBuilder.toString();
    	  if(strEncounters.length()>0)investRuleVO.setEncounterTypes(strEncounters.substring(0, strEncounters.length()-1));
      }
	}//if(encXmlType!=null){
	
	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
	if(prfXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(prfXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Provider-Faclity-Types/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strPRF=stringBuilder.toString();
    	  if(strPRF.length()>0)investRuleVO.setProviderTypesID(strPRF.substring(0, strPRF.length()-1));
      }
	}//if(prfXmlType!=null){
	
	SQLXML countryXmlType =rs.getSQLXML("COUNTRIES_ID");
	if(countryXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(countryXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Countries/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strCon=stringBuilder.toString();
    	  if(strCon.length()>0)investRuleVO.setCountryIDs(strCon.substring(0, strCon.length()-1));
      }
	}//if(countryXmlType!=null){
	

	SQLXML emrtXmlType =rs.getSQLXML("EMIRATES_ID");
	if(emrtXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(emrtXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Emirates/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEmrt=stringBuilder.toString();
    	  if(strEmrt.length()>0)investRuleVO.setEmiratesIDs(strEmrt.substring(0, strEmrt.length()-1));
      }
	}//if(countryXmlType!=null){

	
}



public static HashMap<String,SQLXML> getOgnTrnspltRcptFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
	HashMap<String,SQLXML> hmXmlTypes=new HashMap<>();
	
	Document staticDoc = DocumentHelper.createDocument();
     Element rootFields = staticDoc.addElement("Or-Tp-Rcpt");
     Element fields = rootFields.addElement("Fields");
     fields.addAttribute("claim-type", investigationRuleVO.getClaimType());
     fields.addAttribute("network-type", investigationRuleVO.getNetworkYN());
     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
     fields.addAttribute("geo-location", investigationRuleVO.getGeoLocationID());
     fields.addAttribute("provider-type", investigationRuleVO.getProviderTypes());
     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());
     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
   
     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
     
     SQLXML commRuleFieldsXML = conn.createSQLXML();
     commRuleFieldsXML.setString( staticDoc.asXML());
     hmXmlTypes.put("StaticXml",commRuleFieldsXML );
     
     //creating ProviderFaclityTypes type xml
     Document proTypeDoc = DocumentHelper.createDocument();
     Element rootProFaclityTypes = proTypeDoc.addElement("Provider-Faclity-Types");
     String strProTypes=investigationRuleVO.getProviderTypesID();
     if(strProTypes!=null){
    	 String[]arrProFaclityTypes=strProTypes.split("[|]");
    	 for(String proFaclityType:arrProFaclityTypes){
    		 rootProFaclityTypes.addElement("type").setText(proFaclityType);
    	 }
     }
     SQLXML providerTypesXML = conn.createSQLXML();
     providerTypesXML.setString( proTypeDoc.asXML());
     hmXmlTypes.put("ProviderFaclityTypes",providerTypesXML );
 
	   //creating encounter types xml
     Document encountersDoc = DocumentHelper.createDocument();
     Element rootEncounters = encountersDoc.addElement("Encounters");
     String strEncounter=investigationRuleVO.getEncounterTypes();
     if(strEncounter!=null){
    	 String[]arrEncounters=strEncounter.split("[|]");
    	 for(String encounetr:arrEncounters){
    		 rootEncounters.addElement("type").setText(encounetr);
    	 }
     }
     SQLXML encountersXML = conn.createSQLXML();
     encountersXML.setString( encountersDoc.asXML());
     hmXmlTypes.put("Encounters",encountersXML );
     
     
     
     //creating country xml
     Document countryDoc = DocumentHelper.createDocument();
     Element rootContries = countryDoc.addElement("Countries");
     String strContries=investigationRuleVO.getCountryIDs();
     if(strContries!=null){
    	 String[]arrContries=strContries.split("[|]");
    	 for(String country:arrContries){
    		 rootContries.addElement("gen-id").setText(country);
    	 }
     }
     SQLXML countriesXML = conn.createSQLXML();
     countriesXML.setString( countryDoc.asXML());
     hmXmlTypes.put("Countries",countriesXML );
	
     //creating emirate xml
     Document emirateDoc = DocumentHelper.createDocument();
     Element rootEmirate = emirateDoc.addElement("Emirates");
     String strEmirates=investigationRuleVO.getEmiratesIDs();
     if(strEmirates!=null){
    	 String[]arrEmirates=strEmirates.split("[|]");
    	 for(String emirate:arrEmirates){
    		 rootEmirate.addElement("gen-id").setText(emirate);
    	 }
     }
     SQLXML emirateXML = conn.createSQLXML();
     emirateXML.setString( emirateDoc.asXML());
     hmXmlTypes.put("Emirates",emirateXML );
     
	   //creating clinician copay details  xml
     Document clinCopayDoc = DocumentHelper.createDocument();
     Element rootClin = clinCopayDoc.addElement("Clinicians");
     String strClinCopayDetails=investigationRuleVO.getClinicianCopayDetails();
     if(strClinCopayDetails!=null&&strClinCopayDetails.length()>1){
    	 
    	 String[]arrrClinCopayDetails=strClinCopayDetails.split("[|]");
    if(arrrClinCopayDetails!=null&&arrrClinCopayDetails.length>0){
    	 for(String copayDetails:arrrClinCopayDetails){
    		 Element rootCoapyDtl = rootClin.addElement("Copay-Details");
    		 
    		 String arrGenIds[]=copayDetails.split("[@]");
    		 if(arrGenIds!=null&&arrGenIds.length>0){
    		 String arrCpDetails[]=arrGenIds[1].split("[_]");
    		 if(arrCpDetails!=null&&arrCpDetails.length>0){
    		 rootCoapyDtl.addAttribute("type", arrGenIds[0]);
    		 rootCoapyDtl.addAttribute("copay", arrCpDetails[0]);
    		 rootCoapyDtl.addAttribute("deduct", arrCpDetails[1]);
    		 rootCoapyDtl.addAttribute("flag", arrCpDetails[2]);
    		 }
    		 }
    	 }
    }// if(arrrClinCopayDetails!null&&arrrClinCopayDetails.length>0){
     }
     SQLXML copayDetailsXML = conn.createSQLXML();
     copayDetailsXML.setString( clinCopayDoc.asXML());
     hmXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
      
return hmXmlTypes;
}
public static void getOgnTrnspltRcptConfdtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
	
	
investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	

SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");

 if(sdXmlType!=null){
    SAXReader saxReader = new SAXReader();
   Document  staticdoc = saxReader.read(sdXmlType.getCharacterStream());
  
   Node fieldNode=staticdoc.selectSingleNode("/Or-Tp-Rcpt/Fields");
   if(fieldNode!=null){
   investRuleVO.setClaimType(fieldNode.valueOf("@claim-type"));  
   investRuleVO.setNetworkYN(fieldNode.valueOf("@network-type"));
   investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
   investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
   investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));  
   investRuleVO.setProviderTypes(fieldNode.valueOf("@provider-type"));
   investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
   investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
   investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
   investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
   investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
   investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
   investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
   investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
   investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
 
   investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
   }//if(fieldNode!=null){
   }//if(sdXmlType!=null){
	
 SQLXML encXmlType =rs.getSQLXML("ENCOUNTER_TYPE");
	if(encXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  encdoc = saxReader.read(encXmlType.getCharacterStream());
      List<Node> listType=encdoc.selectNodes("/Encounters/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEncounters=stringBuilder.toString();
    	  if(strEncounters.length()>0)investRuleVO.setEncounterTypes(strEncounters.substring(0, strEncounters.length()-1));
      }
	}//if(encXmlType!=null){
	
	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
	if(prfXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(prfXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Provider-Faclity-Types/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strPRF=stringBuilder.toString();
    	  if(strPRF.length()>0)investRuleVO.setProviderTypesID(strPRF.substring(0, strPRF.length()-1));
      }
	}//if(prfXmlType!=null){
	
	SQLXML countryXmlType =rs.getSQLXML("COUNTRIES_ID");
	if(countryXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(countryXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Countries/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strCon=stringBuilder.toString();
    	  if(strCon.length()>0)investRuleVO.setCountryIDs(strCon.substring(0, strCon.length()-1));
      }
	}//if(countryXmlType!=null){
	

	SQLXML emrtXmlType =rs.getSQLXML("EMIRATES_ID");
	if(emrtXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(emrtXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Emirates/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEmrt=stringBuilder.toString();
    	  if(strEmrt.length()>0)investRuleVO.setEmiratesIDs(strEmrt.substring(0, strEmrt.length()-1));
      }
	}//if(countryXmlType!=null){

	
}



public static HashMap<String,SQLXML> getPsychiatricFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
	HashMap<String,SQLXML> hmXmlTypes=new HashMap<>();
	
	Document staticDoc = DocumentHelper.createDocument();
     Element rootFields = staticDoc.addElement("Psychiatric");
     Element fields = rootFields.addElement("Fields");
     fields.addAttribute("claim-type", investigationRuleVO.getClaimType());
     fields.addAttribute("network-type", investigationRuleVO.getNetworkYN());
     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
     fields.addAttribute("geo-location", investigationRuleVO.getGeoLocationID());
     fields.addAttribute("provider-type", investigationRuleVO.getProviderTypes());
     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());
     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
   
     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
     
     SQLXML commRuleFieldsXML = conn.createSQLXML();
     commRuleFieldsXML.setString( staticDoc.asXML());
     hmXmlTypes.put("StaticXml",commRuleFieldsXML );
     
     //creating ProviderFaclityTypes type xml
     Document proTypeDoc = DocumentHelper.createDocument();
     Element rootProFaclityTypes = proTypeDoc.addElement("Provider-Faclity-Types");
     String strProTypes=investigationRuleVO.getProviderTypesID();
     if(strProTypes!=null){
    	 String[]arrProFaclityTypes=strProTypes.split("[|]");
    	 for(String proFaclityType:arrProFaclityTypes){
    		 rootProFaclityTypes.addElement("type").setText(proFaclityType);
    	 }
     }
     SQLXML providerTypesXML = conn.createSQLXML();
     providerTypesXML.setString( proTypeDoc.asXML());
     hmXmlTypes.put("ProviderFaclityTypes",providerTypesXML );
 
	   //creating encounter types xml
     Document encountersDoc = DocumentHelper.createDocument();
     Element rootEncounters = encountersDoc.addElement("Encounters");
     String strEncounter=investigationRuleVO.getEncounterTypes();
     if(strEncounter!=null){
    	 String[]arrEncounters=strEncounter.split("[|]");
    	 for(String encounetr:arrEncounters){
    		 rootEncounters.addElement("type").setText(encounetr);
    	 }
     }
     SQLXML encountersXML = conn.createSQLXML();
     encountersXML.setString( encountersDoc.asXML());
     hmXmlTypes.put("Encounters",encountersXML );
     
     
     
     //creating country xml
     Document countryDoc = DocumentHelper.createDocument();
     Element rootContries = countryDoc.addElement("Countries");
     String strContries=investigationRuleVO.getCountryIDs();
     if(strContries!=null){
    	 String[]arrContries=strContries.split("[|]");
    	 for(String country:arrContries){
    		 rootContries.addElement("gen-id").setText(country);
    	 }
     }
     SQLXML countriesXML = conn.createSQLXML();
     countriesXML.setString( countryDoc.asXML());
     hmXmlTypes.put("Countries",countriesXML );
	
     //creating emirate xml
     Document emirateDoc = DocumentHelper.createDocument();
     Element rootEmirate = emirateDoc.addElement("Emirates");
     String strEmirates=investigationRuleVO.getEmiratesIDs();
     if(strEmirates!=null){
    	 String[]arrEmirates=strEmirates.split("[|]");
    	 for(String emirate:arrEmirates){
    		 rootEmirate.addElement("gen-id").setText(emirate);
    	 }
     }
     SQLXML emirateXML = conn.createSQLXML();
     emirateXML.setString( emirateDoc.asXML());
     hmXmlTypes.put("Emirates",emirateXML );
     
	   //creating clinician copay details  xml
     Document clinCopayDoc = DocumentHelper.createDocument();
     Element rootClin = clinCopayDoc.addElement("Clinicians");
     String strClinCopayDetails=investigationRuleVO.getClinicianCopayDetails();
     if(strClinCopayDetails!=null&&strClinCopayDetails.length()>1){
    	 
    	 String[]arrrClinCopayDetails=strClinCopayDetails.split("[|]");
    if(arrrClinCopayDetails!=null&&arrrClinCopayDetails.length>0){
    	 for(String copayDetails:arrrClinCopayDetails){
    		 Element rootCoapyDtl = rootClin.addElement("Copay-Details");
    		 
    		 String arrGenIds[]=copayDetails.split("[@]");
    		 if(arrGenIds!=null&&arrGenIds.length>0){
    		 String arrCpDetails[]=arrGenIds[1].split("[_]");
    		 if(arrCpDetails!=null&&arrCpDetails.length>0){
    		 rootCoapyDtl.addAttribute("type", arrGenIds[0]);
    		 rootCoapyDtl.addAttribute("copay", arrCpDetails[0]);
    		 rootCoapyDtl.addAttribute("deduct", arrCpDetails[1]);
    		 rootCoapyDtl.addAttribute("flag", arrCpDetails[2]);
    		 }
    		 }
    	 }
    }// if(arrrClinCopayDetails!null&&arrrClinCopayDetails.length>0){
     }
     SQLXML copayDetailsXML = conn.createSQLXML();
     copayDetailsXML.setString( clinCopayDoc.asXML());
     hmXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
      
return hmXmlTypes;
}
public static void getPsychiatricConfdtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
	
	
investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	

SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");

 if(sdXmlType!=null){
    SAXReader saxReader = new SAXReader();
   Document  staticdoc = saxReader.read(sdXmlType.getCharacterStream());
  
   Node fieldNode=staticdoc.selectSingleNode("/Psychiatric/Fields");
   if(fieldNode!=null){
   investRuleVO.setClaimType(fieldNode.valueOf("@claim-type"));  
   investRuleVO.setNetworkYN(fieldNode.valueOf("@network-type"));
   investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
   investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
   investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));  
   investRuleVO.setProviderTypes(fieldNode.valueOf("@provider-type"));
   investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
   investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
   investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
   investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
   investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
   investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
   investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
   investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
   investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
 
   investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
   }//if(fieldNode!=null){
   }//if(sdXmlType!=null){
	
 SQLXML encXmlType =rs.getSQLXML("ENCOUNTER_TYPE");
	if(encXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  encdoc = saxReader.read(encXmlType.getCharacterStream());
      List<Node> listType=encdoc.selectNodes("/Encounters/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEncounters=stringBuilder.toString();
    	  if(strEncounters.length()>0)investRuleVO.setEncounterTypes(strEncounters.substring(0, strEncounters.length()-1));
      }
	}//if(encXmlType!=null){
	
	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
	if(prfXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(prfXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Provider-Faclity-Types/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strPRF=stringBuilder.toString();
    	  if(strPRF.length()>0)investRuleVO.setProviderTypesID(strPRF.substring(0, strPRF.length()-1));
      }
	}//if(prfXmlType!=null){
	
	SQLXML countryXmlType =rs.getSQLXML("COUNTRIES_ID");
	if(countryXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(countryXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Countries/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strCon=stringBuilder.toString();
    	  if(strCon.length()>0)investRuleVO.setCountryIDs(strCon.substring(0, strCon.length()-1));
      }
	}//if(countryXmlType!=null){
	

	SQLXML emrtXmlType =rs.getSQLXML("EMIRATES_ID");
	if(emrtXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(emrtXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Emirates/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEmrt=stringBuilder.toString();
    	  if(strEmrt.length()>0)investRuleVO.setEmiratesIDs(strEmrt.substring(0, strEmrt.length()-1));
      }
	}//if(countryXmlType!=null){

	
}


public static HashMap<String,SQLXML> getRnlDlsFieldXml(Connection conn,InvestigationRuleVO investigationRuleVO)throws Exception {
	HashMap<String,SQLXML> hmXmlTypes=new HashMap<>();
	
	Document staticDoc = DocumentHelper.createDocument();
     Element rootFields = staticDoc.addElement("Renal-Dialysis");
     Element fields = rootFields.addElement("Fields");
     fields.addAttribute("claim-type", investigationRuleVO.getClaimType());
     fields.addAttribute("network-type", investigationRuleVO.getNetworkYN());
     fields.addAttribute("pre-appr-yn", investigationRuleVO.getPreApprvReqYN());
     fields.addAttribute("pre-appr-limit", investigationRuleVO.getPreApprvLimit());
     fields.addAttribute("geo-location", investigationRuleVO.getGeoLocationID());
     fields.addAttribute("provider-type", investigationRuleVO.getProviderTypes());
     fields.addAttribute("per-policy-limit", investigationRuleVO.getPerPolicyLimit());
     fields.addAttribute("per-claim-limit", investigationRuleVO.getPerClaimLimit());
     fields.addAttribute("ovr-copay", investigationRuleVO.getOvrCopay());
     fields.addAttribute("ovr-deductable", investigationRuleVO.getOvrDeductible());
     fields.addAttribute("ovr-minmax", investigationRuleVO.getOvrMinMaxFlag());
     fields.addAttribute("ovr-applay-on", investigationRuleVO.getOvrApplOn());
     fields.addAttribute("per-plcy-cd-limit", investigationRuleVO.getPerPolicyCpyDdctLimit());
     fields.addAttribute("per-clm-cd-limit", investigationRuleVO.getPerClaimCpyDdctLimit());
     fields.addAttribute("invest-type", investigationRuleVO.getInvsType());
   
     fields.addAttribute("master-code", investigationRuleVO.getActMasterCode());
     
     SQLXML commRuleFieldsXML = conn.createSQLXML();
     commRuleFieldsXML.setString( staticDoc.asXML());
     hmXmlTypes.put("StaticXml",commRuleFieldsXML );
     
     //creating ProviderFaclityTypes type xml
     Document proTypeDoc = DocumentHelper.createDocument();
     Element rootProFaclityTypes = proTypeDoc.addElement("Provider-Faclity-Types");
     String strProTypes=investigationRuleVO.getProviderTypesID();
     if(strProTypes!=null){
    	 String[]arrProFaclityTypes=strProTypes.split("[|]");
    	 for(String proFaclityType:arrProFaclityTypes){
    		 rootProFaclityTypes.addElement("type").setText(proFaclityType);
    	 }
     }
     SQLXML providerTypesXML = conn.createSQLXML();
     providerTypesXML.setString( proTypeDoc.asXML());
     hmXmlTypes.put("ProviderFaclityTypes",providerTypesXML );
 
	   //creating encounter types xml
     Document encountersDoc = DocumentHelper.createDocument();
     Element rootEncounters = encountersDoc.addElement("Encounters");
     String strEncounter=investigationRuleVO.getEncounterTypes();
     if(strEncounter!=null){
    	 String[]arrEncounters=strEncounter.split("[|]");
    	 for(String encounetr:arrEncounters){
    		 rootEncounters.addElement("type").setText(encounetr);
    	 }
     }
     SQLXML encountersXML = conn.createSQLXML();
     encountersXML.setString( encountersDoc.asXML());
     hmXmlTypes.put("Encounters",encountersXML );
     
     
     
     //creating country xml
     Document countryDoc = DocumentHelper.createDocument();
     Element rootContries = countryDoc.addElement("Countries");
     String strContries=investigationRuleVO.getCountryIDs();
     if(strContries!=null){
    	 String[]arrContries=strContries.split("[|]");
    	 for(String country:arrContries){
    		 rootContries.addElement("gen-id").setText(country);
    	 }
     }
     SQLXML countriesXML = conn.createSQLXML();
     countriesXML.setString( countryDoc.asXML());
     hmXmlTypes.put("Countries",countriesXML );
	
     //creating emirate xml
     Document emirateDoc = DocumentHelper.createDocument();
     Element rootEmirate = emirateDoc.addElement("Emirates");
     String strEmirates=investigationRuleVO.getEmiratesIDs();
     if(strEmirates!=null){
    	 String[]arrEmirates=strEmirates.split("[|]");
    	 for(String emirate:arrEmirates){
    		 rootEmirate.addElement("gen-id").setText(emirate);
    	 }
     }
     SQLXML emirateXML = conn.createSQLXML();
     emirateXML.setString( emirateDoc.asXML());
     hmXmlTypes.put("Emirates",emirateXML );
     
	   //creating clinician copay details  xml
     Document clinCopayDoc = DocumentHelper.createDocument();
     Element rootClin = clinCopayDoc.addElement("Clinicians");
     String strClinCopayDetails=investigationRuleVO.getClinicianCopayDetails();
     if(strClinCopayDetails!=null&&strClinCopayDetails.length()>1){
    	 
    	 String[]arrrClinCopayDetails=strClinCopayDetails.split("[|]");
    if(arrrClinCopayDetails!=null&&arrrClinCopayDetails.length>0){
    	 for(String copayDetails:arrrClinCopayDetails){
    		 Element rootCoapyDtl = rootClin.addElement("Copay-Details");
    		 
    		 String arrGenIds[]=copayDetails.split("[@]");
    		 if(arrGenIds!=null&&arrGenIds.length>0){
    		 String arrCpDetails[]=arrGenIds[1].split("[_]");
    		 if(arrCpDetails!=null&&arrCpDetails.length>0){
    		 rootCoapyDtl.addAttribute("type", arrGenIds[0]);
    		 rootCoapyDtl.addAttribute("copay", arrCpDetails[0]);
    		 rootCoapyDtl.addAttribute("deduct", arrCpDetails[1]);
    		 rootCoapyDtl.addAttribute("flag", arrCpDetails[2]);
    		 }
    		 }
    	 }
    }// if(arrrClinCopayDetails!null&&arrrClinCopayDetails.length>0){
     }
     SQLXML copayDetailsXML = conn.createSQLXML();
     copayDetailsXML.setString( clinCopayDoc.asXML());
     hmXmlTypes.put("ClcnCopayDtl",copayDetailsXML );
      
return hmXmlTypes;
}
public static void getRnlDlsConfdtls(ResultSet rs,InvestigationRuleVO investRuleVO)throws Exception{
	
	
investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));	
  
SQLXML sdXmlType =rs.getSQLXML("STATIC_COND");

 if(sdXmlType!=null){
    SAXReader saxReader = new SAXReader();
   Document  staticdoc = saxReader.read(sdXmlType.getCharacterStream());
  
   Node fieldNode=staticdoc.selectSingleNode("/Renal-Dialysis/Fields");
   if(fieldNode!=null){
   investRuleVO.setClaimType(fieldNode.valueOf("@claim-type"));  
   investRuleVO.setNetworkYN(fieldNode.valueOf("@network-type"));
   investRuleVO.setPreApprvReqYN(fieldNode.valueOf("@pre-appr-yn"));
   investRuleVO.setPreApprvLimit(fieldNode.valueOf("@pre-appr-limit"));
   investRuleVO.setGeoLocationID(fieldNode.valueOf("@geo-location"));  
   investRuleVO.setProviderTypes(fieldNode.valueOf("@provider-type"));
   investRuleVO.setPerPolicyLimit(fieldNode.valueOf("@per-policy-limit"));
   investRuleVO.setPerClaimLimit(fieldNode.valueOf("@per-claim-limit"));
   investRuleVO.setOvrCopay(fieldNode.valueOf("@ovr-copay"));
   investRuleVO.setOvrDeductible(fieldNode.valueOf("@ovr-deductable"));
   investRuleVO.setOvrMinMaxFlag(fieldNode.valueOf("@ovr-minmax"));
   investRuleVO.setOvrApplOn(fieldNode.valueOf("@ovr-applay-on"));
   investRuleVO.setPerClaimCpyDdctLimit(fieldNode.valueOf("@per-clm-cd-limit"));
   investRuleVO.setPerPolicyCpyDdctLimit(fieldNode.valueOf("@per-plcy-cd-limit"));
   investRuleVO.setInvsType(fieldNode.valueOf("@invest-type"));
 
   investRuleVO.setActMasterCode(fieldNode.valueOf("@master-code"));
   }//if(fieldNode!=null){
   }//if(sdXmlType!=null){
	
 SQLXML encXmlType =rs.getSQLXML("ENCOUNTER_TYPE");
	if(encXmlType!=null){
       SAXReader saxReader = new SAXReader();       
      Document  encdoc = saxReader.read(encXmlType.getCharacterStream());
      List<Node> listType=encdoc.selectNodes("/Encounters/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEncounters=stringBuilder.toString();
    	  if(strEncounters.length()>0)investRuleVO.setEncounterTypes(strEncounters.substring(0, strEncounters.length()-1));
      }
	}//if(encXmlType!=null){
	
	SQLXML prfXmlType =rs.getSQLXML("PROVIDER_FACILITY_TYPE");
	if(prfXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(prfXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Provider-Faclity-Types/type");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strPRF=stringBuilder.toString();
    	  if(strPRF.length()>0)investRuleVO.setProviderTypesID(strPRF.substring(0, strPRF.length()-1));
      }
	}//if(prfXmlType!=null){
	
	SQLXML countryXmlType =rs.getSQLXML("COUNTRIES_ID");
	if(countryXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(countryXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Countries/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strCon=stringBuilder.toString();
    	  if(strCon.length()>0)investRuleVO.setCountryIDs(strCon.substring(0, strCon.length()-1));
      }
	}//if(countryXmlType!=null){
	

	SQLXML emrtXmlType =rs.getSQLXML("EMIRATES_ID");
	if(emrtXmlType!=null){
       SAXReader saxReader = new SAXReader();
      Document  doc = saxReader.read(emrtXmlType.getCharacterStream());
      List<Node> listType=doc.selectNodes("/Emirates/gen-id");
      
      if(listType!=null){
    	  StringBuilder stringBuilder=new StringBuilder();
    	  for(Node typeNode:listType){
    		  stringBuilder.append(typeNode.getText());
    		  stringBuilder.append("|");
    	  }
    	  
    	  String strEmrt=stringBuilder.toString();
    	  if(strEmrt.length()>0)investRuleVO.setEmiratesIDs(strEmrt.substring(0, strEmrt.length()-1));
      }
	}//if(countryXmlType!=null){

	
}

}
