package run;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import controller.ClientThread;
import controller.ClientThreadManager;
import view.ServerView;

public class ServerRun {
	private static ServerView serverView;
	private static final int PORT = 9999;
	private static ClientThreadManager clientThreadManager;

	private static boolean isShutdown;
	private static ServerSocket server;

	public static void main(String[] args) {
		initView();
		try {
			server = new ServerSocket(PORT);
			clientThreadManager = new ClientThreadManager();
			isShutdown = false;
			System.out.println("Server đang chạy ở port 9999...");
			acceptConnections();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initView() {
		serverView = new ServerView();
		serverView.setVisible(true);
	}

	private static void acceptConnections() {
		try (ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 15, 30, TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(3))) {
			while (!isShutdown) {
				try {
					Socket conn = server.accept();
					System.out.println("+ New Client connected: " + conn);
					ClientThread clientThread = new ClientThread(conn);
					clientThreadManager.add(clientThread);
					executor.execute(clientThread);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void removeConnect(ClientThread clientThread) {
		clientThreadManager.remove(clientThread);

	}

	public static void updateOnlineList() {
		ArrayList<String> names = clientThreadManager.getOnlineList();
		if (names == null || names.isEmpty()) {
			serverView.setListModel();
			return; // Nếu danh sách rỗng hoặc null, không cần thực hiện gì cả
		}
		String result = String.join(";", names);

		serverView.setOnlineList(names);

		clientThreadManager.broadcast("ONLINE_LIST;" + result);

	}

	public static ClientThreadManager getClientThreadManager() {
		return clientThreadManager;
	}

	public static void setClientThreadManager(ClientThreadManager clientThreadManager) {
		ServerRun.clientThreadManager = clientThreadManager;
	}

	public static boolean isShutdown() {
		return isShutdown;
	}

	public static void setShutdown(boolean isShutdown) {
		ServerRun.isShutdown = isShutdown;
	}
}