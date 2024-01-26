<%
/** @ (#) productlist.jsp 14th Nov 2005
 * Project     : TTK Healthcare Services
 * File        : productlist.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 14th Nov 2005
 *
 * @author 		 : Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<%
	pageContext.setAttribute("insuranceCompany", Cache.getCacheObject("insuranceCompany"));
	pageContext.setAttribute("productStatusCode", Cache.getCacheObject("productStatusCode"));
	pageContext.setAttribute("productAuthority", Cache.getCacheObject("productAuthority")); 
%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/productlist.js"></script>
<SCRIPT LANGUAGE="JavaScript">

bAction = false; //to avoid change in web board in product list screen
var TC_Disabled = true;
</SCRIPT>

<!-- S T A R T : Content/Form Area -->
	<html:form action="/ProductListAction.do" method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>List of Products</td>
        <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
      </tr>
    </table>
<!-- E N D : Page Title -->
<div class="contentArea" id="contentArea">

<!-- S T A R T : Search Box -->
<html:errors/>
<table align="center" class="searchContainer" border="0" cellspacing="2" cellpadding="0">
	<tr>
		<td nowrap>Insurance Company:<br>
			<html:select property="insuranceCompany" styleClass="selectBox selectBoxMedium">
		  	 	  <html:option value="">Any</html:option>
		          <html:optionsCollection name="insuranceCompany" label="cacheDesc" value="cacheId" />
            </html:select>
	    </td>
        <td nowrap>Product Name:<br>
            <html:text property="sProductName" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
         
        <td nowrap>Status:<br>
			<html:select property="productStatusCode" styleClass="selectBox" style="width:160px;">
		  	 	  <html:option value="">Any</html:option>
		          <html:optionsCollection name="productStatusCode" label="cacheDesc" value="cacheId" />
            </html:select>
	    </td>
	    
        <td nowrap>Product Code:<br>
            <html:text property="sProductCode" styleClass="textBox textBoxMedium" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
        
         <td nowrap>Product Authority:<br>
			<html:select property="productAuthority" styleClass="selectBox" style="width:160px;">
		  	 	  <html:option value="">Select from the List</html:option>
		          <html:optionsCollection name="productAuthority" label="cacheDesc" value="cacheId" />
            </html:select>
            
	    </td>
        <td width="100%" valign="bottom">
        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
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
		    		<button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard();"><u>C</u>opy to Web Board</button>&nbsp;

		    <%
	    		}//end of if(TTKCommon.isDataFound(request,"tableData"))
	    		if(TTKCommon.isAuthorized(request,"Add"))
	    		{
	    	%>
	    			<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddProduct();"><u>A</u>dd</button>&nbsp;
	    	<%
	    		}//end of if(TTKCommon.isAuthorized(request,"Add"))
	    		if(TTKCommon.isDataFound(request,"tableData")&& TTKCommon.isAuthorized(request,"Delete"))
	    		{
	    	%>
	    	   		<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>&nbsp;
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
<!-- E N D : Content/Form Area -->