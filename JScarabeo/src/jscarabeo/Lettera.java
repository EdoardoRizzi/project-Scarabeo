/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jscarabeo;

import javax.swing.ImageIcon;

/**
 *
 * @author 39334
 */
public class Lettera {

    private char lettera;
    private int valore;
    private ImageIcon img;

    public Lettera() {
        this.valore = 0;
    }

    public Lettera(char l, int v) {
        lettera = l;
        valore = v;
        img = new ImageIcon(lettera + ".png");
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

    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }
}
