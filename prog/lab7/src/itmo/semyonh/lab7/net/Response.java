package itmo.semyonh.lab7.net;

public record Response(long requestID, Status status, ResponseType type, String response) {}
