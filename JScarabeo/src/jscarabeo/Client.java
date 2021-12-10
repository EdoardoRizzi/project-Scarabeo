/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jscarabeo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author lazzarin_andrea
 */
public class Client {

    DatiCondivisi d;
    DatagramSocket client;

    public Client(DatiCondivisi d) throws SocketException {
        this.d = d;
        client = new DatagramSocket(d.getOpponentPort());
    }

    public void sendRichiesta() throws UnknownHostException, IOException {
        String s = "C;" + d.getOpponentIP() + ";" + d.getOpponentNickname();

        byte[] responseBuffer = s.getBytes();

        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

        responsePacket.setAddress(InetAddress.getByName(d.getOpponentIP()));

        responsePacket.setPort(d.getOpponentPort());

        client.send(responsePacket);
    }
}
