/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Supplier;

import com.seanprawn.eha_stocksystem.Controller.DatabaseMethods;
import com.seanprawn.eha_stocksystem.Model.Supplier;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class SupplierListTable {

    TableView<Supplier> table = new TableView<>();
    public static Supplier selectedSupplier;
    public void SuplierListTable()
    {
    }
    
    public TableView CreateSuplierListTable() {
        ObservableList<Supplier> suplList = getList();
        table.setItems(suplList);
       
         //Setup the Table Columns
        TableColumn<Supplier,Integer> suplId = new TableColumn<>("Supplier ID");
        suplId.setCellValueFactory(new PropertyValueFactory("SuplId"));
        
        TableColumn<Supplier,String> suplCode = new TableColumn<>("Supplier Code");
        suplCode.setCellValueFactory(new PropertyValueFactory("SuplCode"));
        
        TableColumn<Supplier,String> sulContact = new TableColumn<>("Contact");
        sulContact.setCellValueFactory(new PropertyValueFactory("SuplContact"));
        
        TableColumn<Supplier,String> suplTel = new TableColumn<>("Tel");
        suplTel.setCellValueFactory(new PropertyValueFactory("SuplTel"));
        
        TableColumn<Supplier,String> suplCell = new TableColumn<>("Cell");
        suplCell.setCellValueFactory(new PropertyValueFactory("SuplCell"));
        
        TableColumn<Supplier,String> suplFax = new TableColumn<>("Fax");
        suplFax.setCellValueFactory(new PropertyValueFactory("SuplFax"));
        
        TableColumn<Supplier,String> suplEmail = new TableColumn<>("Email");
        suplEmail.setCellValueFactory(new PropertyValueFactory("SuplEmail"));
        
        TableColumn<Supplier,String> suplBank = new TableColumn<>("Bank");
        suplBank.setCellValueFactory(new PropertyValueFactory("SuplBank"));
        
        TableColumn<Supplier,Integer> suplBranch = new TableColumn<>("Branch Code");
        suplBranch.setCellValueFactory(new PropertyValueFactory("SuplBranchCode"));
        
         TableColumn<Supplier,Integer> suplAccNum = new TableColumn<>("Account No");
        suplAccNum.setCellValueFactory(new PropertyValueFactory("SuplAccNum"));
        
        TableColumn<Supplier,String> suplAccType = new TableColumn<>("Account Type");
        suplAccType.setCellValueFactory(new PropertyValueFactory("SuplAccType"));
        
        TableColumn<Supplier,String> suplComment = new TableColumn<>("Comments");
        suplComment.setCellValueFactory(new PropertyValueFactory("SuplComments"));
               
        //Add the clumns to the Table
        table.getColumns().add(suplId);
        table.getColumns().add(suplCode);
        table.getColumns().add(sulContact); 
        table.getColumns().add(suplTel); 
        table.getColumns().add(suplCell); 
        table.getColumns().add(suplFax); 
        table.getColumns().add(suplEmail); 
        table.getColumns().add(suplBank); 
        table.getColumns().add(suplBranch);
        table.getColumns().add(suplAccNum);
        table.getColumns().add(suplAccType);
        table.getColumns().add(suplComment);
        
        //Set OnClick Listener for Table
        table.setOnMouseClicked((MouseEvent event) -> {
        if (event.getClickCount() > 0) //Single click
        {
            onSelectSupplier();
        }
    });
        
        return table;
    }

    private ObservableList<Supplier> getList() {
        ObservableList<Supplier> list =DatabaseMethods.getSupplierList();
        return list;
    }

    private void onSelectSupplier() {
        if (table.getSelectionModel().getSelectedItem() != null) 
    {
        selectedSupplier = table.getSelectionModel().getSelectedItem();
    }
    }
    
}
