/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package callexchange;

/**
 *
 * @author magreth.jubilate
 */
public class CallManager {

   private static int inService=0; //number of calls in service
   private static int dropped=0; //number of calls dropped
   private static int arrived=0; //number of calls arrived at the exchange
   private static int totService=0;
   private static float t = (float) 0.0;
   private static float simTime=10;
   
    public static void main(String[] args) {
        
     // The next 2 lines create the simulation clock
	Timer clock = new Timer();
	clock.setTime(0);  // Timer initialized to zero
	List eventList = new List();  // A list to hold events
	
	final float MEANARR = (float)0.2; //**mean inter-arrival 
	final float MEANPRO = (float)0.093; //**mean processing 
	final int ARRSTR = 1; //** a stream # from 1 to 100 to be applied in random # generation. 
	final int PROSTR = 2;
     
	
	// The next 4 lines create the first arrival
	SimulItem event = new SimulItem();
	event.setName("arrive"); 
	event.setTime(clock.getTime() + RandomGen.expon(MEANARR, ARRSTR));
	eventList.insertInOrder(event, event.getTime());
      
        try
	{
 
while(t < simTime)
      {
        
            t=clock.getTime();

		SimulItem removed = eventList.removeFromFront();
		clock.setTime(removed.getTime());

		// Now the event is processed
		if (removed.getName() == "arrive")  // if the event is an arrival
		{
               arrived++;
		  // First the next customer arrival is scheduled
	      event = new SimulItem();
	      event.setName("arrive"); 
              event.setTime(clock.getTime() + RandomGen.expon(MEANARR,ARRSTR));
	      eventList.insertInOrder(event, event.getTime());
            

               if (inService < 50)  // If there is a free circuit
		  {
                    inService++;
   		        event = new SimulItem();
			event.setName("depart"); 
			event.setTime(clock.getTime() + RandomGen.expon(MEANPRO, PROSTR));
			eventList.insertInOrder(event, event.getTime());
                      //System.out.println("Cust in service  "+ inService); 
                      totService++;
		  }
		  else// If no free circuit
		  {
		dropped++;	

		  }
		}
		else // A departure event
		{
           	  inService--;
                   //System.out.println("Cust in service  "+ inService);
                }
                
           }
	}
	catch (ListException e)
	{
	  System.err.println("\n" + e.toString());
	} 
       
       System.out.println("Total calls arrived  "+ arrived);
        System.out.println("Calls served  "+ totService);
       System.out.println("Calls dropped  "+ dropped);
    }
}
