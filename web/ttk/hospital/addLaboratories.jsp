<%@page import="com.ttk.dto.empanelment.LaboratoryServicesVO"%>
<%
/** @ (#) addLaboratories.jsp 
 * Project     : TTK Healthcare Services
 * File        : addLaboratories.jsp
 * Author      : kishor kumar 
 * Company     : RCS Technologies
 * Date Created: 23/03/2015
 *
 * @author 		 : kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
 <%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
 <%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
 <%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
 <%@ page import="java.util.ArrayList, com.ttk.dto.empanelment.InsuranceVO,com.ttk.common.TTKCommon" %>
<script language="javascript" src="/ttk/scripts/hospital/addLaboratories.js"></script>
<head>
	<link rel="stylesheet" type="text/css" href="ttk/styles/style.css" />
	<link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
<link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript">

function toggleMe(a,b){
	var e=document.getElementById(a);
	if(!e)return true;
	if(e.style.display=="none"){
	e.style.display="block";
	}
	else{
	e.style.display="none";
	}
	if(document.getElementById(b).innerHTML	==	"+")
		document.getElementById(b).innerHTML	=	"-";
	else
		document.getElementById(b).innerHTML	=	"+";
return true;
}


</script>

<style>
a{
	text-decoration: none
}
</style>

<%
	boolean viewmode=true;
	String strBoolean = "disabled";
	if(TTKCommon.isAuthorized(request,"Edit"))
		{
			viewmode=false;
			strBoolean = "";
		}
%>
<html:form action="/OnlineCashlessAllPrecriptionAction.do" >
<body id="pageBody">
<div class="contentArea" id="contentArea">

			<!-- S T A R T : Content/Form Area -->
			<div
				style="background-image: url('/ttk/images/Insurance/content.png'); background-repeat: repeat-x;">
				<div class="container" style="background: #fff;">

					<div class="divPanel page-content">
						<!--Edit Main Content Area here-->
						<div class="row-fluid">

							<!-- S T A R T : Page Title -->
							<div class="span8">
								<!-- <div id="navigateBar">Home > Corporate > Detailed > Claim Details</div> -->
								<div id="contentOuterSeparator"></div>
								<h4 class="sub_heading">Routine Investigations </h4>
								<html:errors />
								<div id="contentOuterSeparator"></div>
							</div>

						</div>
						<div class="row-fluid">
							<div style="width: 100%;">
								<div class="span12" style="margin: 0% 0%">

	<html:errors/>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
	<div class="contentArea" id="contentArea">
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
	<%
	String colorType ="",questionDescTemp ="";
   	pageContext.setAttribute("questionDescTemp",questionDescTemp);
   	int colorCode =3;
	pageContext.setAttribute("colorCode",new Integer(colorCode));
	ArrayList hospLabs	=	(ArrayList)request.getAttribute("hospLabs");
	int k=0,l=0;
   	%>
   	
   	<table class="gridWithCheckBox" style="width:85%" border="0" cellpadding="0">
   	<%
		LaboratoryServicesVO laboratoryServicesVO	=	null;
   		String tempGroup	=	"";
   		for(k=0; k<hospLabs.size(); k++)
   		{
   			laboratoryServicesVO	=	(LaboratoryServicesVO)hospLabs.get(k);
   			if(!tempGroup.equals(laboratoryServicesVO.getGroupName()))
   			{
   	   			tempGroup				=	laboratoryServicesVO.getGroupName();
		%>
   			<tr>
   				<td align="center" class="gridHeader" colspan="6"><%= laboratoryServicesVO.getGroupName() %>
<%--     	      &nbsp;&nbsp;&nbsp;&nbsp; <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddLabs('<%= laboratoryServicesVO.getGroupName() %>');"><u>A</u>dd</button>&nbsp;</td> --%>
   				</td>
   			</tr>
   			<tr>
   			<%}//end if
   			for(l=0; (l<=2 && (k+l)<hospLabs.size()); l++)
   	   		{
   				laboratoryServicesVO	=	(LaboratoryServicesVO)hospLabs.get(k+l);
   				if(tempGroup.equals(laboratoryServicesVO.getGroupName())){
   					colorType=((colorCode)%1)==0?"gridOddRow":"gridEvenRow";
   					colorCode++;
   			%>
					<td align="left" class="<%=colorType%>"><%= laboratoryServicesVO.getQuestionDesc() %></td>
					<td width="10%" class="<%=colorType%>">
	             <input name="answer1List1" type="checkbox" value="<%= laboratoryServicesVO.getMedicalTypeId() %>">
	             </td>
				
   			<%}//end if
   			}//end for
   			k	=	k+2;
   			%></tr><%
   		}//end for
   	
   	%></table>
   	
   <!-- bean:define id="questionDescTemp" /-->
   <%-- 
   Commented - Kishor,
   
   As I have written separate code  to display the data
   <table class="gridWithCheckBox" style="width:40%"  border="0" cellpadding="0">
	   <logic:notEmpty name="frmCashlessPrescription" property="hospLabs" scope="request">
		    <logic:iterate id="hospgrad" name="frmCashlessPrescription" property="hospLabs" >
			<bean:define id="questionDescTemp11"  name="hospgrad" property="groupName" type="java.lang.String"/>
		 <%
		 if(!questionDescTemp.equals((String)questionDescTemp11))
			{
		%>
		     <tr>
		     
    	      <td align="left" class="gridHeader" colspan="2"><bean:write property="groupName" name="hospgrad"/></td>
				
          	</tr>
		<%
		questionDescTemp = (String)questionDescTemp11;
		pageContext.setAttribute("questionDescTemp",questionDescTemp);
		 }
		   colorType=((colorCode)%2)==0?"gridOddRow":"gridEvenRow";
		   colorCode++;
	    %>
	           <tr class="<%=colorType%>">
	             <td align="left"><bean:write property="questionDesc" name="hospgrad"/></td>
	             <td width="10%">
	             <input name="answer1List1" type="checkbox" value="Y"  <logic:present name="hospgrad" property='answer1'><logic:match name='hospgrad' property='answer1' value='Y'>checked</logic:match></logic:present> >
	             </td>
	           </tr>
	        <INPUT TYPE="hidden" NAME="selectedMedicalTypeId" value="<bean:write property='medicalTypeId' name='hospgrad'/>">
	        <INPUT TYPE="hidden" NAME="selectedMedicalSeqId" value="<bean:write property='medicalSeqId' name='hospgrad'/>">
	        <INPUT TYPE="hidden" NAME="selectedAnswer1List" >
	        <INPUT TYPE="hidden" NAME="selectedAnswer2List" >
   	      </logic:iterate>
   	  </logic:notEmpty>
   	  </table> --%> 	
	<!-- E N D : Form Fields -->
		
 <!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  		<tr>
	    		<td width="100%" align="center">
	    			<%
					if(TTKCommon.isAuthorized(request,"Edit"))
					{
					%>
					<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSubmitLabs();"><u>S</u>ave</button>&nbsp;
	    			<%
					}
	     			%>
	     			<logic:equal name="Prescription_Type" value="Laboratory" scope="request">
	     				<button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onNextLabs()"><u>N</u>ext</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	     			</logic:equal>

		     		<logic:equal name="Prescription_Type" value="Radiology" scope="request">
	     				<button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onNextRadio()"><u>N</u>ext</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	     			</logic:equal>
	     			
	     			<logic:equal name="Prescription_Type" value="Surgery" scope="request">
	     				<button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onNextSurgery()"><u>N</u>ext</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	     			</logic:equal>
	     			
	     			<logic:equal name="Prescription_Type" value="Pharmacy" scope="request">
	     				<button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onNextPharmacy()"><u>N</u>ext</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	     			</logic:equal>
	     			
	     			<logic:equal name="Prescription_Type" value="MinorSurgery" scope="request">
	     				<button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onNextMinorSurgery()"><u>N</u>ext</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	     			</logic:equal>
	     			
	     			<logic:equal name="Prescription_Type" value="Consumables" scope="request">
	     				<button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onNextConsumables()"><u>N</u>ext</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	     			</logic:equal>
	     			
	     			
	     			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseLabs()"><u>C</u>lose</button>
	    		</td>
			</tr>
		</table>
	</div>
	<!-- E N D : Buttons -->
  
	<!-- /logic:match-->
	<input type="hidden" name="mode" value="">
	</div></div></div></div></div></div></div></body>
</html:form>
