package com.CodingSheep.test.framework;

import java.awt.image.BufferedImage;
import com.CodingSheep.test.window.BufferedImageLoader;

public class Texture
{
	public BufferedImage[] block = new BufferedImage[2];
	public BufferedImage[] player = new BufferedImage[1];
	public BufferedImage[] player_jump = new BufferedImage[0];
	
	private BufferedImage block_sheet = null;
	private BufferedImage player_sheet = null;
	
	SpriteSheet bs, ps;
	
	public Texture()
	{
		BufferedImageLoader loader = new BufferedImageLoader();
		try
		{
			block_sheet = loader.loadImage("/block_sheet.png");
			player_sheet = loader.loadImage("/player_sheet.png");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		bs = new SpriteSheet(block_sheet);
		ps = new SpriteSheet(player_sheet);
		
		getTextures();
	}
	
	private void getTextures()
	{
		block[0] = bs.grabImage(1, 1, 32, 32);
		block[1] = bs.grabImage(2, 1, 32, 32);
		
		player[0] = ps.grabImage(1, 1, 32, 32);
		
		//player_jump[0] = ps.grabImage(0, 0, 32, 32);
	}
}
