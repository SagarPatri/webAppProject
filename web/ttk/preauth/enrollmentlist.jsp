<%
/**
 * @ (#) enrollmentlist.jsp 4th May 2006
 * Project      : TTK HealthCare Services
 * File         : enrollmentlist.jsp
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : 4th May 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/preauth/enrollmentlist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var TC_Disabled = true;
</SCRIPT>
<%
	pageContext.setAttribute("gender", Cache.getCacheObject("gender"));
	pageContext.setAttribute("listInsuranceCompany",Cache.getCacheObject("insuranceCompany"));
	pageContext.setAttribute("pbenifitTypes",Cache.getCacheObject("pbenifitTypes"));
	boolean viewmode = true;
%>
<html:form action="/EnrollmentListAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td width="57%">Select Enrollment</td>
    		<td width="43%" align="right" class="webBoard">&nbsp;</td>
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td nowrap>Beneficiary Name:<br>
            <html:text property="name" styleId="search1" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement)" maxlength="60"/>
       	</td>
      	<td width="21%" nowrap>Member ID:<br>
            <html:text property="enrollmentID" styleId="search2" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement)" maxlength="60"/>
        </td>
          </td>
         <td nowrap>Resident Card ID:<br>
		    	<html:text property="sEmiratesID" styleId="search10" name="frmEnrollSearch" styleClass="textBox textBoxMedium"  maxlength="250"/>
		</td>   
      	<td width="21%" nowrap>Scheme Name:<br>
        	<html:text property="schemeName" styleId="search3" styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement)"/>
		</td>
        <td width="10%" nowrap>Certificate No.:<br>
        	<html:text property="certificateNo" styleId="search4" styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement)"/>
		</td>

        <td nowrap>Gender:<br>
            <span class="textLabel">
            	<html:select styleId="search5" property="genderTypeID" styleClass="selectBox selectBoxSmall">
            		<html:option value="">Any</html:option>
		      		<html:optionsCollection name="gender" label="cacheDesc" value="cacheId" />
				</html:select>
          	</span>
       	</td>
        <td width="1%" nowrap>&nbsp;</td>
      </tr>
      <tr>
        <td nowrap>Scheme No.:<br>
            <html:text property="policyNbr" styleId="search6" styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>
        <td nowrap>Healthcare Company:<br>
            <html:select property="sInsuranceSeqID" styleId="search7" styleClass="selectBox selectBoxMedium" >
				 <html:option value="">Any</html:option>
				<html:optionsCollection name="listInsuranceCompany" label="cacheDesc" value="cacheId"/>
			</html:select>
		</td>
		</td>
        <td nowrap>Employee No.:<br>
            <html:text property="sEmployeeNo" styleId="search8" styleClass="textBox textBoxMedium" maxlength="60"/></td>
        <td nowrap>Corp. Name:<br>
            <html:text property="corpName" styleId="search9" styleClass="textBox textBoxMedium" maxlength="120"/>
        </td>
        <td nowrap>Show Latest:<br>
			<input name="showLatestYN" type="checkbox" value="Y" checked>
		</td>
        <td width="46%" valign="bottom" nowrap>
        <a href="#" class="search" accesskey="s" onClick="onSearch()" ><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
      </tr>
    </table>
	<!-- E N D : Search Box -->
    <!-- S T A R T : Grid -->
    
   <logic:notEqual name="frmEnrollSearch" property="reforward" value="BatchEntry">
    <logic:empty name="claimantVO"> 
   <%-- <logic:notMatch name="frmEnrollSearch" property="reforward" value="callcenter"> --%>
    <ttk:HtmlGrid name="tableData"/>
   <%--  </logic:notMatch> --%>
    </logic:empty> 
    <!-- E N D : Grid -->
    <fieldset>
   
    <logic:notEmpty name="claimantVO">
   <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
   <tr>
   
   <td><font size="3" color="blue">Beneficiary Details</font></td>
   <td width="27%" class="formLabel">Enrollment Id:</td>
    <td width="22%" class="textLabelBold"><a  href= "javascript:edit(0)" ><bean:write property="enrollmentID" name="claimantVO"/></a></td>
   </tr>
     <tr> 
        <td width="22%" class="formLabel">Member Name</td>
        <td width="27%" class="textLabelBold">:&nbsp;<bean:write property="name" name="claimantVO"/></td>      
        <td width="22%" class="formLabel">Gender</td>
        <td width="27%" class="textLabelBold">:&nbsp;<bean:write property="genderDescription" name="claimantVO"/></td>
     </tr>
     <tr> 
        <td width="22%" class="formLabel">Age</td>
        <td width="27%" class="textLabelBold">:&nbsp;<bean:write property="age" name="claimantVO"/></td>      
        <td width="22%" class="formLabel">Policy Number</td>
        <td width="27%" class="textLabelBold">:&nbsp;<bean:write property="policyNbr" name="claimantVO"/></td>
     </tr>
     <tr> 
        <td width="22%" class="formLabel">Policy Start Date</td>
        <td width="27%" class="textLabelBold">:&nbsp;<bean:write property="sDate" name="claimantVO"/></td>     
        <td width="22%" class="formLabel">Policy End Date</td>
        <td width="27%" class="textLabelBold">:&nbsp;<bean:write property="eDate" name="claimantVO"/></td>
     </tr>
     <tr> 
        <td width="22%" class="formLabel">Emirates Id</td>
        <td width="27%" class="textLabelBold">:&nbsp;<bean:write property="sEmiratesID" name="claimantVO"/></td>      
        <td width="22%" class="formLabel">Corp. Name</td>
        <td width="27%" class="textLabelBold">:&nbsp;<bean:write property="groupName" name="claimantVO"/></td>
     </tr>
     <tr> 
        <td width="22%" class="formLabel">Date Of Birth</td>
        <td width="27%" class="textLabelBold">:&nbsp;<bean:write property="dob" name="claimantVO"/></td>      
        <td width="22%" class="formLabel">Insurance Company Name</td>
        <td width="27%" class="textLabelBold">:&nbsp;<bean:write property="companyName" name="claimantVO"/></td>
     </tr>
     <tr> 
        <td width="22%" class="formLabel">Member Active(Yes/No)</td>
        <td width="27%" class="textLabelBold">:&nbsp;<bean:write property="memberActive" name="claimantVO"/></td>      
        <td width="22%" class="formLabel">Corp. Group ID</td>
        <td width="27%" class="textLabelBold">:&nbsp;<bean:write property="corpGroupId" name="claimantVO"/></td>
     </tr>
     <tr> 
        <td width="22%" class="formLabel">Card Number</td>
        <td width="27%" class="textLabelBold">:&nbsp;<bean:write property="cardNo" name="claimantVO"/></td>      
        <td width="22%" class="formLabel">Network Type</td>
        <td width="27%" class="textLabelBold">:&nbsp;<bean:write property="networkType" name="claimantVO"/></td>
     </tr>
       	<tr>
       	<td></td>
       	<td></td>
       	<td></td>
         <td  align="right">
    	<button type="button" name="Button" accesskey="l" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onLogHistory()"><u>L</u>ogHistory</button>
    	</td>
       	</tr>
     </table>
		<legend>Policy Details</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		      <tr>
		    <td nowrap>Benefit Type:&nbsp;&nbsp;&nbsp;&nbsp;
       <html:select property ="benifitType" styleId="benifitTypeID" styleClass="selectBox selectBoxMedium" onchange="javascript:onCheckbenifitType()">
        <html:option value="">Select from list</html:option>
        <html:options collection="pbenifitTypes" property="cacheId" labelProperty="cacheDesc"/>
         </html:select></td></tr>
         
         <tr>
		      	 <logic:equal value="IPT" name="benifitTypeVal" scope="session">
                 
                          <td>
                            <ol  style="display:list-item;"> 
                                <li><b>Consultation</b>
                                <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                               
                                        <tr><td width="25%">Copay/Deductible</td><td>:&nbsp;${tobBenefitsForMemElig[0][0]}</td></tr>
                                        <tr><td width="25%">Clinican Consultation Type</td><td>:&nbsp;${tobBenefitsForMemElig[0][1]}</td></tr>
                                </table>
                                </li>
                                <li><b>Laboratory</b>
                                 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                                   
                                    <tr><td width="25%">Copay</td><td>:&nbsp;${tobBenefitsForMemElig[1][2]}</td></tr>
                                     </table>
                                </li>
                               <li><b>Pathology</b>
								<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								   <tr><td width="25%">Copay</td><td>:&nbsp;${tobBenefitsForMemElig[2][3]}</td></tr>
								</table></li>
                                <li><b>Physiotheraphy</b>
                                    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                                        <tr><td width="25%">Total No. of sessions</td><td>:&nbsp;${tobBenefitsForMemElig[3][4]}</td></tr>
                                       <tr><td width="25%">Sessions Available</td><td>:&nbsp;${tobBenefitsForMemElig[3][6]}</td></tr>   
                                          <tr><td width="25%">Co-pay: (If applicable)</td><td>:&nbsp;${tobBenefitsForMemElig[3][5]}</td></tr>       
                                    </table>
                                </li>
                                 <li><b>Area of Coverage</b>
                                 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                                    <tr><td width="25%">Area coverage</td><td>:&nbsp;${tobBenefitsForMemElig[4][7]}</td></tr>
                                          
                                    </table>
                                </li>
                                 <li><b>Hospital Coverage for IP</b>
                                   <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                                    <tr><td width="25%">hospital coverage</td><td>:&nbsp;${tobBenefitsForMemElig[4][8]}</td></tr>
                                     
                                            
                                    </table>
                                </li>
                            </ol>
                            </td>
                        </logic:equal>
		      	  
		      	   <logic:equal value="OPTS" name="benifitTypeVal" scope="session">
                          <td>
                            <ol  style="display:list-item;"> 
                                <li><b>Hospital Consultation</b>
                                <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                               
                                        <tr><td width="25%">Copay/Deductible</td><td>:&nbsp;${tobBenefitsForMemElig[0][0]}</td></tr>
                                        <tr><td width="25%">Clinican Consultation Type</td><td>:&nbsp;${tobBenefitsForMemElig[0][1]}</td></tr>
                                </table>
                                </li>
                                <li><b>Non Hospital Consultation</b>
                                <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                               
                                        <tr><td width="25%">Copay/Deductible</td><td>:&nbsp;${tobBenefitsForMemElig[1][0]}</td></tr>
                                        <tr><td width="25%">Clinican Consultation Type</td><td>:&nbsp;${tobBenefitsForMemElig[1][1]}</td></tr>
                                </table>
                                </li>
                                <li><b>Laboratory</b>
                                 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                                   
                                    <tr><td width="25%">Copay/Deductible</td><td>:&nbsp;${tobBenefitsForMemElig[2][2]}</td></tr>
                                    </table>
                                </li>
                                <li><b>Pathology</b>
								<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
								   <tr><td width="25%">Copay/Deductible</td><td>:&nbsp;${tobBenefitsForMemElig[3][3]}</td></tr>
								</table></li>
                                <li><b>Physiotheraphy</b>
                                    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                                        <tr><td width="25%">Total No. of sessions</td><td>:&nbsp;${tobBenefitsForMemElig[4][4]}</td></tr>
                                       <tr><td width="25%">Sessions Available</td><td>:&nbsp;${tobBenefitsForMemElig[4][6]}</td></tr>   
                                          <tr><td width="25%">Co-pay: (If applicable)</td><td>:&nbsp;${tobBenefitsForMemElig[4][5]}</td></tr>       
                                    </table>
                                </li>
                                 <li><b>Prescription Medications</b>
                                    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                                        <tr><td width="25%">Prescribed Copay<td>:&nbsp;${tobBenefitsForMemElig[5][7]}</td></tr>
                                        <tr><td width="25%">Total Available Limit(OMR)<td>:&nbsp;${tobBenefitsForMemElig[5][8]}</td></tr>
                                    </table>
                                </li>
                                 <li><b>Area of Coverage</b>
                                 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                                    <tr><td width="25%">Area coverage</td><td>:&nbsp;${tobBenefitsForMemElig[6][9]}</td></tr>
                                          
                                    </table>
                                </li>
                                
                  
                                 <li><b>Hospital Coverage for OP</b>
                                   <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                                    <tr><td width="25%">hospital coverage</td><td>:&nbsp;${tobBenefitsForMemElig[6][10]}</td></tr>
                                            
                                    </table>
                                </li>
                            </ol>
                            </td>
                  </logic:equal>
		      	
		      	
		      	<logic:equal value="OPTC" name="benifitTypeVal" scope="session">
			      		<td> 
								<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr><td width="25%"> Copay/Deductible </td><td> :  ${tobBenefitsForMemElig[0][2]} </td>
				      			</tr>
				      			<tr>
				      				<td width="25%">Total Limit(OMR) </td><td> :  ${tobBenefitsForMemElig[0][0]}</td>
				      			</tr>
				      			<tr><td width="25%">Available Limit(OMR) </td><td> :  ${tobBenefitsForMemElig[0][1]}</td>
				      			</tr>
			      			</table>
			      		</td>
		      	</logic:equal>
		      	
                   <logic:equal value="MTI" name="benifitTypeVal" scope="session">
                         
                          <td>
                            <ol  style="display:list-item;"> 
                                <li><b>Waiting Period</b>
                                <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                               
                                        <tr><td width="25%">Waiting Period</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;${tobBenefitsForMemElig[0][0]}</td></tr>
                                </table>
                                </li>
                                <br>
                                <li><b>In-Patient Details</b>
                                
                                <ul>
                                <br>
                                <li><b>  Normal Delivery (With Emergency)</b>
                                 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                                   
                                    <tr><td width="25%">Copay/Deductible</td><td>:&nbsp;${tobBenefitsForMemElig[0][1]}</td></tr>
                                    <tr><td width="25%">Limit(OMR)</td><td>:&nbsp;${tobBenefitsForMemElig[0][2]}</td></tr>
                                    <tr><td width="25%">Available Limit(OMR)</td><td>:&nbsp;${tobBenefitsForMemElig[0][3]}</td></tr>
                                   <%--  <tr><td width="25%">No. Of Visits</td><td>:&nbsp;${tobBenefitsForMemElig[0][4]}</td></tr> --%>
                                    </table>
                                </li>
                                <li><b>  Normal Delivery (Without Emergency)</b>
                                 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                                   <tr><td width="25%">Copay/Deductible</td><td>:&nbsp;${tobBenefitsForMemElig[0][4]}</td></tr>
                                    <tr><td width="25%">Limit(OMR)</td><td>:&nbsp;${tobBenefitsForMemElig[0][5]}</td></tr>
                                    <tr><td width="25%">Available Limit(OMR)</td><td>:&nbsp;${tobBenefitsForMemElig[0][6]}</td></tr>
                                    <%-- <tr><td width="25%">No. Of Visits</td><td>:&nbsp;${tobBenefitsForMemElig[1][2]}</td></tr> --%>
                                    </table>
                                </li>
                               
                                <li><b>  LSCS (With Emergency)</b>
                                 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                                   <tr><td width="25%">Copay/Deductible</td><td>:&nbsp;${tobBenefitsForMemElig[0][7]}</td></tr>
                                    <tr><td width="25%">Limit(OMR)</td><td>:&nbsp;${tobBenefitsForMemElig[0][8]}</td></tr>
                                    <tr><td width="25%">Available Limit(OMR)</td><td>:&nbsp;${tobBenefitsForMemElig[0][9]}</td></tr>
                                    <%-- <tr><td width="25%">No. Of Visits</td><td>:&nbsp;${tobBenefitsForMemElig[1][2]}</td></tr> --%>
                                    </table>
                                </li>   
                                <li><b>   LSCS (Without Emergency)</b>
                                  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                                   <tr><td width="25%">Copay/Deductible</td><td>:&nbsp;${tobBenefitsForMemElig[0][10]}</td></tr>
                                    <tr><td width="25%">Limit(OMR)</td><td>:&nbsp;${tobBenefitsForMemElig[0][11]}</td></tr>
                                    <tr><td width="25%">Available Limit(OMR)</td><td>:&nbsp;${tobBenefitsForMemElig[0][12]}</td></tr>
                                    <%-- <tr><td width="25%">No. Of Visits</td><td>:&nbsp;${tobBenefitsForMemElig[1][2]}</td></tr> --%>
                                    </table>
                                </li>
                                </ul>   
                                </li>
                                <li><b>Out-Patient Details</b>
                                
                                <ul>
                                <br>
                                <li><b>Out-Patient(With Emergency)</b>
                                 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                                   
                                    <tr><td width="25%">Limit(OMR)</td><td>:&nbsp;${tobBenefitsForMemElig[0][13]}</td></tr>
                                    <tr><td width="25%">Copay/Deductible</td><td>:&nbsp;${tobBenefitsForMemElig[0][14]}</td></tr>
                                    <tr><td width="25%">No. of visits</td><td>:&nbsp;${tobBenefitsForMemElig[0][15]}</td></tr>
                                    </table>
                                </li>
                                <li><b>Out-Patient(Without Emergency)</b>
                                    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                                    <tr><td width="25%">Limit(OMR)</td><td>:&nbsp;${tobBenefitsForMemElig[0][16]}</td></tr>
                                    <tr><td width="25%">Copay/Deductible</td><td>:&nbsp;${tobBenefitsForMemElig[0][17]}</td></tr>
                                    <tr><td width="25%">No. of visits</td><td>:&nbsp;${tobBenefitsForMemElig[0][18]}</td></tr>
                                    </table>
                                </li>
                                </ul>
                                </li>
                                <li><b>Area of Coverage for I/P</b>
                                <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                               
                                        <tr><td width="25%">Area of Coverage for I/P</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;${tobBenefitsForMemElig[0][19]}</td></tr>
                                </table>
                                </li>
                                <br>
                                <li><b> Hospital coverage</b>
                                <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
                               
                                        <tr><td width="25%"> Hospital coverage</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;${tobBenefitsForMemElig[0][20]}</td></tr>
                                </table>
                                </li>
                               
                            </ol>
                            </td>
                  </logic:equal>
		      	<logic:equal value="DNTL" name="benifitTypeVal" scope="session">
			      		
			      		<td> 
								<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr><td width="25%"> Copay/Deductible </td><td> :  ${tobBenefitsForMemElig[0][2]} </td>
				      			</tr>
				      			<tr>
				      				<td width="25%">Total Limit(OMR) </td><td> :  ${tobBenefitsForMemElig[0][0]}</td>
				      			</tr>
				      			<tr><td width="25%">Available Limit(OMR) </td><td> :  ${tobBenefitsForMemElig[0][1]}</td>
				      			</tr>
			      			</table>
			      		</td>
		      	</logic:equal>
		      	
		      	<logic:equal value="DAYC" name="benifitTypeVal" scope="session">
			      		<td> 
							<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr><td width="25%"> Copay </td><td> :  ${tobBenefitsForMemElig[0][0]} </td>
				      			</tr>
			      			</table>
			      		</td>
		      	</logic:equal>
		      		<logic:equal value="PED" name="benifitTypeVal" scope="session">
			      		<td> 
							<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr>
				      			<td width="25%">Waiting Period</td><td> : ${tobBenefitsForMemElig[0][0]} </td>
				      			</tr>
				      			<tr>
				      				<td width="25%">Member Inception Date </td><td> :  ${tobBenefitsForMemElig[0][1]}</td>
				      			</tr>
				      			
			      			</table>
			      		</td>
		      	</logic:equal>
		      	<logic:equal value="CHRO" name="benifitTypeVal" scope="session">
			      		<td> 
							<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr>
				      			<td width="25%">Waiting Period</td><td> : ${tobBenefitsForMemElig[0][0]} </td>
				      			</tr>
				      			<tr>
				      				<td width="25%">Member Inception Date </td><td> :  ${tobBenefitsForMemElig[0][1]}</td>
				      			</tr>
				      			
			      			</table>
			      		</td>
		      	</logic:equal>
		      	</tr>
		      
        </table>
        <INPUT TYPE="hidden" NAME="lngMemberSeqID" VALUE="<bean:write property="memberSeqID" name="claimantVO"/>">
        </logic:notEmpty>
        
        </fieldset>
      </logic:notEqual>
      
      
 <logic:equal  name="frmEnrollSearch" property="reforward" value="BatchEntry">
 <ttk:HtmlGrid name="tableData"/>
</logic:equal> 
      
      
      
      
      
      
      
      
		
    <!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td>&nbsp;</td>
    	<td width="73%" nowrap align="right">
    		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>
    	</td>
  	</tr>
  	<ttk:PageLinks name="tableData"/>
    </table>
</div>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<input type="hidden" name="child" value="EnrollmentList">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<input type="hidden" name="reforward" id="reforward" VALUE=""/>
	<html:hidden property="showLatest"/>
	<html:hidden property="closeFlag"/>
	 
 	</html:form>
	<!-- E N D : Content/Form Area -->
	
	