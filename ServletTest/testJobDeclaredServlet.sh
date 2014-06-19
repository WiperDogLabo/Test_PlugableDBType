#!/bin/bash
if [ ! $# -eq 1 ];then
   echo "Incorrect parameter !"
   echo "Usage: ./testJobDeclaredServlet.sh /wiperdog_home_path"
   exit
else
	wiperdog_home=$1
	#check if wiperdog directory exists
	if [ ! -d "$wiperdog_home" ];then
		echo " >> Wiperdog directory does not exists : $wiperdog_home"
		exit
	fi

	# Config dbms information
	echo "WRITING DBMS INFORMATION INTO: " $wiperdog_home/etc/dbms_info.cfg
	cat > $wiperdog_home/etc/dbms_info.cfg <<eof
		[
			"DbType": [
				"MySQL": "@MYSQL",
				"SQL_Server": "@MSSQL",
				"Postgres": "@PGSQL",
				"MongoDB": "@MONGO",
				"MariaDB": "@MARIA"
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
					"Database_Area":[],
					"Database_Statistic":[],
					"Database_Structure":[],
					"FaultManagement":[],
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

echo ">>>>> TEST POST METHOD OF ImportInstanceServlet <<<<<"
content=$(curl -X POST -H "Accept: application/json" -H "Content-Type: application/json" 'http://localhost:13111/JobDeclared' -d '{"COMMAND":"Write","JOB":{"monitoringType":"@DB","dbType":"MongoDB","jobName":"MongoDB.Database_Area.testjob409","jobFileName":"MongoDB.Database_Area.testjob409","fetchAction":"{\n    return \"Data return issue 409\"\n}","sendType":"Store","resourceId":"Sr/PgDbVer","KEYEXPR":{"_chart":{}}},"PARAMS":{},"INSTANCES":{}}')
echo "Result response data after POST request:"
echo "--------------------------------------------"
echo $content
echo "--------------------------------------------"
if [[ $content =~ '{"status":"OK","message":"Finish process successfully"}' ]]
then
	echo "Get data to generate menu successfully!!!"
	echo "Check file MongoDB.Database_Area.testjob409 was created in /var/job to confirm !!!"
else
	echo "Get data to generate menu failure!!!"
fi