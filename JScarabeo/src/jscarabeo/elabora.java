/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jscarabeo;

/**
 *
 * @author lazzarin_andrea
 */
public class Elabora extends Thread {

    private DatiCondivisi d;

    public Elabora(DatiCondivisi d) {
        this.d = d;
    }

    @Override
    public void run() {
        int conta = 0;
        while (d.isInGame()) {
            if (d.getListPacchettiRicevuti().size() > conta) {
                String temp = d.getListPacchettiRicevuti().get(conta);
                String[] campi = temp.split(";");
                switch (campi[0]) {
                    case "C" -> {
                        d.setOpponentIP(campi[1]);
                        d.setOpponentNickname(campi[2]);
                        d.setInGame(true);
                    }
                    case "P" -> {
                    }
                    case "D" -> d.setInGame(false);
                }
                //rimuove dal sacchetto le lettere pescate
                //aggiorna matrice
                //aggiorna grafica button con le posizioni
                            }
        }
    }
}
