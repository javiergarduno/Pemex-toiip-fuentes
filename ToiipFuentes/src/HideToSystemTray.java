import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 *
 * @author Mohammad Faisal
 * ermohammadfaisal.blogspot.com
 * facebook.com/m.faisal6621
 *
 */

public class HideToSystemTray extends JFrame{
	TrayIcon trayIcon;
	SystemTray tray;
	
	FileChooser chooser = new FileChooser();
	JTextArea logArea;
	
	

	HideToSystemTray(){
		super("Javier Garduño - Copia de fuentes ");

		System.out.println("creating instance");
		
		try{
			System.out.println("setting look and feel");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			System.out.println("Unable to set LookAndFeel");
		}

		if(SystemTray.isSupported()){
			System.out.println("system tray supported");
			tray = SystemTray.getSystemTray();
			

			Image image = Toolkit.getDefaultToolkit().getImage("pemex_icon.png");
			

			ActionListener exitListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Exiting....");
					System.exit(0);
				}
			};

			PopupMenu popup = new PopupMenu();
			
			MenuItem defaultItem = new MenuItem("Cerrar");
			defaultItem.addActionListener(exitListener);
			popup.add(defaultItem);

			defaultItem = new MenuItem("Abrir");
			defaultItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(true);
					setExtendedState(JFrame.NORMAL);
				}
			});
			popup.add(defaultItem);
			
			
			trayIcon = new TrayIcon(image, "Copia de Fuentes", popup);
			trayIcon.setImageAutoSize(true);
			
			
		}else{
			System.out.println("system tray not supported");
		}

		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				if(e.getNewState()==ICONIFIED){
					try {
						tray.add(trayIcon);
						setVisible(false);
						System.out.println("added to SystemTray");
					} catch (AWTException ex) {
						System.out.println("unable to add to tray");
					}
				}
				if(e.getNewState()==7){
					try{
						tray.add(trayIcon);
						setVisible(false);
						System.out.println("added to SystemTray");
					}catch(AWTException ex){
						System.out.println("unable to add to system tray");
					}
				}
				if(e.getNewState()==MAXIMIZED_BOTH){
					tray.remove(trayIcon);
					setVisible(true);
					System.out.println("Tray icon removed");
				}
				if(e.getNewState()==NORMAL){
					tray.remove(trayIcon);
					setVisible(true);
					System.out.println("Tray icon removed");
				}
			}
		});
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("pemex_icon.png"));

		setVisible(true);
		setSize(800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//chooser.setSize(10, 50);
		//add(chooser, BorderLayout.NORTH);
		
		logArea = new JTextArea(40,1 );
        logArea.append("Eventos:\n ");
        logArea.setEditable(false);
        
        JScrollPane jp = new JScrollPane(logArea);        
        add(jp, BorderLayout.CENTER);
        jp.setAutoscrolls(true);
	}
	
	/**
	 * @param text
	 * @return Appends text with date to JFrame
	 */
	void writeLog(String text){
        logArea.append("\n" + text);

	}
	
	/*
	
	public static void main(String[] args){
		HideToSystemTray Frame =  new HideToSystemTray();
		Frame.writeLog("Prueba");
		
	}
	*/
	
}