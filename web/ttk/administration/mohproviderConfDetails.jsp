<!DOCTYPE html>
<%
/** @ (#) mohproviderConfDetails.jsp 11 May 2017
 * Project     :Project X
 * File        : providerConfDetails.jsp
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
<title>Provider Configuration Details</title>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<style type="text/css">

</style>
</head>
<body>
<!-- S T A R T : Content/Form Area -->

<html:form action="/MOHProviderCopayDetailsAction.do" >

<!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td> <bean:write name="caption" scope="request"/></td>
    </tr>
  </table>
<!-- E N D : Page Title -->
  <html:errors/>
  <fieldset>
  <legend></legend>
<div class="contentArea" id="contentArea">
 <ttk:MOHProviderConfDetailsView/>
 
 </div>
 
 </fieldset>


</html:form>

</body>
</html>