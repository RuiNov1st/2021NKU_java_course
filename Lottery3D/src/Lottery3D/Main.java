package Lottery3D;
import java.util.*;

//实现不同的福彩玩法
//不同的玩法有不同的输入规则，需要验证输入是否合法
public class Main {
	public static void main(String arg[]) {
		
	outer:
	while(true){
	//选择投注方式
	//可以查看投注方式
	System.out.print("请输入投注方式"+"\n");
	System.out.print("普通投注方式有：\n");
	System.out.print("SingleChoose,\nOneD(TwoD)Choose,\nGroupChoose,\nGuess1D(Guess2D)Choose,\nGeneralChoose,PackageChoose\n");
	System.out.print("高级投注方式有：\n");
	System.out.print("SumChoose,\nLargeSmallChoose,\nThreeSameChoose,\nTractorChoose,\nOddEvenChoose\n");
	Scanner sc = new Scanner(System.in);
	String type = "";
	
	Lottery3D l = new Lottery3D();
	//设置循环避免投注方式不合法
	mylabels:
	while(true) {
	type = sc.next();
	switch(type) {
	case"SingleChoose":l = new Single();break mylabels;
	case "OneDChoose":l = new OneD();break mylabels ;
	case "GroupChoose":l = new group();break mylabels;
	case"Guess1DChoose":l = new Guess1D();break mylabels;
	case "TwoDChoose":l = new TowD();break mylabels;
	case "Guess2DChoose":l = new Guess2D();break mylabels;
	case "GeneralChoose":l = new General();break mylabels;
	case"SumChoose":l = new sum();break mylabels;
	case"PackageChoose":l = new Package();break mylabels;
	case"LargeSmallChoose":l = new LargeSmall();break mylabels;
	case"ThreeSameChoose":l = new ThreeSame();break mylabels;
	case "TractorChoose":l = new Tractor();break mylabels;
	case"OddEvenChoose":l = new OddEven();break mylabels;
	default:System.out.print("您输入的投注方式不存在，请重新输入\n");
	}
}
	l.play();
	
	inner:
	while(true) {
	System.out.print("再来一次？"+"\n");
	Scanner sc1 = new Scanner(System.in);
	String type1 = sc1.next();
	if(type1.equals("Yes")) {
		break inner;
	}else {
		if(type1.equals("No")) {
			break outer;
		}else {
			continue inner;
		}
	}
		
	}
	}

	}
}
