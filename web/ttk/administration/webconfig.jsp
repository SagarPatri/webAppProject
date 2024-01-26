<%
/**
 * @ (#) webconfig.jsp 4th jan 2008
 * Project      : TTK HealthCare Services
 * File         : webconfig.jsp
 * Author       : Yogesh S.C
 * Company      : Span Systems Corporation
 * Date Created : 4th jan 2008
 *
 * @author       :Yogesh S.C
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	pageContext.setAttribute("webloginHRAddition", Cache.getCacheObject("webloginHRAddition"));
	pageContext.setAttribute("webloginEmpAddition", Cache.getCacheObject("webloginEmpAddition"));
	pageContext.setAttribute("webloginDOI", Cache.getCacheObject("webloginDOI"));
	pageContext.setAttribute("webloginAddSuminsured", Cache.getCacheObject("webloginAddSuminsured"));

	//Added for KOC-1216
	pageContext.setAttribute("webloginOptin",Cache.getCacheObject("webloginOptin")); // added by Praveen for getting websetting value from db.
	//ended

	pageContext.setAttribute("webloginCancellation", Cache.getCacheObject("webloginCancellation"));
	pageContext.setAttribute("webloginSoftcopy", Cache.getCacheObject("webloginSoftcopy"));
	pageContext.setAttribute("webloginSendMailGenTypeID", Cache.getCacheObject("webloginSendMailGenTypeID"));
	pageContext.setAttribute("webloginMailGenTypeID",Cache.getCacheObject("webloginMailGenTypeID"));
	pageContext.setAttribute("empPwdGen", Cache.getCacheObject("empPwdGen"));
	pageContext.setAttribute("intimationAccess", Cache.getCacheObject("intimationAccess"));
	pageContext.setAttribute("onlineAssistance", Cache.getCacheObject("onlineAssistance"));
	pageContext.setAttribute("onlineRating", Cache.getCacheObject("onlineRating"));
	pageContext.setAttribute("relationshipCombination",Cache.getCacheObject("relationshipCombination"));
	pageContext.setAttribute("wellnessAccess", Cache.getCacheObject("wellnessAccess"));
%>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/webconfig.js"></script>

<html:form action="/WebConfigAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	    	<td>Web Configuration Information - <bean:write name="frmWebConfig" property="caption" /><html:hidden property="caption"/></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
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
	<fieldset>
	<legend>Online Enrollment Addition </legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			<tr>
		    	<td width="30%" nowrap class="formLabel">Window Period (days): <span class="mandatorySymbol">*</span></td>
			    <td width="30%"><html:text property="windowPeriod"  styleClass="textBox textBoxMedium" maxlength="3"/></td>
			    <td width="28%" class="formLabel">&nbsp;</td>
			    <td width="12%"><html:hidden property="prodPolicySeqID"/><html:hidden property="policySeqID"/>&nbsp;</td>
		    </tr>
		    <tr>

			    <td  class="formLabel">Modification Time Frame (days): <span class="mandatorySymbol">*</span></td>
			    <td ><html:text property="modTimeFrame"  styleClass="textBox textBoxMedium" maxlength="3"/></td>
			    <td class="formLabel">&nbsp;</td>
			    <td >&nbsp;</td>
		    </tr>
		    <!-- Start Modification As per  KOC 1159 (Aravind) Change Request -->
		    <tr>

			    <td  class="formLabel">Delete Modification Time Frame (days): <span class="mandatorySymbol">*</span></td>
			    <td ><html:text property="intDelModTimeFrame"  styleClass="textBox textBoxMedium" maxlength="3"/></td>
			    <td class="formLabel">&nbsp;</td>
			    <td >&nbsp;</td>
		    </tr>
		        <!-- End Modification As per  KOC 1159 (Aravind) Change Request -->
		    <tr>

			    <td  class="formLabel">First Login Window Period (days): </td>
			    <td ><html:text property="logindtWindowPerd"  styleClass="textBox textBoxMedium" maxlength="2"/></td>
			    <td class="formLabel">&nbsp;</td>
			    <td >&nbsp;</td>
		    </tr>
		    <tr>
			    <td class="formLabel">HR:</td>
			    <td>
			    	<html:select property="groupCntGeneralTypeID"  styleClass="selectBox selectBoxMedium" style="width:190px;">
			    		<html:option value=" ">Select from list</html:option>
			    		<html:optionsCollection name="webloginHRAddition" label="cacheDesc" value="cacheId" />
			    	</html:select>
			    </td>
			    <td class="formLabel">&nbsp;</td>
			    <td>&nbsp;</td>
		    </tr>
		    <tr>
			    <td class="formLabel">Employee: </td>
			    <td>
			    	<html:select property="empAddGeneralTypeID"  styleClass="selectBox selectBoxMedium">
			  			<html:option value=" ">Select from list</html:option>
			    		<html:optionsCollection name="webloginEmpAddition" label="cacheDesc" value="cacheId" />
			    	</html:select>
			    </td>
			    <td class="formLabel">&nbsp;</td>
			    <td>&nbsp;</td>
		    </tr>
	    </table>
	</fieldset>
	<fieldset>
				<legend>Opt In/Out</legend>
					<table width="99%"  border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
						 <tr>
							 <td width="30%" nowrap class="formLabel">OPT IN/OUT </td>
							  <td width="30%"><html:select  property="optGenTypeID" styleClass="selectBox selectBoxMedium" >
								  <html:optionsCollection name="webloginOptin" label="cacheDesc" value="cacheId" />
								 </html:select></td>
							  <td width="28%" class="formLabel">&nbsp;</td>
				    			 <td width="12%">&nbsp;</td>
			      		</tr>
				</table>
		</fieldset>
		<!--Added for IBM.....26.1--->
		<fieldset>
			<legend>Default Employee Floater Premium</legend>
			  <table width="99%"  border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
		 		 <tr>
					<td  width="30%" nowrap class="formLabel">Premium: </td>
					<td width="30%"><html:text property="ibmPolPremium"  styleClass="textBox textBoxMedium" maxlength="17"/></td>
					<td width="28%" class="formLabel">&nbsp;</td>
				    	<td width="12%">&nbsp;</td>
			    	</tr>
			</table>
	</fieldset>

	<fieldset>
	<legend>Scheme </legend>
		<table width="99%"  border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer"> 
		    <tr>
			    <td width="30%" nowrap class="formLabel">Type: </td>
			    <td width="30%">
					<bean:write name="frmWebConfig" property="policySubType"/>
					<html:hidden name="frmWebConfig" property="policySubType"/>
				</td>
			    <td width="28%" class="formLabel">&nbsp;</td>
			    <td width="12%">&nbsp;</td>
		    </tr>
		    <tr>
			    <td class="formLabel">Sum Insured (Rs): <span class="mandatorySymbol">*</span></td>
			    <td>
			    <html:text property="policySumInsured" styleClass="textBox textBoxMedium" maxlength="17" />
				</td>
			    <td class="formLabel">&nbsp;</td>
			    <td>&nbsp;</td>
		    </tr>
	    </table>
	</fieldset>
	<fieldset>
	<legend>Domiciliary Treatment (OPD)  </legend>
		<table width="99%"  border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
		    <tr>
			    <td width="30%" nowrap class="formLabel">Domiciliary Type (OPD): </td>
			    <td width="30%"><bean:write name="frmWebConfig" property="opdDomicillaryType"/><html:hidden name="frmWebConfig" property="opdDomicillaryType"/>	</td>
			    <td width="28%" class="formLabel">&nbsp;</td>
			    <td width="12%">&nbsp;</td>
		    </tr>
		    <tr>
			    <td class="formLabel">OPD Limit (Rs): </td>
			    <td><html:text property="opdSumInsured" styleClass="textBox textBoxMedium" maxlength="17"/></td>
			    <td class="formLabel">&nbsp;</td>
			    <td>&nbsp;</td>
		    </tr>
	    </table>
	</fieldset>
	<fieldset>
	<legend>Settings </legend>
		<table width="99%"  border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
      <tr>
       <td width="30%" nowrap class="formLabel">Date of Inception: <span class="mandatorySymbol">*</span></td>
       <td width="30%">
        <html:select property="inceptionDateGenTypeID"  styleClass="selectBox selectBoxMedium" style="width:190px;">
         <html:option value=" ">Select from list</html:option>
         <html:optionsCollection name="webloginDOI" label="cacheDesc" value="cacheId" />
         </html:select>
         </td>
       <td width="28%" class="formLabel">&nbsp;</td>
       <td width="12%">&nbsp;</td>
      </tr>
      <tr>
       <td class="formLabel">Additional Sum Insured: </td>
       <td><html:select  property="addSumInsuredGenTypeID" styleClass="selectBox selectBoxMedium" >
        <html:optionsCollection name="webloginAddSuminsured" label="cacheDesc" value="cacheId" />
       </html:select></td>
       <td class="formLabel">&nbsp;</td>
       <td>&nbsp;</td>
      </tr>
      <tr>
          <td  class="formLabel">Create Employee: </td>
          <td><html:select  property="sendMailGenTypeID" styleClass="selectBox selectBoxMedium" onchange="javascript:hideMailGenType()">
            <html:optionsCollection name="webloginSendMailGenTypeID" label="cacheDesc" value="cacheId" />
            </html:select>       </td>
       <td colspan="2" align="left" class="formLabel" id="familyDetails" style="padding:0; margin:0;"><table border="0" cellspacing="0" cellpadding="0">
         <tr>
           <td>Family Details: </td>
           <td ><html:select property="mailGenTypeID" styleClass="selectBox selectBoxMedium" style="width:185px;">
           <html:optionsCollection name="webloginMailGenTypeID" label="cacheDesc" value="cacheId" />
         </html:select>
         </td>
         </tr>

       </table></td>
     </tr>
      <tr>
       <td  nowrap class="formLabel">Employee:</td>
       <td >
        <html:select property="softCopyGenTypeID" styleClass="selectBox selectBoxMedium" >
         <html:optionsCollection name="webloginSoftcopy" label="cacheDesc" value="cacheId" />
        </html:select>       </td>
       <td  class="formLabel">&nbsp;</td>
       <td ><html:hidden property="configSeqID"/>&nbsp;</td>
      </tr>
      <tr>
       <td  nowrap class="formLabel">Emp.Pwd.Generation:</td>
       <td >
        <html:select property="pwdGeneralTypeID" styleClass="selectBox selectBoxMedium" >
         <html:optionsCollection name="empPwdGen" label="cacheDesc" value="cacheId" />
        </html:select>       </td>
       <td class="formLabel">&nbsp;</td>
       <td ><html:hidden property="configSeqID"/>&nbsp;</td>
      </tr>
      <tr>
       <td  nowrap class="formLabel">Intimation Access:</td>
       <td >
       		<html:select property="intimationAccessTypeID" styleClass="selectBox selectBoxMedium" >
       			<html:optionsCollection name="intimationAccess" label="cacheDesc" value="cacheId" />
       		</html:select>
       </td>
      </tr>
      <tr>
       <td nowrap class="formLabel">Online Assistance:</td>
       <td>
       		<html:select property="onlineAssTypeID" styleClass="selectBox selectBoxMedium" onchange="hideRating()" >
       			<html:optionsCollection name="onlineAssistance" label="cacheDesc" value="cacheId" />
       		</html:select>
       </td>
      </tr>
<!-- added for koc 1349 -->
       <tr>
       <td nowrap class="formLabel">Welness Login:</td>
       <td>
       		<html:select property="wellnessAccessTypeID" styleClass="selectBox selectBoxMedium" >
       			<html:optionsCollection name="wellnessAccess" label="cacheDesc" value="cacheId" />
       		</html:select>
       </td>
       </tr>
<!-- end added for koc 1349 -->
      <tr>
       <td nowrap class="formLabel">Password Validity:</td>
       <td><html:text property="passwordValidity"  styleClass="textBox textBoxSmall" maxlength="3"/>&nbsp;(days)</td>
	   <td class="formLabel">&nbsp;</td>
	   <td>&nbsp;</td>
      </tr>
      <tr>
      <td nowrap class="formLabel">Show Alert:</td>
      <td colspan="2"><html:text property="alert"  styleClass="textBox textBoxSmall" maxlength="3"/>&nbsp;(days to expire)</td>
	  <td class="formLabel">&nbsp;</td>
	  <td>&nbsp;</td>
      </tr>
   <%--wrongAttempts field added as per KOC 1257  11PP --%>
       <tr>
      <td nowrap class="formLabel">No.of Attempts:</td>
      <td colspan="2"><html:text property="wrongAttempts"  styleClass="textBox textBoxSmall" maxlength="1"/>&nbsp;(to lock)</td>
	  <td class="formLabel">&nbsp;</td>
	  <td>&nbsp;</td>
      </tr>
      <tr>
       <td nowrap class="formLabel">Parents and Parent-In-Laws:</td>
      <td>
    <html:select property="relshipCombintnTypeID" styleClass="selectBox selectBoxMedium" onchange="javascript:hidemembers()" >
       			<html:optionsCollection name="relationshipCombination" label="cacheDesc" value="cacheId" />
       		</html:select>
       </td>

		        <!-- Start Modification As per  KOC 1159 (Aravind) Change Request -->
          <logic:match name="frmWebConfig" property="relshipCombintnTypeID" value="RCA">
     <td  id="noOfMembers"  style="display:" class="formLabel" >No Of Members
              <!--   <html:text property="noOfMembers"  styleClass="textBox textBoxSmall" maxlength="3"/>&nbsp;</td> -->
              <html:select property="noOfMembers" styleClass="selectBox selectBoxMedium" onchange="javascript:hidememberslist()">
       		<html:option value="">Select from List</html:option>
       		<html:option value="1">1</html:option>
       		<html:option value="2">2</html:option>
       		<html:option value="3">3</html:option>
       		<html:option value="4">4</html:option>
       		</html:select>
        </td>
           </logic:match>
           <logic:notMatch name="frmWebConfig" property="relshipCombintnTypeID" value="RCA">
            <td  id="noOfMembers"  style="display: none" class="formLabel" >No Of Members
           <!--   <html:text property="noOfMembers"  styleClass="textBox textBoxSmall" maxlength="3"/>&nbsp;</td>-->
                   <html:select property="noOfMembers" styleClass="selectBox selectBoxMedium" onchange="javascript:hidememberslist()">
       		       <html:option value="">Select from List</html:option>
       		       <html:option value="1">1</html:option>
       		       <html:option value="2">2</html:option>
       		       <html:option value="3">3</html:option>
       		       <html:option value="4">4</html:option>
       		       </html:select>
       		       </td>
            </logic:notMatch>
       <!-- End Modification As per  KOC 1159 (Aravind) Change Request -->
      </tr>
	  <tr>
            <!--  koc note change s -->                        
           <logic:equal name="frmWebConfig" property="noOfMembers" value="2">
            <td id="noOf"  style="display:" class="formLabel" >Opposite/Common:
                  
           	<html:select property="noOf" styleClass="selectBox selectBoxMedium">       			
       			<html:option value="OGE">Opposite Gender</html:option>
       			<html:option value="CGE">Common Gender</html:option>       			
       		</html:select>       		    		
        	</td>  
        	</logic:equal>         
        	<logic:notEqual name="frmWebConfig" property="noOfMembers" value="2">
        	<td id="noOf"  style="display: none" class="formLabel" >Opposite/Common: 
        	      
                   <html:select property="noOf" styleClass="selectBox selectBoxMedium">       		       		
       		       		<html:option value="OGE">Opposite Gender</html:option>
       		       		<html:option value="CGE">Common Gender</html:option>       		       		
       		       </html:select>
       		  </td>
       		</logic:notEqual>       		       	
            <!-- new note e  -->            
       <!-- End Modification As per  KOC 1159 (Aravind) Change Request -->
      </tr>
      <logic:match name="frmWebConfig" property="onlineAssTypeID" value="OAA">
      <tr id="rating" style="display:">
       <td nowrap class="formLabel">Rating:</td>
       <td>
       		<html:select property="ratingGeneralTypeID" styleClass="selectBox selectBoxMedium"  >
       			<html:optionsCollection name="onlineRating" label="cacheDesc" value="cacheId" />
       		</html:select>
       </td>
      </tr>
      </logic:match>
      <logic:notMatch name="frmWebConfig" property="onlineAssTypeID" value="OAA">
      <tr id="rating" style="display:none;">
       <td nowrap class="formLabel">Rating:</td>
       <td>
       		<html:select property="ratingGeneralTypeID" styleClass="selectBox selectBoxMedium"  >
       			<html:optionsCollection name="onlineRating" label="cacheDesc" value="cacheId" />
       		</html:select>
       </td>
      </tr>
      </logic:notMatch>
     </table>
	</fieldset>
	<fieldset>
	<legend>Online Enrollment  Cancellation </legend>
		<table width="99%"  border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
		    <tr>
			    <td width="30%" nowrap class="formLabel">HR:</td>
			    <td width="30%"><html:select property="groupCntCancelGenTypeID"  styleClass="selectBox selectBoxMedium"  >
			      <html:optionsCollection name="webloginCancellation" label="cacheDesc" value="cacheId" />
			    </html:select>
			    </td>
			    <td width="28%" class="formLabel">&nbsp;</td>
			    <td width="12%">&nbsp;</td>
		    </tr>
	    </table>
	</fieldset>
<!--	<fieldset>
	<legend>Softcopy Uploading</legend>
		<table width="99%"  border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
		    <tr>
			    <td width="35%" nowrap class="formLabel">Employee:</td>
			    <td width="45%">
			    	<html:select property="softCopyGenTypeID" styleClass="selectBox selectBoxMedium" >
			    		<html:optionsCollection name="webloginSoftcopy" label="cacheDesc" value="cacheId" />
			    	</html:select>
			    </td>
			    <td width="14%" class="formLabel">&nbsp;</td>
			    <td width="6%"><html:hidden property="configSeqID"/>&nbsp;</td>
		    </tr>
	    </table>
	</fieldset> -->
	<fieldset>
	<legend>User Notification</legend>
		<table width="99%"  border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
		    <tr>
			    <td width="30%" nowrap class="formLabel">Notification Details:</td>
			    <td width="70%">
				<html:textarea property="notiDetails" styleClass="textBox textAreaMediumht"/>
			    </td>
		    </tr>
	    </table>
	</fieldset>
	<fieldset>
	<legend>Customized Report Configuration</legend>
		<table width="99%"  border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
		    <tr>
			    <td width="30%" nowrap class="formLabel">Report From:</td>
			    <td width="30%">
			    	<html:text property="reportFrom"  onblur="javascript:onChangeFrom();" styleClass="textBox textBoxSmall" maxlength="2"/>
			    </td>
			    <td width="28%" class="formLabel">&nbsp;</td>
			    <td width="12%">&nbsp;</td>
		    </tr>
		    <tr>
			    <td width="30%" nowrap class="formLabel">Report To:</td>
			    <td width="30%">
			    	<html:text property="reportTo" styleClass="textBox textBoxSmall" maxlength="2"/>
			    </td>
			    <td width="28%" class="formLabel">&nbsp;</td>
			    <td width="12%">&nbsp;</td>
		    </tr>
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
	<script language="javascript">
		hideMailGenType();
	</script>
 	<input type="hidden" name="child" value="">
  	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
  	<INPUT TYPE="hidden" NAME="mode" VALUE="">
  	<html:hidden property="alert"/>
  	<html:hidden property="passwordValidity"/>
</html:form>
