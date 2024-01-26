<%
/** @ (#) billsummary.jsp
 * Project     		: TTK Healthcare Services
 * File        		: billsummary.jsp
 * Author      		: Pradeep R
 * Company     		: Span Systems Corporation
 * Date Created		: 22nd july
 *
 * @author 	   		:
 * Modified by   	:
 * Modified date 	:
 * Reason        	:
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<script language="JavaScript" src="/ttk/scripts/claims/billsummary.js"></script>

<%
	boolean viewmode=true;
  	if(TTKCommon.isAuthorized(request,"Edit"))
  	{
    	viewmode=false;
  	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
  	pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/BillSummaryAction.do">
	<!-- S T A R T : Page Title -->
  	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td>Bill Summary - <bean:write name="frmBillSummary" property="caption" /></td>
    	</tr>
  	</table>
  	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
		<html:errors/>
	
		<!-- S T A R T : Success Box -->
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
	
		<logic:notEmpty name="tariffcalculation" scope="request">
	  		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   			<tr>
	     			<td>
	     				<img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
			 			<bean:message name="tariffcalculation" scope="request"/>
					</td>
	   			</tr>
	  		</table>
	 	</logic:notEmpty>
	
		<logic:notEmpty name="requestedAmountMismatch" scope="request">
	  		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   			<tr>
	     			<td>
	     				<img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
			 			<bean:message name="requestedAmountMismatch" scope="request"/>
					</td>
	   			</tr>
	  		</table>
	 	</logic:notEmpty>
	 	<!-- E N D : Success Box -->
		<br />
	   	<!-- S T A R T : Form Fields -->
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" style="border: 1px solid #cccccc; ">
	    	<tr class="borderBottom">
	        	<td width="27%" height="20" class="textLabelBold">Bill Details </td>
	          	<td width="10%" align="center" valign="top" class="formLabel">
	          		<span class="textLabelBold">Bill Amt. <br>(Rs)</span>
	          	</td>
	          	<td width="10%" align="center" valign="top" class="textLabelBold">Discount <br>(%) </td>
	          	<td width="5%" align="center" valign="top" class="textLabelBold">Apply</td>
	          	<td width="10%" align="center" valign="top" class="textLabelBold">Net Amt.  <br>(Rs)</td>
	          	<td width="10%" align="center" valign="top" class="textLabelBold">Max. Amt. <br>(Rs) </td>
	          	<td width="28%" valign="top" class="textLabelBold">Notes</td>
			</tr>
			<logic:notEmpty name="frmBillSummary" property="billInfo">
				<bean:define id="optionsTemp" name="frmBillSummary" property="AccGroupDesc"/>	
	   			<logic:iterate id="billInfo" name="frmBillSummary" property="billInfo" >
	    			<logic:notEqual name="billInfo" property="accGroupDesc" value="<%=String.valueOf(optionsTemp) %>">
	     				<bean:define id="optionsTemp" name="billInfo" property="accGroupDesc"/>
	     				<html:hidden name="billInfo" property="accGroupDesc"/>
	       				<tr>
	          				<td class="textLabelBold"><%=String.valueOf(optionsTemp) %></td>
	            			<td align="center" class="formLabel">&nbsp;</td>
	            			<td align="center" class="textLabelBold">&nbsp;</td>
	            			<td align="center" class="textLabelBold">&nbsp;</td>
	            			<td align="center" class="textLabelBold"><span class="formLabel"></span></td>
	            			<td align="center" class="textLabelBold"><span class="formLabel"></span></td>
	            			<td class="textLabel">&nbsp;</td>
	        			</tr>
	   					<logic:iterate id="billInfo" name="frmBillSummary" property="billInfo" >
	    					<logic:equal name="billInfo" property="accGroupDesc" value="<%=String.valueOf(optionsTemp) %>">
	    						<html:hidden name="billInfo" property="accGroupDesc"/>
	        					<tr>
	          						<td class="formLabel">
	           							&nbsp;&nbsp;&nbsp;<bean:write property="wardDesc" name="billInfo" />
	           							<html:hidden name="billInfo" property="wardDesc"/>
	           						</td>				
	            					<td align="center" class="formLabel">
	              						<html:text name="billInfo" property="claimBillAmt" tabindex="-1" readonly="true" styleClass="textBoxDisabled textBoxTiny" />
	            					</td>
	            					<td align="center" class="formLabel">
	            						<span class="textLabelBold"><span class="textLabel">
	                						<html:text name="billInfo" property="discountPercent" tabindex="-1" readonly="true" styleClass="textBoxDisabled" size="4"/>
	            						</span></span>
	            					</td>
	             					<logic:notEmpty name="billInfo" property="discountPercent" >
	              						<td align="center" class="textLabelBold">
	                						<html:checkbox onclick="calNetAmt()" styleId="discountID" name="billInfo" property="applyDiscountYN" value="Y" disabled="<%=viewmode%>" />                				
	                						<input type="hidden" name="hideDiscountYN" value="<bean:write name='billInfo' property='applyDiscountYN'/>">
	                					</td>              						
	            					</logic:notEmpty>
	            					<logic:empty name="billInfo" property="discountPercent" >
	              						<td align="center" class="textLabelBold">
	                						<html:checkbox onclick="calNetAmt()" styleId="discountID" name="billInfo" property="applyDiscountYN" value="Y" disabled="true" />                				
	                						<input type="hidden" name="hideDiscountYN" value="<bean:write name='billInfo' property='applyDiscountYN'/>">
	              						</td>
	            					</logic:empty>
	            					<td align="center" class="textLabelBold">
	              						<html:text name="billInfo" property="claimNetAmount" tabindex="-1" readonly="true" styleClass="textBoxDisabled textBoxTiny" />
	            					</td>
	            					<td align="center" class="textLabelBold">
	              						<html:text name="billInfo" property="claimMaxAmount" tabindex="-1" readonly="true" styleClass="textBoxDisabled textBoxTiny" />
	            					</td>
	            					<td class="textLabel">
	            						<bean:write name="billInfo" property="billNotes"/>
	            					</td>
	        					</tr>
	            				<input type="hidden" name="selectedWardAccGroupSeqID" value="<bean:write property='wardAccGroupSeqID' name='billInfo'/>">
	            				<input type="hidden" name="selectedWardTypeID" value="<bean:write property='wardTypeID' name='billInfo'/>">
	      						<input type="hidden" name="selectedAccGrpDesc" value="<bean:write property='accGroupDesc' name='billInfo'/>">
	            				<input type="hidden" name="selectedWardDesc" value="<bean:write property='wardDesc' name='billInfo'/>">
	      						<input type="hidden" name="selectedBillAmt" value="<bean:write property='claimBillAmt' name='billInfo'/>">
	            				<input type="hidden" name="selectedDiscPer" value="<bean:write property='discountPercent' name='billInfo'/>">
	      						<input type="hidden" name="selectedNetAmt" value="<bean:write property='claimNetAmount' name='billInfo'/>">
	            				<input type="hidden" name="selectedMaxAmt" value="<bean:write property='claimMaxAmount' name='billInfo'/>">
	      						<input type="hidden" name="selectedBillNotes" value="<bean:write property='billNotes' name='billInfo'/>">
	     					</logic:equal>
	    				</logic:iterate>
	   				</logic:notEqual>
	  			</logic:iterate>
	  		</logic:notEmpty>
	  		<tr class="borderTop">
	        	<td class="textLabelBold">Total  Amt. (Rs)</td>
	            <td align="center" class="formLabel">
	            	<html:text name="frmBillSummary" property="totBillAmt" tabindex="-1" readonly="true" styleClass="textBoxDisabled textBoxTiny" maxlength="13" />
	            </td>
	            <td align="center" class="formLabel">&nbsp;</td>
	            <td align="center" class="textLabelBold">&nbsp;</td>
	            <td align="center" class="textLabelBold">
	            	<span class="formLabel">
	              		<html:text name="frmBillSummary" property="totNetAmt" tabindex="-1" readonly="true" styleClass="textBoxDisabled textBoxTiny" maxlength="13" />
	            	</span>
	            </td>
	            <td align="center" class="textLabelBold">
	            	<span class="formLabel">
	              		<html:text name="frmBillSummary" property="totMaxAmt" tabindex="-1" readonly="true" styleClass="textBoxDisabled textBoxTiny" maxlength="13" />
	            	</span>
	            </td>
	            <td align="center" class="textLabelBold">&nbsp;</td>
			</tr>
		</table>
	
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	    	<tr>
	        	<td height="20" colspan="7" class="textLabelBold">Overall Bill Split-up</td>
			</tr>
	       	<tr>
	        	<td width="18%" class="formLabel">Pre-Hospitalization (Rs) </td>
	         	<td width="14%" class="formLabel">
	           		<html:text name="frmBillSummary" property="preHospitalization" tabindex="-1" readonly="true" styleClass="textBoxDisabled textBoxTiny" />
	         	</td>
	         	<td width="15%" class="formLabel">Hospitalization (Rs) </td>
	         	<td width="13%" class="formLabel">
	           		<html:text name="frmBillSummary" property="hospitalization" tabindex="-1" readonly="true" styleClass="textBoxDisabled textBoxTiny" />
	         	</td>
	         	<td width="19%" class="formLabel">Post-Hospitalization (Rs) </td>
	         	<td width="11%" class="formLabel">
	           		<html:text name="frmBillSummary" property="postHospitalization" tabindex="-1" readonly="true" styleClass="textBoxDisabled textBoxTiny" />
	         	</td>
	         	<td width="10%" class="formLabel">&nbsp;</td>
			</tr>
		</table>
	
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	    	<tr>
	        	<td width="20%" class="formLabel">&nbsp;</td>
	         	<td width="30%" class="formLabel">&nbsp;</td>
	         	<td width="20%" class="formLabel">&nbsp;</td>
	         	<td width="30%" class="formLabel">&nbsp;</td>
			</tr>
		</table>
	
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" style="border: 1px solid #cccccc; ">
	    	<tr class="borderBottom">
	        	<td width="27%" height="20" class="textLabelBold">Ailment(s)</td>
	         	<td width="10%" align="center" valign="top" class="textLabelBold">&nbsp;</td>
	         	<td width="10%" align="center" valign="top" class="textLabelBold">&nbsp;</td>
	         	<td width="5%" align="center" valign="top" class="textLabelBold">&nbsp;</td>
	         	<td width="10%" align="center" valign="top" class="textLabelBold">Appr. Amt. <br>(Rs) </td>
	         	<td width="10%" align="center" valign="top" class="textLabelBold">Pkg. Amt.<br>(Rs) </td>
	         	<td width="28%" valign="top" class="textLabelBold">Notes</td>
			</tr>
			<logic:iterate id="ailmentInfo" indexId="i" name="frmBillSummary" property="ailmentInfo" >
	        	<tr style="background:#DDDDDD;">
	         		<td class="formLabel">
	         			<bean:write name="ailmentInfo" property="description"/>
	         			<html:hidden name="ailmentInfo" property="description"/>
	         		</td>
	         		<td align="center" class="formLabel">&nbsp;</td>
	         		<td align="center" class="formLabel">&nbsp;</td>
	         		<td align="center" class="formLabel">&nbsp;</td>
	         		<td align="center" class="formLabel">
	         			<html:text styleId="<%="approvedAilmentAmt"+i%>" name="ailmentInfo" property="approvedAilmentAmt" disabled="<%= viewmode %>" styleClass="textBox textBoxTiny" maxlength="13" onblur="javascript:calcAilAppAmt()" />
	         		</td>
	         		<td align="center" class="formLabel">
	         			<html:text name="ailmentInfo" property="maxAilmentAllowedAmt" tabindex="-1" readonly="true" disabled="<%= viewmode %>" styleClass="textBoxDisabled textBoxTiny" maxlength="13" />
	         		</td>
	         		<td class="textLabel">
	         			<bean:write name="ailmentInfo" property="ailmentNotes"/>
	         		</td>
	       		</tr>
				<logic:notEmpty name="ailmentInfo" property="procedureList">
					<logic:iterate id="ailmentInfolist" indexId="j" name="ailmentInfo" property="procedureList">
						<tr> 
					    	<td class="formLabel">&nbsp;&nbsp;
					        	<bean:write name="ailmentInfolist" property="procDesc" />
					            <input type="hidden" name="procDesc" value="<bean:write name="ailmentInfolist" property="procDesc" />">
					            <input type="hidden" name="procSeqID" value="<bean:write name="ailmentInfolist" property="procSeqID" />">
						        <input type="hidden" name="patProcSeqID" value="<bean:write name="ailmentInfolist" property="patProcSeqID" />">
						        <input type="hidden" name="asscICDPCSSeqID" value="<bean:write name="ailmentInfo" property="ICDPCSSeqID" />">
							</td>
				            <td align="center" class="formLabel">&nbsp;</td>
				            <td align="center" class="formLabel">&nbsp;</td>
				            <td align="center" class="formLabel">&nbsp;</td>
				            <td align="center" class="formLabel">
					            <html:text styleId="<%="procedureAmt"+i+'~'+j%>" name="ailmentInfolist" property="procedureAmt" disabled="<%= viewmode %>" styleClass="textBox textBoxTiny" maxlength="13" indexed="ture" onblur="javascript:calcAilAppAmt()"/>
					        </td>
				            <td align="center" class="formLabel">&nbsp;</td>
						</tr>				          
						<input type="hidden" name="selectedPCSSeqID" value="<bean:write name='ailmentInfo' property='ICDPCSSeqID'/>">
					</logic:iterate>
				</logic:notEmpty>  
	        	<input type="hidden" name="selectedAilmentCapsSeqID" value="<bean:write  name='ailmentInfo' property='ailmentCapsSeqID'/>">
	        	<input type="hidden" name="selectedICDPCSSeqID" value="<bean:write name='ailmentInfo' property='ICDPCSSeqID'/>">
	        	<input type="hidden" name="selectedAilmentNotes" value="<bean:write name='ailmentInfo' property='ailmentNotes'/>">
	        	<input type="hidden" name="selectedDescription" value="<bean:write name='ailmentInfo' property='description'/>">
			</logic:iterate>
			<html:hidden name="frmBillSummary" property="totSumNetAmt"/>
		</table>
	  	<!-- S T A R T : Buttons -->
	  	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
			<tr>
	      		<td width="100%" align="center">
	      			<button type="button" name="Button2" accesskey="v" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onViewBillDetails()"><u>V</u>iew Bill Detail</button>
	      			&nbsp;
			       	<logic:match name="viewmode" value="false">
	      				<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>
	      			</logic:match>
	      		</td>
	    	</tr>
	  	</table>
	  	<!-- E N D : Buttons -->
	</div>
	<input type="hidden" name="mode" value="">
	<logic:notEmpty name="frmBillSummary" property="billInfo">
		<script language="javascript">
	    	calNetAmt();
	      	calTotBill();
	      	calTotMax();
	      	calTotNet();
	      	calToBillSplitUp();
	  	</script>
	</logic:notEmpty>
	
	<input type="hidden" name="selectedClaimSeqID" value="<bean:write property='claimSeqID' name='frmBillSummary'/>">
	<input type="hidden" name="child" value="Bills Summary">
</html:form>
<!-- E N D : Content/Form Area -->