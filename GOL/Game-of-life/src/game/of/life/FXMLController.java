package game.of.life;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class FXMLController implements Initializable 
{
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;
    GraphicsContext graphicsContext;
    @FXML
    private Slider slider;
    Rle fil;
    Logikk brettNextGen;
    Logikk brett;
    Tegner draw;
    double canvasWidth;
    double canvasHeight;
    int antallruterx = 40;
    int antallrutery = 40;
    int[][] array = new int[antallruterx][antallrutery];
    final Timeline timeline = new Timeline();
    long frekvens = 80;
    private final DoubleProperty freq = new SimpleDoubleProperty();
   
    @FXML
    public void startSpill() 
    {
        timeline.play();
    }
    
    @FXML
    public void stopSpill()
    {
        timeline.stop();  
    }
    
    @FXML
    public void nullstillGameboard(ActionEvent event)
    {
      timeline.stop();
      brett.reset();
      brettNextGen = brett;
      draw.tegnGrid(brett.getArray());
        System.out.println("jeg virker");
    }
   
   
    @FXML
    public void lastInnRle(ActionEvent event)
    {
        
        brettNextGen.setArray(fil.lastinn());
        antallruterx = fil.getX();
        antallrutery = fil.getY();
        draw.tegnGrid(brettNextGen.getArray());
    }
   
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        graphicsContext = canvas.getGraphicsContext2D();
        canvasWidth = canvas.getWidth();
        System.out.println("Canvas Width: "+canvasWidth);
        canvasHeight = canvas.getHeight();
        graphicsContext.setLineWidth(3);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicsContext.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        //Frekvens
        freq.bind(slider.valueProperty());
        
        
        //Fargen på celle ved start
        colorPicker.setValue(Color.TEAL);
       
        //GRID
        graphicsContext.setStroke(Color.GREY);
        
        brettNextGen = new Logikk(array, canvasWidth, canvasHeight);
        brett = new Logikk(array, canvasWidth, canvasHeight);
        draw = new Tegner(canvasWidth, canvasHeight, graphicsContext, antallruterx, antallrutery, colorPicker);
        draw.tegnGrid(brettNextGen.getArray());
        fil = new Rle();
        
        colorPicker.setOnAction((ActionEvent) -> 
        {
            //draw.setFill(colorPicker.getValue());
        });

        colorPicker.setValue(colorPicker.getValue()); //Endre farge på colorpicker penne

        graphicsContext.setStroke(colorPicker.getValue()); // Velger farge

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event)
                -> {
            draw.finnrect(brettNextGen.getArray(), (int) event.getX(), (int) event.getY());
        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event)
                -> {
            draw.finnrect(brettNextGen.getArray(), (int) event.getX(), (int) event.getY());
        }
        );

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent event)
                -> {
            //Når musen slippes
        }
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
        (ActionEvent event) ->
        {
            brettNextGen.nextGen();
            draw.tegnGrid(brettNextGen.getArray());      
        }
        ));
        
        timeline.rateProperty().bind(freq);
        //timeline.setCycleCount(Timeline.INDEFINITE);

    }
}
