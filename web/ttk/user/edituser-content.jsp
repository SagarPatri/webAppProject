<%
/** @ (#) edituser-content.jsp 25th July 2005
 * Project     : TTK Healthcare Services
 * File        : edituser-content.jsp
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
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.*, com.ttk.common.security.SecurityProfile" %>
<%
    SecurityProfile spx = (SecurityProfile)request.getSession().getAttribute("SecurityProfile");
    
    String strUserType="INT";//hardcoded for display
    //populate the values in edit mode
    String strLabel="";
    String strMode="";
    if(request.getParameter("rownum")!=null && !request.getParameter("rownum").equals(""))
    {
        strLabel="Edit User Details";
        strMode="Edit";
    }//end of if(request.getParameter("rownum")!=null
    else
    {
    	strLabel="Add User Details";
    	strMode="Add";
    }//end of else
        
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
//function to validate the screen
function isValidated()
{
    if( isBlank(document.forms[1].title,"Title")==true || 
        isAlphabetic(document.forms[1].title,"Title")==false ||
        isBlank(document.forms[1].lastName,"Last Name")==true || 
        isAlphabetic(document.forms[1].lastName,"Last Name")==false ||
        isBlank(document.forms[1].emailId,"Email Id")==true || 
        isEmail(document.forms[1].emailId) == false ||
        isBlank(document.forms[1].umassId,"UMASS ID")==true 
        )
        return false;
    else if(document.forms[1].firstName.value != "" && 
          isAlphabetic(document.forms[1].firstName,"First Name")==false
          ) 
          return false;
    else
    	return true;  
}//end of isValidated()

//function to submit the screen
function onUserSubmit()
{
    if(isValidated())
    {
    	document.forms[1].mode.value="UpdateUser";
    	document.forms[1].action="/EditUserAction.do";
    	document.forms[1].submit();
    }//end of if(isValidated())
    
}//end of onUserSubmit()

//function to delete the user
function onDelete()
{
    var msg = confirm("Are you sure you want to delete the user ?");
    if(msg)
    {
	document.forms[1].mode.value="Delete";
	document.forms[1].action = "/UserAction.do";
	document.forms[1].submit();
    }//end of if(msg)
}//end of onDelete()

//function to cancel the screen
function onCancel()
{
    document.forms[1].mode.value="Cancel";
    document.forms[1].action = "/UserAction.do";
    document.forms[1].submit();
}//end of onUserSubmit()

function onReset()
{
    document.forms[1].reset();
}//end of onReset()

</SCRIPT>
<form action="/EditUserAction" name="frmEditUser" METHOD="POST">
   <TABLE WIDTH="100%" BORDER="0" CELLSPACING="5" CELLPADDING="0">
        <%
        	if(!TTKCommon.checkNull((String)request.getAttribute("updated")).equals(""))
        	{
        %>
        <TR>  
	  <TD COLSPAN="4" CLASS="pageheader"><%= TTKCommon.checkNull((String)request.getAttribute("updated")) %> !!!</TD>
	</TR>
	<%
		}
	%>
	<html:errors/>
        <TR> 
          <TD COLSPAN="2" CLASS="pageheader"><%= strLabel %></TD>
          <TD COLSPAN="2" ID="RIGHT" CLASS="smallcontent">Fields marked with an 
            (<B CLASS="required">*</B>) are mandatory fields&nbsp;&nbsp;</TD>
        </TR>
        <TR> 
          <TD COLSPAN="4"><IMG SRC="/ttk/images/pixel.gif"  ALT="Spacer Image" TITLE="Spacer Image" WIDTH="1" HEIGHT="5"></TD>
        </TR>
        <TR> 
          <TD WIDTH="25%" CLASS="generalcontent" ID="right"><LABEL NAME="lblusertype" FOR="usertype">User 
            Type :</LABEL></TD>
          <TD WIDTH="27%" CLASS="label" ID="left"> <LABEL NAME=lblintuser FOR="intuser"> 
            </LABEL> </TD>
          <TD WIDTH="18%" CLASS="generalcontent" ID="right"><LABEL NAME=lbluserid FOR="userid">User 
            ID :</LABEL></TD>
          <TD CLASS="label" ID="left" WIDTH="30%"> <LABEL NAME=lblintuser FOR="intuser"></LABEL> 
          </TD>
        </TR>
        <TR> 
          <TD WIDTH="25%" CLASS="generalcontent" ID="right"><LABEL FOR="title"><B CLASS="required">*</B> 
            Title :</LABEL></TD>
          <TD CLASS="generalcontent" WIDTH="27%"> 
            <input type="text" name="title" size="20" maxlength="250" id="title" value="<bean:write name="frmEditUser" property="title"/>">
          </TD>
          <TD WIDTH="18%" CLASS="generalcontent" ID="right">&nbsp;</TD>
          <TD CLASS="generalcontent" WIDTH="30%">&nbsp;</TD>
        </TR>
        <TR> 
          <TD WIDTH="25%" CLASS="generalcontent" ID="right"><LABEL NAME="lbllastname" FOR="lastname"><B CLASS="required">*</B> 
            Last Name :</LABEL></TD>
          <TD CLASS="generalcontent" WIDTH="27%"> 
            <INPUT MAXLENGTH=60 NAME="lastName" ID="lname" SIZE="20" VALUE="<bean:write name="frmEditUser" property="lastName"/>">
          </TD>
          <TD WIDTH="18%" CLASS="generalcontent" ID="right"><LABEL NAME="lblfirstname" FOR="firstname">First 
            Name :</LABEL></TD>
          <TD CLASS="generalcontent" WIDTH="30%"> 
            <INPUT NAME="firstName" MAXLENGTH=60 ID="fname" SIZE="20" VALUE="<bean:write name="frmEditUser" property="firstName"/>" >
          </TD>
        </TR>
        <TR> 
          <TD WIDTH="25%" CLASS="generalcontent" ID="right"><LABEL NAME="lblemail" FOR="email"><B CLASS="required">*</B> 
            Email ID :</LABEL></TD>
          <TD CLASS="generalcontent" WIDTH="27%"> 
            <INPUT NAME="emailId" SIZE=20 ID="email" VALUE="<bean:write name="frmEditUser" property="emailId"/>" MAXLENGTH="250">
          </TD>
          <TD WIDTH="18%" CLASS="generalcontent" ID="right"> <%    
                //disply the following rows only in case of Internal User
                if(strUserType.equals("INT")){    
            %> <LABEL NAME="lblumassid" FOR="umassid"><b class="required">*</b> 
            UMASS ID :</LABEL> <%  }   %></TD>
          <TD CLASS="generalcontent" WIDTH="30%"> <%    
                //display the following rows only in case of Internal User
                if(strUserType.equals("INT")){    
            %> 
            <INPUT 
            MAXLENGTH=100 NAME="umassId" ID="umassid" SIZE="20" VALUE="<bean:write name="frmEditUser" property="umassId"/>">
            <%  }   %> </TD>
        </TR>
        <TR> 
          <TD WIDTH="25%" CLASS="generalcontent" ID="right"><LABEL FOR="forwardemailid" >Forwarding 
            Email ID : </LABEL></TD>
          <TD WIDTH="27%"> 
            <INPUT MAXLENGTH=250 NAME="forwardingEmailId" SIZE=20 ID="forwardemailid" VALUE="<bean:write name="frmEditUser" property="forwardingEmailId"/>">
          </TD>
          <TD WIDTH="18%" CLASS="generalcontent" ID="right">
          <!--<LABEL FOR="forward">Forward Email : </LABEL>-->
          </TD>
          <TD WIDTH="30%"> 
            
            </TD>
        </TR>
        <!--
        <TR> 
          <TD COLSPAN="4">&nbsp;</TD>
        </TR>
        <TR> 
          <TD COLSPAN="4" ID="greyseparator"><IMG SRC="/ttk/images/pixel.gif"  ALT="Spacer Image" TITLE="Spacer Image" WIDTH="1" HEIGHT="1"></TD>
        </TR>
        -->
        <TR> 
          <TD COLSPAN="4" CLASS="generalcontent" ID="center">
          <!--<A HREF="javascript:onUserSubmit()"><IMG SRC="/ttk/images/Success.gif" WIDTH="14" HEIGHT="14" BORDER="0" ALT="Save" TITLE="Save"></A>&nbsp;<A HREF="javascript:onReset()"><IMG SRC="/ttk/images/reset.gif" WIDTH="60" HEIGHT="19" ALT="Reset" TITLE="Reset" BORDER="0"></A>&nbsp;<A HREF="javascript:onDelete()"><IMG SRC="/ttk/images/delete.gif" WIDTH="60" HEIGHT="19" BORDER="0" ALT="Delete" TITLE="Delete"></A>&nbsp;<A HREF="javascript:onCancel('activeusers')" onClick="return TrackChanges('activeusers')"><IMG SRC="/ttk/images/cancel.gif" WIDTH="60" HEIGHT="19" ALT="Cancel" TITLE="Cancel" BORDER="0"></A>-->
          <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	    <tr>
	      <td width="100%" align="center">
	      <%
		  if(spx.isAuthorized("UserManagement.Users.ActiveUsers.Edit"))
		  {
	      %>
	      <input type="button" name="Button1" value="Save" onClick="javascript:onUserSubmit()" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'">&nbsp;
	      <input type="button" name="Button2" value="Reset" onClick="javascript:onReset()" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'">&nbsp;
	      <%
	      	  }
	      %>
	      <%
	      	  if(strMode.equals("Edit")){
	      %>
		      <%
			  if(spx.isAuthorized("UserManagement.Users.ActiveUsers.Delete"))
			  {
	      	      %>
		      <input type="button" name="Button3" value="Delete" onClick="javascript:onDelete()" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'">&nbsp;
		      <%
			   }
	      	      %>
		      <input type="button" name="Button4" value="Cancel" onClick="javascript:onCancel()" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'">
	      <%
	      	  }
	      	  else
	      	  {
	      %>
	      		<input type="button" name="Button4" value="Close" onClick="javascript:onCancel()" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'">
	      <%
	      	  }
	      %>	  
	      </td>
	    </tr>
	  </table>
          </TD>
        </TR>
      </TABLE>
      <INPUT TYPE="hidden" NAME="rownum" VALUE='<%= TTKCommon.checkNull(request.getParameter("rownum"))%>'>
      <INPUT TYPE="hidden" NAME="mode" VALUE="">
  </form>


