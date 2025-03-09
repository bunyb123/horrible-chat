package com.chatapp.server;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.WebSocket;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChatServer extends WebSocketServer {
    private final Set<WebSocket> clients = Collections.synchronizedSet(new HashSet<>());

    public ChatServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        clients.add(conn);
        System.out.println("New connection: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        clients.remove(conn);
        System.out.println("Connection closed: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received: " + message);
        broadcastMessage(message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Chat server started on port " + getPort());
    }

    private void broadcastMessage(String message) {
        synchronized (clients) {
            for (WebSocket client : clients) {
                client.send(message);
            }
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        ChatServer server = new ChatServer(port);
        server.start();
        System.out.println("WebSocket chat server running on ws://localhost:" + port);
    }
}
