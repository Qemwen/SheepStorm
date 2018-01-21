package com.sheepgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;


public class SheepStorm extends Game {
	
	public PolygonSpriteBatch batch;
	public BitmapFont font;
        public Hexmap hexmap;
                
	public void create() {
		batch = new PolygonSpriteBatch();
		//Use LibGDX's default Arial font.
		font = new BitmapFont();
		hexmap = new Hexmap();
                this.setScreen(new MainMenuScreen(this));
                
	}

	public void render() {
		super.render(); //important!
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}