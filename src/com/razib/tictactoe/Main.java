package com.razib.tictactoe;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;

import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    private boolean playable = true;
    private boolean turnX = true;
    private Tile[][] board = new Tile[3][3];
    private ArrayList<Combo> combos = new ArrayList<>();
    private Pane root = new Pane();
    private Image mainIcon = new Image("com/razib/tictactoe/icons/tic-tac-toe-game-icon-256.png");
    private Group endGroup = new Group();
    private Scene myscene = new Scene(createContent());
    private Scene endScene = new Scene(endGroup,600,200);



    @Override
    public void start(Stage stage) throws Exception {

//        Line myline = new Line();
//        myline.setStartY(150);
//        myline.setStartX(100);
//        myline.setEndX(500);
//        myline.setEndY(100);
//
//        Text text = new Text();
//        text.setFont(new Font(50));
//        text.setText("This is a test text made by Razib Sarker");
//        text.setX(100);
//        text.setY(150);
//
//
//        //Creating a Path
//        Path path = new Path();
//
//        //Moving to the starting point
//        MoveTo moveTo = new MoveTo(108, 71);
//
//        //Creating 1st line
//        LineTo line1 = new LineTo(321, 161);
//
//        //Creating 2nd line
//        LineTo line2 = new LineTo(126,232);
//
//        //Creating 3rd line
//        LineTo line3 = new LineTo(232,52);
//
//        //Creating 4th line
//        LineTo line4 = new LineTo(269, 250);
//
//        //Creating 4th line
//        LineTo line5 = new LineTo(108, 71);
//
//        //Adding all the elements to the path
//
//        path.getElements().addAll(moveTo,line1, line2, line3, line4, line5);
//
//
//
//        Group root = new Group(myline,text,path);
//        Scene scene = new Scene(root,600,300);
//        stage.setTitle("Sample Application");
//        stage.setScene(scene);
//
//        stage.show();







        myscene.setFill(Color.rgb(252, 239, 181));
        stage.setScene(myscene);
        stage.setTitle("Tic Tac Toe by Razib Sarker");
        stage.getIcons().add(mainIcon);
        stage.setResizable(false);
        stage.show();


//        stage.setTitle("HBox Experiment 1");
//
//        Button button = new Button("My Button");
//
//
//
//        stage.show();




    }



    private Parent createContent(){


        root.setPrefSize(600,600);

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){

                Tile tile = new Tile();

                tile.setTranslateX(j*200);
                tile.setTranslateY(i*200);

                root.getChildren().add(tile);

                board[j][i] = tile;
            }
        }

        //horizontal

        for(int y =0; y < 3; y++){

            combos.add(new Combo(board[0][y],board[1][y],board[2][y]));

        }

        // vertical


        for(int x=0; x<3; x++){

            combos.add(new Combo(board[x][0],board[x][1],board[x][2]));
        }

        //diagonals
        combos.add(new Combo(board[0][0],board[1][1],board[2][2]));
        combos.add(new Combo(board[0][2],board[1][1],board[2][0]));




        return root;
    }

    private void checkState(String player){

        for(Combo combo : combos){
            if(combo.isComplete()){
                playable = false;
                playWinAnimation(combo, player);
                break;
            }
        }
    }

    private void playWinAnimation(Combo combo, String player){

        Line line = new Line();

        line.setStartX(combo.tiles[0].getTranslateX()+100);
        line.setStartY(combo.tiles[0].getTranslateY()+100);



        line.setEndX(combo.tiles[2].getTranslateX()+100);
        line.setEndY(combo.tiles[2].getTranslateY() + 100);


        root.getChildren().add(line);


        System.out.println("Player "+ player +" wins!");

        drawLine(line,player);

    }

    private void drawLine(Line line, String player){

        Text endText = new Text();
//        Button restartButton = new Button("Restart");



        if(player.equals("X")){
            line.setStroke(Color.rgb(28, 108, 237));
            endText.setFill(Color.rgb(28, 108, 237));

        } else {

            line.setStroke(Color.rgb(252, 38, 5));
            endText.setFill(Color.rgb(252, 38, 5));
        }
        endText.setText("Player "+ player +" wins!");
        endText.setFont(Font.font(24));


//        restartButton.setTranslateX(200);
//        restartButton.setTranslateY(150);
//        restartButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                System.out.println("restart Pressed");
//            }
//        });




        finalDialog(endText);





    }


    private void finalDialog(Text endText){



        endGroup.getChildren().addAll(endText);

        endText.setLayoutX(225);
        endText.setLayoutY(50);





        endScene.setFill(Color.rgb(252, 239, 181));
        Stage finalStage = new Stage();
        finalStage.setScene(endScene);
        finalStage.getIcons().add(mainIcon);
        finalStage.setResizable(false);
        finalStage.show();




        CustomButtonClass playAgainButton = new CustomButtonClass(new Text("Play Again"));
        CustomButtonClass quitButton = new CustomButtonClass(new Text("Quit"));
        playAgainButton.setLayoutX(75);
        playAgainButton.setTranslateY(100);

        quitButton.setLayoutX(350);
        quitButton.setLayoutY(100);

        playAgainButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(mouseEvent.getButton() == MouseButton.PRIMARY){


                    finalStage.close();
                    endGroup.getChildren().clear();
                    restart();

                }
            }
        });


        quitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(mouseEvent.getButton() == MouseButton.PRIMARY){
                    System.exit(0);
                }
            }
        });

        endGroup.getChildren().addAll(playAgainButton,quitButton);














    }

    private void restart(){
        playable = true;
        turnX = true;
        combos.clear();
        root.getChildren().clear();
        root.getScene().setRoot(createContent());

    }


    class CustomButtonClass extends StackPane{


        private Rectangle rectangle = new Rectangle(175,45);

        public CustomButtonClass(Text label){


            rectangle.setFill(null);
            rectangle.setStroke(Color.GRAY);
            rectangle.setStrokeWidth(5);

            setAlignment(Pos.CENTER);

            label.setFont(Font.font(30));
            getChildren().addAll(rectangle,label);




            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    rectangle.setFill(Color.ORANGE);
                    label.setFill(Color.WHITE);
                    setCursor(Cursor.HAND);

                }
            });

                setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    rectangle.setFill(null);
                    label.setFill(Color.BLACK);
                    setCursor(Cursor.DEFAULT);

                }
            });


        }
    }





    private class Combo{

        private Tile[] tiles;


        public Combo(Tile... tiles){

            this.tiles = tiles;

        }

        public boolean isComplete(){

            if(tiles[0].getText().isEmpty())
                return false;

            return (tiles[0].getText().equals(tiles[1].getText())) && (tiles[0].getText().equals(tiles[2].getText()));


        }

    }

    private class Tile extends StackPane {

        private Text text;
        private boolean tilePlayed = false;



        public Tile(){

            text= new Text();
            Rectangle border = new Rectangle(200,200);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            setAlignment(Pos.CENTER);
            getChildren().addAll(border,text);
            text.setFont(Font.font(72));

            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    myscene.setCursor(Cursor.HAND);

                }
            });


            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    myscene.setCursor(Cursor.DEFAULT);

                }
            });

            setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    border.setStrokeWidth(3);
                }
            });

            setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    border.setStrokeWidth(1);
                }
            });

            setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    if(!playable)
                        return;


                    if((mouseEvent.getButton() == MouseButton.PRIMARY) && (!tilePlayed)){

                        if(turnX){
                            drawX();
                            tilePlayed = true;
                            turnX = false;
                            checkState("X");

                        } else{

                            drawO();
                            tilePlayed = true;
                            turnX = true;
                            checkState("O");

                        }

//                    if((mouseEvent.getButton() == MouseButton.PRIMARY) && !tilePlayed){
//                        if(turnX){
//                            drawX();
//                            tilePlayed = true;
//                            turnX = false;
//                            checkState();
//                        } else{
//
//                            System.out.println("It's player O's turn");
//                        }
//
//                    } else if((mouseEvent.getButton() == MouseButton.SECONDARY) && !tilePlayed){
//                        if(!turnX){
//                            drawO();
//                            tilePlayed = true;
//                            turnX = true;
//                            checkState();
//                        }else{
//
//                            System.out.println("It's player X's turn");
//                        }

                    }
                }
            });
        }

        private void drawX(){

            text.setText("X");
            text.setFill(Color.rgb(28, 108, 237));


        }

        private void drawO(){

            text.setText("O");
            text.setFill(Color.rgb(252, 38, 5));

        }

        public String getText(){

            return text.getText();
        }
    }



    public static void main(String[] args) {

        launch(args);
    }
}

