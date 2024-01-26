<%
/**
 * @ (#) shortfalldetails.jsp 09th May 2006
 * Project      : TTK HealthCare Services
 * File         : shortfalldetails.jsp
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : 09th May 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/preauth/shortfalldetails.js"></script>
<script language="javascript">
	var JS_Focus_Disabled =true;
</script>
<%
	boolean viewmode=true;
	boolean viewmodestatus=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}
	String ampm[] = {"AM","PM"};
	pageContext.setAttribute("ampm",ampm);
	if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
	{
		pageContext.setAttribute("ShortfallTypeID",Cache.getCacheObject("shortfallType"));
	}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
	else if(TTKCommon.getActiveLink(request).equals("Claims"))
	{
		pageContext.setAttribute("ShortfallTypeID",Cache.getCacheObject("claimShortfallType"));
	}//end of else if(TTKCommon.getActiveLink(request).equals("Claims"))

	pageContext.setAttribute("VoucherStatusID",Cache.getCacheObject("voucherStatus"));
	pageContext.setAttribute("ShortFallStatusID",Cache.getCacheObject("shortfallStatus"));
	pageContext.setAttribute("Reason",Cache.getCacheObject("shortfallReason"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ShortFallDetailsAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
   		<td width="57%"><bean:write name="frmShortFall" property="caption"/></td>
		<td width="43%" align="right" class="webBoard">&nbsp;&nbsp;</td>
   	</tr>
</table>
<!-- E N D : Page Title -->

<!-- S T A R T : Form Fields -->
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

	<logic:match name="frmSuppDoc" property="documentType" value="DCV">
		<fieldset><legend>General</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	    	<td width="20%" height="20" nowrap class="formLabel">Ref. No.:</td>
	    	<td width="30%" nowrap class="textLabelBold"><bean:write name="frmShortFall" property="refNbr"/></td>

	    	<td width="20%" nowrap class="formLabel">
				
				<div id="sentdatelabel" style="display:">
				Sent Date / Time: <span class="mandatorySymbol">*</span>
		    	</div>
		    </td>
		    <td class="textLabelBold" >
				<div id="sentdatetext" style="display:">
					<html:text property="sentDate"  styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
					<A NAME="calSentDate" ID="calSentDate" HREF="#" onClick="javascript:show_calendar('calSentDate','frmShortFall.sentDate',document.frmShortFall.sentDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
					<html:text property="sentTime"  styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;<html:select property="sentDay" name="frmShortFall" styleClass="selectBox" disabled="<%= viewmode %>"><html:options name="ampm" labelName="ampm"/></html:select>
				</div>
			</td>
	  	</tr>
	  	<tr>
	    	<td class="formLabel">Status: <span class="mandatorySymbol">*</span></td>
	    	<td class="textLabelBold">
	    		<html:select property="statusTypeID"  styleClass="selectBox selectBoxMedium" onchange="showhideReasonAuth(this);" disabled="<%= viewmode %>">
					<html:option value="">Select from list</html:option>
	  				<html:options collection="VoucherStatusID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
	      	</td>

	      	<td nowrap class="formLabel">
				<logic:match name="frmShortFall" property="statusTypeID" value="DVR">
					<div id="receiveddatelabel" style="display:">
				</logic:match>
				<logic:notMatch name="frmShortFall" property="statusTypeID" value="DVR">
					<div id="receiveddatelabel" style="display:none;">
				</logic:notMatch>
			    	Received Date / Time: <span class="mandatorySymbol">*</span>
		    	</div>
		    </td>
		    
		    <td class="textLabelBold" >
		   
		      <logic:match name="frmShortFall" property="statusTypeID" value="DVR">
					<div id="receiveddatetext" style="display:">
				</logic:match>
				<logic:notMatch name="frmShortFall" property="statusTypeID" value="DVR">
					<div id="receiveddatetext" style="display:none;">
				</logic:notMatch>
				 <table cellpadding="1" cellspacing="0">
		     	  <tr>
					<td><html:text property="receivedDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
					<A NAME="calRecvdDate" ID="calRecvdDate" HREF="#" onClick="javascript:show_calendar('calRecvdDate','forms[1].receivedDate',document.forms[1].receivedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>&nbsp;</td>
		      <td><html:text property="receivedTime"  styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
		      <td><html:select property="receivedDay" name="frmShortFall" styleClass="selectBox" disabled="<%= viewmode %>"><html:options name="ampm" labelName="ampm"/></html:select></td>
		     </tr>
		    </table>
			</div>
			</td>
	  	</tr>
		<tr>
	    	<td nowrap class="formLabel">Remarks:</td>
	    	<td colspan="3" valign="bottom" nowrap class="formLabel">
	      		<html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	      	</td>
		</tr>
		</table></fieldset>
	</logic:match>

	<logic:match name="frmSuppDoc" property="documentType" value="SRT">
		<fieldset><legend>General</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
	    	<td height="20" class="formLabel">Shortfall No.:</td>
	    	<td class="textLabelBold"><bean:write name="frmShortFall" property="shortfallNo"/></td>
	    	<td nowrap class="formLabel">&nbsp;</td>
	    	<td nowrap class="textLabelBold">&nbsp;</td>
	  	</tr>
	  	<tr>
	    	<td width="18%" class="formLabel">Shortfall Type: <span class="mandatorySymbol">*</span></td>
	    	<td class="formLabel" width="30%">
			<logic:empty name="frmShortFall" property="shortfallSeqID">
				<html:select property="shortfallTypeID"  styleClass="selectBox selectBoxMedium" onchange="onShortfallchange(this);" disabled="<%= viewmode %>">
					<!--<html:option value="">Select from list</html:option>-->
	  				<html:options collection="ShortfallTypeID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
	    	</logic:empty>
	    	<logic:notEmpty name="frmShortFall" property="shortfallSeqID">
		    	<html:select property="shortfallTypeID"  styleClass="selectBox selectBoxMedium" onchange="onShortfallchange(this);" disabled="true">
					<!--<html:option value="">Select from list</html:option>-->
	  				<html:options collection="ShortfallTypeID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
		    	<html:hidden property="shortfallTypeID"/>
	    	</logic:notEmpty>
			</td>
	    	<%--<td nowrap class="formLabel" width="22%">Sent Date / Time:</td>
	    	<td nowrap class="textLabelBold" width="30%">
	    	<bean:write name="frmShortFall" property="sentDate"/>&nbsp;
	        <bean:write name="frmShortFall" property="sentTime"/>&nbsp;
	        <bean:write name="frmShortFall" property="sentDay"/></td>
	    	<html:hidden property="sentDate"/>
	    	<html:hidden property="sentTime"/>
	    	<html:hidden property="sentDay"/>--%>
	  	</tr>
	  	<tr>
	  		<td class="formLabel">Status: <span class="mandatorySymbol">*</span></td>
	    	<td class="textLabelBold">
				<html:select property="statusTypeID"  styleClass="selectBox selectBoxMedium" onchange="showhideReasonAuth(this);" disabled="<%= viewmode %>">
	  				<html:options collection="ShortFallStatusID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
	      	</td>

	      	<td nowrap class="formLabel">
				<logic:match name="frmShortFall" property="statusTypeID" value="RES">
					<div id="receiveddatelabel" style="display:">
				</logic:match>
				<logic:notMatch name="frmShortFall" property="statusTypeID" value="RES">
					<div id="receiveddatelabel" style="display:none;">
				</logic:notMatch>
			    	Received Date / Time: <span class="mandatorySymbol">*</span>
		    	</div>
		    </td>
		    <td class="textLabelBold" >
				<logic:match name="frmShortFall" property="statusTypeID" value="RES">
					<div id="receiveddatetext" style="display:">
				</logic:match>
				<logic:notMatch name="frmShortFall" property="statusTypeID" value="RES">
					<div id="receiveddatetext" style="display:none;">
				</logic:notMatch>
		    	    <html:text property="receivedDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
		    		<A NAME="calRecvdDate" ID="calRecvdDate" HREF="#" onClick="javascript:show_calendar('calRecvdDate','frmShortFall.receivedDate',document.frmShortFall.receivedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
					<html:text property="receivedTime"  styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;<html:select property="receivedDay" name="frmShortFall" styleClass="selectBox" disabled="<%= viewmode %>"><html:options name="ampm" labelName="ampm"/></html:select>
				</div>
			</td>
		</tr>
	  	<logic:match name="frmShortFall" property="reasonYN" value="N">
	 		<tr style="display:none;" id="Reason">
	  	</logic:match>
	  	<logic:match name="frmShortFall" property="reasonYN" value="Y">
			<tr style="display:" id="Reason">
	  	</logic:match>
		  		<td class="formLabel">Reason: <span class="mandatorySymbol">*</span></td>
			  	<td colspan="3">
					<html:select property="reasonTypeID"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
						<html:option value="">Select from list</html:option>
		  				<html:options collection="Reason"  property="cacheId" labelProperty="cacheDesc"/>
			    	</html:select>
				</td>
		  	</tr>
		<tr>
	      	<td nowrap class="formLabel">Remarks:</td>
	      	<td colspan="3" valign="bottom" nowrap class="formLabel">
	      		<html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	      	</td>
		</tr>
		<tr>
			<td class="formLabel">New Correspondence:</td>
    	    <td class="textLabelBold"><span class="formLabel">
    	    	<html:checkbox styleClass="margin-left:-4px;" property="correspondenceYN" value="Y" disabled="<%=viewmode%>" onclick="javascript:setCorrespondenceDate()"/></span>
    	    </td>
    	     <td class="formLabel">Correspond Date / Time:</td>
			    <td class="textLabel">
			    <table cellpadding="1" cellspacing="0">
			     <tr>
			      <td><html:text property="correspondenceDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/><A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmShortFall.correspondenceDate',document.frmShortFall.correspondenceDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;</td>
			      <td><html:text property="correspondenceTime"  styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
			      <td><html:select property="correspondenceDay" name="frmShortFall" styleClass="selectBox" disabled="<%=viewmode%>"><html:options name="ampm" labelName="ampm"/></html:select></td>
			     </tr>
			    </table>
			    	
			 </td>
		</tr>
		<tr>
			<td class="formLabel">Correspondence Count: </td>
				  <td class="textLabel">
				  	<html:text property="correspondenceCount" styleClass="textBox textBoxSmall textBoxDisabled" maxlength="13" disabled="<%= viewmode %>" readonly="true"/>
			</td>
		</tr>
		</table></fieldset>
		<ttk:ShortFallQueries/>
	</logic:match>

<!-- E N D : Form Fields -->

<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td width="100%" align="center">
	       	    <%
    				if(TTKCommon.isAuthorized(request,"Edit"))
					{
			    %>
			    		<logic:match name="frmSuppDoc" property="documentType" value="SRT">

							    <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateShortFall();"><u>G</u>enerate</button>&nbsp;

								<logic:notEmpty name="frmShortFall" property="shortfallSeqID">
									<logic:match name="frmShortFall" property="statusTypeID" value="OPN">
										<button type="button" name="Button" id="send" accesskey="e" style="display:" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSend();">S<u>e</u>nd</button>&nbsp;
									</logic:match>
									<logic:notMatch name="frmShortFall" property="statusTypeID" value="OPN">
										<button type="button" name="Button" id="send" accesskey="e" style="display:none" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSend();">S<u>e</u>nd</button>&nbsp;
									</logic:notMatch>
							    </logic:notEmpty>
		        				<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
		        				<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Reset();"><u>R</u>eset</button>&nbsp;
			    		</logic:match>

			    		<logic:match name="frmSuppDoc" property="documentType" value="DCV">
	    	    			<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateShortFall();"><u>G</u>enerate</button>&nbsp;
	    	    			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
		        			<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Reset();"><u>R</u>eset</button>&nbsp;
	    	    		</logic:match>
		    			<logic:notMatch name="frmShortFall" property="editYN" value="Y">
		    				<SCRIPT LANGUAGE="JavaScript">
								var TC_Disabled = true; //to avoid the alert message on change of form elements
							</SCRIPT>
		   	 			</logic:notMatch>
	    		<%
	    			}
	    		%>
		    		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Close();"><u>C</u>lose</button>&nbsp;
		   </td>
		</tr>
	</table>
<!-- E N D : Buttons -->
</div>
<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
<html:hidden property="reasonYN"/>
<html:hidden property="editYN"/>
<html:hidden property="caption"/>
<html:hidden property="shortfallSeqID"/>
<html:hidden property="shortfallNo"/>
<html:hidden property="shortfalltype"/>
<html:hidden property="refNbr"/>
<html:hidden property="DMSRefID"/>
<html:hidden property="hiddenDate"/>
<html:hidden property="hiddenTime"/>
<html:hidden property="hiddenTimeStamp"/>
<input type="hidden" id="preAuthNo" name="preAuthNo" value="<%=TTKCommon.checkNull(PreAuthWebBoardHelper.getWebBoardDesc(request))%>">
<input type="hidden" id="type" name="shortfallTypeID" value="<bean:write name="frmShortFall" property="shortfallTypeID"/>"/>
<INPUT TYPE="hidden" NAME="shortFall" VALUE="<bean:write name="frmSuppDoc" property="documentType"/>"/>
<logic:match name="frmSuppDoc" property="documentType" value="SRT">
<input type="hidden" name="child" value="ShortFall Details">
</logic:match>
<logic:match name="frmSuppDoc" property="documentType" value="DCV">
<input type="hidden" name="child" value="Discharge Voucher">
</logic:match>
<logic:notEmpty name="frmShortFall" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
<logic:match name="frmSuppDoc" property="documentType" value="DCV">
<script language="javascript">
			onDocumentLoad();
</script>
</logic:match>

</html:form>
<!-- E N D : Content/Form Area -->