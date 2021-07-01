package ui;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import service.ReceiveMail;
import service.SendMail;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Send extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private String path = null;

	/**
	 * Create the frame.
	 */
	public Send() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("�������˻�");
		lblNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 94, 79, 21);
		panel_1.add(lblNewLabel);
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 10, 606, 74);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("��ӭ�����ʼ�С����~");
		lblNewLabel_1.setFont(new Font("΢���ź� Light", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(0, 0, 208, 27);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("�����Ƿ��ʼ�����");
		lblNewLabel_2.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(228, 43, 170, 31);
		panel_2.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("smtp��Ȩ��");
		lblNewLabel_3.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(10, 137, 93, 23);
		panel_1.add(lblNewLabel_3);
		

		
		JLabel lblNewLabel_4 = new JLabel("����");
		lblNewLabel_4.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(10, 183, 93, 15);
		panel_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("@163.com");
		lblNewLabel_5.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		lblNewLabel_5.setBounds(223, 97, 79, 15);
		panel_1.add(lblNewLabel_5);
		
		textField = new JTextField();
		textField.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		textField.setBounds(98, 96, 115, 21);
		panel_1.add(textField);
		textField.setColumns(30);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		textField_1.setBounds(98, 140, 115, 21);
		panel_1.add(textField_1);
		textField_1.setColumns(30);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		textField_2.setBounds(98, 182, 115, 21);
		panel_1.add(textField_2);
		textField_2.setColumns(30);
		
		JButton btnNewButton_1 = new JButton("...");
		btnNewButton_1.setBounds(223, 181, 28, 23);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					int result = 0;					
					JFileChooser fileChooser = new JFileChooser();
					FileSystemView fsv = FileSystemView.getFileSystemView();  //ע���ˣ�������Ҫ��һ��             //�õ�����·��
					fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
					fileChooser.setDialogTitle("��ѡ��Ҫ���͵ĸ���...");
					fileChooser.setApproveButtonText("ȷ��");
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					result = fileChooser.showOpenDialog(contentPane);
					if (JFileChooser.APPROVE_OPTION == result) {
					    	   path=fileChooser.getSelectedFile().getPath();
					    	   System.out.println(path);
					    	   textField_2.setText(path);					    	   
					   }
				} catch (Exception e1) {
					e1.printStackTrace();					
				}
			}
		});
		panel_1.add(btnNewButton_1);
		
		JLabel lblNewLabel_6 = new JLabel("�ʼ�����");
		lblNewLabel_6.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		lblNewLabel_6.setBounds(10, 230, 79, 15);
		panel_1.add(lblNewLabel_6);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		textField_3.setBounds(98, 229, 115, 21);
		panel_1.add(textField_3);
		textField_3.setColumns(30);
		
		JLabel lblNewLabel_7 = new JLabel("���ʼ���ַ");
		lblNewLabel_7.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		lblNewLabel_7.setBounds(10, 272, 79, 15);
		panel_1.add(lblNewLabel_7);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		textField_4.setBounds(98, 271, 115, 21);
		panel_1.add(textField_4);
		textField_4.setColumns(30);
		
		JLabel lblNewLabel_8 = new JLabel("�ʼ�����");
		lblNewLabel_8.setFont(new Font("΢���ź�", Font.BOLD, 15));
		lblNewLabel_8.setBounds(450, 99, 79, 15);
		panel_1.add(lblNewLabel_8);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(331, 121, 285, 166);
		panel_1.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("����", Font.PLAIN, 12));
		scrollPane.setViewportView(textArea);
		
		JButton btnNewButton_2 = new JButton("ȷ��");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					new SendMail(textField.getText(),textField_1.getText(),textField_4.getText(),path,textField_3.getText(),textArea.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnNewButton_2);
		
		JButton btnNewButton = new JButton("�˳�");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					System.exit(0);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnNewButton);
	}
}

