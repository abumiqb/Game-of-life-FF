package game.of.life;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

public class Tegner 
{

    private GraphicsContext graphicsContext;
    private ColorPicker colorPicker;
    private double cellX, cellY;
    private int antallruterxx, antallruteryy;
    private double canvasH, canvasW;
    private double cheight, cwidth;

    public Tegner(double canvasWidth, double canvasHeight, GraphicsContext graphicsContext, int antallruterx, int antallrutery, ColorPicker colorPicker) {
        // brettet på starten av spillet
        this.graphicsContext = graphicsContext;
        this.colorPicker = colorPicker;
        canvasH = canvasHeight;
        canvasW = canvasWidth;
        cellX = (int) canvasWidth / antallruterx;
        cellY = (int) canvasHeight / antallrutery;
        antallruterxx = antallruterx;
        antallruteryy = antallrutery;
        cwidth = canvasWidth;
        cheight = canvasHeight;
    }

    public void tegnGrid(int[][] matris) 
    {
        // brettet som blir tegnet senere, f.eks. når du laster en fil
        graphicsContext.setFill(Color.WHITE);
        colorPicker.setValue(colorPicker.getValue());
        graphicsContext.setLineWidth(0.1);
        graphicsContext.fillRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
        graphicsContext.setStroke(Color.GREY);
        graphicsContext.setFill(colorPicker.getValue());
        
        antallruterxx = matris[0].length;
        antallruteryy = matris.length;
        cellX = canvasW / antallruterxx;
        
        if(cellX*antallruteryy < canvasH)
            cellY = cellX;
        else
            cellY = canvasH / antallruteryy;
        
        cwidth = canvasW;
        cheight = canvasH;
        
        for (int i = 0; i < matris[0].length; i++) 
        {
            for (int j = 0; j < matris.length; j++)
            {
                if (matris[j][i] == 1) 
                {
                    graphicsContext.fillRect(i * cellX, j * cellY, cellX, cellY);
                }

                graphicsContext.strokeRect(i * cellX, j * cellY, cellX, cellY);

            }
        }
    }

    public void finnrect(int[][] matriss, int xCordinate, int yCordinate) 
    {
        double x = 0;
        double y = 0;
        int kolonner = 0;
        int rader = 0;
        double rutex = cellX;
        double rutey = cellY;

        graphicsContext.setFill(colorPicker.getValue());

        for (int i = 1; i < antallruterxx; i++) 
        {
            if (rutex * i < xCordinate) 
            {
                x = x + rutex;
                kolonner++;
            }
        }

        for (int i = 1; i < antallruteryy; i++)
        {
            if (rutey * i < yCordinate) 
            {
                y = y + rutey;
                rader++;
            }
        }

        //System.out.println("Kolonne: " + kolonner+ " | Rad: " + rader);
        // legg til tallet 1 der man tegner blått
        matriss[rader][kolonner] = 1;

        //Vis 2D-arrayen i output
        
        //System.out.println("---------------------");
        //for (int i = 0; i < matriss.length; i++) 
        //{
          //  System.out.println(Arrays.toString(matriss[i]));
        //}
        
        graphicsContext.fillRect(x + 2, y + 2, (cwidth / matriss[0].length) - 3, (cheight / matriss.length) - 3);
    }
    

}
