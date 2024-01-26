<%
/**
 * @ (#)  annexurechallanrpt.jsp Apr 09, 2010
 * Project      : TTKPROJECT
 * File         : annexurechallanrpt.jsp
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : Apr 09, 2010
 *
 * @author       :  Balakrishna Erram
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 *
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */
 %>
 
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%
pageContext.setAttribute("tdsAckInfo",Cache.getCacheObject("tdsAckInfo"));
%>
<script language="javascript">
function finendyear(year)
{
	var i = 1;
	i = i+ parseInt(year);
 	if(i >0)
	{
		document.forms[1].yearTo.value = i;
  	}//end of if(i >1)
}
</script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/reports/annexurechallanrpt.js"></script>

<!-- S T A R T : Content/Form Area -->	
<html:form action="/ReportsAction.do"> 	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><%=TTKCommon.getActiveSubLink(request)%> Reports - <bean:write name="frmReportList" property="reportName"/></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<fieldset>
	<legend>Report Parameters </legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      		<tr>
      			<td nowrap>Financial Year <span class="mandatorySymbol">*</span></td>   
      			<td nowrap><html:text name="frmReportList" property="year"  styleClass="textBox textBoxSmall" maxlength="4" onblur="javascript:finendyear(document.forms[1].year.value);"/>
           			- <html:text name="frmReportList" property="yearTo" readonly="true" styleClass="textBox textBoxTiny textBoxDisabled" />
            	</td>
            	<td>Quarter </td>
            	<td nowrap> <html:select property="payMode" styleId="select10" styleClass="selectBox selectBoxMedium">
              			<html:optionsCollection name="tdsAckInfo" value="cacheId" label="cacheDesc"/>              
              		</html:select>
              	</td>   		
      		</tr>
    	</table>
    	</fieldset>
	<!-- E N D : Form Fields -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="center">Report Type
			<select name="reportType" class="selectBox" id="reporttype">
				<option value="PDF">PDF</option>
				<option value="EXL">EXCEL</option>
			</select> &nbsp;
		   	&nbsp;
			<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();"><u>G</u>enerate Report</button>&nbsp;
           	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
       	</td>
      </tr>
    </table>
    </div>
<html:hidden property="fileName"/>
<html:hidden property="reportID"/>
<html:hidden property="reportName"/>
<input type="hidden" name="mode" value=""/>	
</html:form>