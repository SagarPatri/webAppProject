<%/**
 * @ (#) faxstatus.jsp Jan 25, 2008
 * Project       : TTK HealthCare Services
 * File          : faxstatus.jsp
 * Author        : Yogesh S.C
 * Company       : Span Systems Corporation
 * Date Created  : Jan 25, 2008
 * @author       : Yogesh S.C
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
 <%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
 <%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
 <%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
 <%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
  <%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
  <SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>  
 <script language="javascript" src="/ttk/scripts/reports/faxstatus.js"></script>
<%
	pageContext.setAttribute("faxGenUsers", Cache.getCacheObject("faxGenUsers"));
	pageContext.setAttribute("faxStatus", Cache.getCacheObject("faxStatus"));		
%>
<!-- S T A R T : Content/Form Area -->	
	<html:form action="/FaxStatusReportAction.do" > 
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="80%"> Fax Status Report</td>     
  </tr>
</table>
	<!-- E N D : Page Title --> 
	<!-- S T A R T : Search Box -->
	
	<!-- E N D : Search Box -->
    <!-- S T A R T : Grid -->
    &nbsp;&nbsp;
    <fieldset>
    <legend>Report Parameters </legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">      
      <tr>
        <td class="formLabel"> Generated by:<span class="mandatorySymbol">*</span></td>
        <td class="formLabelBold">
          <html:select property="generatedBy" styleClass="selectBox selectBoxMedium">            
            <html:optionsCollection name="faxGenUsers" label="cacheDesc" value="cacheId" />
          </html:select>
        </td>
        <td class="formLabel"> Fax Status: </td>
        <td><html:select property="faxStatus" styleId="Select1" styleClass="selectBox selectBoxMedium">
          <html:option value="">Any</html:option>
         <html:optionsCollection name="faxStatus" label="cacheDesc" value="cacheId" />                    
        </html:select></td>
      </tr>
      <tr>
        <td width="19%" class="formLabel"> Start Date: <span class="mandatorySymbol">*</span></td>
        <td width="31%" class="textLabel">
          <html:text property="startDate" styleClass="textBox textDate" />
          <A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','forms[1].startDate',document.forms[1].startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
        <td width="19%" class="formLabel"> End Date:</td>
        <td width="31%" class="textLabel">
          <html:text property="endDate"  styleClass="textBox textDate" />
          <A NAME="CalendarObjecttxtRecdDate" ID="CalendarObjecttxtRecdDate" HREF="#" onClick="javascript:show_calendar('CalendarObjecttxtRecdDate','forms[1].endDate',document.forms[1].endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
      </tr>
    </table>
    </fieldset>
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="center">
        <!-- <input type="button" name="Button" value="Generate Report" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();" > -->
        <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();"><u>G</u>enerate Report</button>&nbsp;
        <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
        </td>
      </tr>
    </table>    
    <!-- E N D : Grid -->
    <script>    
    var curDate = new Date();
    var yesDate = new Date(curDate.setDate(curDate.getDate()));
    if(curDate.getDate()>9)
    {
    if(curDate.getMonth()>8)
    {            
    document.forms[1].startDate.value = curDate.getDate()+"/"+(curDate.getMonth()+1)+"/"+curDate.getYear();    
    }
    else
    {
    document.forms[1].startDate.value = curDate.getDate()+"/0"+(curDate.getMonth()+1)+"/"+curDate.getYear();
    }
    }
    else
    {
    if(curDate.getMonth()>8)
    {
    document.forms[1].startDate.value = "0"+curDate.getDate()+"/"+(curDate.getMonth()+1)+"/"+curDate.getYear();
    }
    else
    {
    document.forms[1].startDate.value = "0"+curDate.getDate()+"/0"+(curDate.getMonth()+1)+"/"+curDate.getYear();
    }
    }
    </script>
    <input type="hidden" name="mode" value=""/>
    </html:form>
    
