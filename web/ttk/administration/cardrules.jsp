<%
/** @ (#) cardrules.jsp Nov 08th, 2005
 * Project       : Vidal Health TPA  Services
 * File          : cardrules.jsp
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : 28th Sep 2005
 * @author 		 : Bhaskar Sandra
 * Modified by   : Lancy A
 * Modified date : 25th Mar 2006
 * Reason        :
 */
 %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	pageContext.setAttribute("enrollTypeCode",Cache.getCacheObject("enrollTypeCode"));
	String strLink=TTKCommon.getActiveLink(request);
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/cardrules.js"></script>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/CardRulesAction.do" method="post"  >

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
	    		<td width="57%"><bean:write name="frmCardRules" property="caption"/></td>
	    	<logic:match name="frmCardRules" property="product" value="N">
		    	<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	    	</logic:match>
		</tr>
	</table>
	<!-- E N D : Page Title -->

	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Form Fields -->

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

	<fieldset><legend>Card Rules</legend>
		<table align="center" class="formContainerWithoutPad" border="0" cellspacing="0" cellpadding="0">
	    	<tr>
		    	<td class="formLabel" width="20%">Select Enrollment Type:</td>
			    <logic:match name="frmCardRules" property="product" value="Y">
			    	<td class="formLabel" >
						<html:select property="enrollTypeCode" styleClass="selectBox selectBoxMedium" onchange="javascript:onSelect()">
						<html:option value="">Select from list</html:option>
						<html:optionsCollection name="enrollTypeCode" label="cacheDesc" value="cacheId" />
	 				 	</html:select>
					</td>
				</logic:match>
				<logic:match name="frmCardRules" property="product" value="N">
			    	<td>CORPORATE</td>
				</logic:match>
		        <td  class="formLabel">&nbsp;</td>
	        </tr>
		</table>
	</fieldset>

    <logic:present name="alCardRules" scope="session">

    <bean:define id="optionsTemp" name="frmCardRules" property="Option"/>
    
    
    <bean:define id="alGeneralTypeCode" name="frmCardRules" property="alGeneralTypeCode"/>
	<% int iRemaksID=0;%>
	<logic:iterate id="cardRules" name="alCardRules" >
		<logic:notEqual name="cardRules" property="options" value="<%=String.valueOf(optionsTemp) %>">
			<bean:define id="optionsTemp" name="cardRules" property="options"/>
			<fieldset><legend><% out.println(optionsTemp); %> </legend>
			<table align="center" class="formContainerWithoutPad" border="0" cellspacing="0" cellpadding="0">

			<logic:iterate id="cardRulesTmp" name="alCardRules" >
				<logic:equal name="cardRulesTmp" property="options" value="<%=String.valueOf(optionsTemp) %>">

				<INPUT TYPE="hidden" NAME="cardRuleTypeId" VALUE="<bean:write name="cardRulesTmp" property="cardRuleTypeId" />">
		    	<INPUT TYPE="hidden" NAME="cardRuleSeqId" VALUE="<bean:write name="cardRulesTmp" property="cardRulesSeqId" />">
			    <INPUT TYPE="hidden" NAME="cardRuleDesc" VALUE="<bean:write name="cardRulesTmp" property="description" />">
					<tr>
						<logic:notMatch name="cardRulesTmp" property="description" value="Provider Inclusion">
						    <logic:notMatch name="cardRulesTmp" property="description" value="Provider Exclusion">
						    	<logic:notMatch name="cardRulesTmp" property="description" value="AYUR/Homeo">
						   			 <logic:notMatch name="cardRulesTmp" property="description" value="PED/ Chronic">
										<td class="formLabel" width="20%"><bean:write name="cardRulesTmp" property="description" />: <span class="mandatorySymbol">*</span></td>
									</logic:notMatch>
								</logic:notMatch>
							</logic:notMatch>
						</logic:notMatch>
						<logic:match name="cardRulesTmp" property="description" value="Provider Inclusion">	
								<td class="formLabel" width="20%"><bean:write name="cardRulesTmp" property="description" />:</td>	        
					    </logic:match>
					    <logic:match name="cardRulesTmp" property="description" value="Provider Exclusion">	
								<td class="formLabel" width="20%"><bean:write name="cardRulesTmp" property="description" />:</td>	        
					    </logic:match>
					    
					    <logic:match name="cardRulesTmp" property="description" value="AYUR/Homeo">	
								<td class="formLabel" width="20%"><bean:write name="cardRulesTmp" property="description" />:</td>	        
					    </logic:match>
					    <logic:match name="cardRulesTmp" property="description" value="PED/ Chronic">	
								<td class="formLabel" width="20%"><bean:write name="cardRulesTmp" property="description" />:</td>	        
					    </logic:match>
						
						<td class="textLabel">
						<% iRemaksID++;%>
						<logic:match name="cardRulesTmp" property="showRemarks" value="Y">
							<html:select name="cardRulesTmp" styleId="<%=String.valueOf(iRemaksID)%>" property="generalTypeId" styleClass="selectBox selectBoxLarge" onchange="javascript:onGeneralTypeID(this);">
				                 <html:option value="">Select from list</html:option>
				                 <bean:define id="gnrlTypeIdList" name="cardRulesTmp" property="gnrlTypeIdList"/>
				                 <html:options collection="gnrlTypeIdList" property="cacheId" labelProperty="cacheDesc"/>
				          	</html:select>
			          	</logic:match>
				        <logic:notMatch name="cardRulesTmp" property="showRemarks" value="Y">
				        	<logic:match name="cardRulesTmp" property="description" value="Provider Inclusion">	
								<html:text name="cardRulesTmp" property="generalTypeId" style="width:243px;"/>	        
					        </logic:match>
					        <logic:match name="cardRulesTmp" property="description" value="Provider Exclusion">	
								<html:text name="cardRulesTmp" property="generalTypeId" style="width:243px;"/>	        
					        </logic:match>
				       		<logic:notMatch name="cardRulesTmp" property="description" value="Provider Inclusion">
					       		<logic:notMatch name="cardRulesTmp" property="description" value="Provider Exclusion">
						       		 <html:select name="cardRulesTmp" property="generalTypeId" styleClass="selectBox selectBoxLarge">
						                 <html:option value="">Select from list</html:option>
						                 <bean:define id="gnrlTypeIdList" name="cardRulesTmp" property="gnrlTypeIdList"/>
						                 <html:options collection="gnrlTypeIdList" property="cacheId" labelProperty="cacheDesc"/>
						          	</html:select>
					          	</logic:notMatch>
				          	</logic:notMatch>
				        </logic:notMatch>
						</td>
						<logic:match name="cardRulesTmp" property="showRemarks" value="Y">
							<td class="textLabel">
							<logic:match name="cardRulesTmp" property="generalTypeId" value="SRY">
								<input type="text" id="remarks<%=iRemaksID%>" name="shortRemarks" onkeypress="javascript:blockEnterkey(event.srcElement);" value="<bean:write name="cardRulesTmp" property="shortRemarks"/>" class="textBox textBoxLarge" maxlength="60" >
							</logic:match>
							<logic:notMatch name="cardRulesTmp" property="generalTypeId" value="SRY">
								<input type="text"  style="display:none;" id="remarks<%=iRemaksID%>" name="shortRemarks" onkeypress="javascript:blockEnterkey(event.srcElement);" value="<bean:write name="cardRulesTmp" property="shortRemarks"/>" class="textBox textBoxLarge" maxlength="60">
							</logic:notMatch>
							</td>
						</logic:match>
						<logic:notMatch name="cardRulesTmp" property="showRemarks" value="Y">
							<td class="textLabel">&nbsp;
								<input type="hidden" name="shortRemarks" value="">
							</td>
						</logic:notMatch>
				    </tr>
				</logic:equal>
			</logic:iterate>
			</table>
			</fieldset>
		</logic:notEqual>
	</logic:iterate>
    </logic:present>
  	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    <%
		    if(TTKCommon.isAuthorized(request,"Edit"))
			{
	    		if(request.getSession().getAttribute("alCardRules")!=null)
	    		{
	    %>
		    		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	    <%
	    		}//end of if(request.getAttribute("alCardRules")!=null)
		    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
	    %>
	    	<logic:match name="frmCardRules" property="close" value="Y">
		   	 	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
			</logic:match>
		</td>
	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons -->
	<%
    	if(strLink.equals("Products"))
      	{
    %>
			<input type="hidden" name="child" value="Define Card Rules">
	<%
      	}// end of if(strLink.equals("Products"))
    %>		
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="enrollType" VALUE="">
	<input type="hidden" name="cardRuleFor" id="cardRuleForid" value="<%= request.getSession().getAttribute("cardRuleFor")%>">
	<logic:notEmpty name="frmCardRules" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
<!-- E N D : Content/Form Area -->
</html:form>