import java.util.*;
import java.time.LocalTime;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Event class represents an event in the calendar. It 
 * consists of the event details i.e. title, date, 
 * starting_time and ending_time.
 * 
 * @author Ritika Singhal, Rudra Gandhi, Joleena Marshall
 * @copyright 07-21-2018
 * @version 1.0
 */
class Event implements Serializable {

	private String title;
	private LocalDate date;
	private LocalTime starting_time;
	private LocalTime ending_time;
	
	/**
	 * This method is the constructor of the Event class.
	 * @param none
	 */
	public Event(){}
	
	/**
	 * This method is the constructor of the Event class 
	 * which creates and initializes all the member 
	 * variables by parsing necessary ones.
	 * @param title Title of the event
	 * @param date Date of the event
	 * @param starting_time Starting time of the event
	 * @param ending_time Ending time of the event
	 */
	public Event(String title, String date, String starting_time, String ending_time)
	{
		this.title = title;
		this.date = parse_date(date);
		this.starting_time = parse_starting_time(starting_time);
		if(ending_time == null || ending_time.isEmpty())
			this.ending_time = null;
		else
			this.ending_time = parse_ending_time(ending_time);		
	}
	
	/**
	 * This method is the constructor of the Event class 
	 * which initializes all the member variables
	 * @param title Title of the event
	 * @param date Date of the event
	 * @param starting_time Starting time of the event
	 * @param ending_time Ending time of the event
	 */
	public Event(String title, LocalDate date, LocalTime starting_time, LocalTime ending_time) 
	{
		this.title = title;
		this.date = date;
		this.starting_time = starting_time;
		this.ending_time = ending_time;
	}
	
	/**
	 * This method is used to get the title of the event
	 * @param none
	 * @return String Title of the event.
	 */
	public String getTitle() 
	{
		return title;
	}
	
	/**
	 * This is the method which sets the value of the title.
	 * @param title The string for the event title.
	 * @return nothing
	 * @precondition A string is passed as parameter
	 * @postcondition Title now contains the string value.
	 */
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	/**
	 * This method is used to get the date of the event
	 * @param none
	 * @return LocalDate date of the event.
	 */
	public LocalDate getDate()
	{
		return date;
	}
	
	/**
	 * This is the method which sets the value of the date.
	 * @param date The LocalDate for the event date.
	 * @return nothing
	 * @precondition A LocalDate is passed as parameter
	 * @postcondition Date now contains the LocalDate value.
	 */
	public void setDate(LocalDate date) 
	{
		this.date = date;
	}
	
	/**
	 * This method is used to get the starting_time of the event
	 * @param none
	 * @return LocalTime starting_time of the event.
	 */
	public LocalTime getStarting_time() 
	{
		return starting_time;
	}
	
	/**
	 * This is the method which sets the value of the starting_time.
	 * @param starting_time The LocalTime for the event starting_time.
	 * @return nothing
	 * @precondition A LocalTime is passed as parameter
	 * @postcondition starting_time now contains the LocalTime value.
	 */
	public void setStarting_time(LocalTime starting_time) 
	{
		this.starting_time = starting_time;
	}
	
	/**
	 * This method is used to get the ending_time of the event
	 * @param none
	 * @return LocalTime ending_time of the event.
	 */
	public LocalTime getEnding_time() 
	{
		return ending_time;
	}
	
	/**
	 * This is the method which sets the value of the ending_time.
	 * @param ending_time The LocalTime for the event ending_time.
	 * @return nothing
	 * @precondition A LocalTime is passed as parameter
	 * @postcondition Ending_time now contains the LocalTime value.
	 */
	public void setEnding_time(LocalTime ending_time) 
	{
		this.ending_time = ending_time;
	}	
	
	/**
	 * This method is used to parse a string containing date to 
	 * create a LocalDate object to represent the date
	 * @param date Date of the event in a string
	 * @return LocalDate date of the event.
	 */
	private LocalDate parse_date(String date)
	{
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");
		LocalDate event_date = LocalDate.parse(date, dateTimeFormatter);
		return event_date;
	}
	
	/**
	 * This method is used to parse a string containing starting_time to 
	 * create a LocalTime object to represent the starting_time
	 * @param starting_time Time of the event in a string
	 * @return LocalTime starting_time of the event.
	 */
	private LocalTime parse_starting_time(String starting_time)
	{
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;		
		LocalTime start_time = LocalTime.parse(starting_time, dateTimeFormatter);		

		return start_time;
	}
	
	/**
	 * This method is used to parse a string containing ending_time to 
	 * create a LocalTime object to represent the ending_time
	 * @param ending_time Time of the event in a string
	 * @return LocalTime ending_time of the event.
	 */
	private LocalTime parse_ending_time(String ending_time)
	{
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;		
		LocalTime end_time = LocalTime.parse(ending_time, dateTimeFormatter);		

		return end_time;
	}
	
	/**
	 * This method is used to check if there is a conflict of times 
	 * between two events.
	 * @param event The Event which needs to be checked for
	 * @return boolean If there is a conflict or not.
	 */
	public boolean event_conflict(Event event)
	{
		if(this.getDate().equals(event.getDate()))
			if((this.getStarting_time().compareTo(event.getEnding_time()) < 0 && this.getStarting_time().compareTo(event.getStarting_time()) >= 0) || 
					(this.getEnding_time().compareTo(event.getStarting_time()) > 0 && this.getEnding_time().compareTo(event.getEnding_time()) <= 0) || 
					(event.getStarting_time().compareTo(this.getEnding_time()) < 0 && event.getStarting_time().compareTo(this.getStarting_time()) >= 0) || 
					(event.getEnding_time().compareTo(this.getStarting_time()) > 0 && event.getEnding_time().compareTo(this.getEnding_time()) <= 0))
				return true;			
			
		return false;
	}
	
	/**
	 * This method is used to print all the details of the event.
	 * @param none
	 * @return String Details of the event
	 */
	public String print()
	{
		return (title + " " + starting_time + " - " + ending_time);
	}
}
