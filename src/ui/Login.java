package ui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("«Î ‰»Î√‹¬Î");
		lblNewLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("√‹¬Î");
		lblNewLabel_1.setBounds(88, 61, 37, 27);
		lblNewLabel_1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		
		panel_1.add(lblNewLabel_1);
		
		textField = new JPasswordField();
		textField.setBounds(135, 61, 126, 27);
		panel_1.add(textField);
		textField.setColumns(20);
		
		JButton btnNewButton_1 = new JButton("»∑»œ");
		btnNewButton_1.setBounds(276, 61, 74, 29);
		btnNewButton_1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if(textField.getText().equals("112358")) {
					dispose();
					System.out.print(1111);
					Choose frame1 = new Choose();
					frame1.setTitle("«Î—°‘Ò∑˛ŒÒ");
					frame1.setVisible(true);
				}
				else {
					Notice frame2 = new Notice("√‹¬Î¥ÌŒÛ£¨«Î÷ÿ–¬ ‰»Î°£");
					frame2.setTitle("√‹¬Î¥ÌŒÛ");
					frame2.setVisible(true);
				}
			}
		});
		panel_1.add(btnNewButton_1);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("ÕÀ≥ˆ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					System.exit(0);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel_2.add(btnNewButton);
	}

}
