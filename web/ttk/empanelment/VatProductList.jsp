<%
	/**
	 * @ (#) VatProductList.jsp Sep 19, 2018
	 * Project 	     : ttkproject
	 * File          : VatProductList.jsp
	 * Author        :  Deepthi Meesala
	 * Company       : RCS Technologies
	 * Date Created  : Sep 19, 2018
	 *
	 * Modified by   :  
	 * Modified date :  
	 * Reason        :  
	 */
%>

<%@page import="org.apache.struts.action.DynaActionForm"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/VatProductList.js"></script>


<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/autoComplete.css" />
<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>

</head>


<% 

pageContext.setAttribute("regAuthority",Cache.getCacheObject("regAuthority"));

boolean submissionMode = false;

%>


<!-- S T A R T : Content/Form Area -->
<html:form action="/InsuranceVatProduct.do">
    <!-- S T A R T : Page Title -->
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="57%">List of Products</td>
            <td width="43%" align="right" class="webBoard">&nbsp;</td>
          </tr>
    </table>
    <!-- E N D : Page Title -->
    <html:errors/>
    
    
    <logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
			  		<td>
			  			<img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:message name="updated" scope="request"/>
			  		</td>
				</tr>
			</table>
		</logic:notEmpty>
    
    <div class="contentArea" id="contentArea">
    <!-- S T A R T : Search Box -->
   
     
     
     
     <fieldset>
     <legend>Product Details </legend>
     
     <div>
        <ttk:HtmlGrid  name="tableData"/>
    </div>
   
     </fieldset>
    
    
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="center">
        <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:saveVatProduct();"><u>S</u>ave</button>&nbsp;
        <button type="button" onclick="onClose();" name="Button1" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"><u>C</u>lose</button>
        </td>
        </tr>
        </table>

    </div>
    <!-- E N D : Buttons and Page Counter -->
    <INPUT type="hidden" name="rownum" value="">
    <input type="hidden" name="child" value="">
    <INPUT type="hidden" name="mode" value="onOldLicense">
    <INPUT type="hidden" name="sortId" value="">
    <INPUT type="hidden" name="pageId" value="">
    <INPUT type="hidden" name="tab" value="">
    <INPUT type="hidden" name="reforward" value="">
    <input type="hidden" name="authority" value="">
 
    
    
    
    
    

    
    
    
    
</html:form>
<!-- E N D : Content/Form Area -->
