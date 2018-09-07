import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * MonthView_project class serves as a View and Controller in the MVC 
 * It shows the current month and user can navigate by clicking
 * on the dates in the month. This will change the current date
 * in the MyCalendar and notify all views accordingly. Then, it 
 * will also change today's date in the month view.
 * 
 * @author Ritika Singhal, Rudra Gandhi, Joleena Marshall 
 * @copyright 07-21-2018
 * @version 1.0
 */
public class MonthView_project extends JPanel implements ChangeListener{

	private MyCalendar model;
	private JPanel month_days;
	private JLabel month;
	private MONTHS[] arrayOfMonths = MONTHS.values();
	
	/**
	 * This method is the constructor of the MonthView class 
	 * which sets, creates and initializes all the necessary member 
	 * variables. It creates all the days as buttons and calls the update
	 * function of the model to change the data when the user clicks on 
	 * the buttons. It creates the event and stores it in model by creating
	 * CreateEventForm_project object. It adds the action listeners to all the buttons
	 * and sets the look and feel of the view.
	 * @param m MyCalendar object (i.e. model)
	 */
	public MonthView_project(MyCalendar m)
	{
		model = m;
		setLayout(new BorderLayout());
		
		month_days = new JPanel(new GridLayout(6, 7));
		JPanel header = new JPanel();
		header.setLayout(new BorderLayout());
		JButton create = new JButton("Create");
		create.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JFrame createEvent = new CreateEventForm_project(model);
			}
			
		});
		
		JPanel month_panel = new JPanel();
		month = new JLabel(String.format(arrayOfMonths[model.getDate_today().get(Calendar.MONTH)] + " " + model.getDate_today().get(Calendar.YEAR)));
		JLabel days = new JLabel(String.format("%3s %3s %3s %3s %3s %4s %4s", ("Su"), ("Mo"), ("Tu"), ("We"), ("Th"), ("Fr"), ("Sa")));
		month_panel.add(month);
		
		JButton left = new JButton("<");
		left.setContentAreaFilled(false);

		JButton right = new JButton(">");
		right.setContentAreaFilled(false);

		left.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				GregorianCalendar current = model.getDate_today();
				current.add(Calendar.MONTH, -1);
				model.update(current);
			}
			
		});
		
		right.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				GregorianCalendar current = model.getDate_today();
				current.add(Calendar.MONTH, 1);
				model.update(current);
			}
			
		});
		month_panel.add(left);
		month_panel.add(right);
		
		create_day_buttons();
		
		header.add(create, BorderLayout.PAGE_START);
		header.add(month_panel, BorderLayout.LINE_START);
		header.add(days, BorderLayout.PAGE_END);
		
		add(header, BorderLayout.NORTH);
		add(month_days, BorderLayout.CENTER);
	}
	
	/**
	 * This is the method used to create the buttons for the days in the month
	 * so that they are clickable.
	 * @param none
	 * @return nothing
	 */
	private void create_day_buttons()
	{
		JButton[] day_buttons = new JButton[42];
		
		for(int i=0; i<42; i++)
		{
			day_buttons[i] = new JButton();
			day_buttons[i].setBorderPainted(false);
			day_buttons[i].setMargin(new Insets(0,0,0,0));
			day_buttons[i].setContentAreaFilled(false);
		}

		print_calendar(day_buttons);
	
		for(int i=0; i<42; i++)
		{
			if(day_buttons[i].getText() != "")
			{
				final int day = i;
				day_buttons[i].addActionListener(new ActionListener()
						{

							@Override
							public void actionPerformed(ActionEvent arg0) 
							{
								GregorianCalendar calendar = new GregorianCalendar(model.getDate_today().get(Calendar.YEAR),  model.getDate_today().get(Calendar.MONTH), Integer.parseInt(day_buttons[day].getText()));
								model.update((GregorianCalendar)(calendar.clone()));
								model.update_display_date(calendar);
							}
							
						});
			}
			
			month_days.add(day_buttons[i]);

		}
	}
		
	/**
	 * This is the method used to print the details of a particular 
	 * month in the calendar
	 * @param day_buttons JButtons for the days in the month to be visible
	 * @return nothing
	 */
	public void print_calendar(JButton[] day_buttons)
	{
	    int first_day_of_month;
	    int button_number = 0;
		GregorianCalendar date_today = model.getDate_today();
	    	    
	    GregorianCalendar temp = new GregorianCalendar(date_today.get(Calendar.YEAR), date_today.get(Calendar.MONTH), 1);
	    first_day_of_month = temp.get(Calendar.DAY_OF_WEEK)-1;
	    
	    for(int i=0; i<first_day_of_month; i++)
	    {
	    	day_buttons[button_number].setText("");
	    	day_buttons[button_number].setEnabled(false);
	    	button_number++;
	    }

	    for(int i=0; i<date_today.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
	    {  	
	    	
	    	boolean today = temp.get(Calendar.DAY_OF_MONTH) == date_today.get(Calendar.DAY_OF_MONTH) && 
	    						 temp.get(Calendar.MONTH) == date_today.get(Calendar.MONTH)&& 
	    						 temp.get(Calendar.YEAR) == date_today.get(Calendar.YEAR) ;
	    	
	    	if(today)
	    		day_buttons[button_number].setBorderPainted(true);
	    	else
	    		day_buttons[button_number].setBorderPainted(false);

	  
	    	day_buttons[button_number].setText(Integer.toString(temp.get(Calendar.DAY_OF_MONTH)));
	    	button_number++;	
	    	temp.add(Calendar.DAY_OF_MONTH, 1);
	    }	    	    
	}

	/**
   	* Called when the data in the model is changed.
   `* @param e the event representing the change
    */
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		month.setText(String.format(arrayOfMonths[model.getDate_today().get(Calendar.MONTH)] + " " + model.getDate_today().get(Calendar.YEAR)));		
		month_days.removeAll();
		create_day_buttons();
	}
}
