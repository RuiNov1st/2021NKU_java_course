package Lottery3D;
import java.util.*;



//3D���ʻ�������
 class Lottery3D  {
	//��Ա��������
	int length = 3;//�����ĳ��ȶ���
	Integer[]userNumber = new Integer[length];
	Integer[]winNumber = new Integer[length];
	String userInput;
	Integer[]countwin = new Integer[11];//�Ժ��������ֳ��ֵĸ������м���
	Integer[]countinput = new Integer[11];
	Integer[]location = new Integer[3];//�Ժ����λ�ý��м�¼
	String []choice = new String[2];//����Ҫ�������ֵ�Ͷע��������ֽ��м�¼
	int label_high = 0;//��־�������Ͷע��0��ʾ�񶨡�ż����
	//��Ա��������

	//�����봫��
	void setUserNumber(String userInput){
		this.userInput = userInput;
	}
	//��������н�����
	Integer[]getwinNumber(){
		Integer[]arr = new Integer[length];
		Random r = new Random();
		for(int i = 0;i<length;i++) {
			arr[i]= r.nextInt(10);
		}
		return arr;
	}
	
	//��ֵ�û��������
	Integer[]setuserInput(String s){
		//ת���ַ���
		char a =' ';
		for(int i = 0;i<s.length();i++) {
			a = s.charAt(i);
			//��ĸʱ��Ϊ�˱���play������ͳһ�ԣ��������
			if((a<48||a>57)&&a!='*') {
				s = "000";
				break;
			}
		}
		return this.StringtoIntArray(s);
	}
	
	//�жϽ���
	//��Ҫ����ͬ���淨��д
	int getWins() {
		return 0;
	}
	
	//��String���͵�ת��ΪInteger��������
	Integer[]StringtoIntArray(String s){
		Integer[]arr = new Integer[length];
		for(int i = 0;i<length;i++) {
			if(s.charAt(i)=='*') {
				//�Ǻ�����Ϊ-1
				arr[i] = -1;
			}else{
			arr[i] = Integer.parseInt(s.substring(i,i+1));
			}
		}
		return arr;
	}
	//���ֵķǷ��Լ��
	boolean NumLegal(String s) {
		int length_pro = s.length();
		boolean flag = true;
		char a = ' ';
		for(int i = 0;i<length_pro;i++) {
			a = s.charAt(i);
			if(a<48||a>57) {
				flag = false;
				break;
			}
		}
		return flag;
	}
	//�ַ��ķǷ��Լ��
	boolean CharaLegal(String s) {
		if(!s.equals(choice[0])&&!s.equals(choice[1])) {
			return false;
		}
		if(s.equals(choice[0])) {
			label_high = 1;
		}
		return true;
	}
	
	//�ж������Ƿ�Ϸ�
	//��Ҫ����ͬ���淨��isAvailable������д
	//Ĭ�����룺�������֣�ֻ��ƶ����ֵطǷ��Լ��
	boolean isAvailable(String s) {
		//���ȼ��
		if(s.length()!=length) {return false;}
		//�����ֵķǷ��Խ��м��
		return this.NumLegal(s);
	}
	//��ͬ�������Ų�ͬ���������
	//��ͬ�������д
	void print_sen() {
		
	}
	//��ȡ�û�Ͷע
	String getinput() {
		print_sen();
		Scanner sc1 = new Scanner(System.in);
		String s  = sc1.next();
		return s;
		
	}
	//�ж��Ƿ��ʤ
	//��ͬͶע��ʽ�в�ͬ�Ĺ���
	boolean judwin() {
		return true;
	}
	//�Ը������д���
	void numbercount(){
		//��ʼ��count����Ϊ0
		for(int i = 0;i<11;i++) {
			this.countinput[i] = this.countwin[i] = 0;
		}
		//��¼ÿ�����ֳ��ֵĴ���
		for(int i = 0;i<length;i++) {
			if(userNumber[i]==-1) {
				this.countinput[10]++;
			}else {
			this.countinput[this.userNumber[i]]++;
			}
			this.countwin[this.winNumber[i]]++;
		}
	}
	
	//Ͷע���̣���������ģ��
	void  play() {
		//��ȡ�н�����
		this.winNumber = this.getwinNumber();
		System.out.print("�н�����Ϊ: "+String.valueOf(this.winNumber[0])+" "+String.valueOf(this.winNumber[1])+" "+String.valueOf(this.winNumber[2])+"\n");
		//��ʼֵ����
		String Input = "";
		//�ж������Ƿ�Ϸ�
		while(true) {
			//������ʾ
			Input = this.getinput();
			if(this.isAvailable(Input)) {
				//���ֳ��ȵ�һ����
				if(Input.length()==1) {
					Input+="**";
				}else {
					if(Input.length()==2) {
						Input+="*";
				}
				}
				break;
				}else {
				System.out.print("��������ȷ��Ͷע����\n");
			}
		}
		
		//�����봫��
		this.setUserNumber(Input);
		//��ֵ�������
		this.userNumber = this.setuserInput(Input);
		//�ж��Ƿ�����
		if(judwin()) {
		//�жϽ���
		int bouns = this.getWins();
		//�������
		System.out.print("����õĽ���Ϊ"+String.valueOf(bouns)+"\n");
		}else {
		//û�񽱣��������Ϊ0
		System.out.print("����õĽ���Ϊ"+String.valueOf(0)+"\n");
		}
	}
	
	
}
