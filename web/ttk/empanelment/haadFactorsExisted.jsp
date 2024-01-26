<%@page import="com.ttk.dto.empanelment.HospitalDetailVO"%>
<%
/** @ (#) haadFactorsDetails.jsp 17th Aug 2016
 * Project     : TTK Healthcare Services
 * File        : haadFactorsDetails.jsp
 * Author      : Kishor kumar
 * Company     : RCS Technologies
 * Date Created: 
 *
 * @author 		 : Kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="java.util.ArrayList,com.ttk.common.TTKCommon,com.ttk.common.security.Cache,org.apache.struts.action.DynaActionForm" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/empanelment/haadFactorsDetails.js"></script>

<head>
	<link rel="stylesheet" type="text/css" href="ttk/styles/style.css" />
</head>
<%
ArrayList alHaadCategories=(ArrayList)request.getAttribute("alHaadCategories")==null?(ArrayList)request.getSession().getAttribute("alHaadCategories"):(ArrayList)request.getAttribute("alHaadCategories");
ArrayList alColumnHeaders=(ArrayList)request.getSession().getAttribute("alColumnHeaders");
String hosp_seq_id	=	request.getParameter("hosp_seq_id")==null?"0":request.getParameter("hosp_seq_id");
ArrayList alEligibleNetworks	=	(ArrayList)request.getSession().getAttribute("alEligibleNetworks");


request.getSession().setAttribute("alHaadCategories", alHaadCategories);

%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/EditHaadFactorsHospitalAction.do">
		
		<logic:notEmpty name="fileError" scope="request" >
	<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <bean:write name="fileError" scope="request"/>
	        </td>
	      </tr>
   	 </table>
</logic:notEmpty>


	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
	    	<td>&nbsp;&nbsp;HAAD Factors 
		   	</td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	
	<!-- S T A R T : Form Fields -->
	
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

<fieldset>
			<legend>Add Haad Factors</legend>
			<table align="center" border="1" cellspacing="0" cellpadding="1" style="width: 70%">
			<tr> <td nowrap width="50%"> Eligible Networks <span class="mandatorySymbol">*</span> :</td>
				<td width="50%">
					<html:select property ="eligibleNetworks" styleClass="selectBox selectBoxMedium">
					<html:option value="">--Select From List--</html:option>          					               		
					<html:options collection="alEligibleNetworks" property="cacheId" labelProperty="cacheDesc"/>
					</html:select>
			</td>
			</tr>
			</table>
			<br>
			<table align="center" border="1" cellspacing="0" cellpadding="1" style="width: 70%">
				<tr bgcolor="#04B4AE">
    				<th width="20%" style="padding: 5px;">GROUP </th>
    				<%
					for(int j=0;j<alColumnHeaders.size();j++){
					%>
						<th width="20%" style="padding: 5px;"><%=alColumnHeaders.get(j) %></th>
					<%
					}
					%>
    				
	    		</tr>
				<%for(int k=0;k<alHaadCategories.size();k++){
					String cache[]	=	(String[])alHaadCategories.get(k); 
					%>
					<tr <%=(k%2)==0? "gridOddRow":"gridEvenRow"%>">	
					<th> <%=cache[1]%> <input type="hidden" name="groupName" value="<%=cache[0]%>"> </th>
					<td> <input type="text" name="factor" class="textBoxSmallest" onkeyup="isNumeric(this);"> 	</td>	
					<td> <input type="text" name="baseRate" class="textBoxSmallest" onkeyup="isNumeric(this);"></td>	
					<td> <input type="text" name="gap" class="textBoxSmallest" onkeyup="isNumeric(this);">		</td>	
					<td> <input type="text" name="margin" class="textBoxSmallest" onkeyup="isNumeric(this);">	</td>
					</tr>
					<%
				}%>
				
	    	</table>
</fieldset>	    					
  		<!-- S T A R T : Buttons -->
  		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
    			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveHaadFactors();"><u>S</u>ave</button>&nbsp;
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  				<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>B</u>ack</button>&nbsp;
 				</td>
  			</tr>  		
		</table>
	</div>
<input type="hidden" name="mode" value="">
<input type="hidden" name="hosp_seq_id" value="<%=hosp_seq_id%>">
</html:form>
