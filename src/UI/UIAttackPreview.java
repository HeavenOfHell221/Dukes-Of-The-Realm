package UI;

import java.io.Serializable;

import Duke.Actor;
import DukesOfTheRealm.Castle;
import Interface.IUI;
import Interface.IUpdate;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import Utility.Settings;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public final class UIAttackPreview extends Parent implements IUpdate, Serializable, IUI
{
	private transient Rectangle background;
	private Button buttonAttack;
	
	private Castle currentCastle;
	private Actor currentActor;
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	public UIAttackPreview()
	{
		this.background = new Rectangle(280, 300);
		this.buttonAttack = new Button();
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void start()
	{
		addAllNodes();
		relocateAllNodes();
		setAllButtons();
		setBackground();
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{

	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
	
	private void setAllButtons()
	{
		this.buttonAttack
				.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('/images/egyptian-temple.png'); "
						+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");

		this.buttonAttack.setOnMousePressed(event -> System.out.println("attack!"));
	
		addEventMouse(this.buttonAttack);
	}
	
	private void addEventMouse(final Button b)
	{
		b.setMinSize(64, 64);
		b.setMaxSize(64, 64);
		b.setCursor(Cursor.HAND);
		b.addEventHandler(MouseEvent.MOUSE_ENTERED, event ->
		{
			final Bloom bloom = new Bloom();
			bloom.setThreshold(0.85);
			bloom.setInput(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 15, 0.25, 0, 0));
			b.setEffect(bloom);
		});

		b.addEventHandler(MouseEvent.MOUSE_EXITED, event ->
		{
			b.setEffect(null);
		});

		b.setOnMouseClicked(event ->
		{
			b.setEffect(null);
			final Bloom bloom = new Bloom();
			bloom.setThreshold(0.85);
			bloom.setInput(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 15, 0.25, 0, 0));
			b.setEffect(bloom);
		});
	}
	
	private void setBackground()
	{
		final Stop[] stops = new Stop[]
		{
				new Stop(0, Color.WHITE), new Stop(1, Color.WHITE)
		};
		final LinearGradient lg2 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		this.background.setStroke(lg2);

		this.background.setStrokeWidth(3);
		this.background.setArcHeight(60);
		this.background.setArcWidth(60);
	}
	
	@Override
	public void addNode(final Node node)
	{
		getChildren().add(node);
	}

	@Override
	public void addAllNodes()
	{
		addNode(this.background);
		addNode(this.buttonAttack);
	}

	@Override
	public void relocateAllNodes()
	{
		final int i = 90;
		final int offset = 540;

		final float margin = (float) (Settings.MARGIN_PERCENTAGE) + 0.076f;

		relocate(this.buttonAttack, Settings.SCENE_WIDTH * margin + 1, offset + 190);
		relocate(this.background, Settings.SCENE_WIDTH * margin - 17, offset - 22);
	}

	@Override
	public void setAllVisible(final boolean visible)
	{
		setVisible(this.background, visible);
		setVisible(this.buttonAttack, visible);
	}

	@Override
	public void switchCastle(final Castle castle, final Actor actor, final boolean productionVisible, final boolean attackVisible)
	{
		this.currentCastle = castle;
		this.currentActor = actor;
		setAllVisible(attackVisible);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
}
