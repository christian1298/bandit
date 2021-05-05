/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bandit;

import bandit.controller.Adapter;
import bandit.controller.StartStopController;
import bandit.model.Bandit;
import bandit.model.Zaehler;
import bandit.view.View;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


/**
 *
 * @author Christian
 */
public class Start 
{
  public Start()
  {
  View view = new View();
  Bandit model = new Bandit();
  model.onSubscription();
  //
  Adapter adapter = new Adapter(model, view);
  adapter.onSubscription();
  StartStopController con = new StartStopController(model,view);
  con.RegisterEvents();
  view.setVisible(true);
  }

  public static void main(String[] args) 
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e)
    {
      JOptionPane.showMessageDialog(null, e.toString());
    }
    new Start();
  }

}
