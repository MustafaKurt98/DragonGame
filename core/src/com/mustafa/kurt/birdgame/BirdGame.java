package com.mustafa.kurt.birdgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class BirdGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    int flapState = 0;
    Texture[] dragon;
    Texture enemy1;
    Texture enemy2;
    Texture enemy3;
    float dragonX = 0;
    float dragonY = 0;
    float dragonWidth = 0;
    float dragonHeight = 0;
    float enemyWidht = 0;
    float enemyHeight = 0;
    int gameState = 0;
    float velocity = 0;
    float gravity = 0.7f;
    float screenWidth = 0;
    float screenHeight = 0;
    float enemyVelocity = 2;
    Random random;
    Circle dragonCircle;
    ShapeRenderer shapeRenderer;
    int score = 0;
    int scoredEnemy = 0;
    int bestScore=0;
    BitmapFont font;
    BitmapFont font2;
    BitmapFont font3;
    int numberofEnemies = 100;
    float[] enemyX = new float[numberofEnemies];
    float[] enemyOffSet = new float[numberofEnemies];
    float[] enemyOffSet2 = new float[numberofEnemies];
    float[] enemyOffSet3 = new float[numberofEnemies];
    float distance = 0;

    Circle[] enemyCircles;
    Circle[] enemyCircles2;
    Circle[] enemyCircles3;


    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        dragon = new Texture[4];//----------------------------
        dragon[0] = new Texture("dragon.png");//--------------------
        dragon[1] = new Texture("dragon2.png");//--------------------
        dragon[2] = new Texture("dragon3.png");//--------------------
        dragon[3] = new Texture("dragon4.png");//--------------------
        enemy1 = new Texture("enemy.png");
        enemy2 = new Texture("enemy.png");
        enemy3 = new Texture("enemy.png");
        random = new Random();
        distance = Gdx.graphics.getWidth() / 4;
        dragonX = Gdx.graphics.getWidth() / 5;
        dragonY = Gdx.graphics.getHeight() / 2;
        dragonWidth = Gdx.graphics.getWidth() / 12;
        dragonHeight = Gdx.graphics.getHeight() / 8;
        enemyWidht = Gdx.graphics.getWidth() / 20;
        enemyHeight = Gdx.graphics.getWidth() / 20;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        shapeRenderer = new ShapeRenderer();

        dragonCircle = new Circle();
        enemyCircles = new Circle[numberofEnemies];
        enemyCircles2 = new Circle[numberofEnemies];
        enemyCircles3 = new Circle[numberofEnemies];

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(6);

        font2 = new BitmapFont();
        font2.setColor(Color.BLACK);
        font2.getData().setScale(6);

        font3 = new BitmapFont();
        font3.setColor(Color.BLACK);
        font3.getData().setScale(6);

        for (int i = 0; i < numberofEnemies; i++) {
            enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

            enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2 + i * distance;

            enemyCircles[i] = new Circle();
            enemyCircles2[i] = new Circle();
            enemyCircles3[i] = new Circle();
        }
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (gameState == 1) {
            if (enemyX[scoredEnemy] < dragonX) {
                score++;

                if (scoredEnemy < numberofEnemies - 1) {
                    scoredEnemy++;
                } else {
                    scoredEnemy = 0;
                }
            }
            if (Gdx.input.justTouched()) {
                velocity = -10;
            }
            for (int i = 0; i < numberofEnemies; i++) {
                if (enemyX[i] < enemyWidht) {
                    enemyX[i] = enemyX[i] + numberofEnemies * distance;
                    enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                } else {
                    enemyX[i] = enemyX[i] - enemyVelocity;
                }
                batch.draw(enemy1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet[i], enemyWidht, enemyHeight);
                batch.draw(enemy2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet2[i], enemyWidht, enemyHeight);
                batch.draw(enemy3, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet3[i], enemyWidht, enemyHeight);

                enemyCircles[i] = new Circle(enemyX[i] + enemyWidht / 2, Gdx.graphics.getHeight() / 2 + enemyOffSet[i] + enemyHeight / 2, enemyWidht / 2);
                enemyCircles2[i] = new Circle(enemyX[i] + enemyWidht / 2, Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + enemyHeight / 2, enemyWidht / 2);
                enemyCircles3[i] = new Circle(enemyX[i] + enemyWidht / 2, Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + enemyHeight / 2, enemyWidht / 2);
            }
            if (dragonY > 0) {
                velocity = velocity + gravity;
                dragonY = dragonY - velocity;
            } else {
                gameState = 2;
            }
        } else if (gameState == 0) {
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        } else if (gameState == 2) {
            if (score>bestScore){
                bestScore=score;
            }
            font2.draw(batch, "Oyun Bitti! Yeniden baslamak için tekrar dokunun", 100, Gdx.graphics.getHeight() / 1.5f);
            font.draw(batch, "Skorunuz: " + score, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 2);
            font3.draw(batch,"En iyi skorunuz: "+bestScore,Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/3);
            if (Gdx.input.justTouched()) {
                gameState = 1;

                dragonY = Gdx.graphics.getHeight() / 2;

                for (int i = 0; i < numberofEnemies; i++) {
                    enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

                    enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2 + i * distance;

                    enemyCircles[i] = new Circle();
                    enemyCircles2[i] = new Circle();
                    enemyCircles3[i] = new Circle();
                }
                velocity = 0;
                scoredEnemy = 0;
                score = 0;
            }
        }

        if (flapState == 0) {
            flapState = 1;
        } else
            flapState = 0;

        font.draw(batch, String.valueOf(score), 100, 200);
        batch.draw(dragon[flapState], dragonX, dragonY, dragonWidth, dragonHeight);
        batch.end();

        dragonCircle.set(dragonX + dragonWidth / 2, dragonY + dragonHeight / 2, dragonWidth / 3);

        for (int i = 0; i < numberofEnemies; i++) {
            if (Intersector.overlaps(dragonCircle, enemyCircles[i]) || Intersector.overlaps(dragonCircle, enemyCircles2[i]) || Intersector.overlaps(dragonCircle, enemyCircles3[i])) {
                gameState = 2;
            }
        }
        shapeRenderer.end();
    }
    @Override
    public void dispose() {
    }
}
