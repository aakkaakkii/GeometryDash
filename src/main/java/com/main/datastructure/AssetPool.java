package com.main.datastructure;

import com.main.component.Sprite;
import com.main.component.Spritesheet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    public static Map<String, Sprite> sprites = new HashMap<>();
    public static Map<String, Spritesheet> spritesheets = new HashMap<>();

    public static boolean hasSprite(String pictureFile) {
        File tmp = new File(pictureFile);
        return AssetPool.sprites.containsKey(tmp.getAbsolutePath());
    }

    public static boolean hasSpritesheet(String pictureFile) {
        File tmp = new File(pictureFile);
        return AssetPool.spritesheets.containsKey(tmp.getAbsolutePath());
    }

    public static Sprite getSprite(String pictureFile) {
        File file = new File(pictureFile);
        if (AssetPool.hasSprite(file.getAbsolutePath())) {
            return AssetPool.sprites.get(file.getAbsolutePath());
        } else {
            Sprite sprite = new Sprite(pictureFile);
            AssetPool.addSprite(pictureFile, sprite);
            return sprite;
        }
    }

    public static Spritesheet getSpritesheet(String pictureFile) {
        File file = new File(pictureFile);
        if(AssetPool.hasSprite(file.getAbsolutePath())) {
            return AssetPool.spritesheets.get(file.getAbsolutePath());
        } else {
            System.out.println("no SpriteSheer " + pictureFile);
            System.exit(-1);
        }
        return null;
    }

    /**
     * @param pictureFile The absolute path to the picture
     * @param sprite
     */
    public static void addSprite(String pictureFile, Sprite sprite) {
        File file = new File(pictureFile);

        if (!AssetPool.hasSprite(file.getAbsolutePath())) {
            AssetPool.sprites.put(file.getAbsolutePath(), sprite);
        } else {
            System.out.println("asset pool already has asset: " + file.getAbsolutePath());
            System.exit(-1);
        }
    }

    public static void addSpritesheet(String pictureFile, int tileWidth, int tileHeight,
                                      int spacing, int columns, int size) {
        File file = new File(pictureFile);
        if (!AssetPool.hasSpritesheet(file.getAbsolutePath())) {
            Spritesheet spritesheet = new Spritesheet(pictureFile, tileWidth, tileHeight,
                    spacing, columns, size);
            AssetPool.spritesheets.put(file.getAbsolutePath(), spritesheet);
        }
    }}
