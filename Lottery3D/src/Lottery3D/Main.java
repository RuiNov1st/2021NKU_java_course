package Lottery3D;
import java.util.*;

//ʵ�ֲ�ͬ�ĸ����淨
//��ͬ���淨�в�ͬ�����������Ҫ��֤�����Ƿ�Ϸ�
public class Main {
	public static void main(String arg[]) {
		
	outer:
	while(true){
	//ѡ��Ͷע��ʽ
	//���Բ鿴Ͷע��ʽ
	System.out.print("������Ͷע��ʽ"+"\n");
	System.out.print("��ͨͶע��ʽ�У�\n");
	System.out.print("SingleChoose,\nOneD(TwoD)Choose,\nGroupChoose,\nGuess1D(Guess2D)Choose,\nGeneralChoose,PackageChoose\n");
	System.out.print("�߼�Ͷע��ʽ�У�\n");
	System.out.print("SumChoose,\nLargeSmallChoose,\nThreeSameChoose,\nTractorChoose,\nOddEvenChoose\n");
	Scanner sc = new Scanner(System.in);
	String type = "";
	
	Lottery3D l = new Lottery3D();
	//����ѭ������Ͷע��ʽ���Ϸ�
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
	default:System.out.print("�������Ͷע��ʽ�����ڣ�����������\n");
	}
}
	l.play();
	
	inner:
	while(true) {
	System.out.print("����һ�Σ�"+"\n");
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
