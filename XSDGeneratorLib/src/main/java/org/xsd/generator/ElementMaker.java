package org.xsd.generator;

import lombok.Data;
import lombok.ToString;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * builder class for building XSD element.
 */
@Data
@ToString
public class ElementMaker implements XMLCommonConstants  {

    private final String elementName;
    private final String nameAttrVal;
    private final String typeAttrVal;
    private final String maxOccureValue;
    private final String minOccureValue;
    private final Element sequence;
    private final Document doc;

    private ElementMaker (Builder builder) {
      this.elementName = builder.elementName;
      this.nameAttrVal = builder.nameAttrVal;
      this.typeAttrVal = builder.typeAttrVal;
      this.maxOccureValue = builder.maxOccureValue;
      this.minOccureValue = builder.minOccureValue;
      this.sequence = builder.sequence;
      this.doc = builder.doc;
    }

    public static class Builder {

      private String elementName;
      private String nameAttrVal;
      private String typeAttrVal;
      private String maxOccureValue;
      private String minOccureValue;
      private Element sequence;
      private Document doc;

      public Builder(Document doc) {
        this.doc = doc;
      }

      public Builder setElementName(String elementName) {
        this.elementName = elementName;
        return this;
      }
      public Builder setNameAttrVal(String nameAttrVal) {
        this.nameAttrVal = nameAttrVal;
        return this;
      }
      public Builder setTypeAttrVal(String typeAttrVal) {
        this.typeAttrVal = typeAttrVal;
        return this;
      }
      public Builder setMaxOccureValue(String maxOccureValue) {
        this.maxOccureValue = maxOccureValue;
        return this;
      }
      public Builder setMinOccureValue(String minOccureValue) {
        this.minOccureValue = minOccureValue;
        return this;
      }
      public Builder setSequence(Element sequence) {
        this.sequence = sequence;
        return this;
      }

      public Element build() {
       return addElementInCurrentSequence(new ElementMaker(this));
      }

      private Element addElementInCurrentSequence(ElementMaker params) {
        Element element = doc.createElement(params.getElementName());
        if (params.getNameAttrVal() != null) {
          element.setAttribute(NAME, params.getNameAttrVal());
        }
        if (params.getTypeAttrVal() != null) {
          element.setAttribute(TYPE, params.getTypeAttrVal());
        }
        if (params.getMinOccureValue() != null) {
          element.setAttribute(MIN_OCCURS, params.getMinOccureValue());
        }
        if (params.getMaxOccureValue() != null) {
          element.setAttribute(MAX_OCCURS, params.getMaxOccureValue());
        }
        if (params.getSequence() != null) {
          params.getSequence().appendChild(element);
        }
        return element;
      }
    }

  }