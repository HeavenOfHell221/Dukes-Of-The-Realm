package UI;



import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Kingdom;
import Utility.Settings;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class CastleUI extends Parent{

	private Castle castle;
	private Text text;
	private Pane playfieldLayer;
	private Rectangle background;
	
	private static CastleUI instance = new CastleUI();
	
	private CastleUI()
	{	
		this.playfieldLayer = playfieldLayer;
		this.text = new Text();
		this.text.setFont(new Font(20));
		this.text.setWrappingWidth(200);
		this.text.setTextAlignment(TextAlignment.CENTER);
		this.text.setFill(Color.FLORALWHITE);
		
		this.background = new Rectangle(200, 0);
		this.background.setArcHeight(60);
		this.background.setArcWidth(60);
		
		this.background.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.1), 25);
		this.text.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.1), 50);
	}
	
	public void Update()
	{
		if(this.castle != null)
		{
			if(this.castle.GetActor() == Kingdom.GetPlayer())
			{
				this.text.setText(
					"Name : " + castle.GetActor().GetName() + "\n" + 
					"Level : " + this.castle.GetLevel() + "\n" +
					Settings.FLORIN_PER_SECOND*this.castle.GetLevel() + " Florin(s) / s" + "\n" +
					"Florin : " + (int)this.castle.GetTotalFlorin() + "\n\n" +
					"Piker : " + this.castle.GetReserveOfSoldiers().getNbPikers() + "\n" +
					"Knights : " + this.castle.GetReserveOfSoldiers().getNbKnights() + "\n" +
					"Onager : " + this.castle.GetReserveOfSoldiers().getNbOnagers() + "\n");
				this.background.setHeight(280);
			}
			else
			{
				this.text.setText(
						"Name : " + castle.GetActor().GetName() + "\n" + 
						"Level : " + this.castle.GetLevel());
				this.background.setHeight(100);
			}
		}
			
	}
	
	public void SwitchCastle(Castle castle)
	{
		this.castle = castle;
	}
	
	public static CastleUI GetInstance()
	{
		return instance;
	}
	
	public void SetCastle(Castle castle)
	{
		this.castle = castle;
	}
	
	public Castle GetCastle()
	{
		return this.castle;
	}
	
	public void SetPlayfieldLayer(Pane playfieldLayer)
	{
		this.playfieldLayer = playfieldLayer;
		this.playfieldLayer.getChildren().add(instance);
		instance.getChildren().add(this.background);
		instance.getChildren().add(this.text);
	}
	
	
}
