import bagel.Window;
import bagel.util.Rectangle;


/**
 * The Weapon of the game.
 */
public abstract class Weapon {
    private static double weaponSpeed  = 5;
    private final double SHOOT_SPEED = 5;
    protected double weaponX = Window.getWidth();
    protected double weaponY;
    private boolean isObtained;
    private boolean isShot;
    private boolean weaponRemoved;

    /**
     * Instantiates a new Weapon.
     */
    public Weapon(){
        weaponRemoved = false;
        isShot = false;
        isObtained = false;
        weaponY = getRandomNumber(100,500);
    }

    /**
     * Render the weapon.
     */
    public abstract void renderWeapon();

    /**
     * Gets the Rectangle of the weapon box.
     * @return Rectangle the rectangle box of the weapon.
     */
    public abstract Rectangle getWeaponBox();

    /**
     * Update of the weapon on every frame.
     */
    public abstract void update();

    /**
     * Gets y-coordinate of the weapon.
     * @return double y-coordinate of the weapon
     */
    public double getWeaponY() {
        return weaponY;
    }

    /**
     * Gets x-coordinate of the weapon.
     * @return double x-coordinate of the weapon
     */
    public double getWeaponX() {
        return weaponX;
    }

    /**
     * The status of whether the weapon is obtained by the bird
     * @return boolean The status of the weapon whether it is picked up by the bird
     */
    public boolean isObtained() {
        return isObtained;
    }

    /**
     * Status of whether the weapon was shot out by the bird
     * @return boolean The status of the weapon being shot by the bird
     */
    public boolean isShot() {
        return isShot;
    }

    /**
     * Status of whether the weapon has been removed.
     * @return boolean The status of weapon being removed.
     */
    public boolean isWeaponRemoved() {
        return weaponRemoved;
    }

    /**
     * Weapon being obtained by the bird
     */
    public void obtain(){
        isObtained = true;
    }

    /**
     * Weapon being shot by the bird.
     */
    public void weaponShoot(){
        isShot = true;
    }

    /**
     * Gets the speed of the weapon.
     * @return double The speed of the weapon.
     */
    public static double getWeaponSpeed() {
        return weaponSpeed;
    }

    /**
     * Gets the shooting speed of the weapon.
     * @return double The weapon shooting speed.
     */
    public double getShootSpeed() {
        return SHOOT_SPEED;
    }

    /**
     * Remove the weapon.
     */
    public void removeWeapon(){
        weaponRemoved = true;
        weaponX = -200;
    }

    /**
     * Increase the weapon speed.
     */
    public static void speedUpWeapon(){
        weaponSpeed = weaponSpeed * 1.5;
    }

    /**
     * Decrease the weapon speed.
     */
    public static void slowDownWeapon(){
        weaponSpeed = weaponSpeed / 1.5;
    }

    /**
     * Reset the weapon speed.
     */
    public static void initialWeaponSpeed(){
        weaponSpeed = 5;
    }

    /**
     * Gets a random number between the min value and max value for the y-coordinate of weapon spawning.
     * @param min the min value.
     * @param max the max value.
     * @return double The random number between min value and max value.
     */
    public double getRandomNumber(double min, double max) {
        return ((Math.random() * (max - min)) + min);
    }
}
