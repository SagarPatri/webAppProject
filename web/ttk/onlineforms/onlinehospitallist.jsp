<%
/** @ (#) onlinehospitallist.jsp
 * Project     : TTK Healthcare Services
 * File        : onlinehospitallist.jsp
 * Author      : Balaji C R B
 * Company     : Span Systems Corporation
 * Date Created: March 11 , 2008
 *
 * @author 	   : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/onlineforms/onlinehospitallist.js"></script>

<%@ page import="com.ttk.common.security.Cache" %>
<%
	pageContext.setAttribute("stateCode",Cache.getCacheObject("stateCode"));
	pageContext.setAttribute("cityCode",Cache.getCacheObject("cityCode"));
%>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/OnlineSelectHospitalAction.do">
	<!-- S T A R T : Page Title -->

	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Select Hospitals</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="5" cellpadding="0">
	  <tr>
    		<td>Hospital Name:<br>
	     		<html:text property="sHospitalName" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="250"/>
	    	</td>
    		<td>State:<br>
		       	   <html:select property="stateCode" styleClass="selectBoxWeblogin selectBoxMediumWeblogin">
	                 <html:option value="">Any</html:option>
	                 <html:options collection="stateCode" property="cacheId" labelProperty="cacheDesc"/>
	              </html:select>
	    	</td>	    	
            <td>City:<br>
 		  			<html:select property="cityCode" styleClass="selectBoxWeblogin selectBoxMediumWeblogin">
	           		 <html:option value="">Any</html:option>
	           		 <html:options collection="cityCode" property="cacheId" labelProperty="cacheDesc"/>
          		    </html:select>
          	</td>
     	  	<td valign="bottom" width="100%" align="left">
     	  			<a href="#" class="search" accesskey="s" onClick="onSearch()" ><img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
		  	</td>
	</tr>
	</table>
	<!-- E N D : Search Box -->

	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="hospitalListData"/>
	<!-- E N D : Grid -->

	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="27%"></td>
	    <td width="73%" align="right">
		    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>
	    </td>
	  </tr>
	   <ttk:PageLinks name="hospitalListData"/>
	</table>
	</div>
	<!-- E N D : Buttons and Page Counter -->

	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<input type="hidden" name="child" value="HospitalList">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
 	<script> ClientReset=false;TC_PageDataChanged=true;</script> <!-- to give warning to user if he trying to change tab -->
	</html:form>
<!-- E N D : Content/Form Area -->