/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Dashboard;

import com.seanprawn.eha_stocksystem.Controller.DatabaseMethods;
import com.seanprawn.eha_stocksystem.Model.ClientEvent;
import com.seanprawn.eha_stocksystem.Model.Supplement;
import com.seanprawn.eha_stocksystem.Model.SupplementDataChart;
import com.seanprawn.eha_stocksystem.View.Supplement.SupplementListTableDashboard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class Dashboard 
{
    GridPane dashBoardPane = new GridPane(); //Main Outer Container for Dashboard
//    FlowPane dashFlowPane = new FlowPane();
    FlowPane gaugePane = new FlowPane(); // Container for gauges
    FlowPane LeftArea = new FlowPane(); //Add Supplement data to left side
    FlowPane RightArea = new FlowPane(); //Add Patient data to left side
    
    GridPane topLeft = new GridPane();
    GridPane topRight = new GridPane();
    GridPane bottomLeft = new GridPane();
    GridPane bottomRight = new GridPane();
    public static ClientEvent selected;
    ObservableList<String> empty = FXCollections.observableArrayList(); // Empty array
    String[] noItems = {"n/a", "No ", "Appointments ", "for Today "};
    Label lblLowStck = new Label("Low Stock Supplements");
    Label lblAppoint = new Label("Appointments for Today");
    
    public Dashboard()
    {
    }
    
    public Dashboard(GridPane pane)
    {
        this.dashBoardPane = pane;
    }
    
    public GridPane getDashbpard()
    {
        return this.dashBoardPane;
    
    }
    
    public void setupDashBoard()
    {
        lblAppoint.setId("header");
        lblLowStck.setId("header");
//        dashFlowPane.setOrientation(Orientation.VERTICAL);
//        dashBoardPane.setAlignment(Pos.TOP_CENTER);
//        dashBoardPane.setMaxSize(800, 400);
//        dashBoardPane.setPadding(new Insets(20, 0, 20, 20));
        
//        dashFlowPane.setAlignment(Pos.TOP_CENTER);
//        dashFlowPane.setMaxSize(800, 400);
//        dashFlowPane.setPadding(new Insets(0, 10, 10, 10));
    }
    
    public GridPane createDashboard()
    {

            empty.setAll(noItems);
            this.setupDashBoard();
            //Patient Gauges
            TilePane gauges = createGauges(); //Create Gauges for patient data
            gauges.setOrientation(Orientation.HORIZONTAL);
            gaugePane.getChildren().add(gauges);

            //Patient Appointments
            GridPane patientApp = getAppointments();
            ScrollPane scrollAppoint = new ScrollPane(patientApp);
            scrollAppoint.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollAppoint.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            //Patient Birthdays
            GridPane patientsBDaysMonth = createPatientBDayList();   //Patients Birhdays for the month
            Label totalLabel = new Label("Patients Seen"); //Label for patients
            totalLabel.setId("header");
            totalLabel.setAlignment(Pos.CENTER);

            //Supplements Sold 
            StackedAreaChart<String, Number> areaChartSupl = createAreaChartSupplements(); 
    //        areaChartSupl.setMinSize(200, 100);
    //        areaChartSupl.setPrefSize(370, 280);
    //        areaChartSupl.setMaxSize(380,300);

    //        Low Supplement Stock
            SupplementListTableDashboard list = new SupplementListTableDashboard(); //create the table
            ObservableList<Supplement> supList = list.getLowStockList();//get the list of supplements
            TableView suplTable = list.CreateSupplementListTable(supList);//add the list to the table

            //Setup Dashboard panes
            topLeft.setMaxSize(500, 325);
            topLeft.setPadding(new Insets(10, 10, 10, 10));

            topRight.setMaxSize(500, 325);
            topRight.setPadding(new Insets(10, 10, 10, 10));

            bottomLeft.setMaxSize(500, 325);
            bottomLeft.setPadding(new Insets(10, 10, 10, 10));

            bottomRight.setMaxSize(500, 325);
            bottomRight.setPadding(new Insets(10, 10, 10, 10));

            //Add views to the dashboard panes
            topLeft.add(areaChartSupl,0,0);
            topRight.add(totalLabel, 0, 0);
            topRight.add(gaugePane, 0, 1);
            topRight.add(lblAppoint, 0, 2);
            topRight.add(scrollAppoint, 0, 3);
            bottomLeft.add(lblLowStck, 0, 0);
            bottomLeft.add(suplTable, 0, 1);
            bottomRight.add(patientsBDaysMonth, 0, 0);

            //Add dashboard panes to outer dashboard layout
            dashBoardPane.add(topLeft, 0, 0);
            dashBoardPane.add(topRight, 2, 0);
            dashBoardPane.add(bottomLeft, 0, 2);
            dashBoardPane.add(bottomRight, 2, 2);

            //Add Tooltips for dashboard
            Tooltip dashTipArea = new Tooltip("The amount of Supplements Sold over each respective period");
            Tooltip dashTipApp = new Tooltip("Customer appointments scheduled for today");
            Tooltip dashTipClBday = new Tooltip("Customer Birthdays for this month");
            Tooltip dashTipPatients = new Tooltip("How many patients seen over the respective period");

            Tooltip.install(scrollAppoint, dashTipApp);
            Tooltip.install(areaChartSupl, dashTipArea);
            Tooltip.install(patientsBDaysMonth, dashTipClBday);
            Tooltip.install(gaugePane, dashTipPatients);
        return dashBoardPane;
    }

    private TilePane createGauges() 
    {
        //Total Patients for the Month
        Group patMonth = new Group();
        
        Text lblPatientMonth = new Text("Last Month");
        lblPatientMonth.setId("labelDash");
        SimpleMetroArcGauge totalPatientsMonth = new SimpleMetroArcGauge();
        totalPatientsMonth.setStyle("-fxx-backplate-color: linear-gradient(#EF2B2B,#F7FE2E,#3CFE2E);");
        totalPatientsMonth.setMaxSize(100, 100);
        int patientsMth = DatabaseMethods.getPatientsLastMonth(); //Retrieve data from DB
        totalPatientsMonth.setValue(patientsMth);  // Set data to gauge
        patMonth.getChildren().addAll(lblPatientMonth,totalPatientsMonth);//Add gauge and label to the Group 
        
        //Total patients for the year
        int patientsYr = DatabaseMethods.getPatientsLastYear(); //Get data from DB
        Text lblPatientYr = new Text("Last Year");
        lblPatientYr.setId("labelDash");
        SimpleMetroArcGauge totalPatYr = new SimpleMetroArcGauge();
        totalPatYr.setValue(patientsYr); // Set data to gauge
        Group patYr = new Group();
        totalPatYr.setMaxSize(100, 100);
        
        patYr.getChildren().addAll(lblPatientYr, totalPatYr); //Add gauge and label to the Group
        TilePane gauges = new TilePane(patMonth,patYr); //Add gauges and labels to the Tilepane layout
        gauges.setVgap(40);
        
//        gauges.setOrientation(Orientation.HORIZONTAL);
        return gauges; // return the layout
    }

    private GridPane createPatientBDayList() 
    {

         GridPane patBDaysMonth = new GridPane(); // Outer layout pane
         GridPane bDayBox = new GridPane();
         ObservableList<ClientEvent> clientBDayList = DatabaseMethods.getClientBirthdaysMonth();

        if(clientBDayList!=null)
        {
            Label lblClId = new Label("Client ID");
            lblClId.setUnderline(true);
            Label lblClName = new Label("Client Name");
            lblClName.setUnderline(true);
            Label lblClSurName = new Label("Surname");
            lblClSurName.setUnderline(true);

           bDayBox.add(lblClId, 0, 0);
           bDayBox.add(lblClName, 1, 0);
           bDayBox.add(lblClSurName, 2, 0);
           
            for(int i = 0; i<clientBDayList.size();i++)
            {
            TextField fieldID = new TextField();
            
            fieldID.setText(clientBDayList.get(i).getEventClId().toString());
            fieldID.setStyle("-fx-background-color: #D2FEFD;");
            fieldID.setEditable(false);
            
            bDayBox.add(fieldID, 0, (i+1));
            
            TextField fieldName = new TextField();
            fieldName.setText(clientBDayList.get(i).getEventClName());
            fieldName.setStyle("-fx-background-color: #F5FEB7;");
            fieldName.setEditable(false);
            bDayBox.add(fieldName, 1, (i+1));
            
            TextField fieldSurName = new TextField();
            fieldSurName.setStyle("-fx-background-color: #BEFECD;");
            fieldSurName.setEditable(false);
            fieldSurName.setText(clientBDayList.get(i).getEventClSurname());
            bDayBox.add(fieldSurName, 2, (i+1));
            }
        }else
        {
            Text fieldIText = new Text(empty.get(0));
            bDayBox.add(fieldIText, 0, (1));
            Text fieldName = new Text(empty.get(0));
            bDayBox.add(fieldName, 1, (1));
            Text fieldSurName = new Text(empty.get(0));
            bDayBox.add(fieldSurName, 2, (1));
        }

        ScrollPane scrollBdays = new ScrollPane(bDayBox);
        scrollBdays.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollBdays.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
         
        Label labelBdays = new Label("Client Birthdays this month");
        labelBdays.setAlignment(Pos.CENTER_LEFT);
        labelBdays.setId("header");
        
        patBDaysMonth.add(labelBdays,0,1);
        patBDaysMonth.add(scrollBdays, 0, 3);

        return patBDaysMonth;
    }

    private StackedAreaChart<String, Number> createAreaChartSupplements() 
    {

        
        CategoryAxis xAxis = new CategoryAxis(); //Defining X axis 
        NumberAxis yAxis = new NumberAxis(0, 100, 10); //Defining y axis
        yAxis.setLabel("No.of Supplements");
        
        //Create Area Chart
        StackedAreaChart<String, Number> areaChart = new StackedAreaChart(xAxis, yAxis); 
        areaChart.setTitle("Supplements Sold");
        areaChart.setId("bar");
         
        //Supplements Sold for year
        ObservableList<SupplementDataChart> suplListYr = null;
        suplListYr = DatabaseMethods.getSuplSoldListYear();
        
        XYChart.Series suplsYear = new XYChart.Series(); 
        suplsYear.setName("Last Year");

        if(suplListYr != null && suplListYr.size()>0)
        {
            for (SupplementDataChart item : suplListYr) {
                suplsYear.getData().add(new XYChart.Data(item.getSuplID(), item.getSuplQtySold()));
            } 
        }else
         {
             suplsYear.getData().add(new XYChart.Data("no data", 0));
         }
         

        //Supplements Sold for Month
        ObservableList<SupplementDataChart> suplListMnth = null;
        suplListMnth = DatabaseMethods.getSuplSoldListYear();

        XYChart.Series suplsMnth = new XYChart.Series();
        suplsMnth.setName("Last Month");

        if(suplListMnth != null && suplListMnth.size()>0)
        {
            for (SupplementDataChart item : suplListMnth) {
                suplsMnth.getData().add(new XYChart.Data(item.getSuplID(), item.getSuplQtySold()));
            } 
        }else
         {
             suplsMnth.getData().add(new XYChart.Data("no data", 0));
         }
        
         //Supplements Top Ten Sold for Year
        ObservableList<SupplementDataChart> topTenListYear = null;
        topTenListYear = DatabaseMethods.getTop10SuplListYear();
        
        XYChart.Series suplTopSeriesYr = new XYChart.Series(); 
        suplTopSeriesYr.setName("Top Ten Year");
       
        if(topTenListYear != null && topTenListYear.size()>0)
        {
            for (SupplementDataChart item : topTenListYear) {
                suplTopSeriesYr.getData().add(new XYChart.Data(item.getSuplID(), item.getSuplQtySold()));
            } 
        }else
         {
             suplTopSeriesYr.getData().add(new XYChart.Data("no data", 0));
         }

         //Supplements Top Ten Sold for Month
         ObservableList<SupplementDataChart> topTenListMonth = DatabaseMethods.getTop10SuplListMonth();
        
        XYChart.Series suplTopSeriesMth = new XYChart.Series(); 
        suplTopSeriesMth.setName("Top Ten Month");
//        suplTopSeriesYr.getData().add(topTenListYear);
        if(topTenListMonth != null && topTenListMonth.size()>0)
        {
            for (SupplementDataChart item : topTenListMonth) {
                suplTopSeriesMth.getData().add(new XYChart.Data(item.getSuplID(), item.getSuplQtySold()));
            } 
        }else
         {
             suplTopSeriesMth.getData().add(new XYChart.Data("no data", 0));
         }

        //Setting the data to chart   
        areaChart.getData().addAll(suplsYear,suplsMnth,suplTopSeriesYr, suplTopSeriesMth);

        return areaChart;
    }

    
//}

    private GridPane getAppointments() {
        GridPane patientBox = new GridPane();
         ObservableList<ClientEvent> clientList = DatabaseMethods.getClientAppointmentsToday();
        if(clientList!=null || clientList.get(0) != null)
        {
            Label lblClAppTime = new Label("Appointment Time");
            lblClAppTime.setUnderline(true);
            Label lblClName = new Label("Client Name");
            lblClName.setUnderline(true);
            Label lblClSurName = new Label("Surname");
            lblClSurName.setUnderline(true);

           patientBox.add(lblClAppTime, 0, 0);
           patientBox.add(lblClName, 1, 0);
           patientBox.add(lblClSurName, 2, 0);
           
           for(int i = 0; i<clientList.size();i++)
            {
            TextField fieldTime = new TextField();
            fieldTime.setStyle("-fx-background-color: #F5FEB7;");
            fieldTime.setEditable(false);
            fieldTime.setText(clientList.get(i).getEventClAppTime().toString());
            patientBox.add(fieldTime, 0, (i+1));
            
            TextField fieldName = new TextField();
            fieldName.setText(clientList.get(i).getEventClName());
            fieldName.setStyle("-fx-background-color: #BEFECD;");
            fieldName.setEditable(false);
            patientBox.add(fieldName, 1, (i+1));
            
            TextField fieldSurName = new TextField();
            fieldSurName.setStyle("-fx-background-color: #D2FEFD;");
            fieldSurName.setEditable(false);
            fieldSurName.setText(clientList.get(i).getEventClSurname());
            patientBox.add(fieldSurName, 2, (i+1));

            fieldTime.setOnAction((event) ->{
                
            });
            }
        }else
        {
            Text fieldTime = new Text(empty.get(0));
            patientBox.add(fieldTime, 0, (1));
            Text fieldName = new Text(empty.get(0));
            patientBox.add(fieldName, 1, (1));
            Text fieldSurName = new Text(empty.get(0));
            patientBox.add(fieldSurName, 2, (1));
        }
        return patientBox;
    }
}
