<%
/** @ (#) associatecorporate.jsp October 22nd, 2009
 * Project    	 : TTK Healthcare Services
 * File       	 : associatecorporate.jsp
 * Author     	 : Navin Kumar R
 * Company    	 : Span Systems Corporation
 * Date Created	 : October 22nd, 2009
 *
 * @author 		 : Navin Kumar R
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import=" com.ttk.common.TTKCommon" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="JavaScript" src="/ttk/scripts/finance/associatecorporate.js"></script>
<html:form action="/FloatAccAssocAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>
    			List of Associated Groups 
    			<bean:write property="caption" name="frmFloatAccDetails"/> 
    		</td>    		
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
		<html:errors/>
		<!-- S T A R T : Success Box -->
	 	<logic:notEmpty name="updated" scope="request">
	  		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   			<tr>
	     			<td>
	     				<img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         			<bean:message name="updated" scope="request"/>
	     			</td>
	  			</tr>
	 		</table>
    	</logic:notEmpty>
    	<!-- S T A R T : Table -->
		<div class="scrollableGrid" style="height:350px;">
			<ttk:HtmlGrid name="CorporateTable"/>
		</div>	
		<br>	
		<fieldset>
    		<legend>Policy Information</legend>
    		<table id="newassociate" class="formContainer"  border="0" cellspacing="0" cellpadding="0" style="margin:0px 0px 0px 10px;">
      			<tr>
      			<td width="20%" class="formLabel">Policy No: </td>
        			<td width="20%" class="textLabelBold">
        				<bean:write property="policyNo"  name="frmFloatAccDetails"/></td>
        				
        			<td width="20%" class="formLabel">Corporate Name: </td>
        			<td width="20%" class="textLabelBold">
        				<bean:write property="groupName"  name="frmFloatAccDetails"/></td>
        			<td width="20%" class="formLabel">Group Id:</td>
        			<td width="20%" class="textLabelBold">
        				<bean:write property="groupID"  name="frmFloatAccDetails"/>&nbsp;&nbsp;&nbsp;
        				<%
		    			if(TTKCommon.isAuthorized(request,"Associate Corporate"))
		    			{
		   				%> 	       			
		      			<a href="#" onClick="javascript:changeCorporate();"><img src="/ttk/images/EditIcon.gif" alt="Select Corporate" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;
			        	<a href="#" onClick="javascript:clearCorporate();"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Corporate Info." width="16" height="16" border="0" align="absmiddle"></a>
			        	<%
			    		}
		        		%>			    
			        </td>		                	        
      			</tr>
    		</table>
    		<%-- <table id="clearassociate" class="formContainer"  border="0" cellspacing="0" cellpadding="0" style="margin:0px 0px 0px 10px;display:none;">
      			<tr>
        			<td width="20%" class="formLabel">Corporate Name: </td>
        			<td width="30%" class="textLabelBold">&nbsp;</td>
        			<td width="20%" class="formLabel">Group Id:</td>
        			<td width="30%" class="textLabelBold">&nbsp;&nbsp;&nbsp;
        				<% 
		    			if(TTKCommon.isAuthorized(request,"Associate Corporate"))
		    			{
		   				%> 	       			
		      			<a href="#" onClick="javascript:changeCorporate();"><img src="/ttk/images/EditIcon.gif" alt="Select Corporate" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;
			        	<a href="#" onClick="javascript:clearCorporate();"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Corporate Info." width="16" height="16" border="0" align="absmiddle"></a>
			        	<%
			    		}
		        		%>			    
			        </td>		                	        
      			</tr>
    		</table> --%>
		</fieldset>		
		<!-- S T A R T : Buttons -->
		<table class="buttonsContainerGrid" align="center" border="0" cellspacing="0" cellpadding="0">
	    	<tr>
	        	<td align="center">
	        		<%
		    		if(TTKCommon.isAuthorized(request,"Associate Corporate"))
		    		{
		   			%>
		   			<button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAssociateCorp()"><u>A</u>ssociate</button>&nbsp;	        		
	        		<%
		    		}
	        		%>
	        		<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>	        		
	        	</td>
	      	</tr>
	    </table>
	    <!-- E N D : Buttons -->
	</div>
	<logic:notEmpty name="frmFloatAccDetails" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
	<input type="hidden" name="mode" value="">
	<input type="hidden" name="child" value="">
	<html:hidden property="groupName"/>
	<html:hidden property="groupID"/>
	<html:hidden property="floatAcctSeqID"/>
	<html:hidden property="groupRegnSeqID"/>
	<html:hidden property="rownum"/>	
</html:form>