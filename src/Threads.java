public class Threads implements Runnable{
   private WordRecord record;
   private HandleWords handle;

   public Threads(WordRecord record, HandleWords handle){
      super();
      this.record = record;
      this.handle = handle;  
   }
   
   @Override
   public void run(){
      while(!handle.getEnd()){
         if(record.dropped()){
            handle.wordMissed();
            record.resetWord();
            handle.setChange();
         }
         //else if(handle.paused()){
           // continue;
         //}
         else{
            record.drop(1);
            handle.update();
         }
         try{
            Thread.sleep(record.getSpeed()/15);
         }
         catch(InterruptedException e){
            e.printStackTrace();
         }
       }
     }
     
     public synchronized void reset(){
         record.resetWord();
     }
   
}
