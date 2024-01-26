<%
/**
 * @ (#) associateofficelist.jsp
 * Project      : Vidal Health TPA  Services
 * File         : associateofficelist.jsp
 * Author       : Balaji C R B
 * Company      : Span Systems Corporation
 * Date Created : Oct 16,2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="java.util.ArrayList,com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	ArrayList alOfficeList=(ArrayList)request.getAttribute("alOfficeList");
%>
<script language="javascript" src="/ttk/scripts/administration/associateofficelist.js"></script>
<!-- S T A R T : Content/Form Area -->

<html:form action="/AssociateOfficeListAction.do" >
<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0"
		cellpadding="0">
		<tr>
			<td width="51%"><bean:write name="frmAssociateOffice" property="caption"/></td>
		</tr>
	</table>
	<!-- E N D : Page Title  --> 
<div class="contentArea" id="contentArea">	
<table align="center" class="gridWithCheckBox"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
        <td width="50%" class="gridHeader" nowrap align="absmiddle" border="0">Company Code&nbsp;&nbsp</td>
        <td width="47%" class="gridHeader" nowrap>Office Type</td>
        <td width="3%" align="center" nowrap class="gridHeader"><input type="checkbox" id="checkall" name="checkall" value="checkbox" onClick="javascript:oncheck('checkall')"></td>
	</tr>
	<logic:notEmpty name="alOfficeList">
		<logic:iterate id="officeVO" name="alOfficeList" indexId="i">
		 <tr class="<%=(i.intValue()%2)==0? "gridOddRow":"gridEvenRow"%>">	
				<td><bean:write name="officeVO" property="path"/></td>	
				<td><bean:write name="officeVO" property="officeType"/></td>
				<td><input type="checkbox" id="chkid<%=i.intValue()%>" name="chkopt" value="<bean:write name="officeVO" property="insSeqId"/>" onClick="javascript:oncheck(this.id)"></td>					        
			</tr>
		</logic:iterate>
	</logic:notEmpty>
</table>
<!-- S T A R T : Buttons and Page Counter -->
<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="27%" align="left"> </td>
    <td width="73%" nowrap align="right">
    <%
                 if(TTKCommon.isAuthorized(request,"Edit"))
                 {
    %>
    <logic:notEmpty name="alOfficeList">
     
    <button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAssociate()"><u>A</u>ssociate</button>&nbsp;
    </logic:notEmpty>
    <%
    		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	%>
    
	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button></td>    
  </tr>
</table>
</div>
<logic:notEmpty name="frmAssociateOffice" property="sOffIds">
<script language="javascript">selectValues("<bean:write name="frmAssociateOffice" property="sOffIds"/>");</script>
</logic:notEmpty>
<INPUT TYPE="hidden" NAME="sOffIds" VALUE="">
<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>	
