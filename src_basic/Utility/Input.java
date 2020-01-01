package Utility;

import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.SPACE;

import java.util.BitSet;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * G�re les saisies clavier.
 */
public class Input
{
	/**
	 * Le Bitset servant � stocker une touche clavier.
	 */
	private final BitSet keyboardBitSet = new BitSet();

	/**
	 * La sc�ne dans laquelle les saisies clavier sont r�alis�es.
	 */
	private Scene scene = null;

	/**
	 * Met en place la sc�ne.
	 *
	 * @param scene La sc�ne dans laquelle les saisies clavier sont r�alis�es.
	 */
	public Input(final Scene scene)
	{
		this.scene = scene;
	}

	/**
	 * Ajoute des �v�nements d�tectant l'appui ou le rel�chement d'une touche du clavier.
	 */
	public void addListeners()
	{
		this.scene.addEventFilter(KeyEvent.KEY_PRESSED, this.keyPressedEventHandler);
		this.scene.addEventFilter(KeyEvent.KEY_RELEASED, this.keyReleasedEventHandler);
	}

	/**
	 * Enl�ve les �v�nements d�tectant l'appui ou le rel�chement d'une touche du clavier.
	 */
	public void removeListeners()
	{
		this.scene.removeEventFilter(KeyEvent.KEY_PRESSED, this.keyPressedEventHandler);
		this.scene.removeEventFilter(KeyEvent.KEY_RELEASED, this.keyReleasedEventHandler);
	}

	/**
	 * Ajoute une action lorsque une touche du clavier est press�e.
	 */
	private final EventHandler<KeyEvent> keyPressedEventHandler = event ->
	{
		// register key down
		this.keyboardBitSet.set(event.getCode().ordinal(), true);
		event.consume();
	};

	/**
	 * Ajoute une action lorsque une touche du clavier est rel�ch�e.
	 */
	private final EventHandler<KeyEvent> keyReleasedEventHandler = event ->
	{
		// register key up
		this.keyboardBitSet.set(event.getCode().ordinal(), false);
		event.consume();
	};

	/**
	 * V�rifie si une touche particuli�re du clavier est press�e.
	 *
	 * @param  key La touche � v�rifier.
	 * @return     true si la touche est bien press�e.
	 */
	private boolean is(final KeyCode key)
	{
		return this.keyboardBitSet.get(key.ordinal());
	}

	/**
	 * V�rifie si la touche ESCAPE du clavier est press�e.
	 *
	 * @return true si elle l'est.
	 */
	public boolean isExit()
	{
		return is(ESCAPE);
	}

	/**
	 * V�rifie si la touche SPACE du clavier est press�e.
	 *
	 * @return true si elle l'est.
	 */
	public boolean isSpace()
	{
		return is(SPACE);
	}
}
