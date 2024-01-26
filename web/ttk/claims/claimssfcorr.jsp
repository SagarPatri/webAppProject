<%
/** @ (#) claimssfcorr.jsp
 * Project     : TTK Healthcare Services
 * File        : claimssfcorr.jsp
 * Author      : Manohar 
 * Company     : RCS
 * Date Created: Jan 02,2013
 *
 * @author 		 : Manohar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,java.util.ArrayList"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.dto.administration.WorkflowVO"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/claims/claimssfcorr.js"></SCRIPT>
<script>
//hidebutton();
bAction=false;
var TC_Disabled = true;
</script>
<%
	pageContext.setAttribute("sShortfallStatus",Cache.getCacheObject("claimShortfallStatus"));
	pageContext.setAttribute("sTtkBranch", Cache.getCacheObject("officeInfo"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ClaimsSFCorrAction.do">

<logic:notEmpty name="fileName" scope="request">
		<script language="JavaScript">
		alert("Please Copy this Batch Number "+"<bean:write name="batchNumber"/>");
			var w = screen.availWidth - 10;
			var h = screen.availHeight - 82;
			var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			window.open("/ShortfallResendEmailAction.do?mode=doViewFilePdf&displayFile=<bean:write name="fileName"/>",'ShortfallScreen',features);
		</script>
	</logic:notEmpty>
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">List of Claim Shortfall's</td>
			
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
	
	
	<!-- S T A R T : Search Box -->
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	
	
	<%--Modified by satyaaa as per  Latest changes KOC 1179 --%>
		<tr class="searchContainerWithTab">
			<td nowrap> Claim No.:<br>
            	<html:text property="sClaimNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
            </td>
            <td nowrap> Shortfall No.:<br>
            	<html:text property="sShortfallNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
            </td>
        	<td nowrap> Shortfall Status:<br>
            	<html:select property="sShortfallStatus" styleClass="selectBox selectBoxMoreMedium">
		        	<html:optionsCollection name="sShortfallStatus" label="cacheDesc" value="cacheId" />
            	</html:select>
        	</td>
        	<td nowrap> Email ID:<br>
            	<html:select property="sEmailIDStatus" styleId="sEmailIDStatus" styleClass="selectBox selectBoxMedium" onchange="javascript:hidebutton()">
		  	 	<html:option value="NA">Not Available</html:option>
				<html:option value="AVAI">Available</html:option>
		  	 		
		  	 		
		  	 		<!-- html:optionsCollection name="sShortfallStatus" label="cacheDesc" value="cacheId" /-->
            	</html:select>
        	</td>
        	        	
        </tr>   
        <tr class="searchContainerWithTab">
        <td nowrap>Mobile Status:<br>
			<html:select property="sMobileStatus" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="NA">Not Available</html:option>
		  	 		<html:option value="AVAI">Available</html:option>
		  	 		<!-- html:optionsCollection name="sShortfallStatus" label="cacheDesc" value="cacheId" /-->
            	</html:select>
            	</td>
            <td nowrap>Vidal Health Branch:<br>
            	<html:select property="sTtkBranch" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sTtkBranch" label="cacheDesc" value="cacheId" />
            	</html:select>
        	</td>
        	
        	<td valign="bottom" nowrap>
	        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        	</td>
		</tr>   		
	</table>
	<!-- E N D : Search Box -->
	
	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->


	
	<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
		<logic:equal name="sEmailIDStatus" value="NA">
       	 
    	 <tr  id="GenerateSend" style="display: none">
        	<td width="100%" align="right">
	       	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateSend();">Generate & <u>S</u>end</button>&nbsp;
			 </td>
			 </tr>
			 <tr  id="GeneratePrint" style="display:">
			<td width="100%" align="right">
			<button type="button" name="Button" id="send" accesskey="p" style="display:" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGeneratePrint();">Generate & <u>P</u>rint</button>&nbsp;
		    </td>
		   </tr>
    		   </logic:equal>
		   <logic:notEqual name="sEmailIDStatus" value="NA">
		   
		    <tr  id="GenerateSend" style="display: ">
        	<td width="100%" align="right">
	       	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateSend();">Generate & <u>S</u>end</button>&nbsp;
			 </td>
			 </tr>
			 <tr  id="GeneratePrint" style="display: none">
			<td width="100%" align="right">
			<button type="button" name="Button" id="send" accesskey="p" style="display:" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGeneratePrint();">Generate & <u>P</u>rint</button>&nbsp;
		    </td>
		   </tr>
    	    	 
		   </logic:notEqual>
		<ttk:PageLinks name="tableData"/>
	</table>
	<!-- E N D : Buttons -->
	
	</div>	
	
	<html:hidden property="sShortfallStatus"/>
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="child" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="filename" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->