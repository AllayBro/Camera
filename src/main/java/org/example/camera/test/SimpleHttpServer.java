package org.example.camera.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Простой HTTP-сервер для выдачи WSDL и XSD-файлов по порту 8080.
 * Не требует внешнего контейнера. Использует classpath.
 */
public class SimpleHttpServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("=== SimpleHttpServer started on port 8080 ===");
            System.out.println("WSDL: http://localhost:8080/wsdl/DroneServiceConcrete.wsdl");
            System.out.println("Stop with Ctrl+C");

            while (true) {
                Socket client = serverSocket.accept();
                new Thread(() -> handle(client)).start();
            }
        } catch (IOException e) {
            System.err.println("Server start error: " + e.getMessage());
        }
    }

    private static void handle(Socket client) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             OutputStream out = client.getOutputStream()) {

            String request = in.readLine();
            if (request == null) return;
            System.out.println("Request: " + request);

            String path = request.split(" ")[1];
            String resource;
            if (path.equals("/WEB-INF/wsdl/DroneServiceConcrete.wsdl"))
                resource = "WEB-INF/wsdl/DroneServiceConcrete.wsdl";
            else if (path.equals("/wsdl/DroneServiceAbstract.wsdl"))
                resource = "wsdl/DroneServiceAbstract.wsdl";
            else if (path.equals("/wsdl/drone_target.xsd"))
                resource = "drone_target.xsd";
            else {
                send404(out);
                return;
            }

            byte[] content = readResource(resource);
            sendResponse(out, 200, "application/xml; charset=UTF-8", content);

        } catch (Exception e) {
            System.err.println("Request error: " + e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException ignored) {}
        }
    }

    private static byte[] readResource(String path) throws IOException {
        try (InputStream in = SimpleHttpServer.class.getClassLoader().getResourceAsStream(path)) {
            if (in == null) throw new IOException("Resource not found: " + path);
            return in.readAllBytes();
        }
    }

    private static void sendResponse(OutputStream out, int code, String type, byte[] body) throws IOException {
        out.write(("HTTP/1.1 " + code + " OK\r\n").getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Type: " + type + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Length: " + body.length + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
        out.write(body);
    }

    private static void send404(OutputStream out) throws IOException {
        String html = "<html><body><h1>404 Not Found</h1></body></html>";
        sendResponse(out, 404, "text/html; charset=UTF-8", html.getBytes(StandardCharsets.UTF_8));
    }
}
