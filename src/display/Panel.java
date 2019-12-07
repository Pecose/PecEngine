package display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import listeners.KeyPressedListener;
import listeners.KeyReleasedListener;
import listeners.KeyTypedListener;
import listeners.Listeners;
import listeners.MouseClickedListener;
import listeners.MouseDraggedListener;
import listeners.MouseEnteredListener;
import listeners.MouseExitedListener;
import listeners.MouseMovedListener;
import listeners.MousePressedListener;
import listeners.MouseReleasedListener;
import listeners.MouseWheelMovedListener;

public class Panel extends JPanel{  
	private static final long serialVersionUID = -696807925661491890L;
	public Frame frame;
	
	private boolean proportional; // lorsque cette variable est à true, l'adaptation respecte le rapport hauteur/largeur voulu du dessin, à l'inverse lorsqu'elle est à false, la largeur et la hauteur sont adaptée à la dimension correspondante sans respecter le rapport hauteur/largeur du dessin
	private boolean adaptable; // lorsque cette variable est à true l'échelle du dessin est adaptée à la taille du panel, sinon le dessin est fait dans les dimensions voulues du dessin
	private final Dimension drawsize; // la taille voulue du dessin à l'échelle 1:1
	private EventListenerList eventlisteners;
	
	public Dimension getDrawsize(){ return drawsize; }
	
    /** @param pecEngine Objet créé par l'utilisateur pour définir le code d'affichage */
	public Panel(PecEngine pecEngine){
		
		// MouseEventInterceptor a modifier par Listeners--------------------------------------------
//		MouseEventInterceptor mouseEventInterceptor = new MouseEventInterceptor(this);
//		super.addMouseListener(mouseEventInterceptor);
//		super.addMouseMotionListener(mouseEventInterceptor);
//		super.addMouseWheelListener(mouseEventInterceptor);
		//-------------------------------------------------------------------------------------------
		
		Listeners listeners = new Listeners(this);
		super.addMouseListener(listeners);
		super.addMouseMotionListener(listeners);
		super.addMouseWheelListener(listeners);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run(){ Panel.this.frame = new Frame(pecEngine, Panel.this); }
		});
		this.eventlisteners = new EventListenerList();
		this.proportional=true; // adaptation proportionnelle par défaut
		this.adaptable=true; // adaptation par défaut
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
			firePropertyChange("PROPORTIONAL", old, proportional); // on envoie un événement pour indiquer que la propriété a changé
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
			firePropertyChange("ADAPTABLE", old, adaptable); // on envoie un événement pour indiquer que la propriété a changé
			repaint(); // on redessine pour tenir compte du changement
		}
	}
 
    /** return true si l'adaptation est actuellement active, ou false sinon */
	public boolean isAdaptable() { return adaptable; }
 
    /** Détermine la taille actuelle du panel sans les bordures */
	public Dimension getInnerDimension(Dimension dimension) {
		Insets insets=getInsets();
		return new Dimension(Math.max(0, dimension.width-insets.left-insets.right), 
				Math.max(0, dimension.height-insets.top-insets.bottom));
	}
 
    /** Dessine le fond du composant à l'aide du painter */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
 
		// on créé un nouveau contexte graphique pour éviter de modifier 
		// le contexte principal et d'avoir à le restaurer 
		// (comme on va changer l'échelle, on ne risquera pas d'impacter 
		// le reste éventuel du dessin de composants par Swing)		
		Graphics2D g2D = (Graphics2D) g.create();
		
       	// on tient compte du décalage dû à la bordure du composant (composant Border)
		Insets insets=getInsets();
		// on compose avec une translation pour se placer en haut à gauche de l'intérieur des bordures
		g2D.transform(AffineTransform.getTranslateInstance(insets.left, insets.top)); 
		
		// si l'adapdation est active
		if(adaptable) adapt(g2D); // on adapte
		this.frame.pecEngine.display(this, g2D); // on provoque le dessin en invoquant le painter
		this.repaint();
		g2D.dispose(); // on libère les ressources créées pour le contexte graphique
 
	}
 
    /**
     *  Adapte l'échelle du dessin en tenant compte des dimensions voulues 
     *  et des dimensions actuelles du panel, et des dimensions préférentielles
     */ 
	private void adapt(Graphics2D g2d) {
                // adapte de façon proportionnelle le dessin de la taille voulue à la taille préférentielle, en éliminant les bordures éventuelles (sinon on se superposerait aux bordures)
		proportionalAffineTransform(g2d, getInnerDimension(getDrawsize()), getInnerDimension(getPreferredSize()));
		if(proportional) { // si l'adaptation est proportionnelle, on s'adapte proportionnellement à la taille actuelle du composant
			adaptProportional(g2d); // adaptation proportionnelle
		}else{
			affineTransform(g2d, getInnerDimension(getDrawsize()), getInnerDimension(getSize())); // on adapte la largeur et la hauteur indépendamment (non proportionelle )
		}
	}
 
    /** adaptation proportionnelle de la taille du dessin à la taille du composant */
	private void adaptProportional(Graphics2D g2d) {
		proportionalAffineTransform(g2d, getInnerDimension(getDrawsize()), getInnerDimension(getSize()));
	}
 
    /** détermine la transformation d'échelle nécessaire pour qu'un composant
     *  de dimension fromsize à l'échelle 1 se dessine à la dimension tosize, 
     *  sans respecter le rapport largeur/hauteur voulu
     */
	private void affineTransform(Graphics2D g2d, Dimension fromsize, Dimension tosize) {
		double sx = tosize.getWidth()/fromsize.getWidth(); // le facteur d'échelle horizontal est le rapport entre la largeur cible et la largeur d'origine
		double sy = tosize.getHeight()/fromsize.getHeight(); // le facteur d'échelle vertical est le rapport entre la hauteur cible et la hauteur d'origine
		g2d.transform(AffineTransform.getScaleInstance(sx, sy)); // on créé une transformée affine d'échelle et on la compose avec la transformée actuelle affectée au contexte graphique
	}
 
	/**
	 * détermine la transformation d'échelle nécessaire pour qu'un composant de dimension fromsize 
	 * à l'échelle 1 se dessine à la dimension tosize, en respectant le rapport largeur/hauteur voulu
     */
	private void proportionalAffineTransform(Graphics2D g2d, Dimension fromsize, Dimension tosize) {
		double sx = tosize.getWidth()/fromsize.getWidth(); // le facteur d'échelle horizontal est le rapport entre la largeur cible et la largeur d'origine
		double height = fromsize.getHeight()*sx; // on calcule la hauteur proportionnelle avec le facteur d'échelle horizontal
		if ( height > tosize.getHeight() ) { // si la hauteur transformée est trop grande pour la hauteur cible, on adapte verticalement
			sx = tosize.getHeight()/fromsize.getHeight(); // le facteur d'échelle vertical est le rapport entre la hauteur cible et la hauteur d'origine (pour que le dessin ne dépasse pas la hauteur)
		}
		g2d.transform(AffineTransform.getScaleInstance(sx, sx)); // on applique un changement d'échelle de même facteur horizontal et vertical pour respecter le rapport largeur/hauteur voulu
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
	
	@Override
	public synchronized KeyListener[] getKeyListeners() {
		return eventlisteners.getListeners(KeyListener.class);
	} 
	
	public synchronized KeyPressedListener[] getKeyPressedListeners() {
		return eventlisteners.getListeners(KeyPressedListener.class);
	} 
	
	public synchronized KeyReleasedListener[] getKeyReleasedListeners() {
		return eventlisteners.getListeners(KeyReleasedListener.class);
	} 
	
	public synchronized KeyTypedListener[] getKeyTypedListeners() {
		return eventlisteners.getListeners(KeyTypedListener.class);
	} 
	
	public synchronized MouseClickedListener[] getMouseClickedListeners() {
		return eventlisteners.getListeners(MouseClickedListener.class);
	} 
	
	public synchronized MouseDraggedListener[] getMouseDraggedListeners() {
		return eventlisteners.getListeners(MouseDraggedListener.class);
	} 
	
	public synchronized MouseEnteredListener[] getMouseEnteredListeners() {
		return eventlisteners.getListeners(MouseEnteredListener.class);
	} 
	
	public synchronized MouseExitedListener[] getMouseExitedListeners() {
		return eventlisteners.getListeners(MouseExitedListener.class);
	} 
	
	public synchronized MouseMovedListener[] getMouseMovedListeners() {
		return eventlisteners.getListeners(MouseMovedListener.class);
	} 
	
	public synchronized MousePressedListener[] getMousePressedListeners() {
		return eventlisteners.getListeners(MousePressedListener.class);
	}
	
	public synchronized MouseReleasedListener[] getMouseReleasedListeners() {
		return eventlisteners.getListeners(MouseReleasedListener.class);
	}
	
	public synchronized MouseWheelMovedListener[] getMouseWheelMovedListeners() {
		return eventlisteners.getListeners(MouseWheelMovedListener.class);
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
 
	@Override
	public synchronized void addKeyListener(KeyListener l) {
		if(l == null) return;
		eventlisteners.add(KeyListener.class, l);
	}
	
	public synchronized void addKeyPressedListener(KeyPressedListener l) {
		if(l == null) return;
		eventlisteners.add(KeyPressedListener.class, l);
	}
	
	public synchronized void addKeyReleasedListener(KeyReleasedListener l) {
		if(l == null) return;
		eventlisteners.add(KeyReleasedListener.class, l);
	}
	
	public synchronized void addKeyTypedListener(KeyTypedListener l) {
		if(l == null) return;
		eventlisteners.add(KeyTypedListener.class, l);
	}
	
	public synchronized void addMouseClickedListener(MouseClickedListener l) {
		if(l == null) return;
		eventlisteners.add(MouseClickedListener.class, l);
	}
	
	public synchronized void addMouseDraggedListener(MouseDraggedListener l) {
		if(l == null) return;
		eventlisteners.add(MouseDraggedListener.class, l);
	}
	
	public synchronized void addMouseEnteredListener(MouseEnteredListener l) {
		if(l == null) return;
		eventlisteners.add(MouseEnteredListener.class, l);
	}
	
	public synchronized void addMouseExitedListener(MouseExitedListener l) {
		if(l == null) return;
		eventlisteners.add(MouseExitedListener.class, l);
	}
	
	public synchronized void addMouseMovedListener(MouseMovedListener l) {
		if(l == null) return;
		eventlisteners.add(MouseMovedListener.class, l);
	}
	
	public synchronized void addMousePressedListener(MousePressedListener l) {
		if(l == null) return;
		eventlisteners.add(MousePressedListener.class, l);
	}
	
	public synchronized void addMouseReleasedListener(MouseReleasedListener l) {
		if(l == null) return;
		eventlisteners.add(MouseReleasedListener.class, l);
	}
	
	public synchronized void addMouseWheelMovedListener(MouseWheelMovedListener l) {
		if(l == null) return;
		eventlisteners.add(MouseWheelMovedListener.class, l);
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
	
	@Override
	public synchronized void removeKeyListener(KeyListener l) {
		if(l == null) return;
		eventlisteners.remove(KeyListener.class, l);
	}
	
	public synchronized void removeKeyPressedListener(KeyPressedListener l) {
		if(l == null) return;
		eventlisteners.remove(KeyPressedListener.class, l);
	}
	
	public synchronized void removeKeyReleasedListener(KeyReleasedListener l) {
		if(l == null) return;
		eventlisteners.remove(KeyReleasedListener.class, l);
	}
	
	public synchronized void removeKeyTypedListener(KeyTypedListener l) {
		if(l == null) return;
		eventlisteners.remove(KeyTypedListener.class, l);
	}
	
	public synchronized void removeMouseClickedListener(MouseClickedListener l) {
		if(l == null) return;
		eventlisteners.remove(MouseClickedListener.class, l);
	}
	
	public synchronized void removeMouseDraggedListener(MouseDraggedListener l) {
		if(l == null) return;
		eventlisteners.remove(MouseDraggedListener.class, l);
	}
	
	public synchronized void removeMouseEnteredListener(MouseEnteredListener l) {
		if(l == null) return;
		eventlisteners.remove(MouseEnteredListener.class, l);
	}
	
	public synchronized void removeMouseExitedListener(MouseExitedListener l) {
		if(l == null) return;
		eventlisteners.remove(MouseExitedListener.class, l);
	}
	
	public synchronized void removeMouseMovedListener(MouseMovedListener l) {
		if(l == null) return;
		eventlisteners.remove(MouseMovedListener.class, l);
	}
	
	public synchronized void removeMousePressedListener(MousePressedListener l) {
		if(l == null) return;
		eventlisteners.remove(MousePressedListener.class, l);
	}
	
	public synchronized void removeMouseReleasedListener(MouseReleasedListener l) {
		if(l == null) return;
		eventlisteners.remove(MouseReleasedListener.class, l);
	}
	
	public synchronized void removeMouseWheelMovedListener(MouseWheelMovedListener l) {
		if(l == null) return;
		eventlisteners.remove(MouseWheelMovedListener.class, l);
	}
	
	
}

