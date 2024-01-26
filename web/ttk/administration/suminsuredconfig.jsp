<%
/**
 * @ (#)  suminsuredconfig.jsp 
 * Project      : TTK Healthcare Services
 * File         : suminsuredconfig.jsp
 * Author       : Satya Moganti
 * Company      : RCS 
 * 
 *
 * @author       : Satya Moganti
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 *
 * Modified by   :  
 * Modified date :  
 * Reason        :  Created as per Change request KOC1140(SumInsuredRestriction)
	 
 */
 %>
 <%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import=" com.ttk.common.TTKCommon" %>
<!DOCTYPE html>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/suminsuredconfig.js"></script>
	<html:form action="/SumInsuredConfigurationAction.do" method="post">
	
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td>SumInsured Details -<bean:write name="frmConfigSumInsured" property="caption"/></td>
      	
      	<td align="right"></td>
        <td align="right" ></td>
      	
      </tr>
    </table>
    <div class="contentArea" id="contentArea">
	<html:errors/>
<logic:notEmpty name="frmConfigSumInsured" property="preAuthClaimsExistAlert">
	<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <bean:write name="frmConfigSumInsured" property="preAuthClaimsExistAlert"/>
	        </td>
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
 	<legend>SUMINSURED RESTRICTION</legend>
    <table align="center" class="formContainer" border="0" cellspacing="1" cellpadding="1">
      <tr>
        <td id="suminsuredrestrictionYN" class="formLabel">SumInsured Restriction Applied : 
			        
	          <html:select property="suminsuredrestrictionYN" name="frmConfigSumInsured"  style="width:100px;" onchange="javascript:showHideType();" styleClass="selectBox selectBoxMedium">
		      	<html:option value="N">NO</html:option>
		      	 <html:option value="Y">YES</html:option> 
		      	 </html:select>
		 </td>
		   
		 </tr>   
		 <!-- Relation Ship -->
		<logic:match property="suminsuredrestrictionYN" name="frmConfigSumInsured" value="Y"> 
		<tr id="suminsuredconfiguaration" style="display:">
    	   <td class="formLabel"> 
				<table cellpadding="0" cellspacing="0">
					<tr>
	                <td class="formLabel">Maximum Amount
  		               <html:text property="fixedAmtRestriction" name="frmConfigSumInsured"  styleId="search8" styleClass="textBox textBoxSmall" maxlength="12"/><span class="mandatorySymbol">*</span>
                   &nbsp;</td>
                  <td class="formLabel"> Or Percentage
  		             <html:text property="percentageRestriction" name="frmConfigSumInsured"  styleId="search8" styleClass="textBox textBoxSmall" maxlength="2"/><b>%</b><span class="mandatorySymbol">*</span>
                  &nbsp;</td>
       
		         <td class="formLabel">
                     <html:select property="status" name="frmConfigSumInsured"  style="width:115px;"  styleClass="selectBox selectBoxMedium" >
		             <html:option value="LL">Lower</html:option>
		            <!-- <html:option value="HH">Higher</html:option> --> 
		      	 </html:select>&nbsp;</td> 
		      	 <td class="formLabel">of SumInsured on&nbsp;</td>
		      	  <td class="formLabel">Member RelationShip&nbsp;
                      <input type="text" name="membersrelation" id="membersrelation" style="search8" class="textBox textBoxSmall" value="<bean:write name="frmConfigSumInsured" property="membersrelation"/>"><A NAME="relation" ID="relation" HREF="#" onClick="javascript:openList('membersrelation','REL');" ><img src="/ttk/images/EditIcon.gif" width="16" height="16" alt="select relations" border="0" align="absmiddle"></a><span class="mandatorySymbol">*</span> 
                  </td>
		        </tr>
				</table>
				</td>         
			
   </tr>
   </logic:match>
   <logic:notMatch property="suminsuredrestrictionYN" name="frmConfigSumInsured" value="Y">
   <tr id="suminsuredconfiguaration" style="display: none">
    	   <td class="formLabel"> 
				<table cellpadding="0" cellspacing="0">
					<tr>
	         		
  	
       
                   <td class="formLabel">Maximum Amount
  		               <html:text property="fixedAmtRestriction" name="frmConfigSumInsured"  styleId="search8" styleClass="textBox textBoxSmall" maxlength="12"/><span class="mandatorySymbol">*</span>
                   &nbsp;</td>
                  <td class="formLabel"> Or Percentage
  		             <html:text property="percentageRestriction" name="frmConfigSumInsured"  styleId="search8" styleClass="textBox textBoxSmall" maxlength="2"/><b>%</b><span class="mandatorySymbol">*</span>
                  &nbsp;</td>
       
		         <td class="formLabel">
                     <html:select property="status" name="frmConfigSumInsured"  style="width:115px;"  styleClass="selectBox selectBoxMedium" >
		             <html:option value="LL">Lower</html:option>
		            <!-- <html:option value="HH">Higher</html:option> --> 
		      	 </html:select>&nbsp;</td> 
		      	 <td class="formLabel">of SumInsured on&nbsp;</td>
		      	  <td class="formLabel">Member RelationShip&nbsp;
                      <input type="text" name="membersrelation" id="membersrelation" style="search8" class="textBox textBoxSmall" value="<bean:write name="frmConfigSumInsured" property="membersrelation"/>"><A NAME="relation" ID="relation" HREF="#" onClick="javascript:openList('membersrelation','REL');" ><img src="/ttk/images/EditIcon.gif" width="16" height="16" alt="select relations" border="0" align="absmiddle"></a><span class="mandatorySymbol">*</span> 
                  </td>
		        </tr>
				</table>
				</td>         
			
   </tr>
   
   </logic:notMatch>
   
  </table>
	</fieldset>

	
     <table align="center" class="formContainer" border="0" cellspacing="1" cellpadding="1">
   		  <logic:match property="suminsuredrestrictionYN" name="frmConfigSumInsured" value="Y"> 
	          <tr id="suminsuredconfiguarationage" style="display:">
    	            <td class="formLabel"> 
    	            <fieldset>
		                     <legend>SUM INSURED RESTRICTION ON AGE</legend>
						<table cellpadding="0" cellspacing="0">
					    <tr>
	                        <td class="formLabel">&nbsp;&nbsp;Dependent relationship beyond &nbsp;
	                              <html:text property="ageRestricted" name="frmConfigSumInsured"  styleId="ageRestricted" styleClass="textBox textBoxSmall" maxlength="2"/>
	                               </td>
	                                   <%-- <html:select property="ageRestricted" name="frmConfigSumInsured" styleId="ageRestricted" style="width:115px;"  styleClass="selectBox selectBoxSmall" >
		                         <html:option value="70">70</html:option>
		                    	 </html:select>&nbsp;</td>  --%>
		      	                   <td class="formLabel"> years of age is limited to Amount &nbsp;
		         		               <html:text property="ageRestrictedAmount" name="frmConfigSumInsured"  styleId="ageRestrictedAmount" styleClass="textBox textBoxSmall" maxlength="12"/>
                                    &nbsp;</td>
		                </tr>
				         </table>
				     </fieldset>
		           </td>         
			
              </tr>
	     </logic:match>
         <logic:notMatch property="suminsuredrestrictionYN" name="frmConfigSumInsured" value="Y">
              <tr id="suminsuredconfiguarationage" style="display: none">
      	          <td class="formLabel"> 
      	                   <fieldset>
		                   <legend>SUM INSURED RESTRICTION ON AGE</legend>
						   <table cellpadding="0" cellspacing="0">
					         <tr>
					             
	                               <td class="formLabel">&nbsp;&nbsp;Dependent relationship beyond &nbsp;
	                                        <html:text property="ageRestricted" name="frmConfigSumInsured"  styleId="ageRestricted" styleClass="textBox textBoxSmall" maxlength="2"/>
	                                      <%--   <html:select property="ageRestricted" name="frmConfigSumInsured" styleId="ageRestricted"  style="width:115px;"  styleClass="selectBox selectBoxSmall" >
		                                    <html:option value="70">70</html:option>
		           	                        </html:select>&nbsp;--%>
		           	                </td> 
		      	                     <td class="formLabel"> years of age is limited to Amount&nbsp;
		      	                         <html:text property="ageRestrictedAmount" name="frmConfigSumInsured"  styleId="ageRestrictedAmount" styleClass="textBox textBoxSmall" maxlength="12"/>
                                     &nbsp;</td>
		                     </tr>
				        </table>
				         </fieldset>
		          </td>         
		     </tr>
        </logic:notMatch>
     
   </table>
  
    
		
		
	 
	
     <!-- End  Changes as per ChangeRequest(Neetha) KOC1140 on 18 march 2012 -->
 
	<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">  	
	    <%
	       if(TTKCommon.isAuthorized(request,"Edit"))
	       {
    	%>    
	       	<button type="button" name="Button"  accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
	       	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	    <%
	    	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		%>
	       	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		</td>
	  </tr>
	</table>
	</div>
	<input type="hidden" name="mode">
    <html:hidden property="caption" />
	</html:form>