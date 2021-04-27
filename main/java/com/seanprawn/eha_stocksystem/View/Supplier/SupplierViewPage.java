/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Supplier;

import com.seanprawn.eha_stocksystem.Controller.DatabaseMethods;
import com.seanprawn.eha_stocksystem.Model.Supplier;
import com.seanprawn.eha_stocksystem.Controller.ValidateFields;
import static com.seanprawn.eha_stocksystem.Controller.ValidateFields.validateNumberTextField;
import com.seanprawn.eha_stocksystem.MainApp;
import com.seanprawn.eha_stocksystem.Model.Messages;
import com.seanprawn.eha_stocksystem.View.Alerts;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 * ** Properties *********************************
 * supplier_id - int
 * supplier_code - text
 * supplier_contact_person - text
 * supplier_tel = text
 * supplier_cell - text
 * supplier_fax - text
 * supplier_email - text
 * supplier_bank - text
 * supplier_branch_code - int
 * supplier_account_number - int
 * supplier_account_type - text
 * supplier_comments - text
 */
public class SupplierViewPage extends Messages {
    
    GridPane supplierGrid = new GridPane();
    FlowPane supplierView = new FlowPane();
    Supplier selectedSupplier;
    boolean checkEdit = false; // Toggles the Edit Button to set for Edit or for Save new - false = edit , true = save new
    boolean save_edit = false; // if false - Then save new else if True - then edit supplier
    Supplier saveSupl = new Supplier();
    String errorMessage = "Please enter correct data in the fields above";
    
    //Setup the buttons
    Button btnEdit = new Button("Edit Supplier");
    Button btnDelete = new Button("Delete");
    Button btnClear = new Button("Clear Fields");
    Button btnClose = new Button("Close");
    Button btnSave = new Button("Save Changes");
    
    //Setup the text fields
       Text txtSuplId = new Text();
       TextField txtSuplCode = new TextField();
       TextField txtSuplContact = new TextField();
       TextField txtSuplTel= new TextField();
       TextField txtSuplCell = new TextField();
       TextField txtSuplFax = new TextField();
       TextField txtSuplEmail = new TextField();
       TextField txtSuplBank = new TextField();
       TextField txtSuplBranchCode = new TextField();
       TextField txtSuplAccNum =new TextField();
       TextField txtSuplAccType = new TextField();
       TextField txtSuplComments = new TextField();
       Label txtError = new Label();
       
       
              //Setup the Labels
       Label lblSuplId = new Label("Supplier ID:");
       Label lblSuplCode = new Label("Supplier Code:");
       Label lblSuplContact = new Label("Contact:");
       Label lblSuplTel = new Label("Tel:");
       Label lblSuplCell = new Label("Cell:");
       Label lblSuplFax = new Label("Fax:");
       Label lblSuplEmail = new Label("Email");
       Label lblSuplBank = new Label("Bank");
       Label lblSuplBranchCode = new Label("Branch code:");
       Label lblSuplAccNum  = new Label("Account:");
       Label lblSuplAccType = new Label("Account Type");
       Label lblSuplComments = new Label("Comments");
       
       
    public FlowPane getSupplierView()
    {
        return this.supplierView;
        
    }
    
    public void setSupplierView(FlowPane supplier)
    {
        this.supplierView = supplier;
    }
    
    public void SupplierViewPage()
    {
        
    }
    
    public void setSelectedSupplier(Supplier supplier)
    {
        this.selectedSupplier = supplier;
    }
    
    
    /**
     * Sets up the Nodes, and adds them to a GridPane layout
     * The layout is returned to the calling method
     * @return GridPane
     */
    public FlowPane setupViewSupplier()
   {
        Tooltip toggleTip = new Tooltip("Click to toggle Edit/ View");
       Tooltip.install(btnEdit, toggleTip);
       Tooltip backTip = new Tooltip("Click to close this component and go back to list");
       Tooltip.install(btnClose, backTip);
       Tooltip saveTip = new Tooltip("Click to Save this Supplier to the database");
       Tooltip.install(btnSave, saveTip);
       Tooltip delTip = new Tooltip("Click to Delete this Supplier from the database");
       Tooltip.install(btnDelete, delTip);
       Tooltip clearTip = new Tooltip("Click to Clear all fields and start again");
       Tooltip.install(btnClear, clearTip);
//       supplierView.setId("root");
        //These buttons are not visible at first
       btnClear.setVisible(false);
       btnSave.setVisible(false);
       txtError.setId("error");
       SetTextFieldsEditable(false);
//       txtSupNum.setEditable(false); //User may not edit the pk
       
       //Set the values from Selected supplier
//       SetTextFieldValues();

       //Add Labels and TextFields to layout
       AddAllToLayout();
       
       
       //OnClick Handlers for Buttons
       btnClose.setOnAction((event)->{
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           stage.close();
       });
       
       btnClear.setOnAction((event)->{
        //Clears all the text fields
//        txtSuplId ;
        txtSuplCode.clear();
        txtSuplContact.clear();
        txtSuplTel.clear();
        txtSuplCell.clear();
        txtSuplFax.clear();
        txtSuplEmail.clear();
        txtSuplBank.clear();
        txtSuplBranchCode.clear();
        txtSuplAccNum.clear();
       txtSuplAccType.clear();
       txtSuplComments.clear();
       });
       
       btnDelete.setOnAction((event)->{
            Supplier supl = getEditedTextFieldValues();
            int result = 0;
            Boolean check = Alerts.displayVerifyAlert("Delete Supplier", "Are You sure you want to delete supplier: "+supl.getSuplId()+"?");
            if (check) 
            {    
                result = DatabaseMethods.DeleteSupplierInfo(supl);
                if(result>0)
                {
                    Alerts.displayAlert("Success", "Supplier Deleted Successfully");
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    MainApp.refresh = true;
                    stage.close();
                }
                else{
                    Alerts.displayAlert("Failed", "Supplier Failed to Delete, please try again");
                }
            }
       });
       
       btnEdit.setOnAction((event)->{
           if(!checkEdit)
           {
               btnEdit.setText("View Supplier");
               btnSave.setVisible(true);
               checkEdit = true;
               save_edit = true;
               MainApp.refresh = true;
               SetTextFieldValues();
               SetTextFieldsEditable(Boolean.TRUE); // Allow user to edit
               System.out.println("\n Save/Edit == "+save_edit);
           }
           else if(checkEdit)
         {
               btnEdit.setText("Edit Supplier");
               btnSave.setVisible(false);
               checkEdit = false;
               save_edit = false;
               SetTextFieldsEditable(Boolean.FALSE); // User can only view
               System.out.println("\n Save/Edit == "+save_edit);

           }
        });
       
       btnSave.setOnAction((event)->{
           int valid = ValidateAndGetSupplierFields();
           int result = 0;
           boolean check = false;
           if(valid == 0) 
           {
               check = Alerts.displayVerifyAlert("Save Supplier", "Are You sure you want to Save : "+saveSupl.getSuplCode()+"?");
           }
           if(check) 
           {
              if(!save_edit) result = DatabaseMethods.SaveNewSupplierInfo(saveSupl);
              else result = DatabaseMethods.EditSupplierInfo(saveSupl);
              
               if(result>0)
               {
                   Alerts.displayAlert("Success", "Supplier Saved Successfully");
                   Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    MainApp.refresh = true;
                   stage.close();
               }
               else{
                 Alerts.displayAlert("Failed", "Supplier Failed to Update, please try again");
               }
           }
       });
       
        return supplierView; 
   }

    public void SetTextFieldValues() {
       txtSuplId.setText(String.valueOf(this.selectedSupplier.getSuplId()));
       txtSuplCode.setText(String.valueOf(this.selectedSupplier.getSuplCode()));
       txtSuplContact.setText(this.selectedSupplier.getSuplContact());
       txtSuplTel.setText(String.valueOf(this.selectedSupplier.getSuplTel()));
       txtSuplCell.setText(String.valueOf(this.selectedSupplier.getSuplCell()));
       txtSuplFax.setText(String.valueOf(this.selectedSupplier.getSuplFax()));
       txtSuplEmail.setText(String.valueOf(this.selectedSupplier.getSuplEmail()));
       txtSuplBank.setText(String.valueOf(this.selectedSupplier.getSuplBank()));
       txtSuplBranchCode.setText(String.valueOf(this.selectedSupplier.getSuplBranchCode()));
       txtSuplAccNum.setText(String.valueOf(this.selectedSupplier.getSuplAccNum()));
       txtSuplAccType.setText(String.valueOf(this.selectedSupplier.getSuplAccType()));
       txtSuplComments.setText(String.valueOf(this.selectedSupplier.getSuplComments()));
    }

       public Supplier getEditedTextFieldValues() {
        Supplier supl = new Supplier();
        if(txtSuplId.getText() != null) supl.setSupplId(Integer.valueOf(txtSuplId.getText()));
        if(txtSuplCode.getText() != null) supl.setSupplCode(txtSuplCode.getText());
        if(txtSuplContact.getText() != null) supl.setSuplContact(txtSuplContact.getText());
        if(txtSuplTel.getText() != null) supl.setSuplTel(txtSuplTel.getText());
        if(txtSuplCell.getText() != null) supl.setSuplCell(txtSuplCell.getText());
        if(txtSuplFax.getText() != null) supl.setSuplFax(txtSuplFax.getText());
        if(txtSuplEmail.getText() != null) supl.setSuplEmail(txtSuplEmail.getText());
        if(txtSuplBank.getText() != null) supl.setSuplBank(txtSuplBank.getText());
        if(txtSuplBranchCode.getText() != null)  supl.setSuplBranchCode(Integer.valueOf(txtSuplBranchCode.getText()));
        if(txtSuplAccNum.getText() != null) supl.setSuplAccNum(Integer.valueOf(txtSuplAccNum.getText()));
        if(txtSuplAccType.getText() != null) supl.setSuplAccType(txtSuplAccType.getText());
        if(txtSuplComments.getText() != null) supl.setSuplComments(txtSuplComments.getText());
        return supl;
    }
    
    public void SetTextFieldsEditable(Boolean flag) {
       txtSuplCode.setEditable(flag);
       txtSuplContact.setEditable(flag);
       txtSuplTel.setEditable(flag);
       txtSuplCell.setEditable(flag);
       txtSuplFax.setEditable(flag);
       txtSuplEmail.setEditable(flag);
       txtSuplBank.setEditable(flag);
       txtSuplBranchCode.setEditable(flag);
       txtSuplAccNum.setEditable(flag);
       txtSuplAccType.setEditable(flag);
       txtSuplComments.setEditable(flag);
    }

    private void AddAllToLayout() {
        
       supplierGrid.add(lblSuplId, 0, 0);
       supplierGrid.add(txtSuplId, 1, 0);
       supplierGrid.add(lblSuplCode, 0, 1);
       supplierGrid.add(txtSuplCode, 1, 1);
       supplierGrid.add(lblSuplContact, 0, 2);
       supplierGrid.add(txtSuplContact, 1, 2);
       supplierGrid.add(lblSuplTel, 0, 3);
       supplierGrid.add(txtSuplTel, 1, 3);
       
       supplierGrid.add(lblSuplCell, 0, 4);
       supplierGrid.add(txtSuplCell, 1, 4);
       supplierGrid.add(lblSuplFax, 0, 5);
       supplierGrid.add(txtSuplFax, 1, 5);
       supplierGrid.add(lblSuplEmail, 0, 6);
       supplierGrid.add(txtSuplEmail, 1, 6);
       
       supplierGrid.add(lblSuplBank, 0, 7);
       supplierGrid.add(txtSuplBank, 1, 7);
       supplierGrid.add(lblSuplBranchCode, 0, 8);
       supplierGrid.add(txtSuplBranchCode, 1, 8);
       supplierGrid.add(lblSuplAccNum, 0, 9);
       supplierGrid.add(txtSuplAccNum, 1, 9);
       
       supplierGrid.add(lblSuplAccType, 0, 10);
       supplierGrid.add(txtSuplAccType, 1, 10);
       supplierGrid.add(lblSuplComments, 0, 11);
       supplierGrid.add(txtSuplComments, 1, 11);
       supplierGrid.add(txtError, 1, 12);
       
       supplierGrid.add(btnClear, 0, 14);
       supplierGrid.add(btnEdit, 1, 14);
       supplierGrid.add(btnClose, 0, 15);
       supplierGrid.add(btnDelete, 1, 15);
       supplierGrid.add(btnSave, 0, 17);
       //Add Buttons to layout
//       TilePane btnPane = new TilePane();
//        btnPane.setHgap(3);
//        btnPane.setOrientation(Orientation.HORIZONTAL);
//        btnPane.getChildren().add(btnClear);
//        btnPane.getChildren().add(btnDelete);
//        btnPane.getChildren().add(btnEdit);
//        btnPane.getChildren().add(btnSave);
//        btnPane.getChildren().add(btnClose);
        supplierView.setOrientation(Orientation.VERTICAL);
        supplierView.setVgap(10);
//        supplierView.getChildren().addAll(supplierGrid, btnPane);
        supplierView.getChildren().addAll(supplierGrid);
//        supplierGrid.add(btnPane,1,14);
//       supplierView.add(btnClear, 2, 13);
//       supplierView.add(btnDelete, 1, 14);
//       supplierView.add(btnEdit, 0, 14);
//       supplierView.add(btnClose, 2, 14);
//       supplierView.add(btnSave, 0, 16);
    }
    
    /**
     * Gets the values from the input fields, and validates them according to type (string, varchar, or Number)
     * If an error is found, the validation process will stop and 
     * focus to the input textbox in question
     * If there is an error then the error Variable gets incremented
     * @return error
     * 0 - Validated
     * 1 - Not valid
     */
    public int ValidateAndGetSupplierFields()
    {
        int error = 0;
       
        if(txtSuplId.getText() == null || txtSuplId.getText().equals("")) 
        {
            saveSupl.setSupplId(Integer.valueOf("0"));
        }else
        {
            saveSupl.setSupplId(Integer.valueOf(txtSuplId.getText())); //pk - not expected to be null!
        }
        
        if(!ValidateFields.validateStringCharTextField(txtSuplCode.getText())) 
        {
            error++;
            txtSuplCode.requestFocus();
            txtSuplCode.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_CODE);
            return error;
        }else
        {
            txtSuplCode.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSupplCode(txtSuplCode.getText()); //Save the value to supplier object
        }
        
        if(!ValidateFields.validateStringCharTextField(txtSuplContact.getText())) 
        {
            error++;
            txtSuplContact.requestFocus();
            txtSuplContact.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_CONTACT);
            return error;
        }else
        {
            txtSuplContact.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplContact(txtSuplContact.getText()); //Save the value to supplier object
        }
        
        
        if(!ValidateFields.validatePhoneNumberTextField(txtSuplTel.getText())) 
        {
            error++;
            txtSuplTel.requestFocus();
            txtSuplTel.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_TEL);
            return error;
        }else
        {
            txtSuplTel.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplTel(txtSuplTel.getText()); //save the value
        }
        
         if(!ValidateFields.validatePhoneNumberTextField(txtSuplCell.getText())) 
        {
            error++;
            txtSuplCell.requestFocus();
            txtSuplCell.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_TEL);
            return error;
        }else
        {
            txtSuplCell.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplCell(txtSuplCell.getText()); //save the value
        }
         
           if(!ValidateFields.validatePhoneNumberTextField(txtSuplFax.getText())) 
        {
            error++;
            txtSuplFax.requestFocus();
            txtSuplFax.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_FAX);
            return error;
        }else
        {
            txtSuplFax.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplFax(txtSuplFax.getText()); //save the value
        }
           
              if(!ValidateFields.validateEmailTextField(txtSuplEmail.getText())) 
        {
            error++;
            txtSuplEmail.requestFocus();
            txtSuplEmail.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_EMAIL);
            return error;
        }else
        {
            txtSuplEmail.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplEmail(txtSuplEmail.getText()); //save the value
        }
              
        if(!ValidateFields.validateStringCharTextField(txtSuplBank.getText())) 
        {
            error++;
            txtSuplBank.requestFocus();
            txtSuplBank.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_BANK);
            return error;
        }else
        {
            txtSuplBank.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplBank(txtSuplBank.getText()); //save the value
        }
        
        if(!ValidateFields.validateIntegerNumberTextField(txtSuplBranchCode.getText())) 
        {
            error++;
            txtSuplBranchCode.requestFocus();
            txtSuplBranchCode.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_BRANCH_CODE);
            return error;
        }else
        {
            txtSuplBranchCode.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplBranchCode(Integer.valueOf(txtSuplBranchCode.getText())); //save the value
        }
        
        if(!ValidateFields.validateIntegerNumberTextField(txtSuplAccNum.getText())) 
        {
            error++;
            txtSuplAccNum.requestFocus();
            txtSuplAccNum.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_ACC);
            return error;
        }else
        {
            txtSuplAccNum.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplAccNum(Integer.valueOf(txtSuplAccNum.getText())); //Save the value to supplier object
        }
        
        if(!ValidateFields.validateStringCharTextField(txtSuplAccType.getText())) 
        {
            error++;
            txtSuplAccType.requestFocus();
            txtSuplAccType.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_ACC_TYPE);
            return error;
        }else
        {
            txtSuplAccType.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplAccType(txtSuplAccType.getText()); //save the value
        }
        
         if(!ValidateFields.validateStringCharTextField(txtSuplComments.getText())) 
        {
            error++;
            txtSuplComments.requestFocus();
            txtSuplComments.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLEMENT_DESC);
            return error;
        }else
        {
            txtSuplComments.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplComments(txtSuplComments.getText()); //save the value
        }
        
        return error;
    }
}
