package pl.zchrobot.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.Color;

import java.util.Random;

/**
 * Created by Zybi on 12.02.2017.
 * Klasa reprezentujaca pilke
 */

public class Ball {

    private Vector2 p;
    private float radius;
    private Color color;

    // box2d
    Body body;


    public Ball(Vector2 position, float radius, Color color, float gravityScale, World world ) {

        this.p = position;
        this.radius = radius;

        this.color = color;

        // box2d
        // Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.position.set(p.x, p.y); // Set our body's starting position in the world
        body = world.createBody(bodyDef); // Create our body in the world using our body definition
        // body shape
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.6f;
        fixtureDef.friction = 0.1f;         // tarcie
        fixtureDef.restitution = 0.6f;      // Make it bounce a little bit
        Fixture fixture = body.createFixture(fixtureDef); // Create our fixture and attach it to the body

        body.setGravityScale(gravityScale);
        circle.dispose();

    }

    public void render(ShapeRenderer renderer) {

        // animacja
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color);
        float x = this.body.getPosition().x;
        float y = this.body.getPosition().y;
        renderer.circle(x,y,radius,64);
        renderer.end();

    }
}
