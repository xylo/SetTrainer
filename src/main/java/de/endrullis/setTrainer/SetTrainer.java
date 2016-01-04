package de.endrullis.setTrainer;

import javax.swing.*;
import java.awt.*;

/**
 * Set trainer application.
 */
public class SetTrainer extends JFrame {
  public static void main(String[] args) {
    SetTrainer trainer = new SetTrainer();
    trainer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    trainer.setVisible(true);
  }

  public SetTrainer(){
    super("SetTrainer");

    Container cp = getContentPane();
    cp.add(new SetPanel(this));

    pack();
  }
}
