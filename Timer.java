/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package callexchange;

/**
 *
 * @author magreth.jubilate
 */
public class Timer
{
	private float present;

	public Timer()
	{
		present = 0;
	}

	public void setTime(float time)
	{
		present = time;
	}

	public float getTime()
	{
		return present;
	}
}
