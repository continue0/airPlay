package com.tjrac.domain;

import java.util.Random;

/**
	 * �зɻ�: �Ƿ����Ҳ�ǵ���
	 */
public class airCraft extends FlyingObject implements Enemy {
	    private int speed = 3;  //�ƶ�����

	    /** ��ʼ������ */
	    public airCraft(){
	        this.image = GamePlane.airplane;
	        width = image.getWidth();
	        height = image.getHeight();
	        y = -height;          
	        Random rand = new Random();
	        x = rand.nextInt(GamePlane.WIDTH - width);
	    }

	    /** ��ȡ���� */
	    @Override
	    public int getScore() {  
	        return 5;
	    }

	    /** //Խ�紦�� */
	    @Override
	    public  boolean outOfBounds() {   
	        return y>GamePlane.HEIGHT;
	    }

	    /** �ƶ� */
	    @Override
	    public void step() {   
	        y += speed;
	    }


		/** �ı��ƶ��ٶ� */
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





