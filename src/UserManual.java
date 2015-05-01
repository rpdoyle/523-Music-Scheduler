/**
 * Defines strings to be printed in the help window
 *
 */
public class UserManual {

	public static final String MESSAGE_1 = "The music lesson scheduling application is an executable .jar file called MEScheduler.jar. It is compatible with both Windows and Mac OS X machines. The file is in a zipped folder that contains the Apache POI License and Notice files. The application can be downloaded from: (wherever we decide to put the application).";

	public static final String MESSAGE_2 = " In order to run this program, you must download and install the Java Runtime Environment if it is not already on your machine. This can be done at the following link: http://www.java.com/en/download/. Once this software has been installed, you can unzip the MEScheduler folder and run the application by double-clicking on the .jar file.";

	public static final String MESSAGE_3 = " This application is designed to work only with a google form formatted in a particular way. The correctly formatted forms can be found in the following location: (insert link to the google forms). The student and teacher forms should be distributed to the student and the teachers, respectively, looking to enroll in musical empowerment. The room form is designed to be filled out by the administrators. Any other format will not be recognized by the program, and will thus not be able to run. Once all of the information has been inputted into the google form, download the responses as .xls or .xlsx files to be used in the application. If you wish to change any information or omit any particular candidates, now is the time to do so before they are used in the application.";

	public static final String MESSAGE_4 = " When running the music scheduler, you will be instructed to select three Excel files in .xls or .xlsx format. The excel files that are selected should contain teacher information, student information, and room information for the scheduler. You will also select where you would like the generated schedule to be saved. Once these inputs have been selected, click the Schedule button to begin the scheduling process. The scheduler will run to generate a new schedule and alert you when the application is finished running. The output file will contain the time, day, and room that a lesson will be held along with the names of the teacher and student and which instrument they selected. If there is an error with the format of the input excel documents, the application will alert you to which cell contains the error.";

}
