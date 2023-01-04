import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameBoard extends JPanel implements ActionListener {
    int height=400; //pixels on both the axis
    int width=400;

    //total length of the snake on both the axis
    //will be equal to product of height and width of the gameboard
    //as snake height should not go beyond that.
    int x[]=new int[height*width];
    int y[]=new int[height*width];

    //initial size of the snake
    int dots;

    //size of the apples on both the axis
    int apple_x=100;
    int apple_y=100;

    int dot_size=10; //we have taken the size of dot that we will be attaching to our snake initial size everytime

    //declaring an Image class for our images
    Image apple;
    Image body;
    Image head;
    //now specifying direction for the movement
    //as our snakes head is on the left side so we will be setting leftD as true
    boolean leftDirection=true;
    boolean rightDirection=false;
    boolean upDirection=false;
    boolean downDirection=false;

    //here we will be creating an action event just like texteditor
    //using Timer class under java swing
    Timer timer; // whenever 't' will be t++; the action will occur
    int Delay=100; //this will go to the parameters of timer obj.
    // ** the unit of delay is mili-second ** so 400 ms=.4s

    int Rand_pos=39;

    boolean inGame=true; //this will tell us that we are in the game
    public GameBoard(){
        //adding the keylistener to our gameboard obj.
        addKeyListener(new keyadapter());
        setFocusable(true); // this so that it should listen to all of the key events
    //that is make focusable stay focused broda

        setPreferredSize(new Dimension(height,width));

        //setting background colour
        setBackground(Color.BLACK);

        //calling loadImages
        loadImages();

        //game initialized here
        initGame();

    }
    //making a function for declaring the size of our snake
    public void initGame(){
        dots=3;
        for(int i=0;i<dots;i++){

            //we have taken 50,50 as we want it to start after x=50,y=50 pixels as we did earlier in test editor
            //that is from which we want the snake to start running
            //this is the head position
            x[i]=150+i*dot_size;
            y[i]=150;
        }

        //we will be initializing our timer here
        timer=new Timer(Delay,this); //under parameters we are passing a delay int which helps us to map the timer with the actual time
        //starting the timer
        timer.start();
        //so we have specified the actionlistener to 'this' keyword as it will be working on this gameBoard object;
        //but here also we have to implement the action listener class;
    }
    private void loadImages() {
        ImageIcon imageIcon_apple = new ImageIcon("src/resources/apple.png"); //class for storing the path to our image icon object

        //after specifying the path of my apple to the ImageIcon
        //we need to get it to our image class
        apple = imageIcon_apple.getImage(); //getting the image through its source

        //again same thing with rest of our resources
        ImageIcon imageIcon_body=new ImageIcon("src/resources/dot.png");
        body=imageIcon_body.getImage();

        ImageIcon imageIcon_head=new ImageIcon("src/resources/head.png");
        head=imageIcon_head.getImage();
    }
    //overrinding a method of the Jpanel class that we extended above
    //so that we can re-define it
    @Override
    public void paintComponent(Graphics graphics){ //graphics class object helps us to draw the images

        super.paintComponent(graphics);
        if(inGame){
            //observer is our this that is gameboard panel
            graphics.drawImage(apple,apple_x,apple_y,this); //so apple x,apple y was created to store the position of the apple

            //also i want to draw the images of head and body also
            for(int i=0;i<dots;i++){
                if(i==0){
                    //that is if i when 0 will be head
                    //observer is again the game panel
                    graphics.drawImage(head,x[0],y[0],this);
                }
                else{
                    //now not 0 then it's a body
                    graphics.drawImage(body,x[i],y[i],this);
                }
            }
            //here we are calling a toolkit function which will help us to create the paint component again
            //as we will be moving so with every movement the component should get updated according to the timer
            Toolkit.getDefaultToolkit().sync(); //sync function basically syncs up our paintComponent with the timer of the movement
        }
        else {
            gameOver(graphics);
        }
    }
    //creating a function for movement
    private void move(){
        //not equals to zero because we are not changing our heads direction now
        for (int i=dots-1;i>0;i--){

            x[i]=x[i-1]; //for the last iteration it will be x[1]=x[1-1]
            y[i]=y[i-1]; //and y[1]=y[1-1]
        }
        //this is for head
        //according to the co-ordinates
        //we are increasing and decreasing our x and y
        //use your brain
        if(leftDirection){
            x[0]-=dot_size;
        }
        if(rightDirection){
            x[0]+=dot_size;
        }
        if (upDirection){
            y[0]-=dot_size;
        }
        if(downDirection){
            y[0]+=dot_size;
        }
    }
    //function for the apple movement
    private void locateApple(){
    int r=(int)(Math.random()*(Rand_pos)); //we will get value from 0 to 39
    apple_x=r*dot_size; //Here we are multiplying by 10

        r=(int)(Math.random()*(Rand_pos));
        apple_y=r*dot_size;
    }
    //now checking for collision
    private void checkApple(){
        if(x[0]==apple_x && y[0]==apple_y){ //if xof head equals to apple at x pos && y of head equals to apple at y
            dots++; //snake size will increase
            locateApple(); // ****relocating the apple**
        }
    }
    private void checkCollision(){
        //this was checking collision with the wall
        if(x[0]<0) {
            inGame=false; //we have collided
        }
        if(x[0]>=width) {
            inGame=false; //we have collided
        }
        if(y[0]<0) {
            inGame=false; //we have collided
        }
        if(y[0]>=height) {
            inGame=false; //we have collided
        }
        //here checking collision with its own body
        //so we need atleast more than 3 indexes to collide so
        for(int i=dots-1;i>=3;i--){
            if(x[0]==x[i] && y[0]==y[i]){
                inGame=false;
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //all this function are working if inGame is true
     if(inGame){
         //we will be calling our checkApple function at every interval of t
         //whenever t changes we will keep on checking our position of apple
         checkApple();

         //check collision should be tied to the timer so that with  every
         //increase in time we will be checking it
         checkCollision();
         // Whenever there is an action event
         //move function will be called
         move(); //after moving below we are repainting
     }
    repaint(); //this function helps us to trigger the paint component function again
    }
    //now we are creating a child class to create a Keylistener
    public class keyadapter extends KeyAdapter{
        //this keyadapter function is same as actionlistener except now
        //it will listen to our keys event
        @Override
        public void keyPressed(KeyEvent keyEvent){

            int key=keyEvent.getKeyCode(); //getting the key which is pressed and storing it
            //now checking which one is pressed and what to do with it
            if((key==keyEvent.VK_LEFT)&&(!rightDirection)){
                //now we need to make
                leftDirection=true;
                //rest false
                upDirection=false;
                downDirection=false;
            }
            if((key==keyEvent.VK_RIGHT)&&(!leftDirection)){
                //now we need to make
                rightDirection=true;
                //rest false
                upDirection=false;
                downDirection=false;
            }
            if((key==keyEvent.VK_UP)&&(!downDirection)){
                //now we need to make
                upDirection=true;
                //rest false
                leftDirection=false;
                rightDirection=false;
            }
            if((key==keyEvent.VK_DOWN)&&(!upDirection)){
                //now we need to make
                downDirection=true;
                //rest false
                leftDirection=false;
                rightDirection=false;
            }
        }


    }
    //creating a function for gameOver
    private void gameOver(Graphics graphics){
        String msg="Game Over Stupid";
        Font small=new Font("Helvetica", Font.BOLD,14);
        FontMetrics fontMetrics=getFontMetrics(small); //to get the width of the font
        graphics.setColor(Color.WHITE);
        graphics.setFont(small);
        graphics.drawString(msg,(width-fontMetrics.stringWidth(msg))/2,height/2);
    }

}
