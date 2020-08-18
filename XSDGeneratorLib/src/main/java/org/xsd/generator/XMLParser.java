package org.xsd.generator;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 *
 * Common code that can be use in the XSD generation.
 *
 */
public class XMLParser implements XMLCommonConstants {

  private static Logger logger = Logger.getLogger(XMLParser.class.getName());

  /**
   * Document Builder
   * @return
   * @throws Exception
   */
  public DocumentBuilder createDocument() throws Exception {
    DocumentBuilder newXmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    return newXmlDocument;
  }

 /**
  * convert the document and node into a string. 
  * @param doc
  * @return
  * @throws Exception
  */
  public static String xmlConfig(Node doc) throws Exception {
    StringWriter sw = null;
    try {
      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer transformer = tFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      DOMSource domSource = new DOMSource(doc);
      sw = new StringWriter();
      StreamResult sr = new StreamResult(sw);
      transformer.transform(domSource, sr);
      return sw.toString();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("XML config: " + e.getLocalizedMessage());
      throw new RuntimeException(e.getLocalizedMessage(), e);
    } finally {
      try {
        if (sw != null) {
          sw.close();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
        logger.error("Clossing stream writer " + ex.getLocalizedMessage());
        throw new RuntimeException(ex.getLocalizedMessage(), ex);
      }
    }
  }

}
