/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Supplement;

import com.seanprawn.eha_stocksystem.Controller.DatabaseMethods;
import com.seanprawn.eha_stocksystem.Model.Supplement;
import com.seanprawn.eha_stocksystem.View.SupplementPDF;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class SupplementDownloadPDFReport {
    public void SupplementDownloadPDFReport()
    {
    }
    
    DirectoryChooser DIR_CHOOSER = new DirectoryChooser();
    String path;
    ObservableList<Supplement> supList;
    SupplementPDF pdf = new SupplementPDF();
    
    public int DownloadPDFReportForToday(Stage stage)
    {
        int result = 0;
        setupDirectoryChooser(DIR_CHOOSER);
        
        File dir = DIR_CHOOSER.showDialog(stage);
        
        if (dir != null) 
        {
            path = String.valueOf(dir.getAbsolutePath());
            supList = DatabaseMethods.getSupplementList();
        }

        if(supList != null && path != null)
        {
            try 
            {
                result = pdf.CreateSupplementReportPDF(path, supList);
            } 
            catch (IOException ex) {
                Logger.getLogger(SupplementDownloadPDFReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return result;
    }
    
    private void setupDirectoryChooser(DirectoryChooser directoryChooser) 
    {
        // Set title for DirectoryChooser
        directoryChooser.setTitle("Select Directory to save Report");

        // Set Initial Directory
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }
}
