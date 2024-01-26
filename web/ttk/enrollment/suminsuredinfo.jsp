<%/**
 * @ (#) suminsuredinfo.jsp
 * Project       : TTK HealthCare Services
 * File          : suminsuredinfo.jsp
 * Author        : Manikanta Kumar G G
 * Company       : Span Systems Corporation
 * Date Created  : April 21,2011
 * @author       : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/enrollment/suminsuredinfo.js"></script>
<html:form action="/PolicyDetailsAction.do">
<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td>Sum Insured Info - [ <bean:write name="frmPolicyDetails" property="policyNbr"/> ]</td>
		<td align="right" >&nbsp;</td>     
    </tr>
	</table>
	<!-- E N D : Page Title --> 
	<div class="contentArea" id="contentArea">	
	<html:errors/>
	<!-- S T A R T : Form Fields -->
<html:errors/>
<fieldset>
 <legend>Sum Insured Info</legend>
  <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" >
     <tr>
		<td width="23%" height="20" class="formLabel">Total Sum Insured:</td>
		<td width="27%" class="formLabelBold">
			<bean:write name="frmPolicyDetails" property="totalSumInsured"/>
		</td>
		<td class="textLabelBold">
		     <bean:write name="frmPolicyDetails" property="sumWording"/>
 	    </td>
							
	 </tr>
	 <tr>
	  <td width="23%" height="20" class="formLabel">Added Family Sum Insured:</td>
		<td width="27%" class="formLabelBold">
			<bean:write name="frmPolicyDetails" property="addedSIAmt"/>
		</td>
		<td class="textLabelBold">
		     <bean:write name="frmPolicyDetails" property="addedSIWording"/>
 	    </td>
	 </tr>
	 <tr>
		<td width="23%" height="20" class="formLabel">No. of Families:</td>
		<td width="27%" class="formLabelBold">
			<bean:write name="frmPolicyDetails" property="noofFamiliesAdded"/>
		</td>
	 </tr>
	 <tr>
	  <td width="23%" height="20" class="formLabel">Active Members:</td>
		<td width="27%" class="formLabelBold">
			<bean:write name="frmPolicyDetails" property="activeMembers"/>
		</td>
	 </tr>
	 <tr>
		<td width="23%" height="20" class="formLabel">Cancelled Members:</td>
		<td width="27%" class="formLabelBold">
			<bean:write name="frmPolicyDetails" property="cancelMembers"/>
		</td>
	</tr>
  </table>
</fieldset>
<!-- E N D : Form Fields -->
<!-- S T A R T : Buttons -->
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="100%" align="center">
              <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSIClose();"><u>C</u>lose</button>
           </td>
        </tr>
   </table>
</div>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>