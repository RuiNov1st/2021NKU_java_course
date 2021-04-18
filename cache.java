package helloworld;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
class map<T,B>{
	//����HashMap�洢��ֵ
	HashMap<T,Node>c;
	int capacity = 0;
	//����ڵ�
	//��֤ʱ�䳣�����Ӷȣ���¼����˳��
	 class Node<T,B>{
		T key;
		B value;
		Node last = null;
		Node next = null;
		
		//���캯��
		public Node(T key,B value) {
			this.key = key;
			this.value = value;
			this.next = this;
		}
	}
	
	//����ͷβ�ڵ�
	Node HeadNode = null;
	Node TrailNode = null;
	
	//���캯��
	map(int capacity){
		this.c = new HashMap<T,Node>(capacity);
		this.capacity = capacity;
	}
	//ɾ��ĳ���ڵ�
	private void remove(Node n) {
		if(n==HeadNode) {
			//����ͷ�ڵ�
			HeadNode = HeadNode.next;
			//System.out.print(HeadNode.key);
			HeadNode.last = HeadNode;
			n = null;
		}else {
		//����β���
			if(n==TrailNode) {
				TrailNode = TrailNode.last;
				TrailNode.next = TrailNode;
				n = null;
			}else {
				//����ǰ��ڵ�ָ���ָ��
				n.last.next = n.next;
				n.next.last = n.last;
				n = null;//ɾ���ýڵ�
			}
		}
		
		
	}
	//��HashMap������ĳ���ڵ�
	private void insert(T key,Node n) {
		//����ǽڵ㣬����value
		c.put(key, n);
	}
	//�ڽڵ����������µĽڵ�
	public void put(T key,B value) {
		//�Ѿ��������key����ɾ���ټ���
		if(c.containsKey(key)) {
			remove(c.get(key));
			c.remove(key);
		}
		//������ʱ��Ҫɾ��ͷ�ڵ�
		if(c.size()>=capacity) {
			//���ݼ�ֵɾ����Ӧ�ļ�ֵ��
			c.remove(HeadNode.key);
			remove(HeadNode);
		}
		//û��ʱ����ֱ�Ӽ���
		if(c.size()<capacity) {
			//�¶���һ���ڵ�
			Node temp = new Node(key,value);
			//��һ���ڵ�
			if(c.size()==0) {
				HeadNode = temp;
				TrailNode = temp;
				temp.last = temp;
			}else {
				//���ǵ�һ���ڵ�ʱ������β���͵�ǰ�ڵ����һ���ڵ�
				temp.last = TrailNode;
				TrailNode.next =temp;
				TrailNode = temp;
			}
			//printnode();
			//hashMap����
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
		//������ڸü�ֵ�ԣ��򷵻�
		if(c.containsKey(key)) {
			//����Ԫ���Ƶ�β��
			Node temp = c.get(key);
			movetofirst(temp);
			//printnode();
			return temp.value;
		}else {
			//�����ڣ�����null
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


//������
//��������
class SplitString<T,B> {
	List<Integer>key_int;
	List<String>value_str;
	SplitString(){
		key_int = new ArrayList<Integer>();
		value_str = new ArrayList<String>();
	}
	
	//����������ַ���
	 void split_semi(String s) {
		List<String>l= Stream.of(s.split(";")).collect(Collectors.toList());
		for(Iterator<String>it = l.iterator();it.hasNext();) {
			//ʹ�ö��Ž�key��value�ֿ�
			List<String>l1 = Stream.of(it.next().split(",")).collect(Collectors.toList());
			//ֻ����һ��
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
	 //���ؼ�ֵ
	 List<Integer>getkey(){
		 return key_int;
	 }
	 //����ֵ
	 List<String>getvalue(){
		 return value_str;
	 }
}
	
public class cache{
	public static void main(String []args) {
		//��һ��Ϊ�������n�Ͳ�������m
		int init[] = {0,0};
		int i = 0;
		String input = "";
		Scanner sc = new Scanner(System.in);
		init[0] = sc.nextInt();
		init[1] = sc.nextInt();
		//��������ֵ
		String action = "";
		String key_value = "";
		List<Integer>key = new ArrayList();
		List<String>value = new ArrayList();
		map m = new map(init[0]);
		for(int j = 0;j<init[1];j++) {
			//next��ȡ��String��ʹ�ó��÷ָ�����س������С��ո�ָ��һ���ַ���
			action = sc.next();//��ȡ����
			key_value = sc.next();//��ȡ��ֵ��
			SplitString ss = new SplitString();
			ss.split_semi(key_value);
			key = ss.getkey();
			value = ss.getvalue();
			int len = key.size();
			//����
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


