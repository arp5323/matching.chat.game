import java.awt.Dimension;
import javax.swing.JFrame;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Anthony Pardini, Jaycob Zasowski, Jathan  Anadham
 * @group group number 15
 * @version 5/17/17
 * Game class Creates instance of game through the use of timers. Uses Board and Card class to run
**/ 

public class Game extends JFrame implements ActionListener
 
 {

   private JButton start = new JButton("Start");
   private JPanel layout = new JPanel(new GridLayout(0,1));
   private JLabel instructions1 = new JLabel("Instructions: This game is very simple"); 
   private JLabel instructions2 = new JLabel("To play you click 1 button and it will show a number");
   private JLabel instructions3 = new JLabel("then you pick another one, if they match it will disable the button");
   private JLabel instructions4 = new JLabel("the person with the most matches after 30 seconds wins");
   private JLabel instructions5 = new JLabel("Push the start button to begin!");
   
   public static void main(String[] args)
   {
      new Game();        
   }
   
   /**
   * Creates graphical user interface and adds JLabels to our JPanel 
   **/ 
   public Game()
   {
      start.addActionListener(this);
      add(start, BorderLayout.SOUTH);

      layout.add(instructions1);
      layout.add(instructions2);
      layout.add(instructions3);
      layout.add(instructions4);
      layout.add(instructions5);
      
      add(layout, BorderLayout.NORTH);
      
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      pack();
      setLocationRelativeTo(null);
   }
   
   public void actionPerformed(ActionEvent ae)
   {
      JPanel me = new JPanel();
      Object lock = new Object();
       
      Board b = new Board();
      b.setTitle("Memory Game - Player One");     
      Timer timer = new Timer();
      synchronized(lock)
      {
      timer.schedule(
         new TimerTask(){
            public void run()
            {
               b.setVisible(true);
               b.setLocationRelativeTo(null);
            }
         }, 2500);
      }
    
      synchronized(lock)
      {
      timer.schedule(
         new TimerTask(){
            public void run()
            {
               b.setVisible(false);
               System.out.println(b.getScore());
            }
         }, 30000);
      }

   }   
}