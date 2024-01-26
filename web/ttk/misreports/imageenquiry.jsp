
<%
/** @ (#) imageenquiry.jsp May 28, 2007
 * Project     : TTK Healthcare Services
 * File        : imageenquiry.jsp
 * Author      : Ajay Kumar
 * Company     : WebEdge Technologies Pvt.Ltd.
 * Date Created: May 28, 2007
 *
 * @author 		 : Ajay Kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.misreports.ReportCache" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/misreports/imageenquiry.js"></script>

<%
   // boolean viewmode=true;
   boolean viewmode=false;
	
    pageContext.setAttribute("policyTypeID", ReportCache.getCacheObject("enrollTypeCode"));
	
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ImageEnquiryAction.do">
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>List of Image Enquiry</td>
    	</tr>
</table>
<!-- E N D : Page Title -->
<div class="contentArea" id="contentArea">
<!-- S T A R T : Search Box -->
<html:errors/>

<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
<tr>
     <td nowrap>Scheme No.:<br>
       <html:text property="policyNbr" styleClass="textBox textBoxMedium" maxlength="250" />
       </td>
       <td nowrap>Enrollment No.:<br>
        <html:text property="enrollmentNbr" styleClass="textBox textBoxMedium" maxlength="250" />
        </td>
        <td nowrap>Enrollment ID:<br>
         <html:text property="enrollmentID" styleClass="textBox textBoxMedium" maxlength="250" />
         </td>
          <td nowrap>Member Name:<br>
         <html:text property="memberName" styleClass="textBox textBoxMedium" maxlength="250" />
         </td>
         <td nowrap>Corp. Name:<br>
        <html:text property="corporateName" styleClass="textBox textBoxMedium" maxlength="250" />
        </td>
        </tr>
        <tr>
        <td nowrap>Group Id:<br>
          <html:text property="groupId" styleClass="textBox textBoxMedium" maxlength="250" />
      </td>
      <td nowrap>Scheme Type:<br>
		<html:select property="policyType" styleId="enroltype" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
		  	 	<html:option value="">Select from list</html:option>
		  	 	 <!-- <html:option value="ALL">ALL</html:option> -->
		  	 	  <html:options collection="policyTypeID" property="cacheId" labelProperty="cacheDesc"/>
            </html:select>
	    </td>
    <td width="100%" valign="bottom">
    <a href="#" accesskey="s" onClick="javascript:onSearch()" class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
   </td>
</tr>
</table>

<!-- E N D : Search Box -->
<!-- S T A R T : Out put Table -->
<hr width="100%" size="1" noshade="true">
<logic:present name="frmImageEnquiry" property="alImageEnquiryList">
	<bean:size id="size" name="frmImageEnquiry" property="alImageEnquiryList"/>
	<logic:equal name="size" value="0">

	<table width="98%" align="center" border="1" cellspacing="0" cellpadding="0">

	<tr class="gridHeader">
        <th>Scheme No.</th>
        <th>Enrollment Id.</th>
        <th>Employee No.</th>
        <th>Corp. Name</th>
        <th>Member Name</th>
        <th>Image</th>
       
	</tr>
	<tr>
	<td class=\"generalcontent\">&nbsp;&nbsp;No Data Found</td>
	</tr>
	</table>
 	</logic:equal>
	<logic:greaterThan name="size" value="0">
		<table width="98%" align="center" border="1" cellspacing="0" cellpadding="0">
		<tr class="gridHeader">
        	<th>Scheme No.</th>
        	<th>Enrollment Id.</th>
        	<th>Employee No.</th>
        	<th>Corp. Name</th>
        	<th>Member Name</th>
        	<th>Image</th>
		</tr>

<%int index=-1; %>

<logic:iterate id="imageList" property="alImageEnquiryList" name="frmImageEnquiry" >
<%index=index+1; %>
<tr>
<td>
<bean:write name="imageList" property="policyNbr"/>
</td>
<td>
<bean:write name="imageList" property="enrollmentID"/>
</td>
<td>
<bean:write name="imageList" property="employeeNbr"/>
</td>
<td>
<bean:write name="imageList" property="corporateName"/>
</td>
<td>
<bean:write name="imageList" property="memberName"/>
</td>

<td>
<!-- i am using a image servlet names ImageServlet with a URL mapping /ImageServlet and accepts choiceid as a request parameter -->
 
<iframe align="center" width="60" height="76" marginwidth="1" marginheight="1" scrolling="no" src="ImageServlet?choiceid=<%=index%>,<bean:write name="imageList" property="policyNbr"/>,<bean:write name="imageList" property="enrollmentNbr"/>,<bean:write name="imageList" property="enrollmentID"/>,<bean:write name="imageList" property="memberName"/>,<bean:write name="imageList" property="corporateName"/>,<bean:write name="imageList" property="groupId"/>,<bean:write name="imageList" property="policyType"/>" border="0" frameborder="0">
</iframe>
 </td>
</tr>
</logic:iterate>

</table> 
</logic:greaterThan>
	</logic:present>
<!-- E N D :Out put Table-->
</div>
   
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	
</html:form>
<!-- E N D : Content/Form Area -->
