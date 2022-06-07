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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	Sales(){
		
		setLayout(new BorderLayout());
		
		TopMenu menu = new TopMenu();
		JPanel salesPanel = new JPanel();
		
		salesPanel.setLayout(new BorderLayout());
		JPanel sales_sub_1 = new JPanel();
		JPanel sales_sub_2 = new JPanel();
		
		JPanel sales_sub_1_1 = new JPanel();
		JPanel sales_sub_1_2 = new JPanel();
		
		sales_sub_1_1.setLayout(new GridLayout(1,1,10,0));
		JButton showSales = new JButton("매출 조회");
		showSales.addActionListener(this);
		sales_sub_1_1.add(showSales);
		
		
		
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
		String[] table_colum = {"시간","가격","결제수단"};
	
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
		} catch (SQLException e1) {}
		
	}
	
}
