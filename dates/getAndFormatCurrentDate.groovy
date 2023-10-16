import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar

Message processData(Message message) {
    def currentDate = new Date()
    def calendar    = Calendar.getInstance()
    calendar.setTime(currentDate)

    calendar.add(Calendar.HOUR, -3)

    def formattedDate      = new SimpleDateFormat('yyyy-MM-dd').format(calendar.time)
    def formattedTime      = new SimpleDateFormat('HH-mm-ss').format(calendar.time)
    println(formattedDate)
    println(formattedTime)
    return message
}
