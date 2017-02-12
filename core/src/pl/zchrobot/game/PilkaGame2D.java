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
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.FitViewport;


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

    Walls wall;
    Ball b1, b2;


	@Override
	public void create () {

        renderer = new ShapeRenderer();
        viewport = new FitViewport(W,H);
        batch = new SpriteBatch();
        font = new BitmapFont();

		Box2D.init();
		world = new World(new Vector2(0, -10f), true);

		// ball
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(W/10, H-RADIUS);
		body = world.createBody(bodyDef);
        body.setGravityScale(5f);
        body.setLinearVelocity(10f,0f);

		CircleShape circle = new CircleShape();
		circle.setRadius(RADIUS);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.6f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 1.0f; // Make it bounce a little bit
        Fixture fixture = body.createFixture(fixtureDef);
		circle.dispose();


        //BodyDef groundBodyDef = new BodyDef();
        //groundBodyDef.position.set(new Vector2(0, 0f));
        //Body groundBody = world.createBody(groundBodyDef);
        //PolygonShape groundBox = new PolygonShape();
        //groundBox.setAsBox(400, 0.0f);
        //groundBody.createFixture(groundBox, 0.0f);
        //groundBox.dispose();

        wall = new Walls(Color.BLACK,H, W,world);
        b1 = new Ball( new Vector2(W/10, H-RADIUS), RADIUS, Color.GREEN, H,W , world );



	}

	@Override
	public void render () {

		// box2d
		world.step(1/60f, 6, 2);
        float accelX = Gdx.input.getAccelerometerX() * 1f;
        float accelY = Gdx.input.getAccelerometerY() * 1f;
        world.setGravity( new Vector2(accelY, -accelX) );


        viewport.apply();
        renderer.setProjectionMatrix(viewport.getCamera().combined);

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.begin(ShapeType.Filled);
        renderer.setColor(Color.BLACK);
        renderer.circle(this.body.getPosition().x, this.body.getPosition().y, RADIUS,64);
        renderer.end();

        wall.render(renderer);
        b1.render(renderer);


        batch.begin();
        font.setColor(Color.BLUE);
        font.getData().setScale(2);
        Vector2 v1= this.body.getLinearVelocity();
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
