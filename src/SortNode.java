
public class SortNode implements Comparable<SortNode> {
	int fileIdx = -1;
	int cnt;
	int line;
	
	@Override
	public int compareTo(SortNode o) {
		return cnt - o.cnt;
	}
}
