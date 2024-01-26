<%
/** @ (#) preauthintimation.jsp 11th Mar 2008
 * Project     : TTK Healthcare Services
 * File        : preauthintimation.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 11th Mar 2008
 *
 * @author 		 : Chandrasekaran J
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
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.dto.usermanagement.UserSecurityProfile" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/onlineforms/preauthintimation.js"></script>
<%
	 pageContext.setAttribute("ActiveSubLink",TTKCommon.getActiveSubLink(request));
	 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	 String Checkopt="";
	 Checkopt = (String)request.getSession().getAttribute("Checkopt");

%>
<html:form action="/PreAuthIntimationAction.do" >
	<!-- S T A R T : Page Title -->

	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<logic:equal name="ActiveSubLink" value="Pre-Auth">
    			<td>Cashless Intimation<bean:write name="frmPreAuthIntimation" property="caption"/></td>
    		</logic:equal>
    		<logic:equal name="ActiveSubLink" value="Pre-Auth Intimation">
    			<td>Cashless Intimation<bean:write name="frmPreAuthIntimation" property="caption"/></td>
    		</logic:equal>
    		<logic:equal name="ActiveSubLink" value="Claims">
    			<td>Claims Intimation<bean:write name="frmPreAuthIntimation" property="caption"/></td>
    		</logic:equal>
    		<logic:equal name="ActiveSubLink" value="Claims Intimation">
    			<td>Claims Intimation<bean:write name="frmPreAuthIntimation" property="caption"/></td>
    		</logic:equal>
    		<%
		    if(userSecurityProfile.getLoginType().equals("E")||userSecurityProfile.getLoginType().equals("I"))
		    {
			if(Checkopt.equalsIgnoreCase("Y"))
		    	{
		    %>
    			<td align="right"><a href="#" onclick="javascript:onECards()"><img src="/ttk/images/E-Cardbold.gif" alt="E-Card" title="E-Card" width="81" height="17" align="absmiddle" border="0" class="icons"></a></td>
    		<%
			}
		    }
		    %>
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="27%"></td>
        <td width="73%" nowrap align="right">
        	<%
	    		if(TTKCommon.isAuthorized(request,"Add"))
	    		{
	    	%>
        		<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd();"><u>A</u>dd</button>&nbsp;
        	<%
	    		}// end of if(TTKCommon.isAuthorized(request,"Add"))
	    	%>
        	<%
        		if(TTKCommon.isAuthorized(request,"Delete"))
				{
		    		if(TTKCommon.isDataFound(request,"tableData"))
		    		{
	    	%>
	    				<button type="button" id="deleteButton" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>&nbsp;
        	<%
	    			}// end of if(TTKCommon.isDataFound(request,"tableDataCirculars"))
	    		}// end of if(TTKCommon.isAuthorized(request,"Delete"))
	    	%>
	    	<logic:equal name="ActiveSubLink" value="Pre-Auth">
	    		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    	</logic:equal>
	    	<logic:equal name="ActiveSubLink" value="Claims">
	    		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    	</logic:equal>
        </td>
      </tr>
      <ttk:PageLinks name="tableData"/>
    </table>
    </div>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<script language="javascript">
	hideDeleteButton();
	</script>
</html:form>