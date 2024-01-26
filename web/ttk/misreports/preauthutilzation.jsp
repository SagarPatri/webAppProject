<%
/**
 * @ (#) preauthutilzation.jsp 18th May 2015
 * Project      : TTK HealthCare Services
 * File         : preauthutilzation.jsp
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 18th May 2015
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.misreports.ReportCache" %>
<script language="javascript" src="/ttk/scripts/misreports/preauthmis.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
bAction = false;
var TC_Disabled = true;
</SCRIPT>
<%
pageContext.setAttribute("providerType",Cache.getCacheObject("providerType"));
pageContext.setAttribute("benefitTypess",Cache.getCacheObject("benefitTypes"));
pageContext.setAttribute("alCorporateList", Cache.getCacheObject("corporateList"));
pageContext.setAttribute("preauthStatus", Cache.getCacheObject("preauthStatus"));
pageContext.setAttribute("alInsCompanyList", Cache.getCacheObject("InsComp"));
pageContext.setAttribute("ProviderList",Cache.getCacheObject("ProviderList"));
%>


<!-- S T A R T : Content/Form Area -->
<html:form action="/PreAuthReportsListAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		  <logic:equal value="PRP" name="reportTypes" scope="request">	  
		    <td>Pre-Auth Report</td>
		    </logic:equal>
		     <logic:equal value="ERP" name="reportTypes" scope="request">	  
		    <td>Enrollment Report</td>
		    </logic:equal>
		     <logic:equal value="CLR" name="reportTypes" scope="request">	  
		    <td>Claim Report</td>
		    </logic:equal>
		    <%-- <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td> --%>
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
					<bean:write name="updated" scope="request"/>
				</td>
			</tr>
		</table>
	</logic:notEmpty>
	
	<!-- Search box -->
	<table class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>	      				
      		<logic:equal value="CLR" name="reportTypes" scope="request">
	      				 <td align="left" nowrap>Insurance Company:<br>
				               <html:select property="insCompanyCode" styleId="insCompanyCode" styleClass="selectBox selectBoxLarge">
				    		  	 	  <html:option value="">ALL</html:option>
					  				  <html:optionsCollection name="alInsCompanyList" label="cacheDesc" value="cacheId"/>
				                </html:select>
				  		  </td>
      		</logic:equal>
				
			<logic:equal value="CLR" name="reportTypes" scope="request">	  
		   					<td align="left" nowrap>Product Authority:<br>
			   					<html:select property ="payerCodes" styleId="payerCodes" styleClass="selectBox selectBoxMedium">
									<html:option value="CMA">CMA</html:option>
						<%--			<html:option value="">Select from list</html:option> 
									<html:option value="DHA">DHA</html:option>
					     			<html:option value="HAAD">HAAD</html:option>
					     			<html:option value="MOH">MOH</html:option>--%>
								</html:select>
							</td>
		    </logic:equal>
		    
		    <logic:notEqual value="CLR" name="reportTypes" scope="request">	  
		   				 <td align="left" nowrap>Authority<span class="mandatorySymbol">*</span>:<br>
			   				 <html:select property ="payerCodes" styleId="payerCodes"  styleClass="selectBox selectBoxMedium">
							        <html:option value="CMA">CMA</html:option>
							<%--	<html:option value="">Select from list</html:option> 
					       		<html:option value="">ALL</html:option>
				     			<html:option value="CMA">CMA</html:option>
				     			<html:option value="HAAD">HAAD</html:option>
				     			<html:option value="MOH">MOH</html:option>--%>
							</html:select>
						</td>
		    </logic:notEqual>
		
			<logic:equal value="PRP" name="reportTypes" scope="request">
						<td align="left" nowrap>Provider Name:<br>
							<html:select property="providerCodes"  styleId="providerCodes" styleClass="selectBox selectBoxLorge">
						    	 	<html:option value="">Any</html:option>
				  					<html:options collection="ProviderList"  property="cacheId" labelProperty="cacheDesc"/>
						    </html:select>
		<%-- 				<html:text property="" styleId="providerCodes" styleClass="textBox textBoxMedium" readonly="true"/>
							<a href="#" onClick="openListIntX('providerCodes','PROVIDERSCODEGEN')" style="display: inline;"><img src="/ttk/images/EditIcon.gif" alt="Select Providers" width="16" height="16" border="0" align="absmiddle"></a>
		 --%>			</td>
			</logic:equal>
		
			<logic:equal value="ERP" name="reportTypes" scope="request">
		<td align="left" nowrap>Enrollment Type<span class="mandatorySymbol">*</span>:<br>
        		<html:select property="eType" styleClass="selectBox selectBoxMedium">
                <html:option value="">Select from list</html:option>
        		<html:option value="">ALL</html:option>
        	    <html:option value='COR'>Corporate</html:option>
			   <html:option value='IND'>Individuals</html:option>
			   <html:option value='ING'>Individuals as Corporate</html:option>
			   <html:option value='NCR'>Non Corporate</html:option>
			   </html:select>
		</td>
        	</logic:equal>
		
		<td align="left" nowrap>Corporate:<br>
			<html:select property="corporateCodes" styleId="corporateCodes" styleClass="selectBox selectBoxLarge">
				    		  	 	  <html:option value="">ALL</html:option>
					  				  <html:optionsCollection name="alCorporateList" label="cacheDesc" value="cacheId"/>
				                </html:select>
			</td>
			<logic:equal value="CLR" name="reportTypes" scope="request">
				     <td align="left" nowrap>Policy No:<br>
			        		<html:text property="sGroupPolicyNo" styleId="sGroupPolicyNo" styleClass="textBox textBoxMedium" maxlength="250" />
			         </td>
       		</logic:equal>
		
			<logic:equal value="CLR" name="reportTypes" scope="request">	  
		   		<td align="left" nowrap>Benefit Type:<br>
	      			<html:select property="benefitTypes" styleId="benefitTypes" styleClass="selectBox selectBoxMedium">
								<html:option value="">ALL</html:option>
								<html:optionsCollection name="benefitTypess" label="cacheDesc" value="cacheId" />
					</html:select>
				</td>	
			</logic:equal>
		</tr>
	
			<tr>
			  	<logic:equal value="CLR" name="reportTypes" scope="request">	  
		   		
				 <td align="left" nowrap>Claim Source:<br>
	        		<html:select property="claimSource" styleId="claimSource" styleClass="selectBox selectBoxMedium">
	        			<html:option value="">ALL</html:option> 
	        			<html:option value='ECLM'>E-Claims</html:option>
				   		<html:option value='PCLM'>Paper Claims</html:option>
	        		</html:select>
      			 </td>
      			 
      			 <td align="left" nowrap>Provider Name:<br>
					<html:select property="providerCodes"  styleId="providerCodes" styleClass="selectBox selectBoxMedium">
				    	<html:option value="">Select from list</html:option>
		  				<html:options collection="ProviderList"  property="cacheId" labelProperty="cacheDesc"/>
				    </html:select>
		<%-- 			<html:text property="" styleId="providerCodes" styleClass="textBox textBoxMedium" readonly="true"/>
					<a href="#" onClick="openListIntX('providerCodes','PROVIDERSCODEGEN')" style="display: inline;"><img src="/ttk/images/EditIcon.gif" alt="Select Providers" width="16" height="16" border="0" align="absmiddle"></a>
 --%>		   </td>
 
 				<td align="left" nowrap>Provider Category:<br>
    						<html:select property ="providerCategory" styleId="providerCategory" styleClass="selectBox selectBoxMedium">
                 			<html:option value="">ALL</html:option> 
                 			<%-- <html:option value="">Select from list</html:option> --%>
                 			<html:optionsCollection name="providerType" label="cacheDesc" value="cacheId" />
    	   				</html:select>
	      			</td>
		
      			 <td nowrap>Claim Batch No:<br>
            			<html:text property="batchNo" styleId="batchNo"  styleClass="textBox textBoxMedium" maxlength="60"/>
     			 </td>
     				
     				<td align="left" nowrap>Type of Claim:<br>
			        		<html:select property="sType" styleId="sType" styleClass="selectBox selectBoxMedium">
			        		<html:option value="">All</html:option>
			        	    <html:option value='CTM'>Member</html:option>
						   <html:option value='CNH'> Network</html:option>
			        	</html:select>
			        </td>	 
      		</logic:equal>
		        
		        <logic:equal value="ERP" name="reportTypes" scope="request">	 
		            <td align="left" nowrap>Broker Name:<br>
		            <html:text property="sAgentCode" styleId="sAgentCode" styleClass="textBox textBoxMedium" maxlength="250" />
		            </td>
		        </logic:equal>
		             
		           
		        <logic:notEqual value="CLR" name="reportTypes" scope="request">
			             <td align="left" nowrap>Insurance Company<span class="mandatorySymbol">*</span>:<br>
				               <html:select property="insCompanyCode" styleId="insCompanyCode" styleClass="selectBox selectBoxLarge">
				    		  	 	  <html:option value="">Select from list</html:option>
					  				  <html:optionsCollection name="alInsCompanyList" label="cacheDesc" value="cacheId"/>
				                </html:select>
				  		  </td>
			  	</logic:notEqual> 
			  		  
			   	<logic:notEqual value="CLR" name="reportTypes" scope="request">
				     <td align="left" nowrap>Policy No:<br>
			        		<html:text property="sGroupPolicyNo" styleClass="textBox textBoxMedium" maxlength="250" />
			         </td>
       			</logic:notEqual>
       </tr>
             	
    <%--     <logic:equal value="ERP" name="reportTypes" scope="request">
        <td align="left" nowrap>Type:<span class="mandatorySymbol">*</span>
        		<html:select property="cType" styleClass="selectBox selectBoxMedium">
        		<html:option value="">Select from list</html:option> 
        		<!-- <html:option value="ALL">All</html:option> -->
        	    <html:option value='MEM'>Member Level</html:option>
			   <html:option value='POL'> Policy Level</html:option>
        	</html:select>
        	</td>
 		</logic:equal>--%>
	
	    <tr>
	    
	    	 <logic:equal value="ERP" name="reportTypes" scope="request">
			    	<td align="left" nowrap>From Date<span class="mandatorySymbol">*</span>:<br>
								<html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>
								<a name="CalendarObjectStartDate" id="CalendarObjectStartDate" href="#" onClick="javascript:show_calendar('CalendarObjectStartDate','frmMISReports.elements[\'sStartDate\']',document.frmMISReports.elements['sStartDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
									<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
								</a>
					</td>
				<!-- 	<td align="left" nowrap>To Date<span class="mandatorySymbol">*</span>:<br> -->
					<td align="left" nowrap>To Date<span class="mandatorySymbol">*</span>:<br>
								<html:text property="sEndDate"  styleId="sEndDate" styleClass="textBox textDate" maxlength="10"/>
								<a name="CalendarObjectEndDate" id="CalendarObjectEndDate" href="#" onClick="javascript:show_calendar('CalendarObjectEndDate','frmMISReports.elements[\'sEndDate\']',document.frmMISReports.elements['sEndDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
									<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
					</a>
					</td>
					
					  <td align="left" nowrap>Type of Enrolment:<br>
		              <html:select property="sType" styleId="sType" styleClass="selectBox selectBoxMedium">
		   		  	 	<html:option value="">Select from list</html:option>
		   		  	 	<html:option value="ENR">New</html:option>
		   		  	 	<html:option value="END">Endorsement</html:option>
		               </html:select>
			   		 </td>
			 </logic:equal>
	    		  
	    
	        	<logic:equal value='CLR' name="reportTypes" scope="request">
					    <td align="left" nowrap>Claim Status:<br>
					   		 <html:select property ="sStatus"  styleId="sStatus" styleClass="selectBox selectBoxMedium">
								   <html:option value=''>ALL</html:option>
							 	   <html:option value='APR'>Approved</html:option>
								   <html:option value='PCN'>Cancelled</html:option>
								   <html:option value='INP'>Inprogress</html:option>
								   <html:option value='REJ'> Rejected</html:option>
								   <html:option value='PCO'> Closed </html:option>
								   <html:option value='REQ'> Required Information </html:option>
							 	 </html:select>
						</td>
				 </logic:equal>
				 
				 <logic:equal value='PRP' name="reportTypes" scope="request">
					    <td align="left" nowrap>Status<span class="mandatorySymbol">*</span>:<br>
							<html:select property ="sStatus"  styleId="sStatus" styleClass="selectBox selectBoxMedium">
									<html:option value="">Select from list</html:option> 
					        	   <html:option value='APR'>Approved</html:option>
								   <html:option value='PCN'>Cancelled</html:option>
								   <html:option value='INP'>Inprogress</html:option>
								   <html:option value='REJ'> Rejected</html:option>
								   <html:option value='PCO'> Closed </html:option>
								   <html:option value='SRT'> Shortfall</html:option>
								   <html:option value='BNR'> Bill Not Received</html:option>
			             	</html:select>
						</td>
				</logic:equal>
			 
       
      			<logic:equal value="CLR" name="reportTypes" scope="request">
		        
		        <td nowrap>Claim No:<br>
				            <html:text property="claimNo" styleId="claimNo" styleClass="textBox textBoxMedium" maxlength="60"/>
				</td>
		
				<td nowrap>Remittance Advice<br>Uploaded File Name:<br> 
            		<html:text property="remAdvFname" styleId="remAdvFname" styleClass="textBox textBoxMedium" maxlength="60"/>
     			</td>
     					 
     			<td nowrap>Payment Transaction<br>Reference Number:<br>
            		<html:text property="PayTransRefNo" styleId="PayTransRefNo" styleClass="textBox textBoxMedium" maxlength="60"/>
     			</td>
     					
     			<td align="left" nowrap>Payment Status:<br>
					<html:select property ="payStatus" styleId="payStatus" styleClass="selectBox selectBoxMedium">
				      	<html:option value="">Select from list</html:option>	
				   	    <html:option value='PAID'>Payment Done</html:option>
					  	<html:option value='PENDING'>Payment Pending</html:option>
					</html:select>
				</td>
        </logic:equal>
        
        
         <logic:equal value="PRP" name="reportTypes" scope="request">
			    	<td align="left" nowrap>From Date<span class="mandatorySymbol">*</span>:<br>
								<html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>
								<a name="CalendarObjectStartDate" id="CalendarObjectStartDate" href="#" onClick="javascript:show_calendar('CalendarObjectStartDate','frmMISReports.elements[\'sStartDate\']',document.frmMISReports.elements['sStartDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
									<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
								</a>
					</td>
				<!-- 	<td align="left" nowrap>To Date<span class="mandatorySymbol">*</span>:<br> -->
					<td align="left" nowrap>To Date<span class="mandatorySymbol">*</span>:<br>
								<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>
								<a name="CalendarObjectEndDate" id="CalendarObjectEndDate" href="#" onClick="javascript:show_calendar('CalendarObjectEndDate','frmMISReports.elements[\'sEndDate\']',document.frmMISReports.elements['sEndDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
									<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
					</a>
					</td>
		</logic:equal>			
        	
			</tr>
		
		  <tr>
		  		
		  		
		  		<logic:equal value="CLR" name="reportTypes" scope="request">
			        	<%-- <td align="left" nowrap>Claim Received Date<span class="mandatorySymbol">*</span>:<br>
			        	<html:text property="csStartDate" styleClass="textBox textDate" maxlength="10"/>
						<a name="CalendarObjectStartDate" id="CalendarObjectStartDate" href="#" onClick="javascript:show_calendar('CalendarObjectStartDate','frmMISReports.elements[\'csStartDate\']',document.frmMISReports.elements['csStartDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
							<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
						</a>
						</td> --%>
						
						
						<logic:equal value="CLR" name="reportTypes" scope="request">

						<td align="left" nowrap>Select Date Criteria<span class="mandatorySymbol">*</span>:<br>
							<html:select property ="dtCriteria" styleId="dtCriteria" styleClass="selectBox selectBoxMedium">
						       <html:option value="">Select from list</html:option>	
				        	   <html:option value='CRDT'>claims Received Date</html:option>
							   <html:option value='DTINT'>Date of Intimation</html:option>
							   <html:option value='DTADM'>Date of Admission</html:option>
							   <html:option value='DTDIS'>Date of Discharge</html:option>
							   <html:option value='RAUDT'>Remittance Advice Uploaded Date</html:option>
							   <html:option value='CLCDT'>Claims Completed Date </html:option>
				     		   <html:option value='PMDT'>Payment Date</html:option>
					  		</html:select>
						</td>
     			  </logic:equal>
						
						
					<td align="left" nowrap>From Date<span class="mandatorySymbol">*</span>:<br>
						<html:text property="sStartDate" styleId="sStartDate" styleClass="textBox textDate" maxlength="10"/>
						<a name="CalendarObjectStartDate" id="CalendarObjectStartDate" href="#" onClick="javascript:show_calendar('CalendarObjectStartDate','frmMISReports.elements[\'sStartDate\']',document.frmMISReports.elements['sStartDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
							<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
						</a>
					</td>
					<td align="left" nowrap>To Date:<br>
						<html:text property="sEndDate" styleId="sEndDate" styleClass="textBox textDate" maxlength="10"/>
						<a name="CalendarObjectEndDate" id="CalendarObjectEndDate" href="#" onClick="javascript:show_calendar('CalendarObjectEndDate','frmMISReports.elements[\'sEndDate\']',document.frmMISReports.elements['sEndDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
							<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
						</a>
					</td>
				</logic:equal>
		  </tr>
		 </table>
<%-- 		<logic:notEqual value="CLR" name="reportTypes" scope="request">
		<td class="formLabel">Inward Register:
	    <html:select property="tInwardRegister" styleId="inwardReg" styleClass="selectBox selectBoxMedium">
		  	 	  <!-- <html:option value="">Select from list</html:option> -->
		  	 	  <html:option value="ALL">All</html:option>
		  	 	  <html:options collection="inwardRegister" property="cacheId" labelProperty="cacheDesc"/>
            </html:select>
	    </td>
	    </logic:notEqual>
 --%> 
 	
		<%-- <logic:equal value="CLR" name="reportTypes" scope="request">
		<tr>
		<td align="left" nowrap>Claim Inward Date(From): <br>
			<html:text property="csStartDate" styleClass="textBox textDate" maxlength="10"/>
			<a name="CalendarObjectStartDate" id="CalendarObjectStartDate" href="#" onClick="javascript:show_calendar('CalendarObjectStartDate','frmMISReports.elements[\'csStartDate\']',document.frmMISReports.elements['csStartDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
				<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
			</a>
		</td>
		<td align="left" nowrap>Claim Inward Date(To): <br>
			<html:text property="csEndDate" styleClass="textBox textDate" maxlength="10"/>
			<a name="CalendarObjectEndDate" id="CalendarObjectEndDate" href="#" onClick="javascript:show_calendar('CalendarObjectEndDate','frmMISReports.elements[\'csEndDate\']',document.frmMISReports.elements['csEndDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
				<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
			</a>
		</td>
		</tr>
		</logic:equal> --%>
		 
     
      
      
 <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	<tr>
			<td width="100%" align="center">
			<%-- <logic:equal value="PRE" name="reportTypes" scope="request">
				<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGeneratReport()"><u>G</u>enerate Report</button>
			</logic:equal>
			 --%>
			<logic:equal value="PRP" name="reportTypes" scope="request">
				<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onPRPGeneratReport()"><u>G</u>enerate Report</button>
			</logic:equal>
			<%-- <logic:equal value="CLM" name="reportTypes" scope="request">
				<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCLMGeneratReport()"><u>G</u>enerate Report</button>
			</logic:equal> --%>
			
			<logic:equal value="ERP" name="reportTypes" scope="request">
				<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onERPGeneratReport()"><u>G</u>enerate Report</button>
			</logic:equal>
			<logic:equal value="CLR" name="reportTypes" scope="request">
				<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCLRGeneratReport()"><u>G</u>enerate Report</button>
			</logic:equal>
			<%-- <logic:equal value="PRM" name="reportTypes" scope="request">
				<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onPRMGeneratReport()"><u>G</u>enerate Report</button>
			</logic:equal>
			
			<logic:equal value="CSM" name="reportTypes" scope="request">
				<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCSMGeneratReport()"><u>G</u>enerate Report</button>
			</logic:equal> --%>
			
			</td>
			</tr>  		
</table>
    
    <!-- E N D : SELCTIONS -->
	<!-- E N D : Buttons -->
	<!-- E N D : Form Fields -->
   </div>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
 </html:form>