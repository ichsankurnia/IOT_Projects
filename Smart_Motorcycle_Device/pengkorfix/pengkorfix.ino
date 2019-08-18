#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>
#include <TinyGPS++.h>
#include <SoftwareSerial.h>

int cdi = D6;
int starter = D7;
int klakson = D8;
int bt = 0;
int count = 0;

SoftwareSerial serial_connection(D0,D1); //RX=pin 10, TX=pin 11
TinyGPSPlus gps;//This is the GPS object that will pretty much do all the grunt work with the NMEA data 
String lat_str, lng_str , alt_str, mph_str, s;
void setup() {
 
  Serial.begin(9600);//Serial connection
  serial_connection.begin(9600);//This opens up communications to the GPS
  Serial.println("GPS Start");//Just show to the monitor that the sketch has started
  WiFi.begin("see", "11111111");   //WiFi connection
  Serial.println("Connecting");
  while (WiFi.status() != WL_CONNECTED)   //Wait for the WiFI connection completion
  {  
    delay(500);
    Serial.println("Waiting for connection");
  }
  if (WiFi.status() == WL_CONNECTED){
    Serial.println("WiFi Konek");
  }
  pinMode(cdi, OUTPUT);
  pinMode(starter, OUTPUT);
  pinMode(klakson, OUTPUT);
  pinMode(bt, INPUT);                                   
}

void blt ()
{
  // put your main code here, to run repeatedly:
  int bt=digitalRead(D5);
  int cek=1;
  delay(500);
  keluar:
  if(bt>0)  
  { 
    for(unsigned int x=0;x<100;x++){
    int state=digitalRead(D5);
    Serial.println("sistem aktif");
    Serial.println(bt);
    delay(500);
        cek++;
        delay(500); 
        if(state==0)
        {
         /*Serial.println("alarm bunyi")*/
         digitalWrite(cdi,LOW);
                 while(digitalRead(cdi)==LOW)
                {
                 digitalWrite(klakson,HIGH);
                 delay(2000);
                 digitalWrite(klakson,LOW);
                 delay(500);
                           if(bt>0)
                           goto keluar;
                           {
                            digitalWrite(klakson,LOW);
                            delay(200);
                            }
                 }
          
        }
     }
  }
  //Serial.println("motor belum aktif");
//  Serial.println(bt);
//    return bt;
  }
  
void loop() 
{
   while(serial_connection.available())//While there are characters to come from the GPS
  {
    gps.encode(serial_connection.read());//This feeds the serial NMEA data into the library one char at a time
  }
  if(gps.location.isUpdated())//This will pretty much be fired all the time anyway but will at least reduce it to only after a package of NMEA data comes in
  {
    //Get the latest info from the gps object which it derived from the data sent by the GPS unit
    lat_str = String (gps.location.lat(), 6);
    lng_str = String (gps.location.lng(), 6);
    mph_str = String(gps.speed.mph());
    alt_str = String (gps.altitude.feet());

   blt();
   int b=digitalRead(D5);
   Serial.println(b);
   String bt = String(b); 
   String s;
   HTTPClient http;    
   http.begin("http://pokokeyakin.ecb2k16.com/in.php");
   http.addHeader("Content-Type", "application/x-www-form-urlencoded");
   s = "lat_str=";
   s += lat_str;
   s += "&lng_str=";
   s += lng_str;
   s += "&alt_str=";
   s += alt_str;
   s += "&mph_str=";
   s += mph_str;
   s += "&blut=";
   s += bt;
   int httpCode;
   
   httpCode = http.POST(s);   //Send the request
   String payload = http.getString();                  //Get the response payload
   Serial.println(payload);    //Print request response payload

   if(count == 0){
    http.begin("http://pokokeyakin.ecb2k16.com/co.php?id=1");
    count = count + 1;
  }
  else if(count == 1){
    http.begin("http://pokokeyakin.ecb2k16.com/co.php?id=2");
    count = count + 1;
  }
  else if(count == 2){
    http.begin("http://pokokeyakin.ecb2k16.com/co.php?id=3");
    count = count + 1;
  }
  
  httpCode = http.GET(); // httpCode = 200;
 
  String json = http.getString();
  Serial.println(json);

  if(count == 1){
    if(json == "{\"id\":\"1\",\"status\":\"on\"}")
    {
      digitalWrite(cdi, HIGH);
    } else {
      digitalWrite(cdi, LOW);
      }
  }
  else if(count == 2){
    if(json == "{\"id\":\"2\",\"status\":\"on\"}")
    {
      digitalWrite(starter, HIGH);
    } else {
      digitalWrite(starter, LOW);
      }
      count = 0;
  }

  if(count == 2)
    count = 0;
   delay(1000);  //Send a request every 30 seconds

   http.end();
  }
}  

/*
void baca()
{
  HTTPClient http;

  
            
  
  delay(1000);
}
*/
