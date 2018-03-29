package CoreSockets;

import com.sun.deploy.net.protocol.ProtocolType;
import com.sun.security.ntlm.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolFamily;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerThread implements Runnable
{
	int port;
	public ServerSocket server;
	public ServerThread(int port) throws Exception
	{
		System.out.println("新开启一个线程");
		this.port = port;
		server = new ServerSocket(port);
	}

	public void run()
	{
		try
		{
			Socket client = server.accept();

			new Thread(this).start();
			System.out.println(this+"--"+Thread.currentThread());

			InputStream iStream = client.getInputStream();
			byte[] bytes = new byte[1024];
			int len;
			StringBuilder sb = new StringBuilder();
			while((len = iStream.read(bytes))!=-1)
			{
				sb.append(new String(bytes,0,len,"UTF-8"));
			}
			client.shutdownInput();

			OutputStream ostream = client.getOutputStream();

			System.out.println(ostream);
			System.out.println("************************");

			String httpgram = "HTTP/1.1 200 OK\n" +
					"\n" +
					" \n" +
					"\n" +
					"Server:Apache Tomcat/5.0.12\n" +
					"\n" +
					"Date:Mon,6Oct2003 13:23:42 GMT\n" +
					"\n" +
					"Content-Length:112";

			System.out.println("send msg to client");
			ostream.write(httpgram.getBytes("UTF-8"));

			ostream.flush();
			ostream.close();

			iStream.close();

			client.close();
			System.out.println("Server:"+new Date().toString()+" - \n"+client.getInetAddress()+" - \n"+sb.toString());
			System.out.println();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


	}
}