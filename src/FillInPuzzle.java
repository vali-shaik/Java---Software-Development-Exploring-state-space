import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Arrays;


/*
 * FillInPuzzle accepts the stream of data for grid and solves the puzzle using its operations 
 * */
public class FillInPuzzle {
	//input field contains the actual formatted data 
	static String input;
	//WordPuzzle class used to solve and print the Puzzle
	WordPuzzle wordPuzzle;	
	/*
	 * loadPuzzle function accepts stream of data and creates a box based on the data within stream
	 * loads the word table with allowed fillable cells
	 *
	 *	Input: BufferReader
	 *	Output: Boolean
	 *
	 * */
	public boolean loadPuzzle(BufferedReader stream)
	{
		boolean flag=false;
		try {
			StringBuffer sb=new StringBuffer();
			//used to read each line from the buffer
			String currentLine;
			//Read all lines until the end
			while((currentLine=stream.readLine())!=null)
			{
				//Appending the current line to the main String
				sb.append(currentLine);
				//Appending new line for processing in solve( ) method
				sb.append("\n");
			}//while
			//closing Buffer Stream connection after processing
			stream.close(); 
			//Calling SplitInputStream class for formatting the input for processing
			SplitInputStream splitInputStream=new SplitInputStream();
			flag=splitInputStream.divideInputStream(sb.toString());
			if(flag=true)
			{
				//Store the formatted input 
				input=splitInputStream.getInput();
			}
		}//try 
		catch (Exception e) 
		{
			//return false for unsuccessful processing
			flag=false;
		}//catch
		return flag;
	}//loadPuzzle



	/*
	 * solve( ) method used to solve the crossword puzzle and fit the words into grid
	 * 
	 * Input : void
	 * Output: Boolean
	 * 
	 * */
	public boolean solve()
	{
		boolean flag=false;
		try {
			//Calling WordPuzzle class constructor with input (processed from loadPuzzle( ) method )
			//board field in WordPuzzle class will save the processed data
			wordPuzzle=new WordPuzzle(Arrays.asList( input.split( "\n" ) ));
			if(wordPuzzle!=null)
				//Field result of WordPuzzle class will be true if puzzle is solved or it will be false
				flag=wordPuzzle.result;
		}//try
		catch (Exception e) 
		{
			flag=false;
		}//catch
		return flag;
	}//solve


	/*
	 * print( ) method is used to print the grid after successful puzzle solution
	 * 
	 * Input: PrintWriter
	 * Output: void
	 * */
	public void print(PrintWriter outstream)
	{
		if(wordPuzzle!=null)
		{
			//Calling Word Puzzle class
			wordPuzzle.print(outstream);
		}//if
	}//print


	/*choices( ) methos provide the no of time program has undone while filling the grid with words
	 * 
	 * Input: 
	 * Outout: Integer
	 * 
	 * */
	public int choices()
	{
		int choices=0;
		//choices store the no of time program undo while solving the puzzle
		if(wordPuzzle!=null)
		{
			choices=wordPuzzle.getChoice();
		}//if
		return choices;
	}//choices

}//class
