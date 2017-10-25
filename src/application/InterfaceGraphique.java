package application;


import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;


public class InterfaceGraphique {

	//	Variables declaration :
	Stage stage = new Stage();
	Scene scene;
	Button save,undoMove;
	int whoPlaysFirst, playerV;
	

	//	principal Window declaration
	Button exitButton, playerVsPlayer, playerVsMachine, importAPart ;
	VBox verticalLayout;

	//  Player vs Player Window declaration
	GridPane principalLayout;
	BorderPane window;
	HBox TOP,DOWN;
	Button back;
	Label theWinner, whoSTurnIs;
	Circle circlePlayers;



	//	Player vs Machine window declaration
	int valueToSend;
	Button easyN,mediumN,hardN,expertN;



	public InterfaceGraphique() {
		// TODO Auto-generated constructor stub
		stage.setResizable(false);
		stage.centerOnScreen();

		principalLayout = new GridPane();
		principalLayout.getStylesheets().add("Styling.css");
		
		for(int i=0; i<6;i++){
			for(int j=0;j<7;j++){
				Button currentButton = new Button();
				currentButton.setAccessibleText(""+j);
				currentButton.setPrefSize(50, 70);
				currentButton.getStyleClass().add("button1");
				principalLayout.add(currentButton, j, i);
			}
		}	
		
		
		circlePlayers = new Circle(100.0f,100.0f,30.0f);
		whoSTurnIs = new Label("Player's 1 turn");
		back = new Button("go back to principal menu");
		back.setPrefSize(150, 50);

		save = new Button("Save this part");
		save.setPrefSize(150, 50);

		undoMove = new Button("Undo move");
		undoMove.setPrefSize(150, 50);
	}

	// the pricipal window
	public void principalWindow(){
		stage.setTitle(" Principal window --' ");
		

		exitButton = new Button("Exit Window");
		exitButton.setPrefSize(200, 70);
		exitButton.setId("principalWindowButtons");
		exitButton.setOnAction(click -> {
			stage.close();
		});

		playerVsPlayer = new Button("Player Versus Player");
		playerVsPlayer.setPrefSize(200, 70);
		playerVsPlayer.setOnAction(click ->{
			playerVersusPlayerWindow();
		});

		playerVsMachine = new Button("Player Versus Machine");
		playerVsMachine.setPrefSize(200, 70);
		playerVsMachine.setOnAction(click ->{
			playerVersusMachineWindow();
		});

		importAPart = new Button("Importer une partie");
		importAPart.setPrefSize(200, 70);
		importAPart.setOnAction(click ->{
		});


		verticalLayout = new VBox();
		verticalLayout.setPadding(new Insets(50,50,50,50));
		verticalLayout.setSpacing(20);
		verticalLayout.getChildren().addAll(playerVsPlayer,playerVsMachine,importAPart,exitButton);

		verticalLayout.setAlignment(Pos.BOTTOM_CENTER);
		verticalLayout.setId("principalWindowDesign");
		scene = new Scene(verticalLayout,500,500);
		scene.getStylesheets().add("styling.css");
		stage.setScene(scene);
		stage.show();
	}

	// player Versus player Window
	public void playerVersusPlayerWindow(){
		stage.setTitle(" Puissance 4 : Player Versus Player  ");

		TOP = new HBox();
		DOWN = new HBox();
		
		circlePlayers.setFill(Color.YELLOW);
		
		
		
		
		TOP.setPadding(new Insets(25));
		TOP.setSpacing(25);
		TOP.setAlignment(Pos.CENTER);
		TOP.getChildren().addAll(circlePlayers,whoSTurnIs,back,save);

		theWinner = new Label("");
		DOWN.setPadding(new Insets(25));
		DOWN.setAlignment(Pos.CENTER);
		DOWN.getChildren().addAll(theWinner);


		principalLayout.setPadding(new Insets(25));
		principalLayout.setAlignment(Pos.CENTER);


		window = new BorderPane();
		window.setTop(TOP);
		window.setBottom(DOWN);
		window.setCenter(principalLayout);
		
		scene = new Scene(window,500,500);
		stage.setScene(scene);
		stage.show();
	}


	// player versus Machine Window 
	public void playerVersusMachineWindow(){
			stage.setTitle(" Puissance 4 : Player Versus Machine  ");

		
			TOP = new HBox();
			DOWN = new HBox();

			back.setOnAction(click -> {
				principalWindow();
			});
			TOP.setPadding(new Insets(25));
			TOP.setSpacing(15);
			TOP.getChildren().addAll(back,undoMove,save);

			theWinner = new Label("");
			DOWN.setPadding(new Insets(25,50,25,130)); //130 left
			DOWN.getChildren().addAll(theWinner);


			principalLayout.setPadding(new Insets(25));



			window = new BorderPane();
			window.setTop(TOP);
			window.setBottom(DOWN);
			window.setCenter(principalLayout);


			scene = new Scene(window,500,500);
			stage.setScene(scene);
			stage.show();
		
	}

	// window to display when the user chose Player versus Machine, to chose which one to start first
	public int beforPvM(){

		Stage temporary = new Stage();
		temporary.centerOnScreen();
		temporary.setResizable(false);
		temporary.setTitle(" Who wants to play first ? -_- ");

		temporary.centerOnScreen();
		window = new BorderPane();

		HBox choice = new HBox();
		VBox labels = new VBox();

		labels.setPadding(new Insets(30,40,15,150));
		Label who = new Label("WHO WANT TO PLAY FIRST ? ");
		labels.getChildren().addAll(who);


		choice.setSpacing(25);
		choice.setPadding(new Insets(30));

		Button player = new Button("PLAYER");
		player.setPrefSize(200, 50);
		player.setOnAction(click ->{
			valueToSend = 1;
			temporary.close();
		});

		Button machine = new Button("MACHINE");
		machine.setPrefSize(200, 50);
		machine.setOnAction(click -> {
			valueToSend = -1;
			temporary.close();
		});
		choice.getChildren().addAll(player,machine);

		window = new BorderPane();
		window.setTop(labels);
		window.setBottom(choice);		



		scene = new Scene(window,450,150);
		temporary.setScene(scene);
		temporary.showAndWait();

		temporary.setOnCloseRequest(click ->{
			valueToSend = 0;
		});

		return valueToSend;
	}


	// Method to chose the difficulty level 
	public int choseTheDifficulty(){
		Stage tem = new Stage();
		tem.setTitle("chose the diff :3 ");
		
		easyN = new Button("EASY");
		mediumN = new Button("MEDIUM");
		hardN = new Button("HARD");
		expertN = new Button("EXPERT");
		Label bla = new Label(" YOUR DIFFICULTY : ");
		
				
		
		easyN.setPrefSize(200, 70);
		mediumN.setPrefSize(200, 70);
		hardN.setPrefSize(200, 70);
		expertN.setPrefSize(200, 70);
		
		easyN.setOnAction(click ->{
			valueToSend = 2;
			tem.close();
		});
		mediumN.setOnAction(click ->{
			valueToSend = 4;
			tem.close();
		});
		hardN.setOnAction(click ->{
			valueToSend = 6;
			tem.close();
		});
		expertN.setOnAction(click ->{
			valueToSend = 8;
			tem.close();
		});
		
		
		verticalLayout = new VBox();
		verticalLayout.setSpacing(20);
		verticalLayout.setPadding(new Insets(35));
		verticalLayout.getChildren().addAll(bla,easyN,mediumN,hardN,expertN);
		
		scene = new Scene(verticalLayout);
		tem.setScene(scene);
		tem.showAndWait();
		tem.setOnCloseRequest(click ->{
			valueToSend = 0;
		});
		
		return valueToSend;
	}


}
