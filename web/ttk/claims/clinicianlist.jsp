<%@page import="org.apache.struts.action.DynaActionForm"%>
<%
/** @ (#) clinicianlist.jsp June 29,2015
 * Project     : Project-X
 * File        : clinicianlist.jsp
 * Author      : Nagababu K
 * Company     : Vidal Health TPA Pvt. Ltd., 
 * Date Created: June 29,2015
 *
 * @author 		 : Nagababu K
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/claims/clinicianlist.js"></script>

<script>
bAction=false;
var TC_Disabled = true;


</script>

<%

%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ClaimClinicianSearchAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td width="57%">List of Clinicians</td>
    		<td width="43%" align="right" class="webBoard">&nbsp;</td>
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->		
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
      <logic:notEmpty name="providerType" scope="session">
      <td nowrap>Provider Id:<br>        
        <html:text property="sProviderId" styleClass="textBox textBoxMedium"  name="frmPreAuthList"  style="background-color: #f1f1f1;"  />
        </td>
        <td nowrap>Provider Name:<br>        
        <html:text property="sProviderName" styleClass="textBox textBoxLarge"  name="frmPreAuthList"  style="background-color: #f1f1f1;"  />
        </td>
        </logic:notEmpty>
       <logic:empty  name="providerType" scope="session">
          <td nowrap>Provider Id:<br>        
        <html:text property="sProviderId" styleClass="textBox textBoxMedium"  name="frmPreAuthList" readonly="true" style="background-color: #f1f1f1;"  />
        </td>
        <td nowrap>Provider Name:<br>        
        <html:text property="sProviderName" styleClass="textBox textBoxLarge"  name="frmPreAuthList" readonly="true" style="background-color: #f1f1f1;"  />
        </td>
       </logic:empty>
        <td nowrap>Clinician Id:<br>        
        <html:text property="sClinicianId" styleClass="textBox textBoxMedium"  name="frmPreAuthList" />
        </td>
        <td nowrap>Clinician Name:<br>
          <html:text property="sClinicianName" styleClass="textBox textBoxLarge" name="frmPreAuthList"/></td>
		<td valign="bottom" nowrap>
		<span id="searchID">
			<a href="#" accesskey="s" onClick="javascript:clinicianSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
		</span>
			</td>
        <td width="100%">&nbsp;</td>
      </tr>
     </table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid  name="clinicianListData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
     	<tr>
        <td>
     	<ttk:PageLinks  name="clinicianListData"/>
     	</td>
     	</tr>
	</table>
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
 	 <tr>
	    <td width="27%"> </td>
	    <td width="73%" nowrap align="right">
	   <logic:equal value="activityClinicianSearch" name="forwardMode">
	    <button type="button" onclick="closeClinicians();" name="Button1" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"><u>C</u>lose</button>
	    </logic:equal>
	    <logic:notEqual value="activityClinicianSearch" name="forwardMode">
	    <button type="button" onclick="goGeneral();" name="Button1" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"><u>C</u>lose</button>
	    </logic:notEqual>
	    </td>
	    </tr>
	    </table>
	</div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT type="hidden" name="rownum" value="">
	<input type="hidden" name="child" value="">
	<INPUT type="hidden" name="mode" value="">
	<INPUT type="hidden" name="sortId" value="">
	<INPUT type="hidden" name="pageId" value="">
	<INPUT type="hidden" name="tab" value="">
	<INPUT type="hidden" name="reforward" value="">
	
	
	
	<%!String providerType = null; %>
	<%  
	DynaActionForm	frmClaimGeneral=(DynaActionForm)session.getAttribute("frmClaimGeneral");
	providerType = frmClaimGeneral.getString("providerType");%>
	<html:hidden property="sProviderType" name="frmPreAuthList" value="<%=providerType%>" />
	
	
	
	
	
	
</html:form>
<!-- E N D : Content/Form Area -->
