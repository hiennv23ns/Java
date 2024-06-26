package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import dao.UserDAO;
import model.UserModel;
import run.ServerRun;

public class ClientThread implements Runnable {
	private Socket socket;
	private DataInputStream dIn;
	private DataOutputStream dOut;
	private UserModel userModel;

	public ClientThread(Socket socket) throws IOException {
		this.socket = socket;
		this.dIn = new DataInputStream(socket.getInputStream());
		this.dOut = new DataOutputStream(socket.getOutputStream());

	}

	@Override
	public void run() {
		String request;
		while (!ServerRun.isShutdown()) {
			try {
				System.out.println("Đang chờ client request");
				request = dIn.readUTF();
				System.out.println("Receive request from client: " + request);
				String type = request.split(";")[0];
				switch (type) {
				case "LOGIN":

					handleLogin(request);
					break;
				case "CHAT_ALL":
					handleMess(request);
					break;
				default:
					break;
				}
			} catch (Exception e) {
				System.err.println("Kết nối bị tắt đột ngột");
				break;
			}
		}

		// mat ket noi
		try {
			socket.close();
			dIn.close();
			dOut.close();
			ServerRun.removeConnect(this);
			ServerRun.updateOnlineList();
			System.out.println("Client disconnected: " + socket);
		} catch (IOException ex) {
			System.err.println("Mất ket noi: " + ex.getMessage());
		}

	}
	private void handleMess(String request) {
		ServerRun.getClientThreadManager().broadcast(request);
	}

	private void handleLogin(String request) {
		String[] splitted = request.split(";");
		String username = splitted[1];
		String password = splitted[2];

		boolean isLogedIn = ServerRun.getClientThreadManager().isLogedIn(username);
		if (isLogedIn) {
			sendDataToClient("LOGIN;failed;Tài khoản đã được đăng nhập ở nơi khác");
		} else {
			UserDAO userDao = new UserDAO();
			UserModel newUserLogin = new UserModel(username, password);
			UserModel loggedInUser = userDao.login(newUserLogin);

			// Nếu tài khoản đúng
			if (loggedInUser != null) {
				setUserModel(loggedInUser);
				sendDataToClient("LOGIN;success;" + loggedInUser.getFullName());
				ServerRun.updateOnlineList();

			} else {
				sendDataToClient("LOGIN;failed;Sai tài khoản hoặc mật khẩu");
			}
		}
	}

	public void sendDataToClient(String format) {
		try {
			dOut.writeUTF(format);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

}