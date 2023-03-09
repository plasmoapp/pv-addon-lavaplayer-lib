# pv-addon-lavaplayer-lib
Addon library with bundled [LavaPlayer fork](https://github.com/Walkyst/lavaplayer-fork)

## Adding to the project
### Kotlin DSL
```kotlin
repositories {
    maven("https://repo.plo.su")
}

dependencies {
    compileOnly("su.plo:pv-addon-lavaplayer-lib:1.0.1")
}
```
### Groovy DSL
```groovy
repositories {
    maven { url 'https://repo.plo.su/' }
}

dependencies {
    compileOnly 'su.plo:pv-addon-lavaplayer-lib:1.0.1'
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
            <version>1.0.1</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
</project>
```

## Using with universal addons
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
## Using with Bukkit plugins
Add `pv-addon-lavaplayer-lib` to `plugin.yml` `depend`:
```yaml
depend:
  - pv-addon-lavaplayer-lib
```
