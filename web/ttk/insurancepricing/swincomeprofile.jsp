<!-- BIKASH KUMAR -->

<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.struts.action.DynaActionForm"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

    <script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
    <script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="/ttk/scripts/insurancepricing/swpricinghome.js"></script>   
     <script type="text/javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
     <script language="javascript" src="/ttk/scripts/utils.js"></script>
     <SCRIPT LANGUAGE="JavaScript">
    bAction = false; //to avoid change in web board in product list screen //to clarify
    var TC_Disabled = true;
   
   
</SCRIPT>

<!-- S T A R T : Content/Form Area -->

	<html:form action="/SwInsPricingActionIncome.do" method="post"   enctype="multipart/form-data" >

<div class="contentArea" id="contentArea">
    
 <!-- S T A R T : Page Title -->
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
	    		<td><bean:write name="frmSwIncomeProfile" property="caption"/></td>
			    <td width="43%" align="right" class="webBoard">&nbsp;</td>
		  </tr>
	</table>
		<logic:notEmpty name="frmSwIncomeProfile"	property="pricingNumberAlert">
				<table align="center" class="errorContainer" border="0"	cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Warning"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmSwIncomeProfile" property="pricingNumberAlert" /></td>
					</tr>
				</table>
			</logic:notEmpty>
	<logic:notEmpty name="updated" scope="request">
  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
         <bean:message name="updated" scope="request"/>
     </td>
   </tr>
  </table>
 </logic:notEmpty>
 
 <logic:notEmpty name="successMsg" scope="request">
				<table align="center" class="successContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="successMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
 
 <logic:notEmpty name="errorMsg" scope="request">
				<table align="center" class="errorContainer" border="0"	cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Warning"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="errorMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
 

	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
 
 
 <html:errors/> 
  
 
 <div style="width:70%" align="left">
   
   <br><br>
			    <table align="center"  class="gridWithPricing" border="0" cellspacing="1" cellpadding="0">
			       <tr>
          	    <td width="100%" height="28" nowrap class="fieldGroupHead-r">
      		       File Name : 	<html:file  property="stmFile"/>  
      		       <%
             if(TTKCommon.isAuthorized(request,"Edit")) {
               %>	
      		     &nbsp; <button type="button" name="uploadButton" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"     onClick="javascript:onUploadMemDetails()"><u>U</u>pload File</button>
      		     <%
               }
              %>  
               </td>
               <td  height="28" nowrap class="fieldGroupHeader-r">
               <font color="color:#495879;">Show Template :</font><a href="#" onClick="javascript:showTemplate()" >Sample upload file</a>
      		</td>
        </tr>
		 </table>
   
 <logic:notEmpty name="frmSwIncomeProfile" property="table1Data" scope="session">
<table align="center"  class="gridWithPricing" border="0" cellspacing="1" cellpadding="0">
       <tr ID="listsubheader" class="gridHeader">
			      <td ID="listsubheader" class="gridHeader" width="20%">Total covered lives:</td>
			      <td ID="listsubheader" class="gridHeader"  width="20%">
					<b>&nbsp;<bean:write name="frmSwPricing" property="totalCovedLives" scope="session"/></b>
					 
			      </td>
			   
			      <td ID="listsubheader" class="gridHeader" width="30%">Total covered lives eligible for maternity :</td>
			      <td ID="listsubheader" class="gridHeader" width="30%">
				  <b>&nbsp;	<bean:write name="frmSwPricing" property="totalLivesMaternity"/></b>

			      </td>	
			     </tr>
			     </table>
			
			   </logic:notEmpty>
			  
			
			     <br><br>
			<!-- Covered Lives By Age Band Inpatient And Outpatient -->
			
			
	<logic:notEmpty name="frmSwIncomeProfile" property="table1Data" scope="session">
			
			<table align="center" class="gridWithPricing" border="0" cellspacing="1" cellpadding="0">
	<tr><th colspan="5" align="center" ID="listsubheader" class="gridHeader"> <bean:write name="frmSwIncomeProfile" property="table1HeaderName" /></th></tr>
	<tr>
		<td width="20%" ID="listsubheader" CLASS="gridHeader">Gender&nbsp;</td>
		<td width="20%" ID="listsubheader" CLASS="gridHeader">Age range&nbsp;</td>
		<td width="30%" ID="listsubheader" CLASS="gridHeader">Total covered lives &nbsp;</td>
		<td width="30%" ID="listsubheader" CLASS="gridHeader">Overall portfolio distribution &nbsp;</td>
		</tr>
		<logic:iterate id="item" name="frmSwIncomeProfile" indexId="indexSize" property="table1Data" scope="session">
      	<% if(indexSize%2==0) { %>
				<tr class="gridOddRow">
			<% 
			  } else { %>
  				<tr class="gridEvenRow">
  			<%
			  } %>
			  	<td width="20%" class="formLabel">
			  	
			  	<bean:write name="item" property="column2" />
			  	</td>
			  	<td width="20%" class="formLabel">
			  	<bean:write name="item" property="column3" />
			  	</td>
			  	<td width="20%" class="formLabel">
			  	
			  	<input onkeyup="isNumeric(this);checkTotalForTable1();" type="text" id="colTotCovrLivesID<%=indexSize%>" class="textBox textBoxVerySmall" name="colTotCovrLives<%=indexSize%>" value="<bean:write name="item" property="column4" />">
			  
              </td>
              	
			  	<td width="20%" class="formLabel">
			  	<bean:write name="item" property="ovrlPofloDist" />
			  	</td>
			  	
			</tr>
      
	      </logic:iterate>
	      </table>
	       <table align="center" class="gridWithPricing" border="0" cellspacing="1" cellpadding="0"> 
			<tr class="gridEvenRow">
			<td  width="40%">Total covered lives</td>
			<td width="30%" class="textLabelBold"> 
				<html:text property="table1totalLives" name="frmSwIncomeProfile"  styleId="frmSwIncomeProfileID" readonly="true" styleClass="textBox textBoxVerySmall" ></html:text> 
			</td>
			<td width="30%" >
			<b>100%</b>	</td>
			</tr>
			</table>
	   </logic:notEmpty>
	<br><br>
	<!-- Covered Lives By Age Band  maternity  -->
	<logic:notEmpty name="frmSwIncomeProfile" property="table2Data" scope="session">
			
			<table align="center" class="gridWithPricing" border="0" cellspacing="1" cellpadding="0">
	<tr><th colspan="5" align="center" ID="listsubheader" class="gridHeader"> <bean:write name="frmSwIncomeProfile" property="table2HeaderName" /></th></tr>
	<tr>
		<td width="20%" ID="listsubheader" CLASS="gridHeader">Gender&nbsp;</td>
		<td width="20%" ID="listsubheader" CLASS="gridHeader">Age range&nbsp;</td>
		<td width="30%" ID="listsubheader" CLASS="gridHeader">Total covered lives &nbsp;</td>
		<td width="30%" ID="listsubheader" CLASS="gridHeader">Overall portfolio distribution &nbsp;</td>
		</tr>
		<logic:iterate id="item" name="frmSwIncomeProfile" indexId="indexSize" property="table2Data" scope="session">
      	<% if(indexSize%2==0) { %>
				<tr class="gridOddRow">
			<% 
			  } else { %>
  				<tr class="gridEvenRow">
  			<%
			  } %>
			  	<td width="20%" class="formLabel">
			  	
			  	<bean:write name="item" property="column2" />
			  	</td>
			  	<td width="20%" class="formLabel">
			  	<bean:write name="item" property="column3" />
			  	</td>
			  	<td width="20%" class="formLabel">
			  	
			  	<input onkeyup="isNumeric(this);checkTotalForTable2();" type="text" id="colTotCovrLivesID2<%=indexSize%>" class="textBox textBoxVerySmall" name="colTotCovr2Lives<%=indexSize%>" value="<bean:write name="item" property="column4" />">
			  
			  	</td>
			  	
			  	 	<td width="20%" class="formLabel">
			  	<bean:write name="item" property="ovrlPofloDist" />
			  	</td>
			  	
			</tr>
      
	      </logic:iterate>
	      </table>
	       <table align="center" class="gridWithPricing" border="0" cellspacing="1" cellpadding="0"> 
			<tr class="gridEvenRow">
			<td  width="40%">Total covered lives</td>
			<td width="30%" class="textLabelBold"> 
				<html:text property="table2totalLives" name="frmSwIncomeProfile"  styleId="frmSwIncomeProfileID2" readonly="true" styleClass="textBox textBoxVerySmall" ></html:text> 
			</td>
			<td width="30%" >
			<b>100%</b>	</td>
			</tr>
			</table>
			</logic:notEmpty>
			<br><br>
	<!-- Covered lives by Income group   -->
	<logic:notEmpty name="frmSwIncomeProfile" property="table3Data" scope="session">
			
			<table align="center" class="gridWithPricing" border="0" cellspacing="1" cellpadding="0">
	<tr><th colspan="5" align="center" ID="listsubheader" class="gridHeader"> <bean:write name="frmSwIncomeProfile" property="table3HeaderName" /></th></tr>
	<tr>
		<td width="40%" ID="listsubheader" CLASS="gridHeader">Income group&nbsp;</td>
		<td width="30%" ID="listsubheader" CLASS="gridHeader">Total covered lives &nbsp;</td>
		<td width="30%" ID="listsubheader" CLASS="gridHeader">Overall portfolio distribution &nbsp;</td>
		</tr>
		<logic:iterate id="item" name="frmSwIncomeProfile" indexId="indexSize" property="table3Data" scope="session">
      	<% if(indexSize%2==0) { %>
				<tr class="gridOddRow">
			<% 
			  } else { %>
  				<tr class="gridEvenRow">
  			<%
			  } %>
			  	<td width="20%" class="formLabel" id = "incomeGrpId<%=indexSize%>">
			  	
			  	<bean:write name="item" property="column5" />
			  	</td>
			  	<td width="20%" class="formLabel">
		  	<logic:equal name="item" property="incmFlag" value="Y">
			  	
			  	<input onkeyup="isNumeric(this);checkTotalForTable3();" type="text" id="colTotCovrLivesID3<%=indexSize%>" class="textBox textBoxVerySmall" name="colTotCovr3Lives<%=indexSize%>" value="<bean:write name="item" property="column4" />">
			  	
			  	</logic:equal >
			  		<logic:equal name="item" property="incmFlag" value="N">
			  		
			  		<input disabled="disabled" onkeyup="isNumeric(this);checkTotalForTable3();" type="text" id="colTotCovrLivesID3<%=indexSize%>" class="textBox textBoxVerySmall" name="colTotCovr3Lives<%=indexSize%>" value="">
			  	
			  	</logic:equal>  
			  	
			  	
			  	<%-- <input  onkeyup="isNumeric(this);checkTotalForTable3();" type="text" id="colTotCovrLivesID3<%=indexSize%>" class="textBox textBoxVerySmall" name="colTotCovr3Lives<%=indexSize%>" value="<bean:write name="item" property="column4" />">
			   --%>
			  	</td>
			  	
			  	 	<td width="20%" class="formLabel">
			  	<bean:write name="item" property="ovrlPofloDist" />
			  	</td>
			  	
			</tr>
      
	      </logic:iterate>
	      </table>
	       <table align="center" class="gridWithPricing" border="0" cellspacing="1" cellpadding="0"> 
			<tr class="gridEvenRow">
			<td  width="40%">Total covered lives</td>
			<td width="30%" class="textLabelBold"> 
				<html:text property="table3totalLives" name="frmSwIncomeProfile"  styleId="frmSwIncomeProfileID3" readonly="true" styleClass="textBox textBoxVerySmall" ></html:text> 
			</td>
			<td width="30%" >
			<b>100%</b>	</td>
			</tr>
			</table>
	   </logic:notEmpty>
	   <br><br>
	   <!-- Covered lives by Nationality -->
	<logic:notEmpty name="frmSwIncomeProfile" property="table4Data" scope="session">
			
			<table align="center" class="gridWithPricing" border="0" cellspacing="1" cellpadding="0">
	<tr><th colspan="5" align="center" ID="listsubheader" class="gridHeader"> <bean:write name="frmSwIncomeProfile" property="table4HeaderName" /></th></tr>
	<tr>
		<td width="40%" ID="listsubheader" CLASS="gridHeader">Income group&nbsp;</td>
		<td width="30%" ID="listsubheader" CLASS="gridHeader">Total covered lives &nbsp;</td>
		<td width="30%" ID="listsubheader" CLASS="gridHeader">Overall portfolio distribution &nbsp;</td>
		</tr>
		<logic:iterate id="item" name="frmSwIncomeProfile" indexId="indexSize" property="table4Data" scope="session">
      	<% if(indexSize%2==0) { %>
				<tr class="gridOddRow">
			<% 
			  } else { %>
  				<tr class="gridEvenRow">
  			<%
			  } %>
			  	<td width="20%" class="formLabel">
			  	
			  	<bean:write name="item" property="column6" />
			  	</td>
			  	<td width="20%" class="formLabel">
			  	
			  	<input onkeyup="isNumeric(this);checkTotalForTable4();" type="text" id="colTotCovrLivesID4<%=indexSize%>" class="textBox textBoxVerySmall" name="colTotCovr4Lives<%=indexSize%>" value="<bean:write name="item" property="column4" />">
			  	</td>
			  	
			  	 	<td width="20%" class="formLabel">
			  	<bean:write name="item" property="ovrlPofloDist" />
			  	</td>
			  	
			</tr>
      
	      </logic:iterate>
	      </table>
	       <table align="center" class="gridWithPricing" border="0" cellspacing="1" cellpadding="0"> 
			<tr class="gridEvenRow">
			<td  width="40%">Total covered lives</td>
			<td width="30%" class="textLabelBold"> 
				<html:text property="table4totalLives" name="frmSwIncomeProfile"  styleId="frmSwIncomeProfileID4" readonly="true" styleClass="textBox textBoxVerySmall" ></html:text> 
			</td>
			<td width="30%" >
			<b>100%</b>	</td>
			</tr>
			</table>
	   </logic:notEmpty>
	   
	   <logic:notEmpty name="frmSwIncomeProfile" property="table1Data" scope="session">
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		 <tr> 
		     <td colspan="3" align="center">
      		 <%
             if(TTKCommon.isAuthorized(request,"Edit")) {
             %>
      		   <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveIncomePartial('partialsave');">P<u>a</u>rtial Save</button>&nbsp;
      			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveIncome('save');"><u>S</u>ave</button>&nbsp;
      			<button type="button" name="Button" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveIncome('saveProceed');"><u>P</u>roceed >></button>&nbsp;  <!-- onClick="javascript:onViewPlanDesign();" -->
 			<%
            }
          %>
		  	<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseIncome();"><u>B</u>ack</button>&nbsp;

		    </td>
	  	</tr>
	</table>
	</logic:notEmpty>
    <!-- end bikki -->
    <!-- S T A R T : Buttons and Page Counter -->
   
</div>
    <input type="hidden" name="child" value="">
    <INPUT TYPE="hidden" NAME="rownum" VALUE="">
    <INPUT TYPE="hidden" NAME="mode" VALUE="">
    <INPUT TYPE="hidden" NAME="tab" VALUE="">
     <INPUT TYPE="hidden" NAME="totalCovedLives" VALUE="<bean:write name="frmSwPricing" property="totalCovedLives" scope="session"/>">
     <INPUT TYPE="hidden" NAME="totalLivesMaternity" VALUE="<bean:write name="frmSwPricing" property="totalLivesMaternity" scope="session"/>">
     <INPUT TYPE="hidden" NAME="incomeGroup" id = "incomeGroup" VALUE="<%=session.getAttribute("incmGrp") %>">
    <html:hidden property="fileName" name="frmSwIncomeProfile"/>
    <!-- E N D : Buttons and Page Counter -->
</html:form>

<!--  	<script type="text/javascript">

	incomeGrpSel();
	
</script> -->


<!-- E N D : Content/Form Area -->



