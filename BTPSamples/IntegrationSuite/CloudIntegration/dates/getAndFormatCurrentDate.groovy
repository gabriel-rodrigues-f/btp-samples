import com.sap.gateway.ip.core.customdev.util.Message
import java.text.SimpleDateFormat

Message processData(Message message) {
    def currentDate = new Date()
    def calendar    = Calendar.getInstance()
    calendar.setTime(currentDate)

    calendar.add(Calendar.HOUR, -3)

    def formattedDate      = new SimpleDateFormat('yyyy-MM-dd').format(calendar.time)
    def formattedTime      = new SimpleDateFormat('HH-mm-ss').format(calendar.time)

    String stringDates = "Date: ${formattedDate} and Time: ${formattedTime}"

    message.setBody(stringDates)
    return message
}
