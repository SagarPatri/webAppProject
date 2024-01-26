package com.ttk.common;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import javax.xml.bind.DatatypeConverter;

public class Test {

	public static void main(String[] args) {
		String string = "QW@$";

		// encode string using Base64 encoder
		String encoded = DatatypeConverter.printBase64Binary(string.getBytes());
		System.out.println("Encoded String: " + encoded);

		// decode the encoded data
		String decoded = new String(DatatypeConverter
											.parseBase64Binary("UVdAJA=="));
		System.out.println("Decoded String: " + decoded);
	}
static class  TestBean1{
	private String str1;
	private String strarr1[];
	private int int1;
	private int intarr1[]=new int[1];
	private float float1;
	private Integer integer1;
	private BigDecimal bigDecimal1;
	private BigDecimal bigDecimalArr1[];
	
}

public static void printValues2(Object object){
	 try{
		 
		 if(object!=null){
			 Class<?> claz=object.getClass();
			 
			 Field[] fields=claz.getDeclaredFields();
			 System.out.println("===");
			 for(Field field:fields){
				 field.setAccessible(true);
				 if(field!=null)System.out.println(field.getClass().isArray());
				 
				 Object fieldValue=field.get(object);
				 if(fieldValue!=null&&fieldValue.toString().contains("com.ttk.dto.")){
					 
				 }
				 else 
				 System.out.print(field.getName()+":"+fieldValue+" ");
				 
			 }
			 System.out.println("===");
		 }
		 
	 }catch(Exception exception){
		 exception.printStackTrace();
	 }
}
 public static void printValues(Object object){
	 try{
		 
		 if(object!=null){
			 Class<?> claz=object.getClass();
			 
			 Field[] fields=claz.getDeclaredFields();
			 System.out.println("===");
			 System.out.println();
			 for(Field field:fields){
				 field.setAccessible(true);
				 Object fieldValue=field.get(object);
				 if(fieldValue!=null&&fieldValue.toString().contains("com.ttk.dto.")){
					 Class<?> claz2=fieldValue.getClass();
					 System.out.print(" "+field.getName()+"[");
					 Field[] fields2=claz2.getDeclaredFields();
					 for(Field field2:fields2){
						 field2.setAccessible(true);
						 System.out.print(field2.getName()+":"+field2.get(fieldValue)+" ");
					 }
					 System.out.print("] ");
				 }
				 else 
				 System.out.print(field.getName()+":"+fieldValue+" ");
				 
			 }
			 System.out.println();
			 System.out.println("===");
		 }
		 
	 }catch(Exception exception){
		 exception.printStackTrace();
	 }
 }
}
