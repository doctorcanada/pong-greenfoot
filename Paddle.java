import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Defines a Paddle object controlled by the player
 * 
 * @author bcanada
 * @version 2014.11.10
 */
public class Paddle extends SmoothMover
{
    /* FIELDS */
    private Game playField; // this object's copy of a reference to the game object 
    
    /* CONSTRUCTORS */
    public Paddle()
    {
    } // end Paddle constructor
    
    /**
     * Act - do whatever the Paddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        /* 
         * set up reference to the Game world object in the act() 
         * method to avoid a NullPointerException
         */
        playField = (Game)getWorld(); // note that we need to explicitly cast to type Game
                                      // if we want playField to refer to the game object (and
                                      // not the World superclass)

        // behavior depends on the game status
        if ( playField.getGameStatus() == Status.NOT_PLAYING )
        {
            // don't do anything (I'll make this more interesting later)
        }
        else if ( playField.getGameStatus() == Status.PLAYING )
        {
            if ( Greenfoot.isKeyDown("up") ) 
            {
                if ( getExactY() > 64 ) // 32 should be a constant in the Game class
                {
                    setLocation( getExactX(), getExactY() - 5.0 );
                }
            }
            else if ( Greenfoot.isKeyDown("down") ) 
            {
  
                if ( getExactY() < 416 ) // 448 should be a constant in the Game class
                {
                    setLocation( getExactX(), getExactY() + 5.0 );
                }
            }           
        } 
        else if ( playField.getGameStatus() == Status.GAME_OVER )
        {
            playField.removeObject( this );
        }
        
    } // end method act   
    
} // end class Paddle
