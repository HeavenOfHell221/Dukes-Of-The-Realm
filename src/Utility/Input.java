package Utility;

import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.SPACE;

import java.util.BitSet;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Gère les saisies clavier.
 */
public class Input
{
	/**
	 * Le Bitset servant à stocker une touche clavier.
	 */
	private final BitSet keyboardBitSet = new BitSet();

	/**
	 * La scène dans laquelle les saisies clavier sont réalisées.
	 */
	private Scene scene = null;

	/**
	 * Met en place la scène.
	 * 
	 * @param scene La scène dans laquelle les saisies clavier sont réalisées.
	 */
	public Input(final Scene scene)
	{
		this.scene = scene;
	}

	/**
	 * Ajoute des évènements détectant l'appui ou le relâchement d'une touche du clavier.
	 */
	public void addListeners()
	{
		this.scene.addEventFilter(KeyEvent.KEY_PRESSED, this.keyPressedEventHandler);
		this.scene.addEventFilter(KeyEvent.KEY_RELEASED, this.keyReleasedEventHandler);
	}

	/**
	 * Enlève les évènements détectant l'appui ou le relâchement d'une touche du clavier.
	 */
	public void removeListeners()
	{
		this.scene.removeEventFilter(KeyEvent.KEY_PRESSED, this.keyPressedEventHandler);
		this.scene.removeEventFilter(KeyEvent.KEY_RELEASED, this.keyReleasedEventHandler);
	}

	/**
	 * Ajoute une action lorsque une touche du clavier est pressée.
	 */
	private final EventHandler<KeyEvent> keyPressedEventHandler = event ->
	{
		// register key down
		this.keyboardBitSet.set(event.getCode().ordinal(), true);
		event.consume();
	};

	/**
	 * Ajoute une action lorsque une touche du clavier est relâchée.
	 */
	private final EventHandler<KeyEvent> keyReleasedEventHandler = event ->
	{
		// register key up
		this.keyboardBitSet.set(event.getCode().ordinal(), false);
		event.consume();
	};

	/**
	 * Vérifie si une touche particulière du clavier est pressée.
	 * 
	 * @param  key La touche à vérifier.
	 * @return     true si la touche est bien pressée.
	 */
	private boolean is(final KeyCode key)
	{
		return this.keyboardBitSet.get(key.ordinal());
	}

	/**
	 * Vérifie si la touche ESCAPE du clavier est pressée.
	 * 
	 * @return true si elle l'est.
	 */
	public boolean isExit()
	{
		return is(ESCAPE);
	}

	/**
	 * Vérifie si la touche SPACE du clavier est pressée.
	 * 
	 * @return true si elle l'est.
	 */
	public boolean isSpace()
	{
		return is(SPACE);
	}
}
