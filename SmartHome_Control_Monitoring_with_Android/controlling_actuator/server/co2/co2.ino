#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

int lamp = D1;
int lomp = D2;
int lump = D3;

int count = 0;

void setup()
{
    Serial.begin(115200);

    WiFi.begin("Liza H", "nairazio");
    
    pinMode(lamp, OUTPUT);
    pinMode(lomp, OUTPUT);
    pinMode(lump, OUTPUT);

    digitalWrite(lamp, LOW);
    digitalWrite(lomp, LOW);
    digitalWrite(lump, LOW); 
}

void loop()
{
  HTTPClient http;

  if(count == 0){
    http.begin("http://f-home.ecb2k16.com/co.php?id=1");
    count = count + 1;
  }
  else if(count == 1){
    http.begin("http://f-home.ecb2k16.com/co.php?id=2");
    count = count + 1;
  }
  else if(count == 2){
    http.begin("http://f-home.ecb2k16.com/co.php?id=3");
    count = count + 1;
  }
  
  int httpCode = http.GET(); // httpCode = 200;
 
  String json = http.getString();
  Serial.println(json);

  if(count == 1){
    if(json == "{\"id\":\"1\",\"status\":\"on\"}")
    {
      digitalWrite(lamp, HIGH);
    } else {
      digitalWrite(lamp, LOW);
      }
  }
  else if(count == 2){
    if(json == "{\"id\":\"2\",\"status\":\"on\"}")
    {
      digitalWrite(lomp, HIGH);
    } else {
      digitalWrite(lomp, LOW);
      }
  }
  else if(count == 3){
    if(json == "{\"id\":\"3\",\"status\":\"on\"}")
    {
      digitalWrite(lump, HIGH);
    } else {
      digitalWrite(lump, LOW);
      }
      count = 0;
  }

  if(count == 3)
    count = 0;
            
  http.end();
  delay(5000);
}
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTQ0MTU3Nzc0NF19
-->