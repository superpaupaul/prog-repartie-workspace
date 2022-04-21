package tp3;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.util.concurrent.ThreadLocalRandom;

public class UnMobile extends JPanel implements Runnable{
	int saLargeur, saHauteur, sonDebutDessin;
	final int sonPas= 2, sonTemps= ThreadLocalRandom.current().nextInt(0, 15), sonCote= 40;
	public static semaphoreGeneral sem = new semaphoreGeneral(3);
	
	UnMobile( int telleLargeur, int telleHauteur)
	{ 
		super();
		saLargeur= telleLargeur;
		saHauteur= telleHauteur;
		setSize( telleLargeur, telleHauteur );
	}//UnMobile
	
	public void run()
	{ 
		int criticStart = this.saLargeur / 3;
		int criticStop = criticStart * 2;
		this.setBackground(Color.GREEN);
		while(true) {
			for(sonDebutDessin= 0;sonDebutDessin < criticStart - this.sonPas; sonDebutDessin += sonPas)
			{
				repaint();
				try
				{ 
					Thread.sleep(this.sonTemps);
				}
				catch(InterruptedException telleExcp)
				{
					telleExcp.printStackTrace();
				}
			}
			
			this.setBackground(Color.YELLOW);
			sem.syncWait();
			this.setBackground(Color.RED);
			while(sonDebutDessin < criticStop - sonPas)
			{
				repaint();
				try
				{
					Thread.sleep(this.sonTemps);
				}
				catch(InterruptedException telleExcp)
				{
					telleExcp.printStackTrace();
				}
				sonDebutDessin += sonPas;
			}
			
			sem.syncSignal();
		
			this.setBackground(Color.GREEN);
			while(sonDebutDessin <= saLargeur)
			{
				repaint();
				try
				{
					Thread.sleep(this.sonTemps);
				}
				catch(InterruptedException telleExcp)
				{
					telleExcp.printStackTrace();
				}
				sonDebutDessin += sonPas;
			}
			
			// retour 
			
			while(sonDebutDessin >= criticStop)
			{
				repaint();
				try
				{
					Thread.sleep(this.sonTemps);
				}
				catch(InterruptedException telleExcp)
				{
					telleExcp.printStackTrace();
				}
				sonDebutDessin -= sonPas;
			}
			
			this.setBackground(Color.YELLOW);
			sem.syncWait();
			this.setBackground(Color.RED);
			while(sonDebutDessin >= criticStart)
			{
				
				repaint();
				try
				{
					Thread.sleep(this.sonTemps);
				}
				catch(InterruptedException telleExcp)
				{
					telleExcp.printStackTrace();
				}
				sonDebutDessin -= sonPas;
			}
			sem.syncSignal();
			
			this.setBackground(Color.GREEN);
			
			while(sonDebutDessin >= 0)
			{
				repaint();
				try
				{
					Thread.sleep(this.sonTemps);
				}
				catch(InterruptedException telleExcp)
				{
					telleExcp.printStackTrace();
				}
				sonDebutDessin -= sonPas;
			}
		}
		
		
			
	}
	public void paintComponent( Graphics telContexteGraphique)
	{ 
		super.paintComponent( telContexteGraphique );
		telContexteGraphique.fillRect( this.sonDebutDessin, saHauteur/2, sonCote, sonCote);
	}
}
