import bagel.*;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2021
 *
 * Please filling your name below
 * @author: YUN KENG LEONG
 */
public class ShadowFlap extends AbstractGame {
    private final Image LEVEL0_BACKGROUND_IMAGE = new Image("res/level-0/background.png");
    private final Image LEVEL1_BACKGROUND_IMAGE = new Image("res/level-1/background.png");
    private final String START_MSG = "PRESS SPACE TO START";
    private final String ENTER_LEVEL1_MSG = "LEVEL-UP!";
    private final String SHOOT_MSG = "PRESS 'S' TO SHOOT";
    private final String GAME_OVER_MSG = "GAME OVER!";
    private final String CONGRATS_MSG = "CONGRATULATIONS!";
    private final String SCORE_MSG = "SCORE: ";
    private final String FINAL_SCORE_MSG = "FINAL SCORE: ";
    private final int FONT_SIZE = 48;
    private final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    private final int SCORE_MSG_OFFSET = 75;
    private final int SHOOT_MSG_OFFSET = 68;
    private int timescale = 1;
    private double pipeSpawnFrame = 100.0;
    private Bird bird;
    private PlasticPipe plasticPipe;
    private SteelPipe steelPipe;
    private Rock rock;
    private Bomb bomb;
    private LifeBar lifeBar;
    private ArrayList<PipeSet> pipe;
    private ArrayList<Weapon> weapon;
    private ArrayList<Weapon> weaponShot;
    private int score;
    private int frameCount;
    private int levelUpFrameCount;
    private boolean level1;
    private boolean gameOn;
    private boolean win;

    /**
     * Instantiates a new Shadow flap.
     */
    public ShadowFlap() {
        super(1024, 768, "ShadowFlap");
        bird = new Bird();
        lifeBar = new LifeBar();
        pipe = new ArrayList<>();
        weapon = new ArrayList<>();
        weaponShot = new ArrayList<>();
        plasticPipe = new PlasticPipe();
        steelPipe = new SteelPipe();
        rock = new Rock();
        bomb = new Bomb();
        score = 0;
        frameCount = 0;
        levelUpFrameCount = 0;
        level1 = false;
        gameOn = false;
        win = false;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     * The game runs at 60 FPS.
     * All given parameters are suitable for the game to run smoothly on the given FPS.
     */
    @Override
    public void update(Input input) {
        // for level 0 background
        if(!level1) {
            LEVEL0_BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        }

        // for level 1 background
        if(level1){
            LEVEL1_BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        }

        // game exits when escape key is pressed
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // game has not started
        if (!gameOn && levelUpFrameCount == 0) {
            renderInstructionScreen(input);
        }

        // game on level 1 has not started
        if (!gameOn && levelUpFrameCount>150) {
            score = 0;
            level1 = true;
            PipeSet.changePipeSpawn();
            renderLevel1InstructionScreen(input);
        }

        // game over
        if(lifeBar.getFullLife() <= 0){
            renderGameOverScreen();
        }

        // bird out of bound
        if(birdOutOfBound()){
            lifeBar.removeLife();
            bird.respawnBird();
        }


        // game won
        if (win) {
            renderWinScreen();
        }

        // level up moment and reset every parameter
        if(score == 10 && levelUpFrameCount <= 150){
            renderLevelUpScreen();
            gameOn = false;
            levelUpFrameCount ++;
            frameCount = 0;
            timescale = 1;
            pipeSpawnFrame = 100.0;
            pipe = new ArrayList<>();
            lifeBar.level1Life();
            PipeSet.initialPipeSpeed();
            Weapon.initialWeaponSpeed();
        }

        // when L key is pressed to increase timescale
        if (input.wasPressed(Keys.L) && timescale < 5) {
            timescale += 1;
            pipeSpawnFrame /= 1.5;
            pipeSpawnFrame = Math.round(pipeSpawnFrame);
            PipeSet.speedUpPipe();
            Weapon.speedUpWeapon();
        }

        // when K key is pressed to decrease timescale
        if (input.wasPressed(Keys.K) && timescale > 1) {
            timescale -= 1;
            pipeSpawnFrame *= 1.5;
            if(pipeSpawnFrame > 100.0){
                pipeSpawnFrame = 100.0;
            }
            else {
                pipeSpawnFrame = Math.round(pipeSpawnFrame);
            }
            PipeSet.slowDownPipe();
            Weapon.slowDownWeapon();
        }

        // game is active
        if (gameOn && !win && lifeBar.getFullLife() > 0) {
            bird.update(input, level1);

            // game on level 0
            if(!level1) {
                if (frameCount % pipeSpawnFrame == 0.0) {
                    pipe.add(plasticPipe);
                    plasticPipe = new PlasticPipe();
                }
            }

            // game on level 1
            else{
                // random type of pipe is spawned
                if (frameCount % pipeSpawnFrame == 0.0) {
                    double rand = Math.random();
                    if(rand <= 0.5){
                        pipe.add(plasticPipe);
                        plasticPipe = new PlasticPipe();
                    }
                    else{
                        pipe.add(steelPipe);
                        steelPipe = new SteelPipe();
                    }
                }
                else if (frameCount % pipeSpawnFrame == Math.round(pipeSpawnFrame/2.0)) {
                    double rand = Math.random();
                    if(rand <= 0.5) {
                        // spawn rate of weapon is random
                        double randWeapon = Math.random();
                        // type of weapon spawned is also random
                        if (randWeapon <= 0.5) {
                            weapon.add(rock);
                            rock = new Rock();
                        }
                        else {
                            weapon.add(bomb);
                            bomb = new Bomb();
                        }
                    }
                }
            }

            frameCount++;

            // for spawning pipe that was added in pipe set arraylist
            for(PipeSet p : pipe) {
                p.update();
                detectBirdCollision(p);
                updateScore(p);
                if(level1){
                    for(Weapon w : weaponShot){
                        detectWeaponCollision(p, w);
                    }
                }
                if(p.getTopBox().right() <= 0){
                    p.destroy();
                }
            }

            // for spawning weapon that was added in weapon arraylist
            for(Weapon w : weapon){
                w.update();
                detectObtain(w);
                if(bird.getWeapon() && w.isObtained() && !w.isShot()) {
                    w.weaponX = bird.getBox().right();
                    w.weaponY = bird.getY();
                    if (input.wasPressed(Keys.S)) {
                        bird.noWeapon();
                        w.weaponShoot();
                        weaponShot.add(w);
                    }
                }
                if(w.getWeaponX() <= 0){
                    w.removeWeapon();
                }
            }

            lifeBar.update();
        }

    }

    /**
     * Checking if the bird is out of bound.
     * @return boolean The status of whether the bird is out of bound.
     */
    public boolean birdOutOfBound() {
        return (bird.getY() > Window.getHeight()) || (bird.getY() < 0);
    }

    /**
     * Render instruction screen on level 0.
     * @param input The input of the key pressed on keyboard.
     */
    public void renderInstructionScreen(Input input) {
        FONT.drawString(START_MSG, (Window.getWidth()/2.0-(FONT.getWidth(START_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        if (input.wasPressed(Keys.SPACE)) {
            gameOn = true;
        }
    }

    /**
     * Render level 1 instruction screen on level 1.
     * @param input The input of the key pressed on keyboard.
     */
    public void renderLevel1InstructionScreen(Input input) {
        FONT.drawString(START_MSG, (Window.getWidth()/2.0-(FONT.getWidth(START_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        FONT.drawString(SHOOT_MSG, (Window.getWidth()/2.0-(FONT.getWidth(SHOOT_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SHOOT_MSG_OFFSET);
        if (input.wasPressed(Keys.SPACE)) {
            gameOn = true;
        }
    }

    /**
     * Render game over screen.
     */
    public void renderGameOverScreen() {
        FONT.drawString(GAME_OVER_MSG, (Window.getWidth()/2.0-(FONT.getWidth(GAME_OVER_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    /**
     * Render win screen.
     */
    public void renderWinScreen() {
        FONT.drawString(CONGRATS_MSG, (Window.getWidth()/2.0-(FONT.getWidth(CONGRATS_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    /**
     * Render level up screen.
     */
    public void renderLevelUpScreen() {
        FONT.drawString(ENTER_LEVEL1_MSG, (Window.getWidth()/2.0-(FONT.getWidth(ENTER_LEVEL1_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
    }

    /**
     * Detect collision between the bird and the pipe set.
     * @param p The pipe set selected.
     */
    public void detectBirdCollision(PipeSet p) {
        // check for collision with plastic pipe
        if(p instanceof PlasticPipe){
            if(bird.getBox().intersects(p.getTopBox()) || bird.getBox().intersects(p.getBottomBox())){
                lifeBar.removeLife();
                p.destroy();
            }
        }

        // check for collision with steel pipe
        else if(p instanceof SteelPipe){
            if(bird.getBox().intersects(p.getTopBox()) || bird.getBox().intersects(p.getBottomBox()) ||
                    bird.getBox().intersects(((SteelPipe)p).getTopFlame()) || bird.getBox().intersects(((SteelPipe) p).getBottomFlame())) {
                lifeBar.removeLife();
                p.destroy();
            }
        }
    }

    /**
     * Detect collision between pipe set and weapon shot.
     * @param p The pipe set selected
     * @param w The weapon shot
     */
    public void detectWeaponCollision(PipeSet p, Weapon w){
        // check collision of bomb and pipe
        if(w instanceof Bomb) {
            if (w.getWeaponBox().intersects(p.getTopBox()) || w.getWeaponBox().intersects(p.getBottomBox())) {
                p.destroy();
                w.removeWeapon();
                score += 1;
            }
        }

        // check collision of rock and pipe
        else if(w instanceof Rock){
            if(p instanceof PlasticPipe){
                if (w.getWeaponBox().intersects(p.getTopBox()) || w.getWeaponBox().intersects(p.getBottomBox())) {
                    p.destroy();
                    w.removeWeapon();
                    score += 1;
                }
            }
            else{
                if (w.getWeaponBox().intersects(p.getTopBox()) || w.getWeaponBox().intersects(p.getBottomBox())) {
                    w.removeWeapon();
                }
            }
        }

    }

    /**
     * Detect if the bird obtain the weapon
     * @param w The weapon intersected with the bird
     */
    public void detectObtain(Weapon w){
        if(!bird.getWeapon() && !w.isObtained() && bird.getBox().intersects(w.getWeaponBox())){
            bird.obtainWeapon(w);
        }
    }

    /**
     * Update the score of the game.
     * @param p The type of pipe set added in.
     */
    public void updateScore(PipeSet p) {
        if (bird.getX() > p.getTopBox().right() && bird.getX() - p.getTopBox().right() < p.getPipeSpeed()){
            score += 1;
        }
        String scoreMsg = SCORE_MSG + score;
        FONT.drawString(scoreMsg, 100, 100);

        // detect win
        if(score == 30){
            win = true;
        }
    }
}
