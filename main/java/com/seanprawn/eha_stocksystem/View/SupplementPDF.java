/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View;

import com.seanprawn.eha_stocksystem.Model.Supplement;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class SupplementPDF {
    
        int countLines = 0;
        int result = 0;
        int Yaxis = 750; //Top of page line 750 - Keeps track of current line
        int TopLine = 750; //Top of page line 750
        int Lmargin = 20; //Left Margin
        int Rmargin = 600; //Right Margin
        int Yincrement = 15; //Increment Y axis by 15 lines
        int pageSize = 36; // How many lines to allow on the page (not actual page size)
        int pageNo = 0;
        int countTotal =0;
        DecimalFormat df = new DecimalFormat("#.00");
        // Create a new FONT_BOLD object selecting one of the PDF base fonts
        PDFont FONT_BOLD = PDType1Font.HELVETICA_BOLD;
        PDFont FONT_NORM = PDType1Font.HELVETICA;
        //Setup Lables
        String header = "EHA - Enlightened Health Alternative";
        String report = "SUPPLEMENT REPORT";
        String supls = "Supplements: ";

        String suplID = "Name";
        String suplDesc = "Description";
        String suplCost = "Cost";
        String suplMin = "Min";
        String suplMax = "Stock Cnt";
        String suplNappi = "Nappi Code";
        String suplSuplierId = "Supplier ID";

            //Get the date of this report being printed
        Calendar time = Calendar.getInstance();
        //format the date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd");//file
        SimpleDateFormat todayDateFormat = new SimpleDateFormat("dd-MMM-yyyy");//header
        String date = String.valueOf(simpleDateFormat.format(time.getTime()));//File Date
        String today = String.valueOf(todayDateFormat.format(time.getTime())); //Header Date
            
    public int CreateSupplementReportPDF(String path, ObservableList<Supplement> supList) throws IOException
    {
        int rows = supList.size();
        System.out.println("ROWS: "+rows);
        int amt = Math.round((rows / pageSize)+2); //Calc how many pages
        PDPage pages[] = new PDPage[amt];
        PDPageContentStream streams[] = new PDPageContentStream[amt];
        
         try ( // Create a document and add a page to it
        PDDocument document = new PDDocument()) 
        {
            //Init first page of document
            pages[0] = new PDPage(); // Add 1st page to array
            document.addPage( pages[0] ); // Add page to the document
            //Init 1st Content Stream
            try (PDPageContentStream contentStream = new PDPageContentStream(document, pages[0])) {
                streams[0] = contentStream; //Add stream to the array
                writeHeaderToPage(streams[0], document);
               
                int linesWritten = 0; // Keep track of how many
                int pageCount =1;
                linesWritten = writeTableToPage(streams[0], supList, linesWritten, rows, pageCount);//write 1st page supplements
                for(int j = 0;j <= amt;j++)
                {
                    System.out.println("\n Lines Written: "+linesWritten);
                    if(linesWritten < rows)
                    {
                        Yaxis = TopLine;
                        pages[(j+1)] = new PDPage();
                        document.addPage(pages[(j+1)]);
                        streams[(j+1)] = new PDPageContentStream(document, pages[(j+1)]);
                        
                        writeHeaderToPage(streams[(j+1)], document);
                        linesWritten = writeTableToPage(streams[(j+1)], supList, linesWritten, rows, ++pageCount);
                        
                  
                    }
                    else if(linesWritten >= rows)
                    {
                        break;
                    }
                }
                // Make sure that the content stream is closed:
            }
            
            // Save the results , name the file and ensure that the document is properly closed
            //The file is named with the following format:
            //The Path, and then an "EHA_" prefix, then supplement report, then the date of the eport
            document.save( path+"/EHA_Supplement_Report_"+date+".pdf");
            document.close();
            result = 1;// return: OK
            
         
        }catch (IOException ex) {
             Logger.getLogger(SupplementPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private int writeTableToPage(PDPageContentStream contentStream, ObservableList<Supplement> supList, int index, int rows, int page) 
    {
        //Print out all supplements 
        try {
            contentStream.beginText(); // New text stream
            contentStream.setFont(FONT_NORM, 10 );
            contentStream.newLineAtOffset(Lmargin, Yaxis); //Re-align starting point
        } catch (IOException e) {
        }
                 for(int i = index;i<(index+pageSize);i++)
                {
                   ++countLines;
                    Yaxis -= Yincrement;
                   if(countLines <= rows)
                   {
                       try {
                           contentStream.newLineAtOffset(0, -Yincrement );
                           contentStream.showText(supList.get(i).getSuplId());
                           contentStream.newLineAtOffset(100, 0 );
                           contentStream.showText(supList.get(i).getSuplDesc());
                           contentStream.newLineAtOffset(140, 0 );
                           contentStream.showText(String.valueOf(df.format(supList.get(i).getSuplCostClient())));
                           contentStream.newLineAtOffset(50, 0 );
                           contentStream.showText(String.valueOf(supList.get(i).getSuplMinLevels()));
                           contentStream.newLineAtOffset(70, 0 );
                           contentStream.showText(String.valueOf(supList.get(i).getSuplStockLevels()));
                           contentStream.newLineAtOffset(70, 0 );
                           contentStream.showText(supList.get(i).getsuplNappiCode());
                           contentStream.newLineAtOffset(100, 0 );
                           contentStream.showText(String.valueOf(supList.get(i).getSuplSupplierId()));
                           contentStream.newLineAtOffset(-530, 0 ); //Re-align

                       } catch (IOException ex) {
                           Logger.getLogger(SupplementPDF.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   }
                      else
                   {
                       break; // stop loop at end of List
                   }
                }
            try {
                        Yaxis -= Yincrement;
                        contentStream.endText();
                        //Draw Line under Table
                        contentStream.setLineWidth(1);
                        contentStream.moveTo(Lmargin, Yaxis);
                        contentStream.lineTo(Rmargin, Yaxis);
                        contentStream.closeAndStroke();
                        
                        contentStream.beginText(); // Open the stream for text
                        contentStream.setFont(FONT_BOLD, 12 );
                        //Draw Page Number
                        contentStream.newLineAtOffset(400, 20 ); //last Line
                        contentStream.showText("Page "+page); // Page number
                        contentStream.endText();
                        contentStream.close();
                
//                contentStream.close();
            } catch (IOException ex) {
                Logger.getLogger(SupplementPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
            return countLines;
    }

    private void writeHeaderToPage(PDPageContentStream contentStream, PDDocument document) {
            
            //get the image
            String logo_file = "images/eha_logo.jpg";
            PDImageXObject pdImage = null;
            try {
                pdImage = PDImageXObject.createFromFile(logo_file, document);
                contentStream.drawImage(pdImage, 10, 650, 150, 150); // Draw Image

                contentStream.beginText(); // Open the stream for text
                contentStream.setFont(FONT_BOLD, 12 );
                //                contentStream.setLeading(14.5f);

                //Draw Header
                contentStream.newLineAtOffset(200, TopLine ); //1st Line
                contentStream.showText(header);
                //Draw Report
                contentStream.newLineAtOffset(50, -Yincrement ); //2nd line is relative to the 1st!
                contentStream.showText(report);
                Yaxis -= Yincrement;
               
                //Draw Date
                contentStream.newLineAtOffset(30, -Yincrement ); 
                contentStream.showText(today);
                Yaxis -= Yincrement;
                Yaxis -= Yincrement;
                Yaxis -= Yincrement;
                Yaxis -= Yincrement;
                Yaxis -= Yincrement;
                Yaxis -= Yincrement;//Create space

                //Print out Supplement Header
//                contentStream.newLineAtOffset(-400, -(Yincrement*8) ); //-120
//                contentStream.showText(supls); //Header
//                Yaxis -= (Yincrement*8);
                
                contentStream.endText(); // End this text stream, (so as to start the draw line stream!)
//                Yaxis -= Yincrement;
                
                      //Draw Line under header supl Header
                contentStream.setLineWidth(1);
                contentStream.moveTo(Lmargin, Yaxis);
                contentStream.lineTo(Rmargin, Yaxis);
                contentStream.closeAndStroke(); // Close the Line stream
                Yaxis -= Yincrement;
                
                contentStream.beginText(); // Open a new Text stream
                contentStream.newLineAtOffset(Lmargin, Yaxis ); //Re-align starting point
                contentStream.showText(suplID); // Supplement field Headers
                contentStream.newLineAtOffset(100, 0 ); 
                contentStream.showText(suplDesc); // Supplement field Headers
                contentStream.newLineAtOffset(140, 0 ); 
                contentStream.showText(suplCost); // Supplement field Headers
                contentStream.newLineAtOffset(50, 0 ); 
                contentStream.showText(suplMin); // Supplement field Headers
                contentStream.newLineAtOffset(50, 0 ); 
                contentStream.showText(suplMax); // Supplement field Headers
                contentStream.newLineAtOffset(70, 0 ); 
                contentStream.showText(suplNappi); // Supplement field Headers
                contentStream.newLineAtOffset(100, 0 ); 
                contentStream.showText(suplSuplierId); // Supplement field Headers
                contentStream.endText(); // Close the text stream
                
                Yaxis -= Yincrement;
                  //Draw Line under table headers
                contentStream.setLineWidth(1);
                contentStream.moveTo(Lmargin, Yaxis); // re-align
                contentStream.lineTo(Rmargin, Yaxis);
                contentStream.closeAndStroke();
                
                Yaxis -= Yincrement;
            } catch (IOException ex) {
                Logger.getLogger(SupplementPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
