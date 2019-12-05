package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.function.BiConsumer;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
/**
 * Cette classe permet de dessiner dans le fond du panel en adaptant le dessin à la taille du panel
 */
public class ScaleAdaptablePanel extends JPanel {
 
	private boolean proportional; // lorsque cette variable est à true, l'adaptation respecte le rapport hauteur/largeur voulu du dessin, à l'inverse lorsqu'elle est à false, la largeur et la hauteur sont adaptée à la dimension correspondante sans respecter le rapport hauteur/largeur du dessin
	private boolean adaptable; // lorsque cette variable est à true l'échelle du dessin est adaptée à la taille du panel, sinon le dessin est fait dans les dimensions voulues du dessin
	private final BiConsumer<Graphics2D,ImageObserver> painter; // le composant chargé du dessin
	private final Dimension drawsize; // la taille voulue du dessin à l'échelle 1:1
 
        /**
          * @param painter le composant chargé du dessin 
          * @param width la largeur du dessin à l'échelle 1
          * @param height la hauteur du dessin à l'échelle 1
          */
	public ScaleAdaptablePanel(BiConsumer<Graphics2D,ImageObserver> painter, int width, int height) {
		this.painter=painter;
		this.proportional=true; // adaptation proportionnelle par défaut
		this.adaptable=true; // adaptation par défaut
		this.drawsize = new Dimension(width, height);
	}
 
        /**
          * @param proportional true pour adaptation proportionelle, false sinon
          */
	public void setProportional(boolean proportional) {
		boolean old = this.proportional;
		this.proportional=proportional;
		if ( old!=proportional ) { // si la valeur change
			firePropertyChange("PROPORTIONAL", old, proportional); // on envoie un événement pour indiquer que la propriété a changé
			repaint(); // on redessine pour tenir compte du changement
		}
	}
 
        /**
          * return true si l'adaptation est actuellement proportionnelle, ou false sinon 
          */
	public boolean isProportional() {
		return proportional;
	}
 
        /**
          * @param adaptable true pour adaptation, false sinon
          */
	public void setAdaptable(boolean adaptable) {
		boolean old = this.adaptable;
		this.adaptable=adaptable;
		if ( old!=adaptable ) { // si la valeur change
			firePropertyChange("ADAPTABLE", old, adaptable); // on envoie un événement pour indiquer que la propriété a changé
			repaint(); // on redessine pour tenir compte du changement
		}
	}
 
        /**
          * return true si l'adaptation est actuellement active, ou false sinon 
          */
	public boolean isAdaptable() {
		return adaptable;
	}
 
        /**
          * Détermine la taille actuelle du panel sans les bordures
          */
	private Dimension getInnerDimension(Dimension dimension) {
		Insets insets=getInsets();
		return new Dimension(Math.max(0, dimension.width-insets.left-insets.right), 
				Math.max(0, dimension.height-insets.top-insets.bottom));
	}
 
        /**
          * Dessine le fond du composant à l'aider du painter
          */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
 
		if (painter!=null){ // si un painter a été fourni
 
			Graphics2D g2D = (Graphics2D) g.create(); // on créé un nouveau contexte graphique pour éviter de modifier le contexte principal et d'avoir à le restaurer (comme on va changer l'échelle, on ne risquera pas d'impacter le reste éventuel du dessin de composants par Swing)		
 
                        // on tient compte du décalage dû à la bordure du composant (composant Border)
			Insets insets=getInsets();
			g2D.transform(AffineTransform.getTranslateInstance(insets.left, insets.top)); // on compose avec une translation pour se placer en haut à gauche de l'intérieur des bordures
 
			// si l'adapdation est active
			if(adaptable) adapt(g2D); // on adapte

			painter.accept(g2D,this); // on provoque le dessin en invoquant le painter
 
			g2D.dispose(); // on libère les ressources créées pour le contexte graphique
 
		}
 
	}
 
        /**
          * Adapte l'échelle du dessin en tenant compte des dimensions voulues et des dimensions actuelles du panel, et des dimensions préférentielles
          */ 
	private void adapt(Graphics2D g2d) {
                // adapte de façon proportionnelle le dessin de la taille voulue à la taille préférentielle, en éliminant les bordures éventuelles (sinon on se superposerait aux bordures)
		proportionalAffineTransform(g2d, getInnerDimension(drawsize), getInnerDimension(getPreferredSize()));
		if(proportional) { // si l'adaptation est proportionnelle, on s'adapte proportionnellement à la taille actuelle du composant
			adaptProportional(g2d); // adaptation proportionnelle
		}else{
			affineTransform(g2d, getInnerDimension(drawsize), getInnerDimension(getSize())); // on adapte la largeur et la hauteur indépendamment (non proportionelle )
		}
	}
 
        /**
           * adaptation proportionnelle de la taille du dessin à la taille du composant
           */
	private void adaptProportional(Graphics2D g2d) {
		proportionalAffineTransform(g2d, getInnerDimension(drawsize), getInnerDimension(getSize()));
	}
 
        /**
           * détermine la transformation d'échelle nécessaire pour qu'un composant de dimension fromsize à l'échelle 1 se dessine à la dimension tosize, sans respecter le rapport largeur/hauteur voulu
           */
	private void affineTransform(Graphics2D g2d, Dimension fromsize, Dimension tosize) {
		double sx = tosize.getWidth()/fromsize.getWidth(); // le facteur d'échelle horizontal est le rapport entre la largeur cible et la largeur d'origine
		double sy = tosize.getHeight()/fromsize.getHeight(); // le facteur d'échelle vertical est le rapport entre la hauteur cible et la hauteur d'origine
		g2d.transform(AffineTransform.getScaleInstance(sx, sy)); // on créé une transformée affine d'échelle et on la compose avec la transformée actuelle affectée au contexte graphique
	}
 
        /**
           * détermine la transformation d'échelle nécessaire pour qu'un composant de dimension fromsize à l'échelle 1 se dessine à la dimension tosize, en respectant le rapport largeur/hauteur voulu
           */
	private void proportionalAffineTransform(Graphics2D g2d, Dimension fromsize, Dimension tosize) {
		double sx = tosize.getWidth()/fromsize.getWidth(); // le facteur d'échelle horizontal est le rapport entre la largeur cible et la largeur d'origine
		double height = fromsize.getHeight()*sx; // on calcule la hauteur proportionnelle avec le facteur d'échelle horizontal
		if ( height > tosize.getHeight() ) { // si la hauteur transformée est trop grande pour la hauteur cible, on adapte verticalement
			sx = tosize.getHeight()/fromsize.getHeight(); // le facteur d'échelle vertical est le rapport entre la hauteur cible et la hauteur d'origine (pour que le dessin ne dépasse pas la hauteur)
		}
		g2d.transform(AffineTransform.getScaleInstance(sx, sx)); // on applique un changement d'échelle de même facteur horizontal et vertical pour respecter le rapport largeur/hauteur voulu
	}
 
	// démo
 
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
		JPanel main = new JPanel(new BorderLayout());
 
                Dimension dimension = new Dimension(260,180); // dimensions de notre dessin (dimensions maximales de ce qu'on va dessiner)
		ScaleAdaptablePanel panel = new ScaleAdaptablePanel(ScaleAdaptablePanel::paint,dimension.width,dimension.height);
		panel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10,panel.getBackground()));
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(dimension); // on donne comme taille préférentielle la taille de notre dessin (sans adaptation, on veut que le layout permette de voir tout ce qu'on dessine). on n'a pas besoin de tenir compte des bordures parce que le composant ScaleAdaptablePanel adapte toujours le dessin à l'intérieur des bordures
		main.add(panel);
 
		JPanel ui = new JPanel(new GridBagLayout());
 
		JCheckBox checkboxProportional = new JCheckBox("Adaptation proportionelle");
		checkboxProportional.setSelected(panel.isProportional());
		checkboxProportional.addActionListener(e->panel.setProportional(checkboxProportional.isSelected()));
		ui.add(checkboxProportional);
 
		JCheckBox checkboxAdaptable = new JCheckBox("Adaptation");
		checkboxAdaptable.setSelected(panel.isAdaptable());
		checkboxAdaptable.addActionListener(e->panel.setAdaptable(checkboxAdaptable.isSelected()));
		ui.add(checkboxAdaptable);
 
		main.add(ui, BorderLayout.SOUTH);
 
		frame.add(main);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
 
	}
 
	private static Image image = loadImage("suricate.jpg");
	private static Image loadImage(String name) {
		try {
			String path = System.getProperty("user.dir").toString() + "\\" + name;
			return ImageIO.read(new File(path));
		}catch(Throwable e){ return null; }
	}
	private static Font font = new Font("SansSerif",Font.PLAIN,40);
	private static void paint(Graphics2D g, ImageObserver observable) {
		/* // pour debug : dessine un cadre autour du dessin
                g.setColor(Color.LIGHT_GRAY);
		g.drawRect(0,0,240,160);*/
		g.setColor(Color.BLUE);
		g.setFont(font);
		g.drawString("Hello", 20,30);
		g.setColor(Color.RED);
		g.drawRect(120, 80, 100, 40);
		g.setColor(Color.ORANGE);
		g.fillOval(130, 20, 50, 50);
		if(image!=null) {
			g.drawImage(image,10,50,observable);
		}
	}
 
	// fin démo
 
}