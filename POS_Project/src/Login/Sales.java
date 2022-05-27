package Login;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Sales extends JPanel{
	Sales(){
		setLayout(new BorderLayout());
		
		TopMenu menu = new TopMenu();
		JPanel salesPanel = new JPanel();
		
		salesPanel.setLayout(new BorderLayout());
		JPanel sales_sub_1 = new JPanel();
		JPanel sales_sub_2 = new JPanel();
		
		JPanel sales_sub_1_1 = new JPanel();
		JPanel sales_sub_1_2 = new JPanel();
		
		sales_sub_1_1.setLayout(new GridLayout(1,2,10,0));
		sales_sub_1_1.add(new JButton("일일 매출"));
		sales_sub_1_1.add(new JButton("월 매출"));
		
		
		
		sales_sub_1_2.setLayout(new GridLayout(1,2,10,0));
		JLabel totalText = new JLabel("총 금액");
		JTextField totalPrice = new JTextField();
		totalText.setHorizontalAlignment(JLabel.CENTER);
		sales_sub_1_2.add(new JLabel("총 금액"));
		sales_sub_1_2.add(totalPrice);
		
		
		sales_sub_1.setLayout(new GridLayout(1,2,10,0));
		sales_sub_1.add(sales_sub_1_1);
		sales_sub_1.add(sales_sub_1_2);
		
		sales_sub_2.setLayout(new GridLayout(1,1));
		String[] table_colum = {"시간","금액","주문내역","결제방식"};
		String[][] table_row = {{"2022-05-25 12:19:10", "18000", "양지쌀국수 2", "카드"}};
		JTable table = new JTable(table_row,table_colum);
		JScrollPane scrollPane = new JScrollPane(table);
		sales_sub_2.add(scrollPane);
		
		salesPanel.add("North",sales_sub_1);
		salesPanel.add("Center",sales_sub_2);
		
		add("North",menu);
		add("Center",salesPanel);
		
		this.setVisible(true);
		this.setSize(1920, 1080);
	}
}
