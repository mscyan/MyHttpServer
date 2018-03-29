package CoreSockets;

import java.net.ServerSocket;

public class MainSocket
{
	public static void main(String[] args) throws Exception
	{
		ServerThread serverThread = new ServerThread(8080);

		new Thread(serverThread).start();



	}
}
