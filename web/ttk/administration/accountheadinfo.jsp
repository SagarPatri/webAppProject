<%/**
 * @ (#) accountheadinfo.jsp Aug 13th 2008
 * Project       :Vidal Health TPA Services
 * File          : accountheadinfo.jsp
 * Author        : Chandrasekaran J
 * Company       : Span Systems Corporation
 * Date Created  : Aug 13th 2008
 * @author       : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/accountheadinfo.js"></script>

<%
	ArrayList alGroupList=(ArrayList)session.getAttribute("alGroupList");
%>

<!-- S T A R T : Content/Form Area -->	
	<html:form action="/AccountHeadInfoAction.do"> 	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Accountheads Configuration -<bean:write name="frmAccountHeadInfo" property="caption"/></td>
	  </tr>
	</table>
	<!-- E N D : Page Title --> 	
	<div class="contentArea" id="contentArea">
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
	<!-- S T A R T : Form Fields -->
	
 <fieldset><legend>Accounthead List</legend>
 
 <table border="0" align="center" cellpadding="0" cellspacing="0" class="gridWithCheckBox zeroMargin">
  <tr valign="middle">    
 	<td class="gridHeader" style="height:30px;padding-left:5px">Accounthead </td>
    
    <td align="center" class="gridHeader" style="height:30px;padding-left:5px">Include YN</td>
    
   	<td align="center" class="gridHeader" style="height:30px;padding-left:5px">Group Names</td>
   	
   	<td align="center" class="gridHeader" style="height:30px;padding-left:5px">Display in Auth Letter</td>
  </tr>
 <logic:empty name="frmAccountHeadInfo" property="authaccheadlist">
	<tr>
		<td class="generalcontent" colspan="7">&nbsp;&nbsp;No Data Found</td>
	</tr>
 </logic:empty> 
 <logic:notEmpty name="frmAccountHeadInfo" property="authaccheadlist">
 <logic:iterate id="authaccheadlist" name="frmAccountHeadInfo" property="authaccheadlist" indexId="i">
 <html:hidden name="authaccheadlist" property="wardTypeID" />
 
 
 	<tr class="<%=(i.intValue()%2==0)? "gridOddRow":"gridEvenRow"%>">
  	<td width="25%" class="formLabel">
  		<bean:write name="authaccheadlist" property="wardDesc"/>
		<html:hidden name="authaccheadlist" property="wardDesc"/>
	</td>
	
	<td width="15%" align="center">
		<input type="checkbox" name="incAccountHeadYN" onclick="javascript:onCheck()"/>
		<html:hidden name="authaccheadlist" property="incAcctheadYN"/>
	</td>
    
    <td width="35%" align="center">
	   	<html:select name="authaccheadlist" property="authGrpSeqID"  styleClass="selectBox selectBoxMedium" style="width:250px;">
	   		<html:option value="">Select from list</html:option>
	   		<html:options collection="alGroupList"  property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
	</td>
	<td width="20%" align="center">
    	<input type="checkbox" name="showAccountHeadYN" />
    	<html:hidden name="authaccheadlist" property="showAcctheadYN" />
    </td>
	
  </tr>
  </logic:iterate>
  </table>
 </logic:notEmpty>
	</fieldset>
	
	<!-- E N D : Form Fields -->
	<!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <%
        if(TTKCommon.isAuthorized(request,"Edit"))
        {
    %>
    <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
	<%
		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	%>
	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	</td>
  </tr>
</table>

<logic:notEmpty name="frmAccountHeadInfo" property="frmChanged">
		<script> TC_PageDataChanged=true;</script>
</logic:notEmpty>

<logic:notEmpty name="frmAccountHeadInfo" property="authaccheadlist">
<script language="javascript">
			onDocumentLoad();
</script>
</logic:notEmpty>

<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="">
	<!-- E N D : Buttons -->
</div>
</html:form>
