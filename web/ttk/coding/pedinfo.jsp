<%
/** @ (#) pedlist.jsp Feb 6, 2006
 * Project     : TTK Healthcare Services
 * File        : pedlist.jsp
 * Author      : Pradeep R
 * Company     : Span Systems Corporation
 * Date Created: Feb 6, 2006
 *
 * @author 		 : Pradeep R
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/coding/pedinfo.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<html:form action="/AddCodePEDAction.do">

<!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%">List of PEDS - <bean:write name="frmAddPED" property="caption" /></td>
        <td align="right">&nbsp;&nbsp;&nbsp;</td>
   </tr>
</table>
<!-- E N D : Page Title -->
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
<div class="contentArea" id="contentArea">
	<div class="scrollableGrid" style="height:320px">
  <html:errors/>
  <br>
 <!-- S T A R T : Grid -->
  <ttk:HtmlGrid name="PEDTableData" className="gridWithCheckBox zeroMargin"/>
<!--E N D : Grid -->
  </div>
  <fieldset>
        <legend>ICD Coding</legend>
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="formContainer" style="padding:0px;margin:0px;">
          <tr>
             <td width="15%" height="25" nowrap class="formLabel">&nbsp;&nbsp;ICD Code:</td>
            <td width="9%"  style="border-right:1px solid #cccccc;">
				<html:text property="ICDCode" style="width:60px;" styleId="ICDCode" styleClass="textBox textBoxMedium" maxlength="10" onchange="javascript:getDescription()" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);"/>
			</td> 
            <td width="3%" align="center" ><a href="#" class="search" ><img src="/ttk/images/ICDIcon.gif" alt="ICDCode" width="16" height="16" border="0" onClick="javascript:onICDIconClick()"></a></td>
            <td width="72%" align="left" ></td>            
          </tr>
          <tr>
            <td height="10" valign="top" nowrap >&nbsp;</td>
            <td colspan="4" class="textLabelBold">&nbsp;</td>
          </tr>
          <tr>
            <td height="20" valign="top" nowrap class="formLabel">&nbsp;&nbsp;Description:</td>
            <td colspan="4" class="textLabel">
			<bean:write name="frmAddPED" property="description"/>
			</td>
          </tr>          
        </table>
        </fieldset>        
  <table>
      <tr>
      <%
        if("Coding".equals(TTKCommon.getActiveLink(request))){
      %>
      <logic:notEmpty name="frmAddPED" property="seqID">
        <td align="right" width="100%" nowrap>
          
        <%
        	if(TTKCommon.isAuthorized(request,"Edit"))
        	{
    	%>
          <button type="button" name="Button2" accesskey="S" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
          <button type="button" name="Button2" accesskey="R" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
        <%
			}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	    %>  
       </logic:notEmpty>
       <%
       }else {
       %>
         <logic:match name="frmAddPED" property="editYN" value="Y">
          <td align="right" width="100%" nowrap>
          <%
        	if(TTKCommon.isAuthorized(request,"Edit"))
        	{
    	  %>
          <button type="button" name="Button2" accesskey="S" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
          <button type="button" name="Button2" accesskey="R" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
          <%
			}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		  %>
          </logic:match>
        <%}%>  
          <button type="button" name="Button" accesskey="C" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
        </td>
      </tr>
    </table>
     </div>
<!-- E N D : Form Fields -->
<!-- E N D : Content/Form Area -->
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="memberSeqId" VALUE="">
<html:hidden property='seqID' name='frmAddPED'/>
<html:hidden property='PEDType' name='frmAddPED'/>
<html:hidden property='description' name='frmAddPED'/>
<%--<INPUT TYPE="hidden" NAME="leftlink" VALUE="<%=TTKCommon.getActiveLink(request)%>">
<INPUT TYPE="hidden" NAME="tab" VALUE="<%=TTKCommon.getActiveTab(request)%>">
--%><html:hidden property='leftlink' name='frmAddPED' value="<%=TTKCommon.getActiveLink(request)%>"/>
<html:hidden property='tab' name='frmAddPED' value="<%=TTKCommon.getActiveTab(request)%>" />
</html:form>

