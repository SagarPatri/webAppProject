<%@page import="javax.wsdl.Output"%>
<%@page import="com.lowagie.tools.plugins.AbstractTool.Console"%>
<%@page import="com.ttk.dto.common.CacheObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%
/**
 * @ (#) showlistintx.jsp Aug 13th 2006
 * Project      : TTK HealthCare Services
 * File         : showlistintx.jsp
 * Author       : kishor kumar S H
 * Company      : RCS technologies
 * Date Created : 21st May 2015
 *
 * @author       :kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.WebBoardHelper,com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<script>
function showEnableRadio() 
{
	var checkid0 =document.getElementById("checkid0");
	var controlId = document.forms[0].strControlName.value;
	//var iRowCountsss=document.getElementById("iRowCountsss").value;
	var strTypeId = document.forms[0].strTypeId.value;
	var activetab = document.forms[0].activetab.value;
	
	if(strTypeId=="PAYERSCODE" && activetab=="Tariff")
		{
	if (checkid0.checked == true) {
			for (var i = 0; i <= 387; i++) {
				document.getElementById("radid" + i).disabled = "true";
			}
		} else if (checkid0.checked == false) {
			for (var i = 0; i <= 387; i++) {
				
			//	document.getElementById("radid" + i).disabled = "false";
			 document.getElementById("radid" + i).removeAttribute("disabled"); 
			}
		}
		}
	

	}
function saveAppInsComp(){
	objform = document.forms[0];
	var checkid = "checkid";
	var strCheckedVal = "";
	var strCheckedDesc = "";
	var value = "";
	var splitArray = "";
	var applicableins= "";
	var controlname = document.forms[0].strControlName.value;
	for (i = 0; i < objform.length; i++) {
		var regexp = new RegExp("(" + checkid + "){1}[0-9]*");
		if (regexp.test(objform.elements[i].id)) {
			if (objform.elements[i].checked == true){
				value =  objform.elements[i].value;
				splitArray = value.split('\|');
				strCheckedVal = strCheckedVal
						+ splitArray[0] + "|";
				strCheckedDesc = strCheckedDesc
				+ splitArray[1] + "|";
			}
		}//end of if(regexp.test(objform.elements[i].id))
	}//end of for(i=0;i<objform.length;i++)
	if (strCheckedVal == "|") // if no check box is selected
		{
		strCheckedVal = "";
		strCheckedDesc = "";
		}
	else {
		// remove the last '|'
		strCheckedVal = strCheckedVal.substring(0, strCheckedVal
				.lastIndexOf('|'));
		strCheckedDesc = strCheckedDesc.substring(0, strCheckedDesc
				.lastIndexOf('|'));
	}
	applicableins = strCheckedVal +"@"+strCheckedDesc;
	
	if (window.opener && !window.opener.closed)
		window.opener.document.getElementById(controlname).value = applicableins;
	    window.opener.document.getElementById("notiSaveBtnID").click();
	self.close();
       /*  document.forms[0].mode.value = "doSave";
        document.forms[0].action ="/SaveNotificationAction.do";
        document.forms[0].submit(); */
}
	function save() {
		objform = document.forms[0];
		var checkid = "checkid";
		var strCheckedVal = "";
		
		var controlname = document.forms[0].strControlName.value;
		var showRadio = document.forms[0].showRadio.value;
		var strTypeId = document.forms[0].strTypeId.value;
		
		if(strTypeId=="PAYERSCODE")
			{
			 objCheck0 = document.getElementById("checkid0");
			if(objCheck0.checked==false)
			showRadio="Y"
			}
		
		if (showRadio == 'Y') //If showing radio box
		{
			for (var i = 0; i < objform.length; i++) {
				var regexp = new RegExp("^(radid){1}[0-9]{1,}$");
				if (regexp.test(objform.elements[i].id)) {
					if (objform.elements[i].checked == true) {
						strCheckedVal = objform.elements[i].value;
						break;
					}//end of if(objform.elements[i].checked == true)
				}//end of if(regexp.test(objform.elements[i].id))
			}//end of for(i=0;i<objform.length;i++)

			if (strCheckedVal == '') {
				alert('Select atleast one record');
				return false;
			}//end of if(strCheckedVal=='')
		}//end of if(showRadio=='Y')
		else {
			if ("PAYERSCODEGEN" == strTypeId) {
				var chkdYN = "";
				for (i = 0; i < objform.length; i++) {
					var regexp = new RegExp("(" + checkid + "){1}[0-9]*");
					if (regexp.test(objform.elements[i].id)) {
						if (objform.elements[i].checked == true)
							chkdYN = "Y";
						else
							chkdYN = "N";
						strCheckedVal = strCheckedVal
								+ objform.elements[i].value + "|" + chkdYN
								+ "||";
					}//end of if(regexp.test(objform.elements[i].id))

				}//end of for(i=0;i<objform.length;i++)
				strCheckedVal = "||" + strCheckedVal;
				if (strCheckedVal == "||") // if no check box is selected
					strCheckedVal = "";
				else
					// remove the last '|'
					strCheckedVal = strCheckedVal.substring(0, strCheckedVal
							.lastIndexOf('||'));
			} else {
				for (i = 0; i < objform.length; i++) {
					var regexp = new RegExp("(" + checkid + "){1}[0-9]*");
					if (regexp.test(objform.elements[i].id)) {
						if (objform.elements[i].checked == true)
							strCheckedVal = strCheckedVal
									+ objform.elements[i].value + "|";
					}//end of if(regexp.test(objform.elements[i].id))
				}//end of for(i=0;i<objform.length;i++)
				if (strCheckedVal == "|") // if no check box is selected
					strCheckedVal = "";
				else {
					// remove the last '|'
					strCheckedVal = strCheckedVal.substring(0, strCheckedVal
							.lastIndexOf('|'));
					if(strCheckedVal=="TPA033")
						{
						 /* strCheckedVal="TPA021"; */
						}
				}
			}
		}//end of else
		if (window.opener && !window.opener.closed)
			window.opener.document.getElementById(controlname).value = strCheckedVal;
		self.close();
	}//end of save()

	function checkPreSelect(iCount) {
		var iTotalChecked = 0;
		var txtBoxVals = document.forms[0].txtBoxVals.value;
		var showRadio = document.forms[0].showRadio.value;
		var controlId = document.forms[0].strControlName.value;
		var strTypeId = document.forms[0].strTypeId.value;
		/* var activelink = document.forms[0].activelink.value;
		var activesublink = document.forms[0].activesublink.value; */
		var activetab = document.forms[0].activetab.value;
		if (showRadio == 'Y') //for showing radio box
		{
			var selVal = window.opener.document.getElementById(controlId).value;
			for (var i = 0; i < iCount; i++) {
				objCheck = document.getElementById("radid" + i);
				if (objCheck.value == selVal) {
					objCheck.checked = true;
					break;
				}//end of if(objCheck.value == selVal)
			}//end of for(i=0;i<objform.length;i++)
		}//end of if(showRadio=='Y')
		else //for showing check box
		{
			if (strTypeId == "PAYERSCODEGEN") {

				
				var txtBoxValsArr = txtBoxVals.split("\|");
				var l = 3;
				for (var k = 0; k < txtBoxValsArr.length; k++) {
					/* if(txtBoxValsArr[k+1]=="Y")
					{ */
					objCheck = document.getElementById("checkid" + k);
					//if(isSelectedForEmpnl(txtBoxValsArr[k+1])==1)
					if (txtBoxValsArr[l] == "Y") {
						objCheck.checked = true;
						iTotalChecked++;
					}//end of if(strPreSelected.indexOf(objCheck.value)>=0)
					//}
					l = l + 3;
				}

				/* for(i=0;i<iCount;i++)
				{
					objCheck = document.getElementById("checkid"+i);
					if(isSelected(objCheck.value)==1)
					{
						objCheck.checked = true;
						iTotalChecked++;
					}//end of if(strPreSelected.indexOf(objCheck.value)>=0)
				}//end of for(i=0;i<iCount;i++)
				 */} 
			if (strTypeId == "PAYERSCODE"  &&  activetab=="Tariff" && txtBoxVals == "TPA021") {
				
				 objCheck0 = document.getElementById("checkid0");
				
				if (objCheck0.checked == true) {
				//	var iRowCountsss=document.getElementById("iRowCountsss").value;
				
					for (var i = 0; i <= 387; i++) {
						document.getElementById("radid" + i).disabled = "true";
					}
				} else if (checkid0.checked == false) {
					for (var i = 0; i <= 387; i++) {
						document.getElementById("radid" + i).disabled = "false";
					}
				} 
			}
			else {
				for (i = 0; i < iCount; i++) {
					objCheck = document.getElementById("checkid" + i);
					if (isSelected(objCheck.value) == 1) {
						objCheck.checked = true;
						iTotalChecked++;
					}//end of if(strPreSelected.indexOf(objCheck.value)>=0)
				}//end of for(i=0;i<iCount;i++)}
			}
			if (iTotalChecked == iCount)
				{
				if(!strTypeId == "PAYERSCODE")
				document.getElementById("checkall").checked = true;
				}
		}//end of else
	}//end of checkPreSelect(iCount)

	function isSelected(strId) {
		var controlname = document.forms[0].strControlName.value;
		var strPreSelected = window.opener.document.getElementById(controlname).value;
		var temp = new Array();
		temp = strPreSelected.split('|');
		for (var j = 0; j < temp.length; j++)
			if (temp[j] == strId)
				return 1;
		return 0;
	}//end of isSelected(strId)

	function isSelectedForEmpnl(strId) {
		var controlname = document.forms[0].strControlName.value;
		var strPreSelected = window.opener.document.getElementById(controlname).value;
		var temp = new Array();
		temp = strPreSelected.split('|');
		//for(j=1;j<temp.length;j=(j+1))
		alert("strId::" + strId + "::index::" + index + "temp[index]::"
				+ temp[index]);
		if (temp[index] == strId)
			return 1;
		return 0;
	}//end of isSelected(strId)

	function oncheck(strId) {
		var i = 0;
		var bFlag = true;
		if (strId == 'checkall')
			bFlag = document.getElementById("checkall").checked;
		var checkid = "checkid";
		objform = document.forms[0];
		for (i = 0; i < objform.length; i++) {
			var regexp = new RegExp("(" + checkid + "){1}[0-9]*");
			if (regexp.test(objform.elements[i].id)) {
				if (strId == 'checkall')
					objform.elements[i].checked = bFlag;
				else if (objform.elements[i].checked != true) {
					bFlag = false;
					break;
				}//end of else if(objform.elements[i].checked != true)
			}//end of if(regexp.test(objform.elements[i].id))
		}//end of for(i=0;i<objform.length;i++)
		document.getElementById("checkall").checked = bFlag;
	}//end of oncheck(strId)
</script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>

<%
		String strTypeId = TTKCommon.checkNull((String)request.getParameter("typeId"));
		String strControlName = TTKCommon.checkNull((String)request.getParameter("controlName"));
		String strControlValue = TTKCommon.checkNull((String)request.getParameter("controlVal"));
		String strShowRadio=TTKCommon.checkNull((String)request.getParameter("showRadio"));
		String strCondParam=TTKCommon.checkNull((String)request.getParameter("condParam"));
		String stractivelink=TTKCommon.checkNull((String)request.getParameter("activelink"));
		String stractivesublink=TTKCommon.checkNull((String)request.getParameter("activesublink"));
		String stractivetab=TTKCommon.checkNull((String)request.getParameter("activetab"));
		String strPayerCode=TTKCommon.checkNull((String)request.getParameter("payerCode"));	
		String strIdTile = "";
		String strDescTitle ="";
		int iRowCount = 0;
		if(strTypeId.equals("STA"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("stateCode"));
			strIdTile = "";
			strDescTitle ="State Name";
		}//end of if(strTypeId.equals("STA"))
		else if(strTypeId.equals("CON"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("countryCode"));
			strIdTile = "";
			strDescTitle ="Country Name";
		}//end of else if(strTypeId.equals("CON"))
		else if(strTypeId.equals("REL"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("relationshipCode"));
			strIdTile = "";
			strDescTitle ="Relationship";
		}//end of else if(strTypeId.equals("REL"))
		else if(strTypeId.equals("ACH"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("ruleAccountHead"));
			strIdTile = "";
			strDescTitle ="Account Head";
		}//end of else if(strTypeId.equals("ACH"))
		else if(strTypeId.equals("DAYCARE_GROUP"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("daycareGroup"));
			strIdTile = "";
			strDescTitle ="Ailment Groups";
		}//end of else if(strTypeId.equals("ACH"))
		else if(strTypeId.equals("MICD"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("maternityICDGroup"));
			strIdTile = "";
			strDescTitle ="Maternity ICD Groups";
		}//end of else if(strTypeId.equals("ACH"))
		
		//Changes As  per KOC 1284 Vhange Request
		else if(strTypeId.equals("REGION"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("regioncode"));
			strIdTile = "";
			strDescTitle ="Select Regions";
		}//end of else if(strTypeId.equals("REGION"))   
		//Changes As  per KOC 1284 Vhange Request
		//ADDED FOR KOC-1310
		else if (strTypeId.equals("CANCER_GROUP"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("cancerGroup"));
			strIdTile = "";
			strDescTitle = "Cancer ICD Groups";
		}else if (strTypeId.equals("PAYERSCODE"))
		{
		    pageContext.setAttribute("dataList",Cache.getCacheObject("payerCode"));
			strIdTile = "";
			strDescTitle = "PAYERS";
		}else if (strTypeId.equals("PAYERSCODEGEN"))
		{
			pageContext.setAttribute("dataList",Cache.getCacheObject("payerCode"));
			strIdTile = "";
			strDescTitle = "PAYERS";
		}
		else if (strTypeId.equals("PROVIDERSCODEGEN"))
		{
			pageContext.setAttribute("dataList",Cache.getCacheObject("providerCode"));
			strIdTile = "";
			strDescTitle = "PROVIDERS";
		}
		else if (strTypeId.equals("PROVIDERSCODE"))
		{
			pageContext.setAttribute("dataList",Cache.getCacheObject1("providerCodeAdminTariff",strCondParam, request));
			strIdTile = "";
			strDescTitle = "PROVIDERS";
		}else if (strTypeId.equals("NETWORKS"))
		{
			pageContext.setAttribute("dataList",Cache.getCacheObject("networkType"));
			strIdTile = "";
			strDescTitle = "NETWORKS";
		}
		else if (strTypeId.equals("NETWORKSGEN"))
		{
			if(strPayerCode.equals("AMT")){
				pageContext.setAttribute("dataList",Cache.getCacheObject("networkTypeForAMT"));
			}else if(strPayerCode.equals("NIA")){
				pageContext.setAttribute("dataList",Cache.getCacheObject("networkTypeForNIA"));	
			}else if(strPayerCode.equals("OIC")){
				pageContext.setAttribute("dataList",Cache.getCacheObject("networkTypeForOIC"));		
			}
			strIdTile = "Selection";
			strDescTitle = "NETWORKS";
		}
		else if (strTypeId.equals("ApplicableInsCompany"))
		{
			pageContext.setAttribute("dataList",Cache.getCacheObject("applicableInsCompany"));
			strIdTile = "";
			strDescTitle = "Insurance Companies";
		}
		else if (strTypeId.equals("CORPORATES"))
		{
			pageContext.setAttribute("dataList",Cache.getCacheObject("corpCode"));
			strIdTile = "";
			strDescTitle = "CORPORATES";
		}else if (strTypeId.equals("HAADGROUP"))
		{
			pageContext.setAttribute("dataList",Cache.getCacheObject("haadGroup"));
			strIdTile = "";
			strDescTitle = "HAAD GROUP";
		}
		else if (strTypeId.equals("SALARY BAND"))
		{
			Collection<Object> resultList =  null;
			String relation = request.getParameter("relation");
			if(strControlValue.equals("DHA") || strControlValue.equals("MOH")||strControlValue.equals("CMA"))
			{
				if(relation.contains("NSF"))
				{
					resultList =  new ArrayList<Object>();
					  CacheObject voBean = new CacheObject();
					 voBean.setCacheId("GTFLTT");
					 voBean.setCacheDesc("Greater Than 4000 Less Than 12000");
					 CacheObject voBean1 = new CacheObject();
					 voBean1.setCacheId("LEF");
					 voBean1.setCacheDesc("Less Than Or Equal 4000");
					 CacheObject voBean2 = new CacheObject();
					 voBean2.setCacheId("GTET");
					 voBean2.setCacheDesc("Greater Than Or Equal 12000");
					
					 resultList.add(voBean);
					 resultList.add(voBean1);
					 resultList.add(voBean2);
				}
				else
				{
					 resultList =  new ArrayList<Object>();
					  CacheObject voBean = new CacheObject();
					 voBean.setCacheId("GTFLTT");
					 voBean.setCacheDesc("Greater Than 4000 Less Than 12000");
					 CacheObject voBean1 = new CacheObject();
					 voBean1.setCacheId("LEF");
					 voBean1.setCacheDesc("Less Than Or Equal 4000");
					 CacheObject voBean2 = new CacheObject();
					 voBean2.setCacheId("GTET");
					 voBean2.setCacheDesc("Greater Than Or Equal 12000");
					 
					 CacheObject voBean3 = new CacheObject();
					 voBean3.setCacheId("NA");
					 voBean3.setCacheDesc("Not Applicable");
					 resultList.add(voBean);
					 resultList.add(voBean1);
					 resultList.add(voBean2);
					 resultList.add(voBean3);
					
				}
			}
			else if(strControlValue.equals("HAAD"))
			{
				if(relation.contains("NSF"))
				{

					resultList =  new ArrayList<Object>();
					  CacheObject voBean = new CacheObject();
					 voBean.setCacheId("GTFI");
					 voBean.setCacheDesc("Greater Than 5000");
					 CacheObject voBean1 = new CacheObject();
					 voBean1.setCacheId("LTEFI");
					 voBean1.setCacheDesc("Less Than or Equal 5000");
					 resultList.add(voBean);
					 resultList.add(voBean1);
					 
					 
				
				}
				else {
					
					 resultList =  new ArrayList<Object>();
					  CacheObject voBean = new CacheObject();
					 voBean.setCacheId("GTFI");
					 voBean.setCacheDesc("Greater Than 5000");
					 CacheObject voBean1 = new CacheObject();
					 voBean1.setCacheId("LTEFI");
					 voBean1.setCacheDesc("Less Than or Equal 5000");
					 CacheObject voBean2 = new CacheObject();
					 voBean2.setCacheId("NA");
					 voBean2.setCacheDesc("Not Applicable");
					 
					 resultList.add(voBean);
					 resultList.add(voBean1);
					 resultList.add(voBean2);
				}
				
			}
			// for other health authorities
			else {
				if(relation.contains("NSF"))
				{

					resultList =  new ArrayList<Object>();
					  CacheObject voBean = new CacheObject();
					 voBean.setCacheId("GTFI");
					 voBean.setCacheDesc("Greater Than 5000");
					 CacheObject voBean1 = new CacheObject();
					 voBean1.setCacheId("LTEFI");
					 voBean1.setCacheDesc("Less Than or Equal 5000");
					 resultList.add(voBean);
					 resultList.add(voBean1);
					 
					 
				
				}
				else {
					
					 resultList =  new ArrayList<Object>();
					  CacheObject voBean = new CacheObject();
					 voBean.setCacheId("GTFI");
					 voBean.setCacheDesc("Greater Than 5000");
					 CacheObject voBean1 = new CacheObject();
					 voBean1.setCacheId("LTEFI");
					 voBean1.setCacheDesc("Less Than or Equal 5000");
					 CacheObject voBean2 = new CacheObject();
					 voBean2.setCacheId("NA");
					 voBean2.setCacheDesc("Not Applicable");
					 
					 resultList.add(voBean);
					 resultList.add(voBean1);
					 resultList.add(voBean2);
				}
				
			}
			pageContext.setAttribute("dataList",resultList);
			strIdTile = "";
			strDescTitle = "SALARY BAND";
		}
		else if(strTypeId.equals("RELATION"))
		{
			Collection<Object> resultList =  null;
			resultList =  new ArrayList<Object>();
			  CacheObject voBean = new CacheObject();
			 voBean.setCacheId("NSF");
			 voBean.setCacheDesc("Self/Principal");
			 CacheObject voBean1 = new CacheObject();
			 voBean1.setCacheId("YSP");
			 voBean1.setCacheDesc("Spouse");
			 CacheObject voBean2 = new CacheObject();
			 voBean2.setCacheId("NCH");
			 voBean2.setCacheDesc("Children");
			 CacheObject voBean3 = new CacheObject();
			 voBean3.setCacheId("NFR");
			 voBean3.setCacheDesc("Father");
			 CacheObject voBean4 = new CacheObject();
			 voBean4.setCacheId("YMO");
			 voBean4.setCacheDesc("Mother");
			 CacheObject voBean5 = new CacheObject();
			 voBean5.setCacheId("OTH");
			 voBean5.setCacheDesc("Others");
			 resultList.add(voBean);
			 resultList.add(voBean1);
			 resultList.add(voBean2);
			 resultList.add(voBean3);
			 resultList.add(voBean4);
			 resultList.add(voBean5);
			 pageContext.setAttribute("dataList",resultList);
				strIdTile = "";
				strDescTitle = "Relation to List";
		}
		
		//ended
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/CorporateMemberAction.do" >
<title>List of - <%=strDescTitle%></title>
	<div class="contentArea" id="contentArea">
	<html:errors/>

	<table align="center" class="gridWithCheckBox" border="0" cellspacing="1" cellpadding="0">
	<%-- <tr>
	<td width="90%" ID="listsubheader" CLASS="gridHeader"> <%=strDescTitle%> &nbsp;</td>
		<td width="10%" ID="listsubheader" CLASS="gridHeader" nowrap="nowrap">
		<%if(!strShowRadio.equals("Y"))
		{
		%>
			Check All<input name="checkall" id="checkall" type="checkbox" onclick="javascript:oncheck('checkall')">&nbsp;
		<%
		}//end of if(!strshowRadio.equals("Y"))
		%>
        <%=strIdTile%></td>
		
	</tr> --%>
<%-- 	<% if (strTypeId.equals("PAYERSCODE"))
			{
	%>
		<tr>
		<td width="90%" ID="listsubheader" CLASS="gridHeader"> DEFAULT PAYER &nbsp;</td>
			<!-- <td width="10%" ID="listsubheader" CLASS="gridHeader" nowrap="nowrap"> -->
	<td width="10%" ID="listsubheader" CLASS="gridHeader" nowrap="nowrap">
		<%if(!strShowRadio.equals("Y"))
		{
		%>
			Check All<input name="checkall" id="checkall" type="checkbox" onclick="javascript:oncheck('checkall');javascript:showEnableRadio();">&nbsp;
		<%
		}//end of if(!strshowRadio.equals("Y"))
		%>
        <%=strIdTile%></td>
		</td>
		</tr>
	<% 
	} else --%>
		<%-- <%
		if (strTypeId.equals("NETWORKSGEN")){
	%>
		<tr>
		<td width="70%" ID="listsubheader" CLASS="gridHeader">NETWORKS &nbsp;</td>
			<td width="30%" ID="listsubheader" CLASS="gridHeader" nowrap="nowrap"> Selection
		</td>
		</tr>
	
		<%
			ArrayList<CacheObject>datalist = (ArrayList<CacheObject>)Cache.getCacheObject("primaryNetwork");
			if(datalist.size()>0){
				for(CacheObject obj : datalist){%>
					<% 			 if(iRowCount%2==0) {
						%>
						<tr class="gridOddRow">
							<%
								} else {
							%>
						
						<tr class="gridEvenRow">
							<%}
							%>
				<td width="90%" class="textLabelBold"><%=obj.getCacheDesc()%></td>
				<td width="10%" align="center" nowrap="nowrap"><input
					type="radio" id="radid<%=iRowCount%>" name="optRadio"
					 value="<%=obj.getCacheId()%>"   /></td>
				
				<% 			
				}
			}	
		%>
	
	<% 	
		}
	%> --%>
	
	 <% if (!strTypeId.equals("PAYERSCODE")) {
	%>
	<tr>
<td width="90%" ID="listsubheader" CLASS="gridHeader"> <%=strDescTitle%> &nbsp;</td>
		<td width="10%" ID="listsubheader" CLASS="gridHeader" nowrap="nowrap">
			<%if(!strShowRadio.equals("Y"))
		{
		%>
			Check All<input name="checkall" id="checkall" type="checkbox" onclick="javascript:oncheck('checkall')">&nbsp;
		<%
		}//end of if(!strshowRadio.equals("Y"))
		%>
		 <%=strIdTile%>
		 </td>
		 </tr>
<% 	
	}
	  if (strTypeId.equals("PAYERSCODE")) {
		
		ArrayList<CacheObject>datalist = (ArrayList<CacheObject>)Cache.getCacheObject("payerCode");
	   
		if(datalist.size()>0)
		{
		for(CacheObject obj : datalist)
			{
			//	if(obj.getCacheId().equals("TPA033") && strControlValue.equals("TPA021"))
			//	{		%>
					<!-- <tr>
				<td width="90%" class="textLabelBold"> -->
				<%-- <%=obj.getCacheDesc()%> --%>
			<%-- </td>
		<td width="10%" align="center" nowrap="nowrap">
	   <input type="checkbox" id="checkid0" onclick="javascript:oncheck('');javascript:showEnableRadio();" name="optSelect" checked="checked" value="<%=obj.getCacheId()%>" >
				
	   </td>	
		</tr>	 --%>		
				<%-- <%
				}
				else if(obj.getCacheId().equals("TPA033"))
				{		%> --%>
			<%-- 		<tr>
				<td width="90%" class="textLabelBold"><%=obj.getCacheDesc()%></td>
		<td width="10%" align="center" nowrap="nowrap">
	   <input type="checkbox" id="checkid0" onclick="javascript:oncheck('');javascript:showEnableRadio();" name="optSelect"  value="<%=obj.getCacheId()%>" >
				
	   </td>	
		</tr>	 --%>		
				<%
			//	}
			}
		
		}
		%>
			 <!--  <tr>
		<td align="center" colspan="2" class="buttonsContainerGrid">
		
		   <input type="button" name="Button" value="Save" class="buttons" onClick="javascript:save();">&nbsp;
			<input type="button" name="Button" value="Close" class="buttons" onClick="javascript:self.close();">
		</td>
	  </tr> -->
		<tr>
		<td width="70%" ID="listsubheader" CLASS="gridHeader">PAYERS &nbsp;</td>
			<td width="30%" ID="listsubheader" CLASS="gridHeader" nowrap="nowrap"> Selection
		</td>
		</tr>
	<% 	
	}
				ArrayList<CacheObject>datalist = (ArrayList<CacheObject>)Cache.getCacheObject("payerCode");
			%>

			<%
				if (strTypeId.equals("PAYERSCODE")) { 
							if(datalist.size()>0)
							{
								for(CacheObject obj : datalist)
								{
									if(obj.getCacheId().equals("TPA033"))
									{
										continue;
									}%>
									
								<% 			 if(iRowCount%2==0) {
			%>
			<tr class="gridOddRow">
				<%
					} else {
				%>
			
			<tr class="gridEvenRow">
				<%}
				%>
								<% 	if (strTypeId.equals("PAYERSCODE") && strControlValue.equals("TPA021"))
									{
					%>
		
				<td width="90%" class="textLabelBold"><%=obj.getCacheDesc()%></td>
				<td width="10%" align="center" nowrap="nowrap"><input
					type="radio" id="radid<%=iRowCount%>" name="optRadio"
					 value="<%=obj.getCacheId()%>"   /></td>
					 <% 
					 } 
									else if (strTypeId.equals("PAYERSCODE") && obj.getCacheId().equals(strControlValue))
									{%>
										
													<td width="90%" class="textLabelBold"><%=obj.getCacheDesc()%></td>
													<td width="10%" align="center" nowrap="nowrap"><input
														type="radio" id="radid<%=iRowCount%>" name="optRadio"
														 value="<%=obj.getCacheId()%>"   checked="checked"/></td>
					
					 
					 <% }
									else if (strTypeId.equals("PAYERSCODE") && !obj.getCacheId().equals(strControlValue))
									{%>
									
									<td width="90%" class="textLabelBold"><%=obj.getCacheDesc()%></td>
									<td width="10%" align="center" nowrap="nowrap"><input
										type="radio" id="radid<%=iRowCount%>" name="optRadio"
										 value="<%=obj.getCacheId()%>" /></td>
	
	 
	 <%                             }%>

			</tr>
			<%
				iRowCount++;
					}
							}
					}
					 else if (!strTypeId.equals("PAYERSCODE")) {
			%>
			<logic:notEmpty name="dataList">
				 		<logic:iterate id="dataList" name="dataList">
		   <%if(iRowCount%2==0) {
			   %>
				<tr class="gridOddRow">
			<%
			  } else { %>
  				<tr class="gridEvenRow">
  			<%
			  } %>
			 	<td width="70%" class="textLabelBold"><bean:write name="dataList" property="cacheDesc" /></td>
				<td width="30%" align="center" nowrap="nowrap" >
				<%
				if(strShowRadio.equals("Y")){
				%>
				<input type="radio" id="radid<%=iRowCount%>" name="optRadio" value="<bean:write name="dataList" property="cacheId" />">
				<%
				}else{
				%>
				<input type="checkbox" id="checkid<%=iRowCount%>" onclick="javascript:oncheck('');" name="optSelect" value="<bean:write name="dataList" property="cacheId" />">
				<%}%>				
				</td>
				
			</tr>
			<%
			iRowCount++; %>
			</logic:iterate>
	</logic:notEmpty>
			<%
			}%>

	  <tr>
		<td align="center" colspan="2" class="buttonsContainerGrid">
			  <%if(iRowCount!=0) { 
				  if(strTypeId.equals("PAYERSCODEGEN")){
			  %>
				<input type="button" name="Button" value="Associate" class="buttons" onClick="javascript:save();">&nbsp;
				<%
				}
				  else if(strTypeId.equals("ApplicableInsCompany")){
				%>
					<input type="button" name="Button" value="Save" class="buttons" onClick="javascript:saveAppInsComp();">&nbsp;
				<%
				  }
				  else{ 
				  %>
					<input type="button" name="Button" value="Save" class="buttons" onClick="javascript:save();">&nbsp;
					<%
				  }
			  }%>
			<input type="button" name="Button" value="Close" class="buttons" onClick="javascript:self.close();">
		</td>
	  </tr>
	</table>
	<!-- E N D : Form Fields -->
<p>&nbsp;</p>
</div>
<input type="hidden" name="strControlName" value="<%=strControlName%>">
<input type="hidden" name="showRadio" value="<%=strShowRadio%>">
<input type="hidden" name="strTypeId" value="<%=strTypeId %>" >
<input type="hidden" name="txtBoxVals" value="<%=request.getParameter("controlVal") %>" >
 <input type="hidden" name="activetab" id="activetab" value="<%=TTKCommon.getActiveTab(request)%>">
<input type="hidden" name="mode" value="" />
	
<script>
	checkPreSelect(<%=iRowCount%>);
</script>
<!-- E N D : Buttons --></div>
<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->

</html:form>
