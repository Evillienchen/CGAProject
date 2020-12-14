/* Dieser Code wurde mit Hilfe des Tutorials von ThinMatrix erstellt. https://www.youtube.com/watch?v=WMiggUPst-Q&list=PLRIWtICgwaX0u7Rf9zkZhLoLuZVfUksDP&index=2 */
package fontMeshCreator;

/**
 * Stores the vertex data for all the quads on which a text will be rendered.
 * @author Karl
 *
 */
public class TextMeshData {
	
	private float[] vertexPositions;
	private float[] textureCoords;
	
	protected TextMeshData(float[] vertexPositions, float[] textureCoords){
		this.vertexPositions = vertexPositions;
		this.textureCoords = textureCoords;
	}

	public float[] getVertexPositions() {
		return vertexPositions;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public int getVertexCount() {
		return vertexPositions.length/2;
	}

}
