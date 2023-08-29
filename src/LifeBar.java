import bagel.Image;

/**
 * The Life bar of the Bird
 */
public class LifeBar {
    private final Image FULL_LIFE_IMAGE = new Image("res/level/fullLife.png");
    private final Image NO_LIFE_IMAGE = new Image("res/level/noLife.png");
    private final double LIFE_X = 100;
    private final double LIFE_Y = 15;
    private final double LIFE_GAP = 50;
    private int maxLife;
    private int fullLife;

    /**
     * Instantiates a new Life bar.
     */
    public LifeBar(){
        maxLife = 3;
        fullLife = 3;
    }

    /**
     * Update of the game on every frame.
     * Rendering the life bar.
     */
    public void update() {
        renderLife();
    }

    /**
     * Render the life bar on the given number of full life image and empty life image.
     */
    public void renderLife(){
        for(int i=0; i<fullLife; i++){
            FULL_LIFE_IMAGE.drawFromTopLeft(LIFE_X + (i*LIFE_GAP), LIFE_Y);
        }
        for(int i=fullLife; i<maxLife; i++){
            NO_LIFE_IMAGE.drawFromTopLeft(LIFE_X + (i*LIFE_GAP), LIFE_Y);
        }
    }

    /**
     * Remove one full life from life bar.
     */
    public void removeLife(){
        fullLife -= 1;
    }

    /**
     * Get the number of full life of the life bar
     * @return int the remaining full life of the life bar.
     */
    public int getFullLife(){
        return fullLife;
    }

    /**
     * Level up the life bar from Level 0 to Level 1.
     */
    public void level1Life(){
        fullLife = 6;
        maxLife = 6;
    }
}
