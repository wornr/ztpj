package pl.zut.edu.ztpj;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import pl.zut.edu.ztpj.config.DBMS;
import pl.zut.edu.ztpj.db.dto.Employee;

public class Daemon extends Thread {
	private static final int DEFAULT_DAEMON_PORT = 8080;
	private static final int MAX_DAEMON_PORT = 65535;
	private static final int ACCEPT_TIMEOUT = 1000;
	
	private int daemonPort;
	private boolean running;
	
	public Daemon() {
		super();
		configureProxy(DEFAULT_DAEMON_PORT);
	}
	
	public Daemon(int port) {
		this();
		if (port < MAX_DAEMON_PORT)
			configureProxy(port);
	}
	
	public void configureProxy(int daemonPort) {
		this.daemonPort = daemonPort;
	}
	
	public void run() {
		running = true;
		ServerSocket mainSocket = null;
		
		try {
			mainSocket = new ServerSocket(daemonPort);
			mainSocket.setSoTimeout(ACCEPT_TIMEOUT);
			
			Socket clientSocket = null;
			while (running) {
				try {
					clientSocket = mainSocket.accept();
					
					if (running) {
						ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
						
						List<Employee> employees = new ArrayList<>();
						employees.addAll(DBMS.getInstance().getDao().getDaoDirector().getDirectors());
						employees.addAll(DBMS.getInstance().getDao().getDaoTrader().getTraders());
						
						out.writeObject(employees);
					} else {
						try {
							clientSocket.close();
						} catch (IOException e) {}
					}
				} catch (InterruptedIOException e) {}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (mainSocket != null)
					mainSocket.close();
			} catch (Exception exc) {}
		}
	}
	
	public void stopServer() {
		running = false;
	}
}
