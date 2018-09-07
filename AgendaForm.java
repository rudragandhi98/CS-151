import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * AgendaForm class serves as a part of the Controller in the MVC pattern.
 * It asks the user for information about the time period of the events
 * to be displayed
 * @author Ritika Singhal, Rudra Gandhi, Joleena Marshall 
 * @copyright 07-21-2018
 * @version 1.0
 */
public class AgendaForm extends JDialog
{
	private MyCalendar model;
	private GregorianCalendar start_date;
	private GregorianCalendar end_date;

	/**
	 * This method is the constructor of the AgendaForm class 
	 * which sets, creates and initializes all the necessary member 
	 * variables. It takes the user input about starting date and 
	 * ending date of the series of events to be displayed.
	 * @param m MyCalendar object (i.e. model)
	 */
	public AgendaForm(MyCalendar m)
	{
		model = m;
		this.setModal(true);
		setLayout(new GridLayout(4,1));
		
		JLabel title_label = new JLabel("Time period for which events need to be displayed:");
		
		JLabel starting_date_label = new JLabel("Starting date (MM/DD/YYYY):");
		JTextField starting_date_field = new JTextField(15);
		JPanel starting_date = new JPanel();
		starting_date.add(starting_date_label);
		starting_date.add(starting_date_field);
		
		JLabel ending_date_label = new JLabel("Ending date (MM/DD/YYYY):");
		JTextField ending_date_field = new JTextField(15);
		JPanel ending_date = new JPanel();
		ending_date.add(ending_date_label);
		ending_date.add(ending_date_field);

		JButton save = new JButton("SAVE");
		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				String s_date = starting_date_field.getText();
				String e_date = ending_date_field.getText();

				start_date = parse_string_to_date(s_date);
				end_date = parse_string_to_date(e_date);	
				dispose();
			}
						
		});
		
		add(title_label);
		add(starting_date);
		add(ending_date);
		add(save);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	/**
	 * This method is used to parse the date as integers
	 * and create a gregorian calendar for that date
	 * @param date An object of MyCalendar (i.e date)
	 * @return cal
	 */
	private GregorianCalendar parse_string_to_date(String date)
	{
		String[] tokens = date.split("/");
		GregorianCalendar cal = new GregorianCalendar(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[0])-1, Integer.parseInt(tokens[1]));
		return cal;
	}
	
	/**
	 * An accessor method to retrieve the start date
	 * @return start_date the start date 
	 */
	public GregorianCalendar getStart_date() 
	{
		return start_date;
	}

	/**
	 * An accessor method to retrieve the end date 
	 * @return end_date the end date 
	 */
	public GregorianCalendar getEnd_date() 
	{
		return end_date;
	}
}
