package com.daluga.mds.servicios;

import com.daluga.mds.modelos.Archivos;
import com.daluga.mds.modelos.Directorios;
import com.daluga.mds.modelos.Importancia;
import com.daluga.mds.modelos.Paginacion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class GestionDocumentalServicios {
    private static final String URL_DIRECTORIOS_TODOS = "http://192.168.1.228:8085/directorio";
    private static final String URL_ARCHIVOS = "http://192.168.1.228:8085/archivo";
    private static final String URL_ARCHIVOS_BY_DOCUMENTS = "http://192.168.1.228:8085/archivo/get_dir_id";
    private static final String URL_ARCHIVOS_BY_NOMBRE_DIRECTORIO = "http://192.168.1.228:8085/archivo/get_by_name";
    private static final String URL_ARCHIVO_GUARDAR = "http://192.168.1.228:8085/archivo";
    private static final String URL_ARCHIVO_PAGINACION = "http://192.168.1.228:8085/archivo/paginacion";
    private static final String URL_ARCHIVO_IMPORTANCIA = "http://192.168.1.228:8085/importancia";
    private static final String URL_ARCHIVO_PAGINADA = "http://192.168.1.228:8085/archivo/paginada";

    public List<Directorios> obtenerDirectorios() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet getAllDir = new HttpGet(URL_DIRECTORIOS_TODOS);
        CloseableHttpResponse response = client.execute(getAllDir);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String bodyAsString = EntityUtils.toString(response.getEntity());
            ObjectMapper ob = new ObjectMapper();
            List<Directorios> directorios = ob.readValue(bodyAsString, new TypeReference<List<Directorios>>(){});
            client.close();
            return directorios;
        }
        return null;
    }

    public List<Archivos> obtenerArchivos()throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet getAllFiles = new HttpGet(URL_ARCHIVOS);
        CloseableHttpResponse response = client.execute(getAllFiles);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String bodyAsString = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            List<Archivos> archivos = objectMapper.readValue(bodyAsString, new TypeReference<>() {});
            client.close();
            return archivos;
        }
        return null;
    }

    public List<Archivos> obtenerArchivos(Paginacion paginacion)throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet getAllFiles = new HttpGet(URL_ARCHIVOS+"?pageNum="+paginacion.getPageNum()+"&pageSize="+ paginacion.getPageSize());
        CloseableHttpResponse response = client.execute(getAllFiles);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String bodyAsString = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            List<Archivos> archivos = objectMapper.readValue(bodyAsString, new TypeReference<>() {});
            client.close();
            return archivos;
        }
        return null;
    }

    public List<Archivos> obtenerArchivos(Long idDir) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet getAllFiles = new HttpGet(URL_ARCHIVOS_BY_DOCUMENTS+"?id="+idDir);
        CloseableHttpResponse response = client.execute(getAllFiles);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String bodyAsString = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            List<Archivos> archivos = objectMapper.readValue(bodyAsString, new TypeReference<>() {});
            client.close();
            return archivos;
        }
        return null;
    }
    public List<Archivos> obtenerArchivosByIdAndName(Long idDir, String nombre) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet getAllFiles = new HttpGet(URL_ARCHIVOS_BY_NOMBRE_DIRECTORIO+"?id="+idDir+"&nombre="+nombre);
        CloseableHttpResponse response = client.execute(getAllFiles);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String bodyAsString = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            List<Archivos> archivos = objectMapper.readValue(bodyAsString, new TypeReference<>() {});
            client.close();
            return archivos;
        }
        return null;
    }
    public Archivos guardarArchivo(Archivos archivo, File documento) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(URL_ARCHIVO_GUARDAR);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("ubicacionFisica", archivo.getUbicacionFisica());
        builder.addTextBody("area", archivo.getArea().getId().toString());
        builder.addTextBody("importancia", archivo.getImportancia().getId().toString());
        builder.addTextBody("descripcion", String.valueOf(archivo.getDescripcion()));
        builder.addTextBody("fechaCreacion", String.valueOf(archivo.getFechaCreacion()));
        builder.addTextBody("fechaSubida", String.valueOf(archivo.getFechaSubida()));
        builder.addTextBody("nota", String.valueOf(archivo.getNota()));
        builder.addTextBody("estado", String.valueOf(archivo.getEstado()));
        builder.addTextBody("dirName", String.valueOf(archivo.getDirectorio().getId()));
        builder.addBinaryBody(
                "file", documento, ContentType.APPLICATION_OCTET_STREAM, documento.getName());
        HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);

        CloseableHttpResponse response = client.execute(httpPost);
        String bodyAsString = EntityUtils.toString(response.getEntity());
        client.close();

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString,Archivos.class);
    }
    public Paginacion paginacion(Paginacion paginacion) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet getAllFiles = new HttpGet(URL_ARCHIVO_PAGINACION+"?pageNum="+ paginacion.getPageNum() +"&pageSize="+ paginacion.getPageSize());
        CloseableHttpResponse response = client.execute(getAllFiles);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String bodyAsString = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(bodyAsString);
            Paginacion pag = objectMapper.readValue(bodyAsString, Paginacion.class);
            client.close();
            return pag;
        }
        return null;
    }
    public Directorios guardarDirectorio(Directorios directorios){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(URL_DIRECTORIOS_TODOS);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(directorios);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPost);
            String bodyAsString = EntityUtils.toString(response.getEntity());
            return objectMapper.readValue(bodyAsString, Directorios.class);
        } catch (JsonProcessingException e) {
            System.out.println("error en convertir a json");
        } catch (UnsupportedEncodingException e) {
            System.out.println("error en codificar");
        } catch (IOException e) {
            System.out.println("error en guardar el area");
        }
        return null;
    }
    public Importancia guardarImportancia(Importancia importancia){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(URL_ARCHIVO_IMPORTANCIA);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(importancia);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPost);
            String bodyAsString = EntityUtils.toString(response.getEntity());
            return objectMapper.readValue(bodyAsString, Importancia.class);
        } catch (JsonProcessingException e) {
            System.out.println("error en convertir a json");
        } catch (UnsupportedEncodingException e) {
            System.out.println("error en codificar");
        } catch (IOException e) {
            System.out.println("error en guardar el area");
        }
        return null;
    }
    public List<Importancia> obtenerImportancia(){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL_ARCHIVO_IMPORTANCIA);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            String bodyAsString = EntityUtils.toString(response.getEntity());
            return objectMapper.readValue(bodyAsString,new TypeReference<>(){});
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
