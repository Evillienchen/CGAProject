/* Dieser Code wurde mit Hilfe des Tutorials von ThinMatrix erstellt. https://www.youtube.com/watch?v=WMiggUPst-Q&list=PLRIWtICgwaX0u7Rf9zkZhLoLuZVfUksDP&index=2 */
package entities;

import java.util.concurrent.TimeUnit;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(100,35,50);
	private float pitch = 20;
	private float yaw ;
	private float roll;
	private Boolean playerCamera = true;
	private Player player;
	private Boolean waitForIt = true;
	
	public Camera(Player player){
		this.player = player;
	}
	
	public void move(){
		
		checkInputs();
		if(playerCamera) {
			calculateZoom();
			calculatePitch();
			calculateAngleAroundPlayer();
			float horizontalDistance = calculateHorizontalDistance();
			float verticalDistance = calculateVerticalDistance();
			calculateCameraPosition(horizontalDistance, verticalDistance);
			this.yaw = 180 - (player.getRotY()+ angleAroundPlayer);
			
		}else {
		
		}
		
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = player.getRotY() + angleAroundPlayer;
		
		
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticDistance;
	}
	
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel()* 0.1f;
		distanceFromPlayer -= zoomLevel;
	}
	
	private void calculatePitch() {
		if(Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
			
		}
	}
	
	private void calculateAngleAroundPlayer() {
		if(Mouse.isButtonDown(0)) {
			float angleChange = Mouse.getDX()*0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
	
	public float getAngleAroundPlayer() {
		return angleAroundPlayer;
	}
	
	public void checkInputs() {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_O)) {
			if(playerCamera) {
				
				
				playerCamera = false;
				pitch = 90;
				
				float offsetX = (float) (100 * Math.sin(Math.toRadians(180)));
				float offsetZ = (float) (150 * Math.cos(Math.toRadians(180)));
				position.x = offsetX;
				position.z = offsetZ;
				position.y = 450;
				this.yaw = 180;
				
				
			}else {
				
					playerCamera = true;
					pitch = 20;
				
				
				
			}
			
			
		}
	}
	

}