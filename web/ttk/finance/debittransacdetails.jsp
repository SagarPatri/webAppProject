<%
/**
 * @ (#) debittransacdetails.jsp August 28th, 2007
 * Project      : TTK HealthCare Services
 * File         : debittransacdetails.jsp
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
<script language="javascript" src="/ttk/scripts/finance/debittransacdetails.js"></script>
<%
	pageContext.setAttribute("bufferMode",Cache.getCacheObject("bufferMode"));
	boolean viewmode=true;
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>
<html:form action="/DebitNoteDepositAction.do"> 
<!-- S T A R T : Page Title --> 
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td width="57%"><bean:write name="frmDebitNote" property="caption"/></td> 
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
		<fieldset><legend>List of Debit Notes</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td width="10%" align="left" nowrap class="formLabel">Transaction Amt.(OMR):</td>
				<td width="80%" align="left" nowrap>
				<html:text name="frmDebitNote" property="transAmt" readonly="true" styleClass="textBoxDisabled textBoxSmall" /></td>
			</tr>
		</table>
		<BR/>
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="gridWithCheckBox zeroMargin">
          <tr valign="middle"> 
            <td width="12%"  class="gridHeader" style="height:30px;padding-left:5px">Debit Note No</td> 
            <td width="12%"  class="gridHeader" style="padding-left:5px">Debit Note Amount (OMR)</td>
			<td width="12%"  class="gridHeader" style="padding-left:5px">Total Deposited Amount (OMR)</td>
            <td width="14%"  class="gridHeader" style="padding-left:5px">Deposited Amount (OMR)</td> 
            <td width="13%" class="gridHeader" style="padding-left:5px">Pending Amount (OMR)</td> 
            <td width="8%" class="gridHeader" style="padding-left:5px">Trans Type</td>  
            <td width="13%" class="gridHeader" style="padding-left:5px">Current Amount (OMR) </td> 
          </tr>
		<logic:empty name="frmDebitNote" property="debitnote">
			<tr>
				<td class="generalcontent" colspan="7">&nbsp;&nbsp;No Data Found</td>
			</tr>
		</logic:empty>
		<logic:notEmpty name="frmDebitNote" property="debitnote">
	     
	      <logic:iterate id="debitnote" indexId="i" name="frmDebitNote" property="debitnote">
	      <html:hidden name="debitnote" property="debitNoteSeqID" />
	      <html:hidden name="debitnote" property="debitNoteTransSeqID" />
	      <html:hidden name="debitnote" property="debitNoteNbr" />
	      <input type="hidden" name="tranCompleteYN" value="<bean:write property='tranCompleteYN' name='debitnote'/>">
	      
	      <tr class="<%=(i.intValue()%2==0)? "gridOddRow":"gridEvenRow"%>">
	      	<td><bean:write name="debitnote" property="debitNoteNbr"/>
	      		<html:hidden name="debitnote" property="debitNoteNbr"/>
	      	</td>
	      	<td><bean:write name="debitnote" property="debitNoteAmt"/>
	      		<html:hidden name="debitnote" property="debitNoteAmt"/>
	      	</td>
	      	<td><bean:write name="debitnote" property="totalDepositAmt"/>
	      		<html:hidden name="debitnote" property="totalDepositAmt"/>
	      	</td>
	      	<td><bean:write name="debitnote" property="depositedAmt"/>
	      		<html:hidden name="debitnote" property="depositedAmt"/>
	      	</td>
	      	<td><bean:write name="debitnote" property="pendingAmt"/>
	      		<html:hidden name="debitnote" property="pendingAmt"/>
	      	</td>
	      	<td align="right" style="padding-left:5px">
		      	<html:select name="debitnote" property="transTypeID"  styleClass="selectBox selectBoxSmall" onchange="javascript:calcTotalCurAmt()">
					<html:options collection="bufferMode"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
            </td>
			<td align="left" style="padding-left:5px">
	           <html:text name="debitnote" property="currentAmt" styleClass="textBoxSmall textBox"  maxlength="13" onblur="javascript:calcTotalCurAmt()"/>&nbsp;&nbsp;&nbsp;
	          
	           <logic:notEmpty name="debitnote" property="debitNoteTransSeqID">
	           <a href="#" onclick="javascript:onAssociateIcon(<%=i %>);"><img src="ttk/images/EditIcon.gif" alt="Associate claims to deposit" width="16" height="16" border="0" align="absmiddle"></a>
	           </logic:notEmpty>
	           <logic:match name="debitnote" property="tranCompleteYN" value="Y">
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
			<td width="35%" align="right" class="textLabelBold" style="padding-right:5px;" nowrap>Total Transaction Deposited Amt.(OMR):</td>
			<td width="38%" class="formLabel">
			<html:text name="frmDebitNote" property="totDepAmt" readonly="true" styleClass="textBoxDisabled textBoxSmall"/>
				<html:hidden property="totHidDepAmt" />

			<div align="right" class="textLabelBold" style="margin-top:-16px; ">Total Current Amt.(OMR):</div></td>
			<td width="13%">
			<html:text name="frmDebitNote" property="totCurAmt" styleClass="textBoxDisabled textBoxSmall" readonly="true"/></td>
			<html:hidden property="totHidCurAmt" />
		<tr>
	</table>
 </fieldset>
    <!-- E N D : Form Fields --> 
       <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<td width="100%" align="center">
    	<logic:notEmpty name="frmDebitNote" property="debitnote">
    		<%
			    if(TTKCommon.isAuthorized(request,"Edit"))
				{
			%>
	        	<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
				<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Reset()"><u>R</u>eset</button>&nbsp;
			<%
				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
			%>	
		 </logic:notEmpty>			
				<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Close()"><u>C</u>lose</button>
			</td>
		</tr>
	</table>
<!-- E N D : Buttons -->
</div>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<html:hidden property="caption"/>
	<html:hidden property="rownum"/>
	
	<logic:notEmpty name="frmDebitNote" property="debitnote">
		<script language="javascript">
			onDocumentLoad();
		</script>
	</logic:notEmpty>
	<logic:notEmpty name="frmDebitNote" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
</html:form> 
      <!-- E N D : Content/Form Area -->