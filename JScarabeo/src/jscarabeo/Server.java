/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jscarabeo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;
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
        serverSocket = new ServerSocket(666);
    }

    @Override
    public void run() {

        try 
        {
            if(!d.isInGame()) 
            {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                
                while (d.isInGame()) 
                {
                    String inputLine = in.readLine();
                    System.out.println(inputLine);
                    d.addPacchettoRicevuto(inputLine);
                }
            
            in.close();
            clientSocket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
