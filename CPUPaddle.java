import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Defines a CPUPaddle object (identical to a Paddle object, but controlled 
 * automatically using rudimentary artificial intelligence)
 * 
 * @author bcanada
 * @version 2014.11.10
 */
public class CPUPaddle extends Paddle
{
    /* FIELDS */
    private Game playField; // this object's copy of a reference to the game object 
    private int direction; // - 1 for up, +1 for down
    private double speed;
    
    private Ball theBall; // this object's copy of a reference to the ball object
    
    /* CONSTRUCTORS */
    public CPUPaddle()
    {
        // set initial values for fields
        /* TODO: Probably can make better use of the SmoothMover and Vector classes */
        direction = -1;
        speed = 2.0; 
    } // end CPUPaddle constructor
    
    /**
     * Act - do whatever the CPUPaddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // do this in the act() method to avoid a NullPointerException
        playField = (Game)getWorld();  // gets the reference to the current game object
        theBall = playField.getBall(); // gets the reference to the ball object
              
        // behavior depends on the game status
        if ( playField.getGameStatus() == Status.NOT_PLAYING )
        {
            // don't do anything
        }
        else if ( playField.getGameStatus() == Status.PLAYING )
        {
           
            if ( theBall.getExactY() > this.getExactY() )
            {
                direction = +1;        
            } // end if
            
            if ( theBall.getExactY() < this.getExactY() )
            {
                direction = -1;
            } // end if

            // If the ball is more than one "paddle-length" above the paddle's center
            // then increase the speed of the paddle
            if ( Math.abs( theBall.getExactY() - this.getExactY() ) > 64 )
            {
                speed = 6.0; // <-- may want to "tune" the speed to increase challenge
            }
            else if ( Math.abs( theBall.getExactY() - this.getExactY() ) > 32 )
            {
                speed = 3.0; // <-- may want to "tune" the speed to increase challenge
            }
            else
            {
                speed = 1.0; // <-- may want to "tune" the speed to increase challenge
            } // end multiway if/else
            
            // This ensures that the paddle can't move too far up or down
            int boundedY = (int)Math.max( 64, Math.min( 416, (getExactY() + direction * speed) ) );
            
            setLocation( getExactX(), boundedY );

        }
        else if ( playField.getGameStatus() == Status.GAME_OVER )
        {
            playField.removeObject( this );
        }// end multiway if/else
        
    } // end method act
    
} // end class CPUPaddle
