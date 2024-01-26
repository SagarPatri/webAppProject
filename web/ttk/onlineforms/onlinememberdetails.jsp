<%/**
 * @ (#) onlinememberdetails.jsp Jan 14th,2008
 * Project       : TTK HealthCare Services
 * File          : onlinememberdetails.jsp
 * Author        : Krupa J
 * Company       : Span Systems Corporation
 * Date Created  : Jan 14th,2008
 * @author       : Krupa J  
 * Modified by   : Ramakrishna K M
 * Modified date : Apr 05th,2008
 * Reason        : Added Javascript for blocking F5 key
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<!-- 	// Change added for KOC1227H  -->
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,org.apache.struts.action.DynaActionForm,com.ttk.dto.usermanagement.UserSecurityProfile" %>
<head>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/onlinememberdetails.js"></SCRIPT>
<%
//Added as per kOC 1264
UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	String strActiveSubLink=TTKCommon.getActiveSubLink(request);
	//Added as per kOC 1264
%>
<script language="javascript">
	var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>

<script language=javascript>
document.onkeydown = function(){
	if(window.event && window.event.keyCode == 116)
    { // Capture and remap F5
    	window.event.keyCode = 505;
    }//end of if(window.event && window.event.keyCode == 116)
	if(window.event && window.event.keyCode == 505)
    { // New action for F5
    	return false;
        // Must return false or the browser will refresh anyway
    }//end of if(window.event && window.event.keyCode == 505)
}//end of function of keydown
</script>
</head>

<%
  pageContext.setAttribute("floaterNonFloater",Cache.getCacheObject("floaterNonFloater"));
  pageContext.setAttribute("relationshipCode",Cache.getCacheObject("relationshipCode"));
  pageContext.setAttribute("gender",Cache.getCacheObject("gender"));
  boolean viewname=false;
  boolean viewrelation=false;
  boolean viewgender=false;
  boolean viewdob=false;
  boolean viewage=false;
  boolean viewsum=false;
  boolean viewmemtype=false;
  boolean viewfamilysum=false;

  // Change added for KOC1227H
  DynaActionForm frmMemberDetails=(DynaActionForm)request.getSession().getAttribute("frmMemberDetails");
  String noteChange=(String)frmMemberDetails.get("noteChange");
  String EnrollNo = (String)frmMemberDetails.get("enrollmentNbr");
  String policyDate = (String)request.getSession().getAttribute("policyDate");
  String DOJ = (String)request.getSession().getAttribute("empDateOfJoining");
  String DOM = (String)request.getSession().getAttribute("EmpDateOfMarriage");

%>

<!-- S T A R T : Content/Form Area -->
	<html:form action="/OnlineMemberDetailsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="78%">Member Details - <bean:write name="frmMemberDetails" property="caption"/></td>
	    <td width="22%" align="right" class="webBoard">&nbsp;</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
	<div class="contentArea" id="contentArea">
	<logic:notEmpty name="frmMemberDetails" property="loginWindowPeriodAlert">
	<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <bean:write name="frmMemberDetails" property="loginWindowPeriodAlert"/>
	        </td>
	      </tr>
   	 </table>
	</logic:notEmpty>
<!-- Changes as per KOC 1159 and 1160 -->
	<logic:notEmpty name="frmMemberDetails" property="combinationMembersLimit">
	<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	          <td><img src="/ttk/images/ErrorIcon.gif" title="Alert" alt="Alert" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:write name="frmMemberDetails" property="combinationMembersLimit"/>
	      </tr>
   	 </table>
	</logic:notEmpty>

	<!-- Changes as per KOC 1159 and 1160 -->
	<html:errors/>
	<!-- Added Rule Engine Enhancement code : 27/02/2008 -->
	<ttk:BusinessErrors name="BUSINESS_ERRORS" scope="request" />
	<!--  End of Addition -->

	<!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success" width="16" height="16" align="absmiddle">&nbsp;
					<bean:message name="updated" scope="request"/>
				</td>
			</tr>
		</table>
	</logic:notEmpty>

	<logic:notEmpty name="updated" scope="request">
		<%String alert = (String)request.getAttribute("updated");
	 		if(alert.equalsIgnoreCase("message.savedSuccessfully"))
	 		{%>
		 		<script>
		  		window.alert("Dependent Record updated successfully!");
		 		</script>
	 		<%}
	 		else if(alert.equalsIgnoreCase("message.addedSuccessfully"))
	 		{
		 		%>
		 		<script>
		 		window.alert("Dependent Record added successfully!");
		 		</script>
		 	<%
	 		}%>

	</logic:notEmpty>

	<!-- Added for Oracle alert Message on Deletion of Records -->
	
	<logic:notEmpty name="alert" scope="request">
	
		<%String oraclealert = (String)request.getAttribute("alert");
		String rownum = (String)request.getAttribute("rownum");
		
				
		if(oraclealert.equalsIgnoreCase("message.oracle"))
		{
			%>
			<script>
			 var rownum = '<%=rownum%>';
			 var mode = "doconfirmDelete";
			 var msg =  window.confirm("Once you delete the parents, you will not be able to re-enroll until employed with Oracle.");
			 if(msg)
			 {
				 checkDelete(rownum,mode);
			 }
			</script>
			<%
		}
		else if(oraclealert.equalsIgnoreCase("message.others"))
		{
			%>
			<script>
			 var rownum = '<%=rownum%>';
			 var mode = "doconfirmDelete";
			 var msg = window.confirm("Are you sure you want to delete the selected record ?");
			 if(msg)
			 {
				 checkDelete(rownum,mode);
			 }
			</script>
			<%
		}
		
		%>
	</logic:notEmpty>
	




	<!-- E N D : Success Box -->
	<!--Added for IBM....koc-1216-->

		<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<logic:notEmpty name="CheckMaleValidation" scope="request">
					<td><img src="/ttk/images/ErrorIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;<b></b><font color="red"><bean:message name="CheckMaleValidation" scope="request"/></font></b></td>
					</logic:notEmpty>
					<logic:notEmpty name="CheckFemaleValidation" scope="request">
					<td><img src="/ttk/images/ErrorIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;<b></b><font color="red"><bean:message name="CheckFemaleValidation" scope="request"/></font></b></td>
				</logic:notEmpty>
			</tr>
			</table>

	<!--Ended---->
	<logic:notEmpty name="configurationinfo" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success" width="16" height="16" align="absmiddle">&nbsp;
					<bean:message name="configurationinfo" scope="request"/>
				</td>
			</tr>
		</table>
	</logic:notEmpty>

	<logic:notEmpty name="frmMemberDetails" property="display">
		<div class="scrollableGrid" style="height:290px;">
			<fieldset>
					<legend>Member List</legend>
					<!-- S T A R T : Grid -->
					<ttk:HtmlGrid name="tableData" className="gridWithCheckBox zeroMargin"/>
					<!-- E N D : Grid -->

						<table width="98%" style="margin-top:5px;border:1px solid #A0A8BB;" border="0" align="center" cellpadding="0" cellspacing="0" >
						    <tr style="height:22px;">
						    <logic:notEmpty name="frmMemberDetails" property="floaterSumStatus">
							    <logic:notMatch name="frmMemberDetails" property="floaterSumStatus" value="HIDE">
			        			<logic:match name="frmMemberDetails" property="floaterSumStatus" value="SHOW"><%viewfamilysum=true;%></logic:match>
				        			<td align="right" nowrap>Floater Sum insured:&nbsp;&nbsp;</td>
				          				<td width="2%"><html:text property="floaterSumInsured"  maxlength="17" styleClass="textBoxWeblogin textBoxSmall" disabled="<%= viewfamilysum %>"/></td>
				          				&nbsp;
				        		</logic:notMatch>
				        	</logic:notEmpty>
				        	<logic:match name="frmMemberDetails" property="familySumIconYN" value="Y">
							    <td align="right">Family Additional Sum Insured:&nbsp;&nbsp;</td>
							    <td><a href="javascript:onRatesIconFamily()"><img src="/ttk/images/RatesIcon.gif" alt="Family Sum Insured" title="Family Sum Insured" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;</td>
						    	</tr>
							</logic:match>
						</table>
			</fieldset>
		
			</div>
	</logic:notEmpty>
  
	<!-- S T A R T : Buttons -->
	<logic:match name="frmMemberDetails" property="policyOPT" value="Y">
	<logic:equal name="frmMemberDetails" property="allowAddYN" value="N">
	</logic:equal>
	<logic:notEqual name="frmMemberDetails" property="allowAddYN" value="N">
		<fieldset>
		<legend>Member Details Information</legend>
		  	<table align="center" class="formContainerWeblogin" border="0" cellspacing="0" cellpadding="0">
		      <tr style="padding-top:10px;">
		      <logic:notEmpty name="frmMemberDetails" property="nameStatus">
			      <logic:notMatch name="frmMemberDetails" property="nameStatus" value="HIDE">
			      	<logic:match name="frmMemberDetails" property="nameStatus" value="SHOW"><%viewname=true;%></logic:match>
				      	<td align="left" nowrap>Member Name:<logic:match name="frmMemberDetails" property="nameMandatoryYN" value="Y"><span class="mandatorySymbol">*</span></logic:match><br>
				      	<logic:notEqual name="frmMemberDetails" property="relationID" value="NSF">
				          <html:text property="name" styleClass="textBoxWeblogin textBoxMediumWeblogin " maxlength="250" disabled="<%= viewname %>" onkeypress="ConvertToUpperCase(event.srcElement);" />
				        </logic:notEqual>
				        <logic:equal name="frmMemberDetails" property="relationID" value="NSF">
						  <html:text property="name" styleClass="textBoxWeblogin textBoxMediumWeblogin" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="250" disabled="true"/>
			    		  <input type="hidden" name="name" value="<bean:write name="frmMemberDetails" property="name"/>">
					    </logic:equal>
					   </td>
			      </logic:notMatch>
			  </logic:notEmpty>

		     <logic:notEmpty name="frmMemberDetails" property="memTypeStatus">
		        <logic:notMatch name="frmMemberDetails" property="memTypeStatus" value="HIDE">
			        <td align="left" nowrap>Member Type:<logic:match name="frmMemberDetails" property="memTypeMandatoryYN" value="Y"><span class="mandatorySymbol">*</span></logic:match>&nbsp;&nbsp;&nbsp;<br>
					<logic:match name="frmMemberDetails" property="memTypeStatus" value="SHOW"><%viewmemtype=true;%></logic:match>
					<html:select property="memberTypeID" styleClass="selectBoxWeblogin selectBoxMediumWeblogin" disabled="<%= viewmemtype %>">
			           <html:optionsCollection name="floaterNonFloater" label="cacheDesc" value="cacheId" />
			        </html:select>
			        <html:hidden property="memberTypeID"/>
			       	</td>
		       	</logic:notMatch>
		     </logic:notEmpty>

		     <logic:notEmpty name="frmMemberDetails" property="relationStatus">
		        <logic:notMatch name="frmMemberDetails" property="relationStatus" value="HIDE">
			        <td align="left" nowrap>Relationship:<logic:match name="frmMemberDetails" property="relationMandatoryYN" value="Y"><span class="mandatorySymbol">*</span></logic:match>&nbsp;&nbsp;&nbsp;<br>
			        <logic:match name="frmMemberDetails" property="relationStatus" value="SHOW"><%viewrelation=true;%></logic:match>
			        	<html:select property="relationTypeID" styleClass="selectBoxWeblogin selectBoxMediumWeblogin" onchange="onRelationshipChange();" disabled="<%= viewrelation %>">
				        	<html:option value="">Select from list</html:option>
				            <html:optionsCollection name="frmMemberDetails" property="alRelationShip"  label="cacheDesc" value="cacheId" />
			            </html:select>
			        </td>
		        </logic:notMatch>
		     </logic:notEmpty>


		     <logic:notEmpty name="frmMemberDetails" property="genderStatus">
		         <logic:notMatch name="frmMemberDetails" property="genderStatus" value="HIDE">
		         	<td align="left" nowrap id="type" >Gender:<logic:match name="frmMemberDetails" property="genderMandatoryYN" value="Y"><span class="mandatorySymbol">*</span></logic:match><br>
				         <logic:match name="frmMemberDetails" property="genderStatus" value="SHOW"><%viewgender=true;%></logic:match>
				         <logic:equal name="frmMemberDetails" property="genderYN" value="OTH">
				           <html:select property="genderTypeID" styleClass="selectBoxWeblogin selectBoxWeblogin" disabled="<%= viewgender %>">
				           		<html:option value="">Select from list</html:option>
				           		<html:optionsCollection name="gender" label="cacheDesc" value="cacheId" />
				           </html:select>
				            &nbsp;&nbsp;&nbsp;&nbsp;
				       	  </logic:equal>
				       	  <logic:notEqual name="frmMemberDetails" property="genderYN" value="OTH">
					       	  <html:select property="genderTypeID" styleClass="selectBoxWeblogin selectBoxDisabledWeblogin" disabled="true">
								  <html:option value="">Select from list</html:option>
							      <html:optionsCollection name="gender" label="cacheDesc" value="cacheId" />
							  </html:select>
							  <input type="hidden" name="genderTypeID" value="<bean:write name="frmMemberDetails" property="genderYN"/>">
				       	  </logic:notEqual>
			       	 </td>
		         </logic:notMatch>
		     </logic:notEmpty>
	      	 <td width="15%" nowrap colspan="2"></td>
		     </tr>

		      <tr>
		      <%
			  	if(EnrollNo.contains("I310"))
			  	{
			  		
			  		      		%>
			  		      		<logic:notEmpty name="frmMemberDetails" property="dobStatus">
			  			      	<logic:notMatch name="frmMemberDetails" property="dobStatus" value="HIDE">
			  			      	<logic:match name="frmMemberDetails" property="dobStatus" value="SHOW"><%viewdob=true;%></logic:match>
			  				        <td align="left" nowrap >Date of Birth(DD/MM/YYYY):<logic:match name="frmMemberDetails" property="dobMandatoryYN" value="Y"><span class="mandatorySymbol">*</span></logic:match><br>

			  						 <logic:equal name="frmMemberDetails" property="sumInsIconEditYN" value="N">
			  				          <html:text property="dateOfBirth" maxlength="10" styleClass="textBoxWeblogin textDate" onblur="javascript:onIBMDateofBirth()" disabled="<%= viewdob %>" />
			  						 </logic:equal>
			  				         <logic:notEqual name="frmMemberDetails" property="sumInsIconEditYN" value="N">
			  				          <html:text property="dateOfBirth" maxlength="10" styleClass="textBoxWeblogin textDate" onblur="javascript:onIBMDateofBirth()" disabled="<%= viewdob %>" onchange="javascript:onChangeAge()"/>
			  						 </logic:notEqual>
			  					  		&nbsp;<logic:match name="frmMemberDetails" property="dobStatus" value="EDIT"><a name="CalendarObjectBirthDate" id="CalendarObjectBirthDate" href="#" onClick="javascript:show_calendar('CalendarObjectBirthDate','frmMemberDetails.dateOfBirth',document.frmMemberDetails.dateOfBirth.value,'',event,148,178),onChangeAge();return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match>
			  						</td>
			  					</logic:notMatch>
			  				  </logic:notEmpty>

			  				 <logic:notEmpty name="frmMemberDetails" property="ageStatus">
			  			        <logic:notMatch name="frmMemberDetails" property="ageStatus" value="HIDE">
			  					<logic:match name="frmMemberDetails" property="ageStatus" value="SHOW"><%viewage=true;%></logic:match>
			  						<td align="left" nowrap 10px;>Age:<logic:match name="frmMemberDetails" property="ageMandatoryYN" value="Y"><span class="mandatorySymbol">*</span></logic:match><br>
			  				           <logic:equal name="frmMemberDetails" property="sumInsIconEditYN" value="N">
			  				            <html:text property="age"  maxlength="3" styleId="ageid" styleClass="textBoxWeblogin textBoxSmall" disabled="<%= viewage %>" />
			  					          &nbsp;
			  				           </logic:equal>
			  				         <logic:notEqual name="frmMemberDetails" property="sumInsIconEditYN" value="N">
			  				        	 <html:text property="age"  maxlength="3" styleId="ageid" styleClass="textBoxWeblogin textBoxSmall" disabled="<%= viewage %>" onchange="javascript:onChangeAge()"/>
			  					          &nbsp;
			  						 </logic:notEqual>
			  				        </td>
			  			        </logic:notMatch>
			  			         <logic:match name="frmMemberDetails" property="ageStatus" value="HIDE">
			  			        	<input type="hidden" name="age" value="">
			  			        </logic:match>
			  			     </logic:notEmpty>
			  				<%
			  		}
			  	 else
			  	 {
			  			
			  		 %>

		      			<logic:notEmpty name="frmMemberDetails" property="dobStatus">
		      			<logic:notMatch name="frmMemberDetails" property="dobStatus" value="HIDE">
		      			<logic:match name="frmMemberDetails" property="dobStatus" value="SHOW"><%viewdob=true;%></logic:match>
			        	<td align="left" nowrap >Date of Birth(DD/MM/YYYY):<logic:match name="frmMemberDetails" property="dobMandatoryYN" value="Y"><span class="mandatorySymbol">*</span></logic:match><br>

					 	<logic:equal name="frmMemberDetails" property="sumInsIconEditYN" value="N">
			          	<html:text property="dateOfBirth" maxlength="10" styleClass="textBoxWeblogin textDate" onblur="javascript:onDateofBirth()" disabled="<%= viewdob %>" />
					 	</logic:equal>
			         	<logic:notEqual name="frmMemberDetails" property="sumInsIconEditYN" value="N">
			          	<html:text property="dateOfBirth" maxlength="10" styleClass="textBoxWeblogin textDate" onblur="javascript:onDateofBirth()" disabled="<%= viewdob %>" onchange="javascript:onChangeAge()"/>
					 	</logic:notEqual>
				  		&nbsp;<logic:match name="frmMemberDetails" property="dobStatus" value="EDIT"><a name="CalendarObjectBirthDate" id="CalendarObjectBirthDate" href="#" onClick="javascript:show_calendar('CalendarObjectBirthDate','frmMemberDetails.dateOfBirth',document.frmMemberDetails.dateOfBirth.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match>
					</td>
					</logic:notMatch>
			  		</logic:notEmpty>

			 	<logic:notEmpty name="frmMemberDetails" property="ageStatus">
		        <logic:notMatch name="frmMemberDetails" property="ageStatus" value="HIDE">
				<logic:match name="frmMemberDetails" property="ageStatus" value="SHOW"><%viewage=true;%></logic:match>
					<td align="left" nowrap 10px;>Age:<logic:match name="frmMemberDetails" property="ageMandatoryYN" value="Y"><span class="mandatorySymbol">*</span></logic:match><br>
			           <logic:equal name="frmMemberDetails" property="sumInsIconEditYN" value="N">
			            <html:text property="age"  maxlength="3" styleId="ageid" styleClass="textBoxWeblogin textBoxSmall" disabled="<%= viewage %>" />
				          &nbsp;
			           </logic:equal>
			         <logic:notEqual name="frmMemberDetails" property="sumInsIconEditYN" value="N">
			        	 <html:text property="age"  maxlength="3" styleId="ageid" styleClass="textBoxWeblogin textBoxSmall" disabled="<%= viewage %>" onchange="javascript:onChangeAge()"/>
				          &nbsp;
					 </logic:notEqual>
			        </td>
		        </logic:notMatch>
		         <logic:match name="frmMemberDetails" property="ageStatus" value="HIDE">
		        	<input type="hidden" name="age" value="">
		        </logic:match>
		     </logic:notEmpty>
		      <%
			 	}
		      %>
			 <logic:notEmpty name="frmMemberDetails" property="sumInsuredStatus">
				<logic:notMatch name="frmMemberDetails" property="sumInsuredStatus" value="HIDE">
		        <logic:match name="frmMemberDetails" property="sumInsuredStatus" value="SHOW"><%viewsum=true;%></logic:match>

			     <logic:equal name="frmMemberDetails" property="memberTypeID" value="PFL">
					<td align="left" id="totsumins" nowrap 10px;>Family Sum Insured:<logic:match name="frmMemberDetails" property="sumInsuredMandatoryYN" value="Y"><span class="mandatorySymbol">*</span></logic:match><br>
                 </logic:equal>
                 <logic:notEqual name="frmMemberDetails" property="memberTypeID" value="PFL">
					<td align="left" id="totsumins" nowrap 10px;>Sum Insured:<logic:match name="frmMemberDetails" property="sumInsuredMandatoryYN" value="Y"><span class="mandatorySymbol">*</span></logic:match><br>
                 </logic:notEqual>

		       	 <html:text property="totalSumInsured"  maxlength="17" styleClass="textBoxWeblogin textBoxSmall" disabled="<%= viewsum %>"/>
			     &nbsp;
			     <logic:equal name="frmMemberDetails" property="addSumIconYN" value="Y">
			     <logic:notEqual name="frmMemberDetails" property="sumInsIconEditYN" value="N">
			      <a href="#" onClick="onRatesIconDetails()"><img src="/ttk/images/RatesIcon.gif" alt="Additional SumInsured" title="Additional SumInsured" width="16" height="16" border="0" align="center" disabled="<%= viewsum %>"></a>
			     </logic:notEqual>
			    </logic:equal>
			        </td>
		        </logic:notMatch>
		     </logic:notEmpty>

		       <td colspan="2" align="center" nowrap >&nbsp;</td>
		        </tr>
		      </table>
	    </fieldset>
    </logic:notEqual>
    </logic:match>

    <logic:match name="frmMemberDetails" property="policyOPT" value="Y">
	    <logic:notEqual name="frmMemberDetails" property="allowAddYN" value="N">
	    <%
			if(EnrollNo.contains("I310-01")||(EnrollNo.contains("I310-001")))
			{%>
				<font color="#0C48A2">
		 		<b>Declaration:</b>
		 		</font>
				<table>
				<tr>
				<td><input type=checkbox id="Accept" name="confirm" value="Y" disabled="true">&nbsp;I hereby declare that the details as given for enrollment of my son/daughter are correct and confirm that they are unmarried and financially dependent on me and have not established their own independent household.  If any information provided by me is found to be incorrect or false,I understand that I may be asked to submit supporting documents (i.e., government approved id card, educational details, etc) for validation. If the company determines that any false information has been provided, this will be construed as a BCG Violation and appropriate action will be taken, which may include termination of employment.
				</tr>
				</table>
				<%
			}%>
		</logic:notEqual>
		</logic:match>

	<%
		    if(TTKCommon.isAuthorized(request,"Edit") && noteChange.equals("PNF"))
		    {
		%>
	<table>
    <tr>
	 		<td>
				<font color="#0C48A2">
				<b>Note:</b> To edit sum insured please click on <img src="/ttk/images/RatesIcon.gif" alt="Additional SumInsured" title="Additional SumInsured" width="16" height="16" border="0" align="center" > in column next to total sum insured above. <br>
				</font>
			</td>
		 </tr>
		      </table>
			  <%
			}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	      //Added as per kOC 1264
     if (userSecurityProfile.getGroupID().equalsIgnoreCase("A0994") && (strActiveSubLink.equalsIgnoreCase("Enrollment")))
		{
			%>
			 	<%--Added as per kOC 1264 --%>
			<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
			<tr>
			<td><b>Note:</b></td>
			</tr>
			<ul>
			<tr>
			<td>
			<font color="#0C48A2"> <li>Parents Coverage is restricted to Rs.100000(1 Lakh).
			</font>
			</td>
			</tr>
			</ul>
			 </table>
			<%
		
	   }
    %>
      <%--Added as per kOC 1264 --%>



    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td colspan="4" align="center" nowrap style="padding-top:10px;">&nbsp;&nbsp;&nbsp;
		<%
		    if(TTKCommon.isAuthorized(request,"Edit"))
		    {
		%>
		<logic:notMatch name="frmMemberDetails" property="policyStatusTypeID" value="POC">
		<logic:match name="frmMemberDetails" property="policyOPT" value="Y">
        <logic:match name="frmMemberDetails" property="allowAddYN" value="Y">
			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave to List</button>&nbsp;
			<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
	    </logic:match>
	    </logic:match>
	    </logic:notMatch>
		<%
			}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	    %>
		<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
        </td>
        </tr>
    </table>
 
   
	<!-- E N D : Buttons -->
	</div>

	<html:hidden property="policySeqID"/>
	<html:hidden property="policyGroupSeqID"/>
	<html:hidden property="prodPlanSeqID"/>
	<html:hidden property="loginDate"/>
	<html:hidden property="insuredName"/>
	<html:hidden property="relationID"/>
    <html:hidden property="genderYN"/>
    <html:hidden property="dobStatus"/>
    <html:hidden property="ageStatus"/>
    <html:hidden property="sumInsuredStatus"/>
    <html:hidden property="memberSeqID"/>
    <html:hidden property="planAmt"/>
    <html:hidden property="enrollmentNbr"/>
	<html:hidden property="ageDeclaration"/>


	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<input type="hidden" name="focusID" value="">
	<input type="hidden" name="policyDate" value="<%=policyDate%>">
	<input type="hidden" name="DOJ" value="<%=DOJ%>">
	<input type="hidden" name="DOM" value="<%=DOM%>">


	<logic:match name="frmMemberDetails" property="policyOPT" value="Y">
	<%
		if(EnrollNo.contains("I310-01")||(EnrollNo.contains("I310-001")))
		{%>

				<script>
				ageDeclaration();
				</script>
		<%}%>
	</logic:match>

	<logic:notEmpty name="frmMemberDetails" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
	</html:form>

	<!-- E N D : Content/Form Area -->