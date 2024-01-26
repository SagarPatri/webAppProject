<%
/**
 * @ (#) claimsdepositdetails.jsp August 28th, 2007
 * Project      : TTK HealthCare Services
 * File         : claimsdepositdetails.jsp
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : August 28th, 2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/claimsdepositdetails.js"></script>
<%
	pageContext.setAttribute("bufferMode",Cache.getCacheObject("bufferMode"));
%>
<html:form action="/ClaimsDepositDetailsAction.do"> 
<!-- S T A R T : Page Title --> 
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td width="57%"><bean:write name="frmClaimsDeposit" property="caption"/></td> 
            <td width="43%" align="right" class="webBoard">&nbsp;</td> 
          </tr> 
        </table> 
        <!-- E N D : Page Title --> 
        <!-- S T A R T : Form Fields -->
        <div class="contentArea" id="contentArea">
		<html:errors/>
		<!-- S T A R T : Success Box -->
		<logic:notEmpty name="updated" scope="request">
		  	<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
		    	<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
		    		<bean:message name="updated" scope="request"/>
		    	</td>
		 	</tr>
			</table>
		</logic:notEmpty>
		<!-- E N D : Success Box --> 
		<br/>
		<fieldset><legend>List of Claims</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td width="10%" align="left" nowrap class="formLabel">Total Transaction Deposited Amt.(OMR) :</td>
				<td width="80%" align="left" nowrap>
				<html:text name="frmClaimsDeposit" property="transAmt" readonly="true" styleClass="textBoxDisabled textBoxSmall" /></td>
			</tr>
		</table>
		<BR/>
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="gridWithCheckBox zeroMargin">
          <tr valign="middle"> 
            <td width="19%"  class="gridHeader" style="height:30px;padding-left:5px">Claims Settlement Number</td> 
            <td width="15%" align="center"  class="gridHeader" style="padding-left:5px">Approval Amt. (OMR)</td> 
			<td width="15%" align="center" class="gridHeader" style="padding-left:5px">Total Amt. Paid (OMR) </td>
            <td width="15%" align="center" class="gridHeader" style="padding-left:5px">Amt. Paid (OMR)</td> 
            <td width="15%" align="center" class="gridHeader" style="padding-left:5px">Transaction Type</td>
            <td width="15%" align="center" class="gridHeader" style="padding-left:5px">Current Amt. (OMR) </td> 
          </tr>
		<logic:empty name="frmClaimsDeposit" property="claimsdetails">
			<tr>
				<td class="generalcontent" colspan="7">&nbsp;&nbsp;No Data Found</td>
			</tr>
		</logic:empty>
		<logic:notEmpty name="frmClaimsDeposit" property="claimsdetails">
	     
	      <logic:iterate id="claimsdetails" indexId="i" name="frmClaimsDeposit" property="claimsdetails">
	      <input type="hidden" name="tranCompleteYN" value="<bean:write property='tranCompleteYN' name='claimsdetails'/>">
	      <tr class="<%=(i.intValue()%2==0)? "gridOddRow":"gridEvenRow"%>">
	      	<td><bean:write name="claimsdetails" property="claimSettleNbr"/>
	      		<html:hidden name="claimsdetails" property="claimSettleNbr"/>
	      	</td>
	      	<td><bean:write name="claimsdetails" property="approvedAmt"/>
	      		<html:hidden name="claimsdetails" property="approvedAmt"/>
	      	</td>
	      	<td><bean:write name="claimsdetails" property="totalAmtPaid"/>
	      		<html:hidden name="claimsdetails" property="totalAmtPaid"/>
	      	</td>
	      	<td><bean:write name="claimsdetails" property="transAmtPaid"/>
	      		<html:hidden name="claimsdetails" property="transAmtPaid"/>
	      	</td>
	      	<td align="right" style="padding-left:5px">
		      	<html:select name="claimsdetails" property="transTypeID"  styleClass="selectBox selectBoxSmall" onchange="javascript:calcTotalCurAmt()">
					<html:options collection="bufferMode"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
            </td>
			<td align="left" style="padding-left:5px">
	           <html:text name="claimsdetails" property="currentAmt" styleClass="textBoxSmall textBox" maxlength="13" onblur="javascript:calcTotalCurAmt()"/>&nbsp;&nbsp;&nbsp;
	        <html:hidden name="claimsdetails" property="claimSeqID" />
	        
	         <logic:match name="claimsdetails" property="tranCompleteYN" value="Y">
	           		<script language="javascript">
						disableCurrentAmt();
					</script>
	           </logic:match>
	       </td> 
	     </tr>
      </logic:iterate>
      </logic:notEmpty>
    </table>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="40%" align="right" class="textLabelBold" style="padding-right:5px;" nowrap>Total Transaction Amt. Paid (OMR):</td>
			<td width="24%" class="formLabel">
			<html:text name="frmClaimsDeposit" property="totPaidAmt" readonly="true" styleClass="textBoxDisabled textBoxSmall"/>
				<html:hidden property="totHidPaidAmt" />

			<div align="right"  class="textLabelBold" style="margin-top:-16px; ">Total Current Amt.(OMR):</div></td>
			<td width="10%">
			<html:text name="frmClaimsDeposit" property="totCurAmt" styleClass="textBoxDisabled textBoxSmall" readonly="true"/></td>
			<html:hidden property="totHidCurAmt" />
		<tr>
	</table>
 </fieldset>
    <!-- E N D : Form Fields --> 
       <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<td width="100%" align="center">
    	<logic:notEmpty name="frmClaimsDeposit" property="claimsdetails">
    		<%
			    if(TTKCommon.isAuthorized(request,"Edit"))
				{
			%>
				<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAutoFill();"><u>A</u>uto Fill</button>&nbsp;
	        	<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
				<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Reset();"><u>R</u>eset</button>&nbsp;
			<%
				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
			%>	
		</logic:notEmpty>		
				<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Close();"><u>C</u>lose</button>
			</td>
		</tr>
	</table>
<!-- E N D : Buttons -->
</div>
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<html:hidden property="debitNoteNbr" />
	<html:hidden property="caption"/>
	<logic:notEmpty name="frmClaimsDeposit" property="claimsdetails">
		<script language="javascript">
			onDocumentLoad();
		</script>
	</logic:notEmpty>
	<logic:notEmpty name="frmClaimsDeposit" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
</html:form> 
      <!-- E N D : Content/Form Area -->