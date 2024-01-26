<%
/** @ (#) claimawaitingsearch.jsp
 * Project     : TTK Healthcare Services
 *1274 Change request  Bajaj
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,java.util.ArrayList"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/calendar/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/approveawaitingsearch.js"></SCRIPT>
<script>
//hidebutton();
bAction=false;
var TC_Disabled = true;
</script>
<%
	//pageContext.setAttribute("sShortfallStatus",Cache.getCacheObject("claimShortfallStatus"));
	pageContext.setAttribute("sTtkBranch", Cache.getCacheObject("officeInfo"));
%>
<!-- S T A R T : Content/Form Area -->


<html:form action="/ClaimAwaitingApproveSearch.do">

 <!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">Claim Awaitng Search Page</td>
			<%-- <tr>
	    <td><bean:write name="frmClaimAwaitingApprove" property="caption"/></td> --%>    
	    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	    </tr>
		
	</table>
	 
	<!-- E N D : Page Title -->	
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
	
	
	<!-- S T A R T : Search Box -->
	<div class="contentArea" id="contentArea">
	
	<table align="center" class="tablePad"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="10%" nowrap class="textLabelBold">Switch to:</td>
        <td width="90%">
	        <html:select property="switchType" styleClass="specialDropDown" styleId="switchType" onchange="javascript:onSwitch();">
	       <%--  <html:options collection="listSwitchType" property="cacheId" labelProperty="cacheDesc"/>--%>
	            <html:option value="CLM">Claims</html:option>
	            <html:option value="PRE">Cashless</html:option>
			</html:select>
		</td>
      </tr>   
    </table>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	
			<tr class="searchContainerWithTab">
			
			  <td nowrap> EnrollmentId :<br>
            	<html:text property="sEnrollmentId"  styleClass="textBox textBoxMedium" maxlength="60"/>
            </td>
            <td nowrap> Policy Number :<br>
                	<html:text property="sPolicyNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
            </td>
            <logic:match name="frmClaimAwaitingApprove" property="switchType" value="CLM">
			<td nowrap> Claim No.:<br>
            	<html:text property="sClaimNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
            </td>
            </logic:match>
             <logic:match name="frmClaimAwaitingApprove" property="switchType" value="PRE">
			<td nowrap> Pre-Auth No.:<br>
            	<html:text property="sPreAuthNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
            </td>
            </logic:match>
            
             <td nowrap> From Date<br>
            		<html:text property="sFrmDate" styleClass="textBox textDate" maxlength="10" /> 
                   <a name="CalendarObjectC2Date" id="CalendarObjectC2Date" href="#" onClick="javascript:show_calendar('CalendarObjectC2Date','forms[1].sFrmDate',document.forms[1].sFrmDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="sFrmDate" width="24" height="17" border="0" align="absmiddle"></a>
        	   </td>
       	      <td nowrap class="formLabel"> To Date:<br>
                  <html:text property="sToDate" styleClass="textBox textDate" maxlength="10" /> 
                  <a name="CalendarObjectClDate" id="CalendarObjectClDate" href="#" onClick="javascript:show_calendar('CalendarObjectClDate','forms[1].sToDate',document.forms[1].sToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="sToDate" width="24" height="17" border="0" align="absmiddle"></a>
              </td>
        	
        </tr>   
        <tr class="searchContainerWithTab">
        
        <td nowrap> Claim Status :<br>
                   	<html:select property="sClaimStatus" styleClass="selectBox selectBoxLargest">
                   	<html:option value="INP">In-Progress</html:option>
                   	<html:option value="TRAD">Healthcare Administrator Recommendation Accepted</html:option>
		  	 		<html:option value="TRND">Healthcare Administrator Recommendation Not Accepted</html:option>
                   	<%-- <html:option value="INP">In-Progress</html:option>
		  	 		<html:option value="APR">Approved</html:option>
		  	 		<html:option value="REJ">Rejected</html:option>
		  	 		<html:option value="REQ">Required Information</html:option> --%>
                  </html:select>
                 </td>
              
               <td nowrap> Location :<br>
            	<html:select property="sTtkBranch" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sTtkBranch" label="cacheDesc" value="cacheId" />
            	</html:select>
        	</td>
            
           
        	<td nowrap> Insurance RO/DO :<br>
            	<html:text property="sInsuranceRODO"  styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
        	        	
        	<td nowrap>       Claim Recommended Amount    <br>
        	<html:select property="sOperator" styleClass="selectBox selectBoxMedium">
        		<html:option value="">any</html:option>
                	<html:option value="EQ">EqualTo</html:option>
		  	     	<html:option value="LT">LessThan or Equal to</html:option>
		  	 		<html:option value="GT">GreaterThan or Equal to</html:option>
            </html:select>
            </td>
            <td>
            	<html:text property="sClaimRecommendedAmount"  styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
        	<!-- denial process -->
        	 <td nowrap> Insurance Administrator Status :<br>
            	<html:select property="sTPAStatus" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:option value="APR">Approved</html:option>
		  	 		<html:option value="REJ">Rejected</html:option>
            	</html:select>
        	</td>
        	<!-- denial process -->
        	<td valign="bottom" nowrap>
	        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        	</td>
        	
		</tr>   		
	</table>
	<!-- E N D : Search Box -->
	
	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->

<table border="0" cellspacing="0" cellpadding="0">
<tr id="multiRemarks" style="display:none" >
<td nowrap> Multiple Approve/Reject Remarks :</td>
           <td nowrap> 		
           <html:text property="sRemarks"  styleClass="textBox textAreaLongHt" maxlength="200"/>
        	</td>
        	     

</tr>


</table>

	
	<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
		    <tr>
		    <td width="25%" align="center">&nbsp;</td>
		<%  if(TTKCommon.isAuthorized(request,"Edit"))
	        {
			         if(TTKCommon.isAuthorized(request,"Approve"))
			         {
	%>		
	                    <td width="25%" align="right">
	       	              <button type="button" name="Button1" accesskey="a" class="buttons"  id="approve" style="display:" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBulkApprove();">Insurance Administrator Recommendation <u>A</u>ccepted</button>&nbsp;
			           </td>
	<%
	                 }//end of if(TTKCommon.isAuthorized(request,"Approve"))
	 				if(TTKCommon.isAuthorized(request,"Reject"))
	 				{
	 %>
        	
			            <td width="25%" align="right">
	       	             <button type="button" name="Button2" accesskey="n" class="buttons" id="reject" style="display:" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBulkReject();">Insurance Administrator Recommendation <u>N</u>ot Accepted</button>&nbsp;
			            </td>
	<%
					}//end of if(TTKCommon.isAuthorized(request,"Reject"))	
		
	             // if(TTKCommon.isAuthorized(request,"RequiredInformation"))
	              // {
	%>
			          <!--    <td width="25%" align="right">
	       	              <button type="button" name="Button3" accesskey="r" id="reqinf" style="display:" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onRequiredInformation();"><u>R</u>equiredInformation</button>&nbsp;
			             </td> -->
		   	
		
	<%
	              // }//end of if(TTKCommon.isAuthorized(request,"RequiredInformation"))	
	        }//end of if(TTKCommon.isAuthorized(request,"Edit"))
	%>
		  </tr>
			<%-- <tr  id="GeneratePrint" style="display:">
			<td width="100%" align="right">
			<button type="button" name="Button" id="send" accesskey="R" style="display:" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBulkReject();"><u>R</u>ject</button>&nbsp;
		    </td>
		   </tr>--%> 
    	
		<ttk:PageLinks name="tableData"/>
	</table>
	<!-- E N D : Buttons -->
	
	</div>	
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="setStatus" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="child" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->