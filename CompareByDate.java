import java.util.Comparator;

/**
 * CompareByDate class is used to compare two events 
 * based on their date. It implements the compare 
 * function of the Comparator interface.
 * 
 * @author Ritika Singhal, Rudra Gandhi, Joleena Marshall 
 * @copyright 07-21-2018
 * @version 1.0
 */
public class CompareByDate implements Comparator<Event>
{
	
	/**
	 * This method is overridden from the comparator interface.
	 * It compares events based on date and if dates are equal 
	 * then it uses CompareByTime class to compare them based on
	 * their starting_time.
	 * @param event1 The first event to be compared
	 * @param event2 The second event to be compared
	 * @return int The result of the comparison.
	 * @Override
	 */
	public int compare(Event event1, Event event2)
	{
		int result = event1.getDate().compareTo(event2.getDate());
		if(result == 0)
		{
			CompareByTime cbt = new CompareByTime();
			result = cbt.compare(event1, event2);
		}
		
		return result;
	}

}
