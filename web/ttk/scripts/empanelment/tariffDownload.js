function onBackTariff()
{
	document.forms[1].mode.value="doDefaultEmpnlTariff";
    document.forms[1].action = "/TariffActionEmpanelment.do";
    document.forms[1].submit();
}


function onGenerateProviderTariffListReport()										
{
			
			/*if(document.forms[1].payerCode.value=="")
			{
				alert("Please Select Payer");
				document.forms[1].payerCode.focus();
				return false;
			}*/
			/*else if(document.forms[1].networkType.value=="")
			{
				alert("Please Select Network Type");
				document.forms[1].networkType.focus();
				return false;
			}*/
			
			var vCheckedValues="";
			
			var flag=true;
			var recievedChecks=document.forms[1].chkopt;			
			var recievedCheckBoxes = recievedChecks.length ? recievedChecks : [recievedChecks];
			for(var i=0;i<recievedCheckBoxes.length;i++){
	            
				
				if(recievedCheckBoxes[i].checked){
					vCheckedValues+=recievedCheckBoxes[i].value+"|";
	                }			
			    }
		    
		   if(!(vCheckedValues.length>0)){
				
				if(!confirm("Are you sure you want to Download all Records?"))return;
			}
					
						var networkType	= document.forms[1].networkType.value;
						var partmeter = "?mode=doGenerateProviderTariff&networkType="+networkType+"&chkBoxValues="+vCheckedValues;
						var openPage = "/TariffDownLoadEmpanelmentAction.do"+partmeter; 
						var w = screen.availWidth - 10;
						var h = screen.availHeight - 99;
						var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
						window.open(openPage,'',features);
		
}
