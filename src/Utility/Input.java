package Utility;

import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.SPACE;

import java.util.BitSet;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Gere les inputs du clavier.
 */
public class Input
{
	private final BitSet keyboardBitSet = new BitSet();

	private Scene scene = null;

	public Input(final Scene scene)
	{
		this.scene = scene;
	}

	public void addListeners()
	{
		this.scene.addEventFilter(KeyEvent.KEY_PRESSED, this.keyPressedEventHandler);
		this.scene.addEventFilter(KeyEvent.KEY_RELEASED, this.keyReleasedEventHandler);
	}

	public void removeListeners()
	{
		this.scene.removeEventFilter(KeyEvent.KEY_PRESSED, this.keyPressedEventHandler);
		this.scene.removeEventFilter(KeyEvent.KEY_RELEASED, this.keyReleasedEventHandler);
	}

	private final EventHandler<KeyEvent> keyPressedEventHandler = event ->
	{
		// register key down
		this.keyboardBitSet.set(event.getCode().ordinal(), true);
		event.consume();
	};

	private final EventHandler<KeyEvent> keyReleasedEventHandler = event ->
	{
		// register key up
		this.keyboardBitSet.set(event.getCode().ordinal(), false);
		event.consume();
	};

	private boolean is(final KeyCode key)
	{
		return this.keyboardBitSet.get(key.ordinal());
	}

	public boolean isExit()
	{
		return is(ESCAPE);
	}

	public boolean isSpace()
	{
		return is(SPACE);
	}
}
