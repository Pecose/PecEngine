package display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

public class Panel extends JPanel{  
	private static final long serialVersionUID = -696807925661491890L;
	public Frame frame;
	
	private boolean proportional; // lorsque cette variable est � true, l'adaptation respecte le rapport hauteur/largeur voulu du dessin, � l'inverse lorsqu'elle est � false, la largeur et la hauteur sont adapt�e � la dimension correspondante sans respecter le rapport hauteur/largeur du dessin
	private boolean adaptable; // lorsque cette variable est � true l'�chelle du dessin est adapt�e � la taille du panel, sinon le dessin est fait dans les dimensions voulues du dessin
	private final Dimension drawsize; // la taille voulue du dessin � l'�chelle 1:1
	private EventListenerList eventlisteners;
	
	public Dimension getDrawsize(){ return drawsize; }
	
    /** @param pecEngine Objet cr�� par l'utilisateur pour d�finir le code d'affichage */
	public Panel(PecEngine pecEngine){
		MouseEventInterceptor mouseEventInterceptor = new MouseEventInterceptor(this);
		super.addMouseListener(mouseEventInterceptor);
		super.addMouseMotionListener(mouseEventInterceptor);
		super.addMouseWheelListener(mouseEventInterceptor);
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run(){ Panel.this.frame = new Frame(pecEngine, Panel.this); }
		});
		this.eventlisteners = new EventListenerList();
		this.proportional=true; // adaptation proportionnelle par d�faut
		this.adaptable=true; // adaptation par d�faut
		this.drawsize = new Dimension(Screen.getWidth(), Screen.getHeight());
		this.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10,this.getBackground()));
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(300, 300));
	}
 
    /** @param proportional true pour adaptation proportionelle, false sinon */
	public void setProportional(boolean proportional) {
		boolean old = this.proportional;
		this.proportional=proportional;
		if ( old!=proportional ) { // si la valeur change
			firePropertyChange("PROPORTIONAL", old, proportional); // on envoie un �v�nement pour indiquer que la propri�t� a chang�
			repaint(); // on redessine pour tenir compte du changement
		}
	}
 
    /** return true si l'adaptation est actuellement proportionnelle, ou false sinon */
	public boolean isProportional() {
		return proportional;
	}
 
    /** @param adaptable true pour adaptation, false sinon */
	public void setAdaptable(boolean adaptable) {
		boolean old = this.adaptable;
		this.adaptable=adaptable;
		if ( old!=adaptable ) { // si la valeur change
			firePropertyChange("ADAPTABLE", old, adaptable); // on envoie un �v�nement pour indiquer que la propri�t� a chang�
			repaint(); // on redessine pour tenir compte du changement
		}
	}
 
    /** return true si l'adaptation est actuellement active, ou false sinon */
	public boolean isAdaptable() { return adaptable; }
 
    /** D�termine la taille actuelle du panel sans les bordures */
	public Dimension getInnerDimension(Dimension dimension) {
		Insets insets=getInsets();
		return new Dimension(Math.max(0, dimension.width-insets.left-insets.right), 
				Math.max(0, dimension.height-insets.top-insets.bottom));
	}
 
    /** Dessine le fond du composant � l'aide du painter */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
 
		// on cr�� un nouveau contexte graphique pour �viter de modifier 
		// le contexte principal et d'avoir � le restaurer 
		// (comme on va changer l'�chelle, on ne risquera pas d'impacter 
		// le reste �ventuel du dessin de composants par Swing)		
		Graphics2D g2D = (Graphics2D) g.create();
		
       	// on tient compte du d�calage d� � la bordure du composant (composant Border)
		Insets insets=getInsets();
		// on compose avec une translation pour se placer en haut � gauche de l'int�rieur des bordures
		g2D.transform(AffineTransform.getTranslateInstance(insets.left, insets.top)); 
		
		// si l'adapdation est active
		if(adaptable) adapt(g2D); // on adapte
		this.frame.pecEngine.display(this, g2D); // on provoque le dessin en invoquant le painter
		this.repaint();
		g2D.dispose(); // on lib�re les ressources cr��es pour le contexte graphique
 
	}
 
    /**
     *  Adapte l'�chelle du dessin en tenant compte des dimensions voulues 
     *  et des dimensions actuelles du panel, et des dimensions pr�f�rentielles
     */ 
	private void adapt(Graphics2D g2d) {
                // adapte de fa�on proportionnelle le dessin de la taille voulue � la taille pr�f�rentielle, en �liminant les bordures �ventuelles (sinon on se superposerait aux bordures)
		proportionalAffineTransform(g2d, getInnerDimension(getDrawsize()), getInnerDimension(getPreferredSize()));
		if(proportional) { // si l'adaptation est proportionnelle, on s'adapte proportionnellement � la taille actuelle du composant
			adaptProportional(g2d); // adaptation proportionnelle
		}else{
			affineTransform(g2d, getInnerDimension(getDrawsize()), getInnerDimension(getSize())); // on adapte la largeur et la hauteur ind�pendamment (non proportionelle )
		}
	}
 
    /** adaptation proportionnelle de la taille du dessin � la taille du composant */
	private void adaptProportional(Graphics2D g2d) {
		proportionalAffineTransform(g2d, getInnerDimension(getDrawsize()), getInnerDimension(getSize()));
	}
 
    /** d�termine la transformation d'�chelle n�cessaire pour qu'un composant
     *  de dimension fromsize � l'�chelle 1 se dessine � la dimension tosize, 
     *  sans respecter le rapport largeur/hauteur voulu
     */
	private void affineTransform(Graphics2D g2d, Dimension fromsize, Dimension tosize) {
		double sx = tosize.getWidth()/fromsize.getWidth(); // le facteur d'�chelle horizontal est le rapport entre la largeur cible et la largeur d'origine
		double sy = tosize.getHeight()/fromsize.getHeight(); // le facteur d'�chelle vertical est le rapport entre la hauteur cible et la hauteur d'origine
		g2d.transform(AffineTransform.getScaleInstance(sx, sy)); // on cr�� une transform�e affine d'�chelle et on la compose avec la transform�e actuelle affect�e au contexte graphique
	}
 
	/**
	 * d�termine la transformation d'�chelle n�cessaire pour qu'un composant de dimension fromsize 
	 * � l'�chelle 1 se dessine � la dimension tosize, en respectant le rapport largeur/hauteur voulu
     */
	private void proportionalAffineTransform(Graphics2D g2d, Dimension fromsize, Dimension tosize) {
		double sx = tosize.getWidth()/fromsize.getWidth(); // le facteur d'�chelle horizontal est le rapport entre la largeur cible et la largeur d'origine
		double height = fromsize.getHeight()*sx; // on calcule la hauteur proportionnelle avec le facteur d'�chelle horizontal
		if ( height > tosize.getHeight() ) { // si la hauteur transform�e est trop grande pour la hauteur cible, on adapte verticalement
			sx = tosize.getHeight()/fromsize.getHeight(); // le facteur d'�chelle vertical est le rapport entre la hauteur cible et la hauteur d'origine (pour que le dessin ne d�passe pas la hauteur)
		}
		g2d.transform(AffineTransform.getScaleInstance(sx, sx)); // on applique un changement d'�chelle de m�me facteur horizontal et vertical pour respecter le rapport largeur/hauteur voulu
	}

	//Liste des getters ------------------------------------------------
	@Override
	public synchronized MouseListener[] getMouseListeners() {
		return eventlisteners.getListeners(MouseListener.class);
	}
 
	@Override
	public synchronized MouseMotionListener[] getMouseMotionListeners() {
		return eventlisteners.getListeners(MouseMotionListener.class);
	}
	
	@Override
	public synchronized MouseWheelListener[] getMouseWheelListeners() {
		return eventlisteners.getListeners(MouseWheelListener.class);
	} 
 
	//Liste des adders ------------------------------------------------
	@Override
	public synchronized void addMouseListener(MouseListener l) {
		if(l == null) return;
		eventlisteners.add(MouseListener.class, l);
	}
	
	@Override
	public synchronized void addMouseWheelListener(MouseWheelListener l) {
		if(l == null) return;
		eventlisteners.add(MouseWheelListener.class, l);
	}
 
	@Override
	public synchronized void addMouseMotionListener(MouseMotionListener l) {
		if(l == null) return;
		eventlisteners.add(MouseMotionListener.class, l);
	}
 
	//Liste des removers ------------------------------------------------
	@Override
	public synchronized void removeMouseListener(MouseListener l) {
		if(l == null) return;
		eventlisteners.remove(MouseListener.class, l);
	}

	@Override
	public synchronized void removeMouseMotionListener(MouseMotionListener l) {
		if(l == null) return;
		eventlisteners.remove(MouseMotionListener.class, l);
	}
 
	@Override
	public synchronized void removeMouseWheelListener(MouseWheelListener l) {
		if(l == null) return;
		eventlisteners.remove(MouseWheelListener.class, l);
	}
	
}

