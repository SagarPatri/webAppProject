<%
/** @ (#) addinscompany.jsp 25th Nov 2005
 * Project     : TTK Healthcare Services
 * File        : addinscompany.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 25th Nov 2005
 * @author 		 : 
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,org.apache.struts.action.DynaActionForm" %>

<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/addinscompany.js"></script>
<head>
	
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	
	<link rel="stylesheet" type="text/css" href="css/autoComplete.css" />
	<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
	<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>

      
<SCRIPT>
  $(document).ready(function() {
    $("#insName").autocomplete("auto.jsp?mode=authStandard");
}); 
  function getInsCode(obj)
  {
	  $(document).ready(function() {
	  $("#insName").blur(function(){
	    	var ID	=	obj.value;
	        $.ajax({
	        		url: "/AsynchronousAction.do?mode=getCommonMethod&id="+ID+"&getType=InsCode", 
	        		success: function(result){
	      			var res	=	result.split("@");
	     			 document.forms[1].companyCode.value=res[0];
	        }}); 
	    });
	  });
  }
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
</SCRIPT>

</head>
<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>
<%
	DynaActionForm frmCompanyDetails=(DynaActionForm)request.getSession().getAttribute("frmInsCompanyDetails");
	String strOffType=(String)frmCompanyDetails.get("officeType");

	pageContext.setAttribute("alOfficeInfo",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("alSectorType",Cache.getCacheObject("sectorTypeCode"));
	pageContext.setAttribute("alCityCode",Cache.getCacheObject("cityCode"));
	pageContext.setAttribute("alStateCode",Cache.getCacheObject("stateCode"));
	pageContext.setAttribute("alCountryCode",Cache.getCacheObject("countryCode"));
	pageContext.setAttribute("alFundDisbCode",Cache.getCacheObject("fundDisbursalCode"));
	pageContext.setAttribute("alFrequencyCode",Cache.getCacheObject("frequencyCode"));
	pageContext.setAttribute("alAuthType",Cache.getCacheObject("regAuthority"));
	
	//added for Mail-SMS Template for Cigna
	pageContext.setAttribute("notificinfo",Cache.getCacheObject("notificinfo"));
	pageContext.setAttribute("alUserRestrictionGroup",Cache.getCacheObject("userRestrictionGroup"));//KOC Cigna_insurance_resriction  

	//for decoding the office type to display the label
	HashMap hmOfficeInfo=new HashMap();
    hmOfficeInfo.put("IHO","Head Office");
    hmOfficeInfo.put("IRO","Regional Office");
    hmOfficeInfo.put("IDO","Divisional Office");
    hmOfficeInfo.put("IBO","Branch Office");

    boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
		viewmode=false;
	pageContext.setAttribute("viewmode",new Boolean(viewmode));

%>

<!-- S T A R T : Content/Form Area -->
	<html:form action="/UpdateInsCompanyAction.do" method="post" >

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%">Insurance - <bean:write name="frmInsCompanyDetails" property="caption"/></td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
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

    <!-- S T A R T : Form Fields -->
    
	<fieldset><legend>General</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		<tr>
		    <td class="formLabel">Authority Standard:<span class="mandatorySymbol">*</span></td>
		    <td>
		        <html:select property="authStandard" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>" onchange="onChangeAuthority()">
        				<html:option value="">Select from list</html:option>
        				<html:options collection="alAuthType" property="cacheId" labelProperty="cacheDesc"/>
		  		</html:select>
		    </td>
		    
		    
		    <td class="formLabel">Insurance Category:<span class="mandatorySymbol">*</span></td>
		    
		    <td>
		        <html:select property="insCategory" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
        				<html:option value="">Select from list</html:option>
        				<html:option value="PI">Participating Insurer</html:option>
        				<html:option value="NPI">Non-Participating Insurer</html:option>
		  		</html:select>
		    </td>
		    
		</tr>    
		  <tr>
		    <td class="formLabel">Office Type:</td>
		    <td class="textLabelBold"><%=hmOfficeInfo.get(strOffType)%></td>

			    <td>Branch Location: <span class="mandatorySymbol">*</span> </td>
			    <td>
			    	<html:select property="TTKBranchCode" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
        				<html:option value="">Select from list</html:option>
        				<html:options collection="alOfficeInfo" property="cacheId" labelProperty="cacheDesc"/>
			  		</html:select>
			    </td>

		  </tr>
		  <tr>
		    <td width="22%" class="formLabel">Insurance Name: <span class="mandatorySymbol">*</span></td>
		    <td width="32%">
		    	<logic:match name="frmInsCompanyDetails" property="officeType" value="IHO">
		    		<html:text property="companyName" styleId="insName" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%= viewmode %>" onblur="getInsCode(this)"/>
		    	</logic:match>

		    	<logic:notMatch name="frmInsCompanyDetails" property="officeType" value="IHO">
					<b><bean:write name="frmInsCompanyDetails" property="companyName"/></b>
		    		<html:hidden property="companyName"/>
				</logic:notMatch>
		    </td>
		    <td width="22%" class="formLabel">&nbsp;</td>
		    <td width="24%">
		    	&nbsp;
		    </td>
		  </tr>
		  <tr>
		    <td class="formLabel">Insurance Code:  <span class="mandatorySymbol">*</span></td>
		    <td>
		    	<html:text property="companyCode" styleClass="textBox textBoxMedium" maxlength="30" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%= viewmode %>"/>
		    </td>
		    <td class="formLabel">Insurance Abbreviation: <span class="mandatorySymbol">*</span></td>
		    <td>
		    	<logic:match name="frmInsCompanyDetails" property="officeType" value="IHO">
		    		<html:text property="companyAbbreviation" styleClass="textBox textBoxSmall" maxlength="2" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%= viewmode %>"/>
		    	</logic:match>

		    	<logic:notMatch name="frmInsCompanyDetails" property="officeType" value="IHO">
					<b><bean:write name="frmInsCompanyDetails" property="companyAbbreviation"/></b>
		    		<html:hidden property="companyAbbreviation"/>
				</logic:notMatch>
		    </td>
		 
		  
		  
		  
		   <tr>
		   <td class="formLabel">Insurance Common Authority Code:  <span class="mandatorySymbol">*</span></td>
		    <td>
		    	<html:text property="commonAuthorityCode" styleClass="textBox textBoxMedium" maxlength="30" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%= viewmode %>"/>
		    </td>
		    
		    <td></td>
		     <td></td>
		   <%--  
		      <td  class="formLabel">VAT TRN:
             <logic:equal value="175" property="addressVO.countryCode" name="frmInsCompanyDetails">
              <span id="vatTrnLabel" style="" class="mandatorySymbol">*</span>
              </logic:equal>
               <logic:notEqual   value="175" property="addressVO.countryCode" name="frmInsCompanyDetails">
               <span id="vatTrnLabel" style="display:none" class="mandatorySymbol">*</span>
               </logic:notEqual>
            </td>
            <td><html:text property="vatTrnCode" styleClass="textBox textBoxMedium" maxlength="15" onkeyup="isNumaricOnly(this);" disabled="<%=viewmode%>" />
	      	</td> --%>
		  
		  
		  
		  <tr>
		  	<td class="formLabel">Sector Type: <span class="mandatorySymbol">*</span></td>
		    <td>
			    <logic:match name="frmInsCompanyDetails" property="officeType" value="IHO">
					<html:select property="sectorTypeCode" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
        				<html:option value="">Select from list</html:option>
        				<html:options collection="alSectorType" property="cacheId" labelProperty="cacheDesc"/>
			  		</html:select>
				</logic:match>

				<logic:notMatch name="frmInsCompanyDetails" property="officeType" value="IHO">
					<b><bean:write name="frmInsCompanyDetails" property="sectorTypeDesc"/></b>
		    		<html:hidden property="sectorTypeCode"/>
				</logic:notMatch>
		    </td>
		    <td class="formLabel">Empanelment Date: <span class="mandatorySymbol">*</span></td>
		    <td>
		    	<html:text property="empanelmentDate" styleClass="textBox textBoxSmall" maxlength="10" disabled="<%= viewmode %>"/>
		    	<logic:match name="viewmode" value="false">
				    <A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','forms[1].empanelmentDate',document.forms[1].empanelmentDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
			    </logic:match>
		  	</td>
		  </tr>
		  <tr>
		  	<td class="formLabel">Active:</td>
	    	<td>
				<html:checkbox property="companyStatus" value="Y" disabled="<%= viewmode %>"/>
	    	</td>
	    	<!--KOC Cigna_insurance_resriction  -->
	    	<%-- <td>User Restriction: </td>
			    <td>
			    	<html:select property="userRestrictionGroup" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
        				<html:option value="">Select from list</html:option>
        				<html:options collection="alUserRestrictionGroup" property="cacheId" labelProperty="cacheDesc"/>
			  		</html:select>
			    </td> --%>
			    <!--KOC Cigna_insurance_resriction  -->
			
		     <!--Shortfall CR KOC1179 -->
	    	<td class="formLabel">Email Id:</td>
	    	<td>
	    	<!-- bean:write  name="frmInsCompanyDetails"  property="companyEmailID"/-->
	        <html:text property="companyEmailID" styleClass="textBox textBoxMedium" maxlength="50"/>
        	</td>
        	</tr>
        	
        	
        	
        	
        	
        	
        	 <tr>
        	 
        	 	<logic:match name="frmInsCompanyDetails" property="officeType" value="IHO">
        	  <td  class="formLabel">VAT(%): <span class="mandatorySymbol">*</span></td>
        	   <td>
        	   
        	  <logic:equal name="frmInsCompanyDetails" property="authority" value="HAAD">
        	    <html:text property="vatPercent" styleClass="textBox textBoxSmall" maxlength="5" onkeyup="isNumaricOnly(this);" />
        	   </logic:equal>
        	   <logic:notEqual name="frmInsCompanyDetails" property="authority" value="HAAD">
        	    <html:text property="vatPercent" styleClass="textBox textBoxSmall" maxlength="5" onkeyup="isNumaricOnly(this);" readonly="true" disabled="true"/>
        	   </logic:notEqual>
	      	
	      <%-- 	 <logic:notEmpty name="frmInsCompanyDetails" property="vatPercent"> --%>
	      	<br>
						
						<a href="#" onClick="javascript:onSelectProduct(1450,1500)"><b>Select the products to be update</b> </a>
	      	
	     <%--  	</logic:notEmpty> --%>
	      	
	      	</td>
        	</logic:match>
        	
        	
        	
        	  <td  class="formLabel">VAT TRN:
             <logic:equal value="123" property="addressVO.countryCode" name="frmInsCompanyDetails">
              <span id="vatTrnLabel" style="" class="mandatorySymbol">*</span>
              </logic:equal>
               <logic:notEqual   value="123" property="addressVO.countryCode" name="frmInsCompanyDetails">
               <span id="vatTrnLabel" style="display:none" class="mandatorySymbol">*</span>
               </logic:notEqual>
            </td>
            <td><html:text property="vatTrnCode" styleClass="textBox textBoxMedium" disabled="<%=viewmode%>" /><!--  maxlength="15" onkeyup="isNumaricOnly(this);" By Vikram cr-047-->
	      	</td>
		  
        	</tr>
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	<!-- End Shortfall CR KOC1179 -->
		</table>
	</fieldset>
	<fieldset>
	<legend>Address Details</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="22%" class="formLabel">Address 1: <span class="mandatorySymbol">*</span></td>
	        <td width="32%">
	        	<html:text property="addressVO.address1" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%= viewmode %>"/>
	        </td>
	        <td width="22%" class="formLabel">Address 2: </td>
	        <td width="24%">
	        	<html:text property="addressVO.address2" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%= viewmode %>"/>
	        </td>
	      </tr>
	      <tr>
	        <td class="formLabel">Address 3: </td>
	        <td>
	        	<html:text property="addressVO.address3" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%= viewmode %>"/>
	        </td>
	        <td class="formLabel">Governorate: <span class="mandatorySymbol">*</span></td>
	        <td>
	       		<html:select property="addressVO.stateCode" styleId="state" styleClass="selectBox selectBoxMedium" onchange="onChangeState(this)" disabled="<%= viewmode %>">
    				<html:option value="">Select from list</html:option>
    				<html:options collection="alStateCode" property="cacheId" labelProperty="cacheDesc"/>
		  		</html:select>
	        </td></tr>
	      <tr>
			<td class="formLabel">Area: <span class="mandatorySymbol">*</span></td>
	        <td>
		        <html:select property="addressVO.cityCode" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
    				<html:option value="">Select from list</html:option>
    				<html:optionsCollection property="alCityList" label="cacheDesc" value="cacheId"/>
		  		</html:select>
	        </td>
	        <td class="formLabel">PO Box: <span class="mandatorySymbol">*</span></td>
	        <td>
	        	<html:text property="addressVO.pinCode" styleClass="textBox textBoxSmall" maxlength="10" disabled="<%= viewmode %>"/>
	        </td>
	      </tr>
	      <tr>
	        <td class="formLabel">Country: <span class="mandatorySymbol">*</span></td>
	        <td colspan="3">
		      <html:select property="addressVO.countryCode" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>" onchange="onchangeVatTrn()">
		      	<html:option value="">Select from list</html:option>
        		<html:options collection="alCountryCode" property="cacheId" labelProperty="cacheDesc"/>
			  </html:select>
		   </td>
	      </tr>
	      
	      <tr>
        			<td class="formLabel">Phone No1 :<span class="mandatorySymbol">*</span></td>
        			<td>
    				<html:text property="addressVO.isdCode" styleClass="disabledfieldType" size="3" maxlength="3" readonly="true"/>&nbsp;
    				<html:text property="addressVO.stdCode" styleClass="disabledfieldType" size="3" maxlength="3" readonly="true"/>&nbsp;
    				
    			
	        		<logic:empty name="frmInsCompanyDetails" property="addressVO.phoneNo1">
	    			<html:text property="addressVO.phoneNo1" styleId="phoneNbr1" styleClass="disabledfieldType" maxlength="15" value="Phone No1" onclick="changeMe(this)" onblur="getMe('Phone No1')"/>
    				</logic:empty>
    				
    				<logic:notEmpty name="frmInsCompanyDetails" property="addressVO.phoneNo1">
	        			<html:text property="addressVO.phoneNo1" styleId="phoneNbr1" styleClass="disabledfieldType" maxlength="15" onclick="changeMe(this)" onblur="getMe('Phone No1')"/> 
	        		</logic:notEmpty>
	        		</td>
    				<td class="formLabel">Phone No2 :</td>
        			<td>
    				<html:text property="addressVO.isdCode" styleClass="disabledfieldType" size="3" maxlength="3" readonly="true"/>&nbsp;
    				<html:text property="addressVO.stdCode" styleClass="disabledfieldType" size="3" maxlength="3" readonly="true"/>&nbsp;
    				
    				
					<logic:empty name="frmInsCompanyDetails" property="addressVO.phoneNo2">
	    			<html:text property="addressVO.phoneNo2" styleId="phoneNbr2" styleClass="disabledfieldType" maxlength="15" value="Phone No2" onclick="changeMe(this)" onblur="getMe('Phone No2')"/>
    				</logic:empty>
    				
    				<logic:notEmpty name="frmInsCompanyDetails" property="addressVO.phoneNo2">
	        			<html:text property="addressVO.phoneNo2" styleId="phoneNbr2" styleClass="disabledfieldType"  maxlength="15" onclick="changeMe(this)" onblur="getMe('Phone No2')"/> 
	        		</logic:notEmpty>    	
	        		</td>		
      			</tr>
	    </table>
	</fieldset>
	<!-- added for CR Email-SMS Notification for Cigna -->
	<fieldset>
		<legend>General Notification Information</legend>
		    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			    <tr>
				    <td width="17%" class="formLabel">To Email Id:</td>
				    <td width="37%">
				    	<html:text property="mailnotificationVO.notiEmailId" styleClass="textBox textBoxMedium"  maxlength="250"/>
				    </td>
				    <td width="17%" class="formLabel">Cc Email Id:</td>
				    <td width="29%">
				    	<html:text property="mailnotificationVO.ccEmailId" styleClass="textBox textBoxMedium"  maxlength="250"/>
				    </td>					
			    </tr>
			    <tr>
			    	<td class="formLabel">Notification Type:</td>
				    <td>
				    	<html:select property="mailnotificationVO.notiTypeId" styleClass="selectBox selectBoxMedium" >
				    		<html:option value="">--Select from List--</html:option>
							<html:optionsCollection name="notificinfo" label="cacheDesc" value="cacheId"/>
						</html:select>
				    </td>
											
			    </tr>
		    </table>
		</fieldset>
		<fieldset>
		<legend>Policy Renewal Condition</legend>
		    
				    <div style="margin-left: 10px;">
				        Allowed Grace Period for Policy Renewal
				    	<html:text property="policygracePeriod" styleClass="textBox textBoxTiny"  maxlength="250"/> days (Efective from date :<bean:write  name="frmInsCompanyDetails" property="effectivefromdateforpolicygracePeriod"/>)
				    </div>					
				    	<div style="margin-left: 10px;">
				    	Allowed gap(in number of days) between the "Awating Renewal" policy Creation date in VINGS System and the "Policy End Date" of the policy to be renewed
				    	<html:text property="policygapPeriod" styleClass="textBox textBoxTiny"  maxlength="250"/> days (Efective from date :<bean:write name="frmInsCompanyDetails" property="effectivefromdateforpolicygapPeriod"/>)
				        </div>							
		</fieldset>
	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    	<logic:match name="viewmode" value="false">
    			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
				<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
    		</logic:match>
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    </td>
	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons -->
	<html:hidden property="officeType"/>
	<html:hidden property="insuranceSeqID"/>
	<input type="hidden" name="child" value="Add/Edit Insurance Company">
	<input type="hidden" name="mode">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="companyStatus" value="">
	<input type="hidden" name="focusID" value="">
	<input type="hidden" name="reforward" id="reforward" />
	<input type="hidden" name="authority" value="">
	
	<logic:notEmpty name="frmInsCompanyDetails" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
</html:form>
<!-- E N D : Content/Form Area -->