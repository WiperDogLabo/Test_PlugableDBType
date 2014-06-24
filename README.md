Test_PlugableDBType
===================
Testing Wiperdog's PlugableDBType  
Now all the config are contained in etc/use_for_xwiki.cfg, user can config to use any database they want.  
This is used for test that function  

1. JUnitTest  
   Test wiperdog's library  
   Usage: 
    ```

      Run mvn -DTest=* test  

      Or  
      
      Import as maven project and run Junit Test  
      
    ```
2. ServletTest
   Test servlet and RestAPI Service used for getting data  
   Usage:   
    ```
      Run Wiperdog and wait for the service all online  

      Execute all the shell scripts   
    ```
3. XwikiTest
   Contains selenium testcase used for testing layout and function of xwiki page  
   Usage:  
    ```
      Run Wiperdog, make sure there are at lease 1 record of SQL_Server.Performance.Memory_Management.<IstIid> in MongoDB 

      Now open selenium and add all testcases into it    
      
      Set url to test, run test at average speed (If it runs too fast then the javascript processes can not catch up and produce errors). 
    ```
