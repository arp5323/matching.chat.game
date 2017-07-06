import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author Anthony Pardini, Jaycob Zasowski, Jathan  Anadham
 * @group group number 15
 * @version 5/17/17
 * Board class is used to implement to whole memory matching game and contains all the functions used 
**/ 

public class Board extends JFrame{

   private Icon rit = new ImageIcon("rit.jpg");
   private List<Card> cards;
   private Card selectedCard;
   private Card c1;
   private Card c2;
   private Timer t;
   int count = 0;
   JTextField matchesFound;

   /**
   * Constructor Uses lists of Card class objects and integers to create layout of Cards with matching pairs
   * Creates graphical user interface for the game’s board using Cards as its layout
   **/ 
   public Board(){
   
      int pairs = 10;
      List<Card> cardsList = new ArrayList<Card>();
      List<Integer> cardVals = new ArrayList<Integer>();
   
      for (int i = 0; i < pairs; i++){
         cardVals.add(i);              // adds two groups of 10 matching squares
         cardVals.add(i);
      }
      Collections.shuffle(cardVals);
   
      for (int val : cardVals){
         Card c = new Card();
         c.setId(val);
         c.addActionListener(
            new ActionListener(){
               public void actionPerformed(ActionEvent ae){
                  selectedCard = c;
                  doTurn();
               }
            });
         cardsList.add(c);
      }
      this.cards = cardsList;
      //set up the timer
      t = new Timer(750, 
      //Checks to see if cards match
         new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               checkCards();
            }
         });
   
      t.setRepeats(false);
      
      //New panel
      JPanel textP = new JPanel();
      JTextField jtf = new JTextField("Which player can match the most in 30 Seconds?");  
      jtf.setEnabled(false);
      textP.add(jtf);
      add(textP,BorderLayout.NORTH);
   
      //set up the board itself
      JPanel main = new JPanel();

      main.setLayout(new GridLayout(4, 5));
      add(main,BorderLayout.CENTER);
      for (Card c : cards)
      {
         main.add(c);
         c.setForeground(Color.ORANGE);              //STYLING THE JBUTTONS
         c.setBackground(new Color(122, 111, 82));         
         c.setFont(new Font("Tahoma",Font.PLAIN, 25));
      }
         
      JPanel text = new JPanel();
      add(text,BorderLayout.SOUTH);
      matchesFound = new JTextField(10);
      matchesFound.setEnabled(false);
      
      text.add(matchesFound);
      
      setPreferredSize(new Dimension(600,600)); //need to use this instead of setSize
      pack();
      setLocationRelativeTo(null);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);
   }

   /**
   * Acts as a player’s “turn” by returning Card values 
   **/ 
   public void doTurn(){
      if (c1 == null && c2 == null){
         c1 = selectedCard;
         c1.setText(String.valueOf(c1.getId()));
      }
   
      if (c1 != null && c1 != selectedCard && c2 == null){
         c2 = selectedCard;
         c2.setText(String.valueOf(c2.getId()));
         t.start();
      
      }
   }
   
   /**
   * Verifies the player’s matching card values
   **/
   public void checkCards(){
      if (c1.getId() == c2.getId())
      {//match condition
         c1.setEnabled(false); //disables the button
         c2.setEnabled(false);
         c1.setMatched(true); //flags the button as having been matched
         c2.setMatched(true);
         c1.setBackground(new Color(22, 12, 82));
         c2.setBackground(new Color(22, 12, 82));
         
         count++;
         matchesFound.setText("Matches: " + String.valueOf(count));
         if (this.isGameWon()){
            JOptionPane.showMessageDialog(this, "You matched them all!");
         }
      }
      
      else{
         c1.setText(""); //'hides' text
         c2.setText("");
      }
      c1 = null; //reset c1 and c2
      c2 = null;
   }
   
   /**
   * Determines whether or not matches were found within a turn
   **/
   public boolean isGameWon(){
      for(Card c: this.cards){
         if (c.getMatched() == false){
            return false;
         }
      }
      return true;
   }
   
   /**
   * Returns the count of matches found
   **/
   public int getScore()   {
   
      return count;
   
   }

}