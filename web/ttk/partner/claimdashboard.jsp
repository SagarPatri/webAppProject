<%
/**
 * @ (#)claimdashboard.jsp march 24 ,2014
 * Project      : TTK HealthCare Services
 * File         : claimdashboard.jsp(Partner)
 * Author       : satya Moganti
 * Company      : Rcs Technologies
 * Date Created : march 24 ,2014
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/home.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript">
//kocnewhosp
/* function showMe(obj)
{
	var idVal	=	obj.id;
	//alert(idVal);
	if(idVal=='ttl')
	{
		document.getElementById("ttl_1").style.display="inline";
		document.getElementById("ttl_2").style.display="none";
		document.getElementById("ttl_3").style.display="none";
		document.getElementById("ttl_4").style.display="none";
		document.getElementById("ttl_5").style.display="none";
		document.getElementById("ttl_6").style.display="none";
		document.getElementById("ttl_7").style.display="none";
		document.getElementById("ttl_8").style.display="none";
	}else if(idVal=='inp')
	{
		document.getElementById("ttl_1").style.display="none";
		document.getElementById("ttl_2").style.display="inline";
		document.getElementById("ttl_3").style.display="none";
		document.getElementById("ttl_4").style.display="none";
		document.getElementById("ttl_5").style.display="none";
		document.getElementById("ttl_6").style.display="none";
		document.getElementById("ttl_7").style.display="none";
		document.getElementById("ttl_8").style.display="none";
	}else if(idVal=='app')
	{
		document.getElementById("ttl_1").style.display="none";
		document.getElementById("ttl_2").style.display="none";
		document.getElementById("ttl_3").style.display="inline";
		document.getElementById("ttl_4").style.display="none";
		document.getElementById("ttl_5").style.display="none";
		document.getElementById("ttl_6").style.display="none";
		document.getElementById("ttl_7").style.display="none";
		document.getElementById("ttl_8").style.display="none";
	}else if(idVal=='req')
	{
		document.getElementById("ttl_1").style.display="none";
		document.getElementById("ttl_2").style.display="none";
		document.getElementById("ttl_3").style.display="none";
		document.getElementById("ttl_4").style.display="inline";
		document.getElementById("ttl_5").style.display="none";
		document.getElementById("ttl_6").style.display="none";
		document.getElementById("ttl_7").style.display="none";
		document.getElementById("ttl_8").style.display="none";
	}else if(idVal=='cls')
	{
		document.getElementById("ttl_1").style.display="none";
		document.getElementById("ttl_2").style.display="none";
		document.getElementById("ttl_3").style.display="none";
		document.getElementById("ttl_4").style.display="none";
		document.getElementById("ttl_5").style.display="inline";
		document.getElementById("ttl_6").style.display="none";
		document.getElementById("ttl_7").style.display="none";
		document.getElementById("ttl_8").style.display="none";
	}else if(idVal=='rej')
	{
		document.getElementById("ttl_1").style.display="none";
		document.getElementById("ttl_2").style.display="none";
		document.getElementById("ttl_3").style.display="none";
		document.getElementById("ttl_4").style.display="none";
		document.getElementById("ttl_5").style.display="none";
		document.getElementById("ttl_6").style.display="inline";
		document.getElementById("ttl_7").style.display="none";
		document.getElementById("ttl_8").style.display="none";
	}else if(idVal=='paid'){
		document.getElementById("ttl_1").style.display="none";
		document.getElementById("ttl_2").style.display="none";
		document.getElementById("ttl_3").style.display="none";
		document.getElementById("ttl_4").style.display="none";
		document.getElementById("ttl_5").style.display="none";
		document.getElementById("ttl_6").style.display="none";
		document.getElementById("ttl_7").style.display="inline";
		document.getElementById("ttl_8").style.display="none";
	}else
	{
		document.getElementById("ttl_1").style.display="none";
		document.getElementById("ttl_2").style.display="none";
		document.getElementById("ttl_3").style.display="none";
		document.getElementById("ttl_4").style.display="none";
		document.getElementById("ttl_5").style.display="none";
		document.getElementById("ttl_6").style.display="none";
		document.getElementById("ttl_7").style.display="none";
		document.getElementById("ttl_8").style.display="none";
	}
}
function hideMe(obj){
	document.getElementById("ttl_1").style.display="none";
	document.getElementById("ttl_2").style.display="none";
	document.getElementById("ttl_3").style.display="none";
	document.getElementById("ttl_4").style.display="none";
	document.getElementById("ttl_5").style.display="none";
	document.getElementById("ttl_6").style.display="none";
	document.getElementById("ttl_7").style.display="none";
	document.getElementById("ttl_8").style.display="inline";
} */
</script>
<%
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
%>
<html:form action="/OnlineHomeAction.do" >
<% String preAuthClmObj	=	(String)request.getAttribute("preAuthClm");
%> 
<!-- S T A R T : Content/Form Area -->	
	<!-- S T A R T : Page Title -->

<div class="contentArea" id="contentArea">
<!-- S T A R T : Content/Form Area -->

<!-- <div id="navigateBar">Home > Corporate > Detailed > Claim Details</div> -->
	<h4 class="sub_heading">Partner Claims Dashboard</h4>
		<div id="contentOuterSeparator"></div>
<html:errors/>

	<table align="center" class="searchContainer" border="0" cellspacing="3" cellpadding="3">
	<tr>
		<td colspan="5" align="center">Select Pre-Auth/Claims:<br>
   			<select name="preAuthClm" styleClass="selectBox selectBoxSmall">
       			<option value="PAT" <%if("PAT".equals(preAuthClmObj)) out.print("selected"); %>>--Pre-Auth--</option>
       			<option value="CLM" <%if("CLM".equals(preAuthClmObj)) out.print("selected"); %>>--Claims--</option>
   			</select>
       </td>
   	    </tr>
	<tr>
			 <td  width="20%" nowrap>&nbsp;</td>
          	 <td width="20%" nowrap>From Date:<span class="mandatorySymbol">*</span> <br>
	            <INPUT TYPE="text" NAME="sFromDate" class="textBox textDate" VALUE="" maxlength="10"> <A NAME="CalendarObjectFrmDate" ID="CalendarObjectFrmDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectFrmDate','forms[1].sFromDate',document.forms[1].sFromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="frmDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>
          	
          	 <td  width="20%" nowrap>To Date:<span class="mandatorySymbol">*</span><br>
	            <INPUT TYPE="text" NAME="sToDate" class="textBox textDate" VALUE="" maxlength="10"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].sToDate',document.forms[1].sToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="toDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>
  	    	<td  width="20%" valign="bottom" nowrap>
        	<a href="#" accesskey="s" onClick="javascript:onPartnerDashBoardClaimSearch()" class="search">
	        <img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch
       	 	</a>&nbsp;&nbsp;&nbsp;
       	 	
       	 	<a href="#" accesskey="r" onClick="javascript:onPartnerDashBoardClaimRefresh()" class="search">
	        <img src="/ttk/images/RenewIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>R</u>efresh
       	 	</a>&nbsp;&nbsp;&nbsp;
       	 	<!-- 
       	 	<a href="#" accesskey="s" onClick="javascript:onPartnerDashBoardClaimSearchPrevious()" class="search">
	        <img src="/ttk/images/PrevRecord.gif" alt="Previous" title="Previous" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>P</u>revious
       	 	</a>&nbsp;&nbsp;&nbsp;
       	 	
       	 	<a href="#" accesskey="s" onClick="javascript:onDashBoardClaimSearchNext()" class="search">
	        <img src="/ttk/images/NextRecord.gif" alt="Next" title="Next" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>N</u>ext
       	 	</a>
       	 	 -->
       	 	
    		</td>    
    	
    
    
    	 <td  width="20%" nowrap>&nbsp;</td>
		</tr>
		  </table>
	 <ttk:OnlinePtnrClaimDashBoard/>
   
</div>
<!--E N D : Content/Form Area -->
<!-- E N D : Main Container Table --> 
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="leftlink" VALUE="">
<INPUT TYPE="hidden" NAME="sublink" VALUE="">
<INPUT TYPE="hidden" NAME="tab" VALUE="">
<INPUT TYPE="hidden" NAME="seqID" VALUE="">
<INPUT TYPE="hidden" NAME="fileName" VALUE="">
<script type="text/javascript">
showMe('');
</script>
</html:form>
