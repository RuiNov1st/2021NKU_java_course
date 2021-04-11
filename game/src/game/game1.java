package game;

import java.util.*;

//定义游戏1的类，继承自game类
// 1. 实现game类中继承的东西，如角色数量，角色的具体创建（调用Warrior或Master）
//2.定义play方法：a1和a2对抗，选择防守的先出招，输出blood比较高的角色
class game1 extends Game {
	int ActorCount = 2;
	int actionCount = 2;
	int mode = 0;
	//构造函数无参
		game1(int mode){
			this.mode = mode;
		}
		
	//定义内部类
	public class Game1Actor1 extends Actor{
		//构造函数
		//通过子类构造对象来构造Actor类的引用
		Game1Actor1(int id,int blood,String name,int state,int attacknum,int defensefactor){
		//调用Actor构造函数
			super(id,blood,name,state,attacknum,defensefactor);
		}
		//成员函数
			
		//随机操作
		void randomAct(Actor a){
			//获取随机数
			Random r = new Random();
			//生成0或1的随机数并修改a的状态
			//系统状态不允许成为预言家
			a.state = r.nextInt(2);
			}
		}
	//设置输出文字
	void printout() {
		//输入操作：
		System.out.print("choose Attack(a) or Defense(d):");
	}
	//将操作设置独立出来
	//分为模式1和模式2
	void setoperator_1(String acttype) {
		//大小写不敏感
		//检查输入是否合法
	while(!acttype.equals("Attack")&&!acttype.equals("attack")&&!acttype.equals("Defense")&&!acttype.equals("defense")&&!acttype.equals("a")&&!acttype.equals("d")) {
			System.out.print("Wrong type! Please choose again!"+"\n");
			System.out.print("choose Attack(a) or Defense:");
			Scanner sc3 = new Scanner(System.in);
			acttype = sc3.next();
		}
			if(acttype.equals("Attack")||acttype.equals("attack")||acttype.equals("a")) {
					a1.state = 1;
			}else { 
				if(acttype.equals("Defense")||acttype.equals("defense")||acttype.equals("d")) {
				a1.state = 0;
				}
			}
	}
	void setoperator() {
		printout();
		Scanner sc2 = new Scanner(System.in);
		String acttype = sc2.next();
		
		//改变玩家操作状态:
		//首先判断a1的状态是否没被更改
		if(a1.state!=-1) {
			a1.state=-1;
		}
		
		setoperator_1(acttype);
		
		//系统生成随机操作
		Game1Actor1 ga1 = new game1.Game1Actor1(a2.id, a2.blood, a2.name, a2.state, a2.attacknum, a2.defensefactor);
		ga1.randomAct(a2);
		
	}
	
	
	//设置角色类型，创建随机角色（系统角色）
	Actor randomActor(boolean human) {
		//获取随机数
		Random r = new Random();
		//生成0或1的随机数
		int i= r.nextInt(2);
		
		if(i==0) {
			return new Master(this);
		}else {
			return new Warrior(this);
		}
	}
	//根据人类输出创建角色
	Actor setActor(String type) {
		//保证获取正确的角色名字
		while(!type.equals("Master")&&!type.equals("Warrior")&&!type.equals("m")&&!type.equals("M")&&!type.equals("w")&&!type.equals("W")) {
			System.out.print("Wrong type! Please enter again");
			Scanner sc = new Scanner(System.in);
			type = sc.next();
			//sc.close();
		}
		//根据角色名字创建特定角色
		switch(type) {
		case "Master":return new Master(this);
		case "m":return new Master(this);
		case "M":return new Master(this);
		default:return new Warrior(this);//默认生成法师
		}
		
	}
	
	//判定输赢
	void successprint() {
		//获取
		int a1blood = a1.getBlood();
		int a2blood = a2.getBlood();
		//模式一：只有一局，谁的血最多谁赢
		//判定胜利
		if(a1blood==a2blood) {
			System.out.print("Drew!\n");
		}else {
			if(a1blood>a2blood) {
				System.out.print("You are the winner!\n");
			}else {
				System.out.print("You lose!\n");
			}
		}
		
		//输出血量
		System.out.print(a1.name+String.valueOf(a1.id)+"Blood:"+String.valueOf(a1blood)+"\n");
		System.out.print(a2.name+String.valueOf(a2.id)+"Blood:"+String.valueOf(a2blood)+"\n");
	}
	
	//将操作独立出来
	boolean operate() {
		//当两者的操作相同时
		//若两者皆为防守，则人类玩家防守，系统玩家进攻（模式一，为了提高观赏性）
			if(a1.isDefense()) {
				a1.defense();
				a2.attack(a1);
				a1.redefense();
			}
			else {
				//人类玩家进攻，系统玩家可进攻可防守
				//系统玩家防守
				if(a2.isDefense()) {
					a2.defense();
					a1.attack(a2);
					a2.redefense();
				}else {
				//系统玩家进攻，则人类玩家先手
					a1.attack(a2);
					a2.attack(a1);
				}
			}
		return true;
}
	
	//输出创建角色名
	void printoutactor() {
		System.out.print("choose Master(m) or Warrior(w)");
	}
	
	void play_plus(int rank) {
		//操作
		setoperator();
		operate();
		//判定输赢
		successprint();
	}
	
	//设置游戏规则
	void play() {
		play_1(0);
	}
	void play_1(int rank) {
		//创建玩家
		boolean human = false;
		mylabels:
		while(true) {
			System.out.print("choose actor or produced randomly:\n");
			System.out.print("1-choose yourself; 2-produced randomly");
			Scanner sc = new Scanner(System.in);
			int i = 0;
			i = sc.nextInt();
			
			//设置变量记录是否人类自动创建角色
			
			//手动创建
			if(i==1) {
				printoutactor();
				Scanner sc1 = new Scanner(System.in);
				String actortype = sc1.next();
				//人类玩家
				a1 = this.setActor(actortype);
				break mylabels;
			}else {
				if(i==2) {
				//机器随即创建
				human = true;
				a1 = this.randomActor(human);
				human = false;
				break mylabels;
				}else {
					System.out.print("Wrong range!\n");
					continue mylabels;
				}
			}
		}
			
			//系统玩家
			a2 = this.randomActor(human);

			//若系统玩家角色和人类玩家相同，需要更改id：
			if(a1.getname().equals(a2.getname())) {
				a2.setid();
			}
			
			//输出玩家和系统角色
			System.out.print("You are"+" "+a1.getname()+String.valueOf(a1.id)+"\n");
			System.out.print("System is"+" "+a2.getname()+String.valueOf(a2.id)+"\n");
			
			//出手规则：
			
			//若为模式一，则每次只有一局
			play_plus(rank);
			
		}
}
		
			



		
