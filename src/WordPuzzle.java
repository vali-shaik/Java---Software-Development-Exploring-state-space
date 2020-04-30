import java.awt.Point;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

//WordPuzzle used to solve the puzzle and print it to the console
public class WordPuzzle 
{
	int choice; //no of choices used to undo while solving the puzzles
	boolean result;//return true if solution is found
	private final int height, width; // Board size
	private final char[] board; // Current board state.  _ is unfilled.  # is blocked.  other characters are filled.
	private final Set<String> words; // List of words
	private final Map<Point, Integer> vertical = new HashMap<>(), horizontal = new HashMap<>();  //Vertical and horizontal slots
	public int getChoice() 
	{
		return choice;
	}

	/*
	 * WordPuzzle constructor used to solve puzzle
	 * */
	public WordPuzzle ( List<String> lines ) 
	{
		// Parse input data
		final int[] sizes = Stream.of( lines.get(0).split( "\\s+" ) ).mapToInt( Integer::parseInt ).toArray();
		width = sizes[0];  
		height = sizes[1];
		board = String.join( "", lines.subList( 1, height+1 ) ).toCharArray();
		words = new HashSet<>( lines.subList( height+1, lines.size() ) );
		// Find horizontal slots then vertical slots
		for ( int y = 0, size ; y < height ; y++ )
			for ( int x = 0 ; x < width-1 ; x++ )
				if ( isSpace( x, y ) && isSpace( x+1, y ) ) 
				{
					// Find slot size
					for ( size = 2 ; x+size < width && isSpace( x+size, y ) ; size++ ); 
					horizontal.put( new Point( x, y ), size );
					// Skip past this horizontal slot
					x += size; 
				}//if
		for ( int x = 0, size ; x < width ; x++ )
			for ( int y = 0 ; y < height-1 ; y++ )
				if ( isSpace( x, y ) && isSpace( x, y+1 ) ) 
				{
					for ( size = 2 ; y+size < height && isSpace( x, y+size ) ; size++ ); // Find slot size
					vertical.put( new Point( x, y ), size );
					y += size; // Skip past this vertical slot
				}//if
		// Solve the WordPuzzle, horizontal first then vertical
		final boolean solved = solveHorizontal();
		result=solved;

	}//wordPuzzle

	// Helper functions to check or set board cell
	private char get ( int x, int y ) 
	{ 
		return board[ y * width + x ]; 
	}//get
	private void set ( int x, int y, char character ) 
	{ 
		board[ y * width + x ] = character; 
	}//set
	private boolean isSpace ( int x, int y ) 
	{
		return get( x, y ) == '_'; 
	}//isSpace

	// Fit all horizontal slots, when success move to solve vertical.
	private boolean solveHorizontal () 
	{
		return solve( horizontal, this::fitHorizontal, "horizontally", this::solveVertical );
	}//solveHorizontal

	// Fit all vertical slots, report success when done
	private boolean solveVertical () 
	{
		return solve( vertical, this::fitVertical, "vertically", () -> true );
	}//solveVertical


	/*
	 * solve( ) method used to solve puzzle by fitting words into the grid
	 * */
	// Recur each slot, try every word in a loop.  When all slots of this kind are filled successfully, run next stage.
	private boolean solve ( Map<Point, Integer> slot, BiFunction<Point, String, Boolean> fill, String dir, Supplier<Boolean> next ) {
		// If finished, move to next stage.
		if ( slot.isEmpty() ) 
			return next.get();
		final Point pos = slot.keySet().iterator().next();
		final int size = slot.remove( pos );
		final char[] state = board.clone();
		// Try each word                                                    
		for ( String word : words ) {
			if ( word.length() != size ) continue;
			// If the word fit, recur. If recur success done!              
			if ( fill.apply( pos, word ) && solve( slot, fill, dir, next ) )
				return true;
			// Doesn't match. Restore board and try next word                
			choice++;
			System.arraycopy( state, 0, board, 0, board.length );
		}
		//No match.  Restore slot and report failure                     
		slot.put( pos, size );
		return false;
	}

	// Try fit a word to a slot.  Return false if there is a conflict.
	private boolean fitHorizontal ( Point pos, String word ) 
	{
		final int x = pos.x, y = pos.y;
		for ( int i = 0 ; i < word.length() ; i++ ) 
		{
			if ( ! isSpace( x+i, y ) && get( x+i, y ) != word.charAt( i ) ) 
				//For conflict return false
				return false;
			set( x+i, y, word.charAt( i ) );
		}//for
		return true;
	}//fitHorizontal
	private boolean fitVertical ( Point pos, String word ) 
	{
		final int x = pos.x, y = pos.y;
		for ( int i = 0 ; i < word.length() ; i++ ) 
		{
			if ( ! isSpace( x, y+i ) && get( x, y+i ) != word.charAt( i ) ) 
				//For conflict return false
				return false; 
			set( x, y+i, word.charAt( i ) );
		}//for
		return true;
	}//fitVertical

	public void print(PrintWriter outstream)
	{
		// Show board, either fully filled or totally empty.
		if(result==true)
		{
			for ( int i = 0 ; i < board.length ; i++ ) 
			{
				if ( i % width == 0 ) 
					System.out.println();
				//replacing blocked cells from '#' to empty space
				if(board[i]=='#')
				{
					//printing Output Stream to console
					outstream.print(' ');
				}
				else
				{
					//printing cells onto the grid
					outstream.print(board[i]);
				}//else

				outstream.flush();
			}//for
		}//if
	}//print
}