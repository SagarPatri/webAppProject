<%
/** @ (#) floataccountslist.jsp june 10th, 2006
 * Project    	 : TTK Healthcare Services
 * File       	 :

 * Author     	 : Arun K.M
 * Company    	 : Span Systems Corporation
 * Date Created	 : june 10, 2006
 * @author 		 : Arun K.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>

<%
	pageContext.setAttribute("listfloatType",Cache.getCacheObject("floatType"));
	pageContext.setAttribute("listacctStatus",Cache.getCacheObject("acctStatus"));
	pageContext.setAttribute("listofficeInfo",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("listinsuranceCompany",Cache.getCacheObject("insuranceCompany"));
	pageContext.setAttribute("productAuthority", Cache.getCacheObject("productAuthority")); 


%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/finance/floataccountlist.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript">
bAction = false;
var TC_Disabled = true;
</script>

	<html:form action="/FloatSearchAction.do"  method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="57%">List of Float Accounts</td>
    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
  </tr>
</table>
	<!-- E N D : Page Title -->

	<html:errors />
	<!-- S T A R T : Search Box -->
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
	  <td nowrap>Float No.:<br>
       			<html:text property="sFloatNo" styleClass="textBox textBoxMedium" maxlength="60"/>
       </td>
        <td nowrap>Float Name:<br>
            <html:text property="sFloatName" styleClass="textBox textBoxMedium" maxlength="60"/>
         </td>
        <td nowrap>Float Type:<br>
                <html:select property="sFloatType" styleClass="selectBox selectBoxMedium" >
				<html:option value="">Any</html:option>
				<html:optionsCollection name="listfloatType" label="cacheDesc" value="cacheId"/>
				</html:select>
		</td>
        <td nowrap>Status:<br>
            <html:select property="sStatus" styleClass="selectBox selectBoxMedium" >
				<html:option value="">Any</html:option>
				<html:optionsCollection name="listacctStatus" label="cacheDesc" value="cacheId"/>
				</html:select>
		</td>
		<td nowrap>Branch Location:<br>
            <html:select property="sTTKBranch" styleClass="selectBox selectBoxMedium" >
				<html:option value="">Any</html:option>
				<html:optionsCollection name="listofficeInfo" label="cacheDesc" value="cacheId"/>
				</html:select>
		</td>
      </tr>
      <tr>
        <td nowrap>Bank Name:<br>
            <html:text property="sBankName" styleClass="textBox textBoxMedium" maxlength="60"/>
         </td>
        <td nowrap>Bank Account No.:<br>
            <html:text property="sBankAccountNo" styleClass="textBox textBoxMedium" maxlength="60"/>
         </td>
        <td nowrap>Insurance Company:<br>
            <html:select property="sInsuranceCompany" styleClass="selectBox selectBoxMedium">
            <html:option value="">Any</html:option>
            <html:optionsCollection name="listinsuranceCompany" label="cacheDesc" value="cacheId"/>
            </html:select>
        </td>
        <td nowrap>Insurance Code:<br>
            <html:text property="sCompanyCode" styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>  
        
         <!--  Start AK     -->
      
		 <td nowrap>Insurance Authority(Product Authority):<br>
			<html:select property="productAuthority" styleClass="selectBox" style="width:160px;">
		  	 	  <html:option value="">Select from the List</html:option>
		          <html:optionsCollection name="productAuthority" label="cacheDesc" value="cacheId" />
            </html:select>
            
	    </td>
		
       <!-- End AK -->
             
	 </tr>
	 <tr>
        <td nowrap>Claim Settlement No.:<br>
            <html:text property="sClaimSettleNumber" styleClass="textBox textBoxMedium" maxlength="60"/>
         </td>
         <td valign="bottom" nowrap><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>        
	 </tr>
    </table>

	<!-- E N D : Search Box -->
    <!-- S T A R T : Grid -->
    <ttk:HtmlGrid name="tableData"/>
    <!-- E N D : Grid -->
    <!-- S T A R T : Buttons and Page Counter -->
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
		   		<button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard()"><u>C</u>opy to Web Board</button>&nbsp;
		    <%
	    		}
	    	%>

	    	<%

	    		if(TTKCommon.isAuthorized(request,"Add"))
	    		{
	    	%>

	    	<button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAdd()"><u>A</u>dd</button>&nbsp;
	    	<%
	    		}//end of if(TTKCommon.isAuthorized(request,"Add"))
	    		if(TTKCommon.isDataFound(request,"tableData")&& TTKCommon.isAuthorized(request,"Delete"))
	    		{
	    	%>
	       	<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
	    	<%
	    		}//end of if(TTKCommon.isDataFound(request,"tableData")&& TTKCommon.isAuthorized(request,"Delete"))
	    	%>
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
	<INPUT TYPE="hidden" NAME="tab" VALUE="">

	</html:form>