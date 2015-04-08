import java.util.*;

public class WordNet {
	private HashMap<Integer, String> synset;
	private HashMap<String, Node> nounLU;
	private int numSynsets;
	private Digraph hypernym;
	private SAP sap;
	
	// Nodes for nouns
	private class Node {
		private int id;
		private Node next;
		
		public Node(int id) {
			this.id = id;
		}
	}

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) {	   
	   readSynsets(synsets);
	   readHypernyms(hypernyms);
   }
   
   // reads synsets file and creates hashmap of nouns
   private void readSynsets(String synsets) {
	   
	   synset = new HashMap<Integer, String>();
	   nounLU = new HashMap<String, Node>();
	   In in = new In(synsets);
	   
	   while (!in.isEmpty()) {
		   String l = in.readLine();		   
		   String[] fields = l.split(",");
		   String[] nouns = fields[1].split(" ");
		   
		   for (int i = 0; i < nouns.length; i++) {
			   
			   if (nounLU.containsKey(nouns[i])) {
				   
				   Node temp = nounLU.get(nouns[i]);
				   Node first = temp;
				   
				   while (temp.next != null) {
					   temp = temp.next;
				   }
				   
				   temp.next = new Node(Integer.parseInt(fields[0]));				   
				   nounLU.put(nouns[i], first);
				   
			   } else {
				   nounLU.put(nouns[i], new Node(Integer.parseInt(fields[0])));
			   }
		   }
		   synset.put(Integer.parseInt(fields[0]), fields[1]);
		   numSynsets++;
	   }
   }
   
   // reads hypernyms file and creates digraph
   private void readHypernyms(String hypernyms) {
	   
	   hypernym = new Digraph(numSynsets);
	   In in = new In(hypernyms);
	   int roots = 0;
	   boolean[] rootCheck = new boolean[numSynsets];
	   
	   while (!in.isEmpty()) {
		   String l = in.readLine();
		   String[] fields = l.split(",");		   
		   int numFields = fields.length;
		   
		   // if only 1 field in string, possible root
		   if (numFields == 1) roots++;
		   rootCheck[Integer.parseInt(fields[0])] = true;
		   
		   for (int i = 1; i < numFields; i++) {
			   hypernym.addEdge(Integer.parseInt(fields[0]), Integer.parseInt(fields[i]));
		   }
	   }
	   
	   // test for only 1 possible root
	   for (int i = 0; i < rootCheck.length; i++) {
		   if (!rootCheck[i]) roots++;
	   }	   
	   if (roots != 1) throw new java.lang.IllegalArgumentException();
	   
	   // test for DAG
	   KosarajuSharirSCC testDag = new KosarajuSharirSCC(hypernym);
       if (testDag.count() > numSynsets)
           throw new java.lang.IllegalArgumentException();
       
	   sap = new SAP(hypernym);
   }

   // returns all WordNet nouns
   public Iterable<String> nouns() {
	   return nounLU.keySet();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
	   if (word != null) return nounLU.containsKey(word);
	   else throw new java.lang.NullPointerException();
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
	   if (!isNoun(nounA) || !isNoun(nounB))
           throw new java.lang.IllegalArgumentException();

       Iterable<Integer> it0 = getIter(nounA);
       Iterable<Integer> it1 = getIter(nounB);

       return sap.length(it0, it1);
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB){
	   if (!isNoun(nounA) || !isNoun(nounB))
           throw new java.lang.IllegalArgumentException();

       Iterable<Integer> it0 = getIter(nounA);
       Iterable<Integer> it1 = getIter(nounB);

       return synset.get(sap.ancestor(it0, it1));
   }
   
   private Iterable<Integer> getIter(final String noun) {
	   return new Iterable<Integer>() {
		   public Iterator<Integer> iterator() {
			   return new Iterator<Integer>() {
				   Node curr = nounLU.get(noun);
				   
				   public boolean hasNext() {
					   return curr != null;
				   }
				   
				   public Integer next() {
					   Integer val = curr.id;
					   curr = curr.next;
					   return val;
				   }
				   
				   public void remove() {
				   }				   
			   };
		   }
	   };
   }

   // do unit testing of this class
   public static void main(String[] args) {
	   WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
	   StdOut.println("Number of synsets: " + wordnet.numSynsets);
	   assert !wordnet.isNoun("zzyzyzz");
	   assert wordnet.isNoun("subpart");
	   StdOut.println(wordnet.sap("worm", "bird"));
   }
}