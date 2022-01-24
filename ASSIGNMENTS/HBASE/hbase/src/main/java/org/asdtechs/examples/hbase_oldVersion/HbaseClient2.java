package org.asdtechs.examples.hbase_oldVersion;
//package org.cdac.examples.hbase;
//
//import java.io.IOException;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.HColumnDescriptor;
//import org.apache.hadoop.hbase.HTableDescriptor;
//import org.apache.hadoop.hbase.KeyValue;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.Admin;
//import org.apache.hadoop.hbase.client.Connection;
//import org.apache.hadoop.hbase.client.ConnectionFactory;
//import org.apache.hadoop.hbase.client.Get;
//import org.apache.hadoop.hbase.client.HBaseAdmin;
//import org.apache.hadoop.hbase.client.HTable;
//import org.apache.hadoop.hbase.client.Put;
//import org.apache.hadoop.hbase.client.Result;
//import org.apache.hadoop.hbase.client.ResultScanner;
//import org.apache.hadoop.hbase.client.Scan;
//import org.apache.hadoop.hbase.util.Bytes;
//
//public class HbaseClient2 {
//
//	public static void main(String[] args) throws IOException {
//		
//		Configuration config = HBaseConfiguration.create();
//		// HBase admin handle can perform operations over HBase 
//		Configuration config = HBaseConfiguration.create();
//		Connection connection = ConnectionFactory.createConnection(config);
//		Admin admin = connection.getAdmin();
//
////		HBaseAdmin admin = new HBaseAdmin(config);
////		HBaseAdmin admin1 = HBaseAdmin.available(config);
//		
//		try {
//			// Create a table
//			TableName tableName = TableName.valueOf("MyJavaClientTable");
//			HTableDescriptor htd = new HTableDescriptor(tableName);
//			HColumnDescriptor hcd = new HColumnDescriptor("MyColumnFamily");
//			htd.addFamily(hcd);
//			admin.createTable(htd);
//			
//			System.out.println("Table created successfully");
//			
//			HTableDescriptor[] tables = admin.listTables();
//			System.out.println(" ========= Printing table list ======= ");
//			for (HTableDescriptor tableDesc : tables) {
//				System.out.println("Table : " + tableDesc.getTableName().getNameAsString());
//			}
//			// Run some operations—three puts, a get, and a scan—against the table.
//			HTable table = new HTable(config, tableName);
//			try {
//				for (int i = 1; i <= 3; i++) {
//					byte[] row = Bytes.toBytes("row" + i);
//					Put putRow = new Put(row);
//					byte[] columnFamily = Bytes.toBytes("MyColumnFamily");
//					byte[] qualifier = Bytes.toBytes("MyColumn_" + String.valueOf(i));
//					byte[] value = Bytes.toBytes("value" + i);
//					putRow.add(columnFamily, qualifier, value);
//					table.put(putRow);
//				}
//				
//				Get get = new Get(Bytes.toBytes("row1"));
//				Result result = table.get(get);
//				System.out.println("Get1:	" + result);
//				byte[] columnValue = result.getValue(
//						Bytes.toBytes("MyColumnFamily"),
//						Bytes.toBytes("MyColumn_1"));
//				System.out.println("Get2:	" + 
//						new String(columnValue) );
//
//				System.out.println("======================");
//				byte[] columnValue_nonExist = result.getValue(
//						Bytes.toBytes("MyColumnFamily"),
//						Bytes.toBytes("MyColumn_DoesNotExist"));
//				System.out.println("Get_nonExist:	" + 
//						new String(columnValue_nonExist) );
//				System.out.println("======================");
//				
//				Scan scan = new Scan();
//				ResultScanner scanner = table.getScanner(scan);
//				try {
//					for (Result scannerResult : scanner) {
//						System.out.println("Scan:	" + scannerResult);
//						for (KeyValue keyValue : scannerResult.list()) {
//							System.out.println(" Value : " 
//									+ Bytes.toString(keyValue.getValue()));
//						}
//					}
//				} finally {
//					scanner.close();
//				}
//				// Disable then drop the table
//				admin.disableTable(tableName);
//				admin.deleteTable(tableName);
//			} finally {
//				table.close();
//			}
//		} finally {
//			admin.close();
//		}
//	}
//
//}
