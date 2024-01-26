<%
/**
 * 1274 Change Request 		Bajaj
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/awaitinginsurerapprovepage.js"></SCRIPT>

<%

String strTab=TTKCommon.getActiveTab(request);
pageContext.setAttribute("strTab",strTab);
pageContext.setAttribute("insApprovalStatus",Cache.getCacheObject("insApprovalStatus"));
%>
<%
	boolean viewmode=true;
	boolean bEnabled=false;
	boolean viewmode1=true;
	//String ampm[] = {"AM","PM"};
	//boolean blnAmmendmentFlow=false;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
		viewmode1=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))

	
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	String showYN=(String) request.getSession().getAttribute("showYN");
	String strQuery="";
	
	
%>
<html:form action="/UpdateClaimAwaitingApproveSearch.do" >

<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="57%">
    <logic:match name="strTab" value="Pre-Auth">
   Cashless  Summary  Details 
	</logic:match>
	 <logic:match name="strTab" value="Claims">
   Claim Summary  Details 
	</logic:match>
      <bean:write name="frmSaveClaimAwaitingApprove" property="caption"/>
      </td>
   
      
    
   
  </tr>
</table>

	  
	   
	   
	 
<!-- E N D : Page Title -->	
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
	

<div class=contentArea1 id="contentArea">

	<ttk:InsuranceApprovePage/>
	
<fieldset>
	<%--			  <tr>
					     <td colspan="1" height="25" class="formLabel">Insurer Remarks</td>
					     <td colspan="5" class="textLabel">
					     <input type="text" name="insurerRemarks" class="textBox textAreaLongHt"></input></td>
				   </tr> 
					
				  <tr>
					     <td colspan="1" height="25" class="formLabel">Approval</td>
					     <td colspan="3" class="textLabel">
					       <select name="claimApprovlaStatus" class="selectBox selectBoxMedium" >
					              <option value="APR">Approved</option>
					              <option value="REJ">Rejected</option>
					              <option value="REQ">Required Information</option>
					        </select>
					     </td>
				 </tr>
			
			</tr>--%>
			
<%

if(!showYN.equalsIgnoreCase("N"))
		{
	 strQuery=(String)pageContext.getSession().getAttribute("strQuery");
	
			
%>

<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			
			
			<tr>	
			  <td colspan="1" height="25" class="formLabel">Insurer's Decision:<span class="mandatorySymbol">*</span></td>
			  <td colspan="3" class="textLabel">
			  <logic:equal name="frmSaveClaimAwaitingApprove" property="inpStatus" value="INP">
			  <html:select name="frmSaveClaimAwaitingApprove" property="insApprovalStatus"  styleClass="selectBox selectBoxLarge" >
	   		               <html:option value="">Select from list</html:option>
	   		               <html:options collection="insApprovalStatus"  property="cacheId" labelProperty="cacheDesc"/>
		                    </html:select> 
			  
			  	 <%--  <html:select  name="frmSaveClaimAwaitingApprove" property="insApprovalStatus" styleClass="selectBox selectBoxMedium">
				                   <html:option value="">Select from list</html:option>
				                  <html:option value="APR">Approved</html:option>
					              <html:option value="REJ">Rejected</html:option>
					              <html:option value="REQ">Required Information</html:option>
			 
				 </html:select> --%>
				 </logic:equal>
				 <logic:notEqual  name="frmSaveClaimAwaitingApprove" property="inpStatus" value="INP">
				  <html:select name="frmSaveClaimAwaitingApprove" property="insApprovalStatus"  styleClass="selectBox selectBoxLarge" >
	   		                <html:option value="">Select from list</html:option>
	   		               <html:options collection="insApprovalStatus"  property="cacheId" labelProperty="cacheDesc"/>
		                    </html:select> 
				  <%-- <html:select  name="frmSaveClaimAwaitingApprove" property="insApprovalStatus" styleClass="selectBox selectBoxMedium">
				                  <html:option value="APR">Approved</html:option>
					              <html:option value="REJ">Rejected</html:option>
					              <html:option value="REQ">Required Information</html:option>
				  </html:select> --%>
				 </logic:notEqual>
				 </td>
		 </tr>
			
		  <tr>
			  <td colspan="1" height="25" class="formLabel">Insurer's Remarks<span class="mandatorySymbol">*</span></td>
			    <td width="79%" colspan="3">
                 <html:textarea name="frmSaveClaimAwaitingApprove" property="insurerRemarks" styleClass="textBox textAreaLong" />
           </td>
					 <%-- <td>     <bean:write name="frmSaveClaimAwaitingApprove" property="insurerRemarks"/></td> 
					    
					     <logic:empty name="frmSaveClaimAwaitingApprove" property="insurerRemarks">
					         <td colspan="5" class="textLabel">
					         <bean:write name="frmSaveClaimAwaitingApprove" property="insurerRemarks"/></td>
				          </logic:empty>
				           <logic:notEmpty name="frmSaveClaimAwaitingApprove" property="insurerRemarks">
					       <td colspan="5" class="textLabel">
					       <input type="text" name="frmSaveClaimAwaitingApprove" property="insurerRemarks" class="textBox textAreaLongHt"></input></td>
				          </logic:notEmpty>	 --%>	
				   </tr> 
			   
		
</table>

<%
		}//if(showYN.equalsIgnoreCase("N"))
	%>

</fieldset>	
    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	   <td width="100%" align="center">
	   <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
	  <%  if(TTKCommon.isAuthorized(request,"Edit"))
	        {
			         if(TTKCommon.isAuthorized(request,"Approve") || TTKCommon.isAuthorized(request,"Reject") || TTKCommon.isAuthorized(request,"RequiredInformation") )
			        	 
			         {
			        	 if(!showYN.equalsIgnoreCase("N"))
			     		{
	%>		
	   
      	    		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ubmit</button>&nbsp;
	    	    	<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDocumentViewer('<%=strQuery%>');"><u>C</u>laim Documents</button>&nbsp;
	   			   	<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();"><u>D</u>ownload Report</button>
	 	<%
			     		}// if(!showYN.equalsIgnoreCase("N"))
			     		}//end of if(TTKCommon.isAuthorized(request,"Approve") || TTKCommon.isAuthorized(request,"Reject") || TTKCommon.isAuthorized(request,"RequiredInformation") )

	         }//end of if(TTKCommon.isAuthorized(request,"Edit"))
	%>   
	  
	    </td>
	  </tr>
	</table>
	
	
	<!-- E N D : Buttons -->
</div>
<html:hidden property="allowYN" name="frmSaveClaimAwaitingApprove"/>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="">
<INPUT TYPE="hidden" NAME="tab" VALUE="">
<input type="hidden" name="showYN" value="">
<html:hidden property="seqID" name="frmSaveClaimAwaitingApprove"/>
</html:form>
