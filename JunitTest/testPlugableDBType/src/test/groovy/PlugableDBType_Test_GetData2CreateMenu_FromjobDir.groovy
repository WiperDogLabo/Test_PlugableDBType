import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Testcase test method getData2CreateMenu(String JOB_DIR)
 *
 */
class PlugableDBType_Test_GetData2CreateMenu_FromjobDir {
	
	/**
	 * Normal use case
	 */
	@Test
	void test01(){
		def expectRoot = ((new GroovyShell()).evaluate(new File("var/conf/use_for_xwiki.cfg")))['TreeMenuInfo']
		def expectOutput = [
			'MySQL':[:], 
			'MySQL.Database_Area':['MySQL.Database_Area.InnoDBTablespace_Free.job'], 
			'MySQL.Database_Statistic':[], 
			'MySQL.Database_Structure':['MySQL.Database_Structure.Parameter.job'], 
			'MySQL.FaultManagement':[], 
			'MySQL.Performance':['MySQL.Performance.InnoDBBufferPool.job'], 
			'MySQL.Proactive_Check':['MySQL.Proactive_Check.Aborted_Information.job'], 
			'MySQL.Others':[], 
			'SQL_Server':[:], 
			'SQL_Server.Database_Area':['SQL_Server.Database_Area.Database_free.job'], 
			'SQL_Server.Database_Statistic':[], 
			'SQL_Server.Database_Structure':['SQL_Server.Database_Structure.Database_Version.job'], 
			'SQL_Server.FaultManagement':[], 
			'SQL_Server.Performance':['SQL_Server.Performance.Buffer_Cache_Hit_Ratio.job'], 
			'SQL_Server.Proactive_Check':['SQL_Server.Proactive_Check.Batch_Requests.job'], 
			'SQL_Server.Others':[], 
			'Postgres':[:], 
			'Postgres.Database_Area':['Postgres.Database_Area.Tablespace_Free.job'], 
			'Postgres.Database_Statistic':['Postgres.Database_Statistic.Database_Info.job'], 
			'Postgres.Database_Structure':['Postgres.Database_Structure.Database_Version.job'], 
			'Postgres.FaultManagement':[], 'Postgres.Performance':['Postgres.Performance.Buffer_Cache_Hit.job'], 
			'Postgres.Proactive_Check':['Postgres.Proactive_Check.Resource_Limit.job'], 
			'Postgres.Others':[], 
			'MongoDB':[:], 
			'MongoDB.Database_Area':[], 
			'MongoDB.Database_Statistic':[], 
			'MongoDB.Database_Structure':[], 
			'MongoDB.FaultManagement':[], 
			'MongoDB.Performance':[], 
			'MongoDB.Proactive_Check':[], 
			'MongoDB.Others':[], 
			'OS':['OS.CPU_Linux.job'], 
			'Others':[]]
		
		def result = GenerateTreeMenu.getData2CreateMenu("var/job")
		
		assertTrue(result.containsKey('root'))
		assertTrue(result.containsKey('output'))
		
		assertEquals(expectRoot, result['root'])
		assertEquals(expectOutput, result['output'])
	}
	
	/**
	 * null parameter
	 */
	@Test
	void test02(){
		def result = GenerateTreeMenu.getData2CreateMenu(null)
		assertNotNull(result)
		assertTrue(result.containsKey('root'))
		assertTrue(result.containsKey('output'))
	}
}
