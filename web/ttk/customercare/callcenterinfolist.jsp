<%
/**
 * @ (#) callcenterinfolist.jsp 10th Nov 2006
 * Project      : TTK HealthCare Services
 * File         : callcenterinfolist.jsp
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : 10th Nov 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script LANGUAGE="JavaScript" SRC="/ttk/scripts/customercare/callcenterinfolist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<%
	String strSubLink=TTKCommon.getActiveSubLink(request);
	pageContext.setAttribute("strSubLink",strSubLink);
	pageContext.setAttribute("listTTKBranch",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("listInsuranceCompany",Cache.getCacheObject("insuranceCompany"));

	pageContext.setAttribute("stateCode", Cache.getCacheObject("stateCode"));
	pageContext.setAttribute("cityCode", Cache.getCacheObject("cityCode"));
	pageContext.setAttribute("countryCode", Cache.getCacheObject("countryCode"));

%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/CallCenterInfoAction.do" >
	<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    <logic:match name="frmCallCenterInfo" property="sType" value="HOS">
    	Select Hospitals
    </logic:match>
    <logic:match name="frmCallCenterInfo" property="sType" value="INS">
    	Select Healthcare Company Branch
    </logic:match>
    <logic:match name="frmCallCenterInfo" property="sType" value="TTK">
    	Select Vidal Health Branch
    </logic:match>
    </td>
  </tr>
</table>
	<!-- E N D : Page Title -->
	<html:errors/>
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
      <td nowrap>Type:<br>
      <html:select property="sType" styleClass="selectBox selectBoxSmall" onchange="javascript:onChangeType();">
	      <html:option value="HOS">NHCP</html:option>
	      <html:option value="INS">Healthcare</html:option>
	      <html:option value="TTK">Vidal Health</html:option>
      </html:select>
		</td>

	<logic:match name="frmCallCenterInfo" property="sType" value="HOS">
      <td nowrap>Hospital&nbsp;Name:<br>
	     		<html:text property="sHospitalName" styleClass="textBox textBoxMedium"  maxlength="250"/>
	    	</td>

    		<td nowrap>Empanelment&nbsp;No.:<br>
    	  		<html:text property="sEmpanelmentNo" styleClass="textBox textBoxMedium"  maxlength="60"/>
      		</td>
			<td valign="bottom">State:<br>
		       	   <html:select property="stateCode" styleClass="selectBox selectBoxMedium">
	                 <html:option value="">Any</html:option>
	                 <html:options collection="stateCode" property="cacheId" labelProperty="cacheDesc"/>
	              </html:select>
	    		</td>
	    	<td valign="bottom">City:<br>
 		  	<html:select property="cityCode" styleClass="selectBox selectBoxMedium">
	            <html:option value="">Any</html:option>
	            <html:options collection="cityCode" property="cacheId" labelProperty="cacheDesc"/>
           </html:select>
          </td>
      </logic:match>
      <logic:match name="frmCallCenterInfo" property="sType" value="INS">
        <td nowrap>Healthcare Company:<br>
          	<html:select property="sInsuranceSeqID" styleClass="selectBox selectBoxMedium" >
				<html:optionsCollection name="listInsuranceCompany" label="cacheDesc" value="cacheId"/>
			</html:select>
		</td>
        <td nowrap>Company Code:<br>
             <html:text property="sCompanyCode" styleClass="textBox textBoxMedium" maxlength="60" />
        </td>
        <td nowrap>Vidal Health Branch:<br>
        	 <html:select property="sTTKBranchCode" styleClass="selectBox selectBoxMedium" >
				<html:option value="">Any</html:option>
				<html:optionsCollection name="listTTKBranch" label="cacheDesc" value="cacheId"/>
			</html:select>
        </td>
        </logic:match>
        <td valign="bottom" width="100%" nowrap>
        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
      </tr>
</table>
	<!-- E N D : Search Box -->

	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="enquiryListData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
    <td width="73%" nowrap align="right">
    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
    </td>
  </tr>
  <ttk:PageLinks name="enquiryListData"/>
</table>

	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->	</td>

<!-- E N D : Main Container Table -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="child" VALUE="CallCenterInfoList">
</html:form>