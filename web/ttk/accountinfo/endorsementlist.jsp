<%
/** @ (#) endorsementlist.jsp Aug 22, 2007
 * Project     : Vidal Health TPA Services
 * File        : endorsementlist.jsp
 * Author      : Balaji C R B
 * Company     : Span Systems Corporation
 * Date Created: Jul 28, 2007
 *
 * @author 		 : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>


<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<script language="JavaScript">
function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/PolicyAccountInfoDetailAction.do";
	document.forms[1].submit();
}//end of onViewEndorsements()
</script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/AccInfoEndorseAction.do" >

<!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>Endorsement List - [&nbsp;<bean:write name="frmAccInfoEndorsement" property="caption"/>&nbsp;]</td>
    </tr>
  </table>
<!-- E N D : Page Title -->


<!-- S T A R T : Form Fields -->
<div class="contentArea" id="contentArea">
<table align="center" class="gridWithCheckBox"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="gridHeader">Endorsement</br>No.</td>
        <td class="gridHeader">Cust. Endorsement</br>No.</td>
        <td class="gridHeader">Endorsement</br>Type</td>
        <td class="gridHeader">Recd.</br>Dt.</td>
        <td class="gridHeader">Effective</br>Dt.</td>
        <td class="gridHeader">Added No.</br>of Members</td>
        <td class="gridHeader">Modified No.</br>of Members</td>
        <td class="gridHeader">Deleted No.</br>of Members</td>
      </tr>

      <% int i=0; %>
		        <logic:iterate id="endorsementVO" name="frmAccInfoEndorsement" property="EndorsementList">
					<%
						String strClass=i%2==0 ? "gridOddRow" : "gridEvenRow" ;
						i++;
					%>
					<tr class=<%=strClass%>>
				    	<td><bean:write name="endorsementVO" property="endorsementNbr" />&nbsp;</td>
				        <td><bean:write name="endorsementVO" property="custEndorsementNbr" />&nbsp;</td>
				        <td><bean:write name="endorsementVO" property="endorsementType" />&nbsp;</td>
				        <td><bean:write name="endorsementVO" property="recdDate" />&nbsp;</td>
				        <td><bean:write name="endorsementVO" property="effectiveDate" />&nbsp;</td>
				        <td><bean:write name="endorsementVO" property="addMemberCnt" />&nbsp;</td>
				        <td><bean:write name="endorsementVO" property="updateMemberCnt" />&nbsp;</td>
				        <td><bean:write name="endorsementVO" property="deletedMemberCnt" />&nbsp;</td>				        				        
					</tr>
				</logic:iterate>
</table>
<table>
		<tr>
			<td class="buttonsContainerGrid">&nbsp;</td>
			<td align="right" class="buttonsContainerGrid">
				<button type="button" name="Button1" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>
			</td>
		</tr>   	
</table>
		    			
</div>
<!-- E N D : Form Fields -->
<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>