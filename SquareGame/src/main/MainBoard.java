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

	//˫���屳��ͼƬ
	Image offScreenImage = null;
	

	//��Ϸ����С
	public static final int BoardWidth=360;
	public static final int BoardHeight=600;
	public static final int ExtraWidth =180;//�����ŷ�������ʾ��Ϣ
	
	//��Ϸ��������С
	public static final int BoardArrayWidth=12;
	public static final int BoardArrayHeight=20;
	
	//�����С
	public static final int blockSize=30;
	
	//��õķ���
	public int scores=0;
	

	//�����»��ٶ�ʱ��������λ������
	private long speed=800;
	
	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	private static final long serialVersionUID = 1L;
	
	//��Ϸ�������
	public int board[][] = new int[BoardArrayHeight][BoardArrayWidth];
	
	private static MainBoard mainBoard = null ;//��Ϸ�����
	
	private Square square = null;//��ǰ����
	
	private Square next = null;//��һ������
	
	private boolean nextFlag=false;//��ǵ�����һ������
	
	public boolean isNextFlag() {
		return nextFlag;
	}

	public void setNextFlag(boolean nextFlag) {
		this.nextFlag = nextFlag;
	}
	


	
	//�ػ�ʱ����update����������û���Ϸ���ͻ�����
	public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = this.createImage(BoardWidth+ExtraWidth, BoardHeight);//��һ�Ŵ��ڴ�С��ͼƬ
		}
		Graphics gOff= offScreenImage.getGraphics();
		Color c = g.getColor();
		gOff.setColor(Color.GRAY);
		gOff.fillRect(0, 0,BoardWidth, BoardHeight);//����Ϸ���
		
		gOff.setColor(Color.LIGHT_GRAY);
		gOff.fillRect(BoardWidth, 0,ExtraWidth, BoardHeight);	//���������
		
		AttributedString ats=new AttributedString("scores: "+scores);
		ats.addAttribute(TextAttribute.SIZE, new Float(20));
		gOff.setColor(Color.black);
		gOff.drawString(ats.getIterator(), BoardWidth+5,60);//������
		
		draw(gOff);//���Ѵ��ڷ���
		gOff.setColor(c); 
		
		
		paint(gOff);//������ͼƬ������
		g.drawImage(offScreenImage, 0, 0, null);
		
	}
	
	//����ǰ����
	public void paint(Graphics g) {
		if(square!=null)
			square.draw(g);
		if(next!=null)
			next.drawPreview(g);
	}
	
	//����Ϸ���
	public void draw(Graphics g){		
		Color c = g.getColor();	
		for(int i=0;i<board.length;i++){//��ֱ����
			for(int j=0;j<board[0].length;j++){//ˮƽ����
				if(board[i][j]==1){
					//����ɫ��������
					g.setColor(Color.black);
					g.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
					g.setColor(Color.cyan);
					//����ɫС�������γɱ߿�
					g.fillRect(j * blockSize+1, i * blockSize+1, blockSize-2, blockSize-2);
					g.setColor(c);
				}
			}
		}
		g.setColor(c);
	}	
	
	public void launchFrame(){
		this.setLocation(400,100);//���ô���λ��
		this.setSize(BoardWidth+ExtraWidth, BoardHeight);//���ô��ڴ�С=��Ϸ������Ϣ���
		this.setResizable(false);
		this.setBackground(Color.LIGHT_GRAY);
		this.setVisible(true);
		this.addKeyListener(new KeyMonitor());
		
		new Thread (new Timer()).start();//����timer�߳�
		
		
		//��ʼ����Ϸ���
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
				if(nextFlag){//��ǿ��Ե�����һ������
					square=null;
					System.gc();//���ٵ�ǰ����
					square=next;//������һ��
					square.initial();//��ʼ������
					speed=800;//��������ٶ�
					
					next =null;

					next = new Square(mainBoard);//�½���һ��
					
					nextFlag=false;//��ǻ�false
				}
				if(square!=null)
					square.moveDown();//�����ƶ�
				repaint();
				try {
					Thread.sleep(speed);//ÿ��speed�����߳�ִ��һ��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}	
	}

	private class KeyMonitor extends KeyAdapter{

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_RIGHT){//�������
				square.moveRight();
				repaint();
			}
			if (e.getKeyCode()==KeyEvent.VK_LEFT){//�������
				square.moveLeft();
				repaint();
			}
			if (e.getKeyCode()==KeyEvent.VK_DOWN){//������£��ӿ��ٶ�
				speed=80;
				repaint();
			}
			
			if (e.getKeyCode()==KeyEvent.VK_UP){//������£��ӿ��ٶ�
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
