package com.ttk.common;


public class RulesFieldsLookup {

	private static String strSelectYNValues[]=null;
	private static String strSelectYNLabels[]=null;
	
	private static String strSelectYNFValues[]=null;
	private static String strSelectYNFLabels[]=null;
	
	private static String strServiceLimitType3Values[]=null;
	private static String strServiceLimitType3Labels[]=null;
	
	private static String strConTypeRadio123Values[]=null;
	private static String strstrConTypeRadio123Labels[]=null;
	
	private static String strConTypeRadio3Values[]=null;
	private static String strstrConTypeRadio3Labels[]=null;
	
	private static String strYearsMondsDatesValues[]=null;
	private static String strYearsMondsDatesLabels[]=null;
	
	private static String strSelectMinMaxValues[]=null;
	private static String strSelectMinMaxLabels[]=null;
	private static String strCopayDeductibleTypeValues[]=null;
	private static String strCopayDeductibleTypeLabels[]=null;
	
	
	private static String strMaxMinValues[]=null;
	private static String strMaxMinLabels[]=null;
	
	
	static{
		strSelectYNValues=new String[]{"Y","N"};
        strSelectYNLabels=new String[]{"Yes","No"};
        
		 strSelectYNFValues=new String[]{"","Y","N"};
         strSelectYNFLabels=new String[]{"select from list","Yes","No"};
         
         strServiceLimitType3Values=new String[]{"PC","PD","PP"};
         strServiceLimitType3Labels=new String[]{"Per Claim","Per Day","Per Policy Period"};
         
         strConTypeRadio123Values=new String[]{"1","2","3"};
         strstrConTypeRadio123Labels=new String[]{"Pay","Don't Pay","Pay Conditionally"};
         
         strConTypeRadio3Values=new String[]{"3"};
         strstrConTypeRadio3Labels=new String[]{"Pay Conditionally"};
         
         strYearsMondsDatesValues=new String[]{"DD","MM","YY"};
         strYearsMondsDatesLabels=new String[]{"Days","Months","Years"};
         
         strSelectMinMaxValues=new String[]{"MIN","MAX"};
         strSelectMinMaxLabels=new String[]{"MIN","MAX"};
         
         
         //below added by govind
         strCopayDeductibleTypeValues=new String[]{"","DA","RA"};
         strCopayDeductibleTypeLabels=new String[]{"select from list","Discounted Gross Amount","Requested Amount"};
         
         strMaxMinValues=new String[]{"MIN","MAX"};
         strMaxMinLabels=new String[]{"MIN","MAX"};
         // above added by govind
	}
	public static String getSelectBox(String strIdentifier,String strName,String strClass,String strID,String selectvalue,String strJsCall){
		StringBuilder selBoxBuild=new StringBuilder();
		
		selBoxBuild.append("<select name=\"").append(strName).append("\"");
		if(strClass==null||strClass.length()<1)
			strClass="selectBoxForRule";
		
	selBoxBuild.append(" class=\"").append(strClass).append("\"");
		
	selBoxBuild.append(" ");
	selBoxBuild.append(strJsCall);
	selBoxBuild.append(" ");
	
		if(strID==null||strID.length()<1)
			selBoxBuild.append(">");	
		else
		selBoxBuild.append(" id=\"").append(strID).append("\">");
		
		String [][]populationFields=getPopulateFields(strIdentifier);
		
		String strValues[]=populationFields[0];
		String strLabels[]=populationFields[1];
		
		if(selectvalue==null)selectvalue="";
		for(int i=0;i<strValues.length;i++){
			if(selectvalue.equals(strValues[i]))
				selBoxBuild.append("<option value=\""+strValues[i]+"\" selected>"+strLabels[i]+"</option>");
			else 
				selBoxBuild.append("<option value=\""+strValues[i]+"\">"+strLabels[i]+"</option>");
		}
		
		
		selBoxBuild.append("</select>");
		return selBoxBuild.toString();
	
	}
	public static String populateSelectBox(String strName,String strClass,String strID,String selectvalue,String strParamValues,String strParamLabels,String strJsCall){
		StringBuilder selBoxBuild=new StringBuilder();
		//<select name=\"\" class=\"selectBox\"   id=\"\"><option value=\"Y\" selected>Yes</option><option value=\"N\">No</option></select>";
		
		selBoxBuild.append("<select name=\"").append(strName).append("\"");
		if(strClass==null||strClass.length()<1)
			strClass="selectBoxForRule";
		
	selBoxBuild.append(" class=\"").append(strClass).append("\"");
	selBoxBuild.append(" ");
	selBoxBuild.append(strJsCall);
	selBoxBuild.append(" ");
		if(strID==null||strID.length()<1)
			selBoxBuild.append(">");	
		else
		selBoxBuild.append(" id=\"").append(strID).append("\">");
		
		if(strParamValues==null||strParamLabels==null){
			strParamValues="";
			strParamLabels="";
		}
		String strValues[]=strParamValues.split("[,]");
		String strLabels[]=strParamLabels.split("[,]");
		if(strValues.length==strLabels.length){
		for(int i=0;i<strValues.length;i++){
			if(selectvalue.equals(strValues[i]))
				selBoxBuild.append("<option value=\""+strValues[i]+"\" selected>"+strLabels[i]+"</option>");
			else 
				selBoxBuild.append("<option value=\""+strValues[i]+"\">"+strLabels[i]+"</option>");
		}
		
	}
		selBoxBuild.append("</select>");
		return selBoxBuild.toString();
	
	}
	
	public static String getConditionRadioBoxes(String strIdentifier,String strSbID,String selectvalue){
		StringBuilder radioBoxesBuild=new StringBuilder();
		String strName="SB"+strSbID;
		String [][]populationFields=getPopulateFields(strIdentifier);		
		String strValues[]=populationFields[0];
		String strLabels[]=populationFields[1];
		
		if(selectvalue==null||selectvalue.length()<1)selectvalue="2";
		
		
		if(strValues!=null&&strLabels!=null){
		
		
		for(int i=0;i<strValues.length;i++){
			
			if(selectvalue.equals(strValues[i])){
				radioBoxesBuild.append("<input type=\"radio\" checked  name=\""+strName+"\" id=\""+(strName+"ID"+strValues[i])+"\" value=\""+strValues[i]+"\"  class=\"\"   onclick=\"showHideConditions(this,'"+strName+"','"+strSbID+"')\"/>");
				radioBoxesBuild.append("<label for=\""+(strName+"ID"+strValues[i])+"\">"+strLabels[i]+"</label> &nbsp;&nbsp; ");
			}else {
				radioBoxesBuild.append("<input type=\"radio\"  name=\""+strName+"\" id=\""+(strName+"ID"+strValues[i])+"\" value=\""+strValues[i]+"\"  class=\"\"   onclick=\"showHideConditions(this,'"+strName+"','"+strSbID+"')\"/>");
				radioBoxesBuild.append("<label for=\""+(strName+"ID"+strValues[i])+"\">"+strLabels[i]+"</label> &nbsp;&nbsp;");
			}
		}
		}//	if(strValues!=null&&strLabels!=null){
		radioBoxesBuild.append("<input type=\"hidden\"  name=\"sRadioNames\"");
		radioBoxesBuild.append(" value=\"").append(strSbID).append("\"");
		radioBoxesBuild.append(" id=\"RN").append(strSbID).append("\"").append("/>");
		radioBoxesBuild.append("<input type=\"hidden\"  name=\"sRadioValues\"");
		radioBoxesBuild.append(" value=\"").append(selectvalue).append("\"");
		radioBoxesBuild.append(" id=\"RV").append(strSbID).append("\"").append("/>");		
		return radioBoxesBuild.toString();
	
	}
	
	
	private static String[][] getPopulateFields(String identifier){
		
		String fields[][]=new String[2][];
		if("SelectYNF".equals(identifier)){
		fields[0]=strSelectYNFValues;
		fields[1]=strSelectYNFLabels;
		}
		else if("SelectYN".equals(identifier)){
			fields[0]=strSelectYNValues;
			fields[1]=strSelectYNLabels;
		}else if("ServiceLimitType3".equals(identifier)){
				fields[0]=strServiceLimitType3Values;
				fields[1]=strServiceLimitType3Labels;
		}else if("ConTypeRadio123".equals(identifier)){
				 fields[0]=strConTypeRadio123Values;
				 fields[1]=strstrConTypeRadio123Labels;
		 }else if("ConTypeRadio3".equals(identifier)){
			 fields[0]=strConTypeRadio3Values;
			 fields[1]=strstrConTypeRadio3Labels;
	 }else if("YearsMondsDates".equals(identifier)){
			 fields[0]=strYearsMondsDatesValues;
			 fields[1]=strYearsMondsDatesLabels;
	 }else if("SelectMinMax".equals(identifier)){
		 fields[0]=strSelectMinMaxValues;
		 fields[1]=strSelectMinMaxLabels;
 }
	  else if("SelectRaDa".equals(identifier)){ 
		 fields[0]=strCopayDeductibleTypeValues;
		 fields[1]=strCopayDeductibleTypeLabels;
    }
	else if("MnMx".equals(identifier)){ 
			 fields[0]=strMaxMinValues;
			 fields[1]=strMaxMinLabels;
	    }
		
		return fields;
	}
	
	
	private RulesFieldsLookup(){
	
}

}
