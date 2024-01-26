<%@page import="java.util.ArrayList"%>
<%
/**
 * @ (#) showlist.jsp Aug 13th 2006
 * Project      : TTK HealthCare Services
 * File         : showlist.jsp
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : Aug 13th 2006
 *
 * @author       : Krishna K H
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

function save()
{
	objform = document.forms[0];
	var checkid="checkid";
	var strCheckedVal="";
	var controlname=  document.forms[0].strControlName.value;
	var showRadio=document.forms[0].showRadio.value;
	if(showRadio=='Y')  //If showing radio box
	{
		for(var i=0;i<objform.length;i++)
		{
			var regexp=new RegExp("^(radid){1}[0-9]{1,}$"); 
			if(regexp.test(objform.elements[i].id))
			{
				if(objform.elements[i].checked == true)
				{
					strCheckedVal=objform.elements[i].value;
					break;
				}//end of if(objform.elements[i].checked == true)
			}//end of if(regexp.test(objform.elements[i].id))
		}//end of for(i=0;i<objform.length;i++)
		
		if(strCheckedVal=='')
		{
			alert('Select atleast one record');
			return false;
		}//end of if(strCheckedVal=='')
	}//end of if(showRadio=='Y')
	else
	{
		for(var i=0;i<objform.length;i++)
		{
			var regexp=new RegExp("("+checkid+"){1}[0-9]*");
			if(regexp.test(objform.elements[i].id))
			{
				if(objform.elements[i].checked == true)
					strCheckedVal=strCheckedVal+objform.elements[i].value+"|";
			}//end of if(regexp.test(objform.elements[i].id))
		}//end of for(i=0;i<objform.length;i++)
		if(strCheckedVal=="|") // if no check box is selected
			strCheckedVal="";
		else 				 // remove the last '|'
			strCheckedVal=strCheckedVal.substring(0,strCheckedVal.lastIndexOf('|'));
	}//end of else
	if (window.opener && !window.opener.closed)
			window.opener.document.getElementById(controlname).value = strCheckedVal;	
	self.close();
}//end of save()

function checkPreSelect(iCount)
{
	var iTotalChecked = 0;
	var showRadio=document.forms[0].showRadio.value;
	var controlId=document.forms[0].strControlName.value;
	if(showRadio=='Y')		//for showing radio box
	{
		var selVal= window.opener.document.getElementById(controlId).value;
		for(var i=0;i<iCount;i++)
		{
			objCheck = document.getElementById("radid"+i);
			if(objCheck.value == selVal)
			{
				objCheck.checked= true;
				break;
			}//end of if(objCheck.value == selVal)
		}//end of for(i=0;i<objform.length;i++)
	}//end of if(showRadio=='Y')
	else	//for showing check box
	{
		for(var i=0;i<iCount;i++)
		{
			objCheck = document.getElementById("checkid"+i);
			if(isSelected(objCheck.value)==1)
			{
				objCheck.checked = true;
				iTotalChecked++;
			}//end of if(strPreSelected.indexOf(objCheck.value)>=0)
		}//end of for(i=0;i<iCount;i++)
		if(iTotalChecked==iCount)
			document.getElementById("checkall").checked = true;
	}//end of else
}//end of checkPreSelect(iCount)

function isSelected(strId)
{
	var controlname = document.forms[0].strControlName.value;
	var strPreSelected = window.opener.document.getElementById(controlname).value;
	var temp = new Array();
	temp = strPreSelected.split('|');
	for(var j=0;j<temp.length;j++)
		if(temp[j]==strId)
		  return 1;
	return 0;
}//end of isSelected(strId)

function oncheck(strId)
{
	var i	=	0;
	var bFlag=true;
	if(strId =='checkall')
		bFlag = document.getElementById("checkall").checked;
	var checkid="checkid";
	objform = document.forms[0];
	for(var i=0;i<objform.length;i++)
	{
		var regexp=new RegExp("("+checkid+"){1}[0-9]*");
		if(regexp.test(objform.elements[i].id))
		{
			if(strId =='checkall')
				objform.elements[i].checked = bFlag;
			else if(objform.elements[i].checked != true)
			{
				bFlag=false;
				break;
			}//end of else if(objform.elements[i].checked != true)
		}//end of if(regexp.test(objform.elements[i].id))
	}//end of for(i=0;i<objform.length;i++)
 	document.getElementById("checkall").checked = bFlag ;
}//end of oncheck(strId)
</script>
<SCRIPT type="JavaScript/text" SRC="/ttk/scripts/validation.js"></SCRIPT>

<%
		String strTypeId = TTKCommon.checkNull((String)request.getParameter("typeId"));
		String strControlName = TTKCommon.checkNull((String)request.getParameter("controlName"));
		String strControlValue = TTKCommon.checkNull((String)request.getParameter("controlVal"));
		String strShowRadio=TTKCommon.checkNull((String)request.getParameter("showRadio"));
		String strDynaTypeId = TTKCommon.checkNull((String)request.getParameter("dynaTypeId"));
		String strDynaParam = TTKCommon.checkNull((String)request.getParameter("dynaParam"));
		String strIdTile = "";
		String strDescTitle ="";
		int iRowCount = 0;
	if("DynamicValues".equals(strTypeId)){
		pageContext.setAttribute("dataList", Cache.getDynamicCacheObject(strDynaTypeId, strDynaParam));
		strIdTile = "";
		strDescTitle ="State Name";
	}else if(strTypeId.equals("STA"))
		{
			
		}//end of if(strTypeId.equals("STA"))
		 else if(strTypeId.equals("CON"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("countryCode"));			
			strIdTile = "";
			strDescTitle ="Country Name";
		}//end of else if(strTypeId.equals("CON"))
		 else if(strTypeId.equals("ROOM_TYPE"))
			{
				pageContext.setAttribute("dataList", Cache.getCacheObject("roomType"));			
				strIdTile = "";
				strDescTitle ="Room Types";
			}//end of else if(strTypeId.equals("Room Types"))
		else if(strTypeId.equals("CES"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("checkeStatus"));			
			strIdTile = "";
			strDescTitle ="CES Name";
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
		else if(strTypeId.equals("CURRENCY_GROUP"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("currencyGroup"));
			strIdTile = "";
			strDescTitle ="Currency Groups";
		}//end of else if(strTypeId.equals("ACH"))		
		else if(strTypeId.equals("NETWORK_GROUP"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("networkGroup"));
			strIdTile = "";
			strDescTitle ="Network Groups";
		}//end of else if(strTypeId.equals("ACH"))
		else if(strTypeId.equals("NATIONALITIES"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("nationalities"));
			strIdTile = "";
			strDescTitle ="NATIONALITIES";
		}//end of else if(strTypeId.equals("ACH"))PROVIDER_NETWORK
		else if(strTypeId.equals("PROVIDER_NETWORK"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("providerNetworkGroup"));
			strIdTile = "";
			strDescTitle ="Provider Network Groups";
		}//end of else if(strTypeId.equals("ACH"))
		else if(strTypeId.equals("TREATMENT_TYPE"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("treatementType"));
			strIdTile = "";
			strDescTitle ="TREATMENT TYPE ALLOWED";
		}//end of else if(strTypeId.equals("ACH"))
		else if(strTypeId.equals("DEDUCTIBLES_PUB_NETWORK"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("deductablesPubNetwork"));
			strIdTile = "";
			strDescTitle ="DEDUCTIBLES NETWORK";
		}//end of else if(strTypeId.equals("ACH"))
		else if(strTypeId.equals("DEDUCTIBLES_PRI_NETWORK"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("deductablesPriNetwork"));
			strIdTile = "";
			strDescTitle ="DEDUCTIBLES NETWORK";
		}//end of else if(strTypeId.equals("ACH"))
		else if(strTypeId.equals("INOUT_PATIENT"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("patientGroup"));
			strIdTile = "";
			strDescTitle ="Provider Network Groups";
		}//end of else if(strTypeId.equals("ACH"))GOV_HOSPITAL_GROUP
		else if(strTypeId.equals("PRE_CLAME"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("authorization"));
			strIdTile = "";
			strDescTitle ="Provider Network Groups";
		}//end of else if(strTypeId.equals("ACH"))GOV_HOSPITAL_GROUP
		else if(strTypeId.equals("GOV_HOSPITAL_GROUP"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("govHospitalGroup"));
			strIdTile = "";
			strDescTitle ="Gov Hospital Groups";
		}//end of else if(strTypeId.equals("ACH"))MATERNITY_GROUP
		else if(strTypeId.equals("MATERNITY_GROUP"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("maternityGroup"));
			strIdTile = "";
			strDescTitle ="Maternity Groups";
		}//end of else if(strTypeId.equals("ACH"))
		else if(strTypeId.equals("DRUGS_GROUP"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("drugsGroup"));
			strIdTile = "";
			strDescTitle ="Drugs Groups";
		}//end of else if(strTypeId.equals("ACH"))
		else if(strTypeId.equals("SERVICES"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("servicesList"));
			strIdTile = "";
			strDescTitle ="Services List";
		}//end of else if(strTypeId.equals("ACH"))
		else if(strTypeId.equals("DAY_CARE"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("dayCareList"));
			strIdTile = "";
			strDescTitle ="Day Care List";
		}//end of else if(strTypeId.equals("ACH"))DIETICIAN_NEUTRICIAN
		else if(strTypeId.equals("CHRONIC_CONDITION"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("chronicCondition"));
			strIdTile = "";
			strDescTitle ="CHRONIC CONDITION";
		}//end of else if(strTypeId.equals("ACH"))DIETICIAN_NEUTRICIAN
		else if(strTypeId.equals("DIETICIAN_NEUTRICIAN"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("dieticianNeutricianList"));
			strIdTile = "";
			strDescTitle ="Dietician Neutrician List";
		}//end of else if(strTypeId.equals("ACH"))
		else if(strTypeId.equals("WOMEN_HORMONAL"))
		{
			pageContext.setAttribute("dataList", Cache.getCacheObject("womenHormonalList"));
			strIdTile = "";
			strDescTitle ="Women Health And Hormonal List";
		}//end of else if(strTypeId.equals("ACH"))WOMEN_HORMONAL
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
		}else if (strTypeId.equals("PROVIDERSCODE"))
		{
			pageContext.setAttribute("dataList",Cache.getCacheObject("providerCode"));
			strIdTile = "";
			strDescTitle = "PROVIDERS";
		}else if (strTypeId.equals("NETWORKS"))
		{
			pageContext.setAttribute("dataList",Cache.getCacheObject("networkType"));
			strIdTile = "";
			strDescTitle = "NETWORKS";
		}
		else if (strTypeId.equals("CORPORATES"))
		{
			pageContext.setAttribute("dataList",Cache.getCacheObject("corpCode"));
			strIdTile = "";
			strDescTitle = "CORPORATES";
		}else if (strTypeId.equals("MATERNITY_ENCOUNTERS"))
		{
			pageContext.setAttribute("dataList",Cache.getCacheObject("maternityEncounters"));
			strIdTile = "";
			strDescTitle = "MATERNITY ENCOUNTERS";
		}	
		
		
			else if(strTypeId.equals("SELECTEDPRODUCT"))
		{
			pageContext.setAttribute("dataList",Cache.getCacheObject("productCode"));
			strIdTile = "";
			strDescTitle ="Product Name";
		}//end of if(strTypeId.equals("STA"))		
		
		else if (strTypeId.equals("XMLDashboards"))
		{
			
			pageContext.setAttribute("dataList",Cache.getCacheObject("XMLDashboards"));
			strIdTile = "";
			strDescTitle = "DASHBOARDS";
		}
		else if (strTypeId.equals("ProviderTypes"))
		{
			
			pageContext.setAttribute("dataList",Cache.getCacheObject("providerType"));
			strIdTile = "";
			strDescTitle = "Provider Types";
		}
		else if (strTypeId.equals("GeoLocations"))
		{
			
			pageContext.setAttribute("dataList",Cache.getCacheObject("GeoLocations"));
			strIdTile = "";
			strDescTitle = "Geographical Locations";
		}
		
		//ended
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/CorporateMemberAction.do" >
<title>List of - <%=strDescTitle%></title>
	<div class="contentArea" id="contentArea">
	<html:errors/>

	<table align="center" class="gridWithCheckBox" border="0" cellspacing="1" cellpadding="0">
	<tr>
		<td width="5%" ID="listsubheader" CLASS="gridHeader">
		<%if(!strShowRadio.equals("Y"))
		{
		%>
			<input name="checkall" id="checkall" type="checkbox" onclick="javascript:oncheck('checkall')">&nbsp;
		<%
		}//end of if(!strshowRadio.equals("Y"))
		%>
        <%=strIdTile%></td>
		<td width="95%" ID="listsubheader" CLASS="gridHeader"> <%=strDescTitle%> &nbsp;</td>
	</tr>
	<logic:notEmpty name="dataList">
		<logic:iterate id="dataList" name="dataList">
		   <%if(iRowCount%2==0) { %>
				<tr class="gridOddRow">
			<%
			  } else { %>
  				<tr class="gridEvenRow">
  			<%
			  } %>
				<td width="5%" align="center">
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
				<td width="95%" class="textLabelBold"><bean:write name="dataList" property="cacheDesc" /></td>
			</tr>
			<%iRowCount++;%>
		</logic:iterate>
	</logic:notEmpty>
	  <tr>
		<td align="center" colspan="2" class="buttonsContainerGrid">
			  <%if(iRowCount!=0) { %>
				<input type="button" name="Button" value="Save" class="buttons" onClick="javascript:save();">&nbsp;
				<%
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
<script>
	checkPreSelect(<%=iRowCount%>);
</script>
<!-- E N D : Buttons --></div>
<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->

</html:form>
