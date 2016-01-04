package de.endrullis.setTrainer;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Set card UI.
 */
public class SetCardUI extends BasicPanelUI{

  public void paint(Graphics g, JComponent c) {
    super.paint(g, c);

    Graphics2D g2D = (Graphics2D) g;
    g2D.setClip(0, 0, c.getWidth(), c.getHeight());

    SetCard card = (SetCard) c;
    SetCardModel model = card.getModel();

    // draw frame
    g2D.setColor(Color.BLACK);
    g2D.drawRect(0, 0, card.getWidth()-1, card.getHeight()-1);

    if(model == null) return;

    // objects properies
    int count = 0;
    switch(model.getCount()){
      case SetCardModel.COUNT_1 : count = 1; break;
      case SetCardModel.COUNT_2 : count = 2; break;
      case SetCardModel.COUNT_3 : count = 3; break;
    }

    int shapeWidth = (int) (card.getWidth() * 0.6);
    int shapeHeight = (int) (card.getHeight() / 3 * 0.6);
    Shape shape = null;
    switch(model.getShape()){
      case SetCardModel.SHAPE_RECTANGLE :
        shape = new Rectangle2D.Double(-shapeWidth/2, -shapeHeight/2, shapeWidth, shapeHeight); break;
      case SetCardModel.SHAPE_ELLIPSE :
        shape = new Ellipse2D.Double(-shapeWidth/2, -shapeHeight/2, shapeWidth, shapeHeight); break;
      case SetCardModel.SHAPE_TRIANGLE :
        Polygon polygon = new Polygon();
        polygon.addPoint(0, -shapeHeight/2);
        polygon.addPoint(shapeWidth/2, shapeHeight/2);
        polygon.addPoint(-shapeWidth/2, shapeHeight/2);
        shape = polygon;
        break;
    }

    Color color = null;
    switch(model.getColor()){
      case SetCardModel.COLOR_RED : color = Color.RED; break;
      case SetCardModel.COLOR_GREEN : color = Color.GREEN; break;
      case SetCardModel.COLOR_BLUE : color = Color.BLUE; break;
    }

    float filled = 0;
    switch(model.getFilled()){
      case SetCardModel.FILLED_NONE : filled = 0; break;
      case SetCardModel.FILLED_HALF : filled = 0.2f; break;
      case SetCardModel.FILLED_FULL : filled = 1.0f; break;
    }

    // draw objects
    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g2D.setStroke(new BasicStroke(3.0f));

    for(int object_nr = 0; object_nr < count; object_nr++){
      int x = card.getWidth() / 2;
      int y = (int) (card.getHeight() / 2 + (object_nr - ((count-1) / 2.)) * (shapeHeight + 20));

      g2D.translate(x, y);

      g2D.setColor(color);

      g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, filled));
      g2D.fill(shape);
      g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
      g2D.draw(shape);

      g2D.translate(-x, -y);
    }
  }
}
