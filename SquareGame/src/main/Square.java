
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
	private int x;//方块坐标x 与board[height][width]的width挂钩
	private int y;//方块坐标y 与board[height][width]的height挂钩
	private int blockSize;//方块大小
	private int boardArrayWidth;//游戏面板x方向多少格子
	private int boardArrayHeight;//游戏面板y方向多少格子
	private MainBoard mainBoard;
	

	//添加方块需要添加变形数组和形状
	
	//方块变形数组
	int nextShape[]={1,0,2,4,5,6,3,8,9,10,7};
	
	//方块形状
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
		

	//取随机数对象
	private Random random =new Random();
	
	//本方块在shapes中的序号
	private int index;	
	
	//初始化方块位置
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
	//初始化方块
	public void initial(){
	
		for(int i=0;i<body.length;i++)
			for(int j=0;j<body[0].length;j++){
				if(body[i][j]==1){
					this.mainBoard.board[y+i][x+j]=body[i][j];		
				}
			}	
		
	}
	
	//画方块
	public void draw(Graphics g){		
		Color c = g.getColor();	
		for(int i=0;i<body.length;i++)//竖直方向
			for(int j=0;j<body[0].length;j++){//水平方向
				if(body[i][j]==1){
					//画黑色大正方形
					g.fillRect((this.x+j) * blockSize, (this.y+i) * blockSize, blockSize, blockSize);
					g.setColor(Color.GREEN);
					//画绿色小正方形形成边框
					g.fillRect((this.x+j) * blockSize+1, (this.y+i) * blockSize+1, blockSize-2, blockSize-2);
					g.setColor(c);
				}
			}
		g.setColor(c);
	}	

	public void drawPreview(Graphics g){		
		Color c = g.getColor();	
		for(int i=0;i<body.length;i++)//竖直方向
			for(int j=0;j<body[0].length;j++){//水平方向
				if(body[i][j]==1){
					//画黑色大正方形
					g.fillRect((boardArrayWidth+1+j) * blockSize, (5+i) * blockSize, blockSize, blockSize);
					g.setColor(Color.GREEN);
					//画绿色小正方形形成边框
					g.fillRect((boardArrayWidth+1+j) * blockSize+1, (5+i) * blockSize+1, blockSize-2, blockSize-2);
					g.setColor(c);
				}
			}
		g.setColor(c);
	}	
	
	//向上键改变 方块形状
	public void changeShape(){
		int t=nextShape[index];//变形的下一个方块序号
		if(!isChangeCrash(t)){//变形后不碰撞
			
			//把当前的方块在游戏面板消掉
			for(int i=0;i<body.length;i++){
				for(int j=0;j<body[0].length;j++){
					if(body[i][j]==1){
						this.mainBoard.board[y+i][x+j]=0;
					}
				}	
			}
			
			//当前的方块变成变形后的序号和值
			index=t;
			System.arraycopy(shapes[t], 0, body, 0, body.length);
			
			//在游戏面板上放变形后的方块
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
		int tempboard[][]=new int[boardArrayHeight][boardArrayWidth];//临时游戏面板		
		for(int i=0;i<mainBoard.board.length;i++)
			System.arraycopy(mainBoard.board[i], 0, tempboard[i], 0, mainBoard.board[i].length);
		
		//模拟把当前方块在游戏面板消掉
		for(int i=0;i<body.length;i++){
			for(int j=0;j<body[0].length;j++){
				if(body[i][j]==1){
					tempboard[y+i][x+j]=0;
				}
			}
		}
		
		//模拟加入变化后的方块
		for(int i=0;i<shapes[nextIndex].length;i++){
			for(int j=0;j<shapes[nextIndex][0].length;j++){
				if(shapes[nextIndex][i][j]==1){
					if(y+i<0||x+j<0||x+j>=boardArrayWidth||y+i>=boardArrayHeight){
						//与边界有碰撞
						return true;
					}	
					tempboard[y+i][x+j]+=1;
					if(tempboard[y+i][x+j]==2){
						//与现在方块有冲突
						return true;
					}
				}
			}	
		}
		return false;
	}
	
	
	/*
	 * 移动方块
	 */
	//向下
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
		}else{//else表示方块到达底部，
			mainBoard.setNextFlag(true);//标记可以掉下下个方块
			mainBoard.scores+= delLine();
		}
	}
	
	//向左
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
	
	//向右
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
	
	//检测是否可以消行,有则消行
	
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
			//rowflag为可以消掉的行
			if(rowFlag!=-1){
				//消掉rowfalg行
				for(int j=0;j<mainBoard.board[0].length;j++){	
					mainBoard.board[rowFlag][j]=0;
				}
				score=score*2+1;//分数多行则高分
				
				//rowflag-1行全部前进1
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
	
	
	//检测移动是否碰撞,true为碰撞
	//dir 0表示向下，1表示左，2表示右
	public boolean isMoveCrash(int x1,int y1,int dir){
		int tempboard[][]=new int[boardArrayHeight][boardArrayWidth];
		for(int i=0;i<mainBoard.board.length;i++)
			System.arraycopy(mainBoard.board[i], 0, tempboard[i], 0, mainBoard.board[i].length);
		
		int x2=x1;
		int y2=y1;
		switch (dir){
			case 0://向下
				y2=y1+1;
				break;
			case 1://向左
				x2=x1-1;
				break;
			case 2://向右
				x2=x1+1;
				break;
		}		
			//进行移动，方块数组和游戏版面数组相加
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
		//如果相加结果有2则表明相撞
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
