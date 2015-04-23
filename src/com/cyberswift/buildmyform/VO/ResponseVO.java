package com.cyberswift.buildmyform.VO;

import org.xmlpull.v1.XmlPullParser;

import com.cyberswift.buildmyform.Constants.ConstantVO;


public class ResponseVO extends ConstantVO{

   private String string = "string";
  
   public volatile boolean parsingComplete = true;
   public ResponseVO(String url){
      this.urlString = url;
   }
   public String getString(){
      return string;
   }
  
@Override
   public void parseXMLAndStoreIt(XmlPullParser myParser) {
      int event;
      String text=null;
      try {
         event = myParser.getEventType();
         while (event != XmlPullParser.END_DOCUMENT) {
            String name=myParser.getName();
            switch (event){
               case XmlPullParser.START_TAG:
               break;
               case XmlPullParser.TEXT:
               text = myParser.getText();
               break;

               case XmlPullParser.END_TAG:
                  if(name.equals("string")){
                     string = text;
                  }
                  
                  else{
                  }
                  break;
                  }		 
                  event = myParser.next(); 

              }
                 parsingComplete = false;
      } catch (Exception e) {
         e.printStackTrace();
      }

   }
   

}
