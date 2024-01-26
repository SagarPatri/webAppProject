<%
/** @ (#) debitlist.jsp Sep 11, 2007
 * Project    	 : TTK Healthcare Services
 * File       	 : debitlist.jsp
 * Author     	 : Chandrasekaran J
 * Company    	 : Span Systems Corporation
 * Date Created	 : Sep 11, 2007
 * @author 		 : Chandrasekaran J
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
<%
	pageContext.setAttribute("debitType",Cache.getCacheObject("debitType"));
	pageContext.setAttribute("activeTab",TTKCommon.getActiveTab(request));
	
%>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/debitlist.js"></script>
<html:form action="/DebitNoteAction.do"  method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td width="100%"><bean:write name="frmDebitNoteList" property="caption"/></td>
   </tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors />
	<!-- S T A R T : Search Box -->
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	    <tr>
	    	<td width="15%" valign="top" nowrap>Debit Type: <br>
	    		<logic:match name="activeTab" value="General">
				    <html:select property="sDebitType" styleClass="selectBox selectBoxSmall">
			        	<html:optionsCollection name="debitType" label="cacheDesc" value="cacheId"/>
					</html:select>
				</logic:match>
				<logic:notMatch name="activeTab" value="General">
				    <html:select property="sDebitType" styleClass="selectBox selectBoxSmall" disabled="true">
			        	<html:optionsCollection name="debitType" label="cacheDesc" value="cacheId"/>
					</html:select>
					<html:hidden property="sDebitType"/>
				</logic:notMatch>
			</td>
			<td width="15%" valign="top" nowrap>Debit Note:<br>
			    <html:text property="sDebitNote" styleClass="textBox textBoxSmall" maxlength="60"/>
		    </td>
		    <td width="15%" valign="top" nowrap>From Date :<br>
		    	<html:text property="startdate"  styleClass="textBox textDate" /><A NAME="CalendarObjectStartDate" ID="CalendarObjectStartDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectStartDate','forms[1].startdate',document.forms[1].startdate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
		    </td>
		    <td width="15%" valign="top" nowrap>To Date :<br>
		    	<html:text property="enddate" styleClass="textBox textDate" /><A NAME="CalendarObjectEndDate" ID="CalendarObjectEndDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectEndDate','forms[1].enddate',document.forms[1].enddate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
		    </td>
		    <td width="15%" nowrap><br>
		    	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
			<td width="100%">&nbsp;</td>
	    </tr>
    </table>
     <!-- S T A R T : Grid -->
    <ttk:HtmlGrid name="tableDataDebitNote"/>
    <!-- E N D : Grid -->
    <!-- S T A R T : Buttons and Page Counter -->
 	<!-- S T A R T : Buttons and Page Counter -->
    <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="73%" nowrap>&nbsp;
	    </td>
	    <td align="right" nowrap>
			<logic:match name="activeTab" value="General">
		    	<%
		    		if(TTKCommon.isAuthorized(request,"Add"))
		    		{
		    	%>
		    	<button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAdd()"><u>A</u>dd</button>&nbsp;
		    	<%
		    		}//end of if(TTKCommon.isAuthorized(request,"Add"))
		    		//if(TTKCommon.isDataFound(request,"tableDataDebitNote")&& TTKCommon.isAuthorized(request,"Delete"))
		    		//{
		    	%>
		       	<!-- <button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp; -->
		    	<%
		    		//}//end of if(TTKCommon.isDataFound(request,"tableData")&& TTKCommon.isAuthorized(request,"Delete"))
		    	%>
	    	</logic:match>
	    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    </td>
	  </tr>
  		<ttk:PageLinks name="tableDataDebitNote"/>
	</table>
    
	</div>
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	

</html:form>	
