package Login;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Sales extends JPanel implements ActionListener{
	Connection c;
	DefaultTableModel tModel;
	JTextField salesPrice_text;
	ArrayList<Object[]> sales_info = new ArrayList<Object[]>();
	int salesPrice_value=0;
	JComboBox yearCategoryBox;
	JComboBox monthCategoryBox;
	Sales() throws SQLException{
		
		setLayout(new BorderLayout());
		SqlConnection sqlcon=new SqlConnection();
		c = sqlcon.getConnection();
		getSalesInfo();
		
		HashSet<String> years = new HashSet<String>();
		years.add("년도");
		for(Object[] sale : sales_info) {
			String yearText = (String)sale[0];
			yearText=yearText.substring(0, 4);
			years.add(yearText);
		}
		
		String[] yearCategory = years.toArray(new String[0]);
		for (int i=0; i<yearCategory.length;i++) {
			if(yearCategory[i].equals("년도")) {
				String space = yearCategory[0];
				yearCategory[0] = yearCategory[i];
				yearCategory[i] = space;
			}
		}
		
		String[] monthCategory = new String[13];
		monthCategory[0] = "월";
		for(int i=1; i<monthCategory.length; i++) {
			monthCategory[i] = Integer.toString(i);
		}
		
		TopMenu menu = new TopMenu();
		JPanel salesPanel = new JPanel();
		
		salesPanel.setLayout(new BorderLayout());
		JPanel sales_sub_1 = new JPanel();
		JPanel sales_sub_2 = new JPanel();
		
		JPanel sales_sub_1_1 = new JPanel();
		JPanel sales_sub_1_2 = new JPanel();
		
		sales_sub_1_1.setLayout(new GridLayout(1,4,10,0));
		JButton showAllSales = new JButton("전체 조회");
		showAllSales.addActionListener(this);
		
		yearCategoryBox = new JComboBox(yearCategory);
		monthCategoryBox = new JComboBox(monthCategory);
		JButton showSelectedSales = new JButton("조회");
		showSelectedSales.addActionListener(new SearchSales());
		sales_sub_1_1.add(yearCategoryBox);
		sales_sub_1_1.add(monthCategoryBox);
		sales_sub_1_1.add(showSelectedSales);
		sales_sub_1_1.add(showAllSales);
		
		
		sales_sub_1_2.setLayout(new GridLayout(1,2,10,0));
		JLabel totalText = new JLabel("총금액");
		salesPrice_text = new JTextField();
		salesPrice_text.setEditable(false);
		totalText.setHorizontalAlignment(JLabel.CENTER);
		sales_sub_1_2.add(new JLabel("총금액"));
		sales_sub_1_2.add(salesPrice_text);
		
		
		sales_sub_1.setLayout(new GridLayout(1,2,10,0));
		sales_sub_1.add(sales_sub_1_1);
		sales_sub_1.add(sales_sub_1_2);
		
		sales_sub_2.setLayout(new GridLayout(1,1));
		String[] table_colum = {"날짜/시간","가격","결제수단"};
	
		tModel= new DefaultTableModel(table_colum,0) {
			public boolean isCellEditable(int i, int c) {
                return false;
            }
		};
		JTable table = new JTable(tModel);
		JScrollPane scrollPane = new JScrollPane(table);
		sales_sub_2.add(scrollPane);
		
		salesPanel.add("North",sales_sub_1);
		salesPanel.add("Center",sales_sub_2);
		
		add("North",menu);
		add("Center",salesPanel);
		
		this.setVisible(true);
		this.setSize(1920, 1080);
	}

	public void actionPerformed(ActionEvent e) {
		tModel.setNumRows(0);
		sales_info.clear();
		salesPrice_text.setText("0");
		salesPrice_value=0;
		try {
			SqlConnection sqlcon=new SqlConnection();
			c = sqlcon.getConnection();
			Statement stat = c.createStatement();
			String sql = "select * from daily_info";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
				Object[] tableArray = new Object[3];
				LocalDateTime dt= rs.getTimestamp("dTime").toLocalDateTime();
				dt=dt.minusHours(9);
				DateTimeFormatter formatSetting = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String formatedTime = dt.format(formatSetting);
				tableArray[0]=formatedTime;
				tableArray[1]=rs.getInt("dPrice");
				tableArray[2]=rs.getString("dPayment");
				sales_info.add(tableArray);
			}
			for (int i=0; i<sales_info.size();i++) {
				tModel.addRow(sales_info.get(i));
				int price = (int) sales_info.get(i)[1];
				salesPrice_value+=price;
			}
			salesPrice_text.setText(Integer.toString(salesPrice_value));
			yearCategoryBox.setSelectedIndex(0);
			monthCategoryBox.setSelectedIndex(0);
		} catch (SQLException e1) {}
	}
	public void getSalesInfo() throws SQLException {
		Statement stat = c.createStatement();
		String sql = "select * from daily_info";
		ResultSet rs = stat.executeQuery(sql);
		while(rs.next()) {
			Object[] tableArray = new Object[3];
			LocalDateTime dt= rs.getTimestamp("dTime").toLocalDateTime();
			dt=dt.minusHours(9);
			DateTimeFormatter formatSetting = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formatedTime = dt.format(formatSetting);
			tableArray[0]=formatedTime;
			tableArray[1]=rs.getInt("dPrice");
			tableArray[2]=rs.getString("dPayment");
			sales_info.add(tableArray);
		}
	}
	class SearchSales implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			tModel.setNumRows(0);
			String category_year = yearCategoryBox.getSelectedItem().toString();
			String category_month = monthCategoryBox.getSelectedItem().toString();
				if(!category_year.equals("년도") && !category_month.equals("월")) {
					for (Object[] sale : sales_info) {
						String yearText = (String)sale[0];
						yearText=yearText.substring(0, 4);
						String monthText = (String)sale[0];
						int category_month_int = Integer.parseInt(category_month);
						monthText=monthText.substring(5, 7);
						int monthText_int = Integer.parseInt(monthText);
						if(category_year.equals(yearText) && (category_month_int==monthText_int)) {
							tModel.addRow(sale);
						}
					}
				}
				else if(category_year.equals("년도")) {
					JOptionPane.showMessageDialog(null, "조회 하실 년도를 선택해주세요.");
				}
				else if(category_month.equals("월")) {
					JOptionPane.showMessageDialog(null, "조회 하실 월을 선택해주세요.");
				}
				else {
					JOptionPane.showMessageDialog(null, "조회 하실 년도와 월을 선택해주세요.");
				}
					
		}
	}
	
}
	

