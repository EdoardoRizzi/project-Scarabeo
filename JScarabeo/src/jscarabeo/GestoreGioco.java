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
    private String[] Bonus;
    private int numElParola, numElX, numElY, numElBonus;
    
    private boolean confermato;
    //timer

    public GestoreGioco(DatiCondivisi d) throws IOException {
        this.d = d;
        this.FileCSV = new File("LettereValore.csv");
        this.Mano = new Lettera[8];
        for (int i = 0; i < Mano.length; i++) {
            this.Mano[i] = null;
        }
        this.ListaParole = new ArrayList<>();
        ParolaInCorso = new char[17];
        PosizioniSullaX = new int[17];
        PosizioniSullaY = new int[17];
        Bonus = new String[17];
        confermato = false;
        
        PulisciVettori();
    }

    public void run() {
        try {
            generaLista(FileCSV);
            //caricaListaParole();    CRASH TROPPE PAROLE
        } catch (IOException ex) {
            Logger.getLogger(GestoreGioco.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (d.isInGame()) {
            if (d.isTurno() && ManoPossibile()) { //la partita finisce quando non si può più pescare???
                Pescaggio();
            }
            while (!confermato) {
            }
            if (numElParola > 0) {
                if (CercaLettera()) {//trova la lettera mancante
                    if (ControlloParola()) { //Controlla che esista
                        CalcolaPunteggio();    //Calcola punteggio
                        RimuoviLettereUsate();
                    } else {
                        //togli lettere messe nel tabellone e rimettile nella mano
                    }
                }
            }
            PassoDelTurno();
        }
        //vittoria/sconfitta
    }

    //conto quanto tessere deve pescare e controllo che nel sacchetto ci siano abbastanza tessere
    public boolean ManoPossibile() {
        int TessereDaPescare = 0;
        for (int i = 0; i < Mano.length; i++) {
            if (Mano[i] == null) {
                TessereDaPescare++;
            }
        }
        if (d.getlistSacchetto().size() > TessereDaPescare) {
            return true;
        } else {
            return false;
        }
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
                    d.addSacchetto(l);
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
                    messaggioLettere(i);
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

    //compongo il messaggio da inviare per rimuovere le lettere pescate dal sacchetto
    public void messaggioLettere(int pos) {
        String m = "LOUT;" + Mano[pos];
        d.addPacchettoDaInviare(m);
    }

    //ogni volta che una tessera sarà aggiunta sul tabellone verrà richiamto questo metodo
    public void ComponiParola(char let, int posX, int posY, String bonus) {
        ParolaInCorso[numElParola] = let;
        PosizioniSullaX[numElX] = posX;
        PosizioniSullaY[numElY] = posY;
        Bonus[numElBonus] = bonus;

        numElParola++;
        numElX++;
        numElY++;
    }
    
    public boolean CercaLettera() {
        int posMancante = 0;
        int[] Copia;
        boolean Sent = true;
        boolean incrocia = false;

        if (ParolaVert()) {
            Copia = PosizioniSullaX;
        } else {
            Copia = PosizioniSullaY;
        }

        while (Sent || posMancante < Copia.length) {
            if (Copia[posMancante] + 1 != Copia[posMancante + 1]) {
                Sent = false;
                incrocia = true;
                posMancante++;
            }
        }

        //controllo che la lettera che interseca sia l'ultima o la prima lettera che compone la parola
        Lettera[][] CopiaMatrice = d.getMatrice();
        boolean ultimaLettera = false;
        //controllo se è l'ulitma
        if (posMancante == numElParola - 1) {
            if (ParolaVert()) {
                if (CopiaMatrice[PosizioniSullaX[0]][posMancante] != null) {//se è presente una lettera in quella posizione allora esiste
                    ultimaLettera = true;
                    incrocia = true;
                }
            } else {
                if (CopiaMatrice[posMancante][PosizioniSullaY[0]] != null) {
                    ultimaLettera = true;
                    incrocia = true;
                }
            }
        }
        //se non è ancora stata trovata la lettera vuol dire che è la prima
        if (!ultimaLettera) {
            if (ParolaVert()) {
                if (CopiaMatrice[PosizioniSullaX[0]][PosizioniSullaY[0] - 1] != null) {//se è presente una lettera in quella posizione allora esiste
                    posMancante = PosizioniSullaY[0] - 1;
                    incrocia = true;
                }
            } else {
                if (CopiaMatrice[PosizioniSullaX[0] - 1][PosizioniSullaY[0]] != null) {
                    posMancante = PosizioniSullaX[0] - 1;
                    incrocia = true;
                }

            }
        }

        if (incrocia) {
            CreaSpazio(posMancante);
        }
        return incrocia;
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
            AggiungiParolaAllaMatrice();
            return true;
        } else {
            return false;
        }
    }

    public void AggiungiParolaAllaMatrice() {
        for (int i = 0; i < numElParola; i++) {
            d.addLetteraMatrice(ParolaInCorso[i], PosizioniSullaX[i], PosizioniSullaY[i]);
        }
    }

    public void PassoDelTurno() {
        //genero ed aggiungo il messaggio da inviare
        String m = ComponiMessaggio();
        d.addPacchettoDaInviare(m);

        // preparo le variabili per il turno successivo
        //Mano.clear(); capire se bisogna svuotare anche la mano o le lettere non usate restano
        confermato = false;
        PulisciVettori();
    }

    public void PulisciVettori() {
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
            Bonus[i] = ""; //18 = null perché è un valore che non verrà mai usato
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
        ReinserisciLettera();
        for (int i = 0; i < Mano.length; i++) {
            d.addSacchetto(Mano[i]);
            Mano[i] = null;
        }
        Pescaggio();

        //messaggi lin lout
    }

    //invio il messaggio per reinserire tutte le lettere nel sacchetto
    public void ReinserisciLettera() {
        String m = "LIN";
        for (int i = 0; i < Mano.length; i++) {
            m += ";" + Mano[i];
        }
        d.addPacchettoDaInviare(m);
    }

    public void CalcolaPunteggio() {
        String Parola = new String(ParolaInCorso);

        if (Parola.equals("Scarabeo") || Parola.equals("Scarabei")) {
            d.updateMyScore(100);
        }

        if (numElParola == 6) {
            d.updateMyScore(10);
        } else if (numElParola == 7) {
            d.updateMyScore(30);
        } else if (numElParola == 8) {
            d.updateMyScore(50);
        }
        int conteggioParziale = 0;
        Lettera l;
        boolean raddoppia = false;
        boolean triplica = false;
        for (int i = 0; i < Bonus.length; i++) {
            if (Bonus[i] != "") {

                if (Bonus[i].equals("2L")) {
                    l = d.cercaLettera(ParolaInCorso[i]);
                    conteggioParziale += l.getValore() * 2;
                }
                if (Bonus[i].equals("3L")) {
                    l = d.cercaLettera(ParolaInCorso[i]);
                    conteggioParziale += l.getValore() * 3;
                }

                if (Bonus[i].equals("2P")) {
                    raddoppia = true;
                    l = d.cercaLettera(ParolaInCorso[i]);
                    conteggioParziale += l.getValore();
                }
                if (Bonus[i].equals("3P")) {
                    triplica = true;
                    l = d.cercaLettera(ParolaInCorso[i]);
                    conteggioParziale += l.getValore();
                }
            } else {
                l = d.cercaLettera(ParolaInCorso[i]);
                conteggioParziale += l.getValore();
            }
        }
        if (raddoppia) {
            conteggioParziale *= 2;
        }
        if (triplica) {
            conteggioParziale *= 3;
        }
        
        d.updateMyScore(conteggioParziale);
    }

    public void resa() {
        d.setInGame(false);
        d.setMyScore(0);

        String m = "D;";
        d.addPacchettoDaInviare(m);
    }
    
     public void setConfermato(boolean confermato) {
        this.confermato = confermato;
    }

}
