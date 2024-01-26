<%
/** @ (#) chequelist.jsp 12th June 2006
 * Project     	: TTK Healthcare Services
 * File        	: chequelist.jsp
 * Author      	: Harsha Vardhan B N
 * Company     	: Span Systems Corporation
 * Date Created	: 12th June 2006
 *
 * @author 		 : Harsha Vardhan B N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ttk.common.security.Cache,com.ttk.common.TTKCommon" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/finance/chequelist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true;
</script>
<%
	pageContext.setAttribute("InsCompanyID",Cache.getCacheObject("insuranceCompany"));
	pageContext.setAttribute("claimType",Cache.getCacheObject("claimType"));
	pageContext.setAttribute("chequeStatus",Cache.getCacheObject("chequeStatus"));
	pageContext.setAttribute("paymentMethod", Cache.getCacheObject("paymentMethod"));
	
	String newFileName=(String)request.getAttribute("newFileName")==null?"":(String)request.getAttribute("newFileName");
%>

<html:errors/>


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


	<logic:notEmpty name="notifyerror" scope="session">
	
		<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	    
	        <%=request.getSession().getAttribute("notifyerror")%>
	        </td>
	      </tr>
   	 </table>
   	 </logic:notEmpty>
   	 <%  session.removeAttribute("notifyerror");%>
   	 	<logic:notEmpty name="notifyerror" scope="session">
	
		<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	    
	        <%=request.getSession().getAttribute("notifyerror")%>
	        </td>
	      </tr>
   	 </table>
   	 </logic:notEmpty>
   	 <%  session.removeAttribute("notifyerror");%>
   	 	<logic:notEmpty name="notifysuccess" scope="session">
   	 	<table align="center" class="successContainer" style="display:"
					border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
							width="16" height="16" align="absmiddle">&nbsp;  
							  <%=request.getSession().getAttribute("notifysuccess")%></td>
					</tr>
				</table>
				</logic:notEmpty>
				 <%  session.removeAttribute("notifysuccess");%>


<!-- E N D : Tab Navigation -->
	<!-- S T A R T : Content/Form Area -->
	<html:form action="/ChequeSearchAction.do" method="post"   enctype="multipart/form-data" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		<tr>
        	<td>List of Cheques</td>
  		</tr>
	</table>
<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<%-- <html:errors/> --%>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td nowrap>Payment Trans Ref no.:<br>
            <html:text property="sChequeNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>
        <td nowrap>Float Account No.:<br>
		    <html:text property="sFloatAccountNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
	    </td>
	    <td nowrap>Status:<br>
	        <html:select property="sStatus"  styleClass="selectBox selectBoxMedium">
	        	<html:option value="">Any</html:option>
  				<html:options collection="chequeStatus"  property="cacheId" labelProperty="cacheDesc"/>
	    	</html:select>
	    	</td>
   	 	<td nowrap>Start Date:<br>
      		<html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>
            <A NAME="CalendarObjectchqDate11" ID="CalendarObjectchqDate11" HREF="#" onClick="javascript:show_calendar('CalendarObjectchqDate11','forms[1].sStartDate',document.forms[1].sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="sStartDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
		</td>
		<td nowrap>End Date:<br>
	  		<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>
            <A NAME="CalendarObjectchqDate12" ID="CalendarObjectchqDate12" HREF="#" onClick="javascript:show_calendar('CalendarObjectchqDate12','forms[1].sEndDate',document.forms[1].sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="sEndDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
	 	 </td>
	  </tr>
      <tr>
		<td nowrap>Claim Settlement No.:<br>
            <html:text property="sClaimSettleNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>
		<td nowrap>Claim Type:<br>
	        <html:select property="sClaimType"  styleClass="selectBox selectBoxMedium" >
	        <html:option value="">Any</html:option>
  			<html:options collection="claimType"  property="cacheId" labelProperty="cacheDesc"/>
	    	</html:select>
		</td>
		<td nowrap>Insurance Company:<br>
	        <html:select property="sInsuranceCompany"  styleClass="selectBox selectBoxMedium" >
	        <html:option value="">Any</html:option>
  			<html:options collection="InsCompanyID"  property="cacheId" labelProperty="cacheDesc"/>
	    	</html:select>
		</td>
		<td nowrap>Insurance Code:<br>
            <html:text property="sCompanyCode"  styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>
        <td nowrap>Batch No.:<br>
            <html:text property="sBatchNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>
        <!--<td valign="bottom" nowrap><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>-->
        
      </tr>
       <tr>
      	
        <td nowrap>Policy No.:<br>
		    <html:text property="sPolicyNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
	    </td>
	   
	    
        <td nowrap>Enrollment Id:<br>
		    <html:text property="sEnrollmentId"  styleClass="textBox textBoxMedium" maxlength="60"/>
	    </td>
	    <td nowrap>Civil ID:<br>
		    	<html:text property="sEmiratesID" name="frmSearchCheques"  styleClass="textBox textBoxLarge" maxlength="250"/>
		    </td>
	    <td nowrap>Payment Method:<br>
	        <html:select property="sPaymentMethod"  styleClass="selectBox selectBoxMedium" >
	        	<html:option value="">Any</html:option>
  				<html:options collection="paymentMethod"  property="cacheId" labelProperty="cacheDesc"/>
	    	</html:select>
   	 	</td>
   	 	<td valign="bottom" nowrap><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
   	 	<td nowrap>&nbsp;</td>
        <td nowrap>&nbsp;</td>
   	  </tr>
    </table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="27%"></td>
  	    <ttk:PageLinks name="tableData"/>
  	  </tr>
	</table>
	<!--  bikki-->
	 <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	       <tr>
			     	
	          <td><font color="color:#495879;">Show Template :</font><a href="#" onClick="javascript:showChequeTemplate()" >Sample upload file</a><br/><br/><br/></td>
		       
		  </tr>	
				
		  <tr>
          	    <td width="100%" height="28" nowrap class="fieldGroupHead-r">
      		       File Name : 	<html:file property="stmFile"/>  	
               </td>
               
               <td>&nbsp;</td>
        </tr>
			
       <tr>
        <td>&nbsp;</td>
       </tr>
       
       <tr>
        <td  height="28" nowrap class="fieldGroupHeader">
      	<button type="button" name="uploadButton" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"     onClick="javascript:onUploadCheque()"><u>U</u>pload File</button>
      		</td>
        <td>&nbsp;</td>
          
     </tr>
          <tr>
        <td>&nbsp;</td>
       </tr>
			</table>
			
			<fieldset style="width:35%;"><legend>Log Details</legend>
          <table align="left"   width="85%"  border="0"  style="padding-left:17px;border-left-width:29px;"  cellspacing="0" cellpadding="0">
        <tr>
          
          <td width="50%" height="28">
         
           Start Date:<br/>
           <html:text property="startDate" styleClass="textBox textDate" maxlength="10"/>
				<A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','frmSearchCheques.startDate',document.frmSearchCheques.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
           </td>
          
           <td width="50%" height="28">
           End Date:<br/>
           <html:text property="endDate" styleClass="textBox textDate" maxlength="10"/>
	        	<A NAME="calEndDate" ID="calEndDate" HREF="#" onClick="javascript:show_calendar('calEndDate','frmSearchCheques.endDate',document.frmSearchCheques.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
            </td>
            <td width="50%" height="28">
             <a href="#" accesskey="s" onClick="javascript:onSearchLog()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
            </td>
              
        </tr>
             </table>
             </fieldset>
             
             
             <logic:equal value="Y"  property="successYN" name="frmSearchCheques">
          <fieldset align="right" style="width:50%;"><legend>Summary of your latest data uploaded</legend>
          <table    width="85%"  border="0"  style="padding-left:17px;border-left-width:29px;"  cellspacing="0" cellpadding="0">
             
           <logic:iterate id="mapEntry" name="frmSearchCheques" property="totalStatusCount">
    
           <tr>      
             <td><bean:write name="mapEntry" property="key"/></td>
             <td><bean:write name="mapEntry" property="value"/></td>
         </tr> 
        </logic:iterate>  
        
        <tr>
       <td>&nbsp;</td>
       </tr>
          <tr>
	 <td align="center"><b><a href="#" onClick="javascript:downloadErrorLog()">Click here </a>to download error log</b> </td>
	          
       </tr>
       
	<tr><td>&nbsp;</td></tr>
	<tr>
	       <td width="100%" colspan="2" >
	          (Please correct the records found in error log and re-submit the whole claim excel once again) 
	      </td>
	</tr>
</table>
</fieldset>
</logic:equal>

<!--bikki -->

 
   

</div>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
    <html:hidden property="fileName" name="frmSearchCheques"/>
    
   
</html:form>
<!-- E N D : Main Container Table -->