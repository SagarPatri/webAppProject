<%
/**
 * @ (#) associatedisease.jsp 22nd September 2010
 * Project      : Vidal Health TPA  Services
 * File         : associatedisease.jsp
 * Author       : Manikanta Kumar G G
 * Company      : Span Systems Corporation
 * Date Created : 22nd September 2010
 *
 * @author       : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/associatedisease.js"></script>
<%	
    pageContext.setAttribute("associatedList",Cache.getCacheObject("debitAssoc"));
%>
<html:form action="/ClauseDiseaseAction.do">
<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
 		<tr>
   			<td><bean:write name="frmClauseDisease" property="caption"/></td>   			
 		</tr>
	</table>
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
		<td nowrap>Disease Description:<br>
        <html:text  property="diseaseDesc" styleClass="textBox textBoxMedium"  maxlength="250"  onkeypress="javascript:blockEnterkey(event.srcElement);" /></td>

         <td nowrap>Search In:<br>
            <html:select property="associatedList" styleClass="selectBox selectBoxMedium" >
				 <html:optionsCollection name="associatedList" label="cacheDesc" value="cacheId"/>
			</html:select>
		</td>
		<td width="100%" valign="bottom" nowrap>
        <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
      </tr>
    </table>
    <!-- S T A R T : Grid -->
    <ttk:HtmlGrid name="tableData"/>
    <!-- E N D : Grid -->

    <!-- S T A R T : Buttons and Page Counter -->
    <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		 <tr>
		    <td width="100%" nowrap align="right" colspan="2" >
		    
		    <%
		    if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Edit"))
		     {
		     %>
				<logic:match  property="associatedList" name="frmClauseDisease" value="DBU">
		      		<button type="button" name="Button1" accesskey="A" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAssociateExclude('DBA')"><u>A</u>ssociate</button>&nbsp;
		    	</logic:match>
		    	<logic:match property="associatedList" name="frmClauseDisease" value="DBA">
		     		 <button type="button" name="Button2" accesskey="U" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAssociateExclude('DBU')"><u>U</u>nassociate</button>&nbsp;
		    	</logic:match>
		    <%
		   		 }//end of if(TTKCommon.isDataFound(request,"tableData")&& TTKCommon.isAuthorized(request,"Add"))
		    %>
		    <button type="button" name="Button3" accesskey="C" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>&nbsp;		      
          </td>
	  	</tr>
	  	<tr>
	  <ttk:PageLinks name="tableData"/>
	  </tr>
	</table>
    </div>
    <!-- E N D : Buttons and Page Counter -->
    <INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	</html:form>
