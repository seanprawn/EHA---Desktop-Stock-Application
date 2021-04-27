/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View;

import com.seanprawn.eha_stocksystem.Controller.DatabaseMethods;
import com.seanprawn.eha_stocksystem.Controller.SceneController;
import com.seanprawn.eha_stocksystem.MainApp;
import com.seanprawn.eha_stocksystem.Model.User;
import com.seanprawn.eha_stocksystem.View.Supplement.SupplementListPage;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 * 
 * Entry into the system. Login Page loads first
 * Authenticates user
 */
public class LoginPage 
{
    //Setup vars
    Stage mainStage = new Stage();
    User user = null;
    public static int userGroup = 0;
    
//    Blank Constructor
    public LoginPage()
    {
    }
    
    public LoginPage(Stage stage)
    {
        this.mainStage = stage;
    }
 
    public Stage getMainLoginStage()
    {
        return this.mainStage;
        
    }
    
    /**
     * public GridPane loadLoginPane()
     * Sets up nodes and adds the nodes to a GridPane container 
     * @return GridPane
     */
    public GridPane loadLoginPane()
    {   
        GridPane loginPane = new GridPane();
        loginPane = setupLoginPane(loginPane);
        
        //        Initialise the nodes for Gridpane layout
        Label lblUserName = new Label("User Name:");
        TextField txtUserName = new TextField();
        Label lblPassword = new Label("Password:");
        PasswordField passField = new PasswordField();
        Button btnLogin = new Button("Login");
        btnLogin.setId("btnLogin");
        btnLogin.setDefaultButton(true); // Sets the enter button as shortcut
        Label lblMessage = new Label();

        //        Add Nodes to GridPane Layout
        loginPane.add(lblUserName, 0, 0);
        loginPane.add(txtUserName, 1, 0); 
        loginPane.add(lblPassword, 0, 1);
        loginPane.add(passField, 1, 1);
        loginPane.add(btnLogin, 0, 2);
        loginPane.add(lblMessage, 1, 2);

   

        //Action for btnLogin
        btnLogin.setOnAction(new EventHandler()
        {
            @Override
            public void handle(Event event)
            {
              //Get the text from user input 
            String checkUser = txtUserName.getText();
            String checkPw = passField.getText();

            user = DatabaseMethods.getUser(checkUser, checkPw); //Access DB and get user data
            if(user!=null) 
            {
                //Set the User access Level
                userGroup = user.getUserGroupId();
                
            }

            if(checkUser.equals(user.getFirstName()) && checkPw.equals(user.getPassword()))
            {
            //If login successful then load main application
                //Load the main page after successful login
                Stage loginStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                SceneController control = new SceneController();
                control.setMainStage(loginStage);
                try {
                    //First Time after -> login load the Supplement List Page
                    SupplementListPage mainSuplListPage = new SupplementListPage();
                    GridPane pane = mainSuplListPage.loadMainListScreen();
                    control.loadMainApplication(pane);
                    MainApp.LoginCount++; // Keep track of how many times logged in
                } catch (Exception e) { e.printStackTrace(System.out);}

                control.setMainPageScene();
            }
            else{
            lblMessage.setText("Incorrect user or pw.");
//            lblMessage.setTextFill(Color.RED);
            lblMessage.setId("error");
            }
                txtUserName.setText("");
                passField.setText("");
            } 
        });
        return loginPane;
    }

    private GridPane setupLoginPane(GridPane loginPane) {
//        loginPane.setPadding(new Insets(30, 30, 30, 30));
        loginPane.setHgap(5);
        loginPane.setVgap(5);
        loginPane.setId("gp");
        
               //Add dropshadow effect to login Form
        DropShadow dropShadow = new DropShadow();
        //        dropShadow.setOffsetX(7);
                dropShadow.setOffsetY(6);
        //        dropShadow.setBlurType(BlurType.GAUSSIAN);
        //        dropShadow.setSpread(0.1);
        //        dropShadow.
        loginPane.setEffect(dropShadow);
        
    //    //Reflection for gridPane
    //        Reflection r = new Reflection();
    //        r.setFraction(1.0f);
    //        loginPane.setEffect(r);
        
        return loginPane;
    }
 }
