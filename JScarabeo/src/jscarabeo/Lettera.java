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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 39334
 */
public class Lettera {
    private char lettera;
    private int valore;

    public Lettera() {
        this.valore = 0;   
    }

    public char getLettera() {
        return lettera;
    }

    public int getValore() {
        return valore;
    }

    public void setLettera(char lettera) {
        this.lettera = lettera;
    }

    public void setValore(int valore) {
        this.valore = valore;
    }
}
