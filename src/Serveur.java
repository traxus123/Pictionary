import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

import javax.swing.JOptionPane;

public class Serveur {

	private static ServerSocket socketserver  ;
	private static Socket socketduserveur ;
	private static BufferedReader in;
	private static PrintWriter out;
	private static int port;

	public static void main(String[] args) throws Exception {
		try {
			port = Integer.parseInt(JOptionPane.showInputDialog(null, "Server Port"));
			socketserver = new ServerSocket(port);
			System.out.println("Le serveur est à l'écoute du port "+socketserver.getLocalPort());
			socketduserveur = socketserver.accept(); 
			System.out.println("User connected");
			out = new PrintWriter(socketduserveur.getOutputStream());
			out.println("User connected");
			out.flush();

			socketduserveur.close();
			socketserver.close();

		}catch (IOException e) {

			e.printStackTrace();
		}
	}
}
