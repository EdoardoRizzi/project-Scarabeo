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
        while (d.isProgramStarted()) {
            if (d.isInGame()) {
                if (d.getListPacchettiRicevuti().size() > conta) {
                    String temp = d.getListPacchettiRicevuti().get(conta);
                    String[] campi = temp.split(";");
                    switch (campi[0]) {
                        case "C" -> {
                            d.setOpponentIP(campi[1]);
                            d.setOpponentNickname(campi[2]);
                        }
                        case "P" -> {
                            for (int i = 1; i < campi.length - 3; i++) {
                                d.addLetteraMatrice(campi[i].charAt(0), Integer.parseInt(campi[i++]), Integer.parseInt(campi[i++]));
                            }
                            d.addOpponentScore(Integer.parseInt(campi[campi.length - 1]));
                            d.setTurno(true);
                        }
                        case "D" ->
                            d.setInGame(false);
                        case "T" ->
                            //imposto turno a true per far entrare nell'else e invertirla nel thread Server
                            d.setTurno(true);
                    }
                    conta++;
                    //rimuove dal sacchetto le lettere pescate
                    //aggiorna matrice
                    //aggiorna grafica button con le posizioni
                }
            }
        }
    }
}
