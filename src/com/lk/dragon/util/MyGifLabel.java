/**
 *
 *
 * 文件名称： ServerHandlerView.java
 * 摘 要：
 * 作 者:XiangMZh
 * 创建时间：2014-8-13 下午3:37:21
 */
package com.lk.dragon.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.Timer;

import com.lk.dragon.db.dao.impl.MailDaoImpl;
import com.lk.dragon.db.domain.Mail;

/**
 * @author xiangmzh
 * @version 1.0.0
 * @revision
 */
public class MyGifLabel extends JLabel implements ActionListener {
	private static final long serialVersionUID = 45345345355L;
	private Timer time;

	public Image image;

	Thread refreshThread;

	/** */
	/**
	 * 
	 * @param image
	 *            : Sample:new ImageIcon(MyGifLabel.class
	 *            .getResource("/picture.gif")).getImage()
	 */
	public MyGifLabel(Image image) {
		this.image = image;
		time = new Timer(100, this);
		time.start();
	}

	/**
	 */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graph = (Graphics2D) g;
		if (image != null) {
			graph.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0,
					image.getWidth(null), image.getHeight(null), null);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.repaint();
	}
	
}
