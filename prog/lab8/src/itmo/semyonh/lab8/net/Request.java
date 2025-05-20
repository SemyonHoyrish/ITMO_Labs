package itmo.semyonh.lab8.net;

public record Request(long id, String commandName, String commandJson, Credentials creds) {}
