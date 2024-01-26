<%
/** @ (#) cardbatch.jsp March 07, 2006
 * Project    	 : TTK Healthcare Services
 * File       	 : cardbatch.jsp
 * Author     	 : Bhaskar Sandra
 * Company    	 : Span Systems Corporation
 * Date Created	 : March 07, 2006
 * @author 		 : Bhaskar Sandra
 * Modified by   : Raghavendra T M
 * Modified date : Mar 5, 2006
 * Reason        :
 */
 %>
 <%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/support/cardprinting.js"></SCRIPT>
<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>

<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
		{
			viewmode=false;
		}
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	pageContext.setAttribute("officeinfo",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("enrollType",Cache.getCacheObject("enrollTypeCode"));

%>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/CreateCardBatchAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>Create New Card Batch</td>
        <td width="43%" align="right" class="webBoard">&nbsp;</td>
      </tr>
    </table>
	<!-- E N D : Page Title -->
	<html:errors />
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
	<fieldset>
    <legend>General</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
       <tr>
        <td class="formLabel">Vidal Health Branch: <span class="formLabel"><span class="mandatorySymbol">*</span></span></td>
        <td width="30%" class="textLabel">
        <html:select property="officeSeqID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
			<html:option value="">Select from list</html:option>
			<html:optionsCollection name="officeinfo" label="cacheDesc" value="cacheId" />
		 </html:select>
        </td>
        <td width="21%" class="formLabel">&nbsp;</td>
        <td width="25%" class="textLabelBold">&nbsp;</td>
      </tr>
	   <tr>
        <td height="2" colspan="4" class="formLabel"></td>
        </tr>
	   <tr>
        <td class="formLabel">Enrollment Type: <span class="formLabel"><span class="mandatorySymbol">*</span></span></td>
        <td class="textLabelBold">
         <html:select property="enrolTypeID" styleId="enrollmenttype" styleClass="selectBox selectBoxMedium" onchange="javascript:doSelectEnrollType();" disabled="<%= viewmode %>">
			<html:option value="">Select from list</html:option>
			<html:optionsCollection name="enrollType" label="cacheDesc" value="cacheId" />
		 </html:select>
        </td>
        <td class="formLabel">&nbsp;</td>
        <td class="textLabelBold">&nbsp;</td>
      </tr>
      <logic:notEqual name="frmCreateCardBatch" scope="session" property="enrolTypeID" value="IND">
	  <logic:notEqual name="frmCreateCardBatch" scope="session" property="enrolTypeID" value="">
	  <tr>
        <td class="formLabel">
        <logic:match name="frmCreateCardBatch" scope="session" property="enrolTypeID" value="COR">
			Corporate
	  	</logic:match>
	 	<logic:notMatch name="frmCreateCardBatch" scope="session" property="enrolTypeID" value="COR">
			Group
	  	</logic:notMatch>
		 Name:</td>
        <td class="textLabelBold"><bean:write name="frmCreateCardBatch" property="groupName" /></td>
        <td class="formLabel leftMorePad">Group Id: <span class="mandatorySymbol">*</span></td>
        <td class="textLabelBold"><bean:write name="frmCreateCardBatch" property="groupID" />&nbsp;&nbsp;&nbsp;
        <logic:match name="viewmode" value="false">
        <html:link href="javascript:changeCorporate();"><img src="/ttk/images/EditIcon.gif" alt="Change Corporate" width="16" height="16" border="0" align="absmiddle"></html:link>
       </logic:match>
        </td>
       </tr>
  	</logic:notEqual>
	</logic:notEqual>
	  <tr>
        <td class="formLabel"  width="24%"> Healthcare Company:</td>
        <td class="textLabelBold"><bean:write name="frmCreateCardBatch" property="companyName"/></td>
        <td class="formLabel">Company Code: <span class="mandatorySymbol">*</span></td>
        <td class="textLabelBold"><bean:write property="officeCode" name="frmCreateCardBatch"/>&nbsp;&nbsp;&nbsp;
        <logic:match name="viewmode" value="false">
        <a href="#" onClick="javascript:changeOffice();"><img src="/ttk/images/EditIcon.gif" alt="Change Office" width="16" height="16" border="0" align="absmiddle"></a>
        </logic:match>
        </td>
	  </tr>
      <tr>
        <td height="2" colspan="4" class="formLabel"></td>
        </tr>
      <tr>
        <td class="formLabel">Product /Scheme Name: <span class="formLabel"><span class="mandatorySymbol">*</span></span></td>
        <td class="textLabelBold"><span class="textLabel">
          <html:select property="productSeqID" styleClass="selectBox selectBoxLargest" disabled="<%= viewmode %>">
			<html:option value="">Select from list</html:option>
			<html:optionsCollection  property="alInsProducts" value="cacheId" label="cacheDesc"/>
		 </html:select>
        </span></td>
        <td class="formLabel">&nbsp;</td>
        <td class="textLabelBold">&nbsp;</td>
      </tr>
	  <tr>
        <td height="2" colspan="4" class="formLabel"></td>
        </tr>
      <tr>
        <td class="formLabel">Agent Code:</td>
        <td class="textLabelBold">
        <html:text property="agentCode"  styleClass="textBox textBoxMedium" onkeypress="javascript:blockEnterkey(event.srcElement);"  maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"></html:text></td>
        <td class="formLabel">&nbsp;</td>
        <td class="textLabelBold">&nbsp;</td>
      </tr>
	</table>
	</fieldset>
	<!-- E N D : Form Fields -->
	<!-- S T A R T :  Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <%
     if(TTKCommon.isAuthorized(request,"Edit"))
     {
    %>
    	<button type="button" name="Button" accesskey="t" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCreate();">Crea<u>t</u>e</button>&nbsp;
	<%
     }//end of if(TTKCommon.isAuthorized(request,"Edit"))
	%>
	   <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	</td>
  </tr>
  </div>
</table>
<!-- E N D : Buttons and Page Counter -->
<html:hidden property="groupID"/>
<html:hidden property="groupName"/>
<html:hidden property="companyName"/>
<html:hidden property="officeCode"/>
<html:hidden property="insuranceSeqID"/>
<html:hidden property="groupRegnSeqID"/>
<input type="hidden" name="mode" value="" >
<input type="hidden" name="rownum" value="" >
<input type="hidden" name="focusID" value="">
<input type="hidden" name="child" value="Create Card Batch">
</html:form>
<!-- E N D : Content/Form Area -->