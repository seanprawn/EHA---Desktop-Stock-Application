/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Supplement;

import com.seanprawn.eha_stocksystem.Model.Supplier;
import com.seanprawn.eha_stocksystem.View.Supplier.SupplierListTable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class SupplSupplierListSearchTable {
    public static Supplier result;
    public static Supplier DisplaySupplierListSearch()
    {
         Stage searchWindow = new Stage();
        
        
        searchWindow.initModality(Modality.APPLICATION_MODAL);
        searchWindow.setTitle("Select Client");
        
        SupplierListTable listTable = new SupplierListTable();
        TableView table = listTable.CreateSuplierListTable();
        
        Button closeBtn = new Button("Cancel");
        Button saveBtn = new Button("Save");
         
        closeBtn.setOnAction(e -> {
            result = null;
            searchWindow.close();
        });
        
         saveBtn.setOnAction(e -> {
        result = (Supplier) table.getSelectionModel().getSelectedItem();
            searchWindow.close();
        });

        GridPane layout = new GridPane();
        layout.add(table, 0, 0);
        layout.add(closeBtn, 0, 1);
        layout.add(saveBtn, 0, 2);
        
        Scene scene = new Scene(layout);
        searchWindow.setScene(scene);
        searchWindow.showAndWait();
         
        return result;
    }
}
