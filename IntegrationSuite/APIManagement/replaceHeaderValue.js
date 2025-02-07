var authorizationHeader = context.getVariable("request.header.Authorization");
var customAuthorizationHeader = authorizationHeader.replace("Token", "");
context.setVariable("request.header.Authorization", customAuthorizationHeader);