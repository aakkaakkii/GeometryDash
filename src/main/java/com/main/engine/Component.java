package com.main.engine;

import com.main.file.Serialize;

import java.awt.Graphics2D;

public abstract class Component extends Serialize {

    public GameObject gameObject;

    public void update(double dt) {

    }


    public void draw(Graphics2D g2) {

    }

    public void start() {

    }


    public abstract Component copy();

}
