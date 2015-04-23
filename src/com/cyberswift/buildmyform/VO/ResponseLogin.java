package com.cyberswift.buildmyform.VO;

import org.xmlpull.v1.XmlPullParser;

import com.cyberswift.buildmyform.Constants.ConstantVO;


public class ResponseLogin extends ConstantVO {

   private String user_id = "d:user_id";
   public String organization_id = "d:organization_id";
   private String names = "d:name";
   private String password = "d:password";
   private String email_id = "d:email_id";
   private String is_valid = "d:is_valid";
   private String user_type_id = "d:user_type_id";
   private String registration_date = "d:registration_date";

   public volatile boolean parsingComplete = true;
   public ResponseLogin(String url){
      this.urlString = url;
   }
  
   public String getUser_id(){
      return user_id;
   }
   public String getOrganization_id(){
	      return organization_id;
	   }
   public String getNames(){
	      return names;
	   }
   public String getPassword(){
	      return password;
	   }
   public String getEmail_id(){
	      return email_id;
	   }
   public String getIs_valid(){
	      return is_valid;
	   }
   public String getUserTypeId(){
	      return user_type_id;
	   }
public String getRegisDate(){
	      return registration_date;
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
                  if(name.equals("d:user_id")){
                	  
                	  user_id = text;
                  }
                  else if(name.equals("d:organization_id")){
                	  
                	  organization_id = text;
                  }
                  else if(name.equals("d:name")){
                	 
                	  names = text;
                  }
                  else if(name.equals("d:password")){
                	  
                	  password = text;
                  }
                  else if(name.equals("d:email_id")){
                	  
                	  email_id = text;
                  }
                  else if(name.equals("d:is_valid")){
                	  
                	  is_valid = text;
                	
                  }
                  else if(name.equals("d:user_type_id")){
                	  
                	  user_type_id = text;
                  }
                  else if(name.equals("d:registration_date")){
                	  
                	  registration_date = text;
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
