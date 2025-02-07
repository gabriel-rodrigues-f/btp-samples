const bToken = context.getVariable("oauthV2.VerifyAccessToken.failed");
const bAPIKey = context.getVariable("verifyapikey.VerifyAPIKey.failed");
const bIPControl = context.getVariable("acl.ipControl.failed");
const bJSONProtection = context.getVariable("jsonthreatprotection.mitigateJSONCodeInjection.failed");
const bXMLProtection = context.getVariable("xmlattack.mitigateXMLCodeInjection.failed");
const bQuota = context.getVariable("ratelimit.applyRateLimit.failed");

var req = context.getVariable('request.content')
req = req.split('"').join("'");
req = req.replace(/(\r\n|\n|\r)/gm, "");
context.setVariable("preflow.content",req);

var name_apiproxy = context.getVariable('apiproxy.name');
name_apiproxy = name_apiproxy.toLowerCase();
context.setVariable("preflow.name",name_apiproxy);

var name_product = context.getVariable('verifyapikey.VerifyAPIKey.apiproduct.name')
name_product = name_product.toLowerCase();
context.setVariable("product.name",name_product);

var status = 200;
var message = "Preflow executed successfully"; 
var ddstatus = "success";
switch(true){
    case bToken:
        status = 401;
        message = "Invalid AccessToken";
        break;
    case bAPIKey:
        status = 401;
        message = "InvalidApiKey";
        break;
    case bIPControl:
        status = 403;
        message = "IPDeniedAccess";
        break;
    case bJSONProtection:
        status = 500;
        message = "JSONThreatProtection The input JSON Payload is invalid.";
        break;
    case bXMLProtection:
        status = 500;
        message = "XMLThreatProtection The input XML Payload is invalid.";
        break;
    case bQuota:
        status = 429;
        message = "Too Many Requests";
        break;
}
if(status != 200){
    ddstatus = "error";
}
context.setVariable("preflow.status",status);
context.setVariable("preflow.message",message);
context.setVariable("preflow.ddstatus",ddstatus);
