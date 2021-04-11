package game;

//继承gam1Actor类
class Warrior extends game1.Game1Actor1 {
	private static int blood = 300;
	public static int id = 1;
	public static int state = -1;//默认状态由玩家选择
	private static int attacknum = 300;
	private static int defensefactor = 10;
	public static String name = "Warrior";
	
	//构造函数
	Warrior(game1 g1){
		//生成勇士角色
		g1.super(id, blood, name, state, attacknum, defensefactor);
	}
	//攻击函数
	public void attack(Actor a) {
		//设置标签看进攻方式
		int label = 0;//默认为general attack
		//同类，double进攻
		if(a.getname().equals(this.getname())) {
			this.doubleattack(a);
			label = 1;
		}else {
			//不同类，普通进攻
			this.generalattack(a);
		}
		//输出当前状态
		if(label==0) {
		System.out.print("Warrior"+String.valueOf(this.id)+' '+"General Attack!"+"\n");
		}else {
		System.out.print("Warrior"+String.valueOf(this.id)+' '+"Double Attack!!"+"\n");
		}
		
	}
	//超级大招：回光返照（特定条件下触发）
	//当血量下降到一定程度时可触发
	public void superattack(Actor a) {
		int attackindex = 0;
		if(a.getname().equals(this.getname())) {
		attackindex = attacknum*4/a.defensefactor;
		}else {
			attackindex = attacknum*2/a.defensefactor;
		}
		a.blood = a.blood-attackindex;
	}
	public void judattack(Actor a) {
		if(this.getBlood()<70) {
			this.superattack(a);
		}else {
			this.attack(a);
		}
	}

}
