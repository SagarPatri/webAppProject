<%
/**
 * @ (#) bonuslist.jsp Feb 10, 2006
 * Project      : TTK HealthCare Services
 * File         : bonuslist.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : Feb 10, 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/enrollment/bonuslist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
%>
<html:form action="/BonusAction.do">

	<!-- S T A R T : Page Title -->
	<logic:match name="frmPolicyList" property="switchType" value="ENM">
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	<logic:match name="frmPolicyList" property="switchType" value="END">
		<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
 	 <tr>
	    <td width="86%">Premium Information - <bean:write name="frmSumInsured" property="caption"/></td>
		<td width="14%" align="right" class="webBoard">&nbsp;</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors/>
   	<!-- S T A R T : Form Fields -->
	<br><div class="scrollableGrid" style="height:382px;">
	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="bonusListData" className="gridWithCheckBox zeroMargin"/>
	<!--E N D : Grid -->
	</div>
	<div class="scrollableGridCalc">
	<table class="gridTotal" cellspacing="0" cellpadding="0" style="width:95%;">
      <tr>
        <td nowrap width="90%" align="right">Cumulative Bonus :</td>
		<td nowrap width="10%" align="right">
			<html:text property="cumulativeBonus" styleClass="textBoxDisabled textBoxSmall" onfocus="this.blur()"/>
		</td>
      </tr>
    </table>
	</div>
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="27%">&nbsp;</td>
        <td width="73%" align="right">
        <%
        	if(TTKCommon.isAuthorized(request,"Add"))
	    	{
	    %>
		    <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd()"><u>A</u>dd</button>&nbsp;
        <%
        	}//end of if(TTKCommon.isDataFound(request,"policyListData")&& TTKCommon.isAuthorized(request,"Add"))
        	if(TTKCommon.isDataFound(request,"bonusListData") && TTKCommon.isAuthorized(request,"Delete"))
	    	  {
		    %>
				<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
    	    <%
    	       }//end of if(TTKCommon.isDataFound(request,"bonusListData") && TTKCommon.isAuthorized(request,"Delete"))
    	    %>
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>&nbsp;
        </td>
      </tr>
    </table>
	<!-- E N D : Buttons and Page Counter -->
<!-- E N D : Form Fields -->
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="rownum" VALUE="">
<input type="hidden" name="child" value="Bonus">
</html:form>

