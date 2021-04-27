/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class Alerts {
    static Boolean result = true;
     public static Boolean displayVerifyAlert(String title, String messsage)
    {
        Stage alertWindow = new Stage();
        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle(title);
        Label label = new Label();
        label.setText(messsage);
        label.setId("error");
        label.setAlignment(Pos.TOP_CENTER);
        label.setPadding(new Insets(20, 20, 20, 20));
        Button closeBtn = new Button("No");
        closeBtn.setAlignment(Pos.BOTTOM_LEFT);
        closeBtn.setOnAction(e -> {
            result = false;
            alertWindow.close();
        });
        
         Button yesBtn = new Button("Yes: "+title);
//         yesBtn.setAlignment(Pos.BOTTOM_RIGHT);
        yesBtn.setOnAction(e -> {
            result = true;
            alertWindow.close();
        });
       
        TilePane tile = new TilePane(closeBtn,yesBtn);
        tile.setHgap(50);
        
        tile.setOrientation(Orientation.HORIZONTAL);
        GridPane layout = new GridPane();
        layout.add(label, 1, 2);
        layout.add(tile, 1, 3);
        layout.setAlignment(Pos.CENTER);
        layout.setId("root");
        
        
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("/styles/Styles.css");
        alertWindow.setScene(scene);
        alertWindow.showAndWait();
         return result;
    }
     
      public static void displayAlert(String title, String messsage)
    {
        
        Stage alertWindow = new Stage();
        
        
        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle(title);
        Label label = new Label();
        label.setText(messsage);
        label.setAlignment(Pos.TOP_CENTER);
        label.setId("error");
        label.setPadding(new Insets(20, 20, 20, 20));
        Button closeBtn = new Button("Close");
//        closeBtn.setAlignment(Pos.BASELINE_CENTER);
        closeBtn.setOnAction(e -> {
        alertWindow.close();
        });
        
        GridPane layout = new GridPane();
        layout.add(label, 2, 2);
        layout.add(closeBtn, 2, 3);
        layout.setAlignment(Pos.CENTER);
        layout.setId("root");
        
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("/styles/Styles.css");
        alertWindow.setScene(scene);
        alertWindow.showAndWait();
    }
}
