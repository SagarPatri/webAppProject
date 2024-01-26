<%
/**
 * @ (#) managerates.jsp 21th Oct 2005
 * Project      : TTK HealthCare Services
 * File         : managerates.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : 21th Oct 2005
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ page import="com.ttk.common.TTKCommon,java.util.ArrayList,com.ttk.dto.empanelment.InsuranceVO"%>
<%@ page import="com.ttk.dto.administration.RateVO,com.ttk.dto.administration.PlanPackageVO"%>
<%@ page import="com.ttk.common.security.Cache,com.ttk.dto.administration.RevisionPlanVO"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	ArrayList alWardCode=Cache.getCacheObject("medicalWardCode");
	pageContext.setAttribute("alWardCode",alWardCode);
	ArrayList alNonWardCode=Cache.getCacheObject("nonMedicalWardCode");
	pageContext.setAttribute("alNonWardCode",alNonWardCode);
	ArrayList alRates=(ArrayList)request.getAttribute("alRates");

	PlanPackageVO planPackageVO=(PlanPackageVO)session.getAttribute("planPackageVO");
	RevisionPlanVO revisePlanVO=(RevisionPlanVO)session.getAttribute("RevisionPlanVO");
	InsuranceVO insuranceVO=(InsuranceVO)request.getSession().getAttribute("insuranceVO");

	String strActiveLink=TTKCommon.getActiveLink(request);
	RateVO rateVo=new RateVO();
	//get the General type from insuranceVO
	String strGeneralCode="";
	if(insuranceVO!=null)
		strGeneralCode=insuranceVO.getGenTypeID();

	String strViewMode="disabled";
	boolean viewmode=true;
    if(TTKCommon.isAuthorized(request,"Edit"))
    {
    	strViewMode="";
    	viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/managerates.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/SaveTarifPackageAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
    	<td>Manage Rates - <%=strActiveLink.equals("Empanelment")? "["+TTKCommon.getWebBoardDesc(request)+"]" : "" %><%=insuranceVO!=null? "["+insuranceVO.getCompanyName()+"]["+insuranceVO.getProdPolicyNumber()+"]" :"" %>[<%=revisePlanVO.getTariffPlanName()%>] [<%=planPackageVO.getName()%>]</td>
		 <td width="5%" align="right" class="webBoard">&nbsp;</td>
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
    <fieldset>
		<legend>Validity Period</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	    	    <tr>
	      		  <td class="formLabel" width="22%">Start Date: </td>
			      <td class="textLabelBold" width="22%"><%=TTKCommon.getFormattedDate(revisePlanVO.getStartDate())%></td>
		    	  <td class="formLabel" width="20%">End Date: </td>
				  <td class="textLabelBold"><%=TTKCommon.getFormattedDate(revisePlanVO.getEndDate())%></td>
		      </tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>Rate Details</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		      <tr>
	         	<logic:match name="frmRates" property="package" value="NPK">
	         		<td class="formLabel" width="22%">Ward Charges:</td>
	         		<td colspan="3">
	         	  		<html:select property="wardCode" styleClass="selectBox selectBoxMedium" onchange="javascript:onWardChanged()" disabled="<%=viewmode%>">
							<html:option value="">--Select--</html:option>
							<logic:match name="frmRates" property="medicalPackageYn" value="Y">
								<html:options collection="alWardCode" property="cacheId" labelProperty="cacheDesc"/>
							</logic:match>
							<logic:notMatch name="frmRates" property="medicalPackageYn" value="Y">
								<html:options collection="alNonWardCode" property="cacheId" labelProperty="cacheDesc"/>
							</logic:notMatch>
						</html:select>
					</td>
		   		</logic:match>
		       </tr>
			   <tr>
			        <td colspan="4" height="10">&nbsp;</td>
	           </tr>
		       <tr>
	 		   <logic:notEmpty name="alRates">
			      	<td class="formLabelBold" width="22%">Room Type</td>
			        <td class="formLabelBold" width="22%">Rate (Rs)</td>
			         <%
				       	if(strActiveLink.equals("Empanelment")&& strGeneralCode.equals("ART"))
			    	    {
				     %>
		    	  	 		<td class="formLabelBold" width="47%">Discount (%)</td>
				     <%
				       	}//end of inner if
				   	 %>
              </logic:notEmpty>
		      </tr>
				<logic:notEmpty name="alRates">
		        <logic:iterate id="rateVO" name="alRates">
		        	<tr>
				        <td><bean:write name="rateVO" property="roomDesc"/></td>
				        <td><input name="rate" type="text" class="textBox textBoxSmall" maxlength="11" <%=strViewMode%>  value="<bean:write name="rateVO" property="rate"/>"></td>
				        <%
	        				if(strActiveLink.equals("Empanelment")&& strGeneralCode.equals("ART"))
			        		{
	        			%>
	        				 <td><input name="discount" type="text" class="textBox textBoxSmall"  maxlength="5" <%=strViewMode%> value="<bean:write name="rateVO" property="disctOnServices"/>"></td>
	        			<%
	        				}
	        			%>
            			<td>&nbsp;</td>
				   </tr>
			   	   <INPUT TYPE="hidden" NAME="pkgCostSeqId" VALUE="<bean:write name="rateVO" property="pkgCostSeqId"/>">
				   <INPUT TYPE="hidden" NAME="pkgDetailSeqId" VALUE="<bean:write name="rateVO" property="pkgDetailSeqId"/>">
		        </logic:iterate>
		        </logic:notEmpty>
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
		    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		    </td>
		  </tr>
		</table>
	</div>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<input type="hidden" name="child" value="Managerates">
	<input type="hidden" name="defaultPlan" value="<%=strGeneralCode%>">
	<INPUT TYPE="hidden" NAME="pageName" VALUE="<%=strActiveLink%>">
	<INPUT TYPE="hidden" NAME="pkgSeqId" VALUE="<bean:write name="frmRates" property="pkgSeqId"/>">
	<INPUT TYPE="hidden" NAME="generalTypeid" VALUE="<bean:write name="frmRates" property="generalTypeid"/>"><!-- to check package or non package -->
	<INPUT TYPE="hidden" NAME="wardTypeId" VALUE="<bean:write name="frmRates" property="wardTypeId"/>">	<!-- ward type id for medical or non medical -->
	<INPUT TYPE="hidden" NAME="selRevPlanSeqId" VALUE="<bean:write name="frmRates" property="selRevPlanSeqId"/>">
	<INPUT TYPE="hidden" NAME="revisePlanSeqId" VALUE="<bean:write name="frmRates" property="revisePlanSeqId"/>">
	<INPUT TYPE="hidden" NAME="package" VALUE="<bean:write name="frmRates" property="package"/>">
</html:form>


