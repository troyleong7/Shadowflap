import bagel.*;
import bagel.util.Rectangle;
import java.util.Random;

/**
 * The Pipe set of the game.
 */
public abstract class PipeSet implements Destroyable{
    private final int PIPE_GAP = 168;
    private final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);
    private final double GAP_RANGE = 200;
    private double topPipeY;
    private double bottomPipeY;
    protected double pipeX = Window.getWidth();
    private static double pipeSpeed = 5;
    private final double[] PIPE_TYPE = {-GAP_RANGE, 0.0, GAP_RANGE};
    private boolean destroyed;
    private double type;
    private static boolean typeChange = false;

    /**
     * Instantiates a new Pipe set.
     */
    public PipeSet() {
        if(!typeChange) {
            type = getRandom(PIPE_TYPE);
        }
        else{
            type = getRandomNumber(-GAP_RANGE, GAP_RANGE);
        }
        destroyed = false;
        topPipeY = -PIPE_GAP / 2.0 + type;
        bottomPipeY = Window.getHeight() + PIPE_GAP / 2.0 + type;
    }

    /**
     * Rendering the pipe set.
     */
    public abstract void renderPipeSet();

    /**
     * Destroying the given pipe set
     */
    public void destroy(){
        destroyed = true;
        pipeX = 0;
    }

    /**
     * Update of the pipe set on every frame.
     */
    public abstract void update();

    /**
     * Gets the top pipe.
     */
    public abstract Rectangle getTopBox();

    /**
     * Gets the bottom pipe.
     */
    public abstract Rectangle getBottomBox();

    /**
     * Increase the speed of the pipe set.
     */
    public static void speedUpPipe(){
        pipeSpeed = pipeSpeed * 1.5;
    }

    /**
     * Decrease the speed of the pipe set.
     */
    public static void slowDownPipe(){
        pipeSpeed = pipeSpeed / 1.5;
    }

    /**
     * Reset the pipe speed.
     */
    public static void initialPipeSpeed(){
        pipeSpeed = 5;
    }

    /**
     * Change the y-coordinate of the gap between both top and bottom as leveling up to Level 1.
     */
    public static void changePipeSpawn(){
        typeChange = true;
    }

    /**
     * Gets a random number in the array.
     * @param array The given array with fixed y-coordinate for Level 0.
     * @return double The random coordinate chosen.
     */
    public double getRandom(double[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    /**
     * Gets random number between the max value and the min value.
     * @param min the min value
     * @param max the max value
     * @return int the random value between max and min.
     */
    public double getRandomNumber(double min, double max) {
        return ((Math.random() * (max - min)) + min);
    }

    /**
     * Get the speed of the pipe.
     * @return double the speed of the pipe.
     */
    public double getPipeSpeed(){
        return pipeSpeed;
    }

    /**
     * Gets the rotator.
     * @return DrawOptions The way the image of pipe should rotate.
     */
    public DrawOptions getROTATOR() {
        return ROTATOR;
    }

    /**
     * Gets y-coordinate of top pipe.
     * @return double y-coordinate of top pipe.
     */
    public double getTopPipeY() {
        return topPipeY;
    }

    /**
     * Gets y-coordinate of bottom pipe.
     * @return double y-coordinate of bottom pipe.
     */
    public double getBottomPipeY() {
        return bottomPipeY;
    }

    /**
     * Gets x-coordinate of pipe set.
     * @return double x-coordinate of pipe set.
     */
    public double getPipeX() {
        return pipeX;
    }

    /**
     * For checking if the pipe is destroyed.
     * @return boolean The status of the pipe set whether it is destroyed.
     */
    public boolean isDestroyed() {
        return destroyed;
    }
}
