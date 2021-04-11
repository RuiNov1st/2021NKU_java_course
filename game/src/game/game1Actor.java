package game;
import java.util.Random;


//定义游戏1的角色选择，被warrior和Master继承
class game1Actor extends Actor {
	
	
	//构造函数
	//通过子类构造对象来构造Actor类的引用
	game1Actor(int id,int blood,String name,int state,int attacknum,int defensefactor){
		//调用Actor构造函数
		super(id,blood,name,state,attacknum,defensefactor);
	}
	//成员函数
	
	//随机操作
	void randomAct(Actor a){
		//获取随机数
		Random r = new Random();
		//生成0或1的随机数并修改a的状态
		//nextInt的范围为[0,n)
		a.state = r.nextInt(2);
		//Debug
		System.out.print(a.state);
		System.out.print("\n");
	}
	

}
