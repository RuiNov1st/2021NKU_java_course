package game;
import java.util.Random;


//������Ϸ1�Ľ�ɫѡ�񣬱�warrior��Master�̳�
class game1Actor extends Actor {
	
	
	//���캯��
	//ͨ�����๹�����������Actor�������
	game1Actor(int id,int blood,String name,int state,int attacknum,int defensefactor){
		//����Actor���캯��
		super(id,blood,name,state,attacknum,defensefactor);
	}
	//��Ա����
	
	//�������
	void randomAct(Actor a){
		//��ȡ�����
		Random r = new Random();
		//����0��1����������޸�a��״̬
		//nextInt�ķ�ΧΪ[0,n)
		a.state = r.nextInt(2);
		//Debug
		System.out.print(a.state);
		System.out.print("\n");
	}
	

}
