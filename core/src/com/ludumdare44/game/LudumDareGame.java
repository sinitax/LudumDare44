package com.ludumdare44.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ludumdare44.game.Cutscenes.CutsceneCharacter;
import com.ludumdare44.game.Cutscenes.CutsceneCharacterPosition;
import com.ludumdare44.game.Cutscenes.CutsceneScreen;
import com.ludumdare44.game.Cutscenes.ScreenFader;
import com.ludumdare44.game.Cutscenes.events.*;

public class LudumDareGame extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }

    public void playNormalMode() {
        setScreen(createIntroCutscene().onComplete(() -> setScreen(new GameScene())));
    }

    public void playEndlessMode() {
        // TODO
        setScreen(new GameScene());
    }

    protected CutsceneScreen createIntroCutscene() {
        Texture playerTexture = new Texture("assets/player.png");
        TextureRegion playerTextureRegion = TextureRegion.split(playerTexture, playerTexture.getWidth()/7, playerTexture.getHeight())[0][0];
        Texture devilTexture = new Texture("assets/devil.png");
        playerTextureRegion.flip(true, false);

        CutsceneCharacter characterPlayer = new CutsceneCharacter(playerTextureRegion, "Player");
        CutsceneCharacter characterDevil = new CutsceneCharacter(devilTexture, "Devil");
        CutsceneScreen cutscene = new CutsceneScreen(new Color(0.26f, 0.26f, 0.31f, 1f), true);

        cutscene.addCutsceneEvent(new FadeEvent(new ScreenFader(Color.BLACK, 2).fadeIn()))
                .addCutsceneEvent(new CharacterEnterEvent(characterPlayer, CutsceneCharacterPosition.RIGHT, 1))
                .addCutsceneEvent(new WaitEvent(1))
                .addCutsceneEvent(new CharacterEnterEvent(characterDevil, CutsceneCharacterPosition.LEFT, 1))
                .addCutsceneEvent(new WaitEvent(2))
                .addCutsceneEvent(new TextEvent("We meet again."))
                .addCutsceneEvent(new TextEvent("Our pact is complete, now hand over your soul!"))
                .addCutsceneEvent(new TextEvent("You refuse?"))
                .addCutsceneEvent(new TextEvent("I guess there is something else you could do for me as payment..."))
                .addCutsceneEvent(new TextEvent("There are some stray souls that need to be collected in the underground."))
                .addCutsceneEvent(new TextEvent("I'll be watching your progress closely. There will a punishment if you're lacking."))
                .addCutsceneEvent(new TextEvent("Now, go! Before I change my mind..."))
                .addCutsceneEvent(new WaitEvent(0.5f))
                .addCutsceneEvent(new CharacterExitEvent(characterPlayer, 1f))
                .addCutsceneEvent(new WaitEvent(1.5f))
                .addCutsceneEvent(new FadeEvent(new ScreenFader(Color.BLACK, 1.5f).fadeOut()));

        return cutscene;
    }
}
