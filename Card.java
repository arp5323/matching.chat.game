import javax.swing.*;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author Anthony Pardini, Jaycob Zasowski, Jathan  Anadham
 * @group group number 15
 * @version 5/17/17
 * Crad is used to match variables being true or false for matched numbers 
**/ 

public class Card extends JButton{

   private int id;
   private boolean matched = false;
   private Icon rit = new ImageIcon("rit.jpg");

   /**
   * Assigns the integer ID of a Card
   **/ 
   public void setId(int id){
      this.id = id;
    }
    
   /**
   * Accesses the integer ID of a Card 
   **/ 
   public int getId(){
        return this.id;
    }

   /**
   * Assigns the boolean value for if the match exists 
   **/ 
   public void setMatched(boolean matched){
      this.matched = matched;
   }

   /**
   * Accesses the boolean value for if the match exists 
   **/ 
   public boolean getMatched(){
      return this.matched;
   }
}

