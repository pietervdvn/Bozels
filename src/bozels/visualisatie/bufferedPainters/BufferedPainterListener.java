package bozels.visualisatie.bufferedPainters;

public interface BufferedPainterListener {
	
	void onSourceWantsToBeRepainted(BufferedPainter source);
	void onImageReady(BufferedPainter source, int repaintId);
	
}
