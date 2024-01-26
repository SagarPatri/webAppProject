<%
/**
 * @ (#) bankaccountlist.jsp 10th June 2006
 * Project      : TTK HealthCare Services
 * File         : bankaccountlist.jsp
 * Author       : Harsha Vardhan B N
 * Company      : Span Systems Corporation
 * Date Created : 10th June 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>

<%
	String strSubLink=TTKCommon.getActiveSubLink(request);
	pageContext.setAttribute("strSubLink",strSubLink);
	pageContext.setAttribute("acctStatus",Cache.getCacheObject("acctStatus"));
	pageContext.setAttribute("officeType",Cache.getCacheObject("officeType"));
	pageContext.setAttribute("officeInfo",Cache.getCacheObject("officeInfo"));
%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/finance/bankaccountlist.js"></SCRIPT>
<script>
bAction=false;
var TC_Disabled = true;
</script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/BankAccountSearchAction.do" method="post">
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <logic:match name="strSubLink"  value="Bank Account">
	      <td>List of Bank Accounts</td>
	    </logic:match>
	  	<logic:match name="strSubLink"  value="Float Account">
	      <td>Select Bank Account <bean:write name="frmSearchBankAccount" property="caption"/></td>
	    </logic:match>
	    <logic:match name="strSubLink"  value="TDS">
	    	      <td>Select Bank Account</td>
	    </logic:match>
	    <logic:match name="strSubLink"  value="Bank Account">
	      <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	    </logic:match>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
  <div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td nowrap>Account&nbsp;No.:<br>
    	  		<html:text property="sAccountNumber" styleClass="textBox textBoxMedium"  maxlength="60"/>
      		</td>
	  		<td nowrap>Account&nbsp;Name:<br>
	     		<html:text property="sAccountName" styleClass="textBox textBoxMedium"  maxlength="60"/>
	    	</td>
	    	<td nowrap>Status:<br>
            <html:select property="sStatus" styleClass="selectBox selectBoxMedium">
            <html:option value="">Any</html:option>
            <html:optionsCollection name="acctStatus" label="cacheDesc" value="cacheId"/>
            </html:select>
         	</td>
        </tr>
        <tr>
			<td nowrap>Bank&nbsp;Name:<br>
    	  		<html:text property="sBankName" styleClass="textBox textBoxMedium"  maxlength="60"/>
      		</td>
      		<td valign="bottom" nowrap>Office Type:<br>
	        	<html:select property="sOfficeType" styleClass="selectBox selectBoxMedium" >
				<html:optionsCollection name="officeType" label="cacheDesc" value="cacheId"/>
				</html:select>
	        </td>
	  		<td valign="bottom" nowrap>Branch Location:<br>
	        	<html:select property="sTtkBranch" styleClass="selectBox selectBoxMedium" >
				<html:option value="">Any</html:option>
				<html:optionsCollection name="officeInfo" label="cacheDesc" value="cacheId"/>
				</html:select>
	        </td>
	        <td width="100%" valign="bottom"><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
  		</tr>
	</table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
    <ttk:HtmlGrid name="tableData"/>

	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		 <tr>
		    <td width="100%" nowrap align="right" colspan="2" >
		      <logic:match name="strSubLink"  value="Bank Account">
		    <%
			   if(TTKCommon.isDataFound(request,"tableData"))
			   {
		    %>
			    <button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard()"><u>C</u>opy to Web Board</button>&nbsp;
			<%
				}//end of if(TTKCommon.isDataFound(request,"tableData"))
				if(TTKCommon.isAuthorized(request,"Add"))
				{
			%>
				<button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAddBankAccount()"><u>A</u>dd</button>&nbsp;
   			<%
	   			}//end of if(TTKCommon.isAuthorized(request,"Add"))
	   			if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Delete"))
	   			{
			%>
        		<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
	 		<%
    			}//end of if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Delete"))
    		%>
    		  </logic:match>
    		  <logic:match name="strSubLink"  value="Float Account">
				<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onCloseList()"><u>C</u>lose</button>
		  </logic:match>
		  <logic:match name="strSubLink"  value="TDS">
		  				<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onCloseList()"><u>C</u>lose</button>
		  </logic:match>
	    	</td>
	  	</tr>
	  <ttk:PageLinks name="tableData"/>
	</table>
  </div>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	
</html:form>
<!-- E N D : Main Container Table -->