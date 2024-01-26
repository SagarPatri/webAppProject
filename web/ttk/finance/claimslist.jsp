<%
/**
 * @ (#) chequeseries.jsp 9th June 2006
 * Project      : TTK HealthCare Services
 * File         : chequeseries.jsp
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : 9th June 2006
 *
 * @author       :Krupa J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/finance/claimslist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var TC_Disabled = true;
	var claimsAmt = new Array(<bean:write name="frmClaims" property="strClaimsAmt" filter="false"/>);
	var convertedClaimsAmt=new Array(<bean:write name="frmClaims" property="strConvertedClaimsAmt" filter="false"/>);
</SCRIPT>
	<%
	    String strWebBoardId = String.valueOf(TTKCommon.getWebBoardId(request));

		pageContext.setAttribute("WebBoardId", strWebBoardId);
		pageContext.setAttribute("claimType", Cache.getCacheObject("claimType"));
		pageContext.setAttribute("policyType", Cache.getCacheObject("enrollTypeCode"));
		pageContext.setAttribute("paymentMethod", Cache.getCacheObject("paymentMethod"));
		pageContext.setAttribute("curencyTypeList", Cache.getCacheObject("allCurencyCode"));		
	%>
		
	<html:form action="/ClaimsSearchAction.do"  method="post"   enctype="multipart/form-data">
	<logic:match name="WebBoardId" value="null" >
	<html:errors/>
	</logic:match>
	
	<logic:notMatch name="WebBoardId" value="null">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	
  		<tr>
    		<td width="57%">List of Claims</td>
    		<td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<logic:notEmpty name="notify" scope="session">
	
		<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	    
	        <%=request.getSession().getAttribute("notify")%>
	        </td>
	      </tr>
   	 </table>
   	 </logic:notEmpty>
   	 <%  session.removeAttribute("notify");%>
   	 
	<!-- S T A R T : Success Box -->
	 <logic:notEmpty name="updated" scope="request">
	  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="updated" scope="request" />
	         <logic:notEmpty name="popup" scope="request">
		         <script>
		         //window.open("/ReportsAction.do?mode=GenerateReport&fileName=ChequePrintingICICI.jrxml&reportID=PrintCheque&reportType=PDF&parameter=<bean:write name='frmClaims' property='parameterValue' filter='false'/>",'',"scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+screen.availWidth - 10+",height="+screen.availHeight - 49);
			         window.focus();
			         var w = screen.availWidth - 10;
					 var h = screen.availHeight - 49;
					 var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			         window.open("/ClaimsSearchAction.do?mode=doClaimsCheque",'',features);
		         </script>
			</logic:notEmpty>
			<logic:notEmpty name="FinBatchReport" scope="request">
	         <script>
	            var w = screen.availWidth - 10;
				var h = screen.availHeight - 49;
				var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
				window.open("<bean:write name="FinBatchReport" scope="request"/>",'',features);
			 </script>
			</logic:notEmpty>
	     </td>
	  </tr>
	 </table>
    </logic:notEmpty>
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="20%" nowrap> Float Type:</td>
				<logic:match name="frmClaims" property="floatType" value="FTD">
					<td width="30%" class="textLabelBold" nowrap>Debit</td>
				</logic:match>
				<logic:notMatch name="frmClaims" property="floatType" value="FTD">
					<td width="30%" class="textLabelBold" nowrap> Regular</td>
				</logic:notMatch>
			
			
			<logic:match name="frmClaims" property="floatType" value="FTD">
				<td width="20%" nowrap>Debit Note:</td>
				<td width="30%" class="textLabelBold" nowrap>
					<bean:write name="frmClaims" property="debitNumber"/>&nbsp;
					<a href="#" onClick="javascript:onSelectDebitNote()"><img src="ttk/images/EditIcon.gif" alt="Select Derbit Note" width="16" height="16" border="0" align="absmiddle" class="icons"></a>&nbsp;&nbsp;
					<a href="#" onClick="javascript:onClearDebit()"><img src="ttk/images/DeleteIcon.gif" alt="Select Derbit Note" width="16" height="16" border="0" align="absmiddle" class="icons"></a>
				</td>
			</logic:match>
			<logic:notMatch name="frmClaims" property="floatType" value="FTD">
				<td width="20%" nowrap>&nbsp;</td>
				<td width="30%" nowrap>&nbsp;</td>
			</logic:notMatch>
		</tr>
	</table>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
		<td nowrap>Claim Settlement No.:<br>
        <html:text  property="claimSettNo" styleClass="textBox textBoxMedium"  maxlength="60"/></td>

		<td nowrap>Enrollment Id:<br>
        <html:text property="enrollmentId" styleClass="textBox textBoxMedium" maxlength="60"/></td>

         <td nowrap> DV Received Date:<br>
         <html:text property="dvReceivedDate" styleClass="textBox textDate" maxlength="10"/>
         <A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','forms[1].dvReceivedDate',document.forms[1].dvReceivedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="dvReceivedDate" width="24" height="17" border="0" align="absmiddle" ></a></td>
         <td nowrap>Corporate Name:<br>
        	<html:text property="corporateName" styleClass="textBox textBoxMedium" maxlength="120"/>
         </td>
         <td nowrap>Member Name:<br>
            <html:text property="claimantName" styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>
         
      </tr>
      <tr>
       
        <td nowrap>Claim Type:<br>
            <html:select property="claimType" styleClass="selectBox selectBoxMedium" >
				 <html:option value="">Select from list</html:option>
				 <html:optionsCollection name="claimType" label="cacheDesc" value="cacheId"/>
			</html:select>
		</td>
        <td nowrap>Policy Type:<br>
            <html:select property="policyType" styleClass="selectBox selectBoxMedium" >
				 <html:option value="">Any</html:option>
				 <html:optionsCollection name="policyType" label="cacheDesc" value="cacheId"/>
			</html:select>
		</td>
        <td nowrap>Payee:<br>
            <html:text property="payee" styleClass="textBox textBoxMedium" maxlength="60"/></td>
            <!-- start changes for cr koc 1103 and 1105 -->
            <td nowrap>Bank A/c Details:<br>
            
           	<html:select property="paymethod1" styleClass="selectBox selectBoxMedium">
        		 <html:option value="EFT">With Bank Details</html:option>
                 <html:option value="OTH">Without Bank Details</html:option>
           	</html:select>
		  </td>
		  <td nowrap>Incurred Currency Format:<br>
		  <html:select property="incuredCurencyFormat" styleId="incuredCurencyFormat">
		 <%--   <html:option value="">Select From List</html:option> --%>
		  <html:optionsCollection name="curencyTypeList" label="cacheDesc" value="cacheId"/>
		  </html:select>
		  </td>
		  
		
            <td nowrap>Source Type:<br>
            
           	<html:select property="sourcetype" styleClass="selectBox selectBoxMedium">
        		  <html:option value="">All</html:option>
        		 <html:option value="N">GN data</html:option>
                 <html:option value="Y">Migrated data</html:option>
           	</html:select>
		  </td>
		<!-- end changes for cr koc 1103 and 1105-->
        <td width="100%" valign="bottom" nowrap>
        <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
        
      </tr>
    </table>
	<!-- E N D : Search Box -->
	
	

    <!-- S T A R T : Grid -->
    <ttk:HtmlGrid name="tableData"/>
    <!-- E N D : Grid -->

    <!-- S T A R T : Buttons and Page Counter -->
    <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
     <tr>
	          <td><font color="color:#495879;">Show Template :</font><a href="#" onClick="javascript:showFinanceTemplate()" >Claim Finance Status</a><br/><br/><br/></td>
		  </tr>		
      <tr>
      	<td width="100%" height="28" nowrap class="fieldGroupHead-r">
      		File Name : 	<html:file property="stmFile"/>  
      		  		<!--<ttk:PageLinks name="tableData"/>
      	--></td>
        <td>&nbsp;</td>
        <td height="28" nowrap class="fieldGroupHeader">Available Float Balance :<br>
            <html:text property="avblFloatBalance" styleClass="textBox textBoxMedium textBoxDisabled" disabled="true"/></td>
        <td nowrap class="fieldGroupHeader">Total Amt. Selected :<br>
            <html:text property="totalAmt" styleClass="textBox textBoxMedium textBoxDisabled" disabled="true"/>
        </td>
        <td height="28" nowrap class="fieldGroupHeader">Available Balance :<br>
            <html:text property="availBalance" styleClass="textBox textBoxMedium textBoxDisabled" disabled="true"/></td>
      </tr>
     <tr>
       <td width="100%" height="28" nowrap class="fieldGroupHeader"></td>
        <td height="28" nowrap class="fieldGroupHeader">&nbsp;</td>
       <td height="28" nowrap class="fieldGroupHeader">Converted Total Amount :<br>
            <input type="text" id="convertedTotalAmt"  class="textBox textBoxMedium textBoxDisabled" readonly="readonly"/></td>
           <td height="28" nowrap class="fieldGroupHeader"><br>
           <html:text property="currencyFormat"  styleClass="textBox textBoxTooTiny textBoxDisabled" disabled="true"/>
           </td>
      </tr>
      <tr>
      <td width="100%" height="28" nowrap class="fieldGroupHeader"></td>
        <td height="28" nowrap class="fieldGroupHeader">&nbsp;</td>
    
          <td nowrap class="fieldGroupHeader"> Payment Method:<br>
        	<html:select property="paymethod" styleClass="selectBox selectBoxMedium" onchange="javascript:enableField(this)" >
				 <html:optionsCollection name="paymentMethod" label="cacheDesc" value="cacheId"/>
			</html:select>
		</td>
               
      
        <td height="28" nowrap class="fieldGroupHeader">Starting Cheque No.:<br>
        <logic:match name="frmClaims" property="paymethod" value="PMM">
            <html:text property="startNo" styleId="startNo" styleClass="textBox textBoxMedium" />
		</logic:match>
        <logic:notMatch name="frmClaims" property="paymethod" value="PMM">
        	<html:text property="startNo" styleId="startNo" disabled="true" styleClass="textBox textBoxMedium textBoxDisabled" />
        </logic:notMatch>
         </td>
          <td height="28" nowrap class="fieldGroupHeader">No. of Claims:<br>
                <html:text property="noofclaimssettlementnum" styleId="noofclaimssettlementnum" styleClass="textBox textBoxMedium textBoxDisabled" disabled="true" />
          </td>
        </tr>
        
        <tr>
      	<td width="100%" height="28" nowrap class="fieldGroupHeader">
      		<!--<ttk:PageLinks name="tableData"/> 	-->
      		</td>
        <td>&nbsp;</td>
        <td height="28" nowrap class="fieldGroupHeader" colspan="3">Remarks:<br>
        <logic:match name="frmClaims" property="paymethod" value="EFT">
            <html:textarea property="remarks" styleClass="textBox textAreaMediumht" />
        </logic:match>
        <logic:notMatch name="frmClaims" property="paymethod" value="EFT">
            <html:textarea property="remarks" styleClass="textBox textAreaMediumht textAreaMediumhtDisabled" disabled="true"/>
        </logic:notMatch></td>
      </tr>
     
      <tr>
        <td  height="28" nowrap class="fieldGroupHeader">
      		<!-- <button type="button" name="uploadButton" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"     onClick="javascript:oncheckDebit();onUploadClaimSettlementNumber()"><u>U</u>pload File</button> -->
      	<button type="button" name="uploadButton" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"     onClick="javascript:onUploadClaimSettlementNumber()"><u>U</u>pload File</button>
      		</td>
        <td>&nbsp;</td>
        <!--<td valign="bottom" nowrap>-->
        <td>
        <%
        
		if(TTKCommon.isAuthorized(request,"Print Cheque"))
    		{
			if(TTKCommon.isDataFound(request,"tableData"))
		     {

    	%>
            <button type="button" name="Button2" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onPrint()"><u>P</u>rint Cheque</button>&nbsp;
        <%
		     }
    		}
        %>
        </td>
         <td nowrap class="fieldGroupHeader">&nbsp;</td>
        <td height="28" nowrap class="fieldGroupHeader">&nbsp;</td>
        </tr>   
        <tr>
        <td width="100%" height="28" nowrap class="fieldGroupHeader">
        </td>
         <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
          </table>
          
           <fieldset style="width:35%;"><legend>Log Details</legend>
          <table align="left"   width="85%"  border="0"  style="padding-left:17px;border-left-width:29px;"  cellspacing="0" cellpadding="0">
        <tr>
          
           <td width="50%" height="28">
         
           Start Date:<br/>
           <html:text property="startDate" styleClass="textBox textDate" maxlength="10"/>
				<A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','frmClaims.startDate',document.frmClaims.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
           </td>
           <td width="50%" height="28">
           End Date:<br/>
           <html:text property="endDate" styleClass="textBox textDate" maxlength="10"/>
	        	<A NAME="calEndDate" ID="calEndDate" HREF="#" onClick="javascript:show_calendar('calEndDate','frmClaims.endDate',document.frmClaims.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
         
             <a href="#" accesskey="s" onClick="javascript:onLogSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
          
            </td>
              
        </tr>
             </table>
        </fieldset> 
    </div>
    </logic:notMatch>
    <!-- E N D : Buttons and Page Counter -->
    <INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<input type="hidden" name="child" value="">
	<html:hidden name="frmClaims" property="debitSeqID"/>
	<html:hidden name="frmClaims" property="debitNumber"/>
	<html:hidden name="frmClaims" property="floatType"/>
	
	</html:form>
