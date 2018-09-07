import java.util.ArrayList;

/**
 * This class is to align the day view 
 * in the vertical direction. Its a concrete class
 * that implements day_alignment strategy.
 * 
 * @author Ritika Singhal, Rudra Gandhi, Joleena Marshall 
 * @copyright 07-21-2018
 * @version 1.0
 *
 */
public class Vertical_alignment implements Day_Alignment
{


	/**
	 * This method is overridden day_alignemnt interface
	 * it prints day view in vertical direction
	 * @param s	The arraylist with strings to be printed
	 * @return String Formatted strings in the arraylist 
	 * in vertical direction.
	 * @Override
	 */
	public String day_view_alignment(ArrayList<String> s) 
	{
		String aligned = "";
		for (int i=0; i<s.size(); i++)
		{
			aligned += s.get(i) + "\n";
		}
		
		return aligned;
	}

}
