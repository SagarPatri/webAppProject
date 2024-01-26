
<%@ page import="com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/enrollment/occupationCodeList.js"></script>
<script>
bAction=false;
var TC_Disabled = true;
</script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/OccupationCodeListAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	  <tr>
  	   <td>List of Occupation Codes </td>
       <td width="43%" align="right" class="webBoard">&nbsp;</td>
  	 </tr>
	</table>
	<html:errors />
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Occupation Code:<br>
        <html:text property="sOccupationCode" styleClass="textBox textBoxMedium"/>
       &nbsp;&nbsp; </td>    
       <td nowrap>Occupation Code Description:<br>
        <html:text property="sOccupationCodeDesc" styleClass="textBox textBoxMedium"/>
       &nbsp;&nbsp; </td>
        <td valign="bottom" nowrap>
            <a href="#" accesskey="s" onClick="javascript:onSearch()" class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
        <td width="100%">&nbsp;</td>      
      </tr>
    </table>
	<!-- E N D : Search Box -->

	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData" />
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  	  <tr>
  		<td width="27%">&nbsp</td>
    	<td width="73%" nowrap align="right">

	    <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>
    	<ttk:PageLinks name="tableData"/>
	</table>
	</div>
	<!-- E N D : Buttons and Page Counter -->


	<INPUT type="hidden" name="rownum" value="">
    <input type="hidden" name="child" value="">
    <INPUT type="hidden" name="mode" value="">
    <INPUT type="hidden" name="sortId" value="">
    <INPUT type="hidden" name="pageId" value="">
	</html:form>
	<!-- E N D : Content/Form Area -->