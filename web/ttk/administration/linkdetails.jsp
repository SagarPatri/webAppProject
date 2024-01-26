<%
/** @ (#) linkdetails.jsp 21st Dec 2007
 * Project     : TTK Healthcare Services
 * File        : linkdetails.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 21st Dec 2007
 *
 * @author 		 : Chandrasekaran J
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
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/linkdetails.js"></script>
<%
    boolean viewmode=true;
    if(TTKCommon.isAuthorized(request,"Edit"))
    {
        viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
    pageContext.setAttribute("viewmode",new Boolean(viewmode));
    pageContext.setAttribute("webloginLink",Cache.getCacheObject("webloginLink"));
    pageContext.setAttribute("webloginLinkReport",Cache.getCacheObject("webloginLinkReport"));
%>
<html:form action="/LinkDetailsAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>Web Configuration Information - Home Page<bean:write name="frmLinkDetails" property="caption"/></td>     
    		<td width="43%" align="right" class="webBoard">&nbsp;</td>
  		</tr>
	</table>
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
	<fieldset>
		<legend>Link Details</legend>
		<table class="formContainer" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px;">
			<tr>
        		<td colspan="7" align="left" nowrap></td>
        	</tr>
			<tr>
				<td width="12%" align="left" nowrap>Show:<br></td>
          		<td width="3%" align="left" nowrap>
          			<html:checkbox property="showLinkYN" value="Y" disabled="<%=viewmode%>"/>
          		</td>
          		<td width="8%" align="left" nowrap>Type<span class="mandatorySymbol">*</span>:&nbsp;&nbsp;</td>
          		<td width="19%" align="left" nowrap>
          			<html:select property="linkTypeID"  styleClass="selectBox selectBoxMedium" style="width:140px;" disabled="<%=viewmode%>" onchange="onchangeLink();">
	            		<html:options collection="webloginLink"  property="cacheId" labelProperty="cacheDesc"/>
            		</html:select>
          		</td>
          		<td width="19%" align="left" nowrap>&nbsp;&nbsp;Link Description<span class="mandatorySymbol">*</span>:</td>
          		<td width="26%" align="left" nowrap>
          			<html:text property="configLinkDesc"  styleClass="textBox textBoxMedium" maxlength="60" readonly="<%=viewmode%>"/>
          		</td>
          		<td width="13%" align="left">
          			<table border="0" cellpadding="0" cellspacing="0" >
          				<tr>
          					<td nowrap="nowrap">
          						<span  style="display:none;" id="path" >File Name<span class="mandatorySymbol">*</span>:&nbsp;&nbsp;&nbsp;
          							<html:text property="path"  styleClass="textBox textBoxMedium" style="width:100px;" maxlength="250" readonly="<%=viewmode%>"/>
          							<a name="Browseicon" id="BrowseIconID" href="#" onClick="javascript:onBrowse();" onMouseOver="window.status='Browse';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/filebrowse.gif" alt="Browse" name="browseImg" align="absmiddle" width="20" height="15" border = "0"></a>
          						</span>
          					</td>
          					<td nowrap="nowrap"></td>
          					<td>
          						<span style="display:none;" id="report">Report:&nbsp;&nbsp;&nbsp;
          							<html:select property="reportID"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
	            						<html:options collection="webloginLinkReport"  property="cacheId" labelProperty="cacheDesc"/>
            						</html:select>
          						</span>
          					</td>
          				</tr>
          			</table>
          		</td>	
			</tr>
			<tr>
				<td colspan="7" align="left" nowrap="nowrap">
					<span id="ordNo" style="display:none;">Sort Order<span class="mandatorySymbol">*</span>:&nbsp;&nbsp;&nbsp;
						<html:text property="orderNumber"  styleClass="textBox textBoxSmall" maxlength="3" readonly="<%=viewmode%>"/>	
					</span>
				</td>	
			</tr>
			<tr>
				<td colspan="7" align="center" nowrap>&nbsp;
				<logic:match name="viewmode" value="false">
					<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave to List</button>&nbsp;
					<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
				</logic:match>
					<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>		
				</td>
			</tr>	
		</table>
	</fieldset>
	<html:hidden property="showLinkYN" value="N" />
	<html:hidden property="configLinkSeqID"/>
	<html:hidden property="caption"/>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<script language="javascript">
	onLoadLinks();
	</script>
</html:form>