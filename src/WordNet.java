import java.util.*;

public class WordNet {
	private HashMap<Integer, String> synset;
	private HashMap<String, Node> nounLU;
	private int numSynsets;
	private Digraph hypernym;
	
	private class Node {
		private int value;
		private Node next;
		
		public Node(int value) {
			this.value = value;
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
			   if (synset.containsKey(nouns[i])) {
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
	   while (!in.isEmpty()) {
		   String l = in.readLine();
		   String[] fields = l.split(",");		   
		   int numFields = fields.length;
		   for (int i = 1; i < numFields; i++) {
			   hypernym.addEdge(Integer.parseInt(fields[0]), Integer.parseInt(fields[i]));
		   }
	   }
   }

   // returns all WordNet nouns
   public Iterable<String> nouns() {
	   return synset.values();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
	   if (word != null) return synset.containsValue(word);
	   else throw new java.lang.NullPointerException();
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
	   return 0;
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB){
	   int idA = synset
   }

   // do unit testing of this class
   public static void main(String[] args) {
	   WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");	   
	   StdOut.printf(wordnet.hypernym.toString());
	   StdOut.printf(wordnet.synset.toString());
	   for (String noun: wordnet.nouns()) {
		   StdOut.println(noun);
	   }
	   assert !wordnet.isNoun("zzyzyzz");
	   assert wordnet.isNoun("subpart");
   }
}