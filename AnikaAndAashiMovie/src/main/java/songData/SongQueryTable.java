package songData;

import java.util.ArrayList;


import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

public class SongQueryTable {
	
	public static void main(String[] args)
	{
			
	ArrayList<Item> stuffs = connect();
	
	System.out.print("made it");
	for(Item thing:stuffs)
	{
		System.out.println(thing.get("Title"));
		System.out.println(thing.get("Artist"));

	}
	}

	public static ArrayList<Item> connect() {
		ArrayList<Item>itemList = new ArrayList<Item>();
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/Users/anikasood/.aws/credentials), and is in valid format.",
                    e);
        }
       AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
        	.withCredentials(credentialsProvider)
            .withRegion("us-west-1")
            .build();

		DynamoDB dynamoDB = new DynamoDB(client);
	    Table table = dynamoDB.getTable("Songs");
	    ScanRequest scanRequest = new ScanRequest()
	    	    .withTableName("Songs");


	    ScanResult result = client.scan(scanRequest);
	    Item item;
	   

	    for(int x = 1; x <= result.getCount(); x++)
	    {
	    	item = table.getItem("Title", "Artist");
	    	itemList.add(item);
	 
	    }
	    
	    return itemList; 
}
}
