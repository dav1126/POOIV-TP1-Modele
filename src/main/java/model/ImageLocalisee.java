package model;

import javafx.scene.image.Image;

public class ImageLocalisee extends Image{
	
	private final String url;
	
	public ImageLocalisee(String url) {
        
		super(url);
        this.url = url;
    }

    public String getURL() {
        
    	return url;
    }
}
