package display;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

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
	public static final int DEFAULT = 0;
	public static final int PROPORTIONAL = 1;
	public static final int ADAPTABLE = 2;
	
	private int displayOption = PROPORTIONAL;
	private final Dimension drawsize = new Dimension(Screen.getWidth(), Screen.getHeight());
	private EventListenerList eventlisteners = new EventListenerList();
	private Insets insets = getInsets();
	
	public int getDisplayOption() { return displayOption; }
	public void setDisplayOption(int displayOption) { this.displayOption = displayOption; }
	public Dimension getDrawsize(){ return drawsize; }
	
    /** @param pecEngine Objet créé par l'utilisateur pour définir le code d'affichage */
	public Panel(PecEngine pecEngine){
		
		Listeners listeners = new Listeners(this);
		super.addMouseListener(listeners);
		super.addMouseMotionListener(listeners);
		super.addMouseWheelListener(listeners);
		super.addKeyListener(listeners);

		this.setFocusable(true);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run(){ Panel.this.frame = new Frame(pecEngine, Panel.this); }
		});
		
		this.setPreferredSize(drawsize);
	}
 
    /** Détermine la taille actuelle du panel sans les bordures */
	public Dimension getInnerDimension(Dimension dimension) {
		return new Dimension(this.getInnerWidth(dimension), this.getInnerHeight(dimension));
	}
	
	/** Détermine la largueur actuelle du panel sans les bordures */
	private int getInnerWidth(Dimension dimension) {
		return Math.max(0, dimension.width-insets.left-insets.right);
	}
	
	/** Détermine la hauteur actuelle du panel sans les bordures */
	private int getInnerHeight(Dimension dimension) {
		return Math.max(0, dimension.height-insets.top-insets.bottom);
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
		adapt(g2D); // on adapte
		this.frame.pecEngine.display(this, g2D); // on provoque le dessin en invoquant le painter
		this.repaint();
		g2D.dispose(); // on libère les ressources créées pour le contexte graphique
 
	}
 
    /**
     *  Adapte l'échelle du dessin en tenant compte des dimensions voulues 
     *  et des dimensions actuelles du panel, et des dimensions préférentielles
     */ 
	private void adapt(Graphics2D g2d) {
        if(getDisplayOption() == PROPORTIONAL){
			// si l'adaptation est proportionnelle, on s'adapte proportionnellement à la taille actuelle du composant
        	g2d.transform(proportionalAffineTransform(getInnerDimension(getDrawsize()), getInnerDimension(getSize())));
		}else if(getDisplayOption() == ADAPTABLE) {
        	// adapte de façon proportionnelle le dessin de la taille voulue à la taille préférentielle, 
         	// en éliminant les bordures éventuelles (sinon on se superposerait aux bordures)
			g2d.transform(affineTransform(getInnerDimension(getDrawsize()), getInnerDimension(getSize()))); 
		}
	}
	
 
    /** détermine la transformation d'échelle nécessaire pour qu'un composant
     *  de dimension fromsize à l'échelle 1 se dessine à la dimension tosize, 
     *  sans respecter le rapport largeur/hauteur voulu
     * @return 
     */
	public AffineTransform affineTransform(Dimension fromsize, Dimension tosize) {
		double sx = tosize.getWidth()/fromsize.getWidth(); // le facteur d'échelle horizontal est le rapport entre la largeur cible et la largeur d'origine
		double sy = tosize.getHeight()/fromsize.getHeight(); // le facteur d'échelle vertical est le rapport entre la hauteur cible et la hauteur d'origine
		return AffineTransform.getScaleInstance(sx, sy); // on créé une transformée affine d'échelle et on la compose avec la transformée actuelle affectée au contexte graphique
	}
	
	/**
	 * détermine la transformation d'échelle nécessaire pour qu'un composant de dimension fromsize 
	 * à l'échelle 1 se dessine à la dimension tosize, en respectant le rapport largeur/hauteur voulu
	 * @return 
     */
	public AffineTransform proportionalAffineTransform(Dimension fromsize, Dimension tosize) {
		double sx = tosize.getWidth()/fromsize.getWidth(); // le facteur d'échelle horizontal est le rapport entre la largeur cible et la largeur d'origine
		double height = fromsize.getHeight()*sx; // on calcule la hauteur proportionnelle avec le facteur d'échelle horizontal
		if ( height > tosize.getHeight() ) { // si la hauteur transformée est trop grande pour la hauteur cible, on adapte verticalement
			sx = tosize.getHeight()/fromsize.getHeight(); // le facteur d'échelle vertical est le rapport entre la hauteur cible et la hauteur d'origine (pour que le dessin ne dépasse pas la hauteur)
		}
		return AffineTransform.getScaleInstance(sx, sx); // on applique un changement d'échelle de même facteur horizontal et vertical pour respecter le rapport largeur/hauteur voulu
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

