/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jscarabeo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lazzarin_andrea
 */
public class Server extends Thread {

    DatiCondivisi d;
    ServerSocket serverSocket;

    public Server(DatiCondivisi d) throws IOException {
        this.d = d;
        serverSocket = new ServerSocket(d.getMyPort());
    }

    @Override
    public void run() {
        while (d.isProgramStarted()) {
            try {
                if (!d.isInGame()) {
                    Socket clientSocket = serverSocket.accept();
                    d.setInGame(true);

                    //inizia chi manda la richiesta per primo 
                    if (!d.isTurno()) {
                        d.setTurno(true);
                        d.addPacchettoDaInviare("T");
                    } else {
                        d.setTurno(false);
                    }

                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String inputLine;
                    while (d.isInGame()) {
                        if ((inputLine = in.readLine()) != null) {
                            System.out.println(inputLine);
                            d.addPacchettoRicevuto(inputLine);
                        }
                    }

                    in.close();
                    clientSocket.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
