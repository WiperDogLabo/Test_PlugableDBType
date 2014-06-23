#!/bin/bash
if [ ! $# -eq 1 ];then
   echo "Incorrect parameter !"
   echo "Usage: ./testDbmsInfoRestService.sh /wiperdog_home_path"
   exit
else
	wiperdog_home=$1
	#check if wiperdog directory exists
	if [ ! -d "$wiperdog_home" ];then
		echo " >> Wiperdog directory does not exists : $wiperdog_home"
		exit
	fi

	# Config dbms information
	echo "WRITING DBMS INFORMATION INTO: " $wiperdog_home/etc/use_for_xwiki.cfg
	cat > $wiperdog_home/etc/use_for_xwiki.cfg <<eof
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

	# Restart wiperdog to apply new configuration of dbms
	echo "** STARTING WIPERDOG ..."
	fuser -k 13111/tcp
	/bin/sh $wiperdog_home/bin/startWiperdog.sh > /dev/null 2>&1 &
	sleep 30
	echo "** WIPERDOG WAS RUNNING ..."
fi

echo ">>>>> TEST GET METHOD OF DbmsInfoRestService <<<<<"
curl -i -H "Accept: application/json" -H "Content-Type: application/json" 'http://localhost:8089/use_for_xwiki'
echo
echo "Case 1. Get all information in use_for_xwiki.cfg"
content=$(curl -i -H "Accept: application/json" -H "Content-Type: application/json" 'http://localhost:8089/use_for_xwiki')
echo "Result response data after GET request:"
echo "--------------------------------------------"
echo $content
echo "****************"
if [[ $content =~ .*'DbType'.*'DbConnStrFormat'.*'TreeMenuInfo'.* ]]
then
	echo "Successfully !!!"
else
	echo "Failure !!!"
fi
echo "****************"
echo
echo "Case 2. Get information in use_for_xwiki.cfg corresponds to key \"DbType\""
content=$(curl -i -H "Accept: application/json" -H "Content-Type: application/json" 'http://localhost:8089/use_for_xwiki/DbType')
echo "Result response data after GET request:"
echo "--------------------------------------------"
echo $content
echo "****************"
if [[ $content =~ .*'"MySQL":"@MYSQL","SQL_Server":"@MSSQL","Postgres":"@PGSQL","MongoDB":"@MONGO","Maria":"@MARIA"'.* ]]
then
	echo "Successfully !!!"
else
	echo $content
	echo "Failure !!!"
fi
echo "****************"
echo
echo "Case 3. Get information in use_for_xwiki.cfg corresponds to key \"TreeMenuInfo\""
content=$(curl -i -H "Accept: application/json" -H "Content-Type: application/json" 'http://localhost:8089/use_for_xwiki/TreeMenuInfo')
echo "Result response data after GET request:"
echo "--------------------------------------------"
echo $content
echo "****************"
if [[ $content =~ .*'MongoDB'.*'Maria'.* ]]
then
	echo "Successfully !!!"
else
	echo "Failure !!!"
fi
echo "****************"
echo
echo "Case 4. Get information in use_for_xwiki.cfg corresponds to key \"DbConnStrFormat\""
content=$(curl -i -H "Accept: application/json" -H "Content-Type: application/json" 'http://localhost:8089/use_for_xwiki/DbConnStrFormat')
echo "Result response data after GET request:"
echo "--------------------------------------------"
echo $content
echo "****************"
if [[ $content =~ .*'jdbc:mysql:'.*'jdbc:sqlserver:'.*'jdbc:postgresql:'.* ]]
then
	echo "Successfully !!!"
else
	echo "Failure !!!"
fi
echo "****************"
