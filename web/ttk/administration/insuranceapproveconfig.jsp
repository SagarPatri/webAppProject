
<%
/**
 * @ (#)  bajaj separation 1274A
 * Reason        :  
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ page import=" com.ttk.common.TTKCommon"%>

<script  src="/ttk/scripts/validation.js" type="text/javascript"></script>
<script type="text/javascript"	src="/ttk/scripts/administration/insuranceapproveconfig.js"></script>
	<%
  boolean viewmode=true;
  %>
<!-- S T A R T : Content/Form Area -->
<html:form action="/InsuranceApproveConfiguration.do" method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0"	cellpadding="0">
		<tr>
			<td>Refer to Insurer -<bean:write
				name="frmConfigInsuranceApprove" property="caption" /></td>
			<td align="right"></td>
			<td align="right"></td>
		</tr>
	</table>

	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors />
	 <!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display: " border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
					width="16" height="16" align="absmiddle">&nbsp; 
					<bean:message name="updated" scope="request" /></td>
			</tr>
		</table>
	</logic:notEmpty> <!-- E N D : Success Box --> <!-- S T A R T : Form Fields -->
	
	
	
          <fieldset>
                            <legend>Healthcare Configuration for Cashless</legend>
                            <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel">Cashless: 
	        		         <html:checkbox name="frmConfigInsuranceApprove" property="insPatYN"  value="Y" styleId="insPatYN" onclick="showHidePreauth();" />
	 					      <input type="hidden" name="insPatYN" value="N">	
	        		        </td>
	        		        </tr>
	        		        <!-- added for bajaj enhancement1 -->
	        		          <logic:match name="frmConfigInsuranceApprove" property="insPatYN" value="Y" >
		                      <tr id="allowedPatYNid" style="display: ">
			                   <td class="formLabel">Approval of Cashless &nbsp;&nbsp;
			                   <html:select property="allowedPatYN" name="frmConfigInsuranceApprove" onchange="javascript:showDataPat();" style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="N">NO</html:option>
				                     <html:option value="Y">YES</html:option>
			                         </html:select>
			                    </td>
			                    <tr>
			                   </logic:match>
			                    <logic:notMatch name="frmConfigInsuranceApprove" property="insPatYN" value="Y" >
		                       <tr id="allowedPatYNid" style="display: none ">
			                   <td class="formLabel">Approval of Cashless &nbsp;&nbsp;
			                   <html:select property="allowedPatYN" styleId="allowedPatYN" name="frmConfigInsuranceApprove" onchange="javascript:showDataPat();" style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="N">NO</html:option>
				                     <html:option value="Y">YES</html:option>
			                         </html:select>
			                    </td>
			                    <tr>
			                   </logic:notMatch>
			                        <!-- end added for bajaj enhancement1 -->
			                  <td class="formLabel">&nbsp;</td>
			                  <td class="formLabel">&nbsp;</td>
		                      </tr>
		  	                </table>
         

		                    <logic:match property="allowedPatYN" name="frmConfigInsuranceApprove"value="Y">
		                              <table align="center" class="formContainer" border="0" cellspacing="0"cellpadding="0">
			                            
			                            <tr id="insuranceapproveconfigYNpat" style="display: ">
			                               <td class="formLabel" > Insurer Reference will be applicable if Cashless Approved Amount&nbsp;
					                        <html:select property="patOperator" name="frmConfigInsuranceApprove" style="width:100px;" styleClass="selectBox selectBoxMedium">
							                     <html:option value="EQ">Equal To</html:option>
							                     <html:option value="GT">More Than</html:option>
							                     <html:option value="LT">Less Than</html:option>
					                            </html:select>&nbsp;&nbsp;					
					                      <html:text property="patApproveAmountLimit" styleClass="textBox textBoxSmall" maxlength="13" /></td>
				                            <td class="formLabel">&nbsp;</td>
				                        </tr>
				                      
			                        </table>
			                 </logic:match>
			                 
			                 <logic:notMatch property="allowedPatYN" name="frmConfigInsuranceApprove" value="Y">
			                         <table align="center" class="formContainer" border="0" cellspacing="0"cellpadding="0">
			                            <tr id="insuranceapproveconfigYNpat" style="display: none">
				    	                    <td class="formLabel" >Insurer Reference will be applicable if Cashless Approved Amount&nbsp;
				    	                             <html:select property="patOperator" name="frmConfigInsuranceApprove" style="width:100px;" styleClass="selectBox selectBoxMedium">
							                   <html:option value="EQ">Equal To</html:option>
							                   <html:option value="GT">More Than</html:option>
							                   <html:option value="LT">Less Than</html:option>
							                   </html:select> &nbsp;&nbsp;
							                   <html:text property="patApproveAmountLimit"	styleClass="textBox textBoxSmall" maxlength="13" /></td>
                                             <td class="formLabel" >&nbsp;</td>
                                          </tr>
                                     </table>
                            </logic:notMatch>
                            <logic:match name="frmConfigInsuranceApprove" property="insPatYN" value="Y" >
						<table align="center" class="formContainer" border="0" cellspacing="0"cellpadding="0">
							 <tr id="allowedPatRejYNid" style="display: ">
                                            <td class="formLabel"> Rejection Of Cashless&nbsp;&nbsp;&nbsp;</td>
				                                            <td><html:select property="rejectionYN" styleId="rejectionYN" name="frmConfigInsuranceApprove" style="width:100px;" styleClass="selectBox selectBoxMedium" onclick="javascript:showNoticationData()"  >
							                                       <html:option value="N">No</html:option>
							                                      <html:option value="Y">Yes</html:option>
							                                    
							                                     </html:select>
							                                   </td>
							                               <td class="formLabel" >&nbsp;</td>
			                               </tr>
			                               </table>
			             
			              </logic:match>
			              <logic:notMatch name="frmConfigInsuranceApprove" property="insPatYN" value="Y" >
					<table align="center" class="formContainer" border="0" cellspacing="0"cellpadding="0">
							 <tr id="allowedPatRejYNid" style="display: none ">
                                            <td class="formLabel"> Rejection Of Cashless&nbsp;&nbsp;&nbsp;</td>
				                                            <td><html:select property="rejectionYN" styleId="rejectionYN" name="frmConfigInsuranceApprove" style="width:100px;" styleClass="selectBox selectBoxMedium" onclick="javascript:showNoticationData()"  >
							                                       <html:option value="N">No</html:option>
							                                      <html:option value="Y">Yes</html:option>
							                                    
							                                     </html:select>
							                                   </td>
							                               <td class="formLabel" >&nbsp;</td>
			                               </tr>
			        </table>
			              </logic:notMatch>
			              
			                                <logic:match property="rejectionYN" name="frmConfigInsuranceApprove" value="Y">
			                                <logic:match property="allowedPatYN" name="frmConfigInsuranceApprove" value="N">
			                                <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
			                                      <tr class="formLabel" id="notificationYN" style="display: ">
                                                         <td class="formLabel"> Notification Cashless &nbsp;&nbsp;</td>
                                                         <td><html:radio property="notificationFlag" styleId="notificationFlag" name="frmConfigInsuranceApprove" value="MAIL">Online Mails</html:radio></td>
					                                     <td><html:radio property="notificationFlag" styleId="notificationFlag" name="frmConfigInsuranceApprove" value="ADOBE">Adobe Forms Attachment</html:radio></td>    					                                     
                                                   </tr>
                                                   </table>
                                                   </logic:match>
	                                               </logic:match>
	                                                  
	                                                    <logic:match property="rejectionYN" name="frmConfigInsuranceApprove" value="N">
			                                <logic:match property="allowedPatYN" name="frmConfigInsuranceApprove" value="Y">
			                                <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
			                                      <tr class="formLabel" id="notificationYN" style="display: ">
                                                         <td class="formLabel"> Notification Cashless &nbsp;&nbsp;</td>
                                                         <td><html:radio property="notificationFlag" styleId="notificationFlag" name="frmConfigInsuranceApprove" value="MAIL">Online Mails</html:radio></td>
					                                     <td><html:radio property="notificationFlag" styleId="notificationFlag" name="frmConfigInsuranceApprove" value="ADOBE">Adobe Forms Attachment</html:radio></td>    					                                     
                                                   </tr>
                                                   </table>
                                                   </logic:match>
	                                               </logic:match>
	                                                <logic:match property="rejectionYN" name="frmConfigInsuranceApprove" value="Y">
			                                <logic:match property="allowedPatYN" name="frmConfigInsuranceApprove" value="Y">
			                                <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
			                                      <tr class="formLabel" id="notificationYN" style="display: ">
                                                         <td class="formLabel"> Notification Cashless &nbsp;&nbsp;</td>
                                                         <td><html:radio property="notificationFlag" styleId="notificationFlag" name="frmConfigInsuranceApprove" value="MAIL">Online Mails</html:radio></td>
					                                     <td><html:radio property="notificationFlag" styleId="notificationFlag" name="frmConfigInsuranceApprove" value="ADOBE">Adobe Forms Attachment</html:radio></td>    					                                     
                                                   </tr>
                                                   </table>
                                                   </logic:match>
	                                               </logic:match>
	                                                   
	                                                   
	                                                   <logic:notMatch property="rejectionYN" name="frmConfigInsuranceApprove" value="Y">
	                                                   <logic:notMatch property="allowedPatYN" name="frmConfigInsuranceApprove" value="Y">
	                                                   <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
			                                             <tr class="formLabel" id="notificationYN" style="display: none">
                                                            <td class="formLabel"> Notification Cashless &nbsp;&nbsp;</td>
                                                            <td><html:radio property="notificationFlag" name="frmConfigInsuranceApprove" styleId="notificationFlag" value="MAIL">Online Mails</html:radio></td>
					                                        <td><html:radio property="notificationFlag" name="frmConfigInsuranceApprove"  styleId="notificationFlag" value="ADOBE">Adobe Forms Attachment</html:radio></td>   				                                         
                                                         </tr>
                                                         </table>
                                                         </logic:notMatch>
	                                                    </logic:notMatch>
	                                                   
	                                                  
	                                                 
	                                                    <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
	            <logic:match property="rejectionYN" name="frmConfigInsuranceApprove" value="Y">		    
	            <logic:match property="allowedPatYN" name="frmConfigInsuranceApprove" value="N">		
		  				  <tr id="timeInHrsandMin" style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Cashless</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrs" styleId="timeInHrs" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMins"  styleId="timeInMins" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset> 
		           </td>         
			   </tr>
			  </logic:match>
		   </logic:match>
			
		   <logic:match property="rejectionYN" name="frmConfigInsuranceApprove" value="N">		    
	            <logic:match property="allowedPatYN" name="frmConfigInsuranceApprove" value="Y">		
		  				  <tr id="timeInHrsandMin" style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Cashless</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrs" styleId="timeInHrs" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMins"  styleId="timeInMins" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset> 
		           </td>         
			   </tr>
			  </logic:match>
		   </logic:match>
		   <logic:match property="rejectionYN" name="frmConfigInsuranceApprove" value="Y">	
			<logic:match property="allowedPatYN" name="frmConfigInsuranceApprove" value="Y">	   
		    <tr id="timeInHrsandMin" style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Cashless</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrs" styleId="timeInHrs" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMins"  styleId="timeInMins" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset> 
		           </td>         
			   </tr>	
			   </logic:match>		  
		   </logic:match>
		
			<logic:notMatch property="rejectionYN" name="frmConfigInsuranceApprove" value="Y">	
			<logic:notMatch property="allowedPatYN" name="frmConfigInsuranceApprove" value="Y">	   
		    <tr id="timeInHrsandMin" style="display: none">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Cashless</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrs" styleId="timeInHrs" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMins"  styleId="timeInMins" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset> 
		           </td>         
			   </tr>	
			   </logic:notMatch>		  
		   </logic:notMatch>
		    
		   </table>
                  </fieldset>


<!-- //added for bajaj enh1 -->
<fieldset>
                            <legend>Healthcare Configuration for Claims</legend>
                            <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel">Claims Cashless: 
	        		         <html:checkbox name="frmConfigInsuranceApprove" property="insClmYN"  value="Y" styleId="insClmYN" onclick="showHideClaim();" />
	 					      <input type="hidden" name="insClmYN" value="N">	
	        		        </td>
	        		        </tr>
	        		        <!-- added for bajaj enhancement1 -->
	        		          <logic:match name="frmConfigInsuranceApprove" property="insClmYN" value="Y" >
		                      <tr id="allowedClmYNid" style="display: ">
			                   <td class="formLabel">Approval of Claim Cashless &nbsp;&nbsp;
			                   <html:select property="allowedClmYN" name="frmConfigInsuranceApprove" onchange="javascript:showDataClm();" style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="N">NO</html:option>
				                     <html:option value="Y">YES</html:option>
			                         </html:select>
			                    </td>
			                    <tr>
			                   </logic:match>
			                    <logic:notMatch name="frmConfigInsuranceApprove" property="insClmYN" value="Y" >
		                       <tr id="allowedClmYNid" style="display: none ">
			                   <td class="formLabel">Approval of Claim Cashless &nbsp;&nbsp;
			                   <html:select property="allowedClmYN" styleId="allowedClmYN" name="frmConfigInsuranceApprove" onchange="javascript:showDataClm();" style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="N">NO</html:option>
				                     <html:option value="Y">YES</html:option>
			                         </html:select>
			                    </td>
			                    <tr>
			                   </logic:notMatch>
			                        <!-- end added for bajaj enhancement1 -->
			                  <td class="formLabel">&nbsp;</td>
			                  <td class="formLabel">&nbsp;</td>
		                      </tr>
		  	                </table>
         

		                    <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove"value="Y">
		                              <table align="center" class="formContainer" border="0" cellspacing="0"cellpadding="0">
			                            
			                            <tr id="insuranceapproveconfigYNclm" style="display: ">
			                               <td class="formLabel" > Insurer Reference will be applicable if Claim Cashless Approved Amount&nbsp;
					                        <html:select property="clmOperator" name="frmConfigInsuranceApprove" style="width:100px;" styleClass="selectBox selectBoxMedium">
							                     <html:option value="EQ">Equal To</html:option>
							                     <html:option value="GT">More Than</html:option>
							                     <html:option value="LT">Less Than</html:option>
					                            </html:select>&nbsp;&nbsp;					
					                      <html:text property="clmApproveAmountLimit" styleClass="textBox textBoxSmall" maxlength="13" /></td>
				                            <td class="formLabel">&nbsp;</td>
				                        </tr>
				                      
			                        </table>
			                 </logic:match>
			                 
			                 <logic:notMatch property="allowedClmYN" name="frmConfigInsuranceApprove" value="Y">
			                         <table align="center" class="formContainer" border="0" cellspacing="0"cellpadding="0">
			                            <tr id="insuranceapproveconfigYNclm" style="display: none">
				    	                    <td class="formLabel" >Insurer Reference will be applicable if Claim Cashless Approved Amount&nbsp;
				    	                             <html:select property="clmOperator" name="frmConfigInsuranceApprove" style="width:100px;" styleClass="selectBox selectBoxMedium">
							                   <html:option value="EQ">Equal To</html:option>
							                   <html:option value="GT">More Than</html:option>
							                   <html:option value="LT">Less Than</html:option>
							                   </html:select> &nbsp;&nbsp;
							                   <html:text property="clmApproveAmountLimit"	styleClass="textBox textBoxSmall" maxlength="13" /></td>
                                             <td class="formLabel" >&nbsp;</td>
                                          </tr>
                                     </table>
                            </logic:notMatch>
                            <logic:match name="frmConfigInsuranceApprove" property="insClmYN" value="Y" >
							<table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
							<tr id="allowedClmRejYNid" style="display: ">
                                            <td class="formLabel"> Rejection Of Claim Cashless&nbsp;&nbsp;&nbsp;</td>
				                                            <td><html:select property="rejectionClmYN" styleId="rejectionClmYN" name="frmConfigInsuranceApprove" style="width:100px;" styleClass="selectBox selectBoxMedium" onclick="javascript:showNoticationDataClm()"  >
							                                       <html:option value="N">No</html:option>
							                                      <html:option value="Y">Yes</html:option>
							                                    
							                                     </html:select>
							                                   </td>
							                               <td class="formLabel" >&nbsp;</td>
			                               </tr>
			                     </table>
			                     </logic:match>
			                     <logic:notMatch name="frmConfigInsuranceApprove" property="insClmYN" value="Y" >
							<table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
							 <tr id="allowedClmRejYNid" style="display: none ">
                                            <td class="formLabel"> Rejection Of Claim Cashless&nbsp;&nbsp;&nbsp;</td>
				                                            <td><html:select property="rejectionClmYN" styleId="rejectionClmYN" name="frmConfigInsuranceApprove" style="width:100px;" styleClass="selectBox selectBoxMedium" onclick="javascript:showNoticationDataClm()"  >
							                                       <html:option value="N">No</html:option>
							                                      <html:option value="Y">Yes</html:option>
							                                    
							                                     </html:select>
							                                   </td>
							                               <td class="formLabel" >&nbsp;</td>
			                               </tr>
			                     </table>
			                     </logic:notMatch>
			                     
			                                <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="Y">
			                                <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="N">
			                                <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
			                                      <tr class="formLabel" id="notificationClmYN" style="display: ">
                                                         <td class="formLabel"> Notification Claim Cashless &nbsp;&nbsp;</td>
                                                         <td><html:radio property="notificationClmFlag" styleId="notificationClmFlag" name="frmConfigInsuranceApprove" value="MAIL">Online Mails</html:radio></td>
					                                     <td><html:radio property="notificationClmFlag" styleId="notificationClmFlag" name="frmConfigInsuranceApprove" value="ADOBE">Adobe Forms Attachment</html:radio></td>                                         
                                                   </tr>
                                                   </table>
                                                   </logic:match>
	                                               </logic:match>
	                                                <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="N">
			                                <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="Y">
			                                <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
			                                      <tr class="formLabel" id="notificationClmYN" style="display: ">
                                                         <td class="formLabel"> Notification Claim Cashless &nbsp;&nbsp;</td>
                                                         <td><html:radio property="notificationClmFlag" styleId="notificationClmFlag" name="frmConfigInsuranceApprove" value="MAIL">Online Mails</html:radio></td>
					                                     <td><html:radio property="notificationClmFlag" styleId="notificationClmFlag" name="frmConfigInsuranceApprove" value="ADOBE">Adobe Forms Attachment</html:radio></td>                                         
                                                   </tr>
                                                   </table>
                                                   </logic:match>
	                                               </logic:match>
	                                                <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="Y">
			                                <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="Y">
			                                <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
			                                      <tr class="formLabel" id="notificationClmYN" style="display: ">
                                                         <td class="formLabel"> Notification Claim Cashless &nbsp;&nbsp;</td>
                                                         <td><html:radio property="notificationClmFlag" styleId="notificationClmFlag" name="frmConfigInsuranceApprove" value="MAIL">Online Mails</html:radio></td>
					                                     <td><html:radio property="notificationClmFlag" styleId="notificationClmFlag" name="frmConfigInsuranceApprove" value="ADOBE">Adobe Forms Attachment</html:radio></td>                                         
                                                   </tr>
                                                   </table>
                                                   </logic:match>
	                                               </logic:match>
	                                                   <logic:notMatch property="rejectionClmYN" name="frmConfigInsuranceApprove" value="Y">
	                                                   <logic:notMatch property="allowedClmYN" name="frmConfigInsuranceApprove" value="Y">
	                                                   <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
			                                             <tr class="formLabel" id="notificationClmYN" style="display: none">
                                                            <td class="formLabel"> Notification Claim Cashless &nbsp;&nbsp;</td>
                                                            <td><html:radio property="notificationClmFlag" name="frmConfigInsuranceApprove" styleId="notificationClmFlag" value="MAIL">Online Mails</html:radio></td>
					                                        <td><html:radio property="notificationClmFlag" name="frmConfigInsuranceApprove"  styleId="notificationClmFlag" value="ADOBE">Adobe Forms Attachment</html:radio></td>    					                                         
                                                         </tr>
                                                         </table>
                                                         </logic:notMatch>
	                                                    </logic:notMatch>
	                                                    
	                                                     <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel">Claims Reimbersement: 
	        		         <html:checkbox name="frmConfigInsuranceApprove" property="insClmRemYN"  value="Y" styleId="insClmYN" onclick="showHideClaimRem();" />
	 					      <input type="hidden" name="insClmRemYN" value="N">	
	        		        </td>
	        		        </tr>
	        		        <!-- added for bajaj enhancement1 -->
	        		          <logic:match name="frmConfigInsuranceApprove" property="insClmRemYN" value="Y" >
		                      <tr id="allowedClmRemYNid" style="display: ">
			                   <td class="formLabel">Approval of Claim Reimbersement &nbsp;&nbsp;
			                   <html:select property="allowedClmRemYN" name="frmConfigInsuranceApprove" onchange="javascript:showDataClmRem();" style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="N">NO</html:option>
				                     <html:option value="Y">YES</html:option>
			                         </html:select>
			                    </td>
			                    <tr>
			                   </logic:match>
			                    <logic:notMatch name="frmConfigInsuranceApprove" property="insClmRemYN" value="Y" >
		                       <tr id="allowedClmRemYNid" style="display: none ">
			                   <td class="formLabel">Approval of Claim Reimbersement &nbsp;&nbsp;
			                   <html:select property="allowedClmRemYN" styleId="allowedClmRemYN" name="frmConfigInsuranceApprove" onchange="javascript:showDataClmRem();" style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="N">NO</html:option>
				                     <html:option value="Y">YES</html:option>
			                         </html:select>
			                    </td>
			                    <tr>
			                   </logic:notMatch>
			                        <!-- end added for bajaj enhancement1 -->
			                  <td class="formLabel">&nbsp;</td>
			                  <td class="formLabel">&nbsp;</td>
		                      </tr>
		  	                </table>
         

		                    <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="Y">
		                              <table align="center" class="formContainer" border="0" cellspacing="0"cellpadding="0">
			                            
			                            <tr id="insuranceapproveconfigYNclmRem" style="display: ">
			                               <td class="formLabel" > Insurer Reference will be applicable if Claim Reimbersement Approved Amount&nbsp;
					                        <html:select property="clmOperatorRem" name="frmConfigInsuranceApprove" style="width:100px;" styleClass="selectBox selectBoxMedium">
							                     <html:option value="EQ">Equal To</html:option>
							                     <html:option value="GT">More Than</html:option>
							                     <html:option value="LT">Less Than</html:option>
					                            </html:select>&nbsp;&nbsp;					
					                      <html:text property="clmApproveAmountLimitRem" styleClass="textBox textBoxSmall" maxlength="13" /></td>
				                            <td class="formLabel">&nbsp;</td>
				                        </tr>
				                      
			                        </table>
			                 </logic:match>
			                 
			                 <logic:notMatch property="allowedClmRemYN" name="frmConfigInsuranceApprove" value="Y">
			                         <table align="center" class="formContainer" border="0" cellspacing="0"cellpadding="0">
			                            <tr id="insuranceapproveconfigYNclmRem" style="display: none">
				    	                    <td class="formLabel" >Insurer Reference will be applicable if Claim Reimbersement Approved Amount&nbsp;
				    	                             <html:select property="clmOperatorRem" name="frmConfigInsuranceApprove" style="width:100px;" styleClass="selectBox selectBoxMedium">
							                   <html:option value="EQ">Equal To</html:option>
							                   <html:option value="GT">More Than</html:option>
							                   <html:option value="LT">Less Than</html:option>
							                   </html:select> &nbsp;&nbsp;
							                   <html:text property="clmApproveAmountLimitRem"	styleClass="textBox textBoxSmall" maxlength="13" /></td>
                                             <td class="formLabel" >&nbsp;</td>
                                          </tr>
                                     </table>
                            </logic:notMatch>
                            <logic:match name="frmConfigInsuranceApprove" property="insClmRemYN" value="Y" >
							<table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
							<tr id="allowedClmRejRemYNid" style="display: ">
                                            <td class="formLabel"> Rejection Of Claim Reimbersement&nbsp;&nbsp;&nbsp;</td>
				                                            <td><html:select property="rejectionClmRemYN" styleId="rejectionClmRemYN" name="frmConfigInsuranceApprove" style="width:100px;" styleClass="selectBox selectBoxMedium" onclick="javascript:showNoticationDataClmRem()"  >
							                                       <html:option value="N">No</html:option>
							                                      <html:option value="Y">Yes</html:option>
							                                    
							                                     </html:select>
							                                   </td>
							                               <td class="formLabel" >&nbsp;</td>
			                               </tr>
			                     </table>
			                     </logic:match>
			                     <logic:notMatch name="frmConfigInsuranceApprove" property="insClmRemYN" value="Y" >
							<table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
							 <tr id="allowedClmRejRemYNid" style="display: none ">
                                            <td class="formLabel"> Rejection Of Claim Reimbersement&nbsp;&nbsp;&nbsp;</td>
				                                            <td><html:select property="rejectionClmRemYN" styleId="rejectionClmRemYN" name="frmConfigInsuranceApprove" style="width:100px;" styleClass="selectBox selectBoxMedium" onclick="javascript:showNoticationDataClmRem()"  >
							                                       <html:option value="N">No</html:option>
							                                      <html:option value="Y">Yes</html:option>
							                                    
							                                     </html:select>
							                                   </td>
							                               <td class="formLabel" >&nbsp;</td>
			                               </tr>
			                     </table>
			                     </logic:notMatch>
			                     
			                                <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="Y">
			                                <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="N">
			                                <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
			                                      <tr class="formLabel" id="notificationClmRemYN" style="display: ">
                                                         <td class="formLabel"> Notification Claim Reimbersement &nbsp;&nbsp;</td>
                                                         <td><html:radio property="notificationClmRemFlag" styleId="notificationClmRemFlag" name="frmConfigInsuranceApprove" value="MAIL">Online Mails</html:radio></td>
					                                     <td><html:radio property="notificationClmRemFlag" styleId="notificationClmRemFlag" name="frmConfigInsuranceApprove" value="ADOBE">Adobe Forms Attachment</html:radio></td>    					                                     
                                                   </tr>
                                                   </table>
                                                   </logic:match>
	                                               </logic:match>
	                                                <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="N">
			                                <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="Y">
			                                <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
			                                      <tr class="formLabel" id="notificationClmRemYN" style="display: ">
                                                         <td class="formLabel"> Notification Claim Reimbersement &nbsp;&nbsp;</td>
                                                         <td><html:radio property="notificationClmRemFlag" styleId="notificationClmRemFlag" name="frmConfigInsuranceApprove" value="MAIL">Online Mails</html:radio></td>
					                                     <td><html:radio property="notificationClmRemFlag" styleId="notificationClmRemFlag" name="frmConfigInsuranceApprove" value="ADOBE">Adobe Forms Attachment</html:radio></td>    					                                     
                                                   </tr>
                                                   </table>
                                                   </logic:match>
	                                               </logic:match>
	                                                <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="Y">
			                                <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="Y">
			                                <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
			                                      <tr class="formLabel" id="notificationClmRemYN" style="display: ">
                                                         <td class="formLabel"> Notification Claim Reimbersement &nbsp;&nbsp;</td>
                                                         <td><html:radio property="notificationClmRemFlag" styleId="notificationClmRemFlag" name="frmConfigInsuranceApprove" value="MAIL">Online Mails</html:radio></td>
					                                     <td><html:radio property="notificationClmRemFlag" styleId="notificationClmRemFlag" name="frmConfigInsuranceApprove" value="ADOBE">Adobe Forms Attachment</html:radio></td>    					                                     
                                                   </tr>
                                                   </table>
                                                   </logic:match>
	                                               </logic:match>
	                                                   <logic:notMatch property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="Y">
	                                                   <logic:notMatch property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="Y">
	                                                   <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
			                                             <tr class="formLabel" id="notificationClmRemYN" style="display: none">
                                                            <td class="formLabel"> Notification Claim Reimbersement &nbsp;&nbsp;</td>
                                                            <td><html:radio property="notificationClmRemFlag" name="frmConfigInsuranceApprove" styleId="notificationClmRemFlag" value="MAIL">Online Mails</html:radio></td>
					                                        <td><html:radio property="notificationClmRemFlag" name="frmConfigInsuranceApprove"  styleId="notificationClmRemFlag" value="ADOBE">Adobe Forms Attachment</html:radio></td>    					                  
                                                         </tr>
                                                         </table>
	                                                    </logic:notMatch>
	                                                    </logic:notMatch>
	                                                    <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
	                                                    <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="Y">
	                                                    <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="N">
	                                                    <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="N">
			                                			<logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="N">
		    
		    <tr id=timeInHrsandMinClm style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset>
		           </td>         
			   </tr>
			   </logic:match>
	           </logic:match>
	           </logic:match>
		   </logic:match>
		   <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="N">
	        <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="Y">
	        <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="N">
			<logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="N">		    
		    <tr id=timeInHrsandMinClm style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset>
		           </td>         
			   </tr>
			   </logic:match>
	           </logic:match>
	           </logic:match>
	            </logic:match>
	           <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="N">
	           <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="N">
	        <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="Y">
			<logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="N">
		    
		    <tr id=timeInHrsandMinClm style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset>
		           </td>         
			   </tr>
			   </logic:match>
	           </logic:match>
	           </logic:match>
		   </logic:match>
		   <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="N">
	     <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="N">
	   <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="N">
	   <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="Y">		    
		    <tr id=timeInHrsandMinClm style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset>
		           </td>         
			   </tr>
			   </logic:match>
	           </logic:match>
	           </logic:match>
		   </logic:match>
		   <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="Y">
	     <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="Y">
	   <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="N">
	   <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="N">		    
		    <tr id=timeInHrsandMinClm style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset>
		           </td>         
			   </tr>
			   </logic:match>
	           </logic:match>
	           </logic:match>
		   </logic:match>
		   <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="N">
	     <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="N">
	   <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="Y">
	   <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="Y">		    
		    <tr id=timeInHrsandMinClm style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset>
		           </td>         
			   </tr>
			   </logic:match>
	           </logic:match>
	           </logic:match>
		   </logic:match>
		   
		   <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="Y">
	     <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="N">
	   <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="Y">
	   <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="N">		    
		    <tr id=timeInHrsandMinClm style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset>
		           </td>         
			   </tr>
			   </logic:match>
	           </logic:match>
	           </logic:match>
		   </logic:match>
		   <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="N">
	     <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="Y">
	   <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="N">
	   <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="Y">		    
		    <tr id=timeInHrsandMinClm style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset>
		           </td>         
			   </tr>
			   </logic:match>
	           </logic:match>
	           </logic:match>
		   </logic:match>
		   <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="Y">
	     <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="Y">
	   <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="Y">
	   <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="Y">		    
		    <tr id=timeInHrsandMinClm style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset>
		           </td>         
			   </tr>
			   </logic:match>
	           </logic:match>
	           </logic:match>
		   </logic:match>
		   <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="Y">
	     <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="Y">
	   <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="Y">
	   <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="N">		    
		    <tr id=timeInHrsandMinClm style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset>
		           </td>         
			   </tr>
			   </logic:match>
	           </logic:match>
	           </logic:match>
		   </logic:match>
		   <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="N">
	     <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="Y">
	   <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="Y">
	   <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="Y">		    
		    <tr id=timeInHrsandMinClm style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset>
		           </td>         
			   </tr>
			   </logic:match>
	           </logic:match>
	           </logic:match>
		   </logic:match>
		   <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="Y">
	     <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="N">
	   <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="Y">
	   <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="Y">		    
		    <tr id=timeInHrsandMinClm style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset>
		           </td>         
			   </tr>
			   </logic:match>
	           </logic:match>
	           </logic:match>
		   </logic:match>
		   <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="Y">
	     <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="Y">
	   <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="N">
	   <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="Y">		    
		    <tr id=timeInHrsandMinClm style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset>
		           </td>         
			   </tr>
			   </logic:match>
	           </logic:match>
	           </logic:match>
		   </logic:match>
		   <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="Y">
	     <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="N">
	   <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="N">
	   <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="Y">		    
		    <tr id=timeInHrsandMinClm style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset>
		           </td>         
			   </tr>
			   </logic:match>
	           </logic:match>
	           </logic:match>
		   </logic:match>
		   <logic:match property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="N">
	     <logic:match property="allowedClmRemYN" name="frmConfigInsuranceApprove"value="Y">
	   <logic:match property="rejectionClmYN" name="frmConfigInsuranceApprove" value="Y">
	   <logic:match property="allowedClmYN" name="frmConfigInsuranceApprove" value="N">		    
		    <tr id=timeInHrsandMinClm style="display: ">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				     </fieldset>
		           </td>         
			   </tr>
			   </logic:match>
	           </logic:match>
	           </logic:match>
		   </logic:match>
		
			<logic:notMatch property="rejectionClmRemYN" name="frmConfigInsuranceApprove" value="Y">
			<logic:notMatch property="allowedClmRemYN" name="frmConfigInsuranceApprove" value="Y">
			<logic:notMatch property="rejectionClmYN" name="frmConfigInsuranceApprove" value="Y">
			<logic:notMatch property="allowedClmYN" name="frmConfigInsuranceApprove" value="Y">
		 
		    <tr id="timeInHrsandMinClm" style="display: none">
    	            <td class="formLabel"> 
    	               <fieldset>
		                     <legend>Mail Frequency Configuration Claims</legend>
					          <table  border="0" cellpadding="0" cellspacing="0">
					             <tr>
		                          <td class="formLabel" > No of Hours</td>
		                          <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInHrsClm" styleId="timeInHrsClm" maxlength="2"></html:text></td>
		                        </tr>
		                          <tr>
		                          <td class="formLabel" > No of Minutes</td>
		       				      <td class="formLabel" ><html:text  name="frmConfigInsuranceApprove" property="timeInMinsClm"  styleId="timeInMinsClm" maxlength="2"></html:text></td>
		                        </tr>
				             </table>
				</fieldset>
				 </td> 
			   </tr>
			 
		   </logic:notMatch>
		   </logic:notMatch>
		   </logic:notMatch>
		   </logic:notMatch>
		   </table>
                  </fieldset>
 
		   
	
		<%--<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		    <tr>
    	            <td class="formLabel">123<bean:write name="frmConfigInsuranceApprove" property="rejectionYN"/> </td>  
                  <td class="formLabel"> 234<bean:write name="frmConfigInsuranceApprove" property="notificationFlag"/>



			 </td>         
			   </tr>
			   </table>--%>
		        
	<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="center">
			<%
	       if(TTKCommon.isAuthorized(request,"Edit"))
	       {
    	%>
			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'"	onClick="javascript:onSave();"><u>S</u>ave</button>
			&nbsp;
			<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>
			&nbsp;
		 <%
	    	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		%>
			<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
			</td>
			
		</tr>
	</table>
	</div>
	<!-- E N D : Buttons -->
	<input type="hidden" name="mode">
	<!-- <input type="hidden" name="insPat">  added for bajaj enhan1
	<input type="hidden" name="insClm">  added for bajaj enhan1 -->
	<html:hidden property="insClm" /> <!--  added for donor expenses-->
	
    <html:hidden property="insPat" /> <!--  added for donor expenses-->
	<html:hidden property="caption" />
<input type="hidden" name="rejectionValue">
	
	 <script>
	 //stopClaim();//<!--  added for bajaj enhan1-->
	 //stopPreauth();//<!--  added for bajaj enhan1-->
    </script>
</html:form>
<!-- E N D : Content/Form Area -->