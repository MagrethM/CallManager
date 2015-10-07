/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package callexchange;

/**
 *
 * @author magreth.jubilate
 */
public class ListException extends RuntimeException
{
	public ListException(String name)
	{
		super("The " + name + " is empty");
	}
}
