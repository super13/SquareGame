
package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * @author super13
 * @version 0.1
 */


public class Square {
	private int body[][]=new int [4][4];
	private int x;//��������x ��board[height][width]��width�ҹ�
	private int y;//��������y ��board[height][width]��height�ҹ�
	private int blockSize;//�����С
	private int boardArrayWidth;//��Ϸ���x������ٸ���
	private int boardArrayHeight;//��Ϸ���y������ٸ���
	private MainBoard mainBoard;
	

	//��ӷ�����Ҫ��ӱ����������״
	
	//�����������
	int nextShape[]={1,0,2,4,5,6,3,8,9,10,7};
	
	//������״
	public int shapes[][][]={
			{{0,1,0,0},
		     {0,1,0,0},
		     {0,1,0,0},
		     {0,1,0,0}},
		     
			{{0,0,0,0},
		     {1,1,1,1},
		     {0,0,0,0},
		     {0,0,0,0}},
		     
			{{0,1,1,0},
		     {0,1,1,0},
		     {0,0,0,0},
		     {0,0,0,0}},
		     
			{{0,1,0,0},
		     {0,1,0,0},
		     {0,1,1,0},
		     {0,0,0,0}},
		     
			{{0,0,1,0},
		     {1,1,1,0},
		     {0,0,0,0},
		     {0,0,0,0}},
		     
			{{0,1,1,0},
		     {0,0,1,0},
		     {0,0,1,0},
		     {0,0,0,0}},
		     
			{{1,1,1,0},
		     {1,0,0,0},
		     {0,0,0,0},
		     {0,0,0,0}},
		     
			{{1,1,1,0},
		     {0,0,1,0},
			 {0,0,0,0},
			 {0,0,0,0}},
			 
			{{0,1,1,0},
		     {0,1,0,0},
			 {0,1,0,0},
			 {0,0,0,0}},
			 
			{{1,0,0,0},
		     {1,1,1,0},
			 {0,0,0,0},
			 {0,0,0,0}},
			 
			{{0,1,0,0},
		     {0,1,0,0},
			 {1,1,0,0},
			 {0,0,0,0}}
			 
		 };
		

	//ȡ���������
	private Random random =new Random();
	
	//��������shapes�е����
	private int index;	
	
	//��ʼ������λ��
	public Square(MainBoard mainBoard){
		this.blockSize=MainBoard.blockSize;
		this.boardArrayHeight=MainBoard.BoardArrayHeight;
		this.boardArrayWidth=MainBoard.BoardArrayWidth;
		this.mainBoard=mainBoard;
		this.x=boardArrayWidth/2-1;
		this.y=0;
		index=random.nextInt(shapes.length);
		System.arraycopy(shapes[index], 0, body, 0, body.length);		
	}
	//��ʼ������
	public void initial(){
	
		for(int i=0;i<body.length;i++)
			for(int j=0;j<body[0].length;j++){
				if(body[i][j]==1){
					this.mainBoard.board[y+i][x+j]=body[i][j];		
				}
			}	
		
	}
	
	//������
	public void draw(Graphics g){		
		Color c = g.getColor();	
		for(int i=0;i<body.length;i++)//��ֱ����
			for(int j=0;j<body[0].length;j++){//ˮƽ����
				if(body[i][j]==1){
					//����ɫ��������
					g.fillRect((this.x+j) * blockSize, (this.y+i) * blockSize, blockSize, blockSize);
					g.setColor(Color.GREEN);
					//����ɫС�������γɱ߿�
					g.fillRect((this.x+j) * blockSize+1, (this.y+i) * blockSize+1, blockSize-2, blockSize-2);
					g.setColor(c);
				}
			}
		g.setColor(c);
	}	

	public void drawPreview(Graphics g){		
		Color c = g.getColor();	
		for(int i=0;i<body.length;i++)//��ֱ����
			for(int j=0;j<body[0].length;j++){//ˮƽ����
				if(body[i][j]==1){
					//����ɫ��������
					g.fillRect((boardArrayWidth+1+j) * blockSize, (5+i) * blockSize, blockSize, blockSize);
					g.setColor(Color.GREEN);
					//����ɫС�������γɱ߿�
					g.fillRect((boardArrayWidth+1+j) * blockSize+1, (5+i) * blockSize+1, blockSize-2, blockSize-2);
					g.setColor(c);
				}
			}
		g.setColor(c);
	}	
	
	//���ϼ��ı� ������״
	public void changeShape(){
		int t=nextShape[index];//���ε���һ���������
		if(!isChangeCrash(t)){//���κ���ײ
			
			//�ѵ�ǰ�ķ�������Ϸ�������
			for(int i=0;i<body.length;i++){
				for(int j=0;j<body[0].length;j++){
					if(body[i][j]==1){
						this.mainBoard.board[y+i][x+j]=0;
					}
				}	
			}
			
			//��ǰ�ķ����ɱ��κ����ź�ֵ
			index=t;
			System.arraycopy(shapes[t], 0, body, 0, body.length);
			
			//����Ϸ����Ϸű��κ�ķ���
			for(int i=0;i<body.length;i++){
				for(int j=0;j<body[0].length;j++){
					if(body[i][j]==1){
						this.mainBoard.board[y+i][x+j]=1;
					}
				}	
			}
		
		}
	}
	
	public boolean isChangeCrash(int nextIndex){
		int tempboard[][]=new int[boardArrayHeight][boardArrayWidth];//��ʱ��Ϸ���		
		for(int i=0;i<mainBoard.board.length;i++)
			System.arraycopy(mainBoard.board[i], 0, tempboard[i], 0, mainBoard.board[i].length);
		
		//ģ��ѵ�ǰ��������Ϸ�������
		for(int i=0;i<body.length;i++){
			for(int j=0;j<body[0].length;j++){
				if(body[i][j]==1){
					tempboard[y+i][x+j]=0;
				}
			}
		}
		
		//ģ�����仯��ķ���
		for(int i=0;i<shapes[nextIndex].length;i++){
			for(int j=0;j<shapes[nextIndex][0].length;j++){
				if(shapes[nextIndex][i][j]==1){
					if(y+i<0||x+j<0||x+j>=boardArrayWidth||y+i>=boardArrayHeight){
						//��߽�����ײ
						return true;
					}	
					tempboard[y+i][x+j]+=1;
					if(tempboard[y+i][x+j]==2){
						//�����ڷ����г�ͻ
						return true;
					}
				}
			}	
		}
		return false;
	}
	
	
	/*
	 * �ƶ�����
	 */
	//����
	public void moveDown() {	
	/*	System.out.println("----------------------------------------------------");
		for(int i1=0;i1<mainBoard.board.length;i1++){
			for(int j1=0;j1<mainBoard.board[0].length;j1++)
				System.out.print(mainBoard.board[i1][j1]+" ");
			System.out.println();
		}		
		
		System.out.println("----------------------------------------------------");
		
		*/
		if(!isMoveCrash(this.x, this.y,0)){
			for(int i=0;i<body.length;i++){
				for(int j=0;j<body[0].length;j++){
					if(body[i][j]==1){					
						mainBoard.board[y+i][x+j]-=1;
						mainBoard.board[y+1+i][x+j]+=1;
					}
				}	
			}	

			this.y += 1;
		}else{//else��ʾ���鵽��ײ���
			mainBoard.setNextFlag(true);//��ǿ��Ե����¸�����
			mainBoard.scores+= delLine();
		}
	}
	
	//����
	public void moveLeft(){
		if(!isMoveCrash(this.x, this.y,1)){
			for(int i=0;i<body.length;i++){
				for(int j=0;j<body[0].length;j++){
					if(body[i][j]==1){					
						mainBoard.board[y+i][x+j]-=1;
						mainBoard.board[y+i][x-1+j]+=1;
					}
				}	
			}	

			this.x -= 1;

		}
	}
	
	//����
	public void moveRight(){
		if(!isMoveCrash(this.x, this.y,2)){
			for(int i=0;i<body.length;i++){
				for(int j=0;j<body[0].length;j++){
					if(body[i][j]==1){					
						mainBoard.board[y+i][x+j]-=1;
						mainBoard.board[y+i][x+1+j]+=1;
					}
				}	
			}	

			this.x += 1;
		

		}
	}
	
	//����Ƿ��������,��������
	
	public int delLine(){
		int score=0;
		int rowFlag=-1;
		for(int i=0;i<mainBoard.board.length;i++){
			for(int j=0;j<mainBoard.board[0].length;j++){
				if(mainBoard.board[i][j]==0){
					rowFlag=-1;
					break;
				}else{
					rowFlag=i;
				}
			}
			//rowflagΪ������������
			if(rowFlag!=-1){
				//����rowfalg��
				for(int j=0;j<mainBoard.board[0].length;j++){	
					mainBoard.board[rowFlag][j]=0;
				}
				score=score*2+1;//����������߷�
				
				//rowflag-1��ȫ��ǰ��1
				for(int r=0;r<rowFlag;r++){
					for(int j=0;j<mainBoard.board[0].length;j++){					
						if(mainBoard.board[r][j]!=0){
							mainBoard.board[r][j]-=1;
							mainBoard.board[r+1][j]+=1;
						}
					}
				}
			}
		
		}		
		return score;		
	}
	
	
	//����ƶ��Ƿ���ײ,trueΪ��ײ
	//dir 0��ʾ���£�1��ʾ��2��ʾ��
	public boolean isMoveCrash(int x1,int y1,int dir){
		int tempboard[][]=new int[boardArrayHeight][boardArrayWidth];
		for(int i=0;i<mainBoard.board.length;i++)
			System.arraycopy(mainBoard.board[i], 0, tempboard[i], 0, mainBoard.board[i].length);
		
		int x2=x1;
		int y2=y1;
		switch (dir){
			case 0://����
				y2=y1+1;
				break;
			case 1://����
				x2=x1-1;
				break;
			case 2://����
				x2=x1+1;
				break;
		}		
			//�����ƶ��������������Ϸ�����������
		for(int i=0;i<body.length;i++){
			for(int j=0;j<body[0].length;j++){
				if(body[i][j]==1){					
					tempboard[y1+i][x1+j]-=1;
					if(y2+i<0||x2+j<0||x2+j>=boardArrayWidth||y2+i>=boardArrayHeight)
						return true;
					tempboard[y2+i][x2+j]+=1;
				}
			}	
		}
		//�����ӽ����2�������ײ
		for(int i=0;i<body.length;i++){
			for(int j=0;j<body[0].length;j++){
				if(body[i][j]==1){
					if(y2+i<0||x2+j<0||x2+j>=boardArrayWidth||y2+i>=boardArrayHeight){						
						return true;
					}				

					if(tempboard[y2+i][x2+j]==2){
						return true;
					}
				}
			}	
		}		
		return false;
	}
}
