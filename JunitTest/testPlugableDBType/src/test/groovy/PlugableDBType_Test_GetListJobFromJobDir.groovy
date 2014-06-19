import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test method GenerateTreeMenu.getListJobFromJobDir(String JOB_DIR)
 * This method get list job file(.job) from job directory
 * result of this method is input for GenerateTreeMenu.getData2CreateMenu
 */
class PlugableDBType_Test_GetListJobFromJobDir {
	
	/**
	 * Get list job from existed directory
	 * @return list job file in directory(only .job file)
	 */
	@Test
	void test01(){
		List result = GenerateTreeMenu.getListJobFromJobDir("var/job")
		assertNotNull(result)
		assertTrue("List job is empty", result.size() > 0)
		assertTrue(result.contains("MySQL.Database_Area.InnoDBTablespace_Free.job"))
		assertTrue(result.contains("MySQL.Database_Structure.Parameter.job"))
		assertTrue(result.contains("MySQL.Performance.InnoDBBufferPool.job"))
		assertTrue(result.contains("MySQL.Proactive_Check.Aborted_Information.job"))
		assertTrue(result.contains("OS.CPU_Linux.job"))
		assertTrue(result.contains("Postgres.Database_Area.Tablespace_Free.job"))
		assertTrue(result.contains("Postgres.Database_Statistic.Database_Info.job"))
		assertTrue(result.contains("Postgres.Database_Structure.Database_Version.job"))
		assertTrue(result.contains("Postgres.Performance.Buffer_Cache_Hit.job"))
		assertTrue(result.contains("Postgres.Proactive_Check.Resource_Limit.job"))
		assertTrue(result.contains("SQL_Server.Database_Area.Database_free.job"))
		assertTrue(result.contains("SQL_Server.Database_Structure.Database_Version.job"))
		assertTrue(result.contains("SQL_Server.Performance.Buffer_Cache_Hit_Ratio.job"))
		assertTrue(result.contains("SQL_Server.Proactive_Check.Batch_Requests.job"))
		
		assertFalse(result.contains("dumpfile.dump"))
	}
	
	/**
	 * Get list job from non-existed directory
	 * @return empty list
	 */
	@Test
	void test02(){
		List result = GenerateTreeMenu.getListJobFromJobDir("var/job1")
		assertNotNull(result)
		assertTrue(result.size() == 0)
	}
	
	/**
	 * parameter is a file, not directory
	 * @return empty list
	 */
	@Test
	void test03(){
		List result = GenerateTreeMenu.getListJobFromJobDir("var/job/dumpfile.dump")
		assertNotNull(result)
		assertTrue(result.size() == 0)
	}
	
	/**
	 * parameter is a file, not directory
	 * @return empty list
	 */
	@Test
	void test04(){
		List result = GenerateTreeMenu.getListJobFromJobDir(null)
		assertNotNull(result)
		assertTrue(result.size() == 0)
	}
}
