package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(8080);
			System.out.println("Start-----");
			Socket socket = server.accept();
			InputStream in = socket.getInputStream();
			
			BufferedReader is = new BufferedReader(new InputStreamReader(in));
			
			String line = is.readLine();
			System.out.println("received from client:" + line);
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.println("received data:" + line);
			pw.flush();
			pw.close();
			is.close();
			socket.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
