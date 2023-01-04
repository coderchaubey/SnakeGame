import javax.swing.*;

//extending Jframe class so that when we instantiate our snakegame class, jframe also gets into action by giving us
//its inbuilt frame structure
public class snakeGame extends JFrame {
    private GameBoard board;
    snakeGame(){
        //creating an instance of gameboard class here
        board=new GameBoard();

        //we added our gameboard into frame like we added textarea
        add(board);

        //this method makes the present frame according to the size
        //of the game board
        setResizable(false);
        pack(); //this means it gets adjusted according to the size of the panel

//        //dont have to specify as snakegame in itself is working as a frame now
//        setBounds(100,100,800,500);

        setVisible(true);

    }

    public static void main(String[] args) {
        JFrame snakeGame=new snakeGame();
    }
}
