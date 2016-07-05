package myPrograms;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class CalculateScore {

	public static void generateNAS(String name) throws IOException, SolrServerException{
		//String name = "George P. Copeland";
		String url = "http://52.38.229.127:8983/solr/test";
		SolrClient solr = new HttpSolrClient(url);
		SolrQuery query = new SolrQuery();
		String queryString = "authorName:\""+name+"\"";
		query.setQuery(queryString);
		query.setFields("id","title","authorName","authorPosition");
		QueryResponse response = solr.query(query);
		SolrDocumentList list = response.getResults();
		System.out.println("Normal author score:"+list.getNumFound());
		double sumWAS = 0;
		for(SolrDocument doc : list){ 
			int indexCount = 0;
			int nameIndex = 0;
			int authorCount = doc.getFieldValues("authorPosition").size();
			for (Object s:doc.getFieldValues("authorName")) {
				String sa = s.toString();
				if(sa.equals(name)){
					nameIndex = indexCount;
					break;
				}
				indexCount+=1;
			}
			indexCount = 0;
			int rankCount = 0;
			for (Object s:doc.getFieldValues("authorPosition")) {
				if(indexCount==nameIndex){
					rankCount = Integer.parseInt(s.toString());
				}
				indexCount+=1;
			}
			sumWAS+=(authorCount-rankCount)/(double)authorCount; 	
		} 
		System.out.println("Weighted author score:"+sumWAS);
	}
	
	

}
