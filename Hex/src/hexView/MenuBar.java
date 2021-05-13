/**
 * Authors : Bruno ARIGANELLO, Titouan CORNILLEAU
 * Date : 2021-05-13
 */
package hexView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar implements ActionListener {

    private HexWindow parent;
    private JMenu menu;
    private JMenuItem itemHelp, itemInfo, itemNew;

    public MenuBar(HexWindow parent) {

        this.parent = parent;

        menu = new JMenu("Options");
        itemHelp = new JMenuItem("Aide");
        itemInfo = new JMenuItem("À propos...");
        itemNew = new JMenuItem("Nouvelle partie");

        itemHelp.addActionListener(this);
        itemInfo.addActionListener(this);
        itemNew.addActionListener(this);

        menu.add(itemHelp);
        menu.add(itemInfo);
        menu.add(itemNew);
        this.add(menu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == itemHelp) {
            new PopHelp();
        }
        if (e.getSource() == itemInfo) {
            new PopHistory();
        }
        if (e.getSource() == itemNew) {
            this.parent.dispose();
            new HexWindow();
        }
    }
}