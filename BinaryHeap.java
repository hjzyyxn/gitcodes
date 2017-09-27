//BinaryHeap to build ADT
public class BinaryHeap<AnyType extends Comparable<? super AnyType>> {
	public BinaryHeap() {}
	public BinaryHeap(int capacity) {}
	public BinaryHeap(AnyType[] items) {}

	public void insert(AnyType x) {}
	public AnyType findMin() {}
	public AnyType deleteMin() {}
	public boolean isEmpty() {}
	public void makeEmpty() {}

	private static final int DEFAULT_CAPACITY = 10;

	private int currentSize;
	private AnyType[] array;

	private void percolateDown(int hole) {}
	private void buildHeap() {}
	private void enlargeArray(int newSize) {}

}