<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<head>
<style type="text/css">

table#textTable{
margin-left: 20px;
width: 500px;
display: inline;
}
table#textTable,table#textTable tr td{
border: 1px solid white;
background-color: #F0F0F0;
border-collapse: collapse;
}

table#textTable tr td{
text-align:left;
font-size: 18px;
padding: 20px;
}
#hexagonLinks{
display: inline;
float: right;
list-style: none;
}
#onlineTableTD{
text-align: center;
font-size: 14px;
}
#onlineSearchEnterTable{
border: none;
padding: 0;
}
</style>
<script type="text/javascript" src="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/onlineforms/brokerLogin/corporateSearch.js"></script>
</head>
<html:form action="/BrokerCorporateSummaryAction.do" >
<html:errors/>
<div id="sideHeading">Corporate Search Page</div>
<br><br>
<div style="border: 1px solid gray;border-radius: 20px;padding: 10px 20px 10px 20px;background-color: #F8F8F8;width: 60%;">
	<table id="onlineSearchEnterTable">
	 <tr>
	  <td id="onlineTableTD">Corporate Name</td>
	  <td id="onlineTableTD">Policy Number</td>
	  <td id="onlineTableTD">Policy Year</td>
	  <td></td>
	 </tr>
	 <tr>
	  <td><html:text property="corporateName" name="frmBroDashBoard" styleClass="textBox textBoxMedLarge"/></td>
	  <td><html:text property="policyNumber" name="frmBroDashBoard" styleClass="textBox textBoxMedLarge"/></td>
	  <td>
	  <html:select property="policyYear" name="frmBroDashBoard" styleClass="selectBox selectBoxMediumWeblogin">
	   <html:option value="">Any</html:option>
	   <html:option value="2010">2010</html:option>
	   <html:option value="2011">2011</html:option>
	   <html:option value="2012">2012</html:option>
	   <html:option value="2013">2013</html:option>
	   <html:option value="2014">2014</html:option>
	   <html:option value="2015">2015</html:option>
	   <html:option value="2016">2016</html:option>
	   <html:option value="2017">2017</html:option>
	   <html:option value="2018">2018</html:option>
	   <html:option value="2019">2019</html:option>
	   <html:option value="2020">2020</html:option>
	  </html:select>	  
	  </td>
	  <td>
	  <table>
	  <tr>
	  <td><img title="Proceed" src="/ttk/images/broker/small_hexagon.png" height="18" width="16"></td>
	  <td id="onlineTableTD"><a title="Proceed" id="imgAncSmall" href="#" onclick="javascript:onSearch();">Proceed</a></td>
	  </tr>
	  </table>
	 </tr>	
	</table>
</div>
<br>
	<table align="center">
	<tr>
	<td><img title="Back" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td><a id="imgAncMedium" onclick="onBack('Home');" href="#" title="Back">Back</a></td>
	</tr>
	</table>
<br>
<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table  align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
     	<tr>
     	<td><ttk:PageLinks name="tableData"/></td>
     	</tr>     	
	</table>
<input type="hidden" name="mode" value="">
<input type="hidden" name="rownum" value="">
<input type="hidden" name="sortId" value="">
<input type="hidden" name="pageId" value="">
<input type="hidden" name="child" value="">
<input type="hidden" name="backID" value="">
</html:form>
