import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Game class is used to generate the objects used in the Pong game, 
 * while also controlling what happens during each pass through the "game loop,"
 * which here is implemented as the Game class's act() method.
 * 
 * One object of the Game class is generated at compile time. This object also
 * serves as the playing field itself. 
 * 
 * @author bcanada
 * @version 2014.11.10
 */
public class Game extends World
{
    /* FIELDS */
    // Composition (has-a) relationships with basic game elements
    private Paddle paddle1;           // game has paddle (for player 1)
    private CPUPaddle paddle2;        // game has paddle (for player 2)
    private Ball ball;                // game has ball
    private Boundary topBoundary;     // game has topBoundary
    private Boundary bottomBoundary;  // game has bottomBoundary
    private Score playerScoreDisplay; // game has playerScoreDisplay    
    private Score cpuScoreDisplay;    // game has cpuScoreDisplay    
    
    // score variables (for the numerical value of the score)
    private int playerScore;
    private int cpuScore;
    
    // 1-D array of GreenfootImage objects for scoreboard
    private GreenfootImage[] scoreNumber = new GreenfootImage[12];
    
    // sound objects
    private GreenfootSound lowBlip;
    private GreenfootSound highBlip;
    private GreenfootSound longBeep;
    
    // enum for game state
    private static Status gameStatus; // game has status (NOT_PLAYING, PLAYING, or GAME_OVER; 
                                      // see the Status enum under "Other classes") 
    
    // constants (adjust as needed)
    public static final int GAME_WIDTH = 640;
    public static final int GAME_HEIGHT = 480;
    public static final int MAX_SCORE = 3;
    
    /* CONSTRUCTORS */
    /**
     * Constructor for objects of class Game.
     * 
     */
    public Game()
    {    
        // Create a new world with 640x480 cells with a cell size of 1x1 pixels 
        // (and set the value of the 4th parameter, 'bounded', to false, so that
        //  we can allow objects like the ball and the top/bottom boundaries 
        //  to exist off-screen)
        super( GAME_WIDTH, GAME_HEIGHT, 1, false ); 
        
        // set the initial game status to NOT_PLAYING
        // TODO: I should incorporate a title screen or display instructions here
        gameStatus = Status.NOT_PLAYING;
        
        // instantiate the paddle and ball objects
        paddle1 = new Paddle();
        paddle2 = new CPUPaddle();
        ball = new Ball();
        topBoundary = new Boundary();
        bottomBoundary = new Boundary();
        
        // instantiate the scoreboard objects
        playerScoreDisplay = new Score();
        cpuScoreDisplay = new Score();
       
        // instantiate the game sound effect objects
        lowBlip  = new GreenfootSound("pong1.mp3");
        highBlip = new GreenfootSound("pong3.mp3");
        longBeep = new GreenfootSound("pong6.mp3");
        
        // initialize scores to zero
        playerScore = 0;
        cpuScore = 0;
        
        // set up scoreboard
        for ( int imageIndex = 0; imageIndex < 12; imageIndex++ )
        {
            String imageFilename = imageIndex + ".png";
            scoreNumber[imageIndex] = new GreenfootImage( imageFilename );
        } // end for loop
        
        // place the objects (coordinates refer to the CENTER point of each object, not the upper left corner)
        addObject( paddle1, (int)( 0.05*GAME_WIDTH ), ( GAME_HEIGHT / 2 ) );
        addObject( paddle2, (int)( 0.95*GAME_WIDTH ), ( GAME_HEIGHT / 2 ) );
        addObject( ball, ( GAME_WIDTH/2 ), ( GAME_HEIGHT / 2 ) );
        
        // place the scoreboard objects in the middle of each half
        addObject( playerScoreDisplay, (int)(0.25*GAME_WIDTH), (int)(0.15*GAME_HEIGHT) );
        addObject( cpuScoreDisplay, (int)(0.75*GAME_WIDTH), (int)(0.15*GAME_HEIGHT) );
        
        // place the top and bottom boundaries just off screen
        addObject( topBoundary, ( GAME_WIDTH / 2 ), 0 - topBoundary.getImage().getHeight()/2 );
        addObject( bottomBoundary, ( GAME_WIDTH / 2 ), GAME_HEIGHT + bottomBoundary.getImage().getHeight()/2 );
        
    } // end Game constructor
    
    /* DEFINE METHODS */
    /**
     * In the Game class, the act() method serves as the game loop for the 
     * overall game. (Keep in mind that all actors still have an act() method, which
     * defines what each actor object specifically does during the game loop.)
     */
    public void act()
    {
        /*
         * the game status determines what happens during each pass through the
         * game loop
         */
        if ( gameStatus == Status.NOT_PLAYING ) 
        {
            if ( Greenfoot.isKeyDown( "space" ) )
                {
                gameStatus = Status.PLAYING;
            } // end if
        }
        else if ( gameStatus == Status.PLAYING )
        {
            // end the game when one player reaches the maximum score 
            if ( playerScore == MAX_SCORE || cpuScore == MAX_SCORE )
            {
                gameStatus = Status.GAME_OVER;
            } // end if 
        }
        else if ( gameStatus == Status.GAME_OVER )
        {
            System.out.println( "Game over!" );
            Greenfoot.stop();            
        } // end multiway if/else
        
    } // end method act
    
    /**
     * Allows other objects to know the current state of the game
     */
    public Status getGameStatus()
    {
        return gameStatus;
        
    } // end method getGameStatus
    
    /**
     * Allows other objects to "see" the ball (more accurately, allows other objects
     * to have a reference to the ball object created here in the Game class)
     * 
     * @return a copy of the reference to the game ball object
     */
    public Ball getBall()
    {
        return ball;
    }
    
    /**
     * Allows other objects to "see" the player's paddle 
     * 
     * @return a copy of the reference to the player's paddle object
     */
    public Paddle getPaddle()
    {
        return paddle1;
    }  
    
    /**
     * Allows other objects to "see" the CPU's paddle 
     * 
     * @return a copy of the reference to the CPU paddle object
     */
    public CPUPaddle getCPUPaddle()
    {
        return paddle2;
    }      
        
    /**
     * Used to increment score for the indicated player 
     * 
     * @param playerNumber    1 = player, 2 = CPU
     */
    public void updateScore( int playerNumber )
    {
        switch ( playerNumber )
        {
            case 1:
                playerScore++;
                playerScoreDisplay.setImage( scoreNumber[playerScore] );
                break;
            case 2:
                cpuScore++;
                cpuScoreDisplay.setImage( scoreNumber[cpuScore] );
                break;
            default:
                break;
        } // end switch
        
        System.out.printf( "Player Score: %d\tCPU Score: %d\n", playerScore, cpuScore );
    } // end method updateScore
    
    /**
     * Plays the low blip sound (when the ball makes contact with a top or bottom boundary)
     */
    public void playLowBlip()
    {
        lowBlip.play();
    } // end method playLowBlip
    
    /**
     * Plays the high blip sound (when the ball makes contact with a paddle)
     */
    public void playHighBlip()
    {
        highBlip.play();
    } // end method playHighBlip

    /**
     * Plays the long beep sound (when the ball exits the screen and a point is scored)
     */
    public void playLongBeep()
    {
        longBeep.play();
    } // end method playLongBeep
    
} // end class Game
