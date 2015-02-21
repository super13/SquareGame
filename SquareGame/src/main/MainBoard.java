/**
 * 
 */
package main;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.text.AttributedString;


/**
 * @author super13
 * @version 0.1
 */
public class MainBoard extends Frame{

	//双缓冲背后图片
	Image offScreenImage = null;
	

	//游戏面板大小
	public static final int BoardWidth=360;
	public static final int BoardHeight=600;
	public static final int ExtraWidth =180;//用来放分数和提示信息
	
	//游戏面板数组大小
	public static final int BoardArrayWidth=12;
	public static final int BoardArrayHeight=20;
	
	//方块大小
	public static final int blockSize=30;
	
	//获得的分数
	public int scores=0;
	

	//方块下滑速度时间间隔，单位，毫秒
	private long speed=800;
	
	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	private static final long serialVersionUID = 1L;
	
	//游戏面板数组
	public int board[][] = new int[BoardArrayHeight][BoardArrayWidth];
	
	private static MainBoard mainBoard = null ;//游戏主面板
	
	private Square square = null;//当前方块
	
	private Square next = null;//下一个方块
	
	private boolean nextFlag=false;//标记掉下下一个方块
	
	public boolean isNextFlag() {
		return nextFlag;
	}

	public void setNextFlag(boolean nextFlag) {
		this.nextFlag = nextFlag;
	}
	


	
	//重画时调用update，从里面调用画游戏面板和画方块
	public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = this.createImage(BoardWidth+ExtraWidth, BoardHeight);//画一张窗口大小的图片
		}
		Graphics gOff= offScreenImage.getGraphics();
		Color c = g.getColor();
		gOff.setColor(Color.GRAY);
		gOff.fillRect(0, 0,BoardWidth, BoardHeight);//画游戏面板
		
		gOff.setColor(Color.LIGHT_GRAY);
		gOff.fillRect(BoardWidth, 0,ExtraWidth, BoardHeight);	//画分数面板
		
		AttributedString ats=new AttributedString("scores: "+scores);
		ats.addAttribute(TextAttribute.SIZE, new Float(20));
		gOff.setColor(Color.black);
		gOff.drawString(ats.getIterator(), BoardWidth+5,60);//画分数
		
		draw(gOff);//画已存在方块
		gOff.setColor(c); 
		
		
		paint(gOff);//画这张图片到窗口
		g.drawImage(offScreenImage, 0, 0, null);
		
	}
	
	//画当前方块
	public void paint(Graphics g) {
		if(square!=null)
			square.draw(g);
		if(next!=null)
			next.drawPreview(g);
	}
	
	//画游戏面板
	public void draw(Graphics g){		
		Color c = g.getColor();	
		for(int i=0;i<board.length;i++){//竖直方向
			for(int j=0;j<board[0].length;j++){//水平方向
				if(board[i][j]==1){
					//画黑色大正方形
					g.setColor(Color.black);
					g.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
					g.setColor(Color.cyan);
					//画绿色小正方形形成边框
					g.fillRect(j * blockSize+1, i * blockSize+1, blockSize-2, blockSize-2);
					g.setColor(c);
				}
			}
		}
		g.setColor(c);
	}	
	
	public void launchFrame(){
		this.setLocation(400,100);//设置窗口位置
		this.setSize(BoardWidth+ExtraWidth, BoardHeight);//设置窗口大小=游戏面板加信息面板
		this.setResizable(false);
		this.setBackground(Color.LIGHT_GRAY);
		this.setVisible(true);
		this.addKeyListener(new KeyMonitor());
		
		new Thread (new Timer()).start();//启动timer线程
		
		
		//初始化游戏面板
		for(int i=0;i<board.length;i++)
			for(int j=0;j<board[0].length;j++)
				board[i][j]=0;
		
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}		
		});

		if(square==null){
			
			square=new Square(this);
			square.initial();
		}
		if(next==null)
			next=new Square(this);	
	}
	
	private class Timer implements Runnable{
		public void run() {
			while (true){
				if(nextFlag){//标记可以掉下下一个方块
					square=null;
					System.gc();//销毁当前方块
					square=next;//等于下一个
					square.initial();//初始化方块
					speed=800;//设回正常速度
					
					next =null;

					next = new Square(mainBoard);//新建下一个
					
					nextFlag=false;//标记回false
				}
				if(square!=null)
					square.moveDown();//向下移动
				repaint();
				try {
					Thread.sleep(speed);//每隔speed毫秒线程执行一次
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}	
	}

	private class KeyMonitor extends KeyAdapter{

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_RIGHT){//方向键右
				square.moveRight();
				repaint();
			}
			if (e.getKeyCode()==KeyEvent.VK_LEFT){//方向键左
				square.moveLeft();
				repaint();
			}
			if (e.getKeyCode()==KeyEvent.VK_DOWN){//方向键下，加快速度
				speed=80;
				repaint();
			}
			
			if (e.getKeyCode()==KeyEvent.VK_UP){//方向键下，加快速度
				square.changeShape();
				repaint();
			}
		}
		
	}
	
	public static void main(String args[]){
		mainBoard=new MainBoard();
		mainBoard.launchFrame();
	}
	
	
}
