<%
/** @ (#) generateindividual.jsp 23rd April 2010
 * Project       : TTK Healthcare Services
 * File          : generateindividual.jsp
 * Author        : Manikanta Kumar
 * Company       : Span Systems Corporation
 * Date Created	 : 23rd April 2010
 *
 * @author 		 : Manikanta Kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
 <%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 <%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
 <%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
 <%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
 <%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
 <%
   pageContext.setAttribute("tdsbatchQuarter",Cache.getCacheObject("tdsbatchQuarter"));
 %>
<script language="javascript" src="/ttk/scripts/finance/generateindividual.js"></script>
<script language="javascript" src="/ttk/scripts/validation.js"></script>
 <!-- S T A R T : Content/Form Area -->	
<html:form action="/GenerateIndividual.do">
<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
 		<tr>
   			<td>List of Hospitals</td>   			
 		</tr>
	</table>
<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
		<html:errors/>	
		
<!-- S T A R T : Success Box -->
	 	<logic:notEmpty name="updated" scope="request">
	  		<table align="center" class="successContainer"  border="0" cellspacing="0" cellpadding="0">
	   			<tr>
	     			<td>
	     				<img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         			<bean:message name="updated" scope="request"/>
	     			</td>
	   			</tr>
	  		</table>
	 	</logic:notEmpty>
 		<!-- E N D : Success Box -->	
<table align="center" class="searchContainer" cellpadding="0" cellspacing="0">
<tr>
  <td nowrap>Financial Year:<br/>
      <html:text name="frmCertGenInd" property="sFinancialyear"  styleClass="textBox textBoxSmall" maxlength="4" onblur="javascript:finendyear(document.forms[1].sFinancialyear.value);"/>
           			- <html:text property="sFinYearTo" readonly="true" styleClass="textBox textBoxTiny textBoxDisabled" />
 </td>
 <td nowrap>Empanelment No.:<br/>
      <html:text property="sEmpanelmentNo"  styleClass="textBox textBoxMedium" maxlength="60"/></td>
 <td nowrap>Frequency:<span class="mandatorySymbol">*</span><br/>
    <html:select property="tdsbatchQtr"  styleClass="selectBox selectBoxMedium">
        <html:option value="">Select from list</html:option>
        <html:optionsCollection name="tdsbatchQuarter" value="cacheId" label="cacheDesc"/>              
    </html:select>
 </td>
 <td valign="bottom" nowrap align="left" width="100%">
	 <a href="#" accesskey="s" onClick="javascript:onSearch();" class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle" >&nbsp;<u>S</u>earch</a>
 </td>
   
</tr>
</table>
<!-- E N D : Search Box -->
			
<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableData"/>
<!-- E N D : Grid -->
<!-- S T A R T :  Page Counter -->
<TABLE align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
<tr>
	    <td width="73%" nowrap>&nbsp;
	    </td>
	    <td align="right" nowrap>
	    <%
	     		if(TTKCommon.isDataFound(request,"tableData"))
		    	{
	    	%>
	    <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerate();"><u>G</u>enerate</button>&nbsp;
	    <%
        		}
        	%>
	   	 <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
	  </td>  
	 </tr>
	 <ttk:PageLinks name="tableData"/>
</TABLE>
</div>
<input type="hidden" name="rownum" value="">
<input type="hidden" name="mode" value="">
<input type="hidden" name="sortId" value="">
<input type="hidden" name="pageId" value="">
</html:form> 
<!-- E N D : Content/Form Area -->	