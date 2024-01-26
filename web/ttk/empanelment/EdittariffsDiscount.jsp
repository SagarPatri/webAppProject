<%@page import="com.ttk.dto.administration.TariffUploadVO"%>
<%
/**
 * @ (#) EditTariffsDiscount.jsp 15th April 2015
 * Project      : TTK HealthCare Services
 * File         : EditTariffsDiscount.jsp
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 15th April 2015
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
 <%@ page import="java.util.ArrayList, com.ttk.common.TTKCommon" %>
 
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/editTariffsDiscount.js"></script>
<SCRIPT LANGUAGE="JavaScript">
bAction = false;
var TC_Disabled = true;
</SCRIPT>
<%
pageContext.setAttribute("alPayerCodeSearch", Cache.getCacheObject("insuranceCompany"));
pageContext.setAttribute("alProviderCodeSearch", Cache.getCacheObject("providerCodeSearch"));
pageContext.setAttribute("alNetworkTypeSearch", Cache.getCacheObject("networkTypeSearch"));
pageContext.setAttribute("alCorpCodeSearch", Cache.getCacheObject("corpCodeSearch"));
pageContext.setAttribute("alServiceName", Cache.getCacheObject("serviceName"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ShowTariffItems.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td>Tariff Discount Update</td>
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
        <%-- <td align="left" nowrap>Type of Tariff: <span class="mandatorySymbol">*</span><br>
			<html:select property="tariffTypeSearch" styleClass="selectBox selectBoxMedium">
				<html:option value="HOSP">Provider</html:option>
			</html:select>
		</td> --%>
        <td align="left" nowrap>Payer:<br>
			<html:select property="payerCodeSearch" styleClass="selectBox selectBoxLarge">
					<html:option value="">ALL</html:option>
		        	<html:optionsCollection name="alPayerCodeSearch" label="cacheDesc" value="cacheId" />
			</html:select>
		</td>
		<td align="left" nowrap>Provider:<br>
			<html:select property="providerCodeSearch" styleClass="selectBox selectBoxLarge" disabled="true" readonly="true">
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
		<%--<td align="left" nowrap>Corporate:<br>
			<html:select property="corpCodeSearch" styleClass="selectBox selectBoxMedium">
					<html:option value="">--Select From List--</html:option>
		        	<html:optionsCollection name="alCorpCodeSearch" label="cacheDesc" value="cacheId" />
			</html:select>
		</td>
		 <td align="left" nowrap>Price Ref No:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Activity Code:<br>
			<html:text property="priceRefNo" styleClass="textBox textBoxMedium"/>
			&nbsp;&nbsp;
			<html:text property="activityCode" styleClass="textBox textBoxSmall"/>
		</td> --%>
		<td align="left" nowrap>Service Type :<br>
			<html:select property="serviceName" styleClass="selectBox selectBoxMedium">
					<html:option value="">--Select From List--</html:option>
		        	<html:optionsCollection name="alServiceName" label="cacheDesc" value="cacheId" />
			</html:select>
		</td>
		<%-- <td align="left" nowrap>Internal Code :<br>
			<html:text property="internalCode" styleClass="textBox textBoxSmall"/>
		</td> --%>
      <td valign="bottom" nowrap>
	        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
      </tr>
      </table>
	<!-- Search box ends here -->
	<br><br><br>
	
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="TariffDiscountTable" className="gridWithCheckBox zeroMargin"/>
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
			    <!-- <button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard();"><u>C</u>opy to Web Board</button>&nbsp; -->
			     Enter Discount Percentage: <html:text property="discAmount" styleClass="textBox textBoxSmall" size="6" maxlength="6"/>
			<%
				}
			%>
	        </td>
	  	</tr>
	  		 <ttk:PageLinks name="TariffDiscountTable"/>
	</table>
	
	
	
	<!-- Below Code to get the multiple values selection, for each price ref numb can able to provide different taiff discounts -->
   
    <%-- 
    
    <%
    String getVal	=	(String)request.getAttribute("showValues")==null?"":(String)request.getAttribute("showValues");
    if(getVal.equals("showValues")){
	String colorType ="",questionDescTemp ="";
   	int colorCode =2;
	pageContext.setAttribute("colorCode",new Integer(colorCode));
	ArrayList alTariffItem	=	(ArrayList)request.getAttribute("alTariffItem");
	int k=0,l=0;
   	%>
   	
   	<table class="formContainer" style="width:85%" border="0" cellpadding="0">
   	<%
		TariffUploadVO tariffUploadVO	=	null;
   		String tempGroup	=	"";
   		for(k=0; k<alTariffItem.size(); k++)
   		{
				colorType=((colorCode)%2)==0?"gridOddRow":"gridEvenRow";
				colorCode++;
				
   			tariffUploadVO	=	(TariffUploadVO)alTariffItem.get(k);
   			if(!tempGroup.equals(tariffUploadVO.getPriceRefNo()))
   			{
   	   			tempGroup				=	tariffUploadVO.getPriceRefNo();
		%>
   			<tr>
   				<td align="center" colspan="4" class="gridEvenRow"><%= tariffUploadVO.getPriceRefNo() %>
    	       </td>
    	      <td align="left" colspan="2" class="gridEvenRow">
    	      <html:text property="discAmount" styleClass="textBox textBoxSmall" styleId="commonClass"></html:text> </td>
   				</td>
   			</tr>
   			<tr>
   			<%}//end if
   			for(l=0; (l<=2 && (k+l)<alTariffItem.size()); l++)
   	   		{
   					tariffUploadVO	=	(TariffUploadVO)alTariffItem.get(k+l);
   			%>
					<td align="left"><%= tariffUploadVO.getActivityCode() %>
				<input name="activityCodes" type="checkbox" value="<%= tariffUploadVO.getTariffSeqId() %>" id="commonClass">
	             </td>
				
   			<%
   			}//end for
   			k	=	k+2;
   			%></tr><%
   		}//end for
   	
   	%></table>
   	<%} %> 
   	
   	--%>
    <!-- Code E N D S to get the multiple values selection, for each price ref numb can able to provide different taiff discounts -->
    

    
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		 <tr>
		    <td width="27%"></td>
		    <td width="73%" align="right">
		    
		    <button type="button" name="Button" accesskey="b" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:onBack();"><u>B</u>ack</button>&nbsp;&nbsp;&nbsp;
		    
		    <%
			   if(TTKCommon.isDataFound(request,"tableData"))
			   {
		    %>
			    <button type="button" name="Button" accesskey="u" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:UpdateDiscount();"><u>U</u>pdate Discount</button>&nbsp;
			<%
				}
			%>
	        </td>
	  	</tr>
	</table>
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
	<input type="hidden" name="selectedIds" value="">
 </html:form>