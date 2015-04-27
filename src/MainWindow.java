/*
 * File: MainWindow.java
 * Description: Implements the main window of the user interface
 * 				for our COMP 523 Music Scheduler program.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;


public class MainWindow {
	
	// Method to set up the user interface elements and position them using a SpringLayout
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setTitle("Music Scheduler");
		frame.setSize(450, 420);
		frame.setResizable(false);
		
		// Center the window on the screen
		frame.setLocationRelativeTo(null);
		
		// Exit our application when the window is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = frame.getContentPane();
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		
		JLabel roomDataLabel = new JLabel("Room Data");
		layout.putConstraint(SpringLayout.WEST, roomDataLabel, 15, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, roomDataLabel, 15, SpringLayout.NORTH, contentPane);
		
		JButton selectRoomDataButton = new JButton("Select...");
		layout.putConstraint(SpringLayout.NORTH, selectRoomDataButton, 5, SpringLayout.SOUTH, roomDataLabel);
		layout.putConstraint(SpringLayout.EAST, selectRoomDataButton, -15, SpringLayout.EAST, contentPane);
		
		JTextField roomDataTextField = new JTextField();
		roomDataTextField.setEditable(false);
		layout.putConstraint(SpringLayout.NORTH, roomDataTextField, 5, SpringLayout.SOUTH, roomDataLabel);
		layout.putConstraint(SpringLayout.WEST, roomDataTextField, 15, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.EAST, roomDataTextField, -5, SpringLayout.WEST, selectRoomDataButton);
		layout.putConstraint(SpringLayout.SOUTH, roomDataTextField, 0, SpringLayout.SOUTH, selectRoomDataButton);
		
		selectRoomDataButton.addActionListener(new SelectButtonActionListener(roomDataTextField, frame));
		
		contentPane.add(roomDataLabel);
		contentPane.add(selectRoomDataButton);
		contentPane.add(roomDataTextField);
		
		JLabel studentDataLabel = new JLabel("Student Data");
		layout.putConstraint(SpringLayout.WEST, studentDataLabel, 15, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, studentDataLabel, 30, SpringLayout.SOUTH, roomDataTextField);
		
		
		JButton selectStudentDataButton = new JButton("Select...");
		layout.putConstraint(SpringLayout.NORTH, selectStudentDataButton, 5, SpringLayout.SOUTH, studentDataLabel);
		layout.putConstraint(SpringLayout.EAST, selectStudentDataButton, -15, SpringLayout.EAST, contentPane);
		
		JTextField studentDataTextField = new JTextField();
		studentDataTextField.setEditable(false);
		layout.putConstraint(SpringLayout.NORTH, studentDataTextField, 5, SpringLayout.SOUTH, studentDataLabel);
		layout.putConstraint(SpringLayout.WEST, studentDataTextField, 15, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.EAST, studentDataTextField, -5, SpringLayout.WEST, selectStudentDataButton);
		layout.putConstraint(SpringLayout.SOUTH, studentDataTextField, 0, SpringLayout.SOUTH, selectStudentDataButton);;
		
		selectStudentDataButton.addActionListener(new SelectButtonActionListener(studentDataTextField, frame));
		
		contentPane.add(studentDataLabel);
		contentPane.add(selectStudentDataButton);
		contentPane.add(studentDataTextField);
		
		JLabel teacherDataLabel = new JLabel("Teacher Data");
		layout.putConstraint(SpringLayout.WEST, teacherDataLabel, 15, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, teacherDataLabel, 30, SpringLayout.SOUTH, studentDataTextField);
		
		JButton selectTeacherDataButton = new JButton("Select...");
		layout.putConstraint(SpringLayout.NORTH, selectTeacherDataButton, 5, SpringLayout.SOUTH, teacherDataLabel);
		layout.putConstraint(SpringLayout.EAST, selectTeacherDataButton, -15, SpringLayout.EAST, contentPane);
		
		JTextField teacherDataTextField = new JTextField();
		teacherDataTextField.setEditable(false);
		layout.putConstraint(SpringLayout.NORTH, teacherDataTextField, 5, SpringLayout.SOUTH, teacherDataLabel);
		layout.putConstraint(SpringLayout.WEST, teacherDataTextField, 15, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.EAST, teacherDataTextField, -5, SpringLayout.WEST, selectTeacherDataButton);
		layout.putConstraint(SpringLayout.SOUTH, teacherDataTextField, 0, SpringLayout.SOUTH, selectTeacherDataButton);;
		
		selectTeacherDataButton.addActionListener(new SelectButtonActionListener(teacherDataTextField, frame));
		
		contentPane.add(teacherDataLabel);
		contentPane.add(selectTeacherDataButton);
		contentPane.add(teacherDataTextField);
		
		JLabel outputDataLabel = new JLabel("Output Path");
		layout.putConstraint(SpringLayout.WEST, outputDataLabel,15, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, outputDataLabel, 30, SpringLayout.SOUTH, teacherDataTextField);
		
		JButton selectOutputDataButton = new JButton("Select...");
		layout.putConstraint(SpringLayout.NORTH, selectOutputDataButton, 5, SpringLayout.SOUTH, outputDataLabel);
		layout.putConstraint(SpringLayout.EAST, selectOutputDataButton, -15, SpringLayout.EAST, contentPane);
		
		
		JTextField outputDataTextField = new JTextField();
		outputDataTextField.setEditable(false);
		layout.putConstraint(SpringLayout.NORTH, outputDataTextField, 5, SpringLayout.SOUTH, outputDataLabel);
		layout.putConstraint(SpringLayout.WEST, outputDataTextField, 15, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.EAST, outputDataTextField, -5, SpringLayout.WEST, selectOutputDataButton);
		layout.putConstraint(SpringLayout.SOUTH, outputDataTextField, 0, SpringLayout.SOUTH, selectOutputDataButton);

		selectOutputDataButton.addActionListener(new OutputSelectButtonActionListener(outputDataTextField, frame) ); 
		
		contentPane.add(outputDataLabel);
		contentPane.add(selectOutputDataButton);
		contentPane.add(outputDataTextField);
		
		JButton scheduleButton = new JButton("Schedule");
		scheduleButton.addActionListener(new ScheduleButtonActionListener(roomDataTextField, studentDataTextField, teacherDataTextField, outputDataTextField, frame));;
		layout.putConstraint(SpringLayout.NORTH, scheduleButton, 30, SpringLayout.SOUTH, outputDataTextField);
		layout.putConstraint(SpringLayout.WEST, scheduleButton, 150, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.EAST, scheduleButton, -150, SpringLayout.EAST, contentPane);
		
		contentPane.add(scheduleButton);
		
		JButton helpButton = new JButton("?");
		layout.putConstraint(SpringLayout.SOUTH, helpButton, -15, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, helpButton, 15, SpringLayout.WEST, contentPane);
		
		contentPane.add(helpButton);

		// Creates the Help Window.
		JFrame helpFrame = new JFrame("Help Window");
		helpFrame.setSize(600, 600);
		helpFrame.setResizable(true);
		Rectangle bounds = helpFrame.getBounds();
		helpFrame.setLocation(10 + bounds.x, 10 + bounds.y);
	
		Container helpContentPane = helpFrame.getContentPane();
		SpringLayout helpLayout = new SpringLayout();
		helpContentPane.setLayout(helpLayout);
	
		String message = UserManual.MESSAGE_1 + '\n' + '\n' + UserManual.MESSAGE_2 + '\n' + '\n' + UserManual.MESSAGE_3 + '\n' + '\n' + UserManual.MESSAGE_4;
	
		// Create a JTextArea for the User Manual
		JTextArea helpText = new JTextArea(message);
		helpText.setLineWrap(true);
		helpText.setWrapStyleWord(true);
		helpText.setBackground(Color.WHITE);
		
		// Set the Font 
		Font f = new Font("Helvetica", 0, 22);
		helpText.setFont(f);
		helpText.setEditable(false);
		
		// Add Scroll Functionality
		JScrollPane scroll = new JScrollPane (helpText);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		// Put the helpText in the Container
		helpLayout.putConstraint(SpringLayout.WEST, scroll, 15, SpringLayout.WEST, helpContentPane);
		helpLayout.putConstraint(SpringLayout.NORTH, scroll, 15, SpringLayout.NORTH, helpContentPane);
		helpLayout.putConstraint(SpringLayout.EAST, scroll, -15, SpringLayout.EAST, helpContentPane);
		helpLayout.putConstraint(SpringLayout.SOUTH, scroll, -15, SpringLayout.SOUTH, helpContentPane);

	    helpContentPane.add(scroll);
	    
		helpButton.addActionListener(new HelpButtonActionListener(helpFrame));
	    		
		// Set the look and feel to match the operating system we are running on
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			System.err.println("Error: Could not set system look and feel");
		}

		// Display the frame on the screen
		frame.setVisible(true);
	}
}

// ActionListener to handle the three select buttons in the user interface.
class SelectButtonActionListener implements ActionListener {

	private final JTextField textField;
	private final Component parent;
	
	public SelectButtonActionListener(final JTextField textField, final Component parent) {
		super();
		this.textField = textField;
		this.parent = parent;
	}
	
	// Open a file chooser dialog and set textField's text to the path of the selected file
	@Override
	public void actionPerformed(ActionEvent arg0) {
		final JFileChooser fc = new JFileChooser();
		
		// Only allow the user to select files with Excel extensions
		FileNameExtensionFilter excelFilter = new FileNameExtensionFilter("Excel Documents", new String[] {"xls", "xlsx"});
		fc.setFileFilter(excelFilter);
		fc.setAcceptAllFileFilterUsed(false);
		
		int returnVal = fc.showDialog(parent, "Select");
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path = fc.getSelectedFile().getPath();
			textField.setText(path);
		}
	}
}

//ActionListener to handle the output select buttons in the user interface.
class OutputSelectButtonActionListener implements ActionListener {

	private final JTextField textField;
	private final Component parent;
	
	public OutputSelectButtonActionListener(final JTextField textField, final Component parent) {
		super();
		this.textField = textField;
		this.parent = parent;
	}
	
	// Open a file chooser dialog and set textField's text to the path of the desired place to store the output file
	@Override
	public void actionPerformed(ActionEvent arg0) {
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(null);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.showSaveDialog(parent);
		String path = fc.getSelectedFile().getPath();
		textField.setText(path);
	}
}

// ActionListener to handle the schedule button
class ScheduleButtonActionListener implements ActionListener {

	private final JTextField roomDataTextField;
	private final JTextField studentDataTextField;
	private final JTextField teacherDataTextField;
	private final JTextField outputDataTextField;
	private final JFrame parent;
	
	public ScheduleButtonActionListener(final JTextField roomDataTextField, final JTextField studentDataTextField, final JTextField teacherDataTextField, final JTextField outputDataTextField, final JFrame parent) {
		this.roomDataTextField = roomDataTextField;
		this.studentDataTextField = studentDataTextField;
		this.teacherDataTextField = teacherDataTextField;
		this.outputDataTextField = outputDataTextField;
		this.parent = parent;
	}
	
	// Open the files specified in the three text fields and run our scheduling algorithm
	@Override
	@SuppressWarnings(value = { "unused" })
	public void actionPerformed(ActionEvent e) {
		System.out.println("Scheduling would happen now");
		System.out.println("Room Data File Path: " + roomDataTextField.getText());
		System.out.println("Student Data File Path: " + studentDataTextField.getText());
		System.out.println("Teacher Data File Path: " + teacherDataTextField.getText());
		
		if (roomDataTextField.getText().length() == 0) {
			showErrorDialog("Please specify a spreadsheet with room data.");
		} else if (studentDataTextField.getText().length() == 0) {
			showErrorDialog("Please specify a spreadsheet with student data.");
		} else if (teacherDataTextField.getText().length() == 0) {
			showErrorDialog("Please specify a spreadsheet with teacher data.");
		} else if (outputDataTextField.getText().length() == 0) {
			showErrorDialog("Please specify an output path for the spreadsheet.");
		}
		
		ArrayList<Room> rooms = null;
		ArrayList<Student> students = null;
		ArrayList<Teacher> teachers = null;
		
		try {
			rooms = ExcelReader.parseRoomData(roomDataTextField.getText());
		} catch (Exception ex) {
			showErrorDialog(ex.getMessage());
			return;
		}

		try {
			students = ExcelReader.parseStudentData(studentDataTextField.getText());
		} catch (Exception ex) {
			showErrorDialog(ex.getMessage());
			return;
		}

		try {
			teachers = ExcelReader.parseTeacherData(teacherDataTextField.getText());
		} catch (Exception ex) {
			showErrorDialog(ex.getMessage());
			return;
		}
		
		// Scoring
		ScoringEngine se = new ScoringEngine(students, teachers);
		ArrayList<Pair> mandatoryPairs = se.getMandatoryPairs();
		Pair[][] scores = se.scoreNonMandatoryPairs();
		
		// Scheduling
		ArrayList<String> specialInstruments = HelperMethods.getAllPossibleSpecialInstruments(rooms);
		ArrayList<RoomDayTime> roomDayTimes = HelperMethods.getRoomDayTimes(rooms);
		Randomization randomizer = new Randomization(mandatoryPairs, scores, rooms, se.getStudents(), se.getTeachers());
		HungarianResult bestResult = randomizer.schedule();
		
		// Create a roomDayTimeInts HashSet based on the times of the roomDayTimes
		HashSet<Integer> roomDayTimeInts = new HashSet<Integer>();
		
		for (int i = 0; i < roomDayTimes.size(); i++) {
			roomDayTimeInts.add(roomDayTimes.get(i).getTime());
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
		Date date = new Date();
		String filename = outputDataTextField.getText() + "/Schedule-" + dateFormat.format(date) + ".xls";
		String [][] data = ExcelWriter.prepareDataToWriteToExcel(bestResult, roomDayTimeInts);
		ExcelWriter.writeDataToExcelFile(filename, data);
		
		showFinishDialog("Scheduling is complete!");
	}
	
	// Play a sound and display an alert notifying the user that the application is done running.
	public void showFinishDialog(String finishMessage){
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(parent, finishMessage, "Complete", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// Play an error sound and display an error dialog with a given message
	public void showErrorDialog(String errorMessage) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(parent, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}
}

// ActionListener to handle the help button
class HelpButtonActionListener implements ActionListener {
	
	private final JFrame helpFrame;	
	
	public HelpButtonActionListener(final JFrame helpFrame) {
		super();
		this.helpFrame = helpFrame;
	}
	
	// Show the help dialog
		@Override
		public void actionPerformed(ActionEvent e) {
			helpFrame.setVisible(true);
		}
			
	}


