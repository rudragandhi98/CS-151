import java.awt.BorderLayout;
import java.awt.Scrollbar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.ListIterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * SelectedView class is a View in the MVC pattern and it shows all the
 * events on the current date, week, month or agenda in the calendar i.e. 
 * the model.
 * 
 * @author Ritika Singhal, Rudra Gandhi, Joleena Marshall
 * @copyright 07-21-2018
 * @version 1.0
 */
public class SelectedView extends JPanel implements ChangeListener {

	private MyCalendar model;
	private JTextArea events;
    private DAYS[] arrayOfDays = DAYS.values();
    private Day_Alignment day_align;
	
	/**
	 * This method is the constructor of the SelectedView class 
	 * which sets, creates and initializes all the necessary member 
	 * variables.
	 * @param m MyCalendar object (i.e. model)
	 */
	public SelectedView(MyCalendar m, Day_Alignment d)
	{
		model = m;
		day_align = d;
		setLayout(new BorderLayout());
		events = new JTextArea(40,70);
		events.setEditable(false);	

		print_day();

		add(events, BorderLayout.NORTH);
	}
	
	/**
	 * This method retrieves the alignment for Day in the calendar 
	 * @param none 
	 * @return day_align alignment of the day
	 */
	public Day_Alignment getDay_align() 
	{
		return day_align;
	}

	/**
	 * This method sets the alignment of the day 
	 * @param day_align The alignment object is being passed 
	 * through setDay_align 
	 */
	public void setDay_align(Day_Alignment day_align) 
	{
		this.day_align = day_align;
	}

	/**
	 * This is the method used to show the details of a current selected
	 * day in the calendar, along with all the events scheduled for 
	 * that day in the order of starting date.
	 * @param none
	 * @return nothing
	 */
	public void print_day()
	{
		GregorianCalendar date_today = model.getCurrent_display_date();	
		
		String event_info = "";
		ArrayList<String> string = new ArrayList<String>();

		string.add(String.format(arrayOfDays[date_today.get(Calendar.DAY_OF_WEEK)-1] + "  " + (date_today.get(Calendar.MONTH)+1) + 
				"/" + date_today.get(Calendar.DAY_OF_MONTH)));
		
        ArrayList<Event> e = model.getEvents(date_today.getTime());
        e.sort(new CompareByTime());
		Iterator it = e.iterator();
		while(it.hasNext())
		{
			String s = ((Event) it.next()).print();
			string.add(s);
		}       	
		event_info = day_align.day_view_alignment(string);
		events.setText(event_info);
	}
	
	
	/**
	 * This is the method used to show the details of a current selected
	 * week in the calendar, along with all the events scheduled for 
	 * that week in the order of starting date.
	 * @param none 
	 * @return nothing
	 */
	public void print_week()
	{
		GregorianCalendar date_today = model.getCurrent_display_date();
		GregorianCalendar temp = new GregorianCalendar(date_today.get(Calendar.YEAR), date_today.get(Calendar.MONTH), date_today.get(Calendar.DAY_OF_MONTH));
		temp.add(Calendar.DAY_OF_WEEK, 1-date_today.get(Calendar.DAY_OF_WEEK));

		String event_info = "";

		for(int i=0; i<7; i++)
		{
			event_info += (String.format(arrayOfDays[temp.get(Calendar.DAY_OF_WEEK)-1] + "  " + (temp.get(Calendar.MONTH)+1) + 
					"/" + temp.get(Calendar.DAY_OF_MONTH))) + "\n";
	        ArrayList<Event> e = model.getEvents(temp.getTime());
	        e.sort(new CompareByTime());
			Iterator it = e.iterator();
			while(it.hasNext())
			{
				String s = ((Event) it.next()).print();
				event_info += s + "\n";
			}       	
			event_info += "\n";
			temp.add(Calendar.DAY_OF_WEEK, 1);

		}
		events.setText(event_info);

	}
	
	/**
	 * This is the method used to show the details of a current selected 
	 * month in the calendar, along with all the events scheduled for 
	 * that month in the order of starting date.
	 * @param none 
	 * @return nothing
	 */
	public void print_month()
	{
		GregorianCalendar date_today = model.getCurrent_display_date();
		GregorianCalendar temp = new GregorianCalendar(date_today.get(Calendar.YEAR), date_today.get(Calendar.MONTH), 1);

		String event_info = "";	    	    

	    for(int i=0; i<date_today.getActualMaximum(Calendar.DAY_OF_MONTH) && i<7; i++)
	    {  		  
	    	GregorianCalendar gc = (GregorianCalendar) (temp.clone());
	    	ArrayList<ArrayList<Event>> event = new ArrayList<ArrayList<Event>>();
	    	for (int j=gc.get(Calendar.DAY_OF_MONTH); j<=date_today.getActualMaximum(Calendar.DAY_OF_MONTH); j+=7)
	    	{
		    	event_info += (String.format("%35s %02d/%02d",arrayOfDays[gc.get(Calendar.DAY_OF_WEEK)-1],(gc.get(Calendar.MONTH)+1) , gc.get(Calendar.DAY_OF_MONTH)));
		        ArrayList<Event> e = model.getEvents(gc.getTime());
		        event.add(e);
		        gc.add(Calendar.DAY_OF_MONTH, 7);
	    	}
	        temp.add(Calendar.DAY_OF_MONTH, 1);
	    	
			event_info += "\n";

	    	ArrayList<Integer> size = new ArrayList<Integer>();
	    	for(int k=0; k<event.size(); k++)
	    		size.add(event.get(k).size());
	    	int max_size = Collections.max(size);
	    	
	    	for(int k=0; k<max_size; k++)
	    	{
	    		String e = "";
	    		for(int p=0; p<event.size(); p++)
	    		{
	    			String s="\t";
			        event.get(p).sort(new CompareByTime());
			        
			        if(k<=event.get(p).size())
					{
			        	ListIterator it = event.get(p).listIterator(k);
			        	if(it.hasNext())
						{
							s = ((Event) it.next()).print();
						}
					}
					e += (String.format("%35s", s));

	    		}
	    		
	    		event_info += e + "\n";
	    	}
    		event_info += "\n";

	    }	
		events.setText(event_info);
	}
	
	/**
	 * This method creates and agendaForm object to get time period
	 * from the user and then displays all the events in that time frame
	 * @param none 
	 */
	public void print_agenda()
	{
		AgendaForm agenda = new AgendaForm(model);
		
		GregorianCalendar start = agenda.getStart_date();
		GregorianCalendar end = agenda.getEnd_date();
		
		if(start == null || end == null)
			return;
		
		String event_info = "";	    	    

	    while(!start.after(end))
	    {  	
	    	event_info += (String.format(arrayOfDays[start.get(Calendar.DAY_OF_WEEK)-1] + "  " + (start.get(Calendar.MONTH)+1) + 
					"/" + start.get(Calendar.DAY_OF_MONTH))) + "\n";
	        ArrayList<Event> e = model.getEvents(start.getTime());
	        e.sort(new CompareByTime());
			Iterator it = e.iterator();
			while(it.hasNext())
			{
				String s = ((Event) it.next()).print();
				event_info += s + "\n";
			}       	
			event_info += "\n";
			start.add(Calendar.DAY_OF_MONTH, 1);
	    }	
	    
	    
		events.setText(event_info);
	}
	
	/**
   	* Called when the data in the model is changed.
   `* @param e the event representing the change
    */
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		switch(model.getDisplay_view())
		{
			case Day:	print_day(); break;
			case Week:	print_week(); break;
			case Month: print_month(); break;
			case Agenda: print_agenda(); break;
		}		
	}
	
	
}
                                        