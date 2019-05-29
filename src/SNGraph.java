import java.awt.*;

class SNGraph {
    private ADS2LinkedList adjList;
    private int numOfEdges;
    // assumes there shall be 100 nodes in the graph
    private int numOfNodes = 100;
    SNGraph(int numOfEdges){
        this.numOfEdges = numOfEdges;
        this.adjList = new ADS2LinkedList();

        for (int i=0; i < numOfEdges; i++){
            adjList.add(new ADS2LinkedList());
        }
    }
    class Edge {
        private int src;
        private int dest;
        private double weight;

        public Edge(int src, int dest, double weight){
            this.src = src;
            this.weight = weight;
            this.dest = dest;
        }

        public int getDest() {
            return dest;
        }

        public int getSrc() {
            return src;
        }

        public double getWeight() {
            return weight;
        }
        
        public void setWeight(double newWeight) {
            weight = newWeight;
        }
    }

    public void addEdge(int src, int dest, double weight){
        Edge edge = new Edge(src, dest, weight);
        ADS2LinkedList tempEdge = (ADS2LinkedList) adjList.get(src).getData();
        tempEdge.add(edge);
    }

    public ADS2LinkedList getList(int i){
        return (ADS2LinkedList) this.adjList.get(i-1).getData();
    }

    public void printGraph(){
        Edge tempEdge;
        for (int i = 1; i <numOfEdges+1 ; i++) {
            ADS2LinkedList list = this.getList(i);
            for (int j = 0; j <list.getCount() ; j++) {
                tempEdge = (Edge) list.get(j).getData();
                System.out.println("vertex-" + tempEdge.getSrc() + " is connected to " +
                        tempEdge.getDest() + " with weight " +  tempEdge.getWeight() + "no." + i);
            }
        }
        dijkstra(4);
    }

    public double findMinDist(double distFromSrc[], Boolean visted[])
    {
        // init min values
        double min = Integer.MAX_VALUE, minIndex=-1;

        for (int v = 0; v < numOfNodes; v++)
            if (visted[v] == false && distFromSrc[v] <= min)
            {
                min = distFromSrc[v];
                minIndex = v;
            }

        return minIndex;
    }


    public double[] dijkstra(int src)
    {
        // output array
        double distFromSrc[] = new double[numOfNodes];

        Boolean visted[] = new Boolean[numOfNodes];

        // init initial variables
        for (int i = 0; i < numOfNodes; i++)
        {
            distFromSrc[i] = Integer.MAX_VALUE;
            visted[i] = false;
        }

        // distance to source is zero
        distFromSrc[src] = 0;
        for (int count = 0; count < numOfNodes-1; count++)
        {
            int currentNode = (int) findMinDist(distFromSrc, visted);
            // mark visited as visited
            visted[currentNode] = true;
            // get node
            ADS2LinkedList tempVertex = this.getList(currentNode + 2);
            // traverse through neighbours of the node
            for (int v = 0; v < tempVertex.getCount(); v++) {
                // updates distFormSrc value if it finds a node that isn't visited, a path from src to the node exits
                // and the path from src to the node is < the current weight
                Edge tempEdge = (Edge) tempVertex.get(v).getData();
                if (!visted[tempEdge.getDest()-1] && tempEdge.getWeight() != 0 &&
                        distFromSrc[currentNode] != Integer.MAX_VALUE &&
                        distFromSrc[currentNode] + tempEdge.getWeight() < distFromSrc[tempEdge.getDest()-1]) {
                    distFromSrc[tempEdge.getDest() - 1] = distFromSrc[currentNode] + tempEdge.getWeight();
                }
            }
        }
        return distFromSrc;
    }

}


class ADS2LinkedList{
    private Node head;
    private int count;

    public ADS2LinkedList() {

    }

    class Node{
        private Node next;
        private Object data;

        public Object getData() {
            return data;
        }

        public void setData(String value) {
            data = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node nextNode) {
            next = nextNode;
        }

        public Node(Object value) {
            next = null;
            data = value;
        }
    }

    public void add(Object data){
        if(head == null){
            head = new Node(data);
        }
        else{
            Node current = head;
            while(current.getNext() != null){
                current = current.getNext();
            }


            current.setNext(new Node(data));
        }


        count++;
    }

    public void addFront(Object data){
        if(head == null){
            head = new Node(data);
        }
        else{
            Node current = head;
            Node newHead = new Node(data);
            newHead.setNext(current);
            head = newHead;
        }

        count++;
    }

    public Node get(int index){
        // if index out of range, return null
        if (index < 0){
            return null;
        }
        Node current = null;
        if(head != null){
            current = head;
            for(int i = 0; i < index; i++){
                if(current.getNext() == null){
                    return null;
                }

                current = current.getNext();
            }
            return current;
        }
        return current;
    }

    public int getCount() {
        return count;
    }


}