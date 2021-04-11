package game;

import java.util.Scanner;
import java.util.*;

//Canplay:
//设置基本玩法的接口
 interface Canplay{
	void attack(Actor a);
	void defense();
}
	
 //Medican：
 //设置药包种类的不同加不同的血量
 //不同的药包有着不同的数量
 class Medicine{
	 //药包的种类设置
	 int general = 3;
	 int median = 2;
	 int advance = 1;
	 
	 //成员函数设置
	 
	 //将字符串转化为数字
	 int toInteger(String type) {
		 switch(type) {
		 case "general":return 1;
		 case "median":return 2;
		 case "advance":return 3;
		 }
		 return 0;
	 }
	 //将数字转化为字符串
	 String toString_pro(int type) {
		 switch(type) {
		 case 1: return "general";
		 case 2:return "median";
		 default:return "advance";
		 }
	 }
	 
	 //获取药包：
	 boolean getMedicine(String type) {
		int medicinelabel = 0;
		medicinelabel = this.toInteger(type);
		 //看是否还有
		 if(havemedican(medicinelabel)) {
			 switch(medicinelabel) {
			 case 1:general--;break;
			 case 2:median--;break;
			 case 3:advance--;break;
			 }
			 return true;
		 }else {
			 return false;
		 }
	 }
	 
	 //判断某种药是否还有药包：
	 boolean havemedican(int medicinelabel) {
		 boolean flag = true;
		 switch(medicinelabel) {
		 case 1: if(general==0)flag = false;break;
		 case 2: if(median==0)flag = false;break;
		 case 3: if(advance==0)flag = false;break;
		 }
		 return flag;
	 }
	 //判断所有的药是否还有药包
	 boolean havemedicine() {
		 boolean flag = true;
		 if(general==0&&median==0&&advance==0) {
			 flag = false;
		 }
		 return flag;
	 }
	 //输出当前药包的存量
	 void getcurrentmedicine() {
		 System.out.print("current medican:\n");
		 System.out.print("general:"+general+"\n");
		 System.out.print("median:"+median+"\n");
		 System.out.print("advance:"+advance+"\n");
	 }
	 
 }
 
 
 

//角色基类:
//1. 实现CanPlay接口
//2. 定义基本属性
//3. 定义基础方法
//4. 要被Game1Actor继承
class Actor implements Canplay {
	//成员变量
	String name;//角色名字
	int id;//角色id
	int blood;//角色血量
	int state;//角色状态：进攻或防守，定义进攻为1，防守为0
	int attacknum;//进攻指数
	int defensefactor;//防守指数
	Medicine blood_bag;//血包：附属于每一个角色
	int count = 3;
	
	//构造函数
	Actor(int id,int blood,String name,int state,int attacknum,int defensefactor){
		this.id = id;
		this.blood = blood;
		this.name = name;
		this.state = state;
		this.defensefactor = defensefactor;
		this.attacknum = attacknum;
		blood_bag = new Medicine();//每个角色在赋初值时都附属自己的血包
	}
	//成员函数
	
	//判断当前是否为防守状态
	boolean isDefense() {
		if(this.state==0) {
			return true;
		}else {
			return false;
		}
	}
	//判断当前是否为进攻状态
	boolean isAttack() {
		if(this.state==1) {
			return true;
		}else {
			return false;
		}
	}
	//是否发动预言状态
	boolean isPredict() {
		if(this.state==2) {
			return true;
		}else {
			return false;
			}
	}
	//加血
	//rui:根据药包种类的不同加不同的血量
	void addBlood(String type) {
		switch(type) {
		case "general":this.blood = this.blood+40;break;
		case "median":this.blood= this.blood+80;break;
		case "advance":this.blood = this.blood+120;break;
		}
		
	}
	//加血的玩家操作流程
	void Bloodoperate() {
		String jud = "";
		//判断是否还有血包
		//所有的血包都没了
		if(!blood_bag.havemedicine()) {
			System.out.print("There is no any blood bags left.\n");
		}else {
		//还有血包
		System.out.print("If you want to add your Blood?-Yes(y)/No(n)"+"\n");
		Scanner sc4 = new Scanner(System.in);
		//判断输入的合法性，若不合法则直接退出加血，（谁叫你连加血都输错）
		if(sc4.hasNext()) {
			jud = sc4.next();
		}else {
			System.out.print("Wrong Input!"+"\n");
		}
	}
		//大小写不敏感
		if(jud.equals("Yes")||jud.equals("yes")||jud.equals("y")||jud.equals("Y")) {
		//输出当前血包存量：
		System.out.print("Your medicine storage:\n");
		blood_bag.getcurrentmedicine();
		System.out.print("Choose blood bags type:g/m/a\n");
		Scanner sc5 = new Scanner(System.in);
		String type = sc5.next();
		//判断是否还有该类型的血包
		//若有，则添加;没有，则添加失败
		switch(type) {
		case "g":type = "general";break;
		case "m":type = "median";break;
		case "a":type = "advance";break;
		default:break;
		}
		 if(blood_bag.getMedicine(type)) {
				this.addBlood(type);
		}else {
				System.out.print("Failure add!");
			 }
		}
}
	
	//系统玩家加血
	//低级玩法：不加血。
	//中级玩法：产生随机数看是否加血，加血也只是加最低级的血，加血只能加三次。
	//高级玩法：为了赢，则从最大的开始添加
	void BloodoperateSys(int rank) {
		if(rank==1) {
	    //中级玩法：
	    Random r = new Random();
	    int type = r.nextInt(2);
	    
	    //当type = 0时不加血
	    if(type!=0) {
	    	if(count>0) {
	    	this.addBlood(blood_bag.toString_pro(1));
	    	count = count-1;
	    }
	}
	}else {
		if(rank==2) {
	//高级玩法：从最高开始加。
	//还有血包
	if(blood_bag.havemedicine()) {
		if(blood_bag.havemedican(3)) {
			this.addBlood("advance");
		}else {
			if(blood_bag.havemedican(2)) {
				this.addBlood("median");
			}else {
				if(blood_bag.havemedican(1))
				this.addBlood("general");
			}
		}
	}
		}
		
}
}
	
	//一般进攻：进攻等于攻击指数/防守指数
	void generalattack(Actor a) {
		int attackindex = this.attacknum/a.defensefactor;
		a.blood = a.blood-attackindex;
	}
	//double进攻：进攻等于攻击指数*2/防守指数
	void doubleattack(Actor a) {
		int attackindex = this.attacknum*2/a.defensefactor;
		a.blood = a.blood-attackindex;
	}
	
	//获取当前血量
	int getBlood() {
		return this.blood;
	}
	//根据角色的不同设置不同的血量
	void setBlood(int blood) {
		this.blood = blood;
	}
	//获取角色类型
	String getname() {
		return this.name;
	}
	//设置角色名字
	void setName(String name) {
		this.name = name;
	}
	
	
	//接口的实现，被具体角色重写
	//attack函数：更改进攻状态，
	public void attack(Actor a) {
	}
	
		
	//defense函数，更改防守状态	
	public void defense() {
		//防守状态下防守指数增加。
		this.defensefactor +=10;
		this.state = 0;
		//输出当时状态]
		System.out.print(this.name+String.valueOf(this.id)+' '+"Defense!"+"\n");
		
	}
	public void judattack(Actor a) {
		this.attack(a);
	}
	public boolean juddefense() {
		this.defense();
		return false;
	}
	//防守指数状态的恢复
	public void redefense() {
		this.defensefactor-=10;
	}
	//在预言家类中重写
	public void predict(Actor a) {
		
	}
	void setid() {
		this.id+=1;
		
	}
	

}
