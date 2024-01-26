<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<!-- <link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link> -->
	<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
    <script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
    <!-- <script type="text/javascript" src="/ttk/scripts/insurancepricing/swpricinghome.js"></script> -->	
     <script type="text/javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script> 
     <script language="javascript" src="/ttk/scripts/utils.js"></script>
   
   <style type="text/css">
  .RedRowClass {
	color: red;
}

  </style>
   
     <SCRIPT LANGUAGE="JavaScript">
	bAction = false; //to avoid change in web board in product list screen //to clarify
	var TC_Disabled = true;
	
	function onSave()
	{
	//alert('onSave');
		document.forms[1].mode.value = "doSave";
		document.forms[1].child.value="";
	    document.forms[1].action = "/SwInsPricingFactorMasterAction.do";
	    document.forms[1].submit();
	}//end of onClose()
	
	
</SCRIPT>
<%

	int iRowCount = 0;
	int iRowCount2table = 0;
	String livesViewMode = "";
	String textbox = "textBox textBoxVerySmall";
%>
<div class="contentArea" id="contentArea">
<!-- S T A R T : Content/Form Area -->
<html:form action="/SwInsPricingFactorMasterAction.do" >
<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<!-- S T A R T : Page Title -->
	<!-- S T A R T : Page Title -->
<%-- 	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
	    		<td><bean:write name="frmSwPricingFactorMaster" property="caption"/></td>
			    <td width="43%" align="right" class="webBoard">&nbsp;</td>
		  </tr>
	</table>
		<logic:notEmpty name="frmSwPricingFactorMaster"	property="pricingNumberAlert">
				<table align="center" class="errorContainer" border="0"	cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Warning"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmSwPricingFactorMaster" property="pricingNumberAlert" /></td>
					</tr>
				</table>
			</logic:notEmpty>
	<logic:notEmpty name="updated" scope="request">
  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
         <bean:message name="updated" scope="request"/>
     </td>
   </tr>
  </table>
  </logic:notEmpty>  --%>
  
  			<logic:notEmpty name="successMsg" scope="request">
				<table align="center" class="successContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="successMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
 
  
<logic:notEmpty name="frmSwPricingFactorMaster" property="factorMasterList">
	<table style="width: 90%;" align="center" class="gridWithPricing" border="0" cellspacing="5" cellpadding="0">

		
					
	    <tr>
		<td width="5%" ID="listsubheader" CLASS="gridHeader" align="center">Fact Id&nbsp;</td>
		<td width="15%" ID="listsubheader" CLASS="gridHeader" align="center">BENEFIT NAME&nbsp;</td>
		<td width="20%" ID="listsubheader" CLASS="gridHeader" align="center">BENEFIT DISCREPTION&nbsp;</td>
		<td width="10%" ID="listsubheader" CLASS="gridHeader" align="center">GENDER&nbsp;</td>
		<td width="30%" ID="listsubheader" CLASS="gridHeader" align="center">DESCRIPTION&nbsp;</td>
		<td width="10%" ID="listsubheader" CLASS="gridHeader" align="center">FACTOR&nbsp;</td>
		<td width="10%" ID="listsubheader" CLASS="gridHeader" align="center">AVERAGE AGE&nbsp;</td>
		</tr>
<logic:iterate id="factRow" name="frmSwPricingFactorMaster" indexId="rowIndex" property="factorMasterList">		
	<%
		 if(rowIndex%2==0) { %>
				<tr class="RedRowClass">  <!-- gridOddRow,BlueRowClass -->
			<%
			  } else { %>
  				<tr class="gridEvenRow">  <!-- gridEvenRow,RedRowClass -->
  			<%
			  } %>
			  	<td width="5%" class="formLabel" align="center">
			  	<input type="hidden" name="factId" value="<bean:write name="factRow" property="factId" />">
			  	<bean:write name="factRow" property="factId" />
			  	</td>
			  	<logic:equal name="factRow" property="rowColor" value="RedRowClass">
			  	<td width="15%" class="formLabel" align="center"><font color="red"><bean:write name="factRow" property="benefitRule" /></font></td>
			  	<td width="20%" class="formLabel" align="center"><font color="red"><bean:write name="factRow" property="benefitDesc"  /></font></td>
			  	<td width="10%" class="formLabel" align="center"><font color="red"><bean:write name="factRow" property="gender" /></font></td>
			  	<td width="30%" class="formLabel" align="center"><font color="red"><bean:write name="factRow" property="description" /></font></td>
			  	</logic:equal>
			  	<logic:notEqual name="factRow" property="rowColor" value="RedRowClass">
			  	<td width="15%" class="formLabel" align="center"><font color="blue"><bean:write name="factRow" property="benefitRule" /></font></td>
			  	<td width="20%" class="formLabel" align="center"><font color="blue"><bean:write name="factRow" property="benefitDesc"  /></font></td>
			  	<td width="10%" class="formLabel" align="center"><font color="blue"><bean:write name="factRow" property="gender" /></font></td>
			  	<td width="30%" class="formLabel" align="center"><font color="blue"><bean:write name="factRow" property="description" /></font></td>
			  	</logic:notEqual>
			  	<td width="10%" class="formLabel" align="center">
			  	<input class="textBox textBoxLong" type="text" name="factor<bean:write name="factRow" property="factId" />" value="<bean:write name="factRow" property="factor" />">
			  	
			  	</td>
			  	<td width="1%" class="formLabel" align="center">
			  	<input class="textBox textBoxSmall" type="text" name="averageAge<bean:write name="factRow" property="factId" />" value="<bean:write name="factRow" property="averageAge" />">
			  
			  	
			  	</td>
			  	<!-- <td width="15%" class="textLabelBold">  -->
		
		</logic:iterate>
 
</logic:notEmpty>

    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
    		<td width="100%" align="center">
	    		<button type="button" name="Button" accesskey="s" class="buttons" align="center" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>
    		</td>
    	</tr>
    </table> 
 

	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<!-- E N D : Buttons and Page Counter -->
</html:form>
</div>
<!-- E N D : Content/Form Area -->