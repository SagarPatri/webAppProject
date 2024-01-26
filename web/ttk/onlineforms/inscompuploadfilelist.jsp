<%
/** @ (#) inscompuploadfilelist.jsp
 * Project     : Vidal Health TPA
 * File        : inscompuploadfilelist.jsp
 * Author      : Thirumalai K P
 * Company     : RCS Technologies
 * Date Created: Jan 28, 2014
 *
 * @author 		 : Thirumalai K P
 * Modified by   :
 * Modified date :
 * Reason        : ins file upload
 */
%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/onlineinsloginfileupload.js"></SCRIPT>
<script>
var TC_Disabled = true;
var filedownloadpath = new Array(<bean:write name="frmInsUploadedFiles" property="InsFileUploadPath" filter="false"/>);
</script>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
		{
			viewmode=false;
		}
	pageContext.setAttribute("viewmode",new Boolean(viewmode));	
	pageContext.setAttribute("strSubLink",TTKCommon.getActiveSubLink(request));

%>
<html:form action="/OnlineInsCompUploadFileList.do">

 <logic:notEmpty name="fileName" scope="request">
		<script language="JavaScript">
		    var w = screen.availWidth - 10;
			var h = screen.availHeight - 82;
			var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			window.open("/OnlineInsCompUploadFileList.do?mode=doFileDownload&displayFile=<bean:write name="fileName"/>",'ShowFile',features);
		</script>
	</logic:notEmpty>
	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>List of Files</td>
		<td width="43%" align="right" class="webBoard">&nbsp;</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	
	<!-- S T A R T : Success Box -->
	<div class="contentArea" id="contentArea">
	 <logic:notEmpty name="updated" scope="request">
	  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="updated" scope="request"/>
	     </td>
	  </tr>
	 </table>
    </logic:notEmpty>
    
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableInsCompFileUpload"/>
	<!-- E N D : Grid -->
	
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">  
	    <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	</td>
	  </tr>
	  <tr>
	  <ttk:PageLinks name="tableInsCompFileUpload"/>
	  </tr>
	</table>
 		</div>
	<!-- E N D : Buttons and Page Counter -->
	<input type="hidden" name="mode" value=""/>
	<html:hidden property="flag" />
	<input type="hidden" name="rownum" value=""/>
	<input type="hidden" name="pageId" value=""/>
	<input type="hidden" name="sortId" value=""/>
	<input type="hidden" name="child" value=""/>
	<html:hidden property="loginUserId" name="frmInsUploadedFiles"/>
</html:form>