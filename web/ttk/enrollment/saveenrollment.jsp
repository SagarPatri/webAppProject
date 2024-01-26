<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList"%>

<html>
<head>
<script type="text/javascript" src="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/enrollment/saveenrollment.js"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/jquery/ttk-jquery.js"></script>
<script type="text/javascript" src="/ttk/scripts/async.js"></script>
<script type="text/javascript">
</script>
<style>
.disabledfieldType{
       font-size: 11px;
       color: #A9A9A9;
       border-left: 1px solid black;
       border-right: 1px solid black;
       border-top:1px solid black; 
       border-bottom: 1px solid black;
}
.disabledfieldType2{
       font-size: 11px;
       color:black;
       border-left: 1px solid black;
       border-right: 1px solid black;
       border-top:1px solid black; 
       border-bottom: 1px solid black;
}
</style>
</head>
<body>

<%
  ArrayList alLocation=(ArrayList)session.getAttribute("alLocation");
  pageContext.setAttribute("listStateCode",Cache.getCacheObject("stateCode"));
  pageContext.setAttribute("listCityCode",Cache.getCacheObject("cityCode"));
  pageContext.setAttribute("listRelationshipCode",Cache.getCacheObject("relationshipCode"));
  pageContext.setAttribute("empStatus",Cache.getCacheObject("empStatus"));
  pageContext.setAttribute("countryCode", Cache.getCacheObject("countryCode"));
  pageContext.setAttribute("designations", Cache.getCacheObject("designationTypeID"));
  pageContext.setAttribute("residentailLocation", Cache.getCacheObject("residentailLocation"));
  pageContext.setAttribute("workLocation", Cache.getCacheObject("workLocation"));
  boolean viewmode=true;
  boolean readmode=false;
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
    readmode=true;
  }//end of if(TTKCommon.isAuthorized(request,"Edit"))

%>
<!-- S T A R T : Content/Form Area -->

<html:form action="/EnrollmentAction.do">

<logic:notEmpty name="SelectedPolicy">
    <logic:equal name="SelectedPolicy" property="memberAdditionPrivelegeYN" value="Y">
      <%viewmode=false; %>
    </logic:equal>
</logic:notEmpty>

<!-- S T A R T : Page Title -->
  <%
    if((TTKCommon.getActiveLink(request)).equals("Online Forms"))
    {
   %>
      <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <%
   }
   else
   {
   %>
      <logic:match name="frmPolicyList" property="switchType" value="ENM">
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      </logic:match>
      <logic:match name="frmPolicyList" property="switchType" value="END">
        <table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
       </logic:match>
   <%
   }
   %>
    <tr>
         <td width="100%"><bean:write name="frmEnrollment" property="caption" /> </td>

     <logic:empty name="SelectedPolicy">
     <td align="right"><a href="#">
        <img src="ttk/images/DocViewIcon.gif" alt="Launch Document Viewer" width="16" height="16" border="0" align="absmiddle" class="icons"></a>&nbsp;&nbsp;&nbsp;</td>
      </logic:empty>
     </tr>
  </table>
  <!-- E N D : Page Title -->
  <!-- Added Rule Engine Enhancement code : 27/02/2008 -->
	<ttk:BusinessErrors name="BUSINESS_ERRORS" scope="request" />
	<!--  End of Addition -->
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

<logic:notMatch name="frmEnrollment" property="corporate" value="noncorporate">
  <fieldset>
  <legend>Employee Information</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="21%" nowrap class="formLabel">Employee No.: <span class="mandatorySymbol">*</span></td>
        <td width="35%" nowrap>
          <html:text property="employeeNbr" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/>
        </td>
        <td class="formLabel" width="19%">Principal Name: <span class="mandatorySymbol">*</span></td>
        <td width="25%">
          <html:text property="name" styleClass="textBox textBoxMedium" style="width:190px;" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%=viewmode%>"/>
          <input type="hidden" name="employeename" id="memname" value="">
        </td>
      </tr>
      <%-- <tr>
        <td width="21%" nowrap class="formLabel">Second Name.:</td>
        <td width="35%" nowrap>
          <html:text property="employeeSecondName" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/>
        </td>
        <td class="formLabel" width="19%">Family Name: <span class="mandatorySymbol">*</span></td>
        <td width="25%">
          <html:text property="employeeFamilyName" styleClass="textBox textBoxMedium" style="width:190px;" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%=viewmode%>"/>
        </td>
      </tr> --%>
      <tr>
        <td class="formLabel">Department:</td>
        <td>
          <html:text property="department" styleClass="textBox textBoxMedium" maxlength="120" disabled="<%=viewmode%>"/>
        </td>
        <td class="formLabel">Occupation:</td>
        <td>
       <%--    <html:select property="designation" styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
           <html:option value="">SELECT FROM LIST</html:option>
            <html:optionsCollection name="designations" label="cacheDesc" value="cacheId" />
          </html:select>--%>
          
			 <%--   <html:text property="occupationCode" styleId="enrollId" styleClass="disabledfieldType" disabled="true" readonly="true" />--%>

			    <html:text property="occuCodeDesc" name="frmPolicyDetails" styleClass="disabledfieldType" readonly="true" />
	        	<a href="#" onClick="javascript:onSelectCodes();"><img src="/ttk/images/EditIcon.gif" alt="Select Account Manager" title="Select Occupation codes" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;
        </td>
      </tr>
      <tr>
        <td class="formLabel">Date of Joining: </td>
        <td>
          <html:text property="startDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>"/>
        <%
          if(viewmode==false)
      {
        %>
            <A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmEnrollment.startDate',document.frmEnrollment.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="joinDate" width="24" height="17" border="0" align="absmiddle"></a>
    <%
      }//end of if(TTKCommon.isAuthorized(request,"Edit"))
    %>
        </td>
        <td class="formLabel">Date of Resignation: </td>
        <td>
          <html:text property="endDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>"/>
        <%
          if(viewmode==false)
      {
        %>
            <A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmEnrollment.endDate',document.frmEnrollment.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="joinDate" width="24" height="17" border="0" align="absmiddle"></a>
      <%
        }//end of if(TTKCommon.isAuthorized(request,"Edit"))
      %>
      </tr>
      <tr>
      	<td class="formLabel">Certificate No.:</td>
      	<td>
          <html:text property="certificateNbr" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/>
        </td>
        <td class="formLabel">CreditCard No.:</td>
      	<td>
          <html:text property="creditCardNbr" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/>
        </td>
      </tr>
      <tr>
        <td class="formLabel">Location:</td>
        <td>
        <html:select property="groupRegnSeqID"  styleClass="selectBox selectBoxMedium" style="width:220px" disabled="<%=viewmode%>">
            <html:options collection="alLocation"  property="cacheId" labelProperty="cacheDesc"/>
    	</html:select>
        </td>
        <td class="formLabel">Status:</td>
        <logic:match name="frmPolicyList" property="switchType" value="END">
        <%
        if(request.getSession().getAttribute("memberstatusdata")!=null){
        if(!(request.getSession().getAttribute("memberstatusdata").equals("POA") || request.getSession().getAttribute("memberstatusdata").equals("POC"))){ %>
	        <td colspan="3" class="formLabelBold">
	          	<bean:write name="frmEnrollment" property="empStatusDesc" />
	      	</td>
	     <%}else{%>
	        <td colspan="3">
	          	<html:select property="empStatusTypeID" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
	          		<html:optionsCollection name="empStatus" label="cacheDesc" value="cacheId"/>
	          	</html:select>
	      	</td>
	      	<%}}else{ %>
	      	   <td colspan="3" class="formLabelBold">
	          	<bean:write name="frmEnrollment" property="empStatusDesc" />
	      	</td>
	      	<%} %>
      	</logic:match>
      	<logic:match name="frmPolicyList" property="switchType" value="ENM">
        <%
        if(request.getSession().getAttribute("memberstatusdata")!=null){
        if(!(request.getSession().getAttribute("memberstatusdata").equals("POA") || request.getSession().getAttribute("memberstatusdata").equals("POC"))){ %>
	        <td colspan="3" class="formLabelBold">
	          	<bean:write name="frmEnrollment" property="empStatusDesc" />
	      	</td>
	    <%}else{ %>
      	<%-- <logic:notMatch name="frmPolicyList" property="switchType" value="ENM">
      	<logic:notMatch name="frmEnrollment" value="POE"> --%>
	        <td colspan="3">
	          	<html:select property="empStatusTypeID" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">

	          		<html:optionsCollection name="empStatus" label="cacheDesc" value="cacheId"/>
	          	</html:select>
	      	</td>
	      	<%}}else{ %>
	      	 <td colspan="3" class="formLabelBold">
	          	<bean:write name="frmEnrollment" property="empStatusDesc" />
	      	</td>
	      	<%} %>
	     </logic:match>
      </tr>
      <tr>
      <td class="formLabel">Family No.:</td>
      	<td>
          <html:text property="familyNbr" styleClass="textBox textBoxMedium" maxlength="15" disabled="<%=viewmode%>"/>
        </td>
  
  <td class="formLabel">Salary Band:<span class="mandatorySymbol">*</span></td>
    <td class="formLabelBold">
    <logic:equal property="productAuthority" name="frmPolicyDetails" value="CMA" scope="session">
    	 <html:select  property="salaryBand"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
  	 		<html:option value="">Select from list</html:option>
  	 		  <html:option value="GTFLTT">Greater Than 4000 Less Than 12000</html:option>
  	 		  <html:option value="LEF">Less Than Or Equal 4000</html:option>
  	 		  <html:option value="GTET">Greater Than Or Equal 12000</html:option>
  	 		  <html:option value="NA">Not Applicable</html:option>
		</html:select>
		</logic:equal>
    <logic:equal property="productAuthority" name="frmPolicyDetails" value="DHA" scope="session">
    	 <html:select  property="salaryBand"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
  	 		<html:option value="">Select from list</html:option>
  	 		  <html:option value="GTFLTT">Greater Than 4000 Less Than 12000</html:option>
  	 		  <html:option value="LEF">Less Than Or Equal 4000</html:option>
  	 		  <html:option value="GTET">Greater Than Or Equal 12000</html:option>
  	 		  <html:option value="NA">Not Applicable</html:option>
		</html:select>
		</logic:equal>
		    <logic:equal property="productAuthority" name="frmPolicyDetails" value="MOH" scope="session">
    	  <html:select  property="salaryBand"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
  	 		<html:option value="">Select from list</html:option>
  	 		  <html:option value="GTFLTT">Greater Than 4000 Less Than 12000</html:option>
  	 		  <html:option value="LEF">Less Than Or Equal 4000</html:option>
  	 		  <html:option value="GTET">Greater Than Or Equal 12000</html:option>
  	 		  <html:option value="NA">Not Applicable</html:option>
		</html:select>
		</logic:equal>
		<logic:equal property="productAuthority" name="frmPolicyDetails" value="HAAD" scope="session">
    	  <html:select  property="salaryBand"  styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>">
  	 		<html:option value="">Select from list</html:option>
  	 		  <html:option value="GTFI">Greater Than 5000</html:option>
  	 		  <html:option value="LTEFI">Less Than or Equal 5000</html:option>
  	 		  <html:option value="NA">Not Applicable</html:option>
		</html:select>
		</logic:equal>
    </td>
      </tr>
      <tr>
      <td class="formLabel">Contract No:<span class="mandatorySymbol">*</span></td>
      <td><html:text property="contractNo" styleClass="textBox textBoxMedium" style="width:190px;" maxlength="60"/></td>
      <td class="formLabel">Commission Based:</td>
    <td class="formLabelBold">
    	 <html:select  property="commissionBased"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
  	 		  <html:option value="">Select from list</html:option>
  	 		  <html:option value="N">NO</html:option>
  	 		  <html:option value="Y">YES</html:option>  	 		  
		</html:select>
    </td>
    </tr>
    
    
    
     <logic:match name="frmPolicyList" property="switchType" value="END">
    <logic:notEmpty property="memberCancelRemarks" name="frmEnrollment">
    <tr>
   <td class="formLabel">Employee Cancellation Remarks:</td>
      	<td>
        <html:textarea property="memberCancelRemarks" styleClass="textBox textAreaLong" readonly="true"  disabled="<%=viewmode%>"/>
        </td>
    </tr>
    </logic:notEmpty>
    </logic:match>
 
     
    
    
    
    
    
    
    
    </table>
  </fieldset>
</logic:notMatch>


<logic:match name="frmEnrollment" property="corporate" value="noncorporate">
    <fieldset>
    <legend>Member Information</legend>
      <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td width="21%" nowrap class="formLabel">Customer No.: </td>
           <td width="35%" nowrap>
            <html:text property="customerNbr" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/>
           </td>
           <td width="19%" nowrap class="formLabel">Card Holders Name: <span class="mandatorySymbol">*</span></td>
	        <td width="25%">
	          <html:text property="name" styleClass="textBox textBoxMedium" style="width:190px;" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%=viewmode%>"/>
	            <input type="hidden" name="cardholdername" id="memname" value="">
	       </td>

        </tr>
      <tr>
              <td class="formLabel" nowrap>Certificate No.: <span class="mandatorySymbol">*</span></td>
              <td >
                <html:text property="certificateNbr" styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%=viewmode%>"/>
              </td>
              <td class="formLabel" nowrap>Credit Card No.: </td>
              <td >
                <html:text property="creditCardNbr" styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%=viewmode%>"/>
               </td>
            </tr>
            <tr>
              <td  nowrap class="formLabel">Order No.:<span class="mandatorySymbol">*</td>
                <td nowrap><span class="formLabel">
                  <html:text property="orderNbr" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/>
                 </span></td>
              <td nowrap class="formLabel">Prev. Order No.:</td>
                <td nowrap><span class="formLabel">
                  <html:text property="prevOrderNbr" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/>
                 </span></td>
            </tr>
            <tr>
            <td nowrap class="formLabel">Status:</td>
		<logic:match name="frmPolicyList" property="switchType" value="ENM">
			  <td colspan="3" class="formLabelBold">
				<bean:write name="frmEnrollment" property="empStatusDesc" />
			</td>
		</logic:match>
		<logic:notMatch name="frmPolicyList" property="switchType" value="ENM">
			<td colspan="3">
				<html:select property="empStatusTypeID" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
					<html:optionsCollection name="empStatus" label="cacheDesc" value="cacheId"/>
				</html:select>
			</td>
		</logic:notMatch>
      </tr>
      </table>
      </fieldset>
</logic:match>

  <fieldset>
  <legend>Policy Information</legend>
  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
          <td width="21%" class="formLabel" nowrap>Proposal Form:</td>
      <td width="35%"><span class="textLabel">
        <html:checkbox property="proposalFormYN" value="Y" disabled="<%=viewmode%>"/>
            </span>
          </td>

        <td width="19%" class="formLabel" nowrap>Proposal Date:</td>
          <td width="25%">
            <html:text property="declarationDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>"/>
          <%
          if(viewmode==false)
      {
         %>
            <A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmEnrollment.declarationDate',document.frmEnrollment.declarationDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="joinDate" width="24" height="17" border="0" align="absmiddle"></a>
       <%
         }//end of if(TTKCommon.isAuthorized(request,"Edit"))
       %>
          </td>
      </tr>
  </table>
  </fieldset>

  <fieldset>
  <legend>Address Information</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="21%" class="formLabel">Address 1:</td>
          <td width="35%">
            <html:text property="memberAddressVO.address1" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>" />
          </td>
          <td width="19%" class="formLabel">Address 2:</td>
          <td width="25%">
            <html:text property="memberAddressVO.address2" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>"/>
          </td>
        </tr>
        <tr>
          <td class="formLabel">Address 3:</td>
          <td>
            <html:text property="memberAddressVO.address3" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>" />
          </td>
          
          
         <td class="formLabel">Country:<span class="mandatorySymbol">*</span></td>
         <td>        	
			<html:select property="memberAddressVO.countryCode" styleId="cnCode"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="getStates('state','stateCode')">
			      <html:option value="">Select from list</html:option>
			      <html:optionsCollection name="countryCode" label="cacheDesc" value="cacheId" />
			</html:select>
        </td>
           </tr>
        <tr>
        <td class="formLabel">Governorate/State:<span class="mandatorySymbol">*</span></td>
        <td>
			
			<html:select property="memberAddressVO.stateCode" styleId="stateCode"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="getStates('city','cityCode')">
			      <html:option value="">Select from list</html:option>
			      <logic:notEmpty name="providerStates" scope="session">
			       <html:optionsCollection name="providerStates" label="value" value="key"  />
			      </logic:notEmpty>			     
			</html:select>
        </td>
      <td class="formLabel">Area:</td>
          <td>          	
          
                
          <html:select property="memberAddressVO.cityCode" styleId="cityCode"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
			      <html:option value="">Select from list</html:option>
			      <logic:notEmpty name="providerAreas" scope="session">
			       <html:optionsCollection name="providerAreas" label="value" value="key"  />
			      </logic:notEmpty>
			</html:select>
			
        </td>
         
        </tr>
        <tr>
           <td class="formLabel">PO Box:</td>
          <td>
            <html:text property="memberAddressVO.pinCode" styleClass="textBox textBoxSmall"  maxlength="10" disabled="<%=viewmode%>"/>
          </td>      
           <%-- <td class="formLabel">District:</td> 
           <td>
	           <html:select property="districtV" styleId="stateCode"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="getStates('city','cityCode')">
				      <html:option value="">Select from list</html:option>
				      <html:optionsCollection name="workLocation" label="cacheDesc" value="cacheId" />		     
				</html:select>
		   </td> --%>
        </tr>
        <tr>
          <td>Email Id1:<span class="mandatorySymbol">*</span></td>
          <td>
            <html:text property="memberAddressVO.emailID" styleClass="textBox textBoxMedium"  maxlength="250" disabled="<%=viewmode%>"/>
         </td>
		  <td>Email Id2:</td>
		    <td>
			   <html:text property="memberAddressVO.emailID2" styleClass="textBox textBoxMedium" maxlength="50" disabled="<%=viewmode%>"/>
		     </td>
		 </tr>
        <tr>
          <td class="formLabel">Office Phone 1:</td>
          <td>
          <logic:empty name="frmEnrollment" property="memberAddressVO.off1IsdCode">
           <html:text name="frmEnrollment" property="memberAddressVO.off1IsdCode" styleId="isdCode1" styleClass="disabledfieldType" size="3" maxlength="3" value="ISD"  onfocus="if (this.value=='ISD') this.value = ''" onblur="if (this.value==''){ this.value = 'ISD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>
          </logic:empty>
          <logic:notEmpty name="frmEnrollment" property="memberAddressVO.off1IsdCode">
             <html:text name="frmEnrollment" property="memberAddressVO.off1IsdCode" styleId="isdCode1" styleClass="disabledfieldType" size="3" maxlength="3"/>
          </logic:notEmpty>
          <logic:empty name="frmEnrollment" property="memberAddressVO.off1StdCode">
            <html:text name="frmEnrollment" property="memberAddressVO.off1StdCode" styleId="stdCode1" styleClass="disabledfieldType" size="3" maxlength="3" value="STD" onfocus="if (this.value=='STD') this.value = ''" onblur="if (this.value==''){ this.value = 'STD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>			
            </logic:empty>
             <logic:notEmpty name="frmEnrollment" property="memberAddressVO.off1StdCode">
            <html:text name="frmEnrollment" property="memberAddressVO.off1StdCode" styleId="stdCode1" styleClass="disabledfieldType" size="3" maxlength="3"/>			
            </logic:notEmpty>
            <html:text property="memberAddressVO.phoneNbr1" styleClass="textBox textBoxMedium"  maxlength="25" disabled="<%=viewmode%>"/>        
          </td>
          <td class="formLabel">Office Phone 2:</td>
          <td>
          <logic:empty name="frmEnrollment" property="memberAddressVO.off2IsdCode">
           <html:text name="frmEnrollment" property="memberAddressVO.off2IsdCode" styleId="isdCode2" styleClass="disabledfieldType" size="3" maxlength="3" value="ISD"  onfocus="if (this.value=='ISD') this.value = ''" onblur="if (this.value==''){ this.value = 'ISD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>
          </logic:empty>
          <logic:notEmpty name="frmEnrollment" property="memberAddressVO.off2IsdCode">
           <html:text name="frmEnrollment" property="memberAddressVO.off2IsdCode" styleId="isdCode2" styleClass="disabledfieldType" size="3" maxlength="3"/>
          </logic:notEmpty>
          <logic:empty name="frmEnrollment" property="memberAddressVO.off2StdCode">
            <html:text name="frmEnrollment" property="memberAddressVO.off2StdCode" styleId="stdCode2" styleClass="disabledfieldType" size="3" maxlength="3" value="STD" onfocus="if (this.value=='STD') this.value = ''" onblur="if (this.value==''){ this.value = 'STD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>			
            </logic:empty>
             <logic:notEmpty name="frmEnrollment" property="memberAddressVO.off2StdCode">
            <html:text name="frmEnrollment" property="memberAddressVO.off2StdCode" styleId="stdCode2" styleClass="disabledfieldType" size="3" maxlength="3"/>			
            </logic:notEmpty>
          <html:text property="memberAddressVO.phoneNbr2" styleClass="textBox textBoxMedium"  maxlength="25" disabled="<%=viewmode%>"/>
          </td>
        </tr>
        <tr>
          <td class="formLabel">Home Phone:</td>
          <td>
          <logic:empty name="frmEnrollment" property="memberAddressVO.homeIsdCode">
           <html:text name="frmEnrollment" property="memberAddressVO.homeIsdCode" styleId="isdCode3" styleClass="disabledfieldType" size="3" maxlength="3" value="ISD"  onfocus="if (this.value=='ISD') this.value = ''" onblur="if (this.value==''){ this.value = 'ISD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>
          </logic:empty>
          <logic:notEmpty name="frmEnrollment" property="memberAddressVO.homeIsdCode">
           <html:text name="frmEnrollment" property="memberAddressVO.homeIsdCode" styleId="isdCode3" styleClass="disabledfieldType" size="3" maxlength="3"/>
          </logic:notEmpty>
          <logic:empty name="frmEnrollment" property="memberAddressVO.homeStdCode">
            <html:text name="frmEnrollment" property="memberAddressVO.homeStdCode" styleId="stdCode3" styleClass="disabledfieldType" size="3" maxlength="3" value="STD" onfocus="if (this.value=='STD') this.value = ''" onblur="if (this.value==''){ this.value = 'STD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>			
            </logic:empty>
             <logic:notEmpty name="frmEnrollment" property="memberAddressVO.homeStdCode">
            <html:text name="frmEnrollment" property="memberAddressVO.homeStdCode" styleId="stdCode3" styleClass="disabledfieldType" size="3" maxlength="3"/>			
            </logic:notEmpty>
          <html:text property="memberAddressVO.homePhoneNbr" styleClass="textBox textBoxMedium"  maxlength="25" disabled="<%=viewmode%>"/>
          </td>
          <td class="formLabel">Contact Number:<span class="mandatorySymbol">*</span></td>
          <td>
         <%--  <logic:empty name="frmEnrollment" property="memberAddressVO.mobileIsdCode">
           <html:text name="frmEnrollment" property="memberAddressVO.mobileIsdCode" styleId="isdCode4" styleClass="disabledfieldType" size="3" maxlength="3" value=""  onfocus="if (this.value=='ISD') this.value = ''" onblur="if (this.value==''){ this.value = 'ISD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>
          </logic:empty>
          <logic:notEmpty name="frmEnrollment" property="memberAddressVO.mobileIsdCode">
           <html:text name="frmEnrollment" property="memberAddressVO.mobileIsdCode" styleId="isdCode4" styleClass="disabledfieldType" size="3" maxlength="3"/>
          </logic:notEmpty> --%>
          
          
           <html:text name="frmEnrollment" property="memberAddressVO.mobileIsdCode" styleId="isdCode4" styleClass="disabledfieldType" size="3" maxlength="3"/>
          <html:text property="memberAddressVO.mobileNbr" styleClass="textBox textBoxMedium"  maxlength="15" disabled="<%=viewmode%>"/></td>
        </tr>
        <tr>
          <td>Fax:</td>
          <td><html:text property="memberAddressVO.faxNbr" styleClass="textBox textBoxMedium"  maxlength="15" disabled="<%=viewmode%>"/></td>
          <td class="formLabel">&nbsp;</td>
          <td class="formLabel">&nbsp;</td>
        </tr>
        <tr>
        <%-- <td>Residential Location:<span class="mandatorySymbol">*</span></td>
        <td>
	        <html:text property="memberAddressVO.residentialLocation" styleClass="textBox textBoxMedium" maxlength="50" disabled="<%=viewmode%>"/>
        </td> --%>
        
        <td class="formLabel">Residential Location:<span class="mandatorySymbol">*</span></td>
        <td>
			 <logic:equal property="productAuthority" name="frmPolicyDetails" value="DHA" >
			<html:select property="memberAddressVO.residentialLocation" styleId="stateCode"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="getStates('city','cityCode')">
			      <html:option value="">Select from list</html:option>
			      <html:optionsCollection name="residentailLocation" label="cacheDesc" value="cacheId" />		     
			</html:select>
			</logic:equal>
				<logic:notEqual property="productAuthority" name="frmPolicyDetails" value="DHA">
			<html:text property="memberAddressVO.residentialLocation" styleId="stateCode" styleClass="textBox textBoxMedium" maxlength="250" />
			</logic:notEqual>
        </td>
        
		<td>Work Location:<span class="mandatorySymbol">*</span></td>
		<td>
	     <logic:equal property="productAuthority" name="frmPolicyDetails" value="DHA" >
			<html:select property="memberAddressVO.workLocation" styleId="stateCode"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="getStates('city','cityCode')">
			      <html:option value="">Select from list</html:option>
			      <html:optionsCollection name="workLocation" label="cacheDesc" value="cacheId" />		     
			</html:select>
			</logic:equal>
			<logic:notEqual property="productAuthority" name="frmPolicyDetails" value="DHA">
			<html:text property="memberAddressVO.workLocation" styleId="stateCode" styleClass="textBox textBoxMedium" maxlength="250" />
			</logic:notEqual>
		 </td>
		 </tr>
      </table>
  </fieldset>

  <fieldset>
    <legend>Bank Account Information</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="21%" class="formLabel" nowrap>Bank Name:</td>
        <td width="35%" class="formLabelBold" nowrap>
          <html:text property="bankName" styleClass="textBox textBoxLarge" maxlength="250" disabled="<%=viewmode%>" readonly="<%=readmode%>"/>
        </td>
        <td width="19%" class="formLabel" nowrap>Branch:</td>
        <td width="25%" class="formLabelBold" nowrap>
          <html:text property="branch" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>" readonly="<%=readmode%>"/>
        </td>
      </tr>
      <tr>
        <td class="formLabel">Bank Account No.:</td>
        <td>
          <html:password property="bankAccNbr" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>" readonly="<%=readmode%>"/>
        </td>
        <td class="formLabel">IFSC Code: </td>
        <td>
          <html:text property="MICRCode" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>" readonly="<%=readmode%>"/>
        </td>
      </tr>
      <tr>
        <td class="formLabel">Bank Phone:</td>
        <td>
          <html:text property="bankPhone" styleClass="textBox textBoxMedium" maxlength="25" disabled="<%=viewmode%>" readonly="<%=readmode%>"/>
        </td>
        <td class="formLabel">&nbsp;</td>
        <td class="formLabel">&nbsp;</td>
      </tr>
    </table>
  </fieldset>
  <fieldset>
  	<legend>Personal Information</legend>
			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		      	<tr>
			        <td width="21%" class="formLabel" nowrap>Date of Marriage:</td>
			        <td width="35%" class="formLabelBold" nowrap>
						<html:text property="dateOfMarriage" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" />
	        			<%
	          				if(viewmode==false)
	      					{
	        			%>
	            				<A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmEnrollment.dateOfMarriage',document.frmEnrollment.dateOfMarriage.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="joinDate" width="24" height="17" border="0" align="absmiddle"></a>
	            		<%
							}//end of if(viewmode==false)
						%>
			        </td>
			        <td width="19%" class="formLabel" nowrap>&nbsp;</td>
			        <td width="25%" class="formLabelBold" nowrap>&nbsp;</td>
		      	</tr>
	    	</table>
  </fieldset>
  <fieldset>
	<legend>General</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
		 	<td width="21%" class="formLabel">Stop Cashless/Claims:</td>
		    <td class="formLabel" width="35%">
			 	<html:checkbox property="stopPatClmYN" value="Y" disabled="<%=viewmode%>"/>
		    </td>
		    <td class="formLabel" width="19%">Received After:</td>
		    <td class="formLabel" width="25%">
		 	   <html:text property="receivedAfter" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>"/>
				<%
          		if(viewmode==false)
      			{
         		%>
		 	   		<a name="CalendarObjectReceivedAfter" id="CalendarObjectReceivedAfter" href="#" onClick="javascript:show_calendar('CalendarObjectReceivedAfter','frmEnrollment.receivedAfter',document.frmEnrollment.receivedAfter.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="receivedAfter" width="24" height="17" border="0" align="absmiddle"></a>
		 	   	 <%
         		}//end of if(viewmode==false)
      			 %>
		 	</td>
 		 </tr>
	</table>
  </fieldset>
  <fieldset>
  <legend>Beneficiary Information</legend>
  <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="21%" class="formLabel">Beneficiary Name: </td>
        <td width="35%">
          <html:text property="beneficiaryname" styleClass="textBox textBoxLarge"  maxlength="250" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%=viewmode%>"/>
        </td>
        <td width="19%" class="formLabel">Relationship: </td>
        <td width="25%">
         <html:select property="relationTypeID" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
          <html:option value="">Select from list</html:option>
          <html:optionsCollection name="listRelationshipCode" label="cacheDesc" value="cacheId"/>
      </html:select>
    </td>
      </tr>
    </table>
  </fieldset>
<!-- E N D : Form Fields -->
<!-- S T A R T : Buttons -->
  <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="100%" align="center">
         <%
          if(viewmode==false)
        {
        %>
	        <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
			<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
        <%
          }//end of if(TTKCommon.isAuthorized(request,"Edit"))
        %>

        <%
          if(TTKCommon.isAuthorized(request,"Delete"))
        {
        %>
           <logic:match name="frmEnrollment" property="check" value="Edit">
			    <button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
            </logic:match>
       <%
             }//end of if(TTKCommon.isAuthorized(request,"Delete"))
       %>
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
      </td>
    </tr>
  </table>
<!-- E N D : Buttons -->
</div>
  <INPUT TYPE="hidden" NAME="mode" value="">
  <input type="hidden" name="child" value="Employee Details">
  <html:hidden property="proposalFormYN" value="" />
  <INPUT TYPE="hidden" NAME="stopPatClmYN" VALUE="">
  <html:hidden property="occupationCode" name="frmPolicyDetails" />
 <html:hidden property="switchType" value="" />
 <html:hidden property="memberCancelRemarks" value="" /> 
   <html:hidden property="productAuthority" name="frmPolicyDetails" /> 
  <logic:notEmpty name="frmPolicyDetails" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
  
</html:form>
</body>
</html>
<!-- E N D : Content/Form Area -->