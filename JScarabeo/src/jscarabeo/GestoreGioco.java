/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jscarabeo;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author 39334
 */
public class GestoreGioco extends Thread {

    private DatiCondivisi d;
    private File FileCSV;
    private List<Lettera> Mano; //tessere in mano durante il turno
    private List<String> ListaParole;
    
    private char[] ParolaInCorso;
    private int[] PosizioniSullaX;
    private int[] PosizioniSullaY;
    private int[] Bonus;
    private int numElParola, numElX, numElY, numElBonus;
    //timer

    public GestoreGioco(DatiCondivisi d) throws IOException {
        this.d = d;
        this.FileCSV = new File("ValoreLettera.csv");
        
        ParolaInCorso = new char[17];
        PosizioniSullaX = new int[17];
        PosizioniSullaY = new int[17];
        Bonus = new int[17];
        
        this.numElParola = 0;
        this.numElX = 0;
        this.numElY = 0;
        this.numElBonus = 0;
    }

    public void run() {
        try {
            caricaListaParole();
            
        } catch (IOException ex) {
            Logger.getLogger(GestoreGioco.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (d.isInGame()) {
            Pescaggio();
        }

    }

    public void generaLista(File f) throws IOException {
        FileReader fr;
        try {
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            String v[];

            while ((linea = br.readLine()) != null) { //assegno un valore alla var linea e se è nulla non entro nel ciclo
                v = linea.split(";");
                char let = v[0].charAt(0);
                int val = Integer.parseInt(v[1]);
                Lettera l = new Lettera(let, val);
                d.addLettera(l);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lettera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void caricaListaParole() throws FileNotFoundException, IOException {
        File f = new File("Parole.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String linea;

        while ((linea = br.readLine()) != null) { //assegno un valore alla var linea e se è nulla non entro nel ciclo
            ListaParole.add(linea);
        }
    }

    public void Pescaggio() {
        if (d.getListLettere() != null) {

            Random r = new Random();
            int posLettera;

            while (Mano.size() < 8) {
                posLettera = r.nextInt(131);
                if (d.getListLettere().get(posLettera) != null) {
                    Mano.add(d.getListLettere().get(posLettera));
                    d.removeLettera(d.getListLettere().get(posLettera).getLettera());
                }
            }
        }
    }

    public void RimuoviLettereUsate() {
        int[] posDaRimuovere = new int[8];
        int numEl = 0;

        if (ParolaInCorso.length != 8) {
            for (int i = 0; i < Mano.size(); i++) {
                if (Mano.get(i).getLettera() == ParolaInCorso[i]) {
                    posDaRimuovere[numEl] = i;
                    numEl++;
                }
            }

            for (int i = 0; i < posDaRimuovere.length; i++) {
                Mano.remove(i);
            }
        } else {
            Mano.clear();
        }
    }

    //ogni volta che una tessera sarà aggiunta sul tabellone verrà richiamto questo metodo
    public void ComponiParola(char let, int posX, int posY) {
        ParolaInCorso[numElParola] = let;
        PosizioniSullaX[numElX] = posX;
        PosizioniSullaY[numElY] = posY;

        numElParola++;
        numElX++;
        numElY++;
    }

    public void CercaLettera() {
        int posMancante = 0;
        int[] Copia;
        boolean Sent = true;

        if (ParolaVert()) {
            Copia = PosizioniSullaX;
        } else {
            Copia = PosizioniSullaY;
        }

        while (Sent || posMancante < Copia.length) {
            if (Copia[posMancante] + 1 != Copia[posMancante + 1]) {
                Sent = false;
                posMancante++;
            }
        }
    }

    public boolean ParolaVert() {
        boolean vert = false;

        if (PosizioniSullaX[0] == PosizioniSullaX[1]) {
            vert = true;
        }
        return vert;

    }

    public boolean ControlloParola() {
        String Parola = new String(ParolaInCorso);
        int index = ListaParole.indexOf(Parola);
        
        if(index != -1){
            return true;
        }else{
            return false;
        }
    }
    
    public void PassoDelTurno(){
        
        String m = ComponiMessaggio();
        d.addPacchettoDaInviare(m);
        Mano.clear();
        ParolaInCorso = new char[17];
        PosizioniSullaX = new int[17];
        PosizioniSullaY = new int[17];
        Bonus = new int[17];
        
        numElParola = 0;
        numElX = 0;
        numElY = 0;
        numElBonus = 0;
    }
    
    public String ComponiMessaggio(){
        String s = "P;";
        
        for(int i=0; i<ParolaInCorso.length;i++){
            s += ParolaInCorso[i]+"-"+PosizioniSullaX[i]+"-"+PosizioniSullaY+";";
        }
        
        return s;
    }
}
