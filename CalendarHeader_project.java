import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * CalendarHeader_project class serves as a Controller in the MVC pattern.
 * It has all the buttons needed to control and navigate the calendar and 
 * what it displays.
 * @author Ritika Singhal, Rudra Gandhi, Joleena Marshall 
 * @copyright 07-21-2018
 * @version 1.0
 */
public class CalendarHeader_project extends JPanel
{
	private MyCalendar model;
	
	/**
	 * This method is the constructor of the MonthView_project class 
	 * which sets, creates and initializes all the necessary member 
	 * variables. It creates the left and right buttons to navigate 
	 * the view in the calendar. It also has day, week, month and 
	 * agenda buttons to change the selected view in the calendar. 
	 * It has from file button to read events from a file.
	 */
	public CalendarHeader_project(MyCalendar m)
	{
		model = m;
		
		JButton today = new JButton("TODAY");
		JButton left = new JButton("<");
		JButton right = new JButton(">");
		
		JButton day = new JButton("DAY");
		JButton week = new JButton("WEEK");
		JButton month = new JButton("MONTH");
		JButton agenda = new JButton("AGENDA");

		JButton from_file = new JButton("FROM FILE");

		left.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				model.update(getChangedDate(-1));
				model.update_display_date(getChangedDate(-1));			
			}
			
		});
		
		right.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				model.update(getChangedDate(1));
				model.update_display_date(getChangedDate(1));
			}	
		});
		
		today.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				model.update_display_date(new GregorianCalendar());
				model.update(new GregorianCalendar());
			}
		});
		
		day.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				model.update_selected_view(SELECTED_VIEW.Day);
			}	
		});
		
		week.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				model.update_selected_view(SELECTED_VIEW.Week);
			}	
		});
		
		month.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				model.update_selected_view(SELECTED_VIEW.Month);
			}	
		});
		
		agenda.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				model.update_selected_view(SELECTED_VIEW.Agenda);
			}	
		});
		
		from_file.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{

					File file = new File("C:\\Users\\Ritika\\Desktop\\CS 151\\Assignment_3\\src\\inputs.txt");
					try{
						Scanner in = new Scanner(file);
						while (in.hasNextLine())
						{
							addEventsFromFile(in.nextLine());
						}
					}
					catch (FileNotFoundException e1){
						e1.printStackTrace();
					}
				}	
		});
		
		add(today);
		add(left);
		add(right);
		add(day);
		add(week);
		add(month);
		add(agenda);
		add(from_file);
	}
	
	/**
	 * This method calculates the changed date and retrieves 
	 * it according to the view day, week, or month 
	 * @param i An int is being passed as an object 
	 * @return date the date 
	 */
	private GregorianCalendar getChangedDate(int i)
	{
		GregorianCalendar date = model.getCurrent_display_date();
		SELECTED_VIEW view = model.getDisplay_view();
		
		switch(view)
		{
			case Day: date.add(Calendar.DAY_OF_MONTH, i); break;
			case Week: date.add(Calendar.DAY_OF_MONTH, 7*i); break;
			case Month: date.add(Calendar.MONTH, i); break;
		}
		
		return date;
	}
	
	/**
	 * This method adds the events from the file with the 
	 * month, date, title, year and times 
	 * @param values values provided by user
	 */
	public void addEventsFromFile(String values) {
		String[] value = values.split(";");
		String title = value[0];
		int year = Integer.parseInt(value[1]);
		int startMonth = Integer.parseInt(value[2]);
		int endMonth = Integer.parseInt(value[3]);
		String days = value[4];
		String startTime = value[5];
		String endTime = value[6];
	
		GregorianCalendar cal = new GregorianCalendar(year, startMonth-1, 1);
		
		for(int i = startMonth; i <= endMonth; i++) {
			cal.set(year, i-1, 1);
			int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			for(int j = 1; j <= daysInMonth; j++) {
				GregorianCalendar temp = new GregorianCalendar(year, i-1, j);
				
				if(days.contains(FindWeekDay(temp.get(Calendar.DAY_OF_WEEK)))) {

					String y = Integer.toString(year);
					String month = String.format("%02d", i);
					String day = String.format("%02d", j);
					String date = month + "/" + day + "/" + y;

					Event e = new Event(title, date, startTime+":00", endTime+":00");
					model.update_event_list(e);
				}
			}
		}
	}
	
	/**
	 * This method finds the day of the week 
	 * @param dayOfTheWeek int the represents day of the week
	 * @return Returns the day of the week 
	 */
	private String FindWeekDay(int dayOfTheWeek) {
		switch (dayOfTheWeek) {
		   case 1:
               return "S";
           case 2:
               return "M";
           case 3:
               return "T";
           case 4:
               return "W";
           case 5:
               return "H";
           case 6:
               return "F";
           case 7:
               return "A";
           default:
               return null;
		}
	}
}