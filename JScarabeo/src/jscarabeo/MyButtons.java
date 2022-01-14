/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jscarabeo;

import javax.swing.JButton;

/**
 *
 * @author lazzarin_andrea
 */
public class MyButtons extends JButton {

    private int x, y;
    private Lettera l;
    private String bonus;

    //btn per la mano
    public MyButtons() {
        this.x = -1;
        this.y = -1;
    }

    //buttons vuoti con bonus
    public MyButtons(int x, int y, String bonus) {
        this.x = x;
        this.y = y;
        this.l = null;
        this.bonus = bonus;
    }

    //buttons vuoti
    public MyButtons(int x, int y) {
        this.x = x;
        this.y = y;
        this.l = null;
        this.bonus = "";
    }

    //buttons della mano
    public MyButtons(int x, int y, Lettera l) {
        this.x = x;
        this.y = y;
        this.l = l;
        this.bonus = "";
    }

    public Lettera getLettera() {
        return l;
    }

    public void setLettera(Lettera l) {
        this.l = l;
    }

    public String getBonus() {
        return bonus;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
