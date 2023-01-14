package com.main.ui;

import com.main.component.SnapToGrid;
import com.main.component.Sprite;
import com.main.engine.Component;
import com.main.engine.GameObject;
import com.main.engine.LevelEditorScene;
import com.main.engine.Window;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class MenuItem extends Component {
    private int x, y, width, height;
    Sprite buttonSprite, hoverSprite, myImage;
    public boolean isSelected;

    private int bufferX, bufferY;


    public MenuItem(int x, int y, int width, int height, Sprite buttonSprite, Sprite hoverSprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonSprite = buttonSprite;
        this.hoverSprite = hoverSprite;
        this.isSelected = false;
    }

    @Override
    public void start() {
        myImage = gameObject.getComponent(Sprite.class);
        this.bufferX = (int) ((this.width / 2.0) - (myImage.width / 2.0));
        this.bufferY = (int) ((this.height / 2.0) - (myImage.height / 2.0));
    }

    @Override
    public void update(double dt) {
        if (!isSelected && (Window.getWindow().mouseListener.x > this.x && Window.getWindow().mouseListener.x <= this.x + this.width)
                && (Window.getWindow().mouseListener.y > this.y && Window.getWindow().mouseListener.y <= this.y + this.height)) {
            if (Window.getWindow().mouseListener.mousePressed && Window.getWindow().mouseListener.mouseButton == MouseEvent.BUTTON1) {
                GameObject obj = gameObject.copy();
                obj.removeComponent(MenuItem.class);
                LevelEditorScene scene = (LevelEditorScene) Window.getWindow().getCurrentScene();

                SnapToGrid snapToGrid = scene.mouseCursor.getComponent(SnapToGrid.class);
                obj.addComponent(snapToGrid);
                scene.mouseCursor = obj;
                isSelected = true;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(this.buttonSprite.image, this.x, this.y, this.width, this.height, null);
        g2.drawImage(myImage.image, this.x + this.bufferX, this.y + this.bufferY, myImage.width, myImage.height, null);
        if (isSelected) {
            g2.drawImage(this.hoverSprite.image, this.x, this.y, this.width, this.height, null);
        }
    }

    @Override
    public Component copy() {
        return null;
    }

    @Override
    public String serialize(int tabSize) {
        return "";
    }
}
