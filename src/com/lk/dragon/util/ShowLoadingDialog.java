/**
 *
 *
 * 文件名称： ServerHandlerView.java
 * 摘 要：
 * 作 者:XiangMZh
 * 创建时间：2014-8-13 下午3:37:21
 */
package com.lk.dragon.util;

import java.awt.BorderLayout;
import java.awt.Dialog;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JRootPane;

/**
 * @author xiangmzh
 * @version 1.0.0
 * @revision
 */
public class ShowLoadingDialog{

	private static final MyGifLabel stateLbl =  new MyGifLabel(new ImageIcon(ShowLoadingDialog.class.getResource("/wait.gif")).getImage());
	private static JDialog loadingDialog = null;
	private ShowLoadingDialog() {}
	
	public static JDialog getDialogInstance(){
		if(loadingDialog == null){
		    loadingDialog = new JDialog();
			loadingDialog.setUndecorated(true);
			loadingDialog.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			loadingDialog.add(stateLbl, BorderLayout.CENTER);
			loadingDialog.setAlwaysOnTop(true);
			loadingDialog.setSize(50, 50);
			loadingDialog.setLocationRelativeTo(null);
			loadingDialog.setResizable(false);
		}
		
		return loadingDialog;
	}
	
}
