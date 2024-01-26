<%
/**
 * @ (#) webconfigmemdetails.jsp Jan 4th, 2008
 * Project      : TTK HealthCare Services
 * File         : webconfigmemdetails.jsp
 * Author       : Srikant_b
 * Company      : Span Systems Corporation
 * Date Created : Jan 4th, 2008
 * 
 * @author       :
 * Modified by   :Balaji C R B
 * Modified date :February 11, 2008
 * Reason        :Cache object change 
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/webconfigmemdetails.js"></script>
<%
	String[] strFieldStatusGenTypeID = (String[])request.getAttribute("fieldstatusgeneraltypeids");
	pageContext.setAttribute("webloginMemConfigAdslY",Cache.getCacheObject("webloginMemConfigAdslY"));
	pageContext.setAttribute("webloginMemConfigAdslN",Cache.getCacheObject("webloginMemConfigAdslN"));
	boolean blShowField = false;
%>
<html:form action="/WebConfigMemDetailsAction.do"> 
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>Web Configuration Information - Member Details<bean:write name="frmWebconfigMemDetails" property="caption"/></td>     
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
		    	<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
		    		<bean:message name="updated" scope="request"/>
		    	</td>
		 	</tr>
			</table>
		</logic:notEmpty>
		<logic:notEmpty name="doborageeditable" scope="request">
		<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><strong><img src="/ttk/images/ErrorIcon.gif" alt="Error" width="16" height="16" align="absmiddle">
			&nbsp;The following errors have occurred - </strong><ol style="padding:0px;margin-top:3px;margin-bottom:0px;margin-left:25px;">  		
			 <li><bean:message name="doborageeditable" scope="request"/></li></ol>
			</td>
   		</tr>
  		</table>
	 </logic:notEmpty>
		<!-- E N D : Success Box --> 
		<br/>
	<fieldset><legend>Configure Field Names</legend>
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="gridWithCheckBox zeroMargin">
          <tr valign="middle"> 
            <td width="30%"  class="gridHeader" style="height:20px;padding-left:5px">Field Name </td> 
            <td width="35%"  class="gridHeader" style="padding-left:5px">Mandatory</td>
			<td width="35%"  class="gridHeader" style="padding-left:5px">Show Field </td>
          </tr>
          
          <logic:empty name="frmWebconfigMemDetails" property="memberdetails">
			<tr>
				<td class="generalcontent" colspan="7" >&nbsp;&nbsp;No Data Found</td>
			</tr>
		</logic:empty>
		<logic:notEmpty name="frmWebconfigMemDetails" property="memberdetails">
	   <logic:iterate id="memberdetails" indexId="i" name="frmWebconfigMemDetails" property="memberdetails">
	      <html:hidden name="memberdetails" property="policyMemFieldTypeID"/>
	      <html:hidden name="memberdetails" property="policySeqID"/>
	      <html:hidden name="memberdetails" property="memberConfigSeqID"/>
   	      <html:hidden name="memberdetails" property="fieldName"/>
	      
	      <tr class="<%=(i.intValue()%2==0)? "gridOddRow":"gridEvenRow"%>">
	      	<td><bean:write name="memberdetails" property="fieldDesc"/>
	      		<html:hidden name="memberdetails" property="fieldDesc"/>
	      	</td>
	      	<td><bean:write name="memberdetails" property="mandatoryYN"/>
	      		<html:hidden name="memberdetails" property="mandatoryYN"/>
	      	</td>
	      	<td align="left" style="padding-left:5px">
	      	
	      	<logic:equal name="memberdetails" property="mandatoryYN" value="Yes">
	      		<%blShowField=true;%>
	      		<html:hidden name="memberdetails" property="fieldStatusGenTypeID"/>
	      	</logic:equal>
	      	<logic:equal name="memberdetails" property="mandatoryYN" value="No">
	      		<%blShowField=false;%>
	      	</logic:equal>

		<logic:empty name="memberdetails" property="memberConfigSeqID">
		  <%
	      	if(strFieldStatusGenTypeID!=null){
	      %>
	      	<html:select name="memberdetails" property="fieldStatusGenTypeID"  styleClass="selectBox selectBoxMedium" disabled="<%= blShowField %>" value="<%=strFieldStatusGenTypeID[i.intValue()]%>">
	      	<html:option value="">Select from list</html:option>
	      	<logic:equal name="memberdetails" property="allowDeselectYN" value="Y">
					<html:options collection="webloginMemConfigAdslY"  property="cacheId" labelProperty="cacheDesc"/>
				</logic:equal>
				<logic:equal name="memberdetails" property="allowDeselectYN" value="N">
					<html:options collection="webloginMemConfigAdslN"  property="cacheId" labelProperty="cacheDesc"/>
				</logic:equal>				
		    </html:select>
	      <%
	      	 } else {
	      %>
	      	<html:select name="memberdetails" property="fieldStatusGenTypeID"  styleClass="selectBox selectBoxMedium" disabled="<%= blShowField %>" >
	      	<html:option value="">Select from list</html:option>
	      	<logic:equal name="memberdetails" property="allowDeselectYN" value="Y">
					<html:options collection="webloginMemConfigAdslY"  property="cacheId" labelProperty="cacheDesc"/>
				</logic:equal>
				<logic:equal name="memberdetails" property="allowDeselectYN" value="N">
					<html:options collection="webloginMemConfigAdslN"  property="cacheId" labelProperty="cacheDesc"/>
				</logic:equal>				
		    </html:select>
	      <% 
	      	}
	      %>
	      	
	      	      	
		</logic:empty>

		<logic:notEmpty name="memberdetails" property="memberConfigSeqID">
	      		<%
	      	if(strFieldStatusGenTypeID!=null){
	      %>
		    <html:select name="memberdetails" property="fieldStatusGenTypeID"  styleClass="selectBox selectBoxMedium" disabled="<%= blShowField %>" value="<%=strFieldStatusGenTypeID[i.intValue()]%>">
		    <logic:equal name="memberdetails" property="allowDeselectYN" value="Y">
					<html:options collection="webloginMemConfigAdslY"  property="cacheId" labelProperty="cacheDesc"/>
				</logic:equal>
				<logic:equal name="memberdetails" property="allowDeselectYN" value="N">
					<html:options collection="webloginMemConfigAdslN"  property="cacheId" labelProperty="cacheDesc"/>
				</logic:equal>
				
		    </html:select> 
	      <%
	      	 } else {
	      %>
	      	<html:select name="memberdetails" property="fieldStatusGenTypeID"  styleClass="selectBox selectBoxMedium" disabled="<%= blShowField %>" >
	      	<logic:equal name="memberdetails" property="allowDeselectYN" value="Y">
					<html:options collection="webloginMemConfigAdslY"  property="cacheId" labelProperty="cacheDesc"/>
				</logic:equal>
				<logic:equal name="memberdetails" property="allowDeselectYN" value="N">
					<html:options collection="webloginMemConfigAdslN"  property="cacheId" labelProperty="cacheDesc"/>
				</logic:equal>
				
		    </html:select>
	      <% 
	      	}
	      %>
	</logic:notEmpty>
	      	
	      
	      		
		    
            </td>
		 </tr>
		 
      </logic:iterate>
      </logic:notEmpty>
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
		
		 <logic:notEmpty  name="frmWebconfigMemDetails" property="memberdetails">
	       	 	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave(document.forms[1])"><u>S</u>ave</button>&nbsp;
	        	<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
	        </logic:notEmpty>
	    <%
			}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		%>	
		
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
			</td>
		</tr>
	</table>
<!-- E N D : Buttons -->
</div>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<input type="hidden" name="child" value="Member Details">
	<html:hidden property="caption"/>
	<html:hidden property="rownum"/>

	
</html:form> 
      <!-- E N D : Content/Form Area -->