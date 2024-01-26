<%
/** @ (#) ailmentdetails.jsp May 10th, 2006
 * Project    	 : TTK Healthcare Services
 * File       	 : ailmentdetails.jsp
 * Author     	 : Raghavendra T M
 * Company    	 : Span Systems Corporation
 * Date Created	 : May 10th, 2006
 * @author 		 : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */ 
 %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/preauth/ailmentdetails.js"></script>
<script language="javascript" src="/ttk/scripts/trackdatachanges.js"></script>
<!--added for KOC-Decoupling--> 
<SCRIPT language=Javascript>   
function isNumeric(field) {
    var re = /^[0-9]*\.*[0-9]*$/;
    if (!re.test(field.value)) {
        alert("Data entered must be Numeric!");
		field.focus();
		field.value="";
		return false;
    }
}
</Script>
<%
	String strLink=TTKCommon.getActiveLink(request);
	String ampm[] = {"AM","PM"};
	boolean viewmode=true;
	String perMode = "disabled  readonly";
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
		perMode = "" ;
	}
	pageContext.setAttribute("ampm",ampm);
	pageContext.setAttribute("strLink",strLink);
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	pageContext.setAttribute("durationType",Cache.getCacheObject("durationType"));
	pageContext.setAttribute("treatmentPlan",Cache.getCacheObject("treatmentPlan"));
	pageContext.setAttribute("specialty",Cache.getCacheObject("specialty"));
	pageContext.setAttribute("dischargeCondition",Cache.getCacheObject("dischargeCondition"));
	pageContext.setAttribute("disabilityType",Cache.getCacheObject("disabilityType"));
	pageContext.setAttribute("medicineSystem",Cache.getCacheObject("medicineSystem"));
    pageContext.setAttribute("ailmentClaimType",Cache.getCacheObject("ailmentClaimType"));
    pageContext.setAttribute("surgeryType",Cache.getCacheObject("surgeryType"));
    pageContext.setAttribute("MaternityType",Cache.getCacheObject("MaternityType"));
	
	//added for KOC-Decoupling
    pageContext.setAttribute("listHospitalizationType",Cache.getCacheObject("hospitalizationType"));
    pageContext.setAttribute("listDurationType",Cache.getCacheObject("durationType"));
    pageContext.setAttribute("listTreatmentPlan",Cache.getCacheObject("treatmentPlanICD"));
    pageContext.setAttribute("listGeneralCodePlan",Cache.getCacheObject("generalCodePlan"));
	
%>
	<html:form action="/AilmentDetailsAction.do">
	<!-- S T A R T : Content/Form Area -->
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Ailment Details<bean:write property="caption" name="frmAilmentDetails"/></td>
	<td align="right" class="webBoard">&nbsp;&nbsp;</td>
  </tr>
</table>
	<div class="contentArea" id="contentArea">
  <html:errors/>
	<!-- E N D : Page Title -->

	<!-- S T A R T : Success Box -->
	<!--KOC FOR Grievance-->
	<logic:notEmpty name="seniorCitizen" scope="request">
    <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
          <bean:message name="seniorCitizen" scope="request"/>
        </td>
      </tr>
    </table>
  </logic:notEmpty>
	<!--<logic:notEmpty name="frmAilmentDetails" property="seniorCitizen">
			  		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   				<tr>
	     			<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
			 			<strong><bean:write property="seniorCitizen" name="frmAilmentDetails"/></strong> 
					</td>
	   				</tr>
	  				</table>			  		
			  		</logic:notEmpty>
 		--><!--KOC FOR Grievance-->
	 <logic:notEmpty name="updated" scope="request">
	  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="updated" scope="request"/>
	     </td>
	  </tr>
	 </table>
    </logic:notEmpty>
    <!-- S T A R T : Form Fields -->

     <!-- S T A R T : Error Dialog Box for Diagnosis Date -->
	 <logic:notEmpty name="diagnosisvalidation" scope="request">
	  <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/ErrorIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="diagnosisvalidation" scope="request"/>
	     </td>
	  </tr>
	 </table>
    </logic:notEmpty>
    
    <!-- S T A R T : Error Dialog Box for Certificate Date -->
	 <logic:notEmpty name="certificatevalidation" scope="request">
	  <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/ErrorIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="certificatevalidation" scope="request"/>
	     </td>
	  </tr>
	 </table>
    </logic:notEmpty>	

<fieldset>
<legend>Medical Information</legend>
  	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="35%" class="formLabel">Complaints: <span class="mandatorySymbol">*</span></td>
            <td width="65%" class="formLabel">
              <html:textarea property="ailmentDesc" name="frmAilmentDetails" styleClass="textBox textAreaMediumht" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
            </td>
        </tr>
        <tr>
      		<td class="formLabel"> System of Medicine:</td>
      		<td>
		      	<html:select property="medicineSystemTypeID" name="frmAilmentDetails" styleClass="selectBox" onchange="javascript:showNoofVisits();" disabled="<%= viewmode %>">	<!-- javascript:showNoofVisits() added for physiotherapy cr 1307 -->
		            <html:options collection="medicineSystem" property="cacheId" labelProperty="cacheDesc"/>
		      	</html:select>
      		</td>
    	</tr>
    	<!-- physiotherapy cr 1307 -->
        <logic:equal name="strLink" value="Claims" >
        <logic:equal name="frmAilmentDetails" property="claimSubTypeID" value="OPD" >
    	<logic:equal name="frmAilmentDetails" property="medicineSystemTypeID" value="SAH" >
    	<tr id="visits" style="display: ">
	        <td class="formLabel">No. of Visits: <span class="mandatorySymbol">*</span></td>
            <td class="formLabel">
             <table cellpadding="1" cellspacing="0">
              <tr>
               <td><html:text property="nuOfVisits" name="frmAilmentDetails" styleClass="textBox textBoxTiny" maxlength="5"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
              </tr>		
   	     </table>
   	    </td>
        </tr>
        </logic:equal>
    	<logic:notEqual name="frmAilmentDetails" property="medicineSystemTypeID" value="SAH" >
		    	<logic:equal name="frmAilmentDetails" property="medicineSystemTypeID" value="ACU" >
		    	<tr id="visits" style="display: ">
			        <td class="formLabel">No. of Visits: <span class="mandatorySymbol">*</span></td>
		            <td class="formLabel">
		             <table cellpadding="1" cellspacing="0">
		              <tr>
		               <td><html:text property="nuOfVisits" name="frmAilmentDetails" styleClass="textBox textBoxTiny" maxlength="5"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
		              </tr>		
		   	     </table>
		   	    </td>
		        </tr>
		    	</logic:equal>
		    	<logic:notEqual name="frmAilmentDetails" property="medicineSystemTypeID" value="ACU" >
				    	<logic:equal name="frmAilmentDetails" property="medicineSystemTypeID" value="CHI" >
				    	<tr id="visits" style="display: ">
					        <td class="formLabel">No. of Visits: <span class="mandatorySymbol">*</span></td>
				            <td class="formLabel">
				             <table cellpadding="1" cellspacing="0">
				              <tr>
				               <td><html:text property="nuOfVisits" name="frmAilmentDetails" styleClass="textBox textBoxTiny" maxlength="5"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
				              </tr>		
				   	     </table>
				   	    </td>
				        </tr>
				    	</logic:equal>
				    	<logic:notEqual name="frmAilmentDetails" property="medicineSystemTypeID" value="CHI" >
						    	<logic:equal name="frmAilmentDetails" property="medicineSystemTypeID" value="OST" >
							    	<tr id="visits" style="display: ">
								        <td class="formLabel">No. of Visits: <span class="mandatorySymbol">*</span></td>
							            <td class="formLabel">
							             <table cellpadding="1" cellspacing="0">
							              <tr>
							               <td><html:text property="nuOfVisits" name="frmAilmentDetails" styleClass="textBox textBoxTiny" maxlength="5"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
							              </tr>		
							   	     </table>
							   	    </td>
							        </tr>
				    			</logic:equal>
						    	<logic:notEqual name="frmAilmentDetails" property="medicineSystemTypeID" value="OST" >		
						    	<tr id="visits" style="display: none">
							        <td class="formLabel">No. of Visits: <span class="mandatorySymbol">*</span></td>
						            <td class="formLabel">
						             <table cellpadding="1" cellspacing="0">
						              <tr>
						               <td><html:text property="nuOfVisits" name="frmAilmentDetails" styleClass="textBox textBoxTiny" maxlength="5"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
						              </tr>		
						   	     </table>
						   	    </td>
						        </tr>
						    	</logic:notEqual>
				  </logic:notEqual>
		    	</logic:notEqual>
		    	</logic:notEqual>
		    	</logic:equal>
			<logic:notEqual name="frmAilmentDetails" property="claimSubTypeID" value="OPD" >	    	
						    	<tr id="visits" style="display:none">
							        <td class="formLabel">No. of Visits: <span class="mandatorySymbol">*</span></td>
						            <td class="formLabel">
						             <table cellpadding="1" cellspacing="0">
						              <tr>
						               <td><html:text property="nuOfVisits" name="frmAilmentDetails" styleClass="textBox textBoxTiny" maxlength="5"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
						              </tr>		
						   	     </table>
						   	    </td>
						        </tr>
	    	</logic:notEqual>
		    	</logic:equal>
		    	<logic:equal name="strLink" value="Pre-Authorization" >
				<logic:empty name="frmAilmentDetails" property="claimSubTypeID">
						    	<tr id="visits" style="display:none">
							        <td class="formLabel">No. of Visits: <span class="mandatorySymbol">*</span></td>
						            <td class="formLabel">
						             <table cellpadding="1" cellspacing="0">
						              <tr>
						               <td><html:text property="nuOfVisits" name="frmAilmentDetails" styleClass="textBox textBoxTiny" maxlength="5"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
						              </tr>		
						   	     </table>
						   	    </td>
						        </tr>
		    	</logic:empty>
		    	</logic:equal>
    	<!-- physiotherapy cr 1307 -->
    	<tr>
      		<td class="formLabel">Accident Related Cases:</td>
      		<td>
		      	<html:select property="ailClaimTypeID" name="frmAilmentDetails" styleClass="selectBox" disabled="<%= viewmode %>">
		            <html:options collection="ailmentClaimType" property="cacheId" labelProperty="cacheDesc"/>
		      	</html:select>
      		</td>
    	</tr>
        <logic:match name="strLink" value="Pre-Authorization" >
<tr>
	 <td class="formLabel">Specialty: <span class="mandatorySymbol">*</span></td>
	 <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
		<tr>
			 <td>
			   <html:select property="specialityTypeID" name="frmAilmentDetails" onchange="showHideType();" style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
			<html:option value="">Select from list</html:option>
			<html:options collection="specialty" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>
			</td>
				<logic:match name="frmAilmentDetails" property="specialityTypeID" value="SUR" >
					<td id="surgerytype" valign="middle" style="padding:0 0 0 18px;display:"><label style="vertical-align:top" class="formLabel">Surgery Type:<span class="mandatorySymbol">*</span></label>
					 <html:select property="surgeryTypeID" name="frmAilmentDetails" styleClass="selectBox"  disabled="<%=viewmode%>" >
					<html:option value="">Select from list</html:option>
					<html:options collection="surgeryType" property="cacheId" labelProperty="cacheDesc"/>
					</html:select>
					</td>
				</logic:match>
				
				<logic:notMatch name="frmAilmentDetails" property="specialityTypeID" value="SUR" >
					<td  id="surgerytype" valign="middle" style="padding:0 0 0 18px;display: none"><label style="vertical-align:top" class="formLabel">Surgery Type:<span class="mandatorySymbol">*</span></label>
					 <html:select property="surgeryTypeID" name="frmAilmentDetails" styleClass="selectBox"  disabled="<%=viewmode%>" >
					<html:option value="">Select from list</html:option>
					<html:options collection="surgeryType" property="cacheId" labelProperty="cacheDesc"/>
					</html:select>
					</td>
				</logic:notMatch>
		</tr>
		
		</table>
		
		    </td>
		</tr>
		
<logic:match name="frmAilmentDetails" property="specialityTypeID" value="MAS" >
   <tr id="maternitytype" style="display: ">
		<td class="formLabel">Maternity Type: <span class="mandatorySymbol" >*</span></td>
		<td  class="formLabel" >
		 <table cellpadding="0" cellspacing="0">
		 <!--added for maternity  -->
	       <tr>
		        <td>
		          <html:select property="maternityTypeID" name="frmAilmentDetails"   onchange="showHideType1();" style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
			          <html:option value="">Select from list</html:option>
			          <html:options collection="MaternityType" property="cacheId" labelProperty="cacheDesc"/>
			      </html:select>
			   </td>
			    <logic:match name="frmAilmentDetails" property="maternityTypeID" value="NBB" >
					<td id="childDob" style="padding:0 0 0 18px;display: "><label style="vertical-align:top" class="formLabel">Child DOB:<span class="mandatorySymbol">*</span></label>
					   
					     <html:text property="childDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
							<logic:match name="viewmode" value="false">
				            	<A NAME="CalendarObjectchildDate" ID="CalendarObjectchildDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectchildDate','forms[1].childDate',document.forms[1].childDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
				           </logic:match>
					    </td>
					     <td id="vaccineDob" style="padding:0 0 0 18px;display: "><label style="vertical-align:top" class="formLabel">Vaccination Date:</label>
								   
					    <html:text property="vaccineDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
						<logic:match name="viewmode" value="false">
						<A NAME="CalendarObjectvaccineDate" ID="CalendarObjectvaccineDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectvaccineDate','forms[1].vaccineDate',document.forms[1].vaccineDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
						</logic:match>
						</td>
					 
				</logic:match>
			<logic:notMatch name="frmAilmentDetails" property="maternityTypeID" value="NBB" >
				<td id="childDob" style="padding:0 0 0 18px;display: none">
					    <label style="vertical-align:top" class="formLabel">Child DOB:<span class="mandatorySymbol">*</span></label>
					     <html:text property="childDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
							<logic:match name="viewmode" value="false">
				            	<A NAME="CalendarObjectchildDate" ID="CalendarObjectchildDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectchildDate','forms[1].childDate',document.forms[1].childDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
				           </logic:match>
					    </td>
				 <td id="vaccineDob" style="padding:0 0 0 18px;display:none "><label style="vertical-align:top" class="formLabel">Vaccination Date:</label>
								   
								     <html:text property="vaccineDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
						<logic:match name="viewmode" value="false">
						<A NAME="CalendarObjectvaccineDate" ID="CalendarObjectvaccineDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectvaccineDate','forms[1].vaccineDate',document.forms[1].vaccineDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
						</logic:match>
						</td>
				</logic:notMatch>
		 </tr>
		</table>
		</td>
		</tr>
		<!-- end added for maternity  -->
	<tr id="noofchildren" style="display: ">
	         <td class="formLabel">No. of Living Children: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	       <tr>
	        <td>
	          <html:select property="livingChildrenNumber" name="frmAilmentDetails"  style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
		         <html:option value="">Select from list</html:option>
		      	 <html:option value="0">0</html:option>
		      	 <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
		         <html:option value="10">10</html:option>
		      </html:select>
		   </td>
		</tr>
		</table>
		</td>
		</tr>
		<%-- added for maternity new--%>
		<tr id="noofbiological" style="display: ">
	         <td class="formLabel">No. of Biological  Children: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	       <tr>
	        <td>
	          <html:select property="biologicalChildrenNumber" name="frmAilmentDetails"  style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
		         <html:option value="">Select from list</html:option>
		      	 <html:option value="0">0</html:option>
		      	 <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
		         <html:option value="10">10</html:option>
		      </html:select>
		   </td>
		</tr>
			</table>
		</td>
		</tr>
		<tr id="noofadopted" style="display: ">
	         <td class="formLabel">No. of Adopted Children: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	       <tr>
	        <td>
	          <html:select property="adoptedChildrenNumber" name="frmAilmentDetails"  style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
		         <html:option value="">Select from list</html:option>
		      	 <html:option value="0">0</html:option>
		      	 <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
		         <html:option value="10">10</html:option>
		      </html:select>
		   </td>
		</tr>
			</table>
		</td>
		</tr>
		<tr id="deliveriesNo" style="display: ">
          <td class="formLabel">No Of Deliveries : <span class="mandatorySymbol">*</span></td>
          <td valign="middle" class="textLabel">
          <html:text property="noOfDeliveries" name="frmAilmentDetails" styleClass="textBox textBoxTiny" maxlength="5" onchange="javascript:computeDischargeDate(this);" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
          </td>
        </tr> 
	<%--end  added for maternity new--%>
		<!--added for maternity  -->
		<tr id="lmpdate" style="display: ">
          <td class="formLabel">LMP: </td>
          <td class="formLabel">
          <html:text property="lmpDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
        <logic:match name="viewmode" value="false">
		<A NAME="CalendarObjectlmpDate" ID="CalendarObjectlmpDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectlmpDate','forms[1].lmpDate',document.forms[1].lmpDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
		</logic:match>
		</td>
        </tr>
		</logic:match>
		<logic:notMatch name="frmAilmentDetails" property="specialityTypeID" value="MAS" >
		<tr id="maternitytype" style="display: none">
	         <td class="formLabel">Maternity Type: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	       <tr>
	        <td>
	          <html:select property="maternityTypeID" name="frmAilmentDetails" onchange="showHideType1();"  style="width:200px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
		          <html:option value="">Select from list</html:option>
		          <html:options collection="MaternityType" property="cacheId" labelProperty="cacheDesc"/>
		      </html:select>
		   </td>
		   
		       <logic:match name="frmAilmentDetails" property="maternityTypeID" value="NBB" >
					<td id="childDob" style="padding:0 0 0 18px;display: "><label style="vertical-align:top" class="formLabel">Child DOB:<span class="mandatorySymbol">*</span></label>
					   
					     <html:text property="childDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
							<logic:match name="viewmode" value="false">
				            	<A NAME="CalendarObjectchildDate" ID="CalendarObjectchildDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectchildDate','forms[1].childDate',document.forms[1].childDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
				           </logic:match>
					    </td>
					     <td id="vaccineDob" style="padding:0 0 0 18px;display: "><label style="vertical-align:top" class="formLabel">Vaccination Date:</label>
								   
								     <html:text property="vaccineDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
						<logic:match name="viewmode" value="false">
						<A NAME="CalendarObjectvaccineDate" ID="CalendarObjectvaccineDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectvaccineDate','forms[1].vaccineDate',document.forms[1].vaccineDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
						</logic:match>
						</td>
					 
				</logic:match>
			<logic:notMatch name="frmAilmentDetails" property="maternityTypeID" value="NBB" >
				
				<td id="childDob" style="padding:0 0 0 18px;display: none">
					    <label style="vertical-align:top" class="formLabel">Child DOB:<span class="mandatorySymbol">*</span></label>
					     <html:text property="childDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
							<logic:match name="viewmode" value="false">
				            	<A NAME="CalendarObjectchildDate" ID="CalendarObjectchildDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectchildDate','forms[1].childDate',document.forms[1].childDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
				           </logic:match>
					    </td>
					     <td id="vaccineDob" style="padding:0 0 0 18px;display:none "><label style="vertical-align:top" class="formLabel">Vaccination Date:</label>
								   
								     <html:text property="vaccineDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
						<logic:match name="viewmode" value="false">
						<A NAME="CalendarObjectvaccineDate" ID="CalendarObjectvaccineDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectvaccineDate','forms[1].vaccineDate',document.forms[1].vaccineDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
						</logic:match>
						</td>
					    
				
			</logic:notMatch>
		</tr>
		</table>
		</td>
		</tr>
		<!-- end added for maternity  -->
		<tr id="noofchildren" style="display: none">
	         <td class="formLabel">No. of Living Children: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	       <tr>
	        <td>
	          <html:select property="livingChildrenNumber" name="frmAilmentDetails"  style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
		         <html:option value="">Select from list</html:option>
		      	 <html:option value="0">0</html:option>
		      	 <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
		         <html:option value="10">10</html:option>
		      </html:select>
		   </td>
		</tr>
		</table>
		</td>
		</tr>
		
			<%-- added for maternity new--%>
		
		<tr id="noofbiological" style="display: none">
	         <td class="formLabel">No. of Biological  Children: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	       <tr>
	        <td>
	          <html:select property="biologicalChildrenNumber" name="frmAilmentDetails"  style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
		         <html:option value="">Select from list</html:option>
		      	 <html:option value="0">0</html:option>
		      	 <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
		         <html:option value="10">10</html:option>
		      </html:select>
		   </td>
		</tr>
			</table>
		</td>
		</tr>
		<tr id="noofadopted" style="display: none">
	         <td class="formLabel">No. of Adopted Children: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	       <tr>
	        <td>
	          <html:select property="adoptedChildrenNumber" name="frmAilmentDetails"  style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
		         <html:option value="">Select from list</html:option>
		      	 <html:option value="0">0</html:option>
		      	 <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
		         <html:option value="10">10</html:option>
		      </html:select>
		   </td>
		</tr>
			</table>
		</td>
		</tr>
		<tr id="deliveriesNo" style="display: none">
          <td class="formLabel">No Of Deliveries : <span class="mandatorySymbol">*</span></td>
          <td valign="middle" class="textLabel">
          <html:text property="noOfDeliveries" name="frmAilmentDetails" styleClass="textBox textBoxTiny" maxlength="5" onchange="javascript:computeDischargeDate(this);" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
          </td>
        </tr> 

			<%--end  added for maternity new--%>
		<!--added for maternity  -->
		<tr id="lmpdate" style="display: none">
          <td class="formLabel">LMP: </td>
          <td class="formLabel">
          <html:text property="lmpDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
        <logic:match name="viewmode" value="false">
		<A NAME="CalendarObjectlmpDate" ID="CalendarObjectlmpDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectlmpDate','forms[1].lmpDate',document.forms[1].lmpDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
		</logic:match>
		</td>
        </tr>
        <!--end added for maternity  -->
		</logic:notMatch>
       </logic:match>
      <logic:match name="strLink" value="Claims" >
	        <tr>
	         <td class="formLabel">Specialty: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	        <tr>
	         <td>
	           <html:select property="specialityTypeID" name="frmAilmentDetails" onchange="showHideType();"  style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
		         <html:option value="">Select from list</html:option>
		         <html:options collection="specialty" property="cacheId" labelProperty="cacheDesc"/>
		      </html:select>
		   </td>
		 <logic:match name="frmAilmentDetails" property="specialityTypeID" value="SUR" >
		    <td id="surgerytype" valign="middle" style="padding:0 0 0 18px;display:"><label style="vertical-align:top" class="formLabel">Surgery Type:  <span class="mandatorySymbol">*</span></label>
		       <html:select property="surgeryTypeID" name="frmAilmentDetails" styleClass="selectBox"  disabled="<%=viewmode%>" >
		           <html:option value="">Select from list</html:option>
		           <html:options collection="surgeryType" property="cacheId" labelProperty="cacheDesc"/>
		       </html:select>
		    </td>
		 </logic:match>
		<logic:notMatch name="frmAilmentDetails" property="specialityTypeID" value="SUR" >
		   <td  id="surgerytype" valign="middle" style="padding:0 0 0 18px;display: none"><label style="vertical-align:top" class="formLabel">Surgery Type:  <span class="mandatorySymbol">*</span></label>
		      <html:select property="surgeryTypeID" name="frmAilmentDetails" styleClass="selectBox"  disabled="<%=viewmode%>" >
		          <html:option value="">Select from list</html:option>
		          <html:options collection="surgeryType" property="cacheId" labelProperty="cacheDesc"/>
		      </html:select>
		  </td>
	   </logic:notMatch>
	   </tr>
		</table>
		</td>
		</tr>
	   <logic:match name="frmAilmentDetails" property="specialityTypeID" value="MAS" >
		<tr id="maternitytype" style="display: ">
	         <td class="formLabel">Maternity Type: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	      <!--added for maternity  -->
	       <tr>
	          <td>
		          <html:select property="maternityTypeID" name="frmAilmentDetails"   onchange="showHideType1();" style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
			          <html:option value="">Select from list</html:option>
			          <html:options collection="MaternityType" property="cacheId" labelProperty="cacheDesc"/>
			      </html:select>
			   </td>
		       <logic:match name="frmAilmentDetails" property="maternityTypeID" value="NBB" >
					<td id="childDob" style="padding:0 0 0 18px;display: "><label style="vertical-align:top" class="formLabel">Child DOB:<span class="mandatorySymbol">*</span></label>
					   
					     <html:text property="childDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
							<logic:match name="viewmode" value="false">
				            	<A NAME="CalendarObjectchildDate" ID="CalendarObjectchildDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectchildDate','forms[1].childDate',document.forms[1].childDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
				           </logic:match>
					    </td>
					      <td id="vaccineDob" style="padding:0 0 0 18px;display: "><label style="vertical-align:top" class="formLabel">Vaccination Date:</label>
								   
								     <html:text property="vaccineDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
						<logic:match name="viewmode" value="false">
						<A NAME="CalendarObjectvaccineDate" ID="CalendarObjectvaccineDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectvaccineDate','forms[1].vaccineDate',document.forms[1].vaccineDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
						</logic:match>
						</td>
					 
				</logic:match>
			<logic:notMatch name="frmAilmentDetails" property="maternityTypeID" value="NBB" >
					<td id="childDob" style="padding:0 0 0 18px;display: none">
					    <label style="vertical-align:top" class="formLabel">Child DOB:<span class="mandatorySymbol">*</span></label>
					     <html:text property="childDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
							<logic:match name="viewmode" value="false">
				            	<A NAME="CalendarObjectchildDate" ID="CalendarObjectchildDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectchildDate','forms[1].childDate',document.forms[1].childDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
				           </logic:match>
					    </td>
					      <td id="vaccineDob" style="padding:0 0 0 18px;display:none "><label style="vertical-align:top" class="formLabel">Vaccination Date:</label>
								   
								     <html:text property="vaccineDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
						<logic:match name="viewmode" value="false">
						<A NAME="CalendarObjectvaccineDate" ID="CalendarObjectvaccineDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectvaccineDate','forms[1].vaccineDate',document.forms[1].vaccineDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
						</logic:match>
						</td>
					</logic:notMatch>
		</tr>
		</table>
		</td><!--added for maternity  -->
		</tr>
		<tr id="noofchildren" style="display: ">
	         <td class="formLabel">No. of Living Children: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	       <tr>
	        <td>
	          <html:select property="livingChildrenNumber" name="frmAilmentDetails"  style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
		         <html:option value="">Select from list</html:option>
		      	 <html:option value="0">0</html:option>
		      	 <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
		         <html:option value="10">10</html:option>
		      </html:select>
		   </td>
		</tr>
		</table>
		</td>
		</tr>
					<%-- added for maternity new--%>
	<tr id="noofbiological" style="display: ">
	         <td class="formLabel">No. of Biological  Children: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	       <tr>
	        <td>
	          <html:select property="biologicalChildrenNumber" name="frmAilmentDetails"  style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
		         <html:option value="">Select from list</html:option>
		      	 <html:option value="0">0</html:option>
		      	 <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
		         <html:option value="10">10</html:option>
		      </html:select>
		   </td>
		</tr>
			</table>
		</td>
		</tr>
		<tr id="noofadopted" style="display: ">
	         <td class="formLabel">No. of Adopted Children: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	       <tr>
	        <td>
	          <html:select property="adoptedChildrenNumber" name="frmAilmentDetails"  style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
		         <html:option value="">Select from list</html:option>
		      	 <html:option value="0">0</html:option>
		      	 <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
		         <html:option value="10">10</html:option>
		      </html:select>
		   </td>
		</tr>
			</table>
		</td>
		</tr>
		<tr id="deliveriesNo" style="display: ">
          <td class="formLabel">No Of Deliveries : <span class="mandatorySymbol">*</span></td>
          <td valign="middle" class="textLabel">
          <html:text property="noOfDeliveries" name="frmAilmentDetails" styleClass="textBox textBoxTiny" maxlength="5" onchange="javascript:computeDischargeDate(this);" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
          </td>
        </tr> 

		<%--end  added for maternity new--%>
		<!--added for maternity  -->
			<tr id="lmpdate" style="display: ">
          <td class="formLabel">LMP: </td>
          <td class="formLabel">
          <html:text property="lmpDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
        <logic:match name="viewmode" value="false">
		<A NAME="CalendarObjectlmpDate" ID="CalendarObjectlmpDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectlmpDate','forms[1].lmpDate',document.forms[1].lmpDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
		</logic:match>
		</td>
        </tr>
		</logic:match>  
		<!--added for maternity  -->
	    <logic:notMatch name="frmAilmentDetails" property="specialityTypeID" value="MAS" >
		<tr id="maternitytype" style="display: none">
	         <td class="formLabel">Maternity Type: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	      <!--added for maternity  -->
	       <tr>
	        <td>
	          <html:select property="maternityTypeID" name="frmAilmentDetails" onchange="showHideType1();"  style="width:200px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
		          <html:option value="">Select from list</html:option>
		          <html:options collection="MaternityType" property="cacheId" labelProperty="cacheDesc"/>
		      </html:select>
		   </td>
		       <logic:match name="frmAilmentDetails" property="maternityTypeID" value="NBB" >
					<td id="childDob" style="padding:0 0 0 18px;display: "><label style="vertical-align:top" class="formLabel">Child DOB:<span class="mandatorySymbol">*</span></label>
					   
					     <html:text property="childDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
							<logic:match name="viewmode" value="false">
				            	<A NAME="CalendarObjectchildDate" ID="CalendarObjectchildDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectchildDate','forms[1].childDate',document.forms[1].childDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
				           </logic:match>
					    </td>
					      <td id="vaccineDob" style="padding:0 0 0 18px;display: "><label style="vertical-align:top" class="formLabel">Vaccination Date:</label>
								   
								     <html:text property="vaccineDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
						<logic:match name="viewmode" value="false">
						<A NAME="CalendarObjectvaccineDate" ID="CalendarObjectvaccineDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectvaccineDate','forms[1].vaccineDate',document.forms[1].vaccineDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
						</logic:match>
						</td>
					 </logic:match>
			<logic:notMatch name="frmAilmentDetails" property="maternityTypeID" value="NBB" >
				<td id="childDob" style="padding:0 0 0 18px;display: none">
					    <label style="vertical-align:top" class="formLabel">Child DOB:<span class="mandatorySymbol">*</span></label>
					     <html:text property="childDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
							<logic:match name="viewmode" value="false">
				            	<A NAME="CalendarObjectchildDate" ID="CalendarObjectchildDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectchildDate','forms[1].childDate',document.forms[1].childDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
				           </logic:match>
					    </td>
					      <td id="vaccineDob" style="padding:0 0 0 18px;display:none "><label style="vertical-align:top" class="formLabel">Vaccination Date:</label>
								   
								     <html:text property="vaccineDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
						<logic:match name="viewmode" value="false">
						<A NAME="CalendarObjectvaccineDate" ID="CalendarObjectvaccineDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectvaccineDate','forms[1].vaccineDate',document.forms[1].vaccineDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
						</logic:match>
						</td>
				</logic:notMatch>
		</tr>
		</table>
		</td>
		</tr><!--added for maternity  -->
		<tr id="noofchildren" style="display: none">
	         <td class="formLabel">No. of Living Children: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	       <tr>
	        <td>
	          <html:select property="livingChildrenNumber" name="frmAilmentDetails"  style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
		         <html:option value="">Select from list</html:option>
		      	 <html:option value="0">0</html:option>
		      	 <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
		         <html:option value="10">10</html:option>
		      </html:select>
		   </td>
		</tr>
		</table>
		</td>
		</tr>
					<%-- added for maternity new--%>
		<tr id="noofbiological" style="display: none">
	         <td class="formLabel">No. of Biological  Children: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	       <tr>
	        <td>
	          <html:select property="biologicalChildrenNumber" name="frmAilmentDetails"  style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
		         <html:option value="">Select from list</html:option>
		      	 <html:option value="0">0</html:option>
		      	 <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
		         <html:option value="10">10</html:option>
		      </html:select>
		   </td>
		</tr>
			</table>
		</td>
		</tr>
		<tr id="noofadopted" style="display: none">
	         <td class="formLabel">No. of Adopted Children: <span class="mandatorySymbol">*</span></td>
	         <td  class="formLabel" ><table cellpadding="0" cellspacing="0">
	       <tr>
	        <td>
	          <html:select property="adoptedChildrenNumber" name="frmAilmentDetails"  style="width:115px;"  styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>">
		         <html:option value="">Select from list</html:option>
		      	 <html:option value="0">0</html:option>
		      	 <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
		         <html:option value="10">10</html:option>
		      </html:select>
		   </td>
		</tr>
			</table>
		</td>
		</tr>
		<tr id="deliveriesNo" style="display: none">
          <td class="formLabel">No Of Deliveries : <span class="mandatorySymbol">*</span></td>
          <td valign="middle" class="textLabel">
          <html:text property="noOfDeliveries" name="frmAilmentDetails" styleClass="textBox textBoxTiny" maxlength="5" onchange="javascript:computeDischargeDate(this);" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
          </td>
        </tr> 

			<%--end  added for maternity new--%>
		<!--added for maternity  -->
		<tr id="lmpdate" style="display: none">
          <td class="formLabel">LMP: </td>
          <td class="formLabel">
          <html:text property="lmpDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
        <logic:match name="viewmode" value="false">
		<A NAME="CalendarObjectlmpDate" ID="CalendarObjectlmpDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectlmpDate','forms[1].lmpDate',document.forms[1].lmpDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
		</logic:match>
		</td>
        </tr><!--added for maternity  -->
		</logic:notMatch> 
	        <logic:match name="frmAilmentDetails" property="specialityTypeID" value="TRA">
		        <tr id="disablity" style="display:">
		          <td class="formLabel">Disability Type:</td>
		          <td class="formLabel">
		          <html:select property="disabilityTypeID" name="frmAilmentDetails" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
		          	<html:option value="">Select from list</html:option>
				    <html:options collection="disabilityType" property="cacheId" labelProperty="cacheDesc"/>
				  </html:select>
		 		</td>
		        </tr>
		   </logic:match>
		   <logic:notMatch name="frmAilmentDetails" property="specialityTypeID" value="TRA">
		        <tr id="disablity" style="display:none;">
		          <td class="formLabel">Disability Type:</td>
		          <td class="formLabel">
		          <html:select property="disabilityTypeID" name="frmAilmentDetails" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
		          	<html:option value="">Select from list</html:option>
				    <html:options collection="disabilityType" property="cacheId" labelProperty="cacheDesc"/>
				  </html:select>
		 		</td>
		        </tr>
		   </logic:notMatch>      
        </logic:match>
        <tr><!-- mandatorySymbol added for koc 1075 PED -->
          <td class="formLabel">Since When: <span class="mandatorySymbol">*</span></td>
            <td class="formLabel">
             <table cellpadding="1" cellspacing="0">
              <tr>
               <td><html:text property="duration" name="frmAilmentDetails" styleClass="textBox textBoxTiny" maxlength="5"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
               <td><html:select property="durationTypeId" name="frmAilmentDetails" styleClass="selectBox" disabled="<%=viewmode%>">
		        <html:options collection="durationType" property="cacheId" labelProperty="cacheDesc"/>
				</html:select></td>
              </tr>		
   	     </table>
   	    </td>
        </tr>
        <logic:match name="strLink" value="Pre-Authorization" >
        <tr>
          <td class="formLabel">Findings:</td>
            <td class="formLabel">
            <html:textarea property="clinicalFindings" name="frmAilmentDetails" styleClass="textBox textAreaMediumht"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
		 </td>
        </tr>
		<tr>
          <td class="formLabel">Provisional Diagnosis: <span class="mandatorySymbol">*</span></td>
            <td class="formLabel">
            <html:textarea property="provisionalDiagnosis" name="frmAilmentDetails" styleClass="textBox textAreaMediumht"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
            </td>
        </tr>
        <tr>
	        <td nowrap class="formLabel">Is Ailment a Complication of Previous Illness:</td>
	        <td valign="middle" class="textLabel">
	        <input name="previousIllYN" type="checkbox" value="Y" readonly="<%=viewmode%>" <logic:match name="frmAilmentDetails" property="previousIllnessYN" value="Y">checked</logic:match> >
	        </td>
	        <td  width="100%">&nbsp;</td>
        </tr>
        <input type="hidden" name="previousIllnessYN" value=""/>
        </logic:match>
        <logic:match name="strLink" value="Claims" >
        <tr>
          <td class="formLabel">Final Diagnosis: <span class="mandatorySymbol">*</span></td>
            <td class="formLabel">
            <html:textarea property="finalDiagnosis" name="frmAilmentDetails" styleClass="textBox textAreaMediumht"  disabled="<%=true%>" readonly="<%=viewmode%>"/>
		 </td>
        </tr>
        <tr>
          <td class="formLabel">History:</td>
            <td class="formLabel">
            <html:textarea property="history" name="frmAilmentDetails" styleClass="textBox textAreaMediumht"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
		 </td>
        </tr>
        <tr>
          <td class="formLabel">Family History:</td>
            <td class="formLabel">
            <html:textarea property="familyHistory" name="frmAilmentDetails" styleClass="textBox textAreaMediumht"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
		 </td>
        </tr>
        </logic:match>
        <tr>
          <td class="formLabel">Plan of Treatment: <span class="mandatorySymbol">*</span></td>
          <td class="formLabel">
          <html:select property="treatmentPlanTypeID" name="frmAilmentDetails" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
          	<html:option value="">Select from list</html:option>
		    <html:options collection="treatmentPlan" property="cacheId" labelProperty="cacheDesc"/>
		  </html:select>
 		</td>
        </tr>
        <logic:match name="strLink" value="Pre-Authorization" >
		<tr>
          <td class="formLabel">Special Investigation:</td>
            <td class="formLabel">
            <textarea name="preInvestReports" class="textBox textAreaMediumht"  <%=perMode %>><bean:write name='frmAilmentDetails' property='investReports'/></textarea>
            </td>
        </tr>
        </logic:match>
        <tr>
          <td class="formLabel">Details of Treatment:</td>
            <td class="formLabel">
            <html:textarea property="lineOfTreatment" name="frmAilmentDetails" styleClass="textBox textAreaMediumht"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
            </td>
        </tr>
		<logic:match name="strLink" value="Claims" >
		<tr>
          <td class="formLabel">Date of Surgery: </td>
          <td class="formLabel">
          <html:text property="surgeryDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
        <logic:match name="viewmode" value="false">
		<A NAME="CalendarObjectsurgeryDate" ID="CalendarObjectsurgeryDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectsurgeryDate','forms[1].surgeryDate',document.forms[1].surgeryDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
		</logic:match>
		</td>
        </tr>
        
        <!-- Mandatory only for Critical Ailment added for KOC-1273-->
       
        <logic:match name ="frmAilmentDetails" property="showDiagnosisYN" value="Y">
        <tr>
        <td class="formLabel">Date of Diagnosis: <span class="mandatorySymbol">*</span></td>
        <td class="formLabel">
        <html:text property="diagnosisDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"/>&nbsp;
        <logic:match name="viewmode" value="false">
        <A NAME="CalendarObjectdiagnosisDate" ID="CalendarObjectdiagnosisDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectdiagnosisDate','forms[1].diagnosisDate',document.forms[1].diagnosisDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="diagnosisDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
		</logic:match>
        </td>
        </tr>
        </logic:match>
        
        <!-- Mandatory only for Critical Ailment added for KOC-1273-->
        
         <logic:match name="frmAilmentDetails" property="showCertificateDateYN" value="Y"> 
       <tr>
         <td class="formlabel">Date of Certificate:  <span class="mandatorySymbol" >*</span></td>
         <td class="formlabel">
         <html:text property="certificateDate" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"/>&nbsp;
         <logic:match name="viewmode" value="false">
         <A NAME="CalendarObjectcertificateDate" ID="CalendarObjectcertificateDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectcertificateDate','forms[1].certificateDate',document.forms[1].certificateDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="certificateDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
         </logic:match>
         </td>
        </tr>
         </logic:match>         
        <!-- Ended -->   
		
		 <!--  <tr>
          <td class="formLabel">C: </td>
          <td class="formLabel">
          <html:text property="C" name="frmAilmentDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
        <logic:match name="viewmode" value="false">
		<A NAME="CalendarObjectC" ID="CalendarObjectC" HREF="#" onClick="javascript:show_calendar('CalendarObjectC','forms[1].C',document.forms[1].C.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;
		</logic:match>
		</td>
        </tr> -->
		 
		
        
        <tr>
          <td class="formLabel">Investigation:</td>
            <td class="formLabel">
            <textarea name="clmInvestReports" class="textBox textAreaMediumht" <%=perMode %>><bean:write name='frmAilmentDetails' property='investReports'/></textarea>
            </td>
        </tr>
		<tr>
          <td class="formLabel">Condition on Discharge:</td>
          <td class="formLabel">

          <html:select property="dischrgConditionType" styleClass="selectBox" disabled="<%=viewmode%>">
		        <html:options collection="dischargeCondition" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>
		</td>
        </tr>
        <tr>
          <td class="formLabel">Advice to Patient:</td>
          <td class="formLabel">
          <html:textarea property="advToPatient" name="frmAilmentDetails" styleClass="textBox textAreaMediumht"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
          </td>
        </tr>
        <tr>
          <td class="formLabel">Doctor Opinion:</td>
          <td class="formLabel">
          <html:textarea property="docOpinion" name="frmAilmentDetails" styleClass="textBox textAreaMediumht"  disabled="true" readonly="true"/>
          </td>
        </tr>
       <!--  added for koc1310-->
        <tr>
	    	<td height="20" class="formLabel">Medical Review:</td>
	    	<td class="formLabel">
	      		<html:checkbox name="frmAilmentDetails" property="cancerMedicalReview" value="Y"/>	
	      		<html:hidden name="frmAilmentDetails" property="cancerMedicalReview" value="N"/>			
			</td>
	    
	    </tr>
        <!-- physiotherapy cr 1320 -->
        <logic:equal name="strLink" value="Claims" >
        <tr>
            <td nowrap class="formLabel">Document for Medical necessity treatment: </td>
	        <td valign="bottom" nowrap class="formLabel">
	        <input name="physiotherapyYN" type="checkbox" value="Y" onclick="showhidePhysioApply()" readonly="<%=viewmode%>" <logic:match name="frmAilmentDetails" property="physiotherapyChargeYN" value="Y">checked</logic:match> >
	        </td>
	        <td  width="100%">&nbsp;</td>
        </tr>
        <input type="hidden" name="physiotherapyChargeYN" value=""/>
        <logic:match name="frmAilmentDetails" property="physiotherapyChargeYN" value="Y" >
        	<tr id="PHYS" style="display:">
        </logic:match>
        <logic:notMatch name="frmAilmentDetails" property="physiotherapyChargeYN" value="Y" >
        	<tr id="PHYS" style="display:none;">
        </logic:notMatch>
          		<td class="formLabel">Received valid document for necessity treatment: </td>
          		<td class="formLabel">
          			<html:select property="physioApplicableID" name="frmAilmentDetails" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
						<html:option value="">Select from list</html:option>
		      			<html:option value="Agree">Agree</html:option>
		      	 		<html:option value="Disagree">Disagree</html:option>
		  			</html:select>
 		 		</td>
        	</tr>
        	</logic:equal>
        <!-- physiotherapy cr 1320 -->
        
        <!--  added for donor expenses-->
          <tr>
	 		<td class="formLabel" width="20%">Recepient Claim (Yes/No):</td>	 				
	  				<td width="4%">	  				
	 					<html:checkbox name="frmAilmentDetails" property="donorExpYN"  value="Y" styleId="donorExpYN" onclick="showHideType2()" />
	 					<input type="hidden" name="donorExpYN" value="N">	 					
	 				</td>
	 				<td>&nbsp;</td>
	     	<td>&nbsp;</td>
	     		<html:hidden property="donorExpenses" /> <!--  added for donor expenses-->
	 	</tr>
	 	
		 <logic:match name="frmAilmentDetails" property="donorExpYN" value="Y" >
		 <tr id="ttkid" style="display: ">
          <td class="formLabel">Donor Healthcare Administrator Id : </td>
          <td valign="middle" class="textLabel"><!--
          	<input name="frmAilmentDetails"  property="ttkNO"  type="text" maxlength="60" class="textBox textBoxMedium" onchange="javascript:computeDischargeDate(this);" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
         --><html:text property="ttkNO" name="frmAilmentDetails" styleClass="textBox textBoxMedium " maxlength="60"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
          
          </td>
        </tr> 
		   
		 </logic:match>
		<logic:notMatch name="frmAilmentDetails" property="donorExpYN" value="Y" >
		<tr id="ttkid" style="display:none ">
          <td class="formLabel">Donor Healthcare Administrator Id :</td>
          <td valign="middle" class="textLabel">
          <html:text property="ttkNO" name="frmAilmentDetails" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
          
          	<!--<input name="frmAilmentDetails"  property="ttkNO"  type="text" maxlength="60" class="textBox textBoxMedium" onchange="javascript:computeDischargeDate(this);" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        --></tr> 
	   </logic:notMatch>
	 	
	 	<!-- End  added for donor expenses-->
		</logic:match>
		<logic:match name="strLink" value="Pre-Authorization" >
        <tr>
          <td class="formLabel">Duration of Hospitalization (Days): <span class="mandatorySymbol">*</span></td>
          <td valign="middle" class="textLabel">
          <html:text property="hospitalizationDays" name="frmAilmentDetails" styleClass="textBox textBoxTiny" maxlength="5" onchange="javascript:computeDischargeDate(this);" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
          </td>
        </tr> 
        <tr>
          <td class="formLabel">Probable Date of Discharge:</td>
          <td valign="middle" class="textLabel">
          	<html:text property="probableDischargeDate" name="frmAilmentDetails" styleClass="textBoxDisabled textDate" maxlength="10" disabled="<%=viewmode%>" tabindex="-1" readonly="true"/>
          </td>
        </tr>
     <!--  added for koc1310-->
        <tr>
	    	<td height="20" class="formLabel">Medical Review:</td>
	    	<td class="formLabel">
	      		<html:checkbox name="frmAilmentDetails" property="cancerMedicalReview" value="Y"/>	
	      		<html:hidden name="frmAilmentDetails" property="cancerMedicalReview" value="N"/>			
			</td>
	    </tr>
        </logic:match>
	</table>
	</fieldset>
<fieldset>
<legend>Associated Illness</legend>
  	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="10" colspan="2" class="textLabelBold"></td>
          </tr>
        <tr>
          <td width="35%" height="20" class="textLabelBold">Disease Name </td>
          <td width="65%" class="textLabelBold">Duration</td>
        </tr>
 <logic:notEmpty property="assocIllnessList" name="frmAilmentDetails">
   <logic:iterate id="assocIllness" property="assocIllnessList" name="frmAilmentDetails" >
        <tr>
          <td class="formLabel"><bean:write property="assocIllTypeDesc" name="assocIllness"/></td>
			<td class="formLabel">
				<table cellpadding="1" cellspacing="0">
					<tr>
						<td><html:text property="assocIllDuration" name="assocIllness" styleClass="textBox textBoxTiny" maxlength="11"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
						<td><html:select property="durationTypeID" name="assocIllness" styleClass="selectBox" disabled="<%=viewmode%>">
		        				<html:options collection="durationType" property="cacheId" labelProperty="cacheDesc"/>
							</html:select></td>
					</tr>
				</table>
            </td>
            </tr>
		<input type="hidden" name="assocIllTypeDesc" value="<bean:write property='assocIllTypeDesc' name='assocIllness'/>">
        <input type="hidden" name="selectedAssocSeqID" value="<bean:write property='assocSeqID' name='assocIllness'/>">
        <!-- input type="hidden" name="selectedPreAuthSeqID" value="<bean:write property='preAuthSeqID' name='assocIllness'/>"-->
        <input type="hidden" name="selectedAssocIllTypeID" value="<bean:write property='assocIllTypeID' name='assocIllness'/>">
  </logic:iterate>
</logic:notEmpty>
    </table>

	</fieldset>
	<!-- added for KOC-Decoupling -->
<logic:match name="strLink" value="Claims" >	
<fieldset>
	<legend>Medical Diagnosis Information</legend>
	
	<ttk:HtmlGrid name="DiagnosisTable"/>
	
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	<tr>	
	<td class="formLabel">Diagnosis: <span class="mandatorySymbol">*</span></td>
	<td><html:textarea name="frmAilmentDetails" property="diagnosisDesc"  styleClass="textBox textAreaMediumht" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
	</tr>

	<tr>
	<td class="formLabel">AilmentType: <span class="mandatorySymbol">*</span></td>
	<td>
      <html:select name="frmAilmentDetails" property="diagnosisType"  styleClass="selectBox" disabled="<%=viewmode%>">          
	  <html:option value="PRE">Primary</html:option>
	  <html:option value="SEC">Secondary</html:option>
	  <html:option value="COM">Comorbidities</html:option>
	  </html:select>
	</td>
	</tr>
	
	<tr>
	<td class="formLabel">Hospitalization Type: <span class="mandatorySymbol"></span></td>
	<td>
      <html:select name="frmAilmentDetails" property="diagHospGenTypeId"  styleClass="selectBox" onchange="showhideHospType()" disabled="<%=viewmode%>">          
	  <html:option value="" >Select from list</html:option>
      <html:options collection="listHospitalizationType"  property="cacheId" labelProperty="cacheDesc"/>
	  </html:select>
	</td>
	</tr>	
  
	<logic:match name="frmAilmentDetails" property="diagHospGenTypeId" value="REP" >
	<tr id="FV" style="display:">
	<td  width="25%" class="formLabel">Frequency of Visits: <span class="mandatorySymbol">*</span></td>
	<td width="75%">
    	<html:text styleClass="textBox textBoxTiny" name="frmAilmentDetails" property="freqOfVisit" onkeypress="return isNumeric(this);" maxlength="5" disabled="<%=viewmode%>"/>
    	<html:select property="freqVisitTypeId"  styleClass="selectBox" disabled="<%=viewmode%>">
		<html:options collection="listDurationType"  property="cacheId" labelProperty="cacheDesc"/>
		</html:select>	
    </td>    
    </tr>
   </logic:match>
   <logic:notMatch name="frmAilmentDetails" property="diagHospGenTypeId" value="REP" >
	<tr id="FV" style="display:none;">
	<td  width="25%" class="formLabel">Frequency of Visits: <span class="mandatorySymbol">*</span></td>
	<td width="75%">
    	<html:text styleClass="textBox textBoxTiny" name="frmAilmentDetails" property="freqOfVisit" onkeypress="return isNumeric(this);" maxlength="5" disabled="<%=viewmode%>"/>
    	<html:select property="freqVisitTypeId"  styleClass="selectBox" disabled="<%=viewmode%>">
		<html:options collection="listDurationType"  property="cacheId" labelProperty="cacheDesc"/>
		</html:select>	
    </td>    
    </tr>
     </logic:notMatch>
	
    
  	<logic:match name="frmAilmentDetails" property="diagHospGenTypeId" value="REP" >
    <tr id="NV" style="display:">
	<td  width="25%" class="formLabel">No.of Visits: <span class="mandatorySymbol">*</span></td>
	<td width="75%"><html:text styleClass="textBox textBoxTiny" name="frmAilmentDetails" property="noOfVisits" onkeypress="return isNumeric(this);" maxlength="5" disabled="<%=viewmode%>"/></td>
	</tr>	
	</logic:match>
		<logic:notMatch name="frmAilmentDetails" property="diagHospGenTypeId" value="REP" >
    <tr id="NV" style="display:none;">
	<td  width="25%" class="formLabel">No.of Visits: <span class="mandatorySymbol">*</span></td>
	<td width="75%"><html:text styleClass="textBox textBoxTiny" name="frmAilmentDetails" property="noOfVisits" onkeypress="return isNumeric(this);" maxlength="5" disabled="<%=viewmode%>"/></td>
	</tr>	
	</logic:notMatch>
	
	<tr>
	 <td width="25%" class="formLabel">Treatment Plan: <span class="mandatorySymbol"></span></td>
      <td  width="75%" class="formLabel">
        <html:select property="diagTreatmentPlanGenTypeId"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="showhideTariffType(this)">
          <html:option value="" >Select from list</html:option>
          <html:options collection="listTreatmentPlan"  property="cacheId" labelProperty="cacheDesc"/>
        </html:select>
      </td>
	</tr>
	 
	
    
    <logic:match name="frmAilmentDetails" property="diagTreatmentPlanGenTypeId" value="SUR" >
	 <tr id="Tariff" style="display:">
       <td width="25%" class="formLabel">Tariff Item:</td>
       <td width="75%" class="formLabelBold">
         <html:select name = "frmAilmentDetails" property="tariffGenTypeId"  styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>">
          <html:options collection="listGeneralCodePlan"  property="cacheId" labelProperty="cacheDesc"/>
        </html:select>
       </td>
    </tr>
	   </logic:match>    
	   <logic:notMatch name="frmAilmentDetails" property="diagTreatmentPlanGenTypeId" value="SUR" >
	 <tr id="Tariff" style="display:none;">
       <td width="25%" class="formLabel">Tariff Item:</td>
       <td width="75%" class="formLabelBold">
         <html:select name = "frmAilmentDetails" property="tariffGenTypeId"  styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>">
          <html:options collection="listGeneralCodePlan"  property="cacheId" labelProperty="cacheDesc"/>
        </html:select>
       </td>
    </tr>
	   </logic:notMatch>                                                                                                                                                                	
	<logic:notMatch name="frmAilmentDetails" property="medCompleteYN" value="Y">		
	<tr>		
	<td><button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveDiagnosis();"><u>A</u>dd Diagnosis</button>&nbsp;
	</td>
	</tr>
	</logic:notMatch>
	</table>
</fieldset>
</logic:match>
<!-- Ended Decoupling -->


	 <!-- S T A R T : Form Fields -->
	 <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <logic:match name="strLink" value="Pre-Authorization" >
    <%
     if(TTKCommon.isAuthorized(request,"Edit"))
     {
    %>
    <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
    <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
    <%} %>
    </logic:match>
      <logic:match name="strLink" value="Claims">
      <%
          if(ClaimsWebBoardHelper.getAmmendmentYN(request).equals("N"))
	      {
	          if(TTKCommon.isAuthorized(request,"Edit"))
	          {
      %>	
    <button type="button" name="Button3" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
    <button type="button" name="Button4" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
    <%
          	  }
          }//end of if(ClaimsWebBoardHelper.getAmmendmentYN(request).equals("N"))
      %>
    </logic:match>
	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
    </td>
  </tr>
</table>
	<!-- E N D : Buttons -->
	</div>
	<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->
	<input type="hidden" name="mode" value=""/>
	<input type="hidden" name="child" value="Ailment Details">
	<html:hidden property="caption" />
	<html:hidden property="dischrgConditionType" />
	<html:hidden property="linkMode" />
	<html:hidden property="investReports" />
	<html:hidden property="claimSeqID"/>
	<html:hidden property="admissionDate" />
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<html:hidden property="nuOfVisits" />    	<!-- physiotherapy cr 1307 -->
	<html:hidden property="claimSubTypeID" />    	<!-- physiotherapy cr 1307 -->
	  <script>
    stopClaim();//<!--  added for donor expenses-->
    </script>
	<html:hidden property="showCertificateDateYN" />
	<html:hidden property="showDiagnosisYN" />
			
	</html:form>