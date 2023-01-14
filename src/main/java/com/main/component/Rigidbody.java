package com.main.component;

import com.main.engine.Component;
import com.main.util.Constants;
import com.main.util.Vector2;

public class Rigidbody extends Component {

    public Vector2 velocity;

    public Rigidbody(Vector2 velocity) {
        this.velocity = velocity;
    }

    @Override
    public void update(double dt) {
        gameObject.transform.position.y += velocity.y * dt;
        gameObject.transform.position.x += velocity.x * dt;

        velocity.y += Constants.GRAVITY * dt;

        if (Math.abs(velocity.y) > Constants.TERMINAL_VELOCITY) {
            velocity.y = Math.signum(velocity.y) * Constants.TERMINAL_VELOCITY;
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
