package com.tjrac.domain;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GamePlane  extends JPanel { 

	public static final int WIDTH = 400; // ����
    public static final int HEIGHT = 700; // ����
    /** ��Ϸ�ĵ�ǰ״̬: START RUNNING PAUSE GAME_OVER */
    private int state;
    private static final int START = 0;
    private static final int RUNNING = 1;
    private static final int PAUSE = 2;
    private static final int GAME_OVER = 3;

    static int score = 0; // �÷�
    private Timer timer; // ��ʱ��
    private int intervel = 1000 / 100; // ʱ����(����)
    //ͼƬ
    public static BufferedImage background;
    public static BufferedImage start;
    public static BufferedImage airplane;
    public static BufferedImage bee;
    public static BufferedImage bullet_u;
    public static BufferedImage hero0;
    public static BufferedImage hero1;
    public static BufferedImage pause;
    public static BufferedImage gameover;
    public static BufferedImage blast_3;
   // public static BufferedImage airplane0;

    private FlyingObject[] flyings = {}; // �л�����
    private Bullet[] bullets = {}; // �ӵ�����
    private myPlane myplane = new myPlane(); // �ҷ���
    static { // ��̬����飬��ʼ��ͼƬ��Դ
        try {
            background = ImageIO.read(myPlane.class.getResource("background1.jpg"));
            start = ImageIO.read(myPlane.class.getResource("start.jpg"));
            airplane = ImageIO.read(myPlane.class.getResource("airplane0.png"));
           // airplane0 = ImageIO.read(myPlane.class.getResource("airplane.png"));
            bee = ImageIO.read(myPlane.class.getResource("graf.png"));
            bullet_u = ImageIO.read(myPlane.class.getResource("bullet_u.gif"));
            hero0 = ImageIO.read(myPlane.class.getResource("hero0.png"));
            hero1 = ImageIO.read(myPlane.class.getResource("hero1.png"));
            pause = ImageIO.read(myPlane.class.getResource("pause1.png"));
            gameover = ImageIO.read(myPlane.class.getResource("gameover0.bmp"));
            blast_3 = ImageIO.read(myPlane.class.getResource("blast_3.gif"));
        } catch (Exception e) {
            e.printStackTrace();
        }
     
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fly");
        GamePlane game = new GamePlane(); // ������
        frame.add(game); // ��������ӵ�JFrame��
        frame.setSize(WIDTH, HEIGHT); // ���ô�С
        frame.setAlwaysOnTop(true); // ��������������
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ĭ�Ϲرղ���
       // frame.setIconImage(new ImageIcon("images/icon.jpg").getImage()); // ���ô����ͼ��
        frame.setLocationRelativeTo(null); // ���ô����ʼλ��
        frame.setVisible(true); // �������paint
       
        game.action(); // ����ִ��
    }
   
    /** �� */
    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null); // ������ͼ
        paintHero(g); // ��Ӣ�ۻ�
        paintBullets(g); // ���ӵ�
        paintFlyingObjects(g); // ��������
        paintScore(g); // ������
        paintState(g); // ����Ϸ״̬
    }

    /** ���ҷ��ɻ� */
    public void paintHero(Graphics g) {
        g.drawImage(myplane.getImage(), myplane.getX(), myplane.getY(), null);
    }

    /** ���ӵ� */
    public void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(),
                    null);
        }
    }

    /** �������� */
    public void paintFlyingObjects(Graphics g) {
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            g.drawImage(f.getImage(), f.getX(), f.getY(), null);
            
        }
    }
    

    /** ������ */
    public void paintScore(Graphics g) {
        int x = 10; // x����
        int y = 25; // y����
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22); // ����
        g.setColor(new Color(0xFF0000));
        g.setFont(font); // ��������
        g.drawString("SCORE:" + score, x, y); // ������
        x += 290; // x������290
        g.drawString("LIFE:" + myplane.getLife(), x, y); // ����
    }

    /** ����Ϸ״̬ */
    public void paintState(Graphics g) {
        switch (state) {
        case START: // ����״̬
            g.drawImage(start, 0, 0, null);
            break;
        case PAUSE: // ��ͣ״̬
            g.drawImage(pause, 0, 0, null);
            break;
        case GAME_OVER: // ��Ϸ��ֹ״̬
            g.drawImage(gameover, 0, 0, null);
            break;
        }
    }


    /** ����ִ�д��� */
    public void action() {

        // �������¼�
        MouseAdapter l = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) { // ����ƶ�
                if (state == RUNNING) { // ����״̬���ƶ�Ӣ�ۻ�--�����λ��
                    int x = e.getX();
                    int y = e.getY();
                    myplane.moveTo(x, y);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) { // ������
                if (state == PAUSE) { // ��ͣ״̬������
                    state = RUNNING;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) { // ����˳�
                if (state != GAME_OVER&&state!=START) { // ��Ϸδ��������������Ϊ��ͣ
                    state = PAUSE;
                }
            	if (state == RUNNING) { // ��Ϸδ��������������Ϊ��ͣ 
                    state = PAUSE; 
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) { // �����
                switch (state) {
                case START:
                    state = RUNNING; // ����״̬������
                    break;
                case GAME_OVER: // ��Ϸ�����������ֳ�
                    flyings = new FlyingObject[0]; // ��շ�����
                    bullets = new Bullet[0]; // ����ӵ�
                    myplane = new myPlane(); // ���´����һ�
                    score = 0; // ��ճɼ�
                    state = START; // ״̬����Ϊ����
                    break;
                }
            }
        };
        this.addMouseListener(l); // �������������
        this.addMouseMotionListener(l); // ������껬������

        timer = new Timer(); // �����̿���
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (state == RUNNING) { // ����״̬
                    enterAction(); // �������볡
                    stepAction(); // ��һ��
                    shootAction(); // Ӣ�ۻ����
                    bangAction(); // �ӵ��������
                    outOfBoundsAction(); // ɾ��Խ������Ｐ�ӵ�
                    checkGameOverAction(); // �����Ϸ����
                }
                repaint(); // �ػ棬����paint()����
            }

        }, intervel, intervel);
        
        
    }

    int flyEnteredIndex = 0; // �������볡����

    /** �������볡 */
    public void enterAction() {
        flyEnteredIndex++;
        if (flyEnteredIndex % 40 == 0) { // 400��������һ��������--10*40
            FlyingObject obj = nextOne(); // �������һ��������
            flyings = Arrays.copyOf(flyings, flyings.length + 1);
            flyings[flyings.length - 1] = obj;
        }
    }

    /** ��һ�� */
    public void stepAction() {
        for (int i = 0; i < flyings.length; i++) { // ��������һ��
            FlyingObject f = flyings[i];
            f.step();
        }

        for (int i = 0; i < bullets.length; i++) { // �ӵ���һ��
            Bullet b = bullets[i];
            b.step();
        }
        myplane.step(); // Ӣ�ۻ���һ��
    }
    /** ��������һ�� */
    public void flyingStepAction() {
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            f.step();
        }
    }

    int shootIndex = 0; // �������

    /** ��� */
    public void shootAction() {
        shootIndex++;
        if (shootIndex % 50 == 0) { // 300���뷢һ��
            Bullet[] bs = myplane.shoot(); // �һ�����ӵ�
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // ����
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length,
                    bs.length); // ׷������
        }
    }  


    /** �ӵ����������ײ��� */
    public void bangAction() {
        for (int i = 0; i < bullets.length; i++) { // ���������ӵ�
            Bullet b = bullets[i];
            bang(b); // �ӵ��ͷ�����֮�����ײ���
        }
    }

    /** ɾ��Խ������Ｐ�ӵ� */
    public void outOfBoundsAction() {
        int index = 0; // ����
        FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // ���ŵķ�����
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            if (!f.outOfBounds()) {
                flyingLives[index++] = f; // ��Խ�������
            }
        }
        flyings = Arrays.copyOf(flyingLives, index); // ����Խ��ķ����ﶼ����

        index = 0; // ��������Ϊ0
        Bullet[] bulletLives = new Bullet[bullets.length];
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            if (!b.outOfBounds()) {
                bulletLives[index++] = b;
            }
        }
        bullets = Arrays.copyOf(bulletLives, index); // ����Խ����ӵ�����
    }

    /** �����Ϸ���� */
    public void checkGameOverAction() {
        if (isGameOver()==true) {
            state = GAME_OVER; // �ı�״̬
        }
    }

    /** �����Ϸ�Ƿ���� */
    public boolean isGameOver() {

        for (int i = 0; i < flyings.length; i++) {
            int index = -1;
            FlyingObject obj = flyings[i];
            if (myplane.hit(obj)) { // ���Ӣ�ۻ���������Ƿ���ײ
            	myplane.subtractLife(); // ����
            	myplane.setDoubleFire(0); // ˫���������
                index = i; // ��¼���ϵķ���������
            }
            if (index != -1) {
                FlyingObject t = flyings[index];
                flyings[index] = flyings[flyings.length - 1];
                flyings[flyings.length - 1] = t; // ���ϵ������һ�������ｻ��

                flyings = Arrays.copyOf(flyings, flyings.length - 1); // ɾ�����ϵķ�����
            }
        }

        return myplane.getLife() <= 0;
    }

    /** �ӵ��ͷ�����֮�����ײ��� */
    public void bang(Bullet bullet) {
        int index = -1; // ���еķ���������
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject obj = flyings[i];
            if(obj.shootBy(bullet)) { // �ж��Ƿ����
            	index = i; // ��¼�����еķ����������
            	break;
            }
           
        }
        if (index != -1) { // �л��еķ�����
        	
            FlyingObject one = flyings[index]; // ��¼�����еķ�����

            FlyingObject temp = flyings[index]; // �����еķ����������һ�������ｻ��
            flyings[index] = flyings[flyings.length - 1];
            flyings[flyings.length - 1] = temp;
            
            flyings = Arrays.copyOf(flyings, flyings.length - 1); // ɾ�����һ��������(�������е�)

            // ���one������(���˼ӷ֣�������ȡ)
            if (one instanceof Enemy) { // ������ͣ��ǵ��ˣ���ӷ�
                Enemy e = (Enemy) one; // ǿ������ת��
                score += e.getScore(); // �ӷ�
                
            } else if (one instanceof Award) { // ��Ϊ���������ý���
                Award a = (Award) one;
                int type = a.getType(); // ��ȡ��������
                switch (type) {
                case Award.DOUBLE_FIRE:
                    myplane.addDoubleFire(); // ����˫������
                    break;
                case Award.LIFE:
                	myplane.addLife(); // ���ü���
                    break;
                }
            }
         
        }
    }


	/**
     * ������ɷ�����
     * 
     * @return ���������
     */
    public static FlyingObject nextOne() {
        Random random = new Random();
        int type = random.nextInt(20); // [0,20)
        if (type == 0) {
            return new Graf();
        } else {
            airCraft e = new airCraft();
            e.addstep(score);
            return e;
        }
    }

}