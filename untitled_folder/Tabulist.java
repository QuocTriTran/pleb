import java.util.*;

public class Tabulist {
	
	private int length;
	private ArrayList<Integer[]> tlist;
	private int[] list;
	private int[] copy_list;
	
	public Tabulist(int length) {
		this.length = length;
		tlist = new ArrayList<Integer[]>();
		list = new int[length];
		copy_list = new int[length];
		for(int i = 0; i < length; i++) {
			list[i] = i;
			copy_list[i] = i;
		}
	}
	
	public boolean duplicate() {
		boolean bool = true;
		for(int i = 0; i < length; i++) {
			if(list[i] != copy_list[i]) {
				bool = false;
				break;
			}
		}
		return bool;
	}
	
	public boolean exists(int a, int b) {
		boolean bool = false;
		int small;
		int big;
		
		if(a <= b) {
			small = a;
			big = b;
		} else {
			small = b;
			big = a;
		}
		for(Integer[] i: tlist) {
			if(i[0].intValue() == a && i[1].intValue() == b) {
				bool = true;
			}
		}
		return bool;
	}
	
	public void calcNeighbour() {
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < length; j++) {
				
			}
		}
	}
}
