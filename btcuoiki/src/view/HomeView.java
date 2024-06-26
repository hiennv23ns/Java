package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLDocument;
import run.ClientRun;

import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HomeView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String userName;
	private DefaultListModel<String> listModel;
	@SuppressWarnings("rawtypes")
	private JList listOnline;
	private JTextPane textPane;
	@SuppressWarnings("unused")
	private HTMLDocument doc;

	public HomeView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Online users");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel.setBounds(586, 115, 156, 31);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(586, 156, 166, 203);
		contentPane.add(scrollPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 533, 349);
		contentPane.add(scrollPane_1);

		textPane = new JTextPane();
		textPane.setContentType("text/html");
		doc = (HTMLDocument) textPane.getStyledDocument();

		scrollPane_1.setViewportView(textPane);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 388, 533, 65);
		contentPane.add(textArea);

		JButton btnNewButton = new JButton("Gửi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = textArea.getText().trim();
				if (message.isEmpty())
					return;

				boolean isPrivateMessage = false;
				String recipient = null;
				String content = message;

				// Kiểm tra nếu tin nhắn chứa tên người dùng với ký tự '@'
				if (message.contains("@")) {
					int atIndex = message.indexOf("@");
					int spaceIndex = message.indexOf(" ", atIndex);

					// Nếu có khoảng trắng sau '@'
					if (spaceIndex != -1) {
						// Tìm kiếm tên người dùng có thể bao gồm nhiều từ
						for (int i = spaceIndex + 1; i < message.length(); i++) {
							char c = message.charAt(i);
							if (Character.isWhitespace(c) || i == message.length() - 1) {
								String potentialRecipient = message
										.substring(atIndex + 1, i == message.length() - 1 ? i + 1 : i).trim();
								if (listModel.contains(potentialRecipient)) {
									recipient = potentialRecipient;
									content = message.substring(i == message.length() - 1 ? i + 1 : i).trim();
									isPrivateMessage = true;
									break;
								}
							}
						}
					} else {
						// Nếu không có khoảng trắng sau '@', xem toàn bộ phần sau '@' là tên người dùng
						String potentialRecipient = message.substring(atIndex + 1).trim();
						if (listModel.contains(potentialRecipient)) {
							recipient = potentialRecipient;
							content = "";
							isPrivateMessage = true;
						}
					}
				}

				if (isPrivateMessage) {
					ClientRun.getSocketController().sendPrivateMessage(getUserName(), recipient, content);
				} else {
					ClientRun.getSocketController().sendMess(getUserName(), content);
				}

				textArea.setText("");
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 15));
		btnNewButton.setBounds(594, 390, 158, 63);
		btnNewButton.setDefaultCapable(true);
		contentPane.add(btnNewButton);

		listModel = new DefaultListModel<>();
		listModel.addElement("Không có ai ở trong server");
		listOnline = new JList<>(listModel);

		scrollPane.setViewportView(listOnline);

		String initialContent = "<html><head><style>" + "* {padding: 0; margin: 0; box-sizing: border-box;}"
				+ "body {background-color: #242526; color: white; font-family: Arial, sans-serif; margin: 0; padding: 1px;}"
				+ ".self {text-align: right; color: white;background-color:#0084FE}"
				+ ".other {text-align: left; color: white;background-color: gray;}"
				+ ".mess {display: flex;gap: 15px;align-items: center;}"
				+ "img {max-width: 28px;height: 28px;object-fit: cover;object-position: center;}"
				+ "span {max-width: 50%;padding: 8px 12px;border-radius: 20px;display: inline-block; word-wrap: break-word;word-break: break-word;}"
				+ "</style></head><body></body></html>";
		textPane.setText(initialContent);

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
		setTitle(userName);
	}

	public void setOnlineList(ArrayList<String> onlines) {

		if (onlines == null) {
			listModel.clear();
			listModel.addElement("Không có ai ở trong server");
			return;
		}
		listModel.clear();
		for (String user : onlines) {
			if (onlines.size() == 1) {
				listModel.clear();
				listModel.addElement("Không có ai ở trong server");
			} else {
				if (!getUserName().equals(user)) {
					listModel.addElement(user);
				}
			}
		}
	}

	public void updateTextPane(String username, String mess) {
		String userClass = username.equals(getUserName()) ? "self" : "other";
		String formattedMessage;
		if (userClass.equals("self")) {
			formattedMessage = String.format("<div class='%s'> %s:<span>%s</span></div>", userClass, "You", mess);

		} else {
			formattedMessage = String.format("<div class='%s'>%s: <span>%s</span></div>", userClass, username, mess);

		}

		String currentContent = textPane.getText();
		int bodyEndIndex = currentContent.lastIndexOf("</body>");
		String updatedContent = currentContent.substring(0, bodyEndIndex) + formattedMessage + "</body></html>";

		textPane.setText(updatedContent);
	}

}