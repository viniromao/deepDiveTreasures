package co.neo32.deepdivetreasures.systems;

import co.neo32.deepdivetreasures.components.CollisionRenderer;
import co.neo32.deepdivetreasures.components.Map;
import co.neo32.deepdivetreasures.components.MapRenderer;
import co.neo32.deepdivetreasures.components.PositionComponent;
import co.neo32.deepdivetreasures.entities.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class RenderingSystem {
    private final SpriteBatch batch;
    private final List<Entity> entities;

    private final MapRenderer mapRenderer;

    private final ShapeRenderer shapeRenderer;

    private final CollisionRenderer collisionRenderer;

    public RenderingSystem(SpriteBatch batch, MapRenderer mapRenderer, ShapeRenderer shapeRenderer, CollisionRenderer collisionRenderer) {
        this.batch = batch;
        this.entities = new ArrayList<>();
        this.mapRenderer = mapRenderer;
        this.shapeRenderer = shapeRenderer;
        this.collisionRenderer = collisionRenderer;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void update(ParticleEffect particleEffect, float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        particleEffect.draw(batch, deltaTime);

        // Draw sprites
        for (Entity entity : entities) {
            PositionComponent position = entity.position;
            Sprite sprite = entity.sprite;

            batch.draw(sprite, position.x, position.y);
            batch.end();

            collisionRenderer.debugRenderCollisionBox(entity,shapeRenderer);
            batch.begin();
        }

        batch.end(); // End the sprite batch

        mapRenderer.render(shapeRenderer);
    }
}