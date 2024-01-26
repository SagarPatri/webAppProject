<%
/** @ (#) floataccdetails.jsp Jun 10, 2006
 * Project    	 : TTK Healthcare Services
 * File       	 : floataccdetails.jsp
 * Author     	 : Raghavendra T M
 * Company    	 : Span Systems Corporation
 * Date Created	 : Jun 10, 2006
 * @author 		 : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/finance/floataccdetails.js"></script>

<%
	String strLink=TTKCommon.getActiveLink(request);
	String style = "textBox textDate textBoxDisabled";
	boolean viewmode=true;
	boolean disablemode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode = false;
		disablemode = false;
		
	}

	pageContext.setAttribute("strLink",strLink);
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	pageContext.setAttribute("disablemode",new Boolean(disablemode));
	pageContext.setAttribute("floatAccType",Cache.getCacheObject("floatType"));
	pageContext.setAttribute("acctStatus",Cache.getCacheObject("acctStatus"));
	pageContext.setAttribute("productTypeCode",Cache.getCacheObject("productTypeCode"));
%>
	<!-- S T A R T : Content/Form Area -->
<html:form action="/FloatAccAction.do">
	<logic:notEmpty name="frmFloatAccDetails" property="floatAcctSeqID">
    	<logic:match name="frmFloatAccDetails" property="transactionYN" value="N">
			<logic:match name="frmFloatAccDetails" property="status" value="ASC">
				<% viewmode=true; %>
		   	</logic:match>
			<logic:notMatch name="frmFloatAccDetails" property="status" value="ASC">
				<% viewmode=false; %>
		   	</logic:notMatch>
		</logic:match>
		<logic:notMatch name="frmFloatAccDetails" property="transactionYN" value="N">
			<% viewmode=true; %>
		</logic:notMatch>
	</logic:notEmpty>
    <logic:empty name="frmFloatAccDetails" property="floatAcctSeqID">
		<% viewmode=false; %>
	</logic:empty>
	<logic:notMatch name="disablemode" value="false">
		<% viewmode=true; %>
	</logic:notMatch>
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td width="57%">
    			Float Account Details 
    			<bean:write property="caption" name="frmFloatAccDetails"/> 
    		</td>
    		<td width="43%" align="right" class="webBoard">
				<logic:notEmpty name="frmFloatAccDetails" property="floatAcctSeqID">
					<logic:match name="frmFloatAccDetails" property="floatType" value="FTD">
						<logic:match name="frmFloatAccDetails" property="statusDesc" value="ASA">
            				<a href="#" onClick="javascript:onSelectDebitNote()"><img src="ttk/images/EditIcon.gif" alt="Select Debit Note Details" width="16" height="16" border="0" align="absmiddle" class="icons"></a>&nbsp;&nbsp;<img src="ttk/images/IconSeparator.gif" width="1" height="15" align="absmiddle" class="icons">&nbsp;
            			</logic:match>
            		</logic:match>
            		<%@ include file="/ttk/common/toolbar.jsp" %>
        		</logic:notEmpty >
    		</td>
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
    	<!-- S T A R T : Form Fields -->
		<fieldset>
    		<legend>General</legend>
    		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      			<tr>
        			<td width="20%" class="formLabel">Float Type:</td>
        			<td width="33%">
        				<html:select property="floatType" name="frmFloatAccDetails" styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>" onchange="showhidefloattype()">
	       					<html:options collection="floatAccType" property="cacheId" labelProperty="cacheDesc"/>
						</html:select>
        			</td>
        			<td class="formLabel">Float No.:</td>
        			<td class="textLabelBold"><bean:write property="floatNo" name="frmFloatAccDetails"/></td>
      			</tr>
      			<tr>
        			<td class="formLabel">Float Name: <span class="mandatorySymbol">*</span></td>
        			<td><html:text property="floatAcctName" name="frmFloatAccDetails"  onkeypress="ConvertToUpperCase(event.srcElement);" styleClass="textBox textBoxLarge" maxlength="60"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        			<td class="formLabel">Status: <span class="mandatorySymbol">*</span></td>
        			<td>
        				<logic:notEmpty name="frmFloatAccDetails" property="floatAcctSeqID">
           					<logic:match name="frmFloatAccDetails" property="status" value="ASC">
                				<html:select property="statusDesc" styleClass="selectBoxMedium selectBoxDisabled" disabled="true">
                					<html:optionsCollection name="acctStatus" label="cacheDesc" value="cacheId" />
                				</html:select>
           					</logic:match>
           					<logic:notMatch name="frmFloatAccDetails" property="status" value="ASC">
                				<html:select property="statusDesc" styleClass="selectBox selectBoxMedium" onchange="showhidestatus();">
                					<html:option value="">Select from list</html:option>
                					<html:optionsCollection name="acctStatus" label="cacheDesc" value="cacheId" />
                				</html:select>
           					</logic:notMatch>
        				</logic:notEmpty>
						<logic:empty name="frmFloatAccDetails" property="floatAcctSeqID">
	       					<html:select property="statusDesc" styleClass="selectBoxMedium selectBoxDisabled" disabled="true" >
		   						<html:optionsCollection name="acctStatus" label="cacheDesc" value="cacheId"/>
		   					</html:select>
						</logic:empty>
        			</td>
      			</tr>
	  			<tr>
        			<td class="formLabel">Created Date:</td>
        			<td><html:text property="createdDate" styleClass="textBox textDate textBoxDisabled" maxlength="8" disabled="<%=viewmode%>" readonly="true"/></td>
        			<logic:notEmpty name="frmFloatAccDetails" property="floatAcctSeqID">
          				<logic:match name="frmFloatAccDetails" property="status" value="ASC">
	        				<td class="formLabel">Closed Date: <span class="mandatorySymbol">*</span></td>
	        				<td><html:text property="closedDate" styleClass="textBox textDate textBoxDisabled" maxlength="10" disabled="<%=viewmode%>" readonly="true"/></td>
		  				</logic:match>
		  				<logic:notMatch name="frmFloatAccDetails" property="status" value="ASC">
		  					<logic:match name="frmFloatAccDetails" property="statusDesc" value="ASC">
			  					<td class="formLabel" id="closedDate" style="display">Closed Date: <span class="mandatorySymbol">*</span></td>
		        				<td id="clsDate" style="display">
			        				<html:text property="closedDate" styleClass="textBox textDate" maxlength="10"/>
									<a name="CalendarObjectClDate" id="CalendarObjectClDate" href="#" onClick="javascript:show_calendar('CalendarObjectClDate','forms[1].closedDate',document.forms[1].closedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a>
			    				</td>
		    				</logic:match>
		    				<logic:notMatch name="frmFloatAccDetails" property="statusDesc" value="ASC">
					  			<td class="formLabel" id="closedDate" style="display:none;">Closed Date: <span class="mandatorySymbol">*</span></td>
				        		<td id="clsDate" style="display:none;">
					        		<html:text property="closedDate" styleClass="textBox textDate" maxlength="10"/>
									<a name="CalendarObjectClDate" id="CalendarObjectClDate" href="#" onClick="javascript:show_calendar('CalendarObjectClDate','forms[1].closedDate',document.forms[1].closedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a>
					    		</td>
				    		</logic:notMatch>
		  				</logic:notMatch>
	   				</logic:notEmpty>
	   				
	   				<td class="formLabel">Direct Billing:</td>	 				
	  				<td>	  					
	 					<html:checkbox property="directBillingYN" styleId="directBillingYN"/>
	 				</td>	
	   				
	   				
      			</tr>
    		</table>
		</fieldset>
		<fieldset>
			<legend>Bank Details</legend>
			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	  			<tr>
        			<td height="20" class="formLabel">Account No.: <span class="mandatorySymbol">*</span></td>
        			<td class="textLabelBold"><bean:write property="accountNO" name="frmFloatAccDetails"/></td>
        			<td class="formLabel">Account Name:</td>
        			<td width="30%" class="textLabelBold">
        				<bean:write property="accountName" name="frmFloatAccDetails"/>&nbsp;&nbsp;&nbsp;
        				<logic:notEmpty name="frmFloatAccDetails" property="floatAcctSeqID">
	        				<logic:match name="frmFloatAccDetails" property="transactionYN" value="N">
	        					<logic:notMatch name="frmFloatAccDetails" property="status" value="ASC">
	        						<logic:match name="disablemode" value="false">
	        							<a href="#" onClick="javascript:selectBankAcc();"><img src="/ttk/images/EditIcon.gif" alt="Select Bank Account" width="16" height="16" border="0" align="absmiddle"></a>
	        						</logic:match>
	        					</logic:notMatch>
	        				</logic:match>
        				</logic:notEmpty>
        				<logic:empty name="frmFloatAccDetails" property="floatAcctSeqID">
        					<logic:match name="disablemode" value="false">
        						<a href="#" onClick="javascript:selectBankAcc();"><img src="/ttk/images/EditIcon.gif" alt="Select Bank Account" width="16" height="16" border="0" align="absmiddle"></a>
        					</logic:match>	        				
        				</logic:empty>
        			</td>
	  			</tr>
	  			<tr>
        			<td width="20%" height="20" class="formLabel">Bank Name:</td>
        			<td width="33%" class="textLabelBold"><bean:write property="bankName" name="frmFloatAccDetails"/> </td>
        			<td width="19%" class="formLabel">Office Type:</td>
        			<td width="30%" class="textLabelBold"><bean:write property="officeTypeDesc" name="frmFloatAccDetails"/></td>
	    		</tr>
	  			<tr>
	    			<td height="20" class="formLabel">Branch Location: </td>
	    			<td class="textLabelBold"><bean:write property="branchName" name="frmFloatAccDetails"/></td>
	    			<td class="formLabel">&nbsp;</td>
	    			<td class="textLabelBold">&nbsp;</td>
	    		</tr>
    		</table>
		</fieldset>
		<fieldset>
    		<legend>Insurance Company</legend>
    		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      			<tr>
        			<td width="20%" class="formLabel">Insurance Company: <span class="mandatorySymbol">*</span></td>
        			<td width="33%" class="textLabelBold"><bean:write property="insComp" name="frmFloatAccDetails"/></td>
        			<td width="19%" class="formLabel">Insurance Code:</td>
        			<td width="33%" class="textLabelBold">
        				<bean:write property="insCompCode"  name="frmFloatAccDetails"/>&nbsp;&nbsp;&nbsp;
        				<logic:notEmpty name="frmFloatAccDetails" property="floatAcctSeqID">
	        				<logic:match name="frmFloatAccDetails" property="transactionYN" value="N">
	        					<logic:notMatch name="frmFloatAccDetails" property="status" value="ASC">
	        						<logic:match name="disablemode" value="false">
        								<a href="#" onClick="javascript:changeOffice();"><img src="/ttk/images/EditIcon.gif" alt="Select Insurance Company" width="16" height="16" border="0" align="absmiddle"></a>
        							</logic:match>	        			
        						</logic:notMatch>
    						</logic:match>
    					</logic:notEmpty>
        				<logic:empty name="frmFloatAccDetails" property="floatAcctSeqID">
        					<logic:match name="disablemode" value="false">
        						<a href="#" onClick="javascript:changeOffice();"><img src="/ttk/images/EditIcon.gif" alt="Select Insurance Company" width="16" height="16" border="0" align="absmiddle"></a>
        					</logic:match>        				
        				</logic:empty>
					</td>
					
      			</tr>
      			<tr>
        			<%-- <td width="20%" class="formLabel">Office Type:</td>
        			<td width="33%" class="textLabelBold"><bean:write property="insOfficeType" name="frmFloatAccDetails"/></td>
        	 --%>		<td width="19%" class="formLabel">Branch Location:</td>
        			<td width="30%" class="textLabelBold"><bean:write property="insTtkBranch" name="frmFloatAccDetails"/></td>
        			
        			<!-- AK -->
        			<td width="19%" class="formLabel">Insurance Authority:</td>
        			<td width="30%" class="textLabelBold"> <bean:write property="payerAuthority" name="frmFloatAccDetails"/></td>
      			</tr>
      			<tr>
      			<td width="20%" class="formLabel">Office Type:</td>
        			<td width="33%" class="textLabelBold"><bean:write property="insOfficeType" name="frmFloatAccDetails"/></td>
      			</tr>
    		</table>
		</fieldset>
		<fieldset>
    		<legend>Product Information</legend>
    		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
    			<tr>
    				<td width="20%" class="formLabel">Product Type: </td>
        			<td width="80%">
        				<html:select property="productTypeCode" name="frmFloatAccDetails" styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>">
	       					<html:option value="">Select from list</html:option>
	       					<html:options collection="productTypeCode" property="cacheId" labelProperty="cacheDesc"/>
						</html:select>
					</td>
				</tr>	
				
				<tr>
					<td width="20%" class="formLabel">Selected Products: </td>
					<td> 	<html:text property="selectedProduct" styleId="selectedProduct" name="frmFloatAccDetails"  styleClass="textBox textBoxLarge" /> 
							<a href="#" onClick="javascript:showInsuranceProducts();"><img src="/ttk/images/EditIcon.gif" alt="Select Insurance Products" width="16" height="16" border="0" align="absmiddle"></a>
        			</td>
    			</tr>
    		</table>
    	</fieldset>
    	
    	
    	<fieldset>
    		<legend>Administration Category</legend>
    		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
    			
    			<tr>
    				<td width="20%" class="formLabel">Select Category Type: </td>
        			<td width="80%">
        			<html:select  property="adminCategoryType" styleClass="selectBox selectBoxMedium" >
					        	<html:option value="">Select from list</html:option>
					        	<html:option value="ASP">AS+</html:option>
					        	<html:option value="ASO">ASO</html:option>
					        	<html:option value="BOTH">Both</html:option>
					       </html:select>
					</td>
        		</tr>	
    			
    			
    		</table>
    		</fieldset>	
    	
    	<fieldset>
    		<legend>Claim Type </legend>
    		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
    			
    			<tr>
					<td width="20%" class="formLabel">Select Claim Type: </td>
					<td> 	<html:text property="networkType" styleId="networkType" name="frmFloatAccDetails"  styleClass="textBox textBoxLarge" /> 
							<a href="#" onClick="javascript:selectNetwork()"><img src="/ttk/images/EditIcon.gif" alt="Select claim Type" width="16" height="16" border="0" align="absmiddle"></a>
        			</td>
    			</tr>
     		</table>
    	</fieldset>
    	
    	
    	<fieldset>
    		<legend>Encounter Type </legend>
    		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
    		
    		<tr>
    				<td width="20%" class="formLabel" >Select Encounter Type: </td>
        			<td width="80%">
        				<html:select  property="encounterType" styleClass="selectBox selectBoxMedium" >
					        	<html:option value="">Select from list</html:option>
					        	<html:option value="IP">IP only</html:option>
					        	<html:option value="OP">OP only</html:option>
					        	<html:option value="BOTH">Both</html:option>
					       </html:select>
					</td>
        	</tr>	
				
		
    		</table>
    	</fieldset>
    	
    	
    	<fieldset>
    		<legend>Policy Type </legend>
    		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
    		
    		<tr>
    				<td width="20%" class="formLabel" >Select Policy Type: </td>
        			<td width="80%">
        				<html:select  property="policyType" styleClass="selectBox selectBoxMedium" >
					        	<html:option value="">Select from list</html:option>
					        	<html:option value="IND">Individual</html:option>
					        	<html:option value="CORP">Corporate</html:option>
					        	<html:option value="BOTH">Both</html:option>
					       </html:select>
					</td>
        	</tr>	
				
		
    		</table>
    	</fieldset>
    	
    	
		<fieldset>
			<legend>Account Details</legend>
			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      			<tr>
        			<td width="20%">Current Bal. : </td>
        			<td width="33%" class="formLabelBold"><bean:write property="currentBalance" name="frmFloatAccDetails"/></td>        			        			        		
        			<td width="19%" class="formLabel" id="regular1">Minimum Account Balance : <span class="mandatorySymbol">*</span></td>
        			<td width="30%" id="regular2">
        				<html:text property="establishAmt" name="frmFloatAccDetails" styleClass="textBox textBoxSmall" maxlength="13"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        			</td>        				
        			<td width="19%" id="debit1">&nbsp;</td>
        			<td width="30%" id="debit2">&nbsp;</td>        				        			        		
      			</tr>
    		</table>    						
		</fieldset>
		<fieldset><legend>Others</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      			<tr>
        			<td width="20%" class="formLabel">Remarks / Reason for Account Opening : <span class="mandatorySymbol">*</span></td>
        			<td width="80%" colspan="3">
        				<html:textarea property="remarks" name="frmFloatAccDetails" styleClass="textBox textAreaLong" disabled="<%=disablemode%>" readonly="<%=disablemode%>"/>
        			</td>
      			</tr>
    		</table>
		</fieldset>
		<fieldset>	
    		<legend>
    			Policy Information&nbsp;
    			<logic:notEmpty name="frmFloatAccDetails" property="floatAcctSeqID">
    				<a href="#" onClick="javascript:onAssociateCorp()">
    					<img src="ttk/images/EditIcon.gif" alt="Group Association" width="16" height="16" border="0" align="absmiddle" class="icons">
    				</a>
    			</logic:notEmpty>
    			<logic:empty name="frmFloatAccDetails" property="floatAcctSeqID">
    				<img src="ttk/images/EditIcon.gif" alt="Group Association" width="16" height="16" border="0" align="absmiddle" class="icons">
    			</logic:empty>
    		</legend>    
			<!-- S T A R T : Grid -->
    		<div class="scrollableGrid" style="height:90px;">
    			<table align="left" class="gridWithCheckBox"  border="0" cellspacing="0" cellpadding="0" style="margin:0px 0px 0px 10px;">
    				<tr>
        				<td width="20%" class="gridHeader">Policy No</td>
        	        	<td width="20%" class="gridHeader">Policy Category</td>
        				<td width="20%" class="gridHeader">Group Id</td>
        				<td width="80%" class="gridHeader">Corporate Name</td>		
      				</tr>      	
      				<% 
      					int i=0; 
      				%>
		        	<logic:iterate id="assocGroupVO" name="frmFloatAccDetails" property="alAssocGrpList">
					<%
						String strClass=i%2==0 ? "gridOddRow" : "gridEvenRow" ;
						i++;
					%>
						<tr class="<%=strClass%>">
							<td><bean:write name="assocGroupVO" property="policyNo" /></td>
					    	 <td><bean:write name="assocGroupVO" property="policyCategory" /></td>
				    		<td><bean:write name="assocGroupVO" property="groupID" /></td>
				        	<td><bean:write name="assocGroupVO" property="groupName" /></td>				        				        				       
						</tr>
					</logic:iterate>
				</table>
	    	</div>
    		<!-- E N D : Grid -->
		</fieldset>
		<!-- E N D : Form Fields -->
		<!-- S T A R T : Buttons and Page Counter -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      		<tr>
        		<td width="100%" align="center">
		   		<%
		    		if(TTKCommon.isAuthorized(request,"Edit"))
		    		{
		   		%>
		    			<button type="button" name="Button2" accesskey="s"
						class="buttons" onMouseout="this.className='buttons'"
						onMouseover="this.className='buttons buttonsHover'"
						onClick="onSave()">
						<u>S</u>ave
					</button>&nbsp;
     					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
		   		<%
		   			}
		   		%>
		   		</td>
		   	</tr>
		</table>
   	</div>	
	<logic:notEmpty name="frmFloatAccDetails" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT type="hidden" name="mode" value="">
	<input type="hidden" name="child" value="">
	<html:hidden property="groupName"/>
	<html:hidden property="groupID"/>
	<html:hidden property="transactionYN"/>
	<html:hidden property="editmode"/>
	<html:hidden property="floatAcctSeqID"/>
	<html:hidden property="groupRegnSeqID"/>
	<html:hidden property="insSeqID"/>
	<html:hidden property="directBilling"/>
	<!-- E N D : Main Container Table -->
	<script>
		showhidefloattype();
		oncheckDirectbilling();
	</script>
</html:form>