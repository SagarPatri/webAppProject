<%
/** @ (#) icdlist.jsp June 18, 2015
 * Project     		: Project-X
 * File        		: icdlist.jsp
 * Author      		: Nagababu K
 * Company     		: Vidal Health TPA Pvt. Ltd.
 * Date Created		: June 18, 2015
 *
 * @author 	   		:
 * Modified by   	:
 * Modified date 	:
 * Reason        	:
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.PreAuthWebBoardHelper"%>
<SCRIPT type="text/JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/preauth/icdlist.js"></script>
<%
	pageContext.setAttribute("activeLink",TTKCommon.getActiveLink(request));
%>

<!-- S T A R T : Content/Form Area -->
	<html:form action="/ICDListAction">
	<!-- S T A R T : Page Title -->
	<logic:match name="activeLink" value="Enrollment">
		<logic:match name="frmPolicyList" property="switchType" value="ENM">
		    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	    </logic:match>
	  	<logic:match name="frmPolicyList" property="switchType" value="END">
	    	<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	  	</logic:match>
		  <tr>
		    <td>Associate ICD</td>
		  </tr>
		</table>
	</logic:match>

	<logic:notMatch name="activeLink" value="Enrollment">
		<%
	      	  if("masterlist".equals(request.getParameter("screen")))
	      	  {
	    %>
   		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	      	<td>ICD List&nbsp;-&nbsp;&nbsp;<bean:write name="frmICDList" property="caption"/></td>
	     </tr>
		</table>
	      	<%
	      	  }
	      	  else
	      	  {
	      	%>
	      	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	      	<tr>
		    <td><bean:write name="frmICDList" property="caption"/></td>
		  	</tr>
			</table>
		    <%
	      	  }
		    %>
	</logic:notMatch>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Code:<br><html:text property="sICDCode" styleClass="textBox textBoxMedium" maxlength="10" /></td>
        <td nowrap>Description:<br>
          <html:text property="sICDName" styleClass="textBox textBoxLarge" maxlength="60"/></td>
		<td valign="bottom" nowrap>
			<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
        <td width="100%">&nbsp;</td>
      </tr>
     </table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="icdListData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
 	 <tr>
	    <td width="27%"> </td>
	    <td width="73%" nowrap align="right">
	    	<%
	    		// Added for ICD Code implementation by UNNI V MANA on 14-05-2008
	    		if(!"Maintenance".equals(TTKCommon.getActiveLink(request))|| "masterlist".equals(request.getParameter("screen")))
	    		{
	    		%>
		    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		      <%
	    		}
	    		else
	    		{
	    			if(!"masterlist".equals(request.getParameter("screen")))
	    			{
	    			  if(TTKCommon.isAuthorized(request,"Add"))
		    			{
	    			%>
	    			<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd();"><u>A</u>dd</button>
	    			<%
	    			 }//end of if(TTKCommon.isAuthorized(request,"Add"))
	    			}//end of if(!"masterlist".equals(request.getParameter("screen")))
	    		}//end of else
		      %>
	    </td>
  	</tr>
	<ttk:PageLinks name="icdListData"/>
	</table>
	</div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<input type="hidden" name="child" value="ICD List">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="mastericd" VALUE="">
	<!--  Added for ICD Code screen implementation by UNNI V MANA on 18/05/2008 -->
	<INPUT TYPE="hidden" NAME="screen" VALUE="<%=request.getParameter("screen")%>" >
	<!--  End of Addition -->

	</html:form>
	<!-- E N D : Content/Form Area -->