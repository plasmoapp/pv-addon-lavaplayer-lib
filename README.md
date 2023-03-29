![](https://i.imgur.com/Z5S9yGW.png)

<div>
    <a href="https://modrinth.com/mod/plasmo-voice">Plasmo Voice</a>
    <span> | </span>
    <a href="https://modrinth.com/mod/pv-addon-lavaplayer-lib">Modrinth</a>
    <span> | </span>
    <a href="https://www.spigotmc.org/resources/plasmo-voice-server.91064/">Spigot</a>
    <span> | </span>
    <a href="https://github.com/plasmoapp/pv-addon-lavaplayer-lib/">GitHub</a>
    <span> | </span>
    <a href="https://discord.com/invite/uueEqzwCJJ">Discord</a>
     <span> | </span>
    <a href="https://www.patreon.com/plasmomc">Patreon</a>
</div>

# pv-addon-lavaplayer-lib

Addon library bundled with [LavaPlayer fork](https://github.com/Walkyst/lavaplayer-fork) library that is needed for some Plasmo Voice add-ons.

## Installation

1. Download the add-on from [Modrinth](https://modrinth.com/mod/pv-addon-lavaplayer-lib)
2. Install the add-on:
   - For Paper, install the library in `~/plugins`.
   - For Fabric or Forge, install the library in `~/mods`.
3. Restart the server

## Adding to the project (For developers)
### Kotlin DSL
```kotlin
repositories {
    maven("https://repo.plo.su")
}

dependencies {
    compileOnly("su.plo:pv-addon-lavaplayer-lib:1.0.2")
}
```
### Groovy DSL
```groovy
repositories {
    maven { url 'https://repo.plo.su/' }
}

dependencies {
    compileOnly 'su.plo:pv-addon-lavaplayer-lib:1.0.2'
}
```

## Using with universal addons
### Kotlin
```kotlin
@Addon(
    ...
    dependencies = [
        Dependency(id = "pv-addon-lavaplayer-lib")
    ]
)
```
### Java
```java
@Addon(
    ...
    dependencies = {
        @Dependency(id = "pv-addon-lavaplayer-lib")
    }
)
```
## Using with Bukkit plugins
Add `pv-addon-lavaplayer-lib` to `plugin.yml` `depend`:
```yaml
depend:
  - pv-addon-lavaplayer-lib
```
