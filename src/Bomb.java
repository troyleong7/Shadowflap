import bagel.Image;

import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * The bomb weapon of the game.
 */
public class Bomb extends Weapon{
    private final Image BOMB_IMAGE = new Image("res/level-1/bomb.png");
    private final double SHOOT_RANGE = 50;
    private double frameCount;

    /**
     * Instantiates a new Bomb.
     */
    public Bomb(){
        super();
        frameCount = 0;
    }

    /**
     * Update of the bomb weapon on every frame.
     */
    @Override
    public void update(){
        if(!isWeaponRemoved()) {
            renderWeapon();
            if (!super.isObtained()) {
                weaponX -= getWeaponSpeed();
            }
            if (super.isObtained() && super.isShot()) {
                frameCount++;
                weaponX += getShootSpeed();
                if (frameCount == SHOOT_RANGE) {
                    removeWeapon();
                }
            }
        }
    }

    /**
     * Render the bomb weapon with the given bomb image.
     */
    @Override
    public void renderWeapon(){
        BOMB_IMAGE.draw(super.getWeaponX(), super.getWeaponY());
    }

    /**
     * Gets the Rectangle of the bomb weapon box.
     * @return Rectangle the rectangle box of the bomb weapon.
     */
    @Override
    public Rectangle getWeaponBox(){
        return BOMB_IMAGE.getBoundingBoxAt(new Point(super.getWeaponX(), super.getWeaponY()));
    }
}
