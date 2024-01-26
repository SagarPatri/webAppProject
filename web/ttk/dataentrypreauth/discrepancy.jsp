<%
/**
 * @ (#) discrepancy.jsp 24th June 2006
 * Project      : TTK HealthCare Services
 * File         : discrepancy.jsp
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : 24th June 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/dataentrypreauth/discrepancy.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/DataEntryDiscrepancyAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
   		<td width="75%"><bean:write name="frmDiscrepancy" property="caption"/></td>
		<!--<td align="right"><a href="#"><img src="/ttk/images/DocViewIcon.gif" alt="Launch Document Viewer" width="16" height="16" border="0" align="absmiddle" class="icons"></a>&nbsp;&nbsp;&nbsp;</td>-->
   	</tr>
</table>
<!-- E N D : Page Title -->

<!-- S T A R T : Form Fields -->
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

<fieldset><legend>Discrepancies</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
        	<td height="6" nowrap class="formLabel"></td>
      	</tr>
      	<tr>
        	<td nowrap class="formLabel">
        	The following discrepancies were found. Please resolve any unresolved Discrepencies.</td>
      	</tr>

      	<%
      		String strResolvableYN="N";
      		String strResolvedYN="N";
      	%>
      	<logic:iterate id="discrepancy" name="alDiscrepancy">
	    	<tr>
    	    	<td nowrap class="labelRed">
    	    		<logic:match name="discrepancy" property="resolvedYN" value="N">
		    	    	<input type="checkbox" name="resolvedYN" value="Y"
			    	    	<logic:present name="discrepancy" property='resolvedYN'>
			    	    		<logic:match name='discrepancy' property='resolvedYN' value='Y'>
			    	    			checked
			    	    		</logic:match>
			    	    	</logic:present>
		    	    	>
	    	    	</logic:match>
	    	    	<logic:notMatch name="discrepancy" property="resolvedYN" value="N">
	    	    	   	<input type="checkbox" name="resolvedYN" value="Y"
		    	    		<logic:present name="discrepancy" property='resolvedYN'>
		    	    			<logic:match name='discrepancy' property='resolvedYN' value='Y'>
		    	    				checked
		    	    			</logic:match>
		    	    		</logic:present>  disabled="true"
	    	    		>
	    	    	</logic:notMatch>
	    	    	<bean:write name="discrepancy" property="discrepancy"/>
    	            <input type="hidden" name="discrepancySeqID" value="<bean:write name="discrepancy" property="discrepancySeqID"/>">
    	            <input type="hidden" name="selectedAnswer1List" value="">

    	            <logic:match name="discrepancy" property="resolvableYN" value="N">
    	            	<%
	    	            	strResolvableYN="Y";
    	            	%>
    	            </logic:match>

					<logic:match name='discrepancy' property='resolvedYN' value='Y'>
						<%
							strResolvedYN="Y";
						%>
					</logic:match>
    			</td>
      		</tr>
      	</logic:iterate>

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
						if(!strResolvableYN.equals("Y")&&!strResolvedYN.equals("Y"))
						{
				%>

						<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onResolve()">R<u>e</u>solve</button>&nbsp;
						<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Reset()"><u>R</u>eset</button>&nbsp;

	    		<%
	    				}
	    			}// end of if(TTKCommon.isAuthorized(request,"Edit"))
	    		%>
	    		<% 
	    			if(TTKCommon.getActiveTab(request).equals("Authorization"))
	    			{	    			
	    		%>
	    			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="modifyLinks('Tab','Authorization')"><u>C</u>lose</button>
	    		<%
	    			}//end of if(TTKCommon.getActiveTab(request).equals("Authorization"))
	    			else if(TTKCommon.getActiveTab(request).equals("Settlement"))
	    			{	    					    		
	    		%>
		    		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="modifyLinks('Tab','Settlement')"><u>C</u>lose</button>
		    	<%
	    			}//end of else if(TTKCommon.getActiveTab(request).equals("Settlement"))
	    			else 
	    			{
		    	%>
		    		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Close()"><u>C</u>lose</button>
		    	<%
	    			}//end of else 
		    	%>
		   </td>
		</tr>
	</table>
<!-- E N D : Buttons -->
</div>
<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
<input type="hidden" name="child" value="Discrepancy">

<html:hidden property="caption"/>
</html:form>
<!-- E N D : Content/Form Area -->