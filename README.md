# silo-system
#### System of 4 silos filling with liquid and processing it. Control is done through actuators and sensors. Detailed UML is used.

##### Paradigm taken from F. Basile, et al. “On the Implementation of Industrial Automation Systems Based on PLC”, IEEE Trans. on Automation Science and Engineering, vol. 10, no. 4, pp.990-1003, Oct 2013.
##### Assignment done in class 22G902: Analysis & Design of Software Systems by Kleanthis Thramboulidis

![alt text](https://github.com/Spiros-Dimitriou/silo-system/blob/master/liqueur-plant.png?raw=true)

### How the system operates
The system can be described as 4 independent silos (s1-s4), each one with different processing inside it, one shared pipe and shared power source for the heavy machinery (mixers).
There are 2 types of liqueurs that can come out of this system. One is type A through silo 1 and 4. The other one is type B through silo 2 and 3.
Therefore two processes run concurrently at the system. One for liqueur type A and one for liqueur type B.
Considering liqueur type A, the process consists of inserting liquid through the IN1 valve, then after it's full, transfer it to silo 4 through the shared pipe. Then in silo 4 heat the liquid till a certain temperature is reached, then mix it then drain it out.
The process for type B liqueur is similar as the liquid goes through silos 2 and 3.

The pipe cannot be used by the 2 processes at the same time. Also the mixers (m3,m4) cannot be used at the same time.

### Approach
The object oriented analysis is used, therefore [UML diagrams](https://github.com/Spiros-Dimitriou/silo-system/tree/master/UML) on use cases, classes, states and sequence were used to develop the application.
