import javax.swing.JFrame;

class Main{
    public static void main(String[] args) {

        //Frame Creation
        JFrame obj = new JFrame();
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Breakout Ball");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // gameplay object has become panel now
        GamePlay gamePlay = new GamePlay();
        obj.add(gamePlay);
    }
}