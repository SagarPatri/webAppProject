<%
/** @ (#) policylist.jsp Jul 26, 2007
 * Project     : Vidal Health TPA Services
 * File        : policylist.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: Jul 26, 2007
 *
 * @author 		 : Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/accountinfo/policylist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
bAction = false; //to avoid change in web board in product list screen
var TC_Disabled = true;
</SCRIPT>
<%
	pageContext.setAttribute("policyType",Cache.getCacheObject("enrollTypeCode"));
	pageContext.setAttribute("insuranceCompany", Cache.getCacheObject("insuranceCompany"));
	pageContext.setAttribute("listTTKBranch",Cache.getCacheObject("officeInfo"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/PolicyAccountInfoAction.do">
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>List of Enrollments</td>
		<td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	</tr>
</table>
<!-- E N D : Page Title -->

<div class="contentArea" id="contentArea">
<html:errors/>

<table align="center" class="searchContainer" border="0" cellspacing="2" cellpadding="2">
      <tr>
	      	<td nowrap>Enrollment Id:<br>
			            <html:text property="sEnrollmentNumber" styleClass="textBox textBoxMedium" styleId="search1" maxlength="60"/>
	        </td>
	      	<td nowrap>Healthcare Company:<br>
	          <html:select property="sInsuranceCompany" styleClass="selectBox selectBoxMedium" styleId="search12">
			  	 	  <html:option value="">Any</html:option>
			          <html:optionsCollection name="insuranceCompany" label="cacheDesc" value="cacheId" />
	          </html:select>
	        </td>
	        <td nowrap>Policy Type: <br>
		      <html:select  property="sPolicyType"  styleClass="selectBox selectBoxMedium" styleId="search11" onchange="javascript:changePolicyType();">
		          	<html:optionsCollection name="policyType" value="cacheId" label="cacheDesc"/>
		      </html:select>
			
	        <td nowrap>Vidal Health Branch:<br>
	            <html:select property="sTTKBranch" styleClass="selectBox selectBoxMedium" styleId="search13" >
					<html:option value="">Any</html:option>
					<html:optionsCollection name="listTTKBranch" label="cacheDesc" value="cacheId"/>
				</html:select>
			</td>&nbsp;&nbsp;&nbsp;&nbsp;
			<td nowrap>Policy No.:<br>
	            <html:text property="sPolicyNumber" styleClass="textBox textBoxMedium"  styleId="search3" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
	        </td>
	      	
       </tr>
       <tr>   
	        <td nowrap>Employee No.:<br>
	        <logic:empty name="frmPolicyAccountInfo" property="sPolicyType">
	        	<html:text property="sEmployeeNumber"  styleClass="textBox textBoxMedium" styleId="search8" maxlength="60" style="background-color: #EEEEEE;" readonly="true"/>
	        </logic:empty>
	        <logic:notEmpty name="frmPolicyAccountInfo" property="sPolicyType">
		        <logic:match name="frmPolicyAccountInfo" property="sPolicyType" value="IND">
		            	<html:text property="sEmployeeNumber"  styleClass="textBox textBoxMedium" styleId="search8" maxlength="60" style="background-color: #EEEEEE;" readonly="true"/>
		        </logic:match>
		        <logic:match name="frmPolicyAccountInfo" property="sPolicyType" value="ING">
		        	<html:text property="sEmployeeNumber"  styleClass="textBox textBoxMedium" styleId="search8" maxlength="60" style="background-color: #EEEEEE;" readonly="true"/>
		        </logic:match>
		        <logic:match name="frmPolicyAccountInfo" property="sPolicyType" value="COR">
					<html:text property="sEmployeeNumber"  styleClass="textBox textBoxMedium" styleId="search8" maxlength="60"/>
		        </logic:match>
		        <logic:match name="frmPolicyAccountInfo" property="sPolicyType" value="NCR">
					<html:text property="sEmployeeNumber"  styleClass="textBox textBoxMedium" styleId="search8" maxlength="60"/>
		        </logic:match>
	        </logic:notEmpty>
	        </td>
	         <td nowrap>Member Name:<br>
	        	<html:text property="sMemberName" styleClass="textBox textBoxMedium" styleId="search9" maxlength="60"/>
	        </td>
	          <td nowrap>Emirates ID:<br>
		    	<html:text property="sEmiratesID" name="frmPolicyAccountInfo"  styleClass="textBox textBoxMedium" maxlength="250"/>
		    </td>
	        <td nowrap>Bank Acct. No.:<br>
	            	<html:text property="sOrderNumber"  styleClass="textBox textBoxMedium"  styleId="search6" maxlength="60"/>
	        </td>&nbsp;&nbsp;&nbsp;&nbsp;
	        <td nowrap>Group Id:<br>
	        <logic:empty name="frmPolicyAccountInfo" property="sPolicyType">
	        	<html:text property="sGroupId"  styleClass="textBox textBoxSmall"  styleId="search7" maxlength="60" style="background-color: #EEEEEE;" readonly="true"/>
	        </logic:empty>
	        <logic:notEmpty name="frmPolicyAccountInfo" property="sPolicyType">
		        <logic:match name="frmPolicyAccountInfo" property="sPolicyType" value="IND">
		            		<html:text property="sGroupId"  styleClass="textBox textBoxSmall"  styleId="search7" maxlength="60" style="background-color: #EEEEEE;" readonly="true"/>
				</logic:match>
				<logic:notMatch name="frmPolicyAccountInfo" property="sPolicyType" value="IND">
					<html:text property="sGroupId"  styleClass="textBox textBoxSmall"  styleId="search7" maxlength="60"/>
					<a href="#" accesskey="g"  onClick="javascript:SelectGroup()" class="search">
								<img src="/ttk/images/EditIcon.gif" title="Select Group" alt="Select Group" width="16" height="16" border="0" align="absmiddle">&nbsp;
					</a>
					<a href="#" onClick="ClearCorporate()"><img src="/ttk/images/DeleteIcon.gif" title="Clear Group" alt="Clear Group" width="16" height="16" border="0" align="absmiddle"></a>
				</logic:notMatch>	
			</logic:notEmpty>		
	        </td>
			  
		</tr>
		
		<tr>
			  <td  nowrap>Group Name:<br>
		  		<html:text property="sGroupName"  styleClass="textBox textBoxMedium" style="background-color: #EEEEEE;" styleId="search7" maxlength="60" readonly="true"/>&nbsp;
		      </td> 
			<td nowrap>Policy Name:<br>
            	<html:text property="sSchemeNumber"  styleClass="textBox textBoxMedium"  styleId="search5" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);"/>
        	</td> 
        	<td nowrap>Certificate No.:<br>
            	<html:text property="sCertificateNumber"  styleClass="textBox textBoxMedium"  styleId="search4" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);"/>
        	</td>
        	<td nowrap>Customer Code:<br>
            	<html:text property="sCustomerCode"  styleClass="textBox textBoxMedium" styleId="search2" maxlength="60"/>
        	</td>
	        <td width="100%" valign="bottom" nowrap>
	        <a href="#" accesskey="s" onClick="javascript:onSearch()" class="search">&nbsp;&nbsp;&nbsp;&nbsp; 
		        <img src="/ttk/images/SearchIcon.gif" title="Search" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch
	        </a>
	    	</td>
      </tr>
    </table>
	<!-- E N D : Search Box -->
<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableData"/>
<!-- E N D :  Grid -->

<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="27%">&nbsp;</td>
	    
	    
	    <td width="73%" align="right" nowrap>
    	<%
    		if(TTKCommon.isDataFound(request,"tableData"))
    		{
    	%>
	    		<button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard();"><u>C</u>opy to Web Board</button>&nbsp;
	    <%
    		}//end of if(TTKCommon.isDataFound(request,"tableData"))
    	%>
	    </td>
	  </tr>
  	  <ttk:PageLinks name="tableData"/>
	</table>
	</div>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
    </html:form>
	<!-- E N D : Content/Form Area -->