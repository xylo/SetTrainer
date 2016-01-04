package de.endrullis.setTrainer;

import javax.swing.*;
import java.awt.*;

/**
 * Set trainer applet.
 */
public class SetTrainerApplet extends JApplet {
  public void init() {
    // init applet
    Container cp = getContentPane();
    cp.setLayout(new BorderLayout());
    cp.add(new SetPanel(this), BorderLayout.CENTER);
  }
}
