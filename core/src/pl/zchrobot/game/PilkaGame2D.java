package pl.zchrobot.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;


public class PilkaGame2D extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    BitmapFont font;

    ShapeRenderer renderer;
    private FitViewport viewport;

    Body body;
    World world;

    float W = 40f;
    float H = 30f;
    float RADIUS = W/30;

    Wall wall;
    Ball b1, b2, b3;

	@Override
	public void create () {

        // box2d start
        Box2D.init();
        world = new World(new Vector2(0, -10f), true);

        renderer = new ShapeRenderer();
        viewport = new FitViewport(W,H);
        batch = new SpriteBatch();
        font = new BitmapFont();

        long seed = System.currentTimeMillis();
        Random r = new Random(seed);

        // physicall objects
        wall = new Wall(Color.BLACK,H, W,world);
        b1 = new Ball( new Vector2(W/10, H-RADIUS), RADIUS, Color.GREEN, H,W , 5f,world );
        b2 = new Ball( new Vector2(W/10*2, H-RADIUS), RADIUS, Color.RED, H,W , 3f, world );
        b3 = new Ball( new Vector2(W/10*3, H-RADIUS), RADIUS, Color.BLUE, H,W , 1f,world );

	}

	@Override
	public void render () {

		// box2d
		world.step(1/60f, 6, 2);
        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();
        world.setGravity( new Vector2(accelY, -accelX) );


        viewport.apply();
        renderer.setProjectionMatrix(viewport.getCamera().combined);

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        wall.render(renderer);
        b1.render(renderer);
        b2.render(renderer);
        b3.render(renderer);


        batch.begin();
        font.setColor(Color.BLUE);
        font.getData().setScale(2);

        Vector2 v1= b1.body.getLinearVelocity();
        float v1X = (float)((int)(v1.x *1000f) /1000f);
        float v1Y = (float)((int)(v1.y *1000f) /1000f);
        String sV = "Vx =  " + Float.toString(v1X) + "; Vy =  " + Float.toString(v1Y);
        font.draw(batch, sV, 10, 160);
        batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		world.dispose();
	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
