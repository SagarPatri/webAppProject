function edit(rownum)
{
    document.forms[1].mode.value="DisplayPrevRecord";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/RuleDataAction.do";
    document.forms[1].submit();
}//end of edit(rownum)