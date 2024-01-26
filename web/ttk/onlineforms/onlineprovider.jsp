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
DynaActionForm frmOnlineProvider=(DynaActionForm)request.getSession().getAttribute("frmOnlineProvider");
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
<html:form action="/NewEnrollAction.do"  >

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
	    	<td>&nbsp;&nbsp;Online <bean:write name="frmOnlineProvider" property="caption"/> 
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
			<legend>General Information</legend>
			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			
			<tr>
        		<td width="20%" class="formLabel">Provider Name: <span class="mandatorySymbol">*</span></td>
        		<td width="30%">
					<html:text property="hospitalName" styleClass="textBox textBoxLarge" maxlength="250" readonly="true"/> 
        		</td>
        		<td width="20%" class="formLabel">Speciality: </td>
        		<td width="30%">
    				<html:select property ="speciality" styleClass="selectBox selectBoxMedium">
	           			<html:option value="">Select from list</html:option>
	           			<html:options collection="hospCode" property="cacheId" labelProperty="cacheDesc"/>
    				</html:select>
        		</td>
      		</tr>
	      		
      		<tr>
        		<td class="formLabel">Trade License No.: </td>
        		<td>
        			<html:text property="tradeLicenceNo" styleClass="textBox textBoxMedium" maxlength="10"/>
        		</td>
        		<td class="formLabel">Provider Type: <span class="mandatorySymbol">*</span></td>
      			<td>
      				<html:select property ="providerTypeId" styleClass="selectBox selectBoxMedium">
               			<html:option value="">Select from list</html:option>
               			<html:options collection="providerType" property="cacheId" labelProperty="cacheDesc"/>
   	   				</html:select>
      			</td>
      		</tr>
	      		
      		<tr>
      		<td class="formLabel">Registering Authority:</td>
        		<td>
					<html:select property ="regAuthority" styleClass="selectBox selectBoxMedium">		
               			<html:options collection="regAuthority" property="cacheId" labelProperty="cacheDesc"/>
       				</html:select>
        		</td>
       			<td class="formLabel">Health Authority License No.:</td>
        		<td>
        			<html:text property="authLicenceNo" styleClass="textBox textBoxMedium" maxlength="60" readonly="true"/>
        		</td>
      		</tr>

<!-- Address Details -->

			<tr>
        			<td width="20%" class="formLabel">Address 1: <span class="mandatorySymbol">*</span></td>
        			<td width="30%">
        				<html:text property="address1" styleClass="textBox textBoxMedium" maxlength="250"/>
        			</td>
        			<td width="20%" class="formLabel">Address 2:</td>
        			<td width="30%">
        				<html:text property="address2" styleClass="textBox textBoxMedium" maxlength="250"/>
        			</td>
      		</tr>
   			<tr>
     			<td class="formLabel">Address 3: </td>
    				<td>
    					<html:text property="address3" styleClass="textBox textBoxMedium" maxlength="250"/>
    				</td>
     			<td class="formLabel">Emirate/State: <span class="mandatorySymbol">*</span></td>
     			<td>
         			<html:select property ="stateCode" styleId="state" styleClass="selectBox selectBoxMedium" onchange="onChangeState(this)" >
              			<html:option value="">Select from list</html:option>
              			<html:options collection="stateCode" property="cacheId" labelProperty="cacheDesc"/>
 	   				</html:select>
     			</td>
   			</tr>
			<tr>
				<td class="formLabel">Area: <span class="mandatorySymbol">*</span></td>
				<td>
					<html:select property ="cityCode" styleClass="selectBox selectBoxMedium" >
						<html:option value="">Select from list</html:option>
						<html:optionsCollection property="alCityList" label="cacheDesc" value="cacheId"/>
						</html:select>
		   			</td>
   			<td class="formLabel">PO Box: <span class="mandatorySymbol">*</span></td>
			<td>
				<html:text property="pinCode" styleClass="textBox textBoxSmall" maxlength="10" />
			</td>
 			</tr>
   			<tr>
     			<td class="formLabel">Country: <span class="mandatorySymbol">*</span></td>
     			<td>
      				<html:select property ="countryCode" styleClass="selectBox selectBoxMedium" >
      				<html:option value="">--Select from list--</html:option>
              			<html:options collection="countryCode" property="cacheId" labelProperty="cacheDesc"/>
       				</html:select>
     			</td>
     			<td class="formLabel">&nbsp;</td>
     			<td>&nbsp;</td>
   			</tr>
   			<tr>
     			<td class="formLabel">Landmark, if any:</td>
     			<td colspan="3">
     				<html:textarea property="landmarks" styleClass="textBox textAreaLong"/>
     			</td>
   			</tr>
   			<tr>
    			<td class="formLabel">Provider Board Phone No.:<span class="mandatorySymbol">*</span></td>
    			<td>
    			<html:text property="isdCode" styleClass="disabledfieldType" size="3" maxlength="3" onclick="changeMe(this)" onblur="getMe('ISD')"/>
    				<html:text property="stdCode" styleClass="disabledfieldType" size="4" maxlength="4" onclick="changeMe(this)" onblur="getMe('STD')"/>
    				
    				<logic:empty name="frmOnlineProvider" property="officePhone1">
	    				<html:text property="officePhone1" styleId="officePhone1" styleClass="disabledfieldType" maxlength="15" value="Phone No1" onclick="changeMe(this)" onblur="getMe('Phone No2')"/>
	    			</logic:empty>
	    			<logic:notEmpty name="frmOnlineProvider" property="officePhone1">
	        			<html:text property="officePhone1" styleId="officePhone1" styleClass="disabledfieldType" maxlength="15" onclick="changeMe(this)" onblur="getMe('Phone No1')"/>
	    			</logic:notEmpty>
    			</td>
    			<td class="formLabel">Office Phone 2:</td>
    			<td>

	    			<html:text property="isdCode" styleClass="disabledfieldType" size="3" maxlength="3" onclick="changeMe(this)" onblur="getMe('ISD')"/>
    				<html:text property="stdCode" styleClass="disabledfieldType" size="4" maxlength="4" onclick="changeMe(this)" onblur="getMe('STD')"/>
    				
    				<logic:empty name="frmOnlineProvider" property="officePhone2">
	    				<html:text property="officePhone2" styleId="officePhone2" styleClass="disabledfieldType" maxlength="15" value="Phone No2" onclick="changeMe(this)" onblur="getMe('Phone No2')"/>
	    			</logic:empty>
	    			<logic:notEmpty name="frmOnlineProvider" property="officePhone2">
	        			<html:text property="officePhone2" styleId="officePhone2" styleClass="disabledfieldType" maxlength="15" onclick="changeMe(this)" onblur="getMe('Phone No2')"/>
	    			</logic:notEmpty>
    			
    			</td>
  			</tr>
   			<tr>
    			<td class="formLabel">Fax:</td>
    			<td>
    				<html:text property="faxNbr" styleClass="textBox textBoxMedium" maxlength="15"/>
    			</td>
    			<td class="formLabel">&nbsp;</td>
    			<td>&nbsp;</td>
  			</tr>
   			<tr>
     			<td class="formLabel">Email Id:</td>
     			<td>
     				<html:text property="primaryEmailId" styleClass="textBox textBoxLarge" maxlength="60"/>
     			</td>
     			<td class="formLabel">WebSite: </td>
     			<td>
     				<html:text property="website" styleId="website" styleClass="textBox textBoxLarge" maxlength="60"/>
     			</td>
   			</tr>
<!-- Address E N D S -->
		</table>
		</fieldset>
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
		    	<!-- button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onNewEnrolSubmit();"><u>S</u>ave</button-->&nbsp;
		    	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveAndNext();"><u>S</u>ave and Next</button>&nbsp;
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