package view;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JScrollPane;

public class ServerView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JList<String> listOnline;
    private JScrollPane scrollPane;
    private DefaultListModel<String> listModel;

    public ServerView() {
        setTitle("ServerView");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Server đang chạy");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(0, 10, 576, 29);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Danh sách online");
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(352, 59, 194, 24);
        contentPane.add(lblNewLabel_1);
        
        scrollPane = new JScrollPane();
        scrollPane.setBounds(320, 93, 256, 260);
        contentPane.add(scrollPane);
        
        listModel = new DefaultListModel<>();
        listModel.addElement("Không có ai ở trong server");
        listOnline = new JList<>(listModel);
        
        scrollPane.setViewportView(listOnline);
    }

    public void setOnlineList(ArrayList<String> onlines) {
    	if(onlines == null||onlines.isEmpty())
    	{
    		listModel.clear();
    		listModel.addElement("Không có ai ở trong server");
    		return;
    	}
        listModel.clear();
        for (String user : onlines) {
        	if(!listModel.contains(user))
            listModel.addElement(user);
        }
    }
    public void setListModel() {
    	listModel.clear();
    	listModel.addElement("Không có ai ở trong server");
    	
    }
}