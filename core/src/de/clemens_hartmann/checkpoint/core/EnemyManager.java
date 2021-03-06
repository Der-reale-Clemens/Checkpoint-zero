package de.clemens_hartmann.checkpoint.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.clemens_hartmann.checkpoint.Checkpoint;
import de.clemens_hartmann.checkpoint.Disposable;
import de.clemens_hartmann.checkpoint.Drawable;
import de.clemens_hartmann.checkpoint.Updateable;

public class EnemyManager {
	private static List<Enemy> enemys = new LinkedList<Enemy>();
	
	public void update(float delta) {
		Iterator<Enemy> iter = enemys.iterator();
		while(iter.hasNext()) {
			Enemy enemy = iter.next();
			enemy.update(delta);
			if(!enemy.isAlive()) {
				enemy.dispose();
				iter.remove();
			}
		}
	}
	
	public static void addEnemy(EnemyTypes enemyType, float x, float y) {
		enemys.add(new EnemyRed(enemyType, x, y));
	}
	
	public static List<Enemy> findEnemys(float x, float y, double range) {
		List<Enemy> enemysInRange = new LinkedList<Enemy>();
		Iterator<Enemy> iter = enemys.iterator();
		while(iter.hasNext()) {
			Enemy enemy = iter.next();
			if(findDistance(enemy, x, y) < range)
				enemysInRange.add(enemy);
		}
		return enemysInRange;
	}
	
	private static float findDistance(Enemy enemy, float x, float y) {
		float xDistance = Math.abs(enemy.getX() - x);
		float yDistance = Math.abs(enemy.getY() - y);
		return xDistance + yDistance;
	}
	
	public void draw(final Checkpoint game) {
		game.batch.begin();
		for(Enemy enemy : enemys) {
			enemy.draw(game);
		}
		game.batch.end();
	}
	
	public static List<Enemy> getEnemys() {
		return enemys;
	}
	

	public void dispose() {
		for(Enemy enemy : enemys) {
			enemy.dispose();
		}
		enemys.clear();
	}
}
