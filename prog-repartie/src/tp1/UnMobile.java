package tp1;

import java.awt.Graphics;

import javax.swing.JPanel;

public class UnMobile extends JPanel implements Runnable{
	int saLargeur, saHauteur, sonDebutDessin;
	final int sonPas= 10, sonTemps= 500, sonCote= 40;
	
	UnMobile( int telleLargeur, int telleHauteur)
	{ 
		super();
		saLargeur= telleLargeur;
		saHauteur= telleHauteur;
		setSize( telleLargeur, telleHauteur );
	}//UnMobile
	
	public void run()
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
			
	}
	public void paintComponent( Graphics telContexteGraphique)
	{ 
		super.paintComponent( telContexteGraphique );
		telContexteGraphique.fillRect( this.sonDebutDessin, saHauteur/2, sonCote, sonCote);
	}
}
