<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<!-- <link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link> -->
	<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
    <script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
     <script type="text/javascript" src="/ttk/scripts/insurancepricing/swpricingOpAOC.js"></script> 	
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
	    document.forms[1].action = "/SwInsPricingOpAreaOfCoverAction.do";
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
 
  
<logic:notEmpty name="frmSwPricingOpAOC" property="opAocList">
	<table style="width: 90%;" align="center" class="gridWithPricing" border="0" cellspacing="5" cellpadding="0">

		
					
	    <tr>
		<td width="5%" ID="listsubheader" CLASS="gridHeader" align="center">OP Area Of Cover&nbsp;</td>
		<td width="15%" ID="listsubheader" CLASS="gridHeader" align="center">Network&nbsp;</td>
	
		</tr>
<logic:iterate id="OpRow" name="frmSwPricingOpAOC" indexId="rowIndex" property="opAocList">		
	<%
		 if(rowIndex%2==0) { %>
				<tr class="gridOddRow">  
			<%
			  } else { %>
  				<tr class="gridEvenRow">  
  			<%
			  } %>
			 
			  	<td width="30%" class="formLabel" align="center"><font color="blue"><bean:write name="OpRow" property="description" /></font></td>
			   	  	<td width="10%" class="formLabel" align="center">
			  	<input class="textBox textBoxLong" type="text" name="network" readonly="true" id="networkID<%=rowIndex%>" value="<bean:write name="OpRow" property="network" />">
			  	<html:link  href="javascript:configureNetwork('${rowIndex}');" ><img src="/ttk/images/EditIcon.gif" alt="Configure Network"  width="16" height="16" border="0" align="absmiddle"></html:link>
			  	 
			  	</td>  
	  
			 <td>
                   	<input type="hidden" name="factId" value="<bean:write name="OpRow" property="factId" />">
                   
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
	<!-- E N D : Buttons and Page Counter -->
</html:form>
</div>
<!-- E N D : Content/Form Area -->