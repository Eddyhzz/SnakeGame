package Lab2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MPanel extends JPanel implements KeyListener, ActionListener{
	ImageIcon title;
	ImageIcon body;
	ImageIcon up;
	ImageIcon down;
	ImageIcon left;
	ImageIcon right;
	ImageIcon food;
	
	int len = 3;
	int score = 0;
	int[] snakex = new int[750];
	int[] snakey = new int[750];
	String fx = "R"; // 方向：R,L,U,D
	boolean isStarted = false;
	boolean isFailed = false;
	Timer timer = new Timer(100, this);
	Timer timer2 = new Timer(200, this);
	Timer timer3  = new Timer(250,this);
	Timer timer4 = new Timer(300,this);
	int foodx;
	int foody;
	Random rand = new Random();
	
	public MPanel() {
		loadImages();
		initSnake();
		this.setFocusable(true);
		this.addKeyListener(this); 
		timer.start();
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		title.paintIcon(this, g, 20, 11);
		
		g.fillRect(20, 75, 855, 600);
		g.setColor(Color.WHITE);
		g.drawString("Len: " + len, 750,35);
		g.drawString("Score: " + score, 750, 50);
		if(fx == "R") {
			right.paintIcon(this, g, snakex[0], snakey[0]);
		}
		else if(fx == "L") {
			left.paintIcon(this, g, snakex[0], snakey[0]);
		}
		else if(fx == "D") {
			down.paintIcon(this, g, snakex[0], snakey[0]);
		}
		else if(fx == "U") {
			up.paintIcon(this, g, snakex[0], snakey[0]);
		}
		for(int i = 1; i < len;i++) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}
		
		food.paintIcon(this, g, foodx, foody);
		
		if(isStarted == false) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.drawString("Press Space to start", 300, 300);
		}
		if(isFailed) {
			g.setColor(Color.RED);
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.drawString("Failed: Press Space to Restart", 200, 300);
		}
		
	
		
	}
	
	public void initSnake() {
		len = 3;
		snakex[0] = 100;
		snakey[0] = 100;
		snakex[1] = 75;
		snakey[1] = 100;
		snakex[2] = 50;
		snakey[2] = 100;
		foodx = 25 + 25 * rand.nextInt(34);
		foody = 75 + 25 * rand.nextInt(24);
		fx = "R";
		score = 0;
	}

	@Override
	public void keyTyped(KeyEvent e) {

		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();  //每个键的背后都有个数字
		if(keyCode == KeyEvent.VK_SPACE) //如果等于空格键
		{
			if(isFailed) {
				isFailed = false;
				initSnake();
			}else {
				isStarted = !isStarted;
			}
			
			repaint();
		}else if(keyCode == KeyEvent.VK_LEFT) {
			fx = "L";
		}else if(keyCode == KeyEvent.VK_RIGHT) {
			fx = "R";
		}else if(keyCode == KeyEvent.VK_UP) {
			fx = "U";
		}else if(keyCode == KeyEvent.VK_DOWN) {
			fx = "D";
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(isStarted && !isFailed) {
			for(int i = len-1; i > 0;i--) {
				snakex[i] = snakex[i-1];
				snakey[i] = snakey[i-1];
			}
			if(fx == "R") {
				snakex[0] = snakex[0] + 25;
				if(snakex[0] > 850)snakex[0] = 25;
			}
			else if(fx == "L") {
				snakex[0] = snakex[0] -25;					
				if(snakex[0] < 25)snakex[0] = 850;
			}
			else if(fx == "U") {
				snakey[0] = snakey[0] -25;	
				if(snakey[0] < 75 )snakey[0] = 650;
				}	
			else if(fx == "D") {
				snakey[0] = snakey[0] + 25;	
				if(snakey[0] > 650)snakey[0] = 75;
			}
				
			if(snakex[0] == foodx && snakey[0] == foody) {
				len++;
				score += 10;
				foodx = 25 + 25 * rand.nextInt(34);
				foody = 75 + 25 * rand.nextInt(24);
			}
			for(int i = 1; i < len; i++) {
				if(snakex[i] == snakex[0] && snakey[i] == snakey[0]) {
					isFailed = true;
				}
			}
			if(score > 50) {
				timer2.start();
			}
			if(score > 100) {
				timer3.start();
			}
			if(score > 200) {
				timer4.start();
			}
			repaint();
		}
		
		timer.start();
		
	}

	private void loadImages() {
		InputStream is ;
		try {
				is = getClass().getClassLoader().getResourceAsStream("title.jpg");
				title = new ImageIcon(ImageIO.read(is));
				is = getClass().getClassLoader().getResourceAsStream("body.png");
				body = new ImageIcon(ImageIO.read(is));
				is = getClass().getClassLoader().getResourceAsStream("up.png");
				up = new ImageIcon(ImageIO.read(is));
				is = getClass().getClassLoader().getResourceAsStream("down.png");
				down = new ImageIcon(ImageIO.read(is));
				is = getClass().getClassLoader().getResourceAsStream("left.png");
				left = new ImageIcon(ImageIO.read(is));
				is = getClass().getClassLoader().getResourceAsStream("right.png");
				right = new ImageIcon(ImageIO.read(is));
				is = getClass().getClassLoader().getResourceAsStream("food.png");
				food = new ImageIcon(ImageIO.read(is));
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}

		
	}
}



