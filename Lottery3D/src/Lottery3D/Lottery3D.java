package Lottery3D;
import java.util.*;



//3D福彩基本定义
 class Lottery3D  {
	//成员变量定义
	int length = 3;//基本的长度定义
	Integer[]userNumber = new Integer[length];
	Integer[]winNumber = new Integer[length];
	String userInput;
	Integer[]countwin = new Integer[11];//对号码中数字出现的个数进行计数
	Integer[]countinput = new Integer[11];
	Integer[]location = new Integer[3];//对号码的位置进行记录
	String []choice = new String[2];//对需要输入文字的投注种类的文字进行记录
	int label_high = 0;//标志文字类的投注，0表示否定、偶数等
	//成员函数定义

	//将输入传入
	void setUserNumber(String userInput){
		this.userInput = userInput;
	}
	//随机设置中奖号码
	Integer[]getwinNumber(){
		Integer[]arr = new Integer[length];
		Random r = new Random();
		for(int i = 0;i<length;i++) {
			arr[i]= r.nextInt(10);
		}
		return arr;
	}
	
	//赋值用户输入号码
	Integer[]setuserInput(String s){
		//转化字符串
		char a =' ';
		for(int i = 0;i<s.length();i++) {
			a = s.charAt(i);
			//字母时，为了保持play函数的统一性，变成数字
			if((a<48||a>57)&&a!='*') {
				s = "000";
				break;
			}
		}
		return this.StringtoIntArray(s);
	}
	
	//判断奖金
	//需要被不同的玩法重写
	int getWins() {
		return 0;
	}
	
	//将String类型的转化为Integer类型数组
	Integer[]StringtoIntArray(String s){
		Integer[]arr = new Integer[length];
		for(int i = 0;i<length;i++) {
			if(s.charAt(i)=='*') {
				//星号设置为-1
				arr[i] = -1;
			}else{
			arr[i] = Integer.parseInt(s.substring(i,i+1));
			}
		}
		return arr;
	}
	//数字的非法性检查
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
	//字符的非法性检查
	boolean CharaLegal(String s) {
		if(!s.equals(choice[0])&&!s.equals(choice[1])) {
			return false;
		}
		if(s.equals(choice[0])) {
			label_high = 1;
		}
		return true;
	}
	
	//判断输入是否合法
	//需要被不同的玩法的isAvailable函数重写
	//默认输入：三个数字，只设计对数字地非法性检查
	boolean isAvailable(String s) {
		//长度检查
		if(s.length()!=length) {return false;}
		//对数字的非法性进行检查
		return this.NumLegal(s);
	}
	//不同输入有着不同的输入语句
	//不同类进行重写
	void print_sen() {
		
	}
	//获取用户投注
	String getinput() {
		print_sen();
		Scanner sc1 = new Scanner(System.in);
		String s  = sc1.next();
		return s;
		
	}
	//判断是否获胜
	//不同投注方式有不同的规则
	boolean judwin() {
		return true;
	}
	//对个数进行处理
	void numbercount(){
		//初始化count数组为0
		for(int i = 0;i<11;i++) {
			this.countinput[i] = this.countwin[i] = 0;
		}
		//记录每个数字出现的次数
		for(int i = 0;i<length;i++) {
			if(userNumber[i]==-1) {
				this.countinput[10]++;
			}else {
			this.countinput[this.userNumber[i]]++;
			}
			this.countwin[this.winNumber[i]]++;
		}
	}
	
	//投注进程：包括测试模块
	void  play() {
		//获取中奖号码
		this.winNumber = this.getwinNumber();
		System.out.print("中奖号码为: "+String.valueOf(this.winNumber[0])+" "+String.valueOf(this.winNumber[1])+" "+String.valueOf(this.winNumber[2])+"\n");
		//初始值定义
		String Input = "";
		//判断输入是否合法
		while(true) {
			//输入提示
			Input = this.getinput();
			if(this.isAvailable(Input)) {
				//保持长度的一致性
				if(Input.length()==1) {
					Input+="**";
				}else {
					if(Input.length()==2) {
						Input+="*";
				}
				}
				break;
				}else {
				System.out.print("请输入正确的投注内容\n");
			}
		}
		
		//将输入传入
		this.setUserNumber(Input);
		//赋值输入号码
		this.userNumber = this.setuserInput(Input);
		//判断是否中了
		if(judwin()) {
		//判断奖金
		int bouns = this.getWins();
		//输出奖金
		System.out.print("您获得的奖金为"+String.valueOf(bouns)+"\n");
		}else {
		//没获奖，输出奖金为0
		System.out.print("您获得的奖金为"+String.valueOf(0)+"\n");
		}
	}
	
	
}
