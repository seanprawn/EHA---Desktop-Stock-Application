/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Client;

import com.seanprawn.eha_stocksystem.Controller.DatabaseMethods;
import com.seanprawn.eha_stocksystem.Model.Client;
import com.seanprawn.eha_stocksystem.Controller.ValidateFields;
import com.seanprawn.eha_stocksystem.MainApp;
import com.seanprawn.eha_stocksystem.Model.ClientRef;
import com.seanprawn.eha_stocksystem.Model.Messages;
import com.seanprawn.eha_stocksystem.View.Alerts;
import java.math.BigInteger;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
 * client_id - int
 * client_name - text
 * client_surname - text
 * client_address -text
 * client_address_code - bigint
 * client_tel_h - text
 * client_tel_w - text
 * client_tel_cell - text
 * client_email - text
 * client_ref - int
 */
public class ClientViewPage extends Messages{
   
    public void ClientViewPage()
    {
    }
    
    GridPane ClientGridView = new GridPane();
    FlowPane ClientView = new FlowPane();
    Client selectedClient;
    boolean checkEdit = false; // Toggles the Edit Button to set for Edit or for Save new - false = edit , true = save new
    boolean save_edit = false; // if false - Then save new else if True - then edit supplier
    Client saveClient = new Client();
    String errorMessage = "Please enter correct data in the fields above";
    
    //Getters and setters
         public FlowPane getClientView()
    {
        return this.ClientView;
        
    }
    
    public void setClientView(FlowPane client)
    {
        this.ClientView = client;
    }
    
    public void setSelectedClient(Client client)
    {
        this.selectedClient = client;
    }
            //Create buttons
    Button btnSave = new Button("Save Changes");
    Button btnClear = new Button("Clear Fields");
//    Button btnCancel = new Button("Cancel");
    Button btnClose = new Button("Close");
    Button btnEdit = new Button("Edit Client Details");
    Button btnDelete = new Button("Delete");
    TilePane tileP = new TilePane(); // all buttons go into Tilepane layout
    
    //Create Labels
    Label lblClientId = new Label("Client ID:       ");
    Label lblClientName = new Label("First Name:");
    Label lblClientSNAme = new Label("SurName:");
    Label lblClientAddr = new Label("Address:");
    Label lblClientAddrCode = new Label("Address Code:");
    Label lblClientTelH = new Label("Tel Home:");
    Label lblClientTelW = new Label("Work:");
    Label lblClientCell = new Label("Cell:");
    Label lblClientEmail = new Label("Email:");
    Label lblClientRef = new Label("Client Ref:");
    Label txtError = new Label();
    
//    Checkbox chckRef = new Checkbox("Client Reference");

    //Create TextFields
    TextField txtClientId = new TextField();
    TextField txtClientName = new TextField();
    TextField txtClientSNAme = new TextField();
    TextField txtClientAddr = new TextField();
    TextField txtClientAddrCode = new TextField();
    TextField txtClientTelH = new TextField();
    TextField txtClientTelW = new TextField();
    TextField txtClientCell = new TextField();
    TextField txtClientEmail = new TextField();
    TextField txtClientRef = new TextField();
        
    /**
     *
     * @return 
     */
    public FlowPane createClientViewPage()
     {
         txtClientRef.setEditable(false);
         addAllToLayout();
        
         //These buttons are not visible at first
       btnClear.setVisible(false);
       btnSave.setVisible(false);
       txtError.setId("error");
       
         //Button OnClick Handlers
          btnClose.setOnAction((event)->{
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           stage.close();
       });
       
       btnClear.setOnAction((event)->{
        //Clears all the text fields
        txtClientId.clear();
        txtClientName.clear();
        txtClientSNAme.clear();
        txtClientAddr.clear();
        txtClientAddrCode.clear();
        txtClientTelH.clear();
        txtClientTelW.clear();
        txtClientCell.clear();
        txtClientEmail.clear();
       txtClientRef.clear();
//       lblClientRef.selectedProperty().setValue(false);
//       txtSuplComments.clear();
       });
       
       btnDelete.setOnAction((event)->{
            Client cl = getEditedTextFieldValues();
            int result = 0;
            Boolean check = Alerts.displayVerifyAlert("Delete Client", "Are You sure you want to delete Client: "+cl.getClName()+" "+cl.getClSurname()+"?");
            if (check) 
            {    
                result = DatabaseMethods.DeleteClientInfo(cl);
                if(result>0)
                {
                    Alerts.displayAlert("Success", "Client Deleted Successfully");
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.close();
                    MainApp.refresh = true;
//                    Stage MainStage = MainApp.getCurrentMainStage();
//                    Scene currScene = MainStage.getScene();
//                    MainStage.setScene(currScene)
                }
                else{
                    Alerts.displayAlert("Failed", "Failed to Delete the Client Info, please try again");
                }
            }
       });
       
       btnEdit.setOnAction((event)->{
           if(!checkEdit)
           {
               btnEdit.setText("View Client");
               btnSave.setVisible(true);
               checkEdit = true;
               save_edit = true;
               
               SetTextFieldValues();
               SetTextFieldsEditable(Boolean.TRUE); // Allow user to edit
               System.out.println("\n Save/Edit == "+save_edit);
           }
           else if(checkEdit)
         {
               btnEdit.setText("Edit Client");
               btnSave.setVisible(false);
               checkEdit = false;
               save_edit = false;
               SetTextFieldsEditable(Boolean.FALSE); // User can only view
               System.out.println("\n Save/Edit == "+save_edit);

           }
        });
       
       btnSave.setOnAction((event)->{
           int valid = ValidateAndGetClientFields();
           int result = 0;
           boolean check = false;
           if(valid == 0) 
           {
               check = Alerts.displayVerifyAlert("Save Client", "Are You sure you want to Save info for : "
                       +saveClient.getClName()+" "+saveClient.getClSurname()+"?");
           }
           if(check) 
           {
              if(!save_edit) result = DatabaseMethods.SaveNewClientInfo(saveClient);
              else result = DatabaseMethods.EditClientInfo(saveClient);
              
               if(result>0)
               {
                   Alerts.displayAlert("Success", "Client Saved Successfully");
                   Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                   MainApp.refresh = true;
                   stage.close();
//                   Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                   
               }
               else{
                 Alerts.displayAlert("Failed", "Client Failed to Update, please try again");
               }
           }
       });
           txtClientRef.setOnMousePressed((event)->{
    //           if(lblClientRef.isSelected())
       if(checkEdit)
       {
    //            {
                    ClientRefSearchTable search = new ClientRefSearchTable();
                    ClientRef selected = search.DisplayClientRef();
                    if(selected != null) txtClientRef.setText(String.valueOf(selected.getRefClRef()));
    //                lblClientRef.selectedProperty().set(false);
       }
    //            }
           });
        return ClientView;
         
     }

    private void addAllToLayout() {
        //Add Buttons to TilePane
//        tileP.setHgap(2);
//        tileP.setMaxWidth(500);
//        tileP.setOrientation(Orientation.HORIZONTAL);
//        tileP.getChildren().addAll(btnClear,btnDelete, btnEdit, btnSave, btnClose); 
        ClientGridView.setId("text");
//        txtError.setId("error");
//        TilePane tileChck = new TilePane(lblClientRef.);
//        tileChck.getChildren().add(lblClientRef);
        ClientView.setOrientation(Orientation.VERTICAL);
        ClientView.setVgap(10);
        
       Tooltip toggleTip = new Tooltip("Click to toggle Edit/ View");
       Tooltip.install(btnEdit, toggleTip);
       Tooltip backTip = new Tooltip("Click to close this component and go back to list");
       Tooltip.install(btnClose, backTip);
       Tooltip saveTip = new Tooltip("Click to Save this Supplement to the database");
       Tooltip.install(btnSave, saveTip);
       Tooltip delTip = new Tooltip("Click to Delete this Supplement from the database");
       Tooltip.install(btnDelete, delTip);
       Tooltip clearTip = new Tooltip("Click to Clear all fields and start again");
       Tooltip.install(btnClear, clearTip);
        
         Tooltip clickTip = new Tooltip("Click to Select Client Reference");
             Tooltip.install(txtClientRef, clickTip);
         Tooltip btnTip = new Tooltip("Click to toggle Edit/View");
             Tooltip.install(btnEdit, btnTip);
        
        //Add All labels and Text Boxes to the layout
        ClientGridView.add(new Text("Client"), 0, 0);
        ClientGridView.add(lblClientId,0,1);
        ClientGridView.add(txtClientId, 1, 1); 
        ClientGridView.add(lblClientName, 0, 2);
        ClientGridView.add(txtClientName, 1, 2);
        ClientGridView.add(lblClientSNAme, 0, 3); 
        ClientGridView.add(txtClientSNAme, 1, 3); 
        ClientGridView.add(lblClientAddr, 0, 4); 
        ClientGridView.add(txtClientAddr, 1, 4);
        ClientGridView.add(lblClientAddrCode, 0, 5);
        ClientGridView.add(txtClientAddrCode, 1, 5);
        ClientGridView.add(lblClientTelH, 0, 6);
        ClientGridView.add(txtClientTelH, 1, 6);
        ClientGridView.add(lblClientTelW, 0, 7);
        ClientGridView.add(txtClientTelW, 1, 7);
        ClientGridView.add(lblClientCell, 0, 8);
        ClientGridView.add(txtClientCell, 1, 8);
        ClientGridView.add(lblClientEmail, 0, 9);
        ClientGridView.add(txtClientEmail, 1, 9);
        ClientGridView.add(lblClientRef, 0, 10);
        ClientGridView.add(txtClientRef, 1, 10);
        ClientGridView.add(txtError, 1, 11);
        ClientGridView.add(btnClear, 0, 13);
        ClientGridView.add(btnEdit, 1, 13);
        ClientGridView.add(btnClose, 0, 14);
        ClientGridView.add(btnDelete, 1, 14);
        ClientGridView.add(btnSave, 0, 16);
        
        ClientView.getChildren().add(ClientGridView);//Add Gridview to layout
//        ClientView.getChildren().add(tileP); // Add Tilepane to the layout
    }

    public Client getEditedTextFieldValues() {
        
        Client client = new Client();
        if(txtClientId.getText() != null) client.setClId(new BigInteger(txtClientId.getText()));
        if(txtClientName.getText() != null) client.setClName(txtClientName.getText());
        if(txtClientSNAme.getText() != null) client.setClSurname(txtClientSNAme.getText());
        if(txtClientAddr.getText() != null) client.setClAddress(txtClientAddr.getText());
        if(txtClientAddrCode.getText() != null) client.setClAddrCode(Integer.valueOf(txtClientAddrCode.getText()));
        if(txtClientTelH.getText() != null) client.setClTelHome(txtClientTelH.getText());
        if(txtClientTelW.getText() != null) client.setClTelWork(txtClientTelW.getText());
        if(txtClientCell.getText() != null) client.setClTelCell(txtClientCell.getText());
        if(txtClientEmail.getText() != null) client.setClEmail(txtClientEmail.getText());
        if(txtClientRef.getText() != null)  client.setClRef(Integer.valueOf(txtClientRef.getText()));
        return client;
    }

    public void SetTextFieldValues() {
        txtClientId.setText(String.valueOf(this.selectedClient.getClId()));
        txtClientName.setText(this.selectedClient.getClName());
        txtClientSNAme.setText(this.selectedClient.getClSurname());
        txtClientAddr.setText(this.selectedClient.getClAddress());
        txtClientAddrCode.setText(String.valueOf(this.selectedClient.getClAddrCode()));
        txtClientTelH.setText(this.selectedClient.getClTelHome());
        txtClientTelW.setText(this.selectedClient.getClTelWork());
        txtClientCell.setText(this.selectedClient.getClTelCell());
        txtClientEmail.setText(this.selectedClient.getClEmail());
        txtClientRef.setText(String.valueOf(this.selectedClient.getClRef()));
    }

    public int ValidateAndGetClientFields() {
         int error = 0;
        
             if(!ValidateFields.validateClientUniqueIdTextField(txtClientId.getText())) 
            {
                error++;
                txtClientId.requestFocus();
                txtClientId.setStyle("-fx-border-color: red;");

                txtError.setText(ERR_ID);
                return error;
            }else
            {
                txtClientId.setStyle("-fx-border-color: transparent;");
                txtError.setText("");
                saveClient.setClId(new BigInteger(txtClientId.getText())); //save the value
            }
//        }     
        
        if(!ValidateFields.validateStringCharTextField(txtClientName.getText())) 
        {
            error++;
            txtClientName.requestFocus();
            txtClientName.setStyle("-fx-border-color: red;");
            
            txtError.setText(Messages.ERR_CLIENT_NAME);
            return error;
        }else
        {
            txtClientName.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveClient.setClName(txtClientName.getText()); //Save the value to supplier object
        }
        
        if(!ValidateFields.validateStringCharTextField(txtClientSNAme.getText())) 
        {
            error++;
            txtClientSNAme.requestFocus();
            txtClientSNAme.setStyle("-fx-border-color: red;");
            
            txtError.setText(Messages.ERR_CLIENT_NAME);
            return error;
        }else
        {
            txtClientSNAme.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveClient.setClSurname(txtClientSNAme.getText()); //Save the value to supplier object
        }
        
        
        if(!ValidateFields.validateStringCharTextField(txtClientAddr.getText())) 
        {
            error++;
            txtClientAddr.requestFocus();
            txtClientAddr.setStyle("-fx-border-color: red;");
            
            txtError.setText(Messages.ERR_CLIENT_ADDRESS);
            return error;
        }else
        {
            txtClientAddr.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveClient.setClAddress(txtClientAddr.getText()); //save the value
        }
        
         if(!ValidateFields.validateNumberTextField(txtClientAddrCode.getText())) 
        {
            error++;
            txtClientAddrCode.requestFocus();
            txtClientAddrCode.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_BRANCH_CODE);
            return error;
        }else
        {
            txtClientAddrCode.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveClient.setClAddrCode(Integer.valueOf(txtClientAddrCode.getText())); //save the value
        }
         
           if(!ValidateFields.validatePhoneNumberTextField(txtClientTelH.getText())) 
        {
            error++;
            txtClientTelH.requestFocus();
            txtClientTelH.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_TEL);
            return error;
        }else
        {
            txtClientTelH.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveClient.setClTelHome(txtClientTelH.getText()); //save the value
        }
           
              if(!ValidateFields.validatePhoneNumberTextField(txtClientTelW.getText())) 
        {
            error++;
            txtClientTelW.requestFocus();
            txtClientTelW.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_TEL);
            return error;
        }else
        {
            txtClientTelW.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveClient.setClTelWork(txtClientTelW.getText()); //save the value
        }
              
        if(!ValidateFields.validatePhoneNumberTextField(txtClientCell.getText())) 
        {
            error++;
            txtClientCell.requestFocus();
            txtClientCell.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_TEL);
            return error;
        }else
        {
            txtClientCell.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveClient.setClTelCell(txtClientCell.getText()); //save the value
        }
        
        if(!ValidateFields.validateEmailTextField(txtClientEmail.getText())) 
        {
            error++;
            txtClientEmail.requestFocus();
            txtClientEmail.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_EMAIL);
            return error;
        }else
        {
            txtClientEmail.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveClient.setClEmail(txtClientEmail.getText()); //save the value
        }
        
        if(!ValidateFields.validateNumberTextField(txtClientRef.getText())) 
        {
            error++;
            txtClientRef.requestFocus();
            txtClientRef.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_CLIENT_REF);
            return error;
        }else
        {
            txtClientRef.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveClient.setClRef(Integer.valueOf(txtClientRef.getText())); //Save the value to supplier object
        }
        return error;
    }
    
     public void SetTextFieldsEditable(Boolean flag) {
       txtClientId.setEditable(flag);
       txtClientName.setEditable(flag);
       txtClientSNAme.setEditable(flag);
       txtClientAddr.setEditable(flag);
       txtClientAddrCode.setEditable(flag);
       txtClientTelH.setEditable(flag);
       txtClientTelW.setEditable(flag);
       txtClientCell.setEditable(flag);
       txtClientEmail.setEditable(flag);
//       lblClientRef.setState(flag);
//       txtClientRef.setEditable(flag);
    }
}
