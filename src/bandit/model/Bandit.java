/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bandit.model;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;

/**
 *
 * @author Christian
 */
public class Bandit implements Subscriber<ZahlHelper>
{
  private Flow.Subscription subscription[] = new Flow.Subscription[3];
  private static int sub = 0; // subscription counter
  private SubmissionPublisher<ZahlHelper> iPublisher;
  private Zaehler z[] = new Zaehler[3];
  
  public Bandit()
  {
    z[0] = new Zaehler(0);
    z[1] = new Zaehler(1);
    z[2] = new Zaehler(2);
    iPublisher = new SubmissionPublisher<>();
  }

  public void onSubscription()
  {
    z[0].addSubscription(this);
    z[1].addSubscription(this);
    z[2].addSubscription(this);
  }
  
  public void addSubscription(Subscriber<ZahlHelper> subscriber)
  {
    iPublisher.subscribe(subscriber);
  }
  
  public void start()
  {
    z[0].start();
    z[1].start();
    z[2].start();
  }
  
  public void stop()
  {
    z[0].stop();
    z[1].stop();
    z[2].stop();
  }
    
  @Override
  public void onSubscribe(Flow.Subscription subscription)
  {
    this.subscription[sub] = subscription;
    sub++;
    subscription.request(1);
  }

  @Override
  public void onNext(ZahlHelper item)
  {
    iPublisher.submit(item);
    subscription[item.id].request(1);
  }

  @Override
  public void onError(Throwable thrwbl)
  {
  }

  @Override
  public void onComplete()
  {
  }
}
