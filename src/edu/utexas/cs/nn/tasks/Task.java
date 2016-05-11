package edu.utexas.cs.nn.tasks;

/**
 * An abstract, general definition of a task for all types of tasks
 * These methods will most likely be included by all tasks
 * @author Jacob Schrum
 */
public interface Task {

	/**
	 * getter method
	 * @return the number of objectives for this task
	 */
    public int numObjectives();

    /**
     * getter method
     * @return an array of all of the minimum scores for this task for each agent
     */
    public double[] minScores();

    /**
     * getter method
     * @return the time stamp for this task
     */
    public double getTimeStamp();
}
