import java.util.*;
import java.io.*;

public class Stacks {
    //Create a adjacency matrix
    //Create linked list for each path
    //Add onto stack
    private int v;
    public ArrayList<Integer>[] arr;
    public static LinkedList<LL> llList;
    public static LinkedList<LinkedList<Integer>> lll;
    public Stacks() {
        llList = new LinkedList<LL>();
        lll = new LinkedList<LinkedList<Integer>>();
    }
    public Stacks(ArrayList<Integer>[] adj, int vertice) {
        arr = adj;
        v = vertice;
    }
    
    public static void pushPath(LL diffPath) {
        llList.add(diffPath);
    }
    
    public static void pushP(LinkedList<Integer> diffPath) {
        lll.add(diffPath);
    }
    
    public static LL popPath() {
       if (llList.isEmpty()) {
			return null;
		}
		LL top = llList.get(llList.size() - 1);
		llList.remove(llList.size() - 1);
		return top;
    }
    public static LL peekPath() {
        if (llList.isEmpty()) {
			return null;
		}
		LL top = llList.get(llList.size() - 1);
		return top;
    }
    public static int sizeStack() {
        return llList.size();
    }
}
