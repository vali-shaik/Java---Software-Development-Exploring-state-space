import java.util.StringTokenizer;

//SplitInputStream class used to format the stream of data from the input stream
public class SplitInputStream {
	//Stores the formatted String
	String input;
	//Size of columns and rows;
	//positions from the input line (1(firstPosition) 2(secondPosition) 5(noOfPositions) h(direction)) 
	int columns,rows,noOfWords,firstPosition,secondPosition,noOfPostions;
	String direction;
	//main grid formation with fillable and closed cells
	String[][] box; // '_' = fillable, '#' = cannot be filled


	//getter method for input field
	public String getInput() 
	{
		return input;
	}//getInput

	/*
	 * divideInputStream() method used to accept the stream of data and return the formatted input
	 * */
	public boolean divideInputStream(String inputStream)
	{
		boolean flag=true;

		try {
			StringBuffer sb=new StringBuffer();
			StringBuffer words=new StringBuffer();
			//split the input stream using new line delimiters
			String[] lines = inputStream.split("\\r?\\n");
			sb.append(lines[0]+"\n");
			//StringTokenizer is used to divide each line into tokens
			StringTokenizer st;
			for(int i=0;i<lines.length;i++)
			{
				st=new StringTokenizer(lines[i].trim());
				while(st.hasMoreTokens())
				{
					if(i==0)
					{
						//parse the first line of the input
						columns=Integer.parseInt(st.nextToken().trim());
						rows=Integer.parseInt(st.nextToken().trim());
						noOfWords=Integer.parseInt(st.nextToken().trim());
						//checking if the input is correct
						if(!(lines.length==noOfWords*2+1))
						{
							return false;
						}//if

						//create a structure 
						box=new String[rows][columns];
						for(int j=0;j<rows;j++)
						{
							for(int k=0;k<columns;k++)
							{
								box[j][k]="#";
							}//for

						}//for
					}//if
					else if(i>0 && i<=noOfWords)
					{
						//parse the  lines of the input
						firstPosition=Integer.parseInt(st.nextToken().trim());
						secondPosition=Integer.parseInt(st.nextToken().trim());
						noOfPostions=Integer.parseInt(st.nextToken().trim());
						direction=st.nextToken();

						if(direction.trim().equalsIgnoreCase("h"))
						{
							//create fillable cells horizontal
							int count=0;
							for(int m=firstPosition,n=secondPosition;count!=noOfPostions&&n<rows;m++)
							{
								box[n][m]="_";
								count++;
							}//for

						}//if
						else if(direction.trim().equalsIgnoreCase("v"))
						{
							//create fillable cells vertical
							int count=0;
							for(int m=firstPosition,n=secondPosition;m<columns&&count!=noOfPostions;n--)
							{
								box[n][m]="_";
								count++;
							}//for

						}//else if
						else
						{
							//if given direction is other horizontal or vertical 
							return false;
						}

					}//else
					else if(i>=noOfWords+1&& i<lines.length)
					{
						//parse the lines for only words
						words.append(st.nextToken()+"\n");
					}
				}//while
			}//for

			for(int i=rows-1;i>=0;i--)
			{
				for(int j=0;j<columns;j++)
				{
					//append each cell to the string
					sb.append(box[i][j]);
				}//for
				sb.append("\n");
			}//for
			sb.append(words);
			//setting final formatted string to input
			input=sb.toString();
		}//try
		catch (NumberFormatException e) 
		{
			flag=false;
		}//catch
		catch (Exception e) 
		{
			flag=false;
		}//catch
		return flag;
	}//divideInputStream


}
