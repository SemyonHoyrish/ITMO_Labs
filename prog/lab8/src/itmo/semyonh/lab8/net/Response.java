package itmo.semyonh.lab8.net;

public record Response(long requestID, Status status, ResponseType type, String response) {}
