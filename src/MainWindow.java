/*
 * File: MainWindow.java
 * Description: Implements the main window of the user interface
 * 				for our COMP 523 Music Scheduler program.
 */

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
		frame.setSize(450, 340);
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
		layout.putConstraint(SpringLayout.SOUTH, roomDataTextField, 0, SpringLayout.SOUTH, selectRoomDataButton);;
		
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
		
		JButton scheduleButton = new JButton("Schedule");
		scheduleButton.addActionListener(new ScheduleButtonActionListener(roomDataTextField, studentDataTextField, teacherDataTextField, frame));;
		layout.putConstraint(SpringLayout.NORTH, scheduleButton, 30, SpringLayout.SOUTH, teacherDataTextField);
		layout.putConstraint(SpringLayout.WEST, scheduleButton, 150, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.EAST, scheduleButton, -150, SpringLayout.EAST, contentPane);
		
		contentPane.add(scheduleButton);
		
		JButton helpButton = new JButton("?");
		helpButton.addActionListener(new HelpButtonActionListener());
		layout.putConstraint(SpringLayout.SOUTH, helpButton, -15, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, helpButton, 15, SpringLayout.WEST, contentPane);
		
		contentPane.add(helpButton);
		
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

// ActionListener to handle the schedule button
class ScheduleButtonActionListener implements ActionListener {

	private final JTextField roomDataTextField;
	private final JTextField studentDataTextField;
	private final JTextField teacherDataTextField;
	private final JFrame parent;
	
	public ScheduleButtonActionListener(final JTextField roomDataTextField, final JTextField studentDataTextField, final JTextField teacherDataTextField, final JFrame parent) {
		this.roomDataTextField = roomDataTextField;
		this.studentDataTextField = studentDataTextField;
		this.teacherDataTextField = teacherDataTextField;
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
		
		String filename = "/Users/afrank11/COMP523/testExcelOutput.xls";
		String [][] data = ExcelWriter.prepareDataToWriteToExcel(bestResult);
		ExcelWriter.writeDataToExcelFile(filename, data);
	}
	
	// Play an error sound and display an error dialog with a given message
	public void showErrorDialog(String errorMessage) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(parent, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}
}

// ActionListener to handle the help button
class HelpButtonActionListener implements ActionListener {

	public HelpButtonActionListener() {
		
	}
	
	// Show the help dialog
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO show help dialog
		System.out.println("Help would now show");
	}
}
