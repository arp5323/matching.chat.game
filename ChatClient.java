import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;

/**
 * @author Anthony Pardini, Jaycob Zasowski, Jathan  Anadham
 * @group group number 15
 * @version 5/17/17
 * Final Project Chat Client connects to the Chat Server in order to play a game
**/ 

public class ChatClient extends JFrame {

   private Socket sock = null;
   Object lock = new Object();  
   private OutputStream ops = null;
   private PrintWriter pw = null;
      
   private JMenuBar jmBar;
   private JMenu jmFile;
   private JMenuItem jmiExit;
   private JPanel jpChat;
   private JPanel jpNorthControl;
   private JPanel jpSouthControl;
   private JButton jbSend;
   private JButton jbName;
   private JButton jbConnect;
   private JButton jbStart;
   private JTextArea serverText;
   private JTextArea clientText;
   private JTextArea scoreText;
   private JLabel message;
   private JLabel about;
   private final int PORT = 16789;
   private final String USER = "localhost";
   private String clientName;
   ArrayList<Integer> al = new ArrayList<Integer>();
   
   public static void main(String[] args) {
   
      new ChatClient();
   
   }
   
   /**
   * Constructor for the Chat Client that creates the GUI 
   **/ 
   public ChatClient()  {
   
      Font font = new Font("Arial", Font.BOLD, 14);
      
      jmBar = new JMenuBar();
      jmFile = new JMenu("File");
      jmiExit = new JMenuItem("Exit");
      
      jmBar.add(jmFile);
      jmFile.add(jmiExit);
      setJMenuBar(jmBar);
      
      jpNorthControl = new JPanel();
      add(jpNorthControl, BorderLayout.NORTH);
      jbName = new JButton("Enter a username");
      jbConnect = new JButton("Connect");
      about = new JLabel("Please Enter a Username and Connect");
      about.setFont(font);
      jpNorthControl.add(about);
      jpNorthControl.add(jbName);
      jpNorthControl.add(jbConnect);
      jpNorthControl.setBackground(Color.ORANGE);
      
      
      jpChat = new JPanel();
      jpChat.setLayout(new BorderLayout());
      add(jpChat, BorderLayout.CENTER);
      jpChat.setBackground(Color.ORANGE);
      
      serverText = new JTextArea(15,25);
      
      serverText.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
      
      serverText.setFont(font);
      serverText.setForeground(Color.RED);

      serverText.setEnabled(false);
      jpChat.add(new JScrollPane(serverText), BorderLayout.NORTH);
      
      clientText = new JTextArea(3,15);
      clientText.setBorder(BorderFactory.createLineBorder(Color.BLUE));
      serverText.setFont(font);
      
      jpChat.add(clientText,BorderLayout.SOUTH);
      
      jbSend = new JButton("Send Message");
      jpChat.add(jbSend,BorderLayout.EAST);
      clientText.setForeground(Color.RED);   //
      jbSend.setMnemonic(KeyEvent.VK_ENTER);
      this.getRootPane().setDefaultButton(jbSend);

      
      message = new JLabel("Enter your message below");
      message.setFont(font);
      jpChat.add(message,BorderLayout.WEST);
      
      jpSouthControl = new JPanel();
      add(jpSouthControl, BorderLayout.SOUTH);
      
      jbStart = new JButton("Start game");
      jpSouthControl.add(jbStart);
      
      scoreText = new JTextArea(2,5);
      scoreText.setBorder(BorderFactory.createLineBorder(Color.BLUE));
      scoreText.setMargin( new Insets(10,10,10,10) );
      scoreText.setFont(font);
      jpSouthControl.add(scoreText,BorderLayout.EAST);
      jpSouthControl.setBackground(Color.ORANGE);   
      
      setSize(600,520);
      setTitle("ChatClient -- Please Enter Username -- ");
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      jbStart.addActionListener   (
         new ActionListener() {
            public void actionPerformed(ActionEvent ae)  {
            
               Board br = new Board();
                              
               Timer timer = new Timer();
               synchronized(lock)
               {
                  timer.schedule(
                  new TimerTask(){
                     public void run()
                     {
                        
                        br.setVisible(false);
                        System.out.println(br.getScore());
                        al.add(br.getScore());
                        pw.println("      " + clientName + " Scored: " + br.getScore() );
                        pw.println("      " + clientName + " Highest score is: " + Collections.max(al));
                        pw.flush();
                        scoreText.setText(" Your highest score " + "\n" + " =  " + Collections.max(al) );
                        System.out.println(al);
                        int i = Collections.max(al);
                        System.out.println("Your highest score: " + i);
                     }
                  },18000);
               }

               
               
               
               
            }
         }
      );

      
      jbName.addActionListener   (
         new ActionListener() {
            public void actionPerformed(ActionEvent ae)  {
            
               if(clientName == null)  {
                  
                  clientName = JOptionPane.showInputDialog("Please Enter a Username");
               }
            }
         }
      );
      
      jbSend.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent ae)  {
                           
            if(clientName == null)  {
                              
               clientName = JOptionPane.showInputDialog("Please Enter a Username");
            }
            if(clientText.getText().length() > 0)  {
                           
               String message = clientText.getText();
               pw.println(clientName + " says: " + message);
               pw.flush();
               clientText.setText("");
            }                        
                           
         }
      }
      );
      
      jbConnect.addActionListener   (
         new ActionListener()   {
            public void actionPerformed(ActionEvent ae)  {
            
               try   {
               
                  sock = new Socket(USER, PORT);
                  ops = sock.getOutputStream();
                  pw = new PrintWriter(ops);
                  ThreadedClient tc = new ThreadedClient(sock);
                  tc.start();
                  
               }
               catch (ConnectException ce)   {
               
                  clientText.setText("The server is not active");
                  Font font = new Font("Arial", Font.BOLD, 14);
                  clientText.setFont(font);
                  clientText.setForeground(Color.RED);
               }
               catch (UnknownHostException uhe) {
               
                  clientText.setText("No host available");
               }
               catch (IOException ioe) {
               
               }
                                   
            }
         
         }
      );  
   }
   
   /**
   * Class used to create the ThreadedClient  
   **/ 
   class ThreadedClient extends Thread {
   
      InputStream is = null;
      BufferedReader br = null;
      Socket sock = null;
      
      public ThreadedClient(Socket _sock) {
      
         sock = _sock;
      }
      
      public void run() {
      
         try   {
         
            is = sock.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            
            while(true) {
            
               String message;
               message = br.readLine();
               serverText.append(message + "\n");
            }
         }
         catch (ConnectException ce)   {
         
         }
         catch (UnknownHostException uhe )   {
         
         }
         catch (IOException ioe) {
         
         }
      }
   
   
   }

      
}