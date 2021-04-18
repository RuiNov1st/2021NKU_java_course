package helloworld;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
class map<T,B>{
	//利用HashMap存储键值
	HashMap<T,Node>c;
	int capacity = 0;
	//定义节点
	//保证时间常量复杂度，记录插入顺序
	 class Node<T,B>{
		T key;
		B value;
		Node last = null;
		Node next = null;
		
		//构造函数
		public Node(T key,B value) {
			this.key = key;
			this.value = value;
			this.next = this;
		}
	}
	
	//定义头尾节点
	Node HeadNode = null;
	Node TrailNode = null;
	
	//构造函数
	map(int capacity){
		this.c = new HashMap<T,Node>(capacity);
		this.capacity = capacity;
	}
	//删除某个节点
	private void remove(Node n) {
		if(n==HeadNode) {
			//更改头节点
			HeadNode = HeadNode.next;
			//System.out.print(HeadNode.key);
			HeadNode.last = HeadNode;
			n = null;
		}else {
		//更改尾结点
			if(n==TrailNode) {
				TrailNode = TrailNode.last;
				TrailNode.next = TrailNode;
				n = null;
			}else {
				//更换前后节点指针的指向
				n.last.next = n.next;
				n.next.last = n.last;
				n = null;//删除该节点
			}
		}
		
		
	}
	//在HashMap中增加某个节点
	private void insert(T key,Node n) {
		//存的是节点，不是value
		c.put(key, n);
	}
	//在节点列中增加新的节点
	public void put(T key,B value) {
		//已经有了这个key，先删除再加入
		if(c.containsKey(key)) {
			remove(c.get(key));
			c.remove(key);
		}
		//容量满时需要删除头节点
		if(c.size()>=capacity) {
			//根据键值删除对应的键值对
			c.remove(HeadNode.key);
			remove(HeadNode);
		}
		//没满时可以直接加入
		if(c.size()<capacity) {
			//新定义一个节点
			Node temp = new Node(key,value);
			//第一个节点
			if(c.size()==0) {
				HeadNode = temp;
				TrailNode = temp;
				temp.last = temp;
			}else {
				//不是第一个节点时，更新尾结点和当前节点的上一个节点
				temp.last = TrailNode;
				TrailNode.next =temp;
				TrailNode = temp;
			}
			//printnode();
			//hashMap储存
			insert(key, temp);
		}
	}
	private void movetofirst(Node n) {
		Node temp = n;
		remove(n);
		Node lat = TrailNode;
		//System.out.print(TrailNode.key);
		lat.next = temp;
		temp.last = lat;
		temp.next = temp;
		TrailNode = temp;
	}
	public Object get(T key) {
		//System.out.print(key);
		//如果存在该键值对，则返回
		if(c.containsKey(key)) {
			//将该元素移到尾端
			Node temp = c.get(key);
			movetofirst(temp);
			//printnode();
			return temp.value;
		}else {
			//不存在，返回null
			return null;
		}
		
	}
	public void printnode() {
		Node temp = HeadNode;
		while(temp!=TrailNode) {
			System.out.print(temp.key);
			temp = temp.next;
		}
		if(temp==TrailNode) {
			System.out.print(temp.key);
		}
		System.out.print("\n");
	}
	
	
}


//主函数
//泛型设置
class SplitString<T,B> {
	List<Integer>key_int;
	List<String>value_str;
	SplitString(){
		key_int = new ArrayList<Integer>();
		value_str = new ArrayList<String>();
	}
	
	//处理输入的字符串
	 void split_semi(String s) {
		List<String>l= Stream.of(s.split(";")).collect(Collectors.toList());
		for(Iterator<String>it = l.iterator();it.hasNext();) {
			//使用逗号将key和value分开
			List<String>l1 = Stream.of(it.next().split(",")).collect(Collectors.toList());
			//只处理一个
			for(int i = 0;i<l1.size();i++) {
				String temp = l1.get(i);
				if(temp.contains("value")) {
					value_str.add(temp); 
				}else {
					key_int.add(Integer.valueOf(temp));
				}
			}
		}
	}
	 //返回键值
	 List<Integer>getkey(){
		 return key_int;
	 }
	 //返回值
	 List<String>getvalue(){
		 return value_str;
	 }
}
	
public class cache{
	public static void main(String []args) {
		//第一行为最大容量n和操作次数m
		int init[] = {0,0};
		int i = 0;
		String input = "";
		Scanner sc = new Scanner(System.in);
		init[0] = sc.nextInt();
		init[1] = sc.nextInt();
		//处理动作和值
		String action = "";
		String key_value = "";
		List<Integer>key = new ArrayList();
		List<String>value = new ArrayList();
		map m = new map(init[0]);
		for(int j = 0;j<init[1];j++) {
			//next获取的String是使用常用分隔符如回车、换行、空格分割的一个字符串
			action = sc.next();//获取动作
			key_value = sc.next();//获取键值对
			SplitString ss = new SplitString();
			ss.split_semi(key_value);
			key = ss.getkey();
			value = ss.getvalue();
			int len = key.size();
			//操作
			if(action.equals("put")) {
				for(i = 0;i<len;i++) {
					int key_temp = key.get(i);
					String value_temp = value.get(i);
					m.put(key_temp, value_temp);
				}
			}else {
				if(action.equals("get")) {
					//System.out.print(key.size());
					for(int a = 0;a<key.size();a++) {
						Object temp = m.get(key.get(a));
						if(temp==null) {
							System.out.print("null;");
						}else {
							System.out.print(temp+";");
						}
					}
				}
				System.out.print("\n");
			}
			
			
		}
	}

}


