package com.tjrac.domain;

/**
 * 敌人，可以有分数
 */
public interface Enemy {
    /** 敌人的分数  */
    int getScore();
    //增加速度
	void addstep(int score);
}


