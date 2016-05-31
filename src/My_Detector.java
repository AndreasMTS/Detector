
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;


public class My_Detector implements PlugInFilter 
{
    public int setup(String arg, ImagePlus imp) 
    {
    	return DOES_8G;
    }
        
    public void run(ImageProcessor original) 
    {
    	ImageProcessor copy = original.duplicate();
    	int w = copy.getWidth();
        int h = copy.getHeight();
        int noo = 0;		//Number of objects detected
        int count = 0;		//counts numbers of runs during pixel detection algorithm
        int elements=0;		//counts the number of found pixel for every object
        int storepos=0; 	//
        int x=0, xx=0, y=0, yy=0;
        int[] storex = new int[w];
        int[] storey = new int[h];		//Test comment
        
        for(int initx=0; initx<w; initx++)
        {
        	storex[initx]=0;
        }
        for(int inity=0; inity<h; inity++)
        {
        	storex[inity]=0;
        }
        
		for (y = 0; y < h; y++)
		{			
			for (x = 0; x < w; x++)
			{
				if (copy.getPixel(x, y) == 0)
				{
					xx=x;
					yy=y;
					noo++;
					elements++;
					copy.putPixel(x, y, 255);
					System.out.printf("Objekt #%d: Position:(%d|%d)   ", noo,x,y);
					
					do{	
						for (int findx=x-1; findx<=x+1; findx++)
						{
							for (int findy=y-1; findy<=y+1; findy++)
							{
								count++;
								if (copy.getPixel(findx, findy) == 0)
								{
									storepos++;
									storex[storepos]=findx;
									storey[storepos]=findy;
									
									elements++;
									copy.putPixel(x, y, 255);								
									
									x=findx;
									y=findy;
								}
								if (count==9 && storepos>0)
								{
									count=0;
									x=storex[storepos];
									y=storey[storepos];
									storepos=0;
								}
							}
						}
					}while(count<=8);
					
				}
				else
				{
					if(elements>0)
					{
						x=xx;
						y=yy;
						System.out.printf("%d Pixel\n", elements);
					}
					elements=0;
				}
			}
		}
		System.out.printf("\nGefundene Objekte: %d\n\n\n", noo);
    }
}	//END








//UEBUNGEN:
/*


	//Filterprogramm mit einstellbaren Filter

    public void run(ImageProcessor original) 
    {
    	//ImagePlus img = IJ.openImage("C:/blume.tif"); //Wie automatisch Datei einlesen???!
        //ImageProcessor original = img.getProcessor(); 
        //byte[] bild = (byte[])original.getPixels();
    	
    	
    	// show text in status bar
  		IJ.showStatus("Punktezähler von Stefan Erben");
  		// set value of progress bar
  		IJ.showProgress(0.0);
  		// display error message
  		IJ.error("Welchen Filter wollen Sie anwenden? (3x3, 5x5) ?");
  		IJ.showProgress(0.5);
   		// display text input dialog
   		int var = 0;
   		var = (int) IJ.getNumber("Ungerade Zahl eingeben", var);
    	
    	int w = original.getWidth();
        int h = original.getHeight();
        ImageProcessor copy = original.duplicate();
          
		for (int x = 0; x <= w; x++) 
		{
						
			for (int y = 0; y <= h; y++) 
			{
				int sum = 0;

				int i, j, filter;
				filter = (var - 1) / 2;
				for (j=-filter; j<=filter; j++)
				{
					for (i=-filter; i<=filter; i++)
					{
						int p = copy.getPixel(x+j, y+i);
						sum = sum + p;
					}
				}
				int q = sum / (var*var);
				original.putPixel(x, y, q);
	         }
         }
		IJ.showProgress(0.9);
		IJ.showMessage("Der" ,var+ "er Filter wird jetzt angewendet!");
		IJ.showProgress(1.0);
              
      }



// Ein- und Ausgabe Fenster, Zahlt Punkte und kreist sie grau ein, Prozessladebalken
public void run(ImageProcessor original) 
    {
    	// show text in status bar
  		IJ.showStatus("Punktezähler von Stefan Erben");
  		// set value of progress bar
  		IJ.showProgress(0.0);
  		// display error message
  		IJ.error("Wollen Sie wirklich alle Punkte zählen?");
  		IJ.showProgress(0.5);
  		IJ.error("Sind Sie sich wirklich sicher?");
   		// display text input dialog
   		String name = IJ.getString("Geben Sie Ihren Namen ein: ","Name");
   		
    	
    	int w = original.getWidth();
        int h = original.getHeight();
        int r = w / 33; 					//Rahmenbreite
        int zaehler = 0;
        ImageProcessor copy = original.duplicate();
          
		for (int x = 0; x <= w; x++) 
		{
						
			for (int y = 0; y <= h; y++) 
			{
				int p = copy.getPixel(x, y);
				
				if (x <= r || x >= w-r || y <= r || y >= h-r)		//Macht einen Rahmen, Breite = 1/20 der Bildbreite
				{
					original.putPixel(x, y, 0);
				}
				else if (p < 10)
				{
					original.setValue (150);
					original.drawOval((x-5), (y-5), 10, 10);
					zaehler++;
				}
				
				//int p = original.getPixel(x, y);
				//original.putPixel((x-h), y, p);
				
				
             }
         }
		IJ.showProgress(0.9);
		IJ.showMessage(name+ ", das Ergebnis lautet wie folgt:");
		IJ.showMessage("Ingesamt wurden" ,zaehler+ " Punkte gefunden!");
		IJ.showProgress(1.0);
		
                
      }
      
      







// INVERTER
public void run(ImageProcessor original) 
{
    int w = original.getWidth();
    int h = original.getHeight();
    
    
	for (int x = 0; x <= w; x++) 
	{
		for (int y = 0; y <= h; y++) 
		{
			int p = original.getPixel(x,y);
			original.putPixel(x, y, (255-p));
          
         }
     }
            
  }


//Binaerwandler

public void run(ImageProcessor original) 
{
    int w = original.getWidth();
    int h = original.getHeight();
    
    
	for (int x = 0; x <= w; x++) 
	{
		for (int y = 0; y <= h; y++) 
		{
			int p = original.getPixel(x,y);
			if (p <105)				//weis oder schwarz		
			{
				original.putPixel(x, y, 255);
			}
			else
			{
				original.putPixel(x, y, 0);

			}
          
         }
     }
            
 }
  
  
  
// einzelne schwarze Pixel mit grauem Kreis einkreisen
 	
 	public void run(ImageProcessor original) 
    {
        int w = original.getWidth();
        int h = original.getHeight();
          
		for (int x = 0; x <= w; x++) 
		{
			for (int y = 0; y <= h; y++) 
			{
				int p = original.getPixel(x, y);
				if (p < 10)
				{
					original.setValue (150);
					original.drawOval((x-5), (y-5), 10, 10);
				}
				
				//int p = original.getPixel(x, y);
				//original.putPixel((x-h), y, p);
				
				
             }
         }
                
      }

*/