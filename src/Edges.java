public class Edges {

    int intersection_1, intersection_2, weight;

    // Constructor to store the link between two intersection along with the distance between them
    public Edges(int intersection_1, int intersection_2, int weight) {
        this.intersection_1 = intersection_1;
        this.intersection_2 = intersection_2;
        this.weight = weight;
    }
}
