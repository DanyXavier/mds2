package com.daluga.mds.servicios;

import com.daluga.mds.modelos.Archivos;
import com.daluga.mds.modelos.Directorios;
import com.daluga.mds.modelos.Importancia;
import com.daluga.mds.modelos.Paginacion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GestionDocumentalServicios {
    private static final String URL_DIRECTORIOS_TODOS = "http://localhost:4000/directorio";
    private static final String URL_ARCHIVOS = "http://localhost:4000/archivo";
    private static final String URL_ARCHIVOS_BY_DOCUMENTS = "http://localhost:4000/archivo/get_dir_id";
    private static final String URL_ARCHIVOS_BY_NOMBRE_DIRECTORIO = "http://localhost:4000/archivo/get_by_name";
    private static final String URL_ARCHIVO_GUARDAR = "http://localhost:4000/archivo";
    private static final String URL_ARCHIVO_PAGINACION = "http://localhost:4000/archivo/paginacion";
    private static final String URL_ARCHIVO_IMPORTANCIA = "http://localhost:4000/directorio/importancia";
    private static final String URL_ARCHIVOS_DOWNLOAD = "http://localhost:4000/archivo/download";
    //192.168.1.228:8085

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
        builder.addTextBody("directorio", String.valueOf(archivo.getDirectorio().getId()));
        builder.addTextBody("nombre", archivo.getArchivo());
        builder.addTextBody("ubicacionFisica", archivo.getUbicacionFisica());
        builder.addTextBody("area", archivo.getArea().getId().toString());
        builder.addTextBody("importancia", archivo.getImportancia().getId().toString());
        builder.addTextBody("descripcion", String.valueOf(archivo.getDescripcion()));
        builder.addTextBody("fechaCreacion", String.valueOf(archivo.getFechaCreacion()));
        builder.addTextBody("fechaSubida", String.valueOf(archivo.getFechaSubida()));
        builder.addTextBody("nota", String.valueOf(archivo.getNota()));
        builder.addTextBody("estado", String.valueOf(archivo.getEstado()));
        builder.addTextBody("dirName", archivo.getDirectorio().getDirectorio());
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
    public File descargarArchivo(Archivos archivos, File fil){
        CloseableHttpClient client = HttpClients.custom()
                .setRedirectStrategy(new LaxRedirectStrategy()) // adds HTTP REDIRECT support to GET and POST methods
                .build();
        HttpPost httpPost = new HttpPost(URL_ARCHIVOS_DOWNLOAD);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(archivos);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            return client.execute(httpPost,new FileDownloadResponseHandler(fil));
        } catch (JsonProcessingException e) {
            System.out.println("error en convertir a json");
        } catch (UnsupportedEncodingException e) {
            System.out.println("error en codificar");
        } catch (IOException e) {
            System.out.println("error en guardar el area");
        }finally {
            IOUtils.closeQuietly(client);
        }
        //return null;
        return null;
    }
    public InputStream verPDF(Archivos archivos){
        CloseableHttpClient client = HttpClients.custom()
                .setRedirectStrategy(new LaxRedirectStrategy()) // adds HTTP REDIRECT support to GET and POST methods
                .build();
        HttpPost httpPost = new HttpPost(URL_ARCHIVOS_DOWNLOAD);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(archivos);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            String fileType = archivos.getArchivo().substring(0,archivos.getArchivo().length()-5).split("\\.")[0];
            Path temp = Files.createTempFile(archivos.getArchivo(),"."+fileType);
            File fil = temp.toFile();
            File file = client.execute(httpPost,new FileDownloadResponseHandler(fil));
            return new FileInputStream(file);
        } catch (JsonProcessingException e) {
            System.out.println("error en convertir a json");
        } catch (UnsupportedEncodingException e) {
            System.out.println("error en codificar");
        } catch (IOException e) {
            System.out.println("error en guardar el area");
        }finally {
            IOUtils.closeQuietly(client);
        }
        //return null;
        return null;
    }

    static class FileDownloadResponseHandler implements ResponseHandler<File> {

        private final File target;

        public FileDownloadResponseHandler(File target) {
            this.target = target;
        }

        @Override
        public File handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            InputStream source = response.getEntity().getContent();
            FileUtils.copyInputStreamToFile(source, this.target);
            return this.target;
        }

    }
}
