# pv-addon-lavaplayer-lib
Addon library bundled with [LavaPlayer fork](https://github.com/Walkyst/lavaplayer-fork) library that is needed for some Plasmo Voice add-ons.

## Installation

1. Download the add-on from [Modrinth](https://modrinth.com/mod/pv-addon-lavaplayer-lib)
2. Install the add-on:
    - If you need the library for a Paper add-on, install it as a Paper plugin in the `~/plugins` directory.
    - If you need the library for a universal add-on, install it as a universal PV add-on.
        - For Paper, install the library in `~/plugins/PlasmoVoice/addons`.
        - For Fabric or Forge, install the library in `~/mods/PlasmoVoice/addons`.
    - If you have both universal and Paper add-ons that require the library, you need to install it as both a universal and a Paper add-on.
3. Restart the server

## Adding to the project (For developers)
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
