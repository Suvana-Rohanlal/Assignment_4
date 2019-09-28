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
      while(!handle.done()){
         if(record.miss()){
            handle.wordMissed();
            record.resetWord();
            handle.setChange();
         }
         else if(handle.paused()){
            continue;
         }
         else{
            record.drop(1);
            handle.update();
         }
         try{
            Thread.sleep(record.getSpeed()/15);
         }
         catch(InterruptedException e){
            e.printStackTrac();
         }
       }
     }
     
     public synchronized void reset(){
         record.resetWord();
     }
   
}
