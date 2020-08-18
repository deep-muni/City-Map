import java.util.Scanner;

public class mainClass {

    public static void main(String args[]){

        String userCommand = "";
        int x1,x2,y1,y2;
        Scanner userInput = new Scanner( System.in );
        boolean booleanOutcome;

        System.out.println("\n################");
        System.out.println("### City Map ###");
        System.out.println("################");
        
        HalifaxMap hm = new HalifaxMap();

        do {
            
            System.out.println("\nnew - New Intersection\troad - Road creation\tpath - Find Path\tprint - Print\tquit - Quit");
            System.out.println("\nEnter your choice:");

            userCommand = userInput.next();


            if (userCommand.equalsIgnoreCase("new")) {
                System.out.println("\nEnter co-ordinates:");
                x1 = userInput.nextInt();
                y1 = userInput.nextInt();
                booleanOutcome = hm.newIntersection(x1, y1);
                System.out.println("New intersection ("+x1+","+ y1+") outcome " + booleanOutcome );
            } else if (userCommand.equalsIgnoreCase("road")) {
                System.out.println("\nEnter co-ordinates of both intersections:");
                x1 = userInput.nextInt();
                y1 = userInput.nextInt();
                x2 = userInput.nextInt();
                y2 = userInput.nextInt();
                booleanOutcome = hm.defineRoad(x1,y1,x2,y2);
                System.out.println("Road creation ("+x1+","+ y1+") & ("+x2+","+y2+") outcome " + booleanOutcome );
            } else if (userCommand.equalsIgnoreCase("path")) {
                System.out.println("\nEnter co-ordinates of source and destination:");
                x1 = userInput.nextInt();
                y1 = userInput.nextInt();
                x2 = userInput.nextInt();
                y2 = userInput.nextInt();
                System.out.println("\nPath between ("+x1+","+ y1+") & ("+x2+" "+y2+"):");
                hm.navigate(x1,y1,x2,y2);
            } else if (userCommand.equalsIgnoreCase("print")) {
                hm.print();
            } else if (userCommand.equalsIgnoreCase("quit")) {
                System.out.println ("\nBye Bye");
            } else {
                System.out.println ("\nBad command: " + userCommand);
            }
        } while (!userCommand.equalsIgnoreCase("quit"));

        userInput.close();
    }
}
