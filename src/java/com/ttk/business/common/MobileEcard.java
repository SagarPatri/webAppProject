package com.ttk.business.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRException;
import org.apache.axis.AxisFault;
import org.apache.commons.codec.binary.Base64;



import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.webservices.helper.ValidatorResourceHelper;
import com.ttk.dao.impl.reports.TTKReportDataSource;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class MobileEcard {


	public static byte[] getMobileImage(String strVidalId,StringBuffer strEcardData) throws org.apache.axis.AxisFault, JRException
	{
		//ArrayList<Byte[]>
		byte[] data=null;
		JasperReport jasperReport,emptyReport,jasperReportSub;
		 JasperPrint jasperPrint=null;
		 ByteArrayOutputStream boas=null;
		 String strPolicyGroupSeqID = null;
		 String jrxmlfile = "";
		 String strTemplateName = "";
		 String strPdfFile="";
		
		String strResult="";
		ArrayList myBytes = new ArrayList();
		
		String[] ecardData=strEcardData.toString().split(",");
		
		strPolicyGroupSeqID=ecardData[0];
		strTemplateName=ecardData[1];
		Long lngMemSeqID=null;
		if(ecardData[2]!=null)
		{
			lngMemSeqID=Long.parseLong(ecardData[2]);
		}
	//
		 if(!(strTemplateName.equals("")))
		 {
			 jrxmlfile=TTKPropertiesReader.getPropertyValue("Ecards")+"E_"+strTemplateName +".jrxml";//"COM_CM_G.jrxml";
		 }else
		 {
			 boas=new ByteArrayOutputStream();
			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyCardReprot.jrxml");
			 jasperPrint = JasperFillManager.fillReport(emptyReport, new HashMap(),new JREmptyDataSource());
			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
			// request.setAttribute("boas",boas);
		 }
		 
		 
		 
				HashMap<String,Object> hashMap = new HashMap<String,Object>();
		
		long lngEmpty = 0;
		
		
		try
		{
			jasperReport = JasperCompileManager.compileReport(jrxmlfile);
			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			
			 boas=new ByteArrayOutputStream();
			 TTKReportDataSource ttkReportDataSource = new TTKReportDataSource("CardPrint1",strPolicyGroupSeqID,lngMemSeqID,lngEmpty);
			 if(ttkReportDataSource.getResultData().next())
			 {
				 ttkReportDataSource.getResultData().beforeFirst();
				jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
				
				
			}//end of else
			 else if(strPolicyGroupSeqID.equals("")||strPolicyGroupSeqID==null)
			 {
				 jasperPrint = JasperFillManager.fillReport(emptyReport, hashMap,new JREmptyDataSource());
			 }//end of if(strPolicyGrSeqID.equals("")||strPolicyGrSeqID==null)

			 strPdfFile = TTKPropertiesReader.getPropertyValue("mobilecards")+strVidalId+".pdf";

			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
			  data=boas.toByteArray();
			
			// JasperExportManager.exportReportToPdfFile(jasperPrint,strPdfFile);
		
			//return strPdfFile;
				return data;
		}//end of try

		catch(Exception exp)
		{
			exp.printStackTrace();
			AxisFault fault=new AxisFault();
			fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
			throw fault;
		}//end of catch

	}//end of getMobileImage(String strVidalId,StringBuffer strEcardData)


public static String getMobileImage1(String strVidalId,StringBuffer strEcardData) throws org.apache.axis.AxisFault, JRException
	{
 		byte[] data=null;
		//ArrayList<Byte[]>
 		StringBuffer sbLocationData=new StringBuffer();
		JasperReport jasperReport,emptyReport;
		 JasperPrint jasperPrint=null;
		 ByteArrayOutputStream boas=null;
		 String strPolicyGroupSeqID = null;
		 String jrxmlfile = "";
		 String strTemplateName = "";
		 String strPdfFile="";
		
		String strResult="";
		
		
		String[] ecardData=strEcardData.toString().split(",");
		
		strPolicyGroupSeqID=ecardData[0];
		strTemplateName=ecardData[1];
		Long lngMemSeqID=null;
		if(ecardData[2]!=null)
		{
			lngMemSeqID=Long.parseLong(ecardData[2]);
		}
	
		 if(!(strTemplateName.equals("")))
		 {
			 jrxmlfile=TTKPropertiesReader.getPropertyValue("Ecards")+"E_"+strTemplateName+".jrxml";//"COM_CM_G.jrxml";
		 }else
		 {
			 boas=new ByteArrayOutputStream();
			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyCardReprot.jrxml");
			 jasperPrint = JasperFillManager.fillReport(emptyReport, new HashMap(),new JREmptyDataSource());
			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
			// request.setAttribute("boas",boas);
		 }
		 
		 
		 
				HashMap<String,Object> hashMap = new HashMap<String,Object>();
		
		long lngEmpty = 0;
		
		
		try
		{
			jasperReport = JasperCompileManager.compileReport(jrxmlfile);
			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			
			 boas=new ByteArrayOutputStream();
			 TTKReportDataSource ttkReportDataSource = new TTKReportDataSource("CardPrint1",strPolicyGroupSeqID,lngMemSeqID,lngEmpty);
			 if(ttkReportDataSource.getResultData().next())
			 {
				 ttkReportDataSource.getResultData().beforeFirst();
				jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
				
				
			}//end of else
			 else if(strPolicyGroupSeqID.equals("")||strPolicyGroupSeqID==null)
			 {
				 jasperPrint = JasperFillManager.fillReport(emptyReport, hashMap,new JREmptyDataSource());
			 }//end of if(strPolicyGrSeqID.equals("")||strPolicyGrSeqID==null)

			 strPdfFile = TTKPropertiesReader.getPropertyValue("mobilecards")+strVidalId+".pdf";
			 

			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
			  data=boas.toByteArray();
			
			 JasperExportManager.exportReportToPdfFile(jasperPrint,strPdfFile);
			/*String satya=Base64.encodeBase64String(data);
			String jpegFile=TTKPropertiesReader.getPropertyValue("mobilecards")+strVidalId+".jpeg";
			String jpegFile1=TTKPropertiesReader.getPropertyValue("mobilecards")+strVidalId+"1"+".jpeg";

			java.io.FileOutputStream imageOutFile = new java.io.FileOutputStream(jpegFile);
			java.io.FileOutputStream imageOutFile1 = new java.io.FileOutputStream(jpegFile1);

       		 imageOutFile.write(org.apache.commons.codec.binary.Base64.decodeBase64(satya));
                      imageOutFile1.write(data);

			imageOutFile1.close(); 
        		imageOutFile.close();
			*/
			 
			
		}//end of try

		catch(Exception exp)
		{
			exp.printStackTrace();
			AxisFault fault=new AxisFault();
			fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
			throw fault;
		}//end of catch
		return (sbLocationData.append((String)TTKPropertiesReader.getPropertyValue("sftplocation")).append("&#").append((strVidalId+".pdf")).append("&#").append("ecards").append("&#").append("ecardsmobile@123")).toString();

	}//end of getMobileImage1(String strVidalId,StringBuffer strEcardData)




	/*public static String getMobileImage1(String strVidalId,StringBuffer strEcardData) throws org.apache.axis.AxisFault, JRException
	{
 		byte[] data=null;
		//ArrayList<Byte[]>
		
		JasperReport jasperReport,emptyReport,jasperReportSub;
		 JasperPrint jasperPrint=null;
		 ByteArrayOutputStream boas=null;
		 String strPolicyGroupSeqID = null;
		 String jrxmlfile = "";
		 String strTemplateName = "";
		 String strPdfFile="";
		
		String strResult="";
		ArrayList myBytes = new ArrayList();
		
		String[] ecardData=strEcardData.toString().split(",");
		
		strPolicyGroupSeqID=ecardData[0];
		strTemplateName=ecardData[1];
		Long lngMemSeqID=null;
		if(ecardData[2]!=null)
		{
			lngMemSeqID=Long.parseLong(ecardData[2]);
		}
	
		 if(!(strTemplateName.equals("")))
		 {
			 jrxmlfile=TTKPropertiesReader.getPropertyValue("Ecards")+"E_"+strTemplateName+".jrxml";//"COM_CM_G.jrxml";
		 }else
		 {
			 boas=new ByteArrayOutputStream();
			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyCardReprot.jrxml");
			 jasperPrint = JasperFillManager.fillReport(emptyReport, new HashMap(),new JREmptyDataSource());
			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
			// request.setAttribute("boas",boas);
		 }
		 
		 
		 
				HashMap<String,Object> hashMap = new HashMap<String,Object>();
		
		long lngEmpty = 0;
		
		
		try
		{
			jasperReport = JasperCompileManager.compileReport(jrxmlfile);
			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			
			 boas=new ByteArrayOutputStream();
			 TTKReportDataSource ttkReportDataSource = new TTKReportDataSource("CardPrint1",strPolicyGroupSeqID,lngMemSeqID,lngEmpty);
			 if(ttkReportDataSource.getResultData().next())
			 {
				 ttkReportDataSource.getResultData().beforeFirst();
				jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
				
				
			}//end of else
			 else if(strPolicyGroupSeqID.equals("")||strPolicyGroupSeqID==null)
			 {
				 jasperPrint = JasperFillManager.fillReport(emptyReport, hashMap,new JREmptyDataSource());
			 }//end of if(strPolicyGrSeqID.equals("")||strPolicyGrSeqID==null)

			 strPdfFile = TTKPropertiesReader.getPropertyValue("mobilecards")+strVidalId+".pdf";
			 

			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
			  data=boas.toByteArray();
			
			 JasperExportManager.exportReportToPdfFile(jasperPrint,strPdfFile);
			String satya=Base64.encodeBase64String(data);
			String jpegFile=TTKPropertiesReader.getPropertyValue("mobilecards")+strVidalId+".jpeg";
			String jpegFile1=TTKPropertiesReader.getPropertyValue("mobilecards")+strVidalId+"1"+".jpeg";

			java.io.FileOutputStream imageOutFile = new java.io.FileOutputStream(jpegFile);
			java.io.FileOutputStream imageOutFile1 = new java.io.FileOutputStream(jpegFile1);

       		 imageOutFile.write(org.apache.commons.codec.binary.Base64.decodeBase64(satya));
                      imageOutFile1.write(data);

			imageOutFile1.close(); 
        		imageOutFile.close();
			
		}//end of try

		catch(Exception exp)
		{
			exp.printStackTrace();
			AxisFault fault=new AxisFault();
			fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
			throw fault;
		}//end of catch
		return (data != null)?Base64.encodeBase64String(data) :"no data found";

	}//end of getMobileImage1(String strVidalId,StringBuffer strEcardData)
*/

	
	public static String getMobileImageData(String strVidalId,StringBuffer strEcardData) throws org.apache.axis.AxisFault, JRException
      {
        String strResult="";
        String jrxmlfile = "";
       // ArrayList myBytes = new ArrayList();
        byte[] imageInByte=null;
        final String[] EXTENSIONS = new String[]{"jpg", "jpeg", "gif" };// and other formats you need
        
         final File Folder = new File(TTKPropertiesReader.getPropertyValue("mobilecards"));
        
      		byte[] data=null;
     		//ArrayList<Byte[]>
     		
     		JasperReport jasperReport,emptyReport,jasperReportSub;
     		 JasperPrint jasperPrint=null;
     		 ByteArrayOutputStream boas=null;
     		 String strPolicyGroupSeqID = null;
     		
     		 String strTemplateName = "";
     		 String strPdfFile="";
     		
     		
     		//ArrayList myBytes = new ArrayList();
     		
     		String[] ecardData=strEcardData.toString().split(",");
     		
     		strPolicyGroupSeqID=ecardData[0];
     		strTemplateName=ecardData[1];
     		Long lngMemSeqID=null;
     		
    		if(ecardData[2]!=null)
    		{
    			lngMemSeqID=Long.parseLong(ecardData[2]);
    		}
    	
   	 
		 // filter to identify images based on their extensions
		     final FilenameFilter IMAGE_FILTER = new FilenameFilter() 
		     {
		    	// @Override
		         public boolean accept(final File Folder, final String name) {
		    		 
		             for (final String ext : EXTENSIONS) {
		                 if (name.endsWith("." + ext)) {
		                     return (true);
		                 }
		             }
		             return (false);
		         }
		     };
		
     		if(ecardData[2]!=null)
     		{
     			lngMemSeqID=Long.parseLong(ecardData[2]);
     		}
     	
     		 if(!(strTemplateName.equals("")))
     		 {
     			 jrxmlfile=TTKPropertiesReader.getPropertyValue("Ecards")+"E_"+strTemplateName+".jrxml";//"COM_CM_G.jrxml";
     		 }else
     		 {
     			 boas=new ByteArrayOutputStream();
     			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyCardReprot.jrxml");
     			 jasperPrint = JasperFillManager.fillReport(emptyReport, new HashMap(),new JREmptyDataSource());
     			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
     			// request.setAttribute("boas",boas);
     		 }
		       try
        {
            
			 
			  String date1 = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
  			  String path="Ecard_" + date1 + ".pdf";
		      String fileName = "attachment; filename=\"+"+path;
		      
		      
		   
		      
		      File pdf = File.createTempFile("output.",".pdf");
		      JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));
		      JasperExportManager.exportReportToPdfFile(jasperPrint,"pdf");
		     // JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
		      
		      
		      
		      
			  
  		       String pdfFile = pdf.getCanonicalPath();
  	           String outputPrefix = TTKPropertiesReader.getPropertyValue("mobilecards")+ecardData[2];
  			  String imageFormat = "jpg";
  		      PDFToImage pdfToImage=new PDFToImage();
			 pdfToImage.convert(pdfFile,outputPrefix,imageFormat);
			  
			 
			String strFileJpegName=outputPrefix+".jpg";
			 
			 File file=new File(strFileJpegName);
			 if(file.exists())
			 {
				     BufferedImage img = null;

		                try {
		                    img = ImageIO.read(new File((outputPrefix+".jpg")));
		                    // Create a byte array output stream.
		    	            ByteArrayOutputStream bao = new ByteArrayOutputStream();

		    	            // Write to output stream
		    	            ImageIO.write(img, "jpg", bao);
		    	            
		    	            imageInByte = bao.toByteArray();
		    	            bao.close();
		    	           
		    	            String satya=Base64.encodeBase64String(imageInByte);
		    				String jpegFile=TTKPropertiesReader.getPropertyValue("mobilecards")+"satya"+".jpeg";
		    				

		    				java.io.FileOutputStream imageOutFile = new java.io.FileOutputStream(jpegFile);
		    				 imageOutFile.write(org.apache.commons.codec.binary.Base64.decodeBase64(satya));
		                      imageOutFile.write(data);

		    	            
		    	         
		    	            
		    	            
		                    
		                    
		                    
		                    
		                } catch (IOException e) {
		                    // handle errors here
		                }
				
			 }
			
			
				
			 
        }//end of try
        
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return (imageInByte != null)? Base64.encodeBase64String(imageInByte) :"no data found";
    }//end of getMobileImageDATA(String strIdentifier)
    

    /**
     * This method method returns the Pdf File Name 
     * @param String object which contains the strVidalId for which data in is required.
     * @param String object which contains the strEcardData for which data in is required.
      * @exception throws TTKException.
     */
public static String getPdfFileName(String strVidalId,StringBuffer strEcardData) throws org.apache.axis.AxisFault, JRException
	{
		JasperReport jasperReport,emptyReport;
		JasperPrint jasperPrint=null;
		ByteArrayOutputStream boas=null;
		String strPolicyGroupSeqID = null;
		String jrxmlfile = "";
		String strTemplateName = "";
		String strPdfFile="";
		String[] ecardData=strEcardData.toString().split(",");
		strPolicyGroupSeqID=ecardData[0];
		strTemplateName=ecardData[1];
		Long lngMemSeqID=null;
		if(ecardData[2]!=null)
		{
			lngMemSeqID=Long.parseLong(ecardData[2]);
		}

		if(!(strTemplateName.equals("")))
		{
			jrxmlfile=TTKPropertiesReader.getPropertyValue("Ecards")+"E_"+strTemplateName+".jrxml";//"COM_CM_G.jrxml";
		}else
		{
			boas=new ByteArrayOutputStream();
			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyCardReprot.jrxml");
			jasperPrint = JasperFillManager.fillReport(emptyReport, new HashMap(),new JREmptyDataSource());
			JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
			
		}
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		long lngEmpty = 0;
		try
		{
			jasperReport = JasperCompileManager.compileReport(jrxmlfile);
			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");

			boas=new ByteArrayOutputStream();
			TTKReportDataSource ttkReportDataSource = new TTKReportDataSource("CardPrint1",strPolicyGroupSeqID,lngMemSeqID,lngEmpty);
			if(ttkReportDataSource.getResultData().next())
			{
				ttkReportDataSource.getResultData().beforeFirst();
				jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
				
				
			}//end of else
			else if(strPolicyGroupSeqID.equals("")||strPolicyGroupSeqID==null)
			{
				jasperPrint = JasperFillManager.fillReport(emptyReport, hashMap,new JREmptyDataSource());
			}//end of if(strPolicyGrSeqID.equals("")||strPolicyGrSeqID==null)
			strPdfFile = TTKPropertiesReader.getPropertyValue("mobilecards")+strVidalId+".pdf";
			
			JasperExportManager.exportReportToPdfFile(jasperPrint,strPdfFile);
		}//end of try

		catch(Exception exp)
		{
			exp.printStackTrace();
			AxisFault fault=new AxisFault();
			fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
			throw fault;
		}//end of catch
		return strPdfFile;
	}//end of getPdfFileName(String strVidalId,StringBuffer strEcardData)


    /**
     * This method method returns the String( jpeg file name )
     * @param String object which contains the strVidalId for which data in is required.
     * @param String object which contains the strFilePdfName for which data in is required.
      * @exception throws TTKException.
     */

	public static String getJpegFileName(String strVidalId,String strFilePdfName) throws org.apache.axis.AxisFault, JRException
	{
		ByteArrayOutputStream boas=null;
		String strFileJpegName="";
			
			try{
			String outputPrefix = TTKPropertiesReader.getPropertyValue("mobilecards")+strVidalId;
			String imageFormat = "jpg";
			PDFToImage pdfToImage=new PDFToImage();
			String finalName=pdfToImage.convert(strFilePdfName,outputPrefix,imageFormat);
			strFileJpegName=finalName+".jpg";
		    }//end of try
		      catch(Exception exp)
		    {
			    exp.printStackTrace();
			    AxisFault fault=new AxisFault();
			    fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
			     throw fault;
		    }//end of catch
		
		return strFileJpegName;
	}//end of getMobileImageDATA(String strIdentifier)

	
	 /**
     * This method method returns the String( Byte [] as String )
     * @param String object which contains the jpegfileName for which data in is required.
      * @exception throws TTKException.
     */
	public static String getImageByteData(String jpegfileName) throws org.apache.axis.AxisFault, JRException
	{
		byte[] imageInByte=null;
		File file=new File(jpegfileName);
		if(file.exists())
		{
			BufferedImage img = null;

			try {
				img = ImageIO.read(new File(jpegfileName));
				// Create a byte array output stream.
				ByteArrayOutputStream bao = new ByteArrayOutputStream();

				// Write to output stream
				ImageIO.write(img, "jpg", bao);
				
				imageInByte = bao.toByteArray();
				bao.close();

				/*
				String satya=Base64.encodeBase64String(imageInByte);
				String jpegFile=TTKPropertiesReader.getPropertyValue("mobilecards")+"hurriah"+".jpeg";
				java.io.FileOutputStream imageOutFile = new java.io.FileOutputStream(jpegFile);
				imageOutFile.write(org.apache.commons.codec.binary.Base64.decodeBase64(satya));
				imageOutFile.write(imageInByte);
				*/

				
				
				
				
			} catch (IOException e) {
				// handle errors here
			}

		}//if fileExists

		return (imageInByte != null)? Base64.encodeBase64String(imageInByte) :"no data found";
	}//end of public static String getImageByteData(String jpegfileName) throws org.apache.axis.AxisFault, JRException

}
