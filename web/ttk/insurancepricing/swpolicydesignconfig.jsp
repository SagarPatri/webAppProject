
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%
/**
 * @ (#)  bajaj separation 1274A
 * Reason        :  
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>

<link rel="stylesheet" href="/ttk/styles/css-wrap.css">
      <script src="/ttk/scripts/bootstrap/js/jquery-3.1.1.min.js"></script>
      <script src="/ttk/scripts/bootstrap/js/popper.min.js"></script>
      <script src="/ttk/scripts/bootstrap/js/bootstrap.min.js"></script>
      <script language="javascript" src="/ttk/scripts/insurancepricing/swpolicydesignconfig.js"></script>
      <!--  <script src="js/jquery.flexslider-min.js"></script> -->
      
      <script type="text/javascript">
function setFSWidth(currTab){
	var mydiv;
	if(currTab==4){
		mydiv=document.getElementById("tab-four");
	} else if(currTab==3){
		mydiv=document.getElementById("tab-three");
	}else if(currTab==2){
		mydiv=document.getElementById("tab-two");
	}else {
		mydiv=document.getElementById("tab-one");
		}
	var w1 = mydiv.clientWidth;	
	var w2 = mydiv.offsetWidth;
	var w3=mydiv.style.width;
	
	alert(w1+":"+w2+":"+w3);
	
}
</script>
     
	<%
  boolean viewmode=true;
	 String pastExplable = "";
	 String demopastExplable = "";	String INPCPM = "";	String OUTCPM = "";	String MATCPM = "";	String OPTCPM = "";	String DENTCPM = "";
	 String textbox = "textBox textBoxVerySmall";
	 HashMap<String,String> hmPropPlicyDtls=null;
	 HashMap<String,String> hmPropPlicyDtls2=null;
  %>
<!-- S T A R T : Content/Form Area -->
<html:form action="/SwPlanDesignConfigurationAction.do" method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0"	cellpadding="0">
		<tr>
			<td><bean:write
				name="frmSwPolicyConfig" property="caption" /></td>
			<td align="right"></td>
			<td align="right"></td>
		</tr>
	</table>

	<!-- E N D : Page Title -->
	
	
			
			<bean:define id="tempPropData" name="frmSwPolicyConfig" property="hmPropPlicyDtlsTable1" type="java.util.HashMap" />
			<bean:define id="tempPropData2" name="frmSwPolicyConfig" property="propPlicyDtlsTable2"/>
			<%hmPropPlicyDtls=(HashMap<String,String>)tempPropData; %>
			<bean:define id="tempCurrentData" name="frmSwPolicyConfig" property="currentPolicyDtlsTable1"/>
			<bean:define id="tempCurrentData2" name="frmSwPolicyConfig" property="currentPolicyDtlsTable2"/>
	 <!-- demographic Data -->	
	<!--  <div style="float:left;display:inline-block;" >	 -->		
	 <div class="contentArea" style="overflow-x:scroll; width:99%;" id="contentArea">		       
	
      <!-- content -->
      <div class="page-container">
      <div class="container-fluid no-padding-left">
       <div class="body-area">
       
         <html:errors />
	 <!-- S T A R T : Success Box -->
	 <logic:notEmpty name="frmSwPolicyConfig"	property="pricingNumberAlert">
				<table align="center" class="errorContainer" border="0"	cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Warning"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmSwPolicyConfig" property="pricingNumberAlert" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			
	<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display: " border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
					width="16" height="16" align="absmiddle">&nbsp; 
					<bean:message name="updated" scope="request" /></td>
			</tr>
		</table>
	</logic:notEmpty> <!-- E N D : Success Box --> <!-- S T A R T : Form Fields -->
	
	 	<logic:notEmpty name="frmSwPolicyConfig"	property="alertMsg">
				<table align="center" class="errorContainer" border="0"	cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Warning"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmSwPolicyConfig" property="alertMsg" /></td>
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
        
        
            <section class="tool-content">
             
           
               <div class="page-body" style="margin-left: 1px;margin-top: -5px;">
              
                 
                  <div class="row custom-row" >    
                   <div class="the-fieldset">
                  <span class="the-legend">Key coverages and demographic details</span>     
                   <div class="table-top-item">
                    
                     <div class="table-tabs2 float-left">
                     
                        <ul class="tabPrc" style="margin-left: 45%; width: 100%;">
                        <%
                        String[]dataTab={"tab-one","tab-two","tab-three","tab-four"};
                        String strCurrentTab=(String)request.getParameter("currentTab");
                        int intCT=(strCurrentTab==null||"1".equals(strCurrentTab)||"".equals(strCurrentTab)?1:new Integer(strCurrentTab).intValue());
                       for(int i=1;i<5;i++){
                        if(intCT==i){
                        %>
                           <li class="tab-link current" data-tab="<%=dataTab[i-1]%>"><span onclick="setCurrentTab(<%=i%>);">Category <%=i%></span></li>
                           <%}else{ %>
                              <li class="tab-link" data-tab="<%=dataTab[i-1]%>"><span onclick="setCurrentTab(<%=i%>);">Category <%=i%></span></li>
                           <%}
                        }// for(int i=1;1<5;i++){ %>
                       <li style="box-shadow:none;border-radius:0px;background-color: white;width: 10px; ">
                      &nbsp;&nbsp;
                        </li>
                         <li style="box-shadow:none;border-radius:0px;background-color: white;width: 10px; ">
                      &nbsp;&nbsp;
                        </li>
                         <li style="box-shadow:none;border-radius:0px;background-color: white;width: 10px; ">
                      &nbsp;&nbsp;
                        </li>
                        <li style="box-shadow:none;border-radius:0px;background-color: white;width: 10px; ">
                      &nbsp;&nbsp;
                        </li>
                        <li style="box-shadow:none;border-radius:0px;background-color: white;width: 10px; ">
                      &nbsp;&nbsp;
                        </li>
                        <li style="box-shadow:none;border-radius:0px;background-color: white;width: 10px; ">
                      &nbsp;&nbsp;
                        </li>
                        <li style="box-shadow:none;border-radius:0px;background-color: white;width: 10px; ">
                      &nbsp;&nbsp;
                        </li>
                       
                        <li style="box-shadow:none;border-radius:0px;background-color: white;width: 10px; ">
                      &nbsp;&nbsp;
                        </li>
                        <%
                            if(TTKCommon.isAuthorized(request,"Edit")) {
                         %>
                         <li style="box-shadow:none;border-radius:0px;background-color: white; ">
                      
                                <a href="#" class="btn btn-primary btn-sm" onclick="onSave();">Save Data</a>
                        </li>
                       
                        <%
                          }
                       %>   
                       
                        </ul>
                       
                        
                     </div>
                    
                     <div class="clear"></div>
                     
                     
                  </div>
                  
                 
          
                     <div class="col-md-10 col-width2" style="margin-left: 5px;">
                        <div class="table-tabs table-tabs2" style="margin-top: 5px;">
                        
                              
                           <div id="tab-one" class="tab-contents<%=("1".equals(strCurrentTab)||strCurrentTab==null||"".equals(strCurrentTab)?" current":"")%>">
                              
                              <table class="table table-hover custom-table" id="table1" >
                                 <thead>
                                 
                                    <tr class="section1">
                                      <td scope="col" id="thCustHead">
                                      Past policies
                                      
                                      </td>
                                       <th scope="col">Start date </th>
                                       <th scope="col">End date</th>
                                       <th scope="col">Duration<br>(mths)</th>
                                       <th scope="col">IP<br>coverage</th>
                                       <th scope="col">OP<br>coverage</th>
                                       <th scope="col">Mat IP<br>coverage</th>
                                       <th scope="col">Mat OP<br>coverage</th>
                                       <th scope="col">Authority</th>
                                       <th scope="col">Insurance Co.</th>
                                       <th scope="col">AS type</th>
                                       <th scope="col">Lives</th>
                                       <th scope="col">Avg age</th>
                                    </tr>
                                 </thead>
                                 <tbody>
                                  <logic:notEmpty property="alPastPoliciesTable1" name="frmSwPolicyConfig">
                           <logic:iterate id="pastPolData" property="alPastPoliciesTable1" name="frmSwPolicyConfig">
                           <tr>
                              <td style="background-color: #16365C;color: #fff;border-bottom: 2px solid #566573;font-weight: bold;">
                              <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="pastPolData" property="polNoToolTip"/>">
                              <bean:write name="pastPolData" property="demoPolicyNo"/>
                              
                              </span>
                              </td>
                              <td><bean:write name="pastPolData" property="covrgSrartDate"/></td>                              
                              <td><bean:write name="pastPolData" property="covrgEndDate"/></td>
                              
                               <logic:equal value="<%=hmPropPlicyDtls.get("DURATION")%>"  name="pastPolData" property="demoPolicyDurationPerMonth">
                                <td><bean:write name="pastPolData" property="demoPolicyDurationPerMonth"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("DURATION")%>"  name="pastPolData" property="demoPolicyDurationPerMonth">
                                <td class="change"><bean:write name="pastPolData" property="demoPolicyDurationPerMonth"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("IP_COV") %>"  name="pastPolData" property="ipCoverage">
                                <td><bean:write name="pastPolData" property="ipCoverage"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("IP_COV")%>"  name="pastPolData" property="ipCoverage">
                                <td class="change"><bean:write name="pastPolData" property="ipCoverage"/></td>
                               </logic:notEqual>
                            
                           <logic:equal value="<%=hmPropPlicyDtls.get("OP_COV") %>"  name="pastPolData" property="opCoverage">
                                <td><bean:write name="pastPolData" property="opCoverage"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("OP_COV") %>"  name="pastPolData" property="opCoverage">
                                <td class="change"><bean:write name="pastPolData" property="opCoverage"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("MAT_IP_COV") %>"  name="pastPolData" property="matIpCoverage">
                                <td><bean:write name="pastPolData" property="matIpCoverage"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("MAT_IP_COV") %>"  name="pastPolData" property="matIpCoverage">
                                <td class="change"><bean:write name="pastPolData" property="matIpCoverage"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("MAT_OP_COV") %>"  name="pastPolData" property="matOpCoverage">
                                <td><bean:write name="pastPolData" property="matOpCoverage"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("MAT_OP_COV") %>"  name="pastPolData" property="matOpCoverage">
                                <td class="change"><bean:write name="pastPolData" property="matOpCoverage"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("AUTHOR_TYPE") %>"  name="pastPolData" property="demoAuthType">
                                <td><bean:write name="pastPolData" property="demoAuthType"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("AUTHOR_TYPE") %>"  name="pastPolData" property="demoAuthType">
                                <td class="change"><bean:write name="pastPolData" property="demoAuthType"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("INS_COMPANY_NAME") %>"  name="pastPolData" property="demoInsCompNm">
                                <td><bean:write name="pastPolData" property="demoInsCompNm"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("INS_COMPANY_NAME") %>"  name="pastPolData" property="demoInsCompNm">
                                <td class="change"><bean:write name="pastPolData" property="demoInsCompNm"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("AS_TYPE") %>"  name="pastPolData" property="demoAsType">
                                <td><bean:write name="pastPolData" property="demoAsType"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("AS_TYPE") %>"  name="pastPolData" property="demoAsType">
                                <td class="change"><bean:write name="pastPolData" property="demoAsType"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("LIVES") %>"  name="pastPolData" property="demoNoOfLives">
                                <td><bean:write name="pastPolData" property="demoNoOfLives"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("LIVES") %>"  name="pastPolData" property="demoAsType">
                                <td class="change"><bean:write name="pastPolData" property="demoNoOfLives"/></td>
                               </logic:notEqual>
                                <logic:equal value="<%=hmPropPlicyDtls.get("AVG_AGE") %>"  name="pastPolData" property="demoAverageAge">
                                <td><bean:write name="pastPolData" property="demoAverageAge"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("AVG_AGE") %>"  name="pastPolData" property="demoAverageAge">
                                <td class="change"><bean:write name="pastPolData" property="demoAverageAge"/></td>
                               </logic:notEqual>
                              </tr>
                           </logic:iterate>
                           
                           </logic:notEmpty>
                                    
                                    <tr>
                                     <td style="background-color: #16365C;color: #fff;border-bottom: 2px solid #566573;font-weight: bold;">Current policy</td>
                                    <td><html:text property="covrgSrartDate" styleClass="form-control table-input" style="width: 90px; height: 20px;" onblur="isDateIfNotEmpty(this);" name="tempCurrentData"/></td>
                                    <td><html:text property="covrgEndDate" styleClass="form-control table-input" style="width: 90px; height: 20px;" onblur="isDateIfNotEmpty(this);" name="tempCurrentData"/></td>
                                    
                                   
                                   <logic:equal value="<%=hmPropPlicyDtls.get("DURATION")%>"  name="tempCurrentData" property="demoPolicyDurationPerMonth">
                                <td> <html:text property="demoPolicyDurationPerMonth" styleClass="form-control table-input" style="width: 60px; height: 20px;" onkeyup="isValidNumber(this);" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("DURATION")%>"  name="tempCurrentData" property="demoPolicyDurationPerMonth">
                                <td> <html:text property="demoPolicyDurationPerMonth" onkeyup="isValidNumber(this);" styleClass="form-control table-input change" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                                   
                               <logic:equal value="<%=hmPropPlicyDtls.get("IP_COV")%>"  name="tempCurrentData" property="ipCoverage">
                                <td> <html:text property="ipCoverage" styleClass="form-control table-input" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("IP_COV")%>"  name="tempCurrentData" property="ipCoverage">
                                <td> <html:text property="ipCoverage" styleClass="form-control table-input change" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                                   
                               <logic:equal value="<%=hmPropPlicyDtls.get("OP_COV")%>"  name="tempCurrentData" property="opCoverage">
                                <td> <html:text property="opCoverage" styleClass="form-control table-input" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("OP_COV")%>"  name="tempCurrentData" property="opCoverage">
                                <td> <html:text property="opCoverage" styleClass="form-control table-input change" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                                   
                                   <logic:equal value="<%=hmPropPlicyDtls.get("MAT_IP_COV")%>"  name="tempCurrentData" property="matIpCoverage">
                                <td> <html:text property="matIpCoverage" styleClass="form-control table-input" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("MAT_IP_COV")%>"  name="tempCurrentData" property="matIpCoverage">
                                <td> <html:text property="matIpCoverage" styleClass="form-control table-input change" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("MAT_OP_COV")%>"  name="tempCurrentData" property="matOpCoverage">
                                <td> <html:text property="matOpCoverage" styleClass="form-control table-input" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("MAT_OP_COV")%>"  name="tempCurrentData" property="matOpCoverage">
                                <td> <html:text property="matOpCoverage" styleClass="form-control table-input change" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("AUTHOR_TYPE")%>"  name="tempCurrentData" property="demoAuthType">
                                <td> <html:text property="demoAuthType" styleClass="form-control table-input" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("AUTHOR_TYPE")%>"  name="tempCurrentData" property="demoAuthType">
                                <td> <html:text property="demoAuthType" styleClass="form-control table-input change" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("INS_COMPANY_NAME")%>"  name="tempCurrentData" property="demoInsCompNm">
                                <td> <html:text property="demoInsCompNm" styleClass="form-control table-input" style="width: 100px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("INS_COMPANY_NAME")%>"  name="tempCurrentData" property="demoInsCompNm">
                                <td> <html:text property="demoInsCompNm" styleClass="form-control table-input change" style="width: 100px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("AS_TYPE")%>"  name="tempCurrentData" property="demoAsType">
                                <td> <html:text property="demoAsType" styleClass="form-control table-input" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("AS_TYPE")%>"  name="tempCurrentData" property="demoAsType">
                                <td> <html:text property="demoAsType" styleClass="form-control table-input change" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("LIVES")%>"  name="tempCurrentData" property="demoNoOfLives">
                                <td> <html:text property="demoNoOfLives"  styleClass="form-control table-input" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("LIVES")%>"  name="tempCurrentData" property="demoNoOfLives">
                                <td> <html:text property="demoNoOfLives"  styleClass="form-control table-input change" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("AVG_AGE")%>"  name="tempCurrentData" property="demoAverageAge">
                                <td> <html:text property="demoAverageAge"  styleClass="form-control table-input" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("AVG_AGE")%>"  name="tempCurrentData" property="demoAverageAge">
                                <td> <html:text property="demoAverageAge"  styleClass="form-control table-input change" style="width: 60px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                               </tr>
                                    
                                 <tr style="border-bottom: 2px solid #566573;font-weight: bold;">  
                                 <td style="background-color: #16365C;color: #fff;">Proposed policy</td>                
                                <td><%=hmPropPlicyDtls.get("COV_START_DATE") %></td>
                                <td><%=hmPropPlicyDtls.get("COV_END_DATE") %></td>
                                <td><%=hmPropPlicyDtls.get("DURATION") %></td>
                                <td><%=hmPropPlicyDtls.get("IP_COV") %></td>
                                <td><%=hmPropPlicyDtls.get("OP_COV") %></td>
                                <td><%=hmPropPlicyDtls.get("MAT_IP_COV") %></td>
                                <td><%=hmPropPlicyDtls.get("MAT_OP_COV") %></td>
                                <td><%=hmPropPlicyDtls.get("AUTHOR_TYPE") %></td>
                                <td><%=TTKCommon.splitAndAddBR(hmPropPlicyDtls.get("INS_COMPANY_NAME"),30) %></td>
                                <td><%=hmPropPlicyDtls.get("AS_TYPE") %></td>
                                <td><%=hmPropPlicyDtls.get("LIVES") %></td>
                                 <td><%=hmPropPlicyDtls.get("AVG_AGE") %></td>
                              </tr>
                                  <logic:notEmpty name="frmSwPolicyConfig" property="alProductListTable1">
                                  <logic:iterate id="productData" name="frmSwPolicyConfig" property="alProductListTable1">
                                  <tr>      
                                  <td style="background-color: #16365C;color: #fff;border-bottom: 2px solid #566573;font-weight: bold;">
                                  
                                  <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="productData" property="polNoToolTip"/>">
                              <bean:write name="productData" property="demoPolicyNo"/>
                              
                              </span>
                                  
                                  </td>            
                                <td colspan="2"><bean:write name="productData" property="productDate"/></td>
                                
                                <%-- <td><bean:write name="productData" property="demoPolicyDurationPerMonth"/></td>
                               
                                <td><bean:write name="productData" property="ipCoverage"/></td>
                               
                                <td><bean:write name="productData" property="opCoverage"/></td>
                                <td><bean:write name="productData" property="matIpCoverage"/></td>
                                <td><bean:write name="productData" property="matOpCoverage"/></td>
                                <td><bean:write name="productData" property="demoAuthType"/></td>
                                <td><bean:write name="productData" property="demoInsCompNm"/></td>
                                <td><bean:write name="productData" property="demoAsType"/></td>
                                <td><bean:write name="productData" property="demoNoOfLives"/></td>
                                <td><bean:write name="productData" property="demoAverageAge"/></td>
                                 --%>
                                <!-- bk -->
                                <logic:equal value="<%=hmPropPlicyDtls.get("DURATION")%>" name="productData" property="demoPolicyDurationPerMonth">
                                <td><bean:write name="productData" property="demoPolicyDurationPerMonth"/></td>
                                </logic:equal>
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("DURATION")%>" name="productData" property="demoPolicyDurationPerMonth">
                                <td class="change"><bean:write name="productData" property="demoPolicyDurationPerMonth"/></td>
                               </logic:notEqual> 
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("IP_COV") %>"  name="productData" property="ipCoverage">
                                <td><bean:write name="productData" property="ipCoverage"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("IP_COV")%>"  name="productData" property="ipCoverage">
                                <td class="change"><bean:write name="productData" property="ipCoverage"/></td>
                               </logic:notEqual>
                               <logic:equal value="<%=hmPropPlicyDtls.get("OP_COV") %>"  name="productData" property="opCoverage">
                                <td><bean:write name="productData" property="opCoverage"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("OP_COV") %>"  name="productData" property="opCoverage">
                                <td class="change"><bean:write name="productData" property="opCoverage"/></td>
                               </logic:notEqual>
                               <logic:equal value="<%=hmPropPlicyDtls.get("MAT_IP_COV") %>"  name="productData" property="matIpCoverage">
                                <td><bean:write name="productData" property="matIpCoverage"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("MAT_IP_COV") %>"  name="productData" property="matIpCoverage">
                                <td class="change"><bean:write name="productData" property="matIpCoverage"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("MAT_OP_COV") %>"  name="productData" property="matOpCoverage">
                                <td><bean:write name="productData" property="matOpCoverage"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("MAT_OP_COV") %>"  name="productData" property="matOpCoverage">
                                <td class="change"><bean:write name="productData" property="matOpCoverage"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("AUTHOR_TYPE") %>"  name="productData" property="demoAuthType">
                                <td><bean:write name="productData" property="demoAuthType"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("AUTHOR_TYPE") %>"  name="productData" property="demoAuthType">
                                <td class="change"><bean:write name="productData" property="demoAuthType"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("INS_COMPANY_NAME") %>"  name="productData" property="demoInsCompNm">
                                <td><bean:write name="productData" property="demoInsCompNm"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("INS_COMPANY_NAME") %>"  name="productData" property="demoInsCompNm">
                                <td class="change"><bean:write name="productData" property="demoInsCompNm"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("AS_TYPE") %>"  name="productData" property="demoAsType">
                                <td><bean:write name="productData" property="demoAsType"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("AS_TYPE") %>"  name="productData" property="demoAsType">
                                <td class="change"><bean:write name="productData" property="demoAsType"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("LIVES") %>"  name="productData" property="demoNoOfLives">
                                <td><bean:write name="productData" property="demoNoOfLives"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("LIVES") %>"  name="productData" property="demoAsType">
                                <td class="change"><bean:write name="productData" property="demoNoOfLives"/></td>
                               </logic:notEqual>
                                <logic:equal value="<%=hmPropPlicyDtls.get("AVG_AGE") %>"  name="productData" property="demoAverageAge">
                                <td><bean:write name="productData" property="demoAverageAge"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("AVG_AGE") %>"  name="productData" property="demoAverageAge">
                                <td class="change"><bean:write name="productData" property="demoAverageAge"/></td>
                               </logic:notEqual>
                              </tr>
                          
                                <!-- end bk -->
                      
                                  </logic:iterate>
                                  </logic:notEmpty>
                                 </tbody>
                              </table>
                           </div>
                           <div id="tab-two" class="tab-contents<%=("2".equals(strCurrentTab)?" current":"")%>">
                              <table class="table table-hover custom-table" id="table1">
                                 <thead>
                                    <tr class="section1">
                                      <td scope="col" id="thCustHead">
                                      Past policies
                                      </td>
                                       <th scope="col">Mat lives</th>
                                       <th scope="col">Mat avg age</th>
                                       <th scope="col">Nationality</th>
                                       <th scope="col">Income dist</th>
                                       <th scope="col">Product</th>
                                       <th scope="col">Network</th>
                                    </tr>
                                 </thead>
                                 <tbody>
                                  <logic:notEmpty property="alPastPoliciesTable1" name="frmSwPolicyConfig">
                           <logic:iterate id="pastPolData" property="alPastPoliciesTable1" name="frmSwPolicyConfig">
                           <tr>
                              <td style="background-color: #16365C;color: #fff;border-bottom: 2px solid #566573;font-weight: bold;">
                                 
                                  <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="pastPolData" property="polNoToolTip"/>">
                              <bean:write name="pastPolData" property="demoPolicyNo"/>
                              
                              </span>
                              
                              
                              </td>
                             
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("MAT_LIVES") %>"  name="pastPolData" property="demoMatLives">
                                <td><bean:write name="pastPolData" property="demoMatLives"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("MAT_LIVES")%>"  name="pastPolData" property="demoMatLives">
                                <td class="change"><bean:write name="pastPolData" property="demoMatLives"/></td>
                               </logic:notEqual>
                            
                           <logic:equal value="<%=hmPropPlicyDtls.get("MAT_AVG_AGE") %>"  name="pastPolData" property="demoMatAvgAge">
                                <td><bean:write name="pastPolData" property="demoMatAvgAge"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("MAT_AVG_AGE") %>"  name="pastPolData" property="demoMatAvgAge">
                                <td class="change"><bean:write name="pastPolData" property="demoMatAvgAge"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("NATIONALITY") %>"  name="pastPolData" property="demoNationality">
                                <td><bean:write name="pastPolData" property="demoNationality"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("NATIONALITY") %>"  name="pastPolData" property="demoNationality">
                                <td class="change"><bean:write name="pastPolData" property="demoNationality"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("INCOME_DIST") %>"  name="pastPolData" property="demoIncomeDist">
                                <td><bean:write name="pastPolData" property="demoIncomeDist"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("INCOME_DIST") %>"  name="pastPolData" property="demoIncomeDist">
                                <td class="change"><bean:write name="pastPolData" property="demoIncomeDist"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("PRODUCT_NAME") %>"  name="pastPolData" property="demoprodNm">
                                <td><bean:write name="pastPolData" property="demoprodNm"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("PRODUCT_NAME") %>"  name="pastPolData" property="demoprodNm">
                                <td class="change"><bean:write name="pastPolData" property="demoprodNm"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("NETWORK") %>"  name="pastPolData" property="demoNetwork">
                                <td><bean:write name="pastPolData" property="demoNetwork"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("NETWORK") %>"  name="pastPolData" property="demoNetwork">
                                <td class="change"><bean:write name="pastPolData" property="demoNetwork"/></td>
                               </logic:notEqual>
                               
                              </tr>
                           </logic:iterate>
                           
                           </logic:notEmpty>
                                    
                                    <tr>
                                     <td style="background-color: #16365C;color: #fff;border-bottom: 2px solid #566573;font-weight: bold;">Current policy</td>
                                  
                                    <logic:equal value="<%=hmPropPlicyDtls.get("MAT_LIVES")%>"  name="tempCurrentData" property="demoMatLives">
                                <td> <html:text property="demoMatLives" styleClass="form-control table-input" style="width: 60px; height: 20px;" onkeyup="isValidNumber(this);" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("MAT_LIVES")%>"  name="tempCurrentData" property="demoMatLives">
                                <td> <html:text property="demoMatLives" styleClass="form-control table-input change" style="width: 60px; height: 20px;" onkeyup="isValidNumber(this);" name="tempCurrentData"/></td>
                               </logic:notEqual>
                                  
                                   <logic:equal value="<%=hmPropPlicyDtls.get("MAT_AVG_AGE")%>"  name="tempCurrentData" property="demoMatAvgAge">
                                <td> <html:text property="demoMatAvgAge" styleClass="form-control table-input" style="width: 60px; height: 20px;" onkeyup="isValidNumber(this);" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("MAT_AVG_AGE")%>"  name="tempCurrentData" property="demoMatAvgAge">
                                <td> <html:text property="demoMatAvgAge" styleClass="form-control table-input change" style="width: 60px; height: 20px;" onkeyup="isValidNumber(this);" name="tempCurrentData"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("NATIONALITY")%>"  name="tempCurrentData" property="demoNationality">
                                <td> <html:text property="demoNationality" styleClass="form-control table-input" style="width: 160px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("NATIONALITY")%>"  name="tempCurrentData" property="demoNationality">
                                <td> <html:text property="demoNationality" styleClass="form-control table-input change" style="width: 160px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("INCOME_DIST")%>"  name="tempCurrentData" property="demoIncomeDist">
                                <td> <html:text property="demoIncomeDist" styleClass="form-control table-input" style="width: 160px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("INCOME_DIST")%>"  name="tempCurrentData" property="demoIncomeDist">
                                <td> <html:text property="demoIncomeDist" styleClass="form-control table-input change" style="width: 160px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("PRODUCT_NAME")%>"  name="tempCurrentData" property="demoprodNm">
                                <td> <html:text property="demoprodNm" styleClass="form-control table-input" style="width: 135px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("PRODUCT_NAME")%>"  name="tempCurrentData" property="demoprodNm">
                                <td> <html:text property="demoprodNm" styleClass="form-control table-input change" style="width: 135px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("NETWORK")%>"  name="tempCurrentData" property="demoNetwork">
                                <td> <html:text property="demoNetwork" styleClass="form-control table-input" style="width: 115px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("NETWORK")%>"  name="tempCurrentData" property="demoNetwork">
                                <td> <html:text property="demoNetwork" styleClass="form-control table-input change" style="width: 115px; height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                               
                                    </tr>
                                    
                                 <tr style="border-bottom: 2px solid #566573;font-weight: bold;">  
                                 <td style="background-color: #16365C;color: #fff;">Proposed policy</td>                
                                <td><%=hmPropPlicyDtls.get("MAT_LIVES") %></td>
                                <td><%=hmPropPlicyDtls.get("MAT_AVG_AGE") %></td>
                                <td><%=TTKCommon.splitAndAddBR(hmPropPlicyDtls.get("NATIONALITY"),38) %></td>
                                <td><%=TTKCommon.splitAndAddBR(hmPropPlicyDtls.get("INCOME_DIST"),33) %></td>
                                <td><%=hmPropPlicyDtls.get("PRODUCT_NAME") %></td>
                                <td><%=hmPropPlicyDtls.get("NETWORK") %></td>
                              </tr>
                                  <logic:notEmpty name="frmSwPolicyConfig" property="alProductListTable1">
                                  <logic:iterate id="productData" name="frmSwPolicyConfig" property="alProductListTable1">
                                  <tr>      
                                  <td style="background-color: #16365C;color: #fff;border-bottom: 2px solid #566573;font-weight: bold;">
                                  
                                  <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="productData" property="polNoToolTip"/>">
                              <bean:write name="productData" property="demoPolicyNo"/>
                              
                              </span>
                              
                                  </td>            
                               
                                <%-- <td><bean:write name="productData" property="demoMatLives"/></td>
                                <td><bean:write name="productData" property="demoMatAvgAge"/></td>
                                <td><bean:write name="productData" property="demoNationality"/></td>
                                <td><bean:write name="productData" property="demoIncomeDist"/></td>
                                <td><bean:write name="productData" property="demoprodNm"/></td>
                                <td><bean:write name="productData" property="demoNetwork"/></td> --%>
                                <!-- bk -->
                                <logic:equal value="<%=hmPropPlicyDtls.get("MAT_LIVES") %>"  name="productData" property="demoMatLives">
                                <td><bean:write name="productData" property="demoMatLives"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("MAT_LIVES")%>"  name="productData" property="demoMatLives">
                                <td class="change"><bean:write name="productData" property="demoMatLives"/></td>
                               </logic:notEqual>
                            
                           <logic:equal value="<%=hmPropPlicyDtls.get("MAT_AVG_AGE") %>"  name="productData" property="demoMatAvgAge">
                                <td><bean:write name="productData" property="demoMatAvgAge"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("MAT_AVG_AGE") %>"  name="productData" property="demoMatAvgAge">
                                <td class="change"><bean:write name="productData" property="demoMatAvgAge"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("NATIONALITY") %>"  name="productData" property="demoNationality">
                                <td><bean:write name="productData" property="demoNationality"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("NATIONALITY") %>"  name="productData" property="demoNationality">
                                <td class="change"><bean:write name="productData" property="demoNationality"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("INCOME_DIST") %>"  name="productData" property="demoIncomeDist">
                                <td><bean:write name="productData" property="demoIncomeDist"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("INCOME_DIST") %>"  name="productData" property="demoIncomeDist">
                                <td class="change"><bean:write name="productData" property="demoIncomeDist"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("PRODUCT_NAME") %>"  name="productData" property="demoprodNm">
                                <td><bean:write name="productData" property="demoprodNm"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("PRODUCT_NAME") %>"  name="productData" property="demoprodNm">
                                <td class="change"><bean:write name="productData" property="demoprodNm"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("NETWORK") %>"  name="productData" property="demoNetwork">
                                <td><bean:write name="productData" property="demoNetwork"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("NETWORK") %>"  name="productData" property="demoNetwork">
                                <td class="change"><bean:write name="productData" property="demoNetwork"/></td>
                               </logic:notEqual>
                                <!-- end bk -->
                                
                              </tr>  
                                  </logic:iterate>
                                  </logic:notEmpty>
                                 </tbody>
                              </table>
                           </div>
                           <div id="tab-three" class="tab-contents<%=("3".equals(strCurrentTab)?" current":"")%>">
                              <table style="width: 95%;" class="table table-hover custom-table" id="table1">
                                 <thead>
                                    <tr class="section1">
                                      <td scope="col" id="thCustHead">
                                      Past policies
                                      </td>
                                       <th scope="col">OP AOC</th>
                                       <th scope="col">Drugs limit</th>
                                       <th scope="col">Drugs copay</th>
                                       <th scope="col">Lab copay/ded</th>
                                       <th scope="col">Consult copay/ded</th>
                                       <th scope="col">Physio limit</th>
                                       <th scope="col">Physio copay</th>
                                       <th scope="col">Mat OP visits</th>
                                       <th scope="col">Mat OP copay</th>
                                    </tr>
                                 </thead>
                                 <tbody>
                                  <logic:notEmpty property="alPastPoliciesTable1" name="frmSwPolicyConfig">
                           <logic:iterate id="pastPolData" property="alPastPoliciesTable1" name="frmSwPolicyConfig">
                           <tr>
                              <td style="background-color: #16365C;color: #fff;border-bottom: 2px solid #566573;font-weight: bold;">
                               
                                  <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="pastPolData" property="polNoToolTip"/>">
                              <bean:write name="pastPolData" property="demoPolicyNo"/>
                              
                              </span>
                              
                              </td>
                            
                              <logic:equal value="<%=hmPropPlicyDtls.get("OP_AOC")%>"  name="pastPolData" property="opAreaCov">
                                <td>
                             <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="pastPolData" property="opAocToolTip"/>">
                              <bean:write name="pastPolData" property="opAreaCov"/>
                              
                              </span>
                                
                                </td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("OP_AOC")%>"  name="pastPolData" property="opAreaCov">
                                <td class="change">
                                  <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="pastPolData" property="opAocToolTip"/>">
                                <bean:write name="pastPolData" property="opAreaCov"/>
                                </span>
                                </td>
                               </logic:notEqual>
                              
                               <logic:equal value="<%=hmPropPlicyDtls.get("DRUGS_LIMIT")%>"  name="pastPolData" property="demoDrugsLimit">
                                <td><bean:write name="pastPolData" property="demoDrugsLimit"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("DRUGS_LIMIT")%>"  name="pastPolData" property="demoDrugsLimit">
                                <td class="change"><bean:write name="pastPolData" property="demoDrugsLimit"/></td>
                               </logic:notEqual>
                              
                               <logic:equal value="<%=hmPropPlicyDtls.get("DRUGS_COPAY")%>"  name="pastPolData" property="demoDrugsCopay">
                                <td><bean:write name="pastPolData" property="demoDrugsCopay"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("DRUGS_COPAY")%>"  name="pastPolData" property="demoDrugsCopay">
                                <td class="change"><bean:write name="pastPolData" property="demoDrugsCopay"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("LAB_COPAY_DEDUCTIBLE") %>"  name="pastPolData" property="demoLabCopayDudct">
                                <td><bean:write name="pastPolData" property="demoLabCopayDudct"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("LAB_COPAY_DEDUCTIBLE")%>"  name="pastPolData" property="demoLabCopayDudct">
                                <td class="change"><bean:write name="pastPolData" property="demoLabCopayDudct"/></td>
                               </logic:notEqual>
                            
                           <logic:equal value="<%=hmPropPlicyDtls.get("CONSULT_DEDUCT") %>"  name="pastPolData" property="demoConsltDudct">
                                <td><bean:write name="pastPolData" property="demoConsltDudct"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("CONSULT_DEDUCT") %>"  name="pastPolData" property="demoConsltDudct">
                                <td class="change"><bean:write name="pastPolData" property="demoConsltDudct"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("PHYSIO_LIMIT") %>"  name="pastPolData" property="demoPhysioLimit">
                                <td><bean:write name="pastPolData" property="demoPhysioLimit"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("PHYSIO_LIMIT") %>"  name="pastPolData" property="demoPhysioLimit">
                                <td class="change"><bean:write name="pastPolData" property="demoPhysioLimit"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("PHYSIO_COPAY") %>"  name="pastPolData" property="demoPhysioCopay">
                                <td><bean:write name="pastPolData" property="demoPhysioCopay"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("PHYSIO_COPAY") %>"  name="pastPolData" property="demoPhysioCopay">
                                <td class="change"><bean:write name="pastPolData" property="demoPhysioCopay"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("MAT_OP_VISITS") %>"  name="pastPolData" property="demoMatOpVisits">
                                <td><bean:write name="pastPolData" property="demoMatOpVisits"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("MAT_OP_VISITS") %>"  name="pastPolData" property="demoMatOpVisits">
                                <td class="change"><bean:write name="pastPolData" property="demoMatOpVisits"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("MAT_OP_COPAY") %>"  name="pastPolData" property="demoMatOpCopay">
                                <td><bean:write name="pastPolData" property="demoMatOpCopay"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("MAT_OP_COPAY") %>"  name="pastPolData" property="demoMatOpCopay">
                                <td class="change"><bean:write name="pastPolData" property="demoMatOpCopay"/></td>
                               </logic:notEqual>
                               
                              </tr>
                           </logic:iterate>
                           
                           </logic:notEmpty>
                                    
                            <tr>
                              <td style="background-color: #16365C;color: #fff;border-bottom: 2px solid #566573;font-weight: bold;">Current policy</td>
                                   
                               <logic:equal value="<%=hmPropPlicyDtls.get("OP_AOC")%>"  name="tempCurrentData" property="opAreaCov">
                                  <td> 
                                  <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="tempCurrentData" property="opAocToolTip"/>">
                                  <html:text property="opAreaCov" styleClass="form-control table-input" style="height: 20px;" name="tempCurrentData"/>
                                  </span>
                                  </td>
                              
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("OP_AOC")%>"  name="tempCurrentData" property="opAreaCov">
                                <td> 
                                   <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="tempCurrentData" property="opAocToolTip"/>">
         
                                <html:text property="opAreaCov" styleClass="form-control table-input change" style="height: 20px;" name="tempCurrentData"/>
                                </span>
                                </td>
                               </logic:notEqual>
                                   
                               <logic:equal value="<%=hmPropPlicyDtls.get("DRUGS_LIMIT")%>"  name="tempCurrentData" property="demoDrugsLimit">
                                  <td> <html:text property="demoDrugsLimit" styleClass="form-control table-input" style="height: 20px;"  name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("DRUGS_LIMIT")%>"  name="tempCurrentData" property="demoDrugsLimit">
                                <td> <html:text property="demoDrugsLimit" styleClass="form-control table-input change" style="height: 20px;"  name="tempCurrentData"/></td>
                               </logic:notEqual>
                                    
                               <logic:equal value="<%=hmPropPlicyDtls.get("DRUGS_COPAY")%>"  name="tempCurrentData" property="demoDrugsCopay">
                                  <td> <html:text property="demoDrugsCopay" styleClass="form-control table-input" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("DRUGS_COPAY")%>"  name="tempCurrentData" property="demoDrugsCopay">
                                <td> <html:text property="demoDrugsCopay" styleClass="form-control table-input change" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                                    
                                    <logic:equal value="<%=hmPropPlicyDtls.get("LAB_COPAY_DEDUCTIBLE")%>"  name="tempCurrentData" property="demoLabCopayDudct">
                                  <td> <html:text property="demoLabCopayDudct" styleClass="form-control table-input" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("LAB_COPAY_DEDUCTIBLE")%>"  name="tempCurrentData" property="demoLabCopayDudct">
                                <td> <html:text property="demoLabCopayDudct" styleClass="form-control table-input change" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                                    
                                    <logic:equal value="<%=hmPropPlicyDtls.get("CONSULT_DEDUCT")%>"  name="tempCurrentData" property="demoConsltDudct">
                                  <td> <html:text property="demoConsltDudct" styleClass="form-control table-input" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("CONSULT_DEDUCT")%>"  name="tempCurrentData" property="demoConsltDudct">
                                <td> <html:text property="demoConsltDudct" styleClass="form-control table-input change" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                                    
                                    <logic:equal value="<%=hmPropPlicyDtls.get("PHYSIO_LIMIT")%>"  name="tempCurrentData" property="demoPhysioLimit">
                                  <td> <html:text property="demoPhysioLimit" styleClass="form-control table-input" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("PHYSIO_LIMIT")%>"  name="tempCurrentData" property="demoPhysioLimit">
                                <td> <html:text property="demoPhysioLimit" styleClass="form-control table-input change" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("PHYSIO_COPAY")%>"  name="tempCurrentData" property="demoPhysioCopay">
                                  <td> <html:text property="demoPhysioCopay" styleClass="form-control table-input"  style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("PHYSIO_COPAY")%>"  name="tempCurrentData" property="demoPhysioCopay">
                                <td> <html:text property="demoPhysioCopay" styleClass="form-control table-input change" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("MAT_OP_VISITS")%>"  name="tempCurrentData" property="demoMatOpVisits">
                                  <td> <html:text property="demoMatOpVisits" styleClass="form-control table-input" style="height: 20px;"  name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("MAT_OP_VISITS")%>"  name="tempCurrentData" property="demoMatOpVisits">
                                <td> <html:text property="demoMatOpVisits" styleClass="form-control table-input change" style="height: 20px;"  name="tempCurrentData"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("MAT_OP_COPAY")%>"  name="tempCurrentData" property="demoMatOpCopay">
                                  <td> <html:text property="demoMatOpCopay" styleClass="form-control table-input" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("MAT_OP_COPAY")%>"  name="tempCurrentData" property="demoMatOpCopay">
                                <td> <html:text property="demoMatOpCopay" styleClass="form-control table-input change" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                                    </tr>
                                    
                                 <tr style="border-bottom: 6px solid #AEB6BF;border-bottom: 2px solid #566573;font-weight: bold;">  
                                 <td style="background-color: #16365C;color: #fff;">Proposed policy</td>                
                             <td>
                             <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<%=hmPropPlicyDtls.get("OPAOC_TOOL_TIP") %>">
                             <%=hmPropPlicyDtls.get("OP_AOC") %>
                             </span>
                             </td>
							 <td><%=hmPropPlicyDtls.get("DRUGS_LIMIT") %></td>
							 <td><%=hmPropPlicyDtls.get("DRUGS_COPAY") %></td>
							 <td><%=hmPropPlicyDtls.get("LAB_COPAY_DEDUCTIBLE") %></td>
							 <td><%=hmPropPlicyDtls.get("CONSULT_DEDUCT") %></td>
							 <td><%=hmPropPlicyDtls.get("PHYSIO_LIMIT") %></td>
							 <td><%=hmPropPlicyDtls.get("PHYSIO_COPAY") %></td>
							 <td><%=hmPropPlicyDtls.get("MAT_OP_VISITS") %></td>
							 <td><%=hmPropPlicyDtls.get("MAT_OP_COPAY") %></td>
                              </tr>
                                  <logic:notEmpty name="frmSwPolicyConfig" property="alProductListTable1">
                                  <logic:iterate id="productData" name="frmSwPolicyConfig" property="alProductListTable1">
                                  <tr>      
                                <td style="background-color: #16365C;color: #fff;border-bottom: 2px solid #566573;font-weight: bold;">
                                <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="productData" property="polNoToolTip"/>">
                              <bean:write name="productData" property="demoPolicyNo"/>
                              
                              </span>
                                </td>            
                               <%--  <td>
                                   <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="productData" property="opAocToolTip"/>">
         
                                <bean:write name="productData" property="opAreaCov"/>
                                </span>
                                </td> --%>
                                
                                <%-- <td><bean:write name="productData" property="demoDrugsLimit"/></td>
                                <td><bean:write name="productData" property="demoDrugsCopay"/></td>
                                <td><bean:write name="productData" property="demoLabCopayDudct"/></td>
                                <td><bean:write name="productData" property="demoConsltDudct"/></td>
                                <td><bean:write name="productData" property="demoPhysioLimit"/></td>
                                <td><bean:write name="productData" property="demoPhysioCopay"/></td>
                                <td><bean:write name="productData" property="demoMatOpVisits"/></td>
                                <td><bean:write name="productData" property="demoMatOpCopay"/></td> --%>
                                
                                <!-- bk -->
                                
                                <logic:equal value="<%=hmPropPlicyDtls.get("OP_AOC")%>"  name="productData" property="opAreaCov">
                                <td>
                             <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="productData" property="opAocToolTip"/>">
                              <bean:write name="productData" property="opAreaCov"/>
                              
                              </span>
                                
                                </td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("OP_AOC")%>"  name="productData" property="opAreaCov">
                                <td class="change">
                                  <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="productData" property="opAocToolTip"/>">
                                <bean:write name="productData" property="opAreaCov"/>
                                </span>
                                </td>
                               </logic:notEqual>
                                <logic:equal value="<%=hmPropPlicyDtls.get("DRUGS_LIMIT")%>"  name="productData" property="demoDrugsLimit">
                                <td><bean:write name="productData" property="demoDrugsLimit"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("DRUGS_LIMIT")%>"  name="productData" property="demoDrugsLimit">
                                <td class="change"><bean:write name="productData" property="demoDrugsLimit"/></td>
                               </logic:notEqual>
                              
                               <logic:equal value="<%=hmPropPlicyDtls.get("DRUGS_COPAY")%>"  name="productData" property="demoDrugsCopay">
                                <td><bean:write name="productData" property="demoDrugsCopay"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("DRUGS_COPAY")%>"  name="productData" property="demoDrugsCopay">
                                <td class="change"><bean:write name="productData" property="demoDrugsCopay"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("LAB_COPAY_DEDUCTIBLE") %>"  name="productData" property="demoLabCopayDudct">
                                <td><bean:write name="productData" property="demoLabCopayDudct"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("LAB_COPAY_DEDUCTIBLE")%>"  name="productData" property="demoLabCopayDudct">
                                <td class="change"><bean:write name="productData" property="demoLabCopayDudct"/></td>
                               </logic:notEqual>
                            
                           <logic:equal value="<%=hmPropPlicyDtls.get("CONSULT_DEDUCT") %>"  name="productData" property="demoConsltDudct">
                                <td><bean:write name="productData" property="demoConsltDudct"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("CONSULT_DEDUCT") %>"  name="productData" property="demoConsltDudct">
                                <td class="change"><bean:write name="productData" property="demoConsltDudct"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("PHYSIO_LIMIT") %>"  name="productData" property="demoPhysioLimit">
                                <td><bean:write name="productData" property="demoPhysioLimit"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("PHYSIO_LIMIT") %>"  name="productData" property="demoPhysioLimit">
                                <td class="change"><bean:write name="productData" property="demoPhysioLimit"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("PHYSIO_COPAY") %>"  name="productData" property="demoPhysioCopay">
                                <td><bean:write name="productData" property="demoPhysioCopay"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("PHYSIO_COPAY") %>"  name="productData" property="demoPhysioCopay">
                                <td class="change"><bean:write name="productData" property="demoPhysioCopay"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("MAT_OP_VISITS") %>"  name="productData" property="demoMatOpVisits">
                                <td><bean:write name="productData" property="demoMatOpVisits"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("MAT_OP_VISITS") %>"  name="productData" property="demoMatOpVisits">
                                <td class="change"><bean:write name="productData" property="demoMatOpVisits"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("MAT_OP_COPAY") %>"  name="productData" property="demoMatOpCopay">
                                <td><bean:write name="productData" property="demoMatOpCopay"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("MAT_OP_COPAY") %>"  name="productData" property="demoMatOpCopay">
                                <td class="change"><bean:write name="productData" property="demoMatOpCopay"/></td>
                               </logic:notEqual>
                                <!-- end bk -->
                                
                              </tr>  
                                  </logic:iterate>
                                  </logic:notEmpty>
                                 </tbody>
                              </table>
                           </div>
                           <div id="tab-four"  class="tab-contents<%=("4".equals(strCurrentTab)?" current":"")%>">
                              <div>
                              <table class="table table-hover custom-table" id="table1" style="width: 95%;"  >
                                 <thead>
                                    <tr class="section1">
                                      <td scope="col" id="thCustHead">
                                      Past policies
                                      </td>
                                       <th scope="col">MBL</th>
                                       <th scope="col">IP AOC</th>
                                       <th scope="col">Room type</th>
                                       <th scope="col">Child age limit</th>
                                       <th scope="col">Companion per<br> day limit</th>
                                       <th scope="col">IP copay</th>
                                       <th scope="col">N delivery limit</th>
                                       <th scope="col">C-section limit</th>
                                       <th scope="col">Mat IP copay</th>
                                    </tr>
                                 </thead>
                                 <tbody>
                                  <logic:notEmpty property="alPastPoliciesTable1" name="frmSwPolicyConfig">
                           <logic:iterate id="pastPolData" property="alPastPoliciesTable1" name="frmSwPolicyConfig">
                           <tr>
                              <td style="background-color: #16365C;color: #fff;border-bottom: 2px solid #566573;font-weight: bold;">
                             
                              <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="pastPolData" property="polNoToolTip"/>">
                              <bean:write name="pastPolData" property="demoPolicyNo"/>
                              
                              </span>
                              </td>
                            
                              <logic:equal value="<%=hmPropPlicyDtls.get("MBL")%>"  name="pastPolData" property="demoMaximumBenfitLimit">
                                <td><bean:write name="pastPolData" property="demoMaximumBenfitLimit"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("MBL")%>"  name="pastPolData" property="demoMaximumBenfitLimit">
                                <td class="change"><bean:write name="pastPolData" property="demoMaximumBenfitLimit"/></td>
                               </logic:notEqual>
                              
                               <logic:equal value="<%=hmPropPlicyDtls.get("IP_AOC")%>"  name="pastPolData" property="demoIpAoc">
                                <td>
                               	<span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="pastPolData" property="ipAocToolTip"/>">
                                <bean:write name="pastPolData" property="demoIpAoc"/>
                                </span>
                                </td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("IP_AOC")%>"  name="pastPolData" property="demoIpAoc">
                                <td class="change">
                                <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="pastPolData" property="ipAocToolTip"/>">
                                <bean:write name="pastPolData" property="demoIpAoc"/>
                                </span>
                                </td>
                               </logic:notEqual>
                              
                               <logic:equal value="<%=hmPropPlicyDtls.get("ROOM_TYPE")%>"  name="pastPolData" property="demoRoomType">
                                <td><bean:write name="pastPolData" property="demoRoomType"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("ROOM_TYPE")%>"  name="pastPolData" property="demoRoomType">
                                <td class="change"><bean:write name="pastPolData" property="demoRoomType"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("CHILD_AGE_LIMIT") %>"  name="pastPolData" property="demoChildAgeLimit">
                                <td><bean:write name="pastPolData" property="demoChildAgeLimit"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("CHILD_AGE_LIMIT")%>"  name="pastPolData" property="demoChildAgeLimit">
                                <td class="change"><bean:write name="pastPolData" property="demoChildAgeLimit"/></td>
                               </logic:notEqual>
                            
                           <logic:equal value="<%=hmPropPlicyDtls.get("COMPANY_PER_DAY_LIMIT") %>"  name="pastPolData" property="demoCompPerDayLimit">
                                <td><bean:write name="pastPolData" property="demoCompPerDayLimit"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("COMPANY_PER_DAY_LIMIT") %>"  name="pastPolData" property="demoCompPerDayLimit">
                                <td class="change"><bean:write name="pastPolData" property="demoCompPerDayLimit"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("IP_COPAY") %>"  name="pastPolData" property="demoIpCopay">
                                <td><bean:write name="pastPolData" property="demoIpCopay"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("IP_COPAY") %>"  name="pastPolData" property="demoIpCopay">
                                <td class="change"><bean:write name="pastPolData" property="demoIpCopay"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("NORMAL_DELI_LIMIT") %>"  name="pastPolData" property="demoNrmlDeliLimit">
                                <td><bean:write name="pastPolData" property="demoNrmlDeliLimit"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("NORMAL_DELI_LIMIT") %>"  name="pastPolData" property="demoNrmlDeliLimit">
                                <td class="change"><bean:write name="pastPolData" property="demoNrmlDeliLimit"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("C_SEC_LIMIT") %>"  name="pastPolData" property="demoCSecLimit">
                                <td><bean:write name="pastPolData" property="demoCSecLimit"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("C_SEC_LIMIT") %>"  name="pastPolData" property="demoCSecLimit">
                                <td class="change"><bean:write name="pastPolData" property="demoCSecLimit"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("MAT_IP_COPAY") %>"  name="pastPolData" property="demoMatIpCopay">
                                <td><bean:write name="pastPolData" property="demoMatIpCopay"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("MAT_IP_COPAY") %>"  name="pastPolData" property="demoMatIpCopay">
                                <td class="change"><bean:write name="pastPolData" property="demoMatIpCopay"/></td>
                               </logic:notEqual>
                               
                              </tr>
                           </logic:iterate>
                           
                           </logic:notEmpty>
                                    
                                    <tr>
                                     <td style="background-color: #16365C;color: #fff;border-bottom: 2px solid #566573;font-weight: bold;">Current policy</td>
                                    
                                     <logic:equal value="<%=hmPropPlicyDtls.get("MBL")%>"  name="tempCurrentData" property="demoMaximumBenfitLimit">
                                  <td> <html:text property="demoMaximumBenfitLimit" styleClass="form-control table-input" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("MBL")%>"  name="tempCurrentData" property="demoMaximumBenfitLimit">
                                <td> <html:text property="demoMaximumBenfitLimit" styleClass="form-control table-input change" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                                    
                                    
                               <logic:equal value="<%=hmPropPlicyDtls.get("IP_AOC")%>"  name="tempCurrentData" property="demoIpAoc">
                                  <td> 
                                  	  <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="tempCurrentData" property="ipAocToolTip"/>">
                                  <html:text property="demoIpAoc" styleClass="form-control table-input" style="height: 20px;" name="tempCurrentData"/>
                                  </span>
                                  </td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("IP_AOC")%>"  name="tempCurrentData" property="demoIpAoc">
                                <td> 
                                     	  <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="tempCurrentData" property="ipAocToolTip"/>">
                             
                                <html:text property="demoIpAoc" styleClass="form-control table-input change" style="height: 20px;" name="tempCurrentData"/>
                              </span>              
                                </td>
                               </logic:notEqual>
                                      
                                <logic:equal value="<%=hmPropPlicyDtls.get("ROOM_TYPE")%>"  name="tempCurrentData" property="demoRoomType">
                                  <td> <html:text property="demoRoomType" styleClass="form-control table-input" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("ROOM_TYPE")%>"  name="tempCurrentData" property="demoRoomType">
                                <td> <html:text property="demoRoomType" styleClass="form-control table-input change" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                                   
                                   <logic:equal value="<%=hmPropPlicyDtls.get("CHILD_AGE_LIMIT")%>"  name="tempCurrentData" property="demoChildAgeLimit">
                                  <td> <html:text property="demoChildAgeLimit" styleClass="form-control table-input" style="height: 20px;"  name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("CHILD_AGE_LIMIT")%>"  name="tempCurrentData" property="demoChildAgeLimit">
                                <td> <html:text property="demoChildAgeLimit" styleClass="form-control table-input change" style="height: 20px;"  name="tempCurrentData"/></td>
                               </logic:notEqual>
                                   
                                   <logic:equal value="<%=hmPropPlicyDtls.get("COMPANY_PER_DAY_LIMIT")%>"  name="tempCurrentData" property="demoCompPerDayLimit">
                                  <td> <html:text property="demoCompPerDayLimit" styleClass="form-control table-input" style="height: 20px;"   name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("COMPANY_PER_DAY_LIMIT")%>"  name="tempCurrentData" property="demoCompPerDayLimit">
                                <td> <html:text property="demoCompPerDayLimit" styleClass="form-control table-input change" style="height: 20px;"  name="tempCurrentData"/></td>
                               </logic:notEqual> 
                                   
                                   <logic:equal value="<%=hmPropPlicyDtls.get("IP_COPAY")%>"  name="tempCurrentData" property="demoIpCopay">
                                  <td> <html:text property="demoIpCopay" styleClass="form-control table-input" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("IP_COPAY")%>"  name="tempCurrentData" property="demoIpCopay">
                                <td> <html:text property="demoIpCopay" styleClass="form-control table-input change" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                                   
                                  <%--  <logic:equal value="<%=hmPropPlicyDtls.get("IP_COPAY")%>"  name="tempCurrentData" property="demoNrmlDeliLimit">
                                  <td> <html:text property="demoNrmlDeliLimit" styleClass="form-control table-input" style="height: 20px;"  name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("IP_COPAY")%>"  name="tempCurrentData" property="demoNrmlDeliLimit">
                                <td> <html:text property="demoNrmlDeliLimit" styleClass="form-control table-input change" style="height: 20px;"  name="tempCurrentData"/></td>
                               </logic:notEqual> --%>
                               
                               <!-- bk -->
                                <logic:equal value="<%=hmPropPlicyDtls.get("NORMAL_DELI_LIMIT")%>"  name="tempCurrentData" property="demoNrmlDeliLimit">
                                  <td> <html:text property="demoNrmlDeliLimit" styleClass="form-control table-input" style="height: 20px;"  name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("NORMAL_DELI_LIMIT")%>"  name="tempCurrentData" property="demoNrmlDeliLimit">
                                <td> <html:text property="demoNrmlDeliLimit" styleClass="form-control table-input change" style="height: 20px;"  name="tempCurrentData"/></td>
                               </logic:notEqual>
                               <!-- end bk -->
                               
                               
                                   
                               <logic:equal value="<%=hmPropPlicyDtls.get("C_SEC_LIMIT")%>"  name="tempCurrentData" property="demoCSecLimit">
                                  <td> <html:text property="demoCSecLimit" styleClass="form-control table-input" style="height: 20px;"  name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("C_SEC_LIMIT")%>"  name="tempCurrentData" property="demoCSecLimit">
                                <td> <html:text property="demoCSecLimit" styleClass="form-control table-input change" style="height: 20px;"  name="tempCurrentData"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("MAT_IP_COPAY")%>"  name="tempCurrentData" property="demoMatIpCopay">
                                  <td> <html:text property="demoMatIpCopay" styleClass="form-control table-input" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("MAT_IP_COPAY")%>"  name="tempCurrentData" property="demoMatIpCopay">
                                <td> <html:text property="demoMatIpCopay" styleClass="form-control table-input change" style="height: 20px;" name="tempCurrentData"/></td>
                               </logic:notEqual>
                                   
                                    </tr>
                                    
                                 <tr style="border-bottom: 2px solid #566573;font-weight: bold;">  
                                 <td style="background-color: #16365C;color: #fff;">Proposed policy</td>                
                              <td><%=hmPropPlicyDtls.get("MBL") %></td>
							 <td>
							 <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<%=hmPropPlicyDtls.get("IPAOC_TOOL_TIP") %>">
							 <%=hmPropPlicyDtls.get("IP_AOC") %>
							 </span>
							 </td>
							 <td><%=hmPropPlicyDtls.get("ROOM_TYPE") %></td>
							 <td><%=hmPropPlicyDtls.get("CHILD_AGE_LIMIT") %></td>
							 <td><%=hmPropPlicyDtls.get("COMPANY_PER_DAY_LIMIT") %></td>
							 <td><%=hmPropPlicyDtls.get("IP_COPAY") %></td>
							 <td><%=hmPropPlicyDtls.get("NORMAL_DELI_LIMIT") %></td>
							 <td><%=hmPropPlicyDtls.get("C_SEC_LIMIT") %></td>
							 <td><%=hmPropPlicyDtls.get("MAT_IP_COPAY") %></td>
                              </tr>
                                  <logic:notEmpty name="frmSwPolicyConfig" property="alProductListTable1">
                                  <logic:iterate id="productData" name="frmSwPolicyConfig" property="alProductListTable1">
                                  <tr>      
                                <td style="background-color: #16365C;color: #fff;border-bottom: 2px solid #566573;font-weight: bold;">
                                <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="productData" property="polNoToolTip"/>">
                              <bean:write name="productData" property="demoPolicyNo"/>
                              
                              </span>
                                
                                </td> 
                                           
                               <%--  <td><bean:write name="productData" property="demoMaximumBenfitLimit"/></td>
                                <td>
                                	  <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="productData" property="ipAocToolTip"/>">
                                <bean:write name="productData" property="demoIpAoc"/>
                                </span>
                                </td>
                                <td><bean:write name="productData" property="demoRoomType"/></td>
                                <td><bean:write name="productData" property="demoChildAgeLimit"/></td>
                                <td><bean:write name="productData" property="demoCompPerDayLimit"/></td>
                                <td><bean:write name="productData" property="demoIpCopay"/></td>
                                <td><bean:write name="productData" property="demoNrmlDeliLimit"/></td>
                                <td><bean:write name="productData" property="demoCSecLimit"/></td>
                                <td><bean:write name="productData" property="demoMatIpCopay"/></td> --%>
                                
                                
                                <!-- bk -->
                                
                                <logic:equal value="<%=hmPropPlicyDtls.get("MBL")%>"  name="productData" property="demoMaximumBenfitLimit">
                                <td><bean:write name="productData" property="demoMaximumBenfitLimit"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("MBL")%>"  name="productData" property="demoMaximumBenfitLimit">
                                <td class="change"><bean:write name="productData" property="demoMaximumBenfitLimit"/></td>
                               </logic:notEqual>
                              
                               <logic:equal value="<%=hmPropPlicyDtls.get("IP_AOC")%>"  name="productData" property="demoIpAoc">
                                <td>
                               	<span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="productData" property="ipAocToolTip"/>">
                                <bean:write name="productData" property="demoIpAoc"/>
                                </span>
                                </td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("IP_AOC")%>"  name="productData" property="demoIpAoc">
                                <td class="change">
                                <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="productData" property="ipAocToolTip"/>">
                                <bean:write name="productData" property="demoIpAoc"/>
                                </span>
                                </td>
                               </logic:notEqual>
                              
                               <logic:equal value="<%=hmPropPlicyDtls.get("ROOM_TYPE")%>"  name="productData" property="demoRoomType">
                                <td><bean:write name="productData" property="demoRoomType"/></td>
                               </logic:equal>                            
                               <logic:notEqual  value="<%=hmPropPlicyDtls.get("ROOM_TYPE")%>"  name="productData" property="demoRoomType">
                                <td class="change"><bean:write name="productData" property="demoRoomType"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("CHILD_AGE_LIMIT") %>"  name="productData" property="demoChildAgeLimit">
                                <td><bean:write name="productData" property="demoChildAgeLimit"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("CHILD_AGE_LIMIT")%>"  name="productData" property="demoChildAgeLimit">
                                <td class="change"><bean:write name="productData" property="demoChildAgeLimit"/></td>
                               </logic:notEqual>
                            
                           <logic:equal value="<%=hmPropPlicyDtls.get("COMPANY_PER_DAY_LIMIT") %>"  name="productData" property="demoCompPerDayLimit">
                                <td><bean:write name="productData" property="demoCompPerDayLimit"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("COMPANY_PER_DAY_LIMIT") %>"  name="productData" property="demoCompPerDayLimit">
                                <td class="change"><bean:write name="productData" property="demoCompPerDayLimit"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("IP_COPAY") %>"  name="productData" property="demoIpCopay">
                                <td><bean:write name="productData" property="demoIpCopay"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("IP_COPAY") %>"  name="productData" property="demoIpCopay">
                                <td class="change"><bean:write name="productData" property="demoIpCopay"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("NORMAL_DELI_LIMIT") %>"  name="productData" property="demoNrmlDeliLimit">
                                <td><bean:write name="productData" property="demoNrmlDeliLimit"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("NORMAL_DELI_LIMIT") %>"  name="productData" property="demoNrmlDeliLimit">
                                <td class="change"><bean:write name="productData" property="demoNrmlDeliLimit"/></td>
                               </logic:notEqual>
                               
                               <logic:equal value="<%=hmPropPlicyDtls.get("C_SEC_LIMIT") %>"  name="productData" property="demoCSecLimit">
                                <td><bean:write name="productData" property="demoCSecLimit"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("C_SEC_LIMIT") %>"  name="productData" property="demoCSecLimit">
                                <td class="change"><bean:write name="productData" property="demoCSecLimit"/></td>
                               </logic:notEqual>
                               
                                <logic:equal value="<%=hmPropPlicyDtls.get("MAT_IP_COPAY") %>"  name="productData" property="demoMatIpCopay">
                                <td><bean:write name="productData" property="demoMatIpCopay"/></td>
                               </logic:equal>                               
                               <logic:notEqual value="<%=hmPropPlicyDtls.get("MAT_IP_COPAY") %>"  name="productData" property="demoMatIpCopay">
                                <td class="change"><bean:write name="productData" property="demoMatIpCopay"/></td>
                               </logic:notEqual>
                                <!-- end bk -->
                              </tr>  
                                  </logic:iterate>
                                  </logic:notEmpty>
                                 </tbody>
                              </table>
                              </div>
                           </div>
                        </div>
                     </div>
                     
                  </div>
                 
               </div>
                 </div>
            </section>
           
          
            
            <section class="tool-content tool-content2">
               <div class="page-body" style="margin-left: 2px;">
               
                  <div class="table-top-item">
                     <div class="clear"></div>
                  </div>
                  <div class="row custom-row">
                  <div class="the-fieldset">
                  <span class="the-legend">Projected per member claims cost - working</span>
          
                     <div class="col-md-10 col-width2" style="margin-left: 5px;margin-top: 5px;">
                        <div class="table-tabs table-tabs2">
                           <table class="table table-hover custom-table" id="table5" >
                              <thead>
                                 <tr class="second-head">
                                    <td></td>
                                    <th scope="col" colspan="2">Coverage details</th>
                                    <th scope="col" colspan="4">Actual/Projected claims cost per covered member (adjusted for trend)</th>
                                    <th scope="col" colspan="4">Credibility (%)</th>
                                 </tr>
                                 <tr>
                                 <td scope="col" id="thCustHead">Policies</td>
                                    <th scope="col">Coverage <br/>start date </th>
                                    <th scope="col">Coverage <br/>end date</th>
                                    <th scope="col">IP</th>
                                    <th scope="col">OP</th>
                                    <th scope="col">MAT IP</th>
                                    <th scope="col">MAT OP</th>
                                    <th scope="col">IP</th>
                                    <th scope="col">OP</th>
                                    <th scope="col">MAT IP</th>
                                    <th scope="col">MAT OP</th>
                                 </tr>
                              </thead>
                              <tbody>
                              
                              <logic:notEmpty name="frmSwPolicyConfig" property="alPastPoliciesTable2">
                                  <logic:iterate id="pastPolicyData" name="frmSwPolicyConfig" property="alPastPoliciesTable2" indexId="arrPosi">
                                  <tr>      
                                <td style="background-color: #16365C;color: #fff;border-bottom: 2px solid #566573;font-weight: bold;">
                                
                                <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="pastPolicyData" property="polNoToolTip"/>">
                              <bean:write name="pastPolicyData" property="demoPolicyNo"/>
                              
                              </span>
                                
                                </td>            
                               
                                <td><bean:write name="pastPolicyData" property="covrgSrartDate"/></td>
                                <td><bean:write name="pastPolicyData" property="covrgEndDate"/></td>
                                <td><bean:write name="pastPolicyData" property="demoCostIp"/></td>
                                <td><bean:write name="pastPolicyData" property="demoCostOp"/></td>
                                <td><bean:write name="pastPolicyData" property="demoCostMatIp"/></td>
                                <td><bean:write name="pastPolicyData" property="demoCostMatOp"/></td>
                                
                                <td>
                                <table class="perscTable">
                                    <tr>
                                    <td>
                                <input type="text" onkeyup="chkCrdbPrc(this,'IP');" name="<%="ipCol"+arrPosi %>" class="form-control table-input" value="<bean:write name="pastPolicyData" property="demoCredIp"/>" <bean:write name="pastPolicyData" property="ipDisableYN"/>>
                                </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                </td>
                                <td>
                                <table class="perscTable">
                                    <tr>
                                    <td>
                                <input type="text" onkeyup="chkCrdbPrc(this,'OP');" name="<%="opCol"+arrPosi %>" class="form-control table-input" value="<bean:write name="pastPolicyData" property="demoCredOp"/>" <bean:write name="pastPolicyData" property="opDisableYN"/>>
                                </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                </td>
                                <td>
                                <table class="perscTable">
                                    <tr>
                                    <td>
                                <input type="text" onkeyup="chkCrdbPrc(this,'MIP');" name="<%="matIpCol"+arrPosi %>" class="form-control table-input" value="<bean:write name="pastPolicyData" property="demoCredMatIp"/>" <bean:write name="pastPolicyData" property="matIpDisableYN"/>>
                               </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                </td>
                                <td>
                                <table class="perscTable">
                                    <tr>
                                    <td>
                                <input type="text"  onkeyup="chkCrdbPrc(this,'MOP');" name="<%="matOpCol"+arrPosi%>" class="form-control table-input" value="<bean:write name="pastPolicyData" property="demoCredMatOp"/>" <bean:write name="pastPolicyData" property="matOpDisableYN"/>>
                               </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                </td>
                                
                                  </tr>  
                                  </logic:iterate>
                                  </logic:notEmpty>
                              
                              <tr style="white-space: nowrap;">
                                     <td style="background-color: #16365C;color: #fff;border-bottom: 2px solid #566573;font-weight: bold;">Current policy experience</td>
                                    
                                     <td><input type="text" name="covrgSrartDate2" onblur="isDateIfNotEmpty(this);" class="form-control table-input" value="<bean:write name="tempCurrentData2" property="covrgSrartDate"/>"></td>
                                    <td><input type="text" name="covrgEndDate2"  onblur="isDateIfNotEmpty(this);" class="form-control table-input" value="<bean:write name="tempCurrentData2" property="covrgEndDate"/>"></td>
                                    <td><input type="text" name="demoCostIp2" onkeyup="isValidNumber(this);" class="form-control table-input" value="<bean:write name="tempCurrentData2" property="demoCostIp"/>"></td>
                                    <td><input type="text" name="demoCostOp2" onkeyup="isValidNumber(this);" class="form-control table-input" value="<bean:write name="tempCurrentData2" property="demoCostOp"/>"></td>
                                    <td><input type="text" name="demoCostMatIp2" onkeyup="isValidNumber(this);" class="form-control table-input" value="<bean:write name="tempCurrentData2" property="demoCostMatIp"/>"></td>
                                    <td><input type="text" name="demoCostMatOp2" onkeyup="isValidNumber(this);" class="form-control table-input" value="<bean:write name="tempCurrentData2" property="demoCostMatOp"/>"></td>
                                    <td>
                                    <table class="perscTable">
                                    <tr>
                                    <td>
                                    <input type="text" onkeyup="chkCrdbPrc(this,'IP');" name="demoCredIp2" class="form-control table-input" value="<bean:write name="tempCurrentData2" property="demoCredIp"/>" <bean:write name="tempCurrentData2" property="ipDisableYN"/>>
                                     </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                    </td>
                                    <td>
                                    <table class="perscTable">
                                    <tr>
                                    <td>
                                    <input type="text" onkeyup="chkCrdbPrc(this,'OP');" name="demoCredOp2" class="form-control table-input" value="<bean:write name="tempCurrentData2" property="demoCredOp"/>" <bean:write name="tempCurrentData2" property="opDisableYN"/>>
                                     </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                    </td>
                                    <td>
                                    <table class="perscTable">
                                    <tr>
                                    <td>
                                    <input type="text"  onkeyup="chkCrdbPrc(this,'MIP');" name="demoCredMatIp2" class="form-control table-input" value="<bean:write name="tempCurrentData2" property="demoCredMatIp"/>" <bean:write name="tempCurrentData2" property="matIpDisableYN"/>>
                                    </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                    </td>
                                    <td>
                                    <table class="perscTable">
                                    <tr>
                                    <td>
                                    <input type="text"  onkeyup="chkCrdbPrc(this,'MOP');" name="demoCredMatOp2" class="form-control table-input" value="<bean:write name="tempCurrentData2" property="demoCredMatOp"/>" <bean:write name="tempCurrentData2" property="matOpDisableYN"/>>
                                    </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                    </td>
                                   </tr>
                                    
                                 <tr style="border-bottom: 2px solid #566573;font-weight: bold;">  
                                 <td style="background-color: #16365C;color: #fff;">Pricing tool Output/Book rate</td>                
                               <td><bean:write name="tempPropData2" property="covrgSrartDate"/></td>
                                <td><bean:write name="tempPropData2" property="covrgEndDate"/></td>
                                <td><bean:write name="tempPropData2" property="demoCostIp"/></td>
                                <td><bean:write name="tempPropData2" property="demoCostOp"/></td>
                                <td><bean:write name="tempPropData2" property="demoCostMatIp"/></td>
                                <td><bean:write name="tempPropData2" property="demoCostMatOp"/></td>
                                
                                <td>
                                <table class="perscTable">
                                    <tr>
                                    <td>
                                <input style="min-width: 60px;" onkeyup="chkCrdbPrc(this,'IP');" type="text" name="<%="ip2Col" %>" class="form-control table-input" value="<bean:write name="tempPropData2" property="demoCredIp"/>" <bean:write name="tempPropData2" property="ipDisableYN"/>>
                                </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                </td>
                                <td>
                                <table class="perscTable">
                                    <tr>
                                    <td>
                                <input style="min-width: 60px;" onkeyup="chkCrdbPrc(this,'OP');" type="text" name="<%="op2Col" %>" class="form-control table-input" value="<bean:write name="tempPropData2" property="demoCredOp"/>" <bean:write name="tempPropData2" property="opDisableYN"/>>
                                </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                </td>
                                <td>
                                <table class="perscTable">
                                    <tr>
                                    <td>
                                <input style="min-width: 60px;"  onkeyup="chkCrdbPrc(this,'MIP');" type="text" name="matIp2Col" class="form-control table-input" value="<bean:write name="tempPropData2" property="demoCredMatIp"/>" <bean:write name="tempPropData2" property="matIpDisableYN"/>>
                                </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                </td>
                                <td>
                                <table class="perscTable">
                                    <tr>
                                    <td>
                                <input style="min-width: 60px;"  onkeyup="chkCrdbPrc(this,'MOP');" type="text" name="matOp2Col" class="form-control table-input" value="<bean:write name="tempPropData2" property="demoCredMatOp"/>" <bean:write name="tempPropData2" property="matOpDisableYN"/>>
                               </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                </td>
                             
                              </tr>
                              
                              <logic:notEmpty name="frmSwPolicyConfig" property="alProductListTable2">
                              <logic:iterate id="productData" name="frmSwPolicyConfig" property="alProductListTable2" indexId="indexSize">
                                  <tr>      
                                <td style="background-color: #16365C;color: #fff;border-top: 2px solid #566573;font-weight: bold;">
                                
                                 <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="<bean:write name="productData" property="polNoToolTip"/>">
                              <bean:write name="productData" property="demoPolicyNo"/>
                              
                              </span>
                                
                                </td>            
                                <td colspan="2"><bean:write name="productData" property="productDate"/></td>
                                <td><bean:write name="productData" property="demoCostIp"/></td>
                                <td><bean:write name="productData" property="demoCostOp"/></td>
                                <td><bean:write name="productData" property="demoCostMatIp"/></td>
                                <td><bean:write name="productData" property="demoCostMatOp"/></td>
                                
                                <%
                                  if(TTKCommon.isAuthorized(request,"Edit")) {
                                   %>
                                <logic:equal value="0" name="indexSize">
                                <td style="text-align:center; vertical-align:top;margin-top:5px;" colspan="4" rowspan="<bean:write name="frmSwPolicyConfig" property="table2ProdRowSize"/>" class="btn-td two-row"><a href="#" onclick="calculateTable2();" style="margin-top:10px;" class="btn btn-primary btn-sm row-btn">Calculate</a></td>
                              </logic:equal>
                                <%
                                   }
                                 %>
                              
                              </tr>  
                            </logic:iterate>
                            </logic:notEmpty>
                                 <logic:empty name="frmSwPolicyConfig" property="alProductListTable2">
                                <tr>      
                                <td style="background-color: #16365C;color: #fff;border-top: 2px solid #566573;font-weight: bold;">
                                
                                 <span class="tooltip-item" data-toggle="tooltip" data-placement="bottom" title="">
                             
                              
                              </span>
                                
                                </td>            
                                <td colspan="2"></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                
                                <td style="text-align:center; vertical-align:top;margin-top:5px;" colspan="4" rowspan="<bean:write name="frmSwPolicyConfig" property="table2ProdRowSize"/>" class="btn-td two-row"><a href="#" onclick="calculateTable2();" style="margin-top:10px;" class="btn btn-primary btn-sm row-btn">Calculate</a></td>
                             
                              </tr>  
                                </logic:empty>
                              <logic:notEmpty name="frmSwPolicyConfig" property="alTable3Values">                                
			            
			                 <logic:iterate id="tempTable3Values" name="frmSwPolicyConfig" property="alTable3Values" indexId="rowID">
			                      <%HashMap<String,String> hmTable3Values=(HashMap<String,String>)tempTable3Values; %>
			                     
			                    <%if("PCPM".equals(hmTable3Values.get("PW_CODE"))||"FCPM".equals(hmTable3Values.get("PW_CODE"))){ %>
			                 <tr style="border-top: 2px solid #566573;font-weight: bold;">
			                 
                                    <td colspan="2" align="center"><%=hmTable3Values.get("PW_NAME") %></td>
                                    <td></td>
                                    <td><%=hmTable3Values.get("PW_IP") %></td>
                                    <td><%=hmTable3Values.get("PW_OP") %></td>
                                    <td><%=hmTable3Values.get("PW_MAT_IP") %></td>
                                    <td><%=hmTable3Values.get("PW_MAT_OP") %></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                     <td></td>
                                 </tr>
			                 <%}else if("COMM".equals(hmTable3Values.get("PW_CODE"))){ %>
			                  <tr>
                                    <td colspan="2" align="center">Comments</td>
                                    <td></td>
                                    <td colspan="4"> 
                                <textarea class="form-control" name="table3Commands" style="margin-left: 0px;margin-top: 0px;left: 0px;top: 0px;text-align: left;padding-left: 0px;font-size: 11px;"  rows="2"><%=hmTable3Values.get("PW_NAME")%></textarea>                                    
                                    </td>
                                    <%
                                       if(TTKCommon.isAuthorized(request,"Edit")) {
                                    %>
                                    <td colspan="4" class="btn-td" style="text-align:center;"><a onclick="calculateTable3();" href="#" class="btn btn-primary btn-sm row-btn">Calculate</a></td>
                                    <%
                                        }
                                     %>
                                 </tr>
			                 <%}else{%>
			                 
			                  <tr>
                                    <td colspan="2" align="center"><%=hmTable3Values.get("PW_NAME") %></td>
                                    <td></td>
                                    <td>
                                    <table class="perscTable">
                                    <tr>
                                    <td>
                                       <input onkeyup="checkPrcnt200(this,'<%=hmTable3Values.get("PW_NAME")%> for Inpatient');" style="min-width: 60px;" type="text" name="<%="tth1Value"+rowID%>" class="form-control table-input" value="<%=hmTable3Values.get("PW_IP")%>" placeholder="">
                                    </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                 
                                  
                                    </td>
                                    <td>
                                     <table class="perscTable">
                                    <tr>
                                    <td>
                                    <input onkeyup="checkPrcnt200(this,'<%=hmTable3Values.get("PW_NAME") %> for Outpatient');" style="min-width: 60px;" type="text" name="<%="tth2Value"+rowID%>" class="form-control table-input" value="<%=hmTable3Values.get("PW_OP")%>" placeholder="">
                                     </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                    </td>
                                    <td>
                                    <table class="perscTable">
                                    <tr>
                                    <td>
                                    <input onkeyup="checkPrcnt200(this,'<%=hmTable3Values.get("PW_NAME") %> for Maternity Inpatient');" style="min-width: 60px;" type="text" name="<%="tth3Value"+rowID%>" class="form-control table-input" value="<%=hmTable3Values.get("PW_MAT_IP")%>" placeholder="">
                                     </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                    </td>
                                    <td>
                                   <table class="perscTable">
                                    <tr>
                                    <td>
                                    <input onkeyup="checkPrcnt200(this,'<%=hmTable3Values.get("PW_NAME") %> for Maternity Outpatient');" style="min-width: 60px;" type="text" name="<%="tth4Value"+rowID%>" class="form-control table-input" value="<%=hmTable3Values.get("PW_MAT_OP")%>" placeholder="">
                                    </td>
                                    <td>%</td>
                                    </tr>
                                    </table>
                                    </td>
                                   
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                     <td></td>
                                 </tr>
			                 
			                 <%}%>
			                 
			                  </logic:iterate>
                                
                                </logic:notEmpty>
            
                                 
                              </tbody>
                           </table>
                        </div>
                     </div>
                  </div>
                  </div>
                  </div>
            </section>
            
            
            </div>
            
            <div class="clear"></div>
         </div>
      </div>
      <!-- scripts -->
	<div class="btn-action" style="text-align: center;width: 100%;">
            <div style="text-align: center;width: 100%;">
            <a href="#" class="btn btn-primary btn-sm btn-custom" onclick="onProceedProposal();">Proceed</a>
            <a href="#" class="btn btn-btn-outline-secondary btn-custom" onclick="onBack();" style="margin-right: 5px;">Back</a>
           </div>
           
            </div>
            
            <div>
    <span style="font-weight: bold;margin-top: 3px;">Note:-</span>
	
 <p style="line-height:0.1;margin-top: 5px;">
 1. Maternity cost is applicable only to maternity eligible covered members.
 </p>
  <p style="line-height:0.1;">
2.<font color="red"> Current policy experience numbers is an input for the user and should be adjusted for trend and IBNR. These can be left blank with zero credibility if not available.</font>
 </p>
 <p style="line-height:0.1">
3. The information generated by the pricing tool is an estimate based on certain pre-determined presumptions on historic data. The results should be interpreted by
 </p>
 <p style="line-height:0.1">
  &nbsp;&nbsp;&nbsp;&nbsp;an experienced  medical insurance underwriter and/or an actuary with the  requisite skills and should always be reviewed for reasonableness.
 </p>
 <p style="line-height:0.1">
4. Nationality category with less than 1% lives is not shown in the table above; this also means for some scenarios nationality distribution may not sum up to exact 100%.
</p>
 <p style="line-height:0.1">
5.<font color="red"> Variations in benefits and key demographics in the past policy years and any existing comparable  products chosen (when compared with the proposed policy benefits)</font>
</p>
<p style="line-height:0.1">
   <font color="red">&nbsp;&nbsp;&nbsp;&nbsp;are highlighted in red in the table above.</font>
</p>
 </div><br>
 <!-- bk -->
  <fieldSet>
      
      			 <table align="center" class="buttonsContainer" border="1" cellspacing="0" cellpadding="0">
			
  				<tr>
  				<td class="formLabel" >UAE  = All UAE</td>
  				<td class="formLabel" >Govt. = Government</td>
  				<td class="formLabel" >Cent pvt = Central Private hospital</td>
  				</tr>
  				<tr>
  				<td class="formLabel" >UAE A = All UAE excluding Abu Dhabi and Al Ain</td>
  				<td class="formLabel" >C = Clinics</td>
  				<td class="formLabel" >SP. Med Care = Specialised Medical Care hospital</td>
  				</tr>
  				<tr>
  				<td class="formLabel" >ISC = All ISC </td>
  				<td class="formLabel" >P = Pharmacy</td>
  				<td class="formLabel" >NMC DEIRA = NMC Deira hospital </td>
  				</tr>
  				<tr>
  				<td class="formLabel" >ISC A = All ISC excluding Maldives</td>
  				<td class="formLabel" >D = Diagnostic centre</td>
  				<td class="formLabel" >Zahr = Zahrawi hospital </td>
  				
  				</tr>
  				<tr>
  				<td class="formLabel" >SEA = All SEA</td>
  				<td class="formLabel" >S = Specialist physician clinic</td>
  				<td class="formLabel" >NMC(S) = NMC Speciality hospital </td>
  				
  				</tr>
  				<tr>
  				<td class="formLabel" >SEA A = All SEA excluding Timor-Leste, Cambodia</td>
  			     <td class="formLabel" >LLH = LLH hospital </td>
  			     <td class="formLabel" >BELH(E) = Belhoul European hospital </td>
  				
  				</tr>
  				<tr>
  				<td class="formLabel" >WW = Worldwide</td>
  					<td class="formLabel" >Tawm = Tawam hospital</td>
  					<td class="formLabel" >BELH(S) = Belhoul Speciality hospital </td>
  				
  				</tr>
  				<tr>
  				<td class="formLabel" >GCC = Gulf countries</td>
  				<td class="formLabel" >IR = Iranian hospital</td>
  				<td class="formLabel" >THU Al-Nah = Thumbay Al Nahda hospital </td>
  				</tr>
  				
  				<!-- bk -->
  				<tr>
  				<td class="formLabel" >N = Network</td>
  				<td class="formLabel" >CJA = Cedar Jebel Ali hospital</td>
  				<td class="formLabel" >Skh Khal = Sheikh Khalifa medical</td>
  				</tr>
  				
  				<tr>
  				<td class="formLabel" >NN = Non Network</td>
  				<td class="formLabel" >Nat = National hospital</td>
  				<td class="formLabel" >Reimb. = Reimbursement</td>
  				</tr>
  				
  				<tr>
  				<td class="formLabel" >HOS = Hospital</td>
  				<td class="formLabel" >GMC = GMC hospital</td>
  				<td class="formLabel" >-</td>
  				</tr>
  			
			</table>
      
      </fieldSet>  
 <!-- end bk -->
 
	</div>
	<!-- E N D : Buttons -->
	 <html:hidden  name="frmSwPolicyConfig" property="finaldataSeqID"/> 
	 <html:hidden  name="frmSwPolicyConfig" property="calCPM_FlagYN"/> 
	 <html:hidden  name="frmSwPolicyConfig" styleId="groupProfileSeqID" property="lngGroupProfileSeqID"/> 
	  
	 
	<input type="hidden" name="mode"> 

	<html:hidden property="insClm" /> 
    <html:hidden property="insPat" />  
	<html:hidden property="caption" />  
	 <INPUT TYPE="hidden" NAME="totaliterate" id="totaliterate">
	 
	 
 <INPUT TYPE="hidden" NAME="tab" VALUE="">




 <script>
         $('#table1 tr').each(function(i,el) {
         var hgt = $(this).height();
         $('#table2 tr').eq(i).height(hgt);
         });  
      </script> 
      <script>
         $('#table4 tr').each(function(i,el) {
         var hgt = $(this).height();
         $('#table3 tr').eq(i).height(hgt);
         });  
      </script>
      <script>
         $('#table5 tr').each(function(i,el) {
               var hgt = $(this).height();
               $('#table6 tr').eq(i).height(hgt);
               });  
               
            
      </script>
      <script>
         $(document).ready(function(){
         $('ul.tabs li').click(function(){
         var tab_id = $(this).attr('data-tab');
         
         $('ul.tabs li').removeClass('current');
         $('.tab-content').removeClass('current');
         
         $(this).addClass('current');
         $("#"+tab_id).addClass('current');
         })
         
         })
           
      </script>
      <script>
     
         $(document).ready(function(){
         
         $('.table-tabs2 ul.tabPrc li').click(function(){
         var tab_id = $(this).attr('data-tab');
         
         $('.table-tabs2 ul.tabPrc li').removeClass('current');
         $('.table-tabs2 .tab-contents').removeClass('current');
         
         $(this).addClass('current');
         $("#"+tab_id).addClass('current');
         })
         
         })
      </script>
      <script>
         $(document).ready(function(){
         $('ul.page-tabs li').click(function(){
         var tab_id = $(this).attr('data-tab');
         
         $('ul.page-tabs li').removeClass('current');
         $('.page-tab-content').removeClass('current');
         
         $(this).addClass('current');
         $("#"+tab_id).addClass('current');
         })
         
         })
      </script>
      <script>
         $(document).ready(function(){
         
         $('ul.tab4 li').click(function(){
         var tab_id = $(this).attr('data-tab');
         
         $('ul.tab4 li').removeClass('current');
         $('.tab-content4').removeClass('current');
         
         $(this).addClass('current');
         $("#"+tab_id).addClass('current');
         })
         
         })
      </script>
      <script>
         $(function () {
         $('[data-toggle="tooltip"]').tooltip()
         $(selector).tooltip({container:'body'});
         })
      </script>
      
      <input type="hidden" name="currentTab" value="<%=TTKCommon.checkNull(request.getParameter("currentTab"))%>" id="currentTabID">
</html:form>
<form name="tf">
 
	<input type="hidden" name="mode"> 
	<input type="hidden" name="tab"> 
</form>
<!-- E N D : Content/Form Area -->