package ui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import service.ReceiveMail;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Receive extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private String path = null;
	/**
	 * Create the frame.
	 */
	public Receive() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("�����˻�");
		lblNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		lblNewLabel.setBounds(108, 113, 79, 21);
		panel_1.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(217, 115, 101, 21);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 10, 426, 74);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("��ӭ�����ʼ�С����~");
		lblNewLabel_1.setFont(new Font("΢���ź� Light", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(0, 0, 208, 27);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("���������ʼ�����");
		lblNewLabel_2.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(129, 43, 170, 31);
		panel_2.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("pop3��Ȩ��");
		lblNewLabel_3.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(108, 143, 108, 23);
		panel_1.add(lblNewLabel_3);
		
		textField_1 = new JTextField();
		textField_1.setBounds(217, 146, 101, 21);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("�ʼ������ַ");
		lblNewLabel_4.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(108, 178, 93, 15);
		panel_1.add(lblNewLabel_4);
		
		textField_2 = new JTextField();
		textField_2.setBounds(217, 177, 101, 21);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("...");
		btnNewButton_1.setBounds(328, 176, 24, 23);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					int result = 0;
					
					JFileChooser fileChooser = new JFileChooser();
					FileSystemView fsv = FileSystemView.getFileSystemView();  //ע���ˣ�������Ҫ��һ��
					//System.out.println(fsv.getHomeDirectory());                //�õ�����·��
					fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
					fileChooser.setDialogTitle("��ѡ�񸽼�Ĭ�ϴ洢λ��");
					fileChooser.setApproveButtonText("ȷ��");
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					result = fileChooser.showOpenDialog(contentPane);
					if (JFileChooser.APPROVE_OPTION == result) {
					    	   path=fileChooser.getSelectedFile().getPath()+"\\";
					    	   textField_2.setText(path);
					    	   System.out.println(path);
					   }					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel_1.add(btnNewButton_1);
		
		JLabel lblNewLabel_5 = new JLabel("@163.com");
		lblNewLabel_5.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		lblNewLabel_5.setBounds(317, 118, 99, 15);
		panel_1.add(lblNewLabel_5);
		
		JButton btnNewButton_2 = new JButton("ȷ��");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					ReceiveMail rec = new ReceiveMail(path,textField.getText(),textField_1.getText());
					rec.resceive();
					RecMailContent frame = new RecMailContent(rec);
					frame.setTitle("�ʼ�����");
					frame.setVisible(true);
					rec.close();
				} catch (Exception e1) {
					e1.printStackTrace();
					Notice frame = new Notice("�������������ԣ�");
					frame.setVisible(true);
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
