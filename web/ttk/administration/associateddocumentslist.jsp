<%
/** @ (#) associateddocumentslist.jsp
 * Project     : Vidal Health TPA  Services
 * File        : associateddocumentslist.jsp
 * Author      : Balaji C R B
 * Company     : Span Systems Corporation
 * Date Created: August 25, 2008
 *
 * @author 		 : Balaji C R B
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
<%@ page import=" com.ttk.common.TTKCommon" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/associateddocumentslist.js"></script>
<%
    boolean viewmode=true;
    if(TTKCommon.isAuthorized(request,"Edit"))
    {
        viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
    pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>
<html:form action="/AssociateDocumentsListAction.do" >
	<!-- S T A R T : Page Title -->
	
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>Document Association <bean:write name="frmAssociateDocuments" property="caption"/></td>    
    		</tr>
	</table>
	<html:errors/>
	<!-- E N D : Page Title --> 
	<div style="width: 99%; float: right;">
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
	<ttk:HtmlGrid name="tableDataAssociatedDocuments" className="gridWithCheckBox zeroMargin"/>
	<!-- E N D : Grid -->
	</div>
	<!-- S T A R T : Buttons -->
	<br>
	<table class="buttonsSavetolistGrid" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="right" nowrap class="formLabel">
			<%
	    		if(TTKCommon.isDataFound(request,"tableDataAssociatedDocuments") && TTKCommon.isAuthorized(request,"Delete"))
				{
		    %>
					<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>
			<%
        		}// end of if(TTKCommon.isDataFound(request,"tableDataLinkDetails") && TTKCommon.isAuthorized(request,"Delete"))
        	%>		
			</td>
		</tr>
	</table>
	<fieldset>
		<legend>Document Details</legend>
		<table class="formContainer" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px;">
			
			<tr>
				
          		<td width="19%" align="left" nowrap>&nbsp;&nbsp;Document Name<span class="mandatorySymbol">*</span>:</td>
          		<td width="26%" align="left" nowrap>
          			<html:text property="docName"  styleClass="textBox textBoxMedium" maxlength="60" readonly="<%=viewmode%>"/>
          		</td>
          		<td width="40%" align="left">
          		  <table border="0" cellpadding="0" cellspacing="0" width="100%">
          		   <tr>
          		   <td nowrap="nowrap">
			    <span id="docPath">Document Path<span class="mandatorySymbol">*</span>: </span>&nbsp;&nbsp;&nbsp;</td>
			     <td><html:text property="docPath"  styleClass="textBox textBoxMedium" style="width:100px;" maxlength="250" readonly="<%=viewmode%>" disabled="true"/>
			      <a name="Browseicon" id="BrowseIconID" href="#" onClick="javascript:onBrowse();" onMouseOver="window.status='Browse';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/filebrowse.gif" alt="Browse" name="browseImg" align="absmiddle" width="20" height="15" border = "0"></a>
			  
          		   </td>
          		   </tr>
          		  </table>
          		  </td>
          	       </tr>
          	       <tr>
          	       	<td colspan="4" align="center">
          	       		<logic:match name="viewmode" value="false">
									<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave to List</button>&nbsp;
									<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
								</logic:match>
					<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>		
          	       	</td>
          	       </tr>
			</table>
		</fieldset>	
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
		    <tr>
      			<td width="100%" align="center">
			
				
				</td>
				
		</table>
		</div>
	<html:hidden property="clauseDocSeqID"/>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">	
</html:form>