/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jscarabeo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lazzarin_andrea
 */
public class DatiCondivisi {

    private Lettera[][] matrice;
    private List<Lettera> ListLettere;
    private List<String> listPacchettiRicevuti;
    private List<String> listPacchettiDaInviare;
    private List<Lettera> listSacchetto;

    private int myScore, opponentScore;
    private int myPort, opponentPort;
    private String myIP, opponentIP;
    private String myNickname, opponentNickname;

    private boolean inGame, Turno, programStarted;
    private Tabellone t;

    public DatiCondivisi() throws UnknownHostException {
        this.matrice = new Lettera[17][17];
        this.listPacchettiRicevuti = new ArrayList<>();
        this.listPacchettiDaInviare = new ArrayList<>();
        this.ListLettere = new ArrayList<>();
        this.listSacchetto = new ArrayList<>();

        this.myPort = 666;
        this.opponentPort = 667;
        this.myScore = 0;
        this.opponentScore = 0;

        this.myIP = "localhost";
        this.myNickname = "";
        this.opponentIP = "";
        this.opponentNickname = "";

        this.inGame = false;
        this.Turno = false;
        this.programStarted = true;

        this.t = null;

    }

    public void addLettera(Lettera l) {
        ListLettere.add(l);
    }
    
    public void addLetteraSacchetto(Lettera l) {
        listSacchetto.add(l);
    }

    public void addSacchetto(Lettera l) {
        listSacchetto.add(l);
    }

    public void addPacchettoRicevuto(String pacchetto) {
        this.listPacchettiRicevuti.add(pacchetto);
    }

    public void addPacchettoDaInviare(String pacchetto) {
        this.listPacchettiDaInviare.add(pacchetto);
    }

    public void removeLettera(char c) {
        int i = 0;
        int indexTrovato = -1;
        boolean trovato = false;
        while (!trovato && i < listSacchetto.size()) {
            if (listSacchetto.get(i).getLettera() == c) {
                trovato = true;
                indexTrovato = i;
            }
        }
        if (indexTrovato != -1) {
            ListLettere.remove(i);
        }
    }

    public Lettera cercaLettera(char c) {
        int i = 0;
        int indexTrovato = -1;
        boolean trovato = false;
        Lettera l = new Lettera();
        while (!trovato && i < listSacchetto.size()) {
            if (listSacchetto.get(i).getLettera() == c) {
                trovato = true;
                indexTrovato = i;
            }
        }
        if (indexTrovato != -1) {
            l = ListLettere.get(i);
        }
        return l;
    }

    public Lettera[][] getMatrice() {
        return matrice;
    }

    public void setMatrice(Lettera[][] matrice) {
        this.matrice = matrice;
    }

    public void addLetteraMatrice(char c, int x, int y) {
        Lettera l;
        l = cercaLettera(c);

        if (l.getValore() == 0) {//se il valore è zero la lettera non esiste
            matrice[x][y] = l;
        }
    }

    public List<Lettera> getListLettere() {
        return ListLettere;
    }

    public void setListLettere(List<Lettera> ListLettere) {
        this.ListLettere = ListLettere;
    }

    public List<String> getListPacchettiRicevuti() {
        return listPacchettiRicevuti;
    }

    public void setListPacchettiRicevuti(List<String> listPacchettiRicevuti) {
        this.listPacchettiRicevuti = listPacchettiRicevuti;
    }

    public List<String> getListPacchettiDaInviare() {
        return listPacchettiDaInviare;
    }

    public void setListPacchettiDaInviare(List<String> listPacchettiDaInviare) {
        this.listPacchettiDaInviare = listPacchettiDaInviare;
    }

    public List<Lettera> getlistSacchetto() {
        return listSacchetto;
    }

    public void setlistSacchetto(List<Lettera> listSacchetto) {
        this.listSacchetto = listSacchetto;
    }

    public int getMyScore() {
        return myScore;
    }

    public void setMyScore(int myScore) {
        this.myScore = myScore;
    }
    
    public void updateMyScore(int score){
        this.myScore += score;
    }
    
    public int getOpponentScore() {
        return opponentScore;
    }

    public void addOpponentScore(int score) {
        this.opponentScore += score;
    }

    public int getMyPort() {
        return myPort;
    }

    public void setMyPort(int myPort) {
        this.myPort = myPort;
    }

    public int getOpponentPort() {
        return opponentPort;
    }

    public void setOpponentPort(int opponentPort) {
        this.opponentPort = opponentPort;
    }

    public String getMyIP() {
        return myIP;
    }

    public void setMyIP(String myIP) {
        this.myIP = myIP;
    }

    public String getOpponentIP() {
        return opponentIP;
    }

    public void setOpponentIP(String opponentIP) {
        this.opponentIP = opponentIP;
    }

    public String getMyNickname() {
        return myNickname;
    }

    public void setMyNickname(String myNickname) {
        this.myNickname = myNickname;
    }

    public String getOpponentNickname() {
        return opponentNickname;
    }

    public void setOpponentNickname(String opponentNickname) {
        this.opponentNickname = opponentNickname;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public boolean isTurno() {
        return Turno;
    }

    public void setTurno(boolean Turno) {
        this.Turno = Turno;
    }

    public void setTabellone(Tabellone t) {
        this.t = t;
    }

    public Tabellone getTabellone() {
        return t;
    }

    public boolean isProgramStarted() {
        return programStarted;
    }

    public void setProgramStarted(boolean programStarted) {
        this.programStarted = programStarted;
    }
}
