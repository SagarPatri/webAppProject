function setUserType()
{ 
 //document.frmLogin.cboUserType.focus();
 //clearDefLbls();
 }
function  showInputs()
{ 
  sCriteria = document.frmLogin.cboUserType.value;		
  switch(sCriteria){
  case "InsCom" :
		// show for insurance company	
		//hide unwanted controls.set them to null and disable them
				
		document.frmLogin.txtInput1.style.visibility="visible";
		document.frmLogin.txtInput1.value="";
		document.frmLogin.txtInput1.disabled=true;
		document.frmLogin.txtInput2.style.visibility="visible";
		document.frmLogin.txtInput2.value="";
		document.frmLogin.txtInput2.disabled=true;
		document.frmLogin.txtInput3.style.visibility="hidden";
		document.frmLogin.txtInput3.value="";
		document.frmLogin.txtInput3.disabled=true;
		
		document.frmLogin.pwdInput2.style.visibility="hidden";
		document.frmLogin.pwdInput2.value="";
		document.frmLogin.pwdInput2.disabled=true;
		document.frmLogin.pwdInput3.style.visibility="hidden";
		document.frmLogin.pwdInput3.value="";
		document.frmLogin.pwdInput3.disabled=true;

		clearDefLbls();
		document.frmLogin.btnLogin.style.visibility="visible";
		document.frmLogin.btnReset.style.visibility="visible";
		
		document.frmLogin.btnLogin.disabled=true;
		document.frmLogin.btnReset.disabled=true;
		
		// enable login button and textboxes
		document.frmLogin.btnLogin.disabled=false;
		document.frmLogin.btnReset.disabled=false;
		document.frmLogin.txtInput1.disabled=false;
		document.frmLogin.txtInput2.disabled=false;
		// set labels
		
			lblInput1.innerText="UserName"; 
			lblInput2.innerText="Password";


		// set focus ..
		document.frmLogin.txtInput1.focus();
		
        break;
        

  case "CompLog" :
		// show for insurance company	
		//hide unwanted controls.set them to null and disable them
				
		document.frmLogin.txtInput1.style.visibility="visible";
		document.frmLogin.txtInput1.value="";
		document.frmLogin.txtInput1.disabled=true;
		document.frmLogin.txtInput2.style.visibility="hidden";
		document.frmLogin.txtInput2.value="";
		document.frmLogin.txtInput2.disabled=true;
		document.frmLogin.txtInput3.style.visibility="hidden";
		document.frmLogin.txtInput3.value="";
		document.frmLogin.txtInput3.disabled=true;
		
			
		document.frmLogin.pwdInput2.style.visibility="visible";
		document.frmLogin.pwdInput2.value="";
		document.frmLogin.pwdInput2.disabled=true;
		document.frmLogin.pwdInput3.style.visibility="hidden";
		document.frmLogin.pwdInput3.value="";
		document.frmLogin.pwdInput3.disabled=true;

		
		clearDefLbls();

		document.frmLogin.btnLogin.style.visibility="visible";
		document.frmLogin.btnReset.style.visibility="visible";

		document.frmLogin.btnLogin.disabled=true;
		document.frmLogin.btnReset.disabled=true;
		
		// enable login button and textboxes
		document.frmLogin.btnLogin.disabled=false;
		document.frmLogin.btnReset.disabled=false;
		document.frmLogin.txtInput1.disabled=false;
		document.frmLogin.txtInput2.width=0;
		document.frmLogin.txtInput2.height=0;
		document.frmLogin.pwdInput2.disabled=false;
		// set labels
		
			lblInput1.innerText="UserName"; 
			lblInput2.innerText="Password";


		// set focus ..
		document.frmLogin.txtInput1.focus();
		
        break;
        
  case "GrpPol" :
  		// show for Group Policies
		//hide unwanted controls.set them to null and disable them
		//hide unwanted controls.set them to null and disable them
				
		document.frmLogin.txtInput1.style.visibility="visible";
		document.frmLogin.txtInput1.value="";
		document.frmLogin.txtInput1.disabled=true;
		document.frmLogin.txtInput2.style.visibility="visible";
		document.frmLogin.txtInput2.value="";
		document.frmLogin.txtInput2.disabled=true;
		document.frmLogin.txtInput3.style.visibility="visible";
		document.frmLogin.txtInput3.value="";
		document.frmLogin.txtInput3.disabled=true;
		
		

		clearDefLbls();

		document.frmLogin.btnLogin.style.visibility="visible";
		document.frmLogin.btnReset.style.visibility="visible";

		document.frmLogin.btnLogin.disabled=true;
		document.frmLogin.btnReset.disabled=true;
		
		
		// enable login button and textboxes
		document.frmLogin.btnLogin.disabled=false;
		document.frmLogin.btnReset.disabled=false;
		document.frmLogin.txtInput1.disabled=false;
		document.frmLogin.txtInput2.disabled=false;
		document.frmLogin.txtInput3.disabled=false;
		// set labels
		
			lblInput1.innerText="Policy Number"; 
			lblInput2.innerText="TTK ID";
			lblInput3.innerText="Password";


		// set focus ..
		document.frmLogin.txtInput1.focus();
		
    
        break;

  case "CitPol" :
		// show for citibank policy holder	
		//hide unwanted controls.set them to null and disable them

		document.frmLogin.txtInput1.style.visibility="visible";
		document.frmLogin.txtInput1.value="";
		document.frmLogin.txtInput1.disabled=true;
		document.frmLogin.txtInput2.style.visibility="visible";
		document.frmLogin.txtInput2.value="";
		document.frmLogin.txtInput2.disabled=true;
		document.frmLogin.txtInput3.style.visibility="visible";
		document.frmLogin.txtInput3.value="";
		document.frmLogin.txtInput3.disabled=true;
		clearDefLbls();

		document.frmLogin.btnLogin.style.visibility="visible";
		document.frmLogin.btnReset.style.visibility="visible";

		document.frmLogin.btnLogin.disabled=true;
		document.frmLogin.btnReset.disabled=true;
		
		
		// enable login button and textboxes
		document.frmLogin.btnLogin.disabled=false;
		document.frmLogin.btnReset.disabled=false;
		document.frmLogin.txtInput1.disabled=false;
		document.frmLogin.txtInput2.disabled=false;
		document.frmLogin.txtInput3.disabled=false;
		// set labels
		
			lblInput1.innerText="Cert No"; 
			lblInput2.innerText="DOB";
			lblInput3.innerText="TTK ID";

		// set focus ..
		document.frmLogin.txtInput1.focus();
		   
        break;
    

  case "IndPol" :
		// show for individual policy holder
		document.frmLogin.txtInput1.style.visibility="visible";
		document.frmLogin.txtInput1.value="";
		document.frmLogin.txtInput1.disabled=true;
		document.frmLogin.txtInput2.style.visibility="visible";
		document.frmLogin.txtInput2.value="";
		document.frmLogin.txtInput2.disabled=true;
		document.frmLogin.txtInput3.style.visibility="hidden";
		document.frmLogin.txtInput3.value="";
		document.frmLogin.txtInput3.disabled=true;
		clearDefLbls();

		document.frmLogin.btnLogin.style.visibility="visible";
		document.frmLogin.btnReset.style.visibility="visible";

		document.frmLogin.btnLogin.disabled=true;
		document.frmLogin.btnReset.disabled=true;
		
		
		// enable login button and textboxes
		document.frmLogin.btnLogin.disabled=false;
		document.frmLogin.btnReset.disabled=false;
		document.frmLogin.txtInput1.disabled=false;
		document.frmLogin.txtInput2.disabled=false;
		// set labels
		
			lblInput1.innerText="Policy No"; 
			lblInput2.innerText="TTK ID";

		// set focus ..
		document.frmLogin.txtInput1.focus();

        break;
    
  default :

		hideAllControls();	
	
		clearDefLbls(); 

		break;
		
   }

 // show 
 }
function clearDefLbls()
{
 lblInput1.innerText=""; 
 lblInput2.innerText="";
 lblInput3.innerText="";
  }

function clearControls()
{
		//disable all text controls
		if (document.frmLogin.txtInput1.style.visibility=="visible") document.frmLogin.txtInput1.value="";
		if (document.frmLogin.txtInput2.style.visibility=="visible") document.frmLogin.txtInput2.value="";
		if (document.frmLogin.txtInput3.style.visibility=="visible") document.frmLogin.txtInput3.value="";
		
 }
function hideAllControls()
{ 
  		document.frmLogin.txtInput1.style.visibility="hidden";
		document.frmLogin.txtInput1.value="";
		document.frmLogin.txtInput1.disabled=true;
		document.frmLogin.txtInput2.style.visibility="hidden";
		document.frmLogin.txtInput2.value="";
		document.frmLogin.txtInput2.disabled=true;
		document.frmLogin.txtInput3.style.visibility="hidden";
		document.frmLogin.txtInput3.value="";
		document.frmLogin.txtInput3.disabled=true;
		
		document.frmLogin.pwdInput2.style.visibility="hidden";
		document.frmLogin.pwdInput2.value="";
		document.frmLogin.pwdInput2.disabled=true;
		document.frmLogin.pwdInput3.style.visibility="hidden";
		document.frmLogin.pwdInput3.value="";
		document.frmLogin.pwdInput3.disabled=true;
		
	
		document.frmLogin.btnLogin.disabled=true;
		document.frmLogin.btnReset.disabled=true;
		
		document.frmLogin.btnLogin.style.visibility="hidden";
		document.frmLogin.btnReset.style.visibility="hidden";

		
 }
 
// validation on clicking login button
function clearControls()
{
		//disable all text controls
		if (document.frmLogin.txtInput1.style.visibility=="visible") document.frmLogin.txtInput1.value="";
		if (document.frmLogin.txtInput2.style.visibility=="visible") document.frmLogin.txtInput2.value="";
		if (document.frmLogin.txtInput3.style.visibility=="visible") document.frmLogin.txtInput3.value="";
		if (document.frmLogin.pwdInput2.style.visibility=="visible") document.frmLogin.pwdInput2.value="";
		if (document.frmLogin.pwdInput3.style.visibility=="visible") document.frmLogin.pwdInput3.value="";
		
 }
 