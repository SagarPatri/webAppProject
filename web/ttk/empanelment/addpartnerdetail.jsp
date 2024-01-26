<%
/**
 * @ (#) addgeneraldetail.jsp 21st Sep 2005
 * Project      : TTK HealthCare Services
 * File         : addgeneraldetail.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : 21st Sep 2005
 * @author       :
 * Modified by   : Krishna K H
 * Modified date : 13 Mar 2006
 * Reason        :
 */
%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/partnergeneral.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	
	<link rel="stylesheet" type="text/css" href="css/autoComplete.css" />
	<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
	<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>
	<SCRIPT>
	</SCRIPT>
</head>

	<script language="javascript">
	var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
	</script>
	
		<html:form action="/EditPartnerSearchAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
	    	<td>General Details - <bean:write name="frmAddPartner" property="caption"/></td>
	    	<td align="right">
	    	     <logic:notEmpty name="frmAddPartner" property="ptnrGnrlSeqId">
					<a href="#" onClick="onConfiguration()"><img src="/ttk/images/EditIcon.gif" alt="Configuration List" width="16" height="16" border="0" align="absmiddle"></a>
				</logic:notEmpty >
			<td>
	    	<td align="right" class="webBoard">&nbsp;
		    	<logic:match name="frmAddPartner" property="caption" value="Edit">
			    	<%@ include file="/ttk/common/toolbar.jsp" %>
				</logic:match>
		   	</td>
		</tr>
	</table>
	
	<%
	  pageContext.setAttribute("alOfficeInfo",Cache.getCacheObject("officeInfo"));
	  pageContext.setAttribute("alCountryCode",Cache.getCacheObject("countryCode"));
		pageContext.setAttribute("stateCode", Cache.getCacheObject("stateCode"));
		pageContext.setAttribute("cityCode", Cache.getCacheObject("cityCode"));
		pageContext.setAttribute("countryCode", Cache.getCacheObject("countryCode"));
		boolean viewmode=true;
		pageContext.setAttribute("viewmode",new Boolean(viewmode));

		if(TTKCommon.isAuthorized(request,"Edit") && TTKCommon.isAuthorized(request,"SpecialPermission"))
		
				{
					viewmode=true;
				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		              else if(TTKCommon.isAuthorized(request,"Edit")){
		                     viewmode=false;
		              }
		pageContext.setAttribute("viewmode",new Boolean(viewmode));
	%>
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
		<logic:notEmpty name="fileError" scope="request" >
	<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <bean:write name="fileError" scope="request"/>
	        </td>
	      </tr>
   	 </table>
   	 </logic:notEmpty>
	<fieldset>
	<legend>Partner Details </legend>
	    		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		<tr>
					<td width="20%" class="formLabel">Partner Name<span class="mandatorySymbol">*</span></td>
    					<td>
	        			<html:text property="partnerName" styleClass="textBox textBoxMedium" maxlength="60"/>
	        		</td>
    				<td width="20%" class="formLabel">Empanelment No.:</td>
    				<td width="30%" class="textLabelBold">
    					<bean:write name="frmAddPartner" property="emplNumber"/>
    				</td>	
  				</tr>
	
			<tr>
    				  <td class="formLabel">Empanelment Date:<span class="mandatorySymbol">*</span></td>
             		   <td>
        				<html:text name="frmAddPartner" property="empanelmentDate" styleId="empanelmentDate" styleClass="textBox textDate" maxlength="10"/>
        				
        						<a name="CalendarObjectEndDate" id="CalendarObjectEndDate" href="#" onClick="javascript:show_calendar('CalendarObjectEndDate','frmAddPartner.empanelmentDate',document.frmAddPartner.empanelmentDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
        					</a>   				
        			</td>
					
					<td class="formLabel"> Partner Licence ID: <span class="mandatorySymbol">*</span></td>
			  		<td>
			  			<html:text property="irdaNumber" styleId="irdaNumber" styleClass="textBox textBoxMedium" maxlength="60"/>
			  		</td>
  			</tr>
			<tr>
	        		<td class="formLabel">Trade License No.: </td>
	        		<td>
	        			<html:text property="panNmbr" styleId="panNmbr" styleClass="textBox textBoxMedium" maxlength="60"/>
	        		</td>
	        		
	        		<td class="formLabel">Name as per Trade License:</td>
	        		<td>
	        			<html:text property="tradeLicenceName" styleClass="textBox textBoxMedium" maxlength="60"/>
	        		</td>
	      		</tr>
	      		<tr>
	      		<td class="formLabel">Country Covered <span class="mandatorySymbol">*</span></td>
    			  <td>
         				<html:select property="countryCode" styleClass="selectBox selectBoxMedium" >
         					<html:option value="">Select from list</html:option>
                 		 <html:options collection="alCountryCode" property="cacheId" labelProperty="cacheDesc"/>
            		 	</html:select>
            		</td>
            		<%-- <td class="formLabel">Currency Accepted:<span class="mandatorySymbol">*</span></td>
			      <td  width="30%" class="textLabel">			      
			       <html:text property="currencyAccepted"  styleId="currencyAccepted"  styleClass="textBox textBoxMedium" readonly="true" />
			      </td>	 --%>	
			      
			      	<td class="formLabel">Currency Accepted:<span class="mandatorySymbol">*</span></td>
						<td class="textLabel">
						<logic:empty name="frmAddPartner"  property="currencyAccepted" scope="session">
						<html:text property="currencyAccepted" styleId="currencyAccepted"  value="OMR"   styleClass="textBox textBoxMedium textBoxDisabled" readonly="false" /><a href="#" onclick="openRadioList('currencyAccepted','CURRENCY_GROUP','option');clearConversionRate();">
		        				  <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency" border="0" align="bottom" > </a></td>
		        			</logic:empty>	
		        			<logic:notEmpty  name="frmAddPartner"  property="currencyAccepted" scope="session">  
		        				<html:text property="currencyAccepted" styleId="currencyAccepted"    styleClass="textBox textBoxMedium textBoxDisabled" readonly="false" /><a href="#" onclick="openRadioList('currencyAccepted','CURRENCY_GROUP','option');clearConversionRate();">
		        				  <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency" border="0" align="bottom" > </a></td>
		        	
		        			</logic:notEmpty>
	      		</tr>
	      		<tr>
	      		<td>Vidal Health Location: <span class="mandatorySymbol">*</span> </td>
			    <td>
			    	<html:select property="TTKBranchCode" styleClass="selectBox selectBoxMedium" >
					<%--<html:option value="">Select from list</html:option>--%>
        				<html:options collection="alOfficeInfo" property="cacheId" labelProperty="cacheDesc"/>
			  		</html:select>
			    </td>
			    <td class="formLabel">In-Patient Pre-approval Limit<span class="mandatorySymbol">*</span></td>
					  <td>
					  <html:text name="frmAddPartner" property="limit" styleId="limit"  styleClass="textBox textBoxSmall" />
					  </td>
	      		</tr>
	</table>
	</fieldset>
	
	
	<fieldset>
    		<legend>Address Details</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      			<tr>
        			<td width="20%" class="formLabel">Address 1: <span class="mandatorySymbol">*</span></td>
        			<td width="30%">
        				<html:text property="addressVO.address1" styleClass="textBox textBoxMedium" maxlength="250" />
        			</td>
        			<td width="20%" class="formLabel">Address 2:</td>
        			<td width="30%">
        				<html:text property="addressVO.address2" styleClass="textBox textBoxMedium" maxlength="250" />
        			</td>
      			</tr>
      			<tr>
        			<td class="formLabel">Address 3: </td>
       				<td>
       					<html:text property="addressVO.address3" styleClass="textBox textBoxMedium" maxlength="250" />
       				</td>
        			<td class="formLabel">City: <span class="mandatorySymbol">*</span></td>
        			<td>
            			<html:select property ="addressVO.stateCode" styleId="state" styleClass="selectBox selectBoxMedium" onchange="onChangeState(this)">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="stateCode" property="cacheId" labelProperty="cacheDesc"/>
    	   				</html:select>
        			</td>
      			</tr>
      			<tr>
        			<td class="formLabel">Area: <span class="mandatorySymbol">*</span></td>
        			<td>
        				<html:select property ="addressVO.cityCode" styleClass="selectBox selectBoxMedium" >
                 			<html:option value="">Select from list</html:option>
                 			<html:optionsCollection property="alCityList" label="cacheDesc" value="cacheId"/>
          				</html:select>
        			</td>
        			<td class="formLabel">PO Box: <span class="mandatorySymbol">*</span></td>
					<td>
						<html:text property="addressVO.pinCode" styleClass="textBox textBoxSmall" maxlength="10"/>
					</td>
      			</tr>
      			<tr>
        			<td class="formLabel">Country: <span class="mandatorySymbol">*</span></td>
        			<td> 
         				<html:select property ="addressVO.countryCode" styleClass="selectBox selectBoxMedium" >
         					<html:option value="">Select from list</html:option>
                 			<html:options collection="countryCode" property="cacheId" labelProperty="cacheDesc"/>
          				</html:select>
        			</td>
        			<td class="formLabel">&nbsp;</td>
        			<td>&nbsp;</td>
      			</tr>
       			<tr>
        			<td class="formLabel">Phone No.:<span class="mandatorySymbol">*</span></td>
        			<td colspan="2">	
        		<logic:equal name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="isdCode" styleClass="disabledfieldType" size="3" maxlength="3" value="ISD" onclick="changeMe(this)" onblur="getMe('ISD')"/>
    			</logic:equal>
    			<logic:notEqual name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="isdCode" styleClass="disabledfieldType" size="3" maxlength="3" onclick="changeMe(this)" onblur="getMe('ISD')"/>
    			</logic:notEqual>	
    			
    			<logic:equal name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="stdCode" styleClass="disabledfieldType" size="4" maxlength="4" value="STD" onclick="changeMe(this)" onblur="getMe('STD')"/>
    			</logic:equal>
    			<logic:notEqual name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="stdCode" styleClass="disabledfieldType" size="4" maxlength="4" onclick="changeMe(this)" onblur="getMe('STD')"/>
    			</logic:notEqual>	
    			
    			<logic:equal name="stdCodes" value="stdCodes" scope="request">
	    			<html:text property="officePhone1" styleClass="disabledfieldType" maxlength="15" value="Phone No" onclick="changeMe(this),isPositiveInteger(this,'Phone No.')" onblur="getMe('Phone No')" />
    			</logic:equal>
    			<logic:notEqual name="stdCodes" value="stdCodes" scope="request">
        			<html:text property="officePhone1" styleClass="disabledfieldType" maxlength="15" onclick="changeMe(this)" onblur="getMe('Phone No')"/>
    			</logic:notEqual>
      			</tr>
      			
       			<tr>
        			<td class="formLabel">Fax:</td>
        			<td>
       			<logic:equal name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="isdCode" styleClass="disabledfieldType" size="3" maxlength="3" value="ISD" onclick="changeMe(this)" onblur="getMe('ISD')"/>
    			</logic:equal>
    			<logic:notEqual name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="isdCode" styleClass="disabledfieldType" size="3" maxlength="3" onclick="changeMe(this)" onblur="getMe('ISD')"/>
    			</logic:notEqual>	
    			
    			<logic:equal name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="stdCode" styleClass="disabledfieldType" size="4" maxlength="4" value="STD" onclick="changeMe(this)" onblur="getMe('STD')"/>
    			</logic:equal>
    			<logic:notEqual name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="stdCode" styleClass="disabledfieldType" size="4" maxlength="4" onclick="changeMe(this)" onblur="getMe('STD')"/>
    			</logic:notEqual>	
    			
    			<logic:equal name="stdCodes" value="stdCodes" scope="request">
	    			<html:text property="faxNbr" styleClass="disabledfieldType" maxlength="15" value="Phone No" onclick="changeMe(this),isPositiveInteger(this,'Phone No.')" onblur="getMe('Phone No')" />
    			</logic:equal>
    			<logic:notEqual name="stdCodes" value="stdCodes" scope="request">
        			<html:text property="faxNbr" styleClass="disabledfieldType" maxlength="15" onclick="changeMe(this)" onblur="getMe('Phone No')"/>
    			</logic:notEqual>
        				<%-- <html:text property="faxNbr" styleClass="textBox textBoxMedium" maxlength="15" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/> --%>
        			</td>
        			<td class="formLabel">Pre Empanelment Mail ID:</td>
        			<td>
        				<html:text property="preEmpanelMailId" styleClass="textBox textBoxLarge"/>
        			</td>
      			</tr>
      			<tr>
        			<td class="formLabel">Email Id: <span class="mandatorySymbol">*</span></td>
        			<td>
        				<html:text property="primaryEmailId" styleClass="textBox textBoxLarge"/>
        			</td>
        			<td class="formLabel">Web Site: </td>
        			<td>
        				<html:text name="frmAddPartner" property="website" styleClass="textBox textBoxLarge" />
        			</td>
      			</tr>
    		</table>
		</fieldset>
		<fieldset>
			<legend>Service Details</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
        			<td class="formLabel">Agreed Administrative fee:</td>
					  <td>
					  	<html:text name="frmAddPartner" property="fee"  styleId="fee" styleClass="textBox textBoxSmall" />
					  </td>
        			<td class="formLabel">Discount(%)</td>
					  <td>
					  <html:text name="frmAddPartner" property="discount" styleId="discount"  styleClass="textBox textBoxSmall" />
					  </td>
				<tr>
                <td class="formLabel"> Service Start Date:</td>
                 <td>
        				<html:text name="frmAddPartner" property="startDate" styleId="startDate" styleClass="textBox textDate" maxlength="10"/>
        				
        					<a name="CalendarObjectStartDate" id="CalendarObjectStartDate" href="#" onClick="javascript:show_calendar('CalendarObjectStartDate','frmAddPartner.startDate',document.frmAddPartner.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
        					</a>    				
        			</td>
        			<td class="formLabel"> Service End Date: </td>
                 <td>
        				<html:text name="frmAddPartner" property="endDate" styleId="endDate" styleClass="textBox textDate" maxlength="10"/>
        				
        					<a name="CalendarObjectEndDate" id="CalendarObjectEndDate" href="#" onClick="javascript:show_calendar('CalendarObjectEndDate','frmAddPartner.endDate',document.frmAddPartner.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
        					</a>
        			</td>
      			</tr>		 
      							<tr>
                <td class="formLabel"> 	<!-- <a href="#" onClick="javascript:onUploadDocs()">Document Uploads </a> --><a href="#">Document Uploads </a></td>
                 <td></td>
        		<td class="formLabel"> </td>
                 <td></td>
      			</tr>	 
	  		</table>
		</fieldset>
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
    			<%
				if(TTKCommon.isAuthorized(request,"Edit"))
				{
				%>
		    		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUserSubmit();"><u>S</u>ave</button>&nbsp;
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
				<%
	    		}
 				%>
 				</td>
  			</tr>  		
		</table>
		<!-- E N D : Buttons -->
		</div>

	<input type="hidden" name="mode" value="">
	<input type="hidden" name="focusID" value="">
	<html:hidden property="caption"/>
	  <html:hidden property="stopPreAuth" />        
    <html:hidden property="stopClaim" />
	<html:hidden property="ptnrSeqId"/>
	<html:hidden property="stopClaimsYN" styleId="stopClaimsYN"/>
	<html:hidden property="stopPreAuthsYN" styleId="stopPreAuthsYN"/>
	<logic:notEmpty name="frmAddPartner" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
	</html:form>