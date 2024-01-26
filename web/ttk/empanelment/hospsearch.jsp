<%
/**
 * @ (#) hospsearch.jsp 12th Sep 2005
 * Project      : TTK HealthCare Services
 * File         : hospsearch.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : 12th Sep 2005
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
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/empanelment/hospsearch.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript">
bAction = false;
var TC_Disabled = true;
</SCRIPT>
<%
	pageContext.setAttribute("subStatus",Cache.getCacheObject("subStatus"));
	pageContext.setAttribute("empanelStatusCode",Cache.getCacheObject("empanelStatusCode"));
	pageContext.setAttribute("stateCode",Cache.getCacheObject("stateCode"));
	pageContext.setAttribute("cityCode",Cache.getCacheObject("cityCode"));
	pageContext.setAttribute("officeInfo",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("gradeCode",Cache.getCacheObject("gradeCode"));
	pageContext.setAttribute("countryCode", Cache.getCacheObject("countryCode"));//added for intX
	pageContext.setAttribute("providerType",Cache.getCacheObject("providerType"));//added for intX
	pageContext.setAttribute("productAuthority",Cache.getCacheObject("productAuthority"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/HospitalSearchAction.do" >
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>List of Providers</td>
	    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
	  </table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td nowrap>Empanelment&nbsp;No.:<br>
    	  		<input type="text" name="sEmpanelmentNo" class="textBox textBoxMedium"  maxlength="60" value="<bean:write name="frmSearchHospital" property="sEmpanelmentNo"/>">
      		</td>
	  		<td nowrap>Provider&nbsp;Name:<br>
	     		<input name="sHospitalName" type="text" class="textBox textBoxMedium"  maxlength="250" value="<bean:write name="frmSearchHospital" property="sHospitalName"/>">
	    	</td>
	    	
	    	<td valign="bottom">Country: <br>
   				<html:select property ="countryCode" styleClass="selectBox selectBoxMedium">
  					<html:option value="">Select from list</html:option>
          			<html:options collection="countryCode" property="cacheId" labelProperty="cacheDesc"/>
   				</html:select>
       		</td>
       		
        	<td valign="bottom">Health Regularity Authority: <br>
   				<html:select property ="productAuthority" styleClass="selectBox selectBoxMedium">
  					<html:option value="">Select from list</html:option>
          			<html:options collection="productAuthority" property="cacheId" labelProperty="cacheDesc"/>
   				</html:select>
       		</td>		
        			
	    	<td valign="bottom">Governorate:<br>
	       	   <html:select property="stateCode" styleClass="selectBox selectBoxMedium">
                 <html:option value="">Any</html:option>
                 <html:options collection="stateCode" property="cacheId" labelProperty="cacheDesc"/>
              </html:select>
    		</td>
              <td valign="bottom">Area:<br>
	 		  <html:select property="cityCode" styleClass="selectBox selectBoxMedium">
                 <html:option value="">Any</html:option>
                 <html:options collection="cityCode" property="cacheId" labelProperty="cacheDesc"/>
               </html:select>
              </td>
              
 		</tr>
  		<tr>
              <td valign="bottom">Current Provider <br>License ID:<br>
	 		  <input name="sDHAID" type="text" class="textBox textBoxMedium"  maxlength="250" value="<bean:write name="frmSearchHospital" property="sDHAID"/>">
              </td>
	 		 <td nowrap>Branch Location:<br>
                 <html:select property="officeInfo" styleClass="selectBox selectBoxMedium">
                      <html:option value="">Any</html:option>
                      <html:options collection="officeInfo" property="cacheId" labelProperty="cacheDesc"/>
                 </html:select>
	        	</td>
      		<td nowrap>Status:<br>
		  		<html:select property="empanelStatusCode" styleClass="selectBox selectBoxMedium"  onchange="javascript:onStatusChanged()">
                      <html:option value="">Any</html:option>
                      <html:options collection="empanelStatusCode" property="cacheId" labelProperty="cacheDesc"/>
                 </html:select>
	  		</td>
    		<td nowrap>Reason:<br>
    		<logic:notEmpty name="alSubStatus">
   		     	<html:select property="subStatus" styleClass="selectBox selectBoxMedium">
                      <html:option value="">Any</html:option>
                      <html:options collection="alSubStatus" property="cacheId" labelProperty="cacheDesc"/>
                 </html:select>
     		</logic:notEmpty>
     		<logic:empty name="alSubStatus">
     			<select name="subStatus" class="selectBox selectBoxMedium">
     				<option value="">Any</option>
 				</select>
     		</logic:empty>
			</td>
			<td>Grading:
                   <html:select property="gradeCode" styleClass="selectBox selectBoxMedium">
                      <html:option value="">Any</html:option>
                      <html:options collection="gradeCode" property="cacheId" labelProperty="cacheDesc"/>
                  </html:select>
   			</td>
   			</tr>
   			<tr>
   			<td>Provider Type: <br>
   				<html:select property ="providerTypeId" styleClass="selectBox selectBoxMedium">
           			<html:option value="">Select from list</html:option>
           			<html:options collection="providerType" property="cacheId" labelProperty="cacheDesc"/>
   				</html:select>
   			</td>
   			
     		<td valign="bottom" width="100%" align="left" colspan="3">&nbsp;</td>
   			<td valign="bottom" width="100%" align="left">
    				<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
    		 </td>
  		</tr>
</table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
    <ttk:HtmlGrid name="tableData"/>

	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		 <tr>
		    <td width="27%"></td>
		    <td width="73%" align="right">
		    <button type="button" name="Button" accesskey="o" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:OnDownloadProviderDetails();">D<u>o</u>ownload Provider Details</button>&nbsp;
		    <%
			   if(TTKCommon.isDataFound(request,"tableData"))
			   {
		    %>
			    <button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard();"><u>C</u>opy to Web Board</button>&nbsp;
			<%
				}
			%>
			<% if(TTKCommon.isAuthorized(request,"Add"))
      			{
      		%>
			    <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddHospital();"><u>A</u>dd</button>
		    <%
      			}
   			%>
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