<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.common.security.Cache"%>
<%
	pageContext.setAttribute("benefitType",Cache.getCacheObject("benifitTypes"));
	pageContext.setAttribute("status",Cache.getCacheObject("claimStatus"));
		
%>

<head>
<meta charset="utf-8">
    <link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
    
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/providerLogin/claimSummarySearch.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>

<style type="text/css">

 #brokerSubContainerTable{
                border-collapse: collapse;
                width: 100%;
                height: 100%;
                margin: 0;
                padding: 0;
            }
            #brokerLeftImg{
                border-collapse: collapse;
                height: 550px;
                vertical-align: top;
                background-color: azure;
                background-image: url("/ttk/images/Insurance/content.png");
            }
            
               #brokerMidBody{
                width: 90%;
                height: 100%;
                vertical-align: top;               
            }
             #brokerRightImg{
                border-collapse: collapse;
                height: 550px;
                vertical-align: top;
                background-color: azure;
                background-image: url("/ttk/images/Insurance/content.png");
            }  
            
            
  #contentOuterSeparator1{margin-top: 0px; 
  border-top-color: rgb(237, 237, 237); 
  border-top-width: 0px; 
  border-top-style: solid;
   margin-bottom: 45px;
    height: 0px; 
    background-color: rgb(245, 245, 245); 
    background-image: -webkit-linear-gradient(top, rgb(245, 245, 245), rgb(255, 255, 255));}
              

</style>






</head>
<html:form action="/OnlineProviderClaimSummaryAction.do"  >
<body id="pageBody">

<div class="contentArea" id="contentArea">
<!-- S T A R T : Content/Form Area -->
 
 <table id="brokerSubContainerTable">
                        <tr>
                            <td  id="brokerLeftImg">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td  id="brokerMidBody">
 
 
 
 
<div class="container" width="1155px" style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >


<div style="margin-left: -53px;">

	<div id="contentOuterSeparator"></div>  
	<h4 style="color: rgb(0, 0, 0);
    background-color: rgb(145, 200, 95);
    text-align: left;
    padding: 5px 16px;
    text-decoration: none;
    margin-left: 0px;
    margin-top: 0px;
    width: 20%;
    margin-bottom: 3px;
    border: 1px solid rgb(145, 200, 95);
    white-space: nowrap;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
    border-radius: 0px 20px 21px 0px;
    font-size: 20px;">Claims Detailed Report</h4>
<html:errors/>
	<div id="contentOuterSeparator1"></div>
</div>           

       </div>
        <div class="row-fluid" >
        <div style="width: 100%;">


<div style="margin-left: -62px;width: 1150px;">

	<table align="center" class="searchContainer" border="0" cellspacing="3" cellpadding="3">
    	      <tr>
    	      
    	      <td width="25%" nowrap>Claim Number:<br>
    	            <html:text property="claimnumber" name="frmclaimsummary" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
    	        </td>
    	        
    	        <td width="25%" nowrap>From date: <span></span>
    	        
    	        
    	        <html:select property ="datefrom" name="frmclaimsummary" styleClass="selectBox selectBoxMedium">
	           			<html:option value="">Select from list</html:option>
	           			<html:option value="CLM_RECEIVED_DATE">Claim Received Date</html:option>
	           			<html:option value="date_of_hospitalization">Treatment Start Date</html:option>
       				</html:select>
    	    
    	        <br>
    	        
    	        
				<html:text property="fromDate" name="frmclaimsummary" styleClass="textBox textBoxMedium" maxlength="12"/>
 					<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmclaimsummary.fromDate',document.frmclaimsummary.fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
  						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
  					</a>
			 	</td>
    	        
    	        <td width="25%" nowrap>To date:<br>
    				<html:text property="toDate" name="frmclaimsummary" styleClass="textBox textBoxMedium" maxlength="12" />
   				<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmclaimsummary.toDate',document.frmclaimsummary.toDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
					<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="toDate1" width="24" height="17" border="0" align="absmiddle">
				</a> 
    	        </td>
    	        
    	        <td width="25%" nowrap>Benefit Type:<br>
       			<html:select property ="benefitType" name="frmclaimsummary" styleClass="selectBox selectBoxMedium">
           			<html:option value="">Select from list</html:option>
           			<html:options collection="benefitType" property="cacheId" labelProperty="cacheDesc"/>
       			</html:select>
   	        </td>
    	     </tr>
    	     <tr>
    	        <td width="25%" nowrap>Invoice Number:<br>
    	            <html:text property="invoiceNo" name="frmclaimsummary" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
    	        </td>
    	        
    	        <td width="25%" nowrap>Batch Number:<br>
    	            <html:text property="batchNo" name="frmclaimsummary" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
    	        </td>
    	        
    	         <td width="25%" nowrap>Status:<br>
         			<html:select property ="status" name="frmclaimsummary" styleClass="selectBox selectBoxMedium">
	           			<html:option value="">Select from list</html:option>
	           			<html:options collection="status" property="cacheId" labelProperty="cacheDesc"/>
       				</html:select>
       	        </td>
    	        
    	     <td width="25%" nowrap>Claim TYpe:<br>
         			<html:select property ="calimType" name="frmclaimsummary" styleClass="selectBox selectBoxMedium">
	           			<html:option value="">Select from list</html:option>
	           			<html:option value="CNH">Network</html:option>
	           		<%-- 	<html:option value="CTM">Member</html:option> --%>
       				</html:select>
       	        </td>
    	     </tr>
    	     
    	      <tr>  <td></td>
    	      		<td></td>
    	      		<td></td>
    	     
    	        
  	        <td width="25%" nowrap colspan="4" align="center">
		 		<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSearch()"><u>S</u>earch</button>
			<!-- <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onGenerateReport();"><u>D</u>ownload</button> -->
			
			</td>
    	     </tr> 
   </table>
   
   <br>
   <div style="margin-left: 8px;overflow: scroll;width: 1150px;height: 350px;">
   <!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableData" className="table table-striped"/>

	
	</div>
	<br>
<table align="right">
<tr>
		     <td></td>
		     
		      <td>
		      <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onGenerateReport();"><u>D</u>ownload</button>
		  </td>
		  </tr>
</table>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		   
		    
		   
   <ttk:PageLinks name="tableData"/>
		    
		 
		   
		    
	</table>
	
</div>


</div>
</div>
</div>
 <br><br><br>
</div>

</td>
                            <td  id="brokerRightImg">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        </tr>
                    </table>
</div>


<!--E N D : Content/Form Area -->
 <%-- <html:hidden NAME="empanelmentId"  property="empanelId" ></html:hidden> --%>  
 <!-- <INPUT TYPE="hidden" NAME="empanelmentId"  property="empanelId" name="frmclaimsummary" VALUE="">  -->
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="rownum" VALUE="">
<INPUT TYPE="hidden" NAME="pageId" VALUE="">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">

  <html:hidden property="letterPath" name="frmclaimsummary" />

</body>
</html:form>

