<%
/**
 * @ (#) groupdetail.jsp 12th Jan 2006
 * Project      : TTK HealthCare Services
 * File         : groupdetail.jsp
 * Author       : Pradeep.R
 * Company      : Span Systems Corporation
 * Date Created : 12th Jan 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%
	pageContext.setAttribute("listGroupType",Cache.getCacheObject("groupType"));
	pageContext.setAttribute("listCityCode",Cache.getCacheObject("cityCode"));
	pageContext.setAttribute("listStateCode",Cache.getCacheObject("stateCode"));
	pageContext.setAttribute("listCountryCode",Cache.getCacheObject("countryCode"));
	pageContext.setAttribute("listTTKBranch",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("notificinfo",Cache.getCacheObject("notificinfo"));
%>

<link rel="stylesheet" type="text/css" href="css/style.css" />
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/empanelment/groupdetail.js"></SCRIPT>
<script language="javascript">
function getMe(obj)
{
	//alert(obj);
	if(obj=='Phone No1')
	{
		if(document.getElementById("phoneNbr1").value=="")
			document.getElementById("phoneNbr1").value=obj;
		
	}else if(obj=='Phone No2')
	{
		if(document.getElementById("phoneNbr2").value=="")
			document.getElementById("phoneNbr2").value=obj;
	}
}

function changeMe(obj)
{
	var val	=	obj.value;
	//alert(val);
	if(val=='Phone No1')
	{
		document.getElementById("phoneNbr1").value="";
	}
	if(val=='Phone No2')
	{
		document.getElementById("phoneNbr2").value="";
	}
}
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/GroupRegistrationAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>
	    	 <logic:notMatch name="frmGroupDetail" property="office" value="Office">
	    	 	<bean:write name="frmGroupDetail" property="caption" /> Group Details
	    	 		<logic:match name="frmGroupDetail" property="caption" value="Edit">
	    	 			- [<bean:write name="frmGroupDetail" property="parentGroupName" />]
	    	 		</logic:match>
	    	 </logic:notMatch>
		     <logic:match name="frmGroupDetail" property="office" value="Office"><bean:write name="frmGroupDetail" property="caption" /> Office Details - [<bean:write name="frmGroupDetail" property="parentGroupName" />]
		    </logic:match>

		</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
    <div class="contentArea" id="contentArea">
    <html:errors/>
    
    <logic:notEmpty name="errorMsg" scope="request">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="errorMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
    
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

	<fieldset>
		<legend><bean:write name="frmGroupDetail" property="office" /> Information</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<logic:notMatch name="frmGroupDetail" property="office" value="Office">
			   <td class="formLabel">Group Type: </td>
			    <td>
				   <html:select property="groupGenTypeID" styleClass="selectBox selectBoxMedium" onchange="onchangeGroup();">
						<html:optionsCollection name="listGroupType" label="cacheDesc" value="cacheId"/>
					</html:select>

			    </td>
			    <td valign="bottom" nowrap>Branch Location: <span class="mandatorySymbol">*</span></td>
			    <td>
	            	<html:select property="officeSeqID" styleClass="selectBox selectBoxMedium" >
					<html:option value="">Select from list</html:option>
					<html:optionsCollection name="listTTKBranch" label="cacheDesc" value="cacheId"/>
					</html:select>
	            </td>
			    <td class="formLabelBold">&nbsp;</td>
  		  </tr>
  		  </logic:notMatch>

		  <tr>
		     <td class="formLabel" width="17%"><bean:write name="frmGroupDetail" property="office" /> Name: <span class="mandatorySymbol">*</span></td>
			    <td width="37%">
			    	<html:text property="groupName" onkeypress="ConvertToUpperCase(event.srcElement);" styleClass="textBox textBoxLarge" maxlength="120" />
			 	</td>
			 <td width="17%" class="formLabel">Group Id: <span class="mandatorySymbol"></span></td>
		    <td width="29%" class="formLabelBold"><bean:write name="frmGroupDetail" property="groupID" /></td>
		  </tr>
		   <tr>
	        <td width="17%" class="formLabel">Location Code: <span class="mandatorySymbol">*</span></td>
	        <td width="37%">
	        	<html:text property="locationCode" onkeypress="ConvertToUpperCase(event.srcElement);" styleClass="textBox textBoxMedium"  maxlength="60"/>
	        </td>
			 <!--  added for koc1346-->
			         <!--   <td class="formLabel" width="20%">PPN (Yes/No):</td>	 				
						<td width="4%">	  				
						<html:checkbox  property="ppnYN"  value="Y" styleId="ppnYN" /> -->
				        <input type="hidden" name="ppnYN" value="N">	 					
			           <!--  </td> -->
			    <!-- end added for koc1346-->	
			    
			    <td class="formLabel" width="17%"><%-- <bean:write name="frmGroupDetail" property="tradeLicenseNo" /> --%> Trade License Number: <span class="mandatorySymbol">*</span> </td>
			    <td width="37%">
			    	<html:text property="tradeLicenseNo" onkeypress="ConvertToUpperCase(event.srcElement);"  styleClass="textBox textBoxMedium" /><%-- maxlength="60" --%> 
			 	</td>  
			    
			    
          </tr>
		  <logic:match name="frmGroupDetail" property="office" value="Office">
		  <tr>
			  <td valign="bottom" nowrap>Vidal Health Branch: <span class="mandatorySymbol">*</span></td>
				    <td>
		            	<html:select property="officeSeqID" styleClass="selectBox selectBoxMedium" >
						<html:option value="">Select from list</html:option>
						<html:optionsCollection name="listTTKBranch" label="cacheDesc" value="cacheId"/>
						</html:select>
		            </td>
			  <td class="formLabelBold">&nbsp;</td>
		  </tr>
		 </logic:match>
		</table>
	</fieldset>


<fieldset>
<legend>Address Information</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="17%" class="formLabel">Address 1: <span class="mandatorySymbol">*</span></td>
        <td width="37%">
        	<html:text property="addressVO.address1" styleClass="textBox textBoxMedium"  maxlength="250"/>
        </td>
        <td width="17%" class="formLabel">Address 2: </td>
        <td width="29%">
        	<html:text property="addressVO.address2" styleClass="textBox textBoxMedium"  maxlength="250" />
        </td>
      </tr>
      <tr>
        <td class="formLabel">Address 3: </td>
        <td>
        	<html:text property="addressVO.address3" styleClass="textBox textBoxMedium" maxlength="250" />
        </td>
        <td class="formLabel">Governorate/State: <span class="mandatorySymbol">*</span></td>
        <td>
			<html:select property="addressVO.stateCode" styleId="state" styleClass="selectBox selectBoxMedium" onchange="onChangeState(this)">
				<html:option value="">Select from list</html:option>
				<html:optionsCollection name="listStateCode" label="cacheDesc" value="cacheId"/>
			</html:select>
        </td>
      </tr>
      <tr>
		<td class="formLabel">Area: <span class="mandatorySymbol">*</span></td>
        <td>
    		<html:select property="addressVO.cityCode" styleClass="selectBox selectBoxMedium" >
				<html:option value="">Select from list</html:option>
				<html:optionsCollection property="alCityList" label="cacheDesc" value="cacheId"/>
			</html:select>
        </td>

        <td class="formLabel">PO Box: <span class="mandatorySymbol">*</span></td>
        <td>
        	<html:text property="addressVO.pinCode" styleClass="textBox textBoxSmall"  maxlength="10"/>
        </td>

      </tr>
      <tr>
        <td class="formLabel">Country: <span class="mandatorySymbol">*</span></td>
        <td>
        	<html:select property="addressVO.countryCode" styleClass="selectBox selectBoxMedium" >
        		<html:option value="">--Select from List--</html:option>
				<html:optionsCollection name="listCountryCode" label="cacheDesc" value="cacheId"/>
			</html:select>
        </td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
  			<td class="formLabel">Phone No1 :<span class="mandatorySymbol">*</span></td>
  			<td>
				<html:text property="addressVO.isdCode" styleClass="disabledfieldType" size="3" maxlength="3" readonly="true"/>&nbsp;
				<html:text property="addressVO.stdCode" styleClass="disabledfieldType" size="3" maxlength="3" readonly="true"/>&nbsp;
				
			
		   		<logic:empty name="frmGroupDetail" property="addressVO.phoneNo1">
				<html:text property="addressVO.phoneNo1" styleId="phoneNbr1" styleClass="disabledfieldType" value="Phone No1" maxlength="15" onclick="changeMe(this)" onblur="getMe('Phone No1')"/> 
				</logic:empty>
				
				<logic:notEmpty name="frmGroupDetail" property="addressVO.phoneNo1">
		   			<html:text property="addressVO.phoneNo1" styleId="phoneNbr1" styleClass="disabledfieldType" onclick="changeMe(this)" maxlength="15" onblur="getMe('Phone No1')"/> 
		   		</logic:notEmpty>
   		</td>
   		
		<td class="formLabel">Phone No2 :</td>
		<td>
				<html:text property="addressVO.isdCode" styleClass="disabledfieldType" size="3" maxlength="3" readonly="true"/>&nbsp;
				<html:text property="addressVO.stdCode" styleClass="disabledfieldType" size="3" maxlength="3" readonly="true"/>&nbsp;
				
				
				<logic:empty name="frmGroupDetail" property="addressVO.phoneNo2">
				<html:text property="addressVO.phoneNo2" styleId="phoneNbr2" styleClass="disabledfieldType" maxlength="15" value="Phone No2" onclick="changeMe(this)" onblur="getMe('Phone No2')"/>
				</logic:empty>
				
				<logic:notEmpty name="frmGroupDetail" property="addressVO.phoneNo2">
		   			<html:text property="addressVO.phoneNo2" styleId="phoneNbr2" styleClass="disabledfieldType"  maxlength="15" onclick="changeMe(this)" onblur="getMe('Phone No2')"/> 
		   		</logic:notEmpty>    
   		</td>			
	</tr>
      			
    </table>
 </fieldset>
 <logic:notMatch name="frmGroupDetail" property="office" value="Office">
	<div id="corporate" style="display:none;">
	 	<fieldset>
		<legend>General Notification Information</legend>
		    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			    <tr>
				    <td width="17%" class="formLabel">To Email Id:</td>
				    <td width="37%">
				    	<html:text property="notiEmailId" styleClass="textBox textBoxMedium"  maxlength="250"/>
				    </td>
				    <td width="17%" class="formLabel">Cc Email Id:</td>
				    <td width="29%">
				    	<html:text property="ccEmailId" styleClass="textBox textBoxMedium"  maxlength="250"/>
				    </td>
					
			    </tr>
			    <tr>
			    	<td class="formLabel">Notification Type:</td>
				    <td>
				    	<html:select property="notiTypeId" styleClass="selectBox selectBoxMedium" >
							<html:optionsCollection name="notificinfo" label="cacheDesc" value="cacheId"/>
						</html:select>
				    </td>
									    
					<td width="17%" class="formLabel">HR Email Id: </td> <!-- <span class="mandatorySymbol">*</span> according cr-047--> 
				    <td width="29%">
				    	<html:text property="hrEmailId" styleClass="textBox textBoxMedium"  maxlength="250"/>
				    </td>
			    </tr>
		    </table>
		</fieldset>
	</div>
</logic:notMatch>
<logic:match name="frmGroupDetail" property="office" value="Office">
		<logic:equal name="frmGroupList" property="groupType" value="CRP">
	 	<fieldset>
		<legend>General Notification Information</legend>
		    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			    <tr>
				    <td width="17%" class="formLabel">To Email Id:</td>
				    <td width="37%">
				    	<html:text property="notiEmailId" styleClass="textBox textBoxMedium"  maxlength="250"/>
				    </td>
				    <td width="17%" class="formLabel">Cc Email Id:</td>
				    <td width="29%">
				    	<html:text property="ccEmailId" styleClass="textBox textBoxMedium"  maxlength="250"/>
				    </td>
					
			    </tr>
			    <tr>
			    	<td class="formLabel">Notification Type:</td>
				    <td>
				    	<html:select property="notiTypeId" styleClass="selectBox selectBoxMedium" >
							<html:optionsCollection name="notificinfo" label="cacheDesc" value="cacheId"/>
						</html:select>
				    </td>
				    
					<td width="17%" class="formLabel">HR Email Id:</td>
				    <td width="29%">
				    	<html:text property="hrEmailId" styleClass="textBox textBoxMedium"  maxlength="250"/>
				    </td>
			    </tr>
		    </table>
		</fieldset>
	</logic:equal>
</logic:match>
<fieldset>
	<legend>Online Assistance Correspondence</legend>
	    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				    <td width="17%" class="formLabel">Email ID:</td>
				    <td>
				    	<html:text property="emailId" styleClass="textBox textBoxMedium"  maxlength="250"/>
				    </td>
			</tr>	    
	    </table>
</fieldset>		
<fieldset>
<legend>Account Manager Information</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
	      <td width="17%" class="formLabel">Manager Name: </td>
	      <td width="37%" class="textLabelBold">
	        	<bean:write name="frmGroupDetail" property="accntManagerName"/>
	        	<a href="#" onClick="javascript:selectAccntManager();"><img src="/ttk/images/EditIcon.gif" alt="Select Account Manager" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;
	        	<a href="#" onClick="javascript:clearAccntManager();"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Account Manager" width="16" height="16" border="0" align="absmiddle"></a>
	      </td>
	      <td width="17%" class="formLabel">&nbsp;</td>
	      <td width="29%">&nbsp;</td>
      <tr>  
    </table>
 </fieldset>
  <!-- E N D : Form Fields -->
<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
		     <%
	    		if(TTKCommon.isAuthorized(request,"Edit"))
				{
		     %>
		   			 <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
					 <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
		     <%
		       	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		     %>

			<%
	    		if(TTKCommon.isAuthorized(request,"Delete"))
				{
		    %>
				   <logic:match name="frmGroupDetail" property="caption" value="Edit">
				   		 <button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>&nbsp;
				    </logic:match>
			<%
		       	}//end of if(TTKCommon.isAuthorized(request,"Delete"))
		     %>
			    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		</td>
	  </tr>
	</table>
<!-- E N D : Buttons --></div>
<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->
	<input type="hidden" name="child" value="GroupDetails">
	<input type="hidden" name="focusID" value="">
 	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
    <INPUT TYPE="hidden" NAME="mode" VALUE="">
    <html:hidden property="accntManagerName"/>
	<html:hidden property="accntManagerSeqID"/>
	<html:hidden property="ppn" /><!-- KOC 1346 for PPN Benefit -->
     <INPUT TYPE="hidden" NAME="caption" VALUE='<bean:write name="frmGroupDetail" property="caption"/>'>
    <INPUT TYPE="hidden" NAME="selectedroot" VALUE='<bean:write name="frmGroupDetail" property="selectedroot"/>'>
    <INPUT TYPE="hidden" NAME="selectednode" VALUE='<bean:write name="frmGroupDetail" property="selectednode"/>'>
    <logic:notEmpty name="frmGroupDetail" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
	<logic:notMatch name="frmGroupDetail" property="office" value="Office">
		<script language="javascript">
			onchangeGroup();
		</script>
	</logic:notMatch>

</html:form>