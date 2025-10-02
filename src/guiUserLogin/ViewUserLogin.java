package guiUserLogin;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/*******
 * <p> Title: GUIStartupPage Class. </p>
 * 
 * <p> Description: The Java/FX-based System Startup Page.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-04-20 Initial version
 *  
 */

public class ViewUserLogin {

	/*-********************************************************************************************

	Attributes

	 *********************************************************************************************/

	// These are the application values required by the user interface

	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

	private static Label label_ApplicationTitle = new Label("Foundation Application Startup Page");

	// This set is for all subsequent starts of the system
	private static Label label_OperationalStartTitle = new Label("Log In or Invited User Account Setup ");
	private static Label label_LogInInsrtuctions = new Label("Enter your user name and password");
	static Alert alertUsernamePasswordError = new Alert(AlertType.INFORMATION);


	//	private User user;
	protected static TextField text_Username = new TextField();
	protected static PasswordField text_Password = new PasswordField();
	private static Button button_Login = new Button("Log In");	

	private static Label label_AccountSetupInsrtuctions = new Label("No account? "+	
			"Enter your invitation code and click on the Account Setup button");
	private static TextField text_Invitation = new TextField();
	private static Button button_SetupAccount = new Button("Setup Account");

	private static Button button_Quit = new Button("Quit");

	private static Stage theStage;	
	private static Pane theRootPane;
	public static Scene theUserLoginScene = null;	


	private static ViewUserLogin theView = null;	//	private static guiUserLogin.ControllerUserLogin theController;


	/*-********************************************************************************************

	Constructor

	 *********************************************************************************************/

	public static void displayUserLogin(Stage ps) {
		
		// Establish the references to the GUI. There is no current user yet.
		theStage = ps;
		
		// If not yet established, populate the static aspects of the GUI
		if (theView == null) theView = new ViewUserLogin();
		
		// Populate the dynamic aspects of the GUI with the data from the user and the current
		// state of the system.		
		text_Username.setText("");		// Reset the username and password from the last use
		text_Password.setText("");
		text_Invitation.setText("");	// Same for the invitation code

		// Set the title for the window, display the page, and wait for the Admin to do something
		theStage.setTitle("CSE 360 Foundation Code: User Login Page");		
		theStage.setScene(theUserLoginScene);
		theStage.show();
	}

	/**********
	 * <p> Method: ViewUserLoginPage() </p>
	 * 
	 * <p> Description: This method is called when the application first starts. It must handle
	 * two cases: 1) when no has been established and 2) when one or more users have been 
	 * established.
	 * 
	 * If there are no users in the database, this means that the person starting the system jmust
	 * be an administrator, so a special GUI is provided to allow this Admin to set a username and
	 * password.
	 * 
	 * If there is at least one user, then a different display is shown for existing users to login
	 * and for potential new users to provide an invitation code and if it is valid, they are taken
	 * to a page where they can specify a username and password.</p>
	 * 
	 * @param ps specifies the JavaFX Stage to be used for this GUI and it's methods
	 * 
	 * @param theRoot specifies the JavaFX Pane to be used for this GUI and it's methods
	 * 
	 * @param db specifies the Database to be used by this GUI and it's methods
	 * 
	 */
	private ViewUserLogin() {

		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		theUserLoginScene = new Scene(theRootPane, width, height);
		
		//CSS
		
		// Background color for the root pane
		   theRootPane.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #abbaab );");
		   
		// Title labels
		label_ApplicationTitle.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");
		label_OperationalStartTitle.setStyle("-fx-text-fill: #34495e;");
		
		
		// Instruction labels
		label_LogInInsrtuctions.setStyle("-fx-text-fill: #555;");
		label_AccountSetupInsrtuctions.setStyle("-fx-text-fill: #555;");
		
		
		// TextFields (rounded edges, padding, border)
		text_Username.setStyle(
		    "-fx-background-color: white; " +
		    "-fx-border-color: #3498db; " +
		    "-fx-border-radius: 5; " +
		    "-fx-background-radius: 5; " +
		    "-fx-padding: 5;"
		);
		
		
		text_Password.setStyle(
			    "-fx-background-color: white; " +
			    "-fx-border-color: #3498db; " +
			    "-fx-border-radius: 5; " +
			    "-fx-background-radius: 5; " +
			    "-fx-padding: 5;"
			);
		
		// Buttons with hover effect
		button_Login.setStyle(
		    "-fx-background-color: #758c75; " +
		    "-fx-text-fill: white; " +
		    "-fx-background-radius: 8;"
		    
		);
		
		
		button_Login.setOnMouseEntered(e -> button_Login.setStyle("-fx-background-color: #596b59; -fx-text-fill: white; -fx-background-radius: 8;"));
		button_Login.setOnMouseExited(e -> button_Login.setStyle("-fx-background-color: #758c75; -fx-text-fill: white; -fx-background-radius: 8;"));

		button_SetupAccount.setStyle(
		    "-fx-background-color: #758c75; " +
		    "-fx-text-fill: white; " +
		    "-fx-background-radius: 8;"
		    
		);
		
		
		button_SetupAccount.setOnMouseEntered(e -> button_SetupAccount.setStyle("-fx-background-color: #596b59; -fx-text-fill: white; -fx-background-radius: 8;"));
		button_SetupAccount.setOnMouseExited(e -> button_SetupAccount.setStyle("-fx-background-color: #758c75; -fx-text-fill: white; -fx-background-radius: 8;"));

		button_Quit.setStyle(
		    "-fx-background-color: #f2d58c; " +
		    "-fx-text-fill: black; " +
		    "-fx-background-radius: 8;"
		);
		button_Quit.setOnMouseEntered(e -> button_Quit.setStyle("-fx-background-color: #e8c469; -fx-text-fill: black; -fx-background-radius: 8;"));
		button_Quit.setOnMouseExited(e -> button_Quit.setStyle("-fx-background-color: #f2d58c; -fx-text-fill: black; -fx-background-radius: 8;"));
		
		
		//*********
		
		// Populate the window with the title and other common widgets and set their static state
		setupLabelUI(label_ApplicationTitle, "Times New Roman", 32, width, Pos.CENTER, 0, 10);

		setupLabelUI(label_OperationalStartTitle, "Times New Roman", 24, width, Pos.CENTER, 0, 60);


		// Existing user log in portion of the page

		setupLabelUI(label_LogInInsrtuctions, "Arial", 18, width, Pos.BASELINE_LEFT, 20, 120);

		// Establish the text input operand field for the username
		setupTextUI(text_Username, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 160, true);
		text_Username.setPromptText("Enter Username");

		// Establish the text input operand field for the password
		setupTextUI(text_Password, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 210, true);
		text_Password.setPromptText("Enter Password");

		// Set up the Log In button
		setupButtonUI(button_Login, "Dialog", 18, 200, Pos.CENTER, 475, 180);
		button_Login.setOnAction((event) -> {ControllerUserLogin.doLogin(theStage); });

		getAlertUsernamePasswordError().setTitle("Invalid username/password!");
		getAlertUsernamePasswordError().setHeaderText(null);


		// The invitation to setup an account portion of the page

		setupLabelUI(label_AccountSetupInsrtuctions, "Arial", 18, width, Pos.BASELINE_LEFT, 20, 300);

		// Establish the text input operand field for the password
		setupTextUI(text_Invitation, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 340, true);
		text_Invitation.setPromptText("Enter Invitation Code");

		// Set up the setup button
		setupButtonUI(button_SetupAccount, "Dialog", 18, 200, Pos.CENTER, 475, 340);
		button_SetupAccount.setOnAction((event) -> {
			System.out.println("**** Calling doSetupAccount");
			ControllerUserLogin.doSetupAccount(theStage, text_Invitation.getText());
		});

		// Set up the Quit button  
		setupButtonUI(button_Quit, "Dialog", 18, 150, Pos.CENTER, 500, 520);
		button_Quit.setOnAction((event) -> {ControllerUserLogin.performQuit(); });

		//		theRootPane.getChildren().clear();

		theRootPane.getChildren().addAll(
				label_ApplicationTitle, 
				label_OperationalStartTitle,
				label_LogInInsrtuctions, label_AccountSetupInsrtuctions, text_Username,
				button_Login, text_Password, text_Invitation, button_SetupAccount,
				button_Quit);
	}


	/*-********************************************************************************************

	Helper methods to reduce code length

	 *********************************************************************************************/

	/**********
	 * Private local method to initialize the standard fields for a label
	 */

	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}


	/**********
	 * Private local method to initialize the standard fields for a button
	 * 
	 * @param b		The Button object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}

	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}

	public static Alert getAlertUsernamePasswordError() {
		return alertUsernamePasswordError;
	}

	public static void setAlertUsernamePasswordError(Alert alertUsernamePasswordError) {
		ViewUserLogin.alertUsernamePasswordError = alertUsernamePasswordError;
	}		
}
