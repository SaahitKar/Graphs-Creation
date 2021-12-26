import java.util.*;
import java.io.*;

public class LL {
    public static int v;
    public static int c = 0, t = 0;
    public static ArrayList<Node>[] arr;
    public static LinkedList<Integer> list;
    public LL(ArrayList<Node> nodes) {
        
    }
    public LL() {
         
    }
    public void initalizeAdj() {
        arr = new ArrayList[v];
        for(int i = 0; i < v; i++) {
            arr[i] = new ArrayList<Node>();
        }
    }
    public static void changeInttoNode(ArrayList<Integer> n, ArrayList<Node> l) {
        ArrayList<Node> intToNode = new ArrayList<>();
        for(int i = 0; i < n.size(); i++) {
            intToNode.add(l.get(n.get(i)));
        }
    }
    public static LL changeLLtoL(LinkedList<Integer> n) {
        LL c = new LL();
        c.list = n;
        return c;
    }
    public static LL llCreate(ArrayList<Integer> n) {
        LL ll = new LL();
        LinkedList<Integer> linked = new LinkedList<>();
        for(int i = 0; i < n.size(); i++) {
            linked.add(n.get(i));
        }
        ll.list = linked;
        return ll;
    }
    //
    public static <T> List<T> getInstance(ArrayList<T> list) {
        List<T> linkedList = new LinkedList<>();
        for (T e: list) {
            linkedList.add(e);
        }
        return linkedList;
    }
    //
    public static void print(LinkedList<Integer> linked) {
        for(int i = 0; i < linked.size(); i++) {
            System.out.print(linked.get(i) + " ");
        }
        System.out.println();
    }
}
