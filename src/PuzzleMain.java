import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class PuzzleMain {

	
	public static void main(String args[]) throws IOException
	{
		FileReader fr=new FileReader("assignment_4.txt");    
        BufferedReader br=new BufferedReader(fr);
		FillInPuzzle f=new FillInPuzzle();
		f.loadPuzzle(br);
		f.solve();
		f.print(new PrintWriter(System.out));
		System.out.println("\n\n\nNo of choices:- "+f.choices());
	}
	
}//class
