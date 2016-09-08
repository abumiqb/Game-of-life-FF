package game.of.life;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class Rle
{
    
    private int x;
    private int y;

    public int[][] lastinn()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Tekst fil", "*.rle"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null)
        {
            System.out.println("Valg fil: " + selectedFile.getName());
        } else
        {
            System.out.print("Fil ikke valgt");
            return new int[40][40];
        }
        int[][] array = null;
        
        //Pattern pattern = Pattern.compile("\\d+|[ob]");
        Pattern pattern = Pattern.compile("\\.*!");
        //Pattern regexy = Pattern.compile("\\x");
        //System.out.println(regexy.toString());
        
        String spillBrett = "";
        String kommentar = "";
       
        try (Scanner scanner = new Scanner(selectedFile))
        {
            //scanner.useDelimiter(Pattern.compile("\\$|!"));
            
            int i = 0;
            int j = 0;
            
            boolean gameBoard = false;
            
            while (scanner.hasNext())
            {
                String stringLinje = scanner.nextLine(); // en linje
                System.out.println(stringLinje + " | ");
                
                Matcher matcher = pattern.matcher(stringLinje);
                
                if(stringLinje.contains(", y") == true)
                {
                    String[] verdier = stringLinje.split(",");
                    
                    x = Integer.parseInt(verdier[0].split("=")[1].trim());
                    y = Integer.parseInt(verdier[1].split("=")[1].trim());
                    
                    kommentar += stringLinje + "\n";
                    
                    array = new int[y][x];
                    
                    gameBoard = true; // sier at grid-en kommer etter denne linja
                }
                else if((matcher.find() || gameBoard == true) && i < array.length && j < array.length)
                {
                    spillBrett += stringLinje;
                }
                else if(stringLinje.charAt(0) == '#')
                {
                    if(stringLinje.charAt(1) == 'N')
                    {
                        kommentar += stringLinje + "\n";
                        
                        System.out.println("Dette er en navnlinje: " + stringLinje);
                    }
                    else if(stringLinje.charAt(1) == 'C')
                    {
                        kommentar += stringLinje + "\n";
                        
                        System.out.println("Dette er en kommentarlinje: " + stringLinje);
                    }
                }
            }
            
            // gå gjennom spillbrett-koden og lage brett
            if(spillBrett != "")
            {
                String[] tegn = spillBrett.split("\\$|!");
                int offsetY = 0;

                for(int k = 0; k < tegn.length; k++)
                {
                    int cI = 0; // currentIndex

                    while(cI < tegn[k].length())
                    {
                        String streng = tegn[k].charAt(cI++)+"";
                        int num = 1;
                        boolean linjeskift = false;

                        if(streng.matches("\\d+"))
                        {
                            String number = "";

                            while(streng.matches("\\d+")){
                                number += streng;
                                
                                if(cI >= tegn[k].length()){
                                    linjeskift = true;
                                    streng = "";
                                } else
                                    streng = tegn[k].charAt(cI++)+"";
                            }

                            num = Integer.parseInt(number);
                            
                            if(linjeskift)
                                num--;
                        }

                        while(--num >= 0)
                        {
                            if(linjeskift){
                                array[k+offsetY] = new int[array[0].length];
                                offsetY++;
                            } else
                                array[k+offsetY][i++] = streng.equals("o") ? 1 : 0;
                        }
                    }

                    i = 0;
                }
            }
            
            StringBuilder sb = new StringBuilder();
            
            for (int[] board : array)
            {
                sb.append(Arrays.toString(board)).append("\n");
            }
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText(kommentar);
            alert.showAndWait();
            
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(Rle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return array;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return x;
    }
    
}
