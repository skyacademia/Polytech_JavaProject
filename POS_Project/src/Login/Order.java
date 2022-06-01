package Login;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class Order extends JFrame {
	private JButton btn;
	Order(JButton btn){
	this.btn = btn;
	this.setLayout(new GridLayout(2,2));
	JPanel tablePanel = new JPanel();
	JPanel infoPanel = new JPanel();
	JPanel categoryPanel = new JPanel();
	JPanel itemPanel = new JPanel();
	
	categoryPanel.setLayout(new GridLayout(2,4));		
	JButton[] categoryBtns = new JButton[8];
	for(int i=0; i<categoryBtns.length; i++) {
		categoryBtns[i] = new JButton(String.format("카테고리 %d",i+1));
		categoryPanel.add(categoryBtns[i]);
	}
		
	itemPanel.setLayout(new GridLayout(2,4));
	JButton[] itemBtns = new JButton[8];
	for(int i=0; i<itemBtns.length; i++) {
		itemBtns[i] = new JButton(String.format("메뉴 %d",i+1));
		itemPanel.add(itemBtns[i]);
	}
	
	tablePanel.setLayout(new GridLayout(1,1));
	String[] table_colum = {"상품명","수량","가격","합계"};
	String[][] table_row = {{"양지 쌀국수", "2", "9000", "18000"}};
	JTable table = new JTable(table_row,table_colum);
	JScrollPane scrollPane = new JScrollPane(table);
	tablePanel.add(scrollPane);
	
	infoPanel.setLayout(new GridLayout(3,1));
	JPanel info_sub_1 = new JPanel();
	JPanel info_sub_2 = new JPanel();
	JPanel info_sub_3 = new JPanel();

	info_sub_1.setLayout(new GridLayout(1,2));
	JButton addBtn = new JButton("수량 +");
	JButton minusBtn = new JButton("수량 -");
	info_sub_1.add(addBtn);
	info_sub_1.add(minusBtn);
	
	info_sub_2.setLayout(new GridLayout(1,2,0,10));
	JLabel totalLabel = new JLabel("총 금액");
	JTextField totalPrice = new JTextField();
	totalPrice.setEnabled(false);
	info_sub_2.add(totalLabel);
	info_sub_2.add(totalPrice);

	info_sub_3.setLayout(new GridLayout(1,3,0,10));
	JButton cardBtn = new JButton("카드");
	JButton cashBtn = new JButton("현금");
	JButton cancelBtn = new JButton("취소");
	info_sub_3.add(cardBtn);
	info_sub_3.add(cashBtn);
	info_sub_3.add(cancelBtn);
	
	infoPanel.add(info_sub_1);
	infoPanel.add(info_sub_2);
	infoPanel.add(info_sub_3);
	
	add(tablePanel);
	add(categoryPanel);
	add(infoPanel);
	add(itemPanel);

	this.setVisible(true);
	this.setSize(1920,1080);
	}
	public void updateButton(JButton[] categoryBtns) throws SQLException {
		MenuInfo info = new MenuInfo();
		ArrayList <String[]> menu = info.getMenuInfo();
		HashSet <String> category_text = new HashSet<String>();
		for (String[] menuItem : menu) {
			category_text.add(menuItem[1]);
		}
	}
}