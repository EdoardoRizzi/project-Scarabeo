/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jscarabeo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lazzarin_andrea
 */
public class Client {

    DatiCondivisi d;
    Socket socket;

    public Client(DatiCondivisi d) throws SocketException {
        this.d = d;
        socket = null;
    }

    public void sendRichiesta() {

        try {
            InetAddress addr = InetAddress.getByName("192.168.1.71");
            socket = new Socket(addr, 666);
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            

            out.println("C;localhost;Ode");
            //System.out.println(inputLine);
            //out.println("pluto");
            //inputLine = in.readLine();
            //System.out.println(inputLine);
            //out.println("exit");
            //inputLine = in.readLine();
            String inputLine;
            do {
                inputLine = in.readLine();
                System.out.println(inputLine);
            } while (inputLine != null); //socket.close();

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }

        //try {
        //socket.close();
        //} catch (IOException ex) {
        // Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }
}
