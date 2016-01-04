package de.endrullis.setTrainer;

import java.util.ArrayList;

/**
 * Model of a Set card.
 */
public class SetCardModel {
  public static final int COUNT_1                 = 0;
  public static final int COUNT_2                 = 1;
  public static final int COUNT_3                 = 2;

  public static final int SHAPE_RECTANGLE         = 0;
  public static final int SHAPE_ELLIPSE           = 1;
  public static final int SHAPE_TRIANGLE          = 2;

  public static final int COLOR_RED               = 0;
  public static final int COLOR_GREEN             = 1;
  public static final int COLOR_BLUE              = 2;

  public static final int FILLED_NONE             = 0;
  public static final int FILLED_HALF             = 1;
  public static final int FILLED_FULL             = 2;

  private int count;
  private int shape;
  private int color;
  private int filled;

  public SetCardModel(int count, int shape, int color, int filled){
    this.count = count;
    this.shape = shape;
    this.color = color;
    this.filled = filled;
  }

  public int getCount() {
    return count;
  }

  public int getShape() {
    return shape;
  }

  public int getColor() {
    return color;
  }

  public int getFilled() {
    return filled;
  }

  /**
   * Creates all set cards.
   *
   * @return ArrayList of SetCardModel
   */
  public static ArrayList getSetCards(){
    ArrayList setCards = new ArrayList();

    for(int count_nr = 0; count_nr < 3; count_nr++){
      for(int shape_nr = 0; shape_nr < 3; shape_nr++){
        for(int color_nr = 0; color_nr < 3; color_nr++){
          for(int filled_nr = 0; filled_nr < 3; filled_nr++){
            setCards.add(new SetCardModel(count_nr, shape_nr, color_nr, filled_nr));
          }
        }
      }
    }

    return setCards;
  }
}
