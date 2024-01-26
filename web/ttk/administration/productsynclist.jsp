<%
/** @ (#) productsynclist.jsp Aug 3rd, 2007
 * Project     : TTK Healthcare Services
 * File        : productsynclist.jsp
 * Author      : Raghavendra T M
 * Company     : Span Systems Corporation
 * Date Created: Aug 3rd, 2007
 *
 * @author 		 : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<%
	pageContext.setAttribute("insuranceCompany", Cache.getCacheObject("insuranceCompany"));
	pageContext.setAttribute("productStatusCode", Cache.getCacheObject("productStatusCode"));
	pageContext.setAttribute("policyStatusForProductSync", Cache.getCacheObject("policyStatusForProductSync"));
	String syncCheckedStatus = (String)request.getAttribute("syncCheckedStatus");
	String strProdPolicySeqID = (String)request.getAttribute("strProdPolicySeqID");
%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/productsynclist.js"></script>
<script lang="javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<SCRIPT LANGUAGE="JavaScript">
bAction = false; //to avoid change in web board in product list screen
var TC_Disabled = true;
</SCRIPT>
<script type="text/javascript">
 function generateLetter(){
	 
	var syncCheckedStatus =document.forms[1].syncCheckedStatus.value;
	var strProdPolicySeqID = document.forms[1].strProdPolicySeqID.value;
	
	if(syncCheckedStatus === 'Y')
    { 
	     var parameterValue=strProdPolicySeqID;
	     var parameter = "";
	      
		 parameter = "?mode=generateLetter&reportType=EXL&parameter="+parameterValue+"&fileName=generalreports/MemberInceptionRulesSynchronizationReport.jrxml&reportID=ProdSyncLetter";
	  var openPage = "/MemberInceptionRulesSynchronizationReport.do"+parameter;
	  var w = screen.availWidth - 10;
	  var h = screen.availHeight - 49;
	  var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	  window.open(openPage,'',features);
    }//end of if(statusID == 'APR')
} 
</script>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/ProductListSyncAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
         <td>List of Schemes - [ <bean:write name="frmProductList" property="companyName"/> ] [ <bean:write name="frmProductList" property="productName"/> ]</td>
      </tr>
    </table>
<!-- E N D : Page Title -->
<div class="contentArea" id="contentArea">

<!-- S T A R T : Success Box -->
		<logic:notEmpty name="updated" scope="request">
		   	<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
			    	<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
			    	<bean:message name="updated" scope="request"/>
			    </td>
			 	</tr>
			</table>
		</logic:notEmpty>
		<logic:notEmpty name="updateSyncFlag" scope="request">
		   	<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
			    	<td><img src="/ttk/images/ErrorIcon.gif" alt="Error" width="16" height="16" align="absmiddle">&nbsp;
			    	<bean:message name="updateSyncFlag" scope="request"/>
			    </td>
			 	</tr>
			</table>
		</logic:notEmpty>
		
	<!-- E N D : Success Box -->
<html:errors/>
<!-- S T A R T : Search Box -->
<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td nowrap>Scheme Number:<br>
            <html:text property="sPolicyNbr" styleClass="textBox textBoxMedium" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
		<td nowrap>Corporate Name:<br>
			<html:text property="sCorporateName" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
	    </td>
        <td nowrap>Valid From:<br>
      	<html:text property="sValidFrom"  styleClass="textBox textDate" maxlength="10"/><A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','forms[1].sValidFrom',document.forms[1].sValidFrom.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a></td>
		<td nowrap>Valid To:<br>
	  <html:text property="sValidDate"  styleClass="textBox textDate" maxlength="10"/><A NAME="calEndDate" ID="calEndDate" HREF="#" onClick="javascript:show_calendar('calEndDate','forms[1].sValidDate',document.forms[1].sValidDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a></td>
      <td nowrap>Policy Status:<br>
      <html:select property="policyStatusIDForProduct" styleClass="selectBox selectBoxMedium">
				<html:option value="">All</html:option>
				<html:optionsCollection name="policyStatusForProductSync" label="cacheDesc" value="cacheId" />
			</html:select> 
      </td>
        <td width="100%" valign="bottom">
        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
	</tr>
</table>
<!-- E N D : Search Box -->

<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableDataSync" />
<!-- E N D : Grid -->

<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" nowrap>&nbsp;
	    </td>
	    <td align="right" nowrap>
	    	<%
	    		if(TTKCommon.isDataFound(request,"tableDataSync") && TTKCommon.isAuthorized(request,"Add"))
	    		{
	    	%>
		    		<!-- <button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSynchronize();">Sy<u>n</u>chronize</button>&nbsp; -->
		    		
                    <BUTTON accessKey=g class=buttons onmouseover="this.className='buttons buttonsHover'"  onMouseout="this.className='buttons'" onclick="openPopUpWindow('Synchronized','Pages to be Synchronized')" name=Button1>Sy<u>n</u>chronize</BUTTON>
		    		
		    		<!--denial process-->
<!-- 		    		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onInsurerSynchronize();">In<u>s</u>urer Synchronize</button>&nbsp;
 -->					<!--denial process-->
		    <%
	    		}//end of if(TTKCommon.isDataFound(request,"tableDataSync") && TTKCommon.isAuthorized(request,"Add"))
	     	%>
	    			<button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSynchronizeAll();">Sy<u>n</u>chronizeAll</button>&nbsp;
	    			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
	    </td>
	  </tr>
  		<ttk:PageLinks name="tableDataSync"/>
	</table>
	
	<logic:notEmpty name="pageidindex" scope="session">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="<%=session.getAttribute("pageidindex")%>">

	</logic:notEmpty>
	<logic:empty name="pageidindex" scope="session">
		<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	</logic:empty>
		<% session.removeAttribute("pageidindex");%>
	</div>
<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">

	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="syncCheckedStatus" value="<%=syncCheckedStatus%>">
	<input type="hidden" name="strProdPolicySeqID" value="<%=strProdPolicySeqID%>">
	<script type="text/javascript">
	generateLetter();
</script>
	</html:form>
<!-- E N D : Content/Form Area -->