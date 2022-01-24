package org.asdtechs.examples.hbase2;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseClientSimple {

	public static void main(String[] args) throws Exception {

		String TABLE_NAME = "MyTableFromJavaSimpleClient";
		//String CF_DEFAULT = "MyCF";

		// YOUR PATH TILL core-site.xml
		String PATH_TILL_CORE_SITE = "/home/hitman/DBDA_HOME/hadoop-3.3.1/etc/hadoop";
		// YOUR PATH TILL hbase-site.xml
		String PATH_TILL_HBASE_SITE = "/home/hitman/DBDA_HOME/hbase-2.4.9/conf";

		// Table creation
		Configuration config = HBaseConfiguration.create();
		config.addResource(new Path(PATH_TILL_CORE_SITE, "core-site.xml"));
		config.addResource(new Path(PATH_TILL_HBASE_SITE, "hbase-site.xml"));
		
		// Create a connection to HBase
		Connection connection = ConnectionFactory.createConnection(config);
		
		
		// Retrieve the admin object from connection
		Admin admin = connection.getAdmin();
		
		// Step 1. Create column family instances
		List<ColumnFamilyDescriptor> columnFamilies = new ArrayList<ColumnFamilyDescriptor>();
		columnFamilies.add(ColumnFamilyDescriptorBuilder.newBuilder("MyColFamily1".getBytes()).build());

		// Step 2. Add the columns to the table descriptor
		TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf(TABLE_NAME))
				.setColumnFamilies(columnFamilies).build();

		// Commit Step 3. create the table onto HBase
		admin.createTable(tableDescriptor);
		System.out.println("Table created : " + tableDescriptor.getTableName());

		// List the tables in HBase
		List<TableDescriptor> tables = admin.listTableDescriptors();
		System.out.println(" ========= Printing table list ======= ");
		for (TableDescriptor tableDesc : tables) {
			for (ColumnFamilyDescriptor family : tableDesc.getColumnFamilies()) {
				System.out.println("Table : " + tableDesc.getTableName().getNameAsString() + " has ColFamily : "
						+ family.getNameAsString());
			}
		}

		// Now add some rows to table
		
		Table table = connection.getTable(tableDescriptor.getTableName());
		long startTime = System.currentTimeMillis();
		int numRows = 1000000;
		byte[] columnFamily1 = Bytes.toBytes("MyColFamily1");
		byte[] column1 = Bytes.toBytes("MyColumn1"); // + String.valueOf(i));
		byte[] column2 = Bytes.toBytes("MyColumn2"); // + String.valueOf(i));

		for (int i = 1; i <= numRows; i++) {
			// Add the row_keys
			byte[] row = Bytes.toBytes("row" + i);
			Put putRow = new Put(row);
			
			// Add the values
			byte[] value1 = Bytes.toBytes("value1" + i);
			byte[] value2 = Bytes.toBytes("value2" + i);
			putRow.addColumn(columnFamily1, column1, value1);
			putRow.addColumn(columnFamily1, column2, value2);
			
			// Commit: Put the values in table
			table.put(putRow);
		}
		long endTime = System.currentTimeMillis();

		System.out.println("Insert Time taken: " + (endTime - startTime) + " millis");

		// Retrieve data from col family
		Get get = new Get(Bytes.toBytes("row1"));
		startTime = System.currentTimeMillis();
		Result result = table.get(get);
		endTime = System.currentTimeMillis();
		
		System.out.println("Get1:	" + result);
		byte[] columnValue = result.getValue(Bytes.toBytes("MyColFamily1"), Bytes.toBytes("MyColumn1"));
		System.out.println("Get2:	" + new String(columnValue));
		System.out.println("Retrieve Time taken: " + (endTime - startTime) + " millis");

		// How to delete a table
		admin.disableTable(TableName.valueOf(TABLE_NAME));
		admin.deleteTable(TableName.valueOf(TABLE_NAME));
		System.out.println("Table deleted successfully");
	}
	
}
