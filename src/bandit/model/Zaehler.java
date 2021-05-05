/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bandit.model;


import java.util.logging.Logger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;
import bandit.util.OhmLogger;

/**
 *
 * @author Christian
 */
public class Zaehler implements Runnable
{
  private static Logger lg = OhmLogger.getLogger();
  
  private int val;
  private ZahlHelper retVal;
  private SubmissionPublisher<ZahlHelper> iPublisher;
  private ExecutorService eService;
  private volatile boolean running;
  
  public Zaehler(Integer id)
  {
    running = false;
    eService = Executors.newSingleThreadExecutor();
    iPublisher = new SubmissionPublisher<>();
    retVal = new ZahlHelper();
    retVal.id = id;
  }
  
  public void addSubscription(Subscriber<ZahlHelper> subscriber)
  {
    iPublisher.subscribe(subscriber);
  }

  public synchronized void start()
  {
    running = true;
    lg.info("Start");
    notifyAll();
    
    eService.submit(this);
  }
  
  public synchronized void stop()
  {
    running = false;
    lg.info("Stop");
    notifyAll();
  }
  
  @Override
  public void run()
  {
    while(true)
    {
      synchronized(this)
      {
      while(!running)
      {
        try
        {
          this.wait();
        }
        catch(InterruptedException e)
        {
          System.out.println(e);
        }
      }
      }
      try
      {
        Thread.sleep(10);
      }
      catch(InterruptedException e)
      {
        System.out.println(e);
      }
      val = (val % 6) + 1;
      retVal.wert = val;
      iPublisher.submit(retVal); //
    }
  }
}
