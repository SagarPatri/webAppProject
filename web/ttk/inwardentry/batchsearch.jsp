<%
/** @ (#) batchsearch.jsp 2nd Feb 2006
 * Project     : TTK Healthcare Services
 * File        : batchsearch.jsp
 * Author      : Raghavendra T M
 * Company     : Span Systems Corporation
 * Date Created: 2nd Feb 2006
 *
 * @author 		 : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.security.Cache,com.ttk.common.TTKCommon,java.util.Date,java.text.SimpleDateFormat" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/inwardentry/batchsearch.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<%
	boolean viewmode=true;
	if((TTKCommon.isAuthorized(request,"Add")) || (TTKCommon.isAuthorized(request,"Delete")))
	{
		viewmode=false;
	}
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	pageContext.setAttribute("InsCompanyID",Cache.getCacheObject("insuranceCompany"));
	pageContext.setAttribute("ClarificationStatusID",Cache.getCacheObject("clarificationstatus"));
	pageContext.setAttribute("BatchStatusId",Cache.getCacheObject("batchStatus"));
	pageContext.setAttribute("EntryModeId",Cache.getCacheObject("entryMode"));
	
	java.util.Date dt = new java.util.Date();
	int iDate=0;
	int iMonth=0;
	int iYear=0;    
	java.text.SimpleDateFormat sdt;
	sdt = new java.text.SimpleDateFormat("dd/MM/yyyy");
	String strDate = sdt.format(dt);
	String str[] = strDate.split("/");
	for(int i=0; i<str.length; i++)
	{
		iDate =Integer.parseInt(str[0]);
		iMonth =Integer.parseInt(str[1]);
		iYear =Integer.parseInt(str[2]);
	}
	dt = new Date(iYear-1900,iMonth-1,iDate-5);	
	String strStartDate = sdt.format(dt);
%>

<!-- E N D : Tab Navigation -->
	<!-- S T A R T : Content/Form Area -->
	<html:form action="/BatchlistAction.do" method="post" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
        <td>List of Batch Entries</td>
  </tr>
</table>
	<!-- E N D : Page Title -->
<html:errors/>
	<!-- S T A R T : Search Box -->
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
      <td nowrap>Batch Type:<br>
		    <html:select property="batchType"  styleClass="selectBox selectBoxMedium">
		    	<html:option value="">Any</html:option>
		    	<html:option value="ENM">Enrollment</html:option>
		    	<html:option value="END">Endorsement</html:option>
		    </html:select>
        </td>
      <td nowrap>Batch No.:<br>
            <html:text property="BatchNbr"  styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>
       <td nowrap>Insurance Company:<br>
		    <html:select property="InsuranceSeqID"  styleClass="selectBox selectBoxMedium">
		    	<html:option value="">Any</html:option>
  				<html:options collection="InsCompanyID"  property="cacheId" labelProperty="cacheDesc"/>
		    </html:select>
        </td>
		<td nowrap>Insurance Code:<br>
            <html:text property="OfficeCode"  styleClass="textBox textBoxMedium" maxlength="30"/>
        </td>
		<td nowrap>Start Date:<br>
		<logic:empty name="frmBatchList" property="startDate">
      			<html:text property="startDate" value="<%=strStartDate%>" styleClass="textBox textDate" maxlength="10"/><A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','forms[1].startDate',document.forms[1].startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a></td>
   		</logic:empty>
   		<logic:notEmpty name="frmBatchList" property="startDate">
     			<html:text property="startDate" styleClass="textBox textDate" maxlength="10"/><A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','forms[1].startDate',document.forms[1].startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a></td>
   		</logic:notEmpty>
	<td nowrap>End Date:<br>
	  <html:text property="endDate"  styleClass="textBox textDate" maxlength="10"/><A NAME="calEndDate" ID="calEndDate" HREF="#" onClick="javascript:show_calendar('calEndDate','forms[1].endDate',document.forms[1].endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a></td>
	  <td valign="bottom" nowrap><a href="#" class="search">&nbsp;</a></td>
        <td width="100%">&nbsp;</td>
      </tr>

      <tr>
        <td nowrap>Clarification Status:<br>
	        <html:select property="ClarifyTypeID"  styleClass="selectBox selectBoxMedium" >
  				<html:options collection="ClarificationStatusID"  property="cacheId" labelProperty="cacheDesc"/>
	    	</html:select>
   	 	</td>
        <td nowrap>Batch Status:<br>
	        <html:select property="BatchStatusID"  styleClass="selectBox selectBoxMedium" >
  				<html:options collection="BatchStatusId"  property="cacheId" labelProperty="cacheDesc"/>
	    	</html:select>
		</td>
		<td nowrap>Entry Mode:<br>
			<html:select property="EntryModeID"  styleClass="selectBox selectBoxMedium" >
  				<html:options collection="EntryModeId"  property="cacheId" labelProperty="cacheDesc"/>
	    	</html:select>
        </td>        
        <td nowrap>Policy No.:<br>
            <html:text property="PolicyNbr"  styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>

        <td valign="bottom" nowrap><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
        <td nowrap>&nbsp;</td>
        <td nowrap>&nbsp;</td>
        <td valign="bottom" nowrap>&nbsp;</td>
        <td colspan="2">&nbsp;</td>
      </tr>


     </table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData" />
	<!-- E N D : Grid -->
<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  <tr>
   <!--  <td width="27%"><a href="#"><img src="/ttk/images/First.gif" alt="First Page" width="5" height="8" border="0"></a>&nbsp;<a href="#"><img src="/ttk/images/Prev.gif" alt="Previous Page" width="4" height="8" border="0"></a>&nbsp;&nbsp;Page 5 of 25&nbsp;&nbsp;<a href="#"><img src="/ttk/images/Next.gif" alt="Next Page" width="4" height="8" border="0"></a>&nbsp;<a href="#"><img src="/ttk/images/Last.gif" alt="Last Page" width="5" height="8" border="0"></a> </td> -->
    <td width="73%" nowrap align="right">
    <td width="73%" nowrap align="right">
    <%
    if(TTKCommon.isAuthorized(request,"Add"))
    {
    %>
    <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd()"><u>A</u>dd</button>&nbsp;
     <%
    }//end of if(TTKCommon.isAuthorized(request,"Add"))
     if(TTKCommon.isDataFound(request,"tableData"))
     {
     if(TTKCommon.isAuthorized(request,"Delete"))
     {
    %>
    <button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
    <%}//end of if(TTKCommon.isDataFound(request,"tableData"))
     }//end of if(TTKCommon.isAuthorized(request,"Delete"))
    %>
    </td>
  </tr>
  <ttk:PageLinks name="tableData"/>
</table>
</div>
	<!-- E N D : Buttons and Page Counter -->

	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	</td>
  </tr>
</table>
</html:form>
<!-- E N D : Main Container Table -->