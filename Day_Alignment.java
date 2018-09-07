import java.util.ArrayList;

/**
 * An interface that aligns the view in print_day. 
 * It serves as the strategy interface used to align day view
 * in selected view class (context).
 * 
 * @author Ritika Singhal, Rudra Gandhi, Joleena Marshall 
 * @copyright 07-21-2018
 * @version 1.0
 *
 */
public interface Day_Alignment 
{
	public String day_view_alignment(ArrayList<String> s);
}
