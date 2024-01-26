<%
/**
 * @ (#)  lineitems.jsp July 18,2006
 * Project      : TTK HealthCare Services
 * File         : lineitems.jsp
 * Author       : Harsha Vardhan B N
 * Company      : Span Systems Corporation
 * Date Created : July 18,2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.ClaimsWebBoardHelper,org.apache.struts.action.DynaActionForm" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/dataentryclaims/lineitems.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="JavaScript">
	var JS_Focus_Disabled =true;
</script>

<script language="JavaScript">
function reloadPage()
{
	alert("Done");
	window.location.href = window.location.pathname + window.location.search;
	//location.reload();
}
</script>
<%
	pageContext.setAttribute("roomsCode",Cache.getCacheObject("roomsCode"));
	pageContext.setAttribute("accountHead",Cache.getCacheObject("accountHead"));
	pageContext.setAttribute("vaccineType",Cache.getCacheObject("vaccineType"));//added for maternity
	
	String ShowPromoteYN = (String)request.getAttribute("ShowPromoteYN");	
	String ammendamentFlag="N";
	if(ClaimsWebBoardHelper.getAmmendmentYN(request).equals("Y"))
	{
		ammendamentFlag="Y";
	}
	pageContext.setAttribute("ammendmentYN",ammendamentFlag);
	DynaActionForm frmLineItems=(DynaActionForm)request.getSession().getAttribute("frmLineItems");
	frmLineItems.set("billDate", "");
	
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/DataEntryLineItemsAction.do" >
	<!-- S T A R T : Page Title -->
	<logic:notEmpty name="revert" scope="request">
    <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
          <bean:message name="revert" scope="request"/>
        </td>
      </tr>
    </table>
  </logic:notEmpty>
	
	 <table width="98%" align="center"  border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	   		 <td></td>
	   		  <%if(!TTKCommon.isAuthorized(request,"SpecialPermission"))
	   		  {
	   			%>
	   		  <td align="right">
	   		  <bean:write name="frmLineItems" property="billsCompleteYN"/>
	   		  <logic:notMatch name="frmLineItems" property="billsCompleteYN" value="Y">
	   		  <logic:equal name="ShowPromoteYN" value="Y">
	   		   	  <a href="#" onClick="onDataEntryPromote()"><img src="ttk/images/PromoteButton.gif" alt="DataEntryPromote" width="81" height="17" border="0" align="absbottom"></a>
			  </logic:equal>
			  </logic:notMatch>
			  
			  <logic:match name="frmLineItems" property="billsCompleteYN" value="Y">
			  <a href="#" onClick="onDataEntryRevert()"><img src="ttk/images/RevertButton.gif" alt="DataEntryRevert" width="81" height="17" border="0" align="absbottom"></a>
			  </logic:match>
	   		   
	   			<%  
	   		  }
	   		%>	  
			 
		   </td>
	    </tr>
	</table>
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="57%">Bill Detail - [ <%=ClaimsWebBoardHelper.getClaimantName(request)%> ]
            <%
			if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			{
			%>
			[ <%=ClaimsWebBoardHelper.getEnrollmentId(request)%> ]
			<%
			} 
			%>
            </td>
            <td align="right" class="webBoard">&nbsp;
				<%@ include file="/ttk/common/toolbar.jsp" %>
		   	</td>
        </tr>
    </table>
	<!-- E N D : Page Title -->	
	<html:errors/>
	<div class="contentArea" id="contentArea">
   		
   		<span  id="errorData">&nbsp;</span>
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
    	<!-- S T A R T : Form Fields -->
    	<!-- S T A R T : Grid -->
    	<br><br>

	    <table width="100%">
			<td  id="finalData">&nbsp;</td>
		</table>

		<!-- E N D : Grid -->

		<!-- S T A R T : Buttons -->
		<br>
		<table class="buttonsSavetolistGrid01"  border="0" cellspacing="0" cellpadding="0">
      		<tr>
        		<td width="86%"  id="finalamount" align="right" nowrap class="fieldGroupHeader" style="padding-right:20px;"></td>
        		<td width="6%"  align="right" nowrap >
        			<logic:match name="ammendmentYN" value="N">
						<button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAddHeader();"><u>A</u>dd Bill Header</button></td>
					</logic:match>
         		</td>
         	</tr>
    	</table>
        
		<!-- E N D : Buttons -->


		<!-- S T A R T : Line Items FieldSet -->
		<fieldset>
			<table class="formContainer"  border="0" cellspacing="0" cellpadding="0" id="BillHeader" style=" display: margin-bottom:63px; " >
         		<tr class="headerInfoValue" style="background:#E6E9ED; padding-left:2px">
           			<td colspan="8" nowrap>Bill Header </td>
          		</tr>
         		<tr>
           			<td width="18%" nowrap class="formLabel">Bill No:<span class="mandatorySymbol">*</span><br>
						<logic:match name="ammendmentYN" value="Y">
 			      	 		<input name="billNbr" type="text" class="textBox maxlength="25" textBoxMedium" readonly="true"/>
 			    		</logic:match>
 			     		<logic:match name="ammendmentYN" value="N">
					 		<input name="billNbr" type="text" maxlength="25" class="textBox textBoxMedium">
						</logic:match>
           			</td>
           			<td width="14%" nowrap class="formLabel">Bill Date:<span class="mandatorySymbol">*</span><br>
           				<logic:match name="ammendmentYN" value="Y">
	           				<html:text property="billDate" styleClass="textBox textDate" maxlength="10" readonly="true"/>
       	   				</logic:match>
	        			<logic:match name="ammendmentYN" value="N">
		        			<html:text property="billDate" styleClass="textBox textDate" maxlength="10" />
            			</logic:match>
	           			<logic:match name="ammendmentYN" value="N">
	           				<A NAME="CalendarObjectempDate11" ID="CalendarObjectempDate11" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmLineItems.billDate',document.frmLineItems.billDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
	           			</logic:match>
            		</td>
           			<td width="20%" nowrap class="formLabel">Bill Issued By:<br>
		       			<logic:match name="ammendmentYN" value="Y">
		        			<input name="billIssuedBy" maxlength="60" type="text" class="textBox textBoxMedium" id="billIssuedBy" readonly="true"/>
		        		</logic:match>
		         		<logic:match name="ammendmentYN" value="N">
							<input name="billIssuedBy" maxlength="60" type="text" class="textBox textBoxMedium" id="billIssuedBy" onkeypress="ConvertToUpperCase(event.srcElement);"/>
						</logic:match>
           			</td>
           			<td width="5%" nowrap >
           				<span class="formLabel">Rx:<br>
             				<logic:match name="ammendmentYN" value="Y">
	    	         			<input type="checkbox" name="rx" value="" disabled="true"/>
	             			</logic:match>
	             			<logic:match name="ammendmentYN" value="N">
		             			<input type="checkbox" name="rx" value="">
		            		</logic:match>
             			</span>
           			</td>
            		<td width="14%" nowrap >
            			<span class="formLabel">Included:<br>
	            			<logic:match name="ammendmentYN" value="Y">
	                			<input type="checkbox" name="include" value="" disabled="true"/>
			              	</logic:match>
			              	<logic:match name="ammendmentYN" value="N">
			                	<input type="checkbox" name="include" value="" checked="true"/>
			              	</logic:match>
            			</span>
            			<br>
            		</td>
            		<!-- added by rekha for donor expenses -->
            		<td width="14%" nowrap >
           				<span class="formLabel">Donor Claim:<br>
             				<logic:match name="ammendmentYN" value="Y">
	    	         			<input type="checkbox" name="donorExpYN" value="" disabled="true"/>
	             			</logic:match>
	             			<logic:match name="ammendmentYN" value="N">
		             			<input type="checkbox" name="donorExpYN" value="">
		            		</logic:match>
             			</span>
           			</td>
           			<!-- end added by rekha for donor expenses -->
            		<td width="15%" nowrap >&nbsp;</td>
         		</tr>
         		<tr>
           			<td width="18%" nowrap >
           				<span class="formLabel"><br></span>
           			</td>
           			<td width="14%" nowrap >&nbsp;</td>
           			<td nowrap class="formLabel">&nbsp;</td>
           			<td colspan="2" nowrap >&nbsp;</td>
           			<td align="right" nowrap >&nbsp;</td>
           			<td align="right" nowrap >
           				<span class="formLabel">
           					<logic:match name="ammendmentYN" value="N">
           					<logic:notMatch name="frmLineItems" property="billsCompleteYN" value="Y">
								<button type="button" name="Button2" accesskey="s" id="billsave" class="buttons" style="display: margin-bottom:63px;" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave('bill');reloadPage()"><u>S</u>ave</button>&nbsp;
           					</logic:notMatch>
           					</logic:match>
           				</span>
           			</td>
         		</tr>
      		</table>
	  		<div id="LineItems" style=" display: none">
		 		<table class="formContainer"  border="0" cellspacing="0" cellpadding="0" style="margin-top:5px;">
			  		<tr>
		        		<td colspan="5" align="left" nowrap></td>
		      		</tr>
		      		<tr class="headerInfoValue" style="background:#E6E9ED; padding-left:2px">
		           		<td colspan="5" nowrap>Line Items for Bill -<span style="color:#A83108;"><div id="billnnumber"/></span></td>
		          	</tr>
			    	<tr>
			       		<td align="left" nowrap class="formLabel">Description:<br>
			       	   		<logic:match name="ammendmentYN" value="Y">
						 		<html:text property="lineItemNbr"  styleClass="textBox textBoxMedium" readonly="true"/>
					   		</logic:match>
					   		<logic:match name="ammendmentYN" value="N">
						 		<html:text property="lineItemNbr"  styleClass="textBox textBoxMedium"/>
					   		</logic:match>
		   	 	       	</td>
					   	<td align="left" nowrap>Account Head:<span class="mandatorySymbol">*</span><br>
					   		<logic:match name="ammendmentYN" value="Y">
								<html:select property="accountHeadTypeID" styleClass="selectBox" onchange="showhideRoomInfo()" disabled="true">
						 	  		<html:option value="">Select from list</html:option>
									<html:options collection="accountHead" property="cacheId" labelProperty="cacheDesc" />
			 			    	</html:select>
			 			 	</logic:match>
			 			 	<logic:match name="ammendmentYN" value="N">
			 			    	<html:select property="accountHeadTypeID" styleClass="selectBox" onchange="showhideRoomInfo()">
						 	  		<html:option value="">Select from list</html:option>
									<html:options collection="accountHead" property="cacheId" labelProperty="cacheDesc" />
			 			    	</html:select>
			 			  	</logic:match>
		 			   	</td>
		 			   	<!--added for koc maternity	 	--><logic:match name="frmLineItems" property="vaccineTypeID" value="VCE" >
					<td id="vaccinetype" valign="middle" style="padding:0 0 0 18px;display:"><label style="vertical-align:top" class="formLabel">Vaccination Type:<span class="mandatorySymbol">*</span></label>
					 <html:select property="vaccineTypeID" styleClass="selectBox" >
					<html:option value="">Select from list</html:option>
					<html:options collection="vaccineType" property="cacheId" labelProperty="cacheDesc"/>
					</html:select>
					</td>
				</logic:match>
				
				<logic:notMatch name="frmLineItems" property="vaccineTypeID" value="VCE" >
					<td id="vaccinetype" valign="middle" style="padding:0 0 0 18px;display: none"><label style="vertical-align:top" class="formLabel">Vaccination Type:<span class="mandatorySymbol">*</span></label>
					 <html:select property="vaccineTypeID" styleClass="selectBox"  >
					<html:option value="">Select from list</html:option>
					<html:options collection="vaccineType" property="cacheId" labelProperty="cacheDesc"/>
					</html:select>
					</td>
				</logic:notMatch>
		 			   	
		 			   	
		 			   	<logic:match name="frmLineItems" property="accountHeadTypeID" value="Y">
					   	<td align="left" id="roomtype" nowrap style="display" class="formLabel">Room Type:<span class="mandatorySymbol">*</span><br>
					   	</logic:match>
					   	<logic:notMatch name="frmLineItems" property="accountHeadTypeID" value="Y">
					   	<td align="left" id="roomtype" nowrap style="display:none;" class="formLabel">Room Type:<span class="mandatorySymbol">*</span><br>
					   	</logic:notMatch>
						   	<logic:match name="ammendmentYN" value="Y">
								<html:select property="roomTypeID" styleClass="selectBox" disabled="true">
									<html:option value="">Select from list</html:option>
								  	<html:options collection="roomsCode" property="cacheId" labelProperty="cacheDesc" />
							  	</html:select>
						   	</logic:match>
						  	<logic:match name="ammendmentYN" value="N">
							  	<html:select property="roomTypeID" styleClass="selectBox">
									<html:option value="">Select from list</html:option>
								  	<html:options collection="roomsCode" property="cacheId" labelProperty="cacheDesc" />
							  	</html:select>
						   	</logic:match>
						</td>
					   	<logic:match name="frmLineItems" property="accountHeadTypeID" value="Y">
		 			   	<td align="left" id="noOfdays" nowrap style="display" class="formLabel">No. of Days:<span class="mandatorySymbol">*</span><br>
		 			   	</logic:match>
		 			   	<logic:notMatch name="frmLineItems" property="accountHeadTypeID" value="Y">
		 			   	<td align="left" id="noOfdays" nowrap style="display:none;" class="formLabel">No. of Days:<span class="mandatorySymbol">*</span><br>
		 			   	</logic:notMatch>
							<logic:match name="ammendmentYN" value="N">
								<html:text property="nbrofDays" styleClass="textBox textBoxTiny" />
							</logic:match>
							<logic:match name="ammendmentYN" value="Y">
								<html:text property="nbrofDays" styleClass="textBox textBoxTiny" readonly="true"/>
							</logic:match>
		   	 	       </td>
		   	 	       <td width="100%" nowrap class="formLabel">&nbsp;</td>
					</tr>
				</table>
			   	<table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left" nowrap class="formLabel">Req. Amt. (Rs):<span class="mandatorySymbol">*</span><br>
							<logic:match name="ammendmentYN" value="Y">
								<html:text property="requestedAmt"  styleClass="textBox textBoxSmall" readonly="true"/>
							</logic:match>
							<logic:match name="ammendmentYN" value="N">
								<html:text property="requestedAmt"  styleClass="textBox textBoxSmall"/>
							</logic:match>
					   	</td>
					   	<td align="left" nowrap class="formLabel">Rejd. Amt. (Rs):<br>
					 		<html:text property="disAllowedAmt"  styleClass="textBoxDisabled textBoxSmall" readonly="true" tabindex="-1"/>
				       	</td>
				       	
					   	<!-- //Koc Decoupling//Koc Decoupling -->
					   	<td  align="left" nowrap class="formLabel">Allwd. Amt. (Rs):<br>
					   		<html:text property="allowedAmt"  styleClass="textBox textBoxSmall" readonly="true" value="0"/>
				       	</td>
				       	<td width="100%" align="center" valign="bottom" nowrap>&nbsp;</td>
					</tr>
				</table>
				<input type="hidden" name="lineItemSeqID" value="" >
				<table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		   	 		<tr>
		   	 			<td colspan="4" align="left" nowrap class="formLabel">Remarks:<br>
		   	 		 		<html:textarea property="remarks" styleClass="textBox textAreaLong"/>
		   	 		   	</td>
		   	 		   	<td width="100%" align="center" valign="bottom">
		   	 		    	<span class="formLabel">
		   	 		    	<logic:notMatch name="frmLineItems" property="billsCompleteYN" value="Y">
		   	 		     		<button type="button" name="Button2" accesskey="s" id="itemsave" class="buttons" style="display: margin-bottom:63px;" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave('item')"><u>S</u>ave</button>&nbsp;
		   	 		    	</logic:notMatch>
		   	 		    	</span>
		   	 		   	</td>
		   	 		   	<td width="100%" align="center" valign="bottom">
		   	 		   		<span class="formLabel">
		   	 		   		<logic:notMatch name="frmLineItems" property="billsCompleteYN" value="Y">
		   	 		   			<button type="button" name="Button2" accesskey="n" id="itemsave" class="buttons" style="display: margin-bottom:63px;" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSaveNext('item')">Save<u>N</u>ext</button>&nbsp;
		   	 		    	</logic:notMatch>
		   	 		    	</span>
		   	 		   	</td>
		   	 		</tr>
		   	  	</table>
   	    	</div>
   		</fieldset>
   		<!-- S T A R T : Buttons //Koc Decoupling
  		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    		<tr>
      			<td width="100%" align="center">
      				<button type="button" name="Button2" accesskey="v" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onViewBillDetails()"><u>V</u>iew Bill Summary</button>&nbsp;
      			</td>
    		</tr>
  		</table> //Koc Decoupling-->
  		<!-- E N D : Buttons -->
  		<html:hidden property="selaccountID" />
  		
   		<input type="hidden" name="rownum1" value="">
		<input type="hidden" name="mode" value="">
		<input type="hidden" name="allowYN" value="">
		<input type="hidden" name="child" value="LineItems">		
		<input type="hidden" name="billSeqID" value="" >
		<input type="hidden" name="flag" value="" >
		<!-- E N D : Line Items FieldSet -->
		<!-- E N D : Content/Form Area -->
	</div>
</html:form>

<script language="javascript">
document.forms[1].elements['billNbr'].focus();
var num=Math.random(1);
if (window.XMLHttpRequest)
{ // Non-IE browsers
	var req = new XMLHttpRequest();
	req.onreadystatechange = processStateChange;
    try {
    	req.open("POST", "/DataEntrySaveLineItemsAction.do?mode=doView", true);
	} catch (e)
	{
		alert(e);
	}
	req.send(null);
} 
else if (window.ActiveXObject)
{ // IE
	req = new ActiveXObject("Microsoft.XMLHTTP");
    if (req)
    {
    	req.onreadystatechange = processStateChange;
        req.open("GET", "/DataEntrySaveLineItemsAction.do?mode=doView&q="+num, true);
        req.send(null);
	}
}

function showhideRoomInfo()
{
	var selObj = document.forms[1].accountHeadTypeID;
	if(typeof(selObj.disabled)!='undefined' && selObj.disabled !=true)
	{
		var selVal = selObj.options[selObj.selectedIndex].value;
		if(selVal!="")
		{
			var selValues = selVal.split("#");
			var selaccountID = selValues[1];
			document.forms[1].selaccountID.value=selaccountID;
			if(selValues[1]=='Y')
			{
				document.getElementById("roomtype").style.display="";
				document.getElementById("noOfdays").style.display="";
				document.getElementById("vaccinetype").style.display="none";
			}//added for maternity
			else if(selValues[0]=='VCE')
			{
				document.getElementById("vaccinetype").style.display="";
				document.getElementById("roomtype").style.display="none";
				document.getElementById("noOfdays").style.display="none";
				document.getElementById("roomtype").VALUE="";
				document.getElementById("noOfdays").VALUE="";
			}
			else
			{
				document.getElementById("roomtype").style.display="none";
				document.getElementById("noOfdays").style.display="none";
				document.getElementById("vaccinetype").style.display="none";
				document.getElementById("roomtype").VALUE="";
				document.getElementById("noOfdays").VALUE="";
				document.getElementById("vaccinetype").VALUE="";
				
			}//added for maternity
			
				
		}
		else
		{
			document.getElementById("roomtype").style.display="none";
			document.getElementById("noOfdays").style.display="none";
			document.getElementById("vaccinetype").style.display="none";
			document.getElementById("roomtype").VALUE="";
			document.getElementById("noOfdays").VALUE="";
			document.getElementById("vaccinetype").VALUE="";
		}
	}
}//end of showhideContactInfo()
//changed for maternity
function onSave(flag)
{
	trimForm(document.forms[1]);
	//alert("billseqid  "+document.forms[1].billSeqID.value);
	var urlstring="";
	if(flag=="bill")
	{
		//alert("bill");
		if(trim(document.forms[1].billNbr.value).length==0)
		{
			alert("Please Enter Bill No.");
			document.forms[1].billNbr.focus();
			return false;
		}//end of if(trim(document.forms[1].billNbr.value).length==0)
		var regexpbillno=/^[a-zA-Z0-9]{1}[a-zA-Z0-9\s\-\/\\]*$/;
		if(trim(document.forms[1].billNbr.value).length>0)
		{
			if(regexpbillno.test(trim(document.forms[1].billNbr.value))==false)
			{
				alert("Bill No. should be alphabets and numbers");
				return false;
			}//end of if(isAlphaNumeric(document.forms[1].billNbr,"Pay Order No")==false)
		}

		if(isDate(document.forms[1].billDate,"Bill Date")==false)
		{
			document.forms[1].billDate.focus();
			return false;
		}//end of if(isDate(document.forms[1].billDate,"Bill Date")==false)

		if(trim(document.forms[1].billIssuedBy.value).length>0)
		{
			var regexp=/^[a-zA-Z]{1}[a-zA-Z0-9\s\.\']*$/;
			if(regexp.test(trim(document.forms[1].billIssuedBy.value))==false)
			{
				alert("Bill Issued By should be alphabets and numbers");
				return false;
			}//end of if(isAlphaNumeric(document.forms[1].billIssuedBy,"Bill Issued By")==false)
		}
		var rx=""
	    var donorExpYN=""	//added for donor expenses
			
		var include=""
		if(document.forms[1].rx.checked)
			rx="Y";
		else
			rx="N";
		if(document.forms[1].include.checked)
			include="Y";
		else
			include="N";
		//added for donor expenses
		if(document.forms[1].donorExpYN.checked)
			donorExpYN="Y";
		else
			donorExpYN="N";
		// end added for donor expenses
		urlstring="mode=doSave&billNbr="+document.forms[1].billNbr.value+"&billDate="+document.forms[1].billDate.value+"&billIssuedBy="+document.forms[1].billIssuedBy.value+"&billWithPrescription="+rx+"&billIncludedYN="+include+"&donorExpYN="+donorExpYN+"&billSeqID="+document.forms[1].billSeqID.value+"&flag="+flag;// changed for maternity
		//alert("urlstring:::"+urlstring);
		onAddHeader();
		//alert(urlstring);
	}//end of if(flag=="bill")
	else
	{
		//alert("item");
		//showhideRoomInfo();
		//calculateRejectedAmount();
		var regexplineItemno=/^[a-zA-Z0-9]{1}[a-zA-Z0-9\s\-\/\\]*$/;
		if(trim(document.forms[1].lineItemNbr.value).length>0)
		{
			if(regexplineItemno.test(trim(document.forms[1].lineItemNbr.value))==false)
			{
				alert("Description should be alphabets and numbers");
				document.forms[1].lineItemNbr.focus();
				document.forms[1].lineItemNbr.select();
				return false;
			}//end of if(regexplineItemno.test(trim(document.forms[1].lineItemNbr.value))==false)
		}//end of if(trim(document.forms[1].lineItemNbr.value).length>0)
		
		if(document.forms[1].accountHeadTypeID.value=="")
		{
			alert("Please select Account Head");
		    if(document.forms[1].accountHeadTypeID.disabled !=true)
				document.forms[1].accountHeadTypeID.focus();
			return false;
		}//end of if(document.forms[1].accountHeadTypeID.value=="")
		var selObj = document.forms[1].accountHeadTypeID;
		var roomtype="";
		var numberofdays="";
		var vaccinetype="";
		vaccinetype=document.forms[1].vaccineTypeID.value;
	
		if(typeof(selObj.disabled)!='undefined')
		{
			var selVal = selObj.options[selObj.selectedIndex].value;
			if(selVal!="")
			{
				var selValues = selVal.split("#");
				var selaccountID = selValues[1];
				if(selValues[1]=='Y')
				{
					if(document.forms[1].roomTypeID.value=="")
					{
						alert("Please select Room Type ");
						document.forms[1].roomTypeID.focus();
						return false;
					}//end of if(document.forms[1].roomsCode.value=="")

					if(document.forms[1].nbrofDays.value=="")
					{
						alert("Please Enter No. of Days");
						document.forms[1].nbrofDays.focus();
						return false;
					}//end of if(document.forms[1].nbrofDays.value=="")

					var numexp=/^[0-9]*$/;
					if(numexp.test(trim(document.forms[1].nbrofDays.value))==false)
					{
						alert("No. of Days should be a numeric value");
						document.forms[1].nbrofDays.focus();
						return false;
					}//end of if(numexp.test(trim(document.forms[1].nbrofDays.value))==false)
					roomtype=document.forms[1].roomTypeID.value;
					numberofdays=document.forms[1].nbrofDays.value;
					vaccinetype=document.forms[1].vaccineTypeID.value;
				}//end of if(selValues[1]=='Y')
				if(selValues[0]=='VCE')
				{
					if(document.forms[1].vaccineTypeID.value=="")
					{
						alert("Please select Vaccination Type ");
						document.forms[1].vaccineTypeID.focus();
						return false;
					}//end of if(document.forms[1].roomsCode.value=="")
				}
			}//end of if(selVal!="")
		}//end of if(typeof(selObj.disabled)!='undefined' && selObj.disabled !=true)

		if(document.forms[1].requestedAmt.value=="")
		{
			alert("Please Enter Req. Amt.");
			document.forms[1].requestedAmt.focus();
			return false;
		}//end of if(document.forms[1].accountHeadTypeID.value=="")

		var amountexp=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.\[1-9]{1}\d?$|^\s*$/;
		if((document.forms[1].requestedAmt.value).length>0)
		{
			if(amountexp.test(trim(document.forms[1].requestedAmt.value))==false)
			{
				alert("Req. Amt. (Rs) should be 10 digits followed by 2 decimal places");
				document.forms[1].requestedAmt.focus();
				return false;
			}
		}//end of if((document.forms[1].requestedAmt.value).length>0)
		//Koc Decoupling
		/*if((document.forms[1].allowedAmt.value).length>0)
		{
			if(amountexp.test(trim(document.forms[1].allowedAmt.value))==false)
			{
				alert("Allwd. Amt. (Rs) should be 10 digits followed by 2 decimal places");
				document.forms[1].allowedAmt.focus();
				return false;
			}
		}//end of if(document.forms[1].allowedAmt.value=="")*/
		//Koc Decoupling
		var reqAmt = parseFloat(document.forms[1].requestedAmt.value);
		var allowedAmt = parseFloat(document.forms[1].allowedAmt.value);
		if(allowedAmt>reqAmt)
		{
			alert("Allowed Amount cannot be greater than Requested Amount");
			document.forms[1].allowedAmt.focus();
			return false;
		}
		//calculateRejectedAmount(); //commented for Decoupling
		if(((document.forms[1].disAllowedAmt.value).length>0)&&((document.forms[1].remarks.value).length==0) && (document.forms[1].accountHeadTypeID.value!="STX#N"))
		{
			alert("Please Enter Remarks.");
			document.forms[1].remarks.focus();
			return false;
		}
		if((document.forms[1].remarks.value).length>750)
		{
			alert("Remarks should not be greater than 750 characters.");
			document.forms[1].remarks.focus();
			return false;
		}

		document.forms[1].remarks.value=escape(document.forms[1].remarks.value);
	     urlstring="mode=doSave&billSeqID="+document.forms[1].billSeqID.value+"&lineItemSeqID="+document.forms[1].lineItemSeqID.value+"&lineItemNbr="+document.forms[1].lineItemNbr.value+"&accountHeadTypeID="+document.forms[1].accountHeadTypeID.value+"&roomTypeID="+roomtype+"&vaccineTypeID="+vaccinetype+"&nbrofDays="+numberofdays+"&requestedAmt="+document.forms[1].requestedAmt.value+"&disAllowedAmt="+document.forms[1].disAllowedAmt.value+"&allowedAmt="+document.forms[1].allowedAmt.value+"&remarks="+document.forms[1].remarks.value+"&flag="+flag;// changed for maternity
		//urlstring="mode=doSave&billSeqID="+document.forms[1].billSeqID.value+"&lineItemSeqID="+document.forms[1].lineItemSeqID.value+"&lineItemNbr="+document.forms[1].lineItemNbr.value+"&accountHeadTypeID="+document.forms[1].accountHeadTypeID.value+"&roomTypeID="+roomtype+"&nbrofDays="+numberofdays+"&requestedAmt="+document.forms[1].requestedAmt.value+"&disAllowedAmt="+document.forms[1].disAllowedAmt.value+"&allowedAmt="+document.forms[1].allowedAmt.value+"&remarks="+document.forms[1].remarks.value+"&flag="+flag;

		//alert(urlstring);
		onAddLineItem(document.forms[1].billSeqID.value,"","N")
	}

	var num1=Math.random(0);
    if (window.XMLHttpRequest)
    { // Non-IE browsers
    	newreq = new XMLHttpRequest();
	    newreq.onreadystatechange = processStateChange1;
    	try {
			newreq.open("GET", "/DataEntrySaveLineItemsAction.do?"+urlstring+"&q="+num1, true);
		} catch (e)
		{
			alert(e);
		}
	    newreq.send(null);
    }
    else if (window.ActiveXObject)
    { // IE
    	newreq = new ActiveXObject("Microsoft.XMLHTTP");
      	if (req)
      	{
        	newreq.onreadystatechange = processStateChange1;
        	newreq.open("GET", "/DataEntrySaveLineItemsAction.do?"+urlstring+"&q="+num1, true);
        	newreq.send(null);
      	}
    }
}//end of onSave()

function onSaveNext(flag)
{
	trimForm(document.forms[1]);
	//alert("billseqid  "+document.forms[1].billSeqID.value);
	var urlstring="";
	if(flag=="bill")
	{
		//alert("bill");
		if(trim(document.forms[1].billNbr.value).length==0)
		{
			alert("Please Enter Bill No.");
			document.forms[1].billNbr.focus();
			return false;
		}//end of if(trim(document.forms[1].billNbr.value).length==0)
		var regexpbillno=/^[a-zA-Z0-9]{1}[a-zA-Z0-9\s\-\/\\]*$/;
		if(trim(document.forms[1].billNbr.value).length>0)
		{
			if(regexpbillno.test(trim(document.forms[1].billNbr.value))==false)
			{
				alert("Bill No. should be alphabets and numbers");
				return false;
			}//end of if(isAlphaNumeric(document.forms[1].billNbr,"Pay Order No")==false)
		}

		if(isDate(document.forms[1].billDate,"Bill Date")==false)
		{
			document.forms[1].billDate.focus();
			return false;
		}//end of if(isDate(document.forms[1].billDate,"Bill Date")==false)

		if(trim(document.forms[1].billIssuedBy.value).length>0)
		{
			var regexp=/^[a-zA-Z]{1}[a-zA-Z0-9\s\.\']*$/;
			if(regexp.test(trim(document.forms[1].billIssuedBy.value))==false)
			{
				alert("Bill Issued By should be alphabets and numbers");
				return false;
			}//end of if(isAlphaNumeric(document.forms[1].billIssuedBy,"Bill Issued By")==false)
		}
		var rx=""
	    var donorExpYN=""	//added for donor expenses
			
		var include=""
		if(document.forms[1].rx.checked)
			rx="Y";
		else
			rx="N";
		if(document.forms[1].include.checked)
			include="Y";
		else
			include="N";
		//added for donor expenses
		if(document.forms[1].donorExpYN.checked)
			donorExpYN="Y";
		else
			donorExpYN="N";
		// end added for donor expenses
		urlstring="mode=doSaveNext&billNbr="+document.forms[1].billNbr.value+"&billDate="+document.forms[1].billDate.value+"&billIssuedBy="+document.forms[1].billIssuedBy.value+"&billWithPrescription="+rx+"&billIncludedYN="+include+"&donorExpYN="+donorExpYN+"&billSeqID="+document.forms[1].billSeqID.value+"&flag="+flag;// changed for maternity
//alert("urlstring:::"+urlstring);
		onAddHeader();
		//alert(urlstring);
	}//end of if(flag=="bill")
	else
	{
		//alert("item");
		//showhideRoomInfo();
		//calculateRejectedAmount();
		var regexplineItemno=/^[a-zA-Z0-9]{1}[a-zA-Z0-9\s\-\/\\]*$/;
		if(trim(document.forms[1].lineItemNbr.value).length>0)
		{
			if(regexplineItemno.test(trim(document.forms[1].lineItemNbr.value))==false)
			{
				alert("Description should be alphabets and numbers");
				document.forms[1].lineItemNbr.focus();
				document.forms[1].lineItemNbr.select();
				return false;
			}//end of if(regexplineItemno.test(trim(document.forms[1].lineItemNbr.value))==false)
		}//end of if(trim(document.forms[1].lineItemNbr.value).length>0)
		
		if(document.forms[1].accountHeadTypeID.value=="")
		{
			alert("Please select Account Head");
		    if(document.forms[1].accountHeadTypeID.disabled !=true)
				document.forms[1].accountHeadTypeID.focus();
			return false;
		}    //end of if(document.forms[1].accountHeadTypeID.value=="")  accountHeadTypeID is not required when next
		var selObj = document.forms[1].accountHeadTypeID;
		var roomtype="";
		var numberofdays="";
		var vaccinetype="";
		vaccinetype=document.forms[1].vaccineTypeID.value;
	
		if(typeof(selObj.disabled)!='undefined')
		{
			var selVal = selObj.options[selObj.selectedIndex].value;
			if(selVal!="")
			{
				var selValues = selVal.split("#");
				var selaccountID = selValues[1];
				if(selValues[1]=='Y')
				{
					if(document.forms[1].roomTypeID.value=="")
					{
						alert("Please select Room Type ");
						document.forms[1].roomTypeID.focus();
						return false;
					}//end of if(document.forms[1].roomsCode.value=="")

					if(document.forms[1].nbrofDays.value=="")
					{
						alert("Please Enter No. of Days");
						document.forms[1].nbrofDays.focus();
						return false;
					}//end of if(document.forms[1].nbrofDays.value=="")

					var numexp=/^[0-9]*$/;
					if(numexp.test(trim(document.forms[1].nbrofDays.value))==false)
					{
						alert("No. of Days should be a numeric value");
						document.forms[1].nbrofDays.focus();
						return false;
					}//end of if(numexp.test(trim(document.forms[1].nbrofDays.value))==false)
					roomtype=document.forms[1].roomTypeID.value;
					numberofdays=document.forms[1].nbrofDays.value;
					vaccinetype=document.forms[1].vaccineTypeID.value;
				}//end of if(selValues[1]=='Y')
				if(selValues[0]=='VCE')
				{
					if(document.forms[1].vaccineTypeID.value=="")
					{
						alert("Please select Vaccination Type ");
						document.forms[1].vaccineTypeID.focus();
						return false;
					}//end of if(document.forms[1].roomsCode.value=="")
				}
			}//end of if(selVal!="")
		}//end of if(typeof(selObj.disabled)!='undefined' && selObj.disabled !=true)

			
		if(document.forms[1].requestedAmt.value=="")
		{
			alert("Please Enter Req. Amt.");
			document.forms[1].requestedAmt.focus();
			return false;
		}//end of if(document.forms[1].accountHeadTypeID.value=="")

		var amountexp=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.\[1-9]{1}\d?$|^\s*$/;
		if((document.forms[1].requestedAmt.value).length>0)
		{
			if(amountexp.test(trim(document.forms[1].requestedAmt.value))==false)
			{
				alert("Req. Amt. (Rs) should be 10 digits followed by 2 decimal places");
				document.forms[1].requestedAmt.focus();
				return false;
			}
		}//end of if((document.forms[1].requestedAmt.value).length>0)

		/*if((document.forms[1].allowedAmt.value).length>0)
		{
			(if(amountexp.test(trim(document.forms[1].allowedAmt.value))==false)
			{
				alert("Allwd. Amt. (Rs) should be 10 digits followed by 2 decimal places");
				document.forms[1].allowedAmt.focus();
				return false;
			}
		}*///end of if(document.forms[1].allowedAmt.value=="")

		var reqAmt = parseFloat(document.forms[1].requestedAmt.value);
		var allowedAmt = parseFloat(document.forms[1].allowedAmt.value);
		if(allowedAmt>reqAmt)
		{
			alert("Allowed Amount cannot be greater than Requested Amount");
			document.forms[1].allowedAmt.focus();
			return false;
		}
		//calculateRejectedAmount();
		/* if(((document.forms[1].disAllowedAmt.value).length>0)&&((document.forms[1].remarks.value).length==0) && (document.forms[1].accountHeadTypeID.value!="STX#N"))
		{
			alert("Please Enter Remarks.");
			document.forms[1].remarks.focus();
			return false;
		} */
		if((document.forms[1].remarks.value).length>750)
		{
			alert("Remarks should not be greater than 750 characters.");
			document.forms[1].remarks.focus();
			return false;
		}

		document.forms[1].remarks.value=escape(document.forms[1].remarks.value);
	     urlstring="mode=doSaveNext&billSeqID="+document.forms[1].billSeqID.value+"&lineItemSeqID="+document.forms[1].lineItemSeqID.value+"&lineItemNbr="+document.forms[1].lineItemNbr.value+"&accountHeadTypeID="+document.forms[1].accountHeadTypeID.value+"&roomTypeID="+roomtype+"&vaccineTypeID="+vaccinetype+"&nbrofDays="+numberofdays+"&requestedAmt="+document.forms[1].requestedAmt.value+"&disAllowedAmt="+document.forms[1].disAllowedAmt.value+"&allowedAmt="+document.forms[1].allowedAmt.value+"&remarks="+document.forms[1].remarks.value+"&flag="+flag;// changed for maternity
		//urlstring="mode=doSave&billSeqID="+document.forms[1].billSeqID.value+"&lineItemSeqID="+document.forms[1].lineItemSeqID.value+"&lineItemNbr="+document.forms[1].lineItemNbr.value+"&accountHeadTypeID="+document.forms[1].accountHeadTypeID.value+"&roomTypeID="+roomtype+"&nbrofDays="+numberofdays+"&requestedAmt="+document.forms[1].requestedAmt.value+"&disAllowedAmt="+document.forms[1].disAllowedAmt.value+"&allowedAmt="+document.forms[1].allowedAmt.value+"&remarks="+document.forms[1].remarks.value+"&flag="+flag;

		//alert(urlstring);
		onAddLineItem(document.forms[1].billSeqID.value,"","N");
	}

	var num1=Math.random(0);
    if (window.XMLHttpRequest)
    { // Non-IE browsers
    	newreq = new XMLHttpRequest();
	    newreq.onreadystatechange = processStateChange1;
    	try {
			newreq.open("GET", "/DataEntrySaveLineItemsAction.do?"+urlstring+"&q="+num1, true);
		} catch (e)
		{
			alert(e);
		}
	    newreq.send(null);
    }
    else if (window.ActiveXObject)
    { // IE
    	newreq = new ActiveXObject("Microsoft.XMLHTTP");
      	if (req)
      	{
        	newreq.onreadystatechange = processStateChange1;
        	newreq.open("GET", "/DataEntrySaveLineItemsAction.do?"+urlstring+"&q="+num1, true);
        	newreq.send(null);
      	}
    }
}//end of onSaveNext()


function calculateRejectedAmount()
{
	//alert("inside onblur calculateRejectedAmount");
	var reqAmount=document.forms[1].requestedAmt.value;
	var allowedAmt=document.forms[1].allowedAmt.value;
	regexp=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.\[1-9]{1}\d?$|^\s*$/;

	if(reqAmount!="" && allowedAmt!="" && regexp.test(reqAmount) && regexp.test(allowedAmt))
	{
		if((document.forms[1].disAllowedAmt.value=reqAmount-allowedAmt)!=0)
		{
			document.forms[1].disAllowedAmt.value=parseFloat(reqAmount-allowedAmt).toFixed(2);
		}//end of if((document.forms[1].disAllowedAmt.value=reqAmount-allowedAmt) !=0)
		else
		{
			document.forms[1].disAllowedAmt.value="";
		}//end of else
		//document.forms[1].disAllowedAmt.value=reqAmount-allowedAmt;
	}
	else if(reqAmount!="" && allowedAmt=="" && regexp.test(reqAmount))
	{
		document.forms[1].disAllowedAmt.value=parseFloat(reqAmount).toFixed(2);
	}
}


function processStateChange1()
{
	if (newreq.readyState == 4) // Complete
    {
    	if (newreq.status == 200) // OK response
	    {
	        //var xml = newreq.responseXML;
	        mergeXML(newreq.responseXML,req.responseXML);
	        //generateHtml(xml);

	    }
	    else
	    {
	        alert("Problem: in processStateChange1 " + req.statusText);
	    }
    }//end of if (req.readyState == 4)
}
function processStateChange()
{
    if (req.readyState == 4) // Complete
    {
    	if (req.status == 200) // OK response
	    {
	        var xml = req.responseXML;
	    	generateHtml(xml);

		}else
	    {
	    	alert("Problem: " + req.statusText);
	    }
    }//end of if (req.readyState == 4)
}

function deleterecord()
{
    if (req.readyState == 4) // Complete
    {
    	if (req.status == 200) // OK response
	    {
	        var newXmlDoc = newreq.responseXML;
	        var oldXmlDoc=req.responseXML;
			var mainLists=newXmlDoc.selectSingleNode("billdetails");
	        if(newXmlDoc.selectSingleNode("/billdetails/error")!=null)
			{
				displayError(newXmlDoc);
			}
			else
			{
				if(newXmlDoc.selectSingleNode("/billdetails")!=null)
				{
					if(mainLists.getAttribute("deleteFlag")=="bill")
					{
						if(newXmlDoc.selectSingleNode("//bill")!=null)
						{
							var selectedBill= newXmlDoc.selectSingleNode("//bill");
							if(oldXmlDoc.selectSingleNode("//bill[@id="+selectedBill.getAttribute("id")+"]")!=null)
							{
								//alert("delete the bill");
								billTobeDeleted=oldXmlDoc.selectSingleNode("//bill[@id="+selectedBill.getAttribute("id")+"]");
								root = oldXmlDoc.documentElement;
								root.removeChild(billTobeDeleted);
								root.normalize();
							}
						}
					}
					else
					{
						//alert("Ielse par tem deletion");
						if(newXmlDoc.selectSingleNode("//lineitem")!=null)
						{
							var selectedLineItem= newXmlDoc.selectSingleNode("//lineitem");
							if(oldXmlDoc.selectSingleNode("//bill[@id="+selectedLineItem.getAttribute("billid")+"]/lineitem[@id="+selectedLineItem.getAttribute("id")+"]")!=null)
							{
								//alert("delete the Line Item");
								lineItemTobeDeleted=oldXmlDoc.selectSingleNode("//bill[@id="+selectedLineItem.getAttribute("billid")+"]/lineitem[@id="+selectedLineItem.getAttribute("id")+"]");
								billHeader = oldXmlDoc.selectSingleNode("//bill[@id="+selectedLineItem.getAttribute("billid")+"]");
								billHeader.removeChild(lineItemTobeDeleted);
								billHeader.normalize();
							}
						}
					}
				}
				generateHtml(oldXmlDoc);
			}//end of else
      	}//end of if (req.status == 200)
		else
   	 	{
    		alert("Problem: " + req.statusText);
		}
	}//end of if (req.readyState == 4)
}


function mergeXML(newXmlDoc,oldXmlDoc)
{
	//alert("inside mergeXML");
	var mainList=newXmlDoc.selectSingleNode("/billdetails");
	
	if(newXmlDoc.selectSingleNode("/billdetails/error")!=null)
	{
		displayError(newXmlDoc);
	}
	else 
	{
		
		if(mainList.getAttribute("mergflag")=='billsave')
		{
			if(newXmlDoc.selectSingleNode("//bill")!=null)
			{
				var selectedBill= newXmlDoc.selectSingleNode("//bill");
				if(oldXmlDoc.selectSingleNode("//bill[@id="+selectedBill.getAttribute("id")+"]")!=null)
				{
					//alert("Edit the header");
					oldBillHeader=oldXmlDoc.selectSingleNode("//bill[@id="+selectedBill.getAttribute("id")+"]");
					oldBillHeader.setAttribute("billno",selectedBill.getAttribute("billno"));
					oldBillHeader.setAttribute("billdate",selectedBill.getAttribute("billdate"));
					oldBillHeader.setAttribute("billissued",selectedBill.getAttribute("billissued"));
					oldBillHeader.setAttribute("rx",selectedBill.getAttribute("rx"));
					oldBillHeader.setAttribute("type",selectedBill.getAttribute("type"));
					oldBillHeader.setAttribute("include",selectedBill.getAttribute("include"));
					oldBillHeader.setAttribute("donorExpYN",selectedBill.getAttribute("donorExpYN"));
					
				}
				else
				{
					root = oldXmlDoc.documentElement;
					root.appendChild(selectedBill);
					root.normalize();
				}
				
			}//end of if(newXmlDoc.selectSingleNode("//bill")!=null)
		}
		if(mainList.getAttribute("mergflag")=='lineitemsave')
		{
			//alert("inside line item merge");
			if(newXmlDoc.selectSingleNode("//lineitem")!=null)
			{
				var selectedLineItem= newXmlDoc.selectSingleNode("//lineitem");
				NextBillSeqId = selectedLineItem.getAttribute("nextbill");
				NextBillNo = selectedLineItem.getAttribute("nextbillno");
				NextCompleteYN = selectedLineItem.getAttribute("nextcompleteYN");
			
				if(oldXmlDoc.selectSingleNode("//bill[@id="+selectedLineItem.getAttribute("billid")+"]/lineitem[@id="+selectedLineItem.getAttribute("id")+"]")!=null)
				{
					//alert("Edit the LineItem");
					oldLineItem=oldXmlDoc.selectSingleNode("//bill[@id="+selectedLineItem.getAttribute("billid")+"]/lineitem[@id="+selectedLineItem.getAttribute("id")+"]");
					oldLineItem.setAttribute("description",selectedLineItem.getAttribute("description"));
					oldLineItem.setAttribute("accountHeadTypeID",selectedLineItem.getAttribute("accountHeadTypeID"));
					oldLineItem.setAttribute("display",selectedLineItem.getAttribute("display"));
					oldLineItem.setAttribute("acchead",selectedLineItem.getAttribute("acchead"));
					oldLineItem.setAttribute("roomTypeId",selectedLineItem.getAttribute("roomTypeId"));
					oldLineItem.setAttribute("vaccineTypeId",selectedLineItem.getAttribute("vaccineTypeId"));// changed for maternity
					oldLineItem.setAttribute("roomdescription",selectedLineItem.getAttribute("roomdescription"));
					oldLineItem.setAttribute("days",selectedLineItem.getAttribute("days"));
					oldLineItem.setAttribute("reqAmt",selectedLineItem.getAttribute("reqAmt"));
					oldLineItem.setAttribute("rejdAmt",selectedLineItem.getAttribute("rejdAmt"));
					oldLineItem.setAttribute("allwdAmt",selectedLineItem.getAttribute("allwdAmt"));
					oldLineItem.setAttribute("remarks",selectedLineItem.getAttribute("remarks"));
				}
				else
				{
					billHeader = oldXmlDoc.selectSingleNode("//bill[@id="+selectedLineItem.getAttribute("billid")+"]");
					billHeader.appendChild(selectedLineItem);
					billHeader.normalize();
				}
			}
		}
		generateHtml(oldXmlDoc);
		//alert("1--:"+NextBillSeqId+"---2--:"+NextBillNo+"---3--:"+NextCompleteYN);
		if(NextBillSeqId=="" || NextBillSeqId==null)
		{
			var NextBillSeqId = 0; var NextBillNo = 0; var NextCompleteYN = ""; 				
		}
		else
		{
			edit('item',NextBillSeqId,NextBillNo,NextCompleteYN);
		}
	}
}

function generateHtml(xml)
{
	//alert("here inside generateHtml "+xml);
	var billdetails=xml.getElementsByTagName("billdetails");
	
	//alert(billdetails[0].getAttribute("ammendament"));
	var ammendament=billdetails[0].getAttribute("ammendament")
	var totreqamt=0,rejamt=0,allowedamt=0;
	var completedYN='N';
	
	var billList=xml.getElementsByTagName("bill");
	var hiddenElementsHTML ='';
	hiddenElementsHTML+="<div class=\"scrollableGrid\" style=\"height:240px;\">";
	hiddenElementsHTML+="<table class=\"gridWithCheckBox zeroMargin\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
	hiddenElementsHTML+="<tr>";
	hiddenElementsHTML+="<td width=\"15%\" align=\"left\" nowrap class=\"gridHeader\">Description&nbsp;</td>";
	hiddenElementsHTML+="<td width=\"9%\" align=\"left\" nowrap class=\"gridHeader\">Acc. Head  &nbsp;</td>";
	hiddenElementsHTML+="<td width=\"4%\" align=\"left\" nowrap class=\"gridHeader\">Room Type</td>";
	hiddenElementsHTML+="<td width=\"5%\" align=\"right\" nowrap class=\"gridHeader\">Days</td>";
	hiddenElementsHTML+="<td width=\"10%\" align=\"right\" nowrap class=\"gridHeader\">Req. Amt.(Rs)</td>";
	hiddenElementsHTML+="<td width=\"10%\" align=\"right\" nowrap class=\"gridHeader\"> Rejd. Amt.(Rs)</td>";
	hiddenElementsHTML+="<td width=\"10%\" align=\"right\" nowrap class=\"gridHeader\">Allwd. Amt.(Rs)</td>";
	hiddenElementsHTML+="<td width=\"13%\" align=\"left\" nowrap class=\"gridHeader\"> Remarks</td>";
	if(ammendament=="N")
	{
		//alert("inside if");
		hiddenElementsHTML+="<td width=\"4%\" style=\"display: margin-bottom:63px;\" id=\"deletehide\" align=\"center\" class=\"gridHeader\"> Delete</td>";
	}
	else
	{
		hiddenElementsHTML+="<td width=\"4%\" style=\"display: margin-bottom:63px;\"  align=\"center\" class=\"gridHeader\"> Delete</td>";
	}
    hiddenElementsHTML+="</tr>";
	//alert(billList.length);
	for(i=0;i<billList.length;i++)
	{
		//alert('Completed YN	:'+billList[i].getAttribute("completeYN"));
		completedYN=billList[i].getAttribute("completeYN");
		hiddenElementsHTML+="<tr >";
		hiddenElementsHTML+="<td class=\"gridBillRow\" colspan=\"8\" nowrap>";
		hiddenElementsHTML+="<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
		hiddenElementsHTML+="<tr>";
		hiddenElementsHTML+="<td style=\"border:0px;\">";
		if(ammendament=="N")
		{
			hiddenElementsHTML+="<a href=\"javascript:onAddLineItem("+billList[i].getAttribute("id")+",'"+billList[i].getAttribute("billno")+"','Y','"+billList[i].getAttribute("completeYN")+"');\" title=\"Add Line Items\"><img src=\"ttk/images/AddIcon.gif\" border=\"0\" align=\"absmiddle\" alt=\"Add Line Items\" title=\"Add Line Items\"></a>&nbsp;&nbsp;&nbsp;</span>";
			hiddenElementsHTML+="<span class=\"cont\"><strong>Bill No:</strong></span> <a href=\"javascript:edit('bill',"+billList[i].getAttribute("id")+",'"+billList[i].getAttribute("billno")+"');\">"+billList[i].getAttribute("billno")+"</a>&nbsp;&nbsp;";
		}
		else
		{
			hiddenElementsHTML+="<span class=\"cont\"><strong>Bill No:</strong></span>"+billList[i].getAttribute("billno")+"&nbsp;&nbsp;";
		}
		hiddenElementsHTML+="<span class=\"cont\"><strong>Bill Date:</strong></span> "+billList[i].getAttribute("billdate")+"&nbsp;&nbsp;";
		hiddenElementsHTML+="<span class=\"cont\"><strong>Bill Issued by:</strong></span>"+ billList[i].getAttribute("billissued")+"&nbsp;&nbsp;";
		hiddenElementsHTML+="<span class=\"cont\"><strong>Rx:</strong></span>"+billList[i].getAttribute("rx")+"&nbsp;&nbsp;";
		hiddenElementsHTML+="<span class=\"cont\"><strong>Type:</strong></span> "+billList[i].getAttribute("type")+"&nbsp;&nbsp;";
		hiddenElementsHTML+="<span class=\"cont\"><strong>Included:</strong></span>"+billList[i].getAttribute("include")+"&nbsp;&nbsp;";
		hiddenElementsHTML+="<span class=\"cont\"><strong>Donor Claim:</strong></span>"+billList[i].getAttribute("donorExpYN")+"&nbsp;&nbsp;";
		hiddenElementsHTML+="</td>";
		hiddenElementsHTML+="</tr>";
		hiddenElementsHTML+="</table>";
		hiddenElementsHTML+="</td>";
		hiddenElementsHTML+="<td class=\"gridBillRow\" align=\"center\">";
		//hiddenElementsHTML+="<input type=\"checkbox\" name=\"checkbox42\" value=\"checkbox\">";
		if(ammendament=="N"&&billList[i].getAttribute("completeYN")=='N')
		{
			hiddenElementsHTML+="<a href=\"javascript:onDelete('bill',"+billList[i].getAttribute("id")+");\" title=\"Delete Bill Header\"><img src=\"ttk/images/DeleteIcon.gif\" border=\"0\" align=\"absmiddle\" alt=\"Delete Bill Header\" title=\"Delete Bill Header\"></a>&nbsp;</span>";
		}
		else
		{
			hiddenElementsHTML+="&nbsp;";
		}
		hiddenElementsHTML+="</td>";
		hiddenElementsHTML+="</tr>";
		if(billList[i].hasChildNodes)
		{
			var styclass='';
			if(billList[i].getAttribute("include")=='No')
			{
				styclass="strikeText";
			}
			for(j=0;j<billList[i].childNodes.length;j++)
			{
				if(j%2==0)
					hiddenElementsHTML+="<tr class=\"gridOddRow\">";
				else
					hiddenElementsHTML+="<tr class=\"gridEvenRow\">";
		  		hiddenElementsHTML+="<td>&nbsp;"+billList[i].childNodes(j).getAttribute("description")+"</td>";
				hiddenElementsHTML+="<td><a href=\"javascript:edit('item',"+billList[i].childNodes(j).getAttribute("id")+",'"+billList[i].getAttribute("billno")+"','"+billList[i].getAttribute("completeYN")+"')\">"+billList[i].childNodes(j).getAttribute("acchead")+"</a></td>";
				hiddenElementsHTML+="<td >&nbsp;"+billList[i].childNodes(j).getAttribute("roomdescription")+"</td>";
				hiddenElementsHTML+="<td align=\"right\" >"+billList[i].childNodes(j).getAttribute("days")+"</td>";
				hiddenElementsHTML+="<td align=\"right\"  class=\'"+styclass+"'\">"+parseFloat(billList[i].childNodes(j).getAttribute("reqAmt")).toFixed(2)+"</td>";
				hiddenElementsHTML+="<td align=\"right\" class=\'"+styclass+"'\">"+parseFloat(billList[i].childNodes(j).getAttribute("rejdAmt")).toFixed(2)+"&nbsp;</td>";
				hiddenElementsHTML+="<td align=\"right\" class=\'"+styclass+"'\">"+parseFloat(billList[i].childNodes(j).getAttribute("allwdAmt")).toFixed(2)+"&nbsp;</td>";
				hiddenElementsHTML+="<td align=\"left\">&nbsp;"+billList[i].childNodes(j).getAttribute("remarks")+"&nbsp;</td>";
				if(ammendament=="N"&&billList[i].getAttribute("completeYN")=='N')
				{
			  		hiddenElementsHTML+="<td align=\"center\"><a href=\"javascript:onDelete('item',"+billList[i].childNodes(j).getAttribute("id")+","+billList[i].getAttribute("id")+");\" title=\"Delete Line Item\"><img src=\"ttk/images/DeleteIcon.gif\" border=\"0\" align=\"absmiddle\" alt=\"Delete Line Item\" title=\"Delete Line Item\"></a></td>";
				}
				else
				{
					hiddenElementsHTML+="<td align=\"center\">&nbsp;</td>";
				}
				hiddenElementsHTML+="</tr>";
				if(billList[i].getAttribute("include")=='Yes')
				{
					totreqamt+=parseFloat(billList[i].childNodes(j).getAttribute("reqAmt"));
					rejamt+=parseFloat(billList[i].childNodes(j).getAttribute("rejdAmt"));
					allowedamt+=parseFloat(billList[i].childNodes(j).getAttribute("allwdAmt"));
				}
			}//end of for
		}//end of if(billList[i].hasChildNodes)
	}//end of for(i=0;i<billList.length;i++)
	totreqamt=parseFloat(totreqamt).toFixed(2);
	rejamt=parseFloat(rejamt).toFixed(2);
	allowedamt=parseFloat(allowedamt).toFixed(2);
	//http://www.webdeveloper.com/forum/archive/index.php/t-3905.html
	hiddenElementsHTML+="</table>";
	hiddenElementsHTML+="</div>";
	//alert(hiddenElementsHTML);
	document.getElementById('finalData').innerHTML= hiddenElementsHTML;
	if(ammendament=='N'&&completedYN=='Y')
	{
		document.getElementById("billsave").style.display="none";
		//document.getElementById("deletehide").style.display="none";
	}
    //var amountsList=xml.getElementsByTagName("amounts");
	var amountHTML="<strong>Total</strong> - Req. Amt. (Rs):<strong> "+totreqamt+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>Rejd. Amt. (Rs)<strong>: "+rejamt+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>Allwd. Amt. (Rs):<strong> "+allowedamt+"</strong>";
	//alert(amountHTML);
	document.getElementById('finalamount').innerHTML= amountHTML;
	document.getElementById('errorData').innerHTML="";		
}

function SetState()
{
	if(document.getElementById("allowYN").checked)
    {
		document.forms[1].allowedAmt.className = "textBox textBoxSmall";
		document.forms[1].allowedAmt.readOnly = false;
	}
    else
    {
   		document.forms[1].allowedAmt.className = "textBoxDisabled textBoxSmall";
   		document.forms[1].allowedAmt.value="";
   		document.forms[1].disAllowedAmt.value=document.forms[1].requestedAmt.value;
   		document.forms[1].allowedAmt.readOnly = true;
	}
}//end of SetState()

function beforeDelete()
{
	document.getElementById("BillHeader").style.display="";
	document.getElementById("LineItems").style.display="";
	clearLineItem();
	onAddHeader();
}

function showBillHeader()
{
	document.getElementById("BillHeader").style.display="";
	document.getElementById("LineItems").style.display="none";
}

function showLineItems()
{
	document.getElementById("BillHeader").style.display="none";
	document.getElementById("LineItems").style.display="";
	document.getElementById("roomtype").style.display="";
	document.getElementById("noOfdays").style.display="";
	document.getElementById("vaccinetype").style.display="";
}
function onAddHeader()
{
	document.forms[1].billSeqID.value="";
	showBillHeader();
	clearBillHeader();
	document.forms[1].billNbr.focus();
}

function onAddLineItem(billid,billnumber,flag,completeYN)
{
	var ammendmentYN = '<%=pageContext.getAttribute("ammendmentYN")%>';
	showLineItems();
	clearLineItem();
	document.forms[1].billSeqID.value=billid;
	if(flag=="Y")
	{
		document.getElementById('billnnumber').innerHTML = billnumber;
	}
	if(completeYN=='Y')
		document.getElementById("itemsave").style.display="none";

	if(ammendmentYN == 'Y')
		document.forms[1].allowedAmt.focus();
	else
		document.forms[1].lineItemNbr.focus();

}//end of onAddLineItem(billid,billnumber)
// changed for maternity
function edit(mode,id,billnum,completeYN)
{
 	var ammendmentYN = '<%=pageContext.getAttribute("ammendmentYN")%>';
 	xml = req.responseXML;
	if(mode=="bill")
	{
		var search=xml.selectNodes("//bill[@id='"+id+"']");
		showBillHeader();
		document.forms[1].billSeqID.value=search[0].getAttribute("id");
		document.forms[1].billNbr.value=search[0].getAttribute("billno");
		document.forms[1].billDate.value=search[0].getAttribute("billdate");
		document.forms[1].billIssuedBy.value=search[0].getAttribute("billissued");
		if(search[0].getAttribute("rx")=="Yes")
			document.forms[1].rx.checked=true;
		else
			document.forms[1].rx.checked=false;

		if(search[0].getAttribute("include")=="Yes")
			document.forms[1].include.checked=true;
		else
			document.forms[1].include.checked=false;
		//added for donor expenses
		if(search[0].getAttribute("donorExpYN")=="Yes")
			document.forms[1].donorExpYN.checked=true;
		else
			document.forms[1].donorExpYN.checked=false;
		// end added for donor expenses
		document.forms[1].billNbr.focus();			
	}
	else
	{
		var search=xml.selectNodes("//bill/lineitem[@id='"+id+"']");
		showLineItems();
		document.getElementById('billnnumber').innerHTML = billnum;
		//alert("else part"+search[0].getAttribute("billid"));
		document.forms[1].billSeqID.value=search[0].getAttribute("billid");
		document.forms[1].lineItemSeqID.value=search[0].getAttribute("id");
		document.forms[1].lineItemNbr.value=search[0].getAttribute("description");
		//alert(search[0].getAttribute("accountHeadTypeID"));
		document.forms[1].accountHeadTypeID.value=search[0].getAttribute("accountHeadTypeID");
		//alert(search[0].getAttribute("accountHeadTypeID"));
		
		    var accHead=document.forms[1].accountHeadTypeID.value;
		    //alert("accHead:::"+accHead);
		    var selValues = accHead.split("#");// changed for maternity
			//alert("selValues"+selValues);
			var selaccountID = selValues[0];// changed for maternity
		
			//alert("selValues1"+selValues[0]);
			document.forms[1].selaccountID.value=selaccountID;// changed for maternity



		if(search[0].getAttribute("display")=="Y")
		{
			document.getElementById("roomtype").style.display="";
			document.getElementById("noOfdays").style.display="";
			document.getElementById("vaccinetype").style.display="none";
			document.forms[1].nbrofDays.value=search[0].getAttribute("days");
			document.forms[1].roomTypeID.value=search[0].getAttribute("roomTypeId");
			document.forms[1].vaccineTypeID.value="";
		}

		else if(search[0].getAttribute("display")=="N")
				{	if(selaccountID=="VCE")
				    {
					    
						document.getElementById("vaccinetype").style.display="";
						document.getElementById("roomtype").style.display="none";
						document.getElementById("noOfdays").style.display="none";
						document.forms[1].nbrofDays.value="";
						document.forms[1].roomTypeID.value="";
						document.forms[1].vaccineTypeID.value=search[0].getAttribute("vaccineTypeId");
						//alert("document.forms[1].vaccineTypeID:::"+document.forms[1].vaccineTypeID.value);
						//document.forms[1].vaccineTypeID.value=19;
						//alert("document.forms[1].vaccineTypeID:::"+document.forms[1].vaccineTypeID.value);
				     }
				else{
					document.forms[1].nbrofDays.value="";
					document.forms[1].roomTypeID.value="";
					document.forms[1].vaccineTypeID.value="";
					document.getElementById("roomtype").style.display="none";
					document.getElementById("noOfdays").style.display="none";
					document.getElementById("vaccinetype").style.display="none";
					}	
		         }
		else
		{
			document.forms[1].nbrofDays.value="";
			document.forms[1].roomTypeID.value="";
			document.forms[1].vaccineTypeID.value="";
			document.getElementById("roomtype").style.display="none";
			document.getElementById("noOfdays").style.display="none";
			document.getElementById("vaccinetype").style.display="none";
			
		}// changed for maternity
		document.forms[1].requestedAmt.value=parseFloat(search[0].getAttribute("reqAmt")).toFixed(2);
		document.forms[1].disAllowedAmt.value=parseFloat(search[0].getAttribute("rejdAmt")).toFixed(2);
		document.forms[1].allowedAmt.value=parseFloat(search[0].getAttribute("allwdAmt")).toFixed(2);
		if(document.forms[1].allowedAmt.value=="0.00"||document.forms[1].allowedAmt.value=="0")
			document.forms[1].allowedAmt.value="";
		if(document.forms[1].disAllowedAmt.value=="0.00"||document.forms[1].disAllowedAmt.value=="0")
			document.forms[1].disAllowedAmt.value="";
		document.forms[1].remarks.value=search[0].getAttribute("remarks");
		if(completeYN=='Y')
			document.getElementById("itemsave").style.display="none";
		
		if(ammendmentYN == 'Y')
			document.forms[1].allowedAmt.focus();
		else
			document.forms[1].lineItemNbr.focus();
	}
}

function clearBillHeader()
{
	document.forms[1].billNbr.value="";
	document.forms[1].billDate.value="";
	document.forms[1].billIssuedBy.value="";
	document.forms[1].rx.checked=false;
	document.forms[1].donorExpYN.checked=false;//added for donor expenses
	document.forms[1].include.checked=true;
}

function clearLineItem()
{
	document.forms[1].lineItemSeqID.value="";
	document.forms[1].lineItemNbr.value="";
	document.forms[1].accountHeadTypeID.value="";
	document.forms[1].requestedAmt.value="";
	document.forms[1].disAllowedAmt.value="";
	document.forms[1].allowedAmt.value="";
	document.forms[1].roomTypeID.value="";
	document.forms[1].vaccineTypeID.value="";
	document.forms[1].nbrofDays.value="";
	document.forms[1].remarks.value="";

	document.getElementById("roomtype").style.display="none";
	document.getElementById("noOfdays").style.display="none";
	document.getElementById("vaccinetype").style.display="none";
	//alert("line item seq id "+ document.forms[1].lineItemSeqID.value);
}
function onViewBillDetails()
{
   document.forms[1].child.value="";
   document.forms[1].mode.value="doViewBillSummary";
   document.forms[1].action="/BillSummaryAction.do";
   document.forms[1].submit();
}

function onDelete(deletionItem,id,billId)
{
	var msg = confirm("Are you sure you want to delete the selected record(s)?");
	urlstring="mode=doDeleteList&id="+id+"&billId="+billId+"&deletionFlag="+deletionItem;
	if(msg)
	{
		if (window.XMLHttpRequest)
		{ // Non-IE browsers
			newreq = new XMLHttpRequest();
			newreq.onreadystatechange = deleterecord;
		    try {
        		newreq.open("GET", "/DataEntryLineItemsAction.do?"+urlstring+"&q="+num, true);
			} catch (e)
			{
				alert(e);
			}
			newreq.send(null);
		} 
		else if (window.ActiveXObject)
		{ // IE
			newreq = new ActiveXObject("Microsoft.XMLHTTP");
		    if (req)
		    {
				beforeDelete();
				newreq.onreadystatechange = deleterecord;
        		newreq.open("GET", "/DataEntryLineItemsAction.do?"+urlstring+"&q="+num, true);

		        newreq.send(null);
			}
		}
	}//end of if(msg)
	reloadPage();	
}//end of onDelete()

function displayError(errorXml)
{
	
	var errror=errorXml.getElementsByTagName("error");
	var errorDesc=errorXml.getElementsByTagName("error")[0].childNodes[0].nodeValue
	if(errorDesc!='')
	{
		var errorHTML='';
		errorHTML+="<table align=\"center\" class=\"errorContainer\" style=\"display:\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
		errorHTML+="<tr><td><strong><img src=\"/ttk/images/ErrorIcon.gif\" alt=\"Error\" width=\"16\" height=\"16\" align=\"absmiddle\">&nbsp;The following errors have occurred - </strong>";
		errorHTML+="<ol style=\"padding:0px;margin-top:3px;margin-bottom:0px;margin-left:25px;\">";	
		errorHTML+="<li style=\"list-style-type:decimal\">";
		errorHTML+=errorDesc;
		errorHTML+="</li></ol></td></tr></table>";
		document.getElementById('errorData').innerHTML= errorHTML;
	}
}

</script>

<!-- E N D : Main Container Table -->
