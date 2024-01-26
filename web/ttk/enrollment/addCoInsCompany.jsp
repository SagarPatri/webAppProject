<%@page import="org.apache.struts.action.DynaActionForm"%>
<%
/** @ (#) providerlist.jsp June 29,2015
 * Project     : Project-X
 * File        : coInsCompanyDetails.jsp
 * Author      : Nagababu K
 * Company     : Vidal Health TPA Pvt. Ltd., 
 * Date Created: June 29,2015
 *
 * @author 		 : Nagababu K
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/enrollment/CoInsCompDetails.js"></script>
<script type="text/javascript" SRC="/ttk/scripts/trackdatachanges.js"></script>
<%@ page
	import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>

<% 
pageContext.setAttribute("coInsCompNameList",Cache.getCacheObject("coInsCompNameList"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/CoInsCompDetailsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td width="57%">Co-insurance details</td>
    		<td width="43%" align="right" class="webBoard">&nbsp;</td>
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors/>
		<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
					<bean:write name="updated" scope="request"/>
				</td>
			</tr>
		</table>
	</logic:notEmpty>
	<logic:notEmpty name="fileError" scope="request" >
	<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <bean:write name="fileError" scope="request"/>
	        </td>
	      </tr>
   	 </table>
	</logic:notEmpty>
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->		
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>

        <td nowrap>Company Name:<br>        
        	<td class="textLabel">
				<html:select property="compName" styleClass="selectBox selectBoxMedium" onchange="getCompanyCode();">
				<html:option value="">Select from list</html:option>
					<html:optionsCollection name="coInsCompNameList" label="cacheDesc" value="cacheId" />
				</html:select>
			</td>
        <td nowrap>Company ID:<br>
         	<html:text property="compID" styleClass="textBox textBoxLarge" disabled="true"/></td>
    	<td nowrap>Percentage:<br>        
        	<html:text property="coInsPercentage" styleClass="textBox textBoxMedium"/>
        </td>
		 </tr>
  	</table>
  	
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
 	 <tr>
	    <td width="27%"> </td>
	    <td width="73%" nowrap align="right">
	    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
	    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseAddComp()"><u>C</u>lose</button>&nbsp;
	    </td>
	    </tr>
	    </table>
	</div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT type="hidden" name="rownum" value="">
	<input type="hidden" name="child" value="">
	<INPUT type="hidden" name="mode" value="">
	<INPUT type="hidden" name="sortId" value="">
	<INPUT type="hidden" name="pageId" value="">
	<INPUT type="hidden" name="tab" value="">
	<INPUT type="hidden" name="reforward" value="">
</html:form>
<!-- E N D : Content/Form Area -->
