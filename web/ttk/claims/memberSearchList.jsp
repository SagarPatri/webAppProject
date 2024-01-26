<%@page import="org.apache.struts.action.DynaActionForm"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/claims/memberlist.js"></script>

<script>
bAction=false;
var TC_Disabled = true;


</script>


<%! String strmemberId="";
    String strprovidername="";
%>

<% 

boolean submissionMode = false;

%>




 <logic:notEqual value="DTR" property="claimSubmissionType"
		name="frmClaimGeneral">
<%

   DynaActionForm frmClaimGeneral = (DynaActionForm)session.getAttribute("frmClaimGeneral");
  if(session.getAttribute("frmClaimGeneral")!=null  && session.getAttribute("count")==null)
  {
	  strmemberId = frmClaimGeneral.getString("memberId");
  int m = strmemberId.indexOf("(");
  if(m>0)
  {
	  strmemberId =	strmemberId.substring(0, m);
  }
  }
  else if(session.getAttribute("count")!=null)
  {
	
	  strmemberId = " ";
  }
  else{
	  session.removeAttribute("frmPreAuthList");
  }
  
%>
</logic:notEqual> 

	 <logic:equal value="DTR" property="claimSubmissionType"
		name="frmClaimGeneral">
		<%
			submissionMode = true;
		%>
	</logic:equal> 





<!-- S T A R T : Content/Form Area -->
<html:form action="/MemberListAction.do">
    <!-- S T A R T : Page Title -->
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="57%">List of Members</td>
            <td width="43%" align="right" class="webBoard">&nbsp;</td>
          </tr>
    </table>
    <!-- E N D : Page Title -->
    <html:errors/>
    <div class="contentArea" id="contentArea">
    <!-- S T A R T : Search Box -->
    <table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
     
      <tr>
      
        <td nowrap>Member Id<br>
        <logic:equal  value="DTR" property="claimSubmissionType" name="frmClaimGeneral">
        <html:text property="sEnrollmentId" styleClass="textBox textBoxMedium textBoxDisabled"  name="frmPreAuthList" readonly="true"/>
        </logic:equal> 
        <logic:notEqual value="DTR" property="claimSubmissionType" name="frmClaimGeneral">
         <html:text property="sEnrollmentId" styleClass="textBox textBoxMedium"  name="frmPreAuthList" />
        </logic:notEqual> 
        </td>

        <td valign="bottom" nowrap>
		    <div id="searchID"/>
            <a  accesskey="s"   onClick="javascript:memberSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
        <td width="100%">&nbsp;</td>       
      
      </tr>
      
     </table>
     <div>
    <!-- E N D : Search Box -->
    <!-- S T A R T : Grid -->
        <ttk:HtmlGrid  name="memberListData"/>
    <!-- E N D : Grid -->
    <!-- S T A R T : Buttons and Page Counter -->
    </div>
    <div>
    <table align="center" class="buttonsContainerGrid" border="0" cellspacing="0" cellpadding="0">
         <tr>
        <td>
         <ttk:PageLinks  name="memberListData"/>
         </td>
         </tr>
    </table>
    </div>

    <table align="center" class="buttonsContainerGrid" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="27%"> </td>
        <td width="73%" nowrap align="right">
        <button type="button" onclick="closeMembers();" name="Button1" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"><u>C</u>lose</button>
        </td>
        </tr>
        </table>

    </div>
    <!-- E N D : Buttons and Page Counter -->
    <INPUT type="hidden" name="rownum" value="">
    <input type="hidden" name="child" value="">
    <INPUT type="hidden" name="mode" value="memberSearch">
    <INPUT type="hidden" name="sortId" value="">
    <INPUT type="hidden" name="pageId" value="">
    <INPUT type="hidden" name="tab" value="">
    <INPUT type="hidden" name="reforward" value="">
</html:form>
<!-- E N D : Content/Form Area -->
