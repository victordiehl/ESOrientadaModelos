package krepo.builder;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;



public class SampleBuilder extends IncrementalProjectBuilder {

	class SampleDeltaVisitor implements IResourceDeltaVisitor {
		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource resource = delta.getResource();
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
				// handle added resource
				checkXML(resource);
				break;
			case IResourceDelta.REMOVED:
				// handle removed resource
				break;
			case IResourceDelta.CHANGED:
				// handle changed resource
				checkXML(resource);
				break;
			}
			//return true to continue visiting children.
			return true;
		}
	}

	class SampleResourceVisitor implements IResourceVisitor {
		public boolean visit(IResource resource) {
			checkXML(resource);
			//return true to continue visiting children.
			return true;
		}
	}

	class XMLErrorHandler extends DefaultHandler {
		
		private IFile file;

		public XMLErrorHandler(IFile file) {
			this.file = file;
		}

		private void addMarker(SAXParseException e, int severity) {
			SampleBuilder.this.addMarker(file, e.getMessage(), e
					.getLineNumber(), severity);
		}

		public void error(SAXParseException exception) throws SAXException {
			addMarker(exception, IMarker.SEVERITY_ERROR);
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			addMarker(exception, IMarker.SEVERITY_ERROR);
		}

		public void warning(SAXParseException exception) throws SAXException {
			addMarker(exception, IMarker.SEVERITY_WARNING);
		}
	}

	public static final String BUILDER_ID = "krepo.sampleBuilder";

	private static final String MARKER_TYPE = "krepo.xmlProblem";

	private SAXParserFactory parserFactory;

	private void addMarker(IFile file, String message, int lineNumber,
			int severity) {
		try {
			IMarker marker = file.createMarker(MARKER_TYPE);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			if (lineNumber == -1) {
				lineNumber = 1;
			}
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
		} catch (CoreException e) {
		}
	}

	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor)
			throws CoreException {
		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		return null;
	}

	protected void clean(IProgressMonitor monitor) throws CoreException {
		// delete markers set and files created
		getProject().deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_INFINITE);
	}

	void checkXML(IResource resource) {
		  if (resource instanceof IFile && resource.getName().endsWith(".properties")) {
		    IFile file = (IFile) resource;
		    changeTrChars(file);
		  } 
		}
	
	private void changeTrChars(IFile file) {
		  InputStream is = null;
		  DataOutputStream out = null;
		  try {
		    is = file.getContents();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		    String line = null;
		    String finalLine = "";
		    while ((line = reader.readLine()) != null) {
		      //concanate the lines
		      finalLine += line + "\n";
		    }
		    //we are done with the input stream
		    is.close();
		    //convert chars
		    String toOut = convertTr(finalLine);
		    //if any chars have been changed
		    if (!toOut.equals(finalLine)) {
		      File fileTo = new File(file.getProject().getLocation().toPortableString() + "/" + file.getProjectRelativePath().toPortableString());
		      out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileTo)));
		      //save the file
		      out.writeBytes(toOut);
		      out.close();
		    }
		  } catch (Exception e) {
		    e.printStackTrace();
		  } finally {

		  }
		}
	
	private String convertTr(String nativeText) {
		  Map charMap = new HashMap();
		  charMap.put("ç", "\\\\u00e7");
		  charMap.put("Ç", "\\\\u00c7");
		  charMap.put("ğ", "\\\\u011f");
		  charMap.put("Ğ", "\\\\u011e");
		  charMap.put("ş", "\\\\u015f");
		  charMap.put("Ş", "\\\\u015e");
		  charMap.put("ı", "\\\\u0131");
		  charMap.put("İ", "\\\\u0130");
		  charMap.put("ö", "\\\\u00f6");
		  charMap.put("Ö", "\\\\u00d6");
		  charMap.put("ü", "\\\\u00fc");
		  charMap.put("Ü", "\\\\u00dc");
		  String asciiText = "";
		  if (nativeText != null) {
		    asciiText = new String(nativeText);
		    Set keySet = charMap.keySet();
		    Iterator it = keySet.iterator();
		    while (it.hasNext()) {
		      String nativeChar = (String) it.next();
		      String asciiChar = (String) charMap.get(nativeChar);
		      asciiText = asciiText.replaceAll(nativeChar, asciiChar);
		    }
		  }
		  return asciiText;
		}

	private void deleteMarkers(IFile file) {
		try {
			file.deleteMarkers(MARKER_TYPE, false, IResource.DEPTH_ZERO);
		} catch (CoreException ce) {
		}
	}

	protected void fullBuild(final IProgressMonitor monitor)
			throws CoreException {
		try {
			getProject().accept(new SampleResourceVisitor());
		} catch (CoreException e) {
		}
	}

	private SAXParser getParser() throws ParserConfigurationException,
			SAXException {
		if (parserFactory == null) {
			parserFactory = SAXParserFactory.newInstance();
		}
		return parserFactory.newSAXParser();
	}

	protected void incrementalBuild(IResourceDelta delta,
			IProgressMonitor monitor) throws CoreException {
		// the visitor does the work.
		delta.accept(new SampleDeltaVisitor());
	}
}
