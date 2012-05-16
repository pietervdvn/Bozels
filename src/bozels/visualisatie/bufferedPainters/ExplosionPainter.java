package bozels.visualisatie.bufferedPainters;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;

import bozels.physicsModel.PhysicalObject;
import bozels.physicsModel.explosions.Explosion;
import bozels.physicsModel.explosions.ExplosionVectorLogger;
import bozels.visualisatie.gameColorModel.GameColorModel;
import bozels.visualisatie.gameColorModel.GameColorModelListener;
import bozels.visualisatie.painterSettingsModel.PainterSettingsModel;


/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class ExplosionPainter extends BufferedPainter implements
		ExplosionVectorLogger, GameColorModelListener {

	private final double range;
	private final GameColorModel cm;
	private final List<Line> lines;

	private final Vec2 center;

	public ExplosionPainter(Explosion exp, GameColorModel cm,
			PainterSettingsModel psm) {
		super(psm);
		this.range = exp.getExpSettingsModel().getRange();
		this.cm = cm;
		this.center = exp.getSource().getBody().getPosition().clone();
		lines = new ArrayList<Line>(exp.getExpSettingsModel().getNumberOfRays());
		exp.addLogger(this);
		cm.addListener(this);
	}

	@Override
	public void paintContents(Graphics2D g) {
		PainterSettingsModel psm = getPainterSettingsModel();
		int scale = psm.getScale();
		g.setColor(cm.getColor(GameColorModel.EXPLOSION_RAYCAST));

		for (Line line : lines) {
			line.drawOn(g, scale);
		}
		int dotS = psm.getCatapultDotSize() * scale / 3;
		g.fillOval((int) (center.x * scale - dotS / 2),
				(int) (center.y * scale - dotS / 2), dotS, dotS);
		g.setColor(cm.getColor(GameColorModel.EXPLOSION_RANGE));
		g.drawOval((int) ((center.x - range) * scale),
				(int) ((center.y - range) * scale), (int) (range * scale * 2),
				(int) (range * scale * 2));
	}

	@Override
	public void captureExplosionVector(Explosion sourceExplosion,
			PhysicalObject hitObject, Vec2 pointOfImpact, Vec2 force) {
		lines.add(new Line(pointOfImpact, force));
		repaint();
	}

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
	private class Line {

		private final Vec2 startPoint;
		private final Vec2 force;

		public Line(Vec2 startPoint, Vec2 force) {
			this.startPoint = startPoint.clone();
			this.force = force.clone();
			this.force.mulLocal(force.length());
			this.force.mulLocal(0.00001f);
			
		}

		public void drawOn(Graphics2D g, int scale) {
			double x = startPoint.x * scale;
			double y = startPoint.y * scale;
			g.drawLine((int) (x), (int) y, (int) (x + force.x),
					(int) (y + force.y));
		}

	}

	@Override
	public void onColorChanged(GameColorModel source) {
		repaint();
	}

}
