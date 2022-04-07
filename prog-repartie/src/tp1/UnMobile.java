package tp1;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

public class UnMobile extends JPanel implements Runnable{
	int saLargeur, saHauteur, sonDebutDessin;
	final int sonPas= 2, sonTemps= 50, sonCote= 40;
	
	UnMobile( int telleLargeur, int telleHauteur)
	{ 
		super();
		saLargeur= telleLargeur;
		saHauteur= telleHauteur;
		setSize( telleLargeur, telleHauteur );
	}//UnMobile
	
	public void run()
	{ 
		while(true)
		{
			for(sonDebutDessin= 0;sonDebutDessin < this.saLargeur - this.sonPas; sonDebutDessin += sonPas)
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
