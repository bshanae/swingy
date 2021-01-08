package model.closed.creatures.enemy;

import application.service.Debug;
import application.service.Exceptions;
import application.service.LogGroup;
import model.closed.Session;
import model.closed.utils.RandomGenerator;

import java.util.LinkedList;
import java.util.List;

public abstract class				EnemySpawner
{
// -------------------------------> Constants

	private static final int		NUMBER_OF_ATTEMPTS_TO_SELECT_ENEMY = 100;

// -------------------------------> Public methods

	public static Enemy				spawn()
	{
		List<Enemy>					possibleEnemies;
		Enemy						selectedEnemy;

		possibleEnemies = generateEnemiesForCurrentLevel();
		selectedEnemy = selectRandomEnemy(possibleEnemies);

		Debug.logFormat
		(
			LogGroup.GAME,
			"[Model/EnemyGenerator] Spawning enemy '%s'", selectedEnemy.getName()
		);

		return selectedEnemy.clone();
	}

// -------------------------------> Private methods

	private static List<Enemy>		generateEnemiesForCurrentLevel()
	{
		List<Enemy>					list;

		list = new LinkedList<>();
		for (Enemy enemy : EnemyStorage.getInstance())
		{
			if (enemy.getLevel() <= Session.getInstance().getHero().getLevel())
				list.add(enemy);
		}

		return list;
	}

	private static Enemy			selectRandomEnemy(List<Enemy> list)
	{
		int							selectedIndex;
		Enemy						selectedMeta;

		float						appearanceProbability;

		for (int i = 0; i < NUMBER_OF_ATTEMPTS_TO_SELECT_ENEMY; i++)
		{
			selectedIndex = RandomGenerator.randomBetween(0, list.size() - 1);
			selectedMeta = list.get(selectedIndex);

			appearanceProbability = getTransformedAppearanceProbability(selectedMeta);

			if (RandomGenerator.randomWithProbability(appearanceProbability))
				return selectedMeta;
		}

		throw new Exceptions.UnexpectedCodeBranch();
	}

	private static float			getTransformedAppearanceProbability(Enemy enemy)
	{
		int							currentLevel;
		int							appearanceLevel;
		int							levelDelta;

		float						appearProbabilityMultiplier;

		currentLevel = Session.getInstance().getHero().getLevel();
		appearanceLevel = enemy.getLevel();
		levelDelta = currentLevel - appearanceLevel;

		appearProbabilityMultiplier = 1.f;

		if (levelDelta > 0)
		{
			appearProbabilityMultiplier = 1.f - 0.05f * levelDelta * levelDelta;
			appearProbabilityMultiplier = Math.max(0.f, appearProbabilityMultiplier);
		}

		return enemy.getSpawnChance() * appearProbabilityMultiplier;
	}
}
