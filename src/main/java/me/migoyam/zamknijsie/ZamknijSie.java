package me.migoyam.zamknijsie;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class ZamknijSie extends JavaPlugin implements Listener {
    private BukkitTask task;
    private final int minutes = getConfig().getInt("serverShutdownAfter");
    private final String string = (minutes == 1) ? " minute." : " minutes.";
    public void timer(){
        if(getServer().getOnlinePlayers().size() == 0) {
            getLogger().info("No players online. Server shutdowns in " + minutes + string);
            task = getServer().getScheduler().runTaskLater(this,()->{
                getServer().shutdown();
                getLogger().info("Shutdown");
            },minutes * 1200L);}}
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        timer();
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        task.cancel();
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        getServer().getScheduler().runTaskLater(this, this::timer, 1);
    }
}