package com.systemhaus.demo;

import javax.swing.SwingUtilities;

public class Main extends javax.swing.JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Main() {
		super("SGBooks - Sistema de Gerenciamento de Biblioteca");
		initComponents();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		Main main = new Main();
        SwingUtilities.invokeLater(() -> {
            main.setVisible(true);
        });
	}

	private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);


        setBounds(0, 0, 900, 700);
    }
}
