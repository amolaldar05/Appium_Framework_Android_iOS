1. ScreenshotUtil: get driver from DriverManager
2. BaseClass: get and set driver from DriverManager
3. platformName: first check extended test (iOS/Android) if we are running testcases on iOS or Android, then it will check from testng parameter if we are running tests from testngxml then it will check from System.getProperty("platformName") if not then it will check from System.getProperty("platformname") if it is windows then it will return "Android" else it will return "iOS", last it will check from config.properties file if it is not set then it will return "Android" by default.

