import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.lang.Math;

/**
 * The Bird of the game.
 */
public class Bird {
    private final Image LEVEL0_WING_DOWN_IMAGE = new Image("res/level-0/birdWingDown.png");
    private final Image LEVEL0_WING_UP_IMAGE = new Image("res/level-0/birdWingUp.png");
    private final Image LEVEL1_WING_DOWN_IMAGE = new Image("res/level-1/birdWingDown.png");
    private final Image LEVEL1_WING_UP_IMAGE = new Image("res/level-1/birdWingUp.png");
    private final double X = 200;
    private final double FLY_SIZE = 6;
    private final double FALL_SIZE = 0.4;
    private final double INITIAL_Y = 350;
    private final double Y_TERMINAL_VELOCITY = 10;
    private final double SWITCH_FRAME = 10;
    private int frameCount = 0;
    private double y;
    private double yVelocity;
    private Rectangle boundingBox;
    private boolean hasWeapon;

    /**
     * Instantiates a new Bird.
     */
    public Bird() {
        hasWeapon = false;
        y = INITIAL_Y;
        yVelocity = 0;
        boundingBox = LEVEL0_WING_DOWN_IMAGE.getBoundingBoxAt(new Point(X, y));
    }

    /**
     * Update of the bird on every frame.
     * @param input  The input of which key was pressed on the keyboard.
     * @param level1 The status of the game, whether level 1 has been reached or not.
     */
    public void update(Input input, boolean level1) {
        frameCount += 1;
        if(!level1) {
            if (input.wasPressed(Keys.SPACE)) {
                yVelocity = -FLY_SIZE;
                LEVEL1_WING_DOWN_IMAGE.draw(X, y);
            } else {
                yVelocity = Math.min(yVelocity + FALL_SIZE, Y_TERMINAL_VELOCITY);
                if (frameCount % SWITCH_FRAME == 0) {
                    LEVEL0_WING_UP_IMAGE.draw(X, y);
                    boundingBox = LEVEL0_WING_UP_IMAGE.getBoundingBoxAt(new Point(X, y));
                } else {
                    LEVEL0_WING_DOWN_IMAGE.draw(X, y);
                    boundingBox = LEVEL0_WING_DOWN_IMAGE.getBoundingBoxAt(new Point(X, y));
                }
            }
        }
        else{
            if (input.wasPressed(Keys.SPACE)) {
                yVelocity = -FLY_SIZE;
                LEVEL1_WING_DOWN_IMAGE.draw(X, y);
            } else {
                yVelocity = Math.min(yVelocity + FALL_SIZE, Y_TERMINAL_VELOCITY);
                if (frameCount % SWITCH_FRAME == 0) {
                    LEVEL1_WING_UP_IMAGE.draw(X, y);
                    boundingBox = LEVEL1_WING_UP_IMAGE.getBoundingBoxAt(new Point(X, y));
                } else {
                    LEVEL1_WING_DOWN_IMAGE.draw(X, y);
                    boundingBox = LEVEL1_WING_DOWN_IMAGE.getBoundingBoxAt(new Point(X, y));
                }
            }
        }

        y += yVelocity;
    }

    /**
     * Gets y-coordinate of the bird.
     * @return double y-coordinate of the bird
     */
    public double getY() {
        return y;
    }

    /**
     * Gets x-coordinate of the bird.
     * @return double x-coordinate of the bird.
     */
    public double getX() {
        return X;
    }

    /**
     * Gets the Rectangle box of the Bird image.
     * @return Rectangle The rectangle of the bird image.
     */
    public Rectangle getBox() {
        return boundingBox;
    }

    /**
     * Get the status of whether the bird is in possession of a weapon.
     * @return boolean The status of whether the bird has a weapon.
     */
    public boolean getWeapon(){
        return hasWeapon;
    }

    /**
     * Respawn the bird to its initial coordinates.
     */
    public void respawnBird(){
        y = INITIAL_Y;
    }

    /**
     * The bird obtains a weapon.
     * @param w the weapon obtained by the bird.
     */
    public void obtainWeapon(Weapon w){
        hasWeapon = true;
        w.obtain();
    }

    /**
     * The bird does not have a weapon anymore.
     */
    public void noWeapon(){
        hasWeapon = false;
    }

}