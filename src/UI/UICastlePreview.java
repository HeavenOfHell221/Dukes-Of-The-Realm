package UI;

import Interface.IUI;
import Interface.IUpdate;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public final class UICastlePreview implements IUpdate, IUI {

	
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/
	
	private ImageView imageKnight;
	private ImageView imagePiker;
	private ImageView imageOnager;
	private ImageView imageCoins;
	private ImageView imageLevel;
	private Text owner;
	private Text florinIncome;
	
	private Rectangle background;
	
	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/
	
	protected UICastlePreview() 
	{
		super();
		this.imageKnight = NewImageView("/images/mounted-knight-white.png");
		this.imagePiker = NewImageView("/images/spartan-white.png");
		this.imageOnager = NewImageView("/images/catapult-white.png");
		this.imageCoins = NewImageView("/images/coins2.png");
		this.imageLevel = NewImageView("/images/coins2.png");
		this.owner = new Text();
		this.florinIncome = new Text();
		this.background = new Rectangle();
	}
	
	/*************************************************/
	/********************* START *********************/
	/*************************************************/
	
	@Override
	public void Start() 
	{
		AddAllNodes();
		RelocateAllNodes();
	}
	
	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/



	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/
	
	private ImageView NewImageView(String path)
	{
		return new ImageView(new Image(getClass().getResource(path).toExternalForm(), 64, 64, false, true)); 
	}
	
	@Override
	public void RelocateAllNodes()
	{
		Relocate(this.imageKnight, 0, 0);
		Relocate(this.imageLevel, 0, 0);
		Relocate(this.imageCoins, 0, 0);
		Relocate(this.imageOnager, 0, 0);
		Relocate(this.imagePiker, 0, 0);
		Relocate(this.owner, 0, 0);
		Relocate(this.florinIncome, 0, 0);
		Relocate(this.background, 0, 0);
	}
	
	@Override
	public void AddAllNodes()
	{
		AddNode(this.imageKnight);
		AddNode(this.imageLevel);
		AddNode(this.imageCoins);
		AddNode(this.imageOnager);
		AddNode(this.imagePiker);
		AddNode(this.owner);
		AddNode(this.florinIncome);
		AddNode(this.background);
	}
	
	@Override
	public void Relocate(Node node, double x, double y)
	{
		node.relocate(x, y);
	}
	
	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
}
