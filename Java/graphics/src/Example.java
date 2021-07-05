import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application;
import javafx.beans.value.ChangeListener; 
import javafx.beans.value.ObservableValue; 
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.*;

// OK this is not best practice - maybe you'd like to create
// a volume data class?
// I won't give extra marks for that though.
/**
 * 
 * @author Joshua Matambo 986390
 *
 */
public class Example extends Application {
	short cthead[][][]; //store the 3D volume data set
	short min, max; //min/max value in the 3D volume data set
	int sliderNumber;
	
    @Override
    public void start(Stage stage) throws FileNotFoundException, IOException {
		stage.setTitle("CThead Viewer");
		

		ReadData();


		int width = 256;
        int height = 256;
		WritableImage medical_image = new WritableImage(width, height);
		ImageView imageView = new ImageView(medical_image); 
		

		WritableImage medical_image1 = new WritableImage(width, height);
		ImageView imageView1 = new ImageView(medical_image1); 
		
		WritableImage medical_image2 = new WritableImage(width, height);
		ImageView imageView2 = new ImageView(medical_image2);
		///
		WritableImage medical_image3 = new WritableImage(width, height);
		ImageView imageView3 = new ImageView(medical_image3); 
		

		WritableImage medical_image4 = new WritableImage(width, height);
		ImageView imageView4 = new ImageView(medical_image4); 
		
		WritableImage medical_image5 = new WritableImage(width, height);
		ImageView imageView5 = new ImageView(medical_image5);
		
		WritableImage resizeImage = new WritableImage(512,512); // double the size of the normal image.
		ImageView resizeImageView = new ImageView(resizeImage);


		Button mip_button=new Button("MIP"); //an example button to switch to MIP mode
		//sliders to step through the slices (z and y directions) (remember 113 slices in z direction 0-112)
		Slider zslider = new Slider(0, 112, 0);
		Slider yslider = new Slider(0, 255, 0);
		Slider xslider = new Slider(0, 255, 0);
		Button resize = new Button("Resize");
		Slider resizeSlider = new Slider(0,100,0); 
		Button refreshImage = new Button("refresh");
		
		// methods to create thumbnail images.
		resize(medical_image3);
		resize1(medical_image4);
		resize2(medical_image5);
		
		mip_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
				MIP(medical_image);
				MIP1(medical_image1);
				MIP2(medical_image2);
				
            }
        });

		refreshImage.setOnAction(new EventHandler<ActionEvent>(){
			@Override 
			public void handle(ActionEvent event) {
				System.out.println(sliderNumber);
				enlarge(resizeImage);
			}
		});
		
		resizeSlider.valueProperty().addListener( 
	            new ChangeListener<Number>() { 
					public void changed(ObservableValue <? extends Number >  
						observable, Number oldValue, Number newValue) 
	            {
						
					sliderNumber =  newValue.intValue();
	            }
		});
		resize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	FlowPane root1 = new FlowPane();
        		root1.setVgap(8);
                root1.setHgap(4);

                root1.getChildren().addAll(resizeImageView, resizeSlider, refreshImage);
                Stage stage1 = new Stage();
                Scene scene = new Scene(root1, 800, 400);
                stage1.setScene(scene);
                stage1.show();
				
            }
        });
		
		zslider.valueProperty().addListener( 
            new ChangeListener<Number>() { 
				public void changed(ObservableValue <? extends Number >  
					observable, Number oldValue, Number newValue) 
            { 
                System.out.println(newValue.intValue());
                PixelWriter image_writer = medical_image.getPixelWriter();
                int w=(int) medical_image.getWidth(), h=(int) medical_image.getHeight(), i, j, c;
                short datum;
                float col;
                for (j=0; j<h; j++) {
                	for (i=0; i<w; i++) {
							//at this point (i,j) is a single pixel in the image
							//here you would need to do something to (i,j) if the image size
							//does not match the slice size (e.g. during an image resizing operation
							//If you don't do this, your j,i could be outside the array bounds
							//In the framework, the image is 256x256 and the data set slices are 256x256
							//so I don't do anything - this also leaves you something to do for the assignment
							datum=cthead[newValue.intValue()][j][i]; //get values from slice 76 (change this in your assignment)
							//calculate the colour by performing a mapping from [min,max] -> [0,255]
							col=(((float)datum-(float)min)/((float)(max-min)));
							//System.out.println("max is "+ MIPmax);
							//System.out.println("The color is .."+col);
							for (c=0; c<3; c++) {
								//and now we are looping through the bgr components of the pixel
								//set the colour component c of pixel (i,j)
								image_writer.setColor(i, j, Color.color(col,col,col, 1.0));
								//					data[c+3*i+3*j*w]=(byte) col;
							} // colour loop
                	} // column loop
        	} // row loop
            } 
        }); 
		yslider.valueProperty().addListener( 
	            new ChangeListener<Number>() { 
					public void changed(ObservableValue <? extends Number >  
						observable, Number oldValue, Number newValue) 
	            { 
	                System.out.println(newValue.intValue());
	                PixelWriter image_writer = medical_image1.getPixelWriter();
	                int w=(int) medical_image1.getWidth(), h=(int) medical_image1.getHeight(), i, k, c;
	                short datum;
	                float col;
	                for (k=0; k<113; k++) {
	                	for (i=0; i<w; i++) {
								datum=cthead[k][newValue.intValue()][i]; //get values from slice 76 (change this in your assignment)
								//calculate the colour by performing a mapping from [min,max] -> [0,25
								col=(((float)datum-(float)min)/((float)(max-min)));
								for (c=0; c<3; c++) {
						 			//and now we are looping through the bgr components of the pixel
									//set the colour component c of pixel (i,j)
									image_writer.setColor(i, k, Color.color(col,col,col, 1.0));
									//					data[c+3*i+3*j*w]=(byte) col;
								} // colour loop
	                	} // column loop
	        	} // row loop
	            } 
	        }); 
		xslider.valueProperty().addListener( 
	            new ChangeListener<Number>() { 
					public void changed(ObservableValue <? extends Number >  
						observable, Number oldValue, Number newValue) 
	            { 
	                System.out.println(newValue.intValue());
	                PixelWriter image_writer = medical_image2.getPixelWriter();
	                
	                int w=(int) medical_image2.getWidth(), h=(int) medical_image2.getHeight(), j=0, k=0, c;
	                short datum;
	                float col;
					for (k=0; k<113; k++) {
						for (j=0; j<h; j++) {
							datum=cthead[k][j][newValue.intValue()]; 
							//calculate the colour by performing a mapping from [min,max] -> [0,255]
							col=(((float)datum-(float)min)/((float)(max-min)));
							for (c=0; c<3; c++) {
								//and now we are looping through the bgr components of the pixel
								//set the colour component c of pixel (i,j)
								image_writer.setColor(j, k, Color.color(col,col,col, 1.0));
							} // colour loop
						} // column loop
					} // row loop
					
	            } 
	        }); 
		FlowPane root = new FlowPane();
		root.setVgap(8);
        root.setHgap(4);
//https://examples.javacodegeeks.com/desktop-java/javafx/scene/image-scene/javafx-image-example/

        root.getChildren().addAll(imageView, imageView1, imageView2,imageView3, imageView4, imageView5, mip_button, zslider, yslider,xslider, resize);

        Scene scene = new Scene(root, 850, 560);
        stage.setScene(scene);
        stage.show();
    }
	
	//Function to read in the cthead data set
	public void ReadData() throws IOException {
		//File name is hardcoded here - much nicer to have a dialog to select it and capture the size from the user
		File file = new File("src//CThead");
		//Read the data quickly via a buffer (in C++ you can just do a single fread - I couldn't find if there is an equivalent in Java)
		DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
		
		int i, j, k; //loop through the 3D data set
		
		min=Short.MAX_VALUE; max=Short.MIN_VALUE; //set to extreme values
		short read; //value read in
		int b1, b2; //data is wrong Endian (check wikipedia) for Java so we need to swap the bytes around
		
		cthead = new short[113][256][256]; //allocate the memory - note this is fixed for this data set
		//loop through the data reading it in
		for (k=0; k<113; k++) {
			for (j=0; j<256; j++) {
				for (i=0; i<256; i++) {
					//because the Endianess is wrong, it needs to be read byte at a time and swapped
					b1=((int)in.readByte()) & 0xff; //the 0xff is because Java does not have unsigned types
					b2=((int)in.readByte()) & 0xff; //the 0xff is because Java does not have unsigned types
					read=(short)((b2<<8) | b1); //and swizzle the bytes around
					if (read<min) min=read; //update the minimum
					if (read>max) max=read; //update the maximum
					cthead[k][j][i]=read; //put the short into memory (in C++ you can replace all this code with one fread)
				}
			}
		}
		System.out.println(min+" "+max); //diagnostic - for CThead this should be -1117, 2248
		//(i.e. there are 3366 levels of grey (we are trying to display on 256 levels of grey)
		//therefore histogram equalization would be a good thing
	}

	
	 /*
        This function shows how to carry out an operation on an image.
        It obtains the dimensions of the image, and then loops through
        the image carrying out the copying of a slice of data into the
		image.
    */
    public void MIP(WritableImage image) {
            //Get image dimensions, and declare loop variables
            int w=(int) image.getWidth(), h=(int) image.getHeight(), i, j, c, k;
            PixelWriter image_writer = image.getPixelWriter();
            float MIPmax[][]=new float[257][257];
			float col;
			short datum;
			//make the maxium of each pixel zero;
			for(int p=0; p<256;p++) {
            	for(int n=0; n<256;n++) {
            		MIPmax[p][w]=0;
            	}
            	
            }
            for (j=0; j<h; j++) {
                for (i=0; i<w; i++) {
                    	for(k=0;k<113;k++) {
							datum=cthead[k][j][i]; 
							col=(((float)datum-(float)min)/((float)(max-min)));
							if (col>MIPmax[j][i]) {
								MIPmax[j][i]=col;
							}
                    	}// loop slices
						for (c=0; c<3; c++) {
									//and now we are looping through the bgr components of the pixel
									//set the colour component c of pixel (i,j)
								image_writer.setColor(i, j, Color.color(MIPmax[j][i],MIPmax[j][i],MIPmax[j][i], 1.0));
						} // colour loop
                    } // column loop
           } // row loop
    }
    
    public void MIP1(WritableImage image) {
        //Get image dimensions, and declare loop variables
        int w=(int) image.getWidth(), h=(int) image.getHeight(), i, j, c, k;
        PixelWriter image_writer = image.getPixelWriter();
        float MIPmax[][]=new float[257][257];
		float col;
		short datum;
		//make the maxium of each pixel zero;
		for(int p=0; p<256;p++) {
        	for(int n=0; n<256;n++) {
        		MIPmax[p][w]=0;
        	}
        	
        }
		for (i=0; i<w; i++) {
        		for (k=0; k<113; k++) {
                		for(j=0;j<h;j++) {
							datum=cthead[k][j][i]; //get values from slice 76 (change this in your assignment)
							//calculate the colour by performing a mapping from [min,max] -> [0,255]
							col=(((float)datum-(float)min)/((float)(max-min)));
							if (col>MIPmax[i][k]) {
								MIPmax[i][k]=col;
							}
                		}// loop slices
						for (c=0; c<3; c++) {
								image_writer.setColor(i, k, Color.color(MIPmax[i][k],MIPmax[i][k],MIPmax[i][k], 1.0));
						} // colour loop
                	} // column loop
        	} // row loop
    }
		
    public void MIP2(WritableImage image) {
        //Get image dimensions, and declare loop variables
        int w=(int) image.getWidth(), h=(int) image.getHeight(), i, j, c, k;
        PixelWriter image_writer = image.getPixelWriter();
        float MIPmax[][]=new float[257][257];
		float col;
		short datum;
		//make the maxium of each pixel zero;
		for(int p=0; p<256;p++) {
        	for(int n=0; n<256;n++) {
        		MIPmax[p][w]=0;
        	}
        	
        }
		for (j=0; j<h; j++) {
        		for (k=0; k<113; k++) {
                		for(i=0;i<w;i++) {
							datum=cthead[k][j][i]; //get values from slice 76 (change this in your assignment)
							//calculate the colour by performing a mapping from [min,max] -> [0,255]
							col=(((float)datum-(float)min)/((float)(max-min)));
							if (col>MIPmax[j][k]) {
								MIPmax[j][k]=col;
							}
                		}// loop slices
						for (c=0; c<3; c++) {
								image_writer.setColor(j, k, Color.color(MIPmax[j][k],MIPmax[j][k],MIPmax[j][k], 1.0));
						} // colour loop
                	} // column loop
        	} // row loop
    }
    

    public void resize(WritableImage image) {
    	int w=(int) image.getWidth(), h=(int) image.getHeight(), i, j, c, k=-1;
        PixelWriter image_writer = image.getPixelWriter();
        float x1,y1;
        int x,y;
        int Ya = h;
        int Yb = 32;
        int Xa = w;
        int Xb = 32;
        float col;
		short datum;
		for(int m = 0; m < 256; m=m+Yb) { // m keeps track of the position in the y axis
			for(int l=0; l < 256; l=l+Xb) { // l keeps  position in the x axis
				k=k+1; // counter to move to next slide
        		for (j=0; j<Yb; j++){ 
        			for (i = 0; i < Xb; i++) {
        				y1=j*(Ya/Yb);// <- make sure this is done using floats
        				x1=i*(Xa/Xb); //<- same
        				x= (int) Math.floor(x1);
        				y = (int) Math.floor(y1);
        				datum = cthead[10+k][y][x];
        				col=(((float)datum - (float)min) / ((float)(max - min)));
        				for (c = 0; c < 3; c++) {
        					image_writer.setColor((l+j), (m+i), Color.color(col,col,col, 1.0));
        				}
        			}	
        		}
			}
        }

    }
    public void resize2(WritableImage image) {
    	int w=(int) image.getWidth(), h=(int) image.getHeight(), i, j, c, k=-1;
        PixelWriter image_writer = image.getPixelWriter();
        float x1,y1;
        int x,y;
        int Ya = h;
        int Yb = 64;
        int Xa = w;
        int Xb = 32;
        float col;
		short datum;
		for(int m = 0; m < 256; m=m+32) { // m keeps track of the position in the y axis
			for(int l=0; l < 256; l=l+Xb) { // l keeps  position in the x axis
				k=k+1; // counter to move to next slide
        		for (j=0; j<Yb; j++){ 
        			for (i = 0; i < Xb; i++) {
        				y1=j*(Ya/Yb);// <- make sure this is done using floats
        				x1=i*(Xa/Xb); //<- same
        				x= (int) Math.floor(x1);
        				y = (int) Math.floor(y1);
        				if(y < 113) {
        					datum = cthead[y][x][76+k];
            				col=(((float)datum - (float)min) / ((float)(max - min)));
            				for (c = 0; c < 3; c++) {
            					image_writer.setColor((m+i),(l+j) , Color.color(col,col,col, 1.0));
            				}
        				}
        				
        			}	
        		}
			}
        }

    }
    public void resize1(WritableImage image) {
    	int w=(int) image.getWidth(), h=(int) image.getHeight(), i, j, c, k=-1;
        PixelWriter image_writer = image.getPixelWriter();
        float x1,y1;
        int x,y;
        int Ya = h;
        int Yb = 64;
        int Xa = w;
        int Xb = 32;
        float col;
		short datum;
		for(int m = 0; m < 256; m=m+32) { // m keeps track of the position in the y axis
			for(int l=0; l < 256; l=l+Xb) { // l keeps  position in the x axis
				k=k+1; // counter to move to next slide
        		for (j=0; j<Yb; j++){ 
        			for (i = 0; i < Xb; i++) {
        				y1=j*(Ya/Yb);// <- make sure this is done using floats
        				x1=i*(Xa/Xb); //<- same
        				x= (int) Math.floor(x1);
        				y = (int) Math.floor(y1);
        				if(y < 113) {// to avoid array out of bounds error
        					datum = cthead[y][76+k][x];
            				col=(((float)datum - (float)min) / ((float)(max - min)));
            				for (c = 0; c < 3; c++) {
            					image_writer.setColor((m+i),(l+j) , Color.color(col,col,col, 1.0));
            				}
        				}
        				
        			}	
        		}
			}
        }

    }
    /*
     * bilinear method for zooming in.
     */
    public void enlarge(WritableImage image) {
    	int i=0, j=0, c, k;
        PixelWriter image_writer = image.getPixelWriter();
        double x1,y1;
        int x,y;
        double Ya = 256;
        double slideNumber1 =sliderNumber;
        double Yb =256 + ((slideNumber1 / 100) *256);
        double Xa = 256;
        double Xb =256 + ((slideNumber1 / 100) *256);
        float col;
        float [][] imageArray = new float[(int) Yb+1][(int) Xb+1]; // create array of colors
		short datum;
		
		
		// make all values in imageArray 0
        for(int l = 0; l < Yb;l++) {
        	for(int m = 0; m < Xb; m++) {
        	   imageArray[l][m] = 0;
        	}
        }
		//below for loop makes white background.
		for(int m = 0; m < 512; m++) { // m keeps track of the position in the y axis
			for(int l=0; l < 512; l++) { // l keeps  position in the x axis
				for (c = 0; c < 3; c++) {
        			image_writer.setColor(m, l, Color.color(1,1,1, 1.0));
        		}
			}
		}
        for (j=0; j<Ya; j++){ 
        	for (i = 0; i < Xa; i++) {
    			y1=j*(Yb/Ya);// <- make sure this is done using floats
        		x1=i*(Xb/Xa); //<- same
        		x= (int) Math.floor(x1);
        		y = (int) Math.floor(y1);
        		//System.out.println("y is "+y+" x is "+x);
           		datum = cthead[76][j][i];
        		col=(((float)datum - (float)min) / ((float)(max - min)));
        		imageArray[y][x]=col; // add colour to y and x positions
        	}	
        }
        float colortemp =0; // store color of last image in array, 
        int tempX = 0; // store x position of last pixel in array,
        int tempY = 0; //store y position of last pixel in array,
        int nextX = 0; // store the x postion of the next pixel in array that is not -1.
        int nextY = 0; // store the y postion of the next pixel in array that is not -1.
        float colortempNext =0; // store the color of the next image.
        
        for(int l = 0; l < Yb-1;l++) {
        	for(int m = 0; m < Xb-1; m++) {
        		if (imageArray[l][m] != 0) {// store the values of a pixel
        			tempY=l;
        			tempX = m;
        			colortemp= imageArray[l][m];
        		}else {
        			for (int p = m; p < Xb-1; p++) {
        				if(imageArray[l][p] != 0) { // find the next pixel with a value
        					nextY=l;
        					nextX = p;
        					colortempNext = imageArray[l][p];
        					
        					float tempvar = (m-tempX)/(nextX -tempX); // use formula to find coordinates of pixel
        					imageArray[l][m] = colortemp + ((colortempNext - colortemp)*tempvar);
        				}
        			}
        		}
        	}
        }
        	for(int m = 0; m < Xb-1;m++) {
            	for(int c1 = 0; c1 < Yb-1; c1++) {
            		if (imageArray[c1][m] != 0) {
            			tempY=c1;
            			tempX = m;
            			colortemp= imageArray[c1][m];
            		}else {
            			for (int  p= c1; p < Yb-1; p++) {
            				if(imageArray[p][m] != 0) {
            					nextY=p;
            					nextX = m;
            					colortempNext = imageArray[c1][m];
            					
            					float tempvar = (c1-tempY)/(nextY -tempY);// the same as above but in the y direction
            					imageArray[c1][m] = colortemp + ((colortempNext - colortemp)*tempvar);
            				}	
            			}
            		}
            		for (c = 0; c < 3; c++) {
            			image_writer.setColor(c1, m, Color.color(imageArray[c1][m],imageArray[c1][m],imageArray[c1][m], 1.0));
            		}	
            	}
        	
        }
    }


    public static void main(String[] args) {
        launch();
    }
  
}