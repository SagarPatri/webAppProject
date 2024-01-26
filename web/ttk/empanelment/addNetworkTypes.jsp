<%@page import="com.ttk.dto.empanelment.NetworkTypeVO"%>
<%
/**
 * @ (#) addNetworkTypes.jsp 13th June 2016
 * Project      : TTK HealthCare Services
 * File         : addNetworkTypes.jsp
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 13th June 2016
 * @author       :
 * Modified by   : Kishor kumar S H
 * Modified date : 13 Mar 2006
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList" %>

<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/empanelment/addNetworkTypes.js"></script>
<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
</head>


<!-- S T A R T : Content/Form Area -->
<html:form action="/HospitalNetworkAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
	    	<td>General Details - Add Network Types</td>
	    	<td align="right">
			</td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	
	<!-- S T A R T : Form Fields -->
	<%
		ArrayList<NetworkTypeVO> alNetworkTypeList=(ArrayList<NetworkTypeVO>)request.getSession().getAttribute("alNetworkTypeList");
		ArrayList<NetworkTypeVO> alNetworkHistory =(ArrayList<NetworkTypeVO>)request.getSession().getAttribute("alNetworkHistory");
	%>
	<div class="contentArea" id="contentArea">
		<html:errors/>
		
		<!-- S T A R T : Success Box -->
		<logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
			  		<td>
			  			<img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:message name="updated" scope="request"/>
			  		</td>
				</tr>
			</table>
		</logic:notEmpty>
		<!-- E N D : Success Box -->
		<logic:notEmpty name="fileError" scope="request" >
	<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <bean:write name="fileError" scope="request"/>
	        </td>
	      </tr>
   	 </table>
</logic:notEmpty>
		<fieldset>
			<legend>Add Network Type</legend>
			<table align="center" class="searchContainer"  border="0" cellspacing="0" cellpadding="0">
				<tr>
    				<td width="20%" class="formLabel">Network Code: <span class="mandatorySymbol">*</span><br>
    					<html:text property="sNetworkCode" styleClass="textBox textBoxMedium" maxlength="2" onkeypress="ConvertToUpperCase(event.srcElement);" />
    				</td>
    				<td width="20%" class="formLabel">Network Name: <span class="mandatorySymbol">*</span><br>
    					<html:text property="sNetworkName" styleClass="textBox textBoxLarge" maxlength="60" />
    				</td>
    				<td width="20%" class="formLabel">Network Order: <span class="mandatorySymbol">*</span><br>
    					<html:text property="sNetworkOrder" styleClass="textBox textBoxSmall" maxlength="3" onkeypress="return isNumberKey(event)" onkeyup="isNumeric(this)"/>
    				</td>
    				
	    			<td width="100%" align="center">
	    			<%
					if(TTKCommon.isAuthorized(request,"Edit"))
					{
					%>
			    		<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd();"><u>A</u>dd</button>&nbsp;
					<%
		    		}
	 				%>
	 				</td>
  			
  				</tr>
	 		</table>
		</fieldset>
		<br><br><br>
    	<fieldset>
    		<legend>Network Type List</legend>
    		<table align="center" border="1" cellspacing="0" cellpadding="1">
				<tr>
    				<th width="15%" style="padding: 5px;">NETWORK CODE </th>
    				<th width="30%" style="padding: 5px;">NETWORK NAME </th>
    				<th width="15%" style="padding: 5px;">NETWORK ORDER</th>
	    		</tr>

		<logic:iterate id="NetworkTypeVO" name="alNetworkTypeList" indexId="i">
		 	<tr class="<%=(i.intValue()%2)==0? "gridOddRow":"gridEvenRow"%>">	
		 	<logic:empty name="edit" scope="request">
				<td><bean:write name="NetworkTypeVO" property="networkCode"/></td>	
				<td><bean:write name="NetworkTypeVO" property="networkName"/></td>	
				<td><bean:write name="NetworkTypeVO" property="networkOrder"/>
					<html:hidden name="NetworkTypeVO" property="seqId"/>
				</td>
			</logic:empty>
			
			<logic:notEmpty name="edit" scope="request">
				<td> <html:text property="networkCode" name="NetworkTypeVO" maxlength="2" onkeypress="ConvertToUpperCase(event.srcElement);"></html:text>  </td>	
				<td> <html:text property="networkName" name="NetworkTypeVO" styleClass="textbox textBoxLargest"></html:text>  </td>	
				<td> <html:text property="networkOrder" name="NetworkTypeVO" onkeyup="isNumeric(this)" onblur="return isBlank(this,'Network Order')"></html:text> 
					 <html:hidden name="NetworkTypeVO" property="seqId"/>
				</td>
			</logic:notEmpty>	
			</tr>
		</logic:iterate>
	    	</table>
    	</fieldset>
    	
     	
	
		<!-- E N D : Form Fields -->
		
    	<!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
    			<%
				if(TTKCommon.isAuthorized(request,"Edit"))
				{
				%>
				<logic:notEmpty name="edit" scope="request">
		    		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUserSubmit();"><u>S</u>ave</button>&nbsp;
		    	</logic:notEmpty>	
					<button type="button" name="Button2" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onEdit();"><u>E</u>dit</button>&nbsp;
				<%
	    		}
 				%>
 				</td>
  			</tr>  		
		</table>
		<!-- E N D : Buttons -->
		
		<fieldset>
    		<legend>Network Type History</legend>
		<table align="center" border="1" cellspacing="0" cellpadding="1">
		<tr> <th colspan="2">Old Value </th> <th colspan="2">New Value </th> </tr>
		<tr> <th>Network Old Code</th> <th>Network Old Name </th> <th>Network New Code</th> <th>Network New Name </th></tr>
		
		<logic:iterate id="NetworkTypeVO" name="alNetworkHistory" indexId="i">
		 	<tr class="<%=(i.intValue()%2)==0? "gridOddRow":"gridEvenRow"%>">	
				<td width="15%"><bean:write name="NetworkTypeVO" property="networkCodeOld"/></td>	
				<td width="15%"><bean:write name="NetworkTypeVO" property="networkNameOld"/></td>	
				<td width="15%"><bean:write name="NetworkTypeVO" property="networkCodeNew"/></td>
				<td width="15%"><bean:write name="NetworkTypeVO" property="networkNameNew"/> </td>
			</tr>
		</logic:iterate>
	    	</table>
    	</fieldset>
		
	</div>
	<!-- E N D : Content/Form Area -->
		
	<input type="hidden" name="mode" value="">
    
</html:form>
<!-- E N D : Main Container Table -->