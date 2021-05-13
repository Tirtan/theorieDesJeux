/**
 * Authors : Bruno ARIGANELLO, Titouan CORNILLEAU
 * Date : 2021-05-13
 */
package hexView;

import javax.swing.*;
import java.awt.*;

public class PopHelp extends JFrame {

    private JTextArea text;

    public PopHelp() {
        this.text = new JTextArea(
                "Le jeu de Hex est un jeu de société combinatoire abstrait pour deux joueurs. " +
                "Il se joue sur un tablier en forme de losange dont les cases sont hexagonales. \n\n" +
                "Le joueur blanc commence. Les joueurs jouent chacun leur " +
                "tour. À chaque tour, le joueur place un pion de sa couleur sur une case libre" +
                "du plateau. Le premier joueur qui réussit à relier ses deux bords par un" +
                "chemin de pions contigus de sa couleur a gagné. Il ne peut y avoir qu'un pion" +
                "par case. Les pions posés le sont définitivement, ils ne peuvent être ni retirés," +
                "ni déplacés.");

        this.text.setLineWrap(true);
        this.text.setWrapStyleWord(true);
        this.text.setEditable(false);
        this.text.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        this.text.setMargin(new Insets(10,10,10,10));
        this.add(text);
        this.setSize(450,425);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
