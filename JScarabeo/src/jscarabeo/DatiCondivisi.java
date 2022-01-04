/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jscarabeo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lazzarin_andrea
 */
public class DatiCondivisi {

    private List<String> listPacchettiRicevuti;
    private int myScore, opponentScore;
    private int myPort, opponentPort;
    private String myIP, opponentIP;
    private String myNickname, opponentNickname;
    private boolean inGame;
    private boolean turn;

    public DatiCondivisi() throws UnknownHostException {
        this.listPacchettiRicevuti = new ArrayList<>();
        this.myPort = 666;
        this.opponentPort = 667;
        this.myScore = 0;
        this.opponentScore = 0;
        this.myIP = InetAddress.getLocalHost().getHostAddress();
        this.opponentIP = "";
        this.inGame = false;
    }

    public void addPacchettoRicevuto(String pacchetto) {
        this.listPacchettiRicevuti.add(pacchetto);
    }

    public String getOpponentIP() {
        return opponentIP;
    }

    public void setOpponentIP(String opponentIP) {
        this.opponentIP = opponentIP;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public int getOpponentPort() {
        return opponentPort;
    }

    public void setOpponentPort(int opponentPort) {
        this.opponentPort = opponentPort;
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

    public int getMyPort() {
        return myPort;
    }
    
    
}
