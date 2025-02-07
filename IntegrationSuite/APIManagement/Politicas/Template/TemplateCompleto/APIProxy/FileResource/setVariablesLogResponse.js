const iStatusCode = context.getVariable("response.status.code");
const jContent = context.getVariable("response.content");

var ddstatus = "success";

var payload = jContent.split('"').join("'");
payload = payload.replace(/(\r\n|\n|\r)/gm, "");


if(iStatusCode < 200 || iStatusCode > 299){
    ddstatus = "error";
    //payload = jContent;
}

context.setVariable("postflow.ddstatus",ddstatus);
context.setVariable("postflow.payload",payload);
