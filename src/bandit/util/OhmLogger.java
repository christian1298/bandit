/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bandit.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.*;

/**
 *
 * @author Christian
 */
public class OhmLogger 
{
  private static Logger lg = null;
  private OhmLogger()
  {
  }
  
  public static Logger getLogger()
  {
    if(lg == null)
    {
      lg = Logger.getLogger("OhmLogger");
      initLogger();
    }
    return lg;
  }
  
  private static void initLogger()
  {
    try{
      // Einstellungen in Properties-Datei
      Properties props = new Properties();
      InputStream is = OhmLogger.class.getResourceAsStream("logger.properties");
      props.load(is);
      
      String datei = props.getProperty("LOG_DATEI");
      FileHandler fh = new FileHandler(datei);
      fh.setFormatter(new OhmFormatter());
      lg.addHandler(fh);
      
      ConsoleHandler ch = new ConsoleHandler();
      ch.setFormatter(new OhmFormatter());
      lg.addHandler(ch);
      
      String level = props.getProperty("LOG_LEVEL");
      lg.setLevel(Level.parse(level));
      
      lg.setUseParentHandlers(false);
    }
    catch(IOException ioex){
      System.err.println(ioex);
    }
  }
}

class OhmFormatter extends SimpleFormatter
{
  public String format(LogRecord record)
  {
    String logLine = "";
    logLine += String.format("%1$tF %1$tT:%1$tL",record.getMillis());
    logLine += " " + record.getLevel();
    logLine += " " + record.getSourceClassName();
    logLine += " " + record.getSourceMethodName();
    logLine += " " + record.getMessage();
    
    
    
    logLine += "\n";
    return logLine;
  }
}
