package com.main.engine;

import com.main.component.*;
import com.main.datastructure.AssetPool;
import com.main.datastructure.Transform;
import com.main.file.Parser;
import com.main.ui.MainContainer;
import com.main.util.Constants;
import com.main.util.Vector2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class LevelEditorScene extends Scene {

    public GameObject player;
    Grid grid;
    CameraControls cameraControls;
    public GameObject mouseCursor;
    MainContainer editingButtons;

    public LevelEditorScene(String name) {
        super(name);
    }

    @Override
    public void init() {
        initAssetPool();
        editingButtons = new MainContainer();

        grid = new Grid();
        cameraControls = new CameraControls();
        editingButtons.start();

        player = new GameObject("Some Game obj", new Transform(new Vector2(310.0f, 110.0f)), 0);

        mouseCursor = new GameObject("Mouse Cursor", new Transform(new Vector2()), 10);
        mouseCursor.addComponent(new SnapToGrid(Constants.TILE_WIDTH, Constants.TILE_HEIGHT));


        Spritesheet layerOne = AssetPool.getSpritesheet("assets/player/layerOne.png");
        Spritesheet layerTwo = AssetPool.getSpritesheet("assets/player/layerTwo.png");
        Spritesheet layerThree = AssetPool.getSpritesheet("assets/player/layerThree.png");
        Player playerComp = new Player(
                layerOne.sprites.get(0),
                layerTwo.sprites.get(0),
                layerThree.sprites.get(0),
                Color.RED, Color.GREEN);

        player.addComponent(playerComp);

        GameObject ground = new GameObject("Ground", new Transform(new Vector2(0, Constants.GROUND_Y)), 1);
        ground.addComponent(new Ground());

        ground.setNonserializable();
        player.setNonserializable();
        addGameObject(player);
        addGameObject(ground);

    }

    private void initAssetPool() {
        AssetPool.addSpritesheet("assets/player/layerOne.png", 42, 42, 2, 13, 13 * 5);
        AssetPool.addSpritesheet("assets/player/layerTwo.png", 42, 42, 2, 13, 13 * 5);
        AssetPool.addSpritesheet("assets/player/layerThree.png", 42, 42, 2, 13, 13 * 5);

        AssetPool.addSpritesheet("assets/groundSprites.png", 42, 42, 2, 6, 12);
        AssetPool.addSpritesheet("assets/ui/buttonSprites.png", 60, 60, 2, 2, 2);
    }

    @Override
    public void update(double dt) {

        if (camera.position.y > Constants.CAMERA_OFFSET_GROUND_Y) {
            camera.position.y = Constants.CAMERA_OFFSET_GROUND_Y;
        }

        for (GameObject g : gameObjects) {
            g.update(dt);
        }
        cameraControls.update(dt);
        grid.update(dt);
        mouseCursor.update(dt);
        editingButtons.update(dt);

        if (Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_F1)) {
            export("Test");
        } else if (Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_F2)) {
            importLevel("Test");
        } else if (Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_F3)) {
            Window.getWindow().changeScene(1);
        }
    }


    private void importLevel(String filename) {
        Parser.openFile(filename);

        GameObject go = Parser.parseGameObject();
        while (go != null) {
            addGameObject(go);
            go = Parser.parseGameObject();
        }
    }


    private void export(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream("/home/aka/programming/java/GeometryDash/levels/" + filename + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            zos.putNextEntry(new ZipEntry(filename + ".json"));

            int i = 0;
            for (GameObject go : gameObjects) {
                String str = go.serialize(0);
                if (str.compareTo("") != 0) {
                    zos.write(str.getBytes());
                    if (i != gameObjects.size() - 1) {
                        zos.write(",\n".getBytes());
                    }
                }
                i++;
            }

            zos.closeEntry();
            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(new Color(1.0f, 1.0f, 1.0f));
        g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        renderer.render(g2);
        grid.draw(g2);
        editingButtons.draw(g2);
        mouseCursor.draw(g2);
    }

}
