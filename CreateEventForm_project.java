import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * CreateEventForm class serves as a Controller in the MVC pattern.
 * It creates a new event and calls the update function of the model
 * to notify all the views and add the event to the calendar
 * 
 * @author Ritika Singhal, Rudra Gandhi, Joleena Marshall 
 * @copyright 07-21-2018
 * @version 1.0
 */
public class CreateEventForm_project extends JFrame
{
	private MyCalendar model;
	

	/**
	 * This method is the constructor of the CreateEventForm class 
	 * which sets, creates and initializes all the necessary member 
	 * variables. It creates 4 textfields to get the details of the new event from
	 * the user. It is displayed as  JFrame containing 4 textfields along
	 * with a Save button to save the event.
	 * @param m MyCalendar object (i.e. model)
	 */
	public CreateEventForm_project(MyCalendar m)
	{
		model = m;
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JLabel title_label = new JLabel("Event Title:");
		JTextField title_field = new JTextField(30);
		JPanel title = new JPanel();
		title.add(title_label);
		title.add(title_field);
		
		JLabel date_label = new JLabel("Event Date:");
		JTextField date_field = new JTextField(15);
		date_field.setText(String.format("%02d", model.getDate_today().get(Calendar.MONTH)+1) + "/" + String.format("%02d", model.getDate_today().get(Calendar.DAY_OF_MONTH))+ "/" + model.getDate_today().get(Calendar.YEAR));
		date_field.setEditable(false);
		JPanel date = new JPanel();
		date.add(date_label);
		date.add(date_field);
		
		JLabel starting_time_label = new JLabel("Event starting time (HH:MM, 24 hour format):");
		JTextField starting_time_field = new JTextField(15);
		JPanel start_time = new JPanel();
		start_time.add(starting_time_label);
		start_time.add(starting_time_field);
		
		JLabel ending_time_label = new JLabel("Event ending time (HH:MM, 24 hour format):");
		JTextField ending_time_field = new JTextField(15);
		JPanel end_time = new JPanel();
		end_time.add(ending_time_label);
		end_time.add(ending_time_field);

		JButton save = new JButton("SAVE");
		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				String event_title = title_field.getText();
				String event_starting_time = starting_time_field.getText();
				String event_ending_time = ending_time_field.getText();

				Event event = new Event(event_title, date_field.getText(),event_starting_time, event_ending_time);
				if(create_event(event) == true)
				{
					model.update_event_list(event);	
					dispose();
				}
				else
					JOptionPane.showMessageDialog(null, "Cannot create this event: Time conflict, Enter Correct time!");
				
			}
			
			
		});
		
		add(title);
		add(date);
		add(start_time);
		add(end_time);
		add(save);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	/**
	 * This is the method used to create the event, checks for any conflicts with 
	 * other events and then finally adds the event to the calendar.
	 * @param event Event to be created
	 * @return Boolean If event was created or not
	 * @precondition The user chose the option to create an event
	 * @postcondition An event is created with user inputs and 
	 * calls add method to add to the event list if no conflicts.
	 */
	public boolean create_event(Event event)
	{	
		if(event.getEnding_time() != null && event.getStarting_time().compareTo(event.getEnding_time()) > 0)
			return false;
		
		ArrayList<Event> events = model.getEvents(model.getDate_today().getTime());
		Iterator it = events.iterator();
		while(it.hasNext())
		{
			if(event.event_conflict((Event) it.next()))
				return false;
		}
		
		return true;
	}
}
