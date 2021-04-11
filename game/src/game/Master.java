package game;

//继承gam1Actor类
class Master extends game1.Game1Actor1 {
	//参数设置
	private static int blood = 100;
	public static int id = 1;
	public static int state = -1;//默认状态由玩家选择
	private static int attacknum = 300;//法师的攻击指数
	private static int defensefactor = 15;//法师的防守指数
	public static String name = "Master";
	//构造函数
	Master(game1 g1){
		//生成法师角色
		g1.super(id, blood, name, state, attacknum, defensefactor);
	}
	//攻击函数
	public void attack(Actor a) {
		//设置标签看进攻方式
		int label = 0;//默认为general attack
		
		//同类，一般进攻
		if(this.getname().equals(a.getname())) {
			this.generalattack(a);
			
		}else {
			//不同类，double进攻
			this.doubleattack(a);
			label = 1;
		}
		//输出当前状态
		if(label==0) {
			System.out.print("Master"+String.valueOf(this.id)+' '+"General Attack!"+"\n");
		}else {
			System.out.print("Master"+String.valueOf(this.id)+' '+"Double Attack!!"+"\n");
		}
		
	}
	//超级防护：特定条件触发（血量下降到一定程度时）
	//发动超级防御：增大防御指数，且不会回复
	public void superdefense() {
		defensefactor=30;
	}
	
	public boolean juddefense() {
		//判断是否会触发超级防护
		boolean flag = false;
		if(this.getBlood()<30) {
			this.superdefense();
			this.defense();
			flag = true;
		}else {
			this.defense();
			flag = false;
		}
		return flag;
	}

}
