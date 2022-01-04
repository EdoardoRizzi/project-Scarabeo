/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jscarabeo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 39334
 */
public class GestoreGioco extends Thread{
    private DatiCondivisi d;
      private File FileCSV;

    public GestoreGioco(DatiCondivisi d) {
        this.d = d;
        this.FileCSV = new File("ValoreLettera.csv");
    }

        
    public void generaLista(File f) throws IOException{
        FileReader fr;
        try {
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            String v[];

            while ((linea = br.readLine()) != null) { //assegno un valore alla var linea e se è nulla non entro nel ciclo
                v = linea.split(";");
                char let = v[0].charAt(0);
                int val = Integer. parseInt(v[1]);
                Lettera l = new Lettera(let, val);
                d.addLettera(l);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lettera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
