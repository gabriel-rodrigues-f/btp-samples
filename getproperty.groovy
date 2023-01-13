import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.util.*;
import com.sap.it.api.mapping.*;
import com.sap.gateway.ip.core.customdev.util.Message;
import java.text.SimpleDateFormat;  
import java.text.DateFormat;
import groovy.json.JsonOutput;
import groovy.xml.MarkupBuilder;
import com.sap.it.api.ITApiFactory;
import com.sap.it.api.mapping.ValueMappingApi;
import com.sap.gateway.ip.core.customdev.util.Message;
import javax.xml.namespace.QName;
import com.sap.gateway.ip.core.customdev.util.SoapHeader;
import com.sap.it.api.securestore.SecureStoreService
import com.sap.it.api.securestore.UserCredential
import com.sap.it.api.ITApiFactory
import java.util.HashMap;
import java.security.*;
import groovy.json.*;
import groovy.util.CharsetToolkit;
import groovy.xml.XmlUtil;

//****************************************************************************
// Get a iFlow Property
//****************************************************************************
def String getProperty(String propertyName, MappingContext context) {

    def property = context.getProperty(propertyName);
	return property.toString();

}