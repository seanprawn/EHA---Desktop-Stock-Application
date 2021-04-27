///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package com.seanprawn.eha_stocksystem.View;
//
//import java.io.FileNotFoundException;

import com.seanprawn.eha_stocksystem.Model.Invoice;
import com.seanprawn.eha_stocksystem.Model.InvoiceSupplement;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
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
public class InvoicePDF {
    
    public int createInvoicePDF(String path, Invoice inv, ObservableList<InvoiceSupplement> suplList)
    {
        //setup Strings
        int result = 0;
        double total = 0;
        if(inv.getInvConsultFee() != null) 
            total = inv.getInvConsultFee();//If Consult fee not null then append to total
        
        String invStr = "INVOICE";
        String header = "EHA - Enlightened Health Alternative"
            + "                         "+invStr;

        String phoneNum = "0727074926";
        String AddrLine1 = "16 Coppledrive Avenue";
        String AddrLine2 = "Roodepoort";
        String AddrLine3 = "Gauteng";
        String AddrLine4 = "1724";
        String consult = "Consultation Fee: ";
        DecimalFormat df = new DecimalFormat("#.00");
        String consultFee = String.valueOf(df.format(inv.getInvConsultFee()));
        
        String supls = "Supplements: ";
        String suplHeaderID = "Name";
        String suplHeaderPrice =  "Price";
        String suplHeaderqty =  "Quantity";
        String suplHeaderTotal = "Total";
        
        String paymentLine1 = "Make EFT payment to:";
        String paymentLine2 = "Mr Casey Millan (EHA Practitioner)";
        String paymentLine3 = "ABSA Account";
        String paymentLine4 = "Account number:     45124561254";
        String paymentLine5 = "Account Type: Cheque";
        String paymentLine6 = "SMS proof of payment to: "+phoneNum
                +" (use INV number as reference): "+inv.getInvNum();
                
        try ( // Create a document and add a page to it
        PDDocument document = new PDDocument()) 
        {
            PDPage page = new PDPage();
            document.addPage( page );
            
            //get the image
            String logo_file = "images/eha_logo.jpg";
            PDImageXObject pdImage = PDImageXObject.createFromFile(logo_file, document);
            
            // Create a new FONT_BOLD object selecting one of the PDF base fonts
            PDFont FONT_BOLD = PDType1Font.HELVETICA_BOLD;
            PDFont FONT_NORM = PDType1Font.HELVETICA;

            try (
            PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.drawImage(pdImage, 10, 650, 150, 150); // Draw Image
            contentStream.beginText(); // Open the stream for text
                contentStream.setFont(FONT_BOLD, 12 );
//                contentStream.setLeading(14.5f);

                //Draw Header
                contentStream.newLineAtOffset(170, 730 ); //1st Line
                contentStream.showText(header);
               
                //Draw Phone number
                contentStream.newLineAtOffset(70, -15 ); //2nd line is relative to the 1st!
                contentStream.showText(phoneNum);
                
                //Draw Invoice Number
                contentStream.newLineAtOffset(100, -100 );
                contentStream.showText("INVOICE NUMBER:");
                contentStream.newLineAtOffset(140, 0);
                contentStream.showText(inv.getInvNum());
                
                //Draw invoice Date
                contentStream.newLineAtOffset(-140, -15 ); 
                contentStream.showText("Date:");
                contentStream.newLineAtOffset(140,0); 
                contentStream.showText(String.valueOf(inv.getInvDate()));
                
                //Draw Client ID
                contentStream.newLineAtOffset(-140, -15 ); 
                contentStream.showText("Client ID:");
                contentStream.newLineAtOffset(120, 0 ); 
                contentStream.showText(String.valueOf(inv.getInvClientId()));
                
                // Print out Address
                contentStream.newLineAtOffset(-370, 34 );
                contentStream.showText(AddrLine1);
                contentStream.newLineAtOffset(0, -15 ); 
                contentStream.showText(AddrLine2);
                contentStream.newLineAtOffset(0, -15 ); 
                contentStream.showText(AddrLine3);
                contentStream.newLineAtOffset(0, -15 ); 
                contentStream.showText(AddrLine4);
                
                //Print out Consultation Fee
                contentStream.newLineAtOffset(0, -50 ); 
                contentStream.showText(consult);
                contentStream.newLineAtOffset(400, 0 ); 
                contentStream.showText(consultFee);
                
                //Print out Supplement Headers
                contentStream.newLineAtOffset(-400, -50 ); 
                contentStream.showText(supls); //Header
               
                contentStream.endText(); // End this text stream, (so as to start the draw line stream!)
//                
//                //Draw Line under header supl Header
                contentStream.setLineWidth(1);
                contentStream.moveTo(90, 465);
                contentStream.lineTo(500, 465);
                contentStream.closeAndStroke(); // Close the Line stream
////                
//                
                contentStream.beginText(); // Open a new Text stream
                contentStream.newLineAtOffset(90, 450 ); //Re-align starting point
                contentStream.showText(suplHeaderID); // Supplement field Headers
                contentStream.newLineAtOffset(150, 0 ); 
                contentStream.showText(suplHeaderPrice); // Supplement field Headers
                contentStream.newLineAtOffset(80, 0 ); 
                contentStream.showText(suplHeaderqty); // Supplement field Headers
                contentStream.newLineAtOffset(120, 0 ); 
                contentStream.showText(suplHeaderTotal); // Supplement field Headers
                contentStream.endText(); // Close the text stream

//                //Draw Line under table headers
                contentStream.setLineWidth(1);
                contentStream.moveTo(90, 440); // re-align
                contentStream.lineTo(500, 440);
                contentStream.closeAndStroke();
               
                contentStream.beginText(); // New text stream
                contentStream.newLineAtOffset(90, 440); //Re-align starting point
                int rows = 0;
                //Print out all supplements bought on Invoice
                for(InvoiceSupplement supl : suplList)
                {
                   contentStream.newLineAtOffset(0, -15 ); 
                   contentStream.showText(supl.getInvSuplId());
                   contentStream.newLineAtOffset(150, 0 ); 
                   contentStream.showText(String.valueOf(df.format(supl.getInvSuplPrice())));
                   contentStream.newLineAtOffset(100, 0 ); 
                   contentStream.showText(String.valueOf(supl.getInvSuplQty()));
                   contentStream.newLineAtOffset(100, 0 ); 
                   contentStream.showText(String.valueOf(df.format(supl.getInvSuplPriceTotal()))); 
                   contentStream.newLineAtOffset(-350, 0 ); //Re-align
                    total += supl.getInvSuplPriceTotal();// Add each supplement price to the total amount
                    rows += 15;
                }
                System.out.println("\n\n\n Rows: "+rows);
                contentStream.endText(); //close text stream so line stream can open
                
                 //Draw Line under Table
                contentStream.setLineWidth(1);
                contentStream.moveTo(90, (440-rows)-10);
                contentStream.lineTo(500, (440-rows)-10);
                contentStream.closeAndStroke();
                
                //Print out Supplement Total
                contentStream.beginText(); //open final text stream
                contentStream.newLineAtOffset(440,  (440-rows)-30); 
                contentStream.showText(String.valueOf(df.format(total))); 
                
                //Print Out Payment Details
                contentStream.newLineAtOffset(-350, -100 ); 
                contentStream.showText(paymentLine1); 
                contentStream.newLineAtOffset(0, -15 ); 
                contentStream.showText(paymentLine2); 
                contentStream.newLineAtOffset(0, -15 ); 
                contentStream.showText(paymentLine3); 
                contentStream.newLineAtOffset(0, -15 ); 
                contentStream.showText(paymentLine4); 
                contentStream.newLineAtOffset(0, -15 ); 
                contentStream.showText(paymentLine5); 
                contentStream.newLineAtOffset(0, -15 ); 
                contentStream.showText(paymentLine6); 
                
            contentStream.endText(); //close text stream
            // Make sure that the content stream is closed:
            contentStream.close();
            }
            
            // Save the results , name the file and ensure that the document is properly closed
            //The file is named with the following format:
            //The Path, and then an "EHA_" prefix, then the inv num, then the date of the inv
            File file = new File(path+"/EHA_"+inv.getInvNum()+"_"+String.valueOf(inv.getInvDate())+".pdf");
            document.save(file);
            document.close();
            
            //Check if Desktop services are available
            if(Desktop.isDesktopSupported())
            { 
                OpenPdfOnSystem(file);
            }else
            {
                Alerts.displayAlert("Save Success", "Please locate the file and open it manually");
            }
            result = 1;// return: OK
        } catch (IOException ex) {
             Logger.getLogger(InvoicePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
          return result;
        }

    private void OpenPdfOnSystem(File file) {
            Desktop deskT = Desktop.getDesktop();
                try {
                    deskT.open(file);
                } catch (IOException ex) {
                    Logger.getLogger(TopMenuBar.class.getName()).log(Level.SEVERE, "Could not open file", ex);
                }
            
    }
}

