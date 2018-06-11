# CCP Ver.1 by Shiwei Li

2018.06.10

## 1. Design

This system is designed to simulate a credit card processing system. 
The structure of source code is very straight forward.
  
```	
* Main functionality is located under com.brtr.cpp
* Utility classes are under com.brtr.utils
* data classes, enums are under com.brtr.model
* test is under com.brtr.test
```
  
  While running, a processor object is initiated, which will keep the accounts data in a Map with name as key while the program is running. 

  Hence credit card number is validated. The number itself is not stored in the system. We only flag if a user/account has a valid or invalid credit card. In future enhancement, credit card can be stored, but only under encrypted circumstances.

## 2. Language

This program is written in Java. This object oriented language is suitable for this program to be extended and easy to add more functions on to it. Most importantly, I am mostly familiar with Java programming ;).

## 3. Running the program
  
* 3.1 Compile and run the main program
     To compile, download the source code, put it under your chosen directory **<your path>**
     
     run following command lines:
     
     	cd <your path>/ccp-master/src/com/brtr
     	javac ccp/*.java model/*.java util/*.java model/command/*.java test/*.java
     	cd <your path>/ccp-master
     	java -cp <your path>/ccp-master/src/ com.brtr.ccp.CreditSystem input.txt

		 
* 3.2 Compile and run test cases
     

     	cd <your path>/ccp-master/src/com/brtr
     	javac ccp/*.java model/*.java util/*.java model/command/*.java test/*.java
     	cd <your path>/ccp-master
     	java -cp <your path>/ccp-master/src/ com.brtr.test.CreditSystemTest

			
* 3.3 Add more test cases

     create test cases with name **"test_input_<# of case>.txt"**, put it under **<your path>/ccp/testcases**
	
     Write expected result of output file with name **"test_r_<# of case>.txt"**, put it under **<your path>/ccp/testcases/results**, here "# of case" should be identical with the case itself.
  
## 4. Possible improvents and optimization
  
     1. Setup the system with a framework which will allow more robust and convenient unit tests.
     
     2. Implement more interactive user experience - Completed
     
          Switch main() in CreditSystem.java by commentting current main() function and uncomment the other currently commented main() function. 
          
          2.1 tests for new functions are also needed.
          
     3. More comprehensive exception handling, logging functionality
     
     4. Data storage, store accounts information while the system is offline. Also this will include a mechanism which allows loading/initiation of data
     
     5. Keep track of all commands that user has requested. 
     
     6. Encrypt credit card and store it. Currently the credit card is not stored.
     
     7. More comprehensive credit card processing regulations(restrictions, negative balance bans, etc)
     
     8. Localization mechanism
     
*This program is also updated on GitHub at https://github.com/shiwei2018/ccp/
