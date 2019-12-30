package UI;

import java.io.Serializable;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Main;
import Interface.IUI;
import Interface.IUpdate;
import Utility.Settings;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 */
public final class UIAttackPreview extends Parent implements IUpdate, Serializable, IUI
{
	/**
	 *
	 */
	private transient Rectangle background;

	/**
	 *
	 */
	private final Button buttonAttack;

	/**
	 *
	 */
	private final Button upKnight;

	/**
	 *
	 */
	private final Button upPiker;

	/**
	 *
	 */
	private final Button upOnager;

	/**
	 *
	 */
	private final Button downKnight;

	/**
	 *
	 */
	private final Button downPiker;

	/**
	 *
	 */
	private final Button downOnager;

	/**
	 *
	 */
	private final ImageView imageKnight;

	/**
	 *
	 */
	private final ImageView imagePiker;

	/**
	 *
	 */
	private final ImageView imageOnager;

	/**
	 *
	 */
	private final Text nbPikerText;

	/**
	 *
	 */
	private final Text nbOnagerText;

	/**
	 *
	 */
	private final Text nbKnightText;

	/**
	 *
	 */
	private int nbPiker;

	/**
	 *
	 */
	private int nbKnight;

	/**
	 *
	 */
	private int nbOnager;

	/**
	 *
	 */
	private Castle currentCastle;

	/**
	 *
	 */
	private Castle lastCastle;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 *
	 */
	public UIAttackPreview()
	{
		this.imageKnight = newImageView("/images/mounted-knight-white.png", 64, 64);
		this.imagePiker = newImageView("/images/spartan-white.png", 64, 64);
		this.imageOnager = newImageView("/images/catapult-white.png", 64, 64);
		this.background = new Rectangle(340, 350);
		this.buttonAttack = new Button();
		this.upKnight = new Button();
		this.upOnager = new Button();
		this.upPiker = new Button();
		this.downKnight = new Button();
		this.downOnager = new Button();
		this.downPiker = new Button();

		this.nbPikerText = new Text();
		this.nbKnightText = new Text();
		this.nbOnagerText = new Text();

		this.lastCastle = null;
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
		setAllTexts();
		setBackground();
		setAllVisible(false);
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		this.nbKnightText.setText(this.nbKnight + "");
		this.nbPikerText.setText(this.nbPiker + "");
		this.nbOnagerText.setText(this.nbOnager + "");
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 *
	 */
	private void setAllTexts()
	{
		setText(this.nbPikerText, 38);
		setText(this.nbKnightText, 38);
		setText(this.nbOnagerText, 38);
	}

	/**
	 *
	 * @param text
	 * @param font
	 */
	private void setText(final Text text, final int font)
	{
		text.setFont(new Font(font));
		text.setWrappingWidth(64);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		text.setText("0");
	}

	/**
	 *
	 */
	private void resetOst()
	{
		this.nbKnight = 0;
		this.nbOnager = 0;
		this.nbPiker = 0;
	}

	/**
	 *
	 * @param b
	 * @param url
	 */
	private void setStyle(final Button b, final String url)
	{
		b.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('" + url + "'); "
				+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");
		b.setMinSize(64, 64);
		b.setMaxSize(64, 64);
		b.setCursor(Cursor.HAND);
	}

	/**
	 *
	 */
	private void setAllButtons()
	{

		setStyle(this.buttonAttack, "images/wide.png");

		setStyle(this.upKnight, "images/upgrade.png");
		setStyle(this.upOnager, "images/upgrade.png");
		setStyle(this.upPiker, "images/upgrade.png");

		setStyle(this.downKnight, "images/downgrade.png");
		setStyle(this.downOnager, "images/downgrade.png");
		setStyle(this.downPiker, "images/downgrade.png");

		this.upKnight.setOnMousePressed(e ->
		{
			if (getNbKnights() > this.nbKnight)
			{
				this.nbKnight++;
			}
		});

		this.upKnight.setOnScroll(e ->
		{
			if (e.getDeltaY() > 0)
			{
				if (getNbKnights() > this.nbKnight)
				{
					this.nbKnight++;
				}
			}
		});

		this.upPiker.setOnMousePressed(e ->
		{
			if (getNbPikers() > this.nbPiker)
			{
				this.nbPiker++;
			}
		});
		this.upOnager.setOnMousePressed(e ->
		{
			if (getNbOnagers() > this.nbOnager)
			{
				this.nbOnager++;
			}
		});

		this.upPiker.setOnScroll(e ->
		{
			if (e.getDeltaY() > 0)
			{
				if (getNbPikers() > this.nbPiker)
				{
					this.nbPiker++;
				}
			}
		});
		this.upOnager.setOnScroll(e ->
		{
			if (e.getDeltaY() > 0)
			{
				if (getNbOnagers() > this.nbOnager)
				{
					this.nbOnager++;
				}
			}
		});

		this.downKnight.setOnMousePressed(e ->
		{
			this.nbKnight = this.nbKnight > 0 ? this.nbKnight - 1 : this.nbKnight;
		});

		this.downPiker.setOnMousePressed(e ->
		{
			this.nbPiker = this.nbPiker > 0 ? this.nbPiker - 1 : this.nbPiker;
		});

		this.downOnager.setOnMousePressed(e ->
		{
			this.nbOnager = this.nbOnager > 0 ? this.nbOnager - 1 : this.nbOnager;
		});

		this.downKnight.setOnScroll(e ->
		{
			if (e.getDeltaY() < 0)
			{
				this.nbKnight = this.nbKnight > 0 ? this.nbKnight - 1 : this.nbKnight;
			}
		});

		this.downPiker.setOnScroll(e ->
		{
			if (e.getDeltaY() < 0)
			{
				this.nbPiker = this.nbPiker > 0 ? this.nbPiker - 1 : this.nbPiker;
			}
		});

		this.downOnager.setOnScroll(e ->
		{
			if (e.getDeltaY() < 0)
			{
				this.nbOnager = this.nbOnager > 0 ? this.nbOnager - 1 : this.nbOnager;
			}
		});

		this.buttonAttack.setOnMousePressed(e ->
		{
			this.lastCastle.createOst(this.currentCastle, this.nbPiker, this.nbKnight, this.nbOnager, false);
			reset();
		});
	}

	/**
	 *
	 */
	private void reset()
	{
		this.nbKnight = 0;
		this.nbOnager = 0;
		this.nbPiker = 0;
		setAllVisible(false);
		Main.pause = false;
	}

	/**
	 *
	 */
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
		addNode(this.upKnight);
		addNode(this.upOnager);
		addNode(this.upPiker);
		addNode(this.downOnager);
		addNode(this.downKnight);
		addNode(this.downPiker);
		addNode(this.imageKnight);
		addNode(this.imageOnager);
		addNode(this.imagePiker);
		addNode(this.nbKnightText);
		addNode(this.nbPikerText);
		addNode(this.nbOnagerText);
	}

	@Override
	public void relocateAllNodes()
	{
		final int i = 69;
		final int offset = 540;

		final float margin = (float) Settings.MARGIN_PERCENTAGE + 0.052f;

		relocate(this.imagePiker, Settings.SCENE_WIDTH * margin + 20, 20 + offset + i * 0);
		relocate(this.imageKnight, Settings.SCENE_WIDTH * margin + 20, 20 + offset + i * 1);
		relocate(this.imageOnager, Settings.SCENE_WIDTH * margin + 20, 20 + offset + i * 2);

		relocate(this.upPiker, Settings.SCENE_WIDTH * margin + 20 + i, 20 + offset + i * 0);
		relocate(this.upKnight, Settings.SCENE_WIDTH * margin + 20 + i, 20 + offset + i * 1);
		relocate(this.upOnager, Settings.SCENE_WIDTH * margin + 20 + i, 20 + offset + i * 2);

		relocate(this.nbPikerText, Settings.SCENE_WIDTH * margin + 20 + i * 2, 45 + offset + i * 0);
		relocate(this.nbKnightText, Settings.SCENE_WIDTH * margin + 20 + i * 2, 45 + offset + i * 1);
		relocate(this.nbOnagerText, Settings.SCENE_WIDTH * margin + 20 + i * 2, 45 + offset + i * 2);

		relocate(this.downPiker, Settings.SCENE_WIDTH * margin + 20 + i * 3, 20 + offset + i * 0);
		relocate(this.downKnight, Settings.SCENE_WIDTH * margin + 20 + i * 3, 20 + offset + i * 1);
		relocate(this.downOnager, Settings.SCENE_WIDTH * margin + 20 + i * 3, 20 + offset + i * 2);

		relocate(this.buttonAttack, Settings.SCENE_WIDTH * margin + 30 + i * 1.5, offset + 250);

		relocate(this.background, Settings.SCENE_WIDTH * margin, offset);
	}

	@Override
	public void setAllVisible(final boolean visible)
	{
		setVisible(this.background, visible);
		setVisible(this.buttonAttack, visible);
		setVisible(this.upKnight, visible);
		setVisible(this.upOnager, visible);
		setVisible(this.upPiker, visible);
		setVisible(this.downKnight, visible);
		setVisible(this.downOnager, visible);
		setVisible(this.downPiker, visible);
		setVisible(this.imageKnight, visible);
		setVisible(this.imagePiker, visible);
		setVisible(this.imageOnager, visible);
		setVisible(this.nbKnightText, visible);
		setVisible(this.nbOnagerText, visible);
		setVisible(this.nbPikerText, visible);
		resetOst();
	}

	/**
	 *
	 * @param castle
	 * @param attackVisible
	 */
	public void switchCastle(final Castle castle, final boolean attackVisible)
	{
		this.lastCastle = this.currentCastle;
		this.currentCastle = castle;

		setAllVisible(attackVisible);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @return
	 * @see    DukesOfTheRealm.Castle#getNbPikers()
	 */
	public int getNbPikers()
	{
		return this.lastCastle.getNbPikers();
	}

	/**
	 * @return
	 * @see    DukesOfTheRealm.Castle#getNbKnights()
	 */
	public int getNbKnights()
	{
		return this.lastCastle.getNbKnights();
	}

	/**
	 * @return
	 * @see    DukesOfTheRealm.Castle#getNbOnagers()
	 */
	public int getNbOnagers()
	{
		return this.lastCastle.getNbOnagers();
	}
}
