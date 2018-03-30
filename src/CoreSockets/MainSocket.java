package CoreSockets;

import java.net.ServerSocket;

public class MainSocket
{
	public static void main(String[] args) throws Exception
	{
		ServerThread serverThread = new ServerThread(8080);
		System.out.println("服务器开启");
		System.out.println();
		System.out.println();
		new Thread(serverThread).start();



	}
}
