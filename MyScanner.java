
package dirtree;

import java.io.*;
import java.util.Scanner;

public class MyScanner {

    public static void main(String[] args) throws IOException {
        
        Scanner s = null;
        
        try{
            s = new Scanner (new BufferedReader(new FileReader("Liv.xml")));
            while (s.hasNext()){   // så länge strängen s har fler bokstäver/ord fortsätt scanna.
                System.out.println(s.next());
            }
        }catch(FileNotFoundException e){
             System.out.println("FILE NOT FOUND!");
        }finally{
            if(s != null){
                s.close();
            }
        }
    }
    
}
    

