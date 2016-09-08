package game.of.life;

import java.util.Arrays;

public class Logikk
{

    public int[][] array;
    private int antallruterx, antallrutery;
    private double cheight, cwidth;
    private int nabocelle;

    public Logikk(int antallruterx, int antallrutery, double canvasWidth, double canvasHeight)
    {
        array = new int[antallruterx][antallrutery];
        this.antallruterx = antallruterx;
        this.antallrutery = antallrutery;

    }

    public Logikk(int[][] array, double canvasWidth, double canvasHeight)
    {
        this.array = array;
        this.antallruterx = antallruterx;
        this.antallrutery = antallrutery;
    }

    public int[][] getArray()
    {
        return array;
    }

    public void setArray(int[][] array)
    {
        this.array = array;
        this.antallruterx = array[0].length;
        this.antallrutery = array.length;
    }

    private int[][] sjekkNabo()
    {
        int[][] newGen = new int[array.length][array[0].length];

        for (int x = 0; x < array.length; x++)
        {

            for (int y = 0; y < array[0].length; y++)
            {
                nabocelle = 0;

                //System.out.println("array[" + x + "][" + y + "]: ");
                if ((x - 1) >= 0 && array[x - 1][y] == 1)
                {
                    //System.out.println("1 Levende celle på over side");
                    nabocelle++;
                }
                if ((x + 1) < array.length && array[x + 1][y] == 1)
                {
                    //System.out.println("1 Levende celle på under");
                    nabocelle++;
                }
                if ((y + 1) < array[0].length && array[x][y + 1] == 1)
                {
                    //System.out.println("1 Levende celle høyre");
                    nabocelle++;
                }
                if ((y - 1) >= 0 && array[x][y - 1] == 1)
                {
                    //System.out.println("1 Levende celle venstre");
                    nabocelle++;
                }
                if ((x - 1) >= 0 && (y - 1) >= 0 && array[x - 1][y - 1] == 1)
                {
                    //System.out.println("1 levende celle ovenfor Diagonal-venstre");
                    nabocelle++;
                }
                if ((x + 1) < array.length && (y - 1) >= 0 && array[x + 1][y - 1] == 1)
                {
                    //System.out.println("1 Levende celle nedenfor Diagonal-venstre");
                    nabocelle++;
                }
                if ((x - 1) >= 0 && (y + 1) < array[0].length && array[x - 1][y + 1] == 1)
                {
                    //System.out.println("1 Levende celle Diagonal over høyre");
                    nabocelle++;
                }
                if ((x + 1) < array.length && (y + 1) < array[0].length && array[x + 1][y + 1] == 1)
                {
                    //System.out.println("1 Levende Celle nedenfor Diagonal-høyre");
                    nabocelle++;
                }

                newGen[x][y] = nabocelle;

            }

        }

        return newGen;
    }

    public void nextGen()
    {
        int[][] neighbours = sjekkNabo();
        int[][] newArray = new int[array.length][array[0].length];
        for (int y = 0; y < neighbours.length; y++)
        {
            for (int x = 0; x < neighbours[0].length; x++)
            {
                if (array[y][x] == 1 && (neighbours[y][x] == 2 || neighbours[y][x] == 3))
                {
                    newArray[y][x] = 1;
                 
                } else if (array[y][x] == 0 && neighbours[y][x] == 3)
                {
                    newArray[y][x] = 1;
                } else
                {
                    newArray[y][x] = 0;
                }

            }
        }

        array = newArray;
    }

    public void reset()
    {
        for (int i = 0; i < array.length; i++)
        {
            for (int j = 0; j < array[0].length; j++)
            {
                array[i][j] = 0;
            }
        }
    }
}
