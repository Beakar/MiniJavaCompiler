class Main extends Lib {
	public void main() {
		LinkedList lst = new EmptyLinkedList();
		lst = lst.put("Pittsburgh", "Steelers");
		lst = lst.put("Miami", "Dolphins");
		lst = lst.put("Oakland", "Raiders");
		printList(lst);
		printStr("===============\n");
		lst = lst.remove("Miami").popRem(lst);
		lst = lst.remove("Buffalo").popRem(lst);
		printList(lst);
		printBool(lst.containsKey("Pittsburgh"));
		printStr("\n");
		printBool(lst.containsKey("Miami"));
		printStr("\n");
		printBool(lst.containsKey("Oakland"));
		printStr("\n");
		printBool(lst.containsKey("Buffalo"));
		printStr("\n");
		printStr("===============\n");
		Hashtable map = new Hashtable().init2(1,1);
		map.put("Pittsburgh", "Steelers");
		map.put("Miami", "Dolphins");
		map.put("Oakland", "Raiders");
		map.put("New England", "Patriots");
		map.put("New York", "Jets");
		map.put("New York", "Giants");
		map.put("Philadelphia", "Eagles");
		map.put("New Orleans", "Saints");
		map.put("Kansas City", "Chiefs");
		map.put("Seattle", "Seahawks");
		map.put("Arizona", "Cardinals");
		map.put("San Francisco", "49ers");
		printVal(map.get("Oakland"),"\n");
		printVal(map.get("Kansas City"), "\n");
		printVal(map.get("Seattle"), "\n");
		printVal(map.get("Miami"),"\n");
		printVal(map.get("Washington"), "\n");
		printVal(map.get("Arizona"), "\n");
		printVal(map.get("New England"),"\n");
		printVal(map.get("New Orleans"), "\n");
		printVal(map.get("Los Angeles"), "\n");
		printVal(map.get("San Francisco"), "\n");
		printVal(map.get("Pittsburgh"),"\n");
		printStr("===============================\n");
		Object[] keys = map.keys();
		for (int i = 0; i < keys.length; i++) {
			Object val = map.get(keys[i]);
			printVal(keys[i], " ==> ");
			printVal(val, "\n");
		}
		printStr("===============================\n");
		Hashtable map2 = new Hashtable().init2(2,2);
		for (int i = 0; i < keys.length; i++) {
			Object val = map.get(keys[i]);
			map2.put(new StringContainer().init((String)keys[i]), val);
		}
		map2.put(new StringContainer().init("New York"), "Jets");
		Object[] keys2 = map2.keys();
		String[] output = new String[keys2.length];
		for (int i = 0; i < keys2.length; i++) {
			output[i] = keys2[i].toString().concat(" ==> ");
			output[i] = output[i].concat(map2.get(keys2[i]).toString());
		}
		for (int i = 0; i < output.length; i++) {
			int bestIdx = i;
			String best = output[bestIdx];
			for (int j = i+1; j < output.length; j++) {
				if (output[j].compareTo(best) < 0) {
					bestIdx = j;
					best = output[j];
				}
			}
			output[bestIdx] = output[i];
			output[i] = best;
		}
		for (int i = 0; i < output.length; i++) {
			printVal(output[i],"\n");
		}
	}
	
	public void printList(LinkedList lst) {
		for (LinkedList ll = lst; !ll.isEmpty(); ll = ((NonEmptyLinkedList)ll).next) {
			NonEmptyLinkedList lll = (NonEmptyLinkedList)ll;
			printStr(lll.key.toString());
			printStr(" => ");
			printStr(lll.value.toString());
			printStr("\n");
		}
	}
	
	public void printVal(Object obj, String trailer) {
		if (obj == null) {
			printStr("(null)");
		}
		else {
			printStr((String)obj);
		}
		printStr(trailer);
	}
	
}
class LinkedList {
	public NonEmptyLinkedList put(Object key, Object value) {
		// abstract
		return null;
	}
	public LinkedList remove(Object key) {
		return this;
	}
	public Object find(Object key) {
		return null;
	}
	public boolean isEmpty() {
		return true;
	}
	public boolean containsKey(Object key) {
		return false;
	}
	public LinkedList popRem(LinkedList orig) {
		return orig;
	}
}
class EmptyLinkedList extends LinkedList {
	public NonEmptyLinkedList put(Object key, Object value) {
		return new NonEmptyLinkedList().init(key, value, this);
	}
}
class NonEmptyLinkedList extends LinkedList {
	Object key;
	Object value;
	LinkedList next;
	public NonEmptyLinkedList init(Object key, Object value, LinkedList next) {
		this.key = key;
		this.value = value;
		this.next = next;
		return this;
	}
	public boolean isEmpty() {
		return false;
	}
	public NonEmptyLinkedList put(Object key, Object value) {
		// if new element added, will be added at front
		NonEmptyLinkedList rtnVal = this;
		if (key.equals(this.key)) {
			this.value = value;
		}
		else {
			NonEmptyLinkedList putRest = next.put(key, value);
			if (putRest != this) {
				// element added: new one at front--keep it at front
				next = putRest.next;
				putRest.next = this;
				rtnVal = putRest;
			}
		}
		return rtnVal;
	}
	public LinkedList remove(Object key) {
		// returns null if no removal, otherwise the list node
		// that was removed, with the rest of the list appended
		LinkedList rtnVal = this;
		if (key.equals(this.key)) {
			// we have our element, and it's already at the head of
			// the list
		}
		else {
			LinkedList rem = next.remove(key);
			if (rem.isEmpty()) {
				// no removal: return null
				rtnVal = rem;
			}
			else {
				// removal: need to splice our node in so that removed
				// node is still at front
				NonEmptyLinkedList nell = (NonEmptyLinkedList) rem;
				this.next = nell.next;
				nell.next = this;
				rtnVal = nell;
			}
		}
		return rtnVal;
	}
	public LinkedList popRem(LinkedList orig) {
		return next;
	}
	public Object find(Object key) {
		Object rtnVal = value;
		if (!key.equals(this.key)) {
			rtnVal = next.find(key);
		}
		return rtnVal;
	}
	public boolean containsKey(Object key) {
		return key.equals(this.key) || next.containsKey(key);
	}
}
class Hashtable {
  // note: will not allow null as key or value
	int count;
	int loadFactorLimit;
	LinkedList[] buckets;
	
	public Hashtable init2(int initSize, int loadFactorLimit) {
		count = 0;
		this.loadFactorLimit = loadFactorLimit;
		buckets = createEmptyArray(initSize);
		return this;
	}
	public Hashtable init1(int initSize) {
		return this.init2(initSize, 5);
	}
	public Hashtable init() {
		return this.init1(20);
	}
	
	public LinkedList[] createEmptyArray(int size) {
		EmptyLinkedList emptyOne = new EmptyLinkedList();
		LinkedList[] rtnVal = new LinkedList[size];
		for (int i = 0; i < rtnVal.length; i++) {
			rtnVal[i] = emptyOne;
		}
		return rtnVal;
	}
	
	public int getSlot(Object key) {
		int rtnVal = key.hashCode() % buckets.length;
		if (rtnVal < 0) {
			rtnVal = rtnVal + buckets.length;
		}
		return rtnVal;
	}
	
	public void put(Object key, Object value) {
		if (key != null && value != null) {
			// note: we are not yet doing table-growing
			int bucketNum = getSlot(key);
			LinkedList l = buckets[bucketNum];
			buckets[bucketNum] = l.put(key, value);
			if (l != buckets[bucketNum]) {
				// element was added
				count++;
				if (count > buckets.length*loadFactorLimit) {
					LinkedList[] temp = buckets;
					buckets = this.createEmptyArray(temp.length*2);
					count = 0;
					for (int i = 0; i < temp.length; i++) {
						LinkedList p = temp[i];
						while (!p.isEmpty()) {
							NonEmptyLinkedList nell = (NonEmptyLinkedList)p;
							this.put(nell.key, nell.value);
							p = nell.next;
						}
					}
				}
			}
		}
	}
	public Object get(Object key) {
		return buckets[getSlot(key)].find(key);
	}
	public Object remove(Object key) {
		Object rtnVal = null;
		int bucketNum = getSlot(key);
		LinkedList ll = buckets[bucketNum];
		LinkedList ll2 = ll.remove(key);
		if (!ll2.isEmpty()) {
			// removal occurred
			count--;
			NonEmptyLinkedList ll3 = (NonEmptyLinkedList)ll2;
			rtnVal = ll3.value;
			buckets[bucketNum] = ll3.next;
		}
		return rtnVal;
	}
	public boolean containsKey(Object key) {
		return this.get(key) != null;
	}
	public void clear() {
		init2(20, loadFactorLimit);
	}
	public Object[] keys() {
		Object[] rtnVal = new Object[count];
		int idx = 0;
		for (int i = 0; i < buckets.length; i++) {
			LinkedList p = buckets[i];
			while (!p.isEmpty()) {
				NonEmptyLinkedList nell = (NonEmptyLinkedList)p;
				rtnVal[idx] = nell.key;
				idx++;
				p = nell.next;
			}
		}
		return rtnVal;
	}
	public int size() {
		return count;
	}
}
class StringContainer {
	String val;
	public StringContainer init(String s) {
		val = s;
		return this;
	}
	public String toString() {
		return val;
	}
}
