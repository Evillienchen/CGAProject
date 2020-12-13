package engineTester;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

//import org.lwjgl.opengl.AMDNameGenDelete;
import org.lwjgl.opengl.Display;
//import org.lwjgl.opengl.Drawable;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		TextMaster.init(loader);
		int counter = 0;
		
		Boolean ate = false;
		
		
		
		
		FontType font = new FontType(loader.loadTexture("verdana"), new File("res/verdana.fnt"));
		GUIText text = new GUIText(counter+"",1,font, new Vector2f (0,0),1f,true);
		
		List<Integer> listOfFruits = new ArrayList<Integer>();
		
		RawModel model = OBJLoader.loadObjModel("box", loader);
		RawModel model2 = OBJLoader.loadObjModel("sphere", loader);
		//RawModel model3 = OBJLoader.loadObjModel("box", loader);
		
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("box")));
		TexturedModel fruitModel = new TexturedModel(model2,new ModelTexture(loader.loadTexture("ground_emit")));
		List<Entity> entities = new ArrayList<Entity>();
		
		Random random = new Random();
		
		/*for(int i=0;i<1;i++){
			entities.add(new Entity(fruitModel, new Vector3f(random.nextFloat()*700 - 400,3,random.nextFloat() * -500),0,0,0,3));
			//entities.get(i);
		}*/
		entities.add(new Entity(fruitModel, new Vector3f(random.nextFloat()*400 - 230,3,random.nextFloat() * -300),0,0,0,3));
		
		
		
		for(int i=0;i<300;i+=8){
			entities.add(new Entity(staticModel, new Vector3f(i,3f,0f),0,0,0,3));
			entities.add(new Entity(staticModel, new Vector3f(-i,3f,0f),0,0,0,3));
			entities.add(new Entity(staticModel,new Vector3f(300f,3f,-i),0,0,0,3));
			entities.add(new Entity(staticModel, new Vector3f (-300f,3f,-i),0,0,0,3));
			entities.add(new Entity(staticModel, new Vector3f (i,3f,-300f),0,0,0,3));
			entities.add(new Entity(staticModel, new Vector3f (-i,3f,-300f),0,0,0,3));
			
		}
		
		
		
		//System.out.println(entities.get(0).getPosition());
		
		
		Light light = new Light(new Vector3f(2000,2000,2000),new Vector3f(1,1,1));
		//List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
		//GuiTexture shadowMap = new GuiTexture(renderer.getShadowMaptexture(),new Vector2f(0.5f,0.5f), new Vector2f(0.5f,0.5f));
		//guiTextures.add(shadowMap);
		Terrain terrain = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(-1,-1,loader,new ModelTexture(loader.loadTexture("grass")));
		
		RawModel snakeModel = OBJLoader.loadObjModel("sphere",loader);
		TexturedModel snake = new TexturedModel(snakeModel, new ModelTexture(loader.loadTexture("snake")));
		
		Vector3f playerPosition = new Vector3f(100,5,-50);
		Player player = new Player(snake, playerPosition,0,180,3,5f);
		
		Camera camera = new Camera(player);
		
		MasterRenderer renderer = new MasterRenderer();

		
		while(!Display.isCloseRequested()){
			camera.move();
			//System.out.println(camera.getPosition());
			//System.out.println(player.getRotY()+camera.getAngleAroundPlayer());
			player.move();
			
			
			//renderer.renderShadowMap(entities, light);
			//System.out.println(player.getPosition());
			if(player.getPosition().z > 0 || player.getPosition().z <-300 || player.getPosition().x <-300 || player.getPosition().x > 300) {
				System.out.println("Du bist tot");
	
				entities.set(0,null);
				try {
					
					
					//ate = false;
					for(int j = listOfFruits.size()-1; j>=0;j--) {
						
						TimeUnit.SECONDS.sleep(1);
						
						entities.remove(listOfFruits.get(j));
						listOfFruits.remove(j); 
						
						player.setPosition(new Vector3f(100,5,-50));
						counter = 0;
						text.remove();
						text.setText(counter+""); 
						
						
					}
					
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				entities.set(0, new Entity(fruitModel, new Vector3f(random.nextFloat()*400 - 200,3,random.nextFloat() * -300),0,0,0,3));
				
				
				
				counter = 0;
			}
			
			
			
			if(entities.get(0).getPosition().getX()+8 >= player.getPosition().getX() && entities.get(0).getPosition().getX()-8 <= player.getPosition().getX() 
					&& entities.get(0).getPosition().getZ()+8 >= player.getPosition().getZ() && entities.get(0).getPosition().getZ()-8 <= player.getPosition().getZ()) {
				System.out.println("Ich habe die Kugel gefressen");
				entities.set(0, new Entity(fruitModel, new Vector3f(random.nextFloat()*400 - 230,3,random.nextFloat() * -230),0,0,0,3));
				ate = true;
				entities.add(new Entity(snake, new Vector3f(player.getPosition()),0,0,0,3));
				listOfFruits.add(entities.size()-1);
				counter += 10;
				text.remove();
				text.setText(counter+"");
				System.out.println(counter);
			}
			
			//System.out.println(player.getPosition());
			
			if(ate) {
				for(int i = 0; i<listOfFruits.size();i++) {
					entities.get(listOfFruits.get(i)).move(player, camera.getAngleAroundPlayer(),10*(1+i));
					if(entities.get(listOfFruits.get(i)).getPosition().z > 0 || entities.get(listOfFruits.get(i)).getPosition().z <-300 || entities.get(listOfFruits.get(i)).getPosition().x <-300 || entities.get(listOfFruits.get(i)).getPosition().x > 300) {
						entities.set(0,null);
						try {
							TimeUnit.SECONDS.sleep(1);
							player.setPosition(new Vector3f(100,5,-50));
							counter = 0;
							text.remove();
							text.setText(counter+"");
							
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
						entities.set(0, new Entity(fruitModel, new Vector3f(random.nextFloat()*400 - 200,3,random.nextFloat() * -300),0,0,0,3));
						
						counter = 0;
						
						ate = false;
						for(int j = listOfFruits.size()-1; j>=0;j--) {
							
							entities.remove(listOfFruits.get(j));
							listOfFruits.remove(j); 
							i = 100;
							
						}
						
						
					}
					//entities.get(listOfFruits.get(i)).setPosition(new Vector3f(player.getPosition().getX()-5f*(i+1),player.getPosition().getY(),player.getPosition().getZ()-5f*(i+1)));
					//entities.get(listOfFruits.get(0)).
					
					//180 - (player.getRotY()+ angleAroundPlayer)
				}
				//entities.get(listOfFruits.get(0)).setPosition(new Vector3f(player.getPosition().getX()-5f,player.getPosition().getY(),player.getPosition().getZ()-5f));
			}
			
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			TextMaster.render();
			DisplayManager.updateDisplay();
			
		
			
		}

		TextMaster.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		
		DisplayManager.closeDisplay();

	}
	


}
