#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>

//Our Wi-Fi ssdid and password
char* ssid = "IchsanKurnia";
char* password = "ichsan14";
String out_lamp = "0";
String in_lamp = "0";
String roof = "0";
String fan = "0";

ESP8266WebServer server; //server variable

void setup() {
  initializePin(); //call function

  //Making Connection With netword
  WiFi.begin(ssid, password);
  
  Serial.begin(115200);
  Serial.print("Searching Connection");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  
  Serial.println("");
  Serial.print("IP Address: ");
  Serial.print(WiFi.localIP());
  
  serverSection();
}

void loop() {
  // put your main code here, to run repeatedly:
  server.handleClient();
}

void initializePin(){
  
  pinMode(D5, OUTPUT);
  pinMode(D6, OUTPUT);
  pinMode(D7, OUTPUT);
  pinMode(D8, OUTPUT);

  digitalWrite(D5, LOW);
  digitalWrite(D6, LOW);
  digitalWrite(D7, LOW);
  digitalWrite(D8, LOW);
}

void serverSection(){
  server.on("/", []() {
    server.send(200, "text/html", "<!DOCTYPE html><html><meta charset='UTF-8'><head></head><body><h2>Future Home</h2><h3><a href='/out_lamp'>Outside Lamp</a></h3><br><h3><a href='/in_lamp'>Inner Lamp</a></h3><br><h3><a href='/roof'>Roof</a></h3><br><h3><a href='/fan'>Fan</a></h3><br></body></html>");
  });

  server.on("/out_lamp", out_lamp_state);
  server.on("/in_lamp", in_lamp_state);
  server.on("/roof", roof_state);
  server.on("/fan", fan_state);

  server.on("/status", all_state);
  
  server.begin();
}

void out_lamp_state(){
  if(out_lamp == "0"){
    out_lamp = "1";
    digitalWrite(D5, HIGH);
    server.send(200, "text/html", out_lamp);
  }else{
    out_lamp = "0";
    digitalWrite(D5, LOW);
    server.send(200, "text/html", out_lamp);
  }
}

void in_lamp_state(){
  if(in_lamp == "0"){
    in_lamp = "1";
    digitalWrite(D6, HIGH);
    server.send(200, "text/html", in_lamp);
  }else{
    in_lamp = "0";
    digitalWrite(D6, LOW);
    server.send(200, "text/html", in_lamp);
  }
}

void roof_state(){
  if(roof == "0"){
    roof = "1";
    digitalWrite(D7, HIGH);
    server.send(200, "text/html", roof);
  }else{
    roof = "0";
    digitalWrite(D7, LOW);
    server.send(200, "text/html", roof);
  }
}

void fan_state(){
  if(fan == "0"){
    fan = "1";
    digitalWrite(D8, HIGH);
    server.send(200, "text/html", fan);
  }else{
    fan = "0";
    digitalWrite(D8, LOW);
    server.send(200, "text/html", fan);
  }
}

void all_state(){
  
  server.send(200, "text/html", "{'ol':'"+out_lamp+"','il':'"+in_lamp+"','ro':'"+roof+"','fan':'"+fan+"'}");

}
