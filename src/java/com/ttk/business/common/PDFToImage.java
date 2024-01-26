package com.ttk.business.common;


import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFImageWriter;



public class PDFToImage {

	
		  private static final String PASSWORD = "";
          private static final String START_PAGE = "-startPage";
          private static final String END_PAGE = "-endPage";
          private static final String IMAGE_FORMAT = "-imageType";
          private static final String OUTPUT_PREFIX = "-outputPrefix";
          private static final String COLOR = "-color";
          private static final String RESOLUTION = "-resolution";

          /**
           * private constructor.
          */
          public PDFToImage()
          {
              //static class
          }

          /**
           * Infamous main method.
           *
           * @param args Command line arguments, should be one and a reference to a file.
           *
           * @throws Exception If there is an error parsing the document.
           */
          
        /* 
          /**
           * This will print the usage requirements and exit.
           */
          private static void usage()
          {
              System.err.println( "Usage: java org.apache.pdfbox.PDFToImage [OPTIONS] <PDF file>\n" +
                  "  -password  <password>          Password to decrypt document\n" +
                  "  -imageType <image type>        (" + getImageFormats() + ")\n" +
                  "  -outputPrefix <output prefix>  Filename prefix for image files\n" +
                  "  -startPage <number>            The first page to start extraction(1 based)\n" +
                  "  -endPage <number>              The last page to extract(inclusive)\n" +
                  "  -color <string>                The color depth (valid: bilevel, indexed, gray, rgb, rgba)\n" +
                  "  -resolution <number>           The bitmap resolution in dpi\n" +
                  "  <PDF file>                     The PDF document to use\n"
                  );
              System.exit(1);
          }

          private static String getImageFormats()
          {
              StringBuffer retval = new StringBuffer();
              String[] formats = ImageIO.getReaderFormatNames();
              for( int i = 0; i < formats.length; i++ )
              {
                  retval.append( formats[i] );
                  if( i + 1 < formats.length )
                  {
                      retval.append( "," );
                  }
              }
              return retval.toString();
          }

	



public  String  convert(String pdfFile,String outputPrefix,String imageFormat) throws Exception
{  
    String password = "";
    int startPage = 1;
    int endPage = Integer.MAX_VALUE;
    String color = "rgb";
    int resolution;
   
    
  try
    {

	 resolution = Toolkit.getDefaultToolkit().getMaximumCursorColors();

      
    }
    catch( HeadlessException e )
    {

        resolution = 256;
    }    
      
    PDDocument document = null;
    try
    {


        document = PDDocument.load(pdfFile);
      int imageType = 24;
    if ("bilevel".equalsIgnoreCase(color))
      {
            imageType = BufferedImage.TYPE_BYTE_BINARY;
        }
        else if ("indexed".equalsIgnoreCase(color))
        {
            imageType = BufferedImage.TYPE_BYTE_INDEXED;
        }
        else if ("gray".equalsIgnoreCase(color))
        {
            imageType = BufferedImage.TYPE_BYTE_GRAY;
        }
        else if ("rgb".equalsIgnoreCase(color))
        {
            imageType = BufferedImage.TYPE_INT_RGB;
        }
        else if ("rgba".equalsIgnoreCase(color))
        {
            imageType = BufferedImage.TYPE_INT_ARGB;
        }
        else
        {
            System.err.println( "Error: the number of bits per pixel must be 1, 8 or 24." );
            System.exit( 2 );
        }
        //Make the call
        PDFImageWriter imageWriter = new PDFImageWriter();
      

        boolean success = imageWriter.writeImage(document, imageFormat, password,
                startPage, endPage, outputPrefix, imageType, resolution);
       
        
       // 
        
        if (!success)
        {
            System.err.println( "Error: no writer found for image format '"
                    + imageFormat + "'" );
            System.exit(1);
        }
    }
    catch (Exception e)
    {
        System.err.println(e);
    }
    finally
    {
        if( document != null )
        {
            document.close();
        }
    }
	return (outputPrefix+startPage);
}



}

