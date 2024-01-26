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
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper" %>


<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/preauth/shortfalldetails.js"></script>
<script type="text/javascript">
	var JS_Focus_Disabled =true;
	
</script>
<%
	String claimTypeDesc = (String)request.getSession().getAttribute("claimTypeDesc");//shortfall phase1
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
		//KOC1179
		pageContext.setAttribute("ShortfallTypeIDNew",Cache.getCacheObject("claimShortfallTypeNew"));		
		pageContext.setAttribute("ShortfallTemplateType",Cache.getCacheObject("claimShortfallTemplateType"));
		pageContext.setAttribute("ShortfallUnderClause",Cache.getCacheObject1("claimShortfallUnderClause",ClaimsWebBoardHelper.getClaimsSeqId(request)));
		pageContext.setAttribute("ShortfallTemplateNetworkType",Cache.getCacheObject("claimShortfallTemplateNetworkType"));//shortfall phase1
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
		<td width="43%" align="right" class="webBoard">123&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
   	</tr>
</table>
<!-- E N D : Page Title -->

<!-- S T A R T : Form Fields -->
<div class="contentArea" id="contentArea">
<html:errors/>

<!-- S T A R T : Success Box -->
<bean:write name="frmShortFall" property="claimTypeDesc" />
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
			<logic:notEqual name="frmShortFall" property="displayclaims"  value="show">
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
			</logic:notEqual>
	    	
	    	<logic:equal name="frmShortFall" property="displayclaims"  value="show">
	    	<logic:empty name="frmShortFall" property="shortfallSeqID">
				<html:select property="shortfallTypeID"  styleClass="selectBox selectBoxMedium" onchange="onShortfallchange(this);" disabled="<%= viewmode %>">
					<!--<html:option value="">Select from list</html:option>-->
	  				<html:options collection="ShortfallTypeIDNew"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
	    	</logic:empty>
	    	<logic:notEmpty name="frmShortFall" property="shortfallSeqID">
		    	<html:select property="shortfallTypeID"  styleClass="selectBox selectBoxMedium" onchange="onShortfallchange(this);" disabled="true">
					<!--<html:option value="">Select from list</html:option>-->
	  				<html:options collection="ShortfallTypeIDNew"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
		    	<html:hidden property="shortfallTypeID"/>
	    	</logic:notEmpty>
	    	</logic:equal>
			</td>	    	
	  	</tr>
		<logic:equal name="frmShortFall" property="displayclaims"  value="show">
	  	<tr>
	    	<td width="18%" class="formLabel">Rise Shortfall: <span class="mandatorySymbol">*</span></td>
	    	<td class="formLabel" width="30%">
	    	<!-- shortfall phase1 -->
	    	
	    <logic:equal name="claimTypeDesc" value="CTM"> 
	    	<logic:empty name="frmShortFall" property="shortfallSeqID">
				<html:select property="shortfallTemplateType" styleId="shortfallTemplateType"  styleClass="selectBox selectBoxLargestWeblogin" onchange="onSetUnderClause(this);"  disabled="<%= viewmode %>">
	  				  <html:option value="">Select from list</html:option> 
	  				     <html:options collection="ShortfallTemplateType"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
		    </logic:empty>
		    <logic:notEmpty name="frmShortFall" property="shortfallSeqID">
		    <html:select property="shortfallTemplateType"  styleId="shortfallTemplateType" styleClass="selectBox selectBoxLargestWeblogin" onchange="onSetUnderClause(this);" disabled="true">
	  		 <html:option value="">Select from list</html:option> 
	  			<html:options collection="ShortfallTemplateType"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
		    	<html:hidden property="shortfallTemplateType"/>
		    </logic:notEmpty>
		     </logic:equal>
		    <logic:equal name="claimTypeDesc" value="CNH">
	    	<logic:empty name="frmShortFall" property="shortfallSeqID">
				<html:select property="shortfallTemplateType" styleId="shortfallTemplateType"  styleClass="selectBox selectBoxLargestWeblogin" onchange="onSetUnderClause(this);"  disabled="<%= viewmode %>">
	  				  <html:option value="">Select from list</html:option> 
	  				<html:options collection="ShortfallTemplateNetworkType"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
		    </logic:empty>
		    
		    <logic:notEmpty name="frmShortFall" property="shortfallSeqID">
		    <html:select property="shortfallTemplateType"  styleId="shortfallTemplateType" styleClass="selectBox selectBoxLargestWeblogin" onchange="onSetUnderClause(this);" disabled="true">
	  		 <html:option value="">Select from list</html:option> 
	  			<html:options collection="ShortfallTemplateNetworkType"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
		    	<html:hidden property="shortfallTemplateType"/>
		    </logic:notEmpty>
		    </logic:equal> 
		    <!-- shortfall phase1 -->
	    	</td>
	    </tr>
	    </logic:equal>
	    
	    <logic:equal name="frmShortFall" property="displayclaims"  value="show">
	      <logic:notMatch name="frmShortFall"  property="shortfallTemplateType" value="DSFO">
	        <tr id="UnderClause" style="display:">
	    	<td width="18%" class="formLabel">Under Clause : <span class="mandatorySymbol">*</span></td>
	    	<td class="formLabel" width="30%">
	    	<logic:empty name="frmShortFall" property="shortfallSeqID">
				<html:select property="shortfallUnderClause" styleId="shortfallUnderClause"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
	  				 <html:option value="">Select from list</html:option>
	  				<html:options collection="ShortfallUnderClause"  property="cacheId" labelProperty="cacheDesc"/>	  				
		    	</html:select>
		    	 </logic:empty>
		    	 <logic:notEmpty name="frmShortFall" property="shortfallSeqID">
				<html:select property="shortfallUnderClause" styleId="shortfallUnderClause"  styleClass="selectBox selectBoxMedium" disabled="true">
				    <html:option value="">Select from list</html:option>
	  				<html:options collection="ShortfallUnderClause"  property="cacheId" labelProperty="cacheDesc"/>
	  				
		    	</html:select>
		    	 </logic:notEmpty>
	    	</td>	    	
	    </tr>
	        </logic:notMatch>
	  <logic:match name="frmShortFall" property="shortfallTemplateType" value="DSFO">
	         <tr id="UnderClause" style="display: none">
	    	    <td width="18%" class="formLabel">Under Clause : <span class="mandatorySymbol">*</span></td>
	    	   <td class="formLabel" width="30%">
	    	    <logic:empty name="frmShortFall" property="shortfallSeqID">
				<html:select property="shortfallUnderClause" styleId="shortfallUnderClause"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
	  			    <html:option value="">Select from list</html:option>
	  				<html:options collection="ShortfallUnderClause"  property="cacheId" labelProperty="cacheDesc"/>	  				
		    	</html:select>
		    	</logic:empty>
		    	 <logic:notEmpty name="frmShortFall" property="shortfallSeqID">
				<html:select property="shortfallUnderClause" styleId="shortfallUnderClause"  styleClass="selectBox selectBoxMedium" disabled="true">
				    <html:option value="">Select from list</html:option>
	  				<html:options collection="ShortfallUnderClause"  property="cacheId" labelProperty="cacheDesc"/>
	  				
		    	</html:select>
		    	 </logic:notEmpty>
	    	  </td>	    	
	         </tr>
	    </logic:match>
	    </logic:equal>	    
	    <logic:equal name="frmShortFall" property="displayclaims"  value="show">
	    <tr>
	    	<td width="18%" class="formLabel">Shortfall Status :</td> 			
	    	<td class="formLabel" width="30%">	
	    		<html:text property="currentShortfallStatus"  styleClass="textBox textBoxMedium textBoxDisabled" maxlength="20" disabled="true" readonly="true"/>	  			
	    	</td>
	    </tr>
	    </logic:equal>
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
		<logic:notEqual name="frmShortFall" property="displayclaims"  value="show">		
		<ttk:ShortFallQueries/>
		</logic:notEqual>
		
		<logic:equal name="frmShortFall" property="displayclaims"  value="show">
		<ttk:ShortFallQueriesClaims/>		
		</logic:equal>
		

<!-- E N D : Form Fields -->

<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td width="100%" align="center">
	       	    <%
    				if(TTKCommon.isAuthorized(request,"Edit"))
					{
			    %>
				<logic:equal name="frmShortFall" property="displayclaims"  value="show">
					<logic:notEmpty name="frmShortFall" property="shortfallSeqID">
					<button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onResendEmail();">Rese<u>n</u>d</button>&nbsp;
					</logic:notEmpty>
  					</logic:equal>
			    		
							<logic:match  name="frmShortFall" property="shortfallTypeID" value="ADS">
			    				
								<logic:empty name="frmShortFall" property="currentShortfallStatus">
			    				<button type="button" name="Button" accesskey="v" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateShortFall();"><u>V</u>iew Letter</button>&nbsp;
			    				</logic:empty>
								<logic:equal  name="frmShortFall" property="currentShortfallStatus" value="Initial Shortfall">
								<button type="button" name="Button" accesskey="v" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateShortFall();"><u>V</u>iew Letter</button>&nbsp;
								</logic:equal>
								</logic:match>
								
								<logic:notMatch name="frmShortFall" property="shortfallTypeID" value="ADS">
								 <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateShortFall();"><u>V</u>iew Letter</button>&nbsp;
								</logic:notMatch>							
		        				<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave & S<u>e</u>nd</button>&nbsp;
		        				<!-- <button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Reset();"><u>R</u>eset</button>&nbsp; -->

			    		
		    			<logic:notMatch name="frmShortFall" property="editYN" value="Y">
		    				<SCRIPT type="text/javascript">
								var TC_Disabled = true; //to avoid the alert message on change of form elements
							</SCRIPT>
		   	 			</logic:notMatch>
	    		<%
	    			}
	    		%>
		    		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:closeShortfalls();"><u>C</u>lose</button>&nbsp;
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
<html:hidden property="displayclaims"/>
<html:hidden property="saveYes"/><!-- 3 shortfall buttons merge -->

<input type="hidden" name="leftlink" value="">
<input type="hidden" name="sublink" value="">
<input type="hidden" name="tab" value="">

<input type="hidden" id="preAuthNo" name="preAuthNo" value="<%=TTKCommon.checkNull(PreAuthWebBoardHelper.getWebBoardDesc(request))%>">
<input type="hidden" id="type" name="shortfallTypeID" value="<bean:write name="frmShortFall" property="shortfallTypeID"/>"/>
<input type="hidden" name="cignaValueYN" value="<bean:write name="frmShortFall" property="cignaYN"/>"/>
<input type="hidden" name="memberClaimYN" value="<bean:write name="frmShortFall" property="memberClaimYN"/>"/>
<input type="hidden" id="shortfallTemplateType" name="shortfallTemplateType" value="<bean:write name="frmShortFall" property="shortfallTemplateType"/>"/>
<INPUT TYPE="hidden" NAME="shortFall" VALUE="SRT"/>
<input type="hidden" id="ShortfallTemplateNetworkType" name="ShortfallTemplateNetworkType" value="<bean:write name="frmShortFall" property="ShortfallTemplateNetworkType"/>"/><!-- shortfall phase1 -->

<input type="hidden" name="child" value="ShortFall Details">

<logic:notEmpty name="frmShortFall" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
<!-- 3 shortfall buttons merge -->
<logic:match name="frmShortFall" property="saveYes" value="Y">
	<logic:match name="frmShortFall" property="statusTypeID" value="OPN">
		<script language="javascript">
				onSendLoad();
		</script>
	</logic:match>
</logic:match>
<!-- 3 shortfall buttons merge -->

</html:form>
<!-- E N D : Content/Form Area -->