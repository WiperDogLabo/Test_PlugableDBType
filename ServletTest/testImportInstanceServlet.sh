if [[ "$#" < 1 ]]; then
   echo "Incorrect parameter !"
   echo "Usage: ./testImportInstanceServlet.sh /wiperdog_home_path"
   exit;
fi
wiperdogPath=$1

# Config dbms information
echo "WRITING DBMS INFORMATION INTO: " $wiperdogPath/etc/use_for_xwiki.cfg
cat > $wiperdogPath/etc/use_for_xwiki.cfg <<eof
	[
		"DbType": [
			"MySQL": "@MYSQL",
			"SQL_Server": "@MSSQL",
			"Postgres": "@PGSQL",
			"MongoDB": "@MONGO",
			"Maria": "@MARIA"
		],
		"DbConnStrFormat": [
			"MySQL": "jdbc:mysql://{host}:{port}/{schemaName}",
			"SQL_Server": "jdbc:sqlserver://{host}:{port}",
			"Postgres": "jdbc:postgresql://{host}:{port}/{dbName}",
			"MongoDB": "",
			"Maria": ""
		],
		"TreeMenuInfo": [
			"MySQL": [
				"Database_Area":[],
				"Database_Statistic":[],
				"Database_Structure":[],
				"FaultManagement":[],
				"Performance":[],
				"Proactive_Check":[],
				"Others":[]
			],
			"SQL_Server":[
				"Database_Area":[],
				"Database_Statistic":[],
				"Database_Structure":[],
				"FaultManagement":[],
				"Performance":[],
				"Proactive_Check":[],
				"Others":[]
			],
			"Postgres":[
				"Database_Area":[],
				"Database_Statistic":[],
				"Database_Structure":[],
				"FaultManagement":[],
				"Performance":[],
				"Proactive_Check":[],
				"Others":[]
			],
			"MongoDB":[
				"HostInfo":[],
				"ServerStatus":[],
				"Statistics":[],
				"Others":[]
			],
			"Maria":[
				"Database_Area":[],
				"Database_Structure":[],
				"Performance":[],
				"Proactive_Check":[],
				"Others":[]
			],
			"OS":[],
			"Others":[]
		]
	]
eof

echo "WRITE OK, RESTARTING WIPERDOG !!!"
fuser -k 13111/tcp
/bin/sh $wiperdogPath/bin/startWiperdog.sh > /dev/null 2>&1 &
sleep 30
echo "WIPERDOG WAS STARTED !!!"

echo ">>>>> TEST GET METHOD OF ImportInstanceServlet <<<<<"
echo
echo "1. Test generate menu functions (action=getListJob)"
content=$(curl -i -H "Accept: application/json" -H "Content-Type: application/json" 'http://localhost:13111/ImportInstanceServlet?action=getListJob')
echo "Result response data after GET request:"
echo "---------------------------------------"
echo $content
echo "****************"
if [[ $content =~ .*'<li>MongoDB'.* ]]
then
	echo "Successfully !!!"
else
	echo "Failure !!!"
fi
echo "****************"
echo
echo "2. Test servlet funtions (action=getInstance)"
echo "Create and write data to testjob409.instance: " $wiperdogPath/var/job/testjob409.instances
cat > $wiperdogPath/var/job/testjob409.instances <<eof
	[
		"instance_1": [schedule: "10"]
	]
eof
content=$(curl -i -H "Accept: application/json" -H "Content-Type: application/json" 'http://localhost:13111/ImportInstanceServlet?action=getInstance&jobFileName=testjob409.job')
echo "Result response data after GET request:"
echo "---------------------------------------"
echo $content
echo "****************"
if [[ $content =~ .*'instance_1'.*'success'.* ]]
then
	echo "Successfully !!!"
else
	echo "Failure !!!"
fi
echo "****************"