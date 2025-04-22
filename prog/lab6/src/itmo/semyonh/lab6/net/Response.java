package itmo.semyonh.lab6.net;

public record Response(long requestID, Status status, ResponseType type, String response) {}
