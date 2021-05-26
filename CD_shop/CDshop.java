package helloworld;


import java.io.IOException;
import java.util.*;
import java.util.logging.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.concurrent.locks.*;

//主线程
public class CDshop {
	public static void main(String []args) {
		//使用锁
		Lock l = new ReentrantLock();
		//设置进货和销售条件
		Condition salecondition;
		Condition getincondition;
		getincondition = l.newCondition();
		salecondition = l.newCondition();
		
		//设置日志
		Logger mylogger = Logger.getLogger("CDshop");
		//输入到文件中
		FileHandler f;
		try {
			f = new FileHandler("D:\\java_file\\HelloWorld\\cdshop.txt");
			SimpleFormatter sf = new SimpleFormatter();
			f.setFormatter(sf);
			mylogger.addHandler(f);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//可售CD列表有十种
		CD_collect cc = new CD_collect(mylogger,l,salecondition,getincondition);
		//可租CD列表10张
		CD_pro []cdrentlist = new CD_pro[10];
		for(int i = 0;i<10;i++) {
			cdrentlist[i] = new CD_pro();
		}
		//给销售和租借的CD类别起名字
		for(int i = 0;i<10;i++) {
			cdrentlist[i].name = "CD_rent"+String.valueOf(i+1);
			cdrentlist[i].num = 1;//每种CD只有一张
		}
		//传入线程中
		ControlThread_pro control = new ControlThread_pro(cc,cdrentlist,mylogger, l,getincondition , salecondition);
		control.start();
	}

}
//把进货和销售看成集合操作
class CD_collect{
	Lock l;
	private Condition getincondition;
	private Condition salecondition;
	int type = 10;
	Logger lg;
	//可售CD列表有十种
	CD_pro []cdlist =new CD_pro[10];
	CD_collect(Logger lg,Lock l, Condition salecondition, Condition getincondition) {
		this.l= l;
		this.getincondition = getincondition;
		this.salecondition = salecondition;
		this.lg = lg;
		for(int i = 0;i<10;i++) {
		cdlist[i] = new CD_pro();
		cdlist[i].name = "CD_saled"+String.valueOf(i+1);
		}
	}
	//进货
	void getin() throws InterruptedException {
		l.lock();
		for(int i = 0;i<10;i++) {
			cdlist[i].num = 10;
			//lg.info(cdlist[i].name+" GetIn Start\n"+cdlist[i].name+" Current Storage:"+cdlist[i].num+"\n");
			System.out.print(cdlist[i].name+" GetIn Start\n");
			System.out.print(cdlist[i].name+" Current Storage:"+cdlist[i].num+"\n");
		}
		salecondition.signalAll();
		long remain = getincondition.awaitNanos(1000000000);
		if(remain<=0) {
			System.out.print("Normal GetIn!\n");
		}
		l.unlock();
	}
	
	//销售
	void sale(int n,int id,int type,GetInThread_pro gt) {
			l.lock();
			//库存不足
			while(this.cdlist[type].num-n<0) {
				//lg.warning(cdlist[type].name+"'s stock is not enough now\n"+cdlist[type].name+" wait to GetIn\n");
				//System.out.print("current Storage: "+this.cdlist[type].num+"\n");
				System.out.print(cdlist[type].name+"'s stock is not enough now\n");
				//System.out.print(cdlist[type].name+" wait to GetIn\n");
				System.out.print(cdlist[type].name+" wait to GetIn\n");
				try {
					System.out.print(new Date()+" Force to get in\n");
					getincondition.signal();
					salecondition.await();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			//库存充足，可以售卖
			cdlist[type].num = cdlist[type].num-n;
			//lg.info(new Date()+"Thread"+id+" "+"Sale "+n+"copies "+cdlist[type].name+"\n"+cdlist[type].name+" Current Storage: "+cdlist[type].num+"\n");
			System.out.print(new Date()+" "+"Thread"+id+" "+"Sale "+n+"copies "+cdlist[type].name+"\n");
			System.out.print(cdlist[type].name+" Current Storage: "+cdlist[type].num+"\n");
			
			l.unlock();
			//启动其他线程
			try {
				//买完后正在执行的线程主动放弃CPU让其他线程执行
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}




//CD的类
class CD_pro{
	String name;
	int num=10;
	
	//租借进程
	synchronized void rent(int id,CD_pro cd,Logger lg) {
		//要借的CD没了
		while(this.num==0) {
			//lg.warning(name+"'s stock is not enough now\n"+name+" wait others to return \n");
			System.out.print(name+"isn't avaliable\n");
			System.out.print(name+" wait others to return \n");
			notifyAll();
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//借到了
		this.num = 0;
		//lg.info(new Date()+"Thread"+id+" "+"Rent "+name+"\n");
		System.out.print(new Date()+" "+"Thread"+id+" "+"Rent "+name+"\n");
		Random time = new Random();
		try {
			//随机等候200~300ms然后归还
			notifyAll();
			wait(time.nextInt(100)+200);
			//Thread.sleep(time.nextInt(100)+200);
			this.num = 1;//归还CD
			//lg.info(name+" has been returned by"+" Thread"+id+"\n");
			System.out.print(new Date()+" "+name+" has been returned by"+" Thread"+id+"\n");
			notifyAll();//唤醒其他想要借同一张CD的线程
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//启动其他线程
		try {
			//买完后正在执行的线程主动放弃CPU让其他线程执行
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

//进货线程
//每次补齐可售CD列表
class GetInThread_pro extends Thread{
	Lock l;
	//控制CD
	CD_pro []cdlist;
	CD_collect cc;
	Logger lg;
	Condition salecondition;
	Condition getincondition;
	//构造函数
	GetInThread_pro(CD_collect cc, Lock l, Condition getincondition, Condition salecondition){
		this.salecondition = salecondition;
		this.getincondition = getincondition;
		this.l = l;
		this.cc = cc;
		this.setDaemon(true);//一直处于循环状态的线程一定要设置为daemon否则主线程无法结束
	}
	
	//同步的是当前的CD列表对象
	public void run(){
		while(true) {
			//lg.info("GetIn Start:\n");
			System.out.print(new Date()+" "+"GetIn Start:\n");
			try {
				cc.getin();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

//销售线程
class saleThread_pro extends Thread{
		Lock l;
		Condition salecondition;
		Condition getincondition;
		GetInThread_pro gt;
		//销售CD列表
		CD_pro [] cd_saled;
		CD_collect cc;
		//区分不同的线程
		static int ids;
		int id;
		Logger lg;
		//构造函数
		saleThread_pro(CD_collect cc,GetInThread_pro gt,Logger lg,Lock l,Condition getincondition,Condition salecondition){
			this.salecondition = salecondition;
			this.getincondition = getincondition;
			this.l = l;
			this.gt = gt;
			this.cc = cc;
			this.setDaemon(true);//一直处于循环状态的线程一定要设置为daemon否则主线程无法结束
			id = ++ids;
			this.lg = lg;
		}
		//防止里面的代码拼错了
		//保证不会有书写错误
		@Override
		public void run() {
			//种类
			Random t = new Random();
			//数量
			Random r = new Random();
			while(true) {
				try {
					Thread.sleep(r.nextInt(200));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//200以内的休眠时间
				
				int saletype = t.nextInt(10);//销售十以内的随机种类
				int salenum = r.nextInt(5)+1;//销售五以内的随机数量
				
					//id为x的销售线程尝试销售某种CD多少张
					//lg.info("saledThread"+id+" Try Sale "+cc.cdlist[saletype].name+" "+salenum+"\n");
					System.out.print("saledThread"+id+" Try Sale "+cc.cdlist[saletype].name+" "+salenum+"\n");
					//要买的CD的库存不够
					if(cc.cdlist[saletype].num<salenum) {
						//随机选择等候还是放弃
						if(r.nextBoolean()) {
							cc.sale(salenum, id,saletype,gt);
						}else {
							//lg.info("saledThread"+id+" give up"+" "+cc.cdlist[saletype].name+"\n");
							System.out.print("saledThread"+id+" give up"+" "+cc.cdlist[saletype].name+"\n");
						}
					}else {
						//库存充足，可以购买
						cc.sale(salenum, id,saletype,gt);
					}
					
				}
		}
		
}

//租借进程
//租借的是单张，因此不采用CD集合的方式同步
class RentThread extends Thread{
	CD_pro []cdrentlist;
	//区分不同的线程
	static int ids;
	int id;
	Logger lg;
	//构造函数
	RentThread(CD_pro[] cd,Logger lg){
		this.cdrentlist = cd;
		this.setDaemon(true);//一直处于循环状态的线程一定要设置为daemon否则主线程无法结束
		id = ++ids;
		this.lg = lg;
	}

	@Override
	public void run() {
		//种类
		Random t = new Random();
		//时间
		Random r = new Random();
		while(true) {
			//启动时间为200ms以内的随机数
			try {
				Thread.sleep(r.nextInt(200));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//200以内的休眠时间
			
			int renttype = t.nextInt(10)+1;//销售十以内的随机种类
			//获取到同步对象
			synchronized(cdrentlist[renttype-1]) {
				//id为x的销售线程尝试销售某种CD多少张
				//lg.info("RentThread"+id+" Try Rent "+cdrentlist[renttype-1].name+"\n");
				System.out.print("RentThread"+id+" Try Rent "+cdrentlist[renttype-1].name+"\n");
				//要买的CD的库存不够
				if(cdrentlist[renttype-1].num==0) {
					//随机选择等候还是放弃
					if(r.nextBoolean()) {
						cdrentlist[renttype-1].rent(id,cdrentlist[renttype-1],lg);
					}else {
						//lg.info("RentThread"+id+" give up"+" "+cdrentlist[renttype-1].name+"\n");
						System.out.print("RentThread"+id+" give up"+" "+cdrentlist[renttype-1].name+"\n");
					}
				}else {
					//库存充足，可以租借
					cdrentlist[renttype-1].rent(id,cdrentlist[renttype-1],lg);
				}
				
			}
		}
	}
}

//控制所有线程的启动和运行
class ControlThread_pro extends Thread{
		//控制CD
		CD_pro []cd_saled;
		CD_pro []cdrentlist;
		CD_collect cc;
		Logger lg;
		Lock l;
		Condition getincondition;
		Condition salecondition;
		
		ControlThread_pro(CD_collect cc,CD_pro []cdrentlist,Logger lg,Lock l,Condition getincondition,Condition salecondition){
			this.cc = cc; 
			this.cdrentlist =cdrentlist;
			this.lg = lg;
			this.l = l;
			this.salecondition = salecondition;
			this.getincondition = getincondition;
		}
		
		@Override
		//去启动其他所有的线程
		public void run() {
			//直接启动进货线程
			GetInThread_pro gt= new GetInThread_pro(cc,l,getincondition,salecondition);
			gt.start();
			//启动两个或两个以上的销售线程
			new saleThread_pro(cc,gt,lg,l,getincondition,salecondition).start();
			new saleThread_pro(cc,gt,lg,l,getincondition,salecondition).start();
			new saleThread_pro(cc,gt,lg,l,getincondition,salecondition).start();
			new saleThread_pro(cc,gt,lg,l,getincondition,salecondition).start();
			
			//租借进程
			new RentThread(cdrentlist,lg).start();
			new RentThread(cdrentlist,lg).start();
			new RentThread(cdrentlist,lg).start();
			new RentThread(cdrentlist,lg).start();
			
			//控制线程睡眠
			//控制线程结束则所有线程结束
			try {
				Thread.sleep(120*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}


