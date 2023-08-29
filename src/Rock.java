import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * The rock weapon of the game.
 */
public class Rock extends Weapon {
    private final Image ROCK_IMAGE = new Image("res/level-1/rock.png");
    private final double SHOOT_RANGE = 25;
    private double frameCount;

    /**
     * Instantiates a new Rock.
     */
    public Rock(){
        super();
        frameCount = 0;
    }

    /**
     * Update of the rock weapon on every frame.
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
     * Render the rock weapon with the given rock image.
     */
    @Override
    public void renderWeapon(){
        ROCK_IMAGE.draw(super.getWeaponX(), super.getWeaponY());
    }

    /**
     * Gets the Rectangle of the rock weapon box.
     * @return Rectangle the rectangle box of the rock weapon.
     */
    @Override
    public Rectangle getWeaponBox(){
        return ROCK_IMAGE.getBoundingBoxAt(new Point(super.getWeaponX(), super.getWeaponY()));
    }
}
