import com.sap.it.api.mapping.MappingContext

def String getProperty(String propertyName, MappingContext context) {
    def propertyValue = context.getProperty(propertyName)
    return propertyValue
}
