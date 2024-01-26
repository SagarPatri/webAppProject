<%/**
 * @ (#) VATConfiguration.jsp
 * Project       : TTK HealthCare Services
 * File          : addNewPremiumEffectivePeriod.jsp
 * Author        : Deepthi Meesala
 * Company       : RCS
 * Date Created  : Oct 3rd,2018	 
 
 */
 
 %>



<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
 <%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>



<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>

<script language="javascript" src="/ttk/scripts/administration/productPremiumConfiguration.js"></script> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
</script>



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body>
<div class="contentArea" id="contentArea">
<html:form action="/Configuration.do" method="post">
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td>Configuration Information - <%=request.getSession().getAttribute("ConfigurationTitle")%> </td>
		<td align="right" >&nbsp;</td>     
    </tr>
	</table>
<html:errors/>
<%-- <%if(request.getParameter("successMsg")!=null){ %> --%>
<logic:notEmpty name="successMsg" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="middle">&nbsp;
						<bean:write name="successMsg" scope="request"/>
				  </td>
				</tr>
			</table>
	</logic:notEmpty>



  <logic:equal  name="frmRenewalDays"  property="capitationYN" value="1">

   <fieldset> 	
   	        <legend>ASO Policy</legend>
            <table align='center' class='formContainer' border='0' cellspacing='0' cellpadding='0' bgcolor='#EEEEEE'>
           
           <tr>
	    			<td class="formLabel">Premium Effective From Date: <span class="mandatorySymbol">*</span>
	    			
           <html:text property="asoFromDate" styleClass="textBox textDate" maxlength="10" />
           <a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmRenewalDays.asoFromDate',document.frmRenewalDays.asoFromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
	    						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
	    					</a>
           
           </td>
           
         <td class="formLabel">Premium Effective To Date: 
	    			
           <html:text property="asoToDate" styleClass="textBox textDate" maxlength="10" />
           <a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmRenewalDays.asoToDate',document.frmRenewalDays.asoToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
	    						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
	    					</a>
           
           </td>  
           
           <td> <a href='#' accesskey='a' onClick='javascript:addNewConfigurationDetailsASO()'><img src='/ttk/images/AddIcon.gif' ALT='Add Capitation-NO' width=13 height=13 border='0' align='absmiddle'></a>
           </td>
           
           <td> <!-- <A accessKey=o onclick='javascript:onDeleteIconCapitationYN();' href='#'><IMG border=0 alt='Delete' src='/ttk/images/DeleteIcon.gif' width=16 height=16></A> -->
         </td>
           </tr>
            </table>
            
            <table align='center' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0' style='overflow:scroll'>
            <tr>
            <th align='center' class='gridHeader' title='Serial Number'>S.No</th>
            <th align='center' class='gridHeader' title='Minimum Age'>Minimum Age</th>
            <th align='center' class='gridHeader' title='Maximum Age'>Maximum Age </th>
            <th align='center' class='gridHeader' title='MaritalStatus'>MaritalStatus</th>
            <th align='center' class='gridHeader' title='Gender Applicable'>Gender Applicable</th>
            <th align='center' class='gridHeader' title='Applicable To Relation'>Applicable To Relation </th>
            <th align='center' class='gridHeader' title='Gross Premium'>Gross Premium</th>
            <th align='center' class='gridHeader' title='Delete'>Delete</th>
            </tr>
            </table>
            </fieldset>
	  </logic:equal>      




<logic:notEqual name="frmRenewalDays"  property="capitationYN" value="1">

<fieldset> 	
   	        <legend>AS Plus Policy</legend>
            <table align='center' class='formContainer' border='0' cellspacing='0' cellpadding='0' bgcolor='#EEEEEE'>
           
           <tr>
	    			<td class="formLabel">Premium Effective From Date: <span class="mandatorySymbol">*</span>
	    			
           <html:text property="asPlusFromDate" styleClass="textBox textDate" maxlength="10" />
           <a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmRenewalDays.asPlusFromDate',document.frmRenewalDays.asPlusFromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
	    						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
	    					</a>
           
           </td>
           
         <td class="formLabel">Premium Effective To Date: 
	    			
           <html:text property="asPlusToDate" styleClass="textBox textDate" maxlength="10" />
           <a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmRenewalDays.asPlusToDate',document.frmRenewalDays.asPlusToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
	    						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
	    					</a>
           
           </td>  
           
           <td> <a href='#' accesskey='a' onClick='javascript:addNewConfigurationDetailsASPlus()'><img src='/ttk/images/AddIcon.gif' ALT='Add Capitation-NO' width=13 height=13 border='0' align='absmiddle'></a>
           </td>
           
           <td> <!-- <A accessKey=o onclick='javascript:onDeleteIconCapitationYN();' href='#'><IMG border=0 alt='Delete' src='/ttk/images/DeleteIcon.gif' width=16 height=16></A> -->
         </td>
           </tr>
            </table>
            
            <table align='center' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0' style='overflow:scroll'>
            <tr>
            <th align='center' class='gridHeader' title='Serial Number'>S.No</th>
            <th align='center' class='gridHeader' title='Minimum Age'>Minimum Age</th>
            <th align='center' class='gridHeader' title='Maximum Age'>Maximum Age </th>
            <th align='center' class='gridHeader' title='MaritalStatus'>MaritalStatus</th>
            <th align='center' class='gridHeader' title='Gender Applicable'>Gender Applicable</th>
            <th align='center' class='gridHeader' title='Applicable To Relation'>Applicable To Relation </th>
            <th align='center' class='gridHeader' title='Gross Premium'>Gross Premium</th>
            <th align='center' class='gridHeader' title='Delete'>Delete</th>
            </tr>
            </table>
            </fieldset>




</logic:notEqual>








    <table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="center">
			<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onPremiumPeriodClose();"><u>C</u>lose</button>
			</td>
			
		</tr>
	</table>









<INPUT TYPE="hidden" NAME="rownum" VALUE="">
<INPUT TYPE="hidden" NAME="premiumConfigSeqId" VALUE="">
<input type="hidden" name="mode">
 <INPUT TYPE="hidden" NAME="sortId" VALUE="">
	    <INPUT TYPE="hidden" NAME="pageId" VALUE="">
	      <INPUT TYPE="hidden" NAME="pageId" VALUE="">
<html:hidden name="frmRenewalDays"  property="capitationYN"/>
<html:hidden name="frmRenewalDays"  property="authority_type"/>
 <html:hidden name="frmRenewalDays" property="healthAuthority" styleId="healthAuthority"  />
 <html:hidden name="frmRenewalDays"  property="forward"/>
<!-- <input type="hidden" name="tab"> -->
</html:form>
</div>
</body>
</html>