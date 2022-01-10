/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jscarabeo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lazzarin_andrea
 */
public class Client extends Thread {

    DatiCondivisi d;
    Socket socket;
    PrintWriter out;

    public Client(DatiCondivisi d) throws SocketException {
        this.d = d;
        socket = null;
    }

    public void sendRichiesta() {
        try {
            InetAddress addr = InetAddress.getByName(d.getOpponentIP());
            socket = new Socket(addr, 667);
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            d.addPacchettoDaInviare("C;" + d.getMyIP() + ";" + d.getMyNickname());
            System.out.println("C;" + d.getMyIP() + ";" + d.getMyNickname());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        int conta = 0;
        while (d.isProgramStarted()) {
            if (d.getListPacchettiDaInviare().size() > conta) {
                out.println(d.getListPacchettiDaInviare().get(conta));
                conta++;
                d.setTurno(false);
            }
        }
    }
}
