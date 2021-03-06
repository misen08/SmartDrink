package xyris.smartdrink.http;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import xyris.smartdrink.http.exceptions.ClientException;
import xyris.smartdrink.http.exceptions.HttpException;

public class WebServiceClient {

    private String servicio;
    private JSONObject request;
    private byte[] response;
    private String hostPlaca = null;
    private  int portPlaca = 50000;

    public WebServiceClient(String servicio, JSONObject request) {
        this.servicio = servicio;
        this.request = request;
    }

    protected void sendData(HttpConnection con) throws Exception {

        try {
            con.addProperty("Content-Type","application/json");
            con.addProperty("Accept","application/json");
            response = con.send(request.toString().getBytes());
        } catch (HttpException e){

        } catch (ClientException e) {

        }catch(Exception e1){

        }
    }

    public Object getResponse(){

        hostPlaca = Configuracion.getInstance().getIp();
        JSONObject obj = new JSONObject();
        try {
            URL url = new URL("http", hostPlaca, portPlaca, servicio);
            HttpClient httpClient = new HttpClient();
            HttpConnection httpConnection = httpClient.getConnection(url,
                    120000, 120000, new Authentication(), "POST");
            httpConnection.addProperty("Content-Type","application/json");
            response = httpConnection.send(request.toString().getBytes());
            InputStream stream = new ByteArrayInputStream(response);
            InputStreamReader ir = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(ir);

            // JsonReader JsonReader = new JsonReader(ir);
            obj = new JSONObject(slurp(stream));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    private static String slurp(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();
        return sb.toString();
    }
}