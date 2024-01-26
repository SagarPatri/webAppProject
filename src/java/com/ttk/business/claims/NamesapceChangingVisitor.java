package com.ttk.business.claims;


import java.util.ListIterator;

import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.VisitorSupport;
import org.dom4j.Element;

public class NamesapceChangingVisitor extends VisitorSupport {
	  private Namespace from;
	  private Namespace to;

	  public NamesapceChangingVisitor(Namespace from, Namespace to) {
	    this.from = from;
	    this.to = to;
	  }

	  public void visit(Element node) {
	    Namespace ns = node.getNamespace();

	    if (ns.getURI().equals(from.getURI())) {
	      QName newQName = new QName(node.getName(), to);
	      node.setQName(newQName);
	    }

	    ListIterator namespaces = node.additionalNamespaces().listIterator();
	    while (namespaces.hasNext()) {
	      Namespace additionalNamespace = (Namespace) namespaces.next();
	      if (additionalNamespace.getURI().equals(from.getURI())) {
	        namespaces.remove();
	      }
	    }
	  }
}

