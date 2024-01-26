<%
/**
 * @ (#) preauthgeneral.jsp May 10, 2006
 * Project      : TTK HealthCare Services
 * File         : preauthgeneral.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : May 10, 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,org.dom4j.Document"%>
<%@page import="org.dom4j.Node,java.util.List,java.util.Iterator" %>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<%
String PreAuthHistoryTypeID = (String)request.getAttribute("PreAuthHistoryTypeID");
String strLink=TTKCommon.getActiveLink(request);
pageContext.setAttribute("PreAuthHistoryTypeID",PreAuthHistoryTypeID);
pageContext.setAttribute("strLink",strLink);
Document document=(Document)request.getAttribute("historyDoc");
%>
<div id="contentArea" class="contentArea">
<%
 if(document!=null){
	Node pNode=document.selectSingleNode("/preauthorizationhistory/preauthorizationdetails");
	List<Node> dNodes=document.selectNodes("/preauthorizationhistory/preauthorizationdetails/diagnosysdetails");
	List<Node> aNodes=document.selectNodes("/preauthorizationhistory/preauthorizationdetails/activitydetails");
%>
<fieldset>
			<legend>Pre-Authorization Details</legend>
			<%if(pNode!=null){ %>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td width="22%" class="formLabel">Pre-Auth No.:</td>
			    <td width="30%" class="textLabelBold"><%=pNode.valueOf("@preauthnumber")%></td>
			    <td class="formLabel" width="19%">Mode of Preauth:</td>
			      <td class="textLabel">
				     <%=pNode.valueOf("@source")%>
			      </td>
			  </tr>
			  <tr>
			   <td class="formLabel">Admission Date / Time:</td>
			    <td class="textLabel"><%=pNode.valueOf("@admissiondate")%></td>
			    <td class="formLabel" width="19%">Discharege Date:</td>
			      <td class="textLabel"><%=pNode.valueOf("@discharegedate")%></td>			    
			  </tr>
			  <tr>
			   <td class="formLabel">Member Id:</td>
			      <td class="textLabel"><%=pNode.valueOf("@memberid")%></td>
			  <td class="formLabel">Patient Name:</td>
			      <td class="textLabel"><%=pNode.valueOf("@memname")%></td>
			  </tr>
			   <tr>
			   <td class="formLabel">Age:</td>
			      <td class="textLabel"><%=pNode.valueOf("@age")%></td>
			  <td class="formLabel">Emirate Id:</td>
			      <td class="textLabel"><%=pNode.valueOf("@emirateid")%></td>
			  </tr>
			  <tr>
			  <td class="formLabel" width="19%">Encounter Type:</td>
			      <td class="textLabel"><%=pNode.valueOf("@encountertype")%></td>
			      <td class="formLabel">Encounter Facility Id:</td>
			      <td class="textLabel"><%=pNode.valueOf("@encfacilityid")%></td>
			  </tr>
			   <tr>
			  <td class="formLabel" width="19%">Encounter Start Type:</td>
			      <td class="textLabel"><%=pNode.valueOf("@encstarttype")%></td>
			      <td class="formLabel">Encounter End Type:</td>
			      <td class="textLabel"><%=pNode.valueOf("@encendtype")%></td>
			  </tr>
			  <tr>
			  <td class="formLabel" width="19%">Autherization No.:</td>
			      <td class="textLabel"><%=pNode.valueOf("@authnumber")%></td>
			      <td class="formLabel">Status:</td>
			      <td class="textLabel"><%=pNode.valueOf("@status")%></td>
			  </tr>
			  <tr>
			   <td class="formLabel">Id Payer:</td>
			      <td class="textLabel"><%=pNode.valueOf("@idpayer")%></td>
			      <td class="formLabel">Payer Id:</td>
			      <td class="textLabel"><%=pNode.valueOf("@payerid")%></td>
			  </tr>
			  <tr>
			   <td class="formLabel">Payer Name:</td>
			      <td class="textLabel"><%=pNode.valueOf("@payername")%></td>
			      <td class="formLabel">Provider Name:</td>
			      <td class="textLabel"><%=pNode.valueOf("@providername")%></td>
			  </tr>
			  <tr>
			   <td class="formLabel">Provider Id:</td>
			      <td class="textLabel"><%=pNode.valueOf("@providerid")%></td>
			      <td class="formLabel">Provider Details:</td>
			      <td class="textLabel"><%=pNode.valueOf("@providerdetails")%></td>
			  </tr>
			  <tr>
			   <td class="formLabel" width="19%">Empanel Number:</td>
			   <td class="textLabel"><%=pNode.valueOf("@empanenumber")%></td>
			   <td class="formLabel" width="19%">Treatment Type:</td>
			   <td class="textLabel"><%=pNode.valueOf("@treatmenttype")%></td>
			  </tr>
			  <tr>
			   <td class="formLabel" width="19%">Gross Amount:</td>
			   <td class="textLabel"><%=pNode.valueOf("@grossamt")%></td>
			   <td class="formLabel" width="19%">Net Amount:</td>
			   <td class="textLabel"><%=pNode.valueOf("@netamt")%></td>
			  </tr>
			   <tr>
			   <td class="formLabel" width="19%">Patient Share:</td>
			   <td class="textLabel"><%=pNode.valueOf("@patientshare")%></td>
			   <td class="formLabel" width="19%">Approved Amount:</td>
			   <td class="textLabel"><%=pNode.valueOf("@approvedamt")%></td>
			  </tr>			  
			   </table>			 
			  <%} %>
			  
		</fieldset>
		<fieldset>		
		<legend>Diagnosis Details</legend>
		 <table align="center"  border="1pt" cellspacing="0" cellpadding="0" style="width:650px;height:auto;">
			  <tr style="height:20px;background-color: #85C2FF;color:white; ">
			   <th class="formLabel" align="center">ICD Code</th>
			    <th class="formLabel" align="center">Ailment Desription</th>			   
			    <th class="formLabel" align="center">Primary Ailment</th>			    			    
			  </tr>
	<%if(dNodes!=null){			
			for(Node dNode:dNodes){			
			%>		   
			  <tr>
			   <td align="center"><%=dNode.valueOf("@diagcode") %></td>
			   <td align="center"><%=dNode.valueOf("@description") %></td>			     
			   <td align="center"><%=dNode.valueOf("@primary") %></td>				 			  				  
			  </tr>				  
			  <%} }%>
			  </table>	 			
		</fieldset>
		<fieldset>	         
			<legend>Activity Details</legend>
		  <table align="center"   border="1" cellspacing="0" cellpadding="0">
			<tr style="height:20px;background-color: #85C2FF;color:white; ">
			<th align="center">Activity Code</th>
			<th align="center">Modifier</th>
			<th align="center">Unit Type</th>
			<th align="center">Quantity</th>
			<th align="center">Start Date</th>
			<th align="center">Gross Amt</th>
			<th align="center">Discount</th>
			<th align="center">Discounted Gross</th>
			<th align="center">Patient Share</th>
			<th align="center">Net Amt</th>
			<th align="center">Approved Amt</th>
			<th align="center">Copay</th>
		    <th align="center">Denial Code</th>
		    <th align="center">Denial Dec</th>
		    <th align="center">Deductble</th>
		    <th align="center">Remarks</th>
			</tr>
			<%if(aNodes!=null){				
				for(Node aNode:aNodes){
				List<Node> oNodes=aNode.selectNodes("observationdetails");
				int rows=(oNodes==null)?1:oNodes.size()+1;
				%>			   
			<tr style="height:20px;">
			<td align="center"><%=aNode.valueOf("@actid") %></td>
			<td align="center"><%=aNode.valueOf("@modifier") %></td>
			<td align="center"><%=aNode.valueOf("@unittype") %></td>
			<td align="center"><%=aNode.valueOf("@qty") %></td>
			<td align="center"><%=aNode.valueOf("@startdate") %></td>
			<td align="center"><%=aNode.valueOf("@gross") %></td>
			<td align="center"><%=aNode.valueOf("@disc") %></td>
			<td align="center"><%=aNode.valueOf("@discgross") %></td>
			<td align="center"><%=aNode.valueOf("@patientshare") %></td>
			<td align="center"><%=aNode.valueOf("@net") %></td>
			<td align="center"><%=aNode.valueOf("@appramt") %></td>
			<td align="center"><%=aNode.valueOf("@copay") %></td>
			<td align="center"><%=aNode.valueOf("@denialcode") %></td>
			<td align="center"><%=aNode.valueOf("@denialdesc") %></td>
			<td align="center"><%=aNode.valueOf("@ded") %></td>
			<td align="center"><%=aNode.valueOf("@remarks") %></td>
			</tr>
			<tr align="left">
			<td colspan="16">
			<table border="1" style="width:75%;">			
			<tr>
			 <th rowspan="<%=rows%>" align="center">Observation Details</th><th align="center">Type</th><th align="center">Code</th><th align="center">Value</th><th align="center">Value Type</th>
			</tr>
			<%
			if(oNodes!=null){				
				for(Node oNode:oNodes){			
			%>
			<tr>
			<td align="center"><%=oNode.valueOf("@type") %></td>
			<td align="center"><%=oNode.valueOf("@code") %></td>
			<td align="center"><%=oNode.valueOf("@value") %></td>
			<td align="center"><%=oNode.valueOf("@valuetype") %></td>
			</tr>									
		<%
		} //oNodes for
		}//oNodes if
		%>
		</table>
		</td>
		</tr>
			<%}//aNodes for			
				}//aNodes if
			%>
	</table>
	</fieldset>		
 <%}//document if%>
</div>

