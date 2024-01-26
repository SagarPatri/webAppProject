<html>
<body>
<%
/**
 * @ (#) usersupport.jsp
 * Project      : TTK Software Support
 * File         : usersupport.jsp
 * Author       : Vamsi Krishna CH
 * Company      : 
 * Date Created : 
 *
 * @author       : Vamsi Krishna CH
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.business.common.SecurityManagerBean,com.ttk.dto.usermanagement.UserSecurityProfile"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@page import="java.util.ArrayList,com.ttk.dto.insurancepricing.PolicyConfigVO" %>


<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
    <script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="/ttk/scripts/insurancepricing/policydesignconfig.js"></script>	
     <script type="text/javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script> 
<SCRIPT LANGUAGE="JavaScript">
	bAction = false; //to avoid change in web board in product list screen //to clarify
	var TC_Disabled = true;
</SCRIPT>
 <%

	int iRowCount = 0;

%>
<!-- S T A R T : Search Box -->
	 

	<html:form action="/UpdateGenerateQuotationAction.do" method="post">
	
	<logic:notEmpty name="fileName" scope="request">
		<script language="JavaScript">
			var w = screen.availWidth - 10;
			var h = screen.availHeight - 82;
			var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			window.open("/ReportsAction.do?mode=doPrintAcknowledgement&displayFile=<bean:write name="fileName"/>&reportType=PDF",'PrintAcknowledgement',features);
		</script>
	</logic:notEmpty>
	
	
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="65%">Generate Quote</td> 
		  </tr>
	</table>
	<div class="contentArea" id="contentArea">
	<html:errors />
	<logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="middle">&nbsp;
						<bean:message name="updated" scope="request"/>
				  </td>
				</tr>
			</table>
		</logic:notEmpty>
	<logic:match name="frmPolicyConfigQuote" property="Message" value="N">
	<fieldset>
    <legend>Loadings</legend>	
	<table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		<tr>
		   <td class="formLabel" width="15%">Administration Cost <span class="mandatorySymbol">*</span> </td>
			   <td class="textLabelBold" width="25%">  
			       <html:text name="frmPolicyConfigQuote" property="adminCost" styleClass="textBox textBoxSmall" maxlength="13" />
			       %
			   </td>
			    <td width="50%"> </td>
			</tr>
			<tr>
		    <td class="formLabel" width="15%">Management Expenses <span class="mandatorySymbol">*</span> </td>
			   <td class="textLabelBold" width="25%">  
			       <html:text name="frmPolicyConfigQuote" property="managementExpenses" styleClass="textBox textBoxSmall" maxlength="13" />
			       %
			   </td>
			</tr>
			<tr>   
			<td class="formLabel" width="15%">Commission <span class="mandatorySymbol">*</span> </td>
			   <td class="textLabelBold" width="25%">  
			       <html:text name="frmPolicyConfigQuote" property="commission" styleClass="textBox textBoxSmall" maxlength="13" />
			       %
			   </td>
			   </tr>
			   <tr>
			 <td class="formLabel" width="15%">Cost of Capital<span class="mandatorySymbol">*</span> </td>
			   <td class="textLabelBold" width="25%">  
			       <html:text name="frmPolicyConfigQuote" property="costOfCapital" styleClass="textBox textBoxSmall" maxlength="13" />
			       %
			   </td>
			   </tr>
			   <tr>
			  <td class="formLabel" width="25%">Profit Margin <span class="mandatorySymbol">*</span> </td>
			   <td class="textLabelBold" width="25%">  
			       <html:text name="frmPolicyConfigQuote" property="profitMargin" styleClass="textBox textBoxSmall" maxlength="13" />
			       %
			   </td>  
		</tr>
	 </table>
	 </fieldset>
	 <fieldset>
    <legend>Reinsured</legend>	
	<table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		 <tr>
			                  <td class="formLabel" width="8%">Inpatient Benefit Reinsured  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:select property="reinSwitch"  name="frmPolicyConfigQuote"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                    <html:option value="N">No</html:option>
				                     <html:option value="Y">Yes</html:option> 
			                         </html:select>
			                    </td> 
			            
			                    </tr>
	 </table>
	 </fieldset>
	 
	 <fieldset>
    <legend>Signatory</legend>	
	<table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		 <tr>
			                  <td class="formLabel" width="8%">Signatory  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:select property="signatory"  name="frmPolicyConfigQuote"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                    <html:option value="NEE">Neetha Uthaiah</html:option>
				                     <html:option value="UDA">Udaya Perera</html:option> 
			                         </html:select>
			                    </td> 
			            
			                    </tr>
	 </table>
	 </fieldset>
	 
	 <table align="center" class="gridWithPricing" border="0" cellspacing="1" cellpadding="0">
	 <tr>
	 <caption width="100%" class="textLabelBold"><bean:write name="frmPolicyConfigQuote" property="lessFourthousand" /></caption>
	 </tr>
	  <tr>
	 <caption width="100%" align="center" ID="listsubheader" CLASS="gridHeader">Male and Female</caption>
	 </tr>
	<tr>
		<td width="15%" ID="listsubheader" CLASS="gridHeader">Member Type&nbsp;</td>
		<td width="15%" ID="listsubheader" CLASS="gridHeader">Age&nbsp;</td>
		<td width="15%" ID="listsubheader" CLASS="gridHeader">Census - no of members&nbsp;</td>
		<td width="30%" ID="listsubheader" CLASS="gridHeader">Premium PPPA &nbsp;</td>
		<td width="30%" ID="listsubheader" CLASS="gridHeader">Total Premium  &nbsp;</td>
		</tr>

<logic:notEmpty name="frmPolicyConfigQuote" property="PremiumFirst">
		<logic:iterate id="item" name="frmPolicyConfigQuote" property="PremiumFirst">
		   <%if(iRowCount%2==0) { %>
				<tr class="gridOddRow">
			<%
			  } else { %>
  				<tr class="gridEvenRow">
  			<%
			  } %>
			  <td width="15%" class="textLabelBold"><bean:write name="item" property="memberType" /></td>
			  	<td width="15%" class="textLabelBold"><bean:write name="item" property="ageQuote" /></td>
			  	<td width="15%" class="textLabelBold"><bean:write name="item" property="censusQuote" /></td>
			  	<td width="30%" class="textLabelBold"> <bean:write name="item" property="premiumQuote" /></td>
			  	<td width="30%" class="textLabelBold"> <bean:write name="item" property="totalPremiumQuote" /></td>
			  	
				
			</tr>
			<%iRowCount++;%>
</logic:iterate>
</logic:notEmpty>
	  
	</table>
	
	<table align="center" class="gridWithPricing" border="0" cellspacing="1" cellpadding="0">
	 <tr>
	 <caption width="100%" class="textLabelBold"><bean:write name="frmPolicyConfigQuote" property="moreFourthousand" /></caption>
	 </tr>
	  <tr>
	 <caption width="100%" align="center" ID="listsubheader" CLASS="gridHeader">Male and Female</caption>
	 </tr>
	<tr>
		<td width="15%" ID="listsubheader" CLASS="gridHeader">Member Type&nbsp;</td>
		<td width="15%" ID="listsubheader" CLASS="gridHeader">Age&nbsp;</td>
		<td width="15%" ID="listsubheader" CLASS="gridHeader">Census - no of members&nbsp;</td>
		<td width="30%" ID="listsubheader" CLASS="gridHeader">Premium PPPA &nbsp;</td>
		<td width="30%" ID="listsubheader" CLASS="gridHeader">Total Premium  &nbsp;</td>
		</tr>
<logic:notEmpty name="frmPolicyConfigQuote" property="PremiumSecond">
		<logic:iterate id="item" name="frmPolicyConfigQuote" property="PremiumSecond">
		   <%if(iRowCount%2==0) { %>
				<tr class="gridOddRow">
			<%
			  } else { %>
  				<tr class="gridEvenRow">
  			<%
			  } %>
			  <td width="15%" class="textLabelBold"><bean:write name="item" property="memberTypeFamily" /></td>
			  	<td width="15%" class="textLabelBold"><bean:write name="item" property="ageQuoteFamily" /></td>
			  	<td width="15%" class="textLabelBold"><bean:write name="item" property="censusQuoteFamily" /></td>
			  	<td width="30%" class="textLabelBold"> <bean:write name="item" property="premiumQuoteFamily" /></td>
			  	<td width="30%" class="textLabelBold"> <bean:write name="item" property="totalPremiumQuoteFamily" /></td>
			  	
				
			</tr>
				<%iRowCount++;%>
</logic:iterate>
</logic:notEmpty>
	  
	</table>
	 
	 
	 
	 
		 
	  
	 <table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="center"> 
			
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'"	onClick="javascript:onCalculateQuote();"><u>C</u>alculate</button>&nbsp;
			<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'"	onClick="javascript:onGenerateFile();"><u>G</u>enerate Quote</button>
			 </td>
			
		</tr>
	</table>
	 
     </logic:match>
     </div>
   <input type="hidden" name="mode" value=""/>
   <input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="groupseqid" VALUE="<bean:write name="frmPolicyConfigQuote" property="groupProfileSeqID" />">
   <input type="hidden" name="parameter" value=""/>
   <input type="hidden" name="reportType" value=""/>
   
   </html:form> 
   </body>
   </html>



