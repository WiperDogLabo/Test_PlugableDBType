import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

/**
 * Test GenerateTreeMenu.getListJobFromMongo()
 * This method get list job from mongodb
 * result of this method is input for GenerateTreeMenu.getData2CreateMenu
 *
 */
class PlugableDBType_Test_GetListJobFromMongo {
	def HOST = "10.0.0.94"
	def PORT = 27017
	String job1 = "DBTYPE_TEST.DIVISION_TEST.job1.localhost-@MSSQL"
	String job2 = "DBTYPE_TEST.DIVISION_TEST.job2.localhost-@MSSQL"
	MongoClient client = new MongoClient(HOST, PORT)
	DB db = client.getDB("wiperdog")
	/**
	 * Add some testing collections
	 */
	@Before
	void prepare(){
		DBCollection col = db.getCollection(job1)
		col.insert(new BasicDBObject([a:'A']))
		col = db.getCollection(job2)
		col.insert(new BasicDBObject([a:'A']))
	}
	
	@After
	void finish(){
		db.getCollection(job1).drop()
		db.getCollection(job2).drop()
		client.close()
	}
	
	/**
	 * Normal case
	 * Mongo has wiperdog db
	 * Has at least 1 collection from wiperdog with format
	 * {DBTYPE}.{DIVISION}.{JOBNAME}.{ISTIID}
	 * @return List job with format
	 * {DBTYPE}.{DIVISION}.{JOBNAME}
	 */
	@Test
	void test01(){
		GenerateTreeMenu.HOST = HOST
		GenerateTreeMenu.PORT = PORT
		List result = GenerateTreeMenu.getListJobFromMongo()
		assertNotNull(result)
		assertTrue(result.size() > 0)
		assertTrue(result.contains(job1.substring(0, job1.lastIndexOf("."))))
		assertTrue(result.contains(job2.substring(0, job2.lastIndexOf("."))))
	}
	
	/**
	 * Mongo has wiperdog db
	 * Has collection with wrong format
	 * @return List job with full collection name
	 */
	@Test
	void test02(){
		// Add some dump collections with wrong format name
		db.getCollection("DBTYPE.WRONGFORMAT").insert(new BasicDBObject([a:"A"]))
		db.getCollection("DBTYPE.WRONGFORMAT2").insert(new BasicDBObject([a:"A"]))
		
		GenerateTreeMenu.HOST = HOST
		GenerateTreeMenu.PORT = PORT
		List result = GenerateTreeMenu.getListJobFromMongo()
		assertNotNull(result)
		assertTrue(result.size() > 0)
		assertTrue(result.contains(job1.substring(0, job1.lastIndexOf("."))))
		assertTrue(result.contains(job2.substring(0, job2.lastIndexOf("."))))
		assertTrue(result.contains("DBTYPE.WRONGFORMAT"))
		assertTrue(result.contains("DBTYPE.WRONGFORMAT2"))
		
		// Cleanup
		db.getCollection("DBTYPE.WRONGFORMAT").drop()
		db.getCollection("DBTYPE.WRONGFORMAT2").drop()
	}
	
	/**
	 * Mongodb's wiperdog database has job with same name but diffirent IstIid
	 * result must show only one job
	 * Example: 
	 *  - dbtype.division.job1.istiid_1
	 *  - dbtype.division.job1.istiid_2
	 *  - dbtype.division.job1.istiid_3
	 *  => Result should has only 1 record of dbtype.division.job1 
	 */
	@Test
	void test03(){
		// Add some dump collections with wrong format name
		db.getCollection("DBTYPE_TEST.DIVISION_TEST.job1.localhost-@MSSQL_1").insert(new BasicDBObject([a:"A"]))
		db.getCollection("DBTYPE_TEST.DIVISION_TEST.job1.localhost-@MSSQL_2").insert(new BasicDBObject([a:"A"]))
		db.getCollection("DBTYPE_TEST.DIVISION_TEST.job1.localhost-@MSSQL_3").insert(new BasicDBObject([a:"A"]))
		
		
		GenerateTreeMenu.HOST = HOST
		GenerateTreeMenu.PORT = PORT
		List result = GenerateTreeMenu.getListJobFromMongo()
		assertNotNull(result)
		assertTrue(result.size() > 0)
		assertTrue(result.contains(job1.substring(0, job1.lastIndexOf("."))))
		assertTrue(result.contains(job2.substring(0, job2.lastIndexOf("."))))
		
		// Check result only has 1 record of job
		def tmp = result.findAll {
			it == "DBTYPE_TEST.DIVISION_TEST.job1"
		}
		assertTrue(tmp.size() == 1)
		
		// Cleanup
		db.getCollection("DBTYPE_TEST.DIVISION_TEST.job1.localhost-@MSSQL_1").drop()
		db.getCollection("DBTYPE_TEST.DIVISION_TEST.job1.localhost-@MSSQL_2").drop()
		db.getCollection("DBTYPE_TEST.DIVISION_TEST.job1.localhost-@MSSQL_3").drop()
	}
}
