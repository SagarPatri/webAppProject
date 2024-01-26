<%/**
 * @ (#) cardbatchlist.jsp July 24 2008
 * Project       : TTK HealthCare Services
 * File          : cardbatchlist.jsp
 * Author        : Sanjay.G.Nayaka
 * Company       : Span Systems Corporation
 * Date Created  : July 24 2008
 * @author       : Sanjay.G.Nayaka
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import="com.ttk.common.TTKCommon" %>

<script language="javascript" src="/ttk/scripts/support/cardbatchlist.js"></script>
<script LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/CardBatchAction">
<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td><bean:write name="frmCardBatch" property="caption"/> - Card Batch List</td>
		<td align="right" class="webBoard"><%@ include file="/ttk/common/toolbar.jsp" %></td>     
    </tr>
	</table>
	<!-- E N D : Page Title --> 
<div class="contentArea" id="contentArea">

<!-- S T A R T : Search Box -->
<html:errors/>
<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	<tr>
	     <td nowrap>Card Batch No.:<span class="mandatorySymbol">*</span><br>
            <html:text property="batchNbr" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
         </td>
          <td width="100%" valign="bottom">
        	 <a href="#" accesskey="s" onClick="javascript:onSearch()" class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
   </tr>
</table>
<!-- E N D : Search Box -->

<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableData" />
<!-- E N D : Grid -->

<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	      <td width="73%" nowrap>&nbsp;</td>
	      <td align="right" nowrap>
	      <% 
	      		if(TTKCommon.isDataFound(request,"tableData"))
	    		    {
	       %>
	    	   <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateCertificate();"><u>G</u>enerate Certificate</button>&nbsp; 	
	    	<%
	    		  }//end of if(TTKCommon.isDataFound(request,"tableData"))
	    	%>
	              <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
	       </td>
	   </tr>
  		<ttk:PageLinks name="tableData"/>
	</table>
	</div>
<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="fileName">
	<INPUT TYPE="hidden" NAME="reportType">
	<INPUT TYPE="hidden" NAME="reportID">
	<script language="JavaScript">
	<logic:notEmpty name="openreport" scope="request">
			var openPage = "/ReportsAction.do?mode=doDisplayReport&"+"reportType=PDF";
			var w = screen.availWidth - 10;
			var h = screen.availHeight - 99;
			var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			window.open(openPage,'',features);
	</logic:notEmpty>
	</script>
	</html:form>
<!-- E N D : Content/Form Area -->
	