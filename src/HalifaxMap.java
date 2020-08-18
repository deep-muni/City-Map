import java.util.ArrayList;

public class HalifaxMap {

    // Define variables
    private final int MAX = Integer.MAX_VALUE;
    private ArrayList<Co_Ordinates> vertices = new ArrayList<>();
    private ArrayList<Edges> edge = new ArrayList<>();
    private int weight[][];

    // Function to store the new co-ordinates in an ArrayList called vertices
    public boolean newIntersection(int x, int y) {
        try {

            // Loop through vertices to check if the co-ordinates already exist
            for (Co_Ordinates co : vertices) {
                             if (x == co.x && y == co.y) {
                        return false;
                }
            }

            // Store the co-ordinates in vertices by creating an object and calling the constructor of class Co_Ordinates
            Co_Ordinates c = new Co_Ordinates(x, y);
            vertices.add(c);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Function to create an edge between two intersection which is stored in ArrayList called edge
    public boolean defineRoad(int x1, int y1, int x2, int y2) {
        try {
            // To get the index values of the co-ordinates stored in vertices
            int intersection_1 = getIndex(x1, y1);
            int intersection_2 = getIndex(x2, y2);

            // If either of the co-ordinate does not exist then it will return false
            if(intersection_1 == -1 || intersection_2 == -1){
                return false;
            }

            // Condition to check if the edge already exist between two co-ordinates
            for (Edges i : edge) {
                if (intersection_1 == i.intersection_1 && intersection_2 == i.intersection_2) {
                    return false;
                }
            }

            // Store the edge between both the co-ordinates. Since it is an undirected graph, stored the link from A->B and B->A

            Edges e = new Edges(intersection_1, intersection_2, distance(x1, y1, x2, y2));
            edge.add(e);

            e = new Edges(intersection_2, intersection_1, distance(x1, y1, x2, y2));
            edge.add(e);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Function to find the shortest path between two given intersections
    public void navigate(int x1, int y1, int x2, int y2) {

        try{
            int source = getIndex(x1,y1);
            int destination = getIndex(x2,y2);

            // Condition to check if the source and destination are same
            if(source == destination){
                if(source == -1){
                    System.out.println("No Link");
                }else{
                    System.out.println(x1 + "\t" + y1);
                }
                return;
            }

            /* Condition to check if source or destination does not exist, there are
               if the source or destination is connected with the rest of the graph*/
            if(source == -1 || destination == -1 || edge.size() == 0 || !isConnected(source) || !isConnected(destination)){
                System.out.println("No Link");
                return;
            }

            /* Creation of array to store the distances between two intersection, the visited intersection
               and the parent node of the current intersection */

            int distanceList[] = new int[vertices.size()];
            boolean visited[] = new boolean[vertices.size()];
            int previous[] = new int[vertices.size()];

            // Initializing the array distance with value MAX and array visited with false
            for (int i = 0; i < vertices.size(); i++) {
                distanceList[i] = MAX;
                visited[i] = false;
            }

            // Function call to create the adjacency matrix
            createMatrix();

            /* Initialize the distance from source to source as 0
               and parent of the source as -1 */
            distanceList[source] = 0;
            previous[source] = -1;

            for (int i = 1; i < vertices.size(); i++) {
                // Function call to get the minimum distance between current intersection and its adjacent intersections
                int adjacent = minimumDistance(distanceList, visited);
                visited[adjacent] = true;

                for (int j = 0; j < vertices.size(); j++) {
                    /* Condition to check if there is link between the intersection and
                       if the distance and weight from adjacent is less than the other intersections */
                    if (weight[adjacent][j] != -1 && ((distanceList[adjacent] + weight[adjacent][j]) < distanceList[j])) {
                        previous[j] = adjacent;
                        distanceList[j] = distanceList[adjacent] + weight[adjacent][j];
                    }
                }
            }

            // Function call to print the shortest path from source to destination
            printPath(destination, previous);

        }catch (Exception e){
            System.out.println("No Link");
        }
    }

    // Function to get the intersection which has the minimum distance from current intersection
    private int minimumDistance(int distanceList[], boolean visited[]){
        int adjacent = -1;
        int distance = MAX;

        for (int i = 0; i < vertices.size(); i++) {
            /* Condition to check if the node has been visited and
               array distance is less than the local variable distance */
            if (!visited[i] && distanceList[i] < distance) {
                adjacent = i;
                distance = distanceList[i];
            }
        }
        return adjacent;
    }

    // Recursive function to print the co-ordinates from the source to the destination
    private void printPath(int currentIntersection, int previous[]) {
        if (currentIntersection != -1) {    // End condition for the recursive call
            printPath(previous[currentIntersection], previous);
            System.out.println(vertices.get(currentIntersection).x + "\t" + vertices.get(currentIntersection).y);    // Output will show the path from source to destination
        }
    }

    // Function to create the adjacency matrix
    private void createMatrix() {
        weight = new int[vertices.size()][vertices.size()];

        // Loop to initialize the values to -1
        for(int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                weight[i][j] = -1;
            }
        }

        // Loop to store the distance in the matrix if there is connection between two intersections
        for (Edges anEdge : edge) {
            weight[anEdge.intersection_1][anEdge.intersection_2] = anEdge.weight;
            weight[anEdge.intersection_2][anEdge.intersection_1] = anEdge.weight;
        }
    }

    // Function to check if an intersection is connected to the rest of the graph
    private boolean isConnected(int intersection) {
        for(Edges e : edge){
            if(intersection == e.intersection_1 || intersection == e.intersection_2){
                // Function will return true if the co-ordinate is connected
                return true;
            }
        }
        // Function will return false if the co-ordinate is not connected
        return false;
    }

    // Function to return the distance between two co-ordinates
    private int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.round(Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)));
    }

    // Function to return the index of the vertex in the ArrayList
    private int getIndex(int x, int y) {
        for (int i = 0; i < vertices.size(); i++) {
            // Condition where both x and y equals the co-ordinate to get the index
            if (x == vertices.get(i).x && y == vertices.get(i).y) {
                return i;
            }
        }
        // If the co-ordinate is not found in the ArrayList, the function will return -1
        return -1;
    }

    // Only for testing, to print the vertices and the edges
    public void print(){
        System.out.println("\nIntersections");
        for(Co_Ordinates c : vertices){
            System.out.println(c.x + " " + c.y);
        }

        System.out.println("\nRoads & Distance");
        for(Edges e : edge){
            System.out.println(e.intersection_1 + " " + e.intersection_2 + " " + e.weight);
        }
    }
}