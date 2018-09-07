import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * MyCalendar is the class which represents the calendar
 * of the user and contains a list of events of the user.
 * It serves as a Model in the MVC Pattern and contains all 
 * the views i.e. change listeners in an arraylist. It contains
 * methods to access or modify the data, notify the listeners.
 * 
 * @author Ritika Singhal, Rudra Gandhi, Joleena Marshall
 * @copyright 07-21-2018
 * @version 1.0
 */
public class MyCalendar {

	private ArrayList<Event> events;
	private GregorianCalendar date_today;
	private GregorianCalendar current_display_date;
	private SELECTED_VIEW display_view;
	private ArrayList<ChangeListener> listeners;

	/**
	 * This method is the constructor of the MyCalendar class 
	 * which creates and initializes all the necessary member 
	 * variables.
	 * @param none
	 */
	public MyCalendar() 
	{
		events = new ArrayList<Event>();
		load_events("events.txt");
		date_today = new GregorianCalendar(); 
		current_display_date = new GregorianCalendar();
		display_view = SELECTED_VIEW.Day;
		listeners = new ArrayList<ChangeListener>();
	}
	
	
	/**
	 * This is the method used to get the current date in the calendar
	 * @param none
	 * @return GregorianCalendar The current date in the calendar
	 */
	public GregorianCalendar getDate_today() 
	{
		return (GregorianCalendar)(date_today.clone());
	}
	
	/**
	 * This method is used to get the current display date 
	 * @param none 
	 * @return GregorianCalendar The display of the current date 
	 */
	public GregorianCalendar getCurrent_display_date() 
	{
		return (GregorianCalendar)(current_display_date.clone());
	}

	/**
	 * This method is used to get the display view
	 * @param none 
	 * @return display_view This displays the view 
	 */
	public SELECTED_VIEW getDisplay_view() 
	{
		return display_view;
	}

	/**
	 * This is the method used to get all the events which are scheduled 
	 * in the calendar by the user on a specified date.
	 * @param date The date for which user needs to get the events
	 * @return ArrayList<Event> The list of events on the specified date
	 */
	public ArrayList<Event> getEvents(Date date)
	{
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Instant instant = date.toInstant();
		LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
		ArrayList<Event> event_list = new ArrayList<Event>();
		
		Iterator it = events.iterator();
        while(it.hasNext())
		{
			Event e = (Event) it.next();
			if(e.getDate().equals(localDate))
				event_list.add(e);
		}

		return event_list;
	}
	
	 /**
	   Attach a listener to the Model
	   @param c the listener
	*/
	public void attach(ChangeListener c)
	{
	   listeners.add(c);
	}
	
	/**
	 * This updates the selected view on the calendar 
	 * @param view The object view is being passed
	 * to the update_selected_view 
	 */
	public void update_selected_view(SELECTED_VIEW view)
	{
		display_view = view;
		
		for (ChangeListener l : listeners)
		{
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * This updates the display of the date in the calendar 
	 * @param date The object date is being passed to the 
	 * update_display_date 
	 */
	public void update_display_date(GregorianCalendar date)
	{
		   current_display_date = date;

		   for (ChangeListener l : listeners)
		   {
		      l.stateChanged(new ChangeEvent(this));
		   }
	}
	
	 /**
	    Change the date in the model 
	    @param date The new date to be set
	    @return nothing
	 */
	public void update(GregorianCalendar date)
	{
	   date_today = date;

	   for (ChangeListener l : listeners)
	   {
	      l.stateChanged(new ChangeEvent(this));
	   }
	}
	
	 /**
	    Change the event list in the model by 
	    adding an event
	    @param e The event to be added
	    @return nothing
	*/
	public void update_event_list(Event e)
	{
	   events.add(e);
	   
	   for (ChangeListener l : listeners)
	   {
	      l.stateChanged(new ChangeEvent(this));
	   }
	}
	
	/**
	 * This is the method used to load all the events from a file
	 * to the events list in the calendar by using serialization.
	 * @param filename The file from which events needs to be loaded 
	 * in the calendar
	 * @return nothing
	 * @exception IOException on input error
	 * @exception ClassNotFoundException on invalid object.
	 * @precondition The user selects the option to load events 
	 * and inputs filename
	 * @postcondition The events are added to the events list if 
	 * file exists
	 */
	public void load_events(String filename)
	{		
		Event event = null;
		
		File file = new File(filename);
		
		if(!file.exists())
		{
			return;
		}
		
		try 
		{			
	        FileInputStream fileIn = new FileInputStream(file);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	                 
	        while(fileIn.available()>0)
	        {
		        event = (Event) in.readObject();
		        events.add(event);
	        }
	        in.close();
	        fileIn.close();
	    } 
		catch (IOException i) 
		{
	        i.printStackTrace();
	        return;
	    } 
		catch (ClassNotFoundException c) 
		{
	        System.out.println("Event class not found");
	        c.printStackTrace();
	        return;
	    }
	}
	
	/**
	 * This is the method used to save all the events of the 
	 * calendar in a events.txt file through java deserialization
	 * @param none
	 * @return nothing
	 */
	public void save_events_in_file()
	{
		Event e = null;
		events.sort(new CompareByDate());		
		try
		{
			FileOutputStream fileout =  new FileOutputStream("events.txt");
			ObjectOutputStream out = new ObjectOutputStream(fileout);
			
			Iterator it = events.iterator();
			while(it.hasNext())
			{
				e = (Event) it.next();
				out.writeObject(e);
			}
			
			out.close();
			fileout.close();
		}
		catch (IOException i)
		{
			i.printStackTrace();
		}
	}
}
