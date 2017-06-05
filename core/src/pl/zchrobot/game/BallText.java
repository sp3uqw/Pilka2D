package pl.zchrobot.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Zybi on 28.05.2017.
 */

public class BallText extends Ball {


    private float agle;
    private float h;
    Texture textureBall;
    TextureRegion region;

    public BallText(World world) {
        super(world);
        this.agle = 0;
        this.textureBall = new Texture("ballRed.png");
        this.h = textureBall.getHeight();
        region = new TextureRegion(textureBall);


    }


    public BallText(Vector2 position, float radius, Color color, float gravityScale, World world) {
        super(position, radius, color, gravityScale, world);
        this.agle = 0;
        this.textureBall = new Texture("ball.png");
        this.h = textureBall.getHeight();
        region = new TextureRegion(textureBall);


    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch batch) {

        // variables
        float x = this.body.getPosition().x;
        float y = this.body.getPosition().y;

        this.agle = this.body.getAngle() * 180f/ (float)Math.PI;

        // batch
        batch.begin();
        //batch.draw(textureBall, x-radius, y-radius, this.radius * 2.0f, this.radius * 2.0f);
        batch.draw(region, x-radius, y-radius, radius*(1f), radius*(1f), radius * 2.0f, radius * 2.0f, 1.0f, 1.0f, agle);
        batch.end();

    }
}
