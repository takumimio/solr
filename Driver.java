package myPrograms;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

public class Driver {

public static void main(String[] args) throws IOException, SolrServerException {
		
		CalculateScore cs = new CalculateScore();
		cs.generateNAS("Rakesh Agrawal");
		
	}

}
