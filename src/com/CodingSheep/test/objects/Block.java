package com.CodingSheep.test.objects;

import java.awt.*;
import java.util.LinkedList;
import com.CodingSheep.test.framework.*;
import com.CodingSheep.test.window.Game;

/**
 * @author Jarrod Raine
 */
public class Block extends GameObject
{
	private int type;
	
	Texture tex = Game.getInstance();
	
	public Block(float x, float y, int type, ObjectId id)
	{
		super(x, y, id);
		this.type = type;
	}

	public void tick(LinkedList<GameObject> object)
	{
		
	}

	public void render(Graphics g)
	{
		if(type == 0)
			g.drawImage(tex.block[0], (int)x, (int)y, null);
		if(type == 1)
			g.drawImage(tex.block[1], (int)x, (int)y, null);
	}

	public Rectangle getBounds()
	{
		return new Rectangle((int)x, (int)y, 32, 32);
	}
}
