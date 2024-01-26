<%
/**
 * @ (#) assignto.jsp 4th May 2006
 * Project      : TTK HealthCare Services
 * File         : assignto.jsp
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : 4th May 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList,com.ttk.common.PreAuthWebBoardHelper"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/preauth/assignto.js"></script>

<%
	boolean viewmode=true;
	pageContext.setAttribute("officeInfo",Cache.getCacheObject("officeInfo"));
	//pageContext.setAttribute("assignUsers",Cache.getCacheObject("assignUsers"));
	ArrayList alUserList=(ArrayList)request.getSession().getAttribute("alUserList");
	String strLink=TTKCommon.getActiveLink(request);
	String strSubLink=TTKCommon.getActiveSubLink(request);
	pageContext.setAttribute("strLink",strLink);
	pageContext.setAttribute("strSubLink",strSubLink);
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>

<!-- S T A R T : Content/Form Area -->
	<html:form action="/AssignToAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="<%=TTKCommon.checkNull(PreAuthWebBoardHelper.getShowBandYN(request)).equals("Y") ? "pageTitleHilite" :"pageTitle" %>" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="57%">Assign To</td>
	<td width="43%" align="right" class="webBoard">&nbsp;</td>
    </tr>
</table>
	<!-- E N D : Page Title -->
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
 	<!-- E N D : Success Box -->


    <!-- S T A R T : Form Fields -->
	<fieldset>
<legend>General</legend>
<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>

    <logic:match name="strLink" value="Pre-Authorization">
    	<td valign="top" class="formLabel">Cashless Selected:</td>
 	</logic:match>
  <logic:match name="strLink" value="Claims">
  	<td valign="top" class="formLabel">Claim Selected:</td>
  </logic:match>
  <logic:match name="strLink" value="Coding">
  	<logic:match name="strSubLink" value="PreAuth">
  		<td valign="top" class="formLabel">Cashless Selected:</td>
  	</logic:match>
  	<logic:match name="strSubLink" value="Claims">
  		<td valign="top" class="formLabel">Claim Selected:</td>
  	</logic:match>	
 </logic:match>
  <td valign="top" class="textLabelBold"><bean:write name="frmAssign" property="selectedPreAuthNos"/></td>
  </tr>
  <tr>
    <td nowrap>Vidal Health Branch:<span class="mandatorySymbol">*</span><br></td>
    <td><html:select property="officeSeqID" styleClass="selectBox selectBoxMedium" onchange="doSelectUsers()" disabled="<%= viewmode %>">
    	<html:options collection="officeInfo" property="cacheId" labelProperty="cacheDesc"/>
    </html:select>
	</td>
  </tr>
  <tr>
    <td width="18%" class="formLabel">Assigned To:<span class="mandatorySymbol">*</span></td>
    <td><html:select property="doctor" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
    	<html:option value="">Select from list</html:option>
       <html:options collection="alUserList" property="cacheId" labelProperty="cacheDesc"/>
    </html:select> </td>
    </tr>
  <tr>
    <td class="formLabel">Remarks:</td>
    <td class="formLabel">
    <html:textarea  property="remarks" styleClass="textBox textAreaLong" disabled="<%= viewmode %>"/></td>
    </tr>
</table>
</fieldset>

	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <logic:match name="viewmode" value="false">
    <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
 	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
 	</logic:match>
    <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCancel();"><u>C</u>lose</button>
  </tr>
</table>
	<!-- E N D : Buttons -->
	</div>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<logic:notEmpty name="frmAssign" property="frmChanged">
 		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
	<html:hidden property="claimSeqID"/>
	<html:hidden property="preAuthSeqID"/>
	<html:hidden property="policySeqID"/>
	<html:hidden property="selectedPreAuthNos"/>
	<html:hidden property="assignUserSeqID"/>
	</html:form>
	<!-- E N D : Content/Form Area -->