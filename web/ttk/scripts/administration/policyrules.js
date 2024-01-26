function edit(rownum)
{
    document.forms[1].mode.value="DisplayPrevRecord";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/DefinePolicyRuleAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function removeSingleQuotes()
{
	
		var NumElements=document.forms[1].elements.length;
		for(k=0; k<NumElements;k++)
		{
			if(document.forms[1].elements[k].type=="text") 
			{
			// checking whether the textbox contains any singlequote
			var txtValue = document.forms[1].elements[k].value;
			if(txtValue.indexOf("'") != -1)
			return false;
			}
		}
return true;
}