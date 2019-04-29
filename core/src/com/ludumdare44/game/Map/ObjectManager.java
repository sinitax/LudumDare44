package com.ludumdare44.game.Map;

import com.badlogic.gdx.math.*;
import com.ludumdare44.game.Physics.Obstacle;
import com.ludumdare44.game.Physics.PhysicsObject;
import com.ludumdare44.game.Physics.VisualPhysObject;

import java.util.ArrayList;
import java.util.Collections;

public class ObjectManager {
    private ArrayList<PhysicsObject> physobjects;

    public ArrayList<PhysicsObject> getObjects() { return physobjects; }

    public void setObstacles(Obstacle[] obstacles) {
        Collections.addAll(physobjects, obstacles);
    }

    public static Rectangle toRectangle(PhysicsObject vpo) {
        return new Rectangle(vpo.getPos().x + vpo.getHitboxOffset().x - vpo.getHitbox().x/2, vpo.getPos().y + vpo.getHitboxOffset().y - vpo.getHitbox().y/2, vpo.getHitbox().x, vpo.getHitbox().y);
    }

    public static void rectangleCollision(PhysicsObject vpo, Rectangle ro, float delta) {
        Rectangle r = ObjectManager.toRectangle(vpo);
        Vector2 speed = vpo.getSpeed();
        Vector2 pos = vpo.getPos();
        Vector2 ppos = pos.cpy().sub(vpo.getSpeed());
        if (r.overlaps(ro)) {
            boolean vertical = r.y + r.getHeight() > ro.getY() || r.y < ro.getY() + ro.getHeight();
            boolean horizontal = r.x + r.getWidth() < ro.getX() || r.x > ro.getX() + ro.getWidth();
            if (horizontal) {
                if (ppos.x + r.getWidth()/2 < ro.getX()) {
                    pos.x = ro.getX() - r.getWidth() / 2 - .1f;
                } else if (ppos.x - r.getWidth()/2 > ro.getX() + ro.getWidth()) {
                    pos.x = ro.getX() + ro.getWidth() + r.getWidth() / 2 + .1f;
                }
                speed.x = 0;
            }
            if (vertical) {
                if (ppos.y + r.getHeight()/2 < ro.getY()) {
                    pos.y = ro.getY() - r.getHeight() / 2 - .1f;
                } else if (ppos.y - r.getWidth()/2 > ro.getY() + ro.getHeight()) {
                    pos.y = ro.getY() + ro.getHeight() + r.getHeight() / 2 + .1f;
                }
                speed.y = 0;
            }
            vpo.setSpeed(speed);
            vpo.setFspeed(new Vector2(0, 0));
            vpo.setPos(pos);
        }
    }

    private void checkCollisions(float delta) {
        //With other objects
        for (int i = 0; i < physobjects.size(); i++) {
            for (int j = 0; j < physobjects.size(); j++) {
                if (i == j) continue;
                PhysicsObject p1 = physobjects.get(i);
                PhysicsObject p2 = physobjects.get(j);
                if (Intersector.overlaps(toRectangle(p1), toRectangle(p2))) {
                    p1.onCollision(p2, delta);
                }
            }
        }
    }

    public void update(float delta) {
        //check physics collisions
        ArrayList<PhysicsObject> deleteList = new ArrayList<>();
        for (PhysicsObject obj : physobjects) {
            if (obj instanceof VisualPhysObject) {
                VisualPhysObject vobj = (VisualPhysObject) obj;
                if (!vobj.isStagnant()) vobj.update(delta);
                if (vobj.isDestroyed()) deleteList.add(obj);
            }
        }
        for (PhysicsObject o: deleteList) {
            physobjects.remove(o);
        }
        checkCollisions(delta);
    }

    public void addObject(PhysicsObject obj) {
        physobjects.add(obj);
    }

    public ObjectManager() {
        physobjects = new ArrayList<>();
    }
}
