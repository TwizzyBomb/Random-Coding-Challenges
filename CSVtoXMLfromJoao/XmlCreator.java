import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlCreator {
	private String path;
	private String dest;
	private MainWindow window;
	
	XmlCreator(String srcPath, String destPath, MainWindow w){
		path = srcPath;
		dest = destPath;
		File destFile = new File(dest);
		if(!destFile.exists()) {
			destFile.mkdirs();
		}
		window = w;
	}
	
	public void run(){
		File file = new File(path);
		String finalDest = null;
		
		if(file.isFile()) {
			if(file.getName().endsWith(".csv")) {
				finalDest = dest + "\\" + file.getName();
				finalDest = finalDest.replace(".csv", ".xml");
				window.updateTextArea("Parsing file: " + file.getName());
		    	createXml(file, finalDest);
			}
			else {
				window.updateTextArea("ERROR: " + file.getName() + " is not a csv file! Stopping.");
			}
		}
		else if(file.isDirectory()) {
			File[] files = file.listFiles();
			int dirSize = files.length;
			int current = 1;
			for(File x : files) {
				if(x.isFile()) {
					if(x.getName().endsWith(".csv")) {
						finalDest = dest + "\\" + x.getName();
						finalDest = finalDest.replace(".csv", ".xml");
						window.updateTextArea("Parsing file " + current + "/" + dirSize + ": " + x.getName());
				    	createXml(x, finalDest);
					}
					else {
						window.updateTextArea("ERROR: " + x.getName() + " is not a csv file! Skipping.");
					}
				}
				else {
					window.updateTextArea("ERROR: " + x.getName() + " is not a file! Skipping.");
				}
				current++;
			}
		}
		else {
			window.updateTextArea("ERROR: " + file.getName() + " is not a file or directory! Stopping.");
		}
	}
	
	public void createXml(File file, String finalDest) {
		String fileName = file.getName();
		String appName = fileName.substring(0, fileName.indexOf("-"));
		String tableName = fileName.substring(fileName.indexOf("-")+1, fileName.length()-4);
		
		BufferedReader reader = null;

		try {

		    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder domBuilder = domFactory.newDocumentBuilder();

		    Document doc = domBuilder.newDocument();
		    
		    Element appElement = doc.createElement(appName);
		    doc.appendChild(appElement);
		    
		    Element tableElement = doc.createElement(tableName);
		    appElement.appendChild(tableElement);

		    reader = new BufferedReader(new FileReader(file));
		    
			List<String> headers = new ArrayList<String>();
			
		    int row = 0;
		    String text = reader.readLine();
		    
		    while (text != null) {
		    	String[] rowValues = text.split(",");
		    	
		        if (row == 0) {
		            for (String headerVal : rowValues) {
		            	headerVal = headerVal.replace(" ", "_");
		            	headerVal = headerVal.replace("/", "");
		            	headerVal = headerVal.replace("-", "");
		            	headerVal = headerVal.toUpperCase();
		                headers.add(headerVal);
		            }
		        } 
		        else {
		            Element rowElement = doc.createElement("ROW");
		            tableElement.appendChild(rowElement);
		            
		            for (int colNum = 0; colNum < headers.size(); colNum++) {
		                String header = headers.get(colNum);
		                String rowVal = "";
		                if(colNum < rowValues.length) {
		                	rowVal = rowValues[colNum];
		                }
		                
	                	if(rowVal != "") {
			                Element currentElement = doc.createElement(header);
			                currentElement.appendChild(doc.createTextNode(rowVal));
			                rowElement.appendChild(currentElement);
	                	}
		            }
		        }
		        row++;
		        text = reader.readLine();
		    }

		    writetoFile(doc, finalDest);
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public static void writetoFile(Document doc, String finalDest) {
		ByteArrayOutputStream baos = null;
	    OutputStreamWriter osw = null;

	    try {
	        baos = new ByteArrayOutputStream();
	        osw = new OutputStreamWriter(baos);

	        TransformerFactory tranFactory = TransformerFactory.newInstance();
	        Transformer aTransformer = tranFactory.newTransformer();
	        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        aTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	        Source src = new DOMSource(doc);
	        Result result = new StreamResult(osw);
	        aTransformer.transform(src, result);
	        
	        osw.flush();
	        
	        try(OutputStream outputStream = new FileOutputStream(finalDest)) {
	            baos.writeTo(outputStream);
	        }
	    } catch (Exception exp) {
	        exp.printStackTrace();
	    } finally {
	        try {
	            osw.close();
	        } catch (Exception e) {
	        }
	        try {
	            baos.close();
	        } catch (Exception e) {
	        }
	    }
	}
}
