<%@page import="org.apache.struts.action.DynaActionForm"%>
<%
/** @ (#) providerlist.jsp June 29,2015
 * Project     : Project-X
 * File        : providerlist.jsp
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
<script type="text/javascript" src="/ttk/scripts/claims/providerlist.js"></script>

<script>
bAction=false;
var TC_Disabled = true;


</script>

<%! String strproviderId="";
    String strprovidername="";
%>
<% 
   DynaActionForm frmClaimGeneral = (DynaActionForm)session.getAttribute("frmClaimGeneral");
  if(session.getAttribute("frmClaimGeneral")!=null  && session.getAttribute("count")==null)
  {
  strproviderId = frmClaimGeneral.getString("providerId");
  int m = strproviderId.indexOf("(");
  if(m>0)
  {
	  strproviderId =	strproviderId.substring(0, m);
  }
  strprovidername = frmClaimGeneral.getString("providerName");
  }
  else if(session.getAttribute("count")!=null)
  {
	  strprovidername = " ";
	  strproviderId = " ";
  }
  else{
	  session.removeAttribute("frmPreAuthList");
  }
  
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ClaimProviderSearchAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td width="57%">List of Providers</td>
    		<td width="43%" align="right" class="webBoard">&nbsp;</td>
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->		
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
      <logic:notEmpty name="claimno" scope="session">
        <td nowrap>Provider Id:<br>        
        <html:text property="sProviderId" styleClass="textBox textBoxMedium"  name="frmPreAuthList" value="<%=strproviderId%>"/>
        </td>
        <td nowrap>Provider Name:<br>
          <html:text property="sProviderName" styleClass="textBox textBoxLarge"  name="frmPreAuthList" value="<%=strprovidername%>"/></td>
          </logic:notEmpty>
          <logic:empty name="claimno" scope="session">
           <td nowrap>Provider Id:<br>        
        <html:text property="sProviderId" styleClass="textBox textBoxMedium"  name="frmPreAuthList" />
        </td>
        <td nowrap>Provider Name:<br>
          <html:text property="sProviderName" styleClass="textBox textBoxLarge"  name="frmPreAuthList"/></td>
          </logic:empty>
		<td valign="bottom" nowrap>
		   <span id="searchID">
			<a href="#" accesskey="s" onClick="javascript:providerSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
			 </span>
			</td>
         
        <td width="100%">&nbsp;</td>
      </tr>
     </table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid  name="providerListData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
     	<tr>
        <td>
     	<ttk:PageLinks  name="providerListData"/>
     	</td>
     	</tr>
	</table>
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
 	 <tr>
	    <td width="27%"> </td>
	    <td width="73%" nowrap align="right">
	    <button type="button" onclick="closeProviders();" name="Button1" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"><u>C</u>lose</button>
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
</html:form>
<!-- E N D : Content/Form Area -->
