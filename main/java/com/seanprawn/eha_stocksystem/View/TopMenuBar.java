/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View;

import com.seanprawn.eha_stocksystem.View.Supplement.SupplementListPage;
import com.seanprawn.eha_stocksystem.Controller.SceneController;
import com.seanprawn.eha_stocksystem.View.Supplement.SupplementDownloadPDFReport;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 * 
 * Sets up the nodes for the Top Menu bar
 */
public class TopMenuBar {
    
    TilePane menuPane = new TilePane();
    HBox hBOX = new HBox();
    TilePane btnsGroup = new TilePane();
    MenuButton menuLogOut = new MenuButton("Account");
    
    public void setMenuPane(TilePane pane)
    {
        this.menuPane = pane;
    }
    
    public TilePane getMenuPane()
    {
        return this.menuPane;
        
    }

    /**
     * Set up and create nodes for the top menu
     * Returns the nodes in a FlowPane layout to the calling function in controller
     * @return FlowPane
     */
    public HBox loadTopMenuBar()
    {   
        setupMenuBar();  //set the menubar settings
       
        // Create the menu Bar buttons 
        MenuButton menu1 = new MenuButton("File");
        MenuItem closeItem = new MenuItem("_Close Application");
        
        MenuButton menu2 = new MenuButton("Options");
        MenuItem reportItem = new MenuItem("Supplement _Report");
        
        MenuButton menu3 = new MenuButton("Help");
        MenuItem helpItem = new MenuItem("View User _Manual");
        
        MenuItem logoutItem = new MenuItem("_Logout");
      
        //Add the menu Items
        menu1.getItems().add(closeItem);
        menu2.getItems().add(reportItem);
        menu3.getItems().add(helpItem);
        menuLogOut.getItems().add(logoutItem);
        
        //Add the menut Items/Buttons to the layout
        btnsGroup.getChildren().addAll(menu1,menu2,menu3,menuLogOut); // Add the menu buttons to group tilePane
        menuPane.getChildren().add(btnsGroup); //Add the buttons to the pane
        hBOX.getChildren().add(menuPane); // Add the buttons to the hBox

        //Add ToolTips
        Tooltip closeTip = new Tooltip("Click to logout and Close Application");
             Tooltip.install(menu1, closeTip);
        Tooltip optionTip = new Tooltip("Click to Download Supplement Report");
             Tooltip.install(menu2, optionTip);
        Tooltip helpTip = new Tooltip("Click to Download User Manual");
             Tooltip.install(menu3, helpTip);
        Tooltip logoutTip = new Tooltip("Click to Logout User");
             Tooltip.install(menuLogOut, logoutTip);
        //Add Shortcut Keys
        closeItem.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.ALT_DOWN, KeyCodeCombination.SHIFT_DOWN));
        helpItem.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN, KeyCodeCombination.SHIFT_DOWN));
        reportItem.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.ALT_DOWN, KeyCodeCombination.SHIFT_DOWN));
        logoutItem.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.ALT_DOWN, KeyCodeCombination.SHIFT_DOWN));
        
             
        closeItem.setOnAction((event) ->{
            Boolean alert = Alerts.displayVerifyAlert("Shutdown", "Are you sure you want to Close Application?");
                if(alert == true)
                {
                    Platform.exit();
                }
        });
        
        reportItem.setOnAction((event) -> {
            //Get the stage from the Menu Button
            Stage stage = (Stage)((Node)menu2).getScene().getWindow();
            
            //Generate report
            SupplementDownloadPDFReport pdf = new SupplementDownloadPDFReport();
            int gen = pdf.DownloadPDFReportForToday(stage);

            //Display result to user
            if(gen >0) 
            {
                Alerts.displayAlert("Success", "PDF Generated Succesfully \n");
            }else
            {
                Alerts.displayAlert("Error", "PDF report unsuccessful, please try again \n");
            }
        });
        
        //Logout Event Handler
        logoutItem.setOnAction((event) -> {
             Boolean alert = Alerts.displayVerifyAlert("Logout", "Are you sure you want to logout?");
                if(alert == true)
                {
                try 
                {
                    //Logs out of the application 
                    SceneController control = new SceneController();
                    Stage mainStage = (Stage)((Node) menuPane).getScene().getWindow();
                    control.setMainStage(mainStage);
                    System.out.println(logoutItem.getText()+": Successful \n");
                    control.login();
                    } catch (Exception ex) {
                        Logger.getLogger(SupplementListPage.class.getName()).log(Level.SEVERE, "Exception in logout process", ex);
                    }
                }
        });
        
        //View the User Manual on the local machine in the Local PDF browser
        helpItem.setOnAction((event) ->{
            File file = new File("EHA_User_Manual.pdf");
            
            //Check if Desktop services are available
            if(Desktop.isDesktopSupported())
            {
            Desktop deskT = Desktop.getDesktop();
                try {
                    deskT.open(file);
                } catch (IOException ex) {
                    Logger.getLogger(TopMenuBar.class.getName()).log(Level.SEVERE, "Could not open file", ex);
                }
            }else
            {
                Alerts.displayAlert("Error", "Could not open the User Manual at this time");
            }
        });
        
        return hBOX;
    }
    
    private void setupMenuBar() {
        btnsGroup.setPadding(new Insets(0, 0, 0, 150));
        btnsGroup.setVgap(5);
    }
}
