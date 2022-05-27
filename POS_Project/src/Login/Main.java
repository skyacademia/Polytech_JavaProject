package Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main extends JPanel {
	Main(){
		TopMenu menu = new TopMenu();
		
		JPanel statusPanel = new JPanel();
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new GridLayout(0,5,100,50));		
		JButton[] tableBtns = new JButton[10];
		for(int i=0; i<tableBtns.length; i++) {
			tableBtns[i] = new JButton(String.format("%d",i+1));
			tableBtns[i].addActionListener(new OrderAction());
			tablePanel.add(tableBtns[i]);
		}
		this.setLayout(new BorderLayout());
		add(menu,BorderLayout.NORTH);
		add(tablePanel,BorderLayout.CENTER);
		this.setVisible(true);
		this.setSize(1920, 1080);
	}
    private class OrderAction implements ActionListener {
    	 
        @Override
        public void actionPerformed(ActionEvent e) {
        	JButton btn = (JButton)e.getSource();
        	JFrame frame = new Order(btn);
        }
        
    }
}
