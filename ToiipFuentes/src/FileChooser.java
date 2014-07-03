import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;



public class FileChooser extends JPanel
implements ActionListener {
	JButton selectButton;

	JFileChooser chooser;
	String choosertitle;



	public FileChooser() {

		setBorder(BorderFactory.createTitledBorder("Seleccione la carpeta destino"));

		selectButton = new JButton("Seleccionar");
		selectButton.addActionListener(this);
		
		this.setSize(200, 100);        
        add(selectButton, BorderLayout.NORTH);
        
	
                
       // pack();
        // arbitrary size to make vertical scrollbar appear
        //setSize(1204, 768);
       // setLocationByPlatform(true);
        setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File("Y:/SIO"));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// disable the "All files" option.
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 

			
      System.out.println("getCurrentDirectory(): " +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " +  chooser.getSelectedFile());
			 

			/*move this code to another event or function
			 */      

		}
			
			
		else {
			System.out.println("No Selection ");
		}
	}


	
	

	
}