/**
 * @ (#) AutoIncrementAbbr.java 
 * Project      : VidalHealth HealthCare Services
 * File         : AutoIncrementAbbr.java
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 20th March 2015
 *
 * @author       :  Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
This java class is used to auto increment of Insurance Abbreviation in Insurance module
Abbr Code	-	A1 to A9, B1 to B9 and so on...
 */


package com.ttk.action.empanelment;

public class AutoIncrementAbbr
{
	public String getInsAbbrevation(String arg1)
	{

		String value 	 = arg1;
		String next		 =	"" ;
		String temp		 =	"";
		int kish	=		0;	
				
		
		if( arg1!=null )
		{
			int charValue	 = value.charAt(0);
							
			
			temp	=	Character.toString(value.toString().charAt(1));
			kish	=	Integer.parseInt(temp);
			
			if(kish<9)
			{
				next	=	String.valueOf( (char) (charValue));
				kish	=	kish+1;
			}
			else
			{
				next	=	String.valueOf( (char) (charValue + 1));
				kish	=	1;
			}
		}
		
		else
		{
			kish		=	1;
			next		=	"A";
		}
		
		value	=	next+kish;
		/*System.out.println("numb::"+(kish));
		System.out.println(next);*/
		return value;
	}
}