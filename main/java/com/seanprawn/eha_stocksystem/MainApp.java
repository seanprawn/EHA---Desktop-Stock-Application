package com.seanprawn.eha_stocksystem;
/*
* @startuml
bob-->alice
@enduml
*/

//import com.seanprawn.eha_stocksystem.Controller.SceneLoader;
import com.seanprawn.eha_stocksystem.Controller.DatabaseAccess;
import com.seanprawn.eha_stocksystem.Controller.SceneController;
import static com.seanprawn.eha_stocksystem.View.SideMenu.dashWindow;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;


public class MainApp extends Application 
{
    private static Stage currentMainStage;
    ////Setup vars
    public static int LoginCount = 0;
//    public static User user = null;
    public static Stage window;
    public static boolean loginFlag = false;
    public static boolean InvFlag = false;
    public static String selectedDirectory; // Selected Directory Path
    public static Boolean refresh = false;
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        window = primaryStage;
        DatabaseAccess.setConnectionOnlineOffline(loginFlag); // True = local; False = Online
        login(window);
    }

    /**
    * The main() method is ignored in correctly deployed JavaFX application.
    * main() serves only as fallback in case the application can not be
    * launched through deployment artifacts, e.g., in IDEs with limited FX
    * support. NetBeans ignores main().
    *
    * @param args the command line arguments
    */
    public static void main(String[] args) {
    launch(args);
    }

      public static Stage getCurrentMainStage() {
          currentMainStage = window;
        return currentMainStage;
    }
    
    public static void login(Stage stage) 
    {
        SceneController control = new SceneController();
        control.setMainStage(stage);
        control.login();
        
        stage.setOnCloseRequest((e) ->{ //If the main window is closed by the user, then close all windows
             dashWindow.close();
         });
    }

}
