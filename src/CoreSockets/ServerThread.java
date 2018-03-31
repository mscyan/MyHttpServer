package CoreSockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable
{
	int port;
	public ServerSocket server;
	public ServerThread(int port) throws Exception
	{
		this.port = port;
		server = new ServerSocket(port);
	}

	public void run()
	{
		try
		{
			//获得连接并开启新的线程用于捕获请求
			Socket client = server.accept();
			new Thread(this).start();

			//获取流
			InputStreamReader inReader = new InputStreamReader(client.getInputStream());

			StringBuilder msg = new StringBuilder();
			int len = -1;
			char[] bytes = new char[2048];
			try
			{
				do{
					if((len=inReader.read(bytes))!=-1)
					{
						msg.append(bytes,0,len);
					}
				}
				while (inReader.ready());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			java.lang.String smsg = msg.toString();
			java.lang.String sm = smsg.substring(smsg.indexOf("/")+1,smsg.indexOf("HTTP/")).trim();

			try
			{
				File fil = new File(this.getClass().getResource("/").toString());
				String path = fil.getParentFile().getPath()+"\\webapps\\pages\\"+sm;
				path = path.replace("file:\\","");
				System.out.println(path);
				File file = new File(path);
				System.out.println(file.exists());

				PrintStream out = new PrintStream(client.getOutputStream());
				out.println("HTTP/1.1 200 OK");
				out.println("Content-Type:text/html;charset:UTF-8");
				out.println();
				FileReader fr = new FileReader(file);
				char[] bs = new char[1024];
				int le = 0;
				while((le = fr.read(bs))!=-1)
				{
					out.print(new String(bs,0,le));
				}
				fr.close();
				out.flush();
				out.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			inReader.close();
			client.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}