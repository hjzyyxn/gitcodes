//quadratic prob for hashtable
public class QuadraticProbingHashTable<AnyType> {
	public QuadraticProbingHashTable() {
		this(DEFAULT_TABLE_SIZE);
	}
	public QuadraticProbingHashTable(int size) {
		allocateArray(size);
		makeEmpty();
	}
	public void makeEmpty() {
		currentSize = 0;
		for(int i = 0; i < array.length; i++)
			array[i] = null;
	}

	public boolean contains(AnyType x) {}
	public void insert(AnyType x) {
		int currentPos = findPos(x);
		if(isActive(currentPos))
			return;

		array[currentPos] = new HashEntry<AnyType>(x, true);

		if(++currentSize > array.length/2)
			rehash();
	}
	public void remove(AnyType x) {
		int currentPos = findPos(x);
		if(isActive(currentPos))
			array[currentPos].isActive = false;
	}

	private static class HashEntry<AnyType> {
		public AnyType element;
		public boolean isActive;

		public HashEntry(AnyType e) {
			this(e, true);
		}

		public HashEntry(AnyType e, boolean i) {
			element = e;
			isActive = i;
		}
	}

	private static final int DEFAULT_TABLE_SIZE = 11;

	private HashEntry<AnyType> [] array;
	private int currentSize;

	private void allocateArray(int arraySize) {
		array = new HashEntry[arraySize];
	}
	private boolean isActive(int currentPos) {
		return array[currentPos] != null && array[currentPos].isActive;
	}
	private int findPos(AnyType x) {
		int offset = 1;
		int currentPos = myhash(x);

		while(array[currentPos] != null &&
			!array[currentPos].element.equals(x)) {
			currentPos += offset;
			offset += 2;
			if(currentPos >= array.length)
				currentPos -= array.length;
		}
		return currentPos;

	}
	private void rehash() {
		HashEntry<AnyType> [] oldArray = array;
		allocateArray(nextPrime(2 * oldArray.length));
		currentSize = 0;
		for(int i = 0; i < oldArray.length; i++)
			if(oldArray[i] != null && oldArray[i].isActive)
				insert(oldArray[i].element);
	}

	private int myhash(AnyType x) {
		int hashVal = x.hashCode( );

        hashVal %= array.length;
        if( hashVal < 0 )
            hashVal += array.length;

        return hashVal;
	}
	private static int nextPrime(int n) {
		if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
	}
	private static boolean isPrime(int n) {
		if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
	}
}