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
	//String flag 	=	(String)request.getAttribute("flag")==null?(String)request.getSession().getAttribute("flag"):(String)request.getAttribute("flag");
	String flag 	=	(String)request.getAttribute("flag");
	String logicVar 	=	(String)request.getAttribute("logicVar")==null?"showValidate":(String)request.getAttribute("logicVar");
	String logicValidateVar 	=	(String)request.getAttribute("logicValidateVar")==null?"showValidate":(String)request.getAttribute("logicValidateVar");
	String sOffIds	=	(String)request.getSession().getAttribute("sOffIds");
	if(flag!=null){
	if(flag.equalsIgnoreCase("true"))
	  {
	    viewmode=true;
	   // logicVar	=	"true";
	  }
	}
	//flag	=	"true";
	// out.println("logicVar :: "+logicVar);
	 /*out.println("hospSeqId :: "+hospSeqId);
	 */
	 String sReqAmntsArr[]=new String[50];
	
	ArrayList alDiagDataList=(ArrayList)request.getAttribute("alDiagDataList");
	
	
	
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
			        <td width="78%" colspan="3">
			          <html:text property="enrollId" name="frmCashlessAdd" styleClass="textBox textBoxLarge" disabled="<%=viewmode %>"/>
			        </td>
		      	</tr>
		    </table>
		    
		<br><br>
			<table border="0" align="center" cellpadding="0" cellspacing="0" class="gridWithCheckBox">
		<tr> 
            <td width="35%" class="gridHeader" style="font-size:12px;"  align='left'>&nbsp;Test Name</td> 
            <td width="35%" class="gridHeader" style="font-size:12px;" align='left'>&nbsp;Requested Amount</td></td>
            <!-- td width="20%" class="gridHeader" style="font-size:12px;" align='left'>&nbsp;Rate</td></td>
            <td width="20%" class="gridHeader" style="font-size:12px;" align='left'>&nbsp;Discount</td></td-->
        </tr>
        
		<logic:iterate id="CashlessDetailVO" name="alDiagDataList" indexId="i">
		 <tr class="<%=(i.intValue()%2)==0? "gridOddRow":"gridEvenRow"%>">	
				<td><bean:write name="CashlessDetailVO" property="testName"/></td>	
				<!-- td><html:text property="reqAmnt" name="frmCashlessAdd" styleClass="textBox" /></td-->
				<td><input type="text" name="amnt" id="chkid<%=i.intValue()%>" value="" class="righttd" onkeypress="return isNumberKey(event)" onblur="return calcAmt()"/></td>
				<td>&nbsp;</td>
				<!--td><bean:write name="CashlessDetailVO" property="diagSeqId"/></td>
				<td><bean:write name="CashlessDetailVO" property="discount"/></td-->
			</tr>
		</logic:iterate>
	
		<tr> 
            <td width="20%" class="gridHeader" style="font-size:12px;"  align='right'>&nbsp;Total Requested</td> 
            <td width="20%" class="gridHeader" style="font-size:12px;" align='left'>&nbsp;
            <input type="text" name="totalReqAmt" id="totalReqAmt" value=""readonly="readonly" size="10"> </td>
        </tr>
        
	<tr>	<td align="center">
			 <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="return onSubmitActualRate()"><u>S</u>ubmit</button>
			 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseReqAmnts();"><u>C</u>lose</button>&nbsp;		       
			 
		</td>	</tr>
	</table>
	
	
	<input type="hidden" name="mode" value="">
	
</html:form>