package me.migoyam.zamknijsie;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class ZamknijSie extends JavaPlugin implements Listener {
    private BukkitTask task;
    private final short minutes = (short) getConfig().getInt("serverShutdownAfter");
    private final String string (){
        if(minutes == 1) {
            return " minute.";
        }else
            return " minutes.";
    }
    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        timer();
    }
    public void timer(){
        if(Bukkit.getOnlinePlayers().size() <= 1) {
            getLogger().info("No players online. Server shutdowns in " + minutes + string());
            task = Bukkit.getScheduler().runTaskLater(this,()-> getServer().shutdown(),minutes * 1200);}}
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Bukkit.getScheduler().cancelTask(task.getTaskId());
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        timer();
    }
}