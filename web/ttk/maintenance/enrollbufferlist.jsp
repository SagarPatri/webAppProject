<%
/**1216B CR
 * @ (#) bufferlist.jsp 19th Jun 2006
 * Project      : TTK HealthCare Services
 * File         : bufferlist.jsp
 * Author       : 
	 * Date Created : 19th Jun 2006
 *
 * @author       :Pradeep R
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon" %>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/maintenance/enrollbufferlist.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/EnrollBufferAction" >
<!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td>List of Buffer Amount </td>
     
    </tr>
  </table>
<!-- E N D : Page Title -->

<div class="contentArea" id="contentArea"><br>
<html:errors/>
<!-- S T A R T : Grid -->
  <ttk:HtmlGrid name="tableData"/>
<!-- E N D : Grid -->

 <!-- S T A R T : Buttons -->
<table  align="right" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  <tr>
 
    
    <td> </td>
    <td nowrap align="right">
      <span class="fieldGroupHeader">Total Buffer Amt. (Rs): <bean:write name="frmBufferAmount" property="totAmount" />
      </span>&nbsp;
      </tr>
      <tr>
       <td> </td>
       <td nowrap align="right">
      <%
      if(TTKCommon.isAuthorized(request,"Add"))
      {
      %>
        <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd();"><u>A</u>dd</button>&nbsp;
      <%
      }
       if(TTKCommon.isAuthorized(request,"Deduct"))
      {
      %>
      <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDeduct();"><u>D</u>educt</button>&nbsp;
      <%
       }
      %>
      
       	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose1();"><u>C</u>lose</button>
   </td>
   
   
  </tr>
    <tr>
    <td>  </td>
 	<td nowrap align="right">
 	</td>
  
     </tr>
 
   <ttk:PageLinks name="tableData"/> 
   </table>
</div>
  <input type="hidden" name="child" value="">
  <INPUT TYPE="hidden" NAME="rownum" VALUE="">
  <INPUT TYPE="hidden" NAME="mode" VALUE="">
   <INPUT TYPE="hidden" NAME="claimType" VALUE="">
   <INPUT TYPE="hidden" NAME="bufferType" VALUE="">
    <INPUT TYPE="hidden" NAME="modeTypeId" VALUE="">
    <html:hidden property="memberSeqId" name="frmBufferAmount"/>
    <html:hidden property="policySeqId" name="frmBufferAmount"/>
    <html:hidden property="policyGroupSeqId" name="frmBufferAmount"/>
     <INPUT TYPE="hidden" NAME="sortId" VALUE="">
  <INPUT TYPE="hidden" NAME="pageId" VALUE="">
</html:form>
<!-- E N D : Buttons -->



