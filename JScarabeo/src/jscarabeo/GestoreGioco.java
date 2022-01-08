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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 39334
 */
public class GestoreGioco extends Thread {

    private DatiCondivisi d;
    private File FileCSV;
    private Lettera[] Mano; //tessere in mano durante il turno
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
        this.Mano = new Lettera[8];
        for (int i = 0; i < Mano.length; i++) {
            this.Mano[i] = null;
        }
        this.ListaParole = new ArrayList<>();
        ParolaInCorso = new char[17];
        PosizioniSullaX = new int[17];      
        PosizioniSullaY = new int[17];
        Bonus = new int[17];

        PulisciVettori();
    }

    public void run() {
        try {
            caricaListaParole();
            generaLista(FileCSV);
        } catch (IOException ex) {
            Logger.getLogger(GestoreGioco.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (d.isInGame()) {
            if (d.isTurno()) {
                Pescaggio();
            }
            while (d.isTurno()) {
            }
            if (numElParola > 0) {
                CercaLettera(); //trova la lettera mancante
                if (ControlloParola()) { //Controlla che esista
                    //calcolaPunteggio();    //Calcola punteggio
                    RimuoviLettereUsate();
                }
            }
            PassoDelTurno();
        }
        //vittoria/sconfitta
    }

    //Genera il "sacchetto" dal quale si pescano le tessere
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
                //aggiungo le lettere nel sacchato da cui si pesca
                for (int i = 0; i < Integer.parseInt(v[2]); i++) {
                    d.addSaccheto(l);
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lettera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void caricaListaParole() throws FileNotFoundException, IOException {
        File f = new File("Parole.txt"); //file contente tutte le parole utilizzate
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String linea;

        while ((linea = br.readLine()) != null) { //assegno un valore alla var linea e se è nulla non entro nel ciclo
            ListaParole.add(linea);
        }
    }

    public void Pescaggio() {
        if (d.getlistSacchetto() != null) {

            Random r = new Random();
            int posLettera;

            for (int i = 0; i < Mano.length; i++) {
                if (Mano[i] == null) {
                    posLettera = r.nextInt(d.getlistSacchetto().size());
                    Mano[i] = d.getlistSacchetto().get(posLettera);
                    d.removeLettera(d.getlistSacchetto().get(posLettera).getLettera());
                    d.getTabellone().setMano(i, d.getlistSacchetto().get(posLettera));
                }
            }
        }
    }

    public void RimuoviLettereUsate() {
        for (int i = 0; i < Mano.length; i++) {
            if (Mano[i].getLettera() == ParolaInCorso[i]) {
                Mano[i] = null;
            }
        }
    }
    //ogni volta che una tessera sarà aggiunta sul tabellone verrà richiamto questo metodo

    public void ComponiParola(char let, int posX, int posY, int bonus) {
        ParolaInCorso[numElParola] = let;
        PosizioniSullaX[numElX] = posX;
        PosizioniSullaY[numElY] = posY;
        Bonus[numElBonus] = bonus;

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

        CreaSpazio(posMancante);

    }

      public void CreaSpazio(int pos) {
        //inserisco la lettera mancante nel vettore ParolaInCorso
        Lettera[][] CopiaMatrice = d.getMatrice();
        char sposta = 'y', sposta1 = 'y';

        for (int i = pos; i < numElParola + 1; i++) {
            if (i == pos) {
                sposta = ParolaInCorso[i];
                if (ParolaVert()) {
                    ParolaInCorso[i] = CopiaMatrice[pos][PosizioniSullaX[0]].getLettera();
                } else {
                    ParolaInCorso[i] = CopiaMatrice[pos][PosizioniSullaY[0]].getLettera();
                }
            }
            if (ParolaInCorso[i + 1] == 'y') {
                sposta1 = ParolaInCorso[i + 1];
            }
            ParolaInCorso[i + 1] = sposta;
            sposta = sposta1;
        }

        int appoggio = 18, appoggio1 = 18;
        if (ParolaVert()) {
            for (int i = pos; i < numElX + 1; i++) {
                if (i == pos) {
                    appoggio = PosizioniSullaY[i];
                    PosizioniSullaY[i] = pos;
                }
                if (PosizioniSullaY[i + 1] == 18) {
                    appoggio1 = PosizioniSullaY[i + 1];
                }
                PosizioniSullaY[i + 1] = appoggio;
                appoggio = appoggio1;

            }
            numElX++;
            PosizioniSullaX[numElX] = PosizioniSullaX[numElX - 1];
        } else {
            for (int i = pos; i < numElY + 1; i++) {
                if (i == pos) {
                    appoggio = PosizioniSullaX[i];
                    PosizioniSullaX[i] = pos;
                }
                if (PosizioniSullaX[i + 1] == 18) {
                    appoggio1 = PosizioniSullaX[i + 1];
                }
                PosizioniSullaX[i + 1] = appoggio;
                appoggio = appoggio1;
            }
            PosizioniSullaY[numElY] = PosizioniSullaY[numElY - 1];
            numElY++;
        }
    }

    public boolean ParolaVert() {
        boolean vert = false;

        if (PosizioniSullaX[0] == PosizioniSullaX[1]) {
            vert = true;
        }
        return vert;

    }

    //verifica che la parola inserita dal giocatore esista realmente
    public boolean ControlloParola() {
        String Parola = new String(ParolaInCorso);
        int index = ListaParole.indexOf(Parola);

        if (index != -1) {
            return true;
        } else {
            return false;
        }
    }

    public void PassoDelTurno() {
        //genero ed aggiungo il messaggio da inviare
        String m = ComponiMessaggio();
        d.addPacchettoDaInviare(m);

        // preparo le variabili per il turno successivo
        //Mano.clear(); capire se bisogna svuotare anche la mano o le lettere non usate restano
        PulisciVettori();
    }
    
    public void PulisciVettori(){
        for (int i = 0; i < Mano.length; i++) {
            Mano[i] = null;
        }
        for (int i = 0; i < ParolaInCorso.length; i++) {
            ParolaInCorso[i] = 'y'; //'y' = null perché è un valore che non verrà mai usato
        }
        for (int i = 0; i < PosizioniSullaX.length; i++) {
            PosizioniSullaX[i] = 18; //18 = null perché è un valore che non verrà mai usato
        }
        for (int i = 0; i < PosizioniSullaY.length; i++) {
            PosizioniSullaY[i] = 18; //18 = null perché è un valore che non verrà mai usato
        }
        for (int i = 0; i < Bonus.length; i++) {
            Bonus[i] = 18; //18 = null perché è un valore che non verrà mai usato
        }

        numElParola = 0;
        numElX = 0;
        numElY = 0;
        numElBonus = 0;
    }

    //scrive il messsaggio che sarà inviato all'avversario
    public String ComponiMessaggio() {
        String s = "P;";

        for (int i = 0; i < ParolaInCorso.length; i++) {
            s += ParolaInCorso[i] + "-" + PosizioniSullaX[i] + "-" + PosizioniSullaY + ";";
        }

        return s;
    }

    //in caso che con le lettere in mano non riesca a comporre nessuna parola
    public void ManoDaRifare() {
        for (int i = 0; i < Mano.length; i++) {
            d.addSaccheto(Mano[i]);
            Mano[i] = null;
        }
        Pescaggio();

        //messaggi lin lout
    }

    public void resa() {
        d.setInGame(false);
    }

}
