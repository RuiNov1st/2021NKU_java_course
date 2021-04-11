package game;

import java.util.*;

import game.game1.Game1Actor1;

//将mode=2分离
class game2 extends game1{
	int ActCount = 3;
	static int mode = 2;
	
	game2(){
		super(mode);
	}
	
	//多设置两个角色
	public class Game2Actor extends game1.Game1Actor1{
		//构造函数
		Game2Actor(int id,int blood,String name,int state,int attacknum,int defensefactor){
			super(id,blood,name,state,attacknum,defensefactor);
		}
		
	}
	//设置输出文字
	void printout() {
		//输出操作
		if(a1.name.equals("Predictor")) {
			System.out.print("choose Attack(a) or Defense(d) or Predict(p):");
		}else {
			System.out.print("choose Attack(a) or Defense(d):");
		}
	}
	//将操作设置独立出来
	//分为模式1和模式2
	void setoperator_2(String acttype) {
		//大小写不敏感
		//检查输入是否合法
		while(!acttype.equals("Attack")&&!acttype.equals("attack")&&!acttype.equals("Defense")&&!acttype.equals("Predict")&&!acttype.equals("p")&&
				!acttype.equals("defense")&&!acttype.equals("a")&&!acttype.equals("d")) {
				System.out.print("Wrong type! Please choose again!"+"\n");
				System.out.print("choose Attack(a) or Defense(d) or Predict(p):");
				Scanner sc3 = new Scanner(System.in);
				acttype = sc3.next();
			}
				if(acttype.equals("Attack")||acttype.equals("attack")||acttype.equals("a")) {
						a1.state = 1;
				}else { 
					if(acttype.equals("Defense")||acttype.equals("defense")||acttype.equals("d")) {
					a1.state = 0;
					}else {
						//预言状态
						if(acttype.equals("Predict")||acttype.equals("predict")||acttype.equals("p")) {
							a1.state = 2;
						}
					}
				}
		}
	//随机生成操作
	void setoperator() {
		printout();
		Scanner sc2 = new Scanner(System.in);
		String acttype = sc2.next();
		
		//改变玩家操作状态:
		//首先判断a1的状态是否没被更改
		if(a1.state!=-1) {
			a1.state=-1;
		}
		
		//预言家模式可以有预言操作
		if(a1.name.equals("Predictor")) {
			setoperator_2(acttype);
		}else {
		setoperator_1(acttype);
		}
		
		//系统生成随机操作
		Game1Actor1 ga1 = new game1.Game1Actor1(a2.id, a2.blood, a2.name, a2.state, a2.attacknum, a2.defensefactor);
		ga1.randomAct(a2);
	}
	//设置角色类型，创建随机角色（系统角色）
	Actor randomActor(boolean human) {
	//获取随机数
	Random r = new Random();
	int i = 0;
	if(human==true) {
	//生成0或1的随机数
	i= r.nextInt(3);
	}else {
	//机器不能创建预言家
	i = r.nextInt(2);
	}
			
	if(i==0) {
		return new Master(this);
	}else {
		if(i==1) {
		return new Warrior(this);
		}else {
			return new predictor(this);
		}
	}
	}
	//根据人类输出创建角色
	Actor setActor(String type) {
	//保证获取正确的角色名字
	while(!type.equals("Master")&&!type.equals("Warrior")&&!type.equals("Predictor")&&!type.equals("m")&&!type.equals("M")&&!type.equals("w")&&!type.equals("W")&&!type.equals("p")&&!type.equals("P")) {
		System.out.print("Wrong type! Please enter again");
		Scanner sc = new Scanner(System.in);
		type = sc.next();
		}
		//根据角色名字创建特定角色
		switch(type) {
		case "Master":return new Master(this);
		case "m":return new Master(this);
		case "M":return new Master(this);
		case "Predictor":return new predictor(this);
		case "p":return new predictor(this);
		case"P":return new predictor(this);
		default:return new Warrior(this);//默认生成法师
		}
			
	}
    //判定输赢
	void successprint() {
		//获取
		int a1blood = a1.getBlood();
		int a2blood = a2.getBlood();
		
		//模式二：打到一方的血小于0为止才能判定输赢。
		if(a1blood<=0) {
		System.out.print("You lose!\n");
		}else {
		if(a2blood<=0) {
		System.out.print("You are the winner!\n");
		}
		}
		//输出血量
		System.out.print(a1.name+String.valueOf(a1.id)+"Blood:"+String.valueOf(a1blood)+"\n");
		System.out.print(a2.name+String.valueOf(a2.id)+"Blood:"+String.valueOf(a2blood)+"\n");
	}

	//将操作独立出来
	boolean operate() {
	//模式二：
	boolean flag1 = false;//判断a1是否会触发超级防护
	boolean flag2 = false;//判断a2是否会触发超级防护
	//人类玩家预言状态下
	if(a1.isPredict()) {
		a1.predict(a2);
		//再次进行操作选择
		setoperator();
	}
		if(a1.isDefense()) {
			if(a1.juddefense()) {
				flag1 = true;
			}
			if(a2.isAttack()) {
				a2.judattack(a1);
			}else {
				if(a2.juddefense()) {
					flag2 = true;
				}
			}
			//若不是触发超级防护，则防守指数需要回复。
			if(!flag1) {
			a1.redefense();
			}
			if(!flag2) {
			a2.redefense();
			}
		}else {
			//人类玩家进攻，系统玩家可进攻可防守
			//系统玩家防守
			if(a2.isDefense()) {
				if(a2.juddefense()) {
					flag2 = true;
				}
				a1.judattack(a2);
				if(!flag2) {
					a2.redefense();
				}
			}else {
			//系统玩家进攻，则人类玩家先手
				a1.judattack(a2);
				if(a2.getBlood()<=0) {
					successprint();
					return true;
				}
				a2.judattack(a1);
			}
		}
		return false;
	}

	//输出创建角色名
	void printoutactor() {
		System.out.print("choose Master(m) or Warrior(w) or Predictor(p)");
	}
	//模式2，只要没达到双方没血都可以继续打下去
	void play_plus(int rank) {
		int countmatch = 1;
		mylabels:
		while(a1.getBlood()>0&&a2.getBlood()>0) {
			if(countmatch%5==0) {
				System.out.print("Would you like to give up? -Y(y)/N(n)\n");
				Scanner sc8 = new Scanner(System.in);
				String choice = sc8.next();
				if(choice.equals("Y")||choice.equals("y")) {
					a1.blood = 0;
					successprint();
					continue mylabels;
				}
			}
		setoperator();
		if(!operate()) {
		successprint();
		}
		if(a1.getBlood()>0&&a2.getBlood()>0) {
		//低级模式情况下系统随机加血
		a1.Bloodoperate();
		a2.BloodoperateSys(rank);
		//查看当前血量
		System.out.print("Your blood is:"+String.valueOf(a1.getBlood())+"\n");
	}
		//可以选择放弃比赛。
		countmatch++;
		
		}
		
	}
	//难度等级选择
	int rank_choose() {
		//选择难度等级
		System.out.print("choose level you wanna challenge:\n0-easy(System player would not add blood automatically),\n1-middle(System player would add blood randomly),\n2-advance(System player would add blood to win):\n");
		Scanner sc5 = new Scanner(System.in);
		while(!sc5.hasNextInt()) {
			System.out.print("Wrong range!\n");
			sc5.nextLine();
		}
		int rank = sc5.nextInt();
		return rank;
	}
	//独立操作
	void play() {
		int rank = rank_choose();
		play_1(rank);
	}
	
	
}