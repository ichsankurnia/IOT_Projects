import requests
import json
import os
import urllib.request, json
import cv2

url = "http://pokokeyakin.ecb2k16.com/jsin.php"
response = urllib.request.urlopen(url)
data = json.loads(response.read().decode())
print(data)

for i in data["gps"]:
    bt = i["bluetooth"]

cam = cv2.VideoCapture(0)
print(bt)

while 1:
	if bt == "0":
	    print("Kamera Aktif")
	    _, frame = cam.read()
	    cv2.waitKey(500)
	    cv2.imwrite("img/person.jpg", frame)
	    break
	    cam.release()
	else:
		print("Kamera mati")
		break

url = 'http://pokokeyakin.ecb2k16.com/img.php'
files = {'image': open('img/person.jpg', 'rb')}
r = requests.post(url, files=files, timeout=60)
print(r)