<%
/** @ (#) planpackagelist.jsp 21st Oct 2005
 * Project     : TTK Healthcare Services
 * File        : planpackagelist.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 21st Oct 2005
 *
 * @author 			Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.dto.administration.RevisionPlanVO,com.ttk.common.security.Cache" %>
<%@ page import="com.ttk.dto.empanelment.InsuranceVO" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%
	RevisionPlanVO revisePlanVO=(RevisionPlanVO)session.getAttribute("RevisionPlanVO");
	InsuranceVO insuranceVO=(InsuranceVO)request.getSession().getAttribute("insuranceVO");
	pageContext.setAttribute("listGeneralCodePlan",Cache.getCacheObject("generalCodePlan"));
	pageContext.setAttribute("listPackageCode",Cache.getCacheObject("packageCode"));
%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/planpackagelist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
    <html:form action="/TariffPlanPackageAction.do">
	<!-- S T A R T : Page Title -->
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		  <logic:notEmpty name="RevisionPlanVO">
	   		<logic:notMatch name="RevisionPlanVO" property="defaultPlanYn" value="Y">
		  		<td >List of Tariff Items - <%=TTKCommon.getActiveSubLink(request).equals("Hospital")? "["+TTKCommon.getWebBoardDesc(request)+"]" : "" %> <%=insuranceVO!=null? "["+insuranceVO.getCompanyName()+"]["+insuranceVO.getProdPolicyNumber()+"]" :"" %>[<%=revisePlanVO.getTariffPlanName()%>] [w.e.f - <%=revisePlanVO.getStartDate()%> <%=revisePlanVO.getEndDate().equals("")? "" :"to "+revisePlanVO.getEndDate() %> ] </td>
		  	</logic:notMatch>

	  		<logic:match name="RevisionPlanVO" property="defaultPlanYn" value="Y">
		  		<td >List of Tariff Items - [<%=revisePlanVO.getTariffPlanName()%>]</td>
		  	</logic:match>
		  </logic:notEmpty>
		  </tr>
		</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
    <html:errors/>
	<!-- S T A R T : Search Box --><table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Name:<br>
        	<html:text property="sPackageName" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/></td>
		<td nowrap>Type:<br>
			<html:select property="generalCodePlan" styleClass="selectBox">
				<html:option value="">Any</html:option>
				<html:options collection="listGeneralCodePlan" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>
		</td>
		<logic:notMatch name="RevisionPlanVO" property="defaultPlanYn" value="Y">
			<td nowrap>Search in:<br>
				<html:select property="packageCode" styleClass="selectBox">
					<html:options collection="listPackageCode" property="cacheId" labelProperty="cacheDesc"/>
				</html:select>
	        </td>
	        <td nowrap  valign="bottom" width="50%">
				<input name="modifiedYN" type="checkbox" value="Y" <logic:match name="frmPlanPackage" property="sModified" value="Y">checked</logic:match>>Modified Tariff Items
				<input type="hidden" name="closed">
			</td>
		</logic:notMatch>

		<logic:match name="RevisionPlanVO" property="defaultPlanYn" value="Y">
			<input type="hidden" name="closed" value="default">
		</logic:match>

		<td valign="bottom"><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
        <td width="50%">&nbsp;</td>
		</tr>
     </table>
	<!-- E N D : Search Box -->

	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="planPackageData" />
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="27%">&nbsp;</td>
    <td width="73%" nowrap align="right">
    	<logic:notEmpty name="RevisionPlanVO">
			<logic:notMatch name="RevisionPlanVO" property="defaultPlanYn" value="Y">
			<%
		  		if(TTKCommon.isDataFound(request,"planPackageData")&& TTKCommon.isAuthorized(request,"Edit"))
		  		{
		    %>
				<logic:match name="frmPlanPackage" property="packageCode" value="AVA">
	    			<button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onChangeStatus('<bean:write name="frmPlanPackage" property="sPackageName"/>')"><u>N</u>ot Available</button>&nbsp;
				</logic:match>
	  			<logic:notMatch name="frmPlanPackage" property="packageCode" value="AVA">
	  				<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onChangeStatus('<bean:write name="frmPlanPackage" property="sPackageName"/>')"><u>A</u>vailable</button>&nbsp;
	  			</logic:notMatch>
	    	<%
	   	  		}//end of if(TTKCommon.isDataFound(request,"planPackageData"))
	    	%>
    		</logic:notMatch>
    	</logic:notEmpty>
    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
    </td>
  </tr>
  <ttk:PageLinks name="planPackageData"/>
</table>
</div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<input type="hidden" name="child" value="PlanPackage">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<html:hidden property="sModified"/>
	</html:form>
	<!-- E N D : Content/Form Area -->