//alice aidlin 206448326
//maayan nadivi 208207068
package Virus;
import java.util.Random;

public class VirusManager {


    private static boolean[][] data;
    static {
        data = new boolean[Virus.values().length][];
        for (int i = 0; i < data.length; i++)
        {
            data[i] = new boolean[Virus.values().length];
            for (int j = 0; j < data.length; j++)
            {
                data[i][j] = i == j;
            }
        }
    }

    public static void toogle(int i, int j) {
        data[i][j] = !data[i][j];
    }

    // func return the virus
    public static IVirus contagion(IVirus src) {
        int index = Virus.findvirus(src);
        System.out.println("print the index = "+index);
        if(index==-1)
            return null;
        IVirus virusim = findRandomVirus(data[index]);
        return virusim;
    }

    public static IVirus findRandomVirus(boolean[] data) {
        int size = 0;
        int[] indexvirus = null;
        for (int i = 0; i < data.length; i++)
        {
            if (data[i]) {
                size++;
                System.out.println("The Size ="+size);
            }
        }
        indexvirus = new int[size];
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i]) {
                indexvirus[j] = i;
                j++;
            }
        }
        Random rand = new Random();
        if (size == 0)
        {
            return null;
        }
        int x = rand.nextInt(size);
        return Virus.values()[indexvirus[x]].getv();// return the final virus after calacular

    }

    public static void SetData(boolean[][] newData)
    {
        data = newData;
    }

    //func return matrix
    public static boolean[][] getData()
    {
        return data;
    }

    // func return location val
    public static boolean getvalue(int i, int j)
    {
        return data[i][j];
    }


}