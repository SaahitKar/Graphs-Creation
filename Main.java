//Saahit Karumuri
//SE 3345.501
//Project #3: Graphs

import java.util.*;
import java.io.*;

public class Main
{
    public static ArrayList<Integer>[] arr;
    public static int vertex = 0;
    public static int pathSize = 0;
    public static Stacks cak = new Stacks();
    public static ArrayList<ArrayList<Integer>> arrarr = new ArrayList<>();
    public static LL lis = new LL();
    public static boolean[] isVisited;
    public static ArrayList<LinkedList<Integer>> li = new ArrayList<>();
	public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Enter the file name for flight path file >>> ");
	    String tmp = sc.nextLine();
	    System.out.println("Enter the file name for requested path file >>> ");
	    String temp = sc.nextLine();
		readIn(tmp, temp);
	}
	
	public static void readIn(String fileName, String fil2) {
        try {
            //Variable Initialization
            FileWriter myWriter = new FileWriter("output.txt");
	        File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            
            ArrayList<Double> c = new ArrayList<>();
            ArrayList<Integer> t = new ArrayList<>();
            ArrayList<Node> listNodes = new ArrayList<Node>();
            ArrayList<String> names = new ArrayList<String>();
            
            int numLines = Integer.parseInt(myReader.nextLine());
            isVisited = new boolean[numLines];
            //Read all lines in flight path file
            for(int i = 0; i < numLines; i++) {
                String line = myReader.nextLine();
                String[] temp = new String[4];
                StringTokenizer tokenizer = new StringTokenizer(line, "|");
                int e = 0;
                
                while (tokenizer.hasMoreTokens()) {
                    temp[e] = tokenizer.nextToken();
                    e++;
                }
                
                c.add(Double.parseDouble(temp[2]));
                t.add(Integer.parseInt(temp[3]));
                names.add(temp[0]);
                names.add(temp[1]);
            }
            //
	        File otherObj = new File(fil2);
            Scanner myR = new Scanner(otherObj);
                //Remove Duplicate Cities
                LinkedHashSet<String> hashSet = new LinkedHashSet<>(names);
                ArrayList<String> nSet = new ArrayList<>(hashSet);
                for(int i = 0; i < nSet.size(); i++) {
                    Node n = new Node(nSet.get(i), (i+1));
                    listNodes.add(n);
                }
                //
                ArrayList<Path> pathList = new ArrayList<>();
                myReader.close();
                vertex = listNodes.size();
                Scanner read = new Scanner(myObj);
                int temp = Integer.parseInt(read.nextLine());
                arr = initalizeAdj(nSet.size());
                for(int i = 0; i < numLines; i++) {
                    int p = 0;
                    String line = read.nextLine();
                    String[] len = new String [4];
                    StringTokenizer tokenizer = new StringTokenizer(line, "|");
                    //
                    len[p] = tokenizer.nextToken();
                    int begin = nSet.indexOf(len[p]);
                    len[p] = tokenizer.nextToken();
                    int end = nSet.indexOf(len[p]);
                    //Send to and create path object
                    Path addtoPaths = new Path(begin, end, c.get(i), t.get(i));
                    arr = addEdge(begin, end, arr);
                    pathList.add(addtoPaths);
                }
               //for loop right here
               int numNumLinesRepeat = Integer.parseInt(myR.nextLine());
               for(int rep = 0; rep < numNumLinesRepeat; rep++) {
               ArrayList<Integer> paths = new ArrayList<>();
                    int pi = 0;
                    String line = myR.nextLine();
                    String[] len = new String [3];
                    StringTokenizer tokenizer = new StringTokenizer(line, "|");
                    //
                    len[pi] = tokenizer.nextToken();
                    int begin = nSet.indexOf(len[pi]);
                    len[pi] = tokenizer.nextToken();
                    int end = nSet.indexOf(len[pi]);
                    String type = tokenizer.nextToken();
               paths.add(begin);
               Stacks cak = dfs(begin, end, paths);
               ArrayList<comBine> cb = new ArrayList<>();
               if(li.isEmpty() == true) {
                   myWriter.write("Flight Path Not Possible.");
               }
               for(int i = 0; i < li.size(); i++) {
                   LinkedList<Integer> listy = li.get(i);
                   int s = 0, e = 0;
                   double tempC = 0;
                   int tempT = 0;
                   for(int j = 0; j < listy.size() - 1; j++) {
                        boolean repeat = false;
                        cak.lll.add(listy);
                        s = listy.get(j);
                        e = listy.get(j+1);
                        for(int k = 0; k < pathList.size(); k++) {
                            if(s == pathList.get(k).start && e == pathList.get(k).end) {
                                tempC += pathList.get(k).cost;
                                tempT += pathList.get(k).time;
                            }
                            else if(s == pathList.get(k).end && e == pathList.get(k).start) {
                                tempC += pathList.get(k).cost;
                                tempT += pathList.get(k).time;
                            }
                        }
                        repeat = false;
                   }
                    comBine tempCombine = new comBine(listy, tempC, tempT);
                    cb.add(tempCombine);
               }
               LinkedList<Double> costOrderO = new LinkedList<>();
               LinkedList<Integer> timeOrderO = new LinkedList<>();
               LinkedList<Double> tempOrderO = new LinkedList<>();
               LinkedList<Integer> ttempOrderO = new LinkedList<>();
               for(int d = 0;  d < cb.size(); d++) {
                   costOrderO.addLast(cb.get(d).cost);
                   timeOrderO.addLast(cb.get(d).time);
                   tempOrderO.addLast(cb.get(d).cost);
                   ttempOrderO.addLast(cb.get(d).time);
               }
               Collections.sort(costOrderO);
               Collections.sort(timeOrderO);
               //cost ordered
               cak.lll.add(timeOrderO);
               if(type.equals("C") || type.equals("c")) {
               myWriter.write("Flight #" + (rep+1) + ": " + nSet.get(begin) + ", " + nSet.get(end) + " (Cost)\n");
                   for(int d = 0; d < costOrderO.size() && d < 3; d++) {
                       double tempC = costOrderO.get(d);
                       int index = tempOrderO.indexOf(tempC);
                       myWriter.write("Path " + (d+1) + ": ");
                       for(int p = 0; p < cb.get(index).arr.size(); p++) {
                            int tempV = cb.get(index).arr.get(p);
                            myWriter.write(nSet.get(tempV) + "->");
                       }
                       myWriter.write(" Time: " + cb.get(index).time + " Cost: " + cb.get(index).cost + "\n");
                   }
                   myWriter.write("\n");
               }
               //
               cak.lll.add(timeOrderO);
               //time ordered
               if(type.equals("T") || type.equals("t")) {
               myWriter.write("Flight #" + (rep+1) + ": " + nSet.get(begin) + ", " + nSet.get(end) + " (Time)\n");
                   for(int d = 0; d < timeOrderO.size() && d < 3; d++) {
                       int tempC = timeOrderO.get(d);
                       int index = ttempOrderO.indexOf(tempC);
                       myWriter.write("Path " + (d+1) + ": ");
                       for(int p = 0; p < cb.get(index).arr.size(); p++) {
                           int tempV = cb.get(index).arr.get(p);
                           myWriter.write(nSet.get(tempV) + "->");
                       }
                       myWriter.write(" Time: " + cb.get(index).time + " Cost: " + cb.get(index).cost + "\n");
                   }
                   myWriter.write("\n");
               }
               arrarr.clear();
               li.clear();
               paths.clear();
            }
            myWriter.close();
        } catch(IOException e) {
            System.out.println("An error occurred when trying to open the text file. Try again.");
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Flight Path Not Possible.");
        }  
	}
	
	public static ArrayList<Integer>[] initalizeAdj(int v) {
	    ArrayList<Integer>[] arr;
        arr = new ArrayList[v];
        for(int i = 0; i < v; i++) {
            arr[i] = new ArrayList<Integer>();
        }
        return arr;
    }
    public static ArrayList<Integer>[] addEdge(int u, int v, ArrayList<Integer>[] arr) {
        //u is the order from the beginning
        //D = 0 & A = 1
        arr[u].add(v);
        arr[v].add(u);
        return arr;
    }
    public static Stacks dfs(int s, int d, ArrayList<Integer> paths) {
        isVisited[s] = true;
        LL c = new LL();
        if(s == d) {
            int[] tempArr = new int[paths.size()];
            for(int i = 0; i < tempArr.length; i++) {
                tempArr[i] = paths.get(i);   
            }
            LinkedList<Integer> a = new LinkedList<>();
            for(int i = 0; i < tempArr.length; i++) {
                //System.out.print(tempArr[i]);
                a.add(tempArr[i]);
            }
            c = LL.changeLLtoL(a);
            li.add(a);
            lis.list.add(s);
            cak.pushPath(c);
            isVisited[s] = false;
            pathSize++;
            return cak;
        }
        Iterator<Integer> itr = arr[s].iterator();
        while(itr.hasNext()) {
            int temp = itr.next();
            if(!isVisited[temp]) {
                paths.add(temp);
                dfs(temp, d, paths);
                paths.remove(new Integer(temp));
            }
        }
        isVisited[s] = false;
        return cak;
    }
    public static void addCak(LL c) {
        cak.llList.add(c);
    }
}

class Path {
    int start,end, time;
    double cost;
    Path(int n1, int n2, double c, int t) {
        start = n1;
        end = n2;
        cost = c;
        time = t;
    }
}

class comBine {
    LinkedList<Integer> arr;
    double cost;
    int time;
    comBine(LinkedList<Integer> r, double c, int t) {
        arr = r;
        cost = c;
        time = t;
    }
    comBine() {    }
}
