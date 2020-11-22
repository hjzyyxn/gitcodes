	public static void printTree2(Node root){
		SequenceStack<Node> stack=new SequenceStack<Node>();
		List<Node> list=new ArrayList<Node>();
		//定义两个节点变量node和pre（这里需要注意pre节点的作用，下方的注释有详细地介绍）
		Node node=root;
		Node pre=null;
		do {
			//将左结点一次压入栈
			while(node!=null){
				stack.push(node);
				node=node.left;
			}
			//获取栈顶节点
			node = stack.get();
			//如果栈顶节点的右孩子不为空，说明还得把右子树压入栈中，这里需要注意
			//node.right!=pre这个条件，因为我们在遍历的过程中，对于（子树）根节点的判断会存在两次
			//第一次是弹出左孩子节点后，对根节点进行是否有右孩子的判断，如果有，则将右孩子压栈
			//第二次是弹出右孩子节点后，这时候因为循环的原因（代码的原因），我们再次对根节点进行了右孩子判断，
			//所以这里就必须得判断该右孩子节点是否在之前的循环中已经判断过了，如果判断过了，则弹出根节点，否则压入右孩子节点。
			//总的来说，pre节点的作用是用来防止重复遍历右孩子节点的。
			if(node.right!=null&&node.right!=pre){
				//node指向右孩子节点
				node=node.right;
			}else{//只要有一个条件不满足则执行
				//弹出栈顶元素
				node=stack.pop();
				//遍历栈顶元素
				list.add(node);
				//将pre指向当前弹出的元素，用来做下次对根节点的再次判断时，右孩子不重复遍历的一个条件
				pre=node;
				//将node设置为null，防止根节点重复压入左孩子节点。
				node=null;
			}
			
		} while (node!=null||!stack.isEmpty());
		System.out.println(list);
	}