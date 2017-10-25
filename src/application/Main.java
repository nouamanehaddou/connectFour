package application;


import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// the Main class
public class Main extends Application {

	int playerMove;
	int counter = 0;
	int LineValueToSend; 

	Puissance4 p = new Puissance4();
	Puissance4Position pos = new Puissance4Position();
	InterfaceGraphique IHM = new InterfaceGraphique();
	Puissance4Move move ;
	Alert alert = new Alert(AlertType.INFORMATION);
	LinkedList<Puissance4Position> historiqueP= new LinkedList<Puissance4Position>();





	public void start(Stage primaryStage) {

		IHM.principalWindow();
		printPosition(pos);

		boolean player = true;
		boolean machine = false;


		// lambda expression to take back the user to the principal window
		IHM.back.setOnAction(click ->{
			IHM.principalWindow();
			for (int i=0; i<42; i++) {
				pos.board[i] = 0;
			}	
			printPosition(pos);
			IHM.principalLayout.setDisable(false);
		});


		// lambda expression to handle the saving button click
		IHM.save.setOnAction(click ->{
			try {
				XMLEncoder encoder = new XMLEncoder(new FileOutputStream("./src/files/play.xml"));
				encoder.writeObject(pos);
				encoder.flush();
				JOptionPane.showMessageDialog(null, "sauvegard avec succe");
			} catch (Exception ex) {
				System.out.println("erreur de sauvegaard de fichier");
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		});
		
		
		
		
		/* lambda expression to import a part saved in the play.xml located in the "files" dirictory */ 
		IHM.importAPart.setOnAction(click ->{
			JFileChooser fs = new JFileChooser(new File("./src/files/"));
			fs.setDialogTitle("Choisir Un Fichier");
			int result = fs.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION){
				try {
					File fi = fs.getSelectedFile();
					XMLDecoder decoder = new XMLDecoder(new FileInputStream("./src/files/play.xml"));
					pos = (Puissance4Position) decoder.readObject();
					JOptionPane.showMessageDialog(null, "sauvegard avec succe");
					decoder.close();
				} catch (Exception ex) {
					System.out.println("erreur de sauvegaard de fichier");
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
			IHM.playerV=-1;
			System.out.println(pos);
			IHM.playerVersusMachineWindow();
			printPosition(pos);
		});




		// lambda expression to handle the undoMove button click when playing a part against machine
		IHM.undoMove.setOnAction(click ->{
			if(historiqueP.isEmpty()){
				alert.setTitle("Information Dialog");
				alert.setContentText("THERE IS NO MOVE TO UNDO");
				alert.showAndWait();
			}else{
				historiqueP.removeLast();
				if(!historiqueP.isEmpty()){
					pos=historiqueP.getLast();
				}
				printPosition(pos);
				System.out.println(pos);
			}
		});

		// lambda expression to handle the playerVsMachine button click
		IHM.playerVsMachine.setOnAction(click ->{
			IHM.playerV = -1;
			int difficulty = IHM.choseTheDifficulty();
			if( difficulty != 0){
				p.depthBae = difficulty;
				IHM.playerVersusMachineWindow();
				if(IHM.beforPvM() == -1){
					Vector v = p.alphaBeta(0, pos,machine);
					pos = (Puissance4Position) v.elementAt(1);
					historiqueP.add(pos);
					printPosition(pos);
				}
			}
		});

		// lambda expression to handle the playerVsPlayer button click
		IHM.playerVsPlayer.setOnAction(click ->{
			IHM.playerV = 1;
			IHM.playerVersusPlayerWindow();
		});



		// loop for listening to the user clicks
		for(int _i=0; _i<42;_i++){
			Button currentButton = (Button) IHM.principalLayout.getChildren().get(_i);
			currentButton.setOnAction( click ->{
				LineValueToSend = -1;
				playerMove = Integer.parseInt(currentButton.getAccessibleText());
				Move move = p.createMove(pos, playerMove);


				if(IHM.playerV==1){
					if(counter%2 == 0 ){
						pos = (Puissance4Position) p.makeMove(pos, GameSearch.HUMAN, move);
						IHM.circlePlayers.setFill(Color.RED);
						IHM.whoSTurnIs.setText("Player's 2 turn");
					}else{
						pos = (Puissance4Position) p.makeMove(pos, GameSearch.PROGRAM, move);
						IHM.circlePlayers.setFill(Color.YELLOW);
						IHM.whoSTurnIs.setText("Player's 1 turn");
					}
					printPosition(pos);	 
				}
				else{
					pos = (Puissance4Position)  p.makeMove(pos, GameSearch.HUMAN, move);
					printPosition(pos);
					if(!p.wonPosition(pos, GameSearch.HUMAN)){
						Vector v = p.alphaBeta(0, pos,machine);
						pos = (Puissance4Position) v.elementAt(1);
						historiqueP.add(pos);
						printPosition(pos);
					}
				}

				counter++;

				if (p.wonPosition(pos, GameSearch.PROGRAM)) {
					showMeSomething();
					IHM.theWinner.setText(" THE WINNER : PLAYER 2");
					alert.setTitle("Information Dialog");
					alert.setContentText("Player 2 won");
					alert.showAndWait();
					IHM.principalLayout.setDisable(true);
				}
				if (p.wonPosition(pos, GameSearch.HUMAN)) {
					showMeSomething();
					IHM.theWinner.setText(" THE WINNER : PLAYER 1");
					alert.setTitle("Information Dialog");
					alert.setContentText("Player 1 won");
					alert.showAndWait();
					IHM.principalLayout.setDisable(true);
				}
				if (p.drawnPosition(pos)) {
					showMeSomething();
					IHM.theWinner.setText(" IT'S A DRAW GAME Biach ! ");
					alert.setTitle("Information Dialog");
					alert.setContentText("drawn game");
					alert.showAndWait();
					IHM.principalLayout.setDisable(true);
				}

			});
		}
	}

	// method to reset all
	public void showMeSomething(){
		IHM.TOP.getChildren().removeAll(IHM.circlePlayers,IHM.whoSTurnIs,IHM.save);
		IHM.TOP.getChildren().remove(IHM.back);
		IHM.TOP.getChildren().add(IHM.back);
		IHM.back.setVisible(true);
	}


	// method to color the button using CSS
	public void printPosition(Position p){
		Puissance4Position blabla = (Puissance4Position) p ;
		for(int i=0; i<42;i++){
			Button currentButton = (Button) IHM.principalLayout.getChildren().get(i);
			if (blabla.board[i] == 1) {
				currentButton.setStyle(" -fx-background-color : yellow");
			} else if (blabla.board[i] == -1) {
				currentButton.setStyle(" -fx-background-color : red");
			}  else{
				currentButton.setStyle(null);
			}
		}
	}



	public static void main(String[] args) {
		launch(args);
	}
}
