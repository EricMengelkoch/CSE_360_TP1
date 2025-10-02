package guiDeleteUsers;

import database.Database;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ControllerDeleteUsers {
	
	/*********************************************************************************************

	User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	 */

	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;		

	
	/**********
	 * <p> Method: doSelectUser() </p>
	 * 
	 * <p> Description: This method uses the ComboBox widget, fetches which item in the ComboBox
	 * was selected (a user in this case), and establishes that user and the current user, setting
	 * easily accessible values without needing to do a query. </p>
	 * 
	 */
	protected static void doSelectUser() {
		ViewDeleteUsers.theSelectedUser = 
				(String) ViewDeleteUsers.combobox_SelectUser.getValue();
		theDatabase.getUserAccountDetails(ViewDeleteUsers.theSelectedUser);
		setupSelectedUser();
	}
	
	
	/**********
	 * <p> Method: repaintTheWindow() </p>
	 * 
	 * <p> Description: This method determines the current state of the window and then establishes
	 * the appropriate list of widgets in the Pane to show the proper set of current values. </p>
	 * 
	 */
	protected static void repaintTheWindow() {
		// Clear what had been displayed
		ViewDeleteUsers.theRootPane.getChildren().clear();
		
		// Determine which of the two views to show to the user
		if (ViewDeleteUsers.theSelectedUser.compareTo("<Select a User>") == 0) {
			// Only show the request to select a user to be updated and the ComboBox
			ViewDeleteUsers.theRootPane.getChildren().addAll(
					ViewDeleteUsers.label_PageTitle, ViewDeleteUsers.label_UserDetails, 
					ViewDeleteUsers.button_UpdateThisUser, ViewDeleteUsers.line_Separator1,
					ViewDeleteUsers.label_SelectUser, ViewDeleteUsers.combobox_SelectUser, 
					ViewDeleteUsers.line_Separator4, ViewDeleteUsers.button_Return,
					ViewDeleteUsers.button_Logout, ViewDeleteUsers.button_Quit);
		}
		else {
			// Show all the fields as there is a selected user (as opposed to the prompt)
				ViewDeleteUsers.theRootPane.getChildren().addAll(
					ViewDeleteUsers.label_PageTitle, ViewDeleteUsers.label_UserDetails,
					ViewDeleteUsers.button_UpdateThisUser, ViewDeleteUsers.line_Separator1,
					ViewDeleteUsers.label_SelectUser,
					ViewDeleteUsers.combobox_SelectUser, 
					ViewDeleteUsers.label_CurrentRoles,
					ViewDeleteUsers.label_SelectRoleToBeAdded,
					ViewDeleteUsers.combobox_SelectRoleToAdd,
					ViewDeleteUsers.button_AddRole,
					ViewDeleteUsers.line_Separator4, 
					ViewDeleteUsers.button_Return,
					ViewDeleteUsers.button_Logout,
					ViewDeleteUsers.button_Quit);
			} 
		
		// Add the list of widgets to the stage and show it
		
		// Set the title for the window
		ViewDeleteUsers.theStage.setTitle("CSE 360 Foundation Code: Admin Opertaions Page");
		ViewDeleteUsers.theStage.setScene(ViewDeleteUsers.theDeleteUsersScene);
		ViewDeleteUsers.theStage.show();
		}
	
	
	
	/**********
	 * <p> Method: setupSelectedUser() </p>
	 * 
	 * <p> Description: This method fetches the current values for the widgets whose values change
	 * based on which user has been selected and any actions that the admin takes. </p>
	 * 
	 */
	private static void setupSelectedUser() {
		System.out.println("*** Entering setupSelectedUser");
		
		// Create the list of roles that could be added for the currently selected user (e.g., Do
		// not show a role to add that the user already has!)
		ViewDeleteUsers.addList.clear();
		ViewDeleteUsers.addList.add("No");
		ViewDeleteUsers.addList.add("Yes");

		
		// Create the list or roles that the user currently has with proper use of a comma between
		// items
		boolean notTheFirst = false;
		String theCurrentRoles = "";
		
		// Admin role - It can only be at the head of a list
		if (theDatabase.getCurrentAdminRole()) {
			theCurrentRoles += "Admin";
			notTheFirst = true;
		}
		
		// Roles 1 - It could be at the head of the list or later in the list
		if (theDatabase.getCurrentNewStaff()) {
			if (notTheFirst)
				theCurrentRoles += ", Staff"; 
			else {
				theCurrentRoles += "Staff";
				notTheFirst = true;
			}
		}

		// Roles 2 - It could be at the head of the list or later in the list
		if (theDatabase.getCurrentNewStudent()) {
			if (notTheFirst)
				theCurrentRoles += ", Student"; 
			else {
				theCurrentRoles += "Student";
				notTheFirst = true;
			}
		}

		// Given the above actions, populate the related widgets with the new values
		ViewDeleteUsers.label_CurrentRoles.setText("This user's current roles: " + 
				theCurrentRoles);		
		ViewDeleteUsers.setupComboBoxUI(ViewDeleteUsers.combobox_SelectRoleToAdd, "Dialog",
				16, 150, 280, 205);
		ViewDeleteUsers.combobox_SelectRoleToAdd.setItems(FXCollections.
				observableArrayList(ViewDeleteUsers.addList));
		ViewDeleteUsers.combobox_SelectRoleToAdd.getSelectionModel().clearAndSelect(0);		
		ViewDeleteUsers.setupButtonUI(ViewDeleteUsers.button_AddRole, "Dialog", 16, 150, 
				Pos.CENTER, 460, 205);

		// Repaint the window showing this new values
		repaintTheWindow();
		
	}
	
	protected static void performDeleteUser() {
		// Determine which item in the ComboBox list was selected
		ViewDeleteUsers.confirmation =
				(String) ViewDeleteUsers.combobox_SelectRoleToAdd.getValue();
		
		// If the selection is No, don't do anything
		if (ViewDeleteUsers.confirmation.compareTo("No") != 0) {
	
		    if (ViewDeleteUsers.theSelectedUser == null || 
		        ViewDeleteUsers.theSelectedUser.equals("<Select a User>")) {
		        System.out.println("Please select a user to delete");
		        return;
		    }
		    
		    // Prevent deleting current user
		    String currentUser = getCurrentUsername(); 
		    if (ViewDeleteUsers.theSelectedUser.equals(currentUser)) {
		        System.out.println("Cannot delete your own account");
		        return;
		    }
		    
		    boolean success = theDatabase.deleteUser(ViewDeleteUsers.theSelectedUser);
		    
		    if (success) {
		        System.out.println("User deleted successfully");
		        // Refresh the user list in combobox
	        	refreshUserList();
		        ViewDeleteUsers.theSelectedUser = "<Select a User>";
		        repaintTheWindow();
		    } else {
		        System.out.println("Failed to delete user");
		    }
		} else {
			performReturn();
		}
	}
	
	private static String getCurrentUsername() {
		String un = ViewDeleteUsers.theUser.getUserName();
	    return un; 
	}
	
	private static void refreshUserList() {
    	List<String> userList = theDatabase.getUserList();
    	String currentUser = getCurrentUsername();
    
    	// Filter out current user
    	List<String> otherUsers = userList.stream()
       	 	.filter(user -> !user.equals(currentUser))
        	.collect(Collectors.toList());
    
    	ViewDeleteUsers.combobox_SelectUser.setItems(FXCollections.observableArrayList(otherUsers));
    	if (!otherUsers.isEmpty()) {
        	ViewDeleteUsers.combobox_SelectUser.getSelectionModel().select(0);
    	} else {
        	ViewDeleteUsers.combobox_SelectUser.getSelectionModel().clearSelection();
    	}
}

		/**********
	 * <p> Method: performReturn() </p>
	 * 
	 * <p> Description: This method returns the user (who must be an Admin as only admins are the
	 * only users who have access to this page) to the Admin Home page. </p>
	 * 
	 */
	protected static void performReturn() {
		guiAdminHome.ViewAdminHome.displayAdminHome(ViewDeleteUsers.theStage,
				ViewDeleteUsers.theUser);
	}
	
	
	/**********
	 * <p> Method: performLogout() </p>
	 * 
	 * <p> Description: This method logs out the current user and proceeds to the normal login
	 * page where existing users can log in or potential new users with a invitation code can
	 * start the process of setting up an account. </p>
	 * 
	 */
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewDeleteUsers.theStage);
	}
	
	
	
	/**********
	 * <p> Method: performQuit() </p>
	 * 
	 * <p> Description: This method terminates the execution of the program.  It leaves the
	 * database in a state where the normal login page will be displayed when the application is
	 * restarted.</p>
	 * 
	 */
	protected static void performQuit() {
		System.exit(0);
	
	
	}
}