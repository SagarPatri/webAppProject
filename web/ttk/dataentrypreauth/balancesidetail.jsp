<%
/** @ (#) balancesidetail.jsp September 14, 2009
 * Project       : TTK Healthcare Services
 * File          : balancesidetail.jsp
 * Author        : Navin Kumar R
 * Company     	 : Span Systems Corporation
 * Date Created  : September 14, 2009
 *
 * @author 		 : Navin Kumar R
 * Modified by   : 
 * Modified date : 
 * Reason        : //Modification 1216BCR Buffer Allocation as Buffer Allocation-Mode and Buffer Amount as Buffer Family /Member Cap
  */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="JavaScript" src="/ttk/scripts/preauth/balancesidetail.js"></script>
<%
  boolean viewmode=true;
  String strAmmendment="";
  String strLink=TTKCommon.getActiveLink(request);
  pageContext.setAttribute("strLink",strLink);
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }//end of if(TTKCommon.isAuthorized(request,"Edit"))
  pageContext.setAttribute("ActiveLink",TTKCommon.getActiveLink(request));
  pageContext.setAttribute("ActiveSubLink",TTKCommon.getActiveSubLink(request));
%>
<!-- S T A R T : Content/Form Area -->

<html:form action="/SIAuthorizationDetailsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
    	<tr>
			<td>Member Info - <bean:write name="frmSIAuthorizationDetails" property="caption"/></td>			
		</tr>
    </table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
	<div class="contentArea" id="contentArea">
		<html:errors/>
		<logic:match name="frmSIAuthorizationDetails" property="stopPreClmVO.stopPatClmYN" value="Yes">
		<fieldset>
		<legend>General</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" >
				<tr>
					<td width="23%" height="20" class="formLabel">Cashless/Claims:</td>
					<td width="27%" class="textLabel"><strong>
					 Not Allowed after <bean:write name='frmSIAuthorizationDetails' property='stopPreClmVO.patClmRcvdAftr'/>	
					</strong></td>	
					<td width="50%" class="formLabelBold">									
				</tr>
		</table>
		</fieldset>
		</logic:match>
		<fieldset>
			<legend>Balance SI details</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" >
				<tr>
					<td width="23%" height="20" class="formLabel">Scheme Sub Type:</td>
					<td width="27%" class="formLabelBold">
						<bean:write name="frmSIAuthorizationDetails" property="balanceSIInfoVO.polSubType"/>
					</td>
					<!-- Modified as per KOC 1216B -->
					<td width="23%" height="20" class="formLabel">Buffer Allocation-Mode:</td>
					<td width="27%" class="formLabelBold">
						<bean:write name="frmSIAuthorizationDetails" property="balanceSIInfoVO.bufferAllocation"/>
					</td>					
				</tr>					
				<tr>
					<td height="20" class="formLabel">Total Sum Insured (Rs):</td>
					<td class="formLabelBold">
						<bean:write name="frmSIAuthorizationDetails" property="balanceSIInfoVO.totalSumInsured"/>
					</td>
					<td height="20" class="formLabel">Utilized Sum Insured (Rs):</td>
					<td class="formLabelBold">
						<bean:write name="frmSIAuthorizationDetails" property="balanceSIInfoVO.utilizedSumInsured"/>
					</td>					
				</tr>
				<tr>
					<td height="20" class="formLabel">Cumulative Bonus (Rs):</td>
					<td class="formLabelBold">
						<bean:write name="frmSIAuthorizationDetails" property="balanceSIInfoVO.bonus"/>
					</td>
					<td height="20" class="formLabel">Utilized Cumu. Bonus (Rs):</td>
					<td class="formLabelBold">
						<bean:write name="frmSIAuthorizationDetails" property="balanceSIInfoVO.utilizedBonus"/>
					</td>					
				</tr>
				<!-- Modified as per KOC 1216B -->
				<tr>
                   <td height="20" class="formLabel">Buffer Family/Member Cap (Rs):</td>
					<td class="formLabelBold">
						<bean:write name="frmSIAuthorizationDetails" property="balanceSIInfoVO.bufferAmt"/>
					</td>
					<td height="20" class="formLabel">Utilized Buffer Amt. (Rs):</td>
					<td class="formLabelBold">
						<bean:write name="frmSIAuthorizationDetails" property="balanceSIInfoVO.utilizedBufferAmt"/>
					</td>
				</tr>				
				<logic:equal name="frmSIAuthorizationDetails" property="memberBufferVO.memberBufferYN" value="Y">
				<tr>
					<td height="20" class="formLabel">Member Buffer Amt. (Rs):</td>
					<td class="formLabelBold">
						<bean:write name="frmSIAuthorizationDetails" property="memberBufferVO.bufferAmtMember"/>
					</td>
					<td height="20" class="formLabel">Utilized Member Buffer Amt. (Rs):</td>
					<td class="formLabelBold">
						<bean:write name="frmSIAuthorizationDetails" property="memberBufferVO.utilizedBufferAmtMember"/>
					</td>
				</tr>	
				</logic:equal>			
			</table>
		</fieldset>
		
			<!-- added for koc1289_1272  -->
				 
		<logic:match name="frmSIAuthorizationDetails" property="restorationPreauthClaimVO.restorationYN" value="Y">
		<fieldset>
		<legend>Restoration Sum Insured</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" >
				<tr>
					<td width="23%" height="20" class="formLabel">Restoration Sum Insured :</td>
						<td class="formLabelBold">
						<bean:write name="frmSIAuthorizationDetails" property='restorationPreauthClaimVO.restSumInsured'/>
					
					</td>									
				</tr>
		</table>
		</fieldset>
		</logic:match>
		
				
				<!-- end added for koc1289_1272  -->
		<fieldset>
			<legend>SI Breakup</legend>
    		<!-- S T A R T : Grid -->
    		<ttk:HtmlGrid name="tableData"/>
    		<!-- E N D : Grid -->
    	</fieldset>
		<%--Changes as per KOC1142 Change Request--%>
    	<fieldset>
			<legend>Copay and SumInsured Limits</legend>
    				<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" >
    		<tr>
					<td height="20" class="formLabel">Max Approved Amount Limit. (Rs):					
						<bean:write name="frmSIAuthorizationDetails" property="balCopayDeducVO.bdApprovedAmt"/>
					</td>
					<td height="20" class="formLabel">Max Copay Amount Limit. (Rs):
						<bean:write name="frmSIAuthorizationDetails" property="balCopayDeducVO.bdMaxCopayAmt"/>
					</td>
				</tr>
				</table>		
    	</fieldset>
		<!-- S T A R T : Buttons and Page Counter -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td align="center">
    				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
    			</td>
  			</tr>
		</table>
		<!-- E N D : Buttons and Page Counter -->    		
	</div>
	<!-- E N D : Buttons and Page Counter -->
  	<input type="hidden" name="mode" value="">
  	<input type="hidden" name="child" value="">
  	<input type="hidden" name="rownum" value="">  	  	
</html:form>

