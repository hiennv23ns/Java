package run;

import java.util.ArrayList;

import controller.SocketController;
import view.HomeView;
import view.LoginView;

public class ClientRun {
	private static SocketController socketController;
	private static LoginView loginView;
	private static HomeView homeView;

	public enum ViewName {
		LOGIN_VIEW, HOME_VIEW
	}

	public static void main(String[] args) {
		new ClientRun();
	}

	public ClientRun() {
		socketController = new SocketController();
		loginView = new LoginView();
		homeView = new HomeView();
		openView(ViewName.LOGIN_VIEW);

	}

	public static void openView(ViewName viewName) {
		if (viewName != null) {
			switch (viewName) {
			case LOGIN_VIEW:
				loginView.setVisible(true);
				break;
			case HOME_VIEW:
				homeView.setVisible(true);

			default:
				break;
			}
		}
	}

	public static void closeView(ViewName viewName) {
		if (viewName != null) {
			switch (viewName) {
			case LOGIN_VIEW:
				loginView.dispose();
				break;
			case HOME_VIEW:
				homeView.dispose();
				break;

			default:
				break;
			}
		}
	}

	public static SocketController getSocketController() {
		return socketController;
	}

	public static void setSocketController(SocketController socketController) {
		ClientRun.socketController = socketController;
	}

	public static LoginView getLoginView() {
		return loginView;
	}

	public static void setLoginView(LoginView loginView) {
		ClientRun.loginView = loginView;
	}

	public static HomeView getHomeView() {
		return homeView;
	}

	public static void setHomeView(HomeView homeView) {
		ClientRun.homeView = homeView;
	}

	public static void updateOnlineList(ArrayList<String> names) {
		if(names==null)
			return;
		homeView.setOnlineList(names);
	}
	public static void textPane(String username,String mess) {
		homeView.updateTextPane(username,mess);
	}
}