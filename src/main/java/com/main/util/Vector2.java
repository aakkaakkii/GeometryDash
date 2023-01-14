package com.main.util;

import com.main.file.Parser;
import com.main.file.Serialize;

public class Vector2 extends Serialize {
    public float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this.x=0;
        this.y=0;
    }

    public Vector2 copy() {
        return new Vector2(x, y);
    }


    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(addFloatProperty("x", x, tabSize, true, true));
        builder.append(addFloatProperty("y", y, tabSize, true, false));

        return builder.toString();
    }

    public static Vector2 deserialize() {
        float x = Parser.consumeFloatProperty("x");
        Parser.consume(',');
        float y = Parser.consumeFloatProperty("y");

        return new Vector2(x, y);
    }
}
