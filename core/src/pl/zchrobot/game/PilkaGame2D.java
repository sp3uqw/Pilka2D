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

    private SpriteBatch batch;
    private Texture img;
    private BitmapFont font;

    private ShapeRenderer renderer;
    private FitViewport viewport;

    private Body body;
    private World world;


    private Wall wall;
    private Ball b1, b2, b3;


    private float W = Const.W;
    private float H = Const.H;
    private float RADIUS = Const.RADIUS;


    private Ball[] ball = new Ball[40];

	@Override
	public void create () {

        // box2d start
        Box2D.init();
        world = new World(new Vector2(0, -10f), false); // false to avoid sleep of objects

        renderer = new ShapeRenderer();
        viewport = new FitViewport(W, H);
        batch = new SpriteBatch();
        font = new BitmapFont();

        long seed = System.currentTimeMillis();
        Random r = new Random(seed);

        // physicall objects
        wall = new Wall(Color.BLACK,H, W,world);
        // balls
        b1 = new Ball( new Vector2(W/10, H-RADIUS), RADIUS, Color.GREEN, 5f,world );
        b2 = new Ball( new Vector2(W/10*2, H-RADIUS), RADIUS, Color.RED, 3f, world );
        b3 = new Ball( new Vector2(W/10*3, H-RADIUS), RADIUS, Color.BLUE, 1f,world );


        for (int i = 0; i < ball.length; i++) {
            ball[i] = new Ball(world);
        }

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

        for (Ball b: ball)
        {
            b.render(renderer);
        }



        batch.begin();
        font.setColor(Color.BLACK);
        font.getData().setScale(1);
        int fps = Gdx.graphics.getFramesPerSecond();
        String sFps = "FPS: " + Integer.toString(fps);
        font.draw(batch, sFps, 10, 50 );
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
