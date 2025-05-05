package itmo.semyonh.lab7.net;

public record Request(long id, String commandName, String commandJson, Credentials creds) {}
