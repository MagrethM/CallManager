/*
 This code is a customization from a more general system developed by Rahul Simha. The code was retrieved from http://www.seas.gwu.edu/~simhaweb/contalg/classwork/module7/examples/QueueControl.java
 */
package SingleServer;

/**
 *
 * @author magreth.jubilate
 */
import java.util.*;
import java.text.*;


public class SingleServerD {

    double arrvRate = 50;
    private static Random random;
    LinkedList<Packet> queue;      //To hold packets in the queue
    PriorityQueue<Event> eventList; //To hold simulation events
    double clock;                   //Simulation clock
    int numArrivals = 0;                    // Number of packets arrived to the system
    int numDepartures;                      // Number of packets departed the system
    int dropped=0;                      
    double totalWaitTime, avgWaitTime;
    double totalSystemTime, avgSystemTime;
    double totalservicetime;
    double totalqueuesize;
    double avQueue;
    double avgservicetime;

    public static void main(String[] argv) {
        System.out.println("Packet no" + "	" + "AvgWaitTime" + "	" + "AvgserviceTime" + "	" + "AvgtotalSystemtime" + "	" + "Time" + "	" + "packets in the queue" + "	" + "AvgNoPacketInQueueu");
        SingleServerD queue = new SingleServerD();
        queue.simulate(10);
        System.out.println(queue);

    }

    void simulate(double simulationtime) {
        init();

        while (clock < simulationtime) {
            Event e = eventList.poll();
            clock = e.eventTime;
            if (e.type == Event.ARRIVAL) {
                handleArrival(e);
            } else {
                handleDeparture(e);
            }
        }

        stats();
    }

    void init() {
        queue = new LinkedList<Packet>();
        eventList = new PriorityQueue<Event>();
        clock = 0.0;
        numArrivals = numDepartures = 0;
        totalservicetime = 0;
        totalWaitTime = totalSystemTime = 0.0;
        scheduleArrival();
    }

    void handleArrival(Event e) {
        
       numArrivals++;
        queue.add(new Packet(clock, randomServiceTime()));

        if (queue.size() == 1) {
            // This is the only Packet => schedule a departure.
            scheduleDeparture();
        }
        scheduleArrival();
        
    }

    void handleDeparture(Event e) {
        numDepartures++;
        Packet c = queue.removeFirst();

        // This is the time from start to finish for this Packet:
        double timeInSystem = clock - c.arrivalTime;
        double servicetime = c.serviceTime;
        totalservicetime += servicetime;

        // Maintain total (for average, to be computed later).
        totalSystemTime += timeInSystem;
        if (queue.size() > 0) {
            // There's a waiting Packet => schedule departure.
            Packet waitingpacket = queue.get(0);
            // This is the time spent only in waiting in the queue:
            double waitTime = clock - waitingpacket.arrivalTime;
            // Note where we are collecting stats for waiting time.
            totalWaitTime += waitTime;
            totalqueuesize = totalqueuesize + queue.size();
            avQueue = totalqueuesize / queue.size();
            scheduleDeparture();
        }
        System.out.println(numDepartures + "	" + totalWaitTime / numDepartures + "	" + totalservicetime / numDepartures + "	" + totalSystemTime / numDepartures + "	" + clock + "	" + queue.size() + "	" + Math.ceil((totalqueuesize / numDepartures)));

    }

    void scheduleArrival() {
        // The next arrival occurs when we add an interrarrival to the the current time.
        double nextArrivalTime = clock + randomInterarrivalTime();
        eventList.add(new Event(nextArrivalTime, Event.ARRIVAL));
    }

    void scheduleDeparture() {
        double nextDepartureTime = clock + randomServiceTime();
        eventList.add(new Event(nextDepartureTime, Event.DEPARTURE));
    }

    double randomInterarrivalTime() {
        return exponential(arrvRate);
    }

    double randomServiceTime() {
        return 0.016;
    }
    // pseudo-random number generator
    private static long seed;

    {
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    public static float uniform() {
        return random.nextFloat();
    }

    double exponential(double gamma) {
        float x = (float) (-Math.log(1 - uniform()) / gamma);
        return x;
    }

    void stats() {
        if (numDepartures == 0) {
            return;
        }
        avgWaitTime = totalWaitTime / numDepartures;
        avgSystemTime = totalSystemTime / numDepartures;
        avgservicetime = totalservicetime / numDepartures;
    }

    public String toString() {
        String results = "Simulation results:";
        results += "\n  numArrivals:     " + numArrivals;
        results += "\n  numDepartures:   " + numDepartures;
        results += "\n  avg Wait:        " + avgWaitTime;
        results += "\n  avg System Time: " + avgSystemTime;
        results += "\n  avg Service Time: " + avgservicetime;
        return results;
    }
}
class Packet {

    double arrivalTime;
    double serviceTime;

    public Packet(double arrivalTime, double serviceTime) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }
}

// Class Event has everything we need for an event: the type of
// event, and when it occurs. To use Java's PriorityQueue, we need
// have this class implement the Comparable interface where
// one event is "less" if it occurs sooner.
class Event implements Comparable {

    public static int ARRIVAL = 1;
    public static int DEPARTURE = 2;
    int type = -1;                     // Arrival or departure.
    double eventTime;                  // When it occurs.

    public Event(double eventTime, int type) {
        this.eventTime = eventTime;
        this.type = type;
    }

    public int compareTo(Object obj) {
        Event e = (Event) obj;
        if (eventTime < e.eventTime) {
            return -1;
        } else if (eventTime > e.eventTime) {
            return 1;
        } else {
            return 0;
        }
    }

    public boolean equals(Object obj) {
        return (compareTo(obj) == 0);
    }
}
