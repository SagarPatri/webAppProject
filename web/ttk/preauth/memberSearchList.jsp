<%@page import="com.ttk.dto.enrollment.MemberDetailVO"%>
 <%@ page import="java.util.ArrayList,com.ttk.common.TTKCommon"%>
 <%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>

<%@page import="org.apache.struts.action.DynaActionForm"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/preauth/memberlist.js"></script>

<script>
bAction=false;
var TC_Disabled = true;


</script>



<% 
pageContext.setAttribute("benefitType",Cache.getCacheObject("benefitTypescallcenter"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/MemberListAction.do">
    <!-- S T A R T : Page Title -->
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="57%">List of Members</td>
            <td width="43%" align="right" class="webBoard">&nbsp;</td>
          </tr>
    </table>
    <!-- E N D : Page Title -->
    <html:errors/>
    <div class="contentArea" id="contentArea">
    <!-- S T A R T : Search Box -->
    <table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Member Id:<br>
        
         <html:text property="sEnrollmentId" styleClass="textBox textBoxMedium"  name="frmPreAuthList" />
         
        </td>

        <td valign="bottom" nowrap>
        <div id="enrSearchID">
            <a href="#" accesskey="s" onClick="javascript:memberSearch();"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
            </div>
            </td>
        <td width="100%">&nbsp;</td>
      </tr>
     </table>
      <logic:notEmpty scope="session" name="memberListData">
   <logic:empty scope="session" name="ListmemberDetailVO">
     <div>
    <!-- E N D : Search Box -->
    <!-- S T A R T : Grid -->
        <ttk:HtmlGrid  name="memberListData"/>
    <!-- E N D : Grid -->
    <!-- S T A R T : Buttons and Page Counter -->
    </div>
    <div>
    <table align="center" class="buttonsContainerGrid" border="0" cellspacing="0" cellpadding="0">
         <tr>
        <td>
         <ttk:PageLinks  name="memberListData"/>
         </td>
         </tr>
    </table>
    </div>
     </logic:empty>
 </logic:notEmpty>
  <logic:notEmpty scope="session" name="ListmemberDetailVO">
     <logic:iterate id="memberDetailVO" name="ListmemberDetailVO" indexId="idx">
      <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
   <tr>
   <td><font size="3" color="blue">Beneficiary Details</font></td>
    
   </tr>
   
   
   <tr>
    
   <td width="27%" class="formLabel">Enrollment Id:</td>
   
   <td width="22%" class="textLabelBold"><a  href= "javascript:editmemberid()" ><bean:write property="memberId" name="memberDetailVO"/></a></td>

	<td width="27%" class="formLabel">DHPO Member Id:</td>
   
   <td width="22%" class="textLabelBold"><bean:write property="dhpoMemberId" name="memberDetailVO"/></td>
   </tr>
   
   
     <tr> 
     <td width="22%" class="formLabel">Patient Name:</td>
        <td width="27%" class="textLabelBold"><bean:write property="patientName" name="memberDetailVO"/></td>
        <td width="22%" class="formLabel">Card Number:</td>
        <td width="27%" class="textLabelBold"><bean:write property="memberId" name="memberDetailVO"/></td>        
        </tr>
        <tr> 
     <td width="22%" class="formLabel">Member Active:</td>
        <td width="27%" class="textLabelBold"><bean:write property="memberActive" name="memberDetailVO"/></td>
        <td width="22%" class="formLabel">Gender:</td>
        <td width="27%" class="textLabelBold"><bean:write property="patientGender" name="memberDetailVO"/></td>        
        </tr>
          <tr> 
     <td width="22%" class="formLabel">Age:</td>
        <td width="27%" class="textLabelBold"><bean:write property="memberAge" name="memberDetailVO"/></td>
        <td width="22%" class="formLabel">Date Of Birth:</td>
        <td width="27%" class="textLabelBold"><bean:write property="memberDOB" name="memberDetailVO"/></td>        
        </tr>
            <tr> 
     <td width="22%" class="formLabel">Insurance Company Name:</td>
        <td width="27%" class="textLabelBold"><bean:write property="payerName" name="memberDetailVO"/></td>
        <td width="22%" class="formLabel">Emirate ID:</td>
        <td width="27%" class="textLabelBold"><bean:write property="emirateId" name="memberDetailVO"/></td>        
        </tr>
           <tr> 
     <td width="22%" class="formLabel">Policy Number:</td>
        <td width="27%" class="textLabelBold"><bean:write property="policyNumber" name="memberDetailVO"/></td>
        <td width="22%" class="formLabel">Network Type:</td>
        <td width="27%" class="textLabelBold"><bean:write property="member_network" name="memberDetailVO"/></td>        
        </tr>
        
          <tr> 
     <td width="22%" class="formLabel">Corp Group Id:</td>
        <td width="27%" class="textLabelBold"><bean:write property="corporate_id" name="memberDetailVO"/></td>
        <td width="22%" class="formLabel">Corporate Name :</td>
        <td width="27%" class="textLabelBold"><bean:write property="corporateName1" name="memberDetailVO"/></td>        
        </tr>
           <tr> 
     <td width="22%" class="formLabel">Policy Start Date:</td>
        <td width="27%" class="textLabelBold"><bean:write property="start_date" name="memberDetailVO"/></td>
        <td width="22%" class="formLabel">Policy End Date :</td>
        <td width="27%" class="textLabelBold"><bean:write property="end_date" name="memberDetailVO"/></td>        
        </tr>
  
       	<tr>
       	<td></td>
       	<td></td>
       	<td></td>
         <td  align="right">
    	<button type="button" name="Button" accesskey="l" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onPreautHistory();"><u>L</u>ogHistory</button>
    	</td>
       	</tr>
     </table>
      </logic:iterate>
      
        <fieldset>
	
                <table align="center" class="tablePad"  border="0" cellspacing="0" cellpadding="0">
                      <tr>
			        <td class="formLabel" colspan="2"><strong>Benefit Type: </strong></td>
			         <td colspan="2">
			        <html:select property ="benefitType" styleId="benefitType" styleClass="selectBox selectBoxMedium" name="frmPreAuthList"  onchange="javascript:onswitchbenefitType()">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="benefitType" property="cacheId" labelProperty="cacheDesc"/>
          			</html:select>
          			</td>
		      	</tr>
				 </table> 
				 
			
		    <br> 
		    <br>
		    
		    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		   
		      <tr>
		      		<td class="formLabel" ><strong>Table of Benefits</strong></td>
		      </tr>
		      		<tr>
		      	<logic:equal value="OPTS" name="benifitTypeVal" scope="session">
			      		<td>
							<ol  style="display:list-item;">  
								<li><b>Hospital Consultation</b>
								<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								
										<tr><td width="17%">Copay/Deductible</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[0][0]}</td></tr>
										<tr><td width="17%">Clinican Consultation Type</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[0][1]}</td></tr>
								</table>
								</li>
								<li><b>Non Hospital Consultation</b>
								<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								
										<tr><td width="17%">Copay/Deductible</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[1][0]}</td></tr>
										<tr><td width="17%">Clinican Consultation Type</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[1][1]}</td></tr>
								</table>
								</li>
								<li><b>Laboratory </b>
								 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								   <tr><td width="17%">Copay/Deductible</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[2][2]}</td></tr>
								</table>
								</li>
								<li><b>Pathology</b>
								<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								   <tr><td width="17%">Copay/Deductible</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[3][3]}</td></tr>
								</table>
								</li>
								<li><b>Physiotheraphy</b>
								    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
										 <tr><td width="17%">Total No. of sessions</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[4][4]}</td></tr>
									     <tr><td width="17%">Sessions Available</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[4][6]}</td></tr>	
								     	 <tr><td width="17%">Co-pay: (If applicable)</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[4][5]}</td></tr>		
									</table>
								</li> 
								 <li><b>Prescription Medications</b>
								    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								       <tr><td width="17%">Prescribed Copay<td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[5][7]}</td></tr>
									   <tr><td width="17%">Total Available Limit(OMR)<td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[5][8]}</td></tr>
								    </table>
								</li> 
								 <li><b>Area of Coverage</b>
								 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								    <tr><td width="17%">Area Coverage</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[6][9]}</td></tr>
								 </table>
								</li> 
								  <li><b>Hospital Coverage</b>
								    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								    <tr><td width="17%">Hospital Coverage for IP</td><td>:&nbsp;&nbsp;${tobBenefitsForMemElig[6][10]}</td></tr>
									</table>
								</li> 
							</ol>
							</td>
		      	</logic:equal>
		      	
		      	<logic:equal value="IPT" name="benifitTypeVal" scope="session">
			      
			      		<td>
							<ol  style="display:list-item;">  
								<li><b>Consultation</b>
								<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								
										<tr><td width="17%">Copay/Deductible</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[0][0]}</td></tr>
										<tr><td width="17%">Clinican Consultation Type</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[0][1]}</td></tr>
								</table>
								</li>
								<li><b>Laboratory</b>
								 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								    
									<tr><td width="17%">Copay</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[1][2]}</td></tr>
									</table>
								</li>
								<li><b>Pathology</b>
								<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								   <tr><td width="17%">Copay</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[2][3]}</td></tr>
								</table></li>
								<li><b>Physiotheraphy</b>
								    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
										<tr><td width="17%">Total No. of sessions</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[3][4]}</td></tr>
									   <tr><td width="17%">Sessions Available</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[3][6]}</td></tr>	
								     	 <tr><td width="17%">Co-pay: (If applicable)</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[3][5]}</td></tr>		
									</table>
								</li> 
								
								 <li><b>Area of Coverage</b>
								 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								    <tr><td width="17%">Area Coverage</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[4][7]}</td></tr>
									   	
									</table>
								</li> 
							<li><b>Hospital Coverage</b>
								   <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								    <tr><td width="17%">Hospital Coverage for IP </td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[4][8]}</td></tr>
									</table>
								</li> 
							</ol>
							</td>
						</logic:equal>
		      	
		      	<logic:equal value="MTI" name="benifitTypeVal" scope="session">
			      		
			      		<td>
							<ol  style="display:list-item;">  
								<li><b>Waiting Period</b>
								<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								
										<tr><td width="20%">Waiting Period</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[0][0]}</td></tr>
								</table>
								</li>
								<br>
								<li><b>In-Patient Details</b>
								
								<ul>
								<br> 
								<li><b>  Normal Delivery (With Emergency)</b>
								 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								    
									<tr><td width="17%">Co-pay/Deductible</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[0][1]}</td></tr>
									<tr><td width="17%">Limit(OMR)</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[0][2]}</td></tr>
									<tr><td width="17%">Available Limit(OMR)</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[0][3]}</td></tr>
							    </table>
								</li>
								<li><b>  Normal Delivery (Without Emergency)</b>
								    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								    <tr><td width="17%">Co-pay/Deductible</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[0][4]}</td></tr>
									<tr><td width="17%">Limit(OMR)</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[0][5]}</td></tr>
									<tr><td width="17%">Available Limit(OMR)</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[0][6]}</td></tr>
									</table>
								</li>
								
								<li><b>  LSCS (With Emergency)</b>
								 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								   <tr><td width="17%">Co-pay/Deductible</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[0][7]}</td></tr>
									<tr><td width="17%">Limit(OMR)</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[0][8]}</td></tr>
									<tr><td width="17%">Available Limit(OMR)</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[0][9]}</td></tr>
								</table>
								</li>	
								<li><b>   LSCS (Without Emergency)</b>
								  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								    <tr><td width="17%">Co-pay/Deductible</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[0][10]}</td></tr>
									<tr><td width="17%">Limit(OMR)</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[1][0]}</td></tr>
									<tr><td width="17%">Available Limit(OMR)</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[1][1]}</td></tr>
								  </table>
								</li>
								</ul>	
								</li>
								<li><b>Out-Patient Details</b>
								<ul>
								<br>
								<li><b>Out-Patient(With Emergency)</b>
								 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								    <tr><td width="17%">Limit(OMR)</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[1][2]}</td></tr>
									<tr><td width="17%">Co-pay/Deductible</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[1][3]}</td></tr>
									<tr><td width="17%">No. of visits</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[1][4]}</td></tr>
								</table>
								</li>
								<li><b>Out-Patient(Without Emergency)</b>
									<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								    <tr><td width="17%">Limit(OMR)</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[1][5]}</td></tr>
									<tr><td width="17%">Co-pay/Deductible</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[1][6]}</td></tr>
									<tr><td width="17%">No. of visits</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[1][7]}</td></tr>
									</table>
								</li>
								</ul>
								</li>
								<li><b>Area of Coverage for I/P</b>
								<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
									<tr><td width="20%">Area of Coverage for I/P</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[1][8]}</td></tr>
								</table>
								</li>
								<br>
								<li><b>Hospital Coverage</b>
								<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
									<tr><td width="20%">Hospital Coverage</td><td>:&nbsp;&nbsp;&nbsp;${tobBenefitsForMemElig[1][9]}</td></tr>
								</table>
								</li>
							</ol>
							</td>
		      	</logic:equal>
		      	
		      	<logic:equal value="OPTC" name="benifitTypeVal" scope="session">
			      		<td> 
							<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr>
				      			<td width="25%">Copay/Deductible </td><td> :&nbsp;${tobBenefitsForMemElig[0][0]}  </td>
				      			</tr>
				      			<tr>
				      				<td width="25%"> Total Limit(OMR)</td><td> :&nbsp;${tobBenefitsForMemElig[0][1]} </td>
				      			</tr>
				      			<tr>
				      			<td width="25%">Available Limit(OMR)</td><td> :&nbsp;${tobBenefitsForMemElig[0][2]}</td>
				      			</tr>
			      			</table>
			      		</td>
		      	</logic:equal>
		      	
		      	<logic:equal value="DNTL" name="benifitTypeVal" scope="session">
			      		<td> 
							<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr>
				      			<td width="25%">Copay/Deductible </td><td> :&nbsp;${tobBenefitsForMemElig[0][0]} </td>
				      			</tr>
				      			<tr>
				      				<td width="25%"> Total Limit(OMR)</td><td> : ${tobBenefitsForMemElig[0][1]} </td>
				      			</tr>
				      			<tr>
				      			<td width="25%">Available Limit(OMR) </td><td> : ${tobBenefitsForMemElig[0][2]} </td>
				      			</tr>
			      			</table>
			      		</td>
		      	</logic:equal>
		      	
		      	<logic:equal value="DAYC" name="benifitTypeVal" scope="session">
			      		<td> 
							<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr>
				      			<td width="25%">Copay </td><td> :   ${tobBenefitsForMemElig[0][0]}  </td>
				      			</tr>
				      		
			      			</table>
			      		</td>
		      	</logic:equal>
		      	
		      		<logic:equal value="CHRO" name="benifitTypeVal" scope="session">
			      		<td> 
							<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr>
				      			<td width="25%">Waiting Period </td><td> :  ${tobBenefitsForMemElig[0][0]}  </td>
				      			</tr>
				      			<tr>
				      				<td width="25%"> Member Inception Date</td>
				      				<td> :  ${tobBenefitsForMemElig[0][1]} </td>
				      			</tr>
				      		</table>
				      		
			      		</td>
		      	</logic:equal>
		      	
		      		<logic:equal value="PED" name="benifitTypeVal" scope="session">
			      		<td> 
							<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr>
				      			<td width="25%">Waiting Period </td><td> :  ${tobBenefitsForMemElig[0][0]}  </td>
				      			</tr>
				      			<tr>
				      				<td width="25%"> Member Inception Date</td>
				      				<td> :   ${tobBenefitsForMemElig[0][1]} </td>
				      			</tr>
				      		</table>
				      		
			      		</td>
		      	</logic:equal>
		      	</tr>
		      	
		</table>   
		    
		    </fieldset>
     </logic:notEmpty>
    <table align="center" class="buttonsContainerGrid" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="27%"> </td>
        <td width="73%" nowrap align="right">
        <button type="button" onclick="closeMembers();" name="Button1" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"><u>C</u>lose</button>
        </td>
        </tr>
        </table>

    </div>
     <% 
      if(session.getAttribute("ListmemberDetailVO")!=null)
      {
    	  ArrayList<MemberDetailVO>ListmemberDetailVO = (ArrayList<MemberDetailVO>)session.getAttribute("ListmemberDetailVO");
    	 for(MemberDetailVO memberDetailVO:ListmemberDetailVO)
    	 {
      %>
    
      <INPUT type="hidden" id="memberSeqID" name="memberSeqID" value="<%=memberDetailVO.getMemberSeqID()+""%>">
     <% }
     }%>
    
    <!-- E N D : Buttons and Page Counter -->
    <INPUT type="hidden" name="rownum" value="">
    <input type="hidden" name="child" value="">
    <INPUT type="hidden" name="mode" value="memberSearch">
    <INPUT type="hidden" name="sortId" value="">
    <INPUT type="hidden" name="pageId" value="">
    <INPUT type="hidden" name="tab" value="">
    <INPUT type="hidden" name="reforward" value="">
    
</html:form>
<!-- E N D : Content/Form Area -->
