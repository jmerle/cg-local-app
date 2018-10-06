# CG Local Application

This application should be used together with [cg-local-ext](https://github.com/jmerle/cg-local-ext). The application watches the selected file and sends the source code to the WebExtension when the file is changed.

## Installation
1. Download the latest jar file from the [releases](https://github.com/jmerle/cg-local-app/releases) page.
2. Make sure you are using a Java 8 runtime. CG Local uses [TornadoFX](https://github.com/edvin/tornadofx), which isn't compatible with Java 9/10 at the moment. If you are using Linux, [SDKMAN](https://sdkman.io/) can be used to easily manage various Java versions.
3. Run the application with `java -jar <jar file>`, where `<jar file>` is the location of the jar file you just downloaded.

## Contributing
Contributions are welcome, just make sure you send your pull requests towards the develop branch.
