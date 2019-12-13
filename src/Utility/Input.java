package Utility;

import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.SPACE;

import java.util.BitSet;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Utilisateur
 *
 */
public class Input
{
	/**
	 *
	 */
	private final BitSet keyboardBitSet = new BitSet();

	/**
	 *
	 */
	private Scene scene = null;

	/**
	 *
	 * @param scene
	 */
	public Input(final Scene scene)
	{
		this.scene = scene;
	}

	/**
	 *
	 */
	public void addListeners()
	{
		this.scene.addEventFilter(KeyEvent.KEY_PRESSED, this.keyPressedEventHandler);
		this.scene.addEventFilter(KeyEvent.KEY_RELEASED, this.keyReleasedEventHandler);
	}

	/**
	 *
	 */
	public void removeListeners()
	{
		this.scene.removeEventFilter(KeyEvent.KEY_PRESSED, this.keyPressedEventHandler);
		this.scene.removeEventFilter(KeyEvent.KEY_RELEASED, this.keyReleasedEventHandler);
	}

	/**
	 *
	 */
	private final EventHandler<KeyEvent> keyPressedEventHandler = event ->
	{
		// register key down
		this.keyboardBitSet.set(event.getCode().ordinal(), true);
		event.consume();
	};

	/**
	 *
	 */
	private final EventHandler<KeyEvent> keyReleasedEventHandler = event ->
	{
		// register key up
		this.keyboardBitSet.set(event.getCode().ordinal(), false);
		event.consume();
	};

	/**
	 *
	 * @param  key
	 * @return
	 */
	private boolean is(final KeyCode key)
	{
		return this.keyboardBitSet.get(key.ordinal());
	}

	/**
	 *
	 * @return
	 */
	public boolean isExit()
	{
		return is(ESCAPE);
	}

	/**
	 *
	 * @return
	 */
	public boolean isSpace()
	{
		return is(SPACE);
	}
}
