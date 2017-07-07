# Authorization Server
This project is a simple authorization server implemented by using Spring OAuth2.

## Usage
To simplify the operation, I'll demonstrate the usage of the server with simple **curl** commands.

### Getting tokens for a user
```
curl -i -X POST --user '<client-id>:<client-secret>' -d 'grant_type=password&username=<user>&password=<password>' <protocol>://<host>[:<port>]/oauth/token
```
- The *`<client-id>`* and *`<client-secret>`* can be obtained from the class [OAuth2Config](src/main/java/demos/authorizationserver/configuration/OAuth2Config.java)
- Another values for the `grant_type` field can also be obtained on the classe **OAuth2Config**
- The *`<user>`* and *`<password>`* can be obtained from the class [SecurityConfig](src/main/java/demos/authorizationserver/configuration/SecurityConfig.java)

### Validating the token with the server
```
curl -i -X POST --user '<client-id>:<client-secret>' -d 'token=<token>' <protocol>://<host>[:<port>]/oauth/check_token
```
The access to the *check_token* endpoint is granted to any authenticated user, so the used *client-id* and *client-secret* can be any if the ones listed on the **OAuth2Config** class.
