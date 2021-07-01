package main;

import java.awt.EventQueue;

import ui.Welcome;

public class Main {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome frame = new Welcome();
					frame.setVisible(true);
					frame.setTitle("欢迎来到邮件小助手");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
