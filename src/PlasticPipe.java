import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;


/**
 * The plastic pipe of the game.
 */
public class PlasticPipe extends PipeSet{
    private final Image PIPE_IMAGE = new Image("res/level/plasticPipe.png");

    /**
     * Instantiates a new Plastic pipe.
     */
    public PlasticPipe(){
        super();
    }

    /**
     * Gets the top plastic pipe.
     * @return Rectangle The Rectangle of the top plastic pipe.
     */
    @Override
    public Rectangle getTopBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(super.getPipeX(), super.getTopPipeY()));
    }

    /**
     * Gets the bottom plastic pipe.
     * @return Rectangle The Rectangle of the bottom plastic pipe.
     */
    @Override
    public Rectangle getBottomBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(super.getPipeX(), super.getBottomPipeY()));

    }

    /**
     * Rendering the pipe set of the given type of pipe which is plastic.
     */
    @Override
    public void renderPipeSet() {
        PIPE_IMAGE.draw(super.getPipeX(), super.getTopPipeY());
        PIPE_IMAGE.draw(super.getPipeX(), super.getBottomPipeY(), super.getROTATOR());
    }

    /**
     * Update of the plastic pipe on every frame.
     */
    @Override
    public void update() {
        if(!super.isDestroyed()) {
            renderPipeSet();
            pipeX -= super.getPipeSpeed();
        }
    }
}
