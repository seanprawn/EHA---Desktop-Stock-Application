/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Invoice;

import com.seanprawn.eha_stocksystem.Controller.DatabaseMethods;
import com.seanprawn.eha_stocksystem.Controller.ValidateFields;
import com.seanprawn.eha_stocksystem.Model.InvoiceSupplementSelect;
import com.seanprawn.eha_stocksystem.View.LoginPage;
import java.text.DecimalFormat;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class InvSupplementListSearchTable {
     TableView<InvoiceSupplementSelect> table = new TableView<>();
    public static InvoiceSupplementSelect selectedSupplement;
    public static InvoiceSupplementSelect result;
    GridPane layout = new GridPane();
    TextField txtQty = new TextField();
    TextField txtPrice= new TextField();
    TextField txtPriceTotal = new TextField();
    TextField txtPriceTotalConsult = new TextField();
    Label txtError = new Label();
    String priceErrMsg = "Please enter a valid Price Range";
    String qtyErrMsg = "Please select a Quantity";
    TextField txtSearch = new TextField();
    Button btnClear = new Button("Clear Search");
    double consultFee;
    int check = 0;

    DecimalFormat df = new DecimalFormat("#.00");

    public void InvSupplementListSearchTable()
    {
        
    }
    
    public void SetInvoiceConsultFee(double consult)
    {
        this.consultFee = consult;
    }
    
    public GridPane CreateSupplementListTable(ObservableList<InvoiceSupplementSelect> suplist)
    {
        table.setItems(suplist);
        txtError.setId("error");
        txtPrice.setEditable(false);
        txtPriceTotal.setEditable(false);
        txtPriceTotalConsult.setEditable(false);
//         ObservableList<InvoiceSupplementSelect> clientList = getList();
//        table.setItems(clientList);
        
        txtSearch.setPromptText("Search Fields");
        //clear the Search Field
        btnClear.setOnAction((event) ->{
            txtSearch.clear();
        });
        
        GridPane tablePane = new GridPane();
        FlowPane searchP = new FlowPane(txtSearch, btnClear);
        tablePane.add(searchP,0,0);
        tablePane.add(table,0,1);
        Tooltip serchTip = new Tooltip("Search the Fields of this table");
        Tooltip.install(searchP, serchTip);
        
        //Search the Table
        //Wrap Suplement list in a Filtered list
          FilteredList<InvoiceSupplementSelect> filteredData = new FilteredList<>(suplist, p -> true);
        
         // Set the filter Predicate whenever the filter changes.
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(InvoiceSupplementSelect -> {
                
                // If filter text is empty, display all Supplements as usual.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                   // Compare every Supplement with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (String.valueOf(InvoiceSupplementSelect.getInvSuplId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Client ID.
                }
                else if (String.valueOf(InvoiceSupplementSelect.getInvSuplPrice()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Client ID.
                }

                return false; // Does not match.
            });
                 //Wrap the FilteredList in a SortedList. 
                SortedList<InvoiceSupplementSelect> sortedData = new SortedList<>(filteredData);

                //Bind the SortedList comparator to the TableView comparator.
                sortedData.comparatorProperty().bind(table.comparatorProperty());

                // Add sorted (and filtered) data to the table.
                table.setItems(sortedData);
        });
        
        
        //Setup the Table Columns
         //Only gets the InvSuplId and InvSuplPrice fields
        TableColumn<InvoiceSupplementSelect,String> suplIdCol = new TableColumn<>("Suppl ID");
        suplIdCol.setCellValueFactory(new PropertyValueFactory("InvSuplId"));
        
        TableColumn<InvoiceSupplementSelect,Double> suplPriceCol = new TableColumn<>("Supl Price");
        suplPriceCol.setCellValueFactory(new PropertyValueFactory("InvSuplPrice"));
   
        //Add the Columns to the table
        table.getColumns().add(suplIdCol); 
        table.getColumns().add(suplPriceCol); 
        
        //Set Onclick Listener for Table
        table.setOnMouseClicked((MouseEvent event) -> {
        if (event.getClickCount() > 0 && check == 0) //Single click selects a supplement
        {
            onSelectSupplement();
            check++;
        }
    });

        return tablePane;
    }

    public InvoiceSupplementSelect displaySupplementListSearch()
    {
        ObservableList<InvoiceSupplementSelect> list = getInvSuplList();
        GridPane tableSupl = CreateSupplementListTable(list);
        tableSupl.setPadding(new Insets(0, 0, 0, 20));
        Stage SuplListWindow = new Stage();
        
        SuplListWindow.initModality(Modality.APPLICATION_MODAL);
        SuplListWindow.setTitle("Select Supplement");
        
          Button closeBtn = new Button("Cancel");
        Button saveBtn = new Button("Save");
        Button clearBtn = new Button("Clear");
        
         clearBtn.setOnAction(e -> {
             txtQty.clear();
             txtPriceTotal.clear();
             txtPriceTotalConsult.clear();
         });
         
          closeBtn.setOnAction(e -> {
            result = null;
            SuplListWindow.close();
        });
        
         saveBtn.setOnAction(e -> {
              if(!ValidateFields.validateIntegerNumberTextField(txtQty.getText())) 
            {
                txtQty.requestFocus();
                txtQty.setStyle("-fx-border-color: red;");
                txtError.setText(qtyErrMsg);
            }else
            {
                txtQty.setStyle("-fx-border-color: transparent;");
                txtError.setText("");
                result = selectedSupplement; //save the supplement details and return to calling function
                SuplListWindow.close();
            }
        });
         
         TilePane btnTile = new TilePane(closeBtn,saveBtn, clearBtn);
         btnTile.setOrientation(Orientation.HORIZONTAL);
         btnTile.setHgap(10);
        layout.add(tableSupl, 0, 0);
        layout.add(txtError, 0, 1);
        layout.add(btnTile, 0, 5);
        
        Scene scene = new Scene(layout);
        SuplListWindow.setScene(scene);
        SuplListWindow.showAndWait();
       
        return result;
    }
    //Only gets the InvSuplId and InvSuplPrice fields
        ObservableList<InvoiceSupplementSelect> getInvSuplList() {
        ObservableList<InvoiceSupplementSelect> list = DatabaseMethods.getListOfSupplementsForInvoice();
        return list;
    }
    
    public void onSelectSupplement() {
        
         if(table.getSelectionModel().getSelectedItem() == null)
                {
//                error++;
                table.requestFocus();
                table.setStyle("-fx-border-color: red;");

                txtError.setText("Click to select a Supplement");
//                return error;
            }else
            {
                selectedSupplement = table.getSelectionModel().getSelectedItem();
                System.out.println("\n Table Selected == "+selectedSupplement.getInvSuplId());
                table.setStyle("-fx-border-color: transparent;");
                txtError.setText("");
                CreateSuplQuantityPricePane();
            }
}

    private void CreateSuplQuantityPricePane() {
//        int qty = 0;
        Label lblQty = new Label("Qty: ");
        Label lblPrice = new Label("Price: ");
        Label lblPriceTotal = new Label("Total Price: ");
        Label lblPriceTotalConsult = new Label("Total Price with Consult: ");
        GridPane qtyPane = new GridPane();
        Tooltip toolT = new Tooltip("Please choose how many of this supplement to add, "
                + "choose only a valid number");
        Tooltip.install(qtyPane, toolT);
       
        txtQty.setStyle("-fx-border-color: red;");
        txtQty.requestFocus();
           txtError.setText(qtyErrMsg);
           
        txtQty.setOnKeyReleased((event) ->{
          calculatePrices();
           txtPrice.setText(String.valueOf(df.format(selectedSupplement.getInvSuplPrice())));
           txtPriceTotal.setText(String.valueOf(df.format(selectedSupplement.getInvSuplPriceTotal())));
           txtPriceTotalConsult.setText(String.valueOf(df.format(selectedSupplement.getInvTotPriceSuplConsult())));
            //clear the errors
            txtQty.setStyle("-fx-border-color: transparent;");
           txtError.setText("");
        });
        
        //If Super user, edit the price
        if(LoginPage.userGroup == 1)
        {
            txtPrice.setEditable(true);
            txtPrice.setOnKeyReleased((event) ->{
            if(txtQty.getText() != null)
            {
                calculatePrices();

                txtPriceTotal.setText(String.valueOf(df.format(selectedSupplement.getInvSuplPriceTotal())));
                txtPriceTotalConsult.setText(String.valueOf(df.format(selectedSupplement.getInvTotPriceSuplConsult())));
                //clear the errors
                txtQty.setStyle("-fx-border-color: transparent;");
                txtError.setText("");
            }
            else
                {
                    txtQty.setStyle("-fx-border-color: red;");
                    txtError.setText(qtyErrMsg);
                }
            });
        }
        
        qtyPane.add(lblQty, 0,0);
        qtyPane.add(lblPrice, 0,1);
        qtyPane.add(lblPriceTotal, 0,2);
        qtyPane.add(lblPriceTotalConsult, 0,3);
      
        qtyPane.add(txtQty,1,0);
        qtyPane.add(txtPrice,1,1);
        qtyPane.add(txtPriceTotal,1,2);
        qtyPane.add(txtPriceTotalConsult,1,3);
        layout.add(qtyPane, 0, 4);
    }

    private void calculatePrices() {
         //calculate and Save the Price values
         String inQty = txtQty.getText();
         String inPrice = txtPrice.getText();
        
         txtQty.setOnKeyTyped(event ->{
        int maxCharacters = 2;
        if(txtQty.getText().length() > maxCharacters) event.consume();
    });
         
          if(!ValidateFields.validateIntegerNumberTextField(inQty)) 
            {
                txtQty.requestFocus();
                txtQty.setStyle("-fx-border-color: red;");
                txtPriceTotal.setText("");
                txtPriceTotalConsult.setText("");
                txtError.setText("Please select valid quantity");
            }else
            {
                //clear the errors
                txtQty.setStyle("-fx-border-color: transparent;");
                txtError.setText("");

                //do the calculations and save the values
                int qty = Integer.parseInt(inQty);
                double price = selectedSupplement.getInvSuplPrice();
                double priceTotal = qty*price;
                double priceTotalConsult = priceTotal+this.consultFee;
                
                selectedSupplement.setInvSuplQty(qty); //save the value
                selectedSupplement.setInvSuplPrice(price); //save the value
                selectedSupplement.setInvSuplPriceTotal(priceTotal); //save the value
                selectedSupplement.setInvTotPriceSuplConsult(priceTotalConsult); //save the value
            }
          
          if(!ValidateFields.validateNumberTextField(inPrice)) 
            {
                txtPrice.requestFocus();
                txtPrice.setStyle("-fx-border-color: red;");
                txtPriceTotal.setText("");
                txtPriceTotalConsult.setText("");
                txtError.setText("Please select valid Price");
            }else
            {
                //clear the errors
                txtPrice.setStyle("-fx-border-color: transparent;");
                txtError.setText("");

                //do the calculations and save the values
                int qty = Integer.parseInt(inQty);
                double price = Double.parseDouble(inPrice);
                double priceTotal = qty*price;
                double priceTotalConsult = priceTotal+this.consultFee;
                
                selectedSupplement.setInvSuplQty(qty); //save the value
                selectedSupplement.setInvSuplPrice(price); //save the value
                selectedSupplement.setInvSuplPriceTotal(priceTotal); //save the value
                selectedSupplement.setInvTotPriceSuplConsult(priceTotalConsult); //save the value
            }
    }
}
