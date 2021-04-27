/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Client;

import com.seanprawn.eha_stocksystem.MainApp;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class ClientEditPage extends ClientViewPage{
    public ClientEditPage()
    {
    }
    
    @Override
    public void ClientViewPage() 
    {
        super.ClientViewPage();
    }
    
      public void setEditClientDataPage()
    {
//        btnClear.setVisible(true);
        MainApp.refresh = true;
        btnEdit.setText("View Client");
        btnSave.setVisible(true);
//        lblClientRef.visibleProperty().set(false);
        this.SetTextFieldsEditable(Boolean.TRUE);
        save_edit = true; // Set true to use UPDATE query
//        MainApp.refresh = true;
    }
    
    public void setCreateClientDataViewPage()
    {
        checkEdit = true;
        MainApp.refresh = true;
        btnClear.setVisible(true);
        btnSave.setVisible(true);
        btnEdit.setVisible(false);
        btnSave.setText("Save New Client");
        btnDelete.setVisible(false);
        save_edit = false; // Set false to use INSERT query
        this.SetTextFieldsEditable(Boolean.TRUE);
        txtClientRef.setPromptText("Click to select");
    }
    
}
