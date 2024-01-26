<%
/**
 * @ (#) chequeseries.jsp 9th June 2006
 * Project      : TTK HealthCare Services
 * File         : chequeseries.jsp
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : 9th June 2006
 *
 * @author       :Krupa J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/chequeseries.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>

<%
    boolean viewmode=true;
    if(TTKCommon.isAuthorized(request,"Edit"))
    {
        viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
    pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>

<html:form action="/ChequeSeriesAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td width="57%">Cheque Series</td>
    	<td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
  	</tr>
	</table>
	<!-- E N D : Page Title -->
<div class="scrollableGrid" style="height:350px;">
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
	<br>
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData" className="gridWithCheckBox zeroMargin"/>
	<!-- E N D : Grid -->
</div>
	<!-- S T A R T : Buttons -->
	<br>
	<table class="buttonsSavetolistGrid"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="right" nowrap class="formLabel">
        <%
	    		if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Delete"))
				{
		%>
        <button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
        </td>
     	<%
        		}
        %>
      </tr>
    </table>
	<fieldset>
    <legend>Cheque Series Details</legend>
    	<table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
     	 <tr>
        	<td align="left" nowrap>Starting Number: <span class="mandatorySymbol">*</span> <br>
        	<html:text property="startNo"  styleClass="textBox textBoxMedium" maxlength="10" readonly="<%=viewmode%>"/></td>

        	<td align="left" nowrap>Ending Number: <span class="mandatorySymbol">*</span> <br>
	    	<html:text property="endNo"  styleClass="textBox textBoxMedium" maxlength="10" readonly="<%=viewmode%>"/></td>

        	<td width="100%" valign="bottom" nowrap class="formLabel">
        	<logic:match name="viewmode" value="false">
	        	<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
	  	 		<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
	  	 	</logic:match>	
      		</td>
      	</tr>
   		</table>
	</fieldset>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<html:hidden property="seqID"/>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
</html:form>
