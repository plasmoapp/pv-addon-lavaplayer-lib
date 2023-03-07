package su.plo.voice.lavaplayer

import su.plo.voice.api.server.PlasmoVoiceServer

class BukkitEntryPoint : org.bukkit.plugin.java.JavaPlugin() {

    override fun onLoad() {
        PlasmoVoiceServer.getAddonManagerInstance().load(LavaPlayerAddon())
    }
}
