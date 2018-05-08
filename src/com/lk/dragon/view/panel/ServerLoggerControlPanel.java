package com.lk.dragon.view.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.lk.dragon.util.FileUtil;
import com.lk.dragon.util.SortTableMouseAdaptor;


/**
 * 日志管理页
 * 
 * @author xmz
 * 
 */
public class ServerLoggerControlPanel extends JPanel implements ActionListener {
	private static Logger logger = Logger
			.getLogger(ServerLoggerControlPanel.class);

	private JLabel lId;// ID
	private JLabel llogTime;// 状态入库时间
	private JLabel llogDetail;// 详细日志
	private JTextField tId;
	private JTextField tLogTime;
	private JTextField tLogDetail;

	private JButton btQuery;

	private JTable jTable;

	public ServerLoggerControlPanel() {
		initComponents();
	}

	private void initComponents() {
		setLayout(null);

		lId = new JLabel("日志ID：");
		llogDetail = new JLabel("日志明细：");
		llogTime = new JLabel("入库时间：");
		tId = new JTextField();
		tLogDetail = new JTextField();
		tLogTime = new JTextField();
		lId.setBounds(10, 10, 80, 40);
		tId.setBounds(60, 20, 100, 25);
		llogTime.setBounds(170, 10, 100, 40);
		tLogTime.setBounds(225, 20, 150, 25);
		llogDetail.setBounds(380, 10, 100, 40);
		tLogDetail.setBounds(440, 20, 150, 25);

		btQuery = new JButton("查询");
		btQuery.setBounds(600, 20, 70, 25);

		initLogTable();// 初始化日志Table
		JScrollPane scrollPane = new JScrollPane(jTable);
		scrollPane.setBounds(10, 50, 650, 550);

		add(lId);
		add(tId);
		add(llogTime);
		add(tLogTime);
		add(llogDetail);
		add(tLogDetail);
		add(btQuery);
		add(scrollPane);
		
		btQuery.addActionListener(this);
		setVisible(true);
	}

	private void initLogTable() {
		jTable = new JTable();
		jTable.setRowHeight(20);
		DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
		String[] headers = getHeaders();
		for (int i = 0; i < headers.length; i++) {
			tableModel.addColumn(headers[i]);
		}
		jTable.getTableHeader().addMouseListener(
				new SortTableMouseAdaptor(jTable));
	}

	/**
	 * 读取配置文件获取表头
	 * 
	 * @return
	 */
	private String[] getHeaders() {

		String value = FileUtil.getValue(System.getProperty("user.dir")
				+ "\\properties\\ui.properties", "headers");
		String[] headers = value.split(";");
		return headers;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("查询")) {
			try {
				// 执行日志查询代码
				JOptionPane.showMessageDialog(null, "查询日志...");
			} catch (Exception e2) {
				logger.info("查询日志出错："+e2.getMessage());
			}
		}
	}

}
