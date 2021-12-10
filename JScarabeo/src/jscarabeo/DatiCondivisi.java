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

    List<String> listPacchettiRicevuti;
    int myScore, opponentScore;
    String myPort, opponentPort;
    String myIP, opponentIP;

    public DatiCondivisi() throws UnknownHostException {
        this.listPacchettiRicevuti = new ArrayList<>();
        this.myPort = "666";
        this.opponentPort = "";
        this.myScore = 0;
        this.opponentScore = 0;
        this.myIP = InetAddress.getLocalHost().getHostAddress();
        this.opponentIP = "";
    }

}
