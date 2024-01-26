<%
/** @ (#) onlineProvider.jsp 08th Jan 2015
 * Project     : TTK Healthcare Services
 * File        : onlineProvider.jsp
 * Author      : Kishor kumar
 * Company     : RCS Technologies
 * Date Created: 22nd Jan 2015
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
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/onlineforms/onlineprovider.js"></script>

<head>
	<link rel="stylesheet" type="text/css" href="ttk/styles/style.css" />
</head>
<%
DynaActionForm frmOnlineProviderContacts=(DynaActionForm)request.getSession().getAttribute("frmOnlineProviderContacts");
pageContext.setAttribute("hospCode", Cache.getCacheObject("hospCode"));
pageContext.setAttribute("regAuthority",Cache.getCacheObject("regAuthority"));
pageContext.setAttribute("providerType",Cache.getCacheObject("providerType"));
pageContext.setAttribute("prefix",Cache.getCacheObject("prefix"));
pageContext.setAttribute("stateCode", Cache.getCacheObject("stateCode"));
pageContext.setAttribute("designationTypeID",Cache.getCacheObject("designationHOS"));
pageContext.setAttribute("cityCode", Cache.getCacheObject("cityCode"));
pageContext.setAttribute("countryCode", Cache.getCacheObject("countryCode"));

pageContext.setAttribute("gender", Cache.getCacheObject("gender"));
%>

<!-- S T A R T : Content/Form Area -->
<div class="contentAreaProvider">
<html:form action="/NewEnrollContactSaveAction.do"  >

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
	    	<td>&nbsp;&nbsp;Online <bean:write name="frmOnlineProviderContacts" property="caption"/> 
		   	</td>
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
			  		<td>
			  			<img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:message name="updated" scope="request"/>
			  		</td>
				</tr>
			</table>
		</logic:notEmpty>
		<!-- E N D : Success Box -->
		
		<fieldset>
		<legend>Insurance Details</legend>
	    
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			<tr>
        		<td width="20%" class="formLabel">Contact Name: <span class="mandatorySymbol">*</span></td>
        		<td width="30%">
    				<html:select property ="prefix" styleClass="selectBox">
	           			<html:options collection="prefix" property="cacheId" labelProperty="cacheDesc"/>
    				</html:select>
					<html:text property="name" styleClass="textBox textBoxMedium" maxlength="250"/>
					 
        		</td>
        		<td width="20%" class="formLabel">Active: </td>
        		<td width="30%">
	        		<html:checkbox property="activeYN" value="Y"/>
        		</td>
        	
      		</tr>
      		
      		<tr>
	        		<td class="formLabel">Gender: </td>
	        		<td>
	        			<html:select property="gender" styleClass="selectBox" >
							<html:option value="">Select from list</html:option>
							<html:options collection="gender" property="cacheId" labelProperty="cacheDesc" />
						</html:select>
	        		</td>
	        		<td class="formLabel">Age: </td>
	        		<td>
	        			<html:text property="age" size="3" styleClass="textBox textBoxMedium" maxlength="3" />
	        		</td>
	       </tr>
	       
      		<tr>	
        		<td width="20%" class="formLabel">Designation: </td>
        		<td width="30%">
    				<html:select property ="designationTypeID" styleClass="selectBox selectBoxMedium">
	           			<html:option value="">Select from list</html:option>
	           			<html:options collection="designationTypeID" property="cacheId" labelProperty="cacheDesc"/>
    				</html:select>
        		</td>
        		<td class="formLabel">Mobile No.: <span class="mandatorySymbol">*</span></td>
        		<td>
	        		<html:text property="isdCode" styleClass="disabledfieldType" size="3" maxlength="3" onclick="changeMe(this)" onblur="getMe('ISD')" disabled="true"/>&nbsp;
        			<html:text property="mobileNbr" styleClass="textBox textBoxMedium" maxlength="10"/>
        		</td>
        	</tr>
	      		
      		<tr>
        		<td class="formLabel">Primary Mail ID: <span class="mandatorySymbol">*</span></td>
        		<td>
					<html:text property="primaryEmailID" styleClass="textBox textBoxLarge" maxlength="250" onblur="getProviderDetails(this)"/> 
        		</td>
        		
        		<td class="formLabel">Alternate Mail Id: </td>
      			<td>
      				<html:text property="secondaryEmailID" styleClass="textBox textBoxLarge" maxlength="250" onblur="getProviderDetails(this)"/> 
      			</td>
      		</tr>
      		
      		<tr>
        			<td class="formLabel">Office Phone:</td>
        			<td colspan="2">
        			
    				<html:text property="isdCode" styleClass="disabledfieldType" size="3" maxlength="3" onclick="changeMe(this)" onblur="getMe('ISD')" disabled="true"/>
    			
    			
        		<html:text property="stdCode" styleClass="disabledfieldType" size="4" maxlength="4" onclick="changeMe(this)" onblur="getMe('STD')" disabled="true"/>
    			
    			
    			<logic:empty name="frmOnlineProviderContacts" property="officePhone1">
    					<html:text property="officePhone1" styleClass="disabledfieldType" maxlength="15" value="Phone No" onclick="changeMe(this)" onblur="getMe('Phone No')"/>
    			</logic:empty>
    			<logic:notEmpty name="frmOnlineProviderContacts" property="officePhone1">
        			<html:text property="officePhone1" styleClass="disabledfieldType" maxlength="15" onclick="changeMe(this)" onblur="getMe('Phone No')"/>
    			</logic:notEmpty>
        			</td>
   			</tr>
  			</table>
  		</fieldset>
  		<!-- S T A R T : Buttons -->
  		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
		    		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUserSubmit();"><u>S</u>ave and Next</button>&nbsp;
		    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    	<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>B</u>ack</button>&nbsp;
		    		
 				</td>
  			</tr>  		
		</table>
		
		
		
	</div>
	      	
<input type="hidden" name="mode" value="">
	<!-- input type="hidden" name="child" value=""-->
</html:form>
	</div>