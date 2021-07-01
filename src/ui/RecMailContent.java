package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import service.ReceiveMail;

import javax.swing.JLabel;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

public class RecMailContent extends JFrame {

	private JPanel contentPane;
	private ReceiveMail rec;
	/**
	 * Create the frame.
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public RecMailContent(ReceiveMail rec) throws MessagingException, IOException {
		this.rec=rec;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 527, 366);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);		
		JLabel lblNewLabel = new JLabel("�ʼ���Ϣ");
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("����");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		panel_1.add(btnNewButton);
		
		JTextArea textArea = new JTextArea();
		contentPane.add(textArea, BorderLayout.WEST);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		textArea.append("������: " + rec.getFrom()+"\r\n");
		
		textArea.append("δ���ʼ���: " + rec.getUnNum()+"\r\n");
		textArea.append("���ʼ�: " + rec.getNewNum()+"\r\n");
		textArea.append("�ʼ�����: " + rec.getTotalNum()+"\r\n");
		for (int i = 0, count = rec.getMailLen(), k; i < count; i++) {
			k=i+1;
			rec.setMsg(rec.getMessages(i));
			textArea.append("------------------������" + k+ "���ʼ�-------------------- "+"\r\n");
			textArea.append("����: " + rec.getSubject()+"\r\n");
			textArea.append("������: " + rec.getFrom()+"\r\n");
			textArea.append("�ռ��ˣ�" + rec.getReceiveAddress(null)+"\r\n");
			textArea.append("����ʱ�䣺" + rec.getSentDate(null)+"\r\n");
			textArea.append("�Ƿ��Ѷ���" + rec.isSeen()+"\r\n");
			textArea.append("�ʼ����ȼ���" + rec.getPriority()+"\r\n");
			textArea.append("�Ƿ���Ҫ��ִ��" + rec.isReplySign()+"\r\n");
			textArea.append("�ʼ���С��" + rec.getMailSize()+"\r\n");
			textArea.append("�Ƿ����������" + rec.isAttachment()+"\r\n");
			textArea.append("�ʼ����ģ�" + rec.getText()+"\r\n");
			textArea.append("------------------��" + k+ "���ʼ���������-------------------- "+"\r\n");	
		}
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}

}
