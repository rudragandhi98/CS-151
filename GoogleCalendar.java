/**
 * Group Project
 * 
 * @author Ritika Singhal, Rudra Gandhi, Joleena Marshall 
 * @copyright 07-21-2018
 * @version 1.0
 */

import java.awt.BorderLayout;
import java.awt.Scrollbar;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

enum MONTHS
{
	Jan, Feb, March, Apr, May, June, July, Aug, Sep, Oct, Nov, Dec;
}
enum DAYS
{
	Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday ;
}

enum SELECTED_VIEW
{
	Day, Week, Month, Agenda;
}

/**
 * GoogleCalendar is the class with the main method
 * which starts and generates the Calendar.
 * 
 * @author Ritika Singhal, Rudra Gandhi, Joleena Marshall 
 * @copyright 07-21-2018
 * @version 1.0
 */
public class GoogleCalendar {

	/**
	Creates a Model and attaches view listener to the model
	and then model to the view and the controller.
	@param args unused
	*/
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame();
		final int FRAME_WIDTH = 1200; 
		final int FRAME_HEIGHT = 500;
		frame.setLayout(new BorderLayout());
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		Day_Alignment day_align = new Vertical_alignment();
		
		MyCalendar model = new MyCalendar();
		CalendarHeader_project calendar_header = new CalendarHeader_project(model);
		MonthView_project month_view = new MonthView_project(model);
		model.attach(month_view);
		SelectedView day_view = new SelectedView(model, day_align);	
		model.attach(day_view);		
		
		frame.add(month_view, BorderLayout.WEST);
		frame.add(day_view, BorderLayout.EAST);
		frame.add(calendar_header, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	

}
