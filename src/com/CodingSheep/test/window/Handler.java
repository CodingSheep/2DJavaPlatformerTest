package com.CodingSheep.test.window;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.CodingSheep.test.framework.GameObject;
import com.CodingSheep.test.framework.ObjectId;
import com.CodingSheep.test.objects.Block;
import com.CodingSheep.test.objects.Flag;
import com.CodingSheep.test.objects.Player;

/**
 * @author Jarrod Raine
 */
public class Handler
{
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	private BufferedImage level2 = null;
	private GameObject tempObject;
	private Camera cam;
	
	public Handler(Camera cam)
	{
		this.cam = cam;
		BufferedImageLoader loader = new BufferedImageLoader();
		level2 = loader.loadImage("/testLevel2.png");
	}
	
	public void addObject(GameObject object)
	{
		this.object.add(object);
	}
	
	public void loadImageLevel(BufferedImage image)
	{
		int w = image.getWidth();
		int h = image.getHeight();
		
		for(int xx = 0; xx < h; xx++)
			for(int yy = 0; yy < w; yy++)
			{
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 255 && green == 255 & blue == 255)
					addObject(new Block(xx * 32, yy * 32, 0, ObjectId.Block));
				if(red == 0 && green == 128 & blue == 0)
					addObject(new Block(xx * 32, yy * 32, 1, ObjectId.Block));
				if(red == 0 && green == 0 & blue == 255)
					addObject(new Player(xx * 32, yy * 32, this, cam, ObjectId.Player));
				if(red == 255 && green == 255 & blue == 0)
					addObject(new Flag(xx * 32, yy * 32, ObjectId.Flag));
			}
	}
	
	public void render(Graphics g)
	{
		for(int i = 0; i < object.size(); i++)
		{
			tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}
	
	public void removeObject(GameObject object)
	{
		this.object.remove(object);
	}
	
	public void switchLevel()
	{
		clearLevel();
		cam.setX(0);
		
		switch(Game.LEVEL)
		{
		case 1:
			Game.LEVEL++;
			loadImageLevel(level2);
			break;
		default:
			System.out.println("Thanks for Playing!");
			System.exit(1);
		}
	}
	
	public void tick()
	{
		for(int i = 0; i < object.size(); i++)
		{
			tempObject = object.get(i);
			
			tempObject.tick(object);
		}
	}
	
	private void clearLevel()
	{
		object.clear();
	}
}
