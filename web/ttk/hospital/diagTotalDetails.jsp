<%
/** @ (#) inscompanyinfo.jsp 25th Feb 2008
 * Project     : TTK Healthcare Services
 * File        : cashlessAdd.jsp
 * Author      : kishor kumar 
 * Company     : RCS Technologies
 * Date Created: 28/11/2014
 *
 * @author 		 : kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
 <%@ page import="java.util.ArrayList,com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %> 
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/hospital/cashlessAddNew.js"></script>

<%
	boolean viewmode=false;
	boolean bEnabled=false;
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	pageContext.setAttribute("altest",Cache.getCacheObject("dctest"));
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	
	Long hospSeqId	=	userSecurityProfile.getHospSeqId();
	String flag 	=	(String)request.getAttribute("flag");
	String logicVar 	=	(String)request.getAttribute("logicVar")==null?"showValidate":(String)request.getAttribute("logicVar");
	String logicValidateVar 	=	(String)request.getAttribute("logicValidateVar")==null?"showValidate":(String)request.getAttribute("logicValidateVar");
	

	String submitRates		=(String)request.getAttribute("submitRates");
	
	ArrayList alDiagnosysEnrolResult=(ArrayList)request.getSession().getAttribute("alDiagnosysEnrolResult");

	ArrayList alDiagnosysTotalResult=(ArrayList)request.getSession().getAttribute("alDiagnosysTotalResult");
	
	if(flag!=null){
		if(flag.equalsIgnoreCase("true"))
		  {
		    viewmode=true;
		  }
		}
	
%>	
<html:form action="/OnlineCashlessHospAction.do" >


	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>Add Cashless Details  </td>     
    		<td width="43%" align="right" class="webBoard">&nbsp;</td>
  		</tr>
	</table>
	<html:errors/>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
	<div class="contentArea" id="contentArea">
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

		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    	<tr>
			        <td width="22%" class="formLabel">Enrollment Id :</td>
			        <td width="78%" colspan="2">
			          <html:text property="enrollId" name="frmCashlessAdd" styleClass="textBox textBoxLarge" disabled="true"/>
			        </td>
		      	</tr>
		    </table>
	<br><br>
	<logic:iterate id="CashlessVO" name="alDiagnosysTotalResult" indexId="i">
	
	<table align="left" border="0" cellspacing="0" cellpadding="0">
	<tr align="left">	
	<td align='left'><strong>Cashless Number : 
			<font color="black"><b><bean:write name="CashlessVO" property="authNumb"/></b></font></strong></td>
	</tr><tr align="left">	
	<td align='left'><strong>Cashless Status:
			<font color="black"><b><bean:write name="CashlessVO" property="preAuthStatus"/></b></font></strong></td>
	</tr>
	</table>
	<br><br>
	</logic:iterate>
	
	<br>	
	<br>
	<table border="0" align="center" cellpadding="0" cellspacing="0" class="gridWithCheckBox">
		<tr> 
            <td width="40%" class="gridHeader" style="font-size:12px;"  align='left'>&nbsp;Test Name</td> 
            <td width="15%" class="gridHeader" style="font-size:12px;" align='left'>&nbsp;Requested Amount</td></td>
            <td width="15%" class="gridHeader" style="font-size:12px;" align='left'>&nbsp;Allowed Amount</td></td>
            <td width="15%" class="gridHeader" style="font-size:12px;" align='left'>&nbsp;Discount %</td></td>
            <td width="15%" class="gridHeader" style="font-size:12px;" align='left'>&nbsp;Final Amount</td></td>
        </tr>
        
	<logic:notEmpty name="alDiagnosysEnrolResult">
	
		<logic:iterate id="CashlessDetailVO" name="alDiagnosysEnrolResult" indexId="i">
		 <tr class="<%=(i.intValue()%2)==0? "gridOddRow":"gridEvenRow"%>">	
				<td><bean:write name="CashlessDetailVO" property="testName"/> </td>	
				<td align="right"><bean:write name="CashlessDetailVO" property="enteredRate"/></td>
				<td align="right"><bean:write name="CashlessDetailVO" property="agreedRate"/></td>
				<td align="right"><bean:write name="CashlessDetailVO" property="discount"/></td>
				<td align="right"><bean:write name="CashlessDetailVO" property="discountRate"/></td>
			</tr>
		</logic:iterate>
		
	<logic:iterate id="CashlessVO" name="alDiagnosysTotalResult" indexId="i">
	 <tr class="<%=(i.intValue()%2)==0? "gridOddRow":"gridEvenRow"%>">	
	 		<td><strong>Total</strong></td>
			<td align="right"><strong><bean:write name="CashlessVO" property="totalEnteredRate"/> </strong></td>
			<td align="right"><strong><bean:write name="CashlessVO" property="totalAgreedRate"/> </strong></td>
			<td> &nbsp;</td>
			<td align="right"><strong><bean:write name="CashlessVO" property="totalDiscRate"/>
			 </strong></td>
		</tr>
	</logic:iterate>
	
	
	</logic:notEmpty>
	
	</table>
	
	<br>
	<br>
	<!-- Remarks Table -->
	<table align="left" border="0" cellspacing="0" cellpadding="0">
	<tr align="left">	
	<td align='left'><strong>Remarks : 
			<font color="black"><b><bean:write name="CashlessVO" property="dcRemarks"/></b></font></strong></td>
	</table>
	
	<br><br><br>
	<table  align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>	<td align="center" colspan="5">
            <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport()"><u>G</u>enerate Letter</button>
            </td> 
        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td align="center" colspan="5">
			 <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseOTP();"><u>C</u>lose</button>&nbsp;
		</td>
		</tr>
	</table>
	<input type="hidden" name="mode" value="">
	
</html:form>