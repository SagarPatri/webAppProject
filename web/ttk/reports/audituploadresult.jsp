
<%
	/** @ (#) audituploadresult.jsp
	 * Project     : TTK Healthcare Services
	 * File        : audituplpadresult.jsp
	 * Author      :  Rishi Sharma
	 * Company     : RCS Technologies
	 * Date Created:  08 August 2017
	 * @author 		 :Rishi Sharma
	 * Modified by   :
	 * Modified date :
	 * Reason        :
	 *
	 */
%>
<%@ page
	import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/reports/audituploadresult.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>

<!-- <link rel="stylesheet" type="text/css" href="css/autoComplete.css" /> -->
<!-- <script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script> -->
	<html:errors/>
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
<%
	pageContext.setAttribute("listSwitchType",Cache.getCacheObject("claims"));

%>
<html:form action="/ReportsAction.do"  method="post"   enctype="multipart/form-data">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0"
		cellpadding="0">
		<tr>
			<td>Audit Result Upload</td>
		
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors />

	<table align="center" class="tablePad" border="0" cellspacing="0"
		cellpadding="0">
		<tr>
			<td width="10%" nowrap class="textLabelBold">Switch to:</td>
			<td width="90%"><html:select   property="switchType" styleId="switchType" name="frmReportList"	styleClass="specialDropDown" styleId="switchType" onchange="javascript:onSwitchAuditResultUpload()">
                 <html:options collection="listSwitchType" property="cacheId" labelProperty="cacheDesc" />
				</html:select></td>
				
				  
		</tr>
	</table>
	<!-- Start of form fields -->
	<!-- Start of Parameter grid -->
	
		<fieldset>
			<legend>Audit Results Upload </legend>
			<table border="0" align="center" cellpadding="0" cellspacing="0"    style="height:80px;">
				<tr>
				<td class="formLabel">File Name : <html:file  property="stmFile"/> </td>
	        <!-- 	<td>
				 </td> -->
			   </tr>
			     <tr>
			     	<logic:equal property="reportName" name="frmReportList"
						scope="session" value="CLM">
	          <td><font color="color:#495879;">Show Template :</font><a href="#" onClick="javascript:showAuditPreAuthClaimTemplate()" >Claim Audit Template</a><br/><br/><br/></td>
		       </logic:equal>
		       	<logic:equal property="reportName" name="frmReportList"
						scope="session" value="PAT">
	          <td><font color="color:#495879;">Show Template :</font><a href="#" onClick="javascript:showAuditPreAuthClaimTemplate()" >Pre-Authorization Audit Template</a><br/><br/><br/></td>
		       </logic:equal>
		  </tr>	
				<tr>
        <td  align="center" height="28" nowrap class="fieldGroupHeader">
      		<button type="button" name="uploadButton" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUploadAuditPreAuthClaim()"><u>U</u>pload File</button>
      		</td>
        <td><button type="button" name="Button" accesskey="c" class="buttons"
						onMouseout="this.className='buttons'"
						onMouseover="this.className='buttons buttonsHover'"
						onClick="javascript:onClose();">
						<u>C</u>lose
					</button></td>
        <!--<td valign="bottom" nowrap>-->
      </tr>
			</table>

		</fieldset>

       <fieldset style="width:70%;margin-top:40%; margin-left:30%;"><legend>Log Details</legend>
          <table align="left"   width="90%"  border="0"  style="padding-left:17px;border-left-width:29px;"  cellspacing="0" cellpadding="0">
        <tr>
          
           <td width="70%" height="28">
         
           Start Date:
           <html:text property="startDate" styleClass="textBox textDate" maxlength="10"/>
				<A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','frmReportList.startDate',document.frmReportList.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
           </td>
           <td width="90%" height="28">
           End Date:
           <html:text property="endDate" styleClass="textBox textDate" maxlength="10"/>
	        	<A NAME="calEndDate" ID="calEndDate" HREF="#" onClick="javascript:show_calendar('calEndDate','frmReportList.endDate',document.frmReportList.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
            </td>
            <td width="50%" height="28">
             <a href="#" accesskey="s" onClick="javascript:onSearchLog()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
            </td>
         
              
        </tr>
             </table>
        </fieldset> 

	

	<!-- End of Report Type - PDF/EXCEL list and generate button -->
	<!-- End of form fields -->
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>