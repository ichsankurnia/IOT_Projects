#include <ESP8266WiFi.h> //library ESP8266wifi yang telah diimportkan
#include "DHT.h" //memasukan library dht11
//mendefinisikan sensor yang dipakai (DHT11, DHT21, dan DHT22)
#define DHTTYPE DHT11   //tipe yang dipilih DHT 11
 
#define ON LOW
#define OFF HIGH
 
const char* ssid = "Liza H";
const char* password = "nairazio";
 
int relay1 = D5; //D1 on ESP Board
int relay2 = D6; //D2 on ESP Board
int relay3 = D7; //D3 on ESP Board
int relay4 = D8;  //D4 on ESP Board
 
int value1 = OFF, value2 = OFF, value3 = OFF, value4 = OFF;

const int DHTPin = D4;
//inisialisasi library DHTpin
DHT dht(DHTPin, DHTTYPE);
 
//menggunakan port 80 untuk http
WiFiServer server(80); 
 
void setup(){
//menggunakan baud komunikasi serial pada 115200
  Serial.begin(115200); 
  delay(20);
  dht.begin();
 
  pinMode(relay1,OUTPUT);pinMode(relay2,OUTPUT);
  pinMode(relay3,OUTPUT);pinMode(relay4,OUTPUT);
  
  digitalWrite(relay1, ON);digitalWrite(relay2, ON);
  digitalWrite(relay3, ON);digitalWrite(relay4, ON);
 
  Serial.println();Serial.println(); //pemberian spasi
  Serial.print("Terhubung dengan Wifi.... ");
 // Koneksi dengan Wifi
  Serial.println(ssid);
 
  //prosedur pengkoneksian wifi
  WiFi.begin(ssid, password);
 
 //pengecekan status wifi
  while (WiFi.status() != WL_CONNECTED) //pengecekan kondisi koneksi wifi
  {
    delay(600);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("Sudah terkoneksi dengan wifi");
 
  server.begin(); //prosedur memulai akses server
  Serial.println("Pemulaian Akses Server");
 
  //menuliskan alamat ip
  Serial.print("Alamat ip yang digunakan untuk pengaksesan: ");
  //contoh format ip address : http://192.168.1.1/
  Serial.print("http://");Serial.print (WiFi.localIP());Serial.println("/"); 
 
}
 
void loop() {
  WiFiClient client = server.available(); //cek kondisi jika terhubung dengan client
  if (!client) {
    return;
  }
 
  //menunggu client, data dikirim
  Serial.println("client baru");
  //jika client tidak available
  while(!client.available()) {delay(5);}
  String request = client.readStringUntil('\r');
  Serial.print(request);client.flush();
  
  //penulisan data pada browser dengan alamat diatas
  client.println("HTTP/1.1 200 OK");client.println("Content-Type: text/html");
  client.println("");
 
  client.println("<!DOCTYPE HTML>");client.println("<html>");
  client.println("<fieldset>");client.println("<font color = blue>");
  client.print("Kendali relay via Wifi");
  client.println("</font>");client.println("</fieldset>");
  client.println("<br><br>");
 
  client.print("relay 1 is now: ");
  // Control relay 1
  if(request.indexOf("/relay1=ON")!= -1)
  {
    digitalWrite(relay1, ON);
    value1 = ON;
    }
 
  if(request.indexOf("/relay1=OFF")!= -1)
  {
    digitalWrite(relay1, OFF);
    value1 = OFF;
    }
  
  if(value1==ON)
  {
    client.print("ON");
  }
  if(value1==OFF)
  {
    client.print("OFF");
    }
    
  client.println("<br><br>");
  client.println("<a href=\"/relay1=ON\"\"><button>relay1 ON </button></a>");
  client.println("<a href=\"/relay1=OFF\"\"><button>relay1 OFF </button></a><br />");  
  client.println("</html>");
 
 client.println("<br><br>");
 client.print("relay 2 is now: ");
  // Control relay 2
  if (request.indexOf("/relay2=ON") != -1)  {
    digitalWrite(relay2, ON);
    value2 = ON;
    }
  if (request.indexOf("/relay2=OFF") != -1)  {
    digitalWrite(relay2, OFF);
    value2 = OFF;
    }
    if(value2==ON){client.print("ON");}
     if(value2==OFF){client.print("OFF");}
   
  client.println("<br><br>");
  client.println("<a href=\"/relay2=ON\"\"><button>relay2 ON </button></a>");
  client.println("<a href=\"/relay2=OFF\"\"><button>relay2 OFF </button></a><br />");  
  client.println("</html>");
 
  client.println("<br><br>");
  client.print("relay 3 is now: ");
  // Control relay 3
  if (request.indexOf("/relay3=ON") != -1)  {
    digitalWrite(relay3, ON);
    value3 = ON;
    }
  if (request.indexOf("/relay3=OFF") != -1)  {
    digitalWrite(relay3, OFF);
    value3 = OFF;
    }
    if(value3==ON){client.print("ON");}
     if(value3==OFF){client.print("OFF");}
    
  client.println("<br><br>");
  client.println("<a href=\"/relay3=ON\"\"><button>relay3 ON </button></a>");
  client.println("<a href=\"/relay3=OFF\"\"><button>relay3 OFF </button></a><br />");  
  client.println("</html>");
 
  client.println("<br><br>");
  client.print("relay 4 is now: ");
  // Control relay 4
  if (request.indexOf("/relay4=ON") != -1)  {
    digitalWrite(relay4, ON);
    value4 = ON;
    } 
  if (request.indexOf("/relay4=OFF") != -1)  {
    digitalWrite(relay4, OFF);
    value4 = OFF;
    }
    if(value4==ON){client.print("ON");}
     if(value4==OFF){client.print("OFF");}
     
  client.println("<br><br>");
  client.println("<a href=\"/relay4=ON\"\"><button>relay4 ON </button></a>");
  client.println("<a href=\"/relay4=OFF\"\"><button>relay4 OFF </button></a><br />");
  client.println("</html>");

  if (client) {
   
    boolean blank_line = true;
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        
        if (c == '\n' && blank_line) {
            // Pembacaan sensor juga bisa sampai 2 detik 'lama' (sensornya sangat lambat)
            float h = dht.readHumidity();
            // Baca suhu sebagai Celsius (default)
            float t = dht.readTemperature();
            // Baca suhu sebagai Fahrenheit (apakah Fahrenheit = benar)
            float f = dht.readTemperature(true);
            // Periksa apakah ada yang membaca gagal dan keluar lebih awal (coba lagi)
            if (isnan(h) || isnan(t) || isnan(f)) {
              Serial.println("Failed to read from DHT sensor!");
            }
            else{
              Serial.print("Kelembaban : ");              
              Serial.print(h);
              Serial.println("%");
              
              Serial.print("Suhu : ");
              Serial.print(t);
              Serial.print(" *C ");
              Serial.println(f);
            }
            
            // Halaman web Anda yang sebenarnya menampilkan suhu dan kelembaban
            client.println("<!DOCTYPE HTML>");
            client.println("<html>");
            client.println("<head></head><body><h1>NodeMCU ESP8266 - Suhu dan Kelembaban Sensor DHT11</h1><h3>Suhu dalam Celcius: ");
            client.println(t);//celsiusTemp
            client.println("*C</h3><h3>Suhu ke dalam Fahrenheit: ");
            client.println(f);//fahrenheitTemp
            client.println("*F</h3><h3>Kelembaban: ");
            client.println(h);
            client.println("%</h3><h3>");
            client.println("</body></html>");     
            break;
        }
        if (c == '\n') {
          // Saat mulai membaca baris baru
          blank_line = true;
        }
        else if (c != '\r') {
          // Ketika menemukan karakter pada baris saat ini
          blank_line = false;
        }
      } 
    }
  }
 
  delay(1);
  Serial.println("Client disonnected");
  Serial.println("");
}
