package com.lk.dragon.view.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lk.dragon.service.RoleInfoService;
import com.lk.dragon.socket.GameServer;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.DbConnectionUtil;
import com.lk.dragon.util.SpringBeanUtil;
import com.lk.dragon.view.ServerHandlerView;


public class ServerBasePanel2 extends JPanel implements ActionListener{
    
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(ServerBasePanel2.class);
	
	private GameServer gameServer ;
	private JPanel DbConnectPanel;
	private TitledBorder border;
	
	private JButton btServerOnOff;
	private JButton btServerRestart;
	
	private JButton btConnection;
	private JLabel lDbAdress;
	private JLabel lDbPort;
	private JLabel lDbDriver;
	private JLabel lDbUser;
	private JLabel lDbPassword;
	private JLabel LDbName;
	
	private JTextField tDbName;
	private JTextField tDbAdress;
	private JTextField tDbPort;
	private JTextField tDbUser;
	private JPasswordField pDbPassword;
	private JComboBox cDbDriver;
	
	private String dbAddress = "";
	private String dbPort = "";
	private String user = "";
	private String password = "";
	private int dbDriver;
	private String url = "";
	private String driver= "";
	private String dbName = ""; 
	
	public ServerBasePanel2(GameServer gameServer){
		this.gameServer = gameServer;
		initComponents();
	}

	/**
	 * 初始化界面组件
	 */
	private void initComponents() {
		setLayout(null);
		
		DbConnectPanel = new JPanel();
		border = new TitledBorder("数据库连接测试");
		border.setTitleColor(Color.RED);
		border.setTitleFont(new Font("微软雅黑",Font.BOLD,15));
		
		btServerOnOff = new JButton("启动服务器");
		btServerRestart = new JButton("重启服务器");
		btConnection = new JButton("连接");
		btServerRestart.setEnabled(false);
		
		lDbAdress = new JLabel("数据库IP：");
		tDbAdress = new JTextField();
		lDbPort = new JLabel("数据库端口：");
		tDbPort = new JTextField();
		lDbUser = new JLabel("用户名：");
		tDbUser = new JTextField();
		lDbPassword = new JLabel("用户密码：");
		pDbPassword = new JPasswordField();
		lDbDriver = new JLabel("数据库类型：");
		cDbDriver = new JComboBox(new String[]{"MYSQL","ORACLE"});
		LDbName = new JLabel("数据库名:");
		tDbName = new JTextField();
		
		btServerOnOff.setBounds(10, 20, 150, 40);
		btServerRestart.setBounds(200, 20, 150, 40);
		DbConnectPanel.setBounds(10, 80, 650, 450);
		DbConnectPanel.setLayout(null);
		lDbAdress.setBounds(10, 20, 80, 40);
		tDbAdress.setBounds(85, 30, 150, 25);
		lDbPort.setBounds(300, 20, 80, 40);
		tDbPort.setBounds(380,30,150,25);
		
		lDbUser.setBounds(10, 50, 80, 40);
		tDbUser.setBounds(85, 60, 150, 25);
		lDbPassword.setBounds(300, 50, 80, 40);
		pDbPassword.setBounds(380, 60, 150, 25);
		
		lDbDriver.setBounds(10, 80, 80, 40);
		cDbDriver.setBounds(85, 90, 150, 25);
		LDbName.setBounds(300, 80, 80, 40);
		tDbName.setBounds(380, 92, 150, 25);
		
		btConnection.setBounds(10, 120, 100, 30);
		add(btServerOnOff);
		add(btServerRestart);
		
		DbConnectPanel.setBorder(border);
		DbConnectPanel.add(lDbAdress);
		DbConnectPanel.add(tDbAdress);
		DbConnectPanel.add(lDbPort);
		DbConnectPanel.add(tDbPort);
		DbConnectPanel.add(lDbUser);
		DbConnectPanel.add(tDbUser);
		DbConnectPanel.add(lDbPassword);
		DbConnectPanel.add(pDbPassword);
		DbConnectPanel.add(lDbDriver);
		DbConnectPanel.add(cDbDriver);
		DbConnectPanel.add(LDbName);
		DbConnectPanel.add(tDbName);
		DbConnectPanel.add(btConnection);
		
		add(DbConnectPanel);
		
		btConnection.addActionListener(this);
		btServerOnOff.addActionListener(this);
		btServerRestart.addActionListener(this);
		
		setVisible(true);
		
		
		
	}

	/**
	 * 注册按钮监听事件
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		dbAddress = tDbAdress.getText();
		dbPort = tDbPort.getText();
		user = tDbUser.getText();
		password = pDbPassword.getText();
		dbDriver = cDbDriver.getSelectedIndex();
		dbName = tDbName.getText();
		
		if (e.getActionCommand().equals("启动服务器")) {
			try {
				new Thread(){

					@Override
					public void run() {
						gameServer.run();
					}
					
				}.start();
                //启动服务器
	
			        // 根据address port 在此处执行启动服务器代码
	                this.btServerRestart.setEnabled(true);
	                this.btServerOnOff.setText("停止服务器");
	                ServerHandlerView.serMenu.setText("服务器运行状态：服务器运行中...");
				
			} catch (Exception e2) {
				//提示服务启动失败
				JOptionPane.showMessageDialog(null, "服务器启动失败");
				logger.info("启动服务器失败" + e2.getMessage());
			}

		} else if (e.getActionCommand().equals("停止服务器")) {
			try {
				    //停止服务
					gameServer.closeServer();
					//gameServer.closingWarService(false);//对于非程序退出类服务 只关闭Socket通信层服务 后台战斗模块继续执行
	                this.btServerOnOff.setText("启动服务器");
	                ServerHandlerView.serMenu.setText("服务器运行状态：服务器未运行");
				
			} catch (Exception e2) {
				//提示停止失败
				JOptionPane.showMessageDialog(null, "停止启动失败");
				logger.info("停止服务器失败" + e2.getMessage());
			}

		} else if (e.getActionCommand().equals("重启服务器")) {
			try {
			    //重新启动服务
				gameServer.restartServer();
				this.btServerOnOff.setText("停止服务器");
				ServerHandlerView.serMenu.setText("服务器运行状态：服务器运行中...");
			} catch (Exception e2) {
				logger.info("重启服务器失败" + e2.getMessage());
				JOptionPane.showMessageDialog(null, "重启服务器失败");
			}

		}else if(e.getActionCommand().equals("连接")){
			if(dbAddress.trim()==null || dbAddress.trim().length()<=0){
				JOptionPane.showMessageDialog(null, "数据库IP不能为空");
				return ;
			}
			if(dbPort.trim()==null || dbPort.trim().length()<=0){
				JOptionPane.showMessageDialog(null, "数据库端口不能为空");
				return ;
			}
			if(user.trim()==null || user.trim().length()<=0){
				JOptionPane.showMessageDialog(null, "用户名不能为空");
				return ;
			}
			if(password.trim()==null || password.trim().length()<=0){
				JOptionPane.showMessageDialog(null, "密码不能为空");
				return ;
			}
			if(dbName.trim()==null || dbName.trim().length()<=0){
				JOptionPane.showMessageDialog(null, "数据库名不能为空");
				return ;
			}
			if(dbDriver == 0){
				driver = DbConnectionUtil.MYSQL_DRIVER;
				url = "jdbc:mysql://"+dbAddress.trim()+":"+dbPort.trim()+"/"+dbName;
			}else{
				driver = DbConnectionUtil.ORACLE_DRIVER;
				url = "jdbc:oracle:thin:@"+dbAddress.trim()+":"+dbPort.trim()+":"+dbName;
			}
			try {
				DbConnectionUtil.connectDb(driver, url, user, password);
				JOptionPane.showMessageDialog(null, "数据库连接正常");
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
				logger.info("数据库驱动获取失败"+e2.getMessage());
				JOptionPane.showMessageDialog(null, "数据库驱动获取失败");
			} catch (SQLException e2) {
				e2.printStackTrace();
				logger.info("数据库驱动获取失败"+e2.getMessage());
				JOptionPane.showMessageDialog(null, "数据库连接失败");
			}
		
		}
		

	}
	
}
