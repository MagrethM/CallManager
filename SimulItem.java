/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package callexchange;

/**
 *
 * @author magreth.jubilate
 */
public class SimulItem  
// This is an object that holds data.  SimObjects are put into lists.
						
{
	private String name;
	private float time;
	
	public SimulItem()
	{
		name = "";
		time = (float)0.0;
	}

	public void setName(String str)
	{
		name = str;
	}

	public String getName()
	{
		return name;
	}

	public void setTime(float fl)
	{
		time = fl;
	}

	public float getTime()
	{
		return time;
	}
	
	
}
