/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jscarabeo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lazzarin_andrea
 */
public class Server extends Thread {
    DatiCondivisi d;
    DatagramSocket server;
    byte[] buffer;
    byte[] dataReceived;
    String messaggioRicevuto;

    public void Server(DatiCondivisi d) throws SocketException {
        this.d = d;
        this.server = new DatagramSocket(d.getMyPort());
        this.buffer = new byte[1500];
        this.messaggioRicevuto = "";
    }

    public void run() {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (d.isInGame()) {
      
            try {
                server.receive(packet);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

            dataReceived = packet.getData(); // copia del buffer dichiarato sopra

            messaggioRicevuto = new String(dataReceived, 0, packet.getLength());
            d.addPacchettoRicevuto(messaggioRicevuto);
            System.out.println(messaggioRicevuto);
        }

    }
}
