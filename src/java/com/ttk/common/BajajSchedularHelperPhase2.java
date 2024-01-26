package com.ttk.common;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;







import org.apache.log4j.Logger;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfEncryptor;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.XfdfReader;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;




public class BajajSchedularHelperPhase2{

	private static final long serialVersionUID = 1L;
	private static final String BajajAllainZPhase2="Form Transformation";
	private static final Logger log = Logger.getLogger( BajajSchedularHelperPhase2.class);
	public  void mergeApproveForm() throws  Exception
	{
		log.info("Inside mergeApproveForm method in BajajSchedularHelper");
		String filename="";	String filefullpath="";	String outputFolder="";File folder=null;String actualFileName="";
		String xfdfFolderlocation=(String)TTKPropertiesReader.getPropertyValue("xfdfdirectory");
		String pdfMergedFilesFolderlocation=(String)TTKPropertiesReader.getPropertyValue("pdfdirectory");

		/**   Location where claim form resides     **/
		String parentClaimForm=(String)TTKPropertiesReader.getPropertyValue("claimform");
		XfdfReader xfdfReader;
		PdfReader pdfReader;
		PdfStamper stamper;
		AcroFields form=null;
		try{
			folder = new File(xfdfFolderlocation);
			File  fileList[]=folder.listFiles();	
			for(int i=0;i<fileList.length;i++)
			{

				/**Reads file from  XFDF files Directory **/
				if (fileList[i].isFile()) {

					filename = fileList[i].getName();
					filefullpath=xfdfFolderlocation+fileList[i].getName();
					actualFileName=pdfMergedFilesFolderlocation+filename.substring(0,filename.lastIndexOf('.'))+".pdf";	
					String fileExtension=filename.substring(filename.lastIndexOf('.') + 1, filename.length()).toLowerCase();
					if (fileExtension.equalsIgnoreCase("xfdf"))  {
					/**Code For Creating new PDF Form with Same Claim No of XFDF File **/
					xfdfReader=new XfdfReader(filefullpath);

						pdfReader=new PdfReader(parentClaimForm);
						form=pdfReader.getAcroFields();
						
						/**Code to merge Xfdf file into ClaimProcessin pdf Form **/
						stamper=new PdfStamper(pdfReader,new FileOutputStream(new File(actualFileName)), '\0', true);
						form=stamper.getAcroFields();
						form.setFields(xfdfReader);
						stamper.close();

						outputFolder=folder+"/processed/"+fileList[i].getName();
						 /** Code to move file from xfdf directory to sub directory*(processed) **/
						new File(filefullpath).renameTo(new File(outputFolder));
					
							/**Code to delete file from xfdf folder **/
						/*if(new File(filefullpath).exists()){
							 new File(filefullpath).delete();
						}*/

						/** Code to move file from xfdf directory to sub directory*(processed) **/
					//	new File(filefullpath).renameTo(new File(outputFolder));

					}//end of if (fileExtension.equalsIgnoreCase("xfdf"))  
				}//end of if (fileList[i].isFile())
			}//End of for(int i=0;i<fileList.length;i++)

		}//try
		catch(Exception exp){

			new Exception(BajajAllainZPhase2);
			exp.printStackTrace();
		}//end of catch(Exception e)




	}

	public  String getClobData(InputStream inputStream)throws Exception{

		InputStreamReader inputStreamReader = new InputStreamReader(inputStream); 
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);    
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		while((line = bufferedReader.readLine()) != null) {  //Read till end
			stringBuffer.append(line);
			stringBuffer.append("\n");
		}//end of while
		//String fileString = stringBuffer.toString();
		bufferedReader.close();         
		inputStreamReader.close();
		inputStream.close();
		// item.delete();
		return stringBuffer.toString();

	}








}