package com.tjrac.domain;

import java.util.Random;
/***
 * ������
 * @author wdy
 *
 */

public class Graf extends FlyingObject implements Award{
    private int xSpeed = 1;   //x�����ƶ��ٶ�
    private int ySpeed = 2;   //y�����ƶ��ٶ�
    private int awardType;    //��������

    /** ��ʼ������ */
    public Graf(){
        this.image = GamePlane.bee;
        width = image.getWidth();
        height = image.getHeight();
        y = -height;
        Random rand = new Random();
        x = rand.nextInt(GamePlane.WIDTH - width);
        awardType = rand.nextInt(2);   //��ʼ��ʱ������
    }

    /** ��ý������� */
    public int getType(){
        return awardType;
    }

    /** Խ�紦�� */
    @Override
    public boolean outOfBounds() {
        return y> GamePlane.HEIGHT;
    }

    /** �ƶ�����б�ŷ� */
    @Override
    public void step() {      
        x += xSpeed;
        y += ySpeed;
        if(x > GamePlane.WIDTH-width){  
            xSpeed = -1;
        }
        if(x < 0){
            xSpeed = 1;
        }
    }
}