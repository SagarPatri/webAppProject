/**
 * @ (#) BigDecimalCustomConverter.java Feb 19, 2006
 * Project       : TTK HealthCare Services
 * File          : BigDecimalCustomConverter.java
 * Author        : Unni V Mana
 * Company       : Span Systems Corporation
 * Date Created  : Feb 19, 2006
 * @author       : Unni V Mana
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import formdef.plugin.conversion.ConversionContext;
import formdef.plugin.conversion.Converter;
import org.apache.log4j.Logger;

/*
 * This custom convertor class converts BigDecimal type to string and vice versa.
 */

public class BigDecimalCustomConverter implements Converter {
	 private static Logger log = Logger.getLogger(BigDecimalCustomConverter.class);
	 public Object convert(ConversionContext context)
	 {
	 	 	Object value = context.getValue();
	        Class  type = context.getType();
	        Object param = context.getParam();
	        String strCheckZero="^[0]{1,}(\\.[0]{1,})?$";
	        	log.debug(" You are inside convert method of BigDecimalCustomConverter class ");
	        	log.debug("converting [" + value + "] to type [" + type + "] using param=[" + param + "]");
	        // create a DecimalFormat applicable to our conversion context
	        DecimalFormat df = createDecimalFormat(context);
	        if(type.getName().equals("java.math.BigDecimal"))  // from form to VO
	        {
	        	if(value==null || value.equals(""))
	        	value="0";
	        	BigDecimal bd = new BigDecimal(value.toString());
	        	return bd;
	        }//end of if(type.getName().equals("java.math.BigDecimal"))
	        else if(type.getName().equals("java.lang.String")) //from VO  to form
	        {
	        	if(value==null || value.equals("") || value.equals("null")||(String.valueOf(value)).matches(strCheckZero))
	        	{
	        		//value="0.00";
	        		value="";
	        		return value.toString();
	        	}
	        }//end of else if(type.getName().equals("java.lang.String"))
	        return df.format(value);
	    }//end of convert(ConversionContext context)
	 protected DecimalFormat createDecimalFormat(ConversionContext context) {
		 Locale locale = context.getLocale();
		 Object param = context.getParam();
		 // create a decimal format specific to the locale being used
		 DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		 // apply the conversion parameter
		 if(param==null)
		 {
			 param=(String) param;
			 param="########.00";
			 df.applyLocalizedPattern((String) param);
		 }//end of if(param==null)
		 else
		 {
			 df.applyLocalizedPattern((String) param);
		 }//end of else
		 return df;
	 }//end of createDecimalFormat
}//end of BigDecimalCustomConverter