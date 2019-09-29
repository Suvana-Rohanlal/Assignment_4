import java.util.*;
import javax.swing.*;

public class HandleWords{
   private Threads[] thread;
   
   private volatile boolean end,change,pause,active;
   
   private final Object label = new Object();
   
   public HandleWords(){
      super();
      this.thread = new Threads[WordApp.words.length];
      end=true;
      pause=false;
      active=false;
   }
   
   public void update(){
      synchronized(label){
         WordApp.labels[0].setText("Caught:" + WordApp.score.getCaught()+"  ");
         WordApp.labels[1].setText("Missed:" + WordApp.score.getMissed()+"  ");
         WordApp.labels[2].setText("Score:" + WordApp.score.getScore()+"  ");
         setChange();
      }
   }
   
   public synchronized boolean check(String answer){
      for(WordRecord w: WordApp.words){
        if(w.matchWord(answer)){
         int len = answer.length();
         WordApp.score.caughtWord(len);
         update();
         if(WordApp.score.getCaught() >= WordApp.totalWords){
           won();
         }
         return true;
      }
  
      }
      return false;
      
   }
   
   public void startRound(){
      end=false;
      active= true;
      int count =0;
      for(WordRecord w: WordApp.words){
         thread[count] = new Threads(w, this);
         new Thread(thread[count]).start();
         count++;
      }
   }
   
   public synchronized void wordMissed(){
      WordApp.score.missedWord();
      update();
      int missed = WordApp.score.getMissed();
      if(missed>=10){
         stop();
         setChange();
         reset();
         WordApp.w.repaintOnce();
      }
   }
   
   public void reset(){
      WordApp.score.resetScore();
      update();
   }

   public void stop(){
      for(Threads t: thread){
         t.reset();
      }
      end = true;
      active = false;
   }
   
   public void end(){
      stop();
      reset();
      WordApp.w.repaintOnce();
   }

   public boolean getEnd(){
      return end;
   }
   
   public void won(){
      stop();
      setChange();
      reset();
      WordApp.w.repaintOnce();
   }


   public boolean getChange(){
      return change;
   }
   
   public void setChange(){
      change = true;
   }
   
   public void resetChange(){
      change = false;
   }
   
   public boolean getActive(){
      return active;
   }
   


}