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
	<%@ page
		import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.business.common.SecurityManagerBean,com.ttk.dto.usermanagement.UserSecurityProfile"%>
	<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
	<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
	<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
	<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
	<%@page
		import="java.util.ArrayList,com.ttk.dto.insurancepricing.PolicyConfigVO"%>


	<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
	<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
	<script type="text/javascript"
		src="/ttk/scripts/insurancepricing/swpolicydesignconfig.js"></script>
	<script type="text/javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
	<style>
.textBoxpricing {
	border: 1px solid #000000;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	color: #FF0000;
	height: 14px;
}

.gridWithPricing1 {
	border-top: 1px solid #A0A8BB;
	border-right: 1px solid #A0A8BB;
	border-bottom: 1px solid #A0A8BB;
	width: 60%;
	margin-top: 10px;
	text-align: left;
}

.buttonsContainerGrid1 {
	/*   border-top: 1px solid #CCCCCC; */
	background-color: #FFFFFF;
	margin-top: 20px;
	padding: 5px 5px 5px 5px;
	width: 98%;
}

.buttonsPricingSmall {
	background-color: #677BA8;
	color: #FFFFFF;
	height: 19px;
	border: 1px solid #555555;
	font-size: 11px;
	padding-left: 5px;
	padding-right: 5px;
	width: 100px;
}

.buttonsPricingLarge {
	background-color: #677BA8;
	color: #FFFFFF;
	height: 19px;
	border: 1px solid #555555;
	font-size: 11px;
	padding-left: 5px;
	padding-right: 5px;
	width: 150px;
}

.textBoxVerySmall {
	width: 40px;
}

.gridOddRow {
	background-color: #FFFFFF;
	height: 20px;
}

.gridEvenRow {
	background-color: #E6E9ED;
	height: 20px;
}
</style>
	<SCRIPT LANGUAGE="JavaScript">
	bAction = false; //to avoid change in web board in product list screen //to clarify
	var TC_Disabled = true;
	
	 function onLoadValidation()
	{
		 
			var csr1Lnth = document.getElementById("csr1Lnth").value;
			for(i=0;i<csr1Lnth;i++){
				 var generalTypeId = document.getElementById("generalTypeId"+i).value; 
				 var riskLoadID = parseFloat(document.getElementById("riskLoadID"+i).value);
				 if(generalTypeId == "MGD"){
					 
					 if(riskLoadID > 0){
	        			 $(".spanComm").removeAttr("style");
	        		 }else{
	        			 
	        			 $(".spanComm").attr("style","display:none");
	        			 
	        		 }
						
				 }
				 
	 /* if(generalTypeId == "TPF"){
					 
		 document.getElementById("riskLoadID"+i).value=7.5;
		 document.getElementById("riskLoadID"+i).disabled=true;
		 document.getElementById("riskLoadID"+i).readOnly="true";
		 document.getElementById("riskLoadID"+i).style.backgroundColor ="#d9dce0";
				
	 } */
				
			}
		
		
		
	} 
	
	function onSaveRiskPremium()
	{
		$('.disabledOption').removeAttr("disabled");
		var csr1Lnth = document.getElementById("csr1Lnth").value;
		for(i=0;i<csr1Lnth;i++){
			 var generalTypeId = document.getElementById("generalTypeId"+i).value; 
			 var riskLoadID = parseFloat(document.getElementById("riskLoadID"+i).value);
			 if(generalTypeId == "MGD" && riskLoadID > 0){
				 var comments = document.getElementById("comments").value;
				if(comments == "" || comments == null || comments == undefined){
					
					alert("Please specify the comments");
					return false;
				}
				 
				 break;
			 }
			
		}	
	
		document.forms[1].mode.value = "doSaveRiskPremium";
	    document.forms[1].action = "/SwGrossPremiumCalculate.do";
	    document.forms[1].submit();
	}//end of onClose()
	
	function onSaveGrossPremium()
	{
		document.forms[1].mode.value = "doSaveGrossPremium";
	    document.forms[1].action = "/SwGrossPremiumCalculate.do";
	    document.forms[1].submit();
	}//end of onClose()
	
	function isNumericPT(field) {
        var re = /^[0-9]*\.*[0-9]*$/;
        if (!re.test(field.value)) {
            alert("Data entered must be Numeric!");
			field.focus();
			field.value="";
			return false;
        }
        
    }
	
	function checkValid(val1, val2){

		 
		 var generalTypeId = document.getElementById("generalTypeId"+val2).value; 
		 var riskLoadID = parseFloat(document.getElementById("riskLoadID"+val2).value);
		 
	      var regexp =/^\d+(?:\.\d{1,1})?$/;

	        
	         if(!regexp.test(riskLoadID))
	        {
	        	    alert(" Only 1 decimal place allowed ");
	    	        document.getElementById("riskLoadID"+val2).focus();
	    	        document.getElementById("riskLoadID"+val2).value="";
	    			return false;
	        }
	      
		 if(generalTypeId == "PFM"){
			 
			 if(riskLoadID > 200){
				 
				 alert("'Profit Margin' cannot exceed 200%");
				 document.getElementById("riskLoadID"+val2).value="";
				 return false;
				 
			 }
			
			 
		 }
        
        if(generalTypeId == "TPF"){
        	
       	 /* document.getElementById("riskLoadID"+val2).value=7.5;
		 document.getElementById("riskLoadID"+val2).disabled=true;
		 document.getElementById("riskLoadID"+val2).readOnly="true";
		 document.getElementById("riskLoadID"+val2).style.backgroundColor ="#d9dce0"; */
        	
        	 if(riskLoadID > 50){
			 alert("'TPA Fee' cannot exceed 50%");
			 document.getElementById("riskLoadID"+val2).value="";
			 return false;
        	 }
			 
		 }
        
        if(generalTypeId == "CONM"){
        	
        	 if(riskLoadID > 100){
			 alert("'Contingency Margin' cannot exceed 100%");
			 document.getElementById("riskLoadID"+val2).value="";
			 return false;
        	 }
			 
		 }
        
        if(generalTypeId == "MGD"){
			 
        	 if(riskLoadID > 50){
			 alert("'Management Discount' cannot exceed 50%");
			 document.getElementById("riskLoadID"+val2).value="";
			 return false;
        	 }
        	 else{
        		 if(riskLoadID > 0){
        			 $(".spanComm").removeAttr("style");
        		 }else{
        			 
        			 $(".spanComm").attr("style","display:none");
            		 document.getElementById("riskLoadID"+val2).style.color = 'red';
        			 
        		 }
        		
        	 }
		 }
        
 
		
	}
	
	function checkValidGrossPremium(val1, val2){
 
	      var regexp =/^\d+(?:\.\d{1,1})?$/;

			 var grossGeneralTypeId = document.getElementById("grossGeneralTypeId"+val2).value;
			 var grossRiskLoadID = parseFloat(document.getElementById("grossRiskLoadID"+val2).value);
			    if(!regexp.test(grossRiskLoadID))
		        {
			        alert(" Only 1 decimal place allowed ");
			        document.getElementById("grossRiskLoadID"+val2).focus();
			        document.getElementById("grossRiskLoadID"+val2).value="";
					return false;
		        }
        
        if(grossGeneralTypeId == "INM"){
			 
        	 if(grossRiskLoadID > 100){
			 alert("'Insurance Margin' cannot exceed 100%");
			 document.getElementById("grossRiskLoadID"+val2).value="";
			 return false;
        	 }
		 }
        
        if(grossGeneralTypeId == "BKC"){
			 
        	 if(grossRiskLoadID > 100){
			 alert("'Broking commission' cannot exceed 100%");
			 document.getElementById("grossRiskLoadID"+val2).value="";
			 return false;
        	 }
		 }
		
	}
	
</SCRIPT>
	<%
		int iRowCount = 0;
		String textbox = "textBox textBoxVerySmall";
	%>
	<!-- S T A R T : Search Box -->


	<html:form action="/SwGrossPremiumCalculate.do" method="post">


		<table align="center" class="pageTitle" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td width="65%"></td>
			</tr>
		</table>
		<div class="contentArea" id="contentArea">
			<html:errors />
			<logic:notEmpty name="updated" scope="request">
				<table align="center" class="successContainer" style="display:"
					border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
							width="16" height="16" align="middle">&nbsp; <bean:message
								name="updated" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>

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
			<%-- <logic:match name="frmSwGrosspremium" property="Message" value="N"> --%>

			<fieldSet>
				<legend>Net risk premium - working</legend>

				<table width="85%">
					<tr>
						<td>
							<table align="left" class="gridWithPricing" border="0"
								cellspacing="1" cellpadding="1">
								<tr>
									<td width="20%" ID="listsubheader" align="center" colspan="2"
										CLASS="gridHeader">Loadings & management discount</td>
								</tr>
								
								<logic:iterate id="cursor1" name="frmSwGrosspremium" indexId="q"
									property="cursor1">


									<%
										if (q % 2 == 0) {
									%>
									<tr class="gridOddRow">
										<%
											} else {
										%>
									
									<tr class="gridEvenRow">
										<%
											}
										%>



										<td width="50%" class="formLabel" align="center"><bean:write
												name="cursor1" property="riscDescription" /></td>
										<%--  	<td class="formLabel" width="40%"><input class="textBox textBoxVerySmall" type="text" id="riskLoadID<%=q%>" name="riskLoad<%=q%>" value="<bean:write property="riskLoad" name="cursor1"/>"></td>
			 
			 onblur="checkValid(this.value)"
				  </td> --%>


										<td class="formLabel" align="center"><logic:equal
												name="cursor1" property="generalTypeId" value="MGD">
												<input class="textBoxpricing textBoxVerySmall disabledOption" type="text"
													id="riskLoadID<%=q%>" name="riskLoad<%=q%>"
													value="<bean:write property="riskLoad" name="cursor1"/>"
													onkeyup="isNumericPT(this);"
													onchange="checkValid(this,<%=q%>);">
												<b>%</b>
											</logic:equal> <logic:notEqual name="cursor1" property="generalTypeId"
												value="MGD">
												<input class="textBox textBoxVerySmall disabledOption" type="text"
													id="riskLoadID<%=q%>" name="riskLoad<%=q%>"
													value="<bean:write property="riskLoad" name="cursor1"/>"
													onkeyup="isNumericPT(this);"
													onchange="checkValid(this,<%=q%>);">
												<b>%</b>
											</logic:notEqual> <html:hidden name="cursor1" property="riskSeqId" /> <html:hidden
												name="cursor1" property="pricSeqId" /> <%-- <html:hidden  name="cursor1" property="generalTypeId"/>  --%>
											<INPUT TYPE="hidden" NAME="generalTypeId"
											id="generalTypeId<%=q%>"
											value="<bean:write property="generalTypeId" name="cursor1"/>">
											<html:hidden name="cursor1" property="detail" /> <html:hidden
												name="cursor1" property="riscDescriptionType" /></td>



									</tr>
								</logic:iterate>

							</table>
						</td>
					</tr>
					<tr>
						<td>


							<table width="60%" align="left" class="gridWithPricing1"
								border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="formLabel" nowrap colspan="4">Comments:<span
										class="mandatorySymbol spanComm">*</span> <br> <html:textarea
											property="comments" name="frmSwGrosspremium"
											styleId="comments" styleClass="textBox textAreaLong" />
									</td>
									<td></td>

								</tr>
							</table>

						</td>
					</tr>

					<tr>
						<td>
							<table width="85%" align="center" class="buttonsContainerGrid1"
								border="0" cellspacing="0" cellpadding="0">
								<tr>

									<td width="46%"></td>
									<td width="54%" align="center">
											<%
                     if(TTKCommon.isAuthorized(request,"Edit")) {
                            %>
										<button type="button" name="Button" accesskey="a"
											class="buttonsPricingLarge"
											onMouseout="this.className='buttonsPricingLarge'"
											onMouseover="this.className='buttonsPricingLarge buttonsHover'"
											onClick="onSaveRiskPremium();">
											<!-- C<u>a</u>lculate Net <u>R</u>isk Premium -->
											Calculate net risk premium
										</button>&nbsp; 	
										<%
                                            }
                                         %>

									</td>


								</tr>
							</table>

						</td>
					</tr>

					<tr>
						<td>
							<table align="left" class="gridWithPricing" border="0"
								cellspacing="1" cellpadding="1">
								<tr>
									<td width="20%" ID="listsubheader" align="center" colspan="0"
										CLASS="gridHeader">Benefit</td>
									<td width="20%" ID="listsubheader" align="center" colspan="0"
										CLASS="gridHeader">Net risk premium per covered member per
										policy year (OMR)</td>
								</tr>
								<logic:iterate id="cursor2" name="frmSwGrosspremium" indexId="q"
									property="cursor2">


									<%
										if (q % 2 == 0) {
									%>
									<tr class="gridOddRow">
										<%
											} else {
										%>
									
									<tr class="gridEvenRow">
										<%
											}
										%>



										<td width="20%" class="formLabel" align="center"><bean:write
												name="cursor2" property="benefit" /></td>
										<td width="20%" class="formLabel" align="center"><bean:write
												name="cursor2" property="riskPremAED" /></td>


									</tr>
								</logic:iterate>

							</table>

						</td>
					</tr>

					<logic:equal name="cursor2" property="showMsg" value="Y">
						<tr>
							<td>
								<table align="center" border="0" cellspacing="0" cellpadding="0">
									<tr>

										<td width="60%" align="left" class="formLabel"><font
											color="red">Maternity inpatient and Maternity
												outpatient cost is included in the overall Inpatient and
												Outpatient cost respectively.</font></td>


									</tr>
								</table>

							</td>
						</tr>

					</logic:equal>
					
							<logic:equal name="cursor2" property="showMsg" value="N">
						<tr>
							<td>
								<table align="center" border="0" cellspacing="0" cellpadding="0">
									<tr>

										<td width="60%" align="left" class="formLabel"><font
											color="red">Maternity premium is applicable only for maternity eligible covered members.</font></td>


									</tr>
								</table>

							</td>
						</tr>

					</logic:equal>

				</table>
			</fieldSet>

			<br> <br>
			<fieldset>
				<legend>Gross premium- working</legend>
				<table width="85%">
					<tr>
						<td>
						
							<table align="left" class="gridWithPricing" border="0"
								cellspacing="1" cellpadding="1">
								<tr>
									<td width="20%" ID="listsubheader" align="center" colspan="2"
										CLASS="gridHeader">Loading</td>
								</tr>
								<logic:iterate id="cursor3" name="frmSwGrosspremium" indexId="q"
									property="cursor3">


									<%
										if (q % 2 == 0) {
									%>
									<tr class="gridOddRow">
										<%
											} else {
										%>
									
									<tr class="gridEvenRow">
										<%
											}
										%>

										<td width="50%" class="formLabel" align="center"><bean:write
												name="cursor3" property="riscDescription" /></td>
					

										<td class="formLabel" align="center">
										<input class="textBox textBoxVerySmall" type="text" id="grossRiskLoadID<%=q%>" name="grossRiskLoad<%=q%>" value="<bean:write property="grossRiskLoad" name="cursor3"/>" onkeyup="isNumericPT(this);" onchange="checkValidGrossPremium(this,<%=q%>);"><b>%</b>
											<html:hidden name="cursor3" property="riskSeqId" /> 
											<html:hidden name="cursor3" property="pricSeqId" /> <%--  <html:hidden  name="cursor3" property="generalTypeId"/>   --%>
											<INPUT TYPE="hidden" NAME="grossGeneralTypeId" id="grossGeneralTypeId<%=q%>" value="<bean:write property="generalTypeId" name="cursor3"/>">
											<html:hidden name="cursor3" property="detail" /> 
											<html:hidden name="cursor3" property="riscDescriptionType" /></td>
									</tr>
								</logic:iterate>
							</table>
						</td>
					</tr>


					<tr>
						<td>
							<table width="85%" align="center" class="buttonsContainerGrid1"
								border="0" cellspacing="0" cellpadding="0">
								<tr>

									<td width="46%"></td>
									<td width="54%" align="center">
											<%
             if(TTKCommon.isAuthorized(request,"Edit")) {
             %>
										<button type="button" name="Button" accesskey="a"
											class="buttonsPricingLarge"
											onMouseout="this.className='buttonsPricingLarge'"
											onMouseover="this.className='buttonsPricingLarge buttonsHover'"
											onClick="onSaveGrossPremium();">
											<!-- C<u>a</u>lculate <u>G</u>ross Premium -->
											Calculate gross premium
										</button>&nbsp; 
											<%
                                                   }
                                              %>

									</td>


								</tr>
							</table>

						</td>
					</tr>

					<tr>
						<td>
							<table align="left" class="gridWithPricing" border="0"
								cellspacing="1" cellpadding="0">
								<tr>
									<td width="50%" ID="listsubheader" align="center"
										CLASS="gridHeader">Benefit &nbsp;</td>
									<td width="50%" ID="listsubheader" align="center"
										CLASS="gridHeader">Gross premium per covered member per
										policy year (OMR) &nbsp;</td>

								</tr>

								<logic:iterate id="cursor4" name="frmSwGrosspremium" indexId="q"
									property="cursor4">


									<%
										if (q % 2 == 0) {
									%>
									<tr class="gridOddRow">
										<%
											} else {
										%>
									
									<tr class="gridEvenRow">
										<%
											}
										%>



										<td width="50%" class="formLabel" align="center"><bean:write
												name="cursor4" property="benefit" /></td>
										<td width="50%" class="formLabel" align="center"><bean:write
												name="cursor4" property="riskPremAED" /></td>


									</tr>
								</logic:iterate>
							</table>
						</td>
					</tr>

					<logic:equal name="cursor2" property="showMsg" value="Y">
						<tr>
							<td>
								<table align="center" border="0" cellspacing="0" cellpadding="0">
									<tr>

										<td width="60%" align="left" class="formLabel"><font
											color="red">Maternity inpatient and Maternity
												outpatient cost is included in the overall Inpatient and
												Outpatient cost respectively.</font></td>


									</tr>
								</table>

							</td>
						</tr>

					</logic:equal>
							<logic:equal name="cursor2" property="showMsg" value="N">
						<tr>
							<td>
								<table align="center" border="0" cellspacing="0" cellpadding="0">
									<tr>

										<td width="60%" align="left" class="formLabel"><font
											color="red">Maternity premium is applicable only for maternity eligible covered members.</font></td>


									</tr>
								</table>

							</td>
						</tr>

					</logic:equal>
					<tr>
						<td>
					<tr>
						<td>
							<table width="85%" align="center" class="buttonsContainer"
								border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="50%" align="center">
										<button type="button" name="Button" accesskey="a"
											class="buttonsPricingLarge"
											onMouseout="this.className='buttonsPricingLarge'"
											onMouseover="this.className='buttonsPricingLarge buttonsHover'"
											onClick="javascript:onViewPricingSummary();">
											<u>P</u>roceed
										</button>&nbsp;
										<button type="button" name="Button" accesskey="b"
											class="buttonsPricingSmall"
											onMouseout="this.className='buttonsPricingSmall'"
											onMouseover="this.className='buttonsPricingSmall buttonsHover'"
											onClick="javascript:onCloseGrossPremium()">
											<u>B</u>ack
										</button>&nbsp;

									</td>


								</tr>
							</table>


						</td>

					</tr>



				</table>

			</fieldset>


			<!-- 	<fieldset>
	<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="left">
			&nbsp;<a href="#" id="discrepancies" onclick="javascript:onViewInputSummary();"><i><b>Click to view pricing summary</b></i></a>&nbsp;&nbsp;&nbsp;
			
			<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'"	onClick="javascript:onViewInputSummary();">View <u>I</u>nput Summary</button>&nbsp;
		</td>
		
			
		</tr>
	</table>
	</fieldset> -->

			<%-- </logic:match> --%>
		</div>
		<input type="hidden" name="mode" value="" />
		<input type="hidden" name="child" value="">
		<INPUT TYPE="hidden" NAME="rownum" VALUE="">
		<INPUT TYPE="hidden" NAME="sortId" VALUE="">
		<INPUT TYPE="hidden" NAME="pageId" VALUE="">
		<INPUT TYPE="hidden" NAME="tab" VALUE="">
		<INPUT TYPE="hidden" NAME="groupseqid"
			VALUE="<bean:write name="frmSwGrosspremium" property="lngGroupProfileSeqID" />">
		<INPUT TYPE="hidden" NAME="addedBy"
			VALUE="<%=(TTKCommon.getUserSeqId(request))%>">

		<INPUT TYPE="hidden" NAME="csr1Lnth" id="csr1Lnth"
			VALUE='<%=session.getAttribute("csr1Lnth")%>' />

		<input type="hidden" name="parameter" value="" />
		<input type="hidden" name="reportType" value="" />

	</html:form>
	<form action="">
		<input type="hidden" name="mode" value="" /> <input type="hidden"
			name="child" value=""> <INPUT TYPE="hidden" NAME="tab"
			VALUE="">
	</form>
	
	<script type="text/javascript">
	
	
	onLoadValidation();
	</script>
	
</body>
</html>



