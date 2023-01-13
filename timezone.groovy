import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import static java.util.Calendar.*;

def Message processData(Message message) {    
    def localTimeZone = TimeZone.getTimeZone('America/Sao_Paulo');
    def cal = Calendar.instance;
    def time = cal.time;
    def timeFormat = '\'PT\'HH\'H\'mm\'M\'ss\'S\'';
    def date = cal.time;
    def dateFormat = 'yyyy-MM-dd\'T00:00:00\'';
    
    //Property
    message.setProperty("nowDateUtc", date.format(dateFormat, localTimeZone));
	message.setProperty("nowTimeUtc", date.format(timeFormat, localTimeZone));
    
    return message;
}