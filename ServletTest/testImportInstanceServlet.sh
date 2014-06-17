if [[ "$#" < 1 ]]; then
   echo "Incorrect parameter !"
   echo "Usage: ./testImportInstanceServlet.sh /wiperdog_home_path"
   exit;
fi
wiperdogPath=$1

# Config dbms information
echo "WRITING DBMS INFORMATION INTO: " $wiperdogPath/etc/dbms_info.cfg
cat > $wiperdogPath/etc/dbms_info.cfg <<eof
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

echo "WRITE OK, RESTARTING WIPERDOG !!!"
fuser -k 13111/tcp
/bin/sh $wiperdogPath/bin/startWiperdog.sh > /dev/null 2>&1 &
sleep 30
echo "WIPERDOG WAS STARTED !!!"

echo ">>>>> TEST GET METHOD OF ImportInstanceServlet <<<<<"
content=$(curl -i -H "Accept: application/json" -H "Content-Type: application/json" 'http://localhost:13111/ImportInstanceServlet?action=getListJob')
echo "Result response data after GET request:"
echo "--------------------------------------------"
echo $content
echo "--------------------------------------------"
if [[ $content =~ .*'<li>MongoDB'.* ]]
then
	echo "Get data to generate menu successfully!!!"
else
	echo "Get data to generate menu failure!!!"
fi