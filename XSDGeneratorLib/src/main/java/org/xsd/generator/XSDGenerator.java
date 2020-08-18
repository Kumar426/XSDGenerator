package org.xsd.generator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Extend XSDGenerator to generate XSD.
 */
public abstract class XSDGenerator implements XMLCommonConstants {
  private Document doc;

  public Document createDoc() {
    try {
      this.doc = docConfig();
    } catch (Exception ex) {
      throw new RuntimeException();
    }
    return doc;
  }

  /**
   * Crate unbounded type of element. Use for array types object.
   * This method will generate required array element [1+]
   * @param nodeName
   * @param seqPath
   * @return
   */
  public Element arrayTypeElementRequired (String nodeName, Element seqPath) {
    Element element = new ElementMaker.Builder(doc)
      .setElementName(ELEMENT)
      .setNameAttrVal(nodeName)
      .setMaxOccureValue(UNBOUNDED)
      .setMinOccureValue(ONE)
      .setSequence(seqPath)
      .build();
    return buildComplexSequence(element);
  }

  /**
   * Crate unbounded type of element. Use for array types object.
   * This method will generate non required array element [0,1+]
   * @param nodeName
   * @param seqPath
   * @return
   */
  public Element arrayTypeElementNotRequired(String nodeName, Element seqPath) {
    Element element = new ElementMaker.Builder(doc)
      .setElementName(ELEMENT)
      .setNameAttrVal(nodeName)
      .setMaxOccureValue(UNBOUNDED)
      .setMinOccureValue(ZERO)
      .setSequence(seqPath)
      .build();
    return buildComplexSequence(element);
  }

  /**
   * Create Normal element. Use this with objects.
   * This method will create required object element.
   * @param nodeName : Name of the Object.
   * @param seqPath : Sequence of where you need to add.
   * @return
   */
  public Element objectTypeElementRequired (String nodeName, Element seqPath) {
    Element associateElement = new ElementMaker.Builder(doc)
      .setElementName(ELEMENT)
      .setNameAttrVal(nodeName)
      .setMaxOccureValue(ONE)
      .setMinOccureValue(ONE)
      .setSequence(seqPath)
      .build();
    return buildComplexSequence(associateElement);
  }

  /**
   * Create Normal element. Use this with objects.
   * This method will create non required object element.
   * @param nodeName : Name of the Object.
   * @param seqPath : Sequence of where you need to add.
   * @return
   */
  public Element objectTypeElementNotRequired(String nodeName, Element seqPath) {
    Element associateElement = new ElementMaker.Builder(doc)
      .setElementName(ELEMENT)
      .setNameAttrVal(nodeName)
      .setMaxOccureValue(ONE)
      .setMinOccureValue(ONE)
      .setSequence(seqPath)
      .build();
    return buildComplexSequence(associateElement);
  }

  /**
   * Create field element which is not required.
   * @param fieldName: Field name
   * @param type: Field type.
   * @param seqPath: Sequence of where you need to add.
   */
  public void fieldElement(String fieldName, String type, Element seqPath) {
    Element associateElement = new ElementMaker.Builder(doc)
      .setElementName(ELEMENT)
      .setNameAttrVal(fieldName)
      .setTypeAttrVal(type)
      .setMaxOccureValue(ONE)
      .setMinOccureValue(ZERO)
      .setSequence(seqPath)
      .build();
  }

  /**
   * Create field element which is required.
   * @param fieldName: Field name
   * @param type: Field type.
   * @param seqPath: Sequence of where you need to add.
   */
  public void requiredFieldElement (String fieldName, String type, Element seqPath) {
    Element associateElement = new ElementMaker.Builder(doc)
      .setElementName(ELEMENT)
      .setNameAttrVal(fieldName)
      .setTypeAttrVal(type)
      .setMaxOccureValue(ONE)
      .setMinOccureValue(ONE)
      .setSequence(seqPath)
      .build();
  }

  /**
   * Generate Name space.
   * @param activityName
   * @param objectName
   * @return
   */
  public static String getNamespace(String activityName, String objectName) {
    String namespace = NAMESPACE + "/" + objectName.toLowerCase().trim() + "/" + activityName;
    return namespace;
  }

  /**
   * Creating sequence for element.
   * @param resElement
   * @return
   */
  private Element buildComplexSequence(Element resElement) {
    Element com = new ElementMaker.Builder(doc).setElementName(COMPLEX_TYPE)
      .setSequence(resElement).build();
    Element seq = new ElementMaker.Builder(doc).setElementName(SEQUENCE)
      .setSequence(com).build();
    return seq;
  }

  /**
   * Generate schema root node.
   * @param nameSpace
   * @param rootName
   * @return
   */
  public Element schemaRoot(String nameSpace, String rootName) {
    Element schemaRoot = doc.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, SCHEMA);
    schemaRoot.setAttribute(ELEMENTFORMDEFAULT, QUALIFIED);
    schemaRoot.setAttribute(TARGET_NAMESPACE, nameSpace);
    Element root = new ElementMaker.Builder(doc)
      .setElementName(ELEMENT)
      .setNameAttrVal(rootName)
      .setSequence(schemaRoot)
      .build();
    doc.appendChild(schemaRoot);
    Element seq = buildComplexSequence(root);
    return seq;
  }

  /**
   * Document default configuration.
   * @return
   * @throws Exception
   */
  public Document docConfig () throws Exception {
    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();
    return doc;
  }
}
