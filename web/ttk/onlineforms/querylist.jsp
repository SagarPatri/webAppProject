<%
/** @ (#) querylist.jsp 20th Oct 2008
 * Project     : TTK Healthcare Services
 * File        : querylist.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 20th Oct 2008
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
<%@ page import=" com.ttk.common.TTKCommon"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/onlineforms/querylist.js"></script>
<%
String Checkopt="";
Checkopt = (String)request.getSession().getAttribute("Checkopt");

%>
<html:form action="/QueryListAction.do" >
	<!-- S T A R T : Page Title -->

	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>Query List<bean:write name="frmQueryList" property="caption"/></td>
			<!--<td align="right"><a href="#" onclick="javascript:onECards()"><img src="/ttk/images/E-Cardbold.gif" alt="E-Card" title="E-Card" width="81" height="17" align="absmiddle" border="0" class="icons"></a></td>-->
			<%if(Checkopt.equalsIgnoreCase("Y"))
		    	{
		    %>
		    	<td align="right"><a href="#" onclick="javascript:onECards()"><img src="/ttk/images/E-Cardbold.gif" alt="E-Card" width="81" height="17" align="absmiddle" border="0" class="icons"></a></td>
		    <%
		    	}%>
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
        		<button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd();"><u>N</u>ew Request</button>&nbsp;
        	<%
  				}//end of if(TTKCommon.isAuthorized(request,"Add"))
  			%>
        </td>
      </tr>
      <ttk:PageLinks name="tableData"/>
    </table>
    </div>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
</html:form>