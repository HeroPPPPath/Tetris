package com.hlju.Tetris;



import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;



import javax.swing.JFrame;

import javax.swing.JMenu;

import javax.swing.JMenuBar;

import javax.swing.JMenuItem;

import javax.swing.JOptionPane;



public class TetrisApp extends JFrame {



	private static final long serialVersionUID = 8995729671326316569L;

	Tetris tetris = new Tetris();



	public TetrisApp() {

		this.setLocationRelativeTo(null);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setSize(280, 350);

		this.setTitle("Tetris Remake");

		this.setResizable(false);



		JMenuBar menu = new JMenuBar();

		this.setJMenuBar(menu);

		JMenu gameMenu = new JMenu("��Ϸ");

		JMenuItem newGameItem = gameMenu.add("����Ϸ");

		newGameItem.addActionListener(this.NewGameAction);

		JMenuItem pauseItem = gameMenu.add("��ͣ");

		pauseItem.addActionListener(this.PauseAction);

		JMenuItem continueItem = gameMenu.add("����");

		continueItem.addActionListener(this.ContinueAction);

		JMenuItem exitItem = gameMenu.add("�˳�");

		exitItem.addActionListener(this.ExitAction);

		JMenu modeMenu = new JMenu("ģʽ");

		JMenuItem v4Item = modeMenu.add("4����");

		v4Item.addActionListener(this.v4Action);

		JMenuItem v6Item = modeMenu.add("6����");

		v6Item.addActionListener(this.v6Action);

		JMenu helpMenu = new JMenu("����");

		JMenuItem aboutItem = helpMenu.add("����");

		aboutItem.addActionListener(this.AboutAction);

		menu.add(gameMenu);

		menu.add(modeMenu);

		menu.add(helpMenu);



		this.add(this.tetris);

		this.tetris.setFocusable(true);

	}



	static public void main(String... args) {

		TetrisApp tetrisApp = new TetrisApp();

		tetrisApp.setVisible(true);

	}



	ActionListener NewGameAction = new ActionListener() {



		@Override

		public void actionPerformed(ActionEvent e) {

			// TODO �Զ����ɵķ������

			TetrisApp.this.tetris.Initial();

		}

	};



	ActionListener PauseAction = new ActionListener() {



		@Override

		public void actionPerformed(ActionEvent e) {

			// TODO �Զ����ɵķ������

			TetrisApp.this.tetris.SetPause(true);

		}

	};



	ActionListener ContinueAction = new ActionListener() {



		@Override

		public void actionPerformed(ActionEvent e) {

			// TODO �Զ����ɵķ������

			TetrisApp.this.tetris.SetPause(false);

		}

	};



	ActionListener ExitAction = new ActionListener() {



		@Override

		public void actionPerformed(ActionEvent e) {

			// TODO �Զ����ɵķ������

			System.exit(0);

		}

	};



	ActionListener AboutAction = new ActionListener() {



		@Override

		public void actionPerformed(ActionEvent e) {

			// TODO �Զ����ɵķ������

			JOptionPane.showMessageDialog(TetrisApp.this, "Tetris Remake Ver 1.0", "����", JOptionPane.WARNING_MESSAGE);

		}

	};



	ActionListener v4Action = new ActionListener() {



		@Override

		public void actionPerformed(ActionEvent e) {

			// TODO �Զ����ɵķ������

			TetrisApp.this.tetris.SetMode("v4");

		}

	};



	ActionListener v6Action = new ActionListener() {



		@Override

		public void actionPerformed(ActionEvent e) {

			// TODO �Զ����ɵķ������

			TetrisApp.this.tetris.SetMode("v6");

		}

	};

}