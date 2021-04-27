/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Invoice;

import com.seanprawn.eha_stocksystem.Controller.DatabaseMethods;
import com.seanprawn.eha_stocksystem.Model.Invoice;
import java.math.BigInteger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class InvoiceListTable {
    public InvoiceListTable()
    {}
    TextField txtSearch = new TextField();
    Button btnClear = new Button("Clear Search");
    TableView<Invoice> table = new TableView<>();
        public static Invoice selectedInvoice;
    
    public  GridPane CreateInvoiceListTable()
    {
        ObservableList<Invoice> invList = getList();
//        ObservableList<InvoiceSupplement> suplList = getInvoiceSupplementsList();
        table.setItems(invList);
        
         
        txtSearch.setPromptText("Search");
        //clear the Search Field
        btnClear.setOnAction((event) ->{
            txtSearch.clear();
        });
        
        GridPane tablePane = new GridPane();
        FlowPane searchP = new FlowPane(txtSearch, btnClear);
        tablePane.add(searchP,0,0);
        tablePane.add(table,0,1);
        Tooltip serchTip = new Tooltip("Search all the Fields of this table");
        Tooltip.install(searchP, serchTip);
        
        //Search the Table
        //Wrap list in a Filtered list
          FilteredList<Invoice> filteredData = new FilteredList<>(invList, p -> true);
        
         // Set the filter Predicate whenever the filter changes.
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Invoice -> {
                
                // If filter text is empty, display all Invoices as usual.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                   // Compare Invoice details, of every Invoice with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (String.valueOf(Invoice.getInvId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Inv ID.
                }
                else if (Invoice.getInvNum().toLowerCase().contains(lowerCaseFilter))
                {
                    return true; // Filter matches inv Num.
                }
                else if (String.valueOf(Invoice.getInvDate()).toLowerCase().contains(lowerCaseFilter))
                {
                    return true; // Filter matches inv Date.
                }
                else if (String.valueOf(Invoice.getInvClientId()).toLowerCase().contains(lowerCaseFilter))
                {
                    return true; // Filter matches inv Client ID.
                }
              
                return false; // Does not match.
            });
                 //Wrap the FilteredList in a SortedList. 
                SortedList<Invoice> sortedData = new SortedList<>(filteredData);

                //Bind the SortedList comparator to the TableView comparator.
                sortedData.comparatorProperty().bind(table.comparatorProperty());

                // Add sorted (and filtered) data to the table.
                table.setItems(sortedData);
        });
        
         //Setup the Table Columns
         TableColumn<Invoice, Integer> invId = new TableColumn<>("Invoice ID");
         invId.setCellValueFactory(new PropertyValueFactory("InvId"));
         
         TableColumn<Invoice, String> invNum = new TableColumn<>("Invoice Number");
         invNum.setCellValueFactory(new PropertyValueFactory("InvNum"));
         
         TableColumn<Invoice, String> invDate = new TableColumn<>("Date");
         invDate.setCellValueFactory(new PropertyValueFactory("InvDate"));
         
         TableColumn<Invoice, BigInteger> invClientId = new TableColumn<>("Client Id");
         invClientId.setCellValueFactory(new PropertyValueFactory("InvClientId"));
         
         TableColumn<Invoice, Double> invFee = new TableColumn<>("Consult Fee");
         invFee.setCellValueFactory(new PropertyValueFactory("InvConsultFee"));
         
         //Add the clumns to the Table
         table.getColumns().add(invId);
         table.getColumns().add(invNum);
         table.getColumns().add(invDate);
         table.getColumns().add(invClientId);
         table.getColumns().add(invFee);
         
         //Set Onclick Listener for Table
        table.setOnMouseClicked((MouseEvent event) -> {
        if (event.getClickCount() > 0) //Single click selects a supplement
        {
            onSelectInvoice();
        }
    });
         
        return tablePane;
    }

    private ObservableList<Invoice> getList() {
        ObservableList<Invoice> list = DatabaseMethods.getInvoiceList();
        return list;
    }
    
    public void onSelectInvoice() {
    // check the table's selected item and get selected item
    if (table.getSelectionModel().getSelectedItem() != null) 
    {
        selectedInvoice = table.getSelectionModel().getSelectedItem();
//        System.out.println("\n Invoice Selected is : \n"+selectedInvoice.getInvNum());
    }
  }
}
