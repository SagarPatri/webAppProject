<%
/** @ (#) linkdetails.jsp 21st Dec 2007
 * Project     : TTK Healthcare Services
 * File        : hospuploads.jsp
 * Author      : Kishor kumar
 * Company     : RCS Technologies
 * Date Created: 14th May 2014
 *
 * @author 		 : Kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache,org.apache.struts.action.DynaActionForm" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/hospuploads.js"></script>
<%
DynaActionForm frmCompanyDetails=(DynaActionForm)request.getSession().getAttribute("frmhospConfiguration");
    boolean viewmode=true;
    if(TTKCommon.isAuthorized(request,"Edit"))
    {
        viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
    pageContext.setAttribute("viewmode",new Boolean(viewmode));
    pageContext.setAttribute("webloginLink",Cache.getCacheObject("webloginLink"));
    pageContext.setAttribute("webloginLinkReport",Cache.getCacheObject("webloginLinkReport"));
%>
<html:form action="/HospitalConfigurationActionUploads.do"  method="post" enctype="multipart/form-data">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>Web Configuration Information - Hospital Information Page </td>     
    		<td width="43%" align="right" class="webBoard">&nbsp;</td>
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- E N D : Page Title --> 
	
	
	
	<div class="scrollableGrid" style="height:290px;">
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
	<br>
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableDataLinkDetails" className="gridWithCheckBox zeroMargin"/>
	<!-- E N D : Grid -->
	</div>
	<!-- S T A R T : Buttons -->
	<br>
	
	<logic:notEmpty name="notify" scope="request">
		<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <bean:write name="notify" scope="request"/>
	        </td>
	      </tr>
   	 </table>
	   </logic:notEmpty>
    	<logic:notEmpty name="updated" scope="request">
	   	<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
			 	<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
		    		<bean:write name="updated" scope="request"/>
		    	</td>
		 	</tr>
		</table>
	</logic:notEmpty>
	
	
	<table class="buttonsSavetolistGrid" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="right" nowrap class="formLabel">
			<%
	    		if(TTKCommon.isDataFound(request,"tableDataLinkDetails") && TTKCommon.isAuthorized(request,"Delete"))
				{
		    %>
					<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>
			<%
        		}// end of if(TTKCommon.isDataFound(request,"tableDataLinkDetails") && TTKCommon.isAuthorized(request,"Delete"))
        	%>		
			</td>
		</tr>
	</table>
	
	
	<br>
	<fieldset>
		<legend>Add Information</legend>
		<table class="formContainer" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px;">
			
			<tr>
          		<td align="left" nowrap>&nbsp;&nbsp;Information Description<span class="mandatorySymbol">*</span>:</td>
          		<td align="left" nowrap>
          			<html:text property="configLinkDesc" styleClass="textBox textBoxMedium" maxlength="60" />
          		</td>
          		<td align="left" nowrap="nowrap">&nbsp;&nbsp;
					Priority<span class="mandatorySymbol">*</span>:&nbsp;&nbsp;&nbsp;
						<html:text property="orderNumber"  styleClass="textBox textBoxSmall" maxlength="3" onkeyup="isPositiveInteger(this,'Priority')"/>
					
					
				</td>
          		<!-- td align="left" id="path">
					File Name<span class="mandatorySymbol">*</span>:&nbsp;&nbsp;&nbsp;
						<html:text property="path"  styleClass="textBox textBoxMedium" style="width:100px;" maxlength="250" />
						<a name="Browseicon" id="BrowseIconID" href="#" onClick="javascript:onBrowse();" onMouseOver="window.status='Browse';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/filebrowse.gif" alt="Browse" name="browseImg" width="20" height="15" border = "0"></a>
          		</td-->	
          		
          		<td align="left">Browse File : </td>
			<td>
				<html:file property="file" styleId="file" />
			</td>
			
			</tr>
			<tr>
				<td align="center" nowrap>&nbsp;
				<logic:match name="viewmode" value="false">
					<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>U</u>pload File</button>
				<!--&nbsp;
					<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
				-->
				</logic:match>
					<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>		
				</td>
			</tr>	
		</table>
		
<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
			<font color=blue><b>Note :</b>  Please Upload DOC,XLS OR PDF files.</font> 
			</td>
     	</tr>
</table>

	</fieldset>
	<html:hidden property="showLinkYN" value="Y" />
	<html:hidden property="caption"/>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<script language="javascript">
	//onLoadLinks();
	</script>
</html:form>