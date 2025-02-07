const clientini = context.getVariable("client.received.start.timestamp");
const clientend = context.getVariable("client.received.end.timestamp");
const finaltime = context.getVariable("system.timestamp");
const sendtarget = context.getVariable("target.sent.start.timestamp");

var apitime = finaltime - clientini;
var backendtime = finaltime - sendtarget;
var proxytime  = apitime - backendtime;

context.setVariable("postflow.apitime",apitime);
context.setVariable("postflow.backendtime",backendtime);
context.setVariable("postflow.proxytime",proxytime);

context.setVariable("postflow.message","API executed in "+apitime+"ms");