## Transformers Battle Simulator
This is a tech assignment for Java Back-End Developer

## How to build and run the unit tests
Use `mvn clean build` to build the project. You will all see the unit tests running information on console:

```
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ TransformerBattleSimulator ---
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.transformer.service.BattleServiceTest
[INFO] Tests run: 11, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.137 s - in com.transformer.service.BattleServiceTest
```
## How to run the application
- 1. Directly run the jar. Find the jar file from [here](https://github.com/ft3049354/TransformerBattleSimulator/tree/master/jar), then download it and run the command

		`java -jar {{the download path}}\TransformerBattleSimulator-0.0.1-SNAPSHOT.jar`

     
   Once you see `Started TransformerBattleSimulatorApplication in 1.374 seconds`, then you can go to [localhost:8080](localhost:8080) to enter the application

- 2. Open the prject in Eclipse IDE. Then Run it as the Spring Boot App.
	The default port is 8080, You can change the port in `src/main/resources/application.properties`


## the API endpoints and the example payloads
After the app is started up, you can visit localhost:8080 to view the page. There already has some examples fill in, you can also change or replace the content to simulate the battle. Click the "Start Battle!" button to simulate. There will be serveral check in javascript to make sure the data is usable. Once the input content pass all the check, it will call POST `\battle` to start the API and return the battle result.
Here is an examples for the content:
```
Soundwave, D, 8,9,2,6,7,5,6,10
Bluestreak, A, 6,6,7,9,5,2,9,7
Hubcap, A, 4,4,4,4,4,4,4,4
```

Battle result will diplayed as:
```
1 battle 
Winning team (Decepticons): Soundwave 
Survivors from the losing team (Autobots): Hubcap
```
## Assumptions
- If the winners of the winning team are more than 1, the winner will be all listed.
- Also if the survivors of the losing team are more than 1, the survivors will be all listed.
- If the total battle result is tied, there won't output any competitors. Instead will output the tied battle message. For each tied battle, both competitors will be eliminated which means no winners.If you see total battle amount is more than winners amount, that mean there has at least a tied game.
- As I only created one controller, so I think the the unite tests for the controller looks like an integration test, so I just leave it as blank.I put most unit tests in the service classes.
