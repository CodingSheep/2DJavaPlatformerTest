package com.CodingSheep.test.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.CodingSheep.test.framework.KeyInput;
import com.CodingSheep.test.framework.ObjectId;
import com.CodingSheep.test.framework.Texture;
import com.CodingSheep.test.objects.Block;
import com.CodingSheep.test.objects.Flag;
import com.CodingSheep.test.objects.Player;

/**
 * @author CodingSheep
 */
public class Game extends Canvas implements Runnable
{
	//Variables
	public static int WIDTH, HEIGHT;
	public static int LEVEL = 1;
	public BufferedImage level = null;
	public BufferedImage background = null;
	
	private boolean running = false;
	private Thread thread;
	
	Handler handler;
	Camera cam;
	static Texture tex;
	Random rand = new Random();
	
	//Code
	
	public static void main(String[] args)
	{
		new Window(800,600,"Platformer Test Window", new Game());
	}
	
	public synchronized void start()
	{
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public static Texture getInstance()
	{
		return tex;
	}
	
	public void run()
	{
		init();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1)
			{
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	
	private void init()
	{
		WIDTH = getWidth();
		HEIGHT = getHeight();
		tex = new Texture();
		BufferedImageLoader loader = new BufferedImageLoader();
		
		level = loader.loadImage("/testLevel.png");
		background = loader.loadImage("/background.png");
		cam = new Camera(0,0);
		handler = new Handler(cam);
		handler.loadImageLevel(level);
		
		this.addKeyListener(new KeyInput(handler));
	}
	
	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D)g;
		
		//////////////////////////////////
		//Draws
		g.setColor(new Color(102, 204, 255));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g2d.translate(cam.getX(), cam.getY());	
		for(int xx = 0; xx < background.getWidth() * 2; xx += background.getWidth())
			g.drawImage(background, xx, 32, this);
		handler.render(g);
		g2d.translate(-cam.getX(), -cam.getY());
		//////////////////////////////////
		
		g.dispose();
		bs.show();
	}
	
	
	private void tick()
	{
		handler.tick();
		for(int i = 0; i < handler.object.size(); i++)
			if(handler.object.get(i).getId() == ObjectId.Player)
				cam.tick(handler.object.get(i));
	}
}