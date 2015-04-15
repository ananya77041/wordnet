# wordnet

WordNet is a semantic lexicon for the English language that is used extensively by computational linguists and cognitive scientists; for example, it was a key component in IBM's Watson. WordNet groups words into sets of synonyms called synsets and describes semantic relationships between them. One such relationship is the is-a relationship, which connects a hyponym (more specific synset) to a hypernym (more general synset). For example, a plant organ is a hypernym of carrot and plant organ is a hypernym of plant root.

In the wordnet digraph, each vertex v is an integer that represents a synset, and each directed edge v→w represents that w is a hypernym of v. The wordnet digraph is a rooted DAG: it is acyclic and has one vertex—the root—that is an ancestor of every other vertex. However, it is not necessarily a tree because a synset can have more than one hypernym.

An ancestral path between two vertices v and w in a digraph is a directed path from v to a common ancestor x, together with a directed path from w to the same ancestor x. A shortest ancestral path is an ancestral path of minimum total length.

Semantic relatedness refers to the degree to which two concepts are related. Measuring semantic relatedness is a challenging problem. For example, most of us agree that George Bush and John Kennedy (two U.S. presidents) are more related than are George Bush and chimpanzee (two primates). However, not most of us agree that George Bush and Eric Arthur Blair are related concepts. But if one is aware that George Bush and Eric Arthur Blair (aka George Orwell) are both communicators, then it becomes clear that the two concepts might be related.
