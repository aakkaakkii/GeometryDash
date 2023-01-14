package com.main.ui;

import com.main.component.BoxBounds;
import com.main.component.Sprite;
import com.main.component.Spritesheet;
import com.main.datastructure.AssetPool;
import com.main.datastructure.Transform;
import com.main.engine.Component;
import com.main.engine.GameObject;
import com.main.util.Constants;
import com.main.util.Vector2;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class MainContainer extends Component {
    public List<GameObject> menuItems;

    public MainContainer() {
        this.menuItems = new ArrayList<>();
        init();
    }

    public void init() {
        Spritesheet groundSprites = AssetPool.getSpritesheet("assets/groundSprites.png");
        Spritesheet buttonSprites = AssetPool.getSpritesheet("assets/ui/buttonSprites.png");

        for (int i = 0; i < groundSprites.sprites.size(); i++) {
            Sprite currSprite = groundSprites.sprites.get(i);
            int x = Constants.BUTTON_OFFSET_X + (currSprite.column * Constants.BUTTON_WIDTH)
                    + (currSprite.column * Constants.BUTTON_SPACING_HZ);
            int y = Constants.BUTTON_OFFSET_Y + (currSprite.row * Constants.BUTTON_HEIGHT)
                    + (currSprite.row * Constants.BUTTON_SPACING_VT);

            GameObject obj = new GameObject("Generated", new Transform(new Vector2(x, y)), -1);
            obj.addComponent(currSprite.copy());
            MenuItem menuItem = new MenuItem(x, y, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT,
                    buttonSprites.sprites.get(0), buttonSprites.sprites.get(1));
            obj.addComponent(menuItem);
            obj.addComponent(new BoxBounds(Constants.TILE_WIDTH, Constants.TILE_HEIGHT));
            menuItems.add(obj);
        }
    }

    @Override
    public void start() {
        for (GameObject g : menuItems) {
            for (Component c : g.getAllComponents()) {
                c.start();
            }
        }
    }

    @Override
    public void update(double dt) {
        for (GameObject g : this.menuItems) {
            g.update(dt);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        for (GameObject g : this.menuItems) {
            g.draw(g2);
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
