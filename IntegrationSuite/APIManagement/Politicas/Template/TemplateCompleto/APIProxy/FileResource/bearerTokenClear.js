var bearerToken = context.getVariable('request.header.Authorization');
context.setVariable("request.header.Authorization",bearerToken.replace("Bearer ", ""));