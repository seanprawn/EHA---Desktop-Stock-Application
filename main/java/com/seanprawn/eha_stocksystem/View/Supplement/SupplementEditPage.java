/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Supplement;

import com.seanprawn.eha_stocksystem.MainApp;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
class SupplementEditPage extends SupplementViewPage{

    public SupplementEditPage() {
    }

    @Override
    public void SupplementViewPage() {
        super.SupplementViewPage(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setEditSupplementPage()
    {
//        btnClear.setVisible(true);
        MainApp.refresh = true;
        btnEdit.setText("View Supplement");
        btnSave.setVisible(true);
        btnDelete.setVisible(false);
        this.SetTextFieldsEditable(Boolean.TRUE);
        save_edit = true; // Set true to use UPDATE query
//        System.out.println("Save/Edit == "+save_edit);
    }
    
    public void setCreateSupplementPage()
    {
        checkEdit=true;
        MainApp.refresh = true;
        btnClear.setVisible(true);
        btnSave.setVisible(true);
        btnEdit.setVisible(false);
        btnSave.setText("Save New Supplement");
        btnDelete.setVisible(false);
        save_edit = false; // Set false to use INSERT query
//        System.out.println("Save/Edit == "+save_edit);
        this.SetTextFieldsEditable(Boolean.TRUE);
        txtSupSupplierId.setPromptText("Click to select");
    }
    
}
//}
