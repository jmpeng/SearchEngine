
public class IndexTable {
	// Maximum files to search
	public static final int FILEMAX = 110;
	// Maximum different words contained in the files
	public static final int WORDMAX = 10000;

	private IndexNode[][] indexT = new IndexNode[FILEMAX][WORDMAX];

	public void addToTable(TSTNode tNode, int wordIdx) {
		if(tNode.fileIdx >= FILEMAX || wordIdx >= WORDMAX) {
			System.err.println("Invalid file index " + tNode.fileIdx + " or word index " + wordIdx);
			return;
		}
		IndexNode idxNode = indexT[tNode.fileIdx][wordIdx];
		// If the node doesn't exist, create it
		if(idxNode == null) {
			idxNode = new IndexNode();
			indexT[tNode.fileIdx][wordIdx] = idxNode;
			idxNode.line = tNode.line;
		}		

		idxNode.cnt++;
	}

	public SortNode[] getCntArray(int wordIdx) {
		SortNode[] cntArray = new SortNode[FILEMAX];
		IndexNode idxNode;

		for ( int i = 0; i < cntArray.length; i++ ) {
			cntArray[i] = new SortNode();
			
			idxNode = indexT[i][wordIdx];
			if(idxNode != null) {
				cntArray[i].cnt = idxNode.cnt;
				cntArray[i].fileIdx = i;
				cntArray[i].line = idxNode.line;
			}
			else {
				cntArray[i].cnt = 0;
				cntArray[i].fileIdx = i;
				cntArray[i].line = 0;
			}
		}

		return cntArray;
	}

}
