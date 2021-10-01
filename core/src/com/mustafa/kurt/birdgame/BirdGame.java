package com.mustafa.kurt.birdgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class BirdGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    Texture dragon;
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

    int numberofEnemies = 100;
    float[] enemyX = new float[numberofEnemies];
    float[] enemyOffSet = new float[numberofEnemies];
    float[] enemyOffSet2 = new float[numberofEnemies];
    float[] enemyOffSet3 = new float[numberofEnemies];
    float distance = 0;


    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        dragon = new Texture("dragon.png");
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

        for (int i = 0; i < numberofEnemies; i++) {
            enemyOffSet[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
            enemyOffSet2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
            enemyOffSet3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);

            enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2 + i * distance;
        }
    }
    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (gameState == 1) {
            if (Gdx.input.justTouched()) {
                velocity = -10;
            }
            for (int i = 0; i < numberofEnemies; i++) {
                if (enemyX[i] < enemyWidht) {
                    enemyX[i] = enemyX[i] + numberofEnemies * distance;
                    enemyOffSet[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
                    enemyOffSet2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
                    enemyOffSet3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
                } else {
                    enemyX[i] = enemyX[i] - enemyVelocity;
                }
                batch.draw(enemy1, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffSet[i], enemyWidht, enemyHeight);
                batch.draw(enemy2, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffSet2[i], enemyWidht, enemyHeight);
                batch.draw(enemy3, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffSet3[i], enemyWidht, enemyHeight);
//				batch.draw(enemy2,enemyX[i],Gdx.graphics.getHeight()/1.25f,enemyWidht,enemyHeight);
            }
            if (dragonY > 0 || velocity < 0) {
                velocity = velocity + gravity;
                dragonY = dragonY - velocity;
            }
        } else {
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        }
        batch.draw(dragon, dragonX, dragonY, dragonWidth, dragonHeight);
        batch.end();
    }
    @Override
    public void dispose() {
    }
}
