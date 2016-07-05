package myPrograms;

import org.apache.commons.io.IOUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.DirectXmlRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class LoadXML {

	public static void main(String[] args) throws SolrServerException, IOException, ParserConfigurationException, SAXException {
		String url = "http://52.38.229.127:8983/solr/test";
		SolrClient solr = new HttpSolrClient(url);
		File f=new File("/Users/hal/Desktop/SigmodRecord2.xml"); 
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
		DocumentBuilder builder=factory.newDocumentBuilder(); 
		Document doc = builder.parse(f); 
		NodeList nl = doc.getElementsByTagName("articlesTuple");
		System.out.println(nl.getLength());
		for(int i=0;i<nl.getLength();i++){
			System.out.println(i);
			SolrInputDocument document = new SolrInputDocument();		
			String title = doc.getElementsByTagName("title").item(i).getFirstChild().getNodeValue();
			document.addField("title", title);
			try{
				String initPage = doc.getElementsByTagName("initPage").item(i).getFirstChild().getNodeValue();
			}catch(Exception e){
				String initPage = "";
			}
			try{
				String endPage = doc.getElementsByTagName("endPage").item(i).getFirstChild().getNodeValue();
			}catch(Exception e){
				String endPage = "";
			}
			int authorCount = (doc.getElementsByTagName("authors").item(i).getChildNodes().getLength())/2;
			String authorName = "";
			for(int j=0;j<authorCount;j++){
				try{
					authorName = doc.getElementsByTagName("authors").item(i).getChildNodes().item((j*2)+1).getFirstChild().getNodeValue();
				}catch(Exception e){
					authorName = "";
				}	
				String authorPosition = doc.getElementsByTagName("authors").item(i).getChildNodes().item((j*2)+1).getAttributes().getNamedItem("AuthorPosition").getNodeValue();
				document.addField("authorName", authorName);
				document.addField("authorPosition", authorPosition);
			}
			//System.out.println(document);
			solr.add(document);
			if(i%100==0){
				solr.commit();
			}
		}
		solr.commit();
		System.out.println("done");
}
}
