package Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Main extends JPanel {
	Main(){
		TopMenu menu = new TopMenu();
		
		JPanel statusPanel = new JPanel();
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new GridLayout(0,5,100,50));		
		TableButton[] tableBtns = new TableButton[10];
		for(int i=0; i<tableBtns.length; i++) {
			tableBtns[i] = new TableButton(i+1);
			tableBtns[i].addActionListener(tableBtns[i]);
			tablePanel.add(tableBtns[i]);
		}
		this.setLayout(new BorderLayout());
		add(menu,BorderLayout.NORTH);
		add(tablePanel,BorderLayout.CENTER);
		this.setVisible(true);
		this.setSize(1920, 1080);
	}
	class TableButton extends JButton implements ActionListener{
		private ArrayList<Object[]> order_info = new ArrayList<Object[]>();
		int table_num;
		TableButton(int num){
			super(Integer.toString(num));
			table_num = num;
			setText(String.format("%d", num));
		}
		
		// 클릭 시 주문 화면으로 이동
		public void actionPerformed(ActionEvent e) {
	    	try {
	    		TableButton btn = (TableButton)e.getSource();
				JFrame frame = new Order(btn);
			} catch (SQLException e1) {}
		}
		
		// 버튼 기능
		public void setOrderInfo(ArrayList<Object[]> order){
			for (int i=0; i<order.size(); i++) {
				order_info.add(order.get(i));
			}
		}
		public ArrayList<Object[]> getOrderInfo(){
			return order_info;
		}
		public void showOrderInfo() {
			if(order_info.size()>0) {
				String text="<html>";
				for(int i=0; i<order_info.size(); i++) {
					String menu = (String) order_info.get(i)[0]; 
					int menu_count = (int) order_info.get(i)[1];
					text+=String.format("%s %d<br>",menu,menu_count);
				}
				text+="</html>";
				setText(text);
			}
		}
		public void afterOrder() {
			order_info.clear();
			setText(String.format("%d", table_num));
		}
	}
}


