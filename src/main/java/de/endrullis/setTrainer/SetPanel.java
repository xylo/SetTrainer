package de.endrullis.setTrainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Main Panel.
 */
public class SetPanel extends JPanel implements ActionListener {
  private ArrayList setCards = SetCardModel.getSetCards();
  private ArrayList displayedCards = new ArrayList();
  private Component parentComponent;

  private long time, timeSum;
  private int count, falts;
  private static final int count_MAX = 50;

  public SetPanel(Component parentComponent){
    this.parentComponent = parentComponent;

    Container cp = this;
    SpringLayout layout = new SpringLayout();
    cp.setLayout(layout);

    Component westComponent = cp;
    String border = SpringLayout.WEST;
    SetCard card = null;
    for(int i=0; i<3; i++){
      card = new SetCard();
      cp.add(card);
      layout.putConstraint(SpringLayout.NORTH, card, 10, SpringLayout.NORTH, cp);
      layout.putConstraint(SpringLayout.WEST, card, 10, border, westComponent);
      westComponent = card;
      border = SpringLayout.EAST;
      displayedCards.add(card);
    }
    layout.putConstraint(SpringLayout.EAST, cp, 10, SpringLayout.EAST, card);
//    layout.putConstraint(SpringLayout.SOUTH, cp, 10, SpringLayout.SOUTH, card);

    JButton startButton = new JButton("Start");
    startButton.setMnemonic(KeyEvent.VK_S);
    startButton.setActionCommand("start");
    startButton.addActionListener(this);
    startButton.registerKeyboardAction(this, "start", KeyStroke.getKeyStroke("S"), JButton.WHEN_IN_FOCUSED_WINDOW);
    cp.add(startButton);
    layout.putConstraint(SpringLayout.NORTH, startButton, 10, SpringLayout.SOUTH, card);
    layout.putConstraint(SpringLayout.WEST, startButton, 10, SpringLayout.WEST, cp);

    JLabel setLabel = new JLabel("Set?");
    cp.add(setLabel);
    layout.putConstraint(SpringLayout.NORTH, setLabel, 15, SpringLayout.SOUTH, card);
    layout.putConstraint(SpringLayout.WEST, setLabel, 10, SpringLayout.EAST, startButton);

    JButton yesButton = new JButton("Yes");
    yesButton.setMnemonic(KeyEvent.VK_Y);
    yesButton.setActionCommand("yes");
    yesButton.addActionListener(this);
    yesButton.registerKeyboardAction(this, "yes", KeyStroke.getKeyStroke("Y"), JButton.WHEN_IN_FOCUSED_WINDOW);
    cp.add(yesButton);
    layout.putConstraint(SpringLayout.NORTH, yesButton, 10, SpringLayout.SOUTH, card);
    layout.putConstraint(SpringLayout.WEST, yesButton, 10, SpringLayout.EAST, setLabel);

    JButton noButton = new JButton("No");
    noButton.setMnemonic(KeyEvent.VK_N);
    noButton.setActionCommand("no");
    noButton.addActionListener(this);
    noButton.registerKeyboardAction(this, "no", KeyStroke.getKeyStroke("N"), JButton.WHEN_IN_FOCUSED_WINDOW);
    cp.add(noButton);
    layout.putConstraint(SpringLayout.NORTH, noButton, 10, SpringLayout.SOUTH, card);
    layout.putConstraint(SpringLayout.WEST, noButton, 10, SpringLayout.EAST, yesButton);
    layout.putConstraint(SpringLayout.SOUTH, cp, 10, SpringLayout.SOUTH, noButton);
  }

  private boolean isSet(SetCardModel a, SetCardModel b, SetCardModel c){
    // count
    if(!testEquality(a.getCount(), b.getCount(), c.getCount()) &&
        !testInequality(a.getCount(), b.getCount(), c.getCount())) return false;

    // colors
    if(!testEquality(a.getColor(), b.getColor(), c.getColor()) &&
        !testInequality(a.getColor(), b.getColor(), c.getColor())) return false;

    // shape
    if(!testEquality(a.getShape(), b.getShape(), c.getShape()) &&
        !testInequality(a.getShape(), b.getShape(), c.getShape())) return false;

    // filled
    if(!testEquality(a.getFilled(), b.getFilled(), c.getFilled()) &&
        !testInequality(a.getFilled(), b.getFilled(), c.getFilled())) return false;

    return true;
  }

  private boolean testEquality(int a, int b, int c){
    return a == b && b == c;
  }

  private boolean testInequality(int a, int b, int c){
    return a != b && b != c && a != c;
  }

  // ActionListener
  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();

//    System.out.println("cmd = " + cmd);

    if(cmd.equals("start")){  // new game
      // set cards
      randomCards();

      count = 0;  falts = 0;
      timeSum = 0;
      time = System.currentTimeMillis();

      return;
    }

    // time difference
    time = System.currentTimeMillis() - time;
    SetCardModel a = ((SetCard) displayedCards.get(0)).getModel();
    SetCardModel b = ((SetCard) displayedCards.get(1)).getModel();
    SetCardModel c = ((SetCard) displayedCards.get(2)).getModel();
    if(a == null) return;

    boolean isSet = isSet(a, b, c);
    if(isSet != cmd.equals("yes")){
      time = 10000;
      falts++;
      JOptionPane.showMessageDialog(parentComponent, "Falsch: Das ist " + (isSet ? "ein" : "kein") + " Set!");
    }

    count++;
    timeSum += time;

    System.out.println("time = " + time);

    if(count == count_MAX){  // finish game
      JOptionPane.showMessageDialog(parentComponent, "Congratulation!\n\nFalts: " + falts + " / " + count_MAX + "\n\tAverage time: " + timeSum/count_MAX + "ms");

      // set empty cards
      for(int i = 0; i < displayedCards.size(); i++){
        SetCard setCard = (SetCard) displayedCards.get(i);
        setCard.setModel(null);
      }
    }

    // set cards
    randomCards();
    time = System.currentTimeMillis();
  }

  private void randomCards() {
    SetCardModel a;
    SetCardModel b;
    SetCardModel c;
    boolean shouldBeSet = Math.random() * 2 < 1;
    while(true){
      for(int i = 0; i < displayedCards.size(); i++){
        SetCard setCard = (SetCard) displayedCards.get(i);
        setCard.setModel((SetCardModel) setCards.get((int) (Math.random() * setCards.size())));
      }

      a = ((SetCard) displayedCards.get(0)).getModel();
      b = ((SetCard) displayedCards.get(1)).getModel();
      c = ((SetCard) displayedCards.get(2)).getModel();

      if(isSet(a, b, c) == shouldBeSet) break;
    }
  }
}
