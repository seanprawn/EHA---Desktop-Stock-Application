/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Invoice;

import com.seanprawn.eha_stocksystem.Controller.ValidateFields;
import com.seanprawn.eha_stocksystem.MainApp;
import com.seanprawn.eha_stocksystem.Model.Invoice;
import com.seanprawn.eha_stocksystem.Model.InvoiceSupplement;
import com.seanprawn.eha_stocksystem.View.InvoicePDF;
import java.io.File;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static com.seanprawn.eha_stocksystem.View.Invoice.InvoiceGeneratePdfPage.DIR_CHOOSER;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 * 
 * Creates and displays a page to generate a PDF of an Invoice
 * User needs to choose a destination first before download can begin
 */
public class InvoiceGeneratePdfPage {
   public void InvoiceGeneratePdfPage(){}; //blank constructor
    
    GridPane layout = new GridPane();
    int result = 0;
    static final DirectoryChooser DIR_CHOOSER = new DirectoryChooser();
//    String selectedDir;
    
    Button closeBtn = new Button("Cancel");
    Button dirBtn = new Button("Select/Change Directory");
    Button genBtn = new Button("Generate PDF");
    Label lblDir = new Label("Selected Directory   ");
    Text txtError = new Text();
    Text textDir = new Text();

    public int displayPDFPage(Invoice inv, ObservableList<InvoiceSupplement> suplList)
    {
        setupDirectoryChooser(DIR_CHOOSER);
        
        Stage GeneratePDFWindow = new Stage();

        GeneratePDFWindow.initModality(Modality.APPLICATION_MODAL);
        GeneratePDFWindow.setTitle("Generate Invoice PDF");
        
        if(MainApp.selectedDirectory!=null)
        {
            textDir.setText(MainApp.selectedDirectory); //remember the user's choice
        }

        closeBtn.setOnAction(e -> {
            result = 0; 
            GeneratePDFWindow.close();
        });
        
        //User choose a directory where to save the invoice
         dirBtn.setOnAction((event) -> {
             dirBtn.setStyle("-fx-border-color: transparent;");
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            File dir = DIR_CHOOSER.showDialog(stage);
            if (dir != null) {
                textDir.setText(dir.getAbsolutePath());
                MainApp.selectedDirectory = String.valueOf(dir.getAbsolutePath()); // Store the selected Directory Path
//                lblDir.setVisible(true);
//                textDir.setVisible(true);
//                genBtn.setVisible(true);
//                layout.add(genBtn, 0, 3);
                
            } else {
//                genBtn.setVisible(false);
//                lblDir.setVisible(false);
//                textDir.setVisible(false);
                if(MainApp.selectedDirectory != null) 
                {
                    textDir.setText(MainApp.selectedDirectory);
                }else
                {
                    textDir.setText(null);
                }
//                layout.getChildren().remove(genBtn);
            }
        });

        genBtn.setOnAction((event) ->{
            String path = textDir.getText(); // Get the path that the user has chosen
            if(!"".equals(path))
            {
                InvoicePDF invoice = new InvoicePDF();
                result = invoice.createInvoicePDF(path, inv, suplList);
                GeneratePDFWindow.close();
            } else 
            {
                if(!ValidateFields.validateStringCharTextField(textDir.getText())) 
                {
                    dirBtn.requestFocus();
                    dirBtn.setStyle("-fx-border-color: red;");

                    txtError.setText("Please choose a directory to save the PDF file");
                }else
                {
                    dirBtn.setStyle("-fx-border-color: transparent;");
                    txtError.setText("");
                }
            }
        });
         
        Tooltip clickTip = new Tooltip("Click to select a Directory where to save the file");
       Tooltip.install(dirBtn, clickTip);
       Tooltip dirTip = new Tooltip("This is the directory where you have chosen to save the Invoice PDF file");
       Tooltip.install(textDir, dirTip);
       Tooltip genTip = new Tooltip("Click Generate the PDF file");
       Tooltip.install(genBtn, genTip);
       Tooltip backTip = new Tooltip("Click to close this component and go back to list");
       Tooltip.install(closeBtn, backTip);
        
        layout.add(lblDir, 0, 0);
        layout.add(textDir, 1, 0);
        layout.add(dirBtn, 0, 2);
        layout.add(closeBtn, 1, 2);
        
        layout.add(txtError, 0, 4);
        layout.add(genBtn, 0, 6);

        layout.setId("root");
        Scene scene = new Scene(layout, 500, 300);
        scene.getStylesheets().add("/styles/Styles.css");
        GeneratePDFWindow.setScene(scene);
        GeneratePDFWindow.showAndWait();
         
        return result;
    }
     
        private void setupDirectoryChooser(DirectoryChooser directoryChooser) {
        // Set title for DirectoryChooser
        directoryChooser.setTitle("Select Directory to save Invoice");
 
        // Set Initial Directory
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }
//}
}
