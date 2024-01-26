<%
/** @ (#) changepolicytype.jsp
 * Project     : TTK Healthcare Services
 * File        : changepolicytype.jsp
 * Author      : Balaji C R B
 * Company     : Span Systems Corporation
 * Date Created: 16,May 2008
 *
 * @author 		 :
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	pageContext.setAttribute("listEnrollmentType",Cache.getCacheObject("enrollTypeCode"));
	pageContext.setAttribute("listInsuranceCompany",Cache.getCacheObject("insuranceCompany"));
%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/maintenance/changepolicytype.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ChangePolicyTypeAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>List of Schemes</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->

	<div class="contentArea" id="contentArea">
	<!-- Start of Success Box -->
	<logic:notEmpty name="updated" scope="request">
    <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
           <bean:message name="updated" scope="request"/>
       </td>
     </tr>
    </table>
   </logic:notEmpty>
   	<!-- End of Success Box -->
   	<!-- Start of Mandatory fields checking-->
	<logic:notEmpty name="policynumberrequired" scope="request">
    <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td><font color="red"><img src="/ttk/images/ErrorIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
           <bean:message name="policynumberrequired" scope="request"/></font>
       </td>
     </tr>
    </table>
   </logic:notEmpty>
   	<!-- End of Mandatory fields checking -->
	<html:errors/>
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
	        <td nowrap>Scheme No.: <span class="mandatorySymbol">*</span><br>
	     	 	<html:text property="sPolicyNumber" styleClass="textBox textBoxMedium" maxlength="60"/>
	     	</td>
		  	<td valign="bottom" nowrap>Enrollment Type:<br>
		  		<html:select property="sEnrollmentType" styleClass="selectBox selectBoxMedium">
				<html:option value="">Any</html:option>
				<html:optionsCollection name="listEnrollmentType" label="cacheDesc" value="cacheId"/>
				</html:select>				
			</td>
		 	 <td nowrap>Healthcare Company:<br>
				<html:select property="sInsuranceCompany" styleClass="selectBox selectBoxLarge" >
				<html:option value="">Any</html:option>
				<html:optionsCollection name="listInsuranceCompany" label="cacheDesc" value="cacheId"/>
				</html:select>
			</td>
	       <td nowrap>&nbsp;<br>	  		
			<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
		   </td>		
		  <td width="100%" nowarap>&nbsp;</td>
      </tr>     
    </table>
	<!-- E N D : Search Box -->

	<!-- S T A R T : Grid -->
	    <ttk:HtmlGrid name="tableData" />
    <!-- E N D : Grid -->

	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="73%" nowrap>&nbsp;
	    </td>
	    <td align="right" nowrap>
	    	<%
	    		if(TTKCommon.isDataFound(request,"tableData"))
	    		{
	    	%>
		    		<script language="javascript">
							onDocumentLoad(<%=request.getAttribute("selectedRecord")%>);
					</script>
					<%
		  				if(TTKCommon.isAuthorized(request,"SpecialPermission"))
		  				{
	      			%>
					<logic:equal name="frmChangePolicyType" property="sPolicySubTypeID" value="PFL">
						<button type="button" name="ctnfbutton" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onChangeToNonFloater();"><u>C</u>hange to Non-Floater</button>&nbsp;
					</logic:equal>
		    		<logic:equal name="frmChangePolicyType" property="sPolicySubTypeID" value="PNF">
		    			<button type="button" name="ctfbutton" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onChangeToFloater();"><u>C</u>hange to Floater</button>&nbsp;
		    		</logic:equal>
		    <%
				  
		   		        }//end of if(TTKCommon.isAuthorized(request,"SpecialPermission"))
  	      	    }//end of if(TTKCommon.isDataFound(request,"tableData"))	    		
	    	%>	
	    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;    			
	    </td>
	  </tr>
  		<ttk:PageLinks name="tableData"/>
	</table>
	
</div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	</html:form>
	<!-- E N D : Content/Form Area -->
