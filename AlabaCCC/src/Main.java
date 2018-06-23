import java.util.*;

public class Main {

    //
    public static void main(String[] args) {
        Integer[] inFrame = {3, 3, 1, 1, 1, 1, 2, 2, 2, 2};
        Integer numberOfPlayers = 4;
        Integer[] playersFrame;
        Integer[] inPlayer = new Integer[numberOfPlayers];
        Integer[] mPlayers = new Integer[numberOfPlayers];
        Integer[] inBallPosition = new Integer[2];
        Integer[] mPlayer = new Integer[4];

        //calc

        String rawIn = "0 0 1 1 0 5 1 2 0 1 2 1 0 2 2 2 0 0";
        String[] inArr;

        //int n = Integer.parseInt(System.console().readLine());

       // String[] strArr = new String[rawIn.length()];
        int[] intArray = new int[rawIn.length()];
        int length = rawIn.split(" ").length;
        String[] strArr = new String[length];
        strArr = rawIn.split(" ");


        Integer[] arrInteger = new Integer[length];
        int index = 0;
        for (String s : strArr) {
             arrInteger[index++] = Integer.parseInt(s);
              System.out.print(s);
        }

////INPUT AS INTEGER ARRAY arrInteger Ok
        inBallPosition = Arrays.copyOfRange(arrInteger, 0, 2);
        playersFrame = Arrays.copyOfRange(arrInteger, 2, length);
/// now arrays ready, split players frame to each player

        List<Integer[]> playersList = new ArrayList<>();
        System.out.println("");
        for (int i = 0; i < playersFrame.length; ++i) {
            //k = i % numberOfPlayers;

            if (i % numberOfPlayers == 0) {
                mPlayer = Arrays.copyOfRange(playersFrame, i, i + 4);
                printArr(mPlayer);
                playersList.add(mPlayer);
            }
        }
        List<Player> cPlayers = new ArrayList<>();
        BallPosition ballPosition = new BallPosition();
        ballPosition.xPosition = inBallPosition[0];
        ballPosition.xPosition = inBallPosition[1];



        printArr(inBallPosition);
        for (Integer[] ply : playersList) {
            printArr(ply);
        }
    }
////







     public static void  printArr(Integer[] intA){
         System.out.println();
             for (Integer val : intA) {
                 System.out.print(val);
             }
      }

    public static  Double calculateDistanceToBall(Coodinates pos1, Coodinates pos2){
        Double distanceToBall = Math.sqrt((pos2.xPosition - pos1.xPosition )^2 +(pos2.yPosition - pos1.yPosition )^2 );
     return distanceToBall;
    };

}
