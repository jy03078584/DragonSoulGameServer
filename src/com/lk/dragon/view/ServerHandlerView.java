/**
 *
 *
 * 文件名称： ServerHandlerView.java
 * 摘 要：
 * 作 者:XiangMZh
 * 创建时间：2014-8-13 下午3:37:21
 */
package com.lk.dragon.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.lk.dragon.service.FactionInfoService;
import com.lk.dragon.service.SqlToolsService;
import com.lk.dragon.service.WarService;
import com.lk.dragon.socket.GameServer;
import com.lk.dragon.util.ShowLoadingDialog;
import com.lk.dragon.util.SpringBeanUtil;
import com.lk.dragon.view.panel.ServerBasePanel2;
import com.lk.dragon.view.panel.ServerLoggerControlPanel;



/**
 * ServerHandler视图
 * 
 * @author xmz
 * 
 */
public class ServerHandlerView extends JFrame {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ServerHandlerView.class);

	
	
	private JTabbedPane jTabbedPane;
	private ServerBasePanel2 sbp;
	private ServerLoggerControlPanel slc;
	public static JMenu serMenu;
	private GameServer gameServer;

	public ServerHandlerView() {
		
		try {
			this.gameServer = new GameServer(9010);
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			logger.info("调整风格失败："+e.getMessage());
		}
		setTitle("游戏服务端");
		// 初始化子组件
		initComponents();

		//初始化状态栏
		initStatue();
		
		// 设置大小,位置
		setSizeAndCentralizeMe(700, 680);//700 600
		
		//UIManager.l
		//窗口不可改变
		setResizable(false);
		
		// 点击窗口右上角的关闭按钮关闭窗口,退出程序
		// 退出前询问
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (0 == JOptionPane.showConfirmDialog(null, "确定退出？", "服务器检测",JOptionPane.YES_NO_OPTION)) {
					//ShowLoadingDialog.LOADINGDIALOG.setVisible(true);
					gameServer.closingWarService(true);
				}
			}
		});
		setVisible(true);
	}

	/**
	 * 初始化状态栏
	 */
	private void initStatue() {

		JMenuBar statuBar = new JMenuBar();
		serMenu = new JMenu("服务器运行状态：未运行");
		serMenu.setForeground(Color.red);
		serMenu.setFont(new Font("微软雅黑",Font.BOLD,15));
		this.setJMenuBar(statuBar);
		statuBar.add(serMenu);
		
	}

	
	// 设置程序大小并定位程序在屏幕正中
	private void setSizeAndCentralizeMe(int width, int height) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(width, height);
		this.setLocation(screenSize.width / 2 - width / 2, screenSize.height
				/ 2 - height / 2);
	}

	/**
	 * 初始化组件
	 */
	private void initComponents() {

		jTabbedPane = new JTabbedPane();
		jTabbedPane.setTabPlacement(JTabbedPane.TOP);
		jTabbedPane.setFont(new Font("微软雅黑",Font.BOLD,15));
		this.add(jTabbedPane);
		
		
		sbp = new ServerBasePanel2(gameServer);
		jTabbedPane.addTab("启/停服务器", sbp);
		slc = new ServerLoggerControlPanel();
		jTabbedPane.addTab("日志管理", slc);
	}

}
