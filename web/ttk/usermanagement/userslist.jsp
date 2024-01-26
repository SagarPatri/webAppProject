<%
/**
 * @ (#) userslist.jsp 28th Dec 2005
 * Project      : TTK HealthCare Services
 * File         : userslist.jsp
 * Author       : Pradeep.R
 * Company      : Span Systems Corporation
 * Date Created : 28th Dec 2005
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%
	pageContext.setAttribute("listUsertype",Cache.getCacheObject("tpaUsers"));
	pageContext.setAttribute("listTTKBranch",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("listCityCode",Cache.getCacheObject("cityCode"));
	pageContext.setAttribute("listInsuranceCompany",Cache.getCacheObject("insuranceCompany"));
	pageContext.setAttribute("listBrokerCompany",Cache.getCacheObject("brokerCompany"));
	pageContext.setAttribute("listUserStatus",Cache.getCacheObject("userStatus"));
%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/usermanagement/userlist.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<html:form action="/UserListAction.do" >
<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <logic:match name="frmUserList" property="sublink" value="ADM">
		  <tr>
		    <td>List of Users</td>
			<!-- Changes Added for Password Policy CR KOC 1235 -->
			<td width="35%">
				<a href="#" onClick="onConfiguration()">
					<img src="/ttk/images/EditIcon.gif" alt="Configuration List" width="16"	height="16" border="0" align="absmiddle">
				</a>
			</td>
			<!-- End changes for Password Policy CR KOC 1235 -->
			<td width="43%" align="right" class="webBoard">&nbsp;</td>
		  </tr>
	  </logic:match>
	   <logic:match name="frmUserList" property="sublink" value="FIN">
		  <tr>
		    <td width="57%">Authorised Signatories </td>
		    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
		  </tr>
	  </logic:match>
	</table>
<!-- E N D : Page Title -->
	<html:errors/>
	<div class="contentArea" id="contentArea">
<!-- S T A R T : Search Box -->
	 <logic:match name="frmUserList" property="sublink" value="ADM">
		<table align="center" class="tablePad"  border="0" cellspacing="0" cellpadding="0">
	      <tr>
				<td width="10%" nowrap class="textLabelBold">User Type:</td>
		        <td width="90%">
			        <html:select property="sUserList" styleClass="specialDropDown" styleId="userType" onchange="javascript:onUserTypeChange()">
						<html:options collection="listUsertype" property="cacheId" labelProperty="cacheDesc"/>
					</html:select>
				</td>
		  </tr>
	    </table>
	 </logic:match>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>User ID:<br>
        	<html:text property="sUserId" styleClass="textBox textBoxMedium" maxlength="30"/> </td>

		<td nowrap>Name:<br>
		 	<html:text property="sName" styleClass="textBox textBoxMedium" maxlength="60"/> </td>

		<td valign="bottom" nowrap>Role:<br>
		 			<html:select property="sRoleId" styleClass="selectBox selectBoxMedium" >
     					<html:option value="">Any</html:option>
     					<html:optionsCollection name="frmUserList" property="listRole" label="cacheDesc" value="cacheId"/>
					</html:select>
        </td>
         <logic:match name="frmUserList" property="sublink" value="ADM">
	   	  	<td width="100%">Status:<br>
					<html:select property="sUserStatus"  styleClass="selectBox selectBoxMedium" >
						<html:options collection="listUserStatus" property="cacheId" labelProperty="cacheDesc"/>
					</html:select>
		  	</td>
		 </logic:match>
		 <logic:match name="frmUserList" property="sublink" value="FIN">
		 	 <td valign="bottom" nowrap>Branch Location:<br>
	            	<html:select property="sTTKBranch" styleClass="selectBox selectBoxMedium" >
					<html:option value="">Any</html:option>
					<html:optionsCollection name="listTTKBranch" label="cacheDesc" value="cacheId"/>
					</html:select>
	         </td>
	         <td width="100%" valign="bottom" nowrap ><a href="#" onClick="javascript:onSearchFinance()" class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;Search</a></td>
	      </logic:match>
	  </tr>

	  <tr>
     		<logic:match name="frmUserList" property="sUserList" value="TTK">
	            <td valign="bottom" nowrap>Vidal Health Branch:<br>
	            	<html:select property="sTTKBranch" styleClass="selectBox selectBoxMedium" >
					<html:option value="">Any</html:option>
					<html:optionsCollection name="listTTKBranch" label="cacheDesc" value="cacheId"/>
					</html:select>
	            </td>
           </logic:match>
           <!--kocb-->
               <logic:match name="frmUserList" property="sUserList" value="BRO">
		 		<td valign="bottom" nowrap>Broker Company:<br>
			  			<html:select property="sBrokerCompany" styleClass="selectBox selectBoxMedium" >
						<html:option value="">Any</html:option>
						<html:optionsCollection name="listBrokerCompany" label="cacheDesc" value="cacheId"/>
						</html:select>
			 	 </td>
		     	 <td valign="bottom" nowrap>Broker Code:<br>
				 <html:text property="sOfficeCode" styleClass="textBox textBoxMedium" maxlength="30"/></td>
			</logic:match>
          

           <logic:match name="frmUserList" property="sUserList" value="CAL">
	            <td valign="bottom" nowrap>Vidal Health Branch:<br>
	            	<html:select property="sTTKBranch" styleClass="selectBox selectBoxMedium" >
					<html:option value="">Any</html:option>
					<html:optionsCollection name="listTTKBranch" label="cacheDesc" value="cacheId"/>
					</html:select>
	            </td>
           </logic:match>

	 		<logic:match name="frmUserList" property="sUserList" value="DMC">
	            <td valign="bottom" nowrap>Vidal Health Branch:<br>
	            	<html:select property="sTTKBranch" styleClass="selectBox selectBoxMedium" >
					<html:option value="">Any</html:option>
					<html:optionsCollection name="listTTKBranch" label="cacheDesc" value="cacheId"/>
					</html:select>
	            </td>
           </logic:match>

	       <logic:match name="frmUserList" property="sUserList" value="HOS">
		      	<td valign="bottom" nowrap>Hospital Name:<br>
		      		<html:text property="sHospitalName" styleClass="textBox textBoxMedium" maxlength="250"/>
		      	</td>
				<td nowrap>City:<br>
					<html:select property="sCityCode" styleClass="selectBox selectBoxMedium" >
					<html:option value="">Any</html:option>
					<html:optionsCollection name="listCityCode" label="cacheDesc" value="cacheId"/>
					</html:select>
				</td>
				<td nowrap>Empanelment&nbsp;No.:<br>
					<html:text property="sEmpanelmentNO" styleClass="textBox textBoxMedium" maxlength="60"/>
				</td>
			</logic:match>
           <logic:match name="frmUserList" property="sUserList" value="PTR">
		      	<td valign="bottom" nowrap>Partner Name:<br>
		      		<html:text property="sPartnerName" styleClass="textBox textBoxMedium" maxlength="250"/>
		      	</td>
				<td nowrap>Area:<br>
					<html:select property="sCityCode" styleClass="selectBox selectBoxMedium" >
					<html:option value="">Any</html:option>
					<html:optionsCollection name="listCityCode" label="cacheDesc" value="cacheId"/>
					</html:select>
				</td>
				<td nowrap>Empanelment&nbsp;No.:<br>
					<html:text property="sEmpanelmentNO" styleClass="textBox textBoxMedium" maxlength="60"/>
				</td>
			</logic:match>
	 		<logic:match name="frmUserList" property="sUserList" value="INS">
		 		<td valign="bottom" nowrap>Healthcare Company:<br>
			  			<html:select property="sInsuranceCompany" styleClass="selectBox selectBoxMedium" >
						<html:option value="">Any</html:option>
						<html:optionsCollection name="listInsuranceCompany" label="cacheDesc" value="cacheId"/>
						</html:select>
			 	 </td>
		     	 <td valign="bottom" nowrap>Company Code:<br>
				 <html:text property="sOfficeCode" styleClass="textBox textBoxMedium" maxlength="30"/></td>
			</logic:match>

			<logic:match name="frmUserList" property="sUserList" value="AGN">
		 		<td valign="bottom" nowrap>Healthcare Company:<br>
			  			<html:select property="sInsuranceCompany" styleClass="selectBox selectBoxMedium" >
						<html:option value="">Any</html:option>
						<html:optionsCollection name="listInsuranceCompany" label="cacheDesc" value="cacheId"/>
						</html:select>
			 	 </td>
		     	 <td valign="bottom" nowrap>Company Code:<br>
				 <html:text property="sOfficeCode" styleClass="textBox textBoxMedium" maxlength="30"/></td>
			</logic:match>

	  		<logic:match name="frmUserList" property="sUserList" value="COR">
	         	<td nowrap>Group Name:<br>
	         	<html:text property="sGrpName" styleClass="textBox textBoxMedium" maxlength="60"/></td>
				<td nowrap>Group Id:<br>
		        <html:text property="sGrpID" styleClass="textBox textBoxMedium" maxlength="250"/></td>
			</logic:match>
         	<logic:match name="frmUserList" property="sublink" value="ADM">
		         <td valign="bottom" nowrap>
			         <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
		         </td>
		         <td valign="bottom" nowrap>&nbsp;</td>
		         <td>&nbsp;</td>
	        </logic:match>
      </tr>
    </table>
<!-- E N D : Search Box -->

<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="tableData"/>
<!-- E N D : Grid -->

<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>&nbsp;</td>
	     <logic:match name="frmUserList" property="sublink" value="ADM">
		   	<td width="73%" nowrap align="right">
			  	<%
		    		if(TTKCommon.isAuthorized(request,"Add"))
					{
			    %>
			    		<logic:match name="frmUserList" property="sUserList" value="TTK">
			    			<button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Add()"><u>A</u>dd</button>&nbsp;
			   			</logic:match>

			   			<logic:match name="frmUserList" property="sUserList" value="CAL">
			    			<button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Add()"><u>A</u>dd</button>&nbsp;
			   			</logic:match>

			   			<logic:match name="frmUserList" property="sUserList" value="DMC">
			    			<button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Add()"><u>A</u>dd</button>&nbsp;
			   			</logic:match>
			    <%
			       	}//end of if(TTKCommon.isAuthorized(request,"Add"))

			   		if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Edit"))
					{
			    %>
					    <logic:match name="frmUserList" property="sUserStatus" value="ACT">
				    			<button type="button" name="Button2" accesskey="i" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"
				    			onClick="javascript:onActive(
						    			'<bean:write name="frmUserList" property="sUserId" />','<bean:write name="frmUserList" property="sName" />',
										'<bean:write name="frmUserList" property="sHospitalName" />','<bean:write name="frmUserList" property="sEmpanelmentNO" />',
										'<bean:write name="frmUserList" property="sOfficeCode" />','<bean:write name="frmUserList" property="sGrpName" />',
										'<bean:write name="frmUserList" property="sGrpID" />'
				    			);" ><u>I</u>nactivate</button>&nbsp;
						</logic:match>
						<logic:match name="frmUserList" property="sUserStatus" value="INA">
				    		<button type="button" name="Button2" accesskey="t" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"
				    			onClick="javascript:onActive(
						    			'<bean:write name="frmUserList" property="sUserId" />','<bean:write name="frmUserList" property="sName" />',
										'<bean:write name="frmUserList" property="sHospitalName" />','<bean:write name="frmUserList" property="sEmpanelmentNO" />',
										'<bean:write name="frmUserList" property="sOfficeCode" />','<bean:write name="frmUserList" property="sGrpName" />',
										'<bean:write name="frmUserList" property="sGrpID" />'
				    			);" >Ac<u>t</u>ivate</button>
						</logic:match>
				<%
			       	}//end of if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Edit"))
			    %>
			</td>
		</logic:match>
		 <logic:match name="frmUserList" property="sublink" value="FIN">
		 	<td width="73%" nowrap align="right">
		 		<%
		    		if(TTKCommon.isAuthorized(request,"Add"))
					{
			    %>
		 			<button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="AddFinance()"><u>A</u>dd</button>&nbsp;
				 <%
			       	}//end of if(TTKCommon.isAuthorized(request,"Add"))

			   		if(TTKCommon.isDataFound(request,"tableData")&& TTKCommon.isAuthorized(request,"Delete"))
					{
			    %>
					<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:DeleteFinance()"><u>D</u>elete</button>&nbsp;
				<%
			       	}//end of if(TTKCommon.isDataFound(request,"tableData"))
			    %>
			</td>
		 </logic:match>
	  </tr>
		<ttk:PageLinks name="tableData"/>
	</table>
	</div>
<!-- E N D : Buttons and Page Counter -->
<!-- E N D : Main Container Table -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<input type="hidden" name="child" value="">
</html:form>