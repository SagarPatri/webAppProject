<!DOCTYPE html>
<%
/** @ (#) locationswithcopay.jsp 11 May 2017
 * Project     :Project X
 * File        : locationswithcopay.jsp
 * Author      : Nagababu K
 * Company     : RCS Technologies
 * Date Created: 11 May 2017
 *
 * @author 	   : Nagababu K
 * Modified by   : 
 * Modified date : 
 * Reason        :
 *
 */
%>


<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%
  pageContext.setAttribute("listGeneralCodePlan",Cache.getCacheObject("generalCodePlan"));
  pageContext.setAttribute("listDurationType",Cache.getCacheObject("durationType"));
  pageContext.setAttribute("benefitTypes",Cache.getCacheObject("benefitTypes"));
  pageContext.setAttribute("claimTypes", Cache.getCacheObject("claimType"));
  boolean viewmode=true;
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }//end of if(TTKCommon.isAuthorized(request,"Edit"))
  pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>
<html>
<head>
<title>Provider Co-pay Details</title>
<script type="text/javascript"  SRC="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/administration/mohlocationswithcopay.js"></script>
<script type="text/javascript" src="/ttk/scripts/utils.js"></script>
<script type="text/javascript" SRC="/ttk/scripts/trackdatachanges.js"></script>
<script type="text/javascript" SRC="/ttk/scripts/common/healthcarelayout.js"></script>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<style type="text/css">
.buttonsHover{
  color:#CD0B3D;
cursor: pointer;
}
</style>
</head>
<body>
<!-- S T A R T : Content/Form Area -->



<html:form action="/MOHProviderCopayDetailsAction.do" >

<!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td> <bean:write name="frmMOHProviderCopayRules" property="caption" /></td>
    </tr>
  </table>
<!-- E N D : Page Title -->
  <html:errors/>
  <!-- S T A R T : Success Box -->
 
 <!-- E N D : Success Box -->
<!-- S T A R T : Form Fields -->
<logic:notEmpty name="addedMsg" scope="request">
				<table align="center" class="successContainer" style="display:"
					border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="addedMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
<logic:equal value="PRO" name="frmMOHProviderCopayRules" property="locationType">
<table align="left" style="color:#000000; border: 1px solid #000000; background-color:#EBEDF2;  margin-top:30px; margin-left: 20px;" border="0" cellspacing="0" cellpadding="0">
		<tr>
		    <td style="margin-top:10px; margin-left: 10px;" nowrap>&nbsp;&nbsp;&nbsp;Provider License ID:<br>
		    	<html:text property="providerLicenseNO" name="frmMOHProviderCopayRules"  styleClass="textBox textBoxLarge" maxlength="60"/>
		    </td>
		    <td style="margin-top:10px; margin-left: 10px;" nowrap>&nbsp;&nbsp;Provider Name:<br>
		    <table>
		    <tr>		    
		    <td><html:text property="providerName" name="frmMOHProviderCopayRules"  styleClass="textBox textBoxLarge" maxlength="60"/></td>
		    <td>
		    <a href="#" accesskey="s" onClick="javascript:onSearchProviders()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
		   
		    </tr>
		    </table>
            	
        	</td>
        	
    	</tr>
        	<tr>
        	 <td colspan="2" align="center">
        	 
        	 </td>
        	</tr>
	</table>
	</logic:equal>
  <fieldset>
  <legend><bean:write name="frmMOHProviderCopayRules" property="benefitTypeDesc"/>&nbsp;Configuration</legend>
<div class="contentArea" id="contentArea" style="height: 350px;">
 <ttk:MOHProviderLocationCopayDetails/>
 </div>
 
 </fieldset>
  <!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
   
   <logic:match name="viewmode" value="false">
   <logic:equal name="providerSearch" value="yes">
    <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddHospCoayDetails();"><u>A</u>dd</button>&nbsp;
        	 </logic:equal>
   <logic:notEqual name="providerSearch" value="yes">
     
      <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
   
   </logic:notEqual>
    </logic:match>
    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	
	</td>
  </tr>
</table>

<!-- E N D : Buttons -->

<INPUT TYPE="hidden" NAME="mode" VALUE="">

<html:hidden name="frmMOHProviderCopayRules" property="locationType"/>
<html:hidden name="frmMOHProviderCopayRules" styleId="benefitType"  property="benefitType"/>
</html:form>

</body>
</html>