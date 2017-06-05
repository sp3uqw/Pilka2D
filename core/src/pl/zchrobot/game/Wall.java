package pl.zchrobot.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.Color;


/**
 * Created by Zybi on 12.02.2017.
 * Klasa reprezentujaca sciany
 */

public class Wall {

    private Color color;
    private float W, H;

    private float BELKA_HEIGHT;
    private float BELKA_WIDTH;
    private float BELKA_X, BELKA_Y; // srodek belki
    PolygonShape kulka;


    public Wall(Color color, float screenHeight, float screenWidth, World world ) {

        this.color = color;
        this.W = screenWidth;
        this.H = screenHeight;
        BELKA_X = W /2;
        BELKA_Y = H /2;
        BELKA_HEIGHT = H /20;
        BELKA_WIDTH = W /4;

        sciana(world);


    }



    public void render(ShapeRenderer renderer) {

        // animacja
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color);
        renderer.rect(BELKA_X - (BELKA_WIDTH/2), BELKA_Y - (BELKA_HEIGHT/2), BELKA_WIDTH, BELKA_HEIGHT);
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(color);
        renderer.rect(0,0,W, H);
        // TODO - dokonczyc polygon
        //renderer.polygon();

        renderer.end();

    }


    private void sciana(World world)   {

        // -----------------------------------------------------------------------------------------------------------------
        // podloga
        BodyDef gBodyDef0 = new BodyDef();              // Create our body definition
        gBodyDef0.position.set(new Vector2(0, 0));      // Set its world position
        Body groundBody = world.createBody(gBodyDef0);  // Create a body from the defintion and add it to the world

        PolygonShape boxFloor = new PolygonShape ();   // Create a polygon shape
        boxFloor.setAsBox(W, 0.0f);

        FixtureDef podloga = new FixtureDef();
        podloga.shape = boxFloor;
        podloga.friction = 1.0f;

        //groundBody.createFixture(boxFloor, 1.0f);          // Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(podloga);

        boxFloor.dispose();                                // Clean up after ourselves

        // -----------------------------------------------------------------------------------------------------------------
        // sciana prawa
        BodyDef gBodyDef1 = new BodyDef();               // Create our body definition
        gBodyDef1.position.set(new Vector2(W, 0.5f));      // Set its world position
        Body groundBody1 = world.createBody(gBodyDef1);  // Create a body from the defintion and add it to the world

        PolygonShape boxLeft = new PolygonShape ();   // Create a polygon shape
        boxLeft.setAsBox(0.5f, H);

        groundBody1.createFixture(boxLeft, 1.0f);          // Create a fixture from our polygon shape and add it to our ground body

        boxLeft.dispose();                                // Clean up after ourselves

// -----------------------------------------------------------------------------------------------------------------
        // sciana lewa
        BodyDef gBodyDef2 = new BodyDef();               // Create our body definition
        gBodyDef2.position.set(new Vector2(0, 0));      // Set its world position
        Body groundBody2 = world.createBody(gBodyDef2);  // Create a body from the defintion and add it to the world

        PolygonShape boxRight = new PolygonShape ();   // Create a polygon shape
        boxLeft.setAsBox(0.5f, H);

        groundBody2.createFixture(boxRight, 1.0f);          // Create a fixture from our polygon shape and add it to our ground body
        boxLeft.dispose();                                // Clean up after ourselves


        // -----------------------------------------------------------------------------------------------------------------
        // sufit
        BodyDef gBodyDef3 = new BodyDef();              // Create our body definition
        gBodyDef3.position.set(new Vector2(0, H));   // Set its world position
        Body groundBody3 = world.createBody(gBodyDef3);  // Create a body from the defintion and add it to the world

        PolygonShape boxTop = new PolygonShape ();   // Create a polygon shape
        boxTop.setAsBox(W, 1f);

        groundBody3.createFixture(boxTop, 1.0f);          // Create a fixture from our polygon shape and add it to our ground body
        boxTop.dispose();                                // Clean up after ourselves



        // -----------------------------------------------------------------------------------------------------------------
        // belka
        BodyDef gBodyDef4 = new BodyDef();              // Create our body definition
        gBodyDef4.position.set(new Vector2(BELKA_X, BELKA_Y));   // Set its world position
        Body groundBody4 = world.createBody(gBodyDef4);  // Create a body from the defintion and add it to the world

        PolygonShape boxBelka = new PolygonShape ();   // Create a polygon shape
        boxBelka.setAsBox(BELKA_WIDTH/2, BELKA_HEIGHT/2);

        groundBody4.createFixture(boxBelka, 1.0f);          // Create a fixture from our polygon shape and add it to our ground body
        boxBelka.dispose();                                // Clean up after ourselves


        // kulka

        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(10f, 0f);
        vertices[1] = new Vector2(10f, 10f);
        vertices[2] = new Vector2(0f, 10f);
        vertices[3] = new Vector2(0f, 0f);

        kulka = new PolygonShape();
        kulka.set(vertices);

    }

}
