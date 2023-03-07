# pv-addon-lavaplayer-lib
Addon library with bundled [LavaPlayer fork](https://github.com/Walkyst/lavaplayer-fork)

## Using with Bukkit
Install this library as a Bukkit plugin, not as a Plasmo Voice addon

## Adding to the project
### Kotlin DSL
```kotlin
repositories {
    maven("https://repo.plo.su")
}

dependencies {
    compileOnly("su.plo:pv-addon-lavaplayer-lib:1.0.0")
}
```
### Groovy DSL
```groovy
repositories {
    maven { url 'https://repo.plo.su/' }
}

dependencies {
    compileOnly 'su.plo:pv-addon-lavaplayer-lib:1.0.0'
}
```
### Maven
```xml
<project>
    <repositories>
        <repository>
            <id>plasmo-repo</id>
            <url>https://repo.plo.su</url>
        </repository>
    </repositories>
    
    <dependencies>
        <dependency>
            <groupId>su.plo</groupId>
            <artifactId>pv-addon-lavaplayer-lib</artifactId>
            <version>1.0.0</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
</project>
```

## Add library to addon dependencies
### Kotlin
```kotlin
@Addon(
    ...
    dependencies = [
        Dependency(id = "lavaplayer-lib")
    ]
)
```
### Java
```java
@Addon(
    ...
    dependencies = {
        @Dependency(id = "lavaplayer-lib")
    }
)
```
