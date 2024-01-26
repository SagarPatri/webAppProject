<%
/** @ (#) premiuminfo.jsp Feb 9, 2006
 * Project     : TTK Healthcare Services
 * File        : premiuminfo.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: Feb 9, 2006
 *
 * @author 		 : Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/enrollment/premiuminfo.js"></script>

	<!-- S T A R T : Content/Form Area -->
	<html:form action="/PremiumInfoAction.do">
	<!-- S T A R T : Page Title -->
	<logic:match name="frmPolicyList" property="switchType" value="ENM">
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	<logic:match name="frmPolicyList" property="switchType" value="END">
	<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
		<tr>
	    <td >Premium Information - <bean:write name="frmPremiumInfo" property="caption"/> </td>
	    </tr>
	</table>
	<!-- E N D : Page Title -->
   	<div class="contentArea" id="contentArea">
   	<html:errors/>
   	<!-- S T A R T : Success Box -->
	 <logic:notEmpty name="updated" scope="request">
	  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="updated" scope="request"/>
	     </td>
	   </tr>
	  </table>
	 </logic:notEmpty>
 	<!-- E N D : Success Box -->

   	<!-- S T A R T : Form Fields -->
	<fieldset>
	<legend>Premium Information</legend>
	<logic:notEmpty name="frmPremiumInfo" property="premiumInfo">
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="17%" align="left" nowrap class="formLabel">Policy Sub Type:</td>
		    <td width="83%" align="left" nowrap class="textLabelBold">
		    	<bean:write name="frmPremiumInfo" property="policySubTypeDesc"/>
		    </td>
		  </tr>
		  <tr><td height="10" colspan="2" align="left" nowrap class="formLabel"></td></tr>
		</table>
	</logic:notEmpty>
	<table align="center" class="gridWithCheckBox zeroMargin" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" nowrap class="gridHeader">Member Name</td>
        <td width="20%" nowrap class="gridHeader">Type</td>
        <td width="17%" nowrap class="gridHeader">Sum Insured </td>
        <td width="17%" nowrap class="gridHeader">Gross Premium </td>
        <td width="16%" nowrap class="gridHeader">Pro-rata Premium </td>
        <td width="16%" nowrap class="gridHeader">Pro-rata Net Premium </td>
      </tr>
      <logic:empty name="frmPremiumInfo" property="premiumInfo">
		<tr>
			<td class="generalcontent" colspan="5">&nbsp;&nbsp;No Data Found</td>
		</tr>
      </logic:empty>
      <logic:notEmpty name="frmPremiumInfo" property="premiumInfo">
      <%
      	int i=0;
      	String strGridStyle="";
      %>
	      <logic:iterate id="premiumInfo" name="frmPremiumInfo" property="premiumInfo" >
	      <html:hidden name="premiumInfo" property="memberSeqID" />
	      <html:hidden name="premiumInfo" property="cancelYN"/>
	      <%
	      	if(i%2==0)
				strGridStyle="gridOddRow";
			else
				strGridStyle="gridEvenRow";
		  %>
	      <tr class="<%=strGridStyle%>">
	      	<td><bean:write name="premiumInfo" property="name"/>
	      		<html:hidden name="premiumInfo" property="name"/>
	      		<logic:match name="premiumInfo" property="cancelYN" value="Y">
	      			<img src="/ttk/images/CancelledIcon.gif" alt="Cancelled" title="Cancelled" width="16" height="16" border="0" align="absmiddle">
	      		</logic:match>
	      	</td>

	      	<!-- for Policy Sub-Type Floater -->
	      	<logic:match name="frmPremiumInfo" property="policySubTypeId" value="PFL">
	      	<%
	      		pageContext.setAttribute("listPolicyType",Cache.getCacheObject("floater"));
	      	%>
	      		<td>
	      			<logic:notMatch name="premiumInfo" property="cancelYN" value="Y">
	      			<%String strMPT="memberPolicyType"+i; %>
		      			<html:select name="premiumInfo" property="memberPolicyTypeID" styleId="<%=strMPT%>" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
							<html:options collection="listPolicyType" property="cacheId" labelProperty="cacheDesc"/>
						</html:select>
					</logic:notMatch>
					<logic:match name="premiumInfo" property="cancelYN" value="Y">
					<%String strMPT="memberPolicyType"+i; %>
						<html:select name="premiumInfo" property="memberPolicyTypeID" styleId="<%=strMPT%>" styleClass="selectBox selectBoxMedium" disabled="true">
							<html:options collection="listPolicyType" property="cacheId" labelProperty="cacheDesc"/>
						</html:select>
						<html:hidden name="premiumInfo" property="memberPolicyTypeID"/>
					</logic:match>
				</td>
		      	<td>
		      <input type="text" name="sumInsured"  Class="textBoxDisabled textBoxSmall" disabled  maxlength="13">&nbsp;&nbsp;
		      		<html:hidden name="premiumInfo" property="totalSumInsured"/>
		      	</td>
		      	<td>
		      	 	<input type="text" name="grossPremium"  Class="textBoxDisabled textBoxSmall" readonly="readonly"  maxlength="13" value="<bean:write name="premiumInfo" property="grossPremium"/>">&nbsp;&nbsp;
		      	<html:hidden name="premiumInfo" property="grossPremium"/> 
		      	</td>
		      	
		      	
		      	
		      	
		      	
		      	
		      	
		    
		      	
		      	<logic:notEqual value="1" name="frmPremiumInfo" property="capitationYN" > 
				<td>
					<input type="text" name="proRataPremium" class="textBoxDisabled textBoxSmall" readonly="readonly" onblur="isPositiveNumber(this,'proRataPremium')" maxlength="13" value="<bean:write name="premiumInfo" property="proRataPremium"/>">
					<html:hidden name="premiumInfo" property="proRataPremium"/> 
				</td>
                 </logic:notEqual>
                
                
                <logic:equal value="1" name="frmPremiumInfo" property="capitationYN" >
                
				<td>
					<input type="text" name="proRataPremium" class="textBoxDisabled textBoxSmall" readonly="readonly"  onblur="isPositiveNumber(this,'proRataPremium')" maxlength="13" value="<bean:write name="premiumInfo" property="proRataPremium"/>">
					<html:hidden name="premiumInfo" property="proRataPremium"/> 
				
			</td>
                </logic:equal>
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
				<td>
				<input type="text" name="netPremium" readonly="readonly" Class="textBoxDisabled textBoxSmall" disabled maxlength="13" value="<bean:write name="premiumInfo" property="netPremium"/>">
					 <html:hidden name="premiumInfo" property="netPremium"/>
					<%-- <input type="text" name="premiumAmount" Class="textBoxDisabled textBoxSmall" disabled maxlength="13">
					<html:hidden name="premiumInfo" property="premiumPaid"/> --%>
				</td>
	      	</logic:match>

	      	<!-- for Policy Sub-Type Non-Floater -->
	      	<logic:match name="frmPremiumInfo" property="policySubTypeId" value="PNF">
	      	<%
	      		pageContext.setAttribute("listPolicyType",Cache.getCacheObject("nonFloater"));
	      	%>
		      	<td>
		      		<logic:notMatch name="premiumInfo" property="cancelYN" value="Y">
		      			<%String strMPT="memberPolicyType"+i; %>
			      		<html:select name="premiumInfo" property="memberPolicyTypeID" styleClass="selectBox selectBoxMedium" styleId="<%=strMPT%>" disabled="<%= viewmode %>">
							<html:options collection="listPolicyType" property="cacheId" labelProperty="cacheDesc"/>
						</html:select>
					</logic:notMatch>
					<logic:match name="premiumInfo" property="cancelYN" value="Y">
					<%String strMPT="memberPolicyType"+i; %>
						<html:select name="premiumInfo" property="memberPolicyTypeID" styleClass="selectBox selectBoxMedium" styleId="<%=strMPT%>" disabled="true">
							<html:options collection="listPolicyType" property="cacheId" labelProperty="cacheDesc"/>
						</html:select>
						<html:hidden name="premiumInfo" property="memberPolicyTypeID"/>
					</logic:match>
				</td>
		      	<td><input type="text" name="sumInsured" class="textBox textBoxSmall textBoxDisabled"  disabled="true" value="<bean:write name="premiumInfo" property="totalSumInsured"/>">&nbsp;&nbsp;
		      		<logic:match name="viewmode" value="false">
			      		<logic:notMatch name="premiumInfo" property="cancelYN" value="Y">
				      		<a href="#" onClick="javascript:onCalcSumInsured(<%=i %>)"><img src="/ttk/images/RatesIcon.gif" alt="Sum Insured" title="Sum Insured" width="16" height="16" border="0" align="absmiddle"></a>
				      	</logic:notMatch>
			      	</logic:match>
		      		<html:hidden name="premiumInfo" property="totalSumInsured" />
		      	</td>
		      	<td>
		      		
		      		<input type="text" name="grossPremium" class="textBoxDisabled textBoxSmall"  readonly="readonly" maxlength="13" value="<bean:write name="premiumInfo" property="grossPremium"/>">
					<%-- <html:hidden name="premiumInfo" property="grossPremium"/> --%>
		      	</td>
		      	
		      	
		      	<logic:notEqual name="frmPremiumInfo" property="capitationYN" value="1">
		      	
		     
		      	<td>
		      		
		      		<input type="text" name="proRataPremium" class="textBoxDisabled textBoxSmall" readonly="readonly" onblur="isPositiveNumber(this,'proRataPremium')"  maxlength="13" value="<bean:write name="premiumInfo" property="proRataPremium"/>">
					
		      	</td>
		      
		      	
		      	  </logic:notEqual>
		      	
		      	 
		      	 <logic:equal name="frmPremiumInfo" property="capitationYN"  value="1">
		      	 <td>
		      		
		      		<input type="text" name="proRataPremium" class="textBoxDisabled textBoxSmall" readonly="readonly" onblur="isPositiveNumber(this,'proRataPremium')"  maxlength="13" value="<bean:write name="premiumInfo" property="proRataPremium"/>">
					
		      	</td>
		      	 
		      	 </logic:equal>
		      	 
		      	 
		      	 
		      	 
		      	 
		      	 
		      	 
		      	 
		      	 
		      	 
		      	 
		      	<td>
		      		<logic:notMatch name="premiumInfo" property="cancelYN" value="Y">
		      		<html:text name="premiumInfo" readonly="true" property="netPremium" styleClass="textBoxDisabled textBoxSmall" maxlength="13" disabled="<%= viewmode %>"/>
			      		<%-- <html:text name="premiumInfo" property="premiumPaid" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%= viewmode %>"/> --%>
			      	</logic:notMatch>
			      	<logic:match name="premiumInfo" property="cancelYN" value="Y">
			      	<html:text name="premiumInfo"  property="netPremium" styleClass="textBoxDisabled textBoxSmall" maxlength="13" readonly="true" disabled="<%= viewmode %>"/>
			      		<%-- <html:text name="premiumInfo" property="premiumPaid" styleClass="textBoxDisabled textBoxSmall" maxlength="13" readonly="true" disabled="<%= viewmode %>"/> --%>
			      	</logic:match>
		      	</td>
            </logic:match>

            <!-- for Policy Sub-Type Floater+Non-Floater -->
            <logic:match name="frmPremiumInfo" property="policySubTypeId" value="PFN">
	      	<%
	      		pageContext.setAttribute("listPolicyType",Cache.getCacheObject("floaterNonFloater"));
	      	%>
		      	<td>
		      		<logic:notMatch name="premiumInfo" property="cancelYN" value="Y">
		      		<%String strMPT="memberPolicyType"+i; %>
			      		<html:select name="premiumInfo" property="memberPolicyTypeID" styleClass="selectBox selectBoxMedium" styleId="<%=strMPT%>" onchange="<%="onFloatNonFloatChange(this,"+ i +")"%>" disabled="<%= viewmode %>">
							<html:options collection="listPolicyType" property="cacheId" labelProperty="cacheDesc"/>
						</html:select>
					</logic:notMatch>
		      		<logic:match name="premiumInfo" property="cancelYN" value="Y">
		      		<%String strMPT="memberPolicyType"+i; %>
			      		<html:select name="premiumInfo" property="memberPolicyTypeID" styleClass="selectBox selectBoxMedium" styleId="<%=strMPT%>" disabled="true">
							<html:options collection="listPolicyType" property="cacheId" labelProperty="cacheDesc"/>
						</html:select>
						<html:hidden name="premiumInfo" property="memberPolicyTypeID"/>
					</logic:match>
				</td>
		      	<td>
		      		<input type="text" name="sumInsured" class="textBox textBoxSmall textBoxDisabled"  disabled="true" maxlength="13" value="<bean:write name="premiumInfo" property="totalSumInsured"/>">&nbsp;&nbsp;
		      		<logic:match name="viewmode" value="false">
			      		<logic:notMatch name="premiumInfo" property="cancelYN" value="Y">
			      			<a href="#" onClick="javascript:onCalcSumInsured(<%=i %>)"><img src="/ttk/images/RatesIcon.gif" alt="Sum Insured" title="Sum Insured" width="16" height="16" border="0" align="absmiddle" id="<%="premiumimg"+i%>"></a>
			      		</logic:notMatch>
			      	</logic:match>
		      		<html:hidden name="premiumInfo" property="totalSumInsured" />
		      	</td>
		      	<td>
		      	 	<input type="text" name="grossPremium"  Class="textBoxDisabled textBoxSmall" readonly="readonly"  maxlength="13" value="<bean:write name="premiumInfo" property="grossPremium"/>">&nbsp;&nbsp;
		      		<%-- <html:hidden name="premiumInfo" property="grossPremium"/> --%>
		      	</td>
		      	<td>
		      	<input type="text" name="proRataPremium" onblur="isPositiveNumber(this,'proRataPremium')" class="textBoxDisabled textBoxSmall" maxlength="13" value="<bean:write name="premiumInfo" property="proRataPremium"/>">
		      		<%-- <html:hidden name="premiumInfo" property="proRataPremium" /> --%>
		      		<%-- <input type="text" name="calculatedPremium" class="textBoxDisabled textBoxSmall" disabled maxlength="13" value="<bean:write name="premiumInfo" property="calcPremium"/>">
		      		<html:hidden name="premiumInfo" property="calcPremium" /> --%>
		      	</td>
		      	<td>
			      	<logic:match name="premiumInfo" property="memberPolicyTypeID" value="PFL">
			      		<%-- <html:text name="premiumInfo" property="premiumPaid" readonly="true" disabled="<%= viewmode %>" styleClass="textBoxDisabled textBoxSmall" maxlength="13" /> --%>
			      		<input type="text" name="netPremium" readonly="readonly" Class="textBoxDisabled textBoxSmall" disabled  maxlength="13" value="<bean:write name="premiumInfo" property="netPremium"/>">&nbsp;&nbsp;
			      		<%-- <html:text name="premiumInfo" property="netPremium" readonly="true" disabled="<%= viewmode %>" styleClass="textBoxDisabled textBoxSmall" maxlength="13" value="<bean:write name="premiumInfo" property="netPremium"/>" /> --%>
			      	</logic:match>
			      	<logic:notMatch name="premiumInfo" property="memberPolicyTypeID" value="PFL">
			      		<logic:notMatch name="premiumInfo" property="cancelYN" value="Y">
			      			<%-- <html:text name="premiumInfo" property="premiumPaid" styleClass="textBox textBoxSmall"  maxlength="13" disabled="<%= viewmode %>"/> --%>
	                    <%--   <html:text name="premiumInfo" property="netPremium" readonly="true" disabled="<%= viewmode %>" styleClass="textBoxDisabled textBoxSmall" maxlength="13" value="<bean:write name="premiumInfo" property="netPremium"/>" /> --%>
			      			<input type="text" name="netPremium" readonly="readonly"  Class="textBoxDisabled textBoxSmall" disabled  maxlength="13" value="<bean:write name="premiumInfo" property="netPremium"/>">&nbsp;&nbsp;
			      		</logic:notMatch>
			      		<logic:match name="premiumInfo" property="cancelYN" value="Y">
			      			<%-- <html:text name="premiumInfo" property="premiumPaid" styleClass="textBoxDisabled textBoxSmall"  readonly="true" maxlength="13" disabled="<%= viewmode %>"/> --%>
			 		      		<%-- <html:text name="premiumInfo" property="netPremium" readonly="true" disabled="<%= viewmode %>" styleClass="textBoxDisabled textBoxSmall" maxlength="13" value="<bean:write name="premiumInfo" property="netPremium"/>"/>     		 --%>
			 		      		<input type="text" name="netPremium" readonly="readonly"  Class="textBoxDisabled textBoxSmall" disabled  maxlength="13" value="<bean:write name="premiumInfo" property="netPremium"/>">&nbsp;&nbsp;	
			      		</logic:match>
			      	</logic:notMatch>
		      	</td>
            </logic:match>

            <!-- for Policy Sub-Type Floater+FloaterWithRestriction  -->
            <logic:match name="frmPremiumInfo" property="policySubTypeId" value="PFR">
	      	<%
	      		pageContext.setAttribute("listPolicyType",Cache.getCacheObject("floaterRestrict"));
	      	%>
		      	<td>
		      		<logic:notMatch name="premiumInfo" property= "cancelYN" value="Y">
		      		<%String strMPT="memberPolicyType"+i; %>
			      		<html:select name="premiumInfo" property="memberPolicyTypeID" styleClass="selectBox selectBoxMedium" styleId="<%=strMPT%>" onchange="<%="onFloatRestrictChange(this,"+ i +")"%>" disabled="<%= viewmode %>">
							<html:options collection="listPolicyType" property="cacheId" labelProperty="cacheDesc"/>
						</html:select>
					</logic:notMatch>
		      		<logic:match name="premiumInfo" property="cancelYN" value="Y">
		      		<%String strMPT="memberPolicyType"+i; %>
						<html:select name="premiumInfo" property="memberPolicyTypeID" styleClass="selectBox selectBoxMedium" styleId="<%=strMPT%>" disabled="true">
							<html:options collection="listPolicyType" property="cacheId" labelProperty="cacheDesc"/>
						</html:select>
						<html:hidden name="premiumInfo" property="memberPolicyTypeID"/>
					</logic:match>
				</td>
		      	<td>
		      		<input type="text" name="sumInsured" class="textBox textBoxSmall textBoxDisabled"  disabled="true" value="<bean:write name="premiumInfo" property="totalSumInsured"/>">&nbsp;&nbsp;
		      		<logic:match name="viewmode" value="false">
			      		<logic:notMatch name="premiumInfo" property="cancelYN" value="Y">
			      			<a href="#" onClick="javascript:onCalcSumInsured(<%=i %>)"><img src="/ttk/images/RatesIcon.gif" alt="Sum Insured" title="Sum Insured" width="16" height="16" border="0" align="absmiddle" id="<%="premiumimg"+i%>"></a>
			      		</logic:notMatch>
		      		</logic:match>
		      		<html:hidden name="premiumInfo" property="totalSumInsured" />
		      	</td>
		      	<td>
		      	<input type="text" name="grossPremium"  Class="textBoxDisabled textBoxSmall" readonly="readonly"  maxlength="13" value="<bean:write name="premiumInfo" property="grossPremium"/>">&nbsp;&nbsp;
		      	<%-- 	<html:hidden name="premiumInfo" property="grossPremium"/> --%>
		      	</td>
		      	<td>
		      		<%-- <input type="text" name="calculatedPremium" class="textBoxDisabled textBoxSmall" disabled maxlength="13" value="<bean:write name="premiumInfo" property="calcPremium"/>">
		      		<html:hidden name="premiumInfo" property="calcPremium" /> --%>
		      		<input type="text" name="proRataPremium" class="textBoxDisabled textBoxSmall" onblur="isPositiveNumber(this,'proRataPremium')" disabled maxlength="13" value="<bean:write name="premiumInfo" property="proRataPremium"/>">
		      		<%-- <html:hidden name="premiumInfo" property="proRataPremium" /> --%>
		      	</td>
		      	<td>
			      	<logic:match name="premiumInfo" property="memberPolicyTypeID" value="PFL">
			      		<%-- <html:text name="premiumInfo" property="premiumPaid" readonly="true" styleClass="textBoxDisabled textBoxSmall"  maxlength="13" disabled="<%= viewmode %>"/> --%>
			      		<%-- <html:text name="premiumInfo" property="netPremium" readonly="true" styleClass="textBoxDisabled textBoxSmall"  maxlength="13" disabled="<%= viewmode %>" value="<bean:write name="premiumInfo" property="netPremium"/>"/> --%>
			      		<input type="text" name="netPremium" readonly="readonly"  Class="textBoxDisabled textBoxSmall" disabled  maxlength="13" value="<bean:write name="premiumInfo" property="netPremium"/>">&nbsp;&nbsp;
			      	</logic:match>
			      	<logic:notMatch name="premiumInfo" property="memberPolicyTypeID" value="PFL">
			      		<logic:notMatch name="premiumInfo" property="cancelYN" value="Y">
			      		<%-- 	<html:text name="premiumInfo" property="premiumPaid" styleClass="textBox textBoxSmall"  maxlength="13" disabled="<%= viewmode %>"/> --%>
			      		<%-- <html:text name="premiumInfo" property="netPremium" readonly="true" styleClass="textBoxDisabled textBoxSmall"  maxlength="13" disabled="<%= viewmode %>" value="<bean:write name="premiumInfo" property="netPremium"/>"/> --%>
			      		<input type="text" name="netPremium" readonly="readonly"  Class="textBoxDisabled textBoxSmall" disabled  maxlength="13" value="<bean:write name="premiumInfo" property="netPremium"/>">&nbsp;&nbsp;
			      		</logic:notMatch>
			      		<logic:match name="premiumInfo" property="cancelYN" value="Y">
			      			<%-- <html:text name="premiumInfo" property="premiumPaid" styleClass="textBoxDisabled textBoxSmall"  readonly="true" maxlength="13" disabled="<%= viewmode %>"/> --%>
			      			<%-- <html:text name="premiumInfo" property="netPremium" styleClass="textBoxDisabled textBoxSmall"  readonly="true" maxlength="13" disabled="<%= viewmode %>" value="<bean:write name="premiumInfo" property="netPremium"/>"/> --%>
			      			<input type="text" name="netPremium" readonly="readonly" Class="textBoxDisabled textBoxSmall" disabled  maxlength="13" value="<bean:write name="premiumInfo" property="netPremium"/>">&nbsp;&nbsp;
			      		</logic:match>
			      	</logic:notMatch>
		      	</td>
            </logic:match>

		    </tr>
		    <%
		    	i++;	// increment the record counter
		    %>
	      </logic:iterate>
      </logic:notEmpty>
    </table>
    <table align="center" class="gridTotal" border="0" cellspacing="0" cellpadding="0">
	    <logic:match name="frmPremiumInfo" property="policySubTypeId" value="PFL">
	      <tr>
	        <td width="44%" align="right" class="formLabel" style="padding-right:5px;">Floater Sum Insured :</td>
	        <td width="38%" class="formLabel">
	        	<input type="text" name="floaterSum" Class="textBoxDisabled textBoxSmall" disabled style="margin-left:-1px; " maxlength="13" value="<bean:write name="frmPremiumInfo" property="floaterSumInsured"/>">&nbsp;&nbsp;
	        	<html:hidden name="frmPremiumInfo" property="floaterSumInsured" />
	        	<logic:match name="viewmode" value="false">
		        	<a href="#" onClick="javascript:onCalcFloaterSumInsured()"><img src="/ttk/images/RatesIcon.gif" alt="Sum Insured" title="Sum Insured" width="16" height="16" border="0" align="absmiddle"></a>
		        </logic:match>
	            <div align="right" style="margin-top:-16px; ">Floater Premium :</div>
	        </td>
	        <td width="18%"><html:text property="floaterPremium" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%= viewmode %>"/></td>
	      </tr>
	    </logic:match>
	    <logic:match name="frmPremiumInfo" property="policySubTypeId" value="PNF">
		  <tr>
	        <td width="44%" align="right" class="formLabel" style="padding-right:5px;">Tot. Family Sum Insured :</td>
	        <td width="38%" class="formLabel">
	        	<input type="text" name="familySum" Class="textBoxDisabled textBoxSmall" style="margin-left:-1px;" maxlength="13" disabled value="<bean:write name="frmPremiumInfo" property="totalFlySumInsured"/>">
	        	<html:hidden name="frmPremiumInfo" property="totalFlySumInsured" />
	        	<div align="right" style="margin-top:-16px; ">Tot. Family Premium :</div>
	        </td>
	        <td width="18%"><html:text property="totalFlyPremium" styleClass="textBox textBoxSmall" maxlength="13" onfocus="this.select();" disabled="<%= viewmode %>"/></td>
	      </tr>
	    </logic:match>
	    <logic:match name="frmPremiumInfo" property="policySubTypeId" value="PFN">
		    <tr>
		        <td width="20%" align="right" class="formLabel" style="padding-right:5px;">&nbsp;</td>
		        <td width="20%" align="right" class="formLabel" style="padding-right:5px;">Floater Sum Insured :</td>
		        <td width="16%" class="formLabel">
		        	<input type="text" name="floaterSum" Class="textBoxDisabled textBoxSmall" disabled style="margin-left:-1px; " maxlength="13" value="<bean:write name="frmPremiumInfo" property="floaterSumInsured"/>">&nbsp;&nbsp;
	        		<html:hidden name="frmPremiumInfo" property="floaterSumInsured" />
		        	<a href="#" onClick="javascript:onCalcFloaterSumInsured()"><img src="/ttk/images/RatesIcon.gif" alt="Sum Insured" title="Sum Insured" width="16" height="16" border="0" align="absmiddle"></a>
		        </td>
		        <td width="18%" align="right" class="formLabel">Floater Premium :</td>
		        <td width="16%"><html:text property="floaterPremium" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%= viewmode %>"/></td>
		    </tr>
		    <tr>
		        <td align="right" class="formLabel" style="padding-right:5px;">&nbsp;</td>
		        <td align="right" class="formLabel" style="padding-right:5px;">Tot. Fly. Sum Insured :</td>
		        <td class="formLabel">
		        	<input type="text" name="familySum" Class="textBoxDisabled textBoxSmall" style="margin-left:-1px;" maxlength="13" disabled value="<bean:write name="frmPremiumInfo" property="totalFlySumInsured"/>">
		        	<html:hidden name="frmPremiumInfo" property="totalFlySumInsured" />
		        </td>
		        <td align="right" class="formLabel">Tot. Fly. Premium :</td>
		        <td><html:text property="totalFlyPremium" styleClass="textBox textBoxSmall" maxlength="13" onfocus="this.select();" disabled="<%= viewmode %>"/></td>
		    </tr>
	    </logic:match>
	    <logic:match name="frmPremiumInfo" property="policySubTypeId" value="PFR">
		    <tr>
		        <td width="20%" align="right" class="formLabel" style="padding-right:5px;">&nbsp;</td>
		        <td width="20%" align="right" class="formLabel" style="padding-right:5px;">Floater Sum Insured :</td>
		        <td width="16%" class="formLabel">
		        	<input type="text" name="floaterSum" Class="textBoxDisabled textBoxSmall" disabled style="margin-left:-1px; " maxlength="13" value="<bean:write name="frmPremiumInfo" property="floaterSumInsured"/>">&nbsp;&nbsp;
		        	<html:hidden name="frmPremiumInfo" property="floaterSumInsured" />
		        	<a href="#" onClick="javascript:onCalcFloaterSumInsured()"><img src="/ttk/images/RatesIcon.gif" alt="Sum Insured" title="Sum Insured" width="16" height="16" border="0" align="absmiddle"></a>
		        </td>
		        <td width="18%" align="right" class="formLabel">Floater Premium :</td>
		        <td width="16%"><html:text property="floaterPremium" styleClass="textBox textBoxSmall" maxlength="13"  disabled="<%= viewmode %>"/></td>
		    </tr>
		    <tr>
		        <td align="right" class="formLabel" style="padding-right:5px;">&nbsp;</td>
		        <td align="right" class="formLabel" style="padding-right:5px;">Tot. Fly. Sum Insured :</td>
		        <td class="formLabel">
		        	<input type="text" name="familySum" Class="textBoxDisabled textBoxSmall" style="margin-left:-1px;" maxlength="13" disabled value="<bean:write name="frmPremiumInfo" property="totalFlySumInsured"/>">
		        	<html:hidden name="frmPremiumInfo" property="totalFlySumInsured" />
		        </td>
		        <td align="right" class="formLabel">Tot. Fly. Premium :</td>
		        <td><html:text property="totalFlyPremium" styleClass="textBox textBoxSmall" onfocus="this.select();" maxlength="13" disabled="<%= viewmode %>"/></td>
		    </tr>
	    </logic:match>
    </table>
      <!--  Added as per KOC 1284 Change Request -->
     <table border="0" cellspacing="0" cellpadding="0" >
      	   <!--  Added as per KOC 1284 Change Request -->		
			   <tr>
			   <td width="33%" align="right" class="formLabel" style="padding-right:5px;">&nbsp;</td>
			   <td width="33%" align="right" class="formLabel" style="padding-right:5px;">&nbsp;</td>
			   <%--  <td width="17%" align="right" class="formLabel" style="padding-right:5px;">&nbsp;Premium  Region : </td>
			    <td width="17%" class="formLabel"><html:select property="selectregion" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
								            <html:option value="">Select from list</html:option>
											<html:option value="R1">Region 1</html:option>
											<html:option value="R2">Region 2</html:option>
				                            <html:option value="R3">Region 3</html:option>
				                            <html:option value="R4">Region 4</html:option>
											</html:select>
				</td> --%>
				
				</tr>
		  
    </table>
      <!--  Added as per KOC 1284 Change Request -->
    </fieldset>
    <!-- S T A R T : Buttons -->
	<logic:notEmpty name="frmPremiumInfo" property="premiumInfo">
		<table align="center" class="buttonsContainer zeroMargin"  border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="100%" align="right" valign="bottom"><html:checkbox property="clearPremiumInfoYN" value="Y" disabled="<%= viewmode %>">&nbsp;<span class="formLabelBold">Delete all Amount</span>&nbsp;</html:checkbox></td>
		  </tr>
		</table>
	</logic:notEmpty>
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    <logic:notEmpty name="frmPremiumInfo" property="premiumInfo">
		    <%
			    if(TTKCommon.isAuthorized(request,"Edit"))
				{
			%>
					<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
					<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
			<%
				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
			%>
	    </logic:notEmpty>
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>&nbsp;
	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons -->
	<!-- E N D : Form Fields -->
	<html:hidden property="policySubTypeId"/>
	<input type="hidden" name="mode" value="PremiumInfo">
	<html:hidden name="frmPremiumInfo" property="activeLink"/>
	<input type="hidden" name="child" value="Premium Information">
	<html:hidden name="frmPremiumInfo" property="selectedmemberSeqID"/>
	<input type="hidden" name="selectedmemberName">
	<html:hidden name="frmPremiumInfo" property="selectregionYN"/>	<!-- Added as per KOPC 1284 Change request -->
	<input type="hidden" name="clearPremiumInfoYN" value="">
	<logic:notEmpty name="frmPremiumInfo" property="premiumInfo">
		<script language="javascript">
			onDocumentLoad();
		</script>
	</logic:notEmpty>
	<logic:notEmpty name="frmPremiumInfo" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
	</html:form>

	<!-- E N D : Content/Form Area -->
