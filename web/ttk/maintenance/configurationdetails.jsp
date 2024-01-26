<%
/** @ (#) configurationdetails.jsp Jul 31 2008
 * Project     		: TTK Healthcare Services
 * File        		: configurationdetails.jsp
 * Author      		: Sendhil Kumar V	
 * Company     		: Span Systems Corporation
 * Date Created		: Jul 31 2008
 *
 * @author 		 	: Sendhil Kumar V
 * Modified by  	:
 * Modified date 	:
 * Reason        	:
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/maintenance/configurationdetails.js"></script>
<%
    boolean viewmode=true;
    if(TTKCommon.isAuthorized(request,"Edit"))
    {
        viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
	pageContext.setAttribute("officeInfo",Cache.getCacheObject("officeInfo"));
%>    	
<html:form action="/ConfigurationDetailsAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>Configuration Information - <bean:write name="frmConfigDetails" property="caption"/></td>     
    		<td width="43%" align="right" class="webBoard">&nbsp;</td>
  		</tr>
	</table>
	<!-- E N D : Page Title --> 
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<div class="scrollableGrid" style="height:190px;">
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
	<!-- E N D : Success Box -->
	 <!-- S T A R T : Form Fields -->
	<br>
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableDataConfigDetails" className="gridWithCheckBox zeroMargin"/>
	<!-- E N D : Grid -->
	</div>
	<br>
	<table class="buttonsSavetolistGrid" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="right" nowrap class="formLabel">
			</td>
		</tr>
	</table>
	<fieldset>
		<legend>Configuration Information</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px;">
			<tr>
        		<td nowrap>Vidal Health Branch: <span class="mandatorySymbol">*</span></td>
				<td>
                <html:select property="officeSeqID" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                     <html:option value="">Any</html:option>
                     <html:options collection="officeInfo" property="cacheId" labelProperty="cacheDesc"/>
                </html:select>
	        	</td>
        	</tr>
			<tr>
          		<td nowrap>Config Param1: <span class="mandatorySymbol">*</span></td>
          		<td nowrap>
          			<html:text property="configParam1"  styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/>
          		</td>
          	</tr>
          	<tr>	
				<td nowrap class="formLabel">Level1 Email ID: <span class="mandatorySymbol">*</span></td>
				<td>
					<html:textarea property="primaryMailID1" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/>
				</td>
			</tr>
			<tr>
          		<td nowrap>Config Param2: </td>
          		<td nowrap>
          			<html:text property="configParam2"  styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/>
          		</td>
          	</tr>
          	<tr>	
				<td nowrap class="formLabel">Level2 Email ID: </td>
				<td>
					<html:textarea property="primaryMailID2" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/>
				</td>
			</tr>
			<tr>
          		<td nowrap>Config Param3: </td>
          		<td nowrap>
          			<html:text property="configParam3"  styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/>
          		</td>
          	</tr>
          	<tr>	
				<td nowrap class="formLabel">Level3 Email ID: </td>
				<td>
					<html:textarea property="primaryMailID3" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/>
				</td>
			</tr>
			<tr> <!-- koc11 koc 11 s -->
          		<td nowrap>Config Param4: </td>
          		<td nowrap>
          			<html:text property="configParam4"  styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/>
          		</td>
          	</tr>
          	<tr>	
				<td nowrap class="formLabel">Level4 Email ID: </td>
				<td>
					<html:textarea property="primaryMailID4" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/>
				</td>
			</tr> <!-- koc11 koc 11 e -->
			<tr>
				<td colspan="7" align="center" nowrap>&nbsp;
				<%
		   		if(TTKCommon.isAuthorized(request,"Edit"))
				{
    	 		%>
					<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave to List</button>&nbsp;
					<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
			    <%
				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
				%>					
					<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>		
				</td>
			</tr>	
		</table>
	</fieldset>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	</div>
</html:form>