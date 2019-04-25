package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Connection extends Thread{
	protected Socket socket;
	protected BufferedReader reader;
	protected PrintWriter writer; 
	private MessagesListener messagesListener;
	private boolean isFromClient;
	public Connection(MessagesListener listener,boolean isFromClient)
	{
		this.isFromClient = isFromClient;
		this.messagesListener = listener;
	}
	public void writeLine(String line)
	{
		if(writer != null)
		{
			System.out.println("writing " + line + " ...");
			writer.println(line);
			writer.flush();
		}
	}
	public void startReading()
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(socket);
				if(reader !=  null)
				{
					String line = null;
					try {
						while( (line = reader.readLine()) != null)
						{
							System.out.println("reading " + line + " ...");
							messagesListener.onMessageRecived(line, isFromClient);
						}
					} catch (IOException e) {
						System.out.println(Connection.this.getClass().getSimpleName());
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	public Socket getSocket() {
		return socket;
	}
	
	
}
