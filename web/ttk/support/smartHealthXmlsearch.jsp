<%
/** @ (#) smartHealthXmlsearch.jsp Nov 14, 2016
 * Project    	 : Project X
 * File       	 : smartHealthXmlsearch.jsp
 * Author     	 : Nagababu K
 * Company    	 : RCS
 * Date Created	 : Nov 14, 2016
 * @author 		 : Nagababu K
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/support/smartHealthXmlsearch.js"></script>
<script>var TC_Disabled = true;</script>
<%
	String strLink=TTKCommon.getActiveLink(request);
	boolean viewmode=false;	 
	
%>
	<!-- S T A R T : Content/Form Area -->
	<html:form action="/SmartHealthXmlAction.do" >
	<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>List of Xml Files</td>
	<td width="43%" align="right" class="webBoard">&nbsp;</td>
  </tr>
</table>
  <html:errors/>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      
	<tr>
	<td nowrap>Download Status:<br>
            <html:select property="downloadedYN" styleClass="selectBox selectBoxMedium">
		       	<html:option value="N">NO</html:option>	 
		  		<html:option value="Y">YES</html:option>
		  		<html:option value="">All</html:option>
			</html:select>
		</td>
		<td valign="bottom" nowrap>
		<table>
		<tr>
		<td valign="bottom" nowrap>
		Xml Received Date From:<br>
            <html:text property="xmlFilesRecievedFrom" styleClass="textBox textDate"  maxlength="10" />
            <A NAME="CalendarObjectReceivedAfter" ID="CalendarObjectReceivedAfter" HREF="#" onClick="javascript:show_calendar('CalendarObjectReceivedAfter','forms[1].xmlFilesRecievedFrom',document.forms[1].xmlFilesRecievedFrom.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="xmlFilesRecievedFrom" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
		</td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td valign="bottom" nowrap>
		Xml Received To:<br>
            <html:text property="xmlFilesRecievedTo" styleClass="textBox textDate"  maxlength="10" />
            <A NAME="CalendarObjectRespondedDate" ID="CalendarObjectRespondedDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectRespondedDate','forms[1].xmlFilesRecievedTo',document.forms[1].xmlFilesRecievedTo.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="xmlFilesRecievedTo" width="24" height="17" border="0" align="absmiddle"></a>
     
		</td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td valign="bottom" nowrap>
		Provider ID:<br>
			<html:text property="providerId" styleClass="textBox textBoxLarge"/>		</td>
		</tr>
		</table>
		</td>			    
    </tr> 
    <tr>
    <td valign="bottom" nowrap>File Name:<br>
            <html:text property="fileName" styleClass="textBox textBoxLarge"/>
        </td>
        <td valign="bottom" nowrap>File ID:<br>
            <html:text property="fileID" styleClass="textBox textBoxLargest"/>
        </td>
    </tr>
    
    <tr>
		<td valign="bottom" nowrap>
		DHPO Transaction Date From:<br>
            <html:text property="DHPOTransactionDateFrom" styleClass="textBox textDate"  maxlength="10" />
            <A NAME="CalendarObjectTransactionDateFrom" ID="CalendarObjectTransactionDateFrom" HREF="#" onClick="javascript:show_calendar('CalendarObjectTransactionDateFrom','forms[1].DHPOTransactionDateFrom',document.forms[1].DHPOTransactionDateFrom.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="DHPOTransactionDateFrom" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
		</td>
		<td valign="bottom" nowrap>
		DHPO Transaction Date To:<br>
            <html:text property="DHPOTransactionDateTo" styleClass="textBox textDate"  maxlength="10" />
            <A NAME="CalendarObjectTransactionDateTo" ID="CalendarObjectTransactionDateTo" HREF="#" onClick="javascript:show_calendar('CalendarObjectTransactionDateTo','forms[1].DHPOTransactionDateTo',document.forms[1].DHPOTransactionDateTo.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="DHPOTransactionDateTo" width="24" height="17" border="0" align="absmiddle"></a>
     
     &nbsp;&nbsp;&nbsp;
     	    <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
     
		</td>
		</tr>
		
    </table>
    	
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	
     
      <tr>
        <td width="27%"><a href="#"></td>
        <td width="73%" align="right"><%
        	if(TTKCommon.isDataFound(request,"tableData")){
	    	%>
	    	 <input id="download" type="button" value="Download" onclick="onDownloadAll();">
	    	 <%} %>
		</td>
		</tr>
   <ttk:PageLinks name="tableData" />
    </table>
    </div>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="downloadMode" VALUE="">
<!-- E N D : Main Container Table -->
</html:form>