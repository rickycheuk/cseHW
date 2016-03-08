package pm.gui;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import properties_manager.PropertiesManager;
import saf.ui.AppGUI;
import saf.AppTemplate;
import saf.components.AppWorkspaceComponent;
import saf.ui.AppMessageDialogSingleton;
import saf.ui.AppYesNoCancelDialogSingleton;

/**
 * This class serves as the workspace component for this application, providing
 * the user interface controls for editing work.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class Workspace extends AppWorkspaceComponent {
 static final String CLASS_MAX_PANE = "max_pane";
    static final String CLASS_TAG_BUTTON = "tag_button";
    static final String EMPTY_TEXT = "";
    static final int BUTTON_TAG_WIDTH = 50;

    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;


    // WE'LL PUT THE WORKSPACE INSIDE A SPLIT PANE
    SplitPane workspaceSplitPane;

    // THESE ARE THE BUTTONS FOR ADDING AND REMOVING COMPONENTS
    BorderPane leftPane;
    Pane tagToolbar;
    Pane tagToolbar2;
    ScrollPane tagToolbarScrollPane;
    ScrollPane tagToolbarScrollPane2;
    ArrayList<Button> tagButtons;

    // THIS IS THE TREE REPRESENTING THE DOM
    TreeView htmlTree;
    ScrollPane treeScrollPane;

    // AND FOR EDITING A TAG
    Pane tagEditorPane;
    ScrollPane tagEditorScrollPane;
    Label tagEditorLabel;
    ArrayList<Label> tagPropertyLabels;
    ArrayList<TextField> tagPropertyTextFields;

    // THIS WILL CONTAIN BOTH THE TREE AND THE TREE EDITOR
    VBox editVBox;

    // THIS IS WHERE WE CAN VIEW THE WEB PAGE OR DIRECTLY EDIT THE CSS
    TabPane rightPane;

    // HERE ARE OUR DIALOGS
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;

    /**
     * Constructor for initializing the workspace, note that this constructor
     * will fully setup the workspace user interface for use.
     *
     * @param initApp The application this workspace is part of.
     *
     * @throws IOException Thrown should there be an error loading application
     * data for setting up the user interface.
     */
    public Workspace(AppTemplate initApp) throws IOException {
	// KEEP THIS FOR LATER
	app = initApp;

	// KEEP THE GUI FOR LATER
	gui = app.getGUI();

	// THIS WILL PROVIDE US WITH OUR CUSTOM UI SETTINGS AND TEXT
	PropertiesManager propsSingleton = PropertiesManager.getPropertiesManager();

	// WE'LL ORGANIZE OUR WORKSPACE COMPONENTS USING A BORDER PANE
	workspace = new BorderPane(); 
	// FIRST THE LEFT HALF OF THE SPLIT PANE
	leftPane = new BorderPane();
        

	// THIS WILL MANAGE ALL EDITING EVENTS

        VBox vbox = new VBox(1);
        vbox.setPrefWidth(260);
        vbox.setMinHeight(80);
	// THIS IS THE TOP TOOLBAR
	tagToolbar = new FlowPane(Orientation.HORIZONTAL);
        tagToolbar.setStyle("-fx-background-color: #75bdd1; -fx-border-color: #327b8f; -fx-border-width: 3");
        tagToolbar.setPrefWidth(260);
        tagToolbar.setMinHeight(80);
        tagButtons = new ArrayList();
        // AND NOW USE THE LOADED TAG TYPES TO ADD BUTTONS
        Button sButton = new Button("<-");
        tagButtons.add(sButton);
        sButton.setMaxWidth(BUTTON_TAG_WIDTH);
        sButton.setMinWidth(BUTTON_TAG_WIDTH);
        sButton.setPrefWidth(BUTTON_TAG_WIDTH);
        sButton.setPrefHeight(BUTTON_TAG_WIDTH);
        sButton.setTranslateX(10);
        sButton.setTranslateY(10);
        tagToolbar.getChildren().add(sButton);

        Button dButton = new Button("(X)");
        tagButtons.add(dButton);
        dButton.setMaxWidth(BUTTON_TAG_WIDTH);
        dButton.setMinWidth(BUTTON_TAG_WIDTH);
        dButton.setPrefWidth(BUTTON_TAG_WIDTH);
        dButton.setPrefHeight(BUTTON_TAG_WIDTH);
        dButton.setTranslateX(20);
        dButton.setTranslateY(10);
        tagToolbar.getChildren().add(dButton);

        Button rButton = new Button("[]");
        tagButtons.add(rButton);
        rButton.setMaxWidth(BUTTON_TAG_WIDTH);
        rButton.setMinWidth(BUTTON_TAG_WIDTH);
        rButton.setPrefWidth(BUTTON_TAG_WIDTH);
        rButton.setPrefHeight(BUTTON_TAG_WIDTH);
        rButton.setTranslateX(30);
        rButton.setTranslateY(10);
        tagToolbar.getChildren().add(rButton);
        
        Button cButton = new Button("O");
        tagButtons.add(cButton);
        cButton.setMaxWidth(BUTTON_TAG_WIDTH);
        cButton.setMinWidth(BUTTON_TAG_WIDTH);
        cButton.setPrefWidth(BUTTON_TAG_WIDTH);
        cButton.setPrefHeight(BUTTON_TAG_WIDTH);
        cButton.setTranslateX(40);
        cButton.setTranslateY(10);
        tagToolbar.getChildren().add(cButton);
        
        tagToolbar2 = new FlowPane(Orientation.HORIZONTAL);
        tagToolbar2.setStyle("-fx-background-color: #75bdd1; -fx-border-color: #327b8f; -fx-border-width: 3");
        tagToolbar2.setPrefWidth(260);
        tagToolbar2.setMinHeight(80);
        
        Button upButton = new Button("^");
        tagButtons.add(upButton);
        upButton.setMaxWidth(BUTTON_TAG_WIDTH+50);
        upButton.setMinWidth(BUTTON_TAG_WIDTH+50);
        upButton.setPrefWidth(BUTTON_TAG_WIDTH+50);
        upButton.setPrefHeight(BUTTON_TAG_WIDTH);
        upButton.setTranslateX(10);
        upButton.setTranslateY(10);
        tagToolbar2.getChildren().add(upButton);
	
        Button doButton = new Button("v");
        tagButtons.add(doButton);
        doButton.setMaxWidth(BUTTON_TAG_WIDTH+50);
        doButton.setMinWidth(BUTTON_TAG_WIDTH+50);
        doButton.setPrefWidth(BUTTON_TAG_WIDTH+50);
        doButton.setPrefHeight(BUTTON_TAG_WIDTH);
        doButton.setTranslateX(20);
        doButton.setTranslateY(10);
        tagToolbar2.getChildren().add(doButton);
        
        Pane tagToolbar3 = new FlowPane(Orientation.VERTICAL);
        tagToolbar3.setStyle("-fx-background-color: #75bdd1; -fx-border-color: #327b8f; -fx-border-width: 3");
        tagToolbar3.setPrefWidth(260);
        tagToolbar3.setMaxHeight(80);
        tagToolbar3.setMinHeight(80);
        Text t = new Text("Background Color");
        t.setTranslateX(10);
        t.setTranslateY(10);
        t.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        tagToolbar3.getChildren().add(t);
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.WHITE);
        colorPicker.setTranslateY(20);
        colorPicker.setTranslateX(10);
        tagToolbar3.getChildren().add(colorPicker);

        Pane tagToolbar4 = new FlowPane(Orientation.VERTICAL);
        tagToolbar4.setStyle("-fx-background-color: #75bdd1; -fx-border-color: #327b8f; -fx-border-width: 3");
        tagToolbar4.setPrefWidth(260);
        tagToolbar4.setMaxHeight(80);
        tagToolbar4.setMinHeight(80);
        Text t1 = new Text("Fill Color");
        t1.setTranslateX(10);
        t1.setTranslateY(10);
        t1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        tagToolbar4.getChildren().add(t1);
        final ColorPicker colorPicker2 = new ColorPicker();
        colorPicker2.setValue(Color.WHITE);
        colorPicker2.setTranslateY(20);
        colorPicker2.setTranslateX(10);
        tagToolbar4.getChildren().add(colorPicker2);
	
        Pane tagToolbar5 = new FlowPane(Orientation.VERTICAL);
        tagToolbar5.setStyle("-fx-background-color: #75bdd1; -fx-border-color: #327b8f; -fx-border-width: 3");
        tagToolbar5.setPrefWidth(260);
        tagToolbar5.setMaxHeight(80);
        tagToolbar5.setMinHeight(80);
        Text t2 = new Text("Outline Color");
        t2.setTranslateX(10);
        t2.setTranslateY(10);
        t2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        tagToolbar5.getChildren().add(t2);
        final ColorPicker colorPicker3 = new ColorPicker();
        colorPicker3.setValue(Color.BLACK);
        colorPicker3.setTranslateY(20);
        colorPicker3.setTranslateX(10);
        tagToolbar5.getChildren().add(colorPicker3);
        
        Pane tagToolbar6 = new FlowPane(Orientation.HORIZONTAL);
        tagToolbar6.setStyle("-fx-background-color: #75bdd1; -fx-border-color: #327b8f; -fx-border-width: 3");
        tagToolbar6.setPrefWidth(260);
        tagToolbar6.setMinHeight(80);
        tagToolbar6.setMaxHeight(80);
        Text t3 = new Text("Outline Thickness");
        t3.setTranslateX(10);
        t3.setTranslateY(10);
        t3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        tagToolbar5.getChildren().add(t3);
        
        Pane tagToolbar7 = new FlowPane(Orientation.HORIZONTAL);
        tagToolbar7.setStyle("-fx-background-color: #75bdd1; -fx-border-color: #327b8f; -fx-border-width: 3");
        tagToolbar7.setPrefWidth(260);
        tagToolbar7.setMinHeight(80);
        tagToolbar7.setMaxHeight(80);
        
        vbox.getChildren().addAll(tagToolbar,tagToolbar2,tagToolbar3,tagToolbar4,tagToolbar5,tagToolbar6,tagToolbar7);
        
	// AND NOW THE REGION FOR EDITING TAG PROPERTIES
	tagEditorPane = new Pane();
        tagEditorPane.setStyle("-fx-background-color: #"+ colorPicker.getValue().toString().substring(2,8));
        tagEditorPane.setPrefWidth(800);
        tagEditorPane.setMaxWidth(800);
        tagEditorPane.setMinWidth(800);
        tagEditorPane.setPrefHeight(600); 
        
         colorPicker.setOnAction(e -> {
		tagEditorPane.setStyle("-fx-background-color: #"+ colorPicker.getValue().toString().substring(2,8));
	    });
	// PUT THEM IN THE LEFT
	leftPane.setLeft(vbox);
        leftPane.getLeft().setStyle("-fx-border-color: #327b8f");
	leftPane.setRight(tagEditorPane);
        
	// NOW FOR THE RIGHT
	rightPane = new TabPane();


	// AND NOW PUT IT IN THE WORKSPACE
	workspaceSplitPane = new SplitPane();
	workspaceSplitPane.getItems().add(leftPane);
	workspaceSplitPane.getItems().add(rightPane);

	// AND FINALLY, LET'S MAKE THE SPLIT PANE THE WORKSPACE
	workspace = new Pane();
	workspace.getChildren().add(workspaceSplitPane);

        // NOTE THAT WE HAVE NOT PUT THE WORKSPACE INTO THE WINDOW,
	// THAT WILL BE DONE WHEN THE USER EITHER CREATES A NEW
	// COURSE OR LOADS AN EXISTING ONE FOR EDITING
	workspaceActivated = false;
    }
    
    /**
     * This function specifies the CSS style classes for all the UI components
     * known at the time the workspace is initially constructed. Note that the
     * tag editor controls are added and removed dynamicaly as the application
     * runs so they will have their style setup separately.
     */
    @Override
    public void initStyle() {
	// NOTE THAT EACH CLASS SHOULD CORRESPOND TO
	// A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
	// CSS FILE
    }

    /**
     * This function reloads all the controls for editing tag attributes into
     * the workspace.
     */
    @Override
    public void reloadWorkspace() {

    }
}
