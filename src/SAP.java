import java.util.Iterator;

public class SAP {
	private final Digraph sap;
	private int ancestor;
	private int minDist;

   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G) {
	   sap = new Digraph(G);
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w) {
	   bfdp(v, w);
	   return minDist;
   }   

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w) {
	   bfdp(v, w);
	   return ancestor;
   }
   
   private void checkBounds(int v, int w) {
       if (v < 0 || v > sap.V() - 1)
           throw new java.lang.IndexOutOfBoundsException();

       if (w < 0 || w > sap.V() - 1)
           throw new java.lang.IndexOutOfBoundsException();
   }
   
   private void checkBounds(Iterable<Integer> v, Iterable<Integer> w) {
       Iterator<Integer> iter = v.iterator();
       while (iter.hasNext()) {
           int tmp = iter.next();
           if (tmp < 0 || tmp > sap.V() - 1)
               throw new java.lang.IndexOutOfBoundsException();
       }

       iter = w.iterator();
       while (iter.hasNext()) {
           int tmp = iter.next();
           if (tmp < 0 || tmp > sap.V() - 1)
               throw new java.lang.IndexOutOfBoundsException();
       }
   }
   
   private void bfdp(int v, int w) {
	   checkBounds(v,w);
	   
	   BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(sap, v);
	   BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(sap, w);
	   minDist = -1;
	   ancestor = -1;
	   for (int i = 0; i < sap.V(); i++) {
		   if (bfdpV.hasPathTo(i) && bfdpW.hasPathTo(i)) {
			   int newMinDist = bfdpV.distTo(i) + bfdpW.distTo(i);
			   if (minDist < 0 || newMinDist < minDist) {
				   minDist = newMinDist;
				   ancestor = i;
			   }
		   }		   
	   }
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w) {	   
	   bfdpIter(v, w);
	   return minDist;
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
	   bfdpIter(v, w);
	   return ancestor;
   }
   
   private void bfdpIter(Iterable<Integer> v, Iterable<Integer> w) {
	   checkBounds(v,w);
	   
	   BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(sap, v);
	   BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(sap, w);
	   minDist = -1;
	   ancestor = -1;
	   for (int i = 0; i < sap.V(); i++) {
		   if (bfdpV.hasPathTo(i) && bfdpW.hasPathTo(i)) {
			   int newMinDist = bfdpV.distTo(i) + bfdpW.distTo(i);
			   if (minDist < 0 || newMinDist < minDist) {
				   minDist = newMinDist;
				   ancestor = i;
			   }
		   }		   
	   }
   }

   // do unit testing of this class
   public static void main(String[] args) {
	    In in = new In(args[0]);
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    while (!StdIn.isEmpty()) {
	        int v = StdIn.readInt();
	        int w = StdIn.readInt();
	        int length   = sap.length(v, w);
	        int ancestor = sap.ancestor(v, w);
	        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	    }
   }
}