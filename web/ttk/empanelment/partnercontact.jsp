<%
/**
 * @ (#) partnercontact.jsp 24th Sep 2005
 * Project      : TTK HealthCare Services
 * File         : partnercontact.jsp
 * Author       : 
 * Company      : 
 * Date Created : 
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ page import="com.ttk.dto.empanelment.HospitalDetailVO" %>
<%@ page import="com.ttk.dto.empanelment.PartnerDetailVO" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.common.TTKPropertiesReader" %>


<%
	PartnerDetailVO partnerDetailVO 		= (PartnerDetailVO)request.getSession().getAttribute("PtnrDetails");
	UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	String strSubLink=TTKCommon.getActiveSubLink(request);
	String strStatus="";
	pageContext.setAttribute("designationPTR", Cache.getCacheObject("designationPTR"));
	pageContext.setAttribute("designationHOS", Cache.getCacheObject("designationHOS"));
	pageContext.setAttribute("designationINS", Cache.getCacheObject("designationINS"));
	pageContext.setAttribute("designationCOR", Cache.getCacheObject("designationCOR"));
	pageContext.setAttribute("designationBRO", Cache.getCacheObject("designationBRO"));
	pageContext.setAttribute("userStatus", Cache.getCacheObject("userStatus"));
	pageContext.setAttribute("designationBAK", Cache.getCacheObject("designationBAK"));
%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/empanelment/partnercontacts.js"></script>

<SCRIPT LANGUAGE="JavaScript">

var TC_Disabled = true;
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<html:form action="/PartnerContactAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td >List of <bean:write name="frmSearchContact" property="caption"/></td>
	    
	<%
		if(strSubLink.equals("Partner"))
		{
	%>
	     	<td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	<%
		}// end of if(strSubLink.equals("Partner"))
	%>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
    <logic:match name="frmSearchContact" property="userStatus" value="ACT">
    	<% strStatus="Inactivate";%>
    </logic:match>
	<logic:notMatch name="frmSearchContact" property="userStatus" value="ACT">
		<% strStatus="Activate";%>
	</logic:notMatch>
    <div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<%
		if(strSubLink.equals("Partner"))
		{
	%>

			<table align="center" class="tablePad"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="10%" nowrap class="textLabelBold">Switch to:</td>
        <td width="90%">
	        <html:select property="switchType" styleClass="specialDropDown" styleId="switchType" onchange="javascript:onSwitch()">
				<html:option value="PAT">PreAuth Contacts</html:option>
				<html:option value="CLM">Claim Contacts</html:option>
				<html:option value="ACC">Account Contacts</html:option>
			</html:select>
		</td>
		</tr>
<%-- 		<logic:notEmpty name="frmSearchContact" property="caption">
		<logic:match name="frmSearchContact" property="caption" value="Professional Contacts">
			<tr>
			<td colspan="2" align="left">&nbsp;&nbsp;&nbsp;&nbsp; <a href="#" onClick="javascript:onUploadProfessionals()"> Upload Clinicians</a>
			</td>
	      </tr>   
     	 </logic:match>
      </logic:notEmpty> --%>
    </table>
	<%
		}// end of if(strSubLink.equals("Partner"))
		if((strSubLink.equals("Insurance"))||(strSubLink.equals("Group Registration"))||(strSubLink.equals("Banks"))  ||(strSubLink.equals("Broker")))
		{
	%>

			<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td> Name:<br>
			      <html:text property="name" styleClass="textBox textBoxLarge" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
			    </td>
				<%
					if(strSubLink.equals("Insurance"))
					{
				%>
					    <td>Designation:<br>
					    	<html:select property="designationINS" styleClass="selectBox selectBoxMedium">
		  							<html:option value="">Any</html:option>
		        					<html:optionsCollection name="designationINS" label="cacheDesc" value="cacheId" />
           						</html:select>
					    </td>
				<%
					}
					else if(strSubLink.equals("Banks"))
					{
				%>
					    <td>Designation:<br>
					    	<html:select property="designationBAK" styleClass="selectBox selectBoxMedium">
		  							<html:option value="">Any</html:option>
		        					<html:optionsCollection name="designationBAK" label="cacheDesc" value="cacheId" />
           						</html:select>
					    </td>
				<%
					}else if(strSubLink.equals("Broker"))
					{
				%>
					    <td>Designation:<br>
					    	<html:select property="designationBRO" styleClass="selectBox selectBoxMedium">
		  							<html:option value="">Any</html:option>
		        					<html:optionsCollection name="designationBRO" label="cacheDesc" value="cacheId" />
           						</html:select>
					    </td>
				<%
					}
					else if(strSubLink.equals("Hospial"))
					{
				%>
					    <td>Designation:<br>
					    	<html:select property="designationHOS" styleClass="selectBox selectBoxMedium">
		  							<html:option value="">Any</html:option>
		        					<html:optionsCollection name="designationHOS" label="cacheDesc" value="cacheId" />
           						</html:select>
					    </td>
				<%
					}
					else if(strSubLink.equals("Partner"))
					{
				%>
				 <td>Designation:<br>
					    	<html:select property="designationPTR" styleClass="selectBox selectBoxMedium">
		  							<html:option value="">Any</html:option>
		        					<html:optionsCollection name="designationPTR" label="cacheDesc" value="cacheId" />
           						</html:select>
					    </td>
				
				<%
					}
					else
					{
				%>
						<td>Designation:<br>
					    	<html:select property="designationCOR" styleClass="selectBox selectBoxMedium">
		  							<html:option value="">Any</html:option>
		        					<html:optionsCollection name="designationCOR" label="cacheDesc" value="cacheId" />
           						</html:select>
					    </td>
				<%
					}
				%>
			    <td>Status:<br>
        			<html:select property="userStatus" styleClass="selectBox selectBoxMedium">
			            	<html:optionsCollection name="userStatus" label="cacheDesc" value="cacheId" />
	        		</html:select>
      			</td>
      			<%
      				if(!(strSubLink.equals("Banks")))
      				{
      			%>
		    			<td valign="bottom" nowrap>
		    			<input name="applUser" type="checkbox" value="Y" <logic:match name="frmSearchContact" property="applUser1" value="Y">checked</logic:match> >Application Users
		    			</td>
    			<%
					}
				%>
				<td valign="bottom" width="100%" nowrap>
					<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
				</td>
			  </tr>
			</table>
   	<%
   		}// end of if((strSubLink.equals("Insurance"))||(strSubLink.equals("Group Registration"))||(strSubLink.equals("Banks")))
   	%>
		<!-- E N D : Search Box -->
		<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="tableData"/>
		
		
		
		
 	<!-- E N D : Grid -->
	<!-- S T A R T :Buttons and  Page Counter -->
		<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	<%
		if(strSubLink.equals("Partner"))
		{
	%>
			  <tr>
			    <td width="27%"></td>
			    <td width="73%" align="right">
			    	<% if(TTKCommon.isAuthorized(request,"Add"))
	          			{
	          		%><logic:match name="frmSearchContact" property="buttonLogic" value="PAT">
	          				<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Add();"><u>A</u>dd</button>&nbsp;
	          		</logic:match>
	          		
	          		<logic:match name="frmSearchContact" property="buttonLogic" value="CLM">
	          				<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Add();"><u>A</u>dd</button>&nbsp;
	          		</logic:match>
	          		
	          		<logic:match name="frmSearchContact" property="buttonLogic" value="ACC">
	          				<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Add();"><u>A</u>dd</button>&nbsp;
	          		</logic:match>
	          		
	          		<%-- <logic:match value="PRO" name="frmSearchContact" property="buttonLogic" >
	          				<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:AddLicense();"><u>A</u>dd</button>&nbsp;
	          		</logic:match> --%>
	          			
	          		<%
	          			}// end of if(TTKCommon.isAuthorized(request,"Add"))
	          			if(TTKCommon.isAuthorized(request,"Delete"))
						{
		          			if(TTKCommon.isDataFound(request,"tableData"))
		          			{
	          		%>
	          					<logic:match name="frmSearchContact" property="userStatus" value="ACT">
								    <button type="button" name="chngid" accesskey="i" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onActivateInactivate('Inactivate');"><u>I</u>nactivate</button>&nbsp;
							    </logic:match>
							    <logic:match name="frmSearchContact" property="buttonLogic" value="PAT"><!-- this logic match added not to show activate for professional contacts -->
								    <logic:notMatch name="frmSearchContact" property="userStatus" value="ACT">
								    	<button type="button" name="chngid" accesskey="t" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onActivateInactivate('Activate');">Activa<u>t</u>e</button>&nbsp;
								    </logic:notMatch>
							    </logic:match>
							      <logic:match name="frmSearchContact" property="buttonLogic" value="CLM"><!-- this logic match added not to show activate for professional contacts -->
								    <logic:notMatch name="frmSearchContact" property="userStatus" value="ACT">
								    	<button type="button" name="chngid" accesskey="t" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onActivateInactivate('Activate');">Activa<u>t</u>e</button>&nbsp;
								    </logic:notMatch>
							    </logic:match>
							      <logic:match name="frmSearchContact" property="buttonLogic" value="ACC"><!-- this logic match added not to show activate for professional contacts -->
								    <logic:notMatch name="frmSearchContact" property="userStatus" value="ACT">
								    	<button type="button" name="chngid" accesskey="t" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onActivateInactivate('Activate');">Activa<u>t</u>e</button>&nbsp;
								    </logic:notMatch>
							    </logic:match>
	          		<%
	          				}// end of if(TTKCommon.isDataFound(request,"tableData"))
	          			}// end of if(TTKCommon.isAuthorized(request,"Delete"))
	          		%>
	          				<INPUT TYPE="hidden" NAME="activeInactive" value="">
	          				<input type="hidden" name="child" value="">
			    </td>
			  </tr>
	<%
		}// end of if(strSubLink.equals("Partner"))
		if(strSubLink.equals("Insurance")||(strSubLink.equals("Group Registration"))||(strSubLink.equals("Banks")) ||(strSubLink.equals("Broker")))
		{
	%>
			<tr>
	    	  <td width="27%"></td>
        	  <td width="73%" nowrap align="right">
        	  	<% if(TTKCommon.isAuthorized(request,"Add"))
	          	   {
	          	%>
        	  			<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Add();"><u>A</u>dd</button>&nbsp;
        	  	<%
        	  		}// end of if(TTKCommon.isAuthorized(request,"Add"))
        	  		if(TTKCommon.isAuthorized(request,"Delete"))
					{
	        	  		if(TTKCommon.isDataFound(request,"tableData"))
			          	{
		        %>
          					<logic:match name="frmSearchContact" property="userStatus" value="ACT">
							    <button type="button" name="chngid" accesskey="i" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onActivateInactivate('Inactivate');"><u>I</u>nactivate</button>&nbsp;
						    </logic:match>
						    <logic:notMatch name="frmSearchContact" property="userStatus" value="ACT">
						    	<button type="button" name="chngid" accesskey="t" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onActivateInactivate('Activate');">Activa<u>t</u>e</button>&nbsp;
						    </logic:notMatch>
          		<%
          				}// end of if(TTKCommon.isDataFound(request,"tableData"))
          			}// end of if(TTKCommon.isAuthorized(request,"Delete"))
          			if(strSubLink.equals("Insurance"))
        	  		{
        	  	%>
        	  			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
        	  	<%
        	  		}
          			else if(strSubLink.equals("Broker"))
        	  		{
        	  	%>
        	  			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBrokerClose();"><u>C</u>lose</button>
        	  	<%
        	  		}

        	  		else if(strSubLink.equals("Group Registration"))
        	  		{
        	  	%>
        	  			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onContactClose();"><u>C</u>lose</button>
        	  	<%
        	  		}
        	 		else if(strSubLink.equals("Banks"))
        	  		{
        	  	%>
        	  			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBankContactClose();"><u>C</u>lose</button>
        	  	<%
        	  		}
        	  	%>
          		<INPUT TYPE="hidden" NAME="activeInactive" value="">
          		<input type="hidden" name="child" value="Contacts">
        	</td>
      	   </tr>
   <%
      	}// end of if(strSubLink.equals("Insurance")||(strSubLink.equals("Group Registration")))
   %>
  		<ttk:PageLinks name="tableData"/>
	</table>
	
	
	
	</div>
	<!-- E N D : Buttons and Page Counter -->
	
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<input type="hidden" name="tab" value="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	
	
	<INPUT TYPE="hidden" NAME="sortIdLicense" VALUE="">
	<INPUT TYPE="hidden" NAME="pageIdLicense" VALUE="">
	<%
	if(strSubLink.equals("Partner"))
	{ %>
		<input type="hidden" name="ptnrEmpnlNo" value="<%=partnerDetailVO.getEmplNumber() %>"/>
		<input type="hidden" name="ptnrSeqId" value="<%=partnerDetailVO.getPtnrSeqId() %>"/>
		<input type="hidden" name="userSeqId" value="<%=userSecurityProfile.getUSER_SEQ_ID() %>"/>
		<input type="hidden" name="userName" value="<%=userSecurityProfile.getUserName() %>"/>
		<input type="hidden" name="ProfessionalSoftCopyURL" value="<%=TTKPropertiesReader.getPropertyValue("ProfessionalSoftCopyURL") %>"/>
	<%} %>	
			
	<INPUT TYPE="hidden" NAME="applUser1" value="<bean:write name="frmSearchContact" property="applUser1"/>">
	</html:form>
	<!-- E N D : Content/Form Area -->
