package game;

//�̳�gam1Actor��
class Warrior extends game1.Game1Actor1 {
	private static int blood = 300;
	public static int id = 1;
	public static int state = -1;//Ĭ��״̬�����ѡ��
	private static int attacknum = 300;
	private static int defensefactor = 10;
	public static String name = "Warrior";
	
	//���캯��
	Warrior(game1 g1){
		//������ʿ��ɫ
		g1.super(id, blood, name, state, attacknum, defensefactor);
	}
	//��������
	public void attack(Actor a) {
		//���ñ�ǩ��������ʽ
		int label = 0;//Ĭ��Ϊgeneral attack
		//ͬ�࣬double����
		if(a.getname().equals(this.getname())) {
			this.doubleattack(a);
			label = 1;
		}else {
			//��ͬ�࣬��ͨ����
			this.generalattack(a);
		}
		//�����ǰ״̬
		if(label==0) {
		System.out.print("Warrior"+String.valueOf(this.id)+' '+"General Attack!"+"\n");
		}else {
		System.out.print("Warrior"+String.valueOf(this.id)+' '+"Double Attack!!"+"\n");
		}
		
	}
	//�������У��عⷵ�գ��ض������´�����
	//��Ѫ���½���һ���̶�ʱ�ɴ���
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
