import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * The steel pipe of the game.
 */
public class SteelPipe extends PipeSet{
    private final Image PIPE_IMAGE = new Image("res/level-1/steelPipe.png");
    private final Image FLAME_IMAGE = new Image("res/level-1/flame.png");
    private final double FLAME_SPAWN_POINT = 10.0;
    private double flameX;
    private double topFlameY;
    private double bottomFlameY;
    private int frameCount;

    /**
     * Instantiates a new Steel pipe.
     */
    public SteelPipe(){
        super();
        frameCount = 0;
        flameX = pipeX;
        topFlameY = getTopBox().bottom() + FLAME_SPAWN_POINT;
        bottomFlameY = getBottomBox().top() - FLAME_SPAWN_POINT;
    }

    /**
     * Render the flame image.
     */
    public void renderFlame(){
        FLAME_IMAGE.draw(flameX, topFlameY);
        FLAME_IMAGE.draw(flameX, bottomFlameY, super.getROTATOR());
    }


    /**
     * Gets the flame coming out of the top steel pipe.
     * @return Rectangle The Rectangle of the flame coming out of the top steel pipe.
     */
    public Rectangle getTopFlame() {
        return FLAME_IMAGE.getBoundingBoxAt(new Point(flameX, topFlameY));
    }

    /**
     * Gets the flame coming out of the bottom steel pipe.
     * @return Rectangle The Rectangle of the flame coming out of the bottom steel pipe.
     */
    public Rectangle getBottomFlame() {
        return FLAME_IMAGE.getBoundingBoxAt(new Point(flameX, bottomFlameY));
    }

    /**
     * Gets the top steel pipe.
     * @return Rectangle The Rectangle of the top steel pipe.
     */
    @Override
    public Rectangle getTopBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(super.getPipeX(), super.getTopPipeY()));
    }

    /**
     * Gets the bottom steel pipe.
     * @return Rectangle The Rectangle of the bottom steel pipe.
     */
    @Override
    public Rectangle getBottomBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(super.getPipeX(), super.getBottomPipeY()));

    }

    /**
     * Rendering the pipe set of the given type of pipe which is steel.
     */
    @Override
    public void renderPipeSet() {
        PIPE_IMAGE.draw(super.getPipeX(), super.getTopPipeY());
        PIPE_IMAGE.draw(super.getPipeX(), super.getBottomPipeY() , super.getROTATOR());
    }

    /**
     * Update of the steel pipe on every frame.
     */
    @Override
    public void update() {
        frameCount++;
        if(!super.isDestroyed()) {
            renderPipeSet();
            if(frameCount % 50 > frameCount % 20){
                renderFlame();
            }
            if(frameCount % 50 == 0){
                frameCount = 0;
            }
            pipeX -= super.getPipeSpeed();
        }
        flameX = pipeX;
    }
}

