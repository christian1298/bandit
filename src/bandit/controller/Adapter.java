/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bandit.controller;

import bandit.model.Bandit;
import bandit.model.ZahlHelper;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import bandit.view.View;

/**
 *
 * @author Christian
 */
public class Adapter implements Subscriber<ZahlHelper>
{
  private Flow.Subscription subscription;
  private Bandit model;
  private View view;
  private int[] array = new int[3];
  private int counter = 0;
  
  public Adapter( Bandit model, View view)
  {
    this.model = model;
    this.view = view;
  }

  public void onSubscription()
  {
    model.addSubscription(this);
  }
  
  @Override
  public void onSubscribe(Flow.Subscription subscription)
  {
    this.subscription = subscription;
    subscription.request(1);
  }

  @Override
  public void onError(Throwable thrwbl)
  {
  }

  @Override
  public void onComplete()
  {
  }

  @Override
  public void onNext(ZahlHelper item)
  {
    switch(item.id)
    {
      case 0:
        view.getLblText1().setText(String.valueOf(item.wert));
      case 1:
        view.getLblText2().setText(String.valueOf(item.wert));
      case 2:
        view.getLblText3().setText(String.valueOf(item.wert));
      default:
        
    }

    subscription.request(1);
  }
}
