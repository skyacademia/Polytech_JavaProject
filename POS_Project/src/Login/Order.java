package Login;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Order extends JFrame {
	HashMap<String, ArrayList<Object[]>> category_menu;
	private Main.TableButton btn;
	Connection c;

	menuButton[] itemBtns;
	DefaultTableModel tModel;
	ArrayList<Object[]> menu_infos;
	ArrayList<Object[]> order_info = new ArrayList<Object[]>();
	int total;
	JTextField totalPrice = new JTextField();

	Order(Main.TableButton btn) throws SQLException {
		this.btn = btn;
		this.setLayout(new GridLayout(2, 2));

		JPanel tablePanel = new JPanel();
		JPanel infoPanel = new JPanel();
		JPanel categoryPanel = new JPanel();
		JPanel itemPanel = new JPanel();

		categoryPanel.setLayout(new GridLayout(2, 4));

		MenuInfo info = new MenuInfo();
		menu_infos = info.getMenuInfo();
		HashSet<String> categorys = new HashSet<String>();
		for (Object[] menu : menu_infos) {
			categorys.add((String) menu[1]);
		}
		category_menu = new HashMap<String, ArrayList<Object[]>>();
		for (String category : categorys) {
			ArrayList<Object[]> sorting_menu = new ArrayList<Object[]>();
			for (Object[] menu : menu_infos) {
				if (category.equals(menu[1])) {
					String menuName = (String) menu[2];
					int menuPrice = (int) menu[3];
					sorting_menu.add(new Object[] { menuName, menuPrice });
				}
			}
			category_menu.put(category, sorting_menu);
		}

		categoryButton[] categoryBtns = new categoryButton[8];
		Iterator hasIter = categorys.iterator();
		for (int i = 0; i < categoryBtns.length; i++) {
			if (hasIter.hasNext()) {
				String category_name = hasIter.next().toString();
				categoryBtns[i] = new categoryButton(category_menu.get(category_name), category_name);
				categoryBtns[i].addActionListener(categoryBtns[i]);
			} else {
				categoryBtns[i] = new categoryButton();
			}
			categoryPanel.add(categoryBtns[i]);
		}

		tablePanel.setLayout(new GridLayout(1, 1));
		String[] table_colum = { "메뉴이름", "개수", "가격", "합계" };
		tModel = new DefaultTableModel(table_colum, 0) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		
		JTable table = new JTable(tModel);
		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane);

		infoPanel.setLayout(new GridLayout(3, 1));

		JPanel info_sub_1 = new JPanel();
		JPanel info_sub_2 = new JPanel();
		JPanel info_sub_3 = new JPanel();

		info_sub_2.setLayout(new GridLayout(1, 2, 0, 10));
		JLabel totalLabel = new JLabel("총 금액");
		totalPrice.setEnabled(false);
		info_sub_2.add(totalLabel);
		info_sub_2.add(totalPrice);

		itemPanel.setLayout(new GridLayout(2, 4));
		itemBtns = new menuButton[8];
		for (int i = 0; i < itemBtns.length; i++) {
			itemBtns[i] = new menuButton();
			itemBtns[i].addActionListener(itemBtns[i]);
			itemPanel.add(itemBtns[i]);
		}

		info_sub_1.setLayout(new GridLayout(1, 2));
		JButton addBtn = new JButton("수량 +");
		addBtn.addActionListener(new countAction(table));
		JButton minusBtn = new JButton("수량 -");
		minusBtn.addActionListener(new countAction(table));
		info_sub_1.add(addBtn);
		info_sub_1.add(minusBtn);

		info_sub_3.setLayout(new GridLayout(1, 3, 0, 10));
		JButton cardBtn = new JButton("카드");
		cardBtn.addActionListener(new paymentAction(cardBtn.getText()));
		JButton cashBtn = new JButton("현금");
		cashBtn.addActionListener(new paymentAction(cashBtn.getText()));
		JButton cancelBtn = new JButton("취소");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn.setOrderInfo(order_info);
				btn.showOrderInfo();
				dispose();
			}
		});

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
		this.setSize(1920, 1080);
		if (this.btn.getOrderInfo().size() > 0) {
			this.order_info = this.btn.getOrderInfo();
			showOrderTable();
			showOrderTotal();
		}
	}

	class countAction implements ActionListener {
		JTable table;
		private String name;

		countAction(JTable table) {
			this.table = table;
		}

		public void actionPerformed(ActionEvent e) {
			JButton countBtn = (JButton) e.getSource();
			int rowIndex = 0;
			name = null;
			if (table.getRowSelectionAllowed()) {
				rowIndex = table.getSelectedRow();
				name = (String) table.getValueAt(rowIndex, 0);
			}
			switch (countBtn.getText()) {
				case "수량 +": {
					for (Object[] order : order_info) {
						if (order[0].equals(name)) {
							order[1] = (int) order[1] + 1;
							order[3] = (int) order[1] * (int) order[2];
						}
					}
					break;
				}
				case "수량 -": {
					for (Object[] order : order_info) {
						if (order[0].equals(name)) {
							order[1] = (int) order[1] - 1;
							order[3] = (int) order[1] * (int) order[2];
						}
					}
					break;
				}
			}
			showOrderTable();
			showOrderTotal();
		}
	}

	class paymentAction implements ActionListener {
		String sql;
		int price;
		String payment;

		paymentAction(String payment) {
			this.payment = payment;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatSetting = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String formatedTime = now.format(formatSetting);
				SqlConnection sqlconn = new SqlConnection();
				c = sqlconn.getConnection();
				Statement stat = c.createStatement();
				price = Integer.parseInt(totalPrice.getText());
				sql = "insert into daily_info values('" + formatedTime + "'," + price + ",'" + payment + "')";
				stat.executeUpdate(sql);
				JOptionPane.showMessageDialog(null, String.format("%d원 결제되었습니다.", price));
				order_info.clear();
				btn.afterOrder();
				dispose();
			} catch (SQLException e1) {
			}
		}
	}

	class categoryButton extends JButton implements ActionListener {
		ArrayList<Object[]> menu_by_category;
		String category_name;

		categoryButton(ArrayList<Object[]> menu_by_category, String name) {
			super();
			category_name = name;
			setText(category_name);
			this.menu_by_category = menu_by_category;
		}

		categoryButton() {
			super();
		}

		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton) e.getSource();
			for (int i = 0; i < menu_by_category.size(); i++) {
				String button_text = "<html>";
				if (btn.getText() != null) {
					String menuName = (String) menu_by_category.get(i)[0];
					int menuPrice = (int) menu_by_category.get(i)[1];
					button_text += String.format("%s<br>%d", menuName, menuPrice);
					button_text += "</html>";
					itemBtns[i].setMenuInfo(menuName, menuPrice);
					itemBtns[i].setText(button_text);
				}
			}
		}
	}
	class menuButton extends JButton implements ActionListener {
		private String menu_name;
		private int menu_price;

		menuButton() {
			super();
		}

		public void actionPerformed(ActionEvent e) {
			int count = 1;
			menuButton clickedBtn = (menuButton) e.getSource();
			if (clickedBtn.getText() != null) {
				Object[] sendingData = { menu_name, count, menu_price, menu_price * count };

				boolean added = false;
				for (int i = 0; i < order_info.size(); i++) {
					if (order_info.get(i)[0].equals(sendingData[0])) {
						Object[] existMenu = order_info.get(i);
						existMenu[1] = (int) existMenu[1] + 1;
						existMenu[3] = (int) existMenu[1] * (int) existMenu[2];
						added = true;
						break;
					}
				}
				if (added == false) {
					order_info.add(sendingData);
				} else {
					added = false;
				}
				showOrderTable();
				showOrderTotal();
			}
		}

		public void setMenuInfo(String name, int price) {
			menu_name = name;
			menu_price = price;
		}
	}

	public void showOrderTable() {
		tModel.setNumRows(0);
		for (int i = 0; i < order_info.size(); i++) {
			tModel.addRow(order_info.get(i));
		}
	}

	public void showOrderTotal() {
		total = 0;
		for (int i = 0; i < order_info.size(); i++) {
			total += (int) order_info.get(i)[3];
		}
		totalPrice.setText(Integer.toString(total));
	}
}
