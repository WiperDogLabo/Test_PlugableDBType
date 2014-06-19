import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Testcase test method getData2CreateMenu(List listjob)
 *
 */
class PlugableDBType_Test_GetData2CreateMenu_FromMongo {
	
	/**
	 * Normal case
	 */
	@Test
	void test01(){
		List listjob = [
			'MySQL.Database_Area.InnoDBTablespace_Free',
			'MySQL.Database_Structure.Parameter',
			'MySQL.Performance.InnoDBBufferPool',
			'MySQL.Proactive_Check.Aborted_Information',
			'OS.CPU_Linux',
			'Postgres.Database_Area.Tablespace_Free',
			'Postgres.Database_Statistic.Database_Info',
			'Postgres.Database_Structure.Database_Version',
			'Postgres.Performance.Buffer_Cache_Hit',
			'Postgres.Proactive_Check.Resource_Limit',
			'SQL_Server.Database_Area.Database_free',
			'SQL_Server.Database_Structure.Database_Version',
			'SQL_Server.Performance.Buffer_Cache_Hit_Ratio',
			'SQL_Server.Proactive_Check.Batch_Requests'
			]
		
		def expectOutput = [
			'MySQL':[:], 
			'MySQL.Database_Area':['MySQL.Database_Area.InnoDBTablespace_Free'], 
			'MySQL.Database_Statistic':[], 
			'MySQL.Database_Structure':['MySQL.Database_Structure.Parameter'], 
			'MySQL.FaultManagement':[], 
			'MySQL.Performance':['MySQL.Performance.InnoDBBufferPool'], 
			'MySQL.Proactive_Check':['MySQL.Proactive_Check.Aborted_Information'], 
			'MySQL.Others':[], 
			'SQL_Server':[:], 
			'SQL_Server.Database_Area':['SQL_Server.Database_Area.Database_free'], 
			'SQL_Server.Database_Statistic':[], 
			'SQL_Server.Database_Structure':['SQL_Server.Database_Structure.Database_Version'], 
			'SQL_Server.FaultManagement':[], 
			'SQL_Server.Performance':['SQL_Server.Performance.Buffer_Cache_Hit_Ratio'], 
			'SQL_Server.Proactive_Check':['SQL_Server.Proactive_Check.Batch_Requests'], 
			'SQL_Server.Others':[], 
			'Postgres':[:], 
			'Postgres.Database_Area':['Postgres.Database_Area.Tablespace_Free'], 
			'Postgres.Database_Statistic':['Postgres.Database_Statistic.Database_Info'], 
			'Postgres.Database_Structure':['Postgres.Database_Structure.Database_Version'], 
			'Postgres.FaultManagement':[], 
			'Postgres.Performance':['Postgres.Performance.Buffer_Cache_Hit'], 
			'Postgres.Proactive_Check':['Postgres.Proactive_Check.Resource_Limit'], 
			'Postgres.Others':[], 
			'MongoDB':[:], 
			'MongoDB.Database_Area':[], 
			'MongoDB.Database_Statistic':[], 
			'MongoDB.Database_Structure':[], 
			'MongoDB.FaultManagement':[], 
			'MongoDB.Performance':[], 
			'MongoDB.Proactive_Check':[], 
			'MongoDB.Others':[], 
			'OS':['OS.CPU_Linux'], 
			'Others':[]]
		def expectRoot = ((new GroovyShell()).evaluate(new File("var/conf/use_for_xwiki.cfg")))['TreeMenuInfo']
		
		def result = GenerateTreeMenu.getData2CreateMenu(listjob)
		
		assertTrue(result.containsKey('root'))
		assertTrue(result.containsKey('output'))
		
		assertEquals(expectRoot, result['root'])
		assertEquals(expectOutput, result['output'])
	}
	
	/**
	 * Another normal case with less data
	 * easy to follow
	 */
	@Test
	void test02(){
		List listjob = [
			'MySQL.Database_Area.InnoDBTablespace_Free',
			'OS.CPU_Linux',
			'Postgres.Database_Area.Tablespace_Free',
			'SQL_Server.Database_Area.Database_free',
			]
		
		def expectOutput = [
			'MySQL':[:],
			'MySQL.Database_Area':['MySQL.Database_Area.InnoDBTablespace_Free'],
			'MySQL.Database_Statistic':[],
			'MySQL.Database_Structure':[],
			'MySQL.FaultManagement':[],
			'MySQL.Performance':[],
			'MySQL.Proactive_Check':[],
			'MySQL.Others':[],
			'SQL_Server':[:],
			'SQL_Server.Database_Area':['SQL_Server.Database_Area.Database_free'],
			'SQL_Server.Database_Statistic':[],
			'SQL_Server.Database_Structure':[],
			'SQL_Server.FaultManagement':[],
			'SQL_Server.Performance':[],
			'SQL_Server.Proactive_Check':[],
			'SQL_Server.Others':[],
			'Postgres':[:],
			'Postgres.Database_Area':['Postgres.Database_Area.Tablespace_Free'],
			'Postgres.Database_Statistic':[],
			'Postgres.Database_Structure':[],
			'Postgres.FaultManagement':[],
			'Postgres.Performance':[],
			'Postgres.Proactive_Check':[],
			'Postgres.Others':[],
			'MongoDB':[:],
			'MongoDB.Database_Area':[],
			'MongoDB.Database_Statistic':[],
			'MongoDB.Database_Structure':[],
			'MongoDB.FaultManagement':[],
			'MongoDB.Performance':[],
			'MongoDB.Proactive_Check':[],
			'MongoDB.Others':[],
			'OS':['OS.CPU_Linux'],
			'Others':[]]
		def expectRoot = ((new GroovyShell()).evaluate(new File("var/conf/use_for_xwiki.cfg")))['TreeMenuInfo']
		
		def result = GenerateTreeMenu.getData2CreateMenu(listjob)
		
		assertTrue(result.containsKey('root'))
		assertTrue(result.containsKey('output'))
		
		assertEquals(expectRoot, result['root'])
		assertEquals(expectOutput, result['output'])
	}
	
	/**
	 * null parameter
	 */
	@Test
	void test03(){
		def result = GenerateTreeMenu.getData2CreateMenu(null)
		assertNotNull(result)
		assertTrue(result.containsKey('root'))
		assertTrue(result.containsKey('output'))
	}
	
	/**
	 * empty parameter
	 */
	@Test
	void test04(){
		def result = GenerateTreeMenu.getData2CreateMenu([])
		assertNotNull(result)
		assertTrue(result.containsKey('root'))
		assertTrue(result.containsKey('output'))
	}
}
