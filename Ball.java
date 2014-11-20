import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Defines the ball object and its movement in response to
 * interactions with the other game elements.
 * 
 * @author bcanada
 * @version 2014.11.10
 */
public class Ball extends SmoothMover
{
    /* FIELDS */
    private Game playField; // this is the local reference to the game object 
    
    private double directionX; // -1 for left, +1 for right
    private double directionY; // -1 for up, +1 for down
    
    private double speed;
    
    // constants
    private final int PLAYER_ONE = 1;
    private final int PLAYER_TWO = 2;
    
    /* CONSTRUCTORS */
    public Ball()
    {
        super( new Vector( -45 + Greenfoot.getRandomNumber( 90 ), 3.0 ));
        if ( Greenfoot.getRandomNumber( 2 ) == 0 )
        {
            getMovement().revertHorizontal();
        }
    } // end Ball constructor
    
    /* METHODS */
    /**
     * Act - do whatever the Ball wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // need to explicitly cast as the World subclass (i.e., Game)
        playField = (Game)getWorld();
        
        // behavior depends on the game status
        if ( playField.getGameStatus() == Status.NOT_PLAYING )
        {
            // don't do anything
        }
        else if ( playField.getGameStatus() == Status.PLAYING )
        {
            move();
           
            /* 
             * Check for intersection with the player's paddle 
             */
            Paddle possiblePaddle = (Paddle)( getOneObjectAtOffset(-4,0,Paddle.class) );
            if ( possiblePaddle != null )
            {
                // Display debug message
                System.out.printf("Ball has intersected with %s\n",possiblePaddle.toString());
                
                // compute return angle and increase speed by 10%
                // TODO: Since this code is duplicated elsewhere, I probably 
                //       should move it into a separate method in this class
                double hitLocation = (this.getExactY()-4.0) - (possiblePaddle.getExactY()-32.0);
                int returnAngle = (int)( 315.0 + (90.0/64.0)*hitLocation );
                getMovement().setDirection( returnAngle );
                getMovement().scale( 1.1 );
                playField.playHighBlip();
            } // end if
            
            /* 
             * Check for intersection with the CPU paddle 
             */
            CPUPaddle possibleCPUPaddle = (CPUPaddle)( getOneObjectAtOffset(+4,0,CPUPaddle.class) );
            if ( possibleCPUPaddle != null )
            {
                // Display debug message
                System.out.printf("Ball has intersected with %s\n",possibleCPUPaddle.toString());
                
                // compute return angle and increase speed by 10%
                // TODO: Since this code is duplicated elsewhere, I probably 
                //       should move it into a separate method in this class
                double hitLocation = (this.getExactY()-4.0) - (possibleCPUPaddle.getExactY()-32.0);
                int returnAngle = (int)( 225.0 - (90.0/64.0)*hitLocation );
                getMovement().setDirection( returnAngle );
                getMovement().scale( 1.1 );
                playField.playHighBlip();
            } // end if
            
            /*
             * If the ball hits the boundary, simply have it reflect off the 
             * surface in the opposite Y direction 
             */
            Boundary possibleTopBoundary = (Boundary)( getOneObjectAtOffset(0,-4,Boundary.class) );
            Boundary possibleBottomBoundary = (Boundary)( getOneObjectAtOffset(0,4,Boundary.class) );
            if ( possibleTopBoundary != null || possibleBottomBoundary != null )
            {
                getMovement().revertVertical();
                playField.playLowBlip();
            } // end if           
            
            // check if the ball has passed the player's paddle
            if ( getExactX() <= 0 )
            {
                playField.updateScore( PLAYER_TWO );
                
                // TODO: Since this code is duplicated elsewhere, I probably 
                //       should move it into a separate method in this class
                setLocation( playField.GAME_WIDTH / 2, playField.GAME_HEIGHT / 2 );
                getMovement().setNeutral();
                getMovement().add( new Vector( -45 + Greenfoot.getRandomNumber( 90 ), 3.0 ) );
                
                if ( Greenfoot.getRandomNumber( 2 ) == 0 )
                {
                    getMovement().revertHorizontal();
                } // end inner if
                
                playField.playLongBeep();
            } // end outer if
            
            // check if the ball has passed the CPU's paddle
            if ( getExactX() >= playField.GAME_WIDTH )
            {
                playField.updateScore( PLAYER_ONE );
                
                // TODO: Since this code is duplicated elsewhere, I probably 
                //       should move it into a separate method in this class
                setLocation( playField.GAME_WIDTH / 2, playField.GAME_HEIGHT / 2 );
                getMovement().setNeutral();
                getMovement().add( new Vector( -45 + Greenfoot.getRandomNumber( 90 ), 3.0 ) );
                
                if ( Greenfoot.getRandomNumber( 2 ) == 0 )
                {
                    getMovement().revertHorizontal();
                } // end inner if
                
                playField.playLongBeep();
            } // end outer if
            
        } 
        else if ( playField.getGameStatus() == Status.GAME_OVER )
        {
            playField.removeObject( this );
        } // end multiway if/else
        
    } // end method act   

} // end class Ball
