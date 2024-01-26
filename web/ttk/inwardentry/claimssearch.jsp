<%
/**
 * @ (#) claimssearch.jsp 18th July 2006
 * Project      : TTK HealthCare Services
 * File         : claimssearch.jsp
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : 18th July 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache;"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/inwardentry/claimssearch.js"></script>

<%
	pageContext.setAttribute("claimType", Cache.getCacheObject("claimType"));
	pageContext.setAttribute("inwardStatus", Cache.getCacheObject("inwardStatus"));
	pageContext.setAttribute("officeInfo", Cache.getCacheObject("officeInfo"));
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/InwardClaimsAction.do">

<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>List of Claims</td>
  </tr>
</table>
	<!-- E N D : Page Title -->
	<html:errors/>
	<!-- S T A R T : Search Box -->
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Inward No.:<br>
            <html:text property="sInwardNbr" styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>
		<td nowrap> Claim No.:<br>
          	<html:text property="sClaimNo"  styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>
        <td nowrap>Claim Type:<br>
            <html:select property="sClaimType" styleClass="selectBox selectBoxMedium">
				<html:option value="">Any</html:option>
              	<html:optionsCollection name="claimType" label="cacheDesc" value="cacheId"/>
            </html:select>
        </td>

        <td nowrap>Start Date:<br>
            <html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>&nbsp;
            <A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','frmInwardClaimsList.sStartDate',document.frmInwardClaimsList.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a> </td>

        <td nowrap>End Date:<br>
            <html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>
            <A NAME="calEndDate" ID="calEndDate" HREF="#" onClick="javascript:show_calendar('calEndDate','frmInwardClaimsList.sEndDate',document.frmInwardClaimsList.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a> </td>
        	<td nowrap>
        </td>
        </tr>
      <tr>
        <td nowrap>Enrollment ID:<br>
            <html:text property="sEnrollmentID" styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>

        <td nowrap>Corporate Name:<br>
            <html:text property="sGroupName" styleClass="textBox textBoxMedium" maxlength="120"/>
        </td>
        <td nowrap>Inward Status:<br>
            <html:select property="sInwardStatus" styleClass="selectBox selectBoxMedium">
              <html:optionsCollection name="inwardStatus" label="cacheDesc" value="cacheId"/>
			</html:select>
        </td>
        <td nowrap>Vidal Health Branch:<br>
            <html:select property="sTtkBranch" styleClass="selectBox selectBoxMedium">
              <html:option value="">Any</html:option>
              <html:optionsCollection name="officeInfo" label="cacheDesc" value="cacheId" />
          </html:select>
        </td>
      </tr>
      <tr>
        <td nowrap>Certificate No.:<br>   
           <html:text property="sCertificateNo" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60"/>
        </td>
        <td nowrap>Policy Name :<br>   
           <html:text property="sSchemeName" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60"/>
        </td>
        <td width="100%" valign="bottom" nowrap>
        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
       </tr>
     </table>
<!-- E N D : Search Box -->

<!-- S T A R T : Grid -->
 <ttk:HtmlGrid name="tableData"/>
<!-- E N D : Grid -->

<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td width="27%">&nbsp</td>
    		<td width="73%" nowrap align="right">
    		<%
    			if(TTKCommon.isAuthorized(request,"Add"))
    			{
    		%>
		   		<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd()"><u>A</u>dd</button>&nbsp;
        	<%
    			}//end of if(TTKCommon.isAuthorized(request,"Add"))

     			if(TTKCommon.isDataFound(request,"tableData"))
     			{
     				if(TTKCommon.isAuthorized(request,"Delete"))
     				{
   			 %>
				 <button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
        	<%
        		}//end of if(TTKCommon.isDataFound(request,"tableData"))
     				}//end of if(TTKCommon.isAuthorized(request,"Delete"))
    		%>
        	</td>
       </tr>
       <ttk:PageLinks name="tableData"/>
	</table>
</div>
<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
</html:form>
	<!-- E N D : Content/Form Area -->