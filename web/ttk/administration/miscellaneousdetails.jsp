<%
/** @ (#) miscellaneousdetails.jsp September 8, 2006
 * Project     : TTK Healthcare Services
 * File        : miscellaneousdetails.jsp
 * Author      : Navin Kumar R
 * Company     : Span Systems Corporation
 * Date Created: September 8, 2006
 *
 * @author 		 : 
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/miscellaneousdetails.js"></script>
<script language="javascript">
function onClose()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].action.value="/MiscellaneousAction.do";
	document.forms[1].submit();
}//end of onClose()

function toUpperCase(value,id,name)
{
	var NumElements=document.forms[1].elements.length;
	for(var i=0;i<NumElements;i++)
	{
	    if(document.forms[1].elements[i].name==name)
		{
		document.forms[1].elements[i].value=value.toUpperCase();
		}
	}//end if  for(int i=0;i<NumElements;i++)
}//end of function toUpperCase()

function onGenerateReport(reportID)
{
	var selectRptType="";
	var fileName ="";
	if(reportID == "MiscePreAuthsandClaims")
	{
		fileName = "reports/enrollment/EnrollPatClmRpt.jrxml";
		selectRptType="PAT_CLM";
	}else if(reportID == "MisceBufferUtil"){
		fileName = "reports/enrollment/EnrollBufferRpt.jrxml";
		selectRptType="BUFFER";
	}else if(reportID == "MisceSIUtil"){
		fileName = "reports/enrollment/EnrollutilisedSumRpt.jrxml";
		selectRptType="UTILISED SUM";
	}//end of else if(selectRptType == "MisceSIUtil")
	else
	{
		fileName = "generalreports/EmptyReprot.jrxml";
	}//end of else
	var partmeter = "?mode=doMisEnrollRpt&fileName="+fileName+"&selectRptType="+selectRptType+"&reportID="+reportID+"&reportType="+document.forms[1].reportType.value+"&enrollmentNumber="+document.forms[1].enrollmentNumber.value;
	var openPage = "/MiscellaneousAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=no,width="+w+",height="+h;
	window.open(openPage,'',features);
	
}//end of onGenerateReport(reportID)

</SCRIPT>

<html:form action="/MiscellaneousAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    	<%-- <%=TTKCommon.getActiveSubLink(request)%>  --%>
    	Schemes Reports -        
    	<bean:write name="frmMiscellaneous" property="reportName"/>
    	<bean:write name="frmMiscellaneous" property="caption"/>   			
    </td>
  </tr>
</table>
<!-- End of: Page Title -->
<div class="contentArea" id="contentArea">
<!-- Start of Reports List -->	  
	<fieldset>
       	<legend>Report Parameters</legend>
       	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			<tr>
           		<td class="formLabel" align="center">Enrollment Number:</td>
           		<td>
           			<html:text property="enrollmentNumber" styleClass="textBox textBoxLong" maxlength="60"/>					            			            		
           		</td>
         	</tr> 
	 	</table>
	</fieldset>     		        
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="center">
        	Report Type
			<select name="reportType" class="selectBox" id="reporttype">
			    <option value="PDF">PDF</option>
		        <option value="EXL">EXCEL</option>
		   </select>
		   &nbsp;
           <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport('<bean:write name="frmMiscellaneous" property="reportID"/>');"><u>G</u>enerate Report</button>&nbsp;          
	       <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>	       
       	</td>
      </tr>
    </table>
<!-- End of Reports List -->
</div>
<html:hidden property="reportID"/>
<html:hidden property="reportName"/>
<html:hidden property="selectRptType" value=""/>
<html:hidden property="parameterValues"/>
<input type="hidden" name="mode" value=""/>
</html:form>