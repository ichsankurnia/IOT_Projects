package com.example.xp.control;

public class Biodata extends Koneksi {
    String URL = "http://f-home.ecb2k16.com/upco.php";
    String url = "";
    String response = "";

    /* public String inserBiodata(String nama, String alamat) {
        try {
            url = URL + "?operasi=insert&nama=" + nama + "&alamat=" + alamat;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    } */

    public String D1ON(String status) {
        try {
            url = URL + "?id=1&status=" + status;
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String D1OFF(String status) {
        try {
            url = URL + "?id=1&status=" + status;
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String D2ON(String status) {
        try {
            url = URL + "?id=2&status=" + status;
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String D2OFF(String status) {
        try {
            url = URL + "?id=2&status=" + status;
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String D3ON(String status) {
        try {
            url = URL + "?id=2&status=" + status;
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String D3OFF(String status) {
        try {
            url = URL + "?id=3&status=" + status;
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }


}