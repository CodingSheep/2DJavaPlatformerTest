package com.CodingSheep.test.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.CodingSheep.test.framework.GameObject;
import com.CodingSheep.test.framework.ObjectId;
import com.CodingSheep.test.framework.Texture;
import com.CodingSheep.test.window.Animation;
import com.CodingSheep.test.window.Camera;
import com.CodingSheep.test.window.Game;
import com.CodingSheep.test.window.Handler;

public class Player extends GameObject
{	
	private final float MAX_SPEED = 10;
	private float width = 32;
	private float height = 64;
	private float gravity = 0.25f;
	private Handler handler;
	private Camera cam;
	private Animation playerWalkRight, playerWalkLeft;
	
	Texture tex = Game.getInstance();
	
	public Player(float x, float y, Handler handler, Camera cam, ObjectId id)
	{
		super(x, y, id);
		this.handler = handler;
		this.cam = cam;
		playerWalkLeft = new Animation(10, tex.player[0]);
		playerWalkRight = new Animation(10, tex.player[0]);
	}
	
	public void tick(LinkedList<GameObject> object)
	{
		x += velX;
		y += velY;
		
		if(velX < 0)
			facing = -1;
		else if(velX > 0)
			facing = 1;
		
		if(falling || jumping)
		{
			velY += gravity;
			
			if(velY > MAX_SPEED)
				velY = MAX_SPEED;
		}
		
		Collision(object);
		playerWalkLeft.runAnimation();
		playerWalkRight.runAnimation();
	}

	public void render(Graphics g)
	{		
		g.setColor(Color.blue);
		if(jumping)
		{
			if(facing == 1)
				g.drawImage(tex.player[0], (int)x, (int)y, 32, 64, null);
			else if(facing == -1)
				g.drawImage(tex.player[0], (int)x, (int)y, 32, 64, null);
		}
		else
		{
			if(velX != 0)
			{
				if(facing == 1)
					playerWalkRight.drawAnimation(g, (int)x, (int)y, 32, 64);
				else
					playerWalkLeft.drawAnimation(g, (int)x, (int)y, 32, 64);
			}
			else
			{
				if(facing == 1)
					g.drawImage(tex.player[0], (int)x, (int)y, 32, 64, null);
				else if(facing == -1)
					g.drawImage(tex.player[0], (int)x, (int)y, 32, 64, null);
			}
		}
	}

	public Rectangle getBounds()
	{
		return new Rectangle((int)((int)x + (width/2)-((width/2)/2)),(int)((int)y + (height/2)),(int)width/2,(int)height/2);
	}
	
	public Rectangle getBoundsLeft()
	{
		return new Rectangle((int)x,(int)y+5,(int)5,(int)height-10);
	}
	
	public Rectangle getBoundsRight()
	{
		return new Rectangle((int)((int)x+width-5),(int)y+5,(int)5,(int)height-10);
	}
	
	public Rectangle getBoundsTop()
	{
		return new Rectangle((int)((int)x + (width/2)-((width/2)/2)),(int)y,(int)width/2,(int)height/2);
	}

	private void Collision(LinkedList<GameObject> object)
	{
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectId.Block)
			{
				if(getBounds().intersects(tempObject.getBounds()))
				{
					y = tempObject.getY() - height;
					velY = 0;
					falling = false;
					jumping = false;
				}
				else
				{
					falling = true;
				}
				if(getBoundsLeft().intersects(tempObject.getBounds()))
				{
					x = tempObject.getX() + width;
				}
				if(getBoundsRight().intersects(tempObject.getBounds()))
				{
					x = tempObject.getX() - width;
				}
				if(getBoundsTop().intersects(tempObject.getBounds()))
				{
					y = tempObject.getY() + 32;
					velY = 0;
				}
			}
			else if(tempObject.getId() == ObjectId.Flag)
				if(getBoundsTop().intersects(tempObject.getBounds()) || getBounds().intersects(tempObject.getBounds()))
					handler.switchLevel();
		}
	}
}