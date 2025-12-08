package org.example.camera.test;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.camera.rest.AnalyticsService;
import org.example.camera.rest.AllDroneService;
import org.example.camera.rest.FinesService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;

/**
 * Lightweight HTTP server that exposes the REST endpoints and WSDL documents
 * from the project without requiring a full Jakarta EE container. Designed for
 * quick local verification on port 8090.
 */
public class LocalIntegrationServer {

    private static final int PORT = 8090;
    private static final String CONTEXT_ROOT = "/camera2";

    private final AllDroneService catalogService = new AllDroneService();
    private final AnalyticsService analyticsService = new AnalyticsService();
    private final FinesService finesService = new FinesService();

    public static void main(String[] args) throws IOException {
        LocalIntegrationServer server = new LocalIntegrationServer();
        server.start();
    }

    private void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.setExecutor(Executors.newCachedThreadPool());

        // REST endpoints
        server.createContext(CONTEXT_ROOT + "/webresources/catalog", exchange -> {
            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendMethodNotAllowed(exchange);
                return;
            }
            respondJson(exchange, catalogService.getCatalog());
        });

        server.createContext(CONTEXT_ROOT + "/webresources/analytics", exchange -> {
            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendMethodNotAllowed(exchange);
                return;
            }
            respondJson(exchange, analyticsService.getAnalytics());
        });

        server.createContext(CONTEXT_ROOT + "/webresources/fines", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String method = exchange.getRequestMethod();
                if ("GET".equalsIgnoreCase(method)) {
                    respondJson(exchange, finesService.getFines());
                } else if ("POST".equalsIgnoreCase(method)) {
                    String body = readRequestBody(exchange);
                    respondJson(exchange, finesService.calculateFine(body));
                } else {
                    sendMethodNotAllowed(exchange);
                }
            }
        });

        // WSDL and XSD
        server.createContext(CONTEXT_ROOT + "/DroneService", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            if (query != null && query.equalsIgnoreCase("wsdl")) {
                respondXml(exchange, readResource("src/main/resources/wsdl/DroneServiceConcrete.wsdl"));
            } else {
                byte[] payload = "<html><body>DroneService endpoint is available. Append ?wsdl to download the WSDL.</body></html>".getBytes(StandardCharsets.UTF_8);
                respond(exchange, "text/html; charset=UTF-8", payload, 200);
            }
        });

        server.createContext("/wsdl/DroneServiceConcrete.wsdl", exchange -> {
            respondXml(exchange, readResource("src/main/resources/wsdl/DroneServiceConcrete.wsdl"));
        });

        server.createContext("/wsdl/DroneServiceAbstract.wsdl", exchange -> {
            respondXml(exchange, readResource("src/main/resources/wsdl/DroneServiceAbstract.wsdl"));
        });

        server.createContext("/wsdl/drone_target.xsd", exchange -> {
            respondXml(exchange, readResource("src/main/resources/drone_target.xsd"));
        });

        System.out.println("=== Local integration server started ===");
        System.out.println("REST endpoints:");
        System.out.println("  GET  http://localhost:" + PORT + CONTEXT_ROOT + "/webresources/catalog");
        System.out.println("  GET  http://localhost:" + PORT + CONTEXT_ROOT + "/webresources/analytics");
        System.out.println("  GET  http://localhost:" + PORT + CONTEXT_ROOT + "/webresources/fines");
        System.out.println("  POST http://localhost:" + PORT + CONTEXT_ROOT + "/webresources/fines");
        System.out.println("WSDL endpoints:");
        System.out.println("  http://localhost:" + PORT + CONTEXT_ROOT + "/DroneService?wsdl");
        System.out.println("  http://localhost:" + PORT + "/wsdl/DroneServiceConcrete.wsdl");
        System.out.println("  http://localhost:" + PORT + "/wsdl/DroneServiceAbstract.wsdl");
        System.out.println("  http://localhost:" + PORT + "/wsdl/drone_target.xsd");
        System.out.println("Press Ctrl+C to stop.");

        server.start();
    }

    private static void respondJson(HttpExchange exchange, String payload) throws IOException {
        respond(exchange, "application/json; charset=UTF-8", payload.getBytes(StandardCharsets.UTF_8), 200);
    }

    private static void respondXml(HttpExchange exchange, byte[] payload) throws IOException {
        respond(exchange, "application/xml; charset=UTF-8", payload, 200);
    }

    private static void respond(HttpExchange exchange, String contentType, byte[] payload, int status) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(status, payload.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(payload);
        }
        exchange.close();
    }

    private static void sendMethodNotAllowed(HttpExchange exchange) throws IOException {
        byte[] payload = "Method Not Allowed".getBytes(StandardCharsets.UTF_8);
        respond(exchange, "text/plain; charset=UTF-8", payload, 405);
    }

    private static String readRequestBody(HttpExchange exchange) throws IOException {
        try (InputStream in = exchange.getRequestBody()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private static byte[] readResource(String relativePath) throws IOException {
        Path path = Path.of(relativePath);
        if (!Files.exists(path)) {
            throw new IOException("Resource not found: " + path.toAbsolutePath());
        }
        return Files.readAllBytes(path);
    }
}



