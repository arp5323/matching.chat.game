import java.net.*;
import java.io.*;
import java.util.*;

/**
 * @author Anthony Pardini, Jaycob Zasowski, Jathan  Anadham
 * @group group number 15
 * @version 5/17/17
 * Final Project ChatServer used by clients connected
**/ 

public class ChatServer {

   Vector<PrintWriter> printClients = new Vector<PrintWriter>();
   private final int PORT = 16789;

   public static void main(String[] args) {
   
      new ChatServer();
   }
   
   /**
   * Constructor for the Chat Server creates the server socker and a new ThreadedServer
   **/ 
   public ChatServer()  {
   
      ServerSocket ss = null;
      
      try {
      
         ss = new ServerSocket(PORT);
         Socket sock = null;
         while(true) {
         
            sock = ss.accept();
            ThreadedServer ts = new ThreadedServer(sock);
            ts.start();         
         }
      
      }
      catch(BindException be) {
      
      }
      catch(IOException ioe) {
      
      }
   
   
   }
   
   /**
   * Class that handles the ThreadedServer
   **/ 
   class ThreadedServer extends Thread {
   
      Socket sock;
      
      public ThreadedServer(Socket _sock) {
      
         sock = _sock;
      }
      
      public void run() {
      
         BufferedReader br;
         PrintWriter pw;
         String message;
         
         try   {
         
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            
            pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));;
            printClients.add(pw);
            int size = printClients.size();
            pw.println("Welcome to the chat server");
            pw.println( size + " Person to Connect");
            pw.println();
            pw.flush();
            
            while((message = br.readLine()) != null)  {
               
               for(PrintWriter pwc: printClients)   {
                  
                  System.out.println(message);
                  pwc.println(message);
                  pwc.flush();
               }
            }
         
         
         }
         catch (SocketException se) {
            
            
         }
         catch (IOException ioe) {
         
         }
      }
   }
}