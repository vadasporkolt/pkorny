package Ping;

import java.sql.SQLException;
import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
	  //dsfdsgfinal dsfstatic Logger logger = Logger.getLogger(App.class);


    @Override
    public void start(Stage primaryStage) throws Exception {
    	
        Parent root = FXMLLoader.load(getClass().getResource("ui.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
    	launch(args);
    	
    	dbsqlconnect con=new dbsqlconnect();
    	con.create();
    	con.close();
    	//Model dsfdsfsdlog=new Model();
    	
    	//log.runMe("PingAPP");
        
    }

}