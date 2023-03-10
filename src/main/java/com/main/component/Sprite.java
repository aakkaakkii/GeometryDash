package com.main.component;

import com.main.Main;
import com.main.datastructure.AssetPool;
import com.main.engine.Component;
import com.main.file.Parser;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Sprite extends Component {

    public BufferedImage image;
    public String pictureFile;
    public int width, height;

    public boolean isSubsprite = false;
    public int row, column, index;

    public Sprite(String pictureFile) {
        this.pictureFile = pictureFile;

        try {
            this.image = ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream("/" + pictureFile)));
            this.width = image.getWidth();
            this.height = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Sprite(BufferedImage image, String pictureFile) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.pictureFile = pictureFile;
    }


    public Sprite(BufferedImage image, int row, int column, int index, String pictureFile) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.row = row;
        this.column = column;
        this.index = index;
        this.isSubsprite = true;
        this.pictureFile = pictureFile;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, (int) gameObject.transform.position.x,
                (int) gameObject.transform.position.y, width, height, null);
    }

    @Override
    public Component copy() {
        if(!isSubsprite) {
            return new Sprite(this.image, pictureFile);
        } else {
            return new Sprite(image, row, column, index, pictureFile);
        }
    }

    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty("Sprite", tabSize));
        builder.append(addBooleanProperty("isSubsprite", isSubsprite, tabSize + 1, true, true));

        if (isSubsprite) {
            builder.append(addStringProperty("FilePath", pictureFile, tabSize + 1, true, true));
            builder.append(addIntProperty("row", row, tabSize + 1, true, true));
            builder.append(addIntProperty("column", column, tabSize + 1, true, true));
            builder.append(addIntProperty("index", index, tabSize + 1, true, false));
            builder.append(closeObjectProperty(tabSize));

            return builder.toString();
        }

        builder.append(addStringProperty("FilePath", pictureFile, tabSize + 1, true, false));
        builder.append(closeObjectProperty(tabSize));
        return builder.toString();
    }

    public static Sprite deserialize() {
        boolean isSubsprite = Parser.consumeBooleanProperty("isSubsprite");
        Parser.consume(',');
        String filePath = Parser.consumeStringProperty("FilePath");

        if (isSubsprite) {
            Parser.consume(',');
            Parser.consumeIntProperty("row");
            Parser.consume(',');
            Parser.consumeIntProperty("column");
            Parser.consume(',');
            int index = Parser.consumeIntProperty("index");
            if (!AssetPool.hasSpritesheet(filePath)) {
                System.out.println("Spritesheet '" + filePath + "' has not been loaded yet!");
                System.exit(-1);
            }
            Parser.consumeEndObjectProperty();
            return (Sprite)AssetPool.getSpritesheet(filePath).sprites.get(index).copy();
        }

        if (!AssetPool.hasSprite(filePath)) {
            System.out.println("Sprite '" + filePath + "' has not been loaded yet!");
            System.exit(-1);
        }

        Parser.consumeEndObjectProperty();
        return (Sprite)AssetPool.getSprite(filePath).copy();
    }
}