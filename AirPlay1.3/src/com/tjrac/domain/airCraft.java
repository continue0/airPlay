package com.tjrac.domain;

import java.util.Random;

/**
	 * 敌飞机: 是飞行物，也是敌人
	 */
public class airCraft extends FlyingObject implements Enemy {
	    private int speed = 3;  //移动步骤

	    /** 初始化数据 */
	    public airCraft(){
	        this.image = GamePlane.airplane;
	        width = image.getWidth();
	        height = image.getHeight();
	        y = -height;          
	        Random rand = new Random();
	        x = rand.nextInt(GamePlane.WIDTH - width);
	    }

	    /** 获取分数 */
	    @Override
	    public int getScore() {  
	        return 5;
	    }

	    /** //越界处理 */
	    @Override
	    public  boolean outOfBounds() {   
	        return y>GamePlane.HEIGHT;
	    }

	    /** 移动 */
	    @Override
	    public void step() {   
	        y += speed;
	    }


		/** 改变移动速度 */
		@Override
		public void addstep(int score) { 
		    if(score<150)
		    	y += speed;
		    else if(score>=150 && score < 300){
		    	speed = 2*speed;
		        y += speed;}
		    else if(score>=250 && score < 450){
		     	speed = 3*speed;
		        y += speed;}
        }
}





