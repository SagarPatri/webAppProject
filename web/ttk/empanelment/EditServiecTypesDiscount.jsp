<%@page import="java.util.LinkedHashSet"%>
<%@page import="org.apache.struts.action.DynaActionForm"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.apache.taglibs.standard.tag.common.xml.ForEachTag"%>
<%@page import="com.ttk.dto.administration.TariffUploadVO"%>
<%
/**
 * @ (#) EditServiecTypesDiscount.jsp 16th May 2015
 * Project      : TTK HealthCare Services
 * File         : EditServiecTypesDiscount.jsp
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 16th May 2015
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
 <%@ page import="java.util.ArrayList, com.ttk.common.TTKCommon" %>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/empanelment/EditServiecTypesDiscount.js"></script>
<SCRIPT type="text/javascript">
bAction = false;
var TC_readonly = true;
</SCRIPT>
<%
pageContext.setAttribute("alPayerCode", Cache.getCacheObject("payerCode"));
pageContext.setAttribute("alProviderCode", Cache.getCacheObject("providerCode"));
pageContext.setAttribute("alNetworkType", Cache.getCacheObject("networkType"));
pageContext.setAttribute("alCorpCode", Cache.getCacheObject("corpCode"));
pageContext.setAttribute("groupProviderList",Cache.getCacheObject("groupProviderList"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ShowServiceTypesEmpanelment.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td>Service Types Discount Update</td>
		  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<div class="horizontalContentArea">
	<html:errors/>
	<!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
					<bean:write name="updated" scope="request"/>
				</td>
			</tr>
		</table>
	</logic:notEmpty>
	
	<logic:notEmpty name="warning" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/Error.gif" alt="Error" width="16" height="16" align="absmiddle">&nbsp;
					<bean:write name="warning" scope="request"/>
				</td>
			</tr>
		</table>
	</logic:notEmpty>
	
	
	<!-- Search box -->
	<fieldset><legend>Service Types Discount Update</legend>
	<table class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="right" nowrap>Type of Tariff: <span class="mandatorySymbol">*</span> :</td>
        <td align="left">
			<html:select property="tariffType" name="frmTariffUpdateServiceTypes" styleClass="selectBox selectBoxMedium" onchange="onChangeTariffType(this)">
				<html:option value="HOSP">Provider</html:option>
			</html:select>
		</td>
		<td> &nbsp;</td>
		</tr>
		
		<tr id="INDGRP" style="display: none;">
        <td align="right" nowrap>Individual/Group: </td>
        <td align="left">
			<html:select property="indOrGrp" styleClass="selectBox selectBoxMedium" onchange="onChangeIndOrGrp(this)">
				<html:option value="IND">Individual</html:option>
				<html:option value="GRP">Group</html:option>
			</html:select>
		</td>
		<td> &nbsp;</td>
		</tr> 
	
	<tr id="GrpProviderNameTR" style="display: none;">
        <td align="right" nowrap>Group Name: </td>
        <td align="left">
			<html:select property ="grpProviderName" styleClass="selectBox selectBoxMedium" onchange="changeGroup()">		
      			<html:options collection="groupProviderList" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>
		</td>
		<td> &nbsp;</td>
		</tr>
		
		<tr>
		<td align="right" nowrap>Provider :</td>
		 <td align="left">
		 	<html:text property="providerCode" styleId="providerCode" styleClass="textBox textBoxMedium" value="<%= (String)request.getSession().getAttribute("AuthLicenseNo") %>" readonly="true"/>
		</td>
		</tr>
		
		<tr>
        <td align="right" nowrap>Payer :</td>
         <td align="left">
            <logic:empty  name="frmSearchTariffItem" scope="session">
		 	<html:text property="payerCode" styleId="payerCode" styleClass="textBox textBoxMedium" readonly="true" value="TPA021"/>
			</logic:empty>
			<logic:notEmpty name="frmSearchTariffItem" scope="session">
			<html:text property="payerCode" styleId="payerCode" styleClass="textBox textBoxMedium" readonly="true"/>
			</logic:notEmpty> 
		
			<a href="#" onClick="openRadioListIntX1('payerCode','PAYERSCODE')" style="display: inline;" id="apayerCode"><img src="/ttk/images/EditIcon.gif" alt="Select Payers" width="16" height="16" border="0" align="absmiddle"></a>
		</td>
		</tr>
		<tr>
		<%-- <td align="right" nowrap>Network Type : </td>
		 <td align="left">
		 	<html:text property="networkType" styleId="networkType" styleClass="textBox textBoxMedium" readonly="true"/>
			<a href="#" onClick="openListIntX('networkType','NETWORKS')" style="display: inline;" id="anetworkType"><img src="/ttk/images/EditIcon.gif" alt="Select Networks" width="16" height="16" border="0" align="absmiddle"></a>
		</td> --%>
		
		<td align="right" nowrap>Network Type <span class="mandatorySymbol">*</span>: </td>
		 <td align="left">
		 	<html:text property="networkType" styleId="networkType" styleClass="textBox textBoxMedium" readonly="true" />
			<a href="#" onClick="openListTariffIntX('networkType','NETWORKSGEN','providerCode')" style="display: inline;" id="anetworkType"><img src="/ttk/images/EditIcon.gif" alt="Select Networks" width="16" height="16" border="0" align="absmiddle"></a>
		</td>
		</tr>
		
		
		</tr>
		
		<tr>
		<td align="right" nowrap>Corporate : </td>
		 <td align="left">
		 	<html:text property="corpCode" styleId="corpCode" styleClass="textBox textBoxMedium" readonly="true"/>
			<a href="#" onClick="openListIntX('corpCode','CORPORATES')" style="display: inline;" id="acorpCode"><img src="/ttk/images/EditIcon.gif" alt="Select Corporates" width="16" height="16" border="0" align="absmiddle" ></a>
		</td>
		</tr>
		<tr>
      </table>
      </fieldset> 
      <br>
      <table class="formContainer" border="0" cellspacing="0" cellpadding="0">
     <tr>
    <td  colspan="2" align="center">
	<button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onNextServiceTypes()"><u>N</u>ext</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBackServiceTypes()"><u>B</u>ack</button>
       	</td>
       </tr>
    </table>
    
	<!-- Search box ends here -->
	<br><br><br>
	<logic:notEmpty name="alServiceTypes" scope="session"> 
	 <table class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
         <td align="left" nowrap>All Services:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      
      <html:select property ="serviceall" styleId="serviceall" styleClass="selectBox selectBoxMedium" onchange="changeServiceTypeYN()"  style="width:62px;height:22px;">
	 <html:option value="Y">Yes</html:option>  
	 <html:option value="N">No</html:option> 
	 </html:select>
     </td>
       </tr>
       </table>
       <br/>
       <br/>
	<table class="formContainer" border="1" cellspacing="0" cellpadding="0" style="width: 60%">
	 <tr bgcolor="#04B4AE">
	 		<td align="left" class="formLabelBold" style="width: 50%"> Service Types</td>
	 <%!int i=0,j=0; 
	 Set set=null,set1=null,set2=null;
	
	 %>
	 <%
	
	 ArrayList<String> alServiceTypes	=	(ArrayList<String>)request.getSession().getAttribute("alServiceTypes");
	 
	 set	=	new LinkedHashSet();//for iterating networks names
	 set1	=	new LinkedHashSet();//for iterating service 
	 set2	=	new LinkedHashSet();//for service Seq Ids service 
	
	for(int k=0;k<alServiceTypes.size();k++)
	{
		String tt[];
		tt	=	alServiceTypes.get(k).toString().split("\\|");
	    set.add(tt[1]);
		set1.add(tt[0]);
		set2.add(tt[2]);
	} 
	Iterator it	=	set.iterator();
	Iterator it1	=	set1.iterator();
	while(it.hasNext()){
		%><th><%= it.next()%> </th><%
	}
%>	</tr>
<tr><td>All Services</td>

	<%for(int l=0;l<set.size();l++){
	
		%>
	<td> <input type="text" size="3" name="netDiscListServiceType" id="netDiscListServiceType<%=l%>"onkeyup="isNumeric(this);setServiceValue(<%=l%>);" maxlength="6" value="0"> </td>		
	<%
	
	} %>
</tr>
<%
i=0;
while(it1.hasNext()){
	
	j=0;
	%><tr>
<td><%= it1.next()%>  </td>
	<%for(int l=0;l<set.size();l++){ 

	%>
	<logic:empty property="serviceall" name="frmTariffUpdateServiceTypes" scope="session">
	<td> <input type="text" size="3" name="netDiscList" onkeyup="isNumeric(this)" id="netDiscList<%=i%><%=j%>" class="disabledBox" readonly="true" maxlength="6" value="0"> </td>		
	</logic:empty>
	<logic:equal property="serviceall" name="frmTariffUpdateServiceTypes" scope="session" value="N">
	<td> <input type="text" size="3" name="netDiscList" onkeyup="isNumeric(this)" id="netDiscList<%=i%><%=j%>" maxlength="6" value="0"> </td>		
	</logic:equal>
	<logic:equal property="serviceall" name="frmTariffUpdateServiceTypes" scope="session" value="Y">
	<td> <input type="text" size="3" name="netDiscList" onkeyup="isNumeric(this)" id="netDiscList<%=i%><%=j%>" maxlength="6" value="0" class="disabledBox" readonly="true"> </td>		
	</logic:equal>
	<%
	j++;}
	%>
</tr><%
i++;}
request.getSession().setAttribute("networks", set);
request.getSession().setAttribute("services", set1);
request.getSession().setAttribute("serviceIds", set2);

%>
	 
	 </table>
	
	<table class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
       	<td  colspan="2" align="center">
	<button type="button" name="updateButton" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUpdateSaveServiceTypes()"><u>U</u>pdate</button>
       	</td>
       </tr>
    </table>
    <input type="hidden" name="servicessize" value="<%=set.size()%>">
    <input type="hidden" name="servicesidd" value="<%=set1.size()%>">
	</logic:notEmpty>
	
    <!-- E N D : SELCTIONS -->
	<!-- E N D : Buttons -->
	<!-- E N D : Form Fields -->
   </div>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="child" value="">
	<input type="hidden" name="selectedIds" value="">
	
		<script>
if(document.forms[1].tariffType.value!="")	
	onChangeTariffType(document.forms[1].tariffType);
</script>
 </html:form>