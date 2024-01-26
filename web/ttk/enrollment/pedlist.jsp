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
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.ClaimsWebBoardHelper"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/enrollment/pedlist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<html:form action="/AddPEDAction.do">
<%
	if(TTKCommon.getActiveLink(request).equals("Claims"))
	{
		pageContext.setAttribute("AmmendMent_Flow",ClaimsWebBoardHelper.getAmmendmentYN(request));
	}//end of if(TTKCommon.getActiveLink(request).equals("Claims"))
	else
	{
		pageContext.setAttribute("AmmendMent_Flow","N");
	}//end of else
%>
<!-- S T A R T : Page Title -->
  <logic:match name="frmAddPED" property="switchType" value="ENM">
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  </logic:match>
  <logic:match name="frmAddPED" property="switchType" value="END">
    <table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
  </logic:match>

    <tr><!-- Personal Waiting Periods added for koc 1278 -->
        <td width="100%">List of PEDS and Personal Waiting Periods - <bean:write name="frmAddPED" property="caption" /></td>
        <td align="right">&nbsp;&nbsp;&nbsp;</td>
   </tr>
</table>
<!-- E N D : Page Title -->

<!-- S T A R T : Form Fields -->

  <div class="scrollableGrid" style="height:450px">
  <html:errors/>
  <br>
 <!-- S T A R T : Grid -->
  <ttk:HtmlGrid name="PEDTableData" className="gridWithCheckBox zeroMargin"/>
<!--E N D : Grid -->
  </div>
  <table class="buttonsSavetolistGrid" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="right" width="100%" nowrap>
          <%
          if(!("Coding".equals(TTKCommon.getActiveLink(request))||"Code CleanUp".equals(TTKCommon.getActiveLink(request))))
          {
	          if(TTKCommon.isAuthorized(request,"Add"))
	          {
	      %>
	         	<logic:notEqual name="AmmendMent_Flow" value="Y">
		         	<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd()"><u>A</u>dd</button>&nbsp;
	         	</logic:notEqual>
	      <%
	          }
	          if(TTKCommon.isDataFound(request,"PEDTableData") && TTKCommon.isAuthorized(request,"Delete"))
	          {
	      %>
				<logic:notEqual name="AmmendMent_Flow" value="Y">
					<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
			    </logic:notEqual>
          <%
    	      }
            }
          %>
          <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
        </td>
      </tr>
    </table>
<!-- E N D : Form Fields -->
<!-- E N D : Content/Form Area -->
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>