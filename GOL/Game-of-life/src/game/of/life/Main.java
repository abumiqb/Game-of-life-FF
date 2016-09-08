package game.of.life;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

	public class Main extends Application 
        {
		@Override
		public void start(Stage primaryStage) throws Exception 
                {
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		       
			Scene scene = new Scene(root, 1100, 980);
		    
			primaryStage.setTitle("Game Of Life");
			primaryStage.setScene(scene);
                        //primaryStage.setResizable(false);
			primaryStage.show();
                                         
		}
		
		public static void main(String[] args) 
                {
			launch(args);
		}
	}
	
		
