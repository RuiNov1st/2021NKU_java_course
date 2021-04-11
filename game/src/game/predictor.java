package game;

class predictor extends game2.Game2Actor {
	//参数设置
	private static int blood = 200;
	public static int id = 1;
	public static int state = -1;//默认状态由玩家选择
	private static int attacknum = 250;//预言家的攻击指数
	private static int defensefactor = 15;//法师的防守指数
	public static String name = "Predictor";
	
	//构造函数
	predictor(game2 g2){
			//生成勇士角色
		g2.super(id, blood, name, state, attacknum, defensefactor);
	}
	//攻击函数
	public void attack(Actor a) {
		//只有普通进攻
		this.generalattack(a);
		System.out.print("Predictor"+String.valueOf(this.id)+' '+"General Attack!"+"\n");
	}
	void printoutstate(Actor a) {
		if(a.state==0) {
		System.out.print(a.getname()+String.valueOf(a.id)+"Defense!\n");
		}else {
			System.out.print(a.getname()+String.valueOf(a.id)+"Attack!\n");
		}
	}
	//预言家：查看他人的操作状态
	public void predict(Actor a) {
		this.state = 2;//预言状态
		//查看对手的操作状态。
		printoutstate(a);
	}
	void setid() {
		id = id+1;
	}
	
			
			
		
}
