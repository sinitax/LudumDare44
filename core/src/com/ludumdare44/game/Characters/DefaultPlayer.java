package com.ludumdare44.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ludumdare44.game.MiscUtils;

public class DefaultPlayer extends com.ludumdare44.game.Characters.Player {
    Texture spriteSheet = new Texture("assets/models/characters/rogue/sprites.png");
    TextureRegion[][] spriteSheetMap = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 10, spriteSheet.getHeight() / 10);
    
    @Override
    public Vector2 getModelScale() {
        return new Vector2(2, 3);
    }
    
    @Override
    public Texture getIcon() {
        return new Texture("assets/models/characters/rogue/icon.png");
    }
    
    @Override
    public Animation<TextureRegion> getGrappleAnimation() {
        return new Animation<>(0.2f, spriteSheetMap[0]);
    }

    @Override
    public Animation<TextureRegion> getRightSwingAnimation() {
        return new Animation<>(0.05f, spriteSheetMap[2]);
    }

    @Override
    public Animation<TextureRegion> getLeftSwingAnimation() {
        return new Animation<>(0.05f, spriteSheetMap[2]);
    }

    @Override
    public Animation<TextureRegion> getAirborneAnimation() {
        return new Animation<>(0.05f, spriteSheetMap[0]);
    }

    @Override
    public com.ludumdare44.game.Characters.Ability getSpecial() { //cloak
        return new com.ludumdare44.game.Characters.Ability(new Animation<>(0.04f, (TextureRegion[]) MiscUtils.concatenate(spriteSheetMap[1], spriteSheetMap[5])), this, 80, 5000, 10000) {
            @Override
            public void perform() {

            }
        };
    }

    @Override
    public com.ludumdare44.game.Characters.Ability getAttack() { //stab
        return new com.ludumdare44.game.Characters.Ability(new Animation<>(0.02f, spriteSheetMap[3]), this, 10,0, 200) {
            @Override
            public void perform() {

            }
        };
    }

    public DefaultPlayer(Vector2 pos) {
        super(pos);
        initAnimations();
    }
}