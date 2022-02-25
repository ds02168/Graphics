package myPicture;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
//헤더파일 참조

public class MidPoint extends JFrame{
	private MyPanel panel = new MyPanel();//패널 객체 생성
	private JLabel la = new JLabel();//레이블 객체 생성
	public int i;
	private double dxy;//기울기
	private double m;//픽셀상 x의 최솟값
	private int x1,y1,x2,y2;//기하학적 x0,y0,x1,y1의 좌표값
	private int mx,my,mxy;//픽셀상 x1,y0,y1의 좌표값
	private int dx,dy,d;//픽셀상 x증가분,y의 증가분, 기울기
	private int incrE,incrNE;//각각 E와 NE픽셀 선택시
	private String rubber;//러버밴드 구현을 위해 구분 스트링
	public MidPoint() {
		setTitle("MidPoint Algorithm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
		Container c = getContentPane();
		c.setBackground(Color.WHITE);
		setLayout(null);
		
		la.setSize(1000,100);
		la.setLocation(100,500);
		
		c.add(la);
		c.addMouseListener(panel);
		c.addMouseMotionListener(panel);
		setSize(1200,600);
		setVisible(true);
	} //픽셀을 그릴 도화지를 생성
	
	class MyPanel extends JPanel implements MouseListener, MouseMotionListener{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.drawLine(1, 1, 1100, 1);
			g.drawLine(1, 1, 1, 500);
			g.drawLine(1100, 0, 1100, 500);
			for(i=1;i<11;i++)
			{
				g.drawLine(i*100, 0, i*100, 500);
				g.setColor(Color.RED);
				g.drawLine((i*100)-10, 50, (i*100)+10, 50);
				g.drawLine((i*100)-10, 150, (i*100)+10, 150);
				g.drawLine((i*100)-10, 250, (i*100)+10, 250);
				g.drawLine((i*100)-10, 350, (i*100)+10, 350);
				g.drawLine((i*100)-10, 450, (i*100)+10, 450);
				g.setColor(Color.BLACK);
			}
			for(i=1;i<6;i++)
			{
				g.drawLine(0, i*100, 1100, i*100);	
			} // 좌우 100픽셀씩 상하 100픽셀씩 간격으로 그리드 격자를 그림
			  //실제 100픽셀=가상의 모니터 1픽셀
			
			dxy = ((double)(500-y2)-(double)(500-y1))/((double)x2-(double)x1);//기울기 
			m=Math.ceil((double)x1/100)*100;//가상의 모니터상 x0좌표
			mx=(int)Math.floor((double)x2/100)*100;//가상의 모니터상 x1좌표
			my=(int)(Math.round((dxy*(m-(double)x1)+(double)y1)/100)*100);//가상의 모니터상 y0좌표
			mxy=(int)(Math.round((dxy*(mx-(double)x2)+(double)y2)/100)*100);//가상의 모니터상 y1좌표
			//가상의 모니터상 위치를 구함
			
			if("RELEASE".equals(rubber)) //버튼을 때면
				g.setColor(Color.RED); //빨간색
			else //드래그 중 일시
				g.setColor(Color.BLUE);//파란색

			g.drawLine((int)m, my, mx, mxy); //기하학적 선을 그림
			
			//드래그중일때와 버튼을 땟을때의 색을 달리 함으로써 러버밴딩 가시성 높임
			
			
			//화면에 (x0,y0), (x1,y1)좌표와 세부사항을 공지함
			la.setText("(x1, y1) : (" + (int)m + ", " + (500-my) +
					")  (x2, y2) : (" + mx + ", " + (500-mxy) + ")   " + "양끝점은 초록색 점, 러버밴딩: 드래그시 파란색");

			//MidPoint 알고리즘
			dx=mx-(int)m;
			dy=(500-mxy)-(500-my);
			d=dy*2-dx;
			incrE=dy*2;
			incrNE=(dy-dx)*2;
			
			//시작 과 끝점은 구분이 쉽게 초록색으로 구현
			g.setColor(Color.GREEN);
			if((m!=0)&&(mx!=0)) {
			g.fillOval((int)m-20, my-20 , 40, 40);
			g.fillOval((int)mx-20, mxy-20 , 40, 40);
			}
			
			if("RELEASE".equals(rubber)) //버튼을 때면
				g.setColor(Color.RED); //빨간색
			else //드래그 중 일시
				g.setColor(Color.BLUE);//파란색
			//드래그중일때와 버튼을 땟을때의 색을 달리 함으로써 러버밴딩 가시성 높임
			
			//MidPoint 알고리즘
			for(i=(int)m+100;i<mx;i+=100) { //실제 100픽셀 = 가상의 1픽셀
				if(d<=0) {
					d += incrE;
				} else {
					d += incrNE;
					my-=100;//자바 JPanel은 상단부터 하단까지 0부터 시작이므로 반대로 -100씩하여 위로 올라감
				}
				
				g.fillOval(i-20, my-20, 40, 40);
			}
			
		}
		
		public void mousePressed(MouseEvent e) { //마우스를 눌렀을때
			x1=e.getX(); 
			y1=e.getY(); //x0,y0의 좌표값을 받음
		}
		public void mouseReleased(MouseEvent e) { //마우스를 땟을때
			x2=e.getX();
			y2=e.getY();//x1,y1의 좌표값을 받음
			rubber = "RELEASE"; //RELEASE로 상태를 바꿈
			panel.repaint();//변경사항 최신화
		}
		public void mouseDragged(MouseEvent e) {//드래구 중일때 (러버밴딩)
			x2=e.getX();
			y2=e.getY();//x1,y1의 좌표값을 받음
			rubber = "DRAG";//DRAG로 상태를 바꿈
			panel.repaint();//변경사항 최신화
		}
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseMoved(MouseEvent e) {}
		//인터페이스 오버라이딩
		
		
	}
	
	
	
	public static void main(String[] args) {
		new MidPoint(); //MidPoint 객체 생성
	}

}
