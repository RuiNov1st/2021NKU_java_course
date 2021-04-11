package game;

import java.util.*;
//游戏基类
// 1.定义角色数量
// 2.实现角色（被Game1继承）
// 3. 定义成员函数（被Game1继承）
abstract class Game {
	//角色种类数量
	int actortypenum = 2;
	//角色设置
	Actor a1;
	Actor a2;//系统玩家
	
	//成员函数
	//设置角色类型，创建随机角色（系统角色）
	abstract Actor randomActor(boolean human);
	
	//根据用户输入的角色创建特定角色
	abstract Actor setActor(String type);
	
	abstract void play();

}
