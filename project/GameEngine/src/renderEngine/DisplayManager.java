/* Dieser Code wurde mit Hilfe des Tutorials von ThinMatrix erstellt. https://www.youtube.com/watch?v=WMiggUPst-Q&list=PLRIWtICgwaX0u7Rf9zkZhLoLuZVfUksDP&index=2 */
package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	private static final int WIDTH = 1280;
	private static final int HEIGTH = 720;
	private static final int FPS_CAP = 120;
	
	private static long lastFrameTime;
	private static float delta;
	
	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3,2); 
		attribs.withForwardCompatible(true);
		attribs.withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGTH));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Snake");

		} catch (LWJGLException e) {
			
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGTH);
		lastFrameTime = getCurrentTime();
	}
	
	public static void updateDisplay() {
		
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime)/1000f;
		lastFrameTime = currentFrameTime;
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	public static void closeDisplay() {
		
		Display.destroy();
	}
	
	private static long getCurrentTime() {
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}

}
