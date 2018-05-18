package drawn;

import java.net.*;
import java.io.*;

public class test extends Thread
{
	final static int port = 2000;
	private Socket socket;

	public static void main(String[] args)
	{
		int compteur = 0;
		try
		{
			ServerSocket socketServeur = new ServerSocket(port); //ajout 2 en argument socket pour la taille max de la file
			System.out.println("Lancement du serveur");
			
			while (true)
			{
				Socket socketClient = socketServeur.accept();
				compteur++;
				test s = new test(socketClient);
				if(compteur == 2)
				{
					s.stop();
					//choix aléatoire du mot
					//envoie des clients
				}
				else if (compteur >=0 && compteur <2)
				{
				s.start();
				}
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public test(Socket socket)
	{
		this.socket = socket;
	}
	
	public void run()
	{
		traitements();
	}
	
	public void traitements()
	{
		try
		{
			String message = "";
			System.out.println("Connexion avec le client : " + socket.getInetAddress());
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream out = new PrintStream(socket.getOutputStream());
			message = in.readLine();
			out.println("Bonjour " + message);
			socket.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}