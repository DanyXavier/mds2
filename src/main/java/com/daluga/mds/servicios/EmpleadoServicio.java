package com.daluga.mds.servicios;

import com.daluga.mds.modelos.Archivos;
import com.daluga.mds.modelos.Areas;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class EmpleadoServicio {
    private static final String AREA_REST = "http://localhost:4000/area";
    private static final String EMPLEADO_REST = "http://localhost:4000/empleado";
    public Areas guardarArea(Areas area){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(AREA_REST);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(area);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPost);
            String bodyAsString = EntityUtils.toString(response.getEntity());
            return objectMapper.readValue(bodyAsString,Areas.class);
        } catch (JsonProcessingException e) {
            System.out.println("error en convertir a json");
        } catch (UnsupportedEncodingException e) {
            System.out.println("error en codificar");
        } catch (IOException e) {
            System.out.println("error en guardar el area");
        }
        return null;
    }
    public List<Areas> obtenerAreas(){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpPost = new HttpGet(AREA_REST);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            CloseableHttpResponse response = client.execute(httpPost);
            String bodyAsString = EntityUtils.toString(response.getEntity());
            return objectMapper.readValue(bodyAsString, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            System.out.println("error en convertir a json");
        } catch (UnsupportedEncodingException e) {
            System.out.println("error en codificar");
        } catch (IOException e) {
            System.out.println("error en guardar el area");
        }
        return null;
    }
}
