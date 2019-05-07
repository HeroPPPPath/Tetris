package com.hlju.Tetris;



import java.awt.Color;

import java.awt.Graphics;

import java.awt.Point;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;



import javax.swing.JOptionPane;

import javax.swing.JPanel;

import javax.swing.Timer;



public class Tetris extends JPanel {



	private static final long serialVersionUID = -807909536278284335L;

	private static final int BlockSize = 10;

	private static final int BlockWidth = 16;

	private static final int BlockHeigth = 26;

	private static final int TimeDelay = 1000;



	private static final String[] AuthorInfo = {

			"�����ˣ�","HelloClyde"

	};



	// ����Ѿ��̶��ķ���

	private boolean[][] BlockMap = new boolean[BlockHeigth][BlockWidth];



	// ����

	private int Score = 0;

	

	//�Ƿ���ͣ

	private boolean IsPause = false;



	// 7����״

	static boolean[][][] Shape = BlockV4.Shape;



	// ���䷽���λ��,���Ͻ�����

	private Point NowBlockPos;



	// ��ǰ�������

	private boolean[][] NowBlockMap;

	// ��һ���������

	private boolean[][] NextBlockMap;

	/**

	 * ��Χ[0,28) 7�֣�ÿ����4����ת״̬����4*7=28 %4��ȡ��ת״̬ /4��ȡ��״

	 */

	private int NextBlockState;

	private int NowBlockState;

	

	//��ʱ��

	private Timer timer;



	public Tetris() {

		// TODO �Զ����ɵĹ��캯�����

		this.Initial();

		timer = new Timer(Tetris.TimeDelay, this.TimerListener);

		timer.start();

		this.addKeyListener(this.KeyListener);

	}

	

	public void SetMode(String mode){

		if (mode.equals("v6")){

			Tetris.Shape = BlockV6.Shape;

		}

		else{

			Tetris.Shape = BlockV4.Shape;

		}

		this.Initial();

		this.repaint();

	}



	/**

	 * �µķ�������ʱ�ĳ�ʼ��

	 */

	private void getNextBlock() {

		// ���Ѿ����ɺõ���һ�η��鸳����ǰ����

		this.NowBlockState = this.NextBlockState;

		this.NowBlockMap = this.NextBlockMap;

		// �ٴ�������һ�η���

		this.NextBlockState = this.CreateNewBlockState();

		this.NextBlockMap = this.getBlockMap(NextBlockState);

		// ���㷽��λ��

		this.NowBlockPos = this.CalNewBlockInitPos();

	}

	

	/**

	 * �ж���������ķ����ǽ���Ѿ��̶��ķ����Ƿ��нӴ�

	 * @return

	 */

	private boolean IsTouch(boolean[][] SrcNextBlockMap,Point SrcNextBlockPos) {

		for (int i = 0; i < SrcNextBlockMap.length;i ++){

			for (int j = 0;j < SrcNextBlockMap[i].length;j ++){

				if (SrcNextBlockMap[i][j]){

					if (SrcNextBlockPos.y + i >= Tetris.BlockHeigth || SrcNextBlockPos.x + j < 0 || SrcNextBlockPos.x + j >= Tetris.BlockWidth){

						return true;

					}

					else{

						if (SrcNextBlockPos.y + i < 0){

							continue;

						}

						else{

							if (this.BlockMap[SrcNextBlockPos.y + i][SrcNextBlockPos.x + j]){

								return true;

							}

						}

					}

				}

			}

		}

		return false;

	}

	

	/**

	 * �̶����鵽��ͼ

	 */

	private boolean FixBlock(){

		for (int i = 0;i < this.NowBlockMap.length;i ++){

			for (int j = 0;j < this.NowBlockMap[i].length;j ++){

				if (this.NowBlockMap[i][j])

					if (this.NowBlockPos.y + i < 0)

						return false;

					else

						this.BlockMap[this.NowBlockPos.y + i][this.NowBlockPos.x + j] = this.NowBlockMap[i][j];

			}

		}

		return true;

	}

	

	/**

	 * �����´����ķ���ĳ�ʼλ��

	 * @return ��������

	 */

	private Point CalNewBlockInitPos(){

		return new Point(Tetris.BlockWidth / 2 - this.NowBlockMap[0].length / 2, - this.NowBlockMap.length);

	}



	/**

	 * ��ʼ��

	 */

	public void Initial() {

		//���Map

		for (int i = 0;i < this.BlockMap.length;i ++){

			for (int j = 0;j < this.BlockMap[i].length;j ++){

				this.BlockMap[i][j] = false;

			}

		}

		//��շ���

		this.Score = 0;

		// ��ʼ����һ�����ɵķ������һ�����ɵķ���

		this.NowBlockState = this.CreateNewBlockState();

		this.NowBlockMap = this.getBlockMap(this.NowBlockState);

		this.NextBlockState = this.CreateNewBlockState();

		this.NextBlockMap = this.getBlockMap(this.NextBlockState);

		// ���㷽��λ��

		this.NowBlockPos = this.CalNewBlockInitPos();

		this.repaint();

	}

	

	public void SetPause(boolean value){

		this.IsPause = value;

		if (this.IsPause){

			this.timer.stop();

		}

		else{

			this.timer.restart();

		}

		this.repaint();

	}



	/**

	 * ��������·���״̬

	 */

	private int CreateNewBlockState() {

		int Sum = Tetris.Shape.length * 4;

		return (int) (Math.random() * 1000) % Sum;

	}



	private boolean[][] getBlockMap(int BlockState) {

		int Shape = BlockState / 4;

		int Arc = BlockState % 4;

		System.out.println(BlockState + "," + Shape + "," + Arc);

		return this.RotateBlock(Tetris.Shape[Shape], Arc);

	}



	/**

	 * ԭ�㷨

	 * 

	 * ��ת����Map��ʹ�ü�����任,ע��Դ���󲻻ᱻ�ı�

	 * ʹ��round���doubleת����int���ȶ�ʧ���½������ȷ������

	 * 

	 * @param BlockMap

	 *            ��Ҫ��ת�ľ���

	 * @param angel

	 *            rad�Ƕȣ�Ӧ��Ϊpi/2�ı���

	 * @return ת����ɺ�ľ�������

	 

	private boolean[][] RotateBlock(boolean[][] BlockMap, double angel) {

		// ��ȡ������

		int Heigth = BlockMap.length;

		int Width = BlockMap[0].length;

		// �¾���洢���

		boolean[][] ResultBlockMap = new boolean[Heigth][Width];

		// ������ת����

		float CenterX = (Width - 1) / 2f;

		float CenterY = (Heigth - 1) / 2f;

		// ������任���λ��

		for (int i = 0; i < BlockMap.length; i++) {

			for (int j = 0; j < BlockMap[i].length; j++) {

				//�����������ת���ĵ�����

				float RelativeX = j - CenterX;

				float RelativeY = i - CenterY;

				float ResultX = (float) (Math.cos(angel) * RelativeX - Math.sin(angel) * RelativeY);

				float ResultY = (float) (Math.cos(angel) * RelativeY + Math.sin(angel) * RelativeX);

				// ������Ϣ

				//System.out.println("RelativeX:" + RelativeX + "RelativeY:" + RelativeY);

				//System.out.println("ResultX:" + ResultX + "ResultY:" + ResultY);

				

				//��������껹ԭ

				Point OrginPoint = new Point(Math.round(CenterX + ResultX), Math.round(CenterY + ResultY));

				ResultBlockMap[OrginPoint.y][OrginPoint.x] = BlockMap[i][j];

			}

		}

		return ResultBlockMap;

	}

	**/

	

	/**

	 * 

	 * @param shape 7��ͼ��֮һ

	 * @param time ��ת����

	 * @return

	 * 

	 * https://blog.csdn.net/janchin/article/details/6310654  ��ת����

	 */

	

	private boolean[][] RotateBlock(boolean[][] shape, int time) {

		if(time == 0) {

			return shape;

		}

		int heigth = shape.length;

		int width = shape[0].length;

		boolean[][] ResultMap = new boolean[heigth][width];

		int tmpH = heigth - 1, tmpW = 0;

		for(int i = 0; i < heigth && tmpW < width; i++) {

			for(int j = 0; j < width && tmpH > -1; j++) {

				ResultMap[i][j] = shape[tmpH][tmpW];

				tmpH--;

			}

			tmpH = heigth - 1;

			tmpW++;

		}

		for(int i = 1; i < time; i++) {

			ResultMap = RotateBlock(ResultMap, 0);

		}

		return ResultMap;

	}



	/**

	 * ���Է�����������ת����

	 * 

	 * @param args

	 */

	static public void main(String... args) {

		boolean[][] SrcMap = Tetris.Shape[3];

		Tetris.ShowMap(SrcMap);

		/*

		for (int i = 0;i < 7;i ++){

			System.out.println(i);

			Tetris.ShowMap(Tetris.Shape[i]);

		}

		*/

		

		Tetris tetris = new Tetris();

		boolean[][] result = tetris.RotateBlock(SrcMap, 1);

		Tetris.ShowMap(result);

		

	}

	

	/**

	 * ���Է�������ʾ����

	 * @param SrcMap

	 */

	static private void ShowMap(boolean[][] SrcMap){

		System.out.println("-----");

		for (int i = 0;i < SrcMap.length;i ++){

			for (int j = 0;j < SrcMap[i].length;j ++){

				if (SrcMap[i][j])

					System.out.print("*");

				else

					System.out.print(" ");

			}

			System.out.println();

		}

		System.out.println("-----");

	}



	/**

	 * ������Ϸ����

	 */

	@Override

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// ��ǽ

		for (int i = 0; i < Tetris.BlockHeigth + 1; i++) {

			g.drawRect(0 * Tetris.BlockSize, i * Tetris.BlockSize, Tetris.BlockSize, Tetris.BlockSize);

			g.drawRect((Tetris.BlockWidth + 1) * Tetris.BlockSize, i * Tetris.BlockSize, Tetris.BlockSize,

					Tetris.BlockSize);

		}

		for (int i = 0; i < Tetris.BlockWidth; i++) {

			g.drawRect((1 + i) * Tetris.BlockSize, Tetris.BlockHeigth * Tetris.BlockSize, Tetris.BlockSize,

					Tetris.BlockSize);

		}

		// ����ǰ����

		for (int i = 0; i < this.NowBlockMap.length; i++) {

			for (int j = 0; j < this.NowBlockMap[i].length; j++) {

				if (this.NowBlockMap[i][j])

					g.fillRect((1 + this.NowBlockPos.x + j) * Tetris.BlockSize, (this.NowBlockPos.y + i) * Tetris.BlockSize,

						Tetris.BlockSize, Tetris.BlockSize);

			}

		}

		// ���Ѿ��̶��ķ���

		for (int i = 0; i < Tetris.BlockHeigth; i++) {

			for (int j = 0; j < Tetris.BlockWidth; j++) {

				if (this.BlockMap[i][j])

					g.fillRect(Tetris.BlockSize + j * Tetris.BlockSize, i * Tetris.BlockSize, Tetris.BlockSize,

						Tetris.BlockSize);

			}

		}

		//������һ������

		for (int i = 0;i < this.NextBlockMap.length;i ++){

			for (int j = 0;j < this.NextBlockMap[i].length;j ++){

				if (this.NextBlockMap[i][j])

					g.fillRect(190 + j * Tetris.BlockSize, 30 + i * Tetris.BlockSize, Tetris.BlockSize, Tetris.BlockSize);

			}

		}

		// ����������Ϣ

		g.drawString("��Ϸ����:" + this.Score, 190, 10);

		for (int i = 0;i < Tetris.AuthorInfo.length;i ++){

			g.drawString(Tetris.AuthorInfo[i], 190, 100 + i * 20);

		}

		

		//������ͣ

		if (this.IsPause){

			g.setColor(Color.white);

			g.fillRect(70, 100, 50, 20);

			g.setColor(Color.black);

			g.drawRect(70, 100, 50, 20);

			g.drawString("PAUSE", 75, 113);

		}

	}

	/**

	 * 

	 * @return

	 */

	private int ClearLines(){

		int lines = 0;

		for (int i = 0;i < this.BlockMap.length;i ++){

			boolean IsLine = true;

			for (int j = 0;j < this.BlockMap[i].length;j ++){

				if (!this.BlockMap[i][j]){

					IsLine = false;

					break;

				}

			}

			if (IsLine){

				for (int k = i;k > 0;k --){

					this.BlockMap[k] = this.BlockMap[k - 1];

				}

				this.BlockMap[0] = new boolean[Tetris.BlockWidth];

				lines ++;

			}

		}

		return lines;

	}

	

	// ��ʱ������

	ActionListener TimerListener = new ActionListener() {

		

		@Override

		public void actionPerformed(ActionEvent arg0) {

			// TODO �Զ����ɵķ������

			if (Tetris.this.IsTouch(Tetris.this.NowBlockMap, new Point(Tetris.this.NowBlockPos.x, Tetris.this.NowBlockPos.y + 1))){

				if (Tetris.this.FixBlock()){

					Tetris.this.Score += Tetris.this.ClearLines() * 10;

					Tetris.this.getNextBlock();

				}

				else{

					JOptionPane.showMessageDialog(Tetris.this.getParent(), "GAME OVER");

					Tetris.this.Initial();

				}

			}

			else{

				Tetris.this.NowBlockPos.y ++;

			}

			Tetris.this.repaint();

		}

	};

	

	//��������

	java.awt.event.KeyListener KeyListener = new java.awt.event.KeyListener(){



		@Override

		public void keyPressed(KeyEvent e) {

			// TODO �Զ����ɵķ������

			if (!IsPause){

				Point DesPoint;

				switch (e.getKeyCode()) {

				case KeyEvent.VK_DOWN:

					DesPoint = new Point(Tetris.this.NowBlockPos.x, Tetris.this.NowBlockPos.y + 1);

					if (!Tetris.this.IsTouch(Tetris.this.NowBlockMap, DesPoint)){

						Tetris.this.NowBlockPos = DesPoint;

					}

					break;

				case KeyEvent.VK_UP:

					boolean[][] TurnBlock = Tetris.this.RotateBlock(Tetris.this.NowBlockMap,1);

					if (!Tetris.this.IsTouch(TurnBlock, Tetris.this.NowBlockPos)){

						Tetris.this.NowBlockMap = TurnBlock;

					}

					break;

				case KeyEvent.VK_RIGHT:

					DesPoint = new Point(Tetris.this.NowBlockPos.x + 1, Tetris.this.NowBlockPos.y);

					if (!Tetris.this.IsTouch(Tetris.this.NowBlockMap, DesPoint)){

						Tetris.this.NowBlockPos = DesPoint;

					}

					break;

				case KeyEvent.VK_LEFT:

					DesPoint = new Point(Tetris.this.NowBlockPos.x - 1, Tetris.this.NowBlockPos.y);

					if (!Tetris.this.IsTouch(Tetris.this.NowBlockMap, DesPoint)){

						Tetris.this.NowBlockPos = DesPoint;

					}

					break;

				}

				//System.out.println(Tetris.this.NowBlockPos);

				repaint();

			}

		}



		@Override

		public void keyReleased(KeyEvent e) {

			// TODO �Զ����ɵķ������

			

		}



		@Override

		public void keyTyped(KeyEvent e) {

			// TODO �Զ����ɵķ������

			

		}

		

	};

}