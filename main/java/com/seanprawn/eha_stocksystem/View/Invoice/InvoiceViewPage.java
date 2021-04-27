/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Invoice;

import com.seanprawn.eha_stocksystem.Controller.DatabaseMethods;
import com.seanprawn.eha_stocksystem.Controller.ValidateFields;
import static com.seanprawn.eha_stocksystem.Controller.ValidateFields.validateNumberTextField;
import com.seanprawn.eha_stocksystem.MainApp;
import com.seanprawn.eha_stocksystem.Model.Client;
import com.seanprawn.eha_stocksystem.Model.Invoice;
import com.seanprawn.eha_stocksystem.Model.InvoiceSupplement;
import com.seanprawn.eha_stocksystem.Model.InvoiceSupplementSelect;
import com.seanprawn.eha_stocksystem.Model.Messages;
import com.seanprawn.eha_stocksystem.View.Alerts;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class InvoiceViewPage extends Messages{
    public void InvoiceViewPage()
    {
    
    }
    //Create all layout nodes
    GridPane invoiceGridPage = new GridPane();
    FlowPane invoiceViewPage = new FlowPane();
    TilePane btnTile = new TilePane();
    TilePane pdfTile = new TilePane();
    ScrollPane scroll = new ScrollPane();
    ScrollPane scrollNew = new ScrollPane();
    GridPane invSuplPane = new GridPane();
    
    //Create objects
    Invoice selectedInvoice;
    Invoice saveInv = new Invoice();
    InvoiceSupplementSelect saveSuplSelect;
    public static int viewInvoice;
    double consultPrice;
    List<Label> labels = new ArrayList<>();
    List<TextField> textFields = new ArrayList<>();
    int invSuplCount = 0;
    
    //Create String Objects
    String errorMessage = "";
    String priceErrMsg = "Please enter a valid Price Range";
    String defaultError = "Please enter correct data in the fields above";   
    String errSave = "Error";
    String succSave = "Success";
    Format formatter = new SimpleDateFormat("yyyy-mm-dd");
    
    //Create buttons
    Button btnSave = new Button("Save Changes");
    Button btnClear = new Button("Clear Fields");
    Button btnClose = new Button("Close");
    CheckBox chckSupplBox = new CheckBox("Click to Add Supplements");
    Label lblSuplAmt = new Label("How Many Supplements to add?");
    TextField supplAmount = new TextField();
    Button btnGenerate = new Button("Generate Invoice PDF");

    //Create Labels and textFields
    Label lblInvId = new Label("Invoice ID");
    Label lblInvNum = new Label("Invoice Number");
    Label lblInvDate = new Label("Date");
    Label lblInvClientId = new Label("Client ID");
    Label lblInvConsultFee = new Label("Consult Fee");

    TextField txtInvId = new TextField();
    TextField txtInvNum = new TextField();
    TextField txtInvDate = new TextField();
    DatePicker datePick = new DatePicker(LocalDate.now());
    TextField txtInvClientId = new TextField();
    TextField txtInvConsultFee = new TextField();
    
    Label txtError = new Label();
    DecimalFormat df = new DecimalFormat("#.00");
    
    public FlowPane getInvoiceViewPage()
    {
        return this.invoiceViewPage;
    }
    public void setInvoiceViewPage(FlowPane pane)
    {
        this.invoiceViewPage = pane;
    }
    
     public void setSelectedInvoice(Invoice inv)
    {
        this.selectedInvoice = inv;
    }
 
    public FlowPane createInvoicePage()
    {
        //setup tooltips
       Tooltip clickTip = new Tooltip("Click to select a Supplement");
       Tooltip.install(chckSupplBox, clickTip);
       Tooltip dateTip = new Tooltip("Click to select the date for this Invoice");
       Tooltip.install(datePick, dateTip);
       Tooltip backTip = new Tooltip("Click to close this component and go back to list");
       Tooltip.install(btnClose, backTip);
       Tooltip saveTip = new Tooltip("Click to Save this Invoice to the database");
       Tooltip.install(btnSave, saveTip);
       Tooltip clearTip = new Tooltip("Click to Clear all fields and start again");
       Tooltip.install(btnClear, clearTip);
       Tooltip pdfTip = new Tooltip("Click to Download the Invoice as a PDF document");
       Tooltip.install(btnGenerate, pdfTip);

        
        txtInvClientId.editableProperty().set(false);
        txtError.setId("error");
        scroll.setMinSize(380, 300);
        scroll.setPrefSize(380, 350);
        scroll.setMaxSize(380, 500);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        scrollNew.setMinSize(380, 300);
        scrollNew.setPrefSize(380, 350);
        scrollNew.setMaxSize(380, 500);
        scrollNew.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollNew.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        addAllFieldsToLayout();

        //OnClick Handlers for Buttons
       btnClose.setOnAction((event)->{
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           stage.close();
       });
       
        //Clears all the text fields
       btnClear.setOnAction((event)->{
            txtInvNum.clear();
            supplAmount.clear();
            chckSupplBox.selectedProperty().set(Boolean.FALSE);
            txtInvDate.clear();
            datePick.setValue(LocalDate.now());
            txtInvClientId.clear();
            txtInvConsultFee.clear();
            invSuplCount = 0;
            textFields.clear(); //Clear the Textfield array
            labels.clear(); // Clear the Label array
            invSuplPane.getChildren().clear(); // Clear all previous Supplements in list
            scrollNew.setContent(null);
       });
       
       chckSupplBox.setOnAction((event)->{
           if(chckSupplBox.isSelected() == true)
           {
                if(!validateNumberTextField(txtInvConsultFee.getText())) //User must first enter a consult
                {
                    txtInvConsultFee.requestFocus();
                    txtInvConsultFee.setStyle("-fx-border-color: red;");

                    txtError.setText(priceErrMsg);
                    chckSupplBox.selectedProperty().set(false);
                 }else
                 {
                     //clear the errors
                    txtInvConsultFee.setStyle("-fx-border-color: transparent;");
                    txtError.setText("");
                    consultPrice = Double.valueOf(txtInvConsultFee.getText());// Save the consult fee to pass to list search

                    //Create the search Table for Selecting a supplement
                    InvSupplementListSearchTable searchSupl = new InvSupplementListSearchTable();
                    searchSupl.SetInvoiceConsultFee(consultPrice); //pass the consult fee for the calculations
                    InvoiceSupplementSelect supl = searchSupl.displaySupplementListSearch();

                    if(supl == null)
                    {
                        errorMessage = "Nothing Selected";
                        txtError.setText(errorMessage);
                        chckSupplBox.selectedProperty().set(false);
                    }else
                    {
                        addSupplementToInvoiceSupplementList(supl); //Adds the supplement to the invoice supplement list
                        chckSupplBox.selectedProperty().set(false);
                    }
                }

           }
           else if(chckSupplBox.isSelected() == false)
           {
               invSuplCount = 0;
           }
       });
       
       btnSave.setOnAction((event)->{
           int result = 0;
           boolean check = false;
           
           int valid = ValidateAndGetInvoiceFields();
           ObservableList<InvoiceSupplement> supls = getAllSuplsFromInvoice(); 

           
           if(valid == 0 && supls != null) 
           {
               check = Alerts.displayVerifyAlert("Save Invoice", "Are You sure you want to Save Invoice number  "+saveInv.getInvNum()+"?");
           }
           if(check) 
           {
               result = DatabaseMethods.SaveNewInvoice(saveInv, supls);
              
               if(result>0)
               {
                   Alerts.displayAlert(succSave, "Invoice Saved Successfully");
                   Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    MainApp.refresh = true;
                   stage.close();
               }
               else{
                 Alerts.displayAlert(errSave, "Invoice Failed to Save, please try again");
               }
           }
       });
       
                txtInvNum.setOnKeyTyped(event ->{
        int maxCharacters = 9; // Only allow this many characters
        if(txtInvNum.getText().length() > maxCharacters) event.consume();//stops the user from entering any more characters
    });
       
       btnGenerate.setOnAction((event) ->{
          ObservableList<InvoiceSupplement> suplViewList = DatabaseMethods.getInvoiceSupplements(selectedInvoice);

          InvoiceGeneratePdfPage pdfPage = new InvoiceGeneratePdfPage();
          int gen = pdfPage.displayPDFPage(selectedInvoice, suplViewList);
          if(gen >0) 
          {
              Alerts.displayAlert(succSave, "PDF Saved Succesfully \n");
          }else{
              Alerts.displayAlert(errSave, "PDF not saved, please try again \n");
          }
       });
       
       //Allow user to search and select a client ID from the database
       txtInvClientId.setOnMousePressed((event) -> {
//           System.out.println("\n\n InvClientId editable "+txtInvClientId.editableProperty().getValue());
           if(MainApp.InvFlag == Boolean.TRUE)
           {
               InvClientListSearchTable clientTable = new InvClientListSearchTable();
               Client selectedClient = InvClientListSearchTable.displayClientListSearch();
               if(selectedClient != null) txtInvClientId.setText(String.valueOf(selectedClient.getClId()));
           }
       });
       
        return invoiceViewPage;
    }

      public void SetTextFieldValues() {
       txtInvId.setText(String.valueOf(this.selectedInvoice.getInvId()));
       txtInvNum.setText(String.valueOf(this.selectedInvoice.getInvNum()));
       txtInvDate.setText(String.valueOf(this.selectedInvoice.getInvDate()));
       txtInvClientId.setText(String.valueOf(this.selectedInvoice.getInvClientId()));
       txtInvConsultFee.setText(String.valueOf(df.format(this.selectedInvoice.getInvConsultFee())));
       getListAndDisplayInvoiceSupplements(); // Display all Supplements linked to this specific invoice
    }
    
    private void addAllFieldsToLayout() {
        //Add Buttons to TilePane
        btnTile.setHgap(2);
        btnTile.setMaxWidth(400);
        btnTile.setOrientation(Orientation.HORIZONTAL);
        btnTile.getChildren().addAll( btnGenerate, btnClear, btnSave, btnClose);//Add buttons to tilepane
//        pdfTile.getChildren().addAll(buttonDir, textDir);
        txtInvClientId.setEditable(false);
        
        invoiceGridPage.setId("text");

        invoiceViewPage.setOrientation(Orientation.VERTICAL);
        invoiceViewPage.setVgap(10);

        invoiceGridPage.add(lblInvId, 0, 1);
        invoiceGridPage.add(txtInvId, 1, 1);
        invoiceGridPage.add(lblInvNum, 0, 2);
        invoiceGridPage.add(txtInvNum, 1, 2);
        invoiceGridPage.add(lblInvDate, 0, 3);
        invoiceGridPage.add(txtInvDate, 1, 3); //Ony view the date as a text
        invoiceGridPage.add(datePick, 1, 3); //edit the date as DatePicker
        invoiceGridPage.add(lblInvClientId, 0, 4);
        invoiceGridPage.add(txtInvClientId, 1, 4);
        invoiceGridPage.add(lblInvConsultFee, 0, 5);
        invoiceGridPage.add(txtInvConsultFee, 1, 5);
        invoiceGridPage.add(txtError, 1, 6);
        invoiceGridPage.add(chckSupplBox, 1, 7);
    }

    private int ValidateAndGetInvoiceFields() {
         int error = 0;
        
        if(!ValidateFields.validateStringCharTextField(txtInvNum.getText())) 
        {
            error++;
            txtInvNum.requestFocus();
            txtInvNum.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_INV_NUM);
            return error;
        }else
        {
            txtInvNum.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveInv.setInvNum(txtInvNum.getText()); //Save the value to supplement object
        }
        
            if(datePick.getValue() == null) //(datePick.getValue().toString())) 
            {
                error++;
                datePick.requestFocus();
                datePick.setStyle("-fx-border-color: red;");

                txtError.setText(ERR_INV_DATE);
                return error;
            }else
            {
                datePick.setStyle("-fx-border-color: transparent;");
                txtError.setText("");
                saveInv.setInvDate(datePick.getValue()); //Save the value to supplement object
            }
        
         if(!ValidateFields.validateClientUniqueIdTextField(txtInvClientId.getText())) 
            {
                error++;
                txtInvClientId.requestFocus();
                txtInvClientId.setStyle("-fx-border-color: red;");

                txtError.setText(ERR_ID);
                return error;
            }else
            {
                txtInvClientId.setStyle("-fx-border-color: transparent;");
                txtError.setText("");
                saveInv.setInvClientId(new BigInteger(txtInvClientId.getText())); //save the value
            }

         
           if(!validateNumberTextField(txtInvConsultFee.getText())) 
        {
            error++;
            txtInvConsultFee.requestFocus();
            txtInvConsultFee.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLEMENT_PRICE);
            return error;
        }else
        {
            txtInvConsultFee.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveInv.setInvConsultFee(Double.valueOf(txtInvConsultFee.getText())); //save the value
        }
           
              if(textFields.isEmpty()) // User must choose at least 1 supplement for the invoice
        {
            error++;
            chckSupplBox.requestFocus();
            chckSupplBox.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_INV_ADD_SUPLS);
            return error;
        }else
        {
            chckSupplBox.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
        }        
        return error;
    }

    public void SetTextFieldsEditable(Boolean flag) {
        txtInvId.setEditable(flag);
        txtInvNum.setEditable(flag);
        txtInvClientId.setEditable(flag);
        txtInvConsultFee.setEditable(flag);
        txtInvDate.setEditable(flag);
        MainApp.InvFlag = flag;
    }
    
         public void setInvoiceView(){
        btnClear.setVisible(false);
       btnSave.setVisible(false);
        txtInvDate.setVisible(true);
        datePick.setVisible(false);
        invoiceViewPage.setOrientation(Orientation.VERTICAL);
        chckSupplBox.setVisible(Boolean.FALSE);
        lblSuplAmt.setVisible(false);
        supplAmount.setVisible(false);
//        buttonDir.setVisible(true);
//        textDir.setVisible(true);
        this.SetTextFieldsEditable(Boolean.FALSE);
        SetTextFieldValues();
        invoiceViewPage.getChildren().add(pdfTile);

    }
         
      public void setInvoiceNew(){
        btnClear.setVisible(Boolean.TRUE);
       btnSave.setVisible(Boolean.TRUE);
        txtInvDate.setVisible(Boolean.FALSE);
        datePick.setVisible(Boolean.TRUE);
        invoiceViewPage.setOrientation(Orientation.VERTICAL);
        chckSupplBox.setVisible(Boolean.TRUE);
        supplAmount.setVisible(Boolean.TRUE);
        lblInvId.setVisible(false);
        this.SetTextFieldsEditable(Boolean.TRUE);
//        buttonDir.setVisible(false);
//        textDir.setVisible(false);

        invoiceViewPage.getChildren().add(invoiceGridPage);
        invoiceViewPage.getChildren().add(scrollNew);
        invoiceViewPage.getChildren().add(btnTile);

    }

    private void getListAndDisplayInvoiceSupplements() {
                //Gets the Supplements and adds to layout
        ObservableList<InvoiceSupplement> list = DatabaseMethods.getInvoiceSupplements(selectedInvoice);
        int listSize = list.size(); // How many supplements are in the list
        int listLabelSize = listSize*5; // how many rows per supplement
        GridPane invSuplGPane = new GridPane();
        
        Label labelsView[] = new Label[listLabelSize];
        Text textFieldsView[] = new Text[listLabelSize];
        
        if(listSize > 0)
        {
            for(int i = 0;i<listSize;i++)
            {
             //Create Labels 
            Label lblInvSuplHeading = new Label("Invoice Supplement "+(i+1));
            lblInvSuplHeading.setStyle("-fx-underline:true; -fx-font-weight: bold;");

            labelsView[i*5] = new Label("Supplement ID: ");
            labelsView[(i*5)+1] = new Label("Supplement Price:  ");
            labelsView[(i*5)+2] = new Label("Supplement qty:  ");
            labelsView[(i*5)+3] = new Label("Supplement Price Total:  ");
            labelsView[(i*5)+4] = new Label("Supplement Price with Consult:  ");
           
            // Create the TextFields and fetch data
            textFieldsView[i*5] = new Text(list.get(i).getInvSuplId());
            textFieldsView[(i*5)+1] = new Text("R "+String.valueOf(df.format(list.get(i).getInvSuplPrice())));
            textFieldsView[(i*5)+2] = new Text(list.get(i).getInvSuplQty().toString());
            textFieldsView[(i*5)+3] = new Text("R "+String.valueOf(df.format(list.get(i).getInvSuplPriceTotal())));
            textFieldsView[(i*5)+4] = new Text("R "+String.valueOf(df.format(list.get(i).getInvTotPriceSuplConsult())));

            //Add Labels to inner layout
            invSuplGPane.add(lblInvSuplHeading, 0, i*7); // Heading first row
            invSuplGPane.add(labelsView[i*5], 0, (i*7)+1); // Increment each row under the next
            invSuplGPane.add(labelsView[(i*5)+1], 0, (i*7)+2);
            invSuplGPane.add(labelsView[(i*5)+2], 0, (i*7)+3);
            invSuplGPane.add(labelsView[(i*5)+3], 0, (i*7)+4);
            invSuplGPane.add(labelsView[(i*5)+4], 0, (i*7)+5);
            invSuplGPane.add(new Label(), 0, (i*7)+6); // Add extra row for spacing
            invSuplGPane.add(new Label(), 0, (i*7)+7); // Add extra row for spacing
            
            //Add TextFields to inner layout
            invSuplGPane.add(textFieldsView[i*5], 1, (i*7)+1);
            invSuplGPane.add(textFieldsView[(i*5)+1], 1, (i*7)+2);
            invSuplGPane.add(textFieldsView[(i*5)+2], 1, (i*7)+3);
            invSuplGPane.add(textFieldsView[(i*5)+3], 1, (i*7)+4);
            invSuplGPane.add(textFieldsView[(i*5)+4], 1, (i*7)+5);
            invSuplGPane.add(new Text(), 1, (i*7)+6); // Add extra row for spacing
            invSuplGPane.add(new Text(), 1, (i*7)+7); // Add extra row for spacing
//                System.out.println("Loop "+i);
            }
            

            scroll.setContent(invSuplGPane);//Add Labels and TextFields to the outer layout

        invoiceViewPage.getChildren().add(invoiceGridPage);
        invoiceViewPage.getChildren().add(scroll);
        invoiceViewPage.getChildren().add(btnTile);
        }else 
        {
            invoiceGridPage.add(new Text("No Supplements to display"), 0, 8);
        }
    }

    private void addSupplementToInvoiceSupplementList(InvoiceSupplementSelect supl) {
        GridPane suplGrid = new GridPane();
        
        //Create Labels 
        Label lblInvSuplHeading = new Label("Invoice Supplement "+(invSuplCount+1));
        lblInvSuplHeading.setStyle("-fx-underline:true; -fx-font-weight: bold;");
//                   
        labels.add(new Label("Supplement ID: "));
        labels.add(new Label("Supplement Price:  "));
        labels.add(new Label("Supplement Qty:  "));
        labels.add(new Label("Supplement Price Total:  "));
        labels.add(new Label("Supplement Price with Consult:  "));

        //create Textfields
        textFields.add(new TextField(supl.getInvSuplId()));
        textFields.add(new TextField(String.valueOf(df.format(supl.getInvSuplPrice()))));
        textFields.add(new TextField(String.valueOf(supl.getInvSuplQty())));
        textFields.add(new TextField(String.valueOf(df.format(supl.getInvSuplPriceTotal()))));
        textFields.add(new TextField(String.valueOf(df.format(supl.getInvTotPriceSuplConsult()))));

//       //Add Labels to inner layout
        suplGrid.add(lblInvSuplHeading, 0, 0); // Heading first row
        suplGrid.add(labels.get((invSuplCount*5)), 0, 1); // Increment each row under the next
        suplGrid.add(labels.get((invSuplCount*5)+1), 0, 2);
        suplGrid.add(labels.get((invSuplCount*5)+2), 0, 3);
        suplGrid.add(labels.get((invSuplCount*5)+3), 0, 4);
        suplGrid.add(labels.get((invSuplCount*5)+4), 0, 5);
        suplGrid.add(new Label(), 0, 6); // Add extra row for spacing
        suplGrid.add(new Label(), 0, 7); // Add extra row for spacing
//
        //Add TextFields to inner layout
        suplGrid.add(textFields.get(invSuplCount*5), 1, 1);
        suplGrid.add(textFields.get((invSuplCount*5)+1), 1, 2);
        suplGrid.add(textFields.get((invSuplCount*5)+2), 1, 3);
        suplGrid.add(textFields.get((invSuplCount*5)+3), 1, 4);
        suplGrid.add(textFields.get((invSuplCount*5)+4), 1, 5);
        suplGrid.add(new Text(), 1, 6); // Add extra row for spacing
        suplGrid.add(new Text(), 1, 7); // Add extra row for spacing
        invSuplPane.add(suplGrid, 0 ,invSuplCount);
        scrollNew.setContent(invSuplPane);//Add Labels and TextFields to the outer layout

        for(int j = 0;j<4;j++)
       {
        System.out.println("\n Inv Array No: "+invSuplCount+" - "+labels.get((invSuplCount*5)+j).getText()+" - "+textFields.get((invSuplCount*5)+j).getText()+"\n");
       }
        invSuplCount++;
    }

    //Gets all the Supplement details from the TextFields array and adds to a list
    //returns it as an ObservableList of type SupplementInvoice
    private ObservableList<InvoiceSupplement> getAllSuplsFromInvoice() {
        ObservableList<InvoiceSupplement> list;
        list = FXCollections.observableArrayList();
        if(!textFields.isEmpty())
        {
            System.out.println("\n\n TextFields SIZE == "+textFields.size());
            for (int i = 0; i < (textFields.size() / 5); i++) { // All Inv Supplements have 5 elements
                InvoiceSupplement supl = new InvoiceSupplement();
                supl.setInvSuplId(textFields.get(i*5).getText());
                supl.setInvSuplPrice(Double.valueOf(textFields.get((i*5)+1).getText()));
                supl.setInvSuplQty(Integer.valueOf(textFields.get((i*5)+2).getText()));
                supl.setInvSuplPriceTotal(Double.valueOf(textFields.get((i*5)+3).getText()));
                supl.setInvTotPriceSuplConsult(Double.valueOf(textFields.get((i*5)+4).getText()));
                list.add(supl);
            }
        } 
        return list;
    }
    

 
}
//}
