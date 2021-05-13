/**
 * Authors : Bruno ARIGANELLO, Titouan CORNILLEAU
 * Date : 2021-05-13
 */
package hexView;

import javax.swing.*;
import java.awt.*;

public class PopHistory extends JFrame {

    private JTextArea text;

    public PopHistory() {
        this.text = new JTextArea(
                "Ce jeu, inventé par des mathématiciens fait uniquement appel à la logique, " +
                "à l'image du go ou des échecs. Son étude est source d'inspiration, non " +
                "seulement en théorie des jeux, mais aussi pour d'autres branches des " +
                "mathématiques comme la topologie ou la géométrie algébrique. \n\nLe premier " +
                "énoncé de la règle du jeu est l'œuvre de Piet Hein lors d'une conférence " +
                "en 1942 au Parenthesis, l'association des mathématiciens de l'Université " +
                "de Copenhague. L'auteur cherchait alors à imaginer un jeu équitable, " +
                "progressif, fini, clair, stratégique et décisif. Le jeu porte alors le " +
                "nom de Polygone. \n\nEn 1948, et de manière indépendante, un jeune " +
                "mathématicien de l'Université de Princeton du nom de John Nash " +
                "réinvente le jeu et le diffuse au Fine Hall, une salle commune aux " +
                "élèves et aux professeurs du département de mathématiques. Il en " +
                "perçoit l'intérêt en théorie des jeux et démontre l'existence d'une" +
                " stratégie gagnante pour le premier joueur. Cependant, sa preuve " +
                "est non constructive, c’est-à-dire qu'elle n'indique pas de stratégie" +
                " gagnante. En 1952, la société Parker Brothers commercialise le jeu " +
                "aux États-Unis. ");

        this.text.setLineWrap(true);
        this.text.setWrapStyleWord(true);
        this.text.setEditable(false);
        this.text.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        this.text.setMargin(new Insets(10,10,10,10));

        this.add(text);
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
