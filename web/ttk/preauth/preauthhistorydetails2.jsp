
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,org.dom4j.Document"%>
<%@page import="org.dom4j.Node,java.util.List,java.util.Iterator" %>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/preauth/preauthhistorydetails.js"></SCRIPT>
<script type="text/javascript">
// var JS_Focus_Disabled =true;
function onClose()
{
	document.forms[1].tab.value ="General";
    document.forms[1].mode.value = "doCloseHistory";
    document.forms[1].action = "/PreAuthHistoryAction.do";
    document.forms[1].submit();
}//end of function addPreAuth()


</script>

<div id="contentArea" class="contentArea">
<form action="/PreAuthHistoryAction.do">
<ttk:PreAuthHistoryDetails2/>

<fieldset>
		<legend>View Documents</legend>
	<div id="scroll"   style="height:290px; width:1100px; overflow-x:scroll ; overflow-y:scroll; padding-bottom:10px;">
	<!-- E N D : Success Box -->
	<!-- S T A R T : Form Fields -->
	<br>
	
	<!-- S T A R T : Grid -->
		<ttk:PreauthDocsUpload/>
	<!-- E N D : Grid -->
	</div>
</fieldset>
 
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="leftlink" VALUE="">
<INPUT TYPE="hidden" NAME="sublink" VALUE="">
<INPUT TYPE="hidden" NAME="tab" VALUE="">	
 
 <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
 	 <tr>
	    <td width="73%" nowrap align="center">
	    <!-- <button type="submit"  name="Button1" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"><u>C</u>lose</button> -->
	         <button type="button"  name="Button" accesskey="c"  onClick="onClose()"><u>C</u>lose</button>
	    
	    </td>
	 </tr>
</table>

	<INPUT TYPE="hidden" NAME="file" VALUE="">
	<INPUT TYPE="hidden" NAME="authSeqID" VALUE="<%=(String)request.getAttribute("authSeqID") %>">
 </form>

</div>

