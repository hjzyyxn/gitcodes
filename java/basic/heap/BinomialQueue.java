//二项队列
public class BinomialQueue<AnyType extends Comparable<? super AnyType>> {
	public BinomialQueue() {
		theTrees = new BinNode[ DEFAULT_TREES ];
        makeEmpty( );
	}
	public BinomialQueue(AnyType item) {
		currentSize = 1;
        theTrees = new BinNode[ 1 ];
        theTrees[ 0 ] = new BinNode<>( item, null, null );
	}

	public void merge(BinomialQueue<AnyType> rhs) {
		if (this == rhs)
			return;

		currentSize += rhs.currentSize;
		if (currentSize > capacity()) {
			int maxLength = Math.max(theTrees.length, rhs.theTrees.length);
			expandTheTrees(maxLength + 1);
		}

		BinNode<AnyType> carry = null;
		for (int i = 0, j = 1; j <= currentSize; i++, j *= 2) {
			BinNode<AnyType> t1 = theTrees[i];
			BinNode<AnyType> t2 = i < rhs.theTrees.length ? rhs.theTrees[ i ] : null;

            int whichCase = t1 == null ? 0 : 1;
            whichCase += t2 == null ? 0 : 2;
            whichCase += carry == null ? 0 : 4;

            switch( whichCase )
            {
              case 0: /* No trees */
              case 1: /* Only this */
                break;
              case 2: /* Only rhs */
                theTrees[ i ] = t2;
                rhs.theTrees[ i ] = null;
                break;
              case 4: /* Only carry */
                theTrees[ i ] = carry;
                carry = null;
                break;
              case 3: /* this and rhs */
                carry = combineTrees( t1, t2 );
                theTrees[ i ] = rhs.theTrees[ i ] = null;
                break;
              case 5: /* this and carry */
                carry = combineTrees( t1, carry );
                theTrees[ i ] = null;
                break;
              case 6: /* rhs and carry */
                carry = combineTrees( t2, carry );
                rhs.theTrees[ i ] = null;
                break;
              case 7: /* All three */
                theTrees[ i ] = carry;
                carry = combineTrees( t1, t2 );
                rhs.theTrees[ i ] = null;
                break;
            }
		}

		for( int k = 0; k < rhs.theTrees.length; k++ )
            rhs.theTrees[ k ] = null;
        rhs.currentSize = 0;
	}
	public void insert(AnyType x) {
		merge(new BinomialQueue<AnyType>(x));
	}
	public AnyType findMin() {
		if( isEmpty( ) )
            throw new UnderflowException( );

        return theTrees[ findMinIndex( ) ].element;
	}
	public AnyType deleteMin() {
		if( isEmpty( ) )
            throw new UnderflowException( );

        int minIndex = findMinIndex( );
        AnyType minItem = theTrees[ minIndex ].element;

        BinNode<AnyType> deletedTree = theTrees[ minIndex ].leftChild;

        // Construct H''
        BinomialQueue<AnyType> deletedQueue = new BinomialQueue<>( );
        deletedQueue.expandTheTrees( minIndex );
        
        deletedQueue.currentSize = ( 1 << minIndex ) - 1;
        for( int j = minIndex - 1; j >= 0; j-- )
        {
            deletedQueue.theTrees[ j ] = deletedTree;
            deletedTree = deletedTree.nextSibling;
            deletedQueue.theTrees[ j ].nextSibling = null;
        }

        // Construct H'
        theTrees[ minIndex ] = null;
        currentSize -= deletedQueue.currentSize + 1;

        merge( deletedQueue );
        
        return minItem;
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}
	public void makeEmpty() {
		currentSize = 0;
        for( int i = 0; i < theTrees.length; i++ )
            theTrees[ i ] = null;
	}

	private static class BinNode<AnyType> {
		BinNode(AnyType theElement) {
			this(theElement, null, null);
		}

		BinNode(AnyType theElement, BinNode<AnyType> lt, BinNode<AnyType> nt) {
			element = theElement;
			leftChild = lt;
			nextSibling = nt;
		}

		AnyType element;
		BinNode<AnyType> leftChild;
		BinNode<AnyType> nextSibling;
	}

	private static final int DEFAULT_TREES = 1;

	private int currentSize;
	private BinNode<AnyType> [] theTrees;

	private void expandTheTrees(int newNumTrees) {
		BinNode<AnyType> [ ] old = theTrees;
        int oldNumTrees = theTrees.length;

        theTrees = new BinNode[ newNumTrees ];
        for( int i = 0; i < Math.min( oldNumTrees, newNumTrees ); i++ )
            theTrees[ i ] = old[ i ];
        for( int i = oldNumTrees; i < newNumTrees; i++ )
            theTrees[ i ] = null;
	}

	private BinNode<AnyType> combineTrees(BinNode<AnyType> t1, BinNode<AnyType> t2) {
		if (t1.element.compareTo(t2.element) > 0)
			return combineTrees(t2, t1);
		t2.nextSibling = t1.leftChild;
		t1.leftChild = t2;
		return t1;
	}

	private int capacity() {
		return (1 << theTrees.length) - 1;
	}
	private int findMinIndex() {
		int i;
        int minIndex;

        for( i = 0; theTrees[ i ] == null; i++ )
            ;

        for( minIndex = i; i < theTrees.length; i++ )
            if( theTrees[ i ] != null &&
                theTrees[ i ].element.compareTo( theTrees[ minIndex ].element ) < 0 )
                minIndex = i;

        return minIndex;
	}
}