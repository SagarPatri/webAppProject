<%
/** @ (#) user-content.jsp 25th July 2005
 * Project     : TTK Healthcare Services
 * File        : user-content.jsp
 * Author      : Nagaraj D.V
 * Company     : Span Systems Corporation
 * Date Created: 25th July 2005
 *
 * @author Nagaraj D.V
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.common.*, com.ttk.common.security.SecurityProfile" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%

	SecurityProfile spx = (SecurityProfile)request.getSession().getAttribute("SecurityProfile");
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="AddEditUser";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/EditUserAction.do";
   	document.forms[1].submit();
}//end of edit(rownum)

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].mode.value="Users";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/UserAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].mode.value="Users";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/UserAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="Users";
    document.forms[1].action = "/UserAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="Forward";
    document.forms[1].action = "/UserAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="Backward";
    document.forms[1].action = "/UserAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

function onSearch()
{
    document.forms[1].mode.value = "Users";
    document.forms[1].action = "/UserAction.do";
    document.forms[1].submit();
}//end of onSearch()

function onAddEditUser()
{
    document.forms[1].mode.value = "AddEditUser";
    document.forms[1].action = "/EditUserAction.do";
   	document.forms[1].submit();
}//end of onAddEditUser()

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	var msg = confirm("Are you sure you want to delete the selected users ?");
	if(msg)
	{
	    document.forms[1].mode.value = "DeleteList";
	    document.forms[1].action = "/UserAction.do";
	    document.forms[1].submit();
	}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

</SCRIPT>

<!-- S T A R T : Content/Form Area -->
	 <form action="/UserAction" name="frmSearchUser" METHOD="POST">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="57%">List of Healthcare Companies</td>
	    <td width="43%" align="right" class="webBoard">Web Board:&nbsp;
	      <!--<select name="drpStatus" class="selectBox selectBoxMedium webBoardList">
	        <option>ICICI Bank</option>
	        <option>TATA AIG</option>
	        <option>New India Assurance</option>
	        <option>Bajaj Alliance</option>
	        <option>Oriental Healthcare</option>
	        <option>Citibank</option>
	        </select>
	        -->
	        <ttk:SelectBox cacheObject="countryCode" id="" className="webBoardList"/>
	    </td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td nowrap>Last Name:<br>
	      <input name="sLastName" type="text" class="textBox textBoxLarge" value="<bean:write name="frmSearchUser" property="sLastName"/>" ></td>
		<td nowrap>First Name:<br>
		  <input name="sFirstName" type="text" class="textBox textDate" value="<bean:write name="frmSearchUser" property="sFirstName"/>"></td>
	    <td nowrap>Email:<br>
	      <input name="sEmailId" type="text" class="textBox textDate" value="<bean:write name="frmSearchUser" property="sEmailId"/>"></td>
	    <td nowrap>
	    <input name="StartDate" type="text" class="textBox textDate"><A NAME="CalendarObjectStartDate" ID="CalendarObjectStartDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectStartDate','forms[1].StartDate',document.forms[1].StartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
	    </td>
	    <td width="100%" valign="bottom"><a href="#" onClick="javascript:onSearch()" class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;Search</a></td>
	  </tr>
	</table>
	<!-- E N D : Search Box -->

	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid/>

	<!-- E N D : Grid -->

	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td colspan="2" align="right">
	    <input type="button" name="Button" value="Copy to Web Board" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'">&nbsp;
	    <input type="button" name="Button" value="Add" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddEditUser()">&nbsp;
	    <%
	    	if(spx.isAuthorized("UserManagement.Users.ActiveUsers.Delete"))
	    	{
	    %>
	    <input type="button" name="Button2" value="Delete" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()">
	    <%
	    	}
	    %>
	    </td>
	  </tr>

	  <ttk:PageLinks/>

	</table>

	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	</form>

	<!-- E N D : Buttons and Page Counter -->

	<!-- E N D : Content/Form Area -->
