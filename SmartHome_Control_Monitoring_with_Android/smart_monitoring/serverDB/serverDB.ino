#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>
 
void setup() {
 
  Serial.begin(9600);                                  //Serial connection
  WiFi.begin("Liza H", "nairazio");   //WiFi connection
 
  while (WiFi.status() != WL_CONNECTED) {  //Wait for the WiFI connection completion
    delay(500);
    Serial.println("Waiting for connection");
  }
}
 
void loop() {
   int temp = random(25,40);
   int humi = random(40,80);
   int ppm = random(1,1024);
   int ldr= random(1,1024);
   String postData;
   HTTPClient http;    //Declare object of class HTTPClient
   http.begin("http://f-home.ecb2k16.com/input.php");      //Specify request destination
   http.addHeader("Content-Type", "application/x-www-form-urlencoded");
   postData = "temp=";
   postData += temp;
   postData += "&humi=";
   postData += humi;
   postData += "&ppm=";
   postData += ppm;
   postData += "&ldr=";
   postData += ldr;
   int httpCode = http.POST(postData);   //Send the request
   String payload = http.getString();                  //Get the response payload
   Serial.println(payload);    //Print request response payload
   http.end();  //Close connection
delay(2000);  //Send a request every 30 seconds

}
