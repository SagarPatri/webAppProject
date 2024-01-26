<%
/**
 * @ (#) searchtariff.jsp 9th April 2015
 * Project      : TTK HealthCare Services
 * File         : searchtariff.jsp
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 9th April 2015
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/searchtariff.js"></script>
<SCRIPT LANGUAGE="JavaScript">
bAction = false;
var TC_Disabled = true;
</SCRIPT>
<%
pageContext.setAttribute("alPayerCodeSearch", Cache.getCacheObject("insuranceCompany"));
pageContext.setAttribute("alProviderCodeSearch", Cache.getCacheObject("providerCodeSearch"));
pageContext.setAttribute("alNetworkTypeSearch", Cache.getCacheObject("primaryNetwork"));
pageContext.setAttribute("alCorpCodeSearch", Cache.getCacheObject("corpCodeSearch"));
pageContext.setAttribute("alServiceName", Cache.getCacheObject("serviceName"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/TarrifSearchAction.do" method="post" enctype="multipart/form-data">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td>Tariff Search</td>
		    <%-- <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td> --%>
		  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	
	<html:errors/>
	<!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
					<bean:write name="updated" scope="request"/>
				</td>
			</tr>
		</table>
	</logic:notEmpty>
	
	<!-- Search box -->
	<table class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" nowrap>Type of Tariff: <span class="mandatorySymbol">*</span><br>
			<html:select property="tariffTypeSearch" styleClass="selectBox selectBoxMedium" onchange="onChangeTariffType(this)">
				<html:option value="">--Select From List--</html:option>
				<html:option value="HOSP">Provider</html:option>
				<html:option value="INS">Payer</html:option>
				<html:option value="COR">Corporate</html:option>			
			</html:select>
		</td>
        <td align="left" nowrap>Payer:<br>
			<html:select property="payerCodeSearch" styleClass="selectBox selectBoxLarge">
					<html:option value="">--Select From List--</html:option>
		        	<html:optionsCollection name="alPayerCodeSearch" label="cacheDesc" value="cacheId" />
			</html:select>
		</td>
		<td align="left" nowrap>Provider:<br>
			<html:select property="providerCodeSearch" styleClass="selectBox selectBoxMedium">
				<html:option value="">--Select From List--</html:option>
				<html:optionsCollection name="alProviderCodeSearch" label="cacheDesc" value="cacheId" />
			</html:select>
		</td>
		<td align="left" nowrap>Network Type:<br>
			<html:select property="networkTypeSearch" styleClass="selectBox selectBoxMedium">
					<html:option value="">--Select From List--</html:option>
		        	<html:optionsCollection name="alNetworkTypeSearch" label="cacheDesc" value="cacheId" />
			</html:select>
		</td>
		</tr>
		
		<tr>
		<td align="left" nowrap>Corporate:<br>
			<html:select property="corpCodeSearch" styleClass="selectBox selectBoxMedium">
					<html:option value="">--Select From List--</html:option>
		        	<html:optionsCollection name="alCorpCodeSearch" label="cacheDesc" value="cacheId" />
			</html:select>
		</td>
		<td align="left" nowrap>Price Ref No:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Activity Code:<br>
			<html:text property="priceRefNo" styleClass="textBox textBoxMedium"/>
			&nbsp;&nbsp;
			<html:text property="activityCode" styleClass="textBox textBoxSmall"/>
		</td>
		<td align="left" nowrap>Service Type :<br>
			<html:select property="serviceName" styleClass="selectBox selectBoxMedium">
					<html:option value="">--All--</html:option>
		        	<html:optionsCollection name="alServiceName" label="cacheDesc" value="cacheId" />
			</html:select>
		</td>
		<td align="left" nowrap>Internal Code :<br>
			<html:text property="internalCode" styleClass="textBox textBoxSmall"/> 
		</td>
      <td valign="bottom" nowrap>
	        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
      </tr>
      </table>
	<!-- Search box ends here -->
    
    <div>
    <p> 
<!-- 	<button type="button" name="Button" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUploadButton()"><u>U</u>pload Tariff</button>
 	<a href="#" onclick="javascript:onUploadLink()">Click here to <u>U</u>pload Tariff</a>
 	-->
 	<button type="button" name="Button" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUploadLink()"><u>U</u>pload Tariff</button>
 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 	<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onModifyTariff()"><u>E</u>dit/Modify Tariff Items</button>
 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onModifyServiceType()"><u>S</u>ervice Type Edit/Modify</button>
    </p>
    </div>
    
    <!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData" className="gridWithCheckBox zeroMargin"/>
	<!-- E N D : Grid -->
    
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		 <tr>
		    <td width="27%"></td>
		    <td width="73%" align="right">
		    <%
			   if(TTKCommon.isDataFound(request,"tableData"))
			   {
		    %>
			    <button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard();"><u>C</u>opy to Web Board</button>&nbsp;
			<%
				}
			%>
	        </td>
	  	</tr>
	  		 <ttk:PageLinks name="tableData"/>

	</table>
	
	<!-- <div>
	<br>
		<a href="#" onclick="javascript:onModifyTariff()">Click here to Edit/Modify</a>		
		&nbsp;&nbsp;&nbsp;&nbsp;
    </div> -->
	
    <!-- E N D : SELCTIONS -->
	<!-- E N D : Buttons -->
	<!-- E N D : Form Fields -->
   </div>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="child" value="">
 </html:form>