package com.tjrac.domain;

import java.util.Random;
/***
 * 奖励类
 * @author wdy
 *
 */

public class Graf extends FlyingObject implements Award{
    private int xSpeed = 1;   //x坐标移动速度
    private int ySpeed = 2;   //y坐标移动速度
    private int awardType;    //奖励类型

    /** 初始化数据 */
    public Graf(){
        this.image = GamePlane.bee;
        width = image.getWidth();
        height = image.getHeight();
        y = -height;
        Random rand = new Random();
        x = rand.nextInt(GamePlane.WIDTH - width);
        awardType = rand.nextInt(2);   //初始化时给奖励
    }

    /** 获得奖励类型 */
    public int getType(){
        return awardType;
    }

    /** 越界处理 */
    @Override
    public boolean outOfBounds() {
        return y> GamePlane.HEIGHT;
    }

    /** 移动，可斜着飞 */
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