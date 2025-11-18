package main.com.rozsakgergo.window.bars;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar implements ActionListener {
    JMenu fileMenu = new JMenu("File");
    JMenu editMenu = new JMenu("Edit");
    JMenu helpMenu = new JMenu("Help");
    JMenuItem loadItem = new JMenuItem("Load");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem exitItem = new JMenuItem("Exit");
    JMenuItem editPointItem = new JMenuItem("Edit Points");
    JMenuItem editLinesItem = new JMenuItem("Edit Lines");
    public MenuBar() {
        this.add(fileMenu);
        this.add(editMenu);
        this.add(helpMenu);
        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        editMenu.add(editPointItem);
        editMenu.add(editLinesItem);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==loadItem) {
            System.out.println("WIP: Item Loaded");
        } else if(e.getSource()==saveItem) {
            System.out.println("WIP: Item Saved");
        }
    }
}
