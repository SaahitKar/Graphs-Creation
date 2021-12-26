public class Node {
    String city;
    Node next;
    int order;
    //Initalized Constructor
    Node(String s1, int o) {
        city = s1;
        order = o;
    }
    public void print() {
        System.out.println(order);
    }
}
