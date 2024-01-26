<%
/**
 * @ (#) supportdoclist.jsp 06th May 2006
 * Project      : TTK HealthCare Services
 * File         : supportdoclist.jsp
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : 06th May 2006
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
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper,com.ttk.dto.claims.ClaimIntimationVO" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/preauth/supportdoclist.js"></script>
<script>
function onEditIcon()
{
	document.forms[1].child.value="Intimations";
    document.forms[1].mode.value="doDefault";
    document.forms[1].action="/IntimationsAction.do";
    document.forms[1].submit();
}//end of onEditIcon()
</script>
<%
  if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
  {
    if(PreAuthWebBoardHelper.checkWebBoardId(request)!=null && PreAuthWebBoardHelper.getBufferAllowedYN(request).equals("Y"))
    {
      pageContext.setAttribute("DocTypeID",Cache.getCacheObject("supportBuffer"));
    }
    else
    {
      pageContext.setAttribute("DocTypeID",Cache.getCacheObject("documentType"));
    }
  }//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
  else
  {
    if(ClaimsWebBoardHelper.checkWebBoardId(request)!=null && ClaimsWebBoardHelper.getBufferAllowedYN(request).equals("Y"))
    {
      pageContext.setAttribute("DocTypeID",Cache.getCacheObject("claimsSupportBuffer"));
    }
    else
    {
      pageContext.setAttribute("DocTypeID",Cache.getCacheObject("claimsSupportDoc"));
    }
  }//end of else if(TTKCommon.getActiveLink(request).equals("Claims"))
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/SupportDocAction.do">

<!-- S T A R T : Page Title -->
<%
  if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
  {
%>
    <table align="center" class="<%=TTKCommon.checkNull(PreAuthWebBoardHelper.getShowBandYN(request)).equals("Y") ? "pageTitleHilite" :"pageTitle" %>" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="57%"><bean:write name="frmSuppDoc" property="caption"/></td>
            <td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
      </tr>
    </table>
<%
  }
  if(TTKCommon.getActiveLink(request).equals("Claims"))
  {
%>
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="57%"><bean:write name="frmSuppDoc" property="caption"/></td>
          <td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
      </tr>
    </table>
<%
  }
%>
<!-- E N D : Page Title -->
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
<!-- S T A R T : Search Box -->
  <table align="center" class="tablePad" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap width="15%" class="textLabelBold">Document Type:</td>
      <td width="85%">
        <html:select property="documentType"  styleClass="specialDropDown" onchange="onSearch()">
            <html:options collection="DocTypeID"  property="cacheId" labelProperty="cacheDesc"/>
          </html:select>
          </td>
    </tr>
    </table>
    <!-- E N D : Search Box -->
<logic:notMatch name="frmSuppDoc" property="documentType" value="DCL" >

  <logic:match name="frmSuppDoc" property="documentType" value="SCI" >

  <fieldset align="center">
    <legend>Claim Intimation Details </legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" height="20" nowrap class="formLabel">Estimated Amt. (Rs): </td>
        <td width="35%" nowrap class="textLabel"><bean:write name="claimIntimationVO" property="estimatedAmt"/></td>
        <td width="15%" nowrap class="formLabel">Intimation Date: </td>
        <td width="30%" nowrap class="textLabelBold"><bean:write name="claimIntimationVO" property="claimIntimationDate"/>&nbsp;&nbsp;&nbsp;
        <%
	        if(TTKCommon.isAuthorized(request,"Edit"))
            {
        %>
	        <a href="#" onclick="javascript:onEditIcon();"><img src="ttk/images/EditIcon.gif" alt="Select Claim Intimation" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;
	        <a href="#" onclick="javascript:onDeleteIcon();"><img src="ttk/images/DeleteIcon.gif" alt="Clear Intimation Date" width="16" height="16" border="0" align="absmiddle"></a>
	    <%
	    	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	    %>
        </td>
      </tr>
      <tr>
        <td height="20" nowrap class="formLabel">Ailment Details:</td>
        <td nowrap class="textLabel"><bean:write name="claimIntimationVO" property="ailmentDesc"/></td>
        <td nowrap class="formLabel">Likely Date of Hospt.:  </td>
        <td nowrap class="textLabelBold"><bean:write name="claimIntimationVO" property="claimLikelyDateOfHosp"/> </td>
      </tr>
       <tr>
        <!--koc 1339 for mail  -->
        <td  height="20" nowrap class="formLabel">Source.:  </td>
        <td  nowrap class="textLabel"><bean:write name="claimIntimationVO" property="source"/> </td>
        <td nowrap class="formLabel">Patient Name.:  </td>
        <td nowrap class="textLabel"><bean:write name="claimIntimationVO" property="patientName"/> </td>
        <!--koc 1339 for mail  -->
        </tr>
      <tr>
        <td nowrap class="formLabel">Hospital Information:</td>
        <td colspan="3" nowrap class="textLabel"><bean:write name="claimIntimationVO" property="hospitalName"/></td>
        </tr>
      <tr>
        <td nowrap class="formLabel">&nbsp;</td>
        <td colspan="3" class="textLabel"><bean:write name="claimIntimationVO" property="hospitalAaddress"/></td>
        </tr>    
    </table>
<input type="hidden" name="hidCallLogSeqID" value="<bean:write name="claimIntimationVO" property="callLogSeqID" />" >

    </fieldset>
    <!-- E N D : Grid -->
  <!-- S T A R T : Buttons and Page Counter -->
  <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="center">
	    <%
	        if(TTKCommon.isAuthorized(request,"Edit"))
            {
        %>
	        <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClaimIntimationSave();"><u>S</u>ave</button>&nbsp;
    	    <button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onIntimationReset();"><u>R</u>eset</button>
    	<%
    		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
    	%>
        </td>
      </tr>
    </table>
  <!-- E N D : Buttons and Page Counter -->

  </table>
</logic:match>

  <logic:notMatch name="frmSuppDoc" property="documentType" value="SCI" >

  <!-- S T A R T : Grid -->
  <ttk:HtmlGrid name="tableData"/>
  <!-- E N D : Grid -->

  <!-- S T A R T : Buttons and Page Counter -->
    <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="27%"></td>
            <td width="73%" align="right">
              <%
            if(TTKCommon.isAuthorized(request,"Add"))
            {
          %>
              <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd();"><u>A</u>dd</button>&nbsp;

              <%
                }// end of if(TTKCommon.isAuthorized(request,"Add"))
            if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Delete"))
            {
          %>
                  <button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>&nbsp;
               <%
                }// end of if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Delete"))
              %>
            </td>
          </tr>
          <ttk:PageLinks name="tableData"/>

      </logic:notMatch>
</logic:notMatch>
<logic:match name="frmSuppDoc" property="documentType" value="DCL" >
    <ttk:DocumentChkList />
    <!-- S T A R T : Buttons and Page Counter -->
      <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
      <tr>
      <td align="center">
      <%
          if(TTKCommon.isAuthorized(request,"Document Checklist")&& TTKCommon.isAuthorized(request,"Edit"))
          {
      %>
			  <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
    	   	  <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>
      <%} %>
      </td>
	  </tr>
	  </table>
</logic:match>
</table>
<!-- E N D : Buttons and Page Counter -->
</div>
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
<input type="hidden" name="child" value="">
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">
<INPUT TYPE="hidden" NAME="pageId" VALUE="">
<logic:notEmpty name="frmSuppDoc" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
</html:form>
<!-- E N D : Content/Form Area -->