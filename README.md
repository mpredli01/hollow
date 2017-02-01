
## Netflix Introduces Hollow, a Java Library for Processing In-Memory Datasets

### InfoQ Java Queue

#### published January 31, 2017

This example application demonstrates how to implement a producer, an API generator, and a consumer for a [Hollow](http://hollow.how/) project. It has been adapted from the original GitHub [project](https://github.com/Netflix/hollow-reference-implementation). 

The application can be built with [Gradle 3.0](https://gradle.org/) by executing the following command:

`gradle clean && gradle build`

In the `build.gradle` file, there are references to the three main methods necessary to run this example application:

`mainClassName = 'org.redlich.hollow.producer.Producer'`
`mainClassName = 'org.redlich.hollow.consumer.Consumer'`
`mainClassName = 'org.redlich.hollow.consumer.api.APIGenerator'`

To run the APIGenerator, comment the `mainClassName` references to the Producer and Consumer and execute the command:

`gradle run`

The generated Java files will be located in folder, `src/main/java/org/redlich/consumer/api/generated`.

To run the Producer, comment the `mainClassName` references to the Consumer and APIGenerator and once again execute the command:

`gradle run`

To run the Consumer, open a new Terminal window, comment the `mainClassName` references to the Producer and APIGenerator and once again execute the command:

`gradle run`

Please don't hesitate to contact me at [mike@redlich.net](mailto:mike@redlich.net) for any questions.
