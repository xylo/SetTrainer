package de.endrullis.setTrainer;

import javax.swing.*;
import java.awt.*;

/**
 * Set card component.
 */
public class SetCard extends JPanel{
  private SetCardModel model = null;

  public SetCard(){
    setUI(new SetCardUI());
    setBackground(Color.WHITE);
    setPreferredSize(new Dimension(200, 300));
  }

  public SetCardModel getModel(){
    return model;
  }

  public void setModel(SetCardModel model){
    this.model = model;
    repaint();
  }
}
